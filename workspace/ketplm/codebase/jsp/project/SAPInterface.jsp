<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="java.util.Date,
                java.util.Hashtable,
                java.util.Vector"%>
<%@page import="wt.project.Role,
                wt.team.TeamHelper,
                wt.team.TeamTemplate"%>
<%@page import="e3ps.common.code.NumberCode,
                e3ps.common.code.NumberCodeHelper,
                e3ps.common.util.*,
                e3ps.project.*"%>
<%@page import="e3ps.project.sap.MoldPriceData"%>
<%@page import="e3ps.project.sap.SAPMoldPrice"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
   String order = request.getParameter("order");

   Vector fuc1 = SAPMoldPrice.moldPrice(order, SAPMoldPrice.Fun1);  //공수비
   Vector fuc2  = SAPMoldPrice.moldPrice(order, SAPMoldPrice.Fun2); //구매발주가

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="1">
  <tr>
    <td colspan="5"><%=messageService.getString("e3ps.message.ket_message", "00870") %><%--공수비--%></td>
  </tr>
  <tr>
    <td><%=messageService.getString("e3ps.message.ket_message", "01164") %><%--날짜--%></td>
    <td><%=messageService.getString("e3ps.message.ket_message", "00996") %><%--금액--%></td>
    <td><%=messageService.getString("e3ps.message.ket_message", "02200") %><%--워크 센터 ID--%></td>
    <td><%=messageService.getString("e3ps.message.ket_message", "02501") %><%--전표 번호--%></td>
    <td><%=messageService.getString("e3ps.message.ket_message", "01173") %><%--내용--%></td>
  </tr>
<%
//공수비
int totalPrice = 0;
for(int i = 0; i < fuc1.size(); i++){

  MoldPriceData moldPriceData = (MoldPriceData)fuc1.get(i);
  String date = moldPriceData.date;  //날짜
  String price = moldPriceData.price;  // 금액
  try{
    totalPrice += Integer.parseInt(price);
  }catch(Exception ex){
     Kogger.debug("rrrrrrrr");
  }
  String workId = moldPriceData.workId; //워크 센터 ID
  String number = moldPriceData.number; //전표 번호
  String content = moldPriceData.content; //내용
%>
  <tr>
    <td><%=date %></td>
    <td><%=price %></td>
    <td><%=workId %></td>
    <td><%=number %></td>
    <td><%=content %></td>
  </tr>
<%
  }
%>
    <tr>
    <td colspan="4"><%=messageService.getString("e3ps.message.ket_message", "03155") %><%--합산--%></td>
    <td><%=totalPrice%></td>
  </tr>
</table>

<br>
<br>

<table border="1">
  <tr>
    <td colspan="5"><%=messageService.getString("e3ps.message.ket_message", "01472") %><%--발주가--%></td>
  </tr>
  <tr>
    <td><%=messageService.getString("e3ps.message.ket_message", "01164") %><%--날짜--%></td>
    <td><%=messageService.getString("e3ps.message.ket_message", "00996") %><%--금액--%></td>
    <td><%=messageService.getString("e3ps.message.ket_message", "02200") %><%--워크 센터 ID--%></td>
    <td><%=messageService.getString("e3ps.message.ket_message", "02501") %><%--전표 번호--%></td>
    <td><%=messageService.getString("e3ps.message.ket_message", "01173") %><%--내용--%></td>
  </tr>
<%
//공수비
int totalPrice2 = 0;
for(int i = 0; i < fuc2.size(); i++){

  MoldPriceData moldPriceData = (MoldPriceData)fuc2.get(i);
  String date = moldPriceData.date;  //날짜
  String price = moldPriceData.price;  // 금액
  try{
    totalPrice2 += Integer.parseInt(price);
  }catch(Exception ex){
     Kogger.debug("rrrrrrrr");
  }
  String workId = moldPriceData.workId; //워크 센터 ID
  String number = moldPriceData.number; //전표 번호
  String content = moldPriceData.content; //내용
%>
  <tr>
    <td><%=date %></td>
    <td><%=price %></td>
    <td><%=workId %></td>
    <td><%=number %></td>
    <td><%=content %></td>
  </tr>
<%
  }
%>
    <tr>
    <td colspan="4"><%=messageService.getString("e3ps.message.ket_message", "03155") %><%--합산--%></td>
    <td><%=totalPrice2%></td>
  </tr>
</table>

<br>
<br>

<table border=1>
  <tr>
    <td><%=messageService.getString("e3ps.message.ket_message", "00871") %><%--공수비 + 발주비--%></td>
    <td><%=totalPrice + totalPrice2%></td>
  </tr>
</table>



</body>
</html>
