<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.common.folder.FolderUtil,
				e3ps.common.util.CommonUtil,
				e3ps.common.jdf.config.Config,
				wt.folder.Folder"%>
<%@page import="e3ps.doc.DocCodeType"%>
<%@page import="e3ps.doc.beans.DocCodeTypeHelper"%>
<%@include file="/jsp/common/context.jsp"%>

<%
	String function = e3ps.common.web.ParamUtil.getStrParameter(request.getParameter("function"));
	String oneselectname = e3ps.common.web.ParamUtil.getStrParameter(request.getParameter("name0"), "회사표준");
%>
<%@page import="e3ps.doc.E3PSDocument"%>
<%@page import="java.util.ArrayList"%>
<%@page import="e3ps.common.code.GenNumberCode"%>
<%@page import="e3ps.common.jdf.config.ConfigImpl"%>
<%@page import="java.util.HashMap"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.common.web.ParamUtil"%>
<%@page import="e3ps.common.jdf.config.ConfigEx"%>

<jsp:include page="/jsp/common/select.jsp" flush="false">
	<jsp:param name="selectname" value="select"/>
	<jsp:param name="function" value="<%=function%>"/>
</jsp:include>
<SCRIPT LANGUAGE="JavaScript">
	var rootSelect = getRootSelect('select');
<%
	DocCodeType root = e3ps.doc.beans.DocCodeTypeHelper.getDocTypeForECO(oneselectname);
	String rootOID = CommonUtil.getOIDString(root);
	makeTree(out, root, rootOID);
%>
	printSelect('select');
</SCRIPT>

<%!
	HashMap map;
	public void makeTree(JspWriter out, DocCodeType parent, String parentOid) throws Exception
	{
		QueryResult result = PersistenceHelper.manager.navigate(parent, "child", e3ps.doc.DocCodeTypeParentChild.class);
		while(result.hasMoreElements())
		{
			DocCodeType codeType = (DocCodeType)result.nextElement();
			String id = ""+CommonUtil.getOIDLongValue(codeType);

			String parentId = ""+CommonUtil.getOIDLongValue(parent);

			out.println("var node"+id+" = new E3PSSelectNode('"+CommonUtil.getOIDString(codeType)+"','"+codeType.getName()+"');");

			out.println("node"+id+".etc1 = '"+codeType.getDescription()+"'");

			if(parentOid.equals(CommonUtil.getOIDString(parent)))
			{
			    out.println("rootSelect.add(node"+id+");");
			}
			else
			{
			    out.println("node"+parentId+".add(node"+id+");");
			}

			makeTree(out,codeType,id);
		}
	}
%>
