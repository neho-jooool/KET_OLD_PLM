<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="e3ps.cost.dao.CostAuthDao, e3ps.cost.control.CostAuthCtl"%>
<%@ page import="java.util.ArrayList, java.util.Hashtable"%>
<%
	String empno = request.getParameter("account");
	CostAuthCtl costAuthCtl = new CostAuthCtl();
	ArrayList modifyAuthList = costAuthCtl.modifyAuthList(empno);

	Hashtable modifyAuth = null;
	modifyAuth = (Hashtable)modifyAuthList.get(0);
	String account = (String)modifyAuth.get("account");
	String name = (String)modifyAuth.get("name");
	String groupName = (String)modifyAuth.get("groupName");
	String email = (String)modifyAuth.get("email");
	String position = (String)modifyAuth.get("position");
	String auth = (String)modifyAuth.get("auth");
	String groupMaster= (String)modifyAuth.get("groupMaster");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<link rel="stylesheet" type="text/css" href="/plm/jsp/cost/auth/css/auth/auth.css"/>
<link rel="stylesheet" type="text/css" href="/plm/jsp/cost/auth/css/button/button.css"/>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery.smartPop.min.js"></script><!-- 팝업 -->
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/fixedtableheader.min.js"></script><!-- 헤더고정 -->
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery.alphanumeric.js"></script><!-- 숫자만입력 -->
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/auth.js"></script>
</head>
<body>
<form id="modifyForm">
<input type="hidden" id="name" value="<%=name%>"/>
<input type="hidden" id="empno" value="<%=account%>"/>
<div align="center">
	<table border="0" cellspacing="0" cellpadding="0" class="tb">
	<tr>
		<th>사&nbsp;&nbsp;&nbsp;&nbsp;번&nbsp;:&nbsp;</th>
		<td align="left" class="tdEnd"><%=account %></td>
	</tr>
	<tr>
		<th>이&nbsp;&nbsp;&nbsp;&nbsp;름&nbsp;:&nbsp;</th>
		<td align="left" class="tdEnd"><%=name %></td>
	</tr>
	<tr>
		<th>직&nbsp;&nbsp;&nbsp;&nbsp;급&nbsp;:&nbsp;</th>
		<td align="left" class="tdEnd"><%=position %></td>
	</tr>
	<tr>
		<th>부&nbsp;&nbsp;&nbsp;&nbsp;서&nbsp;:&nbsp;</th>
		<td align="left" class="tdEnd"><%=groupName %></td>
	</tr>
	<tr>
		<th>E-mail&nbsp;:&nbsp;</th>
		<td align="left" class="tdEnd"><%=email %></td>
	</tr>
	<tr>
		<th>권&nbsp;&nbsp;&nbsp;&nbsp;한&nbsp;:&nbsp;</th>
		<td class="tdEnd" align="left"><input type="radio" name="auth" value="S" class="modifyRadio" <%if("S".equals(auth)){%>checked<%} %>>S |<input type="radio" name="auth" value="L" class="modifyRadio" <%if("L".equals(auth)){%>checked<%} %>>L |<input type="radio" name="auth" value="D" class="modifyRadio" <%if("D".equals(auth)){%>checked<%} %>>D |<input type="radio" name="auth" value="A" class="modifyRadio" <%if("A".equals(auth)){%>checked<%} %>>A</td>
	</tr>
	<tr>
		<th>그&nbsp;룹&nbsp;장&nbsp;:&nbsp;</th>
		<td class="tdEnd" align="left"><input type="checkbox" name="groupMaster" class="modifyRadio" <%if("G".equals(groupMaster)){%>checked<%} %>>그룹장</td>
	</tr>
	<tr>
		<td class="tdEnd" colspan="2">
			<input type="button" id="modifyAuth" class="button black medium" value="수정"/>
			<input type="button" id="deleteAuth" class="button black medium" value="삭제"/>
		</td>
	</tr>
	</table>
</div>
</form>
</body>
</html>