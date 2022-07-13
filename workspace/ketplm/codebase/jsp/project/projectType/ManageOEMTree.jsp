<%@page import="java.util.Collections"%>
<%@page import="java.util.Comparator"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.common.util.CommonUtil,java.util.ArrayList"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>

<%@page import="e3ps.project.beans.OEMTypeHelper"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="e3ps.project.outputtype.OEMType"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.common.query.SearchUtil"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="wt.query.OrderBy"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<html>
<head>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script src="/plm/portal/js/menu.js"></script>
<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/dtree.js"></script>
<script src="/plm/portal/js/newtree.js"></script>
<%
	String oemtype = StringUtil.trimToEmpty(request.getParameter("oemtype"));
	String codetype = StringUtil.trimToEmpty(request.getParameter("codetype"));
	OEMType oemCode = OEMType.toOEMType(oemtype);
	String nation = StringUtil.trimToEmpty(request.getParameter("nation"));
	String code = "";
	
	if(CommonUtil.isMember("국내차종관리")){
	    code = "1000";
	}
	
	if(CommonUtil.isMember("국외차종관리")){
	    code = "2000";
	}
	
	QuerySpec qs = new QuerySpec();
	int idx = qs.appendClassList(NumberCode.class, true);
	SearchCondition sc2 = new SearchCondition(NumberCode.class, "codeType", "=", codetype);
	qs.appendWhere(sc2, new int[] {idx});
	
	if(StringUtils.isNotEmpty(code)){
	    qs.appendAnd();
	    SearchCondition sc2_1 = new SearchCondition(NumberCode.class, "code", "=", code);
	    qs.appendWhere(sc2_1, new int[] {idx});
	}
	
	qs.appendAnd();
	SearchCondition sc3 = new SearchCondition(NumberCode.class, "parentReference.key.classname", true);
	qs.appendWhere(sc3, new int[] {idx});
	
	ClassAttribute ca2 = new ClassAttribute(NumberCode.class,"code");
	qs.appendOrderBy(new OrderBy(ca2, false), new int[] { idx }); 
	
	Kogger.debug("tree qs = " + qs);
	
	QueryResult qr = PersistenceHelper.manager.find( qs );
	
	String numCodeName = "";
	String numCodeOid = "";
	
	
%>
<BODY>
<form name=tree method=post>
<input type=button value='<%=messageService.getString("e3ps.message.ket_message", "01768") %><%--새로고침--%>' onclick='location.reload()' id='innerbutton'>
<input type='hidden' name='oemtype' value='<%=oemtype%>'>
<input type='hidden' name='codetype' value='<%=codetype%>'>
<DIV id="scrollbox">
<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
	<tr>
		<td valign="top" align="left">
		  <table border=0>
			  <tr>
				  <td>
					  <table oncontextmenu="return false" onselectstart="return false"  id=treeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
				  </td>
			  </tr>
		  </table>
		</td>
	</tr>
</table>
</DIV>
<script type="text/javascript">
	var target = eval("parent.main");
	var tree = new Tree("treeTable","<%=oemCode.getComment()%>","/plm/portal/icon/tree/board");
	tree.selectionMode = tree.DISCONTIGUOUS_TREE_SELECTION;
	tree.treeSelectionListener = function(node, event){
		this.selectNode(node, event);
		tree.selectedNode[0].get("oid");
		target.document.location.href="/plm/jsp/project/projectType/ViewOEMType.jsp?oid="+tree.selectedNode[0].get("oid")+"&oemtype=<%=oemtype%>";
	}
<%
ArrayList nodeList = OEMTypeHelper.getList(oemtype);
out.print("tree.root.put(\"oid\", \"root\");");

while(qr.hasMoreElements()){
	Object[] o = (Object[])qr.nextElement();
    NumberCode nCode = (NumberCode) o[0];
    String ncNodeId = nCode.getPersistInfo().getObjectIdentifier().getStringValue();
    long oidLong = CommonUtil.getOIDLongValue(ncNodeId);
    String ncNodeName = nCode.getName();
    String venderCode = StringUtil.checkNull(nCode.getVenderCode());
  
    %>
    
    var node<%=oidLong%> = new TreeNode("<%=ncNodeName%>");
	node<%=oidLong%>.put("oid", "<%=ncNodeId%>");
	tree.root.add(node<%=oidLong%>);
    
    
    <%
    if(oemtype.equals("PAYCONTRACTOR")){
    	QuerySpec qs4 = new QuerySpec();
		int idx4 = qs4.appendClassList(OEMProjectType.class, true);
		
		SearchCondition sc4 = new SearchCondition(OEMProjectType.class, "makerReference.key.id", "=", oidLong );
		qs4.appendWhere(sc4, new int[] {idx4});
		qs4.appendAnd();
		SearchCondition sc5 = new SearchCondition(OEMProjectType.class, "oemType", "=", oemtype);
		qs4.appendWhere(sc5, new int[] {idx4});
		ClassAttribute ca = new ClassAttribute(OEMProjectType.class,"code");
		qs4.appendOrderBy(new OrderBy(ca, false), new int[] { idx4 }); 
		
		QueryResult qr4 = PersistenceHelper.manager.find( qs4 );
		
		while(qr4.hasMoreElements()){
			Object[] o2 = (Object[])qr4.nextElement();
			OEMProjectType opt = (OEMProjectType)o2[0];
			String oNodeId = opt.getPersistInfo().getObjectIdentifier().getStringValue();
			long oLong = CommonUtil.getOIDLongValue(oNodeId);
			String optName = opt.getName();
			%>
				var node<%=oLong%> = new TreeNode("<%=optName%>");
				node<%=oLong%>.put("oid", "<%=oNodeId%>");
				node<%=oidLong%>.add(node<%=oLong%>);
			<%
		}
    }
    
    ArrayList list = NumberCodeHelper.getChildNumberCode2(nCode, "");
    
    Collections.sort(list, new Comparator<NumberCode>() {
        @Override
        public int compare(NumberCode o1, NumberCode o2) {
        if (o1 == null || o2 == null) {
            return 0;
        }
        return o1.getName().compareTo(o2.getName());
        }
    });
    
	for(int i = 0 ; i < list.size(); i++){
	  numCodeOid = ((NumberCode)list.get(i)).getPersistInfo().getObjectIdentifier().getStringValue();
	  long numLong = CommonUtil.getOIDLongValue(numCodeOid);
	  numCodeName = ((NumberCode)list.get(i)).getName();
	  
	%>
	var node<%=numLong%> = new TreeNode("<%=numCodeName%>");
	node<%=numLong%>.put("oid", "<%=numCodeOid%>");
	node<%=oidLong%>.add(node<%=numLong%>);

	<%
		QuerySpec qs4 = new QuerySpec();
		int idx4 = qs4.appendClassList(OEMProjectType.class, true);
		
		SearchCondition sc4 = new SearchCondition(OEMProjectType.class, "makerReference.key.id", "=", numLong );
		qs4.appendWhere(sc4, new int[] {idx4});
		qs4.appendAnd();
		SearchCondition sc5 = new SearchCondition(OEMProjectType.class, "oemType", "=", oemtype);
		qs4.appendWhere(sc5, new int[] {idx4});
		ClassAttribute ca = null;
		if("CARTYPE".equals(oemtype)){
			ca = new ClassAttribute(OEMProjectType.class,"name");
		}else{
			ca = new ClassAttribute(OEMProjectType.class,"code");
		}
		qs4.appendOrderBy(new OrderBy(ca, false), new int[] { idx4 }); 
		
		QueryResult qr4 = PersistenceHelper.manager.find( qs4 );
		
		while(qr4.hasMoreElements()){
			Object[] o2 = (Object[])qr4.nextElement();
			OEMProjectType opt = (OEMProjectType)o2[0];
			String oNodeId = opt.getPersistInfo().getObjectIdentifier().getStringValue();
			long oLong = CommonUtil.getOIDLongValue(oNodeId);
			String optName = opt.getName();
			%>
				var node<%=oLong%> = new TreeNode("<%=optName%>");
				node<%=oLong%>.put("oid", "<%=oNodeId%>");
				node<%=numLong%>.add(node<%=oLong%>);
			<%
		}
	}
}
%>
//    tree.expandAll();
	tree.repaint();
</SCRIPT>
</form>
<%OEMTypeHelper.getList(); %>
</body>
</html>
