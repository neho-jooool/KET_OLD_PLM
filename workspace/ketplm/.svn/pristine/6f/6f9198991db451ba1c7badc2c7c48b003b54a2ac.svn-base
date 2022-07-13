<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="e3ps.cost.control.CostResultAcc"%>
<%@ page import="e3ps.cost.control.CostResultAccCase"%>
<%@ page import="e3ps.cost.control.CostApprCtl"%>
<%
	String send_ok 		= StringUtil.checkNull(request.getParameter("send_ok"));
	String pk_cr_group 	= StringUtil.checkNull(request.getParameter("pk_cr_group"));
	String rev_no      	= StringUtil.checkNull(request.getParameter("rev_no"));
	String team      	= StringUtil.checkNull(request.getParameter("team"));

	String p_case_data_value = "";
	String p_group_case_count = "";
	String p_table_row = "";
	if(send_ok.equals("ok")){//결재 요청
		CostResultAcc rac = new CostResultAcc();
		String[] acc_case_value = rac.resultAcc(pk_cr_group, rev_no, "");
		p_case_data_value 	= acc_case_value[0];
		p_group_case_count 	= acc_case_value[1];
		p_table_row			= acc_case_value[2];

		if(p_case_data_value.equals("ok")){
			CostResultAccCase rac2 = new CostResultAccCase();
			rac2.resultAccCase(pk_cr_group, rev_no,p_group_case_count,p_table_row);
		}
	}
%>
<script langeuage = "javascript">
this.close();
</script>