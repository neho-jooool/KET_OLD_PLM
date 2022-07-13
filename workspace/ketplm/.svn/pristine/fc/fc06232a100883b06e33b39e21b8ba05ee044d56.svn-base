<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%@page import = "wt.fc.PersistenceHelper,
                                    wt.fc.QueryResult,
                                    wt.content.*,
                  e3ps.common.web.ParamUtil,
                                    e3ps.common.util.DateUtil,
                                    e3ps.common.util.CommonUtil,
                                    e3ps.common.util.StringUtil,
                                    e3ps.common.content.*,
                                    e3ps.ews.beans.EWSUtil,
                                    e3ps.ews.beans.EWSHelper,
                                    e3ps.ews.entity.KETEarlyWarning,
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
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">

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
    <td class="tdwhiteM0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right">
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><a href="javascript:goExcel();"><img src="../../portal/images/iocn_excel.png" border="0"></a></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
        <tr>
          <td width="70" rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
          <td width="95" rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
          <td width="20" rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02224") %><%--월--%></td>
          <td colspan="4" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00833") %><%--고객불량--%></td>
          <td colspan="3" class="tdgrayM">
              <% if (ketEarlyWarning.getInOut().equals("i")) { %>
                   <%=messageService.getString("e3ps.message.ket_message", "00883") %><%--공정불량--%>
              <% }else { %>
                   <%=messageService.getString("e3ps.message.ket_message", "01934") %><%--수입불량--%>
              <% } %>
          </td>
          <td colspan="4" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01785") %><%--생산성(%)--%></td>
        </tr>
        <tr>
            <td width="65" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02881") %><%--출하--%><br><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
          <td width="55" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01620") %><%--불량--%><br><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
          <td width="55" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01620") %><%--불량--%><br><%=messageService.getString("e3ps.message.ket_message", "00699") %><%--건수--%></td>
          <td width="50" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01620") %><%--불량--%><br>PPM</td> <!-- 불량 / PPM-->
          <td width="65" class="tdgrayM">
              <% if (ketEarlyWarning.getInOut().equals("i")) { %>
                   <%=messageService.getString("e3ps.message.ket_message", "01787", "<br>") %><%--생산{0}수량--%></td>
              <% }else { %>
                   <%=messageService.getString("e3ps.message.ket_message", "02377") %><%--입고--%><br><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
              <% } %>
          </td>
          <td width="55" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01620") %><%--불량--%><br><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
          <td width="50" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01620") %><%--불량--%><br>PPM</td>
          <td width="40" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02001") %><%--시간--%><br><%=messageService.getString("e3ps.message.ket_message", "00573") %><%--가동--%></td>
          <td width="40" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01904") %><%--성능--%><br><%=messageService.getString("e3ps.message.ket_message", "00573") %><%--가동--%></td>
          <td width="40" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02103") %><%--양품율--%></td>
          <td width="40" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02663") %><%--종합--%><br><%=messageService.getString("e3ps.message.ket_message", "03254") %><%--효율--%></td>
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

                  if (resultList.size() > 0){
                      partResult = (Hashtable)resultList.get(0);
        %>
        <tr>
          <td class="tdwhiteM" rowspan=<%=resultList.size()%> ><%=(String)part.get("partNo")%></td>
          <td class="tdwhiteM" rowspan=<%=resultList.size()%> ><%=(String)part.get("partName")%></td>
          <td class="tdwhiteM"><%=(String)partResult.get("month")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("outputCnt")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("custError")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("custErrorCnt")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("custErrorPPM")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("proCnt")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("proError")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("proErrorPPM")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("facility")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("perform")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("good")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("total")%></td>
        </tr>
        <%
                      for ( int jnx = 1; jnx < resultList.size() ; jnx++ ) {
                          partResult = (Hashtable)resultList.get(jnx);
        %>
        <tr>
          <td class="tdwhiteM"><%=(String)partResult.get("month")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("outputCnt")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("custError")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("custErrorCnt")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("custErrorPPM")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("proCnt")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("proError")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("proErrorPPM")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("facility")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("perform")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("good")%></td>
          <td class="tdwhiteR"><%=(String)partResult.get("total")%></td>
        </tr>
        <%    }
            }else {    %>
          <td class="tdwhiteM"><%=(String)part.get("partNo")%></td>
          <td class="tdwhiteM"><%=(String)part.get("partName")%></td>
          <td class="tdwhiteM">&nbsp;</td>
          <td class="tdwhiteR">&nbsp;</td>
          <td class="tdwhiteR">&nbsp;</td>
          <td class="tdwhiteR">&nbsp;</td>
          <td class="tdwhiteR">&nbsp;</td>
          <td class="tdwhiteR">&nbsp;</td>
          <td class="tdwhiteR">&nbsp;</td>
          <td class="tdwhiteR">&nbsp;</td>
          <td class="tdwhiteR">&nbsp;</td>
          <td class="tdwhiteR">&nbsp;</td>
          <td class="tdwhiteR">&nbsp;</td>
          <td class="tdwhiteR">&nbsp;</td>
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
<iframe name="download" align="center" width="0" height="0" border="0"></iframe>
</form>
</body>
</html>
