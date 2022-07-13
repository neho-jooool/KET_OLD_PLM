<%@ page import="java.util.Hashtable
              ,java.util.ArrayList
              ,java.util.Vector
              ,java.util.List
              ,java.util.Map
              ,java.util.HashMap
              ,e3ps.common.util.StringUtil
              ,e3ps.common.util.DateUtil
              ,e3ps.common.util.CommonUtil
              ,e3ps.common.util.KETObjectUtil
              ,e3ps.common.util.NumberCodeUtil
              ,e3ps.common.code.NumberCode
              ,e3ps.common.code.NumberCodeHelper
              ,e3ps.ecm.beans.ProdEcrBeans
              ,e3ps.ecm.entity.*
              ,e3ps.ecm.entity.KETProdECRIssueLink
              ,e3ps.ecm.entity.KETProdECRPartLink
              ,e3ps.project.ProjectIssue
              ,e3ps.common.content.ContentInfo
              ,e3ps.common.content.ContentUtil
              ,e3ps.groupware.company.Department
              ,wt.org.WTUser
              ,wt.fc.QueryResult
              ,wt.fc.PersistenceHelper
              ,wt.query.*
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
<jsp:useBean id="competent" class="e3ps.ecm.entity.KETCompetentDepartment" scope="request"/>
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
  ArrayList<Hashtable<String, String>> chgTypeList = codeHash.get("changeType");
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
    
  
  // 주간보고
  String pboOid = "";
  String stateDisplayName = "";
  String stateCode = "";
  
  String reviewRequestDate = "";
  
  String subjectDisplayName = "";
  String subjectOid = "";
  String subjectCode = "";
  String chargeDisplayName = "";
  String chargeOid = "";
  String chargeName = "";
  
  String webEditor = "";
  String webEditorText = "";
  
  String reviewDisplayName = "";
  String reviewOid = "";
  String reviewCode = "";
  String meetingCode = "";
  
  // 첨부파일
  ContentHolder holder = null;
  Vector attachFileList = null;
  
  // 권한
  boolean isOwner = false;  // 작성자
  boolean isChief = false;  // 부서장
  boolean isCharge = false; // 담당자
  
  if (PersistenceHelper.isPersistent(competent)) {
      
      pboOid = StringUtils.stripToEmpty(CommonUtil.getOIDString( competent ));
      stateDisplayName = competent.getLifeCycleState().getDisplay(messageService.getLocale());
      stateCode = competent.getLifeCycleState().toString();
      
      // 회신기한
      reviewRequestDate = DateUtil.getTimeFormat(competent.getReplyDeadline(), "yyyy-MM-dd"); 
      
      // 주관부서
      Department subject = competent.getSubject();
      subjectDisplayName = (subject != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(subject.getName())) : "";
      subjectOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(subject));
      subjectCode = StringUtils.stripToEmpty(competent.getSubjectCode());
      
      // 담당자
      WTUser charge = competent.getCharge();
      chargeDisplayName = (charge != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(charge.getFullName())) : "";
      chargeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(charge));
      chargeName = StringUtils.stripToEmpty(competent.getChargeName());
      
      // 검토내용
      webEditor = (String) competent.getWebEditor();
      webEditorText = (String) competent.getWebEditorText();

      // 검토결과
      NumberCode review = competent.getReview();
      reviewDisplayName = (review != null) ? StringUtils.stripToEmpty(review.getName()) : "";
      reviewOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(review));
      reviewCode = StringUtils.stripToEmpty(competent.getReviewCode());

      meetingCode = StringUtils.stripToEmpty(competent.getMeetingCode());
      // 첨부파일
      holder = ContentHelper.service.getContents( (ContentHolder)competent );
      attachFileList = ContentUtil.getSecondaryContents(holder);      
      
      // 권한
      // 작성자
      WTUser owner = (WTUser)competent.getCreator().getPrincipal();
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
      competent = null;
      
	      
	  // 주관부서
	  Department subject = (expansion != null) ? expansion.getSubject() : null;
	  subjectDisplayName = (subject != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(subject.getName())) : "";
	  subjectOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(subject));
	  subjectCode = StringUtils.stripToEmpty(((expansion != null) ? expansion.getSubjectCode() : null));
	  
	  // 담당자
	  WTUser charge = (expansion != null) ? expansion.getCharge() : null;
	  chargeDisplayName = (charge != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(charge.getFullName())) : "";
	  chargeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(charge));
	  chargeName = StringUtils.stripToEmpty(( (expansion != null) ? expansion.getChargeName() : null));
	  
      // 검토요청기한
      reviewRequestDate = (expansion != null) ? DateUtil.getTimeFormat(expansion.getReviewRequestDate(), "yyyy-MM-dd") : ""; 
	 
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
      
      // 기본정보 입력시 주관부서도 담당자도 입력하지 않았을 경우 
      if(subject == null && charge == null) {
		  // 기본정보 작성자에게 권한을 준다.
		  WTUser ecrOwner = (prodEcr != null) ? (WTUser)prodEcr.getCreator().getPrincipal() : null;
		  if(loginUser.equals(ecrOwner)) {
	          isOwner = true;
	      }
      }
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

// 결재 상신 후 호출되는 피드백 function
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
<input type="hidden" name="tabName" value="ecrCompetent">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td height="24px">&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0" style="<%=(isIframe)?"display:none;":""%>">
                <%
                if(ecrStateCode.equals("APPROVED")) {
                    /* 
	                out.println(isOwner);
	                out.println(isChief);
	                out.println(isCharge);
	                out.println(stateCode);
	                */    
	                if( stateCode.equals("") || ((isOwner || isChief || isCharge)  && (stateCode.equals("INWORK") || stateCode.equals("REWORK"))) )
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
                <%
	                }
	                if( (isOwner || isCharge) && (stateCode.equals("INWORK") || stateCode.equals("REWORK")) )
	                {
                %>
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
	                /*
	                if( isChief && (stateCode.equals("") || stateCode.equals("INWORK") || stateCode.equals("REWORK")) )
	                {
	            %>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goReject();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "04101") %><%--기각--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
	            <%   
	                }
	                */
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
      
      
      <div style="width:100%; height:640px; overflow-x:auto; overflow-y:auto;">
          <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
              <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09145") %><%--검토부서--%></td>
              <td width="330" class="tdwhiteL"><%=subjectDisplayName %>&nbsp;</td>
              <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04104") %><%--담당자--%></td>
              <td width="330" class="tdwhiteL0"><%=chargeDisplayName %>&nbsp;</td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04102") %><%--회신기한--%></td>
              <td class="tdwhiteL"><%=reviewRequestDate %>&nbsp;</td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04106") %><%--검토결과--%></td>
              <td class="tdwhiteL0"><%=NumberCodeUtil.getNumberCodeValue("REVIEWRESULT", reviewCode, messageService.getLocale().toString())%>&nbsp;</td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
              <td class="tdwhiteL"><a href="javascript:viewHistory('<%=pboOid %>');"><%=stateDisplayName %></a>&nbsp;</td>
              <td class="tdblueL">회의 필요여부</td>
              <td class="tdwhiteL0"><%=NumberCodeUtil.getNumberCodeValue("MEETINGREQ", meetingCode, messageService.getLocale().toString())%>&nbsp;</td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04105") %><%--검토내용--%></td>
              <td height="400" colspan="3" class="tdwhiteL0">
              
              
                            <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                            <textarea name="webEditor" rows="0" cols="0" style="display: none"><%=webEditor %></textarea> 
                            <textarea name="webEditorText" rows="0" cols="0" style="display: none"><%=webEditorText %></textarea> 
                            <!-- Editor Area Container : Start -->
                            <!-- 이노디터에서 작성된 내용을 보낼 Form End --> 
                            <div id="EDITOR_AREA_CONTAINER"></div> 
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
                  <div id="div_scroll3" style="width:100%;height:81;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
                    <table width="100%" cellpadding="0" cellspacing="0">
                      <!-- tr class="">
                        <td width="40" class="tdgrayM">No</td>
                        <td class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
                      </tr -->
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
	                          String urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
	                          url = "<a href=" + urlpath + " target='_blank'>" + cntInfo.getName() + "</a>&nbsp;(&nbsp;" + cntInfo.getFileSize() + "&nbsp;)";
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
                  <table border="0" cellspacing="0" cellpadding="0" width="655">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table -->
              </td>
            </tr>
          </table>
        
        </div>
        
        
        </td>
  </tr>
</table>
</form>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</body>
</html>
