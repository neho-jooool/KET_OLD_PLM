package e3ps.edm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import wt.lifecycle.State;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.message.KETMessageService;
import e3ps.common.treegrid.TgPagingControl;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.StringUtil;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.util.IBAStringDefinitionCache;
import ext.ket.shared.log.Kogger;

/**
 * [PLM System 1차개선]>[PLM System 2차개선] 클래스명 : EDMDao 설명 : 도면 관리 Dao 수정자 : BoLee > tklee 작성일자 : 2013. 8. 25. > 2014. 6. 23.
 */

public class EPMDocumentDao {

    public EPMDocumentDao() {
	super();
    }

    /**
     * 함수명 : searchEPMDocument 설명 :
     * 
     * @param mapList
     * @return List<Map<String, Object>>
     * @throws Exception
     *             작성자 : Tklee 작성일자 : 2014. 6. 25.
     */
    public List<Map<String, Object>> searchEPMDocument(List<Map> conditionList, TgPagingControl pager, boolean allDataNotPagingData)
	    throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<Map<String, Object>> epmDocumentList = new ArrayList<Map<String, Object>>();

	try {
	    // 기본 : 혹시 페이징쿼리 참조하시는 분은 주석 처리된 소스를 참조하세요.
	    // String query = oDaoManager.getSearchEPMDocQuery(query); // 기본 쿼리
	    // query = oDaoManager.getOrderByQuery(query, pager); // Order By 붙이는 쿼리
	    // String sortCol = pager.getSortCol(); // Order By 필요한 Parameter 가져올 때
	    // String sortType = pager.getSortType();
	    // query = oDaoManager.getPagingQuery(query, pager); // 기본 Paging 쿼리

	    // 쿼리 튜닝 : 나중에 PrepareStatement Param 과 승인일 요청일을 처리한다.
	    String sortCol = pager.getSortCol();
	    // 1) 10 개의 Drawing Oid 가져오기
	    String query = getSearchEPMDocQueryBySortNCondition(conditionList, sortCol);
	    query = oDaoManager.getOrderByQuery(query, pager);
	    if (!allDataNotPagingData) {
		query = oDaoManager.getPagingQuery(query, pager);
	    }
	    // 2) 10 개의 Drawing Oid를 in 문에 넣고 값을 Select 해온다.
	    query = getSearchEPMDocQueryByEpmOid(query);
	    query = getSearchEPMDocQueryBySelectIn(query);
	    query = oDaoManager.getOrderByQuery(query, pager);

	    epmDocumentList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    return getEpmResultSet(rs);
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {

	}

	return epmDocumentList;
    }

    private HashMap<String, Object> getEpmResultSet(ResultSet rs) throws SQLException {

	HashMap<String, Object> epmDocument = new HashMap<String, Object>();

	epmDocument.put("drawingOid", rs.getString("drawingOid"));
	epmDocument.put("drawingMasterOid", rs.getString("drawingMasterOid"));
	epmDocument.put("drawingNo", rs.getString("drawingNo")); // DrawingNo < ##sort
	epmDocument.put("drawingName", rs.getString("drawingName")); // DrawingName < ##sort
	epmDocument.put("ver", rs.getString("ver")); //
	epmDocument.put("manufactureVer", rs.getString("manufactureVer")); // Ver < ##sort
	epmDocument.put("status", rs.getString("statusCode")); // Status
	epmDocument.put("createDate", rs.getTimestamp("createDate")); // CreateDate < ##sort
	epmDocument.put("holderOid", rs.getString("holderOid")); // File
	epmDocument.put("appDataOid", rs.getString("appDataOid")); // File
	epmDocument.put("extension", rs.getString("extension")); // File
	epmDocument.put("creator", rs.getString("creator")); // Creator < sort
	epmDocument.put("cadType", StringUtil.checkNull(rs.getString("cadType"))); // CADType < ##sort
	epmDocument.put("dummy", rs.getString("dummy")); // Dummy

	epmDocument.put("requestDate", StringUtil.checkNull(rs.getString("requestDate")));// RequestDate
	epmDocument.put("approvalDate", StringUtil.checkNull(rs.getString("approvalDate")));// ApprovalDate

	epmDocument.put("projectNo", StringUtil.checkNull(rs.getString("projectNo")));// ProjectNo
	epmDocument.put("projectName", StringUtil.checkNull(rs.getString("projectName")));// ProjectName
	epmDocument.put("createDeptName", rs.getString("createDeptName"));// CreateDeptName
	epmDocument.put("partNumber", StringUtil.checkNull(rs.getString("partNumber")));// PartNumber
	epmDocument.put("devStage", rs.getString("devStage"));// DevStage
	epmDocument.put("category", rs.getString("category"));// Category
	epmDocument.put("security", rs.getString("security"));// Security

	return epmDocument;
    }

    public List<Map<String, Object>> searchEPMDocumentForExcel(List<Map> conditionList) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<Map<String, Object>> epmDocumentList = new ArrayList<Map<String, Object>>();

	try {

	    String query = getSearchEPMDocQueryByExcel(conditionList);

	    epmDocumentList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    return getEpmResultSet(rs);
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {

	}

	return epmDocumentList;
    }

    public int searchEPMDocumentCount(List<Map> conditionList) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	String sortCol = null;
	String query = getSearchEPMDocQueryBySortNCondition(conditionList, sortCol);
	int totalCount = oDaoManager.queryForCount(query);

	return totalCount;
    }

    // private String getSearchEPMDocQuery(List<Map> conditionList) throws Exception {
    //
    // StringBuffer sb = new StringBuffer();
    // KETParamMapUtil map = null;
    // boolean isGroupBy = false;
    //
    // sb.append(" SELECT epm.classnamea2a2 || ':' || epm.ida2a2 AS drawingOid                                               \n");
    // sb.append("       ,m.documentnumber AS drawingNo                                                                      \n");
    // sb.append("       ,m.name AS drawingName                                                                              \n");
    // sb.append("       ,epm.versionida2versioninfo AS ver                                                                  \n");
    // sb.append("       ,epm.statestate AS status                                                                            \n");
    // sb.append("       ,epm.createstampa2 AS createDate                                                                    \n");
    // sb.append("       ,holder.classnamekeyroleaobjectref || ':' || holder.ida3a5 AS holderOid                             \n");
    // sb.append("       ,holder.classnamekeyrolebobjectref || ':' || holder.ida3b5 AS appDataOid                            \n");
    // sb.append("       ,REVERSE(SUBSTR(REVERSE(app.filename), 0, INSTR(REVERSE(app.filename), '.', 1, 1)-1)) AS extension  \n");
    // sb.append("       ,p.name AS creator                                                                                  \n");
    // sb.append("       ,s1.value AS cadType                                                                                \n");
    // sb.append("       ,s4.value AS dummy                                                                                  \n");
    // //
    // sb.append("  FROM EPMDocumentMaster m                                                                                 \n");
    // sb.append("      ,EPMDocument epm                                                                                     \n");
    // sb.append("      ,HolderToContent holder                                                                              \n");
    // sb.append("      ,ApplicationData app                                                                                 \n");
    // sb.append("      ,People p                                                                                            \n");
    // sb.append("      ,StringValue s1                                                                                      \n");
    // sb.append("      ,StringValue s4                                                                                      \n");
    //
    // // 사업부구분이 있는 경우
    // for (Map<String, Object> condition : conditionList) {
    // KETParamMapUtil queryTable = KETParamMapUtil.getMap(condition);
    // String[] businessType = queryTable.getStringArray("businessType");
    // if (businessType != null && businessType.length > 0) {
    // sb.append("      ,EPMDocTypeCodeLink t                                                                        \n");
    // }
    // break;
    // }
    //
    // // 부품번호가 있는 경우
    // for (Map<String, Object> condition : conditionList) {
    // KETParamMapUtil queryTable = KETParamMapUtil.getMap(condition);
    // String[] partNumber = queryTable.getStringArray("partNumberMasterOid");
    // if (partNumber != null && partNumber.length > 0) {
    // sb.append("      ,EPMLink l        \n");
    // sb.append("      ,WTPartMaster pmaster                                                                        \n");
    //
    // isGroupBy = true; // Group By 필요
    // }
    // break;
    // }
    //
    // // 프로젝트번호가 있는 경우
    // for (Map<String, Object> condition : conditionList) {
    // KETParamMapUtil queryTable = KETParamMapUtil.getMap(condition);
    // String projectNo = queryTable.getString("projectNo");
    // if (projectNo != null && projectNo.length() > 0) {
    // sb.append("      ,EPMDocProjectLink pjt                                                                       \n");
    // sb.append("      ,E3PSProjectMaster pm                                                                        \n");
    //
    // isGroupBy = true; // Group By 필요
    // }
    // break;
    // }
    //
    // // 도면구분이 있는 경우
    // for (Map<String, Object> condition : conditionList) {
    // KETParamMapUtil queryTable = KETParamMapUtil.getMap(condition);
    // String[] devStage = queryTable.getStringArray("devStage");
    // if (devStage != null && devStage.length > 0) {
    // sb.append("      ,StringValue s2                                                                              \n");
    // }
    // break;
    // }
    //
    // // 도면유형이 있는 경우
    // for (Map<String, Object> condition : conditionList) {
    // KETParamMapUtil queryTable = KETParamMapUtil.getMap(condition);
    // String[] category = queryTable.getStringArray("category");
    // if (category != null && category.length > 0) {
    // sb.append("      ,StringValue s3                                                                              \n");
    // }
    // break;
    // }
    //
    // sb.append("  WHERE m.ida2a2 = epm.ida3masterreference                                                                 \n");
    // sb.append("    AND epm.ida2a2 = holder.ida3a5                                                                         \n");
    // sb.append("    AND holder.ida3b5 = app.ida2a2                                                                         \n");
    // sb.append("    AND app.role = 'PRIMARY'                                                                               \n");
    // sb.append("    AND epm.latestiterationinfo = 1                                                                        \n");
    // sb.append("    AND epm.statecheckoutinfo NOT IN ('term','wrk','to del','to wrk')                                      \n");
    // sb.append("    AND p.ida3b4 = epm.ida3d2iterationinfo                                                                 \n");
    // sb.append("    AND s1.idA3A4(+) = epm.idA2A2                                                                          \n");
    // sb.append("    AND s1.hierarchyIDA6(+) = '4011703118'                                                                 \n");
    // sb.append("    AND s4.ida3a4(+) = epm.ida2a2                                                                          \n");
    // sb.append("    AND s4.hierarchyida6(+) = '454201551'                                                                  \n");
    //
    // // 검색 조건 - 결과 내 재검색 loop
    // for (Map<String, Object> condition : conditionList) {
    //
    // map = KETParamMapUtil.getMap(condition);
    //
    // // 사업부구분이 있는 경우
    // String[] businessType = map.getStringArray("businessType");
    // if (businessType != null && businessType.length > 0) {
    // sb.append("    AND epm.branchiditerationinfo = t.branchida3b5                                                 \n");
    // sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("t.ida3a5",
    // KETParamMapUtil.OidToString(KETParamMapUtil.toString(businessType)), false)).append("\n");
    // }
    //
    // // 부품번호가 있는 경우
    // String[] partNumber = map.getStringArray("partNumberMasterOid");
    // if (partNumber != null && partNumber.length > 0) {
    // sb.append("    AND m.ida2a2 = l.ida3a5                                                                        \n");
    // sb.append("    AND l.ida3b5 = pmaster.ida2a2                                                                  \n");
    // sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("pmaster.ida2a2",
    // KETParamMapUtil.OidToString(KETParamMapUtil.toString(partNumber)), false)).append(" \n");
    // }
    //
    // // 도면명이 있는 경우
    // if (StringUtil.checkString(map.getString("name"))) {
    // sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m.name", map.getString("name"), true)).append("     \n");
    // }
    //
    // // 도면번호가 있는 경우
    // if (StringUtil.checkString(map.getString("number"))) {
    // sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m.documentnumber", map.getString("number"),
    // true)).append(" \n");
    // }
    //
    // // 작성일자(from)가 있는 경우
    // if (StringUtil.checkString(map.getString("create_start"))) {
    // String create_start = map.getString("create_start");
    // create_start = create_start.substring(0, 4) + create_start.substring(5, 7) + create_start.substring(8, 10);
    // sb.append("    AND epm.createstampa2 >= TO_DATE('" + create_start + "' || '000000','YYYYMMDDHH24MISS')         \n");
    // }
    //
    // // 작성일자(to)가 있는 경우
    // if (StringUtil.checkString(map.getString("create_end"))) {
    // String create_end = map.getString("create_end");
    // create_end = create_end.substring(0, 4) + create_end.substring(5, 7) + create_end.substring(8, 10);
    // sb.append("    AND epm.createstampa2 <= TO_DATE('" + create_end + "' || '235959','YYYYMMDDHH24MISS')           \n");
    // }
    //
    // // 작성자가 있는 경우
    // String creator = map.getString("creator");
    // if (creator != null && creator.length() > 0) {
    // sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("epm.ida3d2iterationinfo", KETParamMapUtil.OidToString(creator),
    // false)).append(" \n");
    // }
    //
    // // 작성부서가 있는 경우
    // String edmCreateDept = map.getString("edmCreateDeptName");
    // if (edmCreateDept != null && edmCreateDept.length() > 0) {
    // sb.append("    AND epm.branchiditerationinfo in (select OBJBRANCHID    \n ");
    // sb.append("                                        from EDMUserData    \n ");
    // sb.append("                                       where 1=1            \n ");
    // sb.append("                                        AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("creatordeptname",
    // edmCreateDept, false)).append(" \n");
    // sb.append("                                       )                    \n ");
    // }
    //
    // // 최신 버전일 경우
    // String latest = map.getString("latest");
    // if (latest.length() == 0) {
    // latest = String.valueOf(true);
    // }
    // if (Boolean.parseBoolean(latest)) {
    // sb.append("  AND EPM.VERSIONIDA2VERSIONINFO = ( SELECT TO_CHAR(MAX(TO_NUMBER(E.VERSIONIDA2VERSIONINFO))) \n");
    // sb.append("					FROM EPMDOCUMENT E \n");
    // sb.append("					WHERE E.IDA3MASTERREFERENCE = EPM.IDA3MASTERREFERENCE \n");
    // sb.append("					AND E.STATECHECKOUTINFO NOT IN ('term','wrk','to del','to wrk') \n");
    // sb.append("					AND E.LATESTITERATIONINFO = 1 )  \n");
    // }
    //
    // // 프로젝트번호가 있는 경우
    // String projectNo = map.getString("projectNo");
    // if (projectNo != null && projectNo.length() > 0) {
    // sb.append("    AND epm.branchiditerationinfo = pjt.branchida3b5                                                      \n");
    // sb.append("    AND pjt.ida3a5 = pm.ida2a2                                                                            \n");
    // sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("pm.pjtno", projectNo, false)).append("          \n");
    // }
    //
    // // 상태가 있는 경우
    // String[] state = map.getStringArray("state");
    // if (state != null && state.length > 0) {
    // sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("epm.statestate", state, false)).append("        \n");
    // }
    //
    // // CAD종류가 있는 경우
    // String[] cadAppType = map.getStringArray(EDMHelper.IBA_CAD_APP_TYPE);
    // if (cadAppType != null && cadAppType.length > 0) {
    // sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("s1.value", cadAppType, true)).append("          \n");
    // }
    //
    // // 도면구분이 있는 경우
    // String[] devStageAry = map.getStringArray(EDMHelper.IBA_DEV_STAGE);
    // if (devStageAry != null && devStageAry.length > 0) {
    // sb.append("    AND s2.ida3a4 = epm.ida2a2                                                                            \n");
    // sb.append("    AND s2.hierarchyida6 = '161469840'                                                                    \n");
    // sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("s2.value", devStageAry, true)).append("         \n");
    // }
    //
    // // 도면유형이 있는 경우
    // String[] categoryAry = map.getStringArray(EDMHelper.IBA_CAD_CATEGORY);
    // if (categoryAry != null && categoryAry.length > 0) {
    // sb.append("    AND s3.ida3a4 = epm.ida2a2                                                                            \n");
    // sb.append("    AND s3.hierarchyida6 = '1278432361'                                                                   \n");
    // sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("s3.value", categoryAry, true)).append("         \n");
    // }
    // }
    //
    // // 부품번호가 있거나 프로젝트번호가 있는 경우
    // if (isGroupBy) {
    // sb.append("   GROUP BY m.documentnumber                                 \n");
    // sb.append("           ,epm.classnamea2a2                                \n");
    // sb.append("           ,epm.ida2a2                                       \n");
    // sb.append("           ,m.name                                           \n");
    // sb.append("           ,epm.versionida2versioninfo                       \n");
    // sb.append("           ,epm.statestate                                   \n");
    // sb.append("           ,epm.createstampa2                                \n");
    // sb.append("           ,holder.classnamekeyroleaobjectref                \n");
    // sb.append("           ,holder.ida3a5                                    \n");
    // sb.append("           ,holder.classnamekeyrolebobjectref                \n");
    // sb.append("           ,holder.ida3b5                                    \n");
    // sb.append("           ,app.filename                                     \n");
    // sb.append("           ,p.name                                           \n");
    // sb.append("           ,s1.value                                         \n");
    // sb.append("           ,s4.value                                         \n");
    // }
    //
    // Kogger.debug(getClass(), "######## 도면 검색 쿼리 : \n");
    //
    // return sb.toString();
    // }

    private String getSearchEPMDocQueryByEpmOid(String query) {

	StringBuffer sb = new StringBuffer();

	sb.append(" SELECT DRAWINGOID FROM ( \n");
	sb.append(query);
	sb.append(" ) \n");

	return sb.toString();
    }

    /**
     * 
     * @param conditionList
     * @return
     * @throws Exception
     * @메소드명 : getSearchEPMDocQueryNew
     * @작성자 : tklee
     * @작성일 : 2014. 6. 24.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private String getSearchEPMDocQueryBySortNCondition(List<Map> conditionList, String sortCol) throws Exception {

	// [EDM분석-14.06.24 - 도면과 관련된 쿼리 버그 및 성능향상을 위해 수정 TKLEE
	StringBuffer sb = new StringBuffer();
	KETParamMapUtil map = null;

	// 1. select에 Sort칼럼 + Oid만 가져온다.
	// 2. where 절을 튜님 및 custom_index를 추가한다.
	// 3. select를 count로 가져오는 경우 성능 향상 효과

	EPMDocDaoCondition dCondition = new EPMDocDaoCondition(conditionList);

	KETMessageService messageService = KETMessageService.getMessageService();
	Locale locale = messageService.getLocale();

	boolean hasCadTypeSort = false;

	// EPM 기본 정보
	sb.append(" SELECT EPM.IDA2A2 AS DRAWINGOID \n");
	// Sorting에 필요한 select column 정의
	if (StringUtils.isNotEmpty(sortCol)) {
	    if ("CreateDate".equals(sortCol)) {
		sb.append(" ,EPM.CREATESTAMPA2 AS CREATEDATE \n");
	    } else if ("DrawingNo".equals(sortCol)) {
		sb.append(" ,EM.DOCUMENTNUMBER AS DRAWINGNO \n");
	    } else if ("Creator".equals(sortCol)) {
		sb.append(" ,P.NAME AS CREATOR \n");
	    } else if ("DrawingName".equals(sortCol)) {
		sb.append(" ,EM.NAME AS DRAWINGNAME \n");
	    } else if ("ManufactureVer".equals(sortCol)) {
		sb.append(" ,SV_MANUF_VER.VALUE AS manufactureVer \n");
	    } else if ("Dummy".equals(sortCol)) {
		sb.append(" ,SV_DUMMY.VALUE AS Dummy \n");
	    } else if ("Status".equals(sortCol)) {
		sb.append(" ,DECODE(EPM.statestate, 'APPROVED', '" + State.toState("APPROVED").getDisplay(locale) + "', 'INWORK', '"
		        + State.toState("INWORK").getDisplay(locale) + "', 'UNDERREVIEW', '"
		        + State.toState("UNDERREVIEW").getDisplay(locale) + "', '') AS Status \n");
	    } else if ("CADType".equals(sortCol)) {
		hasCadTypeSort = true;
		sb.append(" ,SV_CAD_TYPE.VALUE AS CADTYPE \n "); // CadType
	    }
	}

	// From
	sb.append("  FROM EPMDOCUMENTMASTER EM \n");
	sb.append("      ,EPMDOCUMENT EPM \n");
	// Sorting에 필요한 select column에 필요하거나, where 조건에 필요한 from절 > 관련 table 삽입
	if (hasCadTypeSort || dCondition.getCadTypeList().size() > 0)
	    sb.append("      ,STRINGVALUE SV_CAD_TYPE \n");
	if (dCondition.getDevStageList().size() > 0)
	    sb.append("      ,STRINGVALUE SV_DEV_STAGE \n");
	if (dCondition.getCadCategoryList().size() > 0)
	    sb.append("      ,STRINGVALUE SV_CAD_CATEGORY \n");
	if ("Creator".equals(sortCol)) {
	    sb.append("      ,People P \n");
	}
	if ("Dummy".equals(sortCol)) {
	    sb.append("      ,STRINGVALUE SV_DUMMY \n");
	}
	if ("ManufactureVer".equals(sortCol)) {
	    sb.append("      ,STRINGVALUE SV_MANUF_VER \n");
	}
	sb.append("  WHERE 1=1 \n");
	sb.append("  AND EM.IDA2A2 = EPM.IDA3MASTERREFERENCE \n");
	sb.append("  AND EPM.LATESTITERATIONINFO = 1 \n");
	sb.append("  AND EPM.STATECHECKOUTINFO IN ('c/i','c/o') \n"); // ('term','wrk','to del','to wrk')
	// Sorting에 필요한 select column에 필요하거나, where 조건에 필요한 from절 > 관련 table 삽입 > 된 것의 where 절 추가
	if (hasCadTypeSort || dCondition.getCadTypeList().size() > 0) {
	    sb.append("  AND EPM.IDA2A2 = SV_CAD_TYPE.IDA3A4(+) AND '" + IBAStringDefinitionCache.getInstance().getCadAppType()
		    + "' = SV_CAD_TYPE.IDA3A6(+) \n");
	}

	if (dCondition.getDevStageList().size() > 0) {
	    sb.append("  AND EPM.IDA2A2 = SV_DEV_STAGE.IDA3A4(+) AND '" + IBAStringDefinitionCache.getInstance().getDevStage()
		    + "' = SV_DEV_STAGE.IDA3A6(+) \n");
	}

	if (dCondition.getCadCategoryList().size() > 0) {
	    sb.append("  AND EPM.IDA2A2 = SV_CAD_CATEGORY.IDA3A4(+) AND '" + IBAStringDefinitionCache.getInstance().getCadCategory()
		    + "' = SV_CAD_CATEGORY.IDA3A6(+) \n");
	}

	if ("Creator".equals(sortCol)) {
	    sb.append("  AND EPM.IDA3D2ITERATIONINFO = P.IDA3B4(+) \n");
	}

	if ("ManufactureVer".equals(sortCol)) {
	    sb.append("  AND EPM.IDA2A2 = SV_MANUF_VER.IDA3A4(+) AND '" + IBAStringDefinitionCache.getInstance().getManufacturingVersion()
		    + "' = SV_MANUF_VER.IDA3A6(+) \n");
	}

	if ("Dummy".equals(sortCol)) {
	    sb.append("  AND EPM.IDA2A2 = SV_DUMMY.IDA3A4(+) AND '" + IBAStringDefinitionCache.getInstance().getIsDummyFile()
		    + "' = SV_DUMMY.IDA3A6(+) \n");
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 검색 조건 - 결과 내 재검색 loop
	for (Map<String, Object> condition : conditionList) {

	    map = KETParamMapUtil.getMap(condition);

	    // 사업부구분이 있는 경우
	    String[] businessType = map.getStringArray("businessType");
	    if (businessType != null && businessType.length > 0 && StringUtils.isNotEmpty(businessType[0])) {
		// 사업구분
		sb.append("    AND EPM.BRANCHIDITERATIONINFO IN ( SELECT T.BRANCHIDA3B5 FROM EPMDOCTYPECODELINK T WHERE ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("T.IDA3A5",
		                KETParamMapUtil.OidToString(KETParamMapUtil.toString(businessType)), false)).append(" ) \n");
	    }

	    // 부품번호로 검색(부품번호가 있는 경우)
	    String[] partNumberArray = map.getStringArray("partNumber");

	    if (partNumberArray != null && partNumberArray.length > 0 && StringUtils.isNotEmpty(partNumberArray[0])) {

		// 부품번호
		boolean hasPercent = false;
		for (String element : partNumberArray) {
		    if (StringUtils.isNotEmpty(element) && element.indexOf("*") != -1) {
			hasPercent = true;
			break;
		    }
		}

		if (hasPercent) {

		    sb.append(
			    "    AND EM.IDA2A2 IN ( SELECT L.IDA3A5 FROM EPMLINK L, WTPARTMASTER PMASTER WHERE L.IDA3B5 = PMASTER.IDA2A2 AND ")
			    .append(KETQueryUtil.getSqlQueryForMultiSearch("PMASTER.WTPARTNUMBER",
			            KETParamMapUtil.toString(partNumberArray), false)).append(" ) \n");
		} else {

		    String firstPartInParam = "";
		    for (String element : partNumberArray) {
			if (StringUtils.isNotEmpty(element)) {
			    element = element.trim();
			    if ("".equals(firstPartInParam)) {
				firstPartInParam = "'" + element + "'";
			    } else {
				firstPartInParam = firstPartInParam + "," + "'" + element + "'";
			    }
			}
		    }

		    EPMPartMatcher matcher = new EPMPartMatcher();
		    List<String> matchEpmNoList = matcher.getPartMatchEpmNo(partNumberArray);

		    String secondPartInParam = "";
		    for (String element : matchEpmNoList) {
			if (StringUtils.isNotEmpty(element)) {
			    element = element.trim();
			    if ("".equals(secondPartInParam)) {
				secondPartInParam = "'" + element + "'";
			    } else {
				secondPartInParam = secondPartInParam + "," + "'" + element + "'";
			    }
			}
		    }

		    sb.append("    AND EM.IDA2A2 IN ( ");
		    sb.append("    	SELECT L.IDA3A5  \n");
		    sb.append("    	FROM EPMLINK L, WTPARTMASTER PMASTER  \n");
		    sb.append("    	WHERE L.IDA3B5 = PMASTER.IDA2A2  \n");
		    sb.append("    	AND PMASTER.WTPARTNUMBER IN ( " + firstPartInParam + " ) \n");
		    sb.append("   	UNION ALL \n");
		    sb.append("    	SELECT IDA2A2 \n");
		    sb.append("    	FROM EPMDOCUMENTMASTER \n");
		    sb.append("    	WHERE DOCUMENTNUMBER IN ( " + secondPartInParam + " ) \n");
		    sb.append("    ) ");
		}
	    }

	    // 부품 MastOid 가 있는 경우
	    String[] partNumberMasterOid = map.getStringArray("partNumberMasterOid");
	    if (partNumberMasterOid != null && partNumberMasterOid.length > 0
		    && !(partNumberMasterOid.length == 1 && partNumberMasterOid[0].equals(""))) {
		// 부품번호
		sb.append(
		        "    AND EM.IDA2A2 IN ( SELECT L.IDA3A5 FROM EPMLINK L, WTPARTMASTER PMASTER WHERE L.IDA3B5 = PMASTER.IDA2A2 AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("PMASTER.IDA2A2",
		                KETParamMapUtil.OidToString(KETParamMapUtil.toString(partNumberMasterOid)), false)).append(" ) \n");
	    }

	    // 도면명이 있는 경우
	    if (StringUtil.checkString(map.getString("name"))) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("EM.NAME", map.getString("name"), true))
		        .append("     \n");
	    }

	    // 도면번호가 있는 경우
	    if (StringUtil.checkString(map.getString("number"))) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("EM.DOCUMENTNUMBER", map.getString("number"), true))
		        .append(" \n");
	    }

	    // 작성일자(from)가 있는 경우
	    if (StringUtil.checkString(map.getString("create_start"))) {
		String create_start = map.getString("create_start");
		create_start = create_start.substring(0, 4) + create_start.substring(5, 7) + create_start.substring(8, 10);
		sb.append("    AND EPM.CREATESTAMPA2 >= TO_DATE('" + create_start + "' || '000000','YYYYMMDDHH24MISS')         \n");
	    }

	    // 작성일자(to)가 있는 경우
	    if (StringUtil.checkString(map.getString("create_end"))) {
		String create_end = map.getString("create_end");
		create_end = create_end.substring(0, 4) + create_end.substring(5, 7) + create_end.substring(8, 10);
		sb.append("    AND EPM.CREATESTAMPA2 <= TO_DATE('" + create_end + "' || '235959','YYYYMMDDHH24MISS')           \n");
	    }

	    // 작성자가 있는 경우
	    String creator = map.getString("creator");
	    if (creator != null && creator.length() > 0) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("EPM.IDA3D2ITERATIONINFO", KETParamMapUtil.OidToString(creator),
		                false)).append(" \n");
	    }

	    sb.append(CommonUtil.ketsUserListWhereStr("EPM.IDA3D2ITERATIONINFO"));

	    // 작성부서가 있는 경우
	    String edmCreateDept = map.getString("edmCreateDeptName");
	    if (edmCreateDept != null && edmCreateDept.length() > 0) {
		sb.append("    AND EPM.BRANCHIDITERATIONINFO IN ( SELECT OBJBRANCHID FROM EDMUserData WHERE 1=1 AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("CREATORDEPTNAME", edmCreateDept, false)).append(" ) \n");
	    }

	    // 최신 버전일 경우
	    String latest = map.getString("latest");
	    if (latest.length() == 0) {
		latest = String.valueOf(true);
	    }
	    if (Boolean.parseBoolean(latest)) {
		// 최신으로 검색
		sb.append("  AND EPM.VERSIONIDA2VERSIONINFO = ( SELECT TO_CHAR(MAX(TO_NUMBER(E.VERSIONIDA2VERSIONINFO))) \n");
		sb.append("					FROM EPMDOCUMENT E \n");
		sb.append("					WHERE E.IDA3MASTERREFERENCE = EPM.IDA3MASTERREFERENCE \n");
		sb.append("					AND E.STATECHECKOUTINFO NOT IN ('term','wrk','to del','to wrk') \n");
		sb.append("					AND E.LATESTITERATIONINFO = 1 )  \n");
	    }

	    // 프로젝트번호가 있는 경우
	    String projectNo = map.getString("projectNo");
	    if (projectNo != null && projectNo.length() > 0) {
		// 프로젝트번호
		sb.append("    AND EPM.BRANCHIDITERATIONINFO IN (SELECT PJT.BRANCHIDA3B5 FROM EPMDOCPROJECTLINK PJT,E3PSPROJECTMASTER PM ")
		        .append(" WHERE PJT.IDA3A5 = PM.IDA2A2 AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("PM.PJTNO", projectNo, false)).append(" ) \n");
	    }

	    // 상태가 있는 경우
	    String[] state = map.getStringArray("state");
	    if (state != null && state.length > 0 && StringUtils.isNotEmpty(state[0])) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("EPM.STATESTATE", state, false)).append("        \n");
	    }

	    // CAD종류가 있는 경우
	    String[] cadAppType = map.getStringArray(EDMHelper.IBA_CAD_APP_TYPE);
	    if (cadAppType != null && cadAppType.length > 0 && StringUtils.isNotEmpty(cadAppType[0])) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("SV_CAD_TYPE.VALUE", cadAppType, false, true))
		        .append(" \n");
	    }

	    // 도면구분이 있는 경우
	    String[] devStageAry = map.getStringArray(EDMHelper.IBA_DEV_STAGE);
	    if (devStageAry != null && devStageAry.length > 0 && StringUtils.isNotEmpty(devStageAry[0])) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("SV_DEV_STAGE.VALUE", devStageAry, false))
		        .append(" \n");
	    }

	    // 도면유형이 있는 경우
	    String[] categoryAry = map.getStringArray(EDMHelper.IBA_CAD_CATEGORY);
	    if (categoryAry != null && categoryAry.length > 0 && StringUtils.isNotEmpty(categoryAry[0])) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("SV_CAD_CATEGORY.VALUE", categoryAry, false))
		        .append(" \n");
	    }

	}

	Kogger.debug(getClass(), "######## 도면 검색 쿼리1 : \n");

	return sb.toString();
    }

    /**
     * 
     * @param conditionList
     * @return
     * @throws Exception
     * @메소드명 : getSearchEPMDocQueryNew
     * @작성자 : tklee
     * @작성일 : 2014. 6. 25.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private String getSearchEPMDocQueryBySelectIn(String subQuery) throws Exception {

	// [EDM분석-14.06.24 - 도면과 관련된 쿼리 버그 및 성능향상을 위해 수정 TKLEE
	StringBuffer sb = new StringBuffer();
	KETMessageService messageService = KETMessageService.getMessageService();
	Locale locale = messageService.getLocale();

	// SELECT를 서버페이징시에 분해하여 처리함.
	// 1. StringValue를 applicationData, HolderToContent 등을 모두 select로 빼서 처리한다.

	// select된 것을 가져온 후에 in으로 넣어서 처리
	// EPM 기본 정보
	sb.append(" SELECT EPM.CLASSNAMEA2A2 || ':' || EPM.IDA2A2 AS DRAWINGOID \n");
	sb.append(" ,EM.CLASSNAMEA2A2 || ':' || EM.IDA2A2 AS DRAWINGMASTEROID \n");
	sb.append(" ,EM.DOCUMENTNUMBER AS DRAWINGNO \n");
	sb.append(" ,EM.NAME AS DRAWINGNAME \n");
	sb.append(" ,EPM.VERSIONIDA2VERSIONINFO AS VER \n");
	sb.append(" ,EPM.STATESTATE AS statusCode \n");
	sb.append(" ,DECODE(EPM.statestate, 'APPROVED', '" + State.toState("APPROVED").getDisplay(locale) + "', 'INWORK', '"
	        + State.toState("INWORK").getDisplay(locale) + "', 'UNDERREVIEW', '" + State.toState("UNDERREVIEW").getDisplay(locale)
	        + "', '') AS Status \n");
	sb.append(" ,EPM.CREATESTAMPA2 AS CREATEDATE \n");
	sb.append(" ,(SELECT NAME FROM PEOPLE WHERE EPM.IDA3D2ITERATIONINFO = IDA3B4 ) AS CREATOR \n");
	// Vault 관련
	sb.append(" ,(SELECT HOLDER.CLASSNAMEKEYROLEAOBJECTREF || ':' || HOLDER.IDA3A5 FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP \n")
	        .append(" WHERE EPM.IDA2A2 = HOLDER.IDA3A5 AND HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS HOLDEROID \n");
	sb.append(" ,(SELECT HOLDER.CLASSNAMEKEYROLEBOBJECTREF || ':' || HOLDER.IDA3B5 FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP \n")
	        .append(" WHERE EPM.IDA2A2 = HOLDER.IDA3A5 AND HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS APPDATAOID \n");
	sb.append(" ,(SELECT REVERSE(SUBSTR(REVERSE(APP.FILENAME), 0, INSTR(REVERSE(APP.FILENAME), '.', 1, 1)-1)) \n")
	        .append(" FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP WHERE EPM.IDA2A2 = HOLDER.IDA3A5 AND \n")
	        .append(" HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS EXTENSION \n");
	// CadType, Dummy
	sb.append(" ,(SELECT VALUE FROM STRINGVALUE WHERE EPM.IDA2A2 = IDA3A4 AND IDA3A6 = (SELECT IDA2A2 FROM STRINGDEFINITION D \n")
	        .append(" WHERE D.NAME = 'CADAppType') ) AS CADTYPE \n");
	sb.append(" ,(SELECT VALUE FROM STRINGVALUE WHERE EPM.IDA2A2 = IDA3A4 AND IDA3A6 = (SELECT IDA2A2 FROM STRINGDEFINITION D \n")
	        .append(" WHERE D.NAME = 'IsDummyFile') ) AS DUMMY \n");
	// ADDED BY TKLEE NEW COLUMN
	// 부품 번호 : PartNumber
	sb.append(" ,(SELECT LISTAGG(PMASTER.WTPARTNUMBER, ',') WITHIN GROUP(ORDER BY PMASTER.WTPARTNUMBER ) AS PART_NO FROM EPMLINK L, \n")
	        .append(" WTPARTMASTER PMASTER WHERE L.IDA3A5 = EM.IDA2A2 AND L.IDA3B5 = PMASTER.IDA2A2 ) AS PARTNUMBER \n");
	// 도면 구분 : DevStage
	sb.append(" ,(SELECT VALUE FROM STRINGVALUE WHERE EPM.IDA2A2 = IDA3A4 AND IDA3A6 = (SELECT IDA2A2 FROM STRINGDEFINITION D \n")
	        .append(" WHERE D.NAME = 'DevStage') ) AS DEVSTAGE \n");
	// 도면 유형 : Category
	sb.append(" ,(SELECT VALUE FROM STRINGVALUE WHERE EPM.IDA2A2 = IDA3A4 AND IDA3A6 = (SELECT IDA2A2 FROM STRINGDEFINITION D \n")
	        .append(" WHERE D.NAME = 'CADCategory') ) AS CATEGORY \n");
	// 양산버전 : ManufacturingVersion
	sb.append(" ,(SELECT VALUE FROM STRINGVALUE WHERE EPM.IDA2A2 = IDA3A4 AND IDA3A6 = (SELECT IDA2A2 FROM STRINGDEFINITION D \n")
	        .append(" WHERE D.NAME = 'ManufacturingVersion') ) AS manufactureVer \n");
	// 보안등급 : Security
	sb.append(" ,(SELECT VALUE FROM STRINGVALUE WHERE EPM.IDA2A2 = IDA3A4 AND IDA3A6 = (SELECT IDA2A2 FROM STRINGDEFINITION D \n")
	        .append(" WHERE D.NAME = 'Security') ) AS security \n");
	// 작성부서 : CreateDeptName
	sb.append(
	        " ,(SELECT LISTAGG(CASE WHEN ROWNUM <=1 THEN U.CREATORDEPTNAME ELSE NULL END,',') WITHIN GROUP(ORDER BY U.CREATESTAMPA2\n")
	        .append(" DESC) FROM EDMUSERDATA U WHERE U.OBJBRANCHID = EPM.BRANCHIDITERATIONINFO ) AS CREATEDEPTNAME \n");
	// 상신일
	sb.append(" ,(SELECT TO_CHAR(H.REQUESTDATE, 'yyyy-MM-dd') FROM KETEpmApprovalHisLink HL, KETEpmApprovalHis H \n").append(
	        "   WHERE EPM.BRANCHIDITERATIONINFO = HL.BRANCHIDA3B5 AND HL.IDA3A5 = H.IDA2A2) AS requestDate \n");
	// 승인일
	sb.append(" ,(SELECT TO_CHAR(H.APPROVALDATE, 'yyyy-MM-dd') FROM KETEpmApprovalHisLink HL, KETEpmApprovalHis H \n").append(
	        "   WHERE EPM.BRANCHIDITERATIONINFO = HL.BRANCHIDA3B5 AND HL.IDA3A5 = H.IDA2A2) AS approvalDate \n");
	// 프로젝트 번호 : ProjectNo
	sb.append(" ,(SELECT LISTAGG(PM.PJTNO, ',') WITHIN GROUP(ORDER BY PM.PJTNO ) FROM EPMDOCPROJECTLINK PJT,E3PSPROJECTMASTER PM \n")
	        .append(" WHERE EPM.BRANCHIDITERATIONINFO = PJT.BRANCHIDA3B5 AND PJT.IDA3A5 = PM.IDA2A2) AS PROJECTNO \n");
	// 프로젝트 명 : ProjectName
	sb.append(" ,(SELECT LISTAGG(PM.PJTNAME, ',') WITHIN GROUP(ORDER BY PM.PJTNAME ) FROM EPMDOCPROJECTLINK PJT,E3PSPROJECTMASTER \n")
	        .append(" PM WHERE EPM.BRANCHIDITERATIONINFO = PJT.BRANCHIDA3B5 AND PJT.IDA3A5 = PM.IDA2A2) AS PROJECTNAME \n");

	// From
	sb.append("  FROM EPMDOCUMENTMASTER EM \n");
	sb.append("      ,EPMDOCUMENT EPM \n");
	sb.append("  WHERE 1=1 \n");
	sb.append("  AND EM.IDA2A2 = EPM.IDA3MASTERREFERENCE \n");
	sb.append("  AND EPM.IDA2A2 IN ( \n");
	sb.append(subQuery);
	sb.append("  ) \n");
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	Kogger.debug(getClass(), "######## 도면 검색 쿼리 2 : \n");

	return sb.toString();
    }

    /**
     * 
     * @param conditionList
     * @return
     * @throws Exception
     * @메소드명 : getSearchEPMDocQueryNew
     * @작성자 : tklee
     * @작성일 : 2014. 6. 24.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private String getSearchEPMDocQueryByExcel(List<Map> conditionList) throws Exception {

	// [EDM분석-14.06.24 - 도면과 관련된 쿼리 버그 및 성능향상을 위해 수정 TKLEE
	StringBuffer sb = new StringBuffer();
	KETParamMapUtil map = null;

	EPMDocDaoCondition dCondition = new EPMDocDaoCondition(conditionList);

	// SELECT를 서버페이징시에 분해하여 처리함.
	// 1. StringValue를 applicationData, HolderToContent 등을 모두 select로 빼서 처리한다.

	// select된 것을 가져온 후에 in으로 넣어서 처리
	// EPM 기본 정보
	sb.append(" SELECT EPM.CLASSNAMEA2A2 || ':' || EPM.IDA2A2 AS DRAWINGOID \n");
	sb.append(" ,EM.CLASSNAMEA2A2 || ':' || EM.IDA2A2 AS DRAWINGMASTEROID \n");
	sb.append(" ,EM.DOCUMENTNUMBER AS DRAWINGNO \n");
	sb.append(" ,EM.NAME AS DRAWINGNAME \n");
	sb.append(" ,EPM.VERSIONIDA2VERSIONINFO AS VER \n");
	sb.append(" ,EPM.STATESTATE AS statusCode \n");
	sb.append(" ,EPM.CREATESTAMPA2 AS CREATEDATE \n");
	sb.append(" ,(SELECT NAME FROM PEOPLE WHERE EPM.IDA3D2ITERATIONINFO = IDA3B4 ) AS CREATOR \n");
	// Vault 관련
	sb.append(" ,(SELECT HOLDER.CLASSNAMEKEYROLEAOBJECTREF || ':' || HOLDER.IDA3A5 FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP \n")
	        .append(" WHERE EPM.IDA2A2 = HOLDER.IDA3A5 AND HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS HOLDEROID \n");
	sb.append(" ,(SELECT HOLDER.CLASSNAMEKEYROLEAOBJECTREF || ':' || HOLDER.IDA3B5 FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP \n")
	        .append(" WHERE EPM.IDA2A2 = HOLDER.IDA3A5 AND HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS APPDATAOID \n");
	sb.append(" ,(SELECT REVERSE(SUBSTR(REVERSE(APP.FILENAME), 0, INSTR(REVERSE(APP.FILENAME), '.', 1, 1)-1)) \n")
	        .append(" FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP WHERE EPM.IDA2A2 = HOLDER.IDA3A5 AND \n")
	        .append(" HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS EXTENSION \n");
	// CadType, Dummy
	if (dCondition.getCadTypeList().size() > 0) {
	    sb.append(" ,SV_CAD_TYPE.VALUE AS CADTYPE \n ");
	} else {
	    sb.append(" ,(SELECT VALUE FROM STRINGVALUE WHERE EPM.IDA2A2 = IDA3A4 AND IDA3A6 = (SELECT IDA2A2 FROM STRINGDEFINITION D \n")
		    .append(" WHERE D.NAME = 'CADAppType') ) AS CADTYPE \n");
	}
	sb.append(" ,(SELECT VALUE FROM STRINGVALUE WHERE EPM.IDA2A2 = IDA3A4 AND IDA3A6 = (SELECT IDA2A2 FROM STRINGDEFINITION D \n")
	        .append(" WHERE D.NAME = 'IsDummyFile') ) AS DUMMY \n");
	// ADDED BY TKLEE NEW COLUMN
	// 부품 번호 : PartNumber
	sb.append(" ,(SELECT LISTAGG(PMASTER.WTPARTNUMBER, ',') WITHIN GROUP(ORDER BY PMASTER.WTPARTNUMBER ) AS PART_NO FROM EPMLINK L, \n")
	        .append(" WTPARTMASTER PMASTER WHERE L.IDA3A5 = EM.IDA2A2 AND L.IDA3B5 = PMASTER.IDA2A2 ) AS PARTNUMBER \n");

	// 도면 구분 : DevStage
	if (dCondition.getDevStageList().size() > 0) {
	    sb.append(" ,SV_DEV_STAGE.VALUE AS DEVSTAGE \n ");
	} else {
	    sb.append(" ,(SELECT VALUE FROM STRINGVALUE WHERE EPM.IDA2A2 = IDA3A4 AND IDA3A6 = (SELECT IDA2A2 FROM STRINGDEFINITION D \n")
		    .append(" WHERE D.NAME = 'DevStage') ) AS DEVSTAGE \n");
	}

	// 도면 유형 : Category
	if (dCondition.getCadCategoryList().size() > 0) {
	    sb.append(" ,SV_CAD_CATEGORY.VALUE AS CATEGORY \n ");
	} else {
	    sb.append(" ,(SELECT VALUE FROM STRINGVALUE WHERE EPM.IDA2A2 = IDA3A4 AND IDA3A6 = (SELECT IDA2A2 FROM STRINGDEFINITION D \n")
		    .append(" WHERE D.NAME = 'CADCategory') ) AS CATEGORY \n");
	}

	// 양산버전 : ManufacturingVersion
	sb.append(" ,(SELECT VALUE FROM STRINGVALUE WHERE EPM.IDA2A2 = IDA3A4 AND IDA3A6 = (SELECT IDA2A2 FROM STRINGDEFINITION D \n")
	        .append(" WHERE D.NAME = 'ManufacturingVersion') ) AS manufactureVer \n");

	// 보안등급 : Security
	sb.append(" ,(SELECT VALUE FROM STRINGVALUE WHERE EPM.IDA2A2 = IDA3A4 AND IDA3A6 = (SELECT IDA2A2 FROM STRINGDEFINITION D \n")
	        .append(" WHERE D.NAME = 'Security') ) AS security \n");

	// 작성부서 : CreateDeptName
	sb.append(
	        " ,(SELECT LISTAGG(CASE WHEN ROWNUM <=1 THEN U.CREATORDEPTNAME ELSE NULL END,',') WITHIN GROUP(ORDER BY U.CREATESTAMPA2\n")
	        .append(" DESC) FROM EDMUSERDATA U WHERE U.OBJBRANCHID = EPM.BRANCHIDITERATIONINFO ) AS CREATEDEPTNAME \n");
	// 상신일
	sb.append(" ,(SELECT TO_CHAR(H.REQUESTDATE, 'yyyy-MM-dd') FROM KETEpmApprovalHisLink HL, KETEpmApprovalHis H \n").append(
	        "   WHERE EPM.BRANCHIDITERATIONINFO = HL.BRANCHIDA3B5 AND HL.IDA3A5 = H.IDA2A2) AS requestDate \n");
	// 승인일
	sb.append(" ,(SELECT TO_CHAR(H.APPROVALDATE, 'yyyy-MM-dd') FROM KETEpmApprovalHisLink HL, KETEpmApprovalHis H \n").append(
	        "   WHERE EPM.BRANCHIDITERATIONINFO = HL.BRANCHIDA3B5 AND HL.IDA3A5 = H.IDA2A2) AS approvalDate \n");
	// 프로젝트 번호 : ProjectNo
	sb.append(" ,(SELECT LISTAGG(PM.PJTNO, ',') WITHIN GROUP(ORDER BY PM.PJTNO ) FROM EPMDOCPROJECTLINK PJT,E3PSPROJECTMASTER PM \n")
	        .append(" WHERE EPM.BRANCHIDITERATIONINFO = PJT.BRANCHIDA3B5 AND PJT.IDA3A5 = PM.IDA2A2) AS PROJECTNO \n");
	// 프로젝트 명 : ProjectName
	sb.append(" ,(SELECT LISTAGG(PM.PJTNAME, ',') WITHIN GROUP(ORDER BY PM.PJTNAME ) FROM EPMDOCPROJECTLINK PJT,E3PSPROJECTMASTER \n")
	        .append(" PM WHERE EPM.BRANCHIDITERATIONINFO = PJT.BRANCHIDA3B5 AND PJT.IDA3A5 = PM.IDA2A2) AS PROJECTNAME \n");

	// From
	sb.append("  FROM EPMDOCUMENTMASTER EM \n");
	sb.append("      ,EPMDOCUMENT EPM \n");
	if (dCondition.getCadTypeList().size() > 0)
	    sb.append("      ,STRINGVALUE SV_CAD_TYPE \n");
	if (dCondition.getDevStageList().size() > 0)
	    sb.append("      ,STRINGVALUE SV_DEV_STAGE \n");
	if (dCondition.getCadCategoryList().size() > 0)
	    sb.append("      ,STRINGVALUE SV_CAD_CATEGORY \n");

	// Where
	sb.append("  WHERE 1=1 \n");
	sb.append("  AND EM.IDA2A2 = EPM.IDA3MASTERREFERENCE \n");
	sb.append("  AND EPM.LATESTITERATIONINFO = 1 \n");
	sb.append("  AND EPM.STATECHECKOUTINFO NOT IN ('term','wrk','to del','to wrk') \n");
	// Sorting에 필요한 select column에 필요하거나, where 조건에 필요한 from절 > 관련 table 삽입 > 된 것의 where 절 추가
	if (dCondition.getCadTypeList().size() > 0) {
	    sb.append("  AND EPM.IDA2A2 = SV_CAD_TYPE.IDA3A4(+) AND '" + IBAStringDefinitionCache.getInstance().getCadAppType()
		    + "' = SV_CAD_TYPE.IDA3A6(+) \n");
	}

	if (dCondition.getDevStageList().size() > 0) {
	    sb.append("  AND EPM.IDA2A2 = SV_DEV_STAGE.IDA3A4(+) AND '" + IBAStringDefinitionCache.getInstance().getDevStage()
		    + "' = SV_DEV_STAGE.IDA3A6(+) \n");
	}

	if (dCondition.getCadCategoryList().size() > 0) {
	    sb.append("  AND EPM.IDA2A2 = SV_CAD_CATEGORY.IDA3A4(+) AND '" + IBAStringDefinitionCache.getInstance().getCadCategory()
		    + "' = SV_CAD_CATEGORY.IDA3A6(+) \n");
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 검색 조건 - 결과 내 재검색 loop
	for (Map<String, Object> condition : conditionList) {

	    map = KETParamMapUtil.getMap(condition);

	    // 사업부구분이 있는 경우
	    String[] businessType = map.getStringArray("businessType");
	    if (businessType != null && businessType.length > 0 && StringUtils.isNotEmpty(businessType[0])) {
		// 사업구분
		// sb.append("  AND EPM.BRANCHIDITERATIONINFO IN ( SELECT T.BRANCHIDA3B5 FROM EPMDOCTYPECODELINK T WHERE  T.IDA3A5 IN ('19734', '19735')) \n");
		sb.append("    AND EPM.BRANCHIDITERATIONINFO IN ( SELECT T.BRANCHIDA3B5 FROM EPMDOCTYPECODELINK T WHERE ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("T.IDA3A5",
		                KETParamMapUtil.OidToString(KETParamMapUtil.toString(businessType)), false)).append(" ) \n");
	    }

	    // 부품번호가 있는 경우
	    String[] partNumberArray = map.getStringArray("partNumber");

	    if (partNumberArray != null && partNumberArray.length > 0 && StringUtils.isNotEmpty(partNumberArray[0])) {

		// 부품번호
		boolean hasPercent = false;
		for (String element : partNumberArray) {
		    if (StringUtils.isNotEmpty(element) && element.indexOf("*") != -1) {
			hasPercent = true;
			break;
		    }
		}

		if (hasPercent) {

		    sb.append(
			    "    AND EM.IDA2A2 IN ( SELECT L.IDA3A5 FROM EPMLINK L, WTPARTMASTER PMASTER WHERE L.IDA3B5 = PMASTER.IDA2A2 AND ")
			    .append(KETQueryUtil.getSqlQueryForMultiSearch("PMASTER.WTPARTNUMBER",
			            KETParamMapUtil.toString(partNumberArray), false)).append(" ) \n");
		} else {

		    String firstPartInParam = "";
		    for (String element : partNumberArray) {
			if (StringUtils.isNotEmpty(element)) {
			    element = element.trim();
			    if ("".equals(firstPartInParam)) {
				firstPartInParam = "'" + element + "'";
			    } else {
				firstPartInParam = firstPartInParam + "," + "'" + element + "'";
			    }
			}
		    }

		    EPMPartMatcher matcher = new EPMPartMatcher();
		    List<String> matchEpmNoList = matcher.getPartMatchEpmNo(partNumberArray);

		    String secondPartInParam = "";
		    for (String element : matchEpmNoList) {
			if (StringUtils.isNotEmpty(element)) {
			    element = element.trim();
			    if ("".equals(secondPartInParam)) {
				secondPartInParam = "'" + element + "'";
			    } else {
				secondPartInParam = secondPartInParam + "," + "'" + element + "'";
			    }
			}
		    }

		    sb.append("    AND EM.IDA2A2 IN ( ");
		    sb.append("    	SELECT L.IDA3A5  \n");
		    sb.append("    	FROM EPMLINK L, WTPARTMASTER PMASTER  \n");
		    sb.append("    	WHERE L.IDA3B5 = PMASTER.IDA2A2  \n");
		    sb.append("    	AND PMASTER.WTPARTNUMBER IN ( " + firstPartInParam + " ) \n");
		    sb.append("   	UNION ALL \n");
		    sb.append("    	SELECT IDA2A2 \n");
		    sb.append("    	FROM EPMDOCUMENTMASTER \n");
		    sb.append("    	WHERE DOCUMENTNUMBER IN ( " + secondPartInParam + " ) \n");
		    sb.append("    ) ");
		}
	    }

	    // 부품 MastOid 가 있는 경우
	    String[] partNumberMasterOid = map.getStringArray("partNumberMasterOid");
	    if (partNumberMasterOid != null && partNumberMasterOid.length > 0
		    && !(partNumberMasterOid.length == 1 && partNumberMasterOid[0].equals(""))) {
		// 부품번호
		sb.append(
		        "    AND EM.IDA2A2 IN ( SELECT L.IDA3A5 FROM EPMLINK L, WTPARTMASTER PMASTER WHERE L.IDA3B5 = PMASTER.IDA2A2 AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("PMASTER.IDA2A2",
		                KETParamMapUtil.OidToString(KETParamMapUtil.toString(partNumberMasterOid)), false)).append(" ) \n");
	    }

	    // 도면명이 있는 경우
	    if (StringUtil.checkString(map.getString("name"))) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("EM.NAME", map.getString("name"), true))
		        .append("     \n");
	    }

	    // 도면번호가 있는 경우
	    if (StringUtil.checkString(map.getString("number"))) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("EM.DOCUMENTNUMBER", map.getString("number").toUpperCase(), false))
		        .append(" \n");
	    }

	    // 작성일자(from)가 있는 경우
	    if (StringUtil.checkString(map.getString("create_start"))) {
		String create_start = map.getString("create_start");
		create_start = create_start.substring(0, 4) + create_start.substring(5, 7) + create_start.substring(8, 10);
		sb.append("    AND EPM.CREATESTAMPA2 >= TO_DATE('" + create_start + "' || '000000','YYYYMMDDHH24MISS')         \n");
	    }

	    // 작성일자(to)가 있는 경우
	    if (StringUtil.checkString(map.getString("create_end"))) {
		String create_end = map.getString("create_end");
		create_end = create_end.substring(0, 4) + create_end.substring(5, 7) + create_end.substring(8, 10);
		sb.append("    AND EPM.CREATESTAMPA2 <= TO_DATE('" + create_end + "' || '235959','YYYYMMDDHH24MISS')           \n");
	    }

	    // 작성자가 있는 경우
	    String creator = map.getString("creator");
	    if (creator != null && creator.length() > 0) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("EPM.IDA3D2ITERATIONINFO", KETParamMapUtil.OidToString(creator),
		                false)).append(" \n");
	    }

	    // 작성부서가 있는 경우
	    String edmCreateDept = map.getString("edmCreateDeptName");
	    if (edmCreateDept != null && edmCreateDept.length() > 0) {
		sb.append("    AND EPM.BRANCHIDITERATIONINFO IN ( SELECT OBJBRANCHID FROM EDMUserData WHERE 1=1 AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("CREATORDEPTNAME", edmCreateDept, false)).append(" ) \n");
	    }

	    // 최신 버전일 경우
	    String latest = map.getString("latest");
	    if (latest.length() == 0) {
		latest = String.valueOf(true);
	    }
	    if (Boolean.parseBoolean(latest)) {
		// 최신으로 검색
		// sb.append("  AND NOT EXISTS (SELECT NULL \n");
		// sb.append("                    FROM EPMDOCUMENT E \n");
		// sb.append("                   WHERE E.IDA3MASTERREFERENCE = EPM.IDA3MASTERREFERENCE \n");
		// sb.append("                     AND E.STATECHECKOUTINFO NOT IN ('term','wrk','to del','to wrk') \n");
		// sb.append("                     AND E.IDA3C2ITERATIONINFO = EPM.IDA2A2 ) \n");
		sb.append("  AND EPM.VERSIONIDA2VERSIONINFO = ( SELECT TO_CHAR(MAX(TO_NUMBER(E.VERSIONIDA2VERSIONINFO))) \n");
		sb.append("					FROM EPMDOCUMENT E \n");
		sb.append("					WHERE E.IDA3MASTERREFERENCE = EPM.IDA3MASTERREFERENCE \n");
		sb.append("					AND E.STATECHECKOUTINFO NOT IN ('term','wrk','to del','to wrk') \n");
		sb.append("					AND E.LATESTITERATIONINFO = 1 )  \n");
	    }

	    // 프로젝트번호가 있는 경우
	    String projectNo = map.getString("projectNo");
	    if (projectNo != null && projectNo.length() > 0) {
		// 프로젝트번호
		// sb.append("  AND EPM.BRANCHIDITERATIONINFO IN (SELECT PJT.BRANCHIDA3B5 FROM EPMDOCPROJECTLINK PJT,E3PSPROJECTMASTER PM WHERE PJT.IDA3A5 = PM.IDA2A2 AND PM.PJTNO = 'A11B014' ) \n");
		sb.append("    AND EPM.BRANCHIDITERATIONINFO IN (SELECT PJT.BRANCHIDA3B5 FROM EPMDOCPROJECTLINK PJT,E3PSPROJECTMASTER PM ")
		        .append(" WHERE PJT.IDA3A5 = PM.IDA2A2 AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("PM.PJTNO", projectNo.toUpperCase(), false)).append(" ) \n");
	    }

	    // 상태가 있는 경우
	    String[] state = map.getStringArray("state");
	    if (state != null && state.length > 0 && StringUtils.isNotEmpty(state[0])) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("EPM.STATESTATE", state, false)).append("        \n");
	    }

	    // CAD종류가 있는 경우
	    String[] cadAppType = map.getStringArray(EDMHelper.IBA_CAD_APP_TYPE);
	    if (cadAppType != null && cadAppType.length > 0 && StringUtils.isNotEmpty(cadAppType[0])) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("SV_CAD_TYPE.VALUE", cadAppType, false, true))
		        .append(" \n");
	    }

	    // 도면구분이 있는 경우
	    String[] devStageAry = map.getStringArray(EDMHelper.IBA_DEV_STAGE);
	    if (devStageAry != null && devStageAry.length > 0 && StringUtils.isNotEmpty(devStageAry[0])) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("SV_DEV_STAGE.VALUE", devStageAry, false))
		        .append(" \n");
	    }

	    // 도면유형이 있는 경우
	    String[] categoryAry = map.getStringArray(EDMHelper.IBA_CAD_CATEGORY);
	    if (categoryAry != null && categoryAry.length > 0 && StringUtils.isNotEmpty(categoryAry[0])) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("SV_CAD_CATEGORY.VALUE", categoryAry, false))
		        .append(" \n");
	    }
	}

	Kogger.debug(getClass(), "######## 도면 검색 쿼리 3 : \n");

	return sb.toString();
    }
}
