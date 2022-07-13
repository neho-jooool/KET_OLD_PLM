<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,
                wt.fc.*,
                wt.org.*,
                wt.vc.*,
                wt.vc.wip.*,
                wt.project.*" %>
<%@page import="e3ps.doc.*,
                e3ps.doc.beans.*,
                e3ps.groupware.company.*,
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
    boolean isProject = false;
    boolean isTemplete = false;
    boolean canDelete = false;
    boolean canManage = false;
    boolean canRegistry = false;
    String viewName = "ProjectView";
    String createName = "ProjectOutputCreate";
    String updateName = "ProjectOutputUpdate";

    boolean isAdmin = false;//CommonUtil.isAdmin();
    String oid = StringUtil.checkNull(request.getParameter("oid"));
    Persistable per = CommonUtil.getObject(oid);
    TemplateProject template = null;
    if(per instanceof TemplateProject){
        template = (TemplateProject)per;
    }else{
        TemplateTask task = (TemplateTask)per;
        template = (TemplateProject)task.getProject();
    }



    boolean isInWork = template.getLifeCycleState() == State.INWORK;
    boolean isReWork =  template.getLifeCycleState() == State.toState("REWORK");
    boolean isCheckOut = template.isCheckOut();
    boolean isWorkingCopy = template.isWorkingCopy();
    boolean isModify = ((isWorkingCopy || !isCheckOut) && (isInWork || isReWork));


    if ( per instanceof TemplateProject ) {  // 프로젝트인 경우
        if ( per instanceof E3PSProject ) {  // E3PSProject 인 경우
            isProject = true;
            isTemplete = false;
            viewName = "ProjectView";
            createName = "ProjectOutputCreate";
            updateName = "ProjectOutputUpdate";

            E3PSProjectData projectData = new E3PSProjectData((E3PSProject)per);
            ProjectAccessData accessData = projectData.getAccessState();
            boolean isProjectUser = ProjectUserHelper.manager.isProjectUser((E3PSProject)per);

            canRegistry = accessData.isContinue();
            canDelete = ( isProjectUser && isModify || isAdmin ) && !accessData.isComplete();  // 관리자 또는 구성원 이면서 프로젝트가 진행중일 경우
            canManage = ( isProjectUser && isModify || isAdmin ) && !accessData.isComplete();  // 관리자 또는 구성원 이면서 프로젝트가 진행중일 경우
        } else {  // TempleteProject 인 경우
            isProject = true;
            isTemplete = true;
            viewName = "/template/TemplateProjectView";
            createName = "/template/TemplateProjectOutputCreate";
            updateName = "/template/TemplateProjectOutputUpdate";

            canDelete = isModify || isAdmin;  // 관리자일 경우
            canManage = isModify || isAdmin;  // 관리자일 경우
        }
    } else {  // 테스크인 경우
        if ( per instanceof E3PSTask ) {  // E3PSTask 인 경우
            isProject = false;
            isTemplete = false;
            viewName = "TaskView";
            createName = "ProjectOutputCreate";
            updateName = "ProjectOutputUpdate";

            E3PSTaskData taskData = new E3PSTaskData((E3PSTask)per);
            E3PSTaskAccessData accessData = taskData.getAccessState();
            boolean isTaskUser = TaskUserHelper.manager.isTaskUser((E3PSTask)per);
            boolean isPM = ProjectUserHelper.manager.isPM(taskData.E3PSProject);

            canRegistry = accessData.isContinue();
            //canDelete = ( isPM || isTaskUser || isAdmin ) && !accessData.isComplete();  // 관리자 또는 구성원 이면서 태스크가 진행중일 경우
            //canManage = ( isPM || isTaskUser || isAdmin ) && !accessData.isComplete();  // 관리자 또는 구성원 이면서 태스크가 진행중일 경우
            canDelete = ( isPM && isModify || isAdmin ) && !accessData.isComplete();  // 관리자 또는 구성원 이면서 태스크가 진행중일 경우
            canManage = ( isPM && isModify || isAdmin ) && !accessData.isComplete();  // 관리자 또는 구성원 이면서 태스크가 진행중일 경우
        } else {  // TempleteTask 인 경우
            isProject = false;
            isTemplete = true;
            viewName = "/template/TemplateTaskView";
            createName = "/template/TemplateProjectOutputCreate";
            updateName = "/template/TemplateProjectOutputUpdate";

            canDelete = isModify || isAdmin;  // 관리자일 경우
            canManage = isModify || isAdmin;  // 관리자일 경우
        }
    }

    try {
        String deleteOutput = request.getParameter("deleteOutput");
        if ( deleteOutput != null ) {
            ProjectOutput output = (ProjectOutput)CommonUtil.getObject(deleteOutput);
            if ( output != null ) ProjectOutputHelper.manager.deleteProjectOutput(output);
        }
    } catch (Exception ex) {
	Kogger.error(ex);
    }
%>
<%@page import="wt.lifecycle.State"%>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="javascript" src="/plm/portal/js/viewObject.js"></script>
<script language="JavaScript">
<!--
function addOutput(){
    var url="/plm/jsp/project/<%=createName%>.jsp?oid=<%=oid%>";
    openOtherName(url,"process","600","350","status=no,scrollbars=no,resizable=no");
}

function updateOutput(v, v1){
    var url="/plm/jsp/project/<%=updateName%>.jsp?oid="+v+"&tLocation="+v1;
    openOtherName(url,"process","800","610","status=no,scrollbars=no,resizable=no");
}

function deleteOutput(v){
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "01918") %><%--산출물을 삭제 합니다.\n등록된 산출물의 경우 프로젝트에서는 삭제되지만,\n문서관리에서 남아 있습니다.\n삭제하시겠습니까?--%>") ) {
        document.forms[0].action = "/plm/jsp/project/<%=viewName%>.jsp?deleteOutput="+v;
        document.forms[0].method = "post";
        document.forms[0].submit();
    }
}

function viewDoc( oid ) {
    var url = "/plm/jsp/doc/docView.jsp?oid="+oid;
    openOtherName(url,"viewDoc","850","600","status=no,scrollbars=yes,resizable=no");
}

function registryOutput(v){
    var url = "/plm/jsp/project/ProjectOutputRegistry.jsp?oid="+v;
    openOtherName(url,"window","850","650","status=no,scrollbars=no,resizable=no");
    //newWin = window.open(url,"window", "scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=820,height=600,resizable=no,top="+screenHeight+",left="+screenWidth);
    //newWin.resizeTo(800,600);
    //newWin.focus();
}

function registryLinkOutput(oid){
    var url = "/plm/jsp/project/ProjectOutputDocRegistryLinkForm.jsp?oid="+oid;
    newWin = window.open(url,"window", "scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=600,height=400,resizable=no,top="+screenHeight+",left="+screenWidth);
    newWin.resizeTo(700, 500);
    newWin.focus();
}
-->
</script>
<%  if(canManage) {  %>
<table width="100%" align="center">
    <tr>
        <td height="25"><input type=button class="s_font" id=innerbutton value='<%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%>' onclick="javascript:addOutput()"></td>
    </tr>
</table>
<%  } else {  %>
<br>
<%  } %>
<table width="100%" cellspacing="1" cellpadding="1" border="0" align="center" bgcolor=AABDC6>
    <tr>
        <%if(isTemplete){ %>
            <td width=50% class="tdblueM" nowrap><%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%></td>
            <td width=30% class="tdblueM" nowrap><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
            <td width=20% class="tdblueM" nowrap width=10%>Role</td>
            <%if(canDelete){%><td class="tdblueM" width=5% nowrap><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td><%}%>
        <%}else{ %>
            <td width=32% class="tdblueM" nowrap><%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%></td>
            <td width=22% class="tdblueM" nowrap><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
            <td class="tdblueM" nowrap width=10%><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
            <td class="tdblueM" nowrap width=8%><%=messageService.getString("e3ps.message.ket_message", "01200") %><%--담당--%></td>
            <td class="tdblueM" nowrap width=1%><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
            <td class="tdblueM" nowrap width=8%><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
            <td class="tdblueM" nowrap width=10%><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
            <%if(canDelete){%><td class="tdblueM" width=5% nowrap><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td><%}%>
        <%}%>
    </tr>
<%
    QueryResult qr = null;
    if ( isProject ) qr = ProjectOutputHelper.manager.getProjectOutput((TemplateProject)per);
    else qr = ProjectOutputHelper.manager.getTaskOutput((TemplateTask)per);
    if(qr != null) {
    while ( qr.hasMoreElements() ) {
        Object[] objArr = (Object[])qr.nextElement();
        ProjectOutput output = (ProjectOutput)objArr[0];
        ProjectOutputData data = new ProjectOutputData (output);
%>
    <tr onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
<%    if (isTemplete) {  %>
        <td width=50% class="tdwhiteM">&nbsp;
            <%if(canManage){%>&nbsp;<a href="javascript:updateOutput('<%=data.oid%>','<%=data.location%>');"><%=data.name%></a>
            <%}else{%>&nbsp;<%=data.name%>
            <%}%>
        </td>
        <td width=30% class="tdwhiteM">&nbsp;<%=data.locationStr%></td>
        <td width=20% class="tdwhiteM">&nbsp;<%=output.getOutputRole()==null?"":data.role_ko %></td>
        <%if(canDelete){%>
            <td class="tdwhiteM" width=10% align="center"><a href="JavaScript:deleteOutput('<%=data.oid%>')"><img src="/plm/portal/icon/del.gif" width="13" height="12" border="0"></a></td>
        <%}%>
<%     } else {
            if ( data.isRegistry ) {
                String imgStr = "";
                if( WorkInProgressHelper.isCheckedOut( (Workable)data.currentDocument )) imgStr = "../images/docmgt/doc_checkout.gif";
                imgStr = "<img border=0 align='absmiddle' src=" + imgStr + " border=0>";
                imgStr = "";

%>
        <td class="tdwhiteM" align="left">&nbsp;<%=imgStr%><A HREF="javascript:openViewProject('<%=CommonUtil.getOIDString(data.currentDocument)%>');"><%=data.name%></td>
<%
            } else {
                if ( canManage  ) {
%>
        <td class="tdwhiteM">&nbsp;<a href="javascript:updateOutput('<%=data.oid%>','<%=data.location%>');"><%=data.name%></a></td>
<%        } else {  %>
        <td class="tdwhiteM">&nbsp;<%=data.name%></td>
<%
                }
            }
%>
        <td class="tdwhiteM">&nbsp;<%=data.locationStr%></td>
        <td class="tdwhiteM">&nbsp;
            <%
            String deptName = "";
            if(output.getOwner().getPrincipal()!=null){
                PeopleData pdata = new PeopleData(output.getOwner().getPrincipal());
                deptName=pdata.departmentName;
            }
            %>
            <%=deptName%>
        </td>
        <TD class="tdwhiteM" >&nbsp;<%=output.getOwner()==null?"":StringUtil.checkNull(output.getOwner().getFullName())%></TD>
<%      if ( !data.isRegistry ) { %>
        <td class="tdwhiteM" colspan=3>
<%        //if ( data.canRegistry && canManage && canRegistry && data.isCreator ) {  %>
<%        if ( data.canRegistry && canRegistry && data.isCreator ) {  %>
            <table border=0 width=90% align=center>
                <tr align=center>
                    <td><input type=button class="btnTras" onclick="JavaScript:registryOutput('<%=data.oid%>')" id=button value='<%=messageService.getString("e3ps.message.ket_message", "02722") %><%--직접작성--%>'></td>
                    <td><input type=button class="btnTras" onclick="JavaScript:registryLinkOutput('<%=data.oid%>')" id=button value='<%=messageService.getString("e3ps.message.ket_message", "01352") %><%--링크등록--%>'></td>
                </tr>
            </table>
<%        } else {%>
            <font color="red"><%=messageService.getString("e3ps.message.ket_message", "01333") %><%--등록안됨--%></font>
<%        }  %>
        </td>
<%      } else {  %>
        <td class="tdwhiteM" >&nbsp;<%=data.currentDocument.getVersionDisplayIdentifier()%></td>
        <td class="tdwhiteM" >&nbsp;<%=data.currentDocument.getLifeCycleState().getLocalizedMessage(Locale.KOREAN)%></td>
        <td class="tdwhiteM" >&nbsp;<%=DateUtil.getDateString(data.currentDocument.getPersistInfo().getModifyStamp(), "D")%></td>
<%      }  %>
        <%if(canDelete){%><td >
            <%if(data.canDelete){%><a class="tdwhiteM" href="JavaScript:deleteOutput('<%=data.oid%>')"><p><img src="/plm/portal/icon/del.gif" width="13" height="12" border="0"></p></a>
            <%}else{%>&nbsp;
            <%}%></td>
        <%}%>
<%    }  %>
    </tr>
<%  } %>
<%  } %>
</table>
