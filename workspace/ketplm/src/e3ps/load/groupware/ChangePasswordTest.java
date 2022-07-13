/*
 * @(#) LoadPeople.java Create on 2005. 7. 7.
 * Copyright (c) e3ps. All rights reserved
 *
 * @version 1.00
 * @since jdk 1.4.02
 * @createdate 2005. 7. 7.
 * @author Choi Kang Hun, khchoi@e3ps.com
 */
package e3ps.load.groupware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import e3ps.common.util.LoginAuthUtil;
import ext.ket.shared.log.Kogger;

public class ChangePasswordTest
{
    public static void main(String[] args)
    {
//		String urlStr = "http://mjoh.ket.com/plm/jsp/SSOLogin.jsp?mode=cp&pax_webid=drm&pax_passwd=44444";
		String urlStr = "http://ketplmdev.ket.com/plm/jsp/SSOLogin.jsp?mode=cp&pax_webid=drm&pax_passwd=88888";

Kogger.debug(ChangePasswordTest.class,  "=====>ChangePasswordTest Call URL : " + urlStr );

		try
		{
			URL url = new URL( urlStr );
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setAllowUserInteraction(true);

			BufferedReader br = new BufferedReader( new InputStreamReader(connection.getInputStream()) );

			String thisLine = "";
			String rData = "";

			while( (thisLine = br.readLine()) != null )
			{
				rData += thisLine;
			}

			br.close();

			String ldap_pw = LoginAuthUtil.getUserPassword( "drm", false );

Kogger.debug(ChangePasswordTest.class,  "=====> ChangePasswordTest Call Result : "	+ ldap_pw );

		}
		catch (MalformedURLException e)
		{
			Kogger.error(ChangePasswordTest.class, e);
		}
		catch (IOException e)
		{
			Kogger.error(ChangePasswordTest.class, e);
		}
		catch( Exception e )
		{
			Kogger.error(ChangePasswordTest.class, e);
		}

        System.exit(0);
    }
}
