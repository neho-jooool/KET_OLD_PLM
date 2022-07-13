<%@ page language="java" contentType="application/vnd.ms-excel; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="wt.fc.*,wt.lifecycle.*,
        wt.org.*,
        wt.query.*,
        wt.session.*,
        wt.util.*"%>
<%@page import="e3ps.project.*,
        e3ps.project.beans.*,
        e3ps.common.util.*,
        e3ps.common.code.*,
        e3ps.common.jdf.config.*,
        e3ps.common.code.GenNumberCode,
        e3ps.common.web.HtmlTagUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="e3ps.common.web.ParamUtil"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Locale"%>
<%@page import="e3ps.common.query.SearchUtil"%>
<%@page import="e3ps.common.web.QueryBroker"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<html>
<%
  QuerySpec mainSpec = new QuerySpec();
  Class main_target = JELProject.class;
  int main_idx = mainSpec.addClassList(main_target, true);

  String pjtType = request.getParameter("pjtType");
  if(pjtType == null){
    pjtType = "";
  }
  /////////////////////////////////////////////////////////////////////
  // Sub Query Start mainSpec
  /////////////////////////////////////////////////////////////////////

  if(!mainSpec.isAdvancedQueryEnabled()) {
    mainSpec.setAdvancedQueryEnabled(true);
  }

  QuerySpec spec = null;
  spec = new QuerySpec();

  if(!spec.isAdvancedQueryEnabled()) {
    spec.setAdvancedQueryEnabled(true);
  }

  Class target = JELProject.class;
  int idx_target = spec.addClassList(target, false);

  spec.setDistinct(true);

  ClassAttribute ca = new ClassAttribute(JELProject.class, "thePersistInfo.theObjectIdentifier.id");
  ca.setColumnAlias("projectId");
  spec.appendSelect(ca, true);

    if(StringUtil.checkString(pjtType)) {
    if(spec.getConditionCount() > 0) spec.appendAnd();
    spec.appendWhere(new SearchCondition(JELProject.class, JELProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt(pjtType)), new int[]{idx_target});
  }

  if(spec.getConditionCount() > 0) {
    spec.appendAnd();
  }
  spec.appendWhere(new SearchCondition(target, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),new int[] { idx_target});
  if(spec.getConditionCount() > 0){
    spec.appendAnd();
  }
  spec.appendWhere(new SearchCondition(target, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),new int[] {idx_target});

  /////////////////////////////////////////////////////////////////////
  // Sub Query End mainSpec
  /////////////////////////////////////////////////////////////////////

  SubSelectExpression subfrom = new SubSelectExpression(spec);
    subfrom.setFromAlias(new String[]{"B0"}, 0);
    int sub_pjt_index = mainSpec.appendFrom(subfrom);

    if(mainSpec.getConditionCount() > 0) mainSpec.appendAnd();

    SearchCondition sc = new SearchCondition(new ClassAttribute(JELProject.class, "thePersistInfo.theObjectIdentifier.id"),
                "=",
                new KeywordExpression(mainSpec.getFromClause().getAliasAt(sub_pjt_index) + ".projectId"));
    sc.setFromIndicies(new int[]{main_idx,sub_pjt_index},0);
    sc.setOuterJoin(0);
    mainSpec.appendWhere(sc, new int[]{main_idx,sub_pjt_index});

    int sortIdx = 0;
  SearchUtil.setOrderBy(mainSpec, target, main_idx, "thePersistInfo.modifyStamp", "modifyStamp", true);

  QueryBroker broker = null;
  QueryResult result = null;
  try{
    result = broker.search(mainSpec, true);
  }catch(Exception e) {
      Kogger.error(e);
  }

%>

<head>
<title>현황</title>


<body>

<form>
  <table cellpadding="0" cellspacing="0" border="1" bgcolor="#D6DBE7" width="1500" >
    <tr align=center>
            <td colspan='7'><font size='24pt'><b><%=messageService.getString("e3ps.message.ket_message", "03075") %><%--프로젝트 산출물 현황--%></b></font></td>
    </tr>
  </table>
  <table border="0" cellspacing="0" cellpadding="0" >
    <tr>
      <td class="space20"> </td>
    </tr>
  </table>
  <table border="0" cellspacing="0" cellpadding="0" >
    <tr>
      <td valign="top" style="padding:0px 0px 0px 0px">

        <!-- NEW Start -->
        <table border="1" cellspacing="1" cellpadding="1" >

          <tr bgcolor="#D6DBE7">
            <td width="200"><%=messageService.getString("e3ps.message.ket_message", "01366") %><%--모델(차종)--%></td>
            <td width="600"><%=messageService.getString("e3ps.message.ket_message", "00398") %><%--PROJECT NO(PROJECT 명)--%></td>
                        <td width="100"><%=messageService.getString("e3ps.message.ket_message", "02731") %><%--진행상태--%></td>
            <td width="100">PM</td>
            <td width="130"><%=messageService.getString("e3ps.message.ket_message", "00819") %><%--계획{0}시작일자--%></td>
            <td width="130"><%=messageService.getString("e3ps.message.ket_message", "00827") %><%--계획{0}종료일자--%></td>
            <td width="140"><%=messageService.getString("e3ps.message.ket_message", "01730") %><%--산출물 전체/등록/미등록--%></td>
          </tr>
          <%
            boolean checkOut = false;
            int AllOutput = 0;
            int AllInput = 0;
            int AllNotInput = 0;
            while (result.hasMoreElements()) {
              Object[] obj = (Object[]) result.nextElement();
              JELProject project = (JELProject)obj[0];

              int total_count = 0;
              total_count = ProjectOutputHelper.getOutputCount(project, false);
              int input_count = 0;
              input_count = ProjectOutputHelper.getOutputCount(project, true);
              int not_input = 0;
              not_input =  total_count - input_count;

              AllOutput = AllOutput + total_count;
              AllInput = AllInput +input_count;
              AllNotInput = AllNotInput + not_input;

          %>
          <tr>

            <td ><%=(project.getModel()==null)?"":project.getModel().getName()%></td>
            <td ><font color="#0000FF">
              <B><%=project.getPjtNo()%> (<%=project.getPjtName()%>)</B>
            </font>
            </td>
            <td >
              <%=project.getLifeCycleState().getDisplay(messageService.getLocale())%>
            </td>
            <td ><%=StringUtil.checkNull(ProjectUserHelper.manager.getPM(project).getFullName())%></td>
            <%
              ExtendScheduleData schedule= (ExtendScheduleData)project.getPjtSchedule().getObject();
            %>
            <td ><%=DateUtil.getDateString(schedule.getPlanStartDate(), "d")%></td>
            <td ><%=DateUtil.getDateString(schedule.getPlanEndDate(), "d")%></td>
            <td >[<%=""+total_count%>]/[<%=""+input_count%>]/[<%=""+not_input%>]</td>

          </tr>
          <% } %>

        </table>
                <%=messageService.getString("e3ps.message.ket_message", "02491") %><%--전체 산출물 수--%> : <%=""+AllOutput%> <br>
                <%=messageService.getString("e3ps.message.ket_message", "02489") %><%--전체 등록 수--%> : <%=""+AllInput%> <br>
                <%=messageService.getString("e3ps.message.ket_message", "02490") %><%--전체 미등록 수--%> : <%=""+AllNotInput%> <br>


      </td>
    </tr>
  </table>
</form>
</body>
</html>
