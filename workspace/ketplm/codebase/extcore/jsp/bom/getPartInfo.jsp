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
List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

Hashtable params = new Hashtable();



String partNo = request.getParameter("partNo");
String ecoNumber = KETStringUtil.nvl((String)request.getParameter("ecoNumber"), "");
String gubun = KETStringUtil.nvl((String)request.getParameter("gubun"), "");
String partOid = "";
String partType = "";
String startNo = partNo;

WTPart part = pUtil.getLatestPart(partNo);

if(part!=null)
{
    long id = pUtil.getPartLongId(part);
    partOid = Long.toString(id);
    partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
    
    params.put("partOid",partOid);
    params.put("partType",partType);
    params.put("dataType","TreeGrid");
    params.put("ecoNumber",ecoNumber);
    params.put("gubun",gubun);
    
    treeList = bean.getLatestBOM(params);
    
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
