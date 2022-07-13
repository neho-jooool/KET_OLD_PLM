<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="e3ps.cost.control.CostApprCtl"%>
<%
    String pk_cr_group 		=  StringUtil.checkNull(request.getParameter ("pk_cr_group"));
    String cost_report_add 	= "ok";
    String report_pk				=  StringUtil.checkNull(request.getParameter("report_pk"));
    String user_case_count 	=  StringUtil.checkNull(request.getParameter("user_case_count"));
    String step_no				=  StringUtil.checkNull(request.getParameter("step_no"));
    String table_row				=  StringUtil.checkNull(request.getParameter("table_row"));
    String team					=  StringUtil.checkNull(request.getParameter("team"));
    String pass_type			=  StringUtil.checkNull(request.getParameter("pass_type"));
    String rev_no					=  StringUtil.checkNull(request.getParameter("rev_no"));
    String p_leader_day 		=  StringUtil.checkNull(request.getParameter("p_leader_day"));
    String r_owner_day			=  StringUtil.checkNull(request.getParameter("r_owner_day"));
    String r_pre_day				=  StringUtil.checkNull(request.getParameter("r_pre_day"));
    String JB_day				=  StringUtil.checkNull(request.getParameter("JB_day"));
    String note_4					= StringUtil.checkNull(request.getParameter("note_4"));
    String K_pass				= StringUtil.checkNull((String)session.getAttribute("K_pass"));
    String Ename				= StringUtil.checkNull((String)session.getAttribute("Ename"));
%>
<html>
<head>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery-1.7.1.min.js"></script>
<title>한국단자 종합 개발 시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
<!--
.style1 {font-size: 12px;font-weight: bold; color:#3D505A; text-align:center}
.style2 {font-size: 12px;text-align:left;}
.style3 {font-size: 14px; color:#FFFFFF; font-weight: bold;text-align:center}
.style4 {font-size: 12px;text-align:center}
-->
</style>
</head>
<script language="javascript" src="/plm/jsp/cost/js/date_picker.js"></script>
<script language="javascript">
 DP_InitPicker();
</script>
<script language = "javascript">

function init()
{
  document.u_login.admin_pw.focus();
  <%if(pass_type.equals("ok_1")){%>
          document.u_login.consent_2[1].checked = true;
          consent_change_2('');
  <%}else if(pass_type.equals("ok")){%>
          document.u_login.consent[1].checked = true;
          consent_change("");
  <%}else{%>
          document.u_login.JB_day.value = '<%=JB_day%>';
          document.u_login.consent_1[1].checked = true;
        consent_change_1("");
  <%}%>


}

function sendForm()
{
    var db_pass = '<%=K_pass%>';
    var pass_type = '<%=pass_type%>';
     <%if(pass_type.equals("ok_1")){%>
        consent_change_2('');
    <%}else if(pass_type.equals("ok")){%>
        consent_change("");
    <%}else{%>
        document.u_login.JB_day.value = '<%=JB_day%>';
        consent_change_1("");
    <%}%>

    if(document.u_login.consent_txt.value.indexOf("'") > -1) {
    	alert("특수문자  ' 는 입력할 수 없습니다.");
    	return;
    }


    if( document.u_login.admin_pw.value == "" )
    {
        alert(" 비밀번호를 입력해주세요. ");
          document.u_login.admin_pw.focus();
    }
    else
    {
        if(db_pass != document.u_login.admin_pw.value){
            alert("비밀번호가 틀렸습니다.");
        }else{
           if((pass_type == "ok_1" && document.u_login.consent_2[1].checked == true) || (pass_type == "ok" && document.u_login.consent[1].checked == true)){//결재의견 update
           	   if(document.u_login.consent_txt.value != "" && document.u_login.consent_txt.value != null){
           	   		consentTxtUpdate();
           	   }
           }
           alert("완료메세지가 뜰 때까지 절대 화면을 이동하거나 마우스 클릭을 하지 마시기 바랍니다.");
           document.u_login.submit();
        }
    }
}
 /**********************************************
 결재여부 - 팀장
 **********************************************/

 function consent_change_2(a)
 {
   if (document.u_login.consent_2[0].checked == true ){   //반려
        document.getElementById('kyul_txt').innerText ="	* 반려(전결) 사유";
        document.u_login.consent_txt.disabled = false ;
        document.u_login.consent_txt.style.background = "#FFFFFF";
        document.u_login.action = "/plm/jsp/cost/costreport/work_no.jsp?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&step_no=<%=step_no%>&team=<%=team%>&report_pk=<%=report_pk%>&pass_type=<%=pass_type%>&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>";
    }
   else if (document.u_login.consent_2[1].checked == true ){  //결재
        document.getElementById('kyul_txt').innerText ="	* 결재의견";
        //document.u_login.consent_txt.disabled = false ;
        //document.u_login.consent_txt.style.background = "#E6E6E6";
        document.u_login.action = "/plm/jsp/cost/costreport/work_ok.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&step_no="+'<%=step_no%>'+"&team="+'<%=team%>'+"&report_pk="+'<%=report_pk%>'+"&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>'+"&consent_txt="+document.u_login.consent_txt.value;
    }else{		//전결
        document.u_login.consent_txt.disabled = false ;
        document.getElementById('kyul_txt').innerText ="	* 반려(전결) 사유";
        document.u_login.consent_txt.style.background = "#FFFFFF";
        document.u_login.action = "/plm/jsp/cost/costreport/work_finish.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&step_no="+'<%=step_no%>'+"&team="+'<%=team%>'+"&report_pk="+'<%=report_pk%>'+"&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>'+"&note_4="+'<%=note_4%>'+"&pass_type=7"+"&consent_txt="+document.u_login.consent_txt.value;
    }
 }
 /**********************************************
 결재여부 - 센타장
 **********************************************/

 function consent_change(a)
 {
   if (document.u_login.consent[0].checked == true ){   //반려
        document.u_login.consent_txt.disabled = false ;
        document.getElementById('kyul_txt').innerText ="	* 반려(전결) 사유";
        document.u_login.consent_txt.style.background = "#FFFFFF";
        document.u_login.action = "/plm/jsp/cost/costreport/work_no.jsp?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&step_no=<%=step_no%>&team=<%=team%>&report_pk=<%=report_pk%>&pass_type=<%=pass_type%>&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>";
    }
   else if (document.u_login.consent[1].checked == true ){  //결재
        //document.u_login.consent_txt.disabled = true ;
        //document.u_login.consent_txt.style.background = "#E6E6E6";
         document.getElementById('kyul_txt').innerText ="	* 결재의견";
         document.u_login.action = "/plm/jsp/cost/costreport/work_ok.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&step_no="+'<%=step_no%>'+"&team="+'<%=team%>'+"&report_pk="+'<%=report_pk%>'+"&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>'+"&consent_txt="+document.u_login.consent_txt.value;
    }
   else{		//전결
        document.u_login.consent_txt.disabled = false ;
        document.getElementById('kyul_txt').innerText ="	* 반려(전결) 사유";
        document.u_login.consent_txt.style.background = "#FFFFFF";
        document.u_login.action = "/plm/jsp/cost/costreport/work_finish.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&step_no="+'<%=step_no%>'+"&team="+'<%=team%>'+"&report_pk="+'<%=report_pk%>'+"&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>'+"&note_4="+'<%=note_4%>'+"&pass_type=6"+"&consent_txt="+document.u_login.consent_txt.value;
    }
 }

  /**********************************************
 결재여부 - 본부장
 **********************************************/

 function consent_change_1(a)
 {
   if (document.u_login.consent_1[0].checked == true ){		//반려
        document.u_login.consent_txt.disabled = false ;
        document.u_login.consent_txt.style.background = "#FFFFFF";
        document.u_login.action = "/plm/jsp/cost/costreport/work_no.jsp?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&step_no=<%=step_no%>&team=<%=team%>&report_pk=<%=report_pk%>&pass_type=<%=pass_type%>&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>";
    }
   else{				//전결
        document.u_login.consent_txt.disabled = false ;
        document.u_login.consent_txt.style.background = "#FFFFFF";
        document.u_login.action = "/plm/jsp/cost/costreport/work_finish.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&step_no="+'<%=step_no%>'+"&team="+'<%=team%>'+"&report_pk="+'<%=report_pk%>'+"&pass_type=2&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>'+"&JB_day="+document.u_login.JB_day.value+"&p_leader_day="+'<%=p_leader_day%>'+"&r_owner_day="+'<%=r_owner_day%>'+"&r_pre_day="+'<%=r_pre_day%>'+"&note_4="+'<%=note_4%>';
    }
 }

function consentTxtUpdate(){
	var consent_txt = escape(encodeURIComponent(document.u_login.consent_txt.value));
	var Ename = '<%=Ename%>';
	Ename = escape(encodeURIComponent(Ename));
	var queryString = "/plm/jsp/cost/costreport/consentAction.jsp?consent_txt="+consent_txt+"&report_pk="+'<%=report_pk%>'+"&Ename="+Ename;

	$.ajax({
		url:queryString,
		type:"POST",
		dataType:"xml",
		success:function(data){
		}
	});
}
</script>
<body onLoad=init();>
<form  method=post name="u_login" action="">
  <table width="240" border="0" align="center" cellspacing="1">
<%if(pass_type.equals("ok_1")){%>
    <tr bgcolor="#CCCCCC">
      <td height="20" colspan="2" class="style2"><input type="radio" name="consent_2" value="no" onclick= "consent_change_2(this.a)" >
        반려
        <input type="radio" name="consent_2" value="yes" onclick= "consent_change_2(this.a)" >
        결재
        <input type="radio" name="consent_2" value="yes_end" onclick= "consent_change_2(this.a)" >
         전결</td>
    </tr>
    <%}else if(pass_type.equals("ok")){ %>
    <tr bgcolor="#CCCCCC">
      <td height="20" colspan="2" class="style2"><input type="radio" name="consent" value="no" onclick= "consent_change(this.a)" >
        반려
        <input type="radio" name="consent" value="yes" onclick= "consent_change(this.a)" >
        결재
        <input type="radio" name="consent" value="yes_end" onclick= "consent_change(this.a)" >
        전결</td>
    </tr>
<%}else{%>
    <tr bgcolor="#CCCCCC">
      <td height="9" colspan="2" class="style2"><input type="radio" name="consent_1" value="no" onclick= "consent_change_1(this.a)" >
        반려
        <input type="radio" name="consent_1" value="yes_end" onclick= "consent_change_1(this.a)">
        전결 </td>
    </tr>
    <tr bgcolor="#CCCCCC">
      <td height="10" colspan="2" class="style2">&nbsp;결재일: <input name="JB_day" type="text" size="12" class="DateInput" autocomplete="off" onBlur="DP_PickerInput_blur()"></td>
    </tr>
<%}%>
    <tr bgcolor="#CCCCCC">
      <td id = "kyul_txt" height="20" colspan="2" valign="bottom" class="style2">&nbsp; * 반려(전결) 사유</td>
    </tr>
    <tr bgcolor="#CCCCCC">
      <td height="30" colspan="2" class="style4"><textarea name="consent_txt" cols="30" rows="5"></textarea></td>
    </tr>
    <tr bgcolor="#CCCCCC">
      <td width="110" height="25" class="style1">Password</td>
      <td width="123" class="style4"><input name="admin_pw" type="password" class="style2" size="20" ></td>
    </tr>
    <tr bgcolor="#CCCCCC">
      <td height="25" colspan="2" class="style4"><input type="button" name="enter" value="확인" onClick="sendForm();" ></td>
    </tr>
  </table>
</form>
</body>

</html>
