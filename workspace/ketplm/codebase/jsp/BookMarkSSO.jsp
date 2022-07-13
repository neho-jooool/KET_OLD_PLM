<%@page import="e3ps.groupware.company.People"%>
<%@page import="e3ps.groupware.company.E3PSCompanyHelper"%>
<%@page import="e3ps.groupware.company.PeopleHelper"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.util.Base64"%>
<%@page import="ext.ket.shared.util.CredentialCookie"%>
<%@page import="ext.ket.shared.util.LoginAuthUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>

<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<!-- 로그 설정 임포트 끝 -->
<%@ page contentType="text/html;charset=UTF-8" session="true" %>
<%
  /*   response.setHeader("Pragma", "no-cache;");
    response.setHeader("Expires", "-1;"); */

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // Proxies.

    String loginType = request.getParameter("mode");
    String returnUrl = request.getParameter("returnUrl");
    String emp_no    = request.getParameter("emp_no");//연말정산 접속용
    String viewtype = request.getParameter("viewtype");//타시스템에서 접속시 구분자
    String parameter = request.getParameter("parameter");//타시스템서 접속시 파라미터
    
    String kmsId = "";
    String kmsPw = "";
    String kmsLinkId = "";
    String redirectUrl = "";
    String pboOid = request.getParameter("pboOid");
    String pax_webid = request.getParameter("pax_webid");
    Kogger.debug("######## SSO Login #######");
    //Kogger.debug("ID : " + pax_webid);
    
    //EP Login
    try{
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for( int i = 0; i < cookies.length; i++ ){ 
                Cookie cookie = cookies[i];
                if(cookie.getName().equals("kms_linkID")){
                   kmsLinkId =  cookie.getValue();  
                }
            } 
        }
        
        kmsLinkId = new String(Base64.decode(kmsLinkId),"UTF-8");
        if(StringUtil.checkString(kmsLinkId)){
            String[] kmsLinkArr = kmsLinkId.split("[|]");
            kmsId = kmsLinkArr[0];
            kmsPw = kmsLinkArr[1];
        }
        
        if("neho".equals(pax_webid) || "neho".equals(kmsId) || "plm01".equals(pax_webid) || "plm01".equals(kmsId)){//관리자 권한으로 접속하기 위함.neho 계정이 없어서..
            kmsId = "wcadmin";
            kmsPw = "wcadmin";
        }
        
        Kogger.debug("ID : " + kmsId);
        Kogger.debug("PW : " + kmsPw);
        System.out.println("kmsId ::: " + kmsId);
        System.out.println("kmsPw ::: " + kmsPw);
        
        boolean isValidatePassword = LoginAuthUtil.validatePassword(kmsId, kmsPw);
        
        
        if(!isValidatePassword){//ep에서 비밀번호 변경되어 plm에 로그인 할 수 없는 경우 개별적으로 비밀번호 동기화 2017.05.16 by 황정태
            LoginAuthUtil.syncPassword(kmsId, kmsPw);
            isValidatePassword = LoginAuthUtil.validatePassword(kmsId, kmsPw);
        }
        
        if("REPLACEPART".equals(loginType)){
            if(isValidatePassword){
               redirectUrl = "/plm/ext/orther/ortherRedirect.do?viewtype=" + loginType +"&parameter=";
               CredentialCookie credentaialCookie = new CredentialCookie(request, response);
               credentaialCookie.set(kmsId, kmsPw);
               response.setHeader("Location", redirectUrl);
               response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            }else{
               %>
               <script type="text/javascript">alert("권한이 없습니다.");window.close();</script>
               <%
            }
            
        }else{
            if (isValidatePassword) {
                if(StringUtil.checkString(loginType) && "cost_mail".equals(loginType)){//원가시스템 접속 처리
                   Kogger.debug("returnUrl = >"+returnUrl);
                   redirectUrl = "/plm/jsp/cost/common/login_ok.jsp?mode="+loginType+"&cost_url="+returnUrl;
                }else{
                   if(StringUtil.checkString(loginType) && "mail".equals(loginType)){
                       redirectUrl = "/plm/ext/mailRedirect.do?pboOid=" + pboOid;
                   }else if (StringUtil.checkString(loginType) && "yesone".equals(loginType)) {//연말정산 접속처리
                       redirectUrl = "/plm/ext/yesone/yesoneRedirect.do?emp_no=" + emp_no;
                   }else if (StringUtil.checkString(loginType) && "orther".equals(loginType)) {//qms 등 타시스템에서 접속처리
                       redirectUrl = "/plm/ext/orther/ortherRedirect.do?viewtype=" + viewtype +"&parameter="+parameter;
                   }else{
                       redirectUrl = "/plm/ext/main.do";
                   }
                }
            
                CredentialCookie credentaialCookie = new CredentialCookie(request, response);
                credentaialCookie.set(kmsId, kmsPw);
                response.setHeader("Location", redirectUrl);
                response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            } else if("KPLUS1".equalsIgnoreCase(pax_webid)) {
                CredentialCookie credentaialCookie = new CredentialCookie(request, response);
                credentaialCookie.set("kplus1", "kplus1");
                response.setHeader("Location", "/plm/ext/main.do");
                response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            } else{
                if(StringUtil.checkString(pax_webid) || "yesone".equals(loginType) || "orther".equals(loginType)){
                    CredentialCookie credentaialCookie = new CredentialCookie(request, response);
                    credentaialCookie.set("kplus2", "kplus2");
                    if("yesone".equals(loginType)){//연말정산 접속처리
                       redirectUrl = "/plm/ext/yesone/yesoneRedirect.do?emp_no=" + emp_no;
                       response.setHeader("Location", redirectUrl);
                    }else if ("orther".equals(loginType)) {//qms 등 타시스템에서 접속처리
                            redirectUrl = "/plm/ext/orther/ortherRedirect.do?viewtype=" + viewtype +"&parameter="+parameter;
                            response.setHeader("Location", redirectUrl);
                    }else{
                       response.setHeader("Location", "/plm/ext/main.do");
                    }
                    response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
                }else{
                    CredentialCookie credentaialCookie = new CredentialCookie(request, response);
                    credentaialCookie.set("kplus1", "kplus1");
                    response.setHeader("Location", "/plm/ext/main.do");
                    response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
                }
            }
        }
        
        
    }catch(Exception e){
    Kogger.error(e);
    }

%>