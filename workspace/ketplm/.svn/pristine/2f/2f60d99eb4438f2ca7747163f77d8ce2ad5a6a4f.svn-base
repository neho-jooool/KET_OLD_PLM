<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="wt.util.*, wt.introspection.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<head>
<title>Query Key & Column Name</title>
</head>

<BODY>
<br><br>
<center>

<form method="post" name="class_define" id="class_define">
<strong><%=messageService.getString("e3ps.message.ket_message", "02916") %><%--클래스 명--%> (<font color="#000080"><%=messageService.getString("e3ps.message.ket_message", "03360") %><%--예--%> : wt.part.WTPart</font>) :</strong> <input type="text" name="className" style="width: 350;">
<input type="submit" name="submit" value="<%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%>">
</form>

<%
String className = request.getParameter("className");
if(className == null)
  className = "";

String msg="";
%>

<br>
<%=className%>.class
<br><br>

<table border=1 cellpadding=0 cellspacing=2 width=600>
<tr valign="middle" style="background: Aqua;">
<td align="center" valign="middle">
Java Type
</td>
<td align="center" valign="middle">
Query Key
</td>
<td align="center" valign=middle>
Column Name
</td>
</tr>

<%
try {
  if(className != null && !className.equals("")) {
    String sColName = "";
    String desName = "";
    String type = "";

    ColumnDescriptor[] descriptors = WTIntrospector.getDatabaseInfo(className).getBaseTableInfo().getColumnDescriptors();

    for (int nIndex = 0 ; nIndex < descriptors.length ; nIndex++)
    {
      desName = descriptors[nIndex].getName();
      type = descriptors[nIndex].getJavaType();
      sColName = descriptors[nIndex].getColumnName();
%>
<tr>
<td align=left width=400 valign=middle>
<%=type.trim() %>
</td>
<td align=left width=400 valign=middle>
<%=desName.trim()%>
</td>
<td align=left width=200 valign=middle>
<%=sColName.trim()%><br>
</td>
</tr>
<%
    }
  }
} catch (WTException e) {
  msg = e.getLocalizedMessage();
}
%>
</table>
<br><br><br><br><br>
<strong><font color="#FF0000"><%=msg%></font></strong>
</center>
</body>
</html>
