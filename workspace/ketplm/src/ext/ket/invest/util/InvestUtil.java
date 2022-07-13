package ext.ket.invest.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.ClassAttribute;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.util.WTAttributeNameIfc;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProject;
import e3ps.project.ExtendScheduleData;
import e3ps.project.ProjectSubcontractorLink;
import e3ps.project.beans.ProjectHelper;
import ext.ket.common.util.ObjectUtil;
import ext.ket.cost.util.CostCalculateUtil;
import ext.ket.invest.entity.KETInvestDateHistory;
import ext.ket.invest.entity.KETInvestMaster;
import ext.ket.invest.entity.KETInvestTask;
import ext.ket.invest.entity.KETInvestTaskDTO;

public class InvestUtil {
    @SuppressWarnings("unused")
    public static final InvestUtil manager = new InvestUtil();

    public static final String REGDIT = "IST000"; // 등록중
    public static final String INWORK = "IST001"; // 접수대기
    public static final String INPROGRESS = "IST002"; // 진행중
    public static final String UNDERREVIEW = "IST003"; // 검토중
    public static final String COMPLETE = "IST004"; // 완료

    public static final int FLAGCOMPLETE = 0;
    public static final int FLAGINPROGRESS = 1;
    public static final int FLAGWARNING = 2;
    public static final int FLAGDELAY = 3;

    public List<Map<String, Object>> getInvestDateHistoryList(KETInvestMaster im) throws Exception {

	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	QuerySpec qs = new QuerySpec(KETInvestDateHistory.class);
	qs.setAdvancedQueryEnabled(true);

	ClassAttribute createStampA2 = new ClassAttribute(KETInvestDateHistory.class, "thePersistInfo.createStamp");

	SQLFunction createStamp = SQLFunction.newSQLFunction(SQLFunction.TO_CHAR, createStampA2,
	        ConstantExpression.newExpression("YYYY-MM-DD"));
	createStamp.setColumnAlias("createDate");
	qs.appendSelect(createStamp, false);

	qs.appendWhere(new SearchCondition(KETInvestDateHistory.class, KETInvestDateHistory.INVEST_MASTER_REFERENCE + "."
	        + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(im)), new int[] { 0 });

	SearchUtil.setOrderBy(qs, KETInvestDateHistory.class, 0, "thePersistInfo.createStamp", "createDate", false);

	QueryResult qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    Object[] objects = (Object[]) qr.nextElement();
	    KETInvestDateHistory it = (KETInvestDateHistory) objects[0];
	    String createDate = String.valueOf(objects[1]);
	    Map<String, Object> map = new HashMap<String, Object>();

	    String changeHistory = it.getChangeHistory().replaceAll("\r\n", "<br/>");

	    String orgRequestDate = DateUtil.getDateString(it.getOrgRequestDate(), "d");
	    String changeRequestDate = DateUtil.getDateString(it.getChangeRequestDate(), "d");

	    map.put("worker", it.getWorker().getFullName());
	    map.put("createDate", createDate);
	    map.put("changeHistory", changeHistory);
	    map.put("orgRequestDate", orgRequestDate);
	    map.put("changeRequestDate", changeRequestDate);

	    list.add(map);
	}

	return list;
    }

    /**
     * <pre>
     * @description 세부 요청사항 목록 조회(DTO)
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:38:37
     * @method getInvestTaskList
     * @param reqMap
     * @return List<KETInvestTaskDTO>
     * @throws Exception
     * </pre>
     */
    public List<KETInvestTaskDTO> getInvestTaskList(Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");
	String taskCode = (String) reqMap.get("taskCode");
	KETInvestMaster im = (KETInvestMaster) CommonUtil.getObject(oid);

	List<KETInvestTaskDTO> itList = new ArrayList<KETInvestTaskDTO>();
	List<KETInvestTask> list = getInvestTaskList(im, taskCode);

	for (KETInvestTask it : list) {

	    KETInvestTaskDTO itDTO = new KETInvestTaskDTO(it);
	    itList.add(itDTO);
	}

	return itList;
    }

    /**
     * <pre>
     * @description 세부 요청사항 목록 조회
     * @author dhkim
     * @date 2018. 5. 25. 오후 3:00:08
     * @method getInvestTaskList
     * @param im
     * @return List<KETInvestTask>
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public List<KETInvestTask> getInvestTaskList(KETInvestMaster im, String taskCode) throws Exception {

	List<KETInvestTask> list = new ArrayList<KETInvestTask>();

	QuerySpec qs = new QuerySpec(KETInvestTask.class);
	qs.setAdvancedQueryEnabled(true);

	qs.appendWhere(new SearchCondition(KETInvestTask.class, KETInvestTask.INVEST_MASTER_REFERENCE + "."
	        + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(im)), new int[] { 0 });

	if (StringUtils.isNotEmpty(taskCode)) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETInvestTask.class, KETInvestTask.TASK_CODE, SearchCondition.EQUAL, taskCode),
		    new int[] { 0 });
	}
	SearchUtil.setOrderBy(qs, KETInvestTask.class, 0, KETInvestTask.SORT, false);

	QueryResult qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    KETInvestTask it = (KETInvestTask) qr.nextElement();
	    list.add(it);
	}

	return list;
    }

    public boolean isApproveOk(KETInvestMaster im) throws Exception {// 투자비 회수총액 (회수+미회수) 이 고객투자비 보다 큰지 체크

	List<KETInvestTask> list = this.getInvestTaskList(im, "ATYPE");
	String collectExpenseExpr = "";
	String investExpense = StringUtil.checkNullZero(im.getInvestExpense());

	for (KETInvestTask task : list) {
	    collectExpenseExpr += StringUtil.checkNullZero(task.getCollectExpense()) + "+";
	}
	collectExpenseExpr = StringUtils.removeEnd(collectExpenseExpr, "+");
	collectExpenseExpr = (String) CostCalculateUtil.manager.eval(collectExpenseExpr, 0);

	String std = "if (" + StringUtil.checkNullZero(investExpense) + " <= " + collectExpenseExpr + " ){ true; } else { false; }";

	return checkStd(std);
    }

    public boolean checkStd(String std) throws Exception {
	ScriptEngineManager mgr = new ScriptEngineManager();
	ScriptEngine engine = mgr.getEngineByName("javascript");

	String check = engine.eval(std).toString();
	return Boolean.valueOf(check);
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
    public List<Map<String, Object>> getInvestTaskMapList(Map<String, Object> reqMap) throws Exception {

	List<Map<String, Object>> itList = new ArrayList<Map<String, Object>>();
	List<KETInvestTaskDTO> list = getInvestTaskList(reqMap);

	for (KETInvestTaskDTO it : list) {

	    Map<String, Object> itMap = ObjectUtil.manager.converObjectToMap(it);

	    itList.add(itMap);
	}

	return itList;
    }

    public List<Map<String, Object>> getProjectListByDrNumber(String drNumber) throws Exception {
	Map<String, Object> projectMap = null;
	List<Map<String, Object>> ProductProjectList = new ArrayList<Map<String, Object>>();
	QuerySpec spec = new QuerySpec(E3PSProject.class);

	spec.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.DEV_REQUEST_NO, SearchCondition.EQUAL, drNumber.toUpperCase()),
	        new int[] { 0 });

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
	        new int[] { 0 });

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { 0 });

	QueryResult rs = PersistenceHelper.manager.find(spec);

	E3PSProject project = null;
	while (rs.hasMoreElements()) {
	    project = (E3PSProject) rs.nextElement();
	    projectMap = new HashMap<String, Object>();
	    projectMap.put("pjtNo", project.getPjtNo());
	    projectMap.put("pjtName", project.getPjtName());
	    if (project.getBusiness() != null) {
		PeopleData peo = new PeopleData(project.getBusiness());
		projectMap.put("salesName", peo.name);
		projectMap.put("salesDept", peo.departmentName);
	    }
	    ExtendScheduleData schedule = (ExtendScheduleData) project.getPjtSchedule().getObject();
	    projectMap.put("execEndDate", DateUtil.getDateString(schedule.getExecEndDate(), "d"));
	    ProductProjectList.add(projectMap);

	}
	return ProductProjectList;
    }

    public String getDelayFlag(KETInvestMaster im) throws Exception {
	Timestamp requestDate = im.getRequestDate();
	String delayFlag = "";
	if (InvestUtil.COMPLETE.equals(im.getInvestState())) {// 완료
	    delayFlag = "green";
	} else {
	    Date reqDate = DateUtil.getTimestampFormat(DateUtil.getDateString(requestDate, "date"), "yyyy-MM-dd");
	    Date createDate = DateUtil.getTimestampFormat(DateUtil.getDateString(im.getCreateTimestamp(), "date"), "yyyy-MM-dd");
	    Date curDate = DateUtil.getCurrentTimestamp();

	    int reqDiff = this.diffDuration(reqDate, curDate);
	    int createDiff = this.diffDuration(curDate, createDate);

	    if (im.getDateChangeCount() > 1 || reqDiff < 1) {
		// 회수예정일2회 이상 지연 변경, 회수예정일 초과
		delayFlag = "red";
	    } else if (createDiff >= 60 && InvestUtil.INWORK.equals(im.getInvestState()) || reqDiff < 61) {// 생성 후 60일 이상 미접수 건, 회수예정일 60일 전
		delayFlag = "orange";
	    } else {// 남은 회수 예정기간이 30 일 이후
		delayFlag = "blue";
	    }
	}

	return delayFlag;
    }

    public int diffDuration(Date date1, Date date2) throws Exception {

	long oneDayMillis = 24 * 60 * 60 * 1000;
	int duration = (int) ((date1.getTime() - date2.getTime()) / oneDayMillis);

	return duration;
    }

    public List<Map<String, Object>> getProjectInfoByPjtNo(Map<String, Object> reqMap) throws Exception {
	List<Map<String, Object>> dataList = (List<Map<String, Object>>) reqMap.get("data");

	List<Map<String, Object>> pjtList = new ArrayList<Map<String, Object>>();

	Map<String, Object> pjtMap = new HashMap<String, Object>();

	for (Map<String, Object> data : dataList) {
	    String pjtNo = (String) data.get("pjtNo");

	    E3PSProject project = ProjectHelper.manager.getProject(pjtNo);

	    pjtMap = new HashMap<String, Object>();

	    pjtMap.put("salesName", "");
	    pjtMap.put("salesDept", "");

	    if (project != null) {
		pjtMap.put("pjtName", project.getPjtName());
		pjtMap.put("pjtNo", project.getPjtNo());
		if (project.getBusiness() != null) {
		    PeopleData peo = new PeopleData(project.getBusiness());
		    pjtMap.put("salesName", peo.name);
		    pjtMap.put("salesDept", peo.departmentName);
		}
		pjtMap.put("pjt_customer", this.getSubcontractNames(project));
		ExtendScheduleData schedule = (ExtendScheduleData) project.getPjtSchedule().getObject();
		pjtMap.put("execEndDate", DateUtil.getDateString(schedule.getExecEndDate(), "d"));
	    }
	    pjtList.add(pjtMap);
	}
	return pjtList;
    }

    public String getSubcontractNames(E3PSProject pjt) throws Exception {
	String subcontractNames = "";
	QuerySpec psspec = new QuerySpec(ProjectSubcontractorLink.class);
	SearchUtil.appendEQUAL(psspec, ProjectSubcontractorLink.class, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(pjt), 0);
	QueryResult psresult = PersistenceHelper.manager.find(psspec);
	while (psresult != null && psresult.hasMoreElements()) {
	    ProjectSubcontractorLink link = (ProjectSubcontractorLink) psresult.nextElement();
	    if (link != null) {
		subcontractNames += link.getSubcontractor().getName() + ",";
	    }
	}
	subcontractNames = StringUtils.removeEnd(subcontractNames, ",");
	return subcontractNames;
    }

    public boolean reqNoDupCheck(String reqNo) throws Exception {
	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(KETInvestMaster.class, true);

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();

	qs.appendWhere(new SearchCondition(KETInvestMaster.class, KETInvestMaster.REQ_NO, SearchCondition.EQUAL, reqNo), new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(qs);

	boolean isDup = qr.size() > 0;

	return isDup;

    }

    public KETInvestMaster getInvestMasterByReqNo(String reqNo) throws Exception {

	KETInvestMaster im = null;

	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(KETInvestMaster.class, true);

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();

	qs.appendWhere(new SearchCondition(KETInvestMaster.class, KETInvestMaster.REQ_NO, SearchCondition.EQUAL, reqNo), new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(qs);
	while (qr.hasMoreElements()) {
	    im = (KETInvestMaster) qr.nextElement();
	}
	return im;
    }
}
