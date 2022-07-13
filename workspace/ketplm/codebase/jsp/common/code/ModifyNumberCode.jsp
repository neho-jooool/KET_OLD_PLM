<html>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.code.NumberCode,
        e3ps.common.code.NumberCodeType,
        e3ps.common.code.GenNumberCode,
        e3ps.common.util.StringUtil,
        e3ps.common.util.CommonUtil,
        e3ps.common.jdf.config.Config,
        e3ps.common.jdf.config.ConfigEx,
        e3ps.common.web.HtmlTagUtil"%>
<%@include file="/jsp/common/context.jsp"%>

<%
  Config conf = ConfigEx.getInstance("message");
  String oid = request.getParameter("oid");
  String codetype = request.getParameter("codetype");
  String str_CodeType = NumberCodeType.toNumberCodeType(codetype).getDisplay();
  NumberCode nCode = null;
  if(oid != null)
    nCode = (NumberCode)CommonUtil.getObject(oid);
  GenNumberCode gen = new GenNumberCode();
%>
<head>
<title> Modify NumberCode</title>
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
function doUpdate()
{
  if( checkField(formCode.name, "이름")
    || checkField(formCode.code, "코드"))
    return;
  var temp_codeType = document.forms[0].codetype.value;

//  if(!isValidCipher(temp_codeType, document.forms[0].code)) return;

  if(!confirm("<%=conf.getString("confirm.update")%>")) return;

  disabledAllBtn();
  document.forms[0].action = "/plm/servlet/e3ps/NumberCodeServlet?cmd=modify&codetype=<%=codetype%>";
  document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body>
<form name='formCode' method='post'>

<!-- Hidden Value -->
<input type='hidden' name='codetype' value='<%=codetype%>'>
<input type='hidden' name='oid' value='<%=oid%>'>

<div class=div_title><SCRIPT>printTitle('<%=str_CodeType%> <%=messageService.getString("e3ps.message.ket_message", "00124") %><%--CODE 수정--%>')</SCRIPT></div>
<div class=div_title_action>
  <input type='button' id=button2 value='확인' onClick="doUpdate()">
  <input type='button' id=button2 value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' onClick="self.close()">
</div>

<div class=div_body_title><%=str_CodeType%> <%=messageService.getString("e3ps.message.ket_message", "00124") %><%--CODE 수정--%></div>
<div class=div_body_row>
  <div class=div_row_label>이름<span class="style1"> *</span></div>
  <div class=div_row_long><input type='text' name='name' value="<%=nCode.getName()%>"  id=input></div>
</div>
<div class=div_body_row>
  <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%><span class="style1"> *</span></div>
  <div class=div_row_long><input type='text' name='code' value="<%=nCode.getCode()%>" id=input maxlength=5></div>
</div>
<div class=div_body_row>
  <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></div>
  <div class=div_row_long><input type='text' name='description' value="<%=StringUtil.checkNull(nCode.getDescription())%>" id=input></div>
</div>
</form>
</body>
</html>
