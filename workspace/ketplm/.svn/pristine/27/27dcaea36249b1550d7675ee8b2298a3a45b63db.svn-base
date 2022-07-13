<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.trySchdule.TrySchdule"%>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleData"%>
<%@page import="java.util.HashMap"%>
<%@page import="e3ps.project.moldPart.beans.MoldPartHelper"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<html>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<head>

<%
	String msg = "";
	String mode = "";
	String managerOid = request.getParameter("managerOid");
	String projectOid = request.getParameter("projectOid");

	String popup = request.getParameter("popup");
	String tmpPopUp = "";
	if("popup".equals(popup)) {
		tmpPopUp = "&popup=popup";
	}

	if(request.getParameter("mode") != null){
		mode = request.getParameter("mode");
	}
	if(mode.equals("del")){
		HashMap map = new HashMap();
		map.put("managerOid", managerOid);
		MoldPartHelper.delete(map);
	}else{
		//String managerOid = request.getParameter("managerOid");
		String subPartOid[] = request.getParameterValues("subPartOid");

	    String dieNumber = request.getParameter("dieNumber");
		//String projectOid = request.getParameter("projectOid");
	    String ecoNumber = request.getParameter("ecoNumber");
	    //String levType = request.getParameter("levType");
	    String partProcess = request.getParameter("temppartProcess");
	    //String createType = request.getParameter("createType");
	    String exhibitDate = request.getParameter("exhibitDate");
	    String completeDate = request.getParameter("completeDate");
	    String usageOid[] = request.getParameterValues("linkOid");
	    String partClass[] = request.getParameterValues("partClass");
	    String partType[] = request.getParameterValues("partType");
	    String endDate[] = request.getParameterValues("endDate");
	    String mType[] = request.getParameterValues("mType");
	    String actionType[] = request.getParameterValues("actionType");
	    String transferDate[] = request.getParameterValues("transferDate");
	    String etc[] = request.getParameterValues("etc");

	    /*
	    Kogger.debug("usageOid.length = " + usageOid.length);
	    Kogger.debug("partClass.length = " + partClass.length);

	    Kogger.debug("dieNumber" + " = " + dieNumber);
	    Kogger.debug("projectOid" + " = " + projectOid);

	    Kogger.debug("ecoNumber" + " = " + ecoNumber);
	    Kogger.debug("levType" + " = " + levType);
	    Kogger.debug("partProcess" + " = " + partProcess);
	    Kogger.debug("createType" + " = " + createType);
	    Kogger.debug("exhibitDate" + " = " + exhibitDate);
	    Kogger.debug("completeDate" + " = " + completeDate);
	    Kogger.debug("usageOid" + " = " + usageOid);
	    Kogger.debug("partClass" + " = " + partClass);
	    Kogger.debug("partType" + " = " + partType);
	    Kogger.debug("endDate" + " = " + endDate);
	    Kogger.debug("mType" + " = " + mType);
	    Kogger.debug("actionType" + " = " + actionType);
	    Kogger.debug("transferDate" + " = " + transferDate);
	    Kogger.debug("etc" + " = " + etc);
	    */
	    HashMap map = new HashMap();

	    map.put("dieNumber", dieNumber);
	    map.put("projectOid", projectOid);
	    map.put("ecoNumber", ecoNumber);
	    //map.put("levType", levType);
	    map.put("partProcess", partProcess);
	    //map.put("createType", createType);
	    map.put("exhibitDate", exhibitDate);
	    map.put("completeDate", completeDate);
	    map.put("usageOid", usageOid);
	    map.put("partClass", partClass);
	    map.put("partType", partType);
	    map.put("endDate", endDate);
	    map.put("mType", mType);
	    map.put("actionType", actionType);
	    map.put("transferDate", transferDate);
	    map.put("etc", etc);

	    map.put("managerOid", managerOid);
	    map.put("subPartOid", subPartOid);

		if(mode.equals("modi")){
			//Kogger.debug("modi");
			MoldPartHelper.update(map);
		}else{
			//Kogger.debug("create");
	    	MoldPartHelper.create(map);
		}
	}
%>


<title>Insert title here</title>

<script>
function goViewPage(pOid, mOid){
	location.href="/plm/jsp/project/moldPart/MoldPartView.jsp?projectOid=" + pOid + "&managerOid=" + mOid + "<%=tmpPopUp %>";
}
</script>
</head>
<body>
<form>
<script>
<%if(mode.equals("del")){%>
	alert("<%=messageService.getString("e3ps.message.ket_message", "01699") %><%--삭제되었습니다.--%>");
	goViewPage('<%=projectOid %>', '')
<%}else if(mode.equals("modi")){%>
	alert("<%=messageService.getString("e3ps.message.ket_message", "01947") %><%--수정되었습니다.--%>");
	goViewPage('<%=projectOid %>', '<%=managerOid %>');
<%}else{%>
    alert('<%=messageService.getString("e3ps.message.ket_message", "01324") %><%--등록되었습니다.--%>');
	
    document.forms[0].action = "/plm/servlet/e3ps/MoldPartSearchServlet?command=search";
	document.forms[0].submit();
	window.close();
<%}%>

</script>
</form>
</body>
</html>
