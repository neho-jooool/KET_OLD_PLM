<%@ page contentType="text/html;charset=UTF-8" buffer="16kb"%>
<%@page import="wt.fc.*,
                java.util.*,
                java.text.*" %>
<%@page import = "e3ps.project.*,
                  e3ps.project.beans.*,
                  e3ps.common.util.*"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="e3ps.project.outputtype.ProjectOutPutType"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/common/context.jsp" %>
<%!

    /* public Hashtable getIssueTask(E3PSProject project)throws Exception{

        QueryResult rs = ProjectTaskHelper.manager.getIssueTask(project);
        Hashtable hash = new Hashtable();
        while(rs.hasMoreElements()){
            Object o[] = (Object[])rs.nextElement();
            E3PSTask task = (E3PSTask)o[0];
            String oid = CommonUtil.getOIDString(task);
            hash.put(oid, oid);
        }
        return hash;

    } */
%>

<%
    int popInt = 497 ;
    int pullInt = 520;
    boolean isWBSUICheck = false;
    String oid = StringUtil.checkNull(request.getParameter("oid"));

    String command = StringUtil.checkNull(request.getParameter("command"));
    String wbsCopyOid = StringUtil.checkNull(request.getParameter("wbsCopyOid"));

    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    if("wbsCopy".equals(command)){
        TemplateProject tp = (TemplateProject)CommonUtil.getObject(wbsCopyOid);
        if(tp != null){
            try{
            project = ProjectHelper.manager.wbsCopyProjectTask(tp,project);
            }catch(Exception e){
        	Kogger.error(e);
            }
        }
        if(project != null){
        %>
            <script>
                alert("<%=messageService.getString("e3ps.message.ket_message", "00552") %><%--WBS Copy 완료--%>");
            </script>
        <%
        }
    }

    //int issueC = ProjectHelper.manager.getIssueCount(project);
    //boolean isProjectIssue = issueC > 0;


    //Hashtable issueH = getIssueTask(project);
    int pjtState = project.getPjtState();

    String title = "";
    String treeTitle = "";
    boolean isWbs = false;
    boolean isFirstVersion = false;
    boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
    boolean isPm = ProjectUserHelper.manager.isPM(project);

    String wbsType = "";
    if(project instanceof MoldProject){
        title = ((MoldProject)project).getMoldInfo().getPartName();
        if(title == null){
            title = "";
        }
        treeTitle = project.getPjtNo() + "(" + title + ")";
    }else{
        isWbs = true;
        if(project instanceof ReviewProject){
            wbsType = "1";
        }else{
            wbsType = "2";
        }
        if(project.getPjtHistory() == 0){
            isFirstVersion = true;
        }

        title = project.getPjtName();
        treeTitle = project.getPjtNo() + "(" + project.getPjtName() + ")";
    }
    int wbsProjectType = project.getPjtType();
    String color = "#B4B4B4";

    if(project.getLifeCycleState().toString().equals("COMPLETED")){
        color ="74C600";
    }

    else if(pjtState == ProjectStateFlag.PROJECT_STATE_DELAY){
        color ="FF3300";

    }

    else if(pjtState == ProjectStateFlag.PROJECT_STATE_DELAY_PROGRESS){
        color ="ORANGE";

    }

    else if(pjtState == ProjectStateFlag.PROJECT_STATE_PROGRESS){
        color = "0033CC";
    }

    long sl = -1;
    String taskOid = StringUtil.checkNull(request.getParameter("taskOid"));
    if(taskOid != null && taskOid.length() > 0){
        sl = CommonUtil.getOIDLongValue(taskOid);
    }


    String pjtStartFont = "<FONT color='" + color + "'>";

    String startFONT = "<FONT color='" + color + "'>";
    String endFONT = "</FONT>";

    String popup = request.getParameter("popup");
    String tmpPopUp = "";
    if(popup != null && !popup.equals("")) {
        tmpPopUp = "&popup=popup";
    }
    
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
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style>
<!--
#scrollbox {
	overflow-x: auto;
	overflow-y: auto;
}
-->
</style>
<%@include file="/jsp/common/multicombo.jsp" %>
<script src="/plm/portal/js/newtree.js" type="text/javascript"></script>
<script language="JavaScript">
<!--
    function manageTask(oid){
        var url = "/plm/jsp/project/manage/ManageProjectTaskFrm.jsp?oid="+oid;
        openOtherName(url,"manageTask","950","800","status=1,scrollbars=no,resizable=1");
    }
    
    function manageTaskApplet(oid){
        var url = "/plm/jsp/project/scheduleEditorCnt.jsp?oid="+oid;
        openOtherName(url,"manageTaskApplet","250","400","status=yes,scrollbars=no,resizable=yes");
    }
    function onMenu(i){
        if(document.getElementById('menu'+i).style.color!='#b4dcfb'){
            document.getElementById('menu'+i).style.color="#b4dcfa";
        }
    }
    
    function outMenu(i){
        if(document.getElementById('menu'+i).style.color!='#b4dcfb'){
            document.getElementById('menu'+i).style.color="#000000";
        }
    }
    
    function excelDown(){
        document.forms[0].method = "post";
        document.forms[0].command.value = "excelDown";
        document.forms[0].action = "/plm/servlet/e3ps/ProjectServlet";
        document.forms[0].submit();
    }
    
    function displayChange(type) {
        if ( type == '0' ) {
            normalTreeDisplay.style.display = "block";
            milestoneTreeDisplay.style.display = "none";
            outputTreeDisplay.style.display = "none";
            menu1.style.color="#ff3c00"
            menu2.style.color="#08476f"
            menu3.style.color="#08476f"
        } else if(type == '1') {
            normalTreeDisplay.style.display = "none";
            milestoneTreeDisplay.style.display = "none";
            outputTreeDisplay.style.display = "none";
        } else if(type =='2'){
            normalTreeDisplay.style.display = "none";
            milestoneTreeDisplay.style.display = "none";
            outputTreeDisplay.style.display = "none";
        } else if(type =='3'){
            normalTreeDisplay.style.display = "none";
            milestoneTreeDisplay.style.display = "block";
            outputTreeDisplay.style.display = "none";
            menu1.style.color="#08476f"
            menu2.style.color="#ff3c00"
            menu3.style.color="#08476f"
        } else if(type =='4'){
            normalTreeDisplay.style.display = "none";
            milestoneTreeDisplay.style.display = "none";
            outputTreeDisplay.style.display = "block";
            menu1.style.color="#08476f"
            menu2.style.color="#08476f"
            menu3.style.color="#ff3c00"
        }
    }

    function onProjectTemplate(obj) {

        var url = "/plm/jsp/project/template/ProjectTempSelectPopUp.jsp?mode=one&wType=1&selectReview=<%=wbsProjectType%>";
        if(obj == 2 ){
            url = "/plm/jsp/project/template/ProjectTempSelectPopUp.jsp?mode=one&wType=4&selectReview=<%=wbsProjectType%>";
        }

        attache = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=840px; dialogHeight:530px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }

        acceptProjectTemplate(attache);
    }

    function acceptProjectTemplate(objArr) {
        if(objArr.length == 0) {
            return;
        }
        var form = document.forms[0];

        var trArr;
        for(var i = 0; i < objArr.length; i++) {
            trArr = objArr[i];
            form.wbsCopy.value = trArr[1];
            form.wbsCopyOid.value = trArr[0];
            form.wbsCopy.title = trArr[1];
        }

    }
    function wbsDelete(){
        document.forms[0].wbsCopy.value= "";
        document.forms[0].wbsCopyOid.value= "";
    }
    function wbsCopyAction() {
        if(document.forms[0].wbsCopy.value == ""){
            alert("<%=messageService.getString("e3ps.message.ket_message", "00566") %><%--WBS를 선택 해 주십시오--%>");
            return;
        }

        if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03283") %><%--WBS Copy를 하시겠습니까? (관리자 권한)--%>")) {
            return;
        }
        if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03282") %><%--WBS Copy를 진행하면 해당 프로젝트의 전체 Task가 삭제됩니다. 계속 진행 하시겠습니까?--%>")) {
            return;
        }

        document.forms[0].method = "post";
        document.forms[0].command.value = "wbsCopy";
        document.forms[0].submit();
    }


    function viewImage(){
        var url = "/plm/jsp/project/ProjectStateImageView.jsp";
        openOtherName(url,"","170","170","status=no,scrollbars=no,resizable=yes");
    }
    
    function reloadTree(){
        document.location.reload();
    }
    
    function openChangeProjectSchedule(oid) {
        <%
            if ( isMold && isChild ) {
        %>
            var url="/plm/jsp/project/CreateMoldProject.jsp?oid=" + oid;
            openOtherName(url, changePopupNameForIE9(oid), 730, 680, "status=yes,scrollbars=auto,resizable=yes");
        <%
            }
            else {
        %>
            var screenWidth = screen.availWidth;
            var screenHeight = screen.availHeight;
            var url = "/plm/servlet/e3ps/ProjectScheduleServlet?oid=" + oid + "&popup=<%= StringUtil.checkNull(popup) %>&cmd=search&width=" + screenWidth + "&height=" + screenHeight;
            openOtherName(url, changePopupNameForIE9(oid), screenWidth, screenHeight, "status=no,scrollbars=yes,resizable=yes");
        <%
            }
        %>
    }
-->
</script>

<style>
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 5px;
    margin-bottom: 5px;
}
-->
</style>
</head>
<%@include file="/jsp/common/processing.html"%>
<script type="text/javascript">
//showProcessing();
$('body').loadMask('loading...');
</script>
<body onLoad="javascript:screenSize();">
    <form name="projectTree" method="post">
        <input type="hidden" name="command"> 
        <input type="hidden" name="oid" value="<%=oid%>"> 
        <input type=hidden name=popup value='<%=popup%>'>
        <div class="contents-wrap" style="margin-bottom:3px">
            <div style="width:100%">
                <div class="tree-box box-size">
                    <div style="text-align:center">
                        <span class="tree-num-color"><%=project.getPjtNo()%></span>
                        <span class="r-space3"><img src="/plm/portal/images/ico_tree_excel.png" onclick="excelDown()" style="cursor: pointer;"/></span>
                        <span class="r-space3"><img src="/plm/portal/images/ico_tree_refresh.png" onclick="reloadTree()" style="cursor: pointer;"/></span>
                        <span class="r-space3"><img src="/plm/portal/images/ico_tree_task.png" onclick="openChangeProjectSchedule('<%=oid%>')" style="cursor: pointer;"/></span>
                    </div>
                    <div style="background:url(/plm/portal/images/bg_ui_tree.gif) repeat-x 0 50%;padding:5px 0 7px 0"></div>
                    <div class="tree-text">
                        <%if(CommonUtil.isMember("SQ인증감사")){ %>
                        <a id="menu2" href="javascript:displayChange('3');"><%=messageService.getString("e3ps.message.ket_message", "00323")%><!-- 마일스톤 --></a>
                        <%}else{ %>
                        <a id="menu1" href="javascript:displayChange('0');" style="color:#ff3c00">WBS</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                        <a id="menu2" href="javascript:displayChange('3');"><%=messageService.getString("e3ps.message.ket_message", "00323")%><!-- 마일스톤 --></a>&nbsp;&nbsp;|&nbsp;&nbsp;
                        <a id="menu3" href="javascript:displayChange('4');"><%=messageService.getString("e3ps.message.ket_message", "01716")%><%--산출물--%></a>
                        <%} %>
                    </div>
                </div>
            </div>
        </div>
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="top: 20px">
            <tr>
                <td width="100%" align="center" valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td valign="top">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr id="normalTreeDisplay" <%if(CommonUtil.isMember("SQ인증감사")){ %> style="display: none" <%}else{%> style="display: block" <%} %> >
                                        <td>
                                            <%
                                                if (popup != null && !popup.equals("")) {
                                            %>
                                            <DIV id="scrollbox" style="width:190px;height:<%=popInt%>;overflow:auto">
                                                <table oncontextmenu="return false" onselectstart="return false" id="treeTable"
                                                    ondragstart="return false" cellSpacing="0" cellPadding="0" border="0">
                                                </table>
                                            </div> 
                                            <%
                                                 } else {
                                            %>
                                            <DIV id="scrollbox" style="width:190px;height:<%=pullInt%>;overflow:auto">
                                                <table oncontextmenu="return false" onselectstart="return false" id="treeTable"
                                                    ondragstart="return false" cellSpacing="0" cellPadding="0" border="0">
                                                </table>
                                            </div> 
                                            <%
                                                 }
                                            %>
                                        </td>
                                    </tr>
                                    <tr id="milestoneTreeDisplay" <%if(CommonUtil.isMember("SQ인증감사")){ %> style="display: block" <%}else{%> style="display: none" <%} %> >
                                        <td valign="top">
                                            <%
                                                if (popup != null && !popup.equals("")) {
                                            %>
                                            <DIV id="scrollbox3" style="width:190px;height:<%=popInt%>;overflow:auto">
                                                <table oncontextmenu="return false" onselectstart="return false" id=milestoneTreeTable
                                                    ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
                                            </div> 
                                            <%
                                                 } else {
                                            %>
                                            <DIV id="scrollbox3" style="width:190px;height:<%=pullInt%>;overflow:auto">
                                                <table oncontextmenu="return false" onselectstart="return false" id=milestoneTreeTable
                                                    ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
                                            </div> 
                                            <%
                                                 }
                                            %>
                                        </td>
                                    </tr>
                                    <tr id="outputTreeDisplay" style="display: none">
                                        <td valign="top">
                                            <%
                                                if (popup != null && !popup.equals("")) {
                                            %>
                                            <DIV id="scrollbox4" style="width:190px;height:<%=popInt%>;overflow:auto">
                                                <table oncontextmenu="return false" onselectstart="return false" id=outputTreeTable
                                                    ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
                                            </div> 
                                            <%
                                                } else {
                                            %>
                                            <DIV id="scrollbox4" style="width:190px;height:<%=pullInt%>;overflow:auto">
                                                <table oncontextmenu="return false" onselectstart="return false" id=outputTreeTable
                                                    ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
                                            </div> 
                                            <%
                                                }
                                            %>
                                        </td>
                                    </tr>
                                </table></td>
                        </tr>


                    </table>
                </td>
                <td width="1" bgcolor="#c6c6c6"></td>
                <td width="9" valign="top">
                    <!--img src="/plm/portal/images/arrow.gif" width="9" height="30"-->
                </td>
            </tr>
        </table>
        <SCRIPT>

    //alert("size = " + document.body.clientHeight);
    $(window).bind('resize', function (){
        //alert('resize');
        screenSize();
    });

    function screenSize(){
       /* var topHeight = 95+36;
	   var topHeight = 190+36;
	   var topHeight = 190; */
	   var topHeight = 75;


        if(document.body.clientHeight < topHeight){
            return;
        }
        //alert(document.body.clientHeight+" - "+topHeight);
        document.getElementById("scrollbox").style.height = document.body.clientHeight - topHeight;
        document.getElementById("scrollbox3").style.height = document.body.clientHeight - topHeight;
        document.getElementById("scrollbox4").style.height = document.body.clientHeight - topHeight;
    }

    var target = eval("parent.parent.body");
    var tree = new Tree("treeTable","<%=pjtStartFont%><%=treeTitle%><%=endFONT%>","/plm/portal/icon/tree/project");
    tree.selectionMode = tree.SINGLE_TREE_SELECTION;
    tree.treeSelectionListener = function(node, event){
        this.selectNode(node, event);
        if ( node.get("nodeType") == "project" ) {
            <%if(project instanceof ReviewProject){%>
            target.document.location.href = "/plm/jsp/project/ReviewProjectView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
            <%}else if(project instanceof ProductProject){%>
            target.document.location.href = "/plm/jsp/project/ProjectExtView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
            <%}else if(project instanceof MoldProject) {%>
            target.document.location.href = "/plm/jsp/project/MoldProjectView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
            <%}else if(project instanceof WorkProject) {%>
            target.document.location.href = "/plm/jsp/project/WorkProjectView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
            <%}%>
        } else if ( node.get("nodeType") == "task" ) {
        	if ( node.get("taskType") == "Gate" ) {
                target.document.location.href = "/plm/jsp/project/TaskGateView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
        	}else if ( node.get("taskType") == "신뢰성평가" ) {
                target.document.location.href = "/plm/jsp/project/TaskTrustView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
            }else if ( node.get("taskType") == "DR" ) {
            	<%if(project instanceof ProductProject){%>
            	target.document.location.href = "/plm/jsp/project/TaskAssessView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
                <%}else{%>
                target.document.location.href = "/plm/jsp/project/TaskView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
                <%}%>
            }else {
                target.document.location.href = "/plm/jsp/project/TaskView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
            }
        }
    }
    <%-- tree.root.isIssue = <%=isProjectIssue%>; --%>
    tree.root.put("oid","<%=oid%>");
    tree.root.put("nodeType","project");
    var sNode;

<%
    Calendar currentDate = Calendar.getInstance();
    Calendar endDate = Calendar.getInstance();
    ProjectTreeNode root = (ProjectTreeNode)ProjectScheduleHelper.manager.getRoot(project, false);
    Vector list = new Vector();
    makeVector(root, list);
    settingTaskColor(list);
    //Kogger.debug("list = " + list.size());
    Hashtable sortHash = new Hashtable();
    Hashtable sortHashOEM = new Hashtable();
    String lang = messageService.getLocale().toString();
    
    for(int i = 0; i < list.size(); i++){
	    
        ProjectTreeNode node = (ProjectTreeNode)list.get(i);
        ProjectTreeNodeData td = (ProjectTreeNodeData)node.getUserObject();
        if(td.getData() instanceof TemplateProject){
            /* TemplateProject 이다*/
            continue;
        }
        E3PSTask childTask = (E3PSTask)td.getData();

        boolean isOption = childTask.isOptionType();
        ProjectTreeNode pnode = (ProjectTreeNode)node.getParent();
        ProjectTreeNodeData ptd = (ProjectTreeNodeData)pnode.getUserObject();
        String childTaskOID = CommonUtil.getOIDString( childTask);
        //boolean isIssue = issueH.containsKey(childTaskOID);

        sortHash.put(childTaskOID, new Integer(i));
        sortHashOEM.put(childTaskOID, new Integer(i));

        long childTaskOIDValue = CommonUtil.getOIDLongValue( childTask);
        long parentTaskOIDValue = 0;
        if(ptd.getData() instanceof E3PSProject){
            parentTaskOIDValue = 0;
        }else{
            parentTaskOIDValue = CommonUtil.getOIDLongValue((E3PSTask)ptd.getData());
        }

        String nodeName = StringUtil.htmlCharEncode(childTask.getTaskName());
        String nodeNameEn = StringUtil.htmlCharEncode(childTask.getTaskNameEn());
        boolean isEnLang = "en".equals(lang) && !StringUtil.isEmpty(nodeNameEn);
        if(isEnLang){
            nodeName = nodeNameEn; 
        }
        String taskType = StringUtil.htmlCharEncode(childTask.getTaskType());
%>
        var node<%=childTaskOIDValue%> = new TreeNode("<%=getTaskStateFont(node)%>" + unescape("<%=nodeName%>") + "<%=endFONT%>");
        node<%=childTaskOIDValue%>.put("oid","<%=childTaskOID%>");
        node<%=childTaskOIDValue%>.put("nodeType","task");
        node<%=childTaskOIDValue%>.put("taskType","<%=taskType%>");
        <%-- node<%=childTaskOIDValue%>.isIssue = <%=isIssue%>; --%>
    <%if(isOption){%>
        node<%=childTaskOIDValue%>.icon = "option";
    <%}%>
    <%if(  parentTaskOIDValue == 0){%>
            tree.root.add(node<%=childTaskOIDValue%>);
    <%}else{%>
        node<%=parentTaskOIDValue%>.add(node<%=childTaskOIDValue%>);
    <%}%>

    <%if(childTaskOIDValue == sl){
        //Kogger.debug("okkkkkkkkkkkkk......gggg");
    %>

    sNode = node<%=childTaskOIDValue%>;
    <%}%>
<%}%>
   tree.expandAll();
   if(sNode != null){
        tree.selectNode(sNode);
   }else{
        tree.selectNode(tree.root);
   }
   tree.repaint();



    var tree = new Tree("milestoneTreeTable","<%=treeTitle%>","/plm/portal/icon/tree/project");
    tree.selectionMode = tree.SINGLE_TREE_SELECTION;
    tree.treeSelectionListener = function(node, event){
        this.selectNode(node, event);
        if ( node.get("nodeType") == "project" ) {
            <%if(project instanceof ReviewProject){%>
            target.document.location.href = "/plm/jsp/project/ReviewProjectView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
            <%}else if(project instanceof ProductProject){%>
            target.document.location.href = "/plm/jsp/project/ProjectExtView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
             <%}else if(project instanceof MoldProject) {%>
            target.document.location.href = "/plm/jsp/project/MoldProjectView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
            <%}else if(project instanceof WorkProject) {%>
            target.document.location.href = "/plm/jsp/project/WorkProjectView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
            <%}%>
        } else if ( node.get("nodeType") == "task" ) {
            if ( node.get("taskType") == "Gate" ) {
                target.document.location.href = "/plm/jsp/project/TaskGateView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
            }else if ( node.get("taskType") == "신뢰성평가" ) {
                target.document.location.href = "/plm/jsp/project/TaskTrustView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
            }else if ( node.get("taskType") == "DR" ) {
                <%if(project instanceof ProductProject){%>
                target.document.location.href = "/plm/jsp/project/TaskAssessView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
                <%}else{%>
                target.document.location.href = "/plm/jsp/project/TaskView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
                <%}%>
            }else {
                target.document.location.href = "/plm/jsp/project/TaskView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
            }
        }
    }
    tree.root.put("oid","<%=oid%>");
    tree.root.put("nodeType","project");
<%
    for(int i = 0; i < list.size(); i++){
        ProjectTreeNode node = (ProjectTreeNode)list.get(i);
        ProjectTreeNodeData td = (ProjectTreeNodeData)node.getUserObject();
        if(td.getData() instanceof E3PSProject){
            /* TemplateProject 이다*/
            continue;
        }

        E3PSTask childTask = (E3PSTask)td.getData();

        boolean isOption = childTask.isOptionType();

        //Kogger.debug("childTask.isMileStone==>"+ childTask.isMileStone());
        if(childTask.isMileStone()) {
            color = "black";
            startFONT = "<FONT color='" + color + "'>";

            ProjectTreeNode pnode = (ProjectTreeNode)node.getParent();
            ProjectTreeNodeData ptd = (ProjectTreeNodeData)pnode.getUserObject();
            String childTaskOID = CommonUtil.getOIDString( childTask);
            //boolean isIssue = issueH.containsKey(childTaskOID);

            long childTaskOIDValue = CommonUtil.getOIDLongValue( childTask);
            long parentTaskOIDValue = 0;

            String taskEndDate = "";
            E3PSTaskData taskData = new E3PSTaskData(childTask);
            if(taskData.taskExecEndDate != null){
                taskEndDate = DateUtil.getTimeFormat(taskData.taskExecEndDate, "yy/MM/dd");
            }else{
                taskEndDate = DateUtil.getTimeFormat(taskData.taskPlanEndDate, "yy/MM/dd");
            }

%>
            var node<%=childTaskOIDValue%> = new TreeNode("<%=startFONT%>"+ unescape("<%=childTask.getTaskName()%> [<%=taskEndDate%>]")+"<%=endFONT%>");
            node<%=childTaskOIDValue%>.put("oid","<%=childTaskOID%>");
            node<%=childTaskOIDValue%>.put("nodeType","task");
            <%-- node<%=childTaskOIDValue%>.isIssue = <%=isIssue%>; --%>
            <%if(isOption){%>
                node<%=childTaskOIDValue%>.icon = "option";
            <%}%>

            tree.root.add(node<%=childTaskOIDValue%>);

<%}}%>
   tree.expandAll();
   tree.repaint();

   //output tree

    var tree = new Tree("outputTreeTable","<%=pjtStartFont%><%=treeTitle%><%=endFONT%>","/plm/portal/icon/tree/project");
    tree.selectionMode = tree.SINGLE_TREE_SELECTION;
    tree.treeSelectionListener = function(node, event){
        this.selectNode(node, event);
        if ( node.get("nodeType") == "project" ) {
            target.document.location.href = "/plm/servlet/e3ps/SearchProjectOutputServlet?oid="+node.get("oid")+"&type=normal" + "<%=tmpPopUp%>";
        } else if ( node.get("nodeType") == "task" ) {
            target.document.location.href = "/plm/servlet/e3ps/SearchProjectOutputServlet?oid="+node.get("oid")+"&type=nomal&subAll=all&taskType=task" + "<%=tmpPopUp%>";
        }
    }
    tree.root.put("oid","<%=oid%>");
    tree.root.put("nodeType","project");
<%
    for(int i = 0; i < list.size(); i++){
        ProjectTreeNode node = (ProjectTreeNode)list.get(i);
        ProjectTreeNodeData td = (ProjectTreeNodeData)node.getUserObject();
        if(td.getData() instanceof TemplateProject){
            /* TemplateProject 이다*/
            continue;
        }
        E3PSTask childTask = (E3PSTask)td.getData();

        boolean isOption = childTask.isOptionType();

        ProjectTreeNode pnode = (ProjectTreeNode)node.getParent();
        ProjectTreeNodeData ptd = (ProjectTreeNodeData)pnode.getUserObject();
        String childTaskOID = CommonUtil.getOIDString( childTask);
        //boolean isIssue = issueH.containsKey(childTaskOID);
        sortHash.put(childTaskOID, new Integer(i));
        sortHashOEM.put(childTaskOID, new Integer(i));

        long childTaskOIDValue = CommonUtil.getOIDLongValue( childTask);
        long parentTaskOIDValue = 0;
        if(ptd.getData() instanceof E3PSProject){
            parentTaskOIDValue = 0;
        }else{
            parentTaskOIDValue = CommonUtil.getOIDLongValue((E3PSTask)ptd.getData());
        }

        String nodeName = StringUtil.htmlCharEncode(childTask.getTaskName());
    %>
        var node<%=childTaskOIDValue%> = new TreeNode("<%=getTaskStateFont(node)%>" + unescape("<%=nodeName%>") + "<%=endFONT%>");
        node<%=childTaskOIDValue%>.put("oid","<%=childTaskOID%>");
        node<%=childTaskOIDValue%>.put("nodeType","task");
        <%-- node<%=childTaskOIDValue%>.isIssue = <%=isIssue%>; --%>

    <%if(isOption){%>
        node<%=childTaskOIDValue%>.icon = "option";
    <%}%>

    <%if(  parentTaskOIDValue == 0){%>
            tree.root.add(node<%=childTaskOIDValue%>);
    <%}else{%>
        node<%=parentTaskOIDValue%>.add(node<%=childTaskOIDValue%>);
    <%}%>


<%}%>
   tree.expandAll();
   tree.repaint();
   $('body').unLoadMask();
</SCRIPT>
</form>
</body>
</html>
<%!public void makeTypeTree(Object o, Vector vector, TemplateProject project, ProjectOutPutTypeComparator comparator) throws Exception {

	vector.add(o);

	if (o instanceof ProjectOutPutType) {

	    ProjectOutPutType pt = (ProjectOutPutType) o;
	    QuerySpec spec = ProjectOutPutTypeHelper.getCodeQuerySpec(pt);
	    QueryResult qr = PersistenceHelper.manager.find(spec);

	    if (qr.size() == 0) {
		QuerySpec sp = ProjectOutputHelper.getProjectOutputSpec(pt, project);
		QueryResult rs = PersistenceHelper.manager.find(sp);
		Vector v = new Vector();
		while (rs.hasMoreElements()) {
		    v.add(rs.nextElement());
		}
		Collections.sort(v, comparator);

		for (int i = 0; i < v.size(); i++) {
		    makeTypeTree(v.get(i), vector, project, comparator);
		}
		return;
	    }

	    while (qr.hasMoreElements()) {

		Object[] obj = (Object[]) qr.nextElement();
		ProjectOutPutType suboutputtype = (ProjectOutPutType) obj[0];
		makeTypeTree(suboutputtype, vector, project, comparator);
	    }
	}

    }

    private String getTaskStateFont(E3PSTask task) throws Exception {

	//E3PSTaskData E3PSTaskData = new E3PSTaskData(task);

	if (task.getTaskState() == ProjectStateFlag.TASK_STATE_COMPLETE) {
	    return "<FONT color='74C600'>";
	}

	if (E3PSTaskData.getPreferComp(task) < 15) {

	    return "<FONT color='0033CC'>";
	}

	if (E3PSTaskData.getDifferDateGap(task) >= 0) {
	    return "<FONT color='0033CC'>";//blue
	} else {
	    return "<FONT color='FF3300'>";//red
	}
    }

    private String getTaskStateFont(ProjectTreeNode node) {
	ProjectTreeNodeData td = (ProjectTreeNodeData) node.getUserObject();
	E3PSTask task = (E3PSTask) td.getData();
	if (task.getTaskState() == ProjectStateFlag.TASK_STATE_COMPLETE) {
	    return "<FONT color='74C600'>";
	}

	Integer tem = (Integer) node.tempObj;

	int state = tem.intValue();

	//Kogger.debug("state ========= " + state);
	if (state == E3PSTaskData.STATE_BAR_DELAY) {

	    return "<FONT color='FF3300'>";
	}

	if (state == E3PSTaskData.STATE_BAR_EXDELAY) {
	    return "<FONT color='ORANGE'>";
	}
	if (state == E3PSTaskData.STATE_BAR_NORMAL) {
	    return "<FONT color='0033CC'>";//blue
	}

	return "<FONT color='0033CC'>";

    }

    public void makeVector(ProjectTreeNode node, Vector vector) {

	vector.add(node);
	for (int i = 0; i < node.getChildCount(); i++) {
	    makeVector((ProjectTreeNode) node.getChildAt(i), vector);
	}

    }

    public void settingTaskColor(Vector nodeList) {

	for (int i = nodeList.size() - 1; i >= 0; i--) {

	    ProjectTreeNode node = (ProjectTreeNode) nodeList.get(i);
	    ProjectTreeNodeData td = (ProjectTreeNodeData) node.getUserObject();

	    if (node.getChildCount() == 0) {

		if (td.getData() instanceof E3PSTask) {
		    E3PSTask childTask = (E3PSTask) td.getData();
		    int state = E3PSTaskData.getStateBarType(childTask);
		    node.setTempObj(new Integer(state), false);
		}
	    } else {

		int state = -1;
		if (!(td.getData() instanceof E3PSTask)) {
		    continue;
		}
		state = E3PSTaskData.getDelayType(node, (E3PSTask) td.getData());
		node.setTempObj(new Integer(state), false);
	    }
	}
    }%>