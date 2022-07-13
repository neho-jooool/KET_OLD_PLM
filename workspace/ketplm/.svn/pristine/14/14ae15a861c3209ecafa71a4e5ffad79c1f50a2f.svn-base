<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import=" ext.ket.bom.query.*"%>
<%@page import=" java.util.*"%>
<%@page import=" java.io.*"%>
<%@page import=" org.json.*"%>
<%@ page import="wt.part.WTPart"%>
<%@ page import="ext.ket.part.util.*"%>
<%@ page import="ext.ket.bom.util.KETBomPartUtil"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%

KETCad2BomQueryBean bean = new KETCad2BomQueryBean();
KETBOMQueryBean bean2 = new KETBOMQueryBean();

Hashtable params = new Hashtable();
String oid = (String)request.getParameter("modelOid");
params.put("modelOid", oid);


List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

List<Map<String, Object>> newTreeList = new ArrayList<Map<String, Object>>();

treeList = bean.getCad2BomViewData(params);

String startNo = "";
if(treeList!=null && treeList.size()>0)
{
    Hashtable rootH = (Hashtable)treeList.get(0);
    startNo = (String)rootH.get("modelNo");
}
/*
Hashtable check = new Hashtable();

for(int i=0;i<treeList.size();i++)
{
    Map<String, Object> data = (Map<String, Object>)treeList.get(i);
    
    String parentModelNo  = (String)data.get("parentModelNo");
    String modelNo        = (String)data.get("modelNo");
    
    if(!check.containsKey(parentModelNo+"_"+modelNo))
    {
	   newTreeList.add(data);
    }
    
    
    check.put(parentModelNo+"_"+modelNo, "");
}
*/

System.out.println(treeList);
//System.out.println(startNo);
if(startNo!=null && !startNo.equals(""))
{
    resultList = bean2.convertTreeList(treeList, "modelNo", startNo, "modelNo", "parentModelNo");
}

JSONArray jsonArray = new JSONArray(resultList);
String  jsondata =jsonArray.toJSONString();

//System.out.println(jsondata);

PrintWriter pw = response.getWriter();
//System.out.println(jsonArray.toJSONString());
pw.print(jsondata);
pw.flush();
pw.close();


%>
