<%@ page language="java" contentType="text/html; charset=euc-kr"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="java.net.URLDecoder,java.util.ArrayList, java.util.Hashtable"%>
<%@ page import="e3ps.cost.control.CostApprCtl"%>
<%

    String team                         = StringUtil.checkNull(request.getParameter("team"));
    String pk_cr_group              = StringUtil.checkNull(request.getParameter("pk_cr_group"));
    String step_no                  = StringUtil.checkNull(request.getParameter("step_no"));
    String rev_no                       = StringUtil.checkNull(request.getParameter("rev_no"));
    String group_case_count         = StringUtil.checkNull(request.getParameter("group_case_count"));
    String login_id                 = StringUtil.checkNull((String)session.getAttribute("cost_id"));
    String f_name                   =  StringUtil.checkNull(request.getParameter("f_name"));
    //String dept_no                    =  StringUtil.checkNull(request.getParameter("dept_no"));
    String visitor                  =  StringUtil.checkNull(request.getParameter("visitor"));
    String select_name                  =  StringUtil.checkNull(request.getParameter("select_name"));
    String name = "";
    String Id = "";
    String dept_no = "";

    dept_no = StringUtil.ChangeDeptNoCP(team);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>결재요청</title>
<link href="../../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
//<![CDATA[
function sendForm(){

    var select = document.getElementById("groupJang_Id");
    var GroupJang_Id = $("#groupJang_Id").val();
    var GroupJang_name = $("#groupjangName").val();
    GroupJang_name = escape(encodeURIComponent(GroupJang_name));
    if(GroupJang_Id != null && GroupJang_Id != ""){
        var url = "/plm/jsp/cost/costdb/re_pass.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&step_no="+'<%=step_no%>'+"&team="+'<%=team%>'+"&visitor="+'<%=visitor%>'+"&select_name="+'<%=select_name%>'+"&rev_no="+'<%=rev_no%>'+"&group_case_count="+'<%=group_case_count%>'+"&GroupJang_name="+GroupJang_name+"&GroupJang_Id="+GroupJang_Id;
        window.open(url, "window_2", "width=262,height=210,scrollbars=no");
        //this.close();

    }else{
        alert("결재담당자가 지정되지 않았습니다.");
    }
}

function addTR(totalArray){
	var ptable;
	ptable = document.getElementById("sApproval");
	if(ptable.rows.length > 0){
		ptable.deleteRow(0);
	}
	var newTR = ptable.insertRow(0);
	for(var i=0; i<totalArray.length; i++){
	    var newTD = document.createElement('TD');
	    if(i==0){
	        newTD.innerText = totalArray[i];
	        newTD.width = "170";
	    }else if(i==1){
	        newTD.innerText = totalArray[i];
	        newTD.width = "90";
	    }else if(i==2){
	        newTD.innerText = totalArray[i];
	        newTD.width = "159";
	    }
	    newTD.className = "tdwhiteM";
	    newTR.appendChild(newTD);
	}
}
//]]>
</script>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/searchUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
</head>
<form name=requestApproval method="post">
<body onload="javascript:window.focus();">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="space5"></td>
        </tr>
        <tr>
          <td><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="../../../portal/images/icon_3.png"></td>
                <td class="font_01">결재담당자</td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="560" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="30">&nbsp;</td>
          <td valign="top"><table border="0" cellspacing="0" cellpadding="0" width="560">
              <tr>
                <td class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="560">
              <tr>
                <td width="120" class="tdblueL">결재담당자</td>
                <td width="440" class="tdwhiteM0"><table border="0" cellspacing="0" cellpadding="0" width="420">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                  <table width="420" border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                      <td>&nbsp;</td>
                      <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="../../../portal/images/btn_1.gif"></td>
                                  <td background="../../../portal/images/btn_bg1.gif"><a href="javascript:SearchUtil.selectCostOneUserById('groupJang_Id', 'groupjangName');" class="btn_blue">담당자지정</a></td>
                                  <td width="10"><img src="../../../portal/images/btn_2.gif"></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="420">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                  <table width="420" cellpadding="0" cellspacing="0" class="table_border">
                    <tr>
                      <td width="170" class="tdgrayM">부서</td>
                      <td width="90" class="tdgrayM">직급</td>
                      <td width="160" class="tdgrayM">이름</td>
                    </tr>
                  </table>
                      <div style="width: 420px; height: 50px; overflow-x: hidden; overflow-y: auto;">
                      <input type="hidden" id="groupJang_Id" /><input type="hidden" id="groupjangName" />
                    <table id="sApproval" name="sApproval" width="420" cellpadding="0" cellspacing="0" class="table_border"></table>
                  </div>
                  <table border="0" cellspacing="0" cellpadding="0" width="420">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table width="560" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space10"></td>
              </tr>
            </table>
            <table width="560" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td align="center"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="../../../portal/images/btn_1.gif"></td>
                            <td background="../../../portal/images/btn_bg1.gif"><a href="javascript:sendForm()" class="btn_blue">결재요청</a></td>
                            <td width="10"><img src="../../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                      <td width="5">&nbsp;</td>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="../../../portal/images/btn_1.gif"></td>
                            <td background="../../../portal/images/btn_bg1.gif"><a href="javascript:window.close()" class="btn_blue">닫기</a></td>
                            <td width="10"><img src="../../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="560">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</form>
</html>
