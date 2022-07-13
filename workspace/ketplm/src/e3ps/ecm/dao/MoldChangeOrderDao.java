package e3ps.ecm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.method.MethodContext;
import wt.pom.DBProperties;
import wt.pom.WTConnection;
import e3ps.bom.common.iba.IBAUtil;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.query.LoggableStatement;
import e3ps.common.treegrid.TgPagingControl;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.NumberCodeUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.ecm.beans.EcmSearchHelper;
import e3ps.ecm.beans.EcmUtil;
import e3ps.ecm.entity.KETCodeDetailVO;
import e3ps.ecm.entity.KETCodeVO;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldChangeOrderVO;
import e3ps.ecm.entity.KETMoldChangeRequest;
import e3ps.ecm.entity.KETMoldECALinkVO;
import e3ps.ecm.entity.KETMoldEcoEcrLinkVO;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETSearchEcoDetailVO;
import e3ps.edm.beans.EDMHelper;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.shared.log.Kogger;

public class MoldChangeOrderDao {
    private static final long serialVersionUID = -1432672658288760111L;
    private static String dateFormatString = EcmUtil.getDateFormatString();

    /**
     * 
     * 함수명 : getProjectInfo 설명 : 프로젝트명을 조회한다.
     * 
     * @param projectNo
     *            : 프로젝트번호
     * @return String : 프로젝트명
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    public String getProjectInfo(String projectNo) throws Exception {
	StringBuffer sql = new StringBuffer();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String projectName = "";
	try {
	    con = PlmDBUtil.getConnection();
	    sql.append("SELECT partNo pjtNo, pjtName projectName \n").append("FROM E3PSProjectMaster T1 \n").append("WHERE pjtNo = '" + projectNo + "' \n");

	    pstmt = con.prepareStatement(sql.toString());
	    rs = pstmt.executeQuery();
	    projectName = "";
	    if (rs.next()) {
		projectName = rs.getString("projectName");
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new Exception(e);
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
	return projectName;
    }

    /**
     * 
     * 함수명 : getCodeList 설명 : 코드명 목록을 조회한다.
     * 
     * @param codeType
     *            : 코드구분
     * @return KETCodeVO : 코드 VO
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    public KETCodeVO getCodeList(String codeType, String locale) throws Exception {
	StringBuffer sql = new StringBuffer();
	Connection con = null;
	LoggableStatement pstmt = null;
	ResultSet rs = null;

	KETCodeVO ketCodeVO = new KETCodeVO();
	ArrayList<KETCodeDetailVO> ketCodeDetailVOList = new ArrayList<KETCodeDetailVO>();
	KETCodeDetailVO ketCodeDetailVO = null;
	try {
	    con = PlmDBUtil.getConnection();
	    sql.append("SELECT NC.CODE   AS CODE                \n");
	    sql.append("      ,NCV.VALUE AS NAME                \n");
	    sql.append("  FROM NUMBERCODE NC                    \n");
	    sql.append("      ,NUMBERCODEVALUE NCV              \n");
	    sql.append(" WHERE NC.CODETYPE = NCV.CODETYPE       \n");
	    sql.append("   AND NC.CODE     = NCV.CODE           \n");
	    sql.append("   AND NC.DISABLED = 0                  \n");
	    sql.append("   AND NC.CODETYPE = '" + codeType + "' \n");
	    sql.append("   AND NCV.LANG    = '" + locale + "'   \n");
	    sql.append(" ORDER BY TO_NUMBER(NC.SORTING)         \n");

	    pstmt = new LoggableStatement(con, sql.toString());
	    rs = pstmt.executeQuery();
	    int cnt = 0;
	    while (rs.next()) {
		cnt++;
		ketCodeDetailVO = new KETCodeDetailVO();
		ketCodeDetailVO.setCode(rs.getString("code"));
		ketCodeDetailVO.setName(rs.getString("name"));
		ketCodeDetailVOList.add(ketCodeDetailVO);
	    }
	    ketCodeVO.setTotalCount(cnt);
	    ketCodeVO.setKetCodeDetailVOList(ketCodeDetailVOList);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new Exception(e);
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
	return ketCodeVO;
    }

    /**
     * 
     * 함수명 : getCodeNames 설명 : 코드명 목록 조회 후 문자열을 연결한다.
     * 
     * @param codeType
     *            : 코드구분
     * @param codes
     *            : 코드
     * @return 코드명 문자열
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    @SuppressWarnings("rawtypes")
    public String getCodeNames(String codeType, String codes) throws Exception {
	String codeName = "";
	if ("".equals(codes)) {
	    return codeName;
	}
	StringBuffer sql = new StringBuffer();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    con = PlmDBUtil.getConnection();
	    sql.append("SELECT code, name \n");
	    sql.append("FROM NumberCode \n");
	    sql.append("WHERE 1 = 1 \n");
	    sql.append("AND codeType = ? \n");
	    sql.append("AND code IN (? \n");

	    int max = 20;
	    int i = 0;
	    for (i = 0; i < max; i++) {
		sql.append(", ?");
	    }
	    sql.append("\n) \n");
	    sql.append("ORDER BY code \n");

	    pstmt = new LoggableStatement(con, sql.toString());
	    int pstmtCnt = 1;
	    pstmt.setString(pstmtCnt++, codeType);
	    ArrayList arrCode = KETStringUtil.getToken(codes, "|");
	    int size = arrCode.size();
	    for (i = 0; i < size; i++) {
		pstmt.setString(pstmtCnt++, (String) arrCode.get(i));
	    }
	    for (int j = i; j <= max; j++) {
		pstmt.setString(pstmtCnt++, "0");
	    }

	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		codeName = rs.getString("name");
	    }
	    while (rs.next()) {
		codeName += "/ " + rs.getString("name");
	    }
	} catch (Exception e) {
	    throw new Exception(e);
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
	return codeName;
    }

    public String getCodeNames(String codeType, String codes, String locale) throws Exception {
	String codeName = "";
	if ("".equals(codes)) {
	    return codeName;
	}
	StringBuffer sql = new StringBuffer();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    con = PlmDBUtil.getConnection();
	    sql.append("SELECT NC.CODE   AS CODE                \n");
	    sql.append("      ,NCV.VALUE AS NAME                \n");
	    sql.append("  FROM NUMBERCODE NC                    \n");
	    sql.append("      ,NUMBERCODEVALUE NCV              \n");
	    sql.append(" WHERE NC.CODETYPE = NCV.CODETYPE       \n");
	    sql.append("   AND NC.CODE     = NCV.CODE           \n");
	    sql.append("   AND NC.DISABLED = 0                  \n");
	    sql.append("   AND NCV.LANG    = '" + locale + "'   \n");
	    sql.append("   AND NC.CODETYPE = ?                  \n");
	    sql.append("    AND NC.CODE IN (?                    \n");

	    int max = 20;
	    int i = 0;
	    for (i = 0; i < max; i++) {
		sql.append(", ?");
	    }
	    sql.append("\n) \n");
	    sql.append("ORDER BY code \n");

	    pstmt = new LoggableStatement(con, sql.toString());
	    int pstmtCnt = 1;
	    pstmt.setString(pstmtCnt++, codeType);
	    ArrayList arrCode = KETStringUtil.getToken(codes, "|");
	    int size = arrCode.size();
	    for (i = 0; i < size; i++) {
		pstmt.setString(pstmtCnt++, (String) arrCode.get(i));
	    }
	    for (int j = i; j <= max; j++) {
		pstmt.setString(pstmtCnt++, "0");
	    }

	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		codeName = rs.getString("name");
	    }
	    while (rs.next()) {
		codeName += "/ " + rs.getString("name");
	    }
	} catch (Exception e) {
	    throw new Exception(e);
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
	return codeName;
    }

    /**
     * 
     * 함수명 : getEcoId 설명 : ECOID를 채번한다.(ex:ECO-YYMM-001)
     * 
     * @return ECOID
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    public String getEcoId() throws Exception {
	StringBuffer sql = new StringBuffer();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    con = PlmDBUtil.getConnection();
	    sql.append("SELECT FN_GET_ECM_NUMBERING(?) FROM DUAL \n");
	    pstmt = con.prepareStatement(sql.toString());
	    pstmt.setString(1, "ECO");
	    rs = pstmt.executeQuery();
	    String ecoId = "";
	    while (rs.next()) {
		ecoId = rs.getString(1);
	    }

	    return ecoId;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new Exception(e);
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
    }

    /**
     * 
     * 함수명 : searchEcoList 설명 : 제품/금형 ECO 목록을 검색한다.
     * 
     * @param ketSearchEcoVO
     *            : 금형 ECO 검색 VO
     * @param isExcel
     *            : 엑셀처리여부(0:미처리 1:엑셀처리)
     * @return ketSearchEcoVO : 금형 ECO 검색 VO
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    public List<Map<String, Object>> searchEcoList(boolean isExcel, KETParamMapUtil hash, List<Map<String, Object>> conditionList) throws Exception {
	ArrayList<KETSearchEcoDetailVO> ketSearchEcoDetailVOList = new ArrayList<KETSearchEcoDetailVO>();// ECO 검색상세 List
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	Map<String, Object> ketSearchEcoDetailVO = null;
	List<Map<String, Object>> ecoMonthlyReportList = new ArrayList<Map<String, Object>>();

	try {
	    con = PlmDBUtil.getConnection();

	    sql = getSearchEcoListSQL(isExcel, hash, conditionList);
	    Kogger.debug(getClass(), " ########################################  sql  ==  " + sql);

	    pstmt = con.prepareStatement(sql);
	    int resultRows = 0;

	    rs = pstmt.executeQuery();

	    EcmComDao ecmComDao = new EcmComDao(con);

	    while (rs.next()) {
		ketSearchEcoDetailVO = new HashMap<String, Object>();

		ketSearchEcoDetailVO.put("EcoId", (StringUtil.checkNull(rs.getString("ecoId"))));// 1.
		ketSearchEcoDetailVO.put("EcoName", (StringUtil.checkNull(rs.getString("ecoName"))));// 2
		ketSearchEcoDetailVO.put("DevFlag", (rs.getString("devyn")));

		// ketSearchEcoDetailVO.put("PartNumber",( EcmSearchHelper.manager.getPartNoStr(rs.getString("oid"))));
		// ketSearchEcoDetailVO.put("PartName",( EcmSearchHelper.manager.getPartNameStr(rs.getString("oid"))));
		ketSearchEcoDetailVO.put("PartNumber", StringUtil.checkNull(rs.getString("partNumber2")));
		ketSearchEcoDetailVO.put("PartName", StringUtil.checkNull(rs.getString("partName2")));

		// ketSearchEcoDetailVO.setPartNameTrunc(strTrunc);//4

		if (rs.getString("prodMoldClsName").equals("금형")) {

		    // ketSearchEcoDetailVO.put("ChangeReason",getCodeNames("CHANGEREASON",
		    // StringUtil.checkNull(rs.getString("changeReason"))));
		    ketSearchEcoDetailVO.put("ChangeReason", StringUtil.checkNull(rs.getString("changeReason2")));

		} else {

		    // ketSearchEcoDetailVO.put("ChangeReason",getCodeNames("PRODECOREASON",
		    // StringUtil.checkNull(rs.getString("changeReason"))));
		    ketSearchEcoDetailVO.put("ChangeReason", StringUtil.checkNull(rs.getString("prodecoReason2")));

		}

		if (StringUtil.checkNull(rs.getString("other_reason")).length() > 0) {
		    ketSearchEcoDetailVO.put("OtherChgReason", ("(" + StringUtil.checkNull(rs.getString("other_reason")) + ")"));
		} else {
		    ketSearchEcoDetailVO.put("OtherChgReason", (StringUtil.checkNull(rs.getString("other_reason"))));
		}

		ketSearchEcoDetailVO.put("SecureBudgetYn", (StringUtil.checkNull(rs.getString("secureBudgetYn"))));// 6
		ketSearchEcoDetailVO.put("CreatorName", (rs.getString("creatorName")));// 7

		String strCreateDate = DateUtil.getDateString(rs.getTimestamp("createDate"), "date");
		ketSearchEcoDetailVO.put("CreateDate", strCreateDate);// 8

		ketSearchEcoDetailVO.put("SancStateFlag", (rs.getString("stateStateName")));// 9
		ketSearchEcoDetailVO.put("CreateDeptName", (rs.getString("orgName")));// 9
		ketSearchEcoDetailVO.put("OrgName", (rs.getString("orgName")));// 9

		ketSearchEcoDetailVO.put("Oid", (rs.getString("oid")));
		ketSearchEcoDetailVO.put("ProdMoldClsName", (rs.getString("prodMoldClsName")));

		// ketSearchEcoDetailVO.put("ApproveDate",(
		// EcmUtil.getLastApproveDate((Persistable)KETObjectUtil.getObject(rs.getString("oid"))) ));

		String approveDate = DateUtil.getDateString(rs.getTimestamp("approveDate2"), "date");
		ketSearchEcoDetailVO.put("ApproveDate", approveDate);// 8

		ketSearchEcoDetailVO.put("CarMakerDomain", StringUtil.checkNull(rs.getString("domestic_flag")));
		ketSearchEcoDetailVO.put("CarMaker", (StringUtil.checkNull(rs.getString("car_maker"))));
		ketSearchEcoDetailVO.put("CarCategory", (StringUtil.checkNull(rs.getString("car_category"))));

		// ketSearchEcoDetailVO.put("IncProdType",( getCodeNames("INCREASEPRODTYPE",
		// StringUtil.checkNull(rs.getString("inc_prod_type")))));
		ketSearchEcoDetailVO.put("IncProdType", StringUtil.checkNull(rs.getString("incProdType2")));

		if (StringUtil.checkNull(rs.getString("other_inc_prod_type")).length() > 0) {
		    ketSearchEcoDetailVO.put("OtherIncProdType", ("(" + StringUtil.checkNull(rs.getString("other_inc_prod_type")) + ")"));
		} else {
		    ketSearchEcoDetailVO.put("OtherIncProdType", (StringUtil.checkNull(rs.getString("other_inc_prod_type"))));
		}

		if (StringUtil.checkNull(rs.getString("vendor_flag")).length() > 0) {
		    if ("i".equals(rs.getString("vendor_flag"))) {

			// ketSearchEcoDetailVO.put("ProdVendor",( StringUtil.checkNull( ecmComDao.getCodeName("PRODUCTIONDEPT",
			// rs.getString("prod_vendor")))));//생산처명
			ketSearchEcoDetailVO.put("ProdVendor", StringUtil.checkNull(rs.getString("prodVendor2")));

		    } else if ("o".equals(rs.getString("vendor_flag"))) {

			// ketSearchEcoDetailVO.put("ProdVendor",(
			// StringUtil.checkNull(ecmComDao.getPartnerName(rs.getString("prod_vendor")))));
			ketSearchEcoDetailVO.put("ProdVendor", StringUtil.checkNull(rs.getString("partnerName2")));

		    }
		} else {
		    ketSearchEcoDetailVO.put("ProdVendor", (StringUtil.checkNull(rs.getString("prod_vendor"))));
		}

		// ketSearchEcoDetailVO.put("CustomChk",( StringUtil.checkNull( getCodeNames("CUSTOMCHECK",
		// StringUtil.checkNull(rs.getString("custom_flag"))) )));
		ketSearchEcoDetailVO.put("CustomChk", StringUtil.checkNull(rs.getString("customChk2")));

		if (StringUtil.checkNull(rs.getString("custom_desc")).length() > 0) {
		    ketSearchEcoDetailVO.put("CustomChkDesc", ("(" + StringUtil.checkNull(rs.getString("custom_desc")) + ")"));
		} else {
		    ketSearchEcoDetailVO.put("CustomChkDesc", (StringUtil.checkNull(rs.getString("custom_desc"))));
		}

		// ketSearchEcoDetailVO.put("ControlFlag",( StringUtil.checkNull( getCodeNames("CHANGEFACT",
		// StringUtil.checkNull(rs.getString("control_flag"))) ) ));
		ketSearchEcoDetailVO.put("ControlFlag", StringUtil.checkNull(rs.getString("controlFlag2")));

		ketSearchEcoDetailVO.put("CuDrawingChk", (StringUtil.checkNull(rs.getString("cu_drawing"))));
		String oid = StringUtil.checkNull(rs.getString("projectOid"));
		ketSearchEcoDetailVO.put("ProjectNo", (StringUtil.checkNull(rs.getString("projectNo"))));
		ketSearchEcoDetailVO.put("ProjectOid", oid);

		// ketSearchEcoDetailVO.put("RelatedECRStr", EcmSearchHelper.manager.getRelatedECRLinkStr(rs.getString("oid")) );
		ketSearchEcoDetailVO.put("RelatedECRStr", StringUtil.checkNull(rs.getString("relatedECRStr2")));

		// ketSearchEcoDetailVO.put("RelatedECOStr", EcmSearchHelper.manager.getRelatedECOLinkStr(rs.getString("oid")) );
		ketSearchEcoDetailVO.put("RelatedECOStr", StringUtil.checkNull(rs.getString("relatedECOStr2")));

		String CompleteReqDateStr = WorkflowSearchHelper.manager.getLastApprovalDate(CommonUtil.getObject(rs.getString("oid")));

		if (!CompleteReqDateStr.equals("&nbsp;")) {
		    Date date = DateUtil.parseDateStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), CompleteReqDateStr);
		    CompleteReqDateStr = DateUtil.getDateString(date, new SimpleDateFormat("yyyy-MM-dd"));
		}

		ketSearchEcoDetailVO.put("CompleteReqDate", CompleteReqDateStr);

		ketSearchEcoDetailVO.put("divisionFlag", rs.getString("divisionFlag"));
		ketSearchEcoDetailVO.put("divisonFlagName", rs.getString("divisionFlagName"));
		ketSearchEcoDetailVO.put("costvariationrate", (StringUtil.checkNull(rs.getString("costvariationrate"))));
		ketSearchEcoDetailVO.put("costchange", (StringUtil.checkNull(rs.getString("costchange"))));
		ketSearchEcoDetailVO.put("defectDivName", (StringUtil.checkNull(rs.getString("defectDivName"))));
		ketSearchEcoDetailVO.put("defectTypeName", (StringUtil.checkNull(rs.getString("defectTypeName"))));

		ecoMonthlyReportList.add(ketSearchEcoDetailVO);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}

	return ecoMonthlyReportList;
    }

    public List<Map<String, Object>> getMoldEcoCostList(boolean isExcel, KETParamMapUtil hash, List<Map<String, Object>> conditionList) throws Exception {

	List<Map<String, Object>> list = null;

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    StringBuffer sql = new StringBuffer();

	    sql.append(" SELECT ECOID,                                                                           \n");
	    sql.append("        (SELECT ECOITEMCODE FROM KETBOMECOHEADER BOM WHERE BOM.IDA2A2 = T3.IDA3A5        \n ");
	    sql.append("          UNION                                                                          \n");
	    sql.append("         SELECT NEWBOMCODE FROM KETBOMHEADER BOM WHERE BOM.IDA2A2 = T3.IDA3A5) AS PARTNO, \n");
	    sql.append("        RELATEDDIENO      AS DIENO,                                                      \n");
	    sql.append("        FN_GET_CODE_NAME_STR('PRODECOREASON', CHANGEREASON) AS CHANGEREASON,             \n");
	    sql.append("        EXPECTCOST,                                                            \n");
	    sql.append("        SECUREBUDGETYN,                                                                  \n");
	    sql.append("        DECODE (DIVISIONFLAG,  'C', '자동차',  'E', '전자') AS DIVISIONFLAG ,        \n");
	    sql.append("        (SELECT ST2.NAME  FROM WTUSER ST1                                                \n");
	    sql.append(" 	                        , PEOPLE ST2                                                 \n");
	    sql.append(" 	     WHERE ST1.IDA2A2 = T1.IDA3B7                                                     \n");
	    sql.append(" 	       AND ST2.IDA3B4 = ST1.IDA2A2 ) CREATORNAME ,                                   \n");
	    sql.append(" 	   DEPTNAME                                                                          \n");
	    sql.append(" 	   , ( SELECT MAX(PL.NAME)                                                           \n");
	    sql.append(" 	         FROM PHASETEMPLATE PH                                                       \n");
	    sql.append(" 	            , PHASE PL                                                               \n");
	    sql.append(" 		    WHERE 1 = 1                                                                  \n");
	    sql.append(" 	          AND PL.IDA3A4 =PH.IDA2A2                                                   \n");
	    sql.append(" 	          AND PH.PHASESTATE = T1.STATESTATE ) STATESTATENAME                          \n");
	    sql.append("   FROM KETPRODCHANGEORDER T1, KETPRODCHANGEACTIVITY T2, KETPRODECABOMLINK T3               \n");
	    sql.append("  WHERE T1.IDA2A2 = T2.IDA3A8                                                   \n");
	    sql.append("    AND T2.IDA2A2 = T3.IDA3B5                                                              \n");

	    String active = (String) hash.get("Active");

	    for (Map<String, Object> condistion : conditionList) {
		KETParamMapUtil map = KETParamMapUtil.getMap(condistion);

		if (!map.getString("partNo").equals("")) {

		    sql.append("     AND ( \n");

		    sql.append("          T1.idA2A2 IN ( \n");
		    sql.append("                        SELECT ST1.idA3B5 \n");
		    sql.append("                          FROM KETProdECOPartLink ST1 \n");
		    sql.append("                             , wtpart    p \n");
		    sql.append("                             , wtpartmaster    m \n");
		    sql.append("                         WHERE 1 = 1 \n");
		    sql.append("                           AND st1.ida3a5 = p.ida2a2 \n");
		    sql.append("                           AND p.ida3masterreference = m.ida2a2 \n");
		    sql.append("                           AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m.wtpartnumber", map.getString("partNo"), false)).append(" \n");
		    sql.append("                       ) \n");
		    sql.append("          OR \n");
		    sql.append("          T1.ida2a2 IN ( \n");
		    sql.append("                        SELECT eca.ida3a8 \n");
		    sql.append("                          FROM KETProdChangeActivity eca \n");
		    sql.append("                             , KETProdECABomLink blink \n");
		    sql.append("                             , ( \n");
		    sql.append("                                SELECT a.ida2a2, a.ecoitemcode \n");
		    sql.append("                                  FROM KETBomEcoHeader a \n");
		    sql.append("                                 UNION ALL \n");
		    sql.append("                                SELECT b.ida2a2, b.ecoitemcode \n");
		    sql.append("                                  FROM KETBomHeader b \n");
		    sql.append("                               ) head \n");
		    sql.append("                         WHERE eca.ida2a2 = blink.ida3b5 \n");
		    sql.append("                           AND blink.ida3a5 = head.ida2a2 \n");
		    sql.append("                           AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("head.ecoitemcode", map.getString("partNo"), false)).append(" \n");
		    sql.append("                       ) \n");

		    sql.append("          ) \n");

		}

		if (!map.getString("partName").equals("")) {

		    sql.append("     AND ( \n");

		    sql.append("          T1.idA2A2 IN ( \n");
		    sql.append("                        SELECT ST1.idA3B5                                                                                                                                                         \n");
		    sql.append("                          FROM KETProdECOPartLink ST1                                                                                                                                     \n");
		    sql.append("                             , wtpart    p                                                                                                                                                           \n");
		    sql.append("                             , wtpartmaster  m                                                                                                                                                   \n");
		    sql.append("                         WHERE 1 = 1                                                                                                                                                               \n");
		    sql.append("                           AND st1.ida3a5 = p.ida2a2                                                                                                                                         \n");
		    sql.append("                           AND p.ida3masterreference = m.ida2a2                                                                                                                          \n");
		    sql.append("                           AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m.NAME", map.getString("partName"), false)).append("        \n");
		    sql.append("                       ) \n");
		    sql.append("          OR \n");
		    sql.append("          T1.ida2a2 IN ( \n");
		    sql.append("                        SELECT eca.ida3a8 \n");
		    sql.append("                          FROM KETProdChangeActivity eca \n");
		    sql.append("                             , KETProdECABomLink blink \n");
		    sql.append("                             , ( \n");
		    sql.append("                                SELECT a.ida2a2, a.description \n");
		    sql.append("                                  FROM KETBomEcoHeader a \n");
		    sql.append("                                 UNION ALL \n");
		    sql.append("                                SELECT b.ida2a2, b.description \n");
		    sql.append("                                  FROM KETBomHeader b \n");
		    sql.append("                               ) head \n");
		    sql.append("                         WHERE eca.ida2a2 = blink.ida3b5 \n");
		    sql.append("                           AND blink.ida3a5 = head.ida2a2 \n");
		    sql.append("                           AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("head.description", map.getString("partName"), false)).append(" \n");
		    sql.append("                       ) \n");

		    sql.append("          ) \n");

		}
		if (StringUtil.checkNull(active).equals("true")) {
		    sql.append("AND T1.idA2A2 IN (");
		    sql.append("                                    SELECT b.ida2a2 from KETProdChangeActivity A ,KETProdChangeOrder B, ketprodecaepmdoclink C ");
		    sql.append("                                     WHERE a.ida3a8  = b.ida2a2     ");
		    sql.append("                                     AND a.ida2a2 = c.ida3b5 )   ");
		}
		// 프로젝트번호
		if (StringUtil.checkNull((String) map.get("projectOid")).length() > 0) {
		    // sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.projectOid", (String) map.get("projectOid"),
		    // false)).append("        \n");
		    // 제품
		    sql.append(" AND T1.projectOid IN ( \n");
		    sql.append("               SELECT DISTINCT PPR.CLASSNAMEA2A2||':'||PPR.IDA2A2 \n");
		    sql.append("                 FROM E3PSPROJECTMASTER EMA \n");
		    sql.append("                    , PRODUCTPROJECT PPR \n");
		    sql.append("                WHERE EMA.IDA2A2 = PPR.IDA3B8 \n");
		    sql.append("                  AND EMA.PJTNO IN ( \n");
		    sql.append("                                    SELECT DISTINCT EMA.PJTNO \n");
		    sql.append("                                     FROM E3PSPROJECTMASTER EMA \n");
		    sql.append("                                        , PRODUCTPROJECT PPR \n");
		    sql.append("                                    WHERE EMA.IDA2A2 = PPR.IDA3B8 \n");
		    sql.append("                                      AND PPR.CLASSNAMEA2A2||':'||PPR.IDA2A2 = '" + (String) map.get("projectOid") + "' \n");
		    sql.append("                                   ) \n");

		    sql.append("                UNION ALL \n");

		    // 금형
		    sql.append("               SELECT DISTINCT PPR.CLASSNAMEA2A2||':'||PPR.IDA2A2 \n");
		    sql.append("                 FROM E3PSPROJECTMASTER EMA \n");
		    sql.append("                    , MOLDPROJECT PPR \n");
		    sql.append("                WHERE EMA.IDA2A2 = PPR.IDA3B8 \n");
		    sql.append("                  AND EMA.PJTNO IN ( \n");
		    sql.append("                                    SELECT DISTINCT EMA.PJTNO \n");
		    sql.append("                                     FROM E3PSPROJECTMASTER EMA \n");
		    sql.append("                                        , MOLDPROJECT PPR \n");
		    sql.append("                                    WHERE EMA.IDA2A2 = PPR.IDA3B8 \n");
		    sql.append("                                      AND PPR.CLASSNAMEA2A2||':'||PPR.IDA2A2 = '" + (String) map.get("projectOid") + "' \n");
		    sql.append("                                   ) \n");
		    sql.append("                      ) \n");

		}
		// 프로젝트명
		if (StringUtil.checkNull((String) map.get("projectName")).length() > 0) {
		    // sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.projectOid", (String) map.get("projectOid"),
		    // false)).append("        \n");
		    // 제품
		    sql.append(" AND T1.projectOid IN ( \n");

		    sql.append("               SELECT DISTINCT PPR.CLASSNAMEA2A2||':'||PPR.IDA2A2 \n");
		    sql.append("                 FROM E3PSPROJECTMASTER EMA \n");
		    sql.append("                    , PRODUCTPROJECT PPR \n");
		    sql.append("                WHERE EMA.IDA2A2 = PPR.IDA3B8 \n");
		    sql.append("                  AND UPPER(EMA.PJTNAME) LIKE UPPER('%" + (String) map.get("projectName") + "%') \n");

		    sql.append("                UNION ALL \n");

		    // 금형
		    sql.append("               SELECT DISTINCT PPR.CLASSNAMEA2A2||':'||PPR.IDA2A2 \n");
		    sql.append("                 FROM E3PSPROJECTMASTER EMA \n");
		    sql.append("                    , MOLDPROJECT PPR \n");
		    sql.append("                WHERE EMA.IDA2A2 = PPR.IDA3B8 \n");
		    sql.append("                  AND UPPER(EMA.PJTNAME) LIKE UPPER('%" + (String) map.get("projectName") + "%') \n");

		    sql.append("                      ) \n");

		}
		// 부서명
		if (StringUtil.checkNull((String) map.get("orgName")).length() > 0) {
		    sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.deptName", (String) map.get("orgName"), false)).append("        \n");
		}
		// 작성자OID
		if (StringUtil.checkNull(map.getString("creatorOid")).length() > 0) {
		    sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.idA3B7", KETParamMapUtil.OidToString(map.getString("creatorOid")), false)).append("        \n");
		}

		// 로그인 사용자가 KETS 일 경우
		if (CommonUtil.isKETSUser()) {
		    sql.append(CommonUtil.ketsUserListWhereStr("T1.idA3B7"));
		}

		// //ECO번호
		if (StringUtil.checkNull(map.getString("ecoId")).length() > 0) {
		    // sql.append("     AND T1.ecoId like  'ECO-'||'" + KETStringUtil.getLikeString( (String)map.get("ecoId") ) +
		    // "'    \n");
		    sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.ecoId", ("ECO-" + (String) map.get("ecoId")), false)).append("        \n");
		    // sql.append("     AND T1.ecoId like '" + KETStringUtil.getLikeString((String) map.get("ecoId")) + "'    \n");

		}
		// 사업자 구분
		String division = (String) map.get("divisionFlag");
		if (division != null && division.trim().length() > 0 && !division.equalsIgnoreCase("null")) {
		    sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.divisionFlag", division, false)).append("        \n");
		}
		// 개발양산구분
		String devYn = (String) hash.get("HdevYn");
		if (devYn != null && devYn.trim().length() > 0 && !devYn.equalsIgnoreCase("null")) {
		    sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.devYn", devYn, false)).append("        \n");
		}
		// 진행상태
		String state = (String) hash.get("HsancStateFlag");
		if (state != null && state.trim().length() > 0 && !state.equalsIgnoreCase("null")) {
		    sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.stateState", state, false)).append("        \n");
		}
		// 작성일자
		if (!map.getString("createStartDate").equals("")) {
		    String predate = map.getString("createStartDate");
		    predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
		    sql.append("    AND T1.createStampA2 >= TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		}

		// 승인일자
		if (!map.getString("approveStartDate").equals("")) {
		    String predate = map.getString("approveStartDate");
		    predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
		    sql.append("    AND completeddate >= TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		}

		if (!map.getString("approveEndDate").equals("")) {
		    String postdate = map.getString("approveEndDate");
		    postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
		    sql.append("    AND completeddate <= TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')                \n");
		}

		if (!map.getString("createEndDate").equals("")) {
		    String postdate = map.getString("createEndDate");
		    postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
		    sql.append("    AND T1.createStampA2 <= TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')                \n");
		}
		// ECO제목
		if (StringUtil.checkNull((String) map.get("ecoName")).length() > 0) {
		    sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.ecoName", (String) map.get("ecoName"), false)).append("        \n");
		}
		// 2013.03.13 e3ps shkim add
		// ECR 번호
		if (StringUtil.checkNull((String) map.get("ecrNumber")).length() > 0) {
		    sql.append("  AND T1.idA2A2 IN ( SELECT idA3A5 FROM KETProdChangeLink WHERE idA3B5 IN ( SELECT idA2A2 from KETProdChangeRequest WHERE ecrid = '"
			    + ((String) map.get("ecrNumber")).toUpperCase() + "' ) )  \n");
		}

		if (!map.getString("design_guide_yn").equals("")) {
		    if ("YN".equals(map.getString("design_guide_yn").toUpperCase())) {
			sql.append("  AND T1.designguideyn is not null \n");
		    } else {
			sql.append("  AND T1.designguideyn  = '" + ((String) map.get("design_guide")).toUpperCase() + "'  \n");
		    }
		}

		if (!map.getString("design_sheet_yn").equals("")) {
		    if ("YN".equals(map.getString("designchecksheetyn").toUpperCase())) {
			sql.append("  AND T1.designchecksheetyn is not null \n");
		    } else {
			sql.append("  AND T1.designchecksheetyn  = '" + ((String) map.get("design_sheet_yn")).toUpperCase() + "'  \n");
		    }
		}

	    }

	    rs = stat.executeQuery(sql.toString());

	    list = ext.ket.common.util.ObjectUtil.manager.rsToList(rs);

	} catch (Exception e) {
	    throw new Exception(e);
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (stat != null) {
		stat.close();
	    }
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}

	return list;
    }

    public List<Map<String, Object>> searchEcoList(boolean isExcel, KETParamMapUtil hash, List<Map<String, Object>> conditionList, TgPagingControl pager) throws Exception {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	// Map<String, Object> ketSearchEcoDetailVO = null;
	List<Map<String, Object>> ecoMonthlyReportList = new ArrayList<Map<String, Object>>();

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	try {

	    /*
	     * con = PlmDBUtil.getConnection();
	     * 
	     * sql = getSearchEcoListSQL(isExcel ,hash, conditionList); Kogger.debug(getClass(),
	     * " ########################################  sql  ==  " + sql );
	     * 
	     * pstmt = con.prepareStatement(sql); int resultRows = 0;
	     * 
	     * rs = pstmt.executeQuery();
	     * 
	     * EcmComDao ecmComDao = new EcmComDao(con);
	     */

	    sql = getSearchEcoListSQL(isExcel, hash, conditionList, pager);

	    /*
	     * sql = oDaoManager.getOrderByQuery(sql, pager); sql = oDaoManager.getPagingQuery(sql, pager);
	     */

	    ecoMonthlyReportList = oDaoManager.queryForList(sql, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    Map<String, Object> ketSearchEcoDetailVO = new HashMap<String, Object>();

		    ketSearchEcoDetailVO.put("EcoId", (StringUtil.checkNull(rs.getString("ecoId"))));// 1.
		    ketSearchEcoDetailVO.put("EcoName", (StringUtil.checkNull(rs.getString("ecoName"))));// 2
		    ketSearchEcoDetailVO.put("DevFlag", (rs.getString("devyn")));

		    // ketSearchEcoDetailVO.put("PartNumber",( EcmSearchHelper.manager.getPartNoStr(rs.getString("oid"))));
		    // ketSearchEcoDetailVO.put("PartName",( EcmSearchHelper.manager.getPartNameStr(rs.getString("oid"))));
		    ketSearchEcoDetailVO.put("PartNumber", StringUtil.checkNull(rs.getString("partNumber2")));
		    ketSearchEcoDetailVO.put("PartName", StringUtil.checkNull(rs.getString("partName2")));

		    // ketSearchEcoDetailVO.setPartNameTrunc(strTrunc);//4

		    if (rs.getString("prodMoldClsName").equals("금형")) {

			// ketSearchEcoDetailVO.put("ChangeReason",getCodeNames("CHANGEREASON",
			// StringUtil.checkNull(rs.getString("changeReason"))));
			ketSearchEcoDetailVO.put("ChangeReason", StringUtil.checkNull(rs.getString("changeReason2")));

		    } else {

			// ketSearchEcoDetailVO.put("ChangeReason",getCodeNames("PRODECOREASON",
			// StringUtil.checkNull(rs.getString("changeReason"))));
			ketSearchEcoDetailVO.put("ChangeReason", StringUtil.checkNull(rs.getString("prodecoReason2")));

		    }

		    if (StringUtil.checkNull(rs.getString("other_reason")).length() > 0) {
			ketSearchEcoDetailVO.put("OtherChgReason", ("(" + StringUtil.checkNull(rs.getString("other_reason")) + ")"));
		    } else {
			ketSearchEcoDetailVO.put("OtherChgReason", (StringUtil.checkNull(rs.getString("other_reason"))));
		    }

		    ketSearchEcoDetailVO.put("SecureBudgetYn", (StringUtil.checkNull(rs.getString("secureBudgetYn"))));// 6
		    ketSearchEcoDetailVO.put("CreatorName", (rs.getString("creatorName")));// 7

		    String strCreateDate = DateUtil.getDateString(rs.getTimestamp("createDate"), "date");
		    ketSearchEcoDetailVO.put("CreateDate", (strCreateDate));// 8

		    ketSearchEcoDetailVO.put("SancStateFlag", (rs.getString("stateStateName")));// 9
		    ketSearchEcoDetailVO.put("CreateDeptName", (rs.getString("orgName")));// 9
		    ketSearchEcoDetailVO.put("OrgName", (rs.getString("orgName")));// 9

		    ketSearchEcoDetailVO.put("Oid", (rs.getString("oid")));
		    ketSearchEcoDetailVO.put("ProdMoldClsName", (rs.getString("prodMoldClsName")));

		    // ketSearchEcoDetailVO.put("ApproveDate",(
		    // EcmUtil.getLastApproveDate((Persistable)KETObjectUtil.getObject(rs.getString("oid"))) ));

		    String ApproveDate = DateUtil.getDateString(rs.getTimestamp("approveDate2"), "date");
		    ketSearchEcoDetailVO.put("ApproveDate", ApproveDate);

		    ketSearchEcoDetailVO.put("CarMakerDomain", StringUtil.checkNull(rs.getString("domestic_flag")));
		    ketSearchEcoDetailVO.put("CarMaker", (StringUtil.checkNull(rs.getString("car_maker"))));
		    ketSearchEcoDetailVO.put("CarCategory", (StringUtil.checkNull(rs.getString("car_category"))));

		    // ketSearchEcoDetailVO.put("IncProdType",( getCodeNames("INCREASEPRODTYPE",
		    // StringUtil.checkNull(rs.getString("inc_prod_type")))));
		    ketSearchEcoDetailVO.put("IncProdType", StringUtil.checkNull(rs.getString("incProdType2")));

		    if (StringUtil.checkNull(rs.getString("other_inc_prod_type")).length() > 0) {
			ketSearchEcoDetailVO.put("OtherIncProdType", ("(" + StringUtil.checkNull(rs.getString("other_inc_prod_type")) + ")"));
		    } else {
			ketSearchEcoDetailVO.put("OtherIncProdType", (StringUtil.checkNull(rs.getString("other_inc_prod_type"))));
		    }

		    if (StringUtil.checkNull(rs.getString("vendor_flag")).length() > 0) {
			if ("i".equals(rs.getString("vendor_flag"))) {

			    // ketSearchEcoDetailVO.put("ProdVendor",( StringUtil.checkNull( ecmComDao.getCodeName("PRODUCTIONDEPT",
			    // rs.getString("prod_vendor")))));//생산처명
			    ketSearchEcoDetailVO.put("ProdVendor", StringUtil.checkNull(rs.getString("prodVendor2")));

			} else if ("o".equals(rs.getString("vendor_flag"))) {

			    // ketSearchEcoDetailVO.put("ProdVendor",(
			    // StringUtil.checkNull(ecmComDao.getPartnerName(rs.getString("prod_vendor")))));
			    ketSearchEcoDetailVO.put("ProdVendor", StringUtil.checkNull(rs.getString("partnerName2")));

			}
		    } else {
			ketSearchEcoDetailVO.put("ProdVendor", (StringUtil.checkNull(rs.getString("prod_vendor"))));
		    }

		    // ketSearchEcoDetailVO.put("CustomChk",( StringUtil.checkNull( getCodeNames("CUSTOMCHECK",
		    // StringUtil.checkNull(rs.getString("custom_flag"))) )));
		    ketSearchEcoDetailVO.put("CustomChk", StringUtil.checkNull(rs.getString("customChk2")));

		    if (StringUtil.checkNull(rs.getString("custom_desc")).length() > 0) {
			ketSearchEcoDetailVO.put("CustomChkDesc", ("(" + StringUtil.checkNull(rs.getString("custom_desc")) + ")"));
		    } else {
			ketSearchEcoDetailVO.put("CustomChkDesc", (StringUtil.checkNull(rs.getString("custom_desc"))));
		    }

		    // ketSearchEcoDetailVO.put("ControlFlag",( StringUtil.checkNull( getCodeNames("CHANGEFACT",
		    // StringUtil.checkNull(rs.getString("control_flag"))) ) ));
		    ketSearchEcoDetailVO.put("ControlFlag", StringUtil.checkNull(rs.getString("controlFlag2")));

		    ketSearchEcoDetailVO.put("CuDrawingChk", (StringUtil.checkNull(rs.getString("cu_drawing"))));
		    String oid = StringUtil.checkNull(rs.getString("projectOid"));
		    ketSearchEcoDetailVO.put("ProjectNo", (StringUtil.checkNull(rs.getString("projectNo"))));
		    ketSearchEcoDetailVO.put("ProjectOid", oid);

		    // ketSearchEcoDetailVO.put("RelatedECRStr", EcmSearchHelper.manager.getRelatedECRLinkStr(rs.getString("oid")) );
		    ketSearchEcoDetailVO.put("RelatedECRStr", StringUtil.checkNull(rs.getString("relatedECRStr2")));

		    // ketSearchEcoDetailVO.put("RelatedECOStr", EcmSearchHelper.manager.getRelatedECOLinkStr(rs.getString("oid")) );
		    ketSearchEcoDetailVO.put("RelatedECOStr", StringUtil.checkNull(rs.getString("relatedECOStr2")));
		    ketSearchEcoDetailVO.put("costchange", StringUtil.checkNull(rs.getString("costchange")));
		    ketSearchEcoDetailVO.put("costvariationrate", StringUtil.checkNull(rs.getString("costvariationrate")));
		    ketSearchEcoDetailVO.put("ecoApplyPoint", StringUtil.checkNull(rs.getString("ecoapplypoint")));

		    return ketSearchEcoDetailVO;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}

	return ecoMonthlyReportList;
    }

    public int searchEcoListCount(boolean isExcel, KETParamMapUtil hash, List<Map<String, Object>> conditionList) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	String sql = getSearchEcoListSQL(isExcel, hash, conditionList);
	int totalCount = oDaoManager.queryForCount(sql);

	return totalCount;
    }

    /**
     * 
     * ECN 리스트
     * 
     * @param isExcel
     * @param paramMap
     * @param pager
     * @return
     * @throws Exception
     * @메소드명 : searchEcnList
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<Map<String, Object>> searchEcnList(boolean isExcel, KETParamMapUtil paramMap, TgPagingControl pager) throws Exception {
	// Map<String, Object> ketSearchEcoDetailVO = null;
	List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {

	    String sql = "";

	    /*
	     * con = PlmDBUtil.getConnection();
	     * 
	     * sql = getSearchEcnListSQL(isExcel ,hash, conditionList); Kogger.debug(getClass(),
	     * " ########################################  sql  ==  " + sql );
	     * 
	     * pstmt = con.prepareStatement(sql); int resultRows = 0;
	     * 
	     * rs = pstmt.executeQuery();
	     * 
	     * EcmComDao ecmComDao = new EcmComDao(con);
	     */

	    sql = getSearchEcnListSQL(isExcel, paramMap, pager);

	    /*
	     * sql = oDaoManager.getOrderByQuery(sql, pager); sql = oDaoManager.getPagingQuery(sql, pager);
	     */

	    resultList = oDaoManager.queryForList(sql, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    Map<String, Object> ketSearchEcnDetailVO = new HashMap<String, Object>();

		    ketSearchEcnDetailVO.put("EcnNo", StringUtil.checkNull(rs.getString("ecnNo")));
		    ketSearchEcnDetailVO.put("EcnOid", StringUtil.checkNull(rs.getString("ecnOid")));
		    ketSearchEcnDetailVO.put("StateAppro", StringUtil.checkNull(rs.getString("stateAppro")));
		    ketSearchEcnDetailVO.put("StateApproName", StringUtil.checkNull(rs.getString("stateApproName")));
		    ketSearchEcnDetailVO.put("ReceiveConfirm", StringUtil.checkNull(rs.getString("receiveConfirm")));
		    ketSearchEcnDetailVO.put("JobComplete", StringUtil.checkNull(rs.getString("jobComplete")));

		    String strJobCompleteDate = DateUtil.getDateString(rs.getTimestamp("jobCompleteDate"), "date");
		    ketSearchEcnDetailVO.put("JobCompleteDate", StringUtil.checkNull(strJobCompleteDate));

		    ketSearchEcnDetailVO.put("EcoOid", StringUtil.checkNull(rs.getString("ecoOid")));
		    ketSearchEcnDetailVO.put("EcoNo", StringUtil.checkNull(rs.getString("ecoNo")));
		    ketSearchEcnDetailVO.put("EcoName", StringUtil.checkNull(rs.getString("ecoName")));
		    ketSearchEcnDetailVO.put("ChangeReason", StringUtil.checkNull(rs.getString("changeReason")));
		    ketSearchEcnDetailVO.put("Creator", StringUtil.checkNull(rs.getString("creator")));

		    String strCreateDate = DateUtil.getDateString(rs.getTimestamp("createDate"), "date");
		    ketSearchEcnDetailVO.put("CreateDate", StringUtil.checkNull(strCreateDate));

		    ketSearchEcnDetailVO.put("ProjectOid", StringUtil.checkNull(rs.getString("projectOid")));
		    ketSearchEcnDetailVO.put("ProjectNo", StringUtil.checkNull(rs.getString("projectNo")));
		    // ketSearchEcnDetailVO.put("ProjectName", StringUtil.checkNull(rs.getString("projectName")));

		    ketSearchEcnDetailVO.put("WorkerName", StringUtil.checkNull(rs.getString("workername")));
		    ketSearchEcnDetailVO.put("DeptName", StringUtil.checkNull(rs.getString("deptname")));

		    String strRequestDate = DateUtil.getDateString(rs.getTimestamp("requestdate"), "date");
		    ketSearchEcnDetailVO.put("RequestDate", StringUtil.checkNull(strRequestDate));

		    ketSearchEcnDetailVO.put("CustomName", StringUtil.checkNull(rs.getString("customname")));

		    return ketSearchEcnDetailVO;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}

	return resultList;
    }

    public int searchEcnListCount(boolean isExcel, KETParamMapUtil paramMap) throws Exception {
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	String sql = getSearchEcnListSQL(isExcel, paramMap);
	int totalCount = oDaoManager.queryForCount(sql);

	return totalCount;
    }

    private String getSearchEcnListSQL(boolean isExcel, KETParamMapUtil paramMap) throws Exception {
	return this.getSearchEcnListSQL(isExcel, paramMap, null);
    }

    private String getSearchEcnListSQL(boolean isExcel, KETParamMapUtil paramMap, TgPagingControl pager) throws Exception {
	StringBuffer sql = new StringBuffer();

	sql.append(" SELECT * \n");
	sql.append("   FROM ( \n");
	sql.append(" SELECT t.* \n");
	if (pager != null) {

	    String sortCol = pager.getSortCol();
	    String sortType = pager.getSortType();

	    if (sortCol != null && !"".equals(sortCol.trim())) {
		sql.append(" , row_number() over (").append(" ORDER BY ").append(sortCol).append(" ").append(sortType).append(") row_id \n");

	    }
	} else {
	    sql.append(" , row_number() over (order by createDate desc) row_id \n");

	}
	sql.append("   FROM ( \n");

	sql.append("         SELECT \n");

	if (isExcel) {
	    sql.append("              ecnNo \n");
	} else {
	    sql.append("              SUBSTR(ecnNo, 5) AS ecnNo \n");
	}

	sql.append("                , ecnOid \n");
	sql.append("                , stateAppro \n");
	sql.append("                , stateApproName \n");
	sql.append("                , receiveConfirm \n");
	sql.append("                , jobComplete \n");
	sql.append("                , jobCompleteDate \n");
	sql.append("                , ecoOid \n");

	if (isExcel) {
	    sql.append("                , ecoNo \n");
	} else {
	    sql.append("                , SUBSTR(ecoNo, 5) AS ecoNo \n");
	}

	sql.append("                , ecoName \n");
	sql.append("                , changeReason \n");
	sql.append("                , creator \n");
	sql.append("                , createDate \n");
	sql.append("                , projectOid \n");
	// sql.append("                , DECODE(projectNo_product, null, DECODE(projectNo_mold, null, DECODE(projectNo_review, null, '', projectNo_review), projectNo_mold), projectNo_product) AS projectNo \n");
	sql.append("		    , projectNo \n");
	// sql.append("                , DECODE(projectName_product, null, DECODE(projectName_mold, null, DECODE(projectName_review, null, '', projectName_review), projectName_mold), projectName_product) AS projectName \n");
	// sql.append("		    , projectName \n");
	sql.append("		    , workername \n");
	sql.append("		    , deptname \n");
	sql.append("		    , requestdate \n");
	sql.append("		    , customname \n");
	sql.append("           FROM ( \n");

	// 제품
	sql.append("                  SELECT   ecn.ecnnumber AS ecnNo \n");
	sql.append("                         , ecn.classnamea2a2||':'||ecn.IDA2A2 AS ecnOid \n");
	sql.append("                         , ecn.statestate AS stateAppro \n");
	sql.append("                         , ( SELECT max(pl.name) \n");
	sql.append("                               FROM phasetemplate ph \n");
	sql.append("                                  , PHASE pl \n");
	sql.append("                              WHERE 1 = 1 \n");
	sql.append("                                AND pl.idA3A4 = ph.idA2A2 \n");
	sql.append("                                AND ph.phaseState = ecn.stateState ) AS stateApproName \n");

	// sql.append("                         , ( \n");
	// sql.append("                                 SELECT SUM(DECODE(act.receiveConfirmedDate, null, 0, 1)) ||'/'|| COUNT(*) \n");
	// sql.append("                                   FROM KETProdChangeActivity act \n");
	// sql.append("                                  WHERE act.IDA3A8 = eco.IDA2A2 \n");
	// sql.append("                                    AND act.activitytype IN ('3', '4') \n");
	// sql.append("                           ) AS receiveConfirm \n");
	// sql.append("                         , ( \n");
	// sql.append("                                 SELECT SUM(DECODE(act.completeDate, null, 0, 1)) ||'/'|| COUNT(*) \n");
	// sql.append("                                   FROM KETProdChangeActivity act \n");
	// sql.append("                                  WHERE act.IDA3A8 = eco.IDA2A2 \n");
	// sql.append("                                    AND act.activitytype IN ('3', '4') \n");
	// sql.append("                           ) AS jobComplete \n");

	sql.append(" , DECODE(act.receiveConfirmedDate, null, 0, 1) receiveConfirm	\n");
	sql.append(" , DECODE(act.completeDate, null, 0, 1) AS jobComplete 		\n");

	sql.append("                         , act.completeDate AS jobCompleteDate \n");
	sql.append("                         , eco.classnamea2a2||':'||eco.IDA2A2 AS ecoOid \n");
	sql.append("                         , eco.ecoid AS ecoNo \n");
	sql.append("                         , eco.econame AS ecoName \n");
	sql.append("                         , FN_GET_CODE_NAME_STR('CHANGEREASON', changeReason) AS changeReason \n");
	sql.append("                         , (SELECT st2.name FROM WTUser st1 , People st2  WHERE st2.IDA3B4 = st1.IDA2A2 AND st1.IDA2A2 = eco.IDA3B7) AS creator \n");
	sql.append("                         , eco.createstampa2 AS createDate \n");
	sql.append("                         , eco.projectoid AS projectOid \n");
	sql.append("                         , (SELECT max(e.pjtno) AS pjtno FROM e3psprojectmaster e, productProject p WHERE p.ida2a2 = substr(eco.projectoid, instr( eco.projectoid, ':')+1, length(eco.projectoid)) AND p.ida3b8 = e.ida2a2) AS projectNo \n");
	// sql.append("                         , (SELECT max(e.pjtname) AS pjtname FROM e3psprojectmaster e, productProject p WHERE p.ida2a2 = substr(eco.projectoid, instr( eco.projectoid, ':')+1, length(eco.projectoid))) AS projectName \n");
	sql.append("                         , (select fullname from wtuser where WTUSER.CLASSNAMEA2A2||':'||WTUSER.ida2a2 = ACT.workerid) as workername \n");
	sql.append("			     , (select b.name from people a, department b where a.classnamekeyb4 ||':'||a.ida3b4 = ACT.workerid and a.ida3c4 = b.ida2a2) as deptname \n");
	sql.append("                         , ACT.COMPLETEREQUESTDATE AS requestdate \n");
	sql.append("                         , ACT.customname AS customname \n");
	// sql.append("                         , (SELECT MAX(e.pjtno) AS pjtno FROM e3psprojectmaster e, productProject p WHERE p.ida2a2 = substr(eco.projectoid, instr( eco.projectoid, ':')+1, length(eco.projectoid)) AND p.ida3b8 = e.ida2a2) AS projectNo_product \n");
	// sql.append("                         , (SELECT MAX(e.pjtname) AS pjtname FROM e3psprojectmaster e, productProject p WHERE p.ida2a2 = substr(eco.projectoid, instr( eco.projectoid, ':')+1, length(eco.projectoid))) AS projectName_product \n");
	// sql.append("                         , (SELECT MAX(e.pjtno) AS pjtno FROM e3psprojectmaster e, moldProject p WHERE p.ida2a2 = substr(eco.projectoid, instr( eco.projectoid, ':')+1, length(eco.projectoid)) AND p.ida3b8 = e.ida2a2) AS projectNo_mold \n");
	// sql.append("                         , (SELECT MAX(e.pjtname) AS pjtname FROM e3psprojectmaster e, moldProject p WHERE p.ida2a2 = substr(eco.projectoid, instr( eco.projectoid, ':')+1, length(eco.projectoid))) AS projectName_mold \n");
	// sql.append("                         , (SELECT MAX(e.pjtno) AS pjtno FROM e3psprojectmaster e, reviewProject p WHERE p.ida2a2 = substr(eco.projectoid, instr( eco.projectoid, ':')+1, length(eco.projectoid)) AND p.ida3b8 = e.ida2a2) AS projectNo_review \n");
	// sql.append("                         , (SELECT MAX(e.pjtname) AS pjtname FROM e3psprojectmaster e, reviewProject p WHERE p.ida2a2 = substr(eco.projectoid, instr( eco.projectoid, ':')+1, length(eco.projectoid))) AS projectName_review \n");

	sql.append("                    FROM   KETChangeNotice ecn \n");
	sql.append("                         , KETEcoEcnLink nolink \n");
	sql.append("                         , KETProdChangeOrder eco \n");
	sql.append("                         , KETProdChangeActivity ACT  \n");
	sql.append("                   WHERE ecn.IDA2A2 = nolink.IDA3B5 AND nolink.IDA3A5 = eco.IDA2A2 AND ECO.IDA2A2 = ACT.IDA3A8 AND act.activitytype IN ('3', '4') \n");

	// Where
	String ecoId = paramMap.getString("ecoId");
	String ecoName = paramMap.getString("ecoName");
	// String creatorName = paramMap.getString("creatorName");
	String creatorOid = paramMap.getString("creatorOid");
	// String creator = paramMap.getString("creator");
	// String projectNo = paramMap.getString("projectNo");
	String projectOid = paramMap.getString("projectOid");
	String projectName = paramMap.getString("projectName");
	String createStartDate = paramMap.getString("createStartDate");
	String createEndDate = paramMap.getString("createEndDate");
	String sancStateFlag = paramMap.getString("sancStateFlag");
	String completeStartDate = paramMap.getString("completeStartDate");
	String completeEndDate = paramMap.getString("completeEndDate");
	String ecnNumber = paramMap.getString("ecnNumber");
	// String ecnOid = paramMap.getString("ecnOid");
	String ecnUserOid = paramMap.getString("ecnUserOid");
	String deptCode = paramMap.getString("deptCode");

	if (ecnUserOid != null && !ecnUserOid.equals("")) {
	    sql.append(" AND ACT.workerid = '" + ecnUserOid + "' \n");
	}

	if (deptCode != null && !deptCode.equals("")) {

	    String deptOid = CommonUtil.getOIDLongValue2Str(deptCode);

	    sql.append(" AND ACT.WORKERID IN (SELECT WT.CLASSNAMEA2A2||':'||WT.IDA2A2 								\n");
	    sql.append("                        FROM (SELECT * FROM DEPARTMENT DPART WHERE CONNECT_BY_ISLEAF = 1 				\n");
	    sql.append("                               START WITH DPART.IDA2A2 = '" + deptOid + "' AND ENABLED = 1 					\n");
	    sql.append("                               CONNECT BY PRIOR DPART.IDA2A2 = IDA3PARENTREFERENCE) DATA, PEOPLE PE, WTUSER WT 		\n");
	    sql.append("                       WHERE PE.IDA3C4 = DATA.IDA2A2 AND PE.IDA3B4 = WT.IDA2A2) 					\n");
	}

	if (ecoId != null && !ecoId.equals("")) {
	    sql.append(" AND UPPER(eco.ecoid) LIKE UPPER('%" + ecoId + "%') \n");
	}
	if (ecoName != null && !ecoName.equals("")) {
	    sql.append(" AND UPPER(eco.econame) LIKE UPPER('%" + ecoName + "%') \n");
	}
	if (creatorOid != null && !creatorOid.equals("")) {
	    sql.append(" AND eco.IDA3B7 = " + KETParamMapUtil.OidToString(creatorOid) + " \n");
	}

	// 로그인 사용자가 KETS 일 경우
	if (CommonUtil.isKETSUser()) {
	    sql.append(CommonUtil.ketsUserListWhereStr("eco.IDA3B7"));
	}

	if (projectOid != null && !projectOid.equals("")) {
	    // sql.append(" AND eco.projectoid = '" + projectOid + "' \n");

	    // 제품
	    sql.append(" AND eco.projectOid IN ( \n");
	    sql.append("               SELECT DISTINCT PPR.CLASSNAMEA2A2||':'||PPR.IDA2A2 \n");
	    sql.append("                 FROM E3PSPROJECTMASTER EMA \n");
	    sql.append("                    , PRODUCTPROJECT PPR \n");
	    sql.append("                WHERE EMA.IDA2A2 = PPR.IDA3B8 \n");
	    sql.append("                  AND EMA.PJTNO IN ( \n");
	    sql.append("                                    SELECT DISTINCT EMA.PJTNO \n");
	    sql.append("                                     FROM E3PSPROJECTMASTER EMA \n");
	    sql.append("                                        , PRODUCTPROJECT PPR \n");
	    sql.append("                                    WHERE EMA.IDA2A2 = PPR.IDA3B8 \n");
	    sql.append("                                      AND PPR.CLASSNAMEA2A2||':'||PPR.IDA2A2 = '" + projectOid + "' \n");
	    sql.append("                                   ) \n");

	    sql.append("                UNION ALL \n");

	    // 금형
	    sql.append("               SELECT DISTINCT PPR.CLASSNAMEA2A2||':'||PPR.IDA2A2 \n");
	    sql.append("                 FROM E3PSPROJECTMASTER EMA \n");
	    sql.append("                    , MOLDPROJECT PPR \n");
	    sql.append("                WHERE EMA.IDA2A2 = PPR.IDA3B8 \n");
	    sql.append("                  AND EMA.PJTNO IN ( \n");
	    sql.append("                                    SELECT DISTINCT EMA.PJTNO \n");
	    sql.append("                                     FROM E3PSPROJECTMASTER EMA \n");
	    sql.append("                                        , MOLDPROJECT PPR \n");
	    sql.append("                                    WHERE EMA.IDA2A2 = PPR.IDA3B8 \n");
	    sql.append("                                      AND PPR.CLASSNAMEA2A2||':'||PPR.IDA2A2 = '" + projectOid + "' \n");
	    sql.append("                                   ) \n");
	    sql.append("                      ) \n");

	}
	if (projectName != null && !projectName.equals("")) {
	    sql.append(" AND ( \n");
	    sql.append("      eco.projectoid IN ( \n");
	    sql.append("                        SELECT p.classnamea2a2||':'||p.ida2a2 \n");
	    sql.append("                          FROM e3psprojectmaster e, \n");
	    sql.append("                               productproject p \n");
	    sql.append("                         WHERE e.ida2a2 = p.ida3b8 \n");
	    sql.append("                           AND UPPER(e.pjtname) LIKE UPPER('%" + projectName + "%') \n");
	    sql.append("                       ) \n");

	    sql.append("      OR eco.projectoid IN ( \n");
	    sql.append("                        SELECT p.classnamea2a2||':'||p.ida2a2 \n");
	    sql.append("                          FROM e3psprojectmaster e, \n");
	    sql.append("                               moldproject p \n");
	    sql.append("                         WHERE e.ida2a2 = p.ida3b8 \n");
	    sql.append("                           AND UPPER(e.pjtname) LIKE UPPER('%" + projectName + "%') \n");
	    sql.append("                       ) \n");

	    sql.append("      OR eco.projectoid IN ( \n");
	    sql.append("                        SELECT p.classnamea2a2||':'||p.ida2a2 \n");
	    sql.append("                          FROM e3psprojectmaster e, \n");
	    sql.append("                               REVIEWPROJECT p \n");
	    sql.append("                         WHERE e.ida2a2 = p.ida3b8 \n");
	    sql.append("                           AND UPPER(e.pjtname) LIKE UPPER('%" + projectName + "%') \n");
	    sql.append("                       ) \n");
	    sql.append("     ) \n");
	}
	if (createStartDate != null && !createStartDate.equals("")) {
	    createStartDate = createStartDate.substring(0, 4) + createStartDate.substring(5, 7) + createStartDate.substring(8, 10);
	    sql.append(" AND eco.createStampA2 >= TO_DATE('" + createStartDate + "' || '000000','YYYYMMDDHH24MISS') \n");
	}
	if (createEndDate != null && !createEndDate.equals("")) {
	    createEndDate = createEndDate.substring(0, 4) + createEndDate.substring(5, 7) + createEndDate.substring(8, 10);
	    sql.append(" AND eco.createStampA2 <= TO_DATE('" + createEndDate + "' || '235959','YYYYMMDDHH24MISS') \n");
	}
	if (sancStateFlag != null && !sancStateFlag.equals("")) {
	    if ("DELAY".equals(sancStateFlag)) {
		sql.append(" and act.completeDate is null and TO_CHAR(ACT.COMPLETEREQUESTDATE,'YYYYMMDD') < TO_CHAR(SYSDATE,'YYYYMMDD') AND ECO.STATESTATE = 'APPROVED' \n");
	    } else {
		sql.append(" AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("ecn.stateState", sancStateFlag, false)).append(" \n");
	    }

	}
	if (completeStartDate != null && !completeStartDate.equals("")) {
	    completeStartDate = completeStartDate.substring(0, 4) + completeStartDate.substring(5, 7) + completeStartDate.substring(8, 10);
	    sql.append(" AND ecn.completeDate >= TO_DATE('" + completeStartDate + "' || '000000','YYYYMMDDHH24MISS') \n");
	}
	if (completeEndDate != null && !completeEndDate.equals("")) {
	    completeEndDate = completeEndDate.substring(0, 4) + completeEndDate.substring(5, 7) + completeEndDate.substring(8, 10);
	    sql.append(" AND ecn.completeDate <= TO_DATE('" + completeEndDate + "' || '235959','YYYYMMDDHH24MISS') \n");
	}
	if (ecnNumber != null && !ecnNumber.equals("")) {
	    sql.append(" AND UPPER(ecn.ecnnumber) LIKE UPPER('%" + ecnNumber + "%') \n");
	}

	// UNION ALL
	sql.append("                  UNION ALL \n");

	// 금형
	sql.append("                  SELECT   ecn.ecnnumber AS ecnNo \n");
	sql.append("                         , ecn.classnamea2a2||':'||ecn.IDA2A2 AS ecnOid \n");
	sql.append("                         , ecn.statestate AS stateAppro \n");
	sql.append("                         , ( SELECT max(pl.name) \n");
	sql.append("                               FROM phasetemplate ph \n");
	sql.append("                                  , PHASE pl \n");
	sql.append("                              WHERE 1 = 1 \n");
	sql.append("                                AND pl.idA3A4 = ph.idA2A2 \n");
	sql.append("                                AND ph.phaseState = ecn.stateState ) AS stateApproName \n");

	// sql.append("                         , ( \n");
	// sql.append("                                 SELECT SUM(DECODE(act.receiveConfirmedDate, null, 0, 1)) ||'/'|| COUNT(*) \n");
	// sql.append("                                   FROM KETMoldChangeActivity act \n");
	// sql.append("                                  WHERE act.IDA3A8 = eco.IDA2A2 \n");
	// sql.append("                                    AND act.activitytype IN ('4') \n"); // 표준품 리스트는 제외
	// sql.append("                           ) AS receiveConfirm \n");
	// sql.append("                         , ( \n");
	// sql.append("                                 SELECT SUM(DECODE(act.completeDate, null, 0, 1)) ||'/'|| COUNT(*) \n");
	// sql.append("                                   FROM KETMoldChangeActivity act \n");
	// sql.append("                                  WHERE act.IDA3A8 = eco.IDA2A2 \n");
	// sql.append("                                    AND act.activitytype IN ('4') \n"); // 표준품 리스트는 제외
	// sql.append("                           ) AS jobComplete \n");

	sql.append(" , DECODE(act.receiveConfirmedDate, null, 0, 1) receiveConfirm	\n");
	sql.append(" , DECODE(act.completeDate, null, 0, 1) AS jobComplete 		\n");
	sql.append("                         , act.completeDate AS jobCompleteDate \n");
	sql.append("                         , eco.classnamea2a2||':'||eco.IDA2A2 AS ecoOid \n");
	sql.append("                         , eco.ecoid AS ecoNo \n");
	sql.append("                         , eco.econame AS ecoName \n");
	sql.append("                         , FN_GET_CODE_NAME_STR('CHANGEREASON', changeReason) AS changeReason \n");
	sql.append("                         , (SELECT st2.name FROM WTUser st1 , People st2  WHERE st2.IDA3B4 = st1.IDA2A2 AND st1.IDA2A2 = eco.IDA3B7) AS creator \n");
	sql.append("                         , eco.createstampa2 AS createDate \n");
	sql.append("                         , eco.projectoid AS projectOid \n");
	sql.append("                         , (SELECT max(e.pjtno) AS pjtno FROM e3psprojectmaster e, moldProject p WHERE p.ida2a2 = substr(eco.projectoid, instr( eco.projectoid, ':')+1, length(eco.projectoid)) AND p.ida3b8 = e.ida2a2) AS projectNo \n");
	// sql.append("                         , (SELECT max(e.pjtname) AS pjtname FROM e3psprojectmaster e, moldProject p WHERE p.ida2a2 = substr(eco.projectoid, instr( eco.projectoid, ':')+1, length(eco.projectoid))) AS projectName \n");
	sql.append("                         , (select fullname from wtuser where WTUSER.CLASSNAMEA2A2||':'||WTUSER.ida2a2 = ACT.workerid) as workername \n");
	sql.append("			     , (select b.name from people a, department b where a.classnamekeyb4 ||':'||a.ida3b4 = ACT.workerid and a.ida3c4 = b.ida2a2) as deptname \n");
	sql.append("                         , ACT.COMPLETEREQUESTDATE AS requestdate \n");
	sql.append("                         , ACT.customname AS customname \n");
	// sql.append("                         , (SELECT MAX(e.pjtno) AS pjtno FROM e3psprojectmaster e, productProject p WHERE p.ida2a2 = substr(eco.projectoid, instr( eco.projectoid, ':')+1, length(eco.projectoid)) AND p.ida3b8 = e.ida2a2) AS projectNo_product \n");
	// sql.append("                         , (SELECT MAX(e.pjtname) AS pjtname FROM e3psprojectmaster e, productProject p WHERE p.ida2a2 = substr(eco.projectoid, instr( eco.projectoid, ':')+1, length(eco.projectoid))) AS projectName_product \n");
	// sql.append("                         , (SELECT MAX(e.pjtno) AS pjtno FROM e3psprojectmaster e, moldProject p WHERE p.ida2a2 = substr(eco.projectoid, instr( eco.projectoid, ':')+1, length(eco.projectoid)) AND p.ida3b8 = e.ida2a2) AS projectNo_mold \n");
	// sql.append("                         , (SELECT MAX(e.pjtname) AS pjtname FROM e3psprojectmaster e, moldProject p WHERE p.ida2a2 = substr(eco.projectoid, instr( eco.projectoid, ':')+1, length(eco.projectoid))) AS projectName_mold \n");
	// sql.append("                         , (SELECT MAX(e.pjtno) AS pjtno FROM e3psprojectmaster e, reviewProject p WHERE p.ida2a2 = substr(eco.projectoid, instr( eco.projectoid, ':')+1, length(eco.projectoid)) AND p.ida3b8 = e.ida2a2) AS projectNo_review \n");
	// sql.append("                         , (SELECT MAX(e.pjtname) AS pjtname FROM e3psprojectmaster e, reviewProject p WHERE p.ida2a2 = substr(eco.projectoid, instr( eco.projectoid, ':')+1, length(eco.projectoid))) AS projectName_review \n");

	sql.append("                    FROM   KETChangeNotice ecn \n");
	sql.append("                         , KETEcoEcnLink nolink \n");
	sql.append("                         , KETMoldChangeOrder eco \n");
	sql.append("                         , KETMoldChangeActivity ACT  \n");
	sql.append("                   WHERE ecn.IDA2A2 = nolink.IDA3B5 AND nolink.IDA3A5 = eco.IDA2A2 AND ECO.IDA2A2 = ACT.IDA3A8 AND act.activitytype IN ('4') \n");

	if (ecnUserOid != null && !ecnUserOid.equals("")) {
	    sql.append(" AND ACT.workerid = '" + ecnUserOid + "' \n");
	}

	if (deptCode != null && !deptCode.equals("")) {

	    String deptOid = CommonUtil.getOIDLongValue2Str(deptCode);

	    sql.append(" AND ACT.WORKERID IN (SELECT WT.CLASSNAMEA2A2||':'||WT.IDA2A2 								\n");
	    sql.append("                        FROM (SELECT * FROM DEPARTMENT DPART WHERE CONNECT_BY_ISLEAF = 1 				\n");
	    sql.append("                               START WITH DPART.IDA2A2 = '" + deptOid + "' AND ENABLED = 1 					\n");
	    sql.append("                               CONNECT BY PRIOR DPART.IDA2A2 = IDA3PARENTREFERENCE) DATA, PEOPLE PE, WTUSER WT 		\n");
	    sql.append("                       WHERE PE.IDA3C4 = DATA.IDA2A2 AND PE.IDA3B4 = WT.IDA2A2) 					\n");
	}

	if (ecoId != null && !ecoId.equals("")) {
	    sql.append(" AND UPPER(eco.ecoid) LIKE UPPER('%" + ecoId + "%') \n");
	}
	if (ecoName != null && !ecoName.equals("")) {
	    sql.append(" AND UPPER(eco.econame) LIKE UPPER('%" + ecoName + "%') \n");
	}
	if (creatorOid != null && !creatorOid.equals("")) {
	    sql.append(" AND eco.IDA3B7 = " + KETParamMapUtil.OidToString(creatorOid) + " \n");
	}
	if (projectOid != null && !projectOid.equals("")) {
	    sql.append(" AND eco.projectoid = '" + projectOid + "' \n");
	}
	if (projectName != null && !projectName.equals("")) {
	    sql.append(" AND ( \n");
	    sql.append("      eco.projectoid IN ( \n");
	    sql.append("                        SELECT p.classnamea2a2||':'||p.ida2a2 \n");
	    sql.append("                          FROM e3psprojectmaster e, \n");
	    sql.append("                               productproject p \n");
	    sql.append("                         WHERE e.ida2a2 = p.ida3b8 \n");
	    sql.append("                           AND UPPER(e.pjtname) LIKE UPPER('%" + projectName + "%') \n");
	    sql.append("                       ) \n");

	    sql.append("      OR eco.projectoid IN ( \n");
	    sql.append("                        SELECT p.classnamea2a2||':'||p.ida2a2 \n");
	    sql.append("                          FROM e3psprojectmaster e, \n");
	    sql.append("                               moldproject p \n");
	    sql.append("                         WHERE e.ida2a2 = p.ida3b8 \n");
	    sql.append("                           AND UPPER(e.pjtname) LIKE UPPER('%" + projectName + "%') \n");
	    sql.append("                       ) \n");

	    sql.append("      OR eco.projectoid IN ( \n");
	    sql.append("                        SELECT p.classnamea2a2||':'||p.ida2a2 \n");
	    sql.append("                          FROM e3psprojectmaster e, \n");
	    sql.append("                               REVIEWPROJECT p \n");
	    sql.append("                         WHERE e.ida2a2 = p.ida3b8 \n");
	    sql.append("                           AND UPPER(e.pjtname) LIKE UPPER('%" + projectName + "%') \n");
	    sql.append("                       ) \n");

	    sql.append("     ) \n");
	}
	if (createStartDate != null && !createStartDate.equals("")) {
	    // createStartDate = createStartDate.substring(0, 4) + createStartDate.substring(5, 7) + createStartDate.substring(8, 10);
	    sql.append(" AND eco.createStampA2 >= TO_DATE('" + createStartDate + "' || '000000','YYYYMMDDHH24MISS') \n");
	}
	if (createEndDate != null && !createEndDate.equals("")) {
	    // createEndDate = createEndDate.substring(0, 4) + createEndDate.substring(5, 7) + createEndDate.substring(8, 10);
	    sql.append(" AND eco.createStampA2 <= TO_DATE('" + createEndDate + "' || '235959','YYYYMMDDHH24MISS') \n");
	}
	if (sancStateFlag != null && !sancStateFlag.equals("")) {
	    if ("DELAY".equals(sancStateFlag)) {
		sql.append(" and act.completeDate is null and TO_CHAR(ACT.COMPLETEREQUESTDATE,'YYYYMMDD') < TO_CHAR(SYSDATE,'YYYYMMDD') AND ECO.STATESTATE = 'APPROVED' \n");
	    } else {
		sql.append(" AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("ecn.stateState", sancStateFlag, false)).append(" \n");
	    }

	}
	if (completeStartDate != null && !completeStartDate.equals("")) {
	    // completeStartDate = completeStartDate.substring(0, 4) + completeStartDate.substring(5, 7) + completeStartDate.substring(8,
	    // 10);
	    sql.append(" AND ecn.completeDate >= TO_DATE('" + completeStartDate + "' || '000000','YYYYMMDDHH24MISS') \n");
	}
	if (completeEndDate != null && !completeEndDate.equals("")) {
	    // completeEndDate = completeEndDate.substring(0, 4) + completeEndDate.substring(5, 7) + completeEndDate.substring(8, 10);
	    sql.append(" AND ecn.completeDate <= TO_DATE('" + completeEndDate + "' || '235959','YYYYMMDDHH24MISS') \n");
	}
	if (ecnNumber != null && !ecnNumber.equals("")) {
	    sql.append(" AND UPPER(ecn.ecnnumber) LIKE UPPER('%" + ecnNumber + "%') \n");
	}
	sql.append("                ) \n");

	// Order by
	/*
	 * if (pager != null) {
	 * 
	 * String sortCol = pager.getSortCol(); String sortType = pager.getSortType();
	 * 
	 * if (sortCol != null && !"".equals(sortCol.trim())) {
	 * sql.append(" ORDER BY ").append(sortCol).append(" ").append(sortType).append(" \n");
	 * 
	 * }
	 * 
	 * }
	 */

	sql.append("        ) t \n");
	// ECN이 아예 붙어있지 않는 경우는 제외한다.
	// sql.append(" WHERE receiveConfirm != '/0' \n");
	// sql.append(" WHERE receiveConfirm != '0' \n");

	sql.append("    )\n");

	// Paging
	if (pager != null) {

	    String sPage = String.valueOf(pager.getCurrentPageNo() + 1);
	    String sPageRowCnt = String.valueOf(pager.getPageSize());

	    sql.append(" WHERE row_id BETWEEN (").append(sPage).append("-1) * ").append(sPageRowCnt).append("+1 AND (").append(sPage).append(") * ").append(sPageRowCnt).append(" \n");

	}

	// sql.append( "       ) \n" );

	Kogger.debug(getClass(), sql.toString());
	return sql.toString();
    }

    public List<Map<String, Object>> searchMyEcmList(boolean isExcel, KETParamMapUtil hash, List<Map<String, Object>> conditionList, TgPagingControl pager) throws Exception {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	// Map<String, Object> ketSearchEcoDetailVO = null;
	List<Map<String, Object>> ecoMonthlyReportList = new ArrayList<Map<String, Object>>();

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	try {

	    /*
	     * con = PlmDBUtil.getConnection();
	     * 
	     * sql = getSearchEcoListSQL(isExcel ,hash, conditionList); Kogger.debug(getClass(),
	     * " ########################################  sql  ==  " + sql );
	     * 
	     * pstmt = con.prepareStatement(sql); int resultRows = 0;
	     * 
	     * rs = pstmt.executeQuery();
	     * 
	     * EcmComDao ecmComDao = new EcmComDao(con);
	     */

	    sql = this.getSearchMyEcmListSQL(isExcel, hash, conditionList, pager);

	    /*
	     * sql = oDaoManager.getOrderByQuery(sql, pager); sql = oDaoManager.getPagingQuery(sql, pager);
	     */

	    ecoMonthlyReportList = oDaoManager.queryForList(sql, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    Map<String, Object> ketSearchEcoDetailVO = new HashMap<String, Object>();

		    // PBO OID
		    ketSearchEcoDetailVO.put("Oid", (rs.getString("oid")));
		    // 구분
		    ketSearchEcoDetailVO.put("ProdMoldClsName", (rs.getString("prodMoldClsName")));
		    // No
		    ketSearchEcoDetailVO.put("EcoId", (StringUtil.checkNull(rs.getString("ecoId"))));// 1.
		    // 제목
		    ketSearchEcoDetailVO.put("EcoName", (StringUtil.checkNull(rs.getString("ecoName"))));// 2
		    // 부품번호
		    ketSearchEcoDetailVO.put("PartNumber", StringUtil.checkNull(rs.getString("partNumber2")));
		    // 부품명
		    ketSearchEcoDetailVO.put("PartName", StringUtil.checkNull(rs.getString("partName2")));
		    // 설계변경사유
		    // 제품 ECO, ECR
		    if (rs.getString("oid").lastIndexOf("KETProdChangeOrder") > 0 || rs.getString("oid").lastIndexOf("KETProdChangeRequest") > 0) {
			ketSearchEcoDetailVO.put("ChangeReason", StringUtil.checkNull(rs.getString("prodecoReason2")));

		    }
		    // 금형 ECO, ECR
		    else if (rs.getString("oid").lastIndexOf("KETMoldChangeOrder") > 0 || rs.getString("oid").lastIndexOf("KETMoldChangeRequest") > 0) {
			ketSearchEcoDetailVO.put("ChangeReason", StringUtil.checkNull(rs.getString("changeReason2")));

		    } else {
			ketSearchEcoDetailVO.put("ChangeReason", "");

		    }
		    // 설계변경사유 중 기타에 대한 사용자 입력값
		    if (StringUtil.checkNull(rs.getString("other_reason")).length() > 0) {
			ketSearchEcoDetailVO.put("OtherChgReason", ("(" + StringUtil.checkNull(rs.getString("other_reason")) + ")"));
		    } else {
			ketSearchEcoDetailVO.put("OtherChgReason", (StringUtil.checkNull(rs.getString("other_reason"))));
		    }
		    // 예상비용
		    ketSearchEcoDetailVO.put("SecureBudgetYn", (StringUtil.checkNull(rs.getString("secureBudgetYn"))));// 6
		    // 작성자
		    ketSearchEcoDetailVO.put("CreatorName", (rs.getString("creatorName")));// 7
		    // 작성일
		    String strCreateDate = DateUtil.getDateString(rs.getTimestamp("createDate"), "date");
		    ketSearchEcoDetailVO.put("CreateDate", (strCreateDate));// 8
		    // 상태
		    ketSearchEcoDetailVO.put("SancStateFlag", (rs.getString("stateStateName")));// 9
		    // 프로젝트 관련 정보
		    String projectOid = StringUtil.checkNull(rs.getString("projectOid"));
		    ketSearchEcoDetailVO.put("ProjectNo", (StringUtil.checkNull(rs.getString("projectNo"))));
		    ketSearchEcoDetailVO.put("ProjectOid", projectOid);

		    return ketSearchEcoDetailVO;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}

	return ecoMonthlyReportList;
    }

    public int searchMyEcmListCount(boolean isExcel, KETParamMapUtil hash, List<Map<String, Object>> conditionList) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	String sql = getSearchMyEcmListSQL(isExcel, hash, conditionList);
	int totalCount = oDaoManager.queryForCount(sql);

	return totalCount;
    }

    private String getSearchMyEcmListSQL(boolean isExcel, KETParamMapUtil hash, List<Map<String, Object>> conditionList) throws Exception {

	return this.getSearchMyEcmListSQL(isExcel, hash, conditionList, null);
    }

    private String getSearchMyEcmListSQL(boolean isExcel, KETParamMapUtil hash, List<Map<String, Object>> conditionList, TgPagingControl pager) throws Exception {
	StringBuffer sql = new StringBuffer();

	// 제품/금형+ECR/ECO 구분
	String prodMoldCls = hash.getString("prodMoldCls");

	sql.append(" SELECT t.* \n");
	sql.append("   FROM ( \n");

	// 엑셀 구분
	sql.append("         SELECT oid oid \n");
	sql.append("              , prodMoldClsName prodMoldClsName \n");

	if (isExcel) {
	    sql.append("          , ecoId ecoId \n");
	} else {
	    sql.append("          , substr(ecoId, 5) ecoId \n");
	}
	sql.append("              , ecoName ecoName \n");

	// FUNCTION 생성 : FN_GET_PART_NO_NAME_STR
	sql.append("              , FN_GET_PART_NO_NAME_STR(classname2, idvalue2, 'NUMBER') AS partNumber2 \n");
	sql.append("              , FN_GET_PART_NO_NAME_STR(classname2, idvalue2, 'NAME') AS partName2 \n");

	// FUNCTION 생성 : FN_GET_CODE_NAME
	sql.append("              , changeReason2 AS changeReason2 \n");
	sql.append("              , prodecoReason2 AS prodecoReason2 \n");

	/*
	 * sql.append(
	 * "              , DECODE(SUBSTR(partInfo, INSTR(partInfo, '|', 1, 2) + 1, INSTR(partInfo, '|', 1, 3) - INSTR(partInfo, '|', 1, 2) - 1), '0', '' \n"
	 * ); sql.append(
	 * "                       , DECODE(SIGN(TO_NUMBER(SUBSTR(partInfo, INSTR(partInfo, '|', 1, 2) + 1, INSTR(partInfo, '|', 1, 3) - INSTR(partInfo, '|', 1, 2) - 1)) \n"
	 * ); sql.append(
	 * "                                - TO_NUMBER(SUBSTR(partInfo, INSTR(partInfo, '|', 1, 3) + 1))), 0, '확보', '미확보')) secureBudgetYn \n"
	 * );
	 */
	sql.append("              , secureBudgetYn AS secureBudgetYn \n");

	sql.append("              , creatorName creatorName \n");
	sql.append("              , createDate createDate \n");
	sql.append("              , stateStateName stateStateName \n");

	sql.append("              , projectOid projectOid \n");
	sql.append("              , projectNo projectNo \n");
	sql.append("              , other_reason other_reason \n");

	if (pager != null) {

	    String sortCol = pager.getSortCol();
	    String sortType = pager.getSortType();

	    if (sortCol != null && !"".equals(sortCol.trim())) {
		sql.append("      , row_number() over (").append(" ORDER BY ").append(sortCol).append(" ").append(sortType).append(") row_id \n");

	    }
	} else {
	    sql.append("          , row_number() over (order by createDate desc) row_id \n");

	}

	sql.append("          FROM ( \n");

	// SQL 1/2
	sql.append("                 SELECT * FROM ( ").append(this.getSearchMyEcmListDetailSQL(hash, conditionList)).append(" ) T1 \n");
	// SQL 2/2
	sql.append("                         WHERE 1 = 1 \n");

	for (Map<String, Object> condistion : conditionList) {
	    KETParamMapUtil map = KETParamMapUtil.getMap(condistion);

	    // 작성자OID
	    if (StringUtil.checkNull(map.getString("creatorOid")).length() > 0) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.idA3B7", KETParamMapUtil.OidToString(map.getString("creatorOid")), false)).append(" \n");
	    }
	    // 작성일자
	    if (!map.getString("createStartDate").equals("")) {
		String predate = map.getString("createStartDate");
		predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
		sql.append("    AND T1.createDate >= TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS') \n");
	    }
	    if (!map.getString("createEndDate").equals("")) {
		String postdate = map.getString("createEndDate");
		postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
		sql.append("    AND T1.createDate <= TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS') \n");
	    }
	    // 구분
	    if (StringUtil.checkNull(map.getString("prodMoldCls")).length() > 0) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.prodMoldCls", KETParamMapUtil.OidToString(map.getString("prodMoldCls")), false)).append(" \n");
	    }
	    // //ECO번호
	    if (StringUtil.checkNull(map.getString("ecoId")).length() > 0) {
		sql.append("   AND ( \n");
		sql.append("           ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.ecoId", ("ECO-" + (String) map.get("ecoId")), false)).append(" \n");
		sql.append("        OR ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.ecoId", ("ECR-" + (String) map.get("ecoId")), false)).append(" \n");
		sql.append("       ) \n");

	    }
	    // ECO제목
	    if (StringUtil.checkNull((String) map.get("ecoName")).length() > 0) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.ecoName", (String) map.get("ecoName"), false)).append(" \n");
	    }
	    // 프로젝트번호
	    if (StringUtil.checkNull((String) map.get("projectOid")).length() > 0) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.projectOid", (String) map.get("projectOid"), false)).append(" \n");
	    }
	    // 프로젝트명
	    if (StringUtil.checkNull((String) map.get("projectName")).length() > 0) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.projectName", (String) map.get("projectName"), false)).append(" \n");
	    }
	    // 부품번호
	    if (!map.getString("partNo").equals("")) {
		sql.append("     AND ( \n");

		sql.append("          T1.idvalue2 IN ( \n");
		sql.append("                         SELECT ST1.idA3B5 \n");
		sql.append("                           FROM KETProdECOPartLink ST1 \n");
		sql.append("                              , wtpart p \n");
		sql.append("                              , wtpartmaster m \n");
		sql.append("                          WHERE 1 = 1 \n");
		sql.append("                            AND st1.ida3a5 = p.ida2a2 \n");
		sql.append("                            AND p.ida3masterreference = m.ida2a2 \n");
		sql.append("                            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m.wtpartnumber", map.getString("partNo"), false)).append(" \n");
		sql.append("                         ) \n");

		sql.append("       OR T1.idvalue2 IN ( \n");
		sql.append("                         SELECT ST1.idA3B5 \n");
		sql.append("                           FROM KETMoldECOPartLink ST1 \n");
		sql.append("                              , wtpart p \n");
		sql.append("                              , wtpartmaster m \n");
		sql.append("                          WHERE 1 = 1 \n");
		sql.append("                            AND st1.ida3a5 = p.ida2a2 \n");
		sql.append("                            AND p.ida3masterreference = m.ida2a2 \n");
		sql.append("                            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m.wtpartnumber", map.getString("partNo"), false)).append(" \n");
		sql.append("                         ) \n");

		sql.append("       OR T1.idvalue2 IN ( \n");
		sql.append("                         SELECT ST1.idA3B5 \n");
		sql.append("                           FROM KETProdECRPartLink ST1 \n");
		sql.append("                              , wtpart p \n");
		sql.append("                              , wtpartmaster m \n");
		sql.append("                          WHERE 1 = 1 \n");
		sql.append("                            AND st1.ida3a5 = p.ida2a2 \n");
		sql.append("                            AND p.ida3masterreference = m.ida2a2 \n");
		sql.append("                            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m.wtpartnumber", map.getString("partNo"), false)).append(" \n");
		sql.append("                         ) \n");

		sql.append("       OR T1.idvalue2 IN ( \n");
		sql.append("                         SELECT ST1.idA3B5 \n");
		sql.append("                           FROM KETMoldECRPartLink ST1 \n");
		sql.append("                              , wtpart p \n");
		sql.append("                              , wtpartmaster m \n");
		sql.append("                          WHERE 1 = 1 \n");
		sql.append("                            AND st1.ida3a5 = p.ida2a2 \n");
		sql.append("                            AND p.ida3masterreference = m.ida2a2 \n");
		sql.append("                            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m.wtpartnumber", map.getString("partNo"), false)).append(" \n");
		sql.append("                         ) \n");

		sql.append("         ) \n");
	    }
	    // 부품명
	    if (!map.getString("partName").equals("")) {
		sql.append("     AND ( \n");

		sql.append("          T1.idvalue2 IN ( \n");
		sql.append("                         SELECT ST1.idA3B5 \n");
		sql.append("                           FROM KETProdECOPartLink ST1 \n");
		sql.append("                              , wtpart p \n");
		sql.append("                              , wtpartmaster m \n");
		sql.append("                          WHERE 1 = 1 \n");
		sql.append("                            AND st1.ida3a5 = p.ida2a2 \n");
		sql.append("                            AND p.ida3masterreference = m.ida2a2 \n");
		sql.append("                            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m.NAME", map.getString("partName"), false)).append(" \n");
		sql.append("                         ) \n");

		sql.append("       OR T1.idvalue2 IN ( \n");
		sql.append("                         SELECT ST1.idA3B5 \n");
		sql.append("                           FROM KETMoldECOPartLink ST1 \n");
		sql.append("                              , wtpart p \n");
		sql.append("                              , wtpartmaster m \n");
		sql.append("                          WHERE 1 = 1 \n");
		sql.append("                            AND st1.ida3a5 = p.ida2a2 \n");
		sql.append("                            AND p.ida3masterreference = m.ida2a2 \n");
		sql.append("                            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m.NAME", map.getString("partName"), false)).append(" \n");
		sql.append("                         ) \n");

		sql.append("       OR T1.idvalue2 IN ( \n");
		sql.append("                         SELECT ST1.idA3B5 \n");
		sql.append("                           FROM KETProdECRPartLink ST1 \n");
		sql.append("                              , wtpart p \n");
		sql.append("                              , wtpartmaster m \n");
		sql.append("                          WHERE 1 = 1 \n");
		sql.append("                            AND st1.ida3a5 = p.ida2a2 \n");
		sql.append("                            AND p.ida3masterreference = m.ida2a2 \n");
		sql.append("                            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m.NAME", map.getString("partName"), false)).append(" \n");
		sql.append("                         ) \n");

		sql.append("       OR T1.idvalue2 IN ( \n");
		sql.append("                         SELECT ST1.idA3B5 \n");
		sql.append("                           FROM KETMoldECRPartLink ST1 \n");
		sql.append("                              , wtpart p \n");
		sql.append("                              , wtpartmaster m \n");
		sql.append("                          WHERE 1 = 1 \n");
		sql.append("                            AND st1.ida3a5 = p.ida2a2 \n");
		sql.append("                            AND p.ida3masterreference = m.ida2a2 \n");
		sql.append("                            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m.NAME", map.getString("partName"), false)).append(" \n");
		sql.append("                         ) \n");

		sql.append("         ) \n");

	    }
	    // 개발양산구분
	    if (!map.getString("devYn").equals("")) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.devYn", map.getString("devYn"), false)).append(" \n");
	    }
	    // 진행상태
	    if (!map.getString("sancStateFlag").equals("")) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.stateState", map.getString("sancStateFlag"), false)).append(" \n");
	    }

	}

	// 로그인 사용자가 KETS 일 경우
	if (CommonUtil.isKETSUser()) {
	    sql.append(CommonUtil.ketsUserListWhereStr("T1.idA3B7"));
	}

	sql.append("               ) \n");

	sql.append("        ) t \n");

	// Paging
	if (pager != null) {

	    String sPage = String.valueOf(pager.getCurrentPageNo() + 1);
	    String sPageRowCnt = String.valueOf(pager.getPageSize());

	    sql.append(" WHERE row_id BETWEEN (").append(sPage).append("-1) * ").append(sPageRowCnt).append("+1 AND (").append(sPage).append(") * ").append(sPageRowCnt).append(" \n");

	}

	Kogger.debug(getClass(), sql.toString());
	return sql.toString();
    }// end-of-getSearchEcoListSQL

    /**
     * 
     * 함수명 : searchEcoDetail 설명 : 금형 ECO 상세 자료를 조회한다.
     * 
     * @param ketMoldChangeOrderVO
     *            : 금형 ECO VO
     * @return ketMoldChangeOrderVO : 금형 ECO VO
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    public KETMoldChangeOrderVO searchEcoDetail(KETMoldChangeOrderVO ketMoldChangeOrderVO, String locale) throws Exception {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	KETMoldChangeOrder ketMoldChangeOrder = ketMoldChangeOrderVO.getKetMoldChangeOrder();
	try {
	    con = PlmDBUtil.getConnection();
	    sql = getSearchMoldEcoDetailSQL(ketMoldChangeOrderVO);
	    pstmt = con.prepareStatement(sql);
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		ketMoldChangeOrder.setEcoId(StringUtil.checkNull(rs.getString("ecoId")));// 1
		ketMoldChangeOrder.setDevYn(StringUtil.checkNull(rs.getString("devYn")));// 2
		ketMoldChangeOrderVO.setDevYnName(StringUtil.checkNull(rs.getString("devYnName")));// 2
		ketMoldChangeOrder.setEcoName(StringUtil.checkNull(rs.getString("ecoName")));// 3
		ketMoldChangeOrder.setDivisionFlag(StringUtil.checkNull(rs.getString("divisionFlag")));// 4
		ketMoldChangeOrderVO.setDivisionFlagName(StringUtil.checkNull(rs.getString("divisionFlagName")));// 4

		String oid = StringUtil.checkNull(rs.getString("projectOid"));
		ketMoldChangeOrder.setProjectOid(oid);// 5.프로젝트oid
		// if(!"".equals(oid)) {
		// E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
		// if(project != null) {
		// E3PSProjectData projectData = new E3PSProjectData(project);
		// ketMoldChangeOrderVO.setProjectNo(projectData.ViewpjtNo);//5.프로젝트번호
		// ketMoldChangeOrderVO.setProjectName(projectData.pjtName);//5.프로젝트명
		// } else {
		// ketMoldChangeOrderVO.setProjectNo("");//5.프로젝트번호
		// ketMoldChangeOrderVO.setProjectName("");//5.프로젝트명
		// }
		// } else {
		// ketMoldChangeOrderVO.setProjectNo("");//5.프로젝트번호
		// ketMoldChangeOrderVO.setProjectName("");//5.프로젝트명
		// }
		ketMoldChangeOrderVO.setProjectNo(StringUtil.checkNull(rs.getString("projectNo")));// 5.프로젝트번호
		ketMoldChangeOrderVO.setProjectName(StringUtil.checkNull(rs.getString("projectName")));// 5.프로젝트명

		ketMoldChangeOrderVO.setOrgName(StringUtil.checkNull(rs.getString("orgName")));// 6.작성부서명
		ketMoldChangeOrderVO.setTeamName(StringUtil.checkNull(rs.getString("teamName")));// 6.작성팀명
		ketMoldChangeOrderVO.setCreatorName(StringUtil.checkNull(rs.getString("creatorName")));// 7.작성자명
		ketMoldChangeOrderVO.setCreateDate(StringUtil.checkNull(rs.getString("createDate")));// 8.작성일자
		ketMoldChangeOrderVO.setUpdateDate(StringUtil.checkNull(rs.getString("updateDate")));// 9.수정일자

		ketMoldChangeOrderVO.setApprovalName(StringUtil.checkNull(EcmUtil.getLastApproverName(ketMoldChangeOrder)));// 10.승인자명
		ketMoldChangeOrderVO.setApprovalDate(StringUtil.checkNull(EcmUtil.getLastApproveDate(ketMoldChangeOrder)));// 11.승인일자

		ketMoldChangeOrderVO.setProgressState(StringUtil.checkNull(rs.getString("stateState")));// 12.진행상태
		ketMoldChangeOrderVO.setEcoWorkerName(StringUtil.checkNull(rs.getString("ecoWorkerName")));// 13.ECO담당자명
		// String stateName = ECMProperties.getInstance().getString(ketMoldChangeOrderVO.getProgressState());
		// ketMoldChangeOrderVO.setProgressStateName(stateName);//12.진행상태명
		ketMoldChangeOrderVO.setProgressStateName(StringUtil.checkNull(rs.getString("stateStateName")));// 12.진행상태명
		ketMoldChangeOrderVO.setOid(rs.getString("oid"));
		ketMoldChangeOrderVO.setMoldOid(StringUtil.checkNull(rs.getString("mold_oid")));// 양산금형 link oid
		ketMoldChangeOrderVO.setTotalCount(1);

		String vendorFlag = StringUtil.checkNull(rs.getString("vendorFlag"));
		String prodVendor = StringUtil.checkNull(rs.getString("prodVendor"));
		if (!"".equals(prodVendor)) {
		    if ("i".equals(vendorFlag)) {
			ketMoldChangeOrderVO.setProdVendorName(StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("PRODUCTIONDEPT", prodVendor, locale)));// 생산처명
		    } else if ("o".equals(vendorFlag)) {
			EcmComDao ecmComDao = new EcmComDao(con);
			ketMoldChangeOrderVO.setProdVendorName(ecmComDao.getPartnerName(prodVendor));// 생산처명
		    }
		} else {
		    ketMoldChangeOrderVO.setProdVendorName("");// 생산처명
		}
		ketMoldChangeOrderVO.setChangeReasonName(getCodeNames("CHANGEREASON", StringUtil.checkNull(ketMoldChangeOrder.getChangeReason()), locale));
		ketMoldChangeOrderVO.setIncreaseProdTypeName(getCodeNames("INCREASEPRODTYPE", StringUtil.checkNull(ketMoldChangeOrder.getIncreaseProdType()), locale));

		// SAP I/F의 성공여부(재전송 버튼을 보이게 하느냐 마느냐)
		ProdEcoDao ecoDao = new ProdEcoDao(con);
		boolean isSucessSapInterface = ecoDao.isSucessSapInterface(StringUtil.checkNull(rs.getString("ecoId")));
		ketMoldChangeOrderVO.setSucessSapInterface(isSucessSapInterface);

	    }
	    ketMoldChangeOrderVO.setKetMoldChangeOrder(ketMoldChangeOrder);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
	return ketMoldChangeOrderVO;
    }

    /**
     * 
     * 함수명 : searchMoldEcaAll 설명 : 금형 ECA 상세 목록을 조회한다.
     * 
     * @param ketMoldChangeOrderVO
     *            : 금형 ECO VO
     * @return ketMoldChangeOrderVO : 금형 ECO VO
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    public KETMoldChangeOrderVO searchMoldEcaAll(KETMoldChangeOrderVO ketMoldChangeOrderVO, String cmd) throws Exception {
	Connection con = null;
	// PreparedStatement pstmt = null;
	LoggableStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	String bomChangeStr = "";
	ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = new ArrayList<KETMoldECALinkVO>();// ECA 검색상세 List
	KETMoldECALinkVO ketMoldECALinkVO = null;
	try {
	    con = PlmDBUtil.getConnection();
	    EcmComDao comDao = new EcmComDao(con);

	    // BOM이 있느냐 없느냐?
	    int bomCompCnt = comDao.getCntBomEcoComponent(ketMoldChangeOrderVO.getKetMoldChangeOrder().getEcoId());
	    //
	    sql = getSearchMoldEcaAllSQL(ketMoldChangeOrderVO, cmd, bomCompCnt);
	    // pstmt = con.prepareStatement(sql);
	    pstmt = new LoggableStatement(con, sql);

	    long oid = ketMoldChangeOrderVO.getKetMoldChangeOrder().getPersistInfo().getObjectIdentifier().getId();
	    pstmt.setLong(1, oid);// 금형ECO(KetMoldChangeOrder) oid
	    pstmt.setLong(2, oid);// 금형ECO(KetMoldChangeOrder) oid
	    pstmt.setLong(3, oid);// 금형ECO(KetMoldChangeOrder) oid
	    pstmt.setLong(4, oid);// 금형ECO(KetMoldChangeOrder) oid

	    boolean isActivityCompleted = true;
	    String stateStr = ketMoldChangeOrderVO.getKetMoldChangeOrder().getLifeCycleState().getDisplay();
	    if (stateStr.equals("계획수립") || stateStr.equals("활동수행") || stateStr.equals("재작업")) {
		isActivityCompleted = false;
	    }

	    // if (cmd.equalsIgnoreCase("view") || cmd.equalsIgnoreCase("popupview")) {
	    // if ((cmd.equalsIgnoreCase("view") || cmd.equalsIgnoreCase("popupview")) || (isActivityCompleted &&
	    // cmd.equalsIgnoreCase("updateview"))) {
	    if (isActivityCompleted && (cmd.equalsIgnoreCase("view") || cmd.equalsIgnoreCase("popupview")) && bomCompCnt > 0) {
		pstmt.setLong(5, oid);// 금형ECO(KetMoldChangeOrder) oid
		pstmt.setLong(6, oid);// 금형ECO(KetMoldChangeOrder) oid
	    }

	    Kogger.debug(getClass(), "Command : " + cmd);
	    Kogger.debug(getClass(), "##############################################");
	    Kogger.debug(getClass(), pstmt.getQueryString());
	    Kogger.debug(getClass(), "##############################################");

	    rs = pstmt.executeQuery();

	    while (rs.next()) {

		String bom_chg_flag = rs.getString("bom_chg_flag");
		if (bom_chg_flag != null && bom_chg_flag.equalsIgnoreCase("변경없음")) {
		    // Do nothing..!!

		} else {

		    ketMoldECALinkVO = new KETMoldECALinkVO();
		    ketMoldECALinkVO.setActivityType(StringUtil.checkNull(rs.getString("activityType")));// 활동계획구분(1:도면, 2:BOM, 3:문서)
		    ketMoldECALinkVO.setActivityTypeName(StringUtil.checkNull(rs.getString("activityTypeName")));// 활동계획구분명(1:도면, 2:BOM,
			                                                                                         // 3:문서)
		    ketMoldECALinkVO.setRelEcaOid(StringUtil.checkNull(rs.getString("relEcaOid")));// 활동계획oid
		    ketMoldECALinkVO.setActivityStatus(StringUtil.checkNull(rs.getString("activityStatus")));// 활동계획진행상태
		    ketMoldECALinkVO.setWorkerId(StringUtil.checkNull(rs.getString("workerId")));// 활동계획담당자oid
		    ketMoldECALinkVO.setWorkerName(StringUtil.checkNull(rs.getString("workerName")));// 활동계획담당자명
		    ketMoldECALinkVO.setCompleteDate(StringUtil.checkNull(rs.getString("completeDate")));// 활동계획완료일자
		    ketMoldECALinkVO.setRelEcaObjectLinkOid(StringUtil.checkNull(rs.getString("relEcaObjectLinkOid")));// 관련활동객체링크oid
		    ketMoldECALinkVO.setRelEcaObjectOid(StringUtil.checkNull(rs.getString("relEcaObjectOid")));// 관련활동객체oid
		    ketMoldECALinkVO.setRelEcaObjectNo(StringUtil.checkNull(rs.getString("relEcaObjectNo")));// 관련활동객체번호
		    ketMoldECALinkVO.setRelEcaObjectName(StringUtil.checkNull(rs.getString("relEcaObjectName")));// 관련활동객체명
		    ketMoldECALinkVO.setRelEcaObjectPreRev(StringUtil.checkNull(rs.getString("relEcaObjectPreRev")));// 관련활동객체이전버전
		    ketMoldECALinkVO.setRelEcaObjectAfterRev(StringUtil.checkNull(rs.getString("relEcaObjectAfterRev")));// 관련활동객체이후버전
		    ketMoldECALinkVO.setRelEcaObjectPlanDate(StringUtil.checkNull(rs.getString("relEcaObjectPlanDate")));// 관련활동객체변경예정일
		    ketMoldECALinkVO.setRelEcaObjectActivityComment(StringUtil.checkNull(rs.getString("relEcaObjectActivityComment")));// 관련활동객체변경내용
		    ketMoldECALinkVO.setChangeType(StringUtil.checkNull(rs.getString("changeType")));// 관련활동객체변경유형
		    ketMoldECALinkVO.setDieNo(StringUtil.checkNull(rs.getString("dieNo")));// 금형변경일정dieno
		    ketMoldECALinkVO.setScheduleId(StringUtil.checkNull(rs.getString("scheduleId")));// 금형변경일정id
		    ketMoldECALinkVO.setMoldChangePlanOid(StringUtil.checkNull(rs.getString("planOid")));// 금형변경일정oid
		    ketMoldECALinkVO.setRelEcaEpmChangeYn(StringUtil.checkNull(rs.getString("changeYn")));// 변경여부
		    ketMoldECALinkVO.setRelEcaEpmDocType(StringUtil.checkNull(rs.getString("epmDocType")));// 도면구분
		    ketMoldECALinkVO.setBeforePartOid(StringUtil.checkNull(rs.getString("beforePartOid")));// Before부품oid
		    ketMoldECALinkVO.setEcoHeaderOid(StringUtil.checkNull(rs.getString("ecoheaderoid"))); // BOM EcoHeader Oid
		    ketMoldECALinkVO.setBeforeQty(StringUtil.checkNull(rs.getString("beforeQty")));
		    ketMoldECALinkVO.setAfterQty(StringUtil.checkNull(rs.getString("afterQty")));
		    ketMoldECALinkVO.setBomChgFlag(StringUtil.checkNull(rs.getString("bom_chg_flag")));

		    ketMoldECALinkVO.setBeforeMaterial(StringUtil.checkNull(rs.getString("beforeMaterial")));
		    ketMoldECALinkVO.setAfterMaterial(StringUtil.checkNull(rs.getString("afterMaterial")));
		    ketMoldECALinkVO.setBeforeHardness(StringUtil.checkNull(rs.getString("beforeHardness")));
		    ketMoldECALinkVO.setAfterHardness(StringUtil.checkNull(rs.getString("afterHardness")));

		    // 비용확보
		    ketMoldECALinkVO.setRelated_die_no(StringUtil.checkNull(rs.getString("related_die_no")));
		    ketMoldECALinkVO.setExpect_cost(StringUtil.checkNull(rs.getString("expect_cost")));
		    ketMoldECALinkVO.setSecure_budget_yn(StringUtil.checkNull(rs.getString("secure_budget_yn")));

		    // ECN
		    ketMoldECALinkVO.setCustomCode(StringUtil.checkNull(rs.getString("customCode")));
		    ketMoldECALinkVO.setCustomName(StringUtil.checkNull(rs.getString("customName")));
		    ketMoldECALinkVO.setCompleteRequestDate(DateUtil.getTimeFormat(rs.getTimestamp("completeRequestDate"), "yyyy-MM-dd"));
		    ketMoldECALinkVO.setReceiveConfirmedDate(DateUtil.getTimeFormat(rs.getTimestamp("receiveConfirmedDate"), "yyyy-MM-dd"));
		    ketMoldECALinkVO.setActivityTypeDesc(StringUtil.checkNull(rs.getString("activityTypeDesc")));
		    ketMoldECALinkVO.setActivityBackDesc(StringUtil.checkNull(rs.getString("activityBackDesc")));

		    ketMoldECALinkVOList.add(ketMoldECALinkVO);

		}

	    }
	    ketMoldChangeOrderVO.setKetMoldECALinkVOList(ketMoldECALinkVOList);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
	return ketMoldChangeOrderVO;
    }

    /**
     * 
     * 함수명 : searchMoldEcaAllByWorkerId 설명 : 담당자별 금형 ECA 상세목록을 조회한다.
     * 
     * @param ketMoldChangeOrderVO
     *            : 금형 ECO VO
     * @return ketMoldChangeOrderVO : 금형 ECO VO
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    public KETMoldChangeOrderVO searchMoldEcaAllByWorkerId(KETMoldChangeOrderVO ketMoldChangeOrderVO, String cmd) throws Exception {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";
	String afterPartOid = "";

	ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = new ArrayList<KETMoldECALinkVO>();// ECA 검색상세 List
	KETMoldECALinkVO ketMoldECALinkVO = null;
	try {
	    con = PlmDBUtil.getConnection();
	    sql = getSearchMoldEcaAllByWorkerIdSQL(ketMoldChangeOrderVO, cmd);
	    pstmt = con.prepareStatement(sql);

	    long oid = ketMoldChangeOrderVO.getKetMoldChangeOrder().getPersistInfo().getObjectIdentifier().getId();
	    String loginUserOid = ketMoldChangeOrderVO.getLoginUserOid();

	    pstmt.setLong(1, oid);// 금형ECO(KetMoldChangeOrder) oid
	    pstmt.setString(2, loginUserOid);// 로그인담당자oid

	    rs = pstmt.executeQuery();
	    int ecaType = StringUtil.getIntParameter(ketMoldChangeOrderVO.getEcaType(), 0);
	    EcmSearchHelper helper = new EcmSearchHelper();

	    while (rs.next()) {
		ketMoldECALinkVO = new KETMoldECALinkVO();
		ketMoldECALinkVO.setActivityType(StringUtil.checkNull(rs.getString("activityType")));// 활동계획구분(1:도면, 2:BOM, 3:문서)
		ketMoldECALinkVO.setActivityTypeName(StringUtil.checkNull(rs.getString("activityTypeName")));// 활동계획구분명(1:도면, 2:BOM, 3:문서)
		ketMoldECALinkVO.setRelEcaOid(StringUtil.checkNull(rs.getString("relEcaOid")));// 활동계획oid
		ketMoldECALinkVO.setActivityStatus(StringUtil.checkNull(rs.getString("activityStatus")));// 활동계획진행상태
		ketMoldECALinkVO.setWorkerId(StringUtil.checkNull(rs.getString("workerId")));// 활동계획담당자oid
		ketMoldECALinkVO.setWorkerName(StringUtil.checkNull(rs.getString("workerName")));// 활동계획담당자명
		ketMoldECALinkVO.setCompleteDate(StringUtil.checkNull(rs.getString("completeDate")));// 활동계획완료일자
		ketMoldECALinkVO.setRelEcaObjectLinkOid(StringUtil.checkNull(rs.getString("relEcaObjectLinkOid")));// 관련활동객체링크oid
		ketMoldECALinkVO.setRelEcaObjectOid(StringUtil.checkNull(rs.getString("relEcaObjectOid")));// 관련활동객체oid
		ketMoldECALinkVO.setRelEcaObjectNo(StringUtil.checkNull(rs.getString("relEcaObjectNo")));// 관련활동객체번호
		ketMoldECALinkVO.setRelEcaObjectName(StringUtil.checkNull(rs.getString("relEcaObjectName")));// 관련활동객체명
		ketMoldECALinkVO.setRelEcaObjectPreRev(StringUtil.checkNull(rs.getString("relEcaObjectPreRev")));// 관련활동객체이전버전
		ketMoldECALinkVO.setRelEcaObjectAfterRev(StringUtil.checkNull(rs.getString("relEcaObjectAfterRev")));// 관련활동객체이후버전
		ketMoldECALinkVO.setRelEcaObjectPlanDate(StringUtil.checkNull(rs.getString("relEcaObjectPlanDate")));// 관련활동객체변경예정일
		ketMoldECALinkVO.setRelEcaObjectActivityComment(StringUtil.checkNull(rs.getString("relEcaObjectActivityComment")));// 관련활동객체변경내용
		ketMoldECALinkVO.setChangeType(StringUtil.checkNull(rs.getString("changeType")));// 관련활동객체변경유형
		ketMoldECALinkVO.setDieNo(StringUtil.checkNull(rs.getString("dieNo")));// 금형변경일정dieno
		ketMoldECALinkVO.setScheduleId(StringUtil.checkNull(rs.getString("scheduleId")));// 금형변경일정id
		ketMoldECALinkVO.setMoldChangePlanOid(StringUtil.checkNull(rs.getString("planOid")));// 금형변경일정oid
		ketMoldECALinkVO.setRelEcaEpmChangeYn(StringUtil.checkNull(rs.getString("changeYn")));// 변경여부
		ketMoldECALinkVO.setRelEcaEpmDocType(StringUtil.checkNull(rs.getString("epmDocType")));// 도면구분

		// 도면 유형
		String value = "";
		String relEcaObjectOid = StringUtil.checkNull(rs.getString("relEcaObjectOid"));
		Persistable persistable = CommonUtil.getObject(relEcaObjectOid);
		if (persistable instanceof EPMDocument) {
		    value = IBAUtil.getAttrValue((EPMDocument) persistable, EDMHelper.IBA_CAD_CATEGORY);
		}
		ketMoldECALinkVO.setCadCategory(value);

		ketMoldECALinkVO.setBeforePartOid(StringUtil.checkNull(rs.getString("beforePartOid")));// Before부품oid
		ketMoldECALinkVO.setEcoHeaderOid(StringUtil.checkNull(rs.getString("ecoHeaderOid")));
		ketMoldECALinkVO.setBeforeQty(StringUtil.checkNull(rs.getString("beforeQty")));
		ketMoldECALinkVO.setAfterQty(StringUtil.checkNull(rs.getString("afterQty")));
		ketMoldECALinkVO.setBomChgFlag(StringUtil.checkNull(rs.getString("bom_chg_flag")));

		afterPartOid = "";
		try {
		    if (ecaType == 1) {// 도면
			afterPartOid = WCUtil.getOIDString(helper.getLatestObject(ketMoldECALinkVO.getRelEcaObjectOid()));
		    } else if (ecaType == 2) {// BOM
			afterPartOid = WCUtil.getOIDString(helper.getLatestObject(ketMoldECALinkVO.getBeforePartOid()));
		    }
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}

		ketMoldECALinkVO.setAfterPartOid(afterPartOid);// After부품oid

		// 비용확보
		ketMoldECALinkVO.setRelated_die_no(StringUtil.checkNull(rs.getString("related_die_no")));
		ketMoldECALinkVO.setExpect_cost(StringUtil.checkNull(rs.getString("expect_cost")));
		ketMoldECALinkVO.setSecure_budget_yn(StringUtil.checkNull(rs.getString("secure_budget_yn")));

		// ECN
		ketMoldECALinkVO.setCustomCode(StringUtil.checkNull(rs.getString("customCode")));
		ketMoldECALinkVO.setCustomName(StringUtil.checkNull(rs.getString("customName")));
		ketMoldECALinkVO.setCompleteRequestDate(DateUtil.getTimeFormat(rs.getTimestamp("completeRequestDate"), "yyyy-MM-dd"));
		ketMoldECALinkVO.setReceiveConfirmedDate(DateUtil.getTimeFormat(rs.getTimestamp("receiveConfirmedDate"), "yyyy-MM-dd"));
		ketMoldECALinkVO.setActivityTypeDesc(StringUtil.checkNull(rs.getString("activityTypeDesc")));
		ketMoldECALinkVO.setActivityBackDesc(StringUtil.checkNull(rs.getString("activityBackDesc")));

		ketMoldECALinkVOList.add(ketMoldECALinkVO);
	    }

	    ketMoldChangeOrderVO.setKetMoldECALinkVOList(ketMoldECALinkVOList);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
	return ketMoldChangeOrderVO;
    }

    /**
     * 
     * 함수명 : searchMoldEcoRelEcrList 설명 : 금형ECO 연계 ECR 목록을 조회한다.
     * 
     * @param ketMoldChangeOrderVO
     *            : 금형 ECO VO
     * @return ketMoldChangeOrderVO : 금형 ECO VO
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    public KETMoldChangeOrderVO searchMoldEcoRelEcrList(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws Exception {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	ArrayList<KETMoldEcoEcrLinkVO> ketMoldEcoEcrLinkVOList = new ArrayList<KETMoldEcoEcrLinkVO>();// 연계ECR상세 List
	KETMoldEcoEcrLinkVO ketMoldEcoEcrLinkVO = null;
	KETMoldChangeRequest moldEcr = null;
	String approveDate = "";
	try {
	    con = PlmDBUtil.getConnection();
	    sql = getSearchMoldEcoRelEcrListSQL(ketMoldChangeOrderVO);
	    pstmt = con.prepareStatement(sql);
	    long oid = ketMoldChangeOrderVO.getKetMoldChangeOrder().getPersistInfo().getObjectIdentifier().getId();
	    pstmt.setLong(1, oid);// 금형ECO(KetMoldChangeOrder) oid
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		ketMoldEcoEcrLinkVO = new KETMoldEcoEcrLinkVO();
		ketMoldEcoEcrLinkVO.setRelEcrLinkOid(rs.getString("linkOid"));// 연계ECR링크oid
		ketMoldEcoEcrLinkVO.setRelEcrOid(rs.getString("oid"));// 연계ECRoid
		ketMoldEcoEcrLinkVO.setRelEcrId(rs.getString("ecrId"));// 연계ECRID
		ketMoldEcoEcrLinkVO.setRelEcrName(rs.getString("ecrName"));
		ketMoldEcoEcrLinkVO.setCreateDeptName(StringUtil.checkNull(rs.getString("orgName")));// 작성부서명
		ketMoldEcoEcrLinkVO.setCreatorName(StringUtil.checkNull(rs.getString("creatorName")));// 작성자명
		// ketMoldEcoEcrLinkVO.setApproveDate(StringUtil.checkNull(rs.getString("approveDate")));//승인일자
		moldEcr = (KETMoldChangeRequest) WCUtil.getObject(ketMoldEcoEcrLinkVO.getRelEcrOid());
		approveDate = StringUtil.checkNull(EcmUtil.getLastApproveDate(moldEcr));
		ketMoldEcoEcrLinkVO.setApproveDate(approveDate);// 승인일자
		ketMoldEcoEcrLinkVOList.add(ketMoldEcoEcrLinkVO);
	    }
	    ketMoldChangeOrderVO.setKetMoldEcoEcrLinkVOList(ketMoldEcoEcrLinkVOList);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
	return ketMoldChangeOrderVO;
    }

    /**
     * 
     * 함수명 : searchMoldEcoRelProdEcoList 설명 : 금형ECO 연계 제품 ECO 목록을 조회한다.
     * 
     * @param ketMoldChangeOrderVO
     *            : 금형 ECO VO
     * @return ketMoldChangeOrderVO : 금형 ECO VO
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    public KETMoldChangeOrderVO searchMoldEcoRelProdEcoList(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws Exception {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	ArrayList<KETMoldEcoEcrLinkVO> ketMoldEcoProdEcoLinkVOList = new ArrayList<KETMoldEcoEcrLinkVO>();// 연계ECR상세 List
	KETMoldEcoEcrLinkVO ketMoldEcoEcrLinkVO = null;
	KETProdChangeOrder prodEco = null;
	String approveDate = "";
	try {
	    con = PlmDBUtil.getConnection();
	    sql = getSearchMoldEcoRelProdEcoListSQL(ketMoldChangeOrderVO);
	    pstmt = con.prepareStatement(sql);
	    long oid = ketMoldChangeOrderVO.getKetMoldChangeOrder().getPersistInfo().getObjectIdentifier().getId();
	    pstmt.setLong(1, oid);// 금형ECO(KetMoldChangeOrder) oid
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		ketMoldEcoEcrLinkVO = new KETMoldEcoEcrLinkVO();
		ketMoldEcoEcrLinkVO.setRelEcrLinkOid(rs.getString("linkOid"));// 연계ECR링크oid
		ketMoldEcoEcrLinkVO.setRelEcrOid(rs.getString("oid"));// 연계ECOoid
		ketMoldEcoEcrLinkVO.setRelEcrId(rs.getString("ecoId"));// 연계ECOID
		ketMoldEcoEcrLinkVO.setRelEcrName(rs.getString("ecoName"));
		ketMoldEcoEcrLinkVO.setCreateDeptName(StringUtil.checkNull(rs.getString("orgName")));// 작성부서명
		ketMoldEcoEcrLinkVO.setCreatorName(StringUtil.checkNull(rs.getString("creatorName")));// 작성자명
		// ketMoldEcoEcrLinkVO.setApproveDate(StringUtil.checkNull(rs.getString("approveDate")));//승인일자
		prodEco = (KETProdChangeOrder) WCUtil.getObject(ketMoldEcoEcrLinkVO.getRelEcrOid());
		approveDate = StringUtil.checkNull(EcmUtil.getLastApproveDate(prodEco));
		ketMoldEcoEcrLinkVO.setApproveDate(approveDate);// 승인일자
		if (EcmSearchHelper.manager.isSecureBudget(ketMoldEcoEcrLinkVO.getRelEcrOid())) {
		    ketMoldEcoEcrLinkVO.setSecureBudgetYn("확보");
		} else {
		    ketMoldEcoEcrLinkVO.setSecureBudgetYn("미확보");
		}
		ketMoldEcoProdEcoLinkVOList.add(ketMoldEcoEcrLinkVO);
	    }
	    ketMoldChangeOrderVO.setKetMoldEcoProdEcoLinkVOList(ketMoldEcoProdEcoLinkVOList);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
	return ketMoldChangeOrderVO;
    }// end-of-searchMoldEcoRelProdEcoList

    /**
     * 
     * 함수명 : getMoldECADeleteList 설명 : 금형 ECA 삭제대상 목록을 조회한다.
     * 
     * @param ketMoldChangeOrderVO
     *            : 금형 ECO VO
     * @return ArrayList : 금형 ECA 삭제대상 목록
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ArrayList getMoldECADeleteList(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws Exception {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	long oid = ketMoldChangeOrderVO.getKetMoldChangeOrder().getPersistInfo().getObjectIdentifier().getId();
	ArrayList moldECADeleteList = new ArrayList();// ECA 삭제대상 List
	try {
	    con = PlmDBUtil.getConnection();
	    String ecaOid = getMoldECAByWorkerId(ketMoldChangeOrderVO, 1);
	    // ECA와 도면/BOM/문서 링크가 없는 자료를 조회한다.
	    sql = getMoldECADeleteListSQL();
	    pstmt = con.prepareStatement(sql);
	    pstmt.setLong(1, oid);// 금형ECO(KetMoldChangeOrder) oid
	    pstmt.setLong(2, oid);// 금형ECO(KetMoldChangeOrder) oid
	    pstmt.setLong(3, oid);// 금형ECO(KetMoldChangeOrder) oid
	    pstmt.setLong(4, oid);// 금형ECO(KetMoldChangeOrder) oid
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		if (!ecaOid.equals(rs.getString("moldEcaOid"))) {// ECO담당자는 삭제하지 않는다.
		    moldECADeleteList.add(rs.getString("moldEcaOid"));
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
	return moldECADeleteList;
    }

    /**
     * 
     * 함수명 : getMoldPlanDeleteList 설명 : 금형변경일정 삭제대상 목록을 조회한다.
     * 
     * @param ketMoldChangeOrderVO
     *            : 금형 ECO VO
     * @return ArrayList : 금형변경일정 삭제대상 목록
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ArrayList getMoldPlanDeleteList(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws Exception {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	long oid = ketMoldChangeOrderVO.getKetMoldChangeOrder().getPersistInfo().getObjectIdentifier().getId();
	ArrayList moldPlanDeleteList = new ArrayList();// ECA 삭제대상 List
	try {
	    con = PlmDBUtil.getConnection();
	    sql = getMoldPlanDeleteListSQL();
	    pstmt = con.prepareStatement(sql);
	    pstmt.setLong(1, oid);// 금형ECO(KetMoldChangeOrder) oid
	    pstmt.setLong(2, oid);// 금형ECO(KetMoldChangeOrder) oid
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		moldPlanDeleteList.add(rs.getString("moldPlanOid"));
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
	return moldPlanDeleteList;
    }

    /**
     * 
     * 함수명 : getMoldECAByWorkerId 설명 : ECO담당자oid로 금형 ECA를 조회한다.
     * 
     * @param ketMoldChangeOrderVO
     *            : 금형 ECO VO
     * @param oldNewCls
     *            :기존/신규구분(0:old 1:new)
     * @return String : 금형 ECA OID
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    public String getMoldECAByWorkerId(KETMoldChangeOrderVO ketMoldChangeOrderVO, int oldNewCls) throws Exception {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	long oid = ketMoldChangeOrderVO.getKetMoldChangeOrder().getPersistInfo().getObjectIdentifier().getId();
	String ecaOid = "";
	try {
	    con = PlmDBUtil.getConnection();
	    sql = getMoldECAByWorkerIdSQL();
	    pstmt = con.prepareStatement(sql);
	    if (oldNewCls == 0) {// old
		pstmt.setString(1, ketMoldChangeOrderVO.getOldEcoWorkerId());// ECO담당자oid
	    } else {// new
		pstmt.setString(1, ketMoldChangeOrderVO.getKetMoldChangeOrder().getEcoWorkerId());// ECO담당자oid
	    }
	    pstmt.setLong(2, oid);// 금형ECO(KetMoldChangeOrder) oid
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		ecaOid = rs.getString("ecaOid");
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
	return ecaOid;
    }

    /**
     * 
     * 함수명 : getSearchEcoListSQL 설명 : 제품/금형 ECO 목록을 조회하는 2차 SQL을 작성한다.
     * 
     * @param ketSearchEcoVO
     *            : 금형 ECO 검색 VO
     * @param ingPage
     *            : page 처리여부(0:미처리 1:page처리)
     * @param isExcel
     *            : 엑셀처리여부(0:미처리 1:엑셀처리)
     * @param prodMoldCls
     *            : 제품/금형구분(1:제품 2:금형)
     * @return String : SQL
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    private String getSearchEcoListSQL(boolean isExcel, KETParamMapUtil hash, List<Map<String, Object>> conditionList) throws Exception {

	return this.getSearchEcoListSQL(isExcel, hash, conditionList, null);
    }

    private String getSearchEcoListSQL(boolean isExcel, KETParamMapUtil hash, List<Map<String, Object>> conditionList, TgPagingControl pager) throws Exception {
	StringBuffer sql = new StringBuffer();

	// sql.append( "SELECT * \n" );
	// sql.append( "  FROM ( \n" );

	/*
	 * sql.append( "                SELECT ROWNUM row_id \n" ); sql.append( "                         , t.* \n" );
	 */

	sql.append("SELECT * \n");
	sql.append("  FROM ( \n");
	sql.append("                SELECT t.* \n");
	if (pager != null) {

	    String sortCol = pager.getSortCol();
	    String sortType = pager.getSortType();

	    if (sortCol != null && !"".equals(sortCol.trim())) {
		sql.append(" , row_number() over (").append(" ORDER BY ").append(sortCol).append(" ").append(sortType).append(") row_id \n");

	    }
	} else {
	    sql.append(" , row_number() over (order by createDate desc) row_id \n");

	}
	sql.append("                   FROM ( \n");

	if (isExcel) {
	    sql.append("                                SELECT ecoId ecoId \n");
	} else {
	    sql.append("                                SELECT substr(ecoId, 5) ecoId \n");
	}
	sql.append("                                         , ecoName ecoName \n");
	sql.append("                                         , divisionFlag divisionFlag \n");
	sql.append("                                         , divisionFlagName divisionFlagName \n");
	sql.append("                                         , '' partNumber \n");
	sql.append("                                         , '' partName \n");
	sql.append("                                         , changeReason changeReason \n");
	sql.append("                                         , DECODE(SUBSTR(partInfo, INSTR(partInfo, '|', 1, 2) + 1, INSTR(partInfo, '|', 1, 3) - INSTR(partInfo, '|', 1, 2) - 1), '0', '' \n");
	sql.append("                                                        , DECODE(SIGN(TO_NUMBER(SUBSTR(partInfo, INSTR(partInfo, '|', 1, 2) + 1, INSTR(partInfo, '|', 1, 3) - INSTR(partInfo, '|', 1, 2) - 1)) \n");
	sql.append("                                                       - TO_NUMBER(SUBSTR(partInfo, INSTR(partInfo, '|', 1, 3) + 1))), 0, '확보', '미확보')) secureBudgetYn \n");
	sql.append("                                         , creatorName creatorName \n");
	sql.append("                                         , createDate createDate \n");
	sql.append("                                         , stateState stateState \n");
	sql.append("                                         , stateStateName stateStateName \n");
	sql.append("                                         , partNumberList partNumberList \n");
	sql.append("                                         , oid oid \n");

	sql.append("                                         , classname2, idvalue2 \n");
	// 인덱스 생성 : KETWFMAPPROVALMASTER$IDA3B4
	// FUNCTION 생성 : FN_GET_PART_NO_NAME_STR
	sql.append("                                         , FN_GET_PART_NO_NAME_STR(classname2, idvalue2, 'NUMBER') AS partNumber2 \n");
	sql.append("                                         , FN_GET_PART_NO_NAME_STR(classname2, idvalue2, 'NAME') AS partName2 \n");
	// FUNCTION 생성 : FN_GET_RELATED_ECRECOLINK_STR
	sql.append("                                         , FN_GET_RELATED_ECRECOLINK_STR('ECO', classname2, idvalue2) AS relatedECOStr2 \n");
	sql.append("                                         , FN_GET_RELATED_ECRECOLINK_STR('ECR', classname2, idvalue2) AS relatedECRStr2 \n");

	sql.append("                                         , (SELECT nc.name FROM NUMBERCODE nc WHERE nc.codetype = 'PRODUCTIONDEPT' AND nc.code = prod_vendor) AS prodVendor2 \n");
	sql.append("                                         , (SELECT kp.partnerName FROM KETPartner kp WHERE kp.partnerNo = prod_vendor) AS partnerName2 \n");
	// FUNCTION 생성 : FN_GET_CODE_NAME
	sql.append("                                         , FN_GET_CODE_NAME_STR('CHANGEREASON', changeReason) AS changeReason2 \n");
	sql.append("                                         , FN_GET_CODE_NAME_STR('PRODECOREASON', changeReason) AS prodecoReason2 \n");
	sql.append("                                         , FN_GET_CODE_NAME_STR('INCREASEPRODTYPE', inc_prod_type) AS incProdType2 \n");
	sql.append("                                         , FN_GET_CODE_NAME_STR('CUSTOMCHECK', custom_flag) AS customChk2 \n");
	sql.append("                                         , FN_GET_CODE_NAME_STR('CHANGEFACT', control_flag) AS controlFlag2 \n");
	sql.append("                                         , TO_NUMBER(SUBSTR(partInfo, INSTR(partInfo, '|', 1, 2) + 1, INSTR(partInfo, '|', 1, 3) - INSTR(partInfo, '|', 1, 2) - 1)) partCount \n");
	sql.append("                                         , TO_NUMBER(SUBSTR(partInfo, INSTR(partInfo, '|', 1, 3) + 1)) secureBudgetYesCount \n");
	sql.append("                                         , orgName orgName \n");
	sql.append("                                         , prodMoldClsName prodMoldClsName \n");
	sql.append("                                         , projectOid projectOid \n");
	sql.append("                                         , projectNo projectNo \n");
	sql.append("                                         , projectName projectName \n");
	sql.append("                                         , devyn devyn \n");
	sql.append("                                         , domestic_flag domestic_flag \n");
	sql.append("                                         , car_maker car_maker \n");
	sql.append("                                         , car_category car_category \n");
	sql.append("                                         , inc_prod_type inc_prod_type \n");
	sql.append("                                         , other_inc_prod_type other_inc_prod_type \n");
	sql.append("                                         , vendor_flag vendor_flag \n");
	sql.append("                                         , prod_vendor prod_vendor \n");
	sql.append("                                         , custom_flag custom_flag \n");
	sql.append("                                         , custom_desc custom_desc \n");
	sql.append("                                         , control_flag control_flag \n");
	sql.append("                                         , cu_drawing cu_drawing \n");
	sql.append("                                         , other_reason other_reason \n");
	sql.append("                                         , costvariationrate costvariationrate \n");
	sql.append("                                         , costchange costchange \n");
	sql.append("                                         , approveDate2 \n");
	sql.append("                                         , defectDivName  \n");
	sql.append("                                         , defectTypeName  \n");
	sql.append("                                         , ecoapplypoint  \n");
	sql.append("                                FROM ( \n");

	// 제품+금형ECO
	if (hash.getString("prodMoldCls").equals("1") || hash.getString("prodMoldCls").equals("2")) {
	    sql.append(getSearchEcoListDetailSQL(isExcel, hash.getString("prodMoldCls"), hash, conditionList));
	} else {
	    sql.append(getSearchEcoListDetailSQL(isExcel, "1", hash, conditionList));
	    sql.append("UNION ALL \n");
	    sql.append(getSearchEcoListDetailSQL(isExcel, "2", hash, conditionList));
	}

	sql.append("                                        ) \n");

	// Order by
	/*
	 * if (pager != null) {
	 * 
	 * String sortCol = pager.getSortCol(); String sortType = pager.getSortType();
	 * 
	 * if (sortCol != null && !"".equals(sortCol.trim())) {
	 * sql.append(" ORDER BY ").append(sortCol).append(" ").append(sortType).append(" \n");
	 * 
	 * }
	 * 
	 * }
	 */

	sql.append("            ) t) WHERE 1=1 \n");

	// Paging
	if (pager != null) {

	    String sPage = String.valueOf(pager.getCurrentPageNo() + 1);
	    String sPageRowCnt = String.valueOf(pager.getPageSize());

	    sql.append(" AND row_id BETWEEN (").append(sPage).append("-1) * ").append(sPageRowCnt).append("+1 AND (").append(sPage).append(") * ").append(sPageRowCnt).append(" \n");

	}

	// sql.append( "       ) \n" );

	Kogger.debug(getClass(), sql.toString());
	return sql.toString();
    }// end-of-getSearchEcoListSQL

    /**
     * 
     * 함수명 : getSearchEcoListDetailSQL 설명 : 제품/금형 ECO 목록을 조회하는 1차 SQL을 작성한다.
     * 
     * @param ketSearchEcoVO
     *            : 금형 ECO 검색 VO
     * @param ingPage
     *            : page 처리여부(0:미처리 1:page처리)
     * @param isExcel
     *            : 엑셀처리여부(0:미처리 1:엑셀처리)
     * @param prodMoldCls
     *            : 제품/금형구분(1:제품 2:금형)
     * @return String : SQL
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    // SQL 생성
    private String getSearchEcoListDetailSQL(boolean isExcel, String prodMoldCls, KETParamMapUtil hash, List<Map<String, Object>> conditionList) throws Exception {
	StringBuffer sql = new StringBuffer();
	String active = (String) hash.get("Active");

	sql.append("     SELECT                                                                                 \n");
	sql.append("            T1.ecoId                                                             ecoId      \n");
	sql.append("          , T1.ecoName                                                           ecoName    \n");
	sql.append("    , T1.divisionFlag \n");
	sql.append("    , (SELECT ST1.name divisionFlagName FROM NumberCode ST1 WHERE ST1.codeType = 'DIVISIONFLAG' AND ST1.code = T1.divisionFlag) divisionFlagName \n");
	sql.append("          , ( SELECT NVL(MIN(ST3.WTPartNumber || '|' || ST3.name), '|') || '|' || COUNT(*) || '|' || COUNT(DECODE(secureBudgetYn, 'Y', 1))    \n");
	if (prodMoldCls.equals("1")) {
	    sql.append("                FROM KETProdECOPartLink ST1                                                                                                                                                         \n");
	} else {
	    sql.append("                FROM KETMoldECOPartLink ST1                                                                                                                                                         \n");
	}
	sql.append("                      , WTPart ST2                                                                                                                                                                             \n");
	sql.append("                      , WTPartMaster ST3                                                                                                                                                                     \n");
	sql.append("             WHERE 1 = 1                                                                                                                                                                                     \n");
	sql.append("                AND ST1.idA3A5 = ST2.idA2A2                                                                                                                                                            \n");
	sql.append("                AND ST1.idA3B5 = T1.idA2A2                                                                                                                                                            \n");
	sql.append("                AND ST2.idA3masterReference = ST3.idA2A2 )                                                                                                             partInfo                    \n");
	sql.append("           , ( SELECT ST2.name                                                                                                                                                                                 \n");
	sql.append("                FROM WTUser ST1                                                                                                                                                                             \n");
	sql.append("                        , people ST2                                                                                                                                                                             \n");
	sql.append("               WHERE ST1.idA2A2 = T1.idA3B7                                                                                                                                                             \n");
	sql.append("                   AND ST2.idA3B4 = ST1.idA2A2 )                                                                                                                             creatorName             \n");

	// sql.append("           , TO_CHAR(T1.createStampA2, '"+ dateFormatString + "') createDate \n");
	sql.append("           , T1.createStampA2 AS createDate \n");

	sql.append("           , T1.changeReason                                                                                                                                                     changeReason        \n");
	sql.append("           , T1.classnameA2A2||':'|| T1.idA2A2                                                                                                                                 oid                         \n");

	sql.append("           , T1.classnameA2A2 AS classname2, T1.idA2A2 AS idvalue2 \n");

	sql.append("           , T1.stateState                                                                                                                                                             stateState                \n");
	sql.append("           , T1.deptName                                                                                                                                                             orgName                 \n");
	sql.append("           , ''                                                                                                                                                                             partNumberList         \n");
	sql.append("           , T1.projectOid                                                                                                                                                             projectOid                \n");
	sql.append("           , ( SELECT max(pl.name)                                                                                                                                                                            \n");
	sql.append("                FROM phasetemplate ph                                                                                                                                                                     \n");
	sql.append("                        , PHASE pl                                                                                                                                                                                 \n");
	sql.append("               WHERE 1 = 1                                                                                                                                                                                     \n");
	// sql.append("                   AND pl.idA3A4 = T1.idA3A2State                                                                                                                                                     \n");
	sql.append("                   AND pl.idA3A4 =ph.idA2A2                                                                                                                                                             \n");
	sql.append("                   AND ph.phaseState = T1.stateState    )                                                                                                                     stateStateName         \n");
	sql.append("           , FN_GET_NUMBERCODEVALUE('DEVYN',T1.DEVYN,'ko')                                                                                                                            devyn                    \n");

	if (prodMoldCls.equals("1")) {// 제품ECO
	    sql.append("           , ( SELECT e.pjtno                                                                                                                                                                                    \n");
	    sql.append("                   FROM e3psprojectmaster e                                                                                                                                                                \n");
	    sql.append("                           , productProject p                                                                                                                                                                    \n");
	    sql.append("              WHERE p.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) )                                                                               \n");
	    sql.append("                 AND p.ida3b8 = e.ida2a2  )                                                                                                                                     projectNo                \n");
	    sql.append("           , ( SELECT e.pjtname                                                                                                                                                                                \n");
	    sql.append("                   FROM e3psprojectmaster e                                                                                                                                                                \n");
	    sql.append("                           , productProject p                                                                                                                                                                    \n");
	    sql.append("              WHERE p.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) )                                                                               \n");
	    sql.append("                 AND p.ida3b8 = e.ida2a2  )                                                                                                                                     projectName            \n");
	    sql.append("           , '제품'                                                                                                                                                                     prodMoldClsName     \n");
	    sql.append("            , DECODE(t1.domesticyn, '1000', '국내', '2000', '국외')                                                                                                         domestic_flag            \n");
	    sql.append("           , ( SELECT c.name                                                                                                                                                                                    \n");
	    sql.append("                   FROM numbercode c                                                                                                                                                                        \n");
	    sql.append("                 WHERE c.codetype='CUSTOMEREVENT'                                                                                                                                                    \n");
	    sql.append("                     AND c.description = '자동차'                                                                                                                                                            \n");
	    sql.append("                     AND c.code = t1.carmaker )                                                                                                                                 car_maker                \n");
	    sql.append("           , ( SELECT e.name                                                                                                                                                                                    \n");
	    sql.append("                   FROM oemprojecttype e                                                                                                                                                                    \n");
	    sql.append("                             ,  numbercode c                                                                                                                                                                        \n");
	    sql.append("                 WHERE e.oemtype = 'CARTYPE'                                                                                                                                                             \n");
	    sql.append("                     AND e.ida3a4 = c.ida2a2                                                                                                                                                                    \n");
	    sql.append("                     AND c.description = '자동차'                                                                                                                                                            \n");
	    sql.append("                     AND c.code = t1.carmaker                                                                                                                                                                \n");
	    sql.append("                     AND e.code = t1.carcategory )                                                                                                                             car_category            \n");
	    sql.append("           , ''                                                                                                                                                                            inc_prod_type            \n");
	    sql.append("           , ''                                                                                                                                                                            other_inc_prod_type    \n");
	    sql.append("           , ''                                                                                                                                                                            vendor_flag                \n");
	    sql.append("           , ''                                                                                                                                                                            prod_vendor            \n");
	    sql.append("           , t1.custormerflag                                                                                                                                                        custom_flag            \n");
	    sql.append("           , t1.othercustflagdesc                                                                                                                                                custom_desc            \n");
	    sql.append("           , t1.changeflag                                                                                                                                                            control_flag            \n");
	    sql.append("           , t1.cudrawingchangeyn                                                                                                                                                cu_drawing                \n");
	    sql.append("           , t1.otherchangereason                                                                                                                                                other_reason            \n");
	    sql.append("           , decode(t1.costchangecode, 'DECREASE', '-', 'INCREASE', '+') || t1.costvariationrate || decode(t1.costvariationrate,'','','%') as costvariationrate            \n");
	    sql.append("           , ( SELECT n.name                                                                                \n");
	    sql.append("                 FROM numbercode n                                                                          \n");
	    sql.append("                WHERE n.codetype = 'COSTCHANGE'                                                             \n");
	    sql.append("                  AND n.code = T1.COSTCHANGECODE ) as costchange                                                         \n");
	    sql.append("           , T2.completeddate as approveDate2  \n");
	    sql.append("           , T1.defectDivName as defectDivName  \n");
	    sql.append("           , T1.defectTypeName as defectTypeName  \n");
	    sql.append("           , T1.ecoApplyPoint as ecoApplyPoint  \n");
	    sql.append(" FROM KETProdChangeOrder T1, KETWFMAPPROVALMASTER T2                                                                                                                                                                            \n");
	} else {// 금형ECO
	    sql.append("           , ( SELECT e.pjtno                                                                                                                                                                                    \n");
	    sql.append("                   FROM e3psprojectmaster e                                                                                                                                                                \n");
	    sql.append("                           , moldproject m                                                                                                                                                                        \n");
	    sql.append("              WHERE m.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) )                                                                               \n");
	    sql.append("                 AND m.ida3b8 = e.ida2a2  )                                                                                                                                 projectNo                \n");
	    sql.append("           , ( SELECT e.pjtname                                                                                                                                                                                \n");
	    sql.append("                   FROM e3psprojectmaster e                                                                                                                                                                \n");
	    sql.append("                           , moldproject m                                                                                                                                                                        \n");
	    sql.append("              WHERE m.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) )                                                                               \n");
	    sql.append("                 AND m.ida3b8 = e.ida2a2  )                                                                                                                                 projectName            \n");
	    sql.append("           , '금형'                                                                                                                                                                     prodMoldClsName     \n");
	    sql.append("           , ''                                                                                                                                                                             domestic_flag            \n");
	    sql.append("           , ''                                                                                                                                                                             car_maker                \n");
	    sql.append("           , ''                                                                                                                                                                             car_category            \n");
	    sql.append("           , t1.increaseprodtype                                                                                                                                                    inc_prod_type            \n");
	    sql.append("           , t1.otherincreaseprodtype                                                                                                                                            other_inc_prod_type    \n");
	    sql.append("           , t1.vendorflag                                                                                                                                                            vendor_flag                \n");
	    sql.append("           , t1.prodvendor                                                                                                                                                            prod_vendor            \n");
	    sql.append("           , ''                                                                                                                                                                            custom_flag            \n");
	    sql.append("           , ''                                                                                                                                                                            custom_desc            \n");
	    sql.append("           , ''                                                                                                                                                                            control_flag            \n");
	    sql.append("           , ''                                                                                                                                                                            cu_drawing                \n");
	    sql.append("           , t1.otherreasondesc                                                                                                                                                    other_reason            \n");
	    sql.append("           , '' as costvariationrate                                                                                                                                             \n");
	    sql.append("           , '' as costchange                                                                                                                                                                \n");
	    sql.append("           , T2.completeddate as approveDate2 \n");
	    sql.append("           , '' as defectDivName  \n");
	    sql.append("           , '' as defectTypeName  \n");
	    sql.append("           , '' as ecoApplyPoint  \n");
	    sql.append(" FROM KETMoldChangeOrder T1, KETWFMAPPROVALMASTER T2                                                                                                                                                                           \n");
	}
	sql.append("WHERE T1.IDA2A2 = T2.IDA3B4(+)                                                                                                                                                                          \n");

	for (Map<String, Object> condistion : conditionList) {
	    KETParamMapUtil map = KETParamMapUtil.getMap(condistion);

	    if (!map.getString("partNo").equals("")) {
		if (prodMoldCls.equals("1")) {
		    sql.append("     AND ( \n");

		    sql.append("          T1.idA2A2 IN ( \n");
		    sql.append("                        SELECT ST1.idA3B5 \n");
		    sql.append("                          FROM KETProdECOPartLink ST1 \n");
		    sql.append("                             , wtpart    p \n");
		    sql.append("                             , wtpartmaster    m \n");
		    sql.append("                         WHERE 1 = 1 \n");
		    sql.append("                           AND st1.ida3a5 = p.ida2a2 \n");
		    sql.append("                           AND p.ida3masterreference = m.ida2a2 \n");
		    sql.append("                           AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m.wtpartnumber", map.getString("partNo"), false)).append(" \n");
		    sql.append("                       ) \n");
		    sql.append("          OR \n");
		    sql.append("          T1.ida2a2 IN ( \n");
		    sql.append("                        SELECT eca.ida3a8 \n");
		    sql.append("                          FROM KETProdChangeActivity eca \n");
		    sql.append("                             , KETProdECABomLink blink \n");
		    sql.append("                             , ( \n");
		    sql.append("                                SELECT a.ida2a2, a.ecoitemcode \n");
		    sql.append("                                  FROM KETBomEcoHeader a \n");
		    sql.append("                                 UNION ALL \n");
		    sql.append("                                SELECT b.ida2a2, b.ecoitemcode \n");
		    sql.append("                                  FROM KETBomHeader b \n");
		    sql.append("                               ) head \n");
		    sql.append("                         WHERE eca.ida2a2 = blink.ida3b5 \n");
		    sql.append("                           AND blink.ida3a5 = head.ida2a2 \n");
		    sql.append("                           AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("head.ecoitemcode", map.getString("partNo"), false)).append(" \n");
		    sql.append("                       ) \n");

		    sql.append("          ) \n");
		} else {
		    sql.append("     AND ( \n");

		    sql.append("          T1.idA2A2 IN ( \n");
		    sql.append("                        SELECT ST1.idA3B5 \n");
		    sql.append("                          FROM KETMoldECOPartLink ST1 \n");
		    sql.append("                             , wtpart    p \n");
		    sql.append("                             , wtpartmaster    m \n");
		    sql.append("                         WHERE 1 = 1 \n");
		    sql.append("                           AND st1.ida3a5 = p.ida2a2 \n");
		    sql.append("                           AND p.ida3masterreference = m.ida2a2 \n");
		    sql.append("                           AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m.wtpartnumber", map.getString("partNo"), false)).append(" \n");
		    sql.append("                       ) \n");
		    sql.append("          OR \n");
		    sql.append("          T1.ida2a2 IN ( \n");
		    sql.append("                        SELECT eca.ida3a8 \n");
		    sql.append("                          FROM KETMoldChangeActivity eca \n");
		    sql.append("                             , KETMoldECABomLink blink \n");
		    sql.append("                             , ( \n");
		    sql.append("                                SELECT a.ida2a2, a.ecoitemcode \n");
		    sql.append("                                  FROM KETBomEcoHeader a \n");
		    sql.append("                                 UNION ALL \n");
		    sql.append("                                SELECT b.ida2a2, b.ecoitemcode \n");
		    sql.append("                                  FROM KETBomHeader b \n");
		    sql.append("                               ) head \n");
		    sql.append("                         WHERE eca.ida2a2 = blink.ida3b5 \n");
		    sql.append("                           AND blink.ida3a5 = head.ida2a2 \n");
		    sql.append("                           AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("head.ecoitemcode", map.getString("partNo"), false)).append(" \n");
		    sql.append("                       ) \n");
		    sql.append("          ) \n");
		}
	    }

	    if (!map.getString("partName").equals("")) {
		if (prodMoldCls.equals("1")) {
		    sql.append("     AND ( \n");

		    sql.append("          T1.idA2A2 IN ( \n");
		    sql.append("                        SELECT ST1.idA3B5                                                                                                                                                         \n");
		    sql.append("                          FROM KETProdECOPartLink ST1                                                                                                                                     \n");
		    sql.append("                             , wtpart    p                                                                                                                                                           \n");
		    sql.append("                             , wtpartmaster  m                                                                                                                                                   \n");
		    sql.append("                         WHERE 1 = 1                                                                                                                                                               \n");
		    sql.append("                           AND st1.ida3a5 = p.ida2a2                                                                                                                                         \n");
		    sql.append("                           AND p.ida3masterreference = m.ida2a2                                                                                                                          \n");
		    sql.append("                           AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m.NAME", map.getString("partName"), false)).append("        \n");
		    sql.append("                       ) \n");
		    sql.append("          OR \n");
		    sql.append("          T1.ida2a2 IN ( \n");
		    sql.append("                        SELECT eca.ida3a8 \n");
		    sql.append("                          FROM KETProdChangeActivity eca \n");
		    sql.append("                             , KETProdECABomLink blink \n");
		    sql.append("                             , ( \n");
		    sql.append("                                SELECT a.ida2a2, a.description \n");
		    sql.append("                                  FROM KETBomEcoHeader a \n");
		    sql.append("                                 UNION ALL \n");
		    sql.append("                                SELECT b.ida2a2, b.description \n");
		    sql.append("                                  FROM KETBomHeader b \n");
		    sql.append("                               ) head \n");
		    sql.append("                         WHERE eca.ida2a2 = blink.ida3b5 \n");
		    sql.append("                           AND blink.ida3a5 = head.ida2a2 \n");
		    sql.append("                           AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("head.description", map.getString("partName"), false)).append(" \n");
		    sql.append("                       ) \n");

		    sql.append("          ) \n");
		} else {
		    sql.append("     AND ( \n");

		    sql.append("          T1.idA2A2 IN ( \n");
		    sql.append("                        SELECT ST1.idA3B5                                                                                                                                                         \n");
		    sql.append("                          FROM KETMoldECOPartLink ST1                                                                                                                                     \n");
		    sql.append("                             , wtpart    p                                                                                                                                                           \n");
		    sql.append("                             , wtpartmaster  m                                                                                                                                                   \n");
		    sql.append("                         WHERE 1 = 1                                                                                                                                                               \n");
		    sql.append("                           AND st1.ida3a5 = p.ida2a2                                                                                                                                         \n");
		    sql.append("                           AND p.ida3masterreference = m.ida2a2                                                                                                                          \n");
		    sql.append("                           AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m.NAME", map.getString("partName"), false)).append("        \n");
		    sql.append("                       ) \n");
		    sql.append("          OR \n");
		    sql.append("          T1.ida2a2 IN ( \n");
		    sql.append("                        SELECT eca.ida3a8 \n");
		    sql.append("                          FROM KETMoldChangeActivity eca \n");
		    sql.append("                             , KETMoldECABomLink blink \n");
		    sql.append("                             , ( \n");
		    sql.append("                                SELECT a.ida2a2, a.description \n");
		    sql.append("                                  FROM KETBomEcoHeader a \n");
		    sql.append("                                 UNION ALL \n");
		    sql.append("                                SELECT b.ida2a2, b.description \n");
		    sql.append("                                  FROM KETBomHeader b \n");
		    sql.append("                               ) head \n");
		    sql.append("                         WHERE eca.ida2a2 = blink.ida3b5 \n");
		    sql.append("                           AND blink.ida3a5 = head.ida2a2 \n");
		    sql.append("                           AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("head.description", map.getString("partName"), false)).append(" \n");
		    sql.append("                       ) \n");

		    sql.append("          ) \n");
		}
	    }
	    if (StringUtil.checkNull(active).equals("true")) {
		sql.append("AND T1.idA2A2 IN (");
		sql.append("                                    SELECT b.ida2a2 from KETProdChangeActivity A ,KETProdChangeOrder B, ketprodecaepmdoclink C ");
		sql.append("                                     WHERE a.ida3a8  = b.ida2a2     ");
		sql.append("                                     AND a.ida2a2 = c.ida3b5 )   ");
	    }
	    // 프로젝트번호
	    if (StringUtil.checkNull((String) map.get("projectOid")).length() > 0) {
		// sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.projectOid", (String) map.get("projectOid"),
		// false)).append("        \n");
		// 제품
		sql.append(" AND T1.projectOid IN ( \n");
		sql.append("               SELECT DISTINCT PPR.CLASSNAMEA2A2||':'||PPR.IDA2A2 \n");
		sql.append("                 FROM E3PSPROJECTMASTER EMA \n");
		sql.append("                    , PRODUCTPROJECT PPR \n");
		sql.append("                WHERE EMA.IDA2A2 = PPR.IDA3B8 \n");
		sql.append("                  AND EMA.PJTNO IN ( \n");
		sql.append("                                    SELECT DISTINCT EMA.PJTNO \n");
		sql.append("                                     FROM E3PSPROJECTMASTER EMA \n");
		sql.append("                                        , PRODUCTPROJECT PPR \n");
		sql.append("                                    WHERE EMA.IDA2A2 = PPR.IDA3B8 \n");
		sql.append("                                      AND PPR.CLASSNAMEA2A2||':'||PPR.IDA2A2 = '" + (String) map.get("projectOid") + "' \n");
		sql.append("                                   ) \n");

		sql.append("                UNION ALL \n");

		// 금형
		sql.append("               SELECT DISTINCT PPR.CLASSNAMEA2A2||':'||PPR.IDA2A2 \n");
		sql.append("                 FROM E3PSPROJECTMASTER EMA \n");
		sql.append("                    , MOLDPROJECT PPR \n");
		sql.append("                WHERE EMA.IDA2A2 = PPR.IDA3B8 \n");
		sql.append("                  AND EMA.PJTNO IN ( \n");
		sql.append("                                    SELECT DISTINCT EMA.PJTNO \n");
		sql.append("                                     FROM E3PSPROJECTMASTER EMA \n");
		sql.append("                                        , MOLDPROJECT PPR \n");
		sql.append("                                    WHERE EMA.IDA2A2 = PPR.IDA3B8 \n");
		sql.append("                                      AND PPR.CLASSNAMEA2A2||':'||PPR.IDA2A2 = '" + (String) map.get("projectOid") + "' \n");
		sql.append("                                   ) \n");
		sql.append("                      ) \n");

	    }
	    // 프로젝트명
	    if (StringUtil.checkNull((String) map.get("projectName")).length() > 0) {
		// sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.projectOid", (String) map.get("projectOid"),
		// false)).append("        \n");
		// 제품
		sql.append(" AND T1.projectOid IN ( \n");

		sql.append("               SELECT DISTINCT PPR.CLASSNAMEA2A2||':'||PPR.IDA2A2 \n");
		sql.append("                 FROM E3PSPROJECTMASTER EMA \n");
		sql.append("                    , PRODUCTPROJECT PPR \n");
		sql.append("                WHERE EMA.IDA2A2 = PPR.IDA3B8 \n");
		sql.append("                  AND UPPER(EMA.PJTNAME) LIKE UPPER('%" + (String) map.get("projectName") + "%') \n");

		sql.append("                UNION ALL \n");

		// 금형
		sql.append("               SELECT DISTINCT PPR.CLASSNAMEA2A2||':'||PPR.IDA2A2 \n");
		sql.append("                 FROM E3PSPROJECTMASTER EMA \n");
		sql.append("                    , MOLDPROJECT PPR \n");
		sql.append("                WHERE EMA.IDA2A2 = PPR.IDA3B8 \n");
		sql.append("                  AND UPPER(EMA.PJTNAME) LIKE UPPER('%" + (String) map.get("projectName") + "%') \n");

		sql.append("                      ) \n");

	    }
	    // 부서명
	    if (StringUtil.checkNull((String) map.get("orgName")).length() > 0) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.deptName", (String) map.get("orgName"), false)).append("        \n");
	    }
	    // 작성자OID
	    if (StringUtil.checkNull(map.getString("creatorOid")).length() > 0) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.idA3B7", KETParamMapUtil.OidToString(map.getString("creatorOid")), false)).append("        \n");
	    }

	    // 로그인 사용자가 KETS 일 경우
	    if (CommonUtil.isKETSUser()) {
		sql.append(CommonUtil.ketsUserListWhereStr("T1.idA3B7"));
	    }

	    // //ECO번호
	    if (StringUtil.checkNull(map.getString("ecoId")).length() > 0) {
		// sql.append("     AND T1.ecoId like  'ECO-'||'" + KETStringUtil.getLikeString( (String)map.get("ecoId") ) + "'    \n");
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.ecoId", ("ECO-" + (String) map.get("ecoId")), false)).append("        \n");
		// sql.append("     AND T1.ecoId like '" + KETStringUtil.getLikeString((String) map.get("ecoId")) + "'    \n");

	    }
	    // 사업자 구분
	    String division = (String) map.get("HdivisionFlag");
	    if (division != null && division.trim().length() > 0 && !division.equalsIgnoreCase("null")) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.divisionFlag", division, false)).append("        \n");
	    }
	    // 개발양산구분
	    String devYn = (String) hash.get("HdevYn");
	    if (devYn != null && devYn.trim().length() > 0 && !devYn.equalsIgnoreCase("null")) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.devYn", devYn, false)).append("        \n");
	    }
	    // 진행상태
	    String state = (String) hash.get("HsancStateFlag");
	    if (state != null && state.trim().length() > 0 && !state.equalsIgnoreCase("null")) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.stateState", state, false)).append("        \n");
	    }
	    // 작성일자
	    if (!map.getString("createStartDate").equals("")) {
		String predate = map.getString("createStartDate");
		predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
		sql.append("    AND T1.createStampA2 >= TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')                 \n");
	    }

	    // 승인일자
	    if (!map.getString("approveStartDate").equals("")) {
		String predate = map.getString("approveStartDate");
		predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
		sql.append("    AND completeddate >= TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')                 \n");
	    }

	    if (!map.getString("approveEndDate").equals("")) {
		String postdate = map.getString("approveEndDate");
		postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
		sql.append("    AND completeddate <= TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')                \n");
	    }

	    if (!map.getString("createEndDate").equals("")) {
		String postdate = map.getString("createEndDate");
		postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
		sql.append("    AND T1.createStampA2 <= TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')                \n");
	    }
	    // ECO제목
	    if (StringUtil.checkNull((String) map.get("ecoName")).length() > 0) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.ecoName", (String) map.get("ecoName"), false)).append("        \n");
	    }
	    // 2013.03.13 e3ps shkim add
	    // ECR 번호
	    if (StringUtil.checkNull((String) map.get("ecrNumber")).length() > 0) {
		if (prodMoldCls.equals("1")) { // 제품
		    sql.append("  AND T1.idA2A2 IN ( SELECT idA3A5 FROM KETProdChangeLink WHERE idA3B5 IN ( SELECT idA2A2 from KETProdChangeRequest WHERE ecrid = '"
			    + ((String) map.get("ecrNumber")).toUpperCase() + "' ) )  \n");
		} else { // 금형
		    sql.append("  AND T1.idA2A2 IN ( SELECT idA3A5 FROM KETMoldChangeLink WHERE idA3B5 IN ( SELECT idA2A2 from KETMoldChangeRequest WHERE ecrid = '"
			    + ((String) map.get("ecrNumber")).toUpperCase() + "' ) )  \n");
		}
	    }

	    if (prodMoldCls.equals("1")) {
		if (!map.getString("design_guide_yn").equals("")) {
		    if ("YN".equals(map.getString("design_guide_yn").toUpperCase())) {
			sql.append("  AND T1.designguideyn is not null \n");
		    } else {
			sql.append("  AND T1.designguideyn  = '" + ((String) map.get("design_guide")).toUpperCase() + "'  \n");
		    }
		}

		if (!map.getString("design_sheet_yn").equals("")) {
		    if ("YN".equals(map.getString("designchecksheetyn").toUpperCase())) {
			sql.append("  AND T1.designchecksheetyn is not null \n");
		    } else {
			sql.append("  AND T1.designchecksheetyn  = '" + ((String) map.get("design_sheet_yn")).toUpperCase() + "'  \n");
		    }
		}

	    }
	}

	Kogger.debug(getClass(), sql.toString());
	return sql.toString();
    }// end-of-getSearchEcoListDetailSQL

    private String getSearchMyEcmListDetailSQL(KETParamMapUtil hash, List<Map<String, Object>> conditionList) throws Exception {
	StringBuffer sql = new StringBuffer();

	// 제품 ECO
	sql.append("     SELECT \n");
	sql.append("             T1.classnameA2A2||':'|| T1.idA2A2 AS oid \n");
	sql.append("           , T1.classnameA2A2 AS classname2 \n");
	sql.append("           , T1.idA2A2 AS idvalue2 \n");

	sql.append("           , T1.ecoId AS ecoId \n");
	sql.append("           , T1.ecoName AS ecoName \n");

	sql.append("           , ( SELECT ST2.name \n");
	sql.append("                FROM WTUser ST1 \n");
	sql.append("                   , people ST2 \n");
	sql.append("               WHERE ST1.idA2A2 = T1.idA3B7 \n");
	sql.append("                 AND ST2.idA3B4 = ST1.idA2A2 ) AS creatorName \n");
	sql.append("           , T1.createStampA2 AS createDate \n");

	// FUNCTION 생성 : FN_GET_CODE_NAME
	sql.append("           , '' AS changeReason2 \n");
	sql.append("           , FN_GET_CODE_NAME_STR('PRODECOREASON', T1.changeReason) AS prodecoReason2 \n");

	sql.append("           , T1.stateState AS stateState \n");
	sql.append("           , ( SELECT max(pl.name) \n");
	sql.append("                 FROM phasetemplate ph \n");
	sql.append("                        , PHASE pl \n");
	sql.append("                WHERE 1 = 1 \n");
	sql.append("                  AND pl.idA3A4 =ph.idA2A2                                                                                                                                                             \n");
	sql.append("                  AND ph.phaseState = T1.stateState ) AS stateStateName \n");
	sql.append("           , T1.devyn AS devyn \n");

	sql.append("           , '제품 ECO' AS prodMoldClsName \n");

	/*
	 * sql.append(
	 * "           , ( SELECT NVL(MIN(ST3.WTPartNumber || '|' || ST3.name), '|') || '|' || COUNT(*) || '|' || COUNT(DECODE(secureBudgetYn, 'Y', 1)) \n"
	 * ); sql.append("                 FROM KETProdECOPartLink ST1 \n"); // 제품 sql.append("                    , WTPart ST2 \n");
	 * sql.append("                    , WTPartMaster ST3 \n"); sql.append("                WHERE 1 = 1 \n");
	 * sql.append("                  AND ST1.idA3A5 = ST2.idA2A2 \n"); sql.append("                  AND ST1.idA3B5 = T1.idA2A2 \n");
	 * sql.append("                  AND ST2.idA3masterReference = ST3.idA2A2 ) AS partInfo \n");
	 */
	sql.append("           , ( SELECT DECODE(MIN(ST1.secureBudgetYn), 'Y', '확보', '미확보') \n");
	sql.append("                 FROM KETProdECOPartLink ST1 \n"); // 제품
	sql.append("                WHERE ST1.idA3B5 = T1.idA2A2 ) AS secureBudgetYn \n");

	sql.append("           , T1.projectOid AS projectOid \n");
	sql.append("           , ( SELECT e.pjtno \n");
	sql.append("                 FROM e3psprojectmaster e \n");
	sql.append("                    , productProject p \n");
	sql.append("                WHERE p.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) ) \n");
	sql.append("                  AND p.ida3b8 = e.ida2a2  ) AS projectNo \n");
	sql.append("           , ( SELECT e.pjtname \n");
	sql.append("                 FROM e3psprojectmaster e \n");
	sql.append("                    , productProject p \n");
	sql.append("                WHERE p.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) ) \n");
	sql.append("                  AND p.ida3b8 = e.ida2a2  ) AS projectName \n");

	sql.append("           , t1.otherchangereason AS other_reason \n");

	// 조건을 위한 정보
	sql.append("           , t1.idA3B7 \n"); // 작성자
	sql.append("           , '1' AS prodMoldCls \n"); // 구분

	sql.append(" FROM KETProdChangeOrder T1 \n");

	// 금형 ECO
	sql.append("UNION ALL \n");
	sql.append("     SELECT \n");
	sql.append("             T1.classnameA2A2||':'|| T1.idA2A2 AS oid \n");
	sql.append("           , T1.classnameA2A2 AS classname2 \n");
	sql.append("           , T1.idA2A2 AS idvalue2 \n");

	sql.append("           , T1.ecoId AS ecoId \n");
	sql.append("           , T1.ecoName AS ecoName \n");

	sql.append("           , ( SELECT ST2.name \n");
	sql.append("                FROM WTUser ST1 \n");
	sql.append("                   , people ST2 \n");
	sql.append("               WHERE ST1.idA2A2 = T1.idA3B7 \n");
	sql.append("                 AND ST2.idA3B4 = ST1.idA2A2 ) AS creatorName \n");
	sql.append("           , T1.createStampA2 AS createDate \n");

	// FUNCTION 생성 : FN_GET_CODE_NAME
	sql.append("           , FN_GET_CODE_NAME_STR('CHANGEREASON', T1.changeReason) AS changeReason2 \n");
	sql.append("           , '' AS prodecoReason2 \n");

	sql.append("           , T1.stateState AS stateState \n");
	sql.append("           , ( SELECT max(pl.name) \n");
	sql.append("                 FROM phasetemplate ph \n");
	sql.append("                        , PHASE pl \n");
	sql.append("                WHERE 1 = 1 \n");
	sql.append("                  AND pl.idA3A4 =ph.idA2A2                                                                                                                                                             \n");
	sql.append("                  AND ph.phaseState = T1.stateState ) AS stateStateName \n");
	sql.append("           , T1.devyn AS devyn \n");

	sql.append("           , '금형 ECO' AS prodMoldClsName \n");

	/*
	 * sql.append(
	 * "           , ( SELECT NVL(MIN(ST3.WTPartNumber || '|' || ST3.name), '|') || '|' || COUNT(*) || '|' || COUNT(DECODE(secureBudgetYn, 'Y', 1)) \n"
	 * ); sql.append("                 FROM KETMoldECOPartLink ST1 \n"); // 금형 sql.append(
	 * "                    , WTPart ST2                                                                                                                                                                             \n"
	 * ); sql.append("                    , WTPartMaster ST3 \n"); sql.append("                WHERE 1 = 1 \n");
	 * sql.append("                  AND ST1.idA3A5 = ST2.idA2A2 \n"); sql.append("                  AND ST1.idA3B5 = T1.idA2A2 \n");
	 * sql.append("                  AND ST2.idA3masterReference = ST3.idA2A2 ) AS partInfo \n");
	 */
	sql.append("           , ( SELECT DECODE(MIN(ST1.secureBudgetYn), 'Y', '확보', '미확보') \n");
	sql.append("                 FROM KETProdECOPartLink ST1 \n"); // 제품
	sql.append("                WHERE ST1.idA3B5 = T1.idA2A2 ) AS secureBudgetYn \n");

	sql.append("           , T1.projectOid AS projectOid \n");
	sql.append("           , ( SELECT e.pjtno \n");
	sql.append("                   FROM e3psprojectmaster e \n");
	sql.append("                           , moldproject m \n");
	sql.append("              WHERE m.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) ) \n");
	sql.append("                 AND m.ida3b8 = e.ida2a2  ) AS projectNo \n");
	sql.append("           , ( SELECT e.pjtname \n");
	sql.append("                   FROM e3psprojectmaster e \n");
	sql.append("                           , moldproject m \n");
	sql.append("              WHERE m.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) ) \n");
	sql.append("                 AND m.ida3b8 = e.ida2a2  ) AS projectName \n");

	sql.append("           , t1.otherreasondesc AS other_reason \n");

	// 조건을 위한 정보
	sql.append("           , t1.idA3B7 \n"); // 작성자
	sql.append("           , '2' AS prodMoldCls \n"); // 구분

	sql.append(" FROM KETMoldChangeOrder T1 \n");

	// 제품 ECR
	sql.append("UNION ALL \n");
	sql.append("     SELECT \n");
	sql.append("             T1.classnameA2A2||':'|| T1.idA2A2 AS oid \n");
	sql.append("           , T1.classnameA2A2 AS classname2 \n");
	sql.append("           , T1.idA2A2 AS idvalue2 \n");

	sql.append("           , T1.ecrId AS ecoId \n");
	sql.append("           , T1.ecrName AS ecoName \n");

	sql.append("           , ( SELECT ST2.name \n");
	sql.append("                FROM WTUser ST1 \n");
	sql.append("                   , people ST2 \n");
	sql.append("               WHERE ST1.idA2A2 = T1.idA3B7 \n");
	sql.append("                 AND ST2.idA3B4 = ST1.idA2A2 ) AS creatorName \n");
	sql.append("           , T1.createStampA2 AS createDate \n");

	// FUNCTION 생성 : FN_GET_CODE_NAME
	sql.append("           , '' AS changeReason2 \n");
	sql.append("           , FN_GET_CODE_NAME_STR('PRODECOREASON', T1.changeReason) AS prodecoReason2 \n");

	sql.append("           , T1.stateState AS stateState \n");
	sql.append("           , ( SELECT max(pl.name) \n");
	sql.append("                 FROM phasetemplate ph \n");
	sql.append("                        , PHASE pl \n");
	sql.append("                WHERE 1 = 1 \n");
	sql.append("                  AND pl.idA3A4 =ph.idA2A2                                                                                                                                                             \n");
	sql.append("                  AND ph.phaseState = T1.stateState ) AS stateStateName \n");
	sql.append("           , T1.devyn AS devyn \n");

	sql.append("           , '제품 ECR' AS prodMoldClsName \n");

	sql.append("           , '' AS secureBudgetYn \n");

	sql.append("           , T1.projectOid AS projectOid \n");
	sql.append("           , ( SELECT e.pjtno \n");
	sql.append("                   FROM e3psprojectmaster e \n");
	sql.append("                           , productProject m \n");
	sql.append("              WHERE m.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) ) \n");
	sql.append("                 AND m.ida3b8 = e.ida2a2  ) AS projectNo \n");
	sql.append("           , ( SELECT e.pjtname \n");
	sql.append("                   FROM e3psprojectmaster e \n");
	sql.append("                           , productProject m \n");
	sql.append("              WHERE m.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) ) \n");
	sql.append("                 AND m.ida3b8 = e.ida2a2  ) AS projectName \n");

	sql.append("           , t1.otherChangeReasonDesc AS other_reason \n");

	// 조건을 위한 정보
	sql.append("           , t1.idA3B7 \n"); // 작성자
	sql.append("           , '3' AS prodMoldCls \n"); // 구분

	sql.append(" FROM KETProdChangeRequest T1 \n");

	// 금형 ECR
	sql.append("UNION ALL \n");
	sql.append("     SELECT \n");
	sql.append("             T1.classnameA2A2||':'|| T1.idA2A2 AS oid \n");
	sql.append("           , T1.classnameA2A2 AS classname2 \n");
	sql.append("           , T1.idA2A2 AS idvalue2 \n");

	sql.append("           , T1.ecrId AS ecoId \n");
	sql.append("           , T1.ecrName AS ecoName \n");

	sql.append("           , ( SELECT ST2.name \n");
	sql.append("                FROM WTUser ST1 \n");
	sql.append("                   , people ST2 \n");
	sql.append("               WHERE ST1.idA2A2 = T1.idA3B7 \n");
	sql.append("                 AND ST2.idA3B4 = ST1.idA2A2 ) AS creatorName \n");
	sql.append("           , T1.createStampA2 AS createDate \n");

	// FUNCTION 생성 : FN_GET_CODE_NAME
	sql.append("           , FN_GET_CODE_NAME_STR('CHANGETYPE', T1.changeType) AS changeReason2 \n");
	sql.append("           , '' AS prodecoReason2 \n");

	sql.append("           , T1.stateState AS stateState \n");
	sql.append("           , ( SELECT max(pl.name) \n");
	sql.append("                 FROM phasetemplate ph \n");
	sql.append("                        , PHASE pl \n");
	sql.append("                WHERE 1 = 1 \n");
	sql.append("                  AND pl.idA3A4 =ph.idA2A2                                                                                                                                                             \n");
	sql.append("                  AND ph.phaseState = T1.stateState ) AS stateStateName \n");
	sql.append("           , T1.devyn AS devyn \n");

	sql.append("           , '금형 ECR' AS prodMoldClsName \n");

	sql.append("           , '' AS secureBudgetYn \n");

	sql.append("           , T1.projectOid AS projectOid \n");
	sql.append("           , ( SELECT e.pjtno \n");
	sql.append("                   FROM e3psprojectmaster e \n");
	sql.append("                           , moldProject m \n");
	sql.append("              WHERE m.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) ) \n");
	sql.append("                 AND m.ida3b8 = e.ida2a2  ) AS projectNo \n");
	sql.append("           , ( SELECT e.pjtname \n");
	sql.append("                   FROM e3psprojectmaster e \n");
	sql.append("                           , moldProject m \n");
	sql.append("              WHERE m.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) ) \n");
	sql.append("                 AND m.ida3b8 = e.ida2a2  ) AS projectName \n");

	sql.append("           , t1.otherChangeDesc AS other_reason \n");

	// 조건을 위한 정보
	sql.append("           , t1.idA3B7 \n"); // 작성자
	sql.append("           , '4' AS prodMoldCls \n"); // 구분

	sql.append(" FROM KETMoldChangeRequest T1 \n");

	// Kogger.debug(getClass(), sql.toString());
	return sql.toString();
    }// end-of-getSearchEcoListDetailSQL

    /**
     * 
     * 함수명 : getSearchMoldEcoDetailSQL 설명 : 금형ECO 상세조회 SQL을 작성한다.
     * 
     * @param ketMoldChangeOrderVO
     *            : 금형 ECO VO
     * @return String : SQL
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    private String getSearchMoldEcoDetailSQL(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT \n");
	sql.append("    T1.ecoId \n");
	sql.append("    , T1.devYn \n");
	sql.append("    , (SELECT ST1.name devYnName FROM NumberCode ST1 WHERE ST1.codeType = 'DEVYN' AND ST1.code = T1.devYn) devYnName \n");
	sql.append("    , T1.ecoName \n");
	sql.append("    , T1.divisionFlag \n");
	sql.append("    , (SELECT ST1.name divisionFlagName FROM NumberCode ST1 WHERE ST1.codeType = 'DIVISIONFLAG' AND ST1.code = T1.divisionFlag) divisionFlagName \n");
	sql.append("    , T1.projectOid \n");
	sql.append("           , ( SELECT e.pjtno                                                                                                                                                                                    \n");
	sql.append("                   FROM e3psprojectmaster e                                                                                                                                                                \n");
	sql.append("                           , moldproject m                                                                                                                                                                        \n");
	sql.append("              WHERE m.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) )                                                                               \n");
	sql.append("                 AND m.ida3b8 = e.ida2a2  )                                                                                                                                 projectNo                \n");
	sql.append("           , ( SELECT e.pjtname                                                                                                                                                                                \n");
	sql.append("                   FROM e3psprojectmaster e                                                                                                                                                                \n");
	sql.append("                           , moldproject m                                                                                                                                                                        \n");
	sql.append("              WHERE m.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) )                                                                               \n");
	sql.append("                 AND m.ida3b8 = e.ida2a2  )                                                                                                                                 projectName            \n");
	sql.append("    , T1.deptName orgName \n");
	sql.append("    , (SELECT name \n");
	sql.append("    FROM TeamTemplate ST1 \n");
	sql.append("    WHERE ST1.idA2A2 = T1.idA3teamTemplateId) teamName \n");
	sql.append("    , (SELECT ST2.name \n");
	sql.append("    FROM WTUser ST1 \n");
	sql.append("        , people ST2 \n");
	sql.append("    WHERE ST1.idA2A2 = T1.idA3B7 \n");
	sql.append("    AND ST2.idA3B4 = ST1.idA2A2) creatorName \n");
	sql.append("    , TO_CHAR(T1.createStampA2, '" + dateFormatString + "') createDate \n");
	sql.append("    , TO_CHAR(T1.updateStampA2, '" + dateFormatString + "') updateDate \n");
	sql.append("    , T1.stateState \n");
	sql.append("           , ( SELECT max(pl.name)                                                                                                                                                                            \n");
	sql.append("                FROM phasetemplate ph                                                                                                                                                                     \n");
	sql.append("                        , PHASE pl                                                                                                                                                                                 \n");
	sql.append("               WHERE 1 = 1                                                                                                                                                                                     \n");
	// sql.append("                   AND pl.idA3A4 = T1.idA3A2State                                                                                                                                                     \n");
	sql.append("                   AND pl.idA3A4 =ph.idA2A2                                                                                                                                                             \n");
	sql.append("                   AND ph.phaseState = T1.stateState    )                                                                                                                     stateStateName         \n");
	sql.append("    , (SELECT ST2.name \n");
	sql.append("    FROM WTUser ST1 \n");
	sql.append("        , people ST2 \n");
	sql.append("    WHERE ST1.idA2A2 = TO_NUMBER(SUBSTR(T1.ecoWorkerId, INSTR(T1.ecoWorkerId, ':', 1, 1) + 1)) \n");
	sql.append("    AND ST2.idA3B4 = ST1.idA2A2) ecoWorkerName \n");
	sql.append("    , T1.prodVendor \n");
	sql.append("    , T1.vendorFlag \n");
	sql.append("    , T1.increaseProdType \n");
	sql.append("    , T1.classnameA2A2||':'|| T1.idA2A2 oid \n");
	sql.append("    , (select classnamea2a2||':'||ida2a2 from KETMoldChangePlan where ida3a4 = T1.ida2a2) mold_oid \n");
	sql.append("FROM KETMoldChangeOrder T1 \n");
	sql.append("WHERE 1 = 1 \n");
	sql.append("AND T1.idA2A2 = " + ketMoldChangeOrderVO.getOid() + " \n");

	return sql.toString();
    }// end-of-getSearchMoldEcoDetailSQL

    /**
     * 
     * 함수명 : getSearchMoldEcoRelEcrListSQL 설명 : 금형ECO 연계 ECR 목록조회 SQL을 작성한다.
     * 
     * @param ketMoldChangeOrderVO
     *            : 금형 ECO VO
     * @return String : SQL
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    private String getSearchMoldEcoRelEcrListSQL(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT T1.classnameA2A2||':'|| T1.idA2A2 linkOid \n");
	sql.append("    , T2.classnameA2A2||':'|| T2.idA2A2 oid \n");
	sql.append("    , T2.ecrId \n");
	sql.append("    , T2.ecrName \n");
	sql.append("    , T2.deptName orgName \n");
	sql.append("    , (SELECT ST2.name \n");
	sql.append("    FROM WTUser ST1 \n");
	sql.append("        , people ST2 \n");
	sql.append("    WHERE ST1.idA2A2 = T2.idA3B7 \n");
	sql.append("    AND ST2.idA3B4 = ST1.idA2A2) creatorName \n");
	sql.append("    , TO_CHAR(T1.createStampA2, '" + dateFormatString + "') approveDate \n");
	sql.append("FROM KETMoldChangeLink T1 \n");
	sql.append("    , KETMoldChangeRequest T2 \n");
	sql.append("WHERE 1 = 1 \n");
	sql.append("AND T1.idA3A5 = ? \n");
	sql.append("AND T2.idA2A2 = T1.idA3B5 \n");
	sql.append("ORDER BY 3 \n");

	return sql.toString();
    }// end-of-getSearchMoldEcoRelEcrListSQL

    /**
     * 
     * 함수명 : getSearchMoldEcoRelProdEcoListSQL 설명 : 금형 ECO 연계 제품 ECO 목록조회 SQL을 작성한다.
     * 
     * @param ketMoldChangeOrderVO
     *            : 금형 ECO VO
     * @return String : SQL
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    private String getSearchMoldEcoRelProdEcoListSQL(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT T1.classnameA2A2||':'|| T1.idA2A2 linkOid \n");
	sql.append("    , T2.classnameA2A2||':'|| T2.idA2A2 oid \n");
	sql.append("    , T2.ecoId \n");
	sql.append("    , T2.deptName orgName \n");
	sql.append("    , T2.econame ecoName \n");
	sql.append("    , (SELECT ST2.name \n");
	sql.append("    FROM WTUser ST1 \n");
	sql.append("        , people ST2 \n");
	sql.append("    WHERE ST1.idA2A2 = T2.idA3B7 \n");
	sql.append("    AND ST2.idA3B4 = ST1.idA2A2) creatorName \n");
	sql.append("    , TO_CHAR(T1.createStampA2, '" + dateFormatString + "') approveDate \n");
	sql.append("FROM KETMoldProdEcoLink T1 \n");
	sql.append("    , KETProdChangeOrder T2 \n");
	sql.append("WHERE 1 = 1 \n");
	sql.append("AND T1.idA3B5 = ? \n");
	sql.append("AND T2.idA2A2 = T1.idA3A5 \n");
	sql.append("ORDER BY 3 \n");

	return sql.toString();
    }// end-of-getSearchMoldEcoRelProdEcoListSQL

    /**
     * 
     * 함수명 : getSearchMoldEcaAllSQL 설명 : 금형ECA 상세조회 SQL을 작성한다.
     * 
     * @param ketMoldChangeOrderVO
     *            : 금형 ECO VO
     * @return String : SQL
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    private String getSearchMoldEcaAllSQL(KETMoldChangeOrderVO ketMoldChangeOrderVO, String mode, int bomCnt) throws Exception {
	KETMoldChangeOrder moldEco = ketMoldChangeOrderVO.getKetMoldChangeOrder();

	boolean isActivityCompleted = true;
	String stateStr = moldEco.getLifeCycleState().getDisplay();
	if (stateStr.equals("계획수립") || stateStr.equals("활동수행") || stateStr.equals("재작업")) {
	    isActivityCompleted = false;
	}

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT '1' activityType     \n");
	sql.append("           , T2.createStampA2 \n");
	sql.append("           , '도면' activityTypeName \n");
	sql.append("           , T1.classnameA2A2||':'|| T1.idA2A2 relEcaOid \n");
	sql.append("           , T1.activityStatus activityStatus \n");
	sql.append("           , T1.workerId workerId \n");
	sql.append("           , ( SELECT ST2.name \n");
	sql.append("                FROM WTUser ST1 \n");
	sql.append("                        , people ST2 \n");
	sql.append("               WHERE ST1.idA2A2 = TO_NUMBER(SUBSTR(T1.workerId, INSTR(T1.workerId, ':', 1, 1) + 1)) \n");
	sql.append("                  AND ST2.idA3B4 = ST1.idA2A2) workerName \n");
	sql.append("           , TO_CHAR(T1.completeDate, '" + dateFormatString + "') completeDate \n");
	sql.append("           , T2.classnameA2A2||':'|| T2.idA2A2 relEcaObjectLinkOid \n");
	sql.append("           , T4.classnameA2A2||':'|| T4.idA2A2 relEcaObjectOid \n");
	sql.append("           , T5.documentNumber relEcaObjectNo \n");
	sql.append("           , T5.name relEcaObjectName \n");
	sql.append("           , T2.preVersion relEcaObjectPreRev \n");
	sql.append("           , T2.afterVersion relEcaObjectAfterRev \n");
	sql.append("           , TO_CHAR(T2.planDate, '" + dateFormatString + "') relEcaObjectPlanDate \n");
	sql.append("           , T2.activityComment relEcaObjectActivityComment \n");
	sql.append("           , T2.changeType changeType \n");
	sql.append("           , T2.dieNo dieNo \n");
	sql.append("           , T2.scheduleId scheduleId \n");
	sql.append("           , T2.changeYn changeYn \n");
	sql.append("           , T2.epmDocType epmDocType \n");
	sql.append("           , to_number('') beforeQty \n");
	sql.append("           , to_number('') afterQty \n");
	sql.append("           ,( SELECT ST1.classnameA2A2||':'|| ST1.ida2a2 planOid \n");
	sql.append("               FROM KETMoldChangePlan ST1 \n");
	sql.append("             WHERE 1 = 1 \n");
	sql.append("                 AND ST1.scheduleId = T2.scheduleId ) planOid \n");
	sql.append("           , '' beforePartOid \n");
	sql.append("           , '' ecoheaderoid \n");
	sql.append("            , '' bom_chg_flag    \n");
	sql.append("          ,    '' beforeMaterial    \n");
	sql.append("          ,    '' afterMaterial        \n");
	sql.append("          ,    '' beforeHardness        \n");
	sql.append("          ,    '' afterHardness            \n");

	// 비용확보
	sql.append("          ,    '' AS related_die_no \n");
	sql.append("          ,    '' AS expect_cost \n");
	sql.append("          ,    '' AS secure_budget_yn \n");

	// ECN
	sql.append("          ,    T1.CUSTOMCODE AS customCode \n");
	sql.append("          ,    T1.CUSTOMNAME AS customName \n");
	sql.append("          ,    T1.COMPLETEREQUESTDATE AS completeRequestDate \n");
	sql.append("          ,    T1.RECEIVECONFIRMEDDATE AS receiveConfirmedDate \n");
	sql.append("          ,    T1.ACTIVITYTYPEDESC AS activityTypeDesc \n");
	sql.append("          ,    T1.ACTIVITYBACKDESC AS activityBackDesc \n");

	sql.append(" FROM KETMoldChangeActivity T1 \n");
	sql.append("           , KETMoldECAEpmDocLink T2 \n");
	sql.append("           , EPMDocument T4 \n");
	sql.append("           , EPMDocumentMaster T5 \n");
	sql.append("WHERE 1 = 1 \n");
	sql.append("     AND T1.idA3A8 = ? \n");
	sql.append("     AND T2.idA3B5 = T1.idA2A2 \n");
	sql.append("     AND T4.idA2A2 = T2.idA3A5 \n");
	sql.append("     AND T5.idA2A2 = T4.idA3MasterReference \n");
	sql.append("     AND T4.latestiterationinfo = 1 \n");

	if (isActivityCompleted && (mode.equalsIgnoreCase("view") || mode.equalsIgnoreCase("popupview")) && bomCnt > 0) {
	    // if (isActivityCompleted && (mode.equalsIgnoreCase("view") || mode.equalsIgnoreCase("popupview"))) {
	    // 조회화면
	    // if ((mode.equalsIgnoreCase("view") || mode.equalsIgnoreCase("popupview")) || (isActivityCompleted &&
	    // mode.equalsIgnoreCase("updateview"))) {

	    // ============================================================================
	    // 초도이고 BOM작업후 작업완료하였을 경우
	    // ============================================================================
	    sql.append("UNION ALL \n");
	    sql.append("SELECT '2' activityType \n");
	    sql.append("          , T2.createStampA2 \n");
	    sql.append("          , 'BOM' activityTypeName \n");
	    sql.append("          , T1.classnameA2A2||':'|| T1.idA2A2 relEcaOid \n");
	    sql.append("          , T1.activityStatus activityStatus \n");
	    sql.append("          , T1.workerId workerId \n");
	    sql.append("          , ( SELECT ST2.name \n");
	    sql.append("               FROM WTUser ST1 \n");
	    sql.append("                         , people ST2 \n");
	    sql.append("              WHERE ST1.idA2A2 = TO_NUMBER(SUBSTR(T1.workerId, INSTR(T1.workerId, ':', 1, 1) + 1)) \n");
	    sql.append("                  AND ST2.idA3B4 = ST1.idA2A2) workerName \n");
	    sql.append("          , TO_CHAR(T1.completeDate, '" + dateFormatString + "') completeDate \n");
	    sql.append("          , T2.classnameA2A2||':'|| T2.idA2A2 relEcaObjectLinkOid \n");
	    sql.append("          , T3.classnameA2A2||':'|| T3.idA2A2 relEcaObjectOid \n");

	    // 모부품번호
	    sql.append("          , T3.ecoItemCode relEcaObjectNo \n");

	    sql.append("          , T3.description relEcaObjectName \n");
	    sql.append("          , T2.preVersion relEcaObjectPreRev \n");
	    sql.append("          , T2.afterVersion relEcaObjectAfterRev \n");
	    sql.append("          , TO_CHAR(T2.planDate, '" + dateFormatString + "') relEcaObjectPlanDate \n");
	    sql.append("          , T2.activityComment relEcaObjectActivityComment \n");
	    sql.append("          , '' changeType \n");

	    // 자부품번호
	    sql.append("          , T4.childItemCode dieNo \n");

	    sql.append("          , T3.bomVersion scheduleId \n");// 버전
	    sql.append("          , T2.changeYn changeYn \n");
	    sql.append("          , '' epmDocType \n");
	    sql.append("          , to_number('') beforeQty \n");// 변경전수량
	    sql.append("          , to_number(T4.Quantity) afterQty \n");// 변경후수량
	    sql.append("          , '' planOid \n");
	    sql.append("          , T2.beforePartOid beforePartOid \n");
	    sql.append("          , t3.classnamea2a2|| ':' || t3.ida2a2 ecoheaderoid \n");
	    sql.append("          , '신규' bom_chg_flag     \n");
	    sql.append("          ,    '' beforeMaterial    \n");
	    sql.append("          ,    t4.material afterMaterial \n");
	    sql.append("          ,    '' beforeHardness \n");
	    sql.append("          ,    CASE WHEN t4.hardnessto IS NOT NULL OR t4.hardnessto IS NOT NULL THEN t4.hardnessfrom ||'~'|| t4.hardnessto END afterHardness \n");

	    // 비용확보
	    sql.append("          ,    T2.RELATEDDIENO AS related_die_no \n");
	    sql.append("          ,    T2.EXPECTCOST AS expect_cost \n");
	    sql.append("          ,    T2.SECUREBUDGETYN AS secure_budget_yn \n");

	    // ECN
	    sql.append("          ,    T1.CUSTOMCODE AS customCode \n");
	    sql.append("          ,    T1.CUSTOMNAME AS customName \n");
	    sql.append("          ,    T1.COMPLETEREQUESTDATE AS completeRequestDate \n");
	    sql.append("          ,    T1.RECEIVECONFIRMEDDATE AS receiveConfirmedDate \n");
	    sql.append("          ,    T1.ACTIVITYTYPEDESC AS activityTypeDesc \n");
	    sql.append("          ,    T1.ACTIVITYBACKDESC AS activityBackDesc \n");

	    sql.append("FROM KETMoldChangeActivity T1 \n");
	    sql.append("         , KETMoldECABomLink T2 \n");
	    sql.append("         , ketbomheader T3 \n");
	    sql.append("         , KETBOMComponent T4 \n");
	    sql.append("WHERE 1 = 1 \n");
	    sql.append("     AND T1.idA3A8 = ? \n");
	    sql.append("     AND T2.idA3B5 = T1.idA2A2 \n");
	    sql.append("     AND T2.idA3A5 = T3.idA2A2 \n");

	    sql.append("     AND T3.newbomcode = T4.newbomcode \n");
	    // sql.append("     AND T4.newflag = 'NEW' \n");

	    sql.append("    AND t1.completedate IS NOT NULL \n");

	    // ============================================================================
	    // 설변이고 BOM작업후 작업완료되었을 경우
	    // ============================================================================
	    sql.append("UNION ALL \n");
	    sql.append("SELECT '2' activityType \n");
	    sql.append("          , T2.createStampA2 \n");
	    sql.append("          , 'BOM' activityTypeName \n");
	    sql.append("          , T1.classnameA2A2||':'|| T1.idA2A2 relEcaOid \n");
	    sql.append("          , T1.activityStatus activityStatus \n");
	    sql.append("          , T1.workerId workerId \n");
	    sql.append("          , ( SELECT ST2.name \n");
	    sql.append("               FROM WTUser ST1 \n");
	    sql.append("                         , people ST2 \n");
	    sql.append("              WHERE ST1.idA2A2 = TO_NUMBER(SUBSTR(T1.workerId, INSTR(T1.workerId, ':', 1, 1) + 1)) \n");
	    sql.append("                  AND ST2.idA3B4 = ST1.idA2A2) workerName \n");
	    sql.append("          , TO_CHAR(T1.completeDate, '" + dateFormatString + "') completeDate \n");
	    sql.append("          , T2.classnameA2A2||':'|| T2.idA2A2 relEcaObjectLinkOid \n");
	    sql.append("          , T3.classnameA2A2||':'|| T3.idA2A2 relEcaObjectOid \n");

	    // 모부품번호
	    sql.append("          , T3.ecoItemCode relEcaObjectNo \n");

	    sql.append("          , T3.description relEcaObjectName \n");
	    sql.append("          , T2.preVersion relEcaObjectPreRev \n");
	    sql.append("          , T2.afterVersion relEcaObjectAfterRev \n");
	    sql.append("          , TO_CHAR(T2.planDate, '" + dateFormatString + "') relEcaObjectPlanDate \n");
	    sql.append("          , T2.activityComment relEcaObjectActivityComment \n");
	    sql.append("          , '' changeType \n");

	    // 자부품번호
	    sql.append("          , T4.childItemCode dieNo \n");

	    sql.append("          , T3.bomVersion scheduleId \n");// 버전
	    sql.append("          , T2.changeYn changeYn \n");
	    sql.append("          , '' epmDocType \n");
	    sql.append("          , to_number(T4.beforeQuantity) beforeQty \n");// 변경전수량
	    sql.append("          , to_number(T4.afterQuantity) afterQty \n");// 변경후수량
	    sql.append("          , '' planOid \n");
	    sql.append("          , T2.beforePartOid beforePartOid \n");
	    sql.append("          , t3.classnamea2a2|| ':' || t3.ida2a2 ecoheaderoid \n");
	    sql.append("          , DECODE( t4.ecocode, 'Add', '추가', 'Update', '변경', 'Remove', '삭제', '변경없음' ) bom_chg_flag     \n");
	    sql.append("          ,    t4.beforematerial beforeMaterial    \n");
	    sql.append("          ,    t4.aftermaterial afterMaterial        \n");
	    sql.append("          ,    CASE WHEN t4.beforehardnessfrom IS NOT NULL OR t4.afterhardnessfrom IS NOT NULL "
		    + "THEN t4.beforehardnessfrom ||'~'    || t4.afterhardnessfrom END                beforeHardness        \n");
	    sql.append("          ,    CASE WHEN t4.beforehardnessto IS NOT NULL OR t4.afterhardnessto IS NOT NULL "
		    + "THEN     t4.beforehardnessto ||'~'    || t4.afterhardnessto END                    afterHardness            \n");

	    // 비용확보
	    sql.append("          ,    T2.RELATEDDIENO AS related_die_no \n");
	    sql.append("          ,    T2.EXPECTCOST AS expect_cost \n");
	    sql.append("          ,    T2.SECUREBUDGETYN AS secure_budget_yn \n");

	    // ECN
	    sql.append("          ,    T1.CUSTOMCODE AS customCode \n");
	    sql.append("          ,    T1.CUSTOMNAME AS customName \n");
	    sql.append("          ,    T1.COMPLETEREQUESTDATE AS completeRequestDate \n");
	    sql.append("          ,    T1.RECEIVECONFIRMEDDATE AS receiveConfirmedDate \n");
	    sql.append("          ,    T1.ACTIVITYTYPEDESC AS activityTypeDesc \n");
	    sql.append("          ,    T1.ACTIVITYBACKDESC AS activityBackDesc \n");

	    sql.append("FROM KETMoldChangeActivity T1 \n");
	    sql.append("         , KETMoldECABomLink T2 \n");

	    sql.append("         , KETBomEcoHeader T3 \n");
	    /*
	     * sql.append("     , ( \n"); sql.append(
	     * "        SELECT classnameA2A2, ida2a2, ecoheadernumber, ecoitemcode, bomVersion, description, attribute2, boxquantity \n");
	     * sql.append("	  FROM ketbomecoheader \n"); sql.append("     UNION ALL \n"); sql.append(
	     * "        SELECT classnameA2A2, ida2a2, ecoheadernumber, ecoitemcode, bomVersion, description, attribute2, boxquantity \n");
	     * sql.append("	  FROM ketbomheader \n"); sql.append("       ) T3 \n");
	     */

	    sql.append("         , KETBOMECOComponent T4 \n");
	    sql.append("WHERE 1 = 1 \n");
	    sql.append("     AND T1.idA3A8 = ? \n");
	    sql.append("     AND T2.idA3B5 = T1.idA2A2 \n");
	    sql.append("     AND T2.idA3A5 = T3.idA2A2 \n");

	    sql.append("     AND T3.ecoHeaderNumber = T4.ecoHeaderNumber \n");
	    sql.append("     AND T3.ecoItemCode = T4.parentItemCode \n");
	    sql.append("   AND T3.ecoitemcode = T4.ecoitemcode \n");
	    // sql.append("     AND T4.ecocode IS NOT NULL  \n");
	    sql.append("    AND t1.completedate IS NOT NULL \n");

	    // ============================================================================
	    // 초도이거나 설변이고, BOM작업 없이 작업완료하였을 경우
	    // ============================================================================
	    sql.append("UNION ALL \n");
	    sql.append("SELECT t1.activitytype activity_type \n");
	    sql.append("           , t2.createstampa2 createStampA2             \n");
	    sql.append("           , 'BOM' activityTypeName         \n");
	    sql.append("           , t1.classnamea2a2||':'|| t1.ida2a2 relEcaOid                    \n");
	    sql.append("           , t1.activitystatus activityStatus                \n");
	    sql.append("           , t1.workerid workerId                        \n");
	    sql.append("           , ( SELECT st2.name \n");
	    sql.append("                FROM wtuser st1 \n");
	    sql.append("                          , people st2 \n");
	    sql.append("               WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1 ) + 1 ) ) \n");
	    sql.append("                   AND st2.ida3b4 = st1.ida2a2 ) workerName                \n");
	    sql.append("            , TO_CHAR( t1.completedate, 'YYYY-MM-DD' ) completeDate                \n");
	    sql.append("            , t2.classnamea2a2 ||':'|| t2.ida2a2 relEcaObjectLinkOid        \n");
	    sql.append("            , t3.classnamea2a2 ||':'|| t3.ida2a2 relEcaObjectOid            \n");
	    sql.append("            , t3.ecoitemcode relEcaObjectNo            \n");
	    sql.append("            , t3.description relEcaObjectName        \n");
	    sql.append("            , t2.preversion relEcaObjectPreRev        \n");
	    sql.append("            , t2.afterversion relEcaObjectAfterRev    \n");
	    sql.append("            , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) relEcaObjectPlanDate    \n");
	    sql.append("            , t2.activitycomment relEcaObjectActivityComment   \n");
	    sql.append("            , '' changeType \n");

	    sql.append("            , t3.ecoitemcode dieNo \n");
	    // sql.append("            , '' dieNo \n");

	    sql.append("            , '' scheduleId \n");
	    sql.append("            , t2.changeyn changeYn \n");
	    sql.append("            , '' epmDocType                \n");
	    sql.append("            , to_number('') beforeQty                     \n");
	    sql.append("            , to_number('') afterQty                     \n");
	    sql.append("            , '' planOid                        \n");
	    sql.append("            , t2.beforepartoid beforePartOid                \n");
	    sql.append("             , t3.classnamea2a2 || ':' || t3.ida2a2 ecoheaderoid                \n");
	    sql.append("            , '변경없음' bom_chg_flag                 \n");
	    sql.append("          ,    '' beforeMaterial    \n");
	    sql.append("          ,    '' afterMaterial        \n");
	    sql.append("          ,    '' beforeHardness        \n");
	    sql.append("          ,    '' afterHardness            \n");

	    // 비용확보
	    sql.append("          ,    t2.RELATEDDIENO AS related_die_no \n");
	    sql.append("          ,    t2.EXPECTCOST AS expect_cost \n");
	    sql.append("          ,    t2.SECUREBUDGETYN AS secure_budget_yn \n");

	    // ECN
	    sql.append("          ,    t1.CUSTOMCODE AS customCode \n");
	    sql.append("          ,    t1.CUSTOMNAME AS customName \n");
	    sql.append("          ,    t1.COMPLETEREQUESTDATE AS completeRequestDate \n");
	    sql.append("          ,    t1.RECEIVECONFIRMEDDATE AS receiveConfirmedDate \n");
	    sql.append("          ,    t1.ACTIVITYTYPEDESC AS activityTypeDesc \n");
	    sql.append("          ,    t1.ACTIVITYBACKDESC AS activityBackDesc \n");

	    sql.append(" FROM ketmoldchangeactivity t1 \n");
	    sql.append("           , ketmoldecabomlink t2 \n");

	    // sql.append("           , ketbomecoheader t3 \n");

	    sql.append("     , ( \n");

	    sql.append("         SELECT a.classnameA2A2, a.ida2a2, a.ecoheadernumber, a.ecoitemcode, a.bomVersion, a.description, a.attribute2, a.boxquantity \n");
	    sql.append("           FROM ketbomecoheader a \n");
	    sql.append("          WHERE a.ida2a2 NOT IN ( \n");
	    /*
	     * sql.append("                                 SELECT a.ida2a2 \n");
	     * sql.append("                                   FROM ketbomecoheader a \n");
	     * sql.append("                                 MINUS \n");
	     */sql.append("                                 SELECT T3.ida2a2 \n");
	    sql.append("                                   FROM KETMoldChangeActivity T1 \n");
	    sql.append("                                      , KETMoldECABomLink T2 \n");
	    sql.append("                                      , ketbomecoheader T3 \n");
	    sql.append("                                      , KETBOMECOComponent T4 \n");
	    sql.append("                                  WHERE 1 = 1 \n");
	    sql.append("                                    AND T2.idA3B5 = T1.idA2A2 \n");
	    sql.append("                                    AND T2.idA3A5 = T3.idA2A2 \n");
	    sql.append("                                    AND T3.ecoHeaderNumber = T4.ecoHeaderNumber \n");
	    sql.append("                                    AND T3.ecoItemCode = T4.parentItemCode \n");
	    sql.append("                                    AND T3.ecoitemcode = T4.ecoitemcode \n");
	    sql.append("                                    AND T1.completedate IS NOT NULL \n");
	    sql.append("          	                ) \n");

	    sql.append("          UNION ALL \n");

	    sql.append("         SELECT a.classnameA2A2, a.ida2a2, a.ecoheadernumber, a.ecoitemcode, a.bomVersion, a.description, a.attribute2, a.boxquantity \n");
	    sql.append("           FROM ketbomheader a \n");
	    sql.append("          WHERE a.ida2a2 IN ( \n");
	    sql.append("                                 SELECT a.ida2a2 \n");
	    sql.append("                                   FROM ketbomheader a \n");
	    sql.append("                                 MINUS \n");
	    sql.append("                                 SELECT a.ida2a2 \n");
	    sql.append("                                   FROM ketbomheader a \n");
	    sql.append("                                      , KETBOMComponent b \n");
	    sql.append("                                  WHERE a.newbomcode = b.newbomcode \n");
	    // sql.append("                                    AND b.newflag = 'NEW' \n");
	    sql.append("          	                ) \n");

	    /*
	     * sql.append(
	     * "        SELECT classnameA2A2, ida2a2, ecoheadernumber, ecoitemcode, bomVersion, description, attribute2, boxquantity \n");
	     * sql.append("	  FROM ketbomecoheader \n"); sql.append("     UNION ALL \n"); sql.append(
	     * "        SELECT classnameA2A2, ida2a2, ecoheadernumber, ecoitemcode, bomVersion, description, attribute2, boxquantity \n");
	     * sql.append("	  FROM ketbomheader \n");
	     */

	    sql.append("       ) t3 \n");

	    sql.append("WHERE 1 = 1 \n");
	    sql.append("      AND t1.ida3a8 = ? \n");
	    sql.append("      AND t2.ida3b5 = t1.ida2a2 \n");
	    sql.append("      AND t2.ida3a5 = t3.ida2a2 \n");
	    sql.append("      AND t1.completedate IS NOT NULL \n");

	}
	// 수정화면
	else {
	    sql.append("UNION ALL \n");
	    sql.append("SELECT t1.activitytype activity_type                \n");
	    sql.append("           , t2.createstampa2 createStampA2             \n");
	    sql.append("           , 'BOM' activityTypeName         \n");
	    sql.append("           , t1.classnamea2a2||':'|| t1.ida2a2 relEcaOid                    \n");
	    sql.append("           , t1.activitystatus activityStatus                \n");
	    sql.append("           , t1.workerid workerId                        \n");
	    sql.append("           , ( SELECT st2.name \n");
	    sql.append("                FROM wtuser st1 \n");
	    sql.append("                          , people st2 \n");
	    sql.append("               WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1 ) + 1 ) ) \n");
	    sql.append("                   AND st2.ida3b4 = st1.ida2a2 ) workerName                \n");
	    sql.append("            , TO_CHAR( t1.completedate, 'YYYY-MM-DD' ) completeDate                \n");
	    sql.append("            , t2.classnamea2a2 ||':'|| t2.ida2a2 relEcaObjectLinkOid        \n");
	    sql.append("            , t3.classnamea2a2 ||':'|| t3.ida2a2 relEcaObjectOid            \n");
	    sql.append("            , t3.ecoitemcode relEcaObjectNo            \n");
	    sql.append("            , t3.description relEcaObjectName        \n");
	    sql.append("            , t2.preversion relEcaObjectPreRev        \n");
	    sql.append("            , t2.afterversion relEcaObjectAfterRev    \n");
	    sql.append("            , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) relEcaObjectPlanDate    \n");
	    sql.append("            , t2.activitycomment relEcaObjectActivityComment   \n");
	    sql.append("            , '' changeType                 \n");
	    sql.append("            , '' dieNo                        \n");
	    sql.append("            , '' scheduleId                \n");
	    sql.append("            , t2.changeyn changeYn                    \n");
	    sql.append("            , '' epmDocType                \n");
	    sql.append("            , to_number('') beforeQty                     \n");
	    sql.append("            , to_number('') afterQty                     \n");
	    sql.append("            , '' planOid                        \n");
	    sql.append("            , t2.beforepartoid beforePartOid                \n");
	    sql.append("             , t3.classnamea2a2 || ':' || t3.ida2a2 ecoheaderoid                \n");
	    sql.append("            , '' bom_chg_flag                 \n");
	    sql.append("          ,    '' beforeMaterial    \n");
	    sql.append("          ,    '' afterMaterial        \n");
	    sql.append("          ,    '' beforeHardness        \n");
	    sql.append("          ,    '' afterHardness            \n");

	    // 비용확보
	    sql.append("          ,    t2.RELATEDDIENO AS related_die_no \n");
	    sql.append("          ,    t2.EXPECTCOST AS expect_cost \n");
	    sql.append("          ,    t2.SECUREBUDGETYN AS secure_budget_yn \n");

	    // ECN
	    sql.append("          ,    t1.CUSTOMCODE AS customCode \n");
	    sql.append("          ,    t1.CUSTOMNAME AS customName \n");
	    sql.append("          ,    t1.COMPLETEREQUESTDATE AS completeRequestDate \n");
	    sql.append("          ,    t1.RECEIVECONFIRMEDDATE AS receiveConfirmedDate \n");
	    sql.append("          ,    t1.ACTIVITYTYPEDESC AS activityTypeDesc \n");
	    sql.append("          ,    t1.ACTIVITYBACKDESC AS activityBackDesc \n");

	    sql.append(" FROM ketmoldchangeactivity t1                                                                                                                                         \n");
	    sql.append("           , ketmoldecabomlink t2                                                                                                                                              \n");

	    // sql.append("           , ketbomecoheader t3 \n");
	    sql.append("     , ( \n");
	    sql.append("        SELECT classnameA2A2, ida2a2, ecoheadernumber, ecoitemcode, bomVersion, description, attribute2, boxquantity \n");
	    sql.append("	  FROM ketbomecoheader \n");
	    sql.append("     UNION ALL \n");
	    sql.append("        SELECT classnameA2A2, ida2a2, ecoheadernumber, ecoitemcode, bomVersion, description, attribute2, boxquantity \n");
	    sql.append("	  FROM ketbomheader \n");
	    sql.append("       ) t3 \n");

	    sql.append("WHERE 1 = 1                                                                                                                                                                   \n");
	    sql.append("      AND t1.ida3a8 = ?                                                                                                                                                       \n");
	    sql.append("      AND t2.ida3b5 = t1.ida2a2                                                                                                                                             \n");
	    sql.append("      AND t2.ida3a5 = t3.ida2a2                                                                                                                                             \n");
	}

	sql.append("UNION ALL \n");
	sql.append("SELECT '3' activityType \n");
	sql.append("          , T2.createStampA2 \n");
	sql.append("          , '표준품'                                                                                                                                     activityTypeName \n");
	sql.append("          , T1.classnameA2A2||':'|| T1.idA2A2                                                                                                 relEcaOid \n");
	sql.append("          , T1.activityStatus                                                                                                                         activityStatus \n");
	sql.append("          , T1.workerId                                                                                                                             workerId \n");
	sql.append("          , ( SELECT ST2.name \n");
	sql.append("              FROM WTUser ST1 \n");
	sql.append("                      , people ST2 \n");
	sql.append("             WHERE ST1.idA2A2 = TO_NUMBER(SUBSTR(T1.workerId, INSTR(T1.workerId, ':', 1, 1) + 1)) \n");
	sql.append("                 AND ST2.idA3B4 = ST1.idA2A2)                                                                                             workerName \n");
	sql.append("          , TO_CHAR(T1.completeDate, '" + dateFormatString + "')                                                                     completeDate \n");
	sql.append("          , DECODE(T2.idA2A2, NULL, '', T2.classnameA2A2||':'|| T2.idA2A2)                                                         relEcaObjectLinkOid \n");
	sql.append("          , DECODE(T4.idA2A2, NULL, '', T4.classnameA2A2||':'|| T4.idA2A2)                                                         relEcaObjectOid \n");
	sql.append("          , T4.documentNo                                                                                                                         relEcaObjectNo \n");
	sql.append("          , T4.title                                                                                                                                    relEcaObjectName \n");
	sql.append("          , T2.preVersion                                                                                                                             relEcaObjectPreRev \n");
	sql.append("          , T2.afterVersion                                                                                                                         relEcaObjectAfterRev \n");
	sql.append("          , TO_CHAR(T2.planDate, '" + dateFormatString + "')                                                                         relEcaObjectPlanDate \n");
	sql.append("          , T2.activityComment                                                                                                                     relEcaObjectActivityComment \n");
	sql.append("          , ( SELECT decode( COUNT(*), 0, '',  max(l.partno)||' 외 '||(COUNT(*)-1)||'개' )                                        \n");
	sql.append("               FROM ketmoldstdpartline l                                                                                                     \n");
	sql.append("             WHERE l.ida3a3 = t2.ida2a2 )                                                                                                     changeType \n");
	sql.append("          , T2.targetPart                                                                                                                             dieNo \n");
	sql.append("          , ''                                                                                                                                             scheduleId    \n");
	sql.append("          , ''                                                                                                                                             changeYn         \n");
	sql.append("          , ''                                                                                                                                             epmDocType  \n");
	sql.append("           , to_number('')                                                                                                                             beforeQty        \n");
	sql.append("           , to_number('')                                                                                                                            afterQty            \n");
	sql.append("           , ''                                                                                                                                             planOid         \n");
	sql.append("           , ''                                                                                                                                             beforePartOid \n");
	sql.append("           , ''                                                                                                                                             ecoheaderoid \n");
	sql.append("            , ''                                                                                                                                             bom_chg_flag    \n");
	sql.append("          ,    ''                                                                                                                                             beforeMaterial    \n");
	sql.append("          ,    ''                                                                                                                                             afterMaterial        \n");
	sql.append("          ,    ''                                                                                                                                             beforeHardness        \n");
	sql.append("          ,    ''                                                                                                                                              afterHardness            \n");

	// 비용확보
	sql.append("          ,    '' AS related_die_no \n");
	sql.append("          ,    '' AS expect_cost \n");
	sql.append("          ,    '' AS secure_budget_yn \n");

	// ECN
	sql.append("          ,    T1.CUSTOMCODE AS customCode \n");
	sql.append("          ,    T1.CUSTOMNAME AS customName \n");
	sql.append("          ,    T1.COMPLETEREQUESTDATE AS completeRequestDate \n");
	sql.append("          ,    T1.RECEIVECONFIRMEDDATE AS receiveConfirmedDate \n");
	sql.append("          ,    T1.ACTIVITYTYPEDESC AS activityTypeDesc \n");
	sql.append("          ,    T1.ACTIVITYBACKDESC AS activityBackDesc \n");

	sql.append("FROM KETMoldChangeActivity T1                                                                                                                             \n");
	sql.append("         , KETMoldECADocLink T2                                                                                                                                 \n");
	sql.append("         , KETProjectDocument T4                                                                                                                                 \n");
	sql.append("WHERE 1 = 1                                                                                                                                                         \n");
	sql.append("    AND T1.idA3A8 = ?                                                                                                                                             \n");
	sql.append("     AND T2.idA3B5 = T1.idA2A2                                                                                                                                 \n");
	sql.append("     AND T4.idA2A2 = T2.idA3A5                                                                                                                                 \n");

	// ECN
	sql.append("UNION ALL \n");
	sql.append("SELECT '4' activityType \n");
	sql.append("          , T2.createStampA2 \n");
	sql.append("          , '활동'                                                                                                                                     activityTypeName \n");
	sql.append("          , T1.classnameA2A2||':'|| T1.idA2A2                                                                                                 relEcaOid \n");
	sql.append("          , T1.activityStatus                                                                                                                         activityStatus \n");
	sql.append("          , T1.workerId                                                                                                                             workerId \n");
	sql.append("          , ( SELECT ST2.name \n");
	sql.append("              FROM WTUser ST1 \n");
	sql.append("                      , people ST2 \n");
	sql.append("             WHERE ST1.idA2A2 = TO_NUMBER(SUBSTR(T1.workerId, INSTR(T1.workerId, ':', 1, 1) + 1)) \n");
	sql.append("                 AND ST2.idA3B4 = ST1.idA2A2)                                                                                             workerName \n");
	sql.append("          , TO_CHAR(T1.completeDate, '" + dateFormatString + "')                                                                     completeDate \n");
	sql.append("          , DECODE(T2.idA2A2, NULL, '', T2.classnameA2A2||':'|| T2.idA2A2)                                                         relEcaObjectLinkOid \n");
	sql.append("          , DECODE(T4.idA2A2, NULL, '', T4.classnameA2A2||':'|| T4.idA2A2)                                                         relEcaObjectOid \n");
	sql.append("          , T4.documentNo                                                                                                                         relEcaObjectNo \n");
	sql.append("          , T4.title                                                                                                                                    relEcaObjectName \n");
	sql.append("          , T2.preVersion                                                                                                                             relEcaObjectPreRev \n");
	sql.append("          , T2.afterVersion                                                                                                                         relEcaObjectAfterRev \n");
	sql.append("          , TO_CHAR(T2.planDate, '" + dateFormatString + "')                                                                         relEcaObjectPlanDate \n");
	sql.append("          , T2.activityComment                                                                                                                     relEcaObjectActivityComment \n");
	sql.append("          , ( SELECT decode( COUNT(*), 0, '',  max(l.partno)||' 외 '||(COUNT(*)-1)||'개' )                                        \n");
	sql.append("               FROM ketmoldstdpartline l                                                                                                     \n");
	sql.append("             WHERE l.ida3a3 = t2.ida2a2 )                                                                                                     changeType \n");
	sql.append("          , T2.targetPart                                                                                                                             dieNo \n");
	sql.append("          , ''                                                                                                                                             scheduleId    \n");
	sql.append("          , ''                                                                                                                                             changeYn         \n");
	sql.append("          , ''                                                                                                                                             epmDocType  \n");
	sql.append("           , to_number('')                                                                                                                             beforeQty        \n");
	sql.append("           , to_number('')                                                                                                                            afterQty            \n");
	sql.append("           , ''                                                                                                                                             planOid         \n");
	sql.append("           , ''                                                                                                                                             beforePartOid \n");
	sql.append("           , ''                                                                                                                                             ecoheaderoid \n");
	sql.append("            , ''                                                                                                                                             bom_chg_flag    \n");
	sql.append("          ,    ''                                                                                                                                             beforeMaterial    \n");
	sql.append("          ,    ''                                                                                                                                             afterMaterial        \n");
	sql.append("          ,    ''                                                                                                                                             beforeHardness        \n");
	sql.append("          ,    ''                                                                                                                                              afterHardness            \n");

	// 비용확보
	sql.append("          ,    '' AS related_die_no \n");
	sql.append("          ,    '' AS expect_cost \n");
	sql.append("          ,    '' AS secure_budget_yn \n");

	// ECN
	sql.append("          ,    T1.CUSTOMCODE AS customCode \n");
	sql.append("          ,    T1.CUSTOMNAME AS customName \n");
	sql.append("          ,    T1.COMPLETEREQUESTDATE AS completeRequestDate \n");
	sql.append("          ,    T1.RECEIVECONFIRMEDDATE AS receiveConfirmedDate \n");
	sql.append("          ,    T1.ACTIVITYTYPEDESC AS activityTypeDesc \n");
	sql.append("          ,    T1.ACTIVITYBACKDESC AS activityBackDesc \n");

	sql.append("FROM KETMoldChangeActivity T1 \n");
	sql.append("         , KETMoldECADocLink T2 \n");
	sql.append("         , KETProjectDocument T4 \n");
	sql.append("WHERE T1.activityType = '4' \n");
	sql.append("    AND T1.idA3A8 = ? \n");
	sql.append("     AND T1.idA2A2 = T2.idA3B5(+) \n");
	sql.append("     AND T2.idA3A5 = T4.idA2A2(+) \n");

	sql.append("ORDER BY 1 ASC, 11 ASC, 18 ASC \n");
	return sql.toString();
    }// end-of-getSearchMoldEcaAllSQL

    /**
     * 
     * 함수명 : getSearchMoldEcaAllByWorkerIdSQL 설명 : 금형ECA 담당자별 상세조회 SQL을 작성한다.
     * 
     * @param ketMoldChangeOrderVO
     *            : 금형 ECO VO
     * @return String : SQL
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    private String getSearchMoldEcaAllByWorkerIdSQL(KETMoldChangeOrderVO ketMoldChangeOrderVO, String cmd) throws Exception {
	StringBuffer sql = new StringBuffer();
	int ecaType = StringUtil.getIntParameter(ketMoldChangeOrderVO.getEcaType(), 0);
	boolean isActivityComplete = false;

	if (ketMoldChangeOrderVO.getKetMoldChangeOrder().getLifeCycleState().getDisplay().equals("활동완료")) {
	    isActivityComplete = true;
	}

	if (ecaType == 1) {// 도면
	    sql.append("SELECT '1'                                                                                                                             activityType \n");
	    sql.append("          , T2.createStampA2                                                                                                                             \n");
	    sql.append("          , '도면'                                                                                                                         activityTypeName \n");
	    sql.append("          , T1.classnameA2A2||':'|| T1.idA2A2                                                                                 relEcaOid \n");
	    sql.append("          , T1.activityStatus                                                                                                         activityStatus \n");
	    sql.append("          , T1.workerId                                                                                                             workerId \n");
	    sql.append("          , (SELECT ST2.name \n");
	    sql.append("              FROM WTUser ST1 \n");
	    sql.append("                     , people ST2 \n");
	    sql.append("            WHERE ST1.idA2A2 = TO_NUMBER(SUBSTR(T1.workerId, INSTR(T1.workerId, ':', 1, 1) + 1)) \n");
	    sql.append("                AND ST2.idA3B4 = ST1.idA2A2)                                                                                 workerName \n");
	    sql.append("          , TO_CHAR(T1.completeDate, '" + dateFormatString + "')                                                     completeDate \n");
	    sql.append("          , T2.classnameA2A2||':'|| T2.idA2A2                                                                                 relEcaObjectLinkOid \n");
	    sql.append("          , T4.classnameA2A2||':'|| T4.idA2A2                                                                                 relEcaObjectOid \n");
	    sql.append("          , T5.documentNumber                                                                                                 relEcaObjectNo \n");
	    sql.append("          , T5.name                                                                                                                 relEcaObjectName \n");
	    sql.append("          , T2.preVersion                                                                                                             relEcaObjectPreRev \n");
	    sql.append("          , T2.afterVersion                                                                                                         relEcaObjectAfterRev \n");
	    sql.append("          , TO_CHAR(T2.planDate, '" + dateFormatString + "')                                                         relEcaObjectPlanDate \n");
	    sql.append("          , T2.activityComment                                                                                                     relEcaObjectActivityComment \n");
	    sql.append("          , T2.changeType                                                                                                         changeType \n");
	    sql.append("          , T2.dieNo                                                                                                                 dieNo \n");
	    sql.append("          , T2.scheduleId                                                                                                             scheduleId \n");
	    sql.append("          , T2.changeYn                                                                                                             changeYn \n");
	    sql.append("          , T2.epmDocType                                                                                                         epmDocType \n");
	    sql.append("            , ''                                                                                                                                          beforeQty                     \n");
	    sql.append("            , ''                                                                                                                                          afterQty                     \n");
	    sql.append("          , ( SELECT ST1.classnameA2A2||':'|| ST1.ida2a2 planOid \n");
	    sql.append("                FROM KETMoldChangePlan ST1 \n");
	    sql.append("               WHERE 1 = 1 \n");
	    sql.append("                   AND ST1.scheduleId = T2.scheduleId)                                                                     planOid \n");
	    sql.append("          , ''                                                                                                                             beforePartOid \n");
	    sql.append("          , ''                                                                                                                             ecoHeaderOid \n");
	    sql.append("          , ''                                                                                                                             bom_chg_flag     \n");

	    // 비용확보
	    sql.append("          ,    '' AS related_die_no \n");
	    sql.append("          ,    '' AS expect_cost \n");
	    sql.append("          ,    '' AS secure_budget_yn \n");

	    // ECN
	    sql.append("          ,    T1.CUSTOMCODE AS customCode \n");
	    sql.append("          ,    T1.CUSTOMNAME AS customName \n");
	    sql.append("          ,    T1.COMPLETEREQUESTDATE AS completeRequestDate \n");
	    sql.append("          ,    T1.RECEIVECONFIRMEDDATE AS receiveConfirmedDate \n");
	    sql.append("          ,    T1.ACTIVITYTYPEDESC AS activityTypeDesc \n");
	    sql.append("          ,    T1.ACTIVITYBACKDESC AS activityBackDesc \n");

	    sql.append("FROM KETMoldChangeActivity T1 \n");
	    sql.append("         , KETMoldECAEpmDocLink T2 \n");
	    sql.append("         , EPMDocument T4 \n");
	    sql.append("         , EPMDocumentMaster T5 \n");
	    sql.append("WHERE 1 = 1 \n");
	    sql.append("     AND T1.idA3A8 = ? \n");
	    sql.append("     AND T1.workerId = ? \n");
	    sql.append("     AND T2.idA3B5 = T1.idA2A2 \n");
	    sql.append("     AND T4.idA2A2 = T2.idA3A5 \n");
	    sql.append("     AND T5.idA2A2 = T4.idA3MasterReference \n");
	    sql.append("     AND T4.latestiterationinfo = 1 \n");
	} else if (ecaType == 2) {// BOM
	    if (cmd.equalsIgnoreCase("view") || isActivityComplete) {
		// sql.append(
		// "SELECT t1.activitytype                                                                                                                        activityType                    \n"
		// );
		// sql.append(
		// "           , t2.createstampa2                                                                                                                   createStampA2             \n"
		// );
		// sql.append(
		// "           , 'BOM'                                                                                                                                 activityTypeName         \n"
		// );
		// sql.append(
		// "           , t1.classnamea2a2||':'|| t1.ida2a2                                                                                                 relEcaOid                    \n"
		// );
		// sql.append(
		// "           , t1.activitystatus                                                                                                                     activityStatus                \n"
		// );
		// sql.append(
		// "           , t1.workerid                                                                                                                         workerId                        \n"
		// );
		// sql.append(
		// "           , ( SELECT st2.name                                                                                                                                                  \n"
		// );
		// sql.append(
		// "                FROM wtuser st1                                                                                                                                                 \n"
		// );
		// sql.append(
		// "                          , people st2                                                                                                                                              \n"
		// );
		// sql.append(
		// "               WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1 ) + 1 ) )                                            \n"
		// );
		// sql.append(
		// "                   AND st2.ida3b4 = st1.ida2a2 )                                                                                         workerName                \n"
		// );
		// sql.append(
		// "            , TO_CHAR( t1.completedate, 'YYYY-MM-DD' )                                                                             completeDate                \n"
		// );
		// sql.append(
		// "            , t2.classnamea2a2 ||':'|| t2.ida2a2                                                                                             relEcaObjectLinkOid        \n"
		// );
		// sql.append(
		// "            , t3.classnamea2a2 ||':'|| t3.ida2a2                                                                                             relEcaObjectOid            \n"
		// );
		// sql.append(
		// "            , t3.ecoitemcode                                                                                                                     relEcaObjectNo            \n"
		// );
		// sql.append(
		// "            , t3.description                                                                                                                     relEcaObjectName        \n"
		// );
		// sql.append(
		// "            , t2.preversion                                                                                                                     relEcaObjectPreRev        \n"
		// );
		// sql.append(
		// "            , t2.afterversion                                                                                                                     relEcaObjectAfterRev    \n"
		// );
		// sql.append(
		// "            , TO_CHAR( t2.plandate, 'YYYY-MM-DD' )                                                                                 relEcaObjectPlanDate    \n"
		// );
		// sql.append(
		// "            , t2.activitycomment                                                                                                             relEcaObjectActivityComment   \n"
		// );
		// sql.append(
		// "            , ''                                                                                                                                         changeType                 \n");
		// sql.append(
		// "            , ''                                                                                                                                         dieNo                        \n"
		// );
		// sql.append(
		// "            , ''                                                                                                                                         scheduleId                \n"
		// );
		// sql.append(
		// "            , t2.changeyn                                                                                                                         changeYn                    \n"
		// );
		// sql.append(
		// "            , ''                                                                                                                                        epmDocType                \n"
		// );
		// sql.append(
		// "            , ''                                                                                                                                          beforeQty                     \n"
		// );
		// sql.append(
		// "            , ''                                                                                                                                          afterQty                     \n"
		// );
		// sql.append(
		// "            , ''                                                                                                                                         planOid                        \n"
		// );
		// sql.append(
		// "            , t2.beforepartoid                                                                                                                    beforePartOid                \n"
		// );
		// sql.append(
		// "             , t3.classnamea2a2 || ':' || t3.ida2a2                                                                                            ecoheaderoid                \n"
		// );
		// sql.append(
		// " FROM ketmoldchangeactivity t1                                                                                                                                         \n"
		// );
		// sql.append(
		// "           , ketmoldecabomlink t2                                                                                                                                              \n"
		// );
		// sql.append(
		// "           , ketbomecoheader t3                                                                                                                                            \n"
		// );
		// sql.append(
		// "WHERE 1 = 1                                                                                                                                                                   \n"
		// );
		// sql.append(
		// "      AND t1.ida3a8 = ?                                                                                                                                                       \n"
		// );
		// sql.append("      AND t1.workerId = ?                                                                                                                                                     \n"
		// );
		// sql.append(
		// "      AND t2.ida3b5 = t1.ida2a2                                                                                                                                             \n"
		// );
		// sql.append(
		// "      AND t2.ida3a5 = t3.ida2a2                                                                                                                                             \n"
		// );

		sql.append("SELECT '2'                                                                                                                                          activityType \n");
		sql.append("          , T2.createStampA2                                                                                                                                   \n");
		sql.append("          , 'BOM'                                                                                                                                  activityTypeName \n");
		sql.append("          , T1.classnameA2A2||':'|| T1.idA2A2                                                                                              relEcaOid \n");
		sql.append("          , T1.activityStatus                                                                                                                      activityStatus \n");
		sql.append("          , T1.workerId                                                                                                                          workerId \n");
		sql.append("          , ( SELECT ST2.name \n");
		sql.append("               FROM WTUser ST1 \n");
		sql.append("                         , people ST2 \n");
		sql.append("              WHERE ST1.idA2A2 = TO_NUMBER(SUBSTR(T1.workerId, INSTR(T1.workerId, ':', 1, 1) + 1))                                 \n");
		sql.append("                  AND ST2.idA3B4 = ST1.idA2A2)                                                                                          workerName \n");
		sql.append("          , TO_CHAR(T1.completeDate, '" + dateFormatString + "')                                                                  completeDate \n");
		sql.append("          , T2.classnameA2A2||':'|| T2.idA2A2                                                                                              relEcaObjectLinkOid \n");
		sql.append("          , T3.classnameA2A2||':'|| T3.idA2A2                                                                                              relEcaObjectOid \n");
		sql.append("          , T3.ecoItemCode                                                                                                                      relEcaObjectNo \n");
		sql.append("          , T3.description                                                                                                                          relEcaObjectName \n");
		sql.append("          , T2.preVersion                                                                                                                          relEcaObjectPreRev \n");
		sql.append("          , T2.afterVersion                                                                                                                      relEcaObjectAfterRev \n");
		sql.append("          , TO_CHAR(T2.planDate, '" + dateFormatString + "')                                                                      relEcaObjectPlanDate \n");
		sql.append("          , T2.activityComment                                                                                                                  relEcaObjectActivityComment \n");
		sql.append("          , ''                                                                                                                                          changeType \n");
		sql.append("          , T4.childItemCode                                                                                                                  dieNo \n");// 자부품번호
		sql.append("          , T3.bomVersion                                                                                                                      scheduleId \n");// 버전
		sql.append("          , T2.changeYn                                                                                                                          changeYn \n");
		sql.append("          , ''                                                                                                                                           epmDocType \n");
		sql.append("          , T4.beforeQuantity                                                                                                                  beforeQty \n");// 변경전수량
		sql.append("          , T4.afterQuantity                                                                                                                      afterQty \n");// 변경후수량
		sql.append("          , ''                                                                                                                                          planOid \n");
		sql.append("          , T2.beforePartOid                                                                                                                      beforePartOid \n");
		sql.append("          , t3.classnamea2a2|| ':' || t3.ida2a2                                                                                               ecoheaderoid \n");
		sql.append("          , DECODE( t4.ecocode, 'Add', '추가', 'Update', '변경', 'Remove', '삭제' )                                                 bom_chg_flag     \n");

		// 비용확보
		sql.append("          ,    T2.RELATEDDIENO AS related_die_no \n");
		sql.append("          ,    T2.EXPECTCOST AS expect_cost \n");
		sql.append("          ,    T2.SECUREBUDGETYN AS secure_budget_yn \n");

		// ECN
		sql.append("          ,    T1.CUSTOMCODE AS customCode \n");
		sql.append("          ,    T1.CUSTOMNAME AS customName \n");
		sql.append("          ,    T1.COMPLETEREQUESTDATE AS completeRequestDate \n");
		sql.append("          ,    T1.RECEIVECONFIRMEDDATE AS receiveConfirmedDate \n");
		sql.append("          ,    T1.ACTIVITYTYPEDESC AS activityTypeDesc \n");
		sql.append("          ,    T1.ACTIVITYBACKDESC AS activityBackDesc \n");

		sql.append("FROM KETMoldChangeActivity T1 \n");
		sql.append("         , KETMoldECABomLink T2 \n");

		// sql.append("         , KETBomEcoHeader T3 \n");
		sql.append("     , ( \n");
		sql.append("        SELECT classnameA2A2, ida2a2, ecoheadernumber, ecoitemcode, bomVersion, description, attribute2, boxquantity \n");
		sql.append("	  FROM ketbomecoheader \n");
		sql.append("     UNION ALL \n");
		sql.append("        SELECT classnameA2A2, ida2a2, ecoheadernumber, ecoitemcode, bomVersion, description, attribute2, boxquantity \n");
		sql.append("	  FROM ketbomheader \n");
		sql.append("       ) T3 \n");

		sql.append("         , KETBOMECOComponent T4 \n");
		sql.append("WHERE 1 = 1 \n");
		sql.append("     AND T1.idA3A8 = ? \n");
		sql.append("      AND t1.workerId = ?                                                                                                                                                     \n");
		sql.append("     AND T2.idA3B5 = T1.idA2A2 \n");
		sql.append("     AND T2.idA3A5 = T3.idA2A2 \n");
		sql.append("     AND T3.ecoHeaderNumber = T4.ecoHeaderNumber \n");
		sql.append("     AND T3.ecoItemCode = T4.parentItemCode \n");
		sql.append("   AND T3.ecoitemcode = T4.ecoitemcode                                                            \n");
		sql.append("     AND T4.ecocode IS NOT NULL  \n");
	    } else {
		sql.append("SELECT t1.activitytype                                                                                                                        activityType                    \n");
		sql.append("           , t2.createstampa2                                                                                                                   createStampA2             \n");
		sql.append("           , 'BOM'                                                                                                                                 activityTypeName         \n");
		sql.append("           , t1.classnamea2a2||':'|| t1.ida2a2                                                                                                 relEcaOid                    \n");
		sql.append("           , t1.activitystatus                                                                                                                     activityStatus                \n");
		sql.append("           , t1.workerid                                                                                                                         workerId                        \n");
		sql.append("           , ( SELECT st2.name                                                                                                                                                  \n");
		sql.append("                FROM wtuser st1                                                                                                                                                 \n");
		sql.append("                          , people st2                                                                                                                                              \n");
		sql.append("               WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1 ) + 1 ) )                                            \n");
		sql.append("                   AND st2.ida3b4 = st1.ida2a2 )                                                                                         workerName                \n");
		sql.append("            , TO_CHAR( t1.completedate, 'YYYY-MM-DD' )                                                                             completeDate                \n");
		sql.append("            , t2.classnamea2a2 ||':'|| t2.ida2a2                                                                                             relEcaObjectLinkOid        \n");
		sql.append("            , t3.classnamea2a2 ||':'|| t3.ida2a2                                                                                             relEcaObjectOid            \n");
		sql.append("            , t3.ecoitemcode                                                                                                                     relEcaObjectNo            \n");
		sql.append("            , t3.description                                                                                                                     relEcaObjectName        \n");
		sql.append("            , t2.preversion                                                                                                                     relEcaObjectPreRev        \n");
		sql.append("            , t2.afterversion                                                                                                                     relEcaObjectAfterRev    \n");
		sql.append("            , TO_CHAR( t2.plandate, 'YYYY-MM-DD' )                                                                                 relEcaObjectPlanDate    \n");
		sql.append("            , t2.activitycomment                                                                                                             relEcaObjectActivityComment   \n");
		sql.append("            , ''                                                                                                                                         changeType                 \n");
		sql.append("            , ''                                                                                                                                         dieNo                        \n");
		sql.append("            , ''                                                                                                                                         scheduleId                \n");
		sql.append("            , t2.changeyn                                                                                                                         changeYn                    \n");
		sql.append("            , ''                                                                                                                                        epmDocType                \n");
		sql.append("            , ''                                                                                                                                          beforeQty                     \n");
		sql.append("            , ''                                                                                                                                          afterQty                     \n");
		sql.append("            , ''                                                                                                                                         planOid                        \n");
		sql.append("            , t2.beforepartoid                                                                                                                    beforePartOid                \n");
		sql.append("             , t3.classnamea2a2 || ':' || t3.ida2a2                                                                                            ecoheaderoid                \n");
		sql.append("          , ''                                                                                                                                         bom_chg_flag                 \n");

		// 비용확보
		sql.append("          ,    t2.RELATEDDIENO AS related_die_no \n");
		sql.append("          ,    t2.EXPECTCOST AS expect_cost \n");
		sql.append("          ,    t2.SECUREBUDGETYN AS secure_budget_yn \n");

		// ECN
		sql.append("          ,    t1.CUSTOMCODE AS customCode \n");
		sql.append("          ,    t1.CUSTOMNAME AS customName \n");
		sql.append("          ,    t1.COMPLETEREQUESTDATE AS completeRequestDate \n");
		sql.append("          ,    t1.RECEIVECONFIRMEDDATE AS receiveConfirmedDate \n");
		sql.append("          ,    t1.ACTIVITYTYPEDESC AS activityTypeDesc \n");
		sql.append("          ,    t1.ACTIVITYBACKDESC AS activityBackDesc \n");

		sql.append(" FROM ketmoldchangeactivity t1                                                                                                                                         \n");
		sql.append("           , ketmoldecabomlink t2                                                                                                                                              \n");

		// sql.append("           , ketbomecoheader t3 \n");
		sql.append("     , ( \n");
		sql.append("        SELECT classnameA2A2, ida2a2, ecoheadernumber, ecoitemcode, bomVersion, description, attribute2, boxquantity \n");
		sql.append("	  FROM ketbomecoheader \n");
		sql.append("     UNION ALL \n");
		sql.append("        SELECT classnameA2A2, ida2a2, ecoheadernumber, ecoitemcode, bomVersion, description, attribute2, boxquantity \n");
		sql.append("	  FROM ketbomheader \n");
		sql.append("       ) t3 \n");

		sql.append("WHERE 1 = 1                                                                                                                                                                   \n");
		sql.append("      AND t1.ida3a8 = ?                                                                                                                                                       \n");
		sql.append("      AND t1.workerId = ?                                                                                                                                                     \n");
		sql.append("      AND t2.ida3b5 = t1.ida2a2                                                                                                                                             \n");
		sql.append("      AND t2.ida3a5 = t3.ida2a2                                                                                                                                             \n");
	    }
	} else if (ecaType == 3) {// 표준품
	    sql.append("SELECT '3' activityType \n");
	    sql.append("    , T2.createStampA2 \n");
	    sql.append("    , '표준품' activityTypeName \n");
	    sql.append("    , T1.classnameA2A2||':'|| T1.idA2A2 relEcaOid \n");
	    sql.append("    , T1.activityStatus activityStatus \n");
	    sql.append("    , T1.workerId workerId \n");
	    sql.append("    , (SELECT ST2.name \n");
	    sql.append("    FROM WTUser ST1 \n");
	    sql.append("        , people ST2 \n");
	    sql.append("    WHERE ST1.idA2A2 = TO_NUMBER(SUBSTR(T1.workerId, INSTR(T1.workerId, ':', 1, 1) + 1)) \n");
	    sql.append("    AND ST2.idA3B4 = ST1.idA2A2) workerName \n");
	    sql.append("    , TO_CHAR(T1.completeDate, '" + dateFormatString + "') completeDate \n");
	    sql.append("    , DECODE(T2.idA2A2, NULL, '', T2.classnameA2A2||':'|| T2.idA2A2) relEcaObjectLinkOid \n");
	    sql.append("    , DECODE(T4.idA2A2, NULL, '', T4.classnameA2A2||':'|| T4.idA2A2) relEcaObjectOid \n");
	    sql.append("    , T4.documentNo relEcaObjectNo \n");
	    sql.append("    , '' relEcaObjectName \n");
	    sql.append("    , T2.preVersion relEcaObjectPreRev \n");
	    sql.append("    , T2.afterVersion relEcaObjectAfterRev \n");
	    sql.append("    , TO_CHAR(T2.planDate, '" + dateFormatString + "') relEcaObjectPlanDate \n");
	    sql.append("    , T2.activityComment relEcaObjectActivityComment \n");
	    sql.append("    , T2.changeType changeType \n");
	    sql.append("    , T2.targetPart dieNo \n");
	    sql.append("    , '' scheduleId \n");
	    sql.append("    , '' changeYn \n");
	    sql.append("    , '' epmDocType \n");
	    sql.append("            , ''                                                                                                                                          beforeQty                     \n");
	    sql.append("            , ''                                                                                                                                          afterQty                     \n");
	    sql.append("    , '' planOid \n");
	    sql.append("    , '' beforePartOid \n");
	    sql.append("    , '' ecoHeaderOid \n");
	    sql.append("    , ''                                                                                                                                         bom_chg_flag                 \n");

	    // 비용확보
	    sql.append("          ,    '' AS related_die_no \n");
	    sql.append("          ,    '' AS expect_cost \n");
	    sql.append("          ,    '' AS secure_budget_yn \n");

	    // ECN
	    sql.append("          ,    T1.CUSTOMCODE AS customCode \n");
	    sql.append("          ,    T1.CUSTOMNAME AS customName \n");
	    sql.append("          ,    T1.COMPLETEREQUESTDATE AS completeRequestDate \n");
	    sql.append("          ,    T1.RECEIVECONFIRMEDDATE AS receiveConfirmedDate \n");
	    sql.append("          ,    T1.ACTIVITYTYPEDESC AS activityTypeDesc \n");
	    sql.append("          ,    T1.ACTIVITYBACKDESC AS activityBackDesc \n");

	    sql.append("FROM KETMoldChangeActivity T1 \n");
	    sql.append("    , KETMoldECADocLink T2 \n");
	    sql.append("    , KETProjectDocument T4 \n");
	    sql.append("WHERE 1 = 1 \n");
	    sql.append("AND T1.idA3A8 = ? \n");
	    sql.append("AND T1.workerId = ? \n");
	    sql.append("AND T2.idA3B5 = T1.idA2A2 \n");
	    sql.append("AND T4.idA2A2 = T2.idA3A5 \n");
	} else if (ecaType == 4) {// ECN
	    sql.append("SELECT '4' activityType \n");
	    sql.append("    , T2.createStampA2 \n");
	    sql.append("    , '표준품' activityTypeName \n");
	    sql.append("    , T1.classnameA2A2||':'|| T1.idA2A2 relEcaOid \n");
	    sql.append("    , T1.activityStatus activityStatus \n");
	    sql.append("    , T1.workerId workerId \n");
	    sql.append("    , (SELECT ST2.name \n");
	    sql.append("    FROM WTUser ST1 \n");
	    sql.append("        , people ST2 \n");
	    sql.append("    WHERE ST1.idA2A2 = TO_NUMBER(SUBSTR(T1.workerId, INSTR(T1.workerId, ':', 1, 1) + 1)) \n");
	    sql.append("    AND ST2.idA3B4 = ST1.idA2A2) workerName \n");
	    sql.append("    , TO_CHAR(T1.completeDate, '" + dateFormatString + "') completeDate \n");
	    sql.append("    , DECODE(T2.idA2A2, NULL, '', T2.classnameA2A2||':'|| T2.idA2A2) relEcaObjectLinkOid \n");
	    sql.append("    , DECODE(T4.idA2A2, NULL, '', T4.classnameA2A2||':'|| T4.idA2A2) relEcaObjectOid \n");
	    sql.append("    , T4.documentNo relEcaObjectNo \n");
	    sql.append("    , '' relEcaObjectName \n");
	    sql.append("    , T2.preVersion relEcaObjectPreRev \n");
	    sql.append("    , T2.afterVersion relEcaObjectAfterRev \n");
	    sql.append("    , TO_CHAR(T2.planDate, '" + dateFormatString + "') relEcaObjectPlanDate \n");
	    sql.append("    , T2.activityComment relEcaObjectActivityComment \n");
	    sql.append("    , T2.changeType changeType \n");
	    sql.append("    , T2.targetPart dieNo \n");
	    sql.append("    , '' scheduleId \n");
	    sql.append("    , '' changeYn \n");
	    sql.append("    , '' epmDocType \n");
	    sql.append("            , ''                                                                                                                                          beforeQty                     \n");
	    sql.append("            , ''                                                                                                                                          afterQty                     \n");
	    sql.append("    , '' planOid \n");
	    sql.append("    , '' beforePartOid \n");
	    sql.append("    , '' ecoHeaderOid \n");
	    sql.append("    , ''                                                                                                                                         bom_chg_flag                 \n");

	    // 비용확보
	    sql.append("          ,    '' AS related_die_no \n");
	    sql.append("          ,    '' AS expect_cost \n");
	    sql.append("          ,    '' AS secure_budget_yn \n");

	    // ECN
	    sql.append("          ,    T1.CUSTOMCODE AS customCode \n");
	    sql.append("          ,    T1.CUSTOMNAME AS customName \n");
	    sql.append("          ,    T1.COMPLETEREQUESTDATE AS completeRequestDate \n");
	    sql.append("          ,    T1.RECEIVECONFIRMEDDATE AS receiveConfirmedDate \n");
	    sql.append("          ,    T1.ACTIVITYTYPEDESC AS activityTypeDesc \n");
	    sql.append("          ,    T1.ACTIVITYBACKDESC AS activityBackDesc \n");

	    sql.append("FROM KETMoldChangeActivity T1 \n");
	    sql.append("    , KETMoldECADocLink T2 \n");
	    sql.append("    , KETProjectDocument T4 \n");
	    sql.append("WHERE T1.activityType = '4' \n");
	    sql.append("AND T1.idA3A8 = ? \n");
	    sql.append("AND T1.workerId = ? \n");
	    sql.append("AND T1.idA2A2 = T2.idA3B5(+) \n");
	    sql.append("AND T2.idA3A5 = T4.idA2A2(+)  \n");
	}

	sql.append("ORDER BY 1, 14 DESC, 11  \n");
	return sql.toString();
    }// end-of-getSearchMoldEcaAllSQL

    /**
     * 
     * 함수명 : getMoldECADeleteListSQL 설명 : 삭제대상 금형ECA 목록을 조회하는 SQL을 작성한다.
     * 
     * @return String : SQL
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    private String getMoldECADeleteListSQL() throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT T1.classnameA2A2||':'|| T1.idA2A2 moldEcaOid \n");
	sql.append("    , COUNT(T2.idA3B5) CNT \n");
	sql.append("FROM KETMoldChangeActivity T1 \n");
	sql.append("    ,( \n");
	sql.append("    SELECT ST2.idA3B5 \n");
	sql.append("    FROM KETMoldECAEpmDocLink ST2 \n");
	sql.append("        , KETMoldChangeActivity ST1 \n");
	sql.append("    WHERE 1 = 1 \n");
	sql.append("    AND ST2.idA3B5 = ST1.idA2A2 \n");
	sql.append("    AND ST1.idA3A8 = ? \n");
	sql.append("    UNION ALL \n");
	sql.append("    SELECT ST2.idA3B5 \n");
	sql.append("    FROM KETMoldECABomLink ST2 \n");
	sql.append("        , KETMoldChangeActivity ST1 \n");
	sql.append("    WHERE 1 = 1 \n");
	sql.append("    AND ST2.idA3B5 = ST1.idA2A2 \n");
	sql.append("    AND ST1.idA3A8 = ? \n");
	sql.append("    UNION ALL \n");
	sql.append("    SELECT ST2.idA3B5 \n");
	sql.append("    FROM KETMoldECADocLink ST2 \n");
	sql.append("        , KETMoldChangeActivity ST1 \n");
	sql.append("    WHERE 1 = 1 \n");
	sql.append("    AND ST2.idA3B5 = ST1.idA2A2 \n");
	sql.append("    AND ST1.idA3A8 = ? \n");
	sql.append("    ) T2 \n");
	sql.append("WHERE 1 = 1 \n");
	sql.append("AND T1.idA3A8 = ? \n");
	sql.append("AND T2.idA3B5 (+)= T1.idA2A2 \n");

	// ECN은 문서가 필요없기때문에 삭제대상에서 제외한다.
	sql.append("    AND T1.ACTIVITYTYPE != '4' \n");

	sql.append("HAVING COUNT(T2.idA3B5) < 1 \n");
	sql.append("GROUP BY T1.classnameA2A2||':'|| T1.idA2A2 \n");

	return sql.toString();
    }// end-of-getMoldECADeleteListSQL

    /**
     * 
     * 함수명 : getMoldPlanDeleteListSQL 설명 : 금형변경일정 삭제대상 목록을 조회하는 SQL을 작성한다.
     * 
     * @return String : SQL
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    private String getMoldPlanDeleteListSQL() throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT T1.classnameA2A2||':'|| T1.idA2A2 moldPlanOid \n");
	sql.append("FROM KETMoldChangePlan T1 \n");
	sql.append("WHERE 1 = 1 \n");
	sql.append("AND T1.idA3A4 = ? \n");
	sql.append("MINUS \n");
	sql.append("SELECT T3.classnameA2A2||':'|| T3.idA2A2 moldPlanOid \n");
	sql.append("FROM  KETMoldChangeActivity T1 \n");
	sql.append("    , KETMoldECAEpmDocLink T2 \n");
	sql.append("    , KETMoldChangePlan T3 \n");
	sql.append("WHERE 1 = 1 \n");
	sql.append("AND T1.idA3A8 = ? \n");
	sql.append("AND T2.idA3B5 = T1.idA2A2 \n");
	sql.append("AND T3.scheduleId = T2.scheduleId \n");

	return sql.toString();
    }// end-of-getMoldPlanDeleteListSQL

    /**
     * 
     * 함수명 : getMoldECAByWorkerIdSQL 설명 : 금형 ECO OID, 금형 ECO 담당자 OID로 금형ECA를 조회하는 SQL을 작성한다.
     * 
     * @return String : SQL
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    // ECO담당자로 ECA 조회
    private String getMoldECAByWorkerIdSQL() throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT T1.classnameA2A2||':'|| T1.ida2a2 ecaOid \n");
	sql.append("    , T1.* \n");
	sql.append("FROM KETMoldChangeActivity T1 \n");
	sql.append("WHERE 1 = 1 \n");
	sql.append("AND workerId = ? \n");
	sql.append("AND idA3A8 = ? \n");
	sql.append("AND activityType = '1' \n");// ECO담당자는 1:도면으로 지정되어 있기 때문에...

	return sql.toString();
    }// end-of-getMoldECAByWorkerIdSQL

    public KETMoldChangeOrderVO getStdPartList(KETMoldChangeOrderVO moldEcoVO, String moldEcoOid) throws Exception {
	ArrayList<Hashtable<String, String>> stdPartList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> stdPartHash = null;

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT                                                     \n");
	sql.append("         substr(p.partno, 0, 8)       die_no            \n");
	sql.append("        , p.partno                         part_no            \n");
	sql.append("        , m.name                      part_name        \n");
	sql.append("        , p.changetype               change_type    \n");
	sql.append("        , p.description                part_desc        \n");
	sql.append(" FROM ketmoldstdpartline p                        \n");
	sql.append("        ,  wtpartmaster m                                \n");
	sql.append("WHERE p.partno = m.wtpartnumber                \n");
	sql.append("   AND p.moldecooid = ?                            \n");
	sql.append("ORDER BY partno                                        \n");

	try {
	    conn = PlmDBUtil.getConnection();

	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, moldEcoOid);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		stdPartHash = new Hashtable<String, String>();
		stdPartHash.put("die_no", rs.getString("die_no"));
		stdPartHash.put("part_no", rs.getString("part_no"));
		stdPartHash.put("part_name", rs.getString("part_name"));
		stdPartHash.put("change_type", rs.getString("change_type"));
		stdPartHash.put("part_desc", StringUtil.checkNull(rs.getString("part_desc")));

		stdPartList.add(stdPartHash);
	    }

	    moldEcoVO.setStdPartList(stdPartList);
	} catch (Exception e) {
	    throw e;
	} finally {
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(conn);
	}

	return moldEcoVO;
    }

}
