/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : EcmComDao.java
 * 작성자 : 오승연
 * 작성일자 : 2010. 10. 18.
 */
package e3ps.ecm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.StringUtil;
import e3ps.ecm.beans.EcmSearchHelper;
import e3ps.ecm.entity.KETSearchEcoDetailVO;
import e3ps.ecm.entity.KETSearchEcoVO;
import e3ps.project.beans.MoldProjectHelper;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartUtil;

/**
 * 클래스명 : EcmComDao 설명 : 작성자 : 오승연 작성일자 : 2010. 10. 18.
 */
public class EcmComDao {

    private Connection conn;
    private PreparedStatement pstmt;
    // private LoggableStatement pstmt;
    private ResultSet rs;

    public EcmComDao(Connection conn) {
	this.conn = conn;
    }

    public ArrayList<Hashtable<String, String>> getCodeList(String codeType, String locale) throws Exception {
	ArrayList<Hashtable<String, String>> codeList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> code = null;

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT NC.CODE   AS CODE                \n");
	sql.append("      ,NCV.VALUE AS NAME                \n");
	sql.append("  FROM NUMBERCODE NC                    \n");
	sql.append("      ,NUMBERCODEVALUE NCV              \n");
	sql.append(" WHERE NC.CODETYPE = NCV.CODETYPE       \n");
	sql.append("   AND NC.CODE     = NCV.CODE           \n");
	sql.append("   AND NC.DISABLED = 0                  \n");
	sql.append("   AND NC.CODETYPE = '" + codeType + "' \n");
	sql.append("   AND NCV.LANG    = '" + locale + "'   \n");
	if("PROBLEMDIVTYPE".equals(codeType)){
	    sql.append(" AND IDA3A3 = 0         \n");
	}
	sql.append(" ORDER BY TO_NUMBER(NC.SORTING)         \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		code = new Hashtable<String, String>();
		code.put("code", rs.getString("code"));
		code.put("name", StringUtils.stripToEmpty(rs.getString("name")));

		codeList.add(code);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return codeList;
    }

    public String getCodeName(String codeType, String code) throws Exception {
	String codeName = "";

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT c.code                                 \n");
	sql.append("            , c.name                                 \n");
	sql.append("  FROM numbercode c                    \n");
	sql.append("WHERE c.codetype =?                    \n");
	sql.append("   AND c.code = ?                            \n");
	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    pstmt.setString(1, codeType);
	    pstmt.setString(2, code);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		codeName = rs.getString("name");
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return codeName;
    }

    // 외주협력업체명 조회
    public String getPartnerName(String partnerNo) throws Exception {
	String partnerName = "";
	if (partnerNo == null) {
	    return partnerName;
	}
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT partnerName \n");
	sql.append("FROM KETPartner \n");
	sql.append("WHERE partnerNo = ? \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    pstmt.setString(1, partnerNo);

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		partnerName = rs.getString("partnerName");
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return partnerName;
    }

    /**
     * 함수명 : getCarMaker 설명 : 고객사(차량Maker) 정보를 가져오는 함수( 자동차 사업부만 해당)
     * 
     * @param domesticFlag
     *            - 국내/국외 code (1000/2000)
     * @return
     * @throws Exception
     *             작성자 : 오승연 작성일자 : 2010. 11. 23.
     */
    public ArrayList<Hashtable<String, String>> getCarMaker(String domesticFlag) throws Exception {
	ArrayList<Hashtable<String, String>> codeList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> code = null;
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT n.code                                        \n");
	sql.append("           , n.name                                        \n");
	sql.append(" FROM numbercode n                            \n");
	sql.append("WHERE n.codetype = 'CUSTOMEREVENT'    \n");
	sql.append("    AND n.ida3a3 <> '0'                            \n");
	sql.append("    AND n.description = '자동차'                 \n");
	sql.append("CONNECT BY PRIOR n.ida2a2 = n.ida3a3     \n");
	sql.append(" START WITH n.code= ?                             \n");
	sql.append("ORDER SIBLINGS BY n.code                     \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, domesticFlag);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		code = new Hashtable<String, String>();
		code.put("code", rs.getString("code"));
		code.put("name", rs.getString("name"));

		codeList.add(code);
	    }

	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return codeList;

    }

    /**
     * 함수명 : getCarCategory 설명 : 고객사(차량 Maker)의 차종을 가져오는 함수
     * 
     * @param carMakerId
     *            - 고객사 Id(자동차사업부)
     * @return
     * @throws Exception
     *             작성자 : 오승연 작성일자 : 2010. 11. 23.
     */
    public ArrayList<Hashtable<String, String>> getCarCategory(String carMakerId) throws Exception {
	ArrayList<Hashtable<String, String>> codeList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> code = null;

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT o.code                                      \n");
	sql.append("          , o.name                                     \n");
	sql.append(" FROM numbercode n                         \n");
	sql.append("          , oemprojecttype o                       \n");
	sql.append("WHERE o.ida3a4 = n.ida2a2                  \n");
	sql.append("    AND o.oemtype ='CARTYPE'               \n");
	sql.append("    AND o.isdisabled = 0                       \n");
	sql.append("    AND n.code= ?                                  \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, carMakerId);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		code = new Hashtable<String, String>();
		code.put("code", rs.getString("code"));
		code.put("name", rs.getString("name"));

		codeList.add(code);
	    }

	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return codeList;
    }

    /**
     * 함수명 : getPartChangeInfo 설명 : Part No 에 대한 ECO 횟수와 최종 변경일자를 가져오는 함수
     * 
     * @param partNo
     * @return
     * @throws Exception
     *             작성자 : 오승연 작성일자 : 2010. 12. 9.
     */
    public Hashtable<String, String> getPartChangeInfo(String partNo, String startDate, String endDate) throws Exception {
	Hashtable<String, String> changeInfo = null;

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT SUM(eco_count)                                                          eco_count                                      \n");
	sql.append("         , to_char( MAX(last_update_date), 'YYYY-MM-DD' )              last_update_date                             \n");
	sql.append("FROM (                                                                                                                                 \n");
	sql.append("          SELECT MAX ( po.updatestampa2 )      last_update_date                                                        \n");
	sql.append("                   , COUNT ( po.ecoid )               eco_count                                                                 \n");
	sql.append("           FROM ketprodecopartlink plink                                                                                       \n");
	sql.append("                   , ketprodchangeorder po                                                                                      \n");
	sql.append("                   , wtpart  p                                                                                                          \n");
	sql.append("                   , wtpartmaster pm                                                                                                \n");
	sql.append("          WHERE plink.ida3a5 = p.ida2a2                                                                                       \n");
	sql.append("              AND plink.ida3b5 = po.ida2a2                                                                                     \n");
	sql.append("              AND p.ida3masterreference = pm.ida2a2                                                                        \n");
	sql.append("              AND po.statestate = 'APPROVED'                                                                               \n");
	sql.append("              AND pm.wtpartnumber = ?                                                                                       \n");
	sql.append("              AND po.updatestampa2 >= nvl( to_date( ?, 'YYYY-MM-DD' ), po.updatestampa2 )             \n");
	sql.append("              AND po.updatestampa2 <= nvl( to_date( ?, 'YYYY-MM-DD' )+0.9999, po.updatestampa2 )  \n");
	sql.append("         UNION ALL                                                                                                                  \n");
	sql.append("         SELECT MAX ( po.updatestampa2 )      last_update_date                                                        \n");
	sql.append("                  , COUNT ( po.ecoid )                   eco_count                                                                 \n");
	sql.append("          FROM ketmoldecopartlink plink                                                                                       \n");
	sql.append("                  , ketmoldchangeorder po                                                                                      \n");
	sql.append("                  , wtpart  p                                                                                                          \n");
	sql.append("                  , wtpartmaster pm                                                                                                \n");
	sql.append("          WHERE plink.ida3a5 = p.ida2a2                                                                                       \n");
	sql.append("              AND plink.ida3b5 = po.ida2a2                                                                                     \n");
	sql.append("              AND p.ida3masterreference = pm.ida2a2                                                                        \n");
	sql.append("              AND po.statestate = 'APPROVED'                                                                                \n");
	sql.append("              AND pm.wtpartnumber  = ?                                                                                      \n");
	sql.append("              AND po.updatestampa2 >= nvl( to_date( ?, 'YYYY-MM-DD' ), po.updatestampa2 )             \n");
	sql.append("              AND po.updatestampa2 <= nvl( to_date( ?, 'YYYY-MM-DD' )+0.9999, po.updatestampa2 ) )\n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    pstmt.setString(1, partNo);
	    pstmt.setString(2, startDate);
	    pstmt.setString(3, endDate);

	    pstmt.setString(4, partNo);
	    pstmt.setString(5, startDate);
	    pstmt.setString(6, endDate);

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		changeInfo = new Hashtable<String, String>();
		changeInfo.put("eco_count", StringUtil.checkNullZero(rs.getString("eco_count")));
		changeInfo.put("last_update_date", StringUtil.checkNull(rs.getString("last_update_date")));
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return changeInfo;
    }

    public boolean insertBomCoWoker(Hashtable<String, String> workerHash) throws Exception {
	boolean isSuccess = false;

	StringBuffer sql = new StringBuffer();
	sql.append("INSERT INTO ketbomecocoworker    \n");
	sql.append("                ( ecoitemcode                \n");
	sql.append("                , ecoheadernumber        \n");
	sql.append("                , coworkerid                    \n");
	sql.append("                , coworkername              \n");
	sql.append("                , coworkerdept              \n");
	sql.append("                , coworkeremail             \n");
	sql.append("                , endworkingflag )          \n");
	sql.append("      VALUES ( ?                              \n");
	sql.append("                  , ?                               \n");
	sql.append("                  , ?                               \n");
	sql.append("                  , ?                               \n");
	sql.append("                  , ?                               \n");
	sql.append("                  , ?                               \n");
	sql.append("                  , 1 )                             \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    pstmt.setString(1, workerHash.get("part_no"));
	    pstmt.setString(2, workerHash.get("eco_id"));
	    pstmt.setString(3, workerHash.get("worker_id"));
	    pstmt.setString(4, workerHash.get("worker_name"));
	    pstmt.setString(5, workerHash.get("worker_dept"));
	    pstmt.setString(6, workerHash.get("worker_email"));

	    pstmt.executeUpdate();

	    isSuccess = true;
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return isSuccess;
    }

    public boolean updateBomCoWoker(Hashtable<String, String> workerHash) throws Exception {
	boolean isSuccess = false;

	StringBuffer sql = new StringBuffer();
	sql.append("UPDATE ketbomecocoworker \n");
	sql.append("   SET coworkerid = ? \n");
	sql.append("     , coworkername = ? \n");
	sql.append("     , coworkerdept = ? \n");
	sql.append("     , coworkeremail = ?  \n");
	sql.append(" WHERE ecoheadernumber = ? \n");
	sql.append("   AND coworkerid = ? \n");
	sql.append("   AND ecoitemcode = ? \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    pstmt.setString(1, workerHash.get("worker_id"));
	    pstmt.setString(2, workerHash.get("worker_name"));
	    pstmt.setString(3, workerHash.get("worker_dept"));
	    pstmt.setString(4, workerHash.get("worker_email"));

	    pstmt.setString(5, workerHash.get("eco_id"));
	    pstmt.setString(6, workerHash.get("old_worker_id"));
	    pstmt.setString(7, workerHash.get("part_no"));

	    pstmt.executeUpdate();

	    isSuccess = true;
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return isSuccess;
    }

    public boolean deleteBomCoWorker(String ecoId, String partNo) throws Exception {
	boolean isSuccess = false;

	StringBuffer sql = new StringBuffer();
	sql.append("DELETE FROM ketbomecocoworker           \n");
	sql.append("          WHERE ecoheadernumber = ?        \n");
	sql.append("             AND ecoitemcode = ?               \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    pstmt.setString(1, ecoId);
	    pstmt.setString(2, partNo);

	    pstmt.executeUpdate();

	    isSuccess = true;
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return isSuccess;
    }

    public boolean deleteAllBomCoWorker(String ecoId) throws Exception {
	boolean isSuccess = false;

	StringBuffer sql = new StringBuffer();
	sql.append("DELETE FROM ketbomecocoworker           \n");
	sql.append("          WHERE ecoheadernumber = ?        \n");
	sql.append("             AND endworkingflag = '1'          \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    pstmt.setString(1, ecoId);

	    pstmt.executeUpdate();

	    isSuccess = true;
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return isSuccess;
    }

    public boolean deleteBomComponent(String ecoId, String partNo) throws Exception {
	boolean isSuccess = false;

	StringBuffer sql = new StringBuffer();
	sql.append("DELETE FROM ketbomecocomponent       \n");
	sql.append("          WHERE ecoheadernumber = ?        \n");
	sql.append("             AND ecoitemcode = ?               \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    pstmt.setString(1, ecoId);
	    pstmt.setString(2, partNo);

	    pstmt.executeUpdate();

	    isSuccess = true;
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return isSuccess;
    }

    public boolean deleteAllBomComponent(String ecoId) throws Exception {
	boolean isSuccess = false;

	StringBuffer sql = new StringBuffer();
	sql.append("DELETE FROM ketbomecocomponent          \n");
	sql.append("          WHERE ecoheadernumber = ?        \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    pstmt.setString(1, ecoId);

	    pstmt.executeUpdate();

	    isSuccess = true;
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return isSuccess;
    }

    public boolean deleteBomTempComponent(String ecoId, String partNo) throws Exception {
	boolean isSuccess = false;

	StringBuffer sql = new StringBuffer();
	sql.append("DELETE FROM ketbomecotempcomponent         \n");
	sql.append("          WHERE ecoheadernumber = ?                \n");
	sql.append("             AND ecoitemcode = ?                       \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    pstmt.setString(1, ecoId);
	    pstmt.setString(2, partNo);

	    pstmt.executeUpdate();

	    isSuccess = true;
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return isSuccess;
    }

    public boolean deleteAllBomTempComponent(String ecoId) throws Exception {
	boolean isSuccess = false;

	StringBuffer sql = new StringBuffer();
	sql.append("DELETE FROM ketbomecotempcomponent       \n");
	sql.append("          WHERE ecoheadernumber = ?            \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    pstmt.setString(1, ecoId);

	    pstmt.executeUpdate();

	    isSuccess = true;
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return isSuccess;
    }

    public KETSearchEcoVO searchPart(KETSearchEcoVO ketSearchEcoVO) throws Exception {
	ArrayList<KETSearchEcoDetailVO> ketSearchEcoDetailVOList = new ArrayList<KETSearchEcoDetailVO>();// ECO 검색상세 List
	KETSearchEcoDetailVO ketSearchEcoDetailVO = null;

	String partType = ketSearchEcoVO.getPartType();
	String partNumber = ketSearchEcoVO.getPartNumber();
	String partName = ketSearchEcoVO.getPartName();
	String epmNumber = ketSearchEcoVO.getEpmNumber();
	String epmName = ketSearchEcoVO.getEpmName();

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT m.wtpartnumber part_number \n");
	sql.append("     , m.name part_name \n");
	sql.append("     , p.versionsortida2versioninfo ver_iteration \n");
	sql.append("     , p.versionida2versioninfo version \n");
	sql.append("     , p.iterationida2iterationinfo iteration \n");
	sql.append("     , p.statestate state \n");
	sql.append("     , ( SELECT ph.name \n");
	sql.append("           FROM phasetemplate ph \n");
	sql.append("              , phaselink pl \n");
	sql.append("          WHERE pl.ida3a5 = p.ida3a2state \n");
	sql.append("            AND pl.ida3b5 = ph.ida2a2 \n");
	sql.append("            AND ph.phasestate = p.statestate ) status_name \n");
	sql.append("     , p.classnamea2a2 || ':' || p.ida2a2 oid \n");
	sql.append("     , p.classnamekeymasterreference || ':' || p.ida3masterreference master_oid \n");
	sql.append("     , p.PTC_STR_3TYPEINFOWTPART part_type \n");
	/*
	 * sql.append(
	 * "     , DECODE( x.value, 'D', 'Die No', 'M', '금형부품', 'F', '제품', 'H', '반제품', 'R', '원자재', 'K', '포장재', 'S', '스크랩', 'W', '상품','제품' ) part_type_name \n"
	 * );
	 */
	sql.append("     , decode(p."
	        + PartSpecEnum.SpPartType.getColumnName()
	        + ", 'D', 'Die No', 'M', '금형부품', 'F', '제품', 'H', '반제품', 'R', '원자재', 'K', '포장재', 'S', '스크랩', 'W', '상품','제품') as part_type_name \n");
	sql.append("     , p." + PartSpecEnum.SpPartType.getColumnName() + " as typeCode \n");

	sql.append("  FROM WTPart p \n");
	sql.append("     , WTPartMaster m \n");

	sql.append("     ,  ( SELECT m2.IDA2A2, MAX(BRANCHIDITERATIONINFO) BRANCHIDITERATIONINFO \n");
	sql.append("            FROM WTPart p2, WTPartMaster m2 \n");
	sql.append("           WHERE 1=1 \n");

	if (partNumber.length() > 0) {
	    sql.append("        AND " + KETQueryUtil.getSqlQueryForMultiSearch("m2.wtpartnumber", partNumber, true) + " \n");
	}

	if (partName.length() > 0) {
	    sql.append("        AND " + KETQueryUtil.getSqlQueryForMultiSearch("m2.name", partName, true) + " \n");
	}

	if (partType.length() > 0 && !partType.equals("A")) {
	    if ("P".equals(partType)) {
		sql.append("     AND p2." + PartSpecEnum.SpPartType.getColumnName() + " not in ('D', 'M', 'O') \n");
	    } else {
		sql.append("     AND p2." + PartSpecEnum.SpPartType.getColumnName() + " = ? \n");
	    }
	}
	// 기타도 검색되도록 수정함.
	// else {
	// sql.append("         AND p2." + PartSpecEnum.SpPartType.getColumnName() + " != 'O' \n");
	// }

	// 도면번호, 도면명 검색
	if (epmNumber.length() > 0 || epmName.length() > 0) {
	    sql.append("         AND m2.IDA2A2 IN ( \n");

	    sql.append("                           SELECT L.IDA3B5 \n");
	    sql.append("                             FROM EPMLINK L, EPMDOCUMENTMASTER EM \n");
	    sql.append("                            WHERE L.IDA3A5 = EM.IDA2A2 \n");
	    sql.append("                              AND L.REQUIRED = 1 \n");

	    if (epmNumber.length() > 0) {
		sql.append("                         AND " + KETQueryUtil.getSqlQueryForMultiSearch("EM.DOCUMENTNUMBER", epmNumber, true)
		        + " \n");

	    }
	    if (epmName.length() > 0) {
		sql.append("                         AND " + KETQueryUtil.getSqlQueryForMultiSearch("EM.NAME", epmName, true) + " \n");
	    }

	    sql.append("                          ) \n");
	}

	sql.append("             AND p2.ida3masterreference = m2.ida2a2 \n");
	sql.append("             AND p2.statecheckoutinfo != 'wrk' \n");
	sql.append("             AND p2.latestiterationinfo = 1 \n");
	sql.append("           GROUP BY m2.ida2a2 ) x \n");

	sql.append(" WHERE  M.IDA2A2 = P.IDA3MASTERREFERENCE				    \n");
    sql.append("   AND P.LATESTITERATIONINFO = 1							\n");
    sql.append("   AND P.STATECHECKOUTINFO NOT IN ('wrk')					\n");
    sql.append("   AND M.IDA2A2 = x.IDA2A2									\n");
    sql.append("   AND P.BRANCHIDITERATIONINFO = x.BRANCHIDITERATIONINFO	\n");
    if (partNumber.length() > 0) {
	    sql.append("        AND " + KETQueryUtil.getSqlQueryForMultiSearch("m.wtpartnumber", partNumber, true) + " \n");
	}

	// 로그인 사용자가 KETS 일 경우
	if (CommonUtil.isKETSUser()) {
	    sql.append(CommonUtil.ketsUserListWhereStr("p.ida3d2iterationinfo"));
	}

	sql.append(" ORDER BY m.wtpartnumber \n");

	try {
	    int pstmtCnt = 1;
	    int totalCnt = 0;
	    pstmt = conn.prepareStatement(sql.toString());

	    if (partType.length() > 0 && !partType.equals("A")) {
		if ("P".equals(partType)) {
		    // Do nothing..!!
		} else {
		    pstmt.setString(pstmtCnt, partType);
		    pstmtCnt++;
		}
	    }

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		ketSearchEcoDetailVO = new KETSearchEcoDetailVO();
		ketSearchEcoDetailVO.setOid(rs.getString("oid"));
		ketSearchEcoDetailVO.setPartNumber(rs.getString("part_number"));
		ketSearchEcoDetailVO.setPartName(rs.getString("part_name"));
		ketSearchEcoDetailVO.setPartVersion(rs.getString("version"));
		ketSearchEcoDetailVO.setPartType(rs.getString("part_type"));
		ketSearchEcoDetailVO.setPartTypeName(rs.getString("part_type_name"));
		ketSearchEcoDetailVO.setSancStateFlag(rs.getString("status_name"));

		// Die No
		String die_no = "";
		String dieName = "";
		String dieCnt = "";
		// 제품
		if (PartUtil.isProductType(rs.getString("typeCode"))) {
		    die_no = StringUtil.checkNull(MoldProjectHelper.getDieNo(ketSearchEcoDetailVO.getPartNumber()));
		    dieCnt = MoldProjectHelper.getDieNoCnt(ketSearchEcoDetailVO.getPartNumber());
		}
		// 금형
		else if ("D".equals(rs.getString("typeCode"))) {
		    die_no = StringUtil.checkNull(EcmSearchHelper.manager.getRelatedPartNo(ketSearchEcoDetailVO.getPartNumber()));
		}
		dieName = StringUtil.checkNull(EcmSearchHelper.manager.getRelatedPartName(die_no));

		ketSearchEcoDetailVO.setDieNo(die_no);
		ketSearchEcoDetailVO.setDieName(dieName);
		ketSearchEcoDetailVO.setDieCnt(dieCnt);

		ketSearchEcoDetailVOList.add(ketSearchEcoDetailVO);

		totalCnt++;
	    }

	    ketSearchEcoVO.setResultRows(totalCnt);
	    ketSearchEcoVO.setTotalCount(totalCnt);
	    ketSearchEcoVO.setKetSearchEcoDetailVOList(ketSearchEcoDetailVOList);
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}
	return ketSearchEcoVO;
    }

    public String getLastestPart(String partNumber) throws Exception {
	String partOid = "";

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT p.classnamea2a2 || ':' || p.ida2a2                                            oid                                            \n");
	sql.append("FROM WTPart p                                                                                                                            \n");
	sql.append("        , WTPartMaster m                                                                                                                    \n");
	sql.append("        ,  ( SELECT MAX(p2.versionsortida2versioninfo) objver, MAX(p2.ida2a2) objid, p2."
	        + PartSpecEnum.SpPartType.getColumnName() + " as value                        \n");
	sql.append("             FROM WTPart p2, WTPartMaster m2                                                                 \n");
	sql.append("            WHERE 1=1                                                                            \n");
	sql.append("               AND m2.wtpartnumber = ?                                                                                                \n");
	sql.append("               AND p2.ida3masterreference = m2.ida2a2                                                                            \n");
	sql.append("               AND p2.statecheckoutinfo != 'wrk'                                                                                    \n");
	sql.append("               AND p2.latestiterationinfo = 1                                                                                            \n");
	sql.append("           GROUP BY m2.wtpartnumber, p2." + PartSpecEnum.SpPartType.getColumnName()
	        + " ) x                                                                                \n");
	sql.append("WHERE p.ida3masterreference = m.ida2a2                                                                                            \n");
	sql.append("    AND p.ida2a2 = x.objid                                                                                                                \n");
	sql.append("ORDER BY m.wtpartnumber                                                                                                                \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, partNumber);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		partOid = rs.getString("oid");
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return partOid;
    }

    public String getChangeReasonName(String chgReasonCode, String codeType) throws Exception {
	String chgReasonName = "";
	StringTokenizer st = new StringTokenizer(chgReasonCode, "|");
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT n.name            name                            \n");
	sql.append("    FROM numbercode n                                \n");
	sql.append("WHERE n.codetype = ?                                 \n");
	sql.append("    AND n.code = ?");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    int tokenCnt = 0;
	    while (st.hasMoreTokens()) {
		pstmt.setString(1, codeType);
		pstmt.setString(2, st.nextToken());

		tokenCnt++;

		rs = pstmt.executeQuery();

		if (rs.next()) {
		    chgReasonName += rs.getString("name");
		    if (!rs.getString("name").equals("기타")) {
			chgReasonName += "/";
		    }
		}
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return chgReasonName;
    }

    public int getCntBomEcoComponent(String ecoId) throws Exception {
	int compCnt = 0;

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT count(c.ecoheadernumber) \n");
	/*
	 * sql.append("  FROM ketbomecoheader h \n");
	 */
	sql.append("  FROM \n");
	sql.append("       ( \n");
	sql.append("        SELECT ida2a2, ecoheadernumber, ecoitemcode \n");
	sql.append("	  FROM ketbomecoheader \n");
	sql.append("     UNION ALL \n");
	sql.append("        SELECT ida2a2, ecoheadernumber, ecoitemcode \n");
	sql.append("	  FROM ketbomheader \n");
	sql.append("       ) h \n");

	sql.append("     , ketbomecocomponent c \n");
	sql.append(" WHERE h.ecoheadernumber = c.ecoheadernumber \n");
	sql.append("   AND h.ecoitemcode = c.ecoitemcode \n");
	sql.append("   AND h.ecoheadernumber = ? \n");
	sql.append("   AND c.ecocode IS not NULL \n");
	sql.append(" GROUP BY c.ecoheadernumber \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoId);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		compCnt = rs.getInt(1);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return compCnt;
    }

    public int getNotCompleteBomChangeCnt(String ecoId, String itemCode, String userId) throws Exception {
	int notChangedCnt = 0;

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT COUNT(*)                        \n");
	sql.append(" FROM ketbomecocoworker w        \n");
	sql.append("WHERE w.ecoheadernumber = ?    \n");
	sql.append("  AND w.ecoitemcode =?            \n");
	// sql.append( "  AND w.coworkerid =?                \n" );
	sql.append("  AND w.endworkingflag <> '4'    \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoId);
	    pstmt.setString(2, itemCode);
	    // pstmt.setString( 3, userId );

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		notChangedCnt = rs.getInt(1);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return notChangedCnt;
    }

    public boolean updateBomCoWorker(String ecoId, String ecoItemCode) throws Exception {
	boolean isUpdated = false;

	StringBuffer sql = new StringBuffer();
	sql.append("UPDATE ketbomecocoworker        \n");
	sql.append("     SET endworkingflag = '1'        \n");
	sql.append("  WHERE ecoheadernumber = ?      \n");
	sql.append("     AND ecoitemcode = ?             \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoId);
	    pstmt.setString(2, ecoItemCode);

	    pstmt.executeUpdate();

	    isUpdated = true;
	} catch (Exception e) {
	    throw e;
	} finally {
	    statementRsClose();
	}

	return isUpdated;
    }

    /**
     * 함수명 : statementRsClose 설명 :
     * 
     * @throws Exception
     *             작성자 : 오승연 작성일자 : 2010. 10. 14.
     */
    public void statementRsClose() throws Exception {
	if (rs != null)
	    rs.close();

	if (pstmt != null)
	    pstmt.close();
    }

}
