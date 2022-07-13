<%@page contentType="text/html; charset=UTF-8"%>
<%@page import ="e3ps.common.util.CommonUtil,java.util.ArrayList"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="wt.query.OrderBy"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.beans.OEMTypeHelper"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="e3ps.project.outputtype.OEMType"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.project.outputtype.ModelPlan"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%
	String numCodeOid = request.getParameter("numCodeOid");
	String carTypeCode = null;
	String carTypeName = null;
	String oemtype = "CARTYPE";
	
	long numLong = CommonUtil.getOIDLongValue(numCodeOid);
	
	QuerySpec qs4 = new QuerySpec();
	int idx4 = qs4.appendClassList(OEMProjectType.class, true);
	
	if(numLong==0) numLong=1;
	
	SearchCondition sc4 = new SearchCondition(OEMProjectType.class, "makerReference.key.id", "=", numLong );
	qs4.appendWhere(sc4, new int[] {idx4});
	qs4.appendAnd();
	SearchCondition sc5 = new SearchCondition(OEMProjectType.class, "oemType", "=", oemtype);
	qs4.appendWhere(sc5, new int[] {idx4});
	ClassAttribute ca = new ClassAttribute(OEMProjectType.class,"code");
	qs4.appendOrderBy(new OrderBy(ca, false), new int[] { idx4 }); 
	Kogger.debug("CarTypeSelect", null, null, "qs42 ===>" + qs4);
	QueryResult qr4 = PersistenceHelper.manager.find( qs4 );
	
%>
	var pos = 1;
<%
    if(qr4.hasMoreElements()){
%>
		document.form01.carType.disabled = false;
<%
		while(qr4.hasMoreElements()){
			Object[] o2 = (Object[])qr4.nextElement();
			OEMProjectType opt = (OEMProjectType)o2[0];
			carTypeCode = opt.getPersistInfo().getObjectIdentifier().getStringValue();
			//Kogger.debug("carTypeCode ===>" + carTypeCode);
			
			carTypeName = opt.getName();
			//Kogger.debug("carTypeName ===>" + carTypeName);
%>		
			document.form01.carType.length = pos+1;
			document.form01.carType.options[pos].value= "<%=carTypeCode%>";
			document.form01.carType.options[pos++].text = "<%=carTypeName%>";
<%
		}
	}else{	
%>
		document.form01.carType.disabled = false;
<%
	}
%>
