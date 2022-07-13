<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page import="e3ps.project.beans.E3PSProjectSearchHelper"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.project.*,
                  wt.fc.*,
                  ext.ket.project.gate.entity.*,
                  e3ps.common.util.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%@include file="/jsp/common/context.jsp" %>
<%
    String oid = null;
    if(StringUtil.checkString(request.getParameter("oldOid"))){
	    oid = StringUtil.checkNull(request.getParameter("oldOid"));
    }else if(StringUtil.checkString(request.getParameter("oid")) && request.getParameter("oid").indexOf("Project") > 0){
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(request.getParameter("oid"));
	    E3PSProject latestProject = ProjectHelper.manager.getLastProject(project.getMaster());
	    oid = CommonUtil.getOIDString(E3PSProjectHelper.service.getProjectByProjectNo(latestProject.getPjtNo()));
    }else if(StringUtil.checkString(request.getParameter("pjtNo"))){
	    oid = CommonUtil.getOIDString(E3PSProjectHelper.service.getProjectByProjectNo(request.getParameter("pjtNo")));  
    }else{
	    oid = StringUtil.checkNull(request.getParameter("oid"));
    }
    //방어코드
    if(!StringUtil.checkString(oid)){
%>
<script>
alert('해당 프로젝트(또는Task)가\n일정변경 취소 또는 삭제/미등록됨 으로 인해 존재하지 않습니다.\n재 검색후 다시 시도 하시기 바랍니다.');
self.close();
</script>    
<%	     
        return;
    }
    
    String projectOid = oid;
    Object obj= CommonUtil.getObject(oid);
    String target = "";
    String pjtName = "";
    String targetType = StringUtil.checkNull(request.getParameter("targetType"));
    if(obj instanceof ReviewProject){
        target = "ReviewProject";
        ReviewProject project = (ReviewProject)obj;
        if(project.getPjtName() != null){
            pjtName = project.getPjtName();
        }
    }else if( obj instanceof WorkProject){
        target = "WorkProject";
        WorkProject project = (WorkProject)obj;
        if(project.getPjtName() != null){
            pjtName = project.getPjtName();
        }
    }else if( obj instanceof ProductProject){
        target = "Project";
        ProductProject project = (ProductProject)obj;
        if(project.getPjtName() != null){
            pjtName = project.getPjtName();
        }
    }else if( obj instanceof MoldProject){
        target = "MoldProject";
        MoldProject project = (MoldProject)obj;
        if(project.getPjtName() != null){
            pjtName = project.getPjtName();
        }
    }else if ( obj instanceof E3PSTask ) {
	    E3PSTask taskObj = (E3PSTask)obj;
        projectOid = CommonUtil.getOIDString(E3PSProjectHelper.service.getProjectByProjectNo(taskObj.getProject().getPjtNo()));
        if("Gate".equals(taskObj.getTaskType())) {
            target = "TaskGate";
        }else if("신뢰성평가".equals(taskObj.getTaskType())) {
            target = "TaskTrust";
        }else if("DR".equals(taskObj.getTaskType()) && taskObj.getProject() instanceof ProductProject) {
            target = "TaskAssess";
        }else if("Try".equals(taskObj.getTaskType())) {
            target = "Task";
        }else {
            target = "Task";
        }
    }else if ( obj instanceof GateAssessResult ) {
	   E3PSTask taskObj = null;
	   QueryResult qr = PersistenceHelper.manager.navigate((GateAssessResult)obj, "task", GateAssRsltTaskLink.class);
	   
	   if(qr.hasMoreElements()) {
	       taskObj = (E3PSTask)qr.nextElement();
	   }
	   if(taskObj!=null) {
	       oid = CommonUtil.getOIDString(taskObj);
		   projectOid = CommonUtil.getOIDString(E3PSProjectHelper.service.getProjectByProjectNo(taskObj.getProject().getPjtNo()));
	   }
	   target = "TaskGate";
	   
        
    }else if ( obj instanceof AssessSheet ) {
       ProductProject pjtObj = null;
       QueryResult qr = PersistenceHelper.manager.navigate((AssessSheet)obj, "project", ProjectAssSheetLink.class);
       
       if(qr.hasMoreElements()) {
		   pjtObj = (ProductProject)qr.nextElement();
       }
       
       if(pjtObj!=null) {
           oid = CommonUtil.getOIDString(pjtObj);
           //projectOid = CommonUtil.getOIDString(pjtObj);
           projectOid = CommonUtil.getOIDString(E3PSProjectHelper.service.getProjectByProjectNo(pjtObj.getPjtNo()));
       }
       targetType = "FULL";
       //target = "/ext/project/gate/updateProjectAssessForm.do?pjtOid=" + projectOid + "&oid=" + projectOid ;
       target = "/plm/extcore/jsp/project/gate/updateProjectAssessTarget.jsp?pjtOid=" + projectOid + "&oid=" + projectOid ;
    }

    String popup = request.getParameter("popup");
    String tmpPopUp = "";
    if(popup != null && !popup.equals("")) {
        tmpPopUp = "&popup=popup";
    }

    if(!"FULL".equals(targetType)) {
	    target = target + "View";
    }

    String issueType = StringUtil.checkNull(request.getParameter("issueType"));

    if("1".equals(issueType)){
	   target = target + "4";
    }else if("2".equals(issueType)){
        target = target + "2";
    }else if("3".equals(issueType)){
        target = target + "_5";
    }
    
    if("ATFT".equals(targetType)){
	   target = "ProjectExtView9";
    }
    
    if("ISSUE".equals(targetType)){
	   target = "ProjectExtView5";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<TITLE><%=pjtName%></TITLE>
<script>
function movePopup(oid, conScr){
    var link = document.getElementById("linkFrame");
    var tree = document.getElementById("treeFrame");
    var cont = document.getElementById("bottomRightFrame");

    link.src = "/plm/jsp/project/ProjectLinkFrm.jsp?oid=" + oid + "&popup=popup";
    tree.src = "/plm/jsp/project/ProjectTree.jsp?oid=" + oid + "&popup=popup";
    cont.src = conScr;
}
function movePaage(leftSrc, contSrc) {
	var link = document.getElementById("linkFrame");
    var tree = document.getElementById("treeFrame");
    var cont = document.getElementById("bottomRightFrame");

    //link.src = "/plm/jsp/project/ProjectLinkFrm.jsp?oid=" + oid + "&popup=popup";
    tree.src = "/plm/jsp/project/ProjectTree.jsp?oid=" + oid + "&popup=popup";
    cont.src = conScr;
}
window.resizeTo(1200,900);
</script>
</head>
<!-- NEW Start -->
<frameset rows=50,* framespacing=0 frameborder=0 id=topFrame >
    <frame name="taskTop" noresize id=topFrame frameborder="no" scrolling="no" src="/plm/jsp/project/PopUpProjectTop.jsp?oid=<%=oid%>">
        <frameset cols=200,*  frameborder=0 id=bottomFrame >
        <frameset rows="7,*"  name=bottomLeftFrame id=bottomLeftFrame>
            <frame name="link" scrolling="no" id="linkFrame" src="about:blank" noresize>
            <frame name="tree" scrolling="no" id="treeFrame" src="/plm/jsp/project/ProjectTree.jsp?oid=<%=projectOid%><%=tmpPopUp%>&taskOid=<%=oid %>" noresize>
            </frameset>
<%
	if("FULL".equals(targetType)) {
%>
            <frame name=body frameborder="no" id=bottomRightFrame src=<%=target%> noresize>
<%
	}else {
%>
            <frame name=body frameborder="no" id=bottomRightFrame src=/plm/jsp/project/<%=target%>.jsp?oid=<%=oid%><%=tmpPopUp%> noresize>
<%
	}
%>
        </frameset>

</frameset>
<script>

    var treeDiv = document.getElementById("tree");
    var imgDiv = document.getElementById("link");
    //alert(treeDiv.offsetHeight +'\t' + imgDiv.offsetHeight + '\t' + document.body.clientHeight);
    h1 = document.height -imgDiv.offsetHeight;
    treeDiv.height = h1;
</script>
</html>