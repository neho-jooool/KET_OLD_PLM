<%@page import="e3ps.project.beans.ProjectScheduleHelper"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.project.beans.ProjectTreeNode"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.jdf.config.*"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.beans.ProjectViewButtonAuth"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%
    String oid = request.getParameter("oid");
    Config conf = ConfigImpl.getInstance();
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    ProjectViewButtonAuth auth = new ProjectViewButtonAuth(project);

    String popup = request.getParameter("popup");
    boolean isbizAdmin = CommonUtil.isMember("Business Administrators");

    String pOid = "";
    boolean isMold = false;
    boolean isChild = false;

    if(project instanceof MoldProject){
        ProjectTreeNode root = (ProjectTreeNode)ProjectScheduleHelper.manager.getRoot((MoldProject)project, false);
        Vector list = new Vector();
        makeVector(root, list);
        isMold = true;
        E3PSProjectData data = new E3PSProjectData(project);
        if(data.pjtPlanEndDate == null || list.size() == 1){
            isChild = true;
        }
        pOid = CommonUtil.getOIDString(((MoldProject)project).getMoldInfo().getProject());
    }

    //out.println("auth.isTaskManage>> " +auth.isTaskManage);
%>

<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="javascript" src="/plm/portal/js/script.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/select.js"></SCRIPT>
<script language=JavaScript src="/plm/portal/js/viewObject.js"></script>

<script language=JavaScript>
<!--
var view = true;
function SapSync(oid){
    var url="/plm/jsp/project/SAPSync.jsp?oid=" + oid;
    openOtherName(url,"sapSync","500","300","status=no,scrollbars=no,resizable=no");
}

function SapMold(oid) {
    var url = "/plm/jsp/project/SAPMold.jsp?oid="+oid;
    //openOtherName(url, "sapMold", "1000", "800", "status=no,scrollbars=yes,resizable=yes");

    openWindow(url, 'sapMold', 1000, 800);
}

function viewToggle(){
    if ( view ) {
        document.popen.src="/plm/portal/icon/blue_icon_open.gif";
        parent.projectframe.cols="20,*";
        view = false;
    } else{
        document.popen.src="/plm/portal/icon/blue_icon_close.gif";
        parent.projectframe.cols="200,*"
        view = true;
    }
}

function viewData(src) {
<%
    if(popup != null && popup.equals("popup")) {
%>
    parent.body.location.href = src;
<%
    } else {
%>
    parent.parent.document.frames["contName"].location.href = src;
<%
    }
%>
    //parent.parent.document.frames["contName"].location.href = src;
}

function reloadTree(){
    //var f = parent.tree.document.forms[0];
    //f.action = "/plm/jsp/project/ProjectTree.jsp?oid=<%=oid%>";
    //f.submit();
    parent.document.location.reload();
}

function manageTask(oid){
    //alert(<%=isMold%>);
    //alert(<%=isChild%>);
    <%if(isMold && isChild){%>
        var url="/plm/jsp/project/CreateMoldProject.jsp?oid=" + oid;
        openOtherName(url, "popup", 730, 680, "status=yes,scrollbars=auto,resizable=yes");
    <%}else{%>
        var url = "/plm/jsp/project/manage/ManageProjectTaskFrm.jsp?oid="+oid;
        openOtherName(url,"manageTask","950","800","status=no,scrollbars=no,resizable=no");
    <%}%>
}

// [START] [PLM System 1차개선] Project 일정 변경 화면 Link, 2013-06-10, BoLee
function openChangeProjectSchedule(oid) {

    <%
        if ( isMold && isChild ) {
    %>
        var url="/plm/jsp/project/CreateMoldProject.jsp?oid=" + oid;
        openOtherName(url, "popup", 730, 680, "status=yes,scrollbars=auto,resizable=yes");
    <%
        }
        else {
    %>
        var screenWidth = screen.availWidth;
        var screenHeight = screen.availHeight;
        var url = "/plm/servlet/e3ps/ProjectScheduleServlet?oid=" + oid + "&popup=<%= StringUtil.checkNull(popup) %>&cmd=search&width=" + screenWidth + "&height=" + screenHeight;
        openOtherName(url, "ChangeProjectSchedule", screenWidth, screenHeight, "status=no,scrollbars=no,resizable=yes");
    <%
        }
    %>
}
// [END] [PLM System 1차개선] Project 일정 변경 화면 Link, 2013-06-10, BoLee

function projectDepartScheduleLevel(oid){
    //openWindow('/plm/jsp/project/ProjectSchedule.jsp?oid='+oid, 'ProjectSchedule',870,700);
    var url = "/plm/jsp/project/ProjectScheduleLevel.jsp?oid="+oid;
    openOtherName(url,"manageTask","870","700","status=no,scrollbars=yes,resizable=yes");
}

function projectView(){
    openWindow("/plm/jsp/project/ProjectViewFrm.jsp?oid=<%=pOid%>&popup=popup", '',1050,800);
}
-->
</script>
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>
</head>
<body onLoad="MM_preloadImages('/plm/portal/images/btn_ref_s.png','/plm/portal/images/btn_task_s.png')">
<form>
<table width="100%" border="0" cellspacing="0" cellpadding="0" >
    <%if(popup == null){ %>
    <tr>
       <td height="44">

       <table width="100%" border="0" cellspacing="0" cellpadding="0">
           <tr>
             <td><img src="/plm/portal/images/subh_8.png"></td>
           </tr>
           <tr>
             <td height="10"></td>
           </tr>
         </table>
         </td>
         <td width="1" bgcolor="#c6c6c6"></td>
         <td width="9" valign="top"></td>
     </tr>
     <%} %>
          <!--tr>
              <td height="10"></td>
              <td width="1" bgcolor="#c6c6c6"></td>
             <td width="9" valign="top"></td>
          </tr-->
          <tr>
            <td align="center" valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="7" height="7"><img src="/plm/portal/images/box_5.gif"></td>
                            <td bgcolor="#e6f0ff"></td>
                            <td width="7" height="7"><img src="/plm/portal/images/box_6.gif"></td>
                        </tr>
                        <tr>
                            <td width="7" bgcolor="#e6f0ff"></td>
                            <td align="center" valign="right" bgcolor="#e6f0ff">
                                <!-- a href="javascript:viewData('/plm/jsp/project/ProjectPeopleList.jsp?oid=<%=oid%>');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftbtn_03','','/plm/portal/images/btn_mem_s.png',1)">
                            <img src="/plm/portal/images/btn_mem.png" alt="구성원 목록" name="leftbtn_03" border="0"></a--> <!-- 제품 프로젝트 --> <%
     if (isMold) {
 %>
                                <a href="javascript:projectView();" onMouseOut="MM_swapImgRestore()"
                                onMouseOver="MM_swapImage('leftbtn_03','','/plm/portal/images/btn_product_s.gif',1)"> <img
                                    src="/plm/portal/images/btn_product.gif"
                                    alt='<%=messageService.getString("e3ps.message.ket_message", "02548")%><%--제품 프로젝트--%>'
                                    name="leftbtn_03" border="0"></a> <%
     }
 %> <%
     if (true) {
 %> <a
                                href="javascript:viewData('/plm/servlet/e3ps/IssueServlet?command=searchProjectIssue&projectOid=<%=oid%>');"
                                onMouseOut="MM_swapImgRestore()"
                                onMouseOver="MM_swapImage('leftbtn_04','','/plm/portal/images/btn_issue_s.png',1)"> <img
                                    src="/plm/portal/images/btn_issue.png"
                                    alt="<%=messageService.getString("e3ps.message.ket_message", "00271")%><%--Issue 목록--%>"
                                    name="leftbtn_04" border="0"></a> <%
     }
 %> <a href="javascript:reloadTree();"
                                onMouseOut="MM_swapImgRestore()"
                                onMouseOver="MM_swapImage('leftbtn_02','','/plm/portal/images/btn_ref_s.png',1)"> <img
                                    src="/plm/portal/images/btn_ref.png"
                                    alt="<%=messageService.getString("e3ps.message.ket_message", "01768")%><%--새로고침--%>" name="leftbtn_02"
                                    border="0"></a> <!-- ERP 동기화 --> <!-- TASK 관리 --> <%
     if ((auth.isLatest && (auth.isPM || CommonUtil.isAdmin() || auth.isPMO || isbizAdmin) && (auth.isPlanChange || auth.isReWork || auth.isPMOInWork))
 		    || (auth.isLatest && (auth.isPMO || isbizAdmin) && auth.isPMOInWork)) {
 %>
                                <%--
                            <a href="javascript:manageTask('<%=oid%>');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftbtn_07','','/plm/portal/images/btn_task_s.png',1)">
                            <img src="/plm/portal/images/btn_task.png" alt="TASK관리" name="leftbtn_07" border="0"></a>
                            --%> <!-- [START] [PLM System 1차개선] Project 일정 변경 화면 Link, 2013-06-10, BoLee --> <a
                                href="javascript:openChangeProjectSchedule('<%=oid%>');" onMouseOut="MM_swapImgRestore()"
                                onMouseOver="MM_swapImage('leftbtn_07','','/plm/portal/images/btn_task_s.png',1)"> <img
                                    src="/plm/portal/images/btn_task.png"
                                    alt="<%=messageService.getString("e3ps.message.ket_message", "00405")%><%--Project 일정 변경--%>"
                                    name="leftbtn_07" border="0"></a> <!-- [END] [PLM System 1차개선] Project 일정 변경 화면 Link, 2013-06-10, BoLee -->
                                <%
                                    }
                                %>
                            </td>
                            <td width="7" bgcolor="#e6f0ff"></td>
                        </tr>
                        <tr>
                            <td width="7" height="7"><img src="/plm/portal/images/box_7.gif"></td>
                            <td bgcolor="#e6f0ff"></td>
                            <td width="7" height="7"><img src="/plm/portal/images/box_8.gif"></td>
                        </tr>
                    </table>
                </td>
          </tr>
    <tr>
        <td width="190" height="6"align="center" valign="top">
    </tr>
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
