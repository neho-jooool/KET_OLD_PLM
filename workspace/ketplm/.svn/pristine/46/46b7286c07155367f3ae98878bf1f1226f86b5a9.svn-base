<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,
                wt.fc.*,
                wt.org.*,
                wt.vc.*,
                wt.part.*,
                wt.vc.wip.*" %>
<%@page import="e3ps.groupware.company.*,
                e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String oid = request.getParameter("oid");
    String state = CharUtil.E2K(request.getParameter("state"));
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    E3PSProjectData projectData = new E3PSProjectData(project);
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "03077") %><%--프로젝트 상태 정보--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT LANGUAGE=JavaScript>
function MGRCreate(){
        width = 800;
        height = 600;
        var screenWidth = (screen.availWidth-width)/2;
        var screenHeight = (screen.availHeight-height)/2;
        var url = "/plm/jsp/project/MGRCreate.jsp?oid=<%=oid%>";
        newWin =       window.open(url,"compWindow","scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=800,height=600,resizable=no,top="+screenHeight+",left="+screenWidth);
                newWin.resizeTo(800,600);
                newWin.focus();
    }
</SCRIPT>
</head>
<body bgcolor="white" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0">
<form>
<input type=hidden name=oid value='<%=oid%>'>
<table width=95% height=40 align=center border=0>
    <tr>
        <td>
            <table border=0 cellpadding=0 cellspacing=0 >
                <tr>
                    <td><img src=/plm/portal/images/title2_left.gif></td>
                    <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "03077") %><%--프로젝트 상태 정보--%></td>
                    <td><img src=/plm/portal/images/title2_right.gif></td>
                </tr>
            </table>
        </td>
        <td align="right">
            <input type=button class="btnTras" onclick="javascript:MGRCreate()" value="<%=messageService.getString("e3ps.message.ket_message", "01895") %><%--성과 입력--%>" id=button>&nbsp;&nbsp;&nbsp;
            <input type=button class="btnTras" onclick="javascript:submitForm()" value="<%=messageService.getString("e3ps.message.ket_message", "02656") %><%--종료--%>" id=button>&nbsp;&nbsp;&nbsp;
            <input type=button class="btnTras" onclick="javascript:self.close()" value="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" id=button>
        </td>
    </tr>
</table>
<table width="95%" border="0" cellpadding="2" cellspacing="1" bgcolor=AABDC6 align=center>
    <tr>
        <td bgcolor="#EBEFF3" id=textblue>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
        <td bgcolor="#ffffff">&nbsp;<%=projectData.pjtNo%></td>
        <td bgcolor="#F4F6F3" colspan=4>
            <!--진행률 그림-->
            <table width=100% border=0 align=center>
                <tr>
                    <td width=10%>(<%=DateUtil.getDateString(projectData.pjtPlanStartDate, "D")%>)</td>
                    <td width=80%>
                        <table border=0 width=100% cellpadding=0 cellspacing=0>
                            <tr>
                                <td align=right width=<%=projectData.pjtCompletion%>%><img src=/plm/portal/icon/bar_arrow1.gif></td>
                                <td align=left width=<%=(100-projectData.pjtCompletion)%>%><img src=/plm/portal/icon/bar_arrow2.gif></td>
                            </tr>
                            <tr height=10>
                                <td <%if(projectData.pjtCompletion!=0){%>background=/plm/portal/icon/bar_full.gif<%}%>></td>
                                <td <%if(projectData.pjtCompletion!=100){%>background=/plm/portal/icon/bar_blank.gif<%}%>></td>
                            </tr>
                        </table>
                    </td>
                    <td width=10%>(<%=DateUtil.getDateString(projectData.pjtPlanEndDate, "D")%>)</td>
                </tr>
            </table>
            <!--//-->
        </td>
    </tr>
    <tr>
        <td width="140" bgcolor="#EBEFF3" id=textblue>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02019") %><%--시작일자--%></td>
        <td width="15%" bgcolor="#ffffff">&nbsp;<b><font color="red"><%=DateUtil.getDateString(projectData.pjtPlanStartDate, "D")%></font></b></td>
        <td width="90" bgcolor="#EBEFF3" id=textblue>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02659") %><%--종료일자--%></td>
        <td width="15%" bgcolor="#ffffff">&nbsp;<b><font color="red"><%=DateUtil.getDateString(projectData.pjtPlanEndDate, "D")%></font></b></td>
        <td width="90" bgcolor="#EBEFF3" id=textblue>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02828") %><%--총기간--%></td>
        <td width="35%" bgcolor="#ffffff">&nbsp;<%=projectData.pjtDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
    </tr>
    <tr>
        <td bgcolor="#EBEFF3" id=textblue>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%></td>
        <td bgcolor="#ffffff">&nbsp;<b><font color="red"><%=projectData.pjtCompletion%>&nbsp;%</font></b></td>
        <td bgcolor="#EBEFF3" id=textblue>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
        <td bgcolor="#ffffff" <% if( String.valueOf(projectData.pjtType).equals("2") ) {out.println("");}else{out.println("colspan=3");} %>><%=projectData.getStateStr(projectData.pjtStateName)%></td>
        <% if(String.valueOf(projectData.pjtType).equals("2")) { %>
        <td bgcolor="#EBEFF3" id=textblue>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01964") %><%--수주여부--%></td>
        <td bgcolor="#ffffff">&nbsp;
            <select name="pjtOrder" style="width:95%" >
                <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                <option value="수주"><%=messageService.getString("e3ps.message.ket_message", "01961") %><%--수주--%></option>
                <option value="미수주"><%=messageService.getString("e3ps.message.ket_message", "01452") %><%--미수주--%></option>
            </select>
        </td>
        <% } %>
    </tr>
    <tr>
        <td width="100" bgcolor="#EBEFF3" id=textblue>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02327") %><%--인원--%></td>
        <td bgcolor="#ffffff" colspan="5" align="left">
            <table width="100%" cellspacing="1" cellpadding="1" border="0" class=tb1 bgcolor=AABDC6>
                <tr bgcolor="#D6DBE7"  align=center>
                    <td nowrap id=title align="center" width="30">NO</td>
                    <td nowrap id=title align="center"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                    <td nowrap id=title align="center"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                    <td nowrap id=title align="center"><%=messageService.getString("e3ps.message.ket_message", "02723") %><%--직책--%></td>
                    <td nowrap id=title align="center"><%=messageService.getString("e3ps.message.ket_message", "03110") %><%--프로젝트권한--%></td>
                    <td nowrap id=title align="center">e-mail</td>
                </tr>
<%
            QueryResult list = ProjectUserHelper.manager.getProjectUser(project);
            int count = 1;
            while(list.hasMoreElements()){
                Object[] o = (Object[])list.nextElement();
                ProjectUserData data = new ProjectUserData((ProjectMemberLink)o[0]);
%>
                <tr align=center bgcolor=ffffff>
                    <td nowrap width="30" align=center><%=count++%></td>
                    <td nowrap>&nbsp;<a href="JavaScript:viewPeople('<%=data.peopleOID%>')"><%=data.name%></a></td>
                    <td nowrap>&nbsp;<%=data.departmentName%></td>
                    <td nowrap>&nbsp;<%=data.duty%></td>
                    <td nowrap>&nbsp;<%=data.projectDuty%></td>
                    <td nowrap>&nbsp;<a href="mailto:<%=data.email%>"><%=data.email%></a></td>
                </tr>
<%  }  %>
<%  if ( list == null || list.size() == 0 ) {  %>
                <tr bgcolor="#FFFFFF">
                    <td colspan="6" height="25" align="center" id=tb_gray><font color="red"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></font></td>
                </tr>
<%  }  %>
            </table>
        </td>
    </tr>
</table>
<br>
<table width=95% height=40 align=center border=0>
    <tr>
        <td>
            <table border=0 cellpadding=0 cellspacing=0 >
                <tr>
                    <td><img src=/plm/portal/images/title2_left.gif></td>
                    <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "03056") %><%--프로젝트 TASK 정보--%></td>
                    <td><img src=/plm/portal/images/title2_right.gif></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<table width="95%" cellspacing="1" cellpadding="1" border="0" class=tb1 align=center bgcolor=AABDC6>
    <tr bgcolor="#D6DBE7" align=center>
'        <td id=tb_blue rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%></td>
        <td id=tb_blue colspan="4"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
        <td id=tb_blue rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
        <td id=tb_blue rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%></td>
    </tr>
    <tr bgcolor="#D6DBE7" align=center>
        <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "00819") %><%--계획{0}시작일자--%></td>
        <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "00827") %><%--계획{0}종료일자--%></td>
        <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02061") %><%--실제 시작일자--%></td>
        <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02063") %><%--실제 종료일자--%></td>
    </tr>
<%

    ProjectTreeNode root = (ProjectTreeNode)ProjectScheduleHelper.manager.getRoot(project, false);

    Vector results = new Vector();
    makeVector(root, results);


    for(int i = 1; i < results.size(); i++){
        ProjectTreeNode node = (ProjectTreeNode)results.get(i);
        ProjectTreeNodeData td = (ProjectTreeNodeData)node.getUserObject();
        E3PSTask task = (E3PSTask)td.getData();

        E3PSTaskData taskData = new E3PSTaskData(task);

        int level = td.level;
%>
    <tr bgcolor=white>
        <td id=tb_gray>
        <%for(int j = 2; j < level; j++){ %>
        &nbsp;&nbsp;&nbsp;
        <%}%>

        <% if(level != 1){ %>
        <img src="/plm/portal/icon/tree/project/lastnode.png" border="0">

        <%}%>

        <img src="/plm/portal/icon/task.gif"><%=taskData.taskName%></td>
        <td id=tb_gray align=center>&nbsp;<%=DateUtil.getDateString(taskData.taskPlanStartDate, "D")%></td>
        <td id=tb_gray align=center><%=DateUtil.getDateString(taskData.taskPlanEndDate, "D")%></td>
        <td id=tb_gray align=center>&nbsp;<%=DateUtil.getDateString(taskData.taskExecStartDate, "D")%></td>
        <td id=tb_gray align=center><%=DateUtil.getDateString(taskData.taskExecEndDate, "D")%></td>
        <td id=tb_gray align=center><%=taskData.getEndNodeStateStr()%></td>
        <td id=tb_gray align=center><%=taskData.taskCompletion%>&nbsp;%</td>
    </tr>
<%}
%>

</table>
<br>
</form>
</body>
</html><script language="JavaScript">
<!--
var screenWidth = screen.availWidth/2-150;
var screenHeight = screen.availHeight/2-75;

function viewPeople(v){
    var str="/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+v;
    newWin = window.open(str,"viewPeople", "scrollbars=no,status=no,menubar=no,toolbar=no,location=no,directories=no,width=550,height=400,resizable=yes,mebar=no,left=40,top=65");
    newWin.focus();
}

function viewIssue(v){
    var str="/plm/page/project/projectIssueView.jsp?view=history&oid="+v;
    newWin = window.open(str,"window", "scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=700,height=500,resizable=no,top="+screenHeight+",left="+screenWidth);
    newWin.resizeTo(700,500);
    newWin.focus();
}

function viewDoc( oid ) {
    var str="/plm/page/docmgt/docView.jsp?view=history&viewtype=open&oid="+oid;
    newWin = window.open(str,"window", "scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=800,height=600,resizable=no,top="+screenHeight+",left="+screenWidth);
    newWin.resizeTo(800,600);
    newWin.focus();
}

var processState = true;
function submitForm(){
<%  //if ( !canComplete ) {  %>
    //alert('<%//=completeStr%>');
    //return;
<%
//  }

    if ( projectData.pjtCompletion  < 100 ) {
%>
        alert('<%=messageService.getString("e3ps.message.ket_message", "03121") %><%--프로젝트의 모든 진행률이 100%가 아닙니다--%>');
        return;
<%  }  %>

//수주 프로젝트 일 경우
<%  if ( projectData.pjtType == 2 ) { %>
    if(document.forms[0].pjtOrder.selectedIndex < 1) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01965") %><%--수주여부를 꼭 선택해 주시기 바랍니다--%>");
        return;
    }
<%  } %>
    if ( processState ) {
        if ( confirm('<%=messageService.getString("e3ps.message.ket_message", "03103") %><%--프로젝트를 종료합니다.\n종료된 프로젝트는 재진행 할수 없습니다.\n종료하시겠습니까?--%>') ) {
            var url = '/plm/jsp/common/processing.html?img=make_projectdone.gif';
            openOtherName(url,"process","100","100","status=no,scrollbars=no,resizable=no");
            document.forms[0].target = "process";
            <% if( projectData.pjtType == 2 ) { %>
            document.forms[0].action = "ProjectComplete.jsp?isComplete=<%=state%>&pjtOrderName="+document.forms[0].pjtOrder[document.forms[0].pjtOrder.selectedIndex].value;
            <% } else { %>
            document.forms[0].action = "ProjectComplete.jsp?isComplete=<%=state%>";
            <% } %>
            document.forms[0].method = "post";
            //alert(document.forms[0].action);
            document.forms[0].submit();
        }
    }
}
-->
</script>

<%!
    public void makeVector(ProjectTreeNode node, Vector vector){
        vector.add(node);
        for(int i = 0; i < node.getChildCount(); i++){
            makeVector((ProjectTreeNode)node.getChildAt(i), vector);
        }
    }
%>
