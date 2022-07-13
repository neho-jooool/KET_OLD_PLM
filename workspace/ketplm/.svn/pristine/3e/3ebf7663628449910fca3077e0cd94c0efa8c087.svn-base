<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="wt.session.*,wt.org.*,wt.fc.*,
                        e3ps.groupware.company.*,
                        java.util.*"%>
<%@page import="e3ps.groupware.util.PasswordChange"%>

<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<!-- 로그 설정 임포트 끝 -->

<%@ taglib uri="http://www.ptc.com/infoengine/taglib/core" prefix="ie"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="request" />
<jsp:setProperty name="wtcontext" property="request" value="<%= request %>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<ie:getService varName="ie"/>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "02975") %><%--패스워드 변경중--%></title>
</head>
<body>

<%
    String id = request.getParameter("id");
    String passwd = request.getParameter("_field_view");

    WTPrincipal oldPrincipal = SessionHelper.manager.getPrincipal();//지금 view를 보고있는 사용자 정보 받아오기
    String currentId = oldPrincipal.getName();
    boolean isMine = currentId.equals(id);

    Kogger.debug("changePassword", null, null, id + "  ==== " + passwd);

    PasswordChange.changePassword(id, passwd);

    QueryResult qr = PersistenceHelper.manager.navigate((WTUser)wt.org.OrganizationServicesMgr.getPrincipal(id), "people", WTUserPeopleLink.class);
    if (qr.hasMoreElements())
    {
        People people = (People) qr.nextElement();
        
        Kogger.debug("changePassword", null, null, ">> " + people.getName());

        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.MONTH, 1);
//        Kogger.debug(">> " + ca);
        people.setPwChangeDate(new java.sql.Timestamp(ca.getTimeInMillis()));
        people.setWarningCount(0);
        PersistenceHelper.manager.modify(people);
%>
        <SCRIPT>
            alert("<%=messageService.getString("e3ps.message.ket_message", "00009", id) %><%--{0} 패스워드가 변경되었습니다--%>");
            opener.document.forms[0].submit();
            self.close();
        </SCRIPT>
<%
    }
%>
<%--
    String cmd = request.getParameter("cmd");
    if("logout".equals(cmd))
    {
        response.sendRedirect("/plm/jsp/groupware/company/CheckPasswordLogout.jsp");
/*	    try{
            response.setHeader("authorization",null);
            session.invalidate();
            wtcontext.destroy();
            wtcontext.setSession(false);
            wtcontext.setAuthentication(null);

            Cookie mycookie = new Cookie ("AuthCookie","");
            mycookie.setPath("/");
            response.addCookie(mycookie);

            out.println("<script>document.location.href='/';</script>");
            return;

        }catch(Exception ex){
           ex.printStackTrace();
       }
        */
    }
--%>
</body>
</html>
<script language="javascript">
<!--
function goPage() {
<%	if ( isMine ) {	%>
    alert('<%=messageService.getString("e3ps.message.ket_message", "02977") %><%--패스워드가 변경되었습니다\n로그인을 다시 하여 주시기 바랍니다--%>');
<%	} else { %>
    alert('<%=messageService.getString("e3ps.message.ket_message", "02976") %><%--패스워드가 변경되었습니다--%>');
<%	}	%>
    self.close();
}
//-->
</script>
