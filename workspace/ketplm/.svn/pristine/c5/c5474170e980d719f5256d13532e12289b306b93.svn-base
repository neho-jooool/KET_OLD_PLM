<%@page import="e3ps.project.issue.Issue"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="wt.fc.*" %>
<%@page import = "e3ps.project.*,
                  e3ps.project.beans.*,
                  e3ps.common.util.*,
                  java.util.*,
                  e3ps.common.web.*"%>
<%@page import="e3ps.project.issue.IssueHelper"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%
    Hashtable paramHash =WebUtil.getHttpParamsFromAjax(request);

//     title        이슈제목
//     disposer      처리 처리자
//     planCompleteDate  처리 요청일자
//     description      상세 내용
//     EcrLinkOid      ECR
//    handlingcontents  처리 내용

        String cmd = StringUtil.checkNull((String)paramHash.get("cmd"));

        String EcrLinkOid = "";
        String[] EcrLinkOids = {};


        if(paramHash.get("EcrLinkOid") instanceof String){
            EcrLinkOid = StringUtil.checkNull( (String)paramHash.get("EcrLinkOid"));
        }else if(paramHash.get("EcrLinkOid") instanceof String[]){
            EcrLinkOids =  (String[])paramHash.get("EcrLinkOid");
        }

        // 등록
        String title = StringUtil.checkNull( (String)paramHash.get("title"));
        String disposer = StringUtil.checkNull( (String)paramHash.get("disposer"));
        String planCompleteDate = StringUtil.checkNull( (String)paramHash.get("planCompleteDate"));
        String description = StringUtil.checkNull( (String)paramHash.get("description"));
        String taskOid = StringUtil.checkNull( (String)paramHash.get("taskOid"));


        // 처리
        String issueOid = StringUtil.checkNull( (String)paramHash.get("issueOid"));
        String handlingcontents = StringUtil.checkNull( (String)paramHash.get("handlingcontents"));

        Kogger.debug("cmd ==> " + cmd);
        Kogger.debug("disposer ==> " + disposer);


        if(EcrLinkOid != null) {
            Kogger.debug("EcrLinkOid ==> " + EcrLinkOid);
        }

        Hashtable hash = null;

        boolean isIssue = false;
        if(cmd.equals("create")){
            hash = new Hashtable();
            hash.put("cmd", cmd);
            hash.put("taskOid", taskOid);
            hash.put("title", title);
            hash.put("disposer", disposer);
            hash.put("planCompleteDate", planCompleteDate);
            hash.put("description", description);


            if(paramHash.get("EcrLinkOid") instanceof String){
                if(!EcrLinkOid.equals("")) {
                hash.put("EcrLinkOid", EcrLinkOid);
                }
            }else{
                hash.put("EcrLinkOid", EcrLinkOids);
            }


            try{
                isIssue  = KETIssueHelper.issueCreate(hash);
            }catch(Exception e){
        	Kogger.error(e);
            }
            if(isIssue){
%>
            alert("<%=messageService.getString("e3ps.message.ket_message", "01315")%><%--등록 되었습니다--%>");
            opener.document.forms[0].method="post";
            opener.document.forms[0].action = "/plm/jsp/project/TaskView.jsp?oid=<%=taskOid%>";
            opener.document.forms[0].submit();
            self.close();
<%
    }
        }
%>
<%
    if(cmd.equals("modifyIssue")){
            hash = new Hashtable();

            hash.put("cmd", cmd);
            hash.put("title", title);
            hash.put("disposer", disposer);
            hash.put("planCompleteDate", planCompleteDate);
            hash.put("description", description);
            hash.put("issueOid", issueOid);

            if(paramHash.get("EcrLinkOid") instanceof String){
                if(!EcrLinkOid.equals("")) {
                hash.put("EcrLinkOid", EcrLinkOid);
                }
            }else{
                hash.put("EcrLinkOid", EcrLinkOids);
            }
            try{
                isIssue  = KETIssueHelper.issueCreate(hash);
            }catch(Exception e){
        	Kogger.error(e);
            }
            if(isIssue){
%>
            alert("<%=messageService.getString("e3ps.message.ket_message", "01940")%><%--수정 되었습니다--%>");
            opener.document.forms[0].method="post";
            opener.document.forms[0].action = "/plm/jsp/project/TaskView.jsp?oid=<%=taskOid%>";
            opener.document.forms[0].submit();
            self.close();
        <%
    }
        }
%>
<%
    if(cmd.equals("modify")){
            hash = new Hashtable();
            hash.put("cmd", cmd);
            hash.put("issueOid", issueOid);
            hash.put("handlingcontents", handlingcontents);

            if(paramHash.get("EcrLinkOid") instanceof String){
                if(!EcrLinkOid.equals("")) {
                    hash.put("EcrLinkOid", EcrLinkOid);
                    }
            }else{
                hash.put("EcrLinkOid", EcrLinkOids);
            }
            try{
                isIssue  = KETIssueHelper.issueCreate(hash);
            }catch(Exception e){
        	Kogger.error(e);
            }
            if(isIssue){
%>
            alert("<%=messageService.getString("e3ps.message.ket_message", "02776")%><%--처리 되었습니다--%>");
            opener.document.forms[0].method="post";
            opener.document.forms[0].action = "/plm/jsp/project/TaskView.jsp?oid=<%=taskOid%>";
            opener.document.forms[0].submit();
            self.close();
<%
    }
        }
%>
<%
    if(cmd.equals("delete")){

            try{
                isIssue  = KETIssueHelper.issueDelete(issueOid);
            }catch(Exception e){
        	Kogger.error(e);
            }
            if(isIssue){
%>
            alert("<%=messageService.getString("e3ps.message.ket_message", "01692") %><%--삭제 되었습니다--%>");
            document.forms[0].method="post";
            document.forms[0].action = "/plm/jsp/project/TaskView.jsp?oid=<%=taskOid%>";
            document.forms[0].submit();
<%
            }
        }
%>








