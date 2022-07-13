<html>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.code.NumberCodeType,
        e3ps.common.code.GenNumberCode,
        e3ps.common.web.HtmlTagUtil"%>
<%@include file="/jsp/common/context.jsp"%>

<%
  String codetype = request.getParameter("codetype");
  String str_CodeType = NumberCodeType.toNumberCodeType(codetype).getDisplay();

  GenNumberCode gen = new GenNumberCode();
%>

<head>
<title> Create NumberCode</title>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/jsp/common/code/code.js"></SCRIPT>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<SCRIPT LANGUAGE="JavaScript">
<!--
function doSubmit()
{
  if( checkField(formCode.name, "이름")
    || checkField(formCode.code, "코드"))
    return;

  var temp_codeType = document.forms[0].codetype.value;

//  if(!isValidCipher(temp_codeType, document.forms[0].code)) return;

  disabledAllBtn();
  document.forms[0].action = "/plm/servlet/e3ps/NumberCodeServlet?cmd=create";
  document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body>
<form name='formCode' method='post'>

<!-- Hidden Value -->
<input type='hidden' name='codetype' value='<%=codetype%>'>

<div class=div_title><SCRIPT>printTitle('<%=str_CodeType%> <%=messageService.getString("e3ps.message.ket_message", "00123") %><%--CODE 등록--%>')</SCRIPT></div>
<div class=div_title_action>
  <input type='button' id=button2 value='등록' onClick="JavaScript:doSubmit()">
  <input type='button' id=button2 value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' onClick="self.close()">
</div>

<div class=div_body_title><%=str_CodeType%> CODE 등록</div>
<div class=div_body_row>
  <div class=div_row_label>이름<span class="style1"> *</span></div>
  <div class=div_row_long><input type='text' name='name' id=input></div>
</div>
<div class=div_body_row>
  <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%><span class="style1"> *</span></div>
  <div class=div_row_long><input type='text' name='code' id=input maxlength=5></div>
</div>
<div class=div_body_row>
  <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></div>
  <div class=div_row_long><input type='text' name='description' id=input></div>
</div>
</form>
</body>
</html>
