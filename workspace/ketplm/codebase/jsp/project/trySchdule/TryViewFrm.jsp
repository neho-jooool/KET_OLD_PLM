<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.project.*,
                  e3ps.common.util.*"%>
<%@include file="/jsp/common/context.jsp" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="hashMap" class="java.util.HashMap" scope="request" />
<%
    String oid = StringUtil.checkNull(request.getParameter("oid"));
    String projectOid = oid;
    Object obj= CommonUtil.getObject(oid);

    E3PSTaskData taskData = new E3PSTaskData((E3PSTask)obj);

    MoldProject moldProject = (MoldProject)((E3PSTask)obj).getProject();

    String tagetDay = StringUtil.checkNull(request.getParameter("tagetDay"));;
    String displ = StringUtil.checkNull(request.getParameter("displ"));;

    if(taskData.taskExecStartDate != null){
        tagetDay = DateUtil.getDateString(taskData.taskExecStartDate, "D");
    }else{
        tagetDay = DateUtil.getDateString(taskData.taskPlanStartDate, "D");
    }
    if(taskData.taskExecEndDate != null){
        tagetDay = DateUtil.getDateString(taskData.taskExecEndDate, "D");
    }

    displ = moldProject.getMoldInfo().getItemType();

    String tmpPopUp = "&popup=popup";

%>

<%@page import="e3ps.project.beans.E3PSTaskData"%><html>
<head>
<TITLE><%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%></TITLE>
<script>
function movePopup(oid, conScr){
    var link = document.getElementById("linkFrame");
    var tree = document.getElementById("treeFrame");
    var cont = document.getElementById("bottomRightFrame");

    link.src = "/plm/jsp/project/ProjectLinkFrm.jsp?oid=" + oid + "&popup=popup";
    tree.scr = "/plm/jsp/project/ProjectTree.jsp?oid=" + oid + "&popup=popup";
    cont.src = conScr;
}
</script>
</head>
<!-- NEW Start -->

<frameset rows=50,* framespacing=0 frameborder=0 id=topFrame >
    <frame name="taskTop" noresize id=topFrame frameborder="0" scrolling="no" src="/plm/jsp/project/trySchdule/PopUpTop.jsp">
        <frameset cols=200,* framespacing=0 frameborder=no id=bottomFrame>
            <frame name=bottomLeftFrame noresize frameborder=no scrolling=no src="/plm/jsp/project/trySchdule/Calendar.jsp?tagetDay=<%=tagetDay %><%=tmpPopUp %>" id=bottomLeftFrame>

            <frame name=contName frameborder=no id=bottomRightFrame src="/plm/jsp/project/trySchdule/TryDaily.jsp?tagetDay=<%=tagetDay %>&displ=<%=displ %><%=tmpPopUp %>">
        </frameset>

</frameset>


</html>
