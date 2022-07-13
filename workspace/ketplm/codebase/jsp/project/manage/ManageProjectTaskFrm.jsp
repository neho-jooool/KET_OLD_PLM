<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.util.*" %>
<%@include file="/jsp/common/context.jsp" %>
<%  String oid =  StringUtil.checkNull(request.getParameter("oid"));  %>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "03054") %><%--프로젝트 Task 관리--%></title>
</head>
<!-- OLD -->
<!--frameset rows="1*" cols="200,*" border="0" frameborder="0" framespacing="0">
<frame name="tree" scrolling="no" marginwidth="0" marginheight="0" src="/plm/jsp/project/manage/ManageProjectTaskTree.jsp?oid=<%=oid%>">
<frame name="body" scrolling="auto" marginwidth="0" marginheight="0" src="/plm/jsp/project/manage/ManageProjectTaskList.jsp?oid=<%=oid%>">
</frameset-->

<!-- NEW -->
<frameset rows="50,*" framespacing="0" frameborder="no">
  <frame name="taskTop" noresize frameborder="no" scrolling="no" src="/plm/jsp/project/manage/ManageProjectTaskTop.jsp?oid=<%=oid%>">
  <frameset cols="200,*" framespacing="2px" frameborder="no">
    <frame name="tree" frameborder="no" scrolling="auto" src="/plm/jsp/project/manage/ManageProjectTaskTree.jsp?oid=<%=oid%>" style="border-right-width:3px;border-right-style:solid;border-right-color:#191970">
    <frame name="body" frameborder="no" scrolling="auto" src="/plm/jsp/project/manage/ManageProjectTaskList.jsp?oid=<%=oid%>">
  </frameset>
</frameset>

</html>
