package e3ps.bom.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.Registry;
import ext.ket.shared.log.Kogger;

public class SearchAppliedProductDao {

	Registry registry;
	DBConnectionManager res;
	Statement stmt;
	Connection conn;

	BOMSearchUtilDao searchUtil = new BOMSearchUtilDao();

	public SearchAppliedProductDao() {}

	public Vector searchAppliedProduct(String itemCode, String itemDesc, String orgCode) {
		Vector returnVec = new Vector();

		Vector productVec = new Vector();												// Applied Product
		Vector parentItemVec = new Vector();											// parent Item
		int roopIndex = 0;

		Vector itemVec = searchParentItem(itemCode, itemDesc, orgCode);
		Vector parentItemTemp = itemVec;

		while (true) {
			Vector itemVecTemp = new Vector();
			Vector parentItemTempTemp = new Vector();

			for (int i=0; i<itemVec.size(); i++) {
				Hashtable parentItemHash = (Hashtable)itemVec.elementAt(i);
				String itemCodeTemp = Utility.checkNVL(parentItemHash.get("itemCode"));
				String itemDescTemp = Utility.checkNVL(parentItemHash.get("description"));
				String itemTypeTemp = Utility.checkNVL(parentItemHash.get("itemType"));
				String itemOidTemp = Utility.checkNVL(parentItemHash.get("oid"));

				if (!itemTypeTemp.equals("3")) {
					if (!isDuplicateItem(productVec, itemOidTemp)) {
						productVec.addElement(parentItemHash);
						parentItemVec.addElement(parentItemTemp.elementAt(i));
					}

				} else {
					Vector parentItemVecTemp = searchParentItem(itemCodeTemp, itemDescTemp, orgCode);
					for (int j=0; j<parentItemVecTemp.size(); j++) {
						itemVecTemp.addElement(parentItemVecTemp.elementAt(j));
						parentItemTempTemp.addElement(parentItemTemp.elementAt(i));
					}
				}
			}

			roopIndex++;
			if (itemVecTemp.size() == 0) break;

			itemVec = itemVecTemp;
			parentItemTemp = parentItemTempTemp;
		}

		returnVec.addElement(productVec);
		returnVec.addElement(parentItemVec);

		return returnVec;
	}

	public Vector searchParentItem(String itemCode, String itemDesc, String orgCode) {
		Vector returnVec = new Vector();

		String sql = " SELECT   c.parentitemcode pItem, a.name descr, a.defaultunit uit, e.statestate status, ph.name statusKr 	\n";
				sql += "	        , e.versionida2versioninfo || '.' || e.iterationida2iterationinfo ver,  e.ida2a2 oid 						\n";
				sql += "	FROM wtpart e, wtpartmaster a, wtpart b, KETPartUsageLink c, wtpartmaster d 								\n";
				sql += "	       , phasetemplate ph, phaselink pl 																				\n";
				sql += "		WHERE a.ida2a2 = b.ida3masterreference 																		\n";
				sql += "		AND b.ida2a2 = c.ida3a5 																							\n";
				sql += "		AND c.ida3b5 = d.ida2a2 																							\n";
				sql += "		AND c.childitemcode like '"+  itemCode + "' 																	\n";
				sql += "    	AND e.ida2a2 = b.ida2a2 																							\n";
				sql += "    	and pl.ida3a5 = e.ida3a2state 																					\n";
				sql += "    	and pl.ida3b5 = ph.ida2a2 																						\n";
				sql += "    	and ph.phasestate = e.statestate 																				\n";
				//shin.....New, OLd ???
				//sql += "    and e.BOMALLOWED = 'Y' \n";
				sql += "	AND e.ida2a2 = (SELECT max(bb.ida2a2) 																			\n";
				sql += "                        FROM wtpartmaster aa, wtpart bb 															\n";
				sql += "                       WHERE aa.ida2a2 = bb.ida3masterreference 													\n";
				sql += "                          AND (bb.statecheckoutinfo = 'wrk' or bb.statecheckoutinfo = 'c/i') 					\n";
				sql += "                          AND bb.latestiterationinfo = 1 																\n"; 
				sql += "                          AND aa.ida2a2 = a.ida2a2 ) 																	\n";
Kogger.debug(getClass(), ">>> SQL [searchParentItem] : " + sql);				
		try 
		{
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) 
			{
				Hashtable parentItem = new Hashtable();
				parentItem.put("itemCode", rs.getString("pItem"));
				parentItem.put("description", rs.getString("descr"));
				parentItem.put("version", rs.getString("ver"));
				parentItem.put("uit", rs.getString("uit"));
				parentItem.put("status", rs.getString("status"));
				parentItem.put("statusKr", rs.getString("statusKr"));
				
				parentItem.put("quantity", new Double(1.00000));
				parentItem.put("startDate", "");
				parentItem.put("endDate", "");
				
				//shin....
				parentItem.put("ecoNo", "");
				//parentItem.put("ecoNo", rs.getString("econo") == null ? "" : rs.getString("econo").toString().trim());
				parentItem.put("oid", rs.getString("oid"));

				returnVec.addElement(parentItem);
			}

		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmt != null) stmt.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (SQLException sqe) {
				Kogger.error(getClass(), sqe);
			}
		}
		return returnVec;
	}

	private boolean isDuplicateItem(Vector productVec, String oid) {
		boolean duplicate = false;

		for (int i=0; i<productVec.size(); i++) {
			Hashtable productItem = (Hashtable)productVec.elementAt(i);
			String productItemOid = Utility.checkNVL(productItem.get("oid"));

			if (productItemOid.equals(oid)) {
				duplicate = true;
				break;
			}
		}

		return duplicate;
	}
}
