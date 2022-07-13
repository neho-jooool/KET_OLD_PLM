package ext.ket.issue.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import wt.content.ContentHelper;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerRef;
import wt.introspection.BaseTableInfo;
import wt.introspection.ClassInfo;
import wt.introspection.DatabaseInfo;
import wt.introspection.WTIntrospector;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleTemplate;
import wt.org.WTUser;
import wt.part.WTPartMaster;
import wt.pds.StatementSpec;
import wt.pom.Transaction;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.CompositeQuerySpec;
import wt.query.KeywordExpression;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.historyprocess.HistoryHelper;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.common.util.ObjectUtil;
import ext.ket.issue.entity.KETGeneralissueLink;
import ext.ket.issue.entity.KETIssueMaster;
import ext.ket.issue.entity.KETIssueMasterDTO;
import ext.ket.issue.entity.KETIssueMemberLink;
import ext.ket.issue.entity.KETIssuePartLink;
import ext.ket.issue.entity.KETIssueTask;
import ext.ket.issue.entity.KETIssueTaskDTO;
import ext.ket.issue.entity.KETIssueToDoDTO;
import ext.ket.issue.util.IssueUtil;
import ext.ket.sales.entity.KETSalesProject;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.shared.util.SessionUtil;

/*********************************************************
 * @description
 * @author dhkim
 * @date 2017. 11. 17. 오전 10:52:12
 * @Pakage ext.ket.project.cost.service
 * @class StandardCostBomService
 *********************************************************/
public class StandardIssueService extends StandardManager implements IssueService, Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(StandardIssueService.class);

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 5. 25. 오전 10:08:11
     * @method newStandardIssueService
     * @return StandardIssueService
     * @throws WTException
     * </pre>
     */
    public static StandardIssueService newStandardIssueService() throws WTException {
	StandardIssueService instance = new StandardIssueService();
	instance.initialize();
	return instance;
    }

    /**
     * <pre>
     * @description 고객 요구사항 완료
     * @author dhkim
     * @date 2018. 6. 4. 오후 2:16:04
     * @method completeIssueMaster
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public Map<String, Object> completeIssueMaster(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	String oid = (String) reqMap.get("oid");
	boolean isComplete = true;

	KETIssueMaster im = (KETIssueMaster) CommonUtil.getObject(oid);

	List<KETIssueTask> itList = IssueUtil.manager.getIssueTaskList(im);

	String notCompItName = "";

	for (KETIssueTask it : itList) {

	    if (!IssueUtil.COMPLETE.equals(it.getIssueState())) {
		notCompItName = it.getSubject();
		isComplete = false;
		break;
	    }
	}

	if (isComplete) {

	    im.setIssueState(IssueUtil.COMPLETE);
	    im.setCompleteDate(DateUtil.getCurrentTimestamp());

	    PersistenceHelper.manager.save(im);
	    resMap.put("message", "완료 되었습니다.");
	    resMap.put("result", true);

	} else {
	    resMap.put("message", "세부 진행 항목 [" + notCompItName + "]가 진행중입니다.");
	    resMap.put("result", false);
	}

	return resMap;
    }

    /**
     * <pre>
     * @description 고객대응관리 검색 목록
     * @author dhkim
     * @date 2018. 6. 4. 오후 2:16:08
     * @method findPagingList
     * @param im
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public PageControl findPagingList(KETIssueMasterDTO im) throws Exception {
	QuerySpec query = getIssueMasterQuery(im);
	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	if (StringUtil.isEmpty(pageSessionId)) {
	    pageControl = CommonSearchHelper.find(im.getFormPage(), im.getFormPage(), query);
	} else {
	    pageControl = CommonSearchHelper.find(im.getFormPage(), im.getFormPage(), im.getPage() + 1, pageSessionId);
	}
	return pageControl;
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 6. 27. 오후 5:00:01
     * @method findTaskPagingList
     * @param im
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public PageControl findTaskPagingList(KETIssueMasterDTO im) throws Exception {
	QuerySpec query = getIssueTaskQuery(im);
	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	if (StringUtil.isEmpty(pageSessionId)) {
	    pageControl = CommonSearchHelper.find(im.getFormPage(), im.getFormPage(), query);
	} else {
	    pageControl = CommonSearchHelper.find(im.getFormPage(), im.getFormPage(), im.getPage() + 1, pageSessionId);
	}
	return pageControl;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 6. 27. 오후 5:00:06
     * @method getIssueTaskQuery
     * @param im
     * @return QuerySpec
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings({ "deprecation", "rawtypes" })
    private QuerySpec getIssueTaskQuery(KETIssueMasterDTO im) throws Exception {
	QuerySpec qs = new QuerySpec();
	qs.setAdvancedQueryEnabled(true);

	int idx = qs.addClassList(KETIssueTask.class, true);
	int idx2 = qs.addClassList(KETIssueMaster.class, true);

	ClassAttribute ca0 = new ClassAttribute(KETIssueTask.class, KETIssueTask.ISSUE_MASTER_REFERENCE + "."
	        + WTAttributeNameIfc.REF_OBJECT_ID);
	ClassAttribute ca1 = new ClassAttribute(KETIssueMaster.class, WTAttributeNameIfc.ID_NAME);
	SearchCondition sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	sc0.setFromIndicies(new int[] { idx, idx2 }, 0);
	sc0.setOuterJoin(0);

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(sc0, new int[] { idx, idx2 });

	// 최신버전
	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(KETIssueTask.class, KETIssueTask.LASTEST, SearchCondition.IS_TRUE, true), new int[] { idx });

	// 세부 담당자
	if (StringUtil.checkString(im.getTmUserOid())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueTask.class, KETIssueTask.WORKER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
		    SearchCondition.EQUAL, CommonUtil.getOIDLongValue(im.getTmUserOid())), new int[] { idx });
	}

	// 세부 담당부서
	if (StringUtil.checkString(im.getTaskDeptCode())) {

	    String deptCode = im.getTaskDeptCode();
	    if (StringUtil.checkString(deptCode) && deptCode.indexOf("Department") >= 0) {
		Department dept = (Department) CommonUtil.getObject(deptCode);
		deptCode = dept.getCode();
	    }

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueTask.class, KETIssueTask.DEPT_CODE, SearchCondition.EQUAL, deptCode),
		    new int[] { idx });
	}

	// 세부항목 상태
	if (StringUtil.checkString(im.getIssueTaskState())) {

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(
		    new SearchCondition(KETIssueTask.class, KETIssueTask.ISSUE_STATE, SearchCondition.EQUAL, im.getIssueTaskState()),
		    new int[] { idx });

	    boolean isDelayState = Boolean.parseBoolean(im.getDelayState());

	    System.out.println(isDelayState);
	    System.out.println(im.getDelayState());

	    // 지연검색
	    if (IssueUtil.INPROGRESS.equals(im.getIssueTaskState()) && isDelayState) {

		String alias = qs.getFromClause().getAliasAt(idx);
		String subQs = "(SELECT SYSDATE-" + alias + ".REQUESTDATE-1 FROM DUAL)";

		KeywordExpression kexp = new KeywordExpression(subQs);
		KeywordExpression kexp2 = new KeywordExpression("0");
		sc0 = new SearchCondition(kexp, SearchCondition.GREATER_THAN_OR_EQUAL, kexp2);
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(sc0, new int[] { idx });
	    }
	}

	// 요청부서
	if (StringUtil.checkString(im.getDeptCode())) {

	    String deptCode = im.getDeptCode();
	    if (StringUtil.checkString(deptCode) && deptCode.indexOf("Department") >= 0) {
		Department dept = (Department) CommonUtil.getObject(deptCode);
		deptCode = dept.getCode();
	    }

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.DEPT_CODE, SearchCondition.EQUAL, deptCode),
		    new int[] { idx2 });
	}

	// 요청번호
	if (StringUtil.checkString(im.getReqNo())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.REQ_NO, SearchCondition.EQUAL, im.getReqNo()),
		    new int[] { idx2 });
	}

	// 요청명
	if (StringUtil.checkString(im.getReqName())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.REQ_NAME, SearchCondition.LIKE, "%" + im.getReqName()
		    + "%"), new int[] { idx2 });
	}

	// 요청구분
	if (StringUtil.checkString(im.getReqCode())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.REQ_CODE, SearchCondition.EQUAL, im.getReqCode()),
		    new int[] { idx2 });
	}

	// 주 담당자
	if (StringUtil.checkString(im.getManagerOid())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.MANAGER_REFERENCE + "."
		    + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(im.getManagerOid())),
		    new int[] { idx2 });
	}

	// 요청서 상태
	if (StringUtil.checkString(im.getIssueState())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(
		    new SearchCondition(KETIssueMaster.class, KETIssueMaster.ISSUE_STATE, SearchCondition.EQUAL, im.getIssueState()),
		    new int[] { idx2 });
	}

	// 완료 요청일
	if (StringUtil.checkString(im.getRequestStartDate())) {
	    Timestamp requestDate = DateUtil.getTimestampFormat(im.getRequestStartDate(), "yyyy-MM-dd");
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.REQUEST_DATE, SearchCondition.GREATER_THAN_OR_EQUAL,
		    requestDate), new int[] { idx2 });
	}

	// 완료 요청일
	if (StringUtil.checkString(im.getRequestEndDate())) {
	    Timestamp requestDate = DateUtil.getTimestampFormat(im.getRequestEndDate(), "yyyy-MM-dd");
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.REQUEST_DATE, SearchCondition.LESS_THAN, requestDate),
		    new int[] { idx2 });
	}

	// 자동차사
	if (StringUtil.checkString(im.getLastCustomer())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(
		    new SearchCondition(KETIssueMaster.class, KETIssueMaster.LAST_CUSTOMER, SearchCondition.EQUAL, im.getLastCustomer()),
		    new int[] { idx2 });
	}

	// 고객사
	if (StringUtil.checkString(im.getSubContractor())) {
	    String[] subContractor = im.getSubContractor().split(",");

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(new ClassAttribute(KETIssueMaster.class, KETIssueMaster.SUB_CONTRACTOR), SearchCondition.IN,
		    new ArrayExpression(subContractor)), new int[] { idx2 });
	}

	// 대표차종
	if (StringUtil.checkString(im.getOemOid())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.OEM_TYPE_REFERENCE + "."
		    + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(im.getOemOid())),
		    new int[] { idx2 });
	}

	// 품번
	if (StringUtil.checkString(im.getPartNos())) {
	    String partNos = StringUtils.replace(im.getPartNos(), ",", "|");

	    String alias = qs.getFromClause().getAliasAt(idx2);
	    String subQs = "(SELECT IDA2A2 FROM KETIssueMaster WHERE REGEXP_LIKE(PARTNOS,'" + partNos + "') )";

	    KeywordExpression kexp = new KeywordExpression(alias + ".IDA2A2");
	    KeywordExpression kexp2 = new KeywordExpression(subQs);
	    SearchCondition sc1 = new SearchCondition(kexp, SearchCondition.IN, kexp2);
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(sc1, new int[] { idx2 });
	}

	// 상세구분
	if (StringUtil.checkString(im.getDetailCode())) {
	    // if (qs.getConditionCount() > 0) qs.appendAnd();
	    // qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.DETAIL_CODE , SearchCondition.EQUAL,
	    // im.getDetailCode()), new int[] { idx2 });
	    //
	    String[] DetailCode = im.getDetailCode().split(",");

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(new ClassAttribute(KETIssueMaster.class, KETIssueMaster.DETAIL_CODE), SearchCondition.IN,
		    new ArrayExpression(DetailCode)), new int[] { idx2 });
	}

	// 사업부
	if (StringUtil.checkString(im.getDivisionCode())) {
	    String[] DivisionCode = im.getDivisionCode().split(",");

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(new ClassAttribute(KETIssueMaster.class, KETIssueMaster.DIVISION_CODE), SearchCondition.IN,
		    new ArrayExpression(DivisionCode)), new int[] { idx2 });
	}

	// 회의대상
	if (StringUtil.checkString(im.getMeetingTarget())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(
		    new SearchCondition(KETIssueMaster.class, KETIssueMaster.MEETING_TARGET, SearchCondition.EQUAL, im.getMeetingTarget()),
		    new int[] { idx2 });
	}

	// 소팅로직
	if (!StringUtil.isTrimToEmpty(im.getSortName())) {

	    String checkName = im.getSortName();
	    boolean sortOrder = "-".equals(checkName.substring(0, 1));

	    Class sortClass = KETIssueTask.class;
	    int sortIdx = idx;
	    String sortName = KETIssueTask.CREATE_TIMESTAMP;

	    if (checkName.indexOf("issueMaster") >= 0) {
		sortClass = KETIssueMaster.class;
		sortIdx = idx2;
		sortName = KETIssueMaster.CREATE_TIMESTAMP;

		if (sortOrder)
		    checkName = checkName.substring(12);
		else
		    checkName = checkName.substring(11);
	    } else {
		if (sortOrder)
		    checkName = checkName.substring(1);
	    }

	    switch (checkName) {
	    case "subject":
		sortName = KETIssueTask.SUBJECT;
		break;
	    case "workerName":
		sortName = KETIssueTask.WORKER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID;
		break;
	    case "workerDeptName":
		sortName = KETIssueTask.DEPT_CODE;
		break;
	    case "completeDate":
		sortName = KETIssueTask.COMPLETE_DATE;
		break;
	    case "requestDate":
		sortName = KETIssueTask.REQUEST_DATE;
		break;
	    case "issueStateName":
		sortName = KETIssueTask.ISSUE_STATE;
		SearchUtil.setOrderBy(qs, sortClass, sortIdx, sortName, sortOrder);
		sortName = KETIssueTask.REQUEST_DATE;
		break;
	    case "ReqNo":
		sortName = KETIssueMaster.REQ_NO;
		break;
	    case "ReqName":
		sortName = KETIssueMaster.REQ_NAME;
		break;
	    case "LastCustomerName":
		sortName = KETIssueMaster.LAST_CUSTOMER;
		break;
	    case "SubContractorName":
		sortName = KETIssueMaster.SUB_CONTRACTOR;
		break;
	    case "ReqCodeName":
		sortName = KETIssueMaster.REQ_CODE;
		break;
	    case "RankName":
		sortName = KETIssueMaster.RANK;
		break;
	    case "CreateDate":
		sortName = KETIssueMaster.CREATE_TIMESTAMP;
		break;
	    case "RequestDate":
		sortName = KETIssueMaster.REQUEST_DATE;
		break;
	    case "ManagerName":
		sortName = KETIssueMaster.MANAGER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID;
		break;
	    case "CompleteDate":
		sortName = KETIssueMaster.COMPLETE_DATE;
		break;
	    case "IssueStateCnt":
		sortName = KETIssueMaster.ISSUE_STATE;
		break;
	    default:
		break;
	    }

	    SearchUtil.setOrderBy(qs, sortClass, sortIdx, sortName, sortOrder);
	}

	return qs;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 6. 7. 오후 3:59:22
     * @method getIssueMasterList
     * @param pboOid
     * @return List<KETIssueMasterDTO>
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    @Override
    public List<KETIssueMasterDTO> getIssueMasterList(String pboOid) throws Exception {

	List<KETIssueMasterDTO> list = new ArrayList<KETIssueMasterDTO>();

	KETIssueMasterDTO im = new KETIssueMasterDTO();
	im.setPboOid(pboOid);

	QuerySpec qs = getIssueMasterQuery(im);

	QueryResult qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {

	    Object[] objArr = (Object[]) qr.nextElement();
	    KETIssueMaster iMaster = (KETIssueMaster) objArr[0];
	    KETIssueMasterDTO imDTO = new KETIssueMasterDTO(iMaster);

	    list.add(imDTO);
	}

	return list;
    }

    /**
     * <pre>
     * @description  고객대응관리 검색 쿼리
     * @author dhkim
     * @date 2018. 6. 4. 오후 2:16:10
     * @method getIssueMasterQuery
     * @param im
     * @return QuerySpec
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("rawtypes")
    private QuerySpec getIssueMasterQuery(KETIssueMasterDTO im) throws Exception {

	QuerySpec qs = new QuerySpec();
	qs.setAdvancedQueryEnabled(true);

	int idx = qs.addClassList(KETIssueMaster.class, true);

	if (StringUtil.checkString(im.getPboOid()) || StringUtil.checkString(im.getPboNo()) || StringUtil.checkString(im.getPboName())) {

	    int idx2 = qs.addClassList(KETGeneralissueLink.class, false);

	    ClassAttribute ca0 = new ClassAttribute(KETIssueMaster.class, WTAttributeNameIfc.ID_NAME);
	    ClassAttribute ca1 = new ClassAttribute(KETGeneralissueLink.class, KETGeneralissueLink.ROLE_AOBJECT_REF + "."
		    + WTAttributeNameIfc.REF_OBJECT_ID);
	    SearchCondition sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	    sc0.setFromIndicies(new int[] { idx, idx2 }, 0);
	    sc0.setOuterJoin(0);

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(sc0, new int[] { idx, idx2 });

	    int idx3 = qs.addClassList(KETSalesProject.class, false);

	    ca0 = new ClassAttribute(KETGeneralissueLink.class, KETGeneralissueLink.ROLE_BOBJECT_REF + "."
		    + WTAttributeNameIfc.REF_OBJECT_ID);
	    ca1 = new ClassAttribute(KETSalesProject.class, WTAttributeNameIfc.ID_NAME);
	    sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	    sc0.setFromIndicies(new int[] { idx2, idx3 }, 0);
	    sc0.setOuterJoin(0);

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(sc0, new int[] { idx2, idx3 });

	    if (StringUtil.checkString(im.getPboOid())) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(
		        new SearchCondition(KETSalesProject.class, WTAttributeNameIfc.ID_NAME, SearchCondition.EQUAL, CommonUtil
		                .getOIDLongValue(im.getPboOid())), new int[] { idx3 });
	    }

	    // 프로젝트번호
	    if (StringUtil.checkString(im.getPboNo())) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(
		        new SearchCondition(KETSalesProject.class, KETSalesProject.PROJECT_NO, SearchCondition.EQUAL, im.getPboNo()),
		        new int[] { idx3 });
	    }

	    // 프로젝트명
	    if (StringUtil.checkString(im.getPboName())) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(
		        new SearchCondition(KETSalesProject.class, KETSalesProject.PROJECT_NAME, SearchCondition.LIKE, "%"
		                + im.getPboName() + "%"), new int[] { idx3 });
	    }

	    // SOP START DATE
	    if (StringUtil.checkString(im.getSopStartDate())) {
		Timestamp sop = DateUtil.getTimestampFormat(im.getSopStartDate(), "yyyy-MM-dd");
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(new SearchCondition(KETSalesProject.class, KETSalesProject.SOP, SearchCondition.GREATER_THAN_OR_EQUAL, sop),
		        new int[] { idx3 });
	    }

	    // SOP END DATE
	    if (StringUtil.checkString(im.getSopEndDate())) {
		Timestamp sop = DateUtil.getTimestampFormat(im.getSopEndDate(), "yyyy-MM-dd");
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(new SearchCondition(KETSalesProject.class, KETSalesProject.SOP, SearchCondition.LESS_THAN, sop),
		        new int[] { idx3 });
	    }

	}

	// 요청부서
	if (StringUtil.checkString(im.getDeptCode())) {

	    String deptCode = im.getDeptCode();
	    if (StringUtil.checkString(deptCode) && deptCode.indexOf("Department") >= 0) {
		Department dept = (Department) CommonUtil.getObject(deptCode);
		deptCode = dept.getCode();
	    }

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.DEPT_CODE, SearchCondition.EQUAL, deptCode),
		    new int[] { idx });
	}

	// 요청번호
	if (StringUtil.checkString(im.getReqNo())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.REQ_NO, SearchCondition.EQUAL, im.getReqNo()),
		    new int[] { idx });
	}

	// 요청명
	if (StringUtil.checkString(im.getReqName())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.REQ_NAME, SearchCondition.LIKE, "%" + im.getReqName()
		    + "%"), new int[] { idx });
	}

	// 요청구분
	if (StringUtil.checkString(im.getReqCode())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.REQ_CODE, SearchCondition.EQUAL, im.getReqCode()),
		    new int[] { idx });
	}

	// 주 담당자
	if (StringUtil.checkString(im.getManagerOid())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.MANAGER_REFERENCE + "."
		    + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(im.getManagerOid())),
		    new int[] { idx });
	}

	// 요청서 상태
	if (StringUtil.checkString(im.getIssueState())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(
		    new SearchCondition(KETIssueMaster.class, KETIssueMaster.ISSUE_STATE, SearchCondition.EQUAL, im.getIssueState()),
		    new int[] { idx });
	}

	// 완료 요청일 - 등록일자로 변경함
	if (StringUtil.checkString(im.getRequestStartDate())) {
	    Timestamp requestDate = DateUtil.getTimestampFormat(im.getRequestStartDate(), "yyyy-MM-dd");
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.CREATE_TIMESTAMP,
		    SearchCondition.GREATER_THAN_OR_EQUAL, requestDate), new int[] { idx });
	}

	// 완료 요청일 - 등록일자로 변경함
	if (StringUtil.checkString(im.getRequestEndDate())) {
	    Timestamp requestDate = DateUtil.getTimestampFormat(im.getRequestEndDate(), "yyyy-MM-dd");
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.CREATE_TIMESTAMP, SearchCondition.LESS_THAN,
		    requestDate), new int[] { idx });
	}

	// 완료일자 시작일
	if (StringUtil.checkString(im.getCompleteStartDate())) {
	    Timestamp sop = DateUtil.getTimestampFormat(im.getCompleteStartDate(), "yyyy-MM-dd");
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.COMPLETE_DATE, SearchCondition.GREATER_THAN_OR_EQUAL,
		    sop), new int[] { idx });
	}

	// 완료일자 종료일
	if (StringUtil.checkString(im.getCompleteEndDate())) {
	    Timestamp sop = DateUtil.getTimestampFormat(im.getCompleteEndDate(), "yyyy-MM-dd");
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.COMPLETE_DATE, SearchCondition.LESS_THAN, sop),
		    new int[] { idx });
	}

	// 세부 담당자
	if (StringUtil.checkString(im.getTmUserOid())) {

	    int idx4 = qs.addClassList(KETIssueTask.class, false);

	    ClassAttribute ca0 = new ClassAttribute(KETIssueMaster.class, WTAttributeNameIfc.ID_NAME);
	    ClassAttribute ca1 = new ClassAttribute(KETIssueTask.class, KETIssueTask.ISSUE_MASTER_REFERENCE + "."
		    + WTAttributeNameIfc.REF_OBJECT_ID);
	    SearchCondition sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	    sc0.setFromIndicies(new int[] { idx, idx4 }, 0);
	    sc0.setOuterJoin(0);

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(sc0, new int[] { idx, idx4 });

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueTask.class, KETIssueTask.WORKER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
		    SearchCondition.EQUAL, CommonUtil.getOIDLongValue(im.getTmUserOid())), new int[] { idx4 });
	}

	// 자동차사
	if (StringUtil.checkString(im.getLastCustomer())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(
		    new SearchCondition(KETIssueMaster.class, KETIssueMaster.LAST_CUSTOMER, SearchCondition.EQUAL, im.getLastCustomer()),
		    new int[] { idx });
	}

	// 고객사
	if (StringUtil.checkString(im.getSubContractor())) {
	    String[] subContractor = im.getSubContractor().split(",");

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(new ClassAttribute(KETIssueMaster.class, KETIssueMaster.SUB_CONTRACTOR), SearchCondition.IN,
		    new ArrayExpression(subContractor)), new int[] { idx });
	}

	// 대표차종
	if (StringUtil.checkString(im.getOemOid())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETIssueMaster.class, KETIssueMaster.OEM_TYPE_REFERENCE + "."
		    + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(im.getOemOid())),
		    new int[] { idx });
	}

	// 품번
	if (StringUtil.checkString(im.getPartNos())) {
	    String partNos = StringUtils.replace(im.getPartNos(), ",", "|");
	    String alias = qs.getFromClause().getAliasAt(idx);
	    String subQs = "(SELECT IDA2A2 FROM KETIssueMaster WHERE REGEXP_LIKE(PARTNOS,'" + partNos + "') )";

	    KeywordExpression kexp = new KeywordExpression(alias + ".IDA2A2");
	    KeywordExpression kexp2 = new KeywordExpression(subQs);
	    SearchCondition sc0 = new SearchCondition(kexp, SearchCondition.IN, kexp2);
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(sc0, new int[] { idx });
	}

	// 상세구분
	if (StringUtil.checkString(im.getDetailCode())) {
	    String[] DetailCode = im.getDetailCode().split(",");

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(new ClassAttribute(KETIssueMaster.class, KETIssueMaster.DETAIL_CODE), SearchCondition.IN,
		    new ArrayExpression(DetailCode)), new int[] { idx });
	}

	// 사업부
	if (StringUtil.checkString(im.getDivisionCode())) {
	    String[] DivisionCode = im.getDivisionCode().split(",");

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(new ClassAttribute(KETIssueMaster.class, KETIssueMaster.DIVISION_CODE), SearchCondition.IN,
		    new ArrayExpression(DivisionCode)), new int[] { idx });
	}

	// 회의대상
	if (StringUtil.checkString(im.getMeetingTarget())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(
		    new SearchCondition(KETIssueMaster.class, KETIssueMaster.MEETING_TARGET, SearchCondition.EQUAL, im.getMeetingTarget()),
		    new int[] { idx });
	}

	// 소팅로직
	if (!StringUtil.isTrimToEmpty(im.getSortName())) {

	    String checkName = im.getSortName();
	    Class sortClass = KETIssueMaster.class;
	    int sortIdx = idx;
	    String sortName = KETIssueMaster.REQ_NO;
	    boolean isDesc = checkName.startsWith("-"); // 내림차순
	    if (checkName.startsWith("-")) {
		checkName = checkName.substring(1);
	    }

	    switch (checkName) {
	    case "reqNo":
		sortName = KETIssueMaster.REQ_NO;
		break;
	    case "reqName":
		sortName = KETIssueMaster.REQ_NAME;
		break;
	    case "lastCustomerName":
		sortName = KETIssueMaster.LAST_CUSTOMER;
		break;
	    case "subContractorName":
		sortName = KETIssueMaster.SUB_CONTRACTOR;
		break;
	    case "reqCodeName":
		sortName = KETIssueMaster.REQ_CODE;
		break;
	    case "rankName":
		sortName = KETIssueMaster.RANK;
		break;
	    case "createDate":
		sortName = KETIssueMaster.CREATE_TIMESTAMP;
		break;
	    case "requestDate":
		sortName = KETIssueMaster.REQUEST_DATE;
		break;
	    case "completeDate":
		sortName = KETIssueMaster.COMPLETE_DATE;
		break;
	    case "managerName":
		sortName = KETIssueMaster.MANAGER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID;
		break;
	    // case "pboNo" :
	    // sortClass = KETSalesProject.class;
	    // sortIdx = idx3;
	    // sortName = KETSalesProject.PROJECT_NO;
	    // break;
	    // case "pboName" :
	    // sortClass = KETSalesProject.class;
	    // sortIdx = idx3;
	    // sortName = KETSalesProject.PROJECT_NAME;
	    // break;
	    // case "pboState" :
	    // sortClass = KETSalesProject.class;
	    // sortIdx = idx3;
	    // sortName = KETSalesProject.LIFE_CYCLE_STATE;
	    // break;
	    default:
		break;
	    }

	    if (isDesc) {
		SearchUtil.setOrderBy(qs, sortClass, sortIdx, sortName, true);
	    } else {
		SearchUtil.setOrderBy(qs, sortClass, sortIdx, sortName, false);
	    }
	}

	LOGGER.info("ISSUE MASTER QUERY ############## " + qs);

	return qs;
    }

    /**
     * <pre>
     * @description 고객대응관리 To-Do 검색 목록
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:37:17
     * @method findToDoPagingList
     * @param itd
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public PageControl findToDoPagingList(KETIssueToDoDTO itd) throws Exception {

	StatementSpec qs = getIssueToDoQuery(itd);

	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	if (StringUtil.isEmpty(pageSessionId)) {
	    pageControl = CommonSearchHelper.find(itd.getFormPage(), itd.getFormPage(), qs);
	} else {
	    pageControl = CommonSearchHelper.find(itd.getFormPage(), itd.getFormPage(), itd.getPage() + 1, pageSessionId);
	}
	return pageControl;
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 6. 11. 오후 4:59:43
     * @method getIssueTodoListCnt
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public int getIssueTodoListCnt() throws Exception {

	KETIssueToDoDTO itd = new KETIssueToDoDTO();
	StatementSpec qs = getIssueToDoQuery(itd);
	QueryResult qr = PersistenceHelper.manager.find(qs);

	return qr.size();
    }

    /**
     * <pre>
     * @description 고객대응관리 To-Do 검색 쿼리
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:37:26
     * @method getIssueToDoQuery
     * @param itd
     * @return StatementSpec
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    private StatementSpec getIssueToDoQuery(KETIssueToDoDTO itd) throws Exception {

	long toDoUser = CommonUtil.getOIDLongValue(SessionHelper.manager.getPrincipal());

	// 요청 사항 쿼리 ####################################################################

	Class<KETIssueMaster> imClass = KETIssueMaster.class;
	QuerySpec imQs = new QuerySpec();
	imQs.setAdvancedQueryEnabled(true);
	int imIdx = imQs.appendClassList(imClass, false);

	ClassInfo classInfo = WTIntrospector.getClassInfo(imClass);
	DatabaseInfo dbInfo = classInfo.getDatabaseInfo();
	BaseTableInfo btInfo = dbInfo.getBaseTableInfo();
	String objName = btInfo.getColumnDescriptor(WTAttributeNameIfc.OID_CLASSNAME).getColumnName();
	String objId = btInfo.getColumnDescriptor(WTAttributeNameIfc.ID_NAME).getColumnName();
	KeywordExpression kexp = new KeywordExpression(imQs.getFromClause().getAliasAt(imIdx) + "." + objName + "||':'||"
	        + imQs.getFromClause().getAliasAt(imIdx) + "." + objId);
	kexp.setColumnAlias("imOid");

	imQs.appendSelect(kexp, new int[] { imIdx }, false);

	imQs.appendWhere(new SearchCondition(imClass, KETIssueMaster.MANAGER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
	        SearchCondition.EQUAL, toDoUser), new int[] { imIdx });

	if (imQs.getConditionCount() > 0)
	    imQs.appendAnd();
	imQs.appendWhere(new SearchCondition(new ClassAttribute(imClass, KETIssueMaster.ISSUE_STATE), SearchCondition.IN,
	        new ArrayExpression(new String[] { IssueUtil.INPROGRESS, IssueUtil.INWORK })), new int[] { imIdx });

	String alias = imQs.getFromClause().getAliasAt(imIdx);
	String subQs = "(SELECT COUNT(ITCNT0.idA2A2) FROM KETISSUETASK ITCNT0 WHERE ((ITCNT0.IDA3B8 = " + alias + ".IDA2A2)"
	        + " AND (ITCNT0.ISSUESTATE IN ('IST002','IST001')) AND (ITCNT0.LASTEST = 1)))";

	kexp = new KeywordExpression("0");
	KeywordExpression kexp2 = new KeywordExpression(subQs);
	SearchCondition sc0 = new SearchCondition(kexp, SearchCondition.EQUAL, kexp2);
	if (imQs.getConditionCount() > 0)
	    imQs.appendAnd();
	imQs.appendWhere(sc0, new int[] { imIdx });

	LOGGER.info("imQs ############ " + imQs);

	// 요청 사항 쿼리 END #################################################################

	// 세부 요청 사항 쿼리 ################################################################
	Class<KETIssueTask> itClass = KETIssueTask.class;
	QuerySpec itQs = new QuerySpec();
	itQs.setAdvancedQueryEnabled(true);
	int itIdx = itQs.appendClassList(itClass, false);

	classInfo = WTIntrospector.getClassInfo(itClass);
	dbInfo = classInfo.getDatabaseInfo();
	btInfo = dbInfo.getBaseTableInfo();
	objName = btInfo.getColumnDescriptor(WTAttributeNameIfc.OID_CLASSNAME).getColumnName();
	objId = btInfo.getColumnDescriptor(WTAttributeNameIfc.ID_NAME).getColumnName();
	kexp = new KeywordExpression(itQs.getFromClause().getAliasAt(itIdx) + "." + objName + "||':'||"
	        + itQs.getFromClause().getAliasAt(itIdx) + "." + objId);
	kexp.setColumnAlias("itOid");

	itQs.appendSelect(kexp, new int[] { itIdx }, false);

	itQs.appendWhere(new SearchCondition(itClass, KETIssueTask.WORKER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
	        SearchCondition.EQUAL, toDoUser), new int[] { itIdx });

	if (itQs.getConditionCount() > 0)
	    itQs.appendAnd();
	itQs.appendWhere(new SearchCondition(itClass, KETIssueTask.LASTEST, SearchCondition.IS_TRUE, true), new int[] { itIdx });

	if (itQs.getConditionCount() > 0)
	    itQs.appendAnd();
	itQs.appendWhere(new SearchCondition(itClass, KETIssueTask.ISSUE_STATE, SearchCondition.EQUAL, IssueUtil.INPROGRESS),
	        new int[] { itIdx });

	// 세부 요청 사항 쿼리 END #################################################################

	CompositeQuerySpec qs = new CompositeQuerySpec();
	qs.setAdvancedQueryEnabled(true);
	qs.addComponent(imQs);
	qs.addComponent(itQs);

	return qs;
    }

    /**
     * <pre>
     * @description 세부 요청사항 저장
     * @author dhkim
     * @date 2018. 5. 28. 오후 3:47:30
     * @method saveIssueTask
     * @param itDTO
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public Map<String, Object> saveIssueTask(KETIssueTaskDTO itDTO, Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	Transaction trx = new Transaction();
	try {

	    trx.start();
	    List<WTUser> toList = new ArrayList<WTUser>();
	    boolean isComplete = Boolean.parseBoolean((String) reqMap.get("isComplete"));

	    String oid = itDTO.getOid();
	    String workerOid = itDTO.getWorkerOid();
	    Object webEditor = itDTO.getWebEditor();
	    Object webEditorText = itDTO.getWebEditorText();

	    KETIssueTask it = (KETIssueTask) CommonUtil.getObject(oid);

	    if (it.getBranchId() == 0)
		it.setBranchId(CommonUtil.getOIDLongValue(oid));
	    int version = it.getVersion();
	    it.setLastest(false);

	    KETIssueTask itOld = (KETIssueTask) PersistenceHelper.manager.save(it);

	    it = (KETIssueTask) HistoryHelper.duplicate(it);
	    it.setVersion(++version);
	    it.setLastest(true);

	    KETIssueMaster im = it.getIssueMaster();

	    it.setWebEditor(webEditor);
	    it.setWebEditorText(webEditorText);

	    if (isComplete) {

		it.setIssueState(IssueUtil.COMPLETE);
		it.setCompleteDate(DateUtil.getCurrentTimestamp());

	    } else if (StringUtil.checkString(workerOid)) {

		WTUser oldWorker = it.getWorker();

		if (oldWorker == null || !workerOid.equals(CommonUtil.getOIDString(oldWorker))) {
		    it.setWorker((WTUser) CommonUtil.getObject(workerOid));
		    toList.add(it.getWorker());
		    Department dept = DepartmentHelper.manager.getDepartment(it.getWorker());
		    if (dept != null)
			it.setDeptCode(dept.getCode());
		    else
			it.setDeptCode(null);
		}
	    }

	    it = (KETIssueTask) PersistenceHelper.manager.save(it);

	    ContentHelper.service.copyContent(itOld, it);

	    resMap.put("oid", CommonUtil.getOIDString(it));

	    KETContentHelper.service.updateContent(it, itDTO);

	    // 참조자 복사
	    List<WTUser> memberList = new ArrayList<WTUser>();

	    QueryResult qr = PersistenceHelper.manager.navigate(itOld, KETIssueMemberLink.MEMBER_ROLE, KETIssueMemberLink.class, true);

	    while (qr.hasMoreElements()) {

		WTUser member = (WTUser) qr.nextElement();
		KETIssueMemberLink memberLink = KETIssueMemberLink.newKETIssueMemberLink(it, member);
		PersistenceHelper.manager.save(memberLink);
		memberList.add(member);

	    }

	    // 담당자 메일발송 처리
	    if (toList.size() > 0 || IssueUtil.COMPLETE.equals(it.getIssueState())) {

		if (!IssueUtil.COMPLETE.equals(it.getIssueState())) {
		    KETMailHelper.service.sendFormMail("08138", "NoticeMailLine5.html", it, KETObjectUtil.getUser("wcadmin"), toList);
		}

		if (StringUtil.checkString(it.getDeptCode())) {

		    WTUser chief = null;
		    Department dept = DepartmentHelper.manager.getDepartment(it.getDeptCode());

		    if (dept != null)
			chief = PeopleHelper.manager.getChiefUser(dept);

		    if (chief != null && !chief.equals(it.getWorker())) {
			toList = new ArrayList<WTUser>();
			toList.add(chief);

			if (IssueUtil.COMPLETE.equals(it.getIssueState())) {
			    KETMailHelper.service.sendFormMail("08140", "NoticeMailLine6.html", it, KETObjectUtil.getUser("wcadmin"),
				    toList);
			} else {
			    KETMailHelper.service.sendFormMail("08139", "NoticeMailLine5.html", it, KETObjectUtil.getUser("wcadmin"),
				    toList);
			}
		    }
		}

		if (IssueUtil.COMPLETE.equals(it.getIssueState())) {
		    KETMailHelper.service.sendFormMail("08140", "NoticeMailLine6.html", it, KETObjectUtil.getUser("wcadmin"), memberList);
		} else {
		    KETMailHelper.service.sendFormMail("08139", "NoticeMailLine5.html", it, KETObjectUtil.getUser("wcadmin"), memberList);
		}
	    }

	    // 주담당자 메일발송 체크
	    if (isComplete) {

		List<KETIssueTask> itList = IssueUtil.manager.getIssueTaskList(im);

		for (KETIssueTask itask : itList) {
		    if (!IssueUtil.COMPLETE.equals(itask.getIssueState())) {
			isComplete = false;
			break;
		    }
		}

		toList = new ArrayList<WTUser>();
		toList.add(im.getManager());

		// 주담당자 메일발송 처리
		if (isComplete) {
		    // 요청서 완료 요청 메일
		    KETMailHelper.service.sendFormMail("08141", "NoticeMailLine2.html", im, KETObjectUtil.getUser("wcadmin"), toList);
		} else {
		    // 세부 요청사항 완료 공지
		    KETMailHelper.service.sendFormMail("08140", "NoticeMailLine6.html", it, KETObjectUtil.getUser("wcadmin"), toList);
		}
	    }

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return resMap;
    }

    public String reStartTask(List<Map<String, Object>> reStartIssueTaskList) throws Exception {
	List<WTUser> toList = new ArrayList<WTUser>();
	List<WTUser> memberList = new ArrayList<WTUser>();
	String oid = "";
	String mailTemplate = "NoticeMailLine5.html";

	for (Map<String, Object> task : reStartIssueTaskList) {
	    String itOid = (String) task.get("itOid");
	    String comments = (String) task.get("comments");
	    KETIssueTask it = (KETIssueTask) CommonUtil.getObject(itOid);
	    oid = CommonUtil.getOIDString(it.getIssueMaster());
	    it.setIssueState(IssueUtil.INPROGRESS);
	    it.setCompleteDate(null);
	    it.setWebEditor(null);
	    it.setWebEditorText(null);
	    it.setReStartReason(comments);
	    it = (KETIssueTask) PersistenceHelper.manager.save(it);
	    if (StringUtils.isNotEmpty(comments)) {
		mailTemplate = "NoticeMailLine6.html";
	    }
	    toList = new ArrayList<WTUser>();
	    toList.add(it.getWorker());
	    KETMailHelper.service.sendFormMail("08138", mailTemplate, it, KETObjectUtil.getUser("wcadmin"), toList);

	    if (StringUtil.checkString(it.getDeptCode())) {

		WTUser chief = null;
		Department dept = DepartmentHelper.manager.getDepartment(it.getDeptCode());

		if (dept != null)
		    chief = PeopleHelper.manager.getChiefUser(dept);

		LOGGER.info("########### 메일요청 부서장 ######## : " + chief);
		if (chief != null && !chief.equals(it.getWorker())) {
		    toList = new ArrayList<WTUser>();
		    toList.add(chief);
		    memberList.add(chief);
		    KETMailHelper.service.sendFormMail("08139", mailTemplate, it, KETObjectUtil.getUser("wcadmin"), toList);
		}
	    }

	    QueryResult qr = PersistenceHelper.manager.navigate(it, KETIssueMemberLink.MEMBER_ROLE, KETIssueMemberLink.class, true);

	    toList = new ArrayList<WTUser>();

	    while (qr.hasMoreElements()) {

		WTUser member = (WTUser) qr.nextElement();

		for (WTUser chief : memberList) {
		    if (CommonUtil.getOIDString(chief).equals(CommonUtil.getOIDString(member))) {
			continue;
		    }
		}
		toList.add(member);

		KETMailHelper.service.sendFormMail("08139", mailTemplate, it, KETObjectUtil.getUser("wcadmin"), toList);

	    }
	}

	return oid;
    }

    @Override
    public Map<String, Object> reStartIssueTask(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	Transaction trx = new Transaction();

	try {
	    trx.start();

	    ObjectMapper mapper = new ObjectMapper();

	    String reStartIssueTaskListStr = (String) reqMap.get("reStartIssueTaskList");
	    List<Map<String, Object>> reStartIssueTaskList = mapper.readValue(reStartIssueTaskListStr,
		    new TypeReference<List<Map<String, Object>>>() {
		    });

	    resMap.put("oid", reStartTask(reStartIssueTaskList));
	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return resMap;
    }

    /**
     * <pre>
     * @description 고객 요구사항 저장
     * @author dhkim
     * @date 2018. 5. 25. 오후 12:01:41
     * @method saveIssueMaster
     * @param issueDTO
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public Map<String, Object> saveIssueMaster(KETIssueMasterDTO issueDTO, Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();
	List<Map<String, Object>> rejectList = new ArrayList<Map<String, Object>>();

	Transaction trx = new Transaction();
	try {

	    trx.start();

	    WTContainerRef containerRef = WCUtil.getWTContainerRef();
	    LifeCycleTemplate LCtemplate = LifeCycleHelper.service.getLifeCycleTemplate("KET_COMMON_LC", containerRef);
	    String folderPath = "/Default/ISSUE";
	    SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);

	    KETIssueMaster im = null;

	    boolean isReqProgress = Boolean.parseBoolean((String) reqMap.get("isReqProgress"));
	    String oid = issueDTO.getOid();

	    if (StringUtil.checkString(oid)) {
		im = (KETIssueMaster) CommonUtil.getObject(oid);
		if (isReqProgress)
		    im.setIssueState(IssueUtil.INPROGRESS);
	    } else {
		im = KETIssueMaster.newKETIssueMaster();
		im.setReqNo(IssueUtil.manager.getGenerateIssueReqNo());
		im.setIssueState(IssueUtil.INWORK);
		im.setDeptCode(CommonUtil.getUserDeptCodeFromSession());

		LifeCycleHelper.setLifeCycle(im, LCtemplate);
		FolderHelper.assignLocation((FolderEntry) im, folder);
	    }

	    reqMap.remove("requestDate");
	    ObjectUtil.manager.convertMapToObject(reqMap, im);
	    String LastCustomer = issueDTO.getLastCustomer();
	    Object o = CommonUtil.getObject(LastCustomer);

	    if (o != null && o instanceof NumberCode) {
		im.setLastCustomer(((NumberCode) o).getCode());
	    }

	    String subcontractor = issueDTO.getSubContractor();
	    o = CommonUtil.getObject(subcontractor);
	    if (o != null && o instanceof NumberCode) {
		im.setSubContractor(((NumberCode) o).getCode());
	    }

	    im.setRequestDate(DateUtil.getTimestampFormat(issueDTO.getRequestDate(), "yyyy-MM-dd"));
	    im.setManager((WTUser) CommonUtil.getObject(issueDTO.getManagerOid()));
	    im.setOemType((OEMProjectType) CommonUtil.getObject(issueDTO.getOemOid()));

	    Persistable pbo = CommonUtil.getObject(issueDTO.getPboOid());
	    String divisionCode = (CommonUtil.isMember("자동차사업부") ? "CA" : (CommonUtil.isMember("전자사업부") ? "ER" : ""));
	    im.setDivisionCode(divisionCode);

	    im = (KETIssueMaster) PersistenceHelper.manager.save(im);
	    resMap.put("oid", CommonUtil.getOIDString(im));

	    KETContentHelper.service.updateContent(im, issueDTO);

	    KETGeneralissueLink link = IssueUtil.manager.getPBOLink(im);

	    if (pbo != null) {

		if (link == null) {
		    link = KETGeneralissueLink.newKETGeneralissueLink(im, pbo);
		} else {
		    link.setPbo(pbo);
		}

		link.setPboNo(issueDTO.getPboNo());
		link.setPboName(issueDTO.getPboName());
		PersistenceHelper.manager.save(link);

	    } else if (link != null) {
		PersistenceHelper.manager.delete(link);
	    }

	    ObjectMapper mapper = new ObjectMapper();

	    // partMaster 연계 Start
	    QueryResult partListRs = IssueUtil.manager.getPartLinkList(im);

	    while (partListRs.hasMoreElements()) {
		KETIssuePartLink partLink = (KETIssuePartLink) partListRs.nextElement();
		PersistenceHelper.manager.delete(partLink);
	    }

	    String partListStr = (String) reqMap.get("partList");
	    List<Map<String, Object>> partList = mapper.readValue(partListStr, new TypeReference<List<Map<String, Object>>>() {
	    });

	    String partNos = "";
	    int partCnt = 0;

	    if (partList != null) {

		for (Map<String, Object> partMap : partList) {

		    String partOid = StringUtil.checkNull((String) partMap.get("partOid"));

		    WTPartMaster part = (WTPartMaster) CommonUtil.getObject(partOid);

		    KETIssuePartLink partLink = KETIssuePartLink.newKETIssuePartLink(im, part);
		    partLink.setPartNo(part.getNumber());
		    partLink.setPartName(part.getName());
		    PersistenceHelper.manager.save(partLink);

		    partNos += part.getNumber() + ",";
		    partCnt++;
		}
		partNos = StringUtils.removeEnd(partNos, ",");

	    }
	    im.setPartNos(partNos);
	    im.setPartCount(partCnt);
	    im = (KETIssueMaster) PersistenceHelper.manager.save(im);

	    // partMaster 연계 End

	    List<KETIssueTask> itList = IssueUtil.manager.getIssueTaskList(im);

	    String itListStr = (String) reqMap.get("itList");
	    List<Map<String, Object>> issueTaskList = mapper.readValue(itListStr, new TypeReference<List<Map<String, Object>>>() {
	    });

	    List<String> itOidList = new ArrayList<String>();

	    if (issueTaskList != null) {

		int sort = 0;
		for (Map<String, Object> itMap : issueTaskList) {

		    List<WTUser> toList = new ArrayList<WTUser>();
		    List<WTUser> memberList = new ArrayList<WTUser>();

		    String itOid = (String) itMap.get("itOid");
		    String subject = StringUtil.checkNull((String) itMap.get("subject"));
		    String requestDate = StringUtil.checkNull((String) itMap.get("requestDate"));
		    String tmUserOid = StringUtil.checkNull((String) itMap.get("tmUserOid"));
		    String refUserOids = StringUtil.checkNull((String) itMap.get("refUserOids"));
		    String rejectCheck = StringUtil.checkNull((String) itMap.get("rejectCheck"));

		    WTUser tmWorker = null;
		    if (StringUtil.checkString(tmUserOid))
			tmWorker = (WTUser) CommonUtil.getObject(tmUserOid);

		    itOidList.add(itOid);

		    KETIssueTask it = null;
		    KETIssueTask itOld = null;

		    if (StringUtil.checkString(itOid)) {

			it = (KETIssueTask) CommonUtil.getObject(itOid);

			WTUser worker = it.getWorker();
			String reqDate = DateUtil.getDateString(it.getRequestDate(), "d");

			if (!tmUserOid.equals(CommonUtil.getOIDString(worker)) || !subject.equals(it.getSubject())
			        || !requestDate.equals(reqDate)) {

			    int version = it.getVersion();
			    it.setLastest(false);

			    itOld = (KETIssueTask) PersistenceHelper.manager.save(it);

			    it = (KETIssueTask) HistoryHelper.duplicate(itOld);
			    it.setVersion(++version);
			    it.setLastest(true);
			}

			boolean addTo = false;

			if (worker == null || !tmUserOid.equals(CommonUtil.getOIDString(worker))) {
			    it.setWorker(tmWorker);
			    addTo = true;
			}

			if (IssueUtil.REJECT.equals(it.getIssueState())) {
			    addTo = false;
			}

			if (isReqProgress || addTo)
			    toList.add(it.getWorker());

			if (isReqProgress) {
			    it.setIssueState(IssueUtil.INPROGRESS);
			}

		    } else {

			it = KETIssueTask.newKETIssueTask();
			it.setIssueMaster(im);
			it.setIssueState(IssueUtil.INWORK);
			it.setLastest(true);

			if (IssueUtil.INPROGRESS.equals(im.getIssueState())) {
			    it.setIssueState(IssueUtil.INPROGRESS);
			    it.setProgressDate(DateUtil.getCurrentTimestamp());
			    if (tmWorker != null)
				toList.add(tmWorker);
			}

			LifeCycleHelper.setLifeCycle(it, LCtemplate);
			FolderHelper.assignLocation((FolderEntry) it, folder);
		    }

		    it.setSort(sort++);
		    it.setSubject(subject);

		    if (tmWorker != null) {
			it.setWorker(tmWorker);
			Department dept = DepartmentHelper.manager.getDepartment(tmWorker);
			if (dept != null)
			    it.setDeptCode(dept.getCode());
		    }

		    if (StringUtil.checkString(requestDate)) {
			it.setRequestDate(DateUtil.getTimestampFormat(requestDate, "yyyy-MM-dd"));
		    } else {
			it.setRequestDate(null);
		    }

		    it = (KETIssueTask) PersistenceHelper.manager.save(it);

		    // 참조자 삭제
		    QueryResult qr = PersistenceHelper.manager
			    .navigate(it, KETIssueMemberLink.MEMBER_ROLE, KETIssueMemberLink.class, false);
		    while (qr.hasMoreElements()) {
			PersistenceHelper.manager.delete((Persistable) qr.nextElement());
		    }

		    // 참조자 추가
		    if (StringUtil.checkString(refUserOids)) {

			String[] refUserOid = refUserOids.split(",");

			for (String userOid : refUserOid) {
			    WTUser member = (WTUser) CommonUtil.getObject(userOid);
			    memberList.add(member);
			    if (member != null) {
				KETIssueMemberLink memberLink = KETIssueMemberLink.newKETIssueMemberLink(it, member);
				PersistenceHelper.manager.save(memberLink);
			    }
			}
		    }

		    if (itOld != null) {
			ContentHelper.service.copyContent(itOld, it);
		    }

		    if (it.getBranchId() == 0) {
			it.setBranchId(CommonUtil.getOIDLongValue(it));
			it = (KETIssueTask) PersistenceHelper.manager.save(it);
		    }

		    if (IssueUtil.REJECT.equals(it.getIssueState())) {

			if ("START".equals(rejectCheck)) {
			    Map<String, Object> rejectMap = new HashMap<String, Object>();
			    rejectMap.put("itOid", CommonUtil.getOIDString(it));
			    rejectList.add(rejectMap);
			}

		    }

		    // 메일 발송 처리
		    if (toList.size() > 0) {
			KETMailHelper.service.sendFormMail("08138", "NoticeMailLine5.html", it, KETObjectUtil.getUser("wcadmin"), toList);

			if (StringUtil.checkString(it.getDeptCode())) {

			    WTUser chief = null;
			    Department dept = DepartmentHelper.manager.getDepartment(it.getDeptCode());

			    if (dept != null)
				chief = PeopleHelper.manager.getChiefUser(dept);

			    LOGGER.info("########### 메일요청 부서장 ######## : " + chief);
			    if (chief != null && !chief.equals(it.getWorker())) {
				toList = new ArrayList<WTUser>();
				toList.add(chief);
				KETMailHelper.service.sendFormMail("08139", "NoticeMailLine5.html", it, KETObjectUtil.getUser("wcadmin"),
				        toList);

				if (memberList.contains(chief)) {
				    memberList.remove(chief);
				    LOGGER.info("########### 메일요청 참조자 부서장 제외 ########");
				}
			    }
			}

			for (WTUser member : memberList)
			    LOGGER.info("########### 메일요청 참조자 ######## : " + member.getFullName());

			KETMailHelper.service.sendFormMail("08139", "NoticeMailLine5.html", it, KETObjectUtil.getUser("wcadmin"),
			        memberList);
		    }
		}
	    }

	    for (KETIssueTask it : itList) {
		if (!itOidList.contains(CommonUtil.getOIDString(it))) {

		    List<KETIssueTask> branchList = IssueUtil.manager.getBranchIssueTask(it);

		    for (KETIssueTask itBranch : branchList) {
			PersistenceHelper.manager.delete(itBranch);
		    }
		}
	    }
	    reStartTask(rejectList);
	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return resMap;
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 6. 21. 오전 11:16:28
     * @method changeIssuePBO
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public Map<String, Object> changeIssuePBO(Map<String, Object> reqMap) throws Exception {
	Map<String, Object> resMap = new HashMap<String, Object>();

	Transaction trx = new Transaction();
	try {

	    trx.start();

	    String oid = (String) reqMap.get("oid");
	    String pboOId = (String) reqMap.get("pboOid");

	    KETIssueMaster im = (KETIssueMaster) CommonUtil.getObject(oid);
	    Persistable pbo = CommonUtil.getObject(pboOId);

	    String pboNo = "";
	    String pboName = "";

	    if (pbo instanceof KETSalesProject) {

		Map<String, Object> pboMap = IssueUtil.manager.getProjectInfo((KETSalesProject) pbo);

		pboNo = (String) pboMap.get("projectNo");
		pboName = (String) pboMap.get("projectName");

		im.setSubContractor((String) pboMap.get("subContractorCode"));
		im.setLastCustomer((String) pboMap.get("lastCustomerCode"));
		String oemOid = (String) pboMap.get("oemOid");

		OEMProjectType oemType = null;
		if (StringUtil.checkString(oemOid))
		    oemType = (OEMProjectType) CommonUtil.getObject(oemOid);
		im.setOemType(oemType);
	    }

	    im = (KETIssueMaster) PersistenceHelper.manager.save(im);

	    KETGeneralissueLink link = IssueUtil.manager.getPBOLink(im);

	    if (pbo != null) {
		if (link == null) {
		    link = KETGeneralissueLink.newKETGeneralissueLink(im, pbo);
		} else {
		    link.setPbo(pbo);
		}

		link.setPboNo(pboNo);
		link.setPboName(pboName);
		PersistenceHelper.manager.save(link);

	    } else if (link != null) {
		PersistenceHelper.manager.delete(link);
	    }

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return resMap;
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 8. 8. 오후 4:28:53
     * @method deleteIssueRef
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public Map<String, Object> deleteIssue(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub

	String oid = (String) reqMap.get("oid");

	Map<String, Object> resMap = new HashMap<String, Object>();

	Transaction trx = new Transaction();
	try {

	    trx.start();

	    KETIssueMaster im = (KETIssueMaster) CommonUtil.getObject(oid);

	    List<KETIssueTask> itList = IssueUtil.manager.getIssueTaskList(im);

	    // 세부진행항목 삭제
	    for (KETIssueTask it : itList) {

		List<KETIssueTask> itBranchList = IssueUtil.manager.getBranchIssueTask(it);

		for (KETIssueTask itBranch : itBranchList) {
		    PersistenceHelper.manager.delete(itBranch);
		}
	    }

	    // 고객 요구사항 삭제
	    PersistenceHelper.manager.delete(im);

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return resMap;
    }

    @Override
    public Map<String, Object> rejectIssueTask(KETIssueTaskDTO itDTO) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	Transaction trx = new Transaction();
	try {

	    trx.start();
	    List<WTUser> toList = new ArrayList<WTUser>();

	    String oid = itDTO.getOid();
	    Object webEditor = itDTO.getWebEditor();
	    Object webEditorText = itDTO.getWebEditorText();

	    KETIssueTask it = (KETIssueTask) CommonUtil.getObject(oid);
	    it.setIssueState(IssueUtil.REJECT);
	    it.setCompleteDate(DateUtil.getCurrentTimestamp());// 반려일자
	    it.setWebEditor(webEditor);
	    it.setWebEditorText(webEditorText);

	    it = (KETIssueTask) PersistenceHelper.manager.save(it);

	    KETIssueMaster im = it.getIssueMaster();

	    if (StringUtil.checkString(it.getDeptCode())) {

		WTUser chief = null;
		Department dept = DepartmentHelper.manager.getDepartment(it.getDeptCode());

		if (dept != null)
		    chief = PeopleHelper.manager.getChiefUser(dept);

		if (chief != null && !chief.equals(it.getWorker())) {
		    toList.add(chief);

		}
	    }

	    QueryResult qr = PersistenceHelper.manager.navigate(it, KETIssueMemberLink.MEMBER_ROLE, KETIssueMemberLink.class, true);

	    while (qr.hasMoreElements()) {

		WTUser member = (WTUser) qr.nextElement();
		toList.add(member);

	    }

	    toList.add(im.getManager());
	    // toList.add(it.getWorker());
	    // 세부 요청사항 반려 공지
	    KETMailHelper.service.sendFormMail("08149", "NoticeMailLine5.html", it, KETObjectUtil.getUser("wcadmin"), toList);
	    resMap.put("oid", oid);

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return resMap;
    }

    @Override
    public Map<String, Object> meetingTarget(Map<String, Object> reqMap) throws Exception {
	Map<String, Object> resMap = new HashMap<String, Object>();

	Transaction trx = new Transaction();
	try {

	    trx.start();

	    String oid = (String) reqMap.get("oid");
	    String meetingTarget = (String) reqMap.get("meetingTarget");

	    KETIssueMaster im = (KETIssueMaster) CommonUtil.getObject(oid);
	    im.setMeetingTarget(meetingTarget);

	    im = (KETIssueMaster) PersistenceHelper.manager.save(im);

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return resMap;
    }
}
