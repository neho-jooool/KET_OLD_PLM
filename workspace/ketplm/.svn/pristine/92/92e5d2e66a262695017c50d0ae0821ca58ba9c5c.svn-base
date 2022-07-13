<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.ProjectTaskHelper"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.project.moldPart.beans.MoldPartHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.part.WTPart"%>
<%@page import="com.ptc.ddl.servlet.WTURLEncoder"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%

String projectOid = WTURLEncoder.decode(request.getParameter("projectOid"));
Kogger.debug("projectOid = " + projectOid);
MoldProject project = null;
if(projectOid != null && projectOid.length() > 0){
	project = (MoldProject)CommonUtil.getObject(projectOid);
}

String dieNo = project.getPjtNo();
int cha = ProjectTaskHelper.getDeBugChaSh(project);
String levelType = "";
int subCha = 0;
String title = "";
if(cha > 1){
	levelType = "디버깅";
	cha--;
	subCha = MoldPartHelper.getDebugSubCh(dieNo, cha);
    title = levelType + " " + cha;
    if(subCha > 1){
    	title += "_" + (subCha - 1);
    }
    title += "차";
    
}else{
	levelType = "금형제작";
	cha = MoldPartHelper.getTypeChaSh(dieNo, levelType);
	title = levelType;
	if(cha > 1){
	  title += " " + cha + "차";
	}
}

%>

<stdinfo>
			<results>
				<contents>
					<data_info>
					<l_message><![CDATA[<%=title%>]]></l_message>
					</data_info>
				</contents>
			</results>
</stdinfo>

