<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*, wt.org.*,wt.query.*,wt.team.*,wt.project.Role"%>
<%@page import="e3ps.common.util.*,e3ps.groupware.company.*"%>
<%@page import="e3ps.project.*,e3ps.project.beans.*"%>
<%@page import="e3ps.wbs.*,e3ps.wbs.service.*"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<%@include file="/jsp/common/context.jsp"%>
<stdinfo>
	<results>
		<contents>
<%
	String message = "false";
	String command = request.getParameter("command");
	command= java.net.URLDecoder.decode(command==null?"":command,"euc-kr");

	if("autoComplete".equals(command)) {
%>
		<data_info>
<%
		message = "true";
		String wbsName = request.getParameter("cname");
		
		Kogger.debug("wbsName ================= " + wbsName);
		
		QuerySpec spec = new QuerySpec(WBSItem.class);
		
		spec.appendWhere(new SearchCondition(WBSItem.class, "wbsName", SearchCondition.LIKE, "%" + wbsName + "%"), new int[]{0});
		
		QueryResult rs = PersistenceHelper.manager.find(spec);
		
		Kogger.debug("rs.size() = " + rs.size());
		
		while(rs.hasMoreElements()){
			WBSItem item = (WBSItem)rs.nextElement();
			String name = item.getWbsName();
			String itemOid = CommonUtil.getOIDString(item);
			String code = item.getWbsCode();
%>
				<l_code><![CDATA[<%=code%>]]></l_code>
				<l_name><![CDATA[<%=name%>]]></l_name>
				<l_oid><![CDATA[<%=itemOid%>]]></l_oid>
<%
		}
%>
		</data_info>
<%	
	}

%>
				<message>
					<l_message><![CDATA[<%=message%>]]></l_message>
				</message>
			</contents>
		</results>
	</stdinfo>
