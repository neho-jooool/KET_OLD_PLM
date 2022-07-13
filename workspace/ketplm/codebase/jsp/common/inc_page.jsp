
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<table width=98% align=center>
	<tr>
		<td width=100%>
			<c:import url="/jsp/common/pageControl.jsp">
				<c:param name="totalCount" value="${control.totalCount}"/>
				<c:param name="totalPage" value="${control.totalPage}"/>
				<c:param name="currentPage" value="${control.currentPage}"/>
				<c:param name="startPage" value="${control.startPage}"/>
				<c:param name="endPage" value="${control.endPage}"/>
				<c:param name="href" value="${param.href}"/>
				<c:param name="param" value="${control.param}"/>
				<c:param name="sessionid" value="${control.sessionId}"/>
				<c:param name="isPost" value="${param.ispost}"/>
				<c:param name="cmd" value="${param.cmd}"/>
			</c:import>
		</td>
	</tr>
</table>
