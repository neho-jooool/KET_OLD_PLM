<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "e3ps.common.util.CommonUtil,
                  e3ps.common.util.DateUtil,
                  e3ps.common.util.StringUtil,
                  e3ps.common.obj.ObjectData,
                  e3ps.common.obj.ObjectUtil,
                  e3ps.common.content.*,
                  e3ps.common.web.ParamUtil,
                  e3ps.common.code.NumberCode,
                  e3ps.common.code.NumberCodeHelper,
                  e3ps.ews.entity.KETEarlyWarning,
                  e3ps.ews.entity.KETEarlyWarningPlan,
                  e3ps.ews.entity.KETEarlyWarningPlanLink,
                  e3ps.ews.entity.KETEarlyWarningResultLink,
                  e3ps.ews.entity.KETEarlyWarningTarget,
                  e3ps.ews.entity.EarlyWarningTargetLink,
                  e3ps.ews.entity.KETEarlyWarningEndReq,
                  e3ps.ews.entity.KETEarlyWarningEnd,
                  e3ps.ews.entity.KETEndReqLink,
                  e3ps.ews.entity.KETEarlyWarningEndReqLink,
                  e3ps.ews.entity.KETEarlyWarningStepLink,
                  e3ps.ews.entity.KETEarlyWarningStep,
                  e3ps.ews.dao.PartnerDao,
                  e3ps.ews.dao.EarlyWarningEndDao,
                  e3ps.ews.beans.EWSUtil,
                  e3ps.ews.beans.EWSHelper,
                  e3ps.wfm.util.WorkflowSearchHelper,
                  e3ps.project.MoldProject,
                  e3ps.project.MoldItemInfo,
                  e3ps.project.ProductProject,
                  e3ps.project.beans.MoldProjectHelper,
                  wt.fc.QueryResult,
                  wt.fc.PersistenceHelper,
                  wt.vc.VersionControlHelper,
                  wt.content.*,
                  wt.org.WTUser,
                  wt.doc.WTDocument,
                  wt.doc.WTDocumentMaster,
                  wt.session.SessionHelper,
                  java.util.Hashtable,
                  java.util.ArrayList,
                                    java.util.Vector"%>

<%
    String oid = ParamUtil.getParameter(request, "oid");
    KETEarlyWarning ketEarlyWarning = (KETEarlyWarning)CommonUtil.getObject(oid);
    // 결재대상 화면 여부
    boolean isIframe = Boolean.parseBoolean(StringUtil.checkReplaceStr(request.getParameter("isIframe"), "false"));

    QueryResult isEarlyWarningStep = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarning.getMaster(), KETEarlyWarningStepLink.ROLE_AOBJECT_ROLE, KETEarlyWarningStepLink.class, false);
    KETEarlyWarningStepLink ketEarlyWarningStepLink = null;
    Object earlyWarningStepMaster = null;
    ObjectData earlyWarninStepMasterData = null;

    while(isEarlyWarningStep.hasMoreElements()){
        ketEarlyWarningStepLink = (KETEarlyWarningStepLink)isEarlyWarningStep.nextElement();
        earlyWarningStepMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEarlyWarningStepLink.getRoleAObject());
        earlyWarninStepMasterData = new ObjectData((WTDocument)earlyWarningStepMaster);
    }

    String stepOid = earlyWarninStepMasterData.getOid();
    KETEarlyWarningStep ketEarlyWarningStep = (KETEarlyWarningStep)CommonUtil.getObject(stepOid);
    String ewsStep = ketEarlyWarningStep.getLifeCycleState().toString();

    String isPopup = ParamUtil.getParameter(request, "isPopup");

    WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
    WTUser owner = (WTUser)ketEarlyWarning.getCreator().getPrincipal();
    boolean isOwner = false;
    if( owner.equals( loginUser ) ) {
        isOwner = true;
    }
    boolean isMainMember = EWSUtil.isMainMember(ketEarlyWarning);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/multicombo.jsp" %>
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="../../portal/js/common.js"></script>
<script language="javascript" src="/plm/jsp/ews/EWSCommon.js"></script>
<style type="text/css">
<!--
body {
    margin-left: 5px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>
<script language='javascript'>
<!--

    // 수정화면으로 이동
    function goUpdate(cmd){
        var oid = document.forms[0].oid.value;

        showProcessing();
      disabledAllBtn();

      window.location= '/plm/jsp/ews/UpdateEarlyWarning.jsp?oid='+oid+'&cmd='+cmd;
    }

    // 초기유동관리 지정 삭제
    function goDelete(){
        if(confirm('<%=messageService.getString("e3ps.message.ket_message", "01697") %><%--삭제 하시겠습니까?--%>')){
            showProcessing();
          disabledAllBtn();
          
          $.ajax({
              type : 'post',
              url : '/plm/servlet/e3ps/EarlyWarningServlet?cmd=delete',
              data : $("#frm").serializefiles(),
              processData : false,
              contentType : false,
              success : function(data) {
                  opener.search();
                  window.close();
              },
              error : function() {
                  alert('삭제에 실패하였습니다.');
                  // 프로그레스바 숨기기
                  hideProcessing();
              }
          });
          

          /* document.forms[0].action = '/plm/servlet/e3ps/EarlyWarningServlet?cmd=delete';
          document.forms[0].encoding = 'multipart/form-data';
          document.forms[0].submit(); */
      }
    }

    // 계획서 등록화면으로 이동
    function goCreatePlan(){
        var oid = document.forms[0].oid.value;

        showProcessing();
        disabledAllBtn();

        window.location = '/plm/jsp/ews/CreateEarlyWarningPlan.jsp?oid='+oid;
    }

    // 계획서 상세조회화면으로 이동
    function goViewPlan(planOid){
        showProcessing();
      disabledAllBtn();

      window.location= '/plm/jsp/ews/ViewEarlyWarningPlan.jsp?planOid='+planOid;
    }

    function goViewPlan2(planOid){
        var url='../../jsp/ews/ViewEarlyWarningPlanPopup.jsp?planOid='+planOid;
        openWindow(url,"","830","600","status=1,scrollbars=no,resizable=no");
    }

    // 실적보고 등록화면으로 이동
    function goCreateResult(){
        var oid = document.forms[0].oid.value;

        showProcessing();
        disabledAllBtn();

        window.location = '/plm/jsp/ews/CreateEarlyWarningResult.jsp?oid='+oid;
    }

    // 실적보고 상세화면으로 이동
    function goViewResult(resultOid){
        showProcessing();
      disabledAllBtn();

      window.location= '/plm/jsp/ews/ViewEarlyWarningResult.jsp?resultOid='+resultOid;
    }

    // 해제신청 등록화면으로 이동
    function goCreateEndReq(){
        var oid = document.forms[0].oid.value;

        showProcessing();
        disabledAllBtn();

        window.location = '/plm/jsp/ews/CreateEarlyWarningEndReq.jsp?oid='+oid;
    }

    // 해제판정 등록화면으로 이동
    function goCreateEnd(){
        var oid = document.forms[0].oid.value;

        showProcessing();
        disabledAllBtn();

        window.location = '/plm/jsp/ews/CreateEarlyWarningEnd.jsp?oid='+oid;
    }

    // 해제 상세화면으로 이동
    function goViewEnd(endReqOid){
        showProcessing();
      disabledAllBtn();

      window.location= '/plm/jsp/ews/ViewEarlyWarningEnd.jsp?endReqOid='+endReqOid;
    }

    //중단화면 이동
  function goStop(){
      var stepOid = document.forms[0].stepOid.value;
      var oid = document.forms[0].oid.value;
      var url='/plm/jsp/ews/StopEarlyWarning.jsp?stepOid='+stepOid+'&oid='+oid;

        openWindow(url,"","500","350","status=1,scrollbars=no,resizable=no");
  }

  function reopen(){
      if(confirm("<%=messageService.getString("e3ps.message.ket_message", "03332") %><%--재게하시겠습니까?--%>")){
          showProcessing();
          disabledAllBtn();

          document.forms[0].action = '/plm/servlet/e3ps/EarlyWarningStepServlet?cmd=reopen';
          document.forms[0].encoding = 'multipart/form-data';
          document.forms[0].submit();
      }
  }

//-->
</script>
</head>
<body class="<%=(isIframe)?"": "popup-background02 popup-space02"%>">
<form method="post" id="frm" name="frm" enctype="multipart/form-data">

<!-- hidden begin -->
<input type='hidden' name='oid' value=<%=oid%> >
<input type='hidden' name='stepOid' value=<%=stepOid%> >
<!-- hidden end -->

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" <%if(isPopup != null && isPopup.equals("Y"))out.print("style='display:none'");%> >
        <tr>
          <td>
              <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02815") %><%--초기유동관리 지정 상세조회--%></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" <%if(isPopup != null && isPopup.equals("Y"))out.print("style='display:none'");%>>
        <tr>
          <td>&nbsp;</td>
          <td align="right">
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                  <% String state = ketEarlyWarning.getLifeCycleState().toString();
                     String version = VersionControlHelper.getVersionIdentifier(ketEarlyWarning).getSeries().getValue();
                  %>
                  <% if (state != null && (state.equals("INWORK") || state.equals("REWORK")) && isOwner) { %>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goUpdate('update');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goDelete();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goPage('<%=oid%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <% }else if (state != null && state.equals("APPROVED") && ObjectUtil.isLatestVersion(ketEarlyWarning) && (EWSUtil.isQuality() || CommonUtil.isAdmin() || CommonUtil.isMember("Business Administrators"))) { %>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goUpdate('revise');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
               <%--    <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %>닫기</a></td>                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td> --%>
                <% }
                   if ( (state != null && state.equals("APPROVED")) || (version != null && !version.equals("0")) ) { %>
                <%
                    QueryResult isPlan = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarning.getMaster(), KETEarlyWarningPlanLink.ROLE_BOBJECT_ROLE, KETEarlyWarningPlanLink.class, false);
                      KETEarlyWarningPlanLink ketEarlyWarningPlanLink = null;
                  Object planMaster = null;
                  ObjectData planMasterData = null;
                  KETEarlyWarningPlan ketEarlyWarningPlan = null;
                  String planOid = null;
                  String planState = null;
                  String planVersion = null;

                  QueryResult isResult = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarning.getMaster(), KETEarlyWarningResultLink.ROLE_BOBJECT_ROLE, KETEarlyWarningResultLink.class, false);
                      KETEarlyWarningResultLink ketEarlyWarningResultLink = null;
                  Object resultMaster = null;
                  ObjectData resultMasterData = null;

                  EarlyWarningEndDao endDao = new EarlyWarningEndDao();
                    String endReqOid = endDao.ViewEndReqId (CommonUtil.getOIDString((WTDocumentMaster)ketEarlyWarning.getMaster()));
                  KETEarlyWarningEndReq ketEarlyWarningEndReq = null;
                  String endReqState = null;

                  QueryResult isEnd = null;
                  KETEndReqLink ketEndReqLink = null;
                  Object endMaster = null;
                  ObjectData endMasterData = null;
                  KETEarlyWarningEnd ketEarlyWarningEnd = null;
                  String endOid = null;
                  String endState = null;

                      if (isPlan != null && isPlan.size() != 0){
                         while(isPlan.hasMoreElements()){
                             ketEarlyWarningPlanLink = (KETEarlyWarningPlanLink)isPlan.nextElement();
                             planMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEarlyWarningPlanLink.getRoleBObject());
                             planMasterData = new ObjectData((WTDocument)planMaster);
                             planOid = planMasterData.getOid();
                             ketEarlyWarningPlan = (KETEarlyWarningPlan)CommonUtil.getObject(planOid);
                             planState = ketEarlyWarningPlan.getLifeCycleState().toString();
                             planVersion = VersionControlHelper.getVersionIdentifier(ketEarlyWarningPlan).getSeries().getValue();
                         }
                  %>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goViewPlan('<%=planOid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00810") %><%--계획서--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%
                        if (isResult != null && isResult.size() != 0){
                             while(isResult.hasMoreElements()){
                                 ketEarlyWarningResultLink = (KETEarlyWarningResultLink)isResult.nextElement();
                                 resultMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEarlyWarningResultLink.getRoleBObject());
                                 resultMasterData = new ObjectData((WTDocument)resultMaster);
                             }
                %>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goViewResult('<%=resultMasterData.getOid()%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02036") %><%--실적보고--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%
                             if (endReqOid != null && !endReqOid.equals("")){
                                 ketEarlyWarningEndReq = (KETEarlyWarningEndReq)CommonUtil.getObject(endReqOid);
                                 endReqState = ketEarlyWarningEndReq.getLifeCycleState().toString();
                %>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goViewEnd('<%=endReqOid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03191") %><%--해제--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%
                                 isEnd = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarningEndReq.getMaster(), KETEndReqLink.ROLE_BOBJECT_ROLE, KETEndReqLink.class, false);
                                 if (isEnd != null && isEnd.size() != 0){
                                     while(isEnd.hasMoreElements()){
                                         ketEndReqLink = (KETEndReqLink)isEnd.nextElement();
                                         endMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEndReqLink.getRoleBObject());
                                         endMasterData = new ObjectData((WTDocument)endMaster);
                                         endOid = endMasterData.getOid();
                                         ketEarlyWarningEnd = (KETEarlyWarningEnd)CommonUtil.getObject(endOid);
                                         endState = ketEarlyWarningEnd.getLifeCycleState().toString();
                                     }
                                 }
                                 if ((isEnd == null || isEnd.size() == 0) && ( ewsStep != null && ewsStep.equals("EWRDECISION")) && (EWSUtil.isQuality() || CommonUtil.isAdmin() || CommonUtil.isMember("Business Administrators")) ) {
                    %>
                    <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goCreateEnd();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03198") %><%--해제판정--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                    <%
                                 }
                                 if (isEnd != null && isEnd.size() != 0 && endState != null && endState.equals("APPROVED") && ketEarlyWarningEnd.getEndresult().equals("N")
                                     && ((ketEarlyWarning.getInOut().equals("i") && EWSUtil.isProduction())
                                         || (ketEarlyWarning.getInOut().equals("o") && EWSUtil.isPurchase())
                                         || EWSUtil.isQuality() || CommonUtil.isAdmin() || CommonUtil.isMember("Business Administrators")) ) {
                    %>
                    <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goCreateEndReq();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03192") %><%--해제신청--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                    <%
                            }
                             }else {
                                 if ( ewsStep != null && !ewsStep.equals("EWRCANCELED")
                                      && ((ketEarlyWarning.getInOut().equals("i") && EWSUtil.isProduction())
                                          || (ketEarlyWarning.getInOut().equals("o") && EWSUtil.isPurchase())
                                          || EWSUtil.isQuality() || CommonUtil.isAdmin() || CommonUtil.isMember("Business Administrators")) ){
                %>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goCreateEndReq();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03192") %><%--해제신청--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%
                        }
                            }
                        }else if ((isResult == null || isResult.size() == 0)
                                     && ( ewsStep != null && ewsStep.equals("EWRREPORT"))
                                     && ( (ketEarlyWarning.getInOut().equals("i") && EWSUtil.isProduction())
                                           || (ketEarlyWarning.getInOut().equals("o") && EWSUtil.isPurchase())
                                           || EWSUtil.isQuality() || CommonUtil.isAdmin() || CommonUtil.isMember("Business Administrators")) ) {
                %>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goCreateResult();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02037") %><%--실적보고등록--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%
                        }
                  }
                  if ((isPlan == null || isPlan.size() == 0)
                       && (ewsStep != null && ewsStep.equals("PLANNING"))
                       && ( (ketEarlyWarning.getInOut().equals("i") && EWSUtil.isProduction())
                             || (ketEarlyWarning.getInOut().equals("o") && EWSUtil.isPurchase())
                             || CommonUtil.isAdmin() || CommonUtil.isMember("Business Administrators")) ) {
                %>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goCreatePlan();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00814") %><%--계획서등록--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%}%>
                <% if ( isMainMember && ewsStep != null
                        && ((ewsStep.equals("PLANNING") && ((isPlan == null || isPlan.size() == 0) || (planState != null && (planState.equals("INWORK")|| planState.equals("REWORK")))))
                            || (ewsStep.equals("EWRREPORT"))
                            || (ewsStep.equals("EWRREQUEST") && (endReqState != null && (endReqState.equals("INWORK")|| endReqState.equals("REWORK"))))
                            || (ewsStep.equals("EWRDECISION") && (endState != null && (endState.equals("INWORK")|| endState.equals("REWORK")))))) {%>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goStop();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02688") %><%--중단--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <% } %>
                <% if ( isMainMember && ewsStep != null && ewsStep.equals("EWRCANCELED")){ %>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:reopen();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02444") %><%--재개--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <% } %>
                <%} %>

                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:try{opener.search();}catch(error){ }window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%-- 닫기--%></a></td>
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
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00946") %><%--관리번호--%></td>
          <td colspan="3" class="tdwhiteL0"><%=ketEarlyWarning.getNumber()%></td>
        </tr>
        <tr>
          <td Class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td class="tdwhiteL">
              <a href="javascript:viewVersionHistory('<%=oid%>');"><%=VersionControlHelper.getVersionIdentifier(ketEarlyWarning).getSeries().getValue()%></a>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
          <td class="tdwhiteL0"><a href="javascript:viewHistory('<%=oid%>');"><%=ketEarlyWarning.getLifeCycleState().getDisplay(messageService.getLocale())%></a></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
          <td class="tdwhiteL"><%=ketEarlyWarning.getCreatorFullName()%></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
          <td class="tdwhiteL0"><%=DateUtil.getTimeFormat(ketEarlyWarning.getCreateTimestamp(),"yyyy-MM-dd")%></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01952") %><%--수정자--%></td>
          <td class="tdwhiteL"><%=ketEarlyWarning.getModifierFullName()%></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
          <td class="tdwhiteL0"><%=DateUtil.getTimeFormat(ketEarlyWarning.getModifyTimestamp(),"yyyy-MM-dd")%></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02000") %><%--승인자--%></td>
                <td class="tdwhiteL">
                    <% if (state != null && !state.equals("INWORK")) {
                              if (WorkflowSearchHelper.manager.getLastApprover(ketEarlyWarning) != null) {%>
                                  <%=WorkflowSearchHelper.manager.getLastApprover(ketEarlyWarning).getFullName()%>
                    <%   }
                       } %>
                    &nbsp;
                </td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
                <td class="tdwhiteL0">
                        <% if (state != null && !state.equals("INWORK")) { %>
                        <%=WorkflowSearchHelper.manager.getLastApprovalDate(ketEarlyWarning)%>
                    <% } %>
                    &nbsp;
                  </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
          <td class="tdwhiteL">
              <a href="javascript:viewProjectPopup('<%=EWSUtil.ViewPjtOid(ketEarlyWarning.getPjtNo())%>');"><%=ketEarlyWarning.getPjtNo()%></a>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
          <td class="tdwhiteL0">
              <a href="javascript:viewProjectPopup('<%=EWSUtil.ViewPjtOid(ketEarlyWarning.getPjtNo())%>');"><%=EWSUtil.ViewPjtName(ketEarlyWarning.getPjtNo())%></a>&nbsp;
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02557") %><%--제품/부품--%></td>
          <td colspan="3" class="tdwhiteL0">
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="98%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                <td class="tdgrayM">Die No</td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%></td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02533") %><%--제작처--%></td>
              </tr>
              <%
                ArrayList partList = EWSHelper.manager.getPartTarget(ketEarlyWarning);
                Hashtable<String, String> part = new Hashtable<String, String>();

                                for ( int inx = 0; inx < partList.size() ; inx++ ) {
                                    part = (Hashtable)partList.get(inx);
              %>
                        <tr>
                        <td class="tdwhiteM"><%=(String)part.get("partNo")%></td>
                        <td class="tdwhiteM"><%=(String)part.get("partName")%></td>
                        <td class="tdwhiteM"><%=(String)part.get("dieNo")%></td>
                        <td class="tdwhiteM"><%=(String)part.get("type")%></td>
                                <td class="tdwhiteM"><%=(String)part.get("proTeamName")%></td>
                        <td class="tdwhiteM"><%=(String)part.get("dieProTeam")%></td>
                      </tr>
              <%}%>
            </table>
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01975") %><%--수행담당자(정)--%></td>
          <td class="tdwhiteL">
              <%=((WTUser)CommonUtil.getObject(ketEarlyWarning.getFstCharge())).getFullName()%>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01974") %><%--수행담당자(부)--%></td>
          <td class="tdwhiteL0">
              <% if (ketEarlyWarning.getSndCharge() != null) { %>
                  <%=((WTUser)CommonUtil.getObject(StringUtil.checkNull(ketEarlyWarning.getSndCharge()))).getFullName()%>
              <% } %> &nbsp;
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03238") %><%--활동기간--%></td>
          <td class="tdwhiteL">
              <%=DateUtil.getTimeFormat(ketEarlyWarning.getStartDate(),"yyyy-MM-dd")%>&nbsp;~&nbsp;
              <%=DateUtil.getTimeFormat(ketEarlyWarning.getEndDate(),"yyyy-MM-dd")%>&nbsp;
              <%
                 String startDate = DateUtil.getTimeFormat(ketEarlyWarning.getStartDate(), "yyyyMMdd");
                 String endDate = DateUtil.getTimeFormat(ketEarlyWarning.getEndDate(), "yyyyMMdd");
              %>
              (<%=messageService.getString("e3ps.message.ket_message", "00138", DateUtil.getDaysFromTo(endDate,startDate)) %><%--{0}일--%>)
              <%
                   String stepEndDate = DateUtil.getTimeFormat(ketEarlyWarningStep.getEndDate(), "yyyyMMdd");
                   if ( !endDate.equals(stepEndDate) ){
            %>
              <br> [<%=messageService.getString("e3ps.message.ket_message", "02132") %><%--연장일--%> : <%=DateUtil.getTimeFormat(ketEarlyWarningStep.getEndDate(), "yyyy-MM-dd")%>]
            <% } %>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00811") %><%--계획서 제출기한--%></td>
          <td class="tdwhiteL0">
              <% if(ketEarlyWarning.getPlanDate() != null){ %>
                  <%=DateUtil.getTimeFormat(ketEarlyWarning.getPlanDate(),"yyyy-MM-dd")%>
              <% } %> &nbsp;
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%></td>
          <td colspan="3" class="tdwhiteL0"><%=EWSUtil.ViewInout(ketEarlyWarning)%></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01381") %><%--목표--%></td>
          <td colspan="3" class="tdwhiteL0">
              <table border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="98%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                <td rowspan="2" class="tdgrayM" style="width:180px"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                <td rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00833") %><%--고객불량--%><br>(PPM)</td>
                <td rowspan="2" class="tdgrayM">
                    <% if (ketEarlyWarning.getInOut().equals("i")) { %>
                         <%=messageService.getString("e3ps.message.ket_message", "00883") %><%--공정불량--%>
                          <% }else { %>
                               <%=messageService.getString("e3ps.message.ket_message", "01934") %><%--수입불량--%>
                          <% } %>
                    <br>(PPM)</td>
                <td colspan="4" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01785") %><%--생산성(%)--%></td>
              </tr>
              <tr>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02002") %><%--시간가동--%></td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01905") %><%--성능가동--%></td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02103") %><%--양품율--%></td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02665") %><%--종합효율--%></td>
              </tr>
              <%
                                for ( int inx = 0; inx < partList.size() ; inx++ ) {
                                    part = (Hashtable)partList.get(inx);
              %>
                      <tr>
                        <td class="tdwhiteM"><%=(String)part.get("partNo")%></td>
                        <td class="tdwhiteR"><%=(String)part.get("partName")%></td>
                        <td class="tdwhiteR" style="word-break:break-all;"><%=(String)part.get("cusError")%></td>
                        <td class="tdwhiteR" style="word-break:break-all;"><%=(String)part.get("workError")%></td>
                        <td class="tdwhiteR"><%=(String)part.get("facility")%></td>
                        <td class="tdwhiteR"><%=(String)part.get("perform")%></td>
                        <td class="tdwhiteR"><%=(String)part.get("good")%></td>
                        <td class="tdwhiteR"><%=(String)part.get("targetTotal")%></td>
                      </tr>
              <%}%>
            </table>
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table></td>
        </tr>
                <%
                    ContentHolder holder = ContentHelper.service.getContents((ContentHolder)CommonUtil.getObject(oid));
                    Vector files = ContentHelper.getContentList(holder);
                %>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01439") %><%--문제점 List--%></td>
          <td colspan="3" class="tdwhiteL0">
              <%
                  if (files != null && files.size() > 0){
                      for (int inx = 0 ; inx < files.size() ; inx++){
                          ApplicationData appData = (ApplicationData)files.get(inx);
                          String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
                          //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                          String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
              %>
                          <a href="<%=url%>" target="_blank"><%=appData.getFileName()%></a><%if(inx<files.size()){%><br><%} %>
              <%  }
                }else if(files == null || files.size() < 1){
              %>
                       &nbsp;
            <%}%>
          </td>
        </tr>
        <% if ( ewsStep != null && ewsStep.equals("EWRCANCELED")){ %>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02691") %><%--중단일자--%></td>
          <td colspan="3" class="tdwhiteL0"><%=DateUtil.getTimeFormat(ketEarlyWarningStep.getStopdate(),"yyyy-MM-dd")%></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02689") %><%--중단 사유--%></td>
          <td colspan="3" style="height:80" class="tdwhiteL0">
              <textarea name="textfield10" class="txt_field" id="textfield12" style="width:80%; height:96%" readonly ><%=StringUtil.checkNull(ketEarlyWarningStep.getReason())%></textarea>
          </td>
        </tr>
        <% } %>
      </table>
    </td>
  </tr>
    <tr <%if(isPopup != null && isPopup.equals("Y"))out.print("style='display:none'");%>>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
<form method="post"  name="SearchListForm">
<input type="hidden" name="page" value="">
<input type="hidden" name="ewsno" value="">
<input type="hidden" name="pjtno" value="">
<input type="hidden" name="partno" value="">
<input type="hidden" name="partname" value="">
<input type="hidden" name="inout" value="">
<input type="hidden" name="production" value="">
<input type="hidden" name="fstcharge" value="">
<input type="hidden" name="step" value="">
<input type="hidden" name="creator" value="">
<input type="hidden" name="createdate" value="">
</form>
</body>
</html>
