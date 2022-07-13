<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "e3ps.common.web.ParamUtil,
                  e3ps.common.util.CommonUtil,
                  e3ps.common.util.DateUtil,
                  e3ps.common.util.StringUtil,
                  e3ps.common.obj.ObjectData,
                  e3ps.common.obj.ObjectUtil,
                  e3ps.common.content.*,
                  e3ps.common.code.NumberCode,
                  e3ps.ews.beans.EWSUtil,
                  e3ps.ews.beans.EWSHelper,
                  e3ps.ews.dao.PartnerDao,
                  e3ps.ews.entity.KETEarlyWarning,
                  e3ps.ews.entity.KETEarlyWarningPlan,
                  e3ps.ews.entity.KETEarlyWarningPlanLink,
                  e3ps.ews.entity.KETEarlyWarningTarget,
                  e3ps.ews.entity.EarlyWarningTargetLink,
                  e3ps.ews.entity.KETEarlyWarningStepLink,
                  e3ps.ews.entity.KETEarlyWarningStep,
                  e3ps.wfm.util.WorkflowSearchHelper,
                  e3ps.groupware.company.People,
                  e3ps.groupware.company.PeopleHelper,
                  wt.content.*,
                  wt.doc.WTDocument,
                  wt.doc.WTDocumentMaster,
                  wt.fc.QueryResult,
                  wt.fc.PersistenceHelper,
                  wt.vc.VersionControlHelper,
                  wt.org.WTUser,
                  wt.session.SessionHelper,
                  java.util.Hashtable,
                  java.util.ArrayList,
                  java.util.Vector,
                  java.util.StringTokenizer"%>

<%
    /********************************     ketEarlyWarningPlan object     ********************************/

    String planOid = ParamUtil.getParameter(request, "planOid");
    KETEarlyWarningPlan ketEarlyWarningPlan = (KETEarlyWarningPlan)CommonUtil.getObject(planOid);
    // 결재대상 화면 여부
    boolean isIframe = Boolean.parseBoolean(StringUtil.checkReplaceStr(request.getParameter("isIframe"), "false"));

    /********************************     ketEarlyWarningPlan object     ********************************/

    /********************************       ketEarlyWarning object       ********************************/

    QueryResult isEarlyWarning = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarningPlan.getMaster(), KETEarlyWarningPlanLink.ROLE_AOBJECT_ROLE, KETEarlyWarningPlanLink.class, false);
    KETEarlyWarningPlanLink ketEarlyWarningPlanLink = null;
  Object earlyWarningMaster = null;
  ObjectData earlyWarninMasterData = null;

  while(isEarlyWarning.hasMoreElements()){
         ketEarlyWarningPlanLink = (KETEarlyWarningPlanLink)isEarlyWarning.nextElement();
        earlyWarningMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEarlyWarningPlanLink.getRoleAObject());
        earlyWarninMasterData = new ObjectData((WTDocument)earlyWarningMaster);
    }

    String oid = earlyWarninMasterData.getOid();
    KETEarlyWarning ketEarlyWarning = (KETEarlyWarning)CommonUtil.getObject(oid);

    /********************************       ketEarlyWarning object       ********************************/

    /********************************       ketEarlyWarningStep object       ********************************/

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

    /********************************       ketEarlyWarningStep object       ********************************/

    String isPopup = ParamUtil.getParameter(request, "isPopup");
    String isTodo = ParamUtil.getParameter(request, "todo");

    WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
    WTUser owner = (WTUser)ketEarlyWarningPlan.getCreator().getPrincipal();
    boolean isOwner = false;
    if( owner.equals( loginUser ) ) {
        isOwner = true;
    }
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="../../portal/js/common.js"></script>
<script language="javascript" src="/plm/jsp/ews/EWSCommon.js"></script>
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 20px;
    margin-right: 10px;
    margin-bottom: 5px;
}
</style>
<script language='javascript'>
<!--

    //초기유동관리 계획서 수정화면 이동
    function goUpdate(cmd){
        var oid = document.forms[0].oid.value;
        var planOid = document.forms[0].planOid.value;

        showProcessing();
      disabledAllBtn();

      window.location= '/plm/jsp/ews/UpdateEarlyWarningPlan.jsp?oid='+oid+'&planOid='+planOid+'&cmd='+cmd+'&todo=<%=isTodo%>';
    }

    //초기유동관리 계획서 삭제
    function goDelete(){
        if(confirm('<%=messageService.getString("e3ps.message.ket_message", "01697") %><%--삭제 하시겠습니까?--%>')){
            showProcessing();
          disabledAllBtn();

          document.forms[0].action = '/plm/servlet/e3ps/EarlyWarningPlanServlet?cmd=delete';
          document.forms[0].encoding = 'multipart/form-data';
          document.forms[0].submit();
      }
    }

//-->
</script>
</head>
<body class="<%=(isIframe)?"": "popup-background02"%>">
<form method="post">

<!-- hidden begin -->
<input type='hidden' name='oid' value=<%=oid%> >
<input type='hidden' name='planOid' value=<%=planOid%> >
<input type='hidden' name='isTodo' value=<%=isTodo%> >
<!-- hidden end -->

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
        <% if (isPopup != null && isPopup.equals("Y")) { %>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none">
      <% }else {%>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
      <% } %>
        <tr>
          <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02812") %><%--초기유동관리 계획서 상세조회--%></td>
                
              </tr>
            </table></td>
        </tr>
       
      </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" <%if(isPopup != null && isPopup.equals("Y"))out.print("style='display:none'");%> >
        <tr>
          <td>&nbsp;</td>
          <td align="right">
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <% String state = ketEarlyWarningPlan.getLifeCycleState().toString(); %>
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
                <%    if ( ewsStep != null && !ewsStep.equals("EWRCANCELED") ){ %>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goPage('<%=planOid%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%    } %>
                <% }else if (state != null && state.equals("APPROVED") && ObjectUtil.isLatestVersion(ketEarlyWarningPlan) && (EWSUtil.isProduction() || EWSUtil.isPurchase() || CommonUtil.isAdmin() || CommonUtil.isMember("Business Administrators"))) { %>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goUpdate('revise');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <% } %>
                <% if( isTodo != null && isTodo.equals("Y")){ %>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <% }else{ %>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td><!-- 기본정보 -->
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goBack();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <% } %>
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
          <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "00946") %><%--관리번호--%></td>
          <td colspan="3" class="tdwhiteL0"><%=ketEarlyWarning.getNumber()%></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td class="tdwhiteL">
              <a href="javascript:viewVersionHistory('<%=planOid%>');"><%=VersionControlHelper.getVersionIdentifier(ketEarlyWarningPlan).getSeries().getValue()%></a>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
          <td class="tdwhiteL0"><a href="javascript:viewHistory('<%=planOid%>');"><%=ketEarlyWarningPlan.getLifeCycleState().getDisplay(messageService.getLocale())%></a></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
          <td class="tdwhiteL"><%=ketEarlyWarningPlan.getCreatorFullName()%></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
          <td class="tdwhiteL0"><%=DateUtil.getTimeFormat(ketEarlyWarningPlan.getCreateTimestamp(),"yyyy-MM-dd")%></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01952") %><%--수정자--%></td>
          <td class="tdwhiteL"><%=ketEarlyWarningPlan.getModifierFullName()%></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
          <td class="tdwhiteL0"><%=DateUtil.getTimeFormat(ketEarlyWarningPlan.getModifyTimestamp(),"yyyy-MM-dd")%></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02000") %><%--승인자--%></td>
                <td class="tdwhiteL">
                    <% if (state != null && !state.equals("INWORK")) {
                              if (WorkflowSearchHelper.manager.getLastApprover(ketEarlyWarningPlan) != null) {%>
                                  <%=WorkflowSearchHelper.manager.getLastApprover(ketEarlyWarningPlan).getFullName()%>
                    <%   }
                       } %>
                    &nbsp;
                </td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
                <td class="tdwhiteL0">
                        <% if (state != null && !state.equals("INWORK")) { %>
                        <%=WorkflowSearchHelper.manager.getLastApprovalDate(ketEarlyWarningPlan)%>
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
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%></td>
          <td colspan="3" class="tdwhiteL0"><%=EWSUtil.ViewInout(ketEarlyWarning)%></td>
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
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00811") %><%--계획서 제출기한--%></td>
          <td class="tdwhiteL0">
              <% if(ketEarlyWarning.getPlanDate() != null){ %>
                  <%=DateUtil.getTimeFormat(ketEarlyWarning.getPlanDate(),"yyyy-MM-dd")%>
              <% } %> &nbsp;
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01381") %><%--목표--%></td>
          <td colspan="3" class="tdwhiteM0">
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="98%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="80" rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                <td width="165" rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                <td width="80" rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00833") %><%--고객불량--%><br>(PPM)</td>
                <td rowspan="2" class="tdgrayM">
                    <% if (ketEarlyWarning.getInOut().equals("i")) { %>
                               <%=messageService.getString("e3ps.message.ket_message", "00883") %><%--공정불량--%>
                          <% }else { %>
                               <%=messageService.getString("e3ps.message.ket_message", "01934") %><%--수입불량--%>
                          <% } %>
                          <br>(PPM)</td>
                <td colspan="4" class="tdgrayM"> <%=messageService.getString("e3ps.message.ket_message", "01785") %><%--생산성(%)--%></td>
              </tr>
              <tr>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02002") %><%--시간가동--%>/td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01905") %><%--성능가동--%></td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02103") %><%--양품율--%></td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02665") %><%--종합효율--%></td>
              </tr>
              <%
                ArrayList partList = EWSHelper.manager.getPartTarget(ketEarlyWarning);
                Hashtable<String, String> part = new Hashtable<String, String>();

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
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03242") %><%--활동멤버--%></td>
          <td colspan="3" class="tdwhiteL0">
              <%
                 if (ketEarlyWarningPlan.getCftMember() != null){
                   String cftMember = null;
                   People people = null;
                   String dept = null;
                   String duty = null;
                   String memberName = null;
                     StringTokenizer st = new StringTokenizer(ketEarlyWarningPlan.getCftMember(), "/");
                       while (st.hasMoreTokens()){
                         cftMember = st.nextToken();
                         people = PeopleHelper.manager.getPeople(((WTUser)CommonUtil.getObject(cftMember)).getName());
                         if(people.getDepartment() == null) {
                              dept = messageService.getString("e3ps.message.ket_message", "03147")/*한국단자*/;
                         }else {
                              dept = people.getDepartment().getName();
                         }
                         duty = people.getDuty();
                         memberName = ((WTUser)CommonUtil.getObject(cftMember)).getFullName();
              %>
                            <%=StringUtil.checkNull(dept) + " / " + StringUtil.checkNull(duty) + " / " + StringUtil.checkNull(memberName)%><BR>
              <%    }
                 }else { %>
                      &nbsp;
              <% } %>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02694") %><%--중점관리 사항--%></td>
          <td colspan="3" style="height:80" class="tdwhitel0">
              <textarea name="textfield10" class="txt_field" id="textfield12" style="width:96%; height:96%" readonly ><%=StringUtil.checkNull(ketEarlyWarningPlan.getManageDesc())%></textarea>
          </td>
        </tr>
                <%
                    ContentHolder holder = ContentHelper.service.getContents((ContentHolder)CommonUtil.getObject(planOid));
                    Vector files = ContentHelper.getContentList(holder);
                %>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00810") %><%--계획서--%></td>
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
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space15"></td>
        </tr>
      </table></td>
  </tr>
    <tr <%if(isPopup != null && isPopup.equals("Y"))out.print("style='display:none'");%> >
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
