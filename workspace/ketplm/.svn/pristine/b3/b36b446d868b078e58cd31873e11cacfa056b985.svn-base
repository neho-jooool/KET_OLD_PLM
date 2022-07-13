<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import = "java.util.ArrayList,
                   java.util.Hashtable,
                   e3ps.common.util.StringUtil,
                    e3ps.common.util.DateUtil,
                   e3ps.common.web.ParamUtil" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    ArrayList list = (ArrayList)request.getAttribute("list");
    Hashtable condition = (Hashtable)request.getAttribute("condition");

    String cmd          = "";
    String devType      = "";
    String statestate        = "";
    String dept          = "";
    String dept1        = "";
    String dept2        = "";
    String dept3        = "";
    String creator        = "";
    String year          = "";
    String pTaskName     = "";
    String mTaskName    = "";
    String itemType      = "";
    String making      = "";
    String moldType1    = "";
    String moldType2    = "";
    String name         = "";
    String pjtno         = "";
    String pjtLink       = "";
    String pjtname       = "";
    String taskstate     = "";
    String taskname     = "";
    String departName   = "";
    String userName     = "";
    String planenddate   = "";
    String execenddate   = "";
    String execstartdate   = "";
    String checkDept       = "";

    int days = DateUtil.getDaysFrom21Century( DateUtil.getCurrentDateString("d")  );

    if( condition != null && !condition.isEmpty() ){
        cmd          = StringUtil.checkNull((String)condition.get("cmd"));
        checkDept       = ParamUtil.getParameter(request, "checkDept");
        devType       = ParamUtil.getParameter(request, "devType");
        statestate       = ParamUtil.getParameter(request, "statestate");
        dept          = ParamUtil.getParameter(request, "dept");
        dept1        = ParamUtil.getParameter(request, "dept1");
        dept2        = ParamUtil.getParameter(request, "dept2");
        dept3        = ParamUtil.getParameter(request, "dept3");
        creator        = ParamUtil.getParameter(request, "creator");
        year          = ParamUtil.getParameter(request, "year");
        pTaskName       = ParamUtil.getParameter(request, "pTaskName");
         mTaskName      = ParamUtil.getParameter(request, "mTaskName");

        itemType       = StringUtil.checkNull((String)condition.get("itemType"));
        making         = StringUtil.checkNull((String)condition.get("making"));
        moldType1     = StringUtil.checkNull((String)condition.get("moldType1"));
        moldType2     = StringUtil.checkNull((String)condition.get("moldType2"));

        name         = StringUtil.checkNull((String)condition.get("name"));
        pjtno         = StringUtil.checkNull((String)condition.get("pjtno"));
        pjtLink         = StringUtil.checkNull((String)condition.get("pjtLink"));
        pjtname       = StringUtil.checkNull((String)condition.get("pjtname"));
        taskstate       = StringUtil.checkNull((String)condition.get("taskstate"));
        taskname       = StringUtil.checkNull((String)condition.get("taskname"));
        departName     = StringUtil.checkNull((String)condition.get("departName"));
        userName       = StringUtil.checkNull((String)condition.get("userName"));
        planenddate     = StringUtil.checkNull((String)condition.get("planenddate"));
        execenddate     = StringUtil.checkNull((String)condition.get("execenddate"));
    }

    String sortSQL = "";
    if( condition != null && !condition.isEmpty() )
    {
        sortSQL = condition.get("sort").toString().replace(" ", "");
        if( "".equals(sortSQL) || sortSQL == null ){
            sortSQL = "2DESC";
        }
    }

    String sortCol1 = "";
    if("reportList4".equals(cmd)){
        sortCol1 = "기술<br>유형";
    }else{
        sortCol1 = messageService.getString("e3ps.message.ket_message", "00625", "<br>")/*개발{0}구분*/;
    }
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
    String sortCol4 = "진행현황";
    if(sortSQL.equals("4ASC") || sortSQL.equals("4DESC")) {
        sortCol4 = "<u>"+sortCol4+"</u>";
    }
    String sortCol5 = "TASK";
    if(sortSQL.equals("5ASC") || sortSQL.equals("5DESC")) {
        sortCol5 = "<u>"+sortCol5+"</u>";
    }
    String sortCol6 = "TASK<br>주관부서";
    if(sortSQL.equals("6ASC") || sortSQL.equals("6DESC")) {
        sortCol6 = "<u>"+sortCol6+"</u>";
    }
    String sortCol7 = "TASK<br>담당자";
    if(sortSQL.equals("7ASC") || sortSQL.equals("7DESC")) {
        sortCol7 = "<u>"+sortCol7+"</u>";
    }
    String sortCol8 = messageService.getString("e3ps.message.ket_message", "00822")/*계획일*/;
    if(sortSQL.equals("8ASC") || sortSQL.equals("8DESC")) {
        sortCol8 = "<u>"+sortCol8+"</u>";
    }
    String sortCol9 = "실제일";
    if(sortSQL.equals("9ASC") || sortSQL.equals("9DESC")) {
        sortCol9 = "<u>"+sortCol9+"</u>";
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
        var sqlColNumber ='<%=StringUtil.getNumber(sortSQL)%>';
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
        document.forms[0].cmd.value = "<%=cmd%>";
        document.forms[0].devType.value = "<%=devType%>";
        document.forms[0].statestate.value = "<%=statestate%>";
        document.forms[0].dept.value = "<%=dept%>";
        document.forms[0].dept1.value = "<%=dept1%>";
        document.forms[0].dept2.value = "<%=dept2%>";
        document.forms[0].dept3.value = "<%=dept3%>";
        document.forms[0].creator.value = "<%=creator%>";
        document.forms[0].year.value = "<%=year%>";
        document.forms[0].pTaskName.value = "<%=pTaskName%>";
        document.forms[0].mTaskName.value = "<%=mTaskName%>";
        document.forms[0].checkDept.value = "<%=checkDept%>";
        <% if("reportList4".equals(cmd)){ %>
            document.forms[0].itemType.value ="<%=itemType%>";
            document.forms[0].making.value ="<%=making%>";
            document.forms[0].moldType1.value ="<%=moldType1%>";
            document.forms[0].moldType2.value ="<%=moldType2%>";
        <% } %>

        document.forms[0].sort.value = sortSQL;

        document.forms[0].action = "/plm/servlet/e3ps/TaskReportServlet";
        document.forms[0].submit();
    }

//-->
</script>
</head>
<body>
<form method="post">

<!-- hidden begin -->
<input type='hidden' name='cmd' value="">
<input type='hidden' name='devType' value="">
<input type='hidden' name='statestate' value="">
<input type='hidden' name='dept' value="">
<input type='hidden' name='dept1' value="">
<input type='hidden' name='dept2' value="">
<input type='hidden' name='dept3' value="">
<input type='hidden' name='creator' value="">
<input type='hidden' name='year' value="">
<input type='hidden' name='pTaskName' value="">
<input type='hidden' name='mTaskName' value="">

<input type='hidden' name='itemType' value="">
<input type='hidden' name='making' value="">
<input type='hidden' name='moldType1' value="">
<input type='hidden' name='moldType2' value="">
<input type='hidden' name='checkDept' value="">

<input type='hidden'  name='sort'  value="">
<!-- hidden end -->

<div id="div_scroll" style="width:860px; height:570px; overflow:auto;">
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr class="headerLock">
      <td>
          <% if (list.size() > 10) { %>
        <table border="0" cellspacing="0" cellpadding="0" width="842" style=table-layout:fixed>
            <col width='35'><col width='55'><col width='70'><col width='167'><col width='60'><col width='130'><col width='100'><col width='50'><col width='75'><col width='60'>
        <% } else { %>
        <table border="0" cellspacing="0" cellpadding="0" width="860" style=table-layout:fixed>
            <col width='35'><col width='55'><col width='70'><col width='185'><col width='60'><col width='130'><col width='100'><col width='50'><col width='75'><col width='60'>
        <% } %>
            <tr>
          <td rowspan="2" class="tdblueM">No</td>
          <td rowspan="2" class="tdblueM"><a href="javascript:doSort('sortCol1', '1');"><span id="sortCol1"><%=sortCol1%></span><a></td>
          <td rowspan="2" class="tdblueM"><a href="javascript:doSort('sortCol2', '2');"><span id="sortCol2"><%=sortCol2%></span><a></td>
          <td rowspan="2" class="tdblueM"><a href="javascript:doSort('sortCol3', '3');"><span id="sortCol3"><%=sortCol3%></span><a></td>
          <td rowspan="2" class="tdblueM"><a href="javascript:doSort('sortCol4', '4');"><span id="sortCol4"><%=sortCol4%></span><a></td>
          <td rowspan="2" class="tdblueM"><a href="javascript:doSort('sortCol5', '5');"><span id="sortCol5"><%=sortCol5%></span><a></td>
          <td rowspan="2" class="tdblueM"><a href="javascript:doSort('sortCol6', '6');"><span id="sortCol6"><%=sortCol6%></span><a></td>
          <td rowspan="2" class="tdblueM"><a href="javascript:doSort('sortCol7', '7');"><span id="sortCol7"><%=sortCol7%></span><a></td>
          <td colspan="2" class="tdwhiteM0"><font color="#4398BC"><%=messageService.getString("e3ps.message.ket_message", "00501") %><%--TASK완료--%></font></td>

        </tr>
        <tr>
          <td class="tdblueM"><a href="javascript:doSort('sortCol8', '8');"><span id="sortCol8"><%=sortCol8%></span><a></td>
          <td class="tdwhiteM0"><a href="javascript:doSort('sortCol9', '9');"><span id="sortCol9"><%=sortCol9%></span><a></td>
        </tr>
      </table>
    </td>
  </tr>
    <tr>
        <td>
            <% if (list.size() > 10) { %>
            <table border="0" cellspacing="0" cellpadding="0" width="842" style=table-layout:fixed>
                <col width='35'><col width='55'><col width='70'><col width='167'><col width='60'><col width='130'><col width='100'><col width='50'><col width='75'><col width='60'>
          <% } else { %>
          <table border="0" cellspacing="0" cellpadding="0" width="860" style=table-layout:fixed>
                <col width='35'><col width='55'><col width='70'><col width='185'><col width='60'><col width='130'><col width='100'><col width='50'><col width='75'><col width='60'>
          <% } %>
            <%
            Hashtable project = null;
            int lastInx = 0;
          if( list != null && list.size() > 0 ){
            lastInx = list.size();
                for(int inx = 0 ; inx < list.size(); inx++){
                          project = (Hashtable)list.get(inx);
        %>
            <tr>
                    <td class="tdwhiteM"><%=lastInx--%></td>
                    <td class="tdwhiteM"><%=project.get("name")%>&nbsp;</td>
                    <td class="tdwhiteM"><a href="javascript:viewProjectPopup('<%=project.get("pjtLink")%>');"><%=project.get("pjtno")%></a></td>
                    <% if (list.size() > 10) { %>
                    <td class="tdwhiteL"><span class="pjtname1" title="<%=project.get("pjtname")%>"><%=project.get("pjtname")%></span></td>
                    <% } else { %>
                    <td class="tdwhiteL"><span class="pjtname2" title="<%=project.get("pjtname")%>"><%=project.get("pjtname")%></span></td>
                    <% } %>
                    <% if( project.get("taskstate").equals("5") ){  %>
                        <td class="tdwhiteM"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_green_bar.gif"></td>
                    <%}else if( project.get("taskstate").equals("0")){
                            String planEDT = project.get("planenddate").toString().substring(0,10);
                            int planend = DateUtil.getDaysFrom21Century( planEDT  );
                            if(days < planend ){
                    %>
                            <td class="tdwhiteM"><img src="../../portal/images/state_blue_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"></td>
                    <%
                            }else{
                                if(days == planend && project.get("taskcompletion").equals("0")){
                    %>
                                    <td class="tdwhiteM"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_yellow_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"></td>
                    <%
                                }else{
                     %>
                                    <td class="tdwhiteM"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_red_bar.gif"><img src="../../portal/images/state_blank_bar.gif"></td>
                    <%
                                }
                            }
                        }else{}
                    %>
                    <td class="tdwhiteL"><%=project.get("taskname")%></td>
                    <td class="tdwhiteM"><%=project.get("departName")%></td>
                    <td class="tdwhiteM"><%=project.get("userName")%></td>
                    <td class="tdwhiteM"><%=project.get("planenddate")%></td>
                    <td class="tdwhiteM0"><%=project.get("execenddate")%></td>
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
