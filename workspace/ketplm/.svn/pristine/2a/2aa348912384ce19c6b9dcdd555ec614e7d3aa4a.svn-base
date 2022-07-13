package e3ps.bom.command.bomcheckoutin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;

import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.Registry;
import ext.ket.shared.log.Kogger;

public class BOMCheckOutInDao
{
    private DBConnectionManager res = null;
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    //static private Registry daoReg = Registry.getRegistry( "nex1.dao.dao" );

    Registry registry = Registry.getRegistry("e3ps.bom.bom");
    public BOMCheckOutInDao()
    {

    }

	public boolean isItemCheckedOut( String itemcode ) throws Exception
	{
		boolean flag = false;
		StringBuffer sqlString = new StringBuffer();

		try
		{
			if( connectionDB() )
			{
	            sqlString.append( " SELECT  decode( COUNT(*), 0, 'N', 'Y' )			\n" );
	            sqlString.append( "  FROM  ketbomcheckout co					    \n" );
	            sqlString.append( " WHERE  co.itemcode = ?							\n" );

				pstmt = conn.prepareStatement( sqlString.toString() );
				pstmt.setString( 1, itemcode );

				rs = pstmt.executeQuery();

				if( rs.next() )
				{
					if( rs.getString(1).equalsIgnoreCase("Y") )
					{
						flag = true;
					}
					else
					{
						flag = false;
					}
				}
			}
		}
		catch( Exception exception )
		{
  			throw exception;
  		}
		finally
		{
			sqlString.delete( 0, sqlString.length() );
			disconnectionDB();
  		}

		return flag;
	}

	public boolean isItemCheckedOutCurrentUser( String itemcode, String user_id ) throws Exception
	{
		boolean flag = false;
		StringBuffer sqlString = new StringBuffer();

		try
		{
			if( connectionDB() )
			{
	            sqlString.append( " SELECT  decode( COUNT(*), 0, 'N', 'Y' )			\n" );
	            sqlString.append( "  FROM  ketbomcheckout co					    \n" );
	            sqlString.append( " WHERE  co.itemcode = ?							\n" );
	            sqlString.append( "    AND  co.checkouterid = ?						\n" );

				pstmt = conn.prepareStatement( sqlString.toString() );
				pstmt.setString( 1, itemcode );
				pstmt.setString( 2, user_id );

				rs = pstmt.executeQuery();

				if( rs.next() )
				{
					if( rs.getString(1).equalsIgnoreCase("Y") )
					{
						flag = true;
					}
					else
					{
						flag = false;
					}
				}
			}
		}
		catch( Exception exception )
		{
  			throw exception;
  		}
		finally
		{
			sqlString.delete( 0, sqlString.length() );
			disconnectionDB();
  		}

		return flag;
	}

	public Hashtable getCheckOuterInfo( String itemcode ) throws Exception
	{
		Hashtable returnHash = new Hashtable();
		StringBuffer sqlString = new StringBuffer();

		try
		{
			if( connectionDB() )
			{
	            sqlString.append( " SELECT	co.itemcode				itemcode		\n" );
	            sqlString.append( " 			, co.checkouterid		user_id		\n" );
	            sqlString.append( " 			, u.name					user_name	\n" );
	            sqlString.append( " 			, u.email					user_email	\n" );
	            sqlString.append( "  FROM   ketbomcheckout co					    \n" );
	            sqlString.append( "            , people u								    \n" );
	            sqlString.append( " WHERE   co.itemcode = ?							\n" );
	            sqlString.append( "    AND   co.checkouterid = u.id			        \n" );

				pstmt = conn.prepareStatement( sqlString.toString() );
				pstmt.setString( 1, itemcode );

				rs = pstmt.executeQuery();

				while( rs.next() )
				{
					returnHash.put( "itemcode", rs.getString("itemcode") );
					returnHash.put( "user_id", rs.getString("user_id") );
					returnHash.put( "user_name", rs.getString("user_name") );
					returnHash.put( "user_email", rs.getString("user_email") );
				}
			}
		}
		catch( Exception exception )
		{
  			throw exception;
  		}
		finally
		{
			sqlString.delete( 0, sqlString.length() );
			disconnectionDB();
  		}

		return returnHash;
	}

	public String getCheckOuterID( String itemcode ) throws Exception
	{
		String user_id = "";
		StringBuffer sqlString = new StringBuffer();

		try
		{
			if( connectionDB() )
			{
	            sqlString.append( " SELECT   co.checkouterid		user_id			\n" );
	            sqlString.append( "  FROM   ketbomcheckout co						\n" );
	            sqlString.append( " WHERE   co.itemcode = ?							\n" );

				pstmt = conn.prepareStatement( sqlString.toString() );
				pstmt.setString( 1, itemcode );

				rs = pstmt.executeQuery();

				if( rs.next() )
				{
					user_id = rs.getString( "user_id" );
				}
			}
		}
		catch( Exception exception )
		{
  			throw exception;
  		}
		finally
		{
			sqlString.delete( 0, sqlString.length() );
			disconnectionDB();
  		}

		return user_id;
	}


	public String getCheckOuterName( String itemcode ) throws Exception
	{
		String user_name = "";
		StringBuffer sqlString = new StringBuffer();

		try
		{
			if( connectionDB() )
			{
	            sqlString.append( " SELECT   u.name  user_name							\n" );
	            sqlString.append( "  FROM   ketbomcheckout co							\n" );
	            sqlString.append( "           , people u										\n" );
	            sqlString.append( " WHERE   co.itemcode = ?								\n" );
	            sqlString.append( "    AND   co.checkouterid = u.id						\n" );

				pstmt = conn.prepareStatement( sqlString.toString() );
				pstmt.setString( 1, itemcode );

				rs = pstmt.executeQuery();

				if( rs.next() )
				{
					user_name = rs.getString( "user_name" );
				}
			}
		}
		catch( Exception exception )
		{
  			throw exception;
  		}
		finally
		{
			sqlString.delete( 0, sqlString.length() );
			disconnectionDB();
  		}

		return user_name;
	}


	public String getCheckOuterEMail( String itemcode ) throws Exception
	{
		String user_email = "";
		StringBuffer sqlString = new StringBuffer();

		try
		{
			if( connectionDB() )
			{
	            sqlString.append( " SELECT   u.email  user_email							\n" );
	            sqlString.append( "  FROM   ketbomcheckout co							\n" );
	            sqlString.append( "           , people u										\n" );
	            sqlString.append( " WHERE   co.itemcode = ?								\n" );
	            sqlString.append( "    AND   co.checkouterid = u.id						\n" );

				pstmt = conn.prepareStatement( sqlString.toString() );
				pstmt.setString( 1, itemcode );

				rs = pstmt.executeQuery();

				if( rs.next() )
				{
					user_email = rs.getString( "user_email" );
				}
			}
		}
		catch( Exception exception )
		{
  			throw exception;
  		}
		finally
		{
			sqlString.delete( 0, sqlString.length() );
			disconnectionDB();
  		}

		return user_email;
	}

	public boolean setCheckOut( String itemcode, String itemversion, String user_id ) throws Exception
	{
		boolean flag = false;
		StringBuffer sqlString = new StringBuffer();

		Kogger.debug(getClass(), "######### itemcode : "+itemcode);
		Kogger.debug(getClass(), "######### user_id : "+user_id);
		try
		{
			if( connectionDB() )
			{
	            sqlString.append( " INSERT INTO																			\n" );
	            sqlString.append( " ketbomcheckout ( itemcode,  itemversion, checkouterid )					\n" );
	            sqlString.append( " VALUES( ?, ? ,?) 																		\n" );

				pstmt = conn.prepareStatement( sqlString.toString() );
				pstmt.setString( 1, itemcode );
				pstmt.setString( 2, itemversion );
				pstmt.setString( 3, user_id );

				int result = pstmt.executeUpdate();

				if( result == 1 )
				{
					flag = true;
					conn.commit();
				}
				else
				{
					flag = false;
					conn.rollback();
				}
			}
		}
		catch( Exception exception )
		{
			conn.rollback();
  			throw exception;
  		}
		finally
		{
			sqlString.delete( 0, sqlString.length() );
			disconnectionDB();
  		}

		return flag;
	}

	public boolean setCheckIn( String itemcode, String user_id ) throws Exception
	{
		boolean flag = false;
		StringBuffer sqlString = new StringBuffer();

		try
		{
			if( connectionDB() )
			{
	            sqlString.append( " DELETE													\n" );
	            sqlString.append( "  FROM    ketbomcheckout co							\n" );
	            sqlString.append( " WHERE   co.itemcode = ?								\n" );
	            sqlString.append( "    AND   co.checkouterid = ?							\n" );

				pstmt = conn.prepareStatement( sqlString.toString() );
				pstmt.setString( 1, itemcode );
				pstmt.setString( 2, user_id );

				int result = pstmt.executeUpdate();

				// 동일한 itemcode 를 체크아웃 하고있는 경우 모두 삭제해줘야 함
				if ( result > 0 )
				{
					flag = true;
					conn.commit();
				}
				else
				{
					flag = false;
					conn.rollback();
				}

//				if( result == 1 )
//				{
//					flag = true;
//					conn.commit();
//				}
//				else
//				{
//					flag = false;
//					conn.rollback();
//				}
			}
		}
		catch( Exception exception )
		{
			conn.rollback();
  			throw exception;
  		}
		finally
		{
			sqlString.delete( 0, sqlString.length() );
			disconnectionDB();
  		}

		return flag;
	}

	public boolean setCancelCheckIn( String itemcode, String user_id ) throws Exception
	{
		boolean flag = false;
		StringBuffer sqlString = new StringBuffer();

		try
		{
			if( connectionDB() )
			{
	            sqlString.append( " DELETE													\n" );
	            sqlString.append( "  FROM	ketbomcheckout co							\n" );
	            sqlString.append( " WHERE 	co.itemcode = ?								\n" );
	            sqlString.append( "    AND   co.checkouterid = ?							\n" );

				pstmt = conn.prepareStatement( sqlString.toString() );
				pstmt.setString( 1, itemcode );
				pstmt.setString( 2, user_id );

				int result = pstmt.executeUpdate();

				if( result == 1 )
				{
					flag = true;
					conn.commit();
				}
				else
				{
					flag = false;
					conn.rollback();
				}
			}
		}
		catch( Exception exception )
		{
			conn.rollback();
  			throw exception;
  		}
		finally
		{
			sqlString.delete( 0, sqlString.length() );
			disconnectionDB();
  		}

		return flag;
	}

	public boolean connectionDB()
	{
		boolean flag = false;

		try
		{
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			conn.setAutoCommit( false );

			flag = true;
		}
		catch( Exception e )
		{
			Kogger.error(getClass(), e);
		}

		return flag;
	}

	private void disconnectionDB()
	{
		try
		{
			if( pstmt != null )
			{
				pstmt.close();
			}

			if( rs != null )
			{
				rs.close();
			}
		}
		catch( Exception e )
		{
			Kogger.error(getClass(), e);
		}
		finally
		{
			if( res != null )
			{
				try
				{
					res.freeConnection( registry.getString("plm"), conn );
				}
				catch( Exception ex )
				{
					Kogger.error(getClass(), ex);
				}
			}
		}
	}



	   public String workingStatusUpdate(boolean flag)
	    {
	        String status = "";
	        String SQL = "";
	        try
	        {
	        	if( connectionDB() )
				{

						if(flag)
						{
							SQL =  "UPDATE ketbomcoworker																					";
							SQL += "SET endWorkingFlag = '2'																					";
							SQL += "WHERE newbomcode = '" + Utility.checkNVL(BOMBasicInfoPool.getPublicModelName()) + "'	";
							SQL += "AND coworkerid = '" + Utility.checkNVL(BOMBasicInfoPool.getUserId()) + "'						";
						}
						else
						{
							SQL =  "UPDATE ketbomecocoworker																					";
							SQL += "SET endWorkingFlag = '2'																						";
							SQL += "WHERE ecoheadernumber = '" + Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()) + "'	";
							SQL += "AND coworkerid = '" + Utility.checkNVL(BOMBasicInfoPool.getUserId()) + "'							";
						}
						pstmt = conn.prepareStatement( SQL );
						pstmt.executeUpdate();

				conn.commit();
				}
	        }
	        catch(Exception e)
	        {
				conn.rollback();
	            Kogger.error(getClass(), e);
	        }
	        finally
	        {
	            try
	            {
	            	res.freeConnection( registry.getString("plm"), conn );
				}
	            catch(Exception e){}
	            return status;
	        }
	    }
}
