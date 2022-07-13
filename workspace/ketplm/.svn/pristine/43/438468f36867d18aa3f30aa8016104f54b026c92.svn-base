<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="e3ps.sap.*" %>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<html>

<%
String oid = request.getParameter("oid");
E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
%>

<head>

<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<title><%=messageService.getString("e3ps.message.ket_message", "00219") %><%--ERP 동기화 및 에러 확인--%></title>
<style type="text/css">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;
}
-->
</style>
</head>
<body>
<form>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00218") %><%--ERP 동기화 LOG--%></td>
                <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "00218") %><%--ERP 동기화 LOG--%> </td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <a href="javascript:self.close();">
                        <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                        </a>
                    </td>
                </tr>
            </table>
            <table border="1" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tdblueM" width="30%"><%=messageService.getString("e3ps.message.ket_message", "00225", "<br>") %><%--ERP 동기화{0} 결과 값--%></td>
                    <td class="tdwhiteL" width="70%" >&nbsp;
                        <textarea name="Desc" cols="52" rows="8" class="fm_area" readOnly style="width:100%"><% PJTInfoERPInterface.sendProjectInfoToSap(project, out); %></textarea>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</form>
</body>
</html>

<%
//try{
    //PJTInfoERPInterface.getTaskInfos(project);
//}catch(Exception ex){
    //ex.printStackTrace();
//}
%>
