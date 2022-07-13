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

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
    String oid = StringUtil.checkNull(request.getParameter("oid"));
    String state = StringUtil.checkNull(request.getParameter("state"));
    E3PSTask task = (E3PSTask)CommonUtil.getObject(oid);
    E3PSProject project = (E3PSProject)task.getProject();
    E3PSTaskData taskData = new E3PSTaskData(task);
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "00484") %><%--Task 상태 정보--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script src="/plm/portal/js/viewObject.js"></script>
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
                    <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "00484") %><%--Task 상태 정보--%></td>
                    <td><img src=/plm/portal/images/title2_right.gif></td>
                </tr>
            </table>
        </td>
        <td align="right">
            <input type=button class="btnTras" onclick="javascript:submitForm()" value="<%=messageService.getString("e3ps.message.ket_message", "02656") %><%--종료--%>" id=button>&nbsp;
            <input type=button class="btnTras" onclick="javascript:self.close()" value="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" id=button>
        </td>
    </tr>
</table>
<table width="95%" border="0" cellpadding="2" cellspacing="1" bgcolor=AABDC6 align=center>
    <tr>
        <td bgcolor="#EBEFF3">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%></td>
        <td bgcolor="F4F6F3">&nbsp;<%=taskData.taskName%></td>
        <td bgcolor="#ffffff" colspan=4>
            <!--진행률 그림-->
            <table width="100%" border=0 align=center>
                <tr>
                    <td width="10%"> (<%=DateUtil.getDateString(taskData.taskPlanStartDate, "D")%>)</td>
                    <td width="80%">
                        <table border=0 width=100% cellpadding=0 cellspacing=0>
                            <tr>
                                <td align=right width=<%=taskData.taskCompletion%>%><img src="/plm/portal/icon/bar_arrow1.gif"></td>
                                <td align=left width=<%=(100-taskData.taskCompletion)%>%><img src="/plm/portal/icon/bar_arrow2.gif"></td>
                            </tr>
                            <tr height=10>
                                <td <%if(taskData.taskCompletion!=0){%>background="/plm/portal/icon/bar_full.gif"<%}%>></td>
                                <td <%if(taskData.taskCompletion!=100){%>background="/plm/portal/icon/bar_blank.gif"<%}%>></td>
                            </tr>
                        </table>
                    </td>
                    <td width="10%">(<%=DateUtil.getDateString(taskData.taskPlanEndDate, "D")%>)</td>
                </tr>
            </table>
            <!--//-->
        </td>
    </tr>
    <tr>
        <td width="130" bgcolor="#EBEFF3">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02019") %><%--시작일자--%></td>
        <td width="20%" bgcolor="F4F6F3">&nbsp;<b><font color="red"><%=DateUtil.getDateString(taskData.taskPlanStartDate, "D")%></font></b></td>
        <td width="100" bgcolor="#EBEFF3">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02659") %><%--종료일자--%></td>
        <td width="20%" bgcolor="F4F6F3">&nbsp;<b><font color="red"><%=DateUtil.getDateString(taskData.taskPlanEndDate, "D")%></font></b></td>
        <td width="100" bgcolor="#EBEFF3">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
        <td width="30%" bgcolor="F4F6F3">&nbsp;<%=taskData.taskDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
    </tr>
    <tr>
        <td bgcolor="#EBEFF3">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%></td>
        <td bgcolor="F4F6F3">&nbsp;<b><font color="red"><%=taskData.taskCompletion%>&nbsp;%</font></b></td>
        <td bgcolor="#EBEFF3">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
        <td bgcolor="F4F6F3"><%=taskData.getStateStr()%></td>
        <td bgcolor="F4F6F3" colspan="2" align="center">&nbsp;</td>
    </tr>
<%
    QueryResult dependList = TaskDependencyHelper.manager.getDependTaskList(task);
    if(dependList!=null && dependList.size()>0){
%>
    <tr>
        <td width="100" bgcolor="#EBEFF3">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01837") %><%--선행태스크--%></td>
        <td bgcolor="F4F6F3" colspan="5" align="left">
            <!--종속태스크//-->
            <table width=100% border=0 cellpadding=2 cellspacing=0>
                <tr>
                    <td width=100%>
                        <table width="99%" cellspacing="1" cellpadding="1" border="0" class=tb1 bgcolor=AABDC6>
                            <tr bgcolor="#D6DBE7"  align=center>
                                <td id=tb_blue width="45%" id=t_red><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                                <td id=tb_blue width="10%"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                                <td id=tb_blue width="20%"><%=messageService.getString("e3ps.message.ket_message", "02773") %><%--책임자--%></td>
                                <td id=tb_blue width="10%"><%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%></td>
                            </tr>
<%
        while ( dependList.hasMoreElements() ) {
            TaskDependencyLink link = (TaskDependencyLink)dependList.nextElement();
            String linkOid = CommonUtil.getOIDString(link);
            E3PSTaskData dependData = new E3PSTaskData((E3PSTask)link.getDependTarget());
%>
                            <tr align=center bgcolor=white>
                                <td id=tb_gray><%=dependData.taskName%></td>
                                <td id=tb_gray><%=dependData.taskDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                                <td id=tb_gray><%=dependData.getTaskUserStr()%>&nbsp;</td>
                                <td id=tb_gray><%=dependData.taskCompletion%> %</td>
                            </tr>
<%    }  %>
                        </table>
                    </td>
                </tr>
            </table>
            <!--//-->
        </td>
    </tr>
<%
    }
    QueryResult masterList = TaskUserHelper.manager.getMaster(task);
    if(masterList!=null && masterList.size()>0){
%>
    <tr>
        <td width="100" bgcolor="#EBEFF3">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02773") %><%--책임자--%></td>
        <td bgcolor="F4F6F3" colspan="5" align="left">
            <!--책임자//-->
            <table width=100% border=0 cellpadding=2 cellspacing=0>
                <tr>
                    <td width=100%>
                        <table width="99%" cellspacing="1" cellpadding="1" border="0" class=tb1 bgcolor=AABDC6>
                            <tr bgcolor="#D6DBE7"  align=center>
                                <td width=35% id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                                <td width=15% id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                                <td width=15% id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                                <td width=30% id=tb_blue>E-Mail</td>
                            </tr>
<%
        while ( masterList.hasMoreElements() ) {
            Object o[]=(Object[])masterList.nextElement();
            TaskMemberLink link = (TaskMemberLink)o[0];
            PeopleData data = new PeopleData(link.getMember().getMember());
            String wtuserOID = CommonUtil.getOIDString(data.wtuser);
            String peopleOID = CommonUtil.getOIDString(data.people);
%>
                            <tr align=center bgcolor=white>
                                <td  id=tb_gray><a href="JavaScript:viewPeople('<%=peopleOID%>')"><%=data.name%></td>
                                <td  id=tb_gray><%=data.departmentName%></td>
                                <td  id=tb_gray><%=data.duty%></td>
                                <td  id=tb_gray><%=data.email%></td>
                            </tr>
<%    }  %>
                        </table>
                    </td>
                </tr>
            </table>
            <!--//-->
        </td>
    </tr>
<%
    }
    QueryResult memberlist = TaskUserHelper.manager.getMember(task);
    if(memberlist!=null && memberlist.size()>0){
%>
    <tr>
        <td width="100" bgcolor="#EBEFF3">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
        <td bgcolor="F4F6F3" colspan="5" align="left">
            <!--담당자//-->
            <table width=100% border=0 cellpadding=2 cellspacing=0>
                <tr>
                    <td width=100%>
                        <table width="99%" cellspacing="1" cellpadding="1" border="0" class=tb1 bgcolor=AABDC6>
                            <tr bgcolor="#D6DBE7"  align=center>
                                <td width=35% id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                                <td width=15% id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                                <td width=15% id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                                <td width=30% id=tb_blue>E-Mail</td>
                            </tr>
<%
        while ( memberlist.hasMoreElements() ) {
            Object o[]=(Object[])memberlist.nextElement();
            TaskMemberLink link = (TaskMemberLink)o[0];
            PeopleData data = new PeopleData(link.getMember().getMember());
            String wtuserOID = CommonUtil.getOIDString(data.wtuser);
            String peopleOID = CommonUtil.getOIDString(data.people);
%>
                            <tr align=center bgcolor=white>
                                <td id=tb_gray><a href="JavaScript:viewPeople('<%=peopleOID%>')"><%=data.name%></td>
                                <td id=tb_gray><%=data.departmentName%></td>
                                <td id=tb_gray><%=data.duty%></td>
                                <td id=tb_gray><%=data.email%></td>
                            </tr>
<%    }  %>
                        </table>
                    </td>
                </tr>
            </table>
            <!--//-->
        </td>
    </tr>
<%
    }
    QueryResult qr = ProjectOutputHelper.manager.getTaskOutput(task);
    int registryCount = 0;
    int willRegistryCount = 0;
    int primaryCount = 0;    //필수 산출물
    boolean canComplete = true;
    String completeStr = "";
    if ( qr.size() > 0 ) {
%>
    <tr>
        <td width="100" bgcolor="#EBEFF3" rowspan=2>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01716") %><%--산출물--%></td>
        <td bgcolor="F4F6F3" colspan="5" align="left">
            <!--산출물//-->
            <table width=100% border=0 cellpadding=2 cellspacing=0>
                <tr>
                    <td width=100%>
                        <table width="99%" cellspacing="1" cellpadding="1" border="0" class=tb1 bgcolor=AABDC6>
                            <tr bgcolor="#D6DBE7"  align=center>
                                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%></td>
                                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02918") %><%--타입--%></td>
                                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02795") %><%--첨부여부--%></td>
                                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
                                <td width="100" id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                            </tr>
<%
        while ( qr.hasMoreElements() ) {
            Object[] objArr = (Object[])qr.nextElement();
            ProjectOutput output = (ProjectOutput)objArr[0];
            ProjectOutputData data = new ProjectOutputData (output);
            if ( output.isIsPrimary() ) {
                if ( data.isRegistry ) registryCount++;
                else {
                    if ( canComplete ) {
                        canComplete = false;
                        completeStr = messageService.getString("e3ps.message.ket_message", "01740")/*미등록 산출물이 있습니다.\n산출물은 모두 등록되어야 태스크 종료가 가능합니다*/;
                    }
                    willRegistryCount++;
                }

                if ( data.isWorking ) {
                    if ( canComplete ) {
                        canComplete = false;
                        completeStr = messageService.getString("e3ps.message.ket_message", "01742")/*산출물중에 승인중이거나 배포중인 산출물이 있습니다\n모든 산출물은 승인됨이나 배포완료상태일때 태스크 종료가 가능합니다*/;
                    }
                }

                primaryCount++;
            }
%>
                            <tr bgcolor=white align=center>
<%
            if ( data.isRegistry ) {
                String imgStr = "doc.gif";
                if( WorkInProgressHelper.isCheckedOut( (Workable)data.currentDocument )) imgStr = "doc_checkout.gif";
%>
                                <td id=tb_gray><A HREF="javascript:openViewProject('<%=CommonUtil.getOIDString(data.currentDocument)%>');"><%=data.name%></td>
<%      } else {  %>
                                <td bgcolor="white" id=tb_gray><%=data.name%></td>
<%      }  %>
                                <td id=tb_gray><%=data.objType%></td>
                                <td id=tb_gray><%=data.isPrimary%></td>
                                <td id=tb_gray><%=data.locationStr%></td>
                                <td width="100" id=tb_gray><%if(data.registryUser!=null)out.print(data.registryUser.getFullName());%>&nbsp;</td>
<%      if ( !data.isRegistry ) { %>
                                <td id=tb_gray bgcolor="white" colspan=3><b><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01333") %><%--등록안됨--%></font></b></td>
<%      } else {  %>
                                <td id=tb_gray bgcolor="white"><%=data.currentDocument.getVersionDisplayIdentifier()%></td>
                                <td id=tb_gray bgcolor="white"><%=data.currentDocument.getLifeCycleState().getLocalizedMessage(Locale.KOREAN)%></td>
                                <td id=tb_gray bgcolor="white"><%=data.createDate%></td>
<%      }  %>

                            </tr>
<%    }  %>
                        </table>
                    </td>
                </tr>
            </table>
            <!--//-->
        </td>
    </tr>
    <tr>
        <td bgcolor="F4F6F3" colspan="5" align="left">
            <B><%=messageService.getString("e3ps.message.ket_message", "02826") %><%--총 산출물 수--%> : <%=qr.size()%></B><BR>
            <B><%=messageService.getString("e3ps.message.ket_message", "03129") %><%--필수 산출물 수--%> : <%=primaryCount%><B><BR>
            <B><%=messageService.getString("e3ps.message.ket_message", "01331") %><%--등록된 필수 산출물 수--%> : <%=registryCount%></B><BR>
            <B><%=messageService.getString("e3ps.message.ket_message", "01446") %><%--미등록된 필수 산출물 수--%> : <%=willRegistryCount%></B>
        </td>
    </tr>
<%
    }
%>
</table>
<br>
</form>
</body>
</html>
<script language="JavaScript">
<!--
var screenWidth = screen.availWidth/2-150;
var screenHeight = screen.availHeight/2-75;

function viewPeople(v){
    var str="/plm/jsp/company/selectPeopleView.jsp?viewtype=open&oid="+v;
    newWin = window.open(str,"window", "scrollbars=no,status=no,menubar=no,toolbar=no,location=no,directories=no,width=550,height=400,resizable=no,top="+screenHeight+",left="+screenWidth);
    newWin.resizeTo(550,400);
    newWin.focus();
}

function viewTask(v){
    document.forms[0].oid.value = v;
    document.forms[0].action = "/plm/jsp/project/TaskView.jsp";
    document.forms[0].method = "post";
    document.forms[0].submit();
}

var processState = true;
function submitForm(){
<%  if ( !canComplete ) {  %>
    alert('<%=completeStr%>');
    return;
<%  }  %>
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03337") %><%--종료된 Task는 재진행 할수 없습니다.\n종료하시겠습니까?--%>") ) {
        document.forms[0].action = "/plm/servlet/e3ps/ManageProjectTaskServlet?command=complete&isComplete=<%=state%>&oid=<%=oid%>";
        document.forms[0].method = "post";
        document.forms[0].submit();
    }
}
-->
</script>
