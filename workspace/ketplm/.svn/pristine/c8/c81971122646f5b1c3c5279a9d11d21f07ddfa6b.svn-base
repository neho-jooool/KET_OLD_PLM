<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.org.*"%>
<%@page import="e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
    HistoryType[] historyList = HistoryType.getHistoryTypeSet();
    String oid = request.getParameter("oid");
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "02367") %><%--일정이력 저장--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
<script language=JavaScript>
<!--
function create(obj){
    if ( isNullData(document.forms[0].issue.value) ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02275") %><%--이력저장 사유를 지정하세요--%>');
        document.forms[0].issue.focus();
        return;
    }

    if(isNullData(document.forms[0].description.value)){
        alert("<%=messageService.getString("e3ps.message.ket_message", "01870") %><%--설명을 입력해 주십시오--%>");
        return;
    }

    disabledAllBtn();
    showProcessing();

    //document.forms[0].action = "SaveProjectSchedule.jsp";
    document.forms[0].action = "/plm/servlet/e3ps/ProjectServlet";
    document.forms[0].command.value = "historyUpdate";
    document.forms[0].method = "post";
    document.forms[0].submit();
}
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@include file="/jsp/common/processing.html"%>
<FORM method=post>
<!-- Hidden Value -->
<input type=hidden name=oid value="<%=oid%>">
<input type="hidden" name="command">
<table width="95%" height=40 border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
        <td height="30">
            <!--title//-->
            <table border=0 cellpadding=0 cellspacing=0>
                <tr>
                    <td><img src=/plm/portal/images/title2_left.gif></td>
                    <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "02367") %><%--일정이력 저장--%></td>
                    <td><img src=/plm/portal/images/title2_right.gif></td>
                </tr>
            </table>
            <!--title end//-->
        </td>
        <td align="right">
            <input type=button id=button class="btnTras" onclick="JavaScript:create(this);" value='<%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%>'>&nbsp;
            <input type=button id=button class="btnTras" onclick="Javascript:history.go(-1);" value='<%=messageService.getString("e3ps.message.ket_message", "01309") %><%--뒤로--%>'>&nbsp;
            <input type=button id=button class="btnTras" onclick="JavaScript:self.close();" value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>'>
        </td>
    </tr>
</table>
<table width="95%" cellspacing="0" cellpadding="2" border="0" class=tb1 align=center>
    <tr bgcolor="#D6DBE7" align=center><td colspan="2" align=center id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02368") %><%--일정이력 저장시--%> <font color="red">*</font> <%=messageService.getString("e3ps.message.ket_message", "00026") %><%--{0}는 필수항목입니다--%>.</td></tr>
    <tr>
        <td id=tb_gray width="30%" bgcolor="#e7e7e7">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02274") %><%--이력저장 사유--%><font color="red">*</font></td>
        <td id=tb_gray width="70%" bgcolor="#f5f5f5">
            <select name="issue" id=i style="width:100%">
                <option value="" selected>-------- <%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%> --------</option>
<%
    for ( int i = 0 ; i < historyList.length ; i++ ) {
        if( !historyList[i].isSelectable() ) continue;
%>
                <option value="<%=historyList[i]%>" ><%=historyList[i].getDisplay(messageService.getLocale())%></option>
<%  }  %>
            </select>
        </td>
    </tr>
    <tr>
        <td id=tb_gray bgcolor="EBEFF3">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%> <font color="red">*</font></td>
        <td id=tb_gray bgcolor="ffffff" align="center"><textarea name="description" rows="6" style="width:99%" id=i></textarea></td>
    </tr>
</table>
<!--//-->
</form>
</body>
</html>

<td width="37%">
