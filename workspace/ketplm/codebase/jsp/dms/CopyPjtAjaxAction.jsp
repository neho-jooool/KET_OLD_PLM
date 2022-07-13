<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="wt.fc.*, 
								wt.httpgw.*,
								wt.org.*,
								wt.query.*,
								wt.team.*,
								e3ps.project.E3PSProject,
								e3ps.project.beans.E3PSProjectData,
								e3ps.project.beans.ProductHelper,
								e3ps.project.ProductInfo,
								e3ps.project.ModelInfo,
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
		String pjtOid = request.getParameter("pjtOid");
		pjtOid= java.net.URLDecoder.decode(pjtOid==null?"":pjtOid,"euc-kr");
		
		E3PSProject project = (E3PSProject)CommonUtil.getObject(pjtOid);
		E3PSProjectData projectData = new E3PSProjectData(project);
		
		//개발제품명
		String pjtName = projectData.pjtName;
%> 
    <l_pjtName><![CDATA[<%=pjtName%>]]></l_pjtName>
<%		
		//최종사용처
		NumberCode nc = null;
		String numCodeName = "";
		String numCodeOid = "";
		
		if(projectData.customereventVec != null && projectData.customereventVec.size() > 0) {
			for(int inx = 0; inx < projectData.customereventVec.size(); inx++) {
				nc = (NumberCode)projectData.customereventVec.get(inx);
				numCodeName = nc.getName();
			  numCodeOid = CommonUtil.getOIDString(nc);
			  
%>
			  <l_endName><![CDATA[<%=numCodeName%>]]></l_endName>
			  <l_endOid><![CDATA[<%=numCodeOid%>]]></l_endOid>
<%
			}
		}
		
		//의뢰처
		if(projectData.subcontractorVec != null && projectData.subcontractorVec.size() > 0) {
			for(int inx = 0; inx < projectData.subcontractorVec.size(); inx++) {
				nc = (NumberCode)projectData.subcontractorVec.get(inx);
				numCodeName = nc.getName();
			  numCodeOid = CommonUtil.getOIDString(nc);
%>
			  <l_reqName><![CDATA[<%=numCodeName%>]]></l_reqName>
			  <l_reqOid><![CDATA[<%=numCodeOid%>]]></l_reqOid>
<%
			}
		}
		
		//제품정보
		QuerySpec qs = new QuerySpec();
		int idxpi = qs.appendClassList(ProductInfo.class, true);
		
		SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id" , "=", CommonUtil.getOIDLongValue(pjtOid));
    qs.appendWhere(cs, new int[] { idxpi } );
    
    QueryResult qrpi = PersistenceHelper.manager.find(qs);
    
    ProductInfo pi = null;
    String pOid = null;
    String productName = null;
    String carName = null;
    String carName1 = null;
    String carName2 = null;
    String productArea = null;
    String us = null;
    String price = null;
    String cost = null;
    String profit = null;
    while(qrpi.hasMoreElements()){
    	Object o[] = (Object[])qrpi.nextElement();
    	pi = (ProductInfo)o[0];
    	
    	pOid = pi.getPersistInfo().getObjectIdentifier().toString();
    	productName = pi.getPName()==null?"":pi.getPName();
    	carName1 = ProductHelper.getCarName(pi.getPersistInfo().getObjectIdentifier().toString());
    	carName2 = ProductHelper.getCarName2(pi.getPersistInfo().getObjectIdentifier().toString());
    	carName = carName1==""?carName2:carName1;
    	productArea = pi.getAreas()==null?"":pi.getAreas();
    	us = pi.getUsage()==null?"":pi.getUsage();
    	price = pi.getPrice()==null?"":pi.getPrice();
    	cost = pi.getCost()==null?"":pi.getCost();
    	profit = pi.getRate()==null?"":pi.getRate();  
%>
			<l_pOid><![CDATA[<%=pOid%>]]></l_pOid>
			<l_productName><![CDATA[<%=productName%>]]></l_productName>
			<l_carName><![CDATA[<%=carName%>]]></l_carName>
			<l_productArea><![CDATA[<%=productArea%>]]></l_productArea>
			<l_us><![CDATA[<%=us%>]]></l_us>
			<l_price><![CDATA[<%=price%>]]></l_price>
			<l_cost><![CDATA[<%=cost%>]]></l_cost>
			<l_profit><![CDATA[<%=profit%>]]></l_profit>
<%
			if(pOid != null && !pOid.equals("")) {		
				Long oidLong = CommonUtil.getOIDLongValue(pOid);
				
				QuerySpec qs2 = new QuerySpec();
				int idx = qs2.addClassList(ModelInfo.class, true);
				
				SearchCondition sc = new SearchCondition(ModelInfo.class, "productReference.key.id" , "=" , oidLong );
				qs2.appendWhere(sc, new int[] { idx });
				
				QueryResult qr = PersistenceHelper.manager.find(qs2);
				
				int t1 = 0;
				int t2 = 0;
				int t3 = 0;
				int t4 = 0;
				int t5 = 0;
				int t6 = 0;
				int t7 = 0;
				int t8 = 0;
				int t9 = 0;
				int t10 = 0;

				while(qr.hasMoreElements()){
					Object o2[] = (Object[])qr.nextElement();
					ModelInfo mi = (ModelInfo)o2[0];
					int y1 = Integer.parseInt(mi.getYear1()==null?"0":mi.getYear1());
					int y2 = Integer.parseInt(mi.getYear2()==null?"0":mi.getYear2());
	        int y3 = Integer.parseInt(mi.getYear3()==null?"0":mi.getYear3());
	        int y4 = Integer.parseInt(mi.getYear4()==null?"0":mi.getYear4());
	        int y5 = Integer.parseInt(mi.getYear5()==null?"0":mi.getYear5());
	        int y6 = Integer.parseInt(mi.getYear6()==null?"0":mi.getYear6());
	        int y7 = Integer.parseInt(mi.getYear7()==null?"0":mi.getYear7());
	        int y8 = Integer.parseInt(mi.getYear8()==null?"0":mi.getYear8());
	        int y9 = Integer.parseInt(mi.getYear9()==null?"0":mi.getYear9());
	        int y10 = Integer.parseInt(mi.getYear10()==null?"0":mi.getYear10());
	        int sum = y1 + y2 + y3 + y4 + y5 + y6 + y7 + y8 + y9 + y10 ;
	        int sumY6 = y6 + y7 + y8 + y9 + y10 ;     
	        
	        t1 = t1 + Math.round(y1*Integer.parseInt(mi.getUsage())*Integer.parseInt(StringUtil.checkNullZero(mi.getOptionRate()))/100);
	        t2 = t2 + Math.round(y2*Integer.parseInt(mi.getUsage())*Integer.parseInt(StringUtil.checkNullZero(mi.getOptionRate()))/100);
	        t3 = t3 + Math.round(y3*Integer.parseInt(mi.getUsage())*Integer.parseInt(StringUtil.checkNullZero(mi.getOptionRate()))/100);
	        t4 = t4 + Math.round(y4*Integer.parseInt(mi.getUsage())*Integer.parseInt(StringUtil.checkNullZero(mi.getOptionRate()))/100);
	        t5 = t5 + Math.round(y5*Integer.parseInt(mi.getUsage())*Integer.parseInt(StringUtil.checkNullZero(mi.getOptionRate()))/100);
	        t6 = t6 + Math.round(y6*Integer.parseInt(mi.getUsage())*Integer.parseInt(StringUtil.checkNullZero(mi.getOptionRate()))/100);
	        t7 = t7 + Math.round(y7*Integer.parseInt(mi.getUsage())*Integer.parseInt(StringUtil.checkNullZero(mi.getOptionRate()))/100);
	        t8 = t8 + Math.round(y8*Integer.parseInt(mi.getUsage())*Integer.parseInt(StringUtil.checkNullZero(mi.getOptionRate()))/100);
	        t9 = t9 + Math.round(y9*Integer.parseInt(mi.getUsage())*Integer.parseInt(StringUtil.checkNullZero(mi.getOptionRate()))/100);
	        t10 = t10 + Math.round(y10*Integer.parseInt(mi.getUsage())*Integer.parseInt(StringUtil.checkNullZero(mi.getOptionRate()))/100);
%>	        
					<l_mpOid><![CDATA[<%=pOid%>]]></l_mpOid>
					<l_mOid><![CDATA[<%=mi.getPersistInfo().getObjectIdentifier().toString()%>]]></l_mOid>
					<l_mName><![CDATA[<%=mi.getName()==null?mi.getModel().getName():mi.getName()%>]]></l_mName>
					<l_y1><![CDATA[<%=y1%>]]></l_y1>
					<l_y2><![CDATA[<%=y2%>]]></l_y2>
					<l_y3><![CDATA[<%=y3%>]]></l_y3>
					<l_y4><![CDATA[<%=y4%>]]></l_y4>
					<l_y5><![CDATA[<%=y5%>]]></l_y5>
					<l_y6><![CDATA[<%=sumY6%>]]></l_y6>
					<l_sum><![CDATA[<%=sum%>]]></l_sum>
					<l_usage><![CDATA[<%=mi.getUsage()%>]]></l_usage>
					<l_option><![CDATA[<%=mi.getOptionRate()%>]]></l_option>
<%
	      }	
%>
        <l_t1><![CDATA[<%=t1%>]]></l_t1>
        <l_t2><![CDATA[<%=t2%>]]></l_t2>
        <l_t3><![CDATA[<%=t3%>]]></l_t3>
        <l_t4><![CDATA[<%=t4%>]]></l_t4>
        <l_t5><![CDATA[<%=t5%>]]></l_t5>
        <l_t6><![CDATA[<%=t6+t7+t8+t9+t10%>]]></l_t6>
        <l_tsum><![CDATA[<%=t1+t2+t3+t4+t5+t6+t7+t8+t9+t10%>]]></l_tsum>
<%
	    }
		}
%>			
    </data_info>
		</contents>
	</results>
</stdinfo>


