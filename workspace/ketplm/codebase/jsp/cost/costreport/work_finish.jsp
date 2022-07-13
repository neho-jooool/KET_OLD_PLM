<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="e3ps.cost.control.CostApprCtl"%>
<%
        String Ename    = StringUtil.checkNull((String)session.getAttribute("Ename"));
        String Dname        = StringUtil.checkNull((String)session.getAttribute("Dname"));
        String emp_no   = StringUtil.checkNull((String)session.getAttribute("emp_no"));
        String dept_no  = StringUtil.checkNull((String)session.getAttribute("dept_no"));
        String K_pass   = StringUtil.checkNull((String)session.getAttribute("K_pass"));
        String position = StringUtil.checkNull((String)session.getAttribute("position"));
        String login_id = StringUtil.checkNull((String)session.getAttribute("cost_id"));

        String pk_cr_group      = StringUtil.checkNull(request.getParameter("pk_cr_group"));
        String cost_report_add  = "ok";
        String report_pk                = StringUtil.checkNull(request.getParameter("report_pk"));
        String step_no              = StringUtil.checkNull(request.getParameter("step_no"));
        String table_row                = StringUtil.checkNull(request.getParameter("table_row"));
        String team                 = StringUtil.checkNull(request.getParameter("team"));
        String consent_txt          = StringUtil.checkNull(request.getParameter("consent_txt"));
        String pass_type            = StringUtil.checkNull(request.getParameter("pass_type"));
        String rev_no                   = StringUtil.checkNull(request.getParameter("rev_no"));
        String user_case_count  = StringUtil.checkNull(request.getParameter("user_case_count"));

        String JB_day           = StringUtil.checkNull(request.getParameter("JB_day"));
        String p_leader_day     = StringUtil.checkNull(request.getParameter("p_leader_day"));
        String r_owner_day      = StringUtil.checkNull(request.getParameter("r_owner_day"));
        String r_pre_day            = StringUtil.checkNull(request.getParameter("r_pre_day"));
        String note_4               = StringUtil.checkNull(request.getParameter("note_4"));

        //11721	커넥터연구개발1팀
  	    //11722	커넥터연구개발2팀
  	    //11723	커넥터연구개발3팀
        if(team.equals("1")){
        	team = "11732";//커넥터설계1팀
        }else if(team.equals("11")){
    	    team = "11728";//커넥터개발팀
        }else if(team.equals("12")){
            team = "11729";//커넥터양산개선팀
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
        }else if(team.equals("5")){
            team = "11743";//연구개발5팀
        }else if(team.equals("6")){
            team = "11740";//연구개발6팀
        }else if(team.equals("21")){
            team = "11745";//시작개발팀
        }else if(team.equals("7")){
            team = "11734";//전장부품개발팀
        }

        boolean mail_ok = false;
        CostApprCtl appr = new CostApprCtl();
        if(pass_type.equals("6") || pass_type.equals("7")){
        	mail_ok = appr.Appr_report_ver_2(pk_cr_group, cost_report_add, user_case_count, report_pk, table_row, team, rev_no, Ename, step_no, login_id+"@ket.com",consent_txt,pass_type);
        }else{
        	mail_ok = appr.Appr_report_ver_3(table_row, team, step_no, pass_type, consent_txt, report_pk,pk_cr_group, rev_no, JB_day, p_leader_day,  r_owner_day, r_pre_day, note_4, Ename, login_id+"@ket.com");
        }

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
            <%//if(pass_type.equals("2" )){%>
                //alert("2차 배포를 완료하였습니다.");
            <%//}%>
            <%//if(pass_type.equals("3" )){%>
                //alert("3차 배포를 완료하였습니다.");
            <%//}%>
            <%if(pass_type.equals("5" )){%>
                alert("최종 배포를 완료하였습니다.");
            <%}else if(pass_type.equals("6") || pass_type.equals("7")){%>
            	alert("전결되었습니다.");
            <%}else{%>
            	alert("결재를 완료하였습니다.");
            <%}%>

            <%if(pass_type.equals("1") || pass_type.equals("2") || pass_type.equals("3") || pass_type.equals("4") || pass_type.equals("6") || pass_type.equals("7") ){%>
                opener.parent.location.href = "/plm/jsp/cost/costreport/cost_report_1.jsp?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&report_pk=<%=report_pk%>&cost_report_add=<%=cost_report_add%>&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>" ;
            <%}else{%>
                window.location.href = "/plm/jsp/cost/costreport/cost_report_1.jsp?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&report_pk=<%=report_pk%>&cost_report_add=<%=cost_report_add%>&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>" ;
            <%}%>
        }else{
            alert("결재가 실패하였습니다.");
        }
        this.close();
    }
    </script>
    </script>
    </head>
    <body onload="CALL();">
    </body>
    </html>