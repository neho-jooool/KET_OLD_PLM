<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,
                wt.fc.*,
                wt.org.*" %>
<%@page import="e3ps.groupware.company.*,
                e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*" %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
    boolean isTemplete = true;
    boolean canDelete = false;
    boolean canManage = false;
    String functionName = "addMemberUseTemplateTask";
    String viewName = "TemplateTaskView";



    boolean isAdmin = CommonUtil.isAdmin();
    String oid = StringUtil.checkNull(request.getParameter("oid"));
    TemplateTask task = (TemplateTask)CommonUtil.getObject(oid);
    TemplateProject project = task.getProject();
    String projectOID = CommonUtil.getOIDString(project);
    TaskViewButtonAuth buttonAuth = null;
    String taskType = "";

    if ( task instanceof E3PSTask ) {  // 실 프로젝트인 경우
        isTemplete = false;
        functionName = "addMemberUseTask";

        E3PSTaskData taskData = new E3PSTaskData((E3PSTask)task);
        buttonAuth = new TaskViewButtonAuth((E3PSTask)task);
        E3PSTaskAccessData accessData = taskData.getAccessState();

        taskType = (taskData!=null)?taskData.tasktype:"";
        if("Gate".equals(taskType) ) {
            viewName = "TaskGateView";
        }else if("신뢰성평가".equals(taskType) ) {
            viewName = "TaskTrustView";
        }else {
            viewName = "TaskView";
        }
        
        boolean isPM = ProjectUserHelper.manager.isPM(taskData.e3psProject);

        canDelete = (isPM || isAdmin) ;  // 프로젝트 PM 이거나 관리자 이면서 태스크가 진행중일 경우
        canManage = (isPM || isAdmin) ;  // 프로젝트 PM 이거나 관리자 이면서 태스크가 진행중일 경우



    } else {  // 템플릿 프로젝트인 경우
        canDelete = isAdmin;
        canManage = isAdmin;
    }

    try {
        String deleteMember = request.getParameter("deleteMember");
        if (!StringUtil.isEmpty(deleteMember)) {
            WTUser user = (WTUser)CommonUtil.getObject(deleteMember);
            if ( user != null ) TaskUserHelper.manager.deleteTaskMember(task, user);
        }

        String[] addMember = request.getParameterValues("addMember");
        if ( addMember != null && addMember.length > 0 ) {
            for ( int i = 0 ; i < addMember.length ; i++ ) {
                WTUser user = (WTUser)CommonUtil.getObject(addMember[i]);
                if ( user != null ) TaskUserHelper.manager.setMember(task, user);
            }
        }
    } catch (Exception ex) {
	Kogger.error(ex);
    }
%>
<script language="JavaScript">
<!--

/*
function addMember() {


    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
        return;
    }

    onProgressBar();

    var param = "";

    for(var i = 0; i < attacheMembers.length; i++) {
        param += "addMember=" + encodeURIComponent(attacheMembers[i][0]) + "&";;
    }



    document.forms[0].method="post";
    document.forms[0].action = "/plm/jsp/project/TaskView.jsp?"+param;
    document.forms[0].submit();

}
*/

function addMember(){
    var url = "/plm/jsp/project/SelectProjectPeopleList.jsp?oid=<%=projectOID%>&listType=all&mode=m&function=<%=functionName%>";
    openSameName(url,"500","500","status=no,scrollbars=yes,resizable=no");
}


function addUser() {
	var targetId = "addUser";
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m&popOn=Y&invokeMethod=popupAcceptUser";
    openOtherName(url,"selUser","820","670","status=no,scroll=no,scrollbars=yes,resizable=yes");
    /*
    acceptUsers = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if ( typeof acceptUsers == "undefined" || acceptUsers == null ) {
        return;
    }
    // isAppend : Y - 이전 값에 현재 선택된 값 추가
    // isAppend : N - 현재 선택 된 값만 추가
    var isAppend = "N";
    acceptUser(targetId, acceptUsers, isAppend);
    */
}

function popupAcceptUser(arrObj) {
	acceptUser("addUser", arrObj, "N");
}

function acceptUser(targetId, arrObj, isAppend) {
    var paramStr = "";
    var userId = new Array();     // Id
    var userName = new Array();   // Nmae
    for ( var i=0; i < arrObj.length; i++ ) {
        // [0] - wtuser oid // [1] - people oid // [2] - dept oid
        // [3] - user id    // [4] - name       // [5] - dept name
        // [6] - duty       // [7] - duty code  // [8] - email

        var infoArr = arrObj[i];
        userId[i] = infoArr[0];
        userName[i] = infoArr[4];
        paramStr = paramStr + "addMember=" + userId[i] + "&"
    }

    document.forms[0].method="post";
    <%
    if("Gate".equals(taskType) ) {
	%>
//    alert("/plm/jsp/project/TaskGateView.jsp?"+paramStr);
        document.forms[0].action = "/plm/jsp/project/TaskGateView.jsp?"+paramStr;
    <%
    }else if("신뢰성평가".equals(taskType)){
	%>
        document.forms[0].action = "/plm/jsp/project/TaskTrustView.jsp?"+paramStr;
    <%
    }else if("DR".equals(taskType)){
	    %>
	        document.forms[0].action = "/plm/jsp/project/TaskAssessView.jsp?"+paramStr;
	    <%
	}else {
	%>
        document.forms[0].action = "/plm/jsp/project/TaskView.jsp?"+paramStr;
    <%
    }
    %>
    document.forms[0].submit();
}


function deleteMember(v){
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03301") %><%--담당자를 삭제 합니다.\n삭제하시겠습니까?--%>") ) {
        document.forms[0].action = "/plm/jsp/project/<%=viewName%>.jsp?deleteMaster="+v;
        document.forms[0].method = "post";
        document.forms[0].submit();
    }
}
-->
</script>


<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td>
<%
    QueryResult memberlist = TaskUserHelper.manager.getMember(task);
    while ( memberlist.hasMoreElements() ) {
        Object o[]=(Object[])memberlist.nextElement();
        TaskMemberLink link = (TaskMemberLink)o[0];
        PeopleData data = new PeopleData(link.getMember().getMember());
        String wtuserOID = CommonUtil.getOIDString(data.wtuser);
        String peopleOID = CommonUtil.getOIDString(data.people);
%>

        &nbsp;<a href="JavaScript:viewPeople('<%=peopleOID%>')"><%=data.name%>
        /&nbsp;<%=data.departmentName%>
        <%if(buttonAuth != null && buttonAuth.isTaskMemberModify || isAdmin){%>
            <a href="JavaScript:deleteMember('<%=wtuserOID%>')"><img src="/plm/portal/images/table_close.png" width="13" height="12" border="0"></a>
        <%}%>
        &nbsp;
<%  }  %>
        </td>
<%if(buttonAuth != null && buttonAuth.isTaskMemberModify || isAdmin) { %>
	<%
	   E3PSTask taskMem = (E3PSTask)task;
	if (taskMem.getTaskState()!=ProjectStateFlag.TASK_STATE_COMPLETE){ //TASK_STATE_COMPLETE = 5;         //TASK 종료 상태
	%>
        <td align="right" width="70">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:addUser();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
            </table>
        </td>
<%      }
	} 
%>
    </tr>
</table>
