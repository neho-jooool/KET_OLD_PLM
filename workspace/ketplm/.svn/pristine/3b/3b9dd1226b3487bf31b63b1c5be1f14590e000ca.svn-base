package e3ps.common.util;


import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import wt.util.WTProperties;

import com.ptc.windchill.keystore.WTKeyStoreUtil;

import ext.ket.shared.log.Kogger;

/** 
 * AuthUtil 인증관련 Utility Class
 * @author  Y. J. JEON
 * @version 1.0
 **/
public class LoginAuthUtil{
        
    private static boolean VERBOSE              = true;
    private static boolean DEFAULT_ENCODE_FLAG  = true;
    private static String HOSTNAME              = "";
    private static String WEBAPPNAME            = "";
    private static String CODEBASE              = "";
    private static WTProperties wtproperties    = null;
    private static Properties IEPROP            = new Properties();
    
    private static String JNDIADAPTER           = "";
    private static String LDAPHOST              = "";
    private static String LDAPPORT              = "";
    private static String LDAPURL               = "";
    private static String LDAPDN                = "";
    private static String LDAPPW                = "";
    private static String SEARCHBASE            = "";
    
    private static boolean isInitialized        = false;
    private static DirContext ctx               = null;
    
    public static String getBaseDn() {
    	return SEARCHBASE;
    }

    public static DirContext getCtx() {
    	return ctx;
    }
            
    public static void initialize(){
        try{
            Hashtable userHash  = new Hashtable();          
            wtproperties    = WTProperties.getLocalProperties();
            HOSTNAME        = wtproperties.getProperty("java.rmi.server.hostname","");
            WEBAPPNAME      = wtproperties.getProperty("wt.webapp.name","");
            CODEBASE        = wtproperties.getProperty("wt.server.codebase","");

            // Windchill Properties
            JNDIADAPTER = wtproperties.getProperty("wt.federation.org.defaultAdapter","com.ptc.Ldap");
            
            // Customizing Properties
            e3ps.common.jdf.config.Config myConfig = e3ps.common.jdf.config.ConfigImpl.getInstance();
            
            DEFAULT_ENCODE_FLAG = (new Boolean(myConfig.getString("auth.isauth"))).booleanValue();
            LDAPPORT= "389";//myConfig.getString("ldap.port");

            // InfoEngine Properties
            IEPROP.load( new FileInputStream(wtproperties.getProperty("wt.federation.ie.propertyResource")) );
            String ldapSeeAlso  = IEPROP.getProperty("seeAlso");

            int startIdx    = ldapSeeAlso.indexOf("/")+2;
            int endIdx      = ldapSeeAlso.indexOf("@");

            String ieIdPw   = ldapSeeAlso.substring(startIdx, endIdx);

            LDAPDN          = ieIdPw.substring(0, ieIdPw.indexOf(":"));
            LDAPPW          = ieIdPw.substring(ieIdPw.indexOf(":")+1);
            ldapSeeAlso     = ldapSeeAlso.substring(endIdx+1);
            endIdx          = ldapSeeAlso.indexOf("/");
            LDAPHOST        = ldapSeeAlso.substring(0,endIdx);
            LDAPURL         = "ldap://"+LDAPHOST+":"+LDAPPORT;
            SEARCHBASE      = "ou=people,"+ldapSeeAlso.substring(endIdx+1);
            
            
            SEARCHBASE = SEARCHBASE.replaceAll("configuration", "AdministrativeLdap");
            
            String product_root = wtproperties.getProperty("wt.home");
            Kogger.debug(LoginAuthUtil.class, product_root);
			 String pw_property = "ie.ldap.managerPw";
            // Setting the DirContext parameters
            userHash.put( Context.SECURITY_PRINCIPAL, LDAPDN );
            userHash.put( Context.SECURITY_CREDENTIALS, WTKeyStoreUtil.decryptProperty(pw_property, LDAPPW, product_root));
            userHash.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");                       
            userHash.put(Context.PROVIDER_URL, LDAPURL);
            //-- inform the LDAP provider that the value of 'loginAllowedTimeMap'           
            
            if( VERBOSE ){
                Kogger.debug(LoginAuthUtil.class, "### AuthUtil initialize ###");
                Kogger.debug(LoginAuthUtil.class, "==HOSTNAME : "+HOSTNAME);
                Kogger.debug(LoginAuthUtil.class, "==WEBAPPNAME : "+WEBAPPNAME);
                Kogger.debug(LoginAuthUtil.class, "==CODEBASE : "+CODEBASE);
                Kogger.debug(LoginAuthUtil.class, "==SEARCHBASE : "+SEARCHBASE);
                Kogger.debug(LoginAuthUtil.class, "==LDAPDN : "+LDAPDN);
                Kogger.debug(LoginAuthUtil.class, "==LDAPPW : "+LDAPPW);
                Kogger.debug(LoginAuthUtil.class, "==JNDIADAPTER : "+JNDIADAPTER);
                Kogger.debug(LoginAuthUtil.class, "==LDAPURL : "+LDAPURL);
                Kogger.debug(LoginAuthUtil.class, "==DEFAULT_ENCODE_FLAG : "+DEFAULT_ENCODE_FLAG);
            }
            
            try{                    
                ctx             = new InitialDirContext(userHash);                  
                isInitialized   = true;
                
                if (VERBOSE) 
                    Kogger.debug(LoginAuthUtil.class, "Ace Technology LdapService initialized...");
                
            }catch( NamingException ne ){
                if( ctx!=null )
                    ctx.close();                
                Kogger.error(LoginAuthUtil.class, ne);
            }
        }catch(Exception ex){
            Kogger.error(LoginAuthUtil.class, ex);
        }       
    }
    
    
    /** 
    * 사용자 정보를 Ldap으로 부터 읽어 들인다.
    * @param    userId      (사용자사번)     
    * @return   Attributes
    * @since    2005.02
    */
    public static Attributes getUserInfo(String userId){

        initialize();

        Attributes attrs    = null;
        String filter       = "uid="+userId+", "+SEARCHBASE;

        try{
            attrs   = ctx.getAttributes(filter);
            if( VERBOSE ){
                try{
                    Kogger.debug(LoginAuthUtil.class, "### AuthUtil getUserInfo ###");
                    Kogger.debug(LoginAuthUtil.class, "==uid : "+attrs.get("uid").get());
                    Kogger.debug(LoginAuthUtil.class, "==sn : "+attrs.get("sn").get());
                    Kogger.debug(LoginAuthUtil.class, "==mail : "+attrs.get("mail").get());
                    Kogger.debug(LoginAuthUtil.class, "==userPassword : "+attrs.get("userPassword").get());                
                }catch(Exception e){
                    Kogger.error(LoginAuthUtil.class, e);
                }
            }
        }catch(Exception e){
            Kogger.error(LoginAuthUtil.class, e);
        }finally{
            try{
                ctx.close();
            }catch(NamingException ne){}
        }
        return attrs;
    }
    
    
    /** 
    * 사용자의 Windchill password를 반환한다.(임시Password를 labeledUri로 대체함)
    * @param    userId      (사용자사번) 
    * @param    encodeFlag  (암호화 사용여부)
    * @return   String type의 사용자의 실제 Password 값(복호화하지 않음)
    * @since    2005.02
    */
    public static String getUserPassword(String userId, boolean encodeFlag)
    throws Exception{       

        initialize();

        String password     = "";
        try{
            Attributes attrs    = getUserInfo(userId);
            if( attrs!=null ){
                String bulkString   = "http://";
                password    = attrs.get("userPassword")==null?"":attrs.get("userPassword").get().toString();                
                if(password.indexOf(bulkString)>-1){
                    password    = password.substring(bulkString.length());                  
                }
                if( encodeFlag )
                    password    = Base64.decodeToString(password);
            }
        }catch(Exception e){
            Kogger.error(LoginAuthUtil.class, e);
        }
        return password;
    }
    
    
    /** 
    * 사용자의 Windchill password를 반환한다.
    * (Default로 암호화 처리가 추가됨).
    * @param    userId      (사용자사번)     
    * @return   String type의 사용자의 실제 Password 값(복호화하지 않음)
    * @since    2005.02
    * @see      getAuthorization(String userId, boolean encodeFlag)
    */
    public static String   getUserPassword(String userId){      
        String password     = "";
        try{
            password    = LoginAuthUtil.getUserPassword(userId, DEFAULT_ENCODE_FLAG);
        }catch(Exception ex){
            Kogger.error(LoginAuthUtil.class, ex);
        }
        return password;
    }
    
    
    /** 
    * 사용자의 ID와 암호를 Ldap과 직접 비교하여 유효한지를 검사   
    * @param    userId      (사용자사번)     
    * @param    passwd      (사용자Password)       
    * @return   String type의 사용자의 실제 Password 값(복호화하지 않음)
    * @since    2005.02
    * @see      getAuthorization(String userId, boolean encodeFlag)
    */
    public static boolean validatePassword(String userId, String passwd) throws Exception {
        
        initialize();
        
        boolean isValidUser = false;
        String semUserObjectId = "uid="+userId+"," + SEARCHBASE;
        
        try {           
            Kogger.debug(LoginAuthUtil.class, "validatePassword ... " + userId + "/" + passwd);
            SearchControls ctls = new SearchControls();
            ctls.setSearchScope( SearchControls.OBJECT_SCOPE );
            ctls.setReturningAttributes( new String[0] );
            String passStr  = "userPassword=" + (DEFAULT_ENCODE_FLAG?passwd:passwd);
            
            
            if( VERBOSE ){
                Kogger.debug(LoginAuthUtil.class, "### semUserObjectId - " + semUserObjectId);
                Kogger.debug(LoginAuthUtil.class, "### validatePassword - "+passStr);
                Kogger.debug(LoginAuthUtil.class, "### validatePassword - semUserObjectId : "+semUserObjectId);
                Kogger.debug(LoginAuthUtil.class, "### validatePassword - SearchControls : "+ctls);
                
            }
            NamingEnumeration sre = ctx.search( semUserObjectId, passStr, ctls );           

            if ( sre != null && sre.hasMoreElements()){
                isValidUser = true;
                SearchResult sResult    = (SearchResult)sre.nextElement();                  
                Attributes attrs        = (Attributes)sResult.getAttributes();
                ctx.modifyAttributes(semUserObjectId,2,attrs);
            }else{
                isValidUser = false;
                ctx.close();
            }           
        }catch(NamingException ne){
            Kogger.error(LoginAuthUtil.class, ne);
            isValidUser = false;
        }finally{
            
        }
        
        return isValidUser;
    }
    
    public static void getAuthentication(String id, String pw){
        try{
            Hashtable userHash  = new Hashtable();

            // Setting the DirContext parameters
            userHash.put( Context.SECURITY_PRINCIPAL, LDAPDN );
            userHash.put( Context.SECURITY_CREDENTIALS, LDAPPW );
            userHash.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");                       
            userHash.put(Context.PROVIDER_URL, LDAPURL);
            
            Kogger.debug(LoginAuthUtil.class, "### AuthUtil initialize ###");
            Kogger.debug(LoginAuthUtil.class, "==LDAPDN : "+LDAPDN);
            Kogger.debug(LoginAuthUtil.class, "==LDAPPW : "+LDAPPW);
            Kogger.debug(LoginAuthUtil.class, "==LDAPURL : "+LDAPURL);
            
            try{                    
                ctx             = new InitialDirContext(userHash);                  
                isInitialized   = true;             
                Kogger.debug(LoginAuthUtil.class, "############## LdapService initialized...");
            
            }catch( NamingException ne ){
                if( ctx!=null )
                    ctx.close();                
                Kogger.error(LoginAuthUtil.class, ne);
            }
        }catch(Exception ex){
            Kogger.error(LoginAuthUtil.class, ex);
        }
    }
    
    public static void main(String[] args) throws Exception {
		LoginAuthUtil.initialize();
		//Kogger.debug(getClass(), "basedn >> " + LoginAuthUtil.getBaseDn());

		// E3PSCompanyHelper.service.changePassword("PLMTFT", "k");
	}
    
}
