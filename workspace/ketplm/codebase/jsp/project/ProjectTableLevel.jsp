<%@page contentType="text/html; charset=UTF-8"%>
<%@page  errorPage="/jsp/common/error.jsp"%>
<%@page import="e3ps.common.iba.AttributeData,
                e3ps.project.*,
                e3ps.project.beans.*,
                java.util.*"%>
<%@page import="e3ps.common.util.*"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>

<HTML>
<HEAD>
<%

    String projectCode = request.getParameter("Project");
    String wbsCode = request.getParameter("WBS");
    String oid  = "";
    E3PSProject project =ProjectHelper.getProject(projectCode);

    if(project != null){
       oid = CommonUtil.getOIDString(project);
    }
    if(project == null){
%>
<script>
        alert("<%=messageService.getString("e3ps.message.ket_message", "03093") %><%--프로젝트 정보가 없습니다--%>");
</script>
<%
        return;
    }

    Object obj = project;

    if(wbsCode != null && wbsCode.length() > 0){
        obj = ProjectTaskHelper.getTask(project, wbsCode);
    }

    Kogger.debug("obj == " + obj);

    if(obj == null){
        out.println(messageService.getString("e3ps.message.ket_message", "00037", wbsCode)/*WBS {0}에 해당 되는 프로젝트가 없습니다*/);
        return;
    }

    ProjectTreeNode root = null;
    boolean isProject = false;
    if(obj instanceof E3PSProject){
         isProject = true;
        root = (ProjectTreeNode)ProjectScheduleHelper.manager.getRoot((E3PSProject)obj, false);
    }else{
        root = (ProjectTreeNode)ProjectScheduleHelper.manager.getRoot((E3PSTask)obj, false);
    }

    Vector list = new Vector();
    makeVector(root, list);
    if(isProject){
        list.remove(0);
    }
%>

<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<TITLE>BOM</TITLE>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/checkbox2.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/jsp/part/part.js"></SCRIPT>
<SCRIPT>

    function refresh(){
        disabledAllBtn();
        showProcessing();
        document.forms[0].action.value="";
        document.forms[0].submit();
    }

    function allProjectView(oid){
        var screenWidth = 0 ;
        var screenHeight = 0 ;
        var url =  "/plm/jsp/project/ProjectViewFrm.jsp?oid="+oid;
        newWin = window.open(url,"window","scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=1200,height=800,resizable=no,top="+screenHeight+",left="+screenWidth);
        newWin.focus();

    }

</SCRIPT>
</HEAD>

<BODY background="/plm/portal/images/img_common/contents_bg.gif">
<%@include file="/jsp/common/processing.html"%>
<FORM method=post >

<table border="0" cellspacing="0" cellpadding="0" class="tab_popup05">
    <tr>
      <td>
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="popBox">
                <tr>
                    <td class="boxTLeft"></td>
                  <td class="boxTCenter"></td>
                    <td class="boxTRight"></td>
                </tr>
                <tr>
                    <td class="boxLeft"></td>
                    <td>

                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <tr>
                                <td class="titleP"><%=messageService.getString("e3ps.message.ket_message", "03091") %><%--프로젝트 정보 보기--%></td>
                            </tr>
                        </table>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <tr>
                                <td height="10" class="space5"> </td>
                            </tr>
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
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td class="fontorange">
                                    <img src='/plm/portal/icon/project.gif' border=0>&nbsp;<a href="JavaScript:allProjectView('<%=oid%>')"><b><%=project.getPjtNo()%>&nbsp;(&nbsp;<%=project.getPjtName()%>&nbsp;)&nbsp;</b>
                                </td>
                            </tr>
                        </table>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <tr>
                                <td height="10" class="space10"> </td>
                            </tr>
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
                        <TABLE id=listTable border="0" cellspacing="0" cellpadding="0" width="100%">
                            <TR>

                                <TD class="tdblueM">Level</TD>
                                <TD class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%></TD>
                                <TD class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></TD>
                                <TD class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></TD>
                                <TD class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02064") %><%--실제시작일--%></TD>
                                <TD class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02067") %><%--실제종료일--%></td>
                                <TD class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00799") %><%--계획 %--%></td>
                                <TD class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02032") %><%--실적--%> %</td>
                                <TD class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01199") %><%--달성율--%> %</td>
                            </TR>
    <% for(int i = 0; i < list.size(); i++){
            ProjectTreeNode node = (ProjectTreeNode)list.get(i);
            ProjectTreeNodeData td = (ProjectTreeNodeData)node.getUserObject();
            E3PSTask task = (E3PSTask)td.getData();
            int level = td.level;
            int completion = task.getTaskCompletion();
            int planCompletion = 0;
            int dalsung = 0;

            String taskName = task.getTaskName();
            String planStart = DateUtil.getDateString(td.getPlanStartDate() , "date");
            String planEnd = DateUtil.getDateString(td.getPlanEndDate() , "date");
            String exStart = "";
            String exEnd = "";

            if(td.getExecStartDate() != null){
                 exStart = DateUtil.getDateString(td.getExecStartDate() , "date");
            }

            if(td.getExecEndDate() != null){
                exEnd = DateUtil.getDateString(td.getExecEndDate() , "date");
            }
            Calendar startDate = Calendar.getInstance();
            long currentTime = startDate.getTimeInMillis();
            if(td.getExecStartDate() != null){
                startDate.setTime(td.getExecStartDate());
                long startTimes = startDate.getTimeInMillis();
                startDate.add(Calendar.DAY_OF_YEAR, td.getDuration());
                long endTimes = startDate.getTimeInMillis();

                if(currentTime < endTimes){
                    long planTimes = currentTime - startTimes;
                    long totalTimes = endTimes - startTimes;

                    planCompletion = (int)((planTimes * 100)/totalTimes);

                }else{
                    planCompletion = 100;
                }
            }

            if(planCompletion > 0){
                dalsung = (completion * 100) / planCompletion;
                if(dalsung > 100){
                    dalsung = 100;
                }
            }



    %>
                            <TR>
                                <TD class="tdwhiteM">&nbsp;<%=level%></TD>
                                <TD class="tdwhiteL">&nbsp;
                                    <TABLE cellpadding="0">
                                        <TR>
                                            <TD><%for(int j = 0; j < level; j++){ %><img src='/plm/portal/icon/textarea_back.gif' border=0><%}%></TD>
                                            <TD>
                                                <img src='/plm/portal/icon/task.gif' border=0>
                                            </TD>
                                            <TD><%=taskName%></TD>
                                            <TD></TD>
                                        </TR>
                                    </TABLE>
                                </TD>
                                <TD class="tdwhiteL">&nbsp;<%=planStart%></TD>
                                <TD class="tdwhiteM">&nbsp;<%=planEnd%></TD>
                                <TD class="tdwhiteM">&nbsp;<%=exStart%></TD>
                                <TD class="tdwhiteM">&nbsp;<%=exEnd%></td>
                                <TD class="tdwhiteM">&nbsp;<%=planCompletion%></td>
                                <TD class="tdwhiteM">&nbsp;<%=completion%></td>
                                <TD class="tdwhiteM">&nbsp;<%=dalsung%></td>
                            </TR>
    <%}%>

                        </TABLE>

                    </td>
                    <td class="boxRight"></td>
                </tr>
                <tr>
                    <td class="boxBLeft"></td>
                    <td valign="bottom" class="boxBCenter"></td>
                    <td class="boxBRight"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>

</FORM>
</BODY>
</HTML>

<%!
    public void makeVector(ProjectTreeNode node, Vector vector){
        vector.add(node);
        for(int i = 0; i < node.getChildCount(); i++){
            makeVector((ProjectTreeNode)node.getChildAt(i), vector);
        }
    }
%>
