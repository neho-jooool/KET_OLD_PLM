<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.project.CheckoutLink"%>
<%@page import="e3ps.project.beans.DefaultProjectTreeNode"%>
<%@page import="e3ps.project.historyprocess.HistoryHelper"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.project.beans.TreeNodeData"%>
<%@page import="e3ps.project.E3PSTask"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%!
public static void makeVector(DefaultProjectTreeNode node, Vector vector){
    vector.add(node);
    for(int i = 0; i < node.getChildCount(); i++){
        makeVector((DefaultProjectTreeNode)node.getChildAt(i), vector);
    }
}

private String getTaskStateFont(DefaultProjectTreeNode node) {

    int result = node.getCompareResult();

    String fontStr = "";

    switch(result){
        case DefaultProjectTreeNode.ADD:
            fontStr ="추가";
            break;
        case DefaultProjectTreeNode.DELTE:
            fontStr ="삭제";
            break;
        case DefaultProjectTreeNode.MODIFY:
            fontStr ="수정";
            break;
        case DefaultProjectTreeNode.DEFAULT:
            fontStr ="유지";
            break;
    }

    return fontStr;
}
%>
<%
    String oid = request.getParameter("oid");

    if(oid == null || oid.length() == 0){
        oid = "e3ps.project.MoldProject:304214";
    }

    Kogger.debug("odi ===== " + oid);

    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);


    QueryResult rs = PersistenceHelper.manager.navigate(project, CheckoutLink.ORIGINAL_COPY_ROLE, CheckoutLink.class);

    E3PSProject oldProject = null;
    if(rs.hasMoreElements()){
        oldProject = (E3PSProject)rs.nextElement();
    }

    DefaultProjectTreeNode root = (DefaultProjectTreeNode)HistoryHelper.getCompareProject(project, oldProject, new HashMap());

    Vector vector = new Vector();
    makeVector(root, vector);


%>



<%@page import="e3ps.project.beans.E3PSTaskData"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="javax.swing.tree.TreeNode"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}

table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}
</style>

</head>

<body>
<form>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/plm/portal/images/logo_popupbg.png">
                      <table height="28" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02359") %><%--일정변경내역--%></td>
                            <td width="10"></td>
                          </tr>
                      </table>
                      </td>
                      <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
                    </tr>
                  </table>
                </td>
            </tr>
              </table>
             <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                          <!--
                          <td>&nbsp;</td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:comparison();" class="btn_blue">수정</a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                            </table>
                        </td>
                         -->
                          <td>&nbsp;</td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                            </table>
                        </td>
                    </tr>
                    </table>
                </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>

            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td valign="top">
                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <tr>
                                <td class="tab_btm2"></td>
                            </tr>
                        </table>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <tr>
                                <td  class="tab_btm1"></td>
                            </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td width="50" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                                <td width="250" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02920") %><%--태스크--%></td>
                                <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td>
                                <td colspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td>
                            </tr>
                            <tr>
                                <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td>
                                <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td>
                                <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td>
                                <td width="70" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td>
                            </tr>
                            <%
                            for(int i = 0; i < vector.size(); i++){
                                DefaultProjectTreeNode node = (DefaultProjectTreeNode)vector.get(i);

                                String taskType = getTaskStateFont(node);
                                E3PSTaskData childData = null;
                                E3PSTaskData compareData = null;

                                TreeNodeData td = (TreeNodeData)node.getUserObject();

                                if(td.getData() instanceof E3PSProject){
                                    /* TemplateProject 이다*/
                                    continue;
                                }

                                E3PSTask childTask = (E3PSTask)td.getData();

                                Kogger.debug(node.getLevel() + "  " + childTask.getTaskName());

                                E3PSTask compareTask = (E3PSTask)node.getCompareTask();

                                TreeNode[] tNode = node.getPath();

                                if("유지".equals(taskType)){
                                    continue;
                                }else if("추가".equals(taskType)){
                                    childData = new E3PSTaskData(childTask);
                                    %>
                                    <tr>
                                        <td width="50" class="tdwhiteM"><%=taskType %></td>
                                        <td width="250" class="tdwhiteM"><%=childTask.getTaskName() %></td>
                                        <td width="70" class="tdwhiteM">-</td>
                                        <td width="70" class="tdwhiteM">-</td>
                                        <td width="70" class="tdwhiteM"><%=childData == null?"-":DateUtil.getTimeFormat(childData.taskPlanStartDate, "yy/MM/dd") %></td>
                                        <td width="70" class="tdwhiteM0"><%=childData == null?"-":DateUtil.getTimeFormat(childData.taskPlanEndDate, "yy/MM/dd") %></td>
                                    </tr>
                                    <%
                                }else if("삭제".equals(taskType)){
                                    childData = new E3PSTaskData(childTask);
                                    %>
                                    <tr>
                                        <td width="50" class="tdwhiteM"><%=taskType %></td>
                                        <td width="250" class="tdwhiteM"><%=childTask.getTaskName() %></td>
                                        <td width="70" class="tdwhiteM"><%=childData == null?"-":DateUtil.getTimeFormat(childData.taskPlanStartDate, "yy/MM/dd") %></td>
                                        <td width="70" class="tdwhiteM"><%=childData == null?"-":DateUtil.getTimeFormat(childData.taskPlanEndDate, "yy/MM/dd") %></td>
                                        <td width="70" class="tdwhiteM">-</td>
                                        <td width="70" class="tdwhiteM0">-</td>
                                    </tr>
                                    <%
                                }else if("수정".equals(taskType)){
                                    childData = new E3PSTaskData(childTask);
                                    compareData = new E3PSTaskData(compareTask);
                                    %>
                                    <tr>
                                        <td width="50" class="tdwhiteM"><%=taskType %></td>
                                        <td width="250" class="tdwhiteM"><%=childTask.getTaskName() %></td>
                                        <td width="70" class="tdwhiteM"><%=compareData == null?"-":DateUtil.getTimeFormat(compareData.taskPlanStartDate, "yy/MM/dd") %></td>
                                        <td width="70" class="tdwhiteM"><%=compareData == null?"-":DateUtil.getTimeFormat(compareData.taskPlanEndDate, "yy/MM/dd") %></td>
                                        <td width="70" class="tdwhiteM"><%=childData == null?"-":DateUtil.getTimeFormat(childData.taskPlanStartDate, "yy/MM/dd") %></td>
                                        <td width="70" class="tdwhiteM0"><%=childData == null?"-":DateUtil.getTimeFormat(childData.taskPlanEndDate, "yy/MM/dd") %></td>
                                    </tr>
                                    <%
                                }
                            } %>
<%

        if(vector.size() == 0) {
%>
                            <tr>
                                <td class='tdwhiteM0' align='center' colspan='6'> <%=messageService.getString("e3ps.message.ket_message", "01508") %><%--변경된 태스크가 없습니다--%> </td>
                            </tr>
<%    }%>
                        </table>

                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</form>
</body>
</html>
