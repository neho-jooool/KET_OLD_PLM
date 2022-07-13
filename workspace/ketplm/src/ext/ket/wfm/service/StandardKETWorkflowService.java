package ext.ket.wfm.service;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.dataservice.DataServiceFactory;
import wt.dataservice.Datastore;
import wt.dataservice.Oracle;
import wt.dataservice.SQLServer;
import wt.epm.EPMDocument;
import wt.events.KeyedEvent;
import wt.events.KeyedEventListener;
import wt.fc.ObjectNoLongerExistsException;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceManagerEvent;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTObject;
import wt.inf.container.WTContained;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.introspection.BaseTableInfo;
import wt.introspection.ClassInfo;
import wt.introspection.DatabaseInfo;
import wt.introspection.WTIntrospector;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.LifeCycleTemplate;
import wt.lifecycle.LifeCycleTemplateReference;
import wt.lifecycle.State;
import wt.org.OrganizationServicesMgr;
import wt.org.WTPrincipal;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.ownership.OwnershipHelper;
import wt.ownership.OwnershipServerHelper;
import wt.part.WTPart;
import wt.pds.PartialResultException;
import wt.pds.StatementSpec;
import wt.pom.Transaction;
import wt.project.Role;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.CompoundQuerySpec;
import wt.query.ConstantExpression;
import wt.query.KeywordExpression;
import wt.query.OrderBy;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SetOperator;
import wt.services.ManagerException;
import wt.services.ServiceEventListenerAdapter;
import wt.services.StandardManager;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.team.Team;
import wt.team.TeamException;
import wt.team.TeamHelper;
import wt.team.TeamManaged;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.workflow.WfException;
import wt.workflow.definer.WfDefinerHelper;
import wt.workflow.definer.WfProcessTemplate;
import wt.workflow.definer.WfTemplateObject;
import wt.workflow.engine.ProcessData;
import wt.workflow.engine.WfActivity;
import wt.workflow.engine.WfEngineHelper;
import wt.workflow.engine.WfEngineServerHelper;
import wt.workflow.engine.WfProcess;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WfAssignmentState;
import wt.workflow.work.WorkItem;
import wt.workflow.work.WorkflowHelper;
import wt.workflow.work.WorkflowServerHelper;
import e3ps.bom.entity.KETBomEcoHeader;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.ecm.beans.EcmUtil;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldECABomLink;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdECABomLink;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.CheckoutLink;
import e3ps.project.E3PSProject;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.ReviewProject;
import e3ps.project.WorkProject;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalLine;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.entity.KETWfmMasterProcessLink;
import e3ps.wfm.service.KETWfmHelper;
import e3ps.wfm.util.WFMProperties;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.cost.entity.CostReport;
import ext.ket.dqm.entity.KETDqmAction;
import ext.ket.dqm.entity.KETDqmHeader;
import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.invest.entity.KETInvestMaster;
import ext.ket.invest.entity.KETInvestTask;
import ext.ket.invest.util.InvestUtil;
import ext.ket.issue.entity.KETIssueMaster;
import ext.ket.issue.entity.KETIssueTask;
import ext.ket.issue.util.IssueUtil;
import ext.ket.sample.entity.KETSampleRequest;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.shared.util.ComparatorUtil;
import ext.ket.shared.util.SearchUtil;
import ext.ket.shared.util.SessionUtil;
import ext.ket.wfm.entity.KETFavorApprovalUser;
import ext.ket.wfm.entity.KETFavorApprovalUserLink;
import ext.ket.wfm.entity.KETWfmApprovalHistoryDTO;
import ext.ket.wfm.entity.WorkItemDTO;

/**
 * @클래스명 : StandardWorkflowService
 * @작성자 : Jason, Han
 * @작성일 : 2014. 6. 26.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class StandardKETWorkflowService extends StandardManager implements KETWorkflowService, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3524684285064920010L;

    private KeyedEventListener listener;

    public static StandardKETWorkflowService newStandardKETWorkflowService() throws WTException {
	StandardKETWorkflowService instance = new StandardKETWorkflowService();
	instance.initialize();
	return instance;
    }

    /*
     * (non-Javadoc)
     * 
     * @see wt.services.StandardManager#performStartupProcess()
     */
    @Override
    protected synchronized void performStartupProcess() throws ManagerException {
	super.performStartupProcess();
	listener = new KETWfmApprovalMasterEventListener(this.getName());
	getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey("PRE_DELETE"));
	System.out.println("StandardKETWorkflowService PersistenceManagerEvent.PRE_DELETE listener");
    }

    /**
     * LifeCycleManaged 객체 삭제 시 연관된 KETWfmApprovalMaster를 찾아 삭제해 주는 이벤트
     * 
     * @클래스명 : KETWfmApprovalMasterEventListener
     * @작성자 : Jason, Han
     * @작성일 : 2014. 8. 20.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    class KETWfmApprovalMasterEventListener extends ServiceEventListenerAdapter {

	public KETWfmApprovalMasterEventListener(String manager_name) {
	    super(manager_name);
	}

	public void notifyVetoableEvent(Object obj) throws Exception {
	    if (!(obj instanceof KeyedEvent))
		return;
	    if (obj instanceof KeyedEvent) {
		KeyedEvent keyEvent = (KeyedEvent) obj;
		Object eventObj = keyEvent.getEventTarget();
		String eventTypeStr = keyEvent.getEventType();
		if (eventObj instanceof LifeCycleManaged && "PRE_DELETE".equals(eventTypeStr)) {
		    Persistable pbo = (Persistable) eventObj;
		    KETWfmApprovalMaster appMaster = WorkflowSearchHelper.manager.getMaster(pbo);
		    if (appMaster != null) {
			Kogger.debug(getClass(), "[PRE_DELETE] StandardKETWorkflowService : KETWfmApprovalMasterEventListener - "
			        + appMaster.toString());
			PersistenceHelper.manager.delete(appMaster);
		    }
		}
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.service.CommonServiceInterface#find(java.lang.Object)
     */
    @Override
    public List<WorkItemDTO> find(WorkItemDTO paramObject) throws Exception {
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.service.CommonServiceInterface#findPaging(java.lang.Object)
     */
    @Override
    public PageControl findPaging(WorkItemDTO paramObject) throws Exception {

	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	StatementSpec query = null;
	if ("listWorkItem".equals(paramObject.getCommand())) {
	    query = getFilterUncompletedWorkItems(paramObject, false);
	} else if ("listTempWorkItem".equals(paramObject.getCommand())) {
	    query = getFilterTempWorkItems(paramObject);
	} else if ("listInProgressWorkItem".equals(paramObject.getCommand())) {
	    query = getFilterInProgressWorkItem(paramObject, getStateWorkItem(paramObject, KETWfmApprovalHistoryDTO.INPROGRESS));
	} else if ("listCompletedWorkItem".equals(paramObject.getCommand())) {
	    query = getFilterCompletedWorkItem(paramObject, getStateWorkItem(paramObject, KETWfmApprovalHistoryDTO.COMPLETED));
	} else if ("listReceiptWorkItem".equals(paramObject.getCommand())) {
	    query = getFilterCompletedWorkItem(paramObject, getStateWorkItem(paramObject, KETWfmApprovalHistoryDTO.RECEIPT));
	}
	Kogger.info(getClass(), query);
	if (query.isAdvancedQuery()) {
	    if (!query.isAdvancedQueryEnabled())
		query.setAdvancedQueryEnabled(true);
	}
	if (query != null) {
	    if (StringUtil.isEmpty(pageSessionId)) {
		pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), query);
	    } else {
		pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), paramObject.getPage() + 1,
		        pageSessionId);
	    }
	}
	return pageControl;
    }

    private StatementSpec getFilterInProgressWorkItem(WorkItemDTO paramObject, ArrayList<KETWfmApprovalMaster> masterList) throws Exception {

	ArrayList<Long> list = new ArrayList<Long>();
	for (KETWfmApprovalMaster master : masterList) {
	    LifeCycleManaged lcm = null;
	    try {
		lcm = (LifeCycleManaged) master.getPbo();
	    } catch (Exception e) {
		try {
		    lcm = (LifeCycleManaged) master.getBusinessobjectRef().getObject();
		} catch (Exception e2) {

		}
	    }
	    String filterClass = StringUtil.checkNull(paramObject.getFilterClass());
	    if (lcm != null) {
		if (StringUtil.checkString(filterClass)) {
		    String[] strings = filterClass.split(",");
		    boolean flag = false;
		    for (String str : strings) {
			if (lcm.getClass().getName().indexOf(str) != -1)
			    flag = true;
		    }
		    if (flag) {
			if (!"REWORK".equals(lcm.getLifeCycleState().toString()) && isInProgressMyWorkItem(master)) {
			    list.add(CommonUtil.getOIDLongValue(master));
			}
		    }
		} else {
		    if (!"REWORK".equals(lcm.getLifeCycleState().toString()) && isInProgressMyWorkItem(master))
			list.add(CommonUtil.getOIDLongValue(master));
		}
	    }
	}
	long[] longs = new long[list.size()];
	for (int i = 0; i < list.size(); i++) {
	    longs[i] = list.get(i);
	}
	if (longs.length == 0) {
	    longs = new long[1];
	    longs[0] = 0;
	}
	QuerySpec querySpec = new QuerySpec();
	int m_idx = querySpec.appendClassList(KETWfmApprovalMaster.class, true);
	SearchCondition sc = new SearchCondition(KETWfmApprovalMaster.class, "thePersistInfo.theObjectIdentifier.id", longs, false);
	querySpec.appendWhere(sc, new int[] { m_idx });
	String predate = paramObject.getPredate();
	String postdate = paramObject.getPostdate();
	if (StringUtil.checkString(predate)) {
	    SearchUtil.appendTimeFrom(querySpec, KETWfmApprovalMaster.class, "thePersistInfo.createStamp",
		    DateUtil.convertStartDate2(predate), m_idx);
	}
	if (StringUtil.checkString(postdate)) {
	    SearchUtil.appendTimeTo(querySpec, KETWfmApprovalMaster.class, "thePersistInfo.createStamp",
		    DateUtil.convertEndDate2(postdate), m_idx);
	}
	SearchUtil.setOrderBy(querySpec, KETWfmApprovalMaster.class, m_idx, "thePersistInfo.createStamp", true);
	return querySpec;
    }

    private ArrayList<KETWfmApprovalMaster> getStateWorkItem(WorkItemDTO paramObject, int state) throws Exception {

	long userOID = CommonUtil.getOIDLongFromSessionUser();
	Class<KETWfmApprovalMaster> appMaster = KETWfmApprovalMaster.class;
	Class<KETWfmApprovalHistory> appHistory = KETWfmApprovalHistory.class;
	ArrayList<KETWfmApprovalMaster> list = new ArrayList<KETWfmApprovalMaster>();
	QuerySpec spec = new QuerySpec();
	int m_idx = spec.addClassList(appMaster, true);
	if (KETWfmApprovalHistoryDTO.COMPLETED == state) {
	    int h_idx = spec.addClassList(appHistory, false);
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(appMaster, "thePersistInfo.theObjectIdentifier.id"), "=",
		    new ClassAttribute(appHistory, "appMasterReference.key.id")), new int[] { m_idx, h_idx });
	    SearchUtil.appendNULL(spec, appMaster, "completedDate", SearchCondition.NOT_NULL, true, m_idx);
	    SearchUtil.appendNULL(spec, appHistory, "completedDate", SearchCondition.NOT_NULL, true, h_idx);
	    SearchUtil.appendEQUAL(spec, appHistory, "owner.key.id", userOID, h_idx);
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    String[] activityNames = new String[] { KETWfmApprovalHistoryDTO.RECEIVER, KETWfmApprovalHistoryDTO.REFERENCE };
	    spec.appendWhere(new SearchCondition(appHistory, "activityName", activityNames, false, true), new int[] { h_idx });

	} else if (KETWfmApprovalHistoryDTO.INPROGRESS == state) {
	    /***************************************************************
	     * 결재진행함 검색조건 1. 내가 상신한 결재 건 중 승인완료 이전인 건 2. 결재자(검토/합의)가 결재를 완료하였지만 승인완료 이전인 건
	     ***************************************************************/
	    int h_idx = spec.addClassList(appHistory, false);
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(appMaster, "thePersistInfo.theObjectIdentifier.id"), "=",
		    new ClassAttribute(appHistory, "appMasterReference.key.id")), new int[] { m_idx, h_idx });
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendOpenParen();
	    // 1. 내가 상신한 결재 건 중 승인완료 이전인 건
	    spec.appendOpenParen();
	    spec.appendWhere(new SearchCondition(appMaster, "completedDate", SearchCondition.IS_NULL, true), new int[] { m_idx });
	    SearchUtil.appendEQUAL(spec, appMaster, "owner.key.id", userOID, m_idx);
	    SearchUtil.appendBOOLEAN(spec, appMaster, "startFlag", SearchCondition.IS_TRUE, m_idx);
	    spec.appendCloseParen();
	    spec.appendOr();
	    spec.appendOpenParen();
	    // 2. 결재자(검토/합의)로 결재를 완료하였지만 승인완료 이전인 건
	    spec.appendWhere(new SearchCondition(appMaster, "completedDate", SearchCondition.IS_NULL, true), new int[] { m_idx });
	    SearchUtil.appendBOOLEAN(spec, appMaster, "startFlag", SearchCondition.IS_TRUE, m_idx);
	    SearchUtil.appendNULL(spec, appHistory, "completedDate", SearchCondition.NOT_NULL, true, h_idx);
	    SearchUtil.appendEQUAL(spec, appHistory, "owner.key.id", userOID, h_idx);
	    String[] activityNames = new String[] { KETWfmApprovalHistoryDTO.DISCUSS, KETWfmApprovalHistoryDTO.REVIEW,
		    KETWfmApprovalHistoryDTO.REQRECIPIENT };
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(appHistory, "activityName", activityNames, false, false), new int[] { h_idx });
	    spec.appendCloseParen();
	    spec.appendCloseParen();

	} else if (KETWfmApprovalHistoryDTO.RECEIPT == state) {
	    int h_idx = spec.addClassList(appHistory, false);
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(appMaster, "thePersistInfo.theObjectIdentifier.id"), "=",
		    new ClassAttribute(appHistory, "appMasterReference.key.id")), new int[] { m_idx, h_idx });
	    SearchUtil.appendNULL(spec, appMaster, "completedDate", SearchCondition.NOT_NULL, true, m_idx);
	    SearchUtil.appendNULL(spec, appHistory, "completedDate", SearchCondition.NOT_NULL, true, h_idx);
	    SearchUtil.appendEQUAL(spec, appHistory, "owner.key.id", userOID, h_idx);
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    String[] activityNames = new String[] { KETWfmApprovalHistoryDTO.RECEIVER };
	    spec.appendWhere(new SearchCondition(appHistory, "activityName", activityNames, false, false), new int[] { h_idx });
	    SearchUtil.appendNOTEQUAL(spec, appHistory, "decision", KETWfmApprovalHistoryDTO.DELEGATE, h_idx);

	}
	spec.setDistinct(true);

	Kogger.info(getClass(), spec);
	QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);
	while (result.hasMoreElements()) {
	    Object[] objects = (Object[]) result.nextElement();
	    KETWfmApprovalMaster master = (KETWfmApprovalMaster) objects[0];
	    if (!list.contains(master))
		list.add(master);
	}

	return list;
    }

    private StatementSpec getFilterCompletedWorkItem(WorkItemDTO paramObject, ArrayList<KETWfmApprovalMaster> masterList) throws Exception {

	ArrayList<Long> list = new ArrayList<Long>();
	for (KETWfmApprovalMaster master : masterList) {
	    LifeCycleManaged lcm = null;
	    try {
		lcm = (LifeCycleManaged) master.getPbo();
	    } catch (Exception e) {
		try {
		    lcm = (LifeCycleManaged) master.getBusinessobjectRef().getObject();
		} catch (Exception e2) {

		}
	    }
	    String filterClass = StringUtil.checkNull(paramObject.getFilterClass());
	    if (lcm != null) {
		if (StringUtil.checkString(filterClass)) {
		    String[] strings = filterClass.split(",");
		    boolean flag = false;
		    for (String str : strings) {
			if (lcm.getClass().getName().indexOf(str) != -1)
			    flag = true;
		    }
		    if (flag) {
			if (!"REWORK".equals(lcm.getLifeCycleState().toString())) {
			    list.add(CommonUtil.getOIDLongValue(master));
			}
		    }
		} else {
		    list.add(CommonUtil.getOIDLongValue(master));
		}
	    }
	}
	long[] longs = new long[list.size()];
	for (int i = 0; i < list.size(); i++) {
	    longs[i] = list.get(i);
	}
	if (longs.length == 0) {
	    longs = new long[1];
	    longs[0] = 0;
	}
	QuerySpec querySpec = new QuerySpec();
	int m_idx = querySpec.appendClassList(KETWfmApprovalMaster.class, true);
	SearchCondition sc = new SearchCondition(KETWfmApprovalMaster.class, "thePersistInfo.theObjectIdentifier.id", longs, false);
	querySpec.appendWhere(sc, new int[] { m_idx });
	String predate = paramObject.getPredate();
	String postdate = paramObject.getPostdate();
	if (StringUtil.checkString(predate)) {
	    SearchUtil.appendTimeFrom(querySpec, KETWfmApprovalMaster.class, "completedDate", DateUtil.convertStartDate2(predate), m_idx);
	}
	if (StringUtil.checkString(postdate)) {
	    SearchUtil.appendTimeTo(querySpec, KETWfmApprovalMaster.class, "completedDate", DateUtil.convertEndDate2(postdate), m_idx);
	}
	SearchUtil.setOrderBy(querySpec, KETWfmApprovalMaster.class, m_idx, "completedDate", true);

	return querySpec;
    }

    private StatementSpec getTempWorkItems(WorkItemDTO paramObject) throws Exception {

	String predate = paramObject.getPredate();
	String postdate = paramObject.getPostdate();
	long userOID = CommonUtil.getOIDLongFromSessionUser();

	QuerySpec spec = new QuerySpec();
	Class<KETWfmApprovalMaster> appMaster = KETWfmApprovalMaster.class;
	int idx = spec.appendClassList(appMaster, true);
	SearchUtil.appendEQUAL(spec, appMaster, "owner.key.id", userOID, idx);
	SearchUtil.appendNULL(spec, appMaster, "completedDate", SearchCondition.IS_NULL, true, idx);
	if (StringUtil.checkString(predate)) {
	    SearchUtil.appendTimeFrom(spec, appMaster, "thePersistInfo.createStamp", DateUtil.convertStartDate2(predate), idx);
	}
	if (StringUtil.checkString(postdate)) {
	    SearchUtil.appendTimeTo(spec, appMaster, "thePersistInfo.createStamp", DateUtil.convertEndDate2(postdate), idx);
	}
	SearchUtil.setOrderBy(spec, appMaster, idx, "thePersistInfo.createStamp", true);
	return spec;
    }

    private StatementSpec getFilterTempWorkItems(WorkItemDTO paramObject) throws Exception {

	StatementSpec spec = getTempWorkItems(paramObject);
	if (spec.isAdvancedQuery()) {
	    if (!spec.isAdvancedQueryEnabled())
		spec.setAdvancedQueryEnabled(true);
	}
	Kogger.debug(getClass(), spec.toString());
	QueryResult result = PersistenceHelper.manager.find(spec);
	ArrayList<Long> list = new ArrayList<Long>();
	while (result.hasMoreElements()) {
	    Object[] objArr = (Object[]) result.nextElement();
	    KETWfmApprovalMaster apmaster = (KETWfmApprovalMaster) objArr[0];
	    LifeCycleManaged lcm = null;
	    try {
		lcm = (LifeCycleManaged) apmaster.getPbo();
	    } catch (Exception e) {
		try {
		    lcm = (LifeCycleManaged) apmaster.getBusinessobjectRef().getObject();
		} catch (Exception e2) {

		}
	    }
	    if (lcm != null) {
		if (!apmaster.isStartFlag()) {
		    list.add(CommonUtil.getOIDLongValue(apmaster));
		} else if (apmaster.isStartFlag() && "REWORK".equals(lcm.getLifeCycleState().toString())) {
		    list.add(CommonUtil.getOIDLongValue(apmaster));
		}
	    }
	}
	long[] longs = new long[list.size()];
	for (int i = 0; i < list.size(); i++) {
	    longs[i] = list.get(i);
	}
	if (longs.length == 0) {
	    longs = new long[1];
	    longs[0] = 0;
	}
	QuerySpec querySpec = new QuerySpec();
	int m_idx = querySpec.appendClassList(KETWfmApprovalMaster.class, true);
	SearchCondition sc = new SearchCondition(KETWfmApprovalMaster.class, "thePersistInfo.theObjectIdentifier.id", longs, false);
	querySpec.appendWhere(sc, new int[] { m_idx });

	String filterClass = StringUtil.checkNull(paramObject.getFilterClass());
	if (filterClass.length() > 0) {
	    if (filterClass != null && filterClass.trim().length() > 0 && !filterClass.equalsIgnoreCase("null")) {
		if (querySpec.getConditionCount() > 0)
		    querySpec.appendAnd();
		querySpec.appendOpenParen();
		StringTokenizer filterClassToken = new StringTokenizer(filterClass, ",");
		while (filterClassToken.hasMoreTokens()) {
		    querySpec.appendWhere(new SearchCondition(KETWfmApprovalMaster.class, "businessobjectRef.key.classname",
			    SearchCondition.LIKE, "%" + filterClassToken.nextToken() + "%"), new int[] { m_idx });
		    if (filterClassToken.hasMoreTokens())
			querySpec.appendOr();
		}
		querySpec.appendCloseParen();
	    }
	}

	SearchUtil.setOrderBy(querySpec, KETWfmApprovalMaster.class, m_idx, "thePersistInfo.createStamp", true);
	return querySpec;
    }

    private long[] getFilterWorkItems(WorkItemDTO workItemDTO, boolean isTodo) throws Exception {

	QuerySpec spec = getUncompletedWorkItems(SessionHelper.manager.getPrincipal(), null, null, isTodo);
	if (spec.isAdvancedQuery()) {
	    if (!spec.isAdvancedQueryEnabled())
		spec.setAdvancedQueryEnabled(true);
	}
	Kogger.debug(getClass(), spec.toString());
	QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);
	String filterClass = StringUtil.checkNull(workItemDTO.getFilterClass());
	ArrayList<Long> list = new ArrayList<Long>();
	ArrayList<Long> toDoActivityList = new ArrayList<Long>();
	while (result.hasMoreElements()) {
	    Object[] objArr = (Object[]) result.nextElement();
	    WorkItem workItem = (WorkItem) objArr[0];
	    Persistable pbo = null;
	    try {
		pbo = workItem.getPrimaryBusinessObject().getObject();
	    } catch (Exception e) {
		continue;
	    }
	    if (pbo != null) {
		if (StringUtil.checkString(filterClass)) {
		    String[] strings = filterClass.split(",");
		    boolean flag = false;
		    for (String str : strings) {
			if (pbo.getClass().getName().indexOf(str) != -1)
			    flag = true;
		    }
		    if (flag) {
			// 설변 todo 부품, 도면 (activity type 1, 2 )은 화면에서 하나만 보이도록 수정
			// tklee 2015.1.29
			if (isTodo) {
			    if (pbo instanceof KETProdChangeActivity) {
				KETProdChangeActivity tempEca = (KETProdChangeActivity) pbo;
				if ("1".equals(tempEca.getActivityType()) || "2".equals(tempEca.getActivityType())) {
				    toDoActivityList.add(CommonUtil.getOIDLongValue(workItem));
				} else {
				    list.add(CommonUtil.getOIDLongValue(workItem));
				}
			    } else if (pbo instanceof KETMoldChangeActivity) {
				KETMoldChangeActivity tempEca = (KETMoldChangeActivity) pbo;
				if ("1".equals(tempEca.getActivityType()) || "2".equals(tempEca.getActivityType())) {
				    toDoActivityList.add(CommonUtil.getOIDLongValue(workItem));
				} else {
				    list.add(CommonUtil.getOIDLongValue(workItem));
				}
			    } else {
				list.add(CommonUtil.getOIDLongValue(workItem));
			    }
			} else {
			    list.add(CommonUtil.getOIDLongValue(workItem));
			}
		    }
		} else {
		    // 설변 todo 부품, 도면 (activity type 1, 2 )은 화면에서 하나만 보이도록 수정
		    // tklee 2015.1.29
		    if (isTodo) {
			if (pbo instanceof KETProdChangeActivity) {
			    KETProdChangeActivity tempEca = (KETProdChangeActivity) pbo;
			    if ("1".equals(tempEca.getActivityType()) || "2".equals(tempEca.getActivityType())) {
				toDoActivityList.add(CommonUtil.getOIDLongValue(workItem));
			    } else {
				list.add(CommonUtil.getOIDLongValue(workItem));
			    }
			} else if (pbo instanceof KETMoldChangeActivity) {
			    KETMoldChangeActivity tempEca = (KETMoldChangeActivity) pbo;
			    if ("1".equals(tempEca.getActivityType()) || "2".equals(tempEca.getActivityType())) {
				toDoActivityList.add(CommonUtil.getOIDLongValue(workItem));
			    } else {
				list.add(CommonUtil.getOIDLongValue(workItem));
			    }
			} else {
			    list.add(CommonUtil.getOIDLongValue(workItem));
			}
		    } else {
			list.add(CommonUtil.getOIDLongValue(workItem));
		    }
		}
	    }
	}

	if (isTodo && toDoActivityList.size() > 0) {
	    list.addAll(getMultiToOneWorkItemByEco(toDoActivityList));
	}

	long[] longs = new long[list.size()];
	for (int i = 0; i < list.size(); i++) {
	    longs[i] = list.get(i);
	}
	if (longs.length == 0) {
	    longs = new long[1];
	    longs[0] = 0;
	}
	return longs;
    }

    @Override
    public QuerySpec getFilterUncompletedWorkItems(WorkItemDTO workItemDTO, boolean isTodo) throws Exception {

	long[] longs = getFilterWorkItems(workItemDTO, isTodo);

	QuerySpec spec = new QuerySpec();
	int idx = spec.addClassList(WorkItem.class, true);
	SearchCondition sc = new SearchCondition(WorkItem.class, "thePersistInfo.theObjectIdentifier.id", longs, false);
	spec.appendWhere(sc, new int[] { idx });
	SQLFunction localSQLFunction = getTruncateFunction(new ClassAttribute(WorkItem.class, "thePersistInfo.createStamp"));
	spec.appendOrderBy(new OrderBy(localSQLFunction, true), new int[] { idx });
	SearchUtil.setOrderBy(spec, WorkItem.class, idx, "role", false);

	System.out.println("###### qs :" + spec.toString());

	return spec;
    }

    /**
     * <pre>
     * @description My Todo 쿼리 (고객대응관리 병합)
     * @author dhkim
     * @date 2018. 7. 12. 오후 12:42:20
     * @method getMyTodoQuery
     * @param workItemDTO
     * @param isTodo
     * @return
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    @Override
    public StatementSpec getMyTodoQuery(WorkItemDTO workItemDTO, boolean isTodo) throws Exception {

	long toDoUser = CommonUtil.getOIDLongValue(SessionHelper.manager.getPrincipal());

	long[] longs = getFilterWorkItems(workItemDTO, isTodo);

	QuerySpec wiQs = new QuerySpec();
	wiQs.setAdvancedQueryEnabled(true);
	int idx = wiQs.addClassList(WorkItem.class, false);

	ClassInfo classInfo = WTIntrospector.getClassInfo(WorkItem.class);
	DatabaseInfo dbInfo = classInfo.getDatabaseInfo();
	BaseTableInfo btInfo = dbInfo.getBaseTableInfo();
	String objName = btInfo.getColumnDescriptor(WTAttributeNameIfc.OID_CLASSNAME).getColumnName();
	String objId = btInfo.getColumnDescriptor(WTAttributeNameIfc.ID_NAME).getColumnName();
	KeywordExpression kexp = new KeywordExpression(wiQs.getFromClause().getAliasAt(idx) + "." + objName + "||':'||"
	        + wiQs.getFromClause().getAliasAt(idx) + "." + objId);
	kexp.setColumnAlias("wiOid");

	wiQs.appendSelect(kexp, new int[] { idx }, false);

	SearchCondition sc = new SearchCondition(WorkItem.class, WTAttributeNameIfc.ID_NAME, longs, false);
	wiQs.appendWhere(sc, new int[] { idx });

	SearchUtil.setOrderBy(wiQs, WorkItem.class, idx, WorkItem.CREATE_TIMESTAMP, "STAMPORDER", true);
	SearchUtil.setOrderBy(wiQs, WorkItem.class, idx, WorkItem.ROLE, "ROLEORDER", false);

	// 요청 사항 쿼리 ####################################################################

	Class<KETIssueMaster> imClass = KETIssueMaster.class;
	QuerySpec imQs = new QuerySpec();
	imQs.setAdvancedQueryEnabled(true);
	int imIdx = imQs.appendClassList(imClass, false);

	classInfo = WTIntrospector.getClassInfo(imClass);
	dbInfo = classInfo.getDatabaseInfo();
	btInfo = dbInfo.getBaseTableInfo();
	objName = btInfo.getColumnDescriptor(WTAttributeNameIfc.OID_CLASSNAME).getColumnName();
	objId = btInfo.getColumnDescriptor(WTAttributeNameIfc.ID_NAME).getColumnName();
	kexp = new KeywordExpression(imQs.getFromClause().getAliasAt(imIdx) + "." + objName + "||':'||"
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
	SearchUtil.setOrderBy(imQs, imClass, imIdx, KETIssueMaster.MODIFY_TIMESTAMP, "STAMPORDER", true);
	SearchUtil.setOrderBy(imQs, imClass, imIdx, KETIssueMaster.REQ_NAME, "ROLEORDER", true);

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

	SearchUtil.setOrderBy(itQs, itClass, itIdx, KETIssueTask.PROGRESS_DATE, "STAMPORDER", true);
	SearchUtil.setOrderBy(itQs, itClass, itIdx, KETIssueTask.SUBJECT, "ROLEORDER", true);

	// 세부 요청 사항 쿼리 END #################################################################

	// 고객투자비 관리 쿼리 ####################################################################

	Class<KETInvestMaster> investMasterClass = KETInvestMaster.class;
	QuerySpec investMasterQs = new QuerySpec();
	investMasterQs.setAdvancedQueryEnabled(true);
	int investMasterIdx = investMasterQs.appendClassList(investMasterClass, false);

	classInfo = WTIntrospector.getClassInfo(investMasterClass);
	dbInfo = classInfo.getDatabaseInfo();
	btInfo = dbInfo.getBaseTableInfo();
	objName = btInfo.getColumnDescriptor(WTAttributeNameIfc.OID_CLASSNAME).getColumnName();
	objId = btInfo.getColumnDescriptor(WTAttributeNameIfc.ID_NAME).getColumnName();
	kexp = new KeywordExpression(investMasterQs.getFromClause().getAliasAt(investMasterIdx) + "." + objName + "||':'||"
	        + investMasterQs.getFromClause().getAliasAt(investMasterIdx) + "." + objId);
	kexp.setColumnAlias("imOid");

	investMasterQs.appendSelect(kexp, new int[] { investMasterIdx }, false);

	investMasterQs.appendOpenParen();
	investMasterQs.appendWhere(new SearchCondition(investMasterClass, KETInvestMaster.MANAGER_REFERENCE + "."
	        + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, toDoUser), new int[] { investMasterIdx });

	if (investMasterQs.getConditionCount() > 0)
	    investMasterQs.appendAnd();
	investMasterQs.appendWhere(new SearchCondition(new ClassAttribute(investMasterClass, KETInvestMaster.INVEST_STATE),
	        SearchCondition.IN, new ArrayExpression(new String[] { InvestUtil.INPROGRESS, InvestUtil.INWORK })),
	        new int[] { investMasterIdx });

	investMasterQs.appendOr();

	investMasterQs.appendWhere(new SearchCondition(investMasterClass, KETInvestMaster.CREATOR + "." + WTAttributeNameIfc.REF_OBJECT_ID,
	        SearchCondition.EQUAL, toDoUser), new int[] { investMasterIdx });
	if (investMasterQs.getConditionCount() > 0)
	    investMasterQs.appendAnd();
	investMasterQs.appendWhere(new SearchCondition(new ClassAttribute(investMasterClass, KETInvestMaster.INVEST_STATE),
	        SearchCondition.IN, new ArrayExpression(new String[] { InvestUtil.REGDIT })), new int[] { investMasterIdx });

	investMasterQs.appendCloseParen();

	// alias = investMasterQs.getFromClause().getAliasAt(investMasterIdx);
	// subQs = "(SELECT COUNT(ITCNT0.idA2A2) FROM KETINVESTTASK ITCNT0 WHERE ((ITCNT0.IDA3B8 = " + alias + ".IDA2A2)" +
	// " AND (ITCNT0.INVESTSTATE IN ('IST002','IST001'))) AND (ITCNT0.TASKCODE = 'BTYPE'))";
	//
	// kexp = new KeywordExpression("0");
	// kexp2 = new KeywordExpression(subQs);
	// sc0 = new SearchCondition(kexp, SearchCondition.EQUAL, kexp2);
	// if (investMasterQs.getConditionCount() > 0) investMasterQs.appendAnd();
	// investMasterQs.appendWhere(sc0, new int[] { investMasterIdx });
	SearchUtil.setOrderBy(investMasterQs, investMasterClass, investMasterIdx, KETInvestMaster.MODIFY_TIMESTAMP, "STAMPORDER", true);
	SearchUtil.setOrderBy(investMasterQs, investMasterClass, investMasterIdx, KETInvestMaster.REQ_NAME, "ROLEORDER", true);

	// 고객투자비 관리 END #################################################################

	// 고객투자비 관리 세부 요청 사항 쿼리 ################################################################
	Class<KETInvestTask> itClass2 = KETInvestTask.class;
	QuerySpec itQs2 = new QuerySpec();
	itQs2.setAdvancedQueryEnabled(true);
	int itIdx2 = itQs2.appendClassList(itClass2, false);

	classInfo = WTIntrospector.getClassInfo(itClass2);
	dbInfo = classInfo.getDatabaseInfo();
	btInfo = dbInfo.getBaseTableInfo();
	objName = btInfo.getColumnDescriptor(WTAttributeNameIfc.OID_CLASSNAME).getColumnName();
	objId = btInfo.getColumnDescriptor(WTAttributeNameIfc.ID_NAME).getColumnName();
	kexp = new KeywordExpression(itQs2.getFromClause().getAliasAt(itIdx2) + "." + objName + "||':'||"
	        + itQs2.getFromClause().getAliasAt(itIdx2) + "." + objId);
	kexp.setColumnAlias("itOid");

	itQs2.appendSelect(kexp, new int[] { itIdx2 }, false);

	itQs2.appendWhere(new SearchCondition(itClass2, KETInvestTask.WORKER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
	        SearchCondition.EQUAL, toDoUser), new int[] { itIdx2 });

	if (itQs2.getConditionCount() > 0)
	    itQs2.appendAnd();
	itQs2.appendWhere(new SearchCondition(itClass2, KETInvestTask.INVEST_STATE, SearchCondition.EQUAL, InvestUtil.INPROGRESS),
	        new int[] { itIdx2 });

	if (itQs2.getConditionCount() > 0)
	    itQs2.appendAnd();
	itQs2.appendWhere(new SearchCondition(itClass2, KETInvestTask.TASK_CODE, SearchCondition.EQUAL, "BTYPE"), new int[] { itIdx2 });

	SearchUtil.setOrderBy(itQs2, itClass2, itIdx2, KETInvestTask.PROGRESS_DATE, "STAMPORDER", true);
	SearchUtil.setOrderBy(itQs2, itClass2, itIdx2, KETInvestTask.SUBJECT, "ROLEORDER", true);

	// 고객투자비 관리 요청 사항 쿼리 END #################################################################

	CompoundQuerySpec qs = new CompoundQuerySpec();
	qs.setUseComponentOrderBy(true);
	qs.setAdvancedQueryEnabled(true);
	qs.addComponent(wiQs);
	qs.addComponent(imQs);
	qs.addComponent(itQs);
	qs.addComponent(investMasterQs);
	qs.addComponent(itQs2);
	qs.setSetOperator(SetOperator.UNION_ALL);

	return qs;
    }

    // 여러 개 TODO중에서 하나의 TODO를 가져오기 - 이미 자신에게 온 것 중 KETProdChangeActivity, KETMoldChangeActivity 해당 workItem 만 가져옴
    // 따라서, 쿼리에서 ECO별 Activity는 BOM쪽 하나, 도면쪽 하나로 만들어야 함
    private List<Long> getMultiToOneWorkItemByEco(List<Long> toDoActivityList) {

	List<Long> ret = new ArrayList<Long>();
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    String oidsStr = getOidsString(toDoActivityList);
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" SELECT DISTINCT WIDA2A2 AS OID \n");
	    buffer.append(" FROM \n");
	    buffer.append(" ( \n");
	    buffer.append(" 	SELECT A.IDA3A8, A.ACTIVITYTYPE, MAX(W.IDA2A2) WIDA2A2 \n");
	    buffer.append(" 	FROM WorkItem W, KETProdChangeActivity A \n");
	    buffer.append(" 	WHERE (0,W.IDA2A2) IN ( \n");
	    buffer.append("  	      " + oidsStr + "	\n");
	    buffer.append("     ) \n");
	    // buffer.append(" 	AND CLASSNAMEKEYB4 LIKE '%ProdChangeActivity%' \n");
	    buffer.append(" 	AND TO_CHAR( A.IDA2A2 ) =  REPLACE( CLASSNAMEKEYB4, 'OR:e3ps.ecm.entity.KETProdChangeActivity:', '') \n");
	    buffer.append(" 	GROUP BY A.IDA3A8, A.ACTIVITYTYPE \n");
	    buffer.append(" 	UNION ALL \n");
	    buffer.append(" 	SELECT A.IDA3A8, A.ACTIVITYTYPE, MAX(W.IDA2A2) WIDA2A2 \n");
	    buffer.append(" 	FROM WorkItem W, KETMoldChangeActivity A \n");
	    buffer.append(" 	WHERE (0,W.IDA2A2) IN ( \n");
	    buffer.append("  	      " + oidsStr + "	\n");
	    buffer.append("     	) \n");
	    // buffer.append(" 	AND CLASSNAMEKEYB4 LIKE '%MoldChangeActivity%' \n");
	    buffer.append(" 	AND TO_CHAR( A.IDA2A2 ) =  REPLACE( CLASSNAMEKEYB4, 'OR:e3ps.ecm.entity.KETMoldChangeActivity:', '') \n");
	    buffer.append(" 	GROUP BY A.IDA3A8, A.ACTIVITYTYPE \n");
	    buffer.append(" ) \n");

	    String queryStr = buffer.toString();
	    Kogger.debug(getClass(), queryStr);

	    ret = oDaoManager.queryForList(queryStr, new RowSetStrategy<Long>() {

		@Override
		public Long mapRow(ResultSet rs) throws SQLException {

		    Long ret = rs.getLong("oid");
		    return ret;
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {

	}

	return ret;
    }

    private String getOidsString(List<Long> toDoActivityList) {
	if (toDoActivityList == null)
	    return "";

	String ret = "";
	for (int k = 0; k < toDoActivityList.size(); k++) {
	    Long oid = toDoActivityList.get(k);
	    ret = ret + "(0, " + String.valueOf(oid) + "),";

	}
	ret = StringUtils.removeEnd(ret, ",");
	return ret;
    }

    @Override
    public Map<String, String> getMyTodoCnt() throws Exception {
	QuerySpec spec = getFilterUncompletedWorkItems(new WorkItemDTO(), true);
	if (spec.isAdvancedQuery()) {
	    if (!spec.isAdvancedQueryEnabled()) {
		spec.setAdvancedQueryEnabled(true);
	    }
	}
	QueryResult qr = PersistenceServerHelper.manager.query(spec);
	int pjtCnt = 0;
	int ecmCnt = 0;
	int dqmCnt = 0;
	while (qr.hasMoreElements()) {
	    Object[] objects = (Object[]) qr.nextElement();
	    WorkItem item = (WorkItem) objects[0];
	    Persistable pbo = item.getPrimaryBusinessObject().getObject();
	    if (pbo.getClass().getName().indexOf("e3ps.project") != -1) {
		pjtCnt++;
	    } else if (pbo.getClass().getName().indexOf("e3ps.ecm.entity") != -1) {
		ecmCnt++;
	    } else if (pbo.getClass().getName().indexOf("ext.ket.dqm.entity") != -1) {
		dqmCnt++;
	    } else if (pbo.getClass().getName().indexOf("e3ps.ews.entity") != -1) {
		dqmCnt++;
	    }
	}
	Map<String, String> map = new HashMap<String, String>();
	map.put("PROJECT", String.valueOf(pjtCnt));
	map.put("ECM", String.valueOf(ecmCnt));
	map.put("DQM", String.valueOf(dqmCnt));
	return map;
    }

    @SuppressWarnings("deprecation")
    private QuerySpec getUncompletedWorkItems(WTPrincipal owner, Integer queryLimit, String sortKey, boolean isTodo) throws Exception {

	QuerySpec spec = null;
	try {
	    SQLFunction localSQLFunction = getTruncateFunction(new ClassAttribute(WorkItem.class, "thePersistInfo.createStamp"));
	    spec = buildOwnerQuery(owner);
	    SearchCondition localSearchCondition = new SearchCondition(WorkItem.class, "completedBy", true);
	    spec.appendAnd();
	    spec.appendWhere(localSearchCondition, new int[] { 0 });
	    int a_idx = spec.appendClassList(WfAssignedActivity.class, false);
	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(WorkItem.class, "source.key.id"), "=", new ClassAttribute(
		    WfAssignedActivity.class, "thePersistInfo.theObjectIdentifier.id")), new int[] { 0, a_idx });
	    spec.appendAnd();
	    spec.appendOpenParen();
	    spec.appendWhere(new SearchCondition(WfAssignedActivity.class, WfAssignedActivity.NAME, (isTodo) ? "=" : "<>", "수행담당자"),
		    new int[] { a_idx });
	    if (!isTodo)
		spec.appendAnd();
	    else
		spec.appendOr();
	    spec.appendWhere(new SearchCondition(WfAssignedActivity.class, WfAssignedActivity.NAME, (isTodo) ? "=" : "<>", "담당자지정"),
		    new int[] { a_idx });
	    spec.appendCloseParen();
	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(WfAssignedActivity.class, WfAssignedActivity.NAME, "<>", "재작업"), new int[] { a_idx });
	    if (sortKey != null) {
		boolean bool = false;
		if (sortKey.equals("thePersistInfo.modifyStamp"))
		    bool = true;
		Object localObject = new OrderBy(new ClassAttribute(WorkItem.class, sortKey), bool);
		spec.appendOrderBy((OrderBy) localObject, new int[] { 0 });
	    } else {
		spec.appendOrderBy(new OrderBy(localSQLFunction, true), new int[] { 0 });
	    }
	    spec.appendOrderBy(WorkItem.class, "role", false);
	    if (queryLimit == null) {
		queryLimit = new Integer(-1);
	    }
	    int i = queryLimit.intValue();
	    if (i > 0) {
		spec.setAdvancedQueryEnabled(true);
		spec.setQueryLimit(i);
	    }
	    return spec;
	} catch (PartialResultException localPartialResultException) {
	    Kogger.error(getClass(), localPartialResultException.getLocalizedMessage(), localPartialResultException);
	    return spec;
	} catch (QueryException localQueryException) {
	    Kogger.error(getClass(), localQueryException.getLocalizedMessage(), localQueryException);
	    throw new WfException(localQueryException, null);
	} catch (WTPropertyVetoException localWTPropertyVetoException) {
	    Kogger.error(getClass(), localWTPropertyVetoException.getLocalizedMessage(), localWTPropertyVetoException);
	    throw new WTException(localWTPropertyVetoException);
	}
    }

    @SuppressWarnings("deprecation")
    private SQLFunction getTruncateFunction(ClassAttribute paramClassAttribute) throws QueryException, WTPropertyVetoException {

	Datastore localDatastore = DataServiceFactory.getDefault().getDatastore();

	SQLFunction sqlFunction1 = null;
	if (localDatastore instanceof SQLServer) {
	    SQLFunction sqlFunction2 = SQLFunction.newSQLFunction("CONVERT");

	    sqlFunction2.setArgumentAt(new KeywordExpression("varchar"), 0);
	    sqlFunction2.setArgumentAt(paramClassAttribute, 1);

	    sqlFunction2.setArgumentAt(new KeywordExpression("100"), 2);
	    sqlFunction1 = SQLFunction.newSQLFunction("TO_DATE", sqlFunction2);
	} else if (localDatastore instanceof Oracle) {
	    sqlFunction1 = SQLFunction.newSQLFunction("TRUNC");
	    sqlFunction1.setArgumentAt(paramClassAttribute, 0);
	    sqlFunction1.setArgumentAt(new ConstantExpression("MI"), 1);
	}

	return sqlFunction1;
    }

    @SuppressWarnings("deprecation")
    private QuerySpec buildOwnerQuery(WTPrincipal owner) throws WTException {

	Vector<Long> localVector = new Vector<Long>();
	Long localLong = null;

	QuerySpec localQuerySpec = new QuerySpec(WorkItem.class);
	SearchCondition localSearchCondition = null;
	localQuerySpec.appendOpenParen();

	if (owner instanceof WTUser) {
	    localLong = new Long(PersistenceHelper.getObjectIdentifier(owner).getId());
	    addElementNoDup(localVector, localLong);
	}

	int i = localVector.size();
	long[] arrayOfLong = new long[i];
	for (int j = 0; j < i; ++j) {
	    arrayOfLong[j] = localVector.elementAt(j).longValue();
	}
	localSearchCondition = new SearchCondition(WorkItem.class, "ownership.owner.key.id", arrayOfLong, false);

	localQuerySpec.appendWhere(localSearchCondition);
	localQuerySpec.appendCloseParen();
	return localQuerySpec;
    }

    private void addElementNoDup(Vector<Long> paramVector, Long paramObject) {
	if (paramVector.indexOf(paramObject) < 0)
	    paramVector.addElement(paramObject);
    }

    @Override
    public boolean createMyFavorApprovalUser(String[] userids) throws Exception {

	KETFavorApprovalUser favorApprovalUser = getMyFavorApprovalUser();
	if (favorApprovalUser == null) {
	    favorApprovalUser = KETFavorApprovalUser.newKETFavorApprovalUser();
	    favorApprovalUser.setOwner(SessionHelper.manager.getPrincipalReference());
	    favorApprovalUser = (KETFavorApprovalUser) PersistenceHelper.manager.save(favorApprovalUser);
	}
	for (String userid : userids) {
	    People people = PeopleHelper.manager.getPeople(userid);
	    KETFavorApprovalUserLink link = KETFavorApprovalUserLink.newKETFavorApprovalUserLink(favorApprovalUser, people);
	    PersistenceHelper.manager.save(link);
	}
	return true;
    }

    @Override
    public boolean deleteMyFavorApprovalUser(String[] userids) throws Exception {

	KETFavorApprovalUser favorApprovalUser = getMyFavorApprovalUser();
	if (favorApprovalUser != null) {
	    QueryResult result = PersistenceHelper.manager.navigate(favorApprovalUser, KETFavorApprovalUserLink.PEOPLE_ROLE,
		    KETFavorApprovalUserLink.class, false);
	    while (result.hasMoreElements()) {
		KETFavorApprovalUserLink link = (KETFavorApprovalUserLink) result.nextElement();
		People people = link.getPeople();
		for (String userid : userids) {
		    if (userid.equals(people.getId())) {
			PersistenceHelper.manager.delete(link);
			break;
		    }
		}
	    }
	}
	return true;
    }

    @Override
    public KETFavorApprovalUser getMyFavorApprovalUser() throws Exception {
	KETFavorApprovalUser favorApprovalUser = null;
	QuerySpec spec = new QuerySpec();
	int idx = spec.appendClassList(KETFavorApprovalUser.class, true);
	SearchUtil.appendEQUAL(spec, KETFavorApprovalUser.class, "owner.key.id", CommonUtil.getOIDLongFromSessionUser(), idx);
	Kogger.info(getClass(), spec);
	QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);
	if (result.hasMoreElements()) {
	    Object[] objects = (Object[]) result.nextElement();
	    favorApprovalUser = (KETFavorApprovalUser) objects[0];
	}
	return favorApprovalUser;
    }

    @Override
    public boolean doBatchCompleteWorkItem(String[] workItemoids, String comments) throws Exception {

	Transaction trx = null;
	boolean flag = true;
	try {
	    trx = new Transaction();

	    for (String workItemoid : workItemoids) {

		WorkItem item = (WorkItem) CommonUtil.getObject(workItemoid);
		Persistable pbo = item.getPrimaryBusinessObject().getObject();
		KETWfmApprovalMaster appMaster = WorkflowSearchHelper.manager.getMaster(pbo);
		WfActivity activity = (WfActivity) item.getSource().getObject();
		// 2014-12-23 수신확인 일괄승인 제외처리 (결함관리대장 110번), Jason, Han
		if ("반려확인".equals(activity.getName()) || "담당자지정".equals(activity.getName()) || "수행담당자".equals(activity.getName())
		        || "수신확인".equals(activity.getName()))
		    continue;
		String condition = "accept";
		// if ("수신확인".equals(activity.getName())) condition = "confirm";

		Hashtable<String, String> aHash = new Hashtable<String, String>();
		aHash.put("item", workItemoid);
		aHash.put("condition", condition);
		aHash.put("master", CommonUtil.getOIDString(appMaster));
		aHash.put("comment", StringUtil.checkNull(comments));

		KETWfmHelper.service.completeActivity(aHash); // 결재 수행 완료
	    }
	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	    trx = null;
	    flag = false;
	    throw e;
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return flag;
    }

    @Override
    public boolean isCancelApproval(String pbooid) throws Exception {

	boolean flag = true;
	Persistable pbo = CommonUtil.getObject(pbooid);
	KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster(pbo);
	if (approvalMaster != null && approvalMaster.isStartFlag()) {
	    if (approvalMaster.getOwner().getName().equals(CommonUtil.getUserIDFromSession())) {
		@SuppressWarnings("unchecked")
		Vector<KETWfmApprovalHistory> historyVec = WorkflowSearchHelper.manager.getApprovalHistory(approvalMaster);
		Collections.sort(historyVec, ComparatorUtil.APPROVALLINESORT);

		int pos = 0;
		// int idx = 1;
		for (KETWfmApprovalHistory history : historyVec) {
		    String hisName = history.getActivityName();
		    if (KETWfmApprovalHistoryDTO.REQUEST.equals(hisName) || KETWfmApprovalHistoryDTO.REWORK.equals(hisName)) {
			pos = history.getSeqNum();
		    }
		}
		for (KETWfmApprovalHistory history : historyVec) {
		    if (history.getSeqNum() > pos && history.getCompletedDate() != null) {
			flag = false;
			break;
		    }
		}
	    } else {
		flag = false;
	    }
	} else {
	    flag = false;
	}
	return flag;
    }

    @Override
    public boolean doCancelApproval(String pbooid) throws Exception {

	boolean flag = false;
	Transaction trx = new Transaction();
	SessionContext current = SessionContext.getContext();
	try {
	    SessionHelper.manager.setAdministrator();
	    trx.start();
	    LifeCycleManaged pbo = (LifeCycleManaged) CommonUtil.getObject(pbooid);
	    KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster(pbo);
	    WfProcess process = WorkflowSearchHelper.manager.getProcess(approvalMaster);
	    // 결재 프로세스 링크 삭제, 프로세스 삭제
	    if (process != null) {
		QueryResult qr = PersistenceHelper.manager.navigate(process, KETWfmMasterProcessLink.APP_MASTER_ROLE,
		        KETWfmMasterProcessLink.class, false);
		if (qr != null && qr.hasMoreElements()) {
		    KETWfmMasterProcessLink link = (KETWfmMasterProcessLink) qr.nextElement();
		    PersistenceHelper.manager.delete(link);
		    // WfEngineHelper.service.deleteProcess(process);
		    WfEngineHelper.service.terminateObjectsRunningWorkflows(pbo);
		}
	    }
	    // 결재 이력 삭제
	    @SuppressWarnings("unchecked")
	    Vector<KETWfmApprovalHistory> historyVec = WorkflowSearchHelper.manager.getApprovalHistory(approvalMaster);
	    for (KETWfmApprovalHistory ketWfmApprovalHistory : historyVec) {
		PersistenceHelper.manager.delete(ketWfmApprovalHistory);
	    }
	    // 결재 라인 삭제
	    @SuppressWarnings("unchecked")
	    Vector<KETWfmApprovalLine> lineVec = WorkflowSearchHelper.manager.getApprovalLine(approvalMaster);
	    for (KETWfmApprovalLine ketWfmApprovalLine : lineVec) {
		PersistenceHelper.manager.delete(ketWfmApprovalLine);
	    }
	    // 결재 마스터 수정
	    approvalMaster.setStartFlag(false);
	    PersistenceHelper.manager.modify(approvalMaster);

	    // 라이프사이클 초기화
	    LifeCycleHelper.service.reassign(pbo, pbo.getState().getLifeCycleId());

	    // TODO Jason, 2014-08-19, ECM pbo별 후처리 부분 추가 필요.
	    ReferenceFactory rf = new ReferenceFactory();
	    Persistable obj = rf.getReference(pbooid).getObject();

	    if (obj instanceof KETMoldChangeOrder) {
		KETMoldChangeOrder eco = (KETMoldChangeOrder) obj;

		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_ECO_LC", WTContainerHelper.service.getExchangeRef());
		eco = (KETMoldChangeOrder) LifeCycleHelper.service.reassign(eco, paramLifeCycleTemplateReference);

		// ECA 처리
		Object[] ecas = EcmUtil.getChangeActivities(eco);
		if (ecas != null) {
		    KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();

		    for (int cnt = 0; cnt < ecas.length; cnt++) {

			// ECA
			KETMoldChangeActivity eca = (KETMoldChangeActivity) ecas[cnt];

			String activityType = StringUtils.stripToEmpty(eca.getActivityType());
			if (!activityType.equals("4")) {

			    // BOM
			    Vector<KETBomEcoHeader> bomVec = new Vector<KETBomEcoHeader>();
			    QueryResult qrLink = PersistenceHelper.manager.navigate(eca, "bom", KETMoldECABomLink.class, false);
			    while (qrLink.hasMoreElements()) {
				KETMoldECABomLink ketMoldECABomLink = (KETMoldECABomLink) qrLink.nextElement();

				KETBomEcoHeader bomHeader = ketMoldECABomLink.getBom();
				bomVec.addElement(bomHeader);

				// 부품 상태도 검토중으로 변경
				String beforePartOid = ketMoldECABomLink.getBeforePartOid();
				WTPart wtPart = (WTPart) CommonUtil.getObject(beforePartOid);
				String partNumber = wtPart.getNumber();
				String preVersion = ketMoldECABomLink.getPreVersion();
				String afterVersion = ketMoldECABomLink.getAfterVersion();
				// 초도라는 얘기
				if (afterVersion == null || preVersion.equals(afterVersion)) {
				    String partOid = ketBOMQueryBean.getPartOid(partNumber, preVersion);
				    wtPart = (WTPart) CommonUtil.getObject(partOid);
				}
				// 설변이라는 얘기
				else {
				    String partOid = ketBOMQueryBean.getPartOid(partNumber, afterVersion);
				    wtPart = (WTPart) CommonUtil.getObject(partOid);
				}

				if (wtPart != null) {
				    // HEENEETODO : 방어코드 Part에 L/C를 reassign 한다.
				    LifeCycleTemplate partLifeCycle = LifeCycleHelper.service.getLifeCycleTemplate("KET_PART_LC",
					    wtPart.getContainerReference());
				    wtPart = (WTPart) LifeCycleHelper.service.reassign(wtPart,
					    partLifeCycle.getLifeCycleTemplateReference());

				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) wtPart, State.toState("INWORK"));
				}
			    }

			    for (int i = 0; i < bomVec.size(); i++) {
				KETBomEcoHeader bomHeader = bomVec.elementAt(i);
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, State.toState("INWORK"));

			    }

			    // 도면
			    Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromMoldECA(eca);

			    for (int i = 0; i < epmVec.size(); i++) {
				EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("INWORK"));
			    }

			    // ECA
			    Timestamp completeDate = DateUtil.getCurrentTimestamp();
			    eca.setCompleteDate(completeDate);
			    // Added by MJOH, 2011-03-30
			    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////
			    eca.setActivityStatus("WORKCOMPLETED");
			    PersistenceHelper.manager.save(eca);
			    eca = (KETMoldChangeActivity) PersistenceHelper.manager.refresh(eca);
			    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) eca, State.toState("WORKCOMPLETED"));

			}

		    }

		}

		// ECO 처리
		/*
	         * LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) eco, State.toState("ACTIVITYCOMPLETED"));
	         */

	    } else if (obj instanceof KETProdChangeOrder) {
		KETProdChangeOrder eco = (KETProdChangeOrder) obj;

		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_ECO_LC", WTContainerHelper.service.getExchangeRef());
		eco = (KETProdChangeOrder) LifeCycleHelper.service.reassign(eco, paramLifeCycleTemplateReference);

		// ECA 처리
		Object[] ecas = EcmUtil.getChangeActivities(eco);
		if (ecas != null) {
		    KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();

		    for (int cnt = 0; cnt < ecas.length; cnt++) {

			// ECA
			KETProdChangeActivity eca = (KETProdChangeActivity) ecas[cnt];

			String activityType = StringUtils.stripToEmpty(eca.getActivityType());
			if (!activityType.equals("3") && !activityType.equals("4")) {

			    // BOM
			    Vector<KETBomEcoHeader> bomVec = new Vector<KETBomEcoHeader>();
			    QueryResult qrLink = PersistenceHelper.manager.navigate(eca, "bom", KETProdECABomLink.class, false);
			    while (qrLink.hasMoreElements()) {
				KETProdECABomLink ketProdECABomLink = (KETProdECABomLink) qrLink.nextElement();

				KETBomEcoHeader bomHeader = ketProdECABomLink.getBom();
				bomVec.addElement(bomHeader);

				// 부품 상태도 검토중으로 변경
				String beforePartOid = ketProdECABomLink.getBeforePartOid();
				WTPart wtPart = (WTPart) CommonUtil.getObject(beforePartOid);
				String partNumber = wtPart.getNumber();
				String preVersion = ketProdECABomLink.getPreVersion();
				String afterVersion = ketProdECABomLink.getAfterVersion();
				// 초도라는 얘기
				if (afterVersion == null || preVersion.equals(afterVersion)) {
				    String partOid = ketBOMQueryBean.getPartOid(partNumber, preVersion);
				    wtPart = (WTPart) CommonUtil.getObject(partOid);
				}
				// 설변이라는 얘기
				else {
				    String partOid = ketBOMQueryBean.getPartOid(partNumber, afterVersion);
				    wtPart = (WTPart) CommonUtil.getObject(partOid);
				}

				if (wtPart != null) {
				    // HEENEETODO : 방어코드 Part에 L/C를 reassign 한다.
				    LifeCycleTemplate partLifeCycle = LifeCycleHelper.service.getLifeCycleTemplate("KET_PART_LC",
					    wtPart.getContainerReference());
				    wtPart = (WTPart) LifeCycleHelper.service.reassign(wtPart,
					    partLifeCycle.getLifeCycleTemplateReference());

				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) wtPart, State.toState("INWORK"));
				}
			    }

			    for (int i = 0; i < bomVec.size(); i++) {
				KETBomEcoHeader bomHeader = bomVec.elementAt(i);
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, State.toState("INWORK"));

			    }

			    // 도면
			    Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromProdECA(eca);

			    for (int i = 0; i < epmVec.size(); i++) {
				EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("INWORK"));
			    }

			    // ECA
			    Timestamp completeDate = DateUtil.getCurrentTimestamp();
			    eca.setCompleteDate(completeDate);
			    // Added by MJOH, 2011-03-30
			    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////
			    eca.setActivityStatus("WORKCOMPLETED");
			    PersistenceHelper.manager.save(eca);
			    eca = (KETProdChangeActivity) PersistenceHelper.manager.refresh(eca);
			    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) eca, State.toState("WORKCOMPLETED"));

			}

		    }

		}

		// ECO 처리
		/*
	         * LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) eco, State.toState("ACTIVITYCOMPLETED"));
	         */

	    } else if (obj instanceof KETDqmRaise) {
		KETDqmRaise ketDqmRaise = (KETDqmRaise) obj;

		QuerySpec query = new QuerySpec();
		SearchCondition sc = null;
		int idxHeaer = query.appendClassList(KETDqmHeader.class, true);

		sc = new SearchCondition(KETDqmHeader.class, "raiseReference.key.id", SearchCondition.EQUAL,
		        CommonUtil.getOIDLongValue(ketDqmRaise));
		query.appendWhere(sc, new int[] { idxHeaer });

		QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);

		while (qr.hasMoreElements()) {
		    Object[] tempObj = (Object[]) qr.nextElement();
		    KETDqmHeader ketDqmHeader = (KETDqmHeader) tempObj[0];

		    ketDqmHeader.setDqmStateCode("RAISEINWORK");
		    ketDqmHeader.setDqmStateName("등록");

		    PersistenceHelper.manager.modify(ketDqmHeader);
		}

	    } else if (obj instanceof KETDqmAction) {
		KETDqmAction ketDqmAction = (KETDqmAction) obj;

		QuerySpec query = new QuerySpec();
		SearchCondition sc = null;
		int idxHeaer = query.appendClassList(KETDqmHeader.class, true);

		sc = new SearchCondition(KETDqmHeader.class, "actionReference.key.id", SearchCondition.EQUAL,
		        CommonUtil.getOIDLongValue(ketDqmAction));
		query.appendWhere(sc, new int[] { idxHeaer });

		QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);

		while (qr.hasMoreElements()) {
		    Object[] tempObj = (Object[]) qr.nextElement();
		    KETDqmHeader ketDqmHeader = (KETDqmHeader) tempObj[0];

		    ketDqmHeader.setDqmStateCode("ACTIONINWORK");
		    ketDqmHeader.setDqmStateName("담당자접수");

		    PersistenceHelper.manager.modify(ketDqmHeader);
		}
	    } else if (obj instanceof KETSampleRequest) {
		KETSampleRequest ketSampleRequest = (KETSampleRequest) obj;

		ketSampleRequest.setSampleRequestStateCode("INWORK");
		ketSampleRequest.setSampleRequestStateName("작업중");
		PersistenceHelper.manager.modify(ketSampleRequest);
	    } else if (obj instanceof E3PSProject) {// 프로젝트 상신 취소시 original copy가 있으면 일정변경 아니면 최초(등록중) 상태로 변경됨
		E3PSProject project2 = (E3PSProject) obj;
		QueryResult rs = PersistenceHelper.manager.navigate(project2, CheckoutLink.ORIGINAL_COPY_ROLE, CheckoutLink.class, true);

		State reassignState = State.toState("PMOINWORK");
		if (rs != null && rs.size() > 0) {
		    reassignState = State.toState("PLANCHANGE");
		    // LifeCycleHelper.service.setLifeCycleState(project, State.toState("PLANCHANGE"));
		}

		if (obj instanceof ReviewProject) {
		    ReviewProject project = (ReviewProject) obj;
		    LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference("KET_REVIEW_PMS_LC",
			    WTContainerHelper.service.getExchangeRef());
		    project = (ReviewProject) LifeCycleHelper.service.reassign(project, lctRef, project.getContainerReference());
		    Kogger.debug(getClass(), "[doCancelApproval] ReviewProject reassign KET_REVIEW_PMS_LC");
		    project = (ReviewProject) LifeCycleHelper.service.setLifeCycleState(project, reassignState, true);
		    Kogger.debug(getClass(), "[doCancelApproval] ReviewProject setLifeCycleState" + reassignState.toString());
		} else if (obj instanceof ProductProject) {
		    ProductProject project = (ProductProject) obj;
		    LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference("KET_PRODUCT_PMS_LC",
			    WTContainerHelper.service.getExchangeRef());
		    project = (ProductProject) LifeCycleHelper.service.reassign(project, lctRef, project.getContainerReference());
		    Kogger.debug(getClass(), "[doCancelApproval] ProductProject reassign KET_PRODUCT_PMS_LC");
		    project = (ProductProject) LifeCycleHelper.service.setLifeCycleState(project, reassignState, true);
		    Kogger.debug(getClass(), "[doCancelApproval] ProductProject setLifeCycleState" + reassignState.toString());

		} else if (obj instanceof MoldProject) {
		    MoldProject project = (MoldProject) obj;
		    LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference("KET_MOLD_PMS_LC",
			    WTContainerHelper.service.getExchangeRef());
		    project = (MoldProject) LifeCycleHelper.service.reassign(project, lctRef, project.getContainerReference());
		    Kogger.debug(getClass(), "[doCancelApproval] MoldProject reassign KET_MOLD_PMS_LC");
		    project = (MoldProject) LifeCycleHelper.service.setLifeCycleState(project, reassignState, true);
		    Kogger.debug(getClass(), "[doCancelApproval] MoldProject setLifeCycleState" + reassignState.toString());

		} else if (obj instanceof WorkProject) {
		    WorkProject project = (WorkProject) obj;
		    LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference("KET_PRODUCT_PMS_LC",
			    WTContainerHelper.service.getExchangeRef());
		    project = (WorkProject) LifeCycleHelper.service.reassign(project, lctRef, project.getContainerReference());
		    Kogger.debug(getClass(), "[doCancelApproval] WorkProject reassign KET_PRODUCT_PMS_LC");
		    project = (WorkProject) LifeCycleHelper.service.setLifeCycleState(project, reassignState, true);
		    Kogger.debug(getClass(), "[doCancelApproval] WorkProject setLifeCycleState" + reassignState.toString());

		} else if (obj instanceof KETInvestMaster) {
		    KETInvestMaster im = (KETInvestMaster) obj;
		    LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
			    "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		    im = (KETInvestMaster) LifeCycleHelper.service.reassign(im, paramLifeCycleTemplateReference);
		    im.setInvestState("IST002");
		    PersistenceServerHelper.manager.update(im);

		} else if ((obj instanceof CostReport)) {
		    CostReport report = (CostReport) obj;
		    LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
			    "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		    report = (CostReport) LifeCycleHelper.service.reassign(report, paramLifeCycleTemplateReference);

		    Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

		} else if (obj instanceof KETIssueMaster) {
		    KETIssueMaster im = (KETIssueMaster) obj;
		    LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
			    "KET_COMMON_LC", WTContainerHelper.service.getExchangeRef());
		    im = (KETIssueMaster) LifeCycleHelper.service.reassign(im, paramLifeCycleTemplateReference);

		}
	    }

	    flag = true;
	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	    trx = null;
	    throw e;
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	    SessionContext.setContext(current);
	}
	return flag;
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public boolean doRequestDistribute(HashMap<String, Object> request) throws Exception {
	String[] pboOids = new String[1];

	if (request.get("pboOids") != null) {
	    if (request.get("pboOids") instanceof String[]) {
		pboOids = (String[]) request.get("pboOids");
	    } else {
		pboOids[0] = (String) request.get("pboOids");
	    }
	}

	String[] receivers = null;
	if (StringUtil.checkString((String) request.get("receiver"))) {
	    receivers = StringUtil.checkNull((String) request.get("receiver")).split(",");
	}
	String[] references = StringUtil.checkNull((String) request.get("reference")).split(",");
	String textfield = StringUtil.checkNull((String) request.get("textfield"));
	if (pboOids == null) {
	    pboOids = new String[1];
	    pboOids[0] = (String) request.get("pboOids");
	}
	boolean doNotCreate = false;
	if (request.get("doNotCreate") != null) {
	    doNotCreate = (boolean) request.get("doNotCreate");
	}
	Transaction trx = new Transaction();
	boolean flag = false;
	try {
	    if (pboOids.length > 0) {
		String request_p = WFMProperties.getInstance().getString("wfm.request.distribute");
		String receiver_p = WFMProperties.getInstance().getString("wfm.receiver");
		String reference_p = WFMProperties.getInstance().getString("wfm.reference");
		for (String pboOid : pboOids) {
		    LifeCycleManaged pbo = (LifeCycleManaged) CommonUtil.getObject(pboOid);
		    KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster(pbo);
		    // create approval line
		    Vector<KETWfmApprovalLine> lineVec = WorkflowSearchHelper.manager.getApprovalLine(approvalMaster);
		    int ref_seq = 0;
		    int rec_seq = 0;
		    for (KETWfmApprovalLine line : lineVec) {
			if ("receiver".equals(line.getApprovalType())) {
			    if (rec_seq < line.getApprovalOrder())
				rec_seq = line.getApprovalOrder();
			}
			if ("reference".equals(line.getApprovalType())) {
			    if (ref_seq < line.getApprovalOrder())
				ref_seq = line.getApprovalOrder();
			}
		    }
		    if (receivers != null && receivers.length > 0) {
			for (String receiver : receivers) {
			    if (StringUtil.checkString(receiver)) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				hash.put("order", ++rec_seq); // 결재선 지정 시 순서
				hash.put("type", "receiver"); // 결재 단계 (합의전검토/합의/검토및승인)
				hash.put("userID", receiver); // 해당 사용자 id
				hash.put("master", approvalMaster); // 결재마스터
				KETWfmHelper.service.createLine(hash);
			    }
			}
		    }
		    if (references != null && references.length > 0) {
			for (String reference : references) {
			    if (StringUtil.checkString(reference)) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				hash.put("order", ++ref_seq); // 결재선 지정 시 순서
				hash.put("type", "reference"); // 결재 단계 (합의전검토/합의/검토및승인)
				hash.put("userID", reference); // 해당 사용자 id
				hash.put("master", approvalMaster); // 결재마스터
				KETWfmHelper.service.createLine(hash);
			    }
			}
		    }

		    Vector<KETWfmApprovalHistory> historyVec = WorkflowSearchHelper.manager.getApprovalHistory(approvalMaster);
		    int his_seq = historyVec.size();

		    if (!doNotCreate) {
			// create approval history

			// 추가배포요청 이력작성
			KETWfmApprovalHistory hisAttr = KETWfmApprovalHistory.newKETWfmApprovalHistory();
			hisAttr.setActivityName(request_p);
			Timestamp completeDate = DateUtil.getCurrentTimestamp();
			hisAttr.setCompletedDate(completeDate);
			hisAttr.setComments(textfield);
			hisAttr.setDecision(request_p);
			hisAttr.setAppMaster(approvalMaster);
			hisAttr.setOwner(SessionHelper.manager.getPrincipalReference());
			hisAttr.setSeqNum(++his_seq);
			PersistenceHelper.manager.save(hisAttr);
		    }
		    // 수신이력작성
		    if (receivers != null && receivers.length > 0) {
			// WfProcess create
			Enumeration<WfTemplateObject> allTemplates = WfDefinerHelper.service.getAllTemplates();
			WfTemplateObject paramWfTemplateObject = null;
			while (allTemplates.hasMoreElements()) {
			    WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();
			    if (wfTemplate.getName().equals("KET_DISTRIBUTE_WF")) {
				paramWfTemplateObject = wfTemplate;
			    }
			}
			WTContainerRef containerRef = null;
			if (pbo instanceof WTContained) {
			    containerRef = ((WTContained) pbo).getContainerReference();
			} else {
			    containerRef = WTContainerHelper.service.getExchangeRef();
			}
			WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, pbo.getTeamId().getObject(),
			        containerRef);
			WfEngineServerHelper.service.setPrimaryBusinessObject(process, (WTObject) pbo);

			for (String receiver : receivers) {
			    if (StringUtil.checkString(receiver)) {
				// 수신자만 WorkItem 생성을 하기위해 WfProcess에 Role Member를 assign 한다.
				addParticipant(process, Role.toRole("RECIPIENT"), receiver);
				KETWfmApprovalHistory hisAttr = KETWfmApprovalHistory.newKETWfmApprovalHistory();
				hisAttr.setActivityName(receiver_p);
				hisAttr.setCompletedDate(null);
				hisAttr.setDecision("");
				hisAttr.setAppMaster(approvalMaster);
				hisAttr.setOwner(WTPrincipalReference.newWTPrincipalReference(OrganizationServicesMgr.getUser(receiver)));
				hisAttr.setSeqNum(++his_seq);
				PersistenceHelper.manager.save(hisAttr);
			    }
			}
			// WfProcess start
			ProcessData data = process.getContext();
			WfEngineHelper.service.startProcess(process, data, 1);
		    }
		    // 참조이력작성
		    if (references != null && references.length > 0) {
			for (String reference : references) {
			    if (StringUtil.checkString(reference)) {
				KETWfmApprovalHistory hisAttr = KETWfmApprovalHistory.newKETWfmApprovalHistory();
				hisAttr.setActivityName(reference_p);
				hisAttr.setCompletedDate(null);
				hisAttr.setDecision("");
				hisAttr.setAppMaster(approvalMaster);
				hisAttr.setOwner(WTPrincipalReference.newWTPrincipalReference(OrganizationServicesMgr.getUser(reference)));
				hisAttr.setSeqNum(++his_seq);
				PersistenceHelper.manager.save(hisAttr);
			    }
			}
		    }
		}
	    }
	    flag = true;
	    trx.commit();
	    trx = null;

	    List<WTUser> listToUser = null;

	    WTUser wtUserFrom = (WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal();

	    for (String pboOid : pboOids) {

		LifeCycleManaged pbo = (LifeCycleManaged) CommonUtil.getObject(pboOid);

		if (receivers != null && receivers.length > 0) {
		    for (String receiver : receivers) {
			if (StringUtil.checkString(receiver)) {
			    WTUser toUser = KETObjectUtil.getUser(receiver);
			    listToUser = new ArrayList<WTUser>();
			    listToUser.add(toUser);
			    KETMailHelper.service.sendFormMail("08005", "ApprovalNoticeMail.html", pbo, wtUserFrom, listToUser);// 수신 메일 발송
			}
		    }
		}

		if (references != null && references.length > 0) {
		    for (String reference : references) {
			if (StringUtil.checkString(reference)) {
			    WTUser toUser = KETObjectUtil.getUser(reference);
			    listToUser = new ArrayList<WTUser>();
			    listToUser.add(toUser);
			    KETMailHelper.service.sendFormMail("08006", "ApprovalNoticeMail.html", pbo, wtUserFrom, listToUser);// 참조 메일 발송
			}
		    }
		}
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	    trx = null;
	    throw e;
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}
	return flag;
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public boolean delegateWorkItem(WorkItem workitem, WTPrincipal newOwner) throws Exception {

	boolean flag = false;
	Transaction trx = new Transaction();
	try {
	    trx.start();
	    WTPrincipal oldOwner = OwnershipHelper.getOwner(workitem);
	    WTPrincipalReference oldOwnerReference = WTPrincipalReference.newWTPrincipalReference(oldOwner);
	    WTPrincipalReference newOwnerReference = WTPrincipalReference.newWTPrincipalReference(newOwner);
	    WorkflowServerHelper.service.revokeTaskBasedRights(workitem);
	    OwnershipServerHelper.service.changeOwner(workitem, newOwner, false);
	    workitem.setStatus(WfAssignmentState.POTENTIAL);
	    workitem.setOrigOwner(oldOwnerReference);
	    WorkflowServerHelper.service.setTaskBasedRights(workitem, newOwnerReference);
	    PersistenceServerHelper.manager.update(workitem);
	    WorkflowHelper.service.delegate(workitem, newOwner);

	    String comments = "'" + ((WTUser) oldOwner).getFullName() + "'로 부터 '" + ((WTUser) newOwner).getFullName() + "'으로 수신자변경됨";
	    Persistable pbo = workitem.getPrimaryBusinessObject().getObject();
	    KETWfmApprovalMaster master = WorkflowSearchHelper.manager.getMaster(pbo);
	    Vector<KETWfmApprovalHistory> hisvec = WorkflowSearchHelper.manager.getApprovalHistory(master);
	    Collections.sort(hisvec, ComparatorUtil.APPROVALLINESORT);
	    KETWfmApprovalHistory history = getWfmApprovalHistory(hisvec, oldOwner);

	    KETWfmApprovalHistory delegateHistory = KETWfmApprovalHistory.newKETWfmApprovalHistory();
	    delegateHistory.setActivityName(history.getActivityName());
	    delegateHistory.setAppMaster(master);
	    delegateHistory.setConditionalAccept(history.isConditionalAccept());
	    delegateHistory.setLast(history.isLast());
	    delegateHistory.setOwner(newOwnerReference);
	    delegateHistory.setSeqNum(history.getSeqNum() + 1);
	    PersistenceHelper.manager.save(delegateHistory);

	    history.setDecision(KETWfmApprovalHistoryDTO.DELEGATE);
	    history.setCompletedDate(DateUtil.getCurrentTimestamp());
	    history.setComments(comments);
	    if (history.isLast())
		history.setLast(false);
	    PersistenceHelper.manager.save(history);

	    Kogger.debug(getClass(), "old history seq : " + history.getSeqNum());
	    Kogger.debug(getClass(), "new history seq : " + (history.getSeqNum() + 1));

	    int pos = history.getSeqNum();
	    for (int i = pos; i < hisvec.size(); i++) {
		KETWfmApprovalHistory history2 = hisvec.elementAt(i);
		Kogger.debug(getClass(), "change history seq : " + history2.getSeqNum() + " --> " + (history2.getSeqNum() + 1));
		history2.setSeqNum((history2.getSeqNum() + 1));
		PersistenceHelper.manager.save(history2);
	    }

	    trx.commit();
	    flag = true;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(getClass(), e);
	    flag = false;
	    throw e;
	}
	return flag;
    }

    private KETWfmApprovalHistory getWfmApprovalHistory(Vector<KETWfmApprovalHistory> histories, WTPrincipal owner) {

	KETWfmApprovalHistory history = null;
	for (KETWfmApprovalHistory history2 : histories) {
	    if (history2.getOwner().getObject().equals(owner) && history2.getCompletedDate() == null)
		history = history2;
	}
	return history;
    }

    private void addParticipant(TeamManaged lcm, Role role, String id) {

	try {
	    Team team = TeamHelper.service.getTeam((TeamManaged) lcm);
	    team.addPrincipal(role, id == null ? null : OrganizationServicesMgr.getPrincipal(id));
	} catch (TeamException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<WorkItemDTO> getUncompletedWorkItems() throws Exception {

	QuerySpec spec = getFilterUncompletedWorkItems(new WorkItemDTO(), false);
	// spec.appendAnd();
	// spec.appendRowNumCondition(20);
	spec.setAdvancedQueryEnabled(true);
	Kogger.debug(getClass(), spec);
	QueryResult result = PersistenceHelper.manager.find(spec);
	List<WorkItemDTO> list = new ArrayList<WorkItemDTO>();
	int i = 0;
	while (result.hasMoreElements()) {
	    Object[] objects = (Object[]) result.nextElement();
	    WorkItem item = (WorkItem) objects[0];
	    try {
		WorkItemDTO dto = new WorkItemDTO(item);
		if (i < 20) {
		    list.add(dto);
		    i++;
		} else {
		    break;
		}
	    } catch (Exception e) {
		continue;
	    }
	}

	// WTPrincipal owner = SessionHelper.manager.getPrincipal();
	// Integer queryLimit = new Integer(20);
	// boolean isTodo = false;
	// QuerySpec spec = null;
	// List<WorkItemDTO> list = new ArrayList<WorkItemDTO>();
	// try {
	// SQLFunction localSQLFunction = getTruncateFunction(new ClassAttribute(WorkItem.class, "thePersistInfo.createStamp"));
	// spec = buildOwnerQuery(owner);
	// SearchCondition localSearchCondition = new SearchCondition(WorkItem.class, "completedBy", true);
	// spec.appendAnd();
	// spec.appendWhere(localSearchCondition, new int[] { 0 });
	// int a_idx = spec.appendClassList(WfAssignedActivity.class, false);
	// spec.appendAnd();
	// spec.appendWhere(new SearchCondition(new ClassAttribute(WorkItem.class, "source.key.id"), "=", new
	// ClassAttribute(WfAssignedActivity.class,
	// "thePersistInfo.theObjectIdentifier.id")),
	// new int[] { 0, a_idx });
	// spec.appendAnd();
	// spec.appendOpenParen();
	// spec.appendWhere(new SearchCondition(WfAssignedActivity.class, WfAssignedActivity.NAME, (isTodo) ? "=" : "<>", "수행담당자"), new
	// int[] { a_idx });
	// if (!isTodo)
	// spec.appendAnd();
	// else
	// spec.appendOr();
	// spec.appendWhere(new SearchCondition(WfAssignedActivity.class, WfAssignedActivity.NAME, (isTodo) ? "=" : "<>", "담당자지정"), new
	// int[] { a_idx });
	// spec.appendCloseParen();
	// spec.appendAnd();
	// spec.appendRowNumCondition(queryLimit.intValue());
	// spec.appendOrderBy(new OrderBy(localSQLFunction, true), new int[] { 0 });
	// spec.appendOrderBy(WorkItem.class, "role", false);
	// // int i = queryLimit.intValue();
	// // if (i > 0) {
	// spec.setAdvancedQueryEnabled(true);
	// // spec.setQueryLimit(i);
	// // }
	// QueryResult result = PersistenceHelper.manager.find(spec);
	// if (result != null && result.size() > 0) {
	// while (result.hasMoreElements()) {
	// Object[] objects = (Object[]) result.nextElement();
	// WorkItem item = (WorkItem) objects[0];
	// try {
	// WorkItemDTO dto = new WorkItemDTO(item);
	// list.add(dto);
	// } catch (Exception e) {
	// continue;
	// }
	// }
	// }
	//
	// } catch (PartialResultException localPartialResultException) {
	// if (logger.isDebugEnabled()) {
	// Kogger.debug(getClass(), localPartialResultException.getLocalizedMessage(), localPartialResultException);
	// }
	// } catch (QueryException localQueryException) {
	// if (logger.isDebugEnabled()) {
	// Kogger.debug(getClass(), localQueryException.getLocalizedMessage(), localQueryException);
	// }
	// throw new WfException(localQueryException, null);
	// } catch (WTPropertyVetoException localWTPropertyVetoException) {
	// if (logger.isDebugEnabled()) {
	// Kogger.debug(getClass(), localWTPropertyVetoException.getLocalizedMessage(), localWTPropertyVetoException);
	// }
	// throw new WTException(localWTPropertyVetoException);
	// }
	return list;
    }

    @Override
    public void deleteWfProcessTest() throws Exception {

	Class<WfProcess> processclass = WfProcess.class;
	Class<WfProcessTemplate> templateclass = WfProcessTemplate.class;
	QuerySpec spec = new QuerySpec();
	int p_idx = spec.appendClassList(processclass, true);
	int t_idx = spec.appendClassList(templateclass, false);

	spec.appendWhere(new SearchCondition(new ClassAttribute(processclass, "template.key.id"), SearchCondition.EQUAL,
	        new ClassAttribute(templateclass, "thePersistInfo.theObjectIdentifier.id")), new int[] { p_idx, t_idx });
	SearchUtil.appendEQUAL(spec, templateclass, WfProcessTemplate.NAME, "KET_COMMON_WF", t_idx);
	SearchUtil.appendEQUAL(spec, templateclass, "checkoutInfo.state", "wrk", t_idx);
	// SearchUtil.appendEQUAL(spec, processclass, "state", WfState.OPEN_RUNNING.toString(), p_idx);

	Kogger.debug(getClass(), spec.toString());

	QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);
	while (result.hasMoreElements()) {
	    Object[] objects = (Object[]) result.nextElement();
	    WfProcess process = (WfProcess) objects[0];
	    System.out.print("pbo : " + process.getBusinessObjReference() + " / process : " + process.getName());
	    Persistable pbo = CommonUtil.getObject(process.getBusinessObjReference());
	    if (pbo != null) {
		WfEngineHelper.service.terminateObjectsRunningWorkflows(pbo);
		System.out.print(" [terminated]");
	    }
	}
    }

    @Override
    public int getFilterUncompletedWorkItemCount() throws Exception {
	int listWorkItemCnt = 0;
	WorkItemDTO workItemDTO = new WorkItemDTO();
	workItemDTO.setCommand("listWorkItem");
	QuerySpec query = getFilterUncompletedWorkItems(workItemDTO, false);
	if (query.isAdvancedQuery()) {
	    if (!query.isAdvancedQueryEnabled())
		query.setAdvancedQueryEnabled(true);
	}
	QueryResult result = PersistenceHelper.manager.find((StatementSpec) query);
	if (result != null && result.size() > 0)
	    listWorkItemCnt = result.size();
	return listWorkItemCnt;
    }

    @Override
    public WorkItemDTO save(WorkItemDTO paramObject) throws Exception {
	return null;
    }

    @Override
    public WorkItemDTO modify(WorkItemDTO paramObject) throws Exception {
	return null;
    }

    @Override
    public WorkItemDTO delete(WorkItemDTO paramObject) throws Exception {
	return null;
    }

    @Override
    public WorkItem getWorkItem(Persistable pbo, WTUser user) throws Exception {
	// TODO Auto-generated method stub
	WorkItem item = null;
	QuerySpec spec = new QuerySpec();
	int idx = spec.addClassList(WorkItem.class, true);
	SearchUtil.appendEQUAL(spec, WorkItem.class, "primaryBusinessObject.key.classname", CommonUtil.getRefOID(pbo), idx);
	SearchUtil.appendEQUAL(spec, WorkItem.class, "status", WfAssignmentState.POTENTIAL.toString(), idx);
	SearchUtil.appendEQUAL(spec, WorkItem.class, "ownership.owner.key.id", CommonUtil.getOIDLongValue(user), idx);
	Kogger.debug(getClass(), spec.toString());
	QueryResult qr = PersistenceHelper.manager.find(spec);
	if (qr.hasMoreElements()) {
	    Object[] objects = (Object[]) qr.nextElement();
	    item = (WorkItem) objects[0];
	}
	return item;
    }

    private boolean isInProgressMyWorkItem(KETWfmApprovalMaster master) throws Exception {

	boolean flag = false;
	String rejectStr = WFMProperties.getInstance().getString("wfm.reject");
	Vector<KETWfmApprovalHistory> histories = WorkflowSearchHelper.manager.getApprovalHistory(master);
	Collections.sort(histories, ComparatorUtil.APPROVALLINESORT);
	int rejectPos = 0;
	int myPos = 0;
	int rejectCnt = 0;
	boolean progress = false;
	for (KETWfmApprovalHistory history : histories) {
	    String activity = history.getActivityName();
	    if (!(KETWfmApprovalHistoryDTO.REQUEST.equals(activity) || KETWfmApprovalHistoryDTO.RECEIVER.equals(activity) || KETWfmApprovalHistoryDTO.REFERENCE
		    .equals(activity))) {
		if (rejectStr.equals(history.getDecision())) {
		    rejectPos = history.getSeqNum();
		    rejectCnt++;
		}
		if (CommonUtil.getUserIDFromSession().equals(history.getOwner().getName())) {
		    myPos = history.getSeqNum();
		    if (history.getCompletedDate() != null) {
			progress = true;
		    } else {
			progress = false;
		    }
		}
	    }
	}
	if (rejectPos != histories.size() && rejectCnt > 1) {
	    flag = myPos > rejectPos && progress;
	} else if (rejectPos == histories.size()) {
	    if (CommonUtil.getUserIDFromSession().equals(master.getOwner().getName()))
		flag = false;
	    else
		flag = true;
	} else if (rejectPos != histories.size() && rejectCnt == 1) {
	    flag = myPos > rejectPos && progress;
	} else {
	    flag = true;
	}
	// if (CommonUtil.getUserIDFromSession().equals(master.getOwner().getName()) && flag) flag = false;
	return flag;
    }

    @Override
    public void initWfProcessInfo(ObjectReference self) {

	WfProcess process = (WfProcess) self.getObject();
	Persistable pbo = process.getBusinessObjectReference(new ReferenceFactory()).getObject();

	try {
	    // ProcessData data = process.getContext();
	    // KETWfmApprovalMaster master = WorkflowSearchHelper.manager.getMaster(pbo);
	    // Vector<KETWfmApprovalHistory> historyVec = WorkflowSearchHelper.manager.getApprovalHistory(master);
	    // Collections.sort(historyVec, ComparatorUtil.APPROVALLINESORT);
	    //
	    // if (master.getAgreementType().equals("noDiscuss")) {
	    // data.setValue("agreement_type", 0);
	    // }
	    // else if (master.getAgreementType().equals("sequential")) {
	    // data.setValue("agreement_type", 1);
	    // }
	    // else if (master.getAgreementType().equals("parallel")) {
	    // data.setValue("agreement_type", 2);
	    // }
	    // ArrayList<WTUser> reviewerList = new ArrayList<WTUser>();
	    // ArrayList<WTUser> discussList = new ArrayList<WTUser>();
	    // ArrayList<WTUser> submitterList = new ArrayList<WTUser>();
	    // ArrayList<WTUser> receiverList = new ArrayList<WTUser>();
	    // ArrayList<WTUser> referenceList = new ArrayList<WTUser>();
	    //
	    // // String request_p = WFMProperties.getInstance().getString("wfm.request");
	    // String review_p = WFMProperties.getInstance().getString("wfm.review");
	    // String discuss_p = WFMProperties.getInstance().getString("wfm.discuss");
	    // String receiver_p = WFMProperties.getInstance().getString("wfm.receiver");
	    // String reference_p = WFMProperties.getInstance().getString("wfm.reference");
	    // // String reqRecipient_p = WFMProperties.getInstance().getString("wfm.reqrecipient");
	    // // String rework_p = WFMProperties.getInstance().getString("wfm.rework");
	    // // String reject_p = WFMProperties.getInstance().getString("wfm.reject");
	    //
	    // int discussSeq = 0;
	    //
	    // for (KETWfmApprovalHistory history : historyVec) {
	    // String activityName = history.getActivityName();
	    // WTUser owner = (WTUser) history.getOwner().getObject();
	    // if (discuss_p.equals(activityName)) {
	    // if (!discussList.contains(owner)) {
	    // discussList.add(owner);
	    // discussSeq = history.getSeqNum();
	    // }
	    // }
	    // }
	    //
	    // for (KETWfmApprovalHistory history : historyVec) {
	    // String activityName = history.getActivityName();
	    // WTUser owner = (WTUser) history.getOwner().getObject();
	    // if (discussSeq > history.getSeqNum() && review_p.equals(activityName)) {
	    // if (!reviewerList.contains(owner)) reviewerList.add(owner);
	    // }
	    // else if (discussSeq < history.getSeqNum() && review_p.equals(activityName)) {
	    // if (!submitterList.contains(owner)) submitterList.add(owner);
	    // }
	    // else if (receiver_p.equals(activityName)) {
	    // if (!receiverList.contains(owner)) receiverList.add(owner);
	    // }
	    // else if (reference_p.equals(activityName)) {
	    // if (!referenceList.contains(owner)) referenceList.add(owner);
	    // }
	    // }
	    //
	    // if (reviewerList.size() > 0) {
	    // for (int i = 1; i < reviewerList.size() + 1; i++) {
	    // data.setValue("reviewer" + i, reviewerList.get(i - 1));
	    // }
	    // }
	    // if (discussList.size() > 0) {
	    // for (int i = 1; i < discussList.size() + 1; i++) {
	    // if (master.getAgreementType().equals("sequential")) {
	    // data.setValue("sequential" + i, discussList.get(i - 1));
	    // }
	    // else if (master.getAgreementType().equals("parallel")) {
	    // data.setValue("parallel" + i, discussList.get(i - 1));
	    // }
	    // }
	    // }
	    // if (submitterList.size() > 0) {
	    // for (int i = 1; i < submitterList.size() + 1; i++) {
	    // data.setValue("submitter" + i, submitterList.get(i - 1));
	    // }
	    // }
	    // if (submitterList.size() > 0) {
	    // for (int i = 1; i < submitterList.size() + 1; i++) {
	    // data.setValue("submitter" + i, submitterList.get(i - 1));
	    // }
	    // }
	    // process.setContext(data);
	    //
	    // Team team = TeamHelper.service.getTeam((TeamManaged) pbo);
	    // Map rpMap = team.getRolePrincipalMap();
	    // @SuppressWarnings("deprecation")
	    // RolePrincipalTable table = new RolePrincipalTable();
	    // table.putAll(rpMap);
	    // process.setRolePrincipalMap(table);
	    //
	    // PersistenceServerHelper.manager.update(process);

	    Kogger.info(getClass(), "[initWfProcessInfo] WfProcess : " + process.toString());

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	// Logger.info.println("**************************************************************************");
	// Logger.info.println("* Workflow Process Infomation");
	// Logger.info.println("* Workflow template Name : " + process.getTemplate().getName());
	// Logger.info.println("* Workflow Team : " + process.getTeamName());
	// Logger.info.println("* pbo.getTeamName : " + pbo.getTeamName());
	// Logger.info.println("* team.getRolePrincipalMap : " + rpMap);
	// Logger.info.println("* RolePrincipalMap : " + process.getRolePrincipalMap());
	// Logger.info.println("**************************************************************************");
    }

    @Override
    public void doAfterApprovalAction(ObjectReference self) {
	// TODO Auto-generated method stub
	WfProcess process = (WfProcess) self.getObject();
	Persistable obj = process.getBusinessObjectReference(new ReferenceFactory()).getObject();

	try {
	    Kogger.debug(getClass(), "[doAfterApprovalAction] E3PSProject changeStatePBO Action.........");
	    State state = ((LifeCycleManaged) obj).getLifeCycleState();
	    if (state.equals(State.toState("APPROVED"))) {
		Kogger.debug(getClass(), "[doAfterApprovalAction] E3PSProject changeStatePBO APPROVED.........");
		if (obj instanceof ReviewProject) {
		    ReviewProject project = (ReviewProject) obj;
		    State reassignState = State.toState("PROGRESS");
		    LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference("KET_REVIEW_PMS_LC",
			    WTContainerHelper.service.getExchangeRef());
		    project = (ReviewProject) LifeCycleHelper.service.reassign(project, lctRef, project.getContainerReference());
		    Kogger.debug(getClass(), "[doAfterApprovalAction] ReviewProject reassign KET_REVIEW_PMS_LC");
		    project = (ReviewProject) LifeCycleHelper.service.setLifeCycleState(project, reassignState, true);
		    Kogger.debug(getClass(), "[doAfterApprovalAction] ReviewProject setLifeCycleState PROGRESS");
		} else if (obj instanceof ProductProject) {
		    ProductProject project = (ProductProject) obj;
		    State reassignState = State.toState("PROGRESS");
		    LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference("KET_PRODUCT_PMS_LC",
			    WTContainerHelper.service.getExchangeRef());
		    project = (ProductProject) LifeCycleHelper.service.reassign(project, lctRef, project.getContainerReference());
		    Kogger.debug(getClass(), "[doAfterApprovalAction] ProductProject reassign KET_PRODUCT_PMS_LC");
		    project = (ProductProject) LifeCycleHelper.service.setLifeCycleState(project, reassignState, true);
		    Kogger.debug(getClass(), "[doAfterApprovalAction] ProductProject setLifeCycleState PROGRESS");
		} else if (obj instanceof MoldProject) {
		    MoldProject project = (MoldProject) obj;
		    State reassignState = State.toState("PROGRESS");
		    LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference("KET_MOLD_PMS_LC",
			    WTContainerHelper.service.getExchangeRef());
		    project = (MoldProject) LifeCycleHelper.service.reassign(project, lctRef, project.getContainerReference());
		    Kogger.debug(getClass(), "[doAfterApprovalAction] MoldProject reassign KET_MOLD_PMS_LC");
		    project = (MoldProject) LifeCycleHelper.service.setLifeCycleState(project, reassignState, true);
		    Kogger.debug(getClass(), "[doAfterApprovalAction] MoldProject setLifeCycleState PROGRESS");
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

    }

    @Override
    public boolean getStart_flag(ObjectReference self) {
	// TODO Auto-generated method stub
	WfProcess process = (WfProcess) self.getObject();
	boolean start_flag = false;
	try {
	    process = (WfProcess) PersistenceHelper.manager.refresh(process);
	    ProcessData data = process.getContext();
	    if (data != null)
		start_flag = (boolean) data.getValue("start_flag");
	} catch (ObjectNoLongerExistsException e) {
	    Kogger.debug(getClass(), e.getMessage());
	} catch (WTException e) {
	    Kogger.debug(getClass(), e.getMessage());
	}
	return start_flag;
    }

}
