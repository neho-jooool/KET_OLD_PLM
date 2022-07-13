<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.org.WTUser"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.common.web.PageQueryBroker"%>
<%@page import="e3ps.project.TaskMemberLink"%>
<%@page import="e3ps.project.beans.TaskUserHelper"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.ProjectOutput"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="e3ps.project.beans.ProjectUserHelper"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="wt.query.CompositeQuerySpec"%>
<%@page import="wt.introspection.ClassInfo"%>
<%@page import="wt.introspection.WTIntrospector"%>
<%@page import="wt.introspection.DatabaseInfo"%>
<%@page import="wt.introspection.BaseTableInfo"%>
<%@page import="wt.query.KeywordExpression"%>
<%@page import="e3ps.project.beans.E3PSTaskData"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.project.beans.SearchPagingProjectHelper"%>
<%@page import="wt.util.WTAttributeNameIfc"%>
<%@page import="e3ps.project.TemplateTask"%>
<%@page import="wt.query.SetOperator"%>
<%@page import="wt.query.StringSearch"%>
<%@page import="wt.query.CompoundQuerySpec"%>
<%@page import="wt.query.SubSelectExpression"%>
<%@page import="wt.query.OrderBy"%>
<%@page import="e3ps.project.ExtendScheduleData"%>
<%@page import="e3ps.project.beans.ProjectOutputHelper"%>
<%@page import="java.util.Locale"%>
<%@page import="e3ps.project.beans.ProjectStateFlag"%>
<%@page import="wt.pds.StatementSpec"%>
<%@page import="wt.lifecycle.LifeCycleTemplate"%>
<%@page import="wt.lifecycle.LifeCycleHelper"%>
<%@page import="e3ps.common.util.WCUtil"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.lifecycle.PhaseTemplate"%>
<%@page import="e3ps.common.query.SearchUtil"%>
<%@page import="e3ps.project.E3PSProjectMaster"%>
<%@page import="wt.fc.PagingQueryResult"%>
<%@page import="e3ps.common.web.QueryBroker"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.ParsePosition"%>
<%@page import="e3ps.common.web.ParamUtil"%>
<%@page import="e3ps.project.MoldItemInfo"%>
<%@page import="wt.fc.PagingSessionHelper"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="e3ps.project.ProjectMemberLink"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="e3ps.project.beans.ProjectTreeNode"%>
<%@page import="e3ps.project.beans.ProjectScheduleHelper"%>
<%@page import="e3ps.project.beans.ProjectTreeNodeData"%>
<%@page import="e3ps.project.beans.ProjectTaskHelper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page import="java.util.Collections"%>
<%@page import="e3ps.project.beans.TaskOneLevelSeqComparator"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%

    String sortKey = ParamUtil.getStrParameter(request.getParameter("sortKey"));
    String dsc = ParamUtil.getStrParameter(request.getParameter("dsc"));
    if(dsc.length() == 0){
        dsc = "false";
    }
    String imgDsc = "(▲)";
    if(dsc.equals("false")){
        imgDsc = "(▼)";
    }

    if(sortKey.length() == 0){
        sortKey = "thePersistInfo.createStamp";
    }

    String searchPjtState = request.getParameter("searchPjtState");
    String searchPjtName = StringUtil.checkNull(request.getParameter("searchPjtName"));
    String searchPjtNo = StringUtil.checkNull(request.getParameter("searchPjtNo"));
    String initType = request.getParameter("initType");
    String command = request.getParameter("command");
    if(command == null){
        command = "";
    }

    if(searchPjtState == null){
        searchPjtState = "PROGRESS";
    }
    String planStartStartDate = StringUtil.checkNull(request.getParameter("planStartStartDate"));
    String planStartEndDate = StringUtil.checkNull(request.getParameter("planStartEndDate"));
    String planEndStartDate = StringUtil.checkNull(request.getParameter("planEndStartDate"));
    String planEndEndDate = StringUtil.checkNull(request.getParameter("planEndEndDate"));

    String wtUser = StringUtil.checkNull(request.getParameter("wtUser"));
    WTUser selectUser = null;
    if(wtUser.length() > 0){
        selectUser =(WTUser)CommonUtil.getObject(wtUser);
    }

    HashMap map = new HashMap();
    if(command.length() > 0){
        map.put("command", command);
    }
    if(searchPjtState.length() > 0){
        map.put("searchPjtState", searchPjtState);
    }
    if(searchPjtName.length() > 0){
        map.put("searchPjtName", searchPjtName);
    }
    if(searchPjtNo.length() > 0){
        map.put("searchPjtNo", searchPjtNo);
    }
    if(planStartStartDate.length() > 0){
        map.put("planStartStartDate", planStartStartDate);
    }
    if(planStartEndDate.length() > 0){
        map.put("planStartEndDate", planStartEndDate);
    }
    if(planEndStartDate.length() > 0){
        map.put("planEndStartDate", planEndStartDate);
    }
    if(planEndEndDate.length() > 0){
        map.put("planEndEndDate", planEndEndDate);
    }
    if(wtUser.length() > 0){
        map.put("user", wtUser);
    }
    if(sortKey.length() > 0){
        map.put("sortKey", sortKey);
    }
    if(dsc.length() > 0){
        map.put("dsc", dsc);
    }
    Kogger.debug("command : " + command);
    if("projectExcelDown".equals(command)){
        map.put("command", "search");
        String fileName = new String("list".getBytes("euc-kr"), "8859_1");
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=EUC-KR");
        response.setHeader("Content-Disposition","attachment; filename=" + "ProjectList.xls");
        ProjectHelper.excelDownMy(map, response.getOutputStream(), selectUser );
        return;
    }


    String perPageValue = request.getParameter("perPage");
    if(perPageValue == null){
        perPageValue = "10";
    }

    int psize = Integer.parseInt(perPageValue);
    int cpage = 1;
    int total = 0;
    int pageCount = 10;

    //page
    String sessionidstring = request.getParameter("sessionid");
    if (sessionidstring == null){
        sessionidstring = "0";
    }
    long sessionid = Long.parseLong(sessionidstring);
    //Kogger.debug("##sessionid :"+sessionid);

    String pagestring = request.getParameter("tpage");
    if (pagestring != null && pagestring.length() > 0) {
        cpage = Integer.parseInt(pagestring);
    }

    String perPage = request.getParameter("perPage");

    if(perPage == null || perPage.length() == 0){
        perPage = "10";
    }

    PagingQueryResult qrr = null;
    if (sessionid <= 0) {
        if(command.equals("")){
            qrr = null;
        }else{
            qrr = SearchPagingProjectHelper.openPagingSession2(map, 0, psize);
        }
    } else {
        qrr = PagingSessionHelper.fetchPagingSession((cpage - 1)  * psize, psize, sessionid);
    }

    if (qrr != null) {
        total = qrr.getTotalSize();
        sessionid = qrr.getSessionId();
    }
    int reviewCount = 0;
    int protoCount = 0;
    int pilotCount = 0;
    int totalCount = 0 ;
    QueryResult qr = null;
    try{
        qr = SearchPagingProjectHelper.findMy(map);

        while ( qr.hasMoreElements() ) {
            Object[] o = (Object[])qr.nextElement();
            String className = (String)o[0];
            BigDecimal decimal = (BigDecimal)o[1];
            long id = decimal.longValue();
            E3PSProject project = (E3PSProject)CommonUtil.getObject(className+ ":" + id);
            if( project instanceof e3ps.project.ReviewProject){
                reviewCount++;
            }
            if(project.getProcess() != null){
                if(project.getProcess().getName().equals("Pilot")){
                    pilotCount++;
                }else if(project.getProcess().getName().equals("Proto")){
                    protoCount++;
                }
            }
        }


    }catch(Exception e){Kogger.error(e);}
    totalCount = reviewCount+protoCount+pilotCount;

    String titleCount =
     "Total : " + totalCount +"<br>"
    +messageService.getString("e3ps.message.ket_message", "00716")/*검토*/+"      : " + reviewCount +"<br>"
    +"Proto : " + protoCount +"<br>"
    +"Pilot : " + pilotCount;
    //out.print(titleCount);
%>

<title><%=messageService.getString("e3ps.message.ket_message", "03122") %><%--프로젝트이력--%></title>
<head>
<%@include file="/jsp/common/multicombo.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="JavaScript" src="/plm/portal/js/Calendar.js"></script>
<script language="javascript" src="/plm/portal/js/script.js"></script>
<script language="javascript" src="/plm/portal/js/common.js"></script>
<script language="javascript" src="/plm/portal/js/viewObject.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>


<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script> <!-- To-Be JSP에서는 추가 하지 않습니다. -->
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>

<script language="JavaScript">

    function clearDate(str) {
            var tartxt = document.getElementById(str);
            tartxt.value = "";
    }

    function viewPeople(oid) {
        var url = "/plm/e3pscore/org/selectPeopleView.jsp?viewtype=open&oid="+oid;
        openSameName(url,"560","400","status=no,scrollbars=no,resizable=no");
    }

    function gotoPage(p) {
        document.myProject.command.value='MySearch';
        document.myProject.sessionid.value='<%=sessionid%>';
        document.myProject.tpage.value=p;
        document.myProject.submit();
    }

    function Search() {
        //onProgressBar();
        document.myProject.action = "/plm/jsp/project/ListMyProjectPop.jsp";
        document.myProject.method = "post";
        document.myProject.command.value = "MySearch";
        document.myProject.sessionid.value = "0";
        document.myProject.submit();
    }

    function viewProject(oid){
        openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1150,800);
    }

    function sorting(key){
        document.myProject.action = "/plm/jsp/project/ListMyProjectPop.jsp";
        document.myProject.command.value = "MySearch";
        document.myProject.method = "post";
        if(key == document.myProject.sortKey.value){
            if(document.myProject.dsc.value == "false"){
                document.myProject.dsc.value = "true";
            }else{
                document.myProject.dsc.value = "false";
            }
        }else{
            document.myProject.dsc.value = "false";
        }
        document.myProject.sessionid.value = "0";
        document.myProject.sortKey.value = key;
        document.myProject.submit();
    }

    function onKeyPress() {
        if (window.event) {
            if (window.event.keyCode == 13) Search();
        } else return;
    }
    document.onkeypress = onKeyPress;
    function onReset(){
        form = document.forms[0];
//    form.searchPjtNo.value = "";
//    form.searchPjtName.value = "";
        form.searchPjtState.value = "";
        form.planStartStartDate.value = "";
        form.planStartEndDate.value = "";
        form.planEndStartDate.value = "";
        form.planEndEndDate.value = "";

    }

    function goEarlyWarning(){
        document.forms[0].action =  '/plm/servlet/e3ps/EarlyWarningServlet';
        document.forms[0].submit();
    }
    function viewTask(oid) {
        //var url = "/plm/jsp/project/TaskView.jsp?oid="+oid;
        //openOtherName(url,"","830","600","status=1,scrollbars=yes,resizable=1");
        openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1050,800);
    }

    function dosubmit(){
        if(document.myProject.sortKey != null){
            document.myProject.sortKey.value = "";
        }
        document.myProject.sessionid.value= "0";
        document.myProject.command.value = "MySearch";
        document.myProject.action="/plm/jsp/project/ListMyProjectPop.jsp";
        document.myProject.submit();
    }
    function excelDown(){
        if(document.myProject.sortKey != null){
            document.myProject.sortKey.value = "";
        }
        document.myProject.method = "post";
        document.myProject.action="/plm/jsp/project/ListMyProjectPop.jsp?command=projectExcelDown";
        document.myProject.submit();

    }

    $(document).ready(function(){
        // Calener field 설정
        CalendarUtil.dateInputFormat('planStartStartDate','planStartEndDate'); //계획시작일
        CalendarUtil.dateInputFormat('planEndStartDate','planEndEndDate'); //개발완료일
    });
    
</script>
<style type="text/css">
<!--
body {
    margin-left:7px;
}
-->
</style>
</head>


<form name="myProject">
<input type=hidden name=command value="<%=command%>" >
<input type='hidden' name='sessionid' value="<%=sessionidstring %>">
<input type='hidden' name='tpage'>
<input type = "hidden" name = "sortKey" value="<%=sortKey%>">
<input type = "hidden" name = "dsc" value="<%=dsc%>">
<input type=hidden name=initType value="<%=initType%>">
<input type="hidden" name="cmd" value="popup">
<input type="hidden" name="useroid" value="<%=wtUser%>">
<input type="hidden" name="wtUser" value="<%=wtUser%>">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="7"></td>
              <td width="20"><img src="../../portal/images/icon_3.png"></td>
              <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03084") %><%--프로젝트 이력 목록--%></td>

            </tr>
          </table></td>
          <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="5"></td>
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="../../portal/images/tab_1.png"></td>
              <td background="../../portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03046") %><%--프로젝트--%></td>
              <td><img src="../../portal/images/tab_2.png"></td>
            </tr>
          </table></td>
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="../../portal/images/tab_4.png"></td>
              <td background="../../portal/images/tab_6.png" class="btn_tab"><a href="javascript:goEarlyWarning();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02808") %><%--초기유동--%></a></td>
              <td><img src="../../portal/images/tab_5.png""></td>
            </tr>
          </table></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10" height="10"><img src="../../portal/images/box_9.gif"></td>
          <td height="10" background="../../portal/images/box_14.gif"></td>
          <td width="10" height="10"><img src="../../portal/images/box_10.gif""></td>
        </tr>
        <tr>
          <td width="10" background="../../portal/images/box_13.gif">&nbsp;</td>
          <td valign="top"><!--내용-->
              <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                <tr>
                  <td>&nbsp;</td>
                  <td align="right">
                          <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                        <td width="5">&nbsp;</td>
                        <td><table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onClick="javascript:onReset()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                          </table></td>
                        <td width="5">&nbsp;</td>
                        <td><table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onClick="javascript:Search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                          </table>
                           </td>
                           </tr>
                        </table>
                 </td>
                </tr>
              </table>
          <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
              <td class="space5"></td>
            </tr>
          </table>

            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr>
                    <td  class="tab_btm2"></td>
                  </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <!--tr>
                  <td width="150" class="tdblueL">Project No</td>
                <td width="250" class="tdwhiteL0"><input name="searchPjtNo" size=20 value="<%=searchPjtNo%>"></td>
                <td width="150" class="tdblueL">Project Name</td>
                <td width="230" class="tdwhiteL"><input name="searchPjtName" size=20 value="<%=searchPjtName%>"></td>
              </tr-->
              <tr>
                <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td>
                <td colspan="3" class="tdwhiteL0">
                <input name="planStartStartDate" value="<%=planStartStartDate%>" class="txt_field" size="12" engnum="engnum" maxlength=15 />
                <img src="/plm/portal/images/icon_delete.gif" border=0 onclick="javascript:CommonUtil.deleteDateValue('planStartStartDate');">&nbsp;~&nbsp; 
                <input name="planStartEndDate" value="<%=planStartEndDate%>" class="txt_field" size="12" engnum="engnum" maxlength=15 />
                <img src="/plm/portal/images/icon_delete.gif" border=0 onclick="javascript:CommonUtil.deleteDateValue('planStartEndDate');">
                </td>
              </tr>
              <tr>
                <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00647") %><%--개발완료일--%></td>
                <td colspan="3" class="tdwhiteL0">
                <input name="planEndStartDate" value="<%=planEndStartDate%>" class="txt_field" size="12" engnum="engnum" maxlength=15 />
                <img src="/plm/portal/images/icon_delete.gif" border=0 onclick="javascript:CommonUtil.deleteDateValue('planEndStartDate');">&nbsp;~&nbsp;
                <input name="planEndEndDate" value="<%=planEndEndDate%>" class="txt_field" size="12" engnum="engnum" maxlength=15 readonly onclick="javascript:showCal(planEndEndDate);"/>
                <img src="/plm/portal/images/icon_delete.gif" border=0 onclick="javascript:CommonUtil.deleteDateValue('planEndEndDate');">
                </td>
              </tr>
              <tr>
                <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                <td width="230" colspan="3" class="tdwhiteL">
                      <select name="searchPjtState" style="width:95" onChange="javascript:Search();">
                      <OPTION VALUE=""><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></OPTION>
                      <option value="PROGRESS" <%=(searchPjtState.equals("PROGRESS"))?"selected":""%> ><%=messageService.getString("e3ps.message.ket_message", "02726") %><%--진행--%></option>
                      <option value="PLANCHANGE" <%=(searchPjtState.equals("PLANCHANGE"))?"selected":""%> ><%=messageService.getString("e3ps.message.ket_message", "02356") %><%--일정변경--%></option>
                      <option value="COMPLETED" <%=(searchPjtState.equals("COMPLETED"))?"selected":""%> ><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></option>
                      <option value="STOPED" <%=(searchPjtState.equals("STOPED"))?"selected":""%> ><%=messageService.getString("e3ps.message.ket_message", "02695") %><%--중지--%></option>
                      </select>
              </tr>
            </table>
          <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
              <td class="space15"></td>
            </tr>
          </table>
          <table width="100%" border="0" cellspacing="0" cellpadding="0" >
            <tr>
              <td>&nbsp;</td>
              <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><a href="javaScript:excelDown()"><img src="/plm/portal/images/iocn_excel.png" border="0"></a></td>
                    </tr>
                  </table></td>
                  <td width="10"></td>
                  <td align="right"><select name="perPage" class="fm_jmp" style="width:80" onChange="javaScript:dosubmit();" >
                    <option value="10" <%="10".equals(perPage) ? "selected" : ""%>>10</option>
                    <option value="30" <%="30".equals(perPage) ? "selected" : ""%>>30</option>
                    <option value="50" <%="50".equals(perPage) ? "selected" : ""%>>50</option>
                    <option value="100" <%="100".equals(perPage) ? "selected" : ""%>>100</option>
                  </select></td>
                </tr>
              </table></td>
            </tr>
          </table>

          <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
              <td class="space5"></td>
            </tr>
          </table>



          <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
              <td class="space5"></td>
            </tr>
          </table>


<%//if(!command.equals("")){ %>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr>
                    <td  class="tab_btm2"></td>
                  </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr height=30>
                    <td width=5% class="tdblueM">NO</td>
                    <td width=7% class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                    <td width=11% class="tdblueM">Project No</td>
                    <td width=10% class="tdblueM">Project Name</td>
                    <td width=5% class="tdblueM">Rank</td>
                    <td width=10% class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00643") %><%--개발시작일--%></td>
                    <td width=10% class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00647") %><%--개발완료일--%></td>
                    <td width=8% class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                    <td width=24% class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03204") %><%--현재 진행 My Task--%></td>
                </tr>
    <%
    String objOid = "";

    if(qrr != null)  {
        int listCount = total - (psize*(cpage-1));
        while ( qrr.hasMoreElements() ) {
        Object[] o = (Object[])qrr.nextElement();
        String className = (String)o[0];
        BigDecimal decimal = (BigDecimal)o[1];
        long id = decimal.longValue();
        E3PSProject project = (E3PSProject)CommonUtil.getObject(className+ ":" + id);
        E3PSProjectData data = new E3PSProjectData(project);
        String projectName = project.getPjtName();
        String projectNo = project.getPjtNo();
        String taskPlanStartDateStr = "";
        String taskPlanEndDatStr = "";
        if(data.pjtPlanStartDate != null){
            taskPlanStartDateStr = DateUtil.getDateString(data.pjtPlanStartDate, "d");
        }
        if(data.pjtPlanEndDate != null){
            taskPlanEndDatStr = DateUtil.getDateString(data.pjtPlanEndDate, "d");
        }
        String currentTaskNameAll = "";
        String currentTaskNameAllName = "";
        int ctCount = 0;
        Hashtable htMy = null;
        try{
            htMy = ProjectTaskHelper.manager.getMyTaskCondition(project, selectUser);
        }catch(Exception e){ Kogger.error(e);}
    %>
                <tr>
                    <td class="tdwhiteM" ><%=listCount-- %></td>
                    <td class="tdwhiteM" >&nbsp;<%=project.getPjtTypeName() != null ? project.getPjtTypeName() : ""%></td>
                    <td class="tdwhiteM">&nbsp;<a href="javascript:viewProject('<%=PersistenceHelper.getObjectIdentifier( project ).getStringValue()%>');"><%=projectNo%> </a></td>
                    <td class="tdwhiteL" title="<%=projectName%>">&nbsp;
                        <div style="width:100px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;"><%=projectName%></div>
                    </td>
                      <td class="tdwhiteM" >&nbsp;<%=(data.rankName!=null)?data.rankName:""%></td>
                    <td class="tdwhiteM" >&nbsp;<%=taskPlanStartDateStr %></td>
                    <td class="tdwhiteM" >&nbsp;<%=taskPlanEndDatStr %></td>
                    <td class="tdwhiteM" ><%=data.stateKorea%></td>

                      <td class="tdwhiteM" >
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <%
                        if(htMy != null){
                            Vector htv = new Vector(htMy.keySet());
                            Collections.sort(htv, new TaskOneLevelSeqComparator(false));
                            for(int i = 0 ; i < htv.size(); i++){

                                E3PSTask task = (E3PSTask)htv.get(i);
                                String task1 = task.getTaskName();
                                Vector vtTask = (Vector)htMy.get(task);
                                for(int v = 0 ; v < vtTask.size(); v++){
                                    String oneTask = "";
                                    if(v == 0){
                                        oneTask = task1;
                                    }
                                    E3PSTask etask = (E3PSTask)CommonUtil.getObject((String)vtTask.get(v));
                                    E3PSTaskData etData = new E3PSTaskData(etask);

                            %>
                            <tr>
                                <%if(v == 0){ %>
                                <td class="tdwhiteM"  rowspan="<%=vtTask.size()%>" title="<%=oneTask%>"><nobr style="width:80;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><%=oneTask%>&nbsp;</nobr></td>
                                <%} %>
                                <td class="tdwhiteL" title="<%=etask.getTaskName()%>">
                                <nobr style="width:60;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                                <a href='#' onclick="javascript:viewTask('<%=vtTask.get(v)%>');" ><%=etask.getTaskName()%>&nbsp;</a>
                                </nobr></td>
                                <td class="tdwhiteL" title="<%=DateUtil.getDateString(etData.taskPlanEndDate,"D")%>"><nobr style="width:60;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                                <%=DateUtil.getDateString(etData.taskPlanEndDate,"D")%></nobr>
                                </td>
                            </tr>
                            <%  }
                            }

                        }%>
                    </table>
                    </td>

                </tr>

    <%  }

    }

    %>
    <%if(qrr == null || qrr.size() == 0) {%>
                <tr>
                    <td colspan="9" class=tdwhiteM0><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></td>
                </tr>
    <%} %>
            </table>
                  <%
            int ksize = total / psize;
            int x = total % psize;
            if (x > 0)
                ksize++;
            int temp = cpage / pageCount;
            if ((cpage % pageCount) > 0)
                temp++;
            int start = (temp - 1) * pageCount + 1;

            int end = start + pageCount - 1;
            if (end > ksize) {
                end = ksize;
            }
        %>


        <table border="0" cellspacing="0" cellpadding="0" width="780">
          <tr>
            <td class="space10"></td>
          </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="small"><span class="small">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%> :<%=ksize%>)</span></td>
                <td>
                <table border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="40" align="center">
                        <%
                        if (start > 1) {
                        %><a href="JavaScript:gotoPage(1)" class="small"><img src="/plm/portal/images/btn_arrow4.gif"></a>
                        <%
                        }
                        %>
                        </td>
                        <td width="1" bgcolor="#dddddd"></td>
                        <%
                        if (start > 1) {
                        %>
                        <td width="60" class="quick" align="center"><a
                            href="JavaScript:gotoPage(<%=start-1%>)" class="smallblue"><img src="/plm/portal/images/btn_arrow3.gif"></a></td>
                        <td width="1" bgcolor="#dddddd"></td>
                        <%
                            }
                            for (int i = start; i <= end; i++) {
                        %>
                        <td style="padding:2 8 0 7;cursor:hand"
                            onMouseOver="this.style.background='#ECECEC'"
                            OnMouseOut="this.style.background=''" class="nav_on">
                        <%
                        if (i == cpage) {
                        %><b><%=i%>
                        <%
                        } else {
                        %><a href="JavaScript:gotoPage(<%=i%>)"><%=i%></a>
                        <%
                        }
                        %>
                        </td>
                        <%
                            }
                            if (end < ksize) {
                        %>
                        <td width="1" bgcolor="#dddddd"></td>
                        <td width="60" align="center"><a
                            href="JavaScript:gotoPage(<%=end+1%>)" class="smallblue"><img src="/plm/portal/images/btn_arrow1.gif"></a></td>
                        <%
                        }
                        %>
                        <td width="1" bgcolor="#dddddd"></td>
                        <td width="45" align="center">
                        <%
                        if (end < ksize) {
                        %><a href="JavaScript:gotoPage(<%=ksize%>)"
                            class="small"><img src="/plm/portal/images/btn_arrow2.gif"></a>
                        <%
                        }
                        %>
                        </td>
                    </tr>
                </table>
                </td>
                <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>:<%=total%>)</td>
            </tr>
        </table>
<%//} %>
           </td>
          <td width="10" background="../../portal/images/box_15.gif">&nbsp;</td>
        </tr>
        <tr>
          <td width="10" height="10"><img src="../../portal/images/box_11.gif"></td>
          <td height="10" background="../../portal/images/box_16.gif"></td>
          <td width="10" height="10"><img src="../../portal/images/box_12.gif"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>

</form>
</html>

