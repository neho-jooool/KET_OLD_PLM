package ext.ket.dms.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import wt.content.ApplicationData;
import wt.content.ContentServerHelper;
import wt.content.FormatContentHolder;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.MethodContext;
import wt.part.WTPart;
import wt.pds.StatementSpec;
import wt.pom.DBProperties;
import wt.pom.WTConnection;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.KeywordExpression;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETDocumentPartLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.service.KETDmsHelper;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.entity.KETProdECADocLink;
import e3ps.qms.util.QMSDBUtil;
import ext.ket.common.files.util.DownloadView;
import ext.ket.common.files.util.FileContentUtil;
import ext.ket.common.files.util.FileRenamePolicy;
import ext.ket.common.util.ObjectUtil;
import ext.ket.edm.stamping.util.ZipUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.util.SearchUtil;

/**
 * @클래스명 : KETProjectDocumentHelper
 * @작성자 : Jason, Han
 * @작성일 : 2014. 7. 24.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class KETProjectDocumentHelper {

    public static final KETProjectDocumentService service = ServiceFactory.getService(KETProjectDocumentService.class);

    public final static KETProjectDocumentHelper manager = new KETProjectDocumentHelper();

    public boolean isLeafDocCategory(String devDocCagegoryCode) throws Exception {

	QuerySpec spec = new QuerySpec(KETDocumentCategory.class);
	spec.appendWhere(new SearchCondition(KETDocumentCategory.class, KETDocumentCategory.PARENT_CATEGORY_CODE, "=", devDocCagegoryCode),
	        new int[] { 0 });
	QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);
	return (result == null || result.size() == 0);
    }

    public KETDocumentCategory getDocumentCategory(String devDocCagegoryCode) throws Exception {

	KETDocumentCategory category = null;
	QuerySpec spec = new QuerySpec(KETDocumentCategory.class);
	spec.appendWhere(new SearchCondition(KETDocumentCategory.class, KETDocumentCategory.CATEGORY_CODE, "=", devDocCagegoryCode),
	        new int[] { 0 });
	QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);
	if (result.hasMoreElements()) {
	    category = (KETDocumentCategory) result.nextElement();
	}
	return category;
    }

    public String isDisabledCategory(String devDocCagegoryCode) throws Exception {
	QuerySpec q = new QuerySpec(KETDocumentCategory.class);
	q.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", devDocCagegoryCode), new int[] { 0 });
	QueryResult qre = PersistenceHelper.manager.find(q);
	KETDocumentCategory docCate = null;

	while (qre.hasMoreElements()) {
	    docCate = (KETDocumentCategory) qre.nextElement();
	}

	String msg = "";
	String CateName = docCate.getCategoryName();
	if (!docCate.getIsUsed()) {

	    msg = "해당 분류체계는 폐기되었습니다.\n\n";

	    if ("DR회의록".equals(CateName)) {
		msg += "1. DR회의록(Requirement Check Sheet)\n2. DR회의록(Design Review Check Sheet)\n3. 시작회의록(추가금형)\n\n 중 하나를 선택하시기 바랍니다.";
	    }

	    if ("생산준비일정계획서".equals(CateName) || "생산준비종료계획서".equals(CateName) || "양산검증체크시트".equals(CateName)) {
		msg += "1. DR회의록(Requirement Check Sheet)\n2. DR회의록(Design Review Check Sheet)\n\n 중 하나를 선택하세요";
	    }

	}

	return msg;

    }

    public KETProjectDocument getLastestDocument(String docNo) throws Exception {
	return getLastestDocument(docNo, false);
    }

    @SuppressWarnings("deprecation")
    public KETProjectDocument getLastestDocument(String docNo, boolean isApproved) throws Exception {

	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(KETProjectDocument.class, true);

	SearchUtil.appendEQUAL(qs, KETProjectDocument.class, KETProjectDocument.NUMBER, docNo, idx);
	SearchUtil.appendBOOLEAN(qs, KETProjectDocument.class, "iterationInfo.latest", SearchCondition.IS_TRUE, idx);

	String alias = qs.getFromClause().getAliasAt(idx);
	String subQs = "(SELECT MAX(VERSIONIDA2VERSIONINFO) FROM KETPROJECTDOCUMENT WHERE IDA3MASTERREFERENCE=" + alias
	        + ".IDA3MASTERREFERENCE ";

	if (isApproved) {
	    subQs += "AND STATESTATE='APPROVED')";
	} else {
	    subQs += ")";
	}

	KeywordExpression kexp = new KeywordExpression(alias + "." + "VERSIONIDA2VERSIONINFO");
	KeywordExpression kexp2 = new KeywordExpression(subQs);
	SearchCondition sc = new SearchCondition(kexp, SearchCondition.EQUAL, kexp2);
	qs.appendAnd();
	qs.appendWhere(sc, new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(qs);

	if (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    return (KETProjectDocument) o[0];
	}

	return null;
    }

    @SuppressWarnings("deprecation")
    public List<KETProjectDocument> getPartRefDocumentList(String partNo, String[] categoryValue, boolean isApproved, boolean isCategoryCode)
	    throws Exception {
	List<KETProjectDocument> list = new ArrayList<KETProjectDocument>();

	if (categoryValue == null) {
	    return list;
	}

	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(KETProjectDocument.class, true);
	int idx1 = qs.addClassList(KETDocumentPartLink.class, false);
	int idx2 = qs.addClassList(KETDocumentCategoryLink.class, false);
	int idx3 = qs.addClassList(KETDocumentCategory.class, true);

	qs.setAdvancedQueryEnabled(true);

	ClassAttribute ca = new ClassAttribute(KETDocumentPartLink.class, "roleAObjectRef.key.id");
	ClassAttribute ca2 = new ClassAttribute(KETProjectDocument.class, "thePersistInfo.theObjectIdentifier.id");
	SearchCondition sc = new SearchCondition(ca, SearchCondition.EQUAL, ca2);
	sc.setFromIndicies(new int[] { idx1, idx }, 0);
	sc.setOuterJoin(idx);
	qs.appendWhere(sc, new int[] { idx1, idx });

	ca = new ClassAttribute(KETDocumentCategoryLink.class, "roleBObjectRef.key.id");
	ca2 = new ClassAttribute(KETProjectDocument.class, "thePersistInfo.theObjectIdentifier.id");
	sc = new SearchCondition(ca, SearchCondition.EQUAL, ca2);
	sc.setFromIndicies(new int[] { idx2, idx }, 0);
	sc.setOuterJoin(idx);
	qs.appendAnd();
	qs.appendWhere(sc, new int[] { idx2, idx });

	ca = new ClassAttribute(KETDocumentCategoryLink.class, "roleAObjectRef.key.id");
	ca2 = new ClassAttribute(KETDocumentCategory.class, "thePersistInfo.theObjectIdentifier.id");
	sc = new SearchCondition(ca, SearchCondition.EQUAL, ca2);
	sc.setFromIndicies(new int[] { idx2, idx3 }, 0);
	sc.setOuterJoin(idx3);
	qs.appendAnd();
	qs.appendWhere(sc, new int[] { idx2, idx3 });

	if (isCategoryCode) {
	    ca = new ClassAttribute(KETDocumentCategory.class, KETDocumentCategory.CATEGORY_CODE);
	} else {
	    ca = new ClassAttribute(KETDocumentCategory.class, KETDocumentCategory.CATEGORY_NAME);
	}
	sc = new SearchCondition(ca, SearchCondition.IN, new ArrayExpression(categoryValue));
	qs.appendAnd();
	qs.appendWhere(sc, new int[] { idx3 });

	sc = new SearchCondition(KETDocumentPartLink.class, KETDocumentPartLink.PART_NO, SearchCondition.EQUAL, partNo);
	qs.appendAnd();
	qs.appendWhere(sc, new int[] { idx1 });

	// 최신버전
	String alias = qs.getFromClause().getAliasAt(idx);
	String subQs = "(SELECT MAX(TO_NUMBER(VERSIONIDA2VERSIONINFO)) FROM KETPROJECTDOCUMENT WHERE IDA3MASTERREFERENCE=" + alias
	        + ".IDA3MASTERREFERENCE ";

	if (isApproved) {
	    subQs += "AND STATESTATE='APPROVED')";
	} else {
	    subQs += ")";
	}

	KeywordExpression kexp = new KeywordExpression(alias + "." + "VERSIONIDA2VERSIONINFO");
	KeywordExpression kexp2 = new KeywordExpression(subQs);
	sc = new SearchCondition(kexp, SearchCondition.EQUAL, kexp2);
	qs.appendAnd();
	qs.appendWhere(sc, new int[] { idx });

	SearchUtil.appendBOOLEAN(qs, KETProjectDocument.class, "iterationInfo.latest", SearchCondition.IS_TRUE, idx);
	SearchUtil.setOrderBy(qs, KETProjectDocument.class, idx, KETProjectDocument.CREATE_TIMESTAMP, true);
	SearchUtil.setOrderBy(qs, KETProjectDocument.class, 0, "versionInfo.identifier.versionSortId", true);

	QueryResult qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    KETProjectDocument doc = (KETProjectDocument) o[0];
	    KETDocumentCategory category = (KETDocumentCategory) o[1];

	    doc.setAttribute10(category.getCategoryName());
	    list.add(doc);
	}

	return list;

    }

    public List<KETProjectDocument> getPartRefDocumentList(String partNo, String categoryValue, boolean isApproved, boolean isCategoryCode)
	    throws Exception {
	String[] categoryValueArr = { categoryValue };
	return getPartRefDocumentList(partNo, categoryValueArr, isApproved, isCategoryCode);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> chkDupMatPart(Map<String, Object> reqMap) throws Exception {

	String currentDocNo = (String) reqMap.get("currentDocNo");
	String categoryCode = (String) reqMap.get("categoryCode");
	String relatedPartOid = (String) reqMap.get("relatedPartOid");
	List<String> partNoList = (List<String>) reqMap.get("partNoList");

	KETDocumentCategory category = KETDmsHelper.service.selectDocCategory(categoryCode);

	List<String> dupList = new ArrayList<String>();

	if ("Y".equals(category.getAttribute5())) {

	    if (StringUtil.checkString(relatedPartOid)) {
		WTPart relatedPart = (WTPart) CommonUtil.getObject(relatedPartOid);
		String relatedPartNo = relatedPart.getNumber();

		List<KETProjectDocument> list = getPartRefDocumentList(relatedPartNo, categoryCode, false, true);

		for (KETProjectDocument doc : list) {
		    if (doc != null && !doc.getNumber().equals(currentDocNo)) {
			dupList.add(relatedPartNo + " : " + doc.getNumber());
		    }
		}
	    }

	    for (String partNo : partNoList) {

		List<KETProjectDocument> list = getPartRefDocumentList(partNo, categoryCode, false, true);

		for (KETProjectDocument doc : list) {
		    if (doc != null && !doc.getNumber().equals(currentDocNo)) {
			dupList.add(partNo + " : " + doc.getNumber());
		    }
		}
	    }
	}

	Map<String, Object> resMap = new HashMap<String, Object>();
	resMap.put("isDupMatChk", "Y".equals(category.getAttribute5()));
	resMap.put("dupList", dupList);

	return resMap;
    }

    public List<Map<String, Object>> pPapDocSearchList(Map<String, Object> reqMap) throws Exception {

	String tempPartNo = (String) reqMap.get("partNo");
	String tempCategoryName = (String) reqMap.get("categoryName");

	List<Map<String, Object>> bomList = new ArrayList<Map<String, Object>>();

	if (StringUtil.checkString(tempPartNo)) {

	    tempPartNo = StringUtils.upperCase(tempPartNo);

	    String[] partNoArr = tempPartNo.trim().split(",");
	    String[] categoryNameArr = null;

	    if (StringUtil.checkString(tempCategoryName)) {
		categoryNameArr = tempCategoryName.trim().split(",");
	    }

	    List<WTPart> partList = new ArrayList<WTPart>();
	    List<String> rootPartSortList = new ArrayList<String>();

	    for (String partNo : partNoArr) {
		if (!StringUtil.checkString(partNo)) {
		    continue;
		}
		WTPart rootPart = PartBaseHelper.service.getLatestPart(partNo);
		partList.add(rootPart);
		rootPartSortList.add(partNo);
	    }

	    Collections.sort(rootPartSortList);// 오름차순 정렬

	    bomList = getPPAPDocFromPartBOM(partList, categoryNameArr, rootPartSortList);
	    FileContentUtil.manager.saveDownloadHistory("PPAP자료 검색", tempPartNo);

	}

	return bomList;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getPPAPDocFromPartBOM(List<WTPart> partList, String[] categoryNameArr, List<String> rootPartSortList)
	    throws Exception {

	List<Map<String, Object>> bomList = new ArrayList<Map<String, Object>>();

	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;
	PreparedStatement pstmt = null;
	try {
	    conn = (WTConnection) mContext.getConnection();

	    String partIds = "";
	    for (WTPart part : partList) {

		if (part == null) {
		    continue;
		}

		partIds += String.valueOf(CommonUtil.getOIDLongValue(part)) + ",";
	    }
	    partIds = StringUtils.removeEnd(partIds, ",");

	    if (!StringUtil.checkString(partIds)) {
		return bomList;
	    }

	    // -- F:제픔 , H:반제품, P:포장재, S:스크랩, D:금형, M:금형부품,W:상품, R:원자재 (S, K 제외 처리)
	    StringBuffer sql = new StringBuffer();

	    sql.append(" SELECT  LEV,																																										");
	    sql.append("         BOM.PARTNO,                                                                                                                                                                ");
	    sql.append("         NAME AS PARTNAME,                                                                                                                                                          ");
	    sql.append("         BOM.PARENTITEMCODE,                                                                                                                                                        ");
	    sql.append("         BOM.PARENT_WTPART_OID,                                                                                                                                                     ");
	    sql.append("         BOM.WTMASER_OID,                                                                                                                                                           ");
	    sql.append("         BOM.VERSION_WTPART_OID,                                                                                                                                                    ");
	    sql.append("         LAST_WTPART_OID ,                                                                                                                                                          ");
	    sql.append("         (SELECT NAME FROM NUMBERCODE WHERE CODE = PTC_STR_147TYPEINFOWTPART                                                                                                        ");
	    sql.append("             AND CODETYPE = 'MATERIALMAKER') AS MATERIALMAKER,                                                                                                                      ");
	    sql.append("         PTC_STR_71TYPEINFOWTPART AS MANUFACTURER,                                                                                                                                  ");
	    sql.append("         (SELECT PARTNERNAME FROM KETPARTNER WHERE PARTNERNO = PTC_STR_13TYPEINFOWTPART                                                                                             ");
	    sql.append("           UNION SELECT NAME FROM NUMBERCODE WHERE CODE = PTC_STR_13TYPEINFOWTPART AND CODETYPE = 'PRODUCTIONDEPT') AS PRODUCTION,                                                  ");
	    sql.append("         PTC_STR_3TYPEINFOWTPART AS PARTTYPE,                                                                                                                                       ");
	    sql.append("         B.IDA2A2 AS PARTMASTEROID                                                                                                                                                  ");
	    sql.append("   FROM (                                                                                                                                                                           ");
	    sql.append("         SELECT LEVEL AS LEV,                                                                                                                                                       ");
	    sql.append("                BOM.PARTNO,                                                                                                                                                         ");
	    sql.append("                BOM.PARENTITEMCODE,                                                                                                                                                 ");
	    sql.append("                BOM.PARENT_WTPART_OID,                                                                                                                                              ");
	    sql.append("                BOM.WTMASER_OID,                                                                                                                                                    ");
	    sql.append("                BOM.VERSION_WTPART_OID,                                                                                                                                             ");
	    sql.append("                LAST_WTPART_OID                                                                                                                                                     ");
	    sql.append("           FROM (                                                                                                                                                                   ");
	    sql.append("                  SELECT WTPARTNUMBER AS PARTNO, '' AS PARENTITEMCODE,                                                                                                              ");
	    sql.append("                         0 AS ITEMSEQ, 0 AS PARENT_WTPART_OID,0 AS WTMASER_OID, '0' AS VERSION_WTPART_OID,                                                                          ");
	    sql.append("                         P.IDA2A2 AS LAST_WTPART_OID FROM WTPART P, WTPARTMASTER M                                                                                                  ");
	    sql.append("                   WHERE P.IDA3MASTERREFERENCE = M.IDA2A2                                                                                                                           ");
	    sql.append("	                AND P.IDA2A2 IN (" + partIds
		    + ")                                                  					 	                                ");
	    sql.append("               UNION ALL                                                                                                                                                            ");
	    sql.append("                  SELECT B.CHILDITEMCODE AS PARTNO ,B.PARENTITEMCODE ,B.ITEMSEQ ,B.IDA3A5  AS PARENT_WTPART_OID, B.IDA3B5  AS WTMASER_OID ,B.VERSIONITEMCODE AS VERSION_WTPART_OID  ");
	    sql.append("                        ,(SELECT MAX(P.IDA2A2) FROM WTPART P WHERE P.LATESTITERATIONINFO = 1  AND P.STATECHECKOUTINFO != 'wrk'                                                      ");
	    sql.append("                             AND P.STATESTATE = 'APPROVED' AND P.IDA3MASTERREFERENCE = B.IDA3B5 ) AS LAST_WTPART_OID                                                                ");
	    sql.append("                    FROM KETPARTUSAGELINK B ) BOM                                                                                                                                   ");
	    sql.append("              START WITH BOM.PARENT_WTPART_OID  = 0                                                                                                                                 ");
	    sql.append("        CONNECT BY PRIOR BOM.LAST_WTPART_OID =  BOM.PARENT_WTPART_OID                                                                                                               ");
	    sql.append("       ORDER SIBLINGS BY ITEMSEQ                                                                                                                                                    ");
	    sql.append("       ) BOM, WTPART A, WTPARTMASTER B                                                                                                                                              ");
	    sql.append(" WHERE BOM.LAST_WTPART_OID = A.IDA2A2                                                                                                                                               ");
	    sql.append("   AND A.IDA3MASTERREFERENCE = B.IDA2A2                                                                                                                                             ");

	    // sql.append("	SELECT LEVEL AS LEV,                                                                                     ");
	    // sql.append("	BOM.PARTNO,                                                                                              ");
	    // sql.append("	NAME AS PARTNAME,                                                                                        ");
	    // sql.append("	BOM.PARENTITEMCODE,                                                                                      ");
	    // sql.append("	BOM.PARENT_WTPART_OID,                                                                                   ");
	    // sql.append("	BOM.WTMASER_OID,                                                                                         ");
	    // sql.append("	BOM.VERSION_WTPART_OID,                                                                                  ");
	    // sql.append("	LAST_WTPART_OID,                                                                                         ");
	    // sql.append("	(SELECT NAME FROM NUMBERCODE WHERE CODE = PTC_STR_147TYPEINFOWTPART                                      ");
	    // sql.append("	AND CODETYPE = 'MATERIALMAKER') AS MATERIALMAKER, PTC_STR_71TYPEINFOWTPART AS MANUFACTURER,              ");
	    // sql.append("	(SELECT PARTNERNAME FROM KETPARTNER WHERE PARTNERNO = PTC_STR_13TYPEINFOWTPART                           ");
	    // sql.append("	UNION SELECT NAME FROM NUMBERCODE                                                                        ");
	    // sql.append("	WHERE CODE = PTC_STR_13TYPEINFOWTPART AND CODETYPE = 'PRODUCTIONDEPT') AS PRODUCTION,                    ");
	    // sql.append("	PTC_STR_3TYPEINFOWTPART AS PARTTYPE,                                                                     ");
	    // sql.append("	B.IDA2A2 AS PARTMASTEROID                                                                                ");
	    // sql.append("	FROM ( SELECT WTPARTNUMBER AS PARTNO, '' AS PARENTITEMCODE,                                              ");
	    // sql.append("	0 AS ITEMSEQ, 0 AS PARENT_WTPART_OID,0 AS WTMASER_OID, '0' AS VERSION_WTPART_OID,                        ");
	    // sql.append("	P.IDA2A2 AS LAST_WTPART_OID FROM WTPART P, WTPARTMASTER M                                                ");
	    // sql.append("	WHERE P.IDA3MASTERREFERENCE = M.IDA2A2                                                                   ");
	    // sql.append("	AND P.IDA2A2 IN (" + partIds + ")                                                  					 	 ");
	    // sql.append("	UNION ALL                                                                                                ");
	    // sql.append("	SELECT B.CHILDITEMCODE AS PARTNO ,B.PARENTITEMCODE ,B.ITEMSEQ ,B.IDA3A5  AS PARENT_WTPART_OID,           ");
	    // sql.append("	B.IDA3B5  AS WTMASER_OID ,B.VERSIONITEMCODE AS VERSION_WTPART_OID                                        ");
	    // sql.append("	,(SELECT MAX(P.IDA2A2) FROM WTPART P WHERE P.LATESTITERATIONINFO = 1  AND P.STATECHECKOUTINFO != 'wrk'   ");
	    // sql.append("	 AND P.STATESTATE = 'APPROVED' AND P.IDA3MASTERREFERENCE = B.IDA3B5 ) AS LAST_WTPART_OID                 ");
	    // sql.append("	FROM KETPARTUSAGELINK B ) BOM, WTPART A, WTPARTMASTER B                                                  ");
	    // sql.append("	WHERE BOM.LAST_WTPART_OID = A.IDA2A2                                                                     ");
	    // sql.append("	AND A.IDA3MASTERREFERENCE = B.IDA2A2                                                                     ");
	    // sql.append("	START WITH BOM.PARENT_WTPART_OID  = 0                                                                    ");
	    // sql.append("	CONNECT BY PRIOR BOM.LAST_WTPART_OID =  BOM.PARENT_WTPART_OID                                            ");
	    // sql.append("	ORDER SIBLINGS BY ITEMSEQ                                                                                ");

	    pstmt = conn.prepareStatement(sql.toString());
	    rs = pstmt.executeQuery();

	    List<Map<String, Object>> rsList = ObjectUtil.manager.rsToList(rs);

	    for (String sortPartRootNo : rootPartSortList) {
		pPopDataGenereateByBom(rsList, bomList, sortPartRootNo, categoryNameArr);
	    }

	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}

	return bomList;
    }

    public void pPopDataGenereateByBom(List<Map<String, Object>> rsList, List<Map<String, Object>> bomList, String sortPartRootNo,
	    String categoryNameArr[]) throws Exception {

	String ROOTPARTNO = "";

	boolean goFlag = true;

	for (Map<String, Object> bom : rsList) {
	    Map<String, Object> partMap = new HashMap<String, Object>();

	    String PARTTYPE = (String) bom.get("PARTTYPE");
	    if ("S".equals(PARTTYPE) || "K".equals(PARTTYPE))
		continue;

	    String PARTNO = (String) bom.get("PARTNO");
	    String LEV = (String) bom.get("LEV");
	    String PARTNAME = (String) bom.get("PARTNAME");
	    String PARTOID = WTPart.class.getName() + ":" + (String) bom.get("LAST_WTPART_OID");
	    String MATERIALMAKER = StringUtil.checkNull((String) bom.get("MATERIALMAKER"));
	    String MANUFACTURER = StringUtil.checkNull((String) bom.get("MANUFACTURER"));
	    String PRODUCTION = StringUtil.checkNull((String) bom.get("PRODUCTION"));
	    String PARENTPARTNO = StringUtil.checkNull((String) bom.get("PARENTITEMCODE"));

	    if ("1".equals(LEV) && !PARTNO.equals(sortPartRootNo)) {
		goFlag = false;
	    }

	    if ("1".equals(LEV) && PARTNO.equals(sortPartRootNo)) {
		goFlag = true;
	    }

	    if (!goFlag) {
		continue;
	    }

	    if ("1".equals(LEV)) {
		ROOTPARTNO = PARTNO;
	    }

	    if (StringUtil.checkString(MANUFACTURER)) {
		if (StringUtil.checkString(MATERIALMAKER)) {
		    MATERIALMAKER += "," + MANUFACTURER;
		} else {
		    MATERIALMAKER += MANUFACTURER;
		}

	    }
	    if (StringUtil.checkString(PRODUCTION)) {
		if (StringUtil.checkString(MATERIALMAKER)) {
		    MATERIALMAKER += "," + PRODUCTION;
		} else {
		    MATERIALMAKER += PRODUCTION;
		}
	    }
	    partMap.put("LEV", LEV);
	    partMap.put("PARTOID", PARTOID);
	    partMap.put("PARTNO", PARTNO);
	    partMap.put("PARTNAME", PARTNAME);
	    partMap.put("PARENTPARTNO", PARENTPARTNO);
	    partMap.put("ROOTPARTNO", ROOTPARTNO);

	    if ("R".equals(PARTTYPE)) {
		partMap.put("MATERIALMAKER", MATERIALMAKER);
	    }

	    List<KETProjectDocument> docList = getPartRefDocumentList(PARTNO, categoryNameArr, true, false);

	    Map<String, Object> catDocMap = new HashMap<String, Object>();

	    for (KETProjectDocument doc : docList) {

		FormatContentHolder holder = (FormatContentHolder) doc;
		ContentDTO dto = KETContentHelper.manager.getPrimaryContent(holder);
		String categoryName = doc.getAttribute10();

		if ("재질성적서".equals(categoryName) || ("중금속성적서".equals(categoryName))) { // 해당 category의 문서는 KQIS에서 추출한다
		    continue;
		}

		if (getCFNCategory().contains(categoryName)) {

		    String fileName = PARTNO + "_" + categoryName + "_" + dto.getName();
		    if (StringUtil.checkString(PARENTPARTNO)) {
			fileName = PARENTPARTNO + "_" + categoryName + "_" + dto.getName();
		    }

		    dto.setName(fileName);
		    dto.setDownURLStr(dto.getDownURLStr() + "&fileName=" + fileName);
		}

		Map<String, Object> docMap = ObjectUtil.manager.converObjectToMap(dto);
		List<Map<String, Object>> docFiles = (List<Map<String, Object>>) catDocMap.get(doc.getAttribute10());

		if (docFiles == null) {
		    docFiles = new ArrayList<Map<String, Object>>();
		}

		docMap.put("docOid", CommonUtil.getOIDString(doc));
		docMap.put("docNo", doc.getNumber());
		docMap.put("docName", doc.getName());
		docFiles.add(docMap);

		// 정기신뢰성성적서
		if ("정기신뢰성성적서".equals(categoryName)) {

		    catDocMap.put(categoryName, docFiles);// 주첨부파일이 담긴 list를 catDocMap에 미리 담아둔다 아래 세컨더리 로직 돌면서 초기화 되는 현상을 막기 위해

		    List<ContentDTO> dtoList = KETContentHelper.manager.getSecondaryContents(holder);

		    for (ContentDTO secondary : dtoList) {

			if (getCFNCategory().contains(categoryName)) {

			    String fileName = PARTNO + "_" + categoryName + "_" + secondary.getName();
			    secondary.setName(fileName);
			    secondary.setDownURLStr(secondary.getDownURLStr() + "&fileName=" + fileName);

			}

			docMap = ObjectUtil.manager.converObjectToMap(secondary);
			docFiles = (List<Map<String, Object>>) catDocMap.get(doc.getAttribute10());

			if (docFiles == null) {
			    docFiles = new ArrayList<Map<String, Object>>();
			}

			docMap.put("docOid", CommonUtil.getOIDString(doc));
			docMap.put("docNo", doc.getNumber());
			docFiles.add(docMap);
		    }
		}

		catDocMap.put(categoryName, docFiles);

	    }
	    // 재질성적서, 중금속성적서를 kqis 에서 찾는 로직 start
	    Connection conn = null;

	    try {
		// connection creation
		conn = QMSDBUtil.getConnection();

		List<Map<String, Object>> docFiles = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> docFiles2 = new ArrayList<Map<String, Object>>();

		for (String categoryName : categoryNameArr) {
		    if ("재질성적서".equals(categoryName) || "중금속성적서".equals(categoryName)) {

			List<Map<String, String>> fileInfoList = getQmsFileInfo(categoryName, PARTNO, conn);

			for (Map<String, String> fileInfo : fileInfoList) {

			    Map<String, Object> docMap = new HashMap<String, Object>();

			    String qmsFileName = fileInfo.get("fileName");
			    String filePath = fileInfo.get("filePath");

			    if (StringUtils.isEmpty(qmsFileName)) {
				continue;
			    }

			    String fileName = PARTNO + "_" + categoryName + "_" + qmsFileName;

			    if (StringUtil.checkString(PARENTPARTNO)) {
				fileName = PARENTPARTNO + "_" + categoryName + "_" + qmsFileName;
			    }

			    fileName = FileContentUtil.manager.getValidFileName(fileName);// 사용할 수 없는 특수문자 체크

			    docMap.put("name", fileName);
			    docMap.put("filePath", filePath);
			    docMap.put("downURLStr", "/plm/ext/copyDownload?path=" + filePath + "&name=" + fileName);

			    if ("재질성적서".equals(categoryName)) {
				docFiles.add(docMap);
			    } else {
				docFiles2.add(docMap);
			    }

			}
			if ("재질성적서".equals(categoryName)) {
			    catDocMap.put(categoryName, docFiles);
			} else {
			    catDocMap.put(categoryName, docFiles2);
			}

		    }
		}

	    } catch (Exception e) {
		e.printStackTrace();
		QMSDBUtil.close(conn);
	    } finally {
		conn.close();
	    }
	    // 재질성적서, 중금속성적서를 kqis 에서 찾는 로직 end

	    partMap.put("catDocMap", catDocMap);
	    bomList.add(partMap);
	}
    }

    private List<Map<String, String>> getQmsFileInfo(String categoryName, String partNo, Connection conn) throws Exception {

	List<Map<String, String>> fileList = new ArrayList<Map<String, String>>();

	Map<String, String> fileInfo = null;

	String type = "";

	if ("재질성적서".equals(categoryName)) {
	    type = "M";
	}

	if ("중금속성적서".equals(categoryName)) {
	    type = "T";
	}

	StringBuffer sb = new StringBuffer();
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {

	    // sb.append("SELECT dbo.UDF_TOXIC_INSP_ADDFILES_SEARCH('" + partNo + "', '" + type+
	    // "', 'F') as fileName, dbo.UDF_TOXIC_INSP_ADDFILES_SEARCH('" + partNo + "', '" +
	    // type + "', 'U') as filePath \n");

	    sb.append(" SELECT FN AS fileName, FLOC AS filePath FROM dbo.UDF_TOXIC_INSP_ADDFILES_SRCH('" + partNo + "', '" + type + "') \n");

	    pstmt = conn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {

		String filePath = StringUtil.checkNull(rs.getString("filePath"));
		filePath = "T:/_Project/KetQMS" + StringUtils.removeStart(filePath, "..");
		fileInfo = new HashMap<String, String>();

		fileInfo.put("filePath", filePath);
		fileInfo.put("fileName", StringUtil.checkNull(rs.getString("fileName")));
		fileList.add(fileInfo);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {

	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	}

	return fileList;
    }

    @SuppressWarnings("unchecked")
    public String downloadDocFileZip(Map<String, Object> reqMap) throws Exception {

	String downloadUrl = null;

	List<Map<String, Object>> dlItemList = (List<Map<String, Object>>) reqMap.get("dlItemList");
	boolean isDecrypt = (boolean) reqMap.get("isDecrypt");

	String zipDir = DownloadView.TEMPPATH + File.separator + "PPAPFILES_ZIP_" + DateUtil.getCurrentTimestamp().getTime();
	File tempDir = new File(zipDir);
	tempDir.mkdir();

	if (dlItemList.size() > 0) {

	    String targetContent = "";

	    for (Map<String, Object> dlItem : dlItemList) {

		String filePath = String.valueOf(dlItem.get("filePath"));
		String name = String.valueOf(dlItem.get("name"));
		String contentOid = String.valueOf(dlItem.get("contentOid"));
		String partNo = String.valueOf(dlItem.get("partNo"));
		String categoryName = String.valueOf(dlItem.get("categoryName"));
		// String holderOid = String.valueOf(dlItem.get("holderOid"));
		String parentPartNo = String.valueOf(dlItem.get("parentPartNo"));
		String rootPartNo = String.valueOf(dlItem.get("rootPartNo"));

		if (targetContent.indexOf(rootPartNo) < 0 && isDecrypt) {
		    targetContent += rootPartNo + ",";
		}

		ApplicationData appData = null;
		File appFile = null;
		String appFileName = null;

		if (StringUtils.isNotEmpty(contentOid)) {
		    appData = (ApplicationData) CommonUtil.getObject(contentOid);
		    // ContentHolder holder = (ContentHolder) CommonUtil.getObject(holderOid);
		    // InputStream is = ContentServerHelper.service.findLocalContentStream(appData);
		    appFile = ContentServerHelper.service.getStoredItemFile(appData);
		    appFileName = appData.getFileName();
		} else {// 재질성적서, 중금속성적서는 kqis의 파일을 직접 추출한다
		    appFile = new File(filePath);
		    appFileName = name;
		}

		String fileName = appFileName;

		// if (getCFNCategory().contains(categoryName)) {
		// fileName = partNo + "_" + categoryName + "_" + appFileName;
		//
		// if (StringUtil.checkString(parentPartNo)) {
		// fileName = parentPartNo + "_" + categoryName + "_" + appFileName;
		// }
		// }

		File partFolder = new File(zipDir + File.separator + rootPartNo);

		if (!partFolder.exists()) {
		    partFolder.mkdir();
		}

		fileName = FileContentUtil.manager.getValidFileName(fileName);// 사용할 수 없는 특수문자 체크

		File file = new File(zipDir + File.separator + rootPartNo + File.separator + fileName);
		FileRenamePolicy frp = new FileRenamePolicy();
		file = frp.rename(file);

		FileOutputStream fos = new FileOutputStream(file);

		// 암호화
		if (DRMHelper.useDrm) {
		    if (DRMHelper.isEncFile(appFile)) {
			appFile = DRMHelper.Decryptupload(appFile, fileName);
		    }

		    if (!isDecrypt) {
			appFile = DRMHelper.encryptFile(appFile, fileName);
		    }
		}

		FileInputStream is = new FileInputStream(appFile);
		IOUtils.copy(is, fos);

		is.close();
		fos.flush();
		fos.close();
	    }

	    String zipName = "PPAP_DOCFILES_" + DateUtil.getCurrentDateString("d") + ".zip";
	    ZipUtil.zip(zipDir, DownloadView.TEMPPATH + File.separator + zipName);
	    downloadUrl = "/plm/ext/download?path=/TEMP/" + zipName;

	    if (isDecrypt) {
		targetContent = StringUtils.removeEnd(targetContent, ",");
		FileContentUtil.manager.saveDownloadHistory("PPAP자료 복호화 다운로드", targetContent);
	    }
	}

	return downloadUrl;
    }

    private List<String> getCFNCategory() {

	List<String> list = new ArrayList<String>();
	list.add("정기신뢰성성적서");
	list.add("VOCs성적서");
	list.add("냄새성적서");
	list.add("중금속성적서");
	list.add("재질성적서");
	list.add("연소성성적서");

	return list;
    }

    @SuppressWarnings("resource")
    public String getPartNoListFromExcel(Map<String, Object> reqMap, File uploadFile) throws Exception {

	String partNoList = "";

	if (DRMHelper.useDrm) {
	    uploadFile = DRMHelper.Decryptupload(uploadFile, uploadFile.getName());
	}

	String fileName = uploadFile.getName();

	String ext = "";
	if (fileName.indexOf(".") >= 0) {
	    ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
	}

	if (ext.length() == 0) {
	    throw new Exception("정확하지 않은 확장자의 파일입니다.");
	} else {

	    FileInputStream fis = new FileInputStream(uploadFile);

	    if ("xlsx".equals(ext)) {

		XSSFWorkbook wb = new XSSFWorkbook(fis);

		XSSFSheet sheet = wb.getSheetAt(0);

		int rowNum = sheet.getPhysicalNumberOfRows();

		for (int i = 0; i < rowNum; i++) {

		    String partNo = "";
		    XSSFRow row = sheet.getRow(i);

		    XSSFCell cell = row.getCell(0);
		    if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
			partNo = String.valueOf((int) cell.getNumericCellValue());
		    } else {
			partNo = cell.getStringCellValue();
		    }
		    partNoList += partNo + ",";
		}

	    } else if ("xls".equals(ext)) {

		HSSFWorkbook wb = new HSSFWorkbook(fis);
		HSSFSheet sheet = wb.getSheetAt(0);

		int rowNum = sheet.getPhysicalNumberOfRows();

		for (int i = 0; i < rowNum; i++) {

		    String partNo = "";
		    HSSFRow row = sheet.getRow(i);

		    HSSFCell cell = row.getCell(0);
		    if (cell != null) {

			if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
			    partNo = String.valueOf((int) cell.getNumericCellValue());
			} else {
			    partNo = cell.getStringCellValue();
			}
		    }

		    partNoList += partNo + ",";
		}

	    } else {
		throw new Exception("xls,xlsx 확장자의 파일만 업로드 가능합니다.");
	    }
	}

	partNoList = StringUtils.removeEnd(partNoList, ",").trim();

	return partNoList;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> batchRevision(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();
	List<String> docList = (List<String>) reqMap.get("docList");

	List<Map<String, String>> errorList = new ArrayList<Map<String, String>>();

	F1: for (String oid : docList) {

	    KETProjectDocument doc = (KETProjectDocument) CommonUtil.getObject(oid);

	    if (!"APPROVED".equals(doc.getLifeCycleState().toString())) {
		Map<String, String> error = new HashMap<String, String>();
		error.put("oid", oid);
		error.put("docNo", doc.getNumber());
		error.put("message", "승인완료 상태가 아닌 문서는 개정이 불가능 합니다.");
		errorList.add(error);
		continue;
	    }

	    KETProjectDocument lastDoc = getLastestDocument(doc.getNumber());
	    if (!oid.equals(CommonUtil.getOIDString(lastDoc))) {
		Map<String, String> error = new HashMap<String, String>();
		error.put("oid", oid);
		error.put("docNo", doc.getNumber());
		error.put("message", "최신버전이 아닌 문서는 개정이 불가능 합니다.");
		errorList.add(error);
		continue;
	    }

	    // 해당 문서에 진행 ECN이 있는지 체크로직 - 있으면 개정불가
	    QueryResult qr = PersistenceHelper.manager.navigate(doc, "prodECA", KETProdECADocLink.class, false);
	    while (qr.hasMoreElements()) {
		KETProdECADocLink ketProdECADocLink = (KETProdECADocLink) qr.nextElement();
		KETProdChangeActivity eca = ketProdECADocLink.getProdECA();

		if (eca.getCompleteDate() == null) {
		    Map<String, String> error = new HashMap<String, String>();
		    error.put("oid", oid);
		    error.put("docNo", doc.getNumber());
		    error.put("message", "ECN이 진행중인 문서는 개정이 불가능 합니다.");
		    errorList.add(error);
		    continue F1;
		}
	    }
	}

	if (errorList.isEmpty()) {
	    for (String oid : docList) {
		KETProjectDocument doc = (KETProjectDocument) CommonUtil.getObject(oid);
		KETProjectDocumentHelper.service.revise(doc);
	    }
	    resMap.put("message", "일괄개정되었습니다.");
	}

	resMap.put("errorList", errorList);

	return resMap;
    }
}
