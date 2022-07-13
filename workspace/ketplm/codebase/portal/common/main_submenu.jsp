<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "wt.org.*" %>
<%@ page import = "wt.session.*" %>
<%@ page import = "wt.query.*" %>
<%@ page import = "wt.workflow.work.*" %>
<%@ page import = "wt.fc.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "e3ps.project.*" %>
<%@ page import = "e3ps.project.beans.*" %>
<%@ page import = "e3ps.common.web.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%

  String items = "0";
  String projects = "0";
  String tasks = "0";
  /*************************결재 대기 건 수 출력**************************/
  WTPrincipal user = SessionHelper.manager.getPrincipal();
  QuerySpec spec = new QuerySpec();
  int itemIdx = spec.addClassList(WorkItem.class, true);

  if (spec.getConditionCount() > 0)
    spec.appendAnd();
  spec.appendWhere(new SearchCondition(WorkItem.class,
      "ownership.owner.key", SearchCondition.EQUAL,
      PersistenceHelper.getObjectIdentifier(user)),
      new int[] { itemIdx });
  if (spec.getConditionCount() > 0)
    spec.appendAnd();
  spec.appendWhere(new SearchCondition(WorkItem.class,
      WorkItem.STATUS, SearchCondition.EQUAL, "POTENTIAL"),
      new int[] { itemIdx });
  spec.appendOrderBy(new OrderBy(new ClassAttribute(WorkItem.class,
      "thePersistInfo.createStamp"), true), new int[] { itemIdx });
  QueryResult wResult = PersistenceHelper.manager.find(spec);
  if(wResult!=null && wResult.size()>0)items = Integer.toString(wResult.size());
  /********************************************************************/
  /**************************프로젝트 건 수 출력**************************/
  HashMap projectmap = new HashMap();
  projectmap.put("command", "search");
  projectmap.put("searchPjtState", "PROGRESS");
  PagingQueryResult qrrProject= null;
  qrrProject = SearchPagingProjectHelper.openPagingSession2(projectmap, 0,5);
  if(qrrProject!=null && qrrProject.getTotalSize()>0){
    projects = Integer.toString(qrrProject.getTotalSize());
  }
  /********************************************************************/
  /****************************Task 건 수 출력***************************/
  HashMap taskmap = new HashMap();
  taskmap.put("command", "search");
  taskmap.put("searchPjtState", "PROGRESS");
  taskmap.put("searchState", "0");
  PagingQueryResult qrrTask = null;
  qrrTask = SearchPagingProjectHelper.openPagingSession3(taskmap, 0, 5);
  if(qrrTask!=null && qrrTask.getTotalSize()>0){
    tasks = Integer.toString(qrrTask.getTotalSize());
  }
  /********************************************************************/
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../css/e3ps.css" rel="stylesheet" type="text/css">
<script src="/plm/portal/js/script.js" type="text/javascript"></script>
<style type="text/css">
<!--
body {
  margin-left: 0px;
  margin-top: 0px;
  margin-right: 0px;
  margin-bottom: 0px;
}
-->
</style>
</head>
<body>
<tr>
  <table width="200" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="190" height="100%" valign="top"><table width="190" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="44"><table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><img src="/plm/portal/images/subh_1.png"></td>
                </tr>
                <tr>
                  <td height="10"></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td valign="top"><table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif"><a href="javascript:parent.movePaage('/plm/portal/common/groupware_submenu.jsp',
                '/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr');" target="_self" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "00759") %><%--결재대기--%> : <font color="#00357e"><%=items%></font color><%=messageService.getString("e3ps.message.ket_message", "00698") %><%--건--%></a></td>
              </table>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif"><a href="javascript:parent.movePaage('/plm/portal/common/project_submenu.jsp?name0=devproject',
                '/plm/jsp/project/ListMyProject.jsp');" target="_self"class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "03046") %><%--프로젝트--%> : <font color="#00357e"><%=projects %></font color><%=messageService.getString("e3ps.message.ket_message", "00698") %><%--건--%></a></td>
                </tr>
              </table>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif"><a href="javascript:parent.movePaage('/plm/portal/common/project_submenu.jsp?name0=devproject',
                '/plm/jsp/project/ListMyTask.jsp');" target="_self" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "00479") %><%--Task--%> : <font color="#00357e"><%=tasks %></font color><%=messageService.getString("e3ps.message.ket_message", "00698") %><%--건--%></a></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td height="118" align="center"><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><img src="/plm/portal/images/banner_1.gif"></td>
                </tr>
                <tr>
                  <td height="5"></td>
                </tr>
                <tr>
                  <td height="80" align="center" valign="top"   style="background-image:url('/plm/portal/images/banner_2.gif');background-repeat:no-repeat;background-size:100%"><table width="150" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="31">&nbsp;</td>
                    </tr>
                    <tr>
                      <td>(032)850-1369(PMS)</td>
                    </tr>
                    <tr>
                      <td>(032)850-1304(PDM)</td>
                    </tr>
                    <tr>
                      <td><%=messageService.getString("e3ps.message.ket_message", "00001") %><%--(032)850-1154(시스템)--%></td>
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="5"></td>
                </tr>
              </table></td>
          </tr>
        </table></td>
      <td width="1" bgcolor="#c6c6c6"></td>
    </tr>
  </table>
</body>
</html>
