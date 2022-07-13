<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,
                wt.fc.*,
                wt.org.*" %>
<%@page import="e3ps.groupware.company.*,
                e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*,
                wt.project.*" %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
    boolean isTemplate = true;
    boolean canDelete = false;
    boolean canManage = false;
    boolean isEndTask = false;
    String functionName = "addTaskUseTemplateTask";
    String viewName = "/template/TemplateTaskView";
    String viewTreeName = "/template/TemplateProjectViewLeftFrm";
    String history = request.getParameter("isHistory");

    boolean isAdmin = CommonUtil.isAdmin();
    String oid = StringUtil.checkNull(request.getParameter("oid"));

    TemplateTask task = (TemplateTask)CommonUtil.getObject(oid);

    TemplateProject template = (TemplateProject)task.getProject();

    boolean isInWork = template.getLifeCycleState() == State.INWORK;
    boolean isReWork =  template.getLifeCycleState() == State.toState("REWORK");
    boolean isCheckOut = template.isCheckOut();
    boolean isLatest = template.isLastest();
    boolean isWorkingCopy = template.isWorkingCopy();
    boolean isModify = ((isWorkingCopy || !isCheckOut) && (isInWork || isReWork));


    boolean isDependency = false;
    boolean isNotPlan = false;

    //boolean isErp = wbsItem.isIsERP();

    TaskViewButtonAuth buttonAuth = null;
    if ( task instanceof E3PSTask ) {  // 실 프로젝트인 경우
        isTemplate = false;
        functionName = "addTaskUseTask";
        viewName = "TaskView";
        viewTreeName = "ProjectViewLeftFrm";

        E3PSTaskData taskData = new E3PSTaskData((E3PSTask)task);
         buttonAuth = new TaskViewButtonAuth((E3PSTask)task);
        E3PSTaskAccessData accessData = taskData.getAccessState();
        boolean isPM = ProjectUserHelper.manager.isPM((E3PSProject)task.getProject());

        canDelete = ((isModify && isPM) || isAdmin);  // accessData.isComplete(); 프로젝트 PM 이거나 관리자 이면서 태스크가 종료가 아닌 경우
        canManage = ((isModify && isPM) || isAdmin);  // 프로젝트 PM 이거나 관리자 이면서 태스크가 종료가 아닌 경우

        if(!ProjectTaskHelper.isChild(task)){
            isEndTask = true;
        }
    } else {  // 템플릿 프로젝트인 경우

        TemplateProject project = (TemplateProject)task.getProject();
        String state = project.getLifeCycleState().toString();

        isDependency = (state.equals("INWORK") || state.equals("REWORK"));

        canDelete = isLatest && (isInWork || isReWork) || isAdmin;
        canManage = isLatest && (isInWork || isReWork) || isAdmin;

        if(!ProjectTaskHelper.isChild(task)){
            isEndTask = true;
        }
    }


    try {
        String addTask = request.getParameter("addTask");
        if ( addTask != null && addTask.length() > 0) {
            TemplateTask dependTask = (TemplateTask)CommonUtil.getObject(addTask);
            if ( dependTask != null ) TaskDependencyHelper.manager.setDependTask(task, dependTask);
        }

        String deleteTask = request.getParameter("deleteTask");
        if ( deleteTask != null && deleteTask.length() > 0) {
            TaskDependencyLink link = (TaskDependencyLink)CommonUtil.getObject(deleteTask);
            if ( link != null ) TaskDependencyHelper.manager.deleteDependTask(task,link);
        }
    } catch (Exception ex) {
	Kogger.error(ex);
    }
%>
<%@page import="wt.lifecycle.State"%>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="JavaScript">
<!--

function addTask(){
    var url="/plm/jsp/project/SelectProjectTaskList.jsp?oid=<%=oid%>&mode=m&function=<%=functionName%>";
    openSameName(url,"550","500","status=no,scrollbars=yes,resizable=no");
}

function viewTask(v){
    //document.forms[0].oid.value = v;
    //document.forms[0].action = "/plm/jsp/project/<%=viewName%>.jsp";
    //document.forms[0].method = "post";
    //document.forms[0].submit();
    parent.movePaage('/plm/jsp/project/<%=viewTreeName%>.jsp?oid=' + v, '/plm/jsp/project/<%=viewName%>.jsp?oid=' + v);
}

function deleteTask(v){
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "01848") %><%--선행 Task를 삭제 하시겠습니까?--%>") ) {
        document.forms[0].action = "/plm/jsp/project/<%=viewName%>.jsp?deleteTask="+v;
        document.forms[0].method = "post";
        document.forms[0].submit();
    }
}

function modifyLink(){

    document.forms[0].action = "/plm/jsp/project/<%=viewName%>.jsp";
    document.forms[0].cmd.value = "linkModify";
    document.forms[0].method= "post";
    document.forms[0].submit();
}

-->
</script>

<div style="width=99%;overflow-x:hidden;border:0px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">

<table border="0" cellspacing="0" cellpadding="0" width="90%">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="90%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                <td  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                <td  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02773") %><%--책임자--%></td>
              </tr>
<%
    QueryResult dependList = TaskDependencyHelper.manager.getDependTaskList1(task);
    if(dependList != null) {
        while ( dependList.hasMoreElements() ) {
            TaskDependencyLink link = (TaskDependencyLink)dependList.nextElement();
            String linkOid = CommonUtil.getOIDString(link);

            int delayDuration = link.getDelayDuration();
            long loid = CommonUtil.getOIDLongValue(linkOid);
            String taskOID = "";
            String taskName = "";
            String taskDuration = "";
            String taskUserStr = "";
            String taskCompletion = "";
            if ( isTemplate ) {
                TemplateTaskData dependData = new TemplateTaskData(link.getDependSource());
                taskOID = dependData.taskOID;
                taskName = dependData.name;
                taskDuration = dependData.duration+"";
                taskUserStr = dependData.getManagerStr();
            } else {
                E3PSTaskData dependData = new E3PSTaskData((E3PSTask)link.getDependSource());
                taskOID = dependData.e3psTaskOID;
                taskName = dependData.taskName;
                taskDuration = dependData.taskDuration + "";
                taskUserStr = dependData.getTaskUserStr();
                taskCompletion = dependData.taskCompletion + "";
            }
            //out.println("taskDuration ==> " + taskDuration);
%>
              <tr>
                <!-- <td width="250" class="tdwhiteM"><a href="JavaScript:viewTask('<%=taskOID%>')"><%=taskName%></a></td> -->
                <td  class="tdwhiteM"><a href="JavaScript:viewTask('<%=taskOID%>')"><%=taskName%></a></td>
                <td  class="tdwhiteM"><%=taskDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td  class="tdwhiteM"><%=taskUserStr%></td>
              </tr>

<%    }  %>
<%  } %>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="90%">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>



</div>
