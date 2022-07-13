/**
 * @(#) PasswordChangeServlet.java Copyright (c) e3ps. All rights reserverd
 *
 * @version 1.00
 * @since jdk 1.6.18
 * @author Oh Myung Jae, mjoh@lgcns.com
 */

package e3ps.groupware.company.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.httpgw.WTContextBean;
import wt.method.MethodAuthenticator;
import wt.util.WTException;
import e3ps.common.util.AuthHandler;
import e3ps.common.web.CommonServlet;
import e3ps.groupware.company.E3PSCompanyHelper;
import ext.ket.shared.log.Kogger;

public class PasswordChangeServlet extends CommonServlet
{
	private static final long serialVersionUID = 1L;

	protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {

Kogger.debug(getClass(),  "######## PasswordChangeServlet Called ########" );

        WTContextBean wtcontextbean = new WTContextBean();
        wtcontextbean.setRequest(req);

		String userId = req.getParameter( "pax_webid" );
		String passwd = req.getParameter( "pax_passwd" );

		try
		{
			// 원격 호출로 인한 인증 세션이 없으므로, 패스워드 변경 대상 사용자 계정으로 인증 세션을 생성한다.
			MethodAuthenticator authenticator = AuthHandler.getMethodAuthenticator( userId );

			// 인증 세션이 정상적으로 생성된 경우 패스워드 변경처리
			if( authenticator != null )
			{
				wtcontextbean.setAuthentication( authenticator );

Kogger.debug(getClass(),  "######## PasswordChangeServlet : changePassword Service Call ########" );
				E3PSCompanyHelper.service.changePassword( userId, passwd );
			}
		}
		catch( InvocationTargetException e )
		{
			Kogger.error(getClass(), e);
		}
		catch( WTException e )
		{
			Kogger.error(getClass(), e);
		}
    }
}
