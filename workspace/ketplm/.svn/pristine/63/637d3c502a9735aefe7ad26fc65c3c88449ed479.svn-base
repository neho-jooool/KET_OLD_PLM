<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="e3ps.common.code.*"%>
<%@include file="/jsp/common/context.jsp"%>
<%@page import="e3ps.load.code.LoadNumberCode"%>
<stdinfo>
	<results>
		<contents>
<%
	String message = "false";

	String command = request.getParameter("command");
	if(command == null) {
		command = "";
	}
	command = java.net.URLDecoder.decode(command,"euc-kr");

	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	//ERP 전송...
	if("erpSend".equals(command)) {
		
		String codetype = request.getParameter("codetype");
		codetype = java.net.URLDecoder.decode(codetype==null?"":codetype,"euc-kr");
		boolean flag = NumberCodeHelper.sendStdInfoToERP(null, codetype);
		if(flag) {
			message = "true";
		}
		else {
			message = "false";
		}
	}
	
	if("erpSite".equals(command)) {
		
		LoadNumberCode.loadSite();
		message = "true";
	
	}
%>
			<message>
				<l_message><![CDATA[<%=message%>]]></l_message>
			</message>
		</contents>
	</results>
</stdinfo>
