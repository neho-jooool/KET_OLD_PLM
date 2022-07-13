/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : CompanyDao.java
 * 작성자 : 오명재
 * 작성일자 : 2011. 02. 21.
 */
package e3ps.groupware.company.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import e3ps.common.util.PlmDBUtil;

/**
 * 클래스명 : CompanyDao
 * 설명 :
 * 작성자 :  오명재
 * 작성일자 : 2011. 02. 21.
 */
public class CompanyDao
{
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public CompanyDao()
	{
	}

	public String getPassword( String userId ) throws Exception
	{
		String returnStr = "";
		StringBuffer sql = new StringBuffer();

		sql.append( "SELECT p.password pwd	\n" );
		sql.append( " FROM People p				\n" );
		sql.append( "WHERE p.id=?				\n" );

		try
		{
			conn = PlmDBUtil.getConnection();

			pstmt = conn.prepareStatement( sql.toString() );
			pstmt.setString( 1, userId );

			rs = pstmt.executeQuery();

			if( rs.next() )
			{
				returnStr = rs.getString( "pwd" );
			}
		}
		catch( Exception e )
		{
			throw e;
		}
		finally
		{
        	sql.delete( 0 , sql.length() );

    		if( rs != null )
			{
				rs.close();
			}

    		if( pstmt != null )
			{
				pstmt.close();
			}

			PlmDBUtil.close( conn );
		}

		return returnStr;
	}
}
