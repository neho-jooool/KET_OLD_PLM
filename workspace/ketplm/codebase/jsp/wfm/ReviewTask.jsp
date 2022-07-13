<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*" %>
<%@page import = "java.net.URL" %>
<%@page import = "java.sql.Timestamp" %>
<%@page import = "e3ps.wfm.entity.KETWfmApprovalMaster" %>
<%@page import = "e3ps.common.util.*" %>
<%@page import = "e3ps.common.web.*" %>
<%@page import = "e3ps.groupware.company.*" %>
<%@page import = "e3ps.wfm.util.*" %>
<%@page import = "e3ps.project.E3PSProject" %>
<%@page import = "e3ps.project.Performance" %>
<%@page import = "e3ps.common.content.ContentUtil" %>
<%@page import = "e3ps.common.content.ContentInfo" %>
<%@page import = "e3ps.dms.beans.DMSUtil" %>
<%@page import = "e3ps.dms.entity.*" %>
<%@page import = "wt.workflow.work.WfAssignedActivity" %>
<%@page import = "wt.workflow.work.*" %>
<%@page import = "wt.workflow.engine.*" %>
<%@page import = "wt.workflow.WfException" %>
<%@page import = "wt.lifecycle.*" %>
<%@page import = "wt.content.*" %>
<%@page import = "wt.epm.*" %>
<%@page import = "wt.fc.*" %>
<%@page import = "wt.vc.*" %>
<%@page import = "wt.session.*" %>
<%@page import = "wt.org.*" %>
<%@page import = "wt.query.*" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
  String workOID = request.getParameter("oid");
  boolean isExistWorkItem = false;
  String url = "/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr";
  if(CommonUtil.getObject(workOID)!=null){
    WorkItem item = (WorkItem)CommonUtil.getObject(workOID);
    String status = item.getStatus().toString();
    if(status.equals("POTENTIAL")){
      isExistWorkItem = true;
      url = "/plm/jsp/wfm/WfTask.jsp?oid="+workOID;
    }
  }
%>

<SCRIPT LANGUAGE="JavaScript">
<!--
  var isExistWorkItem = "<%=isExistWorkItem %>";
  if(isExistWorkItem=="false"){
    alert('<%=messageService.getString("e3ps.message.ket_message", "02280") %><%--이미 결재 완료된 문서입니다--%>');
  }
  location.href = "<%=url%>";
//-->
</SCRIPT>
