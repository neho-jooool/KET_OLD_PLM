<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@page import="java.util.*,
                wt.util.*" %>
<%@page import="e3ps.common.util.*,
                e3ps.project.*,
                e3ps.project.beans.*,
                wt.lifecycle.*,
                wt.project.*" %>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>                
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="request"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>"/>
<%
    String oid = request.getParameter("oid");
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    //ProjectHelper.manager.completeProject(project,true);

    String isComplete = request.getParameter("isComplete");

    //ADD - Start (20091123)
    String pjtOrderName = request.getParameter("pjtOrderName");
    Kogger.debug("pjtordername==>"+pjtOrderName);
    if(StringUtil.checkString(pjtOrderName)) {
        project.setOrderName(pjtOrderName);

        project = (E3PSProject) PersistenceHelper.manager.save(project);
    }
    //ADD - End (20091123)

    LifeCycleHelper.service.setLifeCycleState(project, State.toState("COMPLETED"), true);

%>
<%@page import="wt.fc.PersistenceHelper"%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "03097") %><%--프로젝트 종료중--%></title>
</head>
<body onLoad="javascript:goPage()">
<form method="post">
</form>
</body>
</html>
<script language="JavaScript">
<!--
function goPage() {
    opener.opener.document.forms[0].action = "/plm/jsp/project/ProjectView.jsp?oid=<%=oid%>";
    opener.opener.document.forms[0].method = "post";
    opener.opener.document.forms[0].submit();
    //opener.opener.parent.tree.location.reload();
    opener.close();
    self.close();
}
//-->
</script>
