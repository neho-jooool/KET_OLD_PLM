<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="wt.method.*,wt.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="com.infoengine.util.*"%>
<%@ page import="e3ps.common.util.*,e3ps.groupware.company.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="request" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean  id="url_factory" class="wt.httpgw.URLFactory" scope="page" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<html>
<head>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<title><%=messageService.getString("e3ps.message.ket_message", "02979") %><%--패스워드수정--%></title>
<script language="javascript" src="/plm/portal/js/script.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<%@include file="/jsp/common/top_include.jsp" %>
</head>
<body>
<%
    String id = request.getParameter("id");
    if ( id.equals("Administrator")) id="wcadmin";

    String uid="uid="+id;

//  if ( CommonUtil.isAdmin() ) {id="wcadmin";}
%>
<form method=POST action="changePassword.jsp">
<input type=hidden name="instance" value="<%=CompanyState.ldapAdapter%>">
<input type=hidden name="dbuser" value="<%=CompanyState.ldapUser%>">
<input type=hidden name="passwd" value="<%=CompanyState.ldapPassword%>">
<input type=hidden name="object" value="<%=uid%>,<%=CompanyState.ldapDirectoryInfo%>">
<input type=hidden name="modification" value="replace">
<input type=hidden name="field" value="replace">
<input type=hidden name="id" value="<%=id%>">
<table border="0" cellspacing="0" cellpadding="0" class="tab_popup01">
    <tr>
        <td>
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="popBox">
                <tr>
                    <td class="boxTLeft"></td>
                  <td class="boxTCenter"></td>
                    <td class="boxTRight"></td>
                </tr>
                <tr>
                    <td class="boxLeft"></td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="titleP"><%=messageService.getString("e3ps.message.ket_message", "02979") %><%--패스워드수정--%></td>
                            </tr>
                        </table>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <tr>
                                <td class="purple"><%=messageService.getString("e3ps.message.ket_message", "02023") %><%--신규 패스워드--%></td>
                                <td align="right">
                                    <a href="#" onclick="javascript:submitForm();">
                                    <img src="/plm/portal/images/img_default/button/board_btn_modify.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>" width="62" height="20" border="0">
                                    </a>
                                    &nbsp;&nbsp;
                                    <a href="#" onclick="javascript:closeWindow();">
                                    <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                                    </a>
                                    <!--table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td class=fixLeft></td>
                                            <td><input type=button class="btnTras" value="수정" onclick="javascript:submitForm();" ></td>
                                            <td class=fixRight></td>
                                        </tr>
                                    </table>                </td>
                                <td width="55">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td class=fixLeft></td>
                                            <td><input name="button" type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" onclick="closeWindow()"></td>
                                            <td class=fixRight></td>
                                        </tr>
                                    </table-->
                                </td>
                            </tr>
                        </table>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <tr>
                                <td class="space5"> </td>
                            </tr>
                        </table>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td class="small"><%=messageService.getString("e3ps.message.ket_message", "02974") %><%--패스워드 변경시 로그인을 다시 하여야 합니다--%></td>
                            </tr>
                            <tr>
                                <td align="center">
                                  <input type='password' name="field_view" class="txt_field" size="35" engnum="engnum" />
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <input type='password' name="_field_view" class="txt_field" size="35" engnum="engnum" />
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td class="boxRight"></td>
                </tr>
                <tr>
                    <td class="boxBLeft"></td>
                    <td valign="bottom" class="boxBCenter"></td>
                    <td class="boxBRight"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</form>
</body>
</html>

<script language="javascript">
<!--
function submitForm()
{
    if(checkField(document.forms[0].field_view, "패스워드")
        || checkField(document.forms[0]._field_view, "패스워드") )
        return;

    if( document.forms[0].field_view.value != document.forms[0]._field_view.value )
    {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02978") %><%--패스워드를 동일하게 입력하세요--%>');
        return;
    }

    var searchF = '';
    if(document.forms[0].field_view.value != '') {
        searchF = "userPassword=" + document.forms[0].field_view.value;
        document.forms[0].field.value = searchF;
    } else {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00352") %><%--password를 입력하세요--%>");
        document.forms[0].field_view.focus();
        return;
    }
    disabledAllBtn();
    document.forms[0].submit();
}
//-->
</script>
