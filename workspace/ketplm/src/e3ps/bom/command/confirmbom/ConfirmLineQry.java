package e3ps.bom.command.confirmbom;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import ext.ket.shared.log.Kogger;

public class ConfirmLineQry
{
    DBConnectionManager res = null;
    Connection conn = null;
    Statement stmt = null;
    Statement stmtComponent = null;
    Statement stmtDesignator = null;
    Statement stmtSubstitute = null;
	Statement stmtItemComponent = null;
	Statement stmtItemSubstitute = null;

    ResultSet rs = null;
	ResultSet rsComponent = null;
	ResultSet rsDesignator = null;
	ResultSet rsSubstitute = null;
	ResultSet rsItemComponent = null;
	ResultSet rsItemSubstitute = null;

	Registry registry = Registry.getRegistry("e3ps.bom.bom");
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");

    public ConfirmLineQry()
    {

    }

 // Coworker 의 이름(ID) 를 가져온다.
    public Vector getCoworkerData(String name)
	{
        String SQL = "";
        Vector coworkerVec = new Vector();
        coworkerVec.removeAllElements();
        String compair = "";

        if( name.indexOf("*") > -1 )
        {
        	name = name.replace("*", "%");
        	compair = " like ";
        }
        else
        {
        	compair = " = ";
        }

        try
        {
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));

			stmt = conn.createStatement();

			SQL  = " select  								\n";
			SQL += "  t.name as dept 					\n";
			SQL += " ,p.duty as duty 					\n";
			SQL += " ,p.id as userId  					\n";
			SQL += " ,p.name as userName    		\n";
			SQL += " from department t, people p   \n";
			SQL += " where p.ida3c4 = t.ida2a2(+)  	\n";

			if( !name.trim().equals("") )
			{
				SQL += "	AND p.name "+compair+" '" + name.trim() + "'				";
			}
//Kogger.debug(getClass(), ">>>>> sql : "+SQL);
            rs = stmt.executeQuery(SQL);

            while( rs.next() )
			{
            	//구분엔 일단 임의로 A를 넣어놓았음.
            	coworkerVec.addElement(new ConfirmBomData("A"
					,Utility.checkNVL(rs.getString("dept"))
					,Utility.checkNVL(rs.getString("duty"))
					,Utility.checkNVL(rs.getString("userId"))
					,Utility.checkNVL(rs.getString("userName"))
					,""
					,""
            	));
			}
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
		finally
		{
			try
			{
				if( rs != null )
				{
					rs.close();
				}

				if( stmt != null )
				{
					stmt.close();
				}
			}
			catch(Exception e)
			{
				MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
				mbox.setModal(true);
				mbox.setVisible(true);
			}
			finally
			{
				if( res != null )
				{
					res.freeConnection(registry.getString("plm"), conn);
				}
			}
		}

        return coworkerVec;
    }

    public String getDeptData(String name, String email)
	{
        String SQL = "";
        String deptStr= "";

        try
        {
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			stmt = conn.createStatement();

			SQL  = " select  								\n";
			SQL += "  t.name as dept 					\n";
			SQL += " ,p.duty as duty 					\n";
			SQL += " ,p.id as userId  					\n";
			SQL += " ,p.name as userName    		\n";
			SQL += " from department t, people p   \n";
			SQL += " where p.ida3c4 = t.ida2a2(+)  	\n";
			SQL += "	AND p.name = '" + name.trim() + "'				";
			SQL += "	AND p.email =  '" + email.trim() + "'				";

//Kogger.debug(getClass(), ">>>>> sql : "+SQL);
            rs = stmt.executeQuery(SQL);

            if( rs.next() )
			{
            	deptStr = Utility.checkNVL(rs.getString("dept"));
            }
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
		finally
		{
			try
			{
				if( rs != null )
				{
					rs.close();
				}

				if( stmt != null )
				{
					stmt.close();
				}
			}
			catch(Exception e)
			{
				MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
				mbox.setModal(true);
				mbox.setVisible(true);
			}
			finally
			{
				if( res != null )
				{
					res.freeConnection(registry.getString("plm"), conn);
				}
			}
		}

        return deptStr;
    }
}
