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

System.out.println("ecoNumber=============>"+ecoNumber);
System.out.println("gubun=============>"+gubun);


String newOid = "";

WTPart part = null;
if(partOid.indexOf("wt.part.WTPart:")!=-1)
{
    part = pUtil.getPart(partOid);
}else 
{
    newOid = "wt.part.WTPart:"+partOid;
    part = pUtil.getPart(newOid);
    String tpartnumber = part.getNumber();
	String trev = wt.vc.VersionControlHelper.getVersionIdentifier(part).getValue();
	
	String bomFlag = bean.partNewOld(tpartnumber, trev);
	System.out.println("bomFlag=============>"+bomFlag);
	/*
	if(!bomFlag.equals("NEW"))
	{
		String prePartOid =  bean.getLatestReleasedPart2(tpartnumber);
		//partOid = prePartOid;
		System.out.println("prePartOid==>"+prePartOid);
		part = pUtil.getPart(prePartOid);
	}*/
	
	
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

if(startNo!=null && !startNo.equals(""))
{
    resultList = bean.convertTreeList(treeList, "partNo", startNo, "partNo", "parentNo");
    
}

JSONArray jsonArray = new JSONArray(resultList);
String  jsondata =jsonArray.toJSONString();

//System.out.println(jsondata);
System.out.println("partOid==>"+partOid);
System.out.println("partType==>"+partType);

PrintWriter pw = response.getWriter();
//System.out.println(jsonArray.toJSONString());
pw.print(jsondata);
pw.flush();
pw.close();


%>
