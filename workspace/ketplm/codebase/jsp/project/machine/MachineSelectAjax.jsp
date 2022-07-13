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
	String moldType = request.getParameter("itemType");
	String type = request.getParameter("type");
	String ton = request.getParameter("ton");
	String maker = request.getParameter("maker");
	
	if(actionType != null){
		actionType = WTURLEncoder.decode(actionType);
	}
	
	if(moldType != null){
		moldType = WTURLEncoder.decode(moldType);
	}
	
	if(type != null){
		type = WTURLEncoder.decode(type);
	}
	
	if(ton != null){
		ton = WTURLEncoder.decode(ton);
	}
	
	if(maker != null){
		maker = WTURLEncoder.decode(maker);
	}
	
	QuerySpec qs = new QuerySpec();
	Vector oids = new Vector();
	Vector names = new Vector();
	Vector tMap = new Vector();
	if("ton".equals(actionType) && !"선택".equals(type)){
		
		int index1 = qs.addClassList(MoldMachine.class, false);
		
		int index2 = qs.addClassList(NumberCode.class, true);
        
		qs.setDistinct(true);
		
        SearchCondition sc = new SearchCondition(
                new ClassAttribute(MoldMachine.class, MoldMachine.TON_REFERENCE + ".key.id"), "=", new ClassAttribute(
                		NumberCode.class, "thePersistInfo.theObjectIdentifier.id"));
        sc.setOuterJoin(0);
        qs.appendWhere(sc, new int[] { index1, index2 });
		
        long typeId = CommonUtil.getOIDLongValue(type);
        qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MOLD_TYPE, "=", moldType) , new int[]{index1});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MACHINE_TYPE_REFERENCE + ".key.id", "=", typeId) , new int[]{0});
		
		SearchUtil.setOrderBy(qs, NumberCode.class, index2, NumberCode.NAME , "cname", false);
        
		
		QueryResult rs = PersistenceHelper.manager.find(qs);
		
		Collections.sort(rs.getObjectVectorIfc().getVector(), new TonComparator());
		while(rs.hasMoreElements()){
			Object[] o = (Object[])rs.nextElement();
			NumberCode tonCode = (NumberCode)o[0];
			oids.add(CommonUtil.getOIDString(tonCode));
			names.add(tonCode.getName());
		}
		
		
		
	}
	
	else if("maker".equals(actionType) && !"선택".equals(ton)){

		int index1 = qs.addClassList(MoldMachine.class, false);
		
		int index2 = qs.addClassList(NumberCode.class, true);
        
		qs.setDistinct(true);
		
        SearchCondition sc = new SearchCondition(
                new ClassAttribute(MoldMachine.class, MoldMachine.MACHINE_MAKER_REFERENCE + ".key.id"), "=", new ClassAttribute(
                		NumberCode.class, "thePersistInfo.theObjectIdentifier.id"));
        sc.setOuterJoin(0);
        qs.appendWhere(sc, new int[] { index1, index2 });
		
		
		
		long tonId = CommonUtil.getOIDLongValue(ton);
		long typeId = CommonUtil.getOIDLongValue(type);
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MOLD_TYPE, "=", moldType) , new int[]{0});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MACHINE_TYPE_REFERENCE + ".key.id", "=", typeId) , new int[]{0});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.TON_REFERENCE + ".key.id", "=", tonId) , new int[]{0});
		SearchUtil.setOrderBy(qs, NumberCode.class, index2, NumberCode.NAME , "cname", false);
		QueryResult rs = PersistenceHelper.manager.find(qs);
		while(rs.hasMoreElements()){
			Object[] o = (Object[])rs.nextElement();
			NumberCode makerCode = (NumberCode)o[0];
			oids.add(CommonUtil.getOIDString(makerCode));
			names.add(makerCode.getName());
		}
	}else if("model".equals(actionType) && !"선택".equals(ton) && !"선택".equals(maker)){
		int index1 = qs.addClassList(MoldMachine.class, true);
		long tonId = CommonUtil.getOIDLongValue(ton);
		long makerId = CommonUtil.getOIDLongValue(maker);
		long typeId = CommonUtil.getOIDLongValue(type);
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MOLD_TYPE, "=", moldType) , new int[]{0});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MACHINE_TYPE_REFERENCE + ".key.id", "=", typeId) , new int[]{0});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.TON_REFERENCE + ".key.id", "=", tonId) , new int[]{0});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MACHINE_MAKER_REFERENCE + ".key.id", "=", makerId) , new int[]{0});
		
		
		SearchUtil.setOrderBy(qs, MoldMachine.class, index1, MoldMachine.MODEL , "cname", false);
		
		QueryResult rs = PersistenceHelper.manager.find(qs);
		while(rs.hasMoreElements()){
			
			Object[] o = (Object[])rs.nextElement();
			MoldMachine data = (MoldMachine)o[0];
			
			oids.add(CommonUtil.getOIDString(data));
			names.add(data.getModel());
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

<%@page import="e3ps.project.machine.MoldMachine"%>
		
<%@page import="java.util.Collections"%>
<%@page import="e3ps.project.beans.TonComparator"%><stdinfo>
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
