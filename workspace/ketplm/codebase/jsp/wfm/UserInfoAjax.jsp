<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.org.*"%>
<%@page import="e3ps.groupware.company.*"%>
<%@page import="java.io.PrintWriter"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<%
    response.setContentType("text/html; charset=UTF-8");
    response.setHeader("Cache-Control", "no-cache");
    String userid = request.getParameter("userid");
    PrintWriter pw = null;
    String userinfo = "";
    try {
		//Kogger.debug("아이디 "+userid);
		if (!userid.equals("")) {
		    People npeople = PeopleHelper.manager.getPeople(userid);
            PeopleData pdata = new PeopleData(npeople);
		    userinfo = pdata.departmentName + "+" + pdata.duty + "+" + pdata.name;
		    //Kogger.debug(userinfo);
		    pw = response.getWriter();
		    pw.print(userinfo);
		    pw.flush();
		}
    } catch (Exception e) {
	Kogger.error(e);
    } finally {
		if (pw != null) {
		    pw.close();
		}
    }
%>
