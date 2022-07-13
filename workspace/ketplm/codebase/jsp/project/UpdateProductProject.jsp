<%@page import="ext.ket.cost.util.CostUtil"%>
<%@page import="ext.ket.cost.entity.PartList"%>
<%@page import="ext.ket.project.program.entity.ProgramDTO"%>
<%@page import="ext.ket.project.program.service.ProgramHelper"%>
<%@page import="ext.ket.part.entity.KETPartClassification"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import = "java.util.*, java.sql.*"%>
<%@page import = "wt.fc.*,
                          wt.org.*,
                          wt.project.Role,
                          wt.query.*,
                          wt.session.*,
                          wt.team.*"%>
<%@page import = "e3ps.common.code.*,
                          e3ps.common.jdf.config.Config,
                          e3ps.common.jdf.config.ConfigImpl,
                          e3ps.common.query.SearchUtil,
                          e3ps.common.util.*,
                          e3ps.common.web.*,
                          e3ps.ews.dao.PartnerDao,
                          e3ps.groupware.company.*,
                          e3ps.project.*,
                          e3ps.project.beans.*,
                          e3ps.project.outputtype.OEMProjectType,
                          e3ps.common.code.NumberCodeHelper,
                          e3ps.common.db.DBCPManager"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />


<%
    String oid = request.getParameter("oid");
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    List<ProgramDTO> programs = ProgramHelper.service.findProgramByProject(oid);
    
    String subContractorOid = "";
    String carTypeOid = "";
    

    for(ProgramDTO program : programs){
        if("1".equals(program.getBaseProgram())) {
            subContractorOid = StringUtil.checkNull(program.getSubContractorOid());
            carTypeOid = StringUtil.checkNull(program.getCarTypeOid());
        }
    }
    
    String state = project.getState().toString();//프로젝트 상태
    
    E3PSProjectData projectData = new E3PSProjectData(project);
    ProductProject productProject = (ProductProject)project;
    
    String linkpjt = productProject.getMaster().getLinkProjectNo();
    String linkpjtoid = "";
    if((productProject.getProcess().getCode().equals("PC002") || productProject.getProcess().getCode().equals("PC005")) && !StringUtil.isEmpty(linkpjt)){
	   linkpjtoid = CommonUtil.getOIDString(ProjectHelper.getProject(linkpjt));     
    }

    boolean isPM = ((ProductProject)project).isIsPM();

    String initType = request.getParameter("initType");
    String projectConsignment = request.getParameter("projectConsignment");
    String projectSite = request.getParameter("projectSite");
    String projectAcceptanceType = request.getParameter("projectAcceptanceType");
    String projectSaleType = request.getParameter("projectSaleType");
    String projectType = "2";
    String pmoid = request.getParameter("pmoid");
    String tempOid = request.getParameter("tempOid");
    String product = request.getParameter("product");
    String pwlinkOid = request.getParameter("pwlinkOid");
    String teamType = "";
    if(pwlinkOid == null){
        pwlinkOid = "";
    }

    if(product == null){
        product ="";
    }

    ProjectManager pm = null;
    if(pmoid == null){
        pmoid = "";
    }else{
        pm = (ProjectManager)CommonUtil.getObject(pmoid);
    }

    if(tempOid == null){
        tempOid = "";
    }

    String oemTypeValue = "";
    if(pm != null){
        OEMProjectType ot = pm.getOemType();
        oemTypeValue = CommonUtil.getOIDString(ot);
    }

    Config conf = ConfigImpl.getInstance();

    String lifecycle = conf.getString("lifecycle.newproject");

    if(initType == null) {
        initType = "produceproject";
    }

    if(projectConsignment == null) {
        projectConsignment = "";
    }

    if(projectSite == null) {
        projectSite = "";
    }

    if(projectAcceptanceType == null) {
        projectAcceptanceType = "";
    }

    if(projectSaleType == null) {
        projectSaleType = "";
    }

    Vector vecTeamStd = null;
    TeamTemplate tempTeam = null;
    if("자동차 사업부".equals( ((ProductProject)project).getTeamType() )){
        tempTeam =  TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");
    }else{
        tempTeam =  TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Electron Project");
    }

    if(tempTeam != null) {
        vecTeamStd = tempTeam.getRoles();
    }

    String tmpTitle = "";
    Vector tMap = null;

    Map<String, Object> parameter = new HashMap<String, Object>();
    List<Map<String, Object>> numCode = null;

    WTUser sessionUser = (WTUser)SessionHelper.manager.getPrincipal();
    PeopleData pd = new PeopleData(sessionUser);
    
    
    String develop_typed_check = StringUtil.checkNull(project.getDevelopedType()); //개발유형
    String[] develop_typed_temp = null;
    
    List<Map<String, Object>> develop_typed_list = null;
    Map<String, Object> develop_typed_map = null;
    
    if(!"".equals(develop_typed_check)){
	    develop_typed_temp = develop_typed_check.split("\\|");
	    parameter.clear();
	    parameter.put("locale",   messageService.getLocale());
	    parameter.put("codeType", "DEVELOPPATTERN");
	    numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
	
	    develop_typed_list = new ArrayList<Map<String, Object>>();
	    
	    for ( int j=0; j < numCode.size(); j++ ) {
		    develop_typed_map = new HashMap<String, Object>();
	        
	        for(int i=0; i<develop_typed_temp.length; i++ ){
	            if(develop_typed_temp[i].equals(numCode.get(j).get("code"))){
	        	   develop_typed_map.put("flag", "1");
	            }
	        }
	        
	        develop_typed_map.put("code", numCode.get(j).get("code"));
	        develop_typed_map.put("value", numCode.get(j).get("value"));
	        develop_typed_list.add(develop_typed_map);
	    }
    }
    
    String devROid = "";
    String devRNumber = "";

    boolean isCostRequest = false;
    
    String selectedReviewProjectNos = StringUtil.checkNull(productProject.getReviewPjtNo());
    
    List<Map<String, Object>> reviewListByReq = new ArrayList<Map<String, Object>>();
    if (project.getDevRequest() != null) {
	   devROid = CommonUtil.getOIDString(project.getDevRequest());
	   devRNumber = project.getDevRequest().getNumber();
	   if(project.getDevRequest() != null && !"".equals(StringUtil.checkNull(project.getDevRequest().getProjectOID()) )){
	       String reviewOids[] = project.getDevRequest().getProjectOID().split(",");
	       for(String Roid : reviewOids){
	           Map<String, Object> reviewdataMap = new HashMap<String, Object>();
	           E3PSProjectMaster matser = (E3PSProjectMaster)productProject.getMaster();
	           if(CostUtil.manager.getPartList(matser) != null){
	           isCostRequest = true;
	           }
	           String pjtNo = ((ReviewProject)CommonUtil.getObject(Roid)).getPjtNo();
	           reviewdataMap.put("pjtNo", pjtNo);
	           reviewdataMap.put("reviewOid", Roid);
	           reviewdataMap.put("selected", "");
	           if(selectedReviewProjectNos.contains(pjtNo)){
	               reviewdataMap.put("selected", "selected");
	           }
	           reviewListByReq.add(reviewdataMap);
	       }    
	   }
	   
    }
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<TITLE><%=messageService.getString("e3ps.message.ket_message", "02632") %><%--제품프로젝트 수정--%></TITLE>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 0px;
}
-->
</style>
<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="JavaScript">
<!--
// str은 0~9까지 숫자만 가능하다.
    function checkNumber(str) {
        var flag=true;
        if (str.length > 0) {
            for (i = 0; i < str.length; i++) {
                if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                    flag=false;
                }
            }
        }
        return flag;
    }
    
    function checkNumber2(str) {
        var flag=true;
        if (str.length > 0) {
            for (i = 0; i < str.length; i++) {
                if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                    flag=false;
                }
            }
        }
        if(str == "0"){
              flag=false;
        }
        return flag;
    }
    
    //Create Function
    function onSave(){

        if(!checkValidate()) {
            return;
        }

        if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "03119") %><%--프로젝트를 수정 하시겠습니까?--%>')) {
            return;
        }
        
        var form = document.forms[0];
        //개발유형
        var cnt = 0;
        form.developTyped.value = "";
        for(var i=0; i<form.chk_develop_typed.length; i++)
        {
            if(form.chk_develop_typed[i].checked)
            {
                cnt++;
                if(cnt < 2) {
                    form.developTyped.value = form.chk_develop_typed[i].value;
                } else {
                    form.developTyped.value += "|" + form.chk_develop_typed[i].value;
                }
            }
        }
        showProcessing();
        
        document.forms[0].method = "post";
        document.forms[0].command.value = "Update";
        //최종사용처, 고객처 모두 선택되게 한다. 선택 않되면 등록이 않되는 multiple 속성
        $('[name=CUSTOMEREVENTOid] option').prop('selected', true);
        $('[name=sOid] option').prop('selected', true);       
        $.ajax( {
            url : "/plm/jsp/project/ActionProductProject.jsp",
            type : "POST",
            data : $('[name=prodForm]').serialize(),
            async : false,
            success: function(data) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "01947")%><%--수정되었습니다. --%>');
                opener.location.href='/plm/jsp/project/ProjectView.jsp?oid='+data.replace(/(^\s*)|(\s*$)/gi, '')+'&popup=popup';
                self.close();
            },
            error : function(){
                alert('<%=messageService.getString("e3ps.message.ket_message", "01943")%><%--수정 실패 하였습니다. --%>');
                hideProcessing();
            }
        });
    }

    //Check Function
    function checkValidate() {
        var form = document.forms[0];
        
        var developePurpose1 = "<%=project.getMainGoalType() == null? "" : project.getMainGoalType().getCode()%>";
        var drNumber = form.drNumber.value;
        
        if(drNumber != '') {
            if(form.reviewPjt.value == ''){
                alert("검토프로젝트 번호를 선택하시기 바랍니다.");
                form.reviewPjt.focus();
                return false;
            }
        }

        if(form.manageProduct.value =='') {
            alert("관리 제품군을 지정하십시오.");
            form.manageProduct.focus();
            return false;
        }
        
        if(form.productTypeLevel2.value =='') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02580") %><%--제품구분을 선택하시기 바랍니다--%>');
            form.productTypeLevel2Name.focus();
            return false;
        }
        
        if(form.process.value =='') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "07114") %><%-- 개발단계를 선택하시기 바랍니다 --%>");
            form.process.focus();
            return false;
        }
          
        if(form.devManager.value =='') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00383") %><%--PM을 지정 하십시오 --%>");
            form.devManager.focus();
            return false;
        }
        
        if(form.projectName.value =='') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00396") %><%--Project Name를(을) 입력하시기 바랍니다--%>");
            form.projectName.focus();
            return false;
        }

        if(form.rank.value =='') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00438") %><%--Rank를 선택하시기 바랍니다--%>");
            form.rank.focus();
            return false;
        }
        
        if(form.teamType.value == '자동차 사업부' && form.model.value =='') {
            alert("대표차종을 지정하십시오.");
            form.tempmodel.focus();
            return false;
        }
        
        var isCheckSC = '<%=subContractorOid%>' != '' && form.sOid.innerHTML.indexOf('<%=subContractorOid%>') < 0;
        var isCheckCT = '<%=carTypeOid%>' != '' && '<%=carTypeOid%>' != form.model.value;
        
        
        if(isCheckSC && isCheckCT){
        	alert("프로그램의 차종, 접점고객 정보와 불일치 합니다.\n변경이 필요한 경우, 프로그램 관리자에게 문의하여\n프로그램 정보 선 수정 후 진행 하시기 바랍니다.");
            form.tempmodel.focus();
            return false;
        }else{
        	if(isCheckSC){
                alert("프로그램의 접점고객 정보와 불일치 합니다.\n변경이 필요한 경우, 프로그램 관리자에게 문의하여\n프로그램 정보 선 수정 후 진행 하시기 바랍니다.");
                form.sOid.focus();
                return false;
            }
            
            if(isCheckCT){
                alert("프로그램의 차종 정보와 불일치 합니다.\n변경이 필요한 경우, 프로그램 관리자에게 문의하여\n프로그램 정보 선 수정 후 진행 하시기 바랍니다.");
                form.tempmodel.focus();
                return false;
            }
        }
        
        if(developePurpose1 == 'DP1' && form.developentType.value =='') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00584") %><%--개발 구분을 입력하시기 바랍니다--%>");
            form.developentType.focus();
            return false;
        }
        
        if(form.obtainType.value =='') {
            alert('수주관리를 입력하시기 바랍니다.');
            form.obtainType.focus();
            return false;
        }
        
        var process = form.process.value;
        
        if($("#linkpjtoid").val() != ''){
        	
            var processCode = '';

            $.ajax({
                type: 'post',
                url : '/plm/ext/project/product/getPjtProcess.do?oid='+$("#linkpjtoid").val(),
                async : false,
                cache:false,
                success : function(result){
                    if(result.msg != ''){
                        processCode = result.msg;
                    }
                }
            });
            
            if((process == 'PC002' || process == 'PC005') && processCode != 'PC001'){
                alert("양산/Pilot 프로젝트 일 경우 연계프로젝트는 Proto만 선택가능합니다.");
                return false;
            }
        }
        
        if($("#linkpjtoid").val() == '' &&(process == 'PC002' || process == 'PC005') && processCode != 'PC001'){
            if(!confirm("연계프로젝트(Proto) 입력이 되지 않았습니다.\r\n그래도 진행하시겠습니까?")){
                return false;   
            }
        }

        return true;
    }

    //Move Function
<%--     function onMovePage(req) {
        var xmlDoc = req.responseXML;
        var showElements = xmlDoc.selectNodes("//message");
        var msg = showElements[0].getElementsByTagName("l_message")[0].text;
        if(msg == 'false') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01182") %>다시 등록하시기 바랍니다');
            return;
        }

        //alert("프로젝트를 등록완료 했습니다.");
        showElements = xmlDoc.selectNodes("//data_info");
        var l_oid = showElements[0].getElementsByTagName("l_oid");

//    opener.document.forms[0].submit();
        opener.document.location.reload();
        self.close();
    }
 --%>
    function getParamValue(name_str) {
        var paramObj = document.all.item(name_str);

        if(paramObj == null || paramObj == 'undefined') {
            return "";
        }

        var rtnParam = "";
        var paramLen = paramObj.length;
        if(paramLen) {
            for(var i = 0; i < paramLen; i++) {
                if(rtnParam.length > 0) {
                    rtnParam += "&";
                }
                rtnParam += name_str + "=" + paramObj[i].value;
            }
        }else {
            rtnParam += name_str + "=" + paramObj.value;
        }

        return rtnParam;
    }
    //ksm
    function getIframeParamValue(name_str) {
        var paramObj = iframe.document.all.item(name_str);
        var tBody = document.getElementById("iteminfoList");
        var rowId = genRowId();
        tableRows = tBody.rows.length;
        newTr = tBody.insertRow(tableRows);
        newTr.id = rowId;

        if(paramObj == null || paramObj == 'undefined') {
            return "";
        }
        var rtnParam = "";
        var paramLen = paramObj.length;
        if(paramLen) {
            dieNoSave = "";
            for(var i = 0; i < paramLen; i++) {
                //if(rtnParam.length > 0) {
                //  rtnParam += "&";
                //}

                if(name_str=="dieNo") {

//            if(paramObj[i].readOnly) {
                        //rtnParam += name_str + "=" + dieNoSave;
                        //name value set

//             newTd = newTr.insertCell(newTr.cells.length);
//             newTd.innerHTML += "<input type='hidden' name='" + name_str + "' value='"+dieNoSave+"'>";

//           }else {
                        //rtnParam += name_str + "=" + paramObj[i].value;
                        //paramobj2.value = paramObj[i].value;
//             dieNoSave = paramObj[i].value;
                        //name value set
                        newTd = newTr.insertCell(newTr.cells.length);
                        newTd.innerHTML += "<input type='hidden' name='" + name_str + "' value='"+paramObj[i].value+"'>";

//           }
                }else{
                    //rtnParam += name_str + "=" + paramObj[i].value;
                    //paramobj2.value = paramObj[i].value;;
                    //name value set
                    newTd = newTr.insertCell(newTr.cells.length);
                    newTd.innerHTML += "<input type='hidden' name='" + name_str + "' value='"+paramObj[i].value+"'>";
                }

            }
        }else {
            //alert("aa");
            //rtnParam += name_str + "=" + paramObj.value;
            //paramobj2.value = paramObj.value;
            //name value set
            //newTd = newTr.insertCell(newTr.cells.length);
            //newTd.innerHTML += "<input type='hidden' name='" + name_str + "' value='"+paramObj.value+"'>";

        }
        //alert(name_str +"  ::   "+paramObj.value);
        return rtnParam;
    }

    function getParamValue3(name_str) {

        var rowId = document.all.item("rowId");
        var rtnParam = "";

        if(rowId.length) {
            for(var j = 0; j < rowId.length; j++){
                var paramObj = document.all.item(name_str+""+rowId[j].value);
//        if(paramObj == null || paramObj == 'undefined') {
//          return "";
//        }
                if(paramObj != null && paramObj != 'undefined') {
                var paramLen = paramObj.length;
                if(paramLen) {
                    for(var k = 0; k < paramObj.length; k++){
                        if(rtnParam.length > 0) {
                                rtnParam += "&";
                            }

                        rtnParam += name_str +""+rowId[j].value+"=" + paramObj[k].value;
                    }
                }else{
                    rtnParam +="&"+ name_str +""+rowId[j].value+"=" + paramObj.value;
                }
                }
            }
        }else {
            var paramObj = document.all.item(name_str+""+rowId.value);
            if(paramObj == null || paramObj == 'undefined') {
                return "";
            }
            var paramLen = paramObj.length;
            if(paramLen) {
                for(var k = 0; k < paramObj.length; k++){
                    if(rtnParam.length > 0) {
                            rtnParam += "&";
                        }

                    rtnParam += name_str +""+rowId.value+"=" + paramObj[k].value;
                }
            }else{
                rtnParam +="&"+ name_str +""+rowId.value+"=" + paramObj.value;
            }
        }
        return rtnParam;

    }

    function projectNoSearch() {
        if(!checkField(document.forms[0].projectNo, "프로젝트NO"))  {
            if(document.forms[0].projectNo.value.length < 7) {
                alert("<%=messageService.getString("e3ps.message.ket_message", "03051") %><%--프로젝트 NO를 다시 확인 후 기입해 주십시요 \n (예:0103104)--%>");
                document.forms[0].projectNo.focus();
            } else {
                document.forms[0].action = "CreateJELProject.jsp";
                document.forms[0].tempProjectNo.value = document.forms[0].projectNo.value;
                document.forms[0].method="post";
                document.forms[0].cmd.value = "search";
                disabledAllBtn();
                showProcessing();
                document.forms[0].submit();
            }
        }
    }

    <%
    if(tempOid.length() > 0){
        TemplateProject tempObj = (TemplateProject)CommonUtil.getObject(tempOid);
        TemplateProjectData data = new TemplateProjectData(tempObj);
        String tempobj_name = data.tempName;
        String tempobj_duration = ""+data.tempDuration;
    %>

    function loadTemp()
    {
/*
        var targetTable = document.getElementById("templatetable");
        tableRows = targetTable.rows.length;
        newTr = targetTable.insertRow(tableRows);


        //Template 명
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL";
        newTd.innerText = " <%=tempobj_name%>";

        //기간
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerText = "<%=tempobj_duration%>";

        //등록일
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerText = "<%=DateUtil.getDateString(data.tempCreateDate, "D")%>";

        //최종수정일
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerText = "<%=DateUtil.getDateString(data.tempModifyDate, "D")%>";

        //삭제
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM0";
        newTd.innerHTML = "<a href=\"javascript:;\" onClick=\"javascript:onDeleteTableRow('templatetable','templateOid','<%=tempOid%>');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='templateOid' value='<%=tempOid%>'>";
*/

    }
    <%
    }%>
    //Project Template 가져오기 끝
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


    
  var targetUserId = "";

  function addRoleMember(rname) {
    
    targetUserId = rname;
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=s&invokeMethod=acceptRoleMember";
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,name, 800, 710, opts);
    
  }

  function acceptRoleMember(objArr) {
	  var rname = targetUserId;
	  if(typeof objArr == "undefined" || objArr == null) {
		  return;
	  }
	  
	  if(objArr.length == 0) {
		  return;
      }
	  
	  var displayName = document.getElementById("temp" + rname);
	  var keyName = document.getElementById(rname);
	  var nonUserArr = new Array();
	  
	  for(var i = 0; i < objArr.length; i++) {
		  infoArr = objArr[i];
		  $('[name=temp'+rname+']').val(infoArr[4]);
		  $('[name='+rname+']').val(infoArr[0]);
		  //PM일 경우 담당부서 설정
		  if(rname == "devManager"){
			  $('[name=department]').val(infoArr[2]);
	      }
	}
  }

  function deleteRoleMember(rname) {
	  document.getElementById("temp" + rname).value = "";
	  document.getElementById(rname).value = "";
  }

     //사용자 가져오기 시작 ........................................................................................
    //............................................................................................................
    function addMember(tableid, membertag) {
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
        attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
        if(typeof attacheMembers == "undefined" || attacheMembers == null) {
            return;
        }
        acceptMember(tableid, membertag, attacheMembers);
    }

    function acceptMember(tableid, membertag, objArr) {
        if(objArr.length == 0) {
            return;
        }

        /*
            subArr[0] = chkobj[i].value;//wtuser oid
            subArr[1] = chkobj[i].poid;//people oid
            subArr[2] = chkobj[i].doid;//dept oid
            subArr[3] = chkobj[i].uid;//uid
            subArr[4] = chkobj[i].sname;//name
            subArr[5] = chkobj[i].dname;//dept name
            subArr[6] = chkobj[i].duty;//duty
            subArr[7] = chkobj[i].dutycode;//duty code
            subArr[8] = chkobj[i].email;//email
        */

        var nonUserArr = new Array();
        for(var i = 0; i < objArr.length; i++) {
            infoArr = objArr[i];
            if(isDuplicateCheckBox(membertag, infoArr[0])) {
                continue;
            }

            nonUserArr[nonUserArr.length] = infoArr;
        }

        if(nonUserArr.length == 0) {
            return;
        }

        var targetTable = document.getElementById(tableid);

        for(var i = 0; i < nonUserArr.length; i++) {
            tableRows = targetTable.rows.length;

            infoArr = nonUserArr[i];
            newTr = targetTable.insertRow(tableRows);

            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerHTML = "<input type=\"checkbox\" name=\"membertag1\" value='" + infoArr[0] + "'><input type='hidden' name='"+membertag+"' value='" + infoArr[0] + "'>";

            //이름
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteL";
            newTd.innerText = infoArr[4];

            //직위
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteL";
            newTd.innerText = infoArr[6];

            //부서
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteL";
            newTd.innerText = infoArr[5];

/*      //삭제
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM0";
            newTd.innerHTML = "<a href=\"javascript:;\" onClick=\"javascript:onDeleteTableRow('"+tableid+"','"+membertag+"','" + infoArr[0] + "');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='"+membertag+"' value='" + infoArr[0] + "'>";
*/    }
    }

    function deleteRows(tableid, chk_name) {
        targetTable = document.getElementById(tableid);
        var chkTag = document.all.item(chk_name);

        if(chkTag == "undefined" ||  chkTag == null){
            return;
        }else{
            var chkLen = chkTag.length;
            if(chkLen) {
                if(chkLen > 1) {
                    for(i=chkLen; i>0; i--) {
                        if(chkTag[i-1].checked == true) {
                            targetTable.deleteRow(i);
                        }
                    }
                }else{
                    if(chkTag.checked == true || chkTag[0].checked == true) {
                        targetTable.deleteRow(1);
                    }
                    return;
                }
            }
        }
    }

     //사용자 가져오기 끝 ........................................................................................

     //부서 가져오기 시작 ........................................................................................
    //............................................................................................................
    function addDepartment() {
        var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=s";
        attacheDept = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=420px; dialogHeight:465px; center:yes");
        if(typeof attacheDept == "undefined" || attacheDept == null) {
            return;
        }

        if ( typeof(attacheDept) != "object" ) {
            var deptSplit = attacheDept.split(",");
            for ( var i=0; i<deptSplit.length-1; i++ ) {
                var param = "command=deptInfo&deptOid=" + deptSplit[i];
                var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
                callServer(url, acceptDept);
            }
        }
        else {
            var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
            var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
            callServer(url, acceptDept);
        }
    }

    function acceptDept(req) {
        var xmlDoc = req.responseXML;
        var showElements = xmlDoc.selectNodes("//message");
        var msg = showElements[0].getElementsByTagName("l_message")[0].text;
        if(msg != 'true') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
            return;
        }

        showElements = xmlDoc.selectNodes("//data_info");
        var l_oid = showElements[0].getElementsByTagName("l_oid");
        var l_name = showElements[0].getElementsByTagName("l_name");
        var l_code = showElements[0].getElementsByTagName("l_code");

        document.forms[0].department.value = l_oid[0].text;
        document.forms[0].tempdepartment.value = l_name[0].text;

    }

    //부서 가져오기 끝 ........................................................................................
    //............................................................................................................
    function isDuplicateCheckBox(chk_name, chk_value) {
        var chkTag = document.all.item(chk_name);//eval("document.forms[0]." + membertag);
        if(chkTag == null || chkTag == 'undefined') {
            return false;
        }

        var chkLen = chkTag.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                if(chkTag[i].value == chk_value) {
                    return true;
                }
            }
        }else {
            if(chkTag.value == chk_value) {
                return true;
            }
        }

        return false;
    }

    function onDeleteTableRow(tableid, chk_name, chk_value) {
        targetTable = document.getElementById(tableid);
        var chkTag = document.all.item(chk_name);

        var chkLen = chkTag.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                if(chkTag[i].value == chk_value) {
                    targetTable.deleteRow(i+1);
                    return;
                }
            }
        }else {
            if(chkTag.value == chk_value) {
                targetTable.deleteRow(1);
                return;
            }
        }
    }

    function clearDate(str) {
        var tartxt = document.getElementById(str);
        tartxt.value = "";
    }

    function onKeyPress() {

        if (window.event) {
            if (window.event.keyCode == 13) erpProjectNoSearch();
        } else return;

    }
    //document.onkeypress = onKeyPress;

    function doSearchPJT(oTable_id) {
        part_table_obj = document.getElementById(oTable_id);
        openWindow("/plm/jsp/project/ProjectSelectPage.jsp?isOpen=false&isModal=false&mode=one&invokeMethod=ProjectInfo", "SelectProject", 1024, 768);
    }

    function ProjectInfo(mpArr){
        var arr = mpArr[0];
        projectoid = arr[0];          // Project OID
        projectno = arr[1];            // 프로젝트  NO
        projectname = arr[2];          // 프로세스
        projectModel = arr[10];          // 모텔 차종
        projectLevel = arr[11];          // 난이도
        projectCustomer = arr[12];        // 발주처
        projectMakecompany = arr[13];      // 생산조직
        projectProduct = arr[14];        // 제품군
        document.forms[0].dsProjectName.value = projectname
        document.forms[0].dsProjectOid.value = projectoid

        //모델/차종
        if( projectModel.length >0){
            for( var i = 0 ; document.forms[0].modelcode.options.length ; i ++){
                if(document.forms[0].modelcode.options[i].value == projectModel){
                    document.forms[0].modelcode.options[i].selected = true;
                    document.forms[0].modelcode.disabled =true;
                    break;
                }
            }
        }
        //난이도
        if( projectLevel.length >0){
            for( var i = 0 ; document.forms[0].levelcode.options.length ; i ++){
                if(document.forms[0].levelcode.options[i].value == projectLevel){
                    document.forms[0].levelcode.options[i].selected = true;
                    document.forms[0].levelcode.disabled =true;
                    break;
                }
            }
        }
        //제품군
        if( projectProduct.length >0){
            for( var i = 0 ; document.forms[0].productcode.options.length ; i ++){
                if(document.forms[0].productcode.options[i].value == projectProduct){
                    document.forms[0].productcode.options[i].selected = true;
                    document.forms[0].productcode.disabled =true;
                    break;
                }
            }
        }
        //발주처
        if( projectCustomer.length >0){
            for( var i = 0 ; document.forms[0].customercode.options.length ; i ++){
                if(document.forms[0].customercode.options[i].value == projectCustomer){
                    document.forms[0].customercode.options[i].selected = true;
                    document.forms[0].customercode.disabled =true;
                    break;
                }
            }
        }
        //생산조직
        if( projectMakecompany.length >0){
            for( var i = 0 ; document.forms[0].makecompanycode.options.length ; i ++){
                if(document.forms[0].makecompanycode.options[i].value == projectMakecompany){
                    document.forms[0].makecompanycode.options[i].selected = true;
                    document.forms[0].makecompanycode.disabled =true;
                    break;
                }
            }
        }
    }

    function codeDelete(strvalue, strdisplay) {
        document.forms[0].dsProjectName.value = "";
        document.forms[0].dsProjectOid.value = "";
        document.forms[0].modelcode.disabled =false;
        document.forms[0].levelcode.disabled =false;
        document.forms[0].productcode.disabled =false;
        document.forms[0].customercode.disabled =false;
        document.forms[0].makecompanycode.disabled =false;
    }

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //제품 가져오기 시작

    function onModelType() {

        var url = "/plm/jsp/project/projectType/SelectOEMMain.jsp?oemtype=CARTYPE&codetype=CUSTOMEREVENT&mode=s";
        attache = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=700px; dialogHeight:460px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }

//    addDependence(attache);

    }
/*
    function addDependence(arr)
    {
        var targetTable;
        targetTable = document.getElementById("dependencetable");

        for(var i = 0; i < arr.length; i++) {
            subArr = arr[i];

            tableRows = targetTable.rows.length;
            newTr = targetTable.insertRow(tableRows);

            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteL";
            newTd.innerHTML = "<input type=hidden name=dependence value='"+subArr[0]+"'>" + subArr[1];

            //삭제
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM0";
            newTd.innerHTML = "<a href=\"javascript:;\" onClick=\"javascript:deleteDependence('dependence', '"+subArr[0]+"');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a>";
        }

    }

    function deleteDependence(chk_name, chk_value) {
        var chkTag = document.all.item(chk_name);

        var chkLen = chkTag.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                if(chkTag[i].value == chk_value) {
                    dependencetable.deleteRow(i);
                    return;
                }
            }
        }else {
            if(chkTag.value == chk_value) {
                dependencetable.deleteRow(0);
                return;
            }
        }
    }
*/
    function addProduct()
    {
        var targetTable = document.getElementById("producttable");
        tableRows = targetTable.rows.length;
        newTr = targetTable.insertRow(tableRows);

        //제품명
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL";
        newTd.innerHTML = "<input type=text name=\"projectTypeName\" size=7>";

        //차종
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<a href=\"javascript:;\" onClick=\"javascript:onModelType();\"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></a>";

        //적용부위
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type=text name=\"projectTypeName\" size=7>";

        //U/S
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerText = "U/S";

        //판매가
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL";
        newTd.innerHTML = "<input type=text name=\"projectTypeName\" size=7>";

        //원가
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type=text name=\"projectTypeName\" size=7>";

        //수익률
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type=text name=\"projectTypeName\" size=7>";

        //고객예상가
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type=text name=\"projectTypeName\" size=7>";

        //목표수익률
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type=text name=\"projectTypeName\" size=7>";

        //목표투자비
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type=text name=\"projectTypeName\" size=7>";

        //삭제
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM0";
        newTd.innerHTML = "<a href=\"javascript:;\" onClick=\"javascript:onDeleteTableRow('templatetable','templateOid','<%//=tempOid%>');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a>";
    }

    //제품 가져오기 끝
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //item 가져오기 시작

    function addItem(typeValue)
    {
        iframe.addItem(typeValue);
    }

    function deleteItem() {
        iframe.deleteItem();
    }

    //item 가져오기 끝
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

function checkAll2(obj, selectObj) {
/*  form = document.forms[0];
    if(form.oid) {
        var chkLen = form.oid.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                form.oid[i].checked = form.chkAll.checked;
            }
        }else{
            form.oid.checked = form.chkAll.checked;
        }
    }
*/
    if(selectObj) {
        var chkLen = selectObj.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                selectObj[i].checked = obj.checked;
            }
        }else{
            selectObj.checked = obj.checked;
        }
    }

}

    //project
    function onProject() {

        var url = "/plm/jsp/project/ProjectSelectPopUp.jsp?mode=multi&type=2&pjtType=2";
        attache = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=750px; dialogHeight:620px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }

        acceptProject(attache);

    }

    function acceptProject(objArr) {
        if(objArr.length == 0) {
            return;
        }

        var targetTable = document.getElementById("projecttable");

        var trArr;
        for(var i = 0; i < objArr.length; i++) {
            tableRows = targetTable.rows.length;

            trArr = objArr[i];

            var poidCheck = "true";
            var projectoidVal = document.all.item("projectOid");
            if(trArr[0] == "<%=oid%>") {
                poidCheck = "false";
            }else if(projectoidVal) {
                if(projectoidVal.length) {
                    for(var j = 0; j < projectoidVal.length; j++) {
                        if(projectoidVal[j].value == trArr[0]) poidCheck = "false";
                    }
                }else {
                    if(projectoidVal.value == trArr[0]) poidCheck = "false";
                }
            }

            if(poidCheck == "true") {
                newTr = targetTable.insertRow(tableRows);

                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                newTd.innerHTML = "<input name=\"projectoid\" type=\"checkbox\" value='" + trArr[0] + "'/><input name=\"projectOid\" type=\"hidden\" value='" + trArr[0] + "'/>";

                //개발구분
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                if( trArr[14]=='null'|| trArr[14]==''){
                    newTd.innerText = " ";
                }else{
                    newTd.innerText = trArr[14];
                }

                //Project No
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                newTd.innerText = trArr[2];

                //Part No
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                if( trArr[10]=='null'|| trArr[10]==''){
                    newTd.innerText = " ";
                }else{
                    newTd.innerText = trArr[10];
                }

                //제품명
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                if( trArr[1]=='null'|| trArr[1]==''){
                    newTd.innerText = " ";
                }else{
                    newTd.innerText = trArr[1];
                }

                //고객사
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                newTd.title= trArr[11];
                if( trArr[11]=='null'|| trArr[11]==''){
                    newTd.innerText = " ";
                }else{

                    newTd.innerHTML = "<div style='width:60;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;'><nobr>"+trArr[11]+"</nobr></div>"


                }

                //대표차종
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                if( trArr[16]=='null'|| trArr[16]==''){
                    newTd.innerText = " ";
                }else{
                    newTd.innerText = trArr[16];
                }

                //개발담당
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                if( trArr[12]=='null' || trArr[12]==''){
                    newTd.innerText = " ";
                }else{
                    newTd.innerText = trArr[12];
                }

                //개발시작일
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                newTd.innerText = trArr[15];
            }
        }
    }

    function deleteProject() {

        var form = document.forms[0];

        if(form.projectoid == "undefined" ||  form.projectoid == null){
            return;
        }else{
            index = form.projectoid.length;

            if(index > 1) {
                for(i=index; i>0; i--) {
                    if(form.projectoid[i-1].checked == true) {
                        projecttable.deleteRow(i);
                    }
                }
            }else{
                if(form.projectoid.checked == true || form.projectoid[0].checked == true) {
                    projecttable.deleteRow(1);
                }
                return;
            }
        }
    }

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //차종 가져오기 시작

    function onModel() {

    	SearchUtil.selectCarType('','','addModel');
    	
/*         var url = "/plm/jsp/project/projectType/SelectOEMMain.jsp?oemtype=CARTYPE&codetype=CUSTOMEREVENT&mode=s";
        attache = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=700px; dialogHeight:460px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }

        addModel(rname, attache); */

    }

    function addModel(objArr) {
        var rname = 'model';
        if(objArr.length == 0) {
            return;
        }
        
        $('[name=model]').val(objArr[0][0])//id
        $('[name=tempModel]').val(objArr[0][1])//name

/*         var displayName = document.getElementById("temp" + rname);
        var keyName = document.getElementById(rname);

        for(var i = 0; i < objArr.length; i++) {
            infoArr = objArr[i];
            displayName.value = infoArr[1];
            keyName.value = infoArr[0];

        } */
    }

    //차종 가져오기 끝
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //조립처 가져오기 시작

    function onCustomer() {

        var url = "/plm/jsp/common/code/SelectNumberCode.jsp?mode=multi&codetype=CUSTOMEREVENT";
        attache = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=840px; dialogHeight:460px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }

        addCustomer(attache);

    }

    function addCustomer(arr)
    {
        var targetTable;
        targetTable = document.getElementById("customertable");

        for(var i = 0; i < arr.length; i++) {
            subArr = arr[i];

            tableRows = targetTable.rows.length;
            newTr = targetTable.insertRow(tableRows);

            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteL";
            newTd.innerHTML = "<input type=hidden name=customer value='"+subArr[0]+"'>" + subArr[1];

            //삭제
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM0";
            newTd.innerHTML = "<a href=\"javascript:;\" onClick=\"javascript:deleteCustomer('customer', '"+subArr[0]+"');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a>";
        }

    }

    function deleteCustomer(chk_name, chk_value) {
        var chkTag = document.all.item(chk_name);

        var chkLen = chkTag.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                if(chkTag[i].value == chk_value) {
                    customertable.deleteRow(i);
                    return;
                }
            }
        }else {
            if(chkTag.value == chk_value) {
                customertable.deleteRow(0);
                return;
            }
        }
    }

    //조립처 가져오기 끝
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    // 멀티 속성 가져오기
    function addProcessM(type, depth) {
        var form = document.forms[0];
        var mode = "multi";
        var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth="+depth+"&selectedDepth="+depth+"&codetype="+type+"&command=select&mode="+mode;

        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");
        if(typeof returnValue == "undefined" || returnValue == null) {
            return;
        }
        if(type == "SUBCONTRACTOR"){
            acceptProcessM2(returnValue, type);
        }else{
            acceptProcessM(returnValue, type);
        }
    }
    //etc
    function acceptProcessM(arrObj, type){
        var subArr;
        var form = document.forms[0];


        var targetTable = document.getElementById(type);
        var subArr;
        //var chkTag = document.all.item("processoid");
        var form = document.forms[0];

        //targetTable.deleteRow(1);

            for(var i = 0; i < arrObj.length; i++) {
                subArr = arrObj[i];
                if(isDuplicateCheckBox(type+'Oid',subArr[0])) {
                    continue;
                }
                tableRows = targetTable.rows.length;
                newTr = targetTable.insertRow(tableRows);

                //Code | Name
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteL";
                newTd.innerText = subArr[2];

                //삭제
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM0";
                newTd.innerHTML = "<a href=\"javascript:;\" onClick=\"javascript:onDeleteTableRow('"+type+"', '"+type+"Oid', '" + subArr[0] + "');\"><p><img src=\"/plm/portal/images/icon_delete.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='"+type+"Oid' value='" + subArr[0] + "'>";
            }

    }
    //SUBCONTRACTOR
    function acceptProcessM2(arrObj, type){
        var subArr;
        var form = document.forms[0];


        var targetTable = document.getElementById(type);
        var subArr;
        //var chkTag = document.all.item("processoid");
        var form = document.forms[0];

        //targetTable.deleteRow(1);

            for(var i = 0; i < arrObj.length; i++) {
                subArr = arrObj[i];
                if(isDuplicateCheckBox('uOid', subArr[0])) {
                    continue;
                }
                //alert(subArr[0]);
                tableRows = targetTable.rows.length;
                newTr = targetTable.insertRow(tableRows);

                //checkbox
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                newTd.width = 20;
                newTd.valign = "top";
                newTd.innerHTML = "<input type=\"checkbox\" name=\"uOid\" value=\"" + subArr[0] + "\"></input><input type='hidden' name='sOid' value='" + subArr[0] + "'>";

                //Code | Name
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteL";
                newTd.width = 110;
                newTd.height = "100%";
                newTd.valign = "top";
                newTd.innerText = subArr[2];

                //납입처 Table
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM0";
                newTd.innerHTML = "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" id=\"" + subArr[0] + "\"></table>";
            }

    }
    //고객처
    function addProcessM2(type, depth) {
        var form = document.forms[0];
        var mode = "multi";
        var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth="+depth+"&selectedDepth="+depth+"&codetype="+type+"&command=select&mode="+mode;

        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:500px; center:yes");
        if(typeof returnValue == "undefined" || returnValue == null) {
            return;
        }

        var tableName = "";

        len = form.uOid.length;
        if(len) {
            for(var i = 0; i < len;i++) {
                if(form.uOid[i].checked == true) {
                    tableName = form.uOid[i].value;
                    //alert(tableName);
                }
            }
        }else{
            tableName = form.uOid.value;
        }

        acceptProcessM3(returnValue, tableName);
    }
    //납입처
    function acceptProcessM3(arrObj, type){
        var subArr;
        var form = document.forms[0];


        var targetTable = document.getElementById(type);
        var subArr;
        //var chkTag = document.all.item("processoid");
        var form = document.forms[0];

        //targetTable.deleteRow(1);

            for(var i = 0; i < arrObj.length; i++) {
                subArr = arrObj[i];
                if(isDuplicateCheckBox(type+'Oid', subArr[0])) {
                    continue;
                }
                tableRows = targetTable.rows.length;
                newTr = targetTable.insertRow(tableRows);

                //Code | Name
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteL";
                newTd.innerText = subArr[2];
                //alert(type + "@" + subArr[0]);
                //삭제
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM0";
                newTd.width = "20";
                newTd.innerHTML = "<input type=\"hidden\" name=\"nOid\" value=\"" + type + "@" + subArr[0] + "\"><a href=\"javascript:;\" onClick=\"javascript:onDeleteTableRow0('"+type+"', '"+type+"Oid', '" + subArr[0] + "');\"><p><img src=\"/plm/portal/images/icon_delete.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='"+type+"Oid' value='" + subArr[0] + "'>";
            }

    }
    // 개발 검토 납입처 관련 삭제
    function onDeleteTableRow0(tableid, chk_name, chk_value) {
        var targetTable = document.getElementById(tableid);
        var chkTag = document.all.item(chk_name);

        var chkLen = chkTag.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                //alert(chkTag[i].value +" : "+chk_value);
                if(chkTag[i].value == chk_value) {
                    targetTable.deleteRow(i);
                    return;
                }
            }
        }else {
            if(chkTag.value == chk_value) {
                targetTable.deleteRow(0);
                return;
            }
        }
    }
    // 개발 검토 납입처 관련 삭제
    function onDelete(){
        if(!checkedCheck()){
            alert("<%=messageService.getString("e3ps.message.ket_message", "01698") %><%--삭제 할 고객사를 선택 하십시오--%> ");
            return;
        }

        form = document.forms[0];

        len = form.uOid.length;
        //alert(len);
        if(len) {
            for(var i = len - 1; i >= 0; i--) {
                if(form.uOid[i].checked == true) {
                    onDeleteTableRow('SUBCONTRACTOR', 'uOid', form.uOid[i].value);
                }
            }
        }else{
            onDeleteTableRow('SUBCONTRACTOR', 'uOid', form.uOid.value);
        }
    }
    // 개발 검토 납입처 관련 추가
    function onAdd(){
        if(!checkedCheck2()){
            return;
        }

        addProcessM2('SAPSUBCONTRACTOR', '0');
    }

    function checkedCheck() {
        form = document.forms[0];
        if(form.uOid == null) {
            return false;
        }

        len = form.uOid.length;
        if(len) {
            for(var i = 0; i < len;i++) {
                if(form.uOid[i].checked == true) {
                    return true;
                }
            }
        }else {
            if(form.uOid.checked == true) {
                return true;
            }
        }

        return false;

    }

    function checkedCheck2() {

        form = document.forms[0];
        if(form.uOid == null) {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00843") %><%--고객사를 등록 하십시오--%> ");
            return false;
        }

        len = form.uOid.length;
        if(len) {
            cnt = 0;
            for(var i = 0; i < len;i++) {
                if(form.uOid[i].checked == true) {
                    cnt++;
                }
            }
            if(cnt == 1){
                return true;
            }else{
                alert('<%=messageService.getString("e3ps.message.ket_message", "03137") %><%--하나의 고객사를 선택 하십시오--%>');
                return false;
            }
        }else {
            if(form.uOid.checked == true) {
                return true;
            }
        }
        alert('<%=messageService.getString("e3ps.message.ket_message", "01170") %><%--납입처를 등록 할 고객사를 선택 하십시오--%>');

        return false;

    }

///////////////////////////////////////////
///////////자동차일정 선택////////////////
///////////////////////////////////////////
        function selectProgram( rowId ){

        var param ="?sid="+rowId;
        //자동차일정 oid

        var optOid =  getParamValue("optOid"+rowId);
        if(optOid.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += optOid;
        }
        var carName =  getParamValue("carName"+rowId);
        if(carName.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += carName;
        }
        //y1
        var y1 =  getParamValue("y1"+rowId);
        if(y1.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y1;
        }
        //y2
        var y2 =  getParamValue("y2"+rowId);
        if(y2.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y2;
        }
        //y3
        var y3 =  getParamValue("y3"+rowId);
        if(y3.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y3;
        }
        //y4
        var y4 =  getParamValue("y4"+rowId);
        if(y4.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y4;
        }
        //y5
        var y5 =  getParamValue("y5"+rowId);
        if(y5.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y5;
        }
        //y6
        var y6 =  getParamValue("y6"+rowId);
        if(y6.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y6;
        }
        //y7
        var y7 =  getParamValue("y7"+rowId);
        if(y7.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y7;
        }
        //y8
        var y8 =  getParamValue("y8"+rowId);
        if(y8.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y8;
        }
        //y9
        var y9 =  getParamValue("y9"+rowId);
        if(y9.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y9;
        }
        //y10
        var y10 =  getParamValue("y10"+rowId);
        if(y10.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y10;
        }

        //usage
        var usage =  getParamValue("usage"+rowId);
        if(usage.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += usage;
        }

        //optionRate
        var optionRate =  getParamValue("optionRate"+rowId);
        if(optionRate.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += optionRate;
        }

        var pOid =  getParamValue("pOid"+rowId);
        if(pOid.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += pOid;
        }

        var url = "/plm/jsp/project/SelectProgram.jsp"+param;
        attache = window.showModalDialog(url,window,"help=no; resizable=no;=yes; status=no; scroll=no; dialogWidth=720px; dialogHeight:500px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }

        addProgram(attache, rowId );

        var paramObj = document.all.item("pOid"+rowId);
        if(paramObj) {
            document.forms[0].deletePOid.value = document.forms[0].deletePOid.value + "@" + paramObj.value;
            //paramObj.value = "";
        }
    }

    function addProgram(attache, rowId)
    {
        if(attache.length == 0) {
            return;
        }

        var productInfo = document.getElementById("productInfo");
//    productInfo.deleteRow(tablerows);
//    tableRows = productInfo.rows.length;

        var carName = document.getElementById("carName"+rowId);
        carName.title = attache[0][0];
        carName.innerHTML  = attache[0][0]+"<input type='hidden' name='carName' id='carName"+rowId+"' value='"+attache[0][0]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y1T' id='y1T"+rowId+"' value='"+attache[0][1]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y2T' id='y2T"+rowId+"' value='"+attache[0][2]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y3T' id='y3T"+rowId+"' value='"+attache[0][3]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y4T' id='y4T"+rowId+"' value='"+attache[0][4]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y5T' id='y5T"+rowId+"' value='"+attache[0][5]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y6T' id='y6T"+rowId+"' value='"+attache[0][6]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y7T' id='y7T"+rowId+"' value='"+attache[0][7]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y8T' id='y8T"+rowId+"' value='"+attache[0][8]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y9T' id='y9T"+rowId+"' value='"+attache[0][9]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y10T' id='y10T"+rowId+"' value='"+attache[0][10]+"'>";

        var usageT = document.getElementById("usageT"+rowId);
        usageT.innerHTML  = attache[0][12]+"<input type='hidden' name='usageT' value='"+attache[0][12]+"'>";
        for(var i = 0; i < attache.length-1; i++) {
            infoArr = attache[i+1];
            usageT.innerHTML  += "<input type='hidden' name='optOid"+rowId+"' id='optOid"+rowId+"' value='"+infoArr[0]+"'>";
            //<input type='hidden' name='pOid"+rowId+"' id='pOid"+rowId+"' value=''>";
            usageT.innerHTML  += "<input type='hidden' name='y1"+rowId+"' id='y1"+rowId+"' value='"+infoArr[2]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y2"+rowId+"' id='y2"+rowId+"' value='"+infoArr[3]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y3"+rowId+"' id='y3"+rowId+"' value='"+infoArr[4]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y4"+rowId+"' id='y4"+rowId+"' value='"+infoArr[5]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y5"+rowId+"' id='y5"+rowId+"' value='"+infoArr[6]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y6"+rowId+"' id='y6"+rowId+"' value='"+infoArr[7]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y7"+rowId+"' id='y7"+rowId+"' value='"+infoArr[8]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y8"+rowId+"' id='y8"+rowId+"' value='"+infoArr[9]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y9"+rowId+"' id='y9"+rowId+"' value='"+infoArr[10]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y10"+rowId+"' id='y10"+rowId+"' value='"+infoArr[11]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='sum' id='sum"+rowId+"' value='"+infoArr[12]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='usage"+rowId+"' id='usage"+rowId+"' value='"+infoArr[13]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='optionRate"+rowId+"' id='optionRate"+rowId+"' value='"+infoArr[14]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='count"+rowId+"' id='count"+rowId+"' value='"+ i +"'>";

        }
    }

    function selectProduct(){
        var url = "/plm/jsp/project/SelectProductInfo.jsp?mode=multi&drKeyOid="+document.forms[0].drKeyOid.value;
        attache = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:700px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }
        addProduct(attache);
    }

    function addProduct(attache) {
        if(attache.length == 0) {
            return;
        }

        for ( var i = 0 ; i < attache.length; i++ ) {
            subarr = attache[i]
            var tBody = document.getElementById("productInfo");

            var rowId = genRowId();

            tableRows = tBody.rows.length;
            newTr = tBody.insertRow(tableRows);

            //제품번호
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "120";
            newTd.innerHTML = "<input type='text' name='pNum' class='txt_fieldRO' readonly style='width:70' id='pNum"+rowId+"' value='"+subarr[1]+"'>";
            newTd.innerHTML += "<input type='hidden' name='reviewProjectNo' id='reviewProjectNo"+rowId+"' value='"+subarr[14]+"'>";
            newTd.innerHTML += "<input type='hidden' name='reviewSeqNo' id='reviewSeqNo"+rowId+"' value='"+subarr[11]+"'>";
            newTd.innerHTML += "&nbsp;<a href=\"javascript:selectPartNumber('pNum', " + rowId + ");\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>";
            newTd.innerHTML += "&nbsp;<a href=\"javascript:clearPartNumber('pNum', " + rowId + ");\"><img src=\"/plm/portal/images/icon_delete.gif\" border=\"0\"></a>";

            //제품명
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "150";
            newTd.innerHTML = "<input type='hidden' name='pOid"+rowId+"'  id='pOid"+rowId+"' value='"+subarr[0]+"'>";
            newTd.innerHTML += "<input type='text' name='pName' class='txt_fieldRO' readonly style='width:145' id='pName"+rowId+"' value='"+subarr[2]+"'>";

            //차종
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "150";
            newTd.innerHTML = "<div style='width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;'><nobr>";
            newTd.innerHTML += "<span name='carName' id='carName"+rowId+"' value='"+subarr[12]+"'>"+subarr[12]+"</span></nobr>&nbsp;";
            newTd.innerHTML += "<a href='javascript:selectProgram("+rowId+");'><img src='/plm/portal/images/icon_5.png' border='0'></a></div>";

            newTd.innerHTML  += "<input type='hidden' name='y1T' value=''>";
            newTd.innerHTML  += "<input type='hidden' name='y2T' value=''>";
            newTd.innerHTML  += "<input type='hidden' name='y3T' value=''>";
            newTd.innerHTML  += "<input type='hidden' name='y4T' value=''>";
            newTd.innerHTML  += "<input type='hidden' name='y5T' value=''>";
            newTd.innerHTML  += "<input type='hidden' name='y6T' value=''>";
            newTd.innerHTML  += "<input type='hidden' name='y7T' value=''>";
            newTd.innerHTML  += "<input type='hidden' name='y8T' value=''>";
            newTd.innerHTML  += "<input type='hidden' name='y9T' value=''>";
            newTd.innerHTML  += "<input type='hidden' name='y10T' value=''><input type='hidden' name='carName' id='carName"+rowId+"' value=''>";

            //적용부위
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "70";
            newTd.align = "center";
            newTd.innerHTML = "<input type='text' name='areas' class='txt_field'  style='width:95%' id='areas"+rowId+"' value='"+subarr[3]+"'>";

            //usage
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "40";
            newTd.innerHTML = "<span name='usageT' id='usageT"+rowId+"' style='width:75%' value='"+subarr[4]+"'>"+subarr[4]+"<input type='hidden' name='usageT' value='"+subarr[4]+"'></span>&nbsp;";

            //고객예상가
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "70";
            newTd.innerHTML = "<input type='text' name='price' class='txt_fieldR'  style='width:95%' id='price"+rowId+"' value='"+subarr[5]+"' onKeyUp=\"javascript:if(!checkNumber2(this.value)) { alert('처음  숫자가 0이 아닌, 숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";

            //판매가
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "70";
            newTd.innerHTML = "<input type='text' name='cost' class='txt_fieldR'  style='width:95%' id='cost"+rowId+"' value='"+subarr[6]+"' onKeyUp=\"javascript:if(!checkNumber2(this.value)) { alert('처음  숫자가 0이 아닌, 숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";

            //수익률
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "70";
            newTd.innerHTML = "<input type='text' name='rate' class='txt_fieldR'  style='width:95%' id='rate"+rowId+"' value='"+subarr[7]+"' onKeyUp=\"javascript:if(!checkNumber2(this.value)) { alert('처음  숫자가 0이 아닌, 숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";

            //삭제
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM0";
            newTd.width = "20";
            newTd.innerHTML = "<a href=\"javascript:;\" onclick=\"javascript:onDeleteTableRow2('productInfo','rowId','" + rowId + "');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a><input type='hidden' name='rowId' value='"+rowId+"'>";
        }

        // partNo에 값입력
        partNoDuplicateCheck();
    }

    function createProduct(oid){

            pchangeCreate(oid);

            var tBody = document.getElementById("productInfo");

            var rowId = genRowId();

            tableRows = tBody.rows.length;
            newTr = tBody.insertRow(tableRows);
            newTr.id = rowId;

            //제품번호
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "120";
            newTd.innerHTML = "<input type='text' name='pNum' class='txt_fieldRO' readonly style='width:70' id='pNum"+rowId+"'>";
            newTd.innerHTML += "<input type='hidden' name='reviewProjectNo' id='reviewProjectNo"+rowId+"' value=''>";
            newTd.innerHTML += "<input type='hidden' name='reviewSeqNo' id='reviewSeqNo"+rowId+"' value=''>";
            newTd.innerHTML += "&nbsp;<a href=\"javascript:selectPartNumber('pNum', " + rowId + ");\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>";
            newTd.innerHTML += "&nbsp;<a href=\"javascript:clearPartNumber('pNum', " + rowId + ");\"><img src=\"/plm/portal/images/icon_delete.gif\" border=\"0\"></a>";

            //제품명
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "150";
            newTd.innerHTML = "<input type='text' name='pName' class='txt_fieldRO' readonly style='width:145' id='pName"+rowId+"'>";

            //차종
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "150";
            newTd.innerHTML = "<div style='width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;'><nobr><span name='carName' id='carName"+rowId+"'></span></nobr>&nbsp;<a href='javascript:selectProgram("+rowId+");'><img src='/plm/portal/images/icon_5.png' border='0'></a></div>";

            //적용부위
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "70";
            newTd.align = "center";
            newTd.innerHTML = "<input type='text' name='areas' class='txt_field'  style='width:95%' id='areas"+rowId+"'>";

            //usage
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "40";
            newTd.innerHTML = "<span name='usageT' id='usageT"+rowId+"' style='width:75%'></span>&nbsp;";

            //고객예상가
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "70";
            newTd.innerHTML = "<input type='text' name='price' class='txt_fieldR'  style='width:95%' id='price"+rowId+"' onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";

            //판매가
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "70";
            newTd.innerHTML = "<input type='text' name='cost' class='txt_fieldR'  style='width:95%' id='cost"+rowId+"' onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";

            //수익률
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "70";
            newTd.innerHTML = "<input type='text' name='rate' class='txt_fieldR'  style='width:95%' id='rate"+rowId+"' onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";
//      newTd.innerHTML += "<input type='hidden' name='year1"+rowId+"' id='rate"+rowId+"'>";

            //삭제
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM0";
            newTd.width = "20";
            newTd.innerHTML = "<a href=\"javascript:;\" onclick=\"javascript:onDeleteTableRow2('productInfo','rowId','" + rowId + "');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a><input type='hidden' name='rowId' value='"+rowId+"'>";
    }

    function onDeleteTableRow2(tableid, chk_name, chk_value) {
        targetTable = document.getElementById(tableid);
        var chkTag = document.all.item(chk_name);

        var chkLen = chkTag.length;
        if ( chkLen ) {
            for ( var i = 0; i < chkLen; i++ ) {
                if ( chkTag[i].value == chk_value ) {
                    pOidObj = document.all.item("pOid"+chk_value);
                    if ( pOidObj ) {
                        if ( pOidObj.value != "" )
                            document.forms[0].deletePOid.value = document.forms[0].deletePOid.value + "@" + pOidObj.value;
                    }
                    targetTable.deleteRow(i+2);

                    // partNo에 값입력
                    partNoDuplicateCheck();

                    return;
                }
            }
        }else {
            if ( chkTag.value == chk_value ) {
                pOidObj = document.all.item("pOid"+chk_value);
                if ( pOidObj ) {
                    if ( pOidObj.value != "" )
                        document.forms[0].deletePOid.value = document.forms[0].deletePOid.value + "@" + pOidObj.value;
                }
                targetTable.deleteRow(2);

                // partNo에 값입력
                partNoDuplicateCheck();

                return;
            }
        }
    }

    //==  [Start] 부품 검색(PartNo)  == //
    // mode = m / s
    // pType=A(전체)/P(제품)/D(다이)/M(금형)
    function selectPartNumber(targetId, arrIndex) {

    	_callBackFuncTargetId = targetId;
        _callBackFuncArrIndex = arrIndex;
        showProcessing();
        SearchUtil.selectOnePart("callBackFuncPartPopup", "pType=P");
    }
    
    var _callBackFuncTargetId;
    var _callBackFuncArrIndex;
    function callBackFuncPartPopup(selectedPartAry){
        if ( typeof selectedPartAry != "undefined" && selectedPartAry != null) {
            setPartNo(_callBackFuncTargetId, _callBackFuncArrIndex, selectedPartAry);
        }
    }

    function setPartNo(targetId, arrIndex, parts) {
        if ( parts != undefined && parts.length > 0 ) {
            var partNo = "";
            var partName = "";
            for (var i = 0; i < parts.length; ++i) {
                partNo = parts[i][1];
                partName = parts[i][2];
            }
            document.getElementById(targetId + arrIndex).value = partNo;
            document.getElementById("pName" + arrIndex).value = partName;

            // partNo에 값입력
            partNoDuplicateCheck();
        }
    }

    function clearPartNumber(targetId, arrIndex) {
        document.getElementById(targetId + arrIndex).value = "";
        document.getElementById("pName" + arrIndex).value = "";
    }

    function partNoDuplicateCheck() {
        var tBody = document.getElementById("productInfo");
        var tableRows = tBody.rows.length;

        // Table TR length
        var tableTrRows = tBody.childNodes.length;

        var newPartNo = new Array();
        for ( var i=2; i<tableRows; i++ ) {
            newPartNo.push(tBody.childNodes[0].childNodes[i].childNodes[0].childNodes[0].value);
        }

        newPartNo = newPartNo.sort();
        var partNoDuplicate = new Array();
        for ( var i=0; i<newPartNo.length; i++ ) {
            var checkDu = 0;
            for ( j=0; j<newPartNo.length; j++ ) {
                if ( newPartNo[i] != newPartNo[j] ) {
                    continue;
                }
                else {
                    checkDu++;
                    if ( checkDu > 1 ) {
                        newPartNo.splice(j,1);
                    }
                }
            }
        }

        document.forms[0].partNo.value = newPartNo.join(", ");

        if ( document.forms[0].partNo.value == "" ) {
            document.forms[0].partNo.value = "미입력";
        }
    }
    //==  [Start] 부품 검색(PartNo)  == //

    function sleep(num){
      var now = new Date();
      var stop = now.getTime() + num;
      while(true){
        now = new Date();
        if(now.getTime() > stop) { return; }
      }
    }

    function genRowId(){
        sleep(1);
        return (new Date()).getTime();
    }

    function checkAll() {
        form = document.forms[0];
        if(form.uOid == null) {
            return;
        }

        len = form.uOid.length;
        if(len) {
            for(var i = 0; i < len;i++) {
                form.uOid[i].checked = form.chkAll.checked;
            }
        }
        else {
            form.uOid.checked = form.chkAll.checked;
        }
    }

    //사내 or 협력사 검색팝업 Open
    function selectProduction(){
        var sel = document.getElementById("selInOut").value;
        if (sel == 'i'){
            selectDepartment();
        }else {
            selectPartner();
        }
    }

    //사내생산처 검색 팝업  Open
    function selectDepartment() {
        var mode = "single";
        var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&codetype=PRODUCTIONDEPT&command=select&mode="+mode;
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");
        if(typeof returnValue == "undefined" || returnValue == null) {
            return;
        }
        linkDept(returnValue);
    }

    //사내생산처 검색결과 셋팅
  function linkDept(arr){
      if(arr.length == 0) {
            alert("<%=messageService.getString("e3ps.message.ket_message", "01553") %><%--부서를 선택하시기 바랍니다--%>");
            return;
        }

        document.getElementById("proteamNo").value = arr[0][0];
        document.getElementById("proteamName").value = arr[0][2];
  }

    //협력사검색 팝업 Open
    function selectPartner(){
        var url="/plm/jsp/ews/SelectPartnerPopup.jsp?mode=single&method=linkPartner";
        openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
  }

  //협력사 검색결과 셋팅
  function linkPartner(arr){
      if(arr.length == 0) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "03215") %><%--협력사를 선택하시기 바랍니다--%>');
            return;
        }

        document.getElementById("proteamNo").value = arr[0][0];
        document.getElementById("proteamName").value = arr[0][1];
  }

  //필드 값 삭제
  function deleteValue(param){
      document.getElementById(param).value = "";
  }

    //생산처 변경 시 타생산처 비활성화 및 삭제
  function changeProteam(){
      deleteValue("proteamName");
      deleteValue("proteamNo");
  }

  //개발담당 or PM 변경
  function displayImg(){
      isPM = document.getElementById("isPM").value;
        if(isPM == "false"){
            document.getElementById("createImg").style.display = "none";
            document.getElementById("deleteImg").style.display = "none";
        }else{
            document.getElementById("createImg").style.display = "";
            document.getElementById("deleteImg").style.display = "";
        }
        document.getElementById("department").value = "";
        document.getElementById("tempdepartment").value = "";
        document.getElementById("devManager").value = "";
        document.getElementById("tempdevManager").value = "";
  }
//제품 변경 사유 등록
  function pchangeCreate(oid){
          var url = "/plm/jsp/project/ProductChangeHistory.jsp?oid="+oid;
        openSameName(url,"500","300","status=no,scrollbars=no,resizable=no");

  }
//제품 변경 사유 보기
  function pchangeView(oid){
          var url = "/plm/jsp/project/ProductChangeHistoryView.jsp?oid="+oid;
        openSameName(url,"520","500","status=no,scrollbars=yes,resizable=no");

  }
  function clearDr(){
	  document.forms[0].drNumber.value='';
      document.forms[0].drKeyOid.value='';
      $("#reviewPjt").multiselect("uncheckAll");
      $("#reviewPjt").empty().data('options');
      $("#reviewPjt").multiselect('refresh');
  }
//개발의뢰서 연계
    function selectDr(){
    //개발 검토  : developmentStep=R
    //제품   : developmentStep=D
        var url="/plm/jsp/dms/SearchDevRequestPop.jsp?method=selDr&developmentStep=D&mode=one";
        openWindow(url,"","800","600","status=1,scrollbars=no,resizable=no");
    }
    function selDr(arr){
    	$("#reviewPjt").empty().data('options');
        $("#reviewPjt").append("<option value=''>선택</option>");
        $("#reviewPjt").focus();
        var param = "command=drSearch";
        for (var i = 0; i < arr.length; i++){
           param += "&drOid=" + encodeURIComponent(arr[i][0]);
        }
        var url = "/plm/jsp/project/ProjectDevRequestAction.jsp";
        postCallServer(url, param, onTargetSet, true);
    }
    var targetReviewTableId = "productInfo";
    function onTargetSet(req){
    	
        var xmlDoc = req.responseXML;
        //var showElements = xmlDoc.selectNodes("//message");
        var msg = xmlDoc.getElementsByTagName("l_message")[0].textContent || xmlDoc.getElementsByTagName("l_message")[0].text; 
        var l_result = xmlDoc.getElementsByTagName("l_result")[0].textContent || xmlDoc.getElementsByTagName("l_result")[0].text;

        if(msg == 'false' && l_result != null && l_result != ""){
            alert(l_result);
            return;
        }
        if(msg == 'false') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
            return;
        }

        //var showElements = xmlDoc.selectNodes("//data_info");
        var l_drNumber = xmlDoc.getElementsByTagName("l_drNumber")[0].textContent || xmlDoc.getElementsByTagName("l_drNumber")[0].text;
        var l_drKeyOid = xmlDoc.getElementsByTagName("l_drKeyOid")[0].textContent || xmlDoc.getElementsByTagName("l_drKeyOid")[0].text;
        
        var form = document.forms[0];
        form.drNumber.value = decodeURIComponent(l_drNumber);
        form.drKeyOid.value = decodeURIComponent(l_drKeyOid);
        
        $("#reviewPjt").multiselect("uncheckAll");
        $("#reviewPjt").empty().data('options');
        $("#reviewPjt").multiselect('refresh');
        
        var l_rvPjtNos = xmlDoc.getElementsByTagName("l_rvPjtNos")[0].textContent || xmlDoc.getElementsByTagName("l_rvPjtNos")[0].text;
        var l_rvPjtOids = xmlDoc.getElementsByTagName("l_rvPjtOids")[0].textContent || xmlDoc.getElementsByTagName("l_rvPjtOids")[0].text;
        
        l_rvPjtNos = decodeURIComponent(l_rvPjtNos);
        l_rvPjtOids = decodeURIComponent(l_rvPjtOids);
        
        var pjtNoArr = l_rvPjtNos.split(",").sort();
        var pjtOidArr = l_rvPjtOids.split(",").sort();
        
        for(var i=0; i<pjtNoArr.length; i++){
            $("#reviewPjt").append("<option value='"+pjtNoArr[i]+"'>"+ pjtNoArr[i] +"</option>");  
        }
        $("#reviewPjt").multiselect('refresh');
    }

    //Number Code Ajax
    function numCodeAjax(codeType, code, targetId) {
        $.ajax( {
            url : "/plm/servlet/e3ps/NumberCodeAjax",
            type : "POST",
            data : {codeType:codeType, code:code},
            dataType : 'json',
            async : false,
            success: function(data) {
                $.each(data.numCode, function() {
                    $("#"+targetId).append("<option value='"+this.code+"' vendercode='"+this.vendercode+"'>"+ this.value +"</option>");
                });
            }
        });
    }

    //Number Code Ajax
    var myCodeVendercode = "";
    function numCodeAjaxMyCode(codeType, myCode) {
        myCodeVendercode = "";
        $.ajax( {
            url : "/plm/servlet/e3ps/NumberCodeAjax",
            type : "POST",
            data : {codeType:codeType, myCode:myCode},
            dataType : 'json',
            async : false,
            success: function(data) {
                $.each(data.numCode, function() {
                    myCodeVendercode = this.vendercode;
                });
            }
        });
    }

    function getSelect1(code) {

        $("#productTypeLevel2").empty().data('options');
        $("#productTypeLevel2").append("<option value='' >"+ "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" +"</option>");
        // 제품구분에서 선택된 code로 공통코드 테이블 조회
        numCodeAjax("PRODUCTTYPELEVEL2", code, "productTypeLevel2");
        // 공통코드조회해서 제품구분 Level2가 존재하거나. 제품구분이 value가 공백이 아니면 제품구분Level2 표시
        // 아닌경우는 제품구분Level2, 방수여부를 초기화후 숨김처리
        if ( $("#productTypeLevel2 option").size() > 1 && $("#productType option:selected").val() != "" ) {
            $("#productTypeLevel2").show();
        }
        else {
            $("#productTypeLevel2").empty().data('options');
            $("#productTypeLevel2").append("<option value='' >"+ "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" +"</option>");
            $("#productTypeLevel2").hide();
        }
        // productTypeLevel2의 options가 없는 경우
//         if ( $("#productTypeLevel2 option").size() == 1 ) {
//             if ( code != "" ) {
//                 getSelect2(code);
//             }
//         }
    }

    // 사용안함
    function getSelect2(code) {
        $("#sealed").empty().data('options');
        $("#sealed").append("<option value='' >"+ "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" +"</option>");
        $("#sealed").hide();

        // code로 PRODUCTTYPELEVEL2에 있는 vendercode 조회
        numCodeAjaxMyCode("PRODUCTTYPELEVEL2", code);

        if ( myCodeVendercode != undefined && myCodeVendercode != "" ) {
            var sp = myCodeVendercode.split("|");
            for ( var i=0; i<sp.length; i++ ) {
                if ( sp[i] == "SEALED" ) {
                    $("#sealed").empty().data('options');
                    $("#sealed").append("<option value='' >"+ "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" +"</option>");
                    numCodeAjax("SEALED", "", "sealed");
                }

                if ( sp[i] == "SERIES" ) {
                    $("#series").empty().data('options');
                    $("#series").append("<option value='' >"+ "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" +"</option>");
                    numCodeAjax("SERIES", "", "series");
                }
            }
        }

        // 방수여부가 selectbox의 size 1보다 클 경우
        if ( $("#sealed option").size() > 1 ) {
            $("#sealed").show();
        }
    }
    
    
    //최종사용처 검색화면 오픈하여 선택된 최종사용처를 입력한다.
    function selectMultiCustomer(arrObj){
        var fm = document.prodForm;
        var pos = fm.CUSTOMEREVENTOid.length;
        var subArr;
        
        if(typeof arrObj == "undefined" || arrObj == null) {
            return;
          }
        
        for(var i = 0; i < arrObj.length; i++) {
            subArr = arrObj[i];
          
            for(var j = 0; j < pos; j++) {
                if(fm.CUSTOMEREVENTOid.options[j].value==subArr[0]){
                    alert(subArr[2]+"는 이미 존재하는 최종 사용처입니다");
                    return;
                }
            }
          
            fm.CUSTOMEREVENTOid.length = pos+i+1;
            fm.CUSTOMEREVENTOid.options[pos+i].value = subArr[0];
            fm.CUSTOMEREVENTOid.options[pos+i].text = subArr[2];
            fm.CUSTOMEREVENTOid.options[pos+i].selected = true;
        }
    }
    
    //최종사용처 검색화면 오픈하여 선택된 최종사용처를 입력한다.
    function insertLastUsingBuyer() {
        
        var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=1&selectedDepth=1&codetype=CUSTOMEREVENT&command=select&mode=multi&designTeam=&invokeMethod=selectMultiCustomer";
        window.open(url, "CUSTOMEREVENT", "top=100px, left=100px, height=500px, width=850px");
    }
        
    //선택된 최종사용처를 삭제한다.
    function deleteLastUsingBuyer() {
        var fm = document.prodForm;
        while(fm.CUSTOMEREVENTOid.selectedIndex>=0){
          if((fm.CUSTOMEREVENTOid.length>0)&&(fm.CUSTOMEREVENTOid.selectedIndex>=0)){
            var pos = fm.CUSTOMEREVENTOid.selectedIndex;
            fm.CUSTOMEREVENTOid.remove(pos);
          }
        }
    }
    
    function selectMultiSubContractor(arrObj){
        var fm = document.prodForm
        var pos = fm.sOid.length;
        var subArr;
        
        if(typeof arrObj == "undefined" || arrObj == null) {
            return;
        }

        for(var i = 0; i < arrObj.length; i++) {
            subArr = arrObj[i];

            for(var j = 0; j < pos; j++) {
                if(fm.sOid.options[j].value==subArr[0]){
                    alert(subArr[2]+"는 이미 존재하는 의뢰처입니다");
                    return;
                }
            }

            fm.sOid.length = pos+i+1;
            fm.sOid.options[pos+i].value= subArr[0];
            fm.sOid.options[pos+i].text = subArr[2];
            fm.sOid.options[pos+i].selected = true;
        }
    }
    
    //의뢰처 검색화면 오픈하여 선택된 의뢰처를 입력한다.
    function insertRequestBuyer() {

      var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&selectedDepth=0&codetype=SUBCONTRACTOR&command=select&mode=multi&viewType=&disable=&invokeMethod=selectMultiSubContractor";
      window.open(url, "MultiSubContractor", "top=100px, left=100px, height=500px, width=850px");
      
    }

    //선택된 의뢰처를 삭제한다.
    function deleteRequestBuyer() {
      var fm = document.prodForm;

      while(fm.sOid.selectedIndex>=0){
        if((fm.sOid.length>0)&&(fm.sOid.selectedIndex>=0)){
          var pos = fm.sOid.selectedIndex;
          fm.sOid.remove(pos);
        }
      }

    }
    
    $(document).ready(function(){
    	<%if(!isCostRequest){ %>
    	$("#reviewPjt").multiselect({
            minWidth: 80,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
    	<%}%>
        //대표차종
        SuggestUtil.bind('CARTYPE', 'tempModel', 'model');
        //PM suggest
        //SuggestUtil.bind('USER', 'tempdevManager', 'devManager');
        SuggestUtil.multiBind('USERDEPT', 'tempdevManager', 'devManager', null, 'department');
        //영업담당자 suggest
        SuggestUtil.bind('USER', 'tempsales', 'sales');
        CalendarUtil.dateInputFormat('planStartDate');
        SuggestUtil.bind('PRODUCTPROJNO', 'pjtno', 'linkpjtoid');
        $('[name=itDivision]').change(function(){
            if($(this).val() == 'dev'){
                $('[name=tempmodel]').val('');
                $('[name=model]').val('');
                $('#modelDiv').hide();
            }else{
                $('#modelDiv').show();              
            }
        });
    });
    
    function developePurposeChange(){
        
        if ( $("#developePurpose1").val() != null ) {
            var choiceCode  = $("#developePurpose1").val();
            
            if(choiceCode != ''){
                $("#developePurpose2").empty().data('options');
                
                $.ajax({
                    url : "/plm/ext/code/getChildCodeList.do?codeType=DEVELOPANDREVIEWGOAL&parentCode="+choiceCode,
                    type : "POST",
                    dataType : 'json',
                    async : false,
                    success: function(data) {
                        $("#developePurpose2").append("<option value=''>선택</option>");
                        $.each(data, function() {
                            $("#developePurpose2").append("<option value='"+this.code+"'>"+ this.value +"</option>");
                        });
                        
                        if(choiceCode == 'DP2' || choiceCode == 'DP3'){//4M 또는 연구 일때 개발구분 선택필요없음
                            $("#developentType").val("-");
                            
                            $("#developentType").prop("disabled", true);

                        }else{
                            $("#developentType").prop("disabled", false);
                        }
                        
                        
                    }
                });
            }else{
                $("#developePurpose2").empty().data('options');
                $("#developePurpose2").append("<option value=''>선택</option>");
            }
        }
     }
    function setOEMTypes(data){
        
        for(var i = 0; i < data.length; i++){
            
            var oemOid = data[i][0];
            var oemName = data[i][1];
            var isDuplicate = false;
            
            $('input[name=oemOid]').each(function(){
                if(oemOid == $(this).val()) {
                    isDuplicate = true;
                    return false;
                }
            });
            
            if(!isDuplicate){
                var oemTypeHtml = "<div style='float:left;width:100px;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;' title='" +
                                  oemName + "'><img src='/plm/portal/images/b-minus.png' style='cursor:pointer;' onclick='$(this).parents(\"div:first\").remove();'> " + 
                                  oemName + "<input type='hidden' name='oemOid' value='" + oemOid + "'/></div>";
                $("#OEMTYPELIST").append(oemTypeHtml);
            }
        }
    }
    
    function setLinkProject(returnValue){
        if(returnValue == null) {
            return;
        }

        var trArr;
        trArr = returnValue[0];

        $("#linkpjtoid").val(trArr[0]);
        $("#pjtno").val(trArr[1]);
    }

    function setProjectNo(){
        
        var url="/plm/jsp/project/SearchPjtPop.jsp?mode=one&modal=N&paramRadio=2&fncall=setLinkProject";
        window.open(url, "setProjectNo", "top=100px, left=100px, height=768px, width=1024px");    
    }
    
    function setPartClaz(returnValue){
        $('[name=productTypeLevel2]').val(returnValue[0].id);//oid
        $('[name=productTypeLevel2Name]').val(returnValue[0].name);//kr name
    }
//-->
</script>
</head>
<body>
<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<form name="prodForm" method="post">
    <!-- hidden begin -->
    <input type="hidden" name="initType" value="<%=initType%>">
    <input type="hidden" name="tempProjectNo" value="">
    <input type="hidden" name="lifecycle" value="Default">
    <input type="hidden" name="pmoid" value="<%=pmoid%>">
    <input type="hidden" name="pwlinkOid" value="<%=pwlinkOid%>">
    <input type="hidden" name="projectType" value="<%=projectType%>">
    <input type="hidden" name="oid" value="<%=oid%>">
    <input type="hidden" name="deletePOid" value="">
    <input type="hidden" name="command" value="Update">
    <input type="hidden" name="department" value="<%=projectData.departmentOid%>">
    <input type="hidden" name="developTyped">
    <!-- hidden end -->
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="50" valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0"
                                    cellspacing="0">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02632")%><%--제품프로젝트 수정--%></td>
                                    </tr>
                                </table></td>
                            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table></td>
            </tr>
            <tr>
                <td valign="top">
                    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></td>
                                        <td align="right">
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                        <%
                                        String pjtno1 = "";
                                        Connection con = null;
                                        PreparedStatement st = null;
                                        ResultSet rs = null;
                        
                                        try {
                                            con = DBCPManager.getConnection("plm");
                        
                                            String sql = "select reviewProjectCode from COST_i where rownum=1 and projectCode = '" + project.getPjtNo() + "'";
                                            st = con.prepareStatement(sql);
                                            rs = st.executeQuery();
                        
                                            if(rs.next()) {
                                                pjtno1 = rs.getString("reviewProjectCode");
                                            }
                                        } catch (Exception e) {
                                            //e.printStackTrace();
                                            throw new Exception(e);
                                        } finally {
                                            try {
                                                if(rs != null){
                                                    rs.close();
                                                }
                                                if(st != null){
                                                    st.close();
                                                }
                        
                                                if (con != null) con.close();
                                            } catch (SQLException e) {
                                                //e.printStackTrace();
                                            }
                        
                                        }
                        
                                        if((CommonUtil.isMember("자동차PMO")) || (CommonUtil.isAdmin())) {
                                        %>
                                                <td class="font_03">&nbsp;<input type="hidden" name="pjtno1" class="txt_field" id="pjtno1" value="<%=pjtno1%>" style="width: 180"></td>
                                        <%} else { %>
                                                <td class="font_03">&nbsp;<input type="hidden" name="pjtno1" class="txt_field" id="pjtno1" value="<%=pjtno1%>" style="width: 180"></td>
                                        <%} %>
                                                    <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;" onclick="javascript:onSave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td width="5">&nbsp;</td>
                                                    <td>
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <COL width="140">
                                    <COL width="250">
                                    <COL width="140">
                                    <COL width="250">
                                    <%
                                    if (CommonUtil.isMember("KETS_PMO") || CommonUtil.isMember("KETS")) {
                                        %>
                                        <input type="hidden" name="teamType" value="KETS">
                                        <input type="hidden" name="projectType" value="5">
                                    <%}else{ %>
                                    <tr>
                                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00660") %><%--개발의뢰서--%></td>
                                        <%if(isCostRequest){ %>
                                        <td class="tdwhiteL" colspan='3'>
                                        <%for(Map<String, Object>  reviewMap : reviewListByReq){ %>
                                            <%if("selected".equals((String)reviewMap.get("selected"))){ %>
                                            <a href="#" onclick="javascript:viewProjectPop('<%=(String)reviewMap.get("reviewOid")%>');"><%=(String)reviewMap.get("pjtNo")%></a>&nbsp;|&nbsp;
                                            <%} %>
                                            
                                         <%} %>
                                        <a href="#" onclick="javascript:openView('<%=devROid%>');"><%=devRNumber%> </a>                            
                                        <input type="hidden" id = 'drNumber' name="drNumber" value="<%=devRNumber%>">
                                        <input type="hidden" id = 'reviewPjt' name="reviewPjt" value="<%=StringUtil.checkNull(productProject.getReviewPjtNo()) %>">                       
                                        </td>
                                        <%}else{ %>
                                        <td class="tdwhiteL"><input type="text" name="drNumber" value="<%=devRNumber%>" class="txt_fieldRO" id="drNumber" style="width:40%" readonly>
                                        <a href="javascript:;" onClick="javascript:selectDr();"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                                        <a href="javascript:;" onClick="javascript:clearDr();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                                        </td>
                                        <td class="tdblueL">검토프로젝트<span class="red">*</span></td>
                                        <td class="tdwhiteL0">
                                            <select name="reviewPjt" id="reviewPjt" class="fm_jmp" style="width: 200px;" multiple="multiple">
                                            <%for(Map<String, Object>  reviewMap : reviewListByReq){ %>
                                            <option value="<%=(String)reviewMap.get("pjtNo") %>" <%=(String)reviewMap.get("selected") %>><%=(String)reviewMap.get("pjtNo") %></option>
                                            <%} %>
                                            </select>
                                        </td>
                                        <%} %>
                                        
                                        <input type='hidden' name="drKeyOid" value="<%=devROid%>"> 
                                        
                                        
                                  </tr>
                                    <%
                                    }
                                    %>
                             
                                    
                                  <tr>
                                    <td class="tdblueL">개발목적<span class="red">*</span></td>
                                    <td class="tdwhiteL">
                                    <%=project.getMainGoalType() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("DEVELOPANDREVIEWGOAL", project.getMainGoalType().getCode(), messageService.getLocale().toString()))+ " / "%>
                                    <%=project.getSubGoalType() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("DEVELOPANDREVIEWGOAL", project.getSubGoalType().getCode(), messageService.getLocale().toString()))%>
                                    </td>
                                    <%
                                    teamType = "";
                                    if("자동차 사업부".equals(projectData.teamType)) {
                                	   teamType = "자동차";
                                    %>
                                        <input type="hidden" name="teamType" value="자동차 사업부">
                                    <%}else {
                                	   teamType = "전자";
                                	%>  <input type="hidden" name="teamType" value="전자 사업부">
                                	<%}
                                	%>
                                	<td class="tdblueL">
                                            <%
                                            if("전자 사업부".equals(projectData.teamType)) {
                                            %>
                                                    전자개발구분
                                            <%
                                            }else{
                                            %>
                                            <%=messageService.getString("e3ps.message.ket_message", "01662")%><%--사업부--%>
                                            <%}%>
                                        </td>
                                        <td class="tdwhiteL0">
                                            <%
                                            if("전자 사업부".equals(projectData.teamType)) {
                                            %>
                                            <select name="itDivision" style="width:180" class="fm_jmp">
                                                <option value="it" <%="it".equals(productProject.getItDivision())?"selected":""%>>전장IT개발</option>
                                                <option value="dev" <%="dev".equals(productProject.getItDivision())?"selected":""%>>전자개발</option>
                                            </select> 
                                            <%
                                            }else{
                                            %>
                                            <%=((ProductProject)project).getTeamType()%>
                                            <%}%>
                                        </td>
                                  </tr>
                                  <tr>
                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578") %><%--제품구분--%>
                                        <span class="red">*</span></td>
                                    <td class="tdwhiteL">
                                        <%
                                        
                                        String productTypeLevel2Name = "";
                                        String productTypeLevel2Oid = "";
                                        //방어코드
                                        try{
                                            if(StringUtil.checkString(project.getProductTypeLevel2()) && project.getProductTypeLevel2().indexOf("KETPartClassification") > 0){
                                                KETPartClassification partClaz = (KETPartClassification)CommonUtil.getObject(project.getProductTypeLevel2());
                                                productTypeLevel2Name = partClaz.getClassKrName();
                                                productTypeLevel2Oid = project.getProductTypeLevel2();
                                            }
                                        }catch(Exception e){
                                        }
                                        %>
                                        <input type="text" name="productTypeLevel2Name" class="txt_field" style="width: 180px" value="<%=productTypeLevel2Name%>">
                                        <input type="hidden" name="productTypeLevel2" value="<%=productTypeLevel2Oid%>">
                                        <a href="javascript:SearchUtil.selectOnePartClazPopUp('setPartClaz','onlyLeaf=Y&openAll=N&depthLevel2=Y');">
                                        <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                                        <a href="javascript:CommonUtil.deleteValue('productTypeLevel2','productTypeLevel2Name');">
                                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                                        
                                        <td class="tdblueL">관리 제품군<span class="red">*</span></td>
                                        <td class="tdwhiteL0">
                                            <select name="manageProduct" class="fm_jmp" style="width: 180">
                                                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                                                <%    
                                                    parameter.clear();
                                                    parameter.put("locale",   messageService.getLocale());
                                                    parameter.put("codeType", "MANAGEPRODUCTTYPE");
                                                    parameter.put("description", teamType);
                                                    numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
                                
                                                    for ( int i=0; i<numCode.size(); i++ ) {
                                                        String selectedValue = "";
                                                        if ( project.getManageProductType() != null && project.getManageProductType().getCode().equals(numCode.get(i).get("code")) )
                                                            selectedValue = "selected";
                                                %>
                                                <option value="<%=numCode.get(i).get("code") %>" <%=selectedValue%>><%=numCode.get(i).get("value")%></option>
                                                <%
                                                    }
                                                %>
                                            </select></td>
                                        
                                    </tr>
                                    <%if(project.getProcess().getCode().equals("PC002") || project.getProcess().getCode().equals("PC005")){ %>
                                    <tr id="linkPjtTR">
                                    <%}else{ %>
                                    <tr id="linkPjtTR" style="display: none">
                                    <%} %>
			                        <td width="140" class="tdblueL">연계 프로젝트</td>
			                            <td class="tdwhiteL">
			                                <input type="text" name="pjtno" id="pjtno" class="txt_field" style="width:26%" value="<%=StringUtil.checkNull(project.getMaster().getLinkProjectNo())%>">
			                                <input type="hidden" name="linkpjtoid" id="linkpjtoid" value="<%=linkpjtoid%>">
			                                <a href="javascript:setProjectNo();"><img src="/plm/portal/images/icon_5.png" border="0"></a> 
			                                <a href="javascript:CommonUtil.deleteValue('pjtno','linkpjtoid');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
			                            </td>
			                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629")%><%--개발단계--%><span class="red">*</span></td>
                                    <td class="tdwhiteL0">
                                    <%if("PMOINWORK".equals(state)) {%> 
                                    <select name="process" class="fm_jmp" style="width: 180">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                        <%
                                        parameter.clear();
                                        parameter.put("locale", messageService.getLocale());
                                        parameter.put("codeType", "PROCESS");
                                        numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                                        for (int i = 0; i < numCode.size(); i++) {
                                            String selectedValue = "";
                                            if ( project.getProcess() != null && project.getProcess().getCode().equals(numCode.get(i).get("code")) )
                                            selectedValue = "selected";
                                        %>
                                       <option value="<%=numCode.get(i).get("code") %>" <%=selectedValue%>><%=numCode.get(i).get("value")%></option>
                                        
                                        <%
                                            }
                                        %>
                                    </select></td>
                                    <%}else{ %>
                                        <input type="hidden" name="process" value="<%=project.getProcess().getCode()%>"><%=project.getProcess().getName()%>
                                    <%} %>
			                        </tr>
                                    <tr>
                                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02139") %><%--영업담당자--%></td>
                                        <td class="tdwhiteL">
                                            <input type="text" name="tempsales" class="txt_field" style="width: 180" value='<%=projectData.salesName%>'> 
                                            <input type='hidden' name='sales' value='<%=projectData.salesOid%>'> 
                                            <a href="javascript:;" onClick="javascript:addRoleMember('sales');"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                                            <a href="javascript:CommonUtil.deleteValue('sales','tempsales');">
                                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                                        </td>
                                        <td width="150" class="tdblueL">PM <span class="red">*</span></td>
                                        <td width="240" class="tdwhiteL0">
                                            <input type='hidden' name='isPM' value='true'>
                                            <input type="text" name="tempdevManager" class="txt_field" style="width: 180" value='<%=projectData.pjtPmName%>'> 
                                            <input type='hidden' name='devManager' value='<%=StringUtil.checkNull(CommonUtil.getOIDString(projectData.pjtPm))%>'>
                                            <a href="javascript:;" onClick="javascript:addRoleMember('devManager');"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                                            <a href="javascript:CommonUtil.deleteValue('tempdevManager','devManager','department');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113")%><%--프로그램명 --%> <span class="red">*</span></td>
                                        <td colspan=3 class="tdwhiteL0">
                                        <input type="text" name="projectName" class="txt_field" style="width: 98%" value="<%=projectData.pjtName%>" /></td>
                                    </tr>
                                    <tr>
                                        <td class="tdblueL">Rank<span class="red">*</span></td>
                                        <td class="tdwhiteL">
                                            <select name="rank" class="fm_jmp" style="width: 180">
                                                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                                                <%
                                                if(teamType != null && teamType.length() > 0) {
                                                    parameter.clear();
                                                    parameter.put("locale",   messageService.getLocale());
                                                    parameter.put("codeType", "RANK");
                                                    parameter.put("code",     "RAN1000");                                                	
                                                    parameter.put("depthLevel", "child");
                                                    numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
                                
                                                    for ( int i=0; i<numCode.size(); i++ ) {
                                                        String selectedValue = "";
                                                        if ( project.getRank() != null && project.getRank().getCode().equals(numCode.get(i).get("code")) )
                                                            selectedValue = "selected";
                                                %>
                                                <option value="<%=numCode.get(i).get("code") %>" <%=selectedValue%>><%=numCode.get(i).get("value")%></option>
                                                <%
                                                    }
                                                }
                                                %>
                                            </select></td>
                                        <td width="150" class="tdblueL">
                                            <%=messageService.getString("e3ps.message.ket_message", "01248") %><%--대표차종--%>
                                            <span class="red">*</span>
                                        </td>
                                        <td width="240" class="tdwhiteL0">
                                            <div id="modelDiv" <%="dev".equals(productProject.getItDivision())?"style=\"display:none\"":""%>>
                                                <%if(!projectData.isCarDivisionMode) {%> 
                                                <input type='hidden' name='tempModel' value=''>
                                                <input type='hidden' name='model' value=''> 
                                                <%}else{ %> 
                                                <input type="text" name="tempModel" class="txt_field" style="width: 180" value="<%=projectData.representModel%>">
                                                <input type='hidden' name='model' value='<%=project.getOemType()==null?"":CommonUtil.getOIDString(project.getOemType())%>'>
                                                <a href="#" onClick="javascript:onModel();"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                                                <a href="javascript:CommonUtil.deleteValue('tempModel','model');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                                            </div>
                                        </td>
                                        <%} %>
                                    </tr>
                                    <tr>
                                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%><span class="red">*</span></td>
                                        
                                        <%
                                        String MainGoalType = "";
                                        %>
                                        
                                        <%
                                        WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
                                        String id = user.getName();//일시적으로 구본경 대리에게 권한부여
                                        
                                        if(project.getMainGoalType() != null){ //개발목적이 4M 또는 연구 일때 개발구분은 선택불가
                                            MainGoalType = project.getMainGoalType().getCode(); 
                                        }
                                        
                                        if(("gubk".equals(id) || CommonUtil.isAdmin()) && !"".equals(MainGoalType) && !"DP2".equals(MainGoalType) && !"DP3".equals(MainGoalType)){ %>
                                        <td class="tdwhiteL"><select name="developentType" class="fm_jmp" style="width: 180">
                                                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                                                <%
                                                parameter.clear();
                                                parameter.put("locale",   messageService.getLocale());
                                                parameter.put("codeType", "DEVELOPENTTYPE");
                                                numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
                                
                                                for ( int i=0; i<numCode.size(); i++ ) {
                                                    if( !"확대적용".equals(numCode.get(i).get("value"))){//확대적용은 영업수주프로젝트에서만 사용
                                                    String selectedValue = "";
                                                    if ( project.getDevelopentType() != null && project.getDevelopentType().getCode().equals(numCode.get(i).get("code")) )
                                                        selectedValue = "selected";
                                                %>
                                                <option value="<%=numCode.get(i).get("code") %>" <%=selectedValue%>><%=numCode.get(i).get("value")%></option>
                                                <%
                                                    }
                                                }
                                                %>
                                        </select></td>
                                        <%}else{ %>
                                        <input type="hidden" name="developentType" value="<%=project.getDevelopentType() == null ? "" : project.getDevelopentType().getCode()%>">
                                        <td class="tdwhiteL"><%=project.getDevelopentType() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("DEVELOPENTTYPE", project.getDevelopentType().getCode(), messageService.getLocale().toString()))%></td>
                                        <%} %>
                                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01843") %><%--설계구분--%></td>
                                        <td class="tdwhiteL0"><select name="designType" class="fm_jmp" style="width: 180">
                                                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                                                <%
                                                parameter.clear();
                                                parameter.put("locale",   messageService.getLocale());
                                                parameter.put("codeType", "DESIGNTYPE");
                                                numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
                                
                                                for ( int i=0; i<numCode.size(); i++ ) {
                                                    String selectedValue = "";
                                                    if ( project.getDesignType() != null && project.getDesignType().getCode().equals(numCode.get(i).get("code")) )
                                                        selectedValue = "selected";
                                                %>
                                                <option value="<%=numCode.get(i).get("code") %>" <%=selectedValue%>><%=numCode.get(i).get("value")%></option>
                                                <%
                                                }
                                                %>
                                        </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847")%><%--최종사용처--%></td>
                                        <td class="tdwhiteL">
                                            <select id="CUSTOMEREVENTOid" name="CUSTOMEREVENTOid" style="width: 75%;" size=2 multiple="multiple">
                                            <%
                                                if (projectData.customereventVec != null && projectData.customereventVec.size() > 0) {
                                                    
                                                    for (int i = 0; i < projectData.customereventVec.size(); i++) {
                                                        NumberCode nc = (NumberCode) projectData.customereventVec.get(i);
                                                        String ncOid = (String)CommonUtil.getOIDString(nc);
                                                        String masterName = NumberCodeUtil.getNumberCodeValue("CUSTOMEREVENT", nc.getCode(), messageService.getLocale().toString());
                                                        out.print("<option value='"+ncOid+"' selected>"+masterName+"</option>");
                                                    }
                                                }
                                            %>
                                            </select>
                                            <a href="javascript:insertLastUsingBuyer()"><img src="../../portal/images/icon_5.png"
                                                border="0" align="top"></a> <a href="javascript:deleteLastUsingBuyer()"><img
                                                src="../../portal/images/icon_delete.gif" border="0" align="top"></a></td>
                                        <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859")%><%--고객처--%></td>
                                        <td class="tdwhiteL0">
                                            <select name="sOid" style="width: 75%;" size=2 multiple="multiple">
                                                <%
                                                    if (projectData.subcontractorVec != null && projectData.subcontractorVec.size() > 0) {
                                                        for (int i = 0; i < projectData.subcontractorVec.size(); i++) {
                                                            NumberCode nc = (NumberCode) projectData.subcontractorVec.get(i);
                                                            String ncOid = (String)CommonUtil.getOIDString(nc);
                                                            String masterName = NumberCodeUtil.getNumberCodeValue("SUBCONTRACTOR", nc.getCode(), messageService.getLocale().toString());
                                                            out.print("<option value='"+ncOid+"' selected>"+masterName+"</option>");
                                                        }
                                                    }
                                                %>
                                            </select>  
                                            <a href="javascript:insertRequestBuyer()"><img src="../../portal/images/icon_5.png" border="0"
                                                align="top"></a> <a href="javascript:deleteRequestBuyer()"><img
                                                src="../../portal/images/icon_delete.gif" border="0" align="top"></a></td>
                                    </tr>
                                    <tr>
                                        <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00817","")%><!-- 계획시작일 --> <span class="red">*</span></td>
                                        <td class="tdwhiteL"><input type="text" name="planStartDate" class="txt_field"
                                            style="width: 100px" value="<%=DateUtil.getDateString(projectData.pjtPlanStartDate, "D")%>"> <img src="/plm/portal/images/icon_delete.gif" border="0"
                                            onclick="javascript:CommonUtil.deleteDateValue('planStartDate');" style="cursor: hand;"></td>
                                        
                                        
                                        <td class="tdblueL">수주관리<span class="red">*</span></td>
                                        <td class="tdwhiteL0"><select name="obtainType" class="fm_jmp" style="width: 180">
                                                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                                                <%
                                                parameter.clear();
                                                parameter.put("locale",   messageService.getLocale());
                                                parameter.put("codeType", "OBTAINORDERTYPE");
                                                numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
                                
                                                for ( int i=0; i<numCode.size(); i++ ) {
                                                    String selectedValue = "";
                                                    if ( project.getObtainType() != null && project.getObtainType().getCode().equals(numCode.get(i).get("code")) )
                                                        selectedValue = "selected";
                                                %>
                                                <option value="<%=numCode.get(i).get("code") %>" <%=selectedValue%>><%=numCode.get(i).get("value")%></option>
                                                <%
                                                }
                                                %>
                                        </select>
                                        </td>
                                    </tr>
                                    <% if(develop_typed_list != null){ %>
                                    <tr>
                                    <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00653","")%><%--개발유형--%></td>
                                    <td width="580" colspan="3" class="tdwhiteL0">
                                        <table border="0" cellspacing="0" cellpadding="0">
		                                    <tr>
					                        <%
					                        for ( int i=0; i < develop_typed_list.size(); i++ )
					                        {
					                        %>
					                        <td width="130"><input type="checkbox" name="chk_develop_typed" id="checkbox"  value='<%=develop_typed_list.get(i).get("code") %>' <%if("1".equals(develop_typed_list.get(i).get("flag"))){ %>checked<%} %>><%=develop_typed_list.get(i).get("value") %></td>
					                        <%
					                        }
					                        %>
									        </tr>
							            </table>
							        </td>
							        </tr>
							        <%}else{%>
							       
							        <tr>
			                        <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00653","")%><%--개발유형--%></td>
			                        <td width="580" colspan="3" class="tdwhiteL0">
			                              <table border="0" cellspacing="0" cellpadding="0">
			                                  <tr>
			                                 <%
			                                    parameter.clear();
			                                    parameter.put("locale", messageService.getLocale());
			                                    parameter.put("codeType", "DEVELOPPATTERN");
			                                    numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
			                                    for ( int i=0; i < numCode.size(); i++ )
			                                    {
			                                       %>
			                                  <td width="130"><input type="checkbox" name="chk_develop_typed" id="checkbox"  value='<%=numCode.get(i).get("code") %>'"><%=numCode.get(i).get("value") %></td>
			                                 <%
			                                       }
			                                 %>
			                                </tr>
			                             </table>
			                       </td>
			                       </tr>
                                    <%} %>
                                    <%-- <tr>
                                        <td class="tdblueL">파생차종
                                            <a href="javascript:SearchUtil.selectCarTypeMulti(setOEMTypes);">
                                            <img src="/plm/portal/images/icon_5.png" border="0"></a>
                                        </td>
			                            <td colspan="3" class="tdwhiteL0">
			                                <div id="OEMTYPELIST">
			                                <%
			                                List<OEMProjectType> oemTypes = projectData.oemTypes;
			                                for(OEMProjectType oemType : oemTypes){
			                                    String oemName = oemType.getName();
			                                    String oemOid = CommonUtil.getOIDString(oemType);
			                                %>
			                                    <div style='float:left;width:100px;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;' title='<%=oemName%>'>
                                                <img src='/plm/portal/images/b-minus.png' style='cursor:pointer;' onclick='$(this).parents("div:first").remove();'>
                                                <%=oemName%><input type='hidden' name='oemOid' value='<%=oemOid%>'/></div>
			                                <%}%>
			                                </div>
			                            </td>
			                       </tr> --%>
                                </table>
                            </td>                               
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>