<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="e3ps.common.content.uploader.*"%>
<%@ page import="java.io.*" %>
<%@page import=" ext.ket.bom.query.*"%>
<%@page import=" ext.ket.bom.util.*"%>
<%@page import=" org.json.*"%>
<%@ page import="wt.part.*"%>
<%@ page import="ext.ket.part.entity.dto.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%

//PrintWriter pw;
KETBOMQueryBean bean = new KETBOMQueryBean();
KETBomPartUtil pUtil = new KETBomPartUtil();

String partNo = (String)request.getParameter("partNo");
String partRev = (String)request.getParameter("partRev");

//partNo = "625447";
if(partRev==null)
    partRev = "";

String partName = "";
//pw = response.getWriter();
List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

result = bean.getPartRevList(partNo, partRev);

if(result!=null && result.size()>0)
{
    Hashtable data = (Hashtable)result.get(0);
    partName = (String)data.get("partName");
}
System.out.println("================================================");

JSONArray jsonArray = new JSONArray(result);
String  jsondata =jsonArray.toJSONString();
//pw.print(result);
//pw.flush();
//pw.close();
%>
{
"partName" : "<%=partName %>",
"RevList": <%=jsondata %>
}