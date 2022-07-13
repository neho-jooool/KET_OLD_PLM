<%@page import="java.text.SimpleDateFormat"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="wt.fc.ReferenceFactory,
                        wt.iba.value.IBAHolder,
                        wt.inf.container.WTContainerRef,
                        wt.lifecycle.LifeCycleHelper,
                        wt.lifecycle.LifeCycleTemplate,
                        wt.lifecycle.PhaseTemplate,
                        wt.lifecycle.State,
                        wt.org.WTUser"%>
<%@page import="e3ps.edm.CADCategory,
                        e3ps.edm.CADManageType,
                        e3ps.edm.CADAppType,
                        e3ps.edm.DevStage,
                        e3ps.edm.beans.EDMHelper,
                        e3ps.edm.util.EDMProperties,
                        e3ps.edm.util.EDMUtil,
                        e3ps.common.code.NumberCode,
                        e3ps.common.code.NumberCodeHelper,
                        e3ps.common.util.*,
                        ext.ket.part.base.service.PartBaseHelper"%>
<%@page import="e3ps.common.util.KETStringUtil"%>
<%@page import="e3ps.common.util.KETParamMapUtil"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%

  // EJS TreeGrid Start
  response.addHeader("Cache-Control","max-age=1, must-revalidate");

  String pagingSizeDefault = PropertiesUtil.getSearchPagingSizeDefault();
  String pagingSize = null;

  if( searchCondition != null && !searchCondition.isEmpty() ) {
      pagingSize = searchCondition.getString("perPage");
  }

  if ( pagingSize == null || pagingSize.trim().length() == 0 ) {
      pagingSize = pagingSizeDefault;
  }
  // EJS TreeGrid End

  if ( request.getAttribute("openSearch") == "Y" ) {
        Calendar cal = Calendar.getInstance();
        searchCondition.put("create_end", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        cal.add(Calendar.MONTH, -1);
        searchCondition.put("create_start", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
  }

  EDMProperties edmProperties = EDMProperties.getInstance();

  WTContainerRef wtContainerRef = EDMUtil.getWTContainerRef(edmProperties.getContainer());
  String edmLC = edmProperties.getEPMDefaultLC();
  LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate(edmLC, wtContainerRef);
  Vector stateVec = LifeCycleHelper.service.getPhaseTemplates(lct);

  String command = searchCondition.getString("command");
  String number = searchCondition.getString("number");
  String name = searchCondition.getString("name");
  String partNumber = searchCondition.getString("partNumber");

  String[] state = searchCondition.getStringArray("state"); // MultiCombo
  String latest = searchCondition.getString("latest");

  String[] businessType = searchCondition.getStringArray("businessType"); // MultiCombo

  String[] devStage = searchCondition.getStringArray("devStage"); // MultiCombo
  String[] category = searchCondition.getStringArray("category"); // MultiCombo
  String[] cadAppType = searchCondition.getStringArray("cadAppType"); // MultiCombo
  String isDummyFile = searchCondition.getString("isDummyFile");

  String createStart = searchCondition.getString("create_start");
  String createEnd = searchCondition.getString("create_end");
  String modifyStart = searchCondition.getString("modify_start");
  String modifyEnd = searchCondition.getString("modify_end");

  String creator = searchCondition.getString("creator");
  String modifier = searchCondition.getString("modifier");

  String edmCreateDeptName = searchCondition.getString("edmCreateDeptName");
  String edmModifyDeptName = searchCondition.getString("edmModifyDeptName");

  String projectNo = searchCondition.getString("projectNo");

  if ( latest.length() == 0 ) {
    latest = "true";
  }

  String from = searchCondition.getString("from");

  // 공통코드
  Map<String, Object> parameter = new HashMap<String, Object>();
  parameter.put("locale",   messageService.getLocale());
  parameter.put("codeType", "DIVISIONFLAG");
  List<Map<String, Object>> businessTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
  
  // 관리자(wcadmin)는 모두 가능하다.
  boolean[] authArry = PartBaseHelper.service.isMember("자동차사업부_제품설계", "전자사업부_제품설계", "자동차사업부_금형설계", "전자사업부_금형설계", "KETS_제품설계", "KETS_금형설계");
  boolean hasProdAuth = ( authArry[0] || authArry[1] ||  authArry[4] || KETObjectUtil.isAdmin() );
  boolean hasMoldAuth = ( authArry[2] || authArry[3] ||  authArry[5] || KETObjectUtil.isAdmin() );
      
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Drawing Search</title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<%--
<script src="/plm/portal/js/treegrid/Grid_KET_S.js"></script>
 --%>
<!-- EJS TreeGrid End -->

<script type="text/javascript" src="/plm/jsp/edm/js/edm.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js?ver=1.0"></script>
<script type="text/javascript" src="/plm/portal/js/Calendar.js"></script>
<script type="text/javascript" src="/plm/jsp/groupware/js/board.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<style type="text/css">
/* body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
} */
table {
    border-spacing: 0;
    border: 0px;
}
table th, table td {padding: 0}
img {
    vertical-align: middle;
}
input {
    vertical-align:middle;line-height:22px;
}
</style>

<script type="text/javascript">
//<![CDATA[
           
$(document).ready(function(){
	
	treeGridResize("#EDMSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
});

function perPage(v) {
    document.forms[0].perPage.value = v;
    
    //search();
    loadEjsGrid();
}

function search(){
    var form = document.forms[0];
    var nn = form.number.value;

//     if(form.number.value==null || form.number.value.length<4){
//         alert("도면번호를 4자리이상 입력하시기 바랍니다.");
//         return;
//     }

    if((form.create_start.value != '') && !onValidCal(form.create_start,false)) {
        alert("'작성일자'를 'YYYY-MM-DD' 형식의 숫자를 입력하시기 바랍니다.");
        return;
    }
    if((form.create_end.value != '') && !onValidCal(form.create_end,false)) {
        alert("'작성일자'를 'YYYY-MM-DD' 형식의 숫자를 입력하시기 바랍니다.");
        return;
    }

    if((form.create_start.value != '') && (form.create_end.value != '') && !onCalcTime('create_start','create_end')) {
    	alert("'작성일자'의 시작일과 종료일의 날짜 순서가 맞지 않습니다.");
        return;
    }

    var epm_No = $("#number").val();
    var part_No = $("#partNumber").val();
    if( ( epm_No == "" || epm_No.indexOf('*') != -1 ) && ( part_No == "" || part_No.indexOf('*') != -1 ) ){
    	
	    var checkTwoCount = 0;
	    // 검색 조건 2가지 이상
	    if($("#businessType option").index($("#businessType option:selected")) != -1 ){
	    	checkTwoCount++;
	    };
	    if($("#devStage option").index($("#devStage option:selected")) != -1 ){
	    	checkTwoCount++;
	    };
	    if($("#category option").index($("#category option:selected")) != -1 ){
	    	checkTwoCount++;
	    };
	    if($("#cadAppType option").index($("#cadAppType option:selected")) != -1 ){
	    	checkTwoCount++;
	    };
	    if($("#state option").index($("#state option:selected")) != -1 ){
	    	checkTwoCount++;
	    };
	    if($("#partNumber").val() != "" ){
	    	//alert("partNumber:" + $("#partNumber").val());
	        checkTwoCount++;
	    };
	    if($("#number").val() != "" ){
	    	//alert("number:" + $("#number").val());
	        checkTwoCount++;
	    };
	    if($("#name").val() != "" ){
	    	//alert("name:" + $("#name").val());
	        checkTwoCount++;
	    };
	    if($("#projectNo").val() != "" ){
	    	//alert("projectNo:" + $("#projectNo").val());
	        checkTwoCount++;
	    };
	    if($("#creator").val() != "" ){
	    	//alert("creator:" + $("#creator").val());
	        checkTwoCount++;
	    };
	    if($("#edmCreateDeptName").val() != "" ){
	    	//alert("edmCreateDeptName:" + $("#edmCreateDeptName").val());
	        checkTwoCount++;
	    };
	    if($("#create_start").val() != "" ||  $("#create_end").val() != "" ){
	        checkTwoCount++;
	    };
	    
	    //alert(checkTwoCount);
	    if( checkTwoCount < 2){
	    	alert(" 부품번호, 도면번호 란에\n Full Code를 넣거나\n 검색 조건을 2가지 이상 추가해야 검색 가능 합니다.");
	    	return;
	    }
    }
    
    //showProcessing();
    //disabledAllBtn();
    //form.command.value="search";
    //form.method = "post";
    //form.target = "_self";
    //form.action ="/plm/servlet/e3ps/EDMServlet";
    //form.submit();
    
    loadEjsGrid();
}

function loadEjsGrid(){
	
    try{
       
       var idx = 0;
       var D = Grids[idx].Data;
       var formName = "form01";
       
       D.Layout.Param.Pagingsize = $("input[name='perPage']").val();
       
       D.Data.Url = "/plm/servlet/e3ps/EDMServlet";
       D.Data.Params = decodeURIComponent($('[name=' + formName + ']').serialize());
       D.Data.Param.command = "searchGridData";
       //D.Data.Params = "command=searchGridData&create_start=" + $("#create_start").val() + "&create_end=" + $("#create_end").val() + "&perPage="+ $("input[name='perPage']").val();
       
       D.Page.Url = "/plm/servlet/e3ps/EDMServlet";
       D.Page.Params =  D.Data.Params;
       D.Page.Param.command = "searchGridPage";
       
       Grids[idx].Reload(D);
       //Grids[idx].ReloadBody(null,idx,"Search");
       
       var S = document.getElementById("Status"+idx);
       if(S) S.innerHTML="Loading";
    
    }catch(e){
    	alert(e.message);
    }
}

//Grids.OnClick = function(grid,row,col,x,y){
//    if(col != 'Panel' && row.Oid){
//         openView(row.Oid);
//    }
//}
    
function doViewEPM(_oid) {

    var vparam = "";
    vparam += "frompage=SearchPage";
    vparam += "&oid="+_oid;

    document.forms[0].method = "post";
    document.forms[0].target = "_self";
    document.forms[0].action = "/plm/jsp/edm/ViewEPMDocument.jsp?"+vparam;;
    document.forms[0].submit();
    
    var url = "/plm/ext/part/base/registPartForm.do?popup=popup";
    var name = "";
    var defaultWidth = 1024; // 1150;
    var defaultHeight = 800; // 800;
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
    
}

function excelDown() {
    document.forms[0].method = "post";
    document.forms[0].target = "_self";
    document.forms[0].action = "/plm/servlet/e3ps/EDMServlet?command=downloadSearchResult";
    document.forms[0].submit();
}

function openCal(variableName) {
    var str="/plm/jsp/common/calendar.jsp?obj="+variableName;
    var opts = "toolbar=0,location=0,directory=0,status=0,menubar=0,scrollbars=0,resizable=0,";
    leftpos = (screen.width - 224)/ 2;
    toppos = (screen.height - 230) / 2 ;
    rest = "width=224,height=230,left=" + leftpos + ',top=' + toppos;

    var newwin = window.open( str , "calendar", opts+rest);
    newwin.focus();
}

// ==  [Start] 부품검색 팝업  == //
function selectPartNo(targetId, mode){ // mode: m | s
	
	_callBackFuncTargetId = targetId;
    if( typeof mode != 'undefined' && ('m' == mode || 'M' == mode )){
    	showProcessing();
    	SearchUtil.selectPart("callBackFuncPartPopup");
    }else{
    	showProcessing();
    	SearchUtil.selectOnePart("callBackFuncPartPopup");
    }
}

var _callBackFuncTargetId;
function callBackFuncPartPopup(selectedPartAry){
    if ( typeof selectedPartAry != "undefined" && selectedPartAry.length > 0) {
        var isAppend = "Y";
        acceptPartNo(_callBackFuncTargetId, selectedPartAry, isAppend);
    }
}

function acceptPartNo(targetId, arrObj, isAppend) {
    var partData = new Array();
    var partMasterOid = new Array();
    for ( var i=0; i < arrObj.length; i++ ) {
        // [0] - wtpart oid       // [1] - part number    // [2] - part name
        // [3] - part version     // [4] - part type      // [5] - die number
        // [6] - die name         // [7] - die cnt        // [8] - wtpartmaster oid

        var infoArr = arrObj[i];
        partData[i] = infoArr[1];
        partMasterOid[i] = infoArr[8];
    }

    var tmpNo  = new Array();
    var tmpMasterOid = new Array();
    if ( $("#"+targetId).val() != "" && isAppend == "Y" ) {
        // ID 중복 제거
        tmpNo = $.merge($("#"+targetId).val().split(", "), partData);
        tmpNo = $.unique(tmpNo.sort());

        tmpMasterOid = $.merge($("#"+targetId+"MasterOid").val().split(", "), partMasterOid);
        tmpMasterOid = $.unique(tmpMasterOid.sort());
    }
    else {
        tmpNo  = partData.sort();
        tmpMasterOid = partMasterOid.sort();
    }

    $("#"+targetId).val(tmpNo.join(", "));
    $("#"+targetId+"MasterOid").val(tmpMasterOid.join(", "));
}

function clearPartNo(targetId) {
    $("#" + targetId).val("");
    $("#" + targetId+"MasterOid").val("");
}
//==  [End] 부품검색 팝업  == //

var projectIdTemp = "";

// 프로젝트 검색 팝업 Open
function popupProject(targetId) {
	projectIdTemp = targetId;
	var url = "/plm/jsp/project/SearchPjtPop.jsp?status=P&type=P&modal=&mode=m&fncall=setProject";
	var opts = "toolbar=0, location=0, directory=0, status=1, menubar=0, scrollbars=1, resizable=0";
	  
	getOpenWindow2(url,"searchEpmDocument", '1024', '768', opts);
	
}

function setProject(arrObj) {
	
	if (arrObj == undefined && arrObj.length == 0) {
		return;
	}
	var targetId = projectIdTemp;
	var isAppend = "Y";
	
    var projectNoData = new Array();
    var projectOidData = new Array();
    for ( var i=0; i < arrObj.length; i++ ) {
        // [0] -  oid       // [1] - project number
        var infoArr = arrObj[i];
        projectNoData[i] = infoArr[1];
        projectOidData[i] = infoArr[0];
    }

    var tmpNo = new Array();
    var tmpOid = new Array();
    if ( $("#"+targetId).val() != "" && isAppend == "Y" ) {
        // ID 중복 제거
        tmpNo = $.merge($("#"+targetId).val().split(", "), projectNoData);
        tmpNo = $.unique(tmpNo.sort());

        tmpOid = $.merge($("#"+targetId+"Oid").val().split(", "), projectOidData);
        tmpOid = $.unique(tmpOid.sort());
    }
    else {
        tmpNo = projectNoData.sort();
        tmpOid = projectOidData.sort();
    }

    $("#"+targetId).val(tmpNo.join(", "));
    $("#"+targetId+"Oid").val(tmpOid.join(", "));
}
function deleteProject() {
    document.forms[0].projectNo.value = "";
    document.forms[0].projectNoOid.value = "";
}

//부서 검색 팝업  Open

//==  [Start] 부서 검색  == //
function doDeleteDepartment() {
    document.forms[0].edmCreateDept.value = "";
    document.forms[0].edmCreateDeptName.value = "";
}

function addDepartment() {
    var url = "/plm/extcore/jsp/project/AddProjectDeptFrm.jsp?mode=m&invokeMethod=setDepartMent";
    var opts = "toolbar=0, location=0, directory=0, status=1, menubar=0, scrollbars=1, resizable=0";
    getOpenWindow2(url,"searchEpmDocumentSetDept", '430', '465', opts);
}

function setDepartMent(arrObj){
	var departOid = "";
	var departName = "";
	for(var i= 0 ; i < arrObj.length; i++){
        departOid += arrObj[i].OID+",";
        departName += arrObj[i].NAME+",";
    }
	deptMerge(departOid, departName, "edmCreateDept", "edmCreateDeptName");
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

    deptMerge(decodeURIComponent(l_oid[0].text), decodeURIComponent(l_name[0].text), "edmCreateDept", "edmCreateDeptName");
}

function deptMerge(deptOid, deptName, targetId, targetName) {
    if ( $("#"+targetId).val() == "" ) {
        $("#"+targetId).val( deptOid );
        $("#"+targetName).val( deptName );
    }
    else {
        var deptOidArr = $("#"+targetId).val().split(", ");
        var deptNameArr = $("#"+targetName).val().split(", ");
        // 선택된 부서 push
        deptOidArr.push(deptOid);
        deptNameArr.push(deptName);
        // 중복 부서 정리
        deptOidArr = $.unique(deptOidArr.sort());
        deptNameArr = $.unique(deptNameArr.sort());

        $("#"+targetId).val( deptOidArr.join(", ") );
        $("#"+targetName).val( deptNameArr.join(", ") );
    }
}
// ==  [End] 부서 검색  == //

function reset() {
  $("#partNumber").val("");
  $("#number").val("");
  $("#name").val("");
  $("#projectNo").val("");
  $("#creator").val("");
  $("#creatorName").val("");
  $("#edmCreateDeptName").val("");
  $("#create_start").val("");
  $("#create_end").val("");
  $("#latest").chacked = false;
  $("#isDummyFile").chacked = false;

  // MultiCombo
  $("#businessType").multiselect("uncheckAll");
  $("#devStage").multiselect("uncheckAll");
  $("#category").multiselect("uncheckAll");
  $("#cadAppType").multiselect("uncheckAll");
  $("#state").multiselect("uncheckAll");

  //결과내재검색 체크해제
  $("input:checkbox[id=searchInSearch]").attr("checked", false);
}

//Jquery
$(document).ready(function(){
  // Enter 검색
  $("form[name=form01]").keypress(function(e) {
      if ( e.which == 13 ) {
          search();
          return false;
      }
  });

  $("#businessType").multiselect({
	  minWidth: 150,
      noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
      checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
      uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
  });
  $("#devStage").multiselect({
	  minWidth: 150,
      noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
      checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
      uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
  });
  $("#category").multiselect({
	  minWidth: 150,
      noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
      checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
      uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
  });
  $("#cadAppType").multiselect({
	  minWidth: 150,
      noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
      checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
      uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
  });
  $("#state").multiselect({
	  minWidth: 150,
      noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
      checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
      uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
  });
});

function registProdEpm(){
	
    var url = "/plm/jsp/common/loading.jsp?url=/plm/jsp/edm/CreateEPMDocument.jsp&key=manageType&value=PRODUCT_DRAWING";
    //var regWin = window.open(url,"","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,width=1150,height=800");
    var name = "";
    var defaultWidth = 1024;
    var defaultHeight = 900;
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
}

function registMoldEpmOne(){
	
    var url = "/plm/jsp/common/loading.jsp?url=/plm/jsp/edm/CreateEPMDocument.jsp&key=manageType&value=MOLD_DRAWING";
    //var regWin = window.open(url,"","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,width=1150,height=800");
    var name = "";
    var defaultWidth = 1024;
    var defaultHeight = 900;
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
}


function registMoldEpmAll(){
	//var url = "/plm/jsp/edm/edmBatchCnt.jsp";
    var url = "/plm/ext/edm/saveEdmBatch";
    //var regWin = window.open(url,"","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,width=1150,height=800");
    var name = "";
    var defaultWidth = 1100;
    var defaultHeight = 800;
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
}

//UI 초기화
$(document).ready(function(){
    //------------ start suggest binding ------------//
    SuggestUtil.bind('PARTNO', 'partNumber');
    SuggestUtil.bind('PRODUCTPROJNO', 'projectNo');
    SuggestUtil.bind('EPMNO', 'number');
    SuggestUtil.bind('USER', 'creatorName', 'creator');
    SuggestUtil.bind('DEPARTMENT', 'edmCreateDeptName', 'edmCreateDept');
    //------------ end suggest binding ------------//
    // default 한달 설정
    //$('[name=startDate]').val(predate);
    //$('[name=endDate]').val(postdate);
    // Calener field 설정
    /*
    CalendarUtil.dateInputFormat('planDate');
    CalendarUtil.dateInputFormat('startDate','endDate'); //기한 설정시 start와 end field를 설정한다.
    */
    // multiselect
    //CommonUtil.multiSelect('partStateCode',100);
    //CommonUtil.multiSelect('spPartDevLevel',100);
    /*
    CommonUtil.multiSelect('customerEvent2',100);
    CommonUtil.multiSelect('pjtState2',100);
    CommonUtil.multiSelect('authorStatus',100);
    */
    CalendarUtil.dateInputFormat('create_start','create_end'); 
    //client paging
    //Sample.createGrid();
    //server paging
    //PartList.createPaingGrid();
});

function helpTest(){
    var    text = " EM41~EM49,JB51~JB59,MG60~MG68,ST71~ST79, \n"; //,PG88~PG89
    text = text + " R40,R50,R60, \n";
    text = text + " KP,KR, \n"; //,KH ,KD4,K,KW,KS,
    text = text + " WH,WH31~WH32, \n\n"; // WH33
    
    //text = text + " 제품 : 610056-3, 477016-09, KL10024-00, H615390-2, S200406, R103081, 310464-AS \n";
    //text = text + " Die No : 1T389000 22089001 10860C00 22845A00 LP020000 1Y007000 10929000 1A171000 2T220000 \n";
    //text = text + " 금형 부품 : 11141000-P52, 11183000-STD27, 10861B00-P18-7 11133-D09-2 21906A00-019 1T389000-D09 \n";
    alert(text);
}

function helpTestPart(){
    var    text = " <<제품>> :\n H31~H33, H41~H43, H45~H49, H51~H67, H71~H79, \n"; 
    text = text + " H88, H89,H31~H33, H41~H43, H45~H49, H51~H67, \n";
    text = text + " H71~H79, H88, H89, HKW, HKS, HKH, HKP, HK, K, KD4, KR, \n";
    text = text + " 31, R40~R42, R44, R50, R60, R68 \n\n";
    text = text + " <<Die>> :\n 10,11,1A,1T,1Y,20,21,22,23,26,29,2A,2T,\n 30,40,60,LC,LI,LM,LP,YT \n\n"; 
    text = text + " <<Mold>> :\n 07,10,11,13,16,19,1A,1R,1T,1Y,20,21,22,\n23,24,25,26,27,29,2A,2T,30,40,D0 \n";
    text = text + " ,D1,D2,D3,D4,D5,D6,D7,D8,D9,EJ,ET,IY,L0,LI,LM,LP,\nP0,P1,P2,P3,P4,P5,P6,P7,P8 \n";
    text = text + " ,P9,R0,S1,S2,S3,S4,S7,S8,S9,ST,U0,UP,Z4 \n";
    //text = text + " 제품 : 610056-3, 477016-09, KL10024-00, H615390-2, S200406, R103081, 310464-AS \n";
    //text = text + " Die No : 1T389000 22089001 10860C00 22845A00 LP020000 1Y007000 10929000 1A171000 2T220000 \n";
    //text = text + " 금형 부품 : 11141000-P52, 11183000-STD27, 10861B00-P18-7 11133-D09-2 21906A00-019 1T389000-D09 \n";
    alert(text);
}


function downCadFile( url ){
    var name = "";
    defaultWidth = 500;
    defaultHeight = 500;
    var opts = ",scrollbars=1,resizable=0";
    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
}

//]]>
</script>
</head>

<body class="body-space">
<form name="form01" autocomplete="off">
<!-- hidden -->
<input type="hidden" name="command" value="<%=command %>">
<input type="hidden" name="from" value="<%=from %>">
<input type="hidden" name="perPage" value="<%=pagingSize %>" />
<!-- hidden end -->

<table style="width:100%;" id="cndHeader" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table style="width:100%; height:28px;" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td style="width:20px;"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01262") %><%--도면검색--%></td>
                                <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01263") %><%--도면관리--%><img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01262") %><%--도면검색--%></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td  class="head_line"></td>
                </tr>
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>
            <table style="width:100%;" border="0" cellspacing="0" cellpadding="0" >
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                            <!--
                                <td>
                                    <input type="checkbox" id="searchInSearch" name="searchInSearch" value="searchInSearch" <%if ( searchCondition.getString("searchInSearch").equals(("searchInSearch")) ){%> checked <%} %>/><%=messageService.getString("e3ps.message.ket_message", "00749") %><%--결과 내 재검색--%>
                                </td>
                             -->
                                <% if(hasProdAuth){ %>
                                <td style="width: 15px;">&nbsp;</td>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:registProdEpm();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02587") %><%--제품도면등록--%></a></td>
                                            <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <% } %>
                                <% if(hasMoldAuth){ %>
                                <td style="width: 5px;">&nbsp;</td>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:registMoldEpmOne();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01061") %><%--금형도면등록(단품)--%></a></td>
                                            <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <td style="width: 5px;">&nbsp;</td>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:registMoldEpmAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01062") %><%--금형도면등록(일괄)--%></a></td>
                                            <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <% } %>
                                <td style="width: 5px;">&nbsp;</td>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:reset();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                                            <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <td style="width:5px;">&nbsp;</td>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                                            <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" style="width:100%;">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <!-- 검색영역 collapse/expand -->
            <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
            <table border="0" cellspacing="0" cellpadding="0" style="width:100%;">
                <tr>
                    <td  class="tab_btm2"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" style="width:100%;">
                <colgroup>
                    <col width="14%" />
                    <col width="19%" />
                    <col width="14%" />
                    <col width="19%" />
                    <col width="14%" />
                    <col width="19%" />
                </colgroup>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                    <td class="tdwhiteL"><input type="text" name="number" class="txt_field"  style="width:98%" value="<%=number%>" id="number"></td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                    <td class="tdwhiteL"><input type="text" name="name" class="txt_field"  style="width:98%" value="<%=name%>" id="name"></td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01264") %><%--도면구분--%></td>
                    <td class="tdwhiteL0">
                        <select id="devStage" name="devStage" class="fm_jmp" style="width:150px" onFocus='this.initialSelect = this.selectedIndex;' multiple="multiple"> <!-- onChange='this.selectedIndex = this.initialSelect;'>  -->
                            <%
                                DevStage[] dsArr = DevStage.getDevStageSet();
                                for(int i = 0; i < dsArr.length; i++) {
                            %>
                                    <OPTION VALUE="<%=dsArr[i].toString()%>" <%=(KETParamMapUtil.contains(devStage, dsArr[i].toString())) ? " selected" : "" %>><%=dsArr[i].getDisplay(messageService.getLocale())%></OPTION>
                            <%
                                }
                            %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00102") %><%--CAD종류--%></td>
                    <td class="tdwhiteL">
                        <select id="cadAppType" name="cadAppType" class="fm_jmp" style="width:150px" onFocus='this.initialSelect = this.selectedIndex;' multiple="multiple"> <!-- onChange='this.selectedIndex = this.initialSelect;'>  -->
                            <%
                                CADAppType[] cadAppTypeArr = CADAppType.getCADAppTypeSet();
                                for(int i = 0; i < cadAppTypeArr.length; i++) {
                            %>
                                    <OPTION VALUE="<%=cadAppTypeArr[i].toString()%>" <%=(KETParamMapUtil.contains(cadAppType, cadAppTypeArr[i].toString())) ? " selected" : "" %>><%=cadAppTypeArr[i].getDisplay(messageService.getLocale())%></OPTION>
                            <%
                                }
                            %>
                        </select>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                    <td class="tdwhiteL">
                        <select id="state" name="state" class="fm_jmp" style="width:150px" multiple="multiple">
                            <%
                                PhaseTemplate pt = null;
                                State lcState = null;
                                for(int i = 0; i < stateVec.size(); i++) {
                                    pt = (PhaseTemplate)stateVec.get(i);
                                    lcState = pt.getPhaseState();
                            %>
                                    <option value="<%=lcState.toString()%>" <%=(KETParamMapUtil.contains(state, lcState.toString())) ? " selected" : "" %>><%=lcState.getDisplay(messageService.getLocale())%></option>
                            <%  }
                            %>
                        </select>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01288") %><%--도면유형--%></td>
                    <td class="tdwhiteL0">
                        <select id="category" name="category" class="fm_jmp" style="width:150px" onFocus='this.initialSelect = this.selectedIndex;' multiple="multiple"> <!-- onChange='this.selectedIndex = this.initialSelect;'>  -->
                            <%
                                CADManageType[] cmtArr = CADManageType.getCADManageTypeSet();
                                for(int i = 0; i < cmtArr.length; i++) {
                                    CADManageType cmt = cmtArr[i];
                            %>
                                    <OPTION VALUE="">-----<%=cmt.getDisplay(messageService.getLocale())%>-----</OPTION>
                            <%
                                    CADCategory[] catArr = edmProperties.getCADCategorySet(cmt.toString());
                                    for(int k = 0; k < catArr.length; k++) {
                            %>
                                        <OPTION VALUE="<%=catArr[k].toString()%>" <%=(KETParamMapUtil.contains(category, catArr[k].toString())) ? " selected" : "" %>><%=catArr[k].getDisplay(messageService.getLocale())%></OPTION>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%></td>
                    <td class="tdwhiteL">
                        <select id="businessType" name="businessType" class="fm_jmp" style="width:150px" multiple="multiple">
                        <%
                        for ( int i=0; i<businessTypeNumCode.size(); i++ ) {
                        %>
                            <option value="<%=businessTypeNumCode.get(i).get("oid") %>" <%=(KETParamMapUtil.contains(businessType, businessTypeNumCode.get(i).get("oid")) ) ? " selected" : "" %>><%=businessTypeNumCode.get(i).get("value")%></option>
                        <%
                        }
                        %>
                        </select>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                    <td class="tdwhiteL">
                        <input type="hidden" id="partNumberMasterOid" name="partNumberMasterOid" value="<%=searchCondition.getString("partNumberMasterOid")%>">
                        <input type="text" id="partNumber" name="partNumber" class="txt_field" style="width:70%" value="<%=partNumber%>" >
                        <a href="javascript:;" onClick="javascript:selectPartNo('partNumber', 'm');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                        <a href="javascript:;" onClick="javascript:clearPartNo('partNumber');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
                    <td class="tdwhiteL0">
                        <input type="hidden" id="projectNoOid" name="projectNoOid" value="<%=searchCondition.getString("projectNoOid")%>">
                        <input type="text" id="projectNo" name="projectNo" class="txt_field" style="width:70%" value="<%=projectNo%>">
                        <a href="javascript:;" onClick="javascript:popupProject('projectNo');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                        <a href="javascript:;" onClick="javascript:deleteProject();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                    <td class="tdwhiteL">
                        <input type="hidden" id="creator" name="creator" value="<%=searchCondition.getString("creator")%>">
                        <input type="text" id="creatorName" name="creatorName"  class="txt_field"  style="width:70%;" value="<%=searchCondition.getString("creatorName")%>">
                        <a href="javascript:;" onClick="javascript:selectUserOid('creator','creatorName');">
                        <img src="/plm/portal/images/icon_user.gif" border="0"></a>
                        <a href="javascript:;" onClick="javascript:CommonUtil.deleteValue('creator','creatorName');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                    <td class="tdwhiteL">
                        <input type="hidden" id="edmCreateDept" name="edmCreateDept" value="<%=searchCondition.getString("edmCreateDept")%>">
                        <input type="text" id="edmCreateDeptName" name="edmCreateDeptName" class="txt_field"  style="width:70%" value="<%=searchCondition.getString("edmCreateDeptName")%>">
                        <a href="javascript:addDepartment();"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                        <a href="#" onfocus="this.blur();" onClick="javascript:doDeleteDepartment();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00174") %><%--Dummy File 여부--%></td>
                    <td class="tdwhiteL0">
                        <input name="isDummyFile" type="radio" class="Checkbox" value="" <%if(isDummyFile.equals("")){%>checked<%}%>><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%>
                        <!--
                        <input name="isDummyFile" type="radio" class="Checkbox" value="<%=EDMHelper.IBA_DUMMY_FILE_VALUE_YES%>" <%if(isDummyFile.equals(EDMHelper.IBA_DUMMY_FILE_VALUE_YES)){%>checked<%}%>>Y
                        <input name="isDummyFile" type="radio" class="Checkbox" value="<%=EDMHelper.IBA_DUMMY_FILE_VALUE_NO%>" <%if(isDummyFile.equals(EDMHelper.IBA_DUMMY_FILE_VALUE_NO)){%>checked<%}%>>N
                      r-->
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                    <td class="tdwhiteL" colspan="3">
                        <input type="text" id="create_start" name="create_start" class="txt_field" style="width: 80px;"  value="">
                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('create_start');">
                        ~ 
                        <input type="text" id="create_end" name="create_end" class="txt_field" style="width: 80px;" value="">
                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('create_end');" >
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                    <td class="tdwhiteL0"><input name="latest" type="radio" class="Checkbox" value="true" <%if("true".equals(latest)){%>checked<%}%>><%=messageService.getString("e3ps.message.ket_message", "02839") %><%--최신--%><input name="latest" type="radio" class="Checkbox" value="false" <%if("false".equals(latest)){%>checked<%}%>><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%> </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" style="width:100%;">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>

            <!-- EJS TreeGrid Start -->
            <div class="content-main">
                <div class="container-fluid">
                    <div class="row-fluid">
                        <div id="listGrid" style="WIDTH:100%;HEIGHT:100%">
                            <bdo 
                                <%--
                                Debug='3' 
                                --%>
                                Debug="1"
                                AlertError="0"
                                
                                Layout_Url="/plm/jsp/edm/SearchEPMDocumentGridLayout.jsp"
                                Layout_Param_Pagingsize="<%=pagingSize %>"

                                Data_Url="/plm/jsp/common/treegrid/InitGridData.jsp"
				                Data_Format="Internal"
				                Data_Data="TGData"
				                
				                Data_Timeout="0"
				                Page_Timeout="0"
				                
				                Data_Method="POST"
				                
				                Page_Url="/plm/jsp/common/treegrid/InitGridPage.jsp"
				                Page_Format="Internal"
				                Page_Data="TGData"
				                Page_Method="POST"
				                
                                Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_Drawing_List"
                            ></bdo>
                        </div>
                    </div>
                </div>
            </div>
            <!-- EJS TreeGrid End -->

        </td>
    </tr>
</table>

<div id="copyright_div" style="clear:both;margin:0;padding:0;border:0;bottom:0;width:100%;height:30px;">
    <iframe src="/plm/portal/common/copyright.html" id="copyright" name="copyright" style="width:100%; height:24px;" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
</div>
</form>
</body>
</html>
