<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.util.*,e3ps.common.web.ParamUtil" %>
<%@include file="/jsp/common/context.jsp"%>
<%
	String codetype = StringUtil.trimToEmpty(request.getParameter("codetype"));
	String command = StringUtil.trimToEmpty(request.getParameter("command"));
	String mode = StringUtil.trimToEmpty(request.getParameter("mode"));
	String invokeMethod = StringUtil.trimToEmpty(request.getParameter("invokeMethod"));
	String displayField = StringUtil.trimToEmpty(request.getParameter("displayField"));
	String valueField = StringUtil.trimToEmpty(request.getParameter("valueField"));

	String expandedDepth = StringUtil.trimToEmpty(request.getParameter("expandedDepth"));
	String selectedDepth = StringUtil.trimToEmpty(request.getParameter("selectedDepth"));

	String numberCode = StringUtil.trimToEmpty(request.getParameter("numberCode"));
	String designTeam = StringUtil.trimToEmpty(request.getParameter("designTeam"));

	String viewType = StringUtil.trimToEmpty(request.getParameter("viewType"));
	String disable = StringUtil.trimToEmpty(request.getParameter("disable"));
	
	String treeWidth = "180";
	if(viewType != null && "noTree".equals(viewType)) treeWidth = "0";

	if(command == null)
		command = "";

	if(mode == null)
		mode = "";

	if(invokeMethod == null)
		invokeMethod = "";
	
	if(displayField == null)
	    displayField = "";
	
	if(valueField == null)
	    valueField = "";

	if(numberCode == null)
		numberCode = "";

	if(designTeam == null)
		designTeam = "";

	if(expandedDepth == null)
		expandedDepth = "";

	if(selectedDepth == null)
		selectedDepth = "";

	String mainParam = "codetype="+codetype;
	if(command.length() > 0)
		mainParam += "&command=" + command;

	if(mode.length() > 0)
		mainParam += "&mode=" + mode;

	if(invokeMethod.length() > 0)
		mainParam += "&invokeMethod=" + invokeMethod;

	if(numberCode.length() > 0)
		mainParam += "&numberCode=" + numberCode;

	if(designTeam.length() > 0)
		mainParam += "&designTeam=" + designTeam;

	if(expandedDepth.length() > 0)
		mainParam += "&expandedDepth=" + expandedDepth;

	if(selectedDepth.length() > 0)
		mainParam += "&selectedDepth=" + selectedDepth;
	
	if(disable.length() > 0)
	        mainParam += "&disable=" + disable;
	
	if(displayField.length() > 0)
	        mainParam += "&displayField=" + displayField;
	
	if(valueField.length() > 0)
	        mainParam += "&valueField=" + valueField;
	
	String treeURL = "/plm/jsp/common/code/NumberCodeMgtTree.jsp?" + mainParam;
	String mainURL = "/plm/servlet/e3ps/NumberCodeMgtListServlet?"+mainParam;
%>
<head>
<base target="_self">
<title>CodeTree</title>
<script language="javascript">
	function pageClose() {
		window.close();
	}
</script>

</head>
<frameset rows="1*" cols="<%=treeWidth%>,*" border="0" frameborder="0" framespacing="0">
<frame name="tree" scrolling="no" marginwidth="0" marginheight="0" src="<%=treeURL%>" style="border-right:2px solid green;">
<frame name="main" scrolling="no" marginwidth="0" marginheight="0" src="<%=mainURL%>">

</frameset>
</html>
