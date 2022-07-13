<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@page import="e3ps.common.util.CommonUtil"%>

<%@page import="e3ps.project.sap.SAPMoldPrice"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.project.sap.MoldPriceData"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
    String orderNumber = request.getParameter("orderNo");
Vector gongsubi = SAPMoldPrice.moldPrice(orderNumber, SAPMoldPrice.Fun1);
Vector gumebasubi = SAPMoldPrice.moldPrice(orderNumber, SAPMoldPrice.Fun2);

%>

<head>
<title></title>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="/plm/portal/js/Calendar.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script type="text/javascript">

</script>

</head>
<body>
<form>
<input type="hidden" name="oid" value=""/>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"></td>
              </tr>
            </table></td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="430" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">
            <table width="420" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01472") %><%--발주가--%></td>
                <td align="right">&nbsp;</td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="420">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="420">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="420">
              <tr>
                <td width="120" class="tdbluem"><%=messageService.getString("e3ps.message.ket_message", "01640") %><%--비용--%></td>
                <td width="120" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01164") %><%--날짜--%></td>
                <td width="180" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01173") %><%--내용--%></td>
              </tr>
              <%
              int total = 0;
              for(int i = 0; i < gumebasubi.size(); i++){

                  MoldPriceData data = (MoldPriceData)gumebasubi.get(i);
                  total += Integer.parseInt(data.price);
              %>
              <tr>
                <td width="120" class="tdwhiteL"><%=data.price%></td>
                <td width="120" class="tdwhiteL"><%=data.date%></td>
                <td width="180" class="tdwhiteL0"><%=data.content%></td>
              </tr>
              <%} %>
              <tr>
                <td width="120" class="tdwhiteL"><%=messageService.getString("e3ps.message.ket_message", "03151") %><%--합계--%></td>
                <td width="120" class="tdwhiteL"><%=total %></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="420">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <table width="420" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00870") %><%--공수비--%></td>
                <td align="right">&nbsp;</td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="420">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="420">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="420">
              <tr>
                <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01640") %><%--비용--%></td>
                <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01164") %><%--날짜--%></td>
                <td width="100" class="tdblueM">WorkId</td>
                <td width="120" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01173") %><%--내용--%></td>
              </tr>
              <%
              int gongsuTotal = 0;
              for(int i = 0; i < gongsubi.size(); i++){
                MoldPriceData data = (MoldPriceData)gongsubi.get(i);
                  gongsuTotal += Integer.parseInt(data.price);
              %>
              <tr>
                <td width="100" class="tdwhiteL"><%=data.price%></td>
                <td width="100" class="tdwhiteL"><%=data.date%></td>
                <td width="100" class="tdwhiteL"><%=data.workId%></td>
                <td width="120" class="tdwhiteL0"><%=data.content%></td>
              </tr>
              <%} %>
              <tr>
                <td width="100" class="tdwhiteL"><%=messageService.getString("e3ps.message.ket_message", "03151") %><%--합계--%></td>
                <td width="100" class="tdwhiteL"><%=gongsuTotal %></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="430">
              <tr>
                <td class="space15"></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="430" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="5">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="425" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
