<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="request" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.collections.WTCollection"%>
<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleHelper"%>
<html>
<%
	String targetDay = request.getParameter("tagetDay");
	
    String oids[] = request.getParameterValues("oid");
    String tryPlans[] = request.getParameterValues("tryPlan");
    
    Hashtable hash = new Hashtable();
    Vector datas = new Vector();
    
    for(int i = 0; oids != null && i < oids.length; i++){
    	
    	Object obj = CommonUtil.getObject(oids[i]);
    	
    	datas.add(obj);
    }
    
	for(int i = 0; tryPlans != null && i < tryPlans.length; i++){
		
		hash.put(tryPlans[i], "");
    }
	
	TrySchduleHelper.setTryPlan(datas, hash);

    String redirectUrl = "./TryDaily.jsp?tagetDay=" + targetDay;
	response.sendRedirect(redirectUrl);

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>
