<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*, wt.org.*,wt.query.*,wt.team.*,wt.project.Role"%>
<%@page import="e3ps.common.util.*,e3ps.groupware.company.*"%>
<%@page import="e3ps.project.*,e3ps.project.beans.*"%>
<%@page import="e3ps.wbs.*,e3ps.wbs.service.*"%>
<%@include file="/jsp/common/context.jsp"%>
<stdinfo>
	<results>
		<contents>
<%
	String message = "false";
	String l_result = "";
	String command = request.getParameter("command");
	if(command == null) {
		command = "";
	}
	command = java.net.URLDecoder.decode(command,"euc-kr");

	
	if("autoCompleteMember".equals(command)) {
		message = "true";
%>
			<data_info>
<%
		String userName = request.getParameter("userName");
		String deptCode = request.getParameter("deptCode");
		String deptOid = request.getParameter("deptOid");

		userName = java.net.URLDecoder.decode(userName==null?"":userName,"euc-kr");
		deptCode = java.net.URLDecoder.decode(deptCode==null?"":deptCode,"euc-kr");
		deptOid = java.net.URLDecoder.decode(deptOid==null?"":deptOid,"euc-kr");
		
		Department dept = null;
		if(deptOid.length() > 0) {
			ReferenceFactory rf = new ReferenceFactory();
			dept = (Department)rf.getReference(deptOid).getObject();
		}

		if( (dept == null) && (deptCode.length() > 0) ) {
			dept = DepartmentHelper.manager.getDepartment(deptCode);
		}

		QueryResult members = DepartmentHelper.manager.getDepartmentPeople(dept, userName, true);
		
		String wtuserOID = "";
		String peopleOID = "";

		PeopleData data = null;
		Object obj[] = null;
		while(members.hasMoreElements()) {
			obj = (Object[])members.nextElement();
			data = new PeopleData(obj[0]);	

			wtuserOID = (data.wtuser).getPersistInfo().getObjectIdentifier().getStringValue();
			peopleOID = (data.people).getPersistInfo().getObjectIdentifier().getStringValue();
%>
				<l_id><![CDATA[<%=data.id%>]]></l_id>
				<l_name><![CDATA[<%=data.name%>]]></l_name>
				<l_duty><![CDATA[<%=data.duty%>]]></l_duty>
				<l_departmentName><![CDATA[<%=data.departmentName%>]]></l_departmentName>
				<l_email><![CDATA[<%=data.email%>]]></l_email>
				<l_oid><![CDATA[<%=wtuserOID%>]]></l_oid>
				<l_peopleOid><![CDATA[<%=peopleOID%>]]></l_peopleOid>
<%
		}
%>
			</data_info>
<%
	}
%>
			<message>
				<l_message><![CDATA[<%=message%>]]></l_message>
				<l_result><![CDATA[<%=l_result%>]]></l_result>
			</message>
		</contents>
	</results>
</stdinfo>
