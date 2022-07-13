<%@page import="wt.fc.ObjectReference"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.ExtendScheduleData"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.beans.TaskHelper"%>
<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.ProductProject"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import = "java.util.ArrayList,
                   java.util.Hashtable,
                   e3ps.common.util.StringUtil,
                   e3ps.common.web.ParamUtil" %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    ArrayList list = (ArrayList)request.getAttribute("list");
    Hashtable condition = (Hashtable)request.getAttribute("condition");

    String cmd = "";
    String year = "";
    String division = "";
    String dept = "";
    String customerNo = "";
    String devType = "";
    String statestate = "";
    String deleyState = "";
    String itemType = "";
    String making = "";
    String moldType1 = "";
    String moldType2 = "";

    if( condition != null && !condition.isEmpty() ){
        cmd = StringUtil.checkNull((String)condition.get("cmd"));
        year = StringUtil.checkNull((String)condition.get("year"));
        division = StringUtil.checkNull((String)condition.get("division"));
        dept = StringUtil.checkNull((String)condition.get("dept"));
        customerNo = StringUtil.checkNull((String)condition.get("customerNo"));
        devType = StringUtil.checkNull((String)condition.get("devType"));
        statestate = StringUtil.checkNull((String)condition.get("statestate"));
        deleyState = StringUtil.checkNull((String)condition.get("deleyState"));
        itemType = StringUtil.checkNull((String)condition.get("itemType"));
        making = StringUtil.checkNull((String)condition.get("making"));
        moldType1 = StringUtil.checkNull((String)condition.get("moldType1"));
        moldType2 = StringUtil.checkNull((String)condition.get("moldType2"));
    }

  String sortSQL = "";
    if( condition != null && !condition.isEmpty() )
    {
        sortSQL = condition.get("sort").toString().replace(" ", "");
    }

    String sortCol1 = messageService.getString("e3ps.message.ket_message", "00625", "<br>")/*개발{0}구분*/;
    if(sortSQL.equals("1ASC") || sortSQL.equals("1DESC")) {
        sortCol1 = "<u>"+sortCol1+"</u>";
    }

    String sortCol2 = "Project<br>No";
    if(sortSQL.equals("2ASC") || sortSQL.equals("2DESC")) {
        sortCol2 = "<u>"+sortCol2+"</u>";
    }

    String sortCol3 = "Project Name";
    if(sortSQL.equals("3ASC") || sortSQL.equals("3DESC")) {
        sortCol3 = "<u>"+sortCol3+"</u>";
    }

    String sortCol4 = messageService.getString("e3ps.message.ket_message", "00643", "<br>")/*개발{0}시작일*/;
    if(sortSQL.equals("4ASC") || sortSQL.equals("4DESC")) {
        sortCol4 = "<u>"+sortCol4+"</u>";
    }

    String sortCol5 = messageService.getString("e3ps.message.ket_message", "00822")/*계획일*/;
    if(sortSQL.equals("5ASC") || sortSQL.equals("5DESC")) {
        sortCol5 = "<u>"+sortCol5+"</u>";
    }

    String sortCol6 = messageService.getString("e3ps.message.ket_message", "02065")/*실제일*/;
    if(sortSQL.equals("6ASC") || sortSQL.equals("6DESC")) {
        sortCol6 = "<u>"+sortCol6+"</u>";
    }

    String sortCol7 = messageService.getString("e3ps.message.ket_message", "02733")/*진행일*/;
    if(sortSQL.equals("7ASC") || sortSQL.equals("7DESC")) {
        sortCol7 = "<u>"+sortCol7+"</u>";
    }

    String sortCol8 = messageService.getString("e3ps.message.ket_message", "02672", "<br>")/*주관{0}부서*/;
    if(sortSQL.equals("8ASC") || sortSQL.equals("8DESC")) {
        sortCol8 = "<u>"+sortCol8+"</u>";
    }

    String sortCol9 = messageService.getString("e3ps.message.ket_message", "02143")/*예산*/;
    if(sortSQL.equals("9ASC") || sortSQL.equals("9DESC")) {
        sortCol9 = "<u>"+sortCol9+"</u>";
    }

    String sortCol10 = messageService.getString("e3ps.message.ket_message", "02032")/*실적*/;
    if(sortSQL.equals("10ASC") || sortSQL.equals("10DESC")) {
        sortCol10 = "<u>"+sortCol10+"</u>";
    }

    String sortCol11 = messageService.getString("e3ps.message.ket_message", "02736")/*진행현황*/;
    if(sortSQL.equals("11ASC") || sortSQL.equals("11DESC")) {
        sortCol11 = "<u>"+sortCol11+"</u>";
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>

<style>
 .headerLock {
  position: relative;
  top:expression(document.getElementById("div_scroll").scrollTop);
  z-index:99;
 }

 .pjtname1{
width:162;
overflow:hidden;
text-overflow:ellipsis;
white-space:nowrap;
}

.pjtname2{
width:180;
overflow:hidden;
text-overflow:ellipsis;
white-space:nowrap;
}

.dept{
width:70;
overflow:hidden;
text-overflow:ellipsis;
white-space:nowrap;
}
</style>

<script language='javascript'>
<!--

    //프로젝트상세조회 팝업
    function viewProjectPopup(oid) {
        var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid=" + oid + "&popup=popup";
        openWindow(url, '',1050,800);
    }

    //검색결과 정렬
    function doSort(sortColId, sortColName){
        var sortSQL = "";
        var sqlColNumber = '<%=StringUtil.getNumber(sortSQL)%>';
        var sqlColName = '<%=sortSQL%>';
        var sortA = sortColName+"ASC";
        var sortD = sortColName+"DESC";


        if(sqlColNumber == sortColName) {
            if(sqlColName == sortA){
                sortSQL = sortColName + " DESC ";
            }else if(sqlColName == sortD){
                sortSQL = sortColName + " ASC ";
            }
        } else {
            sortSQL = sortColName + " ASC ";
        }

        search(sortSQL);
    }

    // 검색
    function search(sortSQL){
        document.forms[0].sort.value = sortSQL;

        document.forms[0].cmd.value = "<%=cmd%>";
        document.forms[0].year.value = "<%=year%>";
        document.forms[0].division.value = "<%=division%>";
        document.forms[0].dept.value = "<%=dept%>";
        document.forms[0].customerNo.value = "<%=customerNo%>";
        document.forms[0].statestate.value = "<%=statestate%>";
        <% if( cmd != null && cmd.equals("reportList1") ){ %>
                document.forms[0].devType.value = "<%=devType%>";
        <% }else if( cmd != null && cmd.equals("reportList2") ){ %>
                document.forms[0].devType.value = "<%=devType%>";
                document.forms[0].deleyState.value = "<%=deleyState%>";
        <% }else if( cmd != null && cmd.equals("reportList3") ){ %>
                document.forms[0].devType.value = "<%=devType%>";
        <% }else if( cmd != null && cmd.equals("reportList4") ){ %>
                document.forms[0].itemType.value = "<%=itemType%>";
                document.forms[0].making.value = "<%=making%>";
                document.forms[0].moldType1.value = "<%=moldType1%>";
                document.forms[0].moldType2.value = "<%=moldType2%>";
                document.forms[0].deleyState.value = "<%=deleyState%>";
        <% }else if( cmd != null && cmd.equals("reportList5") ){ %>
                document.forms[0].itemType.value = "<%=itemType%>";
                document.forms[0].making.value = "<%=making%>";
                document.forms[0].moldType1.value = "<%=moldType1%>";
                document.forms[0].moldType2.value = "<%=moldType2%>";
        <% } %>

        document.forms[0].action = "/plm/servlet/e3ps/ProjectReportServlet";
        document.forms[0].submit();
    }

//-->
</script>
</head>
<body>
<form method="post">

<!-- hidden begin -->
<input type='hidden' name='cmd' value="">
<input type='hidden' name='year' value="">
<input type='hidden' name='division' value="">
<input type='hidden' name='dept' value="">
<input type='hidden' name='customerNo' value="">
<input type='hidden' name='devType' value="">
<input type='hidden' name='statestate' value="">
<input type='hidden' name='deleyState' value="">
<input type='hidden' name='itemType' value="">
<input type='hidden' name='making' value="">
<input type='hidden' name='moldType' value="">
<input type='hidden' name='moldType1' value="">
<input type='hidden' name='moldType2' value="">
<input type='hidden' name='sort' value="">
<!-- hidden end -->

<div id="div_scroll" style="width:860px; height:570px; overflow:auto;">
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr class="headerLock">
      <td>
          <% if (list.size() > 10) { %>
        <table border="0" cellspacing="0" cellpadding="0" width="842" style=table-layout:fixed>
            <col width='35'><col width='45'><col width='70'><col width='167'><col width='70'><col width='70'><col width='70'><col width='50'><col width='75'><col width='60'><col width='60'><col width='70'>
        <% } else { %>
        <table border="0" cellspacing="0" cellpadding="0" width="860" style=table-layout:fixed>
            <col width='35'><col width='45'><col width='70'><col width='185'><col width='70'><col width='70'><col width='70'><col width='50'><col width='75'><col width='60'><col width='60'><col width='70'>
        <% } %>
            <tr>
          <td rowspan="2" class="tdblueM">No</td>
          <td rowspan="2" class="tdblueM"><a href="javascript:doSort('sortCol1', '1');"><span id="sortCol1"><%=sortCol1%></span><a></td>
          <td rowspan="2" class="tdblueM"><a href="javascript:doSort('sortCol2', '2');"><span id="sortCol2"><%=sortCol2%></span><a></td>
          <td rowspan="2" class="tdblueM"><a href="javascript:doSort('sortCol3', '3');"><span id="sortCol3"><%=sortCol3%></span><a></td>
          <td rowspan="2" class="tdblueM"><a href="javascript:doSort('sortCol4', '4');"><span id="sortCol4"><%=sortCol4%></span><a></td>
          <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00646") %><%--개발완료--%></td>
          <td rowspan="2" class="tdblueM"><a href="javascript:doSort('sortCol7', '7');"><span id="sortCol7"><%=sortCol7%></span><a></td>
          <td rowspan="2" class="tdblueM"><a href="javascript:doSort('sortCol8', '8');"><span id="sortCol8"><%=sortCol8%></span><a></td>
          <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01642") %><%--비용(단위:천원)--%></td>
          <td rowspan="2" class="tdblueM0"><a href="javascript:doSort('sortCol11', '11');"><span id="sortCol11"><%=sortCol11%></span><a></td>
        </tr>
        <tr>
          <td class="tdblueM"><a href="javascript:doSort('sortCol5', '5');"><span id="sortCol5"><%=sortCol5%></span><a></td>
          <td class="tdblueM"><a href="javascript:doSort('sortCol6', '6');"><span id="sortCol6"><%=sortCol6%></span><a></td>
          <td class="tdblueM"><a href="javascript:doSort('sortCol9', '9');"><span id="sortCol9"><%=sortCol9%></span><a></td>
          <td class="tdblueM"><a href="javascript:doSort('sortCol10', '10');"><span id="sortCol10"><%=sortCol10%></span><a></td>
        </tr>
      </table>
    </td>
  </tr>
    <tr>
        <td>
            <% if (list.size() > 10) { %>
            <table border="0" cellspacing="0" cellpadding="0" width="842" style=table-layout:fixed>
                <col width='35'><col width='45'><col width='70'><col width='167'><col width='70'><col width='70'><col width='70'><col width='50'><col width='75'><col width='60'><col width='60'><col width='70'>
          <% } else { %>
          <table border="0" cellspacing="0" cellpadding="0" width="860" style=table-layout:fixed>
                <col width='35'><col width='45'><col width='70'><col width='185'><col width='70'><col width='70'><col width='70'><col width='50'><col width='75'><col width='60'><col width='60'><col width='70'>
          <% } %>
            <%
            Hashtable project = null;
            int lastInx = 0;
            boolean checkpp = false;
            String exceDate ="";
          if( list != null && list.size() > 0 ){
            lastInx = list.size();
                for(int inx = 0 ; inx < list.size(); inx++){
                          project = (Hashtable)list.get(inx);

                          String pOid = project.get("oid").toString();
                          E3PSProject ep = (E3PSProject)CommonUtil.getObject(pOid);

                          if(ep instanceof ProductProject) {
                              E3PSTask task = getTask(pOid, "DR6");
                              if(task != null && "COMPLETED".equals(project.get("statestate"))) {
                                  ExtendScheduleData data = (ExtendScheduleData)task.getTaskSchedule().getObject();
                                  exceDate = DateUtil.getDateString(data.getExecEndDate(),"d");
                                  checkpp = true;
                              }
                          }
        %>
            <tr>
                    <td class="tdwhiteM"><%=lastInx--%></td>
                    <td class="tdwhiteM"><%=project.get("devType")%>&nbsp;</td>
                    <td class="tdwhiteM"><a href="javascript:viewProjectPopup('<%=project.get("oid")%>');"><%=project.get("pjtno")%></a></td>
                    <% if (list.size() > 10) { %>
                    <td class="tdwhiteL"><span class="pjtname1" title="<%=project.get("pjtname")%>"><%=project.get("pjtname")%></span></td>
                    <% } else { %>
                    <td class="tdwhiteL"><span class="pjtname2" title="<%=project.get("pjtname")%>"><%=project.get("pjtname")%></span></td>
                    <% } %>
                    <td class="tdwhiteM"><%=project.get("planstartdate")%></td>
                    <td class="tdwhiteM"><%=project.get("planenddate")%>&nbsp;</td>
                    <td class="tdwhiteM"><%=checkpp ? exceDate : project.get("execenddate")%>&nbsp;</td>
                    <td class="tdwhiteM">
                        <% if (project.get("term") != null && ((String)project.get("term")).length() > 0) { %>
                        <%=messageService.getString("e3ps.message.ket_message", "00138", project.get("term")) %><%--{0}일--%>
                        <% }else { %>
                        &nbsp;
                        <% } %>
                    </td>
                    <td class="tdwhiteM"><span class="dept" title="<%=project.get("dept")%>"><%=project.get("dept")%></span></td>
                    <td class="tdwhiteR">&nbsp;<%=project.get("budget")%></td>
                    <% if( StringUtil.getIntParameter(StringUtil.getNumber((String)project.get("execution"))) > StringUtil.getIntParameter(StringUtil.getNumber((String)project.get("budget"))) ) {%>
                    <td class="tdwhiteR"><font color="RED"><%=project.get("execution")%></font></td>
                    <% }else { %>
                    <td class="tdwhiteR">&nbsp;<%=project.get("execution")%></td>
                    <% } %>
                    <td class="tdwhiteM0">
                    <% if (project.get("statestate") != null && ((String)(project.get("statestate"))).equals("COMPLETED")) { %>
                        <img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_green_bar.gif"></td>
                    <% }else if (project.get("statestate") != null && ((String)(project.get("statestate"))).equals("PROGRESS")) { %>
                    <%   if (project.get("pjtstate") != null && ((String)(project.get("pjtstate"))).equals("4")) { %>
                      <img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_red_bar.gif"><img src="../../portal/images/state_blank_bar.gif"></td>
                  <%   }else if (project.get("pjtstate") != null && ((String)(project.get("pjtstate"))).equals("3")) { %>
                    <img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_yellow_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"></td>
                    <%   }else { %>
                        <img src="../../portal/images/state_blue_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"></td>
                    <%   } %>
                    <% }else { %>
                        <img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"></td>
                  <% } %>
                </tr>
                <%
              }
          }else{
        %>
         <tr>
                     <td colspan="12"  class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></td>
                 </tr>
        <%}%>
            </table>
        </td>
    </tr>
</table>
</div>
</form>
</body>
</html>
<%!
public E3PSTask getTask(String oid, String taskName){
    E3PSTask task = null;
    try{
        QuerySpec qs = new QuerySpec(E3PSTask.class);
        long longOid = CommonUtil.getOIDLongValue(oid);

        qs.appendWhere(new SearchCondition(E3PSTask.class,"projectReference.key.id",SearchCondition.EQUAL,longOid),new int[0]);
        qs.appendAnd();
        qs.appendWhere(new SearchCondition(E3PSTask.class,"taskName",SearchCondition.EQUAL,taskName),new int[0]);
        QueryResult qr = PersistenceHelper.manager.find(qs);

        while(qr.hasMoreElements()) {
            Object o = (Object)qr.nextElement();
            task = (E3PSTask)o;
            return task;
        }
    }catch(Exception e){
	Kogger.error(e);
    }

    return task;
}
%>
