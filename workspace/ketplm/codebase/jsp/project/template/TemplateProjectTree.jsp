<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.text.*,
						java.util.*" %>
<%@page import="wt.fc.*,
						wt.lifecycle.State,
						wt.query.QuerySpec,
						wt.query.SearchCondition"%>
<%@page import = "e3ps.common.util.*,
						e3ps.project.*,
						e3ps.project.beans.*,
						e3ps.project.outputtype.OEMPlan,
						e3ps.project.outputtype.OEMProjectType,
						e3ps.project.outputtype.ProjectOutPutType
						"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>


<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%
	String oid = CharUtil.E2K(request.getParameter("oid"));
	TemplateProject project = (TemplateProject)CommonUtil.getObject(oid);
	boolean isInWork = (project.getLifeCycleState()==State.INWORK)||(project.getLifeCycleState().toString()=="REWORK");
	String popup = request.getParameter("popup");
	String tmpPopUp = "";
	if(popup != null && !popup.equals("")) {
		tmpPopUp = "&popup=popup";
	}

	String color = "black";

	String startFONT = "<FONT color='" + color + "'>";
	String endFONT = "</FONT>";

	long sl = -1;
	String taskOid = StringUtil.checkNull(request.getParameter("taskOid"));
	if(taskOid != null && taskOid.length() > 0){
		sl = CommonUtil.getOIDLongValue(taskOid);
	}
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script src="/plm/portal/js/script.js" type="text/javascript"></script>
<script src="/plm/portal/js/newtree.js" type="text/javascript"></script>
<script src="/plm/portal/js/common.js" type="text/javascript"></script>
<script language=JavaScript src="/plm/portal/js/jquery-1.3.2.min.js"></SCRIPT>
<script language="JavaScript">
<!--

function manageTask(oid){
	var url = "/plm/jsp/project/manage/ManageTemplateProjectTaskFrm.jsp?oid="+oid;
	openOtherName(url,"manageTask","900","700","status=yes,scrollbars=no,resizable=yes");
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

function displayChange(type) {
	if ( type == '0' ) {
		normalTreeDisplay.style.display = "block";
		psoTreeDisplay.style.display = "none";
		<%if(project.getOemType()!=null){%>
		oemTreeDisplay.style.display = "none";
		<%}%>
		milestoneTreeDisplay.style.display = "none";
		outputTreeDisplay.style.display = "none";
		menu1.style.color="#b4dcfb"
		menu2.style.color="#000000"
		menu3.style.color="#000000"
	} else if(type == '1') {
		normalTreeDisplay.style.display = "none";
		psoTreeDisplay.style.display = "block";
		<%if(project.getOemType()!=null){%>
		oemTreeDisplay.style.display = "none";
		<%}%>
		milestoneTreeDisplay.style.display = "none";
		outputTreeDisplay.style.display = "none";
	} else if(type =='2'){
		normalTreeDisplay.style.display = "none";
		psoTreeDisplay.style.display = "none";
		<%if(project.getOemType()!=null){%>
		oemTreeDisplay.style.display = "block";
		<%}%>
		milestoneTreeDisplay.style.display = "none";
		outputTreeDisplay.style.display = "none";
	} else if(type =='3'){
		normalTreeDisplay.style.display = "none";
		psoTreeDisplay.style.display = "none";
		<%if(project.getOemType()!=null){%>
		oemTreeDisplay.style.display = "none";
		<%}%>
		milestoneTreeDisplay.style.display = "block";
		outputTreeDisplay.style.display = "none";
		menu1.style.color="#000000"
		menu2.style.color="#b4dcfb"
		menu3.style.color="#000000"
	} else if(type =='4'){
		normalTreeDisplay.style.display = "none";
		psoTreeDisplay.style.display = "none";
		<%if(project.getOemType()!=null){%>
		oemTreeDisplay.style.display = "none";
		<%}%>
		milestoneTreeDisplay.style.display = "none";
		outputTreeDisplay.style.display = "block";
		menu1.style.color="#000000"
		menu2.style.color="#000000"
		menu3.style.color="#b4dcfb"
	}
}

	var target;
<%
	if(popup != null && popup.equals("popup")) {
%>
	target = eval("parent.body");
<%
	} else {
%>
	target = eval("parent.parent.document.frames['contName']");
<%
	}
%>

function outputtypeView() {
alert("ggg");
	target.document.location.href = "/plm/jsp/project/template/TemplateTaskView.jsp";
}

function excelDown(){
	document.forms[0].method = "post";
	document.forms[0].command.value = "excelDown";
	document.forms[0].action = "/plm/servlet/e3ps/TemplateProjectServlet";
	document.forms[0].submit();
}
-->
</script>
</head>

<body onLoad="javascript:screenSize();">
<form name="projectTree" method="post">
<!-- <input type="hidden" name="command"> -->
<input type="hidden" name="oid" value="<%=oid%>">

<table width="200" height="100%" border="0" cellspacing="0" cellpadding="0" >
    <tr>
      <td width="190" align="center" valign="top"><table width="170"  border="0" cellspacing="0" cellpadding="0">

          <tr>
            <td height="60" align="center" valign="top">
	            <table width="170" height="56" border="0" cellspacing="0" cellpadding="0">
	                <tr>
	                  <td width="7" height="7"><img src="/plm/portal/images/box_1.gif" width="7" height="7"></td>
	                  <td bgcolor="#e9e9e9"></td>
	                  <td width="7" height="7"><img src="/plm/portal/images/box_2.gif" width="7" height="7"></td>
	                </tr>
	                <tr>
	                  <td width="7" bgcolor="#e9e9e9"></td>
	                  <td align="center" valign="middle" bgcolor="#e9e9e9" style="word-break:break-all;">
		                <table border="0" cellspacing="0" cellpadding="0">
	                      <tr>
	                        <td align="center"><b><%=project.getPjtName()%></b></td>
	                        <!--
	                        <td width="3">&nbsp;</td>
	                        <td><img src="/plm/portal/images/iocn_excel.png" onclick="javascript:excelDown();" alt="excel down" name="leftbtn_02" border="0"></td>
	                         -->
	                      </tr>
	                    </table>
	                  </td>
	                  <td width="7" bgcolor="#e9e9e9"></td>
	                </tr>
	                <tr>
	                  <td width="7" height="7"><img src="/plm/portal/images/box_3.gif" width="7" height="7"></td>
	                  <td bgcolor="#e9e9e9"></td>
	                  <td width="7" height="7"><img src="/plm/portal/images/box_4.gif" width="7" height="7"></td>
	                </tr>
	              </table>
              </td>
          </tr>
          <tr>
          	<td>
          		<table width="170" align="center">
          			<tr>
            			<td height="28" align="center" valign="middle" bgcolor="#484848"><a id="menu1" href="javascript:displayChange('0');" class="btn_blue" onmouseover="javascript:onMenu('1');" onmouseout="javascript:outMenu('1');" style="color:#b4dcfb"><%=messageService.getString("e3ps.message.ket_message", "01118") %><%--기본--%></a>&nbsp;I&nbsp;<a href="javascript:displayChange('3');" class="btn_blue" id="menu2" onmouseover="javascript:onMenu('2');" onmouseout="javascript:outMenu('2');">Milestone</a>&nbsp;I&nbsp;<a id="menu3" href="javascript:displayChange('4');" class="btn_blue" onmouseover="javascript:onMenu('3');" onmouseout="javascript:outMenu('3');"><%=messageService.getString("e3ps.message.ket_message", "01716") %><%--산출물--%></a></td>
					</tr>
				</table>
			</td>
          </tr>
          <tr>
            <td height="5"></td>
          </tr>
          <tr>
            <td valign="top"><table border="0" cellspacing="0" cellpadding="0">
	<tr id="normalTreeDisplay" style="display:block">
		<td>
			<% if(popup != null && !popup.equals("")) { %>
			<DIV id="scrollbox" style="width:190;overflow:auto">
				<table oncontextmenu="return false" onselectstart="return false"  id="treeTable" ondragstart="return false" cellSpacing="0" cellPadding="0" border="0">
				</table>
			</div>
			<% } else { %>
			<DIV id="scrollbox" style="width:190;overflow:auto">
				<table oncontextmenu="return false" onselectstart="return false"  id="treeTable" ondragstart="return false" cellSpacing="0" cellPadding="0" border="0">
				</table>
			</div>
			<% } %>
		</td>
	</tr>

	<tr id="psoTreeDisplay" style="display:none">
			<td valign="top">
			<% if(popup != null && !popup.equals("")) { %>
				<DIV id="scrollbox" style="width:100%;overflow:auto">
				<table oncontextmenu="return false" onselectstart="return false"  id=psoTreeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
				</div>
			<%} else{ %>
				<DIV id="scrollbox" style="width:100%;overflow:auto">
				<table oncontextmenu="return false" onselectstart="return false"  id=psoTreeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
				</div>
			<% } %>
			</td>
	</tr>
	<tr id="oemTreeDisplay" style="display:none">
			<td valign="top">
			<% if(popup != null && !popup.equals("")) { %>
				<DIV id="scrollbox" style="width:100%;overflow:auto">
				<table oncontextmenu="return false" onselectstart="return false"  id=oemTreeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
				</div>
			<%} else{ %>
				<DIV id="scrollbox" style="width:100%;overflow:auto">



				<table oncontextmenu="return false" onselectstart="return false"  id=oemTreeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
				</div>
			<% } %>
			</td>
	</tr>

	<tr id="milestoneTreeDisplay" style="display:none">
			<td valign="top">
			<% if(popup != null && !popup.equals("")) { %>
				<DIV id="scrollbox3" style="width:100%;overflow:auto">
				<table oncontextmenu="return false" onselectstart="return false"  id=milestoneTreeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
				</div>
			<%} else{ %>
				<DIV id="scrollbox3" style="width:190;overflow:auto">
				<table oncontextmenu="return false" onselectstart="return false"  id=milestoneTreeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
				</div>
			<% } %>
			</td>
	</tr>

	<tr id="outputTreeDisplay" style="display:none">
			<td valign="top">
			<% if(popup != null && !popup.equals("")) { %>
				<DIV id="scrollbox4" style="width:100%;overflow:auto">
				<table oncontextmenu="return false" onselectstart="return false"  id=outputTreeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
				</div>
			<%} else{ %>
				<DIV id="scrollbox4" style="width:190;overflow:auto">
				<table oncontextmenu="return false" onselectstart="return false"  id=outputTreeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
				</div>
			<% } %>
			</td>
	</tr>

</table>

</td>
          </tr>

        </table></td>
      <td width="1" bgcolor="#c6c6c6"></td>
      <td width="9" valign="top"><!--img src="/plm/portal/images/arrow.gif" width="9" height="30"--></td>
    </tr>
  </table>


<SCRIPT>
	$(window).bind('resize', function (){
		//alert('resize');


		screenSize();
	});

	function screenSize(){
		if(document.body.clientHeight < 185){
			return;
		}


			document.getElementById("scrollbox").style.height = document.body.clientHeight - 185;
			document.getElementById("scrollbox3").style.height = document.body.clientHeight - 185;
			document.getElementById("scrollbox4").style.height = document.body.clientHeight - 185;

	}

	var tree = new Tree("treeTable","<%=project.getPjtName()%>","/plm/portal/icon/tree/project");
	tree.selectionMode = tree.SINGLE_TREE_SELECTION;
	tree.treeSelectionListener = function(node, event){
		this.selectNode(node, event);
		if ( node.get("nodeType") == "project" ) {
			target.document.location.href = "/plm/jsp/project/template/TemplateProjectView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
		} else if ( node.get("nodeType") == "task" ) {
			target.document.location.href = "/plm/jsp/project/template/TemplateTaskView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
		}
	}
	var sNode;
	tree.root.put("oid","<%=oid%>");
	tree.root.put("nodeType","project");
<%
	TemplateProjectTreeNode root = (TemplateProjectTreeNode)ProjectScheduleHelper.manager.getRoot(project, false);
	Vector list = new Vector();

	makeVector(root, list);

	Hashtable sortHash = new Hashtable();
	Hashtable sortHashOEM = new Hashtable();

	for(int i = 0; i < list.size(); i++){
		TemplateProjectTreeNode node = (TemplateProjectTreeNode)list.get(i);
		TemplateProjectTreeNodeData td = (TemplateProjectTreeNodeData)node.getUserObject();
		if(td.getData() instanceof TemplateProject){
			/* TemplateProject 이다*/
			continue;
		}


		TemplateTask childTask = (TemplateTask)td.getData();

		boolean isOption = childTask.isOptionType();

		if(childTask.getDepartment() != null && !childTask.getDepartment().isEnabled()){
			color = "red";
		}else{
			color = "black";
		}

		startFONT = "<FONT color='" + color + "'>";

		TemplateProjectTreeNode pnode = (TemplateProjectTreeNode)node.getParent();
		TemplateProjectTreeNodeData ptd = (TemplateProjectTreeNodeData)pnode.getUserObject();
		String childTaskOID = CommonUtil.getOIDString( childTask);
		sortHash.put(childTaskOID, new Integer(i));
		sortHashOEM.put(childTaskOID, new Integer(i));

		long childTaskOIDValue = CommonUtil.getOIDLongValue( childTask);
		long parentTaskOIDValue = 0;
		if(ptd.getData() instanceof TemplateProject){
			parentTaskOIDValue = 0;
		}else{
			parentTaskOIDValue = CommonUtil.getOIDLongValue((TemplateTask)ptd.getData());
		}
%>
			var node<%=childTaskOIDValue%> = new TreeNode("<%=startFONT%>"+ unescape("<%=childTask.getTaskName()%>")+"<%=endFONT%>");
			node<%=childTaskOIDValue%>.put("oid","<%=childTaskOID%>");
			node<%=childTaskOIDValue%>.put("nodeType","task");

		<%if(isOption){%>
			node<%=childTaskOIDValue%>.icon = "option";
		<%}%>

		<%if(	parentTaskOIDValue == 0){%>
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


   var treePso = new Tree("psoTreeTable","<%//=project.getOutputType().getName() %>","/plm/portal/icon/tree/project");
   treePso.selectionMode = tree.SINGLE_TREE_SELECTION;
   treePso.treeSelectionListener = function(node, event){
		this.selectNode(node, event);
		if ( node.get("nodeType") == "output" ) {
			target.document.location.href = "/plm/jsp/project/template/TemplateTaskView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
		}
	}

<%

   Vector treeV = new Vector();

   ProjectOutPutType ptype = project.getOutputType();
   makeTypeTree(ptype, treeV, project, new ProjectOutPutTypeComparator(sortHash));
   long rootTaskOIDValue = 0;//CommonUtil.getOIDLongValue(ptype);

   for(int i = 1; i < treeV.size(); i++){

	   Object o = (Object)treeV.get(i);

	   long childTaskOIDValue = 0;
	   long parentTaskOIDValue = 0;
	   String name = "";
	   String nodeType = "outputType";
	   String taskOID = "";

	   if(o instanceof ProjectOutPutType){
		  ProjectOutPutType pt = (ProjectOutPutType)o;
		  name = pt.getName();
		  childTaskOIDValue = CommonUtil.getOIDLongValue(pt);
		  if(pt.getParent() != null){
			  parentTaskOIDValue = CommonUtil.getOIDLongValue(pt.getParent());
		  }


	   }else if(o instanceof ProjectOutput){
		   ProjectOutput po = (ProjectOutput)o;
		   parentTaskOIDValue = CommonUtil.getOIDLongValue(po.getOutputType());
		   childTaskOIDValue = CommonUtil.getOIDLongValue(po);
		   taskOID = CommonUtil.getOIDString(po.getTask());
		   name = po.getOutputName();
		   nodeType = "output";
	   }



	   %>

	   var node<%=childTaskOIDValue%> = new TreeNode("<%=name%>");
			node<%=childTaskOIDValue%>.put("oid","<%=taskOID%>");
			node<%=childTaskOIDValue%>.put("nodeType","<%=nodeType%>");
		<%if(	parentTaskOIDValue == 0 || parentTaskOIDValue == rootTaskOIDValue){%>
				treePso.root.add(node<%=childTaskOIDValue%>);
		<%}else{%>
			node<%=parentTaskOIDValue%>.add(node<%=childTaskOIDValue%>);
		<%}%>


<%}
%>

   treePso.expandAll();
   treePso.repaint();


 <%if(project.getOemType()!=null){
	OEMProjectType oemtypeRoot = null;
	Object oemObject = project.getOemType().getParent();
	oemtypeRoot =  (OEMProjectType)oemObject;

 %>
   var treeOem = new Tree("oemTreeTable","<%=oemtypeRoot.getName() %>","/plm/portal/icon/tree/project");
   treeOem.selectionMode = tree.SINGLE_TREE_SELECTION;
   treeOem.treeSelectionListener = function(node, event){
		this.selectNode(node, event);
		if ( node.get("nodeType") == "oemType" ) {
			target.document.location.href = "/plm/jsp/project/template/TemplateTaskView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
		}
	}

<%
   Vector treeOEM = new Vector();


   OEMProjectType oemtype = project.getOemType();
   makeTypeTreeOEM(oemtype, treeOEM, project, new OEMTypeComparator(sortHashOEM));

   long rootOEMOIDValue = CommonUtil.getOIDLongValue(oemtype);

   for(int i = 1; i < treeOEM.size(); i++){

	   Object o = (Object)treeOEM.get(i);

	   long childOEMOIDValue = 0;
	   long parentOEMOIDValue = 0;
	   String name = "";
	   String nodeType = "OEMType";
	   String taskOID = "";


	   if(o instanceof OEMProjectType){
		  OEMProjectType pt = (OEMProjectType)o;
		  name = pt.getName();
		  childOEMOIDValue = CommonUtil.getOIDLongValue(pt);
		  if(pt.getParent() != null){
			  parentOEMOIDValue = CommonUtil.getOIDLongValue(pt.getParent());

		  }

		}else if(o instanceof TemplateTask){
		   TemplateTask tt = (TemplateTask)o;
		   parentOEMOIDValue = CommonUtil.getOIDLongValue(tt.getOemType());
		   childOEMOIDValue = CommonUtil.getOIDLongValue(tt);
		   taskOID = CommonUtil.getOIDString(tt);
		   name = tt.getTaskName();
		   nodeType = "OEMType";
	   }
	   %>

	   var node<%=childOEMOIDValue%> = new TreeNode("<%=name%>");
			node<%=childOEMOIDValue%>.put("oid","<%=taskOID%>");
			node<%=childOEMOIDValue%>.put("OEMType","<%=nodeType%>");
		<%if(	parentOEMOIDValue == 0 || parentOEMOIDValue == rootOEMOIDValue){%>
				treeOem.root.add(node<%=childOEMOIDValue%>);
		<%}else{%>
			node<%=parentOEMOIDValue%>.add(node<%=childOEMOIDValue%>);
		<%}%>


<%}
%>

   treeOem.expandAll();
   treeOem.repaint();

  <%}%>

//milestone tree

	var treeMilestone = new Tree("milestoneTreeTable","<%=project.getPjtName()%>","/plm/portal/icon/tree/project");
	treeMilestone.selectionMode = tree.SINGLE_TREE_SELECTION;
	treeMilestone.treeSelectionListener = function(node, event){
		this.selectNode(node, event);
		if ( node.get("nodeType") == "project" ) {
			target.document.location.href = "/plm/jsp/project/template/TemplateProjectView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
		} else if ( node.get("nodeType") == "task" ) {
			target.document.location.href = "/plm/jsp/project/template/TemplateTaskView.jsp?oid="+node.get("oid")+"<%=tmpPopUp%>";
		}
	}
	treeMilestone.root.put("oid","<%=oid%>");
	treeMilestone.root.put("nodeType","project");
<%
	for(int i = 0; i < list.size(); i++){
		TemplateProjectTreeNode node = (TemplateProjectTreeNode)list.get(i);
		TemplateProjectTreeNodeData td = (TemplateProjectTreeNodeData)node.getUserObject();
		if(td.getData() instanceof TemplateProject){
			/* TemplateProject 이다*/
			continue;
		}

		TemplateTask childTask = (TemplateTask)td.getData();

		boolean isOption = childTask.isOptionType();

		if(childTask.isMileStone()) {
			if(childTask.getDepartment() != null && !childTask.getDepartment().isEnabled()){
				color = "red";
			}else{
				color = "black";
			}

			startFONT = "<FONT color='" + color + "'>";

			TemplateProjectTreeNode pnode = (TemplateProjectTreeNode)node.getParent();
			TemplateProjectTreeNodeData ptd = (TemplateProjectTreeNodeData)pnode.getUserObject();
			String childTaskOID = CommonUtil.getOIDString( childTask);
			sortHash.put(childTaskOID, new Integer(i));
			sortHashOEM.put(childTaskOID, new Integer(i));

			long childTaskOIDValue = CommonUtil.getOIDLongValue( childTask);
			long parentTaskOIDValue = 0;
			if(ptd.getData() instanceof TemplateProject){
				parentTaskOIDValue = 0;
			}else{
				parentTaskOIDValue = CommonUtil.getOIDLongValue((TemplateTask)ptd.getData());
			}
%>
			var nodeMilestone<%=childTaskOIDValue%> = new TreeNode("<%=startFONT%>"+ unescape("<%=childTask.getTaskName()%>")+"<%=endFONT%>");
			nodeMilestone<%=childTaskOIDValue%>.put("oid","<%=childTaskOID%>");
			nodeMilestone<%=childTaskOIDValue%>.put("nodeType","task");

			<%if(isOption){%>
				nodeMilestone<%=childTaskOIDValue%>.icon = "option";
			<%}%>

			treeMilestone.root.add(nodeMilestone<%=childTaskOIDValue%>);
<%	}
	}%>
   treeMilestone.expandAll();
   treeMilestone.repaint();

//output tree

	var tree = new Tree("outputTreeTable","<%=project.getPjtName()%>","/plm/portal/icon/tree/project");
	tree.selectionMode = tree.SINGLE_TREE_SELECTION;
	tree.treeSelectionListener = function(node, event){
		this.selectNode(node, event);
		if ( node.get("nodeType") == "project" ) {
			target.document.location.href = "/plm/servlet/e3ps/SearchProjectOutputServlet?oid="+node.get("oid")+"&projectType=temp&type=normal&subAll=all";
		} else if ( node.get("nodeType") == "task" ) {
			target.document.location.href = "/plm/servlet/e3ps/SearchProjectOutputServlet?oid="+node.get("oid")+"&projectType=temp&type=normal&taskType=task&subAll=all";
		}
	}
	tree.root.put("oid","<%=oid%>");
	tree.root.put("nodeType","project");
<%
	for(int i = 0; i < list.size(); i++){
		TemplateProjectTreeNode node = (TemplateProjectTreeNode)list.get(i);
		TemplateProjectTreeNodeData td = (TemplateProjectTreeNodeData)node.getUserObject();
		if(td.getData() instanceof TemplateProject){
			/* TemplateProject 이다*/
			continue;
		}

		TemplateTask childTask = (TemplateTask)td.getData();

		boolean isOption = childTask.isOptionType();

		if(childTask.getDepartment() != null && !childTask.getDepartment().isEnabled()){
			color = "red";
		}else{
			color = "black";
		}

		startFONT = "<FONT color='" + color + "'>";

		TemplateProjectTreeNode pnode = (TemplateProjectTreeNode)node.getParent();
		TemplateProjectTreeNodeData ptd = (TemplateProjectTreeNodeData)pnode.getUserObject();
		String childTaskOID = CommonUtil.getOIDString( childTask);
		sortHash.put(childTaskOID, new Integer(i));
		sortHashOEM.put(childTaskOID, new Integer(i));

		long childTaskOIDValue = CommonUtil.getOIDLongValue( childTask);
		long parentTaskOIDValue = 0;
		if(ptd.getData() instanceof TemplateProject){
			parentTaskOIDValue = 0;
		}else{
			parentTaskOIDValue = CommonUtil.getOIDLongValue((TemplateTask)ptd.getData());
		}
%>
			var node<%=childTaskOIDValue%> = new TreeNode("<%=startFONT%>"+ unescape("<%=childTask.getTaskName()%>")+"<%=endFONT%>");
			node<%=childTaskOIDValue%>.put("oid","<%=childTaskOID%>");
			node<%=childTaskOIDValue%>.put("nodeType","task");

		<%if(isOption){%>
			node<%=childTaskOIDValue%>.icon = "option";
		<%}%>

		<%if(	parentTaskOIDValue == 0){%>
				tree.root.add(node<%=childTaskOIDValue%>);
		<%}else{%>
			node<%=parentTaskOIDValue%>.add(node<%=childTaskOIDValue%>);
		<%}
		%>
<%}%>
   tree.expandAll();
   tree.repaint();

</SCRIPT>
</form>
</body>
</html>
<%!

	public void makeTypeTree(Object o, Vector vector, TemplateProject project, ProjectOutPutTypeComparator comparator)throws Exception{

		vector.add(o);

		if(o instanceof ProjectOutPutType){

			ProjectOutPutType pt = (ProjectOutPutType)o;
			QuerySpec spec = ProjectOutPutTypeHelper.getCodeQuerySpec(pt);
			QueryResult qr = PersistenceHelper.manager.find(spec);

			if(qr.size() == 0){
				QuerySpec sp = ProjectOutputHelper.getProjectOutputSpec(pt, project);
				QueryResult rs = PersistenceHelper.manager.find(sp);
				Vector v = new Vector();
				while(rs.hasMoreElements()){
					v.add(rs.nextElement());
				}
			    Collections.sort(v, comparator);

			    for(int i = 0; i < v.size(); i++){
			    	makeTypeTree(v.get(i), vector, project, comparator);
			    }
			    return;
			}

			while (qr.hasMoreElements()){

				Object[] obj = (Object[])qr.nextElement();
				ProjectOutPutType suboutputtype = (ProjectOutPutType)obj[0];
				makeTypeTree(suboutputtype, vector, project, comparator);
			}
		}

	}

	public void makeVector(TemplateProjectTreeNode node, Vector vector){
		vector.add(node);
		for(int i = 0; i < node.getChildCount(); i++){
			makeVector((TemplateProjectTreeNode)node.getChildAt(i), vector);
		}
	}


	public void makeTypeTreeOEM(Object o, Vector vector, TemplateProject project, OEMTypeComparator comparator)throws Exception{

		vector.add(o);
		if(o instanceof OEMProjectType){

			OEMProjectType oemType = (OEMProjectType)o;
			QuerySpec spec = OEMTypeHelper.getCodeQuerySpec(oemType);
			QueryResult qr = PersistenceHelper.manager.find(spec);

			if(qr.size() == 0){
				//OEMTypeHelper.getTaskSpecfromOEMType(oemType, project);
				QueryResult rs = PersistenceHelper.manager.find(OEMTypeHelper.getTaskSpecfromOEMType(oemType, project));
				Vector v = new Vector();
				while(rs.hasMoreElements()){
					v.add(rs.nextElement());
				}
				Collections.sort(v, comparator);


			    for(int i = 0; i < v.size(); i++){
			    	makeTypeTreeOEM(v.get(i), vector, project, comparator);
			    }
			    return;
			}

			while (qr.hasMoreElements()){

				Object[] obj = (Object[])qr.nextElement();
				OEMProjectType oemtype = (OEMProjectType)obj[0];
				makeTypeTreeOEM(oemtype, vector, project, comparator);
			}
		}

	}

%>
