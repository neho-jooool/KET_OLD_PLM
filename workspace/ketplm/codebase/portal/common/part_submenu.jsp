<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>

<%@page import="e3ps.common.util.WCUtil"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=messageService.getString("e3ps.message.ket_message", "00087") %><%--BOM관리(좌측)--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script src="/plm/portal/js/script.js" type="text/javascript"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>

<script language="JavaScript">
<!--
function viewTree() {
    //document.all.txtLoading.style.display = '';
    document.all.treeDiv.style.display='';
    iframe.document.location = "/plm/jsp/common/loading.jsp?url=/plm/portal/part/tree.jsp&key=viewName&value=Engineering";
}

function bomedit(src)
{
    openWindow(src,"ecad",300,300);
}

-->
</script>
<style type="text/css">
<!--
body {
    background-image: url(../images/img_default/left/left_bg2.gif);
    background-repeat:no-repeat
    background-color: #ffffff;
    margin-top: 0px;
    margin-left:0px;
}
-->
</style>
</head>
<%
    e3ps.common.jdf.config.Config conf = e3ps.common.jdf.config.ConfigImpl.getInstance();
    String cabinet = "";//CommonUtil.getOIDString(wt.clients.folder.FolderTaskLogic.getFolder(conf.getString("cabinet.wtpart"),WCUtil.getWTContainerRef()));
    String whereEng = "key=view&value=Engineering&key=folderoid&value="+cabinet;
    String whereManu = "key=view&value=Engineering&key=folderoid&value="+cabinet;
    boolean isAdmin = CommonUtil.isAdmin();

    boolean isPRT_C = true;
    boolean isPRT_V = true;
    boolean isPRT_ECAD = true;
//	isPRT_C = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("PRT", "BOM관리", "C");
//	isPRT_V = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("PRT", "BOM관리", "V");
//	isPRT_ECAD = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("PRT", "ECADEdit", "A");

%>
<body>
<table width="170" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td><img src="../images/img_default/left/left_img_top2.gif" width="170" height="24"></td>
    </tr>
</table>
<table width="100%" height="33" border="0" cellpadding="0" cellspacing="0" bgcolor="#A863A8" style="margin:0px 0px 10px 0px">
    <tr>
        <td width="30" align="center"><img src="../images/img_default/icon/icon_06.gif" width="11" height="11"></td>
        <td class="font_06leftmenu"><%=messageService.getString("e3ps.message.ket_message", "00086") %><%--BOM관리--%></td>
        <td width="10"><!--img src="../images/img_default/left/side_01.gif" width="10" height="33"--></td>
    </tr>
</table>
<table width="170" border="0" cellpadding="0" cellspacing="0" style="margin-bottom:10px">
    <!-- 부품 검색 -->
    <%if(isPRT_V || isAdmin){ %>
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/bom/SearchPart.jsp&<%=whereEng%>" class="leftmenu1" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01565") %><%--부품 검색--%></a>
        </td>
    </tr>
    <%} %>

    <!-- 부품채번관리 -->
    <%if(isPRT_C || isAdmin){ %>
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/bom/CreatePart.jsp&<%=whereEng%>" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01598") %><%--부품채번관리--%></a>
        </td>
    </tr>
    <%} %>

    <!-- BOM 편집 -->
    <%if(isPRT_C || isAdmin){ %>
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/bom/BOMEditorLoading.jsp" target="BOMEditor" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "00090") %><%--BOM편집--%></a>
        </td>
    </tr>
    <%} %>

    <!-- 부품 등록 Test -->
    <%if(isPRT_C || isAdmin){ %>
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/bom/createPartTest.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01567") %><%--부품 등록 테스트--%></a>
        </td>
    </tr>
    <%} %>
</table>
<table width="170" border="0" cellpadding="0" cellspacing="0" style="margin-bottom:10px">
    <tr>
        <td><img src="../images/img_default/left/left_bg_line.gif" width="169" height="3"></td>
    </tr>
</table>
</body>
</html>
