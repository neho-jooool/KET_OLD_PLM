<%@page contentType="text/html; charset=UTF-8" %>

<%@include file="/jsp/common/context.jsp"%>

<%@page import="e3ps.common.util.WCUtil"%>
<%@page import="wt.inf.container.WTContainerRef"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<html>
<head>
<LINK rel="stylesheet" type="text/css" href="../css/e3ps.css">
<%
    WTContainerRef wtcontainerRef = WCUtil.getWTContainerRef();
%>
<!--<base target="iframe">-->
<SCRIPT LANGUAGE="JavaScript">
function selectFrame(str)
{
    var srcc = "";
    var tName = "";
    if(str == "LifeCycle")
    {
        srcc = "/plm/wt/clients/lifecycle/administrator/LifeCycleAdminTask.jsp?containerOid="+'<%=wtcontainerRef %>';
        tName = "LifeChycleAdmin";
    }else if(str == "WorkFlow")
    {
        srcc = "/plm/wt/clients/workflow/definer/WfAdministrator.jsp?containerOid="+'<%=wtcontainerRef %>';
        tName = "WorkflowAdmin";
    }
    document.all.iframe.src = srcc;
}
</SCRIPT>
</head>

<body  marginwidth="0" marginheight="0" leftmargin="0" topmargin="0" onload="javascript:selectFrame('LifeCycle')">
<form name=drawingmanager>
<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
   <tr>
        <td bgcolor=EDE9DD valign="top"  height="100%" valign=top>
            <table width=200  border="0" cellpadding="0" cellspacing="1">
                <tr>
                    <td height="25" bgcolor=white>
                        <img src="../icon/sub_arr.gif" width=22 height=9 border=0>
                        <a href="javascript:selectFrame('LifeCycle')" id sub onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "01343") %><%--라이프사이클 관리--%></a>
                    </td>
                </tr>
                <tr>
                    <td height="25" bgcolor=white>
                        <img src="../icon/sub_arr.gif" width=22 height=9 border=0>
                        <a href="javascript:selectFrame('WorkFlow')" id sub onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "02202") %><%--워크플로우 관리--%></a>
                    </td>
                </tr>
            </table>
        </td>
        <td width="100%" height="100%">
            <iframe frameborder=0 width="100%" height="100%" name=iframe scrolling=auto></iframe>
        </td>
    </tr>
</table>
<script src='/plm/portal/js/menu.js'></script>
<input type=hidden name=myname value="코드체계관리">
<script>setPosition(null);</script>
</form>
</body>
</html>

