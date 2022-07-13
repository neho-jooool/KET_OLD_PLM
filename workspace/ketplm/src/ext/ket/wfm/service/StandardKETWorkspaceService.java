package ext.ket.wfm.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.change2.WTChangeRequest2;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.PersistentReference;
import wt.fc.QueryResult;
import wt.lifecycle.LifeCycleManaged;
import wt.org.WTPrincipal;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.ownership.OwnershipHelper;
import wt.ownership.OwnershipServerHelper;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pds.StatementSpec;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.query.ClassAttribute;
import wt.query.ColumnListExpression;
import wt.query.CompoundQuerySpec;
import wt.query.KeywordExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SetOperator;
import wt.query.SubSelectExpression;
import wt.query.TableColumn;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.util.WTRuntimeException;
import wt.workflow.engine.ProcessData;
import wt.workflow.engine.WfProcess;
import wt.workflow.engine.WfVariable;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WfAssignmentState;
import wt.workflow.work.WorkItem;
import wt.workflow.work.WorkflowHelper;
import wt.workflow.work.WorkflowServerHelper;
import e3ps.common.code.NumberCode;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.query.E3PSClassTableExpression;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETDocumentPartLink;
import e3ps.dms.entity.KETDocumentProjectLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.entity.KETTechnicalDocument;
import e3ps.ecm.beans.EcmUtil;
import e3ps.ecm.dao.EcmComDao;
import e3ps.ecm.entity.KETChangeRequestExpansion;
import e3ps.ecm.entity.KETCompetentDepartment;
import e3ps.ecm.entity.KETEcrCompetentLink;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETMoldChangeActivityLink;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldECABomLink;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.entity.KETProdChangeActivityLink;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdECABomLink;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleData;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ReviewProject;
import e3ps.project.TaskDependencyLink;
import e3ps.project.TaskMemberLink;
import e3ps.project.TemplateProject;
import e3ps.project.WorkProject;
import e3ps.project.beans.ClassAttributeData;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.service.KETWfmHelper;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.dms.entity.ProjectDocumentDTO;
import ext.ket.dms.service.KETProjectDocumentHelper;
import ext.ket.dqm.entity.KETDqmAction;
import ext.ket.dqm.entity.KETDqmHeader;
import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.dqm.entity.KETDqmTodoAtivity;
import ext.ket.dqm.service.DqmHelper;
import ext.ket.sample.entity.KETSamplePart;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.dto.BaseDTO;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.shared.util.ReflectUtil;
import ext.ket.shared.util.SearchUtil;
import ext.ket.shared.util.SessionUtil;
import ext.ket.wfm.entity.KETTodoDelegateHistory;
import ext.ket.wfm.entity.MyDocumentDTO;
import ext.ket.wfm.entity.MyProjectDTO;
import ext.ket.wfm.entity.MyTaskDTO;
import ext.ket.wfm.entity.WorkItemDTO;

/**
 * @클래스명 : StandardKETWorkspaceService
 * @작성자 : Jason, Han
 * @작성일 : 2014. 7. 9.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class StandardKETWorkspaceService extends StandardManager implements KETWorkspaceService, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3379861132769451662L;

    public static StandardKETWorkspaceService newStandardKETWorkspaceService() throws WTException {
	StandardKETWorkspaceService instance = new StandardKETWorkspaceService();
	instance.initialize();
	return instance;
    }

    @Override
    public List<BaseDTO> find(BaseDTO paramObject) throws Exception {

	StatementSpec query = null;
	List<BaseDTO> dtos = new ArrayList<BaseDTO>();
	QueryResult result = null;
	if ("listMyTask".equals(paramObject.getCommand())) {
	    query = getFilterListMyTaskQuerySpec(paramObject);
	    Kogger.info(getClass(), query);
	    if (query.isAdvancedQuery()) {
		if (!query.isAdvancedQueryEnabled())
		    query.setAdvancedQueryEnabled(true);
	    }
	    result = PersistenceServerHelper.manager.query(query);

	    MyTaskDTO dto = (MyTaskDTO) paramObject;
	    if (dto.isOnlyResult()) {
		dto.setTotalSize(result.size());
		dtos.add(dto);
	    } else {
		while (result.hasMoreElements()) {
		    Object[] objArr = (Object[]) result.nextElement();
		    dtos.add(new MyTaskDTO((E3PSTask) objArr[0]));
		}
	    }

	} else if ("listMyProject".equals(paramObject.getCommand())) {
	    query = getListMyProjectQuerySpec(paramObject);
	    Kogger.info(getClass(), query);
	    if (query.isAdvancedQuery()) {
		if (!query.isAdvancedQueryEnabled())
		    query.setAdvancedQueryEnabled(true);
	    }
	    result = PersistenceServerHelper.manager.query(query);

	    MyProjectDTO dto = (MyProjectDTO) paramObject;
	    if (dto.isOnlyResult()) {
		dto.setTotalSize(result.size());
		dtos.add(dto);
	    } else {
		while (result.hasMoreElements()) {
		    Object[] objArr = (Object[]) result.nextElement();
		    String className = (String) objArr[0];
		    BigDecimal decimal = (BigDecimal) objArr[1];
		    long id = decimal.longValue();
		    E3PSProject project = (E3PSProject) CommonUtil.getObject(className + ":" + id);
		    dtos.add(new MyProjectDTO((E3PSProject) project));
		}
	    }

	} else if ("listMyTodo".equals(paramObject.getCommand())) {
	    query = KETWorkflowHelper.service.getFilterUncompletedWorkItems((WorkItemDTO) paramObject, true);
	    Kogger.info(getClass(), query);
	    if (query.isAdvancedQuery()) {
		if (!query.isAdvancedQueryEnabled())
		    query.setAdvancedQueryEnabled(true);
	    }
	    result = PersistenceServerHelper.manager.query(query);
	} else if ("listMyDocument".equals(paramObject.getCommand())) {
	    query = getListMyDocumentSpec(paramObject);
	    Kogger.info(getClass(), query);
	    if (query.isAdvancedQuery()) {
		if (!query.isAdvancedQueryEnabled())
		    query.setAdvancedQueryEnabled(true);
	    }
	    result = PersistenceServerHelper.manager.query(query);

	    if (result != null) {
		MyDocumentDTO paramDto = (MyDocumentDTO) paramObject;
		if (paramDto.isOnlyResult()) {
		    paramDto.setTotalSize(result.size());
		    dtos.add(paramDto);
		} else {
		    int rownum = paramObject.getRowNum();
		    int k = 0;
		    if (rownum > 0) {
			while (result.hasMoreElements()) {
			    if (rownum < k)
				break;
			    Object[] objArr = (Object[]) result.nextElement();
			    String classname = String.valueOf(objArr[0]);
			    String oid = String.valueOf(objArr[1]);
			    WTDocument document = (WTDocument) CommonUtil.getObject(classname + ":" + oid);
			    MyDocumentDTO dto = new MyDocumentDTO(document);
			    dtos.add(dto);
			    k++;
			}
		    } else {
			while (result.hasMoreElements()) {
			    Object[] objArr = (Object[]) result.nextElement();
			    String classname = String.valueOf(objArr[0]);
			    String oid = String.valueOf(objArr[1]);
			    WTDocument document = (WTDocument) CommonUtil.getObject(classname + ":" + oid);
			    MyDocumentDTO dto = new MyDocumentDTO(document);
			    dtos.add(dto);
			}
		    }
		}

	    }
	}

	return dtos;
    }

    @Override
    public PageControl findPaging(BaseDTO paramObject) throws Exception {

	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");

	StatementSpec query = null;
	if ("listMyTask".equals(paramObject.getCommand())) {
	    query = getFilterListMyTaskQuerySpec(paramObject);
	} else if ("listMyProject".equals(paramObject.getCommand())) {
	    query = getListMyProjectQuerySpec(paramObject);
	} else if ("listMyTodo".equals(paramObject.getCommand())) {
	    query = KETWorkflowHelper.service.getMyTodoQuery((WorkItemDTO) paramObject, true);
	} else if ("listMyECM".equals(paramObject.getCommand())) {
	    query = getListMyECMSpec(paramObject);
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
		pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), paramObject.getPage() + 1, pageSessionId);
	    }
	}
	return pageControl;
    }

    private StatementSpec getListMyECMSpec(BaseDTO paramObject) {
	return null;
    }

    @Override
    public QueryResult getMyProjectList(BaseDTO dto) throws Exception {

	StatementSpec spec = getListMyProjectQuerySpec(dto);
	return PersistenceHelper.manager.find(spec);
    }

    @Override
    public MyTaskDTO save(BaseDTO paramObject) throws Exception {
	return null;
    }

    @Override
    public MyTaskDTO modify(BaseDTO paramObject) throws Exception {
	return null;
    }

    @Override
    public MyTaskDTO delete(BaseDTO paramObject) throws Exception {
	return null;
    }

    @SuppressWarnings("deprecation")
    private StatementSpec getListMyDocumentSpec(BaseDTO paramObject) throws Exception {

	StatementSpec statementSpec = null;
	MyDocumentDTO documentDTO = (MyDocumentDTO) paramObject;
	String createStamp = "createStampA2";
	documentDTO.setCreator(String.valueOf(CommonUtil.getOIDLongFromSessionUser()));

	ReflectUtil.toString(documentDTO);

	ProjectDocumentDTO projectDocumentDTO = new ProjectDocumentDTO(documentDTO);
	projectDocumentDTO.setRowNum(20);
	// KETProjectDocument spec
	QuerySpec projectDocumentSpec = (QuerySpec) KETProjectDocumentHelper.service.getListProjectDocumentQuerySpec(projectDocumentDTO);
	// logger.debug("");
	// logger.debug("");
	// logger.debug(projectDocumentSpec);
	// logger.debug("");
	// logger.debug("");
	// QuerySpec projectDocumentSpec = new QuerySpec();
	// int idx = projectDocumentSpec.addClassList(KETProjectDocument.class, false);
	// projectDocumentSpec.appendSelect(new ClassAttribute(KETProjectDocument.class, WTAttributeNameIfc.OID_CLASSNAME), new int[] { idx
	// }, false);
	// projectDocumentSpec.appendSelect(new ClassAttribute(KETProjectDocument.class, WTAttributeNameIfc.ID_NAME), new int[] { idx },
	// false);
	// TableColumn createStampAttr1 = new TableColumn(projectDocumentSpec.getFromClause().getAliasAt(idx), "createStampA2");
	// // ClassAttribute ca1 = new ClassAttribute(KETProjectDocument.class, "thePersistInfo.createStamp");
	// createStampAttr1.setColumnAlias(createStamp);
	// projectDocumentSpec.appendSelect(createStampAttr1, new int[] { idx }, false);
	// TableColumn masterOidAttr = new TableColumn(projectDocumentSpec.getFromClause().getAliasAt(idx), "IDA3MASTERREFERENCE");
	// TableColumn versionAttr = new TableColumn(projectDocumentSpec.getFromClause().getAliasAt(idx), "VERSIONIDA2VERSIONINFO");
	// ColumnListExpression listExpression = new ColumnListExpression();
	// listExpression.addColumn(masterOidAttr);
	String version = documentDTO.getVersion();
	// if (StringUtil.checkString(version)) {
	// NumberCode code = (NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:" + version);
	// if ("LATEST".equals(code.getCode())) {
	// listExpression.addColumn(versionAttr);
	// }
	// }
	// StatementSpec projDocSubspec = null;
	// String pjtType = documentDTO.getPjtType();
	// if (StringUtil.checkString(pjtType)) {
	// projDocSubspec = getProjectDocumentQuerySpec(documentDTO);
	// } else {
	// documentDTO.setPjtType("제품");
	// QuerySpec spec1 = (QuerySpec) getProjectDocumentQuerySpec(documentDTO);
	// documentDTO.setPjtType("검토");
	// QuerySpec spec2 = (QuerySpec) getProjectDocumentQuerySpec(documentDTO);
	// documentDTO.setPjtType("금형");
	// QuerySpec spec3 = (QuerySpec) getProjectDocumentQuerySpec(documentDTO);
	// CompoundQuerySpec querySpec = new CompoundQuerySpec();
	// querySpec.setSetOperator(SetOperator.UNION);
	// querySpec.addComponent(spec1);
	// querySpec.addComponent(spec2);
	// querySpec.addComponent(spec3);
	// querySpec.setUseComponentOrderBy(true);
	// if (!querySpec.isAdvancedQueryEnabled())
	// querySpec.setAdvancedQueryEnabled(true);
	// projDocSubspec = querySpec;
	// }
	// SubSelectExpression selectExpression = new SubSelectExpression(projDocSubspec);
	// selectExpression.setAccessControlRequired(false);
	// projectDocumentSpec.appendWhere(new SearchCondition(listExpression, "IN", selectExpression), new int[] { idx });
	// SearchUtil.appendBOOLEAN(projectDocumentSpec, KETProjectDocument.class, "iterationInfo.latest", SearchCondition.IS_TRUE, idx);
	// SearchUtil.setOrderBy(projectDocumentSpec, KETProjectDocument.class, idx, WTAttributeNameIfc.CREATE_STAMP_NAME, true);
	// OrderBy orderBy1 = new OrderBy(new ClassAttribute(KETProjectDocument.class, WTAttributeNameIfc.CREATE_STAMP_NAME), true);
	// projectDocumentSpec.appendOrderBy(orderBy1, new int[] { idx });

	// KETTechnicalDocument spec
	QuerySpec techDocumentSpec = new QuerySpec();
	techDocumentSpec.getFromClause().setAliasPrefix("B");
	int t_idx = techDocumentSpec.addClassList(KETTechnicalDocument.class, false);
	techDocumentSpec.appendSelect(new ClassAttribute(KETTechnicalDocument.class, WTAttributeNameIfc.OID_CLASSNAME), new int[] { t_idx }, false);
	techDocumentSpec.appendSelect(new ClassAttribute(KETTechnicalDocument.class, WTAttributeNameIfc.ID_NAME), new int[] { t_idx }, false);
	// ClassAttribute ca2 = new ClassAttribute(KETTechnicalDocument.class, "thePersistInfo.createStamp");
	TableColumn createStampAttr2 = new TableColumn(techDocumentSpec.getFromClause().getAliasAt(t_idx), "createStampA2");
	createStampAttr2.setColumnAlias(createStamp);
	techDocumentSpec.appendSelect(createStampAttr2, new int[] { t_idx }, false);
	TableColumn t_masterOidAttr = new TableColumn(techDocumentSpec.getFromClause().getAliasAt(t_idx), "IDA3MASTERREFERENCE");
	TableColumn t_versionAttr = new TableColumn(techDocumentSpec.getFromClause().getAliasAt(t_idx), "VERSIONIDA2VERSIONINFO");
	ColumnListExpression listExpression2 = new ColumnListExpression();
	listExpression2.addColumn(t_masterOidAttr);
	if (StringUtil.checkString(version)) {
	    NumberCode code = (NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:" + version);
	    if ("LATEST".equals(code.getCode())) {
		listExpression2.addColumn(t_versionAttr);
	    }
	}
	StatementSpec techDocSubspec = getTechnicalDocumentQuerySpec(documentDTO);
	SubSelectExpression selectExpression2 = new SubSelectExpression(techDocSubspec);
	selectExpression2.setAccessControlRequired(false);
	techDocumentSpec.appendWhere(new SearchCondition(listExpression2, "IN", selectExpression2), new int[] { t_idx });
	SearchUtil.appendBOOLEAN(techDocumentSpec, KETTechnicalDocument.class, "iterationInfo.latest", SearchCondition.IS_TRUE, t_idx);
	// SearchUtil.setOrderBy(techDocumentSpec, KETTechnicalDocument.class, t_idx, WTAttributeNameIfc.CREATE_STAMP_NAME, true);
	// OrderBy orderBy2 = new OrderBy(new ClassAttribute(KETTechnicalDocument.class, WTAttributeNameIfc.CREATE_STAMP_NAME), true);
	// techDocumentSpec.appendOrderBy(orderBy2, new int[] { t_idx });

	CompoundQuerySpec mainUnionSpec = new CompoundQuerySpec();

	String pjtNo = documentDTO.getPjtNo();
	if (!StringUtil.checkString(pjtNo)) {
	    mainUnionSpec.setSetOperator(SetOperator.UNION);
	    mainUnionSpec.addComponent(projectDocumentSpec);
	    mainUnionSpec.addComponent(techDocumentSpec);
	    // mainUnionSpec.setUseComponentOrderBy(true);
	    mainUnionSpec.appendOrderBy(new OrderBy(new KeywordExpression(createStamp), true));
	    statementSpec = mainUnionSpec;
	} else {
	    statementSpec = projectDocumentSpec;
	}

	return statementSpec;
    }

    private StatementSpec getTechnicalDocumentQuerySpec(MyDocumentDTO paramObject) throws Exception {

	QuerySpec spec = new QuerySpec();
	Class<WTDocumentMaster> masterClass = WTDocumentMaster.class;
	Class<KETTechnicalDocument> iterClass = KETTechnicalDocument.class;
	int m_idx = spec.addClassList(masterClass, false);
	int i_idx = spec.addClassList(iterClass, false);

	ClassAttribute masterOidAttr = new ClassAttribute(iterClass, "masterReference.key.id");
	spec.appendSelect(masterOidAttr, new int[] { i_idx }, false);

	// Join WTDocumentMaster - KETTechnicalDocument
	spec.appendWhere(new SearchCondition(new ClassAttribute(masterClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(iterClass, "masterReference.key.id")), new int[] { m_idx, i_idx });
	// 문서번호
	String documentNo = paramObject.getDocumentNo();
	if (StringUtil.checkString(documentNo)) {
	    SearchUtil.appendLIKE(spec, iterClass, KETTechnicalDocument.NUMBER, documentNo, i_idx);
	}
	// 문서명
	String documentName = paramObject.getDocumentName();
	if (StringUtil.checkString(documentName)) {
	    SearchUtil.appendLIKE(spec, iterClass, KETTechnicalDocument.TITLE, documentName, i_idx);
	}
	// 결재상태
	String state = paramObject.getState();
	if (StringUtil.checkString(state)) {
	    String[] states = state.split(",");
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(iterClass, "state.state", states, true, false), new int[] { i_idx });
	}

	if (!StringUtil.checkNull(paramObject.getTotalFlag()).equals("Y")) {
	    // 작성자
	    SearchUtil.appendEQUAL(spec, iterClass, "iterationInfo.creator.key.id", CommonUtil.getOIDLongValue(SessionHelper.manager.getPrincipal()), i_idx);
	}
	// 작성일자
	String createDateFrom = paramObject.getCreateDateFrom();
	if (StringUtil.checkString(createDateFrom)) {
	    SearchUtil.appendTimeFrom(spec, iterClass, "thePersistInfo.createStamp", DateUtil.convertStartDate2(createDateFrom), i_idx);
	}
	String createDateTo = paramObject.getCreateDateTo();
	if (StringUtil.checkString(createDateTo)) {
	    SearchUtil.appendTimeTo(spec, iterClass, "thePersistInfo.createStamp", DateUtil.convertEndDate2(createDateTo), i_idx);
	}
	// 버전
	String version = paramObject.getVersion();
	if (StringUtil.checkString(version)) {
	    NumberCode code = (NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:" + version);
	    if ("LATEST".equals(code.getCode())) {
		if (!spec.isAdvancedQueryEnabled())
		    spec.setAdvancedQueryEnabled(true);
		TableColumn versionAttr = new TableColumn(spec.getFromClause().getAliasAt(i_idx), "VERSIONIDA2VERSIONINFO");
		SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, versionAttr);
		spec.appendSelect(function, new int[] { i_idx }, false);
		if (spec.getGroupByClause() == null) {
		    spec.appendGroupBy(masterOidAttr, new int[] { i_idx }, false);
		}
	    }
	}
	// 부품
	String partNo = paramObject.getPartNo();
	if (StringUtil.checkString(partNo)) {
	    if (!spec.isAdvancedQueryEnabled())
		spec.setAdvancedQueryEnabled(true);
	    Class<KETDocumentPartLink> partLinkClass = KETDocumentPartLink.class;
	    Class<WTPartMaster> partMasterClass = WTPartMaster.class;
	    Class<WTPart> partClass = WTPart.class;
	    int pl_idx = spec.addClassList(partLinkClass, false);
	    int pm_idx = spec.addClassList(partMasterClass, false);
	    int p_idx = spec.addClassList(partClass, false);

	    // join KETTechnicalDocument - KETDocumentPartLink
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(iterClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(partLinkClass, WTAttributeNameIfc.ROLEA_OBJECT_ID)), new int[] {
		    i_idx, pl_idx });
	    // join WTPart - KETDocumentPartLink
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(partClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(partLinkClass, WTAttributeNameIfc.ROLEB_OBJECT_ID)), new int[] {
		    p_idx, pl_idx });
	    // join WTPartMaster - WTPart
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(partMasterClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(partClass, "masterReference.key.id")), new int[] { pm_idx,
		    p_idx });

	    SearchUtil.appendLIKE(spec, partClass, WTPart.NUMBER, partNo, p_idx);
	    // latest iteration
	    SearchUtil.appendBOOLEAN(spec, partClass, "iterationInfo.latest", SearchCondition.IS_TRUE, p_idx);
	}
	// 문서분류
	String documentCategory = paramObject.getDocumentCategory();
	if (StringUtil.checkString(documentCategory)) {
	    Class<KETDocumentCategoryLink> categoryLinkClass = KETDocumentCategoryLink.class;
	    Class<KETDocumentCategory> categoryClass = KETDocumentCategory.class;
	    int cl_idx = spec.addClassList(categoryLinkClass, false);
	    int c_idx = spec.addClassList(categoryClass, false);

	    // join KETTechnicalDocument - KETDocumentCategoryLink
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(iterClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(categoryLinkClass, WTAttributeNameIfc.ROLEB_OBJECT_ID)), new int[] {
		    i_idx, cl_idx });
	    // join KETDocumentCategory - KETDocumentCategoryLink
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(categoryClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(categoryLinkClass, WTAttributeNameIfc.ROLEA_OBJECT_ID)),
		    new int[] { c_idx, cl_idx });

	    if (StringUtil.checkString(documentCategory)) {
		String[] projectDocTypeArr = documentCategory.split(",");
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(categoryClass, KETDocumentCategory.CATEGORY_CODE, projectDocTypeArr, true, false), new int[] { c_idx });
	    }
	}
	// latest iteration
	SearchUtil.appendBOOLEAN(spec, iterClass, "iterationInfo.latest", SearchCondition.IS_TRUE, i_idx);

	return spec;
    }

    @SuppressWarnings("unused")
    private StatementSpec getProjectDocumentQuerySpec(MyDocumentDTO paramObject) throws Exception {

	QuerySpec spec = new QuerySpec();
	Class<WTDocumentMaster> masterClass = WTDocumentMaster.class;
	Class<KETProjectDocument> iterClass = KETProjectDocument.class;
	int m_idx = spec.addClassList(masterClass, false);
	int i_idx = spec.addClassList(iterClass, false);

	ClassAttribute masterOidAttr = new ClassAttribute(iterClass, "masterReference.key.id");
	spec.appendSelect(masterOidAttr, new int[] { i_idx }, false);

	// Join WTDocumentMaster - KETProjectDocument
	spec.appendWhere(new SearchCondition(new ClassAttribute(masterClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(iterClass, "masterReference.key.id")), new int[] { m_idx, i_idx });
	// 문서번호
	String documentNo = paramObject.getDocumentNo();
	if (StringUtil.checkString(documentNo)) {
	    SearchUtil.appendLIKE(spec, iterClass, KETProjectDocument.NUMBER, documentNo, i_idx);
	}
	// 문서명
	String documentName = paramObject.getDocumentName();
	if (StringUtil.checkString(documentName)) {
	    SearchUtil.appendLIKE(spec, iterClass, KETProjectDocument.TITLE, documentName, i_idx);
	}
	// 결재상태
	String state = paramObject.getState();
	if (StringUtil.checkString(state)) {
	    String[] states = state.split(",");
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(iterClass, "state.state", states, true, false), new int[] { i_idx });
	}
	if (!StringUtil.checkNull(paramObject.getTotalFlag()).equals("Y")) {
	    // 작성자
	    SearchUtil.appendEQUAL(spec, iterClass, "iterationInfo.creator.key.id", CommonUtil.getOIDLongValue(SessionHelper.manager.getPrincipal()), i_idx);
	}
	// 작성일자
	String createDateFrom = paramObject.getCreateDateFrom();
	if (StringUtil.checkString(createDateFrom)) {
	    SearchUtil.appendTimeFrom(spec, iterClass, "thePersistInfo.createStamp", DateUtil.convertStartDate2(createDateFrom), i_idx);
	}
	String createDateTo = paramObject.getCreateDateTo();
	if (StringUtil.checkString(createDateTo)) {
	    SearchUtil.appendTimeTo(spec, iterClass, "thePersistInfo.createStamp", DateUtil.convertEndDate2(createDateTo), i_idx);
	}
	// 버전
	String version = paramObject.getVersion();
	if (StringUtil.checkString(version)) {
	    NumberCode code = (NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:" + version);
	    if ("LATEST".equals(code.getCode())) {
		if (!spec.isAdvancedQueryEnabled())
		    spec.setAdvancedQueryEnabled(true);
		TableColumn versionAttr = new TableColumn(spec.getFromClause().getAliasAt(i_idx), "VERSIONIDA2VERSIONINFO");
		SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, versionAttr);
		spec.appendSelect(function, new int[] { i_idx }, false);
		if (spec.getGroupByClause() == null) {
		    spec.appendGroupBy(masterOidAttr, new int[] { i_idx }, false);
		}
	    }
	}
	// 부품
	String partNo = paramObject.getPartNo();
	if (StringUtil.checkString(partNo)) {
	    if (!spec.isAdvancedQueryEnabled())
		spec.setAdvancedQueryEnabled(true);
	    Class<KETDocumentPartLink> partLinkClass = KETDocumentPartLink.class;
	    Class<WTPartMaster> partMasterClass = WTPartMaster.class;
	    Class<WTPart> partClass = WTPart.class;
	    int pl_idx = spec.addClassList(partLinkClass, false);
	    int pm_idx = spec.addClassList(partMasterClass, false);
	    int p_idx = spec.addClassList(partClass, false);

	    // join KETProjectDocument - KETDocumentPartLink
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(iterClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(partLinkClass, WTAttributeNameIfc.ROLEA_OBJECT_ID)), new int[] {
		    i_idx, pl_idx });
	    // join WTPart - KETDocumentPartLink
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(partClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(partLinkClass, WTAttributeNameIfc.ROLEB_OBJECT_ID)), new int[] {
		    p_idx, pl_idx });
	    // join WTPartMaster - WTPart
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(partMasterClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(partClass, "masterReference.key.id")), new int[] { pm_idx,
		    p_idx });

	    SearchUtil.appendLIKE(spec, partClass, WTPart.NUMBER, partNo, p_idx);
	    // latest iteration
	    SearchUtil.appendBOOLEAN(spec, partClass, "iterationInfo.latest", SearchCondition.IS_TRUE, p_idx);
	}
	// 문서분류, 품질확보절차
	String documentCategory = paramObject.getDocumentCategory();
	if (StringUtil.checkString(documentCategory)) {
	    Class<KETDocumentCategoryLink> categoryLinkClass = KETDocumentCategoryLink.class;
	    Class<KETDocumentCategory> categoryClass = KETDocumentCategory.class;
	    int cl_idx = spec.addClassList(categoryLinkClass, false);
	    int c_idx = spec.addClassList(categoryClass, false);

	    // join KETProjectDocument - KETDocumentCategoryLink
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(iterClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(categoryLinkClass, WTAttributeNameIfc.ROLEB_OBJECT_ID)), new int[] {
		    i_idx, cl_idx });
	    // join KETDocumentCategory - KETDocumentCategoryLink
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(categoryClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(categoryLinkClass, WTAttributeNameIfc.ROLEA_OBJECT_ID)),
		    new int[] { c_idx, cl_idx });

	    if (StringUtil.checkString(documentCategory)) {
		String[] projectDocTypeArr = documentCategory.split(",");
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(categoryClass, KETDocumentCategory.CATEGORY_CODE, projectDocTypeArr, true, false), new int[] { c_idx });
	    }
	}
	// pjtNo
	String pjtType = paramObject.getPjtType();
	String pjtNo = paramObject.getPjtNo();
	if (StringUtil.checkString(pjtNo)) {
	    Class<KETDocumentProjectLink> projLinkClass = KETDocumentProjectLink.class;
	    Class<E3PSProjectMaster> projMasterClass = E3PSProjectMaster.class;
	    Class<?> projClass = null;
	    if ("제품".equals(pjtType)) {
		projClass = ProductProject.class;
	    } else if ("검토".equals(pjtType)) {
		projClass = ReviewProject.class;
	    } else if ("금형".equals(pjtType)) {
		projClass = MoldProject.class;
	    } else if ("업무".equals(pjtType)) {
		projClass = WorkProject.class;
	    }
	    int pl_idx = spec.addClassList(projLinkClass, false);
	    int pm_idx = spec.addClassList(projMasterClass, false);
	    int p_idx = spec.addClassList(projClass, false);

	    // join KETProjectDocument - KETDocumentProjectLink
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(iterClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(projLinkClass, WTAttributeNameIfc.ROLEA_OBJECT_ID)), new int[] {
		    spec.getFromClause().getPosition(iterClass), pl_idx });
	    // join ProductProject - KETDocumentProjectLink
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(projClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(projLinkClass, WTAttributeNameIfc.ROLEB_OBJECT_ID)), new int[] {
		    p_idx, pl_idx });
	    // join E3PSProjectMaster - ProductProject
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(projMasterClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(projClass, "masterReference.key.id")), new int[] { pm_idx,
		    p_idx });
	    if (pjtNo.indexOf(",") > 0) {
		String[] pjtNos = pjtNo.split(",");
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(projClass, "master>pjtNo", pjtNos, true, false), new int[] { p_idx });
	    } else {
		SearchUtil.appendLIKE(spec, projClass, "master>pjtNo", pjtNo, p_idx);
	    }
	}
	// latest iteration
	SearchUtil.appendBOOLEAN(spec, iterClass, "iterationInfo.latest", SearchCondition.IS_TRUE, i_idx);

	return spec;
    }

    private StatementSpec getFilterListMyTaskQuerySpec(BaseDTO paramObject) throws Exception {

	QuerySpec spec = (QuerySpec) getListMyTaskQuerySpec(paramObject);
	if (spec.isAdvancedQuery()) {
	    if (!spec.isAdvancedQueryEnabled())
		spec.setAdvancedQueryEnabled(true);
	}
	Kogger.debug(getClass(), spec.toString());
	QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);
	ArrayList<Long> list = new ArrayList<Long>();
	while (result.hasMoreElements()) {
	    Object[] objArr = (Object[]) result.nextElement();
	    E3PSTask task = (E3PSTask) objArr[0];
	    // leaf 태스크만 조회 조건 추가
	    // 2014-10-21 단위테스트 65
	    MyTaskDTO myTaskDTO = (MyTaskDTO) paramObject;

	    CompoundQuerySpec compound = null;
	    Class<?> targetClass[] = { ReviewProject.class, ProductProject.class, MoldProject.class, WorkProject.class };
	    compound = new CompoundQuerySpec();
	    compound.setSetOperator(SetOperator.UNION);
	    compound.setAdvancedQueryEnabled(true);

	    for (int i = 0; i < targetClass.length; i++) {
		QuerySpec Spec = new QuerySpec();
		Spec.setAdvancedQueryEnabled(true);
	    }
	    if (task.isLeaf()) {
		// if (ProjectTaskHelper.manager.isLast(task) && task.isLeaf()) {
		String searchType = StringUtil.checkNull(myTaskDTO.getSearchType());
		if (StringUtil.checkString(searchType)) {
		    ExtendScheduleData scheduleData = (ExtendScheduleData) task.getTaskSchedule().getObject();
		    Timestamp planStartDate = scheduleData.getPlanStartDate();
		    Timestamp planEndDate = scheduleData.getPlanEndDate();
		    Timestamp execStartDate = scheduleData.getExecStartDate();
		    Timestamp execEndDate = scheduleData.getExecEndDate();
		    Calendar cal = Calendar.getInstance();
		    String today = DateUtil.getDateString(new Timestamp(cal.getTimeInMillis()), "d");

		    if ("plan".equals(searchType)) {
			// 계획
			// task 계획시작일 > today and task 실제시작일 = null
			if (compareDate(DateUtil.getDateString(planStartDate, "d"), ">", today) && execStartDate == null) {
			    if (!list.contains(task))
				list.add(CommonUtil.getOIDLongValue(task));
			}
		    } else if ("progress".equals(searchType)) {
			// 진행
			// task 실제완료일 = null and ((task 계획시작일 <= today or task 실제시작일 <= today) and (task 계획완료일 >= today or (task 계획완료일 <
			// today and 하나이상 선행 task 미완료)))
			if (execEndDate == null
			        && ((compareDate(DateUtil.getDateString(planStartDate, "d"), "<=", today) || (execStartDate != null && compareDate(DateUtil.getDateString(execStartDate, "d"), "<=",
			                today))) && (compareDate(DateUtil.getDateString(planEndDate, "d"), ">=", today) || (compareDate(DateUtil.getDateString(planEndDate, "d"), "<", today) && !isAllCompletedDependencyTargetTask(task))))) {
			    if (!list.contains(task))
				list.add(CommonUtil.getOIDLongValue(task));
			}
		    } else if ("delay".equals(searchType)) {
			// 지연
			// 모든 선행 task가 완료 and (task 실제완료일 = null and task 계획완료일 < today)
			if (isAllCompletedDependencyTargetTask(task) && (execEndDate == null && compareDate(DateUtil.getDateString(planEndDate, "d"), "<", today))) {
			    if (!list.contains(task))
				list.add(CommonUtil.getOIDLongValue(task));
			}
		    }
		} else {
		    if (!list.contains(task))
			list.add(CommonUtil.getOIDLongValue(task));
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
	spec = new QuerySpec();
	int idx = spec.addClassList(E3PSTask.class, true);
	SearchCondition sc = new SearchCondition(E3PSTask.class, "thePersistInfo.theObjectIdentifier.id", longs, false);
	spec.appendWhere(sc, new int[] { idx });

	return spec;
    }

    private StatementSpec getListMyTaskQuerySpec(BaseDTO paramObject) throws Exception {

	QuerySpec mainSpec = null;
	mainSpec = new QuerySpec();
	Class<E3PSTask> main_target = E3PSTask.class;
	int main_idx = mainSpec.addClassList(main_target, true);

	if (!mainSpec.isAdvancedQueryEnabled()) {
	    mainSpec.setAdvancedQueryEnabled(true);
	}
	// ////////// Sub Query Start mainSpec
	QuerySpec qs = new QuerySpec();

	if (!qs.isAdvancedQueryEnabled()) {
	    qs.setAdvancedQueryEnabled(true);
	}

	int taskMemberlink_idx = qs.appendClassList(TaskMemberLink.class, false);
	int projectMemberlink_idx = qs.appendClassList(ProjectMemberLink.class, false);

	int project_idxx = qs.appendClassList(E3PSProject.class, false);
	int task_idxx = qs.appendClassList(E3PSTask.class, false);
	int schedIndex = qs.appendClassList(ExtendScheduleData.class, false);
	qs.setDistinct(true);
	ClassAttribute ca = new ClassAttribute(E3PSTask.class, wt.util.WTAttributeNameIfc.ID_NAME);
	ca.setColumnAlias("taskOid");
	qs.appendSelect(ca, true);

	SearchCondition exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc.setFromIndicies(new int[] { task_idxx, schedIndex }, 0);
	exsc.setOuterJoin(0);
	qs.appendWhere(exsc, new int[] { task_idxx, schedIndex });

	qs.appendAnd();
	SearchCondition tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc.setFromIndicies(new int[] { task_idxx, project_idxx }, 0);
	tpsc.setOuterJoin(0);
	qs.appendWhere(tpsc, new int[] { task_idxx, project_idxx });
	qs.appendAnd();
	SearchCondition ttl_sc = new SearchCondition(new ClassAttribute(E3PSTask.class, wt.util.WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(TaskMemberLink.class, "roleAObjectRef.key.id"));
	ttl_sc.setFromIndicies(new int[] { task_idxx, taskMemberlink_idx }, 0);
	ttl_sc.setOuterJoin(0);
	qs.appendWhere(ttl_sc, new int[] { task_idxx, taskMemberlink_idx });

	qs.appendAnd();
	SearchCondition ptsc = new SearchCondition(new ClassAttribute(TaskMemberLink.class, "roleBObjectRef.key.id"), "=", new ClassAttribute(ProjectMemberLink.class,
	        wt.util.WTAttributeNameIfc.ID_NAME));
	ptsc.setFromIndicies(new int[] { taskMemberlink_idx, projectMemberlink_idx }, 0);
	ptsc.setOuterJoin(0);
	qs.appendWhere(ptsc, new int[] { taskMemberlink_idx, projectMemberlink_idx });

	MyTaskDTO myTaskDTO = (MyTaskDTO) paramObject;

	String searchPjtName = StringUtil.checkNull(myTaskDTO.getPjtName());
	String searchTaskName = StringUtil.checkNull(myTaskDTO.getTaskName());
	String searchState = StringUtil.checkNull(myTaskDTO.getSearchState());
	String searchPjtState = StringUtil.checkNull(myTaskDTO.getSearchPjtState());
	String searchPjtNo = StringUtil.checkNull(myTaskDTO.getPjtNo());
	String command = StringUtil.checkNull(myTaskDTO.getCommand());
	String searchPjtType = StringUtil.checkNull(MyTaskDTO.getSearchPjtType());
	// String getUser = StringUtil.checkNull((String) map.get("getUser"));
	String notEqualState = "WITHDRAWN";

	// WTUser wtuser = null;
	// if (getUser.length() > 0) {
	// wtuser = (WTUser) CommonUtil.getObject(getUser);
	// } else {
	// wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	// }
	long oidValue_t = CommonUtil.getOIDLongValue((WTUser) SessionHelper.manager.getPrincipal());

	// 태스크 책임자/ 구성원
	if (qs.getConditionCount() > 0) {
	    qs.appendAnd();
	}
	qs.appendWhere(new SearchCondition(ProjectMemberLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue_t), new int[] { projectMemberlink_idx });

	if (qs.getConditionCount() > 0) {
	    qs.appendAnd();
	}

	qs.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.LEAF, SearchCondition.IS_TRUE), new int[] { task_idxx });

	searchTaskName = StringUtil.changeString(searchTaskName, "%", "*");

	if (command.length() > 0) {
	    // 프로젝트
	    if (searchPjtName.length() > 0) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}
		searchPjtName = StringUtil.changeString(searchPjtName, "*", "%");
		qs.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_NAME, SearchCondition.LIKE, searchPjtName), new int[] { project_idxx });
		searchPjtName = StringUtil.changeString(searchPjtName, "%", "*");
	    }
	    // 프로젝트 NO
	    if (searchPjtNo.length() > 0) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}
		searchPjtNo = StringUtil.changeString(searchPjtNo, "*", "%");
		qs.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_NO, SearchCondition.LIKE, searchPjtNo, false), new int[] { project_idxx });
		searchPjtNo = StringUtil.changeString(searchPjtNo, "%", "*");
	    }
	    // 프로젝트 타입
	    if (searchPjtType.length() > 0) {
		if (searchPjtType != null && searchPjtType.trim().length() > 0 && !searchPjtType.equalsIgnoreCase("null")) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendOpenParen();
		    StringTokenizer pjtTypeToken = new StringTokenizer(searchPjtType, ",");
		    while (pjtTypeToken.hasMoreTokens()) {
			qs.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_TYPE_NAME, SearchCondition.EQUAL, pjtTypeToken.nextToken()), new int[] { project_idxx });
			if (pjtTypeToken.hasMoreTokens())
			    qs.appendOr();
		    }
		    qs.appendCloseParen();
		}
	    }
	    // 태스크
	    if (searchTaskName.length() > 0) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}
		searchTaskName = StringUtil.changeString(searchTaskName, "*", "%");
		qs.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.TASK_NAME, SearchCondition.LIKE, searchTaskName, false), new int[] { task_idxx });
		searchTaskName = StringUtil.changeString(searchTaskName, "%", "*");

	    }
	    // 태스크 완료 여부
	    if (searchState != null && searchState.trim().length() > 0 && !searchState.equalsIgnoreCase("null")) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendOpenParen();
		StringTokenizer stateTypeToken = new StringTokenizer(searchState, ",");
		while (stateTypeToken.hasMoreTokens()) {
		    NumberCode code = (NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:" + stateTypeToken.nextToken());
		    qs.appendWhere(new SearchCondition(E3PSTask.class, "taskState", SearchCondition.EQUAL, Integer.parseInt(code.getCode())), new int[] { task_idxx });
		    if (stateTypeToken.hasMoreTokens())
			qs.appendOr();
		}
		qs.appendCloseParen();
	    }
	    // 취소된 프로젝트 조회대상제외
	    if (notEqualState.length() > 0) {
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(E3PSProject.class, "state.state", SearchCondition.NOT_EQUAL, "WITHDRAWN"), new int[] { project_idxx });
	    }
	    // 프로젝트 상태
	    if (searchPjtState != null && searchPjtState.trim().length() > 0 && !searchPjtState.equalsIgnoreCase("null")) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendOpenParen();
		StringTokenizer pjtStateToken = new StringTokenizer(searchPjtState, ",");
		while (pjtStateToken.hasMoreTokens()) {
		    NumberCode code = (NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:" + pjtStateToken.nextToken());
		    qs.appendWhere(new SearchCondition(E3PSProject.class, "state.state", SearchCondition.EQUAL, code.getCode()), new int[] { project_idxx });
		    if (pjtStateToken.hasMoreTokens())
			qs.appendOr();
		}
		qs.appendCloseParen();
	    }

	    String planStartStartDate = StringUtil.checkNull(myTaskDTO.getPlanStartStartDate());
	    String planStartEndDate = StringUtil.checkNull(myTaskDTO.getPlanStartEndDate());
	    String planEndStartDate = StringUtil.checkNull(myTaskDTO.getPlanEndStartDate());
	    String planEndEndDate = StringUtil.checkNull(myTaskDTO.getPlanEndEndDate());
	    if ((planStartStartDate != null && planStartStartDate.length() > 0) || (planStartEndDate != null && planStartEndDate.length() > 0)
		    || (planEndStartDate != null && planEndStartDate.length() > 0) || (planEndEndDate != null && planEndEndDate.length() > 0)) {
		if (planStartStartDate != null && planStartStartDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planStartStartDate, new ParsePosition(0)).getTime()));
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE, SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
		    qs.appendWhere(exsc, new int[] { schedIndex });
		}
		if (planStartEndDate != null && planStartEndDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(planStartEndDate + ":23-59-59", new ParsePosition(0)).getTime()));
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE, SearchCondition.LESS_THAN, convertDate);
		    qs.appendWhere(exsc, new int[] { schedIndex });
		}
		if (planEndStartDate != null && planEndStartDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planEndStartDate, new ParsePosition(0)).getTime()));
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
		    qs.appendWhere(exsc, new int[] { schedIndex });
		}
		if (planEndEndDate != null && planEndEndDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(planEndEndDate + ":23-59-59", new ParsePosition(0)).getTime()));
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, SearchCondition.LESS_THAN, convertDate);
		    qs.appendWhere(exsc, new int[] { schedIndex });
		}
	    }
	}

	// 최신 프로젝트
	if (qs.getConditionCount() > 0) {
	    qs.appendAnd();
	}
	qs.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")), new int[] { project_idxx });

	if (qs.getConditionCount() > 0) {
	    qs.appendAnd();
	}
	qs.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { project_idxx });

	// ////////// Sub Query End mainSpec
	SubSelectExpression subfrom = new SubSelectExpression(qs);
	subfrom.setFromAlias(new String[] { "B0" }, project_idxx);
	// subfrom.set

	int sub_pjt_index = mainSpec.appendFrom(subfrom);
	if (mainSpec.getConditionCount() > 0)
	    mainSpec.appendAnd();
	@SuppressWarnings("deprecation")
	SearchCondition sc = new SearchCondition(new ClassAttribute(E3PSTask.class, wt.util.WTAttributeNameIfc.ID_NAME), "=", new KeywordExpression(mainSpec.getFromClause().getAliasAt(sub_pjt_index)
	        + ".taskOid"));
	sc.setFromIndicies(new int[] { main_idx, sub_pjt_index }, 0);
	sc.setOuterJoin(0);
	mainSpec.appendWhere(sc, new int[] { main_idx, sub_pjt_index });

	int project_idxx2 = mainSpec.appendClassList(E3PSProject.class, false);
	int master_idxx2 = mainSpec.appendClassList(E3PSProjectMaster.class, false);
	int schedIndex2 = mainSpec.appendClassList(ExtendScheduleData.class, false);

	if (mainSpec.getConditionCount() > 0) {
	    mainSpec.appendAnd();
	}
	SearchCondition mastersc2 = new SearchCondition(new ClassAttribute(E3PSProject.class, "masterReference.key.id"), "=", new ClassAttribute(E3PSProjectMaster.class,
	        wt.util.WTAttributeNameIfc.ID_NAME));
	mastersc2.setFromIndicies(new int[] { project_idxx2, master_idxx2 }, 0);
	mastersc2.setOuterJoin(0);
	mainSpec.appendWhere(mastersc2, new int[] { project_idxx2, master_idxx2 });
	mainSpec.appendAnd();
	SearchCondition tpsc2 = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc2.setFromIndicies(new int[] { main_idx, project_idxx2 }, 0);
	tpsc2.setOuterJoin(0);
	mainSpec.appendWhere(tpsc2, new int[] { main_idx, project_idxx2 });
	mainSpec.appendAnd();

	SearchCondition exsc2 = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc2.setFromIndicies(new int[] { main_idx, schedIndex2 }, 0);
	exsc2.setOuterJoin(0);
	mainSpec.appendWhere(exsc2, new int[] { main_idx, schedIndex2 });

	SearchUtil.setOrderBy(mainSpec, ExtendScheduleData.class, schedIndex, "planEndDate", "planEndDate", false);
	SearchUtil.setOrderBy(mainSpec, E3PSProject.class, project_idxx, E3PSProject.PJT_NO, "pjtNo0", true);

	return mainSpec;
    }

    private StatementSpec getListMyProjectQuerySpec(BaseDTO paramObject) throws Exception {

	MyProjectDTO projectDTO = (MyProjectDTO) paramObject;

	CompoundQuerySpec compound = null;
	Class<?> targetClass[] = { ReviewProject.class, ProductProject.class, MoldProject.class, WorkProject.class };
	compound = new CompoundQuerySpec();
	compound.setSetOperator(SetOperator.UNION);
	compound.setAdvancedQueryEnabled(true);

	Vector<ClassAttributeData> attribute = new Vector<ClassAttributeData>();

	ClassAttribute ca = new ClassAttribute(E3PSProject.class, WTAttributeNameIfc.OID_CLASSNAME);
	ca.setColumnAlias("OID_CLASSNAME");
	attribute.add(new ClassAttributeData(ca, 0));

	ca = new ClassAttribute(E3PSProject.class, WTAttributeNameIfc.ID_NAME);
	ca.setColumnAlias("ID_NAME");
	attribute.add(new ClassAttributeData(ca, 0));

	ca = new ClassAttribute(E3PSProject.class, E3PSProject.PJT_TYPE_NAME);
	ca.setColumnAlias("PJT_TYPE_NAME");
	attribute.add(new ClassAttributeData(ca, 0));

	ca = new ClassAttribute(E3PSProject.class, E3PSProject.PJT_NO);
	ca.setColumnAlias("PJT_NO");
	attribute.add(new ClassAttributeData(ca, 0));

	ca = new ClassAttribute(E3PSProject.class, E3PSProject.PJT_NAME);
	ca.setColumnAlias("PJT_NAME");
	attribute.add(new ClassAttributeData(ca, 0));

	ca = new ClassAttribute(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE);
	ca.setColumnAlias("PLAN_START_DATE");
	attribute.add(new ClassAttributeData(ca, 1));

	ca = new ClassAttribute(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE);
	ca.setColumnAlias("PLAN_END_DATE");
	attribute.add(new ClassAttributeData(ca, 1));

	ca = new ClassAttribute(E3PSProject.class, E3PSProject.LIFE_CYCLE_STATE);
	ca.setColumnAlias("LIFE_CYCLE_STATE");
	attribute.add(new ClassAttributeData(ca, 0));

	QuerySpec subQuery = myProjectSubQuery(projectDTO);

	for (int i = 0; i < targetClass.length; i++) {
	    QuerySpec mainSpec = new QuerySpec();
	    mainSpec.setAdvancedQueryEnabled(true);
	    Class<?> main_target = targetClass[i];

	    E3PSClassTableExpression tableExpression = new E3PSClassTableExpression(main_target);
	    int main_idx = mainSpec.appendFrom(tableExpression);
	    int idx_schedule = mainSpec.appendClassList(ExtendScheduleData.class, false);

	    ClassAttribute ca1 = null;
	    ClassAttribute ca2 = null;

	    ca1 = new ClassAttribute(E3PSProject.class, "pjtSchedule.key.id");
	    ca2 = new ClassAttribute(ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME);
	    SearchCondition sc1 = new SearchCondition(ca1, "=", ca2);
	    sc1.setFromIndicies(new int[] { main_idx, idx_schedule }, 0);
	    sc1.setOuterJoin(0);
	    mainSpec.appendWhere(sc1, new int[] { main_idx, idx_schedule });

	    for (int j = 0; j < attribute.size(); j++) {
		ClassAttributeData attrData = (ClassAttributeData) attribute.get(j);
		mainSpec.appendSelect(attrData.ca, new int[] { attrData.index }, false);
	    }

	    if (!mainSpec.isAdvancedQueryEnabled()) {
		mainSpec.setAdvancedQueryEnabled(true);
	    }
	    // ////////// Sub Query Start mainSpec

	    SubSelectExpression subfrom = new SubSelectExpression(subQuery);
	    subfrom.setFromAlias(new String[] { "B0" }, 0);
	    // subfrom.set

	    int sub_pjt_index = mainSpec.appendFrom(subfrom);

	    if (mainSpec.getConditionCount() > 0) {
		mainSpec.appendAnd();
	    }

	    @SuppressWarnings("deprecation")
	    SearchCondition sc = new SearchCondition(new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME), "=", new KeywordExpression(mainSpec.getFromClause().getAliasAt(
		    sub_pjt_index)
		    + ".projectId"));
	    sc.setFromIndicies(new int[] { main_idx, sub_pjt_index }, 0);
	    sc.setOuterJoin(0);
	    mainSpec.appendWhere(sc, new int[] { main_idx, sub_pjt_index });

	    SearchUtil.setOrderBy(mainSpec, ExtendScheduleData.class, mainSpec.getFromClause().getPosition(ExtendScheduleData.class), ExtendScheduleData.PLAN_END_DATE, "PLAN_END_DATE", true);

	    compound.addComponent(mainSpec);
	    compound.setUseComponentOrderBy(true);
	}
	return compound;
    }

    private QuerySpec myProjectSubQuery(MyProjectDTO projectDTO) throws Exception {
	QuerySpec spec = null;
	spec = new QuerySpec();
	if (!spec.isAdvancedQueryEnabled()) {
	    spec.setAdvancedQueryEnabled(true);
	}

	Class<E3PSProject> target = E3PSProject.class;
	int idx_target = spec.addClassList(target, false);

	spec.setDistinct(true);
	ClassAttribute ca = new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
	ca.setColumnAlias("projectId");
	spec.appendSelect(ca, true);

	WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	long lperistable = CommonUtil.getOIDLongValue(wtuser);

	Class<ProjectMemberLink> linkClass = ProjectMemberLink.class;
	int idx_memberLink = spec.addClassList(linkClass, false);
	ClassAttribute cat1 = null;
	ClassAttribute cat2 = null;

	cat1 = new ClassAttribute(linkClass, "roleAObjectRef.key.id");
	cat2 = new ClassAttribute(target, wt.util.WTAttributeNameIfc.ID_NAME);
	SearchCondition sc2 = new SearchCondition(cat1, SearchCondition.EQUAL, cat2);
	sc2.setFromIndicies(new int[] { idx_memberLink, idx_target }, 0);
	sc2.setOuterJoin(SearchCondition.NO_OUTER_JOIN);
	spec.appendWhere(sc2, new int[] { idx_memberLink, idx_target });

	spec.appendAnd();
	spec.appendWhere(new SearchCondition(linkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, lperistable), new int[] { idx_memberLink });
	if ("Y".equals(projectDTO.getIsPm())) {
	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(linkClass, "pjtMemberType", SearchCondition.EQUAL, 0), new int[] { idx_memberLink });
	}

	String command = StringUtil.checkNull(projectDTO.getCommand());
	String searchPjtName = StringUtil.checkNull(projectDTO.getSearchPjtName());
	String searchPjtNo = StringUtil.checkNull(projectDTO.getSearchPjtNo());
	String searchPjtState = StringUtil.checkNull(projectDTO.getSearchPjtState());
	String searchPjtType = StringUtil.checkNull(projectDTO.getSearchPjtType());
	if (command.length() > 0) {

	    // 프로젝트
	    if (searchPjtName.length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}

		searchPjtName = StringUtil.changeString(searchPjtName, "*", "%");
		spec.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_NAME, SearchCondition.LIKE, searchPjtName, false), new int[] { idx_target });
		searchPjtName = StringUtil.changeString(searchPjtName, "%", "*");
	    }
	    // 프로젝트 번호
	    if (searchPjtNo.length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}

		searchPjtNo = StringUtil.changeString(searchPjtNo, "*", "%");
		spec.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_NO, SearchCondition.LIKE, searchPjtNo, false), new int[] { idx_target });
		searchPjtNo = StringUtil.changeString(searchPjtNo, "%", "*");
	    }

	    // 프로젝트 타입
	    if (searchPjtType.length() > 0) {
		if (searchPjtType != null && searchPjtType.trim().length() > 0 && !searchPjtType.equalsIgnoreCase("null")) {
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    spec.appendOpenParen();
		    StringTokenizer pjtTypeToken = new StringTokenizer(searchPjtType, ",");
		    while (pjtTypeToken.hasMoreTokens()) {
			spec.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_TYPE_NAME, SearchCondition.EQUAL, pjtTypeToken.nextToken()), new int[] { idx_target });
			if (pjtTypeToken.hasMoreTokens())
			    spec.appendOr();
		    }
		    spec.appendCloseParen();
		}
	    }

	    // 프로젝트 상태
	    if (searchPjtState.length() > 0) {
		if (searchPjtState != null && searchPjtState.trim().length() > 0 && !searchPjtState.equalsIgnoreCase("null")) {
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    spec.appendOpenParen();
		    StringTokenizer pjtStateToken = new StringTokenizer(searchPjtState, ",");
		    while (pjtStateToken.hasMoreTokens()) {
			NumberCode code = (NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:" + pjtStateToken.nextToken());
			spec.appendWhere(new SearchCondition(E3PSProject.class, "state.state", SearchCondition.EQUAL, code.getCode()), new int[] { idx_target });
			if (pjtStateToken.hasMoreTokens())
			    spec.appendOr();
		    }
		    spec.appendCloseParen();
		}
	    }

	    String planStartStartDate = StringUtil.checkNull(projectDTO.getPlanStartStartDate());
	    String planStartEndDate = StringUtil.checkNull(projectDTO.getPlanStartEndDate());
	    String planEndStartDate = StringUtil.checkNull(projectDTO.getPlanEndStartDate());
	    String planEndEndDate = StringUtil.checkNull(projectDTO.getPlanEndEndDate());

	    if ((planStartStartDate != null && planStartStartDate.length() > 0) || (planStartEndDate != null && planStartEndDate.length() > 0)
		    || (planEndStartDate != null && planEndStartDate.length() > 0) || (planEndEndDate != null && planEndEndDate.length() > 0)) {

		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		int schedIndex = spec.appendClassList(ExtendScheduleData.class, false);
		SearchCondition exsc = new SearchCondition(new ClassAttribute(E3PSProject.class, "pjtSchedule.key.id"), "=", new ClassAttribute(ExtendScheduleData.class,
		        wt.util.WTAttributeNameIfc.ID_NAME));
		exsc.setFromIndicies(new int[] { idx_target, schedIndex }, 0);
		exsc.setOuterJoin(0);
		spec.appendWhere(exsc, new int[] { idx_target, schedIndex });

		if (planStartStartDate != null && planStartStartDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planStartStartDate, new ParsePosition(0)).getTime()));

		    if (spec.getConditionCount() > 0)
			spec.appendAnd();

		    exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE, SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
		    spec.appendWhere(exsc, new int[] { schedIndex });
		}
		if (planStartEndDate != null && planStartEndDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(planStartEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

		    if (spec.getConditionCount() > 0)
			spec.appendAnd();

		    exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE, SearchCondition.LESS_THAN, convertDate);
		    spec.appendWhere(exsc, new int[] { schedIndex });
		}
		if (planEndStartDate != null && planEndStartDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planEndStartDate, new ParsePosition(0)).getTime()));

		    if (spec.getConditionCount() > 0)
			spec.appendAnd();

		    exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
		    spec.appendWhere(exsc, new int[] { schedIndex });
		}
		if (planEndEndDate != null && planEndEndDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(planEndEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

		    if (spec.getConditionCount() > 0)
			spec.appendAnd();

		    exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, SearchCondition.LESS_THAN, convertDate);
		    spec.appendWhere(exsc, new int[] { schedIndex });
		}
	    }
	}

	// //////////Sub Query End mainSpec
	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(new SearchCondition(target, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")), new int[] { idx_target });
	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(new SearchCondition(target, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { idx_target });

	return spec;
    }

    public QueryResult getTeamUsers(TemplateProject project, String withPM) {
	QueryResult result = null;
	try {
	    Class<ProjectMemberLink> targetClass = ProjectMemberLink.class;
	    QuerySpec qs = new QuerySpec(targetClass);

	    qs.appendOpenParen();
	    qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, "=", ProjectUserHelper.MEMBER), new int[] { 0 });
	    qs.appendOr();
	    qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, "=", ProjectUserHelper.ROLEMEMBER), new int[] { 0 });
	    if (withPM.length() > 0) {
		qs.appendOr();
		qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, "=", ProjectUserHelper.PM), new int[] { 0 });
	    }
	    qs.appendCloseParen();

	    qs.appendAnd();
	    long oidValue = CommonUtil.getOIDLongValue(project);
	    qs.appendWhere(new SearchCondition(targetClass, "roleAObjectRef.key.id", "=", oidValue), new int[] { 0 });
	    // qs.appendAnd();
	    // qs.appendWhere(new SearchCondition(targetClass, "pjtRole", false), new int[] { 0 });
	    result = PersistenceHelper.manager.find((StatementSpec) qs);
	    Kogger.debug(getClass(), qs);
	    if (result != null)
		return result;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return result;
    }

    @Override
    public String getReceiptDate(LifeCycleManaged lcm) throws Exception {

	String receiptDate = "";
	WfProcess process = getWfProcessFromPbo(lcm);
	if (process != null) {
	    ProcessData processData = process.getContext();
	    WfVariable variable = processData.getVariable("receiptDate");
	    if (variable != null) {
		Date date = (Date) variable.getValue();
		if (date != null) {
		    receiptDate = DateUtil.getDateString(date, new SimpleDateFormat("yyyy-MM-dd"));
		}
	    }
	}
	return receiptDate;
    }

    @Override
    public boolean saveReceiptDate(LifeCycleManaged lcm, Timestamp receiptDate) throws Exception {

	boolean flag = false;
	WfProcess process = getWfProcessFromPbo(lcm);
	if (process != null) {
	    ProcessData processData = process.getContext();
	    processData.setValue("receiptDate", receiptDate);
	    process.setContext(processData);
	    PersistenceServerHelper.manager.update(process);
	    flag = true;
	}
	return flag;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean delegateTodoWorkItem(WorkItem workitem, WTPrincipal newOwner, String reason) throws Exception {

	boolean flag = false;
	Transaction trx = new Transaction();
	try {
	    trx.start();

	    WTPrincipal oldOwner = OwnershipHelper.getOwner(workitem);
	    WTPrincipalReference oldOwnerReference = WTPrincipalReference.newWTPrincipalReference(oldOwner);
	    WTPrincipalReference newOwnerReference = WTPrincipalReference.newWTPrincipalReference(newOwner);

	    // 담당자를 변경한다.
	    // PBO를 찾는다.
	    PersistentReference persistentReference = workitem.getPrimaryBusinessObject();
	    Persistable persistable = (persistentReference != null) ? persistentReference.getObject() : null;
	    if (persistable != null) {

		WTUser newOwnerWTUser = (newOwnerReference != null) ? (WTUser) newOwnerReference.getObject() : null;
		if (newOwnerWTUser != null) {

		    // ECO, ECR일 경우
		    if (persistable instanceof KETProdChangeActivity || persistable instanceof KETMoldChangeActivity || persistable instanceof KETChangeRequestExpansion) {
			String workerId = CommonUtil.getOIDString(newOwnerWTUser);

			// 제품 ECO 일 경우
			if (persistable instanceof KETProdChangeActivity) {
			    KETProdChangeActivity ketProdChangeActivity = (KETProdChangeActivity) persistable;
			    String old_worker_oid = ketProdChangeActivity.getWorkerId();
			    ketProdChangeActivity.setWorkerId(workerId);

			    // 작업위임자에게 BOM 수정 권한을 준다.
			    WTPart wtPart = null;
			    KETProdChangeOrder prodEco = null;
			    QueryResult qr = PersistenceHelper.manager.navigate(persistable, "prodECO", KETProdChangeActivityLink.class);
			    if (qr.hasMoreElements()) {
				prodEco = (KETProdChangeOrder) qr.nextElement();

			    }
			    if (prodEco != null) {
				qr = PersistenceHelper.manager.navigate(persistable, "bom", KETProdECABomLink.class, false);
				if (qr.hasMoreElements()) {
				    KETProdECABomLink ketProdECABomLink = (KETProdECABomLink) qr.nextElement();
				    String beforePartOid = ketProdECABomLink.getBeforePartOid();
				    wtPart = (WTPart) CommonUtil.getObject(beforePartOid);

				}

				if (wtPart != null) {

				    WTConnection wtConnection = EcmUtil.getWTConnection();
				    Connection conn = wtConnection.getConnection();
				    EcmComDao comDao = new EcmComDao(conn);

				    Hashtable coWokerHash = EcmUtil.getCoWorkerInfo(prodEco.getEcoId(), wtPart.getNumber(), workerId);
				    String old_worker_id = "";
				    try {
					WTUser worker = (WTUser) KETObjectUtil.getObject(old_worker_oid);
					PeopleData pData = new PeopleData(worker);
					if (worker != null) {
					    old_worker_id = worker.getName();
					}
				    } catch (Exception e) {
					Kogger.error(getClass(), e);
				    }
				    coWokerHash.put("old_worker_id", old_worker_id);
				    boolean isSuccess = comDao.updateBomCoWoker(coWokerHash);

				}
			    }

			}
			// 금형 ECO 일 경우
			else if (persistable instanceof KETMoldChangeActivity) {
			    KETMoldChangeActivity ketMoldChangeActivity = (KETMoldChangeActivity) persistable;
			    String old_worker_oid = ketMoldChangeActivity.getWorkerId();
			    ketMoldChangeActivity.setWorkerId(workerId);

			    // 작업위임자에게 BOM 수정 권한을 준다.
			    WTPart wtPart = null;
			    KETMoldChangeOrder moldEco = null;
			    QueryResult qr = PersistenceHelper.manager.navigate(persistable, "moldECO", KETMoldChangeActivityLink.class);
			    if (qr.hasMoreElements()) {
				moldEco = (KETMoldChangeOrder) qr.nextElement();

			    }
			    if (moldEco != null) {
				qr = PersistenceHelper.manager.navigate(persistable, "bom", KETMoldECABomLink.class, false);
				if (qr.hasMoreElements()) {
				    KETMoldECABomLink ketMoldECABomLink = (KETMoldECABomLink) qr.nextElement();
				    String beforePartOid = ketMoldECABomLink.getBeforePartOid();
				    wtPart = (WTPart) CommonUtil.getObject(beforePartOid);

				}

				if (wtPart != null) {

				    WTConnection wtConnection = EcmUtil.getWTConnection();
				    Connection conn = wtConnection.getConnection();
				    EcmComDao comDao = new EcmComDao(conn);

				    Hashtable coWokerHash = EcmUtil.getCoWorkerInfo(moldEco.getEcoId(), wtPart.getNumber(), workerId);
				    String old_worker_id = "";
				    try {
					WTUser worker = (WTUser) KETObjectUtil.getObject(old_worker_oid);
					PeopleData pData = new PeopleData(worker);
					if (worker != null) {
					    old_worker_id = worker.getName();
					}
				    } catch (Exception e) {
					Kogger.error(getClass(), e);
				    }
				    coWokerHash.put("old_worker_id", old_worker_id);
				    boolean isSuccess = comDao.updateBomCoWoker(coWokerHash);

				}

			    }

			}
			// ECR 일 경우
			else if (persistable instanceof KETChangeRequestExpansion) {
			    KETChangeRequestExpansion ketChangeRequestExpansion = (KETChangeRequestExpansion) persistable;
			    ketChangeRequestExpansion.setCharge(newOwnerWTUser);
			    ketChangeRequestExpansion.setChargeName(newOwnerWTUser.getName());

			    // 주관부서 처리
			    WTChangeRequest2 ecr = ketChangeRequestExpansion.getEcr();
			    QueryResult qr = PersistenceHelper.manager.navigate(ecr, "competent", KETEcrCompetentLink.class, false);

			    while (qr.hasMoreElements()) {
				KETEcrCompetentLink ketEcrCompetentLink = (KETEcrCompetentLink) qr.nextElement();
				KETCompetentDepartment ketCompetentDepartment = ketEcrCompetentLink.getCompetent();

				ketCompetentDepartment.setCharge(newOwnerWTUser);
				ketCompetentDepartment.setChargeName(newOwnerWTUser.getName());

				PersistenceHelper.manager.save(ketCompetentDepartment);
				ketCompetentDepartment = (KETCompetentDepartment) PersistenceHelper.manager.refresh(ketCompetentDepartment);

			    }

			}

			// 저장
			PersistenceHelper.manager.save(persistable);
			PersistenceHelper.manager.refresh(persistable);

		    }

		    // 개발품질문제
		    if (persistable instanceof KETDqmTodoAtivity) {
			KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) persistable;
			if (ketDqmTodoAtivity.getTaskCode().equals("DQM_ACTION")) {
			    KETDqmAction ketDqmAction = ketDqmTodoAtivity.getAction();
			    ketDqmAction.setUser((WTUser) newOwner);

			    ketDqmAction = (KETDqmAction) PersistenceHelper.manager.modify(ketDqmAction);

			    KETDqmRaise ketDqmRaise = (KETDqmRaise) DqmHelper.service.setDqmRaiseActionUser(ketDqmTodoAtivity.getHeader().getRaise(), (WTUser) newOwner);
			    PersistenceHelper.manager.modify(ketDqmRaise);
			}

			if (ketDqmTodoAtivity.getTaskCode().equals("REVIEW_USER_CHOISE")) {
			    KETDqmHeader ketdqmHeader = ketDqmTodoAtivity.getHeader();
			    KETDqmRaise ketDqmRaise = ketdqmHeader.getRaise();
			    ketDqmRaise.setPmUser((WTUser) newOwner);

			    ketDqmRaise = (KETDqmRaise) PersistenceHelper.manager.modify(ketDqmRaise);
			}

		    }

		    // TODO Sample 위임
		    if (persistable instanceof KETSamplePart) {
			KETSamplePart ketSamplePart = (KETSamplePart) persistable;
			if (ketSamplePart.getSamplePartStateCode().equals("REQUEST")) {
			    ketSamplePart.setUser((WTUser) newOwner);
			    ketSamplePart = (KETSamplePart) PersistenceHelper.manager.modify(ketSamplePart);
			}
		    }

		}

	    }

	    WorkflowServerHelper.service.revokeTaskBasedRights(workitem);
	    OwnershipServerHelper.service.changeOwner(workitem, newOwner, false);
	    workitem.setStatus(WfAssignmentState.POTENTIAL);
	    workitem.setOrigOwner(oldOwnerReference);
	    WorkflowServerHelper.service.setTaskBasedRights(workitem, newOwnerReference);
	    PersistenceServerHelper.manager.update(workitem);
	    WorkflowHelper.service.delegate(workitem, newOwner, reason);
	    // 위임이력 추가
	    KETTodoDelegateHistory delegateHistory = KETTodoDelegateHistory.newKETTodoDelegateHistory();
	    delegateHistory.setWorkitem(workitem);
	    delegateHistory.setPbo(workitem.getPrimaryBusinessObject().getObject());
	    delegateHistory.setReason(reason);
	    People fromUser = PeopleHelper.manager.getPeople(oldOwner.getName());
	    People toUser = PeopleHelper.manager.getPeople(newOwner.getName());
	    delegateHistory.setFromUser(fromUser);
	    delegateHistory.setToUser(toUser);
	    PersistenceHelper.manager.save(delegateHistory);
	    // 위임알림메일 발송
	    HashMap<String, Object> map = new HashMap<String, Object>();
	    WorkItemDTO dto = new WorkItemDTO(workitem);
	    map.put("type", dto.getTaskType());
	    map.put("mailPboName", dto.getTaskName());
	    map.put("subject1", dto.getTitle());
	    PeopleData beforeUser = new PeopleData(oldOwnerReference.getPrincipal());
	    PeopleData afterUser = new PeopleData(newOwnerReference.getPrincipal());
	    map.put("column1", beforeUser.departmentName + " " + beforeUser.name + " " + beforeUser.duty);
	    map.put("column2", afterUser.departmentName + " " + afterUser.name + " " + afterUser.duty);
	    map.put("subject3Date", DateUtil.getCurrentDateString("d"));
	    map.put("pbo", workitem.getPrimaryBusinessObject().getObject());
	    map.put("from", oldOwnerReference.getPrincipal());
	    List<WTUser> to = new ArrayList();
	    to.add((WTUser) newOwnerReference.getPrincipal());
	    map.put("to", to);
	    KETMailHelper.service.sendFormMail("08022", "ChargerChangeNoticeMail.html", map);

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

    private WfProcess getWfProcessFromPbo(LifeCycleManaged pbo) throws Exception {

	WfProcess wfProcess = null;
	QuerySpec spec = new QuerySpec(WorkItem.class);
	SearchUtil.appendEQUAL(spec, WorkItem.class, "primaryBusinessObject.key.classname", CommonUtil.getRefOID(pbo), 0);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) spec);
	if (qr.hasMoreElements()) {
	    WorkItem item = (WorkItem) qr.nextElement();
	    WfAssignedActivity activity = (WfAssignedActivity) item.getSource().getObject();
	    wfProcess = activity.getParentProcess();
	}
	return wfProcess;
    }

    @Override
    public HashMap<String, String> getMainContentsMyTask() throws Exception {

	HashMap<String, String> map = new HashMap<String, String>();
	MyTaskDTO dto1 = new MyTaskDTO();
	dto1.setCommand("listMyTask");

	NumberCode progress = CodeHelper.manager.getNumberCode("MYTASKPJTSTATE", "PROGRESS");
	NumberCode planchange = CodeHelper.manager.getNumberCode("MYTASKPJTSTATE", "PLANCHANGE");
	String pjtState = CommonUtil.getOIDLongValue2Str(progress) + "," + CommonUtil.getOIDLongValue2Str(planchange);

	NumberCode taskState = CodeHelper.manager.getNumberCode("MYTASKCOMPLETE", "0");
	dto1.setSearchState(CommonUtil.getOIDLongValue2Str(taskState));
	dto1.setSearchPjtState(CommonUtil.getOIDLongValue2Str(pjtState));
	StatementSpec spec1 = getListMyTaskQuerySpec(dto1);
	if (spec1.isAdvancedQuery()) {
	    if (!spec1.isAdvancedQueryEnabled())
		spec1.setAdvancedQueryEnabled(true);
	}
	QueryResult result1 = PersistenceHelper.manager.find(spec1);
	List<E3PSTask> planList = new ArrayList<E3PSTask>();
	List<E3PSTask> progressList = new ArrayList<E3PSTask>();
	List<E3PSTask> delayList = new ArrayList<E3PSTask>();
	while (result1.hasMoreElements()) {
	    Object[] objects = (Object[]) result1.nextElement();
	    E3PSTask task = (E3PSTask) objects[0];
	    if (task.isLeaf()) {
		// if (ProjectTaskHelper.manager.isLast(task)) {
		ExtendScheduleData scheduleData = (ExtendScheduleData) task.getTaskSchedule().getObject();
		Timestamp planStartDate = scheduleData.getPlanStartDate();
		Timestamp planEndDate = scheduleData.getPlanEndDate();
		Timestamp execStartDate = scheduleData.getExecStartDate();
		Timestamp execEndDate = scheduleData.getExecEndDate();
		Calendar cal = Calendar.getInstance();
		String today = DateUtil.getDateString(new Timestamp(cal.getTimeInMillis()), "d");
		// 계획
		// task 계획시작일 > today and task 실제시작일 = null
		if (compareDate(DateUtil.getDateString(planStartDate, "d"), ">", today) && execStartDate == null) {
		    if (!planList.contains(task))
			planList.add(task);
		}
		// 진행
		// task 실제완료일 = null
		// and ((task 계획시작일 <= today or task 실제시작일 <= today)
		// and (task 계획완료일 >= today
		// or (task 계획완료일 < today and 하나이상 선행 task 미완료)))
		else if (execEndDate == null
		        && ((compareDate(DateUtil.getDateString(planStartDate, "d"), "<=", today) || (execStartDate != null && compareDate(DateUtil.getDateString(execStartDate, "d"), "<=", today))) && (compareDate(
		                DateUtil.getDateString(planEndDate, "d"), ">=", today) || (compareDate(DateUtil.getDateString(planEndDate, "d"), "<", today) && !isAllCompletedDependencyTargetTask(task))))) {
		    if (!progressList.contains(task))
			progressList.add(task);
		}
		// 지연
		// 모든 선행 task가 완료 and (task 실제완료일 = null and task 계획완료일 < today)
		else if (isAllCompletedDependencyTargetTask(task) && (execEndDate == null && compareDate(DateUtil.getDateString(planEndDate, "d"), "<", today))) {
		    if (!delayList.contains(task))
			delayList.add(task);
		}
	    }
	}
	map.put("PLAN", String.valueOf(planList.size()));
	map.put("PROGRESS", String.valueOf(progressList.size()));
	map.put("DELAY", String.valueOf(delayList.size()));
	return map;
    }

    /**
     * 모든 선행태스크를 검색하여 선행태스크가 완료되었는지 여부를 확인.
     * 
     * @param task
     * @return
     * @throws Exception
     * @메소드명 : isAllCompletedDependencyTargetTask
     * @작성자 : Jason, Han
     * @작성일 : 2014. 11. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private boolean isAllCompletedDependencyTargetTask(E3PSTask task) throws Exception {
	boolean flag = true;

	QueryResult result = PersistenceHelper.manager.navigate(task, TaskDependencyLink.DEPEND_TARGET_ROLE, TaskDependencyLink.class, true);
	if (result != null) {
	    while (result.hasMoreElements()) {
		E3PSTask sourceTask = (E3PSTask) result.nextElement();
		if (5 != sourceTask.getTaskState()) {
		    flag = false;
		    break;
		}
	    }
	}
	return flag;
    }

    /**
     * yyyy-MM-dd 형식으로 입력받아 비교함.
     * 
     * @param leftSideDate
     * @param operator
     * @param rightSideDate
     * @return
     * @throws Exception
     * @메소드명 : compareDate
     * @작성자 : Jason, Han
     * @작성일 : 2014. 11. 6.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private boolean compareDate(String leftSideDate, String operator, String rightSideDate) throws Exception {

	boolean flag = false;
	if ("<".equals(operator)) {
	    flag = DateUtil.convertDate2(leftSideDate).before(DateUtil.convertDate2(rightSideDate));
	} else if ("<=".equals(operator)) {
	    flag = DateUtil.convertDate2(leftSideDate).before(DateUtil.convertEndDate2(rightSideDate));
	} else if (">".equals(operator)) {
	    flag = DateUtil.convertDate2(leftSideDate).after(DateUtil.convertDate2(rightSideDate));
	} else if (">=".equals(operator)) {
	    flag = DateUtil.convertDate2(leftSideDate).after(DateUtil.convertStartDate2(rightSideDate));
	}
	return flag;
    }

    @Override
    public int getFilterUncompletedTodoCount() throws Exception {
	int listWorkItemCnt = 0;
	WorkItemDTO workItemDTO = new WorkItemDTO();
	workItemDTO.setCommand("listMyTodo");
	StatementSpec query = KETWorkflowHelper.service.getMyTodoQuery(workItemDTO, true);
	if (query.isAdvancedQuery()) {
	    if (!query.isAdvancedQueryEnabled())
		query.setAdvancedQueryEnabled(true);
	}
	QueryResult result = PersistenceHelper.manager.find(query);
	if (result != null && result.size() > 0)
	    listWorkItemCnt = result.size();
	return listWorkItemCnt;
    }

    // 부품의 도면과 부품에 대해서만 TO-DO를 하나로 처리한다. 즉 위임을 하나로 처리하려고 메소드 만듬
    @Override
    public List<WorkItem> getRelatedWorkItem(WorkItem workItem, String prodType) throws Exception {

	List<WorkItem> ret = new ArrayList<WorkItem>();

	try {

	    List<String> wOidList = getMultiWorkItemByOneEca(workItem, prodType);
	    for (String oid : wOidList) {
		ret.add((WorkItem) CommonUtil.getObject(oid));
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return ret;
    }

    // 여러 개 TODO중에서 하나의 TODO를 가져오기 - 이미 자신에게 온 것 중 KETProdChangeActivity, KETMoldChangeActivity 해당 workItem 만 가져옴
    // 따라서, 쿼리에서 ECO별 Activity는 BOM쪽 하나, 도면쪽 하나로 만들어야 함
    private List<String> getMultiWorkItemByOneEca(WorkItem workItem, String prodType) {

	List<String> ret = new ArrayList<String>();
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<String> alBindList = new ArrayList<String>();

	try {

	    StringBuffer buffer = new StringBuffer();
	    // 미완료 WorkItem 중에서
	    // 동일한 조건 : ECO, activity type, 담당자,
	    if ("PROD".equals(prodType)) {
		buffer.append(" SELECT DISTINCT W.CLASSNAMEA2A2||':'||W.IDA2A2 OID \n");
		buffer.append(" FROM WorkItem W, KETProdChangeActivity A \n");
		buffer.append(" ,( \n");
		buffer.append("  SELECT A.IDA3A8, A.ACTIVITYTYPE, W.IDA3A2OWNERSHIP \n");
		buffer.append("  FROM WorkItem W, KETProdChangeActivity A \n");
		buffer.append("  WHERE W.IDA2A2 = ? \n");
		// buffer.append("  AND W.CLASSNAMEKEYB4 LIKE '%ProdChangeActivity%' \n");
		buffer.append("  AND TO_CHAR( A.IDA2A2 ) =  REPLACE( W.CLASSNAMEKEYB4, 'OR:e3ps.ecm.entity.KETProdChangeActivity:', '') \n");
		buffer.append(" ) B \n");
		buffer.append(" WHERE 1 = 1 \n");
		// buffer.append(" AND W.CLASSNAMEKEYB4 LIKE '%ProdChangeActivity%' \n");
		buffer.append(" AND TO_CHAR( A.IDA2A2 ) =  REPLACE( W.CLASSNAMEKEYB4, 'OR:e3ps.ecm.entity.KETProdChangeActivity:', '') \n");
		buffer.append(" AND A.ACTIVITYTYPE IN ( '1', '2') \n");
		buffer.append(" AND A.IDA3A8 = B.IDA3A8 \n");
		buffer.append(" AND W.IDA3A2OWNERSHIP = B.IDA3A2OWNERSHIP \n");
		buffer.append(" AND W.STATUS = 'POTENTIAL' \n");
		buffer.append(" AND A.ACTIVITYTYPE = B.ACTIVITYTYPE \n");
	    } else {
		buffer.append(" SELECT DISTINCT W.CLASSNAMEA2A2||':'||W.IDA2A2 OID \n");
		buffer.append(" FROM WorkItem W, KETMoldChangeActivity A \n");
		buffer.append(" ,( \n");
		buffer.append("  SELECT A.IDA3A8, A.ACTIVITYTYPE, W.IDA3A2OWNERSHIP \n");
		buffer.append("  FROM WorkItem W, KETMoldChangeActivity A \n");
		buffer.append("  WHERE W.IDA2A2 = ? \n");
		// buffer.append("  AND W.CLASSNAMEKEYB4 LIKE '%MoldChangeActivity%' \n");
		buffer.append("  AND TO_CHAR( A.IDA2A2 ) =  REPLACE( W.CLASSNAMEKEYB4, 'OR:e3ps.ecm.entity.KETMoldChangeActivity:', '') \n");
		buffer.append(" ) B \n");
		buffer.append(" WHERE 1 = 1 \n");
		// buffer.append(" AND W.CLASSNAMEKEYB4 LIKE '%MoldChangeActivity%' \n");
		buffer.append(" AND TO_CHAR( A.IDA2A2 ) =  REPLACE( W.CLASSNAMEKEYB4, 'OR:e3ps.ecm.entity.KETMoldChangeActivity:', '') \n");
		buffer.append(" AND A.ACTIVITYTYPE IN ( '1', '2') \n");
		buffer.append(" AND A.IDA3A8 = B.IDA3A8 \n");
		buffer.append(" AND W.IDA3A2OWNERSHIP = B.IDA3A2OWNERSHIP \n");
		buffer.append(" AND W.STATUS = 'POTENTIAL' \n");
		buffer.append(" AND A.ACTIVITYTYPE = B.ACTIVITYTYPE \n");
	    }

	    String queryStr = buffer.toString();
	    Kogger.debug(getClass(), queryStr);

	    alBindList.add(CommonUtil.getOIDLongValue2Str(workItem));

	    ret = oDaoManager.queryForList(queryStr, alBindList, new RowSetStrategy<String>() {

		@Override
		public String mapRow(ResultSet rs) throws SQLException {

		    String ret = rs.getString("OID");
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

    @Override
    public List<Map<String, String>> getMyRetireUserList(String id) throws Exception {

	WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();

	String auth_id = wtuser.getName();

	if (StringUtils.isNotEmpty(id)) {
	    auth_id = id;
	}

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	List<Map<String, String>> retire_list = null;

	StringBuffer buffer = new StringBuffer();
	buffer.append(" SELECT RETIRE_ID,RETIRE_NAME,AUTH_ID,AUTH_NAME FROM RETIRE_AUTH_INFO \n");
	buffer.append(" WHERE upper(AUTH_ID) = upper('" + auth_id + "') AND AUTH_END_DATE >= TO_DATE(SYSDATE) \n");
	String sSql = buffer.toString();

	retire_list = oDaoManager.queryForList(sSql, new RowSetStrategy<Map<String, String>>() {
	    @Override
	    public Map<String, String> mapRow(ResultSet rs) throws SQLException {

		Map<String, String> retire_list = new HashMap<String, String>();
		retire_list.put("RETIRE_ID", StringUtil.checkNull(rs.getString("RETIRE_ID")));
		retire_list.put("RETIRE_NAME", StringUtil.checkNull(rs.getString("RETIRE_NAME")));
		retire_list.put("AUTH_ID", StringUtil.checkNull(rs.getString("AUTH_ID")));
		retire_list.put("AUTH_NAME", StringUtil.checkNull(rs.getString("AUTH_NAME")));

		return retire_list;
	    }
	});

	return retire_list;

    }

    @Override
    public void sendMailDelayWorkItem() throws Exception {
	// TODO Auto-generated method stub
	List<Map<String, String>> delay_list = this.getDelayWorkItem();
	List<WTUser> listToUser = null;
	WorkItemDTO dto = null;
	WTUser wtUserFrom = KETObjectUtil.getUser("wcadmin");

	for (Map<String, String> target : delay_list) {
	    WTUser user = (WTUser) CommonUtil.getObject(target.get("WTUSER_OID"));
	    listToUser = new ArrayList<WTUser>();
	    listToUser.add(user);
	    dto = new WorkItemDTO();
	    dto.setTargetCnt(target.get("TARGETCNT"));
	    KETMailHelper.service.sendFormMail("08137", "NoticeMailLine1.html", dto, wtUserFrom, listToUser);
	}

    }

    public List<Map<String, String>> getDelayWorkItem() throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	List<Map<String, String>> delay_list = null;

	StringBuffer buffer = new StringBuffer();
	buffer.append(" SELECT A2.ID, A2.NAME AS USER_NAME,A3.NAME DEPT,COUNT(*) AS TARGETCNT,A2.CLASSNAMEKEYB4||':'||A2.IDA3B4 AS WTUSER_OID								 \n");
	buffer.append("   FROM WORKITEM A0,WFASSIGNEDACTIVITY A1, PEOPLE A2, DEPARTMENT A3                                                                                                               \n");
	buffer.append("  WHERE (A0.COMPLETEDBY IS NULL ) AND (A0.IDA3A4 = A1.IDA2A2) AND ((A1.NAME <> '수행담당자') AND (A1.NAME <> '담당자지정')) AND (A1.NAME<> '재작업') AND A1.NAME != ('수신확인')       \n");
	buffer.append("    AND A0.IDA3A2OWNERSHIP = A2.IDA3B4                                                                                                                                            \n");
	buffer.append("    AND A2.IDA3C4 = A3.IDA2A2(+)                                                                                                                                                  \n");
	buffer.append("    AND A2.ISDISABLE = 0                                                                                                                                                          \n");
	// buffer.append("    AND TO_DATE(TO_CHAR(SYSDATE, 'YYYYMMDD')) - TO_DATE(TO_CHAR(A0.CREATESTAMPA2,'YYYYMMDD')) > 2                                                                                 \n");
	buffer.append("    AND (TO_DATE(SYSDATE, 'YYYY-MM-DD') - TO_DATE(A0.CREATESTAMPA2, 'YYYY-MM-DD')) > 2        \n");
	buffer.append("    AND A2.ID NOT IN ('e3ps2(e3ps2)','lcw','Administrator')                                   \n");
	buffer.append("  GROUP BY A2.ID, A2.NAME,A3.NAME,A2.CLASSNAMEKEYB4,A2.IDA3B4				     \n");

	String sSql = buffer.toString();

	delay_list = oDaoManager.queryForList(sSql, new RowSetStrategy<Map<String, String>>() {
	    @Override
	    public Map<String, String> mapRow(ResultSet rs) throws SQLException {

		Map<String, String> delay_list = new HashMap<String, String>();
		delay_list.put("ID", StringUtil.checkNull(rs.getString("ID")));
		delay_list.put("USER_NAME", StringUtil.checkNull(rs.getString("USER_NAME")));
		delay_list.put("DEPT", StringUtil.checkNull(rs.getString("DEPT")));
		delay_list.put("TARGETCNT", StringUtil.checkNull(rs.getString("TARGETCNT")));
		delay_list.put("WTUSER_OID", StringUtil.checkNull(rs.getString("WTUSER_OID")));

		return delay_list;
	    }
	});

	return delay_list;

    }

    @Override
    public void completeExceedReceive() throws Exception {
	// TODO Auto-generated method stub
	boolean isOk = true;

	String masterOid = null;
	Persistable pst = null;

	int count = 0;
	KETWfmApprovalMaster master = null;

	QuerySpec spec = new QuerySpec();

	int idx = spec.addClassList(WorkItem.class, true);
	int idx2 = spec.addClassList(People.class, false);

	spec.appendWhere(new SearchCondition(new ClassAttribute(WorkItem.class, "ownership.owner.key.id"), "=", new ClassAttribute(People.class, "userReference.key.id")), new int[] { idx, idx2 });

	spec.appendAnd();
	spec.appendWhere(new SearchCondition(WorkItem.class, WorkItem.STATUS, SearchCondition.EQUAL, "POTENTIAL"), new int[] { idx });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(WorkItem.class, WorkItem.ROLE, SearchCondition.EQUAL, "RECIPIENT"), new int[] { idx });

	Timestamp limitedTime = new Timestamp(System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000L));

	spec.appendAnd();
	spec.appendWhere(new SearchCondition(WorkItem.class, "thePersistInfo.createStamp", "<=", limitedTime), new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(spec);

	String comment = "한달초과-자동수신처리";

	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    WorkItem item = (WorkItem) obj[0];

	    WfAssignedActivity activity = null;

	    try {
		pst = item.getPrimaryBusinessObject().getObject();
	    } catch (WTRuntimeException wte) {
		isOk = false;
		if (StringUtils.contains(wte.getNestedThrowable().toString(), "wt.fc.ObjectNoLongerExistsException")) {
		    Kogger.debug(getClass(), "**************** (PBO 없음)workItem 삭제 **************************");
		    activity = (WfAssignedActivity) item.getSource().getObject();

		    if (PersistenceHelper.isPersistent(activity)) {
			activity.deleteActivity();
		    }

		    continue;
		} else {
		    wte.printStackTrace();
		}

	    }

	    try {
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
		aHash.put("comment", comment);

		KETWfmHelper.service.completeActivity(aHash); // 결재 수행 완료

		item = (WorkItem) PersistenceHelper.manager.refresh(item);
		item.setDescription(comment);
		PersistenceHelper.manager.save(item);
		count++;
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	}
	Kogger.debug(getClass(), "===> " + count + " 건 수신확인완료");
    }

    @Override
    public void sendMailRetireTargetList() throws Exception {
	List<WTUser> listToUser = null;
	WorkItemDTO dto = new WorkItemDTO();
	WTUser wtUserFrom = KETObjectUtil.getUser("wcadmin");

	List<Map<String, Object>> targetList = getRetireTargetList();

	for (Map<String, Object> map : targetList) {
	    listToUser = new ArrayList<WTUser>();
	    listToUser.add((WTUser) map.get("chief"));
	    dto.setCommand((String) map.get("names"));
	    KETMailHelper.service.sendFormMail("08150", "NoticeMailLine2.html", dto, wtUserFrom, listToUser);
	}
    }

    public List<Map<String, Object>> getRetireTargetList() throws Exception {

	QuerySpec qs = new QuerySpec();

	Map<String, Object> chiefMap = new HashMap<String, Object>();
	Map<String, Object> retireInfoMap = new HashMap<String, Object>();
	List<Map<String, Object>> retireInfoList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> targetList = new ArrayList<Map<String, Object>>();

	// 1.아직 퇴사 확정자가 아니고 퇴사예정일자만 있는 대상자 조회
	int idx = qs.appendClassList(People.class, true);

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(People.class, People.IS_DISABLE, SearchCondition.IS_FALSE), new int[] { idx });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(People.class, People.RETIRE_TARGET_DATE, SearchCondition.NOT_NULL, true), new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(qs);

	// 2.퇴사예정자 정보를 retireInfoList 에 담는다. 또한 퇴사예정자의 팀장 정보를 chiefMap 에 담는다
	while (qr.hasMoreElements()) {

	    Object[] o = (Object[]) qr.nextElement();
	    People peo = (People) o[0];
	    WTUser chief = null;

	    if (peo.getDepartment() != null) {
		chief = PeopleHelper.manager.getChiefUser(peo.getDepartment());
	    } else {
		continue;
	    }
	    if (chief == null) {
		continue;
	    }
	    retireInfoMap = new HashMap<String, Object>();
	    retireInfoMap.put("name", peo.getName());
	    retireInfoMap.put("chief", chief);
	    retireInfoList.add(retireInfoMap);

	    chiefMap.put(CommonUtil.getOIDLongValue2Str(chief), chief);
	}

	Set<String> st = chiefMap.keySet();
	Iterator<String> it = st.iterator();

	String chiefOid = "";

	// 3. 팀장정보가 있는 chiefMap를 루프 돌면서 retireInfoList에서 해당 팀장의 팀원을 찾는다, 팀원명을 names 에 합친 후 팀장 wtuser 객체, names 가 들어있는 map을 targetList에 담는다
	while (it.hasNext()) {
	    chiefOid = (String) it.next();
	    String names = "";
	    for (Map<String, Object> map : retireInfoList) {
		if (chiefOid.equals(CommonUtil.getOIDLongValue2Str((WTUser) (map.get("chief"))))) {
		    names += (String) map.get("name") + ",";
		}
	    }
	    retireInfoMap = new HashMap<String, Object>();
	    retireInfoMap.put("chief", chiefMap.get(chiefOid));
	    retireInfoMap.put("names", StringUtils.removeEnd(names, ","));

	    targetList.add(retireInfoMap);

	}

	return targetList;
    }
}
