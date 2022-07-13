<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import=" ext.ket.bom.query.*"%>
<%@page import=" ext.ket.part.base.service.internal.associate.PartEpmRelation"%>
<%@page import=" java.util.*"%>
<%@page import=" java.io.*"%>
<%@page import=" org.json.*"%>
<%@ page import="wt.part.WTPart"%>
<%@ page import="ext.ket.part.util.*"%>
<%@ page import="ext.ket.bom.util.KETBomPartUtil"%>
<%@ page import="ext.ket.bom.query.KETBOMQueryBean"%>
<%@ page import="ext.ket.bom.service.*"%>
<%@ page import="ext.ket.part.classify.service.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@include file="/jsp/common/InitMsgSvc.jsp"%>
<%
String cnt = request.getParameter("cnt");
String ecoNumber      = (String)request.getParameter("ecoNumber");
String topPartNo      = (String)request.getParameter("topPartNo");
String topRev      = (String)request.getParameter("topRev");

System.out.println("ecoNumber===>"+ecoNumber);
System.out.println("topPartNo===>"+topPartNo);
System.out.println("topRev===>"+topRev);

KETBOMQueryBean qbean = new KETBOMQueryBean();
KETBomPartUtil util = new KETBomPartUtil();

String topPartOid = qbean.getPartOid(topPartNo, topRev);
WTPart topPart = util.getPart(topPartOid);

System.out.println("cnt===>"+cnt);

String[] partNo         = null;
String[] partOid        = null;
String[] rpartOid        = null;
String[] oldPWeight     = null;
String[] oldSWeight     = null;
String[] oldTWeight     = null;
String[] oldMaterial    = null;
String[] newPWeight     = null;
String[] newSWeight     = null;
String[] newTWeight     = null;
String[] newMaterial    = null;

Hashtable params = new Hashtable();
ArrayList arr = new ArrayList();


if(cnt.equals("1"))
{
    partNo         = new String[1];
    partOid        = new String[1];
    rpartOid       = new String[1];
    oldPWeight     = new String[1];
    oldSWeight     = new String[1];
    oldTWeight     = new String[1];
    oldMaterial    = new String[1];
    newPWeight     = new String[1];
    newSWeight     = new String[1];
    newTWeight     = new String[1];
    newMaterial    = new String[1];
    
    partNo[0]         = (String)request.getParameter("partNo");
    partOid[0]        = (String)request.getParameter("partOid");
    rpartOid[0]        = (String)request.getParameter("rPartOid");
    oldPWeight[0]     = (String)request.getParameter("oldPWeight");
    oldSWeight[0]     = (String)request.getParameter("oldSWeight");
    oldTWeight[0]     = (String)request.getParameter("oldTWeight");
    oldMaterial[0]    = (String)request.getParameter("oldMaterial");
    newPWeight[0]     = (String)request.getParameter("newPWeight");
    newSWeight[0]     = (String)request.getParameter("newSWeight");
    newTWeight[0]     = (String)request.getParameter("newTWeight");
    newMaterial[0]    = (String)request.getParameter("newMaterial");
}else
{

	partNo         = (String[])request.getParameterValues("partNo");
	partOid        = (String[])request.getParameterValues("partOid");
	rpartOid        = (String[])request.getParameterValues("rPartOid");
	oldPWeight     = (String[])request.getParameterValues("oldPWeight");
	oldSWeight     = (String[])request.getParameterValues("oldSWeight");
	oldTWeight     = (String[])request.getParameterValues("oldTWeight");
	oldMaterial    = (String[])request.getParameterValues("oldMaterial");
	newPWeight     = (String[])request.getParameterValues("newPWeight");
	newSWeight     = (String[])request.getParameterValues("newSWeight");
	newTWeight     = (String[])request.getParameterValues("newTWeight");
	newMaterial    = (String[])request.getParameterValues("newMaterial");
}	
	
params.put("topPartNo", topPartNo);
params.put("topRev", topRev);
params.put("partNo", partNo);
params.put("partOid", partOid);
params.put("rpartOid", rpartOid);
params.put("newPWeight", newPWeight);
params.put("newSWeight", newSWeight);
params.put("newTWeight", newTWeight);
params.put("newMaterial", newMaterial);


arr.add(params);
	
/*
if(partNo!=null && partNo.length>0)
{
    for(int i=0; i < partNo.length; i++)
    {
	   System.out.println("------------------------------------------------------------------");   
	   System.out.println("partNo["+i+"]==>"+partNo[i]);
	   System.out.println("partOid["+i+"]==>"+partOid[i]);
	   System.out.println("oldPWeight["+i+"]==>"+oldPWeight[i]);
	   System.out.println("oldSWeight["+i+"]==>"+oldSWeight[i]);
	   System.out.println("oldTWeight["+i+"]==>"+oldTWeight[i]);
	   System.out.println("oldMaterial["+i+"]==>"+oldMaterial[i]);
	   System.out.println("newPWeight["+i+"]==>"+newPWeight[i]);
	   System.out.println("newSWeight["+i+"]==>"+newSWeight[i]);
	   System.out.println("newTWeight["+i+"]==>"+newTWeight[i]);
	   System.out.println("newMaterial["+i+"]==>"+newMaterial[i]);
	   
	   
	   if(topPartNo.equals(partNo[i].substring(1)))
	   {
	       //topPart
	       PartSpecSetter.setPartSpec(topPart, PartSpecEnum.SpTotalWeight, newTWeight[i]);
	       PartSpecSetter.setPartSpec(topPart, PartSpecEnum.SpScrabWeight, newSWeight[i]);
	       PartSpecSetter.setPartSpec(topPart, PartSpecEnum.SpNetWeight, newPWeight[i]);
	       List list = PartClazHelper.service.getMaterialPartSpecEnum(topPart);
	       if(list!=null && list.size()>0)
	       {
		       PartSpecEnum specenum = (PartSpecEnum) list.get(0);
		       PartSpecSetter.setPartSpec(topPart, specenum, newMaterial[i]);
	       }
	       
	       arr.add(topPart);
	   }
	   
	   WTPart part  = util.getPart(partOid[i]);
	   PartSpecSetter.setPartSpec(part, PartSpecEnum.SpTotalWeight, newTWeight[i]);
       PartSpecSetter.setPartSpec(part, PartSpecEnum.SpScrabWeight, newSWeight[i]);
       PartSpecSetter.setPartSpec(part, PartSpecEnum.SpNetWeight, newPWeight[i]);
       
       List list2 = PartClazHelper.service.getMaterialPartSpecEnum(part);
       
       if(list2!=null && list2.size()>0)
       {
	       PartSpecEnum specenum2 = (PartSpecEnum) list2.get(0);
           PartSpecSetter.setPartSpec(part, specenum2, newMaterial[i]);
       }
       
       System.out.println("getPartSpec==>"+PartSpecGetter.getPartSpec(part, PartSpecEnum.SpTotalWeight));
       System.out.println("------------------------------------------------------------------");  
       
       arr.add(part);
    }
}
*/

String resultMsg = messageService.getString("e3ps.message.ket_message", "09418");//저장되었습니다.
String result = KETBom2Helper.service.savePart(arr);

if(!result.equals(""))
    resultMsg = result;
//pw.print(result);
//pw.flush();
//pw.close();
%>

<script> 
parent.resultMsg("<%=resultMsg%>"); 
//parent.opener.weightCheck="Y";
</script>