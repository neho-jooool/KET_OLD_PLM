<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="e3ps.cost.control.CostApprCtl"%>
<%@ page import="java.util.ArrayList, java.util.Hashtable, java.util.Calendar"%>

<%
	String team						= StringUtil.checkNull(request.getParameter("team"));
	String pk_cr_group				= StringUtil.checkNull(request.getParameter("pk_cr_group"));
	String rev_no						= StringUtil.checkNull(request.getParameter("rev_no"));
	String group_case_count	= StringUtil.checkNull(request.getParameter("group_case_count"));
	String step_no					= StringUtil.checkNull(request.getParameter("step_no"));
	String pps_note					= StringUtil.checkNull(request.getParameter("pps_note"));
	String send_ok					= StringUtil.checkNull(request.getParameter("send_ok"));

	String Ename 	= StringUtil.checkNull((String)session.getAttribute("Ename"));
	String Dname		= StringUtil.checkNull((String)session.getAttribute("Dname"));
	String emp_no	= StringUtil.checkNull((String)session.getAttribute("emp_no"));
	String dept_no	= StringUtil.checkNull((String)session.getAttribute("dept_no"));
	String K_pass	= StringUtil.checkNull((String)session.getAttribute("K_pass"));
	String position	= StringUtil.checkNull((String)session.getAttribute("position"));
	String login_id	= StringUtil.checkNull((String)session.getAttribute("id"));

	if(send_ok.equals("ok")){//반려요청
		CostApprCtl appr = new CostApprCtl();
		appr.Appr_reject_work(pk_cr_group, rev_no, team, group_case_count, pps_note, dept_no);
	}

%>
<html>
<head>
<title>한국단자 종합 개발 시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style type="text/css">
<!--
.style1 {font-size: 12px;font-weight: bold; color:#3D505A; text-align:center}
.style2 {font-size: 12px;text-align:left;}
.style3 {font-size: 14px; color:#FFFFFF; font-weight: bold;text-align:center}
.style4 {font-size: 12px;text-align:center}
-->
</style>
</head>
<script language = "javascript">

function init()
{
  document.u_login.pps_note.focus();

}


function sendForm()
{

	if( document.u_login.pps_note.value == "" )
	{
		alert(" 거부사유를 입력해주세요. ");
  		document.u_login.pps_note.focus();
	}
	else if( document.u_login.admin_pw.value == "" )
	{
		alert(" 비밀번호를 입력해주세요. ");
  		document.u_login.admin_pw.focus();
	}
	else
	{
		var pk_cr_group			= '<%=pk_cr_group%>';
		var step_no 			= '<%=step_no%>';
		var team 				= '<%=team%>';
		var rev_no 				= '<%=rev_no%>';
		var group_case_count 	= '<%=group_case_count%>';
		var send_ok 			= "ok";
		var pps_note = document.u_login.pps_note.value;
		pps_note = escape(encodeURIComponent(pps_note));

		var form = document.forms[0];
		form.action = "/plm/jsp/cost/costdb/reject_pro_work.jsp?pk_cr_group="+pk_cr_group+"&step_no="+step_no+"&team="+team+"&rev_no="+rev_no+"&group_case_count="+group_case_count+"&send_ok="+send_ok+"&pps_note="+pps_note;
		form.submit();
		alert("담당팀에 메일을 전송하였습니다.");
		var url = "/plm/jsp/cost/costdb/cost_re_view_test.jsp?pk_cr_group="+pk_cr_group+"&rev_no="+rev_no+"&data_type=main&group_case_count="+group_case_count+"&step_no=0";
		opener.parent.location.href = url;
		this.close();
   	}
}

</script>
<body onLoad=init();>
<form method="post" name= "u_login" target="_self">
  <table width="240" border="0" align="center" cellspacing="1">
  <tr bgcolor="#CCCCCC">
    <td height="20" colspan="2" valign="bottom" class="style2">&nbsp; * 거부 사유</td>
  </tr>
  <tr bgcolor="#CCCCCC">
    <td height="30" colspan="2" class="style4"><textarea name="pps_note" cols="30" rows="5"><%=pps_note%></textarea></td>
  </tr>
  <tr bgcolor="#CCCCCC">
    <td width="96" height="25" class="style1">Password</td>
    <td width="137" class="style4"><input name="admin_pw" type="password" class="style2" size="20" ></td>
  </tr>
  <tr bgcolor="#CCCCCC">
    <td height="25" colspan="2" class="style4"><input type="button" name="enter" value="확인" onClick="sendForm();" ></td>
  </tr>
</table>
</form>
</body>

</html>
