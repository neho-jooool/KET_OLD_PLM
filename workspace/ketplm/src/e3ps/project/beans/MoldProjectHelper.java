package e3ps.project.beans;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.fc.ObjectReference;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.lifecycle.LifeCycleHelper;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.ecm.beans.EcmSearchHelper;
import e3ps.ews.dao.PartnerDao;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectHelper;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductInfo;
import e3ps.project.ProductProject;
import e3ps.project.TemplateProject;
import e3ps.project.machine.MoldMachine;
import e3ps.project.material.MoldMaterial;
import e3ps.project.trySchdule.beans.TrySchduleHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.entity.KETPartProjectLink;
import ext.ket.part.entity.dto.PartDieProjectHelpDTO;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.project.purchase.service.KETPurchaseHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;

public class MoldProjectHelper implements wt.method.RemoteAccess, java.io.Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public final static String PRESSPM = "Team_PRODUCT05";
    public final static String MOLDDPM = "Team_PRODUCT04";
    public final static String PURCHASEPM = "Team_PRODUCT20";

    public static MoldProject createMoldProject(String moldItemOid, String planStartDate, String templateOid) throws Exception {

	if (!SERVER) {
	    Class argTypes[] = new Class[] { String.class, String.class, String.class };
	    Object args[] = new Object[] { moldItemOid, planStartDate, templateOid };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("createMoldProject", "e3ps.project.beans.MoldProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(MoldProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(MoldProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (MoldProject) obj;
	}

	MoldProject mold = null;
	Transaction transaction = new Transaction();

	try {
	    transaction.start();

	    MoldItemInfo info = (MoldItemInfo) CommonUtil.getObject(moldItemOid);
	    ProductProject productProject = info.getProject();
	    E3PSProjectData projectData = new E3PSProjectData(productProject);

	    mold = MoldProject.newMoldProject();

	    String lifecycle = "KET_MOLD_PMS_LC";

	    if (lifecycle == null || lifecycle.equals("")) {
		lifecycle = "LC_Project";
	    }

	    FolderHelper.assignLocation((FolderEntry) mold, FolderHelper.service.getFolder("/Default/프로젝트", WCUtil.getWTContainerRef()));
	    LifeCycleHelper.setLifeCycle(mold, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, WCUtil.getWTContainerRef()));

	    long infoId = info.getPersistInfo().getObjectIdentifier().getId();
	    TemplateProject templateProject = null;
	    if (templateOid != null && templateOid.length() > 0) {
		templateProject = (TemplateProject) CommonUtil.getObject(templateOid);
		mold.setTemplate(templateProject);
	    }

	    mold.setPjtNo(info.getDieNo());
	    if ("구매품".equals(info.getItemType())) {
		mold.setPjtNo(info.getPartNo());
	    }
	    mold.setPjtName(info.getPartName());
	    if (CommonUtil.isMember("자동차사업부")) {
		mold.setPjtType(3);
	    } else if (CommonUtil.isMember("전자사업부")) {
		mold.setPjtType(4);
	    } else {
		// KETS 사업부
		mold.setPjtType(6);
	    }

	    NumberCode rank = null;

	    Vector parent = NumberCodeHelper.manager.getNumberCodeLevelType("RANK", "금형");
	    for (int i = 0; i < parent.size(); i++) {
		rank = (NumberCode) parent.get(i);
		if ("S".equals(rank.getName())) {
		    break;
		}
	    }
	    // Rank는 수동으로 업데이트 되도록 미리 설정 하지 않는다.
	    // mold.setRank(rank);

	    mold.setMoldInfo(info);

	    Calendar tempCal = Calendar.getInstance();
	    ExtendScheduleData schedule = ExtendScheduleData.newExtendScheduleData();

	    // 1.1 Duration Setting
	    if (StringUtil.checkString(planStartDate.trim())) {
		tempCal.setTime(DateUtil.parseDateStr(planStartDate));
		schedule.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	    }
	    // 1.2 ScheduleHistory (0: ????)
	    schedule.setScheduleHistory(0);

	    schedule.setExecWork(0);

	    schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);

	    mold.setPjtSchedule(ObjectReference.newObjectReference(schedule));

	    boolean isMold = info.getItemType().equalsIgnoreCase("Mold");

	    String pmRole = PRESSPM;

	    if (isMold) {
		pmRole = MOLDDPM;
	    }

	    if ("구매품".equals(info.getItemType())) {
		pmRole = PURCHASEPM;
	    }

	    if (CommonUtil.isMember("전자사업부")) {
		pmRole = "Team_ELECTRON05";
		if (isMold) {
		    pmRole = "Team_ELECTRON04";
		}
	    }

	    Kogger.debug(MoldProjectHelper.class, "#### pmRole : " + pmRole);

	    Hashtable userH = TrySchduleHelper.getProjectRoleMember(productProject);

	    WTUser pmUser = (WTUser) userH.get(pmRole);
	    Kogger.debug(MoldProjectHelper.class, "#### pmUser : " + pmUser);

	    mold = (MoldProject) PersistenceHelper.manager.save(mold);

	    Department myRootDept = DepartmentHelper.manager.getRootDepartment(pmUser);
	    String rootDeptName = "";

	    if (myRootDept != null) {

		if (myRootDept.getName().startsWith("케이이티")) {
		    rootDeptName = "KETS ";
		}
	    }

	    if ("사내".equals(info.getMaking())) {
		String outSourcing = "";
		if (CommonUtil.isMember("전자사업부")) {
		    outSourcing = "전자사출금형개발팀";
		} else {
		    if ("Mold".equals(info.getItemType())) {
			outSourcing = rootDeptName + "사출금형개발팀";
		    } else if ("Press".equals(info.getItemType())) {
			outSourcing = rootDeptName + "프레스금형개발팀";
		    } else if ("구매품".equals(info.getItemType())) {
			outSourcing = "개발구매팀";
		    }
		}

		mold.setOutSourcing(outSourcing);
	    }

	    ProjectUserHelper.manager.setPM(mold, pmUser, 0);

	    // Kogger.debug(getClass(), "save PM..........");

	    if (templateProject != null) {
		ProjectHelper.manager.copyProjectInfo(mold, templateProject);
		TaskHelper.manager.copyProjectFromTemplate(mold, templateProject);
	    }

	    ProjectUserHelper.settingRoleTaskMember(mold);

	    transaction.commit();

	    ProjectScheduleHelper.settingProgress(mold);
	    Kogger.debug(MoldProjectHelper.class, "######################################################################################");
	    Kogger.debug(MoldProjectHelper.class, "######################################################################################");
	    Kogger.debug(MoldProjectHelper.class, "######################################################################################");
	    Kogger.debug(MoldProjectHelper.class, "######################################################################################");
	    Kogger.debug(MoldProjectHelper.class, "#########################추가 금형 프로젝트 생성시 금형 PM 메일 통지 ###########################");
	    // ProjectHelper.manager.projectSendMailPM(mold, pmUser, pmUser, true);
	    List<WTUser> toUser = new ArrayList<WTUser>();
	    toUser.add(pmUser);
	    if (toUser.size() > 0) {
		KETMailHelper.service.sendFormMail("08121", "NoticeMailLine4.html", mold, pmUser, toUser);
	    }
	    Kogger.debug(MoldProjectHelper.class, "#########################추가 금형 프로젝트 생성시 금형 PM 메일 통지 완료 #######################");
	    Kogger.debug(MoldProjectHelper.class, "########################################################## ###########################");

	    transaction = null;

	} catch (Exception e) {
	    Kogger.error(MoldProjectHelper.class, e);
	    throw new WTException(e);
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}
	return mold;
    }

    public static MoldProject modifyMoldProject(HashMap map) throws Exception {
	String moldOid = (String) map.get("moldOid");
	String moldRank = (String) map.get("moldRank");
	String productWeight = (String) map.get("productWeight");
	String scrapWeight = (String) map.get("scrapWeight");
	String pmOid = (String) map.get("pmOid");
	String specialSpec = (String) map.get("specialSpec");
	;
	String outSourcing = (String) map.get("outSourcing");
	String machineOid = (String) map.get("machineOid");
	String remark = (String) map.get("remark");
	String shrinkage = (String) map.get("shrinkage");

	MoldProject mold = (MoldProject) CommonUtil.getObject(moldOid);

	if (moldRank != null && moldRank.length() > 0) {
	    NumberCode rank = (NumberCode) CommonUtil.getObject(moldRank);
	    // 금형프로젝트의 Rank가 변경되면 하위 도면,문서,Try조건표등의 Rank를 수정한다.
	    // 초기에는 Rank가 없을 수 있다.
	    if (mold.getRank() != null && rank != null) {
		E3PSProjectHelper.service.changeRankByProject(mold, mold.getRank().getName(), rank.getName());
	    }
	    mold.setRank(rank);
	}

	if (productWeight != null && productWeight.length() > 0) {
	    mold.setProductWeight(productWeight);
	}

	if (scrapWeight != null && scrapWeight.length() > 0) {
	    mold.setScrapWeight(scrapWeight);
	}

	if (specialSpec != null && specialSpec.length() > 0) {
	    mold.setSpecialSpec(specialSpec);
	}

	if (outSourcing != null && outSourcing.length() > 0) {
	    mold.setOutSourcing(outSourcing);
	}

	if (machineOid != null && machineOid.length() > 0) {
	    MoldMachine machine = (MoldMachine) CommonUtil.getObject(machineOid);
	    mold.setMoldMachine(machine);
	}

	if (remark != null && remark.length() > 0) {
	    mold.setRemark(remark);
	}

	if (shrinkage != null && shrinkage.length() > 0) {
	    mold.setShrinkage(shrinkage);
	}

	mold = (MoldProject) PersistenceHelper.manager.modify(mold);

	if (pmOid != null && pmOid.length() > 0) {
	    WTUser pmUser = (WTUser) CommonUtil.getObject(pmOid);
	    ProjectUserHelper.manager.replacePM(mold, pmUser);
	}

	return mold;
    }

    public static void deleteMoldProject(String oid) throws Exception {
	MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);
	PersistenceHelper.manager.delete(moldProject);
    }

    public static MoldProject getMoldProject(MoldItemInfo itemInfo) throws Exception {

	QuerySpec spec = new QuerySpec(MoldProject.class);

	long itemId = itemInfo.getPersistInfo().getObjectIdentifier().getId();

	spec.appendWhere(new SearchCondition(MoldProject.class, MoldProject.MOLD_INFO_REFERENCE + ".key.id", "=", itemId), new int[] { 0 });

	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(MoldProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
	        new int[] { 0 });

	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(MoldProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { 0 });

	MoldProject project = null;

	QueryResult rs = PersistenceHelper.manager.find(spec);

	if (rs.hasMoreElements()) {
	    project = (MoldProject) rs.nextElement();
	}
	return project;
    }

    public static QueryResult getRelationMoldProject(ProductProject productPjt) throws Exception {
	QuerySpec spec = new QuerySpec();
	int idx1 = spec.addClassList(MoldProject.class, true);
	int idx2 = spec.addClassList(MoldItemInfo.class, false);

	spec.appendWhere(new SearchCondition(MoldProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { idx1 });

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}

	spec.appendWhere(new SearchCondition(MoldProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { idx1 });

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}

	ClassAttribute ca0 = new ClassAttribute(MoldProject.class, MoldProject.MOLD_INFO_REFERENCE + ".key.id");
	ClassAttribute ca1 = new ClassAttribute(MoldItemInfo.class, "thePersistInfo.theObjectIdentifier.id");

	SearchCondition sc = new SearchCondition(ca0, "=", ca1);
	sc.setFromIndicies(new int[] { idx1, idx2 }, 0);
	sc.setOuterJoin(0);
	spec.appendWhere(sc, new int[] { idx1, idx2 });

	spec.appendAnd();
	long productId = productPjt.getPersistInfo().getObjectIdentifier().getId();
	spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.PROJECT_REFERENCE + ".key.id", "=", productId),
	        new int[] { idx2 });

	QueryResult rs = PersistenceHelper.manager.find(spec);
	return rs;
    }

    public static E3PSTask getTask(E3PSProject project, String taskName) throws Exception {

	QuerySpec spec = new QuerySpec(E3PSTask.class);
	long projectId = project.getPersistInfo().getObjectIdentifier().getId();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.PROJECT_REFERENCE + ".key.id", "=", projectId), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.TASK_NAME, "=", taskName), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.DEBUG_SUB, SearchCondition.IS_FALSE, false), new int[] { 0 });
	// Kogger.debug(getClass(), "spec====" + spec);
	QueryResult rs = PersistenceHelper.manager.find(spec);

	E3PSTask task = null;

	if (rs.hasMoreElements()) {
	    task = (E3PSTask) rs.nextElement();
	}
	return task;
    }

    public static E3PSTask getLikeTask(E3PSProject project, String taskName) throws Exception {
	QuerySpec spec = new QuerySpec(E3PSTask.class);
	long projectId = project.getPersistInfo().getObjectIdentifier().getId();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.PROJECT_REFERENCE + ".key.id", "=", projectId), new int[] { 0 });
	spec.appendAnd();

	ClassAttribute ca0 = new ClassAttribute(E3PSTask.class, E3PSTask.TASK_NAME);
	SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
	ColumnExpression ce = ConstantExpression.newExpression("%" + taskName.trim().toUpperCase() + "%");
	SearchCondition sc0 = new SearchCondition(upper, SearchCondition.LIKE, ce);
	spec.appendWhere(sc0, new int[] { 0 });

	spec.appendAnd();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.DEBUG_SUB, SearchCondition.IS_FALSE, false), new int[] { 0 });
	// Kogger.debug(getClass(), "spec====" + spec);
	QueryResult rs = PersistenceHelper.manager.find(spec);
	E3PSTask task = null;
	if (rs.hasMoreElements()) {
	    task = (E3PSTask) rs.nextElement();
	}
	return task;
    }

    public static Vector getDebugingTasks(MoldProject project) throws Exception {

	QueryResult rs = ProjectTaskHelper.manager.getTaskWithType(project, "디버깅");

	E3PSTask debugTask = null;
	if (rs.hasMoreElements()) {
	    Object[] o = (Object[]) rs.nextElement();
	    debugTask = (E3PSTask) o[0];
	}

	Vector vector = new Vector();

	if (debugTask != null) {
	    ProjectTreeNode root = (ProjectTreeNode) ProjectScheduleHelper.manager.getRoot(debugTask, false);

	    for (int i = 0; i < root.getChildCount(); i++) {
		ProjectTreeNode node = (ProjectTreeNode) root.getChildAt(i);
		ProjectTreeNodeData td = (ProjectTreeNodeData) node.getUserObject();
		E3PSTask childTask = (E3PSTask) td.getData();
		if (childTask.getNcha() < 1) {
		    continue;
		}

		DebugingData dData = new DebugingData();
		dData.nCha = childTask.getNcha();
		dData.nchaTask = childTask;
		for (int j = 0; j < node.getChildCount(); j++) {
		    ProjectTreeNode childNode = (ProjectTreeNode) node.getChildAt(j);
		    ProjectTreeNodeData childtd = (ProjectTreeNodeData) childNode.getUserObject();
		    E3PSTask dtask = (E3PSTask) childtd.getData();
		    dData.datas.put(dtask.getTaskName(), dtask);
		}
		vector.add(dData);
	    }
	}

	Collections.sort(vector, new DebugingDataComparator());

	return vector;
    }

    public static E3PSTask getDebugintTask(MoldProject project, int nCha, String taskName) throws Exception {

	QuerySpec spec = new QuerySpec(E3PSTask.class);
	long projectId = project.getPersistInfo().getObjectIdentifier().getId();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.PROJECT_REFERENCE + ".key.id", "=", projectId), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.TASK_NAME, "=", taskName), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.DEBUG_SUB, SearchCondition.IS_TRUE, true), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.NCHA, "=", nCha), new int[] { 0 });

	QueryResult rs = PersistenceHelper.manager.find(spec);

	E3PSTask task = null;

	if (rs.hasMoreElements()) {
	    task = (E3PSTask) rs.nextElement();
	}
	return task;
    }

    private static void settingEnds(ProjectTreeNode root, Vector endTasks) {

	for (int i = 0; i < root.getChildCount(); i++) {
	    settingEnds((ProjectTreeNode) root.getChildAt(i), endTasks);
	}

	if (root.getChildCount() == 0) {
	    endTasks.add(root);
	}
    }

    public static MoldProject getMoldProject(String dieNo) throws Exception {

	QuerySpec spec = new QuerySpec(MoldProject.class);

	spec.appendWhere(new SearchCondition(MoldProject.class, MoldProject.PJT_NO, "=", dieNo), new int[] { 0 });

	if (spec.getConditionCount() > 0)
	    spec.appendAnd();

	spec.appendWhere(new SearchCondition(MoldProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
	        new int[] { 0 });

	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(MoldProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { 0 });

	MoldProject project = null;

	QueryResult rs = PersistenceHelper.manager.find(spec);

	if (rs.hasMoreElements()) {
	    project = (MoldProject) rs.nextElement();
	}
	return project;
    }

    public static HashMap getInoutInfo(String dieNo) throws Exception {
	HashMap hash = new HashMap();

	MoldProject moldProject = getMoldProject(dieNo);
	MoldItemInfo moldInfo = moldProject.getMoldInfo();

	String inoutName1 = null;
	String inoutCode1 = null;
	String inoutName2 = null;
	String inoutCode2 = null;
	PartnerDao partnerDao2 = null;
	if (moldInfo.getPartnerNo() != null && moldInfo.getPartnerNo().length() > 0) {
	    partnerDao2 = new PartnerDao();
	    inoutName1 = "외주";
	    inoutCode1 = "O";
	    inoutName2 = partnerDao2.ViewPartnerName(moldInfo.getPartnerNo());
	    inoutCode2 = moldInfo.getPartnerNo();
	} else if (moldInfo.getPurchasePlace() != null) {
	    inoutName1 = "사내";
	    inoutCode1 = "I";
	    inoutName2 = StringUtil.checkNull(moldInfo.getPurchasePlace().getName());
	    inoutCode2 = moldInfo.getPurchasePlace().getCode();
	}

	hash.put("Type", StringUtil.checkNull(inoutName1));
	hash.put("TypeCode", StringUtil.checkNull(inoutCode1));
	hash.put("Place", StringUtil.checkNull(inoutName2));
	hash.put("PlaceCode", StringUtil.checkNull(inoutCode2));

	return hash;
    }

    public static String getDieNo(String partNo) {
	String dieNo = "";

	try {
	    QuerySpec spec = new QuerySpec(MoldItemInfo.class);

	    ClassAttribute ca0 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PART_NO);
	    SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
	    partNo = partNo.toUpperCase();

	    ColumnExpression ce = ConstantExpression.newExpression(partNo);
	    spec.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce), new int[] { 0 });

	    QueryResult rs = PersistenceHelper.manager.find(spec);

	    MoldItemInfo miInfo = null;

	    while (rs.hasMoreElements()) {
		miInfo = (MoldItemInfo) rs.nextElement();

		dieNo = miInfo.getDieNo();
	    }
	} catch (Exception e) {
	    Kogger.error(MoldProjectHelper.class, e);
	}

	return dieNo;
    }

    /**
     * 함수명 : getDieNoList 설명 : 제품Part No 에 연결된 Die No 리스트를 가지고 오는 함수
     * 
     * @param partNo
     * @return 작성자 : 홍길동 작성일자 : 2011. 6. 15.
     */
    public static ArrayList<Hashtable<String, String>> getDieNoList(String partNo) {
	ArrayList<Hashtable<String, String>> dieNoList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> dieHash = null;
	String dieNo = "";

	try {
	    QuerySpec spec = new QuerySpec(MoldItemInfo.class);

	    ClassAttribute ca0 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PART_NO);
	    SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
	    partNo = partNo.toUpperCase();

	    ColumnExpression ce = ConstantExpression.newExpression(partNo);
	    spec.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce), new int[] { 0 });

	    QueryResult rs = PersistenceHelper.manager.find(spec);

	    MoldItemInfo miInfo = null;

	    while (rs.hasMoreElements()) {
		miInfo = (MoldItemInfo) rs.nextElement();
		dieNo = miInfo.getDieNo();

		dieHash = new Hashtable<String, String>();
		dieHash.put("dieNo", dieNo);
		dieHash.put("partName", EcmSearchHelper.manager.getRelatedPartName(dieNo));
		dieHash.put("status", miInfo.getProject().getLifeCycleState().getDisplay());
		dieNoList.add(dieHash);
	    }
	} catch (Exception e) {
	    Kogger.error(MoldProjectHelper.class, e);
	}

	return dieNoList;
    }

    public static String getDieNoCnt(String partNo) {
	String dieNoCnt = "";

	try {
	    QuerySpec spec = new QuerySpec(MoldItemInfo.class);

	    ClassAttribute ca0 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PART_NO);
	    SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
	    partNo = partNo.toUpperCase();

	    ColumnExpression ce = ConstantExpression.newExpression(partNo);
	    spec.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce), new int[] { 0 });

	    QueryResult rs = PersistenceHelper.manager.find(spec);

	    dieNoCnt = Integer.toString(rs.size());
	} catch (Exception e) {
	    Kogger.error(MoldProjectHelper.class, e);
	}

	return dieNoCnt;

    }

    public static MoldItemInfo getMoldItem(String dieNo, String partNo) throws Exception {
	MoldItemInfo miInfo = null;

	QuerySpec spec = new QuerySpec(MoldItemInfo.class);

	ClassAttribute ca0 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.DIE_NO);
	SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
	dieNo = dieNo.toUpperCase();

	ColumnExpression ce = ConstantExpression.newExpression(dieNo);
	spec.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce), new int[] { 0 });

	if (partNo != null && partNo.length() > 0) {

	    ClassAttribute ca1 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PART_NO);
	    upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca1);
	    partNo = partNo.toUpperCase();

	    ColumnExpression ce1 = ConstantExpression.newExpression(partNo);

	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce1), new int[] { 0 });
	}

	QueryResult rs = PersistenceHelper.manager.find(spec);

	if (rs.hasMoreElements()) {
	    miInfo = (MoldItemInfo) rs.nextElement();
	}

	return miInfo;
    }

    public static MoldProject LinkWBS(String moldProjectOid, String planStartDate, String templateOid) throws Exception {
	/*
	 * Kogger.debug(getClass(), "LinkWBS"); Kogger.debug(getClass(), "moldProjectOid = " + moldProjectOid); Kogger.debug(getClass(),
	 * "LinplanStartDate = " + planStartDate); Kogger.debug(getClass(), "templateOid = " + templateOid);
	 */
	if (!SERVER) {
	    Class argTypes[] = new Class[] { String.class, String.class, String.class };
	    Object args[] = new Object[] { moldProjectOid, planStartDate, templateOid };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("LinkWBS", "e3ps.project.beans.MoldProjectHelper", null, argTypes,
		        args);
	    } catch (RemoteException e) {
		Kogger.error(MoldProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(MoldProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (MoldProject) obj;
	}

	MoldProject mold = (MoldProject) CommonUtil.getObject(moldProjectOid);

	Transaction transaction = new Transaction();

	try {
	    transaction.start();

	    TemplateProject templateProject = null;
	    if (templateOid != null && templateOid.length() > 0) {
		templateProject = (TemplateProject) CommonUtil.getObject(templateOid);
		mold.setTemplate(templateProject);
	    }

	    Calendar tempCal = Calendar.getInstance();
	    ExtendScheduleData schedule = (ExtendScheduleData) mold.getPjtSchedule().getObject();

	    // 1.1 Duration Setting
	    if (StringUtil.checkString(planStartDate.trim())) {
		tempCal.setTime(DateUtil.parseDateStr(planStartDate));
		schedule.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	    }

	    schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);

	    // mold.setPjtSchedule(ObjectReference.newObjectReference(schedule));

	    mold = (MoldProject) PersistenceHelper.manager.modify(mold);

	    if (templateProject != null) {
		ProjectHelper.manager.copyProjectInfo(mold, templateProject);
		TaskHelper.manager.copyProjectFromTemplate(mold, templateProject);
	    }
	    ProjectUserHelper.settingRoleTaskMember(mold);

	    transaction.commit();

	    ProjectScheduleHelper.settingProgress(mold);
	    ProjectScheduleHelper.manager.post_modify_Schedule(mold);

	} catch (Exception e) {
	    Kogger.error(MoldProjectHelper.class, e);
	    throw new WTException(e);
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}

	return mold;
    }

    /**
     * 부품 업데이트시 호출되어 집니다.
     * 
     * @param partNo
     * @param dieNo
     * @throws Exception
     * @메소드명 : updateMoldItemForSync
     * @작성자 : sw775.park
     * @작성일 : 2014. 10. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @SuppressWarnings("deprecation")
    public static void updateMoldItemForSync(String partNo, String dieNo) throws Exception {
	PartDieProjectHelpDTO dto = PartBaseHelper.service.getPartDieInfoForSync(partNo, dieNo);
	if (dto == null) {
	    return;
	}
	Transaction transaction = new Transaction();

	try {
	    transaction.start();

	    if (!StringUtil.isEmpty(partNo)) {
		QuerySpec qs = new QuerySpec(MoldItemInfo.class);
		qs.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.PART_NO, SearchCondition.EQUAL, partNo),
		        new int[] { 0 });
		QueryResult rs = PersistenceHelper.manager.find(qs);
		int i = 0;
		while (rs.hasMoreElements()) {
		    MoldItemInfo itemInfo = (MoldItemInfo) rs.nextElement();
		    // 품명
		    itemInfo.setPartName(dto.getPartName());
		    // 생산처(부품) 사내/외주 구분
		    itemInfo.setProductionPlace(dto.getPartSpManufAt());
		    // 생산처(부품) 사내/외주 구분에 따라 컬럼값이 틀려짐
		    if ("사내".equals(dto.getPartSpManufAt())) {
			itemInfo.setPurchasePlace(NumberCodeHelper.manager.getNumberCode("PRODUCTIONDEPT", dto.getPartSpManufacPlace()));
		    } else {
			itemInfo.setPartnerNo(dto.getPartSpManufacPlace());
		    }
		    // 원재료(부품)
		    itemInfo.setMaterial((MoldMaterial) CommonUtil.getObject(dto.getPartSpMaterialInfo()));
		    // 원재료 특성(부품) 수지-Color, 비철-도금
		    NumberCode property = NumberCodeHelper.manager.getNumberCode("SPECCOLOR", dto.getPartSpColor());
		    if (property == null) {
			property = NumberCodeHelper.manager.getNumberCode("SPECPLATING", dto.getPartSpPlating());
		    }
		    itemInfo.setProperty(property);
		    itemInfo = (MoldItemInfo)PersistenceHelper.manager.save(itemInfo);
		    KETPurchaseHelper.service.outSourcingSync(partNo, null, itemInfo);
		    i++;
		}
		Kogger.debug(MoldProjectHelper.class, "part update count >>>> " + i);
	    }
	    if (!StringUtil.isEmpty(dieNo)) {
		QuerySpec qs = new QuerySpec(MoldItemInfo.class);
		qs.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.DIE_NO, SearchCondition.EQUAL, dieNo), new int[] { 0 });
		QueryResult rs = PersistenceHelper.manager.find(qs);
		int i = 0;
		while (rs.hasMoreElements()) {
		    MoldItemInfo itemInfo = (MoldItemInfo) rs.nextElement();
		    // 금형구분(Mold,Press,구매품)
		    itemInfo.setItemType(dto.getMoldPartClassificType());
		    // 금형속성
		    itemInfo.setMoldType(NumberCodeHelper.manager.getNumberCodeName("MOLDTYPE", dto.getMoldProps()));
		    // 금형 Cavity
		    itemInfo.setCVPitch(dto.getMoldSpCavityStd());
		    // 금형 CycleTime
		    itemInfo.setCTSPM(dto.getMoldSpObjectiveCT());
		    // 제작처(금형) 사내/외주 구분
		    itemInfo.setMaking(dto.getMoldSpMContractSAt());
		    // 제작처(금형) 사내/외주 구분에 따라 컬럼값이 틀려짐
		    if ("사내".equals(dto.getMoldSpMContractSAt())) { // 사내는 NumberCode
			itemInfo.setMakingPlace(NumberCodeHelper.manager.getNumberCode("PRODUCTIONDEPT", dto.getMoldSpDieWhere()));
		    } else {
			itemInfo.setMakingPlacePartnerNo(dto.getMoldSpDieWhere());
		    }
		    PersistenceHelper.manager.save(itemInfo);
		    i++;
		}
		Kogger.debug(MoldProjectHelper.class, "die update count >>>> " + i);
	    }
	    transaction.commit();
	} catch (Exception e) {
	    Kogger.error(MoldProjectHelper.class, e);
	    throw new WTException(e);
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}
    }

    public static void getLinkProjectList(WTPart wtPart, ArrayList<ProductProject> targetPjtList) throws Exception {

	// 부품 등록, 수정시 등록하는 프로젝트 정보(KETPartProjectLink)를 먼저 가져온다.
	QueryResult partQr = PartUtil.getPartProjectLink(wtPart, null);
	KETPartProjectLink bProjectLink = null;
	E3PSProjectMaster partPjtMast = null;
	while (partQr.hasMoreElements()) {
	    Object[] objs = (Object[]) partQr.nextElement();
	    bProjectLink = (KETPartProjectLink) objs[0];
	    partPjtMast = (E3PSProjectMaster) bProjectLink.getProject();
	    ProductProject pjt = (ProductProject) ProjectHelper.getProject(partPjtMast.getPjtNo());
	    targetPjtList.add(pjt);
	}
	// 프로젝트에서 등록한 부품을 통해서 프로젝트 정보를 가져온다.
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT PJT.CLASSNAMEA2A2||':'||PJT.IDA2A2 AS OID                         \n");
	sql.append("FROM PRODUCTPROJECT      PJT                                             \n");
	sql.append("WHERE 1=1                                                                \n");
	sql.append("AND PJT.LASTEST       = 1                                                \n");
	sql.append("AND PJT.CHECKOUTSTATE <> 'c/o'                                           \n");
	sql.append("AND ( PJT.PJTTYPE = '2' )                                                \n");
	sql.append("AND ( PJT.IDA2A2 IN (SELECT IDA3A3 FROM PRODUCTINFO WHERE PNUM = '" + wtPart.getNumber() + "' ) )   \n");

	List<Map<String, Object>> returnObjList = new ArrayList<Map<String, Object>>();
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	returnObjList = oDaoManager.queryForList(sql.toString(), new RowSetStrategy<Map<String, Object>>() {
	    @Override
	    public Map<String, Object> mapRow(ResultSet rs) throws SQLException {
		Map<String, Object> returnObj = new HashMap<String, Object>();
		returnObj.put("oid", rs.getString("oid"));
		return returnObj;
	    }
	});

	for (int i = 0; i < returnObjList.size(); i++) {
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(StringUtil.checkNull((String) returnObjList.get(i).get("oid")));
	    if (partPjtMast != null) {
		if (partPjtMast.getPjtNo().equals(project.getPjtNo())) {
		    continue;
		}
	    }
	    targetPjtList.add((ProductProject) project);
	}

    }

    @SuppressWarnings("deprecation")
    public static void updateProductInfoForSync(WTPart wtPart) throws Exception {

	String partType = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);

	if (!(partType.equals("F") || partType.equals("W") || partType.equals("H"))) {
	    return;
	}

	ArrayList<ProductProject> targetPjtList = new ArrayList<ProductProject>();

	getLinkProjectList(wtPart, targetPjtList);

	String spManufAt = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpManufAt);
	String spManufacPlace = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpManufacPlace);

	for (ProductProject project : targetPjtList) {
	    QuerySpec qs = new QuerySpec();
	    int idxpi = qs.appendClassList(ProductInfo.class, true);
	    SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id", "=", CommonUtil.getOIDLongValue(project));
	    qs.appendWhere(cs, new int[] { idxpi });
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(ProductInfo.class, ProductInfo.P_NUM, "=", wtPart.getNumber()), new int[] { idxpi });

	    QueryResult qrpi = PersistenceHelper.manager.find(qs);

	    while (qrpi.hasMoreElements()) {
		Object o[] = (Object[]) qrpi.nextElement();
		ProductInfo pi = (ProductInfo) o[0];

		if ("1".equals(spManufAt)) {// 사내
		    pi.setAssemblyPlace(NumberCodeHelper.manager.getNumberCode("PRODUCTIONDEPT", spManufacPlace));
		    pi.setAssemblyPlaceType("사내");
		    pi.setAssemblyPartnerNo("");
		} else if ("2".equals(spManufAt)) {// 외주
		    pi.setAssemblyPlaceType("외주");
		    pi.setAssemblyPartnerNo(spManufacPlace);
		}

		PersistenceHelper.manager.modify(pi);
	    }
	}

	projectManufacPlaceUpdate(targetPjtList);

    }

    public static void projectManufacPlaceUpdate(ArrayList<ProductProject> targetPjtList) throws Exception {
	for (ProductProject project : targetPjtList) {
	    QuerySpec qs = new QuerySpec();
	    int idxpi = qs.appendClassList(ProductInfo.class, true);
	    SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id", "=", CommonUtil.getOIDLongValue(project));
	    qs.appendWhere(cs, new int[] { idxpi });

	    QueryResult qrpi = PersistenceHelper.manager.find(qs);

	    String manufacPlace = "";

	    while (qrpi.hasMoreElements()) {
		Object o[] = (Object[]) qrpi.nextElement();
		ProductInfo pi = (ProductInfo) o[0];
		String assPlaceType = pi.getAssemblyPlaceType();
		if ("사내".equals(assPlaceType) && pi.getAssemblyPlace() != null) {
		    manufacPlace += pi.getAssemblyPlace().getCode() + "|";
		}
	    }

	    manufacPlace = StringUtils.removeEnd(manufacPlace, "|");
	    manufacPlace = StringUtil.removeDuplicateStringToken(manufacPlace, "|");

	    project.setManufacPlace(manufacPlace);
	    PersistenceHelper.manager.modify(project);
	}
    }

    public static void main(String args[]) throws Exception {
	// createM();
	wt.method.RemoteMethodServer.getDefault().setUserName("wcadmin");
	wt.method.RemoteMethodServer.getDefault().setPassword("wcadmin");

	// E3PSTask task = getTask((MoldProject)CommonUtil.getObject("e3ps.project.MoldProject:312614"),"금형설계");
	// Kogger.debug(getClass(), "result ===" + task);

	// HashMap hash = getInoutInfo("1A011A05");
	// Kogger.debug(getClass(), "구분 = " + hash.get("Type"));
	// Kogger.debug(getClass(), "장소 = " + hash.get("Place"));

	// Kogger.debug(getClass(), "dieNo === " + getDieNo("part012"));

    }
}
