package ext.ket.shared.util;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @(#) CredentialCookie.java 
 * Copyright (c) Digitek. All rights reserverd
 * @version 1.00
 * @since jdk1.6.0_18
 * @createdate 2010. 11. 1.
 * @author Seong Jin, Han. narrsass@naver.com
 * @desc 
 */
public class CredentialCookie {

    private static final String USERID_COOKIE_NAME = "auth.uid";

    private static final String AUTH_COOKIE_NAME = "AuthCookie";
    
    public static final String USER_ID = "stc.UserId";
    
    public static final String CLIENT_IP = "stc.ClientIP";

    public static final String CREDIT = "stc.CREDIT";

    private HttpServletRequest request;

    private HttpServletResponse response;

    private Cookie userId;

    private Cookie authToken;

    public CredentialCookie(HttpServletRequest request,
            HttpServletResponse response) {
        this.request = request;
        this.response = response;
        userId = getCookie(USERID_COOKIE_NAME);
        authToken = getCookie(AUTH_COOKIE_NAME);
    }

    public void set(String userId, String password) {
        response.addCookie(createCookie(CredentialCookie.USERID_COOKIE_NAME,
                userId));

        String authStr = userId + ":" + password;

        response.addCookie(createCookie(CredentialCookie.AUTH_COOKIE_NAME, Base64.encodeBytes(authStr.getBytes())));

        HttpSession session = request.getSession(true);
        session.setAttribute(USER_ID, userId);
        session.setAttribute(CLIENT_IP, request.getRemoteHost());
        session.setAttribute(CREDIT, this);
        
    }

    public void remove() {
        deleteCookie(USERID_COOKIE_NAME);
        deleteCookie(AUTH_COOKIE_NAME);
    }

    public boolean isPresent() {
        return userId != null && authToken != null;
    }

    public String getUserId() {
        return userId != null ? userId.getValue() : null;
    }

    public String getAuthCookie() throws IOException {
        return authToken != null ? new String(Base64.decode(authToken.getValue())) : null;
    }

    private Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/plm");
        return cookie;
    }

    public void deleteCookie(String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public Cookie getCookie(String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static Cookie getCookie(HttpServletRequest _request, String name) {
        Cookie[] cookies = _request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static String getPasswordFromCookie(HttpServletRequest _request,
            boolean isUserid) throws IOException {
        Cookie[] cookies = _request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals(AUTH_COOKIE_NAME)) {
                    String tokenStr = cookie.getValue();
                    tokenStr = new String(Base64.decode(tokenStr));
                    StringTokenizer st = new StringTokenizer(tokenStr, ":");
                                        
                    if (st.countTokens() == 2) {
                        String userid = st.nextToken();
                        String password = st.nextToken();
                        if(isUserid){
                            return userid;
                        }else{
                            return password;
                        }
                    }
                }
            }
        }
        return null;
    }
}
