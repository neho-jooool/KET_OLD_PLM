<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page import="wt.org.WTUser"%>
<%@ page import="e3ps.common.util.*,e3ps.groupware.company.*"%>
<%@include file="/jsp/common/context.jsp"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
e3ps.common.jdf.message.Message message = e3ps.common.jdf.message.MessageBox.getInstance("message");
%>
<HTML>
<HEAD>
<TITLE><%=messageService.getString("e3ps.message.ket_message", "01219") %><%--대결재자--%></TITLE>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/org.js"></SCRIPT>
<SCRIPT language=JavaScript>
function cancel(oid)
{
  if(!confirm("<%=message.getMessage("confirm.update")%>")) return;

  document.forms[0].action = 'AlertProxy.jsp?cmd=cancel&poid='+oid;
  document.forms[0].submit();
}
</SCRIPT>
</HEAD>
<body>
<%
  String proxyOid = request.getParameter("proxy");
  String pOid = request.getParameter("poid");

  WTUser proxyUser = (WTUser)CommonUtil.getObject(proxyOid);
  People people = (People)CommonUtil.getObject(pOid);

  if("cancel".equals(request.getParameter("cmd")))
  {
        people.setProxy(null);
      wt.fc.PersistenceHelper.manager.modify(people);
%>
    <SCRIPT languange=JavaScript>
      alert("<%=message.getMessage("update.success")%>");
      self.close();
    </SCRIPT>
<%
      return;
  }
%>
<form method=post>
<div class=div_title><SCRIPT>printTitle('<%=messageService.getString("e3ps.message.ket_message", "01221") %><%--대결재자 지정 알림--%>')</SCRIPT></div>
<table width="98%" border="0" cellspacing="1" cellpadding="1" bgcolor="AABDC6" align="center" valign=middle>
  <tr height=100>
    <td bgcolor="D8E0E7" width="100%"align="center" id=title>
      <TABLE>
        <TR>
          <TD id=title><font size=3><%=proxyUser.getFullName()%></font></TD>
          <TD id=title><%=messageService.getString("e3ps.message.ket_message", "00028") %><%--{0}님으로 대결 지정되어 있습니다--%></TD>
        </TR>
      </TABLE>
    </td>
  </tr>
  <tr height=30>
    <td bgcolor="#FFFFFF" align="center">
      <input type='button' id='button2' value='<%=messageService.getString("e3ps.message.ket_message", "01218") %><%--대결 취소--%>' onClick="cancel('<%=pOid%>')">
      <input type='button' id='button2' value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' onClick="closeWindow()">
    </td>
  </tr>
</table>

</form>
</body>
</html>
