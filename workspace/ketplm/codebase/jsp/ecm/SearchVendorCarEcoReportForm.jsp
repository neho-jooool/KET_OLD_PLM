<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "00844") %><%--고객사별 차종별 ECO 현황 조회--%></title>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language="javascript" src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/Calendar.js"></script>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>
<script language=JavaScript src="/plm/jsp/ecm/SearchVendorCarEcoReport.js"></script>
<script language="javascript">
</script>
</head>
<body>
<form method="post" action="">
<input type="hidden" name="cmd" value="search">
<input type="hidden" name="page" value="">
<input type="hidden" name="totalCount" value="0">
<input type="hidden" name="sortColumn" value="1 DESC">
<input type="hidden" name="param" value="parent.">
<input type="hidden" name="oid" value="">
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00844") %><%--고객사별 차종별 ECO 현황 조회--%></td>
                <td align="right">
                  <img src="/plm/portal/images/icon_navi.gif">Home
                  <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                  <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00196") %><%--ECO 현황조회--%>
                  <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00844") %><%--고객사별 차종별 ECO 현황 조회--%>
                </td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onfocus="this.blur();" onClick="javascript:doCancel();"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onfocus="this.blur();" onClick="javascript:doSearch();"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%></td>
          <td width="280" class="tdwhiteL"><select name="prodVendor" class="fm_jmp" style="width:250">
              <option value=""><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></option>
            </select></td>
          <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
          <td width="280" class="tdwhiteL0"><select name="select2" class="fm_jmp" style="width:250">
              <option value=""><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></option>
            </select></td>
        </tr>
        <tr>
          <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
          <td colspan="3" class="tdwhiteL0"><input type="text" name="createStartDate" class="txt_field"  style="width:80" readonly>
          &nbsp;<a href="#" onclick="javascript:showCal('createStartDate');"
          ><img src="/plm/portal/images/icon_6.png"border="0"
          ></a>&nbsp;&nbsp;<a href="#" onfocus="this.blur();" onClick="javascript:clearCondition(6);"
          ><img src="/plm/portal/images/icon_delete.gif" border="0"></a>&nbsp;~&nbsp;
          <input type="text" name="createEndDate" class="txt_field"  style="width:80" readonly>
          &nbsp;<a href="#" onclick="javascript:showCal('createEndDate');"
          ><img src="/plm/portal/images/icon_6.png"border="0"
          ></a>&nbsp;&nbsp;<a href="#" onfocus="this.blur();" onClick="javascript:clearCondition(7);"
          ><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><a href="javascript:doExcel();" onfocus="this.blur();"><img src="/plm/portal/images/iocn_excel.png" border="0"></a></td>
                    </tr>
                  </table></td>
                <td width="10"></td>
                <td align="right"><select name="perPage" class="fm_jmp" style="width:50" style="text-align:center;">
                <option value="10" selected>10</option>
                <option value="30">30</option>
                <option value="50">50</option>
                <option value="100">100</option>
                </select></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <iframe name="frmList" width="781" height="170" src="" border="0" cellspacing="0" cellpadding="0" frameborder="0" marginwidth="0" marginheight="0" scrolling="auto"></iframe>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
        <table border="0" cellspacing="0" cellpadding="0" width="780" id="pageControlTable">
        </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
<iframe name="download" align="center" width="0" height="0" border="0"></iframe>
</body>
</html>
