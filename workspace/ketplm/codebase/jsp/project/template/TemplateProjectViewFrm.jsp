<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.project.*,e3ps.common.util.*"%>
<%@include file="/jsp/common/context.jsp" %>
<%
	String oid = CharUtil.E2K(request.getParameter("oid"));
	String projectOid = oid;
	String auth = request.getParameter("auth");
	String authStr = "";
	if(auth != null){
		authStr = "&auth=true";
		
	}
		
	
	Object obj= CommonUtil.getObject(oid);	
	String target = "Project";
	if ( obj instanceof TemplateTask ) {
		projectOid = CommonUtil.getOIDString(((TemplateTask)obj).getProject());
		target = "Task";
	}

	String popup = StringUtil.checkNull(request.getParameter("popup"));
	String tmpPopUp = "";
	if(popup != null && !popup.equals("")) {
		tmpPopUp = "&popup=popup";
	}
%>

<html>
<head>
</head>
<!-- NEW -->





<frameset rows=1*,0 framespacing=0 frameborder=no id=topFrame>
	<!--frame name=topMenuFrame noresize frameborder=no scrolling=no id=topMenuFrame src="/plm/portal/common/top.jsp"-->
	<frameset cols=230,* framespacing=1 frameborder=0 id=bottomFrame >
		<frameset rows="97,*" cols="1*" name=bottomLeftFrame id=bottomLeftFrame>
			<frame name="link" scrolling="no" style="border-right-width:3px;border-right-style:solid;border-right-color:#191970" 
			src="/plm/jsp/project/template/TemplateProjectLinkForm.jsp?oid=<%=projectOid%><%=tmpPopUp%>" >
			<frame name="tree" scrolling="no" style="border-right-width:3px;border-right-style:solid;border-right-color:#191970" 
			src="/plm/jsp/project/template/TemplateProjectTree.jsp?oid=<%=projectOid%><%=tmpPopUp%>" >
		</frameset>
		<!--frame name=bottomLeftFrame noresize frameborder=no scrolling=no src=/plm/portal/common/project_submenu.jsp id=bottomLeftFrame -->
		<!--frame name=barFrame src=barFrame.html frameborder=no id=barFrame -->
		<frame name=body frameborder=no id=bottomRightFrame src=/plm/jsp/project/<%=target%>View.jsp?oid=<%=oid%><%=tmpPopUp%>>
	</frameset>
</frameset>


<!-- NEW -->
<!-- OLD -->
<!--frameset name=projectframe rows="1*" cols="320,*" border="0" bordercolor="#808080" frameborder="0" framespacing="0">
	<frameset rows="50,*" cols="1*">
		<frame name="link" scrolling="no" src="/plm/jsp/project/template/TemplateProjectLinkForm.jsp?oid=<%=projectOid%>" noresize>
		<frame name="tree" scrolling="no" src="/plm/jsp/project/template/TemplateProjectTree.jsp?oid=<%=projectOid%>" noresize>
	</frameset>
	<frame name="body" scrolling="auto" src="/plm/jsp/project/template/Template<%=target%>View.jsp?oid=<%=oid%>">
</frameset-->
<!-- OLD -->
</html>


<script>

	var treeDiv = document.getElementById("tree");
	var imgDiv = document.getElementById("link");
	//alert(treeDiv.offsetHeight +'\t' + imgDiv.offsetHeight + '\t' + document.body.clientHeight);
	h1 = document.height -imgDiv.offsetHeight;
	treeDiv.height = h1;
</script>
