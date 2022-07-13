<%@page import="e3ps.groupware.company.Department"%>
<%@page import="e3ps.ecm.beans.EcmUtil
                            ,e3ps.ecm.beans.ProdEcoBeans" %>
<%@page import="java.util.Vector
                            ,java.util.Hashtable
                            ,java.util.ArrayList
                            ,wt.content.ContentHolder
                            ,wt.content.ContentHelper
                            ,wt.content.ApplicationData
                            ,wt.org.WTUser
                            ,wt.query.*
                            ,wt.session.SessionHelper" %>
<%@page import="e3ps.common.content.ContentInfo" %>
<%@page import="e3ps.common.content.ContentUtil" %>
<%@page import="wt.content.ContentItem" %>
<%@page import="e3ps.common.util.StringUtil" %>
<%@page import="e3ps.common.util.KETStringUtil" %>
<%@page import="e3ps.common.util.KETObjectUtil" %>
<%@page import="e3ps.common.util.CommonUtil" %>
<%@page import="e3ps.ecm.beans.EcmUtil" %>
<%@page import="e3ps.ecm.beans.EcmSearchHelper" %>
<%@page import="e3ps.ecm.entity.*"%>
<%@page import="ext.ket.dqm.entity.*"%>
<%@page import="e3ps.groupware.company.PeopleData"%>

<%@page import="wt.fc.*"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="prodEco" class="e3ps.ecm.entity.KETProdChangeOrder" scope="request"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:useBean id="ecoHash" class="java.util.Hashtable" scope="request"/>
<jsp:useBean id="prePage" class="java.lang.String" scope="request"/>
<jsp:useBean id="tabName" class="java.lang.String" scope="request"/>
<%
//결재대상 화면 여부
boolean isIframe = Boolean.parseBoolean(StringUtil.checkReplaceStr(request.getParameter("isIframe"), "false"));

String createStartDate = StringUtil.checkNull(request.getParameter("createStartDate"));
String createEndDate = StringUtil.checkNull(request.getParameter("createEndDate"));
ContentHolder holder = ContentHelper.service.getContents( (ContentHolder)prodEco );
Vector attachFileList = ContentUtil.getSecondaryContents(holder);

WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
WTUser owner = (WTUser)prodEco.getCreator().getPrincipal();

String userGroupStr = KETObjectUtil.getCurrentUserGroup();

boolean isOwner = false;
if( owner.equals( loginUser ) )
{
    isOwner = true;
}

boolean isAdmin = CommonUtil.isAdmin();

boolean isModifiable = false;
if( ecoHash.get("status").equals("PLANNING") || ecoHash.get("status").equals("EXCUTEACTIVITY") ||  ecoHash.get("status").equals("ACTIVITYCOMPLETED") || ecoHash.get("status").equals("REWORK") )
{
    isModifiable = true;
}

boolean isPlanningStep = false;
if( ecoHash.get("status").equals("PLANNING") || ecoHash.get("status").equals("REWORK") )
{
    isPlanningStep = true;
}

boolean isAddableActivity = false;
if( ecoHash.get("status").equals("PLANNING") || ecoHash.get("status").equals("EXCUTEACTIVITY") || ecoHash.get("status").equals("REWORK") )
{
    isAddableActivity = true;
}

boolean isActivityCompleted = false;
if( ecoHash.get("status").equals("ACTIVITYCOMPLETED") )
{
    isActivityCompleted = true;
}

boolean isReleased = false;
if( ecoHash.get("status").equals("APPROVED") )
{
    isReleased = true;
}

ArrayList<Hashtable<String, String>> bomList = (ArrayList)ecoHash.get("bomList");
ArrayList<Hashtable<String, String>> parentItemList = (ArrayList)ecoHash.get("parentItemList");
ArrayList<Hashtable<String, String>> epmDocList = (ArrayList)ecoHash.get("epmDocList");
ArrayList<Hashtable<String, String>> docList = (ArrayList)ecoHash.get("docList");

boolean isExistMoldEco = EcmUtil.isExistRelationMoldEco( ecoHash.get("eco_oid").toString() );
String mold_oid = ecoHash.get("mold_oid").toString();

boolean isSucessSapInterface = ((Boolean) ecoHash.get("isSucessSapInterface")).booleanValue();


//초도인지 아닌지
String changeReason = StringUtils.stripToEmpty(prodEco.getChangeReason());
boolean isTheFirst = (changeReason.lastIndexOf("REASON_12") > -1);

// ECA가 있는 지 없는지
boolean hasBomListSize = (bomList != null && bomList.size() > 0);
boolean hasParentItemListSize = (parentItemList != null && parentItemList.size() > 0);
boolean hasEpmDocListSize = (epmDocList != null && epmDocList.size() > 0);
boolean hasDocListSize = (docList != null && docList.size() > 0);

//제품, 금형 ECO 확장팩
/* 
KETChangeRequestExpansion expansion = null;
// ECR 로 찾는다.
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
<title><%=messageService.getString("e3ps.message.ket_message", "02564") %><%--제품ECO상세조회--%></title>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>
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
    /* 
    fnSetBodyStyleValue(1, document.forms[0].hdnBackgroundColor.value, 0);// 배경색, Value, 이노디터 번호
    fnSetBodyStyleValue(2, document.forms[0].hdnBackgroundImage.value, 0);// 배경이미지, Value, 이노디터 번호
    fnSetBodyStyleValue(3, document.forms[0].hdnBackgroundRepeat.value, 0);// 배경이미지 반복옵션, Value, 이노디터 번호
    */
    ////////////////////////////////////////////////////////////////////////////////////////////////////
})
</script>
<script language="javascript">
//처리중 화면 전환
function clickTabBtn(tabId) {
	
	if(tabId == '' || tabId == 'tabBasic') tabId = 1;
	else if(tabId == 'tabActivity') tabId = 2;
	else if(tabId == 'tabEcn') tabId = 3;

    var tabBasic = document.getElementById("tabBasic");
    var tabActivity = document.getElementById("tabActivity");
    var tabEcn = document.getElementById("tabEcn");

    if(tabId == '' || tabId == 1) {
        tabBasic.style.display = "block";
        tabActivity.style.display = "none";
        tabEcn.style.display = "none";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "block";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "none";
        var infoEcn = document.getElementById("infoEcn");
        infoEcn.style.display = "none";

        <%if(!mold_oid.isEmpty()){ %>
        document.getElementById("searchMoldPlan").style.display = 'none';
        <%}%>
        
        document.forms[0].tabName.value = 'tabBasic';
        
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

        <%if(!mold_oid.isEmpty()){ %>
                document.getElementById("searchMoldPlan").style.display = 'block';
        <%}%>
        
        document.forms[0].tabName.value = 'tabActivity';
        
    } else {
        tabBasic.style.display = "none";
        tabActivity.style.display = "none";
        tabEcn.style.display = "block";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "none";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "none";
        var infoEcn = document.getElementById("infoEcn");
        infoEcn.style.display = "block";

        <%if(!mold_oid.isEmpty()){ %>
                document.getElementById("searchMoldPlan").style.display = 'none';
        <%}%>
        
        document.forms[0].tabName.value = 'tabEcn';
        
    }
}

function init() {
	
    var tabName = document.forms[0].tabName.value;
    clickTabBtn(tabName);
    
}

function doCancel()
{
    if( document.forms[0].prePage.value == "S" )
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

function doModify()
{
     <%if( !isActivityCompleted ){%>
     document.forms[0].cmd.value = "ModifyView";
     <%}else{%>
     document.forms[0].cmd.value = "CompleteModifyView";
     <%}%>
     
     document.forms[0].action= '/plm/servlet/e3ps/ProdEcoServlet';
     document.forms[0].target='_self';
     disabledAllBtn();
     showProcessing();
     document.forms[0].submit();
}

// 등록완료
<% /* deprecated */ %>
function doComplete()
{
	alert('deprecated, function doComplete in ViewProdEcoForm.jsp');

	<%-- 
    var epmRows = 0;
    var bomRows = 0;
    <%
     if( epmDocList != null && epmDocList.size() > 0 )
     {
     %>
         epmRows += <%=epmDocList.size()%>;
     <%
     }

     if( bomList != null && bomList.size() > 0 )
     {
    %>
        bomRows += <%=bomList.size()%>;
    <%
     }
     else if( parentItemList != null && parentItemList.size() > 0 )
     {
    %>
        bomRows += <%=parentItemList.size()%>;
    <%
     }
    %>
    
    var isPass = false;
    if( epmRows > 0 || bomRows > 0 )
    {
    	isPass = true;
    }
    else
    {
        
    	//alert("<%=messageService.getString("e3ps.message.ket_message", "00198") %>ECO를 수정하여 도면 또는 BOM 대상을 추가하십시오");
        //return;
        
        // HEENEETODO : 도면 또는 BOM 없이도 ECO를 태울 수 있어야 하지 않을까?
        var message = "<%=messageService.getString("e3ps.message.ket_message", "00198") %>ECO를 수정하여 도면 또는 BOM 대상을 추가하십시오\n"
                    + "<%=messageService.getString("e3ps.message.ket_message", "04260") %>그래도 작업을 계속 진행하시겠습니까?"
                    ;
        if(!confirm(message)) return;

    }
    
    // 저장시 ECN에 대한 유효성을 검사할 필요 없다. ECO 상신시 하면된다.
    isPass = checkDoc();    // ECN 유효성 검사
    if(!isPass)
    {
        return;
    	
    } else  {
        disabledAllBtn();
        showProcessing();
        
        var url = "/plm/servlet/e3ps/ProdEcoServlet";
        var form = document.forms[0];
        form.cmd.value = "CompleteReg";
        form.target = "download";
        form.action = url;
        form.submit();    	
    }
    --%>
}

// 등록완료
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
        	
            var url = "/plm/servlet/e3ps/ProdEcoServlet";
            form.cmd.value = "CompleteReg";
            form.target = "download";
            form.action = url;
            form.submit();   
            
        }
    } else {
        lfn_showValidationResult(message);
        
        hideProcessing();
        enabledAllBtn();
                
        return;
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
            
            var url = "/plm/servlet/e3ps/ProdEcoServlet";
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
        
    var url = "/plm/servlet/e3ps/ProdEcoServlet";
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
        
    var url = "/plm/servlet/e3ps/ProdEcoServlet";
    var form = document.forms[0];
    form.cmd.value = "ResendERP";
    form.target = "download";
    form.action = url;
    form.submit();      
    
}

// 결재요청
<% /* deprecated */ %>
function requestApprove()
{
	alert('deprecated, function requestApprove in ViewProdEcoForm.jsp');
	
	<%-- 
    var form = document.forms[0];

    <%
    boolean budgetFlag = EcmSearchHelper.manager.isSecureBudget(KETObjectUtil.getOidString(prodEco));
    Hashtable<String, String> rtnHash = EcmSearchHelper.manager.getCanRequestApproveFlag( prodEco );
    if( rtnHash.get("flag").equals("FALSE") )
    {
    %>
        //alert('<%=rtnHash.get("msg")%>');
        
        // HEENEETODO : 도면 또는 BOM 없이도 ECO를 태울 수 있어야 하지 않을까?
        var message = "<%=rtnHash.get("msg")%>\n"
                    + "<%=messageService.getString("e3ps.message.ket_message", "04260") %>그래도 작업을 계속 진행하시겠습니까?"
                    ;
        if(!confirm(message)) return;
        else goPage(form.oid.value);
        
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

function initEco()
{
    disabledAllBtn();
    showProcessing();
    
    var url = "/plm/servlet/e3ps/ProdEcoServlet";
    var form = document.forms[0];
    form.cmd.value = "initEco";
    form.target = "download";
    form.action = url;
    form.submit();
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
    var budgetFlag = message;
    
    if(budgetFlag.indexOf("01651") != -1){//비용확보
    	message = message.substring(0,message.lastIndexOf("01651"));
    	$("td[id='divValidationResultLeaf']").show();
    }
    $( "#divValidationResultContent" ).html(message);
	$( "#divValidationResult" ).dialog({ dialogClass: 'noTitleStuff' , width: 500, height: 300 });
	
}
function lfn_hideValidationResult() {
	$( "#divValidationResult" ).dialog("destroy");
    
}

// 프린트
function doPrint() {
    openWindow2("/plm/servlet/e3ps/ProdEcoServlet?prePage=S&cmd=PrintView&oid=<%=KETObjectUtil.getOidString(prodEco) %>","","1024","768","scrollbars=no,resizable=no,top=200,left=250");
	//openWindow2("/plm/jsp/ecm/printProdEcoForm.jsp","","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}


//부품 상세조회 팝업
function viewPart(v_poid){
    var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

//ECR 상세조회 팝업
function viewRelEcr(oid){
    var url = "/plm/servlet/e3ps/ProdEcrServlet?cmd=popupview&oid=" + oid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

//프로젝트상세조회 팝업
function viewProjectPopup(oid) {
    var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid=" + oid + "&popup=popup";
    openWindow(url, '',1050,800);
}

//양산금형조회 팝업
function goView()
{
    var url = '/plm/servlet/e3ps/MoldPlanServlet?prePage=S&cmd=ViewPopup&oid=<%= mold_oid%>';
    openWindow2(url,"","830","700","toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100");
}



function doDelete()
{
	var hasBomListSize = <%=hasBomListSize %>;
	var hasParentItemListSize = <%=hasParentItemListSize %>;
	if(hasBomListSize || hasParentItemListSize) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "04131") %><%--설변부품/도면 탭에서 부품을 제거하셔야 합니다.--%>');
        clickTabBtn(2);
        return;
    }
    var hasEpmDocListSize = <%=hasEpmDocListSize %>;
    if(hasEpmDocListSize) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "04132") %><%--설변부품/도면 탭에서 도면을 제거하셔야 합니다.--%>');
        clickTabBtn(2);
        return;
    }
	var hasDocListSize = <%=hasDocListSize %>;
	if(hasDocListSize) {
	    alert('<%=messageService.getString("e3ps.message.ket_message", "04134") %><%--ECN 탭에서 후속변경대상을 제거하셔야 합니다.--%>');
	    clickTabBtn(3);
	    return;
	}
	
	
    if(confirm("<%=messageService.getString("e3ps.message.ket_message", "01707") %><%--삭제하시겠습니까?--%>")){
        document.forms[0].cmd.value = "Delete";
        document.forms[0].action= '/plm/servlet/e3ps/ProdEcoServlet';
        document.forms[0].target='download';
        disabledAllBtn();
        showProcessing();
        document.forms[0].submit();
    }
}

function viewEpmDocPopup(oid)
{
    var url = "/plm/jsp/edm/ViewEPMDocument.jsp?oid=" + oid;
    openWindow2(url,"","820","1000","toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100");
}

function doCreateMoldEco()
{
      document.forms[0].cmd.value = "CreateMoldEco";
     document.forms[0].action= '/plm/servlet/e3ps/ProdEcoServlet';
     document.forms[0].target='_self';
     document.forms[0].submit();
}

function viewRelDoc(oid)
{
    var url = "/plm/jsp/dms/ViewDocumentPop.jsp?oid="+oid+"&isRefresh=N";
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

//품질문제 상세조회 팝업
function viewRelDqm(v_poid){
  var url = '/plm/ext/dqm/dqmMainForm.do?type=view&oid='+ v_poid;
  getOpenWindow2(url, 'dqmMainFormPopup', 1100, 768);
}

//부품상세조회 팝업에서 그 창이 닫힐때 호출되는 Function
function lfn_feedbackAfterPopupViewPart() {
    try {
	    hideProcessing();
	    enabledAllBtn();
	} catch(e) {}
}

//비용확보 되어있지 않더라도 진행시킨다
function lfn_BudgetCheck(){
	document.forms[0].budgetYn.value = 'Y';
	doComplete2();
}

function fnExcelReport() {

    var tab_text = '<html xmlns:x="urn:schemas-microsoft-com:office:excel">';
    tab_text = tab_text + '<head><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>';
    tab_text = tab_text + '<x:Name>Sheet1</x:Name>';
    tab_text = tab_text + '<x:WorksheetOptions><x:Panes></x:Panes></x:WorksheetOptions></x:ExcelWorksheet>';
    tab_text = tab_text + '</x:ExcelWorksheets></x:ExcelWorkbook></xml></head><body>';
    tab_text = tab_text + "<table border='1px'>";
    var exportTable = $('#bomListExcel').clone();
    exportTable.find('input').each(function (index, elem) { $(elem).remove(); });
    tab_text = tab_text + exportTable.html();
    tab_text = tab_text + '</table></body></html>';
    
    var d = new Date();
    var curr_hour = d.getHours();
    var curr_min = d.getMinutes();
    var curr_sec = d.getSeconds();
    
    var fileName = "bomList"+curr_hour + curr_min + curr_sec  + '.xls';
    var link = document.createElement('a');
    // ie 10+
    try{        
        
        //단순 엑셀 다운로드는 아래와 같이하면 됨. 하지만 암호화를 위해 바이너리 문자열을 자바단으로 넘겨 서버에서 암호화 후 내려받음
        var blob = new Blob([tab_text], { type: "application/vnd.ms-excel;charset=euc-kr" })
        
        if(window.navigator.msSaveOrOpenBlob){
        	
        	window.navigator.msSaveOrOpenBlob(blob, fileName);
            window.saveAs(blob, fileName);
            
        }else if(navigator.userAgent.indexOf("Firefox") !== -1){
            
        	var url = window.URL.createObjectURL(blob);
            link.href = url;
            link.download = fileName;
            document.body.appendChild(link);
            link.click();
            
            setTimeout(function(){
            	document.body.removeChild(link);            
            	window.URL.revokeObjectURL(url);
            },100);
            
         }else{
        	 link.href = window.URL.createObjectURL(blob);
             link.download = fileName;
             link.click();
         }
        
        
        
/*         $("#costExcel").val(tab_text);
        
         var url = '/plm/ext/cost/costExcelCreate.do';
        
        $('[name=ToDoProdEcoForm]').attr('action', url);
        $('[name=ToDoProdEcoForm]').attr('target', 'download');
        $('[name=ToDoProdEcoForm]').submit();     */    
    }
    // ie 9이하
    catch(e){
        var data = document.getElementById('bomList');
        var csvData = [];
        var tmpArr = [];
        var tmpStr = '';
        for (var i = 0; i < data.rows[0].cells.length; i++)
        {
        tmpArr.push((data.rows[0].cells[i].innerText || data.rows[0].cells[i].textContent));
        }
        csvData.push(tmpArr.join('\t'));
        for (var i = 1; i < data.rows.length; i++)
        {
        tmpArr = [];
        for (var j = 0; j < data.rows[0].cells.length; j++)
        {
            tmpArr.push(data.rows[i].cells[j].innerHTML);
        }
        csvData.push(tmpArr.join('\t'));
        }
        var output = csvData.join('\n');
        SaveContents(output);
    }
}
</script>

</head>
<body onload="javascript:init();">
<form  name="ViewProdEcoForm" method="post" action="/plm/servlet/e3ps/ProdEcoServlet" >
<input type="hidden"  name="cmd"  value="View">
<input type= "hidden" name ="oid"  value='<%=KETObjectUtil.getOidString(prodEco) %>' >
<input type="hidden"  name="prePage"  value='<%=request.getParameter("prePage") %>'>
<input type="hidden"  name="tabName"  value='<%=tabName %>'>
<input type="hidden"  name="budgetYn"  value='N'>

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="<%=(isIframe)?"display:none;":""%>">
        <tr>
            <td background="/plm/portal/images/logo_popupbg.png">
                <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02564") %><%--제품ECO상세조회--%></td>
                    <!-- td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00186") %><%--ECO 관리--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00184") %><%--ECO 검색--%>
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
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                <%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%>
                            </td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04290") %><%--설변부품/도면--%></a>
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
                                <a href="javascript:clickTabBtn(3);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04111") %><%--ECN--%></a>
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
                                <a href="javascript:clickTabBtn(1);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>

                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                <%=messageService.getString("e3ps.message.ket_message", "04290") %><%--설변부품/도면--%>
                            </td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(3);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04111") %><%--ECN--%></a>
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
                                <a href="javascript:clickTabBtn(1);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%></a>
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
                                <a href="javascript:clickTabBtn(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04290") %><%--설변부품/도면--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                <%=messageService.getString("e3ps.message.ket_message", "04111") %><%--ECN--%>
                            </td>
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
                    <%if(!mold_oid.isEmpty())
                    {
                    %>
                    <td width="5">&nbsp;</td>
                    <td id="searchMoldPlan">
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goView();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02093") %><%--양산금형조회--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <%
                    }
                    %>
                    <%
                    Department dept = new PeopleData((loginUser)).department;
                    
                    
                    if( (isOwner && isModifiable) || ( dept != null && !dept.getName().equals("연구지원팀") && isAdmin ) )
                    {
                    %>
                    <td width="5">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doModify();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <%
                    }
                    if( isOwner && (isPlanningStep || isAddableActivity))
                    {
                    %>
                    <td width="5">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doDelete();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <%
                        if( isOwner && isPlanningStep) {
                    %>
                    <td width="5">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doComplete2();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01334") %><%--등록완료--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <%
                        }
                        if( isOwner && (isTheFirst && isPlanningStep)) {
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
                    }
                    if( isOwner && isActivityCompleted ) {
                	%>
                	<td width="5">&nbsp;</td>
                	<td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:initEco();" class="btn_blue" onfocus="this.blur();">ECO<%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    
                    <td width="5">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:requestApprove2();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <%
                    }
                    %>
                    <%
                    if( isReleased && (KETObjectUtil.isMember("자동차사업부_금형설계") || KETObjectUtil.isMember("전자사업부_금형설계") || KETObjectUtil.isMember("KETS_금형설계")) ) {
                    %>
                    <td width="5">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doCreateMoldEco();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01004") %><%--금형 ECO 작성--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <%
                    }
                    %>
                    <%
                    // SAP I/F가 실패하였을 경우
                    if((isOwner || isAdmin) && ecoHash.get("status").equals("APPROVED") && !isSucessSapInterface) {
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
                    <!-- td width="5">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doCancel();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01378") %><%--목록--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td -->
                    <%
                    // 승인(디버깅)
                    if(isAdmin && (ecoHash.get("status").equals("UNDERREVIEW") || ecoHash.get("status").equals("APPROVED"))) {
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
                    <%
                    // 프린트
                    if(ecoHash.get("status").equals("UNDERREVIEW") || ecoHash.get("status").equals("APPROVED")) {
                    %>
                    <td width="5">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doPrint();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "03126") %><%--프린트--%></a></td>
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
                </table>
            </td>
        </tr>
      </table>
        
      <table width="100%" height="90%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
          <td height="10" background="/plm/portal/images/box_14.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_10.gif""></td>
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
            <div style="position:; width:100%; height:650px; overflow-x:auto; overflow-y:auto;">
            <div id="tabBasic" style="position:; display:none; z-index:2; ">

                <%@include file="/jsp/ecm/ViewProdEcoBasicForm.jsp" %>

            </div>
            <div id="tabActivity" style="position:; display:none; z-index:1; ">

                <%@include file="/jsp/ecm/ViewProdEcoActivityForm.jsp" %>
              
            </div>
            <div id="tabEcn" style="position:; display:none; z-index:1; ">
            
                <%@include file="/jsp/ecm/reform/ViewProdEcnForm.jsp" %>

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
	  
	  <tr>
        <td colspan="3" style="height: 30px;">&nbsp;</td>
      </tr>  
	  <tr>
        <td id="divValidationResultLeaf" style="display: none;" align="left" width="70">        
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:lfn_BudgetCheck();" class="btn_blue">Yes</a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                <td>&nbsp;</td>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:lfn_hideValidationResult();" class="btn_blue">No</a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
              </tr>
            </table> 
          </td>  
      </tr>
                           
      </table>

    </td>
  
  </tr>

</table>
</div>

<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
<iframe name="setCarData" width="0" height="0" frameborder="0"></iframe>
</body>
</html>
