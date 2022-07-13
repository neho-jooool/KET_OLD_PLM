<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import = "e3ps.common.jdf.config.Config,
                  e3ps.common.jdf.config.ConfigImpl"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/common/context.jsp"%>

<%@page import="e3ps.common.util.CommonUtil"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=messageService.getString("e3ps.message.ket_message", "03211") %><%--현황관리--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script src="/plm/portal/js/script.js" type="text/javascript"></script>
<style type="text/css">
<!--

-->
</style>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td><img src="../images/img_default/left/left_img_top2.gif" width="100%" height="24"></td>
    </tr>
</table>
<table width="100%" height="33" border="0" cellpadding="0" cellspacing="0" bgcolor="#8DC63F" style="margin:0px 0px 10px 0px">
    <tr>
        <td width="30" align="center"><img src="../images/img_default/icon/icon_06.gif" width="11" height="11"></td>
        <td class="font_06leftmenu"><%=messageService.getString("e3ps.message.ket_message", "03211") %><%--현황관리--%></td>
        <td width="10"><!--img src="../images/img_default/left/side_01.gif" width="10" height="33"--></td>
    </tr>
</table>
<%
boolean isAdmin = CommonUtil.isAdmin();
boolean isPJT_CHART1_A = true;
boolean isPJT_CHART2_A = true;
boolean isPJT_CHART3_A = true;
boolean isPJT_CHART4_A = true;


isPJT_CHART1_A = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("PJT", "선행프로젝트", "A");
isPJT_CHART2_A = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("PJT", "프로그램", "A");
isPJT_CHART3_A = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("PJT", "부서별", "A");
isPJT_CHART4_A = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("PJT", "예산대비실적", "A");

%>

<table width="170" border="0" cellpadding="0" cellspacing="0" style="margin-bottom:10px">
    <%if(isPJT_CHART1_A || isAdmin){ %>
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/project/chart/ProjectMainChart2.jsp?pjtType=0" class="leftmenu1" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01833") %><%--선행개발 현황--%></a>
        </td>
    </tr>
    <%} %>
    <%if(isPJT_CHART2_A || isAdmin){ %>
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/project/chart/ProjectMainChart.jsp" class="leftmenu1" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "03043") %><%--프로그램 현황--%></a>
        </td>
    </tr>
    <%} %>
    <%if(isPJT_CHART3_A || isAdmin){ %>
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/project/chart/ProgramDepartmentChart2.jsp" class="leftmenu1" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01558") %><%--부서별 업무현황--%></a>
        </td>
    </tr>
    <%} %>
    <%if(isPJT_CHART4_A || isAdmin){ %>
    <!-- 예산대비 실적 현황 -->
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/project/chart/SearchSAPCost.jsp" class="leftmenu1" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "02145") %><%--예산대비 실적 현황--%></a>
        </td>
    </tr>
    <%} %>

    <%if(isAdmin){ %>
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/project/chart/ProjectOutputChart.jsp" class="leftmenu1" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "03075") %><%--프로젝트 산출물 현황--%></a>
        </td>
    </tr>
    <tr>
        <td height="20" style="padding-left:10">
            <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
            <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/manage/ProjectOutputPage.jsp" class="leftmenu1" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "03073") %><%--프로젝트 산출물 목록--%></a>
        </td>
    </tr>

    <%} %>

</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:10px">
    <tr>
        <td><img src="../images/img_default/left/left_bg_line.gif" width="100%" height="3"></td>
    </tr>
</table>
</body>
</html>
