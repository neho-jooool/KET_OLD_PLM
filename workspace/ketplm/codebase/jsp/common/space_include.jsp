<%--
 /**
 * @(#)	space_include.jsp
 * Copyright (c) e3ps. All rights reserverd
 *
 * @version 1.00
 * @since jdk 1.4.2
 * @author Seong Jin, Han	sjhan@e3ps.com
 * @desc 테이블 사이의 간격을 조절하는 페이지
 */
--%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.util.StringUtil" %>
<%
	String tb_class = StringUtil.checkReplaceStr(request.getParameter("tb_class"), "tab_w01");
	String td_class = StringUtil.checkReplaceStr(request.getParameter("td_class"), "space5");
%>
	<table border="0" cellspacing="0" cellpadding="0" class="<%=tb_class %>">
		<tr>
			<td class="<%=td_class %>"> </td>
		</tr>
	</table>
