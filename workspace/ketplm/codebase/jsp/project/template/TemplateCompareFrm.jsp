<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import = "e3ps.project.*,e3ps.common.util.*"%>
<%@include file="/jsp/common/context.jsp" %>
<html>
<%
		String oids[] = request.getParameterValues("oid");
		String oid ="oid="+oids[0]+"&oid=" +oids[1];

		String viewSelect = oids[0];
		String selectStr = "";
		
		Object obj = CommonUtil.getObject(viewSelect);
		
		if ( obj instanceof TemplateProject ) {
			selectStr = "ProjectView";
		}else {
			selectStr = "TaskView";
		}
		
		
%>
<head>
<title>PLM</title>
</head>
	<frameset cols="230,*" border="0" bordercolor="#808080" frameborder="0" framespacing="0">
			<frame name="menu" scrolling="auto" src="/plm/jsp/project/template/TemplateCompareTree.jsp?<%=oid%>" ondragstart="return true" onselectstart="return true"
		style="cursor:crosshair;cursor:e-resize;border-right-width:2px;border-right-style:solid;border-color:#42617B">
			<frame name="body" scrolling="auto" src="/plm/jsp/project/template/TemplateCompare<%=selectStr%>.jsp?<%=oid%>" id=contName >	
	</frameset>
<noframes>
<body>
<form method=post target=body >
</form>
</body>
</noframes>
</html>

