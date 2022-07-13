<%@page import="ext.ket.bom.service.KETBom2Helper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="e3ps.common.content.uploader.*"%>
<%@ page import="java.io.*" %>
<%@ page import="ext.ket.part.entity.*" %>
<%@ page import="ext.ket.part.classify.service.*" %>
<%@ page import="ext.ket.part.base.service.*" %>
<%@ page import="e3ps.common.util.CommonUtil" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFSheet" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFWorkbook" %>
<%@ page import="org.apache.poi.ss.usermodel.Cell" %>
<%@ page import="org.apache.poi.ss.usermodel.Row" %>
<%@ page import="org.apache.poi.xssf.usermodel.XSSFSheet" %>
<%@ page import="org.apache.poi.xssf.usermodel.XSSFWorkbook" %>
<%@page import=" ext.ket.bom.query.*"%>
<%@page import=" ext.ket.bom.util.*"%>
<%@page import=" ext.ket.bom.sevice.*"%>
<%@page import=" org.json.*"%>
<%@ page import="wt.part.*"%>
<%@ page import="ext.ket.part.entity.dto.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%

KETBOMQueryBean butil = new KETBOMQueryBean();
KETBomPartUtil pUtil = new KETBomPartUtil();

FileUploader uploader = null;
uploader = FileUploader.newFileUploader(null, request, response);
Vector files;
String success = "true";
String jsondata = "";
String root = "";
String errlog = "";
Hashtable rootHash = new Hashtable();

String gubun = request.getParameter("gubun");
String partNumber = request.getParameter("partNumber");
String partRev = request.getParameter("partRev");

String econoA = request.getParameter("econo");
String checkoutA = request.getParameter("checkout");
String checkoutIdA = request.getParameter("checkoutId");
String stateA = request.getParameter("state");


List resultList = new ArrayList<Map<String, Object>>();

System.out.println("gubun======>"+gubun);
//System.out.println("partNumber======>"+partNumber);

System.out.println("partRev======>"+partRev);
System.out.println("econoA======>"+econoA);
System.out.println("checkoutA======>"+checkoutA);
System.out.println("checkoutIdA======>"+checkoutIdA);
System.out.println("stateA======>"+stateA);

Hashtable param = new Hashtable();
param.put("gubun",gubun);
param.put("partNumber",partNumber);
param.put("partRev",partRev);
param.put("econo",econoA);
param.put("checkout",checkoutA);
param.put("checkoutId",checkoutIdA);
param.put("state",stateA);

Map<String, Object> result = new HashMap<String, Object>();
try{
    result = KETBom2Helper.service.excelUploadPart(uploader, param);
    errlog = (String)result.get("errlog");
}catch(Exception e)
{
    errlog = e.getLocalizedMessage();
    result.put("jsondata", "[]");
    result.put("errlog", errlog);
    
    System.out.println("errlog(Exception)===========>"+errlog);
}

System.out.println("errlog===========>"+errlog);

%>
{
    "success": <%=(String)result.get("success") %>,
    "result": {
        "data": <%=(String)result.get("jsondata")%>,
        "errlog": "<%=(String)result.get("errlog")%>"
    }
}