package e3ps.bom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import e3ps.bom.common.component.BOMSubAssyComponent;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBomHelper;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class BOMSearchUtilDao {

	/**
	 * substitute 정보 가져오기 from KETBOMSUBSTITUTEMASTER
	 * @param	parentItemCode : orgCode가 포함되어 있음
	 * @param	childItemCode  : orgCode가 포함되어 있음
	 */
	public Vector getSubstitute(String parentItemCode, String childItemCode) {
		Vector substituteVec = new Vector();

		Registry registry = null;
		DBConnectionManager res = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		parentItemCode = Utility.checkNVL(parentItemCode).trim();

		try {
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			stmt = conn.createStatement();

			sql = " SELECT ITEM.ITEMCODE, ITEM.DESCRIPTION, ITEM.DEFAULTUNIT, ITEM.STATESTATE,						\n"
				+ "	 SUBSTITUTEMASTER.QUANTITY, SUBSTITUTEMASTER.STARTDATE, SUBSTITUTEMASTER.ENDDATE		\n"
				+ "	 FROM KETBOMSUBSTITUTEMASTER SUBSTITUTEMASTER,														\n"
				+ "	 		(SELECT IDA2A2 CID																							\n"
				+ "	 		 FROM WTPARTMASTER																						\n"
				+ "	 		 WHERE WTPARTNUMBER = '" + childItemCode + "') CHILD,											\n"
				+ "	 		(SELECT AA.IDA2A2, BB.NAME DESCRIPTION, BB.WTPARTNUMBER ITEMCODE, 						\n"
				+ "					  BB.DEFAULTUNIT DEFAULTUNIT, AA.STATESTATE STATESTATE								\n"
				+ "	 		 FROM wtpart AA, WTPARTMASTER BB																		\n"
				+ "	 		 WHERE AA.IDA3MASTERREFERENCE = BB.IDA2A2) ITEM												\n"
				+ "	 WHERE SUBSTITUTEMASTER.ATTRIBUTE1 = '" + parentItemCode + "'										\n"
				+ "	 AND SUBSTITUTEMASTER.CHILDITEM = CHILD.CID																\n"
				+ "	 AND SUBSTITUTEMASTER.PARENTITEM =																			\n"
				+ "			(SELECT MAX (PARENTITEM)																					\n"
				+ "			FROM KETBOMSUBSTITUTEMASTER																			\n"
				+ "			WHERE SUBSTITUTEMASTER.PARENTITEM = PARENTITEM)												\n"
				+ "	 AND SUBSTITUTEMASTER.CHILDITEM = CHILD.CID																\n"
				+ "	 AND ITEM.IDA2A2 = (SELECT MAX(IDA2A2)																		\n"
				+ "	 FROM wtpart																											\n"
				+ "	 WHERE IDA3MASTERREFERENCE = SUBSTITUTEMASTER.SUBSTITUTEITEM									\n"
				+ "	 AND (STATECHECKOUTINFO = 'wrk' OR STATECHECKOUTINFO = 'c/i')										\n"
				+ "	 AND LATESTITERATIONINFO = 1)																					";

//Kogger.debug(getClass(), "@@@@@@@@ [getSubstitute] sql : " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {
				String quantity = Utility.checkNVL(rs.getString("QUANTITY")).trim();
				if (quantity.equals("")) quantity = "0";
				BOMSubAssyComponent substituteComp = new BOMSubAssyComponent();
				substituteComp.setSubstituteItemCodeStr(Utility.checkNVL(rs.getString("ITEMCODE")));
				substituteComp.setDescStr(Utility.checkNVL(rs.getString("DESCRIPTION")));
				substituteComp.setUitStr( Utility.checkNVL(rs.getString("DEFAULTUNIT")) );
				substituteComp.setStatusStr( Utility.checkNVL(rs.getString("STATESTATE")) );
				substituteComp.setQuantityDbl(new Double(quantity));
				substituteComp.setStartDate( Utility.checkNVL(rs.getString("STARTDATE")).trim() );
				substituteComp.setEndDate( Utility.checkNVL(rs.getString("ENDDATE")) );

				substituteVec.addElement(substituteComp);
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}
		}

		return substituteVec;
	}

	/**
	 * substitute 정보 가져오기 from ketbomecosubstitute
	 * @param	String ecoHeaderNo
	 * @param	String parentItemCode
	 * @param	String childItemCode
	 * @param	String seqNo		  : multiple chanage activity의 ITEMSEQ
	 * @param	String flag			  : before, after
	 */
	public Vector getSubstitute(String ecoHeaderNo, String parentItemCode, String childItemCode, String seqNo, String flag) {
		Vector rtVec = new Vector();

		Registry registry = null;
		DBConnectionManager res = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			stmt = conn.createStatement();

			sql = " SELECT A.SUBSTITUTEITEMCODE, A.BEFOREQUANTITY, A.AFTERQUANTITY,  									\n"
				+ "        A.BEFORESTARTDATE, A.AFTERSTARTDATE, A.BEFOREENDDATE, A.AFTERENDDATE, 					\n"
				+ "        B.NAME AS DESCRIPTION, B.DEFAULTUNIT AS UIT, C.STATESTATE AS STATUS 							\n"
				+ " FROM ketbomecosubstitute A, WTPARTMASTER B, wtpart C 														\n"
				+ " WHERE A.SUBSTITUTEITEMCODE = B.WTPARTNUMBER 																\n"
				+ " AND B.IDA2A2 = C.IDA3MASTERREFERENCE  																		\n"
				+ " AND A.ECOHEADERNUMBER = '" + ecoHeaderNo + "' 																\n"
				+ " AND A.PARENTITEMCODE = '" + parentItemCode + "' 																\n"
				+ " AND A.CHILDITEMCODE = '" + childItemCode + "' 																	\n"
				+ " AND A.SEQUENCENUMBER = '" + seqNo + "' 																		\n";
			if (flag.equals("before")) sql += " AND AFTERQUANTITY = 0 AND ECOCODE = 'Remove' ";			// before 일 때
			else sql += " AND BEFOREQUANTITY = 0 AND ECOCODE = 'Add'";									// after 일 때
			sql = sql + " AND C.IDA2A2 = (SELECT MAX(D.IDA2A2) "
					  + " FROM wtpart D, WTPARTMASTER E "
					  + " WHERE D.IDA3MASTERREFERENCE = E.IDA2A2 "
					  + " AND (D.STATECHECKOUTINFO = 'wrk' OR D.STATECHECKOUTINFO = 'c/i') "
					  + " AND D.LATESTITERATIONINFO = 1 "
					  + " AND E.WTPARTNUMBER = A.SUBSTITUTEITEMCODE) ";

//Kogger.debug(getClass(), "@@@@@@@@ [getSubstitute] sql : " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				BOMSubAssyComponent substituteComp = new BOMSubAssyComponent();
				substituteComp.setSubstituteItemCodeStr( Utility.checkNVL(rs.getString("SUBSTITUTEITEMCODE")).trim() );
				substituteComp.setDescStr( Utility.checkNVL(rs.getString("DESCRIPTION")) );
				substituteComp.setUitStr( Utility.checkNVL(rs.getString("UIT")) );
				substituteComp.setStatusStr( Utility.checkNVL(rs.getString("STATUS")) );
				String quantity = "0";
				if (flag.equals("before")) {
					quantity = Utility.checkNVL(rs.getString("BEFOREQUANTITY")).trim();
					if (quantity.equals("")) quantity = "0";
					substituteComp.setQuantityDbl( new Double( quantity ) );
					substituteComp.setStartDate( Utility.checkNVL(rs.getString("BEFORESTARTDATE")).trim() );
					substituteComp.setEndDate( Utility.checkNVL(rs.getString("BEFOREENDDATE")).trim() );
				} else {
					quantity = Utility.checkNVL(rs.getString("AFTERQUANTITY")).trim();
					if (quantity.equals("")) quantity = "0";
					substituteComp.setQuantityDbl( new Double( quantity  ) );
					substituteComp.setStartDate( Utility.checkNVL(rs.getString("AFTERSTARTDATE")).trim() );
					substituteComp.setEndDate( Utility.checkNVL(rs.getString("AFTERENDDATE")).trim() );
				}

				rtVec.addElement(substituteComp);
			}

		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}
		}

		return rtVec;
	}

	/**
	 * item의 최신 Version의 item 기본정보 가져오기
	 * @param	itemCode : orgCode를 포함하지 않는다.
	 * @param	itemDesc
	 * @param	orgCode
	 */
	public Hashtable getItemData(String itemCode, String itemDesc, String orgCode) {
		Hashtable itemData = new Hashtable();

		Registry registry = null;
		DBConnectionManager res = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			stmt = conn.createStatement();

			sql =  " SELECT a.name descr, b.versionida2versioninfo ver, 										\n";
			sql += "   b.statestate state, a.defaultunit uit															\n";
			sql += " FROM wtpartmaster a, wtpart b 																\n";
			sql += " WHERE a.ida2a2 = b.ida3masterreference 													\n";
			sql += " AND a.wtpartnumber = '" + itemCode + "' 													\n";
			sql += " AND b.ida2a2 = (SELECT max(d.ida2a2) 														\n";
			sql += "   FROM wtpartmaster c, wtpart d 																\n";
			sql += "   WHERE c.ida2a2 = d.ida3masterreference 													\n";
			sql += "   AND (d.statecheckoutinfo = 'wrk' or d.statecheckoutinfo = 'c/i') 						\n";
			sql += "   AND d.latestiterationinfo = 1 																\n";
			sql += "   AND c.wtpartnumber = a.wtpartnumber 													\n";
			sql += "   AND c.name = a.name) 																		\n";

			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {
				itemData.put("descr", Utility.checkNVL(rs.getString("descr")) );
				itemData.put("version", Utility.checkNVL(rs.getString("ver")) );
				itemData.put("uit", Utility.checkNVL(rs.getString("uit")) );
				itemData.put("state", Utility.checkNVL(rs.getString("state")) );
			}
		}
		catch (Exception e)
		{
			Kogger.error(getClass(), e);
		}
		finally
		{
			try
			{
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}
		}

		return itemData;
	}

	/**
	 * item의 최신 Version의 item 기본정보 가져오기
	 * @param	itemCode : orgCode를 포함하지 않는다.
	 * @param	itemDesc
	 * @param	orgCode
	 */
	public Hashtable getItemData(String itemCode) {
		Hashtable itemData = new Hashtable();

		Registry registry = null;
		DBConnectionManager res = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			stmt = conn.createStatement();

			sql = "	SELECT A.NAME AS DESCRIPTION, B.VERSIONIDA2VERSIONINFO || '.' || B.ITERATIONIDA2ITERATIONINFO AS VERSION, \n"
				+ "		   		 B.DEFAULTUNIT AS UIT, B.STATESTATE AS STATE, 																			\n"
				+ "		   		 B.STATECHECKOUTINFO, B.IDA2A2 AS OID 																					\n"
				+ " 	FROM WTPARTMASTER A, wtpart B 																										\n"
				+ " 	WHERE A.IDA2A2 = B.IDA3MASTERREFERENCE 																						\n"
				+ " 	AND	  A.WTPARTNUMBER = '" + itemCode + "' 																		\n"
				+ " 	AND	  B.IDA2A2 = (SELECT MAX(D.IDA2A2) 																							\n"
				+ " 			  FROM WTPARTMASTER C, wtpart D 																							\n"
				+ " 			  WHERE C.IDA2A2 = D.IDA3MASTERREFERENCE 																				\n"
				+ "				  AND	(D.STATECHECKOUTINFO = 'wrk' OR D.STATECHECKOUTINFO = 'c/i') 												\n"
				+ "				  AND	D.LATESTITERATIONINFO = 1 																							\n"
				+ "				  AND	C.WTPARTNUMBER = A.WTPARTNUMBER 																				\n"
				+ "	  			  AND	C.NAME = A.NAME) 																										\n";

			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {
				itemData.put("desc", Utility.checkNVL(rs.getString("DESCRIPTION")) );
				itemData.put("version", Utility.checkNVL(rs.getString("VERSION")) );
				itemData.put("uit", Utility.checkNVL(rs.getString("UIT")) );
				itemData.put("state", Utility.checkNVL(rs.getString("STATE")) );
				itemData.put("oid", Utility.checkNVL(rs.getString("OID")) );
				itemData.put("stateCheckOutInfo", Utility.checkNVL(rs.getString("STATECHECKOUTINFO")) );
			}

		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}
		}

		return itemData;
	}


	// 해당 변경 최상위 모부품이 신규 BOM 헤더정보인지 여부 확인
	public boolean getIsNewBomCode(String ecoItemCode) {
		boolean isExist = false;

		Registry registry = null;
		DBConnectionManager res = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			stmt = conn.createStatement();

			sql = "	SELECT newbomcode										\n"
				+ " 	FROM  ketbomheader		 								\n"
				+ " 	WHERE newbomcode = '" + ecoItemCode + "' 		\n";

			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {
				isExist = true;
			}

		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}
		}

		return isExist;
	}

	// 해당 ECO의 공동작업자(ID)를 리턴
	public ArrayList<Hashtable<String, String>> getCoworkerList(String ecoHeaderNo, String ecoItemCode) {

		ArrayList<Hashtable<String, String>> coworkers = new ArrayList<Hashtable<String, String>>();

		Registry registry = null;
		DBConnectionManager res = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			stmt = conn.createStatement();

			sql = "	SELECT COWORKERID		 										\n"
				+ " 	        , COWORKERNAME											\n"
				+ " 	        , COWORKEREMAIL											\n"
				+ " 	FROM  ketbomecocoworker  										\n"
				+ " 	WHERE ECOHEADERNUMBER = '" + ecoHeaderNo + "' 		\n"
				+ " 	 AND   ECOITEMCODE = '" + ecoItemCode + "' 				\n";

			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {
				Hashtable<String, String> hash = new Hashtable<String, String>();
				hash.put("userId", Utility.checkNVL( rs.getString("COWORKERID") ));
				hash.put("toName", Utility.checkNVL( rs.getString("COWORKERNAME") ));
				hash.put("toEmail", Utility.checkNVL( rs.getString("COWORKEREMAIL") ));

				coworkers.add(hash);
			}

		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}
		}

		return coworkers;
	}


	/**
	 * 내가 coworker 등록된 ecoHeaderNo + itemCode 의 endworkingflag를 반환한다.
	 * @param	ecoHeaderNo
	 * @param	itemCode    : orgCode를 포함한다.
	 * @param	coworkerId
	 * @return	endWorkingFlag : [End Working Flag] 1 : Not Access, 2 : Check-In, 3 : Check-Out, 4 : End-Working
	 */
	public String getEndWorkingFlag(String ecoHeaderNo, String ecoItemCode, String coworkerId) {
		String endWorkingFlag = "";

		Registry registry = null;
		DBConnectionManager res = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			stmt = conn.createStatement();

			sql = "	SELECT ENDWORKINGFLAG FROM ketbomecocoworker 		\n"
				+ " 	WHERE ECOHEADERNUMBER = '" + ecoHeaderNo + "' 		\n"
				+ " 	AND   ECOITEMCODE = '" + ecoItemCode + "' 				\n"
				+ " 	AND   COWORKERID = '" + coworkerId + "' 					\n";

			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {
				endWorkingFlag = Utility.checkNVL( rs.getString("ENDWORKINGFLAG") );
			}

		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}
		}

		return endWorkingFlag.trim();
	}

	// 신규 BOM의 경우에 해당함
	public String getEndWorkingFlagNew(String itemCode, String coworkerId) {
		String endWorkingFlag = "";

		Registry registry = null;
		DBConnectionManager res = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			stmt = conn.createStatement();

			sql = "	SELECT ENDWORKINGFLAG FROM ketbomcoworker 		\n"
				+ " 	WHERE NEWBOMCODE = '" + itemCode + "' 				\n"
				+ " 	AND   COWORKERID = '" + coworkerId + "' 				\n";

			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {
				endWorkingFlag = Utility.checkNVL( rs.getString("ENDWORKINGFLAG") );
			}

		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}
		}

		return endWorkingFlag.trim();
	}

	/**
	 * 내가 coworker 등록된 ecoHeaderNo + itemCode 의 endworkingflag를 변경
 	 * [End Working Flag] 1 : Not Access, 2 : Check-In, 3 : Check-Out, 4 : End-Working
	 * @param	ecoHeaderNo
	 * @param	itemCode    : orgCode를 포함한다.
	 * @param	coworkerId
	 * @param	flag : checkIn, checkOut, endWorking
	 */
	public void updateEndWorkingFlag(String ecoHeaderNo, String ecoItemCode, String coworkerId, String flag) {
		Registry registry = null;
		DBConnectionManager res = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		String endWorkingFlag = "";
		if (flag.equals("checkIn")) endWorkingFlag = "2";
		else if (flag.equals("checkOut")) endWorkingFlag = "3";
		else if (flag.equals("endWorking")) endWorkingFlag = "4";

		try {
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			conn.setAutoCommit(false);

			stmt = conn.createStatement();

			sql = "	UPDATE ketbomecocoworker SET ENDWORKINGFLAG = '" + endWorkingFlag + "'   	\n"
				+ " 	WHERE ECOHEADERNUMBER = '" + ecoHeaderNo + "' 										\n"
				+ " 	AND   ECOITEMCODE = '" + ecoItemCode + "' 												\n"
				+ " 	AND   COWORKERID = '" + coworkerId + "' 													\n";

			stmt.executeUpdate(sql);
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				Kogger.error(getClass(), e1);
			}
			Kogger.error(getClass(), e);
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}
		}
	}

	/**
	 * ecoHeaderNo 의 모든 공동작업자 endworkingflag 를 flag 값으로 변경
 	 * [End Working Flag] 1 : Not Access, 2 : Check-In, 3 : Check-Out, 4 : End-Working
	 * @param	ecoHeaderNo
	 * @param	flag : 1, 2, 3, 4
	 */
	public static void updateEndWorkingFlag(String ecoHeaderNo, String flag) {
		Registry registry = null;
		DBConnectionManager res = null;
		Connection conn = null;
		Statement stmt = null;
//		String sql = "";
		String sql2 = "";
//		String coworkerId ="";

		try {
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			conn.setAutoCommit(false);

			stmt = conn.createStatement();

//			sql = "	SELECT  coworkerid   											\n"
//				+ "		FROM   ketbomecocoworker  								\n"
//				+ " 	WHERE ECOHEADERNUMBER = '" + ecoHeaderNo + "' 	\n";
//
//			ResultSet rs = stmt.executeQuery(sql);
//Kogger.debug(getClass(), "@@@@@ sql : " + sql);
//			while(rs.next()) {

//				coworkerId = Utility.checkNVL( rs.getString("coworkerid") );

				sql2 = "	UPDATE ketbomecocoworker SET ENDWORKINGFLAG = '" + flag + "'   	\n"
					  + " 	WHERE ECOHEADERNUMBER = '" + ecoHeaderNo + "' 						\n";


//					  + " 	AND   COWORKERID = '" + coworkerId + "' 									\n";

				stmt.executeUpdate(sql2);
Kogger.debug(BOMSearchUtilDao.class, "@@@@@ sql : " + sql2);
//			}

			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				Kogger.error(BOMSearchUtilDao.class, e1);
			}
			Kogger.error(BOMSearchUtilDao.class, e);
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (Exception e) {
				Kogger.error(BOMSearchUtilDao.class, e);
			}
		}
	}

	/**
	 * newBomCode 의 모든 공동작업자 endworkingflag 를 flag 값으로 변경
 	 * [End Working Flag] 1 : Not Access, 2 : Check-In, 3 : Check-Out, 4 : End-Working
	 * @param	newBomCode
	 * @param	flag : 1, 2, 3, 4
	 */
	public static void updateEndWorkingFlagNew(String newBomCode, String flag) {
		Registry registry = null;
		DBConnectionManager res = null;
		Connection conn = null;
		Statement stmt = null;
//		String sql = "";
		String sql2 = "";
//		String coworkerId ="";

		try {
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			conn.setAutoCommit(false);

			stmt = conn.createStatement();

//			sql = "	SELECT  coworkerid   									\n"
//				+ "		FROM   ketbomcoworker  							\n"
//				+ " 	WHERE NEWBOMCODE = '" + newBomCode + "' \n";
//
//			ResultSet rs = stmt.executeQuery(sql);
//
//			while(rs.next()) {
//
//				coworkerId = Utility.checkNVL( rs.getString("coworkerid") );

				sql2 = "	UPDATE ketbomcoworker SET ENDWORKINGFLAG = '" + flag + "'   		\n"
					  + " 	WHERE NEWBOMCODE = '" + newBomCode + "' 							\n";
//					  + " 	AND   COWORKERID = '" + coworkerId + "' 									\n";

				stmt.executeUpdate(sql2);
//			}

			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				Kogger.error(BOMSearchUtilDao.class, e1);
			}
			Kogger.error(BOMSearchUtilDao.class, e);
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (Exception e) {
				Kogger.error(BOMSearchUtilDao.class, e);
			}
		}
	}


	// 신규 금형부품 추가 시, 최하위 노드에 추가할 때 호출하는 함수
	// ItemSeq Max 값,  SequenceNumber 리턴
	public static Hashtable<String, String> getLastNodeItemSeq(String newBomCode) {
		Hashtable<String, String> hashReturn = new Hashtable<String, String>();

		Registry registry = null;
		DBConnectionManager res = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			stmt = conn.createStatement();

			sql = "	SELECT  max( to_number(itemseq) ) itemseq					\n"
				+ "   	         , max( sequencenumber )	 sequencenumber			\n"
				+ "   	FROM ketbomcomponent 											\n"
				+ " 	WHERE NEWBOMCODE = '" + newBomCode + "' 			\n";
//				+ " 	order by sequencenumber desc								\n";

			ResultSet rs = stmt.executeQuery(sql);
			rs.next();		// 최상위 itemseq 하나만 가져오면 되므로

			hashReturn.put("itemseq", Utility.checkNVL( rs.getString("itemseq")));
			hashReturn.put("seq", Utility.checkNVL( rs.getString("sequencenumber")));

		} catch (Exception e) {
			Kogger.error(BOMSearchUtilDao.class, e);
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (Exception e) {
				Kogger.error(BOMSearchUtilDao.class, e);
			}
		}
		return hashReturn;
	}

	// 신규 금형부품 추가 시, 최하위 노드에 추가할 때 호출하는 함수 (설계변경인 경우)
	// ItemSeq Max 값, SequenceNumber 리턴
	public static Hashtable<String, String> getLastNodeItemSeqEco(String ecoHeaderNo, String ecoItemCode) {
		Hashtable<String, String> hashReturn = new Hashtable<String, String>();

		Registry registry = null;
		DBConnectionManager res = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			stmt = conn.createStatement();

			sql = "	SELECT  max( to_number(itemseq) ) itemseq					\n"
				+ "   	         , max( sequencenumber )	 sequencenumber			\n"
				+ "   	FROM ketbomecocomponent 									\n"
				+ " 	WHERE ECOHEADERNUMBER = '" + ecoHeaderNo + "' 		\n"
				+ " 	  AND  ECOITEMCODE = '" + ecoItemCode + "' 				\n";
//				+ " 	order by sequencenumber desc									\n";

			ResultSet rs = stmt.executeQuery(sql);
			rs.next();		// 최상위 itemseq 하나만 가져오면 되므로

			hashReturn.put("itemseq", Utility.checkNVL( rs.getString("itemseq")));
			hashReturn.put("seq", Utility.checkNVL( rs.getString("sequencenumber")));

		} catch (Exception e) {
			Kogger.error(BOMSearchUtilDao.class, e);
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (Exception e) {
				Kogger.error(BOMSearchUtilDao.class, e);
			}
		}
		return hashReturn;
	}

	// sequenceNo 보다 큰 sequenceNo를 가지는 금형부품의 ItemSeq 증가(+10)
	public static void updateItemSeqNew(String newBomCode, String sequenceNo) {
		Registry registry = null;
		DBConnectionManager res = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String sql2 = "";
		String addItemSeq = "";
		String sortSequence = "";
		String childItemCode = "";
		ArrayList<Hashtable> arr = new ArrayList<Hashtable>();

		try {
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			conn.setAutoCommit(false);

			stmt = conn.createStatement();

			// ItemSeq 를 변경해야 할 대상 부품 ItemCode 조회
			sql = "SELECT childitemcode, itemseq 								\n"
			+ "		FROM  ketbomcomponent										\n"
			+ " 	WHERE NEWBOMCODE = '" + newBomCode + "' 		\n"
			+ " 	AND   sequencenumber > '" + sequenceNo + "' 		\n"
			+ " 	order by sequencenumber desc								\n";
Kogger.debug(BOMSearchUtilDao.class, " >>>> SQL : " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Hashtable<String, String> hash = new Hashtable<String, String>();
				hash.put("childitemcode", Utility.checkNVL( rs.getString("childitemcode")));
				hash.put("itemseq", Utility.checkNVL( rs.getString("itemseq")));
//Kogger.debug(BOMSearchUtilDao.class, "@@@@@@@@@ hash : " + hash);
				arr.add(hash);
			}
//Kogger.debug(BOMSearchUtilDao.class, "@@@@@@@@@ arr.Size() : " + arr.size());

			Hashtable<String, String> temp = new Hashtable<String, String>();

			// 변경 대상 Sequence 를 가지는 모든 부품 변경
			for (int i = 0; i < arr.size(); i++) {

				sortSequence = "";		// sequence 초기화
				temp = arr.get(i);
//Kogger.debug(BOMSearchUtilDao.class, "@@@@@@@@@@@@ temp : " + temp);
				childItemCode = Utility.checkNVL(temp.get("childitemcode"));
//Kogger.debug(BOMSearchUtilDao.class, "@@@@@@@@@@@@ childItemCode : " + childItemCode);

				// ItemSeq 수정 (+10)
				addItemSeq = Utility.checkNVL(temp.get("itemseq"));
				addItemSeq = (Integer.parseInt(addItemSeq) + 10) + "";
//Kogger.debug(BOMSearchUtilDao.class, "@@@@@@@@@@@@ addItemSeq : " + addItemSeq);

				// Sequence 만들기
				int tempSeq = Integer.parseInt(addItemSeq.substring(0, addItemSeq.length()-1));
				if (tempSeq < 10)
					sortSequence = "000" + tempSeq;
				else if (tempSeq < 100)
					sortSequence = "00" + tempSeq;
				else if (tempSeq < 1000)
					sortSequence = "0" + tempSeq;
				else if (tempSeq < 10000)
					sortSequence = addItemSeq;

				sortSequence = "0001" + sortSequence;
//Kogger.debug(BOMSearchUtilDao.class,  "@@@@@@@@@@@@ sortSequence : " + sortSequence);

				// 해당 부품 변경
				sql2 = "	UPDATE ketbomcomponent  		 							\n"
				+ "			SET     itemseq = '" + addItemSeq + "'  					\n"
				+ "			    	, sequencenumber = '" + sortSequence + "'  		\n"
				+ " 		WHERE NEWBOMCODE = '" + newBomCode + "' 		\n"
				+ " 		AND   childitemcode = '" + childItemCode + "' 			\n";

Kogger.debug(BOMSearchUtilDao.class, " >>>> SQL : " + sql2);
				stmt.executeUpdate(sql2);
			}

			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				Kogger.error(BOMSearchUtilDao.class, e1);
			}
			Kogger.error(BOMSearchUtilDao.class, e);
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (Exception e) {
				Kogger.error(BOMSearchUtilDao.class, e);
			}
		}
	}

	// sequenceNo 보다 큰 sequenceNo를 가지는 금형부품의 sequenceNo 증가 :: 변경 BOM용
	public static void updateItemSeqECO(String ecoHeaderNo, String ecoItemCode, String sequenceNo) {
		Registry registry = null;
		DBConnectionManager res = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String sql2 = "";
		String addSeq = "";
		String sortSequence = "";
		String childItemCode = "";
		ArrayList<Hashtable> arr = new ArrayList<Hashtable>();

		try {
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			conn.setAutoCommit(false);

			stmt = conn.createStatement();

			// ItemSeq 를 변경해야 할 대상 부품 ItemCode 조회
			sql = "SELECT childitemcode, sequencenumber					\n"
			+ "		FROM  ketbomecocomponent								\n"
			+ " 	WHERE ECOHEADERNUMBER = '" + ecoHeaderNo + "' 	\n"
			+ " 	AND    ECOITEMCODE = '" + ecoItemCode + "' 			\n"
			+ " 	AND    sequencenumber > '" + sequenceNo + "' 		\n"
			+ " 	order by sequencenumber desc								\n";
Kogger.debug(BOMSearchUtilDao.class, " >>>> SQL : " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Hashtable<String, String> hash = new Hashtable<String, String>();
				hash.put("childitemcode", Utility.checkNVL( rs.getString("childitemcode")));
				hash.put("sequence", Utility.checkNVL( rs.getString("sequencenumber")));
Kogger.debug(BOMSearchUtilDao.class, "@@@@@@@@@ hash : " + hash);
				arr.add(hash);
			}
Kogger.debug(BOMSearchUtilDao.class, "@@@@@@@@@ arr.Size() : " + arr.size());

			Hashtable<String, String> temp = new Hashtable<String, String>();

			// 변경 대상 Sequence 를 가지는 모든 부품 변경
			for (int i = 0; i < arr.size(); i++) {

				sortSequence = "";			// sequence 초기화
				temp = arr.get(i);
//Kogger.debug(BOMSearchUtilDao.class, "@@@@@@@@@@@@ temp : " + temp);
				childItemCode = Utility.checkNVL(temp.get("childitemcode"));
Kogger.debug(BOMSearchUtilDao.class, "@@@@@@@@@@@@ childItemCode : " + childItemCode);

				// ItemSeq 수정 (+10)
				addSeq = Utility.checkNVL(temp.get("sequence"));
				addSeq = (Integer.parseInt(addSeq.replace("0001", "")) + 2) + "";
Kogger.debug(BOMSearchUtilDao.class, "@@@@@@@@@@@@ addSeq : " + addSeq);

				// Sequence 만들기
//				int tempSeq = Integer.parseInt(addItemSeq.substring(0, addItemSeq.length()-1)) * 2;
				int tempSeq = Integer.parseInt(addSeq);
				if (tempSeq < 10)
					sortSequence = "000" + tempSeq;
				else if (tempSeq < 100)
					sortSequence = "00" + tempSeq;
				else if (tempSeq < 1000)
					sortSequence = "0" + tempSeq;
				else if (tempSeq < 10000)
					sortSequence = addSeq;

				sortSequence = "0001" + sortSequence;
Kogger.debug(BOMSearchUtilDao.class,  "@@@@@@@@@@@@ sortSequence : " + sortSequence);

				// 해당 부품 변경
				sql2 = "	UPDATE ketbomecocomponent  		 						\n"
//				+ "			SET     itemseq = '" + addItemSeq + "'  					\n"		// item seq 는 변경하면 안됨
				+ "			SET     sequencenumber = '" + sortSequence + "'  		\n"
				+ " 		WHERE ECOHEADERNUMBER = '" + ecoHeaderNo + "' 	\n"
				+ " 		AND    ECOITEMCODE = '" + ecoItemCode + "' 			\n"
				+ " 		AND    childitemcode = '" + childItemCode + "' 			\n";

Kogger.debug(BOMSearchUtilDao.class, " >>>> SQL : " + sql2);
				stmt.executeUpdate(sql2);
			}

			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				Kogger.error(BOMSearchUtilDao.class, e1);
			}
			Kogger.error(BOMSearchUtilDao.class, e);
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (Exception e) {
				Kogger.error(BOMSearchUtilDao.class, e);
			}
		}
	}

	// Migration 된 부품이 BOM 구성된 부품인지 확인하는 함수
	public static boolean isBomChildExistInPartUsageLink(String itemCode)
	{
		boolean isBomChildFlag = false;
		Registry registry = null;
        DBConnectionManager resource = null;
        Connection connection = null;

		try	{
			registry = Registry.getRegistry("e3ps.bom.bom");
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(registry.getString("plm"));

            BOMSearchDao dao = new BOMSearchDao();
			dao.isBomChildExist(connection, itemCode);

			long resultCount = dao.getDataCount();

			if(resultCount > 0) {
				isBomChildFlag = true;
			}
		} catch(Exception ex) {
			Kogger.error(BOMSearchUtilDao.class, ex);
		} finally {
            if(resource != null) {
                resource.freeConnection(registry.getString("plm"), connection);
            }
        }
		return isBomChildFlag;
	}


	// 부품번호로 ECO 헤더 정보의 포장수량(BoxQuantity) 가져오기
	public static String getEcoHeaderBoxQuantity(String ecoItemCode, Connection connection)
	{
        Statement stmt = null;
        String sql = "";
        String strBoxQuantity = "";

		try	{
			stmt = connection.createStatement();

			sql = "SELECT boxquantity									\n"
			+ "		FROM  ketbomecoheader 							\n"
			+ " 	WHERE ecoitemcode = '"+ ecoItemCode +"'	\n"
			+ " 	order by bomversion desc							\n";
Kogger.debug(BOMSearchUtilDao.class, " >>>> SQL : " + sql);

			ResultSet rs = stmt.executeQuery(sql);

			// 최신버전 하나만 가져오면되므로 (없으면 그냥 빈 값으로 넘김)
			if (rs.next())
			{
				strBoxQuantity = Utility.checkNVL( rs.getString("boxquantity") );
			}

		} catch(Exception ex) {
			Kogger.error(BOMSearchUtilDao.class, ex);
		} finally {
				try {
					if (stmt != null) stmt.close();
				} catch (SQLException e) {
					Kogger.error(BOMSearchUtilDao.class, e);
				}
        }
		return strBoxQuantity;
	}


	// PLM 에 이미 등록된 BOM 구성인지 확인
	public int isBomExistInPartUsageLink(String parentOid, String childOid, String childMasterOid)
	{
		Registry registry = null;
        DBConnectionManager resource = null;
        Connection connection = null;
        Statement stmt = null;
        String sql = "";
        int existCount = 0;

		try	{
			registry = Registry.getRegistry("e3ps.bom.bom");
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(registry.getString("plm"));
			stmt = connection.createStatement();

			sql = "SELECT count(*)	cnt										\n"
			+ "		FROM  ketpartusagelink t								\n"
			+ " 	WHERE t.ida3a5 = '" + parentOid + "' 				\n"
			+ " 	AND    t.ida3b5 = '" + childMasterOid + "' 		\n"
			+ " 	AND    t.versionitemcode =  '" + childOid + "' 	\n";
Kogger.debug(getClass(), " >>>> SQL : " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				existCount = Integer.parseInt(rs.getString("cnt"));
			}

		} catch(Exception ex) {
			Kogger.error(getClass(), ex);
		} finally {
				try {
					if (stmt != null) stmt.close();
					if (connection != null)	connection.close();
				} catch (SQLException e) {
					Kogger.error(getClass(), e);
				}
            if(resource != null) {
                resource.freeConnection(registry.getString("plm"), connection);
            }
        }
		return existCount;
	}


	// PartUsageLink 에서 해당 부품의 포장수량(기준수량) 정보 가져오기 [ECM 모듈에서 사용]
	// PartUsageLink 에서 자신의 포장수량은 자신의 자부품이 가지고 있음
	public static String getBoxQuantityPartUsageLink(String itemCode, Connection connection)
	{
        Statement stmt = null;
        String sql = "";
        String boxQuantity = "";

		try	{
			stmt = connection.createStatement();

			sql = "SELECT  t.boxquantity									\n"
			+ "		FROM   ketpartusagelink t								\n"
			+ "		         , ketpartusagelink t2							\n"
			+ " 	WHERE t.parentitemcode = '"+itemCode+"'			\n"
			+ " 	AND   t.parentitemcode = t2.childitemcode(+)		\n"
			+ " 	AND   t.ida3a5 = t2.versionitemcode(+)				\n"
			+ " 	order by t.ida3a5 desc									\n";
Kogger.debug(BOMSearchUtilDao.class, " >>>> SQL : " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			// 자부품이 있는경우에는 한개만 가져오면 된다.
			if ( rs.next() )
			{
				boxQuantity = Utility.checkNVL( rs.getString("boxquantity") );
			}
			else		// 자부품이 존재하지 않는경우에는 1.0 으로 셋팅함
			{
				boxQuantity = "1.0";
			}

		} catch(Exception ex) {
			Kogger.error(BOMSearchUtilDao.class, ex);
		} finally {
				try {
					if (stmt != null) stmt.close();
				} catch (SQLException e) {
					Kogger.error(BOMSearchUtilDao.class, e);
				}
        }
		return boxQuantity;
	}

	// PartUsageLink 에서 해당 부품의 포장수량(기준수량) 정보 가져오기
	// PartUsageLink 에서 자신의 포장수량은 자신의 자부품이 가지고 있음
	public String getBoxQuantityPartUsageLink(String itemCode)
	{
		Registry registry = null;
        DBConnectionManager resource = null;
        Connection connection = null;
        Statement stmt = null;
        String sql = "";
        String boxQuantity = "";

		try	{
			registry = Registry.getRegistry("e3ps.bom.bom");
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(registry.getString("plm"));
			stmt = connection.createStatement();

			sql = "SELECT t.boxquantity									\n"
			+ "		FROM  ketpartusagelink t								\n"
			+ "				, ketpartusagelink t2							\n"
			+ " 	WHERE t.parentitemcode = '"+itemCode+"'			\n"
			+ " 	AND   t.parentitemcode = t2.childitemcode(+)		\n"
			+ " 	AND   t.ida3a5 = t2.versionitemcode(+)				\n"
			+ " 	order by t.ida3a5 desc									\n";
Kogger.debug(getClass(), " >>>> SQL : " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			// 자부품이 있는경우에는 한개만 가져오면 된다.
			if ( rs.next() )
			{
				boxQuantity = Utility.checkNVL( rs.getString("boxquantity") );
			}
			else		// 자부품이 존재하지 않는경우에는 1.0 으로 셋팅함
			{
				boxQuantity = "1.0";
			}

		} catch(Exception ex) {
			Kogger.error(getClass(), ex);
		} finally {
				try {
					if (stmt != null) stmt.close();
					if (connection != null)	connection.close();
				} catch (SQLException e) {
					Kogger.error(getClass(), e);
				}
            if(resource != null) {
                resource.freeConnection(registry.getString("plm"), connection);
            }
        }
		return boxQuantity;
	}


	// PartUsageLink 에서 해당 부품의 포장수량(기준수량) 정보 가져오기 (해당 부품의 버전 지정함)
	// PartUsageLink 에서 자신의 포장수량은 자신의 자부품이 가지고 있음
	public String getBoxQuantityPartUsageLinkAtVersion( String itemCode, String strIda2a2 )
	{
		Registry registry = null;
        DBConnectionManager resource = null;
        Connection connection = null;
        Statement stmt = null;
        String sql = "";
        String boxQuantity = "";

		try	{
			registry = Registry.getRegistry("e3ps.bom.bom");
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(registry.getString("plm"));
			stmt = connection.createStatement();

			sql = "SELECT t.boxquantity									\n"
			+ "		FROM  ketpartusagelink t								\n"
			+ "				, ketpartusagelink t2							\n"
			+ " 	WHERE t.parentitemcode = '"+itemCode+"'			\n"
			+ " 	AND   t.parentitemcode = t2.childitemcode(+)		\n"
			+ " 	AND   t.ida3a5 = t2.versionitemcode(+)				\n"
			+ " 	and    t.ida3a5 = '"+strIda2a2+"'						\n";
Kogger.debug(getClass(), " >>>> SQL : " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			// 자부품이 있는경우에는 한개만 가져오면 된다.
			if ( rs.next() )
			{
				boxQuantity = Utility.checkNVL( rs.getString("boxquantity") );
			}
			else		// 자부품이 존재하지 않는경우에는 1.0 으로 셋팅함
			{
				boxQuantity = "1.0";
			}

		} catch(Exception ex) {
			Kogger.error(getClass(), ex);
		} finally {
				try {
					if (stmt != null) stmt.close();
					if (connection != null)	connection.close();
				} catch (SQLException e) {
					Kogger.error(getClass(), e);
				}
            if(resource != null) {
                resource.freeConnection(registry.getString("plm"), connection);
            }
        }
		return boxQuantity;
	}


	// 해당 부품 최신 버전 정보 리턴
	public String getLastestPartVersion( String partNumber ) throws Exception
	{
		String partVersion = "";
		Registry registry = null;
        DBConnectionManager resource = null;
        Connection connection = null;
        Statement stmt = null;
        String sql = "";

        try
        {
    		registry = Registry.getRegistry("e3ps.bom.bom");
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(registry.getString("plm"));
    		stmt = connection.createStatement();

    		sql =  "SELECT   p.classnamea2a2 || ':' || p.ida2a2											oid													\n"
    			  + "         , p.versionsortida2versioninfo                                             version												\n"
    			  + "FROM WTPart p																															\n"
    			  + "        , WTPartMaster m																													\n"
    			  + "        ,  ( SELECT MAX(p2.versionsortida2versioninfo) objver, MAX(p2.ida2a2) objid, p2."  + PartSpecEnum.SpPartType.getColumnName() + " as value		\n"
    			  + "             FROM WTPart p2, WTPartMaster m2 																	\n"
    			  + "            WHERE 1=1																				\n"
    			  + "               AND m2.wtpartnumber = '"+partNumber+"'																			\n"
    			  + "               AND p2.ida3masterreference = m2.ida2a2																				\n"
    			  + "               AND p2.statecheckoutinfo != 'wrk'																						\n"
    			  + "               AND p2.latestiterationinfo = 1																							\n"
    			  + "           GROUP BY m2.wtpartnumber, p2."  + PartSpecEnum.SpPartType.getColumnName() + " ) x																					\n"
    			  + "WHERE p.ida3masterreference = m.ida2a2																							\n"
    			  + "    AND p.ida2a2 = x.objid																												\n"
    			  + "ORDER BY m.wtpartnumber																												\n";

			ResultSet rs = stmt.executeQuery(sql);

			while( rs.next() )
			{
				partVersion = rs.getString("version");
			}
		}
		catch( Exception e )
		{
			throw e;
		}
		finally {
			try {
				if (stmt != null) stmt.close();
				if (connection != null)	connection.close();
			} catch (SQLException e) {
				Kogger.error(getClass(), e);
			}
			if(resource != null) {
				resource.freeConnection(registry.getString("plm"), connection);
			}
		}

		return partVersion;
	}

	// 최신 부품 Oid 리턴
	public String getLastestPartOid( String partNumber ) throws Exception
	{
		String partOid = "";
		Registry registry = null;
        DBConnectionManager resource = null;
        Connection connection = null;
        Statement stmt = null;
        String sql = "";

        try
        {
    		registry = Registry.getRegistry("e3ps.bom.bom");
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(registry.getString("plm"));
    		stmt = connection.createStatement();

    		sql =  "SELECT   p.classnamea2a2 || ':' || p.ida2a2											oid													\n"
    			  + "         , p.versionsortida2versioninfo                                             version												\n"
    			  + "FROM WTPart p																															\n"
    			  + "        , WTPartMaster m																													\n"
    			  + "        ,  ( SELECT MAX(p2.versionsortida2versioninfo) objver, MAX(p2.ida2a2) objid, p2."  + PartSpecEnum.SpPartType.getColumnName() + " as value								\n"
    			  + "             FROM WTPart p2, WTPartMaster m2 																	\n"
    			  + "            WHERE 1=1																				\n"
    			  + "               AND m2.wtpartnumber = '"+partNumber+"'																			\n"
    			  + "               AND p2.ida3masterreference = m2.ida2a2																				\n"
    			  + "               AND p2.statecheckoutinfo != 'wrk'																						\n"
    			  + "               AND p2.latestiterationinfo = 1																							\n"
    			  + "           GROUP BY m2.wtpartnumber, p2."  + PartSpecEnum.SpPartType.getColumnName() + " ) x																					\n"
    			  + "WHERE p.ida3masterreference = m.ida2a2																							\n"
    			  + "    AND p.ida2a2 = x.objid																												\n"
    			  + "ORDER BY m.wtpartnumber																												\n";

			ResultSet rs = stmt.executeQuery(sql);

			while( rs.next() )
			{
				partOid = rs.getString("oid");
			}
		}
		catch( Exception e )
		{
			throw e;
		}
		finally {
			try {
				if (stmt != null) stmt.close();
				if (connection != null)	connection.close();
			} catch (SQLException e) {
				Kogger.error(getClass(), e);
			}
			if(resource != null) {
				resource.freeConnection(registry.getString("plm"), connection);
			}
		}

		return partOid;
	}


	// 해당 버전의 최신 iteration ida2a2 리턴
	public String getPartOidAtVersion( String itemCode, String strVersion )
	{
		Registry registry = null;
        DBConnectionManager resource = null;
        Connection connection = null;
        Statement stmt = null;
        String sql = "";
        String strOid = "";

		try	{
			registry = Registry.getRegistry("e3ps.bom.bom");
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(registry.getString("plm"));
			stmt = connection.createStatement();

			sql = "SELECT  t.ida2a2  oid											\n"
			+ "		 FROM  wtpart t													\n"
			+ "		         , wtpartmaster m										\n"
			+ " 	WHERE  t.ida3masterreference = m.ida2a2					\n"
			+ " 	  AND   m.wtpartnumber = '"+itemCode+"'				\n"
			+ " 	  AND   t.versionida2versioninfo = '"+strVersion+"'		\n"
			+ " 	  AND   t.statecheckoutinfo != 'wrk'							\n"
			+ " 	  AND   t.latestiterationinfo = 1								\n";
Kogger.debug(getClass(), " >>>> SQL [getPartOidAtVersion] : " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			if ( rs.next() )
			{
				strOid = Utility.checkNVL( rs.getString("oid") );
			}

		} catch(Exception ex) {
			Kogger.error(getClass(), ex);
		} finally {
				try {
					if (stmt != null) stmt.close();
					if (connection != null)	connection.close();
				} catch (SQLException e) {
					Kogger.error(getClass(), e);
				}
            if(resource != null) {
                resource.freeConnection(registry.getString("plm"), connection);
            }
        }
		return strOid;
	}


	// 해당 부품의 버전에 해당하는 ECO Header Number 리턴
	public String getEcoHeaderNoAtVersion( String itemCode, String strVersion )
	{
		Registry registry = null;
        DBConnectionManager resource = null;
        Connection connection = null;
        Statement stmt = null;
        String sql = "";
        String strEcoHeaderNo = "";

		try	{
			registry = Registry.getRegistry("e3ps.bom.bom");
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(registry.getString("plm"));
			stmt = connection.createStatement();

			sql = "SELECT t.ecoheadernumber						\n"
			+ "		 FROM  ketbomecoheader t						\n"
			+ " 	WHERE  t.ecoitemcode = '"+itemCode+"'		\n"
			+ " 	  AND   t.bomversion = '"+strVersion+"'		\n";
Kogger.debug(getClass(), " >>>> SQL [getEcoHeaderNoAtVersion] : " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			while ( rs.next() )
			{
				strEcoHeaderNo = Utility.checkNVL( rs.getString("ecoheadernumber") );
			}

		} catch(Exception ex) {
			Kogger.error(getClass(), ex);
		} finally {
				try {
					if (stmt != null) stmt.close();
					if (connection != null)	connection.close();
				} catch (SQLException e) {
					Kogger.error(getClass(), e);
				}
            if(resource != null) {
                resource.freeConnection(registry.getString("plm"), connection);
            }
        }
		return strEcoHeaderNo;
	}


	// 해당 부품의 가장 최신 ECO 객체의 버전을 리턴
	public String getLatestEcoVersionApproval( String itemCode, String strType )
	{
		Registry registry = null;
        DBConnectionManager resource = null;
        Connection connection = null;
        Statement stmt = null;
        String sql = "";
        String strClass = "";
        String strVersion = "";

		try	{
			registry = Registry.getRegistry("e3ps.bom.bom");
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(registry.getString("plm"));
			stmt = connection.createStatement();

			if (PartUtil.isProductType(strType)) { // 제품인 경우
				strClass = "KETProdChangeOrder";
			} else {												// 금형인 경우
				strClass = "KETMoldChangeOrder";
			}

			sql = "SELECT  distinct i.versionida2versioninfo version													\n"
			+ "		FROM   wtpart i																							\n"
			+ "		         , wtpartmaster m																				\n"
			+ "		WHERE   m.wtpartnumber = '"+itemCode+"'														\n"
			+ " 	   AND   m.ida2a2 = i.ida3masterreference															\n"
			+ " 	   AND   i.latestiterationinfo = '1'  																	\n"
			+ " 	   AND   i.versionida2versioninfo IN ( SELECT  h.bomversion									\n"
			+ " 	                                               FROM   ketbomecoheader h							\n"
			+ " 	                                                         , "+strClass+" t									\n"
			+ " 	                                              WHERE  h.ecoheadernumber = t.ecoid				\n"
			+ " 	                                                AND   t.statestate = 'APPROVED' 					\n"
			+ " 	                                                AND   h.ecoitemcode = '"+itemCode+"' )			\n"
			+ " 	order by version desc																					\n";
Kogger.debug(getClass(), " >>>> SQL [getLatestEcoVersionApproval] : " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			// 최신 버전 한개만 리턴
			if ( rs.next() )
			{
				strVersion = Utility.checkNVL( rs.getString("version") );
			}

		} catch(Exception ex) {
			Kogger.error(getClass(), ex);
		} finally {
				try {
					if (stmt != null) stmt.close();
					if (connection != null)	connection.close();
				} catch (SQLException e) {
					Kogger.error(getClass(), e);
				}
            if(resource != null) {
                resource.freeConnection(registry.getString("plm"), connection);
            }
        }
		return strVersion;
	}


	// BOM 양산이관 시, ERP로 자재마스터 업데이트
	public static void updateItemMasterERP(String ecoHeaderNo, String ecoItemCode) {
		Registry registry = null;
		DBConnectionManager res = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		String strCode = "";
		String strUnit = "";
		String strReturn = "";
		Vector<String> vecCode = new Vector<String>();
Kogger.debug(BOMSearchUtilDao.class, "############# updateItemMasterERP START #############");

		try {
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			stmt = conn.createStatement();

			// 원래료나 스크랩 코드를 가지는 자부품 ItemCode 조회
			// (헤더 하위 1레벨에서만 수정가능하므로 ecoitemcode 와 parentitemcode 가 동일한 것만 찾는다)
			sql = "SELECT distinct(childitemcode) 								\n"
			+ "		FROM  ketbomecocomponent								\n"
			+ " 	WHERE ecoheadernumber = '" + ecoHeaderNo + "' 		\n"
			+ " 	AND   ecoitemcode = '" + ecoItemCode + "' 				\n"
			+ " 	AND   parentitemcode = '" + ecoItemCode + "' 			\n"
			+ " 	and substr(childitemcode, 0, 1) in ('R', 'S')					\n";
Kogger.debug(BOMSearchUtilDao.class, " >>>> SQL1 : " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vecCode.add(rs.getString("childitemcode"));
			}
Kogger.debug(BOMSearchUtilDao.class, "@@@@@@@@@ vecCode.Size() : " + vecCode.size());
Kogger.debug(BOMSearchUtilDao.class, "@@@@@@@@@ vecCode : " + vecCode);

			for (int i = 0; i < vecCode.size(); i++) {

				Hashtable<String, String> hashUpdate = new Hashtable<String, String>();

				// 자재마스터를 업데이트 할 정보 조회
				sql = "	SELECT  parentitemcode 		 							\n"
				+ "					  ,childitemcode										\n"
				+ "					  ,afterquantity										\n"
				+ "					  ,afterunit											\n"
				+ "			FROM   ketbomecocomponent							\n"
				+ " 		WHERE ecoitemcode = '" +ecoItemCode + "' 			\n"
				+ " 		AND    ecoheadernumber = '" + ecoHeaderNo +"'	\n"
				+ " 		AND    childitemcode = '" + vecCode.get(i) +"'		\n"
				+ " 		AND    afterquantity != 0									\n";
Kogger.debug(BOMSearchUtilDao.class, " >>>> SQL2 : " + sql);

				rs = stmt.executeQuery(sql);
				while(rs.next()){

					hashUpdate.put("partNo", rs.getString("parentitemcode"));					// 업데이트 대상 ERP 자재 번호

					strCode = rs.getString("childitemcode");
					strUnit = rs.getString("afterunit");
					if ( !strCode.equals("") ) {
						if (strCode.substring(0, 1).equals("R") ) {
							hashUpdate.put("rQuantity", rs.getString("afterquantity"));			// 순중량
							hashUpdate.put("rUnit", strUnit.substring(strUnit.indexOf("_")+1));	// 중량단위
						} else if ( strCode.substring(0, 1).equals("S") ) {
							hashUpdate.put("sCode", strCode);										// 스크랩코드
							hashUpdate.put("sQuantity", rs.getString("afterquantity"));			// 스크랩중량
						}
					}
Kogger.debug(BOMSearchUtilDao.class, "@@@@@@ hashUpdate : " + hashUpdate);

					// 자재마스터 update
					strReturn = KETBomHelper.service.updateItemMasterERP(hashUpdate);
					if (!strReturn.equals("") && strReturn.substring(0, 1).equals("S")) {
						Kogger.debug(BOMSearchUtilDao.class, ">>>> 자재마스터 Update 성공 :: " + hashUpdate.get("partNo") );
					} else if (strReturn.substring(0, 1).equals("F")) {
						Kogger.debug(BOMSearchUtilDao.class, ">>>> 자재마스터 Update 실패 :: " + hashUpdate.get("partNo") );
					} else if (strReturn.substring(0, 1).equals("N")) {
						Kogger.debug(BOMSearchUtilDao.class, ">>>> 자재마스터 존재안함 :: " + hashUpdate.get("partNo") );
					}
				}
			}
Kogger.debug(BOMSearchUtilDao.class, "############# updateItemMasterERP END #############");
		} catch (Exception e) {
			Kogger.error(BOMSearchUtilDao.class, e);
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (Exception e) {
				Kogger.error(BOMSearchUtilDao.class, e);
			}
		}
	}


	/**
	 * 세션에 설정된 Organization Code를 이용해서 Organization ID를 리턴함
	 * @param	orgCode
	 */
	public String getOrgID(String orgCode)
	{
		String orgID = "";

		Registry registry = null;
		DBConnectionManager res = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		StringBuffer querySQL = new StringBuffer();

		try
		{
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));

			querySQL.append(" SELECT ORGID FROM LSISORGANIZATIONCODE 		\n");
			querySQL.append(" WHERE ORGCODE = ? 										\n");

			stmt = conn.prepareStatement( querySQL.toString() );
			stmt.setString( 1, orgCode );
			ResultSet rs = stmt.executeQuery();

			if( rs.next() )
			{
				orgID = Integer.toString( rs.getInt(1) );
			}

		}
		catch (Exception e)
		{
			Kogger.error(getClass(), e);
		}
		finally
		{
			try
			{
				if(stmt != null)
				{
					stmt.close();
				}

				if(conn != null)
				{
					conn.close();
				}

				if(res != null)
				{
					res.freeConnection(registry.getString("plm"), conn);
				}
			}
			catch (Exception e)
			{
				Kogger.error(getClass(), e);
			}
		}

		return orgID;
	}
}
