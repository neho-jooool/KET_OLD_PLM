<%@page contentType="text/html; charset=UTF-8" buffer="16kb"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.org.WTUser"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.fc.QueryResult"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%

  String searchYear = request.getParameter("tmpYear");
  String searchMonth = request.getParameter("tmpMonth");

  if(!StringUtil.checkString(searchYear)) searchYear = DateUtil.getThisYear();
  if(!StringUtil.checkString(searchMonth)) searchMonth = "";//DateUtil.getThisMonth();

  String searchPjtName = request.getParameter("searchPjtName");
  String searchTaskName = request.getParameter("searchTaskName");
  String searchState = request.getParameter("searchState");
  String initType = request.getParameter("initType");
  String setUser = request.getParameter("setUser");
  String setDept = request.getParameter("setDept");

  if(searchPjtName == null){
    searchPjtName= "";
  }
  if(searchTaskName == null){
    searchTaskName= "";
  }
  if(searchState == null){
    searchState= "0";
  }
  if(setDept == null){
    setDept= "";
  }

  Department depart = null;
  WTUser wtuser = null;
  if(setUser != null){
    if(setUser.length() == 0){
      wtuser = null;
    }else{
      wtuser = (WTUser)CommonUtil.getObject(setUser);
    }
  }else{
    wtuser  = (WTUser)SessionHelper.manager.getPrincipal();
  }

  if(setDept != null && setDept.length() > 0 ){
    depart = (Department)CommonUtil.getObject(setDept);
  }else{
    if(wtuser != null){
      depart = DepartmentHelper.manager.getDepartment(wtuser);
    }
  }

  long long_dept = 0;
    if(!setDept.equals("")){
      long_dept = CommonUtil.getOIDLongValue(setDept);
    }

  QuerySpec spec = null;

    spec = new QuerySpec();
  //int output_idx = spec.addClassList(ProjectOutput.class, false);
  int task_idx = spec.appendClassList(JELTask.class, false);
  int project_idx = spec.addClassList(JELProject.class, false);
  int schedIndex2 = spec.appendClassList(ExtendScheduleData.class, false);
  int master_idx = spec.appendClassList(JELProjectMaster.class, false);
  int memberlink_idx = spec.appendClassList(TaskMemberLink.class, false);


  ClassInfo classinfo2 = WTIntrospector.getClassInfo(ProjectOutput.class);
  DatabaseInfo databaseinfo2 = classinfo2.getDatabaseInfo();
  BaseTableInfo basetableinfo2 = databaseinfo2.getBaseTableInfo();
  String objname2 = basetableinfo2.getColumnDescriptor("thePersistInfo.theObjectIdentifier.classname").getColumnName();
  String objid2 = basetableinfo2.getColumnDescriptor("thePersistInfo.theObjectIdentifier.id").getColumnName();

  KeywordExpression ke2 = new KeywordExpression(spec.getFromClause().getAliasAt(task_idx) + "." + objname2 +
      "||':'||" +
      spec.getFromClause().getAliasAt(task_idx) + "." + objid2);
  ke2.setColumnAlias("taskOid");
  spec.appendSelect(ke2, new int[]{task_idx}, false);


    ClassAttribute planCa2 = new ClassAttribute(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE);
  planCa2.setColumnAlias("PLAN_START_TIME");
  spec.appendSelect(planCa2, new int[]{schedIndex2}, false);



  SearchCondition exxsc = new SearchCondition(new ClassAttribute(JELTask.class, "taskSchedule.key.id"),
        "=", new ClassAttribute(ExtendScheduleData.class, WTAttributeNameIfc.ID_NAME));
  exxsc.setFromIndicies(new int[]{task_idx, schedIndex2}, 0);
  exxsc.setOuterJoin(0);
    spec.appendWhere(exxsc, new int[]{task_idx, schedIndex2});

    //spec.appendAnd();


     //SearchCondition sc = new SearchCondition(new ClassAttribute(ProjectOutput.class, "projectReference.key.id"),"=",
     //    new ClassAttribute(JELProject.class,"thePersistInfo.theObjectIdentifier.id"));
  //sc.setFromIndicies(new int[]{output_idx,project_idx}, 0);
  //sc.setOuterJoin(0);
  //spec.appendWhere(sc,new int[]{output_idx,project_idx });

  //spec.appendAnd();

  //SearchCondition tasksc = new SearchCondition(new ClassAttribute(e3ps.project.JELTask.class, WTAttributeNameIfc.ID_NAME),
    //    "=", new ClassAttribute(ProjectOutput.class, "taskReference.key.id"));
  //tasksc.setFromIndicies(new int[]{task_idx, output_idx}, 0);
    //tasksc.setOuterJoin(0);
    //spec.appendWhere(tasksc, new int[]{task_idx, output_idx});

    spec.appendAnd();
    SearchCondition tpsc = new SearchCondition(new ClassAttribute(JELTask.class, "projectReference.key.id"),"=",
         new ClassAttribute(JELProject.class,"thePersistInfo.theObjectIdentifier.id"));
  tpsc.setFromIndicies(new int[]{task_idx,project_idx}, 0);
  tpsc.setOuterJoin(0);
  spec.appendWhere(tpsc,new int[]{task_idx,project_idx });

  /*
  spec.appendAnd();
  SearchCondition taskMembersc = new SearchCondition(new ClassAttribute(e3ps.project.JELTask.class, WTAttributeNameIfc.ID_NAME),
        "=", new ClassAttribute(TaskMemberLink.class, "roleAObjectRef.key.id"));
  taskMembersc.setFromIndicies(new int[]{task_idx, memberlink_idx}, 0);
  taskMembersc.setOuterJoin(0);
    spec.appendWhere(taskMembersc, new int[]{task_idx, memberlink_idx});
  */

  spec.appendAnd();
  SearchCondition mastersc = new SearchCondition(new ClassAttribute(JELProject.class, "masterReference.key.id"),
        "=", new ClassAttribute(JELProjectMaster.class, "thePersistInfo.theObjectIdentifier.id"));
  mastersc.setFromIndicies(new int[]{project_idx, master_idx}, 0);
  mastersc.setOuterJoin(0);
    spec.appendWhere(mastersc, new int[]{project_idx, master_idx});


    spec.appendAnd();
  SearchCondition ttl_sc = new SearchCondition(new ClassAttribute(JELTask.class, "thePersistInfo.theObjectIdentifier.id"),
        "=", new ClassAttribute(TaskMemberLink.class, "roleAObjectRef.key.id"));
  ttl_sc.setFromIndicies(new int[]{task_idx, memberlink_idx}, 0);
  ttl_sc.setOuterJoin(0);
  spec.appendWhere(ttl_sc, new int[]{task_idx, memberlink_idx});



  //out.println(wtuser +":"+long_dept);
  if(wtuser != null){
      long oidValue = CommonUtil.getOIDLongValue(wtuser);


    if(spec.getConditionCount() > 0){
      spec.appendAnd();
    }
      spec.appendWhere(new SearchCondition(TaskMemberLink.class, "actor.key.id", SearchCondition.EQUAL, oidValue), memberlink_idx);

  }else{
      if( long_dept != 0 ){
           if(spec.getConditionCount() > 0){
          spec.appendAnd();
          spec.appendWhere(new SearchCondition(JELTask.class, "departmentReference.key.id", SearchCondition.EQUAL, long_dept),new int[] { task_idx });
      }
      }

    }

     if(spec.getConditionCount() > 0){
    spec.appendAnd();
     }
  spec.appendWhere(new SearchCondition(JELProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")), new int[]{project_idx});

  if(spec.getConditionCount() > 0) {
    spec.appendAnd();
  }
  spec.appendWhere(new SearchCondition(JELProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),new int[] {project_idx});

    if(searchState != null && searchState.length() > 0){
      if(spec.getConditionCount() > 0)
        spec.appendAnd();
      spec.appendWhere(new SearchCondition(JELTask.class, "taskState", SearchCondition.EQUAL, Integer.parseInt(searchState) ),new int[] {task_idx});

    }

  if(spec.getConditionCount() > 0)
     spec.appendAnd();

    spec.appendOpenParen();
  SearchCondition where11 = new SearchCondition(JELProject.class, "state.state", SearchCondition.EQUAL, "INWORK");
  spec.appendWhere(where11, new int[]{project_idx});
  spec.appendOr();
  SearchCondition where22 = new SearchCondition(JELProject.class, "state.state", SearchCondition.EQUAL, "PROGRESS");
  spec.appendWhere(where22, new int[]{project_idx});
  spec.appendOr();
  SearchCondition where33 = new SearchCondition(JELProject.class, "state.state", SearchCondition.EQUAL, "REWORK");
  spec.appendWhere(where33, new int[]{project_idx});
  spec.appendOr();
  SearchCondition where44 = new SearchCondition(JELProject.class, "state.state", SearchCondition.EQUAL, "PLANWORK");
  spec.appendWhere(where44, new int[]{project_idx});
  spec.appendCloseParen();


  if(searchPjtName != null && searchPjtName.length() > 0){
    if(spec.getConditionCount() > 0)
      spec.appendAnd();
      spec.appendWhere(new SearchCondition(JELProject.class, JELProject.PJT_NAME, SearchCondition.LIKE, "%"+searchPjtName.toUpperCase()+"%"),new int[] {project_idx});
    //StringSearch stringsearch2 = new StringSearch("pjtName");
    //stringsearch2.setValue("%"+searchPjtName.trim()+"%");
    //spec.appendWhere(stringsearch2.getSearchCondition(JELProject.class),new int[]{project_idx});
  }

    if(searchTaskName != null && searchTaskName.length() > 0){
      if(spec.getConditionCount() > 0)
      spec.appendAnd();
     // spec.appendWhere(new SearchCondition(JELTask.class, JELTask.TASK_NAME, SearchCondition.LIKE,  "%"+searchTaskName.trim()+"%"),new int[] {task_idx});
      StringSearch stringsearch = new StringSearch("taskName");
    stringsearch.setValue("%"+searchTaskName.trim()+"%");
    spec.appendWhere(stringsearch.getSearchCondition(JELTask.class),new int[]{task_idx});
    }



    if(spec.getConditionCount() > 0)
    spec.appendAnd();
  if(!searchMonth.equals("")){

    spec.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE,">=",DateUtil.convertStartDate(searchYear+"/"+searchMonth+"/01") ),new int[] {schedIndex2});
    spec.appendAnd();
    spec.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE,"<",DateUtil.convertEndDate(searchYear+"/"+searchMonth+"/31") ),new int[] {schedIndex2});
  }else{

    spec.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE,">=",DateUtil.convertStartDate(searchYear+"/01/01") ),new int[] {schedIndex2});
    spec.appendAnd();
    spec.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE,"<",DateUtil.convertEndDate(searchYear+"/12/31") ),new int[] {schedIndex2});
  }


  SearchUtil.setOrderBy(spec, JELProjectMaster.class, master_idx, "pjtName", "pjtName", false);
  SearchUtil.setOrderBy(spec, TaskMemberLink.class, memberlink_idx, "actor.key.id", "idA3A6", false);
  SearchUtil.setOrderBy(spec, ExtendScheduleData.class, schedIndex2, "planStartDate", "planStartDate", false);


  //out.print(spec);

  PageQueryBroker broker = new PageQueryBroker(request, spec);
  broker.setPsize(15);
  QueryResult qrr = broker.search();





%>
<%@page import="e3ps.common.web.PageQueryBroker"%>
<%@page import="e3ps.project.TaskMemberLink"%>
<%@page import="e3ps.project.beans.TaskUserHelper"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="e3ps.project.JELTask"%>
<%@page import="e3ps.project.ProjectOutput"%>
<%@page import="e3ps.project.JELProject"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="e3ps.project.beans.ProjectUserHelper"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="wt.query.CompositeQuerySpec"%>
<%@page import="wt.introspection.ClassInfo"%>
<%@page import="wt.introspection.WTIntrospector"%>
<%@page import="wt.introspection.DatabaseInfo"%>
<%@page import="wt.introspection.BaseTableInfo"%>
<%@page import="wt.query.KeywordExpression"%>
<%@page import="e3ps.project.beans.JELTaskData"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.project.beans.SearchPagingProjectHelper"%>
<%@page import="wt.util.WTAttributeNameIfc"%>
<%@page import="e3ps.project.TemplateTask"%>
<%@page import="wt.query.SetOperator"%>
<%@page import="wt.query.StringSearch"%>
<%@page import="wt.query.CompoundQuerySpec"%>
<%@page import="wt.query.SubSelectExpression"%>
<%@page import="e3ps.common.web.QueryBroker"%>
<%@page import="wt.query.OrderBy"%>
<%@page import="e3ps.project.ExtendScheduleData"%>
<%@page import="e3ps.project.beans.ProjectOutputHelper"%>
<%@page import="java.util.Locale"%>
<%@page import="e3ps.project.beans.ProjectStateFlag"%>
<%@page import="wt.pds.StatementSpec"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="e3ps.project.ProjectIssue"%>
<%@page import="e3ps.groupware.company.DepartmentHelper"%>
<%@page import="e3ps.groupware.company.Department"%>

<%@page import="java.util.Collection"%>
<%@page import="e3ps.common.query.SearchUtil"%>
<%@page import="e3ps.project.JELProjectMaster"%>
<title><%=messageService.getString("e3ps.message.ket_message", "01559") %><%--부서별 현황--%></title>
<script language="JavaScript">
  function projectView(oid){
    openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", 'projectView',1150,800);
  }
  function clearUser(str){
    var cUser = document.getElementById(str);
    var cTempUser = document.getElementById("temp"+str);
    cUser.value="";
    cTempUser.value="";
  }

  function addOneDepartment() {
    var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=m";
    attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=400px; dialogHeight:465px; center:yes");
    if(typeof attacheDept == "undefined" || attacheDept == null) {
      return;
    }

    var param = "command=deptInfoDept&deptOid=" + attacheDept[0][0];
    var url = "/plm/jsp/project/ProjectAjaxAction.jsp?" + param;
    callServer(url, acceptDeptOne);
  }

  function acceptDeptOne(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg != 'true') {
      alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
      return;
    }

    showElements = xmlDoc.selectNodes("//data_info");
    var l_oid = showElements[0].getElementsByTagName("l_oid");
    var l_name = showElements[0].getElementsByTagName("l_name");

    for(var i = 0; i < l_oid.length; i++) {
      loid = decodeURIComponent(l_oid[i].text);
      lname = decodeURIComponent(l_name[i].text);
      document.chartTask.setDept.value = loid;
      document.chartTask.tempsetDept.value = lname;
      document.chartTask.tempsetUser.value = "";
      document.chartTask.setUser.value = "";
    }

  }

  function selectUser(target){
    var url="/plm/jsp/groupware/company/selectPeopleFrm.jsp?mode=o&function=addUserToTarget&target="+target;
    openOtherName(url,"process","700","410","status=1,scrollbars=no,resizable=yes");
  }
  function viewProjectIssue(oid) {
    var url = "/plm/jsp/project/chart/ProjectIssueChart2.jsp?oid="+oid;
    getOpenWindow(url, "viewProjectIssueDetailPopUp", "1100", "700");
  }

  function viewPeople(oid) {
    var url = "/plm/e3pscore/org/selectPeopleView.jsp?viewtype=open&oid="+oid;
    openSameName(url,"560","400","status=no,scrollbars=no,resizable=no");
  }

  function search() {
    document.chartTask.action = "/plm/jsp/project/chart/ProgramDepartmentChart2.jsp";
    document.chartTask.method = "post";
    document.chartTask.cmd.value = "search";
    document.chartTask.sessionid.value ="0";
    document.chartTask.tpage.value = "";
    document.chartTask.submit();

  }
  function viewTask(oid){
    var url = "/plm/jsp/project/TaskView.jsp?oid="+oid;
    openOtherName(url,"","800","600","status=1,scrollbars=yes,resizable=1");
  }

  function onKeyPress() {
    if (window.event) {
      if (window.event.keyCode == 13) search();
    } else return;
  }
  document.onkeypress = onKeyPress;

</script>

<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/script.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/org.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/select.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/table.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/viewObject.js"></SCRIPT>
<SCRIPT language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/checkbox2.js"></SCRIPT>
</head>
<style type="text/css">
body {
  background-image: url(/plm/portal/images/img_default/background2.gif);
  background-repeat:repeat-x;
  background-color: #ffffff;
  margin-top: 24px;
  margin-left:15px;
}
</style>

<form name=chartTask >
<input type=hidden name=cmd >

<%@include file="/jsp/project/template/ajaxProgress.html"%>
<table height="37" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td valign="top" style="padding:0px 0px 0px 0px">

      <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
        <tr>
        <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01559") %><%--부서별 현황--%></td>
                <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03211") %><%--현황관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "01559") %><%--부서별 현황--%></td>
        <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td valign="top" style="padding-left:12">


      <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor=#4B73E1 align=center>
        <tr><td height=1 width=100%></td></tr>
       </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="tdblueM">
            <table border="0" cellpadding="0" cellspacing="0">
              <COL width="8%"><COL width="22%"><COL width="8%"><COL width="20%">
              <COL width="8%"><COL width="26%"><COL width="8%">

              <tr>
                <td align="right"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%> :</td>
                <td>
                  <input TYPE="hidden" name="setDept" value="<%=(depart == null)?"":CommonUtil.getOIDString(depart)%>" >
                  <input style="background-color:#EEE8AA;" name="tempsetDept" value="<%=(depart == null)?"":depart.getName()%>" size=15 readOnly>
                                    <input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%>' onClick="javascript:addOneDepartment();" class="s_font">
                </td>
                <td align="right"><%=messageService.getString("e3ps.message.ket_message", "01673") %><%--사용자--%> :</td>
                <td>
                  <input TYPE="hidden" name="setUser" value="<%=(wtuser==null)?"":CommonUtil.getOIDString(wtuser) %>">
                  <input style="background-color:#EEE8AA;" name="tempsetUser" value="<%=(wtuser==null)?"":wtuser.getFullName() %>" class="txt_field" size="12" engnum="engnum"  readonly onclick="javascript:selectUser('setUser');"/>
                  <input type="button" class="p_select" onclick="javascript:selectUser('setUser');"/>
                  <a href="JavaScript:clearUser('setUser')"><img src="/plm/portal/images/img_common/x.gif" border=0></a>&nbsp;
                </td>
                <td align="right"><%=messageService.getString("e3ps.message.ket_message", "01176") %><%--년도--%> :</td>
                <td>
                  <select name="tmpYear" size="1" style="width:70">
                    <%

                    //out.println(searchYear);
                    String currentYear = "";
                    for(double i = -10; i < 11; i++) {
                      currentYear = "" + (Integer.parseInt(searchYear) + i);

                      currentYear = currentYear.substring(0, 4);
                    %>
                    <option value='<%=currentYear %>' <%=currentYear.equals(searchYear)?"selected":""%>><%=currentYear %></option>
                    <%
                    }
                    %>
                  </select>
                  <select name="tmpMonth" size="1" style="width:70">
                                        <option value=''>[<%=messageService.getString("e3ps.message.ket_message", "02224") %><%--월--%>]</option>
                    <%
                    for(int i = 1; i < 13; i++) {
                    %>
                    <option value='<%=i %>' <%= searchMonth!=null && searchMonth.equals(String.valueOf(i))?"selected":"" %>><%=i %></option>
                    <% } %>
                  </select>
                </td>
                <td>
                </td>
              </tr>
              <tr>
                                <td align="right"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%> :</td>
                <td><input style="background-color:#EEE8AA;" name="searchPjtName" size=15 value="<%=searchPjtName%>"></td >

                                <td align="right"><%=messageService.getString("e3ps.message.ket_message", "02923") %><%--태스크 명--%> :</td>
                <td><input style="background-color:#EEE8AA;" name="searchTaskName" size=15 value="<%=searchTaskName%>"></td>
                <td align="right"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%> :</td>
                <td>
                  <select name="searchState" size="1" style="width:70" onChange="javascript:search();">
                    <option style="background-color:#EEE8AA;" value='' ><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                                        <option style="background-color:#EEE8AA;" value='0' <%if(searchState.equals("0")){out.print("selected");}%>><%=messageService.getString("e3ps.message.ket_message", "02735") %><%--진행중--%></option>
                                        <option style="background-color:#EEE8AA;" value='5' <%if(searchState.equals("5")){out.print("selected");}%>><%=messageService.getString("e3ps.message.ket_message", "02173") %><%--완료됨--%></option>
                  </select>
                </td>
                <td align="right">
                  <a href="javascript:search();">
                  <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
                  </a>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td align="right" colspan=7>
          <%=messageService.getString("e3ps.message.ket_message", "01558") %><%--부서별 업무현황--%> (<FONT color='74C600'><%=messageService.getString("e3ps.message.ket_message", "01180") %><%--녹색:완료--%></FONT>,<FONT color='0033CC'><%=messageService.getString("e3ps.message.ket_message", "03355") %><%--파랑:진행--%></FONT>,<FONT color='black'><%=messageService.getString("e3ps.message.ket_message", "00715") %><%--검정:미시작--%></FONT>,<FONT color='FF3300'><%=messageService.getString("e3ps.message.ket_message", "01654") %><%--빨강:지연--%></FONT>)
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space10"> </td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor=#4B73E1 align=center>
        <tr><td height=1 width=100%></td></tr>
       </table>

      <table border="0" cellspacing="0" cellpadding="0" width="100%" >
        <tr height=30>

          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01366") %><%--모델(차종)--%> </td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03046") %><%--프로젝트--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02923") %><%--태스크 명--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02773") %><%--책임자--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02064") %><%--실제시작일--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02067") %><%--실제종료일--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02725") %><%--진척율--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02296") %><%--이슈--%></td>
        </tr>
    <%
    String objOid = "";
    if(qrr != null){
      while ( qrr.hasMoreElements() ) {
    Object[] o = (Object[])qrr.nextElement();
    objOid = (String)o[0];
    Object obj = CommonUtil.getObject(objOid);

    WTUser outputUser = null;
    JELTask et = null;
    JELTaskData taskData = null;
    ProjectOutput po = null;
    TaskMemberLink taskLink = null;
    if(obj instanceof JELTask){
      et = (JELTask)obj;
      taskData = new JELTaskData(et);
    }

    QueryResult masterList = TaskUserHelper.manager.getMaster(et);
    String taskMasterUserStr = "";
    if(masterList.hasMoreElements()){
      Object object[]=(Object[])masterList.nextElement();
      TaskMemberLink link = (TaskMemberLink)object[0];
      PeopleData data = new PeopleData(link.getMember().getMember());
      String wtuserOID = CommonUtil.getOIDString(data.wtuser);
      String peopleOID = CommonUtil.getOIDString(data.people);
      taskMasterUserStr = data.name;
    }

    JELProject pjt = (JELProject)et.getProject();
    String taskCheckColor = getTaskStateFont(et);
    String styleColor = "style=color:"+taskCheckColor;

    %>
        <tr height=30>
          <td class="tdwhiteM">&nbsp;<%=(pjt.getModel()==null)?"":StringUtil.checkNull(pjt.getModel().getName())%></td>
          <td class="tdwhiteL" title="<%=pjt.getPjtName() %>">&nbsp;
          <div style="width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
          <a href="#" onClick="Javascript:projectView('<%=CommonUtil.getOIDString(pjt)%>')">
          <nobr><%=pjt.getPjtName() %></nobr>
          </a>
          </div>
          </td>
          <td class="tdwhiteM">&nbsp;<%
          if(pjt.getPjtType()==0){
            out.println(messageService.getString("e3ps.message.ket_message", "01832")/*선행개발*/);
          }else if(pjt.getPjtType()==1){
            out.println(messageService.getString("e3ps.message.ket_message", "02083")/*양산개발*/);
          }else if(pjt.getPjtType()==2){
            out.println(messageService.getString("e3ps.message.ket_message", "00746")/*견적*/);
          }
          %></td>

          <td class="tdwhiteM" <%=styleColor%> >&nbsp;
            <a href="javascript:viewTask('<%=PersistenceHelper.getObjectIdentifier( et ).getStringValue()%>');">
            <%=(et==null)?"":et.getTaskName()%></a>
          </td>
          <td class="tdwhiteM" >&nbsp;<%=taskMasterUserStr%></td>
          <td class="tdwhiteM" <%=styleColor%> >&nbsp;<%=(taskData.taskPlanStartDate==null)?"":DateUtil.getDateString(taskData.taskPlanStartDate, "D")%></td>
          <td class="tdwhiteM" <%=styleColor%> >&nbsp;<%=(taskData.taskPlanEndDate==null)?"":DateUtil.getDateString(taskData.taskPlanEndDate, "D")%></td>
          <td class="tdwhiteM" <%=styleColor%> >&nbsp;<%=(taskData.taskExecStartDate==null)?"":DateUtil.getDateString(taskData.taskExecStartDate, "D")%></td>
          <td class="tdwhiteM" <%=styleColor%> >&nbsp;<%=(taskData.taskExecEndDate==null)?"":DateUtil.getDateString(taskData.taskExecEndDate, "D")%></td>
          <td class="tdwhiteM" <%=styleColor%> >&nbsp;<%=taskData.taskCompletion%> </td>
          <td class="tdwhiteM" <%=styleColor%> >&nbsp;

          <%
          long oidValue2 = CommonUtil.getOIDLongValue(et);
          QuerySpec qs_task = null;
          QueryResult qr_task  = null;
          try{
            qs_task = new QuerySpec();
                Class mainClass = ProjectIssue.class;
                int mainClassPos = qs_task.addClassList(mainClass,true);

                qs_task.appendWhere(new SearchCondition(mainClass, "taskReference.key.id", SearchCondition.EQUAL, oidValue2), mainClassPos);

                qr_task =  PersistenceHelper.manager.find(qs_task);

          }catch(Exception e){e.printStackTrace();}
              int issueCount = 0;
              issueCount = qr_task.size();

          %>
              <%if(pjt.getPjtType()!=2){ %>
                <a href="javascript:viewProjectIssue('<%=CommonUtil.getOIDString(pjt)%>')">
                <B><%=issueCount%></B>
                </a>
              <%} %>
          </td>

        </tr>
  <%
    }
    }
  %>
  <%if(qrr == null ||qrr.size() == 0) {%>
        <tr>
          <td colspan="11" class=tdwhiteM0><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></td>
        </tr>
  <%} %>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
      <tr height=35>
      <td>

      <%=broker.getHtml("chartTask")%>

      </td></tr>
      </table>


      </td>
      </tr>
      </table>
    </td>
  </tr>
</table>
</form>
<%!
  private String getTaskStateFont(JELTask task)throws Exception {

    //JELTaskData jelTaskData = new JELTaskData(task);

    if (task.getTaskState()==ProjectStateFlag.TASK_STATE_COMPLETE){
      return "74C600";
    }


    if(JELTaskData.getPreferComp(task) < 15){

      return "0033CC";
    }



    if ( JELTaskData.getDifferDateGap(task) >= 0 ){
      return "0033CC";//blue
    }
    else{
      return "FF3300";//red
    }
  }


%>
