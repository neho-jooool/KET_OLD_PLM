<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "wt.fc.*,
                  wt.org.*"%>
<%@page import="e3ps.groupware.company.*,
                e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%

    String oid =  request.getParameter("oid");
    String oldOid =  request.getParameter("compareTaskOid");


    String newVersion = request.getParameter("newVersion");
    String oldVersion = request.getParameter("oldVersion");
    String compareResult = request.getParameter("compareResult");

    String compareResultStr = "";
    if(compareResult.equals("2")){
        compareResultStr ="[ " + messageService.getString("e3ps.message.ket_message", "02861")/*추가*/ + "  ]";
    }
    if(compareResult.equals("-1")){
        compareResultStr ="[ " + messageService.getString("e3ps.message.ket_message", "01690")/*삭제*/ + "  ]";
    }
    if(compareResult.equals("1")){
        compareResultStr ="[ " + messageService.getString("e3ps.message.ket_message", "01936")/*수정*/ + "  ]";
    }

    if(oid == null){
        oid = "";
    }

    if(oldOid == null){
        oldOid = "";
    }

    TemplateTask task = null;
    TemplateTaskData taskData = null;
    TemplateProjectData projectData = null;

    TemplateTask task2 = null;
    TemplateTaskData taskData2 = null;
    TemplateProjectData projectData2 = null;


    if(!oid.equals("")){
        task = (TemplateTask)CommonUtil.getObject(oid);
        taskData = new TemplateTaskData(task);
        projectData = new TemplateProjectData(task.getProject());
    }

    if(!oldOid.equals("")){
        task2 = (TemplateTask)CommonUtil.getObject(oldOid);
        taskData2 = new TemplateTaskData(task2);
        projectData2 = new TemplateProjectData(task2.getProject());
    }

%>
<html>
<head>
<title></title>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="JavaScript">
<!--
function viewPeople(oid){
    var url = "/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+oid;
    openSameName(url,"550","400","status=no,scrollbars=no,resizable=no");
}

function updataTask(){
    var url="/plm/jsp/project/template/TemplateTaskUpdate.jsp?oid=<%=oid%>";
    openSameName(url,"600","320","status=no,scrollbars=no,resizable=no");
}
-->
</script>
<style type="text/css">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;

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
-->
</style>


</head>
<body bgcolor="white" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0">
<form>
<!-- Hidden Value -->
<input type="hidden" name="oid" value="<%=oid%>">
<!-- //Hidden Value -->
<table border="0" cellpadding="1" cellspacing="1" width="100%">
    <tr>
        <td valign="top" style="padding-left:12">

            <!------------------------------ 본문 시작 //------------------------------>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <a href="javascript:parent.window.close();">
                        <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                        </a>
                        <!--table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                    <td class=fixLeft></td>
                                    <td ><input type=button class="btnTras" value="닫 기" onClick="javascript:parent.window.close()"></td>
                                    <td class=fixRight></td>
                            </tr>
                        </table-->
                    </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm5"> </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm1"> </td>
                </tr>
            </table>
            <!-- TASK 기본정보(NEW Version) -->
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <COL width="15%"><COL width="85%">
                <%if(!oid.equals("")){%>
                <tr>
                    <td class="tdblueM" colspan=2>
                        <img src='/plm/portal/icon/task.gif'>&nbsp;<B>[TASK]</B>&nbsp;<%=taskData.name%>&nbsp;<B><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%>(<%=newVersion%>) <%=messageService.getString("e3ps.message.ket_message", "02512") %><%--정보--%>  <%=compareResultStr%></B>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueM">TASK <%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                    <td class="tdwhiteL">
                        <%=taskData.duration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>
                    </td>
                </tr>
                <%} else {%>

                <tr>
                    <td class="tdblueM" colspan=2>
                        <img src='/plm/portal/icon/task.gif'>&nbsp;<B><%=messageService.getString("e3ps.message.ket_message", "00036", newVersion) %><%--버전 {0}에 대한 이력 정보가 없습니다--%></B>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueM">TASK <%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                    <td class="tdwhiteL">
                        - <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>
                    </td>
                </tr>
                <%} %>
            </table>
            <!-- //TASK 기본정보(NEW Version) -->
            <!-- TASK 기본정보(OLD Version) -->
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm5"> </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm1"> </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <COL width="15%"><COL width="85%">
                <%if(!oldOid.equals("")){%>
                <tr>
                    <td class="tdblueM" colspan=2>
                        <img src='/plm/portal/icon/task.gif'>&nbsp;<B>[TASK]</B>&nbsp;<%=taskData2.name%>&nbsp;<B><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%>(<%=oldVersion%>) <%=messageService.getString("e3ps.message.ket_message", "02512") %><%--정보--%></B>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueM">TASK <%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                    <td class="tdwhiteL">
                        <%=taskData2.duration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>
                    </td>
                </tr>
                <%} else {%>
                <tr>
                    <td class="tdblueM" colspan=2>
                        <img src='/plm/portal/icon/task.gif'>&nbsp;<B><%=messageService.getString("e3ps.message.ket_message", "00036", oldVersion) %><%--버전 {0}에 대한 이력  정보가 없습니다--%></B>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueM">TASK <%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                    <td class="tdwhiteL">
                        - <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>
                    </td>
                </tr>
                <%} %>
            </table>
            <!-- //TASK 기본정보(OLD Version) -->
            <!-- TASK 상세정보(NEW Version) -->
            <table border="0" cellpadding="0" cellspacing="0" width="100%" >
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm5"> </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm1"> </td>
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor=AABDC6>
            <COL width="15%"><COL width="85%">

                <%if(!oid.equals("")){%>
                <tr>
                    <td class="tdblueM" colspan=2>
                        <img src='/plm/portal/icon/task.gif'>&nbsp;<B>[TASK]</B>&nbsp;<%=taskData.name%>&nbsp;<B><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%>(<%=newVersion%>) <%=messageService.getString("e3ps.message.ket_message", "01748") %><%--상세정보--%></B>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01837") %><%--선행태스크--%></td>
                    <td class="tdwhiteL">
                    <div style="width=100%;overflow-x:hidden;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="tdblueM" width="55%" id=t_red><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                            <td class="tdblueM" width="45%"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>


                        </tr>
                    <%
                        QueryResult dependList = TaskDependencyHelper.manager.getDependTaskList(task);
                        if(dependList != null) {
                            while ( dependList.hasMoreElements() ) {
                                TaskDependencyLink link = (TaskDependencyLink)dependList.nextElement();
                                String linkOid = CommonUtil.getOIDString(link);
                                String taskOID = "";
                                String taskName = "";
                                String taskDuration = "";
                                String taskUserStr = "";
                                String taskCompletion = "";


                                TemplateTaskData dependData = new TemplateTaskData(link.getDependTarget());

                                taskOID = dependData.taskOID;
                                taskName = dependData.name;
                                taskDuration = dependData.duration+"";
                    %>
                        <tr>
                            <td class="tdwhiteM">&nbsp;<a href="JavaScript:viewTask('<%=taskOID%>')"><%=taskName%></a></td>
                            <td class="tdwhiteM" align=center>&nbsp;<%=taskDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>

                        </tr>
                    <%    }  %>
                    <%  } %>


                    </table>
                    </div>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01716") %><%--산출물--%></td>
                    <td class="tdwhiteL">
                    <div style="width:100%;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:3px 1px;">
                    <table border="0" cellspacing="0" cellpadding="0" width="100%" id="outputTable">
                        <tr>
                            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%></td>
                            <td class="tdblueM" width="150"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
                            <td class="tdblueM" width="80">Role</td>
                        </tr>
                        <%
                            ProjectOutput output = null;
                            ProjectOutputData outputData = null;
                            Object[] opObj = null;
                            QueryResult outputQr = ProjectOutputHelper.manager.getTaskOutput(task);
                            while (outputQr.hasMoreElements()) {
                                opObj = (Object[]) outputQr.nextElement();
                                output = (ProjectOutput) opObj[0];
                                //outputUser = output.getOwner() == null ? null : (WTUser) output.getOwner().getPrincipal();
                                outputData = new ProjectOutputData(output);
                        %>

                        <tr>
                            <td class="tdwhiteL" title="<%=outputData.name%>">
                            <div style="width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                            <nobr>
                                <a href="#" onClick="javascript:outputPage('<%=outputData.oid%>','<%=outputData.isRegistry%>');"><%=outputData.name%></a></nobr>
                            </div>
                            </td>
                            <td class="tdwhiteL" title="<%=outputData.locationStr%>">
                            <div style="width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                            <nobr><%=outputData.locationStr%></nobr></div>
                            </td>
                            <td class="tdwhiteM" title="<%=outputData.role_ko%>">
                            &nbsp;<%=outputData.role_ko%>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                    </div>
                    </td>
                </tr>
                <%} else{ %>
                <tr>
                    <td class="tdblueM" colspan=2>
                        <img src='/plm/portal/icon/task.gif'>&nbsp;<B><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%>(<%=newVersion%>) <%=messageService.getString("e3ps.message.ket_message", "01749") %><%--상세정보 이력이 없습니다--%></B>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01837") %><%--선행태스크--%></td>
                    <td class="tdwhiteL">
                        -
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01716") %><%--산출물--%></td>
                    <td class="tdwhiteL">
                        -
                    </td>
                </tr>
                <%} %>
            </table>
            <!-- //TASK 상세정보(NEW Version) -->
            <!-- TASK 상세정보(OLD Version) -->
            <table border="0" cellpadding="0" cellspacing="0" width="100%" >
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm5"> </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm1"> </td>
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor=AABDC6>
            <COL width="15%"><COL width="85%">
                <%if(!oldOid.equals("")){%>
                <tr>
                    <td class="tdblueM" colspan=2>
                        <img src='/plm/portal/icon/task.gif'>&nbsp;<B>[TASK]</B>&nbsp;<%=taskData2.name%>&nbsp;<B><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%>(<%=oldVersion%>) <%=messageService.getString("e3ps.message.ket_message", "01748") %><%--상세정보--%></B>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01837") %><%--선행태스크--%></td>
                    <td class="tdwhiteL">
                    <div style="width=99%;overflow-x:hidden;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="tdblueM" width="55%" id=t_red><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                            <td class="tdblueM" width="45%"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                        </tr>
                    <%
                        QueryResult dependList = TaskDependencyHelper.manager.getDependTaskList(task2);
                        if(dependList != null) {
                            while ( dependList.hasMoreElements() ) {
                                TaskDependencyLink link = (TaskDependencyLink)dependList.nextElement();
                                String linkOid = CommonUtil.getOIDString(link);
                                String taskOID = "";
                                String taskName = "";
                                String taskDuration = "";
                                String taskUserStr = "";
                                String taskCompletion = "";
                                TemplateTaskData dependData = new TemplateTaskData(link.getDependTarget());
                                taskOID = dependData.taskOID;
                                taskName = dependData.name;
                                taskDuration = dependData.duration + "";
                    %>
                        <tr>
                            <td class="tdwhiteM">&nbsp;<a href="JavaScript:viewTask('<%=taskOID%>')"><%=taskName%></a></td>
                            <td class="tdwhiteM" align=center>&nbsp;<%=taskDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>

                        </tr>
                    <%    }  %>
                    <%  } %>


                    </table>
                    </div>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01716") %><%--산출물--%></td>
                    <td class="tdwhiteL">
                    <div style="width:99%;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:3px 1px;">
                    <table border="0" cellspacing="0" cellpadding="0" width="100%" id="outputTable">
                        <tr>
                            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%></td>
                            <td class="tdblueM" width="150"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
                            <td class="tdblueM" width="80">Role</td>
                        </tr>
                        <%
                            ProjectOutput output = null;
                            ProjectOutputData outputData = null;
                            Object[] opObj = null;
                            QueryResult outputQr = ProjectOutputHelper.manager.getTaskOutput(task2);
                            while (outputQr.hasMoreElements()) {
                                opObj = (Object[]) outputQr.nextElement();
                                output = (ProjectOutput) opObj[0];
                                //outputUser = output.getOwner() == null ? null : (WTUser) output.getOwner().getPrincipal();
                                outputData = new ProjectOutputData(output);
                        %>

                        <tr>
                            <td class="tdwhiteL" title="<%=outputData.name%>">
                            <div style="width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                            <nobr>
                                <a href="#" onClick="javascript:outputPage('<%=outputData.oid%>','<%=outputData.isRegistry%>');"><%=outputData.name%></a></nobr>
                            </div>
                            </td>
                            <td class="tdwhiteL" title="<%=outputData.locationStr%>">
                            <div style="width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                            <nobr><%=outputData.locationStr%></nobr></div>
                            </td>
                            <td class="tdwhiteM" title="<%=outputData.role_ko%>">
                            &nbsp;<%=outputData.role_ko%>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                    </div>
                    </td>
                </tr>
                <%} else{ %>
                <tr>
                    <td class="tdblueM" colspan=2>
                        <img src='/plm/portal/icon/task.gif'>&nbsp;<B><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%>(<%=oldVersion%>) <%=messageService.getString("e3ps.message.ket_message", "01749") %><%--상세정보 이력이 없습니다--%></B>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01837") %><%--선행태스크--%></td>
                    <td class="tdwhiteL">
                        -
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01716") %><%--산출물--%></td>
                    <td class="tdwhiteL">
                        -
                    </td>
                </tr>

                <%} %>

            </table>
            <!-- TASK 상세정보(OLD Version) -->
        </td>
    </tr>
</table>
<!-- 본문외관테두리 끝 //-->
</form>
</body>
</html>
