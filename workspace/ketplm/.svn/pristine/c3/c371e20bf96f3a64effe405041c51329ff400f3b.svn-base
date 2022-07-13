<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="e3ps.cost.control.CostApprCtl"%>
<%
    String Ename 	= StringUtil.checkNull((String)session.getAttribute("Ename"));
    String login_id	= StringUtil.checkNull((String)session.getAttribute("cost_id"));

    String pk_cr_group 			= StringUtil.checkNull(request.getParameter("pk_cr_group"));
    String cost_report_add		= "ok";
    String user_case_count  	= StringUtil.checkNull(request.getParameter("user_case_count"));
    String report_pk					= StringUtil.checkNull(request.getParameter("report_pk"));
    String step_no					= StringUtil.checkNull(request.getParameter("step_no"));
    String table_row					= StringUtil.checkNull(request.getParameter("table_row") );
    String team						= StringUtil.checkNull(request.getParameter("team") );
    String rev_no						= StringUtil.checkNull(request.getParameter("rev_no") );
    String consent_txt						= StringUtil.checkNull(request.getParameter("consent_txt") );

    team = StringUtil.ChangeDeptNoCP(team);
    //11721	커넥터연구개발1팀
	//11722	커넥터연구개발2팀
	//11723	커넥터연구개발3팀
    /*
    if(team.equals("1")){
        //team = "11735";//연구개발1팀
        team = "11721";//커넥터연구개발1팀
    }else if(team.equals("11")){
    	team = "11722";//커넥터연구개발2팀
    }else if(team.equals("12")){
    	team = "11723";//커넥터연구개발3팀
    }else if(team.equals("13")){
    	team = "11681";//커넥터연구개발센타
    }else if(team.equals("22")){
    	team = "11724";//전장모듈연구개발1팀
    }else if(team.equals("23")){
    	team = "11725";//전장모듈연구개발2팀
    }else if(team.equals("24")){
    	team = "11726";//전장모듈연구개발3팀
    }else if(team.equals("3")){
    	team = "11737";//연구개발3팀
    }else if(team.equals("4")){
    	team = "11738";//연구개발4팀
    }else if(team.equals("6")){
    	team = "11740";//연구개발6팀
    }else if(team.equals("21")){
    	team = "11741";//시작개발1팀
    }
    */



    boolean mail_ok = false;
    CostApprCtl appr = new CostApprCtl();
    mail_ok = appr.Appr_report_ver_2(pk_cr_group, cost_report_add, user_case_count, report_pk, table_row, team, rev_no, Ename, step_no, login_id+"@ket.com",consent_txt,"");
    String send_ok = "";
    if(mail_ok){
        send_ok = "ok";
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script language= "javascript">
function CALL()
{
    var send_ok = '<%=send_ok%>';
    if(send_ok == "ok"){
        alert("결재를 요청하였습니다.");
        opener.parent.location.href = "/plm/jsp/cost/costreport/cost_report_1.jsp?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&report_pk=<%=report_pk%>&cost_report_add=<%=cost_report_add%>&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>" ;
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