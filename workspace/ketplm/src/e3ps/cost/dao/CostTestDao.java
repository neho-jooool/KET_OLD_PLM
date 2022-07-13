package e3ps.cost.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import e3ps.cost.util.StringUtil;

public class CostTestDao {
    public void Test() throws ClassNotFoundException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            StringBuffer sqlString = new StringBuffer();
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

            java.util.Properties prop = new java.util.Properties();
            prop.put("user", "ghost_ket");
            prop.put("password", "rhtmxm123!@#");

            conn = DriverManager.getConnection( "jdbc:odbc:KET_COST", prop );

            sqlString.append("select A.Email,A.Name from ghost_ket.CDUser A, ghost_ket.auth_cost B where A.GroupCode = '11702' and A.account = B.empno and B.group_m = 'G'         \n              ");
            //sqlString.append(" select empno from ghost_ket.auth_cost where group_m = 'G' ");

            //sqlString.append(" select A.Email,B.Name from ghost_ket.CDUser A,ghost_ket.CDGroup B where A.GroupCode = B.GroupCode AND A.GroupCode = '11702' and B.Name = '제품개발1팀'");

            pstmt = conn.prepareStatement( sqlString.toString() );
            //pstmt.setString( 1, userId );
            rs = pstmt.executeQuery();
            System.out.println(sqlString.toString());
            //System.out.println(pstmt.executeQuery());
            System.out.println(rs);
            while(rs.next()){
                System.out.println(rs.getString("Email") );
                System.out.println(rs.getString("Name") );
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
    }
}
