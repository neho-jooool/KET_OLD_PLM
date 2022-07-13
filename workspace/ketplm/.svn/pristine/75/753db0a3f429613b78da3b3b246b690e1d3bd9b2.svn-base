<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "wt.fc.PersistenceHelper,
                                    wt.fc.QueryResult,
                                    wt.content.*,
                  e3ps.common.web.ParamUtil,
                                    e3ps.common.util.DateUtil,
                                    e3ps.common.util.CommonUtil,
                                    e3ps.common.util.StringUtil,
                                    e3ps.common.content.*,
                                    e3ps.common.code.NumberCode,
                                    e3ps.ews.beans.EWSUtil,
                                    e3ps.ews.beans.EWSHelper,
                                    e3ps.ews.dao.PartnerDao,
                                    e3ps.ews.entity.KETEarlyWarning,
                                    e3ps.ews.entity.KETEarlyWarningTarget,
                                    e3ps.ews.entity.EarlyWarningTargetLink,
                                    e3ps.sap.PartResultInterface,
                                    java.util.Hashtable,
                                    java.util.ArrayList" %>

<%
    String oid = ParamUtil.getParameter(request, "oid");
    KETEarlyWarning ketEarlyWarning = (KETEarlyWarning)CommonUtil.getObject(oid);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<script language=JavaScript src="../../portal/js/common.js"></script>
<script language="javascript" src="/plm/jsp/ews/EWSCommon.js"></script>

<style type="text/css">
<!--
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
-->
</style>
<script type="text/javascript">
<!--

    //엑셀저장
    function goExcel(){
        document.forms[0].target = "download";
        document.forms[0].action = '/plm/servlet/e3ps/EarlyWarningResultServlet';
        document.forms[0].method = "post";
        document.forms[0].submit();
    }

//-->
</script>
</head>

<body>
<form method="post">

<!-- hidden begin -->
<input type="hidden" name="oid" value=<%=oid%> >
<input type="hidden" name="cmd" value="excel">
<!-- hidden end -->

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td  class="tab_btm2"></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="14%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00946") %><%--관리번호--%></td>
    <td colspan="3" class="tdwhiteL0"><%=ketEarlyWarning.getNumber()%></td>
  </tr>
  <tr>
    <td width="14%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
    <td width="36%" class="tdwhiteL">
        <a href="javascript:viewProjectPopup('<%=EWSUtil.ViewPjtOid(ketEarlyWarning.getPjtNo())%>');"><%=ketEarlyWarning.getPjtNo()%></a>
    </td>
    <td width="14%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
    <td width="36%" class="tdwhiteL0">
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
  <tr><!-- 목표/실적 -->
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01386") %><%--목표/실적--%></td>
    <td colspan="3" class="tdwhiteM0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
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
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<iframe name="download" align="center" width="100%" height="0" border="0"></iframe>
</form>
</body>
</html>
