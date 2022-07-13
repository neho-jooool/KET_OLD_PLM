<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/jsp/common/context.jsp"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<HTML>
<head>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<script type="text/javascript">
<!--
    function detail(state1)
    {
        if(document.all.detail==null){
            return;
        }
        if(state1 == "open"){
            document.all.openDetail.style.display = 'none';
            document.all.closeDetail.style.display = '';
            document.all.detail.style.display = '';
        }else{
            document.all.openDetail.style.display = '';
            document.all.closeDetail.style.display = 'none';
            document.all.detail.style.display = 'none';
        }
    }
//-->
</script>
</head>
<BODY>
    <table border="0" cellspacing="0" cellpadding="0" class="tab_popup03">
        <tr>
            <td>
                <table width="100%" border="0" cellpadding="0" cellspacing="0"
                    class="popBox">
                    <tr>
                        <td class="boxTLeft"></td>
                        <td class="boxTCenter"></td>
                        <td class="boxTRight"></td>
                    </tr>
                    <tr>
                        <td class="boxLeft"></td>
                        <td align="center">
                            <!------------------------------------- 본문 시작 //----------------------------------------->
<%
    Throwable throwable = (Throwable) request.getAttribute("javax.servlet.jsp.jspException");
    if (throwable == null) {
        throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
    }
    String euri = (String) request.getAttribute("javax.servlet.error.request_uri");
%>
                            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                <tr>
                                    <td class="tdwhiteM1" colspan=2><font class="titleD"><%=messageService.getString("e3ps.message.ket_message","02116")%><%--에러처리 페이지--%></font></td>
                                </tr>
                            </table>
                            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                <tr>
                                    <td class="tab_btm2"></td>
                                </tr>
                            </table>
                            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                <tr>
                                    <td class="tab_btm1"></td>
                                </tr>
                            </table>
                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <col width="20%">
                                <col width="80%">
                                <tr>
                                    <td class="tdblueL">URL</td>
                                    <td class="tdwhiteL"><%=euri%></td>
                                </tr>
                                <tr>
                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02114") %><%--에러 원인--%></td>
                                    <td class="tdwhiteL"><font class="blue2"><%= throwable.getMessage()%></font></td>
                                </tr>
                                <tr>
                                    <td class="tdblueL"><span id="openDetail" style="display:"><a
                                            href="JavaScript:detail('open')"><%=messageService.getString("e3ps.message.ket_message", "02115") %><%--에러내용보기--%>
                                                ▼</a></span> <span id="closeDetail" style="display: none"><a
                                            href="JavaScript:detail('close')"><%=messageService.getString("e3ps.message.ket_message", "02115") %><%--에러내용보기--%>
                                                ▲</a></span></td>
                                    <td class="tdwhiteL"><%=messageService.getString("e3ps.message.ket_message", "02014") %><%--시스템 관리자에게 문의하십시오--%></td>
                                </tr>
                                <tr>
                                    <td class="tdwhiteL" colspan=2>
                                        <table border="0" cellspacing="0" cellpadding="0" id="detail"
                                            style="display: none">
                                            <tr>
                                                <td>
                                                    <%  throwable.printStackTrace(new java.io.PrintWriter(out)); %>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table> <!------------------------------------- 본문 끝 //----------------------------------------->
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
</BODY>
</HTML>
