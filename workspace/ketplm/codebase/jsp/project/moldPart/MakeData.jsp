<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="wt.part.WTPart"%>
<%@page import="com.ptc.wpcfg.logic.Identified"%>
<%@page import="wt.part.WTPartMasterIdentity"%>
<%@page import="wt.part.WTPartMaster"%>
<%@page import="wt.fc.IdentityHelper"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String oid = request.getParameter("oid");
	String number = request.getParameter("number");
	
	oid = "VR:wt.part.WTPart:27706";
	
	WTPart part = (WTPart)CommonUtil.getObject(oid);
	
	WTPartMaster master = (WTPartMaster)part.getMaster();

	WTPartMasterIdentity emid = (WTPartMasterIdentity) master.getIdentificationObject();
    emid.setNumber("2A031A01");
    IdentityHelper.service.changeIdentity(master, emid);
    out.println("number Changed...1");
    
    oid = "VR:wt.part.WTPart:27714";
    
	part = (WTPart)CommonUtil.getObject(oid);
    master = (WTPartMaster)part.getMaster();
	emid = (WTPartMasterIdentity) master.getIdentificationObject();
    emid.setNumber("2A031A02");
    IdentityHelper.service.changeIdentity(master, emid);
    out.println("number Changed...2");
%>
</body>
</html>
