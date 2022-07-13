<%@ page language="java" contentType="text/html; charset=euc-kr"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="java.net.URLDecoder,java.util.ArrayList, java.util.Hashtable"%>
<%@ page import="e3ps.cost.control.CostApprCtl"%>
<%

    String team             			= StringUtil.checkNull(request.getParameter("team"));
    String pk_cr_group      		= StringUtil.checkNull(request.getParameter("pk_cr_group"));
    String step_no		    		= StringUtil.checkNull(request.getParameter("step_no"));
    String rev_no           			= StringUtil.checkNull(request.getParameter("rev_no"));
    String group_case_count 		= StringUtil.checkNull(request.getParameter("group_case_count"));
    String login_id					= StringUtil.checkNull((String)session.getAttribute("cost_id"));
    String f_name					=  StringUtil.checkNull(request.getParameter("f_name"));
    //String dept_no					=  StringUtil.checkNull(request.getParameter("dept_no"));
    String visitor					=  StringUtil.checkNull(request.getParameter("visitor"));
    String select_name					=  StringUtil.checkNull(request.getParameter("select_name"));
    String name = "";
    String Id = "";
    String dept_no = "";

    dept_no = StringUtil.ChangeDeptNoCP(team);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" >
<title>그룹장 지정</title>
</head>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/searchUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script language = "javascript">
function sendForm(){

    var select = document.getElementById("groupJang_Id");
    var GroupJang_Id = select.options[select.selectedIndex].value;
    var GroupJang_name = select.options[select.selectedIndex].text;
    GroupJang_name = escape(encodeURIComponent(GroupJang_name));
    if(GroupJang_Id != null && GroupJang_Id != ""){
        var url = "/plm/jsp/cost/costdb/re_pass.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&step_no="+'<%=step_no%>'+"&team="+'<%=team%>'+"&visitor="+'<%=visitor%>'+"&select_name="+'<%=select_name%>'+"&rev_no="+'<%=rev_no%>'+"&group_case_count="+'<%=group_case_count%>'+"&GroupJang_name="+GroupJang_name+"&GroupJang_Id="+GroupJang_Id;
        window.open(url, "window_2", "width=262,height=210,scrollbars=no");
        //this.close();

    }else{
        alert("그룹장이 지정되지 않았습니다.");
    }
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
<table width="480" height="67" border="0" align="center" cellpadding="0" cellspacing="1">
    <tr>
        <td height="18" colspan="3" class="style1">&nbsp;■ 그룹장 지정 </td>
    </tr>
    <tr>
        <td height="1" colspan="3" bgcolor="#FFCC00"></td>
    </tr>
    <tr>
        <td height="3" colspan="3"></td>
    </tr>
    <tr>
        <td height="21" align="center">
            <p id="groupJang_name"></p>
            <input type="hidden" id="groupJang_Id" /> 
            <a href="javascript:SearchUtil.selectCostOneUserById('groupJang_Id','groupJang_name');">
            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
            <a href="javascript:CommonUtil.deleteValueById('groupJang_Id','groupJang_name');">
            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
        </td>
  </tr>
  <tr>
      <td align="center"><input type="button" name="send_confirm" value="확 인" onClick="sendForm();" ></td>
  </tr>
  <tr>
    <td height="1" colspan="3" bgcolor="#FFCC00"></td>
    </tr>
</table>
</form>
</body>
</html>
