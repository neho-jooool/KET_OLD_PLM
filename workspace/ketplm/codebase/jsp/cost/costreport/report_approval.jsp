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
%>
<html>
<head>
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
<script language = "javascript">

function init()
{
    document.u_login.consent[1].checked = true;
    //document.u_login.consent_txt.disabled = true ;
    //document.u_login.consent_txt.style.background = "#E6E6E6";
    consent_change();
    document.u_login.admin_pw.focus();
    document.u_login.action = "/plm/jsp/cost/costreport/work_finish.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&step_no="+'<%=step_no%>'+"&team="+'<%=team%>'+"&report_pk="+'<%=report_pk%>'+"&pass_type="+'<%=pass_type%>'+"&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>'+"&note_4="+'<%=note_4%>';
}

function sendForm()
{
    var db_pass = '<%=K_pass%>';
    consent_change();
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
            //alert( document.u_login.action );
            alert("완료메세지가 뜰 때까지 절대 화면을 이동하거나 마우스 클릭을 하지 마시기 바랍니다.");
              document.u_login.submit();
        }
       }
}

  /**********************************************
 결재여부 - 본부장
 **********************************************/

 function consent_change(a)
 {
     if (document.u_login.consent[0].checked == true ){
         document.u_login.consent[1].checked = false;
         document.u_login.consent_txt.disabled = false ;
         document.u_login.consent_txt.style.background = "#FFFFFF"
         document.getElementById('kyul_txt').innerText ="	* 반려(전결) 사유";
         document.u_login.action = "/plm/jsp/cost/costreport/work_no.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&step_no="+'<%=step_no%>'+"&team="+'<%=team%>'+"&report_pk="+'<%=report_pk%>'+"&pass_type=no_"+'<%=pass_type%>'+"&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>';
    }else{
         document.u_login.consent[0].checked = false;
         //document.u_login.consent_txt.disabled = true ;
         //document.u_login.consent_txt.style.background = "#E6E6E6";
         document.getElementById('kyul_txt').innerText ="	* 결재의견";
         document.u_login.action = "/plm/jsp/cost/costreport/work_finish.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&step_no="+'<%=step_no%>'+"&team="+'<%=team%>'+"&report_pk="+'<%=report_pk%>'+"&pass_type="+'<%=pass_type%>'+"&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>'+"&consent_txt="+document.u_login.consent_txt.value;
    }
 }
</script>
<body onLoad=init();>
<form  method=post name="u_login" action="">
  <table width="240" border="0" align="center" cellspacing="1">
    <tr bgcolor="#CCCCCC">
      <td height="20" colspan="2" class="style2"><input type="radio" name="consent" value="no" onclick= "consent_change(this.a)" >
        반려
        <input type="radio" name="consent" value="yes" onclick= "consent_change(this.a)" >
        결재
      </td>
    </tr>
    <tr bgcolor="#CCCCCC">
      <td id = "kyul_txt" h height="20" colspan="2" valign="bottom" class="style2">&nbsp; * 반려 사유</td>
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
