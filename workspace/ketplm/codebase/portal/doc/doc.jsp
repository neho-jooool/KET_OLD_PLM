<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Untitled Document</title>
<SCRIPT LANGUAGE="JavaScript">
<!--
	var columntype=""
	var defaultsetting=""

function getCurrentSetting(){
	if (document.body)
	return (document.body.cols)? document.body.cols : document.body.rows
}

function setframevalue(coltype, settingvalue){
	if (coltype=="rows")
		document.body.rows=settingvalue
	else if (coltype=="cols")
		document.body.cols=settingvalue
}

function resizeFrame(contractsetting){
	if (getCurrentSetting()!=defaultsetting)
		setframevalue(columntype, defaultsetting)
	else
		setframevalue(columntype, contractsetting)
}

function init(){
	if (!document.all && !document.getElementById) return
	if (document.body!=null){
		columntype=(document.body.cols)? "cols" : "rows"
		defaultsetting=(document.body.cols)? document.body.cols : document.body.rows
	}
	else
		setTimeout("init()",100)
}
	setTimeout("init()",100)

	function search(){
//		document.all.method = "post";
		document.all.target = "listFrame";
		document.all.action = "/plm/servlet/e3ps.doc.servlet.DocumentServlet";
		document.all.submit();
	}
//-->
</SCRIPT>
</head>
<%
	String name0 = request.getParameter("name0");
	if("".equals(name0)) name0 = "회사표준";
//	System.out.println("[doc.jsp] name0 - " + name0);
%>
<frameset name="mainFrameSet" rows="230,*" framespacing="0" frameborder="no" border="0">
  <frame src="/plm/jsp/common/loading.jsp?url=/plm/jsp/doc/docSearch.jsp&key=name0&value=<%=name0%>" frameborder="no" scrolling="yes" marginwidth="0" marginheight="0" name="searchFrame" />
  <frame src="/plm/jsp/common/loading.jsp?url=/plm/jsp/doc/docList.jsp" frameborder="no" scrolling="auto" marginwidth="0" marginheight="0" name="listFrame" />
</frameset >
<noframes >
<body>
</body>
</noframes></html>
