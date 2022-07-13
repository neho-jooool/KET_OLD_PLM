<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="e3ps.cost.control.CostResultAcc"%>
<%@ page import="e3ps.cost.control.CostResultAccCase"%>
<%@ page import="e3ps.cost.control.CostApprCtl"%>
<%
	String send_ok 			= StringUtil.checkNull(request.getParameter("send_ok"));
	String admin_pass 	= StringUtil.checkNull(request.getParameter("admin_pass"));//작성내용덮기 ok면 결재로직 태우지않음
	String pk_cr_group 	= StringUtil.checkNull(request.getParameter("pk_cr_group"));
	String rev_no      		= StringUtil.checkNull(request.getParameter("rev_no"));
	String team      			= StringUtil.checkNull(request.getParameter("team"));
	String L_email      		= StringUtil.checkNull(request.getParameter("L_email"));
	String Le_name      			= StringUtil.checkNull(request.getParameter("Le_name"));
	String group_case_count 	= StringUtil.checkNull(request.getParameter("group_case_count"));
	String consent_txt      		= StringUtil.checkNull(request.getParameter("consent_txt"));

	String p_case_data_value = "";
	String p_group_case_count = "";
	String p_table_row = "";
	if(send_ok.equals("ok") || admin_pass.equals("ok")){//결재 요청
		CostResultAcc rac = new CostResultAcc();
		String[] acc_case_value = rac.resultAcc(pk_cr_group, rev_no, "");
		p_case_data_value 	= acc_case_value[0];
		p_group_case_count 	= acc_case_value[1];
		p_table_row			= acc_case_value[2];

		if(p_case_data_value.equals("ok")){
			CostResultAccCase rac2 = new CostResultAccCase();
			rac2.resultAccCase(pk_cr_group, rev_no,p_group_case_count,p_table_row);
		}
		//작성내용덮기 ok

		if(!admin_pass.equals("ok")){
			CostApprCtl appr = new CostApprCtl();
			appr.Appr_ver_3(pk_cr_group, rev_no, team, "1", group_case_count, Le_name);
		}
	}
	if(send_ok.equals("no")){//반려 요청
		CostApprCtl appr = new CostApprCtl();
		appr.Appr_reject(pk_cr_group, rev_no, team, L_email, Le_name, group_case_count, consent_txt);
	}
%>
<script langeuage = "javascript">
<%if(admin_pass.equals("ok")){ %>
	alert("완료되었습니다.");
<%}%>
this.close();
</script>