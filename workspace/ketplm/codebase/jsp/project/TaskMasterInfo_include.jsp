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
    String functionName = "addMasterUseTemplateTask";
    String viewName = "TemplateTaskView";
    boolean isPM = false;

    boolean isAdmin = CommonUtil.isAdmin();
    String oid = StringUtil.checkNull(request.getParameter("oid"));
    TemplateTask task = (TemplateTask)CommonUtil.getObject(oid);
    TemplateProject project = task.getProject();
    String projectOID = CommonUtil.getOIDString(project);
    String addMaster = null;
    String deleteMaster = null;
    TaskViewButtonAuth buttonAuth = null;

    boolean isRole = false;

    if ( task instanceof E3PSTask ) {  // 실 프로젝트인 경우
        isTemplete = false;
        functionName = "addMasterUseTask";
        viewName = "TaskView";

        //E3PSTaskData taskData = new E3PSTaskData((E3PSTask)task);

        buttonAuth = new TaskViewButtonAuth((E3PSTask)task);
        //E3PSTaskAccessData accessData = taskData.getAccessState();

        isRole = (task.getPersonRole() != null && task.getPersonRole().length() > 0);

    } else {  // 템플릿 프로젝트인 경우
        canDelete = isAdmin;
        canManage = isAdmin;
    }

    try {
        deleteMaster = request.getParameter("deleteMaster");
        if ( deleteMaster.length() > 0 && deleteMaster != null  ) {
            //Kogger.debug("delete Master.........................");
            WTUser user = (WTUser)CommonUtil.getObject(deleteMaster);
            if ( user != null ) TaskUserHelper.manager.deleteTaskMaster(task, user);
        }

        addMaster = request.getParameter("addMaster");
        if ( addMaster.length() > 0 && addMaster != null ) {
            Kogger.debug("add Master");
            WTUser user = (WTUser)CommonUtil.getObject(addMaster);

            if ( user != null ) TaskUserHelper.manager.setMaster(task,user);
        }
    } catch (Exception ex) {
	Kogger.error(ex);
    }
%>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="JavaScript">
<!--
/*

function addMaster() {


    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=s";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
        return;
    }

    onProgressBar();

    var param = "";

    for(var i = 0; i < attacheMembers.length; i++) {
        param += "addMaster=" + encodeURIComponent(attacheMembers[i][0]);
    }



    document.forms[0].method="post";
    document.forms[0].action = "/plm/jsp/project/TaskView.jsp?"+param;
    document.forms[0].submit();

}

*/

// [PLM System 1차개선] Task 참여자 추가 시 Project 전체 인원 목록에서 선택하도록 listType=all 추가(기존 : Project 참여 인원), 2013-08-07, BoLee
function addMaster(){
    var url = "/plm/jsp/project/SelectProjectPeopleList.jsp?oid=<%=projectOID%>&listType=all&mode=s&function=<%=functionName%>";
    openSameName(url,"500","500","status=no,scrollbars=yes,resizable=no");
}

function deleteMaster(v){
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03340") %><%--책임자를 삭제 합니다.\n삭제하시겠습니까?--%>") ) {
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
    QueryResult masterList = TaskUserHelper.manager.getMaster(task);
    //Kogger.debug(">>>>>> e333333333 : " + masterList.size());

    if(masterList != null) {

    while ( masterList.hasMoreElements() ) {
        Object o[]=(Object[])masterList.nextElement();
        TaskMemberLink link = (TaskMemberLink)o[0];
        PeopleData data = new PeopleData(link.getMember().getMember());
        String wtuserOID = CommonUtil.getOIDString(data.wtuser);
        String peopleOID = CommonUtil.getOIDString(data.people);
%>
            &nbsp;<a href="JavaScript:viewPeople('<%=peopleOID%>')"><%=data.name%></a>
            /&nbsp;<%=data.departmentName%>
        <%if(!isRole && buttonAuth != null && (buttonAuth.isTaskMasterModify || isAdmin)){%>
            <a href="JavaScript:deleteMaster('<%=wtuserOID%>')"><img src="/plm/portal/images/table_close.png" width="13" height="12" border="0"></a>
        <%}%>
<%  } %>
<%  } %>
        </td>
<%
    if(!isRole && buttonAuth != null && (buttonAuth.isTaskMasterModify || isAdmin)){
    %>
<!--        <td align="right"> -->
<!--            <table border="0" cellspacing="0" cellpadding="0"> -->
<!--                <tr> -->
<!--                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td> -->
<%--                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:addMaster();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %>수정</a></td> --%>
<!--                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td> -->
<!--                </tr> -->
<!--             </table> -->
<!--         </td> -->
<%  }  %>
    </tr>
</table>