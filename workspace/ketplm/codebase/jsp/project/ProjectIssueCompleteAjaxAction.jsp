<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="ext.ket.shared.mail.service.KETMailHelper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="wt.org.WTUser"%>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="wt.fc.*,
                        wt.httpgw.*
                        "%>
<%@page import="e3ps.common.util.*,
                        e3ps.project.*,
                        e3ps.project.beans.*
                        "%>

<%@include file="/jsp/common/context.jsp"%>

<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Collections"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<stdinfo>
    <results>
        <contents>
<%
    String message = "false";
    String l_result = "";
    String command = request.getParameter("command");
    boolean isMailSend = true;
    String encoding = "UTF-8";
    if(command == null) {
        command = "";
    }
    command = WTURLEncoder.decode(command, encoding);

    if("complete".equals(command)) {
        String oid = request.getParameter("oid");
        String complateDetail = StringUtil.checkNull( request.getParameter("complateDetail"));
        if(oid != null) {
            oid = WTURLEncoder.decode(oid, encoding);
            ProjectIssue issue = (ProjectIssue)CommonUtil.getObject(oid);
            Calendar tempCal = Calendar.getInstance();
            Kogger.debug(tempCal.getTime().getTime());
            issue.setPlanDoneDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
            issue.setIsDone(true);
            issue.setIssueAttr1(complateDetail);
            if("Y".equals(issue.getAegisTrans() ) ){
        	issue.setAegisStatus("ATTENTION1");
            }
            issue = ProjectIssueHelper.manager.modifyProjectIssue(issue);

            // 완료 메일 공지
            if(isMailSend){
                //ProjectHelper.projectSendMail(issue, "issueComplate");
                WTUser from = (WTUser) issue.getOwner().getPrincipal();
                List<WTUser> to = new ArrayList<WTUser>();
                to.add((WTUser) issue.getManager().getPrincipal());
                
                if(issue.getProject() instanceof ProductProject){
                    WTUser pm = ProjectUserHelper.manager.getPM(issue.getProject());
                    to.add(pm);
                }
                KETMailHelper.service.sendFormMail("08019", "NoticeMailLine3.html", issue, from, to);
            }

            if(issue != null) {
                message = "true";
%>
            <data_info>
                <l_oid><![CDATA[<%=issue.getPersistInfo().getObjectIdentifier().getStringValue()%>]]></l_oid>
            </data_info>
<%
            }
        }
    }else if("completeAegis".equals(command)) {
        String oid = request.getParameter("oid");
        
        if(oid != null) {
            oid = WTURLEncoder.decode(oid, encoding);
            ProjectIssue issue = (ProjectIssue)CommonUtil.getObject(oid);
            issue.setAegisStatus("NORMAL");
            issue = ProjectIssueHelper.manager.modifyProjectIssue(issue);

            if(issue != null) {
                message = "true";
%>
            <data_info>
                <l_oid><![CDATA[<%=issue.getPersistInfo().getObjectIdentifier().getStringValue()%>]]></l_oid>
            </data_info>
<%
            }
        }
    }else if("assign".equals(command)) {
        String oid = request.getParameter("oid");
        String reassign =  StringUtil.checkNull( request.getParameter("reassign"));
        if(oid != null) {
            oid = WTURLEncoder.decode(oid, encoding);

            ProjectIssue issue = (ProjectIssue)CommonUtil.getObject(oid);
            issue.setIsDone(false);
            issue.setIsAnswerDone(true);
            issue = ProjectIssueHelper.manager.modifyProjectIssue(issue);

            Vector vec = ProjectIssueHelper.manager.getIssueAnswer(issue);
            Collections.sort(vec, new IssueAnswerComparator(false));
            for(int i = 0; i < vec.size(); i++ ) {
                if(i == vec.size()-1) {
                    ProjectIssueAnswerData aData = (ProjectIssueAnswerData) vec.get(i);
                    ProjectIssueAnswer answer = aData.answer;
                    answer.setAttr1(reassign);
                    PersistenceHelper.manager.save(answer);
                }
            }
            //미완료 업무 지정 메일 공지
            if(isMailSend){
                ProjectHelper.projectSendMail(issue, "reCreate");
            }

            if(issue != null) {
                message = "true";
%>
            <data_info>
                <l_oid><![CDATA[<%=issue.getPersistInfo().getObjectIdentifier().getStringValue()%>]]></l_oid>
            </data_info>
<%
            }
        }
    }else if("complateA".equals(command)){
        String oid = request.getParameter("oid");
        if(oid != null) {
            oid = WTURLEncoder.decode(oid, encoding);
            ProjectIssue issue = null;
            ProjectIssueAnswer answer = (ProjectIssueAnswer)CommonUtil.getObject(oid);
            answer.setIsCheck(true);
            answer = (ProjectIssueAnswer)PersistenceHelper.manager.modify(answer);
            issue = ProjectIssueHelper.manager.getIssue(answer);
            issue.setIsAnswerDone(false);
            issue = ProjectIssueHelper.manager.createProjectIssue(issue);

            // 해결방안 등록 완료 메일 공지
            if(isMailSend){
                ProjectHelper.projectSendMail(issue, "answerComplate");
            }

            if(answer != null) {
                message = "true";
%>
            <data_info>
                <l_oid><![CDATA[<%=answer.getPersistInfo().getObjectIdentifier().getStringValue()%>]]></l_oid>
            </data_info>
<%
            }
        }
    }else if("etc".equals(command)){
        String oid = request.getParameter("oid");
        String etcDetail = StringUtil.checkNull( request.getParameter("etcDetail"));
        if(oid != null) {
            oid = WTURLEncoder.decode(oid, encoding);
            ProjectIssue issue = (ProjectIssue)CommonUtil.getObject(oid);
            issue.setIssueAttr2(etcDetail);
            issue = ProjectIssueHelper.manager.createProjectIssue(issue);

            if(issue != null) {
                message = "true";
%>
            <data_info>
                <l_oid><![CDATA[<%=issue.getPersistInfo().getObjectIdentifier().getStringValue()%>]]></l_oid>
            </data_info>
<%
            }
        }
    }
%>

            <message>
                <l_message><![CDATA[<%=message%>]]></l_message>
                <l_result><![CDATA[<%=l_result%>]]></l_result>
            </message>
        </contents>
    </results>
</stdinfo>
