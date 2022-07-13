<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="e3ps.qms.control.QmsCtl"%>
<%@ page import="java.util.ArrayList, java.util.Hashtable"%>
<%
QmsCtl projectCtl = new QmsCtl();
int complete = 0;
complete = projectCtl.InsertPLM();

out.println(complete);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="Content-Type" content="text/html" charset="utf-8"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<body>

</body>
</html>