<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="e3ps.common.content.uploader.*"%>
<%@ page import="java.io.*" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFSheet" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFWorkbook" %>
<%@ page import="org.apache.poi.ss.usermodel.Cell" %>
<%@ page import="org.apache.poi.ss.usermodel.Row" %>
<%@ page import="org.apache.poi.xssf.usermodel.XSSFSheet" %>
<%@ page import="org.apache.poi.xssf.usermodel.XSSFWorkbook" %>
<%@page import=" ext.ket.bom.util.*"%>
<%@page import=" org.json.*"%>
<%@ page import="wt.part.*"%>
<%@ page import="ext.ket.part.entity.dto.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%

//PrintWriter pw;
KETBomPartUtil bean = new KETBomPartUtil();

//pw = response.getWriter();

Hashtable params = new Hashtable();
Hashtable result = new Hashtable();
String success = "true";

String partNumber = "";
String boxQty     = "";
String ecoNumber      = (String)request.getParameter("ecoNumber");
String gubun          = (String)request.getParameter("gubun"); 
String partType       = (String)request.getParameter("partType"); 

String[] partNo       = (String[])request.getParameterValues("partNo");
String[] index        = (String[])request.getParameterValues("index");
String[] lvl          = (String[])request.getParameterValues("lvl");
String[] ict          = (String[])request.getParameterValues("ict");
String[] seq          = (String[])request.getParameterValues("seq");
String[] partName     = (String[])request.getParameterValues("partName");
String[] qty          = (String[])request.getParameterValues("qty");
String[] unit         = (String[])request.getParameterValues("unit");
String[] rev          = (String[])request.getParameterValues("rev");
String[] state        = (String[])request.getParameterValues("state");
String[] econo        = (String[])request.getParameterValues("econo");
String[] checkout     = (String[])request.getParameterValues("checkout");
String[] reftop       = (String[])request.getParameterValues("reftop");
String[] refbtm       = (String[])request.getParameterValues("refbtm");
String[] material     = (String[])request.getParameterValues("material");
String[] hardnessFrom = (String[])request.getParameterValues("hardnessFrom");
String[] hardnessTo   = (String[])request.getParameterValues("hardnessTo");
String[] designDate   = (String[])request.getParameterValues("designDate");
String[] parentNo     = (String[])request.getParameterValues("parentNo");
String[] pver         = (String[])request.getParameterValues("pver");

params.put("ecoNumber", ecoNumber);
params.put("gubun", gubun);
params.put("partType", partType);

params.put("partNo", partNo);
params.put("index", index);
params.put("lvl", lvl);
params.put("ict", ict);
params.put("seq", seq);
params.put("partName", partName);
params.put("qty", qty);
params.put("unit", unit);
params.put("rev", rev);
params.put("state", state);
params.put("econo", econo);
params.put("checkout", checkout);
params.put("reftop", reftop);
params.put("refbtm", refbtm);
params.put("material", material);
params.put("hardnessFrom", hardnessFrom);
params.put("hardnessTo", hardnessTo);
params.put("designDate", designDate);
params.put("parentNo", parentNo);
params.put("pver", pver);

result = bean.getBomValidationResult(params);

//pw.print(result);
//pw.flush();
//pw.close();
%>

{
    "success": <%=success %>,
    "result": {
        "checkdata1": "<%=result.get("checkdata1")%>",
        "checkdata2": "<%=result.get("checkdata2")%>",
        "checkdata3": "<%=result.get("checkdata3")%>",
        "checkdata4": "<%=result.get("checkdata4")%>",
        "checkdata5": "<%=result.get("checkdata5")%>",
        "checkdata6": "<%=result.get("checkdata6")%>",
        "checkdata7": "<%=result.get("checkdata7")%>",
        "checkdata8": "<%=result.get("checkdata8")%>",
        "checkdata9": "<%=result.get("checkdata9")%>",
        "errlog": "<%=result.get("errLog")%>"
    }
}