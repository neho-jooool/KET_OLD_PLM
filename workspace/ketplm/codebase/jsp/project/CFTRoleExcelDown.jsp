<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,java.text.SimpleDateFormat"%>
<%@page import="jxl.write.WritableWorkbook"%>
<%@page import="java.io.File"%>
<%@page import="jxl.*,jxl.write.*"%>
<%@page import="wt.fc.*,wt.org.*, wt.part.*, wt.session.*,wt.lifecycle.*,wt.vc.*,wt.query.*"%>
<%@page import="e3ps.common.query.*,e3ps.common.util.*,e3ps.bom.*"%>
<%@page import="e3ps.project.moldPart.beans.MoldPartHelper"%>
<%@page import="e3ps.project.moldPart.MoldPartManager"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.beans.ProjectScheduleHelper"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />


<%
String oid = request.getParameter("oid");
E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
String fileName = project.getPjtNo();

response.reset();
response.setContentType("application/vnd.ms-excel;charset=EUC-KR");
response.setHeader("Content-Disposition","attachment; filename=" + fileName + ".xls");

ProjectScheduleHelper.excelCFTDown(project, response.getOutputStream());

%>
