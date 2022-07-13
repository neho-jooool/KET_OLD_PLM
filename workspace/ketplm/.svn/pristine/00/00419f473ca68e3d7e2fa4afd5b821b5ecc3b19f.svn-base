package e3ps.wfm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.Registry;
import ext.ket.shared.log.Kogger;

public class WFMSearchUtilDao
{
	public String getDelegator( String ownerID ) throws Exception
	{
		String delegatorID = "";

		Registry registry = null;
        DBConnectionManager resource = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        StringBuffer sqlBuffer = new StringBuffer();

        sqlBuffer.append( " SELECT c.ida3a2ownership ownerOID																				\n" );
        sqlBuffer.append( "            , ou.name ownerID																								\n" );
        sqlBuffer.append( "            , cc.ida3a4 delegatorOID																						\n" );
        sqlBuffer.append( "            , du.name delegatorID																							\n" );
        sqlBuffer.append( "            , TO_char( cc.start_date, 'YYYY-MM-DD' ) startDate												\n" );
        sqlBuffer.append( "            , TO_char( cc.end_date, 'YYYY-MM-DD' ) endDate												\n" );
        sqlBuffer.append( "            , cc.workingday workingDay																					\n" );
        sqlBuffer.append( "   FROM  WTCalendar c																									\n" );
        sqlBuffer.append( "            , CalendarComponent cc																						\n" );
        sqlBuffer.append( "            , ComponentLink cl																								\n" );
        sqlBuffer.append( "            , WTUser ou																										\n" );
        sqlBuffer.append( "            , WTUser du																										\n" );
        sqlBuffer.append( " WHERE ou.name = ?																										\n" );
        sqlBuffer.append( "      AND c.ida2a2 = cl.ida3b5																							\n" );
        sqlBuffer.append( "      AND cl.ida3a5 = cc.ida2a2																						\n" );
        sqlBuffer.append( "      AND c.ida3a2ownership = ou.ida2a2																			\n" );
        sqlBuffer.append( "      AND cc.ida3a4 = du.ida2a2																						\n" );
		sqlBuffer.append( "      AND TO_char( sysdate, 'YYYY-MM-DD' ) >= TO_char( cc.start_date, 'YYYY-MM-DD' )	\n" );
		sqlBuffer.append( "      AND TO_char( sysdate, 'YYYY-MM-DD' ) <= TO_char( cc.end_date, 'YYYY-MM-DD' )	\n" );

		try
		{
			registry = Registry.getRegistry("e3ps.bom.bom");
	        resource = DBConnectionManager.getInstance();
	        connection = resource.getConnection(registry.getString("plm"));

			pstmt = connection.prepareStatement( sqlBuffer.toString() );
			pstmt.setString( 1, ownerID );
			rs = pstmt.executeQuery();

			while( rs.next() )
			{
				delegatorID = rs.getString( "delegatorID" );
			}
		}
		catch( Exception e )
		{
			throw e;
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
			}
			catch (SQLException e)
			{
				Kogger.error(getClass(), e);
			}

			if( resource != null )
			{
				resource.freeConnection(registry.getString("plm"), connection);
			}
		}

		return delegatorID;
	}
}
