<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="e3ps.cost.control.CostApprCtl"%>
<%
	String pk_cr_group				= StringUtil.checkNull(request.getParameter("pk_cr_group"));
	String rev_no						= StringUtil.checkNull(request.getParameter("rev_no"));
	String group_case_count	= StringUtil.checkNull(request.getParameter("group_case_count"));
	String step_no					= StringUtil.checkNull(request.getParameter("step_no"));
	String w_name					= StringUtil.checkNull(request.getParameter("w_name"));
	String send_ok					= StringUtil.checkNull(request.getParameter("send_ok"));

	String Ename 	= StringUtil.checkNull((String)session.getAttribute("Ename"));
	String Dname		= StringUtil.checkNull((String)session.getAttribute("Dname"));
	String emp_no	= StringUtil.checkNull((String)session.getAttribute("emp_no"));
	String dept_no	= StringUtil.checkNull((String)session.getAttribute("dept_no"));
	String K_pass	= StringUtil.checkNull((String)session.getAttribute("K_pass"));
	String position	= StringUtil.checkNull((String)session.getAttribute("position"));
	String login_id	= StringUtil.checkNull((String)session.getAttribute("id"));

	if(send_ok.equals("ok")){
		CostApprCtl appr = new CostApprCtl();
		appr.change_w_name(pk_cr_group, rev_no, w_name, group_case_count,dept_no);
	}

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" >
<title>무제 문서</title>
</head>
<script language = "javascript">
function sendForm(){
	var pk_cr_group 	 		= '<%=pk_cr_group%>';
	var step_no 	     			= '<%=step_no%>';
	var rev_no 					= '<%=rev_no%>';
	var group_case_count	= '<%=group_case_count%>';

	var select = document.getElementById("w_name");
	var option_value = select.options[select.selectedIndex].value;

	var send_ok = "ok";
	var w_name = escape(encodeURIComponent(option_value));
	var form = document.forms[0];
	form.action = "/plm/jsp/cost/costdb/charge_sel_work.jsp?pk_cr_group="+pk_cr_group+"&step_no="+step_no+"&rev_no="+rev_no+"&group_case_count="+group_case_count+"&send_ok="+send_ok+"&w_name="+w_name;
	form.submit();

	alert("담당자에게 메일을 전송하였습니다");
	var url = "/plm/jsp/cost/costdb/cost_re_view_test.jsp?pk_cr_group="+pk_cr_group+"&rev_no="+rev_no+"&data_type=main&group_case_count="+group_case_count+"&step_no=2";
	opener.parent.location.href = url;
	this.close();
}
</script>
<style type="text/css">
<!--
.style1 {font-size: 12px; background-color:#D2D2D2; text-align:left; font-weight: bold; color:#333333}
.style2 {font-size: 12px;text-align:left}
.style3 {font-size: 12px;text-align:center}

-->
</style>
<body>
<form method="post" name= "part_1" target="_self">
<table width="238" height="67" border="0" align="center" cellpadding="0" cellspacing="1">
  <tr>
    <td height="18" colspan="3" class="style1">&nbsp;■ 담당자 선정 </td>
  </tr>
  <tr>
    <td height="1" colspan="3" bgcolor="#FFCC00"></td>
    </tr>
  <tr>
    <td width="83" class="style3">설계원가팀</td>
    <td width="83" height="21"><select name="w_name" >
          <option value="김호식" > 김호식</option>
          <option value="김정철" > 김정철</option>
    	  <option value="박찬호" > 박찬호</option>
    	  <option value="신지훈" > 신지훈</option>
          <option value="이경무" > 이경무</option>
		  <option value="오인석" > 오인석</option>
		  <option value="이정환" > 이정환</option>
      </select></td>
    <td width="68"><input type="button" name="send_confirm" value="  확  인  " onClick="sendForm();" ></td>
  </tr>
  <tr>
    <td height="1" colspan="3" bgcolor="#FFCC00"></td>
    </tr>
</table>
</form>
</body>
</html>
