package ext.ket.dms.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import wt.doc.WTDocumentMaster;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.pds.StatementSpec;
import wt.pom.Transaction;
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
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import e3ps.common.code.NumberCode;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETDocumentOutputLink;
import e3ps.dms.entity.KETDocumentPartLink;
import e3ps.dms.entity.KETDocumentProjectLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.ProjectOutput;
import e3ps.project.ReviewProject;
import e3ps.project.WorkProject;
import e3ps.project.beans.ProjectOutputHelper;
import ext.ket.dms.entity.ProjectDocumentDTO;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.SearchUtil;
import ext.ket.shared.util.SessionUtil;

/**
 * @클래스명 : StandardKETProjectDocumentService
 * @작성자 : Jason, Han
 * @작성일 : 2014. 7. 24.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class StandardKETProjectDocumentService extends StandardManager implements KETProjectDocumentService, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1499072975242096841L;

    public static StandardKETProjectDocumentService newStandardKETProjectDocumentService() throws WTException {
	StandardKETProjectDocumentService instance = new StandardKETProjectDocumentService();
	instance.initialize();
	return instance;
    }

    @Override
    public List<KETProjectDocument> find(ProjectDocumentDTO paramObject) throws Exception {

	List<KETProjectDocument> list = null;
	StatementSpec query = getListProjectDocumentQuerySpec(paramObject);
	Kogger.debug(getClass(), query);
	if (!query.isAdvancedQueryEnabled())
	    query.setAdvancedQueryEnabled(true);
	// QueryResult result = PersistenceHelper.manager.find(query);
	QueryResult result = PersistenceServerHelper.manager.query(query);
	if (result != null) {
	    list = new ArrayList<KETProjectDocument>();
	    while (result.hasMoreElements()) {
		Object[] objArr = (Object[]) result.nextElement();
		list.add((KETProjectDocument) objArr[0]);
	    }
	}
	return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.service.CommonServiceInterface#findPaging(java.lang.Object)
     */
    @Override
    public PageControl findPaging(ProjectDocumentDTO paramObject) throws Exception {

	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	StatementSpec query = null;
	if ("listProjectDocument".equals(paramObject.getCommand())) {
	    query = getListProjectDocumentQuerySpec(paramObject);
	}
	Kogger.debug(getClass(), query);
	if (!query.isAdvancedQueryEnabled())
	    query.setAdvancedQueryEnabled(true);
	if (query != null) {
	    if (StringUtil.isEmpty(pageSessionId)) {
		pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), query);
	    } else {
		pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), paramObject.getPage() + 1, pageSessionId);

	    }
	}
	return pageControl;
    }

    @Override
    public StatementSpec getListProjectDocumentQuerySpec(ProjectDocumentDTO paramObject) throws Exception {
	StatementSpec statementSpec = null;
	String pjtType = paramObject.getPjtType();
	String pjtNo = paramObject.getPjtNo();
	String pjtName = paramObject.getPjtName();
	if (StringUtil.checkString(pjtType)) {
	    QuerySpec spec = (QuerySpec) getProjectDocumentQuerySpec(paramObject);
	    // logger.info("getProjectDocumentQuerySpec : " + spec);
	    if (StringUtil.checkString(pjtNo) || StringUtil.checkString(pjtName)) {
		QuerySpec spec1 = (QuerySpec) spec.clone();
		QuerySpec spec2 = (QuerySpec) spec.clone();
		spec1 = (QuerySpec) getDocumentProjectLinkJoinQuery(spec1, paramObject);
		// logger.info("getDocumentProjectLinkJoinQuery : " + spec1);
		spec2 = (QuerySpec) getDocumentOutputLinkJoinQuery(spec2, paramObject);
		// logger.info("getDocumentOutputLinkJoinQuery : " + spec2);
		CompoundQuerySpec querySpec = new CompoundQuerySpec();
		querySpec.setSetOperator(SetOperator.UNION);
		querySpec.addComponent(spec1);
		querySpec.addComponent(spec2);
		statementSpec = querySpec;
		// logger.info("getListProjectDocumentQuerySpec : " + statementSpec);
	    } else {
		statementSpec = spec;
	    }
	} else {
	    paramObject.setPjtType("제품");
	    QuerySpec spec1 = (QuerySpec) getProjectDocumentQuerySpec(paramObject);
	    StatementSpec statementSpec1 = null;
	    if (StringUtil.checkString(pjtNo) || StringUtil.checkString(pjtName)) {
		QuerySpec spec1_1 = (QuerySpec) spec1.clone();
		QuerySpec spec1_2 = (QuerySpec) spec1.clone();
		spec1_1 = (QuerySpec) getDocumentProjectLinkJoinQuery(spec1_1, paramObject);
		spec1_2 = (QuerySpec) getDocumentOutputLinkJoinQuery(spec1_2, paramObject);
		CompoundQuerySpec querySpec = new CompoundQuerySpec();
		querySpec.setSetOperator(SetOperator.UNION);
		querySpec.addComponent(spec1_1);
		querySpec.addComponent(spec1_2);
		statementSpec1 = querySpec;
	    } else {
		statementSpec1 = spec1;
	    }
	    paramObject.setPjtType("검토");
	    QuerySpec spec2 = (QuerySpec) getProjectDocumentQuerySpec(paramObject);
	    StatementSpec statementSpec2 = null;
	    if (StringUtil.checkString(pjtNo) || StringUtil.checkString(pjtName)) {
		QuerySpec spec1_1 = (QuerySpec) spec2.clone();
		QuerySpec spec1_2 = (QuerySpec) spec2.clone();
		spec1_1 = (QuerySpec) getDocumentProjectLinkJoinQuery(spec1_1, paramObject);
		spec1_2 = (QuerySpec) getDocumentOutputLinkJoinQuery(spec1_2, paramObject);
		CompoundQuerySpec querySpec = new CompoundQuerySpec();
		querySpec.setSetOperator(SetOperator.UNION);
		querySpec.addComponent(spec1_1);
		querySpec.addComponent(spec1_2);
		statementSpec2 = querySpec;
	    } else {
		statementSpec2 = spec2;
	    }
	    paramObject.setPjtType("금형");
	    QuerySpec spec3 = (QuerySpec) getProjectDocumentQuerySpec(paramObject);
	    StatementSpec statementSpec3 = null;
	    if (StringUtil.checkString(pjtNo) || StringUtil.checkString(pjtName)) {
		QuerySpec spec1_1 = (QuerySpec) spec3.clone();
		QuerySpec spec1_2 = (QuerySpec) spec3.clone();
		spec1_1 = (QuerySpec) getDocumentProjectLinkJoinQuery(spec1_1, paramObject);
		spec1_2 = (QuerySpec) getDocumentOutputLinkJoinQuery(spec1_2, paramObject);
		CompoundQuerySpec querySpec = new CompoundQuerySpec();
		querySpec.setSetOperator(SetOperator.UNION);
		querySpec.addComponent(spec1_1);
		querySpec.addComponent(spec1_2);
		statementSpec3 = querySpec;
	    } else {
		statementSpec3 = spec3;
	    }
	    CompoundQuerySpec querySpec = new CompoundQuerySpec();
	    querySpec.setSetOperator(SetOperator.UNION);
	    querySpec.addComponent(statementSpec1);
	    querySpec.addComponent(statementSpec2);
	    querySpec.addComponent(statementSpec3);
	    querySpec.setUseComponentOrderBy(true);
	    if (!querySpec.isAdvancedQueryEnabled())
		querySpec.setAdvancedQueryEnabled(true);
	    statementSpec = querySpec;
	}

	QuerySpec spec = new QuerySpec();
	int idx = 0;
	int ctL_idx = 0;
	int ct_idx = 0;
	int p_idx = 0;
	if (paramObject.getRowNum() > 0) {

	    String createStamp = "createStampA2";
	    idx = spec.addClassList(KETProjectDocument.class, false);
	    ctL_idx = spec.addClassList(KETDocumentCategoryLink.class, false);
	    ct_idx = spec.addClassList(KETDocumentCategory.class, false);
	    spec.appendSelect(new ClassAttribute(KETProjectDocument.class, WTAttributeNameIfc.OID_CLASSNAME), new int[] { idx }, false);
	    spec.appendSelect(new ClassAttribute(KETProjectDocument.class, WTAttributeNameIfc.ID_NAME), new int[] { idx }, false);
	    TableColumn createStampAttr1 = new TableColumn(spec.getFromClause().getAliasAt(idx), "createStampA2");
	    createStampAttr1.setColumnAlias(createStamp);
	    spec.appendSelect(createStampAttr1, new int[] { idx }, false);
	} else {
	    idx = spec.addClassList(KETProjectDocument.class, true);
	    ctL_idx = spec.addClassList(KETDocumentCategoryLink.class, false);
	    ct_idx = spec.addClassList(KETDocumentCategory.class, false);
	}

	TableColumn masterOidAttr = new TableColumn(spec.getFromClause().getAliasAt(idx), "IDA3MASTERREFERENCE");
	TableColumn versionAttr = new TableColumn(spec.getFromClause().getAliasAt(idx), "VERSIONIDA2VERSIONINFO");

	ColumnListExpression listExpression = new ColumnListExpression();
	listExpression.addColumn(masterOidAttr);

	String version = paramObject.getVersion();
	if (StringUtil.checkString(version)) {
	    NumberCode code = (NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:" + version);
	    if ("LATEST".equals(code.getCode())) {
		listExpression.addColumn(versionAttr);
	    }
	}

	SubSelectExpression selectExpression = new SubSelectExpression(statementSpec);
	selectExpression.setAccessControlRequired(false);
	spec.appendWhere(new SearchCondition(listExpression, "IN", selectExpression), new int[] { idx });
	// latest iteration
	SearchUtil.appendBOOLEAN(spec, KETProjectDocument.class, "iterationInfo.latest", SearchCondition.IS_TRUE, idx);

	spec.appendAnd();
	// Join KETDocumentCategoryLink - KETProjectDocument
	spec.appendWhere(new SearchCondition(new ClassAttribute(KETDocumentCategoryLink.class, "roleBObjectRef.key.id"), "=", new ClassAttribute(KETProjectDocument.class,
	        "thePersistInfo.theObjectIdentifier.id")), new int[] { ctL_idx, idx });

	spec.appendAnd();
	// Join KETDocumentCategory - KETDocumentCategoryLink
	spec.appendWhere(new SearchCondition(new ClassAttribute(KETDocumentCategory.class, "thePersistInfo.theObjectIdentifier.id"), "=", new ClassAttribute(KETDocumentCategoryLink.class,
	        "roleAObjectRef.key.id")), new int[] { ct_idx, ctL_idx });

	// 부품
	String partNo = paramObject.getPartNo();
	if (StringUtil.checkString(partNo)) {
	    String[] partNo_ = partNo.split(",");

	    Class<KETDocumentPartLink> partLinkClass = KETDocumentPartLink.class;

	    p_idx = spec.addClassList(partLinkClass, false);

	    spec.appendAnd();
	    // Join KETDocumentCategoryLink - KETProjectDocument
	    spec.appendWhere(
		    new SearchCondition(new ClassAttribute(partLinkClass, "roleAObjectRef.key.id"), "=", new ClassAttribute(KETProjectDocument.class, "thePersistInfo.theObjectIdentifier.id")),
		    new int[] { p_idx, idx });

	    spec.appendAnd();
	    spec.appendOpenParen();
	    for (int i = 0; i < partNo_.length; i++) {
		partNo_[i] = partNo_[i].replaceAll("\\*", "%");
		if (i != 0)
		    spec.appendOr();
		spec.appendWhere(new SearchCondition(partLinkClass, KETDocumentPartLink.PART_NO, SearchCondition.LIKE, partNo_[i], false), new int[] { p_idx });
	    }
	    spec.appendCloseParen();

	}

	String dateRevision = paramObject.getDateRevision();
	if (StringUtil.checkString(dateRevision)) {
	    // 개정도래일
	    String alias = spec.getFromClause().getAliasAt(idx);
	    String subQs = "(TRUNC(TO_DATE(" + alias + ".ATTRIBUTE9,'YYYY-MM-DD') + (" + alias + ".ATTRIBUTE10*30)-(SELECT SYSDATE FROM DUAL)))";

	    KeywordExpression kexp = new KeywordExpression(dateRevision);
	    KeywordExpression kexp2 = new KeywordExpression(subQs);
	    SearchCondition sc = new SearchCondition(kexp, SearchCondition.GREATER_THAN_OR_EQUAL, kexp2);
	    spec.appendAnd();
	    spec.appendWhere(sc, new int[] { idx });
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    String checkName = paramObject.getSortName();

	    if (checkName.equals("documentNo") || checkName.equals("-documentNo")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETProjectDocument.class, idx, KETProjectDocument.DOCUMENT_NO, true);
		} else {
		    SearchUtil.setOrderBy(spec, KETProjectDocument.class, idx, KETProjectDocument.DOCUMENT_NO, false);
		}
	    }

	    if (checkName.equals("documentName") || checkName.equals("-documentName")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETProjectDocument.class, idx, KETProjectDocument.TITLE, true);
		} else {
		    SearchUtil.setOrderBy(spec, KETProjectDocument.class, idx, KETProjectDocument.TITLE, false);
		}
	    }

	    if (checkName.equals("createDate") || checkName.equals("-createDate")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETProjectDocument.class, idx, KETProjectDocument.CREATE_TIMESTAMP, true);
		} else {
		    SearchUtil.setOrderBy(spec, KETProjectDocument.class, idx, KETProjectDocument.CREATE_TIMESTAMP, false);
		}
	    }

	    if (checkName.equals("state") || checkName.equals("-state")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETProjectDocument.class, idx, KETProjectDocument.LIFE_CYCLE_STATE, true);
		} else {
		    SearchUtil.setOrderBy(spec, KETProjectDocument.class, idx, KETProjectDocument.LIFE_CYCLE_STATE, false);
		}
	    }

	    if (checkName.equals("creator") || checkName.equals("-creator")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETProjectDocument.class, idx, "iterationInfo.creator.key.id", true);
		} else {
		    SearchUtil.setOrderBy(spec, KETProjectDocument.class, idx, "iterationInfo.creator.key.id", false);
		}
	    }

	    if (checkName.equals("version") || checkName.equals("-version")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETProjectDocument.class, idx, "versionInfo.identifier.versionId", true);
		} else {
		    SearchUtil.setOrderBy(spec, KETProjectDocument.class, idx, "versionInfo.identifier.versionId", false);
		}
	    }

	    if (checkName.equals("buyerSummit") || checkName.equals("-buyerSummit")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETProjectDocument.class, idx, KETProjectDocument.IS_BUYER_SUMMIT, true);
		} else {
		    SearchUtil.setOrderBy(spec, KETProjectDocument.class, idx, KETProjectDocument.IS_BUYER_SUMMIT, false);
		}
	    }

	    if (checkName.equals("projectDocType") || checkName.equals("-projectDocType")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETDocumentCategory.class, ct_idx, KETDocumentCategory.CATEGORY_NAME, true);
		} else {
		    SearchUtil.setOrderBy(spec, KETDocumentCategory.class, ct_idx, KETDocumentCategory.CATEGORY_NAME, false);
		}
	    }

	    if (checkName.equals("dateRevision") || checkName.equals("-dateRevision")) {

		String alias = spec.getFromClause().getAliasAt(idx);
		String subQs = "(TRUNC(TO_DATE(" + alias + ".ATTRIBUTE9,'YYYY-MM-DD') + (" + alias + ".ATTRIBUTE10*30)-(SELECT SYSDATE FROM DUAL)))";
		KeywordExpression kexp = new KeywordExpression(subQs);
		kexp.setColumnAlias("DATEREV");
		spec.appendSelect(kexp, true);

		if (checkName.startsWith("-")) {
		    spec.appendOrderBy(new OrderBy(kexp, true), new int[] {});
		} else {
		    spec.appendOrderBy(new OrderBy(kexp, false), new int[] {});
		}
	    }
	}

	System.out.println("###" + spec);
	// SearchUtil.setOrderBy(spec, KETProjectDocument.class, idx, WTAttributeNameIfc.CREATE_STAMP_NAME, true);

	return spec;
    }

    @SuppressWarnings("deprecation")
    @Override
    public StatementSpec getProjectDocumentQuerySpec(ProjectDocumentDTO paramObject) throws Exception {

	QuerySpec spec = new QuerySpec();
	Class<WTDocumentMaster> masterClass = WTDocumentMaster.class;
	Class<KETProjectDocument> iterClass = KETProjectDocument.class;
	int m_idx = spec.addClassList(masterClass, false);
	int i_idx = spec.addClassList(iterClass, false);

	// select field
	// ClassAttribute classnameAttr = new ClassAttribute(iterClass, WTAttributeNameIfc.OID_CLASSNAME);
	// ClassAttribute oidAttr = new ClassAttribute(iterClass, WTAttributeNameIfc.ID_NAME);
	ClassAttribute masterOidAttr = new ClassAttribute(iterClass, "masterReference.key.id");
	// TableColumn classnameAttr = new TableColumn(spec.getFromClause().getAliasAt(i_idx), "classnameA2A2");
	// TableColumn oidAttr = new TableColumn(spec.getFromClause().getAliasAt(i_idx), "IDA2A2");
	// spec.appendSelect(classnameAttr, new int[] { i_idx }, false);
	spec.appendSelect(masterOidAttr, new int[] { i_idx }, false);

	// Join WTDocumentMaster - KETProjectDocument
	spec.appendWhere(new SearchCondition(new ClassAttribute(masterClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(iterClass, "masterReference.key.id")), new int[] { m_idx, i_idx });

	// 관리자 제외
	// 아래는 부서열람권한 로직인데 일단 주석처리 (검색은 되도록 해달라는 연구지원팀 요청)
	// if(!CommonUtil.isAdmin()) {
	// //###부서 조회 권한 START #######################################
	//
	// WTUser session = (WTUser) SessionHelper.manager.getPrincipal();
	//
	// PeopleData pd = new PeopleData(session);
	// String deptCode = pd.department.getCode();
	//
	// if (spec.getConditionCount() > 0) spec.appendAnd();
	//
	// spec.appendOpenParen();
	// String alias = spec.getFromClause().getAliasAt(i_idx);
	// String subQs = "(SELECT CODE FROM DEPARTMENT CONNECT BY PRIOR IDA3PARENTREFERENCE = IDA2A2 START WITH CODE = '" + deptCode +
	// "')";
	// KeywordExpression kexp = new KeywordExpression(alias + "." + KETProjectDocument.ATTRIBUTE4);
	// KeywordExpression kexp2 = new KeywordExpression(subQs);
	// SearchCondition sc = new SearchCondition(kexp, SearchCondition.IN, kexp2);
	//
	// spec.appendWhere(sc, new int[] { i_idx });
	//
	// if (spec.getConditionCount() > 0) spec.appendOr();
	// spec.appendWhere(new SearchCondition(iterClass, KETProjectDocument.ATTRIBUTE4, SearchCondition.IS_NULL, true), new int[] { i_idx
	// });
	//
	// spec.appendCloseParen();
	//
	// //###부서 조회 권한 END #######################################
	//
	// //###사용자 조회 권한 START #######################################
	//
	// @SuppressWarnings("unchecked")
	// Vector<String> dutyCodeList = CompanyState.dutyCodeList;
	// String dutyCode = pd.dutycode;
	// String aclDutyCode = "";
	//
	// boolean isDutyAcl = false;
	//
	// for(String dutyC : dutyCodeList) {
	//
	// if(!isDutyAcl && dutyC.equals(dutyCode)) isDutyAcl = true;
	//
	// if(isDutyAcl) {
	// if(StringUtil.checkString(aclDutyCode)) aclDutyCode += ",";
	// aclDutyCode += dutyC;
	// }
	// }
	//
	// if (spec.getConditionCount() > 0) spec.appendAnd();
	// spec.appendOpenParen();
	// spec.appendWhere(new SearchCondition(new ClassAttribute(iterClass, KETProjectDocument.ATTRIBUTE5), SearchCondition.IN, new
	// ArrayExpression(
	// aclDutyCode.split(","))), new int[] { i_idx });
	// if (spec.getConditionCount() > 0) spec.appendOr();
	// spec.appendWhere(new SearchCondition(iterClass, KETProjectDocument.ATTRIBUTE5, SearchCondition.IS_NULL, true), new int[] { i_idx
	// });
	//
	// spec.appendCloseParen();
	// //###사용자 조회 권한 END #######################################
	// }

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
	// 작성자
	String creator = paramObject.getCreator();
	if (!StringUtil.checkNull(paramObject.getTotalFlag()).equals("Y")) {
	    if (StringUtil.checkString(creator)) {
		SearchUtil.appendEQUAL(spec, iterClass, "iterationInfo.creator.key.id", CommonUtil.getOIDLongValue(creator), i_idx);
	    }
	}
	// 작성부서
	String deptName = paramObject.getDeptName();
	if (StringUtil.checkString(deptName)) {
	    SearchUtil.appendLIKE(spec, iterClass, KETProjectDocument.DEPT_NAME, deptName, i_idx);
	}

	// KETS 사업부 관련 필터링
	CommonUtil.ketsUserListWhereStrQs(spec, iterClass, i_idx, "iterationInfo.creator.key.id", true);

	// 사업부
	String divisionOid = paramObject.getDivisionCode();
	if (StringUtil.checkString(divisionOid)) {
	    String[] divisionOids = divisionOid.split(",");
	    String divisionCode = "";
	    for (String oid : divisionOids) {
		NumberCode ncode = (NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:" + oid);
		divisionCode += ncode.getCode() + ",";
	    }
	    String[] divisionCodes = divisionCode.split(",");
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(iterClass, "divisionCode", divisionCodes, true, false), new int[] { i_idx });
	}
	// 고객 제출자료
	String buyerSummit = paramObject.getBuyerSummit();
	if (StringUtil.checkString(buyerSummit) && !"671978938".equals(buyerSummit)) {
	    NumberCode code = (NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:" + buyerSummit);
	    SearchUtil.appendEQUAL(spec, iterClass, "isBuyerSummit", code.getCode(), i_idx);
	}
	// 고객사
	String subcontractors = paramObject.getSubcontractor();
	if (StringUtil.checkString(subcontractors)) {
	    String[] subcontractor = subcontractors.split(",");
	    if (subcontractor.length > 1) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendOpenParen();
		for (int i = 0; i < subcontractor.length; i++) {
		    spec.appendWhere(new SearchCondition(iterClass, "buyerCode", SearchCondition.LIKE, "%" + subcontractor[i] + "%", false), new int[] { i_idx });
		    if (i != subcontractor.length - 1)
			spec.appendOr();
		}
		spec.appendCloseParen();
	    } else {
		SearchUtil.appendLIKE(spec, iterClass, "buyerCode", subcontractor[0], i_idx);
	    }
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
		SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, versionAttr));
		spec.appendSelect(function, new int[] { i_idx }, false);
		if (spec.getGroupByClause() == null) {
		    // Group by
		    // spec.appendGroupBy(classnameAttr, new int[] { i_idx }, false);
		    spec.appendGroupBy(masterOidAttr, new int[] { i_idx }, false);
		}
	    }
	}
	// 부품
	/*
	 * String partNo = paramObject.getPartNo(); if (StringUtil.checkString(partNo)) { String[] partNo_ = partNo.split(",");
	 * 
	 * if (!spec.isAdvancedQueryEnabled()) spec.setAdvancedQueryEnabled(true); Class<KETDocumentPartLink> partLinkClass =
	 * KETDocumentPartLink.class; Class<WTPartMaster> partMasterClass = WTPartMaster.class; Class<WTPart> partClass = WTPart.class; int
	 * pl_idx = spec.addClassList(partLinkClass, false); int pm_idx = spec.addClassList(partMasterClass, false); int p_idx =
	 * spec.addClassList(partClass, false);
	 * 
	 * // join KETProjectDocument - KETDocumentPartLink if (spec.getConditionCount() > 0) spec.appendAnd();
	 * 
	 * spec.appendWhere(new SearchCondition(new ClassAttribute(iterClass, WTAttributeNameIfc.ID_NAME), "=", new
	 * ClassAttribute(partLinkClass, WTAttributeNameIfc.ROLEA_OBJECT_ID)), new int[] { i_idx, pl_idx }); // join WTPart -
	 * KETDocumentPartLink if (spec.getConditionCount() > 0) spec.appendAnd(); spec.appendWhere(new SearchCondition(new
	 * ClassAttribute(partClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(partLinkClass,
	 * WTAttributeNameIfc.ROLEB_OBJECT_ID)), new int[] { p_idx, pl_idx }); // join WTPartMaster - WTPart if (spec.getConditionCount() >
	 * 0) spec.appendAnd(); spec.appendWhere(new SearchCondition(new ClassAttribute(partMasterClass, WTAttributeNameIfc.ID_NAME), "=",
	 * new ClassAttribute(partClass, "masterReference.key.id")), new int[] { pm_idx, p_idx }); if (spec.getConditionCount() > 0)
	 * spec.appendAnd();
	 * 
	 * spec.appendOpenParen(); for (int i = 0; i < partNo_.length; i++) { partNo_[i] = partNo_[i].replaceAll("\\*", "%"); if (i != 0)
	 * spec.appendOr(); spec.appendWhere(new SearchCondition(partClass, WTPart.NUMBER, SearchCondition.LIKE, partNo_[i], false), new
	 * int[] { p_idx }); } spec.appendCloseParen();
	 * 
	 * // SearchUtil.appendLIKE(spec, partClass, WTPart.NUMBER, partNo, p_idx); // spec.appendWhere(new SearchCondition(partClass,
	 * WTPart.NUMBER, partNo_, true, false), new int[] { p_idx }); // latest iteration
	 * 
	 * //SearchUtil.appendBOOLEAN(spec, partClass, "iterationInfo.latest", SearchCondition.IS_TRUE, p_idx); //KETDocumentPartLink의 연계방식이
	 * 문서 리비전 to 자재마스터 방식으로 바뀜에 따라 주석처리함 2019.05.22 황정태
	 * 
	 * System.out.println("spec ## " + spec); // max version select // if (!spec.isAdvancedQueryEnabled()) //
	 * spec.setAdvancedQueryEnabled(true); // TableColumn versionAttr = new TableColumn(spec.getFromClause().getAliasAt(p_idx),
	 * "VERSIONIDA2VERSIONINFO"); // SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, versionAttr); //
	 * spec.appendSelect(function, new int[] { p_idx }, false); // if (spec.getGroupByClause() == null) { // // Group by // //
	 * spec.appendGroupBy(classnameAttr, new int[] { i_idx }, false); // spec.appendGroupBy(oidAttr, new int[] { i_idx }, false); // } }
	 */
	// 문서분류, 품질확보절차
	String projectDocType = paramObject.getProjectDocType();
	String documentQuality = paramObject.getDocumentQuality();
	if (StringUtil.checkString(projectDocType) || StringUtil.checkString(documentQuality)) {
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

	    if (StringUtil.checkString(projectDocType)) {
		String[] projectDocTypeArr = projectDocType.split(",");
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(categoryClass, KETDocumentCategory.CATEGORY_CODE, projectDocTypeArr, true, false), new int[] { c_idx });
	    }
	    if (StringUtil.checkString(documentQuality)) {
		String[] documentQualityArr = documentQuality.split(",");
		if (documentQualityArr.length > 0) {
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    spec.appendOpenParen();
		    for (int i = 0; i < documentQualityArr.length; i++) {
			if ("671979006".equals(documentQualityArr[i])) { // APQP
			    spec.appendWhere(new SearchCondition(categoryClass, KETDocumentCategory.IS_APQP, SearchCondition.IS_TRUE), new int[] { c_idx });
			} else if ("671979007".equals(documentQualityArr[i])) { // PSO10
			    spec.appendWhere(new SearchCondition(categoryClass, KETDocumentCategory.IS_PSO10, SearchCondition.IS_TRUE), new int[] { c_idx });
			} else if ("671979008".equals(documentQualityArr[i])) { // ESIR
			    spec.appendWhere(new SearchCondition(categoryClass, KETDocumentCategory.IS_ESIR, SearchCondition.IS_TRUE), new int[] { c_idx });
			} else if ("671979009".equals(documentQualityArr[i])) { // ISIRCar
			    spec.appendWhere(new SearchCondition(categoryClass, KETDocumentCategory.IS_ISIRCAR, SearchCondition.IS_TRUE), new int[] { c_idx });
			} else if ("671979010".equals(documentQualityArr[i])) { // ISIRElec
			    spec.appendWhere(new SearchCondition(categoryClass, KETDocumentCategory.IS_ISIRELEC, SearchCondition.IS_TRUE), new int[] { c_idx });
			} else if ("671979011".equals(documentQualityArr[i])) { // ANPQP
			    spec.appendWhere(new SearchCondition(categoryClass, KETDocumentCategory.IS_ANPQP, SearchCondition.IS_TRUE), new int[] { c_idx });
			} else if ("671979012".equals(documentQualityArr[i])) { // SYMC
			    spec.appendWhere(new SearchCondition(categoryClass, KETDocumentCategory.IS_SYMC, SearchCondition.IS_TRUE), new int[] { c_idx });
			}
			if (i < documentQualityArr.length - 1)
			    spec.appendOr();
		    }
		    spec.appendCloseParen();
		}
	    }
	}
	// latest iteration
	SearchUtil.appendBOOLEAN(spec, iterClass, "iterationInfo.latest", SearchCondition.IS_TRUE, i_idx);
	spec.setDescendantQuery(false);

	System.out.print("###########" + spec);

	return spec;
    }

    @Override
    public StatementSpec getDocumentProjectLinkJoinQuery(QuerySpec spec, ProjectDocumentDTO paramObject) throws Exception {

	// pjtNo, pjtName
	String pjtType = paramObject.getPjtType();
	String pjtNo = paramObject.getPjtNo();
	String pjtName = paramObject.getPjtName();
	if (StringUtil.checkString(pjtNo) || StringUtil.checkString(pjtName)) {
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
	    spec.appendWhere(new SearchCondition(new ClassAttribute(KETProjectDocument.class, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(projLinkClass, WTAttributeNameIfc.ROLEA_OBJECT_ID)),
		    new int[] { spec.getFromClause().getPosition(KETProjectDocument.class), pl_idx });
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
	    if (StringUtil.checkString(pjtNo))
		if (pjtNo.indexOf(",") > 0) {
		    String[] pjtNos = pjtNo.split(",");
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    spec.appendWhere(new SearchCondition(projMasterClass, "pjtNo", pjtNos, true, false), new int[] { pm_idx });
		} else {
		    SearchUtil.appendLIKE(spec, projMasterClass, "pjtNo", pjtNo, pm_idx);
		}
	    if (StringUtil.checkString(pjtName))
		SearchUtil.appendLIKE(spec, projMasterClass, "pjtName", pjtName, pm_idx);
	}
	return spec;
    }

    @Override
    public StatementSpec getDocumentOutputLinkJoinQuery(QuerySpec spec, ProjectDocumentDTO paramObject) throws Exception {

	// pjtNo, pjtName
	String pjtType = paramObject.getPjtType();
	String pjtNo = paramObject.getPjtNo();
	String pjtName = paramObject.getPjtName();
	if (StringUtil.checkString(pjtNo) || StringUtil.checkString(pjtName)) {
	    Class<KETDocumentOutputLink> projLinkClass = KETDocumentOutputLink.class;
	    Class<ProjectOutput> projOutputClass = ProjectOutput.class;
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
	    int po_idx = spec.addClassList(projOutputClass, false);
	    int pm_idx = spec.addClassList(projMasterClass, false);
	    int p_idx = spec.addClassList(projClass, false);

	    // join KETProjectDocument - KETDocumentOutputLink
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(KETProjectDocument.class, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(projLinkClass, WTAttributeNameIfc.ROLEA_OBJECT_ID)),
		    new int[] { spec.getFromClause().getPosition(KETProjectDocument.class), pl_idx });
	    // join ProjectOutput - KETDocumentOutputLink
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(projOutputClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(projLinkClass, WTAttributeNameIfc.ROLEB_OBJECT_ID)),
		    new int[] { po_idx, pl_idx });
	    // join ProductProject - ProjectOutput
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(projClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(projOutputClass, "projectReference.key.id")), new int[] { p_idx,
		    po_idx });
	    // join E3PSProjectMaster - ProductProject
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(projMasterClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(projClass, "masterReference.key.id")), new int[] { pm_idx,
		    p_idx });
	    if (StringUtil.checkString(pjtNo))
		if (pjtNo.indexOf(",") > 0) {
		    String[] pjtNos = pjtNo.split(",");
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    spec.appendWhere(new SearchCondition(projMasterClass, "pjtNo", pjtNos, true, false), new int[] { pm_idx });
		} else {
		    SearchUtil.appendLIKE(spec, projMasterClass, "pjtNo", pjtNo, pm_idx);
		}
	    if (StringUtil.checkString(pjtName))
		SearchUtil.appendLIKE(spec, projMasterClass, "pjtName", pjtName, pm_idx);
	}
	return spec;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.service.CommonServiceInterface#save(java.lang.Object)
     */
    @Override
    public KETProjectDocument save(ProjectDocumentDTO paramObject) throws Exception {

	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.service.CommonServiceInterface#modify(java.lang.Object)
     */
    @Override
    public KETProjectDocument modify(ProjectDocumentDTO paramObject) throws Exception {
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.service.CommonServiceInterface#delete(java.lang.Object)
     */
    @Override
    public KETProjectDocument delete(ProjectDocumentDTO paramObject) throws Exception {
	return null;
    }

    @Override
    public KETProjectDocument revise(KETProjectDocument doc) throws Exception {

	Transaction trx = new Transaction();

	try {

	    trx.start();

	    KETProjectDocument newDoc = null;

	    newDoc = (KETProjectDocument) VersionControlHelper.service.newVersion(doc);

	    String lifecycle = ((LifeCycleManaged) doc).getLifeCycleName();
	    Folder folder = FolderHelper.service.getFolder((FolderEntry) doc);
	    newDoc = (KETProjectDocument) VersionControlHelper.service.newVersion(doc);
	    FolderHelper.assignLocation((FolderEntry) newDoc, folder);

	    LifeCycleHelper.setLifeCycle(newDoc, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, WCUtil.getWTContainerRef())); // Lifecycle
	    newDoc = (KETProjectDocument) PersistenceHelper.manager.save(newDoc);

	    // 관련 부품
	    QueryResult r = PersistenceHelper.manager.navigate(doc, "part", KETDocumentPartLink.class, false);
	    while (r.hasMoreElements()) {
		KETDocumentPartLink link = (KETDocumentPartLink) r.nextElement();
		KETDocumentPartLink newLink = KETDocumentPartLink.newKETDocumentPartLink(newDoc, link.getPart());
		PersistenceHelper.manager.save(newLink);
	    }

	    // 관련 프로젝트
	    r = PersistenceHelper.manager.navigate(doc, "project", KETDocumentProjectLink.class, false);
	    while (r.hasMoreElements()) {
		KETDocumentProjectLink link = (KETDocumentProjectLink) r.nextElement();
		KETDocumentProjectLink newLink = KETDocumentProjectLink.newKETDocumentProjectLink(newDoc, link.getProject());
		PersistenceHelper.manager.save(newLink);
	    }

	    // 관련 산출물
	    r = PersistenceHelper.manager.navigate(doc, "output", KETDocumentOutputLink.class, false);
	    while (r.hasMoreElements()) {
		KETDocumentOutputLink link = (KETDocumentOutputLink) r.nextElement();
		KETDocumentOutputLink newLink = KETDocumentOutputLink.newKETDocumentOutputLink(newDoc, link.getOutput());
		PersistenceHelper.manager.save(newLink);
	    }

	    // 관련 문서분류
	    r = PersistenceHelper.manager.navigate(doc, "documentCategory", KETDocumentCategoryLink.class, false);
	    if (r.hasMoreElements()) {
		KETDocumentCategoryLink link = (KETDocumentCategoryLink) r.nextElement();
		KETDocumentCategoryLink newLink = KETDocumentCategoryLink.newKETDocumentCategoryLink(link.getDocumentCategory(), newDoc);
		PersistenceHelper.manager.save(newLink);
	    }

	    trx.commit();
	    trx = null;

	} catch (Exception e) {

	    if (trx != null) {
		trx.rollback();
	    }

	    e.printStackTrace();

	}

	return null;
    }

    @Override
    public void changeProjectRankModify(E3PSProject project, String rank) throws Exception {

	if (project == null)
	    return;
	List<KETProjectDocument> list = new ArrayList<KETProjectDocument>();
	// KETDocumentProjectLink
	QueryResult qr1 = PersistenceHelper.manager.navigate(project, KETDocumentProjectLink.DOCUMENT_ROLE, KETDocumentProjectLink.class, true);
	while (qr1.hasMoreElements()) {
	    KETProjectDocument document = (KETProjectDocument) qr1.nextElement();
	    if (!list.contains(document))
		list.add(document);
	}
	// ProjectOutput
	QueryResult qr2 = ProjectOutputHelper.manager.getProjectOutput(project);
	while (qr2.hasMoreElements()) {
	    Object[] objects = (Object[]) qr2.nextElement();
	    ProjectOutput output = (ProjectOutput) objects[0];
	    // KETDocumentOutputLink / OutputDocumentLink
	    QueryResult qr3 = PersistenceHelper.manager.navigate(output, KETDocumentOutputLink.DOCUMENT_ROLE, KETDocumentOutputLink.class, true);
	    while (qr3.hasMoreElements()) {
		KETProjectDocument document = (KETProjectDocument) qr3.nextElement();
		if (!list.contains(document))
		    list.add(document);
	    }
	}
	if (list != null && list.size() > 0) {
	    for (KETProjectDocument document : list) {
		if ("S".equalsIgnoreCase(rank)) {
		    document.setSecurity("S2"); // 대내비
		} else {
		    document.setSecurity("S1"); // 대외비
		}
		PersistenceServerHelper.manager.update(document);
	    }
	}
    }
}
