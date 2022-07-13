<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>

<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.MoldProject"%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String oid = request.getParameter("oid");
		
	MoldProject project = (MoldProject)CommonUtil.getObject(oid);
	
	/*품번*/
	String partNumber = "";
	
	/*제품설계자*/
	String projectPlanerName = "";
	
	/*금형설계자*/
	String moldPlanerName = "";
	
	/*금형조립자*/
	String moldMakerName = "";
	/*품명*/
	String partName = "";
	
	/*원재료명*/
	String materialName = "";
	String materialOid = "";
	
	String propertyOid = "";
	String thickness = "";
	String width = "";
	
	/*제작처*/
	String outsourcingName = "";
	/*CAV수*/
	String cavSu = "";
	
	/*타입*/
	String itemType = "";
	
	/*설비(ton)*/
	String ton = "";
	
	MoldItemInfo mInfo = null;
	mInfo = project.getMoldInfo();
	Hashtable userH = TrySchduleHelper.getProjectRoleMember(project);
	
	WTUser moldPlaner = (WTUser)userH.get(TrySchduleData.MOLDPLANER_ROLE);
	
	if(moldPlaner != null){
		
		moldPlanerName = moldPlaner.getFullName();
	
	}
	
	WTUser moldMaker = (WTUser)userH.get(TrySchduleData.MOLDMAKER_ROLE);
	
	if(moldMaker != null){
		
		moldMakerName = moldMaker.getFullName();
	}  
  
	
	String code = project.getOutSourcing();
	
	if(code != null){
		
		outsourcingName = code;
	
	}
	
	if(mInfo != null){
		
		partNumber = mInfo.getPartNo();
		partName = mInfo.getPartName();
		ProductProject productProject = mInfo.getProject();
		WTUser projectPlaner = ProjectUserHelper.manager.getPM(productProject);
		
		if(projectPlaner != null){
			projectPlanerName = projectPlaner.getFullName();
		}
		itemType = mInfo.getItemType();
		cavSu = mInfo.getCVPitch();
		
		if(cavSu == null){
			cavSu = "";
		}
		
		MoldMaterial materialCode = mInfo.getMaterial();
		
		if(materialCode != null){
			
			materialOid = CommonUtil.getOIDString(materialCode);
			materialName = materialCode.getGrade();
			
			propertyOid = CommonUtil.getOIDString(mInfo.getProperty());
			if("비철".equals(materialCode.getMaterial())){
				thickness = mInfo.getThickness();
				width = mInfo.getWidth();
			}
			
		}
	}
	
	MoldMachine machine = project.getMoldMachine();
	if(machine != null){
		ton = machine.getModel() + "(" + machine.getTon().getName() + ")";
	}

%>



<%@page import="e3ps.project.trySchdule.beans.TrySchduleHelper"%>
<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.project.MoldItemInfo"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.groupware.company.Department"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.project.beans.ProjectUserHelper"%>

<%@page import="e3ps.project.trySchdule.beans.TryPlanData"%>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleData"%>
<%@page import="e3ps.project.material.MoldMaterial"%>
<%@page import="e3ps.project.machine.MoldMachine"%><stdinfo>
			<results>
				<contents>
					<data_info>
						<partNumber><![CDATA[<%=partNumber%>]]></partNumber>
						<projectPlanerName><![CDATA[<%=projectPlanerName%>]]></projectPlanerName>
						<moldPlanerName><![CDATA[<%=moldPlanerName%>]]></moldPlanerName>
						<moldMakerName><![CDATA[<%=moldMakerName%>]]></moldMakerName>
						<partName><![CDATA[<%=partName%>]]></partName>
						<materialName><![CDATA[<%=materialName%>]]></materialName>
						<materialOid><![CDATA[<%=materialOid%>]]></materialOid>
						<propertyOid><![CDATA[<%=propertyOid%>]]></propertyOid>
						<thickness><![CDATA[<%=thickness%>]]></thickness>
						<width><![CDATA[<%=width%>]]></width>
						<outsourcingName><![CDATA[<%=outsourcingName%>]]></outsourcingName>
						<cavSu><![CDATA[<%=cavSu%>]]></cavSu>
						<itemType><![CDATA[<%=itemType%>]]></itemType>
						<ton><![CDATA[<%=ton%>]]></ton>
					</data_info>
					<message>
						<l_message><![CDATA[]]></l_message>
					</message>
				</contents>
			</results>
</stdinfo>
