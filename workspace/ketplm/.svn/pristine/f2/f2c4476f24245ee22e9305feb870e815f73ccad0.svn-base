<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import = "java.util.*,
                  wt.fc.*,
                  wt.org.*,
                  wt.vc.*,
                  wt.part.*,
                  wt.project.Role,
                  wt.team.*,
                  wt.vc.wip.*"%>
<%@page import="e3ps.groupware.company.*,
                e3ps.common.util.*,
                e3ps.common.content.*,
                e3ps.common.jdf.log.Logger,
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
    boolean flag = false;
    String message = "";

    String command = request.getParameter("command");
    String oid = request.getParameter("oid");
    String type = request.getParameter("type");
    String[] wtuserOid = request.getParameterValues("wtuserOid");

    if(command == null || command.length() == 0) {
        command = "";
    }

    HashMap map = new HashMap();
    Vector members = new Vector();
    //Kogger.debug("projectMemberAssign.jsp>>command["+command+"]");
    if("delete".equals(command)) {
        String[] deleteMember = request.getParameterValues("deleteMember");
        if(deleteMember != null) {
            for(int i = 0; i < deleteMember.length; i++) {
                //Kogger.debug("############ wtuseroid : " + wtuserOid[i]);
                members.add(deleteMember[i]);
            }

            if(map == null) {
                map = new HashMap();
            }
            map.put("oid", oid);
            map.put("deleteMember", members);
            flag = ProjectHelper.deleteProjectUser(map);
            if(flag)
                message = "<%=messageService.getString("e3ps.message.ket_message", "00977") %><%--구성원을 삭제했습니다--%>";
            else
                message = "<%=messageService.getString("e3ps.message.ket_message", "00976") %><%--구성원을 삭제하는 중 에러가 발생했습니다 \n 다시 삭제하시기 바랍니다--%>";
        }

    } else if("roleAssign".equals(command)) {
        map.put("oid", oid);

        TeamTemplate tempTeam = TeamHelper.service.getTeamTemplate("Team_Project");
        Kogger.debug("tempTeam >> " + tempTeam);
        if(tempTeam != null) {
            Vector vecTeamStd = tempTeam.getRoles();
            Role role = null;
            String newRoleMember = null;
            Vector roleVec = null;
            for(int i = 0; i < vecTeamStd.size(); i++) {
                role = (Role) vecTeamStd.get(i);
                newRoleMember = request.getParameter(role.toString());
                //roleVec에 사이즈가 0인 경우에는 이미 assign된 role 담당자는 삭제 됨.
                roleVec = new Vector();

                if(newRoleMember != null &&  (newRoleMember.trim()).length() > 0) {
                    roleVec.add(newRoleMember);
                }
                map.put(role.toString(), roleVec);
            }
        }
        flag = ProjectHelper.assignProjectMember(map);
        if(flag)
                message = messageService.getString("e3ps.message.ket_message", "00447")/*Role 담당자를 추가 했습니다*/;
            else
                message = messageService.getString("e3ps.message.ket_message", "00448")/*Role 담당자를 추가하는 중 에러가 발생했습니다 \n 다시 추가하시기 바랍니다*/;
    } else {
        if(wtuserOid != null) {
            for(int i = 0; i < wtuserOid.length; i++) {
                //Kogger.debug("############ wtuseroid : " + wtuserOid[i]);
                members.add(wtuserOid[i]);
            }
        }

        if(map == null) {
            map = new HashMap();
        }
        map.put("oid", oid);
        map.put(type.toUpperCase(), members);

        flag = ProjectHelper.assignProjectMember(map);
        if(flag)
            message = "<%=messageService.getString("e3ps.message.ket_message", "00979") %><%--구성원이 추가되었습니다--%>";
        else
            message = "<%=messageService.getString("e3ps.message.ket_message", "00978") %><%--구성원을 추가하는 중 에러가 발생했습니다 \n 다시 추가하시기 바랍니다--%>";
    }
%>

<html>
<head>
<script language="javascript">
if(parent) {
    alert("<%=message%>");
    parent.returnMethod();
}
</script>
</head>
<body>
</body>
</html>
