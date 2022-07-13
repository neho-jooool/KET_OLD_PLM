<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="e3ps.project.beans.ProjectTreeNode"%>
<%@page import="e3ps.project.beans.ProjectScheduleHelper"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.project.beans.ProjectTreeNodeData"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.beans.E3PSTaskData"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.wbs.WBSItem"%>
<%@page import="e3ps.sap.PJTInfoERPInterface"%>
<%@page import="e3ps.sap.SAPTask"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>


<head>
<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "00224") %><%--ERP 프로젝트 하위 TASK 정보--%> </title>
<style type="text/css">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;
}
-->
</style>
</head>
<body>
    <form>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td class="space20"></td>
            </tr>
        </table>
        <table width=100% height=40 align=center border=0>
            <tr>
                <td valign="top" style="padding:0px 0px 0px 0px">
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
                      <tr>
                        <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00224") %><%--ERP 프로젝트 하위 TASK 정보--%></td>
                        <td align="right" style="padding:8px 0px 0px 0px"></td>
                        <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
                      </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td>&nbsp;</td>
                            <td align="right">
                                <a href="javascript:self.close();">
                                <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                                </a>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>

        <table width="100%" cellspacing="1" cellpadding="1" border="0" align=center bgcolor=AABDC6>
            <tr bgcolor="#D6DBE7" align=center>
                <td id=tb_blue rowspan="2">Level</td>
                <td id=tb_blue rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%></td>
                <td id=tb_blue colspan="7"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
            </tr>
            <tr bgcolor="#D6DBE7" align=center>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "00819") %><%--계획{0}시작일자--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "00827") %><%--계획{0}종료일자--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02061") %><%--실제 시작일자--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02063") %><%--실제 종료일자--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "00807") %><%--계획 진척율--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02034") %><%--실적 진척율--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01199") %><%--달성율--%></td>
            </tr>
        <%
            String projectOid = request.getParameter("oid");
            String wbsOid = request.getParameter("wbs");

            if(projectOid == null && projectOid.length() == 0){
                projectOid = "";
            }

            if(wbsOid == null && wbsOid.length() == 0){
                wbsOid = "";
            }


            E3PSTask project = (E3PSTask)CommonUtil.getObject(projectOid);
            WBSItem wbsitem = (WBSItem)CommonUtil.getObject(wbsOid);


            //ProjectTreeNode root = (ProjectTreeNode)ProjectScheduleHelper.manager.getRoot(project, false);

            Vector results = new Vector();

            results =(Vector)PJTInfoERPInterface.getSAPTasks(project.getProject().getPjtNo(), wbsitem.getWbsCode());

            //makeVector(root, results);


            for(int i = 0; i < results.size(); i++){
                //ProjectTreeNode node = (ProjectTreeNode)results.get(i);
                //ProjectTreeNodeData td = (ProjectTreeNodeData)node.getUserObject();
                SAPTask task = (SAPTask)results.get(i);


                int level = task.level - 2;
        %>
            <tr bgcolor=white>
                <td id=tb_gray>&nbsp;<%=level%> </td>

                <td id=tb_gray title="<%=task.taskName%>" >
                    <div style="width:350;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                    <nobr>
                    <%for(int j = 0; j < level; j++){ %>
                    &nbsp;&nbsp;&nbsp;
                    <%}%>

                    <% if(level != 0){ %>
                    <img src="/plm/portal/icon/tree/project/lastnode.png" border="0">

                    <%}%>
                    <img src="/plm/portal/icon/task.gif"><%=task.taskName%>
                    </nobr>
                    </div>
                </td>

                <td id=tb_gray align=center>&nbsp;<%=task.planStart%></td>
                <td id=tb_gray align=center>&nbsp;<%=task.execEnd%></td>
                <td id=tb_gray align=center>&nbsp;<%=task.execStart%></td>
                <td id=tb_gray align=center>&nbsp;<%=task.execEnd%></td>
                <td id=tb_gray align=center>&nbsp;<%=task.planCompletion%>%</td>
                <td id=tb_gray align=center>&nbsp;<%=task.execCompletion%>%</td>
                <td id=tb_gray align=center>&nbsp;<%=task.dalsungCompletion%>%</td>
            </tr>
        <%}
            if(results.size() == 0 ){
        %>
            <tr bgcolor=white>
                <td id=tb_gray align=center colspan="9" ><font color="red">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></font></td>
            </tr>
        <%} %>
        </table>


    </form>

</body>
</html>

<%!
    public void makeVector(ProjectTreeNode node, Vector vector){
        vector.add(node);
        for(int i = 0; i < node.getChildCount(); i++){
            makeVector((ProjectTreeNode)node.getChildAt(i), vector);
        }
    }
%>
