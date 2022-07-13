<HTML>
<%@page import ="e3ps.common.folder.FolderUtil,
              e3ps.common.util.CommonUtil,
              wt.folder.Folder"
%>
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:include page="/jsp/common/contenxt.jsp" flush="false"/>
<HEAD>
<TITLE><%=messageService.getString("e3ps.message.ket_message", "02905") %><%--캐비넷 생성--%></TITLE>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=javascript src="/plm/jsp/groupware/folder/folder.js"></SCRIPT>
<script language="javascript">
<!--
function create() {
  if(checkField(document.forms[0].folderName,"이름") ||
    isIncludeSlash(document.forms[0].folderName.value)){
    return;
  }

  disabledAllBtn();
  document.forms[0].oid.value = document.forms[0].parentfolder.value;
  document.forms[0].cmd.value = "save";
  document.forms[0].submit();
}
function selectDomain(str,action){
  var addr = "/plm/jsp/groupware/folder/SelectDomain.jsp?domain="+str+"&action="+action;
  openWindow(addr, "Domain", 350, 350);
}
function selectFolder(parent, target)
{
  var url = "/plm/jsp/groupware/folder/SelectFolder.jsp?folderpath="+parent+"&target="+target;
  openWindow(url, "SelectFolder", 300, 400);
}
//-->
</script>
</HEAD>
<%
  String cmd = request.getParameter("cmd");
  String oid = request.getParameter("oid");
  Folder cabinet = (Folder)CommonUtil.getObject(oid);
  if("save".equals(cmd))
  {
    String folderName = request.getParameter("folderName");
    String msg = "";
      wt.util.SortedEnumeration sen = FolderUtil.getSubFolders((Folder)cabinet);
      while(sen.hasMoreElements())
      {
          wt.folder.Folder folder = (wt.folder.Folder) sen.nextElement();
          if(folderName.equals(folder.getName()))
             msg = messageService.getString("e3ps.message.ket_message", "02287")/*이미 존재하는 이름입니다*/;
      }

      if(msg.length() == 0)
    {
          wt.folder.Folder folder = FolderUtil.createFolder((Folder)cabinet, folderName);
          msg = folder == null ? messageService.getString("e3ps.message.ket_message", "01796")/*생성 실패하였습니다*/ :messageService.getString("e3ps.message.ket_message", "01892")/*성공적으로 생성되었습니다*/;
    }
%>  <SCRIPT>
      alert("<%=msg%>");
      opener.document.location.reload();
      self.close();
    </SCRIPT>
<%  return;
  }
%>
<BODY>
<FORM method=post action='/plm/jsp/groupware/folder/CreateFolder.jsp'>
  <input type=hidden name=oid value='<%=oid%>'>
  <input type=hidden name=cmd>
<!-----------상단타이틀 및 버튼//-------------------->
<table width="95%" height=40 align=center border=0>
  <tr>
    <td width="296" align="left">
      <table border=0 cellpadding=0 cellspacing=0 >
      <tr>
        <td><img src=/plm/portal/images/title2_left.gif></td>
        <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "02999") %><%--폴더 생성--%></td>
        <td><img src=/plm/portal/images/title2_right.gif></td>
      </tr>
      </table>
    </td>
    <td align=right>
      <input type=button value='<%=messageService.getString("e3ps.message.ket_message", "01795") %><%--생성--%>' onclick="create()" id=button>&nbsp;&nbsp;
      <input type=button value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' onclick="self.close()" id=button>
    </td>
  </tr>
</table>
<!--------상단타이틀 끝//--------->
<table border="0" cellpadding="1" cellspacing="1" width="95%" bgcolor="#AABDC6" align="center">
  <tr  bgcolor="#D8E0E7" align=center height=23>
      <td width="1%" bgcolor="#D8E0E7" id=title colspan=2><%=messageService.getString("e3ps.message.ket_message", "02999") %><%--폴더 생성--%></td>
  </tr>
  <tr>
    <td align=center width=30% bgcolor=EBEFF3><%=messageService.getString("e3ps.message.ket_message", "01757") %><%--상위 폴더--%></td>
    <td align=left width=70% bgcolor=ffffff>
      <input type=hidden name='parentfolder' value='<%=oid%>'>
      <input type=text id=i name='parentfolderName' value='<%=cabinet.getFolderPath()%>' readonly>
      <input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>' onClick="selectFolder('<%=cabinet.getFolderPath()%>','parentfolder')" id=innerbutton>
    </td>
  </tr>
  <tr>
    <td align=center width=30% bgcolor=EBEFF3><%=messageService.getString("e3ps.message.ket_message", "03002") %><%--폴더 이름--%></td>
    <td align=left width=70%  bgcolor=ffffff><input type=text id=input name=folderName ></td>
  </tr>
<%--   <tr bgcolor=f5f5f5>
    <td align=center width=80 >  도메인 :  </td>
    <td align=left width=180 >
      <input type=text id=i name=newdomain value=<%//=windchillPdmPath%>>
      <a href= 'javascript:selectDomain("domain","new");'>browse</a>
    </td>
  </tr>
--%>
</table>
</FORM>
</BODY>
</HTML>
