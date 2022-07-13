<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import = "e3ps.common.util.CommonUtil,
                wt.folder.Folder,
                wt.org.*,
                e3ps.common.util.CommonUtil,
                e3ps.groupware.company.Department,
                wt.clients.folder.FolderTaskLogic"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="e3ps.equipment.beans.UnitErrorProcessHelper"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%-- e3ps --%>

<%
    wt.org.WTUser checkUser = (wt.org.WTUser) wt.session.SessionHelper.manager.getPrincipal();
    String orgName = checkUser.getOrganizationName();
    if("dist".equals(orgName)) {
%>
    <script>
        alert('<%=messageService.getString("e3ps.message.ket_message", "02267") %><%--이 페이지에 접근할 권한이 없습니다--%>');
        document.href = history.go(-1);
    </script>
<%
    }
%>

<%
    String classification = e3ps.common.util.CharUtil.E2K(request.getParameter("classification"));
    classification = e3ps.common.web.ParamUtil.getStrParameter(classification, "개발");

%>

<html>
<head>
<link rel="stylesheet" href="../css/e3ps.css">
<script src="../js/common.js"></script>
<script src="../js/trcolor.js"></script>

<title><%=messageService.getString("e3ps.message.ket_message", "02297") %><%--이슈 SubMenu--%></title>
<script language="javascript" src="../js/script.js"></script>

</head>
<body >

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td><img src="../images/img_default/left/left_img_top2.gif" width="100%" height="24"></td>
    </tr>
</table>
<table width="100%" height="33" border="0" cellpadding="0" cellspacing="0" bgcolor="#8DC63F" style="margin:0px 0px 10px 0px">
    <tr>
        <td width="30" align="center"><img src="../images/img_default/icon/icon_06.gif" width="11" height="11"></td>
        <td class="font_06leftmenu"><%=messageService.getString("e3ps.message.ket_message", "02299") %><%--이슈 관리--%></td>
        <td width="10">
    </tr>
</table>

<table width="170" border="0" cellspacing="0" cellpadding="0">


<%
    boolean isAuth = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("UEP", "A", e3ps.auth.beans.E3PSAuthFlag.ALL);
    if(CommonUtil.isAdmin() || true){
%>


<%	} %>
    <tr>
        <td height="20" style="padding-left:10">
        <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
        <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/equipment/equipmentList_main.jsp" target="contName" class="2depht"><%=messageService.getString("e3ps.message.ket_message", "02306") %><%--이슈 목록--%></a></td>
    </tr>

<%	// A Code :: 권한관리 Tree 구조에 관리분류 코드값 DOC
    // B Code :: 회사표준
    // C Code ::
    // 			ALL : 모든 권한 ==> A
    // 			CREATE : 생성  ==> C
    // 			REVISION : 개정/수정   ==> N
    // 			VIEW : 파일 내용보기    ==> V
    // 			PRODUCT_VIEW : Product View 보기   ==> P


    //boolean isAuth2 = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("IQC", "IQC", e3ps.auth.beans.E3PSAuthFlag.ALL);
    //boolean isAuth21 = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("IQC", "IQC", e3ps.auth.beans.E3PSAuthFlag.ALL);
    //boolean isAuth22 = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("IQC", "IQC1", e3ps.auth.beans.E3PSAuthFlag.ALL);
    //boolean isAuth23 = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("IQC", "IQC2", e3ps.auth.beans.E3PSAuthFlag.ALL);
    //boolean isAuth24 = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("IQC", "IQC3", e3ps.auth.beans.E3PSAuthFlag.ALL);
    //if(CommonUtil.isAdmin() || isAuth2 || isAuth21|| isAuth22|| isAuth23|| isAuth24){
%>

    <tr>
        <td height="20" style="padding-left:10">
        <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
        <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/equipment/UnitErrorAssignList.jsp" target="contName" class="2depht"><%=messageService.getString("e3ps.message.ket_message", "02300") %><%--이슈 관리 업무 목록--%></a></td>
    </tr>

<%	//} %>
<% if(true){%>
	<tr>
		<td height="20" style="padding-left:10">
		<img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
		<a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/equipment/UnitErrorAssignList_MyAssign.jsp" target="contName" class="2depht"><%=messageService.getString("e3ps.message.ket_message", "00339") %><%--My 관리 업무 목록--%></a></td>
	</tr>

<% } %>


<%
    WTPrincipal wtprincipal = SessionHelper.manager.getPrincipal();
    //ArrayList roles = UnitErrorProcessHelper.getTaskRoles(wtprincipal);

    if(CommonUtil.isAdmin() || true){
%>

    <tr>
        <td height="20" style="padding-left:10">
        <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
        <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/equipment/UnitErrorWorkList.jsp" target="contName" class="2depht"><%=messageService.getString("e3ps.message.ket_message", "02313") %><%--이슈 처리 업무 목록--%></a></td>
    </tr>
<%	} %>
<% if(CommonUtil.isAdmin() || true){%>
	<tr>
		<td height="20" style="padding-left:10">
		<img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
		<a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/equipment/UnitErrorWorkList_MyWork.jsp" target="contName" class="2depht"><%=messageService.getString("e3ps.message.ket_message", "00340") %><%--My 처리 업무 목록--%></a></td>
	</tr>
<% }%>

</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:10px">
    <tr>
        <td><img src="../images/img_default/left/left_bg_line.gif" width="100%" height="3"></td>
    </tr>
</table>

</body>
</html>

