<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*,java.util.*,e3ps.common.query.SearchUtil" %>
<%@page import="wt.fc.*,wt.query.*,wt.org.*,wt.session.*" %>
<%@page import ="e3ps.project.*,e3ps.common.util.*,e3ps.common.web.*,e3ps.project.beans.*"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    String oid = request.getParameter("userOid");
    int PAGE = 0;
    long pagingSessionID = 0;
    String category = ParamUtil.getStrParameter(request.getParameter("category"));
//  String projectCode = ParamUtil.getStrParameter(request.getParameter("projectCode"));

    String sessionID_String = request.getParameter("sessionid");
    PagingQueryResult pagingResults = null;
    if(sessionID_String == null)
    {
        QuerySpec query = new QuerySpec();
        Class projectClass = E3PSProject.class;
        Class wtuserClass = WTUser.class;
        Class linkClass = ProjectPeopleLink.class;
        int projectClassPos = query.addClassList(projectClass, true);
        int wtuserClassPos = query.addClassList(wtuserClass, false);
        int linkClassPos = query.addClassList(linkClass, false);

        query.appendJoin(linkClassPos,ProjectPeopleLink.PROJECT_ROLE,projectClassPos);
          query.appendJoin(linkClassPos,ProjectPeopleLink.MEMBER_ROLE,wtuserClassPos);

        query.appendWhere(new SearchCondition(linkClass,ProjectPeopleLink.MEMBER_TYPE,"<>",ProjectUserHelper.CREATOR),linkClassPos);
        query.appendAnd();

//    WTUser wtuser = (WTUser)SessionHelper.manager.getPrincipal();
      long lperistable=CommonUtil.getOIDLongValue(oid);
        query.appendWhere(new SearchCondition(wtuserClass,"thePersistInfo.theObjectIdentifier.id","=",lperistable), new int[]{wtuserClassPos} );

        QueryResult result = PersistenceHelper.manager.find(query);

        if ( category != null && category.length()>0 && !category.equals("0") ) {
            query.appendAnd();
            query.appendSearchCondition(new SearchCondition(projectClass,E3PSProject.PROJECT_STATE,"=",StringUtil.parseInt(category,ProjectStateFlag.PROJECT_STATE_CONTINUE)));
        }

//    if ( projectCode != null && projectCode.length() > 0 ) {
//      query.appendAnd();
//      query.appendSearchCondition(new SearchCondition(projectClass,E3PSProject.PROJECT_CODE,SearchCondition.LIKE,"%"+projectCode+"%"));
//    }

      SearchUtil.setOrderBy( query, projectClass, projectClassPos, "projectState","sort0", false);
//    SearchUtil.setOrderBy( query, projectClass, projectClassPos, "endDate","sort1", true);

        pagingResults = PagingSessionHelper.openPagingSession(0, PageControl.PERPAGE, query);
        PAGE=1;
    } else {
        PAGE = Integer.parseInt(request.getParameter("page"));
        pagingSessionID = Long.parseLong(sessionID_String);
        pagingResults =  PagingSessionHelper.fetchPagingSession((PAGE-1)*PageControl.PERPAGE, PageControl.PERPAGE, pagingSessionID);
    }
    PageControl control = new PageControl(pagingResults,PAGE);
    control.setHref("userProjectList.jsp");
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01161") %><%--나의 프로젝트--%></title>
<%@include file="/jsp/common/top_include.jsp" %>

<script language=JavaScript>
<!--
function search() {
   document.forms[0].action ="userProjectList.jsp";
   document.forms[0].submit();
}

function viewTask(oid) {
    opener.parent.parent.document.location.href = "/plm/jsp/project/projectViewFrm.jsp?oid="+oid;
}
//-->
</script>
</head>
<body bgcolor="white" text="black" link="blue" vlink="purple" alink="red" leftmargin="0" topmargin="0">
<form method=post>
<input type=hidden name=userOid value=<%=oid%>>
<!-----------상단타이틀 및 버튼//-------------------->
<table width="95%" height=40 align=center border=0>
    <tr>
        <td width="296" align="left">
                <table border=0 cellpadding=0 cellspacing=0 >
                <tr>
                    <td><img src=/plm/portal/images/title2_left.gif></td>
                    <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "01161") %><%--나의 프로젝트--%></td>
                    <td><img src=/plm/portal/images/title2_right.gif></td>
                </tr>
                </table>
        </td>
        <!--검색//-->
        <%--<td width="" align=right>
            <table border="0" cellpadding="0" cellspacing="1">
                <tr align=center>
                     <td width="100">Project Code</td>
                    <td width="100"><input name="projectCode" type="text" id=i value="" style="width:100%;"></td>
                    <td></td>
                    <td width="60"><a href="javascript:search();"><img src="<%=iconUrl%>/find_search.gif" width="55" height="21" border="0"></td>
                </tr>
            </table>
        </td>--%>
        <!--검색끝//-->
        <td align=right>
            <TABLE>
                <TR>
                    <TD><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%> :</TD>
                    <TD>
                        <select name="category" onchange="javascript:search()">
                            <option value='0' selected><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></option>
                            <option value='<%=ProjectStateFlag.PROJECT_STATE_CONTINUE%>' <%if((ProjectStateFlag.PROJECT_STATE_CONTINUE+"").equals(category))out.print("selected");%>>진행</option>
                            <option value='<%=ProjectStateFlag.PROJECT_STATE_STOP%>' <%if((ProjectStateFlag.PROJECT_STATE_STOP+"").equals(category))out.print("selected");%>>중지</option>
                            <option value='<%=ProjectStateFlag.PROJECT_STATE_COMPLETE%>' <%if((ProjectStateFlag.PROJECT_STATE_COMPLETE+"").equals(category))out.print("selected");%>>종료</option>
                        </select>&nbsp;&nbsp;&nbsp;
                    </TD>
                    <TD><input type=button value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' id=button onclick='self.close()'></TD>
                </TR>
            </TABLE>
        </td>
    </tr>
</table>
<!--------상단타이틀 끝//--------->



<table border="0" cellpadding="1" cellspacing="1" width="95%" bgcolor="#AABDC6" align="center">
    <tr  bgcolor="#D8E0E7" align=center height=23>
        <td width="30" bgcolor="#D8E0E7" id=title>No</td>
        <td id=title>Project</td>
        <td id=title><%=messageService.getString("e3ps.message.ket_message", "01212") %><%--담당자/부서--%></td>
        <td id=title><%=messageService.getString("e3ps.message.ket_message", "02018") %><%--시작일--%></td>
        <td id=title><%=messageService.getString("e3ps.message.ket_message", "02658") %><%--종료일--%></td>
        <td id=title><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
        <td id=title><%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%></td>
        <td id=title colspan="2"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
    </tr>
<%
    String color = "#ffffff";
    int count = 1;
    PagingQueryResult result = control.getResult();

    int listCount = control.getTopListCount();
    int count1 = 0;
    while (result.hasMoreElements()) {
        Object[] obj = (Object[])result.nextElement();
        if ( obj != null ) {

        E3PSProjectData data = new E3PSProjectData((E3PSProject)obj[0]);

        String projectStateStr = "진행중";
        if ( data.projectState == ProjectStateFlag.PROJECT_STATE_COMPLETE ) projectStateStr = "종료";
        else if ( data.projectState == ProjectStateFlag.PROJECT_STATE_STOP ) projectStateStr = "종료";

        if ( count%2 != 1 ) color="#ffffff";
        else color = "#ffffff";
        count++;
%>
    <tr bgcolor="#ffffff" onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
        <td width="30" align=center><%=listCount-count1++%></td>
        <td align="left">&nbsp;<%=data.name%></td>
        <td align="left"><%=data.getProjectUserStr()%></td>
        <td align="center"><%=data.execStartDate%></td>
        <td align="center"><%=data.execEndDate%></td>
        <td align="center"><%=messageService.getString("e3ps.message.ket_message", "00138", data.duration) %><%--{0}일--%></td>
        <td align="center"><%=data.completion%>%</td>
        <td align="center"><%=data.getStateStr()%></td>
        <td align="center"><B><%=projectStateStr%></B></td>
    </tr>
<%
        }
    }

    if(control.getTotalCount() == 0)
    {
%>
    <tr bgcolor="#FFFFFF">
        <td colspan="9" height="25" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01916") %><%--소속된 Project(이)가 없습니다--%></font></td>
    </tr>
<%  } %>
</table>
    <table width=95% align=center cellpadding=0= cellspacing=0 border=0><tr><td>
<%@include file="/jsp/common/page_include.jsp" %>
    </td></tr></table>
</form>
</body>
</html>
