<%@page import="e3ps.common.util.NumberCodeUtil"%>
<%@page import="e3ps.ecm.entity.KETProdChangeOrder"%>
<%@page import="e3ps.ecm.entity.KETProdChangeLink"%>

<%@ page import="wt.org.WTUser
                            ,wt.fc.QueryResult
                            ,wt.query.*
                            ,wt.content.ContentHolder
                            ,wt.content.ContentHelper
                            ,wt.content.ContentRoleType
                            ,wt.content.ContentItem
                            ,wt.session.SessionHelper
                            ,wt.fc.PersistenceHelper"%>
<%@ page import= "java.util.Vector
                             ,java.net.URL
                             ,java.util.ArrayList
                             ,java.util.Hashtable" %>
<%@ page import= "e3ps.ecm.entity.KETProdECRIssueLink
                             ,e3ps.common.util.DateUtil
                             ,e3ps.common.util.StringUtil
                             ,e3ps.common.util.KETObjectUtil
                             ,e3ps.ecm.entity.*
                             ,e3ps.common.util.CommonUtil
                             ,e3ps.common.content.ContentInfo
                             ,e3ps.common.content.ContentUtil
                             ,e3ps.groupware.company.*
                             ,e3ps.common.code.NumberCode
                             ,e3ps.common.code.NumberCodeHelper
                             ,e3ps.project.ProjectIssue
                             ,e3ps.project.outputtype.OEMProjectType
                             ,e3ps.ecm.beans.EcmUtil"%>

<%@page import="ext.ket.dqm.entity.*"%>
                             
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@page import="java.sql.Timestamp"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
                             
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="prodEcr" class="e3ps.ecm.entity.KETProdChangeRequest" scope="request"/>
<jsp:useBean id="ecrHash" class="java.util.Hashtable" scope="request"/>
<jsp:useBean id="tabName" class="java.lang.String" scope="request"/>

<jsp:useBean id="prePage" class="java.lang.String" scope="request"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%
    // 결재대상 화면 여부
    boolean isIframe = Boolean.parseBoolean(StringUtil.checkReplaceStr(request.getParameter("isIframe"), "false"));

    WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
    WTUser owner = (WTUser)prodEcr.getCreator().getPrincipal();

    boolean isOwner = false;
    if( owner.equals( loginUser ) )
    {
        isOwner = true;
    }

    
    // ECR 전체 상태
    wt.lifecycle.State ecrStateState = prodEcr.getEcrStateState();
    String ecrStateStateCode = (ecrStateState != null) ? ecrStateState.toString() : "";
    String ecrStateDisplayName = (ecrStateState != null) ? ecrStateState.getDisplay() : "";


    // 관련이슈
    QueryResult issueQr = PersistenceHelper.manager.navigate( prodEcr, "issue", KETProdECRIssueLink.class );
    
    // 관련품질문제
    QueryResult dqmQr = PersistenceHelper.manager.navigate( prodEcr, "dqm", KETEcrDqmLink.class );
    
    ArrayList<Hashtable<String, String>> partList = new ArrayList<Hashtable<String, String>>();
    partList = (ArrayList)ecrHash.get("partList");

    ContentHolder holder = ContentHelper.service.getContents( (ContentHolder)prodEcr );
    Vector attachFileList = ContentUtil.getSecondaryContents(holder);

    //제품, 금형 ECR 확장팩
    KETChangeRequestExpansion expansion = null;
    // ECR 로 찾는다.
    QuerySpec spec = new QuerySpec(KETChangeRequestExpansion.class);
    spec.appendWhere(new SearchCondition(KETChangeRequestExpansion.class, "ecrReference.key.id", "=", CommonUtil.getOIDLongValue(prodEcr)), new int[] { 0 });
    QueryResult result = PersistenceHelper.manager.find(spec);
    if (result.hasMoreElements()) { // while (result.hasMoreElements()) {
      expansion = (KETChangeRequestExpansion) result.nextElement();
    }
    
    QueryResult ecoQr = PersistenceHelper.manager.navigate( prodEcr, "prodECO", KETProdChangeLink.class );
    String ecoid = "";
    if( ecoQr != null && ecoQr.size() > 0 )
    {
        while( ecoQr.hasMoreElements() )
        {
            KETProdChangeOrder eco = (KETProdChangeOrder) ecoQr.nextElement();
            ecoid += eco.getEcoId()+",\r\n";
        }
    }
    ecoid = StringUtils.removeEnd(ecoid, ",\r\n");
    
    String reviewRequestDate = "";
    String carTypeDisplayName = "";
    String carTypeOid = "";
    String carTypeCode = "";
    
    String moldChangeDisplayName = "";
    String moldChangeOid = "";
    String moldChangeCode = "";

    String costChangeDisplayName = "";
    String costChangeOid = "";
    String costChangeCode = "";

    String emergencyPositionDisplayName = "";
    String emergencyPositionOid = "";
    String emergencyPositionCode = "";

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
        
        // 차종
        OEMProjectType carType = expansion.getCarType();
        carTypeDisplayName = (carType != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(carType.getName())) : "";
        carTypeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(carType));
        carTypeCode = StringUtils.stripToEmpty(expansion.getCarTypeCode());
        
        // 금형변경
        NumberCode moldChange = expansion.getMoldChange();
        moldChangeDisplayName = (moldChange != null) ? StringUtils.stripToEmpty(moldChange.getName()) : "";
        moldChangeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(moldChange));
        moldChangeCode = StringUtils.stripToEmpty(expansion.getMoldChangeCode());
        
        // 원가변동
        NumberCode costChange = expansion.getCostChange();
        costChangeDisplayName = (costChange != null) ? StringUtils.stripToEmpty(costChange.getName()) : "";
        costChangeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(costChange));
        costChangeCode = StringUtils.stripToEmpty(expansion.getCostChangeCode());
      
        // 긴급도
        NumberCode emergencyPosition = expansion.getMoldChange();
        emergencyPositionDisplayName = (emergencyPosition != null) ? StringUtils.stripToEmpty(emergencyPosition.getName()) : "";
        emergencyPositionOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(emergencyPosition));
        emergencyPositionCode = StringUtils.stripToEmpty(expansion.getEmergencyPositionCode());
            
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

    
    boolean isExistProdEco = EcmUtil.isExistProdEco( ecrHash.get("ecr_oid").toString() );

    
    // [START] [PLM System 1차 고도화] Windchill 10.2 Upgrade 후 에러 처리, 2014-06-09, 김태현
    //QueryResult ecoqr = PersistenceHelper.manager.navigate(prodEcr, "prodECO", KETProdChangeLink.class );
    QueryResult ecoqr = null;
    try {
       ecoqr = PersistenceHelper.manager.navigate(prodEcr, "prodECO", KETProdChangeLink.class );
    
    } catch( Exception e ) {
	    Kogger.error(e);
    }
    // [END] [PLM System 1차 고도화] Windchill 10.2 Upgrade 후 에러 처리, 2014-06-09, 김태현

    
    // 주간부서
    KETCompetentDepartment competent = null;
    String competentStateCode = "";
    QueryResult qr = PersistenceHelper.manager.navigate(prodEcr, "competent", KETEcrCompetentLink.class, false);
    while (qr.hasMoreElements()) {
        KETEcrCompetentLink link = (KETEcrCompetentLink) qr.nextElement();
        competent = link.getCompetent();
        
        competentStateCode = competent.getLifeCycleState().toString();
    }
    
    // 회의록
    KETMeetingMinutes meeting = null;
    String meetingStateCode = "";
    qr = PersistenceHelper.manager.navigate(prodEcr, "meeting", KETEcrMeetingLink.class, false);
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
<title>Untitled Document</title>

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
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    var strHTMLCode = document.forms[0].webEditor.value;
    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(strHTMLCode, false, 0);

    var strHTMLCode1 = document.forms[0].webEditor1.value;
    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(strHTMLCode1, false, 1);

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    //fnSetBodyStyleValue(1, document.forms[0].hdnBackgroundColor.value, 0);// 배경색, Value, 이노디터 번호
    //fnSetBodyStyleValue(2, document.forms[0].hdnBackgroundImage.value, 0);// 배경이미지, Value, 이노디터 번호
    //fnSetBodyStyleValue(3, document.forms[0].hdnBackgroundRepeat.value, 0);// 배경이미지 반복옵션, Value, 이노디터 번호
    ////////////////////////////////////////////////////////////////////////////////////////////////////
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
function goDelete()
{
    if(confirm("<%=messageService.getString("e3ps.message.ket_message", "01707") %><%--삭제하시겠습니까?--%>")){
        document.forms[0].cmd.value='Delete';
        document.forms[0].target = 'download';
        document.forms[0].action = '/plm/servlet/e3ps/ProdEcrServlet';
        disabledAllBtn();
        showProcessing();
        document.forms[0].submit();
    }
}

function goCreateEco()
{
	var ecoid = document.forms[0].ecoid.value;
	// 주간부서가 '승인완료'되어야 한다.
	var competentStateCode = '<%=competentStateCode %>';
    if(competentStateCode != 'APPROVED') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "04129") %><%--주관부서가 \'승인완료\'되어야 합니다.--%>');
        return;
    }
    
    if(ecoid != ""){
    	if(!confirm("연계된 ECO가 존재합니다.\r\n\r\n"+ecoid+"\r\n\r\n추가ECO 발행하시겠습니까?")){
            return;
        }
    }
    
    
    
	// 회의록이 '승인완료'되어야 한다.
	<%-- 
	var meetingStateCode = '<%=meetingStateCode %>';
	if(meetingStateCode != 'APPROVED') {
		alert('<%=messageService.getString("e3ps.message.ket_message", "04130") %>회의록이 \'승인완료\'되어야 합니다.');
		return;
	}
	--%>
    document.forms[0].cmd.value = 'CreateECO';
    document.forms[0].action = '/plm/servlet/e3ps/ProdEcrServlet';
    document.forms[0].target = '_parent';
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
    goPage(form.oid.value);
}

//품질문제 상세조회 팝업
function viewRelDqm(v_poid){
  var url = '/plm/ext/dqm/dqmMainForm.do?type=view&oid='+ v_poid;
  getOpenWindow2(url, 'dqmMainFormPopup', 1100, 768);
}


//부품 상세조회 팝업
function viewPart(v_poid){
    var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

//프로젝트상세조회 팝업
function viewProjectPopup(oid) {
	if(oid == null || oid == '') return;
	
    var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid=" + oid + "&popup=popup";
    openWindow(url, '',1050,800);
}

function viewIssue(oid){
    var url = '/plm/jsp/project/projectIssueView.jsp?oid='+oid;
    openWindow2(url,"","750","320","scrollbars=no,resizable=no,top=200,left=250");
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
<body>
<form name="ViewEcrForm"  method="post">
<input type="hidden"  name="cmd"  value="View">
<input type= "hidden" name ="oid"  value="<%=CommonUtil.getOIDString( prodEcr )%>" >
<input type="hidden"  name="prePage"  value="<%=prePage %>">
<input type="hidden"  name="ecoid"  value="<%=ecoid %>">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <!-- table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02542") %><%--제품 ECR 상세조회--%></td>
                <td align="right">
                    <img src="/plm/portal/images/icon_navi.gif">Home
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00204") %><%--ECR 관리--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00202") %><%--ECR 검색--%></td>
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
                <%
                if( isOwner && (ecrHash.get("status").toString().equals("INWORK") || ecrHash.get("status").equals("REWORK")) )
                {
                %>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goMoify();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goDelete();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:requestApprove();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
               <%
                }
                if( (ecrHash.get("status").toString().equals("APPROVED") || ecrStateStateCode.equals("CONSIDERED")) && ( KETObjectUtil.isAdmin() || (KETObjectUtil.isMember("자동차사업부_제품설계") || KETObjectUtil.isMember("전자사업부_제품설계") || KETObjectUtil.isMember("KETS_제품설계")) ) )
                {
               %>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goCreateEco();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00191") %><%--ECO 작성--%></a></td>
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
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goList();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01378") %><%--목록--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td -->
               <%
               // 승인(디버깅)
               if(KETObjectUtil.isAdmin()  && ecrHash.get("status").toString().equals("UNDERREVIEW")) {
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
      
      
      <div id="ecrDiv1" style="width:100%; height:640px; overflow-x:auto; overflow-y:auto;">
        
          <table border="0" cellspacing="0" cellpadding="0" width="100%">
	        <tr>
	          <td width="130" class="tdblueL">ECR No</td>
	          <td class="tdwhiteL"><%=ecrHash.get("ecr_id").toString() %></td>
	          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%></td>
	          <td class="tdwhiteL0"><%=NumberCodeUtil.getNumberCodeValue("DEVYN", ecrHash.get("dev_yn").toString(), messageService.getLocale().toString())%></td>
	        </tr>
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
	          <td class="tdwhiteL" title="<%=prodEcr.getEcrName().toString() %>"><div class="ellipsis" style="width:310px;"><nobr><%=prodEcr.getEcrName().toString() %></nobr></div></td>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "ECR 상태") %><%--ECR 상태--%></td>
              <td class="tdwhiteL0"><%=ecrStateDisplayName %></td>
	        </tr>
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01204") %><%--담당사업부--%><span class="red"></span></td>
	          <td class="tdwhiteL"><%=ecrHash.get("division_desc").toString() %></td>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
	          <td class="tdwhiteL0"><a href="javascript:viewHistory('<%=ecrHash.get("ecr_oid").toString() %>');"><%=prodEcr.getLifeCycleState().getDisplay(messageService.getLocale())%></a>&nbsp;</td>
	        </tr>
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%><span class="red"></span></td>
	          <td class="tdwhiteL"><%=prodEcr.getDeptName() %></td>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
	          <td class="tdwhiteL0"><%=prodEcr.getCreatorFullName() %></td>
	        </tr>
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
	          <td class="tdwhiteL"><%=ecrHash.get("create_date").toString()  %></td>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
	          <td class="tdwhiteL0"><%=ecrHash.get("modify_date").toString() %></td>
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02000") %><%--승인자--%></td>
	          <td class="tdwhiteL"><%=StringUtil.checkReplaceStr(EcmUtil.getLastApproverName( prodEcr ), "-") %>&nbsp;</td>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
	          <td class="tdwhiteL0"><%=StringUtil.checkReplaceStr(EcmUtil.getLastApproveDate( prodEcr ), "-")%>&nbsp;</td>
	        </tr>
	        <tr>
	          <td class="tdblueL">Project No</td>
	          <td class="tdwhiteL">
	            <% 
	            if(ecrHash.get("pjt_no") == null || ecrHash.get("pjt_no").toString() == null || ecrHash.get("pjt_no").toString().equals("")) {
	            %>
	            <%=StringUtil.checkReplaceStr(ecrHash.get("pjt_no").toString(), "-") %>&nbsp;
	            <%
	            } else {
	            %>
	            <a href="javascript:openProject('<%=ecrHash.get("pjt_no").toString() %>');"><%=StringUtil.checkReplaceStr(ecrHash.get("pjt_no").toString(), "-") %></a>&nbsp;
	            <%
	            }
	            %>
	          </td>
	          <td class="tdblueL">Project Name</td>
	          <td class="tdwhiteL0"><%=StringUtil.checkReplaceStr(ecrHash.get("pjt_name").toString(), "-")%>&nbsp;</td>
	        </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04123") %><%--검토요청기한--%></td>
              <td class="tdwhiteL"><%=reviewRequestDate %>&nbsp;</td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04122") %><%--차종--%></td>
              <td class="tdwhiteL0"><%=carTypeDisplayName %>&nbsp;</td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09145") %><%--검토부서--%></td>
              <td class="tdwhiteL"><%=subjectDisplayName %>&nbsp;</td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04104") %><%--담당자--%></td>
              <td class="tdwhiteL0"><%=chargeDisplayName %>&nbsp;</td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04124") %><%--금형변경--%></td>
              <td class="tdwhiteL"><%=NumberCodeUtil.getNumberCodeValue("MOLDCHANGE", moldChangeCode, messageService.getLocale().toString())%>&nbsp;</td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04125") %><%--원가변동--%></td>
              <td class="tdwhiteL0"><%=NumberCodeUtil.getNumberCodeValue("COSTCHANGE", costChangeCode, messageService.getLocale().toString())%>&nbsp;</td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04126") %><%--긴급도--%></td>
              <td class="tdwhiteL"><%=NumberCodeUtil.getNumberCodeValue("EMERGENCYPOSITION", emergencyPositionCode, messageService.getLocale().toString())%>&nbsp;</td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01859") %><%--설계변경요청사유--%></td>
              <td class="tdwhiteL0" ><%=NumberCodeUtil.getNumberCodeValue("PRODCHAGEREASON", prodEcr.getChangeReason(), messageService.getLocale().toString())%>&nbsp;</td>
            </tr>
            <%-- 	        
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00939") %>관련이슈</td>
	          <td colspan="3" class="tdwhiteL0">
	            <!-- table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	            <div id="div_scroll" style="width:100%;height=71;overflow:auto;" class="table_border" -->
	            <table width="100%" cellpadding="0" cellspacing="0" style="border:0px;">
	              <tr class="">
	                <!-- td width="20" class="tdgrayM">No</td -->
	                <td width="" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02317") %>이슈명</td>
	                <td width="145" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %>작성자</td>
	                <td width="102" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02429") %>작성일자</td>
	              </tr>
	           <%
	           if( issueQr != null && issueQr.size() > 0 )
	           {
	               int issueCnt = issueQr.size();
	               ProjectIssue issue = null;
	               while( issueQr.hasMoreElements() )
	               {
	                   issue = (ProjectIssue)issueQr.nextElement();
	           %>
	              <tr>
	                <!-- td class="tdwhiteM"><%=issueCnt-- %></td -->
	                <td class="tdwhiteL" title='<%=issue.getSubject()%>'><a href="javascript:viewIssue('<%=CommonUtil.getOIDString(issue) %>');"><div class="ellipsis" style="width:342;"><nobr><%=issue.getSubject() %></nobr></div></a></td>
	                <td class="tdwhiteM"><%=issue.getOwner().getFullName() %></td>
	                <td class="tdwhiteM0"><%=DateUtil.getTimeFormat( issue.getCreateDate(), "yyyy-MM-dd" ) %></td>
	              </tr>
	           <%
	               }
	           }
	           else
	           {
	           %>
	                   <!-- tr>
	                       <td colspan=4 class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "00918") %>관련 이슈가 없습니다</td>
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
	        --%>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04620") %><%--관련품질문제--%></td>
              <td colspan="3" class="tdwhiteL0">
                <table width="100%" cellpadding="0" cellspacing="0" style="border:0px;">
                  <tr class="">
                    <td width="" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04630") %><%--문제점--%></td>
                    <td width="145" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04640") %><%--작성자--%></td>
                    <td width="102" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04650") %><%--작성일자--%></td>
                  </tr>
               <%
               if( dqmQr != null && dqmQr.size() > 0 )
               {
                   while( dqmQr.hasMoreElements() )
                   {
                       KETDqmAction ketDqmAction = (KETDqmAction) dqmQr.nextElement();
                       
                       QuerySpec query = new QuerySpec();
                       int idxHeaer = query.appendClassList(KETDqmHeader.class, true);
                       SearchCondition sc = new SearchCondition(KETDqmHeader.class, "actionReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(ketDqmAction));
                       query.appendWhere(sc, new int[] { idxHeaer });

                       QueryResult dqmHeaderQr = PersistenceHelper.manager.find(query);
                       while (dqmHeaderQr.hasMoreElements()) {
                	       Object[] tempObj = (Object[]) dqmHeaderQr.nextElement();
                           KETDqmHeader ketDqmHeader = (KETDqmHeader) tempObj[0];
                           
                           KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
                           WTUser createUser = (WTUser) ketDqmRaise.getCreator().getPrincipal();
                           PeopleData peopleData = new PeopleData(createUser);
                      
               %>
                  <tr>
                    <td class="tdwhiteL" title='<%=ketDqmHeader.getProblem()%>'><a href="javascript:viewRelDqm('<%=CommonUtil.getOIDString(ketDqmHeader) %>');"><div class="ellipsis" style="width:342;"><nobr><%=ketDqmHeader.getProblem() %></nobr></div></a></td>
                    <td class="tdwhiteM"><%=peopleData.name %></td>
                    <td class="tdwhiteM0"><%=DateUtil.getDateString(ketDqmRaise.getCreateTimestamp(), "date") %></td>
                  </tr>
               <%
                       }    // while (dqmHeaderQr.hasMoreElements()) {
                   }    // while( dqmQr.hasMoreElements() )
               }
               else
               {
               %>
                       <!-- tr>
                           <td colspan=4 class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "00918") %><%--관련 이슈가 없습니다--%></td>
                       </tr -->
               <%
               }
               %>
                </table>
              </td>
            </tr>	        
	        <!-- tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
	          <td colspan="3" class="tdwhiteL0">
	              <table border="0" cellspacing="0" cellpadding="0" width="645">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	              <textarea name="ecr_desc" style='overflow: auto; width:100%; height:50px; padding: 2;' class="txt_field" onfocus="this.blur();" readonly><%=StringUtil.checkNull(prodEcr.getEcrDesc()) %></textarea>
	              <table border="0" cellspacing="0" cellpadding="0" width="645">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	          </td>
	        </tr>
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01116") %><%--기대효과--%></td>
	          <td colspan="3" class="tdwhiteL0">
	              <table border="0" cellspacing="0" cellpadding="0" width="645">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	              <textarea name="ecr_effect" style='overflow: auto; width:100%; height:50px; padding: 2;' class="txt_field"  onfocus="this.blur();" readonly><%=StringUtil.checkNull(prodEcr.getExpectEffect()) %></textarea>
	              <table border="0" cellspacing="0" cellpadding="0" width="645">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	          </td>
	        </tr -->
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00932") %><%--관련부품--%></td>
	          <td colspan="3" class="tdwhiteL0">
	            <!-- table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	            <div id="div_scroll2" style="width:100%;height=71;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
	            <table width="100%" cellpadding="0" cellspacing="0">
	              <tr class="">
	                <!-- td width="40"  class="tdgrayM">No</td -->
	                <td width="90"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
	                <td width="310" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
	                <td width="50"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
	                <!-- td width="148" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02193") %><%--요청사항--%></td -->
	              </tr>
	            <%
	            if( partList != null )
	            {
	                int partCnt = partList.size();
	                Hashtable<String, String> part = null;
	                for( int pCnt=0; pCnt < partList.size(); pCnt++ )
	                {
	                    part = partList.get( pCnt );
	            %>
	              <tr>
	                <!-- td class="tdwhiteM"><%=partCnt-- %></td -->
	                <td class="tdwhiteM"><a href="javascript:viewPart('<%=part.get("part_oid") %>');"><%=part.get("part_no") %></a></td>
	                <td class="tdwhiteL" title="<%=part.get("part_name") %>"><div class="ellipsis" style="width:302;"><nobr><%=part.get("part_name") %></nobr></div></td>
	                <td class="tdwhiteM0"><%=part.get("revDis")%></td>
	                <!-- td class="tdwhiteM0"><%=part.get("ver")%></td -->
	                <!-- td class="tdwhiteL0"><%=part.get("req_comment") %>&nbsp;</td -->
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
	              if( attachFileList.size() > 0 )
	              {
	                int attachCnt = attachFileList.size();
	                ContentInfo cntInfo = null;
	                ContentItem ctf = null;
	                String appDataOid = "";
	                String url = "";
	                  for( int fileCnt=0; fileCnt < attachFileList.size() ; fileCnt++ )
	                  {
	                      cntInfo = (ContentInfo)attachFileList.elementAt(fileCnt);
	                      ctf=(ContentItem)CommonUtil.getObject(cntInfo.getContentOid());
	                                    appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
	
	                                    //url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                                        url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
	                                    url = "<a href=" + url + " target='_blank'>" + cntInfo.getName() + "</a>&nbsp;(&nbsp;" + cntInfo.getFileSize() + "&nbsp;)";
	                 %>
	                      <tr>
	                           <!-- td class="tdwhiteM"><%=attachCnt-- %></td -->
	                           <td class="tdwhiteL0"><%=url%></td>
	                      </tr>
	                 <%
	                      }
	                }
	                else
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
	              <%
	              if(ecoqr != null && ecoqr.hasMoreElements()) {
	              %>
                    <table width="100%" cellpadding="0" cellspacing="0">
                          <tr class="">
                            <!-- td width="40" class="tdgrayM">No</td -->
                            <td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00199") %><%--ECO번호--%></td>
                            <td width="507" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "00193") %><%--ECO 제목--%></td>
                          </tr>
                  <%	                          
	              	  int count = 0;
	                  String ecoOid = "";
	                          
	                          
	                  // [START] [PLM System 1차 고도화] Windchill 10.2 Upgrade 후 에러 처리, 2014-06-09, 김태현
	                  //while(ecoqr.hasMoreElements()) {
	                  while(ecoqr != null && ecoqr.hasMoreElements()) {    
	                  // [END] [PLM System 1차 고도화] Windchill 10.2 Upgrade 후 에러 처리, 2014-06-09, 김태현
	                           
	                           
	                      Object obj = ecoqr.nextElement();
	                      KETProdChangeOrder eco = (KETProdChangeOrder)obj;
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
	              <%    
	              } else {
	              %>
	                &nbsp;
	                <!-- table width="100%" cellpadding="0" cellspacing="0">
                      <tr>
                        <td colspan="2" class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "04113") %><%--관련 ECO가 존재하지 않습니다.--%></td>
                      </tr>
                    </table -->
                  <%
                  }
                  %>	                      
	              
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
