package e3ps.project.beans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.enterprise.RevisionControlled;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.introspection.ClassInfo;
import wt.introspection.WTIntrospectionException;
import wt.introspection.WTIntrospector;
import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.pds.DatabaseInfoUtilities;
import wt.pds.oracle81.OraclePds81;
import wt.pom.DBProperties;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.KeywordExpression;
import wt.query.OrderBy;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.util.WTPropertyVetoException;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.db.DBCPManager;
import e3ps.common.impl.ParentChildLink;
import e3ps.common.query.E3PSClassTableExpression;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.ParamHash;
import e3ps.common.web.ParamUtil;
import e3ps.ecm.beans.EcmUtil;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.groupware.company.PeopleData;
import e3ps.project.CheckoutLink;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.KETMoldChangeOrderOutputLink;
import e3ps.project.KETProdChangeOrderOutputLink;
import e3ps.project.KETTryMoldOutputLink;
import e3ps.project.KETTryPressOutputLink;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.ProjectIssue;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ProjectOutput;
import e3ps.project.ScheduleData;
import e3ps.project.TaskDependencyLink;
import e3ps.project.TaskMemberLink;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateProjectTemplateTaskLink;
import e3ps.project.TemplateTask;
import e3ps.project.dao.ProjectScheduleDao;
import e3ps.project.historyprocess.HistoryHelper;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.project.trycondition.entity.KETTryMold;
import ext.ket.project.trycondition.entity.KETTryPress;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.log.Kogger;

public class ProjectTaskHelper implements RemoteAccess {

    static String host;

    static {
	try {
	    String hostName = WTProperties.getLocalProperties().getProperty("wt.httpgw.hostname");
	    host = "http://" + hostName;
	} catch (java.io.IOException ex) {
	    Kogger.error(ProjectTaskHelper.class, ex);
	}
    }

    public static final ProjectTaskHelper manager = new ProjectTaskHelper();

    private ProjectTaskHelper() {
    }

    public static void updateTaskFromDependencyLink(TemplateTask task, Vector vector) throws Exception {
	for (int i = 0; i < vector.size(); i++) {
	    TaskDependencyLink link = (TaskDependencyLink) vector.get(i);
	    PersistenceHelper.manager.save(link);
	}

	if (task instanceof E3PSTask) {
	    ProjectScheduleHelper.manager.post_modify_Schedule((E3PSTask) task);
	} else {
	    ProjectScheduleHelper.manager.post_modify_template_duration(task);
	}
    }

    public static void updateCompletionFromOutput(E3PSTask task) throws Exception {
	// Kogger.debug(getClass(), "updateCompletionjjjjjjjjjjjjjjjjjjjjjjjjjjjkkkkkkkkkkkkkkk...");

	// WBSItem wbsItem = getWBSItem(task);
	// if(wbsItem != null){
	// if(wbsItem.isIsERP()){
	// return;
	// }
	// }
	QueryResult rs = ProjectOutputHelper.manager.getTaskOutput(task);

	boolean isSendMail = true;

	if (task.getTaskCompletion() == 100) {
	    isSendMail = false;
	}

	int sum_completion = 0;
	int continueCount = 0;
	while (rs.hasMoreElements()) {
	    Object o[] = (Object[]) rs.nextElement();
	    ProjectOutput po = (ProjectOutput) o[0];
	    if (po.getCompletion() < 0 || !po.isIsPrimary()) {
		continueCount++;
		continue;
	    }
	    sum_completion += po.getCompletion();
	}

	// Kogger.debug(getClass(), "ffffffffffffffffffffffffff...");
	int completion = 0;

	boolean isCompleted = false;
	if (rs.size() - continueCount != 0) {
	    completion = sum_completion / (rs.size() - continueCount);
	} else {
	    return;
	}

	task.setTaskCompletion(completion);

	// if(completion == 100){
	// //실제 종료일 등록
	// ExtendScheduleData schedule = (ExtendScheduleData)task.getTaskSchedule().getObject();
	// Timestamp endts = null;
	// endts = DateUtil.getCurrentTimestamp();
	// schedule.setExecEndDate(endts);
	//
	// if(task.getTaskState() == 5){ }else{
	// schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
	// }
	//
	//
	// task.setTaskState(ProjectStateFlag.TASK_STATE_COMPLETE);
	//
	// isCompleted = true;
	// }

	// Kogger.debug(getClass(), "Taskcompletion ===== " + completion);

	task = (E3PSTask) PersistenceHelper.manager.save(task);

	ProjectScheduleHelper.manager.post_modify_completion(task);

	if (isCompleted) {

	    sendSap(task);
	    // try{
	    //
	    // if(!PJTInfoERPInterface.sendProjectInfoToSap((JELProject)task.getProject(), System.out, false)){
	    // Kogger.debug(getClass(), "updateCompletionFromOutput error...");
	    // }
	    // }catch(Exception ex){
	    // Kogger.error(getClass(), ex);
	    // }
	}

	if (isSendMail && task.getTaskCompletion() == 100) {
	    // noticeCompletion100(task);
	}
    }

    public static void updateCompletion(ProjectOutput output) throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { ProjectOutput.class };
	    Object args[] = new Object[] { output };
	    RemoteMethodServer.getDefault().invoke("updateCompletion", ProjectTaskHelper.class.getName(), null, argTypes, args);
	    return;
	}

	Transaction trx = new Transaction();
	try {
	    trx.start();
	    
	    if(output.getObjType().equals("COST")){
		boolean goFlag = true;
		if(StringUtils.isEmpty(output.getTotalCost())){
		    goFlag = false;
		}
		if(StringUtils.isEmpty(output.getTotalCostFinal())){
		    goFlag = false;
		}
		if(StringUtils.isEmpty(output.getRate())){
		    goFlag = false;
		}
		if(StringUtils.isEmpty(output.getRateFinal())){
		    goFlag = false;
		}
		
		if(!goFlag){
		    throw new Exception("원가요소가 등록되지 않았습니다.");
		}
	    }
	    
	    if(output.getObjType().equals("SALES")){
		boolean goFlag = true;
		if(StringUtils.isEmpty(output.getSalesTarget())){
		    goFlag = false;
		}
		if(StringUtils.isEmpty(output.getSalesTargetFinal())){
		    goFlag = false;
		}
		if(StringUtils.isEmpty(output.getYearAverageQty())){
		    goFlag = false;
		}
		if(StringUtils.isEmpty(output.getYearAverageQtyFinal())){
		    goFlag = false;
		}
		
		if(!goFlag){
		    throw new Exception("판매목표가가 등록되지 않았습니다.");
		}
	    }

	    if (output.getObjType().equals("ECO") && output.getCompletion() == 100) {
		KETProdChangeOrder prodChangeOrderObj = null;
		KETMoldChangeOrder moldChangeOrderObj = null;

		QueryResult qr = PersistenceHelper.manager.navigate(output, "change", KETProdChangeOrderOutputLink.class);

		while (qr.hasMoreElements()) {
		    prodChangeOrderObj = (KETProdChangeOrder) qr.nextElement();
		}

		qr = PersistenceHelper.manager.navigate(output, "change", KETMoldChangeOrderOutputLink.class);

		while (qr.hasMoreElements()) {
		    moldChangeOrderObj = (KETMoldChangeOrder) qr.nextElement();
		}

		if (prodChangeOrderObj != null) {
		    if (!prodChangeOrderObj.getState().toString().equals("APPROVED")) {
			throw new Exception("승인된 문서가 존재해야만 100%가  될수 있습니다.");
		    }
		} else if (moldChangeOrderObj != null) {
		    if (!moldChangeOrderObj.getState().toString().equals("APPROVED")) {
			throw new Exception("승인된 문서가 존재해야만 100%가  될수 있습니다.");
		    }
		} else {
		    throw new Exception("승인된 문서가 존재해야만 100%가  될수 있습니다.");
		}

	    } else if (output.getObjType().equals("TRY") && output.getCompletion() == 100) {
		KETTryMold moldTryConditionObj = null;
		KETTryPress pressTryConditionObj = null;

		QueryResult qr = PersistenceHelper.manager.navigate(output, "tryMold", KETTryMoldOutputLink.class);

		while (qr.hasMoreElements()) {
		    moldTryConditionObj = (KETTryMold) qr.nextElement();
		}

		qr = PersistenceHelper.manager.navigate(output, "tryPress", KETTryPressOutputLink.class);

		while (qr.hasMoreElements()) {
		    pressTryConditionObj = (KETTryPress) qr.nextElement();
		}

		// outputNameStr = outputData.tryCondition.getName();
		if (moldTryConditionObj != null) {
		    if (!moldTryConditionObj.getState().toString().equals("APPROVED")) {
			throw new Exception("승인된 문서가 존재해야만 100%가  될수 있습니다.");
		    }
		} else if (pressTryConditionObj != null) {
		    if (!pressTryConditionObj.getState().toString().equals("APPROVED")) {
			throw new Exception("승인된 문서가 존재해야만 100%가  될수 있습니다.");
		    }
		} else {
		    throw new Exception("승인된 문서가 존재해야만 100%가  될수 있습니다.");
		}
	    } else if (!output.getObjType().equals("COST") && !output.getObjType().equals("SALES") && !output.getObjType().equals("ETC") && output.getCompletion() == 100) {
		RevisionControlled doc = ProjectOutputHelper.manager.getDocMasterForOutput(output);
		if (doc != null) {
		    if (!doc.getState().toString().equals("APPROVED")) {
			throw new Exception("승인된 문서가 존재해야만 100%가  될수 있습니다.");
		    }
		} else {
		    throw new Exception("승인된 문서가 존재해야만 100%가  될수 있습니다.");
		}

	    }

	    output = (ProjectOutput) PersistenceHelper.manager.save(output);
	    E3PSTask task = (E3PSTask) output.getTask();

	    updateCompletionFromOutput(task);

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectTaskHelper.class, e);
	    trx.rollback();
	    throw e;
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}
    }

    public static E3PSTask updateCompletion(E3PSTask task) throws Exception {
	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { E3PSTask.class };
	    Object args[] = new Object[] { task };

	    Object obj = RemoteMethodServer.getDefault().invoke("updateCompletion", ProjectTaskHelper.class.getName(), null, argTypes, args);
	    return (E3PSTask) obj;
	}
	// Transaction trx = new Transaction();

	try {
	    // trx.start();

	    boolean isCompleted = false;
	    if (task.getTaskCompletion() == 100) {
		if (isChild(task)) {
		    task.setTaskState(ProjectStateFlag.TASK_STATE_COMPLETE);
		}

		isCompleted = true;

	    }

	    task = (E3PSTask) PersistenceHelper.manager.save(task);

	    // trx.commit();

	    ProjectScheduleHelper.manager.post_modify_completion(task);

	    if (isCompleted) {
		sendSap(task);
		// try{
		// if(!PJTInfoERPInterface.sendProjectInfoToSap((JELProject)task.getProject(), System.out, false)){
		// Kogger.debug(getClass(), "updateCompletion error...");
		// }
		// }catch(Exception ex){
		// Kogger.error(getClass(), ex);
		// }

		// 후행 Task가 있으면 후행 Task 담당자에게 메일 발송
		Kogger.debug(ProjectTaskHelper.class, "##	공지메일 Start");

		// 1.후행Task 부터 찾아야지

		/*
	         * QueryResult dependList = TaskDependencyHelper.manager.getDependTaskList1(task);
	         * 
	         * 
	         * while ( dependList.hasMoreElements() ) { TaskDependencyLink link = (TaskDependencyLink)dependList.nextElement(); String linkOid = CommonUtil.getOIDString(link);
	         * 
	         * E3PSTaskData dependData = new E3PSTaskData((E3PSTask)link.getDependTarget());
	         * 
	         * 
	         * 
	         * //2. 있으면 담당자한테 메일 //누구한테 왜 뭔내용을 보낼것인가.
	         * 
	         * 
	         * }
	         */

		//

		// boolean result = e3ps.common.mail.MailUtil.manager.sendMail(hash);
		// Kogger.debug(getClass(), "Project Task Mail 발송 : " + result);

	    }

	    // trx = null;
	} catch (Exception e) {

	    Kogger.error(ProjectTaskHelper.class, e);
	    // trx.rollback();
	    throw e;
	} finally {
	    // if(trx != null) {
	    // trx.rollback();
	    // }
	}
	return task;
    }

    public static void noticeCompletion100(final E3PSTask task) {
	Thread thread = new Thread() {
	    public void run() {
		Class argTypes[] = new Class[] { E3PSTask.class };
		Object args[] = new Object[] { task };

		try {
		    RemoteMethodServer.getDefault().invoke("_noticeCompletion100", ProjectTaskHelper.class.getName(), null, argTypes, args);
		} catch (RemoteException e) {
		    // TODO Auto-generated catch block
		    Kogger.error(getClass(), e);
		} catch (InvocationTargetException e) {
		    // TODO Auto-generated catch block
		    Kogger.error(getClass(), e);
		}
	    }
	};

	thread.start();
    }

    public static void _noticeCompletion100(E3PSTask task) {

	SessionContext sessioncontext = SessionContext.newContext();
	try {
	    SessionHelper.manager.setAdministrator();
	    ProjectTreeNode node = (ProjectTreeNode) ProjectScheduleHelper.manager.getRoot(task, false, true);

	    HashMap toHash = new HashMap();

	    Vector taskNames = new Vector();

	    setMasters(node, toHash, taskNames);

	    WTUser from = (WTUser) SessionHelper.manager.getPrincipal();

	    String path = "";

	    for (int i = taskNames.size() - 1; i > -1; i--) {
		String tname = (String) taskNames.get(i);

		path += tname;
		if (i != 0) {
		    path += "/";
		}
	    }

	    // Kogger.debug(getClass(), path);

	    String projectNo = task.getProject().getPjtNo() + "(" + task.getProject().getPjtName() + ")";

	    String subject = projectNo + " 프로젝트의 " + path + " 테스크가 100% 가 되었습니다. 확인 후 종료 바랍니다.";

	    WTUser pmUser = ProjectUserHelper.manager.getPM(task.getProject());

	    toHash.put(pmUser.getEMail(), pmUser.getFullName());

	    // MailHtmlContentTemplate contentTemplate = MailHtmlContentTemplate.getInstance();

	    String text = path + " 테스크가 100% 가 되었습니다. 확인 후 종료 바랍니다.";

	    // String taskName = task.getTaskName();
	    // String title = "테스크  확인 종료";
	    // Hashtable hash = new Hashtable();
	    // hash.put("subject", title);
	    // hash.put("text", text);
	    // hash.put("link", host);
	    // hash.put("projectNo", projectNo);
	    // hash.put("taskName", taskName);
	    // String content = contentTemplate.htmlContent(hash, "ProjectDependencyMail.html");
	    //
	    // hash.clear();
	    // hash.put("FROM", from);
	    // hash.put("TO", toHash);
	    // hash.put("SUBJECT", subject);
	    // hash.put("CONTENT", content);
	    //
	    // boolean result = e3ps.common.mail.MailUtil.manager.sendMail(hash);
	    // Kogger.debug(getClass(), "Project Task Mail 발송 : " + result);

	} catch (Exception ex) {
	    Kogger.error(ProjectTaskHelper.class, ex);
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    public static void setMasters(ProjectTreeNode node, HashMap toHash, Vector names) {
	ProjectTreeNodeData pd = (ProjectTreeNodeData) node.getUserObject();
	E3PSTask task = (E3PSTask) pd.getData();
	// Kogger.debug(getClass(), "task Name2222 = " + task.getTaskName());
	names.add(task.getTaskName());
	QueryResult rs = TaskUserHelper.manager.getMaster(task);

	while (rs.hasMoreElements()) {
	    Object o[] = (Object[]) rs.nextElement();

	    TaskMemberLink link = (TaskMemberLink) o[0];
	    WTUser toUser = link.getMember().getMember();
	    try {
		toHash.put(toUser.getEMail(), toUser.getFullName());
	    } catch (WTException e) {
		// TODO Auto-generated catch block
		Kogger.error(ProjectTaskHelper.class, e);
	    }
	}

	if (node.getChildCount() > 0) {
	    setMasters((ProjectTreeNode) node.getChildAt(0), toHash, names);
	}
    }

    public static ArrayList getDependencyList(TemplateTask task) throws Exception {
	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { TemplateTask.class };
	    Object args[] = new Object[] { task };

	    Object obj = RemoteMethodServer.getDefault().invoke("getDependencyList", ProjectTaskHelper.class.getName(), null, argTypes, args);
	    return (ArrayList) obj;
	}
	DefaultProjectTreeNode root = null;
	if (task instanceof E3PSTask) {
	    E3PSTask jelTask = (E3PSTask) task;
	    root = ProjectTreeModel.getLoadTree((E3PSProject) jelTask.getProject());
	} else {
	    root = TemplateProjectModel.getLoadTree(task.getProject(), null);
	}

	ArrayList list = new ArrayList();
	checkDependency(root, list);
	int i = 0;
	for (; i < list.size(); i++) {
	    TemplateTask canDependTask = (TemplateTask) list.get(i);
	    if (PersistenceHelper.isEquivalent(canDependTask, task)) {
		break;
	    }
	}

	ArrayList rlist = new ArrayList();

	for (int j = 0; j < i; j++) {
	    rlist.add(list.get(j));
	}

	return rlist;
    }

    public static Vector getSubTask(TemplateTask task) throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { TemplateTask.class };
	    Object args[] = new Object[] { task };

	    Object obj = RemoteMethodServer.getDefault().invoke("getSubTask", ProjectTaskHelper.class.getName(), null, argTypes, args);
	    return (Vector) obj;
	}

	DefaultProjectTreeNode root = null;

	if (task instanceof E3PSTask) {
	    E3PSTask jelTask = (E3PSTask) task;

	    ProjectTreeModel model = new ProjectTreeModel(jelTask);
	    model.setTree();
	    // model.checkDependencySchedule();
	    model.checkAllSchedule();
	    root = model.getRoot();

	} else {
	    root = TemplateProjectModel.getLoadTreeNode(task);
	}

	// Kogger.debug(getClass(), "root.size() = " + root.getChildCount());

	Vector list = new Vector();
	childNodes(root, list);

	return list;
    }

    private static void childNodes(DefaultProjectTreeNode node, Vector list) {
	TreeNodeData td = (TreeNodeData) node.getUserObject();
	Persistable data = (Persistable) td.getData();
	list.add(data);

	for (int i = 0; i < node.getChildCount(); i++) {
	    DefaultProjectTreeNode child = (DefaultProjectTreeNode) node.getChildAt(i);
	    childNodes(child, list);
	}
    }

    private static boolean checkDependency(DefaultProjectTreeNode node, ArrayList list) {
	TreeNodeData td = (TreeNodeData) node.getUserObject();
	Persistable data = (Persistable) td.getData();
	if (node.getChildCount() == 0) {
	    if (td instanceof ProjectTreeNodeData) {
		ProjectTreeNodeData pd = (ProjectTreeNodeData) td;
		if (pd.getState() != ProjectStateFlag.PROJECT_STATE_COMPLETE) {
		    list.add(data);
		}
	    } else {
		list.add(data);
	    }
	}

	for (int i = 0; i < node.getChildCount(); i++) {
	    DefaultProjectTreeNode child = (DefaultProjectTreeNode) node.getChildAt(i);
	    if (checkDependency(child, list)) {
		break;
	    }
	}

	return false;
    }

    public static void deleteTemplateTask(ParamHash paramHash) throws Exception {
	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { ParamHash.class };
	    Object args[] = new Object[] { paramHash };

	    RemoteMethodServer.getDefault().invoke("deleteTemplateTask", ProjectTaskHelper.class.getName(), null, argTypes, args);
	    return;// (TemplateTask)obj;
	}

	String coid = ParamUtil.getStrParameter(paramHash.getStr("coid"));

	TemplateTask task = (TemplateTask) CommonUtil.getObject(coid);
	Transaction trx = new Transaction();

	try {
	    trx.start();
	    if (isChild(task)) {
		throw new Exception("하위 태스크가 존재 합니다.");
	    }

	    PersistenceHelper.manager.delete(task);
	    TemplateProject project = task.getProject();
	    trx.commit();

	    ProjectScheduleHelper.manager.post_modify_template_duration(project);
	    trx = null;
	} catch (Exception e) {
	    // Kogger.error(getClass(), e);
	    trx.rollback();
	    throw e;
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}
    }

    public static void deleteTask(ParamHash paramHash) throws Exception {
	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { ParamHash.class };
	    Object args[] = new Object[] { paramHash };

	    RemoteMethodServer.getDefault().invoke("deleteTask", ProjectTaskHelper.class.getName(), null, argTypes, args);
	    return;// (TemplateTask)obj;
	}

	String coid = ParamUtil.getStrParameter(paramHash.getStr("coid"));
	// Kogger.debug(getClass(), "coid == > :" + coid);
	E3PSTask task = (E3PSTask) CommonUtil.getObject(coid);

	Transaction trx = new Transaction();

	try {
	    trx.start();

	    if (isChild(task)) {
		throw new Exception("하위 테스크가 존재 합니다.");
	    }

	    if (task.isOptionType()) {
		throw new Exception("필수 태스크는 삭제 할 수 없습니다.");
	    }

	    boolean isComplete = (ProjectStateFlag.TASK_STATE_COMPLETE == task.getTaskState());
	    if (isComplete) {
		throw new Exception("완료된 테스크는 삭제할 수 없습니다.");
	    }

	    QueryResult qr = ProjectOutputHelper.manager.getTaskOutputDocLink(task);
	    if (qr != null && qr.hasMoreElements()) {
		throw new Exception("산출물이 등록 되어 있습니다.");
	    }

	    E3PSProject project = (E3PSProject) task.getProject();

	    PersistenceHelper.manager.delete(task);
	    trx.commit();
	    ProjectScheduleHelper.manager.post_modify_Schedule(project);
	    trx = null;
	} catch (Exception e) {
	    // Kogger.error(getClass(), e);
	    trx.rollback();
	    throw e;
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}
    }

    public static E3PSTask updateTask(ParamHash paramHash) throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { ParamHash.class };
	    Object args[] = new Object[] { paramHash };

	    Object obj = RemoteMethodServer.getDefault().invoke("updateTask", ProjectTaskHelper.class.getName(), null, argTypes, args);
	    return (E3PSTask) obj;
	}

	String coid = (String) paramHash.get("coid"); // JELTask Object OID
	String cname = (String) paramHash.get("cname"); // Task Name
	// int cseq = StringUtil.parseInt((String) paramHash.get("cseq"), 1); //Task 순서
	String cstartdate = (String) paramHash.get("cstartdate"); // Task 시작일자
	String cenddate = (String) paramHash.get("cenddate"); // Task 종료일자
	String desc = (String) paramHash.get("desc"); // Task 설명

	String deptOid = (String) paramHash.get("deptOid");
	String optionType = (String) paramHash.get("optionType");
	String mileStone = (String) paramHash.get("mileStone");
	String taskType = (String) paramHash.get("taskType");
	String drValue = (String) paramHash.get("drValue");

	E3PSTask task = (E3PSTask) CommonUtil.getObject(coid);
	Transaction trx = new Transaction();

	try {
	    trx.start();

	    boolean isComplete = (ProjectStateFlag.TASK_STATE_COMPLETE == task.getTaskState());
	    if (isComplete) {
		if (CommonUtil.isAdmin()) {

		} else {

		    throw new Exception("완료된 테스크는 수정할 수 없습니다.");

		}
	    }
	    boolean isChildCheck = false;
	    if (task.getParent() != null) {
		isChildCheck = isChildName((TemplateTask) task.getParent(), cname.trim());
	    }
	    if (!task.getTaskName().equalsIgnoreCase(cname) && isChildCheck) {
		throw new Exception("이미 " + cname + " 이름을 가진 테스크가 존재합니다. ");
	    }

	    task.setTaskName(cname);

	    E3PSTaskData taskData = new E3PSTaskData(task);
	    Calendar tempCal = Calendar.getInstance();

	    // 1. ExtendScheduleData Object Getter
	    ExtendScheduleData schedule = taskData.schedule;

	    // 1.1 총 기간 [ERP Attribute]
	    if (StringUtil.checkString(cstartdate.trim()) && StringUtil.checkString(cenddate.trim())) {
		int tempDuration = DateUtil.getDaysFromTo(cenddate, cstartdate);
		schedule.setDuration(tempDuration);
	    }

	    if (StringUtil.checkString(cstartdate.trim())) {
		tempCal.setTime(DateUtil.parseDateStr(cstartdate));
		// 1.3.1 계획 시작일자 [PLM Attribute]
		schedule.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
		// 1.3.2 실제 시작일자 [PLM Attribute]
		// schedule.setExecStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	    }
	    // 1.4 계획 종료일자 & 실제 종료일자
	    if (StringUtil.checkString(cenddate.trim())) {
		tempCal.setTime(DateUtil.parseDateStr(cenddate));
		// 1.4.1 계획 종료일자 [PLM Attribute]
		schedule.setPlanEndDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
		// 1.4.2 실제 종료일자 [PLM Attribute]
		// schedule.setExecEndDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	    }

	    if (deptOid != null && deptOid.length() > 0) {
		E3PSTask parentTask = (E3PSTask) task.getParent();

		if (parentTask != null) {

		    String pmEnd = "99999999";
		    ScheduleData psd = (ScheduleData) parentTask.getTaskSchedule().getObject();

		}
	    }
	    // 1.5 표준공수
	    // schedule.setStdWork(Integer.parseInt(stdWork));
	    // 1.6 ExtendScheduleData Object Save
	    schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);

	    // 2.3 테스크 순서 (taskSeq)
	    // task.setTaskSeq(cseq);

	    task.setTaskDesc(desc);

	    task.setOptionType(Boolean.valueOf(optionType).booleanValue());
	    task.setMileStone(Boolean.valueOf(mileStone).booleanValue());
	    task.setTaskType(taskType);
	    task.setDrValue(drValue);

	    task = (E3PSTask) PersistenceHelper.manager.save(task);

	    trx.commit();
	    ProjectScheduleHelper.manager.post_modify_Schedule(task);

	    trx = null;
	} catch (Exception e) {
	    // Kogger.error(getClass(), e);
	    trx.rollback();
	    throw e;
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}

	return task;
    }

    public static TemplateTask updateTemplateTask(ParamHash paramHash) throws Exception {
	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { ParamHash.class };
	    Object args[] = new Object[] { paramHash };

	    Object obj = RemoteMethodServer.getDefault().invoke("updateTemplateTask", ProjectTaskHelper.class.getName(), null, argTypes, args);
	    return (TemplateTask) obj;
	}

	String coid = (String) paramHash.get("coid");
	String cname = (String) paramHash.get("cname");
	String desc = (String) paramHash.get("desc");

	int duration = StringUtil.parseInt((String) paramHash.get("duration"), 1);
	// int stdWork = StringUtil.parseInt((String)paramHash.get("stdWork"), 0);

	String optionType = (String) paramHash.get("optionType");
	String mileStone = (String) paramHash.get("mileStone");
	String taskType = (String) paramHash.get("taskType");
	String drValue = (String) paramHash.get("drValue");

	TemplateTask task = (TemplateTask) CommonUtil.getObject(coid);
	Transaction trx = new Transaction();

	try {
	    trx.start();
	    ScheduleData schedule = (ScheduleData) task.getTaskSchedule().getObject();
	    schedule.setDuration(duration);
	    // schedule.setStdWork(stdWork);
	    schedule = (ScheduleData) PersistenceHelper.manager.modify(schedule);

	    /* one level이라면 */

	    // if(!task.getTaskName().equalsIgnoreCase(cname) && isChildName((TemplateTask)task.getParent(), cname.trim())){
	    // throw new Exception("이미 " + cname + " 이름을 가진 테스크가 존재합니다. ");
	    // }
	    task.setTaskName(cname);

	    task.setTaskDesc(desc);

	    task.setOptionType(Boolean.valueOf(optionType).booleanValue());
	    task.setMileStone(Boolean.valueOf(mileStone).booleanValue());
	    task.setTaskType(taskType);
	    task.setDrValue(drValue);

	    task = (TemplateTask) PersistenceHelper.manager.save(task);

	    trx.commit();

	    ProjectScheduleHelper.manager.post_modify_template_duration(task);
	    trx = null;
	} catch (Exception e) {
	    // Kogger.error(getClass(), e);
	    trx.rollback();
	    throw e;
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}

	return task;
    }

    /**
     * 함수명 : createDebugTask 수정내용 : [PLM System 1차개선] 디버깅 Task 생성 시 선후행 관계 Check하지 않음
     * 
     * @param pjtOid
     * @param reason
     * @param special
     * @param vec
     * @return
     * @throws Exception
     *             수정자 : BoLee 수정일자 : 2013. 7. 15.
     */
    public static E3PSTask createDebugTask(String pjtOid, String reason, String special, Vector vec) throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { String.class, String.class, String.class, Vector.class };
	    Object args[] = new Object[] { pjtOid, reason, special, vec };
	    Object obj = RemoteMethodServer.getDefault().invoke("createDebugTask", ProjectTaskHelper.class.getName(), null, argTypes, args);
	    return (E3PSTask) obj;
	}

	Transaction trx = new Transaction();
	E3PSTask task = null;
	boolean isOneLeveDebug = false;

	try {
	    trx.start();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(pjtOid);
	    int ncha = getDeBugChaSh(project);
	    // reason
	    CheckoutLink checklink = (CheckoutLink) HistoryHelper.checkOut(project, special, Integer.parseInt(reason), "PLANCHANGE");
	    project = (E3PSProject) checklink.getWorkingCopy();

	    QueryResult rs = ProjectTaskHelper.manager.getTaskWithType(project, "디버깅");

	    E3PSTask debugTask = null;

	    if (rs.hasMoreElements()) {
		Object[] o = (Object[]) rs.nextElement();

		debugTask = (E3PSTask) o[0];
	    }

	    if (debugTask == null) {
		debugTask = createDebugOneLevel(project);
		isOneLeveDebug = true;
	    }

	    ExtendScheduleData schedule = new ExtendScheduleData();

	    Calendar tempCal = Calendar.getInstance();

	    schedule.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));

	    schedule.setPlanEndDate(new java.sql.Timestamp(tempCal.getTime().getTime()));

	    schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);

	    task = E3PSTask.newE3PSTask();

	    task.setNcha(ncha);
	    task.setDebug_n(true);

	    task.setTaskName(ncha + " 차 디버깅");
	    task.setTaskSchedule(ObjectReference.newObjectReference(schedule));

	    task.setParent(debugTask);
	    task.setProject(project);

	    task.setReason(reason);
	    task.setSpecial(special);

	    int taskmaxSeq = ProjectTaskHelper.getMaxSeq((TemplateTask) task.getParent(), project);
	    // Kogger.debug(getClass(), "taskmaxSeq === " + taskmaxSeq);
	    task.setTaskSeq(taskmaxSeq);
	    task.setTaskNo(String.valueOf(taskmaxSeq));

	    // task.setTaskType("");
	    task = (E3PSTask) PersistenceHelper.manager.save(task);

	    TemplateProject tempProject = project.getTemplate();

	    TemplateTask templateTask = null;
	    // Kogger.debug(getClass(), "#################################################################### 디버깅 Role");
	    if (tempProject != null) {
		templateTask = getDebugTaskALL(tempProject);
		if (templateTask != null) {
		    // Kogger.debug(getClass(), "templateTask==>"+templateTask.getTaskName());
		}
	    }

	    if (templateTask != null) {
		String roleName = templateTask.getPersonRole();
		// Kogger.debug(getClass(), "roleName==>"+roleName);
		if (roleName != null && roleName.length() > 0) {
		    TaskUserHelper.manager.setTaskRole(task, roleName);
		}
	    }

	    E3PSTask productTask1 = (E3PSTask) getTask(debugTask, "Pilot Sample대응");
	    if (productTask1 != null) {
		taskmaxSeq = ProjectTaskHelper.getMaxSeq((TemplateTask) task.getParent(), project);
		productTask1.setTaskSeq(taskmaxSeq);
		PersistenceHelper.manager.save(productTask1);
	    }

	    E3PSTask productTask = (E3PSTask) getTask(debugTask, "제품합격");
	    if (productTask != null) {
		taskmaxSeq = ProjectTaskHelper.getMaxSeq((TemplateTask) task.getParent(), project);
		productTask.setTaskSeq(taskmaxSeq);
		PersistenceHelper.manager.save(productTask);

	    }

	    if (debugTask != null) {
		/* 부모 선행 link삭제 */
		QueryResult results = PersistenceHelper.manager.navigate(debugTask, TaskDependencyLink.DEPEND_TARGET_ROLE, TaskDependencyLink.class, false);
		while (results.hasMoreElements()) {
		    PersistenceHelper.manager.delete((TaskDependencyLink) results.nextElement());
		}
		/* 부모 후행 link삭제 */
		results = PersistenceHelper.manager.navigate(debugTask, TaskDependencyLink.DEPEND_SOURCE_ROLE, TaskDependencyLink.class, false);
		while (results.hasMoreElements()) {
		    PersistenceHelper.manager.delete((TaskDependencyLink) results.nextElement());
		}
	    }

	    String parentTaskOid = CommonUtil.getOIDString(task);

	    E3PSTask preTask = null;
	    E3PSTask createTask = null;
	    for (int i = 0; i < vec.size(); i++) {
		ParamHash paramHash = (ParamHash) vec.get(i);
		paramHash.put("oid", parentTaskOid);

		createTask = _createTask(paramHash);
		if (preTask != null) {
		    TaskDependencyHelper.manager.setDependTask(createTask, preTask);
		}
		preTask = createTask;

	    }

	    project = (E3PSProject) PersistenceHelper.manager.refresh(project);

	    // [START] [PLM System 1차개선] 디버깅 Task 생성 시 선후행 관계 Check하지 않음, 2013-07-15, BoLee
	    ProjectScheduleHelper.manager.post_modify_Schedule(project, false);
	    // [END] [PLM System 1차개선] 디버깅 Task 생성 시 선후행 관계 Check하지 않음, 2013-07-15, BoLee

	    if (isOneLeveDebug) {
		E3PSTask afterTask = (E3PSTask) getTask(project, "금형이관단계");
		int seq = 0;
		if (afterTask != null) {
		    seq = afterTask.getTaskSeq();
		    int result = updateSeq((E3PSProject) afterTask.getProject(), seq);
		    // Kogger.debug(getClass(), "seq = " + seq + " result = " + result);
		    if (result > 0) {
			debugTask.setTaskSeq(seq);
			PersistenceHelper.manager.save(debugTask);
		    }
		}
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectTaskHelper.class, e);

	    throw e;
	}

	return task;

    }

    public static E3PSTask createDebugOneLevel(E3PSProject project) throws Exception {
	ExtendScheduleData schedule = new ExtendScheduleData();

	Calendar tempCal = Calendar.getInstance();

	schedule.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));

	schedule.setPlanEndDate(new java.sql.Timestamp(tempCal.getTime().getTime()));

	schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);

	E3PSTask task = E3PSTask.newE3PSTask();

	task.setDebug_n(true);
	task.setTaskName("디버깅단계");
	task.setTaskType("디버깅");
	task.setTaskSchedule(ObjectReference.newObjectReference(schedule));
	task.setProject(project);

	int seq = ProjectTaskHelper.getMaxSeq(null, project);

	task.setTaskSeq(seq);
	task.setTaskNo(String.valueOf(seq));

	// task.setTaskType("");
	task = (E3PSTask) PersistenceHelper.manager.save(task);

	return task;
    }

    public static E3PSTask createTask(ParamHash paramHash) throws Exception {
	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { ParamHash.class };
	    Object args[] = new Object[] { paramHash };
	    Object obj = RemoteMethodServer.getDefault().invoke("createTask", ProjectTaskHelper.class.getName(), null, argTypes, args);
	    return (E3PSTask) obj;
	}

	Transaction trx = new Transaction();
	E3PSTask task = null;
	try {
	    trx.start();

	    task = _createTask(paramHash);
	    trx.commit();
	    ProjectScheduleHelper.manager.post_modify_Schedule(task);
	    trx = null;
	} catch (Exception e) {
	    // Kogger.error(getClass(), e);
	    trx.rollback();
	    throw e;
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}
	return task;
    }

    public static E3PSTask _createTask(ParamHash paramHash) throws Exception {

	String oid = (String) paramHash.get("oid"); //
	String cname = (String) paramHash.get("cname"); // Task Name
	// int cseq = StringUtil.parseInt((String) paramHash.get("cseq"), 1); //Task 순서
	String cstartdate = (String) paramHash.get("cstartdate"); // Task 시작일자
	String cenddate = (String) paramHash.get("cenddate"); // Task 종료일자
	String desc = (String) paramHash.get("desc"); // Task 설명
	String stdWork = (String) paramHash.get("stdWork"); // Task 표준공수

	String debug_sub = (String) paramHash.get("debug_sub"); // 금형 N차 Task Sub 냐?

	String optionType = (String) paramHash.get("optionType");
	String mileStone = (String) paramHash.get("mileStone");
	String taskType = (String) paramHash.get("taskType");
	String drValue = (String) paramHash.get("drValue");

	E3PSProject pjtProject = null;
	E3PSTask parentTask = null;
	E3PSProjectData pjtData = null;

	Object obj = CommonUtil.getObject(oid);
	// Kogger.debug(getClass(), "cstartdate: " + cstartdate);
	// Kogger.debug(getClass(), "cenddate: " + cenddate);

	if (obj instanceof E3PSProject) {
	    pjtProject = (E3PSProject) obj;
	    pjtData = new E3PSProjectData(pjtProject);
	} else if (obj instanceof E3PSTask) {
	    parentTask = (E3PSTask) obj;

	    if (parentTask.getTaskState() == ProjectStateFlag.TASK_STATE_COMPLETE) {

		if (CommonUtil.isAdmin()) {

		}
		// throw new Exception("완료된 테스크는 하위 테스크를 추가 할 수 없습니다.");
	    }

	    if (parentTask.isDebug_sub()) {
		throw new Exception("하위 테스크를 추가 할수 없는 태스크 입니다.");
	    }
	}

	Calendar tempCal = Calendar.getInstance();

	// 1. ExtendScheduleData Object Create
	ExtendScheduleData schedule = new ExtendScheduleData();
	// 1.1 총 기간 [ERP Attribute]
	if (StringUtil.checkString(cstartdate.trim()) && StringUtil.checkString(cenddate.trim())) {
	    int tempDuration = DateUtil.getDaysFromTo(cenddate, cstartdate);
	    schedule.setDuration(tempDuration);
	}

	if (StringUtil.checkString(cstartdate.trim())) {
	    tempCal.setTime(DateUtil.parseDateStr(cstartdate));
	    // 1.3.1 계획 시작일자 [PLM Attribute]
	    schedule.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	    // 1.3.2 실제 시작일자 [PLM Attribute]
	    // schedule.setExecStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	}
	// 1.4 계획 종료일자 & 실제 종료일자
	if (StringUtil.checkString(cenddate.trim())) {
	    tempCal.setTime(DateUtil.parseDateStr(cenddate));
	    // 1.4.1 계획 종료일자 [PLM Attribute]
	    schedule.setPlanEndDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	    // 1.4.2 실제 종료일자 [PLM Attribute]
	    // schedule.setExecEndDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	}
	// 1.5 표준공수
	if (stdWork != null && stdWork.length() > 0) {
	    schedule.setStdWork(Integer.parseInt(stdWork));
	}
	// 1.6 ExtendScheduleData Object Save
	schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);

	E3PSTask task = E3PSTask.newE3PSTask();
	task.setTaskName(cname.trim());
	task.setTaskDesc(desc);
	task.setTaskSchedule(ObjectReference.newObjectReference(schedule));

	// 2.7 Link
	if (pjtProject != null) {
	    task.setProject(pjtProject);
	} else if (parentTask != null) {
	    // 2.10.2 TaskDependencyLink Connect

	    if (parentTask.isDebug_n()) {
		int nCha = parentTask.getNcha();
		task.setNcha(nCha);
	    }

	    task.setParent(parentTask);
	    task.setProject(parentTask.getProject());
	}
	task.setTaskName(cname);

	if (parentTask == null) {
	    if (isChildName(task.getProject(), cname)) {
		throw new Exception("이미 " + cname + " 이름을 가진 테스크가 존재합니다. ");
	    }
	} else {
	    if (isChildName(parentTask, cname)) {
		throw new Exception("이미 " + cname + " 이름을 가진 테스크가 존재합니다. ");
	    }
	}

	int maxSeq = ProjectTaskHelper.getMaxSeq((TemplateTask) task.getParent(), task.getProject());
	task.setTaskSeq(maxSeq);
	task.setTaskNo(String.valueOf(maxSeq));

	if (optionType != null && optionType.length() > 0) {
	    // task.setOptionType(Boolean.valueOf(optionType).booleanValue());
	    task.setOptionType(Boolean.valueOf("false").booleanValue());
	}

	if (mileStone != null && mileStone.length() > 0) {
	    task.setMileStone(Boolean.valueOf(mileStone).booleanValue());
	}

	if (debug_sub != null && debug_sub.length() > 0) {
	    task.setDebug_sub(Boolean.valueOf(debug_sub).booleanValue());
	}

	task.setTaskType(taskType);
	task.setDrValue(drValue);
	task = (E3PSTask) PersistenceHelper.manager.save(task);

	if (parentTask != null) {
	    /* 부모 선행 link삭제 */
	    QueryResult results = PersistenceHelper.manager.navigate(parentTask, TaskDependencyLink.DEPEND_TARGET_ROLE, TaskDependencyLink.class, false);
	    while (results.hasMoreElements()) {
		PersistenceHelper.manager.delete((TaskDependencyLink) results.nextElement());
	    }
	    /* 부모 후행 link삭제 */
	    results = PersistenceHelper.manager.navigate(parentTask, TaskDependencyLink.DEPEND_SOURCE_ROLE, TaskDependencyLink.class, false);
	    while (results.hasMoreElements()) {
		PersistenceHelper.manager.delete((TaskDependencyLink) results.nextElement());
	    }
	}

	if (debug_sub != null && debug_sub.length() > 0) {
	    // Kogger.debug(getClass(), "debug_sub = " + debug_sub);
	    E3PSProject project = (E3PSProject) task.getProject();
	    // Kogger.debug(getClass(), "project = " + project);
	    TemplateProject tempProject = project.getTemplate();
	    // Kogger.debug(getClass(), "tempProject = " + tempProject);
	    TemplateTask templateTask = null;
	    if (tempProject != null) {
		templateTask = getDebugTask(tempProject, task.getTaskName());
	    }

	    // Kogger.debug(getClass(), "$$$$$$$$$$$$$$$$$");
	    // Kogger.debug(getClass(), "templateTask = " + templateTask);
	    if (templateTask != null) {
		String roleName = templateTask.getPersonRole();
		// Kogger.debug(getClass(), "roleName = " + roleName);
		if (roleName != null && roleName.length() > 0) {
		    TaskUserHelper.manager.setTaskRole(task, roleName);
		}
		ProjectOutputHelper.manager.copyTaskOutputInfo(new HashMap(), new HashMap(), task, templateTask);
	    }

	    // TaskUserHelper.manager.setTaskRole(task, String role);
	    // ProjectOutputHelper.manager.copyTaskOutputInfo(new HashMap(), new HashMap(),task, //fromTask);
	}
	return task;
    }

    public static TemplateTask getDebugTask(TemplateProject project, String taskName) throws Exception {

	QuerySpec spec = new QuerySpec();
	int idx1 = spec.addClassList(TemplateTask.class, false);
	int idx2 = spec.appendClassList(TemplateTask.class, true);

	ClassAttribute ca0 = new ClassAttribute(TemplateTask.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute ca1 = new ClassAttribute(TemplateTask.class, TemplateTask.PARENT_REFERENCE + ".key.id");

	SearchCondition sc = new SearchCondition(ca0, "=", ca1);
	sc.setFromIndicies(new int[] { idx1, idx2 }, 0);
	sc.setOuterJoin(0);
	spec.appendWhere(sc, new int[] { idx1, idx2 });

	spec.appendAnd();

	spec.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.TASK_NAME, "=", "디버깅(1~N차)"), new int[] { idx1 });

	spec.appendAnd();

	long pjtId = project.getPersistInfo().getObjectIdentifier().getId();

	spec.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.PROJECT_REFERENCE + ".key.id", "=", pjtId), new int[] { idx1 });

	spec.appendAnd();

	ClassAttribute caN = new ClassAttribute(TemplateTask.class, TemplateTask.TASK_NAME);
	SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, caN);
	ColumnExpression ce = ConstantExpression.newExpression("%" + taskName.trim().toUpperCase() + "%");
	SearchCondition sc0 = new SearchCondition(upper, SearchCondition.LIKE, ce);
	spec.appendWhere(sc0, new int[] { idx2 });

	QueryResult rs = PersistenceHelper.manager.find(spec);
	TemplateTask task = null;

	if (rs.hasMoreElements()) {
	    Object[] o = (Object[]) rs.nextElement();
	    task = (TemplateTask) o[0];
	}

	return task;
    }

    public static TemplateTask getDebugTaskALL(TemplateProject project) throws Exception {

	QuerySpec spec = new QuerySpec();
	int idx1 = spec.addClassList(TemplateTask.class, true);

	spec.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.TASK_NAME, "=", "디버깅(1~N차)"), new int[] { idx1 });

	spec.appendAnd();

	long pjtId = project.getPersistInfo().getObjectIdentifier().getId();

	spec.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.PROJECT_REFERENCE + ".key.id", "=", pjtId), new int[] { idx1 });

	QueryResult rs = PersistenceHelper.manager.find(spec);

	TemplateTask task = null;

	if (rs.hasMoreElements()) {
	    Object[] o = (Object[]) rs.nextElement();
	    task = (TemplateTask) o[0];
	}

	return task;
    }

    public static TemplateTask createTemplateTask(ParamHash paramHash) throws Exception {
	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { ParamHash.class };
	    Object args[] = new Object[] { paramHash };
	    Object obj = RemoteMethodServer.getDefault().invoke("createTemplateTask", ProjectTaskHelper.class.getName(), null, argTypes, args);
	    return (TemplateTask) obj;
	}

	String oid = (String) paramHash.get("oid");
	String cname = (String) paramHash.get("cname");
	// int cseq = paramHash.getInt("cseq",1);
	int duration = paramHash.getInt("duration", 1); // 기간
	String desc = (String) paramHash.get("desc");
	String wbsOid = (String) paramHash.get("wbsItem");
	int stdWork = paramHash.getInt("stdWork", 0); // 표준공수

	String optionType = (String) paramHash.get("optionType");
	String mileStone = (String) paramHash.get("mileStone");
	String taskType = (String) paramHash.get("taskType");
	String drValue = (String) paramHash.get("drValue");

	// Kogger.debug(getClass(), "wbsOid=======" + wbsOid);

	Transaction trx = new Transaction();
	TemplateTask task = null;
	try {
	    trx.start();
	    ScheduleData schedule = ScheduleData.newScheduleData();
	    schedule.setDuration(duration);
	    // schedule.setStdWork(stdWork);
	    schedule = (ScheduleData) PersistenceHelper.manager.save(schedule);
	    task = TemplateTask.newTemplateTask();

	    // task.setTaskSeq(cseq);
	    task.setTaskDesc(desc);
	    task.setTaskSchedule(ObjectReference.newObjectReference(schedule));

	    Object obj = CommonUtil.getObject(oid);

	    TemplateTask parentTask = null;

	    if (obj instanceof TemplateProject) {
		task.setProject((TemplateProject) obj);
	    }

	    if (obj instanceof TemplateTask) {
		parentTask = (TemplateTask) obj;
		task.setProject(parentTask.getProject());
		task.setParent(parentTask);
	    }

	    task.setTaskName(cname);
	    if (parentTask == null) {

		if (isChildName(task.getProject(), cname)) {
		    throw new Exception("이미 " + cname + " 이름을 가진 테스크가 존재합니다. ");
		}
	    } else {
		if (isChildName(parentTask, cname)) {
		    throw new Exception("이미 " + cname + " 이름을 가진 테스크가 존재합니다. ");
		}
	    }

	    // if ( obj instanceof TemplateProject ) {
	    // task.setProject((TemplateProject)obj);
	    // String wbsOid = (String)paramHash.get("wbsItem");
	    //
	    // boolean isWBSItem = WBSItemHelper.isWBSItem(task.getProject(), CommonUtil.getOIDLongValue(wbsOid));
	    // if(isWBSItem){
	    // throw new Exception("이미 등록되어 있습니다.");
	    // }
	    //
	    // wbsItem = (WBSItem)CommonUtil.getObject(wbsOid);
	    // String wbsName = wbsItem.getWbsName();
	    // task.setTaskName(wbsName);
	    //
	    // } else if ( obj instanceof TemplateTask ) {
	    //
	    // parentTask = (TemplateTask)obj;
	    // task.setParent(parentTask);
	    // task.setProject(parentTask.getProject());
	    //
	    // QueryResult rs = PersistenceHelper.manager.navigate(parentTask, TaskWBSItemLink.WBS_ROLE, TaskWBSItemLink.class);
	    // boolean isERPTask = false;
	    // boolean isNotPlanTask = false;
	    //
	    // if(rs.hasMoreElements()){
	    // WBSItem pwbsItem = (WBSItem)rs.nextElement();
	    // isERPTask = pwbsItem.isIsERP();
	    // isNotPlanTask = pwbsItem.isIsNotPlanTask();
	    // }
	    //
	    // if(isERPTask){
	    // throw new Exception("ERP 전용 테스크는 하위 테스크를 가질 수 없습니다.");
	    // }
	    // if(isNotPlanTask){
	    // throw new Exception("기간 산정을 하지 않기 때문에 하위 테스크를 가질 수 없습니다");
	    // }
	    //
	    // task.setTaskName(cname);
	    //
	    // if(isChildName(parentTask, cname)){
	    // throw new Exception("이미 " + cname + " 이름을 가진 테스크가 존재합니다. ");
	    // }
	    //
	    // }

	    int maxSeq = ProjectTaskHelper.getMaxSeq((TemplateTask) task.getParent(), task.getProject());

	    task.setTaskSeq(maxSeq);
	    task.setTaskNo(String.valueOf(maxSeq));

	    task.setOptionType(Boolean.valueOf(optionType).booleanValue());
	    task.setMileStone(Boolean.valueOf(mileStone).booleanValue());
	    task.setTaskType(taskType);
	    task.setDrValue(drValue);

	    task = (TemplateTask) PersistenceHelper.manager.save(task);

	    if (parentTask != null) {
		/* 부모 선행 link삭제 */
		QueryResult results = PersistenceHelper.manager.navigate(parentTask, TaskDependencyLink.DEPEND_TARGET_ROLE, TaskDependencyLink.class, false);
		while (results.hasMoreElements()) {
		    PersistenceHelper.manager.delete((TaskDependencyLink) results.nextElement());
		}
		/* 부모 후행 link삭제 */
		results = PersistenceHelper.manager.navigate(parentTask, TaskDependencyLink.DEPEND_SOURCE_ROLE, TaskDependencyLink.class, false);
		while (results.hasMoreElements()) {
		    PersistenceHelper.manager.delete((TaskDependencyLink) results.nextElement());
		}
	    }
	    trx.commit();
	    ProjectScheduleHelper.manager.post_modify_template_duration(task);
	    trx = null;
	} catch (Exception e) {
	    // Kogger.error(getClass(), e);
	    trx.rollback();
	    throw e;
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}
	return task;
    }

    public static int getDeBugChaSh(E3PSProject project) throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { E3PSProject.class };
	    Object args[] = new Object[] { project };
	    try {
		Object obj = RemoteMethodServer.getDefault().invoke("getDeBugChaSh", ProjectTaskHelper.class.getName(), null, argTypes, args);
		return ((Integer) obj).intValue();

	    } catch (RemoteException e) {
		Kogger.error(ProjectTaskHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectTaskHelper.class, e);
		throw new WTException(e);
	    }
	}

	String tableName = "";

	Class taskClass = E3PSTask.class;

	ClassInfo classinfo = WTIntrospector.getClassInfo(taskClass);
	if (DatabaseInfoUtilities.isAutoNavigate(classinfo)) {
	    tableName = DatabaseInfoUtilities.getBaseTableName(classinfo);
	} else {
	    tableName = DatabaseInfoUtilities.getValidTableName(classinfo);
	}

	String ncha = DatabaseInfoUtilities.getValidColumnName(classinfo, E3PSTask.NCHA);

	String debug_n = DatabaseInfoUtilities.getValidColumnName(classinfo, E3PSTask.DEBUG_N);

	String projectKeyColumName = DatabaseInfoUtilities.getValidColumnName(classinfo, "projectReference.key.id");

	Connection con = null;
	PreparedStatement st = null;
	ResultSet rs = null;
	int childLength = 0;
	try {
	    con = DBCPManager.getConnection("plm");

	    String sql = "select nvl(max(" + ncha + "), '0') from " + tableName + " where " + debug_n + " = 1 and " + projectKeyColumName + "=?";
	    // Kogger.debug(ProjectTaskHelper.class, "###### sql = "+sql);
	    st = con.prepareStatement(sql);
	    long parentTaskId = 0;

	    st.setLong(1, project.getPersistInfo().getObjectIdentifier().getId());

	    rs = st.executeQuery();

	    if (rs.next()) {
		childLength = rs.getInt(1);
	    } else {
		throw new Exception("error..");
	    }

	    // write(root);
	} catch (Exception e) {
	    Kogger.error(ProjectTaskHelper.class, e);
	    throw e;
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		}
		if (st != null) {
		    st.close();
		}
		if (con != null)
		    con.close();
	    } catch (SQLException e) {
		Kogger.error(ProjectTaskHelper.class, e);
	    }
	}
	return childLength + 1;

	// select nvl(max(taskcode), '1') from jeltask where taskcode not like 'R%'
    }

    public static int updateSeq(E3PSProject project, int seq) throws Exception {

	int count = 0;
	ClassInfo classinfo = WTIntrospector.getClassInfo(E3PSTask.class);
	String tableName = "";
	if (DatabaseInfoUtilities.isAutoNavigate(classinfo)) {
	    tableName = DatabaseInfoUtilities.getBaseTableName(classinfo);
	} else {
	    tableName = DatabaseInfoUtilities.getValidTableName(classinfo);
	}

	String parentKeyColumnName = DatabaseInfoUtilities.getValidColumnName(classinfo, "parentReference.key.id");

	String seqColumnName = DatabaseInfoUtilities.getValidColumnName(classinfo, E3PSTask.TASK_SEQ);

	String projectKeyColumName = DatabaseInfoUtilities.getValidColumnName(classinfo, "projectReference.key.id");

	Connection con = null;
	PreparedStatement st = null;
	ResultSet rs = null;

	try {
	    con = DBCPManager.getConnection("plm");

	    String sql = "UPDATE " + tableName + " set " + seqColumnName + "= " + seqColumnName + " + 1 where " + parentKeyColumnName + " = ? and " + seqColumnName + " >= ? and "
		    + projectKeyColumName + " = ?";
	    // Kogger.debug(getClass(), "sql=" + sql);
	    st = con.prepareStatement(sql);
	    long parentTaskId = 0;

	    st.setLong(1, parentTaskId);
	    st.setInt(2, seq);
	    st.setLong(3, project.getPersistInfo().getObjectIdentifier().getId());

	    count = st.executeUpdate();

	} catch (Exception ex) {
	    Kogger.error(ProjectTaskHelper.class, ex);
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		}
		if (st != null) {
		    st.close();
		}
		if (con != null) {
		    con.close();
		}
	    } catch (SQLException e) {
		Kogger.error(ProjectTaskHelper.class, e);
	    }
	}

	return count;
    }

    public static int updateSeq(E3PSTask task, int seq) throws Exception {

	int count = 0;
	ClassInfo classinfo = WTIntrospector.getClassInfo(E3PSTask.class);
	String tableName = "";
	if (DatabaseInfoUtilities.isAutoNavigate(classinfo)) {
	    tableName = DatabaseInfoUtilities.getBaseTableName(classinfo);
	} else {
	    tableName = DatabaseInfoUtilities.getValidTableName(classinfo);
	}

	String parentKeyColumnName = DatabaseInfoUtilities.getValidColumnName(classinfo, "parentReference.key.id");

	String seqColumnName = DatabaseInfoUtilities.getValidColumnName(classinfo, E3PSTask.TASK_SEQ);

	String projectKeyColumName = DatabaseInfoUtilities.getValidColumnName(classinfo, "projectReference.key.id");

	Connection con = null;
	PreparedStatement st = null;
	ResultSet rs = null;

	try {
	    con = DBCPManager.getConnection("plm");

	    String sql = "UPDATE " + tableName + " set " + seqColumnName + "= " + seqColumnName + " + 1 where " + parentKeyColumnName + " = ? and " + seqColumnName + " >= ? and "
		    + projectKeyColumName + " = ?";
	    // Kogger.debug(getClass(), "sql=" + sql);
	    st = con.prepareStatement(sql);
	    long parentTaskId = task.getPersistInfo().getObjectIdentifier().getId();

	    st.setLong(1, parentTaskId);
	    st.setInt(2, seq);
	    st.setLong(3, task.getProject().getPersistInfo().getObjectIdentifier().getId());

	    count = st.executeUpdate();

	} catch (Exception ex) {
	    Kogger.error(ProjectTaskHelper.class, ex);
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		}
		if (st != null) {
		    st.close();
		}
		if (con != null) {
		    con.close();
		}
	    } catch (SQLException e) {
		Kogger.error(ProjectTaskHelper.class, e);
	    }
	}

	return count;
    }

    public static boolean isChildName(TemplateProject project, String taskName) throws Exception {
	// if (!wt.method.RemoteMethodServer.ServerFlag) {
	// Class argTypes[] = new Class[]{TemplateProject.class, String.class};
	// Object args[] = new Object[]{project, taskName};
	// try {
	// Object obj =RemoteMethodServer.getDefault().invoke("isChildName", ProjectTaskHelper.class.getName() , null, argTypes, args);
	// return ((Boolean)obj).booleanValue();
	//
	// } catch (RemoteException e) {
	// Kogger.error(getClass(), e);
	// throw new WTException(e);
	// } catch (InvocationTargetException e) {
	// Kogger.error(getClass(), e);
	// throw new WTException(e);
	// }
	// }
	//
	// String tableName = "";
	//
	// Class taskClass = TemplateTask.class;
	//
	// if(project instanceof E3PSProject){
	// taskClass = E3PSTask.class;
	// }
	//
	//
	// ClassInfo classinfo = WTIntrospector.getClassInfo(taskClass);
	// if (DatabaseInfoUtilities.isAutoNavigate(classinfo)) {
	// tableName = DatabaseInfoUtilities.getBaseTableName(classinfo);
	// } else {
	// tableName = DatabaseInfoUtilities.getValidTableName(classinfo);
	// }
	//
	// String parentKeyColumnName = DatabaseInfoUtilities.getValidColumnName(
	// classinfo, "parentReference.key.id");
	//
	// String taskOIDColumnName = DatabaseInfoUtilities.getValidColumnName(
	// classinfo, "thePersistInfo.theObjectIdentifier.id");
	//
	// String taskNameColumnName = DatabaseInfoUtilities.getValidColumnName(
	// classinfo, TemplateTask.TASK_NAME);
	//
	// String projectKeyColumName = DatabaseInfoUtilities.getValidColumnName(
	// classinfo, "projectReference.key.id");
	//
	// Connection con = null;
	// PreparedStatement st = null;
	// ResultSet rs = null;
	//
	// int childLength = 0;
	// try {
	// con = DBCPManager.getConnection("plm");
	//
	// String sql = "select count(" + taskOIDColumnName + ") from " + tableName + " where " + parentKeyColumnName
	// + " = ? and " + taskNameColumnName + "=? and " + projectKeyColumName +"=?";
	//
	// st = con.prepareStatement(sql);
	// long parentTaskId = 0;
	//
	// st.setLong(1, parentTaskId);
	// st.setString(2, taskName);
	// st.setLong(3, project.getPersistInfo().getObjectIdentifier().getId());
	//
	// rs = st.executeQuery();
	//
	// if (rs.next()) {
	// childLength= rs.getInt(1);
	// }else{
	// throw new Exception("error..");
	// }
	//
	// } catch (Exception e) {
	// Kogger.error(getClass(), e);
	// throw e;
	// } finally {
	// try {
	// if(rs != null){
	// rs.close();
	// }
	// if(st != null){
	// st.close();
	// }
	// if (con != null) con.close();
	// } catch (SQLException e) {
	// Kogger.error(getClass(), e);
	// }
	// }
	// return childLength > 0;
	int childLength = 0;
	String tableName = "";
	Class taskClass = TemplateTask.class;
	if (project instanceof E3PSProject) {
	    taskClass = E3PSTask.class;
	}

	ClassInfo classinfo = WTIntrospector.getClassInfo(taskClass);
	String taskOIDColumnName = DatabaseInfoUtilities.getValidColumnName(classinfo, "thePersistInfo.theObjectIdentifier.id");

	QuerySpec spec = new QuerySpec();
	spec.setAdvancedQueryEnabled(true);
	spec.setDescendantQuery(false);
	// spec.addClassList(taskClass, false);
	spec.appendFrom(new E3PSClassTableExpression(taskClass));
	KeywordExpression ke = new KeywordExpression("count(" + taskOIDColumnName + ")");
	ke.setColumnAlias("task_count");
	spec.appendSelect(ke, new int[] { 0 }, false);

	spec.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.PARENT_REFERENCE + ".key.id", "=", 0L), new int[] { 0 });
	spec.appendAnd();
	long projectId = project.getPersistInfo().getObjectIdentifier().getId();
	spec.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.PROJECT_REFERENCE + ".key.id", "=", projectId), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.TASK_NAME, "=", taskName), new int[] { 0 });

	QueryResult rrrr = PersistenceHelper.manager.find(spec);

	if (rrrr.hasMoreElements()) {
	    Object o[] = (Object[]) rrrr.nextElement();
	    BigDecimal number = (BigDecimal) o[0];
	    childLength = number.intValue();
	}

	return childLength > 0;
    }

    public static boolean isChildName(TemplateTask parentTask, String taskName) throws Exception {
	// if (!wt.method.RemoteMethodServer.ServerFlag) {
	// Class argTypes[] = new Class[]{TemplateTask.class, String.class};
	// Object args[] = new Object[]{parentTask, taskName};
	// try {
	// Object obj =RemoteMethodServer.getDefault().invoke("isChildName", ProjectTaskHelper.class.getName() , null, argTypes, args);
	// return ((Boolean)obj).booleanValue();
	//
	// } catch (RemoteException e) {
	// Kogger.error(getClass(), e);
	// throw new WTException(e);
	// } catch (InvocationTargetException e) {
	// Kogger.error(getClass(), e);
	// throw new WTException(e);
	// }
	// }
	//
	// String tableName = "";
	//
	// Class taskClass = parentTask.getClass();
	// ClassInfo classinfo = WTIntrospector.getClassInfo(taskClass);
	// if (DatabaseInfoUtilities.isAutoNavigate(classinfo)) {
	// tableName = DatabaseInfoUtilities.getBaseTableName(classinfo);
	// } else {
	// tableName = DatabaseInfoUtilities.getValidTableName(classinfo);
	// }
	//
	// String parentKeyColumnName = DatabaseInfoUtilities.getValidColumnName(
	// classinfo, "parentReference.key.id");
	//
	// String taskOIDColumnName = DatabaseInfoUtilities.getValidColumnName(
	// classinfo, "thePersistInfo.theObjectIdentifier.id");
	//
	// String taskNameColumnName = DatabaseInfoUtilities.getValidColumnName(
	// classinfo, TemplateTask.TASK_NAME);
	//
	// //MethodContext methodcontext = null;
	// //WTConnection wtconnection = null;
	// Connection con = null;
	// PreparedStatement st = null;
	// ResultSet rs = null;
	//
	// int childLength = 0;
	// try {
	//
	// //methodcontext = MethodContext.getContext();
	// //wtconnection = (WTConnection) methodcontext.getConnection();
	//
	// con = DBCPManager.getConnection("plm");
	//
	// String sql = "select count(" + taskOIDColumnName + ") from " + tableName + " where " + parentKeyColumnName
	// + " = ? and " + taskNameColumnName + "=?";
	//
	// st = con.prepareStatement(sql);
	// long parentTaskId = parentTask.getPersistInfo().getObjectIdentifier().getId();
	//
	// st.setLong(1, parentTaskId);
	// st.setString(2, taskName);
	//
	// rs = st.executeQuery();
	//
	// if (rs.next()) {
	// childLength= rs.getInt(1);
	// }else{
	// throw new Exception("error..");
	// }
	//
	// } catch (Exception e) {
	// Kogger.error(getClass(), e);
	// throw e;
	// } finally {
	// try {
	// if(rs != null){
	// rs.close();
	// }
	// if(st != null){
	// st.close();
	// }
	// if (con != null) con.close();
	// } catch (SQLException e) {
	// Kogger.error(getClass(), e);
	// }
	// }
	// return childLength > 0;
	int childLength = 0;

	String tableName = "";
	Class taskClass = parentTask.getClass();
	ClassInfo classinfo = WTIntrospector.getClassInfo(taskClass);
	String taskOIDColumnName = DatabaseInfoUtilities.getValidColumnName(classinfo, "thePersistInfo.theObjectIdentifier.id");

	QuerySpec spec = new QuerySpec();
	spec.setAdvancedQueryEnabled(true);
	spec.setDescendantQuery(false);
	// spec.addClassList(taskClass, false);
	spec.appendFrom(new E3PSClassTableExpression(taskClass));
	KeywordExpression ke = new KeywordExpression("count(" + taskOIDColumnName + ")");
	ke.setColumnAlias("task_count");
	spec.appendSelect(ke, new int[] { 0 }, false);

	long parentTaskId = parentTask.getPersistInfo().getObjectIdentifier().getId();
	spec.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.PARENT_REFERENCE + ".key.id", "=", parentTaskId), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.TASK_NAME, "=", taskName), new int[] { 0 });

	QueryResult rrrr = PersistenceHelper.manager.find(spec);

	if (rrrr.hasMoreElements()) {
	    Object o[] = (Object[]) rrrr.nextElement();
	    BigDecimal number = (BigDecimal) o[0];
	    childLength = number.intValue();
	}

	return childLength > 0;
    }

    public static void sendSap(E3PSTask task) throws Exception {

	ProjectTreeNode node = (ProjectTreeNode) ProjectScheduleHelper.manager.getRoot(task, false, true);

	E3PSTask oneLevelTask = getOneLevelTask(node);

	if (oneLevelTask != null) {
	    int state = ProjectStateFlag.TASK_STATE_COMPLETE;
	    if (oneLevelTask.getTaskState() == state) {
		try {
		    // if(!PJTInfoERPInterface.sendProjectInfoToSap((E3PSProject)task.getProject(), System.out, false)){
		    // Kogger.debug(getClass(), "updateCompletionFromOutput error...");
		    // }
		} catch (Exception ex) {
		    Kogger.error(ProjectTaskHelper.class, ex);
		}
	    }
	}
    }

    public static E3PSTask getOneLevelTask(ProjectTreeNode node) {
	ProjectTreeNodeData pd = (ProjectTreeNodeData) node.getUserObject();
	E3PSTask task = (E3PSTask) pd.getData();
	if (task.getParent() == null) {
	    return task;
	} else {
	    if (node.getChildCount() > 0) {
		return getOneLevelTask((ProjectTreeNode) node.getChildAt(0));
	    }
	}

	return null;

    }

    public static boolean isEditTask(E3PSTask task) throws Exception {
	ProjectTreeNode node = (ProjectTreeNode) ProjectScheduleHelper.manager.getRoot(task, false, true);

	return isEditTask(node);
    }

    public static boolean isEditTask(ProjectTreeNode node) throws Exception {
	ProjectTreeNodeData pd = (ProjectTreeNodeData) node.getUserObject();
	boolean isEdit = isTaskMaster((E3PSTask) pd.getData());

	if (isEdit) {
	    return true;
	} else {
	    if (node.getChildCount() > 0) {
		return isEditTask((ProjectTreeNode) node.getChildAt(0));
	    }
	}
	return false;
    }

    public static boolean isTaskMaster(E3PSTask task) throws Exception {
	long userId = SessionHelper.manager.getPrincipal().getPersistInfo().getObjectIdentifier().getId();

	long taskId = task.getPersistInfo().getObjectIdentifier().getId();

	QuerySpec spec = new QuerySpec();
	int idx1 = spec.appendClassList(ProjectMemberLink.class, true);
	int idx2 = spec.appendClassList(TaskMemberLink.class, false);

	ClassAttribute ca0 = new ClassAttribute(ProjectMemberLink.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute ca1 = new ClassAttribute(TaskMemberLink.class, "roleBObjectRef.key.id");

	SearchCondition sc = new SearchCondition(ca0, "=", ca1);
	sc.setFromIndicies(new int[] { idx1, idx2 }, 0);
	sc.setOuterJoin(0);
	spec.appendWhere(sc, new int[] { idx1, idx2 });

	spec.appendAnd();

	spec.appendWhere(new SearchCondition(ProjectMemberLink.class, "roleBObjectRef.key.id", "=", userId), new int[] { idx1 });

	spec.appendAnd();

	spec.appendWhere(new SearchCondition(TaskMemberLink.class, "roleAObjectRef.key.id", "=", taskId), new int[] { idx2 });

	spec.appendAnd();

	spec.appendWhere(new SearchCondition(TaskMemberLink.class, TaskMemberLink.TASK_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.PL), idx2);

	QueryResult rs = PersistenceHelper.manager.find(spec);

	return rs.size() > 0;
    }

    public static boolean isChild(TemplateTask parentTask) throws Exception {
	// if (!wt.method.RemoteMethodServer.ServerFlag) {
	// Class argTypes[] = new Class[]{TemplateTask.class};
	// Object args[] = new Object[]{parentTask};
	// try {
	// Object obj =RemoteMethodServer.getDefault().invoke("isChild", ProjectTaskHelper.class.getName() , null, argTypes, args);
	// return ((Boolean)obj).booleanValue();
	//
	// } catch (RemoteException e) {
	// Kogger.error(getClass(), e);
	// throw new WTException(e);
	// } catch (InvocationTargetException e) {
	// Kogger.error(getClass(), e);
	// throw new WTException(e);
	// }
	// }

	// String tableName = "";
	// Class taskClass = parentTask.getClass();
	// ClassInfo classinfo = WTIntrospector.getClassInfo(taskClass);
	// if (DatabaseInfoUtilities.isAutoNavigate(classinfo)) {
	// tableName = DatabaseInfoUtilities.getBaseTableName(classinfo);
	// } else {
	// tableName = DatabaseInfoUtilities.getValidTableName(classinfo);
	// }
	//
	// String parentKeyColumnName = DatabaseInfoUtilities.getValidColumnName(
	// classinfo, "parentReference.key.id");
	//
	// String taskOIDColumnName = DatabaseInfoUtilities.getValidColumnName(
	// classinfo, "thePersistInfo.theObjectIdentifier.id");
	//
	// //MethodContext methodcontext = null;
	// //WTConnection wtconnection = null;
	//
	// Connection con = null;
	// PreparedStatement st = null;
	// ResultSet rs = null;
	//
	// int childLength = 0;
	// try {
	// //methodcontext = MethodContext.getContext();
	// //wtconnection = (WTConnection) methodcontext.getConnection();
	//
	//
	// con = DBCPManager.getConnection("plm");
	//
	// String sql = "select count(" + taskOIDColumnName + ") from " + tableName + " where " + parentKeyColumnName + " = ?";
	// st = con.prepareStatement(sql);
	// long parentTaskId = parentTask.getPersistInfo().getObjectIdentifier().getId();
	// st.setLong(1, parentTaskId);
	//
	// rs = st.executeQuery();
	//
	// if (rs.next()) {
	// childLength= rs.getInt(1);
	// }else{
	// throw new Exception("error..");
	// }
	// //write(root);
	//
	// } catch (Exception e) {
	// Kogger.error(getClass(), e);
	// throw e;
	// } finally {
	// try {
	// if(rs != null){
	// rs.close();
	// }
	// if(st != null){
	// st.close();
	// }
	// if (con != null) con.close();
	// } catch (SQLException e) {
	// Kogger.error(getClass(), e);
	// }
	// }
	// return childLength > 0;

	int childLength = 0;

	String tableName = "";
	Class taskClass = parentTask.getClass();
	ClassInfo classinfo = WTIntrospector.getClassInfo(taskClass);
	String taskOIDColumnName = DatabaseInfoUtilities.getValidColumnName(classinfo, "thePersistInfo.theObjectIdentifier.id");

	QuerySpec spec = new QuerySpec();
	spec.setAdvancedQueryEnabled(true);
	spec.setDescendantQuery(false);
	spec.addClassList(taskClass, false);
	KeywordExpression ke = new KeywordExpression("count(" + taskOIDColumnName + ")");
	ke.setColumnAlias("task_count");
	spec.appendSelect(ke, new int[] { 0 }, false);

	long parentTaskId = parentTask.getPersistInfo().getObjectIdentifier().getId();
	spec.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.PARENT_REFERENCE + ".key.id", "=", parentTaskId), new int[] { 0 });

	QueryResult rrrr = PersistenceHelper.manager.find(spec);

	if (rrrr.hasMoreElements()) {
	    Object o[] = (Object[]) rrrr.nextElement();
	    BigDecimal number = (BigDecimal) o[0];
	    childLength = number.intValue();
	}

	return childLength > 0;
    }

    public static TemplateTask getUpMaxTask(TemplateTask task) throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { TemplateTask.class };
	    Object args[] = new Object[] { task };
	    try {
		Object obj = RemoteMethodServer.getDefault().invoke("getUpMaxTask", ProjectTaskHelper.class.getName(), null, argTypes, args);
		return (TemplateTask) obj;

	    } catch (RemoteException e) {
		Kogger.error(ProjectTaskHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectTaskHelper.class, e);
		throw new WTException(e);
	    }
	}

	// SubQs
	TemplateTask reTask = null;
	TemplateProject project = task.getProject();
	int selectSeq = task.getTaskSeq();

	QuerySpec Subqs = new QuerySpec();
	Subqs.setDescendantQuery(false);
	Class taskClass = TemplateTask.class;
	if (project instanceof E3PSProject) {
	    taskClass = E3PSTask.class;
	}

	int idx_up = Subqs.addClassList(taskClass, false);
	long oidValue = CommonUtil.getOIDLongValue(project);
	if (Subqs.getConditionCount() > 0)
	    Subqs.appendAnd();
	Subqs.appendSelect(new KeywordExpression("NVL(Max(taskseq), -1)"), new int[] { idx_up }, false);
	Subqs.appendWhere(new SearchCondition(taskClass, E3PSTask.TASK_SEQ, "<", selectSeq), new int[] { idx_up });

	if (task.getParent() == null) {
	    SearchUtil.appendEQUAL(Subqs, taskClass, "parentReference.key.id", (long) 0, idx_up);

	} else {

	    long parentId = task.getParent().getPersistInfo().getObjectIdentifier().getId();
	    SearchUtil.appendEQUAL(Subqs, taskClass, "parentReference.key.id", parentId, idx_up);

	}

	SearchUtil.appendEQUAL(Subqs, taskClass, "projectReference.key.id", oidValue, idx_up);

	// Main
	QuerySpec spec = new QuerySpec();
	spec.setDescendantQuery(false);
	spec.setAdvancedQueryEnabled(true);

	int taskClassPos = spec.addClassList(taskClass, true);

	if (task.getParent() == null) {
	    SearchUtil.appendEQUAL(spec, taskClass, "parentReference.key.id", (long) 0, taskClassPos);

	} else {

	    long parentId = task.getParent().getPersistInfo().getObjectIdentifier().getId();
	    SearchUtil.appendEQUAL(spec, taskClass, "parentReference.key.id", parentId, taskClassPos);

	}

	SearchUtil.appendEQUAL(spec, taskClass, "projectReference.key.id", oidValue, taskClassPos);

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}

	ClassAttribute cAttr = new ClassAttribute(taskClass, E3PSTask.TASK_SEQ);
	spec.appendWhere(new SearchCondition(cAttr, SearchCondition.EQUAL, new SubSelectExpression(Subqs)), new int[] { taskClassPos });

	QueryResult rs = PersistenceHelper.manager.find(spec);

	if (rs.hasMoreElements()) {
	    Object o[] = (Object[]) rs.nextElement();
	    reTask = (TemplateTask) o[0];
	}

	return reTask;
    }

    public static TemplateTask getDownMinTask(TemplateTask task) throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { TemplateTask.class };
	    Object args[] = new Object[] { task };
	    try {
		Object obj = RemoteMethodServer.getDefault().invoke("getDownMinTask", ProjectTaskHelper.class.getName(), null, argTypes, args);
		return (TemplateTask) obj;

	    } catch (RemoteException e) {
		Kogger.error(ProjectTaskHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectTaskHelper.class, e);
		throw new WTException(e);
	    }
	}

	// SubQs
	TemplateTask reTask = null;
	TemplateProject project = task.getProject();
	int selectSeq = task.getTaskSeq();

	QuerySpec Subqs = new QuerySpec();
	Subqs.setDescendantQuery(false);
	Class taskClass = TemplateTask.class;
	if (project instanceof E3PSProject) {
	    taskClass = E3PSTask.class;
	}

	int idx_up = Subqs.addClassList(taskClass, false);
	long oidValue = CommonUtil.getOIDLongValue(project);
	if (Subqs.getConditionCount() > 0) {
	    Subqs.appendAnd();
	}

	Subqs.appendSelect(new KeywordExpression("NVL(Min(taskseq), -1)"), new int[] { idx_up }, false);
	Subqs.appendWhere(new SearchCondition(taskClass, E3PSTask.TASK_SEQ, ">", selectSeq), new int[] { idx_up });

	if (task.getParent() == null) {
	    SearchUtil.appendEQUAL(Subqs, taskClass, "parentReference.key.id", (long) 0, idx_up);
	} else {
	    long parentId = task.getParent().getPersistInfo().getObjectIdentifier().getId();
	    SearchUtil.appendEQUAL(Subqs, taskClass, "parentReference.key.id", parentId, idx_up);
	}

	SearchUtil.appendEQUAL(Subqs, taskClass, "projectReference.key.id", oidValue, idx_up);
	// Main
	QuerySpec spec = new QuerySpec();
	spec.setDescendantQuery(false);
	spec.setAdvancedQueryEnabled(true);

	int taskClassPos = spec.addClassList(taskClass, true);

	if (task.getParent() == null) {
	    SearchUtil.appendEQUAL(spec, taskClass, "parentReference.key.id", (long) 0, taskClassPos);

	} else {

	    long parentId = task.getParent().getPersistInfo().getObjectIdentifier().getId();
	    SearchUtil.appendEQUAL(spec, taskClass, "parentReference.key.id", parentId, taskClassPos);

	}

	SearchUtil.appendEQUAL(spec, taskClass, "projectReference.key.id", oidValue, taskClassPos);

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}

	ClassAttribute cAttr = new ClassAttribute(taskClass, E3PSTask.TASK_SEQ);
	spec.appendWhere(new SearchCondition(cAttr, SearchCondition.EQUAL, new SubSelectExpression(Subqs)), new int[] { taskClassPos });

	QueryResult rs = PersistenceHelper.manager.find(spec);

	if (rs.hasMoreElements()) {
	    Object o[] = (Object[]) rs.nextElement();
	    reTask = (TemplateTask) o[0];
	}

	return reTask;
    }

    public static int getMaxSeq(TemplateTask parent, TemplateProject project) throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { TemplateTask.class, TemplateProject.class };
	    Object args[] = new Object[] { parent, project };
	    try {
		Object obj = RemoteMethodServer.getDefault().invoke("getMaxSeq", ProjectTaskHelper.class.getName(), null, argTypes, args);
		return ((Integer) obj).intValue();

	    } catch (RemoteException e) {
		Kogger.error(ProjectTaskHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectTaskHelper.class, e);
		throw new WTException(e);
	    }
	}

	int result = 0;
	Class taskClass = TemplateTask.class;
	if (project instanceof E3PSProject) {
	    taskClass = E3PSTask.class;
	}

	ClassInfo classinfo = WTIntrospector.getClassInfo(taskClass);

	String task_seqColumnName = DatabaseInfoUtilities.getValidColumnName(classinfo, TemplateTask.TASK_SEQ);

	QuerySpec spec = new QuerySpec();
	spec.setAdvancedQueryEnabled(true);
	spec.setDescendantQuery(false);
	spec.addClassList(taskClass, false);
	KeywordExpression ke = new KeywordExpression("NVL(Max(" + task_seqColumnName + "), 0) ");
	ke.setColumnAlias("task_seq");
	spec.appendSelect(ke, new int[] { 0 }, false);

	long id = 0;

	if (parent != null) {
	    id = parent.getPersistInfo().getObjectIdentifier().getId();
	}

	long projectId = project.getPersistInfo().getObjectIdentifier().getId();

	spec.appendWhere(new SearchCondition(taskClass, E3PSTask.PROJECT_REFERENCE + ".key.id", "=", projectId), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(taskClass, E3PSTask.PARENT_REFERENCE + ".key.id", "=", id), new int[] { 0 });

	// Kogger.debug(getClass(), "spec = " + spec);
	QueryResult rrrr = PersistenceHelper.manager.find(spec);

	if (rrrr.hasMoreElements()) {
	    Object o[] = (Object[]) rrrr.nextElement();
	    BigDecimal number = (BigDecimal) o[0];
	    result = number.intValue() + 1;

	}

	// Kogger.debug(getClass(), "result = " + result);

	return result;
    }

    // public static boolean isERPTask(TemplateTask task)throws Exception{
    //
    // QueryResult rs = PersistenceHelper.manager.navigate(task, TaskWBSItemLink.WBS_ROLE, TaskWBSItemLink.class);
    //
    // if(rs.hasMoreElements()){
    // WBSItem wbsItem = (WBSItem)rs.nextElement();
    // return wbsItem.isIsERP();
    // }else{
    // return false;
    // }
    // }

    public static TemplateTask getTask(TemplateProject project, String name) throws Exception {

	long pId = project.getPersistInfo().getObjectIdentifier().getId();

	QuerySpec qs = new QuerySpec();
	int idx_task = qs.addClassList(TemplateTask.class, true);

	// ClassAttribute ca0 = new ClassAttribute(TemplateTask.class, "thePersistInfo.theObjectIdentifier.id");

	// qs.appendAnd();
	// ca0 = new ClassAttribute(TaskWBSItemLink.class, "roleBObjectRef.key.id");
	// ca1 = new ClassAttribute(WBSItem.class, "thePersistInfo.theObjectIdentifier.id");
	// sc = new SearchCondition(ca0, "=", ca1);
	// sc.setFromIndicies(new int[]{idx_link, idx_wbs}, 0);
	// sc.setOuterJoin(0);
	// qs.appendWhere(sc, new int[]{idx_link, idx_wbs});

	qs.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.PARENT_REFERENCE + ".key.id", "=", 0L), new int[] { idx_task });

	qs.appendAnd();

	qs.appendWhere(new SearchCondition(TemplateTask.class, "projectReference.key.id", "=", pId), new int[] { idx_task });

	qs.appendAnd();

	qs.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.TASK_NAME, "=", name), new int[] { idx_task });

	QueryResult rs = PersistenceHelper.manager.find(qs);

	TemplateTask task = null;
	if (rs.hasMoreElements()) {
	    Object obj[] = (Object[]) rs.nextElement();
	    task = (TemplateTask) obj[0];
	}
	return task;
    }

    public static List<E3PSTask> getAllTask(TemplateProject project) throws Exception {
	List<E3PSTask> taskList = new ArrayList<E3PSTask>();
	long pId = project.getPersistInfo().getObjectIdentifier().getId();

	QuerySpec qs = new QuerySpec();
	int idx_task = qs.addClassList(E3PSTask.class, true);
	qs.appendWhere(new SearchCondition(E3PSTask.class, "projectReference.key.id", "=", pId), new int[] { idx_task });
	Kogger.debug(ProjectTaskHelper.class, "rs >> " + qs);
	QueryResult rs = PersistenceHelper.manager.find(qs);
	if (rs.hasMoreElements()) {
	    Object obj[] = (Object[]) rs.nextElement();
	    taskList.add((E3PSTask) obj[0]);
	}
	return taskList;
    }

    public static TemplateTask getTask(TemplateTask parentTask, String name) throws Exception {

	long pId = parentTask.getProject().getPersistInfo().getObjectIdentifier().getId();

	QuerySpec qs = new QuerySpec();
	int idx_task = qs.addClassList(TemplateTask.class, true);

	// ClassAttribute ca0 = new ClassAttribute(TemplateTask.class, "thePersistInfo.theObjectIdentifier.id");

	// qs.appendAnd();
	// ca0 = new ClassAttribute(TaskWBSItemLink.class, "roleBObjectRef.key.id");
	// ca1 = new ClassAttribute(WBSItem.class, "thePersistInfo.theObjectIdentifier.id");
	// sc = new SearchCondition(ca0, "=", ca1);
	// sc.setFromIndicies(new int[]{idx_link, idx_wbs}, 0);
	// sc.setOuterJoin(0);
	// qs.appendWhere(sc, new int[]{idx_link, idx_wbs});

	qs.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.PARENT_REFERENCE + ".key.id", "=", parentTask.getPersistInfo().getObjectIdentifier().getId()), new int[] { idx_task });

	qs.appendAnd();

	qs.appendWhere(new SearchCondition(TemplateTask.class, "projectReference.key.id", "=", pId), new int[] { idx_task });

	qs.appendAnd();

	qs.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.TASK_NAME, "=", name), new int[] { idx_task });

	QueryResult rs = PersistenceHelper.manager.find(qs);

	TemplateTask task = null;
	if (rs.hasMoreElements()) {
	    Object obj[] = (Object[]) rs.nextElement();
	    task = (TemplateTask) obj[0];
	}
	return task;
    }

    public boolean isLast(TemplateTask task) {
	QueryResult qr = getChild(task);
	if (qr == null || qr.size() == 0) {
	    return true;
	}
	return false;
    }

    public boolean canDelete(TemplateTask task) {
	QueryResult qr = getChild(task);
	if (qr != null && qr.hasMoreElements()) {
	    return false;
	}

	qr = ProjectOutputHelper.manager.getTaskOutput(task);
	if (qr != null && qr.hasMoreElements()) {
	    return false;
	}

	qr = TaskUserHelper.manager.getTaskUser(task);
	if (qr != null && qr.hasMoreElements()) {
	    return false;
	}
	return true;
    }

    public boolean canUpdate(TemplateTask task) {
	QueryResult qr = getChild(task);
	if (qr != null && qr.hasMoreElements()) {
	    return false;
	}
	return true;
    }

    public Persistable getParent(TemplateTask _task) {
	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(_task, "parent", ParentChildLink.class);
	    if (qr == null || qr.size() < 1) {
		qr = PersistenceHelper.manager.navigate(_task, "project", TemplateProjectTemplateTaskLink.class);
	    }
	    if (qr != null && qr.hasMoreElements()) {
		return (Persistable) qr.nextElement();
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getChildList(TemplateProject _project) {
	try {
	    QueryResult result = PersistenceHelper.manager.navigate(_project, "task", TemplateProjectTemplateTaskLink.class);
	    if (result != null) {
		return result;
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getChildList(TemplateTask _task) {
	try {
	    QueryResult result = PersistenceHelper.manager.navigate(_task, "child", ParentChildLink.class);
	    if (result != null) {
		return result;
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getChild(TemplateProject project) {
	try {

	    QuerySpec qs = new QuerySpec();
	    int idx_task = qs.appendClassList(TemplateTask.class, true);
	    // int idx_wbs = qs.appendClassList(WBSItem.class, true);
	    // int idx_link = qs.appendClassList(TaskWBSItemLink.class, true);

	    SearchCondition sc = new SearchCondition(TemplateTask.class, "projectReference.key.id", SearchCondition.EQUAL, project.getPersistInfo().getObjectIdentifier().getId());
	    qs.appendWhere(sc, new int[] { idx_task });

	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    sc = new SearchCondition(TemplateTask.class, "parentReference.key.id", SearchCondition.EQUAL, (long) 0);
	    qs.appendWhere(sc, new int[] { idx_task });

	    // if(qs.getConditionCount() > 0) {
	    // qs.appendAnd();
	    // }

	    // ClassAttribute ca0 = new ClassAttribute(TemplateTask.class, "thePersistInfo.theObjectIdentifier.id");
	    // ClassAttribute ca1 = new ClassAttribute(TaskWBSItemLink.class, "roleAObjectRef.key.id");

	    // sc = new SearchCondition(ca0, "=", ca1);
	    // sc.setFromIndicies(new int[]{idx_task, idx_link}, 0);
	    // //sc.setOuterJoin(sc.RIGHT_OUTER_JOIN);
	    // qs.appendWhere(sc, new int[]{idx_task, idx_link});

	    // Kogger.debug(getClass(), "qs(getChild)>>>>>><<<<< "+qs);

	    // qs.appendAnd();
	    // ca0 = new ClassAttribute(TaskWBSItemLink.class, "roleBObjectRef.key.id");
	    // ca1 = new ClassAttribute(WBSItem.class, "thePersistInfo.theObjectIdentifier.id");
	    // sc = new SearchCondition(ca0, "=", ca1);
	    // sc.setFromIndicies(new int[]{idx_link, idx_wbs}, 0);
	    // sc.setOuterJoin(0);
	    // qs.appendWhere(sc, new int[]{idx_link, idx_wbs});

	    SearchUtil.setOrderBy(qs, TemplateTask.class, idx_task, TemplateTask.TASK_SEQ, "taskSeq", false);
	    // Kogger.debug(getClass(), "Project Spec:"+qs);
	    QueryResult result = PersistenceHelper.manager.find(qs);
	    return result;

	    /*
	     * QuerySpec spec = new QuerySpec();
	     * 
	     * Class taskClass = TemplateTask.class; Class wbsItemClass = TemplateTask.class; int taskClassPos = spec.addClassList(taskClass, true); int wbsItemPos = spec.addClassList(wbsItemClass,
	     * true);
	     * 
	     * SearchUtil.appendEQUAL(spec, taskClass, "parentReference.key.id", (long)0, taskClassPos);
	     * 
	     * long oidValue = CommonUtil.getOIDLongValue(project); SearchUtil.appendEQUAL(spec, taskClass, "projectReference.key.id", oidValue, taskClassPos); SearchUtil.setOrderBy(spec, taskClass,
	     * taskClassPos, TemplateTask.TASK_SEQ , "taskSeq", false); QueryResult result = PersistenceHelper.manager.find(spec); if(result != null) return result;
	     */
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getChildWithTargetItem(TemplateTask task) throws Exception {

	QuerySpec spec = new QuerySpec();

	Class taskClass = task.getClass();
	int taskClassPos = spec.addClassList(taskClass, true);

	// int idx_link = spec.appendClassList(TaskWBSItemLink.class, true);

	// int targetClassPos = spec.addClassList(TargetItem.class, true);

	int schduleClassIndex = spec.addClassList(ScheduleData.class, false);

	long oidValue = CommonUtil.getOIDLongValue(task);
	SearchUtil.appendEQUAL(spec, taskClass, "parentReference.key.id", oidValue, taskClassPos);

	ClassAttribute ca0 = null;
	ClassAttribute ca1 = null;
	SearchCondition sc = null;

	spec.appendAnd();
	ca0 = new ClassAttribute(TemplateTask.class, "taskSchedule.key.id");
	ca1 = new ClassAttribute(ScheduleData.class, "thePersistInfo.theObjectIdentifier.id");

	sc = new SearchCondition(ca0, "=", ca1);
	sc.setOuterJoin(sc.NO_OUTER_JOIN);
	spec.appendWhere(sc, new int[] { taskClassPos, schduleClassIndex });

	// SearchUtil.setOrderBy(spec, ScheduleData.class, schduleClassIndex, ScheduleData.PLAN_START_DATE , "startPaln", false);

	// SearchUtil.setOrderBy(spec, ScheduleData.class, schduleClassIndex, ScheduleData.PLAN_END_DATE , "endPlan", false);

	SearchUtil.setOrderBy(spec, taskClass, taskClassPos, TemplateTask.TASK_SEQ, "taskSeq", false);

	// Kogger.debug(getClass(), "Task Spec:"+spec);
	QueryResult result = PersistenceHelper.manager.find(spec);
	return result;
    }

    public QueryResult getChildWithWBSItemLink(TemplateTask task) {
	try {
	    QuerySpec spec = new QuerySpec();

	    Class taskClass = TemplateTask.class;
	    int taskClassPos = spec.addClassList(taskClass, true);
	    // int idx_link = spec.appendClassList(TaskWBSItemLink.class, true);

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }

	    ClassAttribute ca0 = new ClassAttribute(TemplateTask.class, "thePersistInfo.theObjectIdentifier.id");
	    // ClassAttribute ca1 = new ClassAttribute(TaskWBSItemLink.class, "roleAObjectRef.key.id");
	    //
	    // SearchCondition sc = new SearchCondition(ca0, "=", ca1);
	    // sc.setFromIndicies(new int[]{taskClassPos, idx_link}, 0);
	    // sc.setOuterJoin(sc.RIGHT_OUTER_JOIN);
	    // spec.appendWhere(sc, new int[]{taskClassPos, idx_link});

	    long oidValue = CommonUtil.getOIDLongValue(task);
	    SearchUtil.appendEQUAL(spec, taskClass, "parentReference.key.id", oidValue, taskClassPos);
	    SearchUtil.setOrderBy(spec, taskClass, taskClassPos, TemplateTask.TASK_SEQ, "taskSeq", false);
	    QueryResult result = PersistenceHelper.manager.find(spec);
	    if (result != null)
		return result;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getChild(TemplateTask task) {
	try {
	    QuerySpec spec = new QuerySpec();

	    Class taskClass = TemplateTask.class;
	    int taskClassPos = spec.addClassList(taskClass, true);

	    long oidValue = CommonUtil.getOIDLongValue(task);
	    SearchUtil.appendEQUAL(spec, taskClass, "parentReference.key.id", oidValue, taskClassPos);
	    SearchUtil.setOrderBy(spec, taskClass, taskClassPos, TemplateTask.TASK_SEQ, "taskSeq", false);
	    QueryResult result = PersistenceHelper.manager.find(spec);
	    if (result != null)
		return result;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getChildTrue(TemplateProject project) {
	try {
	    QuerySpec spec = new QuerySpec();

	    Class taskClass = TemplateTask.class;
	    int taskClassPos = spec.addClassList(taskClass, true);

	    SearchUtil.appendEQUAL(spec, taskClass, "parentReference.key.id", (long) 0, taskClassPos);

	    long oidValue = CommonUtil.getOIDLongValue(project);
	    SearchUtil.appendEQUAL(spec, taskClass, "projectReference.key.id", oidValue, taskClassPos);
	    SearchUtil.setOrderBy(spec, taskClass, taskClassPos, TemplateTask.TASK_SEQ, "taskSeq", true);
	    QueryResult result = PersistenceHelper.manager.find(spec);
	    if (result != null)
		return result;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getChildUpProject(TemplateProject project, int seq) {
	try {
	    QuerySpec spec = new QuerySpec();

	    Class taskClass = TemplateTask.class;
	    int taskClassPos = spec.addClassList(taskClass, true);

	    SearchUtil.appendEQUAL(spec, taskClass, "parentReference.key.id", (long) 0, taskClassPos);

	    long oidValue = CommonUtil.getOIDLongValue(project);
	    SearchUtil.appendEQUAL(spec, taskClass, "projectReference.key.id", oidValue, taskClassPos);
	    SearchUtil.setOrderBy(spec, taskClass, taskClassPos, TemplateTask.TASK_SEQ, "taskSeq", true);

	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(taskClass, TemplateTask.TASK_SEQ, SearchCondition.EQUAL, seq - 1), new int[] { taskClassPos });

	    QueryResult result = PersistenceHelper.manager.find(spec);
	    if (result != null)
		return result;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getChildDownProject(TemplateProject project, int seq) {
	try {
	    QuerySpec spec = new QuerySpec();

	    Class taskClass = TemplateTask.class;
	    int taskClassPos = spec.addClassList(taskClass, true);

	    SearchUtil.appendEQUAL(spec, taskClass, "parentReference.key.id", (long) 0, taskClassPos);

	    long oidValue = CommonUtil.getOIDLongValue(project);
	    SearchUtil.appendEQUAL(spec, taskClass, "projectReference.key.id", oidValue, taskClassPos);
	    SearchUtil.setOrderBy(spec, taskClass, taskClassPos, TemplateTask.TASK_SEQ, "taskSeq", true);

	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(taskClass, TemplateTask.TASK_SEQ, SearchCondition.EQUAL, seq + 1), new int[] { taskClassPos });

	    QueryResult result = PersistenceHelper.manager.find(spec);
	    if (result != null)
		return result;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getChildTrue(TemplateTask task) {
	try {
	    QuerySpec spec = new QuerySpec();

	    Class taskClass = TemplateTask.class;
	    int taskClassPos = spec.addClassList(taskClass, true);

	    long oidValue = CommonUtil.getOIDLongValue(task);
	    SearchUtil.appendEQUAL(spec, taskClass, "parentReference.key.id", oidValue, taskClassPos);
	    SearchUtil.setOrderBy(spec, taskClass, taskClassPos, TemplateTask.TASK_SEQ, "taskSeq", true);
	    QueryResult result = PersistenceHelper.manager.find(spec);
	    if (result != null)
		return result;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getChildUpTask(TemplateTask task, int seq) {
	try {

	    QuerySpec spec = new QuerySpec();
	    Class taskClass = TemplateTask.class;
	    int taskClassPos = spec.addClassList(taskClass, true);

	    long oidValue = CommonUtil.getOIDLongValue(task);
	    SearchUtil.appendEQUAL(spec, taskClass, "parentReference.key.id", oidValue, taskClassPos);
	    SearchUtil.setOrderBy(spec, taskClass, taskClassPos, TemplateTask.TASK_SEQ, "taskSeq", true);
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(taskClass, TemplateTask.TASK_SEQ, SearchCondition.EQUAL, seq - 1), new int[] { taskClassPos });
	    QueryResult result = PersistenceHelper.manager.find(spec);
	    if (result != null)
		return result;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getChildDownTask(TemplateTask task, int seq) {
	try {
	    QuerySpec spec = new QuerySpec();

	    Class taskClass = TemplateTask.class;
	    int taskClassPos = spec.addClassList(taskClass, true);

	    long oidValue = CommonUtil.getOIDLongValue(task);
	    SearchUtil.appendEQUAL(spec, taskClass, "parentReference.key.id", oidValue, taskClassPos);
	    SearchUtil.setOrderBy(spec, taskClass, taskClassPos, TemplateTask.TASK_SEQ, "taskSeq", true);
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(taskClass, TemplateTask.TASK_SEQ, SearchCondition.EQUAL, seq + 1), new int[] { taskClassPos });
	    QueryResult result = PersistenceHelper.manager.find(spec);
	    if (result != null)
		return result;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    /**
     * task 보다 하위 Seq에 TASK 수정
     * 
     * @param task
     * @return
     */
    public QueryResult getLowerTask(TemplateTask task) {
	try {
	    QuerySpec spec = new QuerySpec();

	    Class taskClass = TemplateTask.class;
	    int taskClassPos = spec.addClassList(taskClass, true);

	    long oidValue = CommonUtil.getOIDLongValue(task.getProject());
	    SearchUtil.appendEQUAL(spec, taskClass, "projectReference.key.id", oidValue, taskClassPos);
	    if (task.getParent() == null) {
		SearchUtil.appendEQUAL(spec, taskClass, "parentReference.key.id", (long) 0, taskClassPos);
	    } else {
		SearchUtil.appendEQUAL(spec, taskClass, "parentReference.key.id", CommonUtil.getOIDLongValue(task.getParent()), taskClassPos);
	    }
	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(taskClass, TemplateTask.TASK_SEQ, SearchCondition.GREATER_THAN_OR_EQUAL, task.getTaskSeq()), taskClassPos);
	    SearchUtil.setOrderBy(spec, taskClass, taskClassPos, TemplateTask.TASK_SEQ, "taskSeq", false);
	    QueryResult result = PersistenceHelper.manager.find(spec);
	    if (result != null)
		return result;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public Vector getLevelOneTask(TemplateProject project) {
	QuerySpec spec = null;
	QueryResult result = null;
	Vector tmp = new Vector();

	try {
	    spec = new QuerySpec();

	    Class target = E3PSTask.class;
	    int idx_target = spec.addClassList(target, true);

	    // SELECT A0.* FROM JELTask A0 WHERE (A0.idA3parentreference = 0) and (a0.ida3b4 = '60122') ORDER BY A0.taskSeq asc

	    SearchUtil.appendEQUAL(spec, target, "parentReference.key.id", Long.parseLong("0"), idx_target);
	    SearchUtil.appendEQUAL(spec, target, "projectReference.key.id", CommonUtil.getOIDLongValue(project), idx_target);

	    SearchUtil.setOrderBy(spec, target, idx_target, "taskSeq", false);
	    // Kogger.debug(getClass(), "getLevelOneTask<<<< "+spec);
	    result = PersistenceHelper.manager.find(spec);

	    int i = 0;
	    while (result.hasMoreElements()) {
		Object[] obj = (Object[]) result.nextElement();

		tmp.add(i, CommonUtil.getFullOIDString((E3PSTask) obj[0]));
		i++;
	    }

	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}

	return tmp;
    }

    public void setLevelOneTaskcode(TemplateProject project, Vector tVec) throws WTPropertyVetoException, WTException {
	Vector taskVec = getLevelOneTask(project);

	for (int i = 0; i < taskVec.size(); i++) {
	    E3PSTask task = (E3PSTask) CommonUtil.getObject((String) taskVec.get(i));

	    // WBS코드 & 네크워트코드 & 액티비티코드 3개가 & 구분자로 들어감
	    task.setTaskCode((String) tVec.get(i));

	    // Kogger.debug(getClass(), "Task["+task.getTaskName()+"]-["+CommonUtil.getFullOIDString(task)+"]>>> "+task.getTaskCode());

	    PersistenceHelper.manager.modify(task);
	}
    }

    public TemplateTask getLevelOneTask(TemplateTask task) {

	if (task.getParent() != null) {
	    getLevelOneTask((TemplateTask) task.getParent());
	}

	return task;
    }

    public static String getTaskMaxSeq() throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] {};
	    Object args[] = new Object[] {};
	    try {
		Object obj = RemoteMethodServer.getDefault().invoke("getTaskMaxSeq", ProjectTaskHelper.class.getName(), null, argTypes, args);
		return ((String) obj);

	    } catch (RemoteException e) {
		Kogger.error(ProjectTaskHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectTaskHelper.class, e);
		throw new WTException(e);
	    }
	}

	/**
	 * 
	 //select nvl(max(taskcode), '1') from jeltask where taskcode not like 'R%'
	 * 
	 */

	String result = "";

	OraclePds81 dds = new OraclePds81();
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;

	try {
	    String sql = "SELECT NVL(MAX(taskCode),'1') no FROM E3PSTask WHERE taskCode NOT LIKE 'R%'";
	    // Kogger.debug(getClass(), "sql = " + sql);

	    con = dds.getDataSource().getConnection();
	    stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    rs = stmt.executeQuery(sql);

	    while (rs.next()) {
		if (rs.getString("no").equals("1"))
		    result = "0000001";
		else {
		    String no = rs.getString("no");
		    // Kogger.debug(getClass(), "no = "+no);
		    result = "" + (Integer.parseInt(no) + 1);
		}
	    }
	} catch (SQLException e) {
	    Kogger.error(ProjectTaskHelper.class, e);
	} finally {
	    try {
		if (rs != null)
		    rs.close();
		if (stmt != null)
		    stmt.close();
		if (con != null)
		    con.close();
	    } catch (SQLException e) {
		Kogger.error(ProjectTaskHelper.class, e);
	    }
	}
	DecimalFormat decimalformat = new DecimalFormat("00000000");
	return decimalformat.format(Long.parseLong(result));

    }

    public QueryResult getChildDR(TemplateProject project) {
	try {

	    QuerySpec qs = new QuerySpec();
	    int idx_task = qs.appendClassList(TemplateTask.class, true);

	    SearchCondition sc = new SearchCondition(TemplateTask.class, "projectReference.key.id", SearchCondition.EQUAL, project.getPersistInfo().getObjectIdentifier().getId());
	    qs.appendWhere(sc, new int[] { idx_task });

	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    sc = new SearchCondition(TemplateTask.class, "taskType", SearchCondition.EQUAL, "DR");
	    qs.appendWhere(sc, new int[] { idx_task });

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    int idx_schedule = qs.appendClassList(ExtendScheduleData.class, false);

	    ClassAttribute ca = null;
	    ClassAttribute ca2 = null;

	    ca = new ClassAttribute(TemplateTask.class, "taskSchedule.key.id");
	    ca2 = new ClassAttribute(ExtendScheduleData.class, "thePersistInfo.theObjectIdentifier.id");
	    SearchCondition sc2 = new SearchCondition(ca, "=", ca2);
	    sc2.setFromIndicies(new int[] { idx_task, idx_schedule }, 0);
	    sc2.setOuterJoin(0);
	    qs.appendWhere(sc2, new int[] { idx_task, idx_schedule });

	    OrderBy orderby = new OrderBy(new ClassAttribute(ExtendScheduleData.class, "planEndDate"), false, null);
	    qs.appendOrderBy(orderby, idx_schedule);

	    // SearchUtil.setOrderBy(qs, TemplateTask.class, idx_task, TemplateTask.TASK_SEQ , "taskSeq", false);

	    QueryResult result = PersistenceHelper.manager.find(qs);
	    return result;

	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }
    
    
    public QueryResult getTaskWithType(TemplateProject project, String type, String category) {
	try {

	    QuerySpec qs = new QuerySpec();
	    int idx_task = qs.appendClassList(TemplateTask.class, true);

	    SearchCondition sc = new SearchCondition(TemplateTask.class, "projectReference.key.id", SearchCondition.EQUAL, project.getPersistInfo().getObjectIdentifier().getId());
	    qs.appendWhere(sc, new int[] { idx_task });

	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    sc = new SearchCondition(TemplateTask.class, "taskType", SearchCondition.EQUAL, type);
	    qs.appendWhere(sc, new int[] { idx_task });
	    
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    sc = new SearchCondition(TemplateTask.class, "taskTypeCategory", SearchCondition.EQUAL, category);
	    qs.appendWhere(sc, new int[] { idx_task });

	    SearchUtil.setOrderBy(qs, TemplateTask.class, idx_task, TemplateTask.TASK_SEQ, "taskSeq", false);
	    QueryResult result = PersistenceHelper.manager.find(qs);
	    return result;

	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getTaskWithType(TemplateProject project, String type) {
	try {

	    QuerySpec qs = new QuerySpec();
	    int idx_task = qs.appendClassList(TemplateTask.class, true);

	    SearchCondition sc = new SearchCondition(TemplateTask.class, "projectReference.key.id", SearchCondition.EQUAL, project.getPersistInfo().getObjectIdentifier().getId());
	    qs.appendWhere(sc, new int[] { idx_task });

	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    sc = new SearchCondition(TemplateTask.class, "taskType", SearchCondition.EQUAL, type);
	    qs.appendWhere(sc, new int[] { idx_task });

	    SearchUtil.setOrderBy(qs, TemplateTask.class, idx_task, TemplateTask.TASK_SEQ, "taskSeq", false);
	    QueryResult result = PersistenceHelper.manager.find(qs);
	    return result;

	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }
    
    public boolean mainSchedulUpdate(TemplateProject project, String mainScheduleCode, boolean isAll) throws Exception{
	
	
	
	Transaction transaction = null;
	Connection conn = null;
	WTConnection wtConn = null;
	boolean target = false;
	try {
	    
	    wtConn = EcmUtil.getWTConnection();
	    conn = wtConn.getConnection();

	    transaction = new Transaction();

	    transaction.start();
	    
	    // 목적 : 프로젝트 일정변경 완료시 or 타스크 완료시 주요 일정 업데이트
	    if (project instanceof ProductProject) {
		
		
		
		E3PSTask scheduleTask = null;

		final String SCHEDULCODE = "MAINSCHEDULECODE";
		
		KETParamMapUtil paramMap = new KETParamMapUtil();

		if (isAll) {// 프로젝트 일정변경완료시 주요일정 계획완료일/실제완료일 초기화
		    Map<String, Object> parameter = new HashMap<String, Object>();
		    parameter.put("locale", Locale.KOREAN);
		    parameter.put("codeType", SCHEDULCODE);

		    List<Map<String, Object>> codeList = NumberCodeHelper.manager.getNumberCodeList(parameter);
		    for (Map<String, Object> mapIn : codeList) {
			
			String code = (String) mapIn.get("code");
			String columnKey = (String) mapIn.get("description");
			
			project = (ProductProject) this.syncMainScheduleProject(project, columnKey, null);//계획완료일 초기화
			
			columnKey = CodeHelper.service.getNumberCodeDescription(SCHEDULCODE, code, "en");
			
			project = (ProductProject) this.syncMainScheduleProject(project, columnKey, null);//실제완료일 초기화
			
		    }

		}

		ProjectScheduleDao dao = new ProjectScheduleDao(conn);

		
		paramMap.put("project", project);
		paramMap.put("oidLong", CommonUtil.getOIDLongValue(project));
		paramMap.put("mainScheduleCode", mainScheduleCode);

		List<KETParamMapUtil> taskList = dao.getProjectTaskMainSchedule(paramMap); //task 리스트를 가져온다
		
		for (KETParamMapUtil taskInfoMap : taskList) {
		    String mscCode = (String)taskInfoMap.get("mainScheduleCode");
		    if(StringUtils.isEmpty(mscCode)){
			continue;
		    }
		    
		    System.out.println(project.getPjtNo() + "의 일정동기화 시작 ");
		    
		    scheduleTask = (E3PSTask)CommonUtil.getObject((String)taskInfoMap.get("taskOid"));
		    
		    ExtendScheduleData data = (ExtendScheduleData) scheduleTask.getTaskSchedule().getObject();// 타스크의 일정정보를 가져온다

		    /****************************************************************************************************************************************************************************
		     * 
		     * 1. 계획완료일 업데이트
		     *****************************************************************************************************************************************************************************/
		    String columnKey = CodeHelper.service.getNumberCodeDescription(SCHEDULCODE, mscCode, "ko"); // 넘버코드에서 업데이트 대상인 컬럼키를 뽑아온다
		    Object value = data.getPlanEndDate(); // 계획완료일을 가져온다

		    project = (ProductProject) this.syncMainScheduleProject(project, columnKey, value);// 프로젝트, 프로젝트의 주요일정 컬럼키, 계획완료일 업데이트
//		    System.out.println(scheduleTask.getTaskName()+" 의 계획 완료일 "+value+" 업데이트 완료! 컬럼키 : "+columnKey);

		    /****************************************************************************************************************************************************************************
		     * 2. 실제완료일 업데이트
		     * 
		     *****************************************************************************************************************************************************************************/

		    columnKey = CodeHelper.service.getNumberCodeDescription(SCHEDULCODE, mscCode, "en"); // 넘버코드에서 업데이트 대상인 컬럼키를 뽑아온다

		    value = data.getExecEndDate();// 실제완료일을 가져온다

		    project = (ProductProject) this.syncMainScheduleProject(project, columnKey, value);// 프로젝트, 프로젝트의 주요일정 컬럼키, 실제완료일 업데이트
//		    System.out.println(scheduleTask.getTaskName()+" 의 실제 완료일 "+value+" 업데이트 완료! 컬럼키 : "+columnKey);
		    
		    System.out.println(project.getPjtNo() + "의 일정동기화 완료 ");
		    target = true;
		}

		PersistenceHelper.manager.save(project);
		
	    }
	    
	    transaction.commit();
	    transaction = null;
	    
	} catch (Exception e) {
	    e.printStackTrace();
	    throw e;
	} finally {

	    if (transaction != null) {
		transaction.rollback();
		transaction = null;
	    }
	    
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !wtConn.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}
	
	return target;
    }
    
    public Object syncMainScheduleProject(Object projectObj, String columnKey, Object value) throws Exception{
	PropertyDescriptor pd = new PropertyDescriptor(columnKey, projectObj.getClass());
    	pd.getWriteMethod().invoke(projectObj, new Object[] { value });
    	return(projectObj);
    }

    public static void test() throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = null;
	    Object args[] = null;
	    try {
		Object obj = RemoteMethodServer.getDefault().invoke("test", ProjectTaskHelper.class.getName(), null, argTypes, args);
		return;

	    } catch (RemoteException e) {
		Kogger.error(ProjectTaskHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectTaskHelper.class, e);
		throw new WTException(e);
	    }
	}

	Class taskClass = E3PSTask.class;

	ClassInfo classinfo = WTIntrospector.getClassInfo(taskClass);

	String task_seqColumnName = DatabaseInfoUtilities.getValidColumnName(classinfo, TemplateTask.TASK_SEQ);

	QuerySpec spec = new QuerySpec();
	spec.setAdvancedQueryEnabled(true);
	spec.setDescendantQuery(false);
	spec.addClassList(E3PSTask.class, false);
	KeywordExpression ke = new KeywordExpression("NVL(Max(" + task_seqColumnName + "), 0) ");
	ke.setColumnAlias("task_seq");
	spec.appendSelect(ke, new int[] { 0 }, false);

	// Kogger.debug(getClass(), "rrr = " + rrrr.size());

	/*
	 * spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.PROJECT_REFERENCE + ".key.id", "=", 0), new int[]{0}); spec.appendAnd(); spec.appendWhere(new SearchCondition(E3PSTask.class,
	 * E3PSTask.PARENT_REFERENCE + ".key.id", "=", 0), new int[]{0});
	 */
	QueryResult rrrr = PersistenceHelper.manager.find(spec);

	if (rrrr.hasMoreElements()) {
	    Object o[] = (Object[]) rrrr.nextElement();
	    BigDecimal number = (BigDecimal) o[0];
	    number.intValue();
	}

    }

    public QueryResult getTaskWithMilestone(TemplateProject project, boolean milestone) {
	try {

	    QuerySpec qs = new QuerySpec();
	    int idx_task = qs.appendClassList(TemplateTask.class, true);

	    SearchCondition sc = new SearchCondition(TemplateTask.class, "projectReference.key.id", SearchCondition.EQUAL, project.getPersistInfo().getObjectIdentifier().getId());
	    qs.appendWhere(sc, new int[] { idx_task });

	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }

	    if (milestone)
		sc = new SearchCondition(TemplateTask.class, "mileStone", SearchCondition.IS_TRUE);
	    else
		sc = new SearchCondition(TemplateTask.class, "mileStone", SearchCondition.IS_FALSE);
	    qs.appendWhere(sc, new int[] { idx_task });

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    int idx_schedule = qs.appendClassList(ExtendScheduleData.class, false);

	    ClassAttribute ca = null;
	    ClassAttribute ca2 = null;

	    ca = new ClassAttribute(TemplateTask.class, "taskSchedule.key.id");
	    ca2 = new ClassAttribute(ExtendScheduleData.class, "thePersistInfo.theObjectIdentifier.id");
	    SearchCondition sc2 = new SearchCondition(ca, "=", ca2);
	    sc2.setFromIndicies(new int[] { idx_task, idx_schedule }, 0);
	    sc2.setOuterJoin(0);
	    qs.appendWhere(sc2, new int[] { idx_task, idx_schedule });

	    OrderBy orderby = new OrderBy(new ClassAttribute(ExtendScheduleData.class, "planEndDate"), false, null);
	    qs.appendOrderBy(orderby, idx_schedule);

	    // SearchUtil.setOrderBy(qs, TemplateTask.class, idx_task, TemplateTask.TASK_SEQ , "taskSeq", false);
	    // OrderBy orderby = new OrderBy(new ClassAttribute(ExtendScheduleData.class, "planEndDate"), false, null);
	    QueryResult result = PersistenceHelper.manager.find(qs);
	    return result;

	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public static TemplateTask getTaskFromTaskNo(TemplateProject project, String taskNo) throws Exception {

	QuerySpec spec = new QuerySpec(TemplateTask.class);

	spec.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.TASK_NO, "=", taskNo), new int[] { 0 });

	spec.appendAnd();

	long projectId = project.getPersistInfo().getObjectIdentifier().getId();
	spec.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.PROJECT_REFERENCE + ".key.id", "=", projectId), new int[] { 0 });

	QueryResult rs = PersistenceHelper.manager.find(spec);
	TemplateTask task = null;
	if (rs.hasMoreElements()) {
	    task = (TemplateTask) rs.nextElement();
	}
	return task;

    }

    public QueryResult getIssueTask(E3PSProject project) throws Exception {

	QuerySpec spec = new QuerySpec();
	int idx1 = spec.addClassList(E3PSTask.class, true);
	int idx2 = spec.addClassList(ProjectIssue.class, false);
	spec.setDistinct(true);

	ClassAttribute ca0 = new ClassAttribute(E3PSTask.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute ca1 = new ClassAttribute(ProjectIssue.class, ProjectIssue.TASK_REFERENCE + ".key.id");

	SearchCondition sc = new SearchCondition(ca0, "=", ca1);
	sc.setFromIndicies(new int[] { idx1, idx2 }, 0);
	sc.setOuterJoin(0);
	spec.appendWhere(sc, new int[] { idx1, idx2 });
	spec.appendAnd();
	long projectId = project.getPersistInfo().getObjectIdentifier().getId();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.PROJECT_REFERENCE + ".key.id", "=", projectId), new int[] { idx1 });

	spec.appendAnd();

	spec.appendWhere(new SearchCondition(ProjectIssue.class, ProjectIssue.IS_DONE, SearchCondition.IS_FALSE, false), new int[] { idx2 });

	return PersistenceHelper.manager.find(spec);
    }

    /*
     * ListMyProjectPop 관련 Helper 프로젝트의 1레벨 태스크와 End 태스크를 하나로 묶어서 Hashtable Return 한다.
     */
    public static Hashtable getMyTaskCondition(E3PSProject project, WTUser selectUser) throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { E3PSProject.class, WTUser.class };
	    Object args[] = new Object[] { project, selectUser };
	    try {
		Object obj = RemoteMethodServer.getDefault().invoke("getMyTaskCondition", ProjectTaskHelper.class.getName(), null, argTypes, args);
		return (Hashtable) obj;

	    } catch (RemoteException e) {
		Kogger.error(ProjectTaskHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectTaskHelper.class, e);
		throw new WTException(e);
	    }
	}
	Hashtable htMy = new Hashtable();
	Object[] obj1 = null;
	QueryResult qr1 = ProjectTaskHelper.manager.getChild(project);

	while (qr1.hasMoreElements()) {
	    obj1 = (Object[]) qr1.nextElement();
	    E3PSTask task = (E3PSTask) obj1[0];
	    E3PSTaskData Data = new E3PSTaskData(task);
	    if (Data.taskState != ProjectStateFlag.TASK_STATE_COMPLETE) {
		// Timestamp tsEnd1 = Data.taskPlanEndDate;
		// Timestamp tsToday1 = DateUtil.convertEndDate(DateUtil.getToDay());
		// if(tsEnd1.getTime() <= tsToday1.getTime()){
		Vector vtMy = new Vector();
		Vector list = new Vector();
		ProjectTreeNode root = (ProjectTreeNode) ProjectScheduleHelper.manager.getRoot(task, false);
		makeVector(root, list);
		for (int i = 0; i < list.size(); i++) {
		    ProjectTreeNode node = (ProjectTreeNode) list.get(i);
		    ProjectTreeNodeData td = (ProjectTreeNodeData) node.getUserObject();
		    E3PSTask childTask = (E3PSTask) td.getData();
		    E3PSTaskData taskData = new E3PSTaskData(childTask);
		    if (taskData.taskState != ProjectStateFlag.TASK_STATE_COMPLETE) {
			Timestamp tsEnd = taskData.taskPlanEndDate;
			Timestamp tsToday = DateUtil.convertEndDate(DateUtil.getToDay());
			if (tsEnd.getTime() <= tsToday.getTime()) {
			    QueryResult parentResult = ProjectTaskHelper.manager.getChildList(childTask);
			    if (parentResult.size() > 0) {
				continue;
			    }
			    boolean checkMyMember = false;
			    QueryResult masterList = TaskUserHelper.manager.getMaster(childTask);
			    while (masterList.hasMoreElements()) {
				Object oms[] = (Object[]) masterList.nextElement();
				TaskMemberLink link = (TaskMemberLink) oms[0];
				PeopleData datams = new PeopleData(link.getMember().getMember());
				if (selectUser.getName().equals(datams.id)) {
				    checkMyMember = true;
				}
			    }
			    QueryResult memberlist = TaskUserHelper.manager.getMember(childTask);
			    while (memberlist.hasMoreElements()) {
				Object omm[] = (Object[]) memberlist.nextElement();
				TaskMemberLink link = (TaskMemberLink) omm[0];
				PeopleData datamm = new PeopleData(link.getMember().getMember());
				if (selectUser.getName().equals(datamm.id)) {
				    checkMyMember = true;
				}
			    }
			    if (!checkMyMember) {
				continue;
			    }
			    String taskUrlLink = taskData.e3psTaskOID;
			    vtMy.add(taskUrlLink);
			}
		    } else {
			continue;
		    }
		}

		htMy.put(task, vtMy);
		// }
	    }
	}
	return htMy;
    }

    public static void makeVector(ProjectTreeNode node, Vector vector) {

	vector.add(node);
	for (int i = 0; i < node.getChildCount(); i++) {
	    makeVector((ProjectTreeNode) node.getChildAt(i), vector);
	}

    }

    public static void main(String args[]) throws Exception {

	RemoteMethodServer server = RemoteMethodServer.getDefault();
	server.setUserName("wcadmin");
	server.setPassword("wcadmin");

	String tOid = "e3ps.project.MoldProject:142174";

	MoldProject project = (MoldProject) CommonUtil.getObject(tOid);

	updateSeq(project, 1);

	// TemplateTask task = getDebugTask(project, "금형부품");

	// Kogger.debug(getClass(), "task ======= " + task.getTaskName());

	/*
	 * test();
	 * 
	 * Class taskClass = E3PSTask.class;
	 * 
	 * ClassInfo classinfo = WTIntrospector.getClassInfo(taskClass); String task_seqColumnName = DatabaseInfoUtilities.getValidColumnName( classinfo, TemplateTask.TASK_SEQ);
	 * 
	 * QuerySpec spec = new QuerySpec(E3PSTask.class); KeywordExpression ke = new KeywordExpression("NVL(Max(" + task_seqColumnName +")"); spec.appendSelect(ke, new int[]{0}, false);
	 * 
	 * QuerySpec mainSpec = new QuerySpec(); mainSpec.setAdvancedQueryEnabled(true); SubSelectExpression subfrom1 = new SubSelectExpression(spec); int idx = mainSpec.appendFrom(subfrom1); ke = new
	 * KeywordExpression(mainSpec.getFromClause().getAliasAt(idx) + "."+ "*"); mainSpec.appendSelect(ke, new int[]{idx}, false);
	 * 
	 * Kogger.debug(getClass(), "mainSpec = " + mainSpec); QueryResult rs = PersistenceHelper.manager.find(mainSpec);
	 * 
	 * while(rs.hasMoreElements()){ Object o[] = (Object[])rs.nextElement(); Kogger.debug(getClass(), o[0]); }
	 */

	// TemplateTask task = (TemplateTask)CommonUtil.getObject("e3ps.project.TemplateTask:162926");

	// Vector v = getSubTask(task);

	// Kogger.debug(getClass(), "v.size() = " + v.size());

    }
    
    public static void makeTree(ProjectTreeNode node,List<ProjectTreeNode> list){
	list.add(node);
	for (int i = 0; i < node.getChildCount(); i++) {
	    makeTree((ProjectTreeNode) node.getChildAt(i), list);
	}
    }
    
    public static List<E3PSTask> getTaskByProject(TemplateProject project) throws Exception {
	List<ProjectTreeNode> list = new ArrayList<ProjectTreeNode>();
	ProjectTreeNode root = (ProjectTreeNode)ProjectScheduleHelper.manager.getRoot(project, false);
	ProjectTaskHelper.makeTree(root, list);
	
	List<E3PSTask> taskList = new ArrayList<E3PSTask>();
	
	for (int i = 0; i < list.size(); i++) {
	    ProjectTreeNode node = (ProjectTreeNode) list.get(i);
	    ProjectTreeNodeData td = (ProjectTreeNodeData) node.getUserObject();
	    if (td.getData() instanceof TemplateProject) {
		continue;
	    }
	    E3PSTask childTask = (E3PSTask) td.getData();
	    taskList.add(childTask);
	}
	
	return taskList;
	
    }
    
    public static boolean isDR6taskCompleteCheck(E3PSTask task) throws Exception {
	
	boolean check = true;
	String taskOid = CommonUtil.getOIDString(task);
	if("6".equals(task.getTaskTypeCategory()) || "MC042".equals(task.getMainScheduleCode())){
	    List<E3PSTask> taskList = ProjectTaskHelper.getTaskByProject(task.getProject());
	    for(E3PSTask checkTask : taskList){
		if(taskOid.equals(CommonUtil.getOIDString(checkTask))){
		    break;
		}
		String taskType = checkTask.getTaskType();
		if(StringUtils.isNotEmpty(taskType) && "GATE".equals(taskType.toUpperCase())){
		    if(checkTask.getTaskState() != 5){
			check = false;
			break;
		    }
		}
	    }
	}
	
	return check;
	
    }
    
    public static boolean isTaskAllCheckByProject(TemplateProject project) throws Exception {
	
	List<E3PSTask> taskList = ProjectTaskHelper.getTaskByProject(project);
	
	boolean stateCheck = true;
	
	for(E3PSTask checkTask : taskList){
	    if(checkTask.getTaskState() != 5){
		stateCheck = false;
		break;
	    }
	}
	return stateCheck;
	
    }
}
