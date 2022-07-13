package e3ps.ecm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import wt.lifecycle.LifeCycleManaged;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import e3ps.ecm.beans.EcmUtil;
import e3ps.ecm.entity.KETSearchEcoDetailVO;
import e3ps.ecm.entity.KETSearchEcoVO;
import ext.ket.shared.log.Kogger;

public class SearchMoldChangeOrderDao {
    private static final long serialVersionUID = -1432672658288760111L;
    private static String     dateFormatString = EcmUtil.getDateFormatString();

    /**
     * 
     * 함수명 : searchMoldPlanPopupList 설명 : 양산 금형변경 일정 팝업 목록을 검색한다.
     * 
     * @param ketSearchEcoVO
     *            : 금형 ECO 검색 VO
     * @return ketSearchEcoVO : 금형 ECO 검색 VO
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    public KETSearchEcoVO searchMoldPlanPopupList(KETSearchEcoVO ketSearchEcoVO) throws Exception {
	ArrayList<KETSearchEcoDetailVO> ketSearchEcoDetailVOList = new ArrayList<KETSearchEcoDetailVO>();//ECO 검색상세 List
	KETSearchEcoDetailVO ketSearchEcoDetailVO = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	try {
	    con = PlmDBUtil.getConnection();
	    sql = getSearchMoldPlanPopupListSQL(ketSearchEcoVO);
	    pstmt = con.prepareStatement(sql);
	    int resultRows = 0;
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		resultRows++;
		ketSearchEcoDetailVO = new KETSearchEcoDetailVO();
		ketSearchEcoDetailVO.setOid(rs.getString("oid"));
		ketSearchEcoDetailVO.setEcoId(rs.getString("scheduleId"));
		ketSearchEcoDetailVO.setDieNo(rs.getString("dieNo"));
		ketSearchEcoDetailVO.setPartNumber(StringUtil.escapeHtml(rs.getString("partNo")));
		ketSearchEcoDetailVO.setPartName(StringUtil.escapeHtml(rs.getString("partName")));
		ketSearchEcoDetailVO.setCreatorName(rs.getString("creatorName"));
		ketSearchEcoDetailVO.setCreateDate(rs.getString("createDate"));
		ketSearchEcoDetailVO.setPlanDate(rs.getString("planDate"));
		ketSearchEcoDetailVOList.add(ketSearchEcoDetailVO);
	    }
	    ketSearchEcoVO.setResultRows(resultRows);
	    ketSearchEcoVO.setTotalCount(resultRows);
	} catch (Exception e) {
	    throw e;
	} finally {
	    if (rs != null) rs.close();
	    if (pstmt != null) pstmt.close();
	    PlmDBUtil.close(con);
	}
	ketSearchEcoVO.setKetSearchEcoDetailVOList(ketSearchEcoDetailVOList);
	return ketSearchEcoVO;
    }

    /**
     * 
     * 함수명 : searchPartPopupListOld 설명 : 부품 팝업 목록을 검색한다.(미사용)
     * 
     * @param ketSearchEcoVO
     *            : 금형 ECO 검색 VO
     * @return ketSearchEcoVO : 금형 ECO 검색 VO
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    public KETSearchEcoVO searchPartPopupListOld(KETSearchEcoVO ketSearchEcoVO) throws Exception {
	ArrayList<KETSearchEcoDetailVO> ketSearchEcoDetailVOList = new ArrayList<KETSearchEcoDetailVO>();//ECO 검색상세 List
	KETSearchEcoDetailVO ketSearchEcoDetailVO = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	try {
	    con = PlmDBUtil.getConnection();
	    sql = getSearchPartPopupListSQL(ketSearchEcoVO);
	    pstmt = con.prepareStatement(sql);
	    int resultRows = 0;
	    rs = pstmt.executeQuery();
	    LifeCycleManaged partLC = null;
	    while (rs.next()) {
		resultRows++;
		ketSearchEcoDetailVO = new KETSearchEcoDetailVO();
		ketSearchEcoDetailVO.setOid(rs.getString("oid"));
		ketSearchEcoDetailVO.setPartNumber(StringUtil.unescapeHtml(rs.getString("partNumber")));
		ketSearchEcoDetailVO.setPartName(StringUtil.unescapeHtml(rs.getString("partName")));
		ketSearchEcoDetailVO.setPartVersion(rs.getString("partVersion"));
		partLC = (wt.lifecycle.LifeCycleManaged) CommonUtil.getObject(ketSearchEcoDetailVO.getOid());
		if (partLC != null) {
		    ketSearchEcoDetailVO.setSancStateFlag(partLC.getLifeCycleState().getDisplay());
		}
		ketSearchEcoDetailVOList.add(ketSearchEcoDetailVO);
	    }
	    ketSearchEcoVO.setResultRows(resultRows);
	    ketSearchEcoVO.setTotalCount(resultRows);
	    ketSearchEcoVO.setKetSearchEcoDetailVOList(ketSearchEcoDetailVOList);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    if (rs != null) rs.close();
	    if (pstmt != null) pstmt.close();
	    PlmDBUtil.close(con);
	}
	return ketSearchEcoVO;
    }

    /**
     * 
     * 함수명 : searchPartPopupList 설명 : 활동추가-부품 팝업 목록을 검색한다.
     * 
     * @param ketSearchEcoVO
     *            : 금형 ECO 검색 VO
     * @return ketSearchEcoVO : 금형 ECO 검색 VO
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    @SuppressWarnings("unchecked")
    public KETSearchEcoVO searchPartPopupList(KETSearchEcoVO ketSearchEcoVO) throws Exception {
	//		ArrayList<KETSearchEcoDetailVO> ketSearchEcoDetailVOList = new ArrayList<KETSearchEcoDetailVO>();//ECO 검색상세 List
	//		KETSearchEcoDetailVO ketSearchEcoDetailVO = null;
	Connection conn = null;
	try {

	    conn = PlmDBUtil.getConnection();
	    EcmComDao comDao = new EcmComDao(conn);
	    Kogger.debug(getClass(), "Part Type = " + ketSearchEcoVO.getPartType());
	    ketSearchEcoVO = comDao.searchPart(ketSearchEcoVO);
	    Kogger.debug(getClass(), "Row Count : ==> " + ketSearchEcoVO.getResultRows());
	    //			SQLFunction upper = null;
	    //			ColumnExpression ce = null;
	    //			ClassAttribute ca = null;
	    //	        QuerySpec qs = new QuerySpec(WTPart.class);
	    //	        qs.appendWhere(VersionControlHelper.getSearchCondition(WTPart.class, true), new int[] { 0 });

	    //	        String type = ketSearchEcoVO.getPartType();
	    //	        String number = ketSearchEcoVO.getPartNumber();
	    //	        String name = ketSearchEcoVO.getPartName();
	    // 부품유형 검색조건 추가 (부품유형이 전체가 선택되지 않은 경우에만 추가함)
	    //	        if (!type.equals("") && !type.equals("A")) {
	    //				@SuppressWarnings("rawtypes")
	    //				Hashtable hashSearchValue = new Hashtable();
	    //				hashSearchValue.put("PartType",type);
	    //		        IBAUtil.appendIBAWhere(qs, WTPart.class, 0, hashSearchValue, false);
	    //	        }
	    //
	    //	        // 부품번호 검색조건
	    //	        if (!number.equals("")){
	    //
	    //	        	qs.appendAnd();
	    //
	    //	        	ca = new ClassAttribute(WTPart.class, WTPart.NUMBER);
	    //	        	upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
	    //
	    //	        	if (number.indexOf("*") >= 0) {
	    //	        		ce = ConstantExpression.newExpression((number.trim().toUpperCase()).replace("*", "%"));
	    //	        		qs.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce), new int[] { 0 });
	    //	        	}else {
	    //	        		ce = ConstantExpression.newExpression(number.trim().toUpperCase());
	    //		        	qs.appendWhere(new SearchCondition(upper,  SearchCondition.EQUAL, ce), new int[] { 0 });
	    //		        }
	    //	        }
	    //
	    //	        // 부품명 검색조건
	    //	        if (!name.equals("")){
	    //	        	qs.appendAnd();
	    //
	    //	        	ca = new ClassAttribute(WTPart.class, WTPart.NAME);
	    //	        	upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
	    //
	    //	        	if (name.indexOf("*") >= 0) {
	    //	        		ce = ConstantExpression.newExpression((name.trim().toUpperCase()).replace("*", "%"));
	    //	        		qs.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce), new int[] { 0 });
	    //	        	} else {
	    //	        		ce = ConstantExpression.newExpression(name.trim().toUpperCase());
	    //		        	qs.appendWhere(new SearchCondition(upper,  SearchCondition.EQUAL, ce), new int[] { 0 });
	    //	        	}
	    //	        }
	    //
	    //			// 최신버전 조건 쿼리
	    //			SearchUtil.addLastVersionCondition(qs, WTPart.class, 0);
	    //			EcmUtil.logging(qs.toString());
	    //
	    //			@SuppressWarnings("deprecation")
	    //			QueryResult result = PersistenceHelper.manager.find(qs);
	    //        	int resultRows = 0;

	    //			Object obj[] = null;
	    //			Object objA = null;
	    //			String str_part_type = "";
	    //			String strDisplayType[] = {"제품","Die No", "금형부품"};
	    //			String p_oid = "";
	    //			String part_type = "";
	    //			String part_number = "";
	    //			String part_name = "";
	    //			String part_ver = "";
	    //			String part_status = "";
	    //
	    //			while(result.hasMoreElements()) {
	    //				WTPart wt = null;
	    //				if (!type.equals("") && !type.equals("A")) {
	    //					obj = (Object[])result.nextElement();
	    //					if(obj[0] instanceof String) {
	    //						wt = (WTPart)CommonUtil.getObject((String)obj[0]);
	    //					}else {
	    //						wt = (WTPart)obj[0];
	    //					}
	    //				}else {
	    //					objA = (Object)result.nextElement();
	    //					if(objA instanceof String) {
	    //						wt = (WTPart)CommonUtil.getObject((String)objA);
	    //					}else {
	    //						wt = (WTPart)objA;
	    //					}
	    //				}
	    //
	    //				// 정보 추출하기
	    //				p_oid = KETObjectUtil.getOidString(wt);
	    //				part_type = PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpPartType);
	    //
	    //				if (part_type != null) {
	    //					if (part_type.equals("P")) {
	    //						str_part_type = strDisplayType[0];
	    //					}else if (part_type.equals("D")) {
	    //						str_part_type = strDisplayType[1];
	    //					}else if (part_type.equals("M")) {
	    //						str_part_type = strDisplayType[2];
	    //					}
	    //				} else {
	    //					part_type = "";
	    //					str_part_type = "";
	    //				}
	    //				part_number = wt.getNumber();
	    //				part_name = wt.getName();
	    //				part_ver = KETObjectUtil.getVersion(wt);
	    //				part_status = wt.getLifeCycleState().getDisplay();
	    //
	    //				resultRows++;
	    //        		ketSearchEcoDetailVO = new KETSearchEcoDetailVO();
	    //        		ketSearchEcoDetailVO.setOid(p_oid);
	    //        		ketSearchEcoDetailVO.setPartNumber(StringUtil.unescapeHtml(part_number));
	    //        		ketSearchEcoDetailVO.setPartName(StringUtil.unescapeHtml(part_name));
	    //        		ketSearchEcoDetailVO.setPartVersion(part_ver);
	    //        		ketSearchEcoDetailVO.setPartType(part_type);
	    //        		ketSearchEcoDetailVO.setPartTypeName(str_part_type);
	    //        		ketSearchEcoDetailVO.setSancStateFlag(part_status);
	    //        		ketSearchEcoDetailVOList.add(ketSearchEcoDetailVO);
	    //			}
	    //        	ketSearchEcoVO.setResultRows(resultRows);
	    //        	ketSearchEcoVO.setTotalCount(resultRows);
	    //        	ketSearchEcoVO.setKetSearchEcoDetailVOList(ketSearchEcoDetailVOList);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    PlmDBUtil.close(conn);
	}
	return ketSearchEcoVO;
    }

    /**
     * 
     * 함수명 : searchEpmPopupList 설명 : 활동추가-도면 팝업 목록을 검색한다.
     * 
     * @param ketSearchEcoVO
     *            : 금형 ECO 검색 VO
     * @return ketSearchEcoVO : 금형 ECO 검색 VO
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    public KETSearchEcoVO searchEpmPopupList(KETSearchEcoVO ketSearchEcoVO) throws Exception {
	ArrayList<KETSearchEcoDetailVO> ketSearchEcoDetailVOList = new ArrayList<KETSearchEcoDetailVO>();//ECO 검색상세 List
	KETSearchEcoDetailVO ketSearchEcoDetailVO = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	try {
	    con = PlmDBUtil.getConnection();
	    sql = getSearcEpmPopupListSQL(ketSearchEcoVO);
	    pstmt = con.prepareStatement(sql);
	    int resultRows = 0;
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		resultRows++;
		ketSearchEcoDetailVO = new KETSearchEcoDetailVO();
		ketSearchEcoDetailVO.setOid(rs.getString("oid"));
		ketSearchEcoDetailVO.setPartNumber(StringUtil.unescapeHtml(rs.getString("docNumber")));
		ketSearchEcoDetailVO.setPartName(StringUtil.unescapeHtml(rs.getString("docName")));
		ketSearchEcoDetailVO.setPartVersion(rs.getString("docVer"));
		ketSearchEcoDetailVO.setEpmDocCls(rs.getString("docCls"));
		ketSearchEcoDetailVO.setCreatorName(rs.getString("creatorName"));
		ketSearchEcoDetailVO.setCreateDate(rs.getString("createDate"));
		ketSearchEcoDetailVOList.add(ketSearchEcoDetailVO);
	    }
	    ketSearchEcoVO.setResultRows(resultRows);
	    ketSearchEcoVO.setTotalCount(resultRows);
	    ketSearchEcoVO.setKetSearchEcoDetailVOList(ketSearchEcoDetailVOList);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    if (rs != null) rs.close();
	    if (pstmt != null) pstmt.close();
	    PlmDBUtil.close(con);
	}
	return ketSearchEcoVO;
    }

    /**
     * 
     * 함수명 : getSearchMoldPlanPopupListSQL 설명 : 양산 금형변경 일정 팝업 목록을 검색하는 SQL을 작성한다.
     * 
     * @param ketSearchEcoVO
     *            : 금형 ECO 검색 VO
     * @return String : SQL
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    private String getSearchMoldPlanPopupListSQL(KETSearchEcoVO ketSearchEcoVO) throws Exception {

	String ida2a2 = StringUtil.checkNull(ketSearchEcoVO.getEcoOid().substring(ketSearchEcoVO.getEcoOid().indexOf(":") + 1, ketSearchEcoVO.getEcoOid().length()));
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT T1.classnameA2A2||':'|| T1.ida2a2 oid\n");
	sql.append("	, T1.scheduleId \n");
	sql.append("	, T1.dieNo \n");
	sql.append("	, T1.partNo \n");
	sql.append("	, T2.name partName \n");
	sql.append("	, (SELECT ST2.name \n");
	sql.append("	FROM WTUser ST1 \n");
	sql.append("		, people ST2 \n");
	sql.append("	WHERE ST1.idA2A2 = T1.idA3Owner \n");
	sql.append("	AND ST2.idA3B4 = ST1.idA2A2) creatorName \n");
	sql.append(" , TO_CHAR(T1.createStampA2, '" + dateFormatString + "') createDate \n");
	if (ketSearchEcoVO.getEcoOid().indexOf("KETProdChangeOrder") > -1 && ida2a2.length() > 0) {
	    sql.append(", TO_CHAR(T1.proddwgplandate, '" + dateFormatString + "') planDate \n");
	}
	else if (ketSearchEcoVO.getEcoOid().indexOf("KETMoldChangeOrder") > -1 && ida2a2.length() > 0) {
	    sql.append(", TO_CHAR(T1.moldchangeplandate, '" + dateFormatString + "') planDate \n");
	}
	else {
	    sql.append(", TO_CHAR(T1.moldchangeplandate, '" + dateFormatString + "') planDate \n");
	}
	sql.append("FROM KETMoldChangePlan T1 \n");
	sql.append("	, WTPartMaster T2 \n");
	sql.append("WHERE 1 = 1 \n");
	sql.append("AND T2.WTPartNumber = T1.dieNo \n");
	sql.append("AND T1.SCHEDULESTATUS <> 'C'   \n");
	if (ketSearchEcoVO.getEcoOid().indexOf("KETProdChangeOrder") > -1 && ida2a2.length() > 0) {
	    sql.append("AND ( t1.ida3b4 = " + ida2a2 + " OR t1.classnamekeyb4 IS null )    \n");
	    sql.append("AND t1.proddwgplandate is not null    									  \n");
	    sql.append("AND t1.proddwgactualdate is null		    								  \n");
	}
	else if (ketSearchEcoVO.getEcoOid().indexOf("KETMoldChangeOrder") > -1 && ida2a2.length() > 0) {
	    sql.append("AND ( t1.ida3a4 = " + ida2a2 + " OR t1.classnamekeya4 IS null )    \n");
	    sql.append("AND t1.moldchangeplandate is not null 								 \n");
	    sql.append("AND t1.moldchangeactualdate is null 								 	 \n");
	}
	else if (ketSearchEcoVO.getEcoOid().indexOf("KETProdChangeOrder") > -1 && ida2a2.length() == 0) {
	    sql.append("AND t1.classnamekeyb4 is null    \n");
	    sql.append("AND t1.proddwgplandate is not null    \n");
	    sql.append("AND t1.proddwgactualdate is null    	   \n");
	}
	else if (ketSearchEcoVO.getEcoOid().indexOf("KETMoldChangeOrder") > -1 && ida2a2.length() == 0) {
	    sql.append("AND t1.classnamekeya4 IS null    \n");
	    sql.append("AND t1.moldchangeplandate is not null 								 \n");
	    sql.append("AND t1.moldchangeactualdate is null 									 \n");
	}
	if (!"".equals(ketSearchEcoVO.getDieNo())) {
	    sql.append("AND T1.dieNo like '" + KETStringUtil.getLikeString(ketSearchEcoVO.getDieNo()) + "' \n");
	}
	if (!"".equals(ketSearchEcoVO.getPartNumber())) {
	    sql.append("AND T1.partNo like '" + KETStringUtil.getLikeString(ketSearchEcoVO.getPartNumber()) + "' \n");
	}


	// 로그인 사용자가 KETS 일 경우
	if (CommonUtil.isKETSUser()) {
	    sql.append(CommonUtil.ketsUserListWhereStr("T1.idA3owner"));
	}


	EcmUtil.logging(sql.toString());
	return sql.toString();
    }

    /**
     * 
     * 함수명 : getSearchPartPopupListSQL 설명 : 활동추가-부품 팝업 목록을 검색하는 SQL을 작성한다.
     * 
     * @param ketSearchEcoVO
     *            : 금형 ECO 검색 VO
     * @return String : SQL
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    private String getSearchPartPopupListSQL(KETSearchEcoVO ketSearchEcoVO) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT T3.WTPartNumber partNumber \n");
	sql.append("	, T3.name partName \n");
	sql.append("	, T2.versionIdA2versionInfo partVersion \n");
	sql.append("	, T2.classnameA2A2||':'||T2.ida2a2 oid \n");
	//		sql.append("	, (SELECT ph.name \n");
	//		sql.append("	FROM phasetemplate ph, phaselink pl \n");
	//		sql.append("	WHERE 1 = 1 \n");
	//		sql.append("	AND pl.idA3A5 = T2.idA3A2State \n");
	//		sql.append("	AND pl.idA3B5 = ph.idA2A2 \n");
	//		sql.append("	AND ph.phaseState = T2.stateState) stateStateName \n");
	sql.append("FROM WTPart T2 \n");
	sql.append("	, WTPartMaster T3 \n");
	sql.append("WHERE 1 = 1 \n");
	sql.append("AND T2.idA3masterReference = T3.ida2a2 \n");
	//		sql.append("AND T2.ida2a2 IN ( \n" );
	//		sql.append("	SELECT MAX(ST2.ida2a2) ida2a2 \n" );
	//		sql.append("		FROM WTPart ST2 \n" );
	//		sql.append("			, WTPartMaster ST3 \n" );
	//		sql.append("		WHERE 1 = 1 \n" );
	//		sql.append("	AND ST2.idA3masterReference = ST3.ida2a2 \n" );
	//		sql.append("	AND ST2.latestiterationinfo = 1 \n" );
	//		sql.append("	GROUP BY ST3.WTPartNumber \n" );
	//		sql.append(") \n" );
	if (!"".equals(ketSearchEcoVO.getPartNumber())) {
	    sql.append("AND T3.WTPartNumber = '" + ketSearchEcoVO.getPartNumber() + "' \n");
	}
	if (!"".equals(StringUtil.checkNull(ketSearchEcoVO.getPartName()))) {
	    sql.append("AND T3.name LIKE '" + StringUtil.getLikeQueryString(ketSearchEcoVO.getPartName().toUpperCase()) + "'	\n");
	}
	sql.append("ORDER BY 1 ASC \n");
	EcmUtil.logging("\n\nSearchMoldChangeOrderDao.getSearchPartPopupListSQL:\n" + sql.toString());
	return sql.toString();
    }//end-of-getSearchPartPopupListSQL

    /**
     * 
     * 함수명 : getSearcEpmPopupListSQL 설명 : 활동추가-도면 팝업 목록을 검색하는 SQL을 작성한다.
     * 
     * @param ketSearchEcoVO
     *            : 금형 ECO 검색 VO
     * @return String : SQL
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    private String getSearcEpmPopupListSQL(KETSearchEcoVO ketSearchEcoVO) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT T1.classnameA2A2||':'|| T1.idA2A2 oid \n");
	sql.append("	, T2.documentNumber docNumber \n");
	sql.append("	, T2.name docName \n");
	sql.append("	, T1.versionIdA2versionInfo docVer \n");
	sql.append("	, DECODE(T2.OWNERAPPLICATION, 'PLMSYSTEM', '2D', '3D') docCls \n");
	sql.append("	, (SELECT ST2.name \n");
	sql.append("	FROM WTUser ST1 \n");
	sql.append("		, people ST2 \n");
	sql.append("	WHERE ST1.idA2A2 = T1.idA3B2IterationInfo \n");
	sql.append("	AND ST2.idA3B4 = ST1.idA2A2) creatorName \n");
	sql.append("	, TO_CHAR(T1.createStampA2, '" + dateFormatString + "') createDate \n");
	sql.append("FROM EPMDocument T1 \n");
	sql.append("	, EPMDocumentMaster T2 \n");
	sql.append("WHERE 1 = 1 \n");
	sql.append("AND T1.idA3MasterReference = T2.idA2A2 \n");
	//		sql.append("AND T1.idA2A2 IN (SELECT MAX(T1.idA2A2) \n");
	//		sql.append("	FROM EPMDocument T1 \n");
	//		sql.append("	        , EPMDocumentMaster T2 \n");
	//		sql.append("	WHERE 1 = 1 \n");
	//		sql.append("	AND T1.latestiterationinfo = 1 \n");
	//		sql.append("	AND idA3MasterReference = T2.idA2A2 \n");
	//		sql.append("AND (T2.OWNERAPPLICATION='PLMSYSTEM') \n");
	//		sql.append("OR (T2.OWNERAPPLICATION='EPM' AND T2.AUTHORINGAPPLICATION='UG' AND (T2.DOCTYPE='CADASSEMBLY' OR T2.DOCTYPE='CADCOMPONENT')) \n");
	if (!"".equals(ketSearchEcoVO.getPartNumber())) {
	    sql.append("AND T2.documentNumber LIKE '" + StringUtil.getLikeQueryString(ketSearchEcoVO.getPartNumber().toUpperCase()) + "'	\n");
	}
	if (!"".equals(StringUtil.checkNull(ketSearchEcoVO.getPartName()))) {
	    sql.append("AND T2.name LIKE '" + StringUtil.getLikeQueryString(ketSearchEcoVO.getPartName().toUpperCase()) + "'	\n");
	}


	// 로그인 사용자가 KETS 일 경우
	if (CommonUtil.isKETSUser()) {
	    sql.append(CommonUtil.ketsUserListWhereStr("T1.ida3d2iterationinfo"));
	}


	//		sql.append(") \n");
	sql.append("ORDER BY 2 ASC \n");
	EcmUtil.logging("\n\nSearchMoldChangeOrderDao.getSearcEpmPopupListSQL:\n" + sql.toString());
	return sql.toString();
    }//end-of-getSearcEpmPopupListSQL

    /**
     * 
     * 함수명 : searchEcmPartPopupList 설명 : 설계변경용 부품 팝업 목록을 검색한다.
     * 
     * @param ketSearchEcoVO
     *            : 금형 ECO 검색 VO
     * @return ketSearchEcoVO : 금형 ECO 검색 VO
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    public KETSearchEcoVO searchEcmPartPopupList(KETSearchEcoVO ketSearchEcoVO) throws Exception {
	ArrayList<KETSearchEcoDetailVO> ketSearchEcoDetailVOList = new ArrayList<KETSearchEcoDetailVO>();//ECO 검색상세 List
	KETSearchEcoDetailVO ketSearchEcoDetailVO = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	try {
	    con = PlmDBUtil.getConnection();
	    if (ketSearchEcoVO.getTotalCount() == 0 && ketSearchEcoVO.getPagingYn() == 1) {//신규조회시 전체건수 조회
		sql = getSearchEcmPartPopupListSQL(ketSearchEcoVO, 0);
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		rs.next();
		ketSearchEcoVO.setTotalCount(rs.getInt("totalCount"));
		if (rs != null) rs.close();
		if (pstmt != null) pstmt.close();
	    }

	    sql = getSearchEcmPartPopupListSQL(ketSearchEcoVO, 1);
	    pstmt = con.prepareStatement(sql);
	    int resultRows = 0;
	    if (ketSearchEcoVO.getPagingYn() == 1) {
		int startRow = (ketSearchEcoVO.getPage() - 1) * ketSearchEcoVO.getPerPage() + 1;
		int endRow = ketSearchEcoVO.getPage() * ketSearchEcoVO.getPerPage();
		pstmt.setInt(1, startRow);
		pstmt.setInt(2, endRow);
	    }
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		resultRows++;
		ketSearchEcoDetailVO = new KETSearchEcoDetailVO();
		ketSearchEcoDetailVO.setPartNumber(StringUtil.escapeHtml(rs.getString("partNo")));
		ketSearchEcoDetailVO.setPartName(StringUtil.escapeHtml(rs.getString("partName")));
		ketSearchEcoDetailVO.setPartVersion(rs.getString("partVersion"));
		ketSearchEcoDetailVO.setOid(rs.getString("oid"));
		ketSearchEcoDetailVOList.add(ketSearchEcoDetailVO);
	    }
	    ketSearchEcoVO.setResultRows(resultRows);
	} catch (Exception e) {
	    throw e;
	} finally {
	    if (rs != null) rs.close();
	    if (pstmt != null) pstmt.close();
	    PlmDBUtil.close(con);
	}
	ketSearchEcoVO.setKetSearchEcoDetailVOList(ketSearchEcoDetailVOList);
	PageControl pageControl = new PageControl(ketSearchEcoVO.getPage(), 10, ketSearchEcoVO.getPerPage(), ketSearchEcoVO.getTotalCount());
	pageControl.setParam(ketSearchEcoVO.getParam());
	ketSearchEcoVO.setPageControl(pageControl);

	return ketSearchEcoVO;
    }

    /**
     * 
     * 함수명 : getSearchEcmPartPopupListSQL 설명 : 설계변경용 부품 목록을 검색하는 SQL을 작성한다.
     * 
     * @param ketSearchEcoVO
     *            : 금형 ECO 검색 VO
     * @param ingPage
     *            : 페이지처리여부(0:미처리 1:페이지처리)
     * @return ketSearchEcoVO : 금형 ECO 검색 VO
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    //SQL 생성
    private String getSearchEcmPartPopupListSQL(KETSearchEcoVO ketSearchEcoVO, int ingPage) throws Exception {
	StringBuffer sql = new StringBuffer();
	if (ingPage == 0) {//건수조회
	    sql.append("SELECT COUNT(*) totalCount \n");
	}
	else {
	    sql.append("SELECT * \n");
	}
	sql.append("  FROM ( \n");
	sql.append("            SELECT ROWNUM row_id \n");
	sql.append("                    , t.* \n");
	sql.append("             FROM ( \n");

	sql.append("SELECT T3.WTPartNumber partNo \n");
	sql.append("	, T3.name partName \n");
	sql.append("	, T2.versionIdA2versionInfo partVersion \n");
	sql.append("	, T2.classnameA2A2||':'||T2.ida2a2 oid \n");
	sql.append("FROM WTPart T2 \n");
	sql.append("	, WTPartMaster T3 \n");
	sql.append("WHERE 1 = 1 \n");
	sql.append("AND T2.idA3masterReference = T3.ida2a2 \n");
	sql.append("AND T2.ida2a2 IN ( \n");
	sql.append("	              SELECT MAX(ST2.ida2a2) ida2a2 \n");
	sql.append("		        FROM WTPart ST2 \n");
	sql.append("			   , WTPartMaster ST3 \n");
	sql.append("		       WHERE 1 = 1 \n");
	sql.append("	                 AND ST2.idA3masterReference = ST3.ida2a2 \n");
	sql.append("	                 AND ST2.latestiterationinfo = 1 \n");
	sql.append("	               GROUP BY ST3.WTPartNumber \n");
	sql.append("                 ) \n");


	// 로그인 사용자가 KETS 일 경우
	if (CommonUtil.isKETSUser()) {
	    sql.append(CommonUtil.ketsUserListWhereStr("T2.ida3d2iterationinfo"));
	}


	sql.append("ORDER BY " + ketSearchEcoVO.getSortColumn() + " \n");

	sql.append(") t  )tb	 \n");
	sql.append("WHERE 1 = 1 \n");
	if (ingPage == 1 && ketSearchEcoVO.getPagingYn() == 1) {//자료조회
	    sql.append("AND tb.row_id BETWEEN ? AND ? \n");
	}
	EcmUtil.logging("\n\nSearchMoldChangeOrderDao.getSearchEcmPartPopupListSQL:\n" + sql.toString());
	return sql.toString();
    }
}
