<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "wt.fc.PersistenceHelper,
                                    wt.fc.QueryResult,
                                    wt.org.WTUser,
                                    wt.session.SessionHelper,
                                    wt.doc.WTDocument,
                  wt.doc.WTDocumentMaster,
                  wt.content.*,
                  e3ps.common.web.ParamUtil,
                                    e3ps.common.util.DateUtil,
                                    e3ps.common.util.CommonUtil,
                                    e3ps.common.util.StringUtil,
                                    e3ps.common.obj.ObjectData,
                                    e3ps.common.obj.ObjectUtil,
                                    e3ps.common.content.*,
                                    e3ps.common.code.NumberCode,
                                    e3ps.ews.beans.EWSUtil,
                                    e3ps.ews.beans.EWSHelper,
                                    e3ps.ews.dao.PartnerDao,
                                    e3ps.ews.entity.KETEarlyWarning,
                                    e3ps.ews.entity.KETEarlyWarningTarget,
                                    e3ps.ews.entity.EarlyWarningTargetLink,
                                    e3ps.ews.entity.KETEarlyWarningPlanLink,
                                    e3ps.ews.entity.KETEarlyWarningPlan,
                                    e3ps.ews.entity.KETEarlyWarningResultLink,
                                    e3ps.ews.entity.KETEarlyWarningEndReqLink,
                                    e3ps.ews.entity.KETEarlyWarningEndReq,
                                    e3ps.ews.entity.KETEarlyWarningStepLink,
                  e3ps.ews.entity.KETEarlyWarningStep,
                                    e3ps.groupware.company.People,
                  e3ps.groupware.company.PeopleHelper,
                                    e3ps.wfm.util.WorkflowSearchHelper,
                                    java.util.StringTokenizer,
                                    java.util.Vector,
                                    java.util.Hashtable,
                                    java.util.ArrayList" %>

<%
    /********************************    ketEarlyWarningEndReq object    ********************************/

    String endReqOid = ParamUtil.getParameter(request, "endReqOid");
    KETEarlyWarningEndReq ketEarlyWarningEndReq = (KETEarlyWarningEndReq)CommonUtil.getObject(endReqOid);

    /********************************    ketEarlyWarningEndReq object    ********************************/

    /********************************       ketEarlyWarning object       ********************************/

    QueryResult isEarlyWarning = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarningEndReq.getMaster(), KETEarlyWarningEndReqLink.ROLE_AOBJECT_ROLE, KETEarlyWarningEndReqLink.class, false);
    KETEarlyWarningEndReqLink ketEarlyWarningEndReqLink = null;
  Object earlyWarningMaster = null;
  ObjectData earlyWarninMasterData = null;

  while(isEarlyWarning.hasMoreElements()){
         ketEarlyWarningEndReqLink = (KETEarlyWarningEndReqLink)isEarlyWarning.nextElement();
        earlyWarningMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEarlyWarningEndReqLink.getRoleAObject());
        earlyWarninMasterData = new ObjectData((WTDocument)earlyWarningMaster);
    }

    String oid = earlyWarninMasterData.getOid();
    KETEarlyWarning ketEarlyWarning = (KETEarlyWarning)CommonUtil.getObject(oid);

    /********************************       ketEarlyWarning object       ********************************/

    /********************************     ketEarlyWarningPlan object     ********************************/

    QueryResult isPlan = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarning.getMaster(), KETEarlyWarningPlanLink.ROLE_BOBJECT_ROLE, KETEarlyWarningPlanLink.class, false);
    KETEarlyWarningPlanLink ketEarlyWarningPlanLink = null;
  Object planMaster = null;
  ObjectData planMasterData = null;

  while(isPlan.hasMoreElements()){
         ketEarlyWarningPlanLink = (KETEarlyWarningPlanLink)isPlan.nextElement();
        planMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEarlyWarningPlanLink.getRoleBObject());
        planMasterData = new ObjectData((WTDocument)planMaster);
    }

    String planOid = planMasterData.getOid();
    KETEarlyWarningPlan ketEarlyWarningPlan = (KETEarlyWarningPlan)CommonUtil.getObject(planOid);

    /********************************     ketEarlyWarningPlan object     ********************************/

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

    String isEnd = ParamUtil.getParameter(request, "isEnd");
    String isPopup = ParamUtil.getParameter(request, "isPopup");
    String isTodo = ParamUtil.getParameter(request, "todo");

    WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
    WTUser owner = (WTUser)ketEarlyWarningEndReq.getCreator().getPrincipal();
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
<!--
body {
    margin-left: 5px;
    margin-top: 0px;
    margin-right: 5px;
    margin-bottom: 0px;
}
-->
</style>
<script type="text/javascript">
<!--

  //해제신청서 수정화면 이동
    function goUpdate(){
        var endReqOid = document.forms[0].endReqOid.value;

        showProcessing();
      disabledAllBtn();

      parent.location= '/plm/jsp/ews/UpdateEarlyWarningEndReq.jsp?endReqOid='+endReqOid+'&todo=<%=isTodo%>';
    }

    //해제신청서 삭제
    function goDelete(){
        if(confirm('<%=messageService.getString("e3ps.message.ket_message", "01697") %><%--삭제 하시겠습니까?--%>')){
            showProcessing();
          disabledAllBtn();

          document.forms[0].action = '/plm/servlet/e3ps/EarlyWarningEndReqServlet?cmd=delete';
          document.forms[0].encoding = 'multipart/form-data';
          document.forms[0].submit();
      }
    }

//-->
</script>
</head>
<body>
<form method="post">

<!-- hidden begin -->
<input type='hidden' name='oid' value=<%=oid%> >
<input type='hidden' name='endReqOid' value=<%=endReqOid%> >
<input type='hidden' name='isTodo' value=<%=isTodo%> >
<!-- hidden end -->

<% String state = ketEarlyWarningEndReq.getLifeCycleState().toString();
   if (state != null && (state.equals("INWORK") || state.equals("REWORK")) && isOwner) { %>
<table width="100%" border="0" cellspacing="0" cellpadding="0" <%if(isPopup != null && isPopup.equals("Y"))out.print("style='display:none'");%> >
  <tr>
    <td align="right">
        <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goUpdate();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
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
                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goPage('<%=endReqOid%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청--%></a></td>
                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
              </tr>
            </table>
          </td>
          <%    } %>
        </tr>
      </table>
    </td>
  </tr>
</table>
<%  }  %>
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
    <td width="110px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00946") %><%--관리번호--%></td>
    <td width="270" class="tdwhiteL"><%=ketEarlyWarning.getNumber()%></td>
    <td width="80" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
    <td width="280" class="tdwhiteL0"><a href="javascript:viewHistory('<%=endReqOid%>');"><%=ketEarlyWarningEndReq.getLifeCycleState().getDisplay(messageService.getLocale())%></a></td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
    <td class="tdwhiteL"><%=ketEarlyWarningEndReq.getCreatorFullName()%></td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
    <td class="tdwhiteL0"><%=DateUtil.getTimeFormat(ketEarlyWarningEndReq.getCreateTimestamp(),"yyyy-MM-dd")%></td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01952") %><%--수정자--%></td>
    <td class="tdwhiteL"><%=ketEarlyWarningEndReq.getModifierFullName()%></td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
    <td class="tdwhiteL0"><%=DateUtil.getTimeFormat(ketEarlyWarningEndReq.getModifyTimestamp(),"yyyy-MM-dd")%></td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02000") %><%--승인자--%></td>
    <td class="tdwhiteL">
        <% if (state != null && !state.equals("INWORK")) {
                  if (WorkflowSearchHelper.manager.getLastApprover(ketEarlyWarningEndReq) != null) {%>
                      <%=WorkflowSearchHelper.manager.getLastApprover(ketEarlyWarningEndReq).getFullName()%>
        <%   }
           } %>
        &nbsp;
    </td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
    <td class="tdwhiteL0">
            <% if (state != null && !state.equals("INWORK")) { %>
            <%=WorkflowSearchHelper.manager.getLastApprovalDate(ketEarlyWarningEndReq)%>
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
    <td class="tdwhiteL"><%=EWSUtil.ViewInout(ketEarlyWarning)%></td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03238") %><%--활동기간--%></td>
    <td class="tdwhiteL0">
        <%=DateUtil.getTimeFormat(ketEarlyWarning.getStartDate(),"yyyy-MM-dd")%>&nbsp;~&nbsp;
        <%=DateUtil.getTimeFormat(ketEarlyWarning.getEndDate(),"yyyy-MM-dd")%>&nbsp;
        <%
             String startDate = DateUtil.getTimeFormat(ketEarlyWarning.getStartDate(), "yyyyMMdd");
           String endDate = DateUtil.getTimeFormat(ketEarlyWarning.getEndDate(), "yyyyMMdd");
        %>
        (<%=messageService.getString("e3ps.message.ket_message", "00138", DateUtil.getDaysFromTo(endDate,startDate)) %><%--{0}일--%>)
    </td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01975") %><%--수행담당자(정)--%></td>
    <td class="tdwhiteL">
        <%=((WTUser)CommonUtil.getObject(ketEarlyWarning.getFstCharge())).getFullName()%>
    </td>
    <td rowspan="2" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03242") %><%--활동멤버--%></td>
    <td rowspan="2" class="tdwhiteL0">
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
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01974") %><%--수행담당자(부)--%></td>
    <td class="tdwhiteL">
        <% if (ketEarlyWarning.getSndCharge() != null) { %>
            <%=((WTUser)CommonUtil.getObject(StringUtil.checkNull(ketEarlyWarning.getSndCharge()))).getFullName()%>
        <% } %> &nbsp;
    </td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01386") %><%--목표/실적--%></td><!--  목표/실적 -->
    <td colspan="3" class="tdwhitel0">
        <table border="0" cellspacing="0" cellpadding="0" width="600">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table width="98%" cellpadding="0" cellspacing="0" class="table_border">
        <tr>
          <td width="70" rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
          <td width="125" rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
          <td width="30" rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td width="30" rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02224") %><%--월--%></td>
          <td width="70" rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00833") %><%--고객불량--%><br>(PPM)</td>
          <td width="70" rowspan="2" class="tdgrayM">
              <% if (ketEarlyWarning.getInOut().equals("i")) { %>
                   <%=messageService.getString("e3ps.message.ket_message", "00883") %><%--공정불량--%>
              <% }else { %>
                   <%=messageService.getString("e3ps.message.ket_message", "01934") %><%--수입불량--%>
              <% } %>
              <br>(PPM)</td>
          <td colspan="4" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01785") %><%--생산성(%)--%></td>
        </tr>
        <tr>
          <td width="60" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02002") %><%--시간가동--%></td>
          <td width="60" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01905") %><%--성능가동--%></td>
          <td width="60" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02103") %><%--양품율--%></td>
          <td width="60" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02665") %><%--종합효율--%></td>
        </tr>
        <%
            ArrayList partList = EWSHelper.manager.getPartTarget(ketEarlyWarning);
          Hashtable<String, String> part = new Hashtable<String, String>();

            Hashtable<String, String> param = null;
            ArrayList resultList = null;
            Hashtable<String, String> partResult = new Hashtable<String, String>();

            for ( int inx = 0; inx < partList.size() ; inx++ ) {
                        part = (Hashtable)partList.get(inx);

                        param = new Hashtable<String, String>();
                param.put("startDate", DateUtil.getTimeFormat(ketEarlyWarning.getStartDate(),"yyyyMM"));
                param.put("endDate", DateUtil.getTimeFormat(ketEarlyWarning.getEndDate(),"yyyyMM"));
                param.put("partNo", StringUtil.checkNull((String)part.get("partNo")));
                param.put("partName", StringUtil.checkNull((String)part.get("partName")));
                param.put("inout", StringUtil.checkNull(ketEarlyWarning.getInOut()));

            resultList = new ArrayList<Hashtable<String, String>>();
                        resultList = EWSHelper.manager.getPartResult(param);
        %>
                <tr>
              <td class="tdwhiteM" rowspan=<%=resultList.size()+1%> ><%=(String)part.get("partNo")%></td>
              <td class="tdwhiteM" rowspan=<%=resultList.size()+1%> ><%=(String)part.get("partName")%></td>
              <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "01381") %><%--목표--%></td>
              <td class="tdwhiteM">&nbsp;</td>
              <td class="tdwhiteR" style="word-break:break-all;"><%=(String)part.get("cusError")%></td>
              <td class="tdwhiteR" style="word-break:break-all;"><%=(String)part.get("workError")%></td>
              <td class="tdwhiteR"><%=(String)part.get("facility")%></td>
              <td class="tdwhiteR"><%=(String)part.get("perform")%></td>
              <td class="tdwhiteR"><%=(String)part.get("good")%></td>
              <td class="tdwhiteR"><%=(String)part.get("targetTotal")%></td>
            </tr>
            <%
                      for ( int jnx = 0; jnx < resultList.size() ; jnx++ ) {
                          partResult = (Hashtable)resultList.get(jnx);
                  %>
                <tr>
                <%  if (jnx == 0){ %>
                  <td class="tdwhiteM" rowspan=<%=resultList.size()%> ><%=messageService.getString("e3ps.message.ket_message", "02032") %><%--실적--%></td>
                <%  } %>
                  <td class="tdwhiteM"><%=(String)partResult.get("month")%></td>
                  <td class="tdwhiteR"><%=(String)partResult.get("custErrorPPM")%></td>
                  <td class="tdwhiteR"><%=(String)partResult.get("proErrorPPM")%></td>
                  <td class="tdwhiteR"><%=(String)partResult.get("facility")%></td>
                  <td class="tdwhiteR"><%=(String)partResult.get("perform")%></td>
                  <td class="tdwhiteR"><%=(String)partResult.get("good")%></td>
                  <td class="tdwhiteR"><%=(String)partResult.get("total")%></td>
                </tr>
        <%
                }
          }
        %>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="600">
        <tr>
          <td class="space5"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td class="tdblueL" style="height:100"><%=messageService.getString("e3ps.message.ket_message", "02193") %><%--요청사항--%></td>
    <td colspan="3" class="tdwhiteL0" style="height:100">
        <textarea name="textfield10" class="txt_field" id="textfield12" style="width:98%; height:96%" readonly ><%=StringUtil.checkNull(ketEarlyWarningEndReq.getRequestdesc())%></textarea>
    </td>
  </tr>
  <%
        ContentHolder holder = ContentHelper.service.getContents((ContentHolder)CommonUtil.getObject(endReqOid));
        Vector files = ContentHelper.getContentList(holder);
    %>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01439") %><%--문제점 List--%></td> <!--  문제점 List -->
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
</form>
</body>
</html>
