<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table>
    <tr>
        <td>
            <font color="#74C600">■</font> <%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%>&nbsp;
            <br>
            <font color="#FF3300">■</font> <%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%>&nbsp;
            <br>
            <font color="ORANGE">■</font> <%=messageService.getString("e3ps.message.ket_message", "02157") %><%--예상지연--%>&nbsp;
            <br>
            <font color="#0033CC">■</font> <%=messageService.getString("e3ps.message.ket_message", "01462") %><%--미진행--%>&nbsp;
        </td>
    </tr>
    <tr>
    <td height="30" valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' onClick="javascript:window.close();" class="s_font"></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
