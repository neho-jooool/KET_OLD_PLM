package ext.ket.project.trycondition.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.BeanUtils;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.part.WTPartMaster;
import wt.pds.StatementSpec;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.folder.FolderUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.KETTryMoldOutputLink;
import e3ps.project.KETTryPressOutputLink;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProjectOutput;
import e3ps.project.material.MoldMaterial;
import ext.ket.common.util.ObjectUtil;
import ext.ket.project.trycondition.entity.KETTryCondition;
import ext.ket.project.trycondition.entity.KETTryConditionProjectLink;
import ext.ket.project.trycondition.entity.KETTryMold;
import ext.ket.project.trycondition.entity.KETTryPartLink;
import ext.ket.project.trycondition.entity.KETTryPress;
import ext.ket.project.trycondition.entity.TryConditionDTO;
import ext.ket.project.trycondition.entity.TryMoldDTO;
import ext.ket.project.trycondition.entity.TryPressDTO;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.SessionUtil;

public class StandardTryConditionService extends StandardManager implements TryConditionService {
    private static final long serialVersionUID = 1L;

    public static StandardTryConditionService newStandardTryConditionService() throws WTException {
	StandardTryConditionService instance = new StandardTryConditionService();
	instance.initialize();
	return instance;
    }

    @Override
    public KETTryCondition delete(TryConditionDTO paramObject) throws Exception {
	tryPartListDelete((KETTryCondition) CommonUtil.getObject(paramObject.getOid()));
	PersistenceHelper.manager.delete(CommonUtil.getObject(paramObject.getOid()));
	return null;
    }

    @Override
    public List<KETTryCondition> find(TryConditionDTO paramObject) throws Exception {
	QuerySpec query = getFindQuery(paramObject);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	List<KETTryCondition> list = new ArrayList<KETTryCondition>();
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    list.add((KETTryCondition) objArr[0]);
	}
	return list;
    }

    @Override
    public PageControl findPaging(TryConditionDTO paramObject) throws Exception {
	QuerySpec qs = getFindQuery(paramObject);

	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	if (StringUtil.isEmpty(pageSessionId)) {
	    pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), qs);
	} else {
	    pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), paramObject.getPage() + 1,
		    pageSessionId);
	}
	return pageControl;
    }

    @SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
    public String getCavityByProject(MoldProject project) throws Exception {
	String cavity = "";
	MoldItemInfo moldInfo = project.getMoldInfo();
	QuerySpec specMoldItem = new QuerySpec();
	int idx = specMoldItem.addClassList(MoldItemInfo.class, true);
	SearchCondition sc = new SearchCondition(MoldItemInfo.class, "projectReference.key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(moldInfo.getProject()));
	specMoldItem.appendWhere(sc, new int[] { idx });
	specMoldItem.appendAnd();
	specMoldItem.appendWhere(new SearchCondition(MoldItemInfo.class, "dieNo", SearchCondition.EQUAL, moldInfo.getDieNo()),
	        new int[] { idx });
	QueryResult rt = PersistenceHelper.manager.find(specMoldItem);
	MoldItemInfo mInfo = null;
	Object[] obj = null;
	Vector vt = new Vector();
	while (rt.hasMoreElements()) {
	    obj = (Object[]) rt.nextElement();
	    mInfo = (MoldItemInfo) obj[0];

	    if (mInfo.getCVPitch() != null) {
		vt.add(mInfo.getCVPitch());
	    }
	}
	for (int i = 0; i < vt.size(); i++) {
	    if (vt.size() == 1) {
		cavity = (String) vt.get(i);
	    } else {
		if ((vt.size() - 1) == i) {
		    cavity = cavity + (String) vt.get(i);
		} else {
		    cavity = cavity + (String) vt.get(i) + "+";
		}
	    }
	}
	return cavity;
    }

    /**
     * Try조건표 검색 쿼리
     * 
     * @param paramObject
     * @return
     * @throws QueryException
     * @메소드명 : getFindQuery
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getFindQuery(TryConditionDTO paramObject) throws Exception {
	QuerySpec qs = new QuerySpec();
	qs.setAdvancedQueryEnabled(true);
	int idx0 = qs.addClassList(KETTryCondition.class, true);
	int idx1 = qs.addClassList(MoldProject.class, false);
	int idx2 = qs.addClassList(MoldItemInfo.class, false);
	// int idx3 = qs.addClassList(ProductProject.class, false);

	qs.appendWhere(new SearchCondition(new ClassAttribute(KETTryCondition.class, "moldProjectReference.key.id"), SearchCondition.EQUAL,
	        new ClassAttribute(MoldProject.class, WTAttributeNameIfc.ID_NAME)), new int[] { idx0, idx1 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(KETTryCondition.class, KETTryMold.LATEST_ITERATION, SearchCondition.IS_TRUE), new int[] { idx0 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(new ClassAttribute(MoldProject.class, "moldInfoReference.key.id"), SearchCondition.EQUAL,
	        new ClassAttribute(MoldItemInfo.class, WTAttributeNameIfc.ID_NAME)), new int[] { idx1, idx2 });

	/*
	 * qs.appendAnd(); qs.appendWhere( new SearchCondition(new ClassAttribute(MoldItemInfo.class, "projectReference.key.id"),
	 * SearchCondition.EQUAL, new ClassAttribute(ProductProject.class, WTAttributeNameIfc.ID_NAME)), new int[] { idx2, idx3 });
	 */

	SearchCondition sc = null;
	if (!StringUtil.isTrimToEmpty(paramObject.getTryNo())) {
	    qs.appendAnd();
	    sc = new SearchCondition(KETTryCondition.class, KETTryCondition.TRY_NO, SearchCondition.LIKE, StringUtil.changeString(
		    paramObject.getTryNo(), "*", "%"), false);
	    qs.appendWhere(sc, new int[] { idx0 });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getDieNo())) {
	    qs.appendAnd();
	    sc = new SearchCondition(MoldItemInfo.class, MoldItemInfo.DIE_NO, SearchCondition.LIKE, StringUtil.changeString(
		    paramObject.getDieNo(), "*", "%"), false);
	    qs.appendWhere(sc, new int[] { idx2 });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getPartNo())) {
	    qs.appendAnd();
	    sc = new SearchCondition(MoldItemInfo.class, MoldItemInfo.PART_NO, SearchCondition.LIKE, StringUtil.changeString(
		    paramObject.getPartNo(), "*", "%"), false);
	    qs.appendWhere(sc, new int[] { idx2 });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getPartName())) {
	    qs.appendAnd();
	    sc = new SearchCondition(MoldItemInfo.class, MoldItemInfo.PART_NAME, SearchCondition.LIKE, StringUtil.changeString(
		    paramObject.getPartName(), "*", "%"), false);
	    qs.appendWhere(sc, new int[] { idx2 });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getMoldType())) {
	    qs.appendAnd();
	    sc = new SearchCondition(MoldItemInfo.class, MoldItemInfo.ITEM_TYPE, SearchCondition.EQUAL, paramObject.getMoldType(), false);
	    qs.appendWhere(sc, new int[] { idx2 });
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getDivision())) {
	    ClassAttribute targetExpression = new ClassAttribute(MoldProject.class, MoldProject.PJT_TYPE);
	    String arrSplit[] = paramObject.getDivision().split(",");
	    for (int i = 0; i < arrSplit.length; i++) {
		if ("1".equals(arrSplit[i])) {// 자동차사업부
		    arrSplit[i] = "3";
		} else if ("2".equals(arrSplit[i])) {// 전자사업부
		    arrSplit[i] = "4";
		} else if ("3".equals(arrSplit[i])) {// KETS
		    arrSplit[i] = "6";
		}
	    }
	    qs.appendAnd();
	    sc = new SearchCondition(targetExpression, SearchCondition.IN, new ArrayExpression(arrSplit));
	    qs.appendWhere(sc, new int[] { idx1 });
	}
	if (CommonUtil.isKETSUser()) {
	    qs.appendAnd();
	    sc = new SearchCondition(MoldProject.class, MoldProject.PJT_TYPE, SearchCondition.EQUAL, 6);
	    qs.appendWhere(sc, new int[] { idx1 });
	}

	// if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	// String sortName = paramObject.getSortName();
	// // '-'내림차순
	// if (sortName.startsWith("-")) {
	// SearchUtil.setOrderBy(qs, KETTryCondition.class, idx1, sortName.substring(1), true);
	// } else {
	// SearchUtil.setOrderBy(qs, KETTryCondition.class, idx1, sortName, false);
	// }
	// } else {
	// SearchUtil.setOrderBy(qs, KETTryCondition.class, idx0, "master>number", true);
	// }
	Kogger.debug(getClass(), qs);
	return qs;
    }

    @Override
    public int getMaxSubVer(TryConditionDTO paramObject) throws Exception {
	ProjectOutput projectOutput = (ProjectOutput) CommonUtil.getObject(paramObject.getProjectOutputOid());
	MoldProject moldProject = (MoldProject) projectOutput.getTask().getProject();

	QuerySpec querySpec = new QuerySpec();
	querySpec.setAdvancedQueryEnabled(true);
	int idx0 = 0;
	int idx1 = 0;
	int idx2 = 0;
	int idx3 = 0;
	SQLFunction max = null;
	if ("Press".equals(moldProject.getMoldInfo().getItemType())) {
	    idx0 = querySpec.addClassList(KETTryPress.class, false);
	    idx1 = querySpec.addClassList(KETTryPressOutputLink.class, false);
	    idx2 = querySpec.addClassList(ProjectOutput.class, false);
	    idx3 = querySpec.addClassList(E3PSTask.class, false);
	    max = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, new ClassAttribute(KETTryPress.class, KETTryPress.SUB_VER));
	    querySpec.appendSelect(max, false);
	    querySpec.appendWhere(new SearchCondition(new ClassAttribute(KETTryPress.class, WTAttributeNameIfc.ID_NAME),
		    SearchCondition.EQUAL, new ClassAttribute(KETTryPressOutputLink.class, "roleAObjectRef.key.id")), new int[] { idx0,
		    idx1 });
	    querySpec.appendAnd();
	    querySpec.appendWhere(new SearchCondition(new ClassAttribute(KETTryPressOutputLink.class, "roleBObjectRef.key.id"),
		    SearchCondition.EQUAL, new ClassAttribute(ProjectOutput.class, WTAttributeNameIfc.ID_NAME)), new int[] { idx1, idx2 });
	    querySpec.appendAnd();
	    querySpec.appendWhere(new SearchCondition(new ClassAttribute(ProjectOutput.class, "taskReference.key.id"),
		    SearchCondition.EQUAL, new ClassAttribute(E3PSTask.class, WTAttributeNameIfc.ID_NAME)), new int[] { idx2, idx3 });
	    querySpec.appendAnd();
	    querySpec.appendWhere(
		    new SearchCondition(KETTryPress.class, KETTryPress.TRY_NO, SearchCondition.EQUAL, paramObject.getTryNo()),
		    new int[] { idx0 });
	    querySpec.appendAnd();
	    querySpec.appendWhere(
		    new SearchCondition(E3PSTask.class, WTAttributeNameIfc.ID_NAME, SearchCondition.EQUAL, CommonUtil
		            .getOIDLongValue(projectOutput.getTask())), new int[] { idx3 });
	} else {
	    idx0 = querySpec.addClassList(KETTryMold.class, false);
	    idx1 = querySpec.addClassList(KETTryMoldOutputLink.class, false);
	    idx2 = querySpec.addClassList(ProjectOutput.class, false);
	    idx3 = querySpec.addClassList(E3PSTask.class, false);
	    max = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, new ClassAttribute(KETTryMold.class, KETTryMold.SUB_VER));
	    querySpec.appendSelect(max, false);
	    querySpec.appendWhere(new SearchCondition(new ClassAttribute(KETTryMold.class, WTAttributeNameIfc.ID_NAME),
		    SearchCondition.EQUAL, new ClassAttribute(KETTryMoldOutputLink.class, "roleAObjectRef.key.id")),
		    new int[] { idx0, idx1 });
	    querySpec.appendAnd();
	    querySpec.appendWhere(new SearchCondition(new ClassAttribute(KETTryMoldOutputLink.class, "roleBObjectRef.key.id"),
		    SearchCondition.EQUAL, new ClassAttribute(ProjectOutput.class, WTAttributeNameIfc.ID_NAME)), new int[] { idx1, idx2 });
	    querySpec.appendAnd();
	    querySpec.appendWhere(new SearchCondition(new ClassAttribute(ProjectOutput.class, "taskReference.key.id"),
		    SearchCondition.EQUAL, new ClassAttribute(E3PSTask.class, WTAttributeNameIfc.ID_NAME)), new int[] { idx2, idx3 });
	    querySpec.appendAnd();
	    querySpec.appendWhere(new SearchCondition(KETTryMold.class, KETTryMold.TRY_NO, SearchCondition.EQUAL, paramObject.getTryNo()),
		    new int[] { idx0 });
	    querySpec.appendAnd();
	    querySpec.appendWhere(
		    new SearchCondition(E3PSTask.class, WTAttributeNameIfc.ID_NAME, SearchCondition.EQUAL, CommonUtil
		            .getOIDLongValue(projectOutput.getTask())), new int[] { idx3 });
	}
	Kogger.debug(getClass(), querySpec);

	QueryResult queryResult = PersistenceServerHelper.manager.query(querySpec);
	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    if (objArr[0] != null) {
		return ((BigDecimal) objArr[0]).intValue() + 1;
	    }
	}
	return 0;
    }

    @SuppressWarnings("deprecation")
    public List<TryConditionDTO> getTryConditionByProject(String projectOid) throws Exception {
	
	MoldProject pjt = (MoldProject)CommonUtil.getObject(projectOid);
	String dieNo = pjt.getPjtNo();
	QuerySpec querySpec = new QuerySpec();
	querySpec.setAdvancedQueryEnabled(true);
	int idx = querySpec.addClassList(KETTryCondition.class, true);
	querySpec.appendWhere(new SearchCondition(KETTryCondition.class, KETTryCondition.LATEST_ITERATION, SearchCondition.IS_TRUE),
	        new int[] { idx });
	querySpec.appendAnd();
	querySpec.appendWhere(new SearchCondition(KETTryCondition.class, "dieNo", SearchCondition.EQUAL, dieNo));
//	querySpec.appendWhere(new SearchCondition(KETTryCondition.class, "moldProjectReference.key.id", SearchCondition.EQUAL, CommonUtil
//	        .getOIDLongValue(projectOid)));
	Kogger.debug(getClass(), querySpec);
	QueryResult queryResult = PersistenceServerHelper.manager.query(querySpec);

	List<TryConditionDTO> returnList = new ArrayList<TryConditionDTO>();
	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    if (objArr[0] != null) {
		returnList.add(new TryConditionDTO((KETTryCondition) objArr[0]));
	    }
	}
	return returnList;
    }

    @Override
    public KETTryCondition modify(TryConditionDTO paramObject) throws Exception {
	KETTryCondition tryCondition = null;
	// PBO 객체화
	String oid = paramObject.getOid();
	Persistable persistable = CommonUtil.getObject(oid);
	if (paramObject instanceof TryPressDTO) {
	    TryPressDTO tryPressDTO = (TryPressDTO) paramObject;
	    KETTryPress tryPress = (KETTryPress) persistable;
	    tryPress.setMoldProject((MoldProject) CommonUtil.getObject(tryPressDTO.getProjectOid()));
	    tryPress.setMaterial((MoldMaterial) CommonUtil.getObject(tryPressDTO.getMaterial()));
	    tryPress.setMoldStruc((NumberCode) CommonUtil.getObject(tryPressDTO.getMoldStruc()));
	    tryPress.setProductMethod((NumberCode) CommonUtil.getObject(tryPressDTO.getProductMethod()));
	    tryPress.setScrapProcess((NumberCode) CommonUtil.getObject(tryPressDTO.getScrapProcess()));
	    tryPress.setPunchingOil((NumberCode) CommonUtil.getObject(tryPressDTO.getPunchingOil()));
	    tryCondition = tryPress;
	} else {
	    TryMoldDTO tryMoldDTO = (TryMoldDTO) paramObject;
	    KETTryMold tryMold = (KETTryMold) persistable;
	    tryMold.setMoldProject((MoldProject) CommonUtil.getObject(tryMoldDTO.getProjectOid()));
	    tryMold.setMaterial((MoldMaterial) CommonUtil.getObject(tryMoldDTO.getMaterial()));
	    tryMold.setMoldStruc((NumberCode) CommonUtil.getObject(tryMoldDTO.getMoldStruc()));
	    tryMold.setGateType((NumberCode) CommonUtil.getObject(tryMoldDTO.getGateType()));
	    tryMold.setMountThickness((NumberCode) CommonUtil.getObject(tryMoldDTO.getMountThickness()));
	    tryMold.setTemperatureSensor((NumberCode) CommonUtil.getObject(tryMoldDTO.getTemperatureSensor()));
	    tryMold.setInjectPressUnit((NumberCode) CommonUtil.getObject(tryMoldDTO.getInjectPressUnit()));
	    tryMold.setPackingPressUnit((NumberCode) CommonUtil.getObject(tryMoldDTO.getPackingPressUnit()));
	    tryMold.setLowPressShapeUnit((NumberCode) CommonUtil.getObject(tryMoldDTO.getLowPressShapeUnit()));
	    tryMold.setBackPressUnit((NumberCode) CommonUtil.getObject(tryMoldDTO.getBackPressUnit()));
	    tryCondition = tryMold;
	}
	if (StringUtil.checkString(paramObject.getTryDate())) {
	    tryCondition.setTryDate(DateUtil.convertStartDate2(paramObject.getTryDate()));
	}
	// 같은 이름을 갖은 properties 복사 type이 틀리면 예외를 array로 등록한다.
	BeanUtils.copyProperties(paramObject, tryCondition, new String[] { "tryDate", "material", "state", "creator", "material",
	        "moldStruc", "productMethod", "scrapProcess", "punchingOil", "gateType", "mountThickness", "temperatureSensor",
	        "injectPressUnit", "packingPressUnit", "lowPressShapeUnit", "backPressUnit" });
	// 이터레이션 관리 X 체크인/아웃 없이 수정
	PersistenceServerHelper.manager.update(tryCondition);

	tryCondition = (KETTryCondition) PersistenceHelper.manager.refresh(tryCondition);

	if (paramObject instanceof TryPressDTO) {// 관련 부품 저장

	    tryRelatedPartSave((TryPressDTO) paramObject, tryCondition);
	}
	// 첨부파일
	KETContentHelper.service.updateContent(tryCondition, paramObject);
	return tryCondition;
    }

    @Override
    public KETTryCondition save(TryConditionDTO paramObject) throws Exception {
	KETTryCondition tryCondition = null;
	// PBO 객체화
	String oid = paramObject.getOid();
	Persistable persistable = CommonUtil.getObject(oid);

	if (persistable == null) {
	    if (paramObject instanceof TryPressDTO) {
		TryPressDTO tryPressDTO = (TryPressDTO) paramObject;

		KETTryPress tryPress = KETTryPress.newKETTryPress();
		tryPress.setMaterial((MoldMaterial) CommonUtil.getObject(tryPressDTO.getMaterial()));
		tryPress.setMoldStruc((NumberCode) CommonUtil.getObject(tryPressDTO.getMoldStruc()));
		tryPress.setProductMethod((NumberCode) CommonUtil.getObject(tryPressDTO.getProductMethod()));
		tryPress.setScrapProcess((NumberCode) CommonUtil.getObject(tryPressDTO.getScrapProcess()));
		tryPress.setPunchingOil((NumberCode) CommonUtil.getObject(tryPressDTO.getPunchingOil()));
		tryCondition = tryPress;
	    } else {
		TryMoldDTO tryMoldDTO = (TryMoldDTO) paramObject;
		KETTryMold tryMold = KETTryMold.newKETTryMold();
		tryMold.setMaterial((MoldMaterial) CommonUtil.getObject(tryMoldDTO.getMaterial()));
		tryMold.setMoldStruc((NumberCode) CommonUtil.getObject(tryMoldDTO.getMoldStruc()));
		tryMold.setGateType((NumberCode) CommonUtil.getObject(tryMoldDTO.getGateType()));
		tryMold.setMountThickness((NumberCode) CommonUtil.getObject(tryMoldDTO.getMountThickness()));
		tryMold.setTemperatureSensor((NumberCode) CommonUtil.getObject(tryMoldDTO.getTemperatureSensor()));
		tryMold.setInjectPressUnit((NumberCode) CommonUtil.getObject(tryMoldDTO.getInjectPressUnit()));
		tryMold.setPackingPressUnit((NumberCode) CommonUtil.getObject(tryMoldDTO.getPackingPressUnit()));
		tryMold.setLowPressShapeUnit((NumberCode) CommonUtil.getObject(tryMoldDTO.getLowPressShapeUnit()));
		tryMold.setBackPressUnit((NumberCode) CommonUtil.getObject(tryMoldDTO.getBackPressUnit()));
		tryCondition = tryMold;
	    }
	}
	// 등록인 경우에만 이름을 등록 한다.
	tryCondition.setName("[" + paramObject.getDieNo() + "]Try No [" + paramObject.getTryNo() + "] Sub ver[" + paramObject.getSubVer()
	        + "] 금형 Try 조건표");
	tryCondition.setMoldProject((MoldProject) CommonUtil.getObject(paramObject.getProjectOid()));
	if (StringUtil.checkString(paramObject.getTryDate())) {
	    tryCondition.setTryDate(DateUtil.convertStartDate2(paramObject.getTryDate()));
	}
	// 같은 이름을 갖은 properties 복사 type이 틀리면 예외를 array로 등록한다.
	BeanUtils.copyProperties(paramObject, tryCondition, new String[] { "tryDate", "material", "state", "creator", "material",
	        "moldStruc", "productMethod", "scrapProcess", "punchingOil", "gateType", "mountThickness", "temperatureSensor",
	        "injectPressUnit", "packingPressUnit", "lowPressShapeUnit", "backPressUnit" });
	FolderHelper.assignLocation((FolderEntry) tryCondition,
	        FolderUtil.getFolder("/Default/" + (CommonUtil.isMember("전자사업부") ? "전자사업부" : "자동차사업부") + "/문서/개발산출문서/개발/금형Try"));
	tryCondition = (KETTryCondition) PersistenceHelper.manager.save(tryCondition);

	// 산출물 연결
	if (persistable == null) {
	    ProjectOutput projectOutput = (ProjectOutput) CommonUtil.getObject(paramObject.getProjectOutputOid());
	    if (paramObject instanceof TryPressDTO) {
		KETTryPressOutputLink outputLink = KETTryPressOutputLink
		        .newKETTryPressOutputLink((KETTryPress) tryCondition, projectOutput);
		PersistenceHelper.manager.save(outputLink);
	    } else {
		KETTryMoldOutputLink outputLink = KETTryMoldOutputLink.newKETTryMoldOutputLink((KETTryMold) tryCondition, projectOutput);
		PersistenceHelper.manager.save(outputLink);
	    }
	}

	if (paramObject instanceof TryPressDTO) {// 관련 부품 저장

	    tryRelatedPartSave((TryPressDTO) paramObject, tryCondition);
	}

	// 첨부파일
	KETContentHelper.service.updateContent(tryCondition, paramObject);
	return tryCondition;
    }

    public void changeMoldRankByProject(E3PSProject project) throws Exception {
	if (project != null && project.getRank() != null && project.getRank().getName().equals("S")) {
	    QueryResult qr = PersistenceHelper.manager.navigate(project, KETTryConditionProjectLink.KETTRY_CONDITION_ROLE,
		    KETTryConditionProjectLink.class, true);
	    while (qr.hasMoreElements()) {
		KETTryCondition tryCondition = (KETTryCondition) qr.nextElement();
		tryCondition.setMoldRank("S");
		PersistenceServerHelper.manager.update(tryCondition);
	    }
	}
    }

    public void tryRelatedPartSave(TryPressDTO dto, KETTryCondition tryCondition) throws Exception {
	String jsonDataStr = "";
	ObjectMapper mapper = new ObjectMapper();
	jsonDataStr = (String) dto.getJsonData();
	List<Map<String, Object>> jsonData = mapper.readValue(jsonDataStr, new TypeReference<List<Map<String, Object>>>() {
	});

	if (jsonData != null && tryCondition != null) {

	    tryPartListDelete(tryCondition);

	    for (Map<String, Object> data : jsonData) {

		String partOid = (String) data.get("partOid");
		WTPartMaster part = (WTPartMaster) CommonUtil.getObject(partOid);

		String totalWeight = StringUtils.remove((String) data.get("totalWeight"), ",");
		String scrabWeight = StringUtils.remove((String) data.get("scrabWeight"), ",");
		String scrabCarrierWeight = StringUtils.remove((String) data.get("scrabCarrierWeight"), ",");
		String netWeight = StringUtils.remove((String) data.get("netWeight"), ",");

		KETTryPartLink link = KETTryPartLink.newKETTryPartLink(tryCondition, part);
		link.setTotalWeight(totalWeight);
		link.setScrabWeight(scrabWeight);
		link.setScrabCarrierWeight(scrabCarrierWeight);
		link.setNetWeight(netWeight);
		link.setPartMaster(part);
		link.setPartName(part.getName());
		link.setPartNo(part.getNumber());

		PersistenceHelper.manager.save(link);
	    }

	}
    }

    public void tryPartListDelete(KETTryCondition tryCondition) throws Exception {
	QueryResult qr = PersistenceHelper.manager.navigate(tryCondition, KETTryPartLink.PART_MASTER_ROLE, KETTryPartLink.class, false);
	while (qr.hasMoreElements()) {
	    KETTryPartLink partLink = (KETTryPartLink) qr.nextElement();
	    PersistenceHelper.manager.delete(partLink);
	}
    }

    @Override
    public List<Map<String, Object>> getTryPartList(String oid) throws Exception {

	KETTryCondition tryCondition = (KETTryCondition) CommonUtil.getObject(oid);

	List<Map<String, Object>> partList = new ArrayList<Map<String, Object>>();

	QueryResult qr = PersistenceHelper.manager.navigate(tryCondition, KETTryPartLink.PART_MASTER_ROLE, KETTryPartLink.class, false);
	while (qr.hasMoreElements()) {

	    KETTryPartLink partLink = (KETTryPartLink) qr.nextElement();
	    Map<String, Object> map = ObjectUtil.manager.converObjectToMap(partLink);
	    map.put("partOid", CommonUtil.getOIDString(partLink.getPartMaster()));
	    partList.add(map);
	}

	return partList;
    }
}
