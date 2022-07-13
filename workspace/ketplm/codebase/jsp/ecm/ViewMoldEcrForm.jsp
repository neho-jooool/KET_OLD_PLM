<%@page import="e3ps.ecm.beans.EcmUtil" %>
<%@page import="wt.content.ContentItem" %>
<%@page import="e3ps.common.util.KETStringUtil" %>
<%@page import="e3ps.common.web.ParamUtil" %>
<%@page import="e3ps.ecm.dao.MoldChangeOrderDao" %>

<%@ page import="java.util.*
              ,e3ps.common.util.StringUtil
              ,e3ps.common.util.DateUtil
              ,e3ps.common.util.CommonUtil
              ,e3ps.common.util.KETObjectUtil
              ,e3ps.common.code.NumberCode
              ,e3ps.common.code.NumberCodeHelper
              ,e3ps.ecm.beans.ProdEcrBeans
              ,e3ps.ecm.entity.*
              ,e3ps.project.ProjectIssue
              ,e3ps.common.content.ContentInfo
              ,e3ps.common.content.ContentUtil
              ,e3ps.groupware.company.Department
              ,wt.org.WTUser
              ,wt.fc.QueryResult
              ,wt.fc.PersistenceHelper
              ,wt.query.*
              ,wt.content.ContentHolder
              ,wt.content.ContentHelper
              ,wt.session.SessionHelper
              ,wt.content.ApplicationData" %>
              
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@page import="java.sql.Timestamp"%>

<jsp:useBean id="ketMoldChangeRequestVO" class="e3ps.ecm.entity.KETMoldChangeRequestVO" scope="request" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%
// 결재대상 화면 여부
boolean isIframe = Boolean.parseBoolean(StringUtil.checkReplaceStr(request.getParameter("isIframe"), "false"));

WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();

WTUser owner = (WTUser)ketMoldChangeRequestVO.getKetMoldChangeRequest().getCreator().getPrincipal();
boolean isOwner = false;
if( owner.equals( loginUser ) ) {
    isOwner = true;
}
boolean isInWork = false;//작업중여부
boolean isUnderReview = false;//검토중여부
boolean isApproved = false;//승인완료여부
boolean existRelMoldEcoLink = false;//관련금형eco 존재여부
if(ketMoldChangeRequestVO.getTotalCount() > 0) {
    if("INWORK".equals(ketMoldChangeRequestVO.getProgressState()) || "REWORK".equals(ketMoldChangeRequestVO.getProgressState()) ) {
        isInWork = true;
    }
    if("UNDERREVIEW".equals(ketMoldChangeRequestVO.getProgressState())) {
        isUnderReview = true;
    }
    if("APPROVED".equals(ketMoldChangeRequestVO.getProgressState())) {
        isApproved = true;
    }
    existRelMoldEcoLink = ketMoldChangeRequestVO.isExistRelMoldEcoLink();//관련금형eco 존재여부
}
String ecrOid = "";
if(ketMoldChangeRequestVO.getOid().indexOf("e3ps.ecm.entity.KETMoldChangeRequest:") == -1){
    ecrOid = "e3ps.ecm.entity.KETMoldChangeRequest:"+ketMoldChangeRequestVO.getOid();
}
else{
    ecrOid = ketMoldChangeRequestVO.getOid();
}

KETMoldChangeRequest ecr = (KETMoldChangeRequest)CommonUtil.getObject(ecrOid);

QueryResult ecoqr = PersistenceHelper.manager.navigate(ecr, "moldECO", KETMoldChangeLink.class);


// ECR 전체 상태
wt.lifecycle.State ecrStateState = ecr.getEcrStateState();
String ecrStateStateCode = (ecrStateState != null) ? ecrStateState.toString() : "";
String ecrStateDisplayName = (ecrStateState != null) ? ecrStateState.getDisplay() : "";


//제품, 금형 ECR 확장팩
KETChangeRequestExpansion expansion = null;
//ECR 로 찾는다.
QuerySpec spec = new QuerySpec(KETChangeRequestExpansion.class);
spec.appendWhere(new SearchCondition(KETChangeRequestExpansion.class, "ecrReference.key.id", "=", CommonUtil.getOIDLongValue(ecr)), new int[] { 0 });
QueryResult result = PersistenceHelper.manager.find(spec);
if (result.hasMoreElements()) { // while (result.hasMoreElements()) {
  expansion = (KETChangeRequestExpansion) result.nextElement();
}


String reviewRequestDate = "";

String subjectDisplayName = "";
String subjectOid = "";
String subjectCode = "";
String chargeDisplayName = "";
String chargeOid = "";
String chargeName = "";

String webEditor = "";
String webEditorText = "";

String webEditor1 = "";
String webEditorText1 = "";

if (PersistenceHelper.isPersistent(expansion)) {
  
  // 검토요청기한
  reviewRequestDate = DateUtil.getTimeFormat(expansion.getReviewRequestDate(), "yyyy-MM-dd"); 
      
  // 주관부서
  Department subject = expansion.getSubject();
  subjectDisplayName = (subject != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(subject.getName())) : "";
  subjectOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(subject));
  subjectCode = StringUtils.stripToEmpty(expansion.getSubjectCode());
  
  // 담당자
  WTUser charge = expansion.getCharge();
  chargeDisplayName = (charge != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(charge.getFullName())) : "";
  chargeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(charge));
  chargeName = StringUtils.stripToEmpty(expansion.getChargeName());
  
  // 현상
  webEditor = (String) expansion.getWebEditor();
  webEditorText = (String) expansion.getWebEditorText();
   
  // 문제점 및 요구사항
  webEditor1 = (String) expansion.getWebEditor1();
  webEditorText1 = (String) expansion.getWebEditorText1();

}

// 검토요청기한이 없을 경우 완료요청일을 보여준다.
String completeReqDate = ketMoldChangeRequestVO.getCompleteReqDateFormat();
if((reviewRequestDate == null || reviewRequestDate.equals("")) && (completeReqDate != null && !completeReqDate.equals(""))) {
    reviewRequestDate = completeReqDate;
}

// 주간부서
KETCompetentDepartment competent = null;
String competentStateCode = "";
QueryResult qr = PersistenceHelper.manager.navigate(ecr, "competent", KETEcrCompetentLink.class, false);
while (qr.hasMoreElements()) {
    KETEcrCompetentLink link = (KETEcrCompetentLink) qr.nextElement();
    competent = link.getCompetent();
    
    competentStateCode = competent.getLifeCycleState().toString();
}

// 회의록
KETMeetingMinutes meeting = null;
String meetingStateCode = "";
qr = PersistenceHelper.manager.navigate(ecr, "meeting", KETEcrMeetingLink.class, false);
while (qr.hasMoreElements()) {
    KETEcrMeetingLink link = (KETEcrMeetingLink) qr.nextElement();
    meeting = link.getMeeting();
    
    meetingStateCode = meeting.getLifeCycleState().toString();
}
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "01006") %><%--금형 ECR 상세조회--%></title>
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
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

<script language=JavaScript src="/plm/jsp/ecm/ViewMoldEcr.js"></script>
<script language="javascript">
//관련ECO 상세화면 팝업
function viewEco(oid) {
    var url = "/plm/servlet/e3ps/MoldEcoServlet?prePage=Search&cmd=view&oid=" + oid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}


//ECO 작성
function callCreateMoldEco2(){
 
 // 주간부서가 '승인완료'되어야 한다.
 var competentStateCode = '<%=competentStateCode %>';
 if(competentStateCode != 'APPROVED') {
     alert('<%=messageService.getString("e3ps.message.ket_message", "04129") %><%--주관부서가 \'승인완료\'되어야 합니다.--%>');
     return;
 }
 
 // 회의록이 '승인완료'되어야 한다.
 <%-- 
 var meetingStateCode = '<%=meetingStateCode %>';
 if(meetingStateCode != 'APPROVED') {
     alert('<%=messageService.getString("e3ps.message.ket_message", "04130") %>회의록이 \'승인완료\'되어야 합니다.');
     return;
 }
 --%>
 
 var url = "/plm/servlet/e3ps/MoldEcrServlet";
 document.forms[0].cmd.value = "goMoldEco";
 document.forms[0].target = "_parent";
 document.forms[0].action = url;
 document.forms[0].method = "post";
 document.forms[0].submit();
}


<% /* deprecated */ %>
function doApproved()
{
    var message = "결재를 강제승인시키는 디버깅 기능입니다.\n"
                + "<%=messageService.getString("e3ps.message.ket_message", "04260") %><%--그래도 작업을 계속 진행하시겠습니까?--%>"
                ;
    if(!confirm(message)) return;
    
    disabledAllBtn();
    showProcessing();
        
    var url = "/plm/servlet/e3ps/ProdEcrServlet";
    var form = document.forms[0];
    form.cmd.value = "ApprovedReg";
    form.target = "download";
    form.action = url;
    form.submit();      
    
}

function lfn_reloadWhole() {
    try {
        parent.lfn_reloadWhole();
        
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
    g_arrSetEditorArea[1] = "EDITOR_AREA_CONTAINER1";// 이노디터를 위치시킬 영역의 ID값 설정

    //]]>
</script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize_ui_half.js"></script>
<script type="text/javascript">
    //<![CDATA[

    // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우

    // Skin 재정의
    //g_nSkinNumber = 0;

    // 크기, 높이 재정의
    g_nEditorWidth = 470;
    g_nEditorHeight = 200;

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
    
    
    // 편집창의 focus/blur 이벤트 발생 후 추가적인 처리를 위한 함수 호출 설정
    // 이노디터를 호출하는 곳에서 해당 함수가 정의되어 있어야 함
    g_strCustomFocusEventFunction = "fnTestFocusEventFunction";

    //]]>
</script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script>

<script language=javascript>
var isBlur = 0;
// 고객사에서 정의한 함수 - focus 이벤트 발생시 호출되는 함수
// 매개변수 1,2 값은 자동으로 이노디터에서 넘겨줌
// 매개변수 1 - 에디터번호
// 매개변수 2 - focus 인 경우는 1 리턴, 아닌 경우 0 리턴
function fnTestFocusEventFunction(EditNumber, FocusYN) {
    
    if(isBlur >= 2) {
        
        // 이노디터 제공함수 - 편집창모드 true, 소스창모드 false 리턴
        if(1 == FocusYN) {
            fnEventHandler_OnFocus(EditNumber);
        
        } else {
            fnEventHandler_OnBlur(EditNumber);
            
        }
        
    } else {
        //document.forms[0].ecr_desc.focus();
        isBlur++;
    }
}


//click event에 대한 Handler
function fnEventHandler_OnFocus(EditNumber) {
    fnResizeEditor(0, 470, 600);
    fnResizeEditor(1, 470, 600);

}
function fnEventHandler_OnBlur(EditNumber) {
    fnResizeEditor(0, 470, 200);
    fnResizeEditor(1, 470, 200);
    
}
</script>

<script language=javascript>
// load event 설정에서 window 객체는 이노디터를 포함하고 있는 window의 객체임
if(window.attachEvent)// IE 6,7,8,9,10
{
    window.attachEvent("onload", fnEventHandler_Onload);
}
else if(window.addEventListener)// IE 11, FireFox, Chrome, Safari
{
    window.addEventListener("load", fnEventHandler_Onload, false);
}


// load event에 대한 Handler
function fnEventHandler_Onload(objEvnt)// objEvent는 브라우저가 자동으로 넘겨주는 Event 객체, 이벤트에 따라, 브라우저에 따라 약간씩 상이함
{
    try
    {
        var vObjDocument = fnGetEditorDocument(0);

        if(vObjDocument.attachEvent)// IE 6,7,8,9,10
        {
            vObjDocument.attachEvent("onkeydown", fnEventHandler_OnKeyDown);
            vObjDocument.attachEvent("onkeyup", fnEventHandler_OnKeyUp);
            vObjDocument.body.attachEvent("onpaste", fnEventHandler_OnPaste);
        }
        else if(vObjDocument.addEventListener)// IE 11, FireFox, Chrome, Safari
        {
            vObjDocument.addEventListener("keydown", fnEventHandler_OnKeyDown, false);
            vObjDocument.addEventListener("keyup", fnEventHandler_OnKeyUp, false);
            vObjDocument.body.addEventListener("paste", fnEventHandler_OnPaste, false);
        }

        //fnResizeEditor(0, 800, 400);// Resize 함수 호출(load 이벤트가 정상적으로 설정되었는 지 확인하고자 예제로 설정)
    }
    catch(e)
    {
        // Onload 이벤트 시점에서 아직 이노디터가 활성화 이전인 경우는 다시 시도       
        window.setTimeout("fnEventHandler_Onload(null)", 1000);
    }
}

//keyup event에 대한 Handler
function fnEventHandler_OnKeyDown(objEvnt)
{
    return false;
}

// keyup event에 대한 Handler
function fnEventHandler_OnKeyUp(objEvnt)
{
    return false;
}

// paste event에 대한 Handler
function fnEventHandler_OnPaste(objEvnt)
{
    return true;
}
</script>
<!-- 이노디터 JS Include End -->

</head>
<body onload="javscript:init();">
<form method="post" action="/plm/servlet/e3ps/MoldEcoServlet">
<input type="hidden" name="cmd" value="view">
<input type="hidden" name="oid" value="<%if(ketMoldChangeRequestVO.getTotalCount() > 0) { out.print(CommonUtil.getOIDString(ketMoldChangeRequestVO.getKetMoldChangeRequest()));}%>">

<input type="hidden" name="changeType" value="">
<input type="hidden" name="stateState" value="<%if(ketMoldChangeRequestVO.getTotalCount() > 0) {out.print(ketMoldChangeRequestVO.getProgressState());}%>">

<input type="hidden" name="page" value="<%=ParamUtil.getParameter(request, "page")%>">
<input type="hidden" name="totalCount" value="<%=ParamUtil.getParameter(request, "totalCount")%>">
<input type="hidden" name="sortColumn" value="<%=ParamUtil.getParameter(request, "sortColumn")%>">
<input type="hidden" name="param" value="<%=ParamUtil.getParameter(request, "param")%>">
<input type="hidden" name="perPage" value="<%=ParamUtil.getParameter(request, "perPage")%>">
<input type="hidden" name="autoSearch" value="<%=ParamUtil.getParameter(request, "autoSearch")%>">
<input type="hidden" name="srchpartOid" value="<%=ParamUtil.getParameter(request, "partOid")%>">
<input type="hidden" name="srchpartNo" value="<%=ParamUtil.getParameter(request, "partNo")%>">
<input type="hidden" name="srchpartName" value="<%=ParamUtil.getParameter(request, "partName")%>">
<input type="hidden" name="srchprojectOid" value="<%=ParamUtil.getParameter(request, "projectOid")%>">
<input type="hidden" name="srchprojectNo" value="<%=ParamUtil.getParameter(request, "projectNo")%>">
<input type="hidden" name="srchprojectName" value="<%=ParamUtil.getParameter(request, "projectName")%>">
<input type="hidden" name="srchorgName" value="<%=ParamUtil.getParameter(request, "orgName")%>">
<input type="hidden" name="srchcreatorOid" value="<%=ParamUtil.getParameter(request, "creatorOid")%>">
<input type="hidden" name="srchcreatorName" value="<%=ParamUtil.getParameter(request, "creatorName")%>">
<input type="hidden" name="srchecrId" value="<%=ParamUtil.getParameter(request, "ecrId")%>">
<input type="hidden" name="srchdivisionFlag" value="<%=ParamUtil.getParameter(request, "divisionFlag")%>">
<input type="hidden" name="srchdevYn" value="<%=ParamUtil.getParameter(request, "devYn")%>">
<input type="hidden" name="srchsancStateFlag" value="<%=ParamUtil.getParameter(request, "sancStateFlag")%>">
<input type="hidden" name="srchecrName" value="<%=ParamUtil.getParameter(request, "ecrName")%>">
<input type="hidden" name="srchprodMoldCls" value="<%=ParamUtil.getParameter(request, "prodMoldCls")%>">
<input type="hidden" name="srchcreateStartDate" value="<%=ParamUtil.getParameter(request, "createStartDate")%>">
<input type="hidden" name="srchcreateEndDate" value="<%=ParamUtil.getParameter(request, "createEndDate")%>">
<input type="hidden" name="prePage" value="<%=ParamUtil.getParameter(request, "prePage")%>">

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <!-- table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01006") %><%--금형 ECR 상세조회--%></td>
                <td align="right">
                  <img src="/plm/portal/images/icon_navi.gif">Home
                  <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                  <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00204") %><%--ECR 관리--%>
                  <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00202") %><%--ECR 검색--%>
                </td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table -->
      <table width="100%" border="0" cellspacing="0" cellpadding="0" style="<%=(isIframe)?"display:none;":""%>">
        <tr>
          <td height="24px">&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <%if(isOwner && isInWork) {//결재상태가 작업중인 경우%>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a id="btnUpdate" href="javascript:callUpdate();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a id="btnDelete" href="javascript:doDelete();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <%}//결재상태가 작업중인 경우%>
                <%if( (isApproved || ecrStateStateCode.equals("CONSIDERED")) && (KETObjectUtil.isAdmin() || KETObjectUtil.isMember("자동차사업부_금형설계") || KETObjectUtil.isMember("KETS_금형설계")) ) {//결재상태가 승인완료이고 관련금형ECO가 존재하지 않는 경우%>
                   <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a id="btnCreateEco" href="javascript:callCreateMoldEco2();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "00191") %><%--ECO 작성--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <%}//결재상태가 승인완료인 경우%>
                <%if( isOwner&& isInWork) {//결재상태가 작업중인 경우%>
                   <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a id="btnApprove" href="javascript:doSanction();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                   <%
                   }
               %>
                <!-- td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:callSearch();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01378") %><%--목록--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td -->
               <%
               // 승인(디버깅)
               if(KETObjectUtil.isAdmin()  && isUnderReview) {
               %>
               <td width="5">&nbsp;</td>
               <td>
                   <table border="0" cellspacing="0" cellpadding="0">
                   <tr>
                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                       <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doApproved();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01987") %><%--승인--%>(Admin)</a></td>
                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                   </tr>
                   </table>
               </td>
               <%
               }
               %>                      
              </tr>
            </table></td>
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
      
      
      <div id="ecrDiv1" style="width:100%; height:640px;; overflow-x:auto; overflow-y:auto;">
          <table border="0" cellspacing="0" cellpadding="0" width="100%">
	        <tr>
	          <td width="130"  class="tdblueL">ECR No</td>
	          <td class="tdwhiteL"><%=ketMoldChangeRequestVO.getKetMoldChangeRequest().getEcrId()%>&nbsp;</td>
	          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%></td>
	          <td class="tdwhiteL0"><%=ketMoldChangeRequestVO.getDevYnName()%>&nbsp;</td>
	        </tr>
	        <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
              <td class="tdwhiteL" title="<%=ketMoldChangeRequestVO.getKetMoldChangeRequest().getEcrName() %>"><div class="ellipsis" style="width:310px;"><nobr><%=ketMoldChangeRequestVO.getKetMoldChangeRequest().getEcrName() %></nobr></div></td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "ECR 상태") %><%--ECR 상태--%></td>
              <td class="tdwhiteL0"><%=ecrStateDisplayName %></td>	          
	        </tr>
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%><span class="red"></span></td>
	          <td class="tdwhiteL"><%=ketMoldChangeRequestVO.getDivisionFlagName()%>&nbsp;</td>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
	          <td class="tdwhiteL0"><a href="javascript:viewHistory('<%=CommonUtil.getOIDString(ketMoldChangeRequestVO.getKetMoldChangeRequest())%>');" onfocus="this.blur();"><%=ketMoldChangeRequestVO.getProgressStateName()%></a>&nbsp;</td>
	        </tr>
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%><span class="red"></span></td>
	          <td class="tdwhiteL"><%=ketMoldChangeRequestVO.getOrgName()%>&nbsp;</td>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
	          <td class="tdwhiteL0"><%=ketMoldChangeRequestVO.getCreatorName()%>&nbsp;</td>
	        </tr>
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%><span class="red"></span></td>
	          <td class="tdwhiteL"><%=ketMoldChangeRequestVO.getCreateDate()%>&nbsp;</td>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
	          <td class="tdwhiteL0"><%=ketMoldChangeRequestVO.getUpdateDate()%>&nbsp;</tr>
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02000") %><%--승인자--%><span class="red"></span></td>
	          <td class="tdwhiteL"><%=StringUtil.checkReplaceStr( ketMoldChangeRequestVO.getApprovalName(),"-" )%>&nbsp;</td>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
	          <td class="tdwhiteL0"><%=StringUtil.checkReplaceStr( ketMoldChangeRequestVO.getApprovalDate(),"-" )%>&nbsp;</td>
	        </tr>
	        <tr>
	          <td class="tdblueL">Project No</td>
	            <%
	            String projectOid = StringUtil.checkNull(ketMoldChangeRequestVO.getKetMoldChangeRequest().getProjectOid());
	            if("".equals(projectOid)) {%>
	          <td class="tdwhiteL">-</td>
	            <%} else {%>
	          <td class="tdwhiteL"><a href="javascript:openProject('<%=ketMoldChangeRequestVO.getProjectNo()%>');"><%=StringUtil.checkNull(ketMoldChangeRequestVO.getProjectNo())%></td>
	            <%}%>
	          <td class="tdblueL">Project Name</td>
	          <%if("".equals(projectOid)) { %>
	          <td class="tdwhiteL0">-</td>
	          <%}else{ %>
	          <td class="tdwhiteL0"><%=StringUtil.checkNull(ketMoldChangeRequestVO.getProjectName())%></td>
	          <%} %>
	        </tr>
	        
	        <%-- 
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00939") %>관련이슈</td>
	          <td colspan="3" class="tdwhiteM0">
	            <!-- table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	            <div id="div_scroll" style="width:100%;height=71;overflow:auto;" class="table_border" -->
	            <table width="100%" cellpadding="0" cellspacing="0">
	              <tr class="">
	                <!-- td width="40" class="tdgrayM">No</td -->
	                <td width="" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02317") %>이슈명</td>
	                <td width="145" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %>작성자</td>
	                <td width="102" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02429") %>작성일자</td>
	              </tr>
	                   <%
	                   if(ketMoldChangeRequestVO.getTotalCount() > 0) {
	                       int size = 0;
                           int i = 0;
                           ArrayList<KETMoldECRIssueLinkVO> ketMoldECRIssueLinkVOList = ketMoldChangeRequestVO.getKetMoldECRIssueLinkVOList();
	                       if(ketMoldECRIssueLinkVOList == null) {
	                           size = 0;
	                       } else {
	                           size = ketMoldECRIssueLinkVOList.size();
	                       }
	                       KETMoldECRIssueLinkVO ketMoldECRIssueLinkVO = null;
	                       for (i = 0 ; i<size; i++ ) {
	                           ketMoldECRIssueLinkVO = (KETMoldECRIssueLinkVO)ketMoldECRIssueLinkVOList.get(i);
	                   %>
	                   <tr>
	                       <!-- td class="tdwhiteM"><%=(i+1)%></td -->
	                       <td class="tdwhiteL"><a href="javascript:viewIssue('<%=ketMoldECRIssueLinkVO.getRelIssueOid()%>');"><%=KETStringUtil.getTruncatedText(ketMoldECRIssueLinkVO.getRelIssueName(), 30)%>&nbsp;</td>
	                       <td class="tdwhiteM"><%=ketMoldECRIssueLinkVO.getRelIssueCreatorName()%>&nbsp;</td>
	                       <td class="tdwhiteM0"><%=ketMoldECRIssueLinkVO.getRelIssueCreateDate()%>&nbsp;</td>
	                   </tr>
	                   <%
	                       }
	                       if(size == 0){
	                   %>
	                   <!-- tr>
	                      <td colspan=3 class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "00918") %>관련 이슈가 없습니다</td>
	                    </tr -->
	                   <%
	                       }
	                   }
	                   %>
	            </table>
	            <!-- /div>
	            <table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table -->
	          </td>
	        </tr>
	        --%>
	        
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%></td>
	          <td class="tdwhiteL"><%=StringUtil.checkReplaceStr(ketMoldChangeRequestVO.getProdVendorName(), "-")%>&nbsp;</td>
	          <!--  완료요청일 제거함
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02178") %><%--완료요청일--%></td>
	          <td class="tdwhiteL0"><%=StringUtil.checkReplaceStr(ketMoldChangeRequestVO.getCompleteReqDateFormat(), "-")%>&nbsp;</td>
	           -->
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04102") %><%--회신기한--%></td>
              <td class="tdwhiteL0"><%=reviewRequestDate %>&nbsp;</td>
	        </tr>
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02782") %><%--처리구분--%></td>
	          <td class="tdwhiteL"><%=StringUtil.checkNull(ketMoldChangeRequestVO.getProcessTypeName())%><%if(StringUtil.checkNull(ketMoldChangeRequestVO.getOtherProcessdesc()).length()>0){ %>(<%=ketMoldChangeRequestVO.getOtherProcessdesc() %>)<%} %></td>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02247") %><%--의뢰구분--%></td>
	          <td class="tdwhiteL0"><%=StringUtil.checkNull(ketMoldChangeRequestVO.getRequestTypeName())%><%if(StringUtil.checkNull(ketMoldChangeRequestVO.getOtherRequestType()).length() >0 ){ %>(<%=ketMoldChangeRequestVO.getOtherRequestType()%>)<%} %></td>
	        </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04103") %><%--주관부서--%></td>
              <td class="tdwhiteL"><%=subjectDisplayName %>&nbsp;</td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04104") %><%--담당자--%></td>
              <td class="tdwhiteL0"><%=chargeDisplayName %>&nbsp;</td>
            </tr>  	        
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01860") %><%--설계변경유형--%></td>
	          <td colspan="3" class="tdwhiteL0"><%=StringUtil.checkNull(ketMoldChangeRequestVO.getChangeTypeName())%><%if(StringUtil.checkNull(ketMoldChangeRequestVO.getOtherChangeType()).length() > 0 ){ %>(<%=ketMoldChangeRequestVO.getOtherChangeType() %>)<%} %></td>
	        </tr>
	        <!-- tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
	          <td colspan="3" class="tdwhiteL0">
	              <table border="0" cellspacing="0" cellpadding="0" width="645">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	              <textarea name="ecrDesc" style='overflow: auto; width:655; height:50px; padding: 2;' class="txt_field" readonly><%=StringUtil.checkNull(ketMoldChangeRequestVO.getKetMoldChangeRequest().getEcrDesc())%></textarea>
	              <table border="0" cellspacing="0" cellpadding="0" width="645">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	          </td>
	        </tr>
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01116") %><%--기대효과--%><span class="red"></span></td>
	          <td colspan="3" class="tdwhiteL0">
	              <table border="0" cellspacing="0" cellpadding="0" width="645">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	            <textarea name="expectEffect" style='overflow: auto; width:655; height:50px; padding: 2;' class="txt_field"  readonly><%=StringUtil.checkNull(ketMoldChangeRequestVO.getKetMoldChangeRequest().getExpectEffect())%></textarea>
	            <table border="0" cellspacing="0" cellpadding="0" width="645">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	          </td>
	        </tr -->
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%></td>
	          <td colspan="3" class="tdwhiteL0">
	            <!-- table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	            <div id="div_scroll2" style="width:100%;height=71;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
	            <table width="100%" cellpadding="0" cellspacing="0">
	              <tr class="">
	                <td width="90"  class="tdgrayM">Die No</td>
	                <td width="" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
	                <td width="50"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
	                <!-- td width="148" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02191") %><%--요청내용--%></td -->
	              </tr>
	                <%
	                if(ketMoldChangeRequestVO.getTotalCount() > 0) {
	                    int size = 0;
	                    int i = 0;
	                    ArrayList<KETMoldECOPartLinkVO> ketMoldECOPartLinkVOList = ketMoldChangeRequestVO.getKetMoldECOPartLinkVOList();
	                    if(ketMoldECOPartLinkVOList == null) {
	                        size = 0;
	                    } else {
	                        size = ketMoldECOPartLinkVOList.size();
	                    }
	                    KETMoldECOPartLinkVO ketMoldECOPartLinkVO = null;
	                    for (i = 0 ; i<size; i++ ) {
	                        ketMoldECOPartLinkVO = (KETMoldECOPartLinkVO)ketMoldECOPartLinkVOList.get(i);
	                %>
	                <tr>
	                    <!-- td class="tdwhiteM"><%=(i+1)%></td -->
	                    <td class="tdwhiteM" ><a href="javascript:viewPart('<%=ketMoldECOPartLinkVO.getRelPartOid()%>');"><%=KETStringUtil.getTruncatedText(ketMoldECOPartLinkVO.getRelPartNumber(), 10)%></a>&nbsp;</td>
	                    <td class="tdwhiteL" title="<%=ketMoldECOPartLinkVO.getRelPartName()%>"><%=KETStringUtil.getTruncatedText(ketMoldECOPartLinkVO.getRelPartName(), 50)%>&nbsp;</td>
	                    <td class="tdwhiteM0"><%=ketMoldECOPartLinkVO.getRelPartRev()%>&nbsp;</td>
	                    <!-- td class="tdwhiteL0"><%=ketMoldECOPartLinkVO.getChangeReqComment()%>&nbsp;</td -->
	                  </tr>
	              <%
	                    }
	                }
	                %>
	            </table>
	            <!-- /div>
	            <table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table -->
	          </td>
	        </tr>
            <tr>
              <td colspan="2" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04127") %><%--현상--%></td>
              <td colspan="2" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04128") %><%--문제점 및 요구사항--%></td>
            </tr>  	        
            <tr>
              <td colspan="2" class="tdwhiteL">
              
              
                            <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                            <textarea name="webEditor" rows="0" cols="0" style="display: none"><%=webEditor %></textarea> 
                            <textarea name="webEditorText" rows="0" cols="0" style="display: none"><%=webEditorText %></textarea> 
                            <!-- Editor Area Container : Start -->
                            <!-- 이노디터에서 작성된 내용을 보낼 Form End --> 
                            <div id="EDITOR_AREA_CONTAINER"></div> 
                            <!-- Editor Area Container : End -->
                            
                            
              </td>
              <td colspan="2" class="tdwhiteL0">
              
              
                            <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                            <textarea name="webEditor1" rows="0" cols="0" style="display: none"><%=webEditor1 %></textarea> 
                            <textarea name="webEditorText1" rows="0" cols="0" style="display: none"><%=webEditorText1 %></textarea> 
                            <!-- Editor Area Container : Start -->
                            <!-- 이노디터에서 작성된 내용을 보낼 Form End --> 
                            <div id="EDITOR_AREA_CONTAINER1"></div> 
                            <!-- Editor Area Container : End -->
                            
                            
              </td>            
            </tr>           
	        <tr>
	        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
	        <td colspan="3" class="tdwhiteL0">
	          <!-- table border="0" cellspacing="0" cellpadding="0" width="100%">
	            <tr>
	              <td class="space5"></td>
	            </tr>
	          </table>
	          <div id="div_scroll3" style="width:100%;height=71;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
	          <table width="100%" cellpadding="0" cellspacing="0">
	            <!-- tr class="">
	              <td width="40" class="tdgrayM">No</td>
	              <td width="597" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
	            </tr -->
	            <%
	            Vector moldEcrAttacheVec = new Vector();
	            //moldEcrAttacheVec = ContentUtil.getSecondaryContents(ketMoldChangeRequestVO.getKetMoldChangeRequest());
	
	            ContentHolder holder = ContentHelper.service.getContents( (ContentHolder)ketMoldChangeRequestVO.getKetMoldChangeRequest() );
	            moldEcrAttacheVec = ContentUtil.getSecondaryContents(holder);
	            int max = moldEcrAttacheVec.size();
	
	            if( moldEcrAttacheVec != null )
	            {
	                ContentInfo cntInfo = null;
	                ContentItem ctf = null;
	                String appDataOid = "";
	                String url = "";
	                for ( int j = 0 ; j<max; j++ )
	                {
	                    cntInfo = (ContentInfo)moldEcrAttacheVec.elementAt(j);
	                    ctf=(ContentItem)CommonUtil.getObject(cntInfo.getContentOid());
	                    appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
	
                        //url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                        url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                        url = "<a href=" + url + " target='_blank'>" + cntInfo.getName() + "</a>&nbsp;(&nbsp;" + cntInfo.getFileSize() + "&nbsp;)";
	
                %>
                        <tr>
	                      <!-- td class="tdwhiteM"><%=j+1%></td -->
	                      <td class="tdwhiteL0"><%=url%></td>
	                    </tr>
	            <%
	                 }
	            }
	            if(max == 0)
	            {
	            %>
	              <!-- tr>
	                <td colspan="2" class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "02800") %><%--첨부파일이 존재하지 않습니다--%></td>
	              </tr -->
	            <%
	            }
	            %>
	          </table>
	          <!-- /div>
	          <table border="0" cellspacing="0" cellpadding="0" width="100%">
	            <tr>
	              <td class="space5"></td>
	            </tr>
	          </table -->
	        </td>
	      </tr>
	
	      <!-- 2013.03.12 e3ps shkim add start -->
	      <tr>
	        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00907") %><%--관련 ECO--%></td>
	        <td colspan="3" class="tdwhiteL0">
	          <!-- table border="0" cellspacing="0" cellpadding="0" width="100%">
	            <tr>
	              <td class="space5"></td>
	            </tr>
	          </table>
	          <div id="div_scroll3" style="width:100%;height=71;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
	          <table width="100%" cellpadding="0" cellspacing="0">
	            <tr class="">
	                <!-- td width="40" class="tdgrayM">No</td -->
	                <td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00199") %><%--ECO번호--%></td>
	                <td width="507" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00193") %><%--ECO 제목--%></td>
	            </tr>
	            <%
                int count = 0;
	            String ecoOid = "";
	            while(ecoqr.hasMoreElements()) {
	                Object obj = ecoqr.nextElement();
	                KETMoldChangeOrder eco = (KETMoldChangeOrder)obj;
	                ecoOid = eco.getPersistInfo().getObjectIdentifier().toString();
	                count++;
	            %>
	              <tr>
	                <!-- td class="tdwhiteM"><%=count %></td -->
	                <td class="tdwhiteL"><a href="javascript:viewEco('<%=ecoOid%>');"><%=eco.getEcoId()%></a></td>
	                <td class="tdwhiteL0"><%=eco.getEcoName()%></td>
	              </tr>
	            <%
	            }
	            %>
	          </table>
	          <!-- /div>
	          <table border="0" cellspacing="0" cellpadding="0" width="100%">
	            <tr>
	              <td class="space5"></td>
	            </tr>
	          </table -->
	        </td>
	      </tr>
	      <!-- 2013.03.12 e3ps shkim add end -->
	    </table>
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
