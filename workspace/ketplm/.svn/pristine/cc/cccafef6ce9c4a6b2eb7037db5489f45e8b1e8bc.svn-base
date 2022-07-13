<%@page import="e3ps.groupware.company.PeopleHelper"%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="e3ps.cost.servlet.LoginManager"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="e3ps.groupware.company.dao.CompanyDao"%>
<%@page import = "wt.org.*,
          wt.fc.PersistenceHelper,
          wt.session.SessionHelper,
          e3ps.common.util.CommonUtil"%>

<% LoginManager loginManager = LoginManager.getInstance(); %>
<%

    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	String userID = user.getName();
	System.out.println("ID :::: "+userID);
	CompanyDao company = new CompanyDao();
	String userPW = company.getPassword(userID);
	if("Administrator".equals(userID)){
	    userID = "neho";
	}
	String mode = StringUtil.checkNull(request.getParameter("mode"));
	String cost_url = StringUtil.checkNull(request.getParameter("cost_url"));
	cost_url = cost_url.replaceAll("@", "&");
	if(loginManager.isValid(userID, userPW)){
//		if(!loginManager.isUsing(userID)){
			loginManager.setSession(session, userID);
			if(mode.equals("cost_mail")){
				response.sendRedirect(cost_url);
			}else if(mode.equals("cost_appr")){
				String position = StringUtil.checkNull((String)session.getAttribute("position"));
				if(!position.equals("사장") && !position.equals("연구원장")){
					out.println("<script>alert('권한이 없습니다.');location.href='http://plm.ket.com/cost_apprLogin.jsp';</script>");
				}else{
					response.sendRedirect(cost_url);
				}
			}else{
				response.sendRedirect("/plm/jsp/cost/index.html");
			}
//		}else{
//		session.invalidate();
//		out.println("<script>alert('로그인 중입니다. 접속을 종료 하겠습니다.');location.href='/codebase/common/main.jsp';</script>");
//		}
	}else{
		session.invalidate();
		if(mode.equals("cost_appr")){
			out.println("<script>alert('ID와 PASSWORD가 다릅니다');location.href='/plm/jsp/cost/cost_apprLogin.jsp';</script>");
		}else{
			out.println("<script>alert('접속 권한이 없습니다.');window.close();</script>");
		}
	}
%>
