<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="e3ps.cost.dao.CostAuthDao, e3ps.cost.control.CostAuthCtl"%>
<%@ page import="java.util.ArrayList, java.util.Hashtable"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<link rel="stylesheet" type="text/css" href="/plm/jsp/cost/css/auth.css"/>
<link rel="stylesheet" type="text/css" href="/plm/jsp/cost/css/button/button.css"/>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/auth.js"></script>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery.smartPop.min.js"></script><!-- 팝업 -->
<script type="text/javascript" src="/plm/jsp/cost/js/jquery.smartPop.js"></script><!-- 팝업 -->
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/fixedtableheader.min.js"></script><!-- 헤더고정 -->
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery.alphanumeric.js"></script><!-- 숫자만입력 -->

</head>
<body>
<form id="insertForm">
<div align="center">
	<table border="0" cellspacing="0" cellpadding="0" class="tb">
	<tr>
		<th>사&nbsp;&nbsp;&nbsp;&nbsp;번&nbsp;:&nbsp;</th>
		<td align="left" class="tdEnd"><input type="text" id="account" size="20"/></td>
	</tr>
	<tr>
		<th>이&nbsp;&nbsp;&nbsp;&nbsp;름&nbsp;:&nbsp;</th>
		<td align="left" class="tdEnd"><input type="text" id="name" size="20"/></td>
	</tr>
	<tr>
		<th>권&nbsp;&nbsp;&nbsp;&nbsp;한&nbsp;:&nbsp;</th>
		<td class="tdEnd" align="left"><input type="radio" name="auth" value="S" class="modifyRadio">S |<input type="radio" name="auth" value="L" class="modifyRadio">L |<input type="radio" name="auth" value="D" class="modifyRadio">D |<input type="radio" name="auth" value="A" class="modifyRadio">A</td>
	</tr>
	<tr>
		<th>그&nbsp;룹&nbsp;장&nbsp;:&nbsp;</th>
		<td class="tdEnd" align="left"><input type="checkbox" name="groupMaster" class="modifyRadio">그룹장</td>
	</tr>
	<tr>
		<td class="tdEnd" colspan="2">
			<input type="button" id="insertAuth" class="button black medium" value="등록"/>
		</td>
	</tr>
	</table>
</div>
</form>
</body>
</html>