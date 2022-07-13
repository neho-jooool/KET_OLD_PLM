<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="e3ps.cost.servlet.LoginManager"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%
	/*LoginManager loginManager = LoginManager.getInstance();
	if(!loginManager.isLogin(session.getId())){
		out.println("<script>alert('로그인을 하지 않았습니다.');top.location='/plm/jsp/cost/common/main.jsp';</script>");
	}
	String userName = loginManager.getUserName(session.getId());*/

	LoginManager loginManager = LoginManager.getInstance();
	String userName = "";
	//String userAuth = "";
	String cost_id = StringUtil.checkNull((String)session.getAttribute("cost_id"));
	if("".equals(cost_id) || cost_id == null){
		out.println("<script>alert('로그인을 하지 않았습니다.');top.location='/plm/jsp/cost/common/main.jsp';</script>");
	}else{
		//userName = loginManager.getUserName(cost_id);
		userName  = (String)session.getAttribute("Ename");
		//userAuth = (String)session.getAttribute("costAuth");
	}



%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="Content-Type" content="text/html" charset="utf-8"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="/plm/jsp/cost/css/auth/auth.css"/>
<link rel="stylesheet" type="text/css" href="/plm/jsp/cost/css/button/button.css"/>
<link rel="stylesheet" type="text/css" href="/plm/jsp/cost/css/jquery.smartPop.css"/>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/fixedtableheader.min.js"></script><!-- 헤더고정 -->
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery.smartPop.min.js"></script><!-- 팝업 -->
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery.alphanumeric.js"></script><!-- 숫자만입력 -->
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/auth.js"></script>
<body>
<form id="auth_form">
	<table width="1241" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table width="1030" border="0" align="center" cellpadding="0" cellspacing="0" >
					<tr>
						<td><input name="userName" id="userName" type="text" size="15">
							<input type="button" id="searchAuth" class="button black medium" value="검색"/>
							<input type="button" id="insertUser" class="button black medium2" value="사용자 추가"/>
						</td>
					</tr>
					<tr><td height="10"></td></tr>
					<tr>
						<td>
							<table width="1030" border="0" cellspacing="0" cellpadding="0" class="tb" id="userList">
								<tr>
									<th width="68">NO</th>
									<th width="71">사번</th>
									<th width="61">이름</th>
									<th width="519">부서</th>
									<th width="111">직급</th>
									<th width="109">메일주소</th>
									<th width="91">권한등급</th>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</body>
</html>