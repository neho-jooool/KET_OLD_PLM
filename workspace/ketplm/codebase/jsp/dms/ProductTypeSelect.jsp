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
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%
	String code = request.getParameter("code");
	NumberCode nCode = (NumberCode)CommonUtil.getObject(code);
%>
	var tBody = document.getElementById("iProductTypeTable");
	var tLength = tBody.rows.length;
	
	for(var i=0; i<tLength; i++){
		tBody.deleteRow(0);
	}
	var innerRow;
	var innerCell;
<%
	ArrayList carProductTypeCodeList = NumberCodeHelper.getChildNumberCode(nCode);
	for(int i = 0 ; i < carProductTypeCodeList.size(); i++){
    	String numCodeOid = ((NumberCode)carProductTypeCodeList.get(i)).getPersistInfo().getObjectIdentifier().getStringValue();
		String numCodeName = ((NumberCode)carProductTypeCodeList.get(i)).getName();   
		if(i%5==0) {    %>              
            innerRow = tBody.insertRow();
<%      }           
%>
	innerCell = innerRow.insertCell();
	innerCell.width = "130";
	innerCell.innerHTML = "<input type='checkbox' name='ProductTypeCode' id='<%=numCodeOid%>'><%=numCodeName%>";
        
<%    }
%>  
	
