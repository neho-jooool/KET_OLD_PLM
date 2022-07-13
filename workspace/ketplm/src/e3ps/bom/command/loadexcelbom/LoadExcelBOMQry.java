package e3ps.bom.command.loadexcelbom;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import ext.ket.shared.log.Kogger;

public class LoadExcelBOMQry
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

    public LoadExcelBOMQry()
    {
    }

 // 부품번호의 데이타
    public Vector getBomItemData(Vector vc)  
	{
    	
    	String itemCode = "";
    	String itemNm = "";
    	String quantity = "";
    	
    	String dienoStr = "";
    	String meterStr = "";
    	String hardnessFromStr = "";
    	String hardnessToStr = "";
    	String desionDateStr = "";
    	
    	Hashtable ht = null;
    	Vector newbom = new Vector();
    	
        Vector extListVec = new Vector();
        Vector nwListVec = new Vector();
        Vector resultListVec = new Vector();
        StringBuffer strSql = new StringBuffer();
        extListVec.removeAllElements();
        nwListVec.removeAllElements();
        resultListVec.removeAllElements();

        try
        {
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));

			stmt = conn.createStatement();

			int ext = 1;
			int nw = 1;
			for(int i=0;i<vc.size();i++)
			{
				String itemNmStr = "";
				itemCode = (String)((Hashtable)vc.elementAt(i)).get("CHILDITEMCODE");
				itemNm = (String)((Hashtable)vc.elementAt(i)).get("DESC");
				quantity = (String)((Hashtable)vc.elementAt(i)).get("QUANTITY");
			
				 dienoStr = (String)((Hashtable)vc.elementAt(i)).get("PARENTITEMCODE");
		    	 meterStr = (String)((Hashtable)vc.elementAt(i)).get("MATERIAL");
		    	 hardnessFromStr = (String)((Hashtable)vc.elementAt(i)).get("HARDNESSFROM");
		    	 hardnessToStr = (String)((Hashtable)vc.elementAt(i)).get("HARDNESSTO");
		    	 desionDateStr = (String)((Hashtable)vc.elementAt(i)).get("DESIGNDATE");
		    	
			strSql.append(" SELECT																				\n")
			.append("		m.name itemNm																		\n")
			.append(" ,	i.versionida2versioninfo || '.' || i.iterationida2iterationinfo version			\n")
			.append(" ,   m.defaultunit unit                                                   				\n") 
			.append(" ,   i.statestate status	                                                   				\n") 
			.append("  FROM wtpart i, wtpartmaster m														\n")
			.append("	WHERE m.wtpartnumber = '" + itemCode + "'										\n")
			.append("	AND m.ida2a2 = i.ida3masterreference												\n")
			.append("	AND i.latestiterationinfo = '1'															\n")
			.append("	ORDER BY i.versionida2versioninfo, i.iterationida2iterationinfo DESC			\n");
//Kogger.debug(getClass(), ">>>>> sql : "+strSql.toString());
            rs = stmt.executeQuery(strSql.toString());
            strSql.delete(0, strSql.length());
            
	            while(rs.next()) 
				{
	            	itemNmStr = rs.getString("itemNm");
	            	extListVec.addElement(new LoadExcelBOMData(
	            			String.valueOf(ext)
	            			,itemCode
	            			,Utility.checkNVL(itemNmStr) 
	            			,Utility.checkNVL(rs.getString("version")) 
	            			,quantity
	            			,Utility.checkNVL(rs.getString("unit")) 
	            			,Utility.checkNVL(rs.getString("status")) 
	            			, dienoStr 
	            	    	, meterStr 
	            	    	, hardnessFromStr 
	            	    	, hardnessToStr 
	            	    	, desionDateStr 
	            		));
	            	ext++;
	            }
	            if(itemNmStr.equals(""))
	            {
	            	nwListVec.addElement(new LoadExcelBOMData(
	            			String.valueOf(nw)
	            			,itemCode
	            			,itemNm 
	            			,""
	            			,quantity
	            			,""
	            			,""
	            			, dienoStr 
	            	    	, meterStr 
	            	    	, hardnessFromStr 
	            	    	, hardnessToStr 
	            	    	, desionDateStr 
	            		));
	            	
	            	ht = new Hashtable();
	            	ht.put("dieno", dienoStr);
	            	ht.put("number", itemCode);
	            	ht.put("name", itemNm);
	            	ht.put("unit", "EA");
	            	ht.put("partType", "DIEM");
	            	ht.put("quantity", quantity);
	            	ht.put("material", meterStr);
	            	ht.put("hardnessFrom", hardnessFromStr);
	            	ht.put("hardnessTo", hardnessToStr);
	            	ht.put("designDate", desionDateStr);
	            	newbom.add(ht);
	            	
	            	nw++;
	            }
			}
			resultListVec.add(extListVec);
			resultListVec.add(nwListVec);
			resultListVec.add(newbom);

        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
		finally
		{
			try
			{
				if(rs != null)
					rs.close();
				stmt.close();
			}
			catch(Exception e)
			{
				MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
				mbox.setModal(true);
				mbox.setVisible(true);
			}
			finally
			{
				if(res != null)
				{
					res.freeConnection(registry.getString("plm"), conn);
				}
			}
		}
        return resultListVec;
    }
    
}
