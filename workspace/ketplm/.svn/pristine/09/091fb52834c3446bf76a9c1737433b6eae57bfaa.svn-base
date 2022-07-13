<%@page contentType="text/html; charset=UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String color = request.getParameter("color");
    if(color == null) color="bbdddd";
        String colspan = request.getParameter("colspan");
    if(color == null) colspan="";
%>
<SCRIPT language=JavaScript src="/plm/portal/js/contents.js"></SCRIPT>



        <TD class=sub01>
            <INPUT type='hidden' name='File' value=''>
            <INPUT type='hidden' name='targetType' value='FILE'>
            <INPUT type='hidden' name='rememberedFile' value=''>
            <INPUT type='hidden' name='rememberedURL' value=''>
            <INPUT type='hidden' name='thisPage' value='FirstTimeCheckInDocument'>
            <p>주 첨부파일<BR>(<%=messageService.getString("e3ps.message.ket_message", "00011", 20) %><%--{0}MB이하만--%>)</p>
        </TD>
        <TD class=sub02_left>
                <table border=0 width=100% cellpadding=0 cellspacing=0 >
                <tr>
                <td width=60><INPUT type='radio' name='primaryContentType' value='file' onClick='switchToFile()'>File</td>
                <Td width=100%>&nbsp;<INPUT type='file' name='PRIMARY' size=45 onchange='changedFile()' id=input></td>
                </td>
                </tr>
                <tr>
                <td bgcolor=ffffff><INPUT type='radio' name='primaryContentType' value='url' onClick='switchToURL()'>URL</td>
                <td>&nbsp;<INPUT type='text' name='primaryURL' size=60 value='' onchange='changedURL()' id=input></td>
                </td>
                </tr>
                <tr>
                <td bgcolor=ffffff colspan=2>
                <INPUT name='primaryContentType' type='radio' value='none' checked onClick='switchToNone()'><%=messageService.getString("e3ps.message.ket_message", "02669") %><%--주 첨부파일이 없습니다--%>
                </td>
                </tr>
                </table>
        </TD>
    </TR>


