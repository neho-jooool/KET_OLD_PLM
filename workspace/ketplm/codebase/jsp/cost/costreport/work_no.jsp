<%@ page language="java" contentType="text/html;charset=euc-kr" pageEncoding="euc-kr" %>
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
    String consent_txt				= StringUtil.checkNull(request.getParameter("consent_txt") );
    String team						= StringUtil.checkNull(request.getParameter("team") );
    String pass_type				= StringUtil.checkNull(request.getParameter("pass_type") );
    String rev_no						= StringUtil.checkNull(request.getParameter("rev_no") );


    team = StringUtil.ChangeDeptNoCP(team);
    /*
    //11721	Ŀ���Ϳ�������1��
	//11722	Ŀ���Ϳ�������2��
	//11723	Ŀ���Ϳ�������3��
    if(team.equals("1")){
        //team = "11735";//��������1��
        team = "11721";//Ŀ���Ϳ�������1��
    }else if(team.equals("11")){
    	team = "11722";//Ŀ���Ϳ�������2��
    }else if(team.equals("12")){
    	team = "11723";//Ŀ���Ϳ�������3��
    }else if(team.equals("13")){
    	team = "11681";//Ŀ���Ϳ������߼�Ÿ
    }else if(team.equals("22")){
    	team = "11724";//�����⿬������1��
    }else if(team.equals("23")){
    	team = "11725";//�����⿬������2��
    }else if(team.equals("24")){
    	team = "11726";//�����⿬������3��
    }else if(team.equals("3")){
    	team = "11737";//��������3��
    }else if(team.equals("4")){
    	team = "11738";//��������4��
    }else if(team.equals("6")){
    	team = "11740";//��������6��
    }else if(team.equals("21")){
    	team = "11741";//���۰���1��
    }
    */


    boolean mail_ok = false;
    CostApprCtl appr = new CostApprCtl();
    mail_ok = appr.Appr_reportReject(table_row, team, step_no, pass_type, consent_txt, report_pk,pk_cr_group, rev_no, user_case_count, Ename, login_id+"@ket.com");
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
        alert("�ݷ� �Ͽ����ϴ�.");
        opener.parent.location.href = "/plm/jsp/cost/costreport/cost_report_1.jsp?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&report_pk=<%=report_pk%>&cost_report_add=<%=cost_report_add%>&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>" ;
    }else{
        alert("�ݷ� �����Ͽ����ϴ�.");
    }
    this.close();
}
</script>
</script>
</head>
<body onload="CALL();">
</body>
</html>