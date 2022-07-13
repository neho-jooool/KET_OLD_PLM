<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.util.*,e3ps.common.web.ParamUtil" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
<%
	String command = request.getParameter("command");
	String oid = request.getParameter("oid");

	if(command == null)
		command = "";

	if(oid == null)
		oid = "";



	String mainParam = "";
	if(command.length() > 0) {
		mainParam += "command=" + command;
	}

	if(oid.length() > 0) {
		if(mainParam.length() > 0) {
			mainParam += "&";
		}
		mainParam += "oid=" + oid;
	}

	String treeURL = "/plm/jsp/project/template/ProjectTempTaskTree.jsp?" + mainParam;
	String mainURL = "/plm/jsp/project/template/ProjectTempEditPage.jsp?"+mainParam;
%>
<html>
<head>
<base target="_self">
<title>CodeTree</title>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="javascript">
	function pageClose() {
		window.close();
	}
</script>

</head>
<frameset rows="1*" cols="300,*" border="0" frameborder="0" framespacing="0">
<frame name="tree" scrolling="no" marginwidth="0" marginheight="0" src="<%=treeURL%>" style="border-right:1px solid green;">
<frame name="main" scrolling="no" marginwidth="0" marginheight="0" src="<%=mainURL%>">
</frameset>
<NOFRAMES>
<table border="0" cellspacing="0" cellpadding="0" class="tab_w01">
	<tr>
		<td>
			<table border=0 cellpadding=0 cellspacing=0 >
				<tr>
					<td><img src=/plm/portal/images/title2_left.gif></td>
					<td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "00516") %><%--Template 정보--%></td>
					<td><img src=/plm/portal/images/title2_right.gif></td>
				</tr>
			</table>
		</td>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class=fixLeft></td>
					<td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" onClick="Javascript:self.close();"></td>
					<td class=fixRight></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</NOFRAMES>

</html>
