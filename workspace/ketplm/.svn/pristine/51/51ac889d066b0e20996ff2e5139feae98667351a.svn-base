package e3ps.wfm.util;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTObject;
import wt.lifecycle.State;
import wt.org.OrganizationServicesHelper;
import wt.org.WTPrincipal;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.pds.StatementSpec;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTRuntimeException;
import wt.workflow.engine.WfBlock;
import wt.workflow.engine.WfProcess;
import wt.workflow.engine.WfRequesterActivity;
import wt.workflow.engine.WfState;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WfAssignmentState;
import wt.workflow.work.WorkItem;
import e3ps.bom.entity.KETBomEcoHeader;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.ParamUtil;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.ecm.entity.KETChangeRequestExpansion;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETMoldECABomLink;
import e3ps.ecm.entity.KETMoldECAEpmDocLink;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.entity.KETProdECABomLink;
import e3ps.ecm.entity.KETProdECAEpmDocLink;
import e3ps.ews.entity.KETEarlyWarning;
import e3ps.ews.entity.KETEarlyWarningEnd;
import e3ps.ews.entity.KETEarlyWarningEndReq;
import e3ps.ews.entity.KETEarlyWarningEndReqLink;
import e3ps.ews.entity.KETEarlyWarningPlan;
import e3ps.ews.entity.KETEarlyWarningPlanLink;
import e3ps.ews.entity.KETEarlyWarningResult;
import e3ps.ews.entity.KETEarlyWarningResultLink;
import e3ps.ews.entity.KETEarlyWarningStep;
import e3ps.ews.entity.KETEarlyWarningStepLink;
import e3ps.ews.entity.KETEndReqLink;
import e3ps.project.ProductProject;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalLine;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.entity.KETWfmMasterHistoryLink;
import e3ps.wfm.entity.KETWfmMasterLineLink;
import e3ps.wfm.entity.KETWfmMasterPboLink;
import e3ps.wfm.entity.KETWfmMultiApprovalRequest;
import e3ps.wfm.entity.KETWfmMultiReqDocLink;
import e3ps.wfm.entity.KETWfmMultiReqEpmLink;
import e3ps.wfm.service.KETWfmHelper;
import ext.ket.dqm.entity.KETDqmTodoAtivity;
import ext.ket.sample.entity.KETSamplePart;
import ext.ket.shared.log.Kogger;

public class WorkflowSearchHelper implements wt.method.RemoteAccess, java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -294201496365887729L;

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public static WorkflowSearchHelper manager = new WorkflowSearchHelper();

    /**
     * 결재 이력 정보를 가져옴
     * 
     * @param master
     *            결재마스터 입력
     * @return
     * @throws WTException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Vector getApprovalHistory(KETWfmApprovalMaster master) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { KETWfmApprovalMaster.class };
	    Object args[] = new Object[] { master };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getApprovalHistory", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (Vector) obj;
	}

	Vector vec = new Vector();

	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(master, "history", KETWfmMasterHistoryLink.class);

	    while (qr.hasMoreElements()) {
		KETWfmApprovalHistory history = (KETWfmApprovalHistory) qr.nextElement();
		vec.addElement(history);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return vec;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Vector getApprovalHistory(KETWfmApprovalMaster master, int degree) throws Exception {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { KETWfmApprovalMaster.class };
	    Object args[] = new Object[] { master };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getApprovalHistory", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (Vector) obj;
	}

	Vector vec = new Vector();

	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(master, "history", KETWfmMasterHistoryLink.class);

	    while (qr.hasMoreElements()) {
		KETWfmApprovalHistory history = (KETWfmApprovalHistory) qr.nextElement();
		if (degree > 0 && history.getDegree() != degree) {
		    continue;
		}
		vec.addElement(history);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return vec;
    }

    /**
     * PBO의 결재 마스터를 가져 옴
     * 
     * @param per
     *            PBO
     * @return
     * @throws WTException
     */
    public KETWfmApprovalMaster getMaster(Persistable per) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Persistable.class };
	    Object args[] = new Object[] { per };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getMaster", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (KETWfmApprovalMaster) obj;
	}

	KETWfmApprovalMaster master = null;

	try {
	    if (per != null) {
		QueryResult qr = PersistenceHelper.manager.navigate(per, "appMaster", KETWfmMasterPboLink.class);

		if (qr.size() > 0) {
		    while (qr.hasMoreElements()) {
			master = (KETWfmApprovalMaster) qr.nextElement();
		    }
		} else if (CommonUtil.getVROID(per) != null) {
		    QuerySpec spec = new QuerySpec();
		    Class targetClass = KETWfmApprovalMaster.class;
		    int masterIdx = spec.addClassList(targetClass, true);

		    spec.appendWhere(
			    new SearchCondition(targetClass, "businessobjectRef.key.classname", SearchCondition.EQUAL, CommonUtil
			            .getVROID(per)), new int[] { masterIdx });
		    qr = PersistenceHelper.manager.find((StatementSpec) spec);

		    while (qr.hasMoreElements()) {
			Object[] obj = (Object[]) qr.nextElement();
			master = (KETWfmApprovalMaster) obj[0];
		    }
		}
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return master;
    }

    /**
     * 지정된 결재선 정보를 가져옴
     * 
     * @param master
     *            결재마스터 입력
     * @return
     * @throws WTException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Vector getApprovalLine(KETWfmApprovalMaster master) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { KETWfmApprovalMaster.class };
	    Object args[] = new Object[] { master };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getApprovalLine", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (Vector) obj;
	}

	Vector vec = new Vector();

	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(master, "line", KETWfmMasterLineLink.class);

	    while (qr.hasMoreElements()) {
		KETWfmApprovalLine line = (KETWfmApprovalLine) qr.nextElement();
		vec.addElement(line);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return vec;
    }

    /**
     * 결재마스터와 연결된 Workflow Process 를 가져옴 (1:1)
     * 
     * @param master
     *            결재마스터 입력
     * @return
     * @throws WTException
     */
    public WfProcess getProcess(KETWfmApprovalMaster master) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { KETWfmApprovalMaster.class };
	    Object args[] = new Object[] { master };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getProcess", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (WfProcess) obj;
	}

	WfProcess process = null;

	try {
	    QuerySpec spec = new QuerySpec();
	    Class targetClass = WfProcess.class;
	    int processIdx = spec.addClassList(targetClass, true);
	    spec.appendWhere(new SearchCondition(targetClass, "businessObjReference", SearchCondition.EQUAL, master.getBusinessobjectRef()
		    .toString()), new int[] { processIdx });

	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(targetClass, "name", SearchCondition.NOT_EQUAL, "KET_TODO_WF"), new int[] { processIdx });

	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(targetClass, "state", SearchCondition.EQUAL, WfState.OPEN_RUNNING),
		    new int[] { processIdx });

	    spec.appendOrderBy(new OrderBy(new ClassAttribute(targetClass, "thePersistInfo.createStamp"), true), new int[] { processIdx });

	    QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);

	    while (result.hasMoreElements()) {
		Object[] obj = (Object[]) result.nextElement();
		process = (WfProcess) obj[0];
		break;
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return process;
    }

    /**
     * 해당 pbo가 승인완료 상태일때 사용가능
     * 
     * @param pbo
     *            Primarybusinessobject
     * @return
     * @throws WTException
     */
    public WTUser getLastApprover(Persistable pbo) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Persistable.class };
	    Object args[] = new Object[] { pbo };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getLastApprover", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (WTUser) obj;
	}

	WTUser lastApprover = null;
	KETWfmApprovalMaster master = getMaster(pbo);

	if (master != null) {
	    try {
		QuerySpec spec = new QuerySpec();
		Class targetClass = KETWfmApprovalHistory.class;
		int historyIdx = spec.addClassList(targetClass, true);

		spec.appendWhere(
		        new SearchCondition(targetClass, "appMasterReference.key.id", SearchCondition.EQUAL, CommonUtil
		                .getOIDLongValue(master)), new int[] { historyIdx });

		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(targetClass, "completedDate", SearchCondition.NOT_NULL, true),
		        new int[] { historyIdx });

		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(targetClass, "last", SearchCondition.IS_TRUE), new int[] { historyIdx });

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);
		// logger.info(spec);

		while (result.hasMoreElements()) {
		    Object[] obj = (Object[]) result.nextElement();
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) obj[0];
		    String activityName = StringUtil.checkNull(history.getActivityName());
		    if (activityName.equals("검토") && history.isLast()) {
			if (ParamUtil.checkStrParameter(history.getDecision(), "&nbsp;").equals("승인")) {
			    lastApprover = OrganizationServicesHelper.manager.getUser(history.getOwner().getName());
			}
		    }

		}
	    } catch (QueryException e) {
		Kogger.error(getClass(), e);
	    } catch (WTException e) {
		Kogger.error(getClass(), e);
	    }
	}

	return lastApprover;
    }

    /**
     * 해당 pbo가 승인완료 이전 결재대기함에서 해당 사용자가 검토인지 승인인지의 여부를 파악하기 위해 사용
     * 
     * @param pbo
     *            Primarybusinessobject
     * @return
     * @throws WTException
     */
    public WTUser getLastUser(Persistable pbo) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Persistable.class };
	    Object args[] = new Object[] { pbo };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getLastUser", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (WTUser) obj;
	}

	WTUser lastApprover = null;
	KETWfmApprovalMaster master = getMaster(pbo);

	if (master != null) {
	    try {
		QuerySpec spec = new QuerySpec();
		Class targetClass = KETWfmApprovalHistory.class;
		int historyIdx = spec.addClassList(targetClass, true);

		spec.appendWhere(
		        new SearchCondition(targetClass, "appMasterReference.key.id", SearchCondition.EQUAL, CommonUtil
		                .getOIDLongValue(master)), new int[] { historyIdx });

		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(targetClass, "completedDate", SearchCondition.IS_NULL, true), new int[] { historyIdx });

		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(targetClass, "last", SearchCondition.IS_TRUE), new int[] { historyIdx });

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);
		// logger.info(spec.toString());

		while (result.hasMoreElements()) {
		    Object[] obj = (Object[]) result.nextElement();
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) obj[0];
		    lastApprover = OrganizationServicesHelper.manager.getUser(history.getOwner().getName());
		}
	    } catch (QueryException e) {
		Kogger.error(getClass(), e);
	    } catch (WTException e) {
		Kogger.error(getClass(), e);
	    }
	}

	return lastApprover;
    }

    /**
     * KETWfmApprovalMaster를 입력받아 결재선의 승인자 정보를 리턴한다.
     * 
     * @param master
     * @return
     * @throws WTException
     * @메소드명 : getLastUser
     * @작성자 : Jason, Han
     * @작성일 : 2014. 8. 29.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public WTUser getLastUser(KETWfmApprovalMaster master) throws WTException {

	WTUser lastApprover = null;
	try {
	    QuerySpec spec = new QuerySpec();
	    Class<KETWfmApprovalHistory> targetClass = KETWfmApprovalHistory.class;
	    int historyIdx = spec.addClassList(targetClass, true);
	    spec.appendWhere(
		    new SearchCondition(targetClass, "appMasterReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(master)),
		    new int[] { historyIdx });
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(targetClass, "completedDate", SearchCondition.IS_NULL, true), new int[] { historyIdx });
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(targetClass, "last", SearchCondition.IS_TRUE), new int[] { historyIdx });
	    QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);
	    if (result.hasMoreElements()) {
		Object[] obj = (Object[]) result.nextElement();
		KETWfmApprovalHistory history = (KETWfmApprovalHistory) obj[0];
		lastApprover = (WTUser) history.getOwner().getPrincipal();
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return lastApprover;
    }

    /**
     * 해당 객체가 승인완료 상태일때 사용가능 yyyy-MM-dd HH:mm:ss 포맷으로 리턴
     * 
     * @param pbo
     *            PrimaryBusinessObject
     * @return
     * @throws WTException
     */
    public String getLastApprovalDate(Persistable pbo) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Persistable.class };
	    Object args[] = new Object[] { pbo };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getLastApprovalDate", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (String) obj;
	}

	String lastDate = "&nbsp;";

	KETWfmApprovalMaster master = getMaster(pbo);

	if (master != null) {
	    if (master.getCompletedDate() != null) {
		lastDate = DateUtil.getTimeFormat(master.getCompletedDate(), "yyyy-MM-dd HH:mm:ss");
	    }
	}

	return lastDate;
    }

    /**
     * 최종승인자 코멘트
     * 
     * @param pbo
     * @return
     * @throws WTException
     */
    public String getLastApprovalComment(Persistable pbo) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Persistable.class };
	    Object args[] = new Object[] { pbo };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getLastApprovalComment", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (String) obj;
	}

	String lastComment = "";
	KETWfmApprovalMaster master = getMaster(pbo);

	if (master != null) {
	    try {
		boolean isDRR = false;

		if (pbo instanceof KETDevelopmentRequest)
		    isDRR = true;

		QuerySpec spec = new QuerySpec();
		Class targetClass = KETWfmApprovalHistory.class;
		int historyIdx = spec.addClassList(targetClass, true);

		spec.appendWhere(
		        new SearchCondition(targetClass, "appMasterReference.key.id", SearchCondition.EQUAL, CommonUtil
		                .getOIDLongValue(master)), new int[] { historyIdx });

		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(targetClass, "completedDate", SearchCondition.NOT_NULL, true),
		        new int[] { historyIdx });

		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(targetClass, "last", SearchCondition.IS_TRUE), new int[] { historyIdx });

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);

		while (result.hasMoreElements()) {
		    Object[] obj = (Object[]) result.nextElement();
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) obj[0];
		    lastComment = ParamUtil.checkStrParameter(history.getComments(), "");
		}
	    } catch (QueryException e) {
		Kogger.error(getClass(), e);
	    } catch (WTException e) {
		Kogger.error(getClass(), e);
	    }
	}

	return lastComment;
    }

    /**
     * 반려자 확인
     * 
     * @param pbo
     * @return rejUser
     * @throws WTException
     */
    public WTPrincipalReference getApprovalRejectUser(Persistable pbo) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Persistable.class };
	    Object args[] = new Object[] { pbo };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getApprovalRejectUser", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (WTPrincipalReference) obj;
	}

	WTPrincipalReference rejUser = null;

	KETWfmApprovalMaster master = getMaster(pbo);
	String reject_p = WFMProperties.getInstance().getString("wfm.reject");

	if (master != null) {
	    try {
		QuerySpec spec = new QuerySpec();
		Class targetClass = KETWfmApprovalHistory.class;
		int historyIdx = spec.addClassList(targetClass, true);

		spec.appendWhere(
		        new SearchCondition(targetClass, "appMasterReference.key.id", SearchCondition.EQUAL, CommonUtil
		                .getOIDLongValue(master)), new int[] { historyIdx });

		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(targetClass, "completedDate", SearchCondition.NOT_NULL, true),
		        new int[] { historyIdx });

		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(targetClass, "decision", SearchCondition.EQUAL, reject_p), new int[] { historyIdx });

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);

		while (result.hasMoreElements()) {
		    Object[] obj = (Object[]) result.nextElement();
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) obj[0];
		    rejUser = history.getOwner();
		}
	    } catch (QueryException e) {
		Kogger.error(getClass(), e);
	    } catch (WTException e) {
		Kogger.error(getClass(), e);
	    }
	}

	return rejUser;
    }

    /**
     * 일괄결재 요청서에 연결된 EPMDocument를 가져옴
     * 
     * @param multiReq
     *            일괄결재요청서
     * @return
     * @throws WTException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Vector<EPMDocument> getEPMList(KETWfmMultiApprovalRequest multiReq) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { KETWfmMultiApprovalRequest.class };
	    Object args[] = new Object[] { multiReq };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getEPMList", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (Vector<EPMDocument>) obj;
	}

	Vector<EPMDocument> returnVec = new Vector<EPMDocument>();

	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(multiReq, "epmDoc", KETWfmMultiReqEpmLink.class, false);

	    while (qr.hasMoreElements()) {
		KETWfmMultiReqEpmLink link = (KETWfmMultiReqEpmLink) qr.nextElement();
		returnVec.addElement(link.getEpmDoc());
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return returnVec;
    }

    /**
     * 산출물 일괄결재 요청서에 연결된 Document를 가져옴
     * 
     * @param multiReq
     *            산출물 일괄결재요청서
     * @return
     * @throws WTException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Vector<WTDocument> getDocList(KETWfmMultiApprovalRequest multiReq) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { KETWfmMultiApprovalRequest.class };
	    Object args[] = new Object[] { multiReq };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getDocList", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (Vector<WTDocument>) obj;
	}

	Vector<WTDocument> returnVec = new Vector<WTDocument>();

	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(multiReq, "doc", KETWfmMultiReqDocLink.class, false);

	    while (qr.hasMoreElements()) {
		KETWfmMultiReqDocLink link = (KETWfmMultiReqDocLink) qr.nextElement();
		returnVec.addElement(link.getDoc());
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return returnVec;
    }

    /**
     * 금형ECA에 연결된 BOM 정보를 가져옴
     * 
     * @param moldCA
     *            금형ECA
     * @return
     * @throws WTException
     */
    public Vector<KETBomEcoHeader> getBOMfromMoldECA(KETMoldChangeActivity moldCA) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { KETMoldChangeActivity.class };
	    Object args[] = new Object[] { moldCA };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getBOMfromMoldECA", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (Vector<KETBomEcoHeader>) obj;
	}

	Vector<KETBomEcoHeader> returnVec = new Vector<KETBomEcoHeader>();

	try {
	    QueryResult qrLink = PersistenceHelper.manager.navigate(moldCA, "bom", KETMoldECABomLink.class);

	    while (qrLink.hasMoreElements()) {
		KETBomEcoHeader bomHeader = (KETBomEcoHeader) qrLink.nextElement();
		returnVec.addElement(bomHeader);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return returnVec;
    }

    /**
     * 금형 ECA에 연결된 EPMDocument를 가져옴
     * 
     * @param moldCA
     *            금형ECA
     * @return
     * @throws WTException
     */
    public Vector<EPMDocument> getEPMfromMoldECA(KETMoldChangeActivity moldCA) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { KETMoldChangeActivity.class };
	    Object args[] = new Object[] { moldCA };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getEPMfromMoldECA", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (Vector<EPMDocument>) obj;
	}

	Vector<EPMDocument> returnVec = new Vector<EPMDocument>();

	try {
	    QueryResult qrLink = PersistenceHelper.manager.navigate(moldCA, "epmDoc", KETMoldECAEpmDocLink.class);

	    while (qrLink.hasMoreElements()) {
		EPMDocument epmDoc = (EPMDocument) qrLink.nextElement();
		returnVec.addElement(epmDoc);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return returnVec;
    }

    /**
     * 제품ECA에 연결된 BOM을 가져옴
     * 
     * @param prodCA
     *            제품ECA
     * @return
     * @throws WTException
     */
    public Vector<KETBomEcoHeader> getBOMfromProdECA(KETProdChangeActivity prodCA) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { KETProdChangeActivity.class };
	    Object args[] = new Object[] { prodCA };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getBOMfromProdECA", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (Vector<KETBomEcoHeader>) obj;
	}

	Vector<KETBomEcoHeader> returnVec = new Vector<KETBomEcoHeader>();

	try {
	    QueryResult qr = null;
	    qr = PersistenceHelper.manager.navigate(prodCA, "bom", KETProdECABomLink.class);

	    while (qr.hasMoreElements()) {
		KETBomEcoHeader bomHeader = (KETBomEcoHeader) qr.nextElement();
		returnVec.addElement(bomHeader);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return returnVec;
    }

    /**
     * 제품ECA에 연결된 EPMDocument를 가져옴
     * 
     * @param prodCA
     *            제품ECA
     * @return
     * @throws WTException
     */
    public Vector<EPMDocument> getEPMfromProdECA(KETProdChangeActivity prodCA) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { KETProdChangeActivity.class };
	    Object args[] = new Object[] { prodCA };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getEPMfromProdECA", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (Vector<EPMDocument>) obj;
	}

	Vector<EPMDocument> returnVec = new Vector<EPMDocument>();

	try {
	    QueryResult qrLink;
	    qrLink = PersistenceHelper.manager.navigate(prodCA, "epmDoc", KETProdECAEpmDocLink.class);

	    while (qrLink.hasMoreElements()) {
		EPMDocument epmDoc = (EPMDocument) qrLink.nextElement();
		returnVec.addElement(epmDoc);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return returnVec;
    }

    /**
     * 초기유동관리의 결재마스터를 가져옴
     * 
     * @param ewObj
     *            입력값은 KETEarlyWarning/KETEarlyWarningPlan/KETEarlyWarningResult/ KETEarlyWarningEndReq/KETEarlyWarningEnd 이어야함
     * @return
     * @throws WTException
     */
    public KETEarlyWarningStep getEWStep(Object ewObj) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Object.class };
	    Object args[] = new Object[] { ewObj };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getEWStep", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (KETEarlyWarningStep) obj;
	}

	KETEarlyWarningStep step = null;

	try {
	    WTDocumentMaster master = new WTDocumentMaster();
	    QueryResult qrLink = null;

	    if (ewObj instanceof KETEarlyWarning) {
		KETEarlyWarning ew = (KETEarlyWarning) ewObj;
		qrLink = PersistenceHelper.manager.navigate(ew.getMaster(), "earlyWarningstep", KETEarlyWarningStepLink.class, false);

		while (qrLink.hasMoreElements()) {
		    KETEarlyWarningStepLink stepLink = (KETEarlyWarningStepLink) qrLink.nextElement();
		    master = stepLink.getEarlyWarningstep();
		}

		step = (KETEarlyWarningStep) ObjectUtil.getLatestObject(master);
	    } else if (ewObj instanceof KETEarlyWarningEnd) {
		KETEarlyWarningEnd ewEnd = (KETEarlyWarningEnd) ewObj;
		qrLink = PersistenceHelper.manager.navigate(ewEnd.getMaster(), "endReq", KETEndReqLink.class, false);

		while (qrLink.hasMoreElements()) {
		    KETEndReqLink endLink = (KETEndReqLink) qrLink.nextElement();
		    master = endLink.getEndReq();
		}

		KETEarlyWarningEndReq ewEndReq = (KETEarlyWarningEndReq) ObjectUtil.getLatestObject(master);
		step = getEWStep(ewEndReq);
	    } else if (ewObj instanceof KETEarlyWarningEndReq) {
		KETEarlyWarningEndReq ewEndReq = (KETEarlyWarningEndReq) ewObj;
		qrLink = PersistenceHelper.manager.navigate(ewEndReq.getMaster(), "earlyWarning", KETEarlyWarningEndReqLink.class, false);

		while (qrLink.hasMoreElements()) {
		    KETEarlyWarningEndReqLink endReqLink = (KETEarlyWarningEndReqLink) qrLink.nextElement();
		    master = endReqLink.getEarlyWarning();
		}

		KETEarlyWarning ew = (KETEarlyWarning) ObjectUtil.getLatestObject(master);
		step = getEWStep(ew);
	    } else if (ewObj instanceof KETEarlyWarningPlan) {
		KETEarlyWarningPlan ewPlan = (KETEarlyWarningPlan) ewObj;
		qrLink = PersistenceHelper.manager.navigate(ewPlan.getMaster(), "earlyWarning", KETEarlyWarningPlanLink.class, false);

		while (qrLink.hasMoreElements()) {
		    KETEarlyWarningPlanLink planLink = (KETEarlyWarningPlanLink) qrLink.nextElement();
		    master = planLink.getEarlyWarning();
		}

		KETEarlyWarning ew = (KETEarlyWarning) ObjectUtil.getLatestObject(master);
		step = getEWStep(ew);
	    } else if (ewObj instanceof KETEarlyWarningResult) {
		KETEarlyWarningResult ewResult = (KETEarlyWarningResult) ewObj;
		qrLink = PersistenceHelper.manager.navigate(ewResult.getMaster(), "earlyWarning", KETEarlyWarningResultLink.class, false);

		while (qrLink.hasMoreElements()) {
		    KETEarlyWarningResultLink resultLink = (KETEarlyWarningResultLink) qrLink.nextElement();
		    master = resultLink.getEarlyWarning();
		}

		KETEarlyWarning ew = (KETEarlyWarning) ObjectUtil.getLatestObject(master);
		step = getEWStep(ew);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return step;
    }

    /**
     * 초기유동관리지정값을 가져옴
     * 
     * @param ewObj
     *            입력값은 KETEarlyWarningPlan/KETEarlyWarningResult/KETEarlyWarningEndReq/ KETEarlyWarningEnd 4가지 임
     * @return
     * @throws WTException
     */
    public KETEarlyWarning getEW(Object ewObj) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Object.class };
	    Object args[] = new Object[] { ewObj };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getEW", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (KETEarlyWarning) obj;
	}

	KETEarlyWarning retew = null;

	try {
	    WTDocumentMaster master = new WTDocumentMaster();
	    QueryResult qrLink = null;

	    if (ewObj instanceof KETEarlyWarningEnd) {
		KETEarlyWarningEnd ewEnd = (KETEarlyWarningEnd) ewObj;
		qrLink = PersistenceHelper.manager.navigate(ewEnd.getMaster(), "endReq", KETEndReqLink.class, false);

		while (qrLink.hasMoreElements()) {
		    KETEndReqLink endLink = (KETEndReqLink) qrLink.nextElement();
		    master = endLink.getEndReq();
		}

		KETEarlyWarningEndReq ewEndReq = (KETEarlyWarningEndReq) ObjectUtil.getLatestObject(master);
		retew = getEW(ewEndReq);
	    } else if (ewObj instanceof KETEarlyWarningEndReq) {
		KETEarlyWarningEndReq ewEndReq = (KETEarlyWarningEndReq) ewObj;
		qrLink = PersistenceHelper.manager.navigate(ewEndReq.getMaster(), "earlyWarning", KETEarlyWarningEndReqLink.class, false);

		while (qrLink.hasMoreElements()) {
		    KETEarlyWarningEndReqLink endReqLink = (KETEarlyWarningEndReqLink) qrLink.nextElement();
		    master = endReqLink.getEarlyWarning();
		}

		retew = (KETEarlyWarning) ObjectUtil.getLatestObject(master);
	    } else if (ewObj instanceof KETEarlyWarningPlan) {
		KETEarlyWarningPlan ewPlan = (KETEarlyWarningPlan) ewObj;
		qrLink = PersistenceHelper.manager.navigate(ewPlan.getMaster(), "earlyWarning", KETEarlyWarningPlanLink.class, false);

		while (qrLink.hasMoreElements()) {
		    KETEarlyWarningPlanLink planLink = (KETEarlyWarningPlanLink) qrLink.nextElement();
		    master = planLink.getEarlyWarning();
		}

		retew = (KETEarlyWarning) ObjectUtil.getLatestObject(master);
	    } else if (ewObj instanceof KETEarlyWarningResult) {
		KETEarlyWarningResult ewResult = (KETEarlyWarningResult) ewObj;
		qrLink = PersistenceHelper.manager.navigate(ewResult.getMaster(), "earlyWarning", KETEarlyWarningResultLink.class, false);

		while (qrLink.hasMoreElements()) {
		    KETEarlyWarningResultLink resultLink = (KETEarlyWarningResultLink) qrLink.nextElement();
		    master = resultLink.getEarlyWarning();
		}

		retew = (KETEarlyWarning) ObjectUtil.getLatestObject(master);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return retew;
    }

    /**
     * 도면검색화면에서 사용되어짐 검색결과로 출력되는 도면이 현재 일괄결재요청서에 연결된 도면인지의 여부를 알려준다. result:false -> 현재 일괄결재서에 연결되어 있지 않은 도면임 result:true - > 현재 일괄결재서에 연결되어
     * 있는 도면임
     * 
     * @param epm
     * @param multiReqOid
     * @return
     * @throws WTException
     */
    public boolean IsInMultiApproval(EPMDocument epm, String multiReqOid) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { EPMDocument.class, String.class };
	    Object args[] = new Object[] { epm, multiReqOid };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("IsInMultiApproval", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return ((Boolean) obj).booleanValue();
	}

	boolean result = true;
	KETWfmMultiApprovalRequest multiReq = null;

	if (!multiReqOid.equals(""))
	    multiReq = (KETWfmMultiApprovalRequest) CommonUtil.getObject(multiReqOid);

	QueryResult qr = PersistenceHelper.manager.navigate(epm, "request", KETWfmMultiReqEpmLink.class, false);

	if (qr.size() > 0) {
	    while (qr.hasMoreElements()) {
		KETWfmMultiReqEpmLink link = (KETWfmMultiReqEpmLink) qr.nextElement();
		KETWfmMultiApprovalRequest compareMulti = (KETWfmMultiApprovalRequest) link.getRequest();
		// Kogger.debug(getClass(), "@@@@@@@@ CompareMulti: "+ compareMulti);
		// Kogger.debug(getClass(), "@@@@@@@@ MultiReq: "+ multiReq);
		State cstate = compareMulti.getState().getState();

		if ((cstate != State.toState("APPROVED")) && (!compareMulti.toString().equals(multiReq.toString()))) {
		    result = true;
		    break;
		} else {
		    result = false;
		}
	    }
	} else {
	    result = false;
	}

	return result;
    }

    /**
     * 초기유동 및 설계변경에서 작업활동 수행을 위하여 발행된 WorkItem을 종료시키는 함수 라우팅 조건값은 없으며 단순 종료 시킬때 사용된다 입력값은
     * KETEarlyWarning/KETEarlyWarningPlan/KETEarlyWarningResult/KETEarlyWarningEndReq/
     * KETMoldChangeActivity/KETProdChangeActivity/KETMoldChangeOrder/KETProdChangeOrder가 해당됨
     * 
     * @param target
     * @throws WTException
     */
    public void taskComplete(Persistable target) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Persistable.class };
	    Object args[] = new Object[] { target };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("taskComplete", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }
	}

	Object pbo = null;
	KETEarlyWarningStep tstep = null;
	KETEarlyWarningStep pstep = null;

	if ((target instanceof KETEarlyWarning) || (target instanceof KETEarlyWarningPlan) || (target instanceof KETEarlyWarningResult)
	        || (target instanceof KETEarlyWarningEndReq)) {
	    tstep = getEWStep(target);
	}

	QuerySpec spec = new QuerySpec();
	int itemIdx = spec.addClassList(WorkItem.class, true);
	WTPrincipal user = SessionHelper.manager.getPrincipal();

	if ((target instanceof KETDqmTodoAtivity) || (target instanceof KETSamplePart) || (target instanceof KETChangeRequestExpansion)) {
	    // KETDqmTodoAtivity, KETSamplePart, KETChangeRequestExpansion 객체는 세션 체크 안하고 객체로 거른다.
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(
		    new SearchCondition(WorkItem.class, "primaryBusinessObject.key.classname", SearchCondition.EQUAL, CommonUtil
		            .getRefOID(target)), new int[] { itemIdx });
	} else {
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(
		    new SearchCondition(WorkItem.class, "ownership.owner.key", SearchCondition.EQUAL, PersistenceHelper
		            .getObjectIdentifier(user)), new int[] { itemIdx });
	}

	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(WorkItem.class, WorkItem.STATUS, SearchCondition.EQUAL, "POTENTIAL"), new int[] { itemIdx });

	spec.appendOrderBy(new OrderBy(new ClassAttribute(WorkItem.class, "thePersistInfo.createStamp"), true), new int[] { itemIdx });

	QueryResult qr = PersistenceHelper.manager.find(spec);

	while (qr.hasMoreElements()) {
	    try {
		Object[] obj = (Object[]) qr.nextElement();
		WorkItem item = (WorkItem) obj[0];

		try {
		    pbo = item.getPrimaryBusinessObject().getObject();
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}

		if ((target instanceof KETEarlyWarning) || (target instanceof KETEarlyWarningPlan)
		        || (target instanceof KETEarlyWarningResult) || (target instanceof KETEarlyWarningEndReq)) {
		    pstep = getEWStep(pbo);

		    if (tstep.equals(pstep)) {
			// 2011.03.15 kkh start
			if (target instanceof KETEarlyWarningPlan) {
			    if (pbo instanceof KETEarlyWarning) {
				Hashtable aHash = new Hashtable();
				aHash.put("item", item);
				aHash.put("condition", "taskComplete");
				aHash.put("master", "");
				aHash.put("comment", "");
				KETWfmHelper.service.completeActivity(aHash);
			    }
			} else {
			    Hashtable aHash = new Hashtable();
			    aHash.put("item", item);
			    aHash.put("condition", "taskComplete");
			    aHash.put("master", "");
			    aHash.put("comment", "");
			    KETWfmHelper.service.completeActivity(aHash);
			}
			// 2011.03.15 kkh end
		    }
		} else {
		    if (pbo.equals(target)) {
			Hashtable aHash = new Hashtable();
			aHash.put("item", item);
			aHash.put("condition", "taskComplete");
			aHash.put("master", "");
			aHash.put("comment", "");

			KETWfmHelper.service.completeActivity(aHash);

		    }
		}
	    } catch (WTRuntimeException runtimeE) {
		continue;
	    }
	}
    }

    /**
     * 입력된 사용자의 아이디로 해당 객체의 결재 참여자인지를 판단하는 함수 isTrue : false -> 입력된 userId는 target 객체의 결재 참여자가 아님 isTrue : true -> 입력된 userId는 target 객체의 결재
     * 참여자임
     * 
     * @param target
     *            결재참여자를 확인할 객체
     * @param userId
     *            결재참여자인지 확인하기 위한 유저의 아이디
     * @return
     * @throws WTException
     */
    public boolean userInApproval(Persistable target, String userId) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Persistable.class, String.class };
	    Object args[] = new Object[] { target, userId };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("userInApproval", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return ((Boolean) obj).booleanValue();
	}

	boolean isTrue = false;
	KETWfmApprovalMaster master = (KETWfmApprovalMaster) getMaster(target);

	if (master != null) {
	    Vector lineVec = getApprovalLine(master);

	    for (int i = 0; i < lineVec.size(); i++) {
		KETWfmApprovalLine line = (KETWfmApprovalLine) lineVec.get(i);

		if (line.getApproverID().equals(userId))
		    isTrue = true;
	    }
	}

	return isTrue;
    }

    /**
     * 현재는 입력 객체가 ECM을 대상으로 구현한 함수임 설계변경에서 작업활동을 위한 WorkItem이 발행될때 WorkItem의 Status와 관계없이 입력 객체가 WorkItem 테이블 내에 Primarybusinessobject로
     * 존재하는지의 유무를 알려줌 isRunning : false -> 입력객체를 가지고 있는 WorkItem이 없으므로 신규로 WorkItem을 발행해야 되는 입력객체임 isRunning : true -> 입력객체를 가지고 있는
     * WorkItem이 존재하므로 WorkItem을 더 이상 발행하지 않아야 되는 입력객체임
     * 
     * @param target
     * @return
     * @throws WTException
     */
    public boolean IsRuninningTodo(Persistable target) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Persistable.class };
	    Object args[] = new Object[] { target };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("IsRuninningTodo", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return ((Boolean) obj).booleanValue();
	}

	boolean isRunning = false;

	QuerySpec spec = new QuerySpec();
	ReferenceFactory rf = new ReferenceFactory();
	String refOid = rf.getReferenceString(target);

	int itemIdx = spec.addClassList(WorkItem.class, true);

	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(WorkItem.class, "primaryBusinessObject.key.classname", SearchCondition.EQUAL, refOid),
	        new int[] { itemIdx });
	spec.appendOrderBy(new OrderBy(new ClassAttribute(WorkItem.class, "thePersistInfo.createStamp"), true), new int[] { itemIdx });

	QueryResult qr = PersistenceHelper.manager.find(spec);

	if (qr.size() > 0) {
	    isRunning = true;
	}

	return isRunning;
    }

    /**
     * 현재는 입력 객체가 ECM,초기유동을 대상으로 구현한 함수임 입력객체가 반려되었을때 호출되는 함수로 재작업을 위해 발행되었던 WorkItem을 모두 삭제함
     * 
     * @param target
     * @return
     * @throws WTException
     */
    public QueryResult getCompletedTodoList(Persistable target) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Persistable.class };
	    Object args[] = new Object[] { target };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getCompletedTodoList", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (QueryResult) obj;
	}

	QueryResult qr = new QueryResult();
	ReferenceFactory rf = new ReferenceFactory();
	String refOid = rf.getReferenceString(target);
	QuerySpec spec = new QuerySpec();

	int itemIdx = spec.addClassList(WorkItem.class, true);

	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(WorkItem.class, "primaryBusinessObject.key.classname", SearchCondition.EQUAL, refOid),
	        new int[] { itemIdx });

	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(WorkItem.class, "status", SearchCondition.EQUAL, "COMPLETED"), new int[] { itemIdx });
	spec.appendOrderBy(new OrderBy(new ClassAttribute(WorkItem.class, "thePersistInfo.createStamp"), true), new int[] { itemIdx });

	qr = PersistenceHelper.manager.find(spec);

	return qr;
    }

    /**
     * 입력값으로 들어온 사용자의 현재 위임자를 찾아내는 함수 현재 위임자가 없을때 널로 리턴 현재 위임자가 있을때 WTPrincipalReference 타입으로 리턴함
     * 
     * @param prinRef
     *            위임자가 존재하는지 확인하기 위한 사용자의 WTPrincipalReference
     * @return
     * @throws WTException
     */
    public WTPrincipalReference getDelegate(WTPrincipalReference prinRef) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { WTPrincipalReference.class };
	    Object args[] = new Object[] { prinRef };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getDelegate", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }

	    return (WTPrincipalReference) obj;
	}

	WTPrincipalReference delegatePrinRef = null;

	Calendar localCalendar1 = Calendar.getInstance();
	Calendar localCalendar2 = Calendar.getInstance();
	Calendar localCalendar3 = Calendar.getInstance();

	try {
	    localCalendar2.clear();
	    localCalendar3.clear();
	    localCalendar2.set(localCalendar1.get(1), localCalendar1.get(2), localCalendar1.get(5), 0, 0, 0);
	    localCalendar3.set(localCalendar1.get(1), localCalendar1.get(2), localCalendar1.get(5), 23, 59, 59);
	    // CalendarServiceFwd calService = new CalendarServiceFwd();
	    // delegatePrinRef = calService.getDelegate(prinRef, localCalendar2.getTime().getTime(), localCalendar3.getTime().getTime());
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return delegatePrinRef;
    }

    /**
     * 반려확인 task에서 재작업 버튼을 눌렀을때 실행되는 메소드 workflow의 block 프로세스내에 있는 WfAssignedActivity 를 모두 삭제한다. (OOTB 구문에서 연관된 WfAssignment/WorkItem 함께
     * 삭제되어짐)
     * 
     * @param process
     * @throws WTException
     */
    public void removeWfAssignedActivity(WfProcess process) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { WfProcess.class };
	    Object args[] = new Object[] { process };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("removeWfAssignedActivity", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }
	}

	QueryResult result = process.getContainerNodes();

	while (result.hasMoreElements()) {
	    WTObject nodeObj = (WTObject) result.nextElement();

	    if (nodeObj instanceof WfRequesterActivity) // 블록 프로세스 필터링
	    {
		WfRequesterActivity reqActivity = (WfRequesterActivity) nodeObj;
		WfBlock block = (WfBlock) reqActivity.getPerformerRef().getObject();
		QueryResult resultBlock = block.getContainerNodes();

		while (resultBlock.hasMoreElements()) {
		    WTObject blockObj = (WTObject) resultBlock.nextElement();

		    if (blockObj instanceof WfAssignedActivity) {
			WfAssignedActivity activity = (WfAssignedActivity) blockObj;
			activity.deleteActivity();
		    }
		}
	    }
	}
    }

    /**
     * 현재는 입력 객체가 ECM을 대상으로 구현한 함수임 ECA가 INWORK 상태이고 삭제될 때 call 되는 메소드
     * 
     * @param target
     * @throws WTException
     */
    public void deleteWorkItem(Persistable target) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Persistable.class };
	    Object args[] = new Object[] { target };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("deleteWorkItem", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }
	}

	SessionContext sessioncontext = SessionContext.newContext();
	try {
	    SessionHelper.manager.setAdministrator();

	    Object pbo = null;
	    QuerySpec spec = new QuerySpec();
	    ReferenceFactory rf = new ReferenceFactory();
	    String refOid = rf.getReferenceString(target);

	    // Kogger.debug(getClass(), "*************객체의 참조 OID: "+refOid);

	    int itemIdx = spec.addClassList(WorkItem.class, true);

	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(WorkItem.class, "primaryBusinessObject.key.classname", SearchCondition.EQUAL, refOid),
		    new int[] { itemIdx });
	    spec.appendOrderBy(new OrderBy(new ClassAttribute(WorkItem.class, "thePersistInfo.createStamp"), true), new int[] { itemIdx });

	    QueryResult qr = PersistenceHelper.manager.find(spec);

	    while (qr.hasMoreElements()) {
		Object[] obj = (Object[]) qr.nextElement();
		WorkItem item = (WorkItem) obj[0];
		WfAssignedActivity activity = (WfAssignedActivity) item.getSource().getObject();

		if (target instanceof KETMoldChangeActivity) {
		    KETMoldChangeActivity moldCA = (KETMoldChangeActivity) target;

		    if (moldCA.getLifeCycleState().equals(State.toState("INWORK"))) {
			activity.deleteActivity();
		    }
		} else if (target instanceof KETProdChangeActivity) {
		    KETProdChangeActivity prodCA = (KETProdChangeActivity) target;

		    if (prodCA.getLifeCycleState().equals(State.toState("INWORK"))) {
			activity.deleteActivity();
		    }
		} else if (target instanceof KETEarlyWarningResult) {
		    if (item.getStatus().equals(WfAssignmentState.POTENTIAL))
			activity.deleteActivity();
		} else if (target instanceof ProductProject) {
		    if (item.getStatus().equals(WfAssignmentState.POTENTIAL))
			activity.deleteActivity();
		}
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    SessionContext.setContext(sessioncontext);

	}

    }
}
