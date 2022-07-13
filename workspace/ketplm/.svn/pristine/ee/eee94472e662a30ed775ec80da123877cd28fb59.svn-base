<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*"%>
<%@page import="e3ps.groupware.company.*"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.common.util.CommonUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

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
            if("deptInfo".equals(command)) {
        %>
                <data_info>
                <%	String deptOid = request.getParameter("deptOid");
                    if(deptOid == null) {
                        deptOid = "";
                    }

                    message = "true";

                    deptOid = java.net.URLDecoder.decode(deptOid,"euc-kr");
                    ReferenceFactory rf = new ReferenceFactory();
                    Department dept = null;
                    try {
                        dept = (Department)rf.getReference(deptOid).getObject();
                    }
                    catch(Exception e) {
                	Kogger.error(e);
                        message = "false";
                    }

                    String idName = request.getParameter("idName");
                    if(idName == null) {
                        idName = "";
                    }
                    idName = java.net.URLDecoder.decode(idName,"euc-kr");
                    String chiefOid = "";
                    String chiefName = "";
                    if(dept != null) {
                        WTUser wtuser = (WTUser)PeopleHelper.manager.getChiefUser(dept);
                        if(wtuser != null){
                            chiefOid = CommonUtil.getOIDString(wtuser);
                            chiefName = wtuser.getFullName();
                        }
                %>
                        <l_name><![CDATA[<%=dept.getName()%>]]></l_name>
                        <l_code><![CDATA[<%=dept.getCode()%>]]></l_code>
                        <l_chiefName><![CDATA[<%=chiefName%>]]></l_chiefName>
                        <l_chiefOid><![CDATA[<%=chiefOid%>]]></l_chiefOid>
                        <l_oid><![CDATA[<%=dept.getPersistInfo().getObjectIdentifier().getStringValue()%>]]></l_oid>
                        <l_idname><![CDATA[<%=idName%>]]></l_idname>
                <%	} else {
                        String noExist = messageService.getString("e3ps.message.ket_message", "01549") /*부서 정보가 존재하지 않습니다\n부서정보를 확인하시기 바랍니다*/;

                %>
                        <l_noExist><![CDATA[<%=noExist%>]]></l_noExist>
                <%	} %>
                </data_info>
        <%
            }
            else if("endChildDept".equals(command)) {
        %>
                    <data_info>
        <%
                String deptOid = request.getParameter("deptOid");
                if(deptOid == null) {
                    deptOid = "";
                }
                deptOid = java.net.URLDecoder.decode(deptOid,"euc-kr");

                ReferenceFactory rf = new ReferenceFactory();
                Department dept = (Department)rf.getReference(deptOid).getObject();
                ArrayList endDepts = DepartmentHelper.manager.getChildEndNodeDept(dept);

                message = "true";

                Department endDept = null;
                for(int i = 0; i < endDepts.size(); i++) {
                    endDept = (Department)endDepts.get(i);


        %>
                        <l_name><![CDATA[<%=endDept.getName()%>]]></l_name>
                        <l_code><![CDATA[<%=endDept.getCode()%>]]></l_code>
                        <l_oid><![CDATA[<%=endDept.getPersistInfo().getObjectIdentifier().getStringValue()%>]]></l_oid>
        <%
                }
        %>
                    </data_info>
        <%
            }
        %>

            <message>
                <l_message><![CDATA[<%=message%>]]></l_message>
            </message>
        </contents>
    </results>
</stdinfo>
