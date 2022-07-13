package e3ps.groupware.company.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import e3ps.groupware.company.E3PSCompanyHelper;
import e3ps.groupware.company.People;
import ext.ket.shared.log.Kogger;

public class SyncUserPassword
{
	public static void main(String[] args) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			RemoteMethodServer methodServer = RemoteMethodServer.getDefault();

			if( methodServer.getUserName() == null )
			{
				String [] userInfo = {"wcadmin", "wcadmin"};

				if( args.length >= 2 )
				{
					userInfo[0] = args[0];
					userInfo[1] = args[1];
				}

				methodServer.setUserName( userInfo[0] );
				methodServer.setPassword( userInfo[1] );

				Kogger.debug(SyncUserPassword.class, "AUTHENTICATION SUCCESS");
			}
			else
			{
				Kogger.debug(SyncUserPassword.class, "AUTHENTICATION FAILE");
			}

			StringBuffer sqlString = new StringBuffer();
			int rowCount = 0;
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			java.util.Properties prop = new java.util.Properties();
			prop.put("user", "dpuser");
			prop.put("password", "dpuser12!@");

			conn = DriverManager.getConnection( "jdbc:odbc:KET_EP", prop );

			sqlString.append( " SELECT u.LoginID								UserID			\n" );
			sqlString.append( "	            , u.passWd							Password		\n" );
			sqlString.append( "    FROM xclick_ket.dbo.v_DP_User_All_plm u			\n" );
			sqlString.append( "            , xclick_ket.dbo.v_DP_Dept d				\n" );
			sqlString.append( "            , xclick_ket.dbo.v_DP_Dept pd				\n" );
			sqlString.append( " WHERE u.LoginID = ?											\n" );
			sqlString.append( "      AND u.PrimaryYN = 'Y'										\n" );
			//sqlString.append( "      AND u.Prop_Gubn = '0'										\n" );
			sqlString.append( "      AND u.DeptCode = d.DeptCode							\n" );
			sqlString.append( "      AND d.ParentDept = pd.DeptCode						\n" );

			pstmt = conn.prepareStatement( sqlString.toString() );

			// All User Search
			QuerySpec qs = new QuerySpec();

			int ii = qs.addClassList(People.class, true);
			int jj = qs.addClassList(WTUser.class, true);

			qs.appendWhere( new SearchCondition(People.class, "userReference.key.id", WTUser.class, "thePersistInfo.theObjectIdentifier.id"), new int[] { ii, jj } );
			QueryResult qr = PersistenceHelper.manager.find(qs);

			Object[] obj = null;
			WTUser wtuser = null;
			String userId = "";
			String passWd = "";

			while( qr.hasMoreElements() )
			{
				rowCount++;

				obj = (Object[]) qr.nextElement();
				wtuser = (WTUser) obj[1];

				if( wtuser.getName() != "Administrator" && wtuser.getName() != "wcadmin" )
				{
					pstmt.clearParameters();
					pstmt.setString( 1, wtuser.getName() );
					rs = pstmt.executeQuery();

					if ( rs.next() )
					{
						userId = getString( rs.getString("UserID") );
						passWd = getString( rs.getString("Password") );

						E3PSCompanyHelper.service.changePassword( userId, passWd );
//						E3PSCompanyHelper.service.changePassword( userId, "1" );
					}
				}
			}
			Kogger.debug(SyncUserPassword.class, "패스워드 동기화 >> " + rowCount + " 건");
		}
		catch( Exception e )
		{
			Kogger.error(SyncUserPassword.class, e);
		}
		finally
		{
			try
			{
				if( rs != null )
				{
					rs.close();
				}

				if( pstmt != null )
				{
					pstmt.close();
				}

				if( conn != null )
				{
					conn.close();
				}
			}
			catch( Exception e )
			{
				throw new WTException(e);
			}
		}
	}

	static public String getString( String str )
	{
		if( str == null )
		{
			return "";
		}
		else if( str.equals("null") )
		{
			return "";
		}
		else
		{
			return str;
		}
	}
}
