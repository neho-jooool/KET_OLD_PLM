<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="e3ps.project.ProductProject"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.MoldItemInfo"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.query.SearchCondition"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%!

public static void updateMold()throws Exception{
    QuerySpec spec = new QuerySpec(MoldItemInfo.class);
    QueryResult rs = PersistenceHelper.manager.find(spec);

    while(rs.hasMoreElements()){
        MoldItemInfo itemInfo = (MoldItemInfo)rs.nextElement();
        ProductProject project = null;
        try{
            project = (ProductProject)itemInfo.getProject();
        }catch(Exception e){

        }

        if(project != null){
            project = getProject(project.getPjtNo());

            itemInfo.setProject(project);
            PersistenceHelper.manager.save(itemInfo);
            Kogger.debug("update..");
        }
    }
    Kogger.debug("ok..");
}

public static ProductProject getProject(String pjtNo)throws Exception{

    QuerySpec spec = new QuerySpec(ProductProject.class);

    spec.appendWhere(new SearchCondition(ProductProject.class, ProductProject.PJT_NO, "=", pjtNo), new int[]{0});

    if(spec.getConditionCount() > 0)
        spec.appendAnd();
    spec.appendWhere(new SearchCondition(ProductProject.class, "lastest", SearchCondition.IS_TRUE, true ),new int[] {0});

    if(spec.getConditionCount() > 0)
        spec.appendAnd();
    spec.appendWhere(new SearchCondition(ProductProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),new int[] {0});


    ProductProject project = null;

    QueryResult rs = PersistenceHelper.manager.find(spec);

    if(rs.hasMoreElements()){
        project = (ProductProject)rs.nextElement();
    }
    return project;
}
%>

<%
QuerySpec spec = new QuerySpec(MoldItemInfo.class);
QueryResult rs = PersistenceHelper.manager.find(spec);

while(rs.hasMoreElements()){
    MoldItemInfo itemInfo = (MoldItemInfo)rs.nextElement();
    ProductProject project = null;
    try{
        project = (ProductProject)itemInfo.getProject();
    }catch(Exception e){
	    Kogger.debug("kkkk = " + itemInfo.getDieNo())
    }
}

%>
</body>
</html>
