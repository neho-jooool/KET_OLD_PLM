<%@ page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
    <title>KET PLM System</title>
  </head>
  <body>
    <center>
    <br><br><br>
    <P><%=messageService.getString("e3ps.message.ket_message", "00369") %><%--PLM 시스템에 등록된 사용자가 아닙니다--%>
    <br>
    <br><%=messageService.getString("e3ps.message.ket_message", "02015") %><%--시스템을 사용해야 하는 경우 관리자에게 문의하세요--%>
    </center>
  </body>
</html>
