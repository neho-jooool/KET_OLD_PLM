<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.common.content.ContentUtil"%>
<%@page import="wt.content.ContentHolder"%>
<%@page import="e3ps.common.content.ContentInfo"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*,
            wt.org.*,
            wt.query.QuerySpec"%>
<%@page import="e3ps.common.util.CommonUtil,
            e3ps.common.util.WCUtil,
            e3ps.dms.entity.KETDocumentCategory,
            e3ps.dms.service.KETDmsHelper,
            e3ps.project.*,
            e3ps.project.beans.*,
            e3ps.project.outputtype.ProjectOutPutType,
            e3ps.dms.entity.KETStandardTemplate,
            wt.enterprise.RevisionControlled,
            wt.fc.QueryResult,
            e3ps.common.obj.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- jQuery -->
<script src="/plm/portal/js/jquery/jquery-1.3.2.min.js"></script>
<script src="/plm/portal/js/jquery/jquery.form.js"></script>
<script src="/plm/portal/js/jquery/json2.js"></script>

<%@include file="/jsp/common/context.jsp"%>
<%
  ProjectOutput output = null;
  TemplateTask task = null;
  TemplateProject project = null;

  String oid = request.getParameter("oid");
  String taskOid = request.getParameter("taskOid");
  String fromPage = request.getParameter("fromPage");
  String projectOid = "";
  String objType ="DOC";
  String myoutputtypeName ="";
  if (oid == null) {
    oid = "";
  }

  if (taskOid == null) {
    taskOid = "";
  }

  if (fromPage == null) {
    fromPage = "";
  }

  ReferenceFactory rf = new ReferenceFactory();
  ProjectOutPutType myoutputtype = null;
  String gateCheckType_arr = "";
  if (oid.length() > 0) {
    output = (ProjectOutput) rf.getReference(oid).getObject();
    gateCheckType_arr = StringUtil.checkNull(output.getGateCheckType()); 
    task = output.getTask();
    project = output.getProject();
    objType = output.getObjType();
    myoutputtype = output.getOutputType();
    if (myoutputtype != null){
      myoutputtypeName = myoutputtype.getName();
    }
  }

  if (output == null && taskOid.length() > 0) {
    task = (TemplateTask) rf.getReference(taskOid).getObject();
    project = task.getProject();
  }
  ProjectOutPutType outputtype =project.getOutputType();
  String name = "";
  String description = "";
  //String role = "";
  String location = "";

  String category2Value = "";
  String category3Value = "";

  boolean isPrimary = true;
  if (output != null) {
    ProjectOutputData data = new ProjectOutputData(output);
    name = data.name;
    description = data.description;
    //role = data.role_en == null ? "" : data.role_en;
    location = data.location;
    isPrimary = output.isIsPrimary();

    WTUser chargeUser = data.registryUser;

    if(location != null && location.length() > 0) {
         StringTokenizer st = new StringTokenizer(location, "/");
         int i = 1;
         while (st.hasMoreTokens()) {
           if(i == 2) category2Value = st.nextToken();
           else if(i == 3) category3Value = st.nextToken();
           else st.nextToken();
           i++;
         }
    }
  }
  
  //
        //ProjectOutput output = null;
        ProjectOutputData outputData = null;
        Object[] opObj = null;
        KETStandardTemplate latestInfo = null;
        QueryResult outputQr = ProjectOutputHelper.manager.getTaskOutput(task);
        while (outputQr.hasMoreElements()) {
          opObj = (Object[]) outputQr.nextElement();
          output = (ProjectOutput) opObj[0];
          //outputUser = output.getOwner() == null ? null : (WTUser) output.getOwner().getPrincipal();
          outputData = new ProjectOutputData(output);
          /* ContentHolder holder = (ContentHolder) output;
          Vector secondaryFiles = ContentUtil.getSecondaryContents(holder);
          int size = secondaryFiles.size(); */
          
          
          KETStandardTemplate ketStandardTemplate = (KETStandardTemplate)CommonUtil.getObject(output.getOutputDocOid());
          
          
          if(ketStandardTemplate != null){
          RevisionControlled latestVersion = ObjectUtil.getLatestVersion(ketStandardTemplate);
          
              latestInfo = (KETStandardTemplate) latestVersion;
          }else{
              latestInfo = null;
          }
        }
  //

  boolean isE3PSTask = false;
  if (task instanceof E3PSTask) {
    isE3PSTask = true;
  }

  String categoryCode = null;
  String docCatePath = null;
  String categoryName = messageService.getString("e3ps.message.ket_message", "01445")/*미등록되어 있습니다*/;
  String docTypeCode = null;
  String isDRCheckSheet = null;
  String tmpStr = null;

  //최초 분류체계 중 개발산출문서를 선택하여 화면에 나타낸다.
  KETDocumentCategory docCate = null;
  ArrayList optList = KETDmsHelper.service.selectChildDocCategory("ROOT");
  Object[] obj = null;
  for(int i = 0; i < optList.size(); i++){
    obj = (Object[])optList.get(i);
    docTypeCode = (String)obj[0];
    docTypeCode = docTypeCode.substring(0,2);

    if(docTypeCode.equals("PD")){
      categoryCode = (String)obj[0];
      categoryName = (String)obj[1];
    }
  }
%>

<html>
<head>
<title></title>
<%@include file="/jsp/common/top_include.jsp"%>
<LINK rel="stylesheet" type="text/css"
  href="/plm/portal/css/e3ps.css">
<style type="text/css">
<!--
.style1 {
    color: #FF0000
}
-->
</style>
<script language='javascript'>
<!--
/*
 * 문서 분류 관련 스크립스 시작 ...........................................................................
 */
var selectLevelIndex = 0;

var initIndex = 0;
var targetSelectTD = "selectTD";

function onChangeFolder(fObj) {
  setSelectTree(fObj);
}

function setSelectTree(fObj) {
  form = document.forms[0];

  selectLevelIndex = new Number(fObj.selectlevel);
  removeSelect(selectLevelIndex);
    if(fObj.value != '') {
    selectSearch(fObj.value);
  }
}

function selectSearch(svalue) {
  onProgressBar();

  var param = "command=select&oid=" + svalue;
  var url = "/plm/jsp/project/template/DocTypeAjaxAction.jsp?" + param;
  callServer(url, onSelectOption);
}

function removeSelect(selectlevel) {

  var fTD = document.all.item(targetSelectTD);
  var spans = fTD.all.tags("SPAN");

  initIndex = new Number(fTD.initIndex);
  len = spans.length;
  for(var i = (len-1); i > selectlevel; i--) {
    var rspan = fTD.all.tags("SPAN")[i];
    if(i > (initIndex-1)) {
      fTD.removeChild(rspan);
    }else{
      var cSelect = rspan.children(0);
      selectNodeInit(cSelect);
    }
  }
}

function selectNodeValue() {
  var fTD = document.all.item(targetSelectTD);
  var spans = fTD.all.tags("SPAN");
  len = spans.length;
  for(var i = len; i > 0; i--) {
    var rspan = spans[i-1];
    var cSelect = rspan.children(0);
    if(cSelect.value.length > 0) {
      return cSelect.value;
    }
  }

  return '';
}

function selectNodeInit(selObj) {
  var selLen = selObj.length;
  for(var i = selLen; i > 1; i--) {
    var selectOption = selObj.options[i-1];
    selObj.removeChild(selectOption);
  }
}

function onSelectOption(req) {
  var xmlDoc = req.responseXML;
  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;

  showElements = xmlDoc.selectNodes("//data_info");
  var l_oid = showElements[0].getElementsByTagName("l_oid");
  var l_name = showElements[0].getElementsByTagName("l_name");

  var arr = new Array();
  for(var i = 0; i < l_oid.length; i++) {
    subArr = new Array();
    subArr[0] = decodeURIComponent(l_oid[i].text);
    subArr[1] = decodeURIComponent(l_name[i].text);
    arr[i] = subArr;
  }
  addSelectNode(arr);
}

function addSelectNode(vArr) {
  var fTD = document.all.item(targetSelectTD);
  initIndex = new Number(fTD.initIndex);

  if(selectLevelIndex >= (initIndex-1)) {
    if(vArr.length == 0){
      return;
    }
  }

  if(selectLevelIndex == (initIndex-1)) {
    var newSpan = document.createElement("SPAN");
    var htmlStr = "<select name='selFolder' selectlevel=" + ++selectLevelIndex + " class='fm_jmp' style='width:110' onChange='javascript:onChangeFolder(this);'><option value=''>[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]<\/option>";
    for(var i = 0; i < vArr.length; i++) {
      htmlStr += "<option value=" + vArr[i][0] + ">" + vArr[i][1] + "<\/option>";
    }
    htmlStr += "<\/select>&nbsp;";
    newSpan.innerHTML = htmlStr;
    fTD.appendChild(newSpan);
  }else{
    var newSpan = fTD.all.tags("SPAN")[selectLevelIndex+1];
    var newSelect = newSpan.children(0);
    for(var i = 0; i < vArr.length; i++) {
      newOpt = document.createElement("OPTION");
      newOpt.text = vArr[i][1];
      newOpt.value = vArr[i][0];
      newSelect.add(newOpt);
    }
  }
}
/*
 * 문서 분류 관련 스크립스 끝 ...........................................................................
 */


 //사용자 가져오기 시작 ........................................................................................
//............................................................................................................
function addViewMember() {
  var url = "/plm/jsp/project/ProjectMemberViewPage.jsp?command=select&mode=multi&fromPage=OutputCreatePage&oid=<%=taskOid%>";
  attaches = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=620px; dialogHeight:460px; center:yes");
  if(typeof attaches == "undefined" || attaches == null) {
    return;
  }
  onViewMemberUpdate(attaches);
}


function onViewMemberUpdate(objArr) {
  if(objArr.length == 0) {
    return;
  }

/*
  subarr[0] = form.oid.value;//oid
  subarr[1] = form.oid.userName;
  subarr[2] = form.oid.dutyName;
  subarr[3] = form.oid.deptName;
  subarr[4] = form.oid.email;
  subarr[5] = form.oid.peopleOID;
  subarr[6] = form.oid.isView;
  subarr[7] = form.oid.isDept;
 */
  for(var i = 0; i < objArr.length; i++) {
    subarr = objArr[i];
    if(subarr[7] == 'true') {
      if(isDeptCheck(subarr[0])) {
        continue;
      }
      acceptDept(subarr);

    } else {
      if(isMemberCheck(subarr[0])) {
        continue;
      }
      acceptMember(subarr);
    }
  }
}



function isMemberCheck(wtuserid) {
  form = document.forms[0];
  if(form.userOid == null || form.userOid == 'undefined') {
    return false;
  }

  var memberLen = form.userOid.length;
  if(memberLen) {
    for(var i = 0; i < memberLen; i++) {
      if(form.userOid[i].value == wtuserid) {
        return true;
      }
    }
  }else {
    if(form.userOid.value == wtuserid) {
      return true;
    }
  }

  return false;
}



function getMemberParameters() {
  form = document.forms[0];

  if(form.userOid == null || form.userOid == 'undefined') {
    return "";
  }

  var userParam = "";
  var memberLen = form.userOid.length;
  if(memberLen) {
    for(var i = 0; i < memberLen; i++) {
      userParam += "&userOid=" + form.userOid[i].value;
    }
  }else {
    userParam += "&userOid=" + form.userOid.value;
  }

  return userParam;
}
//사용자 가져오기 끝 ........................................................................................
//............................................................................................................

//SAVE ACTION BEGIN @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
function onSave() {

  form = document.forms[0];

/*  if(form.outtypecheck.checked ){
    if(form.outputtype.value ==""){
      alert("산출물 인증 단계를 선택해 주세요");
      return;
    }
  }
*/
  if(form.objType.value=="DOC"){
    var param = "command=subcheck&oid=" + form.category3;
    if(form.category3.value == ''){
      alert('<%=messageService.getString("e3ps.message.ket_message", "01398") %><%--문서 위치를 선택 하십시오--%>');
      return;
    }

    var url = "/plm/jsp/project/template/DocTypeAjaxAction.jsp?" + param;
    callServer(url, onSaveAction);
  }else{
    doSubmit();
  }
}

function onSaveAction(req) {
  var xmlDoc = req.responseXML;
  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;


  if(msg == 'true') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03143") %><%--하위문서분류를 선택하시기 바랍니다--%>');
    return;
  }

  doSubmit();
}

function doSubmit() {
  form = document.forms[0];
  if(form.name.value == '') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02526") %><%--제목을 입력하세요--%>');
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

  var tid = form.taskOid.value;
  var opid = form.oid.value;
  onProgressBar();
  var param = "command=addOutputTemplate";
  param += "&taskOid=" + tid;
  param += "&oid=" + opid;
  param += "&docTypeOid=" + form.docTypeOid.value;
  param += "&name=" + escape(encodeURIComponent(form.name.value));
  param += "&description=" + escape(encodeURIComponent(form.description.value));
  /* 추가  */
  param += "&objType=" + form.objType.value;
  param += "&outputtype=";// + form.outputtype.value;
  if(form.isPrimary[0].checked) {
    param += "&isPrimary=" + form.isPrimary[0].value;
  }else {
    param += "&isPrimary=" + form.isPrimary[1].value;
  }
  
  var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
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
  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  if(msg == 'true') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02780") %><%--처리 완료했습니다--%>');
  } else {
    alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
    return;
  }

  window.returnValue = true;
  window.close();
}
//SAVE ACTION END @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

function deleteMember(rname) {
  document.getElementById("temp" + rname).value = "";
  document.getElementById(rname).value = "";
}

function roleChange() {
  form = document.forms[0];

  if(form.role.value == "") {
    var tspan0 = document.getElementById("span0");
    var tspan1 = document.getElementById("span1");

    tspan0.style.display = "";
    tspan1.style.display = "none";
    tspan1.innerText = "";

    return;
  }
  onProgressBar();

  var param = "command=outSearchRoleMember";
  param += "&oid=" + form.taskOid.value;
  param += "&role=" + form.role.value;
  var url = "/plm/jsp/project/ProjectAjaxAction.jsp?" + param;
  callServer(url, onSetRoleMember);
}

function onSetRoleMember(req) {
  var xmlDoc = req.responseXML;
  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  if(msg == 'false') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00446") %><%--Role 담당자를 가져오는 중 에러가 발생했습니다\n다시 시도하시기 바랍니다--%>");
    return;
  }

  showElements = xmlDoc.selectNodes("//data_info");

  var l_oid = showElements[0].getElementsByTagName("l_oid");
  var l_name = showElements[0].getElementsByTagName("l_name");
  var l_linkOid = showElements[0].getElementsByTagName("l_linkOid");

  var tspan0 = document.getElementById("span0");
  var tspan1 = document.getElementById("span1");


  tspan0.style.display = "none";
  tspan1.style.display = "";
  tspan1.innerText = "";

  if(l_oid != null && l_oid.length > 0) {
    loid = decodeURIComponent(l_oid[0].text);
    lname = decodeURIComponent(l_name[0].text);
    llinkOid = decodeURIComponent(l_linkOid[0].text);

    tspan1.innerText = lname;

  }
}

function addChargeUser() {
  onLayer('layer0');

  form = document.forms[0];
  var param = "command=autoCompleteMember";
  param += "&oid=" + form.taskOid.value;
  param += "&name=" + form.tempoutputUser.value;
  param += "&roleType=MEMBER";
  var url = "/plm/jsp/project/ProjectAjaxAction.jsp?" + param;
  callServer(url, setLayerMember);
}

function setLayerMember(req) {
  var xmlDoc = req.responseXML;
  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  if(msg == 'false') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03064") %><%--프로젝트 구성원을 가져오는 중 에러가 발생했습니다\n다시 시도하시기 바랍니다--%>');
    return;
  }

  var showElements = xmlDoc.selectNodes("//data_info");
  var l_oid = showElements[0].getElementsByTagName("l_oid");
  var l_name = showElements[0].getElementsByTagName("l_name");
  var l_duty = showElements[0].getElementsByTagName("l_duty");
  var l_departmentName = showElements[0].getElementsByTagName("l_departmentName");
  var l_email = showElements[0].getElementsByTagName("l_email");
  var l_peopleOid = showElements[0].getElementsByTagName("l_peopleOid");
  var l_linkOid = showElements[0].getElementsByTagName("l_linkOid");


  var htmlStr = "";
  if(l_oid != null && l_oid.length > 0) {
    for(var i = 0; i < l_oid.length; i++) {
      lname = decodeURIComponent(l_name[i].text);
      ldepartmentName = decodeURIComponent(l_departmentName[i].text);
      llinkOid = decodeURIComponent(l_linkOid[i].text);

      htmlStr +="<li><a href='#' onclick=\"javascript:setLayerData('"+lname+"','"+llinkOid+"');\">"+ lname + "&nbsp;|&nbsp;" + ldepartmentName +"</a></li>";
    }
  }
  else {
    htmlStr += "<li style='text-align:center;'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></li>";
  }

  onLayerClear("layer0content");
  var layerContent = document.getElementById("layer0content");
  layerContent.innerHTML = "<ul>" + htmlStr + "</ul>";
}

function setLayerData(displaystr, keystr) {
  form = document.forms[0];

  form.tempoutputUser.value = displaystr;

  offLayer('layer0');
}
function setAbled(){
  if(document.forms[0].outtypecheck.checked == true){
    document.forms[0].outputtype.disabled = false;
  }else{
    document.forms[0].outputtype.value = "";
    document.forms[0].outputtype.disabled = true;
    document.forms[0].outputtype.value="";

  }
}

function changeCategory2(){
//2단계 분류체계변경시 CateSelect.jsp를 호출하여 3단계를 화면에 나타내준다.

  var fm = document.forms[0];
  fm.category3.length = 1;
  fm.category3.selectedIndex = 0;
  var obj = document.getElementById('dummy');

  obj.src = "/plm/jsp/dms/CateSelect.jsp?oid=" + fm.category2.options[fm.category2.selectedIndex].value;
}

function changeCategory3(){
//3단계 분류체계변경시 선택된 분류체계를 categoryCode에 저장하고 DR평가점수 필요여부를 체크한다.
  var fm = document.forms[0];
  var tStr = fm.category3.options[fm.category3.selectedIndex].value;
}

function onSaveNew(){
    //alert("!");
	var fm = document.forms[0];
	
	fm.command.value ="addOutputTemplate";
	
	
	// var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
	
	 
}

<!-- 2014.07.23 표준양식에서 선택된 값 처리 함수-->
function setStandardForm(docOid, docName){
//	alert(docOid+"  "+ docName);
	$("#docOid").val(docOid);
	$("#docName").val(docName);
	$("#tmpName").val(docName);
} 
//-->

function deleteFile(){
	$("#tmpName").val("");
	$("#docOid").val("");
	$("#docName").val("");
}

$(document).ready(function(){
        
	    $("#save").click(function(){
	    	
	    	form = document.forms[0];
	    	  if(form.name.value == '') {
	    	        alert('<%=messageService.getString("e3ps.message.ket_message", "02526") %><%--제목을 입력하세요--%>');
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
	    	  
	    	  var gateCheckType = "";
	    	  
	    	  if(form.category4.value == '대상'){//추가 Gate점검 대상 세팅
	    	      for(i=0; i<form.gateCheckType.length; i++) {
	    	          if(form.gateCheckType[i].checked){
	    	              gateCheckType += form.gateCheckType[i].value+',';
	    	          }
	    	      }
	    	  }else{
	    	      gateCheckType = "";
	    	  }
	    	  form.gateCheckType_param.value = gateCheckType;

	    	  if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "01894") %><%--산출물 정의를 저장하시겠습니까?--%>")){
	    	    return;
	    	  }
	    	
	    	
	    	$("#frm").attr("action","/plm/jsp/project/ProjectAjaxAction.jsp").submit();
	    	
	    });
	    
	    $("#templatePopup").click(function(){
	    	var url="/plm/jsp/dms/SearchStandardDoc.jsp?popup=yes";
	    	var modalHeight = "700px";
	    	var modalWidth ="1020px";
	    	window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth="+modalWidth+"; dialogHeight=" +modalHeight+ "; center:yes");
	    });
	
		$("#frm").ajaxForm({
	        dataType    : "text",
	        success     : function(data){
	                    alert('<%=messageService.getString("e3ps.message.ket_message", "02780") %>');
	                    opener.location.reload();
	                    //window.returnValue = true;
	                    window.close();
	        },
	        error    : function(xhr, status, error){
	                     alert(xhr+"  "+status+"  "+error);
	                     
	        }
	    });
	
	  $("#insertFileLine").click(function(){
		  $("#fileTable").append("<tr><td><input type='checkbox' /><input type='file' name='secondaryFiles' value='찾아보기'/></td></tr>");
	  });
	  
	  $("#deleteFileLine").click(function(){
		  $("input:checkbox:checked").each(function (index) {  
			   if($("input:checkbox").length ==1){}
			   else{
				   var contentOid = $(this).val();
				   $("#frm").append("<input type='hidden' id='isFileDel' name='isFileDel' value='"+contentOid+"' />");
				   $(this).parent().remove();
				   }
			   
		    });
		  
	  });
});

function displayChange(){
    form = document.forms[0];
    var category4 = "";
    for(i=0; i<form.category4.length; i++) {
        if(form.category4[i].selected) category4 = form.category4[i].value;
    }

    if(category4 == "대상") {
        $("#taskTypeCategory").show();

    }else {
        $("#taskTypeCategory").hide();
    }
}
</script>
<script id='dummy'></script>
<style type="text/css">
<!--
body {
}
-->
</style>
</head>
<body onload="setCheck('<%=objType%>')";>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<form id="frm" name=frm method="post" enctype="multipart/form-data"><!-- hidden begin -->
<input type='hidden'  name='docTypeOid' value=''>
<input type='hidden' name='taskOid'  value='<%=taskOid%>'>
<input type='hidden' name='oid'  value='<%=oid%>'>
<input type="hidden" name="objType" value=''>
<input type="hidden" name="command" value="addOutputTemplate">
<input type="hidden" id="docOid" name="docOid" value='<%=output != null ? output.getOutputDocOid() : "" %>'>
<input type="hidden" id="docName" name="docName" value='<%=output != null ? output.getOutputDocName() : "" %>'>
<input type='hidden'  name='gateCheckType_param' value=''>
<!-- ProjectOutput OID --> <!-- hidden end -->
<!-- 산출물 정의 등록 layer 시작 -->

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01732") %><%--산출물 정의--%></td>
            
          </tr>
        </table></td>
        <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="470" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
          <td valign="top"><table width="460" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td width="320px;">&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:void();" id="save" class="btn_blue"><%if(oid != null && oid.length() > 0){ %><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%><%} %></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
               <td align="right">
                <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:window.close();"  class="btn_blue">닫기</a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                 </table>
            </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">

        <%
        if (isE3PSTask) {
        %>
        <col width='20%'>
        <col width='30%'>
        <col width='20%'>
        <col width='30%'>
        <%
        } else {
        %>
        <col width='20%'>
        <col width='80%'>
        <%
          }

          int colspan = 1;
          if (isE3PSTask) {
            colspan = 3;
          }
        %>

        <tr>
                <td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03133") %><%--필수여부--%></td>
                <td  class="tdwhiteL0"><input type="radio" name="isPrimary"  value="1"<%=isPrimary ?  "checked": "" %>>Y<input type=radio name="isPrimary" value="0" <%=!isPrimary ?  "checked": "" %>>N</td>
              </tr>
              <tr>
                <td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01739") %><%--산출물 타입--%></td>
                <td  class="tdwhiteL0">
                <input type="radio" name="ouput" <%=objType.equals("DOC") ?  "checked": "" %> onclick="setCheck('DOC')"><%=messageService.getString("e3ps.message.ket_message", "01394") %><%--문서--%>
                <input type=radio name="ouput" onclick="setCheck('DWG')"  <%=objType.equals("DWG") ?  "checked": "" %>>도면
                <input type=radio name="ouput" onclick="setCheck('ECO')"  <%=objType.equals("ECO") ?  "checked": "" %>>ECO
                <input type=radio name="ouput" onclick="setCheck('TRY')"  <%=objType.equals("TRY") ?  "checked": "" %>>Try조건표
                <input type=radio name="ouput" onclick="setCheck('GATE')"  <%=objType.equals("GATE") ?  "checked": "" %>>Gate
                <input type=radio name="ouput" onclick="setCheck('ETC')"  <%=objType.equals("ETC") ?  "checked": "" %>><%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%>
                 <%if(project instanceof ProductTemplateProject || project instanceof ElectronTemplateProject) {%>
                <input type=radio name="ouput" onclick="setCheck('COST')"  <%=objType.equals("COST") ?  "checked": "" %>>원가
                <input type=radio name="ouput" onclick="setCheck('SALES')"  <%=objType.equals("SALES") ?  "checked": "" %>>판매목표가
                <%} %>
                </td>
              </tr>

               <tr id="docType">
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01424") %></td>
        <td id='selectTD' class="tdwhiteL0" initIndex=4
          colspan='<%=colspan%>'>
          <input type="text" readonly name="category1" value="<%=categoryName%>" class="txt_field"  style="width:80" id="textfield">
                <select name="category2" class="fm_jmp" style="width:80" onchange="javascript:changeCategory2();">
<%
        String selectCategory2Code = "";
          optList = KETDmsHelper.service.selectChildDocCategory(categoryCode);
          obj = null;

        for(int i = 0; i < optList.size(); i++){
          obj = (Object[])optList.get(i);

          categoryCode = (String)obj[0];
          categoryName = (String)obj[1];

          if(categoryName.equals(category2Value)) selectCategory2Code = categoryCode;
%>

               <OPTION VALUE="<%=categoryCode%>" <%=categoryName.equals(category2Value) ? "selected" : ""%>><%=categoryName%></OPTION>
<%        }
        if(optList.size()>0){
          obj = (Object[])optList.get(0);
          categoryCode = (String)obj[0];
          categoryName = (String)obj[1];
        }
%>
            </select>
            <select name="category3" class="fm_jmp" style="width:170" onchange="javascript:changeCategory3();">
               <OPTION VALUE=""></OPTION>
<%
        if(selectCategory2Code.length() > 0) categoryCode = selectCategory2Code;
          optList = KETDmsHelper.service.selectChildDocCategory(categoryCode);
          obj = null;
        for(int i = 0; i < optList.size(); i++){
          obj = (Object[])optList.get(i);

          categoryCode = (String)obj[0]+(String)obj[2];
          categoryName = (String)obj[1];
%>

               <OPTION VALUE="<%=categoryCode%>" <%=categoryName.equals(category3Value) ? "selected" : ""%>><%=categoryName%></OPTION>
<%        }
%>
            </select></td>
              </tr> 
              <tr>
                <td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                <td class="tdwhiteL0" <%if(isE3PSTask) {%> colspan="3" <%}%>><input
          name="name" class="txt_field" style="width:100%;" value="<%=name%>"
          onKeyUp="common_CheckStrLength(this, 80)"
          onChange="common_CheckStrLength(this, 80)"></td>
              </tr>
              <tr>
                <td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
                <td class="tdwhiteL0" <%if(isE3PSTask) {%> colspan="3" <%}%>><textarea
          name="description" cols="35" rows="5" class="fm_area"
          onKeyUp="common_CheckStrLength(this, 666)"
          onChange="common_CheckStrLength(this, 666)" style="width:100%;"><%=description%></textarea></td>
                 <!-- 2014.07.14 컬럼추가  -->
              </tr>
              <tr id="subject">
                    <td class="tdblueL">Gate 대상</td>
                     <td class="tdwhiteL0">
                     <select id="category4" name="category4" class="fm_jmp" style="width:80" onchange="javascript:displayChange();">
                        <OPTION VALUE="">-- <%=messageService.getString("e3ps.message.ket_message", "01802") %> --</OPTION><%--선택--%>
                        <option value="대상" <%if(output != null) {%><%="대상".equals(output.getSubjectType()) ? "selected" :""%><%} %>>대상</option>
                        <option value="비대상" <%if(output != null) {%><%="비대상".equals(output.getSubjectType()) ? "selected" :""%><%} %>>비대상</option>
                     </select>
                     
                     <span name="taskTypeCategory" id ="taskTypeCategory" style="<% if(output == null || (output != null && !"대상".equals(output.getSubjectType()))) out.print("display:none;"); %>">
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
                        
                     </td>
              </tr>
              <tr id="template">
                 <td class="tdblueL">Template</td>
                 <td colspan="3" class="tdwhiteL">
                     <table  border="0" cellpadding="0" cellspacing="0">
                         <tr>
                             <td class="space5"></td>
                         </tr>
                     </table>
                     <table  border="0" cellspacing="0" cellpadding="0" id="fileTable">
                         <tr>
                             <td>
                                  
                                  <input type="text" id="tmpName" name="tmpName" value="<%=latestInfo == null ? "" : latestInfo.getTitle()%>"/><button id="templatePopup">찾기</button>
                                  <a href="javascript:deleteFile();"><img src="/plm/portal/images/icon_delete.gif"></a>
                                 <!-- <table border="0" cellspacing="0" cellpadding="0">
                                     <tr>
                                         <td>
                                             <table border="0" cellspacing="0" cellpadding="0">
                                                 <tr>
                                                     <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                     <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                         href="javascript:void()" id="insertFileLine" class="btn_blue">추가</a></td>
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
                                                         href="javascript:void()" id="deleteFileLine" class="btn_blue">삭제</a></td>
                                                     <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                 </tr>
                                             </table>
                                         </td>
                                     </tr>
                                 </table> -->
                             </td>
                            <%--  <td align="right">&nbsp;</td>
                         </tr>
                          <%
                         // String oid = request.getParameter("oid");
                            if(oid != ""){
                            ContentHolder holder = (ContentHolder) rf.getReference(oid).getObject();
                            Vector secondaryFiles = ContentUtil.getSecondaryContents(holder);
                            int size = secondaryFiles.size();
                            for(int i = 0; i < secondaryFiles.size(); i++){
                        	    ContentInfo file = (ContentInfo)secondaryFiles.elementAt(i);
                          %>
                          <tr>
                            <td><input type="checkbox" value="<%=file.getContentOid()%>" /><a href="<%=file.getDownURL()%>"><%=file.getName()%></a>
                                <input type="hidden"  name="ContentOid" value="<%=file.getContentOid()%>"/>                          
                            </td>
                          </tr>
                          <%}} %>
                          <tr>
                             <td><input type="checkbox"/><input type="file" name="secondaryFiles" value="찾아보기"/></td>
                          </tr> --%>
                     </table>
                    <!--  <table border="0" cellspacing="0" cellpadding="0" width="645">
                         <tr>
                             <td class="space5"></td>
                         </tr>
                     </table>
                     <table border="0" cellpadding="0" cellspacing="0" width="645">
                         <tbody id="iFileTable" />
                     </table>
                     <table width="645" border="0" cellpadding="0" cellspacing="0">
                         <tr>
                             <td class="space5"></td>
                         </tr>
                     </table> -->
                 </td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  
</table>




<!-- 산출물 정의 등록 layer 끝 --> <!-- ############################################################################################################################## -->

<!-- Layer begin -->
<style>
 <!--
#layer0
{
  font: 0.8em arial, helvetica, sans-serif;
  background-color: #fff;
  color: #333;
  border: 1px solid gray;
  visibility:hidden;
  position:absolute;
  z-index:33;
}

#layer0content
{
  margin: 0;
  border: 0;
  padding: 0;
  overflow-x:auto;
  overflow-y:auto;
  scrollbar-highlight-color:#f4f6fb;
  scrollbar-3dlight-color:#c7d0e6;
  scrollbar-face-color:#f4f6fb;
  scrollbar-shadow-color:#f4f6fb;
  scrollbar-darkshadow-color:#c7d0e6;
  scrollbar-track-color:#f4f6fb;
  scrollbar-arrow-color:#607ddb;
}

#layer0content ul
{
  margin: 0;
  padding: 0;
  list-style-type: none;
}

#layer0content li
{
  margin-left: .5em;
  margin-top: .2em;
  border: 0;
  padding: 0;
}

#layer0content a
{
  display: block;
  color: #036;
  background-color: #FFF;
  text-decoration: none;
  width:100%;
  border: 0;
  padding: 0;
  margin:0;
}

#layer0content a:hover
{
  background-color: #ccc;
  color: #FFF;
}

#layer0footer
{
  clear: both;
  margin: 0;
  padding: 0;
  color: #333;
  background-color: #ddd;
  text-align:right;
}

#layer0footer a
{
  display: block;
  color: #036;
  font-weight: normal;
  text-decoration: none;
}


 // -->
 </style>
<script language="javascript">
<!--

function setCheck(arg){
   if(arg == "DWG" || arg == "ETC" || arg == "TRY" || arg == "GATE" || arg=="ECO" || arg=="COST" || arg=="SALES"){
    document.getElementById('docType').style.display = 'none';
    //document.forms[0].category3.disabled = true;
    document.forms[0].objType.value= arg;
  }else{
    document.getElementById('docType').style.display = '';
    //document.forms[0].category3.disabled = false;
    document.forms[0].objType.value = arg;
  } 
   
   if(arg =="DOC" || arg == "GATE"){
	   document.getElementById('template').style.display = '';
   }else{
	   document.getElementById('template').style.display = 'none';
   }
   
   if(arg == "DOC" || arg == "DWG" || arg == "ECO"){
	   document.getElementById('subject').style.display = '';
   }else{
	   document.getElementById('subject').style.display = 'none';
	   document.getElementById('category4').options[1].selected = true;
   }
	//document.forms[0].objType.value = arg;
}

function offLayer(layerid) {
  var tLayer = document.getElementById(layerid);
  tLayer.style.visibility = "hidden";
}

function onLayer(layerid) {
  showLayerLocation(layerid);

  var tLayer = document.getElementById(layerid);
  tLayer.style.visibility = "visible";
}

function onLayerClear(contentid) {
  var layerContent = document.getElementById(contentid);
  var layerUL = layerContent.all.tags("ul");

  var childLen = layerContent.children.length;
  if(childLen) {
    for(var i = 0; i < childLen; i++) {
      layerContent.removeChild(layerContent.children(i));
    }
  }
}

function offLayerClear(contentid) {
  var layerContent = document.getElementById(contentid);
  var layerUL = layerContent.all.tags("ul");

  layerUL.innerHTML = "<img src=\"/plm/portal/images/img_loading/ajax-loader1.gif\" hspace='50' vspace='59' border='0' width='32' height='32'>Loading...";
}

function showLayerLocation(layerid){
  //var cx = window.event.clientX;
  //var cy = window.event.clientY;
  var tLayer = document.getElementById(layerid);
  tLayer.style.left = 420;//cx-200;
  tLayer.style.top = 195;//cy+10;
}
// -->
</script>
<div id="layer0" style="width:200px;">
<div id="layer0content" style="height:150px;">
<ul>
  <!--
      <li><a href="#">content1</a></li>
      <li><a href="#">content2</a></li>
      <li><a href="#">content3</a></li>
      <li><a href="#">content3</a></li>
       -->
  <img src='/plm/portal/images/img_loading/ajax-loader1.gif'
    hspace='84' vspace='59' border='0' width='32' height='32'>
</ul>
</div>
<%-- <div id="layer0footer"><a href="#"
  onclick="javascript:offLayer('layer0');"> [<%=messageService.getString("e3ps.message.ket_message", "01197") %>닫기] <!--
      <img src='/plm/portal/images/img_common/autosearch_close.gif' border='0' hspace='2' vspace='1' width='13' height='13'>
     --> </a></div>
</div> --%>
<!-- Layer end --></form>
</body>
</html>
