<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="wt.fc.*,
				java.util.*,
				java.text.*" %>
<%@page import = "e3ps.project.*,
				  e3ps.project.beans.*,
				  e3ps.common.util.*"%>
				  
				  
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
				  
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
				  
<%@include file="/jsp/common/context.jsp" %>

<%	
	String oid = StringUtil.checkNull(request.getParameter("oid"));

	Object obj = CommonUtil.getObject(oid);
	Calendar currentDate = Calendar.getInstance();
	Calendar endDate = Calendar.getInstance();

	String selectedOid = StringUtil.checkNull(request.getParameter("selectedOid"));
	

	long selectTask = -1;
	if(selectedOid != null && selectedOid.length() > 0){
		selectTask = CommonUtil.getOIDLongValue(selectedOid);
	}
	
	
	String color = "black";
	String rootName = "";
	String rootName2 = "";
	String startFONT = "<FONT color='" + color + "'>";
	String endFONT = "</FONT>";
	String rootOid = oid;
	ProjectViewButtonAuth auth = null;
	if(obj instanceof E3PSProject){
		E3PSProject project = (E3PSProject)obj;
		auth = new ProjectViewButtonAuth(project);
		if(project instanceof MoldProject){
			String partName = ((MoldProject)project).getMoldInfo().getPartName();
			if(partName == null){
				partName = "";
			}
			rootName = project.getPjtNo() + "<br>" + partName;
			rootName2 = project.getPjtNo() + "(" + partName + ")";
		}else{
			rootName = project.getPjtNo() + "<br>" + project.getPjtName();
			rootName2 = project.getPjtNo() + "(" + project.getPjtName() + ")";
		}
	}else if(obj instanceof E3PSTask){
		E3PSTask E3PSTask = (E3PSTask)obj;
		E3PSProject project = (E3PSProject)E3PSTask.getProject();
		auth = new ProjectViewButtonAuth(project);
		rootName = E3PSTask.getTaskName();
		rootName2 = E3PSTask.getTaskName();
		startFONT = getTaskStateFont(E3PSTask);
	}
	
%>
<%@page import="java.sql.Timestamp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

</style>
<script src="/plm/portal/js/script.js" type="text/javascript"></script>
<script src="/plm/portal/js/newtree.js" type="text/javascript"></script>
<script src="/plm/portal/js/common.js" type="text/javascript"></script>
<script language="JavaScript">
<!--
function displayChange(type) {
	if ( type == '0' ) {
		normalTreeDisplay.style.display = "none";
		psoTreeDisplay.style.display = "block";
	} else {
		normalTreeDisplay.style.display = "block";
		psoTreeDisplay.style.display = "none";
	}
}
function reloadTree(){	
	//document.location.reload();
	document.forms[0].action = "/plm/jsp/project/manage/ManageProjectTaskTree.jsp";
	document.forms[0].submit();
}
function reload2(selectedOid){
	document.forms[0].action = "/plm/jsp/project/manage/ManageProjectTaskTree.jsp";
	document.forms[0].selectedOid.value = selectedOid;
	document.forms[0].submit();
}

function excelDown(){
	document.forms[0].method = "post";
	document.forms[0].action = "/plm/jsp/project/manage/ExcelDownLoad.jsp";
	document.forms[0].submit();
}

-->
</script>
<style type="text/css">
<!--
body {
	overflow-x:auto;
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

<body>
<form method="post">
<input type="hidden" name="oid" value="<%=oid%>">
<input type="hidden" name="selectedOid" value="">

<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr align="center">
		<td height="54" align="center" valign="top"><table width="170" height="50" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="7" height="7"><img src="/plm/portal/images/box_5.gif"></td>
                  <td bgcolor="#e6f0ff"></td>
                  <td width="7" height="7"><img src="/plm/portal/images/box_6.gif"></td>
                </tr>
                <tr>
                  <td width="7" bgcolor="#e6f0ff"></td>
                  <td align="center" valign="middle" bgcolor="#e6f0ff">
			<a href="javascript:reloadTree();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftbtn_02','','/plm/portal/images/btn_ref_s.png',1)">
			<img src="/plm/portal/images/btn_ref.png" alt="<%=messageService.getString("e3ps.message.ket_message", "01768") %><%--새로고침--%>" name="leftbtn_02" border="0">
			</a>
			
			
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
</table>




<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr align="center">
		<td height="60" valign="top"><table width="170" height="56" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="7" height="7"><img src="/plm/portal/images/box_1.gif" width="7" height="7"></td>
              <td bgcolor="#e9e9e9"></td>
              <td width="7" height="7"><img src="/plm/portal/images/box_2.gif" width="7" height="7"></td>
            </tr>
            <tr>
              <td width="7" bgcolor="#e9e9e9"></td>
              <td align="center" valign="middle" bgcolor="#e9e9e9"><b><%=rootName%></b><br></td>
              <td width="7" bgcolor="#e9e9e9"></td>
            </tr>
            <tr>
              <td width="7" height="7"><img src="/plm/portal/images/box_3.gif" width="7" height="7"></td>
              <td bgcolor="#e9e9e9"></td>
              <td width="7" height="7"><img src="/plm/portal/images/box_4.gif" width="7" height="7"></td>
            </tr>
          </table></td>
	</tr>
</table>



<table border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr id="normalTreeDisplay" style="display:block">
		<td>
			<table oncontextmenu="return false" onselectstart="return false"  id="treeTable" ondragstart="return false" cellSpacing="0" cellPadding="0" border="0"></table>
		</td>
	</tr>
</table>

<SCRIPT>
<%
	String nodeName = StringUtil.htmlCharEncode(rootName2);
%>
	var target = eval("parent.body");
	var tree = new Tree("treeTable","<%=startFONT%>"+ unescape("<%=nodeName%>")+"<%=endFONT%>","/plm/portal/icon/tree/project");
	//tree.selectionMode = tree.DISCONTIGUOUS_TREE_SELECTION; 
	tree.selectionMode = tree.SINGLE_TREE_SELECTION;
	tree.treeSelectionListener = function(node, event){
	  if(node.get("oid") == ""){
	  	return;
	  }
	  var deptOid = "";
	  
	  if(node.get("deptOid") != null){
	  	 deptOid = node.get("deptOid");
	  }
	  
	  this.selectNode(node, event);
	  target.document.location.href = "/plm/jsp/project/manage/ManageProjectTaskList.jsp?oid="+ node.get("oid");// + "&deptOid=" + deptOid;	
	}
    tree.root.put("oid","<%=rootOid%>");
	tree.root.put("nodeType","project");
	var selectNode;
	
<%
	boolean isDepartMentSchdule = false;
	
    String deptOid = "";
	Vector list = new Vector();
	
	ProjectTreeNode root = null;
	if(obj instanceof E3PSProject){
		root = (ProjectTreeNode)ProjectScheduleHelper.manager.getRoot((E3PSProject)obj, false);
		makeVector(root, list);
	}else if(obj instanceof E3PSTask){
		root = (ProjectTreeNode)ProjectScheduleHelper.manager.getRoot((E3PSTask)obj, false);
		makeVector(root, list);
	}
	
	for(int i = 0; i < list.size(); i++){
		ProjectTreeNode node = (ProjectTreeNode)list.get(i);
		ProjectTreeNodeData td = (ProjectTreeNodeData)node.getUserObject();
		
		//if(td.getData() instanceof TemplateProject){
			/* TemplateProject 이다*/
			//continue;
		//}
		
		if(!isDepartMentSchdule && node.getParent() == null){
			continue;
		}
		
		E3PSTask childTask = (E3PSTask)td.getData();
		
		boolean isOption = childTask.isOptionType();
		
		ProjectTreeNode pnode = (ProjectTreeNode)node.getParent();
		ProjectTreeNodeData ptd = null;
		
		E3PSTask parentTask = null;
		if(pnode != null){
			ptd = (ProjectTreeNodeData)pnode.getUserObject();
			if(ptd.getData() instanceof E3PSTask){
				parentTask = (E3PSTask)ptd.getData();
			}
		}
		
		String childTaskOID = CommonUtil.getOIDString( childTask);
		long childTaskOIDValue = CommonUtil.getOIDLongValue( childTask);
		
		long parentTaskOIDValue = 0;
		
		if(isDepartMentSchdule){
			if(ptd == null){
				parentTaskOIDValue = 0;
			}else{
				parentTaskOIDValue = CommonUtil.getOIDLongValue(parentTask);
			}
			if(parentTaskOIDValue != 0){
				String departOid = CommonUtil.getOIDString(childTask.getDepartment());
				
				if(!deptOid.equals(departOid)){
					//Kogger.debug("continue....");
					continue;
				}
			}
			
			
		}else{
			if(oid.equals(CommonUtil.getOIDString((Persistable)ptd.getData()))){
				parentTaskOIDValue = 0;
			}else{
				parentTaskOIDValue = CommonUtil.getOIDLongValue((E3PSTask)ptd.getData());
			}
		}
		
		nodeName = StringUtil.htmlCharEncode(childTask.getTaskName());
		
%>
	
		var node<%=childTaskOIDValue%> = new TreeNode("<%=getTaskStateFont(childTask)%>" + unescape("<%=nodeName%>") + "<%=endFONT%>");
		node<%=childTaskOIDValue%>.put("oid","<%=childTaskOID%>");
	<%if(isDepartMentSchdule){%>
		node<%=childTaskOIDValue%>.put("deptOid", "<%=deptOid%>");
	<%}%>
		
		node<%=childTaskOIDValue%>.put("nodeType","task");

	<%if(isOption){%>
		node<%=childTaskOIDValue%>.icon = "option";
	<%}%>
		
	<%if(	parentTaskOIDValue == 0){%>
			tree.root.add(node<%=childTaskOIDValue%>);
	<%}else{%>
		
		node<%=parentTaskOIDValue%>.add(node<%=childTaskOIDValue%>);
	<%}%>
	<%if(childTaskOIDValue == selectTask){
	%>
	selectNode = node<%=childTaskOIDValue%>;
	<%}%>
	
<%}%>


   tree.expandAll();
   if(selectNode != null){
	tree.selectNode(selectNode);
   }else{
	tree.selectNode(tree.root);
   }   
   tree.repaint();
</SCRIPT>
</form>
</body>
</html>
<%!

	private String getTaskStateFont(E3PSTask task)throws Exception {
		
		String pmEnd = "99999999";
	
		E3PSTask parentTask = (E3PSTask)task.getParent();
		
		if(parentTask == null){
			
			return "<FONT color='0033CC'>";
			
		}
		
		ScheduleData taskSd = (ScheduleData)task.getTaskSchedule().getObject();	
		
		Timestamp pmEndDate = null;
		if(parentTask.getParent() == null){
		  // pmEndDate = taskSd.getPmEndDate();
		}else{
	
		   ScheduleData ptaskSd = (ScheduleData) parentTask.getTaskSchedule().getObject();	
		   //pmEndDate = ptaskSd.getPmEndDate();
		}
		
		if(pmEndDate != null){
			pmEnd = DateUtil.getTimeFormat(pmEndDate, "yyyyMMdd");
		}
		
		
		String ts_exstartDate = DateUtil.getTimeFormat(taskSd.getPlanEndDate(), "yyyyMMdd");
		if(pmEnd.compareTo(ts_exstartDate) < 0){
			
			Kogger.debug("red = " + pmEnd);
			return "<FONT color='FF3300'>";
		}else{
			return "<FONT color='0033CC'>";
		}
		
	}
	
		
		
	
	public void makeVector(ProjectTreeNode node, Vector vector){
		vector.add(node);
		for(int i = 0; i < node.getChildCount(); i++){
			makeVector((ProjectTreeNode)node.getChildAt(i), vector);
		}
	}
%>
