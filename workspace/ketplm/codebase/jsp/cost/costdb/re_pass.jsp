<%@ page contentType = "text/html; charset=euc-kr" %>
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="e3ps.cost.dao.CostComDao"%>
<%@ page import="e3ps.cost.util.DBUtil"%>
<%@ page import="e3ps.cost.util.MSDBUtil"%>
<%@ page import="e3ps.cost.util.MailUtil"%>
<%@ page import="e3ps.cost.control.CostApprCtl"%>
<%@ page import="java.sql.Connection"%>

<%
    String team             			= StringUtil.checkNull(request.getParameter("team"));
    String pk_cr_group      		= StringUtil.checkNull(request.getParameter("pk_cr_group"));
    String step_no		    		= StringUtil.checkNull(request.getParameter("step_no"));
    String rev_no           			= StringUtil.checkNull(request.getParameter("rev_no"));
    String group_case_count 	= StringUtil.checkNull(request.getParameter("group_case_count"));
    String login_id					= StringUtil.checkNull((String)session.getAttribute("cost_id"));
    String f_name					=  StringUtil.checkNull(request.getParameter("f_name"));
    String GroupJang_Id					=  StringUtil.checkNull(request.getParameter("GroupJang_Id"));
    String GroupJang_name                  =  StringUtil.checkNull(request.getParameter("GroupJang_name"));
    String statestate = StringUtil.checkNull(request.getParameter("statestate"));
    f_name = java.net.URLDecoder.decode(f_name, "UTF-8");
    GroupJang_name = java.net.URLDecoder.decode(GroupJang_name, "UTF-8");
    
    System.out.println("re_pass ::: pk_cr_group ::: " + pk_cr_group);
    System.out.println("re_pass ::: rev_no ::: " + rev_no);
    
    int cnt = 0;
    team = StringUtil.ChangeDeptNoCP(team);

    boolean mail_ok = false;
    CostApprCtl appr = new CostApprCtl();

    if(step_no.equals("0")){//개발담당자 결재
	/*
	    cnt = appr.GroupJangSearch(f_name,team,step_no);
        if(cnt > 0){
            mail_ok = appr.Appr_ver_1(pk_cr_group, rev_no, team, step_no,group_case_count,login_id);
        }else{
            mail_ok = false;
        }
    */
        mail_ok = appr.Appr_ver_1(pk_cr_group, rev_no, team, step_no, group_case_count, login_id, GroupJang_Id, GroupJang_name);
    }

    if(step_no.equals("0.5")){//그룹장 결재
	    if("approval".equals(statestate)){
            mail_ok = appr.Appr_ver_2(pk_cr_group, rev_no, team, step_no,group_case_count);
	    }else if("reject".equals(statestate)){
		    mail_ok = appr.Reject_ver_2(pk_cr_group, rev_no, team, step_no, group_case_count);
        }
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
<script language="JavaScript">

function CALL()
    {
        var send_ok = '<%=send_ok%>';
        var cnt = '<%=cnt%>';

        if(send_ok == 'ok'){
        	var state = '<%=statestate%>';
            var pk_cr_group = '<%=pk_cr_group%>';
            var rev_no      = '<%=rev_no%>';
            var group_case_count = '<%=group_case_count%>';
            var step_no = '<%=step_no%>';

            if(step_no == '0'){
                step_no = '0.5';
            }else if(step_no == '0.5'){
                step_no = '1';
            }else if(step_no == '1'){
                step_no = '2';
            }
            
            if(state == 'reject'){
            	step_no = '0'
            	alert("결재가 반려되었습니다.");
            }else{
                alert("결재를 요청하였습니다.");
            }
            
            var url = "http://plm.ket.com/plm/jsp/cost/costdb/cost_re_view_test.jsp?pk_cr_group="+pk_cr_group+"&rev_no="+rev_no+"&data_type=main&group_case_count="+group_case_count+"&step_no="+step_no;
            //opener.opener.parent.location.href = url;
            //opener.close();
            if(step_no == '0.5'){
                parent.opener.location = url;
            }else{
                opener.location = url;
            }

            //parent.opener.location.reload();
        }else{
        	alert("결재 요청이 실패하였습니다.");

        }
        self.close();
    }
</script>
</head>
<body onload="CALL();">
</body>
</html>
