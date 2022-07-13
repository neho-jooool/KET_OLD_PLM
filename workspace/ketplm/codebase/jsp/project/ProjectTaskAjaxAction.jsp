<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*, wt.org.*,wt.query.*,wt.team.*,wt.project.Role"%>
<%@page import="e3ps.common.util.*,e3ps.groupware.company.*"%>
<%@page import="e3ps.project.*,e3ps.project.beans.*"%>
<%@include file="/jsp/common/context.jsp"%>
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
    if(command == null) {
        command = "";
    }
    command = java.net.URLDecoder.decode(command,"euc-kr");

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    //리스트 검색 .............................................................................
    if("searchTaskCompletions".equals(command)) {
        message = "true";

        String oid = request.getParameter("oid");
        oid = java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");

        int taskCompletion = 0;
        String taskPlanStartDate = "";
        String taskPlanEndDate = "";
        try {
            ReferenceFactory rf = new ReferenceFactory();
            E3PSTask task = (E3PSTask)rf.getReference(oid).getObject();
            E3PSTaskData taskData = new E3PSTaskData(task);

            taskCompletion = taskData.taskCompletion;
            taskPlanStartDate = DateUtil.getDateString(taskData.taskPlanStartDate, "D");
            taskPlanEndDate = DateUtil.getDateString(taskData.taskPlanEndDate, "D");
        }
        catch(Exception e) {
            Kogger.error(e);
            message = "false";
        }
%>
            <data_info>
                <l_taskCompletion><![CDATA[<%=taskCompletion%>]]></l_taskCompletion>
                <l_taskPlanStartDate><![CDATA[<%=taskPlanStartDate%>]]></l_taskPlanStartDate>
                <l_taskPlanEndDate><![CDATA[<%=taskPlanEndDate%>]]></l_taskPlanEndDate>
            </data_info>
<%
    }
    if("searchTemplate".equals(command)) {
        message = "true";
    }
%>



            <message>
                <l_message><![CDATA[<%=message%>]]></l_message>
            </message>
        </contents>
    </results>
</stdinfo>
