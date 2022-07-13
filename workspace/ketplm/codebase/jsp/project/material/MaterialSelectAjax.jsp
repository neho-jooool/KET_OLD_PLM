<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.project.moldPart.beans.MoldPartHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.part.WTPart"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

                
<%
	String actionType = request.getParameter("actionType");
	String material = request.getParameter("material");
	String maker = request.getParameter("maker");
	String type = request.getParameter("type");
	
	if(actionType != null){
		actionType = WTURLEncoder.decode(actionType);
	}
	
	if(material != null){
		material = WTURLEncoder.decode(material);
	}
	
	if(maker != null){
		maker = WTURLEncoder.decode(maker);
	}
	
	if(type != null){
		type = WTURLEncoder.decode(type);
	}
	
	QuerySpec qs = new QuerySpec();
	Vector oids = new Vector();
	Vector names = new Vector();
	Vector tMap = new Vector();
	if("maker".equals(actionType) && !"선택".equals(material)){
		
		int index1 = qs.addClassList(MoldMaterial.class, false);
		
		int index2 = qs.addClassList(NumberCode.class, true);
        
		qs.setDistinct(true);
		
        SearchCondition sc = new SearchCondition(
                new ClassAttribute(MoldMaterial.class, MoldMaterial.MATERIAL_MAKER_REFERENCE + ".key.id"), "=", new ClassAttribute(
                		NumberCode.class, "thePersistInfo.theObjectIdentifier.id"));
        sc.setOuterJoin(0);
        qs.appendWhere(sc, new int[] { index1, index2 });
		
        qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL, "=", material) , new int[]{index1});
		
		SearchUtil.setOrderBy(qs, NumberCode.class, index2, NumberCode.NAME , "cname", false);
        
		
		QueryResult rs = PersistenceHelper.manager.find(qs);
		while(rs.hasMoreElements()){
			Object[] o = (Object[])rs.nextElement();
			NumberCode makerCode = (NumberCode)o[0];
			oids.add(CommonUtil.getOIDString(makerCode));
			names.add(makerCode.getName());
		}
		
		
		tMap = NumberCodeHelper.manager.getNumberCodeLevelType("MATERIALPROPERTIES", material);		
		
		
	}
	
	else if("type".equals(actionType) && !"선택".equals(maker)){

		int index1 = qs.addClassList(MoldMaterial.class, false);
		
		int index2 = qs.addClassList(NumberCode.class, true);
        
		qs.setDistinct(true);
		
        SearchCondition sc = new SearchCondition(
                new ClassAttribute(MoldMaterial.class, MoldMaterial.MATERIAL_TYPE_REFERENCE + ".key.id"), "=", new ClassAttribute(
                		NumberCode.class, "thePersistInfo.theObjectIdentifier.id"));
        sc.setOuterJoin(0);
        qs.appendWhere(sc, new int[] { index1, index2 });
		
		
		
		long makerId = CommonUtil.getOIDLongValue(maker);
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL, "=", material) , new int[]{0});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL_MAKER_REFERENCE + ".key.id", "=", makerId) , new int[]{0});
		SearchUtil.setOrderBy(qs, NumberCode.class, index2, NumberCode.NAME , "cname", false);
		QueryResult rs = PersistenceHelper.manager.find(qs);
		while(rs.hasMoreElements()){
			Object[] o = (Object[])rs.nextElement();
			NumberCode typeCode = (NumberCode)o[0];
			oids.add(CommonUtil.getOIDString(typeCode));
			names.add(typeCode.getName());
		}
	}else if("grade".equals(actionType) && !"선택".equals(maker) && !"선택".equals(type)){
		int index1 = qs.addClassList(MoldMaterial.class, true);
		long makerId = CommonUtil.getOIDLongValue(maker);
		long typeId = CommonUtil.getOIDLongValue(type);
		qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL, "=", material) , new int[]{0});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL_MAKER_REFERENCE + ".key.id", "=", makerId) , new int[]{0});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL_TYPE_REFERENCE + ".key.id", "=", typeId) , new int[]{0});
		
		
		SearchUtil.setOrderBy(qs, MoldMaterial.class, index1, MoldMaterial.GRADE , "cname", false);
		
		QueryResult rs = PersistenceHelper.manager.find(qs);
		while(rs.hasMoreElements()){
			
			Object[] o = (Object[])rs.nextElement();
			MoldMaterial data = (MoldMaterial)o[0];
			
			oids.add(CommonUtil.getOIDString(data));
			names.add(data.getGrade());
		}
	}
	
//	Kogger.debug("oids.size() == " + oids.size() + " " + qs);
%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.util.CommonUtil"%>

<%@page import="com.ptc.ddl.servlet.WTURLEncoder"%><%@page import="wt.query.ClassAttribute"%>
<%@page import="e3ps.common.query.SearchUtil"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>

<%@page import="e3ps.project.material.MoldMaterial"%><stdinfo>
			<results>
				<contents>
					<data_info>
				   
						
					<%for(int i = 0; i < oids.size(); i++){
						String oid = (String)oids.get(i);
						String name = (String)names.get(i);
						%>
					
					
						<l_Oid><![CDATA[<%=oid%>]]></l_Oid>
						<l_name><![CDATA[<%=name%>]]></l_name>
					<%}%>	
					</data_info>
					<typeInfo>
						<type><![CDATA[<%=actionType%>]]></type>
					</typeInfo>
					<attribute>
					   <%for(int i = 0; i < tMap.size(); i++){
						   NumberCode code = (NumberCode)tMap.get(i);
						   String oid = CommonUtil.getOIDString(code);
						   String name = code.getName();
						%>
					
					
						<l_Oid><![CDATA[<%=oid%>]]></l_Oid>
						<l_name><![CDATA[<%=name%>]]></l_name>
					<%}%>	
					 
					</attribute>
					
					<message>
						<l_message><![CDATA[<%="ggggfffffmmm"%>]]></l_message>
					</message>
				</contents>
			</results>
</stdinfo>
