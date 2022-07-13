<%@page contentType="text/html; charset=UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<script language='javascript' src='/plm/jsp/common/content/content.js'></script>
<%
  String color = request.getParameter("color");
  if(color == null) color="bbdddd";
    String colspan = request.getParameter("colspan");
  if(color == null) colspan="";

//   div로 구성된 페이지인 경우
  String isDiv = request.getParameter("isdiv");

  if(isDiv == null)
  {
%>
    <TD bgcolor='<%=color%>' width=20%>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02667") %><%--주 첨부파일--%><BR>&nbsp;(<%=messageService.getString("e3ps.message.ket_message", "00011", 30) %><%--{0}MB이하만--%>)</TD>
    <TD bgcolor=white  colspan='<%=colspan%>'>
<%
  }
  else
  {
%>
    <div class=div_body_row>
      <div class=div_row_label style='background:<%=color%>'><%=messageService.getString("e3ps.message.ket_message", "02667") %><%--주 첨부파일--%><BR>(<%=messageService.getString("e3ps.message.ket_message", "00011", 30) %><%--{0}MB이하만--%>)</div>
      <div class=div_row_long>
<%} %>
  <INPUT type='file' name='PRIMARY'  onchange='isValidPrimarySize(this)' id=input onKeyDown="this.blur()" style="ime-mode:disabled">
<%=(isDiv==null) ? "</TD>" : "</div></div>" %>


