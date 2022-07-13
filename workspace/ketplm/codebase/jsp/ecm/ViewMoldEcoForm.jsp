<%@page import="java.util.Vector" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Hashtable" %>

<%@page import="org.apache.commons.lang.StringUtils" %>

<%@page import="wt.org.WTUser" %>
<%@page import="wt.session.SessionHelper" %>
<%@page import="wt.content.ContentHolder
                            ,wt.content.ContentHelper" %>
<%@page import="wt.content.ContentItem" %>
<%@page import="e3ps.ecm.entity.KETMoldChangeOrder" %>
<%@page import="e3ps.ecm.entity.KETMoldChangeOrderVO" %>
<%@page import="e3ps.ecm.entity.KETMoldECOPartLink" %>
<%@page import="e3ps.ecm.entity.KETMoldECOPartLinkVO" %>
<%@page import="e3ps.ecm.entity.KETMoldECALinkVO" %>
<%@page import="e3ps.ecm.entity.KETMoldEcoEcrLinkVO" %>
<%@page import="e3ps.ecm.beans.EcmUtil" %>
<%@page import="e3ps.ecm.beans.EcmSearchHelper" %>
<%@page import="e3ps.common.content.ContentInfo" %>
<%@page import="e3ps.common.content.ContentUtil" %>
<%@page import="e3ps.common.util.StringUtil" %>
<%@page import="e3ps.common.util.KETStringUtil" %>
<%@page import="e3ps.common.util.CommonUtil" %>
<%@page import="e3ps.common.web.ParamUtil" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="ketMoldChangeOrderVO" class="e3ps.ecm.entity.KETMoldChangeOrderVO" scope="request" />
<%
//결재대상 화면 여부
boolean isIframe = Boolean.parseBoolean(StringUtil.checkReplaceStr(request.getParameter("isIframe"), "false"));

WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
WTUser owner = (WTUser)ketMoldChangeOrderVO.getKetMoldChangeOrder().getCreator().getPrincipal();
String mold_oid = ketMoldChangeOrderVO.getMoldOid(); //양산금형 link oid
boolean isOwner = false;
if( owner.equals( loginUser ) ) {
    isOwner = true;
}

boolean isAdmin = CommonUtil.isAdmin();

boolean isPlanning = false;//계획수립여부
boolean isExcuteActivity = false;//활동수행여부
boolean isActivityCompleted = false;//활동완료여부
boolean hasECA = false;

boolean isViewableState = true;  //변경전후 조회 가능 상태


String createStartDate = StringUtil.checkNull(request.getParameter("createStartDate"));
String createEndDate = StringUtil.checkNull(request.getParameter("createEndDate"));

String fromPage = StringUtil.checkNull(request.getParameter("fromPage"));
int size = 0;
String chkChangeReason[] = new String[7];
String chkIncreaseProdType[] = new String[12];
String codeChangeReason[] = new String[7];
String codeIncreaseProdType[] = new String[12];
if(ketMoldChangeOrderVO.getTotalCount() > 0) {

    ArrayList<KETMoldECALinkVO> ecaList =  ketMoldChangeOrderVO.getKetMoldECALinkVOList();
    if( ecaList != null && ecaList.size() > 0 )
    {
        hasECA = true;
    }

    if("PLANNING".equals(ketMoldChangeOrderVO.getProgressState()) || "REWORK".equals(ketMoldChangeOrderVO.getProgressState()) ) {
        isPlanning = true;
    }
    if("EXCUTEACTIVITY".equals(ketMoldChangeOrderVO.getProgressState())) {
        isExcuteActivity = true;
    }
    if( "ACTIVITYCOMPLETED".equals( ketMoldChangeOrderVO.getProgressState() ) ) {
        isActivityCompleted = true;
    }

    if( isPlanning || isExcuteActivity )
    {
        isViewableState = false;
    }

    ArrayList arrChangeReason = KETStringUtil.getToken(ketMoldChangeOrderVO.getKetMoldChangeOrder().getChangeReason(), "|");
    ArrayList arrIncreaseProdType = KETStringUtil.getToken(ketMoldChangeOrderVO.getKetMoldChangeOrder().getIncreaseProdType(), "|");
    int i = 0;
    int idx = 0;
    for(i=0; i<7; i++) {
        chkChangeReason[i] = "";
        codeChangeReason[i] = "REASON_" + (i+1);
    }
    for(i=0; i<12; i++) {
        chkIncreaseProdType[i] = "";
        codeIncreaseProdType[i] ="INCR_" + (i+1);
    }
    size = arrChangeReason.size();
    for(i=0; i<size; i++) {
        idx = EcmUtil.getMatchArrIndex((String)arrChangeReason.get(i), codeChangeReason);
        if(idx >= 0) {
            chkChangeReason[idx] = "checked";
        }
    }
    size = arrIncreaseProdType.size();
    for(i=0; i<size; i++) {
        idx = EcmUtil.getMatchArrIndex((String)arrIncreaseProdType.get(i), codeIncreaseProdType);
        if(idx >= 0) {
            chkIncreaseProdType[idx] = "checked";
        }
    }
}


boolean isSucessSapInterface = ketMoldChangeOrderVO.isSucessSapInterface();


//초도인지 아닌지
String changeReason = StringUtils.stripToEmpty(ketMoldChangeOrderVO.getKetMoldChangeOrder().getChangeReason());
boolean isTheFirst = (changeReason.lastIndexOf("REASON_10") > -1);

//ECA가 있는 지 없는지
ArrayList<KETMoldECALinkVO> ecaList = ketMoldChangeOrderVO.getKetMoldECALinkVOList();
boolean hasEcaListSize = (ecaList != null && ecaList.size() > 0);


//제품, 금형 ECO 확장팩
/* 
KETChangeRequestExpansion expansion = null;
//ECR 로 찾는다.
QuerySpec spec = new QuerySpec(KETChangeRequestExpansion.class);
spec.appendWhere(new SearchCondition(KETChangeRequestExpansion.class, "ecrReference.key.id", "=", CommonUtil.getOIDLongValue(prodEcr)), new int[] { 0 });
QueryResult result = PersistenceHelper.manager.find(spec);
if (result.hasMoreElements()) { // while (result.hasMoreElements()) {
expansion = (KETChangeRequestExpansion) result.nextElement();
}

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
*/


//변경 전
String webEditor = "";
String webEditorText = "";

//변경 후
String webEditor1 = "";
String webEditorText1 = "";

if(ketMoldChangeOrderVO.getKetMoldChangeOrder() != null) {
 webEditor = StringUtils.stripToEmpty((String) ketMoldChangeOrderVO.getKetMoldChangeOrder().getWebEditor());
 webEditorText = StringUtils.stripToEmpty((String) ketMoldChangeOrderVO.getKetMoldChangeOrder().getWebEditorText());

 webEditor1 = StringUtils.stripToEmpty((String) ketMoldChangeOrderVO.getKetMoldChangeOrder().getWebEditor1());
 webEditorText1 = StringUtils.stripToEmpty((String) ketMoldChangeOrderVO.getKetMoldChangeOrder().getWebEditorText1());
}


/*   
if (PersistenceHelper.isPersistent(expansion)) {
  
  // 검토요청기한
  reviewRequestDate = DateUtil.getTimeFormat(expansion.getReviewRequestDate(), "yyyy-MM-dd"); 
  
  // 차종
  carTypeDisplayName = "";
  carTypeOid = "";
  carTypeCode = "";
  
  // 금형변경
  NumberCode moldChange = expansion.getMoldChange();
  moldChangeDisplayName = (moldChange != null) ? StringUtils.stripToEmpty(moldChange.getName()) : "";
  moldChangeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(moldChange));
  moldChangeCode = StringUtils.stripToEmpty(expansion.getMoldChangeCode());
  
  // 원가변동
  NumberCode costChange = expansion.getCostChange();
  costChangeDisplayName = (moldChange != null) ? StringUtils.stripToEmpty(costChange.getName()) : "";
  costChangeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(costChange));
  costChangeCode = StringUtils.stripToEmpty(expansion.getCostChangeCode());

  // 긴급도
  NumberCode emergencyPosition = expansion.getMoldChange();
  emergencyPositionDisplayName = (moldChange != null) ? StringUtils.stripToEmpty(emergencyPosition.getName()) : "";
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
*/
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "01032") %><%--금형ECO 상세조회--%></title>
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
.noTitleStuff .ui-dialog-titlebar {display:none}
-->
</style>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<!-- script language=JavaScript src="/plm/jsp/ecm/ViewMoldEco.js"></script -->

<script language="javascript">

//탭전환
function clickTabBtn2(tabId) {
    var tabBasic = document.getElementById("tabBasic");
    var tabActivity = document.getElementById("tabActivity");
    var tabEcn = document.getElementById("tabEcn");
    
    if(tabId == '' || tabId == 'tabBasic') tabId = 1;
    if(tabId == 'tabActivity') tabId = 2;
    if(tabId == 'tabEcn') tabId = 3;
    
    if(tabId == 1) {
        tabBasic.style.display = "block";
        tabActivity.style.display = "none";
        tabEcn.style.display = "none";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "block";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "none";
        var infoEcn = document.getElementById("infoEcn");
        infoEcn.style.display = "none";
        
        tabBasic.style.zIndex = 2;
        tabActivity.style.zIndex = 1;
        tabEcn.style.zIndex = 1;
        
        document.forms[0].tabName.value = 'tabBasic';
        
        <%if(!mold_oid.isEmpty()){ %>
        document.getElementById("searchMoldPlan").style.display = 'none';
        <%}%>
        
    } else if(tabId == 2) {
        tabBasic.style.display = "none";
        tabActivity.style.display = "block";
        tabEcn.style.display = "none";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "none";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "block";
        var infoEcn = document.getElementById("infoEcn");
        infoEcn.style.display = "none";
        
        tabBasic.style.zIndex = 1;
        tabActivity.style.zIndex = 2;
        tabEcn.style.zIndex = 1;
        
        document.forms[0].tabName.value = 'tabActivity';
        
        <%if(!mold_oid.isEmpty()){ %>
                document.getElementById("searchMoldPlan").style.display = 'block';
        <%}%>
        
    } else if(tabId == 3) {
        tabBasic.style.display = "none";
        tabActivity.style.display = "none";
        tabEcn.style.display = "block";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "none";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "none";
        var infoEcn = document.getElementById("infoEcn");
        infoEcn.style.display = "block";
        
        tabBasic.style.zIndex = 1;
        tabActivity.style.zIndex = 1;
        tabEcn.style.zIndex = 2;
        
        document.forms[0].tabName.value = 'tabEcn';

        <%if(!mold_oid.isEmpty()){ %>
        document.getElementById("searchMoldPlan").style.display = 'none';
        <%}%>
                
      }
}

//탭전환
<% /* deprecated */ %>
function clickTabBtn(tabId) {
  var tabBasic = document.getElementById("tabBasic");
  var tabActivity = document.getElementById("tabActivity");
  if(tabId == 1) {
      tabBasic.style.visibility = "visible";
      tabActivity.style.visibility = "hidden";

      var infoShow = document.getElementById("infoShow");
      infoShow.style.display = "block";
      var infoHide = document.getElementById("infoHide");
      infoHide.style.display = "none";
  } else {
      tabBasic.style.visibility = "hidden";
      tabActivity.style.visibility = "visible";

      var infoShow = document.getElementById("infoShow");
      infoShow.style.display = "none";
      var infoHide = document.getElementById("infoHide");
      infoHide.style.display = "block";
  }
}

//프로젝트상세조회 팝업
function viewProjectPopup(oid) {
    /* 
	var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid=" + oid + "&popup=popup";
    openWindow(url, '',1050,800);
    */
    
	openView(oid);
}

function callUpdate(){
    var url = "/plm/servlet/e3ps/MoldEcoServlet";
    document.forms[0].cmd.value = "updateview";
    document.forms[0].target = "_self";
    document.forms[0].action = url;
    document.forms[0].method = "post";
    document.forms[0].submit();
}

//목록
function callSearch(){
//  var url = "/plm/jsp/ecm/SearchEcoForm.jsp";
//  document.forms[0].cmd.value = "search";
//  document.forms[0].target = "contName";
//  document.forms[0].action = url;
//  document.forms[0].method = "post";
//  document.forms[0].submit();

     if( document.forms[0].prePage.value == 'Search' )
     {
        history.back();
    }
    else
    {
        var url = "/plm/jsp/ecm/SearchEcoForm.jsp";
        document.forms[0].cmd.value = "search";
        document.forms[0].target = "contName";
        document.forms[0].action = url;
        document.forms[0].method = "post";
        document.forms[0].submit();
    }
}

//자료 취소
function doCancel(){
    document.forms[0].reset();
}

//자료 취소
function doDelete(){
	
	var hasEcaListSize = <%=hasEcaListSize %>;
	if(hasEcaListSize) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "04135") %><%--모든 후속활동(부품/도면/ECN)을 제거하셔야 합니다.--%>');
        return;
    }

    if(!confirm("삭제 하시겠습니까?")) {
        return;
    }
    disabledAllBtn();
    showProcessing();
    var url = "/plm/servlet/e3ps/MoldEcoServlet";
    document.forms[0].cmd.value = "delete";
    document.forms[0].target = "download";
    document.forms[0].action = url;
    document.forms[0].method = "post";
    document.forms[0].submit();
}

//부품 상세조회 팝업
function viewPart(v_poid){
    var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

function viewRelPart(v_poid){
    var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

//ECR 상세조회 팝업
function viewRelEcr(oid){
    var url = "/plm/servlet/e3ps/MoldEcrServlet?cmd=popupview&oid=" + oid;
    openWindow2(url,"","1024","768","scrollbars=auto,resizable=no,top=200,left=250,center=yes");
}

//제품ECO 상세조회 팝업
function viewRelProdEco(oid){
    var url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=popupview&oid=" + oid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

//금형ECO 상세조회 팝업
function viewRelMoldEco(oid){
    var url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=popupview&oid=" + oid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

function viewRelDoc(oid)
{
    var url = "/plm/jsp/dms/ViewDocumentPop.jsp?oid="+oid+"&isRefresh=N";
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

function viewEpmDocPopup(oid)
{
    var url = "/plm/jsp/edm/ViewEPMDocument.jsp?oid=" + oid;
    openWindow2(url,"","820","1000","toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100");
}

//등록완료
<% /* deprecated */ %>
function doComplete( flag ) {
	alert('deprecated, function doComplete in ViewMoldEcoForm.jsp');

	<%-- 
	flag = <%=hasECA %>;
	
    disabledAllBtn();
    showProcessing();
    var url = "/plm/servlet/e3ps/MoldEcoServlet";
    var form = document.forms[0];

    if( flag )
    {
        form.hasECA.value = 'Y';
    }
    else
    {
        form.hasECA.value = 'N';
    }
    form.cmd.value = "complete";
    form.target = "download";
    form.action = url;
    form.method = "post";
    form.encoding = 'multipart/form-data';
    form.submit();
    --%>
}

//등록완료
function doComplete2()
{
    disabledAllBtn();
    showProcessing();
        
    var url = "/plm/servlet/e3ps/ProdEcoServlet";
    var form = document.forms[0];
    form.cmd.value = "validateBeforeCompleteReg";
    form.target = "download";
    form.action = url;
    form.submit();        
}
function lfn_feedbackAfterValidateBeforeCompleteReg(isPass, message)
{
	var form = document.forms[0];
		
	if(isPass) {
        if(message != '') {         
            lfn_showValidationResult(message);    
            
            hideProcessing();
            enabledAllBtn();
                
            return;            
        } else {
	       
			var url = "/plm/servlet/e3ps/MoldEcoServlet";
			form.hasECA.value = 'Y';
			form.cmd.value = "complete";
			form.target = "download";
			form.action = url;
			form.method = "post";
			form.encoding = 'multipart/form-data';
			form.submit();   
			    
	    }
	} else {
		//alert(message);
        lfn_showValidationResult(message);
        
        hideProcessing();
        enabledAllBtn();
        
	    return;
	}
}

function popupStdPart( docLinkOid, docNo )
{
   var form = document.forms[0];
    var url = "/plm/servlet/e3ps/MoldStdPartServlet?pop=Y&docLinkOid="+docLinkOid+"&docNo="+docNo+"&ecoOid="+form.oid.value+"&ecoNo="+form.ecoId.value;

    var height  = screen.height;
    var width   = screen.width;
    var leftpos = width/2 - 200/2;
    var toppos  = height/2 - 400/2;
    if(leftpos<0) leftpos=0;
    if(toppos<0) toppos=0;

    window.open('', "SEARCHREQ", "status=no,  width=650, height=500, resizable=no, scrollbars=no, statusbar=no, left="+leftpos+", top="+toppos);

    document.forms[0].action= url ;
    document.forms[0].target="SEARCHREQ";
    document.forms[0].method= "post"
    document.forms[0].submit();
}

function doEpmDocExcel()
{
    var url = "/plm/servlet/e3ps/MoldEcoServlet";
    var form = document.forms[0];

    form.cmd.value = "epmExcel";
    form.target = "download";
    form.action = url;
    form.method = "post";
    form.submit();
}

function doBomExcel()
{
    var url = "/plm/servlet/e3ps/MoldEcoServlet";
    var form = document.forms[0];

    form.cmd.value = "bomExcel";
    form.target = "download";
    form.action = url;
    form.method = "post";
    form.submit();
}

function doDocExcel()
{
    var url = "/plm/servlet/e3ps/MoldEcoServlet";
    var form = document.forms[0];

    form.cmd.value = "stdPartExcel";
    form.target = "download";
    form.action = url;
    form.method = "post";
    form.submit();
}

function doCancel()
{
    if( document.forms[0].prePage.value == "Search" )
    {
        var createStartDate = "<%=createStartDate%>";
        var createEndDate = "<%=createEndDate%>";
        var url = "/plm/jsp/ecm/SearchEcoForm.jsp?createStartDate=" + createStartDate + "&createEndDate=" + createEndDate + "&autoSearch=Y";
        document.forms[0].action = url;
        document.forms[0].method="post";
        document.forms[0].submit();
    }
    else
    {
        var url = "/plm/jsp/ecm/SearchEcoForm.jsp";
        document.forms[0].cmd.value = "search";
        document.forms[0].target = "contName";
        document.forms[0].action = url;
        document.forms[0].method = "post";
        document.forms[0].submit();
    }
}

<% /* 활동완료 */ %>
function doActivity()
{
    disabledAllBtn();
    showProcessing();
        
    var url = "/plm/servlet/e3ps/ProdEcoServlet";
    var form = document.forms[0];
    form.cmd.value = "validateBeforeActivityReg";
    form.target = "download";
    form.action = url;
    form.submit();      
    
}
function lfn_feedbackAfterValidateBeforeActivityReg(isPass, message)
{
    var form = document.forms[0];
    
    if(isPass) {
        if(message != '') {        	
        	lfn_showValidationResult(message);    
            
        	hideProcessing();
            enabledAllBtn();
                
            return;            
        } else {
            
            var url = "/plm/servlet/e3ps/MoldEcoServlet";
            var form = document.forms[0];
            form.cmd.value = "ActivityReg";
            form.target = "download";
            form.action = url;
            form.submit();      
            
        }
    } else {
    	//alert(message);
        lfn_showValidationResult(message);
        
        hideProcessing();
        enabledAllBtn();
        
        return;
    }
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
        
    var url = "/plm/servlet/e3ps/MoldEcoServlet";
    var form = document.forms[0];
    form.cmd.value = "ApprovedReg";
    form.target = "download";
    form.action = url;
    form.submit();      
    
}

function doResendERP()
{
    disabledAllBtn();
    showProcessing();
        
    var url = "/plm/servlet/e3ps/MoldEcoServlet";
    var form = document.forms[0];
    form.cmd.value = "ResendERP";
    form.target = "download";
    form.action = url;
    form.submit();      
    
}

</script>

<script language="javascript">
// 결재요청
<% /* deprecated */ %>
function doSanction(){
	alert('deprecated, function doSanction in ViewMoldEcoForm.jsp');
    
	<%-- 
    var form = document.forms[0];

    <%
    boolean budgetFlag = EcmSearchHelper.manager.isSecureBudget( ketMoldChangeOrderVO.getOid() );
    Hashtable<String, String> rtnHash = EcmSearchHelper.manager.getCanReqApproveFlagForMoldEco( ketMoldChangeOrderVO.getKetMoldChangeOrder() );
    if( rtnHash.get("flag").equals("FALSE") )
    {
    %>
        alert('<%=rtnHash.get("msg")%>');
    <%
    }
    else if( !budgetFlag )
    {
    %>
        if( confirm('<%=messageService.getString("e3ps.message.ket_message", "01651") %>비용이 확보되지 않았습니다.\n그래도 진행하시겠습니까?') )
        {
            goPage(form.oid.value);
        }
        else
        {
            return;
        }
    <%
    }
    else
    {
    %>
        goPage(form.oid.value);
    <%
    }
    %>
    --%>
}

// 결재요청
function requestApprove2()
{
    disabledAllBtn();
    showProcessing();
    
    var url = "/plm/servlet/e3ps/ProdEcoServlet";
    var form = document.forms[0];
    form.cmd.value = "validateBeforeRequestApprove";
    form.target = "download";
    form.action = url;
    form.submit();      
    
}
function lfn_feedbackAfterValidateBeforeRequestApprove2(isPass, message)
{
    var form = document.forms[0];
    
    if(isPass) {
        if(message != '') {
        	lfn_showValidationResult(message);
                
            hideProcessing();
            enabledAllBtn();
                
            return;
            
        } else {
            goPage(form.oid.value);
        }
    } else {
        alert(message);
        
        hideProcessing();
        enabledAllBtn();
        
        return;
    }
}
function lfn_showValidationResult(message) {
    /* 
    var obj = $("div[id='divValidationResult']").offset();
    $("div[id='divValidationResult']").css({
           "position" : "absolute",
           "top" : "100px",
           "left" : "200px",
           "z-index" : 100
        });

    $("div[id='divValidationResult']").show();
     */ 
     $( "#divValidationResultContent" ).html(message);
    $( "#divValidationResult" ).dialog({ dialogClass: 'noTitleStuff' , width: 500, height: 300 });
    
}
function lfn_hideValidationResult() {
    $( "#divValidationResult" ).dialog("destroy");
    
}

//양산금형조회 팝업
function goView()
{
    var url = '/plm/servlet/e3ps/MoldPlanServlet?prePage=S&cmd=ViewPopup&oid=<%= mold_oid%>';
    openWindow2(url,"","830","700","toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100");
}

</script>

</head>
<body onload="javascript:clickTabBtn2('<%=ParamUtil.getParameter(request, "tabName")%>');">
<form method="post" action="/plm/servlet/e3ps/MoldEcoServlet">
<input type="hidden" name="cmd" value="view">
<input type="hidden" name="oid" value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(CommonUtil.getOIDString(ketMoldChangeOrderVO.getKetMoldChangeOrder()));}%>">
<input type="hidden" name="ecoId" value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print( ketMoldChangeOrderVO.getKetMoldChangeOrder().getEcoId() );}%>">
<input type="hidden" name="devYn" value="">
<input type="hidden" name="divisionFlag" value="">
<input type="hidden" name="changeReason" value="">
<input type="hidden" name="increaseProdType" value="">
<input type='hidden'  name="prePage"  value='<%=ParamUtil.getParameter(request, "prePage")%>'>
<input type='hidden'  name="hasECA"  >
<input type="hidden" name="tabName" value="<%=ParamUtil.getParameter(request, "tabName")%>">

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
<input type="hidden" name="srchecoId" value="<%=ParamUtil.getParameter(request, "ecoId")%>">
<input type="hidden" name="srchdivisionFlag" value="<%=ParamUtil.getParameter(request, "divisionFlag")%>">
<input type="hidden" name="srchdevYn" value="<%=ParamUtil.getParameter(request, "devYn")%>">
<input type="hidden" name="srchsancStateFlag" value="<%=ParamUtil.getParameter(request, "sancStateFlag")%>">
<input type="hidden" name="srchecoName" value="<%=ParamUtil.getParameter(request, "ecoName")%>">
<input type="hidden" name="srchprodMoldCls" value="<%=ParamUtil.getParameter(request, "prodMoldCls")%>">
<input type="hidden" name="srchcreateStartDate" value="<%=ParamUtil.getParameter(request, "createStartDate")%>">
<input type="hidden" name="srchcreateEndDate" value="<%=ParamUtil.getParameter(request, "createEndDate")%>">

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="<%=(isIframe)?"display:none;":""%>">
        <tr>
            <td background="/plm/portal/images/logo_popupbg.png">
                <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01003") %><%--금형 ECO 상세조회--%></td>
                    <!-- td align="right">
                      <img src="/plm/portal/images/icon_navi.gif">Home
                      <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                      <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00186") %><%--ECO 관리--%>
                      <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01003") %><%--금형 ECO 상세조회--%>
                    </td -->
                </tr>
                </table>
            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
        </table>
        <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="20">&nbsp;</td>
            <td class="font_03">
                <table id="infoShow" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%></td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn2(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04290") %><%--설변부품/도면--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn2(3);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04111") %><%--ECN--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>
                        </tr>
                        </table>
                    </td>                       
                </tr>
                </table>
                <table id="infoHide" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn2(1);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>

                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04290") %><%--설변부품/도면--%></td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn2(3);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04111") %><%--ECN--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>
                        </tr>
                        </table>
                    </td>                       
                </tr>
                </table>
              <table id="infoEcn" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn2(1);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>

                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn2(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04290") %><%--설변부품/도면--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04111") %><%--ECN--%></td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                                       
                </tr>
                </table>                  
            </td>
            <td align="right">
                <table border="0" cellspacing="0" cellpadding="0" style="<%=(isIframe)?"display:none;":""%>">
                <tr>
              <%if(!mold_oid.isEmpty()) {%>
                <td width="5">&nbsp;</td>
                <td id = "searchMoldPlan"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goView();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02093") %><%--양산금형조회--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                  <td width="5">&nbsp;</td>
              <%} %>
              <%if(isOwner || isAdmin) {//로그인 사용자가 작성자인 경우,admin인경우%>
                <%if( isPlanning || isExcuteActivity || isActivityCompleted || isAdmin) {//결재상태가 활동완료 이외인 경우,admin인경우%>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:callUpdate();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <%}//결재상태가 활동완료 이외인 경우%>
                <%if(isPlanning || isExcuteActivity) {//결재상태가 계획수립인 경우%>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doDelete();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
               <%}//결재상태가 계획수립인 경우%>
               <%if(isPlanning) {//결재상태가 계획수립인 경우%>
                   <td width="5">&nbsp;</td>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doComplete2();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01334") %><%--등록완료--%></a></td>
                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table></td>
                <%}//결재상태가 계획수립인 경우%>
                <%if(isActivityCompleted) {//결재상태가 활동완료인 경우%>
                    <td width="5">&nbsp;</td>
                    <td><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:requestApprove2();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table></td>
                      <%
                    }
                  }
               %>
                    <%
                    // SAP I/F가 실패하였을 경우
                    if((isOwner || isAdmin) && "APPROVED".equals(ketMoldChangeOrderVO.getProgressState()) && !isSucessSapInterface) {
                    //if((isOwner || isAdmin) && !isSucessSapInterface) {
                    %>
                    <td width="5">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doResendERP();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "04730") %><%--재전송--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <%
                    }
                    %>                     
                      <!--td width="5">&nbsp;</td>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doCancel();" class="btn_blue" onfocus="this.blur();">취소</a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td-->
                        <%
//                      if("SearchEcoForm".equals(fromPage)) {
//                        if("Y".equals(ParamUtil.getParameter(request, "autoSearch"))) {
                        %>
                        <!-- td width="5">&nbsp;</td>
                        <td><table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doCancel();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01378") %><%--목록--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                          </table></td -->
                          <%
//                          }
                          %>
                    
                    <%
                    // 활동완료
                    if( isOwner && (isTheFirst && "PLANNING".equals(ketMoldChangeOrderVO.getProgressState()))) {
                    %>
                    <td width="5">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doActivity();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "03244") %><%--활동완료--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <%
                    }
                    %>   
                    <%
                    // 승인(디버깅)
                    if(isAdmin && ("UNDERREVIEW".equals(ketMoldChangeOrderVO.getProgressState()) || "APPROVED".equals(ketMoldChangeOrderVO.getProgressState()))) {
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
                    <td width="5">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>                          
              </tr>
            </table></td>
        </tr>
      </table>
      <table width="100%" height="90%" border="0" cellspacing="0" cellpadding="0">
      <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
          <td height="10" background="/plm/portal/images/box_14.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_10.gif"></td>
      </tr>
      <tr>
          <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
          <td valign="top">
          <!-- table width="100%" height="100%" border="0" cellspacing="0" cellpadding="10">
              <tr>
                <td valign="top">&nbsp;</td>
              </tr>
            </table -->
 
            <!--내용-->
            <div style="position:; width:100%; height:670px; overflow-x:auto; overflow-y:auto;">
            <div id="tabBasic" style="position:; display:none; z-index:2; ">

                <%@include file="/jsp/ecm/ViewMoldEcoBasicForm.jsp" %>

            </div>
            <div id="tabActivity" style="position:; display:none; z-index:1; ">
                
                <%@include file="/jsp/ecm/ViewMoldEcoActivityForm.jsp" %>
              
            </div>
            <div id="tabEcn" style="position:; display:none; z-index:1; ">
            
                <%@include file="/jsp/ecm/reform/ViewMoldEcnForm.jsp" %>
             
            </div>  
            </div>             
                        
          </td>
          <td width="10" background="/plm/portal/images/box_15.gif">&nbsp;</td>
        </tr>
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_11.gif"></td>
          <td height="10" background="/plm/portal/images/box_16.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_12.gif"></td>
        </tr>
      </table></td>
</tr>
<!-- tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
</tr -->
</table>
</form>

<div id="divValidationResult" style="display: none;">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" align="right">

      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
          <td class="font_01">유효성 검증</td>
          <td align="right" width="70">        
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:lfn_hideValidationResult();" class="btn_blue">닫기<%--닫기--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
              </tr>
            </table> 
          </td>  
        </tr>   
      <tr>
        <td colspan="3" style="height: 30px;">&nbsp;</td>
      </tr>                       
      <tr>
        <td id="divValidationResultContent" name="divValidationResultContent" colspan="3" valign="top" align="left">여기에 에러 메세지가 들어간다.</td>
      </tr>                       
      </table>

    </td>
  
  </tr>

</table>
</div>

<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</body>
</html>
