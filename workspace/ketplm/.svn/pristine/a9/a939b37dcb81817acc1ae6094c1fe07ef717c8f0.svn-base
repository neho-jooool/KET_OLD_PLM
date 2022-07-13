<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="wt.fc.*" %>
<%@page import = "e3ps.project.*,
				  e3ps.project.beans.*,
				  e3ps.common.util.*,
				  java.util.*,
				  e3ps.common.web.*"%>
				  
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>				  

<%
   
	Hashtable paramHash =WebUtil.getHttpParamsFromAjax(request);


	String type = StringUtil.checkNull((String)paramHash.get("command"));
	String coid = StringUtil.checkNull((String)paramHash.get("coid"));
	Kogger.debug(coid);
	Object changeObj= CommonUtil.getObject(coid);

	TemplateTask changeTask = null;
	
	if(changeObj != null){	
		changeTask = (TemplateTask)changeObj;	
		TemplateTask upDownTask = null;
		
		if(type.equals("up")){
			upDownTask = (TemplateTask)ProjectTaskHelper.getUpMaxTask(changeTask);
			
		}else{
			upDownTask = (TemplateTask)ProjectTaskHelper.getDownMinTask(changeTask);
	    }
		
		if(upDownTask != null){
		
			int changeSeq = upDownTask.getTaskSeq();
			upDownTask.setTaskSeq(changeTask.getTaskSeq());
			
			upDownTask = (TemplateTask)PersistenceHelper.manager.modify(upDownTask);
			
			changeTask.setTaskSeq(changeSeq);
			changeTask = (TemplateTask)PersistenceHelper.manager.modify(changeTask);
		}
		
	}
	

	
	
	%>




