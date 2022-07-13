<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="e3ps.cost.servlet.LoginManager"%>

<% LoginManager loginManager = LoginManager.getInstance(); %>
<html>
<body>
<center>
현재 접속자수 : <%= loginManager.getUserCount() %><p>
<hr>
<%
             if(loginManager.isLogin(session.getId())){  //세션 아이디가 로그인 중이면
                           out.println(loginManager.getUserID(session.getId())+session.getAttribute("costAuth")+"님 안녕하세요<br>"
                                                                  +"<a href=logOut.jsp>로그아웃</a>");
             }
             else{  //그렇지 않으면 로그인 할 수 있도록
%>
<form name="login" action="login_ok.jsp" method="post">
아이디: <input type="text" name="userID"><br>
비밀번회: <input type="text" name="userPW"><br>
<input type="submit" value="로그인">
</form>
<%         }%>
</center>
</body>
</html>