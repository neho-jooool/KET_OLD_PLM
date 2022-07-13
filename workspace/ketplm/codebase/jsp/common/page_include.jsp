
<!-- 페이지 설정 시작 //-->
<jsp:include page="/jsp/common/pageControl.jsp" flush="false">
	<jsp:param name="perPage" value="<%=control.getInitPerPage()%>"/>
	<jsp:param name="formPage" value="<%=control.getInitFormPage()%>"/>

	<jsp:param name="totalCount" value="<%=control.getTotalCount()%>"/>
	<jsp:param name="totalPage" value="<%=control.getTotalPage()%>"/>
	<jsp:param name="currentPage" value="<%=control.getCurrentPage()%>"/>
	<jsp:param name="startPage" value="<%=control.getStartPage()%>"/>
	<jsp:param name="endPage" value="<%=control.getEndPage()%>"/>

	<jsp:param name="href" value="<%=control.getHref()%>"/>
	<jsp:param name="param" value="<%=control.getParam()%>"/>
	<jsp:param name="sessionid" value="<%=control.getSessionId()%>"/>
	<jsp:param name="isPost" value="<%=control.isPostMethod()%>"/>
</jsp:include>
<!-- 페이지 설정 끝 //-->
