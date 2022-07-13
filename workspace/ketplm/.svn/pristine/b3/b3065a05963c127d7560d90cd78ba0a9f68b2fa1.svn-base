<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="wt.fc.*" %>
<%@page import = "e3ps.project.*,
				  e3ps.project.beans.*,
				  e3ps.common.util.*,
				  java.util.*,
				  e3ps.common.web.*"%>

<%
		Hashtable paramHash =WebUtil.getHttpParamsFromAjax(request);
		
	
		String type = StringUtil.checkNull((String)paramHash.get("command"));
		String coid = StringUtil.checkNull((String)paramHash.get("coid"));
		Object changeObj= CommonUtil.getObject(coid);
		
		
		E3PSTask changeTask = null;
		
		if(changeObj != null){
			
			changeTask = (E3PSTask)changeObj;	
			E3PSTask upDownTask = null;
		
			if(type.equals("up")){
				upDownTask = (E3PSTask)ProjectTaskHelper.getUpMaxTask(changeTask);
			}else{
				upDownTask = (E3PSTask)ProjectTaskHelper.getDownMinTask(changeTask);
		    }
		
			if(upDownTask != null){
					int changeSeq = upDownTask.getTaskSeq();
					upDownTask.setTaskSeq(changeTask.getTaskSeq());
					upDownTask = (E3PSTask)PersistenceHelper.manager.modify(upDownTask);
					
					changeTask.setTaskSeq(changeSeq);
					changeTask = (E3PSTask)PersistenceHelper.manager.modify(changeTask);
			}
		
		}
%>





