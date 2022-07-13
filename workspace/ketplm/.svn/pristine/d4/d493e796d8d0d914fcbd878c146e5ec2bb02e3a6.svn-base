package ext.ket.part.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.org.WTPrincipal;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pds.StatementSpec;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.IterationIdentifier;
import wt.vc.VersionControlHelper;
import wt.vc.VersionIdentifier;
import wt.vc.Versioned;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.VersionUtil;
import e3ps.cost.util.StringUtil;
import e3ps.edm.EPMDocProjectLink;
import e3ps.edm.EPMLink;
import e3ps.edm.PartToEPMLink;
import e3ps.edm.beans.EDMHelper;
import e3ps.project.E3PSProject;
import e3ps.project.ProjectMaster;
import e3ps.project.beans.ProjectHelper;
import ext.ket.part.base.service.internal.PartBaseDao;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.code.NumberCodeUtil;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.KETPartProjectLink;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryBStrategy;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class PartUtil {

    public static String SEPERATER = "↕";

    private static PartSpecEnum[] partSpecEnumExcepts = new PartSpecEnum[] { PartSpecEnum.SpProjectNo, PartSpecEnum.SpEoNo,
	    PartSpecEnum.SpPartNameHis, PartSpecEnum.SpWeightUnit, PartSpecEnum.SpPartRevision, PartSpecEnum.SpPartType,
	    PartSpecEnum.SpPartDevLevel, PartSpecEnum.SpIsDeleted, PartSpecEnum.SpBOMFlag };

    private static Map<PartSpecEnum, PartSpecEnum> partSpecEnumExceptMap = new HashMap<PartSpecEnum, PartSpecEnum>();

    private static PartSpecEnum[] partSpecEnumList;

    static {

	// 로딩시에 부품 검색에 사용할 enum 정의
	for (PartSpecEnum partSpecEnumExcept : partSpecEnumExcepts) {
	    partSpecEnumExceptMap.put(partSpecEnumExcept, null);
	}

	PartSpecEnum[] partSpecEnums = PartSpecEnum.values();
	partSpecEnumList = new PartSpecEnum[partSpecEnums.length - partSpecEnumExcepts.length];

	int idx = 0;
	for (int k = 0; k < partSpecEnums.length; k++) {

	    PartSpecEnum partSpecEnumItem = partSpecEnums[k];

	    if (partSpecEnumExceptMap.containsKey(partSpecEnumItem)) {
		continue;
	    }

	    partSpecEnumList[idx] = partSpecEnumItem;
	    idx++;
	}
    }

    public static PartSpecEnum[] getPartSpecForSearch() {
	return partSpecEnumList;
    }

    // 비금형제품인지 판단 / 금형은[ 금형 SET - D, 금형부품 - M]으로 구성됨
    public static boolean isProductType(WTPart part) {
	return isProductType(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType));
    }

    // 비금형제품인지 판단 / 금형은[ 금형 SET - D, 금형부품 - M]으로 구성됨
    public static boolean isProductType(String partType) {
	if (partType == null)
	    return false;

	// 예전에는 모두 P로 사용했지만
	// 원자재(R), 반제품(H), 제품(F), 포장재(K), 스크랩(S), 상품(W)
	if ("R".equalsIgnoreCase(partType) || "H".equalsIgnoreCase(partType) || "F".equalsIgnoreCase(partType)
	        || "K".equalsIgnoreCase(partType) || "S".equalsIgnoreCase(partType) || "W".equalsIgnoreCase(partType)
	        || "O".equalsIgnoreCase(partType)
	        // 과거에 사용하던 제품코드를 일단 살려 둠.
	        || "P".equalsIgnoreCase(partType)) {
	    return true;
	} else {
	    return false;
	}
    }

    // 개발단계
    public static String getSpDevLevelText(String code) {

	String ret = "";
	if (code == null)
	    return ret;

	if ("PC003".equals(code) || "PC001".equals(code) || "PC002".equals(code)) {
	    return "개발";
	} else if ("PC004".equals(code)) {
	    return "양산";
	}

	return ret;
    }

    // 부품과 연결된 프로젝트 가져오기
    public static QueryResult getPartProjectLink(WTPart wtPart, ProjectMaster pjtMaster) throws WTException {

	QuerySpec qs = new QuerySpec();
	int idx_link = qs.appendClassList(KETPartProjectLink.class, true);

	if (wtPart != null) {
	    qs.appendWhere(new SearchCondition(KETPartProjectLink.class, "roleBObjectRef.key.branchId", SearchCondition.EQUAL,
		    VersionControlHelper.getBranchIdentifier(wtPart)), new int[] { idx_link });
	}

	if (pjtMaster != null) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(new SearchCondition(EPMDocProjectLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, PersistenceHelper
		    .getObjectIdentifier(pjtMaster).getId()), new int[] { idx_link });
	}

	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

	return qr;
    }

    public static String getPartProjectNo(WTPart part) throws WTException {

	KETPartProjectLink currentProjectLink = null;

	QueryResult qr = getPartProjectLink(part, null);

	while (qr.hasMoreElements()) {
	    Object[] objs = (Object[]) qr.nextElement();
	    currentProjectLink = (KETPartProjectLink) objs[0];
	}

	return currentProjectLink == null ? null : currentProjectLink.getProject().getPjtNo();
    }

    // VersionUtil의 method를 수정함.
    public static Versioned doRevise(Versioned versioned, String version, String lifecycle, String location, String state, String team,
	    String note, String developLevel) throws WTException {

	Versioned newVer = null;
	try {

	    if ((version != null) && (version.trim().length() > 0)) {
		VersionIdentifier newVersionId = VersionUtil.getVersionIdentifier(versioned, version.trim());
		IterationIdentifier newIterationId = VersionControlHelper.firstIterationId(versioned);
		newVer = (Versioned) VersionControlHelper.service.newVersion(versioned, newVersionId, newIterationId);
	    } else {
		newVer = (Versioned) VersionControlHelper.service.newVersion(versioned);
	    }

	    if ((note != null) && (note.trim().length() > 0)) {
		VersionControlHelper.setNote(newVer, note);
	    }

	    if (isProductType((WTPart) versioned)) {
		// 금형도면, 금형SET, 금형부품 제외 - 개발단계 및 리비전
		// 개발 단계 변경

		// 제품 ECO 설계변경 상세사유
		/*
	         * CDR_111^REASON_10 Proto이관 CDR_112^REASON_10 Pilot이관 CDR_113^REASON_10 양산이관
	         */
		if (StringUtils.isNotEmpty(developLevel)) {
		    if ("CDR_113^REASON_10".equals(developLevel)) {
			PartSpecSetter.setPartSpec(((WTPart) newVer), PartSpecEnum.SpPartDevLevel, "PC004");
		    } else if ("CDR_111^REASON_10".equals(developLevel)) {
			PartSpecSetter.setPartSpec(((WTPart) newVer), PartSpecEnum.SpPartDevLevel, "PC003");
		    } else if ("CDR_112^REASON_10".equals(developLevel)) {
			PartSpecSetter.setPartSpec(((WTPart) newVer), PartSpecEnum.SpPartDevLevel, "PC003");
		    }
		}

		// 리비전변경 : 단계[ ECO의 개발단계에 따라 리비전및 개발단계 변경] : PRODUCTION PROTO PILOT TCAR
		String oldRevision = PartSpecGetter.getPartSpec((WTPart) versioned, PartSpecEnum.SpPartRevision);

		// PC001:T-CAR; PC002:Pilot; PC003:Proto; PC004:양산
		if (oldRevision != null && oldRevision.startsWith("D")) {

		    // 리비전 변경
		    if (StringUtils.isNotEmpty(developLevel) && ("CDR_113^REASON_10".equals(developLevel))) {
			PartSpecSetter.setPartSpec((WTPart) newVer, PartSpecEnum.SpPartRevision, "0");
		    } else {
			String revSuffixCode = String.valueOf(Integer.parseInt(oldRevision.substring(1)) + 1);
			revSuffixCode = NumberCodeUtil.getSerailNumberFormat(revSuffixCode, 2);
			PartSpecSetter.setPartSpec((WTPart) newVer, PartSpecEnum.SpPartRevision, "D" + revSuffixCode);
		    }

		} else {
		    if (StringUtils.isEmpty(oldRevision)) {
			PartSpecSetter.setPartSpec((WTPart) newVer, PartSpecEnum.SpPartRevision, "D01");

		    } else {
			PartSpecSetter.setPartSpec((WTPart) newVer, PartSpecEnum.SpPartRevision,
			        String.valueOf(Integer.parseInt(oldRevision) + 1));
		    }
		}
	    } else {
		// 금형 개발 단계 변경 // ECO에 양산이관이 없음.
		// 리비전 1 증가
		String oldRevision = PartSpecGetter.getPartSpec((WTPart) versioned, PartSpecEnum.SpPartRevision);
		if (StringUtils.isEmpty(oldRevision)) {
		    PartSpecSetter.setPartSpec((WTPart) newVer, PartSpecEnum.SpPartRevision, "1");
		} else {
		    PartSpecSetter.setPartSpec((WTPart) newVer, PartSpecEnum.SpPartRevision,
			    String.valueOf(Integer.parseInt(oldRevision) + 1));
		}
	    }

	    VersionUtil.updateRevised(versioned, newVer, location, lifecycle, team);

	    newVer = (Versioned) PersistenceHelper.manager.save(newVer);

	    if ((newVer instanceof LifeCycleManaged) && (state != null) && (state.trim().length() > 0)) {
		LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) newVer, State.toState(state.trim()), false);
	    }

	} catch (WTPropertyVetoException e) {
	    Kogger.error(PartSpecEnum.class, e);
	    throw new WTException(e);
	} catch (WTException e) {
	    throw new WTException(e);
	} catch (Exception e) {
	    throw new WTException(e);
	}

	return newVer;
    }

    // VersionUtil의 method를 수정함.
    public static Versioned doReviseEpm(Versioned versioned, String version, String lifecycle, String location, String state, String team,
	    String note, String developLevel) throws WTException {

	Versioned newVer = null;

	try {

	    if ((version != null) && (version.trim().length() > 0)) {

		VersionIdentifier newVersionId = VersionUtil.getVersionIdentifier(versioned, version.trim());
		IterationIdentifier newIterationId = VersionControlHelper.firstIterationId(versioned);
		newVer = (Versioned) VersionControlHelper.service.newVersion(versioned, newVersionId, newIterationId);

	    } else {

		newVer = (Versioned) VersionControlHelper.service.newVersion(versioned);

	    }

	    if ((note != null) && (note.trim().length() > 0)) {
		VersionControlHelper.setNote(newVer, note);
	    }

	    if (true) { // 일반 제품의 도면
		// 개발 단계 변경 - ECO에서 넘어온 양산 이관 FLAG 사용
		// 제품 ECO 설계변경 상세사유
		/*
	         * CDR_111^REASON_10 Proto이관 CDR_112^REASON_10 Pilot이관 CDR_113^REASON_10 양산이관
	         */
		if (StringUtils.isNotEmpty(developLevel)) {
		    if ("CDR_113^REASON_10".equals(developLevel)) {
			e3ps.common.iba.IBAUtil.changeIBAValue((EPMDocument) versioned, EDMHelper.IBA_DEV_STAGE, "양산단계");
		    }
		}
	    }

	    VersionUtil.updateRevised(versioned, newVer, location, lifecycle, team);

	    newVer = (Versioned) PersistenceHelper.manager.save(newVer);

	    if ((newVer instanceof LifeCycleManaged) && (state != null) && (state.trim().length() > 0)) {
		SessionContext sessioncontext = SessionContext.newContext();
		try {
		    SessionHelper.manager.setAdministrator();
		    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) newVer, State.toState(state.trim()), false);
		    // Input your code.
		} catch (Exception e) {
		    Kogger.error(PartSpecEnum.class, e);
		} finally {
		    SessionContext.setContext(sessioncontext);
		}

	    }

	} catch (WTPropertyVetoException e) {
	    Kogger.error(PartSpecEnum.class, e);
	    throw new WTException(e);
	} catch (WTException e) {
	    throw new WTException(e);
	} catch (Exception e) {
	    throw new WTException(e);
	}

	return newVer;
    }

    // 부품 등록 수정등에 연결할 ProjectMaster를 project No로 가져옴
    public static ProjectMaster getProjectMasterByProjectNo(String projectNo) throws Exception {

	ProjectMaster project = null;
	QuerySpec spec = new QuerySpec(ProjectMaster.class);

	spec.appendWhere(new SearchCondition(ProjectMaster.class, ProjectMaster.PJT_NO, "=", projectNo), new int[] { 0 });
	// spec.appendAnd();
	// spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")), new
	// int[] { 0 });
	spec.setDistinct(true);

	QueryResult qr = PersistenceServerHelper.manager.query(spec);
	if (qr != null) {
	    while (qr.hasMoreElements()) {
		Object objArr = (Object) qr.nextElement();
		project = (ProjectMaster) objArr;
	    }
	}
	return project;
    }

    // 부품 수정화면 등에서 workingCopy 가져오기
    public static WTPart getWorkingCopy(WTPart iPart) throws WTException {

	if (iPart == null)
	    return null;

	wt.vc.wip.Workable workable = (wt.vc.wip.Workable) iPart;
	wt.vc.wip.CheckoutLink checkOutLink = null;
	wt.vc.wip.Workable workingCopy = null;
	wt.vc.wip.Workable orgCopy = null;

	if (!wt.vc.wip.WorkInProgressHelper.isWorkingCopy(workable)) {
	    if (!wt.vc.wip.WorkInProgressHelper.isCheckedOut(workable)) {
		wt.folder.Folder folder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();

		try {
		    checkOutLink = (wt.vc.wip.CheckoutLink) wt.vc.wip.WorkInProgressHelper.service.checkout(workable, folder, "");
		} catch (wt.util.WTPropertyVetoException wtpve) {
		    throw new WTException(wtpve);
		}

		orgCopy = checkOutLink.getOriginalCopy();
		workingCopy = checkOutLink.getWorkingCopy();

	    } else if (wt.vc.wip.WorkInProgressHelper.isCheckedOut(workable)) {
		orgCopy = wt.vc.wip.WorkInProgressHelper.service.originalCopyOf(workable);
		workingCopy = wt.vc.wip.WorkInProgressHelper.service.workingCopyOf(workable);
	    }

	} else if (wt.vc.wip.WorkInProgressHelper.isWorkingCopy(workable)) {
	    workingCopy = workable;
	}

	return (WTPart) workingCopy;
    }

    // 분류체계 가져오기
    public static KETPartClassification getPartClassification(WTPart wtPart) throws Exception {

	KETPartClassification claz = PartClazHelper.service.getPartClassification(wtPart);
	return claz;
    }

    // 분류체계명 가져오기
    public static String getPartClassificationKrName(WTPart wtPart) throws Exception {

	KETPartClassification claz = getPartClassification(wtPart);
	return claz == null ? "" : StringUtil.checkNull(claz.getClassKrName());
    }

    // 프로젝트 정보 가져오기
    public static E3PSProject getE3PSProject(WTPart wtPart) throws WTException {

	E3PSProject project = null;
	QueryResult partQr = getPartProjectLink(wtPart, null);
	while (partQr.hasMoreElements()) {

	    Object[] objs = (Object[]) partQr.nextElement();
	    KETPartProjectLink bProjectLink = (KETPartProjectLink) objs[0];
	    ProjectMaster partPjtMast = bProjectLink.getProject();
	    project = ProjectHelper.getLastProject(partPjtMast);
	    break;
	}

	return project;
    }

    // 부품 유형 가져오기
    public static String getPartType(WTPart wtPart) throws Exception {

	if (wtPart == null)
	    return "";

	String numberCodeValue = CodeHelper.manager.getCodeValue(PartSpecEnum.SpPartType.getAttrCodeType(),
	        PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType));
	return StringUtil.checkNull(numberCodeValue);
    }

    // 부품 KET 리비전 가져오기
    public static String getKetPartRevision(WTPart wtPart) throws Exception {

	if (wtPart == null)
	    return "";

	String spPartRevision = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartRevision);

	return StringUtil.checkNull(spPartRevision);
    }

    // 부품 ECO 정보
    public static String getEcoNo(WTPart wtPart) throws Exception {
	String ecoInfo = "";

	if (wtPart == null)
	    return ecoInfo;

	PartBaseDao partBaseDao = new PartBaseDao();
	long branchId = VersionControlHelper.getBranchIdentifier(wtPart);
	ecoInfo = partBaseDao.searchEONo(branchId);

	return StringUtil.checkNull(ecoInfo);
    }

    // 제품도 정보
    public static List<EPMDocument> getReleateEpmDocumnetList(WTPart wtPart) throws Exception {

	List<EPMDocument> returnEpmDoc = new ArrayList<EPMDocument>();

	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<EPMLink> epmLinkList = query.queryForListLinkByRoleB(EPMLink.class, WTPartMaster.class, (WTPartMaster) wtPart.getMaster(),
	        new QueryBStrategy() {

		    @Override
		    public void handleQuery(QuerySpec query, Class classLink, int indexLink, Class classB, int indexB) throws Exception {

		        if (query.getConditionCount() > 0)
			    query.appendAnd();

		        query.appendWhere(new SearchCondition(new ClassAttribute(classLink, EPMLink.REFERENCE_TYPE), SearchCondition.IN,
		                new ArrayExpression(new String[] { "RELATED_DRAWING", "RELATED_MODEL" })), new int[] { indexLink });

		        if (query.getConditionCount() > 0)
			    query.appendAnd();

		        query.appendWhere(new SearchCondition(classLink, EPMLink.REQUIRED, SearchCondition.EQUAL, 1),
		                new int[] { indexLink });

		        // if (query.getConditionCount() > 0)
		        // query.appendAnd();
		        //
		        // query.appendWhere(new SearchCondition(classLink, EPMLink.ECAD, SearchCondition.EQUAL, false), new int[] {
		        // indexLink });

		    }
	        });

	List<EPMDocument> relatedDrawing = new ArrayList<EPMDocument>();
	if (epmLinkList != null && epmLinkList.size() > 0) {

	    for (EPMLink link : epmLinkList) {

		EPMDocumentMaster epmMast = link.getEpmMaster();
		EPMDocument latestEpmDoc = (EPMDocument) ObjectUtil.getLatestObject(epmMast);
		relatedDrawing.add(latestEpmDoc);
	    }
	}

	QueryResult queryResult = getEPMDocAllBypartVer(wtPart);
	Map<String, PartToEPMLink> verLinkMap = new HashMap<String, PartToEPMLink>();
	Map<String, EPMDocument> verEpmMap = new HashMap<String, EPMDocument>();
	while (queryResult.hasMoreElements()) {
	    Object[] objs = (Object[]) queryResult.nextElement();
	    PartToEPMLink link = (PartToEPMLink) objs[1];
	    EPMDocument verEpmDoc = link.getEpm();
	    EPMDocument latestEpmDoc = (EPMDocument) VersionControlHelper.getLatestIteration(verEpmDoc, false);
	    EPMDocumentMaster epmMast = (EPMDocumentMaster) verEpmDoc.getMaster();
	    if (verLinkMap.containsKey(epmMast.getNumber())) {
		EPMDocument mapEpmDoc = verEpmMap.get(epmMast.getNumber());
		if (mapEpmDoc.getVersionIdentifier().getSeries().greaterThan(latestEpmDoc.getVersionIdentifier().getSeries())) {

		} else {
		    verLinkMap.put(epmMast.getNumber(), link);
		    verEpmMap.put(epmMast.getNumber(), latestEpmDoc);
		}

	    } else {
		verLinkMap.put(epmMast.getNumber(), link);
		verEpmMap.put(epmMast.getNumber(), latestEpmDoc);
	    }
	}

	List<EPMDocument> relatedDrawing_V = new ArrayList<EPMDocument>();
	Iterator it = verLinkMap.keySet().iterator();
	while (it.hasNext()) {
	    String epmDocNumber = (String) it.next();
	    PartToEPMLink link = verLinkMap.get(epmDocNumber);
	    EPMDocument latestEpmDoc = verEpmMap.get(epmDocNumber);
	    relatedDrawing_V.add(latestEpmDoc);
	}

	// -. 부품 마스터에만 있는 도면을 추가한다.
	checkMastRev(relatedDrawing, relatedDrawing_V);

	// 최종으로 add
	addMastRev(returnEpmDoc, relatedDrawing, relatedDrawing_V);

	return returnEpmDoc;
    }

    private static void checkMastRev(List<EPMDocument> mast, List<EPMDocument> ver) {
	for (EPMDocument epmDoc_V : ver) {
	    String epmNo_V = epmDoc_V.getNumber();
	    for (EPMDocument epmDoc : mast) {
		String epmNo = epmDoc.getNumber();
		if (epmNo.equals(epmNo_V)) {
		    mast.remove(epmDoc);
		    break;
		}
	    }
	}
    }

    private static void addMastRev(List<EPMDocument> ret, List<EPMDocument> mast, List<EPMDocument> ver) {
	ret.addAll(ver);
	ret.addAll(mast);
    }

    private static QueryResult getEPMDocAllBypartVer(WTPart objectRoleB) throws Exception {

	WTPrincipal orgPrincipal = SessionHelper.manager.getPrincipal();
	SessionHelper.manager.setAdministrator();

	QueryResult queryResult = null;

	try {

	    QuerySpec query = new QuerySpec();
	    query.setAdvancedQueryEnabled(true);

	    int indexA = query.appendClassList(EPMDocument.class, true);
	    int indexLink = query.appendClassList(PartToEPMLink.class, true);
	    int indexB = query.appendClassList(WTPart.class, true);

	    query.appendJoin(indexLink, "roleAObject", indexA);
	    query.appendJoin(indexLink, "roleBObject", indexB);

	    if (objectRoleB == null) {
		throw new Exception("Query Instance all null Exception");
	    }

	    // if (objectRoleA != null)
	    if (query.getConditionCount() > 0)
		query.appendAnd();

	    query.appendWhere(new SearchCondition(PartToEPMLink.class, "roleBObjectRef.key.branchId", SearchCondition.EQUAL,
		    VersionControlHelper.getBranchIdentifier(objectRoleB)), new int[] { indexLink });

	    if (query.getConditionCount() > 0)
		query.appendAnd();

	    // query.appendWhere(new SearchCondition(PartToEPMLink.class, PartToEPMLink.REFERENCE_TYPE, SearchCondition.EQUAL,
	    // "RELATED_DRAWING"), new int[] { indexLink });
	    query.appendWhere(new SearchCondition(new ClassAttribute(PartToEPMLink.class, PartToEPMLink.REFERENCE_TYPE),
		    SearchCondition.IN, new ArrayExpression(new String[] { "RELATED_DRAWING", "RELATED_MODEL" })), new int[] { indexLink });

	    if (query.getConditionCount() > 0)
		query.appendAnd();

	    query.appendWhere(new SearchCondition(PartToEPMLink.class, PartToEPMLink.REQUIRED, SearchCondition.EQUAL, 1),
		    new int[] { indexLink });

	    Kogger.debug(PartSpecEnum.class, "## REQUEST Query:" + query.toString());

	    queryResult = PersistenceHelper.manager.find(query);

	} catch (Exception e) {
	    Kogger.debug(PartSpecEnum.class, ExceptionUtils.getStackTrace(e));
	    throw e;
	} finally {
	    SessionHelper.manager.setPrincipal(orgPrincipal.getName());
	}

	return queryResult;
    }
}
