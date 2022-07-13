<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String[] usageOids = request.getParameterValues("usageOid");
    
%>

<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="wt.part.WTPartUsageLink"%>
<%@page import="e3ps.bom.entity.KETPartUsageLink"%>
<%@page import="wt.part.WTPartMaster"%>
<%@page import="wt.httpgw.WTURLEncoder"%>
<stdinfo>
			<results>
				<contents>
					<data_info>
				    <%  for(int i = 0; usageOids != null && i < usageOids.length; i++){
				    		String usageOid = WTURLEncoder.decode(usageOids[i]);
				    		Object link = CommonUtil.getObject(usageOid);
				    		String number = "";
				    		String name = "";
				    		String quantity = "";
							String material = "";
							if(link instanceof KETPartUsageLink){
								
								KETPartUsageLink ketLink = (KETPartUsageLink)link;
								material = ketLink.getMaterial();
								quantity = ketLink.getQuantity();
								WTPartMaster master = (WTPartMaster)ketLink.getUses();
							    number = master.getNumber();
							    name = master.getName();
								
							}else if(link instanceof WTPartUsageLink){
								
								WTPartUsageLink usageLink = (WTPartUsageLink)link;
								int q = (int)usageLink.getQuantity().getAmount();
								quantity = String.valueOf(q);
								WTPartMaster master = (WTPartMaster)usageLink.getUses();
							    number = master.getNumber();
							    name = master.getName();
								
							}
							
							if(material == null){
								material = "";
							}
							if(quantity == null){
								quantity = "";
							}
				    
				    %>
					
						<l_usageOid><![CDATA[<%=usageOid%>]]></l_usageOid>
						<l_number><![CDATA[<%=number%>]]></l_number>
						<l_name><![CDATA[<%=name%>]]></l_name>
						<l_quantity><![CDATA[<%=quantity%>]]></l_quantity>
						<l_material><![CDATA[<%=material%>]]></l_material>
					
					<%}%>
					</data_info>
					<message>
						<l_message><![CDATA[<%="ggggfffffmmm"%>]]></l_message>
					</message>
				</contents>
			</results>
</stdinfo>
