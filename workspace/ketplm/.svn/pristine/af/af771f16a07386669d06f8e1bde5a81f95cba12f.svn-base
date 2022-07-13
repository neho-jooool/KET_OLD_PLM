<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.groupware.folder.*,e3ps.common.util.CommonUtil,
            wt.folder.Folder"%>
<%@include file="/jsp/common/context.jsp"%>
<jsp:useBean id="list" class="java.util.Vector" scope="request" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<html>
<head>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Folder Tree</title>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<script>
function treeaction(s,i){
  showProcessing();
  disabledAllBtn();
  document.forms[0].action = "/plm/servlet/e3ps/FolderTreeServlet?action="+s+"&index="+i;
  document.forms[0].method = 'post';
  document.forms[0].submit();
}
function editfolder(oid,level,index){
  var str="/plm/jsp/groupware/folder/EditFolder.jsp?oid="+oid+"&level="+level+"&index="+index;
  openWindow(str, "Edit", 400, 250);
}
function createCabinet(){
    var str="/plm/jsp/groupware/folder/CreateCabinet.jsp";
    openWindow(str, "Cabinet", 350, 150);
}
function createFolder(oid){
  var str = "/plm/jsp/groupware/folder/CreateFolder.jsp?oid="+oid;
  openWindow(str, "Folder", 350,150);
}
</script>
</head>
<body text="black" link="blue" vlink="purple" alink="red">
<%@include file="/jsp/common/processing.html"%>
<form method=post>
<%
  String folderPath = request.getParameter("folderpath");
  String folderOid=null;
  if(folderPath!=null && !"null".equals(folderPath))
    folderOid = CommonUtil.getOIDString(e3ps.common.folder.FolderUtil.getFolder(folderPath));

  boolean isAdmin = CommonUtil.isAdmin();
%>
<input type='hidden' name='folderpath' value='<%=folderPath%>'>
<!-----------상단타이틀 및 버튼//-------------------->
<table width="95%" height=40 align=center border=0>
  <tr>
    <td width="296" align="left">
      <table border=0 cellpadding=0 cellspacing=0 >
      <tr>
        <td><img src=/plm/portal/images/title2_left.gif></td>
        <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "02997") %><%--폴더--%></td>
        <td><img src=/plm/portal/images/title2_right.gif></td>
      </tr>
      </table>
    </td>
    <td align=right>
<%  if(folderOid != null) {%>
      <input type=button value='<%=messageService.getString("e3ps.message.ket_message", "02999") %><%--폴더 생성--%>' id=button onclick="javascript:createFolder('<%=folderOid%>')">
<%  }
    if(isAdmin){ %>
      &nbsp;<input type=button value='<%=messageService.getString("e3ps.message.ket_message", "02905") %><%--캐비넷 생성--%>' id=button onclick='javascript:createCabinet()'>
<%  }%>
    </td>
  </tr>
</table>
<!--------상단타이틀 끝//--------->
<table border="0" cellpadding="1" cellspacing="1" width="95%" bgcolor="#AABDC6" align="center">
  <tr bgcolor="#D8E0E7" align=center height=23>
    <td id=title><%=messageService.getString("e3ps.message.ket_message", "03003") %><%--폴더명--%></td>
    <%if(isAdmin){ %>
    <TD id=title><%=messageService.getString("e3ps.message.ket_message", "01251") %><%--도메인--%></TD>
    <%} %>
  </tr>

<%
for(int i=0; i<list.size();i++)
{
  FolderTreeData data = (FolderTreeData)list.get(i);
  Folder folder = (Folder)CommonUtil.getObject(data.oid);
  wt.fc.QueryResult qr = wt.fc.PersistenceHelper.manager.navigate(folder, "workspace", wt.epm.workspaces.WorkspaceFolder.class);
     if(qr.hasMoreElements()) continue;

     wt.admin.AdminDomainRef domainRef = wt.admin.DomainAdministeredHelper.getAdminDomainRef((wt.admin.DomainAdministered)folder);
%>
  <tr bgcolor="white" height="23">
    <td>&nbsp;
<%
  for(int j=0; j < data.level ; j++)
    out.print("<font color=white>....</font>");
  if(FolderTreeData.empty.equals(data.image))
    out.print("<a href='JavaScript:treeaction(\""+FolderTreeData.expand+"\",\""+i+"\")'>");
  else
    out.print("<a href='JavaScript:treeaction(\""+data.image+"\",\""+i+"\")'>");
  out.println("<img src='/plm/portal/icon/"+data.image+".gif' border=0 align=absmiddle><a>");
  out.println("<img src='/plm/portal/icon/"+data.type+".gif' border=0 align=absmiddle><a>");
  out.println("<a href=\"JavaScript:editfolder('"+data.oid+"','"+data.level+"','"+i+"')\"><b>"+data.name+"</a>");
  out.println("<input type=hidden name=ptree value='"+data.oid+"'>");
  out.println("<input type=hidden name=levels value='"+data.level+"'>");
  out.println("<input type=hidden name=ptreeActions value='"+data.image+"'>");
%>
    </td>
    <%if(isAdmin){ %>
    <TD>&nbsp;<%=domainRef.getName() %></TD>
    <%} %>
  </tr>
<%}%>
</table>
</form>
</body>
</html>
