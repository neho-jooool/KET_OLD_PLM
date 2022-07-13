<HTML>
<%@page import ="e3ps.common.folder.FolderUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>


<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<!-- 로그 설정 임포트 끝 -->
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:include page="/jsp/common/contenxt.jsp" flush="false"/>
<%
    e3ps.common.jdf.message.Message message = e3ps.common.jdf.message.MessageBox.getInstance("message");
%>
<HEAD>
<TITLE><%=messageService.getString("e3ps.message.ket_message", "02905") %><%--캐비넷 생성--%></TITLE>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/jsp/groupware/folder/folder.js"></SCRIPT>
<script language="javascript">
<!--
function save1() {
    if(checkField(document.forms[0].cabinetname,"이름")
        || checkField(document.forms[0].domainname,"도메인")
        || isIncludeSlash(document.forms[0].cabinetname.value)){
        return;
    }

    disabledAllBtn();
    document.forms[0].cmd.value = "save";
    document.forms[0].submit();
}
//-->
</script>
</HEAD>
<%
    String cmd = request.getParameter("cmd");
    String cabinetName = request.getParameter("cabinetname");
    
    Kogger.debug("CreateCabinet", null, null, cmd);
    if("save".equals(cmd))
    {
        String msg = "";
        wt.util.SortedEnumeration sen = FolderUtil.getAllCabinets(true);
        while(sen.hasMoreElements())
        {
            wt.folder.Folder folder = (wt.folder.Folder) sen.nextElement();
            if(cabinetName.equals(folder.getName()))
               msg = messageService.getString("e3ps.message.ket_message", "02287")/*이미 존재하는 이름입니다*/;
        }
        if(msg.length() == 0)
        {
            String domain = request.getParameter("domain");
            wt.admin.AdminDomainRef ref = wt.admin.AdministrativeDomainHelper.getAdminDomainRef(domain);
            wt.folder.Cabinet ca = FolderUtil.createCabinet(cabinetName, ref);
            msg = ca == null ? message.getMessage("save.fail") : message.getMessage("save.success");
        }
%>  <SCRIPT>
            alert("<%=msg%>");
            opener.document.location.reload();
            self.close();
        </SCRIPT>
<%  return;
    }
%>
<BODY onload='document.forms[0].cabinetname.focus()'>
<FORM method=post action='/plm/jsp/groupware/folder/CreateCabinet.jsp'>
<input type=hidden name=cmd>
<div class=div_title><SCRIPT>printTitle('<%=messageService.getString("e3ps.message.ket_message", "02905") %><%--캐비넷 생성--%>')</SCRIPT></div>
<div class=div_title_action>
    <input type=button value='<%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%>' onclick="save1()" id=button>
    <input type=button value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' onclick="closeWindow()" id=button>
</div>

<div class=div_body_title><%=messageService.getString("e3ps.message.ket_message", "02905") %><%--캐비넷 생성--%></div>
<div class=div_body_row>
    <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "02906") %><%--캐비넷명--%></div>
    <div class=div_row_long><input type=text id=input name=cabinetname></div>
</div>
<div class=div_body_row>
    <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "01251") %><%--도메인--%></div>
    <div class=div_row_long>
        <input type=text id=i name=domainname readonly>
        <input type=hidden name=domain>
        <input type=button value='<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>' id=innerbutton onclick='javascript:selectDomain("domain")'>
    </div>
</div>
</FORM>
</BODY>
</HTML>
