<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="wt.fc.*"%>
<%@page import="e3ps.project.beans.*"%>
<%@page import="e3ps.project.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%@include file="/jsp/common/context.jsp"%>
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
    if("stateChange".equals(command)) {//stateChange
        message = "true";

        String oid = request.getParameter("oid");
        String state = request.getParameter("state");
        oid = java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");
        state = java.net.URLDecoder.decode(state==null?"":state,"euc-kr");

        wt.lifecycle.LifeCycleManaged lcm = null;

        try {
            //추후 서비스 작업으로 변경 필요....
            ReferenceFactory rf = new ReferenceFactory();
            lcm = (wt.lifecycle.LifeCycleManaged)rf.getReference(oid).getObject();

            //lcm = wt.lifecycle.LifeCycleHelper.service.setLifeCycleState(lcm, wt.lifecycle.State.toState(state), true);
            ProjectHelper.changeProjectState((E3PSProject)lcm, state);
        }
        catch(Exception e) {
            Kogger.error(e);
            message = "false";
        }
    }
%>
            <message>
                <l_message><![CDATA[<%=message%>]]></l_message>
            </message>
        </contents>
    </results>
</stdinfo>
