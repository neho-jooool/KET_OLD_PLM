<%@page import="ext.ket.dms.service.KETProjectDocumentHelper"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.dms.beans.DMSUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.fc.*"%>
<%@page import = "wt.org.*"%>
<%@page import = "e3ps.access.*,e3ps.access.service.*,e3ps.project.beans.*"%>
<%@page import = "e3ps.common.util.*"%>
<%@page import = "e3ps.groupware.company.*"%>
<%@page import = "e3ps.project.*"%>
<%@page import="e3ps.project.outputtype.ProjectOutPutType"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="e3ps.dms.entity.KETDocumentCategory"%>
<%@page import="e3ps.dms.service.KETDmsHelper"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<html>
<%
    ProjectOutput output = null;
    TemplateTask task = null;
    TemplateProject project = null;
    E3PSTask E3PSTask = null;

    String oid = request.getParameter("oid");
    String taskOid = request.getParameter("taskOid");
    String fromPage = request.getParameter("fromPage");
    String selectTrustOid = request.getParameter("selectTrustOid");
    String selectAssessOid = request.getParameter("selectAssessOid");
    String projectOid = "";
    String objType ="DOC";
    String myoutputtypeName ="";
    String taskType = "";

    boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
    boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;                      //PLM Admin

    TaskViewButtonAuth buttonAuth = null;

    if(oid == null) {
        oid = "";
    }

    if(taskOid == null) {
        taskOid = "";
    }

    if(fromPage == null) {
        fromPage = "";
    }

    if(!taskOid.equals("")){
        E3PSTask = (E3PSTask)CommonUtil.getObject(taskOid);
    }
    
    String taskName = E3PSTask.getTaskName();

    if(E3PSTask != null){
	   buttonAuth = new TaskViewButtonAuth(E3PSTask);
	   taskType = E3PSTask.getTaskType();
    }

    ReferenceFactory rf = new ReferenceFactory();
    ProjectOutPutType myoutputtype = null;

    boolean isPrimary = true;
    if(oid.length() > 0) {
        output = (ProjectOutput)rf.getReference(oid).getObject();
        task = output.getTask();
        isPrimary = output.isIsPrimary();
        project = output.getProject();
        objType = output.getObjType();
        if(objType == null){
            objType = "DOC";
        }
        myoutputtype = output.getOutputType();
        if (myoutputtype != null){
            myoutputtypeName = myoutputtype.getName();
        }

        buttonAuth = new TaskViewButtonAuth((E3PSTask)task);
    }

    if(output == null && taskOid.length() > 0) {
        task = (TemplateTask)rf.getReference(taskOid).getObject();
        project = task.getProject();

    }
    ProjectOutPutType outputtype = project.getOutputType();


    String name = "";
    String description = "";
    String role = "";
    String location = "";
    String userName = "";
    String userOid = "";
    String category2Value = "";
    String category3Value = "";
    String subjectType = "";
    String templateName = "";

    
    String costReview1 = "";
    String costReview2 = "";
    String costFinal1 = "";
    String costFinal2 = "";
    String gateCheckType_arr = "";
    
    if(output != null) {
        ProjectOutputData data = new ProjectOutputData(output);
        name = data.name;
        description = data.description;
        role = data.role_en==null? "":data.role_en;
        location = data.location;
        subjectType = data.subjectType;
        templateName = data.outputDocName;
        gateCheckType_arr = StringUtil.checkNull(output.getGateCheckType()); 
        
        
        if(objType.equals("COST")){
            costReview1 = data.totalCost;
            costFinal1 = data.totalCostFinal;
            costReview2 = data.rate;
            costFinal2 = data.rateFinal;
        }else if(objType.equals("SALES")){
            costReview1 = data.salesTarget;
            costFinal1 = data.salesTargetFinal;
            costReview2 = data.yearAverageQty;
            costFinal2 = data.yearAverageQtyFinal;
        }

        //WTUser chargeUser = data.registryUser;
        if(location != null && location.length() > 0) {
             StringTokenizer st = new StringTokenizer(location, "/");
             int i = 1;
             while (st.hasMoreTokens()) {
                 if(i == 2) category2Value = st.nextToken();
                 else if(i == 3) category3Value = st.nextToken();
                 else st.nextToken();
                 i++;
             }
        	 KETDocumentCategory kdct =  KETProjectDocumentHelper.manager.getDocumentCategory(output.getOutputKeyType());
             category3Value = kdct.getCategoryName();
        }
    }

    boolean isE3PSTask = false;
    if(task instanceof E3PSTask) {
        isE3PSTask = true;
    }

    //최초 분류체계 중 개발산출문서를 선택하여 화면에 나타낸다.
    String categoryCode = null;
    String docCatePath = null;
    String categoryName = messageService.getString("e3ps.message.ket_message", "01445")/*미등록되어 있습니다*/;
    String docTypeCode = null;
    String isDRCheckSheet = null;
    String tmpStr = null;

    KETDocumentCategory docCate = null;

    Map<String, Object> parameter = new HashMap<String, Object>();
    // 1 Level 분류체계 찾기
    parameter.clear();
    parameter.put("docTypeCode", "PD");
    parameter.put("locale",      messageService.getLocale().toString());
    parameter.put("parentCode",  "ROOT");

    List<Map<String, Object>> categoryList = DMSUtil.getDocumentCategory(parameter);
    if ( categoryList.size() > 0 ) {
        categoryCode = categoryList.get(0).get("categoryCode").toString();
        categoryName = categoryList.get(0).get("categoryName").toString();
    }
    
    boolean isProductProject = false;
    
    if(project instanceof ProductProject ){
	   isProductProject = true;
    }
    
    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
    
    boolean isTaskMaster = false;
    //사용자가 Task의 책임자인지 확인
    QueryResult masterList = TaskUserHelper.manager.getMaster(E3PSTask);
    while (masterList.hasMoreElements()) {
    Object o[] = (Object[]) masterList.nextElement();
    TaskMemberLink link = (TaskMemberLink) o[0];
    
    PeopleData data = new PeopleData(link.getMember().getMember());
        if (wtuser.getName().equals(data.id)) {
            isTaskMaster = true;
            break;
        }
    }
    
    //사용자가 Task의 참여자인지 확인
    boolean isTaskMember = false;
    QueryResult memberlist = TaskUserHelper.manager.getMember(E3PSTask);
    if (memberlist != null) {
        while (memberlist.hasMoreElements()) {
        Object o[] = (Object[]) memberlist.nextElement();
        TaskMemberLink link = (TaskMemberLink) o[0];
        PeopleData data = new PeopleData(link.getMember().getMember());
        //         if(buttonAuth != null && buttonAuth.isTaskMemberModify || isAdmin){
            if (wtuser.equals(data.wtuser)) {
                isTaskMember = true;
                break;
            }
        //         }
        }
    }
    
    boolean isEdit = isTaskMaster || isTaskMember;
%>

<head>
<%@include file="/jsp/common/top_include.jsp" %>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
<!--
.style1 {
        color: #FF0000
}
-->
</style>
<script language='javascript'>
var tempdata;
var isDrCheck;
//SAVE ACTION BEGIN @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
function onSave() {

    form = document.forms[0];

    if(form.objType.value=="DOC"){
        var doctypeid;

        var param = "command=subcheck&oid=" + doctypeid;
        if(form.category3.value == ''){
          alert('<%=messageService.getString("e3ps.message.ket_message", "01398") %><%--문서 위치를 선택 하십시오--%>');
            return;
        }
        
        var checkText = fm.category3.options[fm.category3.selectedIndex].text;
        
        //DR 타스크인 경우, 분류체계가 DR로 시작하는 경우, DR 점수입력이 가능한 문서분류체계만 선택이 가능하도록.. DR점수없는 분류체계 선택하는 실수방지차원 2016.07.27 황정태
        if(form.taskType.value == 'DR' && isDrCheck != 'true' && 'DR' == checkText.toUpperCase().match('DR')){
        	alert("DR점수를 입력할 수 없는 문서입니다.");
        	return;
        }
        var code = fm.category3.options[fm.category3.selectedIndex].value;
        var msg = checkDisabledCategory(code);

        if(msg != ''){
            alert(msg);
            return;
        }
        
        
        var url = "/plm/jsp/project/template/DocTypeAjaxAction.jsp?" + param;
        callServer(url, onSaveAction);
    }else{
    	var taskName = form.taskName.value;
    	var goFlag = '<%=isProductProject%>';
    	
    	if(goFlag == 'true'){
    		if((taskName == '원가산출' || taskName == '원가산출 보고서') && form.objType.value != 'COST'){
                alert("산출물 타입은 반드시 [원가] 를 선택하여야합니다.");
                return;
            }
            if((taskName == '판매목표가 등록') && form.objType.value != 'SALES'){
                alert("산출물 타입은 반드시 [판가] 를 선택하여야합니다.");
                return;
            }
            
            var costReview1 = form.costReview1.value;
            var costReview2 = form.costReview2.value;
            var costFinal1 = form.costFinal1.value;
            var costFinal2 = form.costFinal2.value;
            
            if(form.objType.value == 'COST'){
                if(costReview1 == ''){
                    alert("검토 총원가를 입력하세요");
                    form.costReview1.focus();
                    return;
                }
                if(costReview2 == ''){
                    alert("검토 수익율을 입력하세요");
                    form.costReview2.focus();
                    return;
                }
                if(costFinal1 == ''){
                    alert("최종 총원가를 입력하세요");
                    form.costFinal1.focus();
                    return;
                }
                if(costFinal2 == ''){
                    alert("최종 수익율을 입력하세요");
                    form.costFinal2.focus();
                    return;
                }
            }
            
            if(form.objType.value == 'SALES'){
                if(costReview1 == ''){
                    alert("검토 판매목표가를 입력하세요");
                    form.costReview1.focus();
                    return;
                }
                if(costReview2 == ''){
                    alert("검토 연평균수량을 입력하세요");
                    form.costReview2.focus();
                    return;
                }
                if(costFinal1 == ''){
                    alert("최종 판매목표가를 입력하세요");
                    form.costFinal1.focus();
                    return;
                }
                if(costFinal2 == ''){
                    alert("최종 연평균수량을 입력하세요");
                    form.costFinal1.focus();
                    return;
                }
            }
            if(form.objType.value == 'COST' || form.objType.value == 'SALES'){
                var msg = "숫자형식만 입력가능합니다.";
                
                if(!onlyNumber(costReview1)){
                    form.costReview1.focus();
                    alert(msg);
                    return;
                }
                if(!onlyNumber(costReview2)){
                    form.costReview2.focus();
                    alert(msg);
                    return;
                }
                if(!onlyNumber(costFinal1)){
                    form.costFinal1.focus();
                    alert(msg);
                    return;
                }
                if(!onlyNumber(costFinal2)){
                    form.costFinal1.focus();
                    alert(msg);
                    return;
                }
            }
    	}

        doSubmit();
    }
}

function checkDisabledCategory(categoryCode){
    var rtn = '';
    $.ajax({
        url : "/plm/ext/dms/isDisabledCategory.do",
        type : "POST",
        data : {
            devDocCagegoryCode : categoryCode
        },
        dataType : 'json',
        async : false,
        success : function(data) {
            rtn = data;
        }
    });
    return rtn;
}

function onSaveAction(req) {
    var xmlDoc = req.responseXML;
    var msg = xmlDoc.getElementsByTagName("l_message")[0];

    if(msg == 'true') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03143") %><%--하위문서분류를 선택하시기 바랍니다--%>');
        return;
    }
    doSubmit();
}


function doSubmit() {
    form = document.forms[0];
    if(form.name.value == '') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01434") %><%--문서제목을 입력하시기 바랍니다--%>');
        form.name.focus();
        return;
    }

    if(form.objType.value=="DOC"){
        for(i=0; i<form.category3.length; i++) {
            if(form.category3[i].selected == true) {
                form.docTypeOid.value = form.category3[i].value.substring(0,8);
            }
        }
    }

    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "01894") %><%--산출물 정의를 저장하시겠습니까?--%>")){
        return;
    }
    
    
    var gateCheckType = "";
    
    if(form.subjectType.value == '대상'){//추가 Gate점검 대상 세팅
    	for(i=0; i<form.gateCheckType.length; i++) {
            if(form.gateCheckType[i].checked){
                gateCheckType += form.gateCheckType[i].value+',';
            }
        }
    }else{
    	gateCheckType = "";
    }

    var tid = form.taskOid.value;
    var opid = form.oid.value;
    var opUser = "";


    var viewUserIds = getParamValue("userOid");
    var viewDeptIds = getParamValue("deptOid");
    //onProgressBar();
    var param = "command=addOutput";
    param += "&taskOid=" + tid;
    param += "&oid=" + opid;
    param += "&docTypeOid=" + form.docTypeOid.value;
    param += "&name=" + escape(encodeURIComponent(form.name.value));
    param += "&description=" + escape(encodeURIComponent(form.description.value));
    param += "&outputUser=" + opUser;
    param += "&docOid=" + form.docOid.value;
    param += "&docName=" + form.docName.value;
    param += "&selectTrustOid=" + form.selectTrustOid.value;
    param += "&selectAssessOid=" + form.selectAssessOid.value;
    param += "&subjectType=" + form.subjectType.value;
    param += "&costReview1=" + form.costReview1.value;
    param += "&costReview2=" + form.costReview2.value;
    param += "&costFinal1=" + form.costFinal1.value;
    param += "&costFinal2=" + form.costFinal2.value;
    param += "&gateCheckType=" + gateCheckType;
    
    /* 추가  */
    param += "&objType=" + form.objType.value;
    if(form.isPrimary[0].checked) {
        param += "&isPrimary=" + form.isPrimary[0].value;
    }else {
        param += "&isPrimary=" + form.isPrimary[1].value;
    }
    if(viewUserIds.length > 0) {
        param += "&" + viewUserIds;
    }
    if(viewDeptIds.length > 0) {
        param += "&" + viewDeptIds;
    }
    
    //alert(form.docOid.value + ' ' +form.subjectType.value);
    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
    window.returnValue = true;
    postCallServer(url, param, onMessage, false);
    //callServer(url, onMessage);
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


function onMessage(req) {
    var xmlDoc = req.responseXML;
    var msg = xmlDoc.getElementsByTagName("l_message")[0].textContent || xmlDoc.getElementsByTagName("l_message")[0].text || '';
    if(msg == 'true') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02780") %><%--처리 완료했습니다--%>');
        //opener.location.reload();
		opener.parent.document.location.href="/plm/jsp/project/ProjectViewFrm.jsp?oid=<%=taskOid%>&popup=popup"; 

    } else {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
        return;
    }

    window.returnValue = true;
    window.close();
}
//SAVE ACTION END @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


//Document Category Ajax
function numCodeAjax(docTypeCode, parentCode, targetId) {
    $.ajax( {
        url : "/plm/servlet/e3ps/DocumentCategoryAjax",
        type : "POST",
        data : {docTypeCode:docTypeCode, parentCode:parentCode},
        dataType : 'json',
        async : false,
        success: function(data) {
            $.each(data.returnObj, function() {
                $("#"+targetId).append("<option value='"+this.categoryCode+"'>"+ this.categoryName +"</option>");
            });
            tempdata = data;
        }
    });
}

function changeCategory2(){
//2단계 분류체계변경시 CateSelect.jsp를 호출하여 3단계를 화면에 나타내준다.

    var fm = document.forms[0];
    fm.category3.length = 1;
    fm.category3.selectedIndex = 0;
//     var obj = document.getElementById('dummy');
//     obj.src = "/plm/jsp/dms/CateSelect.jsp?oid=" + fm.category2.options[fm.category2.selectedIndex].value;

//     $("#category3").empty().data('options');

    //2단계 분류체계변경시 CateSelect.jsp를 호출하여 3단계를 화면에 나타내준다.
    numCodeAjax("PD", $("#category2").val(), "category3");
}

function changeCategory3(){
//3단계 분류체계변경시 선택된 분류체계를 categoryCode에 저장하고 DR평가점수 필요여부를 체크한다.
    var fm = document.forms[0];
    var tStr = fm.category3.options[fm.category3.selectedIndex].value;
    
    $.each(tempdata.returnObj, function() {
        if(tStr == this.categoryCode){
        	isDrCheck = this.isDrCheck;
        }
    });
}

function standardSearchPopup(){
    var url="/plm/jsp/dms/SearchStandardDoc.jsp?popup=yes&invokeMethod=setStandardForm";
    var modalHeight = "700px";
    var modalWidth ="1060px";
    
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    
    getOpenWindow2(url,"<%=taskOid%>", 1060, 700, opts);
    
}

function setStandardForm(docOid, docName){
    $("#docOid").val(docOid);
    $("#docName").val(docName);
    $("#tmpName").val(docName);
} 

function onlyNumber(num){
	num = String(num).replace(/^\s+|\s+$/g, "");
	var regex = /^[-]?(([1-9][0-9]{0,2}([0-9]{3})*)|[0-9]+){1}(\.[0-9]+)?$/g;
	
	if( regex.test(num) ){
		num = num.replace(/,/g, "");

	    return isNaN(num) ? false : true;

	}else{ return false;  }


	
/*     //방향키 인경우는 제외 처리(글쓰다가 앞으로 다시 안가지는 경우때문)
    if (event.keyCode >= 37 && event.keyCode <= 40) return;

    val=obj.value;
    re=/[^0-9.]/gi;   //가능 문자에 . 추가
    val = val.replace(re,"");
      

    //최초 . 사용하지 못하게 처리함.
    if( val.indexOf(".") == 0){
        val = "";
    }

    // 아래내용은 소수점이 2개이상인지 확인 후 재귀호출해서 여러번 kye up 없이 누르더라도

    // 소숫점이 하나만 남도록 처리

    v_cnt = val.indexOf(".",val.indexOf(".")+1);
      
    if( v_cnt > -1 ){
        val = val.substring(0,v_cnt) + val.substring(v_cnt + 1,val.length);
        obj.value = val;
        SetNum(obj);
    }
    
    obj.value = val; */


}

function displayChange(){
    form = document.forms[0];
    var subjectType = "";
    for(i=0; i<form.subjectType.length; i++) {
        if(form.subjectType[i].selected) subjectType = form.subjectType[i].value;
    }

    if(subjectType == "대상") {
        $("#taskTypeCategory").show();

    }else {
        $("#taskTypeCategory").hide();
    }
}

//-->
</script>

<script id='dummy'></script>
<style type="text/css">
body {
    margin-left:5px;
}
</style>
</head>
<body  onload="setCheck('<%=objType%>');setDrinit();">
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<form name=frm method="post" autocomplete="off">
<!-- hidden begin -->
<input type='hidden' name='docTypeOid' value=''>
<input type='hidden' name='taskName' value='<%=taskName%>'>
<input type='hidden' name='taskOid' value='<%=taskOid%>'>
<input type='hidden' name='selectTrustOid' value='<%=selectTrustOid%>'>
<input type='hidden' name='selectAssessOid' value='<%=selectAssessOid%>'>
<input type='hidden' name='oid' value='<%=oid%>'><!-- ProjectOutput OID -->
<input type="hidden" name="objType" value=''>
<input type="hidden" id="docOid" name="docOid" value=''>
<input type="hidden" id="docName" name="docName" value=''>
<input type="hidden" id="taskType" name="taskType" value='<%=taskType%>'>
<!-- hidden end -->
<!-- 산출물 정의 등록 layer 시작 -->
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td background="/plm/portal/images/logo_popupbg.png">
              <table height="28" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01732") %><%--산출물 정의--%></td>
                    <td width="10"></td>
                  </tr>
              </table>
              </td>
              <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
            </tr>
              </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space10"> </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
	                    <table border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                      <td>
	                    <%if(buttonAuth != null){
                        }
                        %>
	                    <%if(buttonAuth.isOuputDocAdd || CommonUtil.isAdmin() || isEdit){ %>
                        <table border="0" cellspacing="0" cellpadding="0">
                           <tr>
                             <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                             <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:onSave();" class="btn_blue"><%if(oid.length() > 0){%><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%> <%}else{ %><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%><%} %> </a></td>
                             <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                           </tr>
                        </table>
                        <%} %>
	                      </td>
	                      <td align="right">&nbsp;
	                      </td>
	                      <td align="right">
	                          <table border="0" cellspacing="0" cellpadding="0">
	                            <tr>
	                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:parent.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
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
                    <td class="space5"> </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td class="tab_btm2"></td>
            </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
<%
    if(isE3PSTask) {
%>
                <col width='15%'><col width='30%'><col width='30%'><col width='30%'>
<%
    } else {
%>
                <col width='10%'><col width='90%'>
<%
    }

    int colspan = 1;
    if(isE3PSTask) {
        colspan = 3;
    }

%>

            <%
            boolean isLastAddTask = ProjectTaskHelper.manager.isChild(task);
            int state = ((E3PSTask)task).getTaskState();

            if(  (isLastAddTask || state == 5) && !isAdmin && !ProjectUserHelper.manager.isPM(project) ){
                isPrimary = false;
                if(state == 5){isPrimary = true;}
            %>
            <tr>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03133") %><%--필수여부--%></td>
                <td class="tdwhiteL0" colspan = "3"><input type="radio" name="isPrimary"  value="1"  <%if(!isPrimary){out.println("checked");} %> disabled>Y<input type=radio name="isPrimary" value="0" <%if(isPrimary){out.println("checked");} %> disabled>N</td>
            </tr>
            <%}else{ %>
            <tr>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03133") %><%--필수여부--%><font color="red">*</font></td>
                <td class="tdwhiteL0" colspan = "3"><input type="radio" name="isPrimary"  value="1" <%if(isPrimary){out.println("checked");} %> >Y<input type=radio name="isPrimary" value="0" <%if(!isPrimary){out.println("checked");} %> >N</td>
            </tr>

            <%} %>
            <tr>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01739") %><%--산출물 타입--%> <font color="red">*</font></td>
                <td class="tdwhiteL0" colspan="3"><input type="radio" name="ouput" <%="DOC".equals(objType) ?  "checked": "" %> <%if("COST".equals(objType) || "SALES".equals(objType)){out.println("disabled");} %> onclick="setCheck('DOC')"><%=messageService.getString("e3ps.message.ket_message", "01394") %><%--문서--%>
                <input type=radio name="ouput" onclick="setCheck('DWG')"  <%=objType.equals("DWG") ?  "checked": "" %> <%if("COST".equals(objType) || "SALES".equals(objType)){out.println("disabled");} %>>도면
                <input type=radio name="ouput" onclick="setCheck('ECO')"  <%=objType.equals("ECO") ?  "checked": "" %> <%if("COST".equals(objType) || "SALES".equals(objType)){out.println("disabled");} %>>ECO
<%
	String taskNameStr = E3PSTask.getTaskName();
//out.println(E3PSTask.getTaskType()+":"+taskNameStr);
	if("Try".equals(E3PSTask.getTaskType())) {
%>
                <input type=radio name="ouput" onclick="setCheck('TRY')"  <%=objType.equals("TRY") ?  "checked": "" %>>Try
<%
	}
%>
<%--                 <input type=radio name="ouput" onclick="setCheck('GATE')"  <%=objType.equals("GATE") ?  "checked": "" %>>Gate --%>

                <input type=radio name="ouput" onclick="setCheck('QLP')"  <%=objType.equals("QLP") ?  "checked": "" %> <%if("COST".equals(objType) || "SALES".equals(objType)){out.println("disabled");} %>>품질
                <%if(project instanceof ProductProject ) {%>
                <input type=radio name="ouput" onclick="setCheck('COST')"  <%=objType.equals("COST") ?  "checked": "" %> <%if("SALES".equals(objType)){out.println("disabled");} %>>원가
                <input type=radio name="ouput" onclick="setCheck('SALES')"  <%=objType.equals("SALES") ?  "checked": "" %> <%if("COST".equals(objType)){out.println("disabled");} %>>판매목표가
                <%} %>
                <input type=radio name="ouput" onclick="setCheck('ETC')"  <%=objType.equals("ETC") ?  "checked": "" %> <%if("COST".equals(objType) || "SALES".equals(objType)){out.println("disabled");} %>><%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%>

                </td>
            </tr>

              <tr id="docType">
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
                <td id='selectTD' class="tdwhiteL0" initIndex=4
                    colspan='<%=colspan%>'>
                    <input type="text" readonly name="category1" value="<%=categoryName%>" class="txt_field"  style="width:80" id="textfield">
                    <select id="category2" name="category2" class="fm_jmp" style="width:150" onchange="javascript:changeCategory2();">
                    <%
                    String selectCategory2Code = "";

                    parameter.clear();
                    parameter.put("docTypeCode", "PD");
                    parameter.put("locale",      messageService.getLocale().toString());
                    parameter.put("parentCode",  categoryCode);

                    categoryList = DMSUtil.getDocumentCategory(parameter);

                    for ( Map<String, Object> map : categoryList ) {
                        categoryCode = map.get("categoryCode").toString();
                        categoryName = map.get("categoryName").toString();

                        if ( categoryName.equals(category2Value) )
                            selectCategory2Code = categoryCode;
                    %>
                    <option value="<%=categoryCode%>" <%=categoryName.equals(category2Value) ? "selected" : ""%>><%=categoryName%></option>
                    <%
                    }

                    if ( categoryList.size() > 0 ) {
                        categoryCode = categoryList.get(0).get("categoryCode").toString();
                        categoryName = categoryList.get(0).get("categoryName").toString();
                    }
                    %>
                    </select>
                    <select id="category3" name="category3" class="fm_jmp" style="width:170" onchange="javascript:changeCategory3();">
                    <OPTION VALUE=""></OPTION>
                    <%
                    if ( selectCategory2Code.length() > 0 ) categoryCode = selectCategory2Code;

                    parameter.clear();
                    parameter.put("docTypeCode", "PD");
                    parameter.put("locale",      messageService.getLocale().toString());
                    parameter.put("parentCode",  categoryCode);

                    categoryList = DMSUtil.getDocumentCategory(parameter);

                    for ( Map<String, Object> map : categoryList ) {
                        categoryCode = map.get("categoryCode").toString();
                        categoryName = map.get("categoryName").toString();
                    %>
                    <option value="<%=categoryCode%>" <%=categoryName.equals(category3Value) ? "selected" : ""%>><%=categoryName%></option>
                    <%
                    }
                    %>
                    </select>
                </td>
              </tr>


                <tr>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%> <font color="red">*</font></td>
                    <td class="tdwhiteL0" <%if(isE3PSTask) {%>colspan="3"<%}%>>
                        <input name="name" class="txt_field" style="width:100%;" value="<%=name%>">
                    </td>
                </tr>
                <tr>
                    <td class="tdblueM" valign="top"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
                    <td class="tdwhiteL0" <%if(isE3PSTask) {%>colspan="3"<%}%>>
                        <textarea name="description" cols="75" rows="5" class="fm_area"onKeyUp="common_CheckStrLength(this, 100)" onChange="common_CheckStrLength(this, 100)" style="width:100%;"><%=description%></textarea>
                    </td>
                </tr>
                <tr id="gateTargetTr">
                    <td class="tdblueM">Gate <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--Gate 대상--%> </td>
                    <td class="tdwhiteL0" <%if(isE3PSTask) {%>colspan="3"<%}%>">
                        <select id="subjectType" name="subjectType" class="fm_jmp" style="width:75" onchange="javascript:displayChange();">
                            <OPTION VALUE="">-- <%=messageService.getString("e3ps.message.ket_message", "01802") %> --</OPTION><%--선택--%>
                            <OPTION VALUE="<%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상 --%>" <%if("대상".equals(subjectType)) out.println("selected");%>><%=messageService.getString("e3ps.message.ket_message", "01227") %></OPTION>
                            <OPTION VALUE="<%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상 --%>" <%if("비대상".equals(subjectType)) out.println("selected");%>><%=messageService.getString("e3ps.message.ket_message", "01637") %></OPTION>
                        </select>
                        <span name="taskTypeCategory" id ="taskTypeCategory" style="<% if(!"대상".equals(subjectType)) out.print("display:none;"); %>">&nbsp;&nbsp;<font color ='red'>추가점검필요시 ▶</font>
                        <%
						Vector targetTypeVec = NumberCodeHelper.manager.getNumberCodeForQuery("GATELEVELNAME");
						String gateCheckType_arr_[] = {};
						boolean isGate = false;
						if (targetTypeVec != null) {
						    for (int i = 0; i < targetTypeVec.size(); i++) {
						        NumberCode code = (NumberCode) targetTypeVec.get(i);
						        String gateTypeStr = ((String)code.getName()).replaceAll("Gate", "");
						        
						        if(gateCheckType_arr != ""){
						            gateCheckType_arr_ = gateCheckType_arr.split(",");
						        }
						        
						        for (int j = 0; j < gateCheckType_arr_.length;  j++) {
						            if(gateTypeStr.equals(gateCheckType_arr_[j])){
						        	    isGate = true;
						            }
						        }
						%>
						                        <input type="checkbox" name="gateCheckType" id ="gateCheckType" value="<%=gateTypeStr %>" <%if(isGate){ %>checked<%} %>><%=code.getCode() %>
						<%
						        isGate = false;
						    }
						}
						%>
                        </span>
                                             


                    </select>  

                        
                    </td>
                </tr>
                <tr id="templateTr">
                    <td class="tdblueM">Template<%--Template--%>  </td>
                    <td class="tdwhiteL0" colspan = "3">
                        <table>
                            <tr>
                                <td>
                        <input type="text" id="tmpName" name="tmpName" class="txt_field" style="width:135" value="<%=StringUtil.checkNull(templateName) %>"/>
<!--                         <button id="templatePopup" onclick="javascript:standardSearchPopup();">찾기</button> -->
                                </td>
                                <td>
                        <span class="b-small" style="vertical-align:middle"><a href="javascript:standardSearchPopup();"> <%=messageService.getString("e3ps.message.ket_message", "07271") %><%--찾기--%></a></span>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
      <tr>
        <td height="30" valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="10">&nbsp;</td>
            <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
          </tr>
        </table></td>
      </tr>
</table>

<table id="costInfo" border="0" cellspacing="0" cellpadding="0" width="100%" style="display:none">
<tr>
	<td valign="top" style="padding:0px 0px 0px 0px">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr>
	        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
	        <td id="costLabel" class="font_03">판매목표가 등록</td>
	
	    </tr>
	    <tr>
	    <td class="space5"></td>
	    </tr>
	</table>
	
	
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	   <tr><td class="tab_btm2"></td></tr>
	</table>
	
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	   <tr>
	        <td class="tdblueM" width="20%">구분</td>
	        <td id="costTd1" class="tdblueM" width="40%">판매목표가(원)</td>
	        <td id="costTd2" class="tdblueM" width="40%">연평균수량(EA)</td>
	    </tr>
	    <tr>
	        <td class="tdwhiteM">검토</td>
	        <td class="tdwhiteL"><input name="costReview1" class="txt_field" style="width:100%; ime-mode:disabled;" value="<%=StringUtil.checkNull(costReview1) %>"></td>
	        <td class="tdwhiteL"><input name="costReview2" class="txt_field" style="width:100%; ime-mode:disabled;" value="<%=StringUtil.checkNull(costReview2) %>"></td>
	    </tr>
	    <tr>
	        <td class="tdwhiteM">최종</td>
	        <td class="tdwhiteL"><input name="costFinal1" class="txt_field" style="width:100%; ime-mode:disabled;" value="<%=StringUtil.checkNull(costFinal1) %>"></td>
	        <td class="tdwhiteL"><input name="costFinal2" class="txt_field" style="width:100%; ime-mode:disabled;" value="<%=StringUtil.checkNull(costFinal2) %>"></td>
	    </tr>
	</table>
	</td>
</tr>
</table>



<!-- 산출물 정의 등록 layer 끝 -->
<!-- ############################################################################################################################## -->

<!-- Layer begin -->
<%@include file="/jsp/common/AutoCompleteLayer.jsp"%>
<!-- Layer end -->
<script>
function setCheck(arg){
	if(arg == "DWG" || arg == "ETC" || arg == "TRY" || arg == "GATE" || arg=="ECO" || arg == "QLP" || arg == "COST" || arg == "SALES"){
        document.getElementById('docType').style.display = 'none';
        document.forms[0].objType.value = arg;
	}else {
        document.getElementById('docType').style.display = '';
        document.forms[0].objType.value = arg;
	}
	
	if(arg =="DOC" || arg == "GATE"){
        document.getElementById('templateTr').style.display = '';
    }else{
        document.getElementById('templateTr').style.display = 'none';
    }
    
    if(arg == "DOC" || arg == "DWG" || arg == "ECO" || arg == "QLP"){
        document.getElementById('gateTargetTr').style.display = '';
    }else{
        document.getElementById('gateTargetTr').style.display = 'none';
    }
    
    if(arg =="COST" || arg == "SALES"){
        document.getElementById('costInfo').style.display = '';
    }else{
        document.getElementById('costInfo').style.display = 'none';
    }
    
    if(arg =="COST"){
    	document.getElementById("costLabel").innerText  = '원가산출결과 등록';
    	document.getElementById("costTd1").innerText  = '총원가(원)';
    	document.getElementById("costTd2").innerText  = '수익율(%)';
    }else if(arg == "SALES"){
    	document.getElementById("costLabel").innerText  = '판매목표가 등록';
    	document.getElementById("costTd1").innerText  = '판매목표가(원)';
        document.getElementById("costTd2").innerText  = '연평균수량(EA)';
    }
    
}
function setDrinit(){
	fm = document.forms[0];
	var parentCode = fm.category2.options[fm.category2.selectedIndex].value;
	var docTypeCode = "PD";
	$.ajax( {
        url : "/plm/servlet/e3ps/DocumentCategoryAjax",
        type : "POST",
        data : {docTypeCode:docTypeCode, parentCode:parentCode},
        dataType : 'json',
        async : false,
        success: function(data) {
            tempdata = data;
        }
    });

}

</script>

</form>
</body>
</html>
