
<%@page import="wt.session.SessionHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%
	String oid = "";
	long id = 0;
	if(request.getParameter("oid") != null && request.getParameter("oid").length() > 0){
		oid = request.getParameter("oid");
		try{
			id = CommonUtil.getOIDLongValue(oid);
		}catch(Exception e){
		    Kogger.error(e);
		}
	}
	TrySchdule ts = new TrySchdule();

	if(oid.length() == 0){
	%>
		<script type="text/javascript">
		alert("<%=messageService.getString("e3ps.message.ket_message", "00533") %><%--try 정보 없음--%>");
		self.close();
		</script>
	<%
	}
	String planDate = request.getParameter("planDate");

	String reason = "";
	if(request.getParameter("reason") != null && request.getParameter("reason").length() > 0){
		TryChange tc = TryChange.newTryChange();
		tc.setTrySchdule(ts);
		tc.setChangeAttr1(""+id);
		tc.setOwner(SessionHelper.manager.getPrincipalReference());
		tc.setChangeDetil(request.getParameter("reason"));
		tc.setChangeDate(DateUtil.getCurrentTimestamp());
		tc = (TryChange)PersistenceHelper.manager.save(tc);
	}

%>

<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.project.trySchdule.TryChange"%>
<%@page import="e3ps.project.trySchdule.TrySchdule"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<html>
<head>

<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 10px;
	margin-top: 10px;
	margin-right: 10px;
	margin-bottom: 5px;
}
-->
</style>

</head>
<body>
<form>
<input type="hidden" name="reason" value="<%=reason %>"></input>

<script type="text/javascript">

//alert("저장되었습니다.");
opener.document.location.href = "/plm/jsp/project/trySchdule/TrySave.jsp?oid=<%=oid%>&planDate=<%=planDate%>&mode=simple";

self.close();
</script>

</form>
</body>
</html>
