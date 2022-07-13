<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import=" ext.ket.bom.query.*"%>
<%@page import=" java.util.*"%>
<%@page import=" java.io.*"%>
<%@page import=" org.json.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%


KETBOMQueryBean bean = new KETBOMQueryBean();
PrintWriter pw;

String partNo = request.getParameter("partNo");
String rev = request.getParameter("rev");
  
String oid = "";

oid = bean.getPartOid(partNo, rev);

pw = response.getWriter();
pw.print(oid);
pw.flush();
pw.close();
%>
