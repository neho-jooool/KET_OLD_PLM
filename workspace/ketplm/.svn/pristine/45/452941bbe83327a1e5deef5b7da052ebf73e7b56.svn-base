<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.sql.*,
        java.util.*,
        wt.org.WTUser,
        wt.fc.*,
        wt.query.*,
        wt.lifecycle.LifeCycleManaged,
        wt.clients.folder.FolderTaskLogic,
        e3ps.common.util.*,
        e3ps.common.jdf.config.ConfigImpl,
        e3ps.project.beans.*,
        e3ps.groupware.board.*"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="e3ps.project.*"%>
<%@page import = "wt.workflow.work.WorkItem" %>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="java.math.BigDecimal"%>
<%@page import = "wt.org.*" %>
<%@page import = "e3ps.wfm.util.*" %>
<%@page import = "wt.workflow.work.WfAssignedActivity" %>
<%@page import = "e3ps.ecm.entity.KETMoldChangeActivity" %>
<%@page import = "e3ps.ecm.entity.KETProdChangeActivity" %>
<%@page import="e3ps.groupware.board.beans.MyTestOption"%>
<%@page import="e3ps.wfm.entity.KETWfmMultiApprovalRequest"%>
<%@page import="e3ps.groupware.company.PeopleHelper"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalMaster"%>
<%@page import="e3ps.groupware.company.People"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>

<html>
<head>
<title>Untitled Document</title>
<script language="javascript" src="js/script.js"></script>
<script language="javascript" src="js/e3psScroller.js"></script>
<SCRIPT language=JavaScript src="js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/jsp/wfm/viewPBO.js"></SCRIPT>
<link href="css/e3ps.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
<!--
function myWork(oid, task) {
  parent.movePaage('/plm/portal/common/groupware_submenu.jsp', "/plm/servlet/WindchillAuthGW/wt.workflow.work.WorkItemURLProcessor/URLTemplateAction?u8&oid=OR:"+oid+"&action="+task);
//  parent.location ="/plm/portal/groupware/groupware_2.jsp?portalOid="+oid+"&portalAction="+task;
}

function viewProject(oid) {

  //parent.viewContentPage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid='+oid,'/plm/jsp/project/ProjectView.jsp?oid='+oid);
  parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid='+oid, '/plm/jsp/project/ProjectView.jsp?oid='+oid);
//  parent.cen.document.location.href = "/plm/jsp/project/ProjectViewFrm.jsp?oid="+oid;
}

function MM_showHideLayers() { //v6.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }
    obj.visibility=v; }
}
function notice_getCookie(name){
  var arg = name + "=";
  var alen = arg.length;
  var clen = document.cookie.length;
  var i = 0 ;
  while(i < clen)
  {
    var j = i + alen;
    if(document.cookie.substring(i, j) == arg)
      return getCookieVal(j);
    i = document.cookie.indexOf("",i) +1;
    if(i == 0) break;
  }
  return null;
}
function getCookieVal(offset){
  var endstr = document.cookie.indexOf(";", offset);
  if(endstr == -1) endstr = document.cookie.length;
  return unescape(document.cookie.substring(offset, endstr));
}

/*
 * Start [PLM System 1차개선] 수정내용 : 여러 장 밀려서 열기, 2013. 06. 28, 김무준
 */
function openMultiWindow(url, name, width, height, indent)
{
  // modified from common.js, openWindow()

  var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=0,resizable=0,";
  if(width == 'full')
  {
    leftpos = (screen.availWidth - screen.availWidth *0.9 )/ 2;
    toppos = (screen.availHeight - screen.availHeight *0.9 - 30 ) / 2 ;

    leftpos = leftpos + indent;
    toppos = toppos + indent;

    rest = "width=" + (screen.availWidth * 0.9 ) + ",height=" + (screen.availHeight * 0.9 )+',left=' + leftpos + ',top=' + toppos;
  }
  else
  {
    leftpos = (screen.availWidth - width)/ 2;
    toppos = (screen.availHeight - 60 - height) / 2 ;

    leftpos = leftpos + indent;
    toppos = toppos + indent;

    rest = "width=" + width + ",height=" + height+',left=' + leftpos + ',top=' + toppos;
  }

  var newwin = open( url , name, opts+rest);
  newwin.focus();
  return newwin;
}
/*
 * End [PLM System 1차개선] 수정내용 : 여러 장 밀려서 열기, 2013. 06. 28, 김무준
 */

function openNoti(oids) {
  if(oids.length > 0) {
    var oidAry = oids.split(',');
    var oid, idpart;
    for (var i = 0; i < oidAry.length; ++i) {
      oid = oidAry[i];
      idpart = oid.split(':')[1];
      if (notice_getCookie("renewalPopup" + idpart) != "no") {
        openMultiWindow('/plm/jsp/groupware/board/notifyviewPopup.jsp?oid='+oid, 'renewalPopup'+i, 820, 420, i * 15);
      }
    }
  }
}

function openNoti2(oid) {

   var top_position = (screen.height) ? (screen.height-230)/2 : 0; //팝업 위치 세로
   var left_position = (screen.width) ? (screen.width-400)/2 : 0; //팝업 위치 가로
   var settings = "width=400, height=230, top=" + top_position + ", left=" + left_position;

   openWindow('/plm/jsp/groupware/board/notifyviewPopup.jsp?oid='+oid, 'renewalPopup', 850, 460);

}

function myWorkPage(url) {
  document.location.href = "/plm/servlet/WindchillAuthGW/"+url;
}

function viewProject(oid){
    openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1150,800);
}

function viewIssue(v) {
    var url = "/plm/jsp/project/projectIssueView.jsp?oid="+v;
    openOtherName(url,"viewIssue","750","700","status=no,scrollbars=yes,resizable=no");
}
//-->
</script>
<style type="text/css">
<!--
body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 0px;
  margin-bottom: 5px;
}
body, td, th {
  color: #666;
}
-->
</style>
</head>
<%
  int limit = 100;
  String notiOid = "";
  QuerySpec qsaa = new QuerySpec(Notify.class);
  qsaa.appendOrderBy(new OrderBy(new ClassAttribute(Notify.class, "thePersistInfo.createStamp") ,true), new int[]{0});
  QueryResult resultaa = PagingSessionHelper.openPagingSession(0, limit, qsaa);
  while(resultaa.hasMoreElements()) {
    Object[] o = (Object[])resultaa.nextElement();
    Notify noti = (Notify)o[0];
    if (notiOid.length() > 0) notiOid = notiOid + ",";
    notiOid = notiOid + CommonUtil.getOIDString(noti);
  }

%>
<body onload="javascript:openNoti('<%=notiOid%>');" >
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" valign="top" ><table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td valign="top"><table width="384" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="20">&nbsp;</td>
              </tr>
              <tr>
                <td align="center" valign="top">

                <table width="370" height="56" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td background="/plm/portal/images/mainTitleLineS.png" align="left">
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 5px;"></td>
                            <td style="width: 70px; font-family: 'Malgun Gothic'; font-size: 16px; font-weight: bold; padding-bottom:7px; color:#000"><%=messageService.getString("e3ps.message.ket_message", "00338") %><%--My Task--%></td>
                        </tr>
                        </table>
                    </td>
                    <td background="/plm/portal/images/mainTitleLineE.png" align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 280px; text-align: right;">
                                <a href="javascript:parent.movePaage('/plm/portal/common/project_submenu.jsp?name0=devproject', '/plm/servlet/e3ps/ProjectServlet?command=searchMyTask');" target="_self">
                                <img src="images/btn_more.gif" border="0">
                                </a>
                            </td>
                            <td width="10"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>

                  <table width="370" height="30" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td align="center" valign="middle" background="images/barbg.gif"><table width="350" border="0" cellspacing="0" cellpadding="0">
                        <tr class="title_b">
                          <td width="40" class="tdblue2_L"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
                          <td width="200" class="tdblue2_R"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                          <td width="110" class="tdblue2_R"><%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%></td>
                        </tr>
                      </table></td>
                    </tr>
                  </table>
                  <table width="370" height="7" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
          <table width="350" height="110" border="0" cellspacing="0" cellpadding="0">
          <tr><td valign="top">
                  <%
                  HashMap map = new HashMap();
                map.put("command", "search");
                map.put("searchPjtState", "PROGRESS");
                map.put("searchState", "0");
                PagingQueryResult qrr = null;
                qrr = SearchPagingProjectHelper.openPagingSession3(map, 0, 5);

                if(qrr.size()>0){
                  int taskIdx = qrr.size();
                  while ( qrr.hasMoreElements() ) {
                      Object[] o = (Object[])qrr.nextElement();
                      E3PSTask et = null;
                    et = (E3PSTask)o[0];

                    E3PSTaskData data = new E3PSTaskData(et);
                    E3PSProject project = (E3PSProject)et.getProject();

                    /*
                      개발 검토 전자 0
                          자동차 1
                      제품    자동차 2
                          전자 4
                      금형    3
                    */

                    String pjtType = "제품";
                    if(project instanceof ReviewProject){
                      pjtType = "검토";
                    }else if(project instanceof ProductProject){
                      pjtType = "제품";
                    }else if(project instanceof MoldProject){
                      pjtType = "금형";
                    }

                    String taskPlanStartDateStr = "";
                    String taskPlanEndDatStr = "";
                    ExtendScheduleData schedule = (ExtendScheduleData)et.getTaskSchedule().getObject();
                    if(schedule.getPlanStartDate() != null){
                      taskPlanStartDateStr = DateUtil.getDateString(schedule.getPlanStartDate(), "D");
                    }
                    if(schedule.getPlanEndDate() != null){
                      taskPlanEndDatStr = DateUtil.getDateString(schedule.getPlanEndDate(), "D");
                    }
                  %>
                  <table width="350"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="50" class="tdblue3_L"><%=taskIdx-- %></td>
                      <td width="190" class="tdblue3_R" title="<%=et.getProject().getPjtName() %>"><a href="javascript:viewProject('<%=PersistenceHelper.getObjectIdentifier( et ).getStringValue()%>');"><nobr style="text-overflow:ellipsis;overflow:hidden;width:190;cursor:hand;"><%=et.getProject().getPjtName() %></nobr></a></td>
                      <td width="110" class="tdblue3_R" title="<%=(et==null)?"":et.getTaskName() %>"><a href="javascript:viewProject('<%=PersistenceHelper.getObjectIdentifier( et ).getStringValue()%>');"><nobr style="text-overflow:ellipsis;overflow:hidden;width:110;cursor:hand;"><%=(et==null)?"":et.getTaskName() %></nobr></a></td>
                    </tr>
                    </table>
                  <%
                  }
                }else {
              %>
                <table width="350"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td colspan="3" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01329") %><%--등록된 태스크가 없습니다--%>.</font></td>
                    </tr>
                    </table>
              <%
                }
                %></td></tr></table>
                  <table width="370" height="17" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="images/line_0.gif"></td>
                    </tr>
                  </table>
                  </td>
              </tr>
              <tr>
                <td>&nbsp;</td>
              </tr>
            </table></td>
          <td width="12">&nbsp;</td>
          <td valign="top"><table width="384" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="20">&nbsp;</td>
            </tr>
            <tr>
              <td align="center" valign="top">

                <table width="370" height="56" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td background="/plm/portal/images/mainTitleLineS.png" align="left">
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 5px;"></td>
                            <td style="width: 90px; font-family: 'Malgun Gothic'; font-size: 16px; font-weight: bold; padding-bottom:7px; color:#000"><%=messageService.getString("e3ps.message.ket_message", "00337") %><%--My Project--%></td>
                        </tr>
                        </table>
                    </td>
                    <td background="/plm/portal/images/mainTitleLineE.png" align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 260px; text-align: right;">
                                <a href="javascript:parent.movePaage('/plm/portal/common/project_submenu.jsp?name0=devproject', '/plm/servlet/e3ps/ProjectServlet?command=searchMyProject');" target="_self">
                                <img src="images/btn_more.gif" border="0">
                                </a>
                            </td>
                            <td width="10"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>

                <table width="370" height="30" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td align="center" valign="middle" background="images/barbg.gif"><table width="350" border="0" cellspacing="0" cellpadding="0">
                      <tr class="title_b">
                        <td width="40" class="tdblue2_L"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
                        <td width="100" class="tdblue2_R"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
                        <td width="220" class="tdblue2_R"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                      </tr>
                    </table></td>
                  </tr>
                </table>
                <table width="370" height="7" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="350"  height="110" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  <td valign="top">
                <%
                HashMap taskmap = new HashMap();
              taskmap.put("command", "search");
              taskmap.put("searchPjtState", "PROGRESS");
                /*
                  제품
                  금형
                  검토
                */
                String searchPjtType = "제품";
                taskmap.put("searchPjtType", searchPjtType);
              PagingQueryResult qrrTask = null;
              qrrTask = SearchPagingProjectHelper.openPagingSession2(taskmap, 0, 5);
              if(qrrTask.size() > 0)  {
                int projectIdx = qrrTask.size();
                  while ( qrrTask.hasMoreElements() ) {
                  Object[] o = (Object[])qrrTask.nextElement();
                  String className = (String)o[0];
                  BigDecimal decimal = (BigDecimal)o[1];
                  long id = decimal.longValue();


                E3PSProject project = (E3PSProject)CommonUtil.getObject(className+ ":" + id);
                E3PSProjectData data = new E3PSProjectData(project);
                String dieNo = "";
                MoldProject mp = null;
                if(project instanceof MoldProject){
                  mp = (MoldProject)project;
                  if(mp.getMoldInfo() != null){
                    dieNo = mp.getMoldInfo().getDieNo();
                  }
                }
                String projectName = project.getPjtName();
                String projectNo = project.getPjtNo();
                String taskPlanStartDateStr = "";
                String taskPlanEndDatStr = "";
                if(data.pjtPlanStartDate != null){
                  taskPlanStartDateStr = DateUtil.getDateString(data.pjtPlanStartDate, "d");
                }

                %>
                <table width="350" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="55" class="tdblue3_L"><%=projectIdx-- %></td>
                    <td width="97" class="tdblue3_L"><nobr style="text-overflow:ellipsis;overflow:hidden;width:97;"><%=projectNo%></nobr></td>
                    <td width="208" class="tdblue3_R" title="<%=projectName %>"><a href="javascript:viewProject('<%=PersistenceHelper.getObjectIdentifier( project ).getStringValue()%>');"><nobr style="text-overflow:ellipsis;overflow:hidden;width:208;cursor:hand;"><%=projectName %></nobr></a></td>
                  </tr>
                </table>
                <%
                  }
              }else {
                %>  <table width="350"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td colspan="3" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01330") %><%--등록된 프로젝트가 없습니다--%></font></td>
                    </tr>
                    </table>
                <%} %>
                </td></tr></table>
                <table width="370" height="17" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td background="images/line_0.gif"></td>
                  </tr>
                </table></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td valign="top"><table width="384" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="center" valign="top">


                <table width="370" height="56" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td background="/plm/portal/images/mainTitleLineS.png" align="left">
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 5px;"></td>
                            <td style="width: 100px; font-family: 'Malgun Gothic'; font-size: 16px; font-weight: bold; padding-bottom:7px; color:#000"><%=messageService.getString("e3ps.message.ket_message", "00760") %><%--결재대기함--%></td>
                        </tr>
                        </table>
                    </td>
                    <td background="/plm/portal/images/mainTitleLineE.png" align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 250px; text-align: right;">
                                <a href="javascript:parent.movePaage('/plm/portal/common/groupware_submenu.jsp',  '/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr');" target="_self">
                                <img src="images/btn_more.gif" border="0">
                                </a>
                            </td>
                            <td width="10"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>


                <table width="370" height="30" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td align="center" valign="middle" background="images/barbg.gif"><table width="350" border="0" cellspacing="0" cellpadding="0">
                      <tr class="title_b">
                        <td width="40" class="tdblue2_L"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
                        <td width="80" class="tdblue2_R"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                        <td width="170" class="tdblue2_R"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                        <td width="60" class="tdblue2_R"><%=messageService.getString("e3ps.message.ket_message", "02196") %><%--요청자--%></td>
                      </tr>
                    </table></td>
                  </tr>
                </table>
                <table width="370" height="7" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td></td>
                  </tr>
                </table>
                <table width="350"  height="110" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  <td valign="top">
                <%
                WTPrincipal user = SessionHelper.manager.getPrincipal();
                QuerySpec spec = new QuerySpec();
            int itemIdx = spec.addClassList(WorkItem.class,true);

            if(spec.getConditionCount()>0) spec.appendAnd();
            spec.appendWhere(new SearchCondition(WorkItem.class ,
                "ownership.owner.key", SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(user)),new int[]{itemIdx});
            if(spec.getConditionCount()>0) spec.appendAnd();
            spec.appendWhere(new SearchCondition(WorkItem.class,
                WorkItem.STATUS, SearchCondition.EQUAL, "POTENTIAL"),new int[] {itemIdx});
            spec.appendOrderBy(new OrderBy(new ClassAttribute(WorkItem.class, "thePersistInfo.createStamp"),true), new int[]{itemIdx});

            QueryResult qr = PagingSessionHelper.openPagingSession(0, 5, spec);
            int index = qr.size();
            String status = "";
              String type = "";
              String creator = "";
              String title = "";
              String taskName ="";
              String pboOid = "";
            if(qr.size()>0){
              while(qr.hasMoreElements()){
                String retUrl = "#";
                  Object[] obj = (Object[])qr.nextElement();
                  WorkItem item = (WorkItem)obj[0];
                  Object pbo = item.getPrimaryBusinessObject().getObject();
                  title = ClassifyPBOUtil.extractPBOInfo(pbo).get("title").toString();
                  creator = ClassifyPBOUtil.extractPBOInfo(pbo).get("creator").toString();
                  WfAssignedActivity activity = (WfAssignedActivity)item.getSource().getObject();
                  WTUser lastApprover = (WTUser)WorkflowSearchHelper.manager.getLastUser((Persistable)pbo);
                  pboOid = pbo.toString();
                  if(activity.getName().equals("수행담당자"))  {
                    taskName = ClassifyPBOUtil.extractPBOInfo(pbo).get("task").toString();
                    retUrl = ClassifyPBOUtil.getTaskUrl(pbo);
                    if(pbo instanceof KETMoldChangeActivity) {
                      KETMoldChangeActivity moldCA = (KETMoldChangeActivity)pbo;
                      pboOid = moldCA.getMoldECO().toString();
                    }
                    else if(pbo instanceof KETProdChangeActivity) {
                      KETProdChangeActivity prodCA = (KETProdChangeActivity)pbo;
                      pboOid = prodCA.getProdECO().toString();
                    }
                  }
                  else taskName = activity.getName();
                  if(lastApprover!=null){
                    String ownerID = item.getOwnership().getOwner().getName();
                    if(ownerID.equals(lastApprover.getName())){
                      taskName = "승인";
                    }
                  }
                  People apUser = new People();
                  String appUser = "&nbsp";
                KETWfmApprovalMaster appMaster = WorkflowSearchHelper.manager.getMaster((Persistable)pbo);
                if(appMaster != null){
                apUser = PeopleHelper.manager.getPeople(appMaster.getOwner().getName());
                if(apUser != null){
                  appUser = apUser.getName();
                  }
                }
                // 2013.1.29 shkim
                Vector docVec = null;
                if(pbo instanceof KETWfmMultiApprovalRequest) {
                  KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest)CommonUtil.getObject(pboOid);
                  //Vector epmVec = WorkflowSearchHelper.manager.getEPMList(multiReq);
                docVec = WorkflowSearchHelper.manager.getDocList(multiReq);
                //out.println(docVec.size());
                }
                %>
                <table width="350" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="45" class="tdblue3_L"><%=index %></td>
                      <%if(taskName.equals("담당자지정")){ %>
                  <td width="80" class="tdblue3_L"  title="<%= taskName %>">
                  <a href="/plm/jsp/project/Wf_Assign.jsp?oid=<%=item.toString() %>" target="_self"><nobr style="text-overflow:ellipsis;overflow:hidden;width:80;cursor:hand;"><%= taskName %></nobr></a>
                  </td>
                 <%}else if(activity.getName().equals("수행담당자")){%>
                  <td width="80" class="tdblue3_L"  title="<%= taskName %>">
                  <a href="<%=retUrl %>"><nobr style="text-overflow:ellipsis;overflow:hidden;width:80;cursor:hand;"><%= taskName %></nobr></a>
                  </td>
                   <% } else { %>
                  <td width="80" class="tdblue3_L"  title="<%= taskName %>">
                  <a href="javascript:parent.movePaage('/plm/portal/common/groupware_submenu.jsp','/plm/jsp/wfm/ReviewTask.jsp?oid=<%=item.toString() %>')"><nobr style="text-overflow:ellipsis;overflow:hidden;width:80;cursor:hand;"><%= taskName %></nobr></a>
                  </td>
                <%} %>
                <%if(docVec != null && docVec.size() != 0) { %>
                      <td width="165" class="tdblue3_R" title="<%=title %>"><a href="javascript:openView2('<%=pboOid %>')"><nobr style="text-overflow:ellipsis;overflow:hidden;width:165;cursor:hand;"><%=title %></nobr></a></td>
                    <%}else{ %>
                      <td width="165" class="tdblue3_R" title="<%=title %>"><a href="javascript:openView('<%=pboOid %>')"><nobr style="text-overflow:ellipsis;overflow:hidden;width:165;cursor:hand;"><%=title %></nobr></a></td>
                    <%} %>
                    <td width="60" class="tdblue3_L"><%if(appMaster != null){%><%= appUser %><%}else{ %><%=creator %><%} %></td>
                  </tr>
                </table>
                <%  index--;
                  }
            }else { %>
                <table width="350" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  <td colspan="4" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01224") %><%--대기중인 결재 리스트가 없습니다--%></font></td>
                  </tr>
                </table>
                <%} %>
                </td></tr>
                </table>
                <table width="370" height="17" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td background="images/line_0.gif"></td>
                  </tr>
                </table></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table></td>
          <td width="12">&nbsp;</td>
          <td valign="top"><table width="384" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="center" valign="top">

                <table width="370" height="56" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td background="/plm/portal/images/mainTitleLineS.png" align="left">
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 5px;"></td>
                            <td style="width: 80px; font-family: 'Malgun Gothic'; font-size: 16px; font-weight: bold; padding-bottom:7px; color:#000"><%=messageService.getString("e3ps.message.ket_message", "00335") %><%--My Issue--%></td>
                        </tr>
                        </table>
                    </td>
                    <td background="/plm/portal/images/mainTitleLineE.png" align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 270px; text-align: right;">
                                <a href="javascript:parent.movePaage('/plm/portal/common/project_submenu.jsp?name0=devproject', '/plm/servlet/e3ps/IssueServlet?command=searchMyIssue');" target="_self">
                                <img src="images/btn_more.gif" border="0">
                                </a>
                            </td>
                            <td width="10"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>

                <table width="370" height="30" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td align="center" valign="middle" background="images/barbg.gif"><table width="350" border="0" cellspacing="0" cellpadding="0">
                      <tr class="title_b">
                        <td width="40" class="tdblue2_L"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
                        <td width="240" class="tdblue2_R"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                        <td width="70" class="tdblue2_R"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                      </tr>
                    </table></td>
                  </tr>
                </table>
                <table width="370" height="7" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td></td>
                  </tr>
                </table>
                <table width="350"  height="110" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  <td valign="top">
<%
        Hashtable issueTable = new Hashtable();
        issueTable.put("command", "search");
        issueTable.put("myIssueList", "all");
        issueTable.put("category","pre");
        QuerySpec issueSpec = new QuerySpec();
        issueSpec = ProjectIssueHelper.manager.getSearch(issueTable);
        PagingQueryResult issueResult = null;
        issueResult = PagingSessionHelper.openPagingSession(0, 5, issueSpec);
        if(issueResult.size()>0) {
          int issueIdx = issueResult.size();
          while (issueResult.hasMoreElements()) {
            Object[] issueRet = (Object[])issueResult.nextElement();
            ProjectIssue issue = (ProjectIssue) issueRet[0];
            ProjectIssueData data = new ProjectIssueData(issue);
%>
                <table width="350" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="42" class="tdblue3_L"><%= issueIdx--%></td>
                    <td width="235" class="tdblue3_R" title="<%=data.project.getPjtName() %>"><a href="javascript:viewIssue('<%=data.oid%>');"><nobr style="text-overflow:ellipsis;overflow:hidden;width:235;cursor:hand;"><%=data.project.getPjtName() %></nobr></a></td>
                    <td width="73" class="tdblue3_L"><%=DateUtil.getDateString( data.questionDate,"d" ) %></td>
                  </tr>
                </table>
<%
          }
        }else{
%>
          <table width="350" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td colspan="3" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01328") %><%--등록된 이슈가 없습니다--%></font></td>
                    </tr>
                  </table>
<%
        }
%>        </td></tr>
        </table>
                <table width="370" height="17" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td background="images/line_0.gif"></td>
                  </tr>
                </table></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</body>
</html>
