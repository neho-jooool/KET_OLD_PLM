<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<html>
<head>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<!--<base target="iframe">-->
<SCRIPT LANGUAGE="JavaScript"><!--
function selectFrame(str, tName)
{
    var srcc;

    srcc = "";

    if(str == "list"){
        srcc = "/plm/jsp/project/machine/ListMachine.jsp"
    }else if(str == "create"){
        srcc = "/plm/jsp/project/machine/CreateMachine.jsp";
    }else{
        srcc = "/plm/jsp/common/code/AdminNumberCodeMgtFrame.jsp?expandedDepth=-1&selectedDepth=-1&codetype=" + str + "&tname=" + tName;
    }

    document.all.iframe.src = srcc;
}
</SCRIPT>
</head>

<body  marginwidth="0" marginheight="0" leftmargin="0" topmargin="0" >
<form name=drawingmanager>
<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
   <tr>
        <td bgcolor=EDE9DD valign="top"  height="100%" valign=top>
            <table width=200  border="0" cellpadding="0" cellspacing="1">
                <tr>
                    <td height="25" bgcolor=white><img src="/plm/portal/icon/sub_arr.gif" width=22 height=9 border=0><a href="javascript:selectFrame('list','')" id=sub onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "00311") %><%--Machine 목록--%></td>
                </tr>
                <tr>
                    <td height="25" bgcolor=white><img src="/plm/portal/icon/sub_arr.gif" width=22 height=9 border=0><a href="javascript:selectFrame('create','')" id=sub onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "00310") %><%--Machine 등록--%></td>
                </tr>
                <tr>
                    <td height="25" bgcolor=white><img src="/plm/portal/icon/sub_arr.gif" width=22 height=9 border=0><a href="javascript:selectFrame('MACHINEMAKER','Machine Maker')" id=sub onFocus="this.blur()">Maker</td>
                </tr>
                <tr>
                    <td height="25" bgcolor=white><img src="/plm/portal/icon/sub_arr.gif" width=22 height=9 border=0><a href="javascript:selectFrame('MACHINETYPE','Machine Type')" id=sub onFocus="this.blur()">Type</td>
                </tr>
                <tr>
                    <td height="25" bgcolor=white><img src="/plm/portal/icon/sub_arr.gif" width=22 height=9 border=0><a href="javascript:selectFrame('MACHINETON','Machine Ton')" id=sub onFocus="this.blur()">Ton</td>
                </tr>

            </table>
        </td>
        <td width="100%" height="100%">
            <iframe frameborder=0 width="100%" height="100%" src="/plm/jsp/project/machine/ListMachine.jsp" name=iframe scrolling=auto></iframe>
        </td>
    </tr>
</table>
<script src='/plm/portal/js/menu.js'></script>
<input type=hidden name=myname value="Machine관리">
<script>setPosition(null);</script>
</form>
</body>
</html>

