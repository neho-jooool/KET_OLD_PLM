<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.project.moldPart.beans.MoldPartHelper"%>


<%
	String dieNo = request.getParameter("dieNo");

	QueryResult rs = new QueryResult();	
	
	if(dieNo != null && dieNo.length() > 0){
		rs = MoldPartHelper.getMoldProject(dieNo + "*");
		
	}
	
%>



<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.MoldItemInfo"%>
<stdinfo>
			<results>
				<contents>
					<data_info>
						<%while(rs.hasMoreElements()){
							String dieNumber = "";
							String projectOid = "";
							Object o[] = (Object[])rs.nextElement();
							MoldProject project = (MoldProject)o[0];
							projectOid = CommonUtil.getOIDString(project);
							
							MoldItemInfo mitem = project.getMoldInfo();
							dieNumber = mitem.getDieNo();
							
						%>
						
							<l_dieNo><![CDATA[<%=dieNumber%>]]></l_dieNo>
							<l_projectOid><![CDATA[<%=projectOid%>]]></l_projectOid>
						<%}%>
					</data_info>
					<message>
						<l_message><![CDATA[<%="ggggfffffmmm"%>]]></l_message>
					</message>
				</contents>
			</results>
</stdinfo>
