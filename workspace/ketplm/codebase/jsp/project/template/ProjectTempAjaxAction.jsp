<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*, wt.query.*"%>
<%@page import="e3ps.common.util.*"%>
<%@page import="e3ps.project.*,e3ps.project.beans.*"%>
<%@include file="/jsp/common/context.jsp"%>
<%@page import="wt.httpgw.WTURLEncoder"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<stdinfo>
	<results>
		<contents>
<%
	String message = "false";

	String command = request.getParameter("command");
	String name = request.getParameter("name");
	String duration = request.getParameter("duration");
	String description = request.getParameter("description");
	String pjtNo = request.getParameter("pjtNo");
	String imsi = request.getParameter("imsi");
	String ptType = request.getParameter("ptType");
	String planType = request.getParameter("planType");
	String assembling = request.getParameter("assembling");
	String developType = request.getParameter("developType");
	String makeType = request.getParameter("makeType");
	String productType = request.getParameter("productType");
	
	if(command == null) {
		command = "";
	}
	if(name == null) {
		name = "";
	}
	if(duration == null) {
		duration = "";
	} 
	if(description == null) {
		description = "";
	}
	if(pjtNo == null) {
		pjtNo = "";
	}
	if(imsi == null) {
		imsi = "";
	}
	if(planType == null) {
		planType = "";
	}
	if(assembling == null) {
		assembling = "";
	}
	if(developType == null) {
		developType = "";
	}
	if(makeType == null) {
		makeType = "";
	}
	if(productType == null) {
		productType = "";
	}
	
	name= java.net.URLDecoder.decode(name,"euc-kr");
	duration= java.net.URLDecoder.decode(duration,"euc-kr");
	
	
	description= WTURLEncoder.decode(description);
	pjtNo= java.net.URLDecoder.decode(pjtNo,"euc-kr");
	
	if("init".equals(command)) {//Project Template 생성
		String tempid = request.getParameter("tempid");
		if(tempid == null) {
			tempid = "";
		}

		tempid= java.net.URLDecoder.decode(tempid,"euc-kr");

		Hashtable hash = new Hashtable();
		hash.put("NAME", name);
		hash.put("DURATION", duration);
		hash.put("DESCRIPTION", description);
		hash.put("TEMPID", tempid);
		hash.put("IMSI",imsi);
		hash.put("PTTYPE",ptType);
		hash.put("PLANTYPE",planType);
		hash.put("ASSEMBLING",assembling);
		hash.put("DEVELOPTYPE",developType);
		hash.put("MAKETYPE",makeType);
		hash.put("PRODUCTTYPE",productType);
		TemplateProject templateProject = null;
		try {
			templateProject = E3PSProjectHelper.service.createTemplateProject(hash);
		}
		catch(Exception e) {
		    Kogger.error(e);
		}

		if(templateProject != null) {
			message = "true";
%>
			<tempid>
				<l_tempid><![CDATA[<%=templateProject.getPersistInfo().getObjectIdentifier().getStringValue()%>]]></l_tempid>
			</tempid>
<%
		}
	}
	else if("modify".equals(command)) {//Project Template 정보 수정
		String oid = request.getParameter("oid");
		if(oid == null) {
			oid = "";
		}

		oid= java.net.URLDecoder.decode(oid,"euc-kr");
		try {
			HashMap map = new HashMap();
			map.put("name", name);
			map.put("number", pjtNo);
			map.put("description", description);
			map.put("oid", oid);

			TemplateProject templateProject = ProjectHelper.updateTemplateProjectInfo(map);
			if(templateProject != null) {
				message = "true";
%>
				<tempid>
					<l_tempid><![CDATA[<%=templateProject.getPersistInfo().getObjectIdentifier().getStringValue()%>]]></l_tempid>
				</tempid>

<%
			}
		}
		catch(Exception e) {
		    Kogger.error(e);
		}

	}
	else if("addwbs".equals(command)) {//WBS 추가
		String oid = request.getParameter("oid");
		String wbsArr = request.getParameter("wbs");

		oid = java.net.URLDecoder.decode(oid,"euc-kr");
		wbsArr = java.net.URLDecoder.decode(wbsArr,"euc-kr");

		StringTokenizer tokens = new StringTokenizer(wbsArr, ",");

		ArrayList wbsList = new ArrayList();
		while(tokens.hasMoreTokens()) {
			wbsList.add(tokens.nextToken());
		}

		HashMap map = new HashMap();
		map.put("oid", oid);
		map.put("wbs", wbsList);
		boolean flag = false;
		if(flag) {
			message = "true";
		}
	}
	else if("deletetask".equals(command)) {//Task 삭제
		String taskOid = request.getParameter("taskOid");
		taskOid = java.net.URLDecoder.decode(taskOid,"euc-kr");
		try {
			ReferenceFactory rf = new ReferenceFactory();
			TemplateTask task = (TemplateTask)rf.getReference(taskOid).getObject();
			boolean flag = false;//WBSItemHelper.deleteTaskFromTemplate(task);
			if(flag) {
				message = "true";
			}
		}
		catch(Exception e) {
		    Kogger.error(e);
		}
	}
	else if("addtask".equals(command)) {//Task 추가
		String oid = request.getParameter("oid");
		String childOid = request.getParameter("childOid");
		oid = java.net.URLDecoder.decode(oid,"euc-kr");
		childOid = java.net.URLDecoder.decode(childOid,"euc-kr");
		
		HashMap map = new HashMap();
		map.put("name", name);
		map.put("duration", duration);
		map.put("description", description);
		map.put("oid", oid);
		map.put("childOid", childOid);

		try {
			TemplateTask task =  null;//WBSItemHelper.addTaskToTaskTemplate(map);
			if(task != null) {
				message = "true";
			}
		}
		catch(Exception e) {
		    Kogger.error(e);
		}
	}
	else if("modifytask".equals(command)) {//Task 정보 수정
		String oid = request.getParameter("oid");
		oid = java.net.URLDecoder.decode(oid,"euc-kr");
		
		HashMap map = new HashMap();
		map.put("name", name);
		map.put("duration", duration);
		map.put("description", description);
		map.put("childOid", oid);

		try {
			TemplateTask task = null;//WBSItemHelper.addTaskToTaskTemplate(map);
			if(task != null) {
				message = "true";
			}
		}
		catch(Exception e) {
		    Kogger.error(e);
		}
	}
	else if("addpretask".equals(command)) {//선행 Task 추가
		String oid = request.getParameter("oid");
		String preTaskOidArr = request.getParameter("preTaskOid");
		oid = java.net.URLDecoder.decode(oid,"euc-kr");
		preTaskOidArr = java.net.URLDecoder.decode(preTaskOidArr,"euc-kr");
		
		StringTokenizer tokens = new StringTokenizer(preTaskOidArr, ",");

		ArrayList taskList = new ArrayList();
		while(tokens.hasMoreTokens()) {
			taskList.add(tokens.nextToken());
		}

		HashMap map = new HashMap();
		map.put("oid", oid);
		map.put("preTask", taskList);
		boolean flag = false;//WBSItemHelper.addPreTaskTemplate(map);
		if(flag) {
			message = "true";
		}
	}
	else if("deletepretask".equals(command)) {//선행 Task 삭제
		String oid = request.getParameter("oid");
		String linkOid = request.getParameter("linkOid");
		oid = java.net.URLDecoder.decode(oid,"euc-kr");
		linkOid = java.net.URLDecoder.decode(linkOid,"euc-kr");

		ArrayList taskList = new ArrayList();
		taskList.add(linkOid);
		
		HashMap map = new HashMap();
		map.put("oid", oid);
		map.put("preTask", taskList);
		boolean flag = false;//WBSItemHelper.deletePreTaskTemplate(map);
		if(flag) {
			message = "true";
		}
	}
	else if("addTaskOutput".equals(command)) {//산출물 정의 추가
		String oid = request.getParameter("oid");//ProjectOutput OID
		String taskOid = request.getParameter("taskOid");
		String docTypeOid = request.getParameter("docTypeOid");
		name = request.getParameter("name");
		description = request.getParameter("description");
		String role = request.getParameter("role");	

		String userOids[] = request.getParameterValues("userOid");
		String deptOids[] = request.getParameterValues("deptOid");
		
		if(oid == null) {
			oid = "";
		}

		if(taskOid == null) {
			taskOid = "";
		}

		if(name == null) {
			name = "";
		}

		if(description == null) {
			description = "";
		}

		if(docTypeOid == null) {
			docTypeOid = "";
		}

		if(role == null) {
			role = "";
		}

		oid = java.net.URLDecoder.decode(oid,"euc-kr");
		taskOid = java.net.URLDecoder.decode(taskOid,"euc-kr");
		docTypeOid = java.net.URLDecoder.decode(docTypeOid,"euc-kr");
		name = java.net.URLDecoder.decode(name,"euc-kr");
		description = java.net.URLDecoder.decode(description,"euc-kr");
		role = java.net.URLDecoder.decode(role,"euc-kr");


		HashMap map = new HashMap();
		map.put("oid", oid);
		map.put("taskOid", taskOid);
		map.put("name", name);
		map.put("description", description);
		map.put("docTypeOid", docTypeOid);
		map.put("role", role);
		if(userOids != null && userOids.length > 0) {
			map.put("userOid", userOids);
		}

		if(deptOids != null && deptOids.length > 0) {
			map.put("deptOid", deptOids);
		}
		

		ProjectOutput output = null;
		try {
			output = null;//WBSItemHelper.saveDefProjectOutput(map);
		}
		catch(Exception e) {
		    Kogger.error(e);
			output = null;
		}
		if(output != null) {
			message = "true";
		}
	}
	else if("deleteTaskOutput".equals(command)) {//산출물 정의 삭제
		String oid = request.getParameter("oid");
		try {
			ReferenceFactory rf = new ReferenceFactory();
			ProjectOutput output = (ProjectOutput)rf.getReference(oid).getObject();
			ProjectOutputHelper.manager.deleteProjectOutput(output);
		}
		catch(Exception e) {
		    Kogger.error(e);
			message = "false";
			
		}
		message = "true";
	}

	//### 추가, 삭제 후 리스트 가져오기. begin
	if( "addwbs".equals(command) || "deletetask".equals(command)
			|| "addtask".equals(command)) {

		String oid = request.getParameter("oid");
		oid = java.net.URLDecoder.decode(oid,"euc-kr");
		
		QueryResult qr = null;
		try {
			ReferenceFactory rf = new ReferenceFactory();
			Object obj = rf.getReference(oid).getObject();
			
			if (obj instanceof TemplateProject)
			{
				qr = ProjectTaskHelper.manager.getChild((TemplateProject)obj);
			}
			else if (obj instanceof TemplateTask)
			{
				qr = ProjectTaskHelper.manager.getChild((TemplateTask)obj);
			}
			
		}
		catch(Exception e) {
		    Kogger.error(e);
		}
%>
			<data_info>
<%
		if(qr != null) {
			TemplateTaskData data = null;
			
			String taskName = "";
			String taskDesc = "";
			String taskOid = "";

			int seq = 0;
			int taskDuration = 0;			

			Object obj[] = null;
			while(qr.hasMoreElements()) {
				obj = (Object[])qr.nextElement();
				data = new TemplateTaskData((TemplateTask)obj[0]);

				seq = data.seq;
				taskName = data.name;
				taskDuration = data.duration;
				taskDesc = data.description;
				taskOid = data.taskOID;
%>
			
				<l_seq><![CDATA[<%=seq%>]]></l_seq>
				<l_name><![CDATA[<%=taskName%>]]></l_name>
				<l_duration><![CDATA[<%=taskDuration%>]]></l_duration>
				<l_description><![CDATA[<%=taskDesc%>]]></l_description>
				<l_oid><![CDATA[<%=taskOid%>]]></l_oid>
<%
			}
		}
%>
			</data_info>
<%
	}
	//### 추가, 삭제 후 리스트 가져오기. end

	//선행Task 추가, 삭제 후 리스트 가져오기. begin
	if( "addpretask".equals(command) || "deletepretask".equals(command)) {
		String oid = request.getParameter("oid");
		oid = java.net.URLDecoder.decode(oid,"euc-kr");

		ReferenceFactory rf = new ReferenceFactory();
		TemplateTask task = (TemplateTask)rf.getReference(oid).getObject();
		QueryResult dependList = TaskDependencyHelper.manager.getDependTaskList(task);
%>

			<data_info>
<%
		if(dependList != null) {
			String linkOid = "";
			String taskOID = "";
			String taskName = "";
			String taskDuration = "";
			String taskUserStr = "";
			String taskCompletion = "";

			TaskDependencyLink link = null;
			TemplateTaskData dependData = null;
			while ( dependList.hasMoreElements() ) {
				link = (TaskDependencyLink)dependList.nextElement();
				linkOid = link.getPersistInfo().getObjectIdentifier().getStringValue();
				taskOID = "";
				taskName = "";
				taskDuration = "";
				taskUserStr = "";
				taskCompletion = "";
				
				dependData = new TemplateTaskData(link.getDependTarget());
				taskOID = dependData.taskOID;
				taskName = dependData.name;
				taskDuration = dependData.duration+"";
				taskUserStr = dependData.getManagerStr();
%>
				<l_name><![CDATA[<%=taskName%>]]></l_name>
				<l_duration><![CDATA[<%=taskDuration%>]]></l_duration>
				<l_chief><![CDATA[<%=taskUserStr%>]]></l_chief>
				<l_oid><![CDATA[<%=taskOID%>]]></l_oid>
				<l_linkoid><![CDATA[<%=linkOid%>]]></l_linkoid>

<%
			}
		}
%>
			</data_info>

<%
	}
	//선행Task 추가, 삭제 후 리스트 가져오기. end

	//산출물 정의 추가, 삭제 후 리스트 가져오기. begin
	if( "addTaskOutput".equals(command) || "deleteTaskOutput".equals(command)) {
		String taskOid = request.getParameter("taskOid");
		taskOid = java.net.URLDecoder.decode(taskOid,"euc-kr");

		ReferenceFactory rf = new ReferenceFactory();
		TemplateTask task = (TemplateTask)rf.getReference(taskOid).getObject();
		QueryResult result = ProjectOutputHelper.manager.getTaskOutput(task);
		if(result != null & result.size() > 0) {
%>

			<data_info>
<%
			ProjectOutput output = null;
			ProjectOutputData data = null;
			int output_idx = 0;
			Object objArr[] = null;
			while(result.hasMoreElements()) {
				objArr = (Object[])result.nextElement();
				output = (ProjectOutput)objArr[0];
				data = new ProjectOutputData(output);
%>
				<l_name><![CDATA[<%=data.name%>]]></l_name>
				<l_location><![CDATA[<%=data.locationStr%>]]></l_location>
				<l_role><![CDATA[<%=data.role_ko%>]]></l_role>
				<l_oid><![CDATA[<%=data.oid%>]]></l_oid>	
<%
			}
%>
			</data_info>
<%
		}

	}
	//산출물 정의 추가, 삭제 후 리스트 가져오기. end



%>

			<message>
				<l_message><![CDATA[<%=message%>]]></l_message>
			</message>
		</contents>
	</results>
</stdinfo>
