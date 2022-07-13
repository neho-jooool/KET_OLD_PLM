<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil,
                e3ps.common.code.NumberCodeType"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/common/context.jsp"%>


<%@page import="e3ps.common.util.WCUtil"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=messageService.getString("e3ps.message.ket_message", "01050") %><%--금형관리(좌측)--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script src="/plm/portal/js/script.js" type="text/javascript"></script>
<script language="JavaScript">
<!--
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
<body>
<table width="170" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td><img src="../images/img_default/left/left_img_top2.gif" width="170" height="24"></td>
    </tr>
</table>
<table width="100%" height="33" border="0" cellpadding="0" cellspacing="0" bgcolor="#A863A8" style="margin:0px 0px 10px 0px">
    <tr>
        <td width="30" align="center"><img src="../images/img_default/icon/icon_06.gif" width="11" height="11"></td>
        <td class="font_06leftmenu"><%=messageService.getString("e3ps.message.ket_message", "01592") %><%--부품승인관리--%></td>
        <td width="10"><!--img src="../images/img_default/left/side_01.gif" width="10" height="33"--></td>
    </tr>
</table>
<table width="170" border="0" cellpadding="0" cellspacing="0" style="margin-bottom:10px">
    <!-- 업체선정요청서 검색 -->
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/mrm/jsp/SearchSelVendorRequest.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "02112") %><%--업체선정요청서 검색--%></a>
        </td>
    </tr>
    <!-- 업체선정요청서 등록 -->
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/mrm/jsp/CreateSelVendorRequest.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "02113") %><%--업체선정요청서 등록--%></a>
        </td>
    </tr>
    <!-- 금형제작의뢰서 검색 -->
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/mrm/jsp/SearchProdMoldRequest.jsp" class="leftmenu1" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01577") %><%--부품(금형)제작의뢰서 검색--%></a>
        </td>
    </tr>
    <!-- 금형제작의뢰서 등록 -->
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/mrm/jsp/CreateProdMoldRequest.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01578") %><%--부품(금형)제작의뢰서 등록--%></a>
        </td>
    </tr>
    <!-- 부품승인의뢰서 검색 -->
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/mrm/jsp/SearchPartApprovalRequest.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01593") %><%--부품승인의뢰서 검색--%></a>
        </td>
    </tr>
    <!-- 금형승인 현황 -->
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/mrm/jsp/StatusBoardForPartApp.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01091") %><%--금형승인 현황--%></a>
        </td>
    </tr>
    <!-- 모델별 승인현황 -->
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/mrm/jsp/StatusBoardForModelPartApp.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01373") %><%--모델별 승인현황--%></a>
        </td>
    </tr>
    <!-- 업체별 승인현환 -->
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/mrm/jsp/StatusBoardForVendorPartApp.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "02111") %><%--업체별 승인현황--%></a>
        </td>
    </tr>
    <!-- S/W별 승인현황 -->
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/mrm/jsp/StatusBoardForSWPartApp.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "02593") %><%--제품명별 승인현황--%></a>
        </td>
    </tr>
    <!-- 금형관련 Mail  -->
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/mrm/jsp/MailManager.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01363") %><%--메일 관리--%></a>
        </td>
    </tr>

</table>
<table width="170" border="0" cellpadding="0" cellspacing="0" style="margin-bottom:10px">
    <tr>
        <td><img src="../images/img_default/left/left_bg_line.gif" width="169" height="3"></td>
    </tr>
</table>
</body>
</html>
