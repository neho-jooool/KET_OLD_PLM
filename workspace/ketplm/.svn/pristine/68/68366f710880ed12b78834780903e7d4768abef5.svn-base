/**
 * Ldap 에서 사용자를 검색해서 검색된 사용자가 있으면
 * uid=wcadmin,ou=people,cn=AdministrativeLdap,cn=Windchill,o=ptc 를 return 하고
 * 없으면 null 을 return 한다.
 * 따라서 AuthCheck.jsp 등 SSO 를 검색하는 화면에서 사용자를 Check 해서 사용자가 존재 하지 않는다면
 * 시스템에 들어가는것을 막아야 한다.
 */
package e3ps.groupware.company.beans;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import ext.ket.shared.log.Kogger;

public class LdapSearch {

    private String ldapserver="127.0.0.1";
    private String basedn="";


    public LdapSearch() {
        super();
    }

    public String getUserFromLdap(String sUserName){
        DirContext ctx =null;
        Properties props = new Properties();

        props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        props.setProperty(Context.PROVIDER_URL,"ldap://" + ldapserver + ":389");

        props.setProperty(Context.SECURITY_PRINCIPAL,"");
        props.setProperty(Context.SECURITY_CREDENTIALS,"");

        try{

            ctx = new InitialDirContext(props);
            SearchControls search = new SearchControls();
            search.setSearchScope(SearchControls.SUBTREE_SCOPE);

            String searchFilter = "uid=" + sUserName;

            NamingEnumeration result = ctx.search(basedn,searchFilter,search);

            SearchResult entry = null;
            while(result.hasMoreElements()){
                entry = (SearchResult)result.nextElement();
                return entry.getName();
            }
        }catch(Exception e){
            Kogger.error(getClass(), e);
        }
        return null;
    }

    public static void main(String[] args) {
        LdapSearch ldapSearch = new LdapSearch();
        Kogger.debug(LdapSearch.class, ldapSearch.getUserFromLdap("wcadmin"));
    }
}
