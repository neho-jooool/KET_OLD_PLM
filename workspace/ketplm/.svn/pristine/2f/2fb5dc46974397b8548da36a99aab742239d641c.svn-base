<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.HashMap,
                e3ps.project.customerPlan.beans.CustomerPlanHelper"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
	String poid = request.getParameter("poid");
	String coid = request.getParameter("coid");
	String oid =  request.getParameter("oid")!=null?request.getParameter("oid"):"";
	String command = request.getParameter("command");
	String plan_gubun = request.getParameter("planGubun");
	String plan_date = request.getParameter("planDate");
	String msg = "";

	HashMap insertMap = new HashMap();
	insertMap.put("projectOid", poid);
	insertMap.put("customerOid", coid);
	insertMap.put("oid", oid);
	insertMap.put("plan_gubun", plan_gubun);
	insertMap.put("plan_date", plan_date);

	if("add".equals(command)){
		CustomerPlanHelper.createCustomerPlan(insertMap);
		msg = messageService.getString("e3ps.message.ket_message", "02454")/*저장 되었습니다*/;
	}else{
		CustomerPlanHelper.deleteCustomerPlan(insertMap);
		msg = messageService.getString("e3ps.message.ket_message", "01692")/*삭제 되었습니다*/;
	}
%>
<body onLoad="javascript:gotoMenu()">
<form>
<input type="hidden" name="poid" value="<%=poid%>">
<input type="hidden" name="coid" value="<%=coid%>">
</form>
</body>
<script language="JavaScript">
function gotoMenu(){
   	alert("<%=msg%>");
   	document.forms[0].method = "post";
    document.forms[0].action = "/plm/jsp/project/schedule/AddCustomerSchedule.jsp";
    document.forms[0].submit();
}
</script>
