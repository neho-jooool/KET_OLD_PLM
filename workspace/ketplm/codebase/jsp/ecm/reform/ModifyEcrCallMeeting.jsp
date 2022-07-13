<%@ page import="java.util.Hashtable
              ,java.util.ArrayList
              ,java.util.Vector
              ,java.util.List
              ,java.util.Map
              ,java.util.HashMap
              ,e3ps.common.code.*
              ,e3ps.common.util.StringUtil
              ,e3ps.common.util.DateUtil
              ,e3ps.common.util.CommonUtil
              ,e3ps.common.util.KETObjectUtil
              ,e3ps.common.code.NumberCode
              ,e3ps.common.code.NumberCodeHelper
              ,e3ps.ecm.beans.ProdEcrBeans
              ,e3ps.ecm.entity.*
              ,e3ps.common.content.ContentInfo
              ,e3ps.common.content.ContentUtil
              ,e3ps.groupware.company.Department
              ,e3ps.ecm.entity.*
              ,wt.org.WTUser
              ,wt.fc.*
              ,wt.query.*
              ,wt.change2.WTChangeOrder2
              ,wt.content.ContentHelper
              ,wt.content.ContentHolder
              ,wt.content.ContentItem
              ,wt.session.SessionHelper
              ,wt.content.ApplicationData" %>

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@page import="java.sql.Timestamp"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="prodEcr" class="wt.change2.WTChangeRequest2" scope="request"/>
<jsp:useBean id="prePage" class="java.lang.String" scope="request"/>
<jsp:useBean id="meeting" class="e3ps.ecm.entity.KETMeetingMinutes" scope="request"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%
  // 결재대상 화면 여부
  boolean isIframe = Boolean.parseBoolean(StringUtil.checkReplaceStr(request.getParameter("isIframe"), "false"));

  if (prodEcr instanceof e3ps.ecm.entity.KETProdChangeRequest) {
    prodEcr = (e3ps.ecm.entity.KETProdChangeRequest) prodEcr;
  } else if (prodEcr instanceof e3ps.ecm.entity.KETMoldChangeRequest) {
    prodEcr = (e3ps.ecm.entity.KETMoldChangeRequest) prodEcr;
  } 
  String ecrOid = CommonUtil.getOIDString( prodEcr );
  String ecrStateCode = prodEcr.getLifeCycleState().toString();

  // 공통정보
  Map<String, Object> parameter = new HashMap<String, Object>();
  parameter.put("locale", messageService.getLocale());

  ProdEcrBeans beans = new ProdEcrBeans();
  Hashtable<String, ArrayList<Hashtable<String,String>>> codeHash = beans.getInitCodeList(parameter);

  ArrayList<Hashtable<String, String>> devFlagList = codeHash.get("devYn");
  ArrayList<Hashtable<String, String>> divList = codeHash.get("division");
  ArrayList<Hashtable<String, String>> chgTypeList = codeHash.get("changeType");    // 사용하지 않는다.
  ArrayList<Hashtable<String, String>> reasonList = codeHash.get("changeReason");

  WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();

  
  //제품, 금형 ECR 확장팩
  KETChangeRequestExpansion expansion = null;
  // ECR 로 찾는다.
  QuerySpec spec = new QuerySpec(KETChangeRequestExpansion.class);
  spec.appendWhere(new SearchCondition(KETChangeRequestExpansion.class, "ecrReference.key.id", "=", CommonUtil.getOIDLongValue(prodEcr)), new int[] { 0 });
  QueryResult result = PersistenceHelper.manager.find(spec);
  if (result.hasMoreElements()) { // while (result.hasMoreElements()) {
    expansion = (KETChangeRequestExpansion) result.nextElement();
  }
  
  
  // 회의록
  String pboOid = "";
  String stateDisplayName = "";
  String stateCode = "";
  
  String meetingName = "";
  String meetingDate = "";
  String meetingTime = "";
  String meetingLocation = "";
  String attendance = "";
  
  String subjectDisplayName = "";
  String subjectOid = "";
  String subjectCode = "";
  String chargeDisplayName = "";
  String chargeOid = "";
  String chargeName = "";

  String webEditor = "";
  String webEditorText = "";
  
  // 첨부파일
  ContentHolder holder = null;
  Vector attachFileList = null;

  // ECN
  KETChangeNotice ecn = null;
  
  // 권한
  boolean isOwner = false;  // 작성자
  boolean isChief = false;  // 부서장
  boolean isCharge = false; // 담당자

  if (PersistenceHelper.isPersistent(meeting)) {
      
      pboOid = StringUtils.stripToEmpty(CommonUtil.getOIDString( meeting ));
      stateDisplayName = meeting.getLifeCycleState().getDisplay(messageService.getLocale());
      stateCode = meeting.getLifeCycleState().toString();
      
      // 회의명
      meetingName = HtmlUtils.htmlEscape(StringUtils.stripToEmpty(meeting.getMeetingName()));
      // 회의일시
      meetingDate = DateUtil.getTimeFormat(meeting.getMeetingDate(), "yyyy-MM-dd"); 
      // 회의시작
      meetingTime = StringUtils.stripToEmpty(meeting.getMeetingTime());
      // 회의장소
      meetingLocation = StringUtils.stripToEmpty(meeting.getMeetingLocation());
      // 회의 참석자
      attendance = StringUtils.stripToEmpty(meeting.getAttendance());
      
      // 주관부서
      Department subject = meeting.getSubject();
      subjectDisplayName = (subject != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(subject.getName())) : "";
      subjectOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(subject));
      subjectCode = StringUtils.stripToEmpty(meeting.getSubjectCode());
      
      // 담당자
      WTUser charge = meeting.getCharge();
      chargeDisplayName = (charge != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(charge.getFullName())) : "";
      chargeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(charge));
      chargeName = StringUtils.stripToEmpty(meeting.getChargeName());
      
      // 회의내용
      webEditor = (String) meeting.getWebEditor();
      webEditorText = (String) meeting.getWebEditorText();
      
      // 첨부파일
      holder = ContentHelper.service.getContents( (ContentHolder)meeting );
      attachFileList = ContentUtil.getSecondaryContents(holder);  
      
      // Link 처리
      KETEcrEcnLink ketEcrEcnLink = null;
      // ECR 로 찾는다.
      QueryResult qr = PersistenceHelper.manager.navigate(prodEcr, "ecn", KETEcrEcnLink.class, false);
      if (qr.hasMoreElements()) {    // while (qr.hasMoreElements()) {
	      ketEcrEcnLink = (KETEcrEcnLink) qr.nextElement();
	      ecn = ketEcrEcnLink.getEcn();

      }

      // 권한
      // 작성자
      WTUser owner = (WTUser)meeting.getCreator().getPrincipal();
      if(loginUser.equals(owner)) {
          isOwner = true;
      }
      // 부서장
      e3ps.groupware.company.PeopleData peoData = new e3ps.groupware.company.PeopleData(loginUser);
      String chief = peoData.chief; // 로그인한 사용자가 팀장이면 chief는 null도 아니고 ""도 아니다.
      if(!chief.equals("") && chief.equals( subjectDisplayName ) ) {
          isChief = true;
      }
      // 담당자
      if(loginUser.equals(charge)) {
          isCharge = true;
      }
      
  } else {
      meeting = null;
      
      
      // 주관부서
      Department subject = (expansion != null) ? expansion.getSubject() : null;
      subjectDisplayName = (subject != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(subject.getName())) : "";
      subjectOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(subject));
      subjectCode = StringUtils.stripToEmpty(((expansion != null) ? expansion.getSubjectCode() : null));
      
      // 담당자
      WTUser charge = (expansion != null) ? expansion.getCharge() : null;
      chargeDisplayName = (charge != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(charge.getFullName())) : "";
      chargeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(charge));
      chargeName = StringUtils.stripToEmpty(((expansion != null) ? expansion.getChargeName() : null));

      // 권한
      // 부서장
      e3ps.groupware.company.PeopleData peoData = new e3ps.groupware.company.PeopleData(loginUser);
      String chief = peoData.chief; // 로그인한 사용자가 팀장이면 chief는 null도 아니고 ""도 아니다.
      if(!chief.equals("") && chief.equals(subjectDisplayName)) {
          isChief = true;
      }
      // 담당자
      if(loginUser.equals(charge)) {
          isCharge = true;
      }
      
  }

%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>회의소집</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }

.headerLock {
  position: relative;
  top:expression(document.getElementById("div_scroll").scrollTop);
  z-index:99;
 }

.headerLock2 {
  position: relative;
  top:expression(document.getElementById("div_scroll2").scrollTop);
  z-index:99;
 }

 .headerLock3 {
  position: relative;
  top:expression(document.getElementById("div_scroll3").scrollTop);
  z-index:99;
 }
-->
</style>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/multicombo.jsp" %>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>
<script type="text/javascript">
$(document).ready(function(){

    //SuggestUtil.bind('DEPARTMENT', 'callDepartmentDisplayName', 'callDepartmentOid');
    SuggestUtil.multiBind('DEPTUSER', 'callDepartmentDisplayName', 'callDepartmentOid', 'callAttendanceDisplayName', 'callAttendanceOid');
    SuggestUtil.multiBind('USERDEPT', 'callAttendanceDisplayName', 'callAttendanceOid', 'callDepartmentDisplayName', 'callDepartmentOid');
    //SuggestUtil.multiBindCallBackFunc('USERDEPT', 'callAttendanceDisplayName', 'callAttendanceOid', deptCallBackFunc); 콜백 함수로 OBJECT 리턴
    //SuggestUtil.bind('USER', 'callAttendanceDisplayName', 'callAttendanceOid');


    CalendarUtil.dateInputFormat('callMeetingDate'); 
})
</script>
<script type="text/javascript">

//관련 ECO 상세조회 팝업
function viewEco(oid) {
    var url = "/plm/servlet/e3ps/ProdEcoServlet?prePage=S&cmd=View&oid=" + oid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

function goMoify()
{
    document.forms[0].cmd.value='ModifyView';
    document.forms[0].action="/plm/servlet/e3ps/ProdEcrServlet";
    disabledAllBtn();
    showProcessing();
    document.forms[0].submit();

}
function goReject()
{
    if(confirm("<%=messageService.getString("e3ps.message.ket_message", "04112") %><%--기각하시겠습니까?--%>")){
        document.forms[0].cmd.value='Reject';
        document.forms[0].target = 'download';
        document.forms[0].action = '/plm/servlet/e3ps/ProdEcrServlet';
        disabledAllBtn();
        showProcessing();
        document.forms[0].submit();
    }
}

function goCreateEco()
{
    document.forms[0].cmd.value = 'CreateECO';
    document.forms[0].action = '/plm/servlet/e3ps/ProdEcrServlet';
    disabledAllBtn();
    showProcessing();
    document.forms[0].submit();
}

function goList()
{
    //history.back();
    if( document.forms[0].prePage.value == 'Search' )
    {
        history.back();
    }
    else
    {
        document.forms[0].action = '/plm/jsp/ecm/SearchEcrForm.jsp';
        document.forms[0].submit();
    }
}

function requestApprove()
{
    var form = document.forms[0];
    //goPage(form.oid.value);
    goPage(form.pboOid.value);
}

//결재 상신 후 호출되는 피드백 function
function lfn_feedback_after_startProcess() {
   //alert('lfn_feedback_after_startProcess');
    try {
        parent.lfn_reloadWhole();
        
    } catch(e) {}
}

//부품 상세조회 팝업
function viewPart(v_poid){
    var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

//프로젝트상세조회 팝업
function viewProjectPopup(oid) {
    var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid=" + oid + "&popup=popup";
    openWindow(url, '',1050,800);
}

function viewIssue(oid){
    var url = '/plm/jsp/project/projectIssueView.jsp?oid='+oid;
    openWindow2(url,"","750","320","scrollbars=no,resizable=no,top=200,left=250");
}

//회의소집
function insertCallLine()
{
    var innerRow = relCallTable.insertRow();
    innerRow.height = "27";
    var rowIndex = innerRow.rowIndex - 1;
    
    var innerCell = innerRow.insertCell();
    innerCell.style.width = "20";
    innerCell.className = "tdwhiteM";
    innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                        ;
    
    innerCell = innerRow.insertCell();
    //innerCell.style.width = "";
    innerCell.className = "tdwhiteL";
    innerCell.innerHTML = "<input type=\"text\" name=\"callDepartmentDisplayName\" value=\"\" style=\"width:80%\" class='txt_field' >"
				        + "<input type=\"hidden\" name=\"callDepartmentOid\" value=\"\" >"
				        + "<input type=\"hidden\" name=\"callDepartmentCode\" value=\"\" >"
				        + "&nbsp;<a href=\"javascript:lfn_addDepartment2("+ rowIndex +");\"><img src=\"/plm/portal/images/icon_5.png\" border=0></a>"
				        + "&nbsp;<a href=\"javascript:lfn_deleteDept("+ rowIndex +");\"><img src=\"/plm/portal/images/icon_delete.gif\" border=0></a>"
                        ;
				        
    innerCell = innerRow.insertCell();
    //innerCell.style.width = "";
    innerCell.className = "tdwhiteL0";
    innerCell.innerHTML = "<input type='text' name='callAttendanceDisplayName' class='txt_field' style='width:80%'>"
                        + "<input type='hidden' name='callAttendanceOid' >"
                        + "<input type='hidden' name='callAttendanceName' >"
                        + "&nbsp;<a href=\"javascript:lfn_addCharge("+ rowIndex +");\"><img src=\"/plm/portal/images/icon_user.gif\" border=\"0\"></a>"
                        + "&nbsp;<a href=\"javascript:lfn_deleteCharge("+ rowIndex +");\"><img src=\"/plm/portal/images/icon_delete.gif\" border=\"0\"></a>"
                        ;
    

    //SuggestUtil.bind('DEPARTMENT', 'callDepartmentDisplayName', 'callDepartmentOid');
    SuggestUtil.multiBind('DEPTUSER', 'callDepartmentDisplayName', 'callDepartmentOid', 'callAttendanceDisplayName', 'callAttendanceOid');
    SuggestUtil.multiBind('USERDEPT', 'callAttendanceDisplayName', 'callAttendanceOid', 'callDepartmentDisplayName', 'callDepartmentOid');
    //SuggestUtil.multiBindCallBackFunc('USERDEPT', 'callAttendanceDisplayName', 'callAttendanceOid', deptCallBackFunc); 콜백 함수로 OBJECT 리턴
    //SuggestUtil.bind('USER', 'callAttendanceDisplayName', 'callAttendanceOid');

}

//회의소집
function openCallMeeting() {
	var divOpenCall = document.getElementById("divOpenCall");
    var divHistoryCall = document.getElementById("divHistoryCall");

    divOpenCall.style.display = "block";
    divHistoryCall.style.display = "none";
    
}

//보내기
function goCallMeeting() {
	
	// 유효성 체크
	
	if($("[name=callMeetingName]").val() == '') {
	    alert('<%=messageService.getString("e3ps.message.ket_message", "04025") %><%--회의명을 기입하시기 바랍니다.--%>');
	    return;
	}
	
	var hasCallAttendance = false;
	$("#relCallTable").find("[name=callAttendanceOid]").each(function(i) {
        if($(this).val() != '') {
        	hasCallAttendance = true;
            return;
        }
    });
	if(!hasCallAttendance) {
	    alert('<%=messageService.getString("e3ps.message.ket_message", "04026") %><%--참석자를 한명이상 추가하십시오.--%>');    
        return;
    }
	
    document.forms[0].action="/plm/servlet/e3ps/ProdEcrServlet?cmd=CallMeeting";
    document.forms[0].target="download";
    disabledAllBtn();
    showProcessing();
    document.forms[0].submit();
}

//회의소집이력
function openHistoryCall() {
    var divOpenCall = document.getElementById("divOpenCall");
    var divHistoryCall = document.getElementById("divHistoryCall");

    //divOpenCall.style.display = "block";
    divHistoryCall.style.display = "block";

}


<%--주관부서--%>
var SELECTED_POS = 0;
function lfn_addDepartment2(pos) {
	SELECTED_POS = pos;
	
    
    var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=s&invokeMethod=addDepartmentCallBack";
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=430,height=600";
    
    window.open(url,'',opts);
}

function addDepartmentCallBack(attacheDept){
    var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
    var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
    callServer(url, lfn_acceptDept2);
}

function getTagText(xmlDoc){
    return xmlDoc.textContent || xmlDoc.text || '';
}

function lfn_acceptDept2(req) {
    var xmlDoc = req.responseXML;
    var msg = getTagText(xmlDoc.getElementsByTagName("l_message")[0]);
    if(msg != 'true') {
        alert('다시 시도하시기 바랍니다');
        return;
    }

    
    var l_oid = xmlDoc.getElementsByTagName("l_oid");
    var l_name = xmlDoc.getElementsByTagName("l_name");
    var l_code = xmlDoc.getElementsByTagName("l_code");
    var l_chiefOid = xmlDoc.getElementsByTagName("l_chiefOid");
    var l_chiefName = xmlDoc.getElementsByTagName("l_chiefName");
    
    
    var callDepartmentDisplayNames = document.getElementsByName("callDepartmentDisplayName");
    var callDepartmentOids = document.getElementsByName("callDepartmentOid");
    var callDepartmentCodes = document.getElementsByName("callDepartmentCode");
    
    callDepartmentDisplayNames[SELECTED_POS].value = decodeURIComponent(getTagText(l_name[0]));
    callDepartmentOids[SELECTED_POS].value = decodeURIComponent(getTagText(l_oid[0]));
    callDepartmentCodes[SELECTED_POS].value = decodeURIComponent(getTagText(l_code[0]));

    // chiefName, chiefOid 추가
    var callAttendanceDisplayNames = document.getElementsByName("callAttendanceDisplayName");
    var callAttendanceOids = document.getElementsByName("callAttendanceOid");
    
    callAttendanceDisplayNames[SELECTED_POS].value = decodeURIComponent(getTagText(l_chiefName[0]));
    callAttendanceOids[SELECTED_POS].value = decodeURIComponent(getTagText(l_chiefOid[0]));
    
}    
function lfn_deleteDept(pos){
	SELECTED_POS = pos;
    
    var callDepartmentDisplayNames = document.getElementsByName("callDepartmentDisplayName");
    var callDepartmentOids = document.getElementsByName("callDepartmentOid");
    var callDepartmentCodes = document.getElementsByName("callDepartmentCode");
    
    callDepartmentDisplayNames[SELECTED_POS].value = '';
    callDepartmentOids[SELECTED_POS].value = '';
    callDepartmentCodes[SELECTED_POS].value = '';
    
}

<%--담당자--%>
function lfn_addCharge(pos) {
	
	SELECTED_POS = pos;
    
	SearchUtil.selectOneUser('','','lfn_acceptCharge');
}

function lfn_acceptCharge(objArr) {
    if(objArr.length == 0) {
        return;
    }

    // 담당자
    var callAttendanceDisplayNames = document.getElementsByName("callAttendanceDisplayName");
    var callAttendanceOids = document.getElementsByName("callAttendanceOid");
    var callAttendanceNames = document.getElementsByName("callAttendanceName");

    // 주관부서
    var callDepartmentDisplayNames = document.getElementsByName("callDepartmentDisplayName");
    var callDepartmentOids = document.getElementsByName("callDepartmentOid");
    var callDepartmentCodes = document.getElementsByName("callDepartmentCode");

    
    var nonUserArr = new Array();
    for(var i = 0; i < objArr.length; i++) {
        infoArr = objArr[i];
        /* 
        for(var j=0; j < infoArr.length; j++) {
            alert('infoArr['+ j +'] is '+ infoArr[j]);
        }
        */
        // 담당자
        callAttendanceDisplayNames[SELECTED_POS].value = infoArr[4];
        callAttendanceOids[SELECTED_POS].value = infoArr[0];
        callAttendanceNames[SELECTED_POS].value = infoArr[3];
        
        // 주관부서
        if(callDepartmentOids[SELECTED_POS].value == null || callDepartmentOids[SELECTED_POS].value == "") {
        	callDepartmentDisplayNames[SELECTED_POS].value = infoArr[5];
        	callDepartmentOids[SELECTED_POS].value = infoArr[2];
        	callDepartmentCodes[SELECTED_POS].value = "";
        }
    }
    
}    
function lfn_deleteCharge(pos){
	SELECTED_POS = pos;
    
    var callAttendanceDisplayNames = document.getElementsByName("callAttendanceDisplayName");
    var callAttendanceOids = document.getElementsByName("callAttendanceOid");
    var callAttendanceNames = document.getElementsByName("callAttendanceName");
    
    callAttendanceDisplayNames[SELECTED_POS].value = '';
    callAttendanceOids[SELECTED_POS].value = '';
    callAttendanceNames[SELECTED_POS].value = '';

}    

function lfn_feedback_after_callMeeting() {
    try {
        location.reload();
        
    } catch(e) {}
}
</script>

<!-- 이노디터 JS Include Start -->
<script type="text/javascript">
    //<![CDATA[

    // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
    // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
    // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
    var g_arrSetEditorArea = new Array();
    g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정

    //]]>
</script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize_ui.js"></script>
<script type="text/javascript">
    //<![CDATA[

    // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우

    // Skin 재정의
    //g_nSkinNumber = 0;

    // 크기, 높이 재정의
    g_nEditorWidth = 790;
    g_nEditorHeight = 400;

    // 메뉴바 설정
    g_bCustomize_MenuBar_Display = false;
    // 1번 툴바 설정
    g_bCustomize_ToolBar1_Display = false;
    // 2번 툴바 설정
    g_bCustomize_ToolBar2_Display = false;
    // 3번 툴바 설정
    g_bCustomize_ToolBar3_Display = false;
    // 탭바 설정
    g_bCustomize_TabBar_Display = false;
    // 조회모드
    g_nCustomFormEditMode = parseInt('2');
    //]]>
</script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script>
<!-- 이노디터 JS Include End -->

</head>
<body>
<form name="ViewEcrForm"  method="post">
<input type="hidden" name="cmd" value="View">
<input type="hidden" name ="oid" value="<%=ecrOid %>" >
<input type="hidden" name ="pboOid" value="<%=pboOid %>" >

<input type="hidden" name="prePage" value="<%=prePage %>">
<input type="hidden" name="tabName" value="ecrMeeting">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png">
            <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "회의소집") %><%--회의소집--%></td>
                <!-- td align="right">
                    <img src="/plm/portal/images/icon_navi.gif">Home
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00204") %><%--ECR 관리--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02541") %><%--제품 ECR 등록--%>
                  </td -->
              </tr>
            </table>
          </td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
        <tr>
          <td class="space5"></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                        <td><table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goCallMeeting();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "04028") %><%--보내기--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                          </table></td>
                        <td width="5">&nbsp;</td>
                        <!-- td><table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:openHistoryCall();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "04029") %><%--회의소집이력--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                          </table></td>  
                        <td width="5">&nbsp;</td -->
                        <td><table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                          </table></td>
              </tr>
            </table></td>
        </tr>
      </table>

      <div style="width:100%; height:530px; overflow-x:auto; overflow-y:auto;">

	      <div id="divOpenCall" style="display:block;">
	      
	      <%-- 회의소집 --%>
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
		      <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04107") %><%--회의명--%><span class="red">*</span></td>
		      <td class="tdwhiteL0" colspan="3"><input name="callMeetingName" type="text" class="txt_field" id="textfield5" style="width:99%" maxlength="100" value="<%=messageService.getString("e3ps.message.ket_message", "04820", prodEcr.getName()) %>"></td>
		    </tr>
		    <tr>
		      <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04108") %><%--회의일시--%>&nbsp;/&nbsp;<%=messageService.getString("e3ps.message.ket_message", "04030") %><%--회의시간--%></td>
		      <td width="171" class="tdwhiteL">
                <input id="callMeetingDate" name="callMeetingDate" value="" class="txt_fieldR" style="width:90px;">
                <!-- a href="#" onclick="javascript:showCal2('callMeetingDate', 'lfn_feedback_showCal_meetingDate');"><img src="/plm/portal/images/icon_6.png"></a>
                <a href="JavaScript:clearDate('callMeetingDate');"><img src="/plm/portal/images/icon_delete.gif"></a -->
                <img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue("callMeetingDate");' style='cursor: hand;'>

                <input name="callMeetingTime" type="text" class="txt_field" style="width:30%;" value="">  
		      </td>
	          <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04031") %><%--회의장소--%></td>
	          <td width="171" class="tdwhiteL"><input name="callMeetingLocation" type="text" class="txt_field" id="textfield5" style="width:98%" value=""></td>
            </tr>
            
            <%
            String callNumber = "0";
            
            long prodEcrOIDLongValue = CommonUtil.getOIDLongValue(prodEcr);
            
          
            QuerySpec query = new QuerySpec();
            int idx_link = query.appendClassList(KETEcrCallLink.class, false);
            int idx_check =  query.appendClassList(prodEcr.getClass(), false);
            int idx_number = query.appendClassList(KETMeetingCall.class, true);

            ClassAttribute ca1 = new ClassAttribute(prodEcr.getClass(), "thePersistInfo.theObjectIdentifier.id");
            ClassAttribute ca2 = new ClassAttribute(KETMeetingCall.class, "thePersistInfo.theObjectIdentifier.id");
            ClassAttribute ca3 = new ClassAttribute(KETEcrCallLink.class, "roleAObjectRef.key.id");
            ClassAttribute ca4 = new ClassAttribute(KETEcrCallLink.class, "roleBObjectRef.key.id");
                
            SearchCondition sc1 = new SearchCondition(ca1, SearchCondition.EQUAL, ca3);
            query.appendWhere(sc1, new int[] { idx_check, idx_link });
            query.appendAnd();
            SearchCondition sc2 = new SearchCondition(ca2, SearchCondition.EQUAL, ca4);
            query.appendWhere(sc2, new int[] { idx_number, idx_link });

            query.appendAnd();
            SearchCondition sc3 = new SearchCondition(prodEcr.getClass(), "thePersistInfo.theObjectIdentifier.id", SearchCondition.EQUAL, prodEcrOIDLongValue);
            query.appendWhere(sc3, new int[] { idx_check });

            query.appendOrderBy(new OrderBy(new ClassAttribute(KETMeetingCall.class, "callNumber"), true), new int[] { idx_number });
            
            QueryResult qr = PersistenceHelper.manager.find(query);
            if (qr.hasMoreElements()) {
                Object[] objArr = (Object[]) qr.nextElement();
                KETMeetingCall ketMeetingCall = (KETMeetingCall) objArr[0];
                callNumber = ketMeetingCall.getCallNumber();
            }
            %>
            <tr>
              <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04032") %><%--보낸 횟수--%></td>
	          <td class="tdwhiteL"><input type="hidden" name="callNumber" value="<%=callNumber %>"><%=callNumber %></td>
              <td class="tdwhiteL0" colspan="2">&nbsp;</td>
	        </tr> 	              
		    <tr>
		      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04033") %><%--참석요청--%><span class="red">*</span></td>
		      <td colspan="3" class="tdwhiteL0">
		        <table width="100%" cellpadding="0" cellspacing="0" id="relCallTable">
		          <tr class="">
		
		            <td style="width:20px" class="tdgrayM"><a href="#" onclick="javascript:insertCallLine();"><img src="/plm/portal/images/b-plus.png"></a></td>
		            <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04034") %><%--부서--%></td>
		            <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04035") %><%--참석자--%></td>
		                          
		          </tr>
	            </table>
	          </td>
	        </tr>             
	      </table>
	      
	      </div>
	      
	      <%-- 회의소집이력 --%>
	      <div id="divHistoryCall" style="display:block;">
	
	        <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "04029") %><%--회의소집이력--%></td>
              </tr>
            </table>
            	
	        <%
	        String preCallNumber = "";
	        int pos = 0;
	        qr = PersistenceHelper.manager.find(query);
	        while (qr.hasMoreElements()) {
	            Object[] objArr = (Object[]) qr.nextElement();
	            KETMeetingCall ketMeetingCall = (KETMeetingCall) objArr[0];
	              
	            String callSendDate = DateUtil.getTimeFormat(ketMeetingCall.getCallSendDate(), "yyyy-MM-dd");
	            String callMeetingDate = DateUtil.getTimeFormat(ketMeetingCall.getCallMeetingDate(), "yyyy-MM-dd");
	            String callMeetingTime = StringUtils.stripToEmpty(ketMeetingCall.getCallMeetingTime());
	            String callMeetingLocation = StringUtils.stripToEmpty(ketMeetingCall.getCallMeetingLocation());
	            callNumber = ketMeetingCall.getCallNumber();
	            
	            // 부서
	            Department callDepartment = ketMeetingCall.getCallDepartment();
	            String callDepartmentDisplayName = (callDepartment != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(callDepartment.getName())) : "";
	            String callDepartmentOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(callDepartment));
	            String callDepartmentCode = StringUtils.stripToEmpty(ketMeetingCall.getCallDepartmentCode());
	                      
	            // 참석자
	            WTUser callAttendance = ketMeetingCall.getCallAttendance();
	            String callAttendanceDisplayName = (callAttendance != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(callAttendance.getFullName())) : "";
	            String callAttendanceOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(callAttendance));
	            String callAttendanceName = StringUtils.stripToEmpty(ketMeetingCall.getCallAttendanceName());
	            
	            
	                if(pos == 0) {    
	            %>
        
	                  <table border="0" cellspacing="0" cellpadding="0" width="100%">
	                    <tr>
	                      <td  class="tab_btm2"></td>
	                    </tr>
	                  </table>          
	                  <table border="0" cellspacing="0" cellpadding="0" width="100%">
	                    
	            <%    
	                }       
	                
	                	            
	            if(!preCallNumber.equals(callNumber)) {
	
	        	    if(!preCallNumber.equals("")) {
	        %>
			                
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
                                   
	        
	        <%
	        	    }
	        %>
	        
			        <tr>
			          <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04036") %><%--발신일--%></td>
			          <td colspan="3" class="tdwhiteL0"><%=callSendDate %></td>
			        </tr>  
	                <tr>
	                  <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04108") %><%--회의일시--%>&nbsp;/&nbsp;<%=messageService.getString("e3ps.message.ket_message", "04030") %><%--회의시간--%></td>
	                  <td width="171" class="tdwhiteL"><%=callMeetingDate %><% if(!callMeetingDate.equals("") && !callMeetingTime.equals("")) { %>&nbsp;/&nbsp;<% } %><%=callMeetingTime %></td>
	                  <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04031") %><%--회의장소--%></td>
	                  <td width="171" class="tdwhiteL0"><%=callMeetingLocation %></td>
	                </tr> 
			        <tr>
			          <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04033") %><%--참석요청--%></td>
			          <td colspan="3" class="tdwhiteL0">
			            <table width="100%" cellpadding="0" cellspacing="0">
	
	        <%
	            }   // if(!preCallNumber.equals(callNumber)) {
	        %>              
			                                        
			              <tr>
			                <td width="50%" class="tdwhiteL"><%=callDepartmentDisplayName %></td>
			                <td width="50%" class="tdwhiteL0"><%=callAttendanceDisplayName %></td>
			              </tr>
			                            
	        <%
	
	            preCallNumber = callNumber;
	            pos++;
	            
	        }   // while (qr.hasMoreElements()) {
	        
	
	        if(pos > 0) {
	        %>
			                
			            </table>
			          </td>
			        </tr>             
			       </table> 
			                 
	        <%     
	        }    
	        %>
	        
	
	      </div>               
	
      </div>    
          
    </td>
  </tr>
  <!-- tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr -->
</table>
</form>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</body>
</html>