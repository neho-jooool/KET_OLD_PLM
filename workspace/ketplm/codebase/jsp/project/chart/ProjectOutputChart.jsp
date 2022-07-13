<%@page contentType="text/html; charset=UTF-8"%>
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

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
<%@page import="java.util.Locale"%>

<html>
<%
  HashMap map = new HashMap();

  String command = request.getParameter("command");
  if(command == null){
    command = "search";
  }
  String pjtType = request.getParameter("pjtType");

  if(pjtType == null){
    pjtType = "0";
  }
  if (command.length() > 0) {
    map.put("command", command);
  }
  if (pjtType.length() > 0) {
    map.put("pjtType", pjtType);
  }


  int psize = 15;
  int cpage = 1;
  int total = 0;
  int pageCount = 10;

  //page
  String sessionidstring = request.getParameter("sessionid");
  if (sessionidstring == null)
    sessionidstring = "0";
  long sessionid = Long.parseLong(sessionidstring);
  String pagestring = request.getParameter("tpage");
  if (pagestring != null && pagestring.length() > 0) {
    cpage = Integer.parseInt(pagestring);
  }
  //out.println(pjtType +" : "+command +" : "+ map.get("pjtType"));


  PagingQueryResult result = null;
  if (sessionid <= 0) {
    result = SearchPagingProjectHelper.openPagingSession(map, 0, psize);
  } else {
    result = PagingSessionHelper.fetchPagingSession((cpage - 1)  * psize, psize, sessionid);
  }

  if (result != null) {
    total = result.getTotalSize();
    sessionid = result.getSessionId();
  }


%>

<head>
<%@include file="/jsp/common/top_include.jsp" %>
<title><%=messageService.getString("e3ps.message.ket_message", "03210") %><%--현황--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script language="javascript" src="/plm/portal/js/script.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/viewObject.js"></SCRIPT>


<style type="text/css">
body {
  background-image: url(/plm/portal/images/img_default/background2.gif);
  background-repeat:repeat-x;
  background-color: #ffffff;
  margin-top: 24px;
  margin-left:15px;
  margin-right:10px;

  overflow-x:hidden;
  overflow-y:auto;
  scrollbar-highlight-color:#f4f6fb;
  scrollbar-3dlight-color:#c7d0e6;
  scrollbar-face-color:#f4f6fb;
  scrollbar-shadow-color:#f4f6fb;
  scrollbar-darkshadow-color:#c7d0e6;
  scrollbar-track-color:#f4f6fb;
  scrollbar-arrow-color:#607ddb;
}
</style>
<script>

    function gotoPage(p) {
      document.forms[0].sessionid.value='<%=sessionid%>';
      document.forms[0].tpage.value=p;
      document.forms[0].submit();
    }

    function search() {
      document.forms[0].sessionid.value ="0";
      //document.forms[0].tpage.value = "";

      document.forms[0].method = "post";
      document.forms[0].action = "/plm/jsp/project/chart/ProjectOutputChart.jsp";
      document.forms[0].submit();
    }
    function searchExcel() {
      //document.forms[0].command.value='search';
      document.forms[0].sessionid.value ="0";
      document.forms[0].tpage.value = "";

      document.forms[0].method = "post";
      document.forms[0].action = "/plm/jsp/project/chart/ProjectOutputChartExcel.jsp";
      document.forms[0].submit();
    }

</script>
<body>
<%@include file="/jsp/common/processing.html"%>
<form>
<!-- Hidden Value -->

<input type='hidden' name="command"  value="search">
<input type='hidden' name='sessionid'>
<input type='hidden' name='tpage'>
<!-- //Hidden Value -->


  <table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
      <td valign="top" style="padding:0px 0px 0px 0px">

        <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
          <tr>
          <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
          <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03075") %><%--프로젝트 산출물 현황--%></td>
          <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03211") %><%--현황관리--%>  &gt; <%=messageService.getString("e3ps.message.ket_message", "03075") %><%--프로젝트 산출물 현황--%></td>
          <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
          </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr>
            <td>&nbsp;</td>
            <td align="right">
              <a class="a52Btn" href="#" onclick="javascript:searchExcel();">
              <img src="/plm/portal/images/img_default/button/board_btn_excel.gif" alt="Excel" width="62" height="20" border="0">
              </a>
              &nbsp;
              <a href="javascript:search();">
              <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
              </a>

            </td>
          </tr>
        </table>

        <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr>
            <td class="space5"> </td>
          </tr>
        </table>


        <table  border="0" cellspacing="0" cellpadding="0" width="100%">
            <%=messageService.getString("e3ps.message.ket_message", "03060") %><%--프로젝트 구분--%> :
            <select name="pjtType" style="width:100" >
              <OPTION VALUE=""><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></OPTION>
              <OPTION VALUE="0" <%if(pjtType.equals("0")) {%>selected<%}%>><%=messageService.getString("e3ps.message.ket_message", "01832") %><%--선행개발--%></OPTION>
              <OPTION VALUE="1" <%if(pjtType.equals("1")) {%>selected<%}%>><%=messageService.getString("e3ps.message.ket_message", "02083") %><%--양산개발--%></OPTION>
              <OPTION VALUE="2" <%if(pjtType.equals("2")) {%>selected<%}%>><%=messageService.getString("e3ps.message.ket_message", "01962") %><%--수주/견적--%></OPTION>
              <OPTION VALUE="3" <%if(pjtType.equals("3")) {%>selected<%}%>>델텍</OPTION>
            </select>
        </table>

        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td class="tab_btm2"></td>
          </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td class="tab_btm1"></td>
          </tr>
        </table>

        <!-- NEW Start -->
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <COL width="10%"><COL width="43%"><COL width="10%"><COL width="10%">
          <COL width="10%"><COL width="10%"><COL width="7%">

          <tr>
            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01366") %><%--모델(차종)--%></td>
            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00398") %><%--PROJECT NO(PROJECT 명)--%></td>
            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02731") %><%--진행상태--%></td>
            <td class="tdblueM">PM</td>
            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00819") %><%--계획{0}시작일자--%></td>
            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00827") %><%--계획{0}종료일자--%></td>
            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01730") %><%--산출물 전체/등록/미등록--%></td>
          </tr>
          <%
            int listCount = result.getTotalSize();
            int count = result.getTotalSize();
            int sortcount = 0;
            int countAll = count;
            int AllOutput = 0;
            int AllInput = 0;
            int AllNotInput = 0;

            if(cpage > 1)
              sortcount = (15 * cpage) - 15;

            if(cpage > 1 &&  (countAll-sortcount) > 0)
              countAll = count - sortcount;
            boolean checkOut = false;
            while (result.hasMoreElements()) {
              Object[] obj = (Object[]) result.nextElement();
              JELProject project = (JELProject)obj[0];

              /*
              QuerySpec spec = new QuerySpec();
                  Class projectOutputClass = ProjectOutput.class;
                  Class projectClass = JELProject.class;
                  int projectOutputClassPos = spec.addClassList(projectOutputClass, true);
                  int projectClassPos = spec.addClassList(projectClass, false);

                    SearchCondition sc=new SearchCondition(new ClassAttribute(projectOutputClass,"projectReference.key.id"),"=",
                        new ClassAttribute(projectClass,"thePersistInfo.theObjectIdentifier.id"));
                sc.setFromIndicies(new int[]{projectOutputClassPos,projectClassPos},0);
                sc.setOuterJoin(0);
                spec.appendWhere(sc,projectOutputClassPos,projectClassPos);

                long oidValue;
                  oidValue = CommonUtil.getOIDLongValue(project);
                    if(spec.getConditionCount() > 0)
                      spec.appendAnd();
                    spec.appendWhere(new SearchCondition(projectOutputClass,"projectReference.key.id",SearchCondition.EQUAL,oidValue),projectOutputClassPos);


                    QueryResult qs = PersistenceHelper.manager.find(spec);
              int outputCount = qs.size() ;
              */

              int total_count = ProjectOutputHelper.getOutputCount(project, false);
              int input_count = ProjectOutputHelper.getOutputCount(project, true);
              int not_input = total_count - input_count;
              AllOutput = AllOutput + total_count;
              AllInput = AllInput +input_count;
              AllNotInput = AllNotInput + not_input;

          %>
          <tr>

            <td class="tdwhiteL">&nbsp;<%=(project.getModel()==null)?"":project.getModel().getName()%></td>
            <td class="tdwhiteL">&nbsp;
            <font color="#0000FF">
            <a href="javascript:popup('/plm/jsp/project/ProjectOnlyView.jsp?oid=<%=CommonUtil.getOIDString(project)%>&popup=popup',858, 700);"><font color="#0000FF"><B><%=project.getPjtNo()%> (<%=project.getPjtName()%>)</B></font></a>
            </font>

            </td>
            <td class="tdwhiteL">
              <%=project.getLifeCycleState().getDisplay(messageService.getLocale())%>
            </td>
            <td class="tdwhiteL">&nbsp;<%=StringUtil.checkNull(ProjectUserHelper.manager.getPM(project).getFullName())%></td>
            <%
            ExtendScheduleData schedule= (ExtendScheduleData)project.getPjtSchedule().getObject();

            %>
            <td class="tdwhiteL">&nbsp;<%=DateUtil.getDateString(schedule.getPlanStartDate(), "d")%></td>
            <td class="tdwhiteL">&nbsp;<%=DateUtil.getDateString(schedule.getPlanEndDate(), "d")%></td>
            <td class="tdwhiteL">&nbsp;
              <B><%=total_count%>/<%=input_count%>/<%=not_input%></B>
            </td>

          </tr>
          <% } %>
          <%  if (result.getTotalSize() == 0) {  %>
          <tr>
            <td class='tdwhiteM0' align='center' colspan='8'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td>
          </tr>
          <%  }  %>
        </table>

        <!-- 리스트 시작 //-->
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
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr>
            <td class="small"><span class="small">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%> :<%=ksize%>)</span></td>
            <td>
              <table border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="40" align="center">
                    <%  if (start > 1) {  %>
                    <a href="JavaScript:gotoPage(1)" class="small"><%=messageService.getString("e3ps.message.ket_message", "02792") %><%--처음--%></a>
                    <%  }  %>
                  </td>
                  <td width="1" bgcolor="#dddddd"></td>
                  <%  if (start > 1) {  %>
                  <td width="60" class="quick" align="center"><a
                    href="JavaScript:gotoPage(<%=start-1%>)" class="smallblue"><%=messageService.getString("e3ps.message.ket_message", "02322") %><%--이전--%></a>
                  </td>
                  <td width="1" bgcolor="#dddddd"></td>
                  <%  }  %>
                  <%  for (int i = start; i <= end; i++) {  %>
                  <td style="padding:2 8 0 7;cursor:hand"  onMouseOver="this.style.background='#ECECEC'" OnMouseOut="this.style.background=''" class="nav_on">
                    <%  if (i == cpage) {  %>
                      <b><%=i%>
                    <%  } else {  %>
                      <a href="JavaScript:gotoPage(<%=i%>)"><%=i%></a>
                    <%  }  %>
                  </td>
                  <%  }  %>
                  <%  if (end < ksize) {  %>
                  <td width="1" bgcolor="#dddddd"></td>
                  <td width="60" align="center">
                    <a href="JavaScript:gotoPage(<%=end+1%>)" class="smallblue"><%=messageService.getString("e3ps.message.ket_message", "01186") %><%--다음--%></a>
                  </td>
                  <%  }  %>
                  <td width="1" bgcolor="#dddddd"></td>
                  <td width="45" align="center">
                    <%  if (end < ksize) {  %>
                    <a href="JavaScript:gotoPage(<%=ksize%>)" class="small"><%=messageService.getString("e3ps.message.ket_message", "01354") %><%--마지막--%></a>
                    <%  }  %>
                  </td>
                </tr>
              </table>
            </td>
            <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>:<%=total%>)</td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</form>
</body>
</html>
