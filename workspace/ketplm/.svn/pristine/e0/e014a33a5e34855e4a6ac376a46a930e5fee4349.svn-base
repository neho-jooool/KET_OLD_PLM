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

String partOid = request.getParameter("partOid");
String partNo = request.getParameter("partNumber");
String partRev = request.getParameter("partRev");
String level = request.getParameter("level");


String oid = bean.getUsageLinkChildPartOid(partNo, partRev);

if(oid==null || oid.equals(""))
{
    oid = bean.getUsageLinkParentPartOid(partNo);
}

if(oid==null || oid.equals(""))
{
    oid = partOid;
}

params.put("partOid",oid);
params.put("dataType","TreeGrid");
params.put("level",level);

System.out.println("역전개 oid===========>"+oid);
//try{
	//treeList = bean.getReverseBOM(params);
	treeList = bean.getReverseLatestBOM(params);
	//System.out.println(treeList);
	
	String startNo = "";
	if(treeList!=null && treeList.size()>0)
	{
	    Hashtable rootH = (Hashtable)treeList.get(0);
	    startNo = (String)rootH.get("partNo");
	}
	
	
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
//}catch(Exception e)
//{
//    throw e;
//}

%>
