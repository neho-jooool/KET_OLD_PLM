<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="java.sql.*"%>
<%@ page import="e3ps.cost.util.MSDBUtil"%>
<%@ page import="e3ps.cost.util.DBUtil"%>
<%@ page import="e3ps.cost.dao.CostComDao"%>
<%@ page import="java.util.ArrayList, java.util.Hashtable, java.util.Calendar"%>
<%

    String admin_id = StringUtil.checkNull((String)session.getAttribute("emp_no"));
    //admin_id = "20110147";
    String team				= StringUtil.checkNull(request.getParameter("team"));
    String m_team			= StringUtil.checkNull(request.getParameter("team"));
    String pk_cr_group		= StringUtil.checkNull(request.getParameter("pk_cr_group"));
    String step_no			= StringUtil.checkNull(request.getParameter("step_no"));
    String rev_no			= StringUtil.checkNull(request.getParameter("rev_no"));
    String group_case_count	= StringUtil.checkNull(request.getParameter("group_case_count"));
    String consent_txt      = StringUtil.checkNull(request.getParameter("consent_txt"));
    String send_ok          = StringUtil.checkNull(request.getParameter("send_ok"));

    
    team = StringUtil.ChangeDeptNoCP(team);
	/*
    //11721	커넥터연구개발1팀
	//11722	커넥터연구개발2팀
	//11723	커넥터연구개발3팀
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

    Connection conn = null;
    conn = MSDBUtil.getConnection();
    String pw = "";
    String Le_name ="";
    String L_email ="";
    Hashtable SearchPassMap = null;
    try{
        CostComDao dao = new CostComDao(conn);

        ArrayList SearchPassList = dao.SearchPass(admin_id);
        SearchPassMap = (Hashtable)SearchPassList.get(0);
        pw   = (String)SearchPassMap.get("pw");
        L_email = (String)SearchPassMap.get("L_email");
        Le_name = (String)SearchPassMap.get("name");
    }catch(Exception e){
        e.printStackTrace();
        e.getMessage();
    }
    if(conn!=null) {conn.close();}

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
    document.u_login.consent_txt.disabled = true ;
    document.u_login.consent_txt.style.background = "#E6E6E6";
    document.u_login.admin_pw.focus();

}


function sendForm()
{

    if( document.u_login.admin_pw.value == "" )
    {
        alert(" 비밀번호를 입력해주세요. ");
          document.u_login.admin_pw.focus();
          return;
    }
    else
    {
        if(pass_ok(document.u_login.admin_pw.value)){

        }else{
            alert("입력하신 아이디와 비밀번호가 일치하지 않습니다.\n아이디 및 비밀번호를 확인후 다시 시도해 주십시요");
            document.u_login.admin_pw.value = "";
            document.u_login.admin_pw.focus();
            return;
        }
       }
    var pk_cr_group = '<%=pk_cr_group%>';
    var step_no = '<%=step_no%>';
    var m_team = '<%=m_team%>';
    var team = '<%=team%>';
    var rev_no = '<%=rev_no%>';
    var group_case_count = '<%=group_case_count%>';
    var L_email 		 = '<%=L_email%>';
    var Le_name = '<%=Le_name%>';
    Le_name = escape(encodeURIComponent(Le_name));//한글깨짐 방지
    var send_ok 	 = "";
    var msg     	 = "";
    var temp_step_no = "";
    var consent_txt = document.u_login.consent_txt.value;
    consent_txt = escape(encodeURIComponent(consent_txt));
    if (document.u_login.consent[0].checked == true ){
        send_ok = "no";
        msg = "반려하였습니다.";
        temp_step_no = "0";
    }else{
        send_ok = "ok";
        msg = "결재를 완료하였습니다.";
        temp_step_no = "2";
    }

    //var form = document.forms[0];
    //form.action = "/plm/jsp/cost/costdb/pass_login.jsp?pk_cr_group="+pk_cr_group+"&step_no="+temp_step_no+"&team="+m_team+"&rev_no="+rev_no+"&group_case_count="+group_case_count+"&send_ok="+send_ok+"&consent_txt="+consent_txt;
    //form.submit();

    var result_url = "/plm/jsp/cost/costdb/result_acc.jsp?send_ok="+send_ok+"&pk_cr_group="+pk_cr_group+"&rev_no="+rev_no+"&team="+team+"&group_case_count="+group_case_count+"&L_email="+L_email+"&Le_name="+Le_name+"&consent_txt="+consent_txt;
    window.open(result_url, "result", "width=0, height=0, top=10000, left=1000");
    alert(msg);
    var url = "/plm/jsp/cost/costdb/cost_re_view_test.jsp?pk_cr_group="+pk_cr_group+"&rev_no="+rev_no+"&data_type=main&group_case_count="+group_case_count+"&step_no="+temp_step_no;
    opener.parent.location.href = url;
    this.close();

}
 /**********************************************
 결재여부
 **********************************************/

 function consent_change(a)
 {
   if (document.u_login.consent[0].checked == true ){
        document.u_login.consent[1].checked = false;
        document.u_login.consent_txt.disabled = false ;
        document.u_login.consent_txt.style.background = "#FFFFFF";
    }
   else{
        document.u_login.consent[0].checked = false;
        document.u_login.consent_txt.disabled = true ;
        document.u_login.consent_txt.style.background = "#E6E6E6";

    }
 }

 function pass_ok(admin_pw){
     var db_pw = document.u_login.pw.value;
     if(db_pw == ""){
         return false;
     }else{
         if(admin_pw == db_pw){
             return true;;
         }else{
             return false;
         }
     }
 }
 function onKeyPress() {
    if (window.event) {
            if (window.event.keyCode == 13){
                sendForm();
            }
    } else return;
}
document.onkeypress = onKeyPress;
</script>
<body onLoad=init();>
<form  method=post name="u_login" target="_self" >
<input name="pw" type="hidden" size="2" value=<%=pw%>>
<input name="send_ok" type="hidden" size="2" value="">
  <table width="240" border="0" align="center" cellspacing="1">
  <tr bgcolor="#CCCCCC">
    <td height="20" colspan="2" class="style2"><input type="radio" name="consent" value="no" onclick= "consent_change(this.a)">
      반려
      <input type="radio" name="consent" value="yes" onclick= "consent_change(this.a)">
      결재</td>
  </tr>
  <tr bgcolor="#CCCCCC">
    <td height="20" colspan="2" valign="bottom" class="style2">&nbsp; * 반려 사유</td>
  </tr>
  <tr bgcolor="#CCCCCC">
    <td height="30" colspan="2" class="style4"><textarea name="consent_txt" cols="30" rows="5"></textarea></td>
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
