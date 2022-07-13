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
        canDelete = ( isAdmin || isPM) && accessData.isContinue();  // 관리자 이면서 태스크가 진행중일 경우
        canManage = ( isAdmin || isPM ) && accessData.isContinue();  // 관리자 이면서 태스크가 진행중일 경우
    } else {  // 템플릿 프로젝트인 경우
        canDelete = isAdmin;
        canManage = isAdmin;
    }

    try{
        Vector addUser = new Vector();
        //if("member".equals(type))
        //{
            //String[] addMember = request.getParameterValues("addMember");
            //if ( addMember != null && addMember.length > 0 )
            //{
                //for ( int i = 0 ; i < addMember.length ; i++ )
                //{
                    //WTUser user = (WTUser)CommonUtil.getObject(addMember[i]);
                    //if ( user != null ) ProjectUserHelper.manager.setMember(project,user);
                    //addUser.add(user);
                //}
                //if("on".equals(sendNoti))
                //{
                    //if(ProjectUserHelper.manager.sendMail((E3PSProject)project,addUser,"addmem"))
                        //Logger.info.println("추가 구성원에게 알림 메일이 발송되었습니다.");
                    //else
                        //Logger.info.println("추가 구성원에게 메일을 발송하는 과정에서 오류가 발생했습니다.");
                //}
            //}
        //}
        //else if("team".equals(type))
        //{
            legacyMap = new HashMap();
            QueryResult result = ProjectUserHelper.manager.getQueryForTeamUsers(project, "ROLEMEMBER");
            while(result.hasMoreElements())
            {
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

            //ProjectUserHelper.manager.deleteUsersRole(project, addTeamUser);
            if("on".equals(sendNoti)) {
                //ProjectUserHelper.manager.checkRole((E3PSProject)project, newMap, legacyMap);
            }

            if( addTeamUser !=null && addTeamUser.length >0) {
                for(int i=0;i<addTeamUser.length;i++) {
                    WTUser user = (WTUser)CommonUtil.getObject(addTeamUser[i]);
                    //if(user!=null) ProjectUserHelper.manager.setMemberUsers(project, addRole[i], user);
                    //QueryResult qr = ProjectOutputHelper.manager.getOutputByRole(project, addRole[i]);
//                  if(qr!=null)Logger.info.println("projectTeamInfo_include::addTeamUser:qr.size = "+qr.size());
                    //while(qr.hasMoreElements())
                    //{
                        //Object[] obj = (Object[])qr.nextElement();
                        //ProjectOutput output = (ProjectOutput)obj[0];
                        //ProjectOutputData data = new ProjectOutputData(output);
                        //if(data.isWorking)
                        //{
                            //output.setOwner(WTPrincipalReference.newWTPrincipalReference(user));
                            //ProjectOutputHelper.manager.modifyProjectOutput(output);
                        //}
                    //}
                }
            }
        //}
        String deleteMember = request.getParameter("deleteMember");
        if ( deleteMember != null ) {
            WTUser user = (WTUser)CommonUtil.getObject(deleteMember);
            if(user!=null) {
                ProjectMemberLink link = ProjectUserHelper.manager.getProjectPeopleLink(project,user);
                if(link!=null) {
                    //if(link.getRole()!=null)
                    //{
                        //ProjectUserHelper.manager.excludeTeamUser(project,user);
                    //}
                    //else
                    //{
                        ProjectUserHelper.manager.deleteProjectUser(project, user);
                    //}
                    //if("on".equals(sendNoti))
                    //{
                        //Vector uVec = new Vector();
                        //uVec.add(user);
                        //Logger.user.println("projectTeamInfo::deleteMember:project = "+project.getName());
                        //if(ProjectUserHelper.manager.sendMail((E3PSProject)project,uVec,"delmem"))
                            //Logger.info.println("프로젝트에서 제외된 구성원에게 알림 메일이 발송되었습니다.");
                        //else
                            //Logger.info.println("프로젝트에서 제외된 구성원에게 메일을 발송하는 과정에서 오류가 발생했습니다.");
                    //}
                }
            }
        }
    } catch(Exception e) {
	Kogger.error(e);
    }

    //QueryResult tempQr = ProjectUserHelper.manager.getPM(project);
    //if(tempQr.hasMoreElements())
    //{
        //Object[] obj = (Object[])tempQr.nextElement();
        //ProjectMemberLink tempLink = (ProjectMemberLink)obj[0];
        //Kogger.debug("projectTeamInfo_include.jsp::PM = "+tempLink.getMember().getFullName());
    //}
%>
<script language="JavaScript">
<!--

function deleteMember(v) {
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03296") %><%--구성원을 삭제 합니다.\n구성원을 삭제하면 관련 태스크권한도 같이 삭제됩니다.\n삭제하시겠습니까?--%>") ) {
        document.forms[0].action = "/plm/jsp/project/<%=viewName%>.jsp?deleteMember="+v;
        document.forms[0].method = "post";
        document.forms[0].submit();
    }
}

function addMember() {
    var url="/plm/jsp/groupware/company/selectPeopleFrm.jsp?mode=m&function=<%=functionName%>";
    openOtherName(url,"process","700","410","status=no,scrollbars=no,resizable=no");
}

function addRoleMember() {
    var url = "/plm/jsp/project/AddProjectRoleForm.jsp?oid=<%=oid%>";
    openOtherName(url, "addRoleMember", "700", "410", "status=no,scrollbars=no,resizeable=no");
}

function addTeamUser() {
    var url = "/plm/jsp/project/AddMemberForm.jsp?oid=<%=oid%>";
    openOtherName(url,"process","700","410","status=yes,scrollbars=yes,resizable=yes");
}

function excludeMember(ex) {
    if(confirm('<%=messageService.getString("e3ps.message.ket_message", "02952") %><%--팀원에서 삭제됩니다.\n팀원을 삭제하면 관련 태스크 권한도 같이 삭제됩니다.\n삭제하시겠습니까?--%>'))
    {
        document.forms[0].action="/plm/jsp/project/<%=viewName%>.jsp?excludemember="+ex;
        document.forms[0].method="post";
        document.forms[0].submit();
    }
}
-->
</script>
<table width="100%" cellspacing="0" cellpadding="1" border="0" align="center">
    <table border="0" cellpadding="1" cellspacing="0" width="95%" align="center">
        <tr>
            <td width="100%">
                <table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor=AABDC6>
<%     if(canManage) { %>
                    <tr>
                        <input type=button class="btnTras" id=innerbutton value='<%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%>' onclick="javascript:addRoleMember()">&nbsp;
                    </tr>
<%     } %>
                    <tr bgcolor="#D6DBE7" align=center>
                        <td width=35% id=tb_blue>ROLE</td>
                        <td width=15% id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                        <td width=35% id=tb_blue>ROLE</td>
                        <td width=15% id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                    </tr>
    <%
        TeamTemplate tempTeam = TeamHelper.service.getTeamTemplate("Team_Project");
        Vector vecTeamStd = tempTeam.getRoles();
        Role role = null;
        String roleName_en = "";
        String roleName_ko = "";
        String userName = "";

        for (int i = vecTeamStd.size() - 1; i > -1; i--) {
            role = (Role) vecTeamStd.get(i);
            roleName_en = role.toString();
            roleName_ko = role.getDisplay(Locale.KOREA);
            if(legacyMap.get(roleName_en) != null)
                userName = ((WTUser)CommonUtil.getObject(legacyMap.get(roleName_en).toString())).getFullName();
            else
                userName = "";
    %>
                    <tr bgcolor="#ffffff" onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
                        <td id=tb_gray align=center><%=roleName_ko%></td>
                        <td id=tb_gray align=center><%=userName%></td>
    <%
            if(--i>-1) {
                role = (Role) vecTeamStd.get(i);
                roleName_en = role.toString();
                roleName_ko = role.getDisplay(Locale.KOREA);
                if(legacyMap.get(roleName_en) != null)
                    userName = ((WTUser)CommonUtil.getObject(legacyMap.get(roleName_en).toString())).getFullName();
                else
                    userName = "";
    %>
                        <td id=tb_gray align=center><%=roleName_ko%></td>
                        <td id=tb_gray align=center><%=userName%></td>
    <%
            } else {
    %>
                        <td id=tb_gray align=center colspan=2></td>
    <%
            }
    %>
                    </tr>
    <%
        }
    %>
                </table>
            </td>
        </tr>
    </table>
    <br><br>

<%
            String userAssignMethod = request.getParameter("userAssignMethod");
            if(userAssignMethod == null || userAssignMethod.length() == 0) {
                userAssignMethod = "";
            }
%>
    <table border="0" width="100%" cellpadding="1" cellspacing="0">
        <tr>
            <td width="100%">
                <jsp:include page="/jsp/project/selectUserUseCreate_include.jsp" flush="false">
                    <jsp:param name="formObj" value="userMember"/>
                    <jsp:param name="checkBoxName" value="userMemberCheckBox"/>
                    <jsp:param name="tableName" value="userMemberTable"/>
                    <jsp:param name="addFunction" value="addUserMember"/>
                    <jsp:param name="insertFunction" value="insertUserMember"/>
                    <jsp:param name="insertURL" value="/plm/jsp/groupware/company/selectPeopleFrm.jsp"/>
                    <jsp:param name="deleteFunction" value="deleteUserMember"/>
                    <jsp:param name="mode" value="m"/>
                    <jsp:param name="checkOverLap" value="t"/>
                    <jsp:param name="iconUrl" value="/plm/portal/icon"/>
                    <jsp:param name="canManage" value="<%=canManage%>"/>
                    <jsp:param name="userAssignMethod" value="<%=userAssignMethod%>"/>

                </jsp:include>
    <table width="100%" cellspacing="0" cellpadding="1" border="0" align="center">
        <table border="0" cellpadding="1" cellspacing="0" width="95%" align="center">
            <tr>
                <td width="100%">
                    <table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor=AABDC6>
<%
        QueryResult members = ProjectUserHelper.manager.getQueryForTeamUsers(project, "MEMBER");
        while ( members.hasMoreElements() ) {
            ProjectMemberLink link = (ProjectMemberLink)members.nextElement();
            PeopleData data = new PeopleData(link.getMember());
            String wtuserOID = CommonUtil.getOIDString(data.wtuser);
            String peopleOID = CommonUtil.getOIDString(data.people);
%>
                        <tr bgcolor="#ffffff" onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
                            <td id=tb_gray width="20%">&nbsp;<a href="JavaScript:viewPeople('<%=peopleOID%>')"><%=data.name%></td>
                            <td id=tb_gray width="20%">&nbsp;<%=data.duty%></td>
                            <td id=tb_gray width="30%">&nbsp;<%=data.departmentName%></td>
                            <td id=tb_gray width="30%">&nbsp;<a href="mailto:<%=data.email%>"><%=data.email%></a></td>
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
    </table>
<%  //if(members.size()==0)out.println("<br/>");%>
