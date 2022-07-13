<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,java.text.SimpleDateFormat"%>
<%@page import="jxl.write.WritableWorkbook"%>
<%@page import="java.io.File"%>
<%@page import="jxl.*,jxl.write.*"%>
<%@page import="wt.fc.*,wt.org.*, wt.part.*, wt.session.*,wt.lifecycle.*,wt.vc.*,wt.query.*"%>
<%@page import="e3ps.common.query.*,e3ps.common.util.*,e3ps.bom.*"%>
<%@page import="e3ps.project.moldPart.beans.MoldPartHelper"%>
<%@page import="e3ps.project.moldPart.MoldPartManager"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />


<%
String managerOid = request.getParameter("managerOid");
String modify = request.getParameter("isModify");
boolean isModify = false;
MoldPartManager manager = (MoldPartManager)CommonUtil.getObject(managerOid);
response.reset();
response.setContentType("application/vnd.ms-excel;charset=EUC-KR");
response.setHeader("Content-Disposition","attachment; filename=" + "PartList.xls");

Kogger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + modify);
if("true".equals(modify)){
	isModify = true;
}

MoldPartHelper.write(manager, response.getOutputStream(), isModify);

%>
