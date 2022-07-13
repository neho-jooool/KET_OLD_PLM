<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="e3ps.common.util.*"%>
<%@ page import="com.ptc.netmarkets.object.objectResource"%>
<%@ page import="wt.fc.*"%>
<%@ page import="wt.util.InstalledProperties"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jca:getPageModel var="jcaPageModel" scope="page" />
<tags:htmlHeaderRenderer displayTopHtml="true" renderJSLinks="true" />
</head>
<%@include file="/netmarkets/jsp/util/beginLegacy.jspf"%>
<%!private static final String OBJECT_RESOURCE = "com.ptc.netmarkets.object.objectResource";%>
<%
    ResourceBundle objectRb = ResourceBundle.getBundle(OBJECT_RESOURCE, localeBean.getLocale());
%>
<%
    long currentTime = DateUtil.getCurrentTimestamp().getTime();
    String week = "30";
    long weeklater = currentTime - (Long.parseLong(week) * (1000 * 60 * 60 * 24));
    Timestamp weekTime = new Timestamp(weeklater);
    String postdate = DateUtil.getCurrentDateString("d");
    String predate = DateUtil.getDateString(weekTime, "d");
    String initParam = "key=predate&value=" + predate + "&key=postdate&value=" + postdate;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
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
  <table width="200" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="190" height="100%" valign="top"><table width="190" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="44"><table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><img src="/plm/portal/images/subh_9.png"></td>
                </tr>
                <tr>
                  <td height="10"></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td valign="top">
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif"> <a href="/plm/jsp/common/loading.jsp?url=/plm/ext/wfm/workflow/listWorkItem.do"
                    target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "00760")%><!-- 결재대기함 --></a></td>
                </tr>
              </table>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif"> <a
                    href="/plm/jsp/common/loading.jsp?url=/plm/ext/wfm/workflow/listInProgressWorkItem.do" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "00787")%><!-- 결재진행함 --></a></td>
                </tr>
              </table>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif"> <a
                    href="/plm/jsp/common/loading.jsp?url=/plm/ext/wfm/workflow/listCompletedWorkItem.do" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "00777")%><!-- 결재완료함 --></a></td>
                </tr>
              </table>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif"> <a href="/plm/jsp/common/loading.jsp?url=/plm/ext/wfm/workflow/listTempWorkItem.do"
                    target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "02375")%><!-- 임시저장함 --></a></td>
                </tr>
              </table>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif"> <a
                    href="/plm/jsp/common/loading2.jsp?url=/plm/servlet/e3ps/WorkspaceServlet?cmd=searchRefDoc&<%=initParam%>" target="contName"
                    class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "02767")%><%--참조문서함--%></a></td>
                </tr>
              </table>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <%-- [START] [PLM System 고도화] Windchill 10.2 Upgrade 후 에러 처리, 위임달력URL변경 2014-06-09, Jason Han --%>
                  <td><img src="/plm/portal/images/icon_2.gif"> <a
                    href="javascript:launchUtilityWindow('<%=urlFactoryBean.getFactory().getURL("/ptc1/calender/calenderMgmt")%>',
                                                             'CalendarMgmt','directories=no,location=no,menubar=no,scrollbars=auto,status=yes,toolbar=no,resizable=yes');"
                    id='<%=linkBean.getLinkID("link", "calendarManagement")%>' class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "02236")%><%--위임설정--%></a></td>
                  <%-- [END] [PLM System 고도화] Windchill 10.2 Upgrade 후 에러 처리, 위임달력URL변경 2014-06-09, Jason Han --%>
                </tr>
              </table>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif"><a href="/plm/jsp/common/loading.jsp?url=/plm/ext/wfm/workspace/listMyTask.do"
                    target="contName" class="leftmenu1"> My Task<%--My Task--%></a></td>
                </tr>
              </table>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif"><a href="/plm/jsp/common/loading.jsp?url=/plm/ext/wfm/workspace/listMyProject.do"
                    target="contName" class="leftmenu1"> My Project<%--My Project--%></a></td>
                </tr>
              </table>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif"><a
                    href="/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps/IssueServlet&key=command&value=searchMyIssue" target="contName" class="leftmenu1">
                      My Issue<%--My Issue--%>
                  </a></td>
                </tr>
              </table>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif"><a href="/plm/jsp/common/loading.jsp?url=/plm/ext/wfm/workspace/listMyDocument.do"
                    target="contName" class="leftmenu1"> My Document<%--My Document--%></a></td>
                </tr>
              </table>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif"><a href="/plm/jsp/common/loading.jsp?url=/plm/ext/wfm/workspace/listMyDrawings.do"
                    target="contName" class="leftmenu1"> My Drawings<%--My Drawings--%></a></td>
                </tr>
              </table>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif"><a href="/plm/jsp/common/loading.jsp?url=/plm/ext/wfm/workspace/listMyPart.do"
                    target="contName" class="leftmenu1"> My Part<%--My Part--%></a></td>
                </tr>
              </table>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif"><a href="/plm/jsp/common/loading.jsp?url=/plm/ext/wfm/workspace/listMyECM.do"
                    target="contName" class="leftmenu1"> My ECM<%--My ECM--%></a></td>
                </tr>
              </table>
            </td>
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
                  <td height="80" align="center" valign="top"
                    style="background-image: url('/plm/portal/images/banner_2.gif'); background-repeat: no-repeat; background-size: 100%">
                    <table width="150" border="0" cellspacing="0" cellpadding="0">
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
                        <td><%=messageService.getString("e3ps.message.ket_message", "00001")%><%--(032)850-1154(시스템)--%></td>
                      </tr>
                    </table>
                  </td>
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
