<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="e3ps.common.content.fileuploader.FormUploader"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.content.uploader.WBFile"%>
<%@page import="java.io.File"%>
<%@page import="jxl.Workbook"%>
<%@page import="jxl.Sheet"%>
<%@page import="jxl.Cell"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="e3ps.groupware.company.People"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page import="wt.team.TeamTemplate"%>
<%@page import="e3ps.common.util.WCUtil"%>
<%@page import="ext.ket.shared.drm.DRMHelper"%>
<%@page import="wt.team.TeamHelper"%>
<%@page import="java.util.Collections"%>
<%@page import="com.ptc.core.ocmp.framework.RoleComparator"%>
<%@page import="java.util.Locale"%>
<%@page import="wt.project.Role"%>
<%@page import="java.util.HashMap"%>
<%@page import="e3ps.project.beans.ProjectScheduleHelper"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<%
FormUploader uploader = FormUploader.newFormUploader(request);
Hashtable param = uploader.getFormParameters();
String oid = (String)param.get("oid");
E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);

Vector v = uploader.getFiles();

WBFile fileName = null;
if(v.get(0) != null){
    fileName = (WBFile)v.get(0);
}
File file  = fileName.getFile();

if(DRMHelper.useDrm){
    file = DRMHelper.Decryptupload(file, file.getName());    
}
//Kogger.debug("file = " + file);

String flag = "";

flag = ProjectScheduleHelper.uploadProjectSchedule(project, file);


%>
<%=flag%>