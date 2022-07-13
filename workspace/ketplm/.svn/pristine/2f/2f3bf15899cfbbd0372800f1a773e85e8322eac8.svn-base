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
<title>결재승인</title>
<link href="../../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
//<![CDATA[
function approval(){
    var url = "/plm/jsp/cost/costdb/re_pass.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&step_no="+'<%=step_no%>'+"&team="+'<%=team%>'+"&visitor="+'<%=visitor%>'+"&select_name="+'<%=select_name%>'+"&rev_no="+'<%=rev_no%>'+"&group_case_count="+'<%=group_case_count%>'+"&f_name="+'<%=f_name%>'+"&statestate=approval";
    window.open(url, "window_2", "width=262,height=210,scrollbars=no");
}
function reject(){
    var url = "/plm/jsp/cost/costdb/re_pass.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&step_no="+'<%=step_no%>'+"&team="+'<%=team%>'+"&visitor="+'<%=visitor%>'+"&select_name="+'<%=select_name%>'+"&rev_no="+'<%=rev_no%>'+"&group_case_count="+'<%=group_case_count%>'+"&f_name="+'<%=f_name%>'+"&statestate=reject";
    window.open(url, "window_2", "width=262,height=210,scrollbars=no");
}
//]]>
</script>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/searchUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
</head>
<form name=requestApproval method="post">
<input type="hidden" name="pk_cr_group" value="<%=pk_cr_group%>"/>
<input type="hidden" name="step_no" value="<%=step_no%>"/>
<input type="hidden" name="team" value="<%=team%>"/>
<input type="hidden" name="visitor" value="<%=visitor%>"/>
<input type="hidden" name="select_name" value="<%=select_name%>"/>
<input type="hidden" name="rev_no" value="<%=rev_no%>"/>
<input type="hidden" name="group_case_count" value="<%=group_case_count%>"/>
<input type="hidden" name="f_name" value="<%=f_name%>"/>
<input type="hidden" name="statestate"/>
<body onload="javascript:window.focus();">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="../../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="../../../portal/images/icon_3.png"></td>
                <td class="font_01">결재승인</td>
              </tr>
            </table></td>
          <td width="136" align="right"><img src="../../../portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="380" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
          <td valign="top"><table border="0" cellspacing="0" cellpadding="0" width="380">
              <tr>
                <td class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="380">
              <tr>
                <td width="380" class="tdwhiteM0">
                  <table border="0" cellspacing="0" cellpadding="0" width="380">
                    <tr>
                      <td class="space5" align="center"><%=pk_cr_group %> 원가 요청서를 승인 하겠습니까?</td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <table width="380" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space10"></td>
              </tr>
            </table>
            <table width="380" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td align="center"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="../../../portal/images/btn_1.gif"></td>
                            <td background="../../../portal/images/btn_bg1.gif"><a href="javascript:approval()" class="btn_blue">승인</a></td>
                            <td width="10"><img src="../../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                      <td width="5">&nbsp;</td>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="../../../portal/images/btn_1.gif"></td>
                            <td background="../../../portal/images/btn_bg1.gif"><a href="javascript:reject()" class="btn_blue">반려</a></td>
                            <td width="10"><img src="../../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="380">
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
