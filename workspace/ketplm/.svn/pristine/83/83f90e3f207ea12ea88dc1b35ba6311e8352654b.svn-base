<%--
 /**
 * @(#)	select.jsp
 * Copyright (c) e3ps. All rights reserverd
 *
 * @version 1.00
 * @since jdk 1.4.1
 * @author Seung-hwan Choi, skyprda@e3ps.com
 * @desc 다단 select
 */
--%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import ="java.util.StringTokenizer"%>

<%
	String fieldName = request.getParameter("fieldname");	// 테이블에 출력될 필드명 ( var1$var2$var3 )
	String selectName = request.getParameter("selectname");	// 선택상자 이름
	String function = e3ps.common.web.ParamUtil.getStrParameter(request.getParameter("function"));	// function 추가

	StringTokenizer st = null;
	int level = 0;
	if( fieldName != null )
	{
		st = new StringTokenizer(fieldName, "$");
		level = st.countTokens();
	}
%>

<TABLE id='selectTable<%=selectName%>'class="tab_popup05" border="0" cellspacing="0" cellpadding="0">
<%
	if( fieldName != null )
	{
%>
	<!--TR bgcolor="#D6DBE7" align=center height=20-->
	<%
		while (st != null && st.hasMoreTokens())
			st.nextToken();
//			out.println("<TD id=tb_blue>"+st.nextToken()+"</TD>");
	%>
	<!--/TR>
	<TR bgcolor=white-->
	<%
		for(int i=0 ; i<level ; i++)
		{
	%>
		<!--TD-->
			<select name="<%=selectName+i%>" onChange="redirect(this, '<%=selectName+(i+1)%>');<%=function %>" class="fm_jmp" style="width:120">
				<option></option>
			</select>
		<!--/TD-->
	<%}	%>
	<!--/TR-->
<%} %>
</TABLE>
<SCRIPT LANGUAGE="JavaScript" src="/plm/portal/js/select.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
var root<%=selectName%> = new E3PSSelectNode('root','root');
var e3ps<%=selectName%>;
var function<%=selectName%> = "<%=function %>";
</SCRIPT>
