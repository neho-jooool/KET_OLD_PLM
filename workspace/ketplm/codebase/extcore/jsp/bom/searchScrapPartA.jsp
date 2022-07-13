<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="wt.part.QuantityUnit"%>
<%@ page import="wt.part.WTPart"%>
<%@ page import="java.util.*"%>
<%@ page import=" org.json.*"%>
<%@page import=" java.io.*"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="ext.ket.part.util.*"%>
<%@ page import="ext.ket.bom.util.KETBomPartUtil"%>
<%@ page import="ext.ket.bom.query.*"%>
<%@ page import="wt.session.SessionHelper"%>
<%@ page import="wt.org.WTUser"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
     
String partNo  = (String)request.getParameter("partNo");
System.out.println("Action###################################partNo=========>"+partNo);
if(partNo==null || partNo.equals(""))
    partNo = "S1";

System.out.println("partNumber=========>"+partNo);
KETBomPartUtil util = new KETBomPartUtil();
     Vector partList = util.searchItem(partNo+"%");
     List<Map<String, Object>> searchList = new ArrayList<Map<String, Object>>();

for(int i=0; i < partList.size(); i++)
{
    WTPart part = (WTPart)partList.get(i);
    String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType );
    //WTPartMaster partMaster = part.getMaster();
    
    String partNumber = part.getNumber();
    String partName = part.getName();
    String partRev = wt.vc.VersionControlHelper.getVersionIdentifier(part).getValue();
    String newRev = StringUtils.stripToEmpty(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartRevision));
    String partUnit =  part.getDefaultUnit().toString();
    partUnit = "KET_G";
    String state = part.getLifeCycleState().getDisplay();
    
    
    Hashtable data = new Hashtable();
    //data.put("checkbox", "<input type='radio' id='rowSel' name='rowSel' value='"+Integer.toString(i+1)+"'>");
    if(i==0)
	    data.put("checkdata", "<input type='radio' name='rowSel' id='rowSel"+Integer.toString(i)+"' value='"+partNumber+"' checked onClick='checkValue("+Integer.toString(i)+");'>");
    else
	   data.put("checkdata", "<input type='radio' name='rowSel' id='rowSel"+Integer.toString(i)+"' value='"+partNumber+"' onClick='checkValue("+Integer.toString(i)+");'>");
    data.put("no", Integer.toString(i+1));
    data.put("partNo", partNumber);
    data.put("newrev", newRev);
    data.put("rev", partRev);
    data.put("partName", partName);
    data.put("unit", partUnit);
    data.put("state", state);
    
    searchList.add(data);
   
}

JSONArray jsonArray = new JSONArray(searchList);
String  jsondata =jsonArray.toJSONString();

PrintWriter pw = response.getWriter();
//System.out.println(jsonArray.toJSONString());
pw.print(jsondata);
pw.flush();
pw.close();


%>