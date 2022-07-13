<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import=" ext.ket.bom.query.*"%>
<%@page import=" java.util.*"%>
<%@page import=" java.io.*"%>
<%@page import=" org.json.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%


KETBOMQueryBean bean = new KETBOMQueryBean();

List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

Hashtable params = new Hashtable();
//bomUrl = "KETBOMEditList.jsp?gubun="+gubun+"&ecoNumber="+ecoNumber+"&partNumber="+partNumber;

String gubun = (String)request.getParameter("gubun");
String ecoNumber = (String)request.getParameter("ecoNumber");
String partNumber = (String)request.getParameter("partNumber");
String partType = (String)request.getParameter("partType");

params.put("gubun",gubun);
params.put("partType",partType);
params.put("ecoNumber",ecoNumber);
params.put("partNumber",partNumber);
params.put("inDelete","N");

params.put("dataType","TreeGrid");

treeList = bean.getEditBOM(params);

//System.out.println(treeList);

String startNo = "";
if(treeList!=null && treeList.size()>0)
{
    Hashtable rootH = (Hashtable)treeList.get(0);
    startNo = (String)rootH.get("partNo");
}
/*
for(int i=0;i<treeList.size();i++)
{
    Map<String, Object> data = (Map<String, Object>)treeList.get(i);
    
    String parentNo  = (String)data.get("parentNo");
    String partNo        = (String)data.get("partNo");
    
    if(!check.containsKey(parentNo+"_"+partNo))
    {
	    newTreeList.add(data);
    }
    
    check.put(parentNo+"_"+partNo, "");
}
*/

List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
//System.out.println(startNo);
if(startNo!=null && !startNo.equals(""))
{
    resultList = bean.convertTreeList(treeList, "partNo", startNo, "partNo", "parentNo");    
}

JSONArray jsonArray = new JSONArray(resultList);
String  jsondata =jsonArray.toJSONString();

PrintWriter pw = response.getWriter();
//System.out.println(jsonArray.toJSONString());
pw.print(jsonArray);
pw.flush();
pw.close();


%>
