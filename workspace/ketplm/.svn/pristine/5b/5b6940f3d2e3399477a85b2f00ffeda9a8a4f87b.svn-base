package ext.ket.shared.util;

import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import wt.method.MethodContext;
import wt.method.RemoteMethodServer;
import wt.org.WTPrincipal;
import wt.queue.QueuePseudoMethodArgs;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.session.SessionServerHelper;
import e3ps.groupware.company.E3PSCompanyHelper;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;
import ext.ket.shared.log.Kogger;

/**
 * @클래스명 : LoginAuthUtil
 * @작성자 : Jason, Han
 * @작성일 : 2014. 6. 19.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class LoginAuthUtil {

    private static boolean VERBOSE = true;
    // private static String HOSTNAME = "";
    // private static String WEBAPPNAME = "";
    // private static String CODEBASE = "";
    // private static WTProperties wtproperties = null;
    private static Properties IEPROP = new Properties();

    private static String JNDIADAPTER = "";
    private static String LDAPHOST = "";
    private static String LDAPPORT = "";
    private static String LDAPURL = "";
    // private static String LDAPDN = "";
    // private static String LDAPPW = "";
    private static String SEARCHBASE = "";

    private static DirContext ctx = null;

    /**
     * @메소드명 : initialize
     * @작성자 : Administrator
     * @작성일 : 2014. 6. 19.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public static void initialize() {
	try {
	    // Hashtable userHash = new Hashtable();
	    // wtproperties = WTProperties.getLocalProperties();
	    // HOSTNAME = "plm.ket.com";//wtproperties.getProperty("java.rmi.server.hostname", "");
	    // WEBAPPNAME = "plm";//wtproperties.getProperty("wt.webapp.name", "");
	    // CODEBASE = "http://plm.ket.com/plm";//wtproperties.getProperty("wt.server.codebase", "");

	    JNDIADAPTER = "com.ket.Ldap";// wtproperties.getProperty("wt.federation.org.defaultAdapter", "com.ptc.Ldap");
	    LDAPPORT = "389";
	    // IEPROP.load(new FileInputStream(wtproperties.getProperty("wt.federation.ie.propertyResource")));
	    IEPROP.load(new FileInputStream("D:/ptc/Windchill_10.2/Windchill/codebase/WEB-INF/ie.properties"));
	    String ldapSeeAlso = IEPROP.getProperty("seeAlso");

	    // int startIdx = ldapSeeAlso.indexOf("/") + 2;
	    int endIdx = ldapSeeAlso.indexOf("@");

	    // String ieIdPw = ldapSeeAlso.substring(startIdx, endIdx);

	    // LDAPDN = ieIdPw.substring(0, ieIdPw.indexOf(":"));
	    // LDAPPW = ieIdPw.substring(ieIdPw.indexOf(":") + 1);
	    // String product_root = wtproperties.getProperty("wt.home");
	    // String pw_property = "ie.ldap.managerPw";
	    // LDAPPW = WTKeyStoreUtil.decryptProperty(pw_property, LDAPPW, product_root);

	    ldapSeeAlso = ldapSeeAlso.substring(endIdx + 1);
	    endIdx = ldapSeeAlso.indexOf("/");
	    LDAPHOST = ldapSeeAlso.substring(0, endIdx);
	    LDAPURL = "ldap://" + LDAPHOST + ":" + LDAPPORT;
	    SEARCHBASE = "ou=people," + ldapSeeAlso.substring(endIdx + 1);

	    SEARCHBASE = SEARCHBASE.replaceAll("configuration", "AdministrativeLdap");

	    // userHash.put(Context.SECURITY_PRINCIPAL, LDAPDN);
	    // userHash.put(Context.SECURITY_CREDENTIALS, LDAPPW);
	    // userHash.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	    // userHash.put(Context.PROVIDER_URL, LDAPURL);

	    if (VERBOSE) {
		Kogger.debug(LoginAuthUtil.class, "### AuthUtil initialize ###");
		// Kogger.debug(getClass(), "==HOSTNAME : " + HOSTNAME);
		// Kogger.debug(getClass(), "==WEBAPPNAME : " + WEBAPPNAME);
		// Kogger.debug(getClass(), "==CODEBASE : " + CODEBASE);
		Kogger.debug(LoginAuthUtil.class, "==SEARCHBASE : " + SEARCHBASE);
		// Kogger.debug(LoginAuthUtil.class, "==LDAPDN : " + LDAPDN);
		// Kogger.debug(LoginAuthUtil.class, "==LDAPPW : " + LDAPPW);
		Kogger.debug(LoginAuthUtil.class, "==JNDIADAPTER : " + JNDIADAPTER);
		Kogger.debug(LoginAuthUtil.class, "==LDAPURL : " + LDAPURL);
	    }
	    // try {
	    // // ctx = new InitialDirContext(userHash);
	    // if (VERBOSE)
	    // Kogger.debug(getClass(), "### LdapService initialized...");
	    //
	    // } catch (NamingException ne) {
	    // if (ctx != null)
	    // ctx.close();
	    // Kogger.error(getClass(), ne);
	    // }
	} catch (Exception ex) {
	    Kogger.error(LoginAuthUtil.class, ex);
	}
    }

    /**
     * @param userId
     * @param passwd
     * @return
     * @throws Exception
     * @메소드명 : validatePassword
     * @작성자 : Administrator
     * @작성일 : 2014. 6. 19.
     * @설명 : 사용자의 ID와 암호를 Ldap과 직접 비교하여 유효한지를 검사
     * @수정이력 - 수정일,수정자,수정내용
     */
    public static boolean validatePassword(String userId, String passwd) throws Exception {

	initialize();

	boolean isValidUser = false;

	try {
	    // Kogger.debug(getClass(), "validatePassword ... " + userId + "/" + passwd);

	    Hashtable<String, String> env = new Hashtable<String, String>(5, 0.75f);

	    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	    env.put(Context.PROVIDER_URL, LDAPURL);
	    env.put(Context.SECURITY_AUTHENTICATION, "simple");
	    env.put(Context.SECURITY_PRINCIPAL, "uid=" + userId + "," + SEARCHBASE);
	    env.put(Context.SECURITY_CREDENTIALS, passwd);

	    ctx = new InitialDirContext(env);
	    isValidUser = true;
	    Kogger.debug(LoginAuthUtil.class, "### validatePassword :: true");

	} catch (NamingException ne) {
	    Kogger.error(LoginAuthUtil.class, ne);
	    isValidUser = false;
	    Kogger.debug(LoginAuthUtil.class, "### validatePassword :: false");
	} finally {
	    if (ctx != null) {
		ctx.close();
	    }
	}

	return isValidUser;
    }
    
    public static void syncPassword(String kmsId, String kmsPw) {
	if (!RemoteMethodServer.ServerFlag) {
	    Kogger.debug(LoginAuthUtil.class, "### ServerFlag :: false");
            Class argTypes[] = new Class[] {String.class, String.class};

            Object args[] = new Object[] {kmsId,kmsPw};

            try {
                RemoteMethodServer.getDefault().invoke("syncPassword", LoginAuthUtil.class.getName(), null,argTypes, args);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            return;
        }
	
	boolean bool = false;
	
	WTPrincipal localWTPrincipal = null;
	
	SessionContext localSessionContext = null;

	try {

	    WTPrincipal localObject = getAdministrator();


	    bool = SessionServerHelper.manager.setAccessEnforced(false);

	    localWTPrincipal = SessionContext.setEffectivePrincipal((WTPrincipal) localObject);

	    localSessionContext = SessionContext.newContext();

	    SessionHelper.manager.setPrincipal(SessionContext.getEffectivePrincipal().getName());
	    
	    People people = PeopleHelper.manager.getPeople(kmsId);
            
	    if (people != null) {
		Kogger.debug(LoginAuthUtil.class, "### Ldap 동기화 Start ");
        	E3PSCompanyHelper.service.changePassword(kmsId, kmsPw);
        	LoginAuthUtil.validatePassword(kmsId, kmsPw);
        	Kogger.debug(LoginAuthUtil.class, "### Ldap 동기화 End ");
            }
	    
	} catch (Exception e) {
	    e.printStackTrace();
	    
	} finally {

	    SessionServerHelper.manager.setAccessEnforced(bool);
	    SessionContext.setEffectivePrincipal(localWTPrincipal);
	    SessionContext.setContext(localSessionContext);
	    //localMethodContext.unregister();
	    //localMethodContext = null;
	}
    }
    
    public static WTPrincipal getAdministrator() {
	WTPrincipal localWTPrincipal = null;
	MethodContext localMethodContext = MethodContext.getContext(Thread.currentThread());
	int i = localMethodContext == null ? 1 : 0;

	try {

	    if (i != 0) {
		localMethodContext = new MethodContext(new QueuePseudoMethodArgs(LoginAuthUtil.class.getName(), "getAdministrator"));
	    }

	    localWTPrincipal = SessionHelper.manager.getAdministrator();

	} catch (Exception localException) {
	    localException.printStackTrace();
	} finally {
	    if (i != 0)
		localMethodContext.unregister();
	}
	return localWTPrincipal;
    }
}
