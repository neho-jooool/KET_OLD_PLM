<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="e3ps.common.util.CommonUtil,e3ps.common.util.StringUtil,
				e3ps.common.code.*"%>
<%@include file="/jsp/common/context.jsp"%>
<%
	String command = StringUtil.trimToEmpty(request.getParameter("command"));
	String mode = StringUtil.trimToEmpty(request.getParameter("mode"));
	String invokeMethod = StringUtil.trimToEmpty(request.getParameter("invokeMethod"));

	String expandedDepth = StringUtil.trimToEmpty(request.getParameter("expandedDepth"));
	String selectedDepth = StringUtil.trimToEmpty(request.getParameter("selectedDepth"));

	String numberCode = StringUtil.trimToEmpty(request.getParameter("numberCode"));
	String designTeam = StringUtil.trimToEmpty(request.getParameter("designTeam"));

	if( expandedDepth.length()==0)
		expandedDepth = "-1";

	if(selectedDepth.length()==0)
		selectedDepth = "-1";


	String codetype = StringUtil.trimToEmpty(request.getParameter("codetype"));
	String parentOid = StringUtil.trimToEmpty(request.getParameter("parentOid"));

	if(codetype.equals(parentOid))
		parentOid = "";

	String rtnCodeType = codetype;
	/*
	if("UNITDIVISIONCODE".equals(codetype) || "JELSTDPARTCODE".equals(codetype)) {
		codetype = "JELPROCESSTYPE";
		expandedDepth = "1";
		selectedDepth = "1";
	}
	*/

	NumberCodeType numCode = NumberCodeType.toNumberCodeType(codetype);

	/*
	if(parentOid.length() == 0 && designTeam.length() > 0) {
		NumberCode parent = NumberCodeHelper.manager.getNumberCode("JELPROCESSTYPE", designTeam);
		parentOid = parent.getPersistInfo().getObjectIdentifier().getStringValue();
	}

	if(parentOid.length() == 0 && numberCode.length() > 0) {
		NumberCode defaultCode = NumberCodeHelper.manager.getNumberCode(rtnCodeType, numberCode);
		if(defaultCode != null && defaultCode.getParent() != null) {
			parentOid = (defaultCode.getParent()).getPersistInfo().getObjectIdentifier().getStringValue();
		}
	}
	*/
	//overflow-x:auto;overflow-y:auto;
%>
<body bgcolor="eff3f4">
<script src="/plm/portal/js/newtree.js"></script>

<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%" >
	<tr>
		<td>
			<div style="width:100%;height:99%;border:0;padding:0;margin-top:10px;overflow-x:auto;overflow-y:auto">
				<table oncontextmenu="return false" onselectstart="return false"  id=treeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
			</div>
		</td>
	</tr>
</table>

<SCRIPT>
	var target = eval("parent.main");
	var tree = new Tree("treeTable","<%=numCode.getComment()%>","/plm/portal/icon/tree/board");
	//tree.selectionMode = tree.DISCONTIGUOUS_TREE_SELECTION;
	tree.selectionMode = tree.SINGLE_TREE_SELECTION;
	tree.treeSelectionListener = function(node, event){
		/*
		var isChk = false;

		if(<%=selectedDepth%> == 1) {
			//차종 & 난이도
			if( !node.depth == <%=selectedDepth%>) isChk = true;
		} else {
			if( !(node.depth == 0 || node.depth == <%=selectedDepth%>) ) isChk = true;
		}
		*/
		<%/*if(CommonUtil.isAdmin()){%>alert("seled depth : <%=selectedDepth%>="+node.depth);<%}*/%>
		if( (Number("<%=selectedDepth%>") != -1) && (Number("<%=selectedDepth%>") != Number(node.depth))) {
			return;
		}

		<%/*if(CommonUtil.isAdmin()){%>alert("go...");<%}*/%>

		var isChk = true;

		this.selectNode(node, event);


		var sNodeOid = tree.selectedNode[0].get("oid");
		if("<%=codetype%>" == tree.selectedNode[0].get("oid") ) {
			sNodeOid = '';
		}
		//alert(node.depth);
		if(true){//if(node.depth !=0){

		target.document.location.href=
			"/plm/jsp/common/code/AdminNumberCodeMgtList.jsp?command=<%=command%>&mode=<%=mode%>&invokeMethod=<%=invokeMethod%>&codetype=<%=rtnCodeType%>&parentOid="+sNodeOid
			+ "&designTeam=<%=designTeam%>" + "&depth=" + tree.selectedNode[0].depth+"&isChk="+isChk + "&expandedDepth=<%=expandedDepth%>&selectedDepth="+ tree.selectedNode[0].depth ;
		}
	}
<%
	out.print("tree.root.put('oid', '"+codetype+"');");
	if( (Integer.parseInt(expandedDepth) == -1) || (Integer.parseInt(expandedDepth) > 0) ) {
		maketree(out, numCode, null, codetype, Integer.parseInt(expandedDepth), 1);
	}
%>
	//tree.expandAll();
	tree.repaint();

<%
	if(parentOid.length() > 0) {
		NumberCode pcode = (NumberCode)CommonUtil.getObject(parentOid);
		String pid = "";
		String opennode = "";
		ArrayList ancestors = NumberCodeHelper.ancestorNumberCode(pcode);
		ancestors.add(pcode);

		NumberCode openCode = null;
		for(int i = 0; i < ancestors.size(); i++) {
			openCode = (NumberCode)ancestors.get(i);
			pid = openCode.getPersistInfo().getObjectIdentifier().getId() + "";
			opennode = "node"+pid.trim();
			out.println("tree.toggleNode(nodes["+opennode+".index]);");
			if(i == (ancestors.size()-1)) {
				out.println("tree.selectNode(nodes["+opennode+".index]);");
				//out.println("tree.treeSelectionListener(nodes["+opennode+".index]);");
			}
		}

		/*
			if(pcode.getParent() != null) {
				pid = (pcode.getParent()).getPersistInfo().getObjectIdentifier().getId() + "";
				opennode = "node"+pid.trim();
				out.println("tree.toggleNode(nodes["+opennode+".index]);");
			}
			pid = pcode.getPersistInfo().getObjectIdentifier().getId() + "";
			opennode = "node"+pid.trim();
			out.println("tree.toggleNode(nodes["+opennode+".index]);");
		*/
	}
%>
</SCRIPT>
<%//}%>
<%!
	public void maketree(JspWriter out, NumberCodeType codetype, NumberCode parent, String rootOid, int expandedDepth, int currentDepth) throws Exception
	{
		String parentOidLong = "";
		ArrayList list = null;
		if(parent == null) {
			parentOidLong = rootOid;
			list = NumberCodeHelper.getTopNumberCode(NumberCodeType.toNumberCodeType(rootOid));
		}
		else {
			parentOidLong = ""+CommonUtil.getOIDLongValue(parent);
			list = NumberCodeHelper.getChildNumberCode(parent);
		}

		String nodeName = "";
		String oidLong = "";

		for(int i =0; i < list.size(); i++) {
			NumberCode child =(NumberCode)list.get(i);
			oidLong = ""+CommonUtil.getOIDLongValue(child);

			if("WORKCENTER".equals(codetype.toString())){
				nodeName = StringUtil.changeString(child.getCode()+" | "+child.getName(), "'", "&#39;");
			}else{
				nodeName = StringUtil.changeString(child.getName(), "'", "&#39;");
			//nodeName = StringUtil.changeString(child.getCode()+" | "+child.getName(), "'", "&#39;");
			}
			out.print("var node"+oidLong+" = new TreeNode('"+nodeName+"');");
			out.print("node"+oidLong+".put('oid', '"+CommonUtil.getOIDString(child)+"');");

			if(rootOid.equals(parentOidLong))
				out.print("tree.root.add(node"+oidLong+");");
			else
				out.print("node"+parentOidLong+".add(node"+oidLong+");");

			if(!"MATERIALTYPE".equals(codetype.toString())){//원재료 type 선택시 무한루프 현상때문에 막음
			    if( (expandedDepth == -1) || (currentDepth < expandedDepth) ) {
				    maketree(out, codetype, child, rootOid, expandedDepth, (currentDepth+1));
		        }
			}
			
		}
	}
%>

</body>
