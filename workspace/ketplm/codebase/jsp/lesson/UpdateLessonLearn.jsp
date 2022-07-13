<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.StringTokenizer,
                  java.util.Vector,
                  java.util.ArrayList,
                  java.util.HashMap,
                  java.util.Map,
                  java.util.List,
                  e3ps.groupware.company.PeopleData,
                  e3ps.common.util.*,
                  e3ps.common.content.*,
                  e3ps.common.code.NumberCode,
                  e3ps.common.code.NumberCodeHelper,
                  e3ps.dms.entity.*,
                  e3ps.project.E3PSProject,
                  e3ps.project.ProjectOutput,
                  wt.org.WTUser,
                  wt.session.SessionHelper,
                  wt.content.*,
                  wt.query.QuerySpec,
                  wt.query.ClassAttribute,
                  wt.query.OrderBy,
                  wt.fc.PersistenceHelper,
                  wt.fc.QueryResult,
                  e3ps.common.web.ParamUtil,
                  e3ps.lesson.LessonLearn,
                  e3ps.lesson.beans.LessonHelper"%>
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/multicombo.jsp" %>

<%
	String oid = ParamUtil.checkStrParameter(request.getParameter("oid"));
	LessonLearn lesson = (LessonLearn)CommonUtil.getObject(oid);

    Map<String, Object> parameter = new HashMap<String, Object>();
    List<Map<String, Object>> numCode = null;
    List<Map<String, Object>> cause_list = null;
    List<Map<String, Object>> improve_list = null;
    Map<String, Object> cause_map = null;
    Map<String, Object> improve_map = null;
    Map<String, Object> LessonMap = new HashMap<String, Object>();
    List<Map<String, Object>> Lessonlist = null;

    Lessonlist = LessonHelper.LessonData(lesson);
    LessonMap  = Lessonlist.get(0) ;
    String part_no   	= (String)LessonMap.get("part_no");
    String part_name 	= (String)LessonMap.get("part_name");
	String gubun_oid   	= (String)LessonMap.get("gubun_oid");
	String factory_oid  = (String)LessonMap.get("factory_oid");
	String problem   	= (String)LessonMap.get("problem");
	String cause  	 	= (String)LessonMap.get("cause");
	String improve   	= (String)LessonMap.get("improve");
	String department   = (String)LessonMap.get("department");
	String isAttache 	=  (String)LessonMap.get("isAttache");
	Vector secondaryFiles 	=  (Vector)LessonMap.get("attacheList");
	String departmentOid 	=  (String)LessonMap.get("departmentOid");
	String partOid 			=  (String)LessonMap.get("partOid");

	String DieOid 			=  (String)LessonMap.get("DieOid");
	String DieName 			=  (String)LessonMap.get("DieName");
	String DieNo 			=  (String)LessonMap.get("DieNo");
	String equipNo 			=  (String)LessonMap.get("equipNo");
	String equipName 		=  (String)LessonMap.get("equipName");
	String creatorOid 		=  (String)LessonMap.get("creatorOid");
	String creator 			=  (String)LessonMap.get("creator");
	String create_date 		=  (String)LessonMap.get("create_date");

	String occur_date 		= (String)LessonMap.get("occur_date");
	String projectOid  		= (String)LessonMap.get("projectOid");
	String projectName  	= (String)LessonMap.get("projectName");
	String projectNo  		= (String)LessonMap.get("projectNo");
	String prodcut_gubun 	= (String)LessonMap.get("prodcut_gubun");
	String prodcut_gubunOid 	= (String)LessonMap.get("prodcut_gubunOid");
	String cause_gubunOid 		= (String)LessonMap.get("cause_gubunOid");
	String improve_gubunOid 	= (String)LessonMap.get("improve_gubunOid");


	String cause_gubun 		= "";
	String improve_gubun 	= "";
	String cause_gubun_check 		= (String)LessonMap.get("cause_gubun_check");
	String improve_gubun_check 	 	= (String)LessonMap.get("improve_gubun_check");

	String[] cause_temp = cause_gubun_check.split("\\|");
	String[] improve_temp = improve_gubun_check.split("\\|");

    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "LESSONCOMMON");
    numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    cause_list = new ArrayList<Map<String, Object>>();
    improve_list = new ArrayList<Map<String, Object>>();

		for ( int j=0; j < numCode.size(); j++ ) {
			cause_map = new HashMap<String, Object>();
			improve_map = new HashMap<String, Object>();
			for(int i=0; i<cause_temp.length; i++ ){
		    	if(cause_temp[i].equals(numCode.get(j).get("code"))){
		    		cause_map.put("flag", "1");
		    	}
			}
			for(int i=0; i<improve_temp.length; i++ ){
		    	if(improve_temp[i].equals(numCode.get(j).get("code"))){
		    		improve_map.put("flag", "1");
		    	}
			}
			cause_map.put("code", numCode.get(j).get("code"));
			cause_map.put("value", numCode.get(j).get("value"));
			cause_list.add(cause_map);

			improve_map.put("code", numCode.get(j).get("code"));
			improve_map.put("value", numCode.get(j).get("value"));
			improve_list.add(improve_map);
	    }

	ContentHolder holder = ContentHelper.service.getContents(lesson);
	Vector vec = ContentHelper.getContentList(holder);
	  int deleteFileCnt = 0;
	  for(int i=0; secondaryFiles !=null && i<secondaryFiles.size(); i++) {
	    ContentInfo contentInfo = (ContentInfo)secondaryFiles.elementAt(i);
	    if((contentInfo.getType()).equals("FILE")) {
	      deleteFileCnt++;
	    }
	  }

	WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
  	String userName = user.getFullName();
  	PeopleData peoData = new PeopleData(user);

  	String amsg; // alert 메시지용 변수
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="../../portal/js/org.js"></script>
<script language=JavaScript src="../../portal/js/common.js"></script>
<script language=javascript src="../../portal/js/Calendar.js"></script>
<script language=javascript src="/plm/portal/js/util.js"></script>
<script src='/plm/jsp/common/content/content.js'></script>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">

<style type="text/css">
<!--
/* body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 0px;
  margin-bottom: 5px;
} */
-->
</style>
<script language="JavaScript">
<!--

function selfInput(val){
	var form01 = document.forms[0];
	if(val == "1"){
		if(form01.chk_part.checked == true){
			clearPart('P');
			form01.partNo.readOnly = false;
			form01.partName.readOnly = false;
			form01.partNo.className = "txt_field";
			form01.partName.className = "txt_field";
			form01.chk_part.value = "1";
		}else{
			clearPart('P');
			form01.partNo.readOnly = true;
			form01.partName.readOnly = true;
			form01.partNo.className = "txt_fieldRO";
			form01.partName.className = "txt_fieldRO";
			form01.chk_part.value = "";
		}
	}
	if(val == "2"){
		if(form01.chk_die.checked == true){
			clearPart('D');
			form01.DieNo.readOnly = false;
			form01.DieName.readOnly = false;
			form01.DieNo.className = "txt_field";
			form01.DieName.className = "txt_field";
			form01.chk_die.value = "1";
		}else{
			clearPart('D');
			form01.DieNo.readOnly = true;
			form01.DieName.readOnly = true;
			form01.DieNo.className = "txt_fieldRO";
			form01.DieName.className = "txt_fieldRO";
			form01.chk_die.value = "";
		}
	}

}

//첨부파일 삭제
function deleteFile() {
  var form01 = document.forms[0];
  index = form01.fileDelete.length-1;
  for(i=index; i>=1; i--) {
    if(form01.fileDelete[i].checked == true) {
      var deleteFile = document.all[form01.fileDelete[i].sourceIndex+2].value;
      if(deleteFile != null) {
        var addFile = form01.deleteFiles.value;
        if(addFile == "") {
          form01.deleteFiles.value += deleteFile;
        } else {
          form01.deleteFiles.value += "*"+deleteFile;
        }
      }
      fileTable.deleteRow(i+1);
    }
  }
}

//==  [Start] 부서 검색  == //
  function delDepartment() {
	  document.forms[0].devUserDept.value = "";
      document.forms[0].devDeptOid.value = "";
  }

  function addDepartment() {
      var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=s";
      attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=430px; dialogHeight:465px; center:yes");
      if(typeof attacheDept == "undefined" || attacheDept == null) {
          return;
      }

      var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
      var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
      callServer(url, acceptDept);
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
      var l_chiefOid = showElements[0].getElementsByTagName("l_chiefOid");
      var l_chiefName = showElements[0].getElementsByTagName("l_chiefName");

      document.forms[0].devUserDept.value = decodeURIComponent(l_name[0].text);
      document.forms[0].devDeptOid.value = decodeURIComponent(l_oid[0].text);
      //document.forms[0].devUser.value = decodeURIComponent(l_chiefOid[0].text);
      //document.forms[0].tempdevUser.value = decodeURIComponent(l_chiefName[0].text);

  }
  // ==  [End] 부서 검색  == //

  function addMember() {
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=s";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
      return;
    }
    acceptRoleMember(attacheMembers);
  }

  function acceptRoleMember(objArr) {
    if(objArr.length == 0) {
      return;
    }

    var displayName = document.getElementById("creator");
    var keyName = document.getElementById("creatorOid");


    var nonUserArr = new Array();
    for(var i = 0; i < objArr.length; i++) {
      infoArr = objArr[i];
      displayName.value = infoArr[4];
      keyName.value = infoArr[0];
    }
  }

  function deleteMember() {
    document.getElementById("creator").value = "";
    document.getElementById("creatorOid").value = "";
  }

  function popupPart(gubun){
    showProcessing();
    SearchUtil.selectOnePart("callBackFuncPartPopup", "pType=" + gubun);
  }
  
  function callBackFuncPartPopup(selectedPartAry){
      
      if (typeof selectedPartAry != 'undefined' && selectedPartAry.length > 0) {
         if(gubun == 'P'){
           acceptPartNo(selectedPartAry);
         }
         if(gubun == 'D'){
           acceptDieNo(selectedPartAry);
         }
      }
  }

  function acceptPartNo(arrObj) {
      var partNoArr = new Array();
      // [0] - wtpart oid       // [1] - part number    // [2] - part name
      // [3] - part version     // [4] - part type      // [5] - die number
      // [6] - die name         // [7] - die cnt
      var infoArr = arrObj[0];
      document.forms[0].partName.value = infoArr[2];
      document.forms[0].partOid.value  = infoArr[0];
      document.forms[0].partNo.value   = infoArr[1];

  }

  function clearPart(gubun){
	  if(gubun == 'P'){
	  	document.forms[0].partName.value = "";
	  	document.forms[0].partOid.value  = "";
	  	document.forms[0].partNo.value   = "";
	  }
	  if(gubun == 'D'){
		document.forms[0].DieName.value = "";
		document.forms[0].DieOid.value  = "";
	    document.forms[0].DieNo.value   = "";
	  }
  }

  function acceptDieNo(arrObj) {
      var partNoArr = new Array();
      // [0] - wtpart oid       // [1] - part number    // [2] - part name
      // [3] - part version     // [4] - part type      // [5] - die number
      // [6] - die name         // [7] - die cnt
      var infoArr = arrObj[0];
      document.forms[0].DieName.value = infoArr[2];
      document.forms[0].DieOid.value  = infoArr[0];
      document.forms[0].DieNo.value   = infoArr[1];

  }

  function popupProject()
  {
      var url="/plm/jsp/project/SearchPjtPop.jsp?status=P&type=P";
      openWindow(url,"","1024","768","status=0,scrollbars=no,resizable=no");
  }

  function setProject(objArr) {

      if(objArr.length == 0) {
          return;
      }

      var trArr;
      var str = "";
      for(var i = 0; i < objArr.length; i++)
      {
          trArr = objArr[i];

          document.forms[0].projectOid.value= trArr[0];
          document.forms[0].project_id.value = trArr[1];
          document.forms[0].project_name.value= trArr[2];
      }
  }

  function delProject()
  {
      document.forms[0].projectOid.value =  '';
      document.forms[0].project_id.value =  '';
      document.forms[0].project_name.value =  '';
  }

  function common_gb_change(gb){
	  form = document.forms[0];
	  var ch_str = "";
	  if(gb == '1'){
		  ch_str = checkCauseGubun();
		  if(ch_str!= ''){
			  document.getElementById("causeComment").className = "txt_field";
			  document.getElementById("causeComment").readOnly = false;
		  }else{
			  document.getElementById("causeComment").className = "txt_fieldRO";
			  document.getElementById("causeComment").readOnly = true;
			  document.getElementById("causeComment").value = "";
		  }
	  }

	  if(gb == '2'){
		  ch_str = checkImproveGubun();
		  if(ch_str != ''){
			  document.getElementById("improveComment").className = "txt_field";
			  document.getElementById("improveComment").readOnly = false;
		  }else{
			  document.getElementById("improveComment").className = "txt_fieldRO";
			  document.getElementById("improveComment").readOnly = true;
			  document.getElementById("improveComment").value = "";
		  }
	  }

  }

  function checkCauseGubun(){
	  var chk_cause_gubun = document.all("chk_cause_gubun");
	  var str_chk_cause_gubun = '';
	  for( var inx=0; inx < chk_cause_gubun.length ; inx++)
	  {
        if( chk_cause_gubun[inx].checked )
        {
            str_chk_cause_gubun  += chk_cause_gubun[inx].value+"|";
        }
	  }
	  document.forms[0].cause_gubun_check.value = str_chk_cause_gubun;
	  return str_chk_cause_gubun;
  }


  function checkImproveGubun(){
	  var chk_improve_gubun = document.all("chk_improve_gubun");
	  var str_chk_improve_gubun = '';
	  for( var inx=0; inx < chk_improve_gubun.length ; inx++)
	  {
        if( chk_improve_gubun[inx].checked )
        {
            str_chk_improve_gubun  += chk_improve_gubun[inx].value+"|";
        }
	  }
	  document.forms[0].improve_gubun_check.value = str_chk_improve_gubun;
	  return str_chk_improve_gubun;
  }

  function doSave(){
	if(checkValidation()){
	  showProcessing();
	  disabledAllBtn();
      document.forms[0].action = '/plm/servlet/e3ps/LessonLearnServlet?cmd=modify';
      document.forms[0].submit();
	}
  }

  function go_to(url) {
	  showProcessing();
	  disabledAllBtn();
	  window.location=url;
  }

  function deleteDate(gubun){
	  if(gubun == '1'){
	  	document.forms[0].create_date.value = "";
	  }
	  if(gubun == '2'){
	  	document.forms[0].occur_date.value = "";
	  }
  }

  function checkValidation(){
	  var form1 = document.forms[0];
	  var chk_cause_gubun = document.all("chk_cause_gubun");
	  var str_chk_cause_gubun = checkCauseGubun();
	  var chk_improve_gubun = document.all("chk_improve_gubun");
	  var str_chk_improve_gubun = checkImproveGubun();
	  if(form1.prodcut_gubun.value == "0"){
		  alert("<%=messageService.getString("e3ps.message.ket_message", "03435") %>");//제품구분을 선택하세요.
		  return false;
	  }else if(form1.gubun.value == "0"){
		  alert("<%=messageService.getString("e3ps.message.ket_message", "03436") %>");//alert("구분을 선택하세요.");
		  return false;
	  }else if(form1.factory.value == "0"){
		  alert("<%=messageService.getString("e3ps.message.ket_message", "03437") %>");//alert("공장을 선택하세요.");
		  return false;
	  }else if(form1.devUserDept.value == ""){
		  alert("<%=messageService.getString("e3ps.message.ket_message", "03438") %>");//alert("부서를 입력하세요.");
		  return false;
	  }else if(form1.creator.value == ""){
		  alert("<%=messageService.getString("e3ps.message.ket_message", "03439") %>");//alert("작성자를 입력하세요.");
		  return false;
	  }else if(form1.create_date.value == ""){
		  alert("<%=messageService.getString("e3ps.message.ket_message", "03443") %>");//alert("작성일자를 입력하세요.");
		  return false;
	  }else if(form1.occur_date.value == ""){
		  alert("<%=messageService.getString("e3ps.message.ket_message", "03440") %>");//alert("발생일자를 입력하세요.");
		  return false;
	  }else if(str_chk_cause_gubun == ""){
		  alert("<%=messageService.getString("e3ps.message.ket_message", "03441") %>");//alert("원인구분을 선택하세요.");
		  return false;
	  }else if(str_chk_improve_gubun == ""){
		  alert("<%=messageService.getString("e3ps.message.ket_message", "03442") %>");//alert("개선대책구분을 선택하세요.");
		  return false;
	  }else{
		  return true;
	  }
  }
//-->
</script>
</head>
<body class="popup-background02 popup-space">
<form name=form01 method="post" enctype="multipart/form-data">
<input type="hidden" name="oid" value="<%=oid%>">
<input type=hidden name=cause_gubun_check value="">
<input type=hidden name=improve_gubun_check value="">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03449") %><%--습득교훈관리수정--%></td>
                
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="20"><img src="../../portal/images/icon_4.png"></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doSave()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--목록--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
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
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
        	<td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%></td>
            	<td colspan="3" class="tdwhiteL0">
            	<table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width="205"><input type="hidden" name="partOid" value=<%=partOid %>>
	                	<input type="text" name="partNo" class="txt_fieldRO" style="width:200" readonly value="<%=part_no %>">
	                	</td>
	                	<td width="20"><a href="javascript:popupPart('P');" onfocus="this.blur();"><img src="/plm/portal/images/icon_5.png" border="0"></a></td>
	                	<td width="30"><a href="javascript:clearPart('P');" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
	                	<td width=""><input type="text" name="partName" class="txt_fieldRO" style="width:200" id="textfield3" readOnly value="<%=part_name%>"></td>
	                	<td width="15"></td>
	                    <td><b><%=messageService.getString("e3ps.message.ket_message", "02720") %><%--직접입력--%></b></td>
	                    <td width="5"></td>
	                    <td><input type="checkbox" name="chk_part" id="chk_part" onClick="javascript:selfInput('1')"></td>
                	</tr>
                </table>
           		</td>
        </tr>
        <tr>
        	<td width="" class="tdblueL">Die No</td>
            	<td colspan="3" class="tdwhiteL0">
            	<table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width="205"><input type="hidden" name="DieOid" value="<%=DieOid%>">
	                	<input type="text" name="DieNo" class="txt_fieldRO" style="width:200" readonly value="<%=DieNo%>">
	                	</td>
	                	<td width="20"><a href="javascript:popupPart('D');" onfocus="this.blur();"><img src="/plm/portal/images/icon_5.png" border="0"></a></td>
	                	<td width="30"><a href="javascript:clearPart('D');" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
	                	<td width=""><input type="text" name="DieName" class="txt_fieldRO" style="width:200" id="textfield3" readOnly value="<%=DieName%>"></td>
	                	<td width="15"></td>
	                	<td><b><%=messageService.getString("e3ps.message.ket_message", "02720") %><%--직접입력--%></b></td>
	                    <td width="5"></td>
	                    <td><input type="checkbox" name="chk_die" id="chk_die" value = '1' onClick="javascript:selfInput('2')"></td>
                	</tr>
                </table>
           		</td>
        </tr>
<!--
        <tr>
        	<td width="" class="tdblueL">Project No</td>
            	<td colspan="3" class="tdwhiteL0">
            	<table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width="205"><input type="hidden" name="projectOid" value="<%=projectOid %>">
	                	<input type="text" name="project_id" class="txt_fieldRO" style="width:200" readonly value="<%=projectNo %>">
	                	</td>
	                	<td width="20"><a href="javascript:popupProject();" onfocus="this.blur();"><img src="/plm/portal/images/icon_5.png" border="0"></a></td>
	                	<td width="30"><a href="javascript:delProject();" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
	                	<td width=""><input type="text" name="project_name" class="txt_fieldRO" style="width:200" id="textfield3" readOnly value="<%=projectName%>"></td>
                	</tr>
                </table>
           		</td>
        </tr>
-->
        <tr>
        	<td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03445") %><%--설비번호--%></td>
            	<td colspan="3" class="tdwhiteL0">
            	<table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width=""><input type="text" name="equipNo" class="txt_field" style="width:200" id="textfield3" value="<%=equipNo%>" maxlength="8"></td>
                	</tr>
                </table>
           		</td>
        </tr>

        <tr>
        	<td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03446") %><%--설비명--%></td>
            	<td colspan="3" class="tdwhiteL0">
            	<table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width=""><input type="text" name="equipName" class="txt_field" style="width:200" id="textfield3" value="<%=equipName%>" maxlength="10"></td>
                	</tr>
                </table>
           		</td>
        </tr>

		<tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578") %><span class="red">*</span><%--제품구분--%></td>
          <td colspan="3" class="tdwhiteL0"><select name="prodcut_gubun" class="fm_jmp" style="width:205">

              <option value="0"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                <%
                parameter.clear();
                parameter.put("locale",   messageService.getLocale());
                parameter.put("codeType", "PRODUCTTYPE");
                parameter.put("name",     "자동차");
                numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                for ( int i=0; i < numCode.size(); i++ ) {
%>
                <option value="<%=numCode.get(i).get("oid")%>" <%if(numCode.get(i).get("oid").equals(prodcut_gubunOid)){%> selected <%}%>><%=numCode.get(i).get("value")%></option>
<%
  }
%>
            </select>&nbsp;
            </td>
        </tr>

        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00969") %><span class="red">*</span><%--구분--%></td>
          <td colspan="3" class="tdwhiteL0"><select name="gubun" class="fm_jmp" style="width:205">

              <option value="0"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                <%
                NumberCode nCode1=null;
                parameter.clear();
                parameter.put("locale",   messageService.getLocale());
                parameter.put("codeType", "LESSONDIVISION");
                numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                for ( int i=0; i < numCode.size(); i++ ) {
%>
            <option value="<%=numCode.get(i).get("oid")%>" <%if(numCode.get(i).get("oid").equals(gubun_oid)){%> selected <%}%>><%=numCode.get(i).get("value")%></option>
<%
  }
%>
            </select>&nbsp;
            </td>
        </tr>

        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03432") %><span class="red">*</span><%--공장--%></td>
          <td colspan="3" class="tdwhiteL0"><select name="factory" class="fm_jmp" style="width:205">
            <option value="0"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                <%

                NumberCode nCode =null;
                parameter.clear();
                parameter.put("locale",   messageService.getLocale());
                parameter.put("codeType", "PRODUCTIONDEPT");
                numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                for ( int i=0; i < numCode.size(); i++ ) {
%>
            <option value="<%=numCode.get(i).get("oid")%>" <%if(numCode.get(i).get("oid").equals(factory_oid)){%> selected <%}%>><%=numCode.get(i).get("value")%></option>
<%
  }
%>
            </select>&nbsp;
          </td>
        </tr>

        <tr>
        	<td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01538") %><span class="red">*</span><%--부서--%></td>
            <td colspan="3" class="tdwhiteL0">
            	<table border="0" width="255" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width="205">
	                	<input type=hidden name="devDeptOid" value="<%=departmentOid%>">
	                	<input style="width:200" type="text" id="devUserDept" name="devUserDept1" class="txt_fieldRO" readonly value="<%=department%>">
	                	</td>
	                	<td width="20">
	                	<a href="javascript:addDepartment('devDept');" onfocus="this.blur();"><img src="/plm/portal/images/icon_5.png" border="0"></a>
	                	</td>
	                	<td width="30">
	                	<a href="javascript:delDepartment();" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0">
	                	</td>
                	</tr>
                </table>
            </td>
        </tr>
        <tr>
        	<td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><span class="red">*</span><%--작성자--%></td>
            <td colspan="3" class="tdwhiteL0">
            	<table border="0" width="255" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width="205">
	                	<input type=hidden name="creatorOid" value="<%=creatorOid%>">
	                	<input style="width:200" type="text" id="creator" class="txt_fieldRO" readonly value="<%=creator%>">
	                	</td>
	                	<td width="20">
	                	<a href="javascript:addMember();" onfocus="this.blur();"><img src="/plm/portal/images/icon_5.png" border="0"></a>
	                	</td>
	                	<td width="30">
	                	<a href="javascript:deleteMember();" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0">
	                	</td>
                	</tr>
                </table>
            </td>
        </tr>

        <tr>
        	<td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><span class="red">*</span><%--작성일자--%></td>
            <td colspan="3" class="tdwhiteL0">
            	<table border="0" width="255" cellspacing="0" cellpadding="0">
                	<tr>
	                	 <td width="205">
	                	 <input type="text" name="create_date" class="txt_fieldRO"  style="width:200" readonly value="<%=create_date%>">
		                </td>
		                <td width="20">
		                <a href="#"><img src="/plm/portal/images/icon_6.png" border="0" onclick="javascript:showCal(document.forms[0].create_date);"></a>
		                </td>
		                <td width="30">
		                  <a href="javascript:deleteDate('1');" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
		                 </td>
                	</tr>
                </table>
            </td>
        </tr>

        <tr>
        	<td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03450") %><span class="red">*</span><%--발생일자--%></td>
            <td colspan="3" class="tdwhiteL0">
            	<table border="0" width="255" cellspacing="0" cellpadding="0">
                	<tr>
	                	 <td width="205">
	                	 <input type="text" name="occur_date" class="txt_fieldRO"  style="width:200" readonly value="<%=occur_date%>">
		                </td>
		                <td width="20">
		                <a href="#"><img src="/plm/portal/images/icon_6.png" border="0" onclick="javascript:showCal(document.forms[0].occur_date);"></a>
		                </td>
		                <td width="30">
		                  <a href="javascript:deleteDate('2');" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
		                 </td>
                	</tr>
                </table>
            </td>
        </tr>
        </table>




		 <table border="0" cellspacing="0" cellpadding="0" style="width:100%;">
              <tr>
                <td style="width:130px;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                <td class="tdwhiteL0">
                  <input type="hidden" name="deleteFiles">
                  <table style="width:100%;" border="0" cellspacing="0" cellpadding="0"  id="fileTable">
                    <tr>
                      <td></TD>
                      <td>
                        <table>
                          <tr>
                            <td>
                              <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                    <a href="javascript:insertSecondaryFile();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                  <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                              </table>
                            </td>
                            <td>
                              <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                    <a href="javascript:deleteFile();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a>
                                  </td>
                                  <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr align="left" id="fileTableRow" style="display:none">
                      <td align="left" valign="middle" style="width:20px; height:22px;">
                        <input type="checkbox" name="fileDelete">
                      </td>
                      <td>
                        <input type="file" name="filePath" id="input" onchange='isValidSecondarySize(this)' onkeyDown="this.blur()" style="ime-mode:disabled" class="txt_field" size="83">
                      </td>
                    </tr>
                    <%
                    for(int i=0; vec !=null && i<vec.size(); i++) {
                        ApplicationData appData = (ApplicationData)vec.get(i);
                        String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
                        //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                        String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
                    %>
                    <tr align="center" bgcolor="#f7f7f7" >
                        <td align="left" style="width: 20px; height: 22px;"><input type="checkbox" name="fileDelete"></td>
                        <td align="left"><input type="hidden" name="secondaryDelFile" value="<%=appDataOid%>" style="width:740px;">&nbsp;
                            <%=e3ps.common.content.E3PSContentHelper.service.getIconImgTag(appData)%>&nbsp;<a href="<%=url%>" target="_blank"><%=appData.getFileName()%></a>
                        </td>
                    </tr>
                    <%
                    }
                    %>
                  </table>
                </td>
              </tr>
            </table>

        <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td width="130" style="height:130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01438") %><%--문제점--%></td>
          <td colspan="3" style="height:130" class="tdwhiteL0"><textarea name="problemComment" class='txt_field' id="problemComment" style="width:100%; height:96%"><%=problem%></textarea></td>
        </tr>
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03448") %><span class="red">*</span><%--원인구분--%></td>
          <td colspan="3" class="tdwhiteL0">
		      <table border="0" cellspacing="0" cellpadding="0">
		          <tr>
		         <%
		                for ( int i=0; i < cause_list.size(); i++ )
		               {
		               %>
		          <td width="130"><input type="checkbox" name="chk_cause_gubun" id="checkbox"  value='<%=cause_list.get(i).get("code") %>' <%if("1".equals(cause_list.get(i).get("flag"))){ %>checked<%} %> onclick="common_gb_change('1');"><%=cause_list.get(i).get("value") %></td>
		         <%
		               }
		         %>
		        </tr>
		     </table>
	     </td>


        </tr>
        <tr>
          <td width="130" class="tdblueL" style="height:130"><%=messageService.getString("e3ps.message.ket_message", "03434") %><%--원인--%></td>
          <td colspan="3" style="height:130" class="tdwhiteL0"><textarea name="causeComment" class='txt_field' id="causeComment" style="width:100%; height:90%" size=2 ><%=cause%></textarea></td>
        </tr>
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03447") %><span class="red">*</span><%--개선대책구분--%></td>
          <td colspan="3" class="tdwhiteL0">
		      <table border="0" cellspacing="0" cellpadding="0">
		          <tr>
		         <%
		                for ( int i=0; i < improve_list.size(); i++ )
		               {
		               %>
		          <td width="130"><input type="checkbox" name="chk_improve_gubun" id="checkbox"  value='<%=improve_list.get(i).get("code") %>' <%if("1".equals(improve_list.get(i).get("flag"))){ %>checked<%} %> onclick="common_gb_change('2');"><%=improve_list.get(i).get("value") %></td>
		         <%
		               }
		         %>
		        </tr>
		     </table>
	     </td>
        </tr>
        <tr>
          <td width="130" class="tdblueL" style="height:130"><%=messageService.getString("e3ps.message.ket_message", "03433") %><%--개선대책--%></td>
          <td colspan="3" style="height:130" class="tdwhiteL0"><textarea name="improveComment" class='txt_field' id="improveComment" style="width:100%; height:90%" size=2 ><%=improve%></textarea></td>
        </tr>

      </table>
      </form>
</body>
</html>
