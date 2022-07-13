<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.util.*,
                wt.org.*,
                wt.team.*,
                wt.project.Role,
                java.util.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/org.js"></SCRIPT>

<table border="0" cellpadding="1" cellspacing="1" width="100%" bgcolor="#AABDC6" align="center">
    <TR bgcolor="#D8E0E7" align=center height=23>
        <TD width=15% id=title>ROLE</TD>
        <TD width=35% id=title><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></TD>
        <TD width=15% id=title>ROLE</TD>
        <TD width=35% id=title><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></TD>
    </TR>
<%
    // 기준 Team
    TeamTemplate tempTeam = TeamHelper.service.getTeamTemplate("Team_Project");
    if(tempTeam != null) {
        Vector vecTeamStd = tempTeam.getRoles();
        Role role = null;
        String roleName_en = null;
        String roleName_ko = null;

        for (int i = vecTeamStd.size() - 1; i > -1; i--) {
            role = (Role) vecTeamStd.get(i);

            roleName_en = role.toString();
            roleName_ko = role.getDisplay(Locale.KOREA);
            Enumeration enm = tempTeam.getPrincipalTarget(role);

            WTPrincipal prin = null;
            while (enm.hasMoreElements()) {
                WTPrincipalReference ref = (WTPrincipalReference)enm.nextElement();
                prin = ref.getPrincipal();
            }
%>
    <TR bgcolor=ffffff align=center>
        <TD><%=roleName_ko%></TD>
        <TD>
            <input type='text' size=10 name='temp<%=roleName_en%>' value='<%=prin==null?"":((WTUser)prin).getFullName()%>' readonly id=i>
            <input type='hidden' name='<%=roleName_en%>' value='<%=prin==null?"":CommonUtil.getOIDString(prin)%>'>
            <input type='button' class="btnTras" value='<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>' onClick='selectOneUser("<%=roleName_en%>")' id=innerbutton>
            <input type='button' class="btnTras" value='<%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%>' onClick='delOneUser("<%=roleName_en%>")' id=innerbutton>
        </TD>
<%
            prin = null;
            if(--i>-1) {
                role = (Role) vecTeamStd.get(i);

                roleName_en = role.toString();
                roleName_ko = role.getDisplay(Locale.KOREA);
                enm = tempTeam.getPrincipalTarget(role);

                while (enm.hasMoreElements()) {
                    WTPrincipalReference ref = (WTPrincipalReference)enm.nextElement();
                    prin = ref.getPrincipal();
                }
            } else {
                roleName_ko = "";
            }
%>
        <TD><%=roleName_ko%></TD>
        <TD>
<%      if(roleName_ko.length() != 0){ %>
            <input type='text' size=10 name='temp<%=roleName_en%>' value='<%=prin==null?"":((WTUser)prin).getFullName()%>' readonly id=i>
            <input type='hidden' name='<%=roleName_en%>' value='<%=prin==null?"":CommonUtil.getOIDString(prin)%>'>
            <input type='button' class="btnTras" value='<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>' onClick='selectOneUser("<%=roleName_en%>")' id=innerbutton>
            <input type='button' class="btnTras" value='<%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%>' onClick='delOneUser("<%=roleName_en%>")' id=innerbutton>
<%      } %>
        </TD>
    </TR>
<%
        }
    }
%>
</TABLE>
