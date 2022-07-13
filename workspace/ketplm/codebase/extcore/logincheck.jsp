<%@page import="e3ps.groupware.company.dao.CompanyDao"%>
<%@page import="ext.ket.shared.util.CredentialCookie"%>
<%@page import="ext.ket.shared.util.LoginAuthUtil"%>
<%
    String id = request.getParameter("j_username");
    //String pw = request.getParameter("j_password");
    // 권한 체크를 위해 임시로 설정 합니다.
    CompanyDao pwDao = new CompanyDao();
    String pw =  pwDao.getPassword(id);
    String redirectUrl = "";
    if(pw == null || "".equals(pw)){
	   pw = request.getParameter("j_password");
    }   
    if (LoginAuthUtil.validatePassword(id, pw)) {

		redirectUrl = "/plm/ext/main.do";

		CredentialCookie credentaialCookie = new CredentialCookie(request, response);
		credentaialCookie.set(id, pw);
		response.setHeader("Location", redirectUrl);
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
    } else {	    
	    
        redirectUrl = request.getParameter("redirectUrl");

	    if("main".equals(redirectUrl) ){
		  redirectUrl = "/plm/ext/main.do";
	    }else{
		  redirectUrl = "/";
	    }
		
		response.setHeader("Location", redirectUrl);
	    response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
    }
%>
