<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="wt.fc.*,java.util.*,java.text.*" %>
<%@page import = "e3ps.project.*,e3ps.project.beans.*,e3ps.common.util.*"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%@include file="/jsp/common/context.jsp" %>
<%	
	String oid = StringUtil.checkNull(request.getParameter("oid"));
	String selectedOid = StringUtil.checkNull(request.getParameter("selectedOid"));
	

	long sl = -1;
	if(selectedOid != null && selectedOid.length() > 0){
		sl = CommonUtil.getOIDLongValue(selectedOid);
	}

	Kogger.debug("sl === " + sl);

	TemplateProject project = (TemplateProject)CommonUtil.getObject(oid);	
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
	var oid = document.forms[0].selectedOid.value;
	reload(oid);
}

function reload(selectedOid){
	
	document.forms[0].selectedOid.value = selectedOid;
	document.forms[0].submit();
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
-->
</script>

</head>

<body onLoad="MM_preloadImages('/plm/portal/images/btn_ref_s.png');javascript:screenSize();">
<form name="projectTree" method="post">
<input type="hidden" name="oid" value="<%=oid%>">
<input type="hidden" name="selectedOid" value="<%=selectedOid %>">

<table width="200" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="190" height="100%" align="center" valign="top"><table width="170" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="54" align="center" valign="top"><table width="170" height="50" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="7" height="7"><img src="/plm/portal/images/box_5.gif"></td>
                  <td bgcolor="#e6f0ff"></td>
                  <td width="7" height="7"><img src="/plm/portal/images/box_6.gif"></td>
                </tr>
                <tr>
                  <td width="7" bgcolor="#e6f0ff"></td>
                  <td align="center" valign="middle" bgcolor="#e6f0ff"><a href="javascript:reloadTree();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image19','','/plm/portal/images/btn_ref_s.png',1)"><img src="/plm/portal/images/btn_ref.png" name="Image19" border="0"></a></td>
                  <td width="7" bgcolor="#e6f0ff"></td>
                </tr>
                <tr>
                  <td width="7" height="7"><img src="/plm/portal/images/box_7.gif"></td>
                  <td bgcolor="#e6f0ff"></td>
                  <td width="7" height="7"><img src="/plm/portal/images/box_8.gif"></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td height="54" align="center" valign="top"><table width="170" height="56" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="7" height="7"><img src="/plm/portal/images/box_1.gif" width="7" height="7"></td>
                  <td bgcolor="#e9e9e9"></td>
                  <td width="7" height="7"><img src="/plm/portal/images/box_2.gif" width="7" height="7"></td>
                </tr>
                <tr>
                  <td width="7" bgcolor="#e9e9e9"></td>
                  <td align="center" valign="middle" bgcolor="#e9e9e9"><b><%=project.getPjtName()%></b></td>
                  <td width="7" bgcolor="#e9e9e9"></td>
                </tr>
                <tr>
                  <td width="7" height="7"><img src="/plm/portal/images/box_3.gif" width="7" height="7"></td>
                  <td bgcolor="#e9e9e9"></td>
                  <td width="7" height="7"><img src="/plm/portal/images/box_4.gif" width="7" height="7"></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td height="5"></td>
          </tr>
          <tr>
            <td valign="top"><table border="0" cellspacing="0" cellpadding="0">
	<tr id="normalTreeDisplay" style="display:block">
		<td>
			<DIV id="scrollbox" style="width:190;height:450;overflow:auto">
				<table oncontextmenu="return false" onselectstart="return false"  id="treeTable" ondragstart="return false" cellSpacing="0" cellPadding="0" border="0">
				</table>
			</div>
		</td>
	</tr>
</table></td>
          </tr>
        </table></td>
      <td width="1" bgcolor="#c6c6c6"></td>
      <td width="9" valign="top">&nbsp;</td>
    </tr>
  </table>



<SCRIPT>
	$(window).bind('resize', function (){
		//alert('resize');
		screenSize();
	});
	
	function screenSize(){
		if(document.body.clientHeight < 115){
			return;
		}
		document.getElementById("scrollbox").style.height = document.body.clientHeight - 115;
	}

	var target = eval("parent.body");
	var tree = new Tree("treeTable","<%=project.getPjtName()%>","/plm/portal/icon/tree/project");
	tree.selectionMode = tree.SINGLE_TREE_SELECTION;
	tree.treeSelectionListener = function(node, event){
		this.selectNode(node, event);	
		target.document.location.href = "/plm/jsp/project/manage/ManageTemplateProjectTaskList.jsp?oid="+node.get("oid");		
	}
   tree.root.put("oid","<%=oid%>");
	tree.root.put("depth","1");
	tree.root.put("nodeType","project");
	var sNode;
<%
	
	
	TemplateProjectTreeNode root = (TemplateProjectTreeNode)ProjectScheduleHelper.manager.getRoot(project, false);
	
	Vector list = new Vector();
	makeVector(root, list);
	
	for(int i = 0; i < list.size(); i++){
		TemplateProjectTreeNode node = (TemplateProjectTreeNode)list.get(i);
		TemplateProjectTreeNodeData td = (TemplateProjectTreeNodeData)node.getUserObject();
		
		
		if(td.getData() instanceof TemplateProject){
			/* TemplateProject 이다*/
			continue;
		}
		
		TemplateTask childTask = (TemplateTask)td.getData();
		
		boolean isOption = childTask.isOptionType();
		
		TemplateProjectTreeNode pnode = (TemplateProjectTreeNode)node.getParent();
		TemplateProjectTreeNodeData ptd = (TemplateProjectTreeNodeData)pnode.getUserObject();
		
		String childTaskOID = CommonUtil.getOIDString( childTask);
		long childTaskOIDValue = CommonUtil.getOIDLongValue( childTask);
		
		long parentTaskOIDValue = 0;
		
		if(ptd.getData() instanceof TemplateProject){
			parentTaskOIDValue = 0;
		}else{
			parentTaskOIDValue = CommonUtil.getOIDLongValue((TemplateTask)ptd.getData());
		}
		
		Kogger.debug(childTaskOIDValue);
%>
		
			var node<%=childTaskOIDValue%> = new TreeNode("<%=childTask.getTaskName()%>");
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
		<%if(childTaskOIDValue == sl){
				Kogger.debug("okkkkkkkkkkkkk......gggg");
			%>
			
		    //tree.openNode(node<%=childTaskOIDValue%>);
			//tree.addChangedNode(node<%=childTaskOIDValue%>);
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
</SCRIPT>
</form>
</body>
</html>
<%!
	public void makeVector(TemplateProjectTreeNode node, Vector vector){
		vector.add(node);
		for(int i = 0; i < node.getChildCount(); i++){
			makeVector((TemplateProjectTreeNode)node.getChildAt(i), vector);
		}
	}
%>
