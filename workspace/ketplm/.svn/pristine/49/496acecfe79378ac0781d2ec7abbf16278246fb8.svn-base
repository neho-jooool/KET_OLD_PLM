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
        srcc = "/plm/jsp/project/material/ListMaterial.jsp"
    }else if(str == "create"){
        srcc = "/plm/jsp/project/material/CreateMaterial.jsp";
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
                    <td height="25" bgcolor=white><img src="/plm/portal/icon/sub_arr.gif" width=22 height=9 border=0><a href="javascript:selectFrame('list','')" id=sub onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "02215") %><%--원재료 목록--%></td>
                </tr>
                <tr>
                    <td height="25" bgcolor=white><img src="/plm/portal/icon/sub_arr.gif" width=22 height=9 border=0><a href="javascript:selectFrame('create','')" id=sub onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "02214") %><%--원재료 등록--%></td>
                </tr>
                <tr>
                    <td height="25" bgcolor=white><img src="/plm/portal/icon/sub_arr.gif" width=22 height=9 border=0><a href="javascript:selectFrame('MATERIALMAKER','원재료 Maker')" id=sub onFocus="this.blur()">Maker</td>
                </tr>
                <tr>
                    <td height="25" bgcolor=white><img src="/plm/portal/icon/sub_arr.gif" width=22 height=9 border=0><a href="javascript:selectFrame('MATERIALTYPE','원재료 Type')" id=sub onFocus="this.blur()">Type</td>
                </tr>
                <tr>
                    <td height="25" bgcolor=white><img src="/plm/portal/icon/sub_arr.gif" width=22 height=9 border=0><a href="javascript:selectFrame('MATERIALPROPERTIES','원재료 특성')" id=sub onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "02223") %><%--원재료특성--%></td>
                </tr>

            </table>
        </td>
        <td width="100%" height="100%">
            <iframe frameborder=0 width="100%" height="100%" src="/plm/jsp/project/material/ListMaterial.jsp" name=iframe scrolling=auto></iframe>
        </td>
    </tr>
</table>
<script src='/plm/portal/js/menu.js'></script>
<input type=hidden name=myname value="원재료관리">
<script>setPosition(null);</script>
</form>
</body>
</html>

