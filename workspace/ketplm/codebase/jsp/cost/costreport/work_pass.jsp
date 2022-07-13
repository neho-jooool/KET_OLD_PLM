<%@ page contentType = "text/html; charset=euc-kr" %>
<%@ page import="e3ps.cost.control.CostApprCtl"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%
	String Ename 				= StringUtil.checkNull((String)session.getAttribute("Ename"));
	String Dname	    			= StringUtil.checkNull((String)session.getAttribute("Dname"));
	String emp_no				= StringUtil.checkNull((String)session.getAttribute("emp_no"));
	String dept_no				= StringUtil.checkNull((String)session.getAttribute("dept_no"));
	String K_pass				= StringUtil.checkNull((String)session.getAttribute("K_pass"));
	String position				= StringUtil.checkNull((String)session.getAttribute("position"));
	String login_id				= StringUtil.checkNull((String)session.getAttribute("cost_id"));
	String pk_cr_group 		= StringUtil.checkNull((request.getParameter("pk_cr_group")));
	String cost_report_add 	= "ok";
	String user_case_count 	= StringUtil.checkNull(request.getParameter(("user_case_count")));
	String report_pk				= StringUtil.checkNull(request.getParameter(("report_pk")));
	String step_no				= StringUtil.checkNull(request.getParameter(("step_no")));
	String table_row				= StringUtil.checkNull(request.getParameter(("table_row")));
    String rev_no					= StringUtil.checkNull(request.getParameter(("rev_no")));

    boolean mail_ok = false;
	CostApprCtl appr = new CostApprCtl();

	if(step_no.equals("2")){
		mail_ok = appr.Appr_report_ver_1(report_pk, pk_cr_group, rev_no, Ename, dept_no, table_row, user_case_count);
	}
	String send_ok = "";
	if(mail_ok){
		send_ok = "ok";
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<title></title>
<script language= "javascript">
function CALL()
{
	var send_ok = '<%=send_ok%>';
	if(send_ok == "ok"){
		alert("결재를 요청하였습니다.");
		opener.parent.location.href = "/plm/jsp/cost/costreport/cost_report_1.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&report_pk="+'<%=report_pk%>'+"&cost_report_add="+'<%=cost_report_add%>'+"&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>' ;
	}else{
		alert("결재 요청이 실패하였습니다.");
	}
	this.close();
}
</script>
</script>
</head>
<body onload="CALL();">
</body>
</html>