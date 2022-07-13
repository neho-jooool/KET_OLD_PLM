<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import=" ext.ket.bom.query.*"%>
<%@page import=" java.util.*"%>
<%@page import=" java.io.*"%>
<%@page import=" org.json.*"%>
<%@ page import="wt.part.WTPart"%>
<%@ page import="ext.ket.part.util.*"%>
<%@ page import="ext.ket.bom.util.KETBomPartUtil"%>
<%@ page import="e3ps.common.util.KETStringUtil"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%


KETBOMQueryBean bean = new KETBOMQueryBean();
KETBomPartUtil pUtil = new KETBomPartUtil();

List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

List<Map<String, Object>> newTreeList = new ArrayList<Map<String, Object>>();

Hashtable params = new Hashtable();
String partOid = (String)request.getParameter("partOid");
String partType = (String)request.getParameter("partType");
String ecoNumber = KETStringUtil.nvl((String)request.getParameter("ecoNumber"), "");
String gubun = KETStringUtil.nvl((String)request.getParameter("gubun"), "");
String newOid = "";

WTPart part = null;
if(partOid.indexOf("wt.part.WTPart:")!=-1)
{
    part = pUtil.getPart(partOid);
    long id = pUtil.getPartLongId(part);
    partOid = Long.toString(id);
}

//System.out.println("partType==>"+partType);

params.put("partOid",partOid);
params.put("partType",partType);
params.put("dataType","TreeGrid");
params.put("ecoNumber",ecoNumber);
params.put("gubun",gubun);

treeList = bean.getLatestBOM(params);
//treeList = bean.getLatestVersionBOM(params);

//System.out.println(treeList);

String startNo = "";
if(treeList!=null && treeList.size()>0)
{
    Hashtable rootH = (Hashtable)treeList.get(0);
    startNo = (String)rootH.get("partNo");
    //System.out.println(startNo);
}


List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

if(startNo!=null && !startNo.equals(""))
{
    resultList = bean.convertTreeList(treeList, "partNo", startNo, "partNo", "parentNo");
    
}

JSONArray jsonArray = new JSONArray(resultList);
String  jsondata =jsonArray.toJSONString();

System.out.println(jsondata);
System.out.println("partOid==>"+partOid);
System.out.println("partType==>"+partType);

PrintWriter pw = response.getWriter();
//System.out.println(jsonArray.toJSONString());
pw.print(jsondata);
pw.flush();
pw.close();


%>
