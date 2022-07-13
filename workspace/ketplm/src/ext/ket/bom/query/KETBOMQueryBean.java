package ext.ket.bom.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.change2.WTChangeOrder2;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.pom.WTConnection;
import wt.vc.Versioned;
import wt.vc.wip.WorkInProgressHelper;
import e3ps.bom.common.iba.IBAUtil;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.entity.KETPartUsageLink;
import e3ps.bom.framework.util.Registry;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.ecm.beans.ProdEcoBeans;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.util.VersionHelper;
import ext.ket.bom.util.KETBomPartUtil;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.KogDbUtil;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.TimerUtil;

public class KETBOMQueryBean {

    Registry registry = Registry.getRegistry("e3ps.bom.bom");
    boolean isEditBom = false;

    public KETBOMQueryBean() {
	// TODO Auto-generated constructor stub
    }

    /**********************************************************
     * BOM 정전개 정보 추출
     **********************************************************/

    public List<Map<String, Object>> getLatestBOM(Hashtable params) {

	List<Map<String, Object>> bomList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

	List<Map<String, Object>> newbomList = new ArrayList<Map<String, Object>>();
	Hashtable versionHash = new Hashtable();
	Hashtable versionHash2 = new Hashtable();

	KETBomPartUtil pUtil = new KETBomPartUtil();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	String partOid = "";
	String partType = "";
	String dataType = "";
	String ecoNumber = "";
	String gubun = "";

	try {

	    partOid = (String) params.get("partOid");
	    partType = (String) params.get("partType");
	    dataType = (String) params.get("dataType");
	    ecoNumber = (String) params.get("ecoNumber");
	    gubun = (String) params.get("gubun");

	    /*sql.append("SELECT * FROM (   																								\n");
	    sql.append("	SELECT BOM.*,  																								\n");
	    sql.append("	PH.NAME	STATUSKR , NVL(HD.HITEMKEY,'') AS HEADERKEY,  NVL(HD.ECOHEADERNUMBER,'') AS EONO, NVL(HD.COWORKERID,'') AS COUTERID, NVL(HD.COWORKERNAME,'') AS COUTER  					\n");
	    sql.append("	FROM 																									\n");
	    sql.append("	(																									\n");
	    sql.append("	SELECT 0 AS NUM, 																							\n");
	    sql.append("			0 AS LVL,	 																					\n");
	    sql.append("		  10 AS ITEMSEQ,																						\n");
	    sql.append("			null AS ASSEMBLY_ITEM_OID, 																				\n");
	    sql.append("			'' AS ASSEMBLY_ITEM_CODE,																				\n");
	    sql.append("			'' AS ASSEMBLY_ITEM_REV,																				\n");
	    sql.append("			 TO_CHAR(A0.IDA2A2) AS COMPONENT_ITEM_OID, 																		\n");
	    sql.append("			 A0.WTPARTNUMBER AS COMPONENT_ITEM_CODE,																		\n");
	    sql.append("			 A0.NAME AS COMPONENT_ITEM_NAME, 																			\n");
	    sql.append("			 (B0.VERSIONIDA2VERSIONINFO || '.' || B0.ITERATIONIDA2ITERATIONINFO) AS COMPONENT_ITEM_REV,												\n");
	    sql.append("			 '1.000' AS QTY,																					\n");
	    sql.append("			 B0.STATESTATE AS STATUS,	B0.IDA3A2STATE,																		\n");
	    sql.append("			 A0.DEFAULTUNIT AS UNIT, null AS IDA2A2, '' AS STARTDATE, '' AS ENDDATE,  '' AS MATERIAL, '' AS HARDNESSFROM, '' AS HARDNESSTO, '' AS DESIGNDATE, '' AS ICT, '' AS REFTOP, '' AS REFBOTTOM,	\n");
	    sql.append("			 null AS USAGELINKOID, 																					\n");
	    sql.append("			(A0.WTPARTNUMBER || '_' ||B0.VERSIONIDA2VERSIONINFO) AS ITEMINFO, '' AS PARENTITEMINFO, '' AS ECONO													\n");
	    sql.append("	FROM WTPARTMASTER A0, WTPART B0    																					\n");
	    sql.append("	WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE AND B0.LATESTITERATIONINFO=1 																	\n");
	    sql.append("	 AND B0.IDA2A2=?																							\n");
	    sql.append("	) BOM, PHASETEMPLATE PH  ,PHASELINK PL, 																				\n");
	    sql.append("	   (SELECT KEB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KEB.ECOITEMCODE||'_'||KEB.BOMVERSION) AS HITEMKEY, KEC.COWORKERID, KEC.COWORKERNAME, KEB.STATESTATE AS STATUS 					\n");
	    sql.append("	FROM KETBOMECOHEADER KEB, KETBOMECOCOWORKER KEC WHERE KEB.ECOHEADERNUMBER=KEC.ECOHEADERNUMBER AND KEB.ECOITEMCODE=KEC.ECOITEMCODE AND KEB.STATESTATE='APPROVED'									\n");
	    sql.append("       UNION																									\n");
	    // sql.append("      SELECT KB.BOMTEXT AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 								\n");
	    sql.append("      SELECT KB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 								\n");
	    // sql.append("	FROM KETBOMHEADER KB, KETBOMCOWORKER KC 																				\n");
	    // sql.append("       WHERE  KB.NEWBOMCODE=KC.NEWBOMCODE) HD																					\n");
	    sql.append("	FROM KETBOMHEADER KB, KETBOMECOCOWORKER KC 																				\n");
	    sql.append("       WHERE  KB.NEWBOMCODE=KC.ECOITEMCODE  AND KB.ECOHEADERNUMBER=KC.ECOHEADERNUMBER  AND KB.STATESTATE='APPROVED') HD																					\n");
	    sql.append("	  WHERE  PL.IDA3A5 = BOM.IDA3A2STATE                                   																	\n");
	    sql.append("	   AND PL.IDA3B5 = PH.IDA2A2                                     																	\n");
	    sql.append("	   AND PH.PHASESTATE = BOM.STATUS																					\n");
	    sql.append("	   AND BOM.ITEMINFO = HD.HITEMKEY(+) 																				\n");
	    sql.append("     UNION  																									\n");
	    sql.append("	SELECT BOM.*,  PH.NAME	STATUSKR , NVL(HD.HITEMKEY,'') AS HEADERKEY,  NVL(HD.ECOHEADERNUMBER,'') AS EONO, NVL(HD.COWORKERID,'') AS COUTERID, NVL(HD.COWORKERNAME,'') AS COUTER  			\n");
	    sql.append("	  FROM																									\n");
	    sql.append("	      (																									\n");
	    sql.append("		SELECT ROWNUM AS NUM, LEVEL AS LVL,	 X0.ITEMSEQ,																		\n");
	    sql.append("		       X0.IDA3A5 AS ASSEMBLY_ITEM_OID, 																				\n");
	    sql.append("		       X0.PARENTITEMCODE AS ASSEMBLY_ITEM_CODE,																			\n");
	    sql.append("		       (SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5) AS ASSEMBLY_ITEM_REV,													\n");
	    sql.append("		       X0.VERSIONITEMCODE AS COMPONENT_ITEM_OID, 																		\n");
	    sql.append("		       X0.CHILDITEMCODE AS COMPONENT_ITEM_CODE,																			\n");
	    sql.append("		       (SELECT NAME FROM WTPARTMASTER WHERE IDA2A2 = (SELECT IDA3MASTERREFERENCE FROM WTPART WHERE IDA2A2=X0.VERSIONITEMCODE)) AS COMPONENT_ITEM_NAME, 						\n");
	    sql.append("		       X0.CHILDITEMVERSION AS COMPONENT_ITEM_REV,																		\n");
	    sql.append("		       X0.QUANTITY AS QTY,  (SELECT STATESTATE FROM WTPART WHERE IDA2A2=X0.VERSIONITEMCODE) AS STATUS,	(SELECT IDA3A2STATE FROM WTPART WHERE IDA2A2=X0.VERSIONITEMCODE) AS IDA3A2STATE,	\n");
	    sql.append("		       X0.UNIT, X0.IDA2A2, X0.STARTDATE, X0.ENDDATE,  X0.MATERIAL, X0.HARDNESSFROM, X0.HARDNESSTO, X0.DESIGNDATE, X0.ICT AS ICT, X0.REFTOP AS REFTOP, X0.REFBOTTOM AS REFBOTTOM,	\n");
	    sql.append("		       X0.IDA2A2 AS USAGELINKOID, 																				\n");
	    sql.append("		      (X0.CHILDITEMCODE || '_' || X0.CHILDITEMVERSION) AS ITEMINFO, (X0.PARENTITEMCODE || '_' || (SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5)) AS PARENTITEMINFO, X0.ATTRIBUTE10 AS ECONO		\n");
	    sql.append("			FROM KETPARTUSAGELINK X0                               																	\n");
	    sql.append("			START WITH X0.IDA3A5 = ?                                    																\n");
	    sql.append("			      CONNECT BY PRIOR (                                   																\n");
	    sql.append("				  SELECT                                          																\n");
	    sql.append("				  MAX(B.IDA3A5)                                      																\n");
	    sql.append("				  FROM KETPARTUSAGELINK B                              																\n");
	    sql.append("				  WHERE B.PARENTITEMCODE = X0.CHILDITEMCODE  																	\n");
	    sql.append("					) = X0.IDA3A5                              																\n");
	    // 제품
	    if (!(partType.equals("D") || partType.equals("M")))
		sql.append("			      ORDER SIBLINGS BY  X0.ITEMSEQ                              															\n");
	    // 금형
	    else
		sql.append("                              ORDER SIBLINGS BY  X0.CHILDITEMCODE																		\n");

	    sql.append("	   ) BOM , PHASETEMPLATE PH  ,PHASELINK PL, 																				\n");
	    sql.append("	   ( SELECT KEB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KEB.ECOITEMCODE||'_'||KEB.BOMVERSION) AS HITEMKEY, KEC.COWORKERID, KEC.COWORKERNAME, KEB.STATESTATE AS STATUS 					\n");
	    sql.append("	FROM KETBOMECOHEADER KEB, KETBOMECOCOWORKER KEC WHERE KEB.ECOHEADERNUMBER=KEC.ECOHEADERNUMBER AND KEB.ECOITEMCODE=KEC.ECOITEMCODE  AND KEB.STATESTATE='APPROVED'									\n");
	    sql.append("       UNION																									\n");
	    // sql.append("      SELECT KB.BOMTEXT AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 								\n");
	    sql.append("      SELECT KB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 								\n");
	    // sql.append("	FROM KETBOMHEADER KB, KETBOMCOWORKER KC 																				\n");
	    sql.append("	FROM KETBOMHEADER KB, KETBOMECOCOWORKER KC 																				\n");
	    sql.append("       WHERE  KB.NEWBOMCODE=KC.ECOITEMCODE  AND KB.ECOHEADERNUMBER=KC.ECOHEADERNUMBER  AND KB.STATESTATE='APPROVED') HD																					\n");
	    sql.append("      WHERE  PL.IDA3A5 = BOM.IDA3A2STATE                                   																	\n");
	    sql.append("	 AND PL.IDA3B5 = PH.IDA2A2                                     																		\n");
	    sql.append("	 AND PH.PHASESTATE = BOM.STATUS																						\n");
	    sql.append("       AND (BOM.PARENTITEMINFO ||'_'||BOM.COMPONENT_ITEM_CODE) = HD.HITEMKEY(+)  																\n");
	    sql.append("     )  																									\n");
	    sql.append("     ORDER BY NUM 																								\n");*/
	    
	    
	    sql.append("SELECT * FROM (                                                                                                                                                                                                                      \n");
	    sql.append("               SELECT BOM.*,PH.NAME    STATUSKR , '' AS HEADERKEY,  '' AS EONO, '' AS COUTERID, '' AS COUTER FROM                                                                                                                    \n");
	    sql.append("               (                                                                                                                                                                                                                     \n");
	    sql.append("                SELECT 0 AS NUM,                                                                                                                                                                                                     \n");
	    sql.append("                       0 AS LVL,                                                                                                                                                                                                     \n");
	    sql.append("                      10 AS ITEMSEQ,                                                                                                                                                                                                 \n");
	    sql.append("                    null AS ASSEMBLY_ITEM_OID,                                                                                                                                                                                       \n");
	    sql.append("                      '' AS ASSEMBLY_ITEM_CODE,                                                                                                                                                                                      \n");
	    sql.append("                      '' AS ASSEMBLY_ITEM_REV,                                                                                                                                                                                       \n");
	    sql.append("                      TO_CHAR(A0.IDA2A2) AS COMPONENT_ITEM_OID,                                                                                                                                                                      \n");
	    sql.append("                      A0.WTPARTNUMBER AS COMPONENT_ITEM_CODE,                                                                                                                                                                        \n");
	    sql.append("                      A0.NAME AS COMPONENT_ITEM_NAME,                                                                                                                                                                                \n");
	    sql.append("                      B0.VERSIONIDA2VERSIONINFO AS COMPONENT_ITEM_REV,                                                                                                                      \n");
	    sql.append("                     '1.000' AS QTY,  B0.STATESTATE AS STATUS,    B0.IDA3A2STATE,                                                                                                                                                    \n");
	    sql.append("                     'KET_EA' AS UNIT, null AS IDA2A2, '' AS STARTDATE, '' AS ENDDATE,  '' AS MATERIAL, '' AS HARDNESSFROM, '' AS HARDNESSTO, '' AS DESIGNDATE, '' AS ICT, '' AS REFTOP, '' AS REFBOTTOM,                            \n");
	    sql.append("                      null AS USAGELINKOID,                                                                                                                                                                                          \n");
	    sql.append("                     (A0.WTPARTNUMBER || '_' ||B0.VERSIONIDA2VERSIONINFO) AS ITEMINFO, '' AS PARENTITEMINFO, PTC_STR_150TYPEINFOWTPART AS ECONO, PTC_STR_2TYPEINFOWTPART as NEW_COMPONENT_ITEM_REV                                   \n");       
	    sql.append("                 FROM WTPARTMASTER A0, WTPART B0                                                                                                                                                                                     \n");
	    sql.append("                WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE AND B0.LATESTITERATIONINFO=1                                                                                                                                                \n");
	    sql.append("                  AND B0.IDA2A2= ?                                                                                                                                                                                                   \n");
	    sql.append("               ) BOM, PHASETEMPLATE PH  ,PHASELINK PL                                                                                                                                                                                \n");
	    sql.append("               WHERE  PL.IDA3A5 = BOM.IDA3A2STATE                                                                                                                                                                                    \n");
	    sql.append("                 AND PL.IDA3B5 = PH.IDA2A2                                                                                                                                                                                           \n");
	    sql.append("                 AND PH.PHASESTATE = BOM.STATUS                                                                                                                                                                                      \n");
	    sql.append("               UNION                                                                                                                                                                                                                 \n");
	    sql.append("              SELECT BOM.*,  PH.NAME    STATUSKR , '' AS HEADERKEY,  '' AS EONO, '' AS COUTERID, '' AS COUTER FROM                                                                                                                   \n");
	    sql.append("              (                                                                                                                                                                                                                      \n");
	    sql.append("               SELECT ROWNUM AS NUM,                                                                                                                                                                                                 \n");
	    sql.append("                      LEVEL AS LVL,                                                                                                                                                                                                  \n");
	    sql.append("                      X0.ITEMSEQ,                                                                                                                                                                                                    \n");
	    sql.append("                      X0.IDA3A5 AS ASSEMBLY_ITEM_OID,                                                                                                                                                                                \n");
	    sql.append("                      X0.PARENTITEMCODE AS ASSEMBLY_ITEM_CODE,                                                                                                                                                                       \n");
	    sql.append("                      (SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5) AS ASSEMBLY_ITEM_REV,                                                                                                                       \n");
	    sql.append("                      X0.VERSIONITEMCODE AS COMPONENT_ITEM_OID,                                                                                                                                                                      \n");
	    sql.append("                      X0.CHILDITEMCODE AS COMPONENT_ITEM_CODE,                                                                                                                                                                       \n");
	    sql.append("                      B.NAME AS COMPONENT_ITEM_NAME,                                                                                                                                                                                 \n");
	    sql.append("                      A.VERSIONIDA2VERSIONINFO AS COMPONENT_ITEM_REV,                                                                                                                                                                     \n");
	    sql.append("                      X0.QUANTITY AS QTY,  A.STATESTATE AS STATUS,    A.IDA3A2STATE AS IDA3A2STATE,                                                                                                                                  \n");
	    sql.append("                      X0.UNIT, X0.IDA2A2, X0.STARTDATE, X0.ENDDATE,  X0.MATERIAL, X0.HARDNESSFROM, X0.HARDNESSTO, X0.DESIGNDATE, X0.ICT AS ICT, X0.REFTOP AS REFTOP, X0.REFBOTTOM AS REFBOTTOM,                                      \n");
	    sql.append("                      X0.IDA2A2 AS USAGELINKOID,                                                                                                                                                                                     \n");
	    sql.append("                     (X0.CHILDITEMCODE || '_' || X0.CHILDITEMVERSION) AS ITEMINFO, '' as PARENTITEMINFO, a.PTC_STR_150TYPEINFOWTPART AS ECONO, PTC_STR_2TYPEINFOWTPART as NEW_COMPONENT_ITEM_REV                                     \n");
	    sql.append("                 FROM ( SELECT B.*,(SELECT MAX(P.IDA2A2) FROM WTPART P WHERE P.LATESTITERATIONINFO = 1  AND P.STATECHECKOUTINFO != 'wrk'  AND P.STATESTATE = 'APPROVED' AND P.IDA3MASTERREFERENCE = B.IDA3B5 ) AS LAST_WTPART_OID    \n");
	    sql.append("                          FROM KETPARTUSAGELINK B ) X0, WTPART A, WTPARTMASTER B                                                                                                                                                     \n");
	    sql.append("                         WHERE X0.LAST_WTPART_OID = A.IDA2A2                                                                                                                                                                         \n");
	    sql.append("                           AND A.IDA3MASTERREFERENCE = B.IDA2A2                                                                                                                                                                      \n");
	    sql.append("                           START WITH X0.IDA3A5  = ?                                                                                                                                                                                 \n");
	    sql.append("                           CONNECT BY PRIOR X0.LAST_WTPART_OID =  X0.IDA3A5                                                                                                                                                          \n");
	    
	    if (!(partType.equals("D") || partType.equals("M")))
		sql.append("			      ORDER SIBLINGS BY  X0.ITEMSEQ                              															\n");
	    // 금형
	    else
		sql.append("                          ORDER SIBLINGS BY  X0.CHILDITEMCODE																		\n");
	    
	    sql.append("                              ) BOM , PHASETEMPLATE PH  ,PHASELINK PL                                                                                                                                         \n");
	    sql.append("                 WHERE  PL.IDA3A5 = BOM.IDA3A2STATE                                                                                                                                                                                  \n");
	    sql.append("                   AND PL.IDA3B5 = PH.IDA2A2                                                                                                                                                                                         \n");
	    sql.append("                   AND PH.PHASESTATE = BOM.STATUS                                                                                                                                                                                    \n");
	    sql.append(")                                                                                                                                                                                                                                    \n");
	    sql.append("   ORDER BY NUM                                                                                                                                                                                                                      \n");
	    
	    TimerUtil timer = new TimerUtil(getClass().getName());
	    timer.setStartPoint("latestBomQueryStart");
	    
	    // Kogger.debug(getClass(), "SQL===> " + sql.toString());
	    Kogger.debug(getClass(), "partOid===> " + partOid);

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, partOid);
	    pstmt.setString(2, partOid);
	    rs = pstmt.executeQuery();
	    
	    timer.setEndPoint();
	    
	    String partNo = "";
	    String rev = "";
	    String newrev = "";
	    String partName = "";
	    String lvl = "";
	    String seq = "";
	    String qty = "";
	    String unit = "";
	    String econo = "";
	    String econo2 = "";
	    String checkout = "";
	    String checkoutId = "";
	    String ict = "";
	    String reftop = "";
	    String refbtm = "";
	    String material = "";
	    String hardnessFrom = "";
	    String hardnessTo = "";
	    String designDate = "";
	    String state = "";
	    String parentNo = "";
	    String pver = "";

	    String bom_path = "";
	    int i = 0;

	    int old_lvl = 0;

	    timer.setStartPoint("latestBomConvertList");
	    while (rs.next()) {

		//Kogger.debug(getClass(), "getLatestBOM");
		Map<String, Object> data = new Hashtable();

		partNo = rs.getString("COMPONENT_ITEM_CODE") == null ? "" : rs.getString("COMPONENT_ITEM_CODE").trim();
		rev = rs.getString("COMPONENT_ITEM_REV") == null ? "" : rs.getString("COMPONENT_ITEM_REV").trim();

		//String poid = getPartOid(partNo, rev);
		//WTPart part = pUtil.getPart(poid);

		//newrev = getNewVersionCode(partNo, rev);  //bom 속도개선의 일환으로 주석처리 2019.03.13 by 황정태
		newrev = rs.getString("NEW_COMPONENT_ITEM_REV") == null ? "" : rs.getString("NEW_COMPONENT_ITEM_REV").trim(); //newrev는 최신 승인부품의 버전을 쿼리에서 땡겨오도록 변경 2019.03.13 by 황정태
		partName = rs.getString("COMPONENT_ITEM_NAME") == null ? "" : rs.getString("COMPONENT_ITEM_NAME").trim();
		lvl = rs.getString("LVL") == null ? "" : rs.getString("LVL").trim();
		int new_lvl = Integer.parseInt(lvl);
		seq = rs.getString("ITEMSEQ") == null ? "" : rs.getString("ITEMSEQ").trim();
		qty = rs.getString("QTY") == null ? "" : rs.getString("QTY").trim();
		unit = rs.getString("UNIT") == null ? "" : rs.getString("UNIT").trim();

		if (!unit.equals("") && unit.indexOf("KET_") == -1) {
		    unit = "KET_" + unit;
		}

		if (unit.indexOf("KET_%") != -1)
		    unit = "KET_PERCENT";

		// Kogger.debug(getClass(), "unit===>" + unit);

		//econo = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpEoNo); //bom 속도개선의 일환으로 주석처리 2019.03.13 by 황정태

		/*if (econo == null || econo.equals("")) { //bom 속도개선의 일환으로 주석처리 2019.03.13 by 황정태
		    econo = rs.getString("EONO") == null ? "" : rs.getString("EONO").trim();
		}

		econo2 = rs.getString("ECONO") == null ? "" : rs.getString("ECONO").trim();*/
		
		econo = rs.getString("ECONO") == null ? "" : rs.getString("ECONO").trim();
		
		checkout = rs.getString("COUTER") == null ? "" : rs.getString("COUTER").trim();
		// checkoutId = rs.getString("COUTERID") == null ? "" : rs.getString("COUTERID").trim();
		checkoutId = "";

		// if (!ecoNumber.equals("") && lvl != null && lvl.equals("0")) {
		
		// 작업자 표시가 안될테지만.. 어차피 중요하지 않는 정보이므로 bom 속도개선의 일환으로 주석처리 2019.03.13 by 황정태 
		/*if (ecoNumber != null && !ecoNumber.equals("") && gubun != null && !gubun.equals("") && i == 0) {
		    Hashtable chargerH = new Hashtable();
		    if (gubun.indexOf("NEWBOM") != -1) {
			chargerH = (Hashtable) getChargerHash2(ecoNumber, "KETBOMHEADER");
		    } else {
			chargerH = (Hashtable) getChargerHash2(ecoNumber, "KETBOMECOHEADER");
		    }

		    Hashtable chargerInfo = new Hashtable();

		    if (chargerH.containsKey(partNo)) {
			chargerInfo = (Hashtable) chargerH.get(partNo);

			checkout = (String) chargerInfo.get("COWORKERNAME");
			checkoutId = (String) chargerInfo.get("COWORKERID");
		    }

		}*/

		ict = rs.getString("ICT") == null ? "" : rs.getString("ICT").trim();
		reftop = rs.getString("REFTOP") == null ? "" : rs.getString("REFTOP").trim();
		refbtm = rs.getString("REFBOTTOM") == null ? "" : rs.getString("REFBOTTOM").trim();
		// material = rs.getString("MATERIAL") == null ? "" : rs.getString("MATERIAL").trim();
		if ((partType.equals("D") || partType.equals("M")))
		    material = getMaterial(partNo, rev);

		hardnessFrom = rs.getString("HARDNESSFROM") == null ? "" : rs.getString("HARDNESSFROM").trim();
		hardnessTo = rs.getString("HARDNESSTO") == null ? "" : rs.getString("HARDNESSTO").trim();
		designDate = rs.getString("DESIGNDATE") == null ? "" : rs.getString("DESIGNDATE").trim();
		state = rs.getString("STATUSKR") == null ? "" : rs.getString("STATUSKR").trim();

		// state = part.getLifeCycleState().getDisplay();

		parentNo = rs.getString("ASSEMBLY_ITEM_CODE") == null ? "" : rs.getString("ASSEMBLY_ITEM_CODE").trim();
		pver = rs.getString("ASSEMBLY_ITEM_REV") == null ? "" : rs.getString("ASSEMBLY_ITEM_REV").trim();

		/*if (!parentNo.equals("")) {//최상위 루트가 아닐 떄

		    String newpver = "";
		    try {
			versionHash.put(parentNo, pver);//부모품번, windchill 파트 버전을 담는다
			newpver = getNewVersionCode(parentNo, pver); 
			versionHash2.put(parentNo, newpver);//부모품번 ket 파트 버전을 담는다
		    } catch (Exception ex) {
			Kogger.error(getClass(), ex);
		    }

		}*/

		if (lvl.equals("0")) {
		    qty = getBoxQty(partNo, rev);

		}

		if (new_lvl > old_lvl) {
		    bom_path += lvl + "^" + parentNo + "|";
		} else {
		    if (new_lvl < old_lvl)
			bom_path = bom_path.substring(0, bom_path.indexOf(lvl + "^" + parentNo + "|")) + lvl + "^" + parentNo + "|";
		}

		/*if ((partType.equals("D") || partType.equals("M"))) {
		    // rev = getLatestPartRev(partNo);
		    newrev = getNewVersionCode(partNo, rev);
		}*/

		data.put("ict", ict);
		data.put("partNo", partNo);
		data.put("rev", rev);
		data.put("newrev", newrev);
		data.put("partName", partName);
		data.put("lvl", lvl);
		data.put("seq", seq);
		data.put("qty", qty);
		data.put("unit", unit);

		data.put("econo", econo);
		
		/*if (!econo.equals("")) {
		    data.put("econo", econo);
		} else {
		    data.put("econo", econo2);
		}*/

		data.put("checkout", checkout);
		data.put("checkoutId", checkoutId);
		data.put("reftop", reftop);
		data.put("refbtm", refbtm);
		data.put("material", material);
		data.put("hardnessFrom", hardnessFrom);
		data.put("hardnessTo", hardnessTo);
		data.put("designDate", designDate);
		data.put("state", state);
		data.put("parentNo", parentNo);
		data.put("pver", pver);

		data.put("bom_path", bom_path);

		bomList.add(data);
		old_lvl = new_lvl;
		i++;
	    }

	    /*for (int j = 0; j < bomList.size(); j++) {
		Hashtable data = (Hashtable) bomList.get(j);
		String oldPartNo = (String) data.get("partNo");
		String oldrev = (String) data.get("rev");

		String newRev1 = "";
		String newRev2 = "";

		if (j != 0) {
		    if (versionHash.containsKey(oldPartNo)) {
			newRev1 = (String) versionHash.get(oldPartNo);
			newRev2 = (String) versionHash2.get(oldPartNo);

			String poid = getPartOid(oldPartNo, newRev1);
			WTPart part = pUtil.getPart(poid);
			state = part.getLifeCycleState().getDisplay();
			String pstate = part.getState().toString();

			if (pstate.equals("APPROVED")) {

			    if (!oldrev.equals(newRev1)) {

				data.put("state", state);

				data.put("rev", newRev1);
				data.put("newrev", newRev2);
			    }
			}
		    }
		}

		newbomList.add(data);
	    }*/
	    
	    timer.setEndPoint();
	    // Kogger.debug(getClass(), "bomList.size()===> " + bomList.size());
	    timer.setStartPoint("latestBomConvertGrid");
	    if (dataType.equals("TreeGrid")) {

		treeList = getGridBOM(bomList);
	    } else {
		treeList = bomList;
	    }
	    timer.setEndPoint();
	    timer.display();
	    // Kogger.debug(getClass(), treeList.toString());
	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
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
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return treeList;
    }

    /**********************************************************
     * BOM 정전개 Version별정보 추출 function
     **********************************************************/

    public List<Map<String, Object>> getLatestVersionBOM(Hashtable params) {

	List<Map<String, Object>> bomList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();
	KETBomPartUtil pUtil = new KETBomPartUtil();

	String partOid = "";
	String partType = "";
	String dataType = "";

	try {

	    partOid = (String) params.get("partOid");
	    partType = (String) params.get("partType");
	    dataType = (String) params.get("dataType");

	    sql.append("SELECT * FROM (   																								\n");
	    sql.append("	SELECT BOM.*,  																								\n");
	    sql.append("	PH.NAME	STATUSKR , NVL(HD.HITEMKEY,'') AS HEADERKEY,  NVL(HD.ECOHEADERNUMBER,'') AS EONO, NVL(HD.COWORKERID,'') AS COUTERID, NVL(HD.COWORKERNAME,'') AS COUTER  					\n");
	    sql.append("	FROM 																									\n");
	    sql.append("	(																									\n");
	    sql.append("	SELECT 0 AS NUM, 																							\n");
	    sql.append("			0 AS LVL,	 																					\n");
	    sql.append("		  10 AS ITEMSEQ,																						\n");
	    sql.append("			null AS ASSEMBLY_ITEM_OID, 																				\n");
	    sql.append("			'' AS ASSEMBLY_ITEM_CODE,																				\n");
	    sql.append("			'' AS ASSEMBLY_ITEM_REV,																				\n");
	    sql.append("			 TO_CHAR(A0.IDA2A2) AS COMPONENT_ITEM_OID, 																		\n");
	    sql.append("			 A0.WTPARTNUMBER AS COMPONENT_ITEM_CODE,																		\n");
	    sql.append("			 A0.NAME AS COMPONENT_ITEM_NAME, 																			\n");
	    sql.append("			 (B0.VERSIONIDA2VERSIONINFO || '.' || B0.ITERATIONIDA2ITERATIONINFO) AS COMPONENT_ITEM_REV,												\n");
	    sql.append("			 '1.000' AS QTY,  B0.STATESTATE AS STATUS,	B0.IDA3A2STATE,																\n");
	    sql.append("			 'KET_EA' AS UNIT, null AS IDA2A2, '' AS STARTDATE, '' AS ENDDATE,  '' AS MATERIAL, '' AS HARDNESSFROM, '' AS HARDNESSTO, '' AS DESIGNDATE, '' AS ICT, '' AS REFTOP, '' AS REFBOTTOM,	\n");
	    sql.append("			 null AS USAGELINKOID, 																					\n");
	    sql.append("			(A0.WTPARTNUMBER || '_' ||B0.VERSIONIDA2VERSIONINFO) AS ITEMINFO, '' AS PARENTITEMINFO, '' AS ECONO												\n");
	    sql.append("	FROM WTPARTMASTER A0, WTPART B0    																					\n");
	    sql.append("	WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE AND B0.LATESTITERATIONINFO=1 																	\n");
	    sql.append("	 AND B0.IDA2A2=?																							\n");
	    sql.append("	) BOM, PHASETEMPLATE PH  ,PHASELINK PL, 																				\n");
	    sql.append("	   (SELECT KEB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KEB.ECOITEMCODE||'_'||KEB.BOMVERSION) AS HITEMKEY, KEC.COWORKERID, KEC.COWORKERNAME, KEB.STATESTATE AS STATUS 					\n");
	    sql.append("	      FROM KETBOMECOHEADER KEB, KETBOMECOCOWORKER KEC WHERE KEB.ECOHEADERNUMBER=KEC.ECOHEADERNUMBER AND KEB.ECOITEMCODE=KEC.ECOITEMCODE	 AND KEB.STATESTATE='APPROVED'								\n");
	    sql.append("             UNION																								\n");
	    // sql.append("            SELECT KB.BOMTEXT AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 							\n");
	    sql.append("            SELECT KB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 							\n");
	    // sql.append("	      FROM KETBOMHEADER KB, KETBOMCOWORKER KC 																				\n");
	    // sql.append("             WHERE  KB.NEWBOMCODE=KC.NEWBOMCODE   ) HD																				\n");
	    sql.append("	      FROM KETBOMHEADER KB, KETBOMECOCOWORKER KC 																				\n");
	    sql.append("             WHERE  KB.NEWBOMCODE=KC.ECOITEMCODE    AND KB.ECOHEADERNUMBER=KC.ECOHEADERNUMBER  AND KB.STATESTATE='APPROVED' ) HD																				\n");
	    sql.append("	  WHERE  PL.IDA3A5 = BOM.IDA3A2STATE                                   																	\n");
	    sql.append("	   AND PL.IDA3B5 = PH.IDA2A2                                     																	\n");
	    sql.append("	   AND PH.PHASESTATE = BOM.STATUS																					\n");
	    sql.append("	   AND BOM.ITEMINFO = HD.HITEMKEY(+) 																					\n");
	    sql.append("     UNION  																									\n");
	    sql.append("	SELECT BOM.*,  PH.NAME	STATUSKR , NVL(HD.HITEMKEY,'') AS HEADERKEY,  NVL(HD.ECOHEADERNUMBER,'') AS EONO, NVL(HD.COWORKERID,'') AS COUTERID, NVL(HD.COWORKERNAME,'') AS COUTER  			\n");
	    sql.append("	  FROM																									\n");
	    sql.append("	      (																									\n");
	    sql.append("		SELECT ROWNUM AS NUM, LEVEL AS LVL,	 X0.ITEMSEQ,																		\n");
	    sql.append("		       X0.IDA3A5 AS ASSEMBLY_ITEM_OID, 																				\n");
	    sql.append("		       X0.PARENTITEMCODE AS ASSEMBLY_ITEM_CODE,																			\n");
	    sql.append("		       (SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5) AS ASSEMBLY_ITEM_REV,													\n");
	    sql.append("		       X0.VERSIONITEMCODE AS COMPONENT_ITEM_OID, 																		\n");
	    sql.append("		       X0.CHILDITEMCODE AS COMPONENT_ITEM_CODE,																			\n");
	    sql.append("		       (SELECT NAME FROM WTPARTMASTER WHERE IDA2A2 = (SELECT IDA3MASTERREFERENCE FROM WTPART WHERE IDA2A2=X0.VERSIONITEMCODE)) AS COMPONENT_ITEM_NAME, 						\n");
	    sql.append("		       X0.CHILDITEMVERSION AS COMPONENT_ITEM_REV,																		\n");
	    sql.append("		       X0.QUANTITY AS QTY,  (SELECT STATESTATE FROM WTPART WHERE IDA2A2=X0.VERSIONITEMCODE) AS STATUS,	(SELECT IDA3A2STATE FROM WTPART WHERE IDA2A2=X0.VERSIONITEMCODE) AS IDA3A2STATE,	\n");
	    sql.append("		       X0.UNIT, X0.IDA2A2, X0.STARTDATE, X0.ENDDATE,  X0.MATERIAL, X0.HARDNESSFROM, X0.HARDNESSTO, X0.DESIGNDATE, X0.ICT AS ICT, X0.REFTOP AS REFTOP, X0.REFBOTTOM AS REFBOTTOM,	\n");
	    sql.append("		       X0.IDA2A2 AS USAGELINKOID, 																				\n");
	    sql.append("		      (X0.CHILDITEMCODE || '_' || X0.CHILDITEMVERSION) AS ITEMINFO, (X0.PARENTITEMCODE || '_' || (SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5)) AS PARENTITEMINFO, X0.ATTRIBUTE10 AS ECONO			\n");
	    sql.append("			FROM KETPARTUSAGELINK X0                               																	\n");
	    sql.append("			START WITH X0.IDA3A5 = ?                                    																\n");
	    sql.append("			      CONNECT BY PRIOR (                                   																\n");
	    sql.append("				  SELECT                                          																\n");
	    sql.append("				  MAX(B.IDA3A5)                                      																\n");
	    sql.append("				  FROM KETPARTUSAGELINK B                              																\n");
	    sql.append("				  WHERE B.PARENTITEMCODE = X0.CHILDITEMCODE   																	\n");
	    // sql.append("				  WHERE B.PARENTITEMCODE = X0.CHILDITEMCODE  AND (SELECT VERSIONIDA2VERSIONINFO  												\n");
	    // sql.append("           			                                                    FROM WTPART WHERE IDA2A2=B.IDA3A5)=(CASE INSTR(X0.BOMVERSION,'.')  								\n");
	    // sql.append("                                                				                                         WHEN 0 THEN BOMVERSION  								\n");
	    // sql.append("                                                									 ELSE  SUBSTR(BOMVERSION,0,INSTR(BOMVERSION,'.')-1) END )				\n");
	    sql.append("					) = X0.IDA3A5                              																\n");
	    // 제품
	    if (!(partType.equals("D") || partType.equals("M")))
		sql.append("			      ORDER SIBLINGS BY  X0.ITEMSEQ                              															\n");
	    // 금형
	    else
		sql.append("                          ORDER SIBLINGS BY  X0.CHILDITEMCODE																		\n");

	    sql.append("	   ) BOM , PHASETEMPLATE PH  ,PHASELINK PL, 																				\n");
	    sql.append("	   (SELECT KEB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KEB.ECOITEMCODE||'_'||KEB.BOMVERSION) AS HITEMKEY, KEC.COWORKERID, KEC.COWORKERNAME, KEB.STATESTATE AS STATUS 					\n");
	    sql.append("	      FROM KETBOMECOHEADER KEB, KETBOMECOCOWORKER KEC WHERE KEB.ECOHEADERNUMBER=KEC.ECOHEADERNUMBER AND KEB.ECOITEMCODE=KEC.ECOITEMCODE	 AND KEB.STATESTATE='APPROVED'					\n");
	    sql.append("             UNION																								\n");
	    // sql.append("            SELECT KB.BOMTEXT AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 							\n");
	    sql.append("            SELECT KB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 							\n");
	    // sql.append("	      FROM KETBOMHEADER KB, KETBOMCOWORKER KC 																				\n");
	    // sql.append("             WHERE  KB.NEWBOMCODE=KC.NEWBOMCODE ) HD																				\n");
	    sql.append("	      FROM KETBOMHEADER KB, KETBOMECOCOWORKER KC 																				\n");
	    sql.append("             WHERE  KB.NEWBOMCODE=KC.ECOITEMCODE  AND KB.ECOHEADERNUMBER=KC.ECOHEADERNUMBER  AND KB.STATESTATE='APPROVED' ) HD																				\n");
	    sql.append("      WHERE  PL.IDA3A5 = BOM.IDA3A2STATE                                   																	\n");
	    sql.append("	 AND PL.IDA3B5 = PH.IDA2A2                                     																		\n");
	    sql.append("	 AND PH.PHASESTATE = BOM.STATUS																						\n");
	    sql.append("       AND (BOM.PARENTITEMINFO ||'_'||BOM.COMPONENT_ITEM_CODE) = HD.HITEMKEY(+)  																\n");
	    sql.append("     )  																									\n");
	    sql.append("     ORDER BY NUM 																								\n");
	    // Kogger.debug(getClass(), "SQL===> " + sql.toString());
	    // Kogger.debug(getClass(), "partOid===> " + partOid);
	    
	    TimerUtil timer = new TimerUtil(getClass().getName());
	    timer.setStartPoint("latestBomQueryStart");

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, partOid);
	    pstmt.setString(2, partOid);
	    // pstmt.setString(3, partOid);
	    rs = pstmt.executeQuery();
	    
	    timer.setEndPoint();
	    
	    String partNo = "";
	    String rev = "";
	    String newrev = "";
	    String partName = "";
	    String lvl = "";
	    String seq = "";
	    String qty = "";
	    String unit = "";
	    String econo = "";
	    String econo2 = "";
	    String checkout = "";
	    String checkoutId = "";
	    String ict = "";
	    String reftop = "";
	    String refbtm = "";
	    String material = "";
	    String hardnessFrom = "";
	    String hardnessTo = "";
	    String designDate = "";
	    String state = "";
	    String parentNo = "";
	    String pver = "";

	    String bom_path = "";
	    int i = 0;

	    int old_lvl = 0;

	    timer.setStartPoint("latestBomConvertList");
	    
	    while (rs.next()) {

		Kogger.debug(getClass(), "getLatestVersionBOM");
		Map<String, Object> data = new Hashtable();

		partNo = rs.getString("COMPONENT_ITEM_CODE") == null ? "" : rs.getString("COMPONENT_ITEM_CODE").trim();
		rev = rs.getString("COMPONENT_ITEM_REV") == null ? "" : rs.getString("COMPONENT_ITEM_REV").trim();
		newrev = getNewVersionCode(partNo, rev);
		partName = rs.getString("COMPONENT_ITEM_NAME") == null ? "" : rs.getString("COMPONENT_ITEM_NAME").trim();
		lvl = rs.getString("LVL") == null ? "" : rs.getString("LVL").trim();
		int new_lvl = Integer.parseInt(lvl);
		seq = rs.getString("ITEMSEQ") == null ? "" : rs.getString("ITEMSEQ").trim();
		qty = rs.getString("QTY") == null ? "" : rs.getString("QTY").trim();
		unit = rs.getString("UNIT") == null ? "" : rs.getString("UNIT").trim();

		if (!unit.equals("") && unit.indexOf("KET_") == -1) {
		    unit = "KET_" + unit;
		}

		if (unit.indexOf("KET_%") != -1)
		    unit = "KET_PERCENT";

		String poid = getPartOid(partNo, rev);
		WTPart part = pUtil.getPart(poid);
		econo = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpEoNo);

		if (econo == null || econo.equals("")) {
		    econo = rs.getString("EONO") == null ? "" : rs.getString("EONO").trim();
		}

		econo2 = rs.getString("ECONO") == null ? "" : rs.getString("ECONO").trim();
		checkout = rs.getString("COUTER") == null ? "" : rs.getString("COUTER").trim();
		checkoutId = rs.getString("COUTERID") == null ? "" : rs.getString("COUTERID").trim();
		ict = rs.getString("ICT") == null ? "" : rs.getString("ICT").trim();
		reftop = rs.getString("REFTOP") == null ? "" : rs.getString("REFTOP").trim();
		refbtm = rs.getString("REFBOTTOM") == null ? "" : rs.getString("REFBOTTOM").trim();
		// material = rs.getString("MATERIAL") == null ? "" : rs.getString("MATERIAL").trim();
		if ((partType.equals("D") || partType.equals("M")))
		    material = getMaterial(partNo, rev);
		hardnessFrom = rs.getString("HARDNESSFROM") == null ? "" : rs.getString("HARDNESSFROM").trim();
		hardnessTo = rs.getString("HARDNESSTO") == null ? "" : rs.getString("HARDNESSTO").trim();
		designDate = rs.getString("DESIGNDATE") == null ? "" : rs.getString("DESIGNDATE").trim();
		state = rs.getString("STATUSKR") == null ? "" : rs.getString("STATUSKR").trim();
		parentNo = rs.getString("ASSEMBLY_ITEM_CODE") == null ? "" : rs.getString("ASSEMBLY_ITEM_CODE").trim();
		pver = rs.getString("ASSEMBLY_ITEM_REV") == null ? "" : rs.getString("ASSEMBLY_ITEM_REV").trim();

		if (lvl.equals("0")) {
		    qty = getBoxQty(partNo, rev);
		}

		if (new_lvl > old_lvl) {
		    bom_path += lvl + "^" + parentNo + "|";
		} else {
		    if (new_lvl < old_lvl)
			bom_path = bom_path.substring(0, bom_path.indexOf(lvl + "^" + parentNo + "|")) + lvl + "^" + parentNo + "|";
		}

		// Kogger.debug(getClass(), "partNo==========>" + partNo);

		data.put("ict", ict);
		data.put("partNo", partNo);
		data.put("rev", rev);
		data.put("newrev", newrev);
		data.put("partName", partName);
		data.put("lvl", lvl);
		data.put("seq", seq);
		data.put("qty", qty);
		data.put("unit", unit);

		if (!econo.equals("")) {
		    data.put("econo", econo);
		} else {
		    data.put("econo", econo2);
		}

		data.put("checkout", checkout);
		// data.put("checkoutId", checkoutId);
		data.put("checkoutId", "");
		data.put("reftop", reftop);
		data.put("refbtm", refbtm);
		data.put("material", material);
		data.put("hardnessFrom", hardnessFrom);
		data.put("hardnessTo", hardnessTo);
		data.put("designDate", designDate);
		data.put("state", state);
		data.put("parentNo", parentNo);
		data.put("pver", pver);

		data.put("bom_path", bom_path);

		bomList.add(data);
		old_lvl = new_lvl;
	    }
	    
	    timer.setEndPoint();
	    
	    // Kogger.debug(getClass(), "bomList.size()===> " + bomList.size());
	    
	    timer.setStartPoint("latestBomConvertGrid");
	    
	    if (dataType.equals("TreeGrid")) {

		treeList = getGridBOM(bomList);
	    } else {
		treeList = bomList;
	    }
	    
	    timer.setEndPoint();

	    // Kogger.debug(getClass(), treeList.toString());
	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
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
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return treeList;
    }

    /**********************************************************
     * BOM 정전개 Version별정보 추출 function
     **********************************************************/

    public List<Map<String, Object>> getVersionBOM(Hashtable params) {

	List<Map<String, Object>> bomList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();
	KETBomPartUtil pUtil = new KETBomPartUtil();

	String partOid = "";
	String partType = "";
	String dataType = "";

	try {

	    partOid = (String) params.get("partOid");
	    partType = (String) params.get("partType");
	    dataType = (String) params.get("dataType");

	    sql.append("SELECT * FROM (   																								\n");
	    sql.append("	SELECT BOM.*,  																								\n");
	    sql.append("	PH.NAME	STATUSKR , NVL(HD.HITEMKEY,'') AS HEADERKEY,  NVL(HD.ECOHEADERNUMBER,'') AS EONO, NVL(HD.COWORKERID,'') AS COUTERID, NVL(HD.COWORKERNAME,'') AS COUTER  					\n");
	    sql.append("	FROM 																									\n");
	    sql.append("	(																									\n");
	    sql.append("	SELECT 0 AS NUM, 																							\n");
	    sql.append("			0 AS LVL,	 																					\n");
	    sql.append("		  10 AS ITEMSEQ,																						\n");
	    sql.append("			null AS ASSEMBLY_ITEM_OID, 																				\n");
	    sql.append("			'' AS ASSEMBLY_ITEM_CODE,																				\n");
	    sql.append("			'' AS ASSEMBLY_ITEM_REV,																				\n");
	    sql.append("			 TO_CHAR(A0.IDA2A2) AS COMPONENT_ITEM_OID, 																		\n");
	    sql.append("			 A0.WTPARTNUMBER AS COMPONENT_ITEM_CODE,																		\n");
	    sql.append("			 A0.NAME AS COMPONENT_ITEM_NAME, 																			\n");
	    sql.append("			 (B0.VERSIONIDA2VERSIONINFO || '.' || B0.ITERATIONIDA2ITERATIONINFO) AS COMPONENT_ITEM_REV,												\n");
	    sql.append("			 '1.000' AS QTY,  B0.STATESTATE AS STATUS,	B0.IDA3A2STATE,																\n");
	    sql.append("			 'KET_EA' AS UNIT, null AS IDA2A2, '' AS STARTDATE, '' AS ENDDATE,  '' AS MATERIAL, '' AS HARDNESSFROM, '' AS HARDNESSTO, '' AS DESIGNDATE, '' AS ICT, '' AS REFTOP, '' AS REFBOTTOM,	\n");
	    sql.append("			 null AS USAGELINKOID, 																					\n");
	    sql.append("			(A0.WTPARTNUMBER || '_' ||B0.VERSIONIDA2VERSIONINFO) AS ITEMINFO, '' AS PARENTITEMINFO, '' AS ECONO													\n");
	    sql.append("	FROM WTPARTMASTER A0, WTPART B0    																					\n");
	    sql.append("	WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE AND B0.LATESTITERATIONINFO=1 																	\n");
	    sql.append("	 AND B0.IDA2A2=?																						\n");
	    sql.append("	) BOM, PHASETEMPLATE PH  ,PHASELINK PL, 																				\n");
	    sql.append("	   (SELECT KEB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KEB.ECOITEMCODE||'_'||KEB.BOMVERSION) AS HITEMKEY, KEC.COWORKERID, KEC.COWORKERNAME, KEB.STATESTATE AS STATUS 					\n");
	    sql.append("	FROM KETBOMECOHEADER KEB, KETBOMECOCOWORKER KEC WHERE KEB.ECOHEADERNUMBER=KEC.ECOHEADERNUMBER AND KEB.ECOITEMCODE=KEC.ECOITEMCODE AND KEB.STATESTATE='APPROVED'									\n");
	    sql.append("       UNION																									\n");
	    // sql.append("      SELECT KB.BOMTEXT AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 								\n");
	    sql.append("      SELECT KB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 								\n");
	    // sql.append("	FROM KETBOMHEADER KB, KETBOMCOWORKER KC 																				\n");
	    // sql.append("       WHERE  KB.NEWBOMCODE=KC.NEWBOMCODE) HD																					\n");
	    sql.append("	FROM KETBOMHEADER KB, KETBOMECOCOWORKER KC 																				\n");
	    sql.append("       WHERE  KB.NEWBOMCODE=KC.ECOITEMCODE  AND KB.ECOHEADERNUMBER=KC.ECOHEADERNUMBER  AND KB.STATESTATE='APPROVED') HD																					\n");
	    sql.append("	  WHERE  PL.IDA3A5 = BOM.IDA3A2STATE                                   																	\n");
	    sql.append("	   AND PL.IDA3B5 = PH.IDA2A2                                     																	\n");
	    sql.append("	   AND PH.PHASESTATE = BOM.STATUS																					\n");
	    sql.append("	   AND BOM.ITEMINFO = HD.HITEMKEY(+) 																					\n");
	    sql.append("     UNION  																									\n");
	    sql.append("	SELECT BOM.*,  PH.NAME	STATUSKR , NVL(HD.HITEMKEY,'') AS HEADERKEY,  NVL(HD.ECOHEADERNUMBER,'') AS EONO, NVL(HD.COWORKERID,'') AS COUTERID, NVL(HD.COWORKERNAME,'') AS COUTER  			\n");
	    sql.append("	  FROM																									\n");
	    sql.append("	      (																									\n");
	    sql.append("SELECT  ROWNUM AS NUM, LEVEL AS LVL, A0.ITEMSEQ, A0.IDA3A5 AS ASSEMBLY_ITEM_OID, A0.PARENTITEMCODE AS ASSEMBLY_ITEM_CODE, B0.VERSIONIDA2VERSIONINFO AS ASSEMBLY_ITEM_REV, 					\n");
	    sql.append("        A0.VERSIONITEMCODE AS COMPONENT_ITEM_OID, A0.CHILDITEMCODE AS COMPONENT_ITEM_CODE, 															\n");
	    sql.append("        (SELECT NAME FROM WTPARTMASTER WHERE IDA2A2 = (SELECT IDA3MASTERREFERENCE FROM WTPART WHERE IDA2A2=A0.VERSIONITEMCODE)) AS COMPONENT_ITEM_NAME, 							\n");
	    sql.append("        A0.BOMVERSION AS COMPONENT_ITEM_REV,																					\n");
	    sql.append("        A0.QUANTITY AS QTY, (SELECT STATESTATE FROM WTPART WHERE IDA2A2=A0.VERSIONITEMCODE) AS STATUS, 														\n");
	    sql.append("        (SELECT IDA3A2STATE FROM WTPART WHERE IDA2A2=A0.VERSIONITEMCODE) AS IDA3A2STATE,															\n");
	    sql.append("        A0.UNIT, A0.IDA2A2, A0.STARTDATE, A0.ENDDATE, A0.MATERIAL, A0.HARDNESSFROM, A0.HARDNESSTO, A0.DESIGNDATE,	A0.ICT, A0.REFTOP, A0.REFBOTTOM,							\n");
	    sql.append("        A0.IDA2A2 AS USAGELINKOID,																						\n");
	    sql.append("        (A0.CHILDITEMCODE || '_' || A0.BOMVERSION) AS ITEMINFO, 																		\n");
	    sql.append("        (A0.PARENTITEMCODE || '_' || (SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=A0.IDA3A5)) AS PARENTITEMINFO, A0.ATTRIBUTE10 AS ECONO												\n");
	    sql.append("FROM KETPARTUSAGELINK A0, WTPART B0  																						\n");
	    sql.append("WHERE A0.IDA3A5 = B0.IDA2A2																							\n");
	    sql.append("START WITH A0.IDA3A5 =?																								\n");
	    sql.append("CONNECT BY PRIOR																								\n");
	    sql.append("A0.VERSIONITEMCODE = A0.IDA3A5 																							\n");
	    // sql.append("CONNECT BY PRIOR (                                   																 				\n");
	    // sql.append("				  SELECT                                          																\n");
	    // sql.append("				  MAX(B.IDA3A5)                                      																\n");
	    // sql.append("				  FROM KETPARTUSAGELINK B                              																\n");
	    // sql.append("				  WHERE B.PARENTITEMCODE = X0.CHILDITEMCODE  and (SELECT VERSIONIDA2VERSIONINFO  												\n");
	    // sql.append("           FROM WTPART WHERE IDA2A2=839752014)=(CASE INSTR(A0.BOMVERSION,'.')   																\n");
	    // sql.append("                                                WHEN 0 THEN BOMVERSION  																	\n");
	    // sql.append("                                                ELSE  SUBSTR(BOMVERSION,0,INSTR(BOMVERSION,'.')-1) END )												 	\n");
	    // sql.append("					) = A0.IDA3A5                          																	\n");
	    // 제품
	    if (!(partType.equals("D") || partType.equals("M")))
		sql.append("			      ORDER SIBLINGS BY  A0.ITEMSEQ                              															\n");
	    // 금형
	    else
		sql.append("                              ORDER SIBLINGS BY  A0.CHILDITEMCODE																		\n");

	    sql.append("	   ) BOM , PHASETEMPLATE PH  ,PHASELINK PL, 																				\n");
	    sql.append("	   (SELECT KEB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KEB.ECOITEMCODE||'_'||KEB.BOMVERSION) AS HITEMKEY, KEC.COWORKERID, KEC.COWORKERNAME, KEB.STATESTATE AS STATUS 					\n");
	    sql.append("	FROM KETBOMECOHEADER KEB, KETBOMECOCOWORKER KEC WHERE KEB.ECOHEADERNUMBER=KEC.ECOHEADERNUMBER AND KEB.ECOITEMCODE=KEC.ECOITEMCODE AND KEB.STATESTATE='APPROVED'									\n");
	    sql.append("       UNION																									\n");
	    // sql.append("      SELECT KB.BOMTEXT AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 								\n");
	    sql.append("      SELECT KB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 								\n");
	    // sql.append("	FROM KETBOMHEADER KB, KETBOMCOWORKER KC 																				\n");
	    // sql.append("       WHERE  KB.NEWBOMCODE=KC.NEWBOMCODE) HD																					\n");
	    sql.append("	FROM KETBOMHEADER KB, KETBOMECOCOWORKER KC 																				\n");
	    sql.append("       WHERE  KB.NEWBOMCODE=KC.ECOITEMCODE  AND KB.ECOHEADERNUMBER=KC.ECOHEADERNUMBER  AND KB.STATESTATE='APPROVED') HD																					\n");
	    sql.append("      WHERE  PL.IDA3A5 = BOM.IDA3A2STATE                                   																	\n");
	    sql.append("	 AND PL.IDA3B5 = PH.IDA2A2                                     																		\n");
	    sql.append("	 AND PH.PHASESTATE = BOM.STATUS																						\n");
	    sql.append("       AND (BOM.PARENTITEMINFO ||'_'||BOM.COMPONENT_ITEM_CODE) = HD.HITEMKEY(+)  																\n");
	    sql.append("     )  																									\n");
	    sql.append("     ORDER BY NUM 																								\n");

	    Kogger.debug(getClass(), "SQL===> " + sql.toString());
	    Kogger.debug(getClass(), "partOid===> " + partOid);

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, partOid);
	    pstmt.setString(2, partOid);
	    rs = pstmt.executeQuery();

	    String partNo = "";
	    String rev = "";
	    String newrev = "";
	    String partName = "";
	    String lvl = "";
	    String seq = "";
	    String qty = "";
	    String unit = "";
	    String econo = "";
	    String econo2 = "";
	    String checkout = "";
	    String checkoutId = "";
	    String ict = "";
	    String reftop = "";
	    String refbtm = "";
	    String material = "";
	    String hardnessFrom = "";
	    String hardnessTo = "";
	    String designDate = "";
	    String state = "";
	    String parentNo = "";
	    String pver = "";

	    String bom_path = "";
	    int i = 0;

	    int old_lvl = 0;

	    while (rs.next()) {

		Kogger.debug(getClass(), "getVersionBOM");
		Map<String, Object> data = new Hashtable();

		partNo = rs.getString("COMPONENT_ITEM_CODE") == null ? "" : rs.getString("COMPONENT_ITEM_CODE").trim();
		rev = rs.getString("COMPONENT_ITEM_REV") == null ? "" : rs.getString("COMPONENT_ITEM_REV").trim();
		newrev = getNewVersionCode(partNo, rev);
		partName = rs.getString("COMPONENT_ITEM_NAME") == null ? "" : rs.getString("COMPONENT_ITEM_NAME").trim();
		lvl = rs.getString("LVL") == null ? "" : rs.getString("LVL").trim();
		int new_lvl = Integer.parseInt(lvl);
		seq = rs.getString("ITEMSEQ") == null ? "" : rs.getString("ITEMSEQ").trim();
		qty = rs.getString("QTY") == null ? "" : rs.getString("QTY").trim();
		unit = rs.getString("UNIT") == null ? "" : rs.getString("UNIT").trim();

		if (!unit.equals("") && unit.indexOf("KET_") == -1) {
		    unit = "KET_" + unit;
		}

		if (unit.indexOf("KET_%") != -1)
		    unit = "KET_PERCENT";

		String poid = getPartOid(partNo, rev);
		WTPart part = pUtil.getPart(poid);
		econo = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpEoNo);

		if (econo == null || econo.equals("")) {
		    econo = rs.getString("EONO") == null ? "" : rs.getString("EONO").trim();
		}

		econo2 = rs.getString("ECONO") == null ? "" : rs.getString("ECONO").trim();
		checkout = rs.getString("COUTER") == null ? "" : rs.getString("COUTER").trim();
		checkoutId = rs.getString("COUTERID") == null ? "" : rs.getString("COUTERID").trim();
		ict = rs.getString("ICT") == null ? "" : rs.getString("ICT").trim();
		reftop = rs.getString("REFTOP") == null ? "" : rs.getString("REFTOP").trim();
		refbtm = rs.getString("REFBOTTOM") == null ? "" : rs.getString("REFBOTTOM").trim();
		// material = rs.getString("MATERIAL") == null ? "" : rs.getString("MATERIAL").trim();
		if ((partType.equals("D") || partType.equals("M")))
		    material = getMaterial(partNo, rev);
		hardnessFrom = rs.getString("HARDNESSFROM") == null ? "" : rs.getString("HARDNESSFROM").trim();
		hardnessTo = rs.getString("HARDNESSTO") == null ? "" : rs.getString("HARDNESSTO").trim();
		designDate = rs.getString("DESIGNDATE") == null ? "" : rs.getString("DESIGNDATE").trim();
		state = rs.getString("STATUSKR") == null ? "" : rs.getString("STATUSKR").trim();
		parentNo = rs.getString("ASSEMBLY_ITEM_CODE") == null ? "" : rs.getString("ASSEMBLY_ITEM_CODE").trim();
		pver = rs.getString("ASSEMBLY_ITEM_REV") == null ? "" : rs.getString("ASSEMBLY_ITEM_REV").trim();

		if (lvl.equals("0")) {
		    qty = getBoxQty(partNo, rev);
		}

		if (new_lvl > old_lvl) {
		    bom_path += lvl + "^" + parentNo + "|";
		} else {
		    if (new_lvl < old_lvl)
			bom_path = bom_path.substring(0, bom_path.indexOf(lvl + "^" + parentNo + "|")) + lvl + "^" + parentNo + "|";
		}

		data.put("ict", ict);
		data.put("partNo", partNo);
		data.put("rev", rev);
		data.put("newrev", newrev);
		data.put("partName", partName);
		data.put("lvl", lvl);
		data.put("seq", seq);
		data.put("qty", qty);
		data.put("unit", unit);
		if (!econo.equals("")) {
		    data.put("econo", econo);
		} else {
		    data.put("econo", econo2);
		}
		data.put("checkout", checkout);
		// data.put("checkoutId", checkoutId);
		data.put("checkoutId", " ");
		data.put("reftop", reftop);
		data.put("refbtm", refbtm);
		data.put("material", material);
		data.put("hardnessFrom", hardnessFrom);
		data.put("hardnessTo", hardnessTo);
		data.put("designDate", designDate);
		data.put("state", state);
		data.put("parentNo", parentNo);
		data.put("pver", pver);

		data.put("bom_path", bom_path);

		bomList.add(data);
		old_lvl = new_lvl;
	    }

	    // Kogger.debug(getClass(), "bomList.size()===> " + bomList.size());
	    if (dataType.equals("TreeGrid")) {

		treeList = getGridBOM(bomList);
	    } else {
		treeList = bomList;
	    }

	    // Kogger.debug(getClass(), treeList.toString());
	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
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
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return treeList;
    }

    /**********************************************************
     * ECO BOM Header 정보를 가져오는 함수
     **********************************************************/
    public Hashtable getHeaderList(String ecoNumber) {
	Hashtable hList = new Hashtable();
	StringBuffer sql = new StringBuffer();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {

	    sql.append(" SELECT ATTRIBUTE1 AS REVISEYN, ECOITEMCODE, BOMVERSION, DESCRIPTION AS ITEMNAME, ATTRIBUTE2 AS OLDBOXQUANTITY, ");
	    sql.append(" 	BOXQUANTITY, UNITOFQUANTITY AS UNIT FROM KETBOMECOHEADER WHERE ECOHEADERNUMBER = ? ");

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoNumber);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {

		Hashtable data = new Hashtable();

		String reviseYN = rs.getString("REVISEYN") == null ? "" : rs.getString("REVISEYN").trim();
		String partNo = rs.getString("ECOITEMCODE") == null ? "" : rs.getString("ECOITEMCODE").trim();
		String rev = rs.getString("BOMVERSION") == null ? "" : rs.getString("BOMVERSION").trim();
		String partName = rs.getString("ITEMNAME") == null ? "" : rs.getString("ITEMNAME").trim();
		String oldQty = rs.getString("OLDBOXQUANTITY") == null ? "" : rs.getString("OLDBOXQUANTITY").trim();
		String newQty = rs.getString("BOXQUANTITY") == null ? "" : rs.getString("BOXQUANTITY").trim();
		String unit = rs.getString("UNIT") == null ? "" : rs.getString("UNIT").trim();

		data.put("ecoNumber", ecoNumber);
		data.put("reviseYN", reviseYN);
		data.put("partNo", partNo);
		data.put("rev", rev);
		data.put("partName", partName);
		data.put("oldQty", oldQty);
		data.put("newQty", newQty);
		data.put("unit", unit);

		hList.put(partNo, data);
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
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
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}
	return hList;
    }

    public Hashtable getHeaderList(String ecoNumber, WTConnection conn) throws SQLException {
	Hashtable hList = new Hashtable();
	StringBuffer sql = new StringBuffer();

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {

	    sql.append(" SELECT ATTRIBUTE1 AS REVISEYN, ECOITEMCODE, BOMVERSION, DESCRIPTION AS ITEMNAME, ATTRIBUTE2 AS OLDBOXQUANTITY, ");
	    sql.append(" 	BOXQUANTITY, UNITOFQUANTITY AS UNIT FROM KETBOMECOHEADER WHERE ECOHEADERNUMBER = ? ");

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoNumber);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {

		Hashtable data = new Hashtable();

		String reviseYN = rs.getString("REVISEYN") == null ? "" : rs.getString("REVISEYN").trim();
		String partNo = rs.getString("ECOITEMCODE") == null ? "" : rs.getString("ECOITEMCODE").trim();
		String rev = rs.getString("BOMVERSION") == null ? "" : rs.getString("BOMVERSION").trim();
		String partName = rs.getString("ITEMNAME") == null ? "" : rs.getString("ITEMNAME").trim();
		String oldQty = rs.getString("OLDBOXQUANTITY") == null ? "" : rs.getString("OLDBOXQUANTITY").trim();
		String newQty = rs.getString("BOXQUANTITY") == null ? "" : rs.getString("BOXQUANTITY").trim();
		String unit = rs.getString("UNIT") == null ? "" : rs.getString("UNIT").trim();

		data.put("ecoNumber", ecoNumber);
		data.put("reviseYN", reviseYN);
		data.put("partNo", partNo);
		data.put("rev", rev);
		data.put("partName", partName);
		data.put("oldQty", oldQty);
		data.put("newQty", newQty);
		data.put("unit", unit);

		hList.put(partNo, data);
	    }

	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return hList;
    }

    /**********************************************************
     * ECO BOM Component 정보를 가져오는 함수
     **********************************************************/
    public Hashtable getComponentList(String ecoNumber, String partNumber) throws Exception {
	Hashtable cHash = new Hashtable();
	StringBuffer sql = new StringBuffer();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	String partNo = "";
	String rev = "";
	String newrev = "";
	String partName = "";
	String lvl = "";
	String seq = "";
	String qty = "";
	String unit = "";
	String econo = "";
	String checkout = "";
	String checkoutId = "";
	String ict = "";
	String reftop = "";
	String refbtm = "";
	String material = "";
	String hardnessFrom = "";
	String hardnessTo = "";
	String designDate = "";
	String statestr = "";
	String parentNo = "";
	String pver = "";

	String ecocode = "";
	String ecoitemcode = "";

	try {

	    sql.append("SELECT * FROM ( 													");
	    sql.append("  SELECT 1 AS SEQ , A0.ECOITEMCODE AS CHILDITEMCODE, A0.BOMVERSION, A0.DESCRIPTION AS PARTNAME, 0 AS BOMLEVEL, '10' AS ITEMSEQ, NVL(A0.BOXQUANTITY,'1') AS BEFOREQUANTITY,	");
	    sql.append("         A0.UNITOFQUANTITY AS BEFOREUNIT, A0.ECOHEADERNUMBER AS ECONO, B0.COWORKERNAME AS CHECKOUT, B0.COWORKERID AS CHECKOUTID,						");
	    sql.append("         '' AS BEFOREICT, '' AS BEFOREREFTOP, '' AS BEFOREREFBOTTOM, '' AS BEFOREMATERIAL, '' AS BEFOREHARDNESSFROM, '' AS BEFOREHARDNESSTO, '' AS BEFOREDESIGNDATE, 	");
	    sql.append("         '' AS AFTERQUANTITY, '' AS AFTERUNIT, '' AS AFTERICT, '' AS AFTERREFTOP, '' AS AFTERREFBOTTOM,'' AS AFTERMATERIAL,'' AS AFTERHARDNESSFROM, 			");
	    sql.append("         '' AS AFTERHARDNESSTO,'' AS AFTERDESIGNDATE, '' AS PARENTITEMCODE, '' AS ECOCODE, '' AS ECOITEMCODE, A0.STATESTATE, A0.ATTRIBUTE1 AS REVISEYN			");
	    sql.append("    FROM KETBOMECOHEADER A0, KETBOMECOCOWORKER B0																");
	    sql.append("   WHERE A0.ECOHEADERNUMBER=B0.ECOHEADERNUMBER																");
	    sql.append("     AND A0.ECOHEADERNUMBER=? AND A0.ECOITEMCODE=? AND A0.ECOITEMCODE=B0.ECOITEMCODE																");
	    sql.append("   UNION																					");
	    sql.append("  SELECT Y0.*, NVL(D0.STATESTATE,'') AS STATESTATE , '' AS REVISEYN FROM 													");
	    sql.append("  (																						");
	    sql.append("  	SELECT (ROWNUM+1) AS SEQ, A0.CHILDITEMCODE, A0.BOMVERSION, B0.NAME AS PARTNAME, LEVEL AS BOMLEVEL, A0.ITEMSEQ, A0.BEFOREQUANTITY,					");
	    sql.append("           A0.BEFOREUNIT, A0.ECOHEADERNUMBER AS ECONO, NVL(C0.COWORKERNAME,'') AS CHECKOUT, NVL(C0.COWORKERID,'') AS CHECKOUTID, 						");
	    sql.append("           A0.BEFOREICT, A0.BEFOREREFTOP, A0.BEFOREREFBOTTOM, A0.BEFOREMATERIAL, A0.BEFOREHARDNESSFROM, A0.BEFOREHARDNESSTO,						");
	    sql.append("           A0.BEFOREDESIGNDATE, A0.AFTERQUANTITY, A0.AFTERUNIT, A0.AFTERICT, A0.AFTERREFTOP, A0.AFTERREFBOTTOM,								");
	    sql.append("           A0.AFTERMATERIAL, A0.AFTERHARDNESSFROM, A0.AFTERHARDNESSTO,A0.AFTERDESIGNDATE, A0.PARENTITEMCODE, A0.ECOCODE, A0.ECOITEMCODE					");
	    sql.append("      FROM KETBOMECOCOMPONENT A0, WTPARTMASTER B0, 																");
	    sql.append("           (SELECT COWORKERID,COWORKERNAME, ECOITEMCODE, ECOHEADERNUMBER  FROM KETBOMECOCOWORKER WHERE ECOHEADERNUMBER = ? ) C0								");
	    sql.append("     WHERE A0.CHILDITEMCODE = B0.WTPARTNUMBER  																");
	    sql.append("       AND NVL(A0.ECOCODE, ' ') <>'Remove'																	");
	    sql.append("       AND A0.ECOITEMCODE = C0.ECOITEMCODE 																");
	    sql.append("       AND A0.ECOHEADERNUMBER=?																		");
	    sql.append("       AND A0.ECOHEADERNUMBER=C0.ECOHEADERNUMBER																		");

	    sql.append("     START WITH A0.PARENTITEMCODE=?																		");
	    sql.append("   CONNECT BY PRIOR A0.CHILDITEMCODE=A0.PARENTITEMCODE																");

	    // sql.append("     START WITH A0.PARENTITEMCODE=?																		");
	    // sql.append("   CONNECT BY PRIOR A0.PARENTITEMCODE=A0.CHILDITEMCODE																");
	    sql.append("    ORDER SIBLINGS BY  																			");
	    sql.append("         A0.CHILDITEMCODE																			");
	    sql.append("  ) Y0, (SELECT ECOITEMCODE, STATESTATE FROM KETBOMECOHEADER WHERE ECOHEADERNUMBER = ?) D0											");
	    sql.append("   WHERE Y0.CHILDITEMCODE = D0.ECOITEMCODE(+)   ) ORDER BY SEQ														");

	    // Kogger.debug(getClass(), "SQL==>" + sql.toString());

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoNumber);
	    pstmt.setString(2, partNumber);
	    pstmt.setString(3, ecoNumber);
	    pstmt.setString(4, ecoNumber);
	    pstmt.setString(5, partNumber);
	    pstmt.setString(6, ecoNumber);

	    rs = pstmt.executeQuery();

	    String reviseYN = "";

	    while (rs.next()) {
		Map<String, Object> data = new Hashtable();

		partNo = rs.getString("CHILDITEMCODE") == null ? "" : rs.getString("CHILDITEMCODE").trim();
		rev = rs.getString("BOMVERSION") == null ? "" : rs.getString("BOMVERSION").trim();
		newrev = getNewVersionCode(partNo, rev);
		partName = rs.getString("PARTNAME") == null ? "" : rs.getString("PARTNAME").trim();
		lvl = rs.getString("BOMLEVEL") == null ? "" : rs.getString("BOMLEVEL").trim();
		int new_lvl = Integer.parseInt(lvl);
		seq = rs.getString("ITEMSEQ") == null ? "" : rs.getString("ITEMSEQ").trim();
		ict = rs.getString("BEFOREICT") == null ? "" : rs.getString("BEFOREICT").trim();
		econo = rs.getString("ECONO") == null ? "" : rs.getString("ECONO").trim();
		checkout = rs.getString("CHECKOUT") == null ? "" : rs.getString("CHECKOUT").trim();
		checkoutId = rs.getString("CHECKOUTID") == null ? "" : rs.getString("CHECKOUTID").trim();
		ecocode = rs.getString("ECOCODE") == null ? "" : rs.getString("ECOCODE").trim();
		qty = rs.getString("BEFOREQUANTITY") == null ? "" : rs.getString("BEFOREQUANTITY").trim();
		unit = rs.getString("BEFOREUNIT") == null ? "" : rs.getString("BEFOREUNIT").trim();
		reftop = rs.getString("BEFOREREFTOP") == null ? "" : rs.getString("BEFOREREFTOP").trim();
		refbtm = rs.getString("BEFOREREFBOTTOM") == null ? "" : rs.getString("BEFOREREFBOTTOM").trim();
		material = rs.getString("BEFOREMATERIAL") == null ? "" : rs.getString("BEFOREMATERIAL").trim();
		hardnessFrom = rs.getString("BEFOREHARDNESSFROM") == null ? "" : rs.getString("BEFOREHARDNESSFROM").trim();
		hardnessTo = rs.getString("BEFOREHARDNESSTO") == null ? "" : rs.getString("BEFOREHARDNESSTO").trim();
		designDate = rs.getString("BEFOREDESIGNDATE") == null ? "" : rs.getString("BEFOREDESIGNDATE").trim();
		parentNo = rs.getString("PARENTITEMCODE") == null ? "" : rs.getString("PARENTITEMCODE").trim();

		if (qty.indexOf(".") != -1) {
		    if (qty.substring(qty.indexOf(".") + 1).length() == 1) {
			qty = qty + "00";
		    }

		    if (qty.substring(qty.indexOf(".") + 1).length() == 2) {
			qty = qty + "0";
		    }
		} else {
		    qty = qty + ".000";
		}

		/*
	         * if (ecocode != null && !ecocode.equals("")) { qty = rs.getString("AFTERQUANTITY") == null ? "" : rs.getString("AFTERQUANTITY").trim(); unit = rs.getString("AFTERUNIT") == null ? ""
	         * : rs.getString("AFTERUNIT").trim(); reftop = rs.getString("AFTERREFTOP") == null ? "" : rs.getString("AFTERREFTOP").trim(); refbtm = rs.getString("AFTERREFBOTTOM") == null ? "" :
	         * rs.getString("AFTERREFBOTTOM").trim(); material = rs.getString("AFTERMATERIAL") == null ? "" : rs.getString("AFTERMATERIAL").trim(); hardnessFrom = rs.getString("AFTERHARDNESSFROM")
	         * == null ? "" : rs.getString("AFTERHARDNESSFROM").trim(); hardnessTo = rs.getString("AFTERHARDNESSTO") == null ? "" : rs.getString("AFTERHARDNESSTO").trim(); designDate =
	         * rs.getString("AFTERDESIGNDATE") == null ? "" : rs.getString("AFTERDESIGNDATE").trim(); }
	         */

		data.put("partNo", partNo);
		data.put("rev", rev);
		data.put("newrev", newrev);
		data.put("partName", partName);
		data.put("lvl", lvl);
		data.put("ict", ict);
		data.put("seq", seq);
		data.put("qty", qty);
		data.put("unit", unit);
		data.put("econo", econo);
		data.put("checkout", checkout);
		data.put("checkoutId", checkoutId);
		data.put("reftop", reftop);
		data.put("refbtm", refbtm);
		data.put("material", material);
		data.put("hardnessFrom", hardnessFrom);
		data.put("hardnessTo", hardnessTo);
		data.put("designDate", designDate);
		data.put("parentNo", parentNo);
		data.put("pver", pver);
		data.put("ecoitemcode", ecoitemcode);
		data.put("ecocode", ecocode);

		String key = parentNo + "^" + partNo;

		cHash.put(key, data);
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
		throw e;
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}
	return cHash;
    }

    /**********************************************************
     * BOM 편집 데이터를 저장 KETBOMCOMPONENT, KETBOMECOCOMPONENT 에 INSERT
     * 
     * @throws Exception
     **********************************************************/
    public String saveBom(Hashtable params, List<Map<String, Object>> saveList, Hashtable saveKeyHash) throws Exception {
	String result = "저장되었습니다.";
	String boxQty = "";
	String partNumber = "";
	String ecoNumber = "";
	String gubun = "";
	String partType = "";

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	ecoNumber = (String) params.get("ecoNumber");
	partNumber = (String) params.get("partNumber");
	boxQty = (String) params.get("boxQty");
	gubun = (String) params.get("gubun");
	partType = (String) params.get("partType");

	boolean isNewBomStart = false;
	boolean isEcoBomStart = false;
	
	String logEcoOid = null;
	WTChangeOrder2 eco = null;
	String ecoId = (String) params.get("ecoNumber");
	if(ecoId != null){
	    ecoId = ecoId.replace("ECO-", "");
	    ProdEcoBeans beans = new ProdEcoBeans();
	    eco = beans.getEcoByNo(ecoId);
	    if(eco != null){
		logEcoOid = CommonUtil.getOIDString(eco);
	    }
	}

	try {
	    List<Map<String, Object>> componemtList = new ArrayList<Map<String, Object>>();

	    StringBuffer sql;

	    String parentNo = "";
	    String partNo = "";
	    String rev = "";

	    String ict = "";
	    String lvl = "";
	    String seq = "";
	    String qty = "";
	    String unit = "";
	    String reftop = "";
	    String refbtm = "";
	    String material = "";
	    String hardnessFrom = "";
	    String hardnessTo = "";
	    String designDate = "";
	    String bomFlag = "";
	    String parentBomFlag = "";

	    
	    if (gubun.equals("NEWBOM")) {
		
		isNewBomStart = true;
		
		String insertColumn = " NEWBOMCODE, BOMVERSION, PARENTITEMCODE, CHILDITEMCODE, BOMLEVEL, ITEMSEQ";
		insertColumn += ", QUANTITY, UNIT, MATERIAL, HARDNESSFROM, HARDNESSTO, DESIGNDATE";
		insertColumn += ", ICT, REFTOP, REFBOTTOM,NEWFLAG, FIRSTREMARK, SECONDREMARK ";

		// 해당 BOM을 삭제 후 새로 입력
		sql = new StringBuffer();
		sql.append(" DELETE FROM KETBOMCOMPONENT WHERE NEWBOMCODE = '" + partNumber + "' ");
		pstmt = conn.prepareStatement(sql.toString());
		pstmt.executeUpdate();
		try{pstmt.close();}catch(Exception e){}

		Hashtable parentHash = new Hashtable();

		for (int i = 0; i < saveList.size(); i++) {
		    Hashtable data = (Hashtable) saveList.get(i);

		    parentNo = (String) data.get("parentNo");
		    partNo = (String) data.get("partNo");
		    rev = (String) data.get("rev");

		    if (rev.indexOf(".") != -1) {
			rev = rev.substring(0, rev.indexOf("."));
		    }

		    ict = (String) data.get("ict");
		    lvl = (String) data.get("lvl");
		    seq = (String) data.get("seq");
		    qty = (String) data.get("qty");
		    unit = (String) data.get("unit");

		    if (unit.indexOf("KET_") == -1)
			unit = "KET_" + unit;

		    if (unit.indexOf("KET_PERCENT") != -1)
			unit = "KET_%";

		    reftop = (String) data.get("reftop");
		    refbtm = (String) data.get("refbtm");
		    material = (String) data.get("material");
		    hardnessFrom = (String) data.get("hardnessFrom");
		    hardnessTo = (String) data.get("hardnessTo");
		    designDate = (String) data.get("designDate");

		    bomFlag = partNewOld(partNo, rev);

		    parentBomFlag = "NEW";

		    if (parentHash.containsKey(parentNo)) {
			parentBomFlag = (String) parentHash.get(parentNo);
		    }
		    sql = new StringBuffer();
		    if (i == 0 && !partType.equals("D")) // BOMHEADER 정보 입력
		    {
			sql = new StringBuffer();
			sql.append(" UPDATE KETBOMHEADER SET BOXQUANTITY='" + qty + "' WHERE NEWBOMCODE='" + partNumber + "' ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.executeUpdate();
			try{pstmt.close();}catch(Exception e){}

		    }
		    if (i > 0)// BOMCOMPONENT 정보 입력
		    {
			sql = new StringBuffer();
			sql.append(" INSERT INTO KETBOMCOMPONENT (" + insertColumn + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, Utility.checkNVL(partNumber));// NEWBOMCODE
			pstmt.setString(2, Utility.checkNVL(rev));// BOMVERSION
			pstmt.setString(3, Utility.checkNVL(parentNo));// PARENTITEMCODE
			pstmt.setString(4, Utility.checkNVL(partNo)); // CHILDITEMCODE
			pstmt.setString(5, Utility.checkNVL(lvl)); // BOMLEVEL
			pstmt.setString(6, Utility.checkNVL(seq)); // ITEMSEQ
			pstmt.setString(7, Utility.checkNVL(qty)); // QUANTITY
			pstmt.setString(8, Utility.checkNVL(unit)); // UNIT
			pstmt.setString(9, Utility.checkNVL(material)); // MATERIAL
			pstmt.setString(10, Utility.checkNVL(hardnessFrom)); // HARDNESSFROM
			pstmt.setString(11, Utility.checkNVL(hardnessTo)); // HARDNESSTO
			pstmt.setString(12, Utility.checkNVL(designDate)); // DESIGNDATE
			pstmt.setString(13, Utility.checkNVL(ict)); // ICT
			pstmt.setString(14, Utility.checkNVL(reftop)); // REFTOP
			pstmt.setString(15, Utility.checkNVL(refbtm)); // REFBOTTOM
			pstmt.setString(16, Utility.checkNVL(parentBomFlag)); // NEWFLAG
			pstmt.setString(17, Utility.checkNVL(bomFlag)); // FIRSTREMARK
			pstmt.setString(18, Utility.checkNVL("NEW")); // SECONDREMARK

			pstmt.executeUpdate();
			try{pstmt.close();}catch(Exception e){}

		    }

		    parentHash.put(partNo, bomFlag);
		}

		KogDbUtil.log("BOM Editor 초도 Bom 저장", "Success", logEcoOid, (String)null, null, null);
		
	    } else if (gubun.equals("ECOBOM")) {

		isEcoBomStart = true;
		
		String insertColumn = " ECOHEADERNUMBER, PARENTITEMCODE, CHILDITEMCODE, BOMLEVEL, ITEMSEQ";
		insertColumn += ", AFTERQUANTITY, AFTERUNIT, AFTERMATERIAL, AFTERHARDNESSFROM, AFTERHARDNESSTO, AFTERDESIGNDATE";
		insertColumn += ", AFTERICT, AFTERREFTOP, AFTERREFBOTTOM, ECOITEMCODE, APPLIEDPRODUCTCODE, BOMVERSION, ECOCODE	";

		Hashtable headerHash = getHeaderList(ecoNumber);
		Hashtable componentHash = getComponentList(ecoNumber, partNumber);

		// Kogger.debug(getClass(), "componentHash======================>" + componentHash.toString());

		// 1.header 정보 가져옴 (변경 부품 정보)
		// 2.저장 리스트에서 헤더 정보의 하위 1레벨만 확인 후 업데이트(모 부품이 변경대상 부품인 것)
		// - ADD인 경우 하위 BOM 추가 (하위 1레벨 ECOCODE에 플래그 Add 입력)
		// - Delete 인 경우 하위 1레벨 ECOCODE에 플래그 Delete 입력
		// - Update 인 경우 AFTER정보에 업데이트 및 플래그 Update 입력
		// 3.SEQ 는 모두 없데이트

		// ECO 적용 제품 및 LVL
		Hashtable partLvlInfo = getUsageInfoList3(ecoNumber);

		// 삭제된 부품
		Enumeration keys = componentHash.keys();

		while (keys.hasMoreElements()) {
		    String key = (String) keys.nextElement();

		    Hashtable data = (Hashtable) componentHash.get(key);

		    parentNo = (String) data.get("parentNo");
		    partNo = (String) data.get("partNo");
		    String ecoCode = (String) data.get("ecocode");

		    if (!saveKeyHash.containsKey(key)) {
			// 여기서는 삭제 플래그 업데이트

			if (headerHash.containsKey(parentNo)) // 1레벨만 처리하는 로직
			{
			    if (ecoCode.equals("Add")) {

				sql = new StringBuffer();
				sql.append(" DELETE FROM KETBOMECOCOMPONENT WHERE ECOHEADERNUMBER='" + ecoNumber + "'  AND PARENTITEMCODE='" + parentNo + "'   AND CHILDITEMCODE='" + partNo + "' ");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.executeUpdate();
				try{pstmt.close();}catch(Exception e){}

			    } else {
				sql = new StringBuffer();
				sql.append(" UPDATE KETBOMECOCOMPONENT SET ECOCODE='Remove' WHERE ECOHEADERNUMBER='" + ecoNumber + "'  AND PARENTITEMCODE='" + parentNo + "'   AND CHILDITEMCODE='"
				        + partNo + "' ");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.executeUpdate();
				try{pstmt.close();}catch(Exception e){}
			    }
			}

		    }
		}

		String parentItemCode = "";
		String ecoitemcode = "";

		// ADD, UPDATE
		for (int i = 0; i < saveList.size(); i++) {
		    Hashtable data = (Hashtable) saveList.get(i);

		    parentNo = (String) data.get("parentNo");
		    partNo = (String) data.get("partNo");
		    rev = (String) data.get("rev");

		    if (headerHash.containsKey(parentNo)) {
			ecoitemcode = parentNo;
		    }

		    if (rev.indexOf(".") != -1) {
			rev = rev.substring(0, rev.indexOf("."));
		    }

		    ict = (String) data.get("ict");
		    lvl = (String) data.get("lvl");
		    seq = (String) data.get("seq");
		    qty = (String) data.get("qty");
		    unit = (String) data.get("unit");

		    if (unit.indexOf("KET_") == -1)
			unit = "KET_" + unit;

		    if (unit.indexOf("KET_PERCENT") != -1)
			unit = "KET_%";

		    reftop = (String) data.get("reftop");
		    refbtm = (String) data.get("refbtm");
		    material = (String) data.get("material");
		    hardnessFrom = (String) data.get("hardnessFrom");
		    hardnessTo = (String) data.get("hardnessTo");
		    designDate = (String) data.get("designDate");

		    if (designDate.length() > 10) {
			designDate = designDate.substring(0, 10);
		    }

		    String compareStr1 = ict + "^" + qty + "^" + unit + "^" + reftop + "^" + refbtm + "^" + material + "^" + hardnessFrom + "^" + hardnessTo + "^" + designDate;

		    String ckey = parentNo + "^" + partNo;// parentNo + "^" + partNo;

		    if (i == 0 && !partType.equals("D")) // BOMHEADER 정보 입력
		    {
			sql = new StringBuffer();
			sql.append(" UPDATE KETBOMECOHEADER SET BOXQUANTITY='" + qty + "' WHERE ECOHEADERNUMBER='" + ecoNumber + "' AND ECOITEMCODE='" + partNumber + "' ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.executeUpdate();
			try{pstmt.close();}catch(Exception e){}

		    } else {

			if (headerHash.containsKey(parentNo)) // Header 하위 1레벨만 처리하는 로직
			{
			    // ITEMSEQ 무조건 업데이트

			    // Kogger.debug(getClass(), "ckey====>" + ckey);
			    // Kogger.debug(getClass(), "componentHash====>" + componentHash.toString());

			    if (componentHash.containsKey(ckey)) // bomcomponent에 있는 경우
			    {
				Hashtable compdata = (Hashtable) componentHash.get(ckey);

				String bict = (String) compdata.get("ict");
				String bqty = (String) compdata.get("qty");

				if (bqty.indexOf(".") != -1) {
				    if (bqty.substring(bqty.indexOf(".") + 1).length() == 1) {
					bqty = bqty + "00";
				    }

				    if (bqty.substring(bqty.indexOf(".") + 1).length() == 2) {
					bqty = bqty + "0";
				    }
				} else {
				    bqty = bqty + ".000";
				}

				String bunit = (String) compdata.get("unit");

				if (bunit.indexOf("KET_PERCENT") != -1)
				    bunit = "KET_%";

				String breftop = (String) compdata.get("reftop");
				String brefbtm = (String) compdata.get("refbtm");
				String bmaterial = (String) compdata.get("material");
				String bhardnessFrom = (String) compdata.get("hardnessFrom");
				String bhardnessTo = (String) compdata.get("hardnessTo");
				String bdesignDate = (String) compdata.get("designDate");

				String compareStr2 = bict + "^" + bqty + "^" + bunit + "^" + breftop + "^" + brefbtm + "^" + bmaterial + "^" + bhardnessFrom + "^" + bhardnessTo + "^" + bdesignDate;

				String ecocode = (String) compdata.get("ecocode");

				if (ecocode.equals("Add")) {
				    sql = new StringBuffer();

				    sql.append(" UPDATE KETBOMECOCOMPONENT SET ITEMSEQ='" + seq + "', AFTERQUANTITY='" + qty + "', AFTERUNIT='" + unit + "', AFTERMATERIAL='" + material
					    + "', AFTERHARDNESSFROM='" + hardnessFrom + "', AFTERHARDNESSTO='" + hardnessTo + "', AFTERDESIGNDATE='" + designDate + "', AFTERICT='" + ict
					    + "', AFTERREFTOP='" + reftop + "', AFTERREFBOTTOM='" + refbtm + "' WHERE ECOHEADERNUMBER='" + ecoNumber + "'  AND PARENTITEMCODE='" + parentNo
					    + "'   AND CHILDITEMCODE='" + partNo + "' ");

				    pstmt = conn.prepareStatement(sql.toString());
				    pstmt.executeUpdate();
				    try{pstmt.close();}catch(Exception e){}

				} else if (ecocode.equals("Remove")) {
				    // update (ecocode를 데이터 비교하여 다른경우 Update로 하거나 같은 경우 ecocode를 공백으로 입력)

				    if (compareStr1.equals(compareStr2)) {
					sql = new StringBuffer();
					sql.append(" UPDATE KETBOMECOCOMPONENT SET ECOCODE='',ITEMSEQ='" + seq + "' WHERE ECOHEADERNUMBER='" + ecoNumber + "'  AND PARENTITEMCODE='" + parentNo
					        + "'   AND CHILDITEMCODE='" + partNo + "' ");
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.executeUpdate();
					try{pstmt.close();}catch(Exception e){}
				    } else {
					sql = new StringBuffer();

					sql.append(" UPDATE KETBOMECOCOMPONENT SET ECOCODE='Update',ITEMSEQ='" + seq + "', AFTERQUANTITY='" + qty + "', AFTERUNIT='" + unit + "', AFTERMATERIAL='"
					        + material + "', AFTERHARDNESSFROM='" + hardnessFrom + "', AFTERHARDNESSTO='" + hardnessTo + "', AFTERDESIGNDATE='" + designDate + "', AFTERICT='"
					        + ict + "', AFTERREFTOP='" + reftop + "', AFTERREFBOTTOM='" + refbtm + "' WHERE ECOHEADERNUMBER='" + ecoNumber + "'  AND PARENTITEMCODE='" + parentNo
					        + "'   AND CHILDITEMCODE='" + partNo + "' ");

					pstmt = conn.prepareStatement(sql.toString());
					pstmt.executeUpdate();
					try{pstmt.close();}catch(Exception e){}
				    }

				} else if (ecocode.equals("Update")) {
				    // update(ecocode를 데이터 비교하여 같은 경우 ecocode를 공백으로 입력)
				    if (compareStr1.equals(compareStr2)) {
					sql = new StringBuffer();
					sql.append(" UPDATE KETBOMECOCOMPONENT SET ECOCODE='',ITEMSEQ='" + seq + "' WHERE ECOHEADERNUMBER='" + ecoNumber + "'  AND PARENTITEMCODE='" + parentNo
					        + "'   AND CHILDITEMCODE='" + partNo + "' ");
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.executeUpdate();
					try{pstmt.close();}catch(Exception e){}

				    } else {
					sql = new StringBuffer();

					sql.append(" UPDATE KETBOMECOCOMPONENT SET ECOCODE='Update',ITEMSEQ='" + seq + "', AFTERQUANTITY='" + qty + "', AFTERUNIT='" + unit + "', AFTERMATERIAL='"
					        + material + "', AFTERHARDNESSFROM='" + hardnessFrom + "', AFTERHARDNESSTO='" + hardnessTo + "', AFTERDESIGNDATE='" + designDate + "', AFTERICT='"
					        + ict + "', AFTERREFTOP='" + reftop + "', AFTERREFBOTTOM='" + refbtm + "' WHERE ECOHEADERNUMBER='" + ecoNumber + "'  AND PARENTITEMCODE='" + parentNo
					        + "'   AND CHILDITEMCODE='" + partNo + "' ");

					pstmt = conn.prepareStatement(sql.toString());
					pstmt.executeUpdate();
					try{pstmt.close();}catch(Exception e){}

				    }

				} else {
				    // update 비교하여 업데이트
				    if (compareStr1.equals(compareStr2)) {

					if (!partType.equals("D")) {
					    sql = new StringBuffer();
					    sql.append(" UPDATE KETBOMECOCOMPONENT SET ECOCODE='',ITEMSEQ='" + seq + "' WHERE ECOHEADERNUMBER='" + ecoNumber + "'  AND PARENTITEMCODE='" + parentNo
						    + "'   AND CHILDITEMCODE='" + partNo + "' ");
					    pstmt = conn.prepareStatement(sql.toString());
					    pstmt.executeUpdate();
					    try{pstmt.close();}catch(Exception e){}
					} else {
					    sql = new StringBuffer();

					    sql.append(" UPDATE KETBOMECOCOMPONENT SET ECOCODE='',ITEMSEQ='" + seq + "', AFTERQUANTITY='" + qty + "', AFTERUNIT='" + unit + "', AFTERMATERIAL='"
						    + material + "', AFTERHARDNESSFROM='" + hardnessFrom + "', AFTERHARDNESSTO='" + hardnessTo + "', AFTERDESIGNDATE='" + designDate + "', AFTERICT='"
						    + ict + "', AFTERREFTOP='" + reftop + "', AFTERREFBOTTOM='" + refbtm + "' WHERE ECOHEADERNUMBER='" + ecoNumber + "'  AND PARENTITEMCODE='"
						    + parentNo + "'   AND CHILDITEMCODE='" + partNo + "' ");

					    // Kogger.debug(getClass(), "SQL===>" + sql.toString());

					    pstmt = conn.prepareStatement(sql.toString());
					    pstmt.executeUpdate();
					    try{pstmt.close();}catch(Exception e){}
					}
				    } else {
					sql = new StringBuffer();

					sql.append(" UPDATE KETBOMECOCOMPONENT SET ECOCODE='Update',ITEMSEQ='" + seq + "', AFTERQUANTITY='" + qty + "', AFTERUNIT='" + unit + "', AFTERMATERIAL='"
					        + material + "', AFTERHARDNESSFROM='" + hardnessFrom + "', AFTERHARDNESSTO='" + hardnessTo + "', AFTERDESIGNDATE='" + designDate + "', AFTERICT='"
					        + ict + "', AFTERREFTOP='" + reftop + "', AFTERREFBOTTOM='" + refbtm + "' WHERE ECOHEADERNUMBER='" + ecoNumber + "'  AND PARENTITEMCODE='" + parentNo
					        + "'   AND CHILDITEMCODE='" + partNo + "' ");

					// Kogger.debug(getClass(), "SQL===>" + sql.toString());

					pstmt = conn.prepareStatement(sql.toString());
					pstmt.executeUpdate();
					try{pstmt.close();}catch(Exception e){}
				    }
				}

			    } else // bomcomponent에 없는 경우 무조건 ADD
			    {
				// insert
				String appliedProd = "";
				String newLvl = "";

				if (partLvlInfo.containsKey(parentNo)) {
				    Hashtable hinfo = (Hashtable) partLvlInfo.get(parentNo);

				    appliedProd = (String) hinfo.get("appliedProd");
				    newLvl = (String) hinfo.get("lvl");
				    int inewLvl = Integer.parseInt(newLvl) + 1;

				    lvl = Integer.toString(inewLvl);
				}

				if (appliedProd.equals(""))
				    appliedProd = partNumber;

				sql = new StringBuffer();
				sql.append(" INSERT INTO KETBOMECOCOMPONENT (" + insertColumn + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pstmt = conn.prepareStatement(sql.toString());

				// Kogger.debug(getClass(), "DESIGNDATE===>" + Utility.checkNVL(designDate));

				pstmt.setString(1, Utility.checkNVL(ecoNumber));// ECOHEADERNUMBER
				pstmt.setString(2, Utility.checkNVL(parentNo));// PARENTITEMCODE
				pstmt.setString(3, Utility.checkNVL(partNo));// CHILDITEMCODE
				pstmt.setString(4, Utility.checkNVL(lvl)); // BOMLEVEL
				pstmt.setString(5, Utility.checkNVL(seq)); // ITEMSEQ
				pstmt.setString(6, Utility.checkNVL(qty)); // BEFOREQUANTITY
				pstmt.setString(7, Utility.checkNVL(unit)); // BEFOREUNIT
				pstmt.setString(8, Utility.checkNVL(material)); // BEFOREMATERIAL
				pstmt.setString(9, Utility.checkNVL(hardnessFrom)); // BEFOREHARDNESSFROM
				pstmt.setString(10, Utility.checkNVL(hardnessTo)); // BEFOREHARDNESSTO
				pstmt.setString(11, Utility.checkNVL(designDate)); // BEFOREDESIGNDATE
				pstmt.setString(12, Utility.checkNVL(ict)); // BEFOREICT
				pstmt.setString(13, Utility.checkNVL(reftop)); // BEFOREREFTOP
				pstmt.setString(14, Utility.checkNVL(refbtm)); // BEFOREREFBOTTOM
				pstmt.setString(15, Utility.checkNVL(ecoitemcode));// ECOITEMCODE
				pstmt.setString(16, Utility.checkNVL(appliedProd));// APPLIEDPRODUCTCODE
				pstmt.setString(17, Utility.checkNVL(rev));// BOMVERSION
				pstmt.setString(18, Utility.checkNVL("Add"));// ECOCODE

				pstmt.executeUpdate();
				try{pstmt.close();}catch(Exception e){}
			    }

			} else // 1레벨 이상 처리 (ECOCODE는 입력하지 않는다.)
			{
			    if (!componentHash.containsKey(ckey)) // bomcomponent에 없으면 insert ecocode 제외) ITEMCODE==>partNumber로 그냥 넣자
			    {

				String appliedProd = "";
				String newLvl = "";

				if (partLvlInfo.containsKey(parentNo)) {
				    Hashtable hinfo = (Hashtable) partLvlInfo.get(parentNo);

				    appliedProd = (String) hinfo.get("appliedProd");
				    newLvl = (String) hinfo.get("lvl");
				    int inewLvl = Integer.parseInt(newLvl) + 1;

				    lvl = Integer.toString(inewLvl);
				}

				if (appliedProd.equals(""))
				    appliedProd = partNumber;

				String insertColumn2 = " ECOHEADERNUMBER, PARENTITEMCODE, CHILDITEMCODE, BOMLEVEL, ITEMSEQ";
				insertColumn2 += ", BEFOREQUANTITY, BEFOREUNIT, BEFOREMATERIAL, BEFOREHARDNESSFROM, BEFOREHARDNESSTO, BEFOREDESIGNDATE";
				insertColumn2 += ", BEFOREICT, BEFOREREFTOP, BEFOREREFBOTTOM, ECOITEMCODE, APPLIEDPRODUCTCODE, BOMVERSION, ECOCODE	";

				sql = new StringBuffer();
				sql.append(" INSERT INTO KETBOMECOCOMPONENT (" + insertColumn2 + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, Utility.checkNVL(ecoNumber));// ECOHEADERNUMBER
				pstmt.setString(2, Utility.checkNVL(parentNo));// PARENTITEMCODE
				pstmt.setString(3, Utility.checkNVL(partNo));// CHILDITEMCODE
				pstmt.setString(4, Utility.checkNVL(lvl)); // BOMLEVEL
				pstmt.setString(5, Utility.checkNVL(seq)); // ITEMSEQ
				pstmt.setString(6, Utility.checkNVL(qty)); // BEFOREQUANTITY
				pstmt.setString(7, Utility.checkNVL(unit)); // BEFOREUNIT
				pstmt.setString(8, Utility.checkNVL(material)); // BEFOREMATERIAL
				pstmt.setString(9, Utility.checkNVL(hardnessFrom)); // BEFOREHARDNESSFROM
				pstmt.setString(10, Utility.checkNVL(hardnessTo)); // BEFOREHARDNESSTO
				pstmt.setString(11, Utility.checkNVL(designDate)); // BEFOREDESIGNDATE
				pstmt.setString(12, Utility.checkNVL(ict)); // BEFOREICT
				pstmt.setString(13, Utility.checkNVL(reftop)); // BEFOREREFTOP
				pstmt.setString(14, Utility.checkNVL(refbtm)); // BEFOREREFBOTTOM
				pstmt.setString(15, Utility.checkNVL(partNumber));// ECOITEMCODE
				pstmt.setString(16, Utility.checkNVL(appliedProd));// APPLIEDPRODUCTCODE
				pstmt.setString(17, Utility.checkNVL(rev));// BOMVERSION
				pstmt.setString(18, Utility.checkNVL(""));// ECOCODE

				pstmt.executeUpdate();
				try{pstmt.close();}catch(Exception e){}
			    }
			}
		    }
		}
		
		KogDbUtil.log("BOM Editor 설변 Bom 저장", "Success", logEcoOid, (String)null, null, null);
	    }

	    conn.commit();

	    // KETEcmHelper.service.updateEcoAfterSaveBomEditor(ecoNumber, partNumber);
	} catch (Exception e) {
	    
	    if(isNewBomStart){
		KogDbUtil.log("BOM Editor 초도 Bom 저장", "Fail", (String)logEcoOid, e, null, null);
	    }
	    
	    if(isEcoBomStart){
		KogDbUtil.log("BOM Editor 설변 Bom 저장", "Fail", (String)logEcoOid, e, null, null);
	    }
	    
	    try {
		
		conn.rollback();
		Kogger.error(getClass(), e);
		result = e.toString();
		
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
		result = se.toString();
	    }

	    // throw e;
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
		result = e.toString();
		// throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);

		}
	    }

	}
	return result;
    }

    /**********************************************************
     * 개정 시 BOM 편집 데이터를 KETBOMECOCOMPONENT 에 INSERT
     * 
     * @throws Exception
     **********************************************************/
    public void reviseInsertComponentData(String ecoNumber, String oldPartOid, String newVersion, WTConnection conn) throws Exception {

	DBConnectionManager res = DBConnectionManager.getInstance();

	PreparedStatement pstmt = null;
	try {
	    List<Map<String, Object>> usageLinkList = new ArrayList<Map<String, Object>>();
	    List<Map<String, Object>> componemtList = new ArrayList<Map<String, Object>>();
	    KETBomPartUtil pUtil = new KETBomPartUtil();
	    Hashtable params = new Hashtable();

	    String partType = "";
	    String partNumber = "";

	    String insertColumn = " ECOHEADERNUMBER, PARENTITEMCODE, CHILDITEMCODE, BOMLEVEL, ITEMSEQ";
	    insertColumn += ", BEFOREQUANTITY, BEFOREUNIT, BEFOREMATERIAL, BEFOREHARDNESSFROM, BEFOREHARDNESSTO, BEFOREDESIGNDATE";
	    insertColumn += ", BEFOREICT, BEFOREREFTOP, BEFOREREFBOTTOM, ECOITEMCODE, APPLIEDPRODUCTCODE, BOMVERSION	";

	    StringBuffer sql;

	    WTPart part = pUtil.getPart(oldPartOid);

	    partNumber = part.getNumber();
	    partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);

	    long id = pUtil.getPartLongId(part);
	    String partOid = Long.toString(id);

	    params.put("partOid", partOid);
	    params.put("partType", partType);
	    params.put("dataType", "data");
	    params.put("partNumber", partNumber);
	    params.put("gubun", "ECOBOM");
	    params.put("inDelete", "Y");
	    params.put("ecoNumber", "");
	    params.put("gubun", "");

	    // usageLink 와 bomEcoComponent 비교 데이터 가져오기

	    // BOM Header 정보

	    ArrayList ecoHeaderList = getECOBomHeaderItem2(ecoNumber, conn);

	    Hashtable headerH = getHeaderList(ecoNumber, conn);

	    Hashtable partLvlInfo = getEcoHeaderCheckList(ecoNumber, conn);

	    Hashtable revH = new Hashtable();

	    for (int k = 0; k < ecoHeaderList.size(); k++) {
		Hashtable hdata = (Hashtable) ecoHeaderList.get(k);
		String hItem = (String) hdata.get("partNo");
		String hRev = (String) hdata.get("rev");
		String hRevYN = (String) hdata.get("reviseYN");

		if (hItem.equals(partNumber)) {
		    revH.put(hItem, newVersion);
		} else {
		    revH.put(hItem, hRev);
		}
	    }

	    // 기 등록된 BOM Data
	    componemtList = getEcoComponentData2(ecoNumber, conn);
	    // UsageLink BOM Data
	    usageLinkList = getLatestBOM(params);

	    Hashtable checkHash = new Hashtable();

	    if (componemtList != null) {
		for (int i = 0; i < componemtList.size(); i++) //
		{
		    Hashtable compdata = (Hashtable) componemtList.get(i);
		    String uParentNo = (String) compdata.get("parentNo");
		    String uPartNo = (String) compdata.get("partNo");
		    String uEcoitemcode = (String) compdata.get("ecoitemcode");

		    checkHash.put(uParentNo + "↔" + uPartNo, compdata);
		    // data.put("ecoitemcode", ecoitemcode);
		    if (revH.containsKey(uPartNo)) {
			String ver = (String) revH.get(uPartNo);
			sql = new StringBuffer();
			if (partNumber.equals(uParentNo) && !partNumber.equals(uEcoitemcode)) {
			    sql.append(" UPDATE KETBOMECOCOMPONENT SET  ECOITEMCODE='" + partNumber + "',  BOMVERSION='" + ver + "' WHERE ECOHEADERNUMBER='" + ecoNumber + "' AND CHILDITEMCODE='"
				    + uPartNo + "' ");
			} else {

			    sql.append(" UPDATE KETBOMECOCOMPONENT SET  BOMVERSION='" + ver + "' WHERE ECOHEADERNUMBER='" + ecoNumber + "' AND CHILDITEMCODE='" + uPartNo + "' ");

			    pstmt = conn.prepareStatement(sql.toString());
			    pstmt.executeUpdate();
			    try{pstmt.close();}catch(Exception e){}
			}
		    }
		}
	    }

	    if (usageLinkList != null) {
		for (int j = 1; j < usageLinkList.size(); j++) { // j가 1부터 시작하는 이유는 0레벨은 제외할려고....
		    Hashtable usgedata = (Hashtable) usageLinkList.get(j);

		    String uParentNo = (String) usgedata.get("parentNo");
		    String uPartNo = (String) usgedata.get("partNo");
		    String uRev = (String) usgedata.get("rev");

		    if (revH.containsKey(uPartNo))
			uRev = (String) revH.get(uPartNo);

		    if (uRev.indexOf(".") != -1) {
			uRev = uRev.substring(0, uRev.indexOf("."));
		    }

		    String uIct = (String) usgedata.get("ict");
		    String uLvl = (String) usgedata.get("lvl");

		    String lvl = "";
		    String appliedProd = "";
		    String checkKey = uParentNo + "^" + uPartNo;

		    String ecoitemcode = "";

		    if (partLvlInfo.containsKey(checkKey)) {
			Hashtable prodPinfo = (Hashtable) partLvlInfo.get(uParentNo + "^" + uPartNo);
			lvl = (String) prodPinfo.get("lvl");
			appliedProd = (String) prodPinfo.get("appliedProd");
			ecoitemcode = (String) prodPinfo.get("ecoitemcode");
		    }

		    if (appliedProd.equals("")) {
			appliedProd = partNumber;
			ecoitemcode = partNumber;
			lvl = uLvl;
		    }

		    if (ecoitemcode.equals("")) {//ecoitemcode가 값이 없을 경우 insert시 에러발생하므로 추가함
		    	ecoitemcode = partNumber;
		    }
		    
		    String uSeq = (String) usgedata.get("seq");
		    String uQty = (String) usgedata.get("qty");

		    // if(uQty)

		    if (uQty.indexOf(".") != -1) {
			if (uQty.substring(uQty.indexOf(".") + 1).length() == 1) {
			    uQty = uQty + "00";
			}

			if (uQty.substring(uQty.indexOf(".") + 1).length() == 2) {
			    uQty = uQty + "0";
			}
		    } else {
			uQty = uQty + ".000";
		    }

		    String uUnit = (String) usgedata.get("unit");

		    if (uUnit.indexOf("KET_PERCENT") != -1)
			uUnit = "KET_%";

		    String uReftop = (String) usgedata.get("reftop");
		    String uRefbtm = (String) usgedata.get("refbtm");
		    String uMaterial = (String) usgedata.get("material");
		    String uHardnessFrom = (String) usgedata.get("hardnessFrom");
		    String uHardnessTo = (String) usgedata.get("hardnessTo");
		    String uDesignDate = (String) usgedata.get("designDate");
		    // String bomFlag = partNewOld( uPartNo, uRev);

		    if (!checkHash.containsKey(uParentNo + "↔" + uPartNo)) {
			// Kogger.debug(getClass(), "Insert Data===>" + uParentNo + "↔" + uPartNo);
			// INSERT
			sql = new StringBuffer();
			sql.append(" INSERT INTO KETBOMECOCOMPONENT (" + insertColumn + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, Utility.checkNVL(ecoNumber));// ECOHEADERNUMBER
			pstmt.setString(2, Utility.checkNVL(uParentNo));// PARENTITEMCODE
			pstmt.setString(3, Utility.checkNVL(uPartNo));// CHILDITEMCODE
			pstmt.setString(4, Utility.checkNVL(lvl)); // BOMLEVEL
			pstmt.setString(5, Utility.checkNVL(uSeq)); // ITEMSEQ
			pstmt.setString(6, Utility.checkNVL(uQty)); // BEFOREQUANTITY
			pstmt.setString(7, Utility.checkNVL(uUnit)); // BEFOREUNIT
			pstmt.setString(8, Utility.checkNVL(uMaterial)); // BEFOREMATERIAL
			pstmt.setString(9, Utility.checkNVL(uHardnessFrom)); // BEFOREHARDNESSFROM
			pstmt.setString(10, Utility.checkNVL(uHardnessTo)); // BEFOREHARDNESSTO
			pstmt.setString(11, Utility.checkNVL(uDesignDate)); // BEFOREDESIGNDATE
			pstmt.setString(12, Utility.checkNVL(uIct)); // BEFOREICT
			pstmt.setString(13, Utility.checkNVL(uReftop)); // BEFOREREFTOP
			pstmt.setString(14, Utility.checkNVL(uRefbtm)); // BEFOREREFBOTTOM
			pstmt.setString(15, Utility.checkNVL(ecoitemcode));// ECOITEMCODE
			pstmt.setString(16, Utility.checkNVL(appliedProd));// APPLIEDPRODUCTCODE
			pstmt.setString(17, Utility.checkNVL(uRev));// BOMVERSION

			pstmt.executeUpdate();
			try{pstmt.close();}catch(Exception e){}

		    } else {

			Hashtable cHash = (Hashtable) checkHash.get(uParentNo + "↔" + uPartNo);

			// String ecoitemcode = "";
			String cLvl = "";
			int icLvl = 0;
			int iuLvl = 0;
			if (cHash.containsKey("lvl")) {
			    cLvl = (String) cHash.get("lvl");
			    icLvl = Integer.parseInt(cLvl);
			    iuLvl = Integer.parseInt(lvl);
			}

			Kogger.debug(getClass(), "icLvl(컴포넌트)==>" + icLvl);
			Kogger.debug(getClass(), "iuLvl(유시지링크)==>" + iuLvl);

			/*
		         * if (icLvl <= iuLvl) { // uLvl = cLvl; // ecoitemcode = (String) cHash.get("ecoitemcode"); ecoitemcode = partNumber; } else { ecoitemcode = partNumber; }
		         */

			if (appliedProd.equals("")) {
			    appliedProd = ecoitemcode;
			    ecoitemcode = partNumber;
			} else {
			    ecoitemcode = ecoitemcode;
			}

			sql = new StringBuffer();
			sql.append(" UPDATE KETBOMECOCOMPONENT SET BOMLEVEL='" + lvl + "', ECOITEMCODE='" + ecoitemcode + "', APPLIEDPRODUCTCODE='" + appliedProd + "' , BOMVERSION='" + uRev
			        + "' WHERE ECOHEADERNUMBER='" + ecoNumber + "' AND CHILDITEMCODE='" + uPartNo + "' ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.executeUpdate();
			try{pstmt.close();}catch(Exception e){}

		    }
		}
	    }

	} finally {
	    try {
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

    }

    /**********************************************************
     * 개정 취소 시 BOM 편집 데이터를 KETBOMECOCOMPONENT 삭제
     * 
     * @throws Exception
     **********************************************************/

    public void reviseCancelComponentData(String ecoNumber, Map oldPartInfoMap, WTConnection conn) throws Exception {

	PreparedStatement pstmt = null;
	try {
	    List<Map<String, Object>> usageLinkList = new ArrayList<Map<String, Object>>();
	    List<Map<String, Object>> componemtList = new ArrayList<Map<String, Object>>();
	    List<Map<String, Object>> componemtList2 = new ArrayList<Map<String, Object>>();
	    KETBomPartUtil pUtil = new KETBomPartUtil();
	    Hashtable params = new Hashtable();

	    String partType = "";
	    String partNumber = "";
	    String oldVersion = "";
	    String partOid = "";

	    String insertColumn = " ECOHEADERNUMBER, PARENTITEMCODE, CHILDITEMCODE, BOMLEVEL, ITEMSEQ";
	    insertColumn += ", BEFOREQUANTITY, BEFOREUNIT, BEFOREMATERIAL, BEFOREHARDNESSFROM, BEFOREHARDNESSTO, BEFOREDESIGNDATE";
	    insertColumn += ", BEFOREICT, BEFOREREFTOP, BEFOREREFBOTTOM, ECOITEMCODE, APPLIEDPRODUCTCODE, BOMVERSION	";

	    StringBuffer sql;

	    /**
	     * tklee 수정 13.1.14 : revise ObjectNoLongerExistsException 에러 처리
	     * 
	     * 에러 위치 WTPart part = pUtil.getPart(oldPartOid);
	     * 
	     * 
	     */
	    // WTPart part = pUtil.getPart(oldPartOid);
	    //
	    // partNumber = part.getNumber();
	    // oldVersion = part.getVersionIdentifier().getValue().toString();
	    //
	    // String oldPartOid2 = getPartOid2(partNumber, oldVersion, conn);
	    // if (!oldPartOid2.equals(""))
	    // part = pUtil.getPart(oldPartOid2);
	    //
	    // Kogger.debug(getClass(), "oldVersion-------$$$$$$$$$****************************>>>>>>>" + oldVersion);
	    //
	    // partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
	    //
	    // long id = pUtil.getPartLongId(part);
	    // partOid = Long.toString(id);

	    partNumber = (String) oldPartInfoMap.get("partNumber");
	    oldVersion = (String) oldPartInfoMap.get("version");
	    partType = (String) oldPartInfoMap.get("partType");
	    partOid = (String) oldPartInfoMap.get("partOid");

	    params.put("partOid", partOid);
	    params.put("partType", partType);
	    params.put("dataType", "data");
	    // params.put("partNumber", partNumber);
	    // params.put("gubun", "ECOBOM");
	    // params.put("inDelete", "Y");

	    componemtList2 = getEcoComponentData3(ecoNumber, partNumber, conn);

	    // usageLink 와 bomEcoComponent 비교 데이터 가져오기
	    usageLinkList = getLatestBOM(params);
	    // componemtList = getEditBOM(params);
	    componemtList = getEcoComponentData2(ecoNumber, conn);

	    boolean isChild = false;
	    String ecoitemcode = "";

	    ArrayList HeaderItemList = getECOBomHeaderItem2(ecoNumber, conn);

	    Hashtable partLvlInfo = getEcoHeaderCheckList(ecoNumber, conn);

	    sql = new StringBuffer();
	    sql.append(" DELETE FROM KETBOMECOCOMPONENT  WHERE ECOHEADERNUMBER='" + ecoNumber + "' AND ECOITEMCODE='" + partNumber + "' ");

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.executeUpdate();
	    try{pstmt.close();}catch(Exception e){}

	    Hashtable childH = new Hashtable();
	    Hashtable parentH = new Hashtable();

	    for (int i = 0; i < componemtList.size(); i++) //
	    {
		Hashtable compdata = (Hashtable) componemtList.get(i);
		String cParentNo = (String) compdata.get("parentNo");
		String cPartNo = (String) compdata.get("partNo");
		String cRev = (String) compdata.get("rev");
		String cEcoItemcode = (String) compdata.get("rev");
		String cProduct = (String) compdata.get("product");

		String[] iteminfo = new String[2];
		iteminfo[0] = cEcoItemcode;
		iteminfo[1] = cProduct;

		if (!partNumber.equals(cEcoItemcode) && partNumber.equals(cPartNo)) {
		    // partnumH.put(cPartNo, cEcoItemcode);

		    childH.put(partNumber, iteminfo);
		}

		if (!partNumber.equals(cEcoItemcode) && partNumber.equals(cParentNo)) {
		    // partnumH.put(cPartNo, cEcoItemcode);
		    parentH.put(partNumber, iteminfo);
		}

		if (partNumber.equals(cPartNo)) {
		    // BOMVERSION UPDATE
		    sql = new StringBuffer();
		    sql.append(" UPDATE KETBOMECOCOMPONENT SET BOMVERSION='" + oldVersion + "' WHERE ECOHEADERNUMBER='" + ecoNumber + "' AND CHILDITEMCODE='" + cPartNo + "' ");

		    pstmt = conn.prepareStatement(sql.toString());
		    pstmt.executeUpdate();
		    try{pstmt.close();}catch(Exception e){}

		    isChild = true;
		    ecoitemcode = (String) compdata.get("ecoitemcode");

		    Kogger.debug(getClass(), "XXXXXXXXXXXXXXX--------------------------------isChild===>" + ecoitemcode);
		}
	    }

	    if (childH.containsKey(partNumber) && !parentH.containsKey(partNumber)) {
		isChild = true;
	    }

	    if (isChild) {
		for (int j = 1; j < usageLinkList.size(); j++) { // j가 1부터 시작하는 이유는 0레벨은 제외할려고....
		    Hashtable usgedata = (Hashtable) usageLinkList.get(j);
		    String uParentNo = (String) usgedata.get("parentNo");
		    String uPartNo = (String) usgedata.get("partNo");
		    String uRev = (String) usgedata.get("rev");

		    String uIct = (String) usgedata.get("ict");
		    String uLvl = (String) usgedata.get("lvl");
		    String uSeq = (String) usgedata.get("seq");
		    String uQty = (String) usgedata.get("qty");
		    String uUnit = (String) usgedata.get("unit");
		    String uReftop = (String) usgedata.get("reftop");
		    String uRefbtm = (String) usgedata.get("refbtm");
		    String uMaterial = (String) usgedata.get("material");
		    String uHardnessFrom = (String) usgedata.get("hardnessFrom");
		    String uHardnessTo = (String) usgedata.get("hardnessTo");
		    String uDesignDate = (String) usgedata.get("designDate");

		    // Kogger.debug(getClass(), "uPartNo=========>" + uPartNo);
		    // Kogger.debug(getClass(), "uLvl=========>" + uLvl);

		    // partLvlInfo

		    String checkKey = uParentNo + "^" + uPartNo;

		    if (partLvlInfo.containsKey(checkKey)) {

			Hashtable prodPinfo = (Hashtable) partLvlInfo.get(checkKey);
			String chkecoitemcode = (String) prodPinfo.get("ecoitemcode");

			Kogger.debug(getClass(), "PartNumber===>" + partNumber);

			Kogger.debug(getClass(), "chkecoitemcode===>" + chkecoitemcode);

			if (!partNumber.equals(chkecoitemcode)) {
			    sql = new StringBuffer();
			    sql.append(" INSERT INTO KETBOMECOCOMPONENT (" + insertColumn + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			    pstmt = conn.prepareStatement(sql.toString());

			    pstmt.setString(1, Utility.checkNVL(ecoNumber));// ECOHEADERNUMBER
			    pstmt.setString(2, Utility.checkNVL(uParentNo));// PARENTITEMCODE
			    pstmt.setString(3, Utility.checkNVL(uPartNo));// CHILDITEMCODE
			    pstmt.setString(4, Utility.checkNVL(uLvl)); // BOMLEVEL
			    pstmt.setString(5, Utility.checkNVL(uSeq)); // ITEMSEQ
			    pstmt.setString(6, Utility.checkNVL(uQty)); // BEFOREQUANTITY
			    pstmt.setString(7, Utility.checkNVL(uUnit)); // BEFOREUNIT
			    pstmt.setString(8, Utility.checkNVL(uMaterial)); // BEFOREMATERIAL
			    pstmt.setString(9, Utility.checkNVL(uHardnessFrom)); // BEFOREHARDNESSFROM
			    pstmt.setString(10, Utility.checkNVL(uHardnessTo)); // BEFOREHARDNESSTO
			    pstmt.setString(11, Utility.checkNVL(uDesignDate)); // BEFOREDESIGNDATE
			    pstmt.setString(12, Utility.checkNVL(uIct)); // BEFOREICT
			    pstmt.setString(13, Utility.checkNVL(uReftop)); // BEFOREREFTOP
			    pstmt.setString(14, Utility.checkNVL(uRefbtm)); // BEFOREREFBOTTOM
			    pstmt.setString(15, Utility.checkNVL(chkecoitemcode));// ECOITEMCODE
			    pstmt.setString(16, Utility.checkNVL(ecoitemcode));// APPLIEDPRODUCTCODE
			    pstmt.setString(17, Utility.checkNVL(uRev));// BOMVERSION

			    pstmt.executeUpdate();
			    try{pstmt.close();}catch(Exception e){}
			}
		    }
		}
	    }

	} finally {
	    try {
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

    }

    /**********************************************************
     * BOM 편집 권한 체크용
     * 
     * @throws Exception
     **********************************************************/
    public Hashtable getEcoBomHeaderChecker(String partNumber, String partRev) throws Exception {
	Hashtable result = new Hashtable();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	String partOid = "";
	String dataType = "";

	try {

	    sql.append("SELECT ROWNUM, Y0.* FROM (											");
	    sql.append("SELECT  X0.ECOHEADERNUMBER, X0.ECOITEMCODE, X0.HEADER, X0.COMPONENT, X0.STATESTATE								");
	    sql.append("FROM														");
	    sql.append("(														");
	    sql.append("  SELECT ECOHEADERNUMBER AS ECOHEADERNUMBER, NEWBOMCODE AS ECOITEMCODE, 'KETBOMHEADER' AS HEADER, 'KETBOMCOMPONENT' AS COMPONENT, STATESTATE		");
	    sql.append("    FROM KETBOMHEADER 												");
	    sql.append("   WHERE NEWBOMCODE=? AND BOMVERSION=? AND STATESTATE IN ('INWORK','UNDERREVIEW')									");
	    sql.append("   UNION													");
	    sql.append("  SELECT ECOHEADERNUMBER AS ECOHEADERNUMBER, ECOITEMCODE,  'KETBOMECOHEADER' AS HEADER, 'KETBOMECOCOMPONENT' AS COMPONENT, STATESTATE	");
	    sql.append("    FROM KETBOMECOHEADER WHERE ECOITEMCODE = ? AND BOMVERSION=? AND STATESTATE IN ('INWORK','UNDERREVIEW')	AND ATTRIBUTE1='Y'			");
	    sql.append(") X0 ORDER BY X0.HEADER ASC )Y0 WHERE ROWNUM=1									");

	    Kogger.debug(getClass(), "SQL==>" + sql.toString());

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, partNumber);
	    pstmt.setString(2, partRev);
	    pstmt.setString(3, partNumber);
	    pstmt.setString(4, partRev);
	    rs = pstmt.executeQuery();

	    String header = "";
	    String component = "";
	    String ecoNumber = "";
	    String state = "";
	    String ecoItemcode = "";
	    while (rs.next()) {
		ecoNumber = (String) rs.getString("ECOHEADERNUMBER") == null ? "" : rs.getString("ECOHEADERNUMBER").trim();
		ecoItemcode = (String) rs.getString("ECOITEMCODE") == null ? "" : rs.getString("ECOITEMCODE").trim();
		header = (String) rs.getString("HEADER") == null ? "" : rs.getString("HEADER").trim();
		component = (String) rs.getString("COMPONENT") == null ? "" : rs.getString("COMPONENT").trim();
		state = (String) rs.getString("STATESTATE") == null ? "" : rs.getString("STATESTATE").trim();
	    }

	    // Kogger.debug(getClass(), "partNumber==>" + partNumber);
	    // Kogger.debug(getClass(), "SQL==>" + sql.toString());
	    // Kogger.debug(getClass(), "ECOHEADERNUMBER==>" + ecoNumber);
	    // Kogger.debug(getClass(), "HEADER==>" + header);

	    result.put("ecoNumber", ecoNumber);

	    if (header.equals("KETBOMHEADER")) {
		result.put("gubun", "NEWBOM");
	    } else {
		result.put("gubun", "ECOBOM");
	    }

	    Hashtable chargerHash = new Hashtable();
	    if (header.equals("KETBOMHEADER")) {
		chargerHash = getChargerHash(ecoItemcode, header);
	    } else {
		chargerHash = getChargerHash(ecoNumber, header);
	    }

	    result.put("charger", chargerHash);
	    result.put("state", state);
	    result.put("ecoItemcode", ecoItemcode);

	    // WTUser usr = (WTUser) SessionHelper.manager.getPrincipal();
	    // usr.getName()

	} catch (Exception e) {
	    try {
		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return result;
    }

    /**********************************************************
     * BOM 편집 권한 체크용
     * 
     * @throws Exception
     **********************************************************/
    public Hashtable getChargerHash(String ecoNumber, String header) throws Exception {
	Hashtable chargerHash = new Hashtable();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	String partOid = "";
	String dataType = "";

	try {

	    if (header.equals("KETBOMHEADER")) {
		sql.append("SELECT H0.NEWBOMCODE AS ECOHEADERNUMBER, H0.NEWBOMCODE AS ECOITEMCODE, C0.COWORKERID, C0.COWORKERNAME 	");
		// sql.append("FROM KETBOMHEADER H0, KETBOMCOWORKER C0  									");
		// sql.append("WHERE H0.NEWBOMCODE=C0.NEWBOMCODE AND H0.STATESTATE='INWORK' 						");
		sql.append("FROM KETBOMHEADER H0, KETBOMECOCOWORKER C0  									");
		sql.append("WHERE H0.NEWBOMCODE=C0.ECOITEMCODE AND H0.STATESTATE IN ('INWORK','UNDERREVIEW') 						");
		sql.append("AND H0.NEWBOMCODE=? 											");
	    } else {
		sql.append("SELECT H0.ECOHEADERNUMBER, H0.ECOITEMCODE, C0.COWORKERID, C0.COWORKERNAME  					");
		sql.append(" FROM KETBOMECOHEADER H0, KETBOMECOCOWORKER C0 								");
		sql.append("  WHERE H0.ECOHEADERNUMBER=C0.ECOHEADERNUMBER AND H0.STATESTATE IN ('INWORK','UNDERREVIEW') 					");
		sql.append("  AND H0.ECOHEADERNUMBER = ?										");
	    }

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoNumber);
	    rs = pstmt.executeQuery();

	    String itemcode = "";
	    String coworker = "";
	    String coworkernm = "";

	    while (rs.next()) {
		itemcode = (String) rs.getString("ECOITEMCODE") == null ? "" : rs.getString("ECOITEMCODE").trim();
		coworker = (String) rs.getString("COWORKERID") == null ? "" : rs.getString("COWORKERID").trim();
		// coworkernm = (String) rs.getString("COWORKERNAME") == null ? "" : rs.getString("COWORKERNAME").trim();

		chargerHash.put(itemcode, coworker);
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
		throw e;
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
		throw se;
	    }
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return chargerHash;
    }

    /**********************************************************
     * BOM 편집 권한 체크용
     * 
     * @throws Exception
     **********************************************************/
    public Hashtable getVersionHash(String ecoNumber, String header, WTConnection conn) throws Exception {
	Hashtable chargerHash = new Hashtable();

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    StringBuffer sql = new StringBuffer();

	    String partOid = "";
	    String dataType = "";

	    if (header.equals("KETBOMHEADER")) {
		sql.append("SELECT H0.NEWBOMCODE AS ECOHEADERNUMBER, H0.NEWBOMCODE AS ECOITEMCODE, C0.COWORKERID, C0.COWORKERNAME, H0.BOMVERSION 	");
		// sql.append("FROM KETBOMHEADER H0, KETBOMCOWORKER C0  									");
		// sql.append("WHERE H0.NEWBOMCODE=C0.NEWBOMCODE AND H0.STATESTATE='INWORK' 						");
		sql.append("FROM KETBOMHEADER H0, KETBOMECOCOWORKER C0  									");
		sql.append("WHERE H0.NEWBOMCODE=C0.ECOITEMCODE AND H0.STATESTATE IN ('INWORK','UNDERREVIEW') 						");
		sql.append("AND H0.NEWBOMCODE=? 											");
	    } else {
		sql.append("SELECT H0.ECOHEADERNUMBER, H0.ECOITEMCODE, C0.COWORKERID, C0.COWORKERNAME, H0.BOMVERSION  					");
		sql.append(" FROM KETBOMECOHEADER H0, KETBOMECOCOWORKER C0 								");
		sql.append("  WHERE H0.ECOHEADERNUMBER=C0.ECOHEADERNUMBER AND H0.STATESTATE IN ('INWORK','UNDERREVIEW') 					");
		sql.append("  AND H0.ATTRIBUTE1='Y' AND H0.ECOHEADERNUMBER = ?										");
	    }

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoNumber);
	    rs = pstmt.executeQuery();

	    String itemcode = "";
	    String coworker = "";
	    String coworkernm = "";
	    String version = "";

	    while (rs.next()) {
		itemcode = (String) rs.getString("ECOITEMCODE") == null ? "" : rs.getString("ECOITEMCODE").trim();
		version = (String) rs.getString("BOMVERSION") == null ? "" : rs.getString("BOMVERSION").trim();

		Kogger.debug(getClass(), "getVersionHash(" + itemcode + ":" + version + ")");
		chargerHash.put(itemcode, version);
	    }

	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return chargerHash;
    }

    /**********************************************************
     * BOM 편집 권한 체크용
     * 
     * @throws Exception
     **********************************************************/
    public Hashtable getChargerHash2(String ecoNumber, String header) throws Exception {
	Hashtable chargerHash = new Hashtable();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	String partOid = "";
	String dataType = "";

	try {

	    if (header.equals("KETBOMHEADER")) {
		sql.append("SELECT H0.NEWBOMCODE AS ECOHEADERNUMBER, H0.NEWBOMCODE AS ECOITEMCODE, C0.COWORKERID, C0.COWORKERNAME, H0.BOMVERSION 	");
		// sql.append("FROM KETBOMHEADER H0, KETBOMCOWORKER C0  									");
		// sql.append("WHERE H0.NEWBOMCODE=C0.NEWBOMCODE AND H0.STATESTATE='INWORK' 						");
		sql.append("FROM KETBOMHEADER H0, KETBOMECOCOWORKER C0  									");
		sql.append("WHERE H0.NEWBOMCODE=C0.ECOITEMCODE AND H0.STATESTATE IN ('INWORK','UNDERREVIEW') 						");
		sql.append("AND H0.NEWBOMCODE=? 											");
	    } else {
		sql.append("SELECT H0.ECOHEADERNUMBER, H0.ECOITEMCODE, C0.COWORKERID, C0.COWORKERNAME, H0.BOMVERSION  					");
		sql.append(" FROM KETBOMECOHEADER H0, KETBOMECOCOWORKER C0 								");
		sql.append("  WHERE H0.ECOHEADERNUMBER=C0.ECOHEADERNUMBER AND H0.STATESTATE IN ('INWORK','UNDERREVIEW') 					");
		sql.append("  AND H0.ECOHEADERNUMBER = ?										");
	    }

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoNumber);
	    rs = pstmt.executeQuery();

	    String itemcode = "";
	    String coworker = "";
	    String coworkernm = "";
	    String version = "";

	    while (rs.next()) {
		itemcode = (String) rs.getString("ECOITEMCODE") == null ? "" : rs.getString("ECOITEMCODE").trim();
		coworker = (String) rs.getString("COWORKERID") == null ? "" : rs.getString("COWORKERID").trim();
		coworkernm = (String) rs.getString("COWORKERNAME") == null ? "" : rs.getString("COWORKERNAME").trim();
		version = (String) rs.getString("BOMVERSION") == null ? "" : rs.getString("BOMVERSION").trim();

		Hashtable dataHash = new Hashtable();
		dataHash.put("COWORKERID", coworker);
		dataHash.put("COWORKERNAME", coworkernm);
		dataHash.put("VERSION", version);

		chargerHash.put(itemcode, dataHash);
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
		throw e;
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
		throw se;
	    }
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return chargerHash;
    }

    /**********************************************************
     * ECO BOM 편집 DATA TABLE 체크
     * 
     * @throws Exception
     **********************************************************/
    public String getEcoBomTmpTableChecker(String ecoNumber) throws Exception {

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	String cnt = "";

	try {

	    sql.append("SELECT COUNT(*) AS CNT FROM KETBOMECOTEMPCOMPONENT WHERE ECOHEADERNUMBER = ? ");
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoNumber);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		cnt = (String) rs.getString("CNT") == null ? "" : rs.getString("CNT").trim();
	    }

	} catch (Exception e) {
	    try {
		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return cnt;
    }

    /**********************************************************
     * ECO BOM 편집 DATA TABLE 체크
     * 
     * @throws Exception
     **********************************************************/
    public Hashtable getBomVersionList(String gubun, String ecoNumber) throws Exception {

	Hashtable revHash = new Hashtable();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	String cnt = "";

	try {

	    if (gubun.equals("NEWBOM")) {

		sql.append(" SELECT NEWBOMCODE AS ITEMCODE, BOMVERSION FROM KETBOMHEADER WHERE NEWBOMCODE = ? ");
		sql.append(" UNION ");
		sql.append(" SELECT CHILDITEMCODE AS ITEMCODE, BOMVERSION FROM KETBOMCOMPONENT WHERE NEWBOMCODE = ? ");
	    } else {
		sql.append(" SELECT ECOITEMCODE AS ITEMCODE, BOMVERSION FROM KETBOMECOHEADER WHERE ECOHEADERNUMBER = ? ");
		sql.append(" UNION ");
		sql.append(" SELECT CHILDITEMCODE AS ITEMCODE, BOMVERSION FROM KETBOMECOCOMPONENT WHERE ECOHEADERNUMBER = ? ");
	    }
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoNumber);
	    pstmt.setString(2, ecoNumber);
	    rs = pstmt.executeQuery();

	    String itemcode = "";
	    String rev = "";

	    while (rs.next()) {
		itemcode = (String) rs.getString("ITEMCODE") == null ? "" : rs.getString("ITEMCODE").trim();
		rev = (String) rs.getString("BOMVERSION") == null ? "" : rs.getString("BOMVERSION").trim();

		// Kogger.debug(getClass(), "==>" + itemcode + "[" + rev + "]");
		revHash.put(itemcode, rev);
	    }

	} catch (Exception e) {
	    try {
		conn.rollback();
		Kogger.error(getClass(), e);

	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return revHash;
    }

    /**********************************************************
     * ECO BOM HEADER 부품리스트 가져오기
     * 
     * @throws Exception
     **********************************************************/
    public ArrayList getECOBomHeaderItem(String ecoNumber) throws Exception {

	ArrayList itemList = new ArrayList();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	String cnt = "";

	try {

	    sql.append(" SELECT ECOITEMCODE AS ITEMCODE FROM KETBOMECOHEADER WHERE ECOHEADERNUMBER = ? ");

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoNumber);
	    rs = pstmt.executeQuery();

	    String itemcode = "";
	    String rev = "";

	    while (rs.next()) {
		itemcode = (String) rs.getString("ITEMCODE") == null ? "" : rs.getString("ITEMCODE").trim();
		// Kogger.debug(getClass(), "==>" + itemcode + "[" + rev + "]");
		itemList.add(itemcode);
	    }

	} catch (Exception e) {
	    try {
		conn.rollback();
		Kogger.error(getClass(), e);

	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return itemList;
    }

    public ArrayList getECOBomHeaderItem2(String ecoNumber, WTConnection conn) throws Exception {

	ArrayList itemList = new ArrayList();

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    StringBuffer sql = new StringBuffer();

	    String cnt = "";

	    sql.append(" SELECT ECOITEMCODE AS ITEMCODE, BOMVERSION, ATTRIBUTE1 AS REVISEYN FROM KETBOMECOHEADER WHERE ECOHEADERNUMBER = ? ");

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoNumber);
	    rs = pstmt.executeQuery();

	    String itemcode = "";
	    String rev = "";
	    String reviseYN = "";

	    while (rs.next()) {
		itemcode = (String) rs.getString("ITEMCODE") == null ? "" : rs.getString("ITEMCODE").trim();
		rev = (String) rs.getString("BOMVERSION") == null ? "" : rs.getString("BOMVERSION").trim();
		reviseYN = (String) rs.getString("REVISEYN") == null ? "" : rs.getString("REVISEYN").trim();
		// Kogger.debug(getClass(), "==>" + itemcode + "[" + rev + "]");
		Hashtable data = new Hashtable();

		data.put("partNo", itemcode);
		data.put("rev", rev);
		data.put("reviseYN", reviseYN);

		itemList.add(data);
	    }

	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return itemList;
    }

    public ArrayList getECOBomHeaderItem3(String ecoNumber) throws Exception {

	ArrayList itemList = new ArrayList();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	String cnt = "";

	try {

	    sql.append(" SELECT ECOITEMCODE,  BOMVERSION, ATTRIBUTE1 AS REVISEYN FROM KETBOMECOHEADER WHERE ECOHEADERNUMBER = ? ");

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoNumber);
	    rs = pstmt.executeQuery();

	    String itemcode = "";
	    String rev = "";
	    String reviseYN = "";

	    while (rs.next()) {
		itemcode = (String) rs.getString("ECOITEMCODE") == null ? "" : rs.getString("ECOITEMCODE").trim();
		rev = (String) rs.getString("BOMVERSION") == null ? "" : rs.getString("BOMVERSION").trim();
		reviseYN = (String) rs.getString("REVISEYN") == null ? "" : rs.getString("REVISEYN").trim();
		Kogger.debug(getClass(), "==>" + itemcode + "[" + rev + "]" + "[" + reviseYN + "]");
		Hashtable data = new Hashtable();

		data.put("partNo", itemcode);
		data.put("rev", rev);
		data.put("reviseYN", reviseYN);

		itemList.add(data);
	    }

	} catch (Exception e) {
	    try {
		conn.rollback();
		Kogger.error(getClass(), e);

	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return itemList;
    }

    public Hashtable getECOBomHeaderItem4(String ecoNumber, WTConnection conn) throws Exception {

	Hashtable itemHash = new Hashtable();

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    StringBuffer sql = new StringBuffer();

	    String cnt = "";

	    sql.append(" SELECT ECOITEMCODE AS ITEMCODE, BOMVERSION, ATTRIBUTE1 AS REVISEYN FROM KETBOMECOHEADER WHERE ECOHEADERNUMBER = ? ");

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoNumber);
	    rs = pstmt.executeQuery();

	    String itemcode = "";
	    String rev = "";
	    String reviseYN = "";

	    while (rs.next()) {
		itemcode = (String) rs.getString("ITEMCODE") == null ? "" : rs.getString("ITEMCODE").trim();
		rev = (String) rs.getString("BOMVERSION") == null ? "" : rs.getString("BOMVERSION").trim();
		reviseYN = (String) rs.getString("REVISEYN") == null ? "" : rs.getString("REVISEYN").trim();
		// Kogger.debug(getClass(), "==>" + itemcode + "[" + rev + "]");
		Hashtable data = new Hashtable();

		data.put("partNo", itemcode);
		data.put("rev", rev);
		data.put("reviseYN", reviseYN);

		itemHash.put(itemcode, data);
	    }

	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return itemHash;
    }

    /**********************************************************
     * KETBOMECOCOMPONENT 해당 ECO 전체 리스트 가져오기
     * 
     * @throws Exception
     **********************************************************/
    public List<Map<String, Object>> getEcoComponentData2(String ecoNumber, WTConnection conn) throws Exception {
	List<Map<String, Object>> componentList = new ArrayList<Map<String, Object>>();

	DBConnectionManager res = DBConnectionManager.getInstance();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    StringBuffer sql = new StringBuffer();

	    Hashtable itemVersionHash = new Hashtable();

	    String partNo = "";
	    String rev = "";
	    String partName = "";
	    String lvl = "";
	    String seq = "";
	    String qty = "";
	    String unit = "";
	    String econo = "";
	    String checkout = "";
	    String checkoutId = "";
	    String ict = "";
	    String reftop = "";
	    String refbtm = "";
	    String material = "";
	    String hardnessFrom = "";
	    String hardnessTo = "";
	    String designDate = "";
	    String statestr = "";
	    String parentNo = "";
	    String pver = "";

	    String bom_path = "";
	    String ecocode = "";
	    String ecoitemcode = "";
	    String appliedProduct = "";

	    sql.append("SELECT * FROM KETBOMECOCOMPONENT WHERE ECOHEADERNUMBER='" + ecoNumber + "'");

	    pstmt = conn.prepareStatement(sql.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		Map<String, Object> data = new Hashtable();

		partNo = rs.getString("CHILDITEMCODE") == null ? "" : rs.getString("CHILDITEMCODE").trim();
		rev = rs.getString("BOMVERSION") == null ? "" : rs.getString("BOMVERSION").trim();
		lvl = rs.getString("BOMLEVEL") == null ? "" : rs.getString("BOMLEVEL").trim();
		seq = rs.getString("ITEMSEQ") == null ? "" : rs.getString("ITEMSEQ").trim();
		ict = rs.getString("BEFOREICT") == null ? "" : rs.getString("BEFOREICT").trim();
		qty = rs.getString("BEFOREQUANTITY") == null ? "" : rs.getString("BEFOREQUANTITY").trim();
		unit = rs.getString("BEFOREUNIT") == null ? "" : rs.getString("BEFOREUNIT").trim();
		reftop = rs.getString("BEFOREREFTOP") == null ? "" : rs.getString("BEFOREREFTOP").trim();
		refbtm = rs.getString("BEFOREREFBOTTOM") == null ? "" : rs.getString("BEFOREREFBOTTOM").trim();
		material = rs.getString("BEFOREMATERIAL") == null ? "" : rs.getString("BEFOREMATERIAL").trim();
		hardnessFrom = rs.getString("BEFOREHARDNESSFROM") == null ? "" : rs.getString("BEFOREHARDNESSFROM").trim();
		hardnessTo = rs.getString("BEFOREHARDNESSTO") == null ? "" : rs.getString("BEFOREHARDNESSTO").trim();
		designDate = rs.getString("BEFOREDESIGNDATE") == null ? "" : rs.getString("BEFOREDESIGNDATE").trim();
		parentNo = rs.getString("PARENTITEMCODE") == null ? "" : rs.getString("PARENTITEMCODE").trim();
		ecoitemcode = rs.getString("ECOITEMCODE") == null ? "" : rs.getString("ECOITEMCODE").trim();
		appliedProduct = rs.getString("APPLIEDPRODUCTCODE") == null ? "" : rs.getString("APPLIEDPRODUCTCODE").trim();

		data.put("partNo", partNo);
		data.put("rev", rev);
		data.put("lvl", lvl);
		data.put("ict", ict);
		data.put("seq", seq);
		data.put("qty", qty);
		data.put("unit", unit);
		data.put("reftop", reftop);
		data.put("refbtm", refbtm);
		data.put("material", material);
		data.put("hardnessFrom", hardnessFrom);
		data.put("hardnessTo", hardnessTo);
		data.put("designDate", designDate);
		data.put("parentNo", parentNo);
		data.put("ecoitemcode", ecoitemcode);
		data.put("product", appliedProduct);

		componentList.add(data);

	    }

	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return componentList;
    }

    public List<Map<String, Object>> getEcoComponentData3(String ecoNumber, String partNumber, WTConnection conn) throws Exception {
	List<Map<String, Object>> componentList = new ArrayList<Map<String, Object>>();

	DBConnectionManager res = DBConnectionManager.getInstance();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    StringBuffer sql = new StringBuffer();

	    Hashtable itemVersionHash = new Hashtable();

	    String partNo = "";
	    String rev = "";
	    String partName = "";
	    String lvl = "";
	    String seq = "";
	    String qty = "";
	    String unit = "";
	    String econo = "";
	    String checkout = "";
	    String checkoutId = "";
	    String ict = "";
	    String reftop = "";
	    String refbtm = "";
	    String material = "";
	    String hardnessFrom = "";
	    String hardnessTo = "";
	    String designDate = "";
	    String statestr = "";
	    String parentNo = "";
	    String pver = "";

	    String bom_path = "";
	    String ecocode = "";
	    String ecoitemcode = "";

	    sql.append("SELECT * FROM KETBOMECOCOMPONENT WHERE ECOHEADERNUMBER='" + ecoNumber + "' AND ECOITEMCODE='" + partNumber + "'");

	    pstmt = conn.prepareStatement(sql.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		Map<String, Object> data = new Hashtable();

		partNo = rs.getString("CHILDITEMCODE") == null ? "" : rs.getString("CHILDITEMCODE").trim();
		rev = rs.getString("BOMVERSION") == null ? "" : rs.getString("BOMVERSION").trim();
		lvl = rs.getString("BOMLEVEL") == null ? "" : rs.getString("BOMLEVEL").trim();
		seq = rs.getString("ITEMSEQ") == null ? "" : rs.getString("ITEMSEQ").trim();
		ict = rs.getString("BEFOREICT") == null ? "" : rs.getString("BEFOREICT").trim();
		qty = rs.getString("BEFOREQUANTITY") == null ? "" : rs.getString("BEFOREQUANTITY").trim();
		unit = rs.getString("BEFOREUNIT") == null ? "" : rs.getString("BEFOREUNIT").trim();
		reftop = rs.getString("BEFOREREFTOP") == null ? "" : rs.getString("BEFOREREFTOP").trim();
		refbtm = rs.getString("BEFOREREFBOTTOM") == null ? "" : rs.getString("BEFOREREFBOTTOM").trim();
		material = rs.getString("BEFOREMATERIAL") == null ? "" : rs.getString("BEFOREMATERIAL").trim();
		hardnessFrom = rs.getString("BEFOREHARDNESSFROM") == null ? "" : rs.getString("BEFOREHARDNESSFROM").trim();
		hardnessTo = rs.getString("BEFOREHARDNESSTO") == null ? "" : rs.getString("BEFOREHARDNESSTO").trim();
		designDate = rs.getString("BEFOREDESIGNDATE") == null ? "" : rs.getString("BEFOREDESIGNDATE").trim();
		parentNo = rs.getString("PARENTITEMCODE") == null ? "" : rs.getString("PARENTITEMCODE").trim();
		ecoitemcode = rs.getString("ECOITEMCODE") == null ? "" : rs.getString("ECOITEMCODE").trim();

		data.put("partNo", partNo);
		data.put("rev", rev);
		data.put("lvl", lvl);
		data.put("ict", ict);
		data.put("seq", seq);
		data.put("qty", qty);
		data.put("unit", unit);
		data.put("reftop", reftop);
		data.put("refbtm", refbtm);
		data.put("material", material);
		data.put("hardnessFrom", hardnessFrom);
		data.put("hardnessTo", hardnessTo);
		data.put("designDate", designDate);
		data.put("parentNo", parentNo);
		data.put("ecoitemcode", ecoitemcode);

		componentList.add(data);

	    }

	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return componentList;
    }

    /**********************************************************
     * KETBOMECOCOMPONENT 해당 ECO 전체 리스트 가져오기
     * 
     * @throws Exception
     **********************************************************/
    public List<Map<String, Object>> getEcoComponentData(String ecoNumber) throws Exception {
	List<Map<String, Object>> componentList = new ArrayList<Map<String, Object>>();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	Hashtable itemVersionHash = new Hashtable();

	String partNo = "";
	String rev = "";
	String partName = "";
	String lvl = "";
	String seq = "";
	String qty = "";
	String unit = "";
	String econo = "";
	String checkout = "";
	String checkoutId = "";
	String ict = "";
	String reftop = "";
	String refbtm = "";
	String material = "";
	String hardnessFrom = "";
	String hardnessTo = "";
	String designDate = "";
	String statestr = "";
	String parentNo = "";
	String pver = "";

	String bom_path = "";
	String ecocode = "";
	String ecoitemcode = "";

	try {

	    sql.append("SELECT * FROM KETBOMECOCOMPONENT WHERE ECOHEADERNUMBER='" + ecoNumber + "'");

	    pstmt = conn.prepareStatement(sql.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		Map<String, Object> data = new Hashtable();

		partNo = rs.getString("CHILDITEMCODE") == null ? "" : rs.getString("CHILDITEMCODE").trim();
		rev = rs.getString("BOMVERSION") == null ? "" : rs.getString("BOMVERSION").trim();
		lvl = rs.getString("BOMLEVEL") == null ? "" : rs.getString("BOMLEVEL").trim();
		seq = rs.getString("ITEMSEQ") == null ? "" : rs.getString("ITEMSEQ").trim();
		ict = rs.getString("BEFOREICT") == null ? "" : rs.getString("BEFOREICT").trim();
		qty = rs.getString("BEFOREQUANTITY") == null ? "" : rs.getString("BEFOREQUANTITY").trim();
		unit = rs.getString("BEFOREUNIT") == null ? "" : rs.getString("BEFOREUNIT").trim();
		reftop = rs.getString("BEFOREREFTOP") == null ? "" : rs.getString("BEFOREREFTOP").trim();
		refbtm = rs.getString("BEFOREREFBOTTOM") == null ? "" : rs.getString("BEFOREREFBOTTOM").trim();
		material = rs.getString("BEFOREMATERIAL") == null ? "" : rs.getString("BEFOREMATERIAL").trim();
		hardnessFrom = rs.getString("BEFOREHARDNESSFROM") == null ? "" : rs.getString("BEFOREHARDNESSFROM").trim();
		hardnessTo = rs.getString("BEFOREHARDNESSTO") == null ? "" : rs.getString("BEFOREHARDNESSTO").trim();
		designDate = rs.getString("BEFOREDESIGNDATE") == null ? "" : rs.getString("BEFOREDESIGNDATE").trim();
		parentNo = rs.getString("PARENTITEMCODE") == null ? "" : rs.getString("PARENTITEMCODE").trim();
		ecoitemcode = rs.getString("ECOITEMCODE") == null ? "" : rs.getString("ECOITEMCODE").trim();

		data.put("partNo", partNo);
		data.put("rev", rev);
		data.put("lvl", lvl);
		data.put("ict", ict);
		data.put("seq", seq);
		data.put("qty", qty);
		data.put("unit", unit);
		data.put("reftop", reftop);
		data.put("refbtm", refbtm);
		data.put("material", material);
		data.put("hardnessFrom", hardnessFrom);
		data.put("hardnessTo", hardnessTo);
		data.put("designDate", designDate);
		data.put("parentNo", parentNo);
		data.put("ecoitemcode", ecoitemcode);

		componentList.add(data);

	    }
	} catch (Exception e) {
	    try {
		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return componentList;
    }

    /**********************************************************
     * BOM 편집 데이터 리스트 가져오기
     * 
     * @throws Exception
     **********************************************************/
    public List<Map<String, Object>> getEditBOM(Hashtable params) throws Exception {
	List<Map<String, Object>> bomList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();
	KETBomPartUtil util = new KETBomPartUtil();

	String gubun = "";
	String ecoNumber = "";
	String partNumber = "";
	String partType = "";
	String dataType = "";
	String inDelete = ""; // 삭제 포함 여부 (Y : 삭제 포함, N : 삭제 제외)
	String newEcoNumber = "";

	Hashtable itemVersionHash = new Hashtable();
	

	String partNo = "";
	String rev = "";
	String newrev = "";
	String partName = "";
	String lvl = "";
	String seq = "";
	String qty = "";
	String unit = "";
	String econo = "";
	String checkout = "";
	String checkoutId = "";
	String ict = "";
	String reftop = "";
	String refbtm = "";
	String material = "";
	String hardnessFrom = "";
	String hardnessTo = "";
	String designDate = "";
	String statestr = "";
	String parentNo = "";
	String pver = "";

	String bom_path = "";
	String ecocode = "";
	String ecoitemcode = "";
	String addSearchYN = "";
	int i = 0;

	int old_lvl = 0;

	try {
	    isEditBom = true;
	    
	    gubun = (String) params.get("gubun");
	    ecoNumber = (String) params.get("ecoNumber");
	    partNumber = (String) params.get("partNumber");
	    partType = (String) params.get("partType");
	    dataType = (String) params.get("dataType");
	    inDelete = (String) params.get("inDelete");

	    Kogger.debug(getClass(), "ecoNumber===============>" + ecoNumber);
	    

	    // Kogger.debug(getClass(), "==>" + gubun + "[" + ecoNumber + "]" + "[" + partNumber + "]" + "[" + partType + "]");

	    itemVersionHash = getBomVersionList(gubun, ecoNumber);

	    if (gubun.equals("NEWBOM")) {

		/*sql.append("SELECT * FROM ( 													");
		sql.append("SELECT 1 AS SEQ , A0.NEWBOMCODE AS CHILDITEMCODE, A0.BOMVERSION, A0.DESCRIPTION AS PARTNAME, 0 AS BOMLEVEL, '10' AS ITEMSEQ, ");
		sql.append("       NVL(A0.BOXQUANTITY,'1') AS QUANTITY, A0.UNITOFQUANTITY AS UNIT, A0.ECOHEADERNUMBER AS ECONO, A0.STATESTATE,	");
		sql.append("       B0.COWORKERNAME AS CHECKOUT, B0.COWORKERID AS CHECKOUTID, '' AS ICT, '' AS REFTOP, '' AS REFBOTTOM, 		");
		sql.append("       '' AS MATERIAL, '' AS HARDNESSFROM, '' AS HARDNESSTO, '' AS DESIGNDATE, '' AS PARENTITEMCODE			");
		// sql.append("  FROM KETBOMHEADER A0, KETBOMCOWORKER B0										");
		// sql.append(" WHERE A0.NEWBOMCODE=B0.NEWBOMCODE											");
		sql.append("  FROM KETBOMHEADER A0, KETBOMECOCOWORKER B0										");
		sql.append(" WHERE A0.NEWBOMCODE=B0.ECOITEMCODE											");
		sql.append("   AND A0.NEWBOMCODE=?												");
		sql.append(" UNION														");
		sql.append("SELECT Y0.* FROM 													");
		sql.append("(															");
		sql.append("	SELECT (ROWNUM+1) AS SEQ, A0.CHILDITEMCODE, A0.BOMVERSION, B0.NAME AS PARTNAME, LEVEL AS BOMLEVEL, A0.ITEMSEQ, A0.QUANTITY,		");
		// sql.append("	       A0.UNIT, C0.ECOHEADERNUMBER AS ECONO, '' AS STATESTATE, '' AS CHECKOUT, '' AS CHECKOUTID, A0.ICT, A0.REFTOP, A0.REFBOTTOM,	");
		sql.append("	       A0.UNIT, '' AS ECONO, '' AS STATESTATE, '' AS CHECKOUT, '' AS CHECKOUTID, A0.ICT, A0.REFTOP, A0.REFBOTTOM,	");
		sql.append("	       A0.MATERIAL, A0.HARDNESSFROM, A0.HARDNESSTO,A0.DESIGNDATE,A0.PARENTITEMCODE				");
		// sql.append("	  FROM KETBOMCOMPONENT A0, WTPARTMASTER B0, KETBOMHEADER C0									");
		// sql.append("	 WHERE A0.CHILDITEMCODE = B0.WTPARTNUMBER AND A0.NEWBOMCODE = C0.NEWBOMCODE AND A0.NEWBOMCODE=?									");
		sql.append("	  FROM KETBOMCOMPONENT A0, WTPARTMASTER B0									");
		sql.append("	 WHERE A0.CHILDITEMCODE = B0.WTPARTNUMBER AND A0.NEWBOMCODE=?									");
		sql.append("	 START WITH A0.PARENTITEMCODE=?											");
		sql.append("       CONNECT BY PRIOR CHILDITEMCODE=PARENTITEMCODE								");
		sql.append("	 ORDER SIBLINGS BY  												");

		if (!(partType.equals("D") || partType.equals("M"))) {
		    // 제품
		    sql.append("	 A0.ITEMSEQ												");
		} else {
		    // 금형
		    sql.append("	 A0.CHILDITEMCODE											");
		}
		sql.append(" ) Y0 ) ORDER BY SEQ												");*/
		
		sql.append(" SELECT T.*, REGEXP_SUBSTR(PARTINFO, '[^|]+', 1, 1) AS PART_STATE, REGEXP_SUBSTR(PARTINFO, '[^|]+', 1, 2) AS EONO, REGEXP_SUBSTR(PARTINFO, '[^|]+', 1, 3) AS NEWVERSION FROM (                  \n");
		sql.append("         SELECT BOM.*, FN_GET_WTPART_LASTINFO(BOM.CHILDITEMCODE) PARTINFO FROM (                                                          \n");
		sql.append("         SELECT 1 AS SEQ , A0.NEWBOMCODE AS CHILDITEMCODE, A0.BOMVERSION, A0.DESCRIPTION AS PARTNAME, 0 AS BOMLEVEL, '10' AS ITEMSEQ,     \n");
		sql.append("                NVL(A0.BOXQUANTITY,'1') AS QUANTITY, A0.UNITOFQUANTITY AS UNIT, A0.ECOHEADERNUMBER AS ECONO, A0.STATESTATE,               \n");
		sql.append("                B0.COWORKERNAME AS CHECKOUT, B0.COWORKERID AS CHECKOUTID, '' AS ICT, '' AS REFTOP, '' AS REFBOTTOM,                       \n");
		sql.append("                '' AS MATERIAL, '' AS HARDNESSFROM, '' AS HARDNESSTO, '' AS DESIGNDATE, '' AS PARENTITEMCODE                              \n");
		sql.append("           FROM KETBOMHEADER A0, KETBOMECOCOWORKER B0                                                                                     \n");
		sql.append("          WHERE A0.NEWBOMCODE=B0.ECOITEMCODE                                                                                              \n");
		sql.append("            AND A0.NEWBOMCODE= ?                                                                                                          \n");
		sql.append("          UNION                                                                                                                           \n");
		sql.append("         SELECT Y0.* FROM                                                                                                                 \n");
		sql.append("         (                                                                                                                                \n");
		sql.append("             SELECT (ROWNUM+1) AS SEQ, A0.CHILDITEMCODE, A0.BOMVERSION, B0.NAME AS PARTNAME, LEVEL AS BOMLEVEL, A0.ITEMSEQ, A0.QUANTITY,  \n");
		sql.append("                    A0.UNIT, '' AS ECONO, '' AS STATESTATE, '' AS CHECKOUT, '' AS CHECKOUTID, A0.ICT, A0.REFTOP, A0.REFBOTTOM,            \n");
		sql.append("                    A0.MATERIAL, A0.HARDNESSFROM, A0.HARDNESSTO,A0.DESIGNDATE,A0.PARENTITEMCODE                                           \n");
		sql.append("               FROM (SELECT * FROM KETBOMCOMPONENT WHERE NEWBOMCODE=?) A0, WTPARTMASTER B0                                                                                   \n");
		sql.append("              WHERE A0.CHILDITEMCODE = B0.WTPARTNUMBER                                                                \n");
		sql.append("              START WITH A0.PARENTITEMCODE=?                                                                                              \n");
		sql.append("                CONNECT BY PRIOR CHILDITEMCODE=PARENTITEMCODE                                                                             \n");
		sql.append("              ORDER SIBLINGS BY                                                                                                           \n");
		if (!(partType.equals("D") || partType.equals("M"))) {
		    // 제품
		    sql.append("	 A0.ITEMSEQ												\n");
		} else {
		    // 금형
		    sql.append("	 A0.CHILDITEMCODE											\n");
		}
		sql.append("          ) Y0 ) BOM ) T ORDER BY SEQ 												      \n");

		Kogger.debug(getClass(), "SQL==>" + sql.toString());
		pstmt = conn.prepareStatement(sql.toString());
		pstmt.setString(1, partNumber);
		pstmt.setString(2, partNumber);
		pstmt.setString(3, partNumber);

		rs = pstmt.executeQuery();

		while (rs.next()) {
		    Kogger.debug(getClass(), "getEditBOM");
		    Map<String, Object> data = new Hashtable();

		    partNo = rs.getString("CHILDITEMCODE") == null ? "" : rs.getString("CHILDITEMCODE").trim();
		    rev = rs.getString("BOMVERSION") == null ? "" : rs.getString("BOMVERSION").trim();
		    newrev = rs.getString("NEWVERSION") == null ? "" : rs.getString("NEWVERSION").trim();
		    partName = rs.getString("PARTNAME") == null ? "" : rs.getString("PARTNAME").trim();
		    lvl = rs.getString("BOMLEVEL") == null ? "" : rs.getString("BOMLEVEL").trim();
		    int new_lvl = Integer.parseInt(lvl);
		    seq = rs.getString("ITEMSEQ") == null ? "" : rs.getString("ITEMSEQ").trim();
		    qty = rs.getString("QUANTITY") == null ? "" : rs.getString("QUANTITY").trim();
		    unit = rs.getString("UNIT") == null ? "" : rs.getString("UNIT").trim();

		    if (unit.indexOf("KET_%") != -1)
			unit = "KET_PERCENT";

/*		    String poid = getPartOid(partNo, rev);
		    WTPart part = util.getPart(poid);
		    econo = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpEoNo);
*/
		    if (econo == null || econo.equals("")) {
			econo = rs.getString("EONO") == null ? "" : rs.getString("EONO").trim();
		    }

/*		    if (econo == null || econo.equals("")) {
			econo = newEcoNumber;
		    }*/

		    checkout = rs.getString("CHECKOUT") == null ? "" : rs.getString("CHECKOUT").trim();
		    checkoutId = rs.getString("CHECKOUTID") == null ? "" : rs.getString("CHECKOUTID").trim();
		    ict = rs.getString("ICT") == null ? "" : rs.getString("ICT").trim();
		    reftop = rs.getString("REFTOP") == null ? "" : rs.getString("REFTOP").trim();
		    refbtm = rs.getString("REFBOTTOM") == null ? "" : rs.getString("REFBOTTOM").trim();
		    // material = rs.getString("MATERIAL") == null ? "" : rs.getString("MATERIAL").trim();
		    if ((partType.equals("D") || partType.equals("M")))
			material = getMaterial(partNo, rev);
		    hardnessFrom = rs.getString("HARDNESSFROM") == null ? "" : rs.getString("HARDNESSFROM").trim();
		    hardnessTo = rs.getString("HARDNESSTO") == null ? "" : rs.getString("HARDNESSTO").trim();
		    designDate = rs.getString("DESIGNDATE") == null ? "" : rs.getString("DESIGNDATE").trim();
		    statestr = rs.getString("STATESTATE") == null ? "" : rs.getString("STATESTATE").trim();

		    // Kogger.debug(getClass(), "NEWBOM==>" + partNo + "[" + rev + "]");

		    if (lvl.equals("0")) {
			qty = getBoxQty(partNo, rev);
			newEcoNumber = econo;
		    }

		    String statename = "";
		    if (!statestr.equals("")) {
			State state = State.toState(statestr);
			statename = state.getDisplay();
		    }

/*		    if (statename.equals("")) {
			String cpoid = getPartOid(partNo, rev);
			Kogger.debug(getClass(), "partOid===>" + cpoid);
			WTPart cpart = util.getPart(cpoid);

			if (cpart != null) {
			    statename = cpart.getLifeCycleState().getDisplay();
			    Kogger.debug(getClass(), "statename===>" + statename);
			}
		    }*/

		    parentNo = rs.getString("PARENTITEMCODE") == null ? "" : rs.getString("PARENTITEMCODE").trim();
		    if (!parentNo.equals(""))
			pver = (String) itemVersionHash.get(parentNo);

		    if (new_lvl > old_lvl) {
			bom_path += lvl + "^" + parentNo + "|";
		    } else {
			if (new_lvl < old_lvl)
			    bom_path = bom_path.substring(0, bom_path.indexOf(lvl + "^" + parentNo + "|")) + lvl + "^" + parentNo + "|";
		    }

		    data.put("partNo", partNo);
		    data.put("rev", rev);
		    data.put("newrev", newrev);
		    data.put("partName", partName);
		    data.put("ict", ict);
		    data.put("lvl", lvl);
		    data.put("seq", seq);
		    data.put("qty", qty);
		    data.put("unit", unit);
		    data.put("econo", econo);
		    data.put("checkout", checkout);
		    data.put("checkoutId", checkoutId);
		    data.put("reftop", reftop);
		    data.put("refbtm", refbtm);
		    data.put("material", material);
		    data.put("hardnessFrom", hardnessFrom);
		    data.put("hardnessTo", hardnessTo);
		    data.put("designDate", designDate);
		    data.put("state", statename);
		    data.put("parentNo", parentNo);
		    data.put("pver", pver);
		    data.put("ecoitemcode", ecoitemcode);

		    data.put("bom_path", bom_path);

		    bomList.add(data);
		}

	    } else {

		// ArrayList headerItems = getECOBomHeaderItem(ecoNumber);

		sql = new StringBuffer();
		/*sql.append("SELECT * FROM ( 													");
		sql.append("  SELECT 1 AS SEQ , A0.ECOITEMCODE AS CHILDITEMCODE, A0.BOMVERSION, A0.DESCRIPTION AS PARTNAME, 0 AS BOMLEVEL, '10' AS ITEMSEQ, NVL(A0.BOXQUANTITY,'1') AS BEFOREQUANTITY,	");
		sql.append("         A0.UNITOFQUANTITY AS BEFOREUNIT, A0.ECOHEADERNUMBER AS ECONO, B0.COWORKERNAME AS CHECKOUT, B0.COWORKERID AS CHECKOUTID,						");
		sql.append("         '' AS BEFOREICT, '' AS BEFOREREFTOP, '' AS BEFOREREFBOTTOM, '' AS BEFOREMATERIAL, '' AS BEFOREHARDNESSFROM, '' AS BEFOREHARDNESSTO, '' AS BEFOREDESIGNDATE, 	");
		sql.append("         '' AS AFTERQUANTITY, '' AS AFTERUNIT, '' AS AFTERICT, '' AS AFTERREFTOP, '' AS AFTERREFBOTTOM,'' AS AFTERMATERIAL,'' AS AFTERHARDNESSFROM, 			");
		sql.append("         '' AS AFTERHARDNESSTO,'' AS AFTERDESIGNDATE, '' AS PARENTITEMCODE, '' AS ECOCODE, '' AS ECOITEMCODE, A0.STATESTATE, A0.ATTRIBUTE1 AS REVISEYN, FN_GET_WTPARTLASTSTATE(A0.ECOITEMCODE) AS PARTSTATE			");
		sql.append("    FROM KETBOMECOHEADER A0, KETBOMECOCOWORKER B0																");
		sql.append("   WHERE A0.ECOHEADERNUMBER=B0.ECOHEADERNUMBER																");
		sql.append("     AND A0.ECOHEADERNUMBER=? AND A0.ECOITEMCODE=? 	AND A0.ECOITEMCODE=B0.ECOITEMCODE															");
		sql.append("   UNION																					");
		sql.append("  SELECT Y0.*, NVL(D0.STATESTATE,'') AS STATESTATE, '' AS REVISEYN, FN_GET_WTPARTLASTSTATE(CHILDITEMCODE) AS PARTSTATE FROM 															");
		sql.append("  (																						");
		sql.append("  	SELECT (ROWNUM+1) AS SEQ, A0.CHILDITEMCODE, A0.BOMVERSION, B0.NAME AS PARTNAME, LEVEL AS BOMLEVEL, A0.ITEMSEQ, A0.BEFOREQUANTITY,					");
		sql.append("           A0.BEFOREUNIT, A0.ECOHEADERNUMBER AS ECONO, NVL(C0.COWORKERNAME,'') AS CHECKOUT, NVL(C0.COWORKERID,'') AS CHECKOUTID, 						");
		sql.append("           A0.BEFOREICT, A0.BEFOREREFTOP, A0.BEFOREREFBOTTOM, A0.BEFOREMATERIAL, A0.BEFOREHARDNESSFROM, A0.BEFOREHARDNESSTO,						");
		sql.append("           A0.BEFOREDESIGNDATE, A0.AFTERQUANTITY, A0.AFTERUNIT, A0.AFTERICT, A0.AFTERREFTOP, A0.AFTERREFBOTTOM,								");
		sql.append("           A0.AFTERMATERIAL, A0.AFTERHARDNESSFROM, A0.AFTERHARDNESSTO,A0.AFTERDESIGNDATE, A0.PARENTITEMCODE, A0.ECOCODE, A0.ECOITEMCODE					");
		sql.append("      FROM KETBOMECOCOMPONENT A0, WTPARTMASTER B0, 																");
		sql.append("           (SELECT COWORKERID,COWORKERNAME, ECOITEMCODE, ECOHEADERNUMBER  FROM KETBOMECOCOWORKER WHERE ECOHEADERNUMBER = ? ) C0								");
		sql.append("     WHERE A0.CHILDITEMCODE = B0.WTPARTNUMBER AND A0.ECOHEADERNUMBER = ?  																");

		if (inDelete.equals("N"))
		    sql.append("       AND NVL(A0.ECOCODE, ' ') <>'Remove'																");

		sql.append("       AND A0.ECOITEMCODE = C0.ECOITEMCODE 																");
		sql.append("       AND A0.ECOHEADERNUMBER=C0.ECOHEADERNUMBER																		");

		sql.append("       AND A0.ECOHEADERNUMBER=?																		");
		sql.append("     START WITH A0.PARENTITEMCODE=?																		");
		sql.append("   CONNECT BY PRIOR A0.CHILDITEMCODE=A0.PARENTITEMCODE																");
		sql.append("    ORDER SIBLINGS BY  																			");
		if (!(partType.equals("D") || partType.equals("M"))) {
		    // 제품
		    sql.append("         A0.ITEMSEQ																			");
		} else {
		    // 금형
		    sql.append("         A0.CHILDITEMCODE																		");
		}
		sql.append("  ) Y0, (SELECT ECOITEMCODE, STATESTATE FROM KETBOMECOHEADER WHERE ECOHEADERNUMBER = ?) D0											");
		sql.append("   WHERE Y0.CHILDITEMCODE = D0.ECOITEMCODE(+)   ) ORDER BY SEQ														");*/
		
		
		sql.append(" SELECT T.*, REGEXP_SUBSTR(PARTINFO, '[^|]+', 1, 1) AS PARTSTATE, REGEXP_SUBSTR(PARTINFO, '[^|]+', 1, 2) AS EONO, REGEXP_SUBSTR(PARTINFO, '[^|]+', 1, 3) AS NEWVERSION                   \n");
		sql.append("   FROM (                                                                                                                                                                                \n");
		sql.append("           SELECT BOM.*,FN_GET_WTPART_LASTINFO(BOM.CHILDITEMCODE) PARTINFO FROM                                                                                                          \n");
		sql.append("           (                                                                                                                                                                             \n");
		sql.append("           SELECT 1 AS SEQ , A0.ECOITEMCODE AS CHILDITEMCODE, A0.BOMVERSION, A0.DESCRIPTION AS PARTNAME, 0 AS BOMLEVEL, '10' AS ITEMSEQ, NVL(A0.BOXQUANTITY,'1') AS BEFOREQUANTITY,      \n");
		sql.append("                  A0.UNITOFQUANTITY AS BEFOREUNIT, A0.ECOHEADERNUMBER AS ECONO, B0.COWORKERNAME AS CHECKOUT, B0.COWORKERID AS CHECKOUTID,                                                \n");
		sql.append("                  '' AS BEFOREICT, '' AS BEFOREREFTOP, '' AS BEFOREREFBOTTOM, '' AS BEFOREMATERIAL, '' AS BEFOREHARDNESSFROM, '' AS BEFOREHARDNESSTO, '' AS BEFOREDESIGNDATE,            \n");
		sql.append("                  '' AS AFTERQUANTITY, '' AS AFTERUNIT, '' AS AFTERICT, '' AS AFTERREFTOP, '' AS AFTERREFBOTTOM,'' AS AFTERMATERIAL,'' AS AFTERHARDNESSFROM,                             \n");
		sql.append("                  '' AS AFTERHARDNESSTO,'' AS AFTERDESIGNDATE, '' AS PARENTITEMCODE, '' AS ECOCODE, '' AS ECOITEMCODE, A0.STATESTATE, A0.ATTRIBUTE1 AS REVISEYN                          \n");
		sql.append("             FROM KETBOMECOHEADER A0, KETBOMECOCOWORKER B0                                                                                                                               \n");
		sql.append("            WHERE A0.ECOHEADERNUMBER=B0.ECOHEADERNUMBER                                                                                                                                  \n");
		sql.append("              AND A0.ECOHEADERNUMBER=? AND A0.ECOITEMCODE=?     AND A0.ECOITEMCODE=B0.ECOITEMCODE                                                                                        \n");
		sql.append("            UNION                                                                                                                                                                        \n");
		sql.append("           SELECT Y0.*, NVL(D0.STATESTATE,'') AS STATESTATE, '' AS REVISEYN                                                                                                              \n");
		sql.append("             FROM                                                                                                                                                                        \n");
		sql.append("           (                                                                                                                                                                             \n");
		sql.append("               SELECT (ROWNUM+1) AS SEQ, A0.CHILDITEMCODE, A0.BOMVERSION, B0.NAME AS PARTNAME, LEVEL AS BOMLEVEL, A0.ITEMSEQ, A0.BEFOREQUANTITY,                                         \n");
		sql.append("                    A0.BEFOREUNIT, A0.ECOHEADERNUMBER AS ECONO, NVL(C0.COWORKERNAME,'') AS CHECKOUT, NVL(C0.COWORKERID,'') AS CHECKOUTID,                                                \n");
		sql.append("                    A0.BEFOREICT, A0.BEFOREREFTOP, A0.BEFOREREFBOTTOM, A0.BEFOREMATERIAL, A0.BEFOREHARDNESSFROM, A0.BEFOREHARDNESSTO,                                                    \n");
		sql.append("                    A0.BEFOREDESIGNDATE, A0.AFTERQUANTITY, A0.AFTERUNIT, A0.AFTERICT, A0.AFTERREFTOP, A0.AFTERREFBOTTOM,                                                                 \n");
		sql.append("                    A0.AFTERMATERIAL, A0.AFTERHARDNESSFROM, A0.AFTERHARDNESSTO,A0.AFTERDESIGNDATE, A0.PARENTITEMCODE, A0.ECOCODE, A0.ECOITEMCODE                                         \n");
		sql.append("               FROM KETBOMECOCOMPONENT A0, WTPARTMASTER B0,                                                                                                                              \n");
		sql.append("                    (SELECT COWORKERID,COWORKERNAME, ECOITEMCODE, ECOHEADERNUMBER  FROM KETBOMECOCOWORKER WHERE ECOHEADERNUMBER = ? ) C0                                                 \n");
		sql.append("              WHERE A0.CHILDITEMCODE = B0.WTPARTNUMBER AND A0.ECOHEADERNUMBER = ?                                                                                                        \n");
		if (inDelete.equals("N"))
		    sql.append("       AND NVL(A0.ECOCODE, ' ') <>'Remove'																");
		sql.append("                AND A0.ECOITEMCODE = C0.ECOITEMCODE                                                                                                                                      \n");
		sql.append("                AND A0.ECOHEADERNUMBER=C0.ECOHEADERNUMBER                                                                                                                                \n");
		sql.append("                AND A0.ECOHEADERNUMBER=?                                                                                                                                                 \n");
		sql.append("              START WITH A0.PARENTITEMCODE=?                                                                                                                                             \n");
		sql.append("            CONNECT BY PRIOR A0.CHILDITEMCODE=A0.PARENTITEMCODE                                                                                                                          \n");
		sql.append("            ORDER SIBLINGS BY                                                                                                                                                            \n ");
		if (!(partType.equals("D") || partType.equals("M"))) {
		    // 제품
		    sql.append("         A0.ITEMSEQ																			\n");
		} else {
		    // 금형
		    sql.append("         A0.CHILDITEMCODE																		\n");
		}
		sql.append("           ) Y0, (SELECT ECOITEMCODE, STATESTATE FROM KETBOMECOHEADER WHERE ECOHEADERNUMBER = ?) D0                                                                                      \n");
		sql.append("            WHERE Y0.CHILDITEMCODE = D0.ECOITEMCODE(+)                                                                                                                                   \n");
		sql.append("            ) BOM                                                                                                                                                                        \n");
		sql.append("         ) T ORDER BY SEQ                                                                                                                                                                \n");       
		           


		// Kogger.debug(getClass(), "SQL==>" + sql.toString());

		pstmt = conn.prepareStatement(sql.toString());
		pstmt.setString(1, ecoNumber);
		pstmt.setString(2, partNumber);
		pstmt.setString(3, ecoNumber);
		pstmt.setString(4, ecoNumber);
		pstmt.setString(5, ecoNumber);
		pstmt.setString(6, partNumber);
		pstmt.setString(7, ecoNumber);

		rs = pstmt.executeQuery();

		String reviseYN = "";

		Hashtable chargerH = getChargerHash2(ecoNumber, "KETBOMECOHEADER");

		// Kogger.debug(getClass(), chargerH.toString());

		ArrayList ecoHeaderList = new ArrayList();
		ecoHeaderList = getECOBomHeaderItem3(ecoNumber);
		    
		while (rs.next()) {
		    Kogger.debug(getClass(), "getEditBOM");
		    Map<String, Object> data = new Hashtable();

		    partNo = rs.getString("CHILDITEMCODE") == null ? "" : rs.getString("CHILDITEMCODE").trim();
		    rev = rs.getString("BOMVERSION") == null ? "" : rs.getString("BOMVERSION").trim();
		    newrev = rs.getString("NEWVERSION") == null ? "" : rs.getString("NEWVERSION").trim(); 
		    partName = rs.getString("PARTNAME") == null ? "" : rs.getString("PARTNAME").trim();
		    lvl = rs.getString("BOMLEVEL") == null ? "" : rs.getString("BOMLEVEL").trim();
		    int new_lvl = Integer.parseInt(lvl);
		    seq = rs.getString("ITEMSEQ") == null ? "" : rs.getString("ITEMSEQ").trim();

		    //String poid = getPartOid(partNo, rev);
		    //WTPart part = util.getPart(poid);
		    //econo = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpEoNo);

		    if (econo == null || econo.equals("")) {
			econo = rs.getString("EONO") == null ? "" : rs.getString("ECONO").trim();
		    }
		    
		    checkout = rs.getString("CHECKOUT") == null ? "" : rs.getString("CHECKOUT").trim();
		    checkoutId = rs.getString("CHECKOUTID") == null ? "" : rs.getString("CHECKOUTID").trim();

		    if (chargerH.containsKey(partNo)) {
			Hashtable cDataH = (Hashtable) chargerH.get(partNo);
			checkout = (String) cDataH.get("COWORKERNAME");
			checkoutId = (String) cDataH.get("COWORKERID");
		    } else {
			checkout = " ";
			checkoutId = " ";
		    }

		    ecocode = rs.getString("ECOCODE") == null ? "" : rs.getString("ECOCODE").trim();

		    if (lvl.equals("0")) {
			reviseYN = rs.getString("REVISEYN") == null ? "" : rs.getString("REVISEYN").trim();
			if (!reviseYN.equals("Y")) {
			    checkoutId = "";
			    // Kogger.debug(getClass(), partNo + " reviseYN ==>" + reviseYN);
			}
		    }
		    
		    
		    
		    Kogger.debug(getClass(), "ecoHeaderList.size()==>" + ecoHeaderList.size());
		    for (int k = 0; k < ecoHeaderList.size(); k++) {

			Hashtable hdata = (Hashtable) ecoHeaderList.get(k);

			String hItem = (String) hdata.get("partNo");
			String hRev = (String) hdata.get("rev");
			String reviseYN2 = (String) hdata.get("reviseYN");

			if (reviseYN2.equals("N") && hItem.equals(partNo)) {
			    Kogger.debug(getClass(), "reviseYN==" + reviseYN2);
			    Kogger.debug(getClass(), "hItem==" + hItem);
			    checkoutId = "";
			}
		    }

		    if (ecocode.equals("")) {

			// qty = rs.getString("BEFOREQUANTITY") == null ? "" : rs.getString("BEFOREQUANTITY").trim();
			// unit = rs.getString("BEFOREUNIT") == null ? "" : rs.getString("BEFOREUNIT").trim();
			// reftop = rs.getString("BEFOREREFTOP") == null ? "" : rs.getString("BEFOREREFTOP").trim();
			// refbtm = rs.getString("BEFOREREFBOTTOM") == null ? "" : rs.getString("BEFOREREFBOTTOM").trim();
			// material = rs.getString("BEFOREMATERIAL") == null ? "" : rs.getString("BEFOREMATERIAL").trim();
			// hardnessFrom = rs.getString("BEFOREHARDNESSFROM") == null ? "" : rs.getString("BEFOREHARDNESSFROM").trim();
			// hardnessTo = rs.getString("BEFOREHARDNESSTO") == null ? "" : rs.getString("BEFOREHARDNESSTO").trim();
			// designDate = rs.getString("BEFOREDESIGNDATE") == null ? "" : rs.getString("BEFOREDESIGNDATE").trim();
			ict = rs.getString("BEFOREICT") == null ? "" : rs.getString("BEFOREICT").trim();
			qty = rs.getString("BEFOREQUANTITY") == null ? "" : rs.getString("BEFOREQUANTITY").trim();
			unit = rs.getString("BEFOREUNIT") == null ? "" : rs.getString("BEFOREUNIT").trim();

			if (unit.indexOf("KET_%") != -1)
			    unit = "KET_PERCENT";

			reftop = rs.getString("BEFOREREFTOP") == null ? "" : rs.getString("BEFOREREFTOP").trim();
			refbtm = rs.getString("BEFOREREFBOTTOM") == null ? "" : rs.getString("BEFOREREFBOTTOM").trim();
			material = rs.getString("BEFOREMATERIAL") == null ? "" : rs.getString("BEFOREMATERIAL").trim();
			hardnessFrom = rs.getString("BEFOREHARDNESSFROM") == null ? "" : rs.getString("BEFOREHARDNESSFROM").trim();
			hardnessTo = rs.getString("BEFOREHARDNESSTO") == null ? "" : rs.getString("BEFOREHARDNESSTO").trim();
			designDate = rs.getString("BEFOREDESIGNDATE") == null ? "" : rs.getString("BEFOREDESIGNDATE").trim();
		    } else {
			ict = rs.getString("AFTERICT") == null ? "" : rs.getString("AFTERICT").trim();
			qty = rs.getString("AFTERQUANTITY") == null ? "" : rs.getString("AFTERQUANTITY").trim();
			unit = rs.getString("AFTERUNIT") == null ? "" : rs.getString("AFTERUNIT").trim();

			if (unit.indexOf("KET_%") != -1)
			    unit = "KET_PERCENT";

			reftop = rs.getString("AFTERREFTOP") == null ? "" : rs.getString("AFTERREFTOP").trim();
			refbtm = rs.getString("AFTERREFBOTTOM") == null ? "" : rs.getString("AFTERREFBOTTOM").trim();
			// material = rs.getString("AFTERMATERIAL") == null ? "" : rs.getString("AFTERMATERIAL").trim();

			if ((partType.equals("D") || partType.equals("M")))
			    material = getMaterial(partNo, rev);
			hardnessFrom = rs.getString("AFTERHARDNESSFROM") == null ? "" : rs.getString("AFTERHARDNESSFROM").trim();
			hardnessTo = rs.getString("AFTERHARDNESSTO") == null ? "" : rs.getString("AFTERHARDNESSTO").trim();
			designDate = rs.getString("AFTERDESIGNDATE") == null ? "" : rs.getString("AFTERDESIGNDATE").trim();
		    }

		    if (lvl.equals("0")) {
			qty = getBoxQty(partNo, rev);
		    }

		    // Kogger.debug(getClass(), "ECOBOM==>" + partNo + "[" + rev + "]");

		    statestr = rs.getString("PARTSTATE") == null ? "" : rs.getString("PARTSTATE").trim();

		    String statename = "";
		    if (!statestr.equals("")) {
			State state = State.toState(statestr);
			statename = state.getDisplay();
		    }

/*		    if (statename.equals("")) {
			String cpoid = getPartOid(partNo, rev);
			Kogger.debug(getClass(), "partOid===>" + cpoid);
			WTPart cpart = util.getPart(cpoid);

			if (cpart != null) {
			    statename = cpart.getLifeCycleState().getDisplay();
			    Kogger.debug(getClass(), "statename===>" + statename);
			}
		    }*/

		    parentNo = rs.getString("PARENTITEMCODE") == null ? "" : rs.getString("PARENTITEMCODE").trim();
		    if (!parentNo.equals(""))
			pver = (String) itemVersionHash.get(parentNo);

		    if (new_lvl > old_lvl) {
			bom_path += lvl + "^" + parentNo + "|";
		    } else {
			if (new_lvl < old_lvl)
			    bom_path = bom_path.substring(0, bom_path.indexOf(lvl + "^" + parentNo + "|")) + lvl + "^" + parentNo + "|";
		    }
		    if("INWORK".equals(rs.getString("PARTSTATE"))){
			addSearchYN = "Y";
		    }else{
			addSearchYN = "";
		    }

		    data.put("partNo", partNo);
		    data.put("rev", rev);
		    data.put("newrev", newrev);
		    data.put("partName", partName);
		    data.put("lvl", lvl);
		    data.put("ict", ict);
		    data.put("seq", seq);
		    data.put("qty", qty);
		    data.put("unit", unit);
		    data.put("econo", econo);
		    data.put("checkout", checkout);
		    data.put("checkoutId", checkoutId);
		    data.put("reftop", reftop);
		    data.put("refbtm", refbtm);
		    data.put("material", material);
		    data.put("hardnessFrom", hardnessFrom);
		    data.put("hardnessTo", hardnessTo);
		    data.put("designDate", designDate);
		    data.put("state", statename);
		    data.put("parentNo", parentNo);
		    data.put("pver", pver);
		    data.put("ecoitemcode", ecoitemcode);

		    data.put("bom_path", bom_path);
		    data.put("addSearchYN", addSearchYN);

		    bomList.add(data);
		}

	    }

	    if (dataType.equals("TreeGrid")) {

		treeList = getGridBOM(bomList);
	    } else {
		treeList = bomList;
	    }

	} catch (Exception e) {
	    try {
		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		isEditBom = false;
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return treeList;
    }

    /**********************************************************
     * BOM 역전개 정보 추출
     * 
     * @throws Exception
     **********************************************************/
    public List<Map<String, Object>> getReverseBOM(Hashtable params) throws Exception {
	List<Map<String, Object>> bomList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	String partOid = "";
	String dataType = "";
	String level = "";
	Hashtable keyHash = new Hashtable();
	KETBomPartUtil pUtil = new KETBomPartUtil();

	try {

	    partOid = (String) params.get("partOid");
	    level = (String) params.get("level");
	    dataType = (String) params.get("dataType");

	    Kogger.debug(getClass(), "partOid==>" + partOid);

	    WTPart part = null;
	    if (partOid.indexOf("wt.part.WTPart:") != -1) {
		part = pUtil.getPart(partOid);
	    } else {
		String newOid = "wt.part.WTPart:" + partOid;
		part = pUtil.getPart(newOid);
	    }
	    String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
	    // Kogger.debug(getClass(), "level==>" + level);

	    sql.append("SELECT * FROM ( 																									");
	    sql.append("          SELECT BOM.*,  																								");
	    sql.append("	        PH.NAME	STATUSKR , NVL(HD.HITEMKEY,'') AS HEADERKEY,  NVL(HD.ECOHEADERNUMBER,'') AS EONO, NVL(HD.COWORKERID,'') AS COUTERID, NVL(HD.COWORKERNAME,'') AS COUTER						");
	    sql.append("	        FROM 																									");
	    sql.append("	        (																									");
	    sql.append("	        SELECT 0 AS NUM, 																							");
	    sql.append("	                        0 AS LVL,	 																					");
	    sql.append("	                  10 AS ITEMSEQ,																						");
	    sql.append("	                        '' AS ASSEMBLY_ITEM_OID, 																				");
	    sql.append("	                        '' AS ASSEMBLY_ITEM_CODE,																				");
	    sql.append("	                        '' AS ASSEMBLY_ITEM_REV,																				");
	    sql.append("	                         A0.IDA2A2 AS COMPONENT_ITEM_OID, 																			");
	    sql.append("	                         A0.WTPARTNUMBER AS COMPONENT_ITEM_CODE,																		");
	    sql.append("	                         A0.NAME AS COMPONENT_ITEM_NAME, 																			");
	    sql.append("	                         (B0.VERSIONIDA2VERSIONINFO || '.' || B0.ITERATIONIDA2ITERATIONINFO) AS COMPONENT_ITEM_REV,												");
	    sql.append("	                         '1.000' AS QTY,  B0.STATESTATE AS STATUS,	B0.IDA3A2STATE,																");
	    sql.append("	                         'KET_EA' AS UNIT, null AS IDA2A2, '' AS STARTDATE, '' AS ENDDATE,  '' AS MATERIAL, '' AS HARDNESSFROM, '' AS HARDNESSTO, '' AS DESIGNDATE, '' AS ICT, '' AS REFTOP, '' AS REFBOTTOM,	");
	    sql.append("	                         null AS USAGELINKOID, 																					");
	    sql.append("	                        (A0.WTPARTNUMBER || '_' ||B0.VERSIONIDA2VERSIONINFO) AS ITEMINFO, '' AS PARENTITEMINFO,  '' AS ECONO													");
	    sql.append("	        FROM WTPARTMASTER A0, WTPART B0    																					");
	    sql.append("	        WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE 																	");
	    sql.append("	         AND B0.IDA2A2=?																							");
	    sql.append("	        ) BOM, PHASETEMPLATE PH  ,PHASELINK PL, 																				");
	    sql.append("	   (SELECT KEB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KEB.ECOITEMCODE||'_'||KEB.BOMVERSION) AS HITEMKEY, KEC.COWORKERID, KEC.COWORKERNAME, KEB.STATESTATE AS STATUS 					");
	    sql.append("	FROM KETBOMECOHEADER KEB, KETBOMECOCOWORKER KEC WHERE KEB.ECOHEADERNUMBER=KEC.ECOHEADERNUMBER AND KEB.ECOITEMCODE=KEC.ECOITEMCODE  AND KEB.STATESTATE='APPROVED'									");
	    sql.append("       UNION																									");
	    // sql.append("      SELECT KB.BOMTEXT AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 								");
	    sql.append("      SELECT KB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 								");
	    // sql.append("	FROM KETBOMHEADER KB, KETBOMCOWORKER KC 																				");
	    // sql.append("       WHERE  KB.NEWBOMCODE=KC.NEWBOMCODE) HD																					");
	    sql.append("	FROM KETBOMHEADER KB, KETBOMECOCOWORKER KC 																				");
	    sql.append("       WHERE  KB.NEWBOMCODE=KC.ECOITEMCODE  AND KB.ECOHEADERNUMBER=KC.ECOHEADERNUMBER  AND KB.STATESTATE='APPROVED') HD																					");
	    sql.append("	          WHERE  PL.IDA3A5 = BOM.IDA3A2STATE                                   																	");
	    sql.append("	           AND PL.IDA3B5 = PH.IDA2A2                                     																	");
	    sql.append("	           AND PH.PHASESTATE = BOM.STATUS																					");
	    sql.append("	           AND BOM.ITEMINFO = HD.HITEMKEY(+) 																					");
	    sql.append("UNION																											");
	    sql.append("SELECT BOM.*,  PH.NAME	STATUSKR , NVL(HD.HITEMKEY,'') AS HEADERKEY,  NVL(HD.ECOHEADERNUMBER,'') AS EONO, NVL(HD.COWORKERID,'') AS COUTERID, NVL(HD.COWORKERNAME,'') AS COUTER 						");
	    sql.append(" FROM																											");
	    sql.append("       (																										");
	    sql.append("            SELECT ROWNUM AS NUM, LEVEL AS LVL,	 X0.ITEMSEQ,																				");
	    sql.append("                   X0.VERSIONITEMCODE AS ASSEMBLY_ITEM_OID, 																				");
	    sql.append("                   X0.CHILDITEMCODE AS ASSEMBLY_ITEM_CODE,																				");
	    sql.append("                   X0.CHILDITEMVERSION AS ASSEMBLY_ITEM_REV,																				");
	    sql.append("                   X0.IDA3A5 AS COMPONENT_ITEM_OID, 																					");
	    sql.append("                   X0.PARENTITEMCODE AS COMPONENT_ITEM_CODE,																				");
	    sql.append("                  (SELECT NAME FROM WTPARTMASTER WHERE IDA2A2 = (SELECT IDA3MASTERREFERENCE FROM WTPART WHERE IDA2A2=X0.IDA3A5)) AS COMPONENT_ITEM_NAME, 								");
	    sql.append("                   (SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5) AS COMPONENT_ITEM_REV,														");
	    sql.append("                   X0.QUANTITY AS QTY,  (SELECT STATESTATE FROM WTPART WHERE IDA2A2=X0.VERSIONITEMCODE) AS STATUS,	(SELECT IDA3A2STATE FROM WTPART WHERE IDA2A2=X0.VERSIONITEMCODE) AS IDA3A2STATE,		");
	    sql.append("                   X0.UNIT, X0.IDA2A2, X0.STARTDATE, X0.ENDDATE,  X0.MATERIAL, X0.HARDNESSFROM, X0.HARDNESSTO, X0.DESIGNDATE, X0.ICT AS ICT, X0.REFTOP AS REFTOP, X0.REFBOTTOM AS REFBOTTOM,		");
	    sql.append("                   X0.IDA2A2 AS USAGELINKOID, 																						");
	    sql.append("                  (X0.CHILDITEMCODE || '_' || X0.CHILDITEMVERSION) AS ITEMINFO, (X0.PARENTITEMCODE || '_' || (SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5)) AS PARENTITEMINFO, X0.ATTRIBUTE10 AS ECONO			");
	    sql.append("                    FROM KETPARTUSAGELINK X0																						");
	    sql.append("                    START WITH X0.VERSIONITEMCODE=?																					");
	    sql.append("                    CONNECT BY PRIOR X0.IDA3A5 = X0.VERSIONITEMCODE	   																		");
	    sql.append("                    ORDER SIBLINGS BY  X0.PARENTITEMCODE																				");
	    sql.append("       ) BOM , PHASETEMPLATE PH  ,PHASELINK PL, 																					");
	    sql.append("	   (SELECT KEB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KEB.ECOITEMCODE||'_'||KEB.BOMVERSION) AS HITEMKEY, KEC.COWORKERID, KEC.COWORKERNAME, KEB.STATESTATE AS STATUS 					");
	    sql.append("	FROM KETBOMECOHEADER KEB, KETBOMECOCOWORKER KEC WHERE KEB.ECOHEADERNUMBER=KEC.ECOHEADERNUMBER AND KEB.ECOITEMCODE=KEC.ECOITEMCODE  AND KEB.STATESTATE='APPROVED'									");
	    sql.append("       UNION																									");
	    // sql.append("      SELECT KB.BOMTEXT AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 								");
	    sql.append("      SELECT KB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 								");
	    // sql.append("	FROM KETBOMHEADER KB, KETBOMCOWORKER KC 																				");
	    // sql.append("       WHERE  KB.NEWBOMCODE=KC.NEWBOMCODE) HD																					");
	    sql.append("	FROM KETBOMHEADER KB, KETBOMECOCOWORKER KC 																				");
	    sql.append("       WHERE  KB.NEWBOMCODE=KC.ECOITEMCODE  AND KB.ECOHEADERNUMBER=KC.ECOHEADERNUMBER  AND KB.STATESTATE='APPROVED') HD																					");
	    sql.append("  WHERE  PL.IDA3A5 = BOM.IDA3A2STATE                                   																			");
	    sql.append("	 AND PL.IDA3B5 = PH.IDA2A2                                     																			");
	    sql.append("	 AND PH.PHASESTATE = BOM.STATUS																							");
	    sql.append("   AND BOM.PARENTITEMINFO = HD.HITEMKEY(+) 																						");
	    if (!(level.equals("") || level.equals("All")))
		sql.append("   AND BOM.LVL <= ?																									");

	    sql.append(" )																											");
	    sql.append("  ORDER BY NUM 																										");

	    Kogger.debug(getClass(), sql.toString());

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, partOid);
	    pstmt.setString(2, partOid);
	    if (!(level.equals("") || level.equals("All")))
		pstmt.setInt(3, Integer.parseInt(level));

	    rs = pstmt.executeQuery();

	    String partNo = "";
	    String rev = "";
	    String newrev = "";
	    String partName = "";
	    String lvl = "";
	    String seq = "";
	    String qty = "";
	    String unit = "";
	    String econo = "";
	    String econo2 = "";
	    String checkout = "";
	    String checkoutId = "";
	    String ict = "";
	    String reftop = "";
	    String refbtm = "";
	    String material = "";
	    String hardnessFrom = "";
	    String hardnessTo = "";
	    String designDate = "";
	    String state = "";
	    String parentNo = "";
	    String pver = "";

	    String bom_path = "";
	    int i = 0;

	    int old_lvl = 0;

	    while (rs.next()) {

		Kogger.debug(getClass(), "getReverseBOM");
		Map<String, Object> data = new Hashtable();

		partNo = rs.getString("COMPONENT_ITEM_CODE") == null ? "" : rs.getString("COMPONENT_ITEM_CODE").trim();
		rev = rs.getString("COMPONENT_ITEM_REV") == null ? "" : rs.getString("COMPONENT_ITEM_REV").trim();
		newrev = getNewVersionCode(partNo, rev);
		partName = rs.getString("COMPONENT_ITEM_NAME") == null ? "" : rs.getString("COMPONENT_ITEM_NAME").trim();
		lvl = rs.getString("LVL") == null ? "" : rs.getString("LVL").trim();
		int new_lvl = Integer.parseInt(lvl);
		seq = rs.getString("ITEMSEQ") == null ? "" : rs.getString("ITEMSEQ").trim();
		qty = rs.getString("QTY") == null ? "" : rs.getString("QTY").trim();
		unit = rs.getString("UNIT") == null ? "" : rs.getString("UNIT").trim();

		if (unit.indexOf("KET_%") != -1)
		    unit = "KET_PERCENT";

		econo = rs.getString("EONO") == null ? "" : rs.getString("EONO").trim();
		econo2 = rs.getString("ECONO") == null ? "" : rs.getString("ECONO").trim();
		checkout = rs.getString("COUTER") == null ? "" : rs.getString("COUTER").trim();
		checkoutId = rs.getString("COUTERID") == null ? "" : rs.getString("COUTERID").trim();
		ict = rs.getString("ICT") == null ? "" : rs.getString("ICT").trim();
		reftop = rs.getString("REFTOP") == null ? "" : rs.getString("REFTOP").trim();
		refbtm = rs.getString("REFBOTTOM") == null ? "" : rs.getString("REFBOTTOM").trim();
		// material = rs.getString("MATERIAL") == null ? "" : rs.getString("MATERIAL").trim();
		if ((partType.equals("D") || partType.equals("M")))
		    material = getMaterial(partNo, rev);
		hardnessFrom = rs.getString("HARDNESSFROM") == null ? "" : rs.getString("HARDNESSFROM").trim();
		hardnessTo = rs.getString("HARDNESSTO") == null ? "" : rs.getString("HARDNESSTO").trim();
		designDate = rs.getString("DESIGNDATE") == null ? "" : rs.getString("DESIGNDATE").trim();
		state = rs.getString("STATUSKR") == null ? "" : rs.getString("STATUSKR").trim();
		parentNo = rs.getString("ASSEMBLY_ITEM_CODE") == null ? "" : rs.getString("ASSEMBLY_ITEM_CODE").trim();
		pver = rs.getString("ASSEMBLY_ITEM_REV") == null ? "" : rs.getString("ASSEMBLY_ITEM_REV").trim();

		if (new_lvl > old_lvl) {
		    bom_path += lvl + "^" + parentNo + "|";
		} else if (new_lvl < old_lvl) {
		    bom_path = bom_path.substring(0, bom_path.indexOf(lvl + "^" + parentNo + "|")) + lvl + "^" + parentNo + "|";
		}

		data.put("partNo", partNo);
		data.put("rev", rev);
		data.put("newrev", newrev);
		data.put("partName", partName);
		data.put("ict", ict);
		data.put("lvl", lvl);
		data.put("seq", seq);
		data.put("qty", qty);
		data.put("unit", unit);
		if (!econo.equals("")) {
		    data.put("econo", econo);
		} else {
		    data.put("econo", econo2);
		}
		data.put("checkout", checkout);
		data.put("checkoutId", checkoutId);
		data.put("reftop", reftop);
		data.put("refbtm", refbtm);
		data.put("material", material);
		data.put("hardnessFrom", hardnessFrom);
		data.put("hardnessTo", hardnessTo);
		data.put("designDate", designDate);
		data.put("state", state);
		data.put("parentNo", parentNo);
		data.put("pver", pver);

		data.put("bom_path", bom_path);

		String checkkey = parentNo + "_" + pver + "$" + partNo + "_" + rev;

		if (!keyHash.containsKey(checkkey)) {
		    bomList.add(data);

		}

		keyHash.put(checkkey, checkkey);

	    }

	    if (dataType.equals("TreeGrid")) {

		treeList = getGridBOM(bomList);
	    } else {
		treeList = bomList;
	    }

	    // Kogger.debug(getClass(), treeList.toString());
	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return treeList;
    }

    public List<Map<String, Object>> getReverseLatestBOM(Hashtable params) throws Exception {
	List<Map<String, Object>> bomList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	String partOid = "";
	String dataType = "";
	String level = "";
	Hashtable keyHash = new Hashtable();

	KETBomPartUtil pUtil = new KETBomPartUtil();

	try {

	    partOid = (String) params.get("partOid");

	    String partNumber = "";

	    WTPart part = pUtil.getPart("wt.part.WTPart:" + partOid);
	    String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);

	    partNumber = part.getNumber();

	    level = (String) params.get("level");
	    dataType = (String) params.get("dataType");

	    Kogger.debug(getClass(), "partOid==>" + partOid);
	    // Kogger.debug(getClass(), "level==>" + level);

	    sql.append("SELECT * FROM ( 																									");
	    sql.append("          SELECT BOM.*,  																								");
	    sql.append("	        PH.NAME	STATUSKR , NVL(HD.HITEMKEY,'') AS HEADERKEY,  NVL(HD.ECOHEADERNUMBER,'') AS EONO, NVL(HD.COWORKERID,'') AS COUTERID, NVL(HD.COWORKERNAME,'') AS COUTER						");
	    sql.append("	        FROM 																									");
	    sql.append("	        (																									");
	    sql.append("	        SELECT 0 AS NUM, 																							");
	    sql.append("	                        0 AS LVL,	 																					");
	    sql.append("	                  10 AS ITEMSEQ,																						");
	    sql.append("	                        '' AS ASSEMBLY_ITEM_OID, 																				");
	    sql.append("	                        '' AS ASSEMBLY_ITEM_CODE,																				");
	    sql.append("	                        '' AS ASSEMBLY_ITEM_REV,																				");
	    sql.append("	                         A0.IDA2A2 AS COMPONENT_ITEM_OID, 																			");
	    sql.append("	                         A0.WTPARTNUMBER AS COMPONENT_ITEM_CODE,																		");
	    sql.append("	                         A0.NAME AS COMPONENT_ITEM_NAME, 																			");
	    sql.append("	                         (B0.VERSIONIDA2VERSIONINFO || '.' || B0.ITERATIONIDA2ITERATIONINFO) AS COMPONENT_ITEM_REV,												");
	    sql.append("	                         '1.000' AS QTY,  B0.STATESTATE AS STATUS,	B0.IDA3A2STATE,																");
	    sql.append("	                         'KET_EA' AS UNIT, null AS IDA2A2, '' AS STARTDATE, '' AS ENDDATE,  '' AS MATERIAL, '' AS HARDNESSFROM, '' AS HARDNESSTO, '' AS DESIGNDATE, '' AS ICT, '' AS REFTOP, '' AS REFBOTTOM,	");
	    sql.append("	                         null AS USAGELINKOID, 																					");
	    sql.append("	                        (A0.WTPARTNUMBER || '_' ||B0.VERSIONIDA2VERSIONINFO) AS ITEMINFO, '' AS PARENTITEMINFO,  '' AS ECONO													");
	    sql.append("	        FROM WTPARTMASTER A0, WTPART B0    																					");
	    sql.append("	        WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE 																	");
	    sql.append("	         AND B0.IDA2A2=?																							");
	    sql.append("	        ) BOM, PHASETEMPLATE PH  ,PHASELINK PL, 																				");
	    sql.append("	   (SELECT KEB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KEB.ECOITEMCODE||'_'||KEB.BOMVERSION) AS HITEMKEY, KEC.COWORKERID, KEC.COWORKERNAME, KEB.STATESTATE AS STATUS 					");
	    sql.append("	FROM KETBOMECOHEADER KEB, KETBOMECOCOWORKER KEC WHERE KEB.ECOHEADERNUMBER=KEC.ECOHEADERNUMBER AND KEB.ECOITEMCODE=KEC.ECOITEMCODE  AND KEB.STATESTATE='APPROVED'									");
	    sql.append("       UNION																									");
	    // sql.append("      SELECT KB.BOMTEXT AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 								");
	    sql.append("      SELECT KB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 								");
	    // sql.append("	FROM KETBOMHEADER KB, KETBOMCOWORKER KC 																				");
	    // sql.append("       WHERE  KB.NEWBOMCODE=KC.NEWBOMCODE) HD																					");
	    sql.append("	FROM KETBOMHEADER KB, KETBOMECOCOWORKER KC 																				");
	    sql.append("       WHERE  KB.NEWBOMCODE=KC.ECOITEMCODE  AND KB.ECOHEADERNUMBER=KC.ECOHEADERNUMBER  AND KB.STATESTATE='APPROVED') HD																					");
	    sql.append("	          WHERE  PL.IDA3A5 = BOM.IDA3A2STATE                                   																	");
	    sql.append("	           AND PL.IDA3B5 = PH.IDA2A2                                     																	");
	    sql.append("	           AND PH.PHASESTATE = BOM.STATUS																					");
	    sql.append("	           AND BOM.ITEMINFO = HD.HITEMKEY(+) 																					");
	    sql.append("UNION																											");
	    sql.append("SELECT BOM.*,  PH.NAME	STATUSKR , NVL(HD.HITEMKEY,'') AS HEADERKEY,  NVL(HD.ECOHEADERNUMBER,'') AS EONO, NVL(HD.COWORKERID,'') AS COUTERID, NVL(HD.COWORKERNAME,'') AS COUTER 						");
	    sql.append(" FROM																											");
	    sql.append("       (																										");
	    sql.append("            SELECT ROWNUM AS NUM, LEVEL AS LVL,	 X0.ITEMSEQ,																				");
	    sql.append("                   X0.VERSIONITEMCODE AS ASSEMBLY_ITEM_OID, 																				");
	    sql.append("                   X0.CHILDITEMCODE AS ASSEMBLY_ITEM_CODE,																				");
	    sql.append("                   X0.CHILDITEMVERSION AS ASSEMBLY_ITEM_REV,																				");
	    sql.append("                   X0.IDA3A5 AS COMPONENT_ITEM_OID, 																					");
	    sql.append("                   X0.PARENTITEMCODE AS COMPONENT_ITEM_CODE,																				");
	    sql.append("                  (SELECT NAME FROM WTPARTMASTER WHERE IDA2A2 = (SELECT IDA3MASTERREFERENCE FROM WTPART WHERE IDA2A2=X0.IDA3A5)) AS COMPONENT_ITEM_NAME, 								");
	    sql.append("                   (SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5) AS COMPONENT_ITEM_REV,														");
	    sql.append("                   X0.QUANTITY AS QTY,  (SELECT STATESTATE FROM WTPART WHERE IDA2A2=X0.VERSIONITEMCODE) AS STATUS,	(SELECT IDA3A2STATE FROM WTPART WHERE IDA2A2=X0.VERSIONITEMCODE) AS IDA3A2STATE,		");
	    sql.append("                   X0.UNIT, X0.IDA2A2, X0.STARTDATE, X0.ENDDATE,  X0.MATERIAL, X0.HARDNESSFROM, X0.HARDNESSTO, X0.DESIGNDATE, X0.ICT AS ICT, X0.REFTOP AS REFTOP, X0.REFBOTTOM AS REFBOTTOM,		");
	    sql.append("                   X0.IDA2A2 AS USAGELINKOID, 																						");
	    sql.append("                  (X0.CHILDITEMCODE || '_' || X0.CHILDITEMVERSION) AS ITEMINFO, (X0.PARENTITEMCODE || '_' || (SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5)) AS PARENTITEMINFO, X0.ATTRIBUTE10 AS ECONO			");

	    sql.append("		      FROM KETPARTUSAGELINK X0																						 \n");
	    sql.append("    		      START WITH X0.VERSIONITEMCODE IN (SELECT MAX(VERSIONITEMCODE) FROM KETPARTUSAGELINK WHERE CHILDITEMCODE=? GROUP BY PARENTITEMCODE)																					 \n");
	    sql.append("    		      CONNECT BY PRIOR X0.IDA3A5 = X0.VERSIONITEMCODE	   																	 \n");
	    sql.append("    		      ORDER SIBLINGS BY  X0.PARENTITEMCODE	 \n");

	    // sql.append("                    FROM KETPARTUSAGELINK X0																						");
	    // sql.append("                    START WITH X0.VERSIONITEMCODE=?																					");
	    // sql.append("                    CONNECT BY PRIOR X0.IDA3A5 = X0.VERSIONITEMCODE	   																		");
	    // sql.append("                    ORDER SIBLINGS BY  X0.PARENTITEMCODE																				");
	    sql.append("       ) BOM , PHASETEMPLATE PH  ,PHASELINK PL, 																					");
	    sql.append("	   (SELECT KEB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KEB.ECOITEMCODE||'_'||KEB.BOMVERSION) AS HITEMKEY, KEC.COWORKERID, KEC.COWORKERNAME, KEB.STATESTATE AS STATUS 					");
	    sql.append("	FROM KETBOMECOHEADER KEB, KETBOMECOCOWORKER KEC WHERE KEB.ECOHEADERNUMBER=KEC.ECOHEADERNUMBER AND KEB.ECOITEMCODE=KEC.ECOITEMCODE  AND KEB.STATESTATE='APPROVED'									");
	    sql.append("       UNION																									");
	    // sql.append("      SELECT KB.BOMTEXT AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 								");
	    sql.append("      SELECT KB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS 								");
	    // sql.append("	FROM KETBOMHEADER KB, KETBOMCOWORKER KC 																				");
	    // sql.append("       WHERE  KB.NEWBOMCODE=KC.NEWBOMCODE) HD																					");
	    sql.append("	FROM KETBOMHEADER KB, KETBOMECOCOWORKER KC 																				");
	    sql.append("       WHERE  KB.NEWBOMCODE=KC.ECOITEMCODE  AND KB.ECOHEADERNUMBER=KC.ECOHEADERNUMBER  AND KB.STATESTATE='APPROVED') HD																					");
	    sql.append("  WHERE  PL.IDA3A5 = BOM.IDA3A2STATE                                   																			");
	    sql.append("	 AND PL.IDA3B5 = PH.IDA2A2                                     																			");
	    sql.append("	 AND PH.PHASESTATE = BOM.STATUS																							");
	    sql.append("   AND BOM.PARENTITEMINFO = HD.HITEMKEY(+) 																						");
	    if (!(level.equals("") || level.equals("All")))
		sql.append("   AND BOM.LVL <= ?																									");

	    sql.append(" )																											");
	    sql.append("  ORDER BY NUM 																										");

	    Kogger.debug(getClass(), sql.toString());

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, partOid);
	    pstmt.setString(2, partNumber);
	    if (!(level.equals("") || level.equals("All")))
		pstmt.setInt(3, Integer.parseInt(level));

	    rs = pstmt.executeQuery();

	    String partNo = "";
	    String rev = "";
	    String newrev = "";
	    String partName = "";
	    String lvl = "";
	    String seq = "";
	    String qty = "";
	    String unit = "";
	    String econo = "";
	    String econo2 = "";
	    String checkout = "";
	    String checkoutId = "";
	    String ict = "";
	    String reftop = "";
	    String refbtm = "";
	    String material = "";
	    String hardnessFrom = "";
	    String hardnessTo = "";
	    String designDate = "";
	    String state = "";
	    String parentNo = "";
	    String pver = "";

	    String bom_path = "";
	    int i = 0;

	    int old_lvl = 0;

	    while (rs.next()) {

		Kogger.debug(getClass(), "getReverseLatestBOM");
		Map<String, Object> data = new Hashtable();

		partNo = rs.getString("COMPONENT_ITEM_CODE") == null ? "" : rs.getString("COMPONENT_ITEM_CODE").trim();
		rev = rs.getString("COMPONENT_ITEM_REV") == null ? "" : rs.getString("COMPONENT_ITEM_REV").trim();
		newrev = getNewVersionCode(partNo, rev);
		partName = rs.getString("COMPONENT_ITEM_NAME") == null ? "" : rs.getString("COMPONENT_ITEM_NAME").trim();
		lvl = rs.getString("LVL") == null ? "" : rs.getString("LVL").trim();
		int new_lvl = Integer.parseInt(lvl);
		seq = rs.getString("ITEMSEQ") == null ? "" : rs.getString("ITEMSEQ").trim();
		qty = rs.getString("QTY") == null ? "" : rs.getString("QTY").trim();
		unit = rs.getString("UNIT") == null ? "" : rs.getString("UNIT").trim();

		if (unit.indexOf("KET_%") != -1)
		    unit = "KET_PERCENT";

		econo = rs.getString("EONO") == null ? "" : rs.getString("EONO").trim();
		econo2 = rs.getString("ECONO") == null ? "" : rs.getString("ECONO").trim();
		checkout = rs.getString("COUTER") == null ? "" : rs.getString("COUTER").trim();
		checkoutId = rs.getString("COUTERID") == null ? "" : rs.getString("COUTERID").trim();
		ict = rs.getString("ICT") == null ? "" : rs.getString("ICT").trim();
		reftop = rs.getString("REFTOP") == null ? "" : rs.getString("REFTOP").trim();
		refbtm = rs.getString("REFBOTTOM") == null ? "" : rs.getString("REFBOTTOM").trim();
		// material = rs.getString("MATERIAL") == null ? "" : rs.getString("MATERIAL").trim();
		if ((partType.equals("D") || partType.equals("M")))
		    material = getMaterial(partNo, rev);
		hardnessFrom = rs.getString("HARDNESSFROM") == null ? "" : rs.getString("HARDNESSFROM").trim();
		hardnessTo = rs.getString("HARDNESSTO") == null ? "" : rs.getString("HARDNESSTO").trim();
		designDate = rs.getString("DESIGNDATE") == null ? "" : rs.getString("DESIGNDATE").trim();
		state = rs.getString("STATUSKR") == null ? "" : rs.getString("STATUSKR").trim();
		parentNo = rs.getString("ASSEMBLY_ITEM_CODE") == null ? "" : rs.getString("ASSEMBLY_ITEM_CODE").trim();
		pver = rs.getString("ASSEMBLY_ITEM_REV") == null ? "" : rs.getString("ASSEMBLY_ITEM_REV").trim();

		if (new_lvl > old_lvl) {
		    bom_path += lvl + "^" + parentNo + "|";
		} else if (new_lvl < old_lvl) {
		    bom_path = bom_path.substring(0, bom_path.indexOf(lvl + "^" + parentNo + "|")) + lvl + "^" + parentNo + "|";
		}

		data.put("partNo", partNo);
		data.put("rev", rev);
		data.put("newrev", newrev);
		data.put("partName", partName);
		data.put("ict", ict);
		data.put("lvl", lvl);
		data.put("seq", seq);
		data.put("qty", qty);
		data.put("unit", unit);
		if (!econo.equals("")) {
		    data.put("econo", econo);
		} else {
		    data.put("econo", econo2);
		}
		data.put("checkout", checkout);
		data.put("checkoutId", checkoutId);
		data.put("reftop", reftop);
		data.put("refbtm", refbtm);
		data.put("material", material);
		data.put("hardnessFrom", hardnessFrom);
		data.put("hardnessTo", hardnessTo);
		data.put("designDate", designDate);
		data.put("state", state);
		data.put("parentNo", parentNo);
		data.put("pver", pver);

		data.put("bom_path", bom_path);

		String checkkey = parentNo + "_" + pver + "$" + partNo + "_" + rev;

		if (!keyHash.containsKey(checkkey)) {
		    bomList.add(data);

		}

		keyHash.put(checkkey, checkkey);

	    }

	    if (dataType.equals("TreeGrid")) {

		treeList = getGridBOM(bomList);
	    } else {
		treeList = bomList;
	    }

	    // Kogger.debug(getClass(), treeList.toString());
	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return treeList;
    }

    /**********************************************************
     * USAGELINK VERSION 별 BOM (BOM 비교용)---1
     * 
     * @throws Exception
     **********************************************************/
    public Hashtable getVersionBOM(String partNumber, String ver, String topChangeNo, String topChangeRev) throws Exception {

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer stringBuffer = new StringBuffer();
	StringBuffer sql = new StringBuffer();

	KETBomPartUtil pUtil = new KETBomPartUtil();

	LinkedList<Hashtable> keylist = new LinkedList<Hashtable>();
	LinkedList<Hashtable> list = new LinkedList<Hashtable>();
	Hashtable result = new Hashtable();

	String partOid = "";

	try {

	    // 최신 모부품 OID를 USAGELINK에서 가져온다.
	    if (ver.indexOf(".") != -1) {
		ver = ver.substring(0, ver.indexOf("."));
	    }

	    Kogger.debug(getClass(), "partNumber==>" + partNumber);
	    Kogger.debug(getClass(), "ver==>" + ver);

	    // partOid = getUsageLinkParentPartOid(partNumber, ver);
	    partOid = getPartOid(partNumber, ver);

	    String partNo = "";
	    String rev = "";
	    String partName = "";
	    String lvl = "";
	    String qty = "";
	    String reftop = "";
	    String refbtm = "";
	    String hardnessFrom = "";
	    String hardnessTo = "";
	    String parentNo = "";
	    String pver = "";

	    String ict = "";
	    String designDate = "";
	    String unit = "";
	    String material = "";

	    String bom_path = "";
	    String real_parentNo = "";
	    String real_parentRev = "";

	    int old_lvl = 0;
	    int new_lvl = 0;

	    Hashtable bomHash = new Hashtable();

	    if (partOid.equals("")) {

	    } else

	    {
		Kogger.debug(getClass(), "partOid==>" + partOid);
		WTPart part = pUtil.getPart(partOid);
		String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);

		long id = pUtil.getPartLongId(part);
		String partLongValue = Long.toString(id);

		sql.append("SELECT LEVEL AS LVL,																	");
		sql.append("      X0.IDA3A5 AS ASSEMBLY_ITEM_OID, X0.PARENTITEMCODE AS ASSEMBLY_ITEM_CODE,										");
		sql.append("      (SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5) AS ASSEMBLY_ITEM_REV,      							");
		sql.append("      X0.CHILDITEMCODE AS COMPONENT_ITEM_CODE,														");
		sql.append("      (SELECT NAME FROM WTPARTMASTER WHERE IDA2A2 = (SELECT IDA3MASTERREFERENCE FROM WTPART WHERE IDA2A2=X0.VERSIONITEMCODE)) AS COMPONENT_ITEM_NAME, 	");
		sql.append("      X0.BOMVERSION AS COMPONENT_ITEM_REV, X0.UNIT, X0.MATERIAL,											");
		sql.append("      X0.QUANTITY AS QTY, X0.HARDNESSFROM, X0.HARDNESSTO, X0.REFTOP AS REFTOP, X0.REFBOTTOM AS REFBOTTOM, X0.ICT AS ICT, X0.DESIGNDATE	");
		sql.append("FROM KETPARTUSAGELINK X0 																");

		sql.append("			START WITH X0.IDA3A5 = ?                                    																\n");
		sql.append("			      CONNECT BY PRIOR X0.VERSIONITEMCODE = X0.IDA3A5                              																\n");

		// 제품
		if (!(partType.equals("D") || partType.equals("M")))
		    sql.append("			      ORDER SIBLINGS BY  X0.ITEMSEQ                              															\n");
		// 금형
		else
		    sql.append("                              ORDER SIBLINGS BY  X0.CHILDITEMCODE																		\n");

		Kogger.debug(getClass(), "partOid===> " + partOid);
		Kogger.debug(getClass(), "SQL===> " + sql.toString());

		pstmt = conn.prepareStatement(sql.toString());
		pstmt.setString(1, partLongValue);
		// pstmt.setString(2, partOid);

		rs = pstmt.executeQuery();

		while (rs.next()) {

		    Hashtable key = new Hashtable();
		    Hashtable data = new Hashtable();

		    partNo = rs.getString("COMPONENT_ITEM_CODE") == null ? "" : rs.getString("COMPONENT_ITEM_CODE").trim();
		    rev = rs.getString("COMPONENT_ITEM_REV") == null ? "" : rs.getString("COMPONENT_ITEM_REV").trim();
		    partName = rs.getString("COMPONENT_ITEM_NAME") == null ? "" : rs.getString("COMPONENT_ITEM_NAME").trim();
		    lvl = rs.getString("LVL") == null ? "" : rs.getString("LVL").trim();
		    if (lvl != "0")
			new_lvl = Integer.parseInt(lvl);
		    qty = rs.getString("QTY") == null ? "" : rs.getString("QTY").trim();
		    reftop = rs.getString("REFTOP") == null ? "" : rs.getString("REFTOP").trim();
		    refbtm = rs.getString("REFBOTTOM") == null ? "" : rs.getString("REFBOTTOM").trim();
		    hardnessFrom = rs.getString("HARDNESSFROM") == null ? "" : rs.getString("HARDNESSFROM").trim();
		    hardnessTo = rs.getString("HARDNESSTO") == null ? "" : rs.getString("HARDNESSTO").trim();
		    parentNo = rs.getString("ASSEMBLY_ITEM_CODE") == null ? "" : rs.getString("ASSEMBLY_ITEM_CODE").trim();
		    pver = rs.getString("ASSEMBLY_ITEM_REV") == null ? "" : rs.getString("ASSEMBLY_ITEM_REV").trim();

		    ict = rs.getString("ICT") == null ? "" : rs.getString("ICT").trim();
		    designDate = rs.getString("DESIGNDATE") == null ? "" : rs.getString("DESIGNDATE").trim();

		    unit = rs.getString("UNIT") == null ? "" : rs.getString("UNIT").trim();

		    if (unit.indexOf("KET_%") != -1)
			unit = "KET_PERCENT";

		    material = rs.getString("MATERIAL") == null ? "" : rs.getString("MATERIAL").trim();

		    if (lvl.equals("0")) {
			qty = getBoxQty(partNo, rev);
		    }

		    real_parentNo = parentNo;
		    real_parentRev = pver;

		    if (new_lvl > old_lvl) {
			bom_path += lvl + "^" + parentNo + "|";
		    } else {
			if (new_lvl < old_lvl)
			    bom_path = bom_path.substring(0, bom_path.indexOf(lvl + "^" + parentNo + "|")) + lvl + "^" + parentNo + "|";
		    }

		    if (topChangeNo != null && !topChangeNo.equals("") && new_lvl == 1) {
			parentNo = topChangeNo;
			pver = topChangeRev;
		    }

		    key.put("lvl", lvl);
		    key.put("parentNo", parentNo);
		    key.put("child", partNo);
		    key.put("bom_path", bom_path);

		    keylist.add(key);

		    data.put("ict", ict);
		    data.put("partNo", partNo);
		    data.put("rev", rev);
		    data.put("partName", partName);
		    data.put("lvl", lvl);
		    data.put("qty", qty);
		    data.put("reftop", reftop);
		    data.put("refbtm", refbtm);
		    data.put("hardnessFrom", hardnessFrom);
		    data.put("hardnessTo", hardnessTo);
		    data.put("parentNo", parentNo);
		    data.put("pver", pver);
		    data.put("real_parentNo", real_parentNo);
		    data.put("real_parentRev", real_parentRev);

		    data.put("bom_path", bom_path);

		    data.put("ict", ict);
		    data.put("designDate", designDate);
		    data.put("unit", unit);
		    data.put("material", material);

		    bomHash.put(lvl + "|" + parentNo + "|" + partNo, data);

		    list.add(data);

		    old_lvl = new_lvl;
		}
	    }
	    result.put("keylist", keylist);
	    result.put("list", list);
	    result.put("bomHash", bomHash);

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return result;
    }

    /**********************************************************
     * BOM 비교하는 함수
     * 
     * @throws Exception
     **********************************************************/

    public List<Map<String, Object>> compareBOM(String srcNum, String srcRev, String desNum, String desRev) throws Exception {

	// 전체 BOM Tree 정보를 담는 Collection 정의
	List<Map<String, Object>> totalList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> totalTreeList = new ArrayList<Map<String, Object>>();
	Hashtable totalKeyHash = new Hashtable();

	String srcName = "";
	String desName = "";
	try {
	    srcName = getPartName(srcNum, srcRev);
	    desName = getPartName(desNum, desRev);

	    // root data
	    Hashtable rootdata = new Hashtable();

	    rootdata.put("parentNo", "");
	    rootdata.put("partNo", srcNum + "|0");
	    rootdata.put("lvl", "0");

	    rootdata.put("srcpartNo", srcNum);
	    rootdata.put("srcrev", srcRev);

	    String newsrcrev = getNewVersionCode(srcNum, srcRev);
	    rootdata.put("newsrcrev", newsrcrev);

	    rootdata.put("srcpartName", srcName);
	    rootdata.put("srclvl", "0");
	    rootdata.put("srcqty", "1");
	    rootdata.put("srcreftop", "");
	    rootdata.put("srcrefbtm", "");
	    rootdata.put("srchardnessFrom", "");
	    rootdata.put("srchardnessTo", "");
	    rootdata.put("srcparentNo", "");
	    rootdata.put("srcpver", "");
	    rootdata.put("srcbom_path", "");

	    rootdata.put("srcict", "");
	    rootdata.put("srcdesignDate", "");
	    rootdata.put("srcunit", "KET_EA");
	    rootdata.put("srcmaterial", "");

	    rootdata.put("despartNo", desNum);
	    rootdata.put("desrev", desRev);

	    String newdesrev = getNewVersionCode(desNum, desRev);
	    rootdata.put("newdesrev", newdesrev);

	    rootdata.put("despartName", desName);
	    rootdata.put("deslvl", "0");
	    rootdata.put("desqty", "1");
	    rootdata.put("desreftop", "");
	    rootdata.put("desrefbtm", "");
	    rootdata.put("deshardnessFrom", "");
	    rootdata.put("deshardnessTo", "");
	    rootdata.put("desparentNo", "");
	    rootdata.put("despver", "");
	    rootdata.put("desbom_path", "");

	    rootdata.put("desict", "");
	    rootdata.put("desdesignDate", "");
	    rootdata.put("desunit", "KET_EA");
	    rootdata.put("desmaterial", "");

	    totalList.add(rootdata);

	    // 비교할 BOM 소스
	    Hashtable srcHash = getVersionBOM(srcNum, srcRev, srcNum, srcRev);
	    LinkedList srckeylist = (LinkedList) srcHash.get("keylist");
	    LinkedList srclist = (LinkedList) srcHash.get("list");
	    Hashtable srcbomHash = (Hashtable) srcHash.get("bomHash");

	    Enumeration srckeys = srcbomHash.keys();

	    while (srckeys.hasMoreElements()) {
		String srckey = (String) srckeys.nextElement();
		Kogger.debug(getClass(), "srckey==========>" + srckey);

		totalKeyHash.put(srckey, srckey);
	    }

	    // 비교할 BOM 대상
	    Hashtable desHash = getVersionBOM(desNum, desRev, srcNum, srcRev);
	    LinkedList deskeylist = (LinkedList) desHash.get("keylist");
	    LinkedList deslist = (LinkedList) desHash.get("list");
	    Hashtable desbomHash = (Hashtable) desHash.get("bomHash");

	    Enumeration deskeys = desbomHash.keys();
	    while (deskeys.hasMoreElements()) {
		String deskey = (String) deskeys.nextElement();
		totalKeyHash.put(deskey, deskey);
	    }

	    KETComparator comparator = new KETComparator();
	    Vector vecKey = new Vector();

	    Set set = totalKeyHash.keySet();
	    Object[] keys = set.toArray();

	    Arrays.sort(keys, comparator);
	    for (int rowIndex = 0; rowIndex < keys.length; rowIndex++) {
		String el = (String) keys[rowIndex];
		Kogger.debug(getClass(), el + ":" + totalKeyHash.get(el));
		vecKey.add(el);
	    }

	    // Enumeration totkeys = totalKeyHash.keys();

	    // while (totkeys.hasMoreElements()) {
	    // String totkey = (String) totkeys.nextElement();
	    for (int y = 0; y < vecKey.size(); y++) {

		String totkey = (String) vecKey.get(y);

		Kogger.debug(getClass(), "####################################################################################");

		String srcpartNo = "";
		String srcrev = "";
		String srcpartName = "";
		String srclvl = "";
		String srcqty = "";
		String srcreftop = "";
		String srcrefbtm = "";
		String srchardnessFrom = "";
		String srchardnessTo = "";
		String srcparentNo = "";
		String srcpver = "";
		String srcbom_path = "";
		String srcreal_parentNo = "";
		String srcreal_parentRev = "";
		String srcict = "";
		String srcdesignDate = "";
		String srcunit = "";
		String srcmaterial = "";

		String despartNo = "";
		String desrev = "";
		String despartName = "";
		String deslvl = "";
		String desqty = "";
		String desreftop = "";
		String desrefbtm = "";
		String deshardnessFrom = "";
		String deshardnessTo = "";
		String desparentNo = "";
		String despver = "";
		String desbom_path = "";
		String desreal_parentNo = "";
		String desreal_parentRev = "";
		String desict = "";
		String desdesignDate = "";
		String desunit = "";
		String desmaterial = "";

		String gubun = "";
		Kogger.debug(getClass(), "totkey=====>" + totkey);

		if (srcbomHash.containsKey(totkey)) {

		    Kogger.debug(getClass(), "srckey==>" + totkey);
		    Hashtable srcdata = (Hashtable) srcbomHash.get(totkey);

		    srcpartNo = (srcdata.get("partNo") == null) ? "" : (String) srcdata.get("partNo");
		    srcrev = (srcdata.get("rev") == null) ? "" : (String) srcdata.get("rev");
		    newsrcrev = getNewVersionCode(srcpartNo, srcrev);
		    srcpartName = (srcdata.get("partName") == null) ? "" : (String) srcdata.get("partName");
		    srclvl = (srcdata.get("lvl") == null) ? "" : (String) srcdata.get("lvl");

		    srcqty = (srcdata.get("qty") == null) ? "" : (String) srcdata.get("qty");
		    srcreftop = (srcdata.get("reftop") == null) ? "" : (String) srcdata.get("reftop");
		    srcrefbtm = (srcdata.get("refbtm") == null) ? "" : (String) srcdata.get("refbtm");
		    srchardnessFrom = (srcdata.get("hardnessFrom") == null) ? "" : (String) srcdata.get("hardnessFrom");
		    srchardnessTo = (srcdata.get("hardnessTo") == null) ? "" : (String) srcdata.get("hardnessTo");
		    srcparentNo = (srcdata.get("real_parentRev") == null) ? "" : (String) srcdata.get("real_parentNo");
		    srcpver = (srcdata.get("real_parentRev") == null) ? "" : (String) srcdata.get("real_parentRev");
		    srcbom_path = (srcdata.get("bom_path") == null) ? "" : (String) srcdata.get("bom_path");

		    srcict = (srcdata.get("ict") == null) ? "" : (String) srcdata.get("ict");
		    srcdesignDate = (srcdata.get("designDate") == null) ? "" : (String) srcdata.get("designDate");
		    srcunit = (srcdata.get("unit") == null) ? "" : (String) srcdata.get("unit");
		    srcmaterial = (srcdata.get("material") == null) ? "" : (String) srcdata.get("material");

		} else {
		    srcpartNo = "";
		    srcrev = "";
		    newsrcrev = "";
		    srcpartName = "";
		    srclvl = "";
		    srcqty = "";
		    srcreftop = "";
		    srcrefbtm = "";
		    srchardnessFrom = "";
		    srchardnessTo = "";
		    srcparentNo = "";
		    srcpver = "";
		    srcbom_path = "";
		    srcict = "";
		    srcdesignDate = "";
		    srcunit = "";
		    srcmaterial = "";
		}

		if (desbomHash.containsKey(totkey)) {

		    Kogger.debug(getClass(), "deskey==>" + totkey);
		    Hashtable descdata = (Hashtable) desbomHash.get(totkey);

		    despartNo = (descdata.get("partNo") == null) ? "" : (String) descdata.get("partNo");
		    desrev = (descdata.get("rev") == null) ? "" : (String) descdata.get("rev");

		    newdesrev = getNewVersionCode(despartNo, desrev);

		    despartName = (descdata.get("partName") == null) ? "" : (String) descdata.get("partName");
		    deslvl = (descdata.get("lvl") == null) ? "" : (String) descdata.get("lvl");
		    desqty = (descdata.get("qty") == null) ? "" : (String) descdata.get("qty");
		    desreftop = (descdata.get("reftop") == null) ? "" : (String) descdata.get("reftop");
		    desrefbtm = (descdata.get("refbtm") == null) ? "" : (String) descdata.get("refbtm");
		    deshardnessFrom = (descdata.get("hardnessFrom") == null) ? "" : (String) descdata.get("hardnessFrom");
		    deshardnessTo = (descdata.get("hardnessTo") == null) ? "" : (String) descdata.get("hardnessTo");
		    desparentNo = (descdata.get("real_parentNo") == null) ? "" : (String) descdata.get("real_parentNo");
		    despver = (descdata.get("real_parentRev") == null) ? "" : (String) descdata.get("real_parentRev");
		    desbom_path = (descdata.get("bom_path") == null) ? "" : (String) descdata.get("bom_path");
		    desict = (descdata.get("ict") == null) ? "" : (String) descdata.get("ict");
		    desdesignDate = (descdata.get("designDate") == null) ? "" : (String) descdata.get("designDate");
		    desunit = (descdata.get("unit") == null) ? "" : (String) descdata.get("unit");
		    desmaterial = (descdata.get("material") == null) ? "" : (String) descdata.get("material");

		} else {
		    despartNo = "";
		    desrev = "";
		    newdesrev = "";
		    despartName = "";
		    deslvl = "";
		    desqty = "";
		    desreftop = "";
		    desrefbtm = "";
		    deshardnessFrom = "";
		    deshardnessTo = "";
		    desparentNo = "";
		    despver = "";
		    desbom_path = "";

		    desict = "";
		    desdesignDate = "";
		    desunit = "";
		    desmaterial = "";
		}

		// Kogger.debug(getClass(), "src part kry==>" + srclvl+"|"+srcparentNo+"|"+srcpartNo);
		// Kogger.debug(getClass(), "des partNo==>" + deslvl+"|"+desparentNo+"|"+despartNo);

		// if (srcbomHash.containsKey(totkey) && !desbomHash.containsKey(totkey)) {
		if (!desbomHash.containsKey(totkey)) {
		    srcpartNo = "<font color=\"#FF0000\"><B>" + srcpartNo + "</B></font>";

		    Kogger.debug(getClass(), "srcpartNo===>" + srcpartNo);
		    gubun = "ADD";

		}

		// if (!srcbomHash.containsKey(totkey) && desbomHash.containsKey(totkey)) {
		if (!srcbomHash.containsKey(totkey)) {
		    despartNo = "<font color=\"#0000FF\"><B>" + despartNo + "</B></font>";
		    Kogger.debug(getClass(), "despartNo===>" + despartNo);
		    gubun = "DEL";

		}

		if (srcbomHash.containsKey(totkey) && desbomHash.containsKey(totkey)) {
		    int changeCnt = 0;

		    if (!srcpartName.equals(despartName)) {
			srcpartName = "<font color=\"#34862E\"><B>" + srcpartName + "</B></font>";
			despartName = "<font color=\"#34862E\"><B>" + despartName + "</B></font>";
			changeCnt++;
		    }

		    // if (!srcrev.equals(desrev)) {
		    // srcrev = "<font color='#00FF00'>" + srcrev + "</font>";
		    // desrev = "<font color='#00FF00'>" + desrev + "</font>";
		    // changeCnt++;
		    // }

		    if (!newsrcrev.equals(newdesrev)) {
			newsrcrev = "<font color=\"#34862E\"><B>" + newsrcrev + "</B></font>";
			newdesrev = "<font color=\"#34862E\"><B>" + newdesrev + "</B></font>";
			changeCnt++;
		    }

		    if (!srcqty.equals(desqty)) {
			srcqty = "<font color=\"#34862E\"><B>" + srcqty + "</B></font>";
			desqty = "<font color=\"#34862E\"><B>" + desqty + "</B></font>";
			changeCnt++;
		    }

		    if (!srcreftop.equals(desreftop)) {
			srcreftop = "<font color=\"#34862E\"><B>" + srcreftop + "</B></font>";
			desreftop = "<font color=\"#34862E\"><B>" + desreftop + "</B></font>";
			changeCnt++;
		    }

		    if (!srcrefbtm.equals(desrefbtm)) {
			srcrefbtm = "<font color=\"#34862E\"><B>" + srcrefbtm + "</B></font>";
			desrefbtm = "<font color=\"#34862E\"><B>" + desrefbtm + "</B></font>";
			changeCnt++;
		    }

		    if (!srchardnessFrom.equals(deshardnessFrom)) {
			srchardnessFrom = "<font color=\"#34862E\"><B>" + srchardnessFrom + "</B></font>";
			deshardnessFrom = "<font color=\"#34862E\"><B>" + deshardnessFrom + "</B></font>";
			changeCnt++;
		    }

		    if (!srchardnessTo.equals(deshardnessTo)) {
			srchardnessTo = "<font color=\"#34862E\"><B>" + srchardnessTo + "</B></font>";
			deshardnessTo = "<font color=\"#34862E\"><B>" + deshardnessTo + "</B></font>";
			changeCnt++;
		    }

		    if (!srcmaterial.equals(desmaterial)) {
			srcmaterial = "<font color=\"#34862E\"><B>" + srcmaterial + "</B></font>";
			desmaterial = "<font color=\"#34862E\"><B>" + desmaterial + "</B></font>";
			changeCnt++;
		    }

		    if (changeCnt > 0)
			gubun = "CHANGE";
		}

		StringTokenizer st = new StringTokenizer(totkey, "|");
		String[] treekey = new String[st.countTokens()];
		int x = 0;
		while (st.hasMoreTokens()) {
		    treekey[x] = (String) st.nextToken();
		    x++;
		}

		Hashtable data = new Hashtable();

		int iParentLvl = Integer.parseInt(treekey[0]) - 1;

		data.put("parentNo", treekey[1] + "|" + Integer.toString(iParentLvl));
		data.put("partNo", treekey[2] + "|" + treekey[0]);
		data.put("lvl", treekey[0]);

		// Kogger.debug(getClass(), "######################################");

		Kogger.debug(getClass(), "lvl==============>" + treekey[0]);
		Kogger.debug(getClass(), "parentNo==============>" + treekey[1]);
		Kogger.debug(getClass(), "partNo==============>" + treekey[2]);
		// Kogger.debug(getClass(), "######################################");

		data.put("srcpartNo", srcpartNo);
		data.put("srcrev", srcrev);
		data.put("newsrcrev", newsrcrev);
		data.put("srcpartName", srcpartName);
		data.put("srclvl", srclvl);
		data.put("srcqty", srcqty);
		data.put("srcreftop", srcreftop);
		data.put("srcrefbtm", srcrefbtm);
		data.put("srchardnessFrom", srchardnessFrom);
		data.put("srchardnessTo", srchardnessTo);
		data.put("srcparentNo", srcparentNo);
		data.put("srcpver", srcpver);
		data.put("srcbom_path", srcbom_path);
		data.put("srcict", srcict);
		data.put("srcdesignDate", srcdesignDate);
		data.put("srcunit", srcunit);
		data.put("srcmaterial", srcmaterial);

		data.put("despartNo", despartNo);
		data.put("desrev", desrev);
		data.put("newdesrev", newdesrev);
		data.put("despartName", despartName);
		data.put("deslvl", deslvl);
		data.put("desqty", desqty);
		data.put("desreftop", desreftop);
		data.put("desrefbtm", desrefbtm);
		data.put("deshardnessFrom", deshardnessFrom);
		data.put("deshardnessTo", deshardnessTo);
		data.put("desparentNo", desparentNo);
		data.put("despver", despver);
		data.put("desbom_path", desbom_path);
		data.put("desict", desict);
		data.put("desdesignDate", desdesignDate);
		data.put("desunit", desunit);
		data.put("desmaterial", desmaterial);

		// Kogger.debug(getClass(), "CHANGE srcqty==>" + srcqty);
		// Kogger.debug(getClass(), "CHANGE desqty==>" + desqty);

		totalList.add(data);
		Kogger.debug(getClass(), "####################################################################################");
	    }

	    Kogger.debug(getClass(), "totalList=============>" + totalList.size());

	    totalTreeList = getGridBOM(totalList);

	    Kogger.debug(getClass(), totalTreeList.toString());

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return totalTreeList;
    }

    /**********************************************************
     * 엑셀업로드용 부품검색(최신 버전 부품)
     **********************************************************/
    public Hashtable getPartInfo(String partNumber) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();
	ArrayList revList = new ArrayList();

	Hashtable partInfo = new Hashtable();
	WTPart part = null;

	try {

	    sql.append("SELECT ROWNUM AS NUM, X0.* 											");
	    sql.append("FROM (														");
	    sql.append("  SELECT A0.WTPARTNUMBER AS PARTNUMBER ,A0.NAME AS PARTNAME ,(B0.CLASSNAMEA2A2 || ':' || B0.IDA2A2) AS OID, 	");
	    sql.append("  B0.VERSIONIDA2VERSIONINFO AS REV, B0.VERSIONSORTIDA2VERSIONINFO AS SORTVER 					");
	    sql.append("  FROM WTPARTMASTER A0, WTPART B0 WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE AND B0.LATESTITERATIONINFO=1 	");
	    sql.append("  AND A0.WTPARTNUMBER='" + partNumber + "' ORDER BY B0.VERSIONSORTIDA2VERSIONINFO DESC				");
	    sql.append(") X0 WHERE ROWNUM = 1												");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    // Kogger.debug(getClass(), "set:" + partNumber);
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		String oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
		String partName = rs.getString("PARTNAME") == null ? "" : rs.getString("PARTNAME").trim();
		String rev = rs.getString("REV") == null ? "" : rs.getString("REV").trim();
		part = KETBomPartUtil.getPart(oid);
		partInfo.put("oid", oid);
		partInfo.put("WTPart", part);
		partInfo.put("partName", partName);
		partInfo.put("rev", rev);

		String revDis = StringUtils.stripToEmpty(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartRevision));
		partInfo.put("revDis", revDis);

		// Kogger.debug(getClass(), "revision:" + revision);
		// revList.add(revision);
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return partInfo;
    }

    /**********************************************************
     * USAGELINK 자부품의 버전 리스트 (역전개용)
     **********************************************************/
    public ArrayList getVersionList(String partNumber) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	ArrayList revList = new ArrayList();
	try {

	    sql.append(" SELECT REV FROM (SELECT DISTINCT TRIM(BOMVERSION) AS REV  FROM KETPARTUSAGELINK WHERE CHILDITEMCODE='" + partNumber + "') A ORDER BY REV DESC ");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    // Kogger.debug(getClass(), "set:" + partNumber);
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		String revision = rs.getString("REV") == null ? "" : rs.getString("REV").trim();
		if (revision.indexOf(".") != -1) {
		    revision = revision.substring(0, revision.indexOf("."));
		}
		// Kogger.debug(getClass(), "revision:" + revision);
		revList.add(revision);
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return revList;
    }

    /**********************************************************
     * USAGELINK 자부품 OID
     **********************************************************/
    public String getUsageLinkChildPartOid(String partNumber, String rev) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	// ArrayList revList = new ArrayList();
	String oid = "";
	try {

	    if (rev.indexOf(".") != -1) {
		rev = rev.substring(0, rev.indexOf("."));
	    }

	    sql.append(" SELECT  MAX(VERSIONITEMCODE) AS OID  FROM KETPARTUSAGELINK WHERE CHILDITEMCODE='" + partNumber + "' AND BOMVERSION=" + rev + "  ");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    // Kogger.debug(getClass(), "set:" + partNumber);
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return oid;
    }

    /**********************************************************
     * USAGELINK 모부품 OID
     **********************************************************/
    public String getUsageLinkParentPartOid(String partNumber, String rev) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	// ArrayList revList = new ArrayList();
	String oid = "";
	try {

	    if (rev.indexOf(".") != -1) {
		rev = rev.substring(0, rev.indexOf("."));
	    }

	    /*
	     * sql.append(" SELECT  MAX(IDA3A5) AS OID  FROM KETPARTUSAGELINK WHERE PARENTITEMCODE='" + partNumber + "' AND BOMVERSION=" + rev + "  ");
	     */
	    sql.append(" SELECT MAX(IDA3A5) AS OID ");
	    sql.append("   FROM KETPARTUSAGELINK ");
	    sql.append("  WHERE IDA3A5 IN ");
	    sql.append("        (SELECT B0.IDA2A2 ");
	    sql.append("           FROM WTPARTMASTER A0, WTPART B0 ");
	    sql.append("          WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE ");
	    sql.append("            AND A0.WTPARTNUMBER='" + partNumber + "' AND B0.VERSIONIDA2VERSIONINFO ='" + rev + "') ");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    // Kogger.debug(getClass(), "set:" + partNumber);
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return oid;
    }

    /**********************************************************
     * USAGELINK 모부품 OID
     **********************************************************/
    public String getUsageLinkParentPartOid(String partNumber) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	// ArrayList revList = new ArrayList();
	String oid = "";
	try {

	    sql.append(" SELECT  MAX(IDA3A5) AS OID  FROM KETPARTUSAGELINK WHERE PARENTITEMCODE='" + partNumber + "'");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    // Kogger.debug(getClass(), "set:" + partNumber);
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return oid;
    }

    public String getPartName(String partNumber, String rev) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	// ArrayList revList = new ArrayList();
	String partName = "";
	try {

	    if (rev.indexOf(".") != -1) {
		rev = rev.substring(0, rev.indexOf("."));
	    }

	    sql.append(" SELECT NAME FROM WTPARTMASTER WHERE WTPARTNUMBER='" + partNumber + "'");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		partName = rs.getString("NAME") == null ? "" : rs.getString("NAME").trim();
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return partName;
    }

    public String getPartOid(String partNumber, String rev) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	// ArrayList revList = new ArrayList();
	String oid = "";
	try {

	    if (rev.indexOf(".") != -1) {
		rev = rev.substring(0, rev.indexOf("."));
	    }

	    sql.append("   SELECT (B0.CLASSNAMEA2A2||':'||B0.IDA2A2) AS OID FROM WTPARTMASTER A0, WTPART B0    	");
	    sql.append("    WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE 						");
	    sql.append("      AND A0.WTPARTNUMBER='" + partNumber + "' 						");
	    sql.append("      AND B0.VERSIONIDA2VERSIONINFO='" + rev + "' 						");
	    sql.append("      AND B0.LATESTITERATIONINFO=1 							");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
		// Kogger.debug(getClass(), oid);

	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return oid;
    }

    public String getPartOid(String partNumber, String rev, WTConnection conn) throws Exception {
	String oid = "";

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    StringBuffer sql = new StringBuffer();

	    // ArrayList revList = new ArrayList();

	    if (rev.indexOf(".") != -1) {
		rev = rev.substring(0, rev.indexOf("."));
	    }

	    sql.append("   SELECT (B0.CLASSNAMEA2A2||':'||B0.IDA2A2) AS OID FROM WTPARTMASTER A0, WTPART B0    	");
	    sql.append("    WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE 						");
	    sql.append("      AND A0.WTPARTNUMBER='" + partNumber + "' 						");
	    sql.append("      AND B0.VERSIONIDA2VERSIONINFO='" + rev + "' 						");
	    sql.append("      AND B0.LATESTITERATIONINFO=1 							");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
		// Kogger.debug(getClass(), oid);

	    }

	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return oid;
    }

    public String getPartOid2(String partNumber, String rev, WTConnection conn) throws Exception {
	String oid = "";

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    StringBuffer sql = new StringBuffer();

	    // ArrayList revList = new ArrayList();

	    if (rev.indexOf(".") != -1) {
		rev = rev.substring(0, rev.indexOf("."));
	    }

	    sql.append("   SELECT (B0.CLASSNAMEA2A2||':'||B0.IDA2A2) AS OID FROM WTPARTMASTER A0, WTPART B0    	");
	    sql.append("    WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE 						");
	    sql.append("      AND A0.WTPARTNUMBER='" + partNumber + "' 						");
	    sql.append("      AND B0.VERSIONIDA2VERSIONINFO='" + rev + "' 						");
	    sql.append("      AND B0.LATESTITERATIONINFO=1 							");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
		// Kogger.debug(getClass(), oid);

	    }

	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return oid;
    }

    public String isDeleted(String partNumber, String rev) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	// ArrayList revList = new ArrayList();
	String result = "";
	try {

	    if (rev.indexOf(".") != -1) {
		rev = rev.substring(0, rev.indexOf("."));
	    }

	    sql.append("   SELECT  " + PartSpecEnum.SpIsDeleted.getColumnName() + " AS ISDELETE FROM WTPARTMASTER A0, WTPART B0    	");
	    sql.append("    WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE 						");
	    sql.append("      AND A0.WTPARTNUMBER='" + partNumber + "' 						");
	    sql.append("      AND B0.VERSIONIDA2VERSIONINFO='" + rev + "' 						");
	    sql.append("      AND B0.LATESTITERATIONINFO=1 							");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		result = rs.getString("ISDELETE") == null ? "" : rs.getString("ISDELETE").trim();
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return result;
    }

    public String partNewOld(String partNumber, String rev) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	// ArrayList revList = new ArrayList();
	String result = "";
	try {

	    if (rev.indexOf(".") != -1) {
		rev = rev.substring(0, rev.indexOf("."));
	    }

	    sql.append("   SELECT  " + PartSpecEnum.SpBOMFlag.getColumnName() + " AS NEWOLD FROM WTPARTMASTER A0, WTPART B0    	");
	    sql.append("    WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE 						");
	    sql.append("      AND A0.WTPARTNUMBER='" + partNumber + "' 						");
	    sql.append("      AND B0.VERSIONIDA2VERSIONINFO='" + rev + "' 					");
	    sql.append("      AND B0.LATESTITERATIONINFO=1 							");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		result = rs.getString("NEWOLD") == null ? "" : rs.getString("NEWOLD").trim();
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return result;
    }

    public String getBoxQty(String partNumber, String rev) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	KETBomPartUtil util = new KETBomPartUtil();
	StringBuffer sql = new StringBuffer();

	// ArrayList revList = new ArrayList();
	String result = "1.000";
	String partOid = "";
	try {

	    if (rev.indexOf(".") != -1) {
		rev = rev.substring(0, rev.indexOf("."));
	    }

	    partOid = getPartOid(partNumber, rev);
	    WTPart part = util.getPart(partOid);
	    String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);

	    if (partType.equals("F") || partType.equals("W")) {//상품도 추가 2017.05.26 by 황정태

		sql.append("SELECT * FROM ( 														\n");
		sql.append("	SELECT NEWBOMCODE AS PARTNUMBER, BOMVERSION,  										\n");
		sql.append("	       CASE INSTR(BOMVERSION,'.')   											\n");
		sql.append("	       WHEN 0 THEN BOMVERSION  												\n");
		sql.append("	       ELSE  SUBSTR(BOMVERSION,0,INSTR(BOMVERSION,'.')-1) END AS VERSION, NVL(BOXQUANTITY,'1') AS BOXQTY, QUANTITY  	\n");
		sql.append("	  FROM KETBOMHEADER  WHERE NEWBOMCODE='" + partNumber + "' AND BOMVERSION='" + rev + "' 							\n");
		if(isEditBom){//BOM 수정시 수정한 수량이 안나오는 경우를 커버하기 위함 2017.06.22 by 황정태
		    sql.append("       AND TRANSFERFLAG != 'Y' \n");
		}
		sql.append("	 UNION 															\n");
		sql.append("	SELECT ECOITEMCODE AS PARTNUMBER, BOMVERSION,  										\n");
		sql.append("	       CASE INSTR(BOMVERSION,'.')   											\n");
		sql.append("	       WHEN 0 THEN BOMVERSION  												\n");
		sql.append("	       ELSE  SUBSTR(BOMVERSION,0,INSTR(BOMVERSION,'.')-1) END AS VERSION, BOXQUANTITY AS BOXQTY, QUANTITY  		\n");
		sql.append("	  FROM KETBOMECOHEADER WHERE ECOITEMCODE='" + partNumber + "' AND BOMVERSION='" + rev + "' 							\n");
		if(isEditBom){//BOM 수정시 수정한 수량이 안나오는 경우를 커버하기 위함 2017.06.22 by 황정태
		    sql.append("       AND TRANSFERFLAG != 'Y' \n");
		}
		sql.append(") 																\n");

		// Kogger.debug(getClass(), "SQL:" + sql.toString());
		pstmt = conn.prepareStatement(sql.toString());
		// pstmt.setString(1, partNumber);
		rs = pstmt.executeQuery();

		if (rs.next()) {
		    result = rs.getString("BOXQTY") == null ? "" : rs.getString("BOXQTY").trim();
		}
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return result;
    }

    public String getMaterial(String partNumber, String rev) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	// ArrayList revList = new ArrayList();
	String result = "";
	try {

	    if (rev.indexOf(".") != -1) {
		rev = rev.substring(0, rev.indexOf("."));
	    }

	    sql.append("   SELECT  " + PartSpecEnum.SpMaterDie.getColumnName() + " AS MATERIAL FROM WTPARTMASTER A0, WTPART B0    	");
	    sql.append("    WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE 						");
	    sql.append("      AND A0.WTPARTNUMBER='" + partNumber + "' 						");
	    sql.append("      AND B0.VERSIONIDA2VERSIONINFO='" + rev + "' 						");
	    sql.append("      AND B0.LATESTITERATIONINFO=1 							");

	    Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		result = rs.getString("MATERIAL") == null ? "" : rs.getString("MATERIAL").trim();
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return result;
    }

    public String getApprovedPartOid(String partNumber, String rev) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	// ArrayList revList = new ArrayList();
	String oid = "";
	try {

	    sql.append("   SELECT (B0.CLASSNAMEA2A2||':'||B0.IDA2A2) AS OID FROM WTPARTMASTER A0, WTPART B0    	");
	    sql.append("    WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE 						");
	    sql.append("      AND A0.WTPARTNUMBER='" + partNumber + "' 						");
	    sql.append("      AND B0.VERSIONIDA2VERSIONINFO='" + rev + "' 					");
	    sql.append("      AND B0.LATESTITERATIONINFO=1 							");
	    // if (state != null && state.equals("APPROVED"))
	    sql.append("      AND B0.STATESTATE='APPROVED' 							");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return oid;
    }

    /*
     * 새로 정의된 버전을 보여주기위해 버전을 가져오는 함수
     */
    public String getNewVersionCode2(String number, String rev) throws Exception {
	// 도면에서 먼저 찾는다.
	String new_revision = this.getNewVersionCodeFromEpmDoc(number, rev);

	// 도면에서 못찾았을 경우
	if (new_revision.equals("")) {
	    // 부품에서 찾는다.
	    new_revision = this.getNewVersionCode(number, rev);

	}

	return new_revision;
    }

    public String getNewVersionCode(String partNumber, String rev) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	// ArrayList revList = new ArrayList();
	// Hashtable data = new Hashtable();
	String new_revision = rev;
	try {

	    if (rev.indexOf(".") != -1) {
		rev = rev.substring(0, rev.indexOf("."));
	    }

	    sql.append("SELECT A0.WTPARTNUMBER, B0.VERSIONIDA2VERSIONINFO AS REV, B0." + PartSpecEnum.SpPartRevision.getColumnName() + " AS NEWREV	\n");
	    sql.append("  FROM WTPARTMASTER A0, WTPART B0  								\n");
	    sql.append(" WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE AND B0.LATESTITERATIONINFO=1  			\n");
	    sql.append("   AND A0.WTPARTNUMBER='" + partNumber + "'								\n");
	    sql.append("   AND B0.VERSIONIDA2VERSIONINFO=(SELECT  CASE INSTR(BOMVERSION,'.')   				\n");
	    sql.append("                                          WHEN 0 THEN BOMVERSION  				\n");
	    sql.append("                                          ELSE SUBSTR(BOMVERSION,0,INSTR(BOMVERSION,'.')-1)  	\n");
	    sql.append("					  END AS VERSION 					\n");
	    sql.append("                                    FROM (SELECT '" + rev + "' AS BOMVERSION FROM DUAL)) 		\n");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs != null) {
		if (rs.next()) {
		    // String wtPartNumber = rs.getString("WTPARTNUMBER") == null ? "" : rs.getString("WTPARTNUMBER").trim();
		    // String revision = rs.getString("REV") == null ? "" : rs.getString("REV").trim();
		    String newrev = rs.getString("NEWREV") == null ? "" : rs.getString("NEWREV").trim();

		    new_revision = newrev;

		}
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return new_revision;
    }

    public String getNewVersionCode(String partNumber, String rev, WTConnection conn) throws Exception {
	String new_revision = "";

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    StringBuffer sql = new StringBuffer();

	    // ArrayList revList = new ArrayList();
	    // Hashtable data = new Hashtable();

	    if (rev.indexOf(".") != -1) {
		rev = rev.substring(0, rev.indexOf("."));
	    }

	    sql.append("SELECT A0.WTPARTNUMBER, B0.VERSIONIDA2VERSIONINFO AS REV, B0." + PartSpecEnum.SpPartRevision.getColumnName() + " AS NEWREV	\n");
	    sql.append("  FROM WTPARTMASTER A0, WTPART B0  								\n");
	    sql.append(" WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE AND B0.LATESTITERATIONINFO=1  			\n");
	    sql.append("   AND A0.WTPARTNUMBER='" + partNumber + "'								\n");
	    sql.append("   AND B0.VERSIONIDA2VERSIONINFO=(SELECT  CASE INSTR(BOMVERSION,'.')   				\n");
	    sql.append("                                          WHEN 0 THEN BOMVERSION  				\n");
	    sql.append("                                          ELSE SUBSTR(BOMVERSION,0,INSTR(BOMVERSION,'.')-1)  	\n");
	    sql.append("					  END AS VERSION 					\n");
	    sql.append("                                    FROM (SELECT '" + rev + "' AS BOMVERSION FROM DUAL)) 		\n");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs != null) {
		if (rs.next()) {
		    // String wtPartNumber = rs.getString("WTPARTNUMBER") == null ? "" : rs.getString("WTPARTNUMBER").trim();
		    // String revision = rs.getString("REV") == null ? "" : rs.getString("REV").trim();
		    String newrev = rs.getString("NEWREV") == null ? "" : rs.getString("NEWREV").trim();

		    new_revision = newrev;

		}
	    }

	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return new_revision;
    }

    private String getNewVersionCodeFromEpmDoc(String docNumber, String rev) throws Exception {
	String new_revision = "";

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {

	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT B0.CLASSNAMEA2A2||':'||B0.IDA2A2 AS DOC_OID \n");
	    sql.append("  FROM EPMDOCUMENTMASTER A0, EPMDOCUMENT B0 \n");
	    sql.append(" WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE AND B0.LATESTITERATIONINFO=1 \n");
	    sql.append("   AND A0.DOCUMENTNUMBER='" + docNumber + "' \n");
	    sql.append("   AND B0.VERSIONIDA2VERSIONINFO='" + rev + "' \n");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();
	    if (rs != null) {
		if (rs.next()) {
		    String DOC_OID = rs.getString("DOC_OID") == null ? "" : rs.getString("DOC_OID").trim();
		    EPMDocument epmDoc = (EPMDocument) CommonUtil.getObject(DOC_OID);
		    new_revision = StringUtil.checkNull(IBAUtil.getAttrValue(epmDoc, EDMHelper.IBA_MANUFACTURING_VERSION));
		}
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return new_revision;
    }

    /**********************************************************
     * 모부품 체크 정보(하위에 부품이 있는 지 체크)
     **********************************************************/
    public Hashtable getLeafCheckHash(List<Map<String, Object>> bomList) throws Exception {
	Hashtable leafCheckHash = new Hashtable();

	try {

	    if (bomList != null) {
		for (int i = 0; i < bomList.size(); i++) {
		    Hashtable data = (Hashtable) bomList.get(i);

		    String parent = (String) data.get("parentNo");
		    if (!(parent.equals("") || parent == null)) {
			String tmpkey = parent;
			leafCheckHash.put(tmpkey, tmpkey);
		    }

		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return leafCheckHash;
    }

    /**********************************************************
     * BOM COPY 데이터를 저장 KETBOMCOMPONENT에 INSERT
     * 
     * @throws Exception
     **********************************************************/
    public String copyBom(String ecoNumber, WTPart srcPart, WTPart targetPart) throws Exception {

	String result = "success";

	String partType = "";
	String partOid = "";
	KETBomPartUtil pUtil = new KETBomPartUtil();
	List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;

	String partNo = "";
	String rev = "";
	String partName = "";
	String lvl = "";
	String seq = "";
	String qty = "";
	String unit = "";
	String ict = "";
	String reftop = "";
	String refbtm = "";
	String material = "";
	String hardnessFrom = "";
	String hardnessTo = "";
	String designDate = "";
	String parentNo = "";
	String bomFlag = "";

	StringBuffer sql = new StringBuffer();

	try {

	    partType = PartSpecGetter.getPartSpec(srcPart, PartSpecEnum.SpPartType);
	    // Kogger.debug(getClass(), "partType==>"+partType);
	    long id = pUtil.getPartLongId(srcPart);
	    partOid = Long.toString(id);

	    String srcPartNo = srcPart.getNumber();
	    String targetPartNo = targetPart.getNumber();

	    Hashtable srcHash = new Hashtable();
	    srcHash.put("partOid", partOid);
	    srcHash.put("partType", partType);
	    srcHash.put("dataType", "TreeData");

	    treeList = getLatestBOM(srcHash); // source bom list

	    String insertColumn = " NEWBOMCODE, BOMVERSION, PARENTITEMCODE, CHILDITEMCODE, BOMLEVEL, ITEMSEQ";
	    insertColumn += ", QUANTITY, UNIT, MATERIAL, HARDNESSFROM, HARDNESSTO, DESIGNDATE";
	    insertColumn += ", ICT, REFTOP, REFBOTTOM, NEWFLAG ";

	    for (int i = 1; i < treeList.size(); i++) {
		Hashtable data = (Hashtable) treeList.get(i);

		partNo = ((String) data.get("partNo") == null) ? "" : (String) data.get("partNo");
		rev = ((String) data.get("rev") == null) ? "" : (String) data.get("rev");
		partName = ((String) data.get("partName") == null) ? "" : (String) data.get("partName");
		lvl = ((String) data.get("lvl") == null) ? "" : (String) data.get("lvl");
		int new_lvl = Integer.parseInt(lvl);
		seq = ((String) data.get("seq") == null) ? "" : (String) data.get("seq");
		qty = ((String) data.get("qty") == null) ? "" : (String) data.get("qty");
		unit = ((String) data.get("unit") == null) ? "" : (String) data.get("unit");
		ict = ((String) data.get("ict") == null) ? "" : (String) data.get("ict");
		reftop = ((String) data.get("reftop") == null) ? "" : (String) data.get("reftop");
		refbtm = ((String) data.get("refbtm") == null) ? "" : (String) data.get("refbtm");
		material = ((String) data.get("material") == null) ? "" : (String) data.get("material");
		hardnessFrom = ((String) data.get("hardnessFrom") == null) ? "" : (String) data.get("hardnessFrom");
		hardnessTo = ((String) data.get("hardnessTo") == null) ? "" : (String) data.get("hardnessTo");
		designDate = ((String) data.get("designDate") == null) ? "" : (String) data.get("designDate");
		parentNo = ((String) data.get("parentNo") == null) ? "" : (String) data.get("parentNo");

		if (parentNo.equals(srcPartNo)) {
		    parentNo = targetPartNo;
		}
		bomFlag = partNewOld(partNo, rev);

		sql = new StringBuffer();
		sql.append(" INSERT INTO KETBOMCOMPONENT (" + insertColumn + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		pstmt = conn.prepareStatement(sql.toString());

		pstmt.setString(1, Utility.checkNVL(targetPartNo));// NEWBOMCODE
		pstmt.setString(2, Utility.checkNVL(rev));// BOMVERSION
		pstmt.setString(3, Utility.checkNVL(parentNo));// PARENTITEMCODE
		pstmt.setString(4, Utility.checkNVL(partNo)); // CHILDITEMCODE
		pstmt.setString(5, Utility.checkNVL(lvl)); // BOMLEVEL
		pstmt.setString(6, Utility.checkNVL(seq)); // ITEMSEQ
		pstmt.setString(7, Utility.checkNVL(qty)); // QUANTITY
		pstmt.setString(8, Utility.checkNVL(unit)); // UNIT
		pstmt.setString(9, Utility.checkNVL(material)); // MATERIAL
		pstmt.setString(10, Utility.checkNVL(hardnessFrom)); // HARDNESSFROM
		pstmt.setString(11, Utility.checkNVL(hardnessTo)); // HARDNESSTO
		pstmt.setString(12, Utility.checkNVL(designDate)); // DESIGNDATE
		pstmt.setString(13, Utility.checkNVL(ict)); // ICT
		pstmt.setString(14, Utility.checkNVL(reftop)); // REFTOP
		pstmt.setString(15, Utility.checkNVL(refbtm)); // REFBOTTOM
		pstmt.setString(16, Utility.checkNVL(bomFlag)); // NEWFLAG

		pstmt.executeUpdate();
		try{pstmt.close();}catch(Exception e){}

	    }
	    conn.commit();
	} catch (Exception e) {
	    result = "fail";
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
	} finally {

	    try {

		if (pstmt != null) {
		    pstmt.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return result;
    }

    /**********************************************************
     * Cad BOM 데이터를 저장 KETBOMCOMPONENT에 INSERT
     * 
     * @throws Exception
     **********************************************************/
    public String insertBomData(String ecoNumber, String productNo, List<Map<String, Object>> treeList) throws Exception {

	String result = "success";

	String partType = "";
	String partOid = "";
	KETBomPartUtil pUtil = new KETBomPartUtil();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;

	String partNo = "";
	String rev = "";
	String partName = "";
	String lvl = "";
	String seq = "";
	String qty = "";
	String unit = "";
	String ict = "";
	String reftop = "";
	String refbtm = "";
	String material = "";
	String hardnessFrom = "";
	String hardnessTo = "";
	String designDate = "";
	String parentNo = "";
	String bomFlag = "";

	StringBuffer sql = new StringBuffer();

	try {

	    Hashtable srcHash = new Hashtable();
	    srcHash.put("partOid", partOid);
	    srcHash.put("partType", partType);
	    srcHash.put("dataType", "TreeData");

	    String insertColumn = " NEWBOMCODE, BOMVERSION, PARENTITEMCODE, CHILDITEMCODE, BOMLEVEL, ITEMSEQ";
	    insertColumn += ", QUANTITY, UNIT, MATERIAL, HARDNESSFROM, HARDNESSTO, DESIGNDATE";
	    insertColumn += ", ICT, REFTOP, REFBOTTOM, NEWFLAG ";

	    Hashtable keyHash = new Hashtable();

	    for (int i = 0; i < treeList.size(); i++) {
		Hashtable data = (Hashtable) treeList.get(i);

		partNo = ((String) data.get("partNo") == null) ? "" : (String) data.get("partNo");
		rev = ((String) data.get("rev") == null) ? "" : (String) data.get("rev");
		partName = ((String) data.get("partName") == null) ? "" : (String) data.get("partName");
		lvl = ((String) data.get("lvl") == null) ? "" : (String) data.get("lvl");
		// int new_lvl = Integer.parseInt(lvl);
		// seq = ((String) data.get("seq") == null) ? "10" : (String) data.get("seq");
		seq = "10";
		qty = ((String) data.get("qty") == null) ? "" : (String) data.get("qty");
		unit = ((String) data.get("unit") == null) ? "" : (String) data.get("unit");

		if (unit.indexOf("KET_PERCENT") != -1)
		    unit = "KET_%";

		ict = ((String) data.get("ict") == null) ? "" : (String) data.get("ict");
		reftop = ((String) data.get("reftop") == null) ? "" : (String) data.get("reftop");
		refbtm = ((String) data.get("refbtm") == null) ? "" : (String) data.get("refbtm");
		material = ((String) data.get("material") == null) ? "" : (String) data.get("material");
		hardnessFrom = ((String) data.get("hardnessFrom") == null) ? "" : (String) data.get("hardnessFrom");
		hardnessTo = ((String) data.get("hardnessTo") == null) ? "" : (String) data.get("hardnessTo");
		designDate = ((String) data.get("designDate") == null) ? "" : (String) data.get("designDate");
		parentNo = ((String) data.get("parentNo") == null) ? "" : (String) data.get("parentNo");

		String keyStr = parentNo + "↔" + partNo;

		if (!keyHash.containsKey(keyStr)) {
		    bomFlag = partNewOld(partNo, rev);

		    sql = new StringBuffer();
		    sql.append(" INSERT INTO KETBOMCOMPONENT (" + insertColumn + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		    pstmt = conn.prepareStatement(sql.toString());

		    pstmt.setString(1, Utility.checkNVL(productNo));// NEWBOMCODE
		    pstmt.setString(2, Utility.checkNVL(rev));// BOMVERSION
		    pstmt.setString(3, Utility.checkNVL(parentNo));// PARENTITEMCODE
		    pstmt.setString(4, Utility.checkNVL(partNo)); // CHILDITEMCODE
		    pstmt.setString(5, Utility.checkNVL(lvl)); // BOMLEVEL
		    pstmt.setString(6, Utility.checkNVL(seq)); // ITEMSEQ
		    pstmt.setString(7, Utility.checkNVL(qty)); // QUANTITY
		    pstmt.setString(8, Utility.checkNVL(unit)); // UNIT
		    pstmt.setString(9, Utility.checkNVL(material)); // MATERIAL
		    pstmt.setString(10, Utility.checkNVL(hardnessFrom)); // HARDNESSFROM
		    pstmt.setString(11, Utility.checkNVL(hardnessTo)); // HARDNESSTO
		    pstmt.setString(12, Utility.checkNVL(designDate)); // DESIGNDATE
		    pstmt.setString(13, Utility.checkNVL(ict)); // ICT
		    pstmt.setString(14, Utility.checkNVL(reftop)); // REFTOP
		    pstmt.setString(15, Utility.checkNVL(refbtm)); // REFBOTTOM
		    pstmt.setString(16, Utility.checkNVL(bomFlag)); // NEWFLAG

		    pstmt.executeUpdate();
		    try{pstmt.close();}catch(Exception e){}
		}

		keyHash.put(keyStr, "");

	    }
	    conn.commit();
	} catch (Exception e) {
	    result = "fail";
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
	} finally {

	    try {

		if (pstmt != null) {
		    pstmt.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return result;
    }

    /**********************************************************
     * UsageLink BOM 가져오기 (CAD BOM)
     **********************************************************/
    public List<Map<String, Object>> getUsageLinkBOM(String ecoNumber, String prodNumber, String partNumber) throws Exception {

	List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	String partNo = "";
	String rev = "";
	String lvl = "";
	String seq = "";
	String qty = "";
	String unit = "";
	String ict = "";
	String reftop = "";
	String refbtm = "";
	String material = "";
	String hardnessFrom = "";
	String hardnessTo = "";
	String designDate = "";
	String statestr = "";
	String parentNo = "";

	try {

	    sql.append("SELECT ROWNUM AS NUM, LEVEL AS LVL,	 X0.ITEMSEQ, 											\n");
	    sql.append("       X0.PARENTITEMCODE AS ASSEMBLY_ITEM_CODE, 											\n");
	    sql.append("       X0.CHILDITEMCODE AS COMPONENT_ITEM_CODE, 											\n");
	    sql.append("       X0.CHILDITEMVERSION AS COMPONENT_ITEM_REV,	X0.QUANTITY AS QTY, X0.UNIT, 							\n");
	    sql.append("       X0.STARTDATE, X0.ENDDATE,  X0.MATERIAL, X0.HARDNESSFROM, X0.HARDNESSTO, X0.DESIGNDATE,  						\n");
	    sql.append("       X0.ICT AS ICT, X0.REFTOP AS REFTOP, X0.REFBOTTOM AS REFBOTTOM 									\n");
	    sql.append("FROM KETPARTUSAGELINK X0 														\n");
	    sql.append("START WITH   X0.IDA3A5 = (SELECT MAX(IDA3A5) FROM  KETPARTUSAGELINK WHERE PARENTITEMCODE='" + partNumber + "' )    				\n");
	    sql.append("CONNECT BY PRIOR (SELECT MAX(B.IDA3A5) FROM KETPARTUSAGELINK B WHERE B.PARENTITEMCODE = X0.CHILDITEMCODE  ) = X0.IDA3A5   		\n");

	    pstmt = conn.prepareStatement(sql.toString());

	    rs = pstmt.executeQuery();

	    while (rs.next()) {

		Map<String, Object> data = new Hashtable();

		partNo = rs.getString("COMPONENT_ITEM_CODE") == null ? "" : rs.getString("COMPONENT_ITEM_CODE").trim();
		rev = rs.getString("COMPONENT_ITEM_REV") == null ? "" : rs.getString("COMPONENT_ITEM_REV").trim();
		lvl = rs.getString("LVL") == null ? "" : rs.getString("LVL").trim();
		seq = rs.getString("ITEMSEQ") == null ? "" : rs.getString("ITEMSEQ").trim();
		qty = rs.getString("QTY") == null ? "" : rs.getString("QTY").trim();
		unit = rs.getString("UNIT") == null ? "" : rs.getString("UNIT").trim();
		ict = rs.getString("ICT") == null ? "" : rs.getString("ICT").trim();
		reftop = rs.getString("REFTOP") == null ? "" : rs.getString("REFTOP").trim();
		refbtm = rs.getString("REFBOTTOM") == null ? "" : rs.getString("REFBOTTOM").trim();
		material = rs.getString("MATERIAL") == null ? "" : rs.getString("MATERIAL").trim();
		hardnessFrom = rs.getString("HARDNESSFROM") == null ? "" : rs.getString("HARDNESSFROM").trim();
		hardnessTo = rs.getString("HARDNESSTO") == null ? "" : rs.getString("HARDNESSTO").trim();
		designDate = rs.getString("DESIGNDATE") == null ? "" : rs.getString("DESIGNDATE").trim();
		parentNo = rs.getString("ASSEMBLY_ITEM_CODE") == null ? "" : rs.getString("ASSEMBLY_ITEM_CODE").trim();

		partNo = ((String) data.get("partNo") == null) ? "" : (String) data.get("partNo");
		rev = ((String) data.get("rev") == null) ? "" : (String) data.get("rev");
		lvl = ((String) data.get("lvl") == null) ? "" : (String) data.get("lvl");
		seq = ((String) data.get("seq") == null) ? "" : (String) data.get("seq");
		qty = ((String) data.get("qty") == null) ? "" : (String) data.get("qty");
		unit = ((String) data.get("unit") == null) ? "" : (String) data.get("unit");

		if (unit.indexOf("KET_%") != -1)
		    unit = "KET_PERCENT";

		ict = ((String) data.get("ict") == null) ? "" : (String) data.get("ict");
		reftop = ((String) data.get("reftop") == null) ? "" : (String) data.get("reftop");
		refbtm = ((String) data.get("refbtm") == null) ? "" : (String) data.get("refbtm");
		material = ((String) data.get("material") == null) ? "" : (String) data.get("material");
		hardnessFrom = ((String) data.get("hardnessFrom") == null) ? "" : (String) data.get("hardnessFrom");
		hardnessTo = ((String) data.get("hardnessTo") == null) ? "" : (String) data.get("hardnessTo");
		designDate = ((String) data.get("designDate") == null) ? "" : (String) data.get("designDate");
		parentNo = ((String) data.get("parentNo") == null) ? "" : (String) data.get("parentNo");

		data.put("partNo", partNo);
		data.put("rev", rev);
		data.put("lvl", lvl);
		data.put("seq", seq);
		data.put("qty", qty);
		data.put("unit", unit);
		data.put("ict", ict);
		data.put("reftop", reftop);
		data.put("refbtm", refbtm);
		data.put("material", material);
		data.put("hardnessFrom", hardnessFrom);
		data.put("hardnessTo", hardnessTo);
		data.put("designDate", designDate);
		data.put("parentNo", parentNo);

		treeList.add(data);

	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return treeList;
    }

    public String checkECOCoWorker(String ecoNumber, String userId, String gubun) throws Exception {

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql;
	String workFlag = "";
	try {

	    sql = new StringBuffer();

	    Kogger.debug(getClass(), "gubun===========>" + gubun);
	    Kogger.debug(getClass(), "ecoNumber===========>" + ecoNumber);

	    // if (gubun.equals("NEWBOM"))
	    // sql.append(" SELECT ENDWORKINGFLAG FROM KETBOMCOWORKER WHERE NEWBOMCODE='" + partNumber + "' \n");
	    // sql.append(" UNION \n");
	    // if (gubun.equals("ECOBOM"))

	    sql.append(" SELECT ENDWORKINGFLAG FROM KETBOMECOCOWORKER WHERE ECOHEADERNUMBER='" + ecoNumber + "' AND COWORKERID='" + userId + "'\n");

	    Kogger.debug(getClass(), "SQL==>" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    rs = pstmt.executeQuery();

	    if (rs != null) {
		while (rs.next()) {

		    workFlag = rs.getString("ENDWORKINGFLAG") == null ? "" : rs.getString("ENDWORKINGFLAG").trim();
		}
	    }

	    Kogger.debug(getClass(), "workFlag==>" + workFlag);

	    // conn.commit();

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return workFlag;
    }

    /**********************************************************
     * 모부품이 있는 경우 KETPartUsageLink 가져오기
     * 
     * @throws Exception
     **********************************************************/

    public String getEcoNumber(String ecoOid) throws Exception {

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql;
	String ecoNumber = "";
	try {

	    sql = new StringBuffer();
	    sql.append(" SELECT ECOID FROM KETPRODCHANGEORDER WHERE IDA2A2='" + ecoOid + "'");
	    pstmt = conn.prepareStatement(sql.toString());
	    rs = pstmt.executeQuery();

	    if (rs != null) {
		while (rs.next()) {

		    ecoNumber = rs.getString("ECOID") == null ? "" : rs.getString("ECOID").trim();
		}
	    }

	    conn.commit();

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return ecoNumber;
    }

    public boolean updateECOBaseLine(String ecoNumber) throws Exception {

	boolean result = true;

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	PreparedStatement pstmt2 = null;
	ResultSet rs2 = null;

	PreparedStatement pstmt3 = null;
	ResultSet rs3 = null;

	KETBomPartUtil pUtil = new KETBomPartUtil();
	StringBuffer sql = new StringBuffer();
	try {
	    Hashtable itemcodeHash = (Hashtable) getHeaderList(ecoNumber);

	    Hashtable cinPartHash = new Hashtable();

	    sql = new StringBuffer();

	    sql.append(" SELECT A0.PARENTITEMCODE, NVL(B0.BOMVERSION,' ') AS PARENT_REV,  					\n");
	    sql.append("	    A0.CHILDITEMCODE, A0.BOMVERSION AS CHILD_REV, A0.BOMLEVEL  					\n");
	    sql.append("   FROM KETBOMECOCOMPONENT A0,										\n");
	    sql.append("        (SELECT ECOITEMCODE, BOMVERSION 								\n");
	    sql.append("           FROM KETBOMECOHEADER WHERE ECOHEADERNUMBER='" + ecoNumber + "' ) B0 				\n");
	    sql.append("  WHERE A0.ECOHEADERNUMBER='" + ecoNumber + "' AND A0.PARENTITEMCODE = B0.ECOITEMCODE(+) 		\n");
	    sql.append("    AND A0.CHILDITEMCODE IN (SELECT ECOITEMCODE								\n");
	    sql.append("                               FROM KETBOMECOHEADER WHERE ECOHEADERNUMBER='" + ecoNumber + "' ) 	\n");
	    sql.append("  ORDER BY A0.BOMLEVEL DESC 										\n");

	    Kogger.debug(getClass(), "SQL==>" + sql.toString());

	    pstmt = conn.prepareStatement(sql.toString());
	    rs = pstmt.executeQuery();

	    Hashtable compPartRevH = new Hashtable();

	    if (rs != null) {
		while (rs.next()) {

		    String ecoitemCode = rs.getString("CHILDITEMCODE") == null ? "" : rs.getString("CHILDITEMCODE").trim();
		    String ecoitemRev = rs.getString("CHILD_REV") == null ? "" : rs.getString("CHILD_REV").trim();
		    String ecoitemLvl = rs.getString("BOMLEVEL") == null ? "" : rs.getString("BOMLEVEL").trim();

		    Kogger.debug(getClass(), "########################################################");
		    Kogger.debug(getClass(), "ecoitemCode==============>" + ecoitemCode);
		    Kogger.debug(getClass(), "ecoitemRev==============>" + ecoitemRev);
		    Kogger.debug(getClass(), "ecoitemLvl==============>" + ecoitemLvl);
		    Kogger.debug(getClass(), "--------------------------------------------------------");

		    sql = new StringBuffer();

		    sql.append("SELECT * FROM KETBOMECOCOMPONENT  					\n");
		    sql.append("WHERE ECOHEADERNUMBER='" + ecoNumber + "' AND NVL(ECOCODE,' ') <>'Add' 	\n");
		    sql.append("START WITH CHILDITEMCODE ='" + ecoitemCode + "'  			\n");
		    sql.append("CONNECT BY  PRIOR PARENTITEMCODE = CHILDITEMCODE 			\n");
		    sql.append("ORDER SIBLINGS BY ITEMSEQ 						\n");

		    pstmt2 = conn.prepareStatement(sql.toString());
		    rs2 = pstmt2.executeQuery();

		    if (rs2 != null) {
			while (rs2.next()) {

			    String parentNo = rs2.getString("PARENTITEMCODE") == null ? "" : rs2.getString("PARENTITEMCODE").trim();
			    String parentRev = getLatestPartRev(parentNo);
			    String childNo = rs2.getString("CHILDITEMCODE") == null ? "" : rs2.getString("CHILDITEMCODE").trim();
			    String childRev = rs2.getString("BOMVERSION") == null ? "" : rs2.getString("BOMVERSION").trim();

			    Kogger.debug(getClass(), "**************************************************************");
			    Kogger.debug(getClass(), "parentNo==============>" + parentNo);
			    Kogger.debug(getClass(), "parentRev==============>" + parentRev);
			    Kogger.debug(getClass(), "childNo==============>" + childNo);
			    Kogger.debug(getClass(), "childRev==============>" + childRev);

			    String cOid = getPartOid(childNo, childRev);
			    WTPart childPart = pUtil.getPart(cOid);

			    long newChildId = pUtil.getPartLongId(childPart);
			    String newChildIdStr = Long.toString(newChildId);

			    String pOid = getPartOid(parentNo, parentRev);
			    WTPart parentPart = pUtil.getPart(pOid);

			    long parentId = pUtil.getPartLongId(parentPart);
			    String parentIdStr = Long.toString(parentId);

			    Kogger.debug(getClass(), "**************************************************************");

			    if (itemcodeHash.containsKey(parentNo)) // 개정된 부품인 경우
			    {
				sql = new StringBuffer();
				sql.append(" SELECT (CLASSNAMEA2A2 ||':' || IDA2A2) AS OID, IDA3A5, VERSIONITEMCODE FROM KETPARTUSAGELINK ");
				sql.append(" WHERE IDA3A5='" + parentIdStr + "' AND CHILDITEMCODE='" + childNo + "'");

				pstmt3 = conn.prepareStatement(sql.toString());
				rs3 = pstmt3.executeQuery();

				if (rs3 != null) {
				    while (rs3.next()) {
					String uOid = rs3.getString("OID") == null ? "" : rs3.getString("OID").trim();
					String childIdStr = rs3.getString("VERSIONITEMCODE") == null ? "" : rs3.getString("VERSIONITEMCODE").trim();
					String uParentIdStr = rs3.getString("IDA3A5") == null ? "" : rs3.getString("IDA3A5").trim();

					Kogger.debug(getClass(), "KETPARTUSAGELINK IDA2A2==>" + uOid);
					Kogger.debug(getClass(), "KETPARTUSAGELINK IDA3A5==>" + uParentIdStr);
					Kogger.debug(getClass(), "KETPARTUSAGELINK VERSIONITEMCODE==>" + childIdStr);
					Kogger.debug(getClass(), "KETPARTUSAGELINK VERSIONITEMCODE (CHANGE)==>" + newChildIdStr);

					if (!newChildIdStr.equals(childIdStr)) {
					    KETPartUsageLink ulink = pUtil.getUsageLink(uOid);
					    if (ulink != null) {
						ulink.setVersionItemCode(newChildIdStr);
						ulink.setBomVersion(childRev);
						ulink.setChildItemVersion(childRev);
						wt.fc.PersistenceServerHelper.manager.update(ulink);
						PersistenceHelper.manager.refresh(ulink);
					    }
					}

				    }
				}

			    } else // 개정이 안된 부품인 경우 (체크아웃 - 체크인 필요)
			    {
				WTPart workPart = null;
				try {
				    Kogger.debug(getClass(), "WorkingCopy ++++++++++++++++++++++++++++++++++++++++++++++++++");

				    if (!cinPartHash.containsKey(parentNo + "^" + parentRev)) {
					workPart = PartUtil.getWorkingCopy(parentPart);
					workPart = (WTPart) PersistenceHelper.manager.save(workPart);
					workPart = (WTPart) WorkInProgressHelper.service.checkin(workPart, "");
					workPart = (WTPart) PersistenceHelper.manager.refresh(workPart);
				    } else {
					workPart = (WTPart) cinPartHash.get(parentNo + "^" + parentRev);
				    }

				    if (workPart != null) {
					parentId = pUtil.getPartLongId(workPart);
					parentIdStr = Long.toString(parentId);

					cinPartHash.put(parentNo + "^" + parentRev, workPart);
				    }

				    sql = new StringBuffer();
				    sql.append(" SELECT (CLASSNAMEA2A2 ||':' || IDA2A2) AS OID, IDA3A5, VERSIONITEMCODE FROM KETPARTUSAGELINK ");
				    sql.append(" WHERE IDA3A5='" + parentIdStr + "' AND CHILDITEMCODE='" + childNo + "'");

				    if (rs3 != null) {
					while (rs3.next()) {
					    String uOid = rs3.getString("OID") == null ? "" : rs3.getString("OID").trim();
					    String childIdStr = rs3.getString("VERSIONITEMCODE") == null ? "" : rs3.getString("VERSIONITEMCODE").trim();
					    String uParentIdStr = rs3.getString("IDA3A5") == null ? "" : rs3.getString("IDA3A5").trim();

					    Kogger.debug(getClass(), "KETPARTUSAGELINK IDA2A2==>" + uOid);
					    Kogger.debug(getClass(), "KETPARTUSAGELINK IDA3A5==>" + uParentIdStr);
					    Kogger.debug(getClass(), "KETPARTUSAGELINK VERSIONITEMCODE==>" + childIdStr);
					    Kogger.debug(getClass(), "KETPARTUSAGELINK VERSIONITEMCODE (CHANGE)==>" + newChildIdStr);

					    if (!newChildIdStr.equals(childIdStr)) {
						KETPartUsageLink ulink = pUtil.getUsageLink(uOid);
						ulink.setBomVersion(childRev);
						ulink.setChildItemVersion(childRev);
						ulink.setVersionItemCode(newChildIdStr);
						wt.fc.PersistenceServerHelper.manager.update(ulink);
					    }

					}
				    }

				    Kogger.debug(getClass(), "WorkingCopy ++++++++++++++++++++++++++++++++++++++++++++++++++" + parentIdStr);
				} catch (Exception e) {
				    Kogger.error(getClass(), e);
				}
			    }

			}
		    }

		    Kogger.debug(getClass(), "########################################################");
		}
	    }

	    conn.commit();

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return result;
    }

    public boolean updateECOBaseLine(String ecoNumber, WTConnection conn) throws Exception {

	boolean result = true;

	PreparedStatement pstmt = null;
	ResultSet rs = null;

	PreparedStatement pstmt2 = null;
	ResultSet rs2 = null;

	PreparedStatement pstmt3 = null;
	ResultSet rs3 = null;

	try {
	    KETBomPartUtil pUtil = new KETBomPartUtil();
	    StringBuffer sql = new StringBuffer();

	    Hashtable itemcodeHash = (Hashtable) getHeaderList(ecoNumber, conn);

	    Hashtable cinPartHash = new Hashtable();

	    sql = new StringBuffer();

	    sql.append(" SELECT A0.PARENTITEMCODE, NVL(B0.BOMVERSION,' ') AS PARENT_REV,  					\n");
	    sql.append("	    A0.CHILDITEMCODE, A0.BOMVERSION AS CHILD_REV, A0.BOMLEVEL  					\n");
	    sql.append("   FROM KETBOMECOCOMPONENT A0,										\n");
	    sql.append("        (SELECT ECOITEMCODE, BOMVERSION 								\n");
	    sql.append("           FROM KETBOMECOHEADER WHERE ECOHEADERNUMBER='" + ecoNumber + "' ) B0 				\n");
	    sql.append("  WHERE A0.ECOHEADERNUMBER='" + ecoNumber + "' AND A0.PARENTITEMCODE = B0.ECOITEMCODE(+) 		\n");
	    sql.append("    AND A0.CHILDITEMCODE IN (SELECT ECOITEMCODE								\n");
	    sql.append("                               FROM KETBOMECOHEADER WHERE ECOHEADERNUMBER='" + ecoNumber + "' ) 	\n");
	    sql.append("  ORDER BY A0.BOMLEVEL DESC 										\n");

	    Kogger.debug(getClass(), "SQL==>" + sql.toString());

	    pstmt = conn.prepareStatement(sql.toString());
	    rs = pstmt.executeQuery();

	    Hashtable compPartRevH = new Hashtable();

	    if (rs != null) {
		while (rs.next()) {

		    String ecoitemCode = rs.getString("CHILDITEMCODE") == null ? "" : rs.getString("CHILDITEMCODE").trim();
		    String ecoitemRev = rs.getString("CHILD_REV") == null ? "" : rs.getString("CHILD_REV").trim();
		    String ecoitemLvl = rs.getString("BOMLEVEL") == null ? "" : rs.getString("BOMLEVEL").trim();

		    Kogger.debug(getClass(), "########################################################");
		    Kogger.debug(getClass(), "ecoitemCode==============>" + ecoitemCode);
		    Kogger.debug(getClass(), "ecoitemRev==============>" + ecoitemRev);
		    Kogger.debug(getClass(), "ecoitemLvl==============>" + ecoitemLvl);
		    Kogger.debug(getClass(), "--------------------------------------------------------");

		    sql = new StringBuffer();

		    sql.append("SELECT * FROM (SELECT  * FROM KETBOMECOCOMPONENT  					\n");
		    // sql.append("WHERE NVL(ECOCODE,' ') <> 'Remove' AND  ECOHEADERNUMBER='" + ecoNumber + "' ) 	\n");
		    sql.append("WHERE ECOHEADERNUMBER='" + ecoNumber + "' ) 	\n");
		    sql.append("START WITH CHILDITEMCODE ='" + ecoitemCode + "'  			\n");
		    sql.append("CONNECT BY  PRIOR PARENTITEMCODE = CHILDITEMCODE 			\n");
		    sql.append("ORDER SIBLINGS BY ITEMSEQ 						\n");

		    Kogger.debug(getClass(), "SQL==>" + sql.toString());

		    pstmt2 = conn.prepareStatement(sql.toString());
		    rs2 = pstmt2.executeQuery();

		    if (rs2 != null) {
			while (rs2.next()) {

			    String parentNo = rs2.getString("PARENTITEMCODE") == null ? "" : rs2.getString("PARENTITEMCODE").trim();
			    String parentRev = getLatestPartRev(parentNo);
			    String childNo = rs2.getString("CHILDITEMCODE") == null ? "" : rs2.getString("CHILDITEMCODE").trim();
			    String childRev = rs2.getString("BOMVERSION") == null ? "" : rs2.getString("BOMVERSION").trim();
			    String ecoCode = rs2.getString("ECOCODE") == null ? "" : rs2.getString("ECOCODE").trim();

			    Kogger.debug(getClass(), "**************************************************************");
			    Kogger.debug(getClass(), "parentNo==============>" + parentNo);
			    Kogger.debug(getClass(), "parentRev==============>" + parentRev);
			    Kogger.debug(getClass(), "childNo==============>" + childNo);
			    Kogger.debug(getClass(), "childRev==============>" + childRev);

			    String cOid = getPartOid(childNo, childRev, conn);
			    WTPart childPart = pUtil.getPart(cOid);

			    long newChildId = pUtil.getPartLongId(childPart);
			    String newChildIdStr = Long.toString(newChildId);

			    String pOid = getPartOid(parentNo, parentRev, conn);
			    WTPart parentPart = pUtil.getPart(pOid);

			    long parentId = pUtil.getPartLongId(parentPart);
			    String parentIdStr = Long.toString(parentId);

			    Kogger.debug(getClass(), "**************************************************************");

			    // 추가, 삭제인 경우는 제외
			    // if (!(ecoCode.equals("Remove") || !ecoCode.equals("Add"))) {

			    if (itemcodeHash.containsKey(parentNo)) // 개정된 부품인 경우
			    {

				sql = new StringBuffer();
				sql.append(" SELECT (CLASSNAMEA2A2 ||':' || IDA2A2) AS OID, IDA3A5, VERSIONITEMCODE FROM KETPARTUSAGELINK \n");
				sql.append(" WHERE IDA3A5='" + parentIdStr + "' AND CHILDITEMCODE='" + childNo + "' \n");

				Kogger.debug(getClass(), "SQL3=====>" + sql.toString());

				pstmt3 = conn.prepareStatement(sql.toString());
				rs3 = pstmt3.executeQuery();

				if (rs3 != null) {
				    while (rs3.next()) {
					String uOid = rs3.getString("OID") == null ? "" : rs3.getString("OID").trim();
					String childIdStr = rs3.getString("VERSIONITEMCODE") == null ? "" : rs3.getString("VERSIONITEMCODE").trim();
					String uParentIdStr = rs3.getString("IDA3A5") == null ? "" : rs3.getString("IDA3A5").trim();

					Kogger.debug(getClass(), "1.KETPARTUSAGELINK IDA2A2==>" + uOid);
					Kogger.debug(getClass(), "1.KETPARTUSAGELINK IDA3A5==>" + uParentIdStr);
					Kogger.debug(getClass(), "1.KETPARTUSAGELINK VERSIONITEMCODE==>" + childIdStr);

					if (cinPartHash.containsKey(childNo + "^" + childRev)) {
					    WTPart workPart = null;
					    workPart = (WTPart) cinPartHash.get(childNo + "^" + childRev);

					    newChildId = pUtil.getPartLongId(workPart);
					    newChildIdStr = Long.toString(newChildId);

					    Kogger.debug(getClass(), "--1.KETPARTUSAGELINK VERSIONITEMCODE (CHANGE)==>" + newChildIdStr);
					}
					Kogger.debug(getClass(), "1.KETPARTUSAGELINK VERSIONITEMCODE (CHANGE)==>" + newChildIdStr);
					if (!newChildIdStr.equals(childIdStr)) {
					    KETPartUsageLink ulink = pUtil.getUsageLink(uOid);
					    ulink.setBomVersion(childRev);
					    ulink.setChildItemVersion(childRev);
					    ulink.setVersionItemCode(newChildIdStr);
					    wt.fc.PersistenceServerHelper.manager.update(ulink);

					    Kogger.debug(getClass(), "1.###>>>>>>>>>>Update============================================");
					}

				    }
				}

				if (rs3 != null) {
				    rs3.close();
				    rs3 = null;
				}

				if (pstmt3 != null) {
				    pstmt3.close();
				    pstmt3 = null;
				}

			    } else // 개정이 안된 부품인 경우 (체크아웃 - 체크인 필요)
			    {
				WTPart workPart = null;
				try {
				    Kogger.debug(getClass(), "WorkingCopy ++++++++++++++++++++++++++++++++++++++++++++++++++");

				    if (!cinPartHash.containsKey(parentNo + "^" + parentRev)) {
					workPart = PartUtil.getWorkingCopy(parentPart);
					workPart = (WTPart) PersistenceHelper.manager.save(workPart);
					workPart = (WTPart) WorkInProgressHelper.service.checkin(workPart, "");
					workPart = (WTPart) PersistenceHelper.manager.refresh(workPart);

					Kogger.debug(getClass(), "checkout==>" + parentNo + "^" + parentRev);
					cinPartHash.put(parentNo + "^" + parentRev, workPart);

				    } else {
					workPart = (WTPart) cinPartHash.get(parentNo + "^" + parentRev);

					Kogger.debug(getClass(), "cinPartHash in checkout==>" + parentNo + "^" + parentRev);
				    }

				    if (workPart != null) {
					parentId = pUtil.getPartLongId(workPart);
					parentIdStr = Long.toString(parentId);
				    }

				    sql = new StringBuffer();
				    sql.append(" SELECT (CLASSNAMEA2A2 ||':' || IDA2A2) AS OID, IDA3A5, VERSIONITEMCODE FROM KETPARTUSAGELINK ");
				    sql.append(" WHERE IDA3A5='" + parentIdStr + "' AND CHILDITEMCODE='" + childNo + "'");

				    pstmt3 = conn.prepareStatement(sql.toString());
				    rs3 = pstmt3.executeQuery();

				    if (rs3 != null) {
					while (rs3.next()) {
					    String uOid = rs3.getString("OID") == null ? "" : rs3.getString("OID").trim();
					    String childIdStr = rs3.getString("VERSIONITEMCODE") == null ? "" : rs3.getString("VERSIONITEMCODE").trim();
					    String uParentIdStr = rs3.getString("IDA3A5") == null ? "" : rs3.getString("IDA3A5").trim();

					    Kogger.debug(getClass(), "2.KETPARTUSAGELINK IDA2A2==>" + uOid);
					    Kogger.debug(getClass(), "2.KETPARTUSAGELINK IDA3A5==>" + uParentIdStr);
					    Kogger.debug(getClass(), "2.KETPARTUSAGELINK VERSIONITEMCODE==>" + childIdStr);
					    Kogger.debug(getClass(), "2.KETPARTUSAGELINK VERSIONITEMCODE (CHANGE)==>" + newChildIdStr);

					    if (!newChildIdStr.equals(childIdStr)) {
						KETPartUsageLink ulink = pUtil.getUsageLink(uOid);
						ulink.setBomVersion(childRev);
						ulink.setChildItemVersion(childRev);
						ulink.setVersionItemCode(newChildIdStr);
						wt.fc.PersistenceServerHelper.manager.update(ulink);

						Kogger.debug(getClass(), "2.###>>>>>>>>>>Update============================================");
					    }

					}
				    }

				    if (rs3 != null) {
					rs3.close();
					rs3 = null;
				    }

				    if (pstmt3 != null) {
					pstmt3.close();
					pstmt3 = null;
				    }

				    Kogger.debug(getClass(), "WorkingCopy ++++++++++++++++++++++++++++++++++++++++++++++++++" + parentIdStr);
				} catch (Exception e) {
				    Kogger.error(getClass(), e);
				}
			    }

			    // }
			}
		    }

		    if (rs2 != null) {
			rs2.close();
			rs2 = null;
		    }

		    if (pstmt2 != null) {
			pstmt2.close();
			pstmt2 = null;
		    }

		    Kogger.debug(getClass(), "########################################################");
		}
	    }

	} finally {
	    try {
		if (rs3 != null) {
		    rs3.close();
		    rs3 = null;
		}
		if (pstmt3 != null) {
		    pstmt3.close();
		    pstmt3 = null;
		}
		if (rs2 != null) {
		    rs2.close();
		    rs2 = null;
		}
		if (pstmt2 != null) {
		    pstmt2.close();
		    pstmt2 = null;
		}
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return result;
    }

    public ArrayList updateParentUsagelink(String partNumber, String ecoNumber) throws Exception {
	// WFBomEcoPartUsageQry 에서 사용

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	KETBomPartUtil pUtil = new KETBomPartUtil();
	StringBuffer sql = new StringBuffer();
	ArrayList usageList = new ArrayList();

	Hashtable itemcodeHash = (Hashtable) getHeaderList(ecoNumber);

	try {

	    String childoid = getLatestPart(partNumber);
	    WTPart childPart = pUtil.getPart(childoid);
	    String bomversion = wt.vc.VersionControlHelper.getVersionIdentifier(childPart).getValue();
	    long childid = pUtil.getPartLongId(childPart);
	    String childidStr = Long.toString(childid);

	    sql.append(" SELECT (CLASSNAMEA2A2 ||':' || IDA2A2) AS OID, IDA3A5, CHILDITEMCODE, PARENTITEMCODE, VERSIONITEMCODE FROM KETPARTUSAGELINK \n");
	    sql.append("  WHERE IDA3A5=(SELECT MAX(IDA3A5)   \n");
	    sql.append("  FROM KETPARTUSAGELINK  WHERE CHILDITEMCODE='" + partNumber + "') AND CHILDITEMCODE = '" + partNumber + "' \n");

	    Kogger.debug(getClass(), "updateParentUsagelink SELECT SQL==>" + sql.toString());

	    pstmt = conn.prepareStatement(sql.toString());

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		String oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
		String childOidStr = rs.getString("VERSIONITEMCODE") == null ? "" : rs.getString("VERSIONITEMCODE").trim();
		String parentOidStr = rs.getString("IDA3A5") == null ? "" : rs.getString("IDA3A5").trim();
		String parentNumber = rs.getString("PARENTITEMCODE") == null ? "" : rs.getString("PARENTITEMCODE").trim();
		String childNumber = rs.getString("CHILDITEMCODE") == null ? "" : rs.getString("CHILDITEMCODE").trim();

		Kogger.debug(getClass(), "updateParentUsagelink======================================================================");
		Kogger.debug(getClass(), "########################################################");
		Kogger.debug(getClass(), "ECO NO : " + ecoNumber);
		Kogger.debug(getClass(), "PARENT : " + parentNumber + "(" + parentOidStr + ")");
		Kogger.debug(getClass(), "CHILD : " + childNumber + "(" + childOidStr + ")");
		Kogger.debug(getClass(), "########################################################");
		Kogger.debug(getClass(), "updateParentUsagelink======================================================================");

		if (!itemcodeHash.containsKey(parentNumber)) {

		    sql = new StringBuffer();

		    sql.append("UPDATE KETPARTUSAGELINK SET BOMVERSION ='" + bomversion + "', CHILDITEMVERSION='" + bomversion + "', VERSIONITEMCODE='" + childidStr + "' WHERE IDA3A5='"
			    + parentOidStr + "' AND CHILDITEMCODE='" + partNumber + "'");

		    Kogger.debug(getClass(), "getParentUsagelink UPDATE SQL==>" + sql.toString());

		    pstmt = conn.prepareStatement(sql.toString());
		    pstmt.executeUpdate();
		    try{pstmt.close();}catch(Exception e){}

		    Kogger.debug(getClass(), "======================================================================");
		    updateParentUsagelink(parentNumber, ecoNumber);
		}
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return usageList;
    }

    public ArrayList getParentUsagelink(String partNumber, String ecoNumber, WTConnection conn) throws Exception {
	// WFBomEcoPartUsageQry 에서 사용
	ArrayList usageList = new ArrayList();

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    KETBomPartUtil pUtil = new KETBomPartUtil();
	    StringBuffer sql = new StringBuffer();

	    Hashtable itemcodeHash = (Hashtable) getHeaderList(ecoNumber, conn);

	    Hashtable partInfo = getPartInfo(partNumber);
	    WTPart childPart = (WTPart) partInfo.get("WTPart");
	    String bomversion = wt.vc.VersionControlHelper.getVersionIdentifier(childPart).getValue();
	    long childid = pUtil.getPartLongId(childPart);
	    String childidStr = Long.toString(childid);

	    sql.append(" SELECT (CLASSNAMEA2A2 ||':' || IDA2A2) AS OID, IDA3A5, CHILDITEMCODE, PARENTITEMCODE, VERSIONITEMCODE FROM KETPARTUSAGELINK \n");
	    sql.append("  WHERE IDA3A5=(SELECT MAX(IDA3A5)   \n");
	    sql.append("  FROM KETPARTUSAGELINK  WHERE CHILDITEMCODE='" + partNumber + "') AND CHILDITEMCODE = '" + partNumber + "' \n");

	    Kogger.debug(getClass(), "getParentUsagelink SELECT SQL==>" + sql.toString());

	    pstmt = conn.prepareStatement(sql.toString());

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		String oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
		String childOidStr = rs.getString("VERSIONITEMCODE") == null ? "" : rs.getString("VERSIONITEMCODE").trim();
		String parentOidStr = rs.getString("IDA3A5") == null ? "" : rs.getString("IDA3A5").trim();
		String parentNumber = rs.getString("PARENTITEMCODE") == null ? "" : rs.getString("PARENTITEMCODE").trim();
		String childNumber = rs.getString("CHILDITEMCODE") == null ? "" : rs.getString("CHILDITEMCODE").trim();

		Kogger.debug(getClass(), "getParentUsagelink======================================================================");
		Kogger.debug(getClass(), "########################################################");
		Kogger.debug(getClass(), "ECO NO : " + ecoNumber);
		Kogger.debug(getClass(), "PARENT : " + parentNumber + "(" + parentOidStr + ")");
		Kogger.debug(getClass(), "CHILD : " + childNumber + "(" + childOidStr + ")");
		Kogger.debug(getClass(), "########################################################");
		Kogger.debug(getClass(), "getParentUsagelink======================================================================");

		String pOid = getLatestPart(parentNumber, conn);
		WTPart parentPart = pUtil.getPart(pOid);

		Kogger.debug(getClass(), "======================================================================");

		if (!itemcodeHash.containsKey(parentNumber)) {

		    // WTPart workPart = part;
		    WTPart workPart = PartUtil.getWorkingCopy(parentPart);
		    workPart = (WTPart) PersistenceHelper.manager.save(workPart);
		    workPart = (WTPart) WorkInProgressHelper.service.checkin(workPart, "");
		    workPart = (WTPart) PersistenceHelper.manager.refresh(workPart);

		    long nextid = pUtil.getPartLongId(workPart);
		    String nextidStr = Long.toString(nextid);

		    sql = new StringBuffer();

		    sql.append("UPDATE KETPARTUSAGELINK SET BOMVERSION ='" + bomversion + "', CHILDITEMVERSION='" + bomversion + "', VERSIONITEMCODE='" + childidStr + "' WHERE IDA3A5='" + nextidStr
			    + "' AND VERSIONITEMCODE='" + childOidStr + "'");

		    // Kogger.debug(getClass(), "getParentUsagelink UPDATE SQL==>" + sql.toString());

		    // pstmt = conn.prepareStatement(sql.toString());
		    // pstmt.executeUpdate();

		    // Kogger.debug(getClass(), "======================================================================");
		    // Kogger.debug(getClass(), "parentPartNo=========>" + workPart.getNumber());
		    // Kogger.debug(getClass(), "parentPartState(NEW ITER)=========>" + workPart.getLifeCycleState().toString());
		    // Kogger.debug(getClass(), "parentPartNo(getVersionDisplayIdentity)=========>" + workPart.getVersionDisplayIdentity());
		    // Kogger.debug(getClass(), "parentPartNo(getVersionDisplayIdentifier)=========>" + workPart.getVersionDisplayIdentifier());
		    // Kogger.debug(getClass(), "parentPartNo(getIterationDisplayIdentifier)=========>" +
		    // workPart.getIterationDisplayIdentifier());
		    // Kogger.debug(getClass(), "childPart (bomversion)=========>" + bomversion);
		    // Kogger.debug(getClass(), "Ulink Parent(new)=========>=========>" + nextid);

		    getParentUsagelink(parentNumber, ecoNumber, conn);

		    // Hashtable data = new Hashtable();

		    // data.put("NEWPART", part);
		    // data.put("USAGELINK", ulink);

		    // sql = new StringBuffer();

		    // usageList.add(data);
		}
	    }

	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return usageList;
    }

    public ArrayList updateParentUsagelink(String partNumber, String ecoNumber, WTConnection conn) throws Exception {
	// WFBomEcoPartUsageQry 에서 사용
	ArrayList usageList = new ArrayList();

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    KETBomPartUtil pUtil = new KETBomPartUtil();
	    StringBuffer sql = new StringBuffer();

	    Hashtable itemcodeHash = (Hashtable) getHeaderList(ecoNumber, conn);

	    String childoid = getLatestPart(partNumber, conn);
	    WTPart childPart = pUtil.getPart(childoid);
	    String bomversion = wt.vc.VersionControlHelper.getVersionIdentifier(childPart).getValue();
	    long childid = pUtil.getPartLongId(childPart);
	    String childidStr = Long.toString(childid);

	    sql.append(" SELECT (CLASSNAMEA2A2 ||':' || IDA2A2) AS OID, IDA3A5, CHILDITEMCODE, PARENTITEMCODE, VERSIONITEMCODE FROM KETPARTUSAGELINK \n");
	    sql.append("  WHERE IDA3A5=(SELECT MAX(IDA3A5)   \n");
	    sql.append("  FROM KETPARTUSAGELINK  WHERE CHILDITEMCODE='" + partNumber + "') AND CHILDITEMCODE = '" + partNumber + "' \n");

	    Kogger.debug(getClass(), "updateParentUsagelink SELECT SQL==>" + sql.toString());

	    pstmt = conn.prepareStatement(sql.toString());

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		String oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
		String childOidStr = rs.getString("VERSIONITEMCODE") == null ? "" : rs.getString("VERSIONITEMCODE").trim();
		String parentOidStr = rs.getString("IDA3A5") == null ? "" : rs.getString("IDA3A5").trim();
		String parentNumber = rs.getString("PARENTITEMCODE") == null ? "" : rs.getString("PARENTITEMCODE").trim();
		String childNumber = rs.getString("CHILDITEMCODE") == null ? "" : rs.getString("CHILDITEMCODE").trim();

		Kogger.debug(getClass(), "updateParentUsagelink======================================================================");
		Kogger.debug(getClass(), "########################################################");
		Kogger.debug(getClass(), "ECO NO : " + ecoNumber);
		Kogger.debug(getClass(), "PARENT : " + parentNumber + "(" + parentOidStr + ")");
		Kogger.debug(getClass(), "CHILD : " + childNumber + "(" + childOidStr + ")");
		Kogger.debug(getClass(), "########################################################");
		Kogger.debug(getClass(), "updateParentUsagelink======================================================================");

		if (!itemcodeHash.containsKey(parentNumber)) {

		    sql = new StringBuffer();

		    sql.append("UPDATE KETPARTUSAGELINK SET BOMVERSION ='" + bomversion + "', CHILDITEMVERSION='" + bomversion + "', VERSIONITEMCODE='" + childidStr + "' WHERE IDA3A5='"
			    + parentOidStr + "' AND CHILDITEMCODE='" + partNumber + "'");

		    Kogger.debug(getClass(), "getParentUsagelink UPDATE SQL==>" + sql.toString());

		    pstmt = conn.prepareStatement(sql.toString());
		    pstmt.executeUpdate();
		    try{pstmt.close();}catch(Exception e){}

		    Kogger.debug(getClass(), "======================================================================");
		    updateParentUsagelink(parentNumber, ecoNumber, conn);
		}
	    }
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return usageList;
    }

    /**********************************************************
     * TreeGrid 용 BOM 정보 변환
     **********************************************************/

    public List<Map<String, Object>> getGridBOM(List<Map<String, Object>> bomList) throws Exception {

	List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
	Hashtable leafCheckHash = new Hashtable();

	try {

	    leafCheckHash = getLeafCheckHash(bomList);

	    if (bomList != null) {
		for (int i = 0; i < bomList.size(); i++) {
		    Hashtable data = (Hashtable) bomList.get(i);

		    Map<String, Object> newdata = new Hashtable();
		    newdata = data;

		    String lvl = (String) data.get("lvl");
		    String child = (String) data.get("partNo");

		    // Kogger.debug(getClass(), "child===>" + child);

		    String chkkey = child;

		    if (leafCheckHash.containsValue(chkkey)) {
			newdata.put("leaf", false);
			newdata.put("iconCls", "task-folder");

		    } else {
			newdata.put("leaf", true);
			newdata.put("iconCls", "task");
		    }

		    treeList.add(newdata);

		}
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return treeList;
    }

    public List<Map<String, Object>> getGridBOM2(List<Map<String, Object>> bomList) throws Exception {

	List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
	Hashtable leafCheckHash = new Hashtable();

	try {

	    leafCheckHash = getLeafCheckHash(bomList);

	    if (bomList != null) {
		for (int i = 0; i < bomList.size(); i++) {
		    Hashtable data = (Hashtable) bomList.get(i);

		    Map<String, Object> newdata = new Hashtable();
		    newdata = data;

		    String lvl = (String) data.get("lvl");
		    String child = (String) data.get("spartNo");

		    // Kogger.debug(getClass(), "child===>" + child);

		    String chkkey = child;

		    if (leafCheckHash.containsValue(chkkey)) {
			newdata.put("leaf", false);
			newdata.put("iconCls", "task-folder");

		    } else {
			newdata.put("leaf", true);
			newdata.put("iconCls", "task");
		    }

		    treeList.add(newdata);

		}
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return treeList;
    }

    /************************************************************
     * convertTreeList list ==> Tree list resultList : list rootNm : 루트 키 컬럼(키 컬럼) rootVal : 루트 값 (키 값) key : 키 컬럼 upkey : 부모키 컬럼 List
     **********************************************************/
    public static List<Map<String, Object>> convertTreeList(
	    List<Map<String, Object>> resultList /* tree list */
	    , String rootNm /* ex)modelNo */
	    , String rootVal /* ex) <font color='red'>MG656780-5_PRT</font> */
	    , String key /* ex) modelNo */
	    , String upkey /* ex) parentModelNo */) throws Exception {
	// List 생성
	List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
	TimerUtil timer = new TimerUtil("convertTreeList");
	
	try {
	    timer.setStartPoint("convertTreeList");
	    // 조회결과가 있으면
	    if (resultList != null && resultList.size() > 0) {
		// Map 생성
		Map<String, Object> listItem = null;

		// 조회결과 만큼 반복
		for (int g = 0; g < resultList.size(); g++) {
		    // Map에 g번째 Row 넣기
		    listItem = resultList.get(g);
		    // if (listItem.containsKey("children"))
		    // Kogger.debug(getClass(), "listItem.getChildren===>" + listItem.get("children"));
		    // 1 Level Node
		    if (listItem.get(rootNm).equals(rootVal)) {

			// g번째 Map에 children 컬럼 생성
			listItem.put("children", new ArrayList<Map<String, Object>>());
			// Tree에 g번째 Map 추가
			treeList.add(listItem);
		    }
		    // Others Node
		    else {
			new InnerClass_list() {
			    // 현재까지 만들어진 Tree,
			    public void getParentNode(List<Map<String, Object>> treeList/* tree list */
				    , Map<String, Object> listItem/* tree list의 element 1개를 순차적으로 가져온 것 */
				    , String key /* ex) modelNo */
				    , String upkey /* ex) parentModelNo */) {
				
				// 현재까지 만들어진 Tree 만큼 반복

				Hashtable h = new Hashtable();
				for (int t = 0; t < treeList.size(); t++) {
				    // Map에 t번째 Tree Row 넣기
				    Map<String, Object> treeItem = (Map<String, Object>) treeList.get(t);

				    List<Map<String, Object>> childList = (List<Map<String, Object>>) treeItem.get("children");

				    // Kogger.debug(getClass(), "childList==>" + childList.toString());
				    String childPartNo = "";
				    String childPartNoList = "";
				    // Hashtable bompathH = new Hashtable();
				    for (int k = 0; k < childList.size(); k++) {
					Map<String, Object> childItem = (Map<String, Object>) childList.get(k);
					childPartNo = (String) childItem.get(key);
					childPartNoList += (String) childPartNo + "^";
					// bompathH.put(childPartNo, (String) childItem.get("bom_path"));

				    }
				    // Kogger.debug(getClass(), "childPartNo===>" + childPartNoList);

				    // g번째 List가 t번째 Tree 자식이면

				    // if (childPartNo.indexOf(key) == -1) {
				    String checkp = (String) listItem.get(upkey);
				    String checkc = (String) treeItem.get(key);
				    String checker = "";
				    // if (!treeItem.get(key).equals(listItem.get(key))) {
				    
				    if("modelNo".equals(key)){ // CAD2BOM일 경우에
					
					String tempKeyResult = (String)treeItem.get(key);
					if(tempKeyResult != null){
					    // BOM Relation 가능여부를 표시 : StandardCad2BomService 353 라인에서 추가된 소스 때문에 수정함
					    // tklee 2015.1.16 수정
					    tempKeyResult = tempKeyResult.replace("<font color='red'>", "");
					    tempKeyResult = tempKeyResult.replace("</font>", "");
					}
					String tempUpKeyResult = (String)listItem.get(upkey);
					
					if (tempKeyResult.equals(tempUpKeyResult)) {
					    
					    // Map에 g번째 List 넣기
					    Map<String, Object> view = listItem;

					    // g번째 Map을 Tree의 자식으로 추가
					    // Kogger.debug(getClass(), "(" + t + "번째 Tree 자식이 존재)checker===>" + checker);
					    // Kogger.debug(getClass(), "listItem.get(" + upkey + ")==>" + listItem.get(upkey));
					    // Kogger.debug(getClass(), "listItem.get(" + key + ")==>" + listItem.get(key));
					    // Kogger.debug(getClass(), "treeItem.get(" + key + ")==>" + treeItem.get(key));

					    // Kogger.debug(getClass(), "Map에 g번째 List 넣기==>" + listItem.toString());
					    // g번째 Map에 children 컬럼 생성
					    if (childPartNoList.indexOf((String) listItem.get(key) + "^") == -1) { // 중복 체크
						view.put("children", new ArrayList<Map<String, Object>>());
						((List<Map<String, Object>>) treeItem.get("children")).add(view);
					    }
					    // break;
					}

					// h.put(checker, "");
					// t번째 자식이 아니면 t번째 자식의 자식일수 있음
					else {
					    // t번째 Tree 자식이 존재하면
					    h.put(checker, "");
					    if (((List<Map<String, Object>>) treeItem.get("children")).size() > 0) {
						getParentNode((List<Map<String, Object>>) treeItem.get("children"), listItem, key, upkey);
					    }
					}
					
				    }else{
					if (treeItem.get(key).equals(listItem.get(upkey))) {
					    // Map에 g번째 List 넣기
					    Map<String, Object> view = listItem;

					    // g번째 Map을 Tree의 자식으로 추가
					    // Kogger.debug(getClass(), "(" + t + "번째 Tree 자식이 존재)checker===>" + checker);
					    // Kogger.debug(getClass(), "listItem.get(" + upkey + ")==>" + listItem.get(upkey));
					    // Kogger.debug(getClass(), "listItem.get(" + key + ")==>" + listItem.get(key));
					    // Kogger.debug(getClass(), "treeItem.get(" + key + ")==>" + treeItem.get(key));

					    // Kogger.debug(getClass(), "Map에 g번째 List 넣기==>" + listItem.toString());
					    // g번째 Map에 children 컬럼 생성
					    if (childPartNoList.indexOf((String) listItem.get(key) + "^") == -1) { // 중복 체크
						view.put("children", new ArrayList<Map<String, Object>>());
						((List<Map<String, Object>>) treeItem.get("children")).add(view);
					    }
					    // break;
					}

					// h.put(checker, "");
					// t번째 자식이 아니면 t번째 자식의 자식일수 있음
					else {
					    // t번째 Tree 자식이 존재하면

					    h.put(checker, "");
					    if (((List<Map<String, Object>>) treeItem.get("children")).size() > 0) {
						getParentNode((List<Map<String, Object>>) treeItem.get("children"), listItem, key, upkey);
					    }

					}
				    }
				}
			    }
			}.getParentNode(treeList, listItem, key, upkey);
		    }
		}
	    }
	} catch (Exception e) {
	    Kogger.error(KETBOMQueryBean.class, e);
	    throw e;
	}
	timer.setEndPoint();
	timer.display();
	//Kogger.debug(KETBOMQueryBean.class, "JSON List >>> " + treeList);

	return treeList;
    }

    public interface InnerClass_list {
	public void getParentNode(List<Map<String, Object>> treeList, Map<String, Object> listItem, String key, String upkey);
    }

    /************************************************************
     * List 데이터를 트리형태로 정렬하는 재귀함수 (mylist: 기존 데이터 , newlist: 정렬한 데이터, parentNumber: 모부품 파라메터)
     **********************************************************/
    public List<Map<String, Object>> treeRecursive(List<Map<String, Object>> mylist, List<Map<String, Object>> newlist, String parentNumber) {

	for (int i = 0; i < mylist.size(); i++) {
	    Map<String, Object> data = (Map<String, Object>) mylist.get(i);

	    String parentNo = (String) data.get("parentNo");
	    String partNo = (String) data.get("partNo");

	    if (parentNumber.equals(parentNo)) {
		// Kogger.debug(getClass(), "PARENT:"+parentNo +"---"+"CHILD:"+partNo );
		newlist.add(data);
		treeRecursive(mylist, newlist, partNo);
	    }
	}

	return newlist;
    }

    /**
     * 프로젝트의 제품별 자부품 리스트 반환
     * 
     * @param productPartNoList
     * @return
     * @throws Exception
     * @메소드명 : getLatestChildPartByProduct
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 29.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */

    public List<Map<String, Object>> getWorkingChildPartByProduct(List<String> productPartNoList) throws Exception {
	List<Map<String, Object>> bomList = new ArrayList<Map<String, Object>>();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();
	KETBomPartUtil pUtil = new KETBomPartUtil();

	try {

	    Hashtable partTypeHash = new Hashtable();

	    partTypeHash.put("D", "Die No");
	    partTypeHash.put("M", "금형부품");
	    partTypeHash.put("F", "제품");
	    partTypeHash.put("H", "반제품");
	    partTypeHash.put("R", "원자재");
	    partTypeHash.put("K", "포장재");
	    partTypeHash.put("S", "스크랩");
	    partTypeHash.put("W", "상품");

	    sql.append("SELECT * \n");
	    sql.append("  FROM ( \n");

	    for (int i = 0; i < productPartNoList.size(); i++) {
		if (i > 0)
		    sql.append("  UNION ALL \n");

		sql.append("  SELECT * FROM ( \n");

		sql.append("	  SELECT 0 AS NUM \n");
		sql.append("	       , 0 AS LVL \n");
		sql.append("	       , '' AS LEVEL_PATH \n");
		sql.append("	       , '0' AS ITEMSEQ \n");
		sql.append("	       , B0.WTPARTNUMBER AS PRODUCT_CODE \n");
		sql.append("	       , B0.WTPARTNUMBER AS ASSEMBLY_ITEM_CODE \n");
		sql.append("	       , B0.WTPARTNUMBER AS COMPONENT_ITEM_CODE \n");
		sql.append("	       , B0.NAME AS COMPONENT_ITEM_NAME \n");
		sql.append("	       , ( \n");
		sql.append("	          SELECT MAX(VERSIONIDA2VERSIONINFO) \n");
		sql.append("	            FROM WTPART part \n");
		sql.append("	           WHERE PART.IDA3MASTERREFERENCE = B0.IDA2A2 \n");
		sql.append("	             AND part.LATESTITERATIONINFO = '1' \n");
		sql.append("	         ) AS COMPONENT_ITEM_REV \n");
		sql.append("	    FROM WTPARTMASTER B0 \n");
		sql.append("	   WHERE B0.WTPARTNUMBER = ? \n");
		sql.append("	  UNION ALL \n");

		sql.append("  SELECT * FROM ( \n");
		sql.append("	SELECT (ROWNUM+1) AS NUM,LEVEL AS LVL ,SYS_CONNECT_BY_PATH (A0.PARENTITEMCODE, '/') LEVEL_PATH, A0.ITEMSEQ, ");
		sql.append("         A0.NEWBOMCODE AS PRODUCT_CODE, A0.PARENTITEMCODE AS ASSEMBLY_ITEM_CODE, ");
		sql.append("         A0.CHILDITEMCODE AS COMPONENT_ITEM_CODE, ");
		sql.append("         B0.NAME AS COMPONENT_ITEM_NAME,  A0.BOMVERSION AS COMPONENT_ITEM_REV ");
		sql.append("	  FROM KETBOMCOMPONENT A0, WTPARTMASTER B0 ");
		sql.append("	 WHERE A0.CHILDITEMCODE = B0.WTPARTNUMBER AND A0.NEWBOMCODE=? ");
		sql.append("	 START WITH A0.PARENTITEMCODE=? ");
		sql.append("       CONNECT BY PRIOR CHILDITEMCODE=PARENTITEMCODE ");
		sql.append("	 ORDER SIBLINGS BY  A0.ITEMSEQ ");
		sql.append("  ) \n");

		sql.append("  ) \n");
	    }

	    sql.append("       ) \n");
	    sql.append(" ORDER BY PRODUCT_CODE, COMPONENT_ITEM_CODE									 \n");
	    Kogger.debug(getClass(), sql.toString());

	    pstmt = conn.prepareStatement(sql.toString());

	    int idx = 1;
	    for (int i = 0; i < productPartNoList.size(); i++) {

		pstmt.setString(idx++, productPartNoList.get(i));
		pstmt.setString(idx++, productPartNoList.get(i));
		pstmt.setString(idx++, productPartNoList.get(i));

	    }
	    rs = pstmt.executeQuery();

	    String oid = "";
	    String productCode = "";
	    String partNo = "";
	    String rev = "";
	    String partName = "";
	    String state = "";
	    String parentNo = "";
	    String partType = "";
	    String partTypeCode = "";
	    String isNewItem = "";
	    int i = 0;

	    int old_lvl = 0;

	    while (rs.next()) {

		Map<String, Object> data = new Hashtable();

		productCode = rs.getString("PRODUCT_CODE") == null ? "" : rs.getString("PRODUCT_CODE").trim();
		partNo = rs.getString("COMPONENT_ITEM_CODE") == null ? "" : rs.getString("COMPONENT_ITEM_CODE").trim();
		rev = rs.getString("COMPONENT_ITEM_REV") == null ? "" : rs.getString("COMPONENT_ITEM_REV").trim();

		if (rev.indexOf(".") != -1) {
		    rev = rev.substring(0, rev.indexOf("."));
		}

		partName = rs.getString("COMPONENT_ITEM_NAME") == null ? "" : rs.getString("COMPONENT_ITEM_NAME").trim();
		parentNo = rs.getString("ASSEMBLY_ITEM_CODE") == null ? "" : rs.getString("ASSEMBLY_ITEM_CODE").trim();
		isNewItem = "Y";
		oid = getPartOid(partNo, rev);
		WTPart childPart = pUtil.getPart(oid);
		state = childPart.getLifeCycleState().getDisplay();

		partTypeCode = PartSpecGetter.getPartSpec(childPart, PartSpecEnum.SpPartType);
		partType = (String) partTypeHash.get(partTypeCode);

		data.put("productCode", productCode);
		data.put("parentNo", parentNo);
		data.put("partType", partType);
		data.put("partTypeCode", partTypeCode);
		data.put("isNewItem", isNewItem);

		// 최신 리비전
		Versioned versioned = (Versioned) childPart;
		WTPart latestWTPart = (WTPart) VersionHelper.getLatestRevision(versioned);
		String latestWTPartNumber = latestWTPart.getNumber();
		String latestWTPartRevision = latestWTPart.getVersionInfo().getIdentifier().getValue();

		data.put("oid", CommonUtil.getOIDString(latestWTPart));
		data.put("partNo", latestWTPartNumber);
		data.put("rev", latestWTPartRevision);

		String revDis = StringUtils.stripToEmpty(PartSpecGetter.getPartSpec(latestWTPart, PartSpecEnum.SpPartRevision));
		data.put("revDis", revDis);

		data.put("partName", latestWTPart.getName());
		data.put("state", latestWTPart.getLifeCycleState().getDisplay());

		bomList.add(data);
	    }
	} catch (Exception e) {
	    try {
		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }
	}

	return bomList;
    }

    public List<Map<String, Object>> getLatestChildPartByProduct2(List<String> productPartNoList) throws Exception {
	List<Map<String, Object>> bomList = new ArrayList<Map<String, Object>>();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	try {

	    sql.append("SELECT * \n");
	    sql.append("  FROM ( \n");

	    for (int i = 0; i < productPartNoList.size(); i++) {
		if (i > 0)
		    sql.append("  UNION ALL \n");

		sql.append("  SELECT * FROM ( \n");
		sql.append("              SELECT ROWNUM AS NUM								 \n");
		sql.append("                                    ,LEVEL AS LVL								 \n");
		sql.append("                                    ,SYS_CONNECT_BY_PATH (X0.PARENTITEMCODE, '/') LEVEL_PATH, X0.ITEMSEQ			 \n");
		sql.append("                                    ,X0.IDA3A5 AS ASSEMBLY_ITEM_OID						 \n");
		sql.append("                                    ,X0.PARENTITEMCODE AS ASSEMBLY_ITEM_CODE					 \n");
		sql.append("                                    ,(SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5) AS ASSEMBLY_ITEM_REV						 \n");
		sql.append("                                    ,X0.VERSIONITEMCODE AS COMPONENT_ITEM_OID					 \n");
		sql.append("                                    ,X0.CHILDITEMCODE AS COMPONENT_ITEM_CODE					 \n");
		sql.append("                                    , (SELECT NAME								 \n");
		sql.append("                                         FROM WTPARTMASTER							 \n");
		sql.append("                                        WHERE IDA2A2 = (SELECT IDA3MASTERREFERENCE				 \n");
		sql.append("                                                          FROM WTPART						 \n");
		sql.append("                                                         WHERE IDA2A2 = X0.VERSIONITEMCODE))			 \n");
		sql.append("                                         AS COMPONENT_ITEM_NAME							 \n");
		sql.append("                                    ,X0.BOMVERSION AS COMPONENT_ITEM_REV					 \n");
		sql.append("                                    , (SELECT P0.NAME 							 \n");
		sql.append("                                         FROM WTPART W0, LIFECYCLETEMPLATE L0, PHASETEMPLATE P0								 \n");
		sql.append("                                        WHERE W0.IDA3A2STATE= L0.IDA2A2 AND L0.IDA3B9=P0.IDA2A2 AND  W0.IDA2A2 = X0.VERSIONITEMCODE)					 \n");
		sql.append("                                         AS STATUSKR								 \n");
		sql.append("                                    , (SELECT decode(" + PartSpecEnum.SpPartType.getColumnName()
		        + ", 'D', 'Die No', 'M', '금형부품', 'F', '제품', 'H', '반제품', 'R', '원자재', 'K', '포장재', 'S', '스크랩', 'W', '상품','제품')" + " FROM WTPART "
		        + "WHERE IDA2A2 = X0.VERSIONITEMCODE) AS PART_TYPE         	\n");
		sql.append("                                    , (SELECT " + PartSpecEnum.SpPartType.getColumnName() + " FROM WTPART " + "WHERE IDA2A2 = X0.VERSIONITEMCODE) AS PART_TYPE_CODE \n\n");

		sql.append("                                FROM KETPARTUSAGELINK X0							 \n");
		sql.append("                          START WITH X0.IDA3A5 IN (SELECT MAX(A1.IDA2A2) AS IDA2A2 \n");
		sql.append("                                    FROM WTPARTMASTER A0, WTPART A1\n");
		sql.append("                                    WHERE A0.IDA2A2 = A1.IDA3MASTERREFERENCE\n");
		sql.append("                                      AND A1.LATESTITERATIONINFO = '1' \n");
		sql.append("                                      AND A0.WTPARTNUMBER = ? ) \n");
		sql.append("                          CONNECT BY PRIOR (SELECT MAX (B.IDA3A5)						 \n");
		sql.append("                                              FROM KETPARTUSAGELINK B						 \n");
		sql.append("                                             WHERE B.PARENTITEMCODE = X0.CHILDITEMCODE) = X0.IDA3A5		 \n");
		sql.append("                   ORDER SIBLINGS BY X0.ITEMSEQ								 \n");
		sql.append("  ) \n");

	    }

	    sql.append("       ) \n");
	    sql.append(" ORDER BY PRODUCT_CODE, COMPONENT_ITEM_CODE									 \n");
	    Kogger.debug(getClass(), sql.toString());

	    pstmt = conn.prepareStatement(sql.toString());
	    for (int i = 0; i < productPartNoList.size(); i++) {
		pstmt.setString(i + 1, productPartNoList.get(i));
	    }
	    rs = pstmt.executeQuery();

	    String oid = "";
	    String productCode = "";
	    String partNo = "";
	    String rev = "";
	    String partName = "";
	    String state = "";
	    String parentNo = "";
	    String partType = "";
	    String partTypeCode = "";
	    String isNewItem = "";
	    int i = 0;

	    int old_lvl = 0;

	    while (rs.next()) {

		Map<String, Object> data = new Hashtable();
		oid = rs.getString("COMPONENT_ITEM_OID") == null ? "" : rs.getString("COMPONENT_ITEM_OID").trim();
		productCode = rs.getString("PRODUCT_CODE") == null ? "" : rs.getString("PRODUCT_CODE").trim();
		partNo = rs.getString("COMPONENT_ITEM_CODE") == null ? "" : rs.getString("COMPONENT_ITEM_CODE").trim();
		rev = rs.getString("COMPONENT_ITEM_REV") == null ? "" : rs.getString("COMPONENT_ITEM_REV").trim();
		partName = rs.getString("COMPONENT_ITEM_NAME") == null ? "" : rs.getString("COMPONENT_ITEM_NAME").trim();
		state = rs.getString("STATUSKR") == null ? "" : rs.getString("STATUSKR").trim();
		parentNo = rs.getString("ASSEMBLY_ITEM_CODE") == null ? "" : rs.getString("ASSEMBLY_ITEM_CODE").trim();
		partType = rs.getString("PART_TYPE") == null ? "" : rs.getString("PART_TYPE").trim();
		partTypeCode = rs.getString("PART_TYPE_CODE") == null ? "" : rs.getString("PART_TYPE_CODE").trim();
		isNewItem = "Y";

		data.put("productCode", productCode);
		data.put("parentNo", parentNo);
		data.put("partType", partType);
		data.put("partTypeCode", partTypeCode);
		data.put("isNewItem", isNewItem);

		// 최신 리비전
		Versioned versioned = (Versioned) CommonUtil.getObject("wt.part.WTPart:" + oid);
		WTPart latestWTPart = (WTPart) VersionHelper.getLatestRevision(versioned);
		String latestWTPartNumber = latestWTPart.getNumber();
		String latestWTPartRevision = latestWTPart.getVersionInfo().getIdentifier().getValue();

		data.put("oid", CommonUtil.getOIDString(latestWTPart));
		data.put("partNo", latestWTPartNumber);
		data.put("rev", latestWTPartRevision);

		String revDis = StringUtils.stripToEmpty(PartSpecGetter.getPartSpec(latestWTPart, PartSpecEnum.SpPartRevision));
		data.put("revDis", revDis);

		data.put("partName", latestWTPart.getName());
		data.put("state", latestWTPart.getLifeCycleState().getDisplay());

		bomList.add(data);
	    }
	} catch (Exception e) {
	    try {
		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }
	}

	return bomList;
    }

    public List<Map<String, Object>> getLatestChildPartByProduct(List<String> productPartNoList) throws Exception {
	if (productPartNoList == null) {
	    return null;
	}
	List<Map<String, Object>> bomList = new ArrayList<Map<String, Object>>();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();
	try {

	    sql.append("SELECT * \n");
	    sql.append("  FROM ( \n");

	    for (int i = 0; i < productPartNoList.size(); i++) {
		if (i > 0)
		    sql.append("  UNION ALL \n");

		sql.append("	  SELECT B0.WTPARTNUMBER AS PRODUCT_CODE \n");

		sql.append("	       , ( \n");
		sql.append("	          SELECT MAX(IDA2A2) \n");
		sql.append("	            FROM WTPART part \n");
		sql.append("	           WHERE PART.IDA3MASTERREFERENCE = B0.IDA2A2 \n");
		sql.append("	             AND part.LATESTITERATIONINFO = '1' \n");
		sql.append("	         ) AS ASSEMBLY_ITEM_OID \n");
		sql.append("	       , B0.WTPARTNUMBER AS ASSEMBLY_ITEM_CODE \n");
		sql.append("	       , ( \n");
		sql.append("	          SELECT MAX(VERSIONIDA2VERSIONINFO) \n");
		sql.append("	            FROM WTPART part \n");
		sql.append("	           WHERE PART.IDA3MASTERREFERENCE = B0.IDA2A2 \n");
		sql.append("	             AND part.LATESTITERATIONINFO = '1' \n");
		sql.append("	         ) AS ASSEMBLY_ITEM_REV \n");

		sql.append("	       , ( \n");
		sql.append("	          SELECT TO_CHAR(MAX(IDA2A2)) \n");
		sql.append("	            FROM WTPART part \n");
		sql.append("	           WHERE PART.IDA3MASTERREFERENCE = B0.IDA2A2 \n");
		sql.append("	             AND part.LATESTITERATIONINFO = '1' \n");
		sql.append("	         ) AS COMPONENT_ITEM_OID \n");
		sql.append("	       , B0.WTPARTNUMBER AS COMPONENT_ITEM_CODE \n");
		sql.append("	       , ( \n");
		sql.append("	          SELECT MAX(VERSIONIDA2VERSIONINFO) \n");
		sql.append("	            FROM WTPART part \n");
		sql.append("	           WHERE PART.IDA3MASTERREFERENCE = B0.IDA2A2 \n");
		sql.append("	             AND part.LATESTITERATIONINFO = '1' \n");
		sql.append("	         ) AS COMPONENT_ITEM_REV \n");
		sql.append("	       , B0.NAME AS COMPONENT_ITEM_NAME \n");

		sql.append("           , '' AS STATUSKR	\n");
		sql.append("           , (SELECT decode(MAX(" + PartSpecEnum.SpPartType.getColumnName()
		        + "), 'D', 'Die No', 'M', '금형부품', 'F', '제품', 'H', '반제품', 'R', '원자재', 'K', '포장재', 'S', '스크랩', 'W', '상품','제품')" + " FROM WTPART "
		        + "WHERE IDA3MASTERREFERENCE = B0.IDA2A2) AS PART_TYPE         	\n");
		sql.append("           , (SELECT MAX(" + PartSpecEnum.SpPartType.getColumnName() + ") FROM WTPART " + "WHERE IDA3MASTERREFERENCE = B0.IDA2A2) AS PART_TYPE_CODE \n\n");

		sql.append("           , 'N' AS IS_NEW_ITEM \n");

		sql.append("	    FROM WTPARTMASTER B0 \n");
		sql.append("	   WHERE B0.WTPARTNUMBER = ? \n");
		sql.append("	  UNION ALL \n");

		sql.append("  SELECT * FROM ( \n");

		sql.append("   SELECT DISTINCT SUBSTR (REGEXP_SUBSTR (LEVEL_PATH,'/[^/]*',1	,1),2) AS PRODUCT_CODE				 \n");
		sql.append("                  ,ASSEMBLY_ITEM_OID										 \n");
		sql.append("                  ,ASSEMBLY_ITEM_CODE										 \n");
		sql.append("                  ,ASSEMBLY_ITEM_REV										 \n");
		sql.append("                  ,COMPONENT_ITEM_OID										 \n");
		sql.append("                  ,COMPONENT_ITEM_CODE										 \n");
		sql.append("                  ,COMPONENT_ITEM_REV										 \n");
		sql.append("                  ,COMPONENT_ITEM_NAME										 \n");
		sql.append("                  ,STATUSKR											 \n");
		sql.append("                  ,PART_TYPE											 \n");
		sql.append("                  ,PART_TYPE_CODE										 \n");
		sql.append("                  ,(SELECT DECODE(COUNT(*),0,'Y','N') FROM MOLDITEMINFO WHERE PARTNO=COMPONENT_ITEM_CODE) AS IS_NEW_ITEM \n");
		sql.append("     FROM (SELECT BOM.*												 \n");
		sql.append("                 ,PH.NAME STATUSKR										 \n");
		sql.append("                 ,NVL (HD.HITEMKEY, '') AS HEADERKEY								 \n");
		sql.append("                 ,NVL (HD.ECOHEADERNUMBER, '') AS EONO								 \n");
		sql.append("                 ,NVL (C0.CHECKOUTERID, '') AS COUTER								 \n");
		sql.append("             FROM (           SELECT ROWNUM AS NUM								 \n");
		sql.append("                                    ,LEVEL AS LVL								 \n");
		sql.append("                                    ,SYS_CONNECT_BY_PATH (X0.PARENTITEMCODE, '/') LEVEL_PATH			 \n");
		sql.append("                                    ,X0.ITEMSEQ									 \n");
		sql.append("                                    ,X0.IDA3A5 AS ASSEMBLY_ITEM_OID						 \n");
		sql.append("                                    ,X0.PARENTITEMCODE AS ASSEMBLY_ITEM_CODE					 \n");
		sql.append("                                    ,(SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5) AS ASSEMBLY_ITEM_REV						 \n");
		sql.append("                                    ,X0.VERSIONITEMCODE AS COMPONENT_ITEM_OID					 \n");
		sql.append("                                    ,X0.CHILDITEMCODE AS COMPONENT_ITEM_CODE					 \n");
		sql.append("                                    , (SELECT NAME								 \n");
		sql.append("                                         FROM WTPARTMASTER							 \n");
		sql.append("                                        WHERE IDA2A2 = (SELECT IDA3MASTERREFERENCE				 \n");
		sql.append("                                                          FROM WTPART						 \n");
		sql.append("                                                         WHERE IDA2A2 = X0.VERSIONITEMCODE))			 \n");
		sql.append("                                         AS COMPONENT_ITEM_NAME							 \n");
		sql.append("                                    ,X0.BOMVERSION AS COMPONENT_ITEM_REV					 \n");
		sql.append("                                    ,X0.QUANTITY AS QTY								 \n");
		sql.append("                                    , (SELECT STATESTATE							 \n");
		sql.append("                                         FROM WTPART								 \n");
		sql.append("                                        WHERE IDA2A2 = X0.VERSIONITEMCODE)					 \n");
		sql.append("                                         AS STATUS								 \n");
		sql.append("                                    , (SELECT IDA3A2STATE							 \n");
		sql.append("                                         FROM WTPART								 \n");
		sql.append("                                        WHERE IDA2A2 = X0.VERSIONITEMCODE)					 \n");
		sql.append("                                         AS IDA3A2STATE								 \n");
		sql.append("                                    , (SELECT decode(" + PartSpecEnum.SpPartType.getColumnName()
		        + ", 'D', 'Die No', 'M', '금형부품', 'F', '제품', 'H', '반제품', 'R', '원자재', 'K', '포장재', 'S', '스크랩', 'W', '상품','제품')" + " FROM WTPART "
		        + "WHERE IDA2A2 = X0.VERSIONITEMCODE) AS PART_TYPE         	\n");
		sql.append("                                    , (SELECT " + PartSpecEnum.SpPartType.getColumnName() + " FROM WTPART " + "WHERE IDA2A2 = X0.VERSIONITEMCODE) AS PART_TYPE_CODE \n\n");
		sql.append("                                    ,X0.UNIT									 \n");
		sql.append("                                    ,X0.IDA2A2									 \n");
		sql.append("                                    ,X0.STARTDATE								 \n");
		sql.append("                                    ,X0.ENDDATE									 \n");
		sql.append("                                    ,X0.MATERIAL								 \n");
		sql.append("                                    ,X0.HARDNESSFROM								 \n");
		sql.append("                                    ,X0.HARDNESSTO								 \n");
		sql.append("                                    ,X0.DESIGNDATE								 \n");
		sql.append("                                    ,X0.ICT AS ICT							 	 \n");
		sql.append("                                    ,X0.REFTOP AS REFTOP							 \n");
		sql.append("                                    ,X0.REFBOTTOM AS REFBOTTOM							 \n");
		sql.append("                                    ,X0.IDA2A2 AS USAGELINKOID							 \n");
		sql.append("                                    ,(X0.CHILDITEMCODE || '_' || X0.CHILDITEMVERSION) AS ITEMINFO		 \n");
		sql.append("                                    ,(X0.PARENTITEMCODE || '_' || (SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5)) AS PARENTITEMINFO		 \n");
		sql.append("                                FROM KETPARTUSAGELINK X0							 \n");
		sql.append("                          START WITH X0.IDA3A5 IN (SELECT MAX(A1.IDA2A2) AS IDA2A2 \n");
		sql.append("                                    FROM WTPARTMASTER A0, WTPART A1\n");
		sql.append("                                    WHERE A0.IDA2A2 = A1.IDA3MASTERREFERENCE\n");
		sql.append("                                      AND A1.LATESTITERATIONINFO = '1' \n");
		sql.append("                                      AND A0.WTPARTNUMBER = ? ) \n");
		sql.append("                          CONNECT BY PRIOR (SELECT MAX (B.IDA3A5)						 \n");
		sql.append("                                              FROM KETPARTUSAGELINK B						 \n");
		sql.append("                                             WHERE B.PARENTITEMCODE = X0.CHILDITEMCODE) = X0.IDA3A5		 \n");
		sql.append("                   ORDER SIBLINGS BY X0.ITEMSEQ) BOM								 \n");
		sql.append("                 ,PHASETEMPLATE PH										 \n");
		sql.append("                 ,PHASELINK PL											 \n");
		sql.append("                 ,(SELECT (ITEMCODE || '_' || ITEMVERSION) AS ITEMKEY, CHECKOUTERID FROM KETBOMCHECKOUT) C0	 \n");
		sql.append("                 ,(SELECT ECOHEADERNUMBER, (PARENTITEMCODE || '_' || BOMVERSION || '_' || CHILDITEMCODE) AS HITEMKEY \n");
		sql.append("                     FROM KETBOMECOCOMPONENT									 \n");
		sql.append("                    WHERE ECOCODE IS NOT NULL) HD								 \n");
		sql.append("            WHERE PL.IDA3A5 = BOM.IDA3A2STATE									 \n");
		sql.append("              AND PL.IDA3B5 = PH.IDA2A2										 \n");
		sql.append("              AND PH.PHASESTATE = BOM.STATUS									 \n");
		sql.append("              AND BOM.ITEMINFO = C0.ITEMKEY(+)									 \n");
		sql.append("              AND (BOM.PARENTITEMINFO || '_' || BOM.COMPONENT_ITEM_CODE) = HD.HITEMKEY(+))			 \n");

		sql.append("  ) \n");

	    }

	    sql.append("       ) \n");
	    sql.append(" ORDER BY PRODUCT_CODE, COMPONENT_ITEM_CODE									 \n");
	    Kogger.debug(getClass(), sql.toString());

	    pstmt = conn.prepareStatement(sql.toString());

	    int idx = 1;
	    for (int i = 0; i < productPartNoList.size(); i++) {
		pstmt.setString(idx++, CommonUtil.getOIDLongValue2Str(productPartNoList.get(i)));
		pstmt.setString(idx++, CommonUtil.getOIDLongValue2Str(productPartNoList.get(i)));
	    }
	    rs = pstmt.executeQuery();

	    String oid = "";
	    String productCode = "";
	    String partNo = "";
	    String rev = "";
	    String partName = "";
	    String state = "";
	    String parentNo = "";
	    String partType = "";
	    String partTypeCode = "";
	    String isNewItem = "";
	    int i = 0;

	    int old_lvl = 0;

	    while (rs.next()) {

		Map<String, Object> data = new Hashtable();
		oid = rs.getString("COMPONENT_ITEM_OID") == null ? "" : rs.getString("COMPONENT_ITEM_OID").trim();
		productCode = rs.getString("PRODUCT_CODE") == null ? "" : rs.getString("PRODUCT_CODE").trim();
		partNo = rs.getString("COMPONENT_ITEM_CODE") == null ? "" : rs.getString("COMPONENT_ITEM_CODE").trim();
		rev = rs.getString("COMPONENT_ITEM_REV") == null ? "" : rs.getString("COMPONENT_ITEM_REV").trim();
		partName = rs.getString("COMPONENT_ITEM_NAME") == null ? "" : rs.getString("COMPONENT_ITEM_NAME").trim();
		state = rs.getString("STATUSKR") == null ? "" : rs.getString("STATUSKR").trim();
		parentNo = rs.getString("ASSEMBLY_ITEM_CODE") == null ? "" : rs.getString("ASSEMBLY_ITEM_CODE").trim();
		partType = rs.getString("PART_TYPE") == null ? "" : rs.getString("PART_TYPE").trim();
		partTypeCode = rs.getString("PART_TYPE_CODE") == null ? "" : rs.getString("PART_TYPE_CODE").trim();
		isNewItem = rs.getString("IS_NEW_ITEM") == null ? "" : rs.getString("IS_NEW_ITEM").trim();

		data.put("productCode", productCode);
		data.put("parentNo", parentNo);
		data.put("partType", partType);
		data.put("partTypeCode", partTypeCode);
		data.put("isNewItem", isNewItem);

		// 최신 리비전
		Versioned versioned = (Versioned) CommonUtil.getObject("wt.part.WTPart:" + oid);
		WTPart latestWTPart = (WTPart) VersionHelper.getLatestRevision(versioned);
		String latestWTPartNumber = latestWTPart.getNumber();
		String latestWTPartRevision = latestWTPart.getVersionInfo().getIdentifier().getValue();

		data.put("oid", CommonUtil.getOIDString(latestWTPart));
		data.put("partNo", latestWTPartNumber);
		data.put("rev", latestWTPartRevision);

		String revDis = StringUtils.stripToEmpty(PartSpecGetter.getPartSpec(latestWTPart, PartSpecEnum.SpPartRevision));
		data.put("revDis", revDis);

		data.put("partName", latestWTPart.getName());
		data.put("state", latestWTPart.getLifeCycleState().getDisplay());

		bomList.add(data);
	    }
	} catch (Exception e) {
	    try {
		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }
	}

	return bomList;
    }

    public String getLatestReleasedPart(String partNumber, WTConnection conn) throws Exception {
	String oid = "";

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    StringBuffer sql = new StringBuffer();

	    // ArrayList revList = new ArrayList();

	    // sql.append("   SELECT (CLASSNAMEA2A2||':'||IDA2A2) AS OID  FROM WTPART WHERE IDA2A2=    	");
	    // sql.append("   (SELECT MAX(B0.IDA2A2)  FROM WTPARTMASTER A0, WTPART B0    			");
	    // sql.append("    WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE 					");
	    // sql.append("      AND A0.WTPARTNUMBER='" + partNumber + "' 					");
	    // sql.append("      AND B0.LATESTITERATIONINFO=1 AND B0.STATESTATE='APPROVED')		");

	    sql.append("   SELECT (C0.CLASSNAMEA2A2||':'||C0.IDA2A2) AS OID FROM			");
	    sql.append("      (SELECT  B0.*  FROM WTPARTMASTER A0, WTPART B0				");
	    sql.append("        WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE				");
	    sql.append("          AND A0.WTPARTNUMBER='" + partNumber + "' 				");
	    sql.append("          AND B0.LATESTITERATIONINFO=1 AND B0.STATESTATE='APPROVED' 		");
	    sql.append("     ORDER BY B0.VERSIONSORTIDA2VERSIONINFO DESC) C0				");
	    sql.append("   WHERE ROWNUM=1  								");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
		// Kogger.debug(getClass(), oid);

	    }

	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return oid;
    }

    public String getLatestReleasedPart2(String partNumber) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	// ArrayList revList = new ArrayList();
	String oid = "";

	try {

	    // sql.append("   SELECT (CLASSNAMEA2A2||':'||IDA2A2) AS OID  FROM WTPART WHERE IDA2A2=    	");
	    // sql.append("   (SELECT MAX(B0.IDA2A2)  FROM WTPARTMASTER A0, WTPART B0    			");
	    // sql.append("    WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE 					");
	    // sql.append("      AND A0.WTPARTNUMBER='" + partNumber + "' 					");
	    // sql.append("      AND B0.LATESTITERATIONINFO=1 AND B0.STATESTATE='APPROVED')		");

	    sql.append("   SELECT (C0.CLASSNAMEA2A2||':'||C0.IDA2A2) AS OID FROM			");
	    sql.append("      (SELECT  B0.*  FROM WTPARTMASTER A0, WTPART B0				");
	    sql.append("        WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE				");
	    sql.append("          AND A0.WTPARTNUMBER='" + partNumber + "' 				");
	    sql.append("          AND B0.LATESTITERATIONINFO=1 AND B0.STATESTATE='APPROVED' 		");
	    sql.append("     ORDER BY B0.VERSIONSORTIDA2VERSIONINFO DESC) C0				");
	    sql.append("   WHERE ROWNUM=1  								");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
		// Kogger.debug(getClass(), oid);

	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return oid;
    }

    public String getLatestPart(String partNumber) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	// ArrayList revList = new ArrayList();
	String oid = "";

	try {

	    sql.append("   SELECT (C0.CLASSNAMEA2A2||':'||C0.IDA2A2) AS OID FROM			");
	    sql.append("      (SELECT  B0.*  FROM WTPARTMASTER A0, WTPART B0				");
	    sql.append("        WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE				");
	    sql.append("          AND A0.WTPARTNUMBER='" + partNumber + "' 				");
	    sql.append("          AND B0.LATESTITERATIONINFO=1  					");
	    sql.append("     ORDER BY B0.VERSIONSORTIDA2VERSIONINFO DESC) C0				");
	    sql.append("   WHERE ROWNUM=1  								");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
		Kogger.debug(getClass(), oid);

	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return oid;
    }

    public String getLatestPart2(String partNumber, WTConnection conn) throws Exception {
	String oid = "";

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {

	    StringBuffer sql = new StringBuffer();

	    // ArrayList revList = new ArrayList();

	    sql.append("   SELECT (C0.CLASSNAMEA2A2||':'||C0.IDA2A2) AS OID FROM			");
	    sql.append("      (SELECT  B0.*  FROM WTPARTMASTER A0, WTPART B0				");
	    sql.append("        WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE				");
	    sql.append("          AND A0.WTPARTNUMBER='" + partNumber + "' 				");
	    sql.append("          AND B0.LATESTITERATIONINFO=1  					");
	    sql.append("     ORDER BY B0.VERSIONSORTIDA2VERSIONINFO DESC) C0				");
	    sql.append("   WHERE ROWNUM=1  								");

	    Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs != null && rs.next()) {
		oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
		Kogger.debug(getClass(), oid);

	    }

	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return oid;
    }

    public String getLatestPartIDA2A2(String partNumber) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	// ArrayList revList = new ArrayList();
	String oid = "";

	try {

	    sql.append("   SELECT C0.IDA2A2 AS OID FROM			");
	    sql.append("      (SELECT  B0.*  FROM WTPARTMASTER A0, WTPART B0				");
	    sql.append("        WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE				");
	    sql.append("          AND A0.WTPARTNUMBER='" + partNumber + "' 				");
	    sql.append("          AND B0.LATESTITERATIONINFO=1  					");
	    sql.append("     ORDER BY B0.VERSIONSORTIDA2VERSIONINFO DESC) C0				");
	    sql.append("   WHERE ROWNUM=1  								");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
		// Kogger.debug(getClass(), oid);

	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return oid;
    }

    public String getLatestPartIDA2A2(String partNumber, WTConnection conn) throws Exception {
	String oid = "";

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    StringBuffer sql = new StringBuffer();

	    // ArrayList revList = new ArrayList();

	    sql.append("   SELECT C0.IDA2A2 AS OID FROM			");
	    sql.append("      (SELECT  B0.*  FROM WTPARTMASTER A0, WTPART B0				");
	    sql.append("        WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE				");
	    sql.append("          AND A0.WTPARTNUMBER='" + partNumber + "' 				");
	    sql.append("          AND B0.LATESTITERATIONINFO=1  					");
	    sql.append("     ORDER BY B0.VERSIONSORTIDA2VERSIONINFO DESC) C0				");
	    sql.append("   WHERE ROWNUM=1  								");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
		// Kogger.debug(getClass(), oid);

	    }
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return oid;
    }

    public String getLatestPart(String partNumber, WTConnection conn) throws Exception {
	String oid = "";

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    StringBuffer sql = new StringBuffer();

	    // ArrayList revList = new ArrayList();

	    sql.append("   SELECT (C0.CLASSNAMEA2A2||':'||C0.IDA2A2) AS OID FROM			");
	    sql.append("      (SELECT  B0.*  FROM WTPARTMASTER A0, WTPART B0				");
	    sql.append("        WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE				");
	    sql.append("          AND A0.WTPARTNUMBER='" + partNumber + "' 				");
	    sql.append("          AND B0.LATESTITERATIONINFO=1  					");
	    sql.append("     ORDER BY B0.VERSIONSORTIDA2VERSIONINFO DESC) C0				");
	    sql.append("   WHERE ROWNUM=1  								");

	    Kogger.debug(getClass(), "+++++++++++SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		oid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
		// Kogger.debug(getClass(), oid);

	    }

	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return oid;
    }

    public String getLatestPartRev(String partNumber, WTConnection conn) throws Exception {
	String rev = "";

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    StringBuffer sql = new StringBuffer();

	    // ArrayList revList = new ArrayList();

	    // sql.append("   SELECT (CLASSNAMEA2A2||':'||IDA2A2) AS OID  FROM WTPART WHERE IDA2A2=    	");
	    // sql.append("   (SELECT MAX(B0.IDA2A2)  FROM WTPARTMASTER A0, WTPART B0    			");
	    // sql.append("    WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE 					");
	    // sql.append("      AND A0.WTPARTNUMBER='" + partNumber + "' 					");
	    // sql.append("      AND B0.LATESTITERATIONINFO=1 )		");

	    sql.append("   SELECT C0.VERSIONIDA2VERSIONINFO AS REV FROM					");
	    sql.append("      (SELECT  B0.*  FROM WTPARTMASTER A0, WTPART B0				");
	    sql.append("        WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE				");
	    sql.append("          AND A0.WTPARTNUMBER='" + partNumber + "' 				");
	    sql.append("          AND B0.LATESTITERATIONINFO=1 				 		");
	    sql.append("     ORDER BY B0.VERSIONSORTIDA2VERSIONINFO DESC) C0				");
	    sql.append("   WHERE ROWNUM=1  								");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		rev = rs.getString("REV") == null ? "" : rs.getString("REV").trim();
		// Kogger.debug(getClass(), oid);

	    }

	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return rev;
    }

    public String getLatestPartRev(String partNumber) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	// ArrayList revList = new ArrayList();
	String rev = "";

	try {

	    // sql.append("   SELECT (CLASSNAMEA2A2||':'||IDA2A2) AS OID  FROM WTPART WHERE IDA2A2=    	");
	    // sql.append("   (SELECT MAX(B0.IDA2A2)  FROM WTPARTMASTER A0, WTPART B0    			");
	    // sql.append("    WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE 					");
	    // sql.append("      AND A0.WTPARTNUMBER='" + partNumber + "' 					");
	    // sql.append("      AND B0.LATESTITERATIONINFO=1 )		");

	    sql.append("   SELECT C0.VERSIONIDA2VERSIONINFO AS REV FROM					");
	    sql.append("      (SELECT  B0.*  FROM WTPARTMASTER A0, WTPART B0				");
	    sql.append("        WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE				");
	    sql.append("          AND A0.WTPARTNUMBER='" + partNumber + "' 				");
	    sql.append("          AND B0.LATESTITERATIONINFO=1 				 		");
	    sql.append("     ORDER BY B0.VERSIONSORTIDA2VERSIONINFO DESC) C0				");
	    sql.append("   WHERE ROWNUM=1  								");

	    Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		rev = rs.getString("REV") == null ? "" : rs.getString("REV").trim();
		// Kogger.debug(getClass(), oid);

	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return rev;
    }

    public String getLatestPartRev2(String partNumber) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	// ArrayList revList = new ArrayList();
	String rev = "";

	try {

	    // sql.append("   SELECT (CLASSNAMEA2A2||':'||IDA2A2) AS OID  FROM WTPART WHERE IDA2A2=    	");
	    // sql.append("   (SELECT MAX(B0.IDA2A2)  FROM WTPARTMASTER A0, WTPART B0    			");
	    // sql.append("    WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE 					");
	    // sql.append("      AND A0.WTPARTNUMBER='" + partNumber + "' 					");
	    // sql.append("      AND B0.LATESTITERATIONINFO=1 )		");

	    sql.append("   SELECT C0.VERSIONIDA2VERSIONINFO AS REV FROM					");
	    sql.append("      (SELECT  B0.*  FROM WTPARTMASTER A0, WTPART B0				");
	    sql.append("        WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE				");
	    sql.append("          AND A0.WTPARTNUMBER='" + partNumber + "' 				");
	    sql.append("          AND B0.LATESTITERATIONINFO=1 				 		");
	    sql.append("     ORDER BY B0.VERSIONSORTIDA2VERSIONINFO DESC) C0				");
	    sql.append("   WHERE ROWNUM=1  								");

	    Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		rev = rs.getString("REV") == null ? "" : rs.getString("REV").trim();
		// Kogger.debug(getClass(), oid);

	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return rev;
    }

    public ArrayList getPartRevList(String partNumber, String partRev) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	ArrayList revList = new ArrayList();
	String partNo = "";
	String partName = "";
	String partHName = "";
	String partOid = "";
	String partVersion = "";
	String partNewVersion = "";

	try {

	    sql.append("SELECT A0.WTPARTNUMBER AS PARTNUMBER ,A0.NAME AS PARTNAME, B0." + PartSpecEnum.SpPartNameHis.getColumnName() + " AS HPARTNAME , 	\n");
	    sql.append("       (B0.CLASSNAMEA2A2 || ':' || B0.IDA2A2) AS OID, B0.VERSIONIDA2VERSIONINFO AS REV, 		\n");
	    sql.append("       B0." + PartSpecEnum.SpPartRevision.getColumnName() + " AS NEWREV, B0.VERSIONSORTIDA2VERSIONINFO AS SORTVER  			\n");
	    sql.append("  FROM WTPARTMASTER A0, WTPART B0  									\n");
	    sql.append(" WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE AND B0.LATESTITERATIONINFO=1  				\n");
	    sql.append("   AND A0.WTPARTNUMBER='" + partNumber + "' 								\n");
	    if (partRev != null && !partRev.equals(""))
		sql.append("   AND B0.VERSIONIDA2VERSIONINFO='" + partRev + "'   						\n");
	    sql.append(" ORDER BY B0.VERSIONSORTIDA2VERSIONINFO DESC 								\n");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {

		partNo = rs.getString("PARTNUMBER") == null ? "" : rs.getString("PARTNUMBER").trim();
		partName = rs.getString("PARTNAME") == null ? "" : rs.getString("PARTNAME").trim();
		partHName = rs.getString("HPARTNAME") == null ? "" : rs.getString("HPARTNAME").trim();
		partOid = rs.getString("OID") == null ? "" : rs.getString("OID").trim();
		partVersion = rs.getString("REV") == null ? "" : rs.getString("REV").trim();
		partNewVersion = rs.getString("NEWREV") == null ? "" : rs.getString("NEWREV").trim();

		Hashtable data = new Hashtable();
		data.put("partNo", partNo);
		data.put("partName", partName);
		data.put("partHName", partHName);
		data.put("partOid", partOid);
		data.put("rev", partVersion);
		data.put("newrev", partNewVersion);

		revList.add(data);

		// Kogger.debug(getClass(), oid);

	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return revList;
    }

    public ArrayList getECOProductList(String ecoNumber, WTConnection conn) throws Exception {
	ArrayList prodList = new ArrayList();

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    StringBuffer sql = new StringBuffer();

	    String partNo = "";
	    String longId = "";

	    sql.append("SELECT A0.WTPARTNUMBER AS PARTNUMBER, D0.IDA3A5	AS LONGID				\n");
	    sql.append("  FROM WTPARTMASTER A0, WTPART B0 , KETPRODCHANGEORDER C0, KETPRODECOPARTLINK D0 	\n");
	    sql.append(" WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE 						\n");
	    sql.append("   AND B0.IDA2A2=D0.IDA3A5 AND C0.IDA2A2=D0.IDA3B5 					\n");
	    sql.append("   AND C0.ECOID='" + ecoNumber + "'								\n");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {

		partNo = rs.getString("PARTNUMBER") == null ? "" : rs.getString("PARTNUMBER").trim();
		longId = rs.getString("LONGID") == null ? "" : rs.getString("LONGID").trim();

		Hashtable data = new Hashtable();
		data.put("partNo", partNo);
		data.put("longId", longId);

		prodList.add(data);

		// Kogger.debug(getClass(), oid);

	    }
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return prodList;
    }

    public Hashtable getUsageInfoList(String ecoNumber, WTConnection conn) throws Exception {
	Hashtable dataH = new Hashtable();

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    ArrayList prodList = new ArrayList();
	    StringBuffer sql;

	    String prodNumber = "";
	    String prodOid = "";

	    prodList = getECOProductList(ecoNumber, conn);

	    if (prodList != null) {
		for (int i = 0; i < prodList.size(); i++) {
		    Hashtable prodH = (Hashtable) prodList.get(i);

		    prodNumber = (String) prodH.get("partNo");
		    prodOid = (String) prodH.get("longId");
		    sql = new StringBuffer();

		    sql.append("	SELECT	ROWNUM AS NUM, LEVEL AS LVL, X0.ITEMSEQ, 		\n");
		    sql.append("		X0.PARENTITEMCODE AS ASSEMBLY_ITEM_CODE, 		\n");
		    // sql.append("		X0.VERSIONITEMCODE AS COMPONENT_ITEM_OID,  		\n");
		    sql.append("		X0.CHILDITEMCODE AS COMPONENT_ITEM_CODE	 		\n");
		    sql.append("	  FROM  KETPARTUSAGELINK X0                          		\n");
		    sql.append("    START WITH  X0.IDA3A5 = '" + prodOid + "'             			\n");
		    sql.append("    CONNECT BY  PRIOR (                                   	 	\n");
		    sql.append("			  SELECT MAX(B.IDA3A5)                    	\n");
		    sql.append("			    FROM KETPARTUSAGELINK B           		\n");
		    sql.append("		 	   WHERE B.PARENTITEMCODE = X0.CHILDITEMCODE    \n");
		    sql.append("		      ) = X0.IDA3A5       				\n");
		    sql.append("    ORDER SIBLINGS BY  X0.ITEMSEQ     					\n");

		    pstmt = conn.prepareStatement(sql.toString());
		    // pstmt.setString(1, partNumber);
		    rs = pstmt.executeQuery();

		    while (rs.next()) {

			String parentPartNo = rs.getString("ASSEMBLY_ITEM_CODE") == null ? "" : rs.getString("ASSEMBLY_ITEM_CODE").trim();
			String partNo = rs.getString("COMPONENT_ITEM_CODE") == null ? "" : rs.getString("COMPONENT_ITEM_CODE").trim();
			String lvl = rs.getString("LVL") == null ? "" : rs.getString("LVL").trim();
			String num = rs.getString("NUM") == null ? "" : rs.getString("NUM").trim();

			String partKey = parentPartNo + "^" + partNo;

			Hashtable partH = new Hashtable();
			partH.put("appliedProd", prodNumber);
			partH.put("lvl", lvl);
			partH.put("num", num);

			dataH.put(partKey, partH);

		    }

		}
	    }
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return dataH;
    }

    public Hashtable getEcoHeaderCheckList(String ecoNumber, WTConnection conn) throws Exception {
	Hashtable dataH = new Hashtable();

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    ArrayList prodList = new ArrayList();
	    StringBuffer sql;

	    String prodNumber = "";
	    String prodOid = "";

	    prodList = getECOProductList(ecoNumber, conn);

	    ArrayList headerItem = getECOBomHeaderItem2(ecoNumber, conn);

	    Hashtable ecoitemHash = new Hashtable();
	    // Hashtable bomPathHash = new Hashtable();
	    String ecoItemcode = "";

	    if (headerItem != null) {
		for (int i = 0; i < headerItem.size(); i++) {
		    Hashtable data = (Hashtable) headerItem.get(i);

		    String partNo = (String) data.get("partNo");
		    String revYN = (String) data.get("reviseYN");

		    if (revYN.equals("Y"))
			ecoitemHash.put(partNo, "");
		}
	    }

	    if (prodList != null) {

		for (int i = 0; i < prodList.size(); i++) {
		    Hashtable prodH = (Hashtable) prodList.get(i);

		    prodNumber = (String) prodH.get("partNo");
		    prodOid = (String) prodH.get("longId");
		    sql = new StringBuffer();

		    sql.append("	SELECT	ROWNUM AS NUM, LEVEL AS LVL, X0.ITEMSEQ, 		\n");
		    sql.append("		X0.PARENTITEMCODE AS ASSEMBLY_ITEM_CODE, 		\n");
		    // sql.append("		X0.VERSIONITEMCODE AS COMPONENT_ITEM_OID,  		\n");
		    sql.append("		X0.CHILDITEMCODE AS COMPONENT_ITEM_CODE	 		\n");
		    sql.append("	  FROM  KETPARTUSAGELINK X0                          		\n");
		    sql.append("    START WITH  X0.IDA3A5 = '" + prodOid + "'             			\n");
		    sql.append("    CONNECT BY  PRIOR (                                   	 	\n");
		    sql.append("			  SELECT MAX(B.IDA3A5)                    	\n");
		    sql.append("			    FROM KETPARTUSAGELINK B           		\n");
		    sql.append("		 	   WHERE B.PARENTITEMCODE = X0.CHILDITEMCODE    \n");
		    sql.append("		      ) = X0.IDA3A5       				\n");
		    sql.append("    ORDER SIBLINGS BY  X0.ITEMSEQ     					\n");

		    pstmt = conn.prepareStatement(sql.toString());
		    // pstmt.setString(1, partNumber);
		    rs = pstmt.executeQuery();

		    String bom_path = "";
		    int old_lvl = 0;

		    while (rs.next()) {

			String parentPartNo = rs.getString("ASSEMBLY_ITEM_CODE") == null ? "" : rs.getString("ASSEMBLY_ITEM_CODE").trim();
			String partNo = rs.getString("COMPONENT_ITEM_CODE") == null ? "" : rs.getString("COMPONENT_ITEM_CODE").trim();
			String lvl = rs.getString("LVL") == null ? "" : rs.getString("LVL").trim();
			String num = rs.getString("NUM") == null ? "" : rs.getString("NUM").trim();

			if (ecoitemHash.containsKey(parentPartNo)) {
			    ecoItemcode = parentPartNo;
			}

			int new_lvl = Integer.parseInt(lvl);

			if (new_lvl > old_lvl) {
			    bom_path += lvl + "^" + parentPartNo + "|";

			} else {
			    if (new_lvl < old_lvl)
				bom_path = bom_path.substring(0, bom_path.indexOf(lvl + "^" + parentPartNo + "|")) + lvl + "^" + parentPartNo + "|";
			}

			String partKey = parentPartNo + "^" + partNo;

			Hashtable partH = new Hashtable();
			partH.put("appliedProd", prodNumber);
			partH.put("lvl", lvl);
			partH.put("num", num);
			partH.put("bom_path", bom_path);
			partH.put("ecoitemcode", ecoItemcode);

			dataH.put(partKey, partH);
			old_lvl = new_lvl;

		    }
		    
		    if (rs != null) {
			rs.close();
			rs = null;
		    }
		    if (pstmt != null) {
			pstmt.close();
			pstmt = null;
		    }

		}
	    }
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException se2) {
	    }// nothing we can do

	}

	return dataH;
    }

    public ArrayList getECOProductList2(String ecoNumber) throws Exception {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	ArrayList prodList = new ArrayList();
	String partNo = "";
	String longId = "";

	try {

	    sql.append("SELECT A0.WTPARTNUMBER AS PARTNUMBER, D0.IDA3A5	AS LONGID				\n");
	    sql.append("  FROM WTPARTMASTER A0, WTPART B0 , KETPRODCHANGEORDER C0, KETPRODECOPARTLINK D0 	\n");
	    sql.append(" WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE 						\n");
	    sql.append("   AND B0.IDA2A2=D0.IDA3A5 AND C0.IDA2A2=D0.IDA3B5 					\n");
	    sql.append("   AND C0.ECOID='" + ecoNumber + "'								\n");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {

		partNo = rs.getString("PARTNUMBER") == null ? "" : rs.getString("PARTNUMBER").trim();
		longId = rs.getString("LONGID") == null ? "" : rs.getString("LONGID").trim();

		Hashtable data = new Hashtable();
		data.put("partNo", partNo);
		data.put("longId", longId);

		prodList.add(data);

		// Kogger.debug(getClass(), oid);

	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return prodList;
    }

    public Hashtable getUsageInfoList2(String ecoNumber) throws Exception {

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	Hashtable dataH = new Hashtable();
	ArrayList prodList = new ArrayList();
	StringBuffer sql;

	String prodNumber = "";
	String prodOid = "";
	try {
	    prodList = getECOProductList2(ecoNumber);

	    if (prodList != null) {
		for (int i = 0; i < prodList.size(); i++) {
		    Hashtable prodH = (Hashtable) prodList.get(i);

		    prodNumber = (String) prodH.get("partNo");
		    prodOid = (String) prodH.get("longId");
		    sql = new StringBuffer();

		    sql.append("	SELECT	ROWNUM AS NUM, LEVEL AS LVL, X0.ITEMSEQ, 		\n");
		    sql.append("		X0.PARENTITEMCODE AS ASSEMBLY_ITEM_CODE, 		\n");
		    // sql.append("		X0.VERSIONITEMCODE AS COMPONENT_ITEM_OID,  		\n");
		    sql.append("		X0.CHILDITEMCODE AS COMPONENT_ITEM_CODE	 		\n");
		    sql.append("	  FROM  KETPARTUSAGELINK X0                          		\n");
		    sql.append("    START WITH  X0.IDA3A5 = '" + prodOid + "'             			\n");
		    sql.append("    CONNECT BY  PRIOR (                                   	 	\n");
		    sql.append("			  SELECT MAX(B.IDA3A5)                    	\n");
		    sql.append("			    FROM KETPARTUSAGELINK B           		\n");
		    sql.append("		 	   WHERE B.PARENTITEMCODE = X0.CHILDITEMCODE    \n");
		    sql.append("		      ) = X0.IDA3A5       				\n");
		    sql.append("    ORDER SIBLINGS BY  X0.ITEMSEQ     					\n");

		    pstmt = conn.prepareStatement(sql.toString());
		    // pstmt.setString(1, partNumber);
		    rs = pstmt.executeQuery();

		    while (rs.next()) {

			String parentPartNo = rs.getString("ASSEMBLY_ITEM_CODE") == null ? "" : rs.getString("ASSEMBLY_ITEM_CODE").trim();
			String partNo = rs.getString("COMPONENT_ITEM_CODE") == null ? "" : rs.getString("COMPONENT_ITEM_CODE").trim();
			String lvl = rs.getString("LVL") == null ? "" : rs.getString("LVL").trim();
			String num = rs.getString("NUM") == null ? "" : rs.getString("NUM").trim();

			String partKey = parentPartNo + "^" + partNo;

			Hashtable partH = new Hashtable();
			partH.put("appliedProd", prodNumber);
			partH.put("lvl", lvl);
			partH.put("num", num);

			dataH.put(partKey, partH);

		    }

		}
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}
	return dataH;
    }

    public Hashtable getUsageInfoList3(String ecoNumber) throws Exception {

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	Hashtable dataH = new Hashtable();
	ArrayList prodList = new ArrayList();
	StringBuffer sql;

	String prodNumber = "";
	String prodOid = "";
	try {
	    prodList = getECOProductList2(ecoNumber);

	    if (prodList != null) {
		for (int i = 0; i < prodList.size(); i++) {
		    Hashtable prodH = (Hashtable) prodList.get(i);

		    prodNumber = (String) prodH.get("partNo");
		    prodOid = (String) prodH.get("longId");

		    Hashtable rootH = new Hashtable();
		    rootH.put("appliedProd", prodNumber);
		    rootH.put("lvl", "0");
		    rootH.put("num", "0");

		    dataH.put(prodNumber, rootH);

		    sql = new StringBuffer();

		    sql.append("	SELECT	ROWNUM AS NUM, LEVEL AS LVL, X0.ITEMSEQ, 		\n");
		    sql.append("		X0.PARENTITEMCODE AS ASSEMBLY_ITEM_CODE, 		\n");
		    // sql.append("		X0.VERSIONITEMCODE AS COMPONENT_ITEM_OID,  		\n");
		    sql.append("		X0.CHILDITEMCODE AS COMPONENT_ITEM_CODE	 		\n");
		    sql.append("	  FROM  KETPARTUSAGELINK X0                          		\n");
		    sql.append("    START WITH  X0.IDA3A5 = '" + prodOid + "'             			\n");
		    sql.append("    CONNECT BY  PRIOR (                                   	 	\n");
		    sql.append("			  SELECT MAX(B.IDA3A5)                    	\n");
		    sql.append("			    FROM KETPARTUSAGELINK B           		\n");
		    sql.append("		 	   WHERE B.PARENTITEMCODE = X0.CHILDITEMCODE    \n");
		    sql.append("		      ) = X0.IDA3A5       				\n");
		    sql.append("    ORDER SIBLINGS BY  X0.ITEMSEQ     					\n");

		    pstmt = conn.prepareStatement(sql.toString());
		    // pstmt.setString(1, partNumber);
		    rs = pstmt.executeQuery();

		    while (rs.next()) {

			String parentPartNo = rs.getString("ASSEMBLY_ITEM_CODE") == null ? "" : rs.getString("ASSEMBLY_ITEM_CODE").trim();
			String partNo = rs.getString("COMPONENT_ITEM_CODE") == null ? "" : rs.getString("COMPONENT_ITEM_CODE").trim();
			String lvl = rs.getString("LVL") == null ? "" : rs.getString("LVL").trim();
			String num = rs.getString("NUM") == null ? "" : rs.getString("NUM").trim();

			Hashtable partH = new Hashtable();
			partH.put("appliedProd", prodNumber);
			partH.put("lvl", lvl);
			partH.put("num", num);

			dataH.put(partNo, partH);

		    }

		}
	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
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
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}
	return dataH;
    }

    public static void main(String[] args) throws Exception {
	KETBOMQueryBean bean = new KETBOMQueryBean();

	// Hashtable h = bean.getUsageInfoList2("ECO-1410-144");
	// Hashtable h2 = (Hashtable) h.get("615596-2^P10004021");

	// Kogger.debug(getClass(), h2.get("lvl"));
	// WFBomEcoPartUsageQry 에서 사용
	// bean.updateECOBaseLine("ECO-1411-291");

	// bean.compareBOM("615681-5", "3", "615681-5", "2");
	// Kogger.debug(getClass(), arr.size());

	// for (int i = 0; i < arr.size(); i++) {
	// Kogger.debug(getClass(), arr.get(i).toString());
	// }
	// bean.reviseInsertComponentData("ECO-1409-053", "wt.part.WTPart:297034810", "1");
	// bean.reviseCancelComponentData("ECO-1409-053", "wt.part.WTPart:297034810");

	// List<Map<String, Object>> componentList = bean.getEcoComponentData("ECO-1105-045");

	// Kogger.debug(getClass(), componentList.toString());

    }
}
