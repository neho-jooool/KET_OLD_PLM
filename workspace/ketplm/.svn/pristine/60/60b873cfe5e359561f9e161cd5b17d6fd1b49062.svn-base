/**
* @(#) WebUtil.java
* Copyright (c) e3ps. All rights reserverd
* 
 *	@version 1.00
 *	@since jdk 1.4.02 && Windchill 7.0
 *	@createdate 2005. 6. 27.
 *	@author Cho Sung Ok, jerred@e3ps.com
 *	@desc	
 */
package e3ps.common.web;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.httpgw.WTURLEncoder;

import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.CharUtil;

public class WebUtil {
	private static boolean isDecode = ConfigImpl.getInstance ().getBoolean ( "http.parameter.isdecode" );
	
	/**
     * Request로 받은 key, value를 Hashtabled으로 반환한다.
     * 
     * @param req
     * @return
     */
	
	public static ParamHash getHttpParamsFromAjax(HttpServletRequest req){
		ParamHash returnHash = new ParamHash();
        Enumeration eu = req.getParameterNames();
        String key = null;
        String[] value = null;
        while ( eu.hasMoreElements() ) {
            key = (String) eu.nextElement();
            value = req.getParameterValues(key);
            if (value == null) returnHash.put(key, "");
            else if (value.length == 1) {
            	 returnHash.put(key , WTURLEncoder.decode(value[0]));
            } else {
        	    String[] returnValue = new String[value.length];
    			for (int i = 0; i < value.length; i++) {
    				returnValue[i] = WTURLEncoder.decode(value[i]);
    			}
    			returnHash.put(key, returnValue);
            }
        }
        return returnHash;
		
	}
	
    public static ParamHash getHttpParams(HttpServletRequest req) {
    	ParamHash returnHash = new ParamHash();
        Enumeration eu = req.getParameterNames();
        String key = null;
        String[] value = null;
        while ( eu.hasMoreElements() ) {
            key = (String) eu.nextElement();
            
            value = req.getParameterValues(key);
            if (value == null) returnHash.put(key, "");
            else if (value.length == 1) {
            	if (isDecode) returnHash.put(key , CharUtil.E2K(value[0]));
            	else returnHash.put(key , value[0]);
            } else {
            	if (isDecode) {
        			String[] returnValue = new String[value.length];
        			for (int i = 0; i < value.length; i++) {
        				returnValue[i] = CharUtil.E2K(value[i]);
        			}
        			returnHash.put(key, returnValue);
        		} else {
        			returnHash.put(key, value);
        		}
            }
        }
        return returnHash;
    }
    
    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue){
    	int maxAge = (60 * 60 * 24 * 15);
    	setCookie(response, cookieName, cookieValue, maxAge);
    }
    
    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, int maxAge){
    	cookieValue = URLEncoder.encode(cookieValue);
    	Cookie cookie = new Cookie(cookieName, cookieValue);
    	cookie.setMaxAge(maxAge);
    	response.addCookie(cookie);
    }
    
    public static String getCookie(HttpServletRequest request, String cookieName){
    	Cookie cookies[] = request.getCookies();
    	String cookieValue = null;
		for(int i = 0; i < cookies.length; i++){
			String key = cookies[i].getName();
			if(cookieName.equals(key)){
				cookieValue = URLDecoder.decode(cookies[i].getValue());
				break;
			}
		}
		return cookieValue;
    }
}
