<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import=" ext.ket.bom.query.*"%>
<%@page import=" java.util.*"%>
<%@page import=" java.io.*"%>
<%@page import=" org.json.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%

KETBOMQueryBean bean = new KETBOMQueryBean();
PrintWriter pw;
List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

Hashtable params = new Hashtable();

String srcPartNo = request.getParameter("srcPartNo");
String srcPartRev = request.getParameter("srcPartRev");
String desPartNo = request.getParameter("desPartNo");
String desPartRev = request.getParameter("desPartRev");


try{
	treeList = bean.compareBOM(srcPartNo, srcPartRev, desPartNo, desPartRev);
	
	System.out.println("--------------------------------------"+treeList.toString());
	
	String startNo = "";
	
	if(treeList!=null && treeList.size()>0)
	{
	    Hashtable rootH = (Hashtable)treeList.get(0);
	    startNo = (String)rootH.get("partNo");
	}
	
	//startNo = srcPartNo;
	
	List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
	
	//System.out.println(startNo);
	if(startNo!=null && !startNo.equals(""))
	{
	    resultList = bean.convertTreeList(treeList, "partNo", startNo, "partNo", "parentNo");
	    
	}
	
	JSONArray jsonArray = new JSONArray(resultList);
	//String  jsondata =jsonArray.toJSONString();
	
	pw = response.getWriter();
	//System.out.println(jsonArray.toJSONString());
	pw.print(jsonArray);
	pw.flush();
	pw.close();
}catch(Exception e)
{
    e.printStackTrace();
}

%>
