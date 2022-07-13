<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "wt.org.*"%>
<%@page import="e3ps.common.util.*,
                e3ps.project.*,
                e3ps.project.beans.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script language="javascript" src="/plm/portal/js/script.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/viewObject.js"></SCRIPT>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
    String oid = request.getParameter("oid");
    String userOid = request.getParameter("userOid");
    String type = "";
    String popup = "";
    if(request.getParameter("popup") != null && request.getParameter("popup").length() > 0){
        popup = request.getParameter("popup");
    }
    //Kogger.debug("PM_popup ==== " + popup + "  oid == " + oid);
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    WTUser user = (WTUser)CommonUtil.getObject(userOid);
    ProjectUserHelper.manager.replacePM(project, user);
    Object obj = CommonUtil.getObject(oid);
    if(obj instanceof ReviewProject){
%>
        <script>
            alert("<%=messageService.getString("e3ps.message.ket_message", "00375") %><%--PM 수정 되었습니다--%>");
            //parent.document.location.href  = "/plm/jsp/project/ProjectViewFrm.jsp?oid="+"<%=oid%>";

            <%if("popup".equals(popup)){%>
                parent.movePopup('<%=oid%>', '/plm/jsp/project/ReviewProjectView.jsp?oid=<%=oid%>&radioValue=2&popup=popup');
            <%}else{%>
                parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=oid%>', '/plm/jsp/project/ReviewProjectView.jsp?oid=<%=oid%>&radioValue=2');
            <%}%>
        </script>

<%
    }else if(obj instanceof ProductProject){
%>
        <script>
            alert("<%=messageService.getString("e3ps.message.ket_message", "00375") %><%--PM 수정 되었습니다--%>");
            //parent.document.location.href  = "/plm/jsp/project/ProjectViewFrm.jsp?oid="+"<%=oid%>";

            <%if("popup".equals(popup)){%>
                parent.movePopup('<%=oid%>', '/plm/jsp/project/ProjectView2.jsp?oid=<%=oid%>&popup=popup');
            <%}else{%>
                parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=oid%>', '/plm/jsp/project/ProjectView2.jsp?oid=<%=oid%>');
            <%}%>
        </script>

<%
    }else if(obj instanceof MoldProject){
%>
        <script>
            alert("<%=messageService.getString("e3ps.message.ket_message", "00375") %><%--PM 수정 되었습니다--%>");
            //parent.document.location.href  = "/plm/jsp/project/ProjectViewFrm.jsp?oid="+"<%=oid%>";
            <%if("popup".equals(popup)){%>
                parent.movePopup('<%=oid%>', '/plm/jsp/project/MoldProjectView_4.jsp?oid=<%=oid%>&popup=popup');
            <%}else{%>
                parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=oid%>', '/plm/jsp/project/MoldProjectView_4.jsp?oid=<%=oid%>');
            <%}%>
        </script>

<%
    }
%>
</body>
</html>
