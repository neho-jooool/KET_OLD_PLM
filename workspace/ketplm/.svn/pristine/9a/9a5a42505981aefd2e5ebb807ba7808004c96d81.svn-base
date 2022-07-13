<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,
                wt.fc.*,
                wt.org.*,
                wt.session.SessionHelper" %>
<%@page import="e3ps.groupware.company.*,
                e3ps.common.util.*,
                e3ps.common.jdf.log.Logger,
                e3ps.project.*,
                e3ps.project.beans.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
    boolean isTemplete = true;
    boolean canDelete = false;
    boolean canManage = false;
    boolean isPM = false;

    E3PSProjectData projectData = null;
    ProjectAccessData accessData = null;

    boolean isAdmin = CommonUtil.isAdmin();
    String oid = StringUtil.checkNull(request.getParameter("oid"));
    TemplateProject project = (TemplateProject)CommonUtil.getObject(oid);

    boolean isManagerCreator =false;
    if ( project instanceof E3PSProject ) {	// 실 프로젝트인 경우
        isTemplete = false;
        projectData = new E3PSProjectData((E3PSProject)project);
        accessData = projectData.getAccessState();
        isPM = ProjectUserHelper.manager.isPM((E3PSProject)project);

        canDelete = isAdmin && accessData.isContinue();	// 관리자 이면서 태스크가 진행중일 경우
        canManage = (isAdmin || isPM)&& accessData.isContinue();	// 관리자이거나 PM 이면서 태스크가 진행중일 경우
    } else {	// 템플릿 프로젝트인 경우
        canDelete = isAdmin;
        canManage = isAdmin;
    }
%>
<script language="JavaScript">
<!--
function modifyPM(oid) {
    var url="/plm/jsp/groupware/company/selectPeopleFrm.jsp?function=modifyPM";
    openOtherName(url,"project","700","410","status=yes,scrollbars=yes,resizable=yes");
}
-->
</script>

            <table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor=AABDC6>
                <br>
                <tr bgcolor="#D6DBE7" align=center>
                    <td width=35% id=tb_blue align=center><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                    <td width=15% id=tb_blue align=center><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                    <td width=15% id=tb_blue align=center><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                    <td width=30% id=tb_blue align=center>e-mail</td>
                </tr>
<%
    PeopleData pData = new PeopleData(projectData.pjtPm);
    String pOID = CommonUtil.getOIDString(projectData.pjtPm);
%>
                <tr bgcolor="#ffffff" onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
                    <td id=tb_gray align=center>&nbsp;<a href="JavaScript:viewPeople('<%=pOID%>')"><%=pData.name%></td>
                    <td id=tb_gray align=center>&nbsp;<%=pData.departmentName%></td>
                    <td id=tb_gray align=center>&nbsp;<%=pData.duty%></td>
                    <td id=tb_gray align=left>&nbsp;<a href="mailto:<%=pData.email%>"><%=pData.email%></a></td>
                </tr>
            </table>
