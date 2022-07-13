<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script language="javascript">
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
    
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
          <td width="220" class="tdwhiteL">
            <input name="docNumber" type="text" class="txt_field" style="width:98%" value="<%=partNumber %>"  onKeyPress="javascript:checkEnterKey(1);">
          </td>
          <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
          <td width="220" class="tdwhiteL0"><input type="text" name="docName" class="txt_field" style="width:98%" onKeyPress="javascript:checkEnterKey(1);"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" class="table_border" id="listEpmTable">
        <tr>
          <td width="40" class="tdblueM"><input type='checkbox' name='chkAllSelectObj' value='listEpmTable'></td>
          <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
          <td width="*" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
          <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
          <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
          <td width="110" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "04002") %><%--연관 ECO--%></td>
        </tr>
      </table>
    </td>
  </tr>
</table>