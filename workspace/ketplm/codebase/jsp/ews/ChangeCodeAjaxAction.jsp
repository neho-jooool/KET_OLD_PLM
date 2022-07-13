<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="wt.fc.*, 
								wt.httpgw.*,
								wt.org.*,
								wt.query.*,
								wt.team.*,
								e3ps.common.web.ParamUtil,
								e3ps.common.code.NumberCode,
								e3ps.common.code.NumberCodeHelper,
								e3ps.common.util.*"%>

<%@include file="/jsp/common/context.jsp"%>
<stdinfo>
	<results>
		<contents>
			
		<data_info>
<%	
		String parentValue = request.getParameter("parentValue");
		String child = ParamUtil.getParameter(request, "child");
		
		parentValue= java.net.URLDecoder.decode(parentValue==null?"":parentValue,"euc-kr");
		child= java.net.URLDecoder.decode(child==null?"":child,"euc-kr");
		
		QuerySpec qs = new QuerySpec();
		int idx = qs.appendClassList(NumberCode.class, true);
		if(parentValue!=null){
		SearchCondition sc1 = new SearchCondition(NumberCode.class, "code", "=", parentValue);
		qs.appendWhere(sc1, new int[] {idx});
		
		qs.appendAnd();
		}
		SearchCondition sc2 = new SearchCondition(NumberCode.class, "codeType", "=", "PROBLEMTYPE");
		qs.appendWhere(sc2, new int[] {idx});
		
		QueryResult qr = PersistenceHelper.manager.find( qs );
		
		String numCodeName = "";
		String numCodeOid = "";
		
		while(qr.hasMoreElements()){
			Object[] o = (Object[])qr.nextElement();
	    NumberCode nCode = (NumberCode) o[0];
	    	    
	    ArrayList list = NumberCodeHelper.getChildNumberCode(nCode);

			for(int i = 0 ; i < list.size(); i++){
			  numCodeOid = ((NumberCode)list.get(i)).getCode();
			  numCodeName = ((NumberCode)list.get(i)).getName();

%>
			<l_name><![CDATA[<%=numCodeName%>]]></l_name>
			<l_oid><![CDATA[<%=numCodeOid%>]]></l_oid>
<%
			}
		}
%>
    <message>
			<l_child><![CDATA[<%=child%>]]></l_child>
		</message>
    </data_info>
		</contents>
	</results>
</stdinfo>


