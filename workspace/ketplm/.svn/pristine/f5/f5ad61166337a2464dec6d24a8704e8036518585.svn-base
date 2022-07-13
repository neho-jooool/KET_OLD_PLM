package e3ps.bom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import wt.part.WTPart;
import e3ps.bom.common.ItemTableData;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.component.BOMSubAssyComponent;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETBomHelper;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.util.StringUtil;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class BOMSearchDao {
    Registry registry = Registry.getRegistry("e3ps.bom.bom");

    Statement stmt = null;
    Statement stmt1 = null;

    BOMAssyComponent bomComponent = null;
    Vector resultList = new Vector();
    Vector resultList2 = new Vector();
    int dataCount = 0;
    String resultListStr = "";

    Hashtable supplyTypeHash;
    Hashtable reasonProdHash;
    Hashtable reasonMoldHash;
    Hashtable reasonDetailHash;

    private String basicModel = "";
    private String basicSeq = "";

    public BOMSearchDao() {
    }

    public void supplyTypeCodeQuery(Connection connection) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer strSql = new StringBuffer();

	strSql.append(" SELECT														\n").append("		lookup_code code									\n").append(" ,	meaning value										\n").append("  FROM apps.fnd_lookup_values						\n")
	        .append("	WHERE lookup_type = 'WIP_SUPPLY'					\n").append("	ORDER BY code ASC									\n");

	try {
	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery(strSql.toString());

	    supplyTypeHash = new Hashtable();
	    while (rs.next()) {
		supplyTypeHash.put(rs.getString("code"), rs.getString("value"));
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    strSql.delete(0, strSql.length());
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void reasonCodeQuery(Connection connection) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// 제품 변경사유
	StringBuffer strSql = new StringBuffer();
	strSql.append(" SELECT												\n").append("		n.code code									\n").append(" ,	n.name  value								\n").append("  FROM numbercode n							\n")
	        .append("	WHERE n.codetype = 'PRODECOREASON'	\n").append("	AND disabled = 0								\n");

	// 금형 변경사유
	StringBuffer strSql2 = new StringBuffer();
	strSql2.append(" SELECT											\n").append("		n.code code									\n").append(" ,	n.name  value								\n").append("  FROM numbercode n							\n")
	        .append("	WHERE n.codetype = 'CHANGEREASON'	\n").append("	AND disabled = 0								\n");

	try {
	    // 제품 변경사유
	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery(strSql.toString());

	    reasonProdHash = new Hashtable();
	    while (rs.next()) {
		reasonProdHash.put(rs.getString("code"), rs.getString("value"));
	    }

	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }

	    // 금형 변경사유
	    pstmt = connection.prepareStatement(strSql2.toString());
	    rs = pstmt.executeQuery(strSql2.toString());

	    reasonMoldHash = new Hashtable();
	    while (rs.next()) {
		reasonMoldHash.put(rs.getString("code"), rs.getString("value"));
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    strSql.delete(0, strSql.length());
	    strSql2.delete(0, strSql2.length());
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void reasonDetailCodeQuery(Connection connection) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer strSql = new StringBuffer();

	strSql.append(" SELECT								\n").append("		code code					\n").append(" ,	description value			\n").append("  FROM lsisecmcode			\n").append("	WHERE category = '02'			\n")
	        .append("	AND isvalid = 1					\n");

	try {
	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery(strSql.toString());

	    reasonDetailHash = new Hashtable();
	    while (rs.next()) {
		reasonDetailHash.put(rs.getString("code"), rs.getString("value"));
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    strSql.delete(0, strSql.length());
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void existItemSearch(Connection connection, String childItemCode) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer strSql = new StringBuffer();
	String itemCodeStr = "";

	itemCodeStr = childItemCode;

	dataCount = 0;

	strSql.append(" SELECT																				\n").append("		m.wtpartnumber itemCode												\n").append(" ,	m.name descr																\n")
	        .append(" ,	i.versionida2versioninfo ver												\n").append(" ,	i.iterationida2iterationinfo iter												\n").append(" ,	i.statestate status															\n")
	        .append(" ,	p.name  statuskr															\n").append(" ,	m.defaultunit unit															\n").append(" ,	to_char(sysdate, 'YYYY-MM-DD') startDate								\n")
	        .append("  FROM wtpart i, wtpartmaster m	, phasetemplate p, phaselink pl		\n").append("	WHERE m.wtpartnumber = ?													\n")
	        .append("	AND m.ida2a2 = i.ida3masterreference										\n").append("	and p.phasestate = i.statestate												\n")
	        .append(" and pl.ida3a5 = i.ida3a2state													\n")
	        .append(" and pl.ida3b5 = p.ida2a2														\n")

	        // shin....없는칼럼...
	        // .append("	AND i.bomallowed = 'Y'													\n")
	        .append("	AND i.ida2a2 = (SELECT max(ia.ida2a2) FROM wtpart ia WHERE ia.ida3masterreference = m.ida2a2 and ia.latestiterationinfo = '1') \n")
	        .append("	ORDER BY i.versionida2versioninfo, i.iterationida2iterationinfo DESC	\n");

	try {
	    pstmt = connection.prepareStatement(strSql.toString());
	    pstmt.setString(1, itemCodeStr);

	    rs = pstmt.executeQuery();

	    if (rs.next()) {

		dataCount++;
		// shin.....
		bomComponent = new BOMAssyComponent(childItemCode);
		bomComponent.setDescStr(rs.getString("descr"));
		bomComponent.setVersionStr(rs.getString("ver") + "." + rs.getString("iter"));
		bomComponent.setStartDate(rs.getString("startDate"));
		bomComponent.setStatusStr(rs.getString("status"));

		bomComponent.setStatusKrStr(rs.getString("statuskr"));

		bomComponent.setUitStr(rs.getString("unit"));
		bomComponent.setLevelInt(new Integer(0));
		Double qtyDbl = new Double(1.0);
		bomComponent.setQuantityDbl(qtyDbl);
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    strSql.delete(0, strSql.length());
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    // shin... 신규부품인지 ERP등록된(OLD) 건지. 체크하는곳??
    public void newItemSearch(Connection connection, String childItemCode) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer strSql = new StringBuffer();
	String itemCodeStr = "";

	// shin...
	itemCodeStr = childItemCode;

	dataCount = 0;

	// shin... 부품 NEW,OLD 구분 로직 변경...
	KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();
	String strType = "";
	try {
	    WTPart part = kh.searchItem(itemCodeStr);
	    strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpBOMFlag);
	    if (strType.equals("NEW")) {
		dataCount++;
		resultList.addElement(itemCodeStr);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	/*
	 * strSql.append(" SELECT																							\n") .append("		m.wtpartnumber itemCode															\n") //.append(" ,	i.bomflag newFlag																		\n")
	 * .append("  FROM wtpart i, wtpartmaster m															\n")
	 * 
	 * //shin.... //.append("	WHERE i.orgcode = ?																	\n") //.append("	AND m.wtpartnumber = ?																\n") .append("	WHERE m.wtpartnumber = ?																\n")
	 * 
	 * //shin....없는칼럼... //.append("	AND (i.bomflag = 'N' OR i.bomflag IS NULL)										\n") //.append("	AND i.bomallowed = 'Y'																\n")
	 * .append("	AND i.ida2a2 = (SELECT max(ia.ida2a2) FROM wtpart ia WHERE ia.ida3masterreference = m.ida2a2 and ia.latestiterationinfo = '1') \n")
	 * .append("	ORDER BY i.versionida2versioninfo, i.iterationida2iterationinfo DESC				\n");
	 * 
	 * try { resultList.removeAllElements();
	 * 
	 * pstmt = connection.prepareStatement(strSql.toString()); pstmt.setString(1, itemCodeStr);
	 * 
	 * rs = pstmt.executeQuery(); if(rs.next()) //@@@ 여길 막아서 OLD로 테스트 해볼 수 있음. { dataCount++; //resultList.addElement((rs.getString("itemCode")==null ? "" : rs.getString("itemCode").trim()) + "+"
	 * + rs.getString("newFlag")); resultList.addElement((rs.getString("itemCode")==null ? "" : rs.getString("itemCode").trim()) ); } } catch(Exception ex) { Kogger.error(getClass(), ex); throw
	 * ex; } finally { strSql.delete(0, strSql.length()); try{rs.close();} catch(Exception e){} try{pstmt.close();} catch(Exception e){} }
	 */

    }

    public void isBomChildExist(Connection connection, String itemCode) throws Exception {
	PreparedStatement pstmt = null;
	PreparedStatement pstmt1 = null;
	ResultSet rs = null;
	ResultSet rs1 = null;

	String query = "";
	String query1 = "";
	Vector itemId = new Vector();
	String itemMaster = "";

	try {
	    dataCount = 0;

	    query = " select i.ida2a2 ida from wtpart i, wtpartmaster m		\n" + "where m.ida2a2 = i.ida3masterreference						\n" + "	and m.wtpartnumber = ?											\n";

	    pstmt = connection.prepareStatement(query);
	    pstmt.setString(1, itemCode.trim());

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		itemId.addElement(rs.getString("ida") == null ? "" : rs.getString("ida").trim());
	    }

	    if (itemId.size() > 0) {
		itemMaster = "'" + itemId.elementAt(0);
		for (int i = 1; i < itemId.size(); i++) {
		    itemMaster = itemMaster + "', '" + itemId.elementAt(i);
		}
		itemMaster = itemMaster + "'";

		query1 = " SELECT												\n" + "		COUNT(*)											\n" + " FROM KETPartUsageLink							\n" + " WHERE ida3a5 IN (" + itemMaster + ")			\n";

		pstmt1 = connection.prepareStatement(query1);
		rs1 = pstmt1.executeQuery();

		if (rs1.next()) {
		    dataCount = rs1.getInt(1);
		}
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		rs1.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt1.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void searchDownwardList(Connection connection, String item, String desc) throws Exception {
	String compair = "";
	String compair2 = "";
	if (item.indexOf("*") > -1) {
	    item = item.replace("*", "%");
	    compair = " like ";
	} else {
	    compair = " = ";
	}

	if (desc.indexOf("*") > -1) {
	    desc = desc.replace("*", "%");
	    compair2 = " like ";
	} else {
	    compair2 = " = ";
	}

	String s_pId = "";
	String itemCodeStr = "";
	String query = "";
	String query1 = "";
	Vector nodeVec = new Vector();

	ResultSet rs = null;
	ResultSet rs1 = null;

	try {
	    resultList.removeAllElements();

	    itemCodeStr = item;

	    stmt = connection.createStatement();

	    query = " SELECT 																					\n";
	    query += "		i.ida2a2 pid																		\n";
	    query += " FROM wtpart i, wtpartmaster m 													\n";
	    query += "	 WHERE i.ida2a2 IN (																\n";
	    query += "								SELECT 													\n";
	    query += "								MAX (ida3a5) pid										\n";
	    query += "								FROM KETPartUsageLink								\n";
	    query += "								WHERE parentItemcode = m.wtpartnumber		\n";
	    query += "									AND ida3a5 = (SELECT max(ia.ida2a2) FROM wtpart ia WHERE ia.ida3masterreference = m.ida2a2 and ia.latestiterationinfo = '1') 	\n";
	    query += "								)															\n";
	    if (!item.equals("")) {
		query += " AND m.wtpartnumber " + compair + " '" + itemCodeStr + "'							\n";
	    }
	    if (!desc.equals("")) {
		query += " AND m.name " + compair2 + " '" + desc + "'												\n";
	    }
	    query += " AND m.ida2a2 = i.ida3masterreference 											";

	    rs = stmt.executeQuery(query);

	    while (rs.next()) {
		nodeVec.addElement(rs.getString("pid") == null ? "" : rs.getString("pid").trim());
	    }

	    if (nodeVec.size() > 0) {
		int thousandCnt = 1 + nodeVec.size() / 1000;

		for (int cnt = 0; (cnt < thousandCnt) && (cnt * 1000 < nodeVec.size()); cnt++) {
		    s_pId = "'" + nodeVec.elementAt(cnt * 1000);
		    for (int i = (cnt * 1000 + 1); (i < (cnt * 1000 + 1000) && (i < nodeVec.size())); i++) {
			s_pId = s_pId + "', '" + nodeVec.elementAt(i);
		    }
		    s_pId = s_pId + "'";

		    stmt1 = connection.createStatement();

		    query1 = " SELECT DISTINCT             					\n" + "	m.wtpartnumber itemCode          				\n" + "	 ,  m.name descr                    				\n"
			    + "	 ,  m.defaultunit  uit                				\n" + "	 ,  i.createstampa2 createdDate     				\n" + "	 ,  i.statestate status, ph.name statusKr			\n"
			    + "	 ,  u.name creator                 					\n" + "	FROM wtpart i, wtpartmaster m, wtuser u 		\n" + "	 ,phasetemplate ph, phaselink pl					\n"
			    + "	WHERE   m.ida2a2 = i.ida3masterreference  	\n" + " AND i.ida3d2iterationinfo = u.ida2a2			\n" + " and pl.ida3a5 = i.ida3a2state						\n"
			    + " and pl.ida3b5 = ph.ida2a2						\n" + " and ph.phasestate = i.statestate					\n" + " AND i.ida2a2 IN (" + s_pId + ")					\n";

		    rs1 = stmt1.executeQuery(query1);

		    while (rs1.next()) {
			Hashtable hasItem = new Hashtable();
			hasItem.put("itemCode", rs1.getString("itemCode") == null ? "" : rs1.getString("itemCode").trim());
			hasItem.put("description", rs1.getString("descr") == null ? "" : rs1.getString("descr").trim());
			hasItem.put("defaultunit", rs1.getString("uit") == null ? "" : rs1.getString("uit").trim());
			hasItem.put("createdDate", rs1.getString("createdDate") == null ? "" : rs1.getString("createdDate").trim());
			hasItem.put("status", rs1.getString("status") == null ? "" : rs1.getString("status").trim());
			hasItem.put("statusKr", rs1.getString("statusKr") == null ? "" : rs1.getString("statusKr").trim());
			hasItem.put("createdBy", rs1.getString("creator") == null ? "" : rs1.getString("creator").trim());

			resultList.addElement(hasItem);
		    }
		}
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		stmt.close();
	    } catch (Exception e) {
	    }
	    try {
		rs1.close();
	    } catch (Exception e) {
	    }
	    try {
		stmt1.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void oldItemList(Connection connection, String item) throws Exception {
	PreparedStatement pstmt = null;
	PreparedStatement pstmt1 = null;
	ResultSet rs = null;
	ResultSet rs1 = null;

	String itemCode = "";
	String itemCodeStr = "";
	String query = "";
	String query1 = "";
	Vector nodeVec = new Vector();

	try {
	    resultList.removeAllElements();
	    itemCodeStr = item;

	    if (itemCodeStr.indexOf("*") > -1) {
		itemCodeStr = itemCodeStr.replace("*", "%");
	    }
	    query = " SELECT 																				\n" + "		i.ida2a2 pid																		\n" + " FROM wtpart i, wtpartmaster m 													\n" + "	 WHERE i.ida2a2 IN (																\n"
		    + "								SELECT 													\n" + "								MAX (ida3a5) pid										\n" + "								FROM KETPartUsageLink								\n" + "								WHERE parentItemcode = m.wtpartnumber		\n"
		    + "									AND ida3a5 = (SELECT max(ia.ida2a2) FROM wtpart ia WHERE ia.ida3masterreference = m.ida2a2 and ia.latestiterationinfo = '1') 	\n" + "								)															\n"
		    + " AND m.wtpartnumber LIKE ?														\n" + " AND m.ida2a2 = i.ida3masterreference 										\n";

	    pstmt = connection.prepareStatement(query);
	    pstmt.setString(1, itemCodeStr);
	    Kogger.debug(getClass(), "---------------------->> qry1 : " + query);
	    Kogger.debug(getClass(), "---------------------->> itemCodeStr : " + itemCodeStr);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		nodeVec.addElement(rs.getString("pid") == null ? "" : rs.getString("pid").trim());
	    }

	    if (nodeVec.size() > 0) {
		int thousandCnt = 1 + nodeVec.size() / 1000;

		for (int cnt = 0; (cnt < thousandCnt) && (cnt * 1000 < nodeVec.size()); cnt++) {
		    itemCode = "'" + nodeVec.elementAt(cnt * 1000);
		    for (int i = (cnt * 1000 + 1); (i < (cnt * 1000 + 1000) && (i < nodeVec.size())); i++) {
			itemCode = itemCode + "', '" + nodeVec.elementAt(i);
		    }
		    itemCode = itemCode + "'";

		    /*
	             * query1 = " SELECT DISTINCT										\n" + "		m.wtpartnumber itemCode						\n" + "	 ,	m.name descr										\n" + "	 ,	i.uom uom											\n" +
	             * "	 ,	i.userItemType uit									\n" + "	 ,	i.supplytypecode supplyType						\n" + "	 ,	i.itemstatus status									\n" + " FROM wtpart i, wtpartmaster m						\n" +
	             * " WHERE i.orgcode = ?									\n" + " AND i.ida2a2 IN (" + itemCode + ")					\n" + " AND m.ida2a2 = i.ida3masterreference				\n";
	             */

		    query1 = "	 SELECT DISTINCT             					\n" + "		m.wtpartnumber itemCode          				\n" + "		 ,  m.name descr                    				\n"
			    + "		 ,  replace(m.defaultunit,'KET_','')  uit                							\n" + "		 ,  to_char(i.createstampa2,'yyyy-mm-dd') createdDate     				\n"
			    + "		 ,  i.statestate status, ph.name statusKr			\n" + "		 ,  u.name creator                 					\n" + "		FROM wtpart i, wtpartmaster m, wtuser u 		\n"
			    + "		 ,phasetemplate ph, phaselink pl					\n" + "		WHERE   m.ida2a2 = i.ida3masterreference  	\n" + "	 AND i.ida3d2iterationinfo = u.ida2a2				\n"
			    + "	 and pl.ida3a5 = i.ida3a2state							\n" + "	 and pl.ida3b5 = ph.ida2a2							\n" + "	 and ph.phasestate = i.statestate						\n" + "	AND i.ida2a2 IN (" + itemCode
			    + ")					\n";

		    Kogger.debug(getClass(), "---------------------->> qry2 : " + query1);
		    pstmt1 = connection.prepareStatement(query1);
		    // pstmt1.setString(1, BOMBasicInfoPool.getOrgCode());

		    rs1 = pstmt1.executeQuery();

		    while (rs1.next()) {
			ItemTableData data = new ItemTableData();
			data.setItemCode(rs1.getString("itemCode"));
			data.setDesc(rs1.getString("descr"));
			data.setUitStr(rs1.getString("uit"));
			data.setStatus(rs1.getString("status"));
			data.setStartDate(rs1.getString("createdDate"));
			data.setStatusKrStr(rs1.getString("statusKr"));
			data.setUserNmStr(rs1.getString("creator"));
			resultList.addElement(data);
		    }

		    try {
			rs1.close();
		    } catch (Exception e) {
		    }
		    try {
			pstmt1.close();
		    } catch (Exception e) {
		    }
		}
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	    try {
		rs1.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt1.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void isBomParentExist(Connection connection, String itemCode) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	String query = "";

	try {
	    dataCount = 0;

	    query = " SELECT							\n" + "		COUNT(*)						\n" + " FROM KETPartUsageLink		\n" + " WHERE childItemcode = ?	\n";

	    pstmt = connection.prepareStatement(query);
	    pstmt.setString(1, itemCode.trim());

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		dataCount = rs.getInt(1);
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void searchUpwardList(Connection connection, String item, String desc) throws Exception {
	String compair = "";
	String compair2 = "";
	if (item.indexOf("*") > -1) {
	    item = item.replace("*", "%");
	    compair = " like ";
	} else {
	    compair = " = ";
	}

	if (desc.indexOf("*") > -1) {
	    desc = desc.replace("*", "%");
	    compair2 = " like ";
	} else {
	    compair2 = " = ";
	}

	ResultSet rs = null;
	ResultSet rs1 = null;

	String itemCode = "";
	String itemCodeStr = "";
	String query = "";
	String query1 = "";
	Vector nodeVec = new Vector();

	try {
	    resultList.removeAllElements();
	    stmt = connection.createStatement();
	    itemCodeStr = item;

	    query = "	SELECT DISTINCT																			\n";
	    query += "		m.ida3b5 cid																			\n";
	    query += "	FROM KETPartUsageLink m, wtpartmaster w										\n";
	    query += "	WHERE m.orgcode = '" + BOMBasicInfoPool.getOrgCode().trim() + "'			\n";
	    query += "	AND m.ida3b5 = w.ida2a2																\n";
	    if (!item.equals("")) {
		query += "	AND childItemcode " + compair + " '" + itemCodeStr + "'									\n";
	    }
	    if (!desc.equals("")) {
		query += "	AND w.name " + compair2 + " '" + desc + "'													\n";
	    }

	    rs = stmt.executeQuery(query);

	    while (rs.next()) {
		nodeVec.addElement(rs.getString("cid") == null ? "" : rs.getString("cid").trim());
	    }

	    if (nodeVec.size() > 0) {
		int thousandCnt = 1 + nodeVec.size() / 1000;

		for (int cnt = 0; (cnt < thousandCnt) && (cnt * 1000 < nodeVec.size()); cnt++) {
		    itemCode = "'" + nodeVec.elementAt(cnt * 1000);
		    for (int i = (cnt * 1000 + 1); (i < (cnt * 1000 + 1000) && (i < nodeVec.size())); i++) {
			itemCode = itemCode + "', '" + nodeVec.elementAt(i);
		    }
		    itemCode = itemCode + "'";

		    stmt1 = connection.createStatement();
		    query1 = " SELECT DISTINCT																	\n" + "		m.wtpartnumber itemCode													\n" + "	 ,	m.name descr																	\n"
			    + "	 ,  m.defaultunit  uit                												\n" + "	 ,	u.name creator																	\n" + "	 ,	i.createstampa2 createdDate													\n"
			    + "	 ,	i.statestate status, ph.name	statusKr										\n" + " FROM wtpart i, wtpartmaster m	, wtuser u									\n" + "	 ,phasetemplate ph, phaselink pl													\n"
			    + " WHERE m.ida2a2 = i.ida3masterreference										\n" + " AND i.ida3masterreference IN (" + itemCode + ")							\n"
			    + " AND i.ida3d2iterationinfo = u.ida2a2											\n" + " and pl.ida3a5 = i.ida3a2state													\n" + " and pl.ida3b5 = ph.ida2a2														\n"
			    + " and ph.phasestate = i.statestate												\n";

		    rs1 = stmt1.executeQuery(query1);

		    while (rs1.next()) {
			Hashtable hasItem = new Hashtable();
			hasItem.put("itemCode", rs1.getString("itemCode") == null ? "" : rs1.getString("itemCode").trim());
			hasItem.put("description", rs1.getString("descr") == null ? "" : rs1.getString("descr").trim());
			hasItem.put("defaultunit", rs1.getString("uit") == null ? "" : rs1.getString("uit").trim());
			hasItem.put("createdBy", rs1.getString("creator") == null ? "" : rs1.getString("creator").trim());
			hasItem.put("createdDate", rs1.getString("createdDate") == null ? "" : rs1.getString("createdDate").trim());
			hasItem.put("status", rs1.getString("status") == null ? "" : rs1.getString("status").trim());
			hasItem.put("statusKr", rs1.getString("statusKr") == null ? "" : rs1.getString("statusKr").trim());

			resultList.addElement(hasItem);
		    }
		}
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		stmt.close();
	    } catch (Exception e) {
	    }
	    try {
		rs1.close();
	    } catch (Exception e) {
	    }
	    try {
		stmt1.close();
	    } catch (Exception e) {
	    }
	}
    }

    public String getUpwardEcoNo(String cCode) throws Exception {
	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String ecoNo = "";
	String itemCode = "";
	String bomVersion = "";
	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    String query = " SELECT bomversion, parentItemCode FROM 	\n" + "   (SELECT                             	\n" + "       level lvl                        	\n"
		    + "     ,  bomversion                        	\n" + "     ,  parentItemCode                   	\n" + "     ,  childItemCode                    	\n"
		    + "     FROM KETPartUsageLink          	\n" + "     START WITH (versionitemcode) IN (?) 	\n" + "     CONNECT BY PRIOR ida3a5 = versionitemcode	\n" + "     ORDER BY LVL DESC)	\n"
		    + "     WHERE ROWNUM = 1	\n";

	    pstmt = connection.prepareStatement(query);
	    pstmt.setString(1, cCode);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		itemCode = rs.getString("parentItemCode") == null ? "" : rs.getString("parentItemCode").trim();
		bomVersion = rs.getString("bomversion") == null ? "" : rs.getString("bomversion").trim();
		ecoNo = getEcoNumber(itemCode, bomVersion);
		Kogger.debug(getClass(), "========> ecoNo : " + ecoNo);
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		}

		if (pstmt != null) {
		    pstmt.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}
	return ecoNo;
    }

    public String getEcoNumber(String itemCode, String bomVersion) throws Exception {
	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String ecoNo = "";
	String strVer = "";
	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    if (bomVersion.indexOf(".") > 0)
		strVer = bomVersion.substring(0, bomVersion.indexOf("."));

	    String query = "SELECT  distinct ecoheadernumber as ecoNo  from ketbomecoheader		\n" + "WHERE  ecoitemcode = ?																\n" + "   AND  bomversion = ?																\n";
	    Kogger.debug(getClass(), "econo query ===>>> : " + query);
	    Kogger.debug(getClass(), "econo parameter1 ===>>> : " + itemCode);
	    Kogger.debug(getClass(), "econo parameter2 ===>>> : " + bomVersion);

	    pstmt = connection.prepareStatement(query);
	    pstmt.setString(1, itemCode);
	    pstmt.setString(2, strVer);

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		ecoNo = rs.getString("ecoNo") == null ? "" : rs.getString("ecoNo").trim();
		Kogger.debug(getClass(), "========> ecoNo : " + ecoNo);
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		}

		if (pstmt != null) {
		    pstmt.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}
	return ecoNo;
    }

    // ket 사용 정전개 (자부품 포함 모두 최신으로 가져옴)
    public void downwardExplosionCurrentBom(String childItemCode, String changeActivityCode) throws Exception {
	DBConnectionManager res = null;
	Connection connection = null;

	PreparedStatement pstmt = null;
	PreparedStatement pstmtPOid = null;
	PreparedStatement pstmtDesignator = null;
	PreparedStatement pstmtSubstitute = null;

	ResultSet rs = null;
	ResultSet rsPOid = null;
	ResultSet rsDesignator = null;
	ResultSet rsSubstitute = null;
	boolean isProd = false;

	String itemStr = childItemCode.trim();

	WTPart part = KETPartHelper.service.getPart(itemStr);
	String strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);

	if (PartUtil.isProductType(strType)) { // 제품인 경우
	    isProd = true;
	} else { // 금형인 경우
	    isProd = false;
	}

	try {
	    StringBuffer strSql = new StringBuffer();

	    String query1 = "";
	    String query2 = "";

	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    String query = " SELECT 										\n" + "		MAX(ida3a5) pid					\n" + " FROM KETPartUsageLink 				\n" + " WHERE parentItemcode = ?			\n"
		    + "	AND ida3a5 IN (SELECT ia.ida2a2 FROM wtpart ia WHERE ia.ida2a2 = KETPartUsageLink.ida3a5 and ia.latestiterationinfo = '1') 	\n";

	    pstmtPOid = connection.prepareStatement(query);
	    pstmtPOid.setString(1, itemStr);

	    rsPOid = pstmtPOid.executeQuery();
	    String strPOid = "";

	    Kogger.debug(getClass(), ">>> 1. query : " + query);
	    Kogger.debug(getClass(), ">>> pramete - itemStr : " + itemStr);

	    if (rsPOid.next()) {
		strPOid = rsPOid.getString("pid") == null ? "" : rsPOid.getString("pid").trim();
	    }

	    resultList.removeAllElements();

	    // Root Node 의 Item Code 를 가지고 BOM 하향전개
	    strSql.append("	 SELECT																				\n").append("	   bom.lvl lvl                                                  							\n")
		    .append("	,  bom.ida3a5 mid                                              							\n").append("	,  bom.ida3b5 mmid                                              						\n")
		    .append("	,  bom.parentItemCode parentItem                                    				\n").append("	,  bom.childItemCode item                                        						\n")
		    .append("	,  nvl(itemMaster.NAME,'') description                                				\n")
		    .append("	,  item.versionida2versioninfo || '.' || item.iterationida2iterationinfo version     \n").append("	,  item.statestate  status	,ph.name	statusKr										\n")
		    .append("	,  bom.unit  uit				 															\n").append("	,  bom.quantity quantity                                          						\n")
		    .append("	,  bom.boxQuantity boxQuantity                                 						\n").append("	,  bom.itemSeq itemSeq                                          						\n")
		    .append("	,  bom.startDate startDate                                        						\n").append("	,  bom.endDate endDate								         						\n")
		    .append(",  bom.material	material																\n").append(",  bom.hardnessfrom																	\n").append(",  bom.hardnessto																		\n")
		    .append(",  bom.designdate																		\n").append("	  FROM                                                     								\n")
		    .append("	    (SELECT                                                								\n").append("	      rownum seqno                                          							\n")
		    .append("	    ,  level lvl                                                								\n").append("	    ,  ida3b5                                                								\n")
		    .append("	    ,  ida2a2                                    											\n").append("	    ,  ida3a5                                                								\n")
		    .append("	    ,  parentItemCode                                          							\n").append("	    ,  childItemCode                                            							\n")
		    .append("	    ,  unit		                                                							\n").append("	    ,  quantity                                                							\n")
		    .append("	    ,  boxQuantity                                              							\n").append("	    ,  itemSeq                                                							\n")
		    .append("	    ,  startDate                                              								\n").append("	    ,  endDate     																		\n")
		    .append("	    ,  material																			\n").append("	    ,  hardnessfrom																		\n").append("	    ,  hardnessto																		\n")
		    .append("	    ,  designdate       																	\n").append("	    ,  bomversion   				                                   						\n")
		    .append("	    ,  versionitemcode			                                   						\n").append("	    FROM KETPartUsageLink                                        					\n")
		    .append("	    START WITH (ida3a5) IN (?)                                    					\n").append("	          CONNECT BY PRIOR (                                   						\n")
		    .append("	              SELECT                                          							\n").append("	              MAX(ida3a5)                                      							\n")
		    .append("	              FROM KETPartUsageLink b                              				\n").append("	              WHERE b.parentItemcode = KETPartUsageLink.childitemcode      \n")
		    // .append("	               AND   b.ida3a5 = KETPartUsageLink.versionitemcode     			 \n")
		    .append("	                    ) = KETPartUsageLink.ida3a5                              		\n");
	    if (isProd) { // 제품
		strSql.append("	    ORDER SIBLINGS BY  itemSeq) bom                                			\n");
	    } else { // 금형
		strSql.append("	    ORDER SIBLINGS BY  childItemCode) bom                         			\n");
	    }
	    strSql.append("	    , wtpartmaster itemMaster    													\n").append("	    , wtpart item    																		\n").append("	    , phasetemplate ph  																\n")
		    .append("	    , phaselink pl      																	\n").append("	  WHERE bom.ida3b5 = itemMaster.ida2a2 (+)										\n")
		    .append("	  AND bom.versionitemcode = item.ida2a2                                			\n").append("	 and pl.ida3a5 = item.ida3a2state                                   					\n")
		    .append("	 and pl.ida3b5 = ph.ida2a2                                     						\n").append("	 and ph.phasestate = item.statestate       											\n")
		    .append("   order by seqno ");

	    pstmt = connection.prepareStatement(strSql.toString());
	    pstmt.setString(1, strPOid);

	    Kogger.debug(getClass(), ">>> strSql : " + strSql.toString());
	    Kogger.debug(getClass(), ">>> pramete : " + strPOid);

	    rs = pstmt.executeQuery();

	    strSql.delete(0, strSql.length());

	    dataCount = 0;
	    BOMAssyComponent component = null;
	    Vector checkOutItemVec = new Vector();
	    String itemCodeStr = "";
	    String parentItemCodeStr = "";
	    String s_verSion = "";
	    String pOid = "";
	    String cOid = "";

	    query2 = " SELECT	 substituteitem subItem			    		\n";
	    query2 += "          , quantity										\n";
	    query2 += "          , unit												\n";
	    query2 += "FROM   ketbomsubstitutemaster						\n";
	    query2 += "WHERE  parentitem = ?									\n";
	    query2 += "  AND   childitem = ?									\n";
	    query2 += "  AND   versionitemcode = ?							\n";

	    String ketEcoNo = "";
	    pstmtSubstitute = connection.prepareStatement(query2);

	    while (rs.next()) {
		dataCount++;

		itemCodeStr = rs.getString("item") == null ? "" : rs.getString("item").trim();
		parentItemCodeStr = rs.getString("parentItem") == null ? "" : rs.getString("parentItem").trim();
		pOid = rs.getString("mid") == null ? "" : rs.getString("mid").trim();
		cOid = rs.getString("mmid") == null ? "" : rs.getString("mmid").trim();
		s_verSion = rs.getString("version") == null ? "" : rs.getString("version");
		if (dataCount == 1) {
		    ketEcoNo = getEcoNumber(parentItemCodeStr, s_verSion);
		}

		component = new BOMAssyComponent(itemCodeStr.trim());

		component.setSeqInt(new Integer(dataCount));
		component.setLevelInt(new Integer(rs.getString("lvl") == null ? "1" : rs.getString("lvl")));
		component.setDescStr(rs.getString("description") == null ? "" : rs.getString("description"));
		component.setParentItemCodeStr(parentItemCodeStr);
		component.setVersionStr(s_verSion);
		component.setStatusStr(rs.getString("status") == null ? "" : rs.getString("status"));
		component.setStatusKrStr(rs.getString("statusKr") == null ? "" : rs.getString("statusKr"));
		component.setUitStr(rs.getString("uit") == null ? "" : rs.getString("uit"));

		String qty = rs.getString("quantity") == null ? "" : rs.getString("quantity");
		if (qty.startsWith("."))
		    qty = "0" + qty;
		component.setQuantityDbl(new Double(qty));

		String boxQty = rs.getString("boxQuantity") == null ? "" : rs.getString("boxQuantity");
		if (boxQty.startsWith("."))
		    boxQty = "0" + boxQty;
		component.setBoxQuantityDbl(new Double(boxQty));
		component.setStartDate(rs.getString("startDate") == null ? "" : rs.getString("startDate"));
		component.setEndDate(rs.getString("endDate") == null ? "" : rs.getString("endDate"));
		component.setItemSeqInt(new Integer(rs.getString("itemSeq") == null ? "10" : rs.getString("itemSeq")));

		component.setMaterialStr(rs.getString("material") == null ? "" : rs.getString("material"));
		component.setHardnessFrom(rs.getString("hardnessfrom") == null ? "" : rs.getString("hardnessfrom"));
		component.setHardnessTo(rs.getString("hardnessto") == null ? "" : rs.getString("hardnessto"));
		component.setDesignDate(rs.getString("designdate") == null ? "" : rs.getString("designdate"));
		component.setEcoNoStr(ketEcoNo);
		// Added by MJOH, 2011-04-07
		component.setIBAPartType(strType);

		checkOutItemVec.addElement(itemCodeStr);

		Vector substituteVec = new Vector();

		try {
		    Kogger.debug(getClass(), ">>> Sql - query2 : " + query2);
		    Kogger.debug(getClass(), ">>> query2 pramete - parentItemCodeStr : " + parentItemCodeStr);
		    Kogger.debug(getClass(), ">>> query2 pramete - childItemCodeStr : " + itemCodeStr);
		    Kogger.debug(getClass(), ">>> query2 pramete - cOid : " + cOid);

		    pstmtSubstitute.clearParameters();

		    pstmtSubstitute.setString(1, parentItemCodeStr);
		    pstmtSubstitute.setString(2, itemCodeStr);
		    pstmtSubstitute.setString(3, cOid);

		    rsSubstitute = pstmtSubstitute.executeQuery();

		    substituteVec.removeAllElements();

		    while (rsSubstitute.next()) {
			BOMSubAssyComponent subComponent = new BOMSubAssyComponent();

			String subItemCodeStr = rsSubstitute.getString("subItem") == null ? "" : rsSubstitute.getString("subItem");
			subComponent.setSubstituteItemCodeStr(subItemCodeStr);

			String subAssyQty = (rsSubstitute.getString("quantity") == null ? "" : rsSubstitute.getString("quantity"));
			if (!Utility.isDouble(subAssyQty))
			    subAssyQty = "0";
			subComponent.setQuantityDbl(new Double(subAssyQty));
			subComponent.setParentItemCodeStr(parentItemCodeStr);
			subComponent.setChildItemCodeStr(itemCodeStr);
			subComponent.setUitStr(rsSubstitute.getString("unit") == null ? "" : rsSubstitute.getString("unit"));
			substituteVec.addElement(subComponent);
		    }

		    if (rsSubstitute != null) {
			rsSubstitute.close();
			rsSubstitute.close();
		    }

		} catch (Exception ex) {
		    Kogger.error(getClass(), ex);
		}

		if (substituteVec != null && substituteVec.size() > 0) {
		    component.setSubAssyComponent(substituteVec);
		}

		resultList.addElement(component);
	    }

	    Vector coworkerVec = new Vector();
	    String resultItemCode = "";
	    String checkOutItemCode = "";
	    coworkerVec = KETBomHelper.service.getCheckOuter(checkOutItemVec);
	    if (coworkerVec != null && coworkerVec.size() > 0) {
		for (int i = 0; i < resultList.size(); i++) {
		    resultItemCode = resultList.elementAt(i) == null ? "" : resultList.elementAt(i).toString().trim();
		    for (int j = 0; j < coworkerVec.size(); j++) {
			checkOutItemCode = coworkerVec.elementAt(j) == null ? "" : coworkerVec.elementAt(j).toString().trim().substring(0, coworkerVec.elementAt(j).toString().trim().indexOf("|"));
			if (resultItemCode.equals(checkOutItemCode)) {
			    BOMAssyComponent cmp = (BOMAssyComponent) resultList.elementAt(i);
			    cmp.setCheckOutStr(coworkerVec.elementAt(j).toString().substring(coworkerVec.elementAt(j).toString().indexOf("|") + 1));
			    resultList.set(i, cmp);
			    break;
			}
		    }
		}
	    }
	} catch (Exception ee) {
	    Kogger.error(getClass(), ee);
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs.close();
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt.close();
		}

		if (rsPOid != null) {
		    rsPOid.close();
		    rsPOid.close();
		}
		if (pstmtPOid != null) {
		    pstmtPOid.close();
		    pstmtPOid.close();
		}

		if (rsDesignator != null) {
		    rsDesignator.close();
		    rsDesignator.close();
		}
		if (pstmtDesignator != null) {
		    pstmtDesignator.close();
		    pstmtDesignator.close();
		}

		if (rsSubstitute != null) {
		    rsSubstitute.close();
		    rsSubstitute.close();
		}
		if (pstmtSubstitute != null) {
		    pstmtSubstitute.close();
		    pstmtSubstitute.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		MessageBox mbox = new MessageBox("DB Close Failure", "DB Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}
    }

    // ket 사용 역전개.
    public void upwardExplosionCurrentBom(String childItemCode) throws Exception {
	DBConnectionManager res = null;
	Connection connection = null;

	PreparedStatement pstmt = null;
	PreparedStatement pstmtPOid = null;
	PreparedStatement pstmtRoot = null;

	ResultSet rs = null;
	ResultSet rsRoot = null;
	ResultSet rsPOid = null;

	String itemStr = childItemCode.trim();

	// Added by MJOH, 2011-04-07
	WTPart part = KETPartHelper.service.getPart(itemStr);
	String strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);

	try {
	    StringBuffer strSql = new StringBuffer();

	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    String query = " SELECT 							\n"
		    // + "		MAX(ida2a2) cid				\n"
		    + "		MAX(versionitemcode) cid				\n" + " FROM KETPartUsageLink 		\n" + " WHERE childitemcode = ?		\n"
		    + " AND versionitemcode IN (SELECT ia.ida2a2 FROM wtpart ia WHERE ia.ida2a2 = KETPartUsageLink.versionitemcode and ia.latestiterationinfo = '1') ";

	    pstmtPOid = connection.prepareStatement(query);
	    pstmtPOid.setString(1, itemStr);

	    rsPOid = pstmtPOid.executeQuery();

	    String strCOid = "";

	    if (rsPOid.next()) {
		strCOid = rsPOid.getString("cid") == null ? "" : rsPOid.getString("cid").trim();
	    }
	    Kogger.debug(getClass(), "------------------------------------------------>>>query : " + query);
	    Kogger.debug(getClass(), "------------------------------------------------>>>itemStr : " + itemStr);
	    resultList.removeAllElements();

	    String ketEcoNo = StringUtil.checkNull(getUpwardEcoNo(strCOid)); // 역전개 econo를 가져온다.
	    // Root Node 의 Item Code 를 가지고 BOM 역전개
	    strSql.append(" SELECT																											\n").append("	bom.lvl lvl																										\n").append(",	bom.ida3b5 cid																							\n")
		    .append(",	ROWNUM seqno																						\n").append(",	bom.parentItemCode parentItem																		\n").append(",	bom.childItemCode item																				\n")
		    .append(",	nvl(itemMaster.NAME,'') description																	\n").append(",	item.versionida2versioninfo || '.' || item.iterationida2iterationinfo version						\n")
		    .append(",	item.statestate  status, 	ph.name statusKr															\n").append(",	bom.unit  uit																								\n")
		    .append(",	bom.quantity quantity																					\n").append(",	bom.boxQuantity boxQuantity																			\n").append(",	bom.itemSeq itemSeq																					\n")
		    .append(",	bom.startDate startDate																				\n").append(",	bom.endDate endDate																					\n")
		    .append("	  ,bom.material, bom.hardnessfrom, bom.hardnessto, bom.designdate							\n").append("	FROM 																										\n").append("		(SELECT																									\n")
		    .append("			rownum seqno																						\n").append("		,	level lvl																								\n").append("		,	ida3b5																								\n")
		    .append("		,	versionitemcode																						\n").append("		,	ida3a5																									\n").append("		,	parentItemCode																						\n")
		    .append("		,	childItemCode																						\n").append("		,	unit																									\n").append("		,	quantity																								\n")
		    .append("		,	boxQuantity																							\n")
		    .append("		,	itemSeq																								\n")
		    .append("		,	startDate																								\n")
		    .append("		,	endDate																								\n")
		    .append("		,	material																								\n")
		    .append("		,	hardnessfrom																						\n")
		    .append("		,	hardnessto																							\n")
		    .append("		,	designdate																							\n")
		    .append("		FROM KETPartUsageLink																				\n")
		    .append("		START WITH (versionitemcode) IN (?)																\n")
		    .append("		CONNECT BY PRIOR ida3a5 = versionitemcode													\n")
		    // .append("		START WITH (ida2a2) IN (?)																		\n")
		    // .append("		CONNECT BY PRIOR ida3a5 = ida2a2															\n")
		    .append("		) bom																										\n").append("		, wtpartmaster itemMaster																				\n").append("		, wtpart item																								\n")
		    .append("		, phasetemplate ph																						\n").append("		, phaselink pl																							\n").append("	WHERE bom.ida3b5 = itemMaster.ida2a2 																\n")
		    .append("	AND bom.ida3a5 = item.ida2a2																			\n")

		    .append("	and pl.ida3a5 = item.ida3a2state																			\n").append("	and pl.ida3b5 = ph.ida2a2																					\n")
		    .append("	and ph.phasestate = item.statestate																		\n")

		    .append("	AND bom.ida3a5 = (SELECT MAX(i.ida2a2) FROM wtpart i, wtpartmaster m WHERE i.ida3masterreference = m.ida2a2 AND m.wtpartnumber = bom.parentItemCode)		\n")
		    .append("	ORDER BY bom.seqno ASC																				\n");

	    Kogger.debug(getClass(), "@@@@@ sql : " + strSql.toString());
	    Kogger.debug(getClass(), "@@@@@ value : " + strCOid);

	    pstmt = connection.prepareStatement(strSql.toString());
	    pstmt.setString(1, strCOid);

	    rs = pstmt.executeQuery();

	    strSql.delete(0, strSql.length());

	    int dataCount = 0;
	    BOMAssyComponent component = null;
	    Vector checkOutItemVec = new Vector();
	    String itemCodeStr = "";
	    String parentItemCodeStr = "";
	    String levelStr = "";
	    String cOid = "";

	    while (rs.next()) {
		dataCount++;

		// shin.....
		itemCodeStr = rs.getString("parentItem") == null ? "" : rs.getString("parentItem").trim();
		parentItemCodeStr = rs.getString("item") == null ? "" : rs.getString("item").trim();
		Kogger.debug(getClass(), "@@@@@ itemCodeStr : " + itemCodeStr);
		Kogger.debug(getClass(), "@@@@@ parentItemCodeStr : " + parentItemCodeStr);

		levelStr = rs.getString("lvl") == null ? "1" : rs.getString("lvl");
		cOid = rs.getString("cid") == null ? "" : rs.getString("cid");

		// if(dataCount != 1)
		// {
		Kogger.debug(getClass(), "@@@@@ dataCount : " + dataCount);
		component = new BOMAssyComponent(itemCodeStr.trim());
		component.setSeqInt(new Integer(dataCount));
		component.setLevelInt(new Integer(Integer.parseInt(levelStr) - 1));
		component.setDescStr(rs.getString("description") == null ? "" : rs.getString("description"));
		component.setParentItemCodeStr(parentItemCodeStr);
		component.setVersionStr(rs.getString("version") == null ? "" : rs.getString("version"));
		component.setStatusStr(rs.getString("status") == null ? "" : rs.getString("status"));

		component.setStatusKrStr(rs.getString("statusKr") == null ? "" : rs.getString("statusKr"));

		component.setUitStr(rs.getString("uit") == null ? "" : rs.getString("uit"));

		String qty = rs.getString("quantity") == null ? "" : rs.getString("quantity");
		if (qty.startsWith("."))
		    qty = "0" + qty;
		component.setQuantityDbl(new Double(qty));

		String boxQty = rs.getString("boxQuantity") == null ? "" : rs.getString("boxQuantity");
		if (boxQty.startsWith("."))
		    boxQty = "0" + boxQty;
		component.setBoxQuantityDbl(new Double(boxQty));
		component.setStartDate(rs.getString("startDate") == null ? "" : rs.getString("startDate"));
		component.setEndDate(rs.getString("endDate") == null ? "" : rs.getString("endDate"));
		component.setItemSeqInt(new Integer(rs.getString("itemSeq") == null ? "10" : rs.getString("itemSeq")));

		component.setMaterialStr(rs.getString("material") == null ? "" : rs.getString("material"));
		component.setHardnessFrom(rs.getString("hardnessfrom") == null ? "" : rs.getString("hardnessfrom"));
		component.setHardnessTo(rs.getString("hardnessto") == null ? "" : rs.getString("hardnessto"));
		component.setDesignDate(rs.getString("designdate") == null ? "" : rs.getString("designdate"));
		component.setEcoNoStr(ketEcoNo);
		// Added by MJOH, 2011-04-07
		component.setIBAPartType(strType);

		checkOutItemVec.addElement(itemCodeStr);

		resultList.addElement(component);
		// }
	    }

	    strSql.append(" SELECT																							\n").append("		m.wtpartnumber itemCode															\n").append(" ,	m.name description																		\n")
		    .append(" ,	i.versionida2versioninfo || '.' || i.iterationida2iterationinfo version				\n").append(" ,	i.statestate	 status, ph.name statusKr												\n")
		    .append(" ,	m.defaultunit uit																			\n").append(" ,	to_char(sysdate, 'YYYY-MM-DD') startDate											\n")
		    .append("  FROM wtpart i, wtpartmaster m, phasetemplate ph, phaselink pl					\n").append("	WHERE m.wtpartnumber = ?																\n")
		    .append("	AND m.ida2a2 = i.ida3masterreference													\n")

		    .append("	and pl.ida3a5 = i.ida3a2state																\n").append("	and pl.ida3b5 = ph.ida2a2																	\n").append("	and ph.phasestate = i.statestate															\n")

		    .append("	AND i.latestiterationinfo = '1'																\n").append("	ORDER BY i.versionida2versioninfo, i.iterationida2iterationinfo DESC				\n");

	    pstmtRoot = connection.prepareStatement(strSql.toString());
	    pstmtRoot.setString(1, parentItemCodeStr);
	    rsRoot = pstmtRoot.executeQuery();

	    Kogger.debug(getClass(), "@@@@@33333  sql : " + strSql.toString());
	    Kogger.debug(getClass(), "@@@@@33333  parentItemCodeStr : " + parentItemCodeStr);

	    strSql.delete(0, strSql.length());

	    if (rsRoot.next()) {
		component = new BOMAssyComponent(parentItemCodeStr);
		component.setSeqInt(new Integer(dataCount + 1));
		component.setLevelInt(new Integer(Integer.parseInt(levelStr)));
		component.setDescStr(rsRoot.getString("description") == null ? "" : rsRoot.getString("description"));
		component.setParentItemCodeStr("");
		component.setVersionStr(rsRoot.getString("version") == null ? "" : rsRoot.getString("version"));
		component.setStatusStr(rsRoot.getString("status") == null ? "" : rsRoot.getString("status"));
		component.setStatusKrStr(rsRoot.getString("statusKr") == null ? "" : rsRoot.getString("statusKr"));
		component.setUitStr(rsRoot.getString("uit") == null ? "" : rsRoot.getString("uit"));
		component.setQuantityDbl(new Double(1.000));
		component.setBoxQuantityDbl(new Double(1.000));
		component.setStartDate(rsRoot.getString("startDate") == null ? "" : rsRoot.getString("startDate"));
		component.setItemSeqInt(new Integer("10"));
		component.setOpSeqInt(new Integer("1"));
		component.setEcoNoStr(ketEcoNo);
		// Added by MJOH, 2011-04-07
		component.setIBAPartType(strType);

		resultList.addElement(component);
	    }
	} catch (Exception ee) {
	    Kogger.error(getClass(), ee);
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs.close();
		}
		if (rsRoot != null) {
		    rsRoot.close();
		    rsRoot.close();
		}
		if (rsPOid != null) {
		    rsPOid.close();
		    rsPOid.close();
		}

		if (pstmt != null) {
		    pstmt.close();
		    pstmt.close();
		}
		if (pstmtRoot != null) {
		    pstmtRoot.close();
		    pstmtRoot.close();
		}
		if (pstmtPOid != null) {
		    pstmtPOid.close();
		    pstmtPOid.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		MessageBox mbox = new MessageBox("DB Close Failure", "DB Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}
    }

    // ket 사용 버젼별 정전개.
    public void downwardExplosionCurrentBom(String childItemCode, String changeActivityCode, String versionStr) throws Exception {
	DBConnectionManager res = null;
	Connection connection = null;

	PreparedStatement pstmt = null;
	PreparedStatement pstmtPOid = null;
	PreparedStatement pstmtDesignator = null;
	PreparedStatement pstmtSubstitute = null;

	ResultSet rs = null;
	ResultSet rsPOid = null;
	ResultSet rsDesignator = null;
	ResultSet rsSubstitute = null;
	boolean isProd = false;

	String itemStr = childItemCode.trim();

	WTPart part = KETPartHelper.service.getPart(itemStr);
	String strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);

	if (PartUtil.isProductType(strType)) { // 제품인 경우
	    isProd = true;
	} else { // 금형인 경우
	    isProd = false;
	}

	try {
	    StringBuffer strSql = new StringBuffer();

	    String query1 = "";
	    String query2 = "";

	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    String strPOid = "";
	    String query = " SELECT  MAX (ida3a5) pid						\n" + "  FROM  KETPartUsageLink b, wtpart i			\n" + " WHERE b.parentItemcode = ?					\n" + "   AND  b.ida3a5 = i.ida2a2						\n"
		    + "   AND  i.versionida2versioninfo = ?				\n" + "   AND  i.latestiterationinfo = '1'					\n";

	    pstmtPOid = connection.prepareStatement(query);
	    pstmtPOid.setString(1, itemStr);
	    pstmtPOid.setString(2, versionStr);

	    rsPOid = pstmtPOid.executeQuery();

	    Kogger.debug(getClass(), ">>> 1. query : " + query);
	    Kogger.debug(getClass(), ">>> pramete - itemStr : " + itemStr);

	    if (rsPOid.next()) {
		strPOid = rsPOid.getString("pid") == null ? "" : rsPOid.getString("pid").trim();
	    }

	    resultList.removeAllElements();

	    // Root Node 의 Item Code 를 가지고 BOM 하향전개
	    strSql.append("	 SELECT																				\n").append("	   bom.lvl lvl                                                  								\n")
		    .append("	,  bom.ida3a5 mid                                              								\n").append("	,  bom.ida3b5 mmid                                              							\n")
		    .append("	,  bom.parentItemCode parentItem                                    					\n").append("	,  bom.childItemCode item                                        							\n")
		    .append("	,  nvl(itemMaster.NAME,'') description                                					\n")
		    .append("	,  item.versionida2versioninfo || '.' || item.iterationida2iterationinfo version      	\n").append("	,  item.statestate  status																	\n")
		    .append("	,  ph.name	statusKr																		\n").append("	,  bom.unit  uit				 																\n")
		    .append("	,  bom.quantity quantity                                          							\n").append("	,  bom.boxQuantity boxQuantity                                 							\n")
		    .append("	,  bom.itemSeq itemSeq                                          							\n").append("	,  bom.startDate startDate                                        							\n")
		    .append("	,  bom.endDate endDate         															\n").append("	,  bom.material																				\n").append("	,  bom.hardnessfrom																		\n")
		    .append("	,  bom.hardnessto																			\n").append("	,  bom.designdate																			\n")
		    .append("	  FROM                                                     									\n").append("	    (SELECT                                                									\n")
		    .append("	      rownum seqno                                          								\n").append("	    ,  level lvl                                                									\n")
		    .append("	    ,  ida3b5                                                									\n").append("	    ,  ida2a2                                    												\n")
		    .append("	    ,  ida3a5                                                									\n").append("	    ,  parentItemCode                                          								\n")
		    .append("	    ,  childItemCode                                            								\n").append("	    ,  unit 		                                               								\n")
		    .append("	    ,  quantity                                                								\n").append("	    ,  boxQuantity                                              								\n")
		    .append("	    ,  itemSeq                                                								\n").append("	    ,  startDate                                              									\n")
		    .append("	    ,  endDate      																			\n").append("	    ,  material      																			\n").append("	    ,  hardnessfrom    																		\n")
		    .append("	    ,  hardnessto      																		\n").append("	    ,  designdate       																		\n").append("	    ,  bomversion           																	\n")
		    .append("	    ,  versionitemcode           															\n").append("	    FROM KETPartUsageLink                                        						\n")
		    .append("	    START WITH (ida3a5) IN (?)                                    						\n").append("	          CONNECT BY PRIOR                                   							\n")
		    .append("	            versionitemcode = ida3a5                                                     \n");
	    // .append("	              SELECT                                          								\n")
	    // .append("	              MAX(ida3a5)                                      								\n")
	    // .append("	              FROM KETPartUsageLink b                              					\n")
	    // .append("	              WHERE b.parentItemcode = KETPartUsageLink.childitemcode     		\n")
	    // .append("	                    ) = KETPartUsageLink.ida3a5                              			\n");
	    if (isProd) { // 제품
		strSql.append("	    ORDER SIBLINGS BY  itemSeq) bom                                			\n");
	    } else { // 금형
		strSql.append("	    ORDER SIBLINGS BY  childItemCode) bom                         			\n");
	    }
	    strSql.append("	  , wtpartmaster itemMaster    													\n").append("	  , wtpart item   																			\n").append("	  , phasetemplate ph    																	\n")
		    .append("	  , phaselink pl      																		\n").append("	  WHERE bom.ida3b5 = itemMaster.ida2a2 (+)											\n")
		    .append("	  AND bom.versionitemcode = item.ida2a2                                				\n").append("	 and pl.ida3a5 = item.ida3a2state                                   						\n")
		    .append("	 and pl.ida3b5 = ph.ida2a2                                     							\n").append("	 and ph.phasestate = item.statestate        												\n")
		    .append("   order by seqno ");

	    pstmt = connection.prepareStatement(strSql.toString());
	    pstmt.setString(1, strPOid);

	    Kogger.debug(getClass(), ">>> 2. query : " + strSql.toString());
	    Kogger.debug(getClass(), ">>> pramete : " + strPOid);

	    rs = pstmt.executeQuery();

	    strSql.delete(0, strSql.length());

	    dataCount = 0;
	    BOMAssyComponent component = null;
	    Vector checkOutItemVec = new Vector();
	    String itemCodeStr = "";
	    String parentItemCodeStr = "";
	    String s_verSion = "";
	    String pOid = "";
	    String cOid = "";

	    query2 = " SELECT	substituteitem subItem				 		\n";
	    query2 += "         , quantity										\n";
	    query2 += "         , unit											\n";
	    query2 += "FROM  ketbomsubstitutemaster					\n";
	    query2 += "WHERE parentitem = ?								\n";
	    query2 += "  AND  childitem = ?								\n";
	    query2 += "  AND  versionitemcode = ?						\n";

	    String ketEcoNo = "";
	    pstmtSubstitute = connection.prepareStatement(query2);

	    while (rs.next()) {
		dataCount++;

		itemCodeStr = rs.getString("item") == null ? "" : rs.getString("item").trim();
		parentItemCodeStr = rs.getString("parentItem") == null ? "" : rs.getString("parentItem").trim();
		pOid = rs.getString("mid") == null ? "" : rs.getString("mid").trim();
		cOid = rs.getString("mmid") == null ? "" : rs.getString("mmid").trim();
		s_verSion = rs.getString("version") == null ? "" : rs.getString("version");
		if (dataCount == 1) {
		    ketEcoNo = getEcoNumber(parentItemCodeStr, s_verSion);
		}

		component = new BOMAssyComponent(itemCodeStr.trim());

		component.setSeqInt(new Integer(dataCount));
		component.setLevelInt(new Integer(rs.getString("lvl") == null ? "1" : rs.getString("lvl")));
		component.setDescStr(rs.getString("description") == null ? "" : rs.getString("description"));
		component.setParentItemCodeStr(parentItemCodeStr);
		component.setVersionStr(s_verSion);
		component.setStatusStr(rs.getString("status") == null ? "" : rs.getString("status"));
		component.setStatusKrStr(rs.getString("statusKr") == null ? "" : rs.getString("statusKr"));
		component.setUitStr(rs.getString("uit") == null ? "" : rs.getString("uit"));

		String qty = rs.getString("quantity") == null ? "" : rs.getString("quantity");
		if (qty.startsWith("."))
		    qty = "0" + qty;
		component.setQuantityDbl(new Double(qty));

		String boxQty = rs.getString("boxQuantity") == null ? "" : rs.getString("boxQuantity");
		if (boxQty.startsWith("."))
		    boxQty = "0" + boxQty;
		component.setBoxQuantityDbl(new Double(boxQty));
		component.setStartDate(rs.getString("startDate") == null ? "" : rs.getString("startDate"));
		component.setEndDate(rs.getString("endDate") == null ? "" : rs.getString("endDate"));
		component.setItemSeqInt(new Integer(rs.getString("itemSeq") == null ? "10" : rs.getString("itemSeq")));

		component.setMaterialStr(rs.getString("material") == null ? "" : rs.getString("material"));
		component.setHardnessFrom(rs.getString("hardnessfrom") == null ? "" : rs.getString("hardnessfrom"));
		component.setHardnessTo(rs.getString("hardnessto") == null ? "" : rs.getString("hardnessto"));
		component.setDesignDate(rs.getString("designdate") == null ? "" : rs.getString("designdate"));
		component.setEcoNoStr(ketEcoNo);
		// Added by MJOH, 2011-04-07
		component.setIBAPartType(strType);

		checkOutItemVec.addElement(itemCodeStr);

		Vector substituteVec = new Vector();

		try {
		    Kogger.debug(getClass(), ">>> Sql - query2 : " + query2);
		    Kogger.debug(getClass(), ">>> query2 pramete - parentItemCodeStr : " + parentItemCodeStr);
		    Kogger.debug(getClass(), ">>> query2 pramete - childItemCodeStr : " + itemCodeStr);
		    Kogger.debug(getClass(), ">>> query2 pramete - cOid : " + cOid);

		    pstmtSubstitute.clearParameters();
		    pstmtSubstitute.setString(1, parentItemCodeStr);
		    pstmtSubstitute.setString(2, itemCodeStr);
		    pstmtSubstitute.setString(3, cOid);

		    rsSubstitute = pstmtSubstitute.executeQuery();

		    substituteVec.removeAllElements();

		    while (rsSubstitute.next()) {
			BOMSubAssyComponent subComponent = new BOMSubAssyComponent();

			String subItemCodeStr = rsSubstitute.getString("subItem") == null ? "" : rsSubstitute.getString("subItem");
			subComponent.setSubstituteItemCodeStr(subItemCodeStr);

			String subAssyQty = (rsSubstitute.getString("quantity") == null ? "" : rsSubstitute.getString("quantity"));
			if (!Utility.isDouble(subAssyQty))
			    subAssyQty = "0";
			subComponent.setQuantityDbl(new Double(subAssyQty));
			subComponent.setParentItemCodeStr(parentItemCodeStr);
			subComponent.setChildItemCodeStr(itemCodeStr);
			subComponent.setUitStr(rsSubstitute.getString("unit") == null ? "" : rsSubstitute.getString("unit"));
			substituteVec.addElement(subComponent);
		    }

		    if (rsSubstitute != null) {
			rsSubstitute.close();
			rsSubstitute.close();
		    }

		} catch (Exception ex) {
		    Kogger.error(getClass(), ex);
		}

		if (substituteVec != null && substituteVec.size() > 0) {
		    component.setSubAssyComponent(substituteVec);
		}

		resultList.addElement(component);
	    }

	    Vector coworkerVec = new Vector();
	    String resultItemCode = "";
	    String checkOutItemCode = "";
	    coworkerVec = KETBomHelper.service.getCheckOuter(checkOutItemVec);
	    if (coworkerVec != null && coworkerVec.size() > 0) {
		for (int i = 0; i < resultList.size(); i++) {
		    resultItemCode = resultList.elementAt(i) == null ? "" : resultList.elementAt(i).toString().trim();
		    for (int j = 0; j < coworkerVec.size(); j++) {
			checkOutItemCode = coworkerVec.elementAt(j) == null ? "" : coworkerVec.elementAt(j).toString().trim().substring(0, coworkerVec.elementAt(j).toString().trim().indexOf("|"));
			if (resultItemCode.equals(checkOutItemCode)) {
			    BOMAssyComponent cmp = (BOMAssyComponent) resultList.elementAt(i);
			    cmp.setCheckOutStr(coworkerVec.elementAt(j).toString().substring(coworkerVec.elementAt(j).toString().indexOf("|") + 1));
			    resultList.set(i, cmp);
			    break;
			}
		    }
		}
	    }
	} catch (Exception ee) {
	    Kogger.error(getClass(), ee);
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs.close();
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt.close();
		}

		if (rsPOid != null) {
		    rsPOid.close();
		    rsPOid.close();
		}
		if (pstmtPOid != null) {
		    pstmtPOid.close();
		    pstmtPOid.close();
		}

		if (rsDesignator != null) {
		    rsDesignator.close();
		    rsDesignator.close();
		}
		if (pstmtDesignator != null) {
		    pstmtDesignator.close();
		    pstmtDesignator.close();
		}

		if (rsSubstitute != null) {
		    rsSubstitute.close();
		    rsSubstitute.close();
		}
		if (pstmtSubstitute != null) {
		    pstmtSubstitute.close();
		    pstmtSubstitute.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		MessageBox mbox = new MessageBox("DB Close Failure", "DB Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}
    }

    // ket사용 버젼별 역전개
    public void upwardExplosionCurrentBom(String childItemCode, String versionStr) throws Exception {
	
	DBConnectionManager res = null;
	Connection connection = null;

	PreparedStatement pstmt = null;
	PreparedStatement pstmtPOid = null;
	PreparedStatement pstmtRoot = null;

	ResultSet rs = null;
	ResultSet rsRoot = null;
	ResultSet rsPOid = null;

	String itemStr = childItemCode.trim();

	// Added by MJOH, 2011-04-07
	WTPart part = KETPartHelper.service.getPart(itemStr);
	String strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);

	try {
	    StringBuffer strSql = new StringBuffer();

	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    String strCOid = "";
	    strSql.append(" SELECT	  MAX (i.ida2a2) ID							\n").append("FROM   KETPartUsageLink b, wtpart i			\n").append("	WHERE  b.childItemCode = ?						\n")
		    .append("  AND   b.versionitemcode = i.ida2a2			\n").append("  AND   i.versionida2versioninfo = ?				\n").append("  AND   i.latestiterationinfo = '1'					\n");

	    pstmtPOid = connection.prepareStatement(strSql.toString());
	    pstmtPOid.setString(1, childItemCode);
	    pstmtPOid.setString(2, versionStr);
	    rsPOid = pstmtPOid.executeQuery();
	    strSql.delete(0, strSql.length());

	    if (rsPOid.next()) {
		strCOid = rsPOid.getString("ID") == null ? "" : rsPOid.getString("ID").trim();
	    }

	    Kogger.debug(getClass(), "------------------------------------------------>>>query : " + strSql.toString());
	    Kogger.debug(getClass(), "------------------------------------------------>>>itemStr : " + itemStr);
	    resultList.removeAllElements();

	    String ketEcoNo = StringUtil.checkNull(getUpwardEcoNo(strCOid)); // 역전개 econo를 가져온다.
	    // Root Node 의 Item Code 를 가지고 BOM 역전개
	    strSql.append(" SELECT																											\n").append("	bom.lvl lvl																										\n").append(",	bom.ida3b5 cid																							\n")
		    .append(",	ROWNUM seqno																						\n").append(",	bom.parentItemCode parentItem																		\n").append(",	bom.childItemCode item																				\n")
		    .append(",	nvl(itemMaster.NAME,'') description																	\n").append(",	item.versionida2versioninfo || '.' || item.iterationida2iterationinfo version						\n")
		    .append(",	item.statestate  status, 	ph.name statusKr															\n").append(",	bom.unit  uit																								\n")
		    .append(",	bom.quantity quantity																					\n").append(",	bom.boxQuantity boxQuantity																			\n").append(",	bom.itemSeq itemSeq																					\n")
		    .append(",	bom.startDate startDate																				\n").append(",	bom.endDate endDate																					\n").append("	,   bom.material																								\n")
		    .append("	,   bom.hardnessfrom																						\n").append("	,   bom.hardnessto																							\n").append("	,   bom.designdate																							\n")
		    .append("	FROM 																										\n").append("		(SELECT																									\n").append("			rownum seqno																						\n")
		    .append("		,	level lvl																								\n").append("		,	ida3b5																								\n").append("		,	versionitemcode																						\n")
		    .append("		,	ida3a5																									\n").append("		,	parentItemCode																						\n").append("		,	childItemCode																						\n")
		    .append("		,	unit																									\n").append("		,	quantity																								\n").append("		,	boxQuantity																							\n")
		    .append("		,	itemSeq																								\n").append("		,	startDate																								\n").append("		,	endDate																								\n")
		    .append("		,	material																								\n").append("		,	hardnessfrom																						\n").append("		,	hardnessto																							\n")
		    .append("		,	designdate																							\n").append("		FROM KETPartUsageLink																				\n").append("		START WITH (versionitemcode) IN (?)																\n")
		    .append("		CONNECT BY PRIOR ida3a5 = versionitemcode													\n").append("		) bom,																										\n")
		    .append("		wtpartmaster itemMaster, wtpart item, phasetemplate ph, phaselink pl						\n").append("	WHERE bom.ida3b5 = itemMaster.ida2a2 																\n")
		    .append("	AND bom.ida3a5 = item.ida2a2																			\n").append("	and pl.ida3a5 = item.ida3a2state																			\n")
		    .append("	and pl.ida3b5 = ph.ida2a2																					\n").append("	and ph.phasestate = item.statestate																		\n")
		    .append("	AND bom.ida3a5 = (SELECT MAX(i.ida2a2) FROM wtpart i, wtpartmaster m WHERE i.ida3masterreference = m.ida2a2 AND m.wtpartnumber = bom.parentItemCode)		\n")
		    .append("	ORDER BY bom.seqno ASC																				\n");

	    Kogger.debug(getClass(), "@@@@@ sql : " + strSql.toString());
	    Kogger.debug(getClass(), "@@@@@ value : " + strCOid);

	    pstmt = connection.prepareStatement(strSql.toString());
	    pstmt.setString(1, strCOid);

	    rs = pstmt.executeQuery();

	    strSql.delete(0, strSql.length());

	    int dataCount = 0;
	    BOMAssyComponent component = null;
	    Vector checkOutItemVec = new Vector();
	    String itemCodeStr = "";
	    String parentItemCodeStr = "";
	    String levelStr = "";
	    String cOid = "";

	    while (rs.next()) {
		dataCount++;

		itemCodeStr = rs.getString("parentItem") == null ? "" : rs.getString("parentItem").trim();
		parentItemCodeStr = rs.getString("item") == null ? "" : rs.getString("item").trim();

		levelStr = rs.getString("lvl") == null ? "1" : rs.getString("lvl");
		cOid = rs.getString("cid") == null ? "" : rs.getString("cid");

		// if(dataCount != 1)
		// {
		component = new BOMAssyComponent(itemCodeStr.trim());
		component.setSeqInt(new Integer(dataCount));
		component.setLevelInt(new Integer(Integer.parseInt(levelStr) - 1));
		component.setDescStr(rs.getString("description") == null ? "" : rs.getString("description"));
		component.setParentItemCodeStr(parentItemCodeStr);
		component.setVersionStr(rs.getString("version") == null ? "" : rs.getString("version"));
		component.setStatusStr(rs.getString("status") == null ? "" : rs.getString("status"));
		component.setStatusKrStr(rs.getString("statusKr") == null ? "" : rs.getString("statusKr"));
		component.setUitStr(rs.getString("uit") == null ? "" : rs.getString("uit"));

		String qty = rs.getString("quantity") == null ? "" : rs.getString("quantity");
		if (qty.startsWith("."))
		    qty = "0" + qty;
		component.setQuantityDbl(new Double(qty));

		String boxQty = rs.getString("boxQuantity") == null ? "" : rs.getString("boxQuantity");
		if (boxQty.startsWith("."))
		    boxQty = "0" + boxQty;
		component.setBoxQuantityDbl(new Double(boxQty));
		component.setStartDate(rs.getString("startDate") == null ? "" : rs.getString("startDate"));
		component.setEndDate(rs.getString("endDate") == null ? "" : rs.getString("endDate"));
		component.setItemSeqInt(new Integer(rs.getString("itemSeq") == null ? "10" : rs.getString("itemSeq")));
		component.setMaterialStr(rs.getString("material") == null ? "" : rs.getString("material"));
		component.setHardnessFrom(rs.getString("hardnessfrom") == null ? "" : rs.getString("hardnessfrom"));
		component.setHardnessTo(rs.getString("hardnessto") == null ? "" : rs.getString("hardnessto"));
		component.setDesignDate(rs.getString("designdate") == null ? "" : rs.getString("designdate"));
		component.setEcoNoStr(ketEcoNo);
		// Added by MJOH, 2011-04-07
		component.setIBAPartType(strType);

		checkOutItemVec.addElement(itemCodeStr);
		resultList.addElement(component);
		// }
	    }

	    strSql.append(" SELECT																							\n").append("		m.wtpartnumber itemCode															\n").append(" ,	m.name description																		\n")
		    .append(" ,	i.versionida2versioninfo || '.' || i.iterationida2iterationinfo version				\n").append(" ,	i.statestate	 status, ph.name statusKr												\n")
		    .append(" ,	m.defaultunit uit																			\n").append(" ,	to_char(sysdate, 'YYYY-MM-DD') startDate											\n")
		    .append("  FROM wtpart i, wtpartmaster m, phasetemplate ph, phaselink pl					\n").append("	WHERE m.wtpartnumber = ?																\n")
		    .append("	AND m.ida2a2 = i.ida3masterreference													\n").append("	and pl.ida3a5 = i.ida3a2state																\n").append("	and pl.ida3b5 = ph.ida2a2																	\n")
		    .append("	and ph.phasestate = i.statestate															\n").append("	AND i.latestiterationinfo = '1'																\n")
		    .append("	ORDER BY i.versionida2versioninfo, i.iterationida2iterationinfo DESC				\n");

	    pstmtRoot = connection.prepareStatement(strSql.toString());
	    pstmtRoot.setString(1, parentItemCodeStr);
	    rsRoot = pstmtRoot.executeQuery();

	    Kogger.debug(getClass(), "@@@@@33333  sql : " + strSql.toString());
	    Kogger.debug(getClass(), "@@@@@33333  parentItemCodeStr : " + parentItemCodeStr);

	    strSql.delete(0, strSql.length());

	    if (rsRoot.next()) {
		component = new BOMAssyComponent(parentItemCodeStr);
		component.setSeqInt(new Integer(dataCount + 1));
		component.setLevelInt(new Integer(Integer.parseInt(levelStr)));
		component.setDescStr(rsRoot.getString("description") == null ? "" : rsRoot.getString("description"));
		component.setParentItemCodeStr("");
		component.setVersionStr(rsRoot.getString("version") == null ? "" : rsRoot.getString("version"));
		component.setStatusStr(rsRoot.getString("status") == null ? "" : rsRoot.getString("status"));
		component.setStatusKrStr(rsRoot.getString("statusKr") == null ? "" : rsRoot.getString("statusKr"));
		component.setUitStr(rsRoot.getString("uit") == null ? "" : rsRoot.getString("uit"));
		component.setQuantityDbl(new Double(1.000));
		component.setBoxQuantityDbl(new Double(1.000));
		component.setStartDate(rsRoot.getString("startDate") == null ? "" : rsRoot.getString("startDate"));
		component.setItemSeqInt(new Integer("10"));
		component.setOpSeqInt(new Integer("1"));
		component.setEcoNoStr(ketEcoNo);
		// Added by MJOH, 2011-04-07
		component.setIBAPartType(strType);

		resultList.addElement(component);
	    }
	} catch (Exception ee) {
	    Kogger.error(getClass(), ee);
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs.close();
		}
		if (rsRoot != null) {
		    rsRoot.close();
		    rsRoot.close();
		}
		if (rsPOid != null) {
		    rsPOid.close();
		    rsPOid.close();
		}

		if (pstmt != null) {
		    pstmt.close();
		    pstmt.close();
		}
		if (pstmtRoot != null) {
		    pstmtRoot.close();
		    pstmtRoot.close();
		}
		if (pstmtPOid != null) {
		    pstmtPOid.close();
		    pstmtPOid.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		MessageBox mbox = new MessageBox("DB Close Failure", "DB Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}
    }

    public void IsExistItemPlm(Connection connection, Vector nodeVec) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	String itemCode = "";
	String query = "";

	try {
	    resultList.removeAllElements();

	    int thousandCnt = 1 + nodeVec.size() / 1000;

	    for (int cnt = 0; (cnt < thousandCnt) && (cnt * 1000 < nodeVec.size()); cnt++) {
		itemCode = "'" + nodeVec.elementAt(cnt * 1000);
		for (int i = (cnt * 1000 + 1); (i < (cnt * 1000 + 1000) && (i < nodeVec.size())); i++) {
		    itemCode = itemCode + "', '" + nodeVec.elementAt(i);
		}
		itemCode = itemCode + "'";

		query = " SELECT																				\n" + "		m.wtpartnumber itemCode													\n" + " FROM wtpart i, wtpartmaster m													\n"
		        + " WHERE m.ida2a2 = i.ida3masterreference										\n" + " AND m.wtpartnumber IN (" + itemCode + ")									\n" + " AND i.ida2a2 = (SELECT MAX (mid.ida2a2)										\n"
		        + "						   FROM wtpartmaster cid, wtpart mid						\n" + "						   WHERE cid.ida2a2 = mid.ida3masterreference			\n" + "						   AND	cid.wtpartnumber = m.wtpartnumber			\n"
		        + "						   AND mid.latestiterationinfo = '1'							\n" + " )																						\n";

		pstmt = connection.prepareStatement(query);
		rs = pstmt.executeQuery();

		while (rs.next()) {
		    resultList.add(rs.getString("itemCode") == null ? "" : rs.getString("itemCode").trim());
		}
		
		try {
		    rs.close();
		} catch (Exception e) {
		}
		try {
		    pstmt.close();
		} catch (Exception e) {
		}
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void IsObsoletedItem(Connection connection, Vector nodeVec) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	String itemCode = "";
	String query = "";

	try {
	    resultList.removeAllElements();
	    resultList2.removeAllElements();

	    int thousandCnt = 1 + nodeVec.size() / 1000;

	    for (int cnt = 0; (cnt < thousandCnt) && (cnt * 1000 < nodeVec.size()); cnt++) {
		itemCode = "'" + nodeVec.elementAt(cnt * 1000);
		for (int i = (cnt * 1000 + 1); (i < (cnt * 1000 + 1000) && (i < nodeVec.size())); i++) {
		    itemCode = itemCode + "', '" + nodeVec.elementAt(i);
		}
		itemCode = itemCode + "'";

		query = " SELECT	m.wtpartnumber itemCode									\n" + "	           , i.statestate state								\n" + "	           , i." + PartSpecEnum.SpIsDeleted.getColumnName()
		        + "	  itemStatus							\n" + " FROM   wtpart i  										\n" + "          , wtpartmaster m									\n" + " WHERE m.ida2a2 = i.ida3masterreference							\n"
		        + " AND m.wtpartnumber IN (" + itemCode + ")							\n" + " AND i.ida2a2 = (SELECT MAX (mid.ida2a2)							\n" + "						   FROM wtpartmaster cid, wtpart mid			\n"
		        + "						   WHERE cid.ida2a2 = mid.ida3masterreference		\n" + "						   AND	cid.wtpartnumber = m.wtpartnumber		\n" + " 					   AND mid.latestiterationinfo = '1'     			\n"
		        + "                     )			                         				\n";

		pstmt = connection.prepareStatement(query);

		rs = pstmt.executeQuery();

		while (rs.next()) {
		    resultList.add(rs.getString("itemCode") == null ? "" : rs.getString("itemCode").trim());
		    resultList2.add(rs.getString("itemStatus") == null ? "" : rs.getString("itemStatus"));
		}
		
		try {
		    rs.close();
		} catch (Exception e) {
		}
		try {
		    pstmt.close();
		} catch (Exception e) {
		}
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void IsSameSubstitute(Connection connection) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	String query = "";

	try {
	    resultList.removeAllElements();
	    resultList2.removeAllElements();

	    query = " SELECT												\n" + "		substituteitemcode itemCode					\n" + " ,	sequencenumber seq							\n" + " FROM ketbomsubstitute							\n" + " WHERE newbomcode = ?							\n"
		    + " AND substituteitemcode = childitemcode		\n";

	    pstmt = connection.prepareStatement(query);
	    pstmt.setString(1, BOMBasicInfoPool.getPublicModelName());

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		resultList.add(rs.getString("itemCode") == null ? "" : rs.getString("itemCode").trim());
		resultList2.add(rs.getString("seq") == null ? "" : rs.getString("seq").trim());
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    public boolean isModelCheck(Connection connection, String itemCode) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	boolean flag = false;
	String query = "";
	Vector itemId = new Vector();
	String itemMaster = "";

	try {
	    dataCount = 0;
	    query = " SELECT									\n" + "		DISTINCT parentItemCode 		\n" + " FROM KETPartUsageLink				\n" + " WHERE parentItemCode = ?			\n";

	    pstmt = connection.prepareStatement(query);
	    pstmt.setString(1, BOMBasicInfoPool.getPublicModelName());

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		flag = true;
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
	return flag;
    }

    public void IsExistInERP(Connection connection, Vector nodeVec) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	String itemCode = "";
	String query = "";

	try {
	    resultList.removeAllElements();

	    int thousandCnt = 1 + nodeVec.size() / 1000;

	    for (int cnt = 0; (cnt < thousandCnt) && (cnt * 1000 < nodeVec.size()); cnt++) {
		itemCode = "'" + nodeVec.elementAt(cnt * 1000);
		for (int i = (cnt * 1000 + 1); (i < (cnt * 1000 + 1000) && (i < nodeVec.size())); i++) {
		    itemCode = itemCode + "', '" + nodeVec.elementAt(i);
		}
		itemCode = itemCode + "'";

		query = " SELECT														\n" + "		DISTINCT b.parentItemCode itemCode				\n" + " FROM KETPartUsageLink b								\n" + " WHERE b.parentItemCode IN (" + itemCode + ")		\n";

		pstmt = connection.prepareStatement(query);

		rs = pstmt.executeQuery();

		while (rs.next()) {
		    resultList.add(rs.getString("itemCode") == null ? "" : rs.getString("itemCode").trim());
		}
		
		try {
		    rs.close();
		} catch (Exception e) {
		}
		try {
		    pstmt.close();
		} catch (Exception e) {
		}
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void IsExistItemMaster(Connection connection, Vector nodeVec) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	String itemCode = "";
	String query = "";

	try {
	    resultList.removeAllElements();

	    int thousandCnt = 1 + nodeVec.size() / 1000;

	    for (int cnt = 0; (cnt < thousandCnt) && (cnt * 1000 < nodeVec.size()); cnt++) {
		itemCode = "'" + nodeVec.elementAt(cnt * 1000);
		for (int i = (cnt * 1000 + 1); (i < (cnt * 1000 + 1000) && (i < nodeVec.size())); i++) {
		    itemCode = itemCode + "', '" + nodeVec.elementAt(i);
		}
		itemCode = itemCode + "'";

		query = " SELECT																			\n" + "		m.wtpartnumber itemCode												\n"
		        + " FROM wtpart i, wtpartmaster m												\n"
		        + " WHERE m.ida2a2 = i.ida3masterreference									\n"
		        // + " AND (i.transferflag = 'N' OR i.transferflag IS NULL)						\n"
		        + " AND m.wtpartnumber IN (" + itemCode + ")								\n" + " AND i.ida2a2 = (SELECT MAX (mid.ida2a2)									\n" + "						   FROM wtpartmaster cid, wtpart mid					\n"
		        + "						   WHERE cid.ida2a2 = mid.ida3masterreference		\n" + "						   AND	cid.wtpartnumber = m.wtpartnumber		\n" + " 					   AND mid.latestiterationinfo = '1'						\n"
		        + " )																					\n";

		pstmt = connection.prepareStatement(query);

		rs = pstmt.executeQuery();

		while (rs.next()) {
		    resultList.add(rs.getString("itemCode") == null ? "" : rs.getString("itemCode").trim());
		}
		
		try {
		    rs.close();
		} catch (Exception e) {
		}
		try {
		    pstmt.close();
		} catch (Exception e) {
		}
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void IsLifeCycleCompletionItem(Connection connection, Vector nodeVec) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	String itemCode = "";
	String query = "";

	try {
	    resultList.removeAllElements();

	    int thousandCnt = 1 + nodeVec.size() / 1000;

	    for (int cnt = 0; (cnt < thousandCnt) && (cnt * 1000 < nodeVec.size()); cnt++) {
		itemCode = "'" + nodeVec.elementAt(cnt * 1000);
		for (int i = (cnt * 1000 + 1); (i < (cnt * 1000 + 1000) && (i < nodeVec.size())); i++) {
		    itemCode = itemCode + "', '" + nodeVec.elementAt(i);
		}
		itemCode = itemCode + "'";

		query = " SELECT																						\n" + "		m.wtpartnumber itemCode															\n" + "	 ,	i.statestate state																			\n"
		        + " FROM wtpart i, wtpartmaster m															\n" + " WHERE m.ida2a2 = i.ida3masterreference												\n" + " AND i.statestate = 'Completed'															\n"
		        + " AND m.wtpartnumber IN (" + itemCode + ")											\n" + " AND i.ida2a2 = (SELECT MAX (mid.ida2a2)												\n" + "						   FROM wtpartmaster cid, wtpart mid								\n"
		        + "						   WHERE cid.ida2a2 = mid.ida3masterreference					\n" + "						   AND	cid.wtpartnumber = m.wtpartnumber					\n"
		        + "							AND mid.latestiterationinfo = '1'									\n" + " )																								\n";

		pstmt = connection.prepareStatement(query);

		rs = pstmt.executeQuery();

		while (rs.next()) {
		    resultList.add((rs.getString("itemCode") == null ? "" : rs.getString("itemCode").trim()) + "#" + (rs.getString("state") == null ? "" : rs.getString("state").trim()));
		}
		
		try {
		    rs.close();
		} catch (Exception e) {
		}
		try {
		    pstmt.close();
		} catch (Exception e) {
		}
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void IsLifeCycleCompletionDocument(Connection connection, Vector nodeVec) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	String itemCode = "";
	String query = "";

	try {
	    resultList.removeAllElements();

	    int thousandCnt = 1 + nodeVec.size() / 1000;

	    for (int cnt = 0; (cnt < thousandCnt) && (cnt * 1000 < nodeVec.size()); cnt++) {
		itemCode = "'" + nodeVec.elementAt(cnt * 1000);
		for (int i = (cnt * 1000 + 1); (i < (cnt * 1000 + 1000) && (i < nodeVec.size())); i++) {
		    itemCode = itemCode + "', '" + nodeVec.elementAt(i);
		}
		itemCode = itemCode + "'";

		query = " SELECT																						\n" + "		m.wtpartnumber itemCode															\n" + "	 ,	i.statestate state																			\n"
		        + " FROM wtpart i, wtpartmaster m															\n" + " WHERE m.ida2a2 = i.ida3masterreference												\n" + " AND i.statestate = 'Completed'															\n"
		        + " AND m.wtpartnumber IN (" + itemCode + ")											\n" + " AND i.ida2a2 = (SELECT MAX (mid.ida2a2)												\n" + "						   FROM wtpartmaster cid, wtpart mid								\n"
		        + "						   WHERE cid.ida2a2 = mid.ida3masterreference					\n" + "						   AND	cid.wtpartnumber = m.wtpartnumber					\n"
		        + "							AND mid.latestiterationinfo = '1'									\n" + " )																								\n";

		pstmt = connection.prepareStatement(query);

		rs = pstmt.executeQuery();

		while (rs.next()) {
		    resultList.add((rs.getString("itemCode") == null ? "" : rs.getString("itemCode").trim()) + "#" + (rs.getString("state") == null ? "" : rs.getString("state").trim()));
		}
		
		try {
		    rs.close();
		} catch (Exception e) {
		}
		try {
		    pstmt.close();
		} catch (Exception e) {
		}
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void getBOMECOTmpData(String bomEcoHeaderNumber, String bomEcoItemCode) throws Exception {
	DBConnectionManager res = null;
	Connection conn = null;
	PreparedStatement pstmtComponent = null;
	PreparedStatement pstmtSubstitute = null;

	ResultSet rsComponent = null;
	ResultSet rsSubstitute = null;

	Vector vecAllSubAssy = new Vector();

	// Kogger.debug(getClass(), "@@@@@@@@ bomEcoHeaderNumber : " + bomEcoHeaderNumber);
	// Kogger.debug(getClass(), "@@@@@@@@ bomEcoItemCode : " + bomEcoItemCode);
	try {
	    StringBuffer strSql = new StringBuffer();
	    String query = "";

	    res = DBConnectionManager.getInstance();
	    conn = res.getConnection(registry.getString("plm"));

	    resultList.removeAllElements();

	    strSql.append(" SELECT												\n").append("		parentItemCode								\n").append(" ,	childItemCode								\n").append(" ,	afterQuantity									\n").append(" ,	afterUnit										\n")
		    .append(" ,	afterMaterial									\n").append(" ,	afterHardnessFrom							\n").append(" ,	afterHardnessTo								\n").append(" ,	afterDesignDate								\n")
		    .append(" ,	afterStartDate								\n").append(" ,	afterEndDate									\n")
		    // .append(" ,	afterBomType								\n")
		    .append(" ,	sequenceNumber							\n").append(" ,	bomversion									\n").append("  FROM ketbomecotempcomponent			\n").append("	  WHERE ecoHeaderNumber = ?				\n")
		    .append("   AND    ecoItemCode = ?					\n");
	    // Kogger.debug(getClass(), "@@@@@@@@ strSql 1 : " + strSql);

	    pstmtComponent = conn.prepareStatement(strSql.toString());
	    pstmtComponent.setString(1, bomEcoHeaderNumber);
	    pstmtComponent.setString(2, bomEcoItemCode);

	    rsComponent = pstmtComponent.executeQuery();
	    strSql.delete(0, strSql.length());

	    strSql.append(" SELECT												\n").append("		parentItemCode								\n").append(" ,	childItemCode								\n").append(" ,	substituteItemCode							\n")
		    .append(" ,	afterQuantity									\n").append(" ,	afterUnit										\n").append(" ,	afterStartDate								\n").append(" ,	afterEndDate									\n")
		    .append(" ,	sequenceNumber							\n").append("  FROM ketbomecotempsubstitute			\n").append("	WHERE ecoHeaderNumber = ?				\n").append("	AND    ecoItemCode = ?						\n");
	    // Kogger.debug(getClass(), "@@@@@@@@ strSql 2 : " + strSql);

	    pstmtSubstitute = conn.prepareStatement(strSql.toString());
	    pstmtSubstitute.setString(1, bomEcoHeaderNumber);
	    pstmtSubstitute.setString(2, bomEcoItemCode);

	    rsSubstitute = pstmtSubstitute.executeQuery();

	    strSql.delete(0, strSql.length());

	    vecAllSubAssy.removeAllElements();

	    // Substitute Item 의 Item 정보 쿼리를 위한 Vector
	    BOMSubAssyComponent subComponent = null;

	    while (rsSubstitute.next()) {
		subComponent = new BOMSubAssyComponent();

		String subItemCodeStr = rsSubstitute.getString("substituteItemCode") == null ? "" : rsSubstitute.getString("substituteItemCode");
		subComponent.setSubstituteItemCodeStr(subItemCodeStr);
		String subAssyQty = (rsSubstitute.getString("afterQuantity") == null ? "" : rsSubstitute.getString("afterQuantity"));
		if (!Utility.isDouble(subAssyQty))
		    subAssyQty = "0";
		subComponent.setQuantityDbl(new Double(subAssyQty));
		subComponent.setStartDate(rsSubstitute.getString("afterStartDate") == null ? "" : rsSubstitute.getString("afterStartDate"));
		subComponent.setEndDate(rsSubstitute.getString("afterEndDate") == null ? "" : rsSubstitute.getString("afterEndDate"));
		subComponent.setParentItemCodeStr(rsSubstitute.getString("parentItemCode") == null ? "" : rsSubstitute.getString("parentItemCode").toString().trim());
		subComponent.setChildItemCodeStr(rsSubstitute.getString("childItemCode") == null ? "" : rsSubstitute.getString("childItemCode").toString().trim());
		subComponent.setSortOrderStr(rsSubstitute.getString("sequenceNumber") == null ? "" : rsSubstitute.getString("sequenceNumber"));

		vecAllSubAssy.addElement(subComponent);
	    }

	    int dataCount = 0;
	    BOMAssyComponent component = null;

	    while (rsComponent.next()) {
		String itemCodeStr = rsComponent.getString("childItemCode") == null ? "" : rsComponent.getString("childItemCode");
		String sortOrderStr = rsComponent.getString("sequenceNumber") == null ? "" : rsComponent.getString("sequenceNumber");

		Vector subAssyVec = new Vector();

		component = new BOMAssyComponent(itemCodeStr.trim());
		component.setSeqInt(new Integer(dataCount));
		String qty = rsComponent.getString("afterQuantity") == null ? "" : rsComponent.getString("afterQuantity");
		if (qty.startsWith("."))
		    qty = "0" + qty;
		component.setQuantityDbl(new Double(qty));
		component.setUitStr(rsComponent.getString("afterUnit") == null ? "" : rsComponent.getString("afterUnit"));
		component.setMaterialStr(rsComponent.getString("afterMaterial") == null ? "" : rsComponent.getString("afterMaterial"));
		component.setHardnessFrom(rsComponent.getString("afterHardnessFrom") == null ? "" : rsComponent.getString("afterHardnessFrom"));
		component.setHardnessTo(rsComponent.getString("afterHardnessTo") == null ? "" : rsComponent.getString("afterHardnessTo"));
		component.setDesignDate(rsComponent.getString("afterDesignDate") == null ? "" : rsComponent.getString("afterDesignDate"));
		component.setStartDate(rsComponent.getString("afterStartDate") == null ? "" : rsComponent.getString("afterStartDate"));
		component.setEndDate(rsComponent.getString("afterEndDate") == null ? "" : rsComponent.getString("afterEndDate"));
		// component.setBomType(rsComponent.getString("afterBomType") == null ? "" : rsComponent.getString("afterBomType"));
		component.setParentItemCodeStr(rsComponent.getString("parentItemCode") == null ? "" : rsComponent.getString("parentItemCode").toString().trim());
		component.setSortOrderStr(sortOrderStr);
		component.setVersionStr(rsComponent.getString("bomversion") == null ? "" : rsComponent.getString("bomversion").toString().trim());

		// Component 대상과 Remove 셋팅을 위해 레벨 정보가 필요함 (SaveBOMCmd.java)
		if (sortOrderStr.length() == 8) {
		    component.setLevelInt(1);
		} else {
		    component.setLevelInt(0);
		}

		subAssyVec.removeAllElements();

		for (int inx = 0; inx < vecAllSubAssy.size(); inx++) {
		    BOMSubAssyComponent tmpSubAssyComponent = (BOMSubAssyComponent) (vecAllSubAssy.elementAt(inx));
		    if (sortOrderStr.trim().equals(tmpSubAssyComponent.getSortOrderStr().trim()))
			subAssyVec.addElement(tmpSubAssyComponent);
		}

		if (subAssyVec != null && subAssyVec.size() > 0)
		    component.setSubAssyComponent(subAssyVec);

		resultList.addElement(component);
	    }
	} catch (Exception ee) {
	    Kogger.error(getClass(), ee);
	} finally {
	    try {
		if (rsComponent != null) {
		    rsComponent.close();
		    rsComponent.close();
		}
		if (rsSubstitute != null) {
		    rsSubstitute.close();
		    rsSubstitute.close();
		}

		if (pstmtComponent != null) {
		    pstmtComponent.close();
		    pstmtComponent.close();
		}
		if (pstmtSubstitute != null) {
		    pstmtSubstitute.close();
		    pstmtSubstitute.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		MessageBox mbox = new MessageBox("DB Close Failure", "DB Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }
	}
    }

    public void getItemVersionList(Connection connection, String itemCode, String strType) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer strSql = new StringBuffer();

	// String strType = "";
	String strClass = "";
	try {
	    // 부품 타입에 따라 ECO Class 가져옴
	    // WTPart part = KETPartHelper.service.getPart(itemCode);
	    // strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
	    if (PartUtil.isProductType(strType)) { // 제품인 경우
		strClass = "KETProdChangeOrder";
	    } else { // 금형인 경우
		strClass = "KETMoldChangeOrder";
	    }

	    // 부품의 버전 중에서 ECO에서 '승인완료' 상태인 BOM 버전만 가져옴
	    strSql.append(" SELECT	  distinct '0' version from tab															\n")
		    // 0 버전은 무조건 보여주기 위함
		    .append("  UNION																							\n").append("  SELECT  distinct i.versionida2versioninfo version											\n").append("  FROM   wtpart i																					\n")
		    .append("           , wtpartmaster m																		\n").append(" WHERE   m.wtpartnumber = '" + itemCode + "'													\n")
		    .append("    AND   m.ida2a2 = i.ida3masterreference													\n").append("    AND   i.latestiterationinfo = '1'  															\n")
		    .append("    AND   i.versionida2versioninfo IN (	 SELECT  h.bomversion								\n").append("                                                FROM   ketbomecoheader h					\n")
		    .append("                                                         , " + strClass + " t							\n")
		    .append("                                               WHERE   h.ecoheadernumber = t.ecoid		\n")
		    .append("                                                  AND   t.statestate = 'APPROVED' 				\n")
		    .append("                                                  AND   h.ecoitemcode = '" + itemCode + "' )	\n").append(" order by version		  																			\n");

	    Kogger.debug(getClass(), ">>>>> SQL : " + strSql);
	    Kogger.debug(getClass(), ">>>>> itemCode : " + itemCode);

	    resultList.removeAllElements();
	    pstmt = connection.prepareStatement(strSql.toString());

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		resultList.addElement(rs.getString("version") == null ? "" : rs.getString("version").trim());
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    strSql.delete(0, strSql.length());
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    // 해당 부품을 조회(정전개)할 수 있는 최신 버전 리턴
    public String getItemVersionList(String itemCode) throws Exception {
	DBConnectionManager res = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer strSql = new StringBuffer();

	String strVersion = "";
	String strType = "";
	String strClass = "";
	try {
	    // 부품 타입에 따라 ECO Class 가져옴
	    WTPart part = KETPartHelper.service.getPart(itemCode);
	    strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
	    if (PartUtil.isProductType(strType)) { // 제품인 경우
		strClass = "KETProdChangeOrder";
	    } else { // 금형인 경우
		strClass = "KETMoldChangeOrder";
	    }

	    res = DBConnectionManager.getInstance();
	    conn = res.getConnection(registry.getString("plm"));

	    // 부품의 버전 중에서 ECO에서 '승인완료' 상태인 BOM 버전만 가져옴
	    strSql.append(" SELECT	  distinct '0' version from tab															\n")
		    // 0 버전은 무조건 보여주기 위함
		    .append("  UNION																							\n").append("  SELECT  distinct i.versionida2versioninfo version											\n").append("  FROM   wtpart i																					\n")
		    .append("           , wtpartmaster m																		\n").append(" WHERE   m.wtpartnumber = '" + itemCode + "'													\n")
		    .append("    AND   m.ida2a2 = i.ida3masterreference													\n").append("    AND   i.latestiterationinfo = '1'  															\n")
		    .append("    AND   i.versionida2versioninfo IN (	 SELECT  h.bomversion								\n").append("                                                FROM   ketbomecoheader h					\n")
		    .append("                                                         , " + strClass + " t							\n")
		    .append("                                               WHERE   h.ecoheadernumber = t.ecoid		\n")
		    .append("                                                  AND   t.statestate = 'APPROVED' 				\n")
		    .append("                                                  AND   h.ecoitemcode = '" + itemCode + "' )	\n").append(" order by version desc  																			\n");

	    Kogger.debug(getClass(), ">>>>> SQL : " + strSql);
	    Kogger.debug(getClass(), ">>>>> itemCode : " + itemCode);

	    resultList.removeAllElements();
	    pstmt = conn.prepareStatement(strSql.toString());

	    rs = pstmt.executeQuery();
	    rs.next();
	    // 첫번째 버전이 가장 최신버전임
	    strVersion = rs.getString("version") == null ? "0" : rs.getString("version").trim();
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    strSql.delete(0, strSql.length());
	    if (res != null) {
		res.freeConnection(registry.getString("plm"), conn);
	    }
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}

	return strVersion;
    }

    public boolean isUsedItemCode(Connection connection, String rootItemCode) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer strSql = new StringBuffer();
	String itemCodeStr = "";
	boolean isItemCode = false;

	itemCodeStr = rootItemCode.trim();

	strSql.append(" SELECT COUNT(*)															\n").append("  FROM ketbomheader  h													\n").append(" WHERE h.transferflag != '4'													\n")
	        .append("   AND  h.newbomcode = ?													\n").append(" UNION ALL																	\n").append(" SELECT COUNT(*)																\n")
	        .append("  FROM ketbomheader h, ketbomcomponent c							\n").append(" WHERE h.transferflag != '4'													\n").append("   AND  h.newbomcode = c.newbomcode								\n")
	        .append("   AND  c.childitemcode = ?													\n").append(" UNION ALL																	\n").append(" SELECT COUNT(*)																\n")
	        .append("  FROM ketbomecoheader h, ketbomecocomponent c					\n").append(" WHERE h.transferflag != '4'													\n").append("   AND  h.ecoheadernumber = c.ecoheadernumber					\n")
	        .append("   AND  h.ecoitemcode = c.childitemcode									\n").append("   AND  c.childitemcode = ?													\n");

	try {
	    pstmt = connection.prepareStatement(strSql.toString());
	    pstmt.setString(1, itemCodeStr);
	    pstmt.setString(2, itemCodeStr);
	    pstmt.setString(3, itemCodeStr);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		if (rs.getInt(1) > 0) {
		    isItemCode = true;
		    break;
		}
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    strSql.delete(0, strSql.length());
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
	return isItemCode;
    }

    public void getItemDesc(Connection connection, String itemCode) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer strSql = new StringBuffer();
	String itemCodeStr = "";

	itemCodeStr = itemCode;

	strSql.append(" SELECT																						\n").append("		m.name descr																		\n").append("  FROM wtpart i, wtpartmaster m														\n")
	        // .append("	WHERE i.orgcode = ?																	\n")
	        .append("	WHERE m.wtpartnumber = ?															\n").append("	AND m.ida2a2 = i.ida3masterreference												\n")
	        .append("	ORDER BY i.versionida2versioninfo, i.iterationida2iterationinfo DESC			\n");

	try {
	    resultListStr = "";
	    pstmt = connection.prepareStatement(strSql.toString());
	    // pstmt.setString(1, BOMBasicInfoPool.getOrgCode());
	    pstmt.setString(1, itemCodeStr);

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		resultListStr = rs.getString("descr") == null ? "" : rs.getString("descr").trim();
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    strSql.delete(0, strSql.length());
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void isNotExistItemCode(Connection connection, Vector bomVec, Vector subVec) throws Exception {
	PreparedStatement pstmt = null;
	PreparedStatement pstmt1 = null;
	ResultSet rs = null;
	ResultSet rs1 = null;

	String itemCode = "";
	String subItemCode = "";
	String query = "";
	String subQuery = "";

	try {
	    resultList.removeAllElements();

	    int thousandCnt = 1 + bomVec.size() / 1000;
	    Vector tmpBomVec = new Vector();

	    for (int cnt = 0; (cnt < thousandCnt) && (cnt * 1000 < bomVec.size()); cnt++) {
		BOMAssyComponent component = (BOMAssyComponent) bomVec.elementAt(cnt * 1000);

		itemCode = "'" + component.getItemCodeStr().trim();
		for (int i = (cnt * 1000 + 1); (i < (cnt * 1000 + 1000) && (i < bomVec.size())); i++) {
		    BOMAssyComponent cmp = (BOMAssyComponent) bomVec.elementAt(i);
		    itemCode = itemCode + "', '" + cmp.getItemCodeStr().trim();
		}
		itemCode = itemCode + "'";

		query = " SELECT															\n" + "		m.wtpartnumber itemCode								\n" + " FROM wtpartmaster m											\n" + " WHERE m.wtpartnumber IN (" + itemCode + ")			\n";

		pstmt = connection.prepareStatement(query);

		rs = pstmt.executeQuery();

		while (rs.next()) {
		    tmpBomVec.add(rs.getString("itemCode") == null ? "" : rs.getString("itemCode").trim());
		}
		
		try {
		    rs.close();
		} catch (Exception e) {
		}
		try {
		    pstmt.close();
		} catch (Exception e) {
		}
	    }

	    String iStr = "";
	    for (int i = 0; i < bomVec.size(); i++) {
		iStr = bomVec.elementAt(i).toString().trim();
		if (tmpBomVec.toString().indexOf(iStr) == -1) {
		    resultList.addElement(iStr);
		}
	    }

	    resultList2.removeAllElements();

	    int subThousandCnt = 1 + subVec.size() / 1000;
	    Vector tmpSubVec = new Vector();

	    for (int cnt = 0; (cnt < subThousandCnt) && (cnt * 1000 < subVec.size()); cnt++) {
		BOMSubAssyComponent subComponent = (BOMSubAssyComponent) subVec.elementAt(cnt * 1000);

		subItemCode = "'" + subComponent.getSubstituteItemCodeStr().trim();
		for (int i = (cnt * 1000 + 1); (i < (cnt * 1000 + 1000) && (i < subVec.size())); i++) {
		    BOMSubAssyComponent subCmp = (BOMSubAssyComponent) bomVec.elementAt(i);
		    subItemCode = subItemCode + "', '" + subCmp.getSubstituteItemCodeStr().trim();
		}
		subItemCode = subItemCode + "'";

		subQuery = " SELECT																	\n" + "		m.wtpartnumber itemCode										\n" + " FROM wtpartmaster m													\n" + " WHERE m.wtpartnumber IN (" + subItemCode + ")				\n";

		pstmt1 = connection.prepareStatement(subQuery);

		rs1 = pstmt1.executeQuery();

		while (rs1.next()) {
		    tmpSubVec.add(rs1.getString("itemCode") == null ? "" : rs1.getString("itemCode").trim());
		}
		
		try {
		    rs1.close();
		} catch (Exception e) {
		}
		try {
		    pstmt1.close();
		} catch (Exception e) {
		}
	    }

	    String sStr = "";
	    for (int i = 0; i < subVec.size(); i++) {
		sStr = subVec.elementAt(i).toString().trim();
		if (tmpSubVec.toString().indexOf(sStr) == -1) {
		    resultList2.addElement(sStr);
		}
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		rs1.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt1.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void selectItem(Connection connection, String itemCode) throws Exception {
	String query = " select header.newbomcode model,												\n " + "			list.sequenceNumber seq												\n " + " from ketbomheader header,													\n "
	        + "		ketbomcomponent list														\n " + " where																				\n " + "		list.childItemCode = ?														\n " + " 		and list.newFlag = 'NEW'													\n "
	        + " 		and  (list.attribute1 is null or list.attribute1 = 'BASIC')					\n " + "		and header.newbomcode = list.newbomcode							\n "
	        + " 	and header.statestate not in ('Completed','Canceled')						\n " + " 	and header.orgCode = ?														\n " + "	and rownum = 1																	";

	PreparedStatement pstmt = null;
	ResultSet rs = null;

	dataCount = 0;

	try {
	    pstmt = connection.prepareStatement(query);
	    pstmt.setString(1, itemCode);
	    pstmt.setString(2, BOMBasicInfoPool.getOrgCode());

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		dataCount++;
		basicModel = rs.getString("model");
		basicSeq = rs.getString("seq");
	    }
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void selectCommonItem(Connection connection, String itemCode) throws Exception {
	String query = " select header.newbomcode	model,												\n " + "			list.sequenceNumber  seq													\n " + " from ketbomheader header,														\n "
	        + "		ketbomcomponent list															\n " + " where																					\n" + "		list.childItemCode = ?															\n " + " 		and list.newFlag = 'NEW'														\n "
	        + " 		and  (list.attribute1 is not null and list.attribute1 <> 'BASIC')			\n " + "		and header.newbomcode = list.newbomcode								\n "
	        + " 	and header.statestate not in ('Completed','Canceled')							\n " + "	and rownum = 1																			";

	PreparedStatement pstmt = null;
	ResultSet rs = null;

	dataCount = 0;

	try {
	    pstmt = connection.prepareStatement(query);
	    pstmt.setString(1, itemCode);

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		dataCount++;
		basicModel = rs.getString("model");
		basicSeq = rs.getString("seq");
	    }
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void IsExistInMaster(Connection connection, String itemCode) throws Exception {
	String query = " SELECT																						\n " + "		bomflag flag																			\n " + " FROM wtpart i, wtpartmaster m															\n "
	        + " WHERE i.ida3masterreference = m.ida2a2												\n " + " AND i.latestiterationinfo = '1'															\n " + " AND m.wtpartnumber = ?																\n "
	        + " ORDER BY i.versionida2versioninfo, i.iterationida2iterationinfo DESC	";

	PreparedStatement pstmt = null;
	ResultSet rs = null;

	dataCount = 0;
	resultListStr = "";

	try {
	    pstmt = connection.prepareStatement(query);
	    pstmt.setString(1, itemCode);

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		if ((rs.getString("flag") == null ? "" : rs.getString("flag").trim()).equals("Y")) {
		    resultListStr = "Y";
		}
	    }
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    // Added by MJOH, 2007-03-13
    public String getRootEPMDocumentOid(String itemCode) throws Exception {
	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String className = "";
	String epmOid = "";

	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    String query = "SELECT DISTINCT e.classnamea2a2	cname							\n" + "           , e.ida2a2   ida											\n" + "	FROM EPMDOCUMENT e											\n"
		    + "        , EPMDOCUMENTMASTER em								\n" + "        , EPMDESCRIBELINK d										\n" + "        , wtpart i														\n" + "        , WTPARTMASTER m										\n"
		    + "WHERE  d.IDA3B5 = e.ida2a2										\n" + "     AND d.IDA3A5 = i.ida2a2										\n" + "     AND m.ida2a2 = i.IDA3MASTERREFERENCE				\n"
		    + "     AND em.ida2a2 = e.IDA3MASTERREFERENCE				\n" + "     AND m.wtpartnumber = ?									\n" + "     AND e.latestiterationinfo = 1									\n"
		    + "     AND e.statecheckoutinfo IN ( 'c/i', 'wrk' )					\n" + "     AND em.authoringapplication = 'PROE'						\n" // PORE / ACAD / OTHER
		    + "     AND em.doctype = 'CADASSEMBLY'							\n" // CADASSEMBLY / CADCOMPONENT / CADDRAWING
		    + "ORDER BY e.ida2a2													\n";

	    pstmt = connection.prepareStatement(query);
	    pstmt.setString(1, itemCode);

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		className = rs.getString("cname") == null ? "" : rs.getString("cname").trim();
		epmOid = rs.getString("ida") == null ? "" : rs.getString("ida").trim();

		epmOid = className + ":" + epmOid;
		Kogger.debug(getClass(), "========> Root EPMDocument OID : " + epmOid);
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    throw ex;
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		}

		if (pstmt != null) {
		    pstmt.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}

	return epmOid;
    }

    // Added by MJOH, 2007-03-13
    // CAD Structure Loading 시 사용함.
    public Hashtable getExistBOMMasterInfo(String pOid, String cOid) throws Exception {
	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement stmtMaster = null;
	ResultSet rsMaster = null;
	Hashtable returnHash = new Hashtable();

	StringBuffer queryCmp = new StringBuffer();

	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    // Master 에 존재하는 Item 이라면 Master 정보를 가져와 Display 한다.
	    queryCmp.append(" SELECT bomMaster.Quantity qty	");
	    queryCmp.append(", bomMaster.Supplytype supplyType ");
	    queryCmp.append(", decode(bomMaster.costRollup, '1', 'Y', '2', 'N', 'N') costRollup	");
	    queryCmp.append(", bomMaster.Startdate startDate ");
	    queryCmp.append(", bomMaster.Enddate endDate ");
	    queryCmp.append(", bomMaster.Itemseq itemSeq ");
	    queryCmp.append(" bomMaster.Operationseq opSeq ");
	    queryCmp.append(" FROM KETPartUsageLink bomMaster ");
	    queryCmp.append(" WHERE bomMaster.ida3a5 = ? AND bomMaster.attribute1 = ? ");

	    stmtMaster = connection.prepareStatement(queryCmp.toString());
	    stmtMaster.setString(1, pOid);
	    stmtMaster.setString(2, cOid);
	    rsMaster = stmtMaster.executeQuery();

	    if (rsMaster.next()) {
		String qty = rsMaster.getString("qty") == null ? "" : rsMaster.getString("qty");
		if (qty.startsWith(".")) {
		    qty = "0" + qty;
		}

		returnHash.put("qty", qty);
		returnHash.put("supplyType", rsMaster.getString("supplyType") == null ? "" : rsMaster.getString("supplyType"));
		returnHash.put("costRollup", rsMaster.getString("costRollup") == null ? "" : rsMaster.getString("costRollup"));
		returnHash.put("startDate", rsMaster.getString("startDate") == null ? "" : rsMaster.getString("startDate"));
		returnHash.put("endDate", rsMaster.getString("endDate") == null ? "" : rsMaster.getString("endDate"));
		returnHash.put("itemSeq", rsMaster.getString("itemSeq") == null ? "10" : rsMaster.getString("itemSeq"));
		returnHash.put("opSeq", rsMaster.getString("opSeq") == null ? "1" : rsMaster.getString("opSeq"));
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	} finally {
	    try {
		if (rsMaster != null) {
		    rsMaster.close();
		}

		if (stmtMaster != null) {
		    stmtMaster.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}

	return returnHash;
    }

    // Added by MJOH, 2007-03-13
    public String getRootNodeInfo(String itemCode) throws Exception {
	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement stmtBom = null;
	ResultSet rsBom = null;
	String returnStr = "";

	StringBuffer querySql = new StringBuffer();

	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    querySql.append(" SELECT bomflag flag ");
	    querySql.append(" FROM wtpart i, wtpartmaster m ");
	    querySql.append(" WHERE i.ida3masterreference = m.ida2a2	 ");
	    querySql.append(" AND i.latestiterationinfo = '1'	");
	    querySql.append(" AND m.wtpartnumber = ? ");
	    querySql.append(" ORDER BY i.versionida2versioninfo, i.iterationida2iterationinfo DESC");

	    stmtBom = connection.prepareStatement(querySql.toString());
	    stmtBom.setString(1, itemCode);
	    rsBom = stmtBom.executeQuery();

	    if (rsBom.next()) {
		if (!(rsBom.getString("flag") == null ? "" : rsBom.getString("flag").trim()).equals("Y")) {
		    returnStr = "NEW";
		}
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	} finally {
	    try {
		if (rsBom != null) {
		    rsBom.close();
		}

		if (stmtBom != null) {
		    stmtBom.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}

	return returnStr;
    }

    // Added by MJOH, 2007-03-13
    // CAD Structure Loading 시 EPM Document의 Quantity를 설정해주는 함수
    public int getQuantity(String parentEPMOid, String childEPMOid) throws Exception {
	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	int returnQuantity = 0;

	String querySql = "";

	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    querySql = "SELECT COUNT(*) from epmmemberlink t WHERE t.ida3a5 = ? AND t.ida3b5 = ?";

	    stmt = connection.prepareStatement(querySql);
	    stmt.setString(1, parentEPMOid);
	    stmt.setString(2, childEPMOid);

	    rs = stmt.executeQuery();

	    if (rs.next()) {
		returnQuantity = rs.getInt(1);
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		}

		if (stmt != null) {
		    stmt.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}

	return returnQuantity;
    }

    public Vector getResultListVec() {
	return resultList;
    }

    public Vector getResultListVec2() {
	return resultList2;
    }

    public BOMAssyComponent getResultListComponent() {
	return bomComponent;
    }

    public long getDataCount() {
	return dataCount;
    }

    public String getResultListString() {
	return resultListStr;
    }

    public Hashtable getSupplyType() {
	return supplyTypeHash;
    }

    public Hashtable getProdReason() {
	return reasonProdHash;
    }

    public Hashtable getMoldReason() {
	return reasonMoldHash;
    }

    public Hashtable getReasonDetail() {
	return reasonDetailHash;
    }

    public String getBasicModel() {
	return basicModel;
    }

    public String getBasicSeq() {
	return basicSeq;
    }

    public boolean isCommonAssy() {
	if (dataCount == 0)
	    return false;
	else
	    return true;
    }

    public void downwardExplosionCurrentBom(String childItemCode, String changeActivityCode, String versionStr, String optionStr) throws Exception {
	DBConnectionManager res = null;
	Connection connection = null;

	PreparedStatement pstmt = null;
	PreparedStatement pstmtPOid = null;
	PreparedStatement pstmtDesignator = null;
	PreparedStatement pstmtSubstitute = null;

	ResultSet rs = null;
	ResultSet rsPOid = null;
	ResultSet rsDesignator = null;
	ResultSet rsSubstitute = null;

	String itemStr = childItemCode.trim();

	try {
	    StringBuffer strSql = new StringBuffer();
	    String query1 = "";
	    String query2 = "";

	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    String itemIda2a2 = "";
	    String query = " SELECT 										\n" + "		MAX (ida3a5) pid						\n" + " FROM KETPartUsageLink b, wtpart i		\n" + " WHERE b.parentItemcode = ?			\n" + " AND b.ida3a5 = i.ida2a2					\n"
		    + " AND i.versionida2versioninfo = ?		\n" + " AND i.latestiterationinfo = '1'				\n";

	    pstmtPOid = connection.prepareStatement(query);
	    pstmtPOid.setString(1, itemStr);
	    pstmtPOid.setString(2, versionStr);

	    rsPOid = pstmtPOid.executeQuery();

	    if (rsPOid.next()) {
		itemIda2a2 = rsPOid.getString("pid") == null ? "" : rsPOid.getString("pid").trim();
	    }

	    resultList.removeAllElements();

	    // Root Node 의 Item Code 를 가지고 BOM 하향전개
	    strSql.append(" SELECT																											\n").append("	bom.lvl lvl																										\n").append(",	bom.ida3a5 mid																							\n")
		    .append(",	bom.ida3b5 mmid																						\n").append(",	bom.parentItemCode parentItem																		\n").append(",	bom.childItemCode item																				\n")
		    .append(",	nvl(itemMaster.NAME,'') description																	\n").append(",	item.bomflag flag																						\n")
		    .append(",	item.versionida2versioninfo || '.' || item.iterationida2iterationinfo version						\n").append(",	item.itemstatus status																					\n")
		    .append(",	item.useritemtype uit																					\n").append(",	item.uom uom																							\n")
		    .append(",	decode(bom.costRollup, '1', 'Y', '2', 'N', 'N') costRollup											\n").append(",	item.econo ecoNo																						\n")
		    .append(",	bom.quantity quantity																					\n").append(",	bom.supplyType supplyType																			\n").append(",	bom.itemSeq itemSeq																					\n")
		    .append(",	bom.operationSeq opSeq																				\n").append(",	bom.startDate startDate																				\n").append(",	bom.endDate endDate																					\n")
		    .append("	FROM 																										\n").append("		(SELECT																									\n").append("			rownum seqno																						\n")
		    .append("		,	level lvl																								\n").append("		,	ida3b5																								\n").append("		,	attribute1																								\n")
		    .append("		,	ida3a5																									\n").append("		,	parentItemCode																						\n").append("		,	childItemCode																						\n")
		    .append("		,	quantity																								\n").append("		,	itemSeq																								\n").append("		,	operationSeq																						\n")
		    .append("		,	startDate																								\n").append("		,	endDate																								\n").append("		,	supplyType																							\n")
		    .append("		,	costRollup																							\n").append("		FROM KETPartUsageLink																				\n").append("		START WITH (ida3a5) IN (?)																			\n");
	    strSql.append("					CONNECT BY PRIOR ( 																\n");
	    strSql.append("							SELECT																		\n");
	    strSql.append("							MAX(ida3a5)																	\n");
	    strSql.append("							FROM KETPartUsageLink b												\n");
	    strSql.append("							WHERE b.parentItemcode = KETPartUsageLink.childitemcode		\n");
	    strSql.append("                    ) = KETPartUsageLink.ida3a5 													\n");
	    strSql.append("		ORDER BY seqno, itemSeq) bom,																\n").append("		wtpartmaster itemMaster, wtpart item														\n")
		    .append("	WHERE bom.ida3b5 = itemMaster.ida2a2 (+)													\n").append("	AND bom.attribute1 = item.ida2a2																\n").append("   order by seqno ");

	    pstmt = connection.prepareStatement(strSql.toString());
	    pstmt.setString(1, itemIda2a2);

	    rs = pstmt.executeQuery();

	    strSql.delete(0, strSql.length());

	    int dataCount = 0;
	    BOMAssyComponent component = null;
	    Vector checkOutItemVec = new Vector();
	    String itemCodeStr = "";
	    String parentItemCodeStr = "";
	    String pOid = "";
	    String cOid = "";

	    query2 = " SELECT																						\n";
	    query2 += "		m.wtpartnumber subItem															\n";
	    query2 += "	,	sub.quantity qty																		\n";
	    query2 += "	,	sub.supplytype stype																\n";
	    query2 += "	,	sub.startdate stdt																	\n";
	    query2 += "	,	m.NAME descr																		\n";
	    query2 += "	,	i.itemstatus status																	\n";
	    query2 += "	,	i.useritemtype uit																	\n";
	    query2 += "	,	i.uom um																				\n";
	    query2 += "	FROM																						\n";
	    query2 += "		(																						\n";
	    query2 += "		SELECT																				\n";
	    query2 += "			substituteMaster.Substituteitem substituteItemCode						\n";
	    query2 += "		,	substituteMaster.Quantity quantity											\n";
	    query2 += "		,	substituteMaster.SupplyType supplyType									\n";
	    query2 += "		,	substituteMaster.Startdate startDate											\n";
	    query2 += "		,	substituteMaster.Parentitem poid												\n";
	    query2 += "		FROM																					\n";
	    query2 += "			ketbomsubstitutemaster substituteMaster									\n";
	    query2 += "		WHERE substituteMaster.Attribute1 = ?											\n";
	    query2 += "		AND substituteMaster.Childitem = ?												\n";
	    query2 += "		) sub,																					\n";
	    query2 += "	wtpart i, wtpartmaster m																\n";
	    query2 += "	WHERE sub.substituteItemCode = m.Ida2a2										\n";
	    query2 += "	AND i.ida3masterreference = m.ida2a2												\n";
	    query2 += "	AND sub.poid =																			\n";
	    query2 += "						(																		\n";
	    query2 += "							SELECT MAX (Parentitem)										\n";
	    query2 += "							FROM ketbomsubstitutemaster								\n";
	    query2 += "							WHERE Attribute1 = ?											\n";
	    query2 += "						)																		\n";

	    pstmtSubstitute = connection.prepareStatement(query2);

	    while (rs.next()) {
		dataCount++;

		itemCodeStr = rs.getString("item") == null ? "" : rs.getString("item").trim();
		parentItemCodeStr = rs.getString("parentItem") == null ? "" : rs.getString("parentItem").trim();

		pOid = rs.getString("mid") == null ? "" : rs.getString("mid").trim();
		cOid = rs.getString("mmid") == null ? "" : rs.getString("mmid").trim();

		component = new BOMAssyComponent(itemCodeStr.trim());
		component.setSeqInt(new Integer(dataCount));
		component.setLevelInt(new Integer(rs.getString("lvl") == null ? "1" : rs.getString("lvl")));
		component.setDescStr(rs.getString("description") == null ? "" : rs.getString("description"));
		component.setParentItemCodeStr(parentItemCodeStr);
		component.setVersionStr(rs.getString("version") == null ? "" : rs.getString("version"));
		component.setStatusStr(rs.getString("status") == null ? "" : rs.getString("status"));
		component.setUitStr(rs.getString("uit") == null ? "" : rs.getString("uit"));
		component.setUomStr(rs.getString("uom") == null ? "" : rs.getString("uom"));

		component.setEcoNoStr(rs.getString("ecoNo") == null ? "" : rs.getString("ecoNo"));
		String qty = rs.getString("quantity") == null ? "" : rs.getString("quantity");
		if (qty.startsWith("."))
		    qty = "0" + qty;
		component.setQuantityDbl(new Double(qty));
		component.setSupplyTypeStr(rs.getString("supplyType") == null ? "" : rs.getString("supplyType"));
		component.setCostRollUpStr(rs.getString("costRollup") == null ? "" : rs.getString("costRollup"));
		component.setStartDate(rs.getString("startDate") == null ? "" : rs.getString("startDate"));
		component.setEndDate(rs.getString("endDate") == null ? "" : rs.getString("endDate"));
		component.setItemSeqInt(new Integer(rs.getString("itemSeq") == null ? "10" : rs.getString("itemSeq")));
		component.setOpSeqInt(new Integer(rs.getString("opSeq") == null ? "1" : rs.getString("opSeq")));

		checkOutItemVec.addElement(itemCodeStr);

		Vector designatorVec = new Vector();
		Vector substituteVec = new Vector();

		try {
		    pstmtSubstitute.clearParameters();
		    pstmtSubstitute.setString(1, parentItemCodeStr);
		    pstmtSubstitute.setString(2, cOid);
		    pstmtSubstitute.setString(3, parentItemCodeStr);

		    rsSubstitute = pstmtSubstitute.executeQuery();

		    substituteVec.removeAllElements();

		    while (rsSubstitute.next()) {
			BOMSubAssyComponent subComponent = new BOMSubAssyComponent();
			String subItemCodeStr = rsSubstitute.getString("subItem") == null ? "" : rsSubstitute.getString("subItem");
			subComponent.setSubstituteItemCodeStr(subItemCodeStr);
			String subAssyQty = (rsSubstitute.getString("qty") == null ? "" : rsSubstitute.getString("qty"));
			if (!Utility.isDouble(subAssyQty))
			    subAssyQty = "0";
			subComponent.setQuantityDbl(new Double(subAssyQty));
			subComponent.setSupplyTypeStr(rsSubstitute.getString("stype") == null ? "" : rsSubstitute.getString("stype"));
			subComponent.setStartDate(rsSubstitute.getString("stdt") == null ? "" : rsSubstitute.getString("stdt"));
			subComponent.setParentItemCodeStr(parentItemCodeStr);
			subComponent.setChildItemCodeStr(itemCodeStr);
			subComponent.setDescStr(rsSubstitute.getString("descr") == null ? "" : rsSubstitute.getString("descr"));
			subComponent.setStatusStr(rsSubstitute.getString("status") == null ? "" : rsSubstitute.getString("status"));
			subComponent.setUitStr(rsSubstitute.getString("uit") == null ? "" : rsSubstitute.getString("uit"));
			subComponent.setUomStr(rsSubstitute.getString("um") == null ? "" : rsSubstitute.getString("um"));

			substituteVec.addElement(subComponent);
		    }
		    
		    try{
		    if (rsSubstitute != null) {
			rsSubstitute.close();
		    }
		    }catch(Exception e){
		    }
		    
		} catch (Exception ex) {
		    Kogger.error(getClass(), ex);
		}

		if (substituteVec != null && substituteVec.size() > 0) {
		    component.setSubAssyComponent(substituteVec);
		}

		resultList.addElement(component);
	    }

	    if (optionStr.equalsIgnoreCase("Current")) {
		Vector coworkerVec = new Vector();
		String resultItemCode = "";
		String checkOutItemCode = "";
		coworkerVec = KETBomHelper.service.getCheckOuter(checkOutItemVec);
		if (coworkerVec != null && coworkerVec.size() > 0) {
		    for (int i = 0; i < resultList.size(); i++) {
			resultItemCode = resultList.elementAt(i) == null ? "" : resultList.elementAt(i).toString().trim();
			for (int j = 0; j < coworkerVec.size(); j++) {
			    checkOutItemCode = coworkerVec.elementAt(j) == null ? "" : coworkerVec.elementAt(j).toString().trim().substring(0, coworkerVec.elementAt(j).toString().trim().indexOf("|"));
			    if (resultItemCode.equals(checkOutItemCode)) {
				BOMAssyComponent cmp = (BOMAssyComponent) resultList.elementAt(i);
				cmp.setCheckOutStr(coworkerVec.elementAt(j).toString().substring(coworkerVec.elementAt(j).toString().indexOf("|") + 1));
				resultList.set(i, cmp);
				break;
			    }
			}
		    }
		}
	    }
	} catch (Exception ee) {
	    Kogger.error(getClass(), ee);
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs.close();
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt.close();
		}

		if (rsPOid != null) {
		    rsPOid.close();
		    rsPOid.close();
		}
		if (pstmtPOid != null) {
		    pstmtPOid.close();
		    pstmtPOid.close();
		}

		if (rsDesignator != null) {
		    rsDesignator.close();
		    rsDesignator.close();
		}
		if (pstmtDesignator != null) {
		    pstmtDesignator.close();
		    pstmtDesignator.close();
		}

		if (rsSubstitute != null) {
		    rsSubstitute.close();
		    rsSubstitute.close();
		}
		if (pstmtSubstitute != null) {
		    pstmtSubstitute.close();
		    pstmtSubstitute.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		MessageBox mbox = new MessageBox("DB Close Failure", "DB Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}
    }

    public void upwardExplosionCurrentBom(String childItemCode, String versionStr, String optionStr) throws Exception {
	// optionStr : Current(현재) / All(현재+미래)
	DBConnectionManager res = null;
	Connection connection = null;

	PreparedStatement pstmt = null;
	PreparedStatement pstmtPOid = null;
	PreparedStatement pstmtRoot = null;

	ResultSet rs = null;
	ResultSet rsRoot = null;
	ResultSet rsPOid = null;

	String itemStr = childItemCode.trim();

	try {
	    StringBuffer strSql = new StringBuffer();
	    String query = "";

	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    // ******** Version 별 이력 조회 시 Version 에 해당하는 Item 의 Ida2a2 쿼리 ************
	    String itemIda2a2 = "";
	    strSql.append(" SELECT												\n").append("		MAX (i.ida2a2) ID							\n").append("  FROM KETPartUsageLink b, wtpart i		\n").append("	WHERE b.childItemCode = ?					\n")
		    .append("	AND b.attribute1 = i.ida2a2					\n").append("	AND i.versionida2versioninfo = ?				\n").append("	AND i.latestiterationinfo = '1'					\n");

	    pstmtPOid = connection.prepareStatement(strSql.toString());
	    pstmtPOid.setString(1, childItemCode);
	    pstmtPOid.setString(1, versionStr);

	    rsPOid = pstmtPOid.executeQuery();

	    strSql.delete(0, strSql.length());

	    if (rsPOid.next()) {
		itemIda2a2 = rsPOid.getString("code") == null ? "" : rsPOid.getString("code").trim();
	    }
	    // ******** Version 별 이력 조회 시 Version 에 해당하는 Item 의 Ida2a2 쿼리 ************

	    resultList.removeAllElements();

	    // Root Node 의 Item Code 를 가지고 BOM 역전개
	    strSql.append(" SELECT																											\n")
		    .append("	bom.lvl lvl																										\n")
		    .append(",	ROWNUM seqno																						\n")
		    .append(",	bom.parentItemCode item																				\n")
		    .append(",	bom.childItemCode parentItem																		\n")
		    .append(",	nvl(itemMaster.NAME,'') description																	\n")
		    .append(",	item.bomflag flag																						\n")
		    .append(",	item.versionida2versioninfo || '.' || item.iterationida2iterationinfo version						\n")
		    .append(",	item.itemstatus status																					\n")
		    .append(",	item.useritemtype uit																					\n")
		    .append(",	item.uom uom																							\n")
		    .append(",	decode(bom.costRollup, '1', 'Y', '2', 'N', 'N') costRollup											\n")
		    .append(",	item.econo ecoNo																						\n")
		    .append(",	bom.quantity quantity																					\n")
		    .append(",	bom.supplyType supplyType																			\n")
		    .append(",	bom.itemSeq itemSeq																					\n")
		    .append(",	bom.operationSeq opSeq																				\n")
		    .append(",	bom.startDate startDate																				\n")
		    .append(",	bom.endDate endDate																					\n")
		    .append("	FROM 																										\n")
		    .append("		(SELECT																									\n")
		    .append("			rownum seqno																						\n")
		    .append("		,	level lvl																								\n")
		    .append("		,	ida3b5																								\n")
		    .append("		,	attribute1																								\n")
		    .append("		,	ida3a5																									\n")
		    .append("		,	parentItemCode																						\n")
		    .append("		,	childItemCode																						\n")
		    .append("		,	quantity																								\n")
		    .append("		,	itemSeq																								\n")
		    .append("		,	operationSeq																						\n")
		    .append("		,	startDate																								\n")
		    .append("		,	endDate																								\n")
		    .append("		,	supplyType																							\n")
		    .append("		,	costRollup																							\n")
		    .append("		FROM KETPartUsageLink																				\n")
		    .append("		START WITH (attribute1) IN (?)																		\n")
		    .append("		CONNECT BY PRIOR ida3a5 = attribute1															\n")
		    .append("		) bom,																										\n")
		    .append("		wtpartmaster itemMaster, wtpart item																\n")
		    .append("	WHERE bom.ida3b5 = itemMaster.ida2a2 																\n")
		    .append("	AND bom.ida3a5 = item.ida2a2																			\n")
		    .append("	AND bom.ida3a5 = (SELECT MAX(i.ida2a2) FROM wtpart i, wtpartmaster m WHERE i.ida3masterreference = m.ida2a2 AND m.wtpartnumber = bom.parentItemCode and i.latestiterationinfo = '1')		\n")
		    .append("	ORDER BY bom.seqno ASC																				\n");

	    pstmt = connection.prepareStatement(strSql.toString());
	    pstmt.setString(1, itemIda2a2);

	    rs = pstmt.executeQuery();

	    strSql.delete(0, strSql.length());

	    int dataCount = 0;
	    BOMAssyComponent component = null;
	    Vector designatorVec = new Vector();
	    String itemCodeStr = "";
	    String parentItemCodeStr = "";
	    String levelStr = "";

	    while (rs.next()) {
		dataCount++;

		itemCodeStr = rs.getString("item") == null ? "" : rs.getString("item").trim();
		parentItemCodeStr = rs.getString("parentItem") == null ? "" : rs.getString("parentItem").trim();
		levelStr = rs.getString("lvl") == null ? "1" : rs.getString("lvl");

		if (dataCount != 1) {
		    component = new BOMAssyComponent(itemCodeStr.trim());
		    component.setSeqInt(new Integer(dataCount));
		    component.setLevelInt(new Integer(Integer.parseInt(levelStr) - 1));
		    component.setDescStr(rs.getString("description") == null ? "" : rs.getString("description"));
		    component.setParentItemCodeStr(parentItemCodeStr);
		    component.setVersionStr(rs.getString("version") == null ? "" : rs.getString("version"));
		    component.setStatusStr(rs.getString("status") == null ? "" : rs.getString("status"));
		    component.setUitStr(rs.getString("uit") == null ? "" : rs.getString("uit"));
		    component.setUomStr(rs.getString("uom") == null ? "" : rs.getString("uom"));

		    component.setEcoNoStr(rs.getString("ecoNo") == null ? "" : rs.getString("ecoNo"));
		    String qty = rs.getString("quantity") == null ? "" : rs.getString("quantity");
		    if (qty.startsWith("."))
			qty = "0" + qty;
		    component.setQuantityDbl(new Double(qty));
		    component.setSupplyTypeStr(rs.getString("supplyType") == null ? "" : rs.getString("supplyType"));
		    component.setCostRollUpStr(rs.getString("costRollup") == null ? "" : rs.getString("costRollup"));
		    component.setStartDate(rs.getString("startDate") == null ? "" : rs.getString("startDate"));
		    component.setEndDate(rs.getString("endDate") == null ? "" : rs.getString("endDate"));
		    component.setItemSeqInt(new Integer(rs.getString("itemSeq") == null ? "10" : rs.getString("itemSeq")));
		    component.setOpSeqInt(new Integer(rs.getString("opSeq") == null ? "1" : rs.getString("opSeq")));

		    resultList.addElement(component);
		}
	    }

	    strSql.append(" SELECT																					\n").append("		m.wtpartnumber itemCode													\n").append(" ,	m.name description																\n")
		    .append(" ,	i.versionida2versioninfo || '.' || i.iterationida2iterationinfo version		\n").append(" ,	i.statestate	 status															\n")
		    .append(" ,	m.defaultunit uit																	\n").append(" ,	to_char(sysdate, 'YYYY-MM-DD') startDate									\n").append("  FROM wtpart i, wtpartmaster m													\n")
		    .append("	WHERE m.wtpartnumber = ?														\n").append("	AND m.ida2a2 = i.ida3masterreference											\n").append("	AND i.latestiterationinfo = '1'														\n")
		    .append("	ORDER BY i.versionida2versioninfo, i.iterationida2iterationinfo DESC		\n");

	    pstmtRoot = connection.prepareStatement(strSql.toString());
	    pstmtRoot.setString(1, parentItemCodeStr);

	    rsRoot = pstmtRoot.executeQuery();

	    strSql.delete(0, strSql.length());

	    if (rsRoot.next()) {
		component = new BOMAssyComponent(parentItemCodeStr);
		component.setSeqInt(new Integer(dataCount + 1));
		component.setLevelInt(new Integer(Integer.parseInt(levelStr)));
		component.setDescStr(rsRoot.getString("description") == null ? "" : rsRoot.getString("description"));
		component.setParentItemCodeStr("");
		component.setVersionStr(rsRoot.getString("version") == null ? "" : rsRoot.getString("version"));
		component.setStatusStr(rsRoot.getString("status") == null ? "" : rsRoot.getString("status"));
		component.setUitStr(rsRoot.getString("uit") == null ? "" : rsRoot.getString("uit"));
		component.setQuantityDbl(new Double(1.00000));
		component.setStartDate(rsRoot.getString("startDate") == null ? "" : rsRoot.getString("startDate"));
		component.setItemSeqInt(new Integer("10"));
		component.setOpSeqInt(new Integer("1"));

		resultList.addElement(component);
	    }
	} catch (Exception ee) {
	    Kogger.error(getClass(), ee);
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs.close();
		}
		if (rsRoot != null) {
		    rsRoot.close();
		    rsRoot.close();
		}
		if (rsPOid != null) {
		    rsPOid.close();
		    rsPOid.close();
		}

		if (pstmt != null) {
		    pstmt.close();
		    pstmt.close();
		}
		if (pstmtRoot != null) {
		    pstmtRoot.close();
		    pstmtRoot.close();
		}
		if (pstmtPOid != null) {
		    pstmtPOid.close();
		    pstmtPOid.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		MessageBox mbox = new MessageBox("DB Close Failure", "DB Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}
    }

}
