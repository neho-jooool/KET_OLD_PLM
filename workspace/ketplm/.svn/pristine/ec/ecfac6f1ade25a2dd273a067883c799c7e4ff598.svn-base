<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
              ,wt.org.WTUser
              ,wt.fc.*
              ,wt.query.*
              ,wt.content.ContentHelper
              ,wt.content.ContentHolder
              ,wt.content.ContentItem
              ,wt.session.SessionHelper
              ,wt.content.ApplicationData" %>

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@page import="java.sql.Timestamp"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

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
<html>
<head>
<%@include file="/jsp/common/multicombo.jsp" %>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 10px;
    margin-right: 10px;
    margin-bottom: 5px;
}
</style>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
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
    var strHTMLCode = document.forms[0].webEditor.value;
    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(strHTMLCode, false, 0);


    //SuggestUtil.bind('DEPARTMENT', 'callDepartmentDisplayName', 'callDepartmentOid');
    SuggestUtil.multiBind('DEPTUSER', 'callDepartmentDisplayName', 'callDepartmentOid', 'callAttendanceDisplayName', 'callAttendanceOid');
    SuggestUtil.multiBind('USERDEPT', 'callAttendanceDisplayName', 'callAttendanceOid', 'callDepartmentDisplayName', 'callDepartmentOid');
    //SuggestUtil.multiBindCallBackFunc('USERDEPT', 'callAttendanceDisplayName', 'callAttendanceOid', deptCallBackFunc); 콜백 함수로 OBJECT 리턴
    //SuggestUtil.bind('USER', 'callAttendanceDisplayName', 'callAttendanceOid');


    CalendarUtil.dateInputFormat('callMeetingDate'); 
    
    var strContent = document.ViewEcrForm["webEditor"].value;
    
    var objView = document.getElementById("divView");
    objView.innerHTML = strContent;
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
	/* 
	var divOpenCall = document.getElementById("divOpenCall");
    var divHistoryCall = document.getElementById("divHistoryCall");

    divOpenCall.style.display = "block";
    divHistoryCall.style.display = "none";
    */
    
    var url = "/plm/servlet/e3ps/ProdEcrServlet?prePage=Search&cmd=ViewCall&oid=<%=ecrOid %>";
    openWindow(url,"","800","600","scrollbars=no,resizable=no");

}

//보내기
function goCallMeeting() {
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

//취소
function closeCallMeeting() {
    var divOpenCall = document.getElementById("divOpenCall");
    var divHistoryCall = document.getElementById("divHistoryCall");

    divOpenCall.style.display = "none";
    divHistoryCall.style.display = "none";
	
}


<%--주관부서--%>
var SELECTED_POS = 0;
function lfn_addDepartment2(pos) {
	SELECTED_POS = pos;
	
    var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=s";
    attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=430px; dialogHeight:465px; center:yes");
    if(typeof attacheDept == "undefined" || attacheDept == null) {
        return;
    }

    var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
    var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
    callServer(url, lfn_acceptDept2);
}
function lfn_acceptDept2(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg != 'true') {
        alert('다시 시도하시기 바랍니다');
        return;
    }

    showElements = xmlDoc.selectNodes("//data_info");
    var l_oid = showElements[0].getElementsByTagName("l_oid");
    var l_name = showElements[0].getElementsByTagName("l_name");
    var l_code = showElements[0].getElementsByTagName("l_code");
    var l_chiefOid = showElements[0].getElementsByTagName("l_chiefOid");
    var l_chiefName = showElements[0].getElementsByTagName("l_chiefName");
    
    
    var callDepartmentDisplayNames = document.getElementsByName("callDepartmentDisplayName");
    var callDepartmentOids = document.getElementsByName("callDepartmentOid");
    var callDepartmentCodes = document.getElementsByName("callDepartmentCode");
    
    callDepartmentDisplayNames[SELECTED_POS].value = decodeURIComponent(l_name[0].text);
    callDepartmentOids[SELECTED_POS].value = decodeURIComponent(l_oid[0].text);
    callDepartmentCodes[SELECTED_POS].value = decodeURIComponent(l_code[0].text);

    // chiefName, chiefOid 추가
    var callAttendanceDisplayNames = document.getElementsByName("callAttendanceDisplayName");
    var callAttendanceOids = document.getElementsByName("callAttendanceOid");
    
    callAttendanceDisplayNames[SELECTED_POS].value = decodeURIComponent(l_chiefName[0].text);
    callAttendanceOids[SELECTED_POS].value = decodeURIComponent(l_chiefOid[0].text);
    
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
    
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
    attacheMembers = window.showModalDialog(url,window,"help=no; toolbar=no; location=no; directory=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
        return;
    }
    lfn_acceptCharge(attacheMembers);
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
        parent.lfn_reloadWhole();
        
    } catch(e) {}
}


function doPrint(){
    $('#buttonTable').hide();
    //window.print();
    //여백설정
    IEPageSetupX.topMargin=0;
    IEPageSetupX.bottomMargin=0;
    //가로설정
    IEPageSetupX.Orientation = 0;
    //머리글 설정
    IEPageSetupX.header="";
    //바닥글 설정
    IEPageSetupX.footer="";
    //배경색
    IEPageSetupX.PrintBackground = true;
    //미리보기
    IEPageSetupX.Preview();
    //인쇄
    //IEPageSetupX.Print();
    
    doneyet();
    
}

function doneyet() { 
    $('#buttonTable').show();
} 

</script>

<OBJECT id=IEPageSetupX classid="clsid:41C5BC45-1BE8-42C5-AD9F-495D6C8D7586" codebase="/plm/extcore/js/dashboard/IEPageSetupX.cab#version=1,4,0,3" width=0 height=0><param name="copyright" value="http://isulnara.com">
</OBJECT>   

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
    g_nEditorWidth = 600;
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
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display: none"><%=webEditor %></textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColor" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImage" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End -->

<table id="buttonTable" width="99%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td align="right">
                <table border="0" cellspacing="0" cellpadding="0">
	                <tr>
		                <td>
			                <table border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
			                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doPrint();" class="btn_blue" onfocus="this.blur();">인쇄</a></td>
			                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
			                    </tr>
			                </table>
			            </td>
			            <td width="5">&nbsp;</td>
			            <td>
				            <table border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
			                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue" onfocus="this.blur();">닫기</a></td>
			                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
			                    </tr>
			                </table>
			            </td>
		            </tr>
	            </table>
           </td>
        </tr>
    </table>
    <table width="99%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td class="space5"></td>
        </tr>
    </table>
    <div class="contents-wrap" style="width:99%">
        <div>
            <%-- 회의록 --%>
		   <table border="0" cellspacing="0" cellpadding="0">
		     <tr>
		       <td class="space5"></td>
		     </tr>
		   </table>      
          <table border="0" cellspacing="0" cellpadding="0" width="99%" class="table-style-04 border-top-line" style="table-layout: fixed;">
            <tr>
                <td class="title border-bottom-none"><span style="font-size:18px">회의록<span><br><span style="font-size:12px">(The Minutes of a Meeting)</span></td>
            </tr>
          </table>
          <table cellpadding="0" class="table-style-04 border-top-line" width="99%" style="table-layout: fixed;">
            <colgroup>
               <col width="115px" />
               <col width="363px" />
               <col width="115px" />
               <col width="363px" />
           </colgroup>
            <tr>
              <td width="115px" class="title"><%=messageService.getString("e3ps.message.ket_message", "04107") %><%--회의명--%></td>
              <td width="849px"  colspan="3"><%=meetingName %>&nbsp;</td>
            </tr>
            <tr>
              <td width="115px" class="title"><%=messageService.getString("e3ps.message.ket_message", "04108") %><%--회의일시--%>&nbsp;/&nbsp;<%=messageService.getString("e3ps.message.ket_message", "회의시간") %><%--회의시간--%></td>
              <td width="363px" ><%=meetingDate %>&nbsp;<%=meetingTime %>&nbsp;</td>
              <td width="115px" class="title"><%=messageService.getString("e3ps.message.ket_message", "회의장소") %><%--회의장소--%></td>
              <td width="363px" ><%=meetingLocation %>&nbsp;</td>
            </tr>
            <tr>
              <td width="115px" class="title"><%=messageService.getString("e3ps.message.ket_message", "04109") %><%--회의참석자--%></td>
              <td colspan="3" ><%=attendance %>&nbsp;</td>
            </tr>
            <tr>
              <td width="115px" class="title"><%=messageService.getString("e3ps.message.ket_message", "04103") %><%--주관부서--%></td>
              <td width="363px"><%=subjectDisplayName %>&nbsp;</td>
              <td width="115px"  class="title"><%=messageService.getString("e3ps.message.ket_message", "04104") %><%--담당자--%></td>
              <td width="363px"><%=chargeDisplayName %>&nbsp;</td>
            </tr>
            <tr>
              <td width="115px" class="title border-bottom-none"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
              <td colspan="3" class="border-bottom-none"><a href="javascript:viewHistory('<%=pboOid %>');"><%=stateDisplayName %></a>&nbsp;</td>
            </tr>
            </table>
            <table class="table-style-04" cellpadding="0" width="99%" style="table-layout: fixed;">
                <colgroup>
	               <col width="115px" />
	               <col width="363px" />
	               <col width="115px" />
	               <col width="363px" />
	           </colgroup>
                <tbody>
                    <tr>
                        <td width="115px" class="title border-bottom-none"><%=messageService.getString("e3ps.message.ket_message", "04110") %><%--회의내용--%></td>
                        <td colspan="3" class="border-bottom-none"> 
                            <div id="divView" style="overflow-x:auto; overflow-y:hidden; width: 849px"></div>
                        </td>
                    </tr>
                </tbody>
            </table>
            <table cellpadding="0" class="table-style-04 border-bottom-line" width="99%" style="table-layout: fixed;">       
            <colgroup>
                   <col width="115px" />
                   <col width="363px" />
                   <col width="115px" />
                   <col width="363px" />
               </colgroup>
            <tr>
              <td width="115px" class="title"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
              <td colspan="3">
                        <%
                        if( attachFileList != null && attachFileList.size() > 0 )
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
                              <%=url%>
                         <%
                          }
                        }
                        else
                        {
                        }
                        %>
              </td>
            </tr>
            
            <%
            // 제품 ECR일 경우에만 ECN를 사용한다.
            if (prodEcr instanceof e3ps.ecm.entity.KETProdChangeRequest) {
            %>
            <tr>
              <td width="115px" class="title"><%=messageService.getString("e3ps.message.ket_message", "04114") %><%--ECN--%></td>
              <td colspan="3" >
                <table width="100%" cellpadding="0" cellspacing="0" id="relDocTable" class="">
                  <tr>

                          <td style="width:10%" class="title"><%=messageService.getString("e3ps.message.ket_message", "02918") %><%--Type--%></td>
                          <td style="width:60%" class="title"><%=messageService.getString("e3ps.message.ket_message", "04115") %><%--후속업무--%></td>
                          <td style="width:15%" class="title"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                          <td style="width:15%" class="title"><%=messageService.getString("e3ps.message.ket_message", "02178") %><%--완료요청일--%></td>
                          
                  </tr>
                  
                <%
                if(!PersistenceHelper.isPersistent(ecn)) {
                %>
                  <!-- TR >
                    <td colspan='4'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td>
                  </TR -->
                <%
                } else {
                    
                    try {
                        result = PersistenceHelper.manager.navigate(ecn, "eca", KETEcnEcaLink.class, false);
                        if(result == null || result.size() == 0) {
                %>
                          <TR >
                            <td align="center" colspan='4'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td>
                          </TR>
                <%
    
                        } else {
                         
                            // Link 처리
                            KETEcnEcaLink ketEcnEcaLink = null;
                            // ECA
                            KETChangeActivity eca = null;
                            // 타입
                            String activityDisplayDsCode = "";
                            // 후속업무
                            String activityDisplayName = "";
                            // 담당자
                            String activityChargeDisplayName = "";
                            // 완료요청일
                            String activityCompleteRequestDate = "";
    
                            int rowIndex = 0;
                            while(result.hasMoreElements()) {
                                ketEcnEcaLink = (KETEcnEcaLink) result.nextElement();
                                // ECA
                                eca = ketEcnEcaLink.getEca();
                                
                                // 기준정보에 있을 경우
                                NumberCode activity = eca.getActivity();
                                if(PersistenceHelper.isPersistent(activity)) {
                                    // 타입
                                    activityDisplayDsCode = StringUtils.stripToEmpty(activity.getDsCode());
                                    // 후속업무
                                    activityDisplayName = HtmlUtils.htmlEscape(StringUtils.stripToEmpty(activity.getName()));
                                } 
                                // 사용자 입력일 경우
                                else {
                                    // 타입
                                    activityDisplayDsCode = StringUtils.stripToEmpty(eca.getCustomType());
                                    // 후속업무
                                    activityDisplayName = HtmlUtils.htmlEscape(StringUtils.stripToEmpty(eca.getCustomName()));
                                }
                                
                                // 담당자
                                WTUser charge = eca.getCharge();
                                activityChargeDisplayName = (charge != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(charge.getFullName())) : "";
                                // String chargeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(meeting.getCharge()));
                                // String chargeName = StringUtils.stripToEmpty(meeting.getChargeName());
       
                                // 완료요청일
                                activityCompleteRequestDate = DateUtil.getTimeFormat(eca.getCompleteRequestDate(), "yyyy-MM-dd"); 
                %>
                                <tr>
                                  <%
                                  String activityDisplayDsCodeDisplayName = "";
                                  if(activityDisplayDsCode.equals("") || activityDisplayDsCode.equalsIgnoreCase("DOC")) activityDisplayDsCodeDisplayName = messageService.getString("e3ps.message.ket_message", "04119");
                                  else activityDisplayDsCodeDisplayName = messageService.getString("e3ps.message.ket_message", "04120");
                                  %>
                                  <td align="center" class="border-bottom-none"><%=activityDisplayDsCodeDisplayName %>&nbsp;</td>
                                  <td class="border-bottom-none" title="<%=activityDisplayName %>"><%=activityDisplayName %>&nbsp;</td>
                                  <td align="center" class="border-bottom-none"><%=activityChargeDisplayName %>&nbsp;</td>
                                  <td align="center" class="border-bottom-none"><%=activityCompleteRequestDate %>&nbsp;</td>         
                                </tr>
                <%
                                rowIndex++;
                        
                            }  // while(result.hasMoreElements()) {
                        }
                
                    } catch(Exception e) {
                	   Kogger.error(e);
                    }
                            
                }
                %>                  
                </table>   
              </td>
            </tr>    
            <%
            }
            %>
                     
          </table>
        </div>
    </div>
</body>
</html>
