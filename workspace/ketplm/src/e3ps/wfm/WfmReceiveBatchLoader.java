package e3ps.wfm;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

import wt.change2.WTChangeOrder2;
import wt.change2.WTChangeRequest2;
import wt.fc.ObjectNoLongerExistsException;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.CompoundQuerySpec;
import wt.query.OrderBy;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.SetOperator;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.team.Team;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.util.WTRuntimeException;
import wt.workflow.definer.WfDefinerHelper;
import wt.workflow.definer.WfTemplateObject;
import wt.workflow.engine.InvalidDataException;
import wt.workflow.engine.ProcessData;
import wt.workflow.engine.WfEngineHelper;
import wt.workflow.engine.WfEngineServerHelper;
import wt.workflow.engine.WfProcess;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WorkItem;
import e3ps.common.util.CommonUtil;
import e3ps.ecm.beans.EcmUtil;
import e3ps.ecm.beans.ProdEcoBeans;
import e3ps.ecm.entity.KETChangeRequestExpansion;
import e3ps.ecm.entity.KETMoldChangeRequest;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdChangeRequest;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.service.KETWfmHelper;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.shared.log.Kogger;

public class WfmReceiveBatchLoader implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static WfmReceiveBatchLoader manager = new WfmReceiveBatchLoader();

    public WfmReceiveBatchLoader() {

    }

    // windchill e3ps.wfm.WfmReceiveBatchLoader Administrator 0 ⇒ 일괄수신확인
    // windchill e3ps.wfm.WfmReceiveBatchLoader RETIRE 0 ⇒ 퇴사자 일괄수신확인
    // windchill e3ps.wfm.WfmReceiveBatchLoader Administrator 1 ECR-1507-001 ⇒ ECR TO-DO 활동완료
    // windchill e3ps.wfm.WfmReceiveBatchLoader RETIRE 2 ⇒ 퇴사자 결재대기함 중 PBO없는 것 삭제
    // windchill e3ps.wfm.WfmReceiveBatchLoader Administrator 3 ECO-1507-001 ⇒ ECO의 후속활동 생성(ECN)
 // windchill e3ps.wfm.WfmReceiveBatchLoader joohee6 4 e3ps.ecm.entity.KETProdChangeActivity:1033173870 ⇒ ECO의 후속활동 생성(ECN)
    // 파라미터 1 : id (퇴사자 일괄 처리 일 경우 RETIRE)
    // 파라미터 2 : 구분(0:수신확인, 1:TO-DO 활동완료)
    // 파라미터 3 : ECR ID 또는 ECO ID

    // 기능 1: 사용자별 일괄 수신확인 (수신목록에 퇴사자가 존재할 경우 관리자 결재대기함으로 오기때문에 일괄처리위함) 2015.07.17 by 황정태
    // 기능 2: 퇴사자 일괄 수신확인 2017.11.13 by 황정태
    // 기능 3: ECR TO-DO에서 활동완료되지 않은 건 수동으로 돌리기 위해서 추가함 2015.07.21 by 황정태

    public static void main(String[] args) {

	try {

	    String Userid = null;
	    String Gubun = null;
	    String EcrId = null;
	    if (args == null || args.length < 2) {
		throw new Exception("@@ args need !");
	    } else {
		Userid = args[0];
		Gubun = args[1];
	    }

	    if (!Gubun.equals("0") && !Gubun.equals("1") && !Gubun.equals("2") && !Gubun.equals("3") && !Gubun.equals("4")) {
		throw new Exception("@@ args Gubun not available! 0 or 1 or 2");
	    }
	    if (Gubun.equals("1") || Gubun.equals("4")) {
		EcrId = args[2];
		if (StringUtils.isEmpty(EcrId)) {
		    throw new Exception("@@ args EcrId Is null!");
		}
	    }
System.out.println("EcrId : "+EcrId);
	    Kogger.debug(WfmReceiveBatchLoader.class, "@start");
	    WfmReceiveBatchLoader.manager.receiveConfirm(Userid, Gubun, EcrId);
	    Kogger.debug(WfmReceiveBatchLoader.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(WfmReceiveBatchLoader.class, e);
	}
    }

    public void receiveConfirm(String Userid, String Gubun, String EcrId) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class, String.class, String.class };
		Object aobj[] = { Userid, Gubun, EcrId };

		Kogger.debug(getClass(), "@		start");
		RemoteMethodServer.getDefault().invoke("receiveConfirm", null, this, aclass, aobj);
		Kogger.debug(getClass(), "@		end");

		return;

	    } catch (RemoteException e) {
		Kogger.debug(getClass(), e);
	    } catch (InvocationTargetException e) {
		Kogger.debug(getClass(), e);
	    } catch (Exception e) {
		Kogger.debug(getClass(), e);
	    }
	} else {
	    executeMigration(Userid, Gubun, EcrId);
	}
    }

    public void executeMigration(String Userid, String Gubun, String EcrId) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();
	WorkItem item = null;

	try {

	    SessionHelper.manager.setAdministrator();
	    WTUser user = null;
	    try {
		if (!"RETIRE".equals(Userid)) {
		    People people = PeopleHelper.manager.getPeople(Userid);
		    user = people.getUser();
		}

	    } catch (Exception e) {
		throw new Exception("@@ user Error !");
	    }

	    if ("3".equals(Gubun)) {//ecn 생성
		Kogger.debug(getClass(), "**************** createEcnToDo Start **************************");
		try{
		    System.out.println("start..");
		    createEcnToDo(user, EcrId);    
		}catch(Exception e){
		    e.printStackTrace();
		}
		
		Kogger.debug(getClass(), "**************** createEcnToDo End **************************");
	    }else if("4".equals(Gubun)){
		Kogger.debug(getClass(), "**************** createEcnToDoByEcnOid Start **************************");
		try{
		    System.out.println("start..");
		    System.out.println("EcrId : "+EcrId);
		    createEcnToDoByEcnOid(user, EcrId);    
		}catch(Exception e){
		    e.printStackTrace();
		}
		
		Kogger.debug(getClass(), "**************** createEcnToDoByEcnOid End **************************");
	    }else {
	    
		Kogger.debug(getClass(), "**************** getReceiveList Start **************************");

		QuerySpec qs = null;

		if ("RETIRE".equals(Userid)) {
		    Kogger.debug(getClass(), "**************** 퇴사자 일괄수신확인 처리 query spec call start **************************");
		    qs = getQuerySpecForReitrePeople(Gubun);
		    Kogger.debug(getClass(), "**************** 퇴사자 일괄수신확인 처리 query spec call end **************************");
		} else {
		    qs = getQuerySpecForWaitingAppr(user, Gubun, EcrId);
		}

		QueryResult qr = PersistenceHelper.manager.find(qs);

		Kogger.debug(getClass(), "**************** getReceiveList End **************************");
		String oid = null;
		String masterOid = null;
		Persistable pst = null;

		int count = 0;
		KETWfmApprovalMaster master = null;

		Kogger.debug(getClass(), "WorkspaceServlet.getQuerySpecForWaitingAppr: query=\n" + qs);
		if (qr.size() < 1) {
		    Kogger.debug(getClass(), "**************** 수신확인 대상 없음 **************************");
		} else {
		    Kogger.debug(getClass(), "**************** completeActivity Start **************************");
		    while (qr.hasMoreElements()) {
			Object[] obj = (Object[]) qr.nextElement();
			item = (WorkItem) obj[0];
			oid = item.toString();
			WfAssignedActivity activity = null;
			WfAssignedActivity old_activity = null;
			try {
			    pst = item.getPrimaryBusinessObject().getObject();
			} catch (WTRuntimeException wte) {
			    if (StringUtils.contains(wte.getNestedThrowable().toString(), "wt.fc.ObjectNoLongerExistsException")) {
				Kogger.debug(getClass(), "**************** (PBO 없음)workItem 삭제 **************************");
				activity = (WfAssignedActivity) item.getSource().getObject();

				if (PersistenceHelper.isPersistent(activity)) {
				    activity.deleteActivity();
				}

				continue;
			    } else {
				throw new Exception("@@ WTRuntimeException !!");
			    }

			}
			if ("0".equals(Gubun) || "1".equals(Gubun)) {
			    master = WorkflowSearchHelper.manager.getMaster(pst);
			    masterOid = CommonUtil.getOIDString(master);
			    // System.out.println("oid : "+oid);
			    System.out.println("masterOid : " + masterOid);
			    if (StringUtils.isEmpty(masterOid)) {
				masterOid = "";
			    }
			    Hashtable aHash = new Hashtable();
			    aHash.put("item", item);
			    aHash.put("condition", "taskComplete");
			    aHash.put("master", masterOid);
			    if (Gubun.equals("0")) {
				aHash.put("comment", "일괄 수신확인");
			    } else {
				aHash.put("comment", "");
			    }

			    KETWfmHelper.service.completeActivity(aHash); // 결재 수행 완료
			    count++;
			    Kogger.debug(getClass(), "===> " + count + " 건 수신확인완료");
			}

		    }
		    Kogger.debug(getClass(), "**************** completeActivity End **************************");
		}
		Kogger.debug(getClass(), "WorkspaceServlet.getQuerySpecForWaitingAppr: query=\n" + qs);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    Kogger.error(getClass(), e);
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    public WTChangeRequest2 getEcr(String EcrId) throws Exception {

	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(KETProdChangeRequest.class, true);
	if (qs.getConditionCount() > 0) {
	    qs.appendAnd();
	}
	qs.appendWhere(new SearchCondition(KETProdChangeRequest.class, "ecrId", SearchCondition.EQUAL, EcrId), new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(qs);
	WTChangeRequest2 wcr = null;
	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    wcr = (WTChangeRequest2) o[0];
	}

	if (wcr == null || qr.size() < 1) {
	    QuerySpec qs2 = new QuerySpec();
	    int idx2 = qs2.addClassList(KETMoldChangeRequest.class, true);
	    if (qs2.getConditionCount() > 0) {
		qs2.appendAnd();
	    }
	    qs2.appendWhere(new SearchCondition(KETMoldChangeRequest.class, "ecrId", SearchCondition.EQUAL, EcrId), new int[] { idx2 });

	    qr = PersistenceHelper.manager.find(qs2);
	    while (qr.hasMoreElements()) {
		Object[] o = (Object[]) qr.nextElement();
		wcr = (WTChangeRequest2) o[0];
	    }
	}

	return wcr;
    }

    public QuerySpec getQuerySpecForReitrePeople(String Gubun) throws Exception {// 수신미확인 목록이 있는 퇴사자(people) 목록
	QuerySpec spec = new QuerySpec();

	int idx = spec.addClassList(WorkItem.class, true);
	int idx2 = spec.addClassList(People.class, false);
	int idx3 = spec.appendClassList(WfAssignedActivity.class, false);

	spec.appendWhere(new SearchCondition(new ClassAttribute(WorkItem.class, "ownership.owner.key.id"), "=", new ClassAttribute(
	        People.class, "userReference.key.id")), new int[] { idx, idx2 });

	if (spec.getConditionCount() > 0) {

	    if ("0".equals(Gubun) || "1".equals(Gubun)) {
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(WorkItem.class, WorkItem.STATUS, SearchCondition.EQUAL, "POTENTIAL"),
		        new int[] { idx });
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(WorkItem.class, WorkItem.ROLE, SearchCondition.EQUAL, "RECIPIENT"), new int[] { idx });
	    }

	    if ("2".equals(Gubun)) {

		spec.appendAnd();
		spec.appendWhere(new SearchCondition(new ClassAttribute(WorkItem.class, "source.key.id"), "=", new ClassAttribute(
		        WfAssignedActivity.class, "thePersistInfo.theObjectIdentifier.id")), new int[] { idx, idx3 });

	    }
	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(People.class, People.IS_DISABLE, SearchCondition.IS_TRUE), new int[] { idx2 });
	}

	return spec;
    }

    public QuerySpec getQuerySpecForWaitingAppr(WTUser user, String Gubun, String EcrId) throws Exception {

	QuerySpec spec = new QuerySpec();
	int itemIdx = spec.addClassList(WorkItem.class, true);

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(
	        new SearchCondition(WorkItem.class, "ownership.owner.key", SearchCondition.EQUAL, PersistenceHelper
	                .getObjectIdentifier(user)), new int[] { itemIdx });
	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(new SearchCondition(WorkItem.class, WorkItem.STATUS, SearchCondition.EQUAL, "POTENTIAL"), new int[] { itemIdx });
	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	if (Gubun.equals("0")) {
	    spec.appendWhere(new SearchCondition(WorkItem.class, WorkItem.ROLE, SearchCondition.EQUAL, "RECIPIENT"), new int[] { itemIdx });
	}
	if (Gubun.equals("1")) {
	    WTChangeRequest2 wcr = getEcr(EcrId);
	    KETChangeRequestExpansion expansion = null;

	    QuerySpec spec2 = new QuerySpec(KETChangeRequestExpansion.class);
	    int itemIdx2 = spec2.addClassList(KETChangeRequestExpansion.class, true);

	    if (spec2.getConditionCount() > 0) {
		spec2.appendAnd();
	    }

	    spec2.appendWhere(
		    new SearchCondition(KETChangeRequestExpansion.class, "ecrReference.key.id", "=", CommonUtil.getOIDLongValue(wcr)),
		    new int[] { 0 });

	    QueryResult result = PersistenceHelper.manager.find(spec2);
	    if (result.hasMoreElements()) { // while (result.hasMoreElements()) {
		// 제품, 금형 ECR 확장팩
		expansion = (KETChangeRequestExpansion) result.nextElement();
	    }

	    spec.appendWhere(
		    new SearchCondition(WorkItem.class, "primaryBusinessObject.key.classname", SearchCondition.EQUAL, CommonUtil
		            .getRefOID(expansion)), new int[] { itemIdx });

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(WorkItem.class, WorkItem.ROLE, SearchCondition.EQUAL, "ASSIGNEE"), new int[] { itemIdx });
	}

	spec.appendOrderBy(new OrderBy(new ClassAttribute(WorkItem.class, "thePersistInfo.createStamp"), true), new int[] { itemIdx });

	return spec;
    }
    
    public void createEcnToDoByEcnOid(WTUser user, String ecnOid) throws Exception {
	System.out.println("ecnOid :"+ ecnOid);
		
	KETProdChangeActivity prodCA = (KETProdChangeActivity)CommonUtil.getObject(ecnOid);
	
	String workerId = StringUtils.stripToEmpty(prodCA.getWorkerId());
	    // 담당자가 set 되어 있지 않을 경우 생성하지 않는다.
	    if (!workerId.equals("")) {
		System.out.println("진입..7");
		WTUser ecaCharge = (WTUser) CommonUtil.getObject(workerId);
		if (ecaCharge != null) {
		    System.out.println("진입..8");
		    Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
		    WfTemplateObject paramWfTemplateObject = null;

		    while (allTemplates.hasMoreElements()) {
			System.out.println("진입..9");
			WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

			if (wfTemplate.getName().equals("KET_TODO_WF")) {
			    paramWfTemplateObject = wfTemplate;
			}
		    }
		    System.out.println("진입..10");
		    if (paramWfTemplateObject != null) {
			System.out.println("진입..11");
			Team team = (Team) prodCA.getTeamId().getObject();
			WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
			        prodCA.getContainerReference());
			ProcessData data = process.getContext();
			WfEngineServerHelper.service.setPrimaryBusinessObject(process, prodCA);
			data.setValue("charge", ecaCharge);
			WfEngineHelper.service.startProcess(process, data, 1);
		    }
		    System.out.println("진입..12");

		}
	    }
    }

    public void createEcnToDo(WTUser user, String ecoId) throws InvalidDataException, WTRuntimeException, WTException {
	ProdEcoBeans beans = new ProdEcoBeans();
	System.out.println("진입..");
	KETProdChangeOrder prodCO = (KETProdChangeOrder) beans.getEcoByNo(ecoId);
	System.out.println("진입..2");
	Object[] ecaObj = EcmUtil.getChangeActivities(prodCO);
	System.out.println("진입..3"+ecaObj.length);
	for (int index = 0; index < ecaObj.length; index++) {
	    System.out.println("진입..4");
	    KETProdChangeActivity prodCA = (KETProdChangeActivity) ecaObj[index];

	    // activity type??1,2????????(EPM, BOM)
	    if (!WorkflowSearchHelper.manager.IsRuninningTodo(prodCA)) {
		System.out.println("진입..5");
		if ((prodCA.getActivityType().equals("3")) || (prodCA.getActivityType().equals("4"))) // ???????????
		{
		    System.out.println("진입..6");
		    String workerId = StringUtils.stripToEmpty(prodCA.getWorkerId());
		    // 담당자가 set 되어 있지 않을 경우 생성하지 않는다.
		    if (!workerId.equals("")) {
			System.out.println("진입..7");
			WTUser ecaCharge = (WTUser) CommonUtil.getObject(workerId);
			if (ecaCharge != null) {
			    System.out.println("진입..8");
			    Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
			    WfTemplateObject paramWfTemplateObject = null;

			    while (allTemplates.hasMoreElements()) {
				System.out.println("진입..9");
				WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

				if (wfTemplate.getName().equals("KET_TODO_WF")) {
				    paramWfTemplateObject = wfTemplate;
				}
			    }
			    System.out.println("진입..10");
			    if (paramWfTemplateObject != null) {
				System.out.println("진입..11");
				Team team = (Team) prodCA.getTeamId().getObject();
				WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
				        prodCA.getContainerReference());
				ProcessData data = process.getContext();
				WfEngineServerHelper.service.setPrimaryBusinessObject(process, prodCA);
				data.setValue("charge", ecaCharge);
				WfEngineHelper.service.startProcess(process, data, 1);
			    }
			    System.out.println("진입..12");

			}
		    }
		}
	    }

	}
    }
}
