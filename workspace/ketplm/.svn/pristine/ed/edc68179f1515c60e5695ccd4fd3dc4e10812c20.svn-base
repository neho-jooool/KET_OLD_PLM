<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="../../portal/js/org.js"></script>
<script language=JavaScript src="../../portal/js/common.js"></script>

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

<script language='javascript'>

//저장 (Excel)
function saveFromExcel(){

    document.forms[0].action = '/plm/servlet/e3ps/PartServlet?cmd=createExcel';
    document.forms[0].method = "post";
    document.forms[0].submit();
}

//저장
function productSave(){

    document.forms[0].action = '/plm/servlet/e3ps/PartServlet?cmd=create';
    //document.forms[0].partType.value = "CAST";
    document.forms[0].method = "post";
    document.forms[0].submit();
}

</script>
</head>
<body>
<form method=post>

<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01567") %><%--부품 등록 테스트--%></td>
                <td align="right"><img src="../../portal/images/icon_navi.gif">Home<img src="../../portal/images/icon_navi.gif">BOM<img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01567") %><%--부품 등록 테스트--%></td>
              </tr>
              </table>
          </td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <!-- START :: 금형 부품 등록(Excel)
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                  <td background="../../portal/images/btn_bg1.gif"><a href="javascript:saveFromExcel();" class="btn_blue">금형부품 Excel등록</a></td>
                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                  </tr>
              </table>
              </td>
              </tr>
          </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <!-- END :: 금형 부품 등록(Excel) -->

      <!-- START :: 제품 부품 등록  -->
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                  <td background="../../portal/images/btn_bg1.gif"><a href="javascript:productSave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></a></td>
                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                  </tr>
              </table>
              </td>
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
          <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01595") %><%--부품유형--%></td>
          <td colspan="3" class="tdwhiteL0">
              <input name="partType" type="radio" class="Checkbox" value="P" checked><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%>&nbsp;&nbsp;
            <input name="partType" type="radio" class="Checkbox" value="CAST">Die No&nbsp;&nbsp;
            <input name="partType" type="radio" class="Checkbox" value="DIEM"><%=messageService.getString("e3ps.message.ket_message", "01064") %><%--금형부품--%>
          </td>
        </tr>

        <tr>
          <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
          <td width="290" class="tdwhiteL"><input type="text" name="number" class="txt_field"  style="width:270" id="textfield2"></td>
        </tr>
        <tr>
          <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
          <td width="290" class="tdwhiteL">
            <input type="text" name="name" class="txt_field"  style="width:270" id="textfield3">
          </td>
        </tr>
        <tr>
          <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01119") %><%--기본단위--%></td>
          <td width="290" class="tdwhiteL"><input type="text" name="unit" class="txt_field"  style="width:270" id="textfield2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
      <!-- END :: 제품 부품 등록  -->

      <!-- START :: 금형 부품 등록
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                  <td background="../../portal/images/btn_bg1.gif"><a href="#" class="btn_blue">금형 부품등록</a></td>
                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                  </tr>
              </table>
              </td>
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
          <td width="100" class="tdblueM">Die No</td>
          <td width="290" class="tdwhiteL"><input type="text" name="dieNo" class="txt_field"  style="width:270" id="textfield2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
      <!-- END :: 금형 부품 등록  -->
  </tr>
</table>
</form>
</body>
</html>
