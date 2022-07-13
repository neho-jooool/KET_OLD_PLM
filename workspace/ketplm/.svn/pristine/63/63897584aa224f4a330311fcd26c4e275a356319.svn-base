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
	String domCd = request.getParameter("domCd");
	String carCompanyCode = null;
	String carCompanyName = null;
	
	QuerySpec qs = new QuerySpec();
	int idx = qs.appendClassList(NumberCode.class, true);
	SearchCondition sc1 = new SearchCondition(NumberCode.class, "code", "=", domCd);
	qs.appendWhere(sc1, new int[] {idx});
	qs.appendAnd();
	SearchCondition sc2 = new SearchCondition(NumberCode.class, "codeType", "=", "CUSTOMEREVENT");
	qs.appendWhere(sc2, new int[] {idx});
	qs.appendAnd();
	SearchCondition sc3 = new SearchCondition(NumberCode.class, "parentReference.key.classname", true);
	qs.appendWhere(sc3, new int[] {idx});
	ClassAttribute ca2 = new ClassAttribute(NumberCode.class,"code");
	qs.appendOrderBy(new OrderBy(ca2, false), new int[] { idx }); 
	
	Kogger.debug("CarCompanySelect", null, null, "qs ===>" + qs.toString());
	QueryResult qr = PersistenceHelper.manager.find( qs );

	
%>
	var pos = 1;
<%
    if(qr.hasMoreElements()){
%>
		document.form01.carCompany.disabled = false;
<%
		ArrayList carCompanyList = null;
		
		while(qr.hasMoreElements()){
			Object[] o = (Object[])qr.nextElement();
			NumberCode nCode = (NumberCode) o[0];
		    carCompanyList = NumberCodeHelper.getChildNumberCode(nCode);
		    
		    for(int i = 0 ; i < carCompanyList.size(); i++){
		    	carCompanyCode = ((NumberCode)carCompanyList.get(i)).getPersistInfo().getObjectIdentifier().getStringValue();
				carCompanyName = ((NumberCode)carCompanyList.get(i)).getName();

%>		
			document.form01.carCompany.length = pos+1;
			document.form01.carCompany.options[pos].value= "<%=carCompanyCode%>";
			document.form01.carCompany.options[pos++].text = "<%=carCompanyName%>";
<%
		}
	  }
	}else{	
%>
		document.form01.carCompany.disabled = false;
<%
	}
%>
