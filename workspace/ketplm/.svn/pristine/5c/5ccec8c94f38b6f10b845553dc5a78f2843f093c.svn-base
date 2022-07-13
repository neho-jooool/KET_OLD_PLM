package ext.ket.bom.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import wt.part.WTPart;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.Registry;
import ext.ket.bom.util.KETBomPartUtil;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.log.Kogger;

public class KETProjectBomQueryBean {

    Registry registry = Registry.getRegistry("e3ps.bom.bom");

    public KETProjectBomQueryBean() {
	// TODO Auto-generated constructor stub
    }

    public List<Map<String, String>> getUsageLinkPartList(String ida2a2, String productType, String productNumber) {
	List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();

	KETBOMQueryBean bean = new KETBOMQueryBean();
	KETBomPartUtil pUtil = new KETBomPartUtil();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	try {

	    sql.append("		SELECT DISTINCT LEVEL AS LVL,	 X0.ITEMSEQ,																		\n");
	    sql.append("		       X0.IDA3A5 AS ASSEMBLY_ITEM_OID, 																				\n");
	    sql.append("		       X0.PARENTITEMCODE AS ASSEMBLY_ITEM_CODE,																			\n");

	    sql.append("                       (SELECT decode(" + PartSpecEnum.SpPartType.getColumnName()
		    + ", 'D', 'Die No', 'M', '금형부품', 'F', '제품', 'H', '반제품', 'R', '원자재', 'K', '포장재', 'S', '스크랩', 'W', '상품','제품')" + " FROM WTPART "
		    + "WHERE IDA2A2 = X0.VERSIONITEMCODE) AS PART_TYPE,         	\n");
	    sql.append("                                    (SELECT " + PartSpecEnum.SpPartType.getColumnName() + " FROM WTPART " + "WHERE IDA2A2 = X0.VERSIONITEMCODE) AS PART_TYPE_CODE, \n");

	    sql.append("                                    (SELECT NAME								 \n");
	    sql.append("                                         FROM WTPARTMASTER							 \n");
	    sql.append("                                        WHERE IDA2A2 = (SELECT IDA3MASTERREFERENCE				 \n");
	    sql.append("                                                          FROM WTPART						 \n");
	    sql.append("                                                         WHERE IDA2A2 = X0.VERSIONITEMCODE))			 \n");
	    sql.append("                                         AS COMPONENT_ITEM_NAME,							 \n");

	    // sql.append("		       (SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5) AS ASSEMBLY_ITEM_REV,													\n");
	    sql.append("		       X0.VERSIONITEMCODE AS COMPONENT_ITEM_OID, 																		\n");
	    sql.append("		       X0.CHILDITEMCODE AS COMPONENT_ITEM_CODE,																			\n");
	    sql.append("		       (SELECT NAME FROM WTPARTMASTER WHERE IDA2A2 = (SELECT IDA3MASTERREFERENCE FROM WTPART WHERE IDA2A2=X0.VERSIONITEMCODE)) AS COMPONENT_ITEM_NAME, 						\n");
	    sql.append("		       X0.CHILDITEMVERSION AS COMPONENT_ITEM_REV,																		\n");
	    sql.append("		       X0.QUANTITY AS QTY,  (SELECT STATESTATE FROM WTPART WHERE IDA2A2=X0.VERSIONITEMCODE) AS STATUS,	(SELECT IDA3A2STATE FROM WTPART WHERE IDA2A2=X0.VERSIONITEMCODE) AS IDA3A2STATE,	\n");
	    sql.append("		       X0.UNIT, X0.IDA2A2, X0.STARTDATE, X0.ENDDATE,  X0.MATERIAL, X0.HARDNESSFROM, X0.HARDNESSTO, X0.DESIGNDATE, X0.ICT AS ICT, X0.REFTOP AS REFTOP, X0.REFBOTTOM AS REFBOTTOM,	\n");
	    sql.append("		       X0.IDA2A2 AS USAGELINKOID, 																				\n");
	    sql.append("		      (X0.CHILDITEMCODE || '_' || X0.CHILDITEMVERSION) AS ITEMINFO, (X0.PARENTITEMCODE || '_' || (SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5)) AS PARENTITEMINFO, X0.ATTRIBUTE10 AS ECONO		\n");
	    sql.append("			FROM KETPARTUSAGELINK X0                               																	\n");
	    sql.append("			START WITH X0.IDA3A5 = " + ida2a2 + "                                    																\n");
	    sql.append("			      CONNECT BY PRIOR (                                   																\n");
	    sql.append("				  SELECT                                          																\n");
	    sql.append("				  MAX(B.IDA3A5)                                      																\n");
	    sql.append("				  FROM KETPARTUSAGELINK B                              																\n");
	    sql.append("				  WHERE B.PARENTITEMCODE = X0.CHILDITEMCODE  																	\n");
	    sql.append("					) = X0.IDA3A5                              																\n");
	    // 제품
	    if (!(productType.equals("D") || productType.equals("M")))
		sql.append("			      ORDER SIBLINGS BY  X0.ITEMSEQ                              															\n");
	    // 금형
	    else
		sql.append("                          ORDER SIBLINGS BY  X0.CHILDITEMCODE																		\n");

	    // Kogger.debug(getClass(), sql.toString());

	    pstmt = conn.prepareStatement(sql.toString());

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		// String parentNo = rs.getString("ASSEMBLY_ITEM_CODE") == null ? "" : rs.getString("ASSEMBLY_ITEM_CODE").trim();
		String partNo = rs.getString("COMPONENT_ITEM_CODE") == null ? "" : rs.getString("COMPONENT_ITEM_CODE").trim();
		String partType = rs.getString("PART_TYPE") == null ? "" : rs.getString("PART_TYPE").trim();
		String partTypeCode = rs.getString("PART_TYPE_CODE") == null ? "" : rs.getString("PART_TYPE_CODE").trim();
		String partName = rs.getString("COMPONENT_ITEM_NAME") == null ? "" : rs.getString("COMPONENT_ITEM_NAME").trim();
		String partRev = rs.getString("COMPONENT_ITEM_REV") == null ? "" : rs.getString("COMPONENT_ITEM_REV").trim();
		String newRev = bean.getNewVersionCode(partNo, partRev);
		String partOid = bean.getPartOid(partNo, partRev);
		WTPart childPart = pUtil.getPart(partOid);
		String state = childPart.getLifeCycleState().getDisplay();

		// Kogger.debug(getClass(), "STATE===>" + state);
		// // Kogger.debug(getClass(), "ASSEMBLY_ITEM_CODE===>" + parentNo);
		// Kogger.debug(getClass(), "COMPONENT_ITEM_CODE===>" + partNo);
		// Kogger.debug(getClass(), "PART_TYPE===>" + partType);
		// Kogger.debug(getClass(), "PART_TYPE_CODE===>" + partTypeCode);
		// Kogger.debug(getClass(), "COMPONENT_ITEM_NAME===>" + partName);
		// Kogger.debug(getClass(), "partRev===>" + partRev);
		// Kogger.debug(getClass(), "newRev===>" + newRev);

		Map<String, String> data = new HashMap<String, String>();
		data.put("productCode", productNumber);
		// data.put("parentNo", parentNo);
		data.put("partType", partType);
		data.put("partTypeCode", partTypeCode);
		data.put("partNo", partNo);
		data.put("rev", partRev);
		data.put("partName", partName);
		data.put("state", state);
		returnList.add(data);
	    }

	} catch (Exception e) {
	    try {
		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
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
		// throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}

	return returnList;
    }

    public List<Map<String, String>> getWorkingPartList(String ecoheaderNumber, String productNumber, String productType, String gubun) {
	KETBOMQueryBean bean = new KETBOMQueryBean();
	KETBomPartUtil pUtil = new KETBomPartUtil();

	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();
	List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
	Hashtable<String, String> partTypeHash = new Hashtable<String, String>();
	partTypeHash.put("D", "Die No");
	partTypeHash.put("M", "금형부품");
	partTypeHash.put("F", "제품");
	partTypeHash.put("H", "반제품");
	partTypeHash.put("R", "원자재");
	partTypeHash.put("K", "포장재");
	partTypeHash.put("S", "스크랩");
	partTypeHash.put("W", "상품");

	try {

	    if (gubun.equals("NEWBOM")) {
		sql.append("	SELECT DISTINCT A0.CHILDITEMCODE, A0.BOMVERSION, B0.NAME AS PARTNAME, LEVEL AS BOMLEVEL, A0.ITEMSEQ, A0.QUANTITY,		");
		sql.append("	       A0.PARENTITEMCODE							");
		sql.append("	  FROM KETBOMCOMPONENT A0, WTPARTMASTER B0												");
		sql.append("	 WHERE A0.CHILDITEMCODE = B0.WTPARTNUMBER AND A0.NEWBOMCODE='" + productNumber + "'										");
		sql.append("	 START WITH A0.PARENTITEMCODE='" + productNumber + "'														");
		sql.append("       CONNECT BY PRIOR CHILDITEMCODE=PARENTITEMCODE												");
		sql.append("	 ORDER SIBLINGS BY  															");
		if (!(productType.equals("D") || productType.equals("M"))) {
		    // 제품
		    sql.append("	 A0.ITEMSEQ																");
		} else {
		    // 금형
		    sql.append("	 A0.CHILDITEMCODE															");
		}
	    } else {
		sql.append("  	SELECT DISTINCT A0.CHILDITEMCODE, A0.BOMVERSION, B0.NAME AS PARTNAME, LEVEL AS BOMLEVEL, A0.ITEMSEQ					");
		sql.append("      FROM KETBOMECOCOMPONENT A0, WTPARTMASTER B0 																");
		sql.append("     WHERE A0.CHILDITEMCODE = B0.WTPARTNUMBER AND A0.ECOHEADERNUMBER = '" + ecoheaderNumber + "'  										");
		sql.append("       AND NVL(A0.ECOCODE, ' ') <>'Remove'																	");
		sql.append("     START WITH A0.PARENTITEMCODE='" + productNumber + "'																		");
		sql.append("   CONNECT BY PRIOR A0.CHILDITEMCODE=A0.PARENTITEMCODE																");
		sql.append("    ORDER SIBLINGS BY  																			");
		if (!(productType.equals("D") || productType.equals("M"))) {
		    // 제품
		    sql.append("         A0.ITEMSEQ																			");
		} else {
		    // 금형
		    sql.append("         A0.CHILDITEMCODE																		");
		}
	    }
	    // Kogger.debug(getClass(), sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		// String parentNo = rs.getString("PARENTITEMCODE") == null ? "" : rs.getString("PARENTITEMCODE").trim();
		String partNo = rs.getString("CHILDITEMCODE") == null ? "" : rs.getString("CHILDITEMCODE").trim();
		String partName = rs.getString("PARTNAME") == null ? "" : rs.getString("PARTNAME").trim();
		String partRev = rs.getString("BOMVERSION") == null ? "" : rs.getString("BOMVERSION").trim();
		String newRev = bean.getNewVersionCode(partNo, partRev);
		String partOid = bean.getPartOid(partNo, partRev);
		WTPart childPart = pUtil.getPart(partOid);
		String state = childPart.getLifeCycleState().getDisplay();
		String partType = "";
		String partTypeCode = "";

		partTypeCode = PartSpecGetter.getPartSpec(childPart, PartSpecEnum.SpPartType);
		partType = (String) partTypeHash.get(partTypeCode);

		// Kogger.debug(getClass(), "STATE===>" + state);
		// // Kogger.debug(getClass(), "ASSEMBLY_ITEM_CODE===>" + parentNo);
		// Kogger.debug(getClass(), "COMPONENT_ITEM_CODE===>" + partNo);
		// Kogger.debug(getClass(), "PART_TYPE===>" + partType);
		// Kogger.debug(getClass(), "PART_TYPE_CODE===>" + partTypeCode);
		// Kogger.debug(getClass(), "COMPONENT_ITEM_NAME===>" + partName);
		// Kogger.debug(getClass(), "partRev===>" + partRev);
		// Kogger.debug(getClass(), "newRev===>" + newRev);

		Map<String, String> data = new HashMap<String, String>();
		data.put("productCode", productNumber);
		// data.put("parentNo", parentNo);
		data.put("partType", partType);
		data.put("partTypeCode", partTypeCode);
		data.put("partNo", partNo);
		data.put("rev", partRev);
		data.put("partName", partName);
		data.put("state", state);
		returnList.add(data);
	    }

	} catch (Exception e) {
	    try {
		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
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
		// throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }
	}

	return returnList;
    }

    @SuppressWarnings("rawtypes")
    public List<Map<String, String>> getPartList(String partNumber) throws Exception {
	List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
	KETBOMQueryBean bean = new KETBOMQueryBean();
	KETBomPartUtil pUtil = new KETBomPartUtil();

	Hashtable partInfoHash = (Hashtable) bean.getPartInfo(partNumber);
	String partRev = "";
	WTPart part = null;
	long ida2a2 = 0;
	String ida2a2Str = "";

	if (partInfoHash != null && partInfoHash.containsKey("WTPart")) {
	    partRev = (String) partInfoHash.get("rev");
	    part = (WTPart) partInfoHash.get("WTPart");

	    ida2a2 = pUtil.getPartLongId(part);
	    ida2a2Str = Long.toString(ida2a2);
	} else {
	    partRev = bean.getLatestPartRev2(partNumber);
	    String partOid = bean.getPartOid(partNumber, partRev);
	    part = (WTPart) pUtil.getPart(partOid);
	}

	String productType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);

	Hashtable checkData = bean.getEcoBomHeaderChecker(partNumber, partRev);
	String ecoNumber = "";
	String gubun = "";
	String ecoState = "";

	Hashtable charger = new Hashtable();
	if (checkData != null && checkData.size() > 0) {
	    ecoNumber = (String) checkData.get("ecoNumber");
	    gubun = (String) checkData.get("gubun");
	    charger = (Hashtable) checkData.get("charger");
	    ecoState = (String) checkData.get("state");
	}
	// Kogger.debug(getClass(), "ecoNumber====>" + ecoNumber);
	// Kogger.debug(getClass(), "ida2a2====>" + ida2a2Str);

	if (ecoState.equals("APPROVED") || ecoNumber.equals("")) {
	    // Kogger.debug(getClass(), "USAGELINK====>" + ecoState);
	    returnList.addAll(getUsageLinkPartList(ida2a2Str, productType, partNumber));
	} else {
	    // Kogger.debug(getClass(), "STATE====>" + ecoState);
	    if (gubun.equals("ECOBOM")) {
		// Kogger.debug(getClass(), "ECOBOM====>" + gubun);
	    } else {
		// Kogger.debug(getClass(), "NEWBOM====>" + gubun);
	    }
	    returnList.addAll(getWorkingPartList(ecoNumber, partNumber, productType, gubun));
	}

	return returnList;
    }

    public static void main(String[] args) throws Exception {
	// TODO Auto-generated method stub
	KETProjectBomQueryBean bean = new KETProjectBomQueryBean();
	Kogger.debug(KETProjectBomQueryBean.class, "size >> " + bean.getPartList("667488"));
    }

}
