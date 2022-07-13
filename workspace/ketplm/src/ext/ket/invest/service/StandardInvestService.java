package ext.ket.invest.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleTemplate;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.KeywordExpression;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
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
import e3ps.groupware.company.PeopleData;
import e3ps.groupware.company.PeopleHelper;
import ext.ket.common.util.ObjectUtil;
import ext.ket.cost.util.CostCalculateUtil;
import ext.ket.invest.entity.KETInvestDateHistory;
import ext.ket.invest.entity.KETInvestMaster;
import ext.ket.invest.entity.KETInvestMasterDTO;
import ext.ket.invest.entity.KETInvestMemberLink;
import ext.ket.invest.entity.KETInvestTask;
import ext.ket.invest.entity.KETInvestTaskDTO;
import ext.ket.invest.util.InvestUtil;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.shared.util.SessionUtil;

public class StandardInvestService extends StandardManager implements InvestService, Serializable {
    private static final long serialVersionUID = 1L;

    public static StandardInvestService newStandardInvestService() throws WTException {
	StandardInvestService instance = new StandardInvestService();
	instance.initialize();
	return instance;
    }

    public int saveInvestDateHistory(KETInvestMaster im, String changeDate, String changeHistory) throws Exception {

	if (im.getRequestDate() == null) {
	    return im.getDateChangeCount();
	}

	Timestamp targetDate = DateUtil.getTimestampFormat(changeDate, "yyyy-MM-dd");

	if (targetDate.compareTo(im.getRequestDate()) > 0) {
	    KETInvestDateHistory kdh = KETInvestDateHistory.newKETInvestDateHistory();
	    kdh.setInvestMaster(im);
	    kdh.setWorker((WTUser) SessionHelper.manager.getPrincipal());
	    kdh.setChangeRequestDate(targetDate);
	    kdh.setOrgRequestDate(im.getRequestDate());
	    kdh.setChangeHistory(changeHistory);
	    PersistenceHelper.manager.save(kdh);

	    return im.getDateChangeCount() + 1;
	}

	return im.getDateChangeCount();
    }

    /**
     * <pre>
     * @description 고객 요구사항 저장
     * @author dhkim
     * @date 2018. 5. 25. 오후 12:01:41
     * @method saveInvestMaster
     * @param investDTO
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public Map<String, Object> saveInvestMaster(KETInvestMasterDTO investDTO, Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	Transaction trx = new Transaction();
	try {

	    trx.start();

	    WTContainerRef containerRef = WCUtil.getWTContainerRef();
	    LifeCycleTemplate LCtemplate = LifeCycleHelper.service.getLifeCycleTemplate("KET_COMMON_LC", containerRef);
	    String folderPath = "/Default/ISSUE";
	    SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);

	    KETInvestMaster im = null;

	    String isReqProgress = (String) reqMap.get("isReqProgress");

	    String oid = investDTO.getOid();

	    String oldManagerOid = "";

	    if (StringUtil.checkString(oid)) {
		im = (KETInvestMaster) CommonUtil.getObject(oid);
		if ("progress".equals(isReqProgress)) {// 진행요청
		    im.setInvestState(InvestUtil.INPROGRESS);
		} else if ("accept".equals(isReqProgress)) {// 접수요청
		    im.setInvestState(InvestUtil.INWORK);
		}
		oldManagerOid = CommonUtil.getOIDString(im.getManager());
	    } else {

		im = KETInvestMaster.newKETInvestMaster();
		im.setInvestState(InvestUtil.REGDIT);
		im.setDeptCode(CommonUtil.getUserDeptCodeFromSession());

		LifeCycleHelper.setLifeCycle(im, LCtemplate);
		FolderHelper.assignLocation((FolderEntry) im, folder);
	    }

	    reqMap.remove("requestDate");
	    reqMap.remove("completeDate");
	    ObjectUtil.manager.convertMapToObject(reqMap, im);

	    String investExpense = (String) CostCalculateUtil.manager.eval(StringUtil.checkNullZero(im.getInvestExpense_1()) + "+"
		    + StringUtil.checkNullZero(im.getInvestExpense_2()), 0);
	    im.setInvestExpense(StringUtils.removeEnd(investExpense, ".0000"));

	    if (StringUtils.isNotEmpty(investDTO.getRequestDate())) {
		if (!"accept".equals(isReqProgress) && !im.getInvestState().equals(InvestUtil.REGDIT)) {
		    im.setDateChangeCount(saveInvestDateHistory(im, investDTO.getRequestDate(), (String) reqMap.get("changeHistory")));
		}
		im.setRequestDate(DateUtil.getTimestampFormat(investDTO.getRequestDate(), "yyyy-MM-dd"));
	    } else {
		im.setRequestDate(null);
	    }

	    if (StringUtils.isNotEmpty(investDTO.getCompleteDate())) {
		im.setCompleteDate(DateUtil.getTimestampFormat(investDTO.getCompleteDate(), "yyyy-MM-dd"));
	    } else {
		im.setCompleteDate(null);
	    }
	    PeopleData peo = new PeopleData((WTUser) CommonUtil.getObject(investDTO.getManagerOid()));
	    im.setManager(peo.wtuser);
	    im.setFirstDeptCode(peo.department.getCode());// 영업부서

	    // String drKeyOid = investDTO.getDrKeyOid();
	    // KETDevelopmentRequest dr = (KETDevelopmentRequest) CommonUtil.getObject(drKeyOid);
	    // if (dr != null) {
	    // im.setDevRequest(dr);
	    // }

	    ObjectMapper mapper = new ObjectMapper();

	    String pjtListStr = (String) reqMap.get("pjtList");
	    List<Map<String, Object>> pjtList = mapper.readValue(pjtListStr, new TypeReference<List<Map<String, Object>>>() {
	    });
	    String projectNos = "";
	    if (pjtList != null) {
		for (Map<String, Object> itMap : pjtList) {
		    String pjtNo = (String) itMap.get("pjtNo");
		    projectNos += pjtNo + ",";
		}
		projectNos = StringUtils.removeEnd(projectNos, ",");
	    }

	    im.setPjtNos(projectNos);
	    if (StringUtil.checkString(oid)) {
		PersistenceServerHelper.manager.update(im);
		im = (KETInvestMaster) PersistenceHelper.manager.refresh(im);
	    } else {
		im = (KETInvestMaster) PersistenceHelper.manager.save(im);
	    }

	    resMap.put("oid", CommonUtil.getOIDString(im));

	    KETContentHelper.service.updateContent(im, investDTO);

	    String delTaskListStr = (String) reqMap.get("delTaskList");
	    List<Map<String, Object>> delTaskList = mapper.readValue(delTaskListStr, new TypeReference<List<Map<String, Object>>>() {
	    });
	    if (delTaskList != null) {
		for (Map<String, Object> itMap : delTaskList) {
		    String delOid = (String) itMap.get("delOid");
		    KETInvestTask delTask = (KETInvestTask) CommonUtil.getObject(delOid);
		    if (delTask != null) {
			// 참조자 삭제
			QueryResult qr = PersistenceHelper.manager.navigate(delTask, KETInvestMemberLink.MEMBER_ROLE,
			        KETInvestMemberLink.class, false);
			while (qr.hasMoreElements()) {
			    PersistenceHelper.manager.delete((Persistable) qr.nextElement());
			}
			PersistenceHelper.manager.delete(delTask);
		    }
		}

	    }

	    String itListStr = (String) reqMap.get("itList");// 회수증빙자료
	    List<Map<String, Object>> investTaskList = mapper.readValue(itListStr, new TypeReference<List<Map<String, Object>>>() {
	    });

	    if (investTaskList != null) {

		int sort = 0;
		for (Map<String, Object> itMap : investTaskList) {

		    List<WTUser> toList = new ArrayList<WTUser>();
		    List<WTUser> memberList = new ArrayList<WTUser>();
		    String exceptMembers = "";

		    String itOid = (String) itMap.get("itOid");
		    String subject = StringUtil.checkNull((String) itMap.get("subject"));
		    String requestDate = StringUtil.checkNull((String) itMap.get("requestDate"));
		    String tmUserOid = StringUtil.checkNull((String) itMap.get("tmUserOid"));
		    String refUserOids = StringUtil.checkNull((String) itMap.get("refUserOids"));

		    WTUser tmWorker = null;
		    if (StringUtil.checkString(tmUserOid))
			tmWorker = (WTUser) CommonUtil.getObject(tmUserOid);

		    KETInvestTask it = null;

		    if (StringUtil.checkString(itOid)) {

			it = (KETInvestTask) CommonUtil.getObject(itOid);

			WTUser worker = it.getWorker();

			boolean addTo = false;

			if (worker == null || !tmUserOid.equals(CommonUtil.getOIDString(worker))) {
			    it.setWorker(tmWorker);
			    addTo = true;
			}

			if ("progress".equals(isReqProgress) || addTo)
			    toList.add(it.getWorker());

		    } else {

			it = KETInvestTask.newKETInvestTask();
			it.setInvestMaster(im);
			it.setInvestState(InvestUtil.INWORK);

			LifeCycleHelper.setLifeCycle(it, LCtemplate);
			FolderHelper.assignLocation((FolderEntry) it, folder);

			if (InvestUtil.INPROGRESS.equals(im.getInvestState())) {
			    if (tmWorker != null)
				toList.add(tmWorker);
			}
		    }

		    if ("progress".equals(isReqProgress)
			    || (InvestUtil.INPROGRESS.equals(im.getInvestState()) && InvestUtil.INWORK.equals(it.getInvestState()))) {
			it.setInvestState(InvestUtil.INPROGRESS);
			it.setProgressDate(DateUtil.getCurrentTimestamp());
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
		    it.setTaskCode("BTYPE");
		    if (StringUtil.checkString(itOid)) {
			PersistenceServerHelper.manager.update(it);
			it = (KETInvestTask) PersistenceHelper.manager.refresh(it);
		    } else {
			it = (KETInvestTask) PersistenceHelper.manager.save(it);
		    }

		    // 참조자 삭제
		    QueryResult qr = PersistenceHelper.manager.navigate(it, KETInvestMemberLink.MEMBER_ROLE, KETInvestMemberLink.class,
			    false);
		    while (qr.hasMoreElements()) {
			KETInvestMemberLink target = (KETInvestMemberLink) qr.nextElement();
			exceptMembers += CommonUtil.getOIDString(target.getMember()) + ",";
			PersistenceHelper.manager.delete(target);
		    }

		    // 참조자 추가
		    if (StringUtil.checkString(refUserOids)) {

			String[] refUserOid = refUserOids.split(",");

			for (String userOid : refUserOid) {
			    WTUser member = (WTUser) CommonUtil.getObject(userOid);
			    if (member != null) {

				if (!"progress".equals(isReqProgress) && StringUtil.checkString(itOid)
				        && StringUtils.contains(exceptMembers, userOid)) {// 기존에 추가되어 있는 참조자이면 메일발송생략

				} else {
				    if (im.getInvestState().equals(InvestUtil.INPROGRESS)) {
					memberList.add(member);
				    }
				}
				KETInvestMemberLink memberLink = KETInvestMemberLink.newKETInvestMemberLink(it, member);
				PersistenceHelper.manager.save(memberLink);
			    }
			}
		    }

		    // 메일 발송 처리
		    if (toList.size() > 0 || memberList.size() > 0) {

			if (toList.size() > 0) {
			    KETMailHelper.service.sendFormMail("08144", "NoticeMailLine5.html", it, KETObjectUtil.getUser("wcadmin"),
				    toList);

			    if (StringUtil.checkString(it.getDeptCode())) {

				WTUser chief = null;
				Department dept = DepartmentHelper.manager.getDepartment(it.getDeptCode());

				if (dept != null)
				    chief = PeopleHelper.manager.getChiefUser(dept);

				System.out.println("########### 메일요청 부서장 ######## : " + chief);
				if (chief != null && !chief.equals(it.getWorker())) {
				    toList = new ArrayList<WTUser>();
				    toList.add(chief);
				    KETMailHelper.service.sendFormMail("08145", "NoticeMailLine5.html", it,
					    KETObjectUtil.getUser("wcadmin"), toList);

				    if (memberList.contains(chief)) {
					memberList.remove(chief);
					System.out.println("########### 메일요청 참조자 부서장 제외 ########");
				    }
				}
			    }
			}

			for (WTUser member : memberList)
			    System.out.println("########### 메일요청 참조자 ######## : " + member.getFullName());

			KETMailHelper.service.sendFormMail("08145", "NoticeMailLine5.html", it, KETObjectUtil.getUser("wcadmin"),
			        memberList);
		    }
		}
	    }

	    itListStr = (String) reqMap.get("itList2");// 투자비 회수 진행현황
	    investTaskList = mapper.readValue(itListStr, new TypeReference<List<Map<String, Object>>>() {
	    });
	    String collectExpenseExpr = "";
	    String collectExpenseExpr_1 = "";
	    String collectExpenseExpr_2 = "";

	    if (investTaskList != null) {

		int sort = 0;
		for (Map<String, Object> itMap : investTaskList) {

		    String itOid = (String) itMap.get("itOid");
		    String collectCode = StringUtil.checkNull((String) itMap.get("collectCode"));
		    String collectExpense = StringUtil.checkNullZero((String) itMap.get("collectExpense"));
		    String collectDate = StringUtil.checkNull((String) itMap.get("collectDate"));
		    String progressSubject = StringUtil.checkNull((String) itMap.get("progressSubject"));
		    // String tmUserOid = StringUtil.checkNull((String) itMap.get("tmUserOid"));

		    // WTUser tmWorker = null;
		    // if(StringUtil.checkString(tmUserOid)) tmWorker = (WTUser) CommonUtil.getObject(tmUserOid);

		    if (StringUtils.isNotEmpty(collectDate) && ("CODE001".equals(collectCode) || "CODE002".equals(collectCode))) {
			collectExpenseExpr += collectExpense + "+";
		    }

		    if (StringUtils.isNotEmpty(collectDate) && ("CODE001".equals(collectCode))) {
			collectExpenseExpr_1 += collectExpense + "+";
		    }

		    if (StringUtils.isNotEmpty(collectDate) && ("CODE002".equals(collectCode))) {
			collectExpenseExpr_2 += collectExpense + "+";
		    }

		    KETInvestTask it = null;

		    WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();

		    if (StringUtil.checkString(itOid)) {

			it = (KETInvestTask) CommonUtil.getObject(itOid);

			// WTUser worker = it.getWorker();
			//
			// if(worker == null || !tmUserOid.equals(CommonUtil.getOIDString(worker))) {
			//
			//
			// it.setWorker(sessionUser);
			// }
		    } else {

			it = KETInvestTask.newKETInvestTask();
			it.setInvestMaster(im);
			it.setInvestState(InvestUtil.INWORK);
			it.setWorker(sessionUser);
			Department dept = DepartmentHelper.manager.getDepartment(sessionUser);
			if (dept != null)
			    it.setDeptCode(dept.getCode());
			if (InvestUtil.INPROGRESS.equals(im.getInvestState())) {
			    it.setInvestState(InvestUtil.INPROGRESS);
			    it.setProgressDate(DateUtil.getCurrentTimestamp());
			}

			LifeCycleHelper.setLifeCycle(it, LCtemplate);
			FolderHelper.assignLocation((FolderEntry) it, folder);
		    }

		    it.setSort(sort++);
		    it.setCollectCode(collectCode);
		    it.setCollectExpense(collectExpense);
		    it.setProgressSubject(progressSubject);

		    if (StringUtil.checkString(collectDate)) {
			it.setCollectDate(DateUtil.getTimestampFormat(collectDate, "yyyy-MM-dd"));
		    } else {
			it.setCollectDate(null);
		    }
		    it.setTaskCode("ATYPE");
		    if (StringUtil.checkString(itOid)) {
			PersistenceServerHelper.manager.update(it);
			it = (KETInvestTask) PersistenceHelper.manager.refresh(it);
		    } else {
			it = (KETInvestTask) PersistenceHelper.manager.save(it);
		    }
		}
		collectExpenseExpr = StringUtils.removeEnd(collectExpenseExpr, "+");
		collectExpenseExpr = (String) CostCalculateUtil.manager.eval(collectExpenseExpr, 0);

		collectExpenseExpr_1 = StringUtils.removeEnd(collectExpenseExpr_1, "+");
		collectExpenseExpr_1 = (String) CostCalculateUtil.manager.eval(collectExpenseExpr_1, 0);

		collectExpenseExpr_2 = StringUtils.removeEnd(collectExpenseExpr_2, "+");
		collectExpenseExpr_2 = (String) CostCalculateUtil.manager.eval(collectExpenseExpr_2, 0);

		im.setCollectExpense(StringUtils.removeEnd(collectExpenseExpr, ".0000"));
		im.setCollectExpense_1(StringUtils.removeEnd(collectExpenseExpr_1, ".0000"));
		im.setCollectExpense_2(StringUtils.removeEnd(collectExpenseExpr_2, ".0000"));
		PersistenceServerHelper.manager.update(im);
	    }

	    if (im.getInvestState().equals(InvestUtil.INWORK) || im.getInvestState().equals(InvestUtil.INPROGRESS)) {
		if (!CommonUtil.getOIDString(im.getManager()).equals(oldManagerOid)) {
		    isReqProgress = "accept";
		}
	    }

	    if ("accept".equals(isReqProgress)) {// 영업담당자에게 접수요청
		List<WTUser> toList = new ArrayList<WTUser>();
		toList.add(im.getManager());
		KETMailHelper.service.sendFormMail("08148", "NoticeMailLine5.html", im, KETObjectUtil.getUser("wcadmin"), toList);
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
    public PageControl findPagingList(KETInvestMasterDTO im) throws Exception {
	QuerySpec query = getInvestMasterQuery(im);
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
    private QuerySpec getInvestMasterQuery(KETInvestMasterDTO im) throws Exception {

	QuerySpec qs = new QuerySpec();
	qs.setAdvancedQueryEnabled(true);

	int idx = qs.addClassList(KETInvestMaster.class, true);

	// 영업부서
	if (StringUtil.checkString(im.getDeptCode())) {

	    String deptCode = im.getDeptCode();
	    if (StringUtil.checkString(deptCode) && deptCode.indexOf("Department") >= 0) {
		Department dept = (Department) CommonUtil.getObject(deptCode);
		deptCode = dept.getCode();
	    }

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETInvestMaster.class, KETInvestMaster.FIRST_DEPT_CODE, SearchCondition.EQUAL, deptCode),
		    new int[] { idx });
	}

	// 요청번호
	if (StringUtil.checkString(im.getReqNo())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETInvestMaster.class, KETInvestMaster.REQ_NO, SearchCondition.LIKE, "%" + im.getReqNo()
		    + "%"), new int[] { idx });
	}

	// 요청명
	if (StringUtil.checkString(im.getReqName())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETInvestMaster.class, KETInvestMaster.REQ_NAME, SearchCondition.LIKE, "%" + im.getReqName()
		    + "%"), new int[] { idx });
	}

	// 주 담당자
	if (StringUtil.checkString(im.getManagerOid())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETInvestMaster.class, KETInvestMaster.MANAGER_REFERENCE + "."
		    + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(im.getManagerOid())),
		    new int[] { idx });
	}

	String state = im.getState();

	if (StringUtil.checkString(state)) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETInvestMaster.class, KETInvestMaster.INVEST_STATE, SearchCondition.EQUAL, state),
		    new int[] { idx });
	}

	// 요청서 상태
	String investState = im.getInvestState();

	if (StringUtil.checkString(investState)) {

	    if ("gray".equals(investState)) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(new SearchCondition(KETInvestMaster.class, KETInvestMaster.INVEST_STATE, SearchCondition.EQUAL,
		        InvestUtil.COMPLETE), new int[] { idx });
	    } else {
		if ("red".equals(investState)) {
		    String alias = qs.getFromClause().getAliasAt(idx);
		    String subQs = "TRUNC( " + alias + ".REQUESTDATE) - TRUNC(SYSDATE)";
		    // String subQs2 = alias + ".INVESTSTATE = 'IST001' AND TRUNC(SYSDATE)-TRUNC(" + alias + ".CREATESTAMPA2)";

		    KeywordExpression kexp = new KeywordExpression(subQs);
		    KeywordExpression kexp2 = new KeywordExpression("1");
		    // KeywordExpression kexp3 = new KeywordExpression(subQs2);
		    // KeywordExpression kexp4 = new KeywordExpression("7");
		    SearchCondition sc = new SearchCondition(kexp, SearchCondition.LESS_THAN, kexp2);
		    // SearchCondition sc2 = new SearchCondition(kexp3, SearchCondition.GREATER_THAN_OR_EQUAL, kexp4);

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendOpenParen();
		    qs.appendWhere(sc, new int[] { idx });
		    qs.appendOr();
		    // qs.appendWhere(sc2, new int[] { idx });
		    // qs.appendOr();
		    qs.appendWhere(new SearchCondition(KETInvestMaster.class, KETInvestMaster.DATE_CHANGE_COUNT,
			    SearchCondition.GREATER_THAN, 1), new int[] { idx });

		    qs.appendCloseParen();

		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    qs.appendWhere(new SearchCondition(KETInvestMaster.class, KETInvestMaster.INVEST_STATE, SearchCondition.NOT_EQUAL,
			    "IST004"), new int[] { idx });

		} else {

		    String alias = qs.getFromClause().getAliasAt(idx);

		    SearchCondition sc = null;
		    SearchCondition sc2 = null;
		    SearchCondition sc3 = null;

		    if ("orange".equals(investState)) {

			String subsQs = "TRUNC(SYSDATE) - TRUNC( " + alias + ".CREATESTAMPA2)";

			KeywordExpression kexp = new KeywordExpression(subsQs);
			KeywordExpression kexp2 = new KeywordExpression("60");

			sc = new SearchCondition(kexp, SearchCondition.GREATER_THAN_OR_EQUAL, kexp2);

			String subsQs2 = "TRUNC( " + alias + ".REQUESTDATE) - TRUNC(SYSDATE)";

			KeywordExpression kexp3 = new KeywordExpression(subsQs2);
			KeywordExpression kexp4 = new KeywordExpression("61");

			sc2 = new SearchCondition(kexp3, SearchCondition.LESS_THAN, kexp4);

			if (qs.getConditionCount() > 0) {
			    qs.appendAnd();
			}

			qs.appendOpenParen();
			qs.appendOpenParen();
			qs.appendWhere(sc, new int[] { idx });

			if (qs.getConditionCount() > 0) {
			    qs.appendAnd();
			}

			qs.appendWhere(new SearchCondition(KETInvestMaster.class, KETInvestMaster.INVEST_STATE, SearchCondition.EQUAL,
			        "IST001"), new int[] { idx });
			qs.appendCloseParen();
			qs.appendOr();
			qs.appendWhere(sc2, new int[] { idx });

			qs.appendCloseParen();

			if (qs.getConditionCount() > 0) {
			    qs.appendAnd();
			}
			qs.appendWhere(new SearchCondition(KETInvestMaster.class, KETInvestMaster.DATE_CHANGE_COUNT,
			        SearchCondition.LESS_THAN, 2), new int[] { idx });

			if (qs.getConditionCount() > 0) {
			    qs.appendAnd();
			}

			KeywordExpression kexp5 = new KeywordExpression(subsQs2);
			KeywordExpression kexp6 = new KeywordExpression("1");

			sc3 = new SearchCondition(kexp5, SearchCondition.GREATER_THAN, kexp6);
			qs.appendWhere(sc3, new int[] { idx });

			if (qs.getConditionCount() > 0) {
			    qs.appendAnd();
			}

			qs.appendWhere(new SearchCondition(KETInvestMaster.class, KETInvestMaster.INVEST_STATE, SearchCondition.NOT_EQUAL,
			        "IST004"), new int[] { idx });

		    } else if ("blue".equals(investState)) {

			String subsQs = "TRUNC( " + alias + ".REQUESTDATE) - TRUNC(SYSDATE)";

			KeywordExpression kexp = new KeywordExpression(subsQs);
			KeywordExpression kexp2 = new KeywordExpression("30");

			sc = new SearchCondition(kexp, SearchCondition.GREATER_THAN, kexp2);

			if (qs.getConditionCount() > 0) {
			    qs.appendAnd();
			}
			qs.appendWhere(sc, new int[] { idx });

			if (qs.getConditionCount() > 0) {
			    qs.appendAnd();
			}
			qs.appendWhere(new SearchCondition(KETInvestMaster.class, KETInvestMaster.INVEST_STATE, SearchCondition.NOT_EQUAL,
			        "IST004"), new int[] { idx });

			if (qs.getConditionCount() > 0) {
			    qs.appendAnd();
			}
			qs.appendWhere(new SearchCondition(KETInvestMaster.class, KETInvestMaster.DATE_CHANGE_COUNT,
			        SearchCondition.LESS_THAN, 2), new int[] { idx });

			String subQs = "(SELECT IDA2A2 FROM KETInvestMaster A0 WHERE TRUNC(SYSDATE) - TRUNC(A0.CREATESTAMPA2) >= 60 AND (A0.investState = 'IST001'))";

			KeywordExpression kexp3 = new KeywordExpression("A0.IDA2A2");
			KeywordExpression kexp4 = new KeywordExpression(subQs);

			SearchCondition sc4 = new SearchCondition(kexp3, SearchCondition.NOT_IN, kexp4);
			if (qs.getConditionCount() > 0) {
			    qs.appendAnd();
			}
			qs.appendWhere(sc4, new int[] { idx });

		    }
		}
	    }

	}

	// // 요청서 상태
	// if (StringUtil.checkString(im.getInvestState())) {
	// if (qs.getConditionCount() > 0)
	// qs.appendAnd();
	// qs.appendWhere(
	// new SearchCondition(KETInvestMaster.class, KETInvestMaster.INVEST_STATE, SearchCondition.EQUAL, im.getInvestState()),
	// new int[] { idx });
	// }

	// 완료 요청일
	if (StringUtil.checkString(im.getRequestStartDate())) {
	    Timestamp requestDate = DateUtil.getTimestampFormat(im.getRequestStartDate(), "yyyy-MM-dd");
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETInvestMaster.class, KETInvestMaster.REQUEST_DATE, SearchCondition.GREATER_THAN_OR_EQUAL,
		    requestDate), new int[] { idx });
	}

	// 완료 요청일
	if (StringUtil.checkString(im.getRequestEndDate())) {
	    Timestamp requestDate = DateUtil.getTimestampFormat(im.getRequestEndDate(), "yyyy-MM-dd");
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(
		    new SearchCondition(KETInvestMaster.class, KETInvestMaster.REQUEST_DATE, SearchCondition.LESS_THAN, requestDate),
		    new int[] { idx });
	}

	// 세부 담당자
	if (StringUtil.checkString(im.getTmUserOid())) {

	    int idx4 = qs.addClassList(KETInvestTask.class, false);

	    ClassAttribute ca0 = new ClassAttribute(KETInvestMaster.class, WTAttributeNameIfc.ID_NAME);
	    ClassAttribute ca1 = new ClassAttribute(KETInvestTask.class, KETInvestTask.INVEST_MASTER_REFERENCE + "."
		    + WTAttributeNameIfc.REF_OBJECT_ID);
	    SearchCondition sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	    sc0.setFromIndicies(new int[] { idx, idx4 }, 0);
	    sc0.setOuterJoin(0);

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(sc0, new int[] { idx, idx4 });

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(
		    new SearchCondition(KETInvestTask.class, KETInvestTask.WORKER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
		            SearchCondition.EQUAL, CommonUtil.getOIDLongValue(im.getTmUserOid())), new int[] { idx4 });
	}

	// 프로젝트
	if (StringUtil.checkString(im.getPjtNo())) {
	    String pjtNos = StringUtils.replace(im.getPjtNo(), ",", "|");

	    String alias = qs.getFromClause().getAliasAt(idx);
	    String subQs = "(SELECT IDA2A2 FROM KETInvestMaster WHERE REGEXP_LIKE(pjtnos,'" + pjtNos + "') )";

	    KeywordExpression kexp = new KeywordExpression(alias + ".IDA2A2");
	    KeywordExpression kexp2 = new KeywordExpression(subQs);
	    SearchCondition sc1 = new SearchCondition(kexp, SearchCondition.IN, kexp2);
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(sc1, new int[] { idx });
	}

	// 소팅로직
	if (!StringUtil.isTrimToEmpty(im.getSortName())) {

	    String checkName = im.getSortName();
	    Class sortClass = KETInvestMaster.class;
	    int sortIdx = idx;
	    String sortName = KETInvestMaster.REQ_NO;

	    String tempCheckName = checkName;

	    if (!checkName.startsWith("-")) {
		tempCheckName = "-" + checkName;
	    }

	    switch (tempCheckName.substring(1)) {
	    case "reqNo":
		sortName = KETInvestMaster.REQ_NO;
		break;
	    case "reqName":
		sortName = KETInvestMaster.REQ_NAME;
		break;
	    case "createDate":
		sortName = KETInvestMaster.CREATE_TIMESTAMP;
		break;
	    case "requestDate":
		sortName = KETInvestMaster.REQUEST_DATE;
		break;
	    case "managerName":
		sortName = KETInvestMaster.MANAGER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID;
		break;
	    case "investStateName":
		sortName = KETInvestMaster.INVEST_STATE;
		break;
	    case "investExpense":
		sortName = KETInvestMaster.INVEST_EXPENSE;
		break;
	    case "collectExpense":
		sortName = KETInvestMaster.COLLECT_EXPENSE;
		break;
	    case "completeDate":
		sortName = KETInvestMaster.COMPLETE_DATE;
		break;
	    case "mDeptName":
		sortName = KETInvestMaster.DEPT_CODE;
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

	    if (checkName.startsWith("-")) {
		SearchUtil.setOrderBy(qs, sortClass, sortIdx, sortName, true);
	    } else {
		SearchUtil.setOrderBy(qs, sortClass, sortIdx, sortName, false);
	    }
	}

	System.out.println("in MASTER QUERY ############## " + qs);

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
    public Map<String, Object> saveInvestTask(KETInvestTaskDTO itDTO, Map<String, Object> reqMap) throws Exception {

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

	    KETInvestTask it = (KETInvestTask) CommonUtil.getObject(oid);

	    KETInvestMaster im = it.getInvestMaster();

	    it.setWebEditor(webEditor);
	    it.setWebEditorText(webEditorText);

	    if (isComplete) {

		it.setInvestState(InvestUtil.COMPLETE);
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

	    PersistenceServerHelper.manager.update(it);
	    it = (KETInvestTask) PersistenceHelper.manager.refresh(it);

	    resMap.put("oid", CommonUtil.getOIDString(it));

	    KETContentHelper.service.updateContent(it, itDTO);

	    // 참조자 메일발송 리스트 만들기
	    List<WTUser> memberList = new ArrayList<WTUser>();

	    QueryResult qr = PersistenceHelper.manager.navigate(it, KETInvestMemberLink.MEMBER_ROLE, KETInvestMemberLink.class, true);

	    while (qr.hasMoreElements()) {

		WTUser member = (WTUser) qr.nextElement();
		memberList.add(member);

	    }

	    // 담당자 메일발송 처리
	    if (toList.size() > 0 || InvestUtil.COMPLETE.equals(it.getInvestState())) {

		if (!InvestUtil.COMPLETE.equals(it.getInvestState())) {
		    KETMailHelper.service.sendFormMail("08144", "NoticeMailLine5.html", it, KETObjectUtil.getUser("wcadmin"), toList);
		}

		if (StringUtil.checkString(it.getDeptCode())) {

		    WTUser chief = null;
		    Department dept = DepartmentHelper.manager.getDepartment(it.getDeptCode());

		    if (dept != null)
			chief = PeopleHelper.manager.getChiefUser(dept);

		    if (chief != null && !chief.equals(it.getWorker())) {
			toList = new ArrayList<WTUser>();
			toList.add(chief);

			if (InvestUtil.COMPLETE.equals(it.getInvestState())) {
			    toList.add(it.getWorker());
			    KETMailHelper.service.sendFormMail("08146", "NoticeMailLine5.html", it, KETObjectUtil.getUser("wcadmin"),
				    toList);
			} else {
			    KETMailHelper.service.sendFormMail("08145", "NoticeMailLine5.html", it, KETObjectUtil.getUser("wcadmin"),
				    toList);
			}
		    }
		}

		if (InvestUtil.COMPLETE.equals(it.getInvestState())) {
		    KETMailHelper.service.sendFormMail("08146", "NoticeMailLine5.html", it, KETObjectUtil.getUser("wcadmin"), memberList);
		}

		// else {
		// KETMailHelper.service.sendFormMail("08145", "NoticeMailLine5.html", it, KETObjectUtil.getUser("wcadmin"), memberList);
		// }
	    }

	    // 주담당자 메일발송 체크
	    if (isComplete) {

		List<KETInvestTask> itList = InvestUtil.manager.getInvestTaskList(im, "BTYPE");

		for (KETInvestTask itask : itList) {
		    if (!InvestUtil.COMPLETE.equals(itask.getInvestState())) {
			isComplete = false;
			break;
		    }
		}

		toList = new ArrayList<WTUser>();
		toList.add(im.getManager());

		// 주담당자 메일발송 처리
		if (isComplete) {
		    // 요청서 완료 요청 메일
		    KETMailHelper.service.sendFormMail("08147", "NoticeMailLine2.html", im, KETObjectUtil.getUser("wcadmin"), toList);
		} else {
		    // 세부 요청사항 완료 공지
		    KETMailHelper.service.sendFormMail("08146", "NoticeMailLine5.html", it, KETObjectUtil.getUser("wcadmin"), toList);
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

    @Override
    public Map<String, Object> deleteInvest(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub

	String oid = (String) reqMap.get("oid");

	Map<String, Object> resMap = new HashMap<String, Object>();

	Transaction trx = new Transaction();
	try {

	    trx.start();

	    KETInvestMaster im = (KETInvestMaster) CommonUtil.getObject(oid);

	    if (im.getInvestState().equals(InvestUtil.REGDIT)) {

		List<KETInvestTask> itList = InvestUtil.manager.getInvestTaskList(im, "");

		// 세부진행항목 삭제
		for (KETInvestTask it : itList) {

		    PersistenceHelper.manager.delete(it);
		}
		PersistenceHelper.manager.delete(im);
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
}
