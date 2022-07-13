<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*,
                wt.org.*,
                wt.project.Role,
                wt.session.*,
                wt.team.*"%>
<%@page import="e3ps.common.code.*,
                e3ps.common.util.*,
                e3ps.common.web.*,
                e3ps.groupware.company.*,
                e3ps.common.jdf.config.Config,
                e3ps.common.jdf.config.ConfigImpl,
                e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.project.outputtype.OEMProjectType"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%
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
    if ( pwlinkOid == null ) {
        pwlinkOid = "";
    }
    if ( product == null ) {
        product ="";
    }
    ProjectManager pm = null;
    if ( pmoid == null ) {
        pmoid = "";
    } else {
        pm = (ProjectManager)CommonUtil.getObject(pmoid);
    }
    if ( tempOid == null ) {
        tempOid = "";
    }
    String oemTypeValue = "";
    if ( pm != null ) {
        OEMProjectType ot = pm.getOemType();
        oemTypeValue = CommonUtil.getOIDString(ot);
    }

    Config conf = ConfigImpl.getInstance();

    String lifecycle = "KET_PRODUCT_PMS_LC";//conf.getString("lifecycle.newproject");

    if ( initType == null ) {
        initType = "produceproject";
    }
    if ( projectConsignment == null ) {
        projectConsignment = "";
    }
    if ( projectSite == null ) {
        projectSite = "";
    }
    if ( projectAcceptanceType == null ) {
        projectAcceptanceType = "";
    }
    if ( projectSaleType == null ) {
        projectSaleType = "";
    }

    Map<String, Object> parameter = new HashMap<String, Object>();
    List<Map<String, Object>> numCode = null;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<TITLE><%=messageService.getString("e3ps.message.ket_message", "02549") %><%--제품 프로젝트 등록--%></TITLE>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<style type="text/css">
body {
	margin-left: 10px;
	margin-top: 0px;
	margin-right: 10px;
	margin-bottom: 5px;
}

table {
	border-spacing: 0;
	border: 0px;
}

table th,table td {
	padding: 0
}

img {
	vertical-align: middle;
	border: 0;
}

input {
	vertical-align: middle;
	line-height: 22px;
}
</style>

<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript" src="/plm/extcore/js/shared/LocaleUtil.js"></script>
<script type="text/javascript">
<!--
    //Create Function
    function onSave(){
        if(!checkValidate()) {
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
        //최종사용처, 고객처 모두 선택되게 한다. 선택 않되면 등록이 않되는 multiple 속성
        $('[name=CUSTOMEREVENTOid] option').prop('selected', true);
        $('[name=sOid] option').prop('selected', true);  
        $.ajax({
            url : "/plm/jsp/project/ActionProductProject.jsp",
            type : "POST",
            data : $('[name=prodForm]').serialize(),
            success: function(data) {
            	alert('<%=messageService.getString("e3ps.message.ket_message", "01315")%>');
            	openView(data.replace(/(^\s*)|(\s*$)/gi, ''));
            	self.close();
            	//location.href="/plm/jsp/project/ProjectViewFrm.jsp?popup=popup&oid="+data.replace(/(^\s*)|(\s*$)/gi, '');
            },
            error : function(){
            	alert('<%=messageService.getString("e3ps.message.ket_message", "01317")%><%--등록실패 --%>');
            	hideProcessing();
            }
        });
    }

  //Check Function
  function checkValidate() {
    var form = document.forms[0];
    var drNumber = form.drNumber.value;
    var developePurpose1 = form.developePurpose1.value;
    var developePurpose2 = form.developePurpose2.value;
    var process = form.process.value;
    var developentType = form.developentType.value;
    var manageProduct = form.manageProduct.value;
    var obtainType = form.obtainType.value;
    
    if(drNumber != '') {
    	if(form.reviewPjt.value == ''){
    		alert("검토프로젝트 번호를 선택하시기 바랍니다.");
    		form.reviewPjt.focus();
    		return false;
    	}
    }
    
    if(manageProduct =='') {
        alert("관리제품군을 선택하시기 바랍니다.");
        form.manageProduct.focus();
        return false;
    }
    
    if(developePurpose1 =='') {
        alert("개발목적을 선택하시기 바랍니다.");
        form.developePurpose1.focus();
        return false;
    }
    
    if(developePurpose2 == '') {
        alert("개발목적을 선택하시기 바랍니다.");
        form.developePurpose2.focus();
        return false;
    }
    
    
    if (form.productTypeLevel2.value == '') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02580") %><%--제품구분을 선택하시기 바랍니다--%>');
        form.productTypeLevel2.focus();
        return false;
    }

    if(process =='') {
      alert("<%=messageService.getString("e3ps.message.ket_message", "07114") %><%-- 개발단계를 선택하시기 바랍니다 --%>");
      form.process.focus();
      return false;
    }
    
    if(developePurpose1 == 'DP1'){//개발목적이 [제품] 
    	if(developePurpose2 == 'DP101' && process != 'PC001' && process != 'PC002' && process != 'PC003' ){ //개발목적 2레벨이 [신제품] 일 때 개발단계는 T-CAR, PROTO, PILOT 만 선택 가능
    		alert("개발목적이 [제품 / 신제품] 일 때 개발단계는 T-CAR, PROTO, PILOT 중 하나이어야 합니다.");
    		form.process.focus();
    		return false;
    	}
    	if(developePurpose2 == 'DP102' && process != 'PC005'){ //개발목적 2레벨이 [사양추가] 일 때 개발단계는 양산 만 선택가능
            alert("개발목적이 [제품 / 사양추가] 일 때 개발단계는 [양산] 만 선택가능합니다.");
            form.process.focus();
            return false;
        }
    	if(developePurpose2 == 'DP103' && process != 'PC005'){ //개발목적 2레벨이 [확대판매] 일 때 개발단계는 양산 만 선택가능
            alert("개발목적이 [제품 / 확대판매] 일 때 개발단계는 [양산] 만 선택가능합니다.");
            form.process.focus();
            return false;
        }
    }
    
    if(developePurpose1 == 'DP2' && process != 'PC005'){//개발목적 4M 일 때 개발단계는 양산 만 선택가능
    	alert("개발목적이 [4M] 일 때 개발단계는 [양산] 만 선택가능합니다.");
        form.process.focus();
        return false;
    }
    
    if(developePurpose1 == 'DP3' && process != 'PC004'){//개발목적 연구 일 때 개발단계는 [ETC] 만 선택가능
        alert("개발목적이 [연구] 일 때 개발단계는 [ETC] 만 선택가능합니다.");
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

    if (form.rank.value == '') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00438") %><%--Rank를 선택하시기 바랍니다--%>");
        form.rank.focus();
        return false;
    }
    
    if(form.teamType.value == '자동차 사업부' && form.model.value =='') {
        alert("대표차종을 지정하십시오.");
        form.tempmodel.focus();
        return false;
    }

    if(form.developentType.value =='') {
      alert("<%=messageService.getString("e3ps.message.ket_message", "00584") %><%--개발 구분을 입력하시기 바랍니다--%>");
      form.developentType.focus();
      return false;
    }
    
    if(form.developentType.value == '-'){//4M 또는 연구 일때 개발구분 선택은 -
        if(developePurpose1 == 'DP2' || developePurpose1 == 'DP3'){
        	
        }else{
        	alert("<%=messageService.getString("e3ps.message.ket_message", "00584") %><%--개발 구분을 입력하시기 바랍니다--%>");
            form.developentType.focus();
        	return false;
        }
    }
    
    //계획 시작일자 CHECK
    if(form.planStartDate.value == '') {
      alert("<%=messageService.getString("e3ps.message.ket_message", "00645") %><%--개발시작일을 입력하시기바랍니다--%>");
      form.planStartDate.focus();
      return false;
    }
    
    if(obtainType == '') {
        alert("수주관리를 선택하시기 바랍니다.");
        form.obtainType.focus();
        return false;
    }
    
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
    
    if(form.templateOid == null || form.templateOid == 'undefined' || form.templateOid.value == '') {
      alert("<%=messageService.getString("e3ps.message.ket_message", "00566") %><%--WBS를 선택 해 주십시오--%>");
      return false;
    }
    
    if($("#linkpjtoid").val() == '' &&(process == 'PC002' || process == 'PC005') && processCode != 'PC001'){
        if(!confirm("연계프로젝트(Proto) 입력이 되지 않았습니다.\r\n그래도 진행하시겠습니까?")){
            return false;   
        }
    }

    return true;
  }

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

  function getParamValue2(name_str) {

    var rowId = document.all.item("rowId");
    var rtnParam = "";

    for(var j = 0; j < rowId.length; j++){
      var paramObj = document.all.item(name_str+""+rowId[j].value);
      if(paramObj == null || paramObj == 'undefined') {
        return "";
      }
      if(rtnParam.length > 0) {
          rtnParam += "&";
        }

      rtnParam += name_str +"=" + paramObj.value;
    }

    return rtnParam;
  }

  function getParamValue3(name_str) {

    var rowId = document.all.item("rowId");
    var rtnParam = "";

    if(rowId.length) {
      for(var j = 0; j < rowId.length; j++){
        var paramObj = document.all.item(name_str+""+rowId[j].value);
        if(paramObj == null || paramObj == 'undefined') {
          return "";
        }
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

  function getIframeParamValue(name_str) {
    var paramObj = iframe.document.all.item(name_str);

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

  function projectNoSearch() {
    if(!checkField(document.forms[0].projectNo, "프로젝트NO"))    {
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

  //*********************************************************************************************************************************************************
  //프로젝트 속성  가져오기 시작
  function addProcess(type, depth) {
    var form = document.forms[0];

    var tmpType = "";
    if(type==("divisioncode")) {
      tmpType = "DIVISIONCODE";
    } else if(type==("levelcode")) {
      tmpType = "LEVELCODE";
    } else if(type==("productcode")) {
      tmpType = "PRODUCTCODE";
    } else if(type==("customercode")) {
      tmpType = "CUSTOMERCODE";
    } else if(type==("devcompanycode")) {
      tmpType = "DEVCOMPANYCODE";
    } else if(type==("makecompanycode")) {
      tmpType = "MAKECOMPANYCODE";
    } else if(type==("modelcode")) {
      tmpType = "MODELCODE";
    }

    var mode = "one";
    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=1&selectedDepth="+depth+"&codetype="+tmpType+"&command=select&mode="+mode;
    returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");
    if(typeof returnValue == "undefined" || returnValue == null) {
      return;
    }
    acceptProcess(returnValue, type);
  }

  function acceptProcess(arrObj, type){
    var tmpType = "";
    var tmpType1 = "";

    if(type==("divisioncode")) {
      tmpType = "divisioncodetable";
      tmpType1 = "divisioncodeoid";
    } else if(type==("levelcode")) {
      tmpType = "levelcodetable";
      tmpType1 = "levelcodeoid";
    } else if(type==("productcode")) {
      tmpType = "productcodetable";
      tmpType1 = "productcodeoid";
    } else if(type==("customercode")) {
      tmpType = "customercodetable";
      tmpType1 = "customercodeoid";
    } else if(type==("devcompanycode")) {
      tmpType = "devcompanycodetable";
      tmpType1 = "devcompanycodeoid";
    } else if(type==("makecompanycode")) {
      tmpType = "makecompanycodetable";
      tmpType1 = "makecompanycodeoid";
    } else if(type==("modelcode")) {
      tmpType = "modelcodetable";
      tmpType1 = "modelcodeoid";
    }

    var subArr;
    var form = document.forms[0];

      for(var i = 0; i < arrObj.length; i++) {
        subArr = arrObj[i];

        if(type==("divisioncode")) {
          form.divisioncode.value = subArr[1];
          form.divisioncode.name =subArr[2];
        } else if(type==("levelcode")) {
          form.levelcode.value = subArr[1];
          form.levelcode.name =subArr[2];
        } else if(type==("productcode")) {
          form.productcode.value =subArr[1];
          form.productcode.name =subArr[2];
        } else if(type==("customercode")) {
          form.customercode.value =subArr[1];
          form.customercode.name =subArr[2];
        } else if(type==("devcompanycode")) {
          form.devcompanycode.value =subArr[1];
          form.devcompanycode.name =subArr[2];
        } else if(type==("makecompanycode")) {
          form.makecompanycode.value =subArr[1];
          form.makecompanycode.name =subArr[2];
        } else if(type==("modelcode")) {
          form.modelcode.value =subArr[1];
          form.modelcode.name =subArr[2];
        }
      }
  }
  //프로젝트 속성  가져오기 끝
  //*****************************************************************************************************************************************************************

  //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  //Project Template 가져오기 시작
  function onProjectTemplate() {
	  SearchUtil.selectOneWBSTemplate('product','acceptProjectTemplate');
    <%-- var url = "/plm/jsp/project/template/ProjectTempSelectPopUp.jsp?mode=one&wType=<%=CommonUtil.isMember("자동차사업부") ? "2" : "4"%>&type=<%=oemTypeValue%>";
    attache = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=yes; dialogWidth=800px; dialogHeight:400px; center:yes");
    if(typeof attache == "undefined" || attache == null) {
      return;
    }

    acceptProjectTemplate(attache); --%>
  }

  function acceptProjectTemplate(objArr) {
    if(objArr.length == 0) {
      return;
    }


    var targetTable = document.getElementById("templatetable");

    if(targetTable.rows.length > 1) {
      targetTable.deleteRow(1);
    }
    var trArr;
    var i=0;
    //for(var i = 0; i < objArr[objArr.length-1].length; i++) {
      tableRows = targetTable.rows.length;

      trArr = objArr[objArr.length-1];
      newTr = targetTable.insertRow(tableRows);
      //삭제
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<a href=\"javascript:;\" onClick=\"javascript:onDeleteTableRow('templatetable','templateOid','" + trArr.ProjectOid + "');\"><img src=\"/plm/portal/images/b-minus.png\"></a><input type='hidden' name='templateOid' value='" + trArr.ProjectOid + "'>";
      
      for ( var j=0; j<objArr.length; j++) {
    	  if(objArr[j]['name'] == 'productType'){
    	     newTd.innerHTML += '<input type=hidden name=wbsType value='+objArr[j].value+'>';
    	  }
    	  if(objArr[j]['name'] == 'category'){
    		  newTd.innerHTML += '<input type=hidden name=category value='+objArr[j].value+'>';
    	  }
	  }
      
      //Template 명
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.innerText = trArr.TempName;

      //기간
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL text-center";
      newTd.innerText = trArr.TempDu;

      //등록일
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL text-center";
      newTd.innerText =trArr.TempCreateDate;

      //최종수정일
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL0 text-center";
      newTd.innerText = trArr.TempModifyDate;
    //}

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
    newTd.className = "tdwhiteLtext-center";
    newTd.innerText = "<%=tempobj_duration%>";

    //등록일
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteLtext-center";
    newTd.innerText = "<%=DateUtil.getDateString(data.tempCreateDate, "D")%>";

    //최종수정일
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteL text-center";
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

/*            //삭제
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM0";
      newTd.innerHTML = "<a href=\"javascript:;\" onClick=\"javascript:onDeleteTableRow('"+tableid+"','"+membertag+"','" + infoArr[0] + "');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='"+membertag+"' value='" + infoArr[0] + "'>";
*/        }
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
/*
    var targetTable = document.getElementById("refDepttable");

    for(var i = 0; i < l_oid.length; i++) {
      loid = decodeURIComponent(l_oid[i].text);
      lname = decodeURIComponent(l_name[i].text);
      //lcode = decodeURIComponent(l_code[i].text);
      if(isDuplicateCheckBox('refDeptOid' ,loid)) {
        continue;
      }
      tableRows = targetTable.rows.length;
      newTr = targetTable.insertRow(tableRows);

      //부서
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.innerText = lname;

      //삭제
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM0";
      newTd.innerHTML = "<a href=\"javascript:;\" onClick=\"javascript:onDeleteTableRow('refDepttable', 'refDeptOid', '" + loid + "');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='refDeptOid' value='" + loid + "'>";
    }
*/
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
//            if (window.event.keyCode == 13) erpProjectNoSearch();
    } else return;

  }
  //document.onkeypress = onKeyPress;

  function doSearchPJT(oTable_id) {
    part_table_obj = document.getElementById(oTable_id);
    openWindow("/plm/jsp/project/ProjectSelectPage.jsp?isOpen=false&isModal=false&mode=one&invokeMethod=ProjectInfo", "SelectProject", 1024, 768);
  }

  function ProjectInfo(mpArr){
    var arr = mpArr[0];
    projectoid = arr[0];                    // Project OID
    projectno = arr[1];                        // 프로젝트  NO
    projectname = arr[2];                    // 프로세스
    projectModel = arr[10];                    // 모텔 차종
    projectLevel = arr[11];                    // 난이도
    projectCustomer = arr[12];                // 발주처
    projectMakecompany = arr[13];            // 생산조직
    projectProduct = arr[14];                // 제품군
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


//        addDependence(attache);

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
/*    form = document.forms[0];
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
      if(projectoidVal) {
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

        //대표모델
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
/*     var url = "/plm/jsp/project/projectType/SelectOEMMain.jsp?oemtype=CARTYPE&codetype=CUSTOMEREVENT&mode=s";
    attache = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=700px; dialogHeight:460px; center:yes");
    if(typeof attache == "undefined" || attache == null) {
      return;
    }

    addModel(rname, attache); */

  }

  function addModel(objArr) {
    if(objArr.length == 0) {
      return;
    }
    $('[name=model]').val(objArr[0][0])//id
    $('[name=tempmodel]').val(objArr[0][1])//name
  }

  //차종 가져오기 끝
  //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

  //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  //조립처 가져오기 시작

  function onCustomer() {

    var url = "/plm/jsp/common/code/SelectNumberCode.jsp?mode=multi&codetype=LastUsingBuyer";
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
  function addProcessM(type, depth, viewType) {
    var form = document.forms[0];
    var mode = "multi";
    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth="+depth+"&selectedDepth="+depth+"&codetype="+type+"&command=select&mode="+mode+"&viewType="+viewType;

    if(viewType == 'noTree')
      returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=680px; dialogHeight:500px; center:yes");
    else
      returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=860px; dialogHeight:480px; center:yes");
    if(typeof returnValue == "undefined" || returnValue == null) {
      return;
    }
    if(type == "LastUsingBuyer"){
      acceptProcessM2(returnValue, type);
    }else{
      acceptProcessM(returnValue, type);
    }
  }
  function acceptProcessM(arrObj, type){
    var subArr;
    var form = document.forms[0];

    var targetTable = document.getElementById(type);
    var subArr;
    //var chkTag = document.all.item("processoid");

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
        newTd.innerHTML = "<a href=\"javascript:;\" onClick=\"javascript:onDeleteTableRow('"+type+"', '"+type+"Oid', '" + subArr[0] + "');\"><p><img src=\"/plm/portal/images/icon_delete.gif\" border=\"0\"></p></a><input type='hidden' name='"+type+"Oid' value='" + subArr[0] + "'>";
      }

  }
  // 고객처 추가
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
        if(isDuplicateCheckBox('oid', subArr[0])) {
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
        newTd.innerHTML = "<input type=\"checkbox\" name=\"oid\" value=\"" + subArr[0] + "\"></input><input type='hidden' name='sOid' value='" + subArr[0] + "'>";

        //Code | Name
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL";
        newTd.width = 110;
        newTd.height = "100%";
        newTd.valign = "top";
        newTd.innerText = subArr[2];

        //납입처 Table
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL";
        newTd.innerHTML = "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" id=\"" + subArr[0] + "\"></table>";
      }

  }
  //
  function addProcessM2(type, depth, viewType) {
    var form = document.forms[0];
    var mode = "multi";
    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth="+depth+"&selectedDepth="+depth+"&codetype="+type+"&command=select&mode="+mode+"&viewType="+viewType;

    if(viewType == 'noTree')
      returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=680px; dialogHeight:500px; center:yes");
    else
      returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=860px; dialogHeight:500px; center:yes");
    if(typeof returnValue == "undefined" || returnValue == null) {
      return;
    }

    var tableName = "";

    len = form.oid.length;
    if(len) {
      for(var i = 0; i < len;i++) {
        if(form.oid[i].checked == true) {
          tableName = form.oid[i].value;
          //alert(tableName);
        }
      }
    }else{
      tableName = form.oid.value;
    }

    acceptProcessM3(returnValue, tableName);
  }
  //
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
    attache = window.showModalDialog(url,window,"help=no; resizable=no; status=yes; scroll=no; dialogWidth=720px; dialogHeight:500px; center:yes");
    if(typeof attache == "undefined" || attache == null) {
      return;
    }

    addProgram(attache, rowId );

    var paramObj = document.all.item("pOid"+rowId);
    if(paramObj) paramObj.value = "";
  }

  function addProgram(attache, rowId)
  {
    if(attache.length == 0) {
      return;
    }


    var productInfo = document.getElementById("productInfo");
//        productInfo.deleteRow(tablerows);
//        tableRows = productInfo.rows.length;

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


    function onDeleteTableRow2(tableid, chk_name, chk_value) {
        targetTable = document.getElementById(tableid);
        var chkTag = document.all.item(chk_name);

        var chkLen = chkTag.length;
        if ( chkLen ) {
            for ( var i = 0; i < chkLen; i++ ) {
                if ( chkTag[i].value == chk_value ) {
                    targetTable.deleteRow(i+2);

                    // partNo에 값입력
                    partNoDuplicateCheck();

                    return;
                }
            }
        }else {
            if ( chkTag.value == chk_value ) {
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

    function partNoDuplicateCheck(selectPartNo) {
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


  function deleteCal(rname) {
    document.getElementById(rname).value = "";
  }

  // 개발 검토 납입처 관련 삭제
  function onDeleteTableRow0(tableid, chk_name, chk_value) {
    targetTable = document.getElementById(tableid);
    var chkTag = document.all.item(chk_name);

    var chkLen = chkTag.length;
    if(chkLen) {
      for(var i = 0; i < chkLen; i++) {
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
      alert('<%=messageService.getString("e3ps.message.ket_message", "01698") %><%--삭제 할 고객사를 선택 하십시오--%>');
      return;
    }

    form = document.forms[0];

    len = form.oid.length;
    if(len) {
      for(var i = len - 1; i >= 0; i--) {
        if(form.oid[i].checked == true) {
          onDeleteTableRow('LastUsingBuyer', 'oid', form.oid[i].value);
        }
      }
    }else{
      onDeleteTableRow('LastUsingBuyer', 'oid', form.oid.value);
    }
  }
  // 개발 검토 납입처 관련 추가
  function onAdd(){
    if(!checkedCheck2()){
      return;
    }

    addProcessM2('SAPLastUsingBuyer', '0', 'noTree');
  }

  function checkedCheck() {
    form = document.forms[0];
    if(form.oid == null) {
      return false;
    }

    len = form.oid.length;
    if(len) {
      for(var i = 0; i < len;i++) {
        if(form.oid[i].checked == true) {
          return true;
        }
      }
    }else {
      if(form.oid.checked == true) {
        return true;
      }
    }

    return false;

  }

  function checkedCheck2() {

    form = document.forms[0];
    if(form.oid == null) {
      alert("<%=messageService.getString("e3ps.message.ket_message", "00843") %><%--고객사를 등록 하십시오--%> ");
      return false;
    }

    len = form.oid.length;
    if(len) {
      cnt = 0;
      for(var i = 0; i < len;i++) {
        if(form.oid[i].checked == true) {
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
      if(form.oid.checked == true) {
        return true;
      }
    }
    alert('<%=messageService.getString("e3ps.message.ket_message", "01170") %><%--납입처를 등록 할 고객사를 선택 하십시오--%>');

    return false;

  }

  function checkAll() {
    form = document.forms[0];
    if(form.oid == null) {
      return;
    }

    len = form.oid.length;
    if(len) {
      for(var i = 0; i < len;i++) {
        form.oid[i].checked = form.chkAll.checked;
      }
    }
    else {
      form.oid.checked = form.chkAll.checked;
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
    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&codetype=PRODUCTIONDEPT&command=select&mode="+mode+"&viewType=noTree";
    returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=680px; dialogHeight:500px; center:yes");

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
    var url="../../jsp/ews/SelectPartnerPopup.jsp?mode=single&method=linkPartner";
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

  function clearDr(){
	  document.forms[0].drNumber.value='';
	  document.forms[0].drKeyOid.value='';
	  $("#reviewPjt").multiselect("uncheckAll");
	  $("#reviewPjt").empty().data('options');
	  $("#reviewPjt").multiselect('refresh');
//	  $("#reviewPjt").append("<option value=''>선택</option>");
  }
  function selectDr(){
  //개발 검토  : developmentStep=R
  //제품     : developmentStep=D
    var url="/plm/jsp/dms/SearchDevRequestPop.jsp?method=selDr&developmentStep=D&mode=one";
    openWindow(url,"","800","600","status=1,scrollbars=no,resizable=no");
  }
  function selDr(arr){
    var param = "command=drSearch";
    for (var i = 0; i < arr.length; i++){
       param += "&drOid=" + encodeURIComponent(arr[i][0]);
    }
    var url = "/plm/jsp/project/ProjectDevRequestAction.jsp";
    postCallServer(url, param, onTargetSet, true);
  }
  
  window.getTagText = function(xmlDoc){
      return xmlDoc.textContent || xmlDoc.text || '';
  }

  var targetReviewTableId = "productInfo";
  function onTargetSet(req){

    var xmlDoc = req.responseXML;
    //var showElements = xmlDoc.selectNodes("//message");
    //var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    var msg = xmlDoc.getElementsByTagName("l_message")[0];

    if(msg == 'false' && getTagText(xmlDoc.getElementsByTagName("l_result")) != null && getTagText(xmlDoc.getElementsByTagName("l_result")[0]) != ""){
    	alert(getTagText(xmlDoc.getElementsByTagName("l_result")[0]));
      return;
    }
    if(msg == 'false') {
      alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
      return;
    }

    //var showElements = xmlDoc.selectNodes("//data_info");
    var l_drNumber = xmlDoc.getElementsByTagName("l_drNumber");
    var l_drKeyOid = xmlDoc.getElementsByTagName("l_drKeyOid");

    var l_creatorName = xmlDoc.getElementsByTagName("l_creatorName");
    var l_creatorOid = xmlDoc.getElementsByTagName("l_creatorOid");
    var l_salesUserDept = xmlDoc.getElementsByTagName("l_salesUserDept");
    var l_name = xmlDoc.getElementsByTagName("l_name");
    var l_proposalSubmitDate = xmlDoc.getElementsByTagName("l_proposalSubmitDate");
    var l_costSubmitDate = xmlDoc.getElementsByTagName("l_costSubmitDate");
    
    $("#reviewPjt").multiselect("uncheckAll");
    $("#reviewPjt").empty().data('options');
    $("#reviewPjt").multiselect('refresh');
    //$("#reviewPjt").append("<option value=''>선택</option>");
    
    var form = document.forms[0];
    form.drNumber.value = decodeURIComponent(getTagText(l_drNumber[0]));
    form.drKeyOid.value = decodeURIComponent(getTagText(l_drKeyOid[0]));

    //영업 담당자
    form.tempsales.value = decodeURIComponent(getTagText(l_creatorName[0]));
    form.sales.value = decodeURIComponent(getTagText(l_creatorOid[0]));

    //var showElements_ci = xmlDoc.selectNodes("//data_custinfo");
    
    var l_rvPjtNos = xmlDoc.getElementsByTagName("l_rvPjtNos");
    var l_rvPjtOids = xmlDoc.getElementsByTagName("l_rvPjtOids");
    
    l_rvPjtNos = decodeURIComponent(getTagText(l_rvPjtNos[0]));
    l_rvPjtOids = decodeURIComponent(getTagText(l_rvPjtOids[0]));
    
    var pjtNoArr = l_rvPjtNos.split(",").sort();
    var pjtOidArr = l_rvPjtOids.split(",").sort();
    
    for(var i=0; i<pjtNoArr.length; i++){
    	
        $("#reviewPjt").append("<option value='"+pjtNoArr[i]+"'>"+ pjtNoArr[i] +"</option>");
        
    }
    $("#reviewPjt").multiselect('refresh');
    var l_custOid = xmlDoc.getElementsByTagName("l_custOid");
    var l_custName = xmlDoc.getElementsByTagName("l_custName");

    // 최종 사용처
    var targetTable = document.getElementById("CUSTOMEREVENTOid");
    var subOid = false;
    var subName = false;
    var fm = document.prodForm;
    var pos = fm.CUSTOMEREVENTOid.length;
    for(var i = 0; i < l_custOid.length; i++){
        for(var j = 0; j < pos; j++) {
          if(fm.CUSTOMEREVENTOid.options[j].value==getTagText(l_custOid[i])){
            alert(l_subName[i].text+"는 이미 존재하는 최종 사용처입니다");
            return;
          }
        }
        
        fm.CUSTOMEREVENTOid.length = pos+i+1;
        fm.CUSTOMEREVENTOid.options[pos+i].value = getTagText(l_custOid[i]);
        fm.CUSTOMEREVENTOid.options[pos+i].text = getTagText(l_custName[i]);
        fm.CUSTOMEREVENTOid.options[pos+i].selected = true;
    }

    //고객처
    //var showElements_si = xmlDoc.selectNodes("//data_subinfo");
    var l_subOid = xmlDoc.getElementsByTagName("l_subOid");
    var l_subName = xmlDoc.getElementsByTagName("l_subName");

    var targetTable2 = document.getElementById("sOid");
    //deleteLastUsingBuyer();

    var subOid = false;
    var subName = false;
    var fm = document.prodForm;
    var pos = fm.sOid.length;
    for(var i = 0; i < l_subOid.length; i++){
        //subArr = arrObj[i];
      
        for(var j = 0; j < pos; j++) {
          if(fm.sOid.options[j].value==getTagText(l_subOid[i])){
            alert(l_subName[i].text+"는 이미 존재하는 고객처입니다");
            return;
          }
        }
        
        fm.sOid.length = pos+i+1;
        fm.sOid.options[pos+i].value = getTagText(l_subOid[i]);
        fm.sOid.options[pos+i].text = getTagText(l_subName[i]);
        fm.sOid.options[pos+i].selected = true;
    }
    
    
    
    

  }

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

    // 사용안함 -- 나중에 변경될경우지몰라서 삭제하지 않음
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
    
    // ==  [Start] 사람 검색  == //
    function delUser(targetId) {
        $("#" + targetId).val("");
        $("#temp" + targetId).val("");
    }

    function addUser(targetId) {
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=s";
        acceptUsers = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
        if ( typeof acceptUsers == "undefined" || acceptUsers == null ) {
            return;
        }
        // isAppend : Y - 이전 값에 현재 선택된 값 추가
        // isAppend : N - 현재 선택 된 값만 추가
        var isAppend = "N";
        acceptUser(targetId, acceptUsers, isAppend);
    }

    function acceptUser(targetId, arrObj, isAppend) {
        var userId = new Array();     // Id
        var userName = new Array();   // Nmae
        for ( var i=0; i < arrObj.length; i++ ) {
            // [0] - wtuser oid // [1] - people oid // [2] - dept oid
            // [3] - user id    // [4] - name       // [5] - dept name
            // [6] - duty       // [7] - duty code  // [8] - email

            var infoArr = arrObj[i];
            userId[i] = infoArr[0];
            userName[i] = infoArr[4];
        }

        var tmpId = new Array();
        var tmpName = new Array();
        if ( $("#" + targetId).val() != "" && isAppend == "Y" ) {
            // ID 중복 제거
            tmpId = $.merge($("#" + targetId).val().split(", "), userId);
            tmpId = $.unique(tmpId.sort());

            tmpName = $.merge($("#temp" + targetId).val().split(", "), userName);
            tmpName = $.unique(tmpName.sort());
        }
        else {
            tmpId = userId.sort();
            tmpName = userName.sort();
        }

        $("#" + targetId).val(tmpId.join(", "));
        $("#temp" + targetId).val(tmpName.join(", "));
    }
    // ==  [End] 사람 검색  == //
    
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
    	$('[name=CUSTOMEREVENTOid] option[value!="0"]').remove();
        /* var fm = document.prodForm;
        while(fm.CUSTOMEREVENTOid.selectedIndex>=0){
          if((fm.CUSTOMEREVENTOid.length>0)&&(fm.CUSTOMEREVENTOid.selectedIndex>=0)){
            var pos = fm.CUSTOMEREVENTOid.selectedIndex;
            fm.CUSTOMEREVENTOid.remove(pos);
          }
        } */
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
    	$('[name=sOid] option[value!="0"]').remove();
      /* var fm = document.prodForm;

      while(fm.sOid.selectedIndex>=0){
        if((fm.sOid.length>0)&&(fm.sOid.selectedIndex>=0)){
          var pos = fm.sOid.selectedIndex;
          fm.sOid.remove(pos);
        }
      } */

    }
    
    $(document).ready(function(){
    	
    	$("#reviewPjt").multiselect({
            minWidth: 80,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
    	
    	//대표차종
    	SuggestUtil.bind('CARTYPE', 'tempmodel', 'model');
    	//PM suggest
        SuggestUtil.multiBind('USERDEPT', 'tempdevManager', 'devManager', null, 'department');
    	//영업담당자 suggest
        SuggestUtil.bind('USER', 'tempsales', 'sales');
    	
        
        SuggestUtil.bind('PRODUCTPROJNO', 'pjtno', 'linkpjtoid');
        
        CalendarUtil.dateInputFormat('planStartDate');
        
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
//-->

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

function processChange(){
	if ( $("#process").val() != null ) {
		var choiceCode  = $("#process").val();
		if(choiceCode == 'PC002' || choiceCode == 'PC005'){//Pilot 또는 양산 프로젝트만 연계프로젝트 기입이 가능하다 박상수 차장 요청 2020.10.13
			$("#linkPjtTR").show();
		}else{
			$("#linkPjtTR").hide();
			$("#linkpjtoid").val("");
			$("#pjtno").val("");
		}
	}
}

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

function setPartClaz(returnValue){
	$('[name=productTypeLevel2]').val(returnValue[0].id);//oid
	$('[name=productTypeLevel2Name]').val(returnValue[0].name);//kr name
}

</script>

</head>

<%if(tempOid.length() > 0){ %>
<body onload="javascript:loadTemp();">
<%}else{ %>
<body>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
    <%} %>
    <%@include file="/jsp/common/processing.html"%>
    <form name="prodForm" action="/plm/jsp/project/ProductProjectAjaxAction.jsp" method="post">
        <!-- hidden begin -->
        <input type="hidden" name="initType" value="<%=initType%>"> 
        <input type="hidden" name="tempProjectNo" value=""> 
        <input type="hidden" name="lifecycle" value=""> 
        <input type="hidden" name="pmoid" value="<%=pmoid%>"> 
        <input type="hidden" name="pwlinkOid" value="<%=pwlinkOid%>"> 
        <input type="hidden" name="isPM" value="true"> 
        <input type="hidden" name="command" value="Create">
        <input type="hidden" name="department">
        <input type="hidden" name="developTyped">
        
        <!-- hidden end -->

        <div style="display: none">
            <input type="submit" name="aaaaa" value="bbbbbbb"> 
            <input type="hidden" name="command" value='create'> 
            <input type="hidden" id="uploadResponseType" name="mimetype" value="xml"> 
            <input type="submit" name="xxxxxxxxxxx" value="Submit" />
        </div>
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top" height="43">
                    <table style="width: 100%;">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png">
                                <table style="height: 28px;">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02549")%><%--제품 프로젝트 등록--%></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td class="space5"></td>
            </tr>
            <tr>
                <td valign="top">
                    <table width="100%" height="20" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></td>
                            <td align="right">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td class="font_03">&nbsp;<input type="hidden" name="pjtno1" class="txt_field" id="pjtno1"
                                            style="width: 180"></td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onClick="onSave()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310")%><%--등록--%></a>
                                        </td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:window.close();" class="btn_blue">취소</a></td>
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
                        <%
                        String teamType = "";
                        if (CommonUtil.isMember("KETS_PMO")) {
                        %>
                        <input type="hidden" name="teamType" value="KETS">
                        <input type="hidden" name="projectType" value="5">
                        <%}else{ %>
                        <tr>
                            <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00659")%><%--개발의뢰번호--%></td>
                            <td class="tdwhiteL"><input type="text" name="drNumber" class="txt_fieldRO" style="width: 60%"
                                id="drNumber" readonly> <input type=hidden name="drKeyOid" value=""> 
                                <a href="javascript:selectDr();"><img src="/plm/portal/images/icon_5.png" border="0"></a> 
                                <a href="javascript:clearDr();"><img
                                    src="/plm/portal/images/icon_delete.gif" border="0"></a>       
                             </td>
                            <td class="tdblueL">검토프로젝트<span class="red">*</span></td>
                            <td class="tdwhiteL0">
                            <select name="reviewPjt" id="reviewPjt" class="fm_jmp" style="width: 140px;" multiple="multiple"></select>
                            </td>
                        </tr>
                        <%} %>
                        <tr>
                            <td class="tdblueL">관리 제품군<span class="red">*</span></td>
                            <td class="tdwhiteL0">
                                <select name="manageProduct" id="manageProduct" class="fm_jmp" style="width: 99%">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <%
                                        parameter.clear();
                                        parameter.put("locale", messageService.getLocale());
                                        parameter.put("codeType", "MANAGEPRODUCTTYPE");
                                        parameter.put("description", teamType);
                                        numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                                        for (int i = 0; i < numCode.size(); i++) {
                                    %>
                                    <option value="<%=numCode.get(i).get("code")%>"><%=numCode.get(i).get("value")%></option>
                                    <%
                                        }
                                    %>
                            </select>
                            </td>
                            <td width="140" class="tdblueL">
                                <%
                                WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
                                PeopleData pd = new PeopleData(sessionUser);
                                if (CommonUtil.isMember("자동차사업부")) {
                                %> 
                                <%=messageService.getString("e3ps.message.ket_message", "01662")%><%--사업부--%>
                                <%
                                } else {
                                %> 
                                      전자개발구분<%--전자개발구분--%>
                                <span class="red"> <%
                                }
                                %>
                            </td>
                            <td class="tdwhiteL0">
                                <%
                                
                                if (CommonUtil.isMember("자동차사업부")) {
                                        teamType = "자동차";
                                %>
                                <%=messageService.getString("e3ps.message.ket_message", "02401")%><%--자동차사업부--%> 
                                <input type="hidden" name="teamType" value="자동차 사업부"> 
                                <input type="hidden" name="projectType" value="2"> 
                                <%
                                } else if (CommonUtil.isMember("전자사업부")) {
                                    teamType = "전자";
                                    PeopleData peoData = new PeopleData(sessionUser);
                                    String deptName = peoData.departmentName;
                                %>
                                <input type="hidden" name="teamType" value="전자 사업부">
                                <input type="hidden" name="projectType" value="4"> 
                                <select name="itDivision" style="width:98%" class="fm_jmp">
                                    <option value="it" <%=(deptName.startsWith("전장IT")?" selected":"") %>>전장IT개발</option>
                                    <option value="dev" <%=(deptName.startsWith("전장IT")?"":" selected") %>>전자개발</option>
                                </select> 
                                <%
                                }
                                %>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdblueL">개발목적<span class="red">*</span></td>
                            <td class="tdwhiteL0">
                                <select name="developePurpose1" id="developePurpose1" class="fm_jmp" style="width: 98%" onChange="developePurposeChange();">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <%
                                        parameter.clear();
                                        parameter.put("locale", messageService.getLocale());
                                        parameter.put("codeType", "DEVELOPANDREVIEWGOAL");
                                        numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                                        for (int i = 0; i < numCode.size(); i++) {
                                    %>
                                    <option value="<%=numCode.get(i).get("code")%>"><%=numCode.get(i).get("value")%></option>
                                    <%
                                        }
                                    %>
                            </select>
                            </td>
                            <td class="tdwhiteL0" colspan="2">
                                <select name="developePurpose2" id="developePurpose2" class="fm_jmp" style="width: 98%">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                            </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578")%><%--제품구분--%>
                                <span class="red">*</span></td>
                            <td class="tdwhiteL">
                                <input type="text" name="productTypeLevel2Name" class="txt_field" style="width: 70%">
                                <input type="hidden" name="productTypeLevel2" value="">
                                <a href="javascript:SearchUtil.selectOnePartClazPopUp('setPartClaz','onlyLeaf=Y&openAll=N&depthLevel2=Y');">
                                <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                                <a href="javascript:CommonUtil.deleteValue('productTypeLevel2','productTypeLevel2Name');">
                                <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                            </td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629")%><%--개발단계--%><span class="red">*</span></td>
                            <td class="tdwhiteL0">
                                <select name="process" id="process" class="fm_jmp" style="width: 98%" onChange="processChange();">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <%
                                        parameter.clear();
                                        parameter.put("locale", messageService.getLocale());
                                        parameter.put("codeType", "PROCESS");
                                        numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                                        for (int i = 0; i < numCode.size(); i++) {
                                    %>
                                    <option value="<%=numCode.get(i).get("code")%>"><%=numCode.get(i).get("value")%></option>
                                    <%
                                        }
                                    %>
                            </select></td>
                        </tr>
                        <tr id="linkPjtTR" style="display: none">
                        <td width="140" class="tdblueL">연계프로젝트</td>
                            <td class="tdwhiteL" colspan="3">
                                <input type="text" name="pjtno" id="pjtno" class="txt_field" style="width:26%" value="">
                                <input type="hidden" name="linkpjtoid" id="linkpjtoid" value="">
                                <a href="javascript:setProjectNo();"><img src="/plm/portal/images/icon_5.png" border="0"></a> 
                                <a href="javascript:CommonUtil.deleteValue('pjtno','linkpjtoid');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02139")%><%--영업담당자--%></td>
                            <td class="tdwhiteL"><input id="tempsales" type="text" name="tempsales" class="txt_field" style="width: 70%">
                                <input type="hidden" id="sales" name="sales"> <a href="javascript:addRoleMember('sales');"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                                <a href="javascript:deleteRoleMember('sales');"><img
                                    src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00370")%><%-- PM --%>
                                <span class="red">*</span></td>
                            <td class="tdwhiteL0">
                                <input style="width: 70%;" type="text" name="tempdevManager" class="txt_field"> 
                                <input type="hidden" name="devManager"> 
                                <a href="javascript:addRoleMember('devManager');"><img src="/plm/portal/images/icon_user.gif"></a> 
                                <a href="javascript:CommonUtil.deleteValue('devManager','tempdevManager','department');"><img src="/plm/portal/images/icon_delete.gif"></a></td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113")%><!-- 프로젝트명 -->
                                <span class="red">*</span></td>
                            <td colspan=3 class="tdwhiteL0"><input type="text" name="projectName" class="txt_field"
                                style="width: 98%" /></td>
                        </tr>
                        <tr>
                            <td class="tdblueL">Rank<span class="red">*</span></td>
                            <td class="tdwhiteL">
                                <select name="rank" class="fm_jmp" style="width: 98%">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <%
                                        parameter.clear();
                                        parameter.put("locale", messageService.getLocale());
                                        parameter.put("codeType", "RANK");
                                        parameter.put("code",     "RAN1000");
                                        parameter.put("depthLevel", "child");
                                        numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                                        for (int i = 0; i < numCode.size(); i++) {
                                    %>
                                    <option value="<%=numCode.get(i).get("code")%>"><%=numCode.get(i).get("value")%></option>
                                    <%
                                        }
                                    %>
                            </select></td>
                            <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "01248")%><%--대표차종--%>
                                <span class="red">*</span>
                            </td>
                            <td class="tdwhiteL0">
                                <div id="modelDiv">
                                    <input type="text" id="tempmodel" name="tempmodel" class="txt_field" style="width: 70%"> 
                                    <input type="hidden" id="model" name="model" value="">
                                    <a href="javascript:onModel();"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                                    <a href="javascript:CommonUtil.deleteValue('tempModel','model');">
                                    <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00625")%><%--개발구분--%>
                                <span class="red">*</span></td>
                            <td class="tdwhiteL"><select name="developentType" id="developentType" class="fm_jmp" style="width: 98%">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <%
                                        parameter.clear();
                                        parameter.put("locale", messageService.getLocale());
                                        parameter.put("codeType", "DEVELOPENTTYPE");
                                        numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                                        for (int i = 0; i < numCode.size(); i++) {
                                            if( !"확대적용".equals(numCode.get(i).get("value"))){//확대적용은 영업수주프로젝트에서만 사용
                                    %>
                                    <option value="<%=numCode.get(i).get("code")%>"><%=numCode.get(i).get("value")%></option>
                                    <%
                                            }
                                        }
                                    %>
                                    <option value="-">-</option>
                            </select></td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01843")%><%--설계구분--%></td>
                            <td class="tdwhiteL0"><select name="designType" class="fm_jmp" style="width: 98%">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <%
                                        parameter.clear();
                                        parameter.put("locale", messageService.getLocale());
                                        parameter.put("codeType", "DESIGNTYPE");
                                        numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                                        for (int i = 0; i < numCode.size(); i++) {
                                    %>
                                    <option value="<%=numCode.get(i).get("code")%>"><%=numCode.get(i).get("value")%></option>
                                    <%
                                        }
                                    %>
                            </select></td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847")%><%--최종사용처--%></td>
                            <td class="tdwhiteL">
                                <select name="CUSTOMEREVENTOid" style="width: 76%;" size=2 multiple></select>
                                <a href="javascript:insertLastUsingBuyer()"><img src="../../portal/images/icon_5.png" border="0" align="top"></a> 
                                <a href="javascript:deleteLastUsingBuyer()"><img src="../../portal/images/icon_delete.gif" border="0" align="top"></a></td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859")%><%--고객처--%></td>
                            <td class="tdwhiteL0">
                                <select name="sOid" style="width: 76%;" size=2 multiple></select>  
                                <a href="javascript:insertRequestBuyer()"><img src="../../portal/images/icon_5.png" border="0" align="top"></a>
                                <a href="javascript:deleteRequestBuyer()"><img src="../../portal/images/icon_delete.gif" border="0" align="top"></a></td>
                        </tr>
                        <tr>
                            <td class="tdblueL">계획시작일<span class="red">*</span></td>
                            <td class="tdwhiteL"><input type="text" name="planStartDate" class="txt_field"
                                style="width: 100px"> <img src="/plm/portal/images/icon_delete.gif" border="0"
                                onclick="javascript:CommonUtil.deleteDateValue('planStartDate');" style="cursor: hand;"></td>
                            <td class="tdblueL">수주관리<span class="red">*</span></td>
                            <td class="tdwhiteL0"><select name="obtainType" class="fm_jmp" style="width: 98%">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <%
                                        parameter.clear();
                                        parameter.put("locale", messageService.getLocale());
                                        parameter.put("codeType", "OBTAINORDERTYPE");
                                        numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                                        for (int i = 0; i < numCode.size(); i++) {
                                    %>
                                    <option value="<%=numCode.get(i).get("code")%>"><%=numCode.get(i).get("value")%></option>
                                    <%
                                        }
                                    %>
                            </select></td>
                        </tr>
                        
                        <tr>
	                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00653","")%><%--개발유형--%></td>
	                        <td colspan="3" class="tdwhiteL0">
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
						<!-- <tr>
							<td class="tdblueL">파생차종 <a
								href="javascript:SearchUtil.selectCarTypeMulti(setOEMTypes);"><img
									src="/plm/portal/images/icon_5.png" border="0"></a>
							</td>
							<td colspan="3" class="tdwhiteL0">
                                <div id="OEMTYPELIST"></div>
							</td>
						</tr> -->
					</table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space15"></td>
                        </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03">WBS<span class="red">*</span></td>
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
                    <table border="0" cellspacing="0" cellpadding="0" width="100%" id="templatetable">
                        <tr>
                            <td width="30" class="tdblueM"><a href="javascript:onProjectTemplate()"><img src="/plm/portal/images/b-plus.png"></a></td>
                            <td width=* class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00555") %><%--WBS 명--%></td>
                            <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                            <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01335") %><%--등록일--%></td>
                            <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02852") %><%--최종수정일--%></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space15"></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
