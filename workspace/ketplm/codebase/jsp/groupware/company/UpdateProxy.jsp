<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page import="wt.org.WTUser"%>
<%@ page import="e3ps.common.util.*,e3ps.groupware.company.*"%>

<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<!-- 로그 설정 임포트 끝 -->

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/common/context.jsp"%>
<%
e3ps.common.jdf.message.Message message = e3ps.common.jdf.message.MessageBox.getInstance("message");
%>
<HTML>
<HEAD>
<TITLE><%=messageService.getString("e3ps.message.ket_message", "01220") %><%--대결재자 수정--%></TITLE>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/org.js"></SCRIPT>
<SCRIPT language=JavaScript>
function update(oid)
{
    if( document.forms[0].proxy.value == document.forms[0].wtuseroid.value )
    {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02414") %><%--자신을 대결재자로 지정할 수 없습니다--%>');
        return;
    }
    if(!confirm("<%=message.getMessage("confirm.update")%>")) return;

    document.forms[0].action = 'UpdateProxy.jsp?cmd=update&peopleoid='+oid;
    document.forms[0].submit();
}
</SCRIPT>
</HEAD>
<body>
<%
    String peopleoid = request.getParameter("peopleoid");
    String proxyName = "";
    String proxyOid = "";

    People people = (People)CommonUtil.getObject(peopleoid);
    
    Kogger.debug("UpdateProxy", null, null, " people = " + people );
    if(people.getProxy().getObject() != null)
    {
        WTUser proxy = (WTUser)people.getProxy().getObject();
        proxyName = proxy.getFullName();
        proxyOid = CommonUtil.getOIDString(proxy);
    }


    if("update".equals(request.getParameter("cmd")))
    {
        WTUser user = (WTUser)CommonUtil.getObject(request.getParameter("proxy"));
        if(user == null)
            people.setProxy(wt.org.WTPrincipalReference.newWTPrincipalReference());
        else
            people.setProxy(wt.org.WTPrincipalReference.newWTPrincipalReference(user));
        wt.fc.PersistenceHelper.manager.modify(people);
%>
        <SCRIPT languange=JavaScript>
            opener.location.reload();
            alert("<%=message.getMessage("update.success")%>");
            self.close();
        </SCRIPT>
<%
        return;
    }
%>
<form method=post>
<input type=hidden name=wtuseroid value='<%=request.getParameter("wtuseroid") %>'>
<table width="95%" height=40 align=center border=0>
    <tr>
        <td width="50%" align="left">
            <table border=0 cellpadding=0 cellspacing=0 >
            <tr>
                <td><img src=/plm/portal/images/title2_left.gif></td>
                <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "01220") %><%--대결재자 수정--%></td>
                <td><img src=/plm/portal/images/title2_right.gif></td>
            </tr>
            </table>
        </td>
        <TD align=right>
            <input type='button' id='button2' value='<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>' onClick="update('<%=peopleoid %>')">
            <input type='button' id='button2' value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' onClick="closeWindow()">
        </TD>
    </tr>
</table>
<table width="95%" border="0" cellspacing="1" cellpadding="1" bgcolor="AABDC6" align="center">
    <tr valign="middle" height=23>
        <td bgcolor="D8E0E7" width="100%" align="center" id=title><%=messageService.getString("e3ps.message.ket_message", "01220") %><%--대결재자 수정--%></td>
    </tr>
    <tr valign="middle">
        <td bgcolor="#FFFFFF" align="center" height="23">
            <input type='text' name='tempproxy' readonly id=i size=10 value='<%=proxyName %>'>
            <input type='hidden' name='proxy' value='<%=proxyOid %>'>&nbsp;<input type='button' id=innerbutton value='<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>' onclick='selectOneUser("proxy")'>
            <input type='button' id=innerbutton value='<%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%>' onclick='delOneUser("proxy")'>
        </td>
    </tr>
</table>

</form>
</body>
</html>
