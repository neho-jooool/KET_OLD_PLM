<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.project.beans.PerformanceHelper"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%
String radioValue = request.getParameter("radioValue");
String fileName = "성과관리목록";
response.reset();
response.setContentType("application/vnd.ms-excel;charset=EUC-KR");
response.setHeader("Content-Disposition","attachment; filename=" + fileName + ".xls");
PerformanceHelper.manager.excelPerformanceDown(radioValue, response.getOutputStream());

%>
