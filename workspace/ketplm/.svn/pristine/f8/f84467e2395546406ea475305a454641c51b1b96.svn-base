<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,
                wt.fc.*,
                wt.org.*,
                wt.team.*,
                wt.project.Role" %>
<%@page import="e3ps.groupware.company.*,
                e3ps.common.util.*,
                e3ps.common.jdf.log.Logger,
                e3ps.common.jdf.config.Config,
                e3ps.common.jdf.config.ConfigImpl,
                e3ps.auth.beans.E3PSAuthHelper,
                e3ps.project.*,
                e3ps.project.beans.*" %>
                
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>                

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%
    Config conf = ConfigImpl.getInstance();
    String sendNoti = conf.getString("project.sendnoti");

    boolean isTemplete = true;
    boolean canDelete = false;
    boolean canManage = false;
    boolean isAuth = E3PSAuthHelper.manager.isAuth("PRJ", "Project");
    String functionName = "addMemberUseTemplateProject";
    String viewName = "TemplateProjectView";
    String type= request.getParameter("type");
    if(type==null || type.length()==0)
        type="member";

    boolean isAdmin = CommonUtil.isAdmin();
    String oid = StringUtil.checkNull(request.getParameter("oid"));
    TemplateProject project = (TemplateProject)CommonUtil.getObject(oid);

    HashMap legacyMap = null;

    if ( project instanceof E3PSProject ) {  // 실 프로젝트인 경우
        isTemplete = false;
        functionName = "addMemberUseProject";
        viewName = "ProjectView";

        E3PSProjectData projectData = new E3PSProjectData((E3PSProject)project);
        ProjectAccessData accessData = projectData.getAccessState();
        boolean isPM = ProjectUserHelper.manager.isPM((E3PSProject)project);
        canDelete = ( isAdmin || isPM) ;  //&& accessData.isContinue()  관리자 이면서 태스크가 진행중일 경우
        canManage = ( isAdmin || isPM ) ;  //&& accessData.isContinue()  관리자 이면서 태스크가 진행중일 경우
    } else {  // 템플릿 프로젝트인 경우
        canDelete = isAdmin;
        canManage = isAdmin;
    }

    try{
        Vector addUser = new Vector();
        legacyMap = new HashMap();
        QueryResult result = ProjectUserHelper.manager.getQueryForTeamUsers(project, "ROLEMEMBER");
        while(result.hasMoreElements()) {
            ProjectMemberLink link = (ProjectMemberLink)result.nextElement();
            String legRole = link.getPjtRole();
            String legUser = CommonUtil.getOIDString(link.getMember());
            legacyMap.put(legRole,legUser);
        }

        String[] addRole = request.getParameterValues("addrole");
        String[] addTeamUser = request.getParameterValues("addteamuser");

        HashMap newMap = new HashMap();
        if(addRole!=null) {
            for(int i=0;i<addRole.length;i++) {
                if( addTeamUser !=null && addTeamUser.length >0) {
                    newMap.put(addRole[i],addTeamUser[i]);
                }
            }
        }

        if( addTeamUser !=null && addTeamUser.length >0) {
            for(int i=0;i<addTeamUser.length;i++) {
                WTUser user = (WTUser)CommonUtil.getObject(addTeamUser[i]);
            }
        }

        String deleteMember = request.getParameter("deleteMember");
        if ( deleteMember != null ) {
            WTUser user = (WTUser)CommonUtil.getObject(deleteMember);
            if(user!=null) {
                ProjectMemberLink link = ProjectUserHelper.manager.getProjectPeopleLink(project,user);
                if(link!=null) {
                    ProjectUserHelper.manager.deleteProjectUser(project, user);
                }
            }
        }
    } catch(Exception e) {
	   Kogger.error(e);
    }
%>
<script language="JavaScript">
<!--
function deleteMember(v) {
    form = document.forms[0];
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03296") %><%--구성원을 삭제 합니다.\n구성원을 삭제하면 관련 태스크권한도 같이 삭제됩니다.\n삭제하시겠습니까?--%>") ) {
        form.action = "/plm/jsp/project/projectMemberAssign.jsp?command=delete&deleteMember="+v;
        form.target = "userAssignFrame";
        form.method = "post";
        form.submit();
    }
}

function addMember() {
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
        return;
    }
    acceptMember(attacheMembers);
}

function acceptMember(objArr) {
    if(objArr.length == 0) {
        return;
    }

    var addMembers = "";
    for(var i = 0; i < objArr.length; i++) {
        addMembers += "<input type=hidden name=wtuserOid value='"+objArr[i][0]+"'>";
    }
    document.all.addMember.innerHTML = addMembers;

    userAssignMethod();

}

function clearMemberTable(targetId) {
    tableObj  = document.getElementById(targetId);
    if(tableObj.rows.length > 1) {
        for(var i = tableObj.rows.length; i > 1;i--) {
            tableObj.deleteRow(i);
        }
    }
}

function memberTableRefresh(objArr) {
    if(objArr.length == 0) {
        return;
    }
}

function addRoleMember() {
    var url = "/plm/jsp/project/PjtRoleMemberModifyPop.jsp?oid=<%=oid%>";
    openOtherName(url, "addRoleMember", "700", "310", "status=no,scrollbars=no,resizeable=no");
}

function addTeamUser() {
    var url = "/plm/jsp/project/AddMemberForm.jsp?oid=<%=oid%>";
    openOtherName(url,"process","700","410","status=yes,scrollbars=yes,resizable=yes");
}

function excludeMember(ex) {
    if(confirm('<%=messageService.getString("e3ps.message.ket_message", "02952") %><%--팀원에서 삭제됩니다.\n팀원을 삭제하면 관련 태스크 권한도 같이 삭제됩니다.\n삭제하시겠습니까?--%>')) {
        document.forms[0].action="/plm/jsp/project/<%=viewName%>.jsp?excludemember="+ex;
        document.forms[0].method="post";
        document.forms[0].submit();
    }
}

function userAssignMethod() {
    form = document.forms[0];
    form.target = "userAssignFrame";
    form.action = "/plm/jsp/project/projectMemberAssign.jsp?type=<%=type%>";
    form.submit();
}

function returnMethod() {
    form = document.forms[0];
    form.target = "_self";
    form.action = "/plm/jsp/project/ProjectView.jsp";
    form.submit();
}
-->
</script>

<%     if(canManage) { %>
    <table border="0" width="100%" cellpadding="0" cellspacing="0" align="center">
        <tr>
            <td width="100%">
                <input name="btnSubmit1" type="button" class="s_font" value="<%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%>" onClick="javascript:addRoleMember();">
            </td>
        </tr>
    </table>
<%     } else { %>
    <br>
<%    } %>
    <table border="0" cellpadding="1" cellspacing="0" width="100%" align="center">
        <tr>
            <td width="100%">
                <table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor=AABDC6>
                    <tr bgcolor="#D6DBE7" align=center>
                        <td width=35% id=tb_blue>ROLE</td>
                        <td width=15% id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                        <td width="20%" id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                        <td width="30%" id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                    </tr>
    <%
        TeamTemplate tempTeam = TeamHelper.service.getTeamTemplate("Team_Project");
        Vector vecTeamStd = tempTeam.getRoles();
        Role role = null;
        String roleName_en = "";
        String roleName_ko = "";
        String userName = "";
        String duty = "";
        String departName = "";
        PeopleData peopleData = null;

        for (int i = 0; i < vecTeamStd.size(); i++) {
            role = (Role) vecTeamStd.get(i);
            roleName_en = role.toString();
            roleName_ko = role.getDisplay(Locale.KOREA);
            if(legacyMap.get(roleName_en) != null) {
                userName = ((WTUser)CommonUtil.getObject(legacyMap.get(roleName_en).toString())).getFullName();
                peopleData = new PeopleData((WTUser)CommonUtil.getObject(legacyMap.get(roleName_en).toString()));
                duty = StringUtil.checkNull(peopleData.duty);
                departName =StringUtil.checkNull(peopleData.departmentName);
            } else {
                userName = "";
                duty = "";
                departName = "";
            }
    %>
                    <tr bgcolor="#ffffff" onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
                        <td id=tb_gray align=center><%=roleName_ko%></td>
                        <td id=tb_gray align=center><%=userName%></td>
                        <td id=tb_gray align=center><%=StringUtil.checkNull(duty)%></td>
                        <td id=tb_gray align=center><%=StringUtil.checkNull(departName)%></td>
                    </tr>
    <%
        }
    %>
                </table>
            </td>
        </tr>
    </table>
    <br>
<%     if(canManage) { %>
    <table border="0" width="100%" cellpadding="0" cellspacing="0" align="center">
        <tr>
            <td width="100%">
                <input name="btnSubmit1" type="button" class="s_font" value="<%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%>" onClick="javascript:addMember();">
            </td>
        </tr>
    </table>
<%     } else { %>
    <br>
<%    } %>
    <table border="0" width="100%" cellpadding="1" cellspacing="0" align="center">
        <tr>
            <td width="100%">
                <table width="100%" cellspacing="1" cellpadding="1" border="0" id="userMemberTable" bgcolor=AABDC6>
                    <tr bgcolor="#D6DBE7"  align=center>
                        <td width="20%" id=tb_blue ><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                        <td width="20%" id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                        <td width="30%" id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                        <td width="30%" id=tb_blue>e-mail</td>
                        <% if(canDelete) { %>
                        <td width="1%" id=tb_blue>&nbsp;</td>
                        <% } %>
                    </tr>
<%
        QueryResult members = ProjectUserHelper.manager.getQueryForTeamUsers(project, "MEMBER");
        while ( members.hasMoreElements() ) {
            ProjectMemberLink link = (ProjectMemberLink)members.nextElement();
            PeopleData data = new PeopleData(link.getMember());
            String wtuserOID = CommonUtil.getOIDString(data.wtuser);
            String peopleOID = CommonUtil.getOIDString(data.people);
%>
                    <tr bgcolor="#ffffff" onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
                        <td id=tb_gray width="20%" align=center>&nbsp;<a href="JavaScript:viewPeople('<%=peopleOID%>')"><%=data.name%></td>
                        <td id=tb_gray width="20%" align=center>&nbsp;<%=data.duty%></td>
                        <td id=tb_gray width="30%" align=center>&nbsp;<%=data.departmentName%></td>
                        <td id=tb_gray width="30%" align=center>&nbsp;<a href="mailto:<%=data.email%>"><%=data.email%></a></td>
<%      if(canDelete) { %>
                        <td id=tb_gray align=center>
                            <a href="JavaScript:deleteMember('<%=wtuserOID%>')"><img src="/plm/portal/icon/del.gif" width="13" height="12" border="0"></a>
                        </td>
<%      } %>
                    </tr>
<%    }  %>
                </table>
            </td>
        </tr>
    </table>
    <span id=addMember dataformatas=HTML datafld='addMember' datasrc=#DDR_DSO></span>
    <iframe src="" name="userAssignFrame" scrolling=no frameborder=no marginwidth=0 marginheight=0 style="display:none"></iframe>
