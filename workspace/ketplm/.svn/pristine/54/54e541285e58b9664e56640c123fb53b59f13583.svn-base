<HTML>
<%@page import ="wt.folder.*,wt.admin.*,
         e3ps.common.folder.FolderUtil,
         e3ps.common.util.CommonUtil,
         wt.fc.*"%>

<%@page contentType="text/html; charset=UTF-8"%>


<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<!-- 로그 설정 임포트 끝 -->
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/common/context.jsp"%>
<%
e3ps.common.jdf.message.Message message = e3ps.common.jdf.message.MessageBox.getInstance("message");
%>
<HEAD>
<TITLE>Edit Folder</TITLE>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/jsp/groupware/folder/folder.js"></SCRIPT>
<script language="javascript">
<!--
function edit1() {
  if(checkField(document.forms[0].name, "이름")
    || isIncludeSlash(document.forms[0].name.value)) return;

  disabledAllBtn();
  document.forms[0].cmd.value="edit";
  document.forms[0].submit();
}
function save1() {
  if(checkField(document.forms[0].newfolder, "이름")){
    return;
  }
  disabledAllBtn();
  document.forms[0].cmd.value="save";
  document.forms[0].submit();
}
function delete1(){
  if(!confirm('<%=message.getMessage("confirm.delete")%>')) return;
  disabledAllBtn();
  document.forms[0].cmd.value="delete";
  document.forms[0].submit();
}
function alertNclose(msg)
{
  alert(msg);
  opener.document.location.reload();
  self.close();
}
//-->
</script>
</HEAD>
<%
  String cmd = request.getParameter("cmd");
  String oid = request.getParameter("oid");
  Folder folder = (Folder)CommonUtil.getObject(oid);
  wt.admin.AdminDomainRef domainRef = wt.admin.DomainAdministeredHelper.getAdminDomainRef((wt.admin.DomainAdministered)folder);

  String msg = "";

  if("edit".equals(cmd))
  {
    String name = request.getParameter("name");
    Kogger.debug("EditFolder", null, null, " ################"+name);
    String domain = request.getParameter("domain");
        wt.admin.AdminDomainRef ref = wt.admin.AdministrativeDomainHelper.getAdminDomainRef(domain);

    if(folder instanceof Cabinet)
        folder = FolderHelper.service.updateCabinet((Cabinet)folder, name, ref);
    else
    {
        try{
          folder = (Folder)FolderHelper.service.updateSubFolder((SubFolder)folder, name, ref, false);
        }catch(wt.util.WTException e){
            msg = e.getLocalizedMessage(java.util.Locale.KOREA);
        }
    }

    if(folder == null)
      msg = messageService.getString("e3ps.message.ket_message", "01943")/*수정 실패하였습니다*/;
    else
      msg = msg.length()>0 ? msg : message.getMessage("update.success");
  }
  else if("save".equals(cmd))
  {
    String newfolder = request.getParameter("newfolder");
    folder = FolderUtil.createFolder(folder , newfolder);

    if(folder == null)
      msg = messageService.getString("e3ps.message.ket_message", "01796")/*생성 실패하였습니다*/;
    else
      msg = message.getMessage("save.success");
  }
  else if("delete".equals(cmd))
  {
    if(folder instanceof Cabinet){
      msg = messageService.getString("e3ps.message.ket_message", "02907")/*캐비넷은 삭제할 수 없습니다*/;
    }else{
        if("Checked Out".equalsIgnoreCase(folder.getName()))
              msg = messageService.getString("e3ps.message.ket_message", "01712")/*삭제할 수 없는 폴더입니다*/;
        else
        {
            try{
              PersistenceHelper.manager.delete(folder);
              msg = message.getMessage("delete.success");
            }catch(wt.util.WTException e){
                msg = e.getLocalizedMessage(java.util.Locale.KOREA);
            }
        }
    }
  }

  if(msg.length() > 0)
  {
%>    <SCRIPT LANGUAGE="JavaScript">alertNclose('<%=msg%>')</SCRIPT>  <%
    return;
  }
%>
<BODY>
<form method=post>
<input type=hidden name=oid value='<%=oid%>'>
<input type=hidden name=cmd>

<div class=div_title><SCRIPT>printTitle('<%=messageService.getString("e3ps.message.ket_message", "03004") %><%--폴더수정--%>')</SCRIPT></div>
<div class=div_title_action>
  <input type=button value="<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>" onclick="edit1()" id=button>
  <input type=button value="<%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%>" onclick="delete1()" id=button>
  <input type=button value="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" onclick="self.close()" id=button>
</div>

<div class=div_body_title><%=messageService.getString("e3ps.message.ket_message", "03004") %><%--폴더수정--%></div>
<div class=div_body_row>
  <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "03003") %><%--폴더명--%></div>
  <div class=div_row_long><input type=text id=input name=name value="<%=folder.getName()%>"></div>
</div>
<div class=div_body_row>
  <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "01251") %><%--도메인--%></div>
  <div class=div_row_long>
    <input type=text id=i name=domainname readonly value='<%=domainRef.getName()%>'>
    <input type=hidden name=domain value='<%=AdministrativeDomainHelper.manager.getDomainPath(domainRef)%>'>
<%if(folder instanceof Cabinet || CommonUtil.isAdmin()){ %>
    <input type=button value='<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>' id=innerbutton onclick='javascript:selectDomain("domain")'>
<%} %>
  </div>
</div>
<br>
<br>
<div class=div_title><SCRIPT>printTitle('<%=messageService.getString("e3ps.message.ket_message", "03142") %><%--하위 폴더 추가--%>')</SCRIPT></div>
<div class=div_title_action><input type=button value="<%=messageService.getString("e3ps.message.ket_message", "03142") %><%--하위 폴더 추가--%>" onclick="save1()" id=button></div>

<div class=div_body_title><%=messageService.getString("e3ps.message.ket_message", "03141") %><%--하위 폴더--%></div>
<div class=div_body_row>
  <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "03003") %><%--폴더명--%></div>
  <div class=div_row_long><input type=text id=input name=newfolder id=input></div>
</div>
</form>
</BODY>
</HTML>
