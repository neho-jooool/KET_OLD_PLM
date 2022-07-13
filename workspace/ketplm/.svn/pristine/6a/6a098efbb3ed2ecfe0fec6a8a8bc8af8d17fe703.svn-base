/**
 * @(#)	CompanyState.java
 * Copyright (c) e3ps. All rights reserverd
 * 
 * @version 1.00
 * @since jdk 1.4.2
 * @author Cho Sung Ok, jerred@e3ps.com
 */

package e3ps.groupware.company;

import java.net.URL;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;

import wt.util.WTProperties;

public class CompanyState {
    private final static Config config = ConfigImpl.getInstance();
	public static Hashtable dutyTable = new Hashtable();
	public static Vector dutyNameList = new Vector();
	public static Vector dutyCodeList = new Vector();
	public static URL defautlURL;
	public static String companyName;
	public static String ldapAdapter;
	public static String ldapUser;
	public static String ldapPassword;
	public static String ldapDirectoryInfo;
	static {
		try {
			defautlURL = new URL(WTProperties.getServerCodebase().toString() + config.getString("people.default.image"));	
		} catch ( Exception e ) {}
		
		companyName = config.getString("people.company.name");
		String dutyNameStr = config.getString("people.dutylist");
		String dutyCodeStr = config.getString("people.dutycode");
		StringTokenizer nameToken = new StringTokenizer(dutyNameStr,";");
		StringTokenizer codeToken = new StringTokenizer(dutyCodeStr,";");
		while(nameToken.hasMoreTokens()){
		    String name = (String)nameToken.nextElement();
		    String code = (String)codeToken.nextElement();
		    dutyTable.put(code,name);
			dutyNameList.add(name);
			dutyCodeList.add(code);
		}

		ldapAdapter = config.getString("ldap.adapter");
		ldapUser = config.getString("ldap.user");
		ldapPassword = config.getString("ldap.password");
		ldapDirectoryInfo = config.getString("ldap.directory.info");
	}
}
