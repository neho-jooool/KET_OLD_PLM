package ext.ket.issue.util;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.MethodContext;
import wt.pom.DBProperties;
import wt.pom.WTConnection;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTAttributeNameIfc;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.project.E3PSProject;
import e3ps.project.ProjectCustomereventLink;
import e3ps.project.ProjectSubcontractorLink;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.common.util.ObjectUtil;
import ext.ket.issue.entity.KETGeneralissueLink;
import ext.ket.issue.entity.KETIssueMaster;
import ext.ket.issue.entity.KETIssueMasterDTO;
import ext.ket.issue.entity.KETIssuePartLink;
import ext.ket.issue.entity.KETIssueTask;
import ext.ket.issue.entity.KETIssueTaskDTO;
import ext.ket.issue.service.IssueHelper;
import ext.ket.sales.entity.KETSalesCompanyLink;
import ext.ket.sales.entity.KETSalesProject;
import ext.ket.sales.entity.KETSalesProjectDTO;

public class IssueUtil {

    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(IssueUtil.class);

    public static final IssueUtil manager = new IssueUtil();
    public static final String INWORK = "IST001"; // 작성중
    public static final String INPROGRESS = "IST002"; // 진행중
    public static final String REJECT = "IST003"; // 반려
    public static final String COMPLETE = "IST004"; // 완료

    public static final int FLAGCOMPLETE = 0;
    public static final int FLAGINPROGRESS = 1;
    public static final int FLAGWARNING = 2;
    public static final int FLAGDELAY = 3;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 7. 6. 오후 2:21:32
     * @method getIssueStateColor
     * @param pboDTO
     * @return String
     * @throws Exception
     * </pre>
     */
    public String getIssueStateColor(KETSalesProjectDTO pboDTO) throws Exception {

	String color = "";

	List<KETIssueMasterDTO> list = IssueHelper.service.getIssueMasterList(pboDTO.getOid());

	int delay = 0;
	int warning = 0;
	int inProgress = 0;

	for (KETIssueMasterDTO imDTO : list) {
	    switch (imDTO.getDelayFlag()) {
	    case FLAGDELAY:
		delay++;
		break;
	    case FLAGWARNING:
		warning++;
		break;
	    case FLAGINPROGRESS:
		inProgress++;
		break;
	    default:
		break;
	    }
	}

	if (delay > 0)
	    color = "#FE2828";
	else if (warning > 0)
	    color = "#D2D400";
	else if (inProgress > 0)
	    color = "#67B600";

	return color;
    }

    /**
     * <pre>
     * @description 세부 요청사항 전체 버전 조회
     * @author dhkim
     * @date 2018. 6. 7. 오전 10:12:26
     * @method getBranchIssueTask
     * @param it
     * @return List<KETIssueTask>
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public List<KETIssueTask> getBranchIssueTask(KETIssueTask it) throws Exception {

	List<KETIssueTask> list = new ArrayList<KETIssueTask>();

	QuerySpec qs = new QuerySpec(KETIssueTask.class);
	qs.setAdvancedQueryEnabled(true);

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(KETIssueTask.class, KETIssueTask.BRANCH_ID, SearchCondition.EQUAL, it.getBranchId()), new int[] { 0 });

	SearchUtil.setOrderBy(qs, KETIssueTask.class, 0, KETIssueTask.VERSION, false);

	QueryResult qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    list.add((KETIssueTask) qr.nextElement());
	}

	return list;
    }

    /**
     * <pre>
     * @description 세부 요청사항 목록 조회
     * @author dhkim
     * @date 2018. 5. 25. 오후 3:00:08
     * @method getIssueTaskList
     * @param im
     * @return List<KETIssueTask>
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public List<KETIssueTask> getIssueTaskList(KETIssueMaster im) throws Exception {

	List<KETIssueTask> list = new ArrayList<KETIssueTask>();

	QuerySpec qs = new QuerySpec(KETIssueTask.class);
	qs.setAdvancedQueryEnabled(true);

	qs.appendWhere(new SearchCondition(KETIssueTask.class, KETIssueTask.ISSUE_MASTER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(im)),
	        new int[] { 0 });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(KETIssueTask.class, KETIssueTask.LASTEST, SearchCondition.IS_TRUE, true), new int[] { 0 });

	SearchUtil.setOrderBy(qs, KETIssueTask.class, 0, KETIssueTask.SORT, false);

	QueryResult qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    KETIssueTask it = (KETIssueTask) qr.nextElement();
	    list.add(it);
	}

	return list;
    }

    /**
     * <pre>
     * @description 세부 요청사항 목록 조회(DTO)
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:38:37
     * @method getIssueTaskList
     * @param reqMap
     * @return List<KETIssueTaskDTO>
     * @throws Exception
     * </pre>
     */
    public List<KETIssueTaskDTO> getIssueTaskList(Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");
	KETIssueMaster im = (KETIssueMaster) CommonUtil.getObject(oid);

	List<KETIssueTaskDTO> itList = new ArrayList<KETIssueTaskDTO>();
	List<KETIssueTask> list = getIssueTaskList(im);

	for (KETIssueTask it : list) {

	    KETIssueTaskDTO itDTO = new KETIssueTaskDTO(it);
	    itList.add(itDTO);
	}

	return itList;
    }

    /**
     * <pre>
     * @description 세부 요청사항 목록 조회(MAP)
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:38:56
     * @method getIssueTaskMapList
     * @param reqMap
     * @return List<Map<String,Object>>
     * @throws Exception
     * </pre>
     */
    public List<Map<String, Object>> getIssueTaskMapList(Map<String, Object> reqMap) throws Exception {

	List<Map<String, Object>> itList = new ArrayList<Map<String, Object>>();
	List<KETIssueTaskDTO> list = getIssueTaskList(reqMap);

	for (KETIssueTaskDTO it : list) {

	    Map<String, Object> itMap = ObjectUtil.manager.converObjectToMap(it);

	    itList.add(itMap);
	}

	return itList;
    }

    /**
     * <pre>
     * @description 고객 요구사항 번호 생성
     * @author dhkim
     * @date 2018. 5. 25. 오후 2:53:18
     * @method getGenerateIssueReqNo
     * @return String
     * @throws Exception
     * </pre>
     */
    public String getGenerateIssueReqNo() throws Exception {

	NumberFormat nf = NumberFormat.getInstance();
	nf.setMinimumIntegerDigits(4);

	String year = DateUtil.getThisYear();
	year = year.substring(year.length() - 2);

	String newNo = "CR-" + year + "-";
	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();
	    StringBuffer sql = new StringBuffer();

	    sql.append("SELECT MAX(TO_NUMBER(SUBSTR(A0.REQNO,7), '9999')) + 1 AS NEWNO FROM KETISSUEMASTER A0 WHERE SUBSTR(A0.REQNO,4,2)='" + year + "'");
	    stat = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

	    rs = stat.executeQuery(sql.toString());

	    if (rs.next()) {
		int no = rs.getInt("NEWNO");
		if (no == 0)
		    no = 1;
		newNo += nf.format(no);
	    }
	    newNo = StringUtils.remove(newNo, ",");
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

	return newNo;
    }

    /**
     * <pre>
     * @description 고객 요구사항 연결 오브젝트 링크 조회
     * @author dhkim
     * @date 2018. 5. 25. 오전 10:48:15
     * @method getPBOLink
     * @param im
     * @return KETGeneralissueLink
     * @throws Exception
     * </pre>
     */
    public KETGeneralissueLink getPBOLink(KETIssueMaster im) throws Exception {

	QueryResult qr = PersistenceHelper.manager.navigate(im, KETGeneralissueLink.PBO_ROLE, KETGeneralissueLink.class, false);
	if (qr.hasMoreElements())
	    return (KETGeneralissueLink) qr.nextElement();

	return null;
    }

    /**
     * <pre>
     * @description 고객 요구사항 설정 프로젝트 정보 조회
     * @author dhkim
     * @date 2018. 5. 25. 오전 10:43:16
     * @method getProjectInfo
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    public Map<String, Object> getProjectInfo(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	String oid = (String) reqMap.get("oid");

	if (StringUtil.checkString(oid)) {
	    // KETSalesProject project = (KETSalesProject) CommonUtil.getObject(oid);
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);
	    resMap = getProjectInfo2(project);

	}

	return resMap;
    }

    public Map<String, Object> getProjectInfo2(E3PSProject project) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	// Project No
	String projectNo = project.getPjtNo();

	// 프로젝트 명
	String projectName = project.getPjtName();

	// 자동차사
	String lastCustomerName = "";
	String lastCustomerCode = "";

	// 고객사
	String subContractorName = "";
	String subContractorCode = "";

	QuerySpec pcspec = new QuerySpec(ProjectCustomereventLink.class);
	SearchUtil.appendEQUAL(pcspec, ProjectCustomereventLink.class, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(project), 0);
	QueryResult pcresult = PersistenceHelper.manager.find(pcspec);
	while (pcresult != null && pcresult.hasMoreElements()) {
	    ProjectCustomereventLink link = (ProjectCustomereventLink) pcresult.nextElement();
	    if (link != null) {
		lastCustomerCode = link.getCustomerevent().getCode();

		lastCustomerName = link.getCustomerevent().getName();

	    }
	}

	QuerySpec psspec = new QuerySpec(ProjectSubcontractorLink.class);
	SearchUtil.appendEQUAL(psspec, ProjectSubcontractorLink.class, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(project), 0);
	QueryResult psresult = PersistenceHelper.manager.find(psspec);
	while (psresult != null && psresult.hasMoreElements()) {
	    ProjectSubcontractorLink link = (ProjectSubcontractorLink) psresult.nextElement();
	    if (link != null) {
		subContractorCode += link.getSubcontractor().getCode() + ",";

		subContractorName += link.getSubcontractor().getName() + ",";
	    }
	}

	lastCustomerName = StringUtils.removeEnd(lastCustomerName, ",");
	lastCustomerCode = StringUtils.removeEnd(lastCustomerCode, ",");
	subContractorName = StringUtils.removeEnd(subContractorName, ",");
	subContractorCode = StringUtils.removeEnd(subContractorCode, ",");

	OEMProjectType oem = (OEMProjectType) project.getOemType(); // 대표차종
	String oemName = oem.getName();
	String oemOid = CommonUtil.getOIDString(oem);

	resMap.put("projectNo", projectNo);
	resMap.put("projectName", projectName);
	resMap.put("lastCustomerName", lastCustomerName);
	resMap.put("lastCustomerCode", lastCustomerCode);
	resMap.put("subContractorName", subContractorName);
	resMap.put("subContractorCode", subContractorCode);
	resMap.put("oemName", oemName);
	resMap.put("oemOid", oemOid);

	return resMap;
    }

    /**
     * <pre>
     * @description 고객 요구사항 설정 프로젝트 정보 조회 (OBJECT PARAM)
     * @author dhkim
     * @date 2018. 6. 21. 오후 4:45:51
     * @method getProjectInfo
     * @param project
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    public Map<String, Object> getProjectInfo(KETSalesProject project) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	QueryResult qr = PersistenceHelper.manager.navigate(project, KETSalesCompanyLink.COMPANY_ROLE, KETSalesCompanyLink.class, false);

	KETSalesCompanyLink link = null;

	// Project No
	String projectNo = project.getProjectNo();

	// 프로젝트 명
	String projectName = project.getProjectName();

	// 자동차사
	String lastCustomerName = "";
	String lastCustomerCode = "";

	// 고객사
	String subContractorName = "";
	String subContractorCode = "";

	while (qr.hasMoreElements()) {
	    link = (KETSalesCompanyLink) qr.nextElement();

	    if ("customer".equals(link.getCodeType())) {
		subContractorName += link.getCompany().getName() + ",";
		subContractorCode += link.getCompany().getCode() + ",";
	    }
	    if ("buyer".equals(link.getCodeType())) {
		lastCustomerName += link.getCompany().getName();
		lastCustomerCode += link.getCompany().getCode() + ",";
	    }
	}

	lastCustomerName = StringUtils.removeEnd(lastCustomerName, ",");
	lastCustomerCode = StringUtils.removeEnd(lastCustomerCode, ",");
	subContractorName = StringUtils.removeEnd(subContractorName, ",");
	subContractorCode = StringUtils.removeEnd(subContractorCode, ",");

	OEMProjectType oem = (OEMProjectType) project.getOemType(); // 대표차종
	String oemName = oem.getName();
	String oemOid = CommonUtil.getOIDString(oem);

	resMap.put("projectNo", projectNo);
	resMap.put("projectName", projectName);
	resMap.put("lastCustomerName", lastCustomerName);
	resMap.put("lastCustomerCode", lastCustomerCode);
	resMap.put("subContractorName", subContractorName);
	resMap.put("subContractorCode", subContractorCode);
	resMap.put("oemName", oemName);
	resMap.put("oemOid", oemOid);

	return resMap;
    }

    public List<Map<String, Object>> getIssuePartMapList(Map<String, Object> reqMap) throws Exception {

	List<Map<String, Object>> partList = new ArrayList<Map<String, Object>>();
	String oid = (String) reqMap.get("oid");

	Persistable obj = CommonUtil.getObject(oid);

	KETIssueMaster im = null;

	if (obj instanceof KETIssueMaster) {
	    im = (KETIssueMaster) CommonUtil.getObject(oid);
	} else if (obj instanceof KETIssueTask) {
	    im = ((KETIssueTask) CommonUtil.getObject(oid)).getIssueMaster();
	}

	QueryResult qr = getPartLinkList(im);

	while (qr.hasMoreElements()) {
	    KETIssuePartLink link = (KETIssuePartLink) qr.nextElement();
	    Map<String, Object> partMap = new HashMap<String, Object>();
	    partMap.put("partNo", link.getPartMaster().getNumber());
	    partMap.put("partName", link.getPartMaster().getName());
	    partMap.put("partOid", CommonUtil.getOIDString(link.getPartMaster()));
	    partList.add(partMap);
	}

	Collections.sort(partList, new Comparator<Object>() {

	    @Override
	    public int compare(Object obj1, Object obj2) {

		@SuppressWarnings("unchecked")
		HashMap<String, Object> map1 = (HashMap<String, Object>) obj1;
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map2 = (HashMap<String, Object>) obj2;

		int ret = ((String) map1.get("partNo")).compareTo((String) map2.get("partNo"));

		return ret;
	    }
	});

	return partList;
    }

    public QueryResult getPartLinkList(KETIssueMaster im) throws Exception {

	return PersistenceHelper.manager.navigate(im, KETIssuePartLink.PART_MASTER_ROLE, KETIssuePartLink.class, false);
    }
}
