<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.moldPart.beans.MoldPartHelper"%>
<%@page import="e3ps.bom.entity.KETPartUsageLink"%>
<%@page import="wt.part.WTPart"%>

<%@page import="wt.vc.views.ViewHelper"%>
<%@page import="wt.vc.views.View"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.project.ProjectMaster"%>
<%@page import="e3ps.project.MoldItemInfo"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<% 

	QuerySpec spec = new QuerySpec(MoldProject.class);
	
	spec.appendWhere(new SearchCondition(MoldProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true") ),new int[] {0});
	
	if(spec.getConditionCount() > 0) 
		spec.appendAnd();
	spec.appendWhere(new SearchCondition(MoldProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),new int[] {0});
	
	
	MoldProject project = null;
	
	QueryResult rs = PersistenceHelper.manager.find(spec);
	
	while(rs.hasMoreElements()){
		project = (MoldProject)rs.nextElement();
		MoldItemInfo info = project.getMoldInfo();
		ProjectMaster master = project.getMaster();
		if(info != null){
			master.setPjtName(info.getPartName());
			PersistenceHelper.manager.save(master);
			out.println("save = " + info.getPartName());
		}
	}

%>
</body>
</html>
