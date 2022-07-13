<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.org.*"%>
<%@page import="e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
    String oid = request.getParameter("oid");
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "02936") %><%--템플릿 저장--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script language=JavaScript>
<!--
function create(obj){
    if(isNullData(document.forms[0].name.value)){
        alert("<%=messageService.getString("e3ps.message.ket_message", "00520") %><%--Templete 명을 입력해 주십시오--%>");
        return;
    }

    disabledAllBtn();
    showProcessing();
    document.forms[0].action = "SaveTempleteProject.jsp";
    document.forms[0].method = "post";
    document.forms[0].submit();
}
//-->
</script>
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
<%@include file="/jsp/common/processing.html"%>
<FORM method=post>
<input type=hidden name=oid value="<%=oid%>">

<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00513") %><%--Template 저장--%></td>
                <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "00513") %><%--Template 저장--%> </td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <a href="javascript:create(this);">
                        <img src="/plm/portal/images/img_default/button/board_btn_ok.gif" alt='<%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%>' width="62" height="20" border="0">
                        </a>
                        &nbsp;&nbsp;
                        <a href="javascript:self.close();">
                        <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                        </a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td class="tab_btm5"></td>
    </tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td class="tab_btm1"></td>
    </tr>
</table>
<table cellspacing="0" cellpadding="2" border="0" width="100%">
    <tr>
        <td width="25%" class="tdblueL" >&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00519") %><%--Templete 명--%> <font color="red">*</font></td>
        <td width="75%" class="tdwhiteL"  align="center"><input type="text" name="name" style="width:100%;"></td>
    </tr>
</table>
<!--//-->
</form>
</body>
</html>

<td width="37%">
