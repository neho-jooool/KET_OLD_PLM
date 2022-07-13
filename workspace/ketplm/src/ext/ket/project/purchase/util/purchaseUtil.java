package ext.ket.project.purchase.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import wt.fc.PersistenceServerHelper;
import wt.lifecycle.State;
import wt.method.MethodContext;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pom.DBProperties;
import wt.pom.WTConnection;
import wt.session.SessionHelper;
import e3ps.common.impl.Tree;
import e3ps.common.impl.TreeHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.Base64;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.ews.dao.PartnerDao;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.PeopleData;
import e3ps.project.MoldItemInfo;
import e3ps.project.beans.ProjectHelper;
import e3ps.qms.util.QMSDBUtil;
import ext.ket.common.util.ObjectUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.project.purchase.entity.KETPurchaseProject;
import ext.ket.shared.util.EjsConverUtil;

public class purchaseUtil {
    public static final String COMPLETE = "COMPLETED";
    public static final String DELAY = "DELAY";
    public static final String PROGRESS = "PROGRESS";
    public static final String PMOINWORK = "PMOINWORK";
    KETMessageService messageService = KETMessageService.getMessageService();

    public List<Map<String, Object>> mainQueryDataSetting(ResultSet rs) throws Exception {
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	try {

	    String dateColumn = "";
	    int startDateIdx = 1;
	    int endDateIdx = 1;

	    List<String> columnNameList = null;

	    Date curDate = DateUtil.getCurrentTimestamp();

	    if (rs != null) {
		columnNameList = ObjectUtil.manager.rsColumnNameToList(rs);
	    }

	    while (rs.next()) {

		Map<String, Object> map1 = basicConvertMap(rs, true);
		Map<String, Object> map2 = basicConvertMap(rs, false);

		startDateIdx = 1;
		endDateIdx = 1;
		for (String key : columnNameList) {
		    if (key.contains("END") || key.contains("START")) {
			String value = rs.getString(key);
			if (key.contains("START")) {
			    dateColumn = "date" + Integer.toString(startDateIdx);

			    gridDataTypeSetting(map1, dateColumn, value, key, true); // 계획

			    String endDateValue = rs.getString(StringUtils.replace(key, "START", "END"));
			    if (StringUtils.isEmpty(endDateValue)) {
				gridFontColorSetting(map1, dateColumn, value, curDate);
			    }
			    startDateIdx++;

			} else {
			    dateColumn = "date" + Integer.toString(endDateIdx);

			    gridDataTypeSetting(map2, dateColumn, value, key, true); // 실행
			    endDateIdx++;

			}

		    }
		}
		list.add(map1);
		list.add(map2);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception(e);
	}

	return list;
    }

    private void gridDataTypeSetting(Map<String, Object> map, String column, String value, String key, boolean format) throws Exception {

	// String color = "#BDBDBD";
	//
	// if (key.contains("END")) {
	// color = "#FAFAFA";
	// }
	if (StringUtils.isNotEmpty(value) && format) {
	    value = DateUtil.getTimeFormat(DateUtil.getTimestampFormat(value, "yy-MM-dd"), "yy/MM/dd");
	} else {

	    map.put(column + "Type", "Date");
	    map.put(column + "Format", "yyyy-MM-dd");
	    map.put(column + "EditFormat", "yyyy-MM-dd");
	    map.put(column + "DefaultDate", "");
	}
	map.put(column, value);
	// map.put(column + "HtmlPrefix", "<b>");
	// map.put(column + "HtmlPostfix", "</b>");
	// map.put(column + "Color", color);

    }

    private int diffDuration(Date date1, Date date2) throws Exception {

	long oneDayMillis = 24 * 60 * 60 * 1000;
	int duration = (int) ((date1.getTime() - date2.getTime()) / oneDayMillis);

	return duration;
    }

    private void gridFontColorSetting(Map<String, Object> map, String dateColumn, String value, Date curDate) throws Exception {

	if (StringUtils.isNotEmpty(value)) {

	    Date planDate = DateUtil.getTimestampFormat(value, "yyyy-MM-dd");

	    int diff = this.diffDuration(planDate, curDate);
	    if (diff < 0) {
		map.put(dateColumn + "HtmlPrefix", "<font color='red'>");
		map.put(dateColumn + "HtmlPostfix", "</font>");
	    }

	}

    }

    private Map<String, Object> basicConvertMap(ResultSet rs, boolean isPlan) throws Exception {
	Map<String, Object> map = new HashMap<String, Object>();
	String rowSpan = "RowSpan";
	int rowSpanSize = 2;

	map.put("rnum", rs.getString("RNUM"));
	map.put("rnum" + rowSpan, rowSpanSize);
	map.put("partNo" + rowSpan, rowSpanSize);

	map.put("partNo", rs.getString("PARTNO"));
	map.put("partNo" + rowSpan, rowSpanSize);

	map.put("partName", rs.getString("PARTNAME"));
	map.put("partName" + rowSpan, rowSpanSize);

	map.put("outSourcing", rs.getString("OUTSOURCING"));
	map.put("outSourcing" + rowSpan, rowSpanSize);

	map.put("outSourcingGubun", rs.getString("OUTSOURCINGGUBUN"));
	map.put("outSourcingGubun" + rowSpan, rowSpanSize);

	map.put("managerName", rs.getString("MANAGERNAME"));
	map.put("managerName" + rowSpan, rowSpanSize);

	map.put("pjtNo", rs.getString("PJTNO"));
	map.put("pjtNo" + rowSpan, rowSpanSize);

	map.put("bigo", rs.getString("BIGO"));
	map.put("bigo" + rowSpan, rowSpanSize);

	map.put("partNo" + "HtmlPrefix", "<font color='#4398BC'>");
	map.put("partNo" + "HtmlPostfix", "</font>");

	map.put("pjtNo" + "HtmlPrefix", "<font color='#4398BC'>");
	map.put("pjtNo" + "HtmlPostfix", "</font>");

	if (isPlan) {
	    map.put("dateGubun", "계획");
	    // map.put("dateGubunColor", "#BDBDBD");
	} else {
	    map.put("dateGubun", "실행");
	    // map.put("dateGubunColor", "#FAFAFA");
	}

	return map;
    }

    public String getPartRefDocumentListQuery(Map<String, Object> reqMap) {

	String partNo = (String) reqMap.get("partNo");

	StringBuffer sql = new StringBuffer();

	sql.append(" SELECT A0.CLASSNAMEA2A2||':'||A0.IDA2A2 AS OID, A0.VERSIONIDA2VERSIONINFO AS VERSION, A0.DOCUMENTNO, A1.PARTNO                                         \n");
	sql.append("   FROM KETProjectDocument A0,KETDocumentPartLink A1,KETDocumentCategoryLink A2,KETDocumentCategory A3                                                  \n");
	sql.append("  WHERE (A1.idA3A5 = A0.idA2A2) AND (A2.idA3B5 = A0.idA2A2) AND (A2.idA3A5 = A3.idA2A2) AND (A3.categoryCode = 'PD303004')                              \n");
	sql.append("      AND (A0.VERSIONIDA2VERSIONINFO = (SELECT MAX(TO_NUMBER(VERSIONIDA2VERSIONINFO)) FROM KETPROJECTDOCUMENT                                           \n");
	sql.append("                                                                   WHERE IDA3MASTERREFERENCE=A0.IDA3MASTERREFERENCE))         \n");
	// 승인완료 기준으로 관련문서를 가져왔으나 상태관련없이 다 가져오게 변경
	// sql.append("                                                                   WHERE IDA3MASTERREFERENCE=A0.IDA3MASTERREFERENCE AND STATESTATE='APPROVED'))         \n");
	sql.append("      AND (A0.latestiterationInfo = 1)                                                                                                      \n");

	if (partNo != null) {
	    if (StringUtil.checkString(partNo)) {
		sql.append("    AND ").append(
		        KETQueryUtil.getSqlQueryForMultiSearch("a1.partNo", partNo.replaceAll("\\p{Space}", ""), true));
	    }
	}
	sql.append("      ORDER BY A1.PARTNO                                                                                                             \n");
	return sql.toString();
    }

    public List<Map<String, Object>> getPartRefDocumentList(Map<String, Object> reqMap) throws Exception {

	List<Map<String, Object>> list = getResultList(getPartRefDocumentListQuery(reqMap));

	return list;
    }

    public void KETdocConvertMap(String partNo, List<Map<String, Object>> docRefList, Map<String, Object> dataMap) throws Exception {
	String html = "";
	String rev = "";

	if (StringUtils.isNotEmpty(partNo)) {

	    for (Map<String, Object> doc : docRefList) {
		if (partNo.equals((String) doc.get("PARTNO"))) {
		    String oid = (String) doc.get("OID");
		    String docNo = (String) doc.get("DOCUMENTNO");
		    String version = (String) doc.get("VERSION");
		    html += "<a href=javascript:purchase.doViewDoc('" + oid + "');>" + docNo + "<a><br>";
		    KETProjectDocument document = (KETProjectDocument) CommonUtil.getObject(oid);
		    String state = document.getLifeCycleState().getDisplay(messageService.getLocale());
		    rev += "<a href=javascript:viewVersionHistory('" + oid + "');>" + version + "(" + state + ")<a><br>";
		}

	    }
	    html = StringUtils.removeEnd(html, "<br>");
	    rev = StringUtils.removeEnd(rev, "<br>");
	}

	dataMap.put("inspagDocNo", html);
	dataMap.put("rev", rev);
    }

    public void KQISdocConvertMap(String partNo, String sabun, List<Map<String, Object>> docIsirList, Map<String, Object> dataMap)
	    throws Exception {

	String html = "";
	String stepName = "";
	if (StringUtils.isNotEmpty(partNo)) {
	    for (Map<String, Object> doc : docIsirList) {
		if (doc.get("PART_NO").equals(partNo)) {

		    String code = (String) doc.get("CODE");
		    String revCd = (String) doc.get("REV_CD");
		    String docStep = (String) doc.get("DOC_STEP");
		    stepName += (String) doc.get("STEP_NM") + "<br>";
		    String recNo = (String) doc.get("REC_NO");
		    String url = "http://kqis.connector.co.kr/Eco/EcoIsirPtnRead.aspx?gw=Y&id=" + sabun + "&revcd=" + revCd + "&code="
			    + code + "&docstep=" + docStep;
		    html += "<a href=javascript:purchase.openDocLink('" + url + "');>" + recNo + "<a><br>";
		}
	    }
	}

	html = StringUtils.removeEnd(html, "<br>");
	stepName = StringUtils.removeEnd(stepName, "<br>");

	dataMap.put("recNo", html);
	dataMap.put("stepNameHtmlPrefix", "<div>");
	dataMap.put("stepName", stepName);
	dataMap.put("stepNameHtmlPostfix", "</div>");
    }

    public String getAccountNo() throws Exception {

	WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
	PeopleData pData = new PeopleData(user);
	String sabun = pData.people.getAccountNo();

	if (StringUtils.isEmpty(sabun)) {
	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    Cookie[] cookies = request.getCookies();
	    String kmsLinkId = "";
	    String kmsId = "";
	    if (cookies != null) {
		for (int i = 0; i < cookies.length; i++) {
		    Cookie cookie = cookies[i];
		    if (cookie.getName().equals("kms_linkID")) {
			kmsLinkId = cookie.getValue();
		    }
		}
	    }

	    kmsLinkId = new String(Base64.decode(kmsLinkId), "UTF-8");
	    if (StringUtil.checkString(kmsLinkId)) {
		String[] kmsLinkArr = kmsLinkId.split("[|]");
		kmsId = kmsLinkArr[0];
		List<Map<String, Object>> list = getEPAccountNoResultList(getUserNoByEp(kmsId));
		for (Map<String, Object> emp : list) {
		    sabun = (String) emp.get("EmpNo");
		}
	    }
	}

	return sabun;
    }

    public String getUserNoByEp(String id) throws Exception {
	StringBuffer sqlString = new StringBuffer();
	sqlString.append("  SELECT u.empcode                                 EmpNo             \n");
	sqlString.append("    FROM xclick_ket.dbo.v_DP_User_All_plm u                            \n");
	sqlString.append("  WHERE u.LoginID = '" + id + "'                                               \n");
	sqlString.append("      AND u.PrimaryYN = 'Y'                                           \n");

	return sqlString.toString();
    }

    public List<Map<String, Object>> getEPAccountNoResultList(String sql) throws Exception {
	List<Map<String, Object>> list = null;

	Statement stat = null;
	ResultSet rs = null;
	Connection conn = null;

	try {

	    conn = EPDBUtil.getConnection();
	    stat = conn.createStatement();
	    rs = stat.executeQuery(sql);

	    list = ObjectUtil.manager.rsToList(rs);

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception(e);
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (stat != null) {
		stat.close();
	    }
	    EPDBUtil.close(conn);
	}

	return list;
    }

    public Map<String, Object> getDocList(Map<String, Object> reqMap) throws Exception {

	String[] selectGbs = (String[]) reqMap.get("selectGb"); // 상태
	String selectGb = selectGbs[0];

	String mainSql = selectGb.equals("1") ? getMainFindPagingListQuery(reqMap, false, false) : getPartDocQuery(reqMap);

	String sabun = getAccountNo();

	List<Map<String, Object>> projectList = null;

	if (selectGb.equals("1")) {
	    projectList = getResultList(getPartDocQuery(reqMap));
	}
	List<Map<String, Object>> docResultList = getResultList(mainSql);
	List<Map<String, Object>> docIsirList = getKQISResultList(getKQISIsirQuery(reqMap));

	if (docResultList.size() < 1 && StringUtils.isNotEmpty((String) reqMap.get("partNo"))) {
	    docResultList = getResultList(getPartListQuery(reqMap));
	}

	List<Map<String, Object>> docRefList = getPartRefDocumentList(reqMap);

	for (Map<String, Object> dataMap : docResultList) {
	    String partNo = (String) dataMap.get("PARTNO");
	    dataMap.put("CanDelete", "0");
	    dataMap.put("CanSelect", "1");
	    dataMap.put("rnum", dataMap.get("RNUM"));
	    dataMap.put("partNo", partNo);
	    dataMap.put("partName", dataMap.get("PARTNAME"));
	    dataMap.put("dieNo", dataMap.get("DIENO"));
	    String dieNoKey = (String) dataMap.get("PJTNO") + partNo;
	    if (selectGb.equals("1")) {
		for (Map<String, Object> projectMap : projectList) {
		    String dieNoKey2 = (String) projectMap.get("PJTNO") + (String) projectMap.get("PARTNO");
		    if (dieNoKey.equals(dieNoKey2)) {
			dataMap.put("dieNo", projectMap.get("DIENO"));
			break;
		    }
		}
	    }
	    dataMap.put("pjtNo", dataMap.get("PJTNO"));
	    KETdocConvertMap(partNo, docRefList, dataMap);
	    KQISdocConvertMap(partNo, sabun, docIsirList, dataMap);

	}

	return EjsConverUtil.convertToDto(docResultList);

    }

    public String getPartListQuery(Map<String, Object> reqMap) throws Exception {
	String partNo = (String) reqMap.get("partNo");

	StringBuffer sql = new StringBuffer();
	sql.append(" SELECT ROWNUM AS RNUM, DATA.* FROM (                                                                \n");
	sql.append(" SELECT WTPARTNUMBER AS PARTNO,                                                                      	           \n");
	sql.append("            NAME AS PARTNAME                                                                		           \n");
	sql.append("   FROM WTPARTMASTER                                                 \n");
	sql.append("  WHERE 1=1                                                                              \n");
	// 프로젝트번호
	if (partNo != null) {
	    if (StringUtil.checkString(partNo)) {
		sql.append("    AND ").append(
		        KETQueryUtil.getSqlQueryForMultiSearch("WTPARTNUMBER", partNo.replaceAll("\\p{Space}", ""), true));
	    }
	}

	sql.append("    ORDER BY WTPARTNUMBER ) DATA                               \n");
	return sql.toString();
    }

    public String getPartDocQuery(Map<String, Object> reqMap) throws Exception {

	String[] selectGbs = (String[]) reqMap.get("selectGb");
	String selectGb = selectGbs[0];

	StringBuffer sql = new StringBuffer();
	sql.append(" SELECT ROWNUM AS RNUM, DATA.* FROM (                                                                \n");
	sql.append(" SELECT MIINFO.PARTNO,                                                                      	           \n");
	sql.append("            MIINFO.PARTNAME,                                                                 		           \n");
	sql.append("            CASE WHEN DIENO = '미입력' THEN ''  WHEN DIENO = '-' THEN '' ELSE DIENO END DIENO,  \n");
	sql.append("            E3PSPJTMST.PJTNO                                                                 \n");
	sql.append("   FROM E3PSPROJECTMASTER   E3PSPJTMST,                                                  \n");
	sql.append("             PRODUCTPROJECT      PROD,                                                   \n");
	sql.append("             MOLDITEMINFO MIINFO                                                         \n");
	sql.append("  WHERE 1=1                                                                              \n");
	sql.append("    AND E3PSPJTMST.IDA2A2 = PROD.IDA3B8                                                  \n");
	sql.append("    AND PROD.IDA2A2 = MIINFO.IDA3A3                                                      \n");
	sql.append("    AND PROD.LASTEST       = 1                                                           \n");
	sql.append("    AND PROD.CHECKOUTSTATE <> 'c/o'                                                      \n");
	sql.append("    AND SHRINKAGE = '신규'                             \n");

	partMainQueryParamAppend(sql, reqMap, false);

	if ("2".equals(selectGb)) {
	    sql.append(" UNION                                                                      	           \n");
	    sql.append(" SELECT MIINFO.PNUM AS PARTNO,                                                                      	           \n");
	    sql.append("            MIINFO.PNAME AS PARTNAME,                                                                 		           \n");
	    sql.append("            '' AS DIENO,  \n");
	    sql.append("            E3PSPJTMST.PJTNO                                                                 \n");
	    sql.append("   FROM E3PSPROJECTMASTER   E3PSPJTMST,                                                  \n");
	    sql.append("            PRODUCTPROJECT      PROD,                                                   \n");
	    sql.append("            PRODUCTINFO MIINFO                                                         \n");
	    sql.append("  WHERE 1=1                                                                              \n");
	    sql.append("    AND E3PSPJTMST.IDA2A2 = PROD.IDA3B8                                                  \n");
	    sql.append("    AND PROD.IDA2A2 = MIINFO.IDA3A3                                                      \n");
	    sql.append("    AND PROD.LASTEST       = 1                                                           \n");
	    sql.append("    AND PROD.CHECKOUTSTATE <> 'c/o'                                                      \n");

	    partMainQueryParamAppend(sql, reqMap, true);
	}

	sql.append("    ORDER BY PJTNO, PARTNO ) DATA                               \n");
	return sql.toString();
    }

    public void partMainQueryParamAppend(StringBuffer sql, Map<String, Object> reqMap, boolean isProduct) {
	String pjtNo = (String) reqMap.get("pjtNo"); // 프로젝트번호

	String partNo = (String) reqMap.get("partNo");

	String[] states = (String[]) reqMap.get("state");

	// 프로젝트번호
	if (pjtNo != null) {
	    if (StringUtil.checkString(pjtNo)) {
		sql.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJTNO", pjtNo.replaceAll("\\p{Space}", ""), true));
	    }
	}

	// 프로젝트번호
	if (partNo != null) {
	    if (StringUtil.checkString(partNo)) {
		sql.append("    AND ").append(
		        KETQueryUtil.getSqlQueryForMultiSearch(isProduct ? "MIINFO.PNUM" : "MIINFO.partNo",
		                partNo.replaceAll("\\p{Space}", ""), true));
	    }
	}

	// 제품 프로젝트 상태
	if (states != null) {

	    String state = StringUtils.join(states, ',');

	    sql.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("STATESTATE", state, true));

	}
    }

    public String getKQISFastListQuery(String pjtNo) throws Exception {
	StringBuffer sb = new StringBuffer();

	sb.append(" --과거차반영현황 세부항목 																											 \n");
	sb.append(" SELECT CASE WHEN DOC_STEP=100 THEN NULL                                                                                      \n");
	sb.append("                   WHEN DOC_STEP IN (30,35,36,220) THEN NULL                                                                  \n");
	sb.append("                   WHEN ISNULL(DATEDIFF(DAY,REP_REQ_DAY,GETDATE()),0) > 0 THEN '지연'                                           \n");
	sb.append("                   ELSE NULL                                                                                                  \n");
	sb.append("           END AS DELAY_STATUS    -- 지연여부                                                                                    \n");
	sb.append("          ,DBO.UDF_USERNM(A.WRT_ID) AS WRT_USER-- 등록자                                                                        \n");
	sb.append("          ,A.WRT_DAY -- 등록일                                                                                                  \n");
	sb.append("          ,A.PART_NO                                                                                                          \n");
	sb.append("          ,A.PART_NM                                                                                                          \n");
	sb.append("          ,A.CAR_TYPE  -- 차종                                                                                                  \n");
	sb.append("          ,A.REP_REQ_DAY -- 회신요구일                                                                                            \n");
	sb.append("          ,A.DOC_STEP -- 문서단계                                                                                                \n");
	sb.append("          ,(SELECT COUNT(*) FROM TBL_OLDCAR_CT_REF T WHERE T.PCODE = A.CODE) AS REF_REQ_CNT  -- 반영요청건수                       \n");
	sb.append("          ,(SELECT COUNT(*) FROM TBL_OLDCAR_CT_REF T WHERE T.PCODE = A.CODE AND REF_GB='Y') AS REF_REP_CNT -- 반영회신건수         \n");
	sb.append("          ,A.VAL_CNT -- 유효성확인건수                                                                                             \n");
	sb.append("          ,DBO.UDF_STATE('JA10_02',A.DOC_STEP,'KO') AS DOC_STEP_NAME                                                          \n");
	sb.append("          ,ISNULL(                                                                                                            \n");
	sb.append("              STUFF(                                                                                                          \n");
	sb.append("                        (SELECT DISTINCT ', '+DBO.UDF_USERNM_BUSEONM(REF_CHG_ID,'KO')                                         \n");
	sb.append("                          FROM TBL_OLDCAR_CT_REF                                                                              \n");
	sb.append("                         WHERE A.CODE=PCODE                                                                                   \n");
	sb.append("                            AND ISNULL(REF_DAY,'')=''                                                                         \n");
	sb.append("                             FOR XML PATH ('')                                                                                \n");
	sb.append("                        )                                                                                                     \n");
	sb.append("                     ,1                                                                                                       \n");
	sb.append("                     ,2                                                                                                       \n");
	sb.append("                     ,''                                                                                                      \n");
	sb.append("                  )                                                                                                           \n");
	sb.append("          ,'-') AS NO_REPLY_PERSON                                                                                            \n");
	sb.append("          ,A.DOC_STEP                                                                                                         \n");
	sb.append("          ,A.CODE                                                                                                             \n");
	sb.append("   FROM TBL_OLDCAR_CT (NOLOCK) A                                                                                              \n");
	if (StringUtils.isNotEmpty(pjtNo)) {
	    sb.append("    WHERE A.PJT_NO = '" + pjtNo.replaceAll("\\p{Space}", "") + "'                                             \n");
	    // 해당 프로젝트번호로 기본 조회
	}
	return sb.toString();
    }

    public String getKQISTrustListQuery(Map<String, Object> reqMap) throws Exception {

	String adminNos = ((String) reqMap.get("adminNos"));

	if (StringUtils.isEmpty(adminNos)) {
	    return "";
	}

	StringBuffer sql = new StringBuffer();
	sql.append(" SELECT A.CODE                                                                                                           \n");// URL연계용
	                                                                                                                                          // 의뢰서ID
	sql.append("       ,A.ADMIN_NO                                                                                                       \n");// 관리번호 
	sql.append("       ,CASE WHEN B.DOC_STEP = '100' THEN '완료'                                                                           \n");
	sql.append("             WHEN A.DOC_STEP = 100 THEN '진행중'                                                                           \n");
	sql.append("             ELSE '접수중'                                                                                                 \n");
	sql.append("        END TEST_STATE                                                                                                   \n"); // 진행단계
	sql.append("       ,ISNULL(DBO.UDF_COMCODENM('CLC00442', B.TEST_RESULT, 'KO'), '-') AS TEST_RESULT                                   \n"); // 시험결과
	sql.append("       ,(SELECT COUNT(T.CODE)                                                                                            \n");
	sql.append("           FROM TBL_MEASURE_TEST_REQUEST_RESULT (NOLOCK) T                                                               \n");
	sql.append("          WHERE T.PCODE = A.CODE                                                                                         \n");
	sql.append("            AND T.DATA_GUBUN = 'ISSUE'                                                                                   \n");
	sql.append("            AND ISNULL(T.DOC_STEP, 999) = 100                                                                            \n");
	sql.append("        ) AS COMPLETED_CNT                                                                                               \n"); // 완료건수
	sql.append("       ,(SELECT COUNT(T.CODE)                                                                                            \n");
	sql.append("           FROM TBL_MEASURE_TEST_REQUEST_RESULT (NOLOCK) T                                                               \n");
	sql.append("          WHERE T.PCODE = A.CODE                                                                                         \n");
	sql.append("            AND T.DATA_GUBUN = 'ISSUE'                                                                                   \n");
	// sql.append("            AND ISNULL(T.DOC_STEP, 999) <> 100                                                                           \n");
	sql.append("        ) AS TOT_CNT                                                                                                     \n"); // 전체건수
	sql.append("       ,A.PART_NO                                                                                                        \n"); // 품번
	sql.append("       ,DBO.UDF_USERNM(A.WRT_ID) AS REQUESTER                                                                            \n"); // 의뢰자 
	sql.append("       ,DBO.UDF_USERNM(A.TEST_ID) AS TESTER                                                                              \n"); // 시험자
	sql.append("       ,REPLACE(A.REQ_DAY,'.','-') AS REQ_DAY                                                                                                        \n"); // 의뢰일
	sql.append("       ,REPLACE(A.STR_PLAN_DAY,'.','-') AS STR_PLAN_DAY                                \n"); // 시험시작예정일
	sql.append("       ,REPLACE(B.TEST_STR_DAY,'.','-') AS TEST_STR_DAY                               \n"); // 시험시작실제일
	sql.append("       ,REPLACE(A.END_PLAN_DAY,'.','-') AS END_PLAN_DAY                                \n"); // 시험종료예정일
	sql.append("       ,REPLACE(B.TEST_END_DAY,'.','-') AS TEST_END_DAY                               \n"); // 시험종료실제일
	// sql.append("       ,REPLACE(CASE WHEN ISNULL(B.TEST_STR_DAY, '') = '' THEN '('+ A.STR_PLAN_DAY +')' ELSE B.TEST_STR_DAY END,'.','-') AS TEST_STR_DAY  \n");
	// // (계획)시작일자 
	// sql.append("       ,REPLACE(CASE WHEN ISNULL(B.TEST_END_DAY, '') = '' THEN '('+ A.END_PLAN_DAY +')' ELSE B.TEST_END_DAY END,'.','-') AS TEST_END_DAY  \n");
	// // (계획)완료일자  
	sql.append("   FROM TBL_MEASURE_TEST_REQUEST (NOLOCK) A                                                                              \n");
	sql.append("   LEFT OUTER JOIN (SELECT T.*                                                                                           \n");
	sql.append("                      FROM TBL_MEASURE_TEST_REQUEST_RESULT (NOLOCK) T                                                    \n");
	sql.append("                     WHERE T.DATA_GUBUN = 'SHEET'                                                                        \n");
	sql.append("                   ) B ON A.CODE = B.PCODE                                                                               \n");
	sql.append("  WHERE 1=1                                                                                                              \n");
	if (StringUtil.checkString(adminNos)) {
	    sql.append("    AND ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch("A.ADMIN_NO", adminNos.replaceAll("\\p{Space}", ""), true));// 조건:관리번호
	}
	// sql.append("    AND A.ADMIN_NO IN ('2021001011','2020000345')                                                                                        \n");

	return sql.toString();
    }

    public String getKQISTrustQuery(Map<String, Object> reqMap) throws Exception {

	String pjtNo = ((String) reqMap.get("pjtNo"));
	String adminNo = ((String) reqMap.get("adminNo"));
	String testName = ((String) reqMap.get("testName"));

	StringBuffer sql = new StringBuffer();
	sql.append(" SELECT A.CODE,A.ADMIN_NO                                                       \n"); // 관리번호
	sql.append("       ,A.PJT_NO                                                         \n"); // 프로젝트번호
	sql.append("       ,DBO.UDF_COMCODENM('CLC00453', A.TEST_GUBUN, 'KO') AS TEST_GUBUN  \n"); // 시험구분
	sql.append("       ,A.TEST_PURPOSE                                                   \n"); // 시험목적
	sql.append("       ,DBO.UDF_USERNM(A.WRT_ID) AS REQ_ID                               \n"); // 의뢰자 
	sql.append("       ,DBO.UDF_USERNM(A.TEST_ID) AS TEST_ID                              \n"); // 시험자
	sql.append("       ,REPLACE(A.REQ_DAY,'.','-') AS REQ_DAY                                                        \n"); // 의뢰일자 
	sql.append("   FROM TBL_MEASURE_TEST_REQUEST A                                       \n");
	sql.append("  WHERE 1=1                                                              \n");
	if (StringUtils.isNotEmpty(pjtNo)) {
	    sql.append("    AND A.PJT_NO = '" + pjtNo.replaceAll("\\p{Space}", "") + "'                                             \n");
	    // 해당 프로젝트번호로 기본 조회
	}
	if (StringUtils.isNotEmpty(adminNo)) {
	    sql.append("    AND A.ADMIN_NO LIKE '" + adminNo.replaceAll("\\p{Space}", "") + "%'                                        \n"); // 관리번호(검색조건)
	}

	if (StringUtils.isNotEmpty(testName)) {
	    sql.append("    AND DBO.UDF_USERNM(A.TEST_ID) = '" + testName.replaceAll("\\p{Space}", "") + "' \n"); // 시험자
	}

	return sql.toString();
    }

    public String getKQISIsirQuery(Map<String, Object> reqMap) throws Exception {

	String partNo = (String) reqMap.get("partNo");

	StringBuffer sql = new StringBuffer();
	sql.append(" SELECT M.PART_NO, M.CODE                                                               \n");
	sql.append("       ,M.REV_CD                                                             \n");
	sql.append("       ,M.PART_NO                                                            \n");
	sql.append("       ,M.REC_NO    -- 의뢰번호                                                  \n");
	sql.append("       ,M.DOC_STEP  -- 진행상태                                                  \n");
	sql.append("       ,(SELECT C.REF_NM                                                     \n");
	sql.append("           FROM TBL_COM_STATE_SUB_SUB C -- 문서상태 테이블                         \n");
	sql.append("          WHERE SCLASS_CD='JA03_04' -- ISIR 문서상태CLASS                        \n");
	sql.append("            AND REF_CD = M.DOC_STEP                                          \n");
	sql.append("            AND LANG_TYPE='KO'                                               \n");
	sql.append("        ) STEP_NM -- 진행상태명                                                  \n");
	sql.append("   FROM TBL_ECO_ISIR_PTN M WITH(NOLOCK)                                      \n");
	sql.append("   where PART_NO+PTN_ID+code in (SELECT M.PART_NO+M.PTN_ID+MAX(M.CODE) CODE  \n");
	sql.append("    FROM TBL_ECO_ISIR_PTN M                                                  \n");
	sql.append("   WHERE M.USE_YN = 'Y'                                                      \n");
	if (partNo != null) {
	    if (StringUtil.checkString(partNo)) {
		sql.append("    AND ").append(
		        KETQueryUtil.getSqlQueryForMultiSearch("M.PART_NO", partNo.replaceAll("\\p{Space}", ""), true));
	    }
	}
	sql.append("   GROUP BY M.PART_NO, M.PTN_ID)                                             \n");
	// sql.append("     AND PART_NO = '656923-2'                                                \n");
	return sql.toString();
    }

    public List<Map<String, Object>> getKQISResultList(String sql) throws Exception {
	List<Map<String, Object>> list = null;

	if (StringUtils.isEmpty(sql)) {
	    return new ArrayList<Map<String, Object>>();
	}

	Statement stat = null;
	ResultSet rs = null;
	Connection conn = null;

	try {

	    conn = QMSDBUtil.getConnection();
	    stat = conn.createStatement();
	    rs = stat.executeQuery(sql);

	    list = ObjectUtil.manager.rsToList(rs);

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception(e);
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (stat != null) {
		stat.close();
	    }
	    QMSDBUtil.close(conn);
	}

	return list;
    }

    public List<Map<String, Object>> getResultList(String sql) throws Exception {
	List<Map<String, Object>> list = null;

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();
	    rs = stat.executeQuery(sql);

	    list = ObjectUtil.manager.rsToList(rs);

	} catch (Exception e) {
	    e.printStackTrace();
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

    public List<Map<String, Object>> getConvertList(List<Map<String, Object>> dataList) throws Exception {

	WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
	boolean isAdmin = CommonUtil.isAdmin();
	List<Map<String, Object>> convertList = new ArrayList<Map<String, Object>>();
	Date curDate = DateUtil.getCurrentTimestamp();
	for (Map<String, Object> targetMap : dataList) {
	    Map<String, Object> convertMap = ObjectUtil.manager.converMapKeyChangeToObject(KETPurchaseProject.newKETPurchaseProject(),
		    targetMap);

	    String managerCode = (String) convertMap.get("managerCode");
	    boolean isManager = user.getName().equals(managerCode);
	    Iterator<String> iteratorKey = convertMap.keySet().iterator();
	    Map<String, Object> columnColorMap = new HashMap<String, Object>();

	    while (iteratorKey.hasNext()) {
		String mapKey = iteratorKey.next();

		if ((mapKey.toUpperCase().contains("END") || mapKey.toUpperCase().contains("START"))) {

		    if (isManager) {
			gridDataTypeSetting(columnColorMap, mapKey, (String) convertMap.get(mapKey), mapKey.toUpperCase(), false);
		    } else {
			columnColorMap.put(mapKey + "CanEdit", "0");
		    }

		    // columnColorMap.put(mapKey + "HtmlPrefix", "<b>");
		    // columnColorMap.put(mapKey + "HtmlPostfix", "</b>");

		}
		if (mapKey.toUpperCase().contains("START")) {
		    String endDateValue = (String) convertMap.get(StringUtils.replace(mapKey, "Start", "End"));
		    if (StringUtils.isEmpty(endDateValue)) {
			gridFontColorSetting(columnColorMap, mapKey, (String) convertMap.get(mapKey), curDate);
		    }
		}
	    }

	    Iterator<String> iteratorKey2 = columnColorMap.keySet().iterator();
	    while (iteratorKey2.hasNext()) {
		String mapKey = iteratorKey2.next();
		convertMap.put(mapKey, columnColorMap.get(mapKey));
	    }

	    if (isManager) {
		convertMap.put("managerNameIcon", "/plm/portal/images/icon_user.gif");
		convertMap.put("managerNameIconAlign", "Right");
		convertMap.put("managerNameOnClickSideIcon", "javascript:purchase.addMember(Row);");
		convertMap.put("editAuth", "true");
	    } else {
		convertMap.put("outSourcingGubunCanEdit", "0");
		convertMap.put("bigoCanEdit", "0");
		convertMap.put("editAuth", "false");
	    }

	    // convertMap.put("CanDelete", "0");
	    // convertMap.put("CanSelect", "1");

	    String managerId = (String) targetMap.get("MANAGERCODE");

	    convertMap.put("deleteGbIcon", "/plm/portal/images/b-minus.png");
	    convertMap.put("deleteGbIconAlign", "Left");
	    convertMap.put("deleteGbOnClickSideIcon", "javasciprt:purchase.deletePart(Row," + user.getName().equals(managerId) + ");");

	    // convertMap.put("managerNameButton", "/plm/portal/images/icon_delete.gif");
	    // convertMap.put("managerNameOnClickSideButton", "javasciprt:deletePopupValue(Row);");
	    convertMap.put("oid", targetMap.get("OID"));
	    convertMap.put("managerOid", targetMap.get("MANAGEROID"));

	    convertMap.put("pjtNo" + "HtmlPrefix", "<font color='#4398BC'>");
	    convertMap.put("pjtNo" + "HtmlPostfix", "</font>");
	    convertMap.put("partNo" + "HtmlPrefix", "<font color='#4398BC'>");
	    convertMap.put("partNo" + "HtmlPostfix", "</font>");
	    convertList.add(convertMap);
	}

	return convertList;
    }

    public Map<String, Object> dateMapConvert(Map<String, Object> data) throws Exception {

	Date curDate = DateUtil.getCurrentTimestamp();

	List<String> planDateArray = new ArrayList<String>();
	List<String> execDateArray = new ArrayList<String>();

	Map<String, Object> convertMap = new HashMap<String, Object>();

	boolean isDelay = false;
	boolean isProgress = false;
	boolean startNull = false;
	boolean endNull = false;

	Iterator<String> iteratorKey = data.keySet().iterator();

	while (iteratorKey.hasNext()) {
	    String mapKey = iteratorKey.next();
	    String value = (String) data.get(mapKey);

	    if ((mapKey.toUpperCase().contains("START") || mapKey.toUpperCase().contains("END"))
		    && !mapKey.toUpperCase().contains("PROJECT")) {
		Timestamp dateValue = null;

		if (StringUtils.isNotEmpty(value)) {
		    if (mapKey.toUpperCase().contains("START")) {

			String endDateValue = (String) data.get(StringUtils.replace(mapKey, "Start", "End"));
			if (!isDelay && StringUtils.isEmpty(endDateValue)) {
			    Date planDate = DateUtil.getTimestampFormat(value, "yyyy-MM-dd");
			    isDelay = this.diffDuration(planDate, curDate) < 0;
			}

			planDateArray.add(value);
		    } else if (mapKey.toUpperCase().contains("END")) {
			execDateArray.add(value);
		    }
		    dateValue = DateUtil.convertStartDate2(value);
		} else {

		    if (!isProgress) {
			isProgress = true;
		    }

		    if (mapKey.toUpperCase().contains("START")) {
			startNull = true;
		    } else {
			endNull = true;
		    }
		}
		convertMap.put(mapKey, dateValue);
	    } else {
		convertMap.put(mapKey, value);
	    }
	}

	if (planDateArray.size() + execDateArray.size() < 1) {
	    convertMap.put("pjtState", purchaseUtil.PMOINWORK);
	} else {
	    if (!isDelay && !isProgress) {
		convertMap.put("pjtState", purchaseUtil.COMPLETE);
	    } else {
		if (isDelay) {
		    convertMap.put("pjtState", purchaseUtil.DELAY);
		} else {
		    convertMap.put("pjtState", purchaseUtil.PROGRESS);
		}
	    }
	}

	convertMap.put("projectPlanStartDate", null); // 프로젝트계획완료시작일
	convertMap.put("projectPlanEndDate", null); // 프로젝트계획완료종료일
	convertMap.put("projectExecStartDate", null); // 프로젝트실제완료시작일
	convertMap.put("projectExecEndDate", null); // 프로젝트실제완료종료일

	Collections.sort(planDateArray);
	Collections.sort(execDateArray);

	if (planDateArray.size() > 0) {
	    convertMap.put("projectPlanStartDate", DateUtil.convertStartDate2(planDateArray.get(0))); // 프로젝트계획시작일
	    // if (!startNull) {
	    convertMap.put("projectPlanEndDate", DateUtil.convertStartDate2(planDateArray.get(planDateArray.size() - 1))); // 프로젝트계획종료일
	    // }
	}

	if (execDateArray.size() > 0) {
	    convertMap.put("projectExecStartDate", DateUtil.convertStartDate2(execDateArray.get(0))); // 프로젝트실제시작일
	    // if (!endNull) {
	    convertMap.put("projectExecEndDate", DateUtil.convertStartDate2(execDateArray.get(execDateArray.size() - 1))); // 프로젝트실제종료일
	    // }
	}

	return convertMap;
    }

    public String getMainFindPagingListQuery(Map<String, Object> reqMap, boolean isCount, boolean isPaging) throws Exception {
	StringBuffer sql = new StringBuffer();

	if (isCount) {
	    sql.append("SELECT COUNT(*) AS TOTALCNT FROM \n");
	} else {
	    sql.append("SELECT RDATA.* FROM(SELECT ROWNUM AS RNUM, ODATA.* FROM (SELECT DATA.* FROM \n");
	}

	sql.append("(SELECT \n");
	if (isCount) {
	    sql.append("      PJT.IDA2A2 \n");
	} else {
	    sql.append("       PJT.CLASSNAMEA2A2||':'||PJT.IDA2A2               AS OID \n");
	    sql.append("      ,PARTNO \n");
	    sql.append("      ,PARTNAME \n");
	    sql.append("      ,OUTSOURCING \n");
	    sql.append("      ,outSourcingGubun \n");
	    sql.append("      ,MANAGERNAME \n");
	    sql.append("      ,MANAGERCODE \n");
	    sql.append("      ,PJTNO \n");
	    sql.append("      ,BIGO \n");
	    sql.append("      ,managerDeptName \n");
	    sql.append("      ,classnamekeyb8||':'||ida3b8 as managerOid \n");
	    sql.append("      ,TO_CHAR(DEVORDERSTARTDATE,'YYYY-MM-DD')        AS DEVORDERSTARTDATE \n");
	    sql.append("      ,TO_CHAR(DEVORDERENDDATE,'YYYY-MM-DD')        AS DEVORDERENDDATE \n");
	    sql.append("      ,TO_CHAR(IMSPSTARTDATE,'YYYY-MM-DD')        AS IMSPSTARTDATE \n");
	    sql.append("      ,TO_CHAR(IMSPENDDATE,'YYYY-MM-DD')        AS IMSPENDDATE \n");
	    sql.append("      ,TO_CHAR(EXAAGREESTARTDATE,'YYYY-MM-DD')        AS EXAAGREESTARTDATE \n");
	    sql.append("      ,TO_CHAR(EXAAGREEENDDATE,'YYYY-MM-DD')        AS EXAAGREEENDDATE \n");
	    sql.append("      ,TO_CHAR(MOLDMKSTARTDATE,'YYYY-MM-DD')        AS MOLDMKSTARTDATE \n");
	    sql.append("      ,TO_CHAR(MOLDMKENDDATE,'YYYY-MM-DD')        AS MOLDMKENDDATE \n");
	    sql.append("      ,TO_CHAR(MOLDTRYSTARTDATE,'YYYY-MM-DD')        AS MOLDTRYSTARTDATE \n");
	    sql.append("      ,TO_CHAR(MOLDTRYENDDATE,'YYYY-MM-DD')        AS MOLDTRYENDDATE \n");
	    sql.append("      ,TO_CHAR(IMSIZESTARTDATE,'YYYY-MM-DD')        AS IMSIZESTARTDATE \n");
	    sql.append("      ,TO_CHAR(IMSIZEENDDATE,'YYYY-MM-DD')        AS IMSIZEENDDATE \n");
	    sql.append("      ,TO_CHAR(TRUSTSTARTDATE,'YYYY-MM-DD')        AS TRUSTSTARTDATE \n");
	    sql.append("      ,TO_CHAR(TRUSTENDDATE,'YYYY-MM-DD')        AS TRUSTENDDATE \n");
	    sql.append("      ,TO_CHAR(P1STARTDATE,'YYYY-MM-DD')        AS P1STARTDATE \n");
	    sql.append("      ,TO_CHAR(P1ENDDATE,'YYYY-MM-DD')        AS P1ENDDATE \n");
	    sql.append("      ,TO_CHAR(ALLSIZESTARTDATE,'YYYY-MM-DD')        AS ALLSIZESTARTDATE \n");
	    sql.append("      ,TO_CHAR(ALLSIZEENDDATE,'YYYY-MM-DD')        AS ALLSIZEENDDATE \n");
	    sql.append("      ,TO_CHAR(PPAPSTARTDATE1,'YYYY-MM-DD')        AS PPAPSTARTDATE1 \n");
	    sql.append("      ,TO_CHAR(PPAPENDDATE1,'YYYY-MM-DD')        AS PPAPENDDATE1 \n");
	    sql.append("      ,TO_CHAR(PPAPSTARTDATE2,'YYYY-MM-DD')        AS PPAPSTARTDATE2 \n");
	    sql.append("      ,TO_CHAR(PPAPENDDATE2,'YYYY-MM-DD')        AS PPAPENDDATE2 \n");
	    sql.append("      ,TO_CHAR(PPAPSTARTDATE3,'YYYY-MM-DD')        AS PPAPSTARTDATE3 \n");
	    sql.append("      ,TO_CHAR(PPAPENDDATE3,'YYYY-MM-DD')        AS PPAPENDDATE3 \n");
	    sql.append("      ,TO_CHAR(P2STARTDATE,'YYYY-MM-DD')        AS P2STARTDATE \n");
	    sql.append("      ,TO_CHAR(P2ENDDATE,'YYYY-MM-DD')        AS P2ENDDATE \n");
	}

	sql.append("   FROM KETPURCHASEPROJECT   PJT \n");

	sql.append("  WHERE 1=1 \n");

	getMainQueryParamAppend(reqMap, sql);

	sql.append(") DATA");

	if (!isCount) {
	    sql.append(" ORDER BY PJTNO,managerDeptName,managerName,partNo ) ODATA) RDATA\n");

	    // ################################## 페이징 처리 #####################################################

	    if (isPaging) {
		int formPage = StringUtil.getIntParameter((String) reqMap.get("formPage"), PageControl.FORMPAGE);
		int currentPage = Integer.parseInt(StringUtil.checkReplaceStr((String) reqMap.get("page"), "0"));

		int startPos = (((currentPage + 1) - 1) * formPage) + 1;
		String startPosStr = String.valueOf(startPos);

		int endPos = (currentPage + 1) * formPage;
		String endPosStr = String.valueOf(endPos);

		sql.append(" WHERE RDATA.RNUM BETWEEN ").append(startPosStr).append(" AND ").append(endPosStr);
	    }
	}
	// System.out.println(sql);
	return sql.toString();
    }

    public void getMainQueryParamAppend(Map<String, Object> reqMap, StringBuffer sql) throws Exception {

	// 검색 PARAMETER
	String pjtNo = (String) reqMap.get("pjtNo"); // 프로젝트번호

	String managerDeptCode = (String) reqMap.get("pmDeptOid"); // 담당부서

	String managerCode = (String) reqMap.get("pmOid"); // 담당자

	String partNo = (String) reqMap.get("partNo");

	String[] states = (String[]) reqMap.get("state"); // 상태
	String[] pjtStates = (String[]) reqMap.get("pjtState"); // 구매일정 상태
	String[] devDateTypes = (String[]) reqMap.get("devDateType");

	String selectStartDate = (String) reqMap.get("selectStartDate"); // 일자(선택)_시작일
	String selectEndDate = (String) reqMap.get("selectEndDate"); // 일자(선택)_종료일

	// 프로젝트번호
	if (pjtNo != null) {
	    if (StringUtil.checkString(pjtNo)) {
		sql.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJTNO", pjtNo.replaceAll("\\p{Space}", ""), true));
	    }
	}

	// 프로젝트번호
	if (partNo != null) {
	    if (StringUtil.checkString(partNo)) {
		sql.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("partNo", partNo.replaceAll("\\p{Space}", ""), true));
	    }
	}

	// 담당부서
	if (StringUtil.checkString(managerDeptCode)) {
	    managerDeptCode = managerDeptCode.replaceAll("\\p{Space}", ""); // 공백제거
	    for (String deptOid : managerDeptCode.split(",")) {
		Department targetDept = (Department) CommonUtil.getObject(deptOid);
		ArrayList childList = TreeHelper.manager.getAllChildList(Department.class, (Tree) targetDept, new ArrayList());

		for (Object obj : childList) {
		    Department child = (Department) obj;
		    managerDeptCode += "," + CommonUtil.getOIDString(child);
		}
	    }

	    String DEPTCODES = "";
	    for (String deptOid : managerDeptCode.split(",")) {
		Department dept = (Department) CommonUtil.getObject(deptOid);
		if (dept != null) {
		    DEPTCODES += dept.getCode() + ",";
		}
	    }
	    DEPTCODES = StringUtils.removeEnd(DEPTCODES, ",");
	    DEPTCODES = DEPTCODES.trim().replaceAll(",", "','");
	    sql.append("    AND managerDeptCode IN ('" + DEPTCODES + "') \n");
	}

	// 담당자
	if (StringUtil.checkString(managerCode)) {
	    String MANAGERCODES = "";
	    for (String pmOid : managerCode.split(",")) {
		WTUser USER = (WTUser) CommonUtil.getObject(pmOid);
		if (USER != null) {
		    MANAGERCODES += USER.getName() + ",";
		}
	    }
	    MANAGERCODES = StringUtils.removeEnd(MANAGERCODES, ",");
	    MANAGERCODES = MANAGERCODES.trim().replaceAll(",", "','");
	    sql.append("    AND managerCode IN ('" + MANAGERCODES + "') \n");
	}

	if (devDateTypes != null) {

	    String devDateType = StringUtils.join(devDateTypes, ',');

	    // 개발시작일 TYPE
	    if (devDateType.indexOf("DEVELOPDATESTART") >= 0) {

		if (StringUtil.checkString(selectStartDate)) {
		    String startDate = selectStartDate.substring(0, 4) + selectStartDate.substring(5, 7) + selectStartDate.substring(8, 10);
		    sql.append("    AND projectPlanStartDate >= TO_DATE('" + startDate + "' || '000000','YYYYMMDDHH24MISS') \n");
		}
		if (StringUtil.checkString(selectEndDate)) {
		    String endDate = selectEndDate.substring(0, 4) + selectEndDate.substring(5, 7) + selectEndDate.substring(8, 10);
		    sql.append("    AND projectPlanStartDate <  TO_DATE('" + endDate + "' || '235959','YYYYMMDDHH24MISS') \n");
		}
	    }

	    if (devDateType.indexOf("DEVELOPDATEEND") >= 0) {

		if (StringUtil.checkString(selectStartDate)) {
		    String startDate = selectStartDate.substring(0, 4) + selectStartDate.substring(5, 7) + selectStartDate.substring(8, 10);
		    sql.append("    AND projectPlanEndDate >= TO_DATE('" + startDate + "' || '000000','YYYYMMDDHH24MISS') \n");
		}
		if (StringUtil.checkString(selectEndDate)) {
		    String endDate = selectEndDate.substring(0, 4) + selectEndDate.substring(5, 7) + selectEndDate.substring(8, 10);
		    sql.append("    AND projectPlanStartDate <=  TO_DATE('" + endDate + "' || '235959','YYYYMMDDHH24MISS') \n");
		}
		// if (StringUtil.checkString(selectEndDate)) {
		// String endDate = selectEndDate.substring(0, 4) + selectEndDate.substring(5, 7) + selectEndDate.substring(8, 10);
		// sql.append("    AND projectPlanEndDate <  TO_DATE('" + endDate + "' || '235959','YYYYMMDDHH24MISS') \n");
		// }
	    }

	    // 실제시작일 TYPE
	    if (devDateType.indexOf("STARTDATE") >= 0) {

		if (StringUtil.checkString(selectStartDate)) {
		    String startDate = selectStartDate.substring(0, 4) + selectStartDate.substring(5, 7) + selectStartDate.substring(8, 10);
		    sql.append("    AND projectExecStartDate >= TO_DATE('" + startDate + "' || '000000','YYYYMMDDHH24MISS') \n");
		}
		if (StringUtil.checkString(selectEndDate)) {
		    String endDate = selectEndDate.substring(0, 4) + selectEndDate.substring(5, 7) + selectEndDate.substring(8, 10);
		    sql.append("    AND projectExecStartDate <  TO_DATE('" + endDate + "' || '235959','YYYYMMDDHH24MISS') \n");
		}
	    }

	    if (devDateType.indexOf("ENDDATE") >= 0) {

		if (StringUtil.checkString(selectStartDate)) {
		    String startDate = selectStartDate.substring(0, 4) + selectStartDate.substring(5, 7) + selectStartDate.substring(8, 10);
		    sql.append("    AND projectExecEndDate >= TO_DATE('" + startDate + "' || '000000','YYYYMMDDHH24MISS') \n");
		}
		if (StringUtil.checkString(selectEndDate)) {
		    String endDate = selectEndDate.substring(0, 4) + selectEndDate.substring(5, 7) + selectEndDate.substring(8, 10);
		    sql.append("    AND projectExecStartDate <=  TO_DATE('" + endDate + "' || '235959','YYYYMMDDHH24MISS') \n");
		}
		// if (StringUtil.checkString(selectEndDate)) {
		// String endDate = selectEndDate.substring(0, 4) + selectEndDate.substring(5, 7) + selectEndDate.substring(8, 10);
		// sql.append("    AND projectExecEndDate <  TO_DATE('" + endDate + "' || '235959','YYYYMMDDHH24MISS') \n");
		// }
	    }
	}

	// 프로젝트 상태
	if (pjtStates != null) {

	    String pjtState = StringUtils.join(pjtStates, ',');

	    if (StringUtils.contains(pjtState, "PROGRESS")) {
		pjtState += ",DELAY";
	    }
	    pjtState = pjtState.trim().replaceAll(",", "','");
	    sql.append("       AND pjtState IN ('" + pjtState + "') \n");
	}

	// 제품 프로젝트 상태
	if (states != null) {

	    String state = StringUtils.join(states, ',');
	    state = state.trim().replaceAll(",", "','");

	    sql.append("    AND IDA3E8 IN (                                                         \n");
	    sql.append(" SELECT E3PSPJTMST.IDA2A2                                       \n");
	    sql.append("   FROM PRODUCTPROJECT PJT,                         \n");
	    sql.append("             E3PSPROJECTMASTER   E3PSPJTMST         \n");
	    sql.append("            ,EXTENDSCHEDULEDATA  SCHEDULE          \n ");
	    sql.append("   WHERE PJT.LASTEST       = 1                      \n");
	    sql.append("       AND PJT.CHECKOUTSTATE <> 'c/o'               \n");
	    sql.append("       AND PJT.IDA3B8        = E3PSPJTMST.IDA2A2    \n");
	    sql.append("       AND PJT.IDA3A8        = SCHEDULE.IDA2A2      \n");
	    sql.append("       AND PJT.STATESTATE IN ('" + state + "') )\n");
	}

    }

    public List<Map<String, String>> getStateList() throws Exception {
	KETMessageService ms = KETMessageService.getMessageService();

	List<Map<String, String>> stateList = new ArrayList<Map<String, String>>();
	Vector states = ProjectHelper.getSearchState(ProjectHelper.PRODUCTPROJECT_STATE);

	for (int i = 0; i < states.size(); i++) {
	    State lcState = (State) states.get(i);
	    String key = lcState.toString();
	    String display = lcState.getDisplay(ms.getLocale());

	    if (key.equals("UNDERREVIEW")) {
		display = ms.getString("e3ps.message.ket_message", "00786"); // 결재중
	    }

	    Map<String, String> stateMap = new HashMap<String, String>();

	    stateMap.put("key", key);
	    stateMap.put("display", display);
	    stateList.add(stateMap);
	}

	Map<String, String> stateMap = new HashMap<String, String>();
	stateMap.put("key", "delay");
	stateMap.put("display", ms.getString("e3ps.message.ket_message", "02703")); // 지연
	stateList.add(stateMap);

	return stateList;
    }

    public boolean purchaseAuthCheck() throws Exception {

	if (CommonUtil.isAdmin()) {
	    return true;
	}

	WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
	Department userDept = DepartmentHelper.manager.getDepartment(user);

	if (userDept == null) {
	    System.out.println("접속한 사용자 [" + user.getFullName() + "]님의 부서가 지정되지 않았습니다.");
	}

	Department checkDept = DepartmentHelper.manager.getDepartment("10076");// 구매본부
	if (checkDept == null) {
	    System.out.println("구매본부 코드가 없습니다.(10076)");
	}

	if (userDept.getCode().equals(checkDept.getCode())) {
	    return true;
	}
	// ArrayList childList = new ArrayList();
	ArrayList childList = TreeHelper.manager.getAllChildList(Department.class, (Tree) checkDept, new ArrayList());

	for (Object obj : childList) {
	    Department child = (Department) obj;
	    if (child.getCode().equals(userDept.getCode())) {
		return true;
	    }
	}

	return false;
    }

    public String getOutSourcingByPart(String partNo, WTPart part, MoldItemInfo miInfo) throws Exception {

	if (miInfo == null) {
	    return "";
	}

	String outSourcing = "";

	if ("사내".equals(miInfo.getProductionPlace()) && miInfo.getPurchasePlace() != null) {
	    outSourcing = miInfo.getPurchasePlace().getName();
	} else if (StringUtils.isNotEmpty(miInfo.getPartnerNo())) {
	    PartnerDao partnerDao = new PartnerDao();
	    outSourcing = partnerDao.ViewPartnerName(miInfo.getPartnerNo());
	}

	if (StringUtils.isEmpty(outSourcing)) {

	    if (part == null) {
		part = PartBaseHelper.service.getLatestPart(partNo);
	    }
	    if (part != null) {
		outSourcing = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpManufacturer);
	    }
	}

	return outSourcing;
    }

    public void PurchaseProjectStateUpdate() throws Exception {
	Map<String, Object> reqMap = new HashMap<String, Object>();

	List<Map<String, Object>> dataList = this.getResultList(this.getMainFindPagingListQuery(reqMap, false, false));

	for (Map<String, Object> map : dataList) {
	    KETPurchaseProject purchase = (KETPurchaseProject) CommonUtil.getObject((String) map.get("OID"));
	    Map<String, Object> convertMap = ObjectUtil.manager.converObjectToMap(purchase);
	    ObjectUtil.manager.convertMapToObject(this.dateMapConvert(convertMap), purchase);
	    PersistenceServerHelper.manager.update(purchase);
	}
    }
}
