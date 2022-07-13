package ext.ket.project.program.service;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wt.doc.DocumentType;
import wt.enterprise.RevisionControlled;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleException;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.org.WTUser;
import wt.pds.StatementSpec;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ColumnListExpression;
import wt.query.ConstantExpression;
import wt.query.KeywordExpression;
import wt.query.OrderBy;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.query.TableColumn;
import wt.services.StandardManager;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.VersionControlHelper;
import wt.vc.Versioned;
import e3ps.common.code.NumberCode;
import e3ps.common.folder.FolderUtil;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.ManageSequence;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.project.ExtendScheduleData;
import e3ps.project.ProductProject;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.project.outputtype.ModelPlan;
import e3ps.project.outputtype.OEMPlan;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.project.program.entity.KETProgramEvent;
import ext.ket.project.program.entity.KETProgramEventLink;
import ext.ket.project.program.entity.KETProgramProjectLink;
import ext.ket.project.program.entity.KETProgramSchedule;
import ext.ket.project.program.entity.ProgramDTO;
import ext.ket.project.program.entity.ProgramEventDTO;
import ext.ket.project.program.entity.ProgramNotifyDTO;
import ext.ket.project.program.entity.ProgramProjectDTO;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.shared.util.SearchUtil;
import ext.ket.shared.util.SessionUtil;

public class StandardProgramService extends StandardManager implements ProgramService {
    private static final long serialVersionUID = 1L;

    /**
     * Default factory for the class.
     * 
     * @return
     * @throws WTException
     * @메소드명 : newStandartProgramService
     * @작성자 : sw775.park
     * @작성일 : 2014. 7. 24.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static StandardProgramService newStandardProgramService() throws WTException {
	StandardProgramService instance = new StandardProgramService();
	instance.initialize();
	return instance;
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<Map<String, String>> compareNotifyHistory(ProgramNotifyDTO paramObject) throws Exception {
	if (paramObject.getVersionOids() == null) {
	    return null;
	}
	List<String> oids = new ArrayList<String>();
	for (String oid : paramObject.getVersionOids()) {
	    long branchId = ((KETProgramSchedule) CommonUtil.getObject(oid)).getIterationInfo().getBranchId();
	    oids.add(branchId + "");
	}

	// subquery
	QuerySpec subquery = new QuerySpec();
	int idxSub2 = subquery.addClassList(KETProgramEvent.class, false);
	int idxSub = subquery.addClassList(KETProgramEventLink.class, false);
	int idxSub3 = subquery.addClassList(KETProgramSchedule.class, false);
	subquery.appendSelect(new ClassAttribute(KETProgramSchedule.class, "versionInfo.identifier.versionId"), new int[] { idxSub3 },
	        false);

	SQLFunction toChar = SQLFunction.newSQLFunction(
	        SQLFunction.TO_CHAR,
	        new ColumnExpression[] { new ClassAttribute(KETProgramEvent.class, KETProgramEvent.CUSTOMER_EVENT_DATE),
	                ConstantExpression.newExpression("YYYY-MM-DD") });
	toChar.setColumnAlias(KETProgramEvent.CUSTOMER_EVENT_DATE);
	subquery.appendSelect(toChar, new int[] { idxSub2 }, false);
	subquery.appendSelect(new ClassAttribute(KETProgramEvent.class, KETProgramEvent.CUSTOMER_EVENT_NAME), new int[] { idxSub2 }, false);
	subquery.appendWhere(new SearchCondition(new ClassAttribute(KETProgramEventLink.class, "roleBObjectRef.key.branchId"),
	        SearchCondition.EQUAL, new ClassAttribute(KETProgramSchedule.class, "iterationInfo.branchId")),
	        new int[] { idxSub, idxSub3 });
	subquery.appendAnd();
	subquery.appendWhere(new SearchCondition(new ClassAttribute(KETProgramEventLink.class, "roleAObjectRef.key.id"),
	        SearchCondition.EQUAL, new ClassAttribute(KETProgramEvent.class, WTAttributeNameIfc.ID_NAME)),
	        new int[] { idxSub, idxSub2 });
	subquery.appendAnd();
	subquery.appendWhere(new SearchCondition(new ClassAttribute(KETProgramEventLink.class, "roleBObjectRef.key.branchId"),
	        SearchCondition.IN, new ArrayExpression(oids.toArray())), new int[] { idxSub });
	subquery.appendGroupBy(new ClassAttribute(KETProgramSchedule.class, "versionInfo.identifier.versionId"), new int[] { idxSub3 },
	        false);
	subquery.appendGroupBy(new ClassAttribute(KETProgramEvent.class, KETProgramEvent.CUSTOMER_EVENT_DATE), new int[] { idxSub2 }, false);
	subquery.appendGroupBy(new ClassAttribute(KETProgramEvent.class, KETProgramEvent.CUSTOMER_EVENT_NAME), new int[] { idxSub2 }, false);
	// SearchUtil.setOrderBy(subquery, KETProgramSchedule.class, idxSub3, "versionInfo.identifier.versionId", false);

	// group query
	QuerySpec qs = new QuerySpec();
	qs.setAdvancedQueryEnabled(true);
	int idx = qs.appendFrom(new SubSelectExpression(subquery));
	String alias = qs.getFromClause().getAliasAt(idx);
	TableColumn customevent = new TableColumn(alias, KETProgramEvent.CUSTOMER_EVENT_NAME);
	qs.appendSelect(customevent, new int[] { idx }, false);

	for (String version : paramObject.getVersions()) {
	    SQLFunction decode = SQLFunction.newSQLFunction(SQLFunction.DECODE, new ClassAttribute(KETProgramSchedule.class,
		    "versionInfo.identifier.versionId"), new KeywordExpression(version), new ClassAttribute(KETProgramEvent.class,
		    KETProgramEvent.CUSTOMER_EVENT_DATE));
	    SQLFunction max = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, decode);
	    max.setColumnAlias("version" + version);
	    qs.appendSelect(max, false);
	}
	qs.appendGroupBy(new ClassAttribute(KETProgramEvent.class, KETProgramEvent.CUSTOMER_EVENT_NAME), new int[] { idx }, false);
	Kogger.debug(getClass(), qs);

	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	QueryResult qr = PersistenceServerHelper.manager.query(qs);
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    Map<String, String> returnMap = new HashMap<String, String>();
	    returnMap.put("NoColor", "2");// for EJS Treegrid row color
	    returnMap.put("customEventName", (String) objArr[0]);
	    for (int i = 0; i < paramObject.getVersions().size(); i++) {
		returnMap.put("version" + paramObject.getVersions().get(i), (String) objArr[i + 1]);
	    }
	    list.add(returnMap);
	}

	// 재정렬
	List<Map<String, String>> reSortingList = new ArrayList<Map<String, String>>();
	for (ProgramEventDTO eventDTO : this.findEventByProgram(paramObject.getVersionOids().get(0))) {
	    for (Map<String, String> map : list) {
		if (eventDTO.getCustomerEventName().equals(map.get("customEventName"))) {
		    reSortingList.add(map);
		    break;
		}
	    }
	}
	return reSortingList;
    }

    /*
     * 공지(개정)시에 조회화면에서의 공지와 수정화면에서 공지 두가지에서 실행된다. 따라서, paramObject에 oid만 있는 경우와 그렇지 않은 경우 두가지로 분리 하여 처리함.
     * 
     * @see ext.ket.project.program.service.ProgramService#createNotice(ext.ket.project.program.entity.ProgramDTO)
     */
    public KETProgramSchedule createNotice(ProgramDTO paramObject) throws Exception {
	if (StringUtil.isEmpty(paramObject.getOid())) {
	    return null;
	}
	KETProgramSchedule programSchedule = (KETProgramSchedule) CommonUtil.getObject(paramObject.getOid());
	KETProgramSchedule newProgramSchedule = (KETProgramSchedule) this.revise(programSchedule, paramObject.getNotifyReason());
	newProgramSchedule.setIsNotify(false); // 공지가 된상태로 변경
	if (paramObject.getProgramName() != null) {
	    newProgramSchedule.setProgramName(paramObject.getProgramName());
	    newProgramSchedule.setProgramAdmin((WTUser) CommonUtil.getObject(paramObject.getProgramAdmin()));// 프로그램 관리자
	    newProgramSchedule.setLastUsingBuyer((NumberCode) CommonUtil.getObject(paramObject.getLastUsingBuyer())); // 최종사용처
	    newProgramSchedule.setDivisionCode(paramObject.getDivisionCode()); // 사업부
	    newProgramSchedule.setSubContractor((NumberCode) CommonUtil.getObject(paramObject.getSubContractor()));// 고객처
	}
	// newProgramSchedule.setIsNotify(this.hasChangedEvent(programSchedule, paramObject)); // event변경 유무에 따라 공지 유무를 결정 한다.
	// EventLink 수정(insert)
	newProgramSchedule = (KETProgramSchedule) PersistenceHelper.manager.save(newProgramSchedule);
	this.saveProjectEventLink(paramObject, newProgramSchedule);
	this.saveProjectLink(paramObject, newProgramSchedule);
	// TODO 관련 프로젝트의 확인/미확인 컬럼을 수정한다.
	// 담당 PM에게 Mail을 발송한다.
	sendMailToPM(newProgramSchedule);
	return newProgramSchedule;
    }

    /**
     * 담당 PM에게 Mail을 발송한다.
     * 
     * @param newProgramSchedule
     * @throws WTException
     * @throws Exception
     * @메소드명 : sendMailToPM
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private void sendMailToPM(KETProgramSchedule newProgramSchedule) throws WTException, Exception {
	List<WTUser> pmList = new ArrayList<WTUser>();
	QueryResult qr = PersistenceHelper.manager.navigate(newProgramSchedule, KETProgramProjectLink.PROJECT_ROLE,
	        KETProgramProjectLink.class, false);
	while (qr.hasMoreElements()) {
	    KETProgramProjectLink projectLink = (KETProgramProjectLink) qr.nextElement();
	    WTUser pm = ProjectUserHelper.manager.getPM(projectLink.getProject());
	    if (pm != null) {
		pmList.add(pm);
	    }
	}
	KETMailHelper.service.sendFormMail("08040", "NoticeMailLine2.html", newProgramSchedule, newProgramSchedule.getProgramAdmin(),
	        pmList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.service.CommonServiceInterface#delete(java.lang.Object)
     */
    public KETProgramSchedule delete(ProgramDTO paramObject) throws Exception {
	KETProgramSchedule programSchedule = (KETProgramSchedule) CommonUtil.getObject(paramObject.getOid());
	// VersionControlHelper.service.deleteIterations(programSchedule, programSchedule);
	this.deleteEventLinkByProgramSchedule(programSchedule);
	this.deleteProjectLinkByProgramSchedule(programSchedule);
	// 모든 Revision 삭제
	programSchedule = (KETProgramSchedule) PersistenceHelper.manager.delete(programSchedule);
	// Master 삭제
	PersistenceHelper.manager.delete(programSchedule.getMaster());
	return programSchedule;
    }

    /**
     * EventLink 전부 삭제
     * 
     * @param programSchedule
     * @throws Exception
     * @메소드명 : deleteEventLinkByProgramSchedule
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private void deleteEventLinkByProgramSchedule(KETProgramSchedule programSchedule) throws Exception {
	for (ProgramEventDTO eventDTO : this.findEventByProgram(CommonUtil.getOIDString(programSchedule))) {
	    PersistenceHelper.manager.delete((KETProgramEventLink) CommonUtil.getObject(eventDTO.getOid()));
	}
    }

    /**
     * ProjectLink 전부 삭제
     * 
     * @param programSchedule
     * @throws Exception
     * @메소드명 : deleteProjectLinkByProgramSchedule
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private void deleteProjectLinkByProgramSchedule(KETProgramSchedule programSchedule) throws Exception {
	for (ProgramProjectDTO projectDTO : this.findProjectByProgram(CommonUtil.getOIDString(programSchedule))) {
	    PersistenceHelper.manager.delete((KETProgramProjectLink) CommonUtil.getObject(projectDTO.getOid()));
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.service.CommonServiceInterface#find(java.lang.Object)
     */
    public List<KETProgramSchedule> find(ProgramDTO paramObject) throws Exception {
	List<KETProgramSchedule> list = new ArrayList<KETProgramSchedule>();
	QueryResult qr = PersistenceServerHelper.manager.query(this.getFindQuery(paramObject));
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    list.add((KETProgramSchedule) objArr[0]);
	}
	return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.project.program.service.ProgramService#findEventByCarType(ext.ket.project.program.entity.ProgramDTO)
     */
    @SuppressWarnings("deprecation")
    @Override
    public List<ProgramEventDTO> findEventByCarType(String carTypeOid) throws Exception {
	List<ProgramEventDTO> list = new ArrayList<ProgramEventDTO>();
	if (StringUtil.isEmpty(carTypeOid)) {
	    return list;
	}
	long mpOid = CommonUtil.getOIDLongValue(carTypeOid);
	/*QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(OEMPlan.class, true);
	int idx2 = qs.addClassList(OEMProjectType.class, false);
	int idx3 = qs.addClassList(ModelPlan.class, false);
	qs.appendSelect(new ClassAttribute(OEMProjectType.class, OEMProjectType.CODE), true);
	SearchCondition sc = new SearchCondition(new ClassAttribute(ModelPlan.class, "carTypeReference.key.id"), "=", new ClassAttribute(
	        OEMProjectType.class, "thePersistInfo.theObjectIdentifier.id"));
	qs.appendWhere(sc, new int[] { idx3, idx2 });
	qs.appendAnd();
	SearchCondition sc2 = new SearchCondition(new ClassAttribute(ModelPlan.class, "thePersistInfo.theObjectIdentifier.id"), "=",
	        new ClassAttribute(OEMPlan.class, "modelPlanReference.key.id"));
	qs.appendWhere(sc2, new int[] { idx3, idx });
	qs.appendAnd();
	SearchCondition sc3 = new SearchCondition(OEMProjectType.class, "thePersistInfo.theObjectIdentifier.id", "=", mpOid);
	qs.appendWhere(sc3, new int[] { idx2 });
	// SearchUtil.setOrderBy(qs, OEMProjectType.class, idx2, OEMProjectType.CODE, false);
	SearchUtil.setOrderBy(qs, OEMPlan.class, idx, OEMPlan.OEM_END_DATE, false);
	Kogger.debug(getClass(), qs);*/
	QuerySpec qs = this.getEventQuerySpec(mpOid);
	
	QueryResult qr = PersistenceHelper.manager.find(qs);
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    OEMPlan oemPlan = (OEMPlan) objArr[0];
	    // 차종이벤트 일정이 있는 경우만 리스트에 나타나도록 수정
	    if (oemPlan.getOemEndDate() != null) {
		list.add(new ProgramEventDTO((OEMPlan) objArr[0]));
	    }
	}
	return list;
    }
    
    public QuerySpec getEventQuerySpec(long mpOid) throws Exception{
	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(OEMPlan.class, true);
	int idx2 = qs.addClassList(OEMProjectType.class, false);
	int idx3 = qs.addClassList(ModelPlan.class, false);
	qs.appendSelect(new ClassAttribute(OEMProjectType.class, OEMProjectType.CODE), true);
	SearchCondition sc = new SearchCondition(new ClassAttribute(ModelPlan.class, "carTypeReference.key.id"), "=", new ClassAttribute(
	        OEMProjectType.class, "thePersistInfo.theObjectIdentifier.id"));
	qs.appendWhere(sc, new int[] { idx3, idx2 });
	qs.appendAnd();
	SearchCondition sc2 = new SearchCondition(new ClassAttribute(ModelPlan.class, "thePersistInfo.theObjectIdentifier.id"), "=",
	        new ClassAttribute(OEMPlan.class, "modelPlanReference.key.id"));
	qs.appendWhere(sc2, new int[] { idx3, idx });
	qs.appendAnd();
	SearchCondition sc3 = new SearchCondition(OEMProjectType.class, "thePersistInfo.theObjectIdentifier.id", "=", mpOid);
	qs.appendWhere(sc3, new int[] { idx2 });
	// SearchUtil.setOrderBy(qs, OEMProjectType.class, idx2, OEMProjectType.CODE, false);
	SearchUtil.setOrderBy(qs, OEMPlan.class, idx, OEMPlan.OEM_END_DATE, false);
	
	return qs;
    }
    
    public String findEventbyCarTypeAndEventName(String carTypeOid, String eventName) throws Exception{//차종과 이벤트 name으로 자동차의 이벤트 찾기 2017.06.08 by 황정태
	
	long mpOid = CommonUtil.getOIDLongValue(carTypeOid);
	
	QuerySpec qs = this.getEventQuerySpec(mpOid);
	QueryResult qr = PersistenceHelper.manager.find(qs);
	OEMPlan oemPlan = null;
	String oemPlanOid = "";
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    oemPlan = (OEMPlan) objArr[0];
	    if(eventName.equals(oemPlan.getOemType().getName())){
		oemPlanOid = CommonUtil.getOIDString(oemPlan);
	    }
	}
	return oemPlanOid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.project.program.service.ProgramService#findEventByProgram(java.lang.String)
     */
    @SuppressWarnings("deprecation")
    @Override
    public List<ProgramEventDTO> findEventByProgram(String programOid) throws Exception {
	List<ProgramEventDTO> list = new ArrayList<ProgramEventDTO>();
	if (StringUtil.isEmpty(programOid)) {
	    return list;
	}
	KETProgramSchedule programSchedule = (KETProgramSchedule) CommonUtil.getObject(programOid);
	long branchId = programSchedule.getIterationInfo().getBranchId();
	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(KETProgramEventLink.class, true);
	int idx2 = qs.addClassList(KETProgramEvent.class, false);
	SearchCondition sc2 = new SearchCondition(new ClassAttribute(KETProgramEventLink.class, WTAttributeNameIfc.ROLEA_OBJECT_ID),
	        SearchCondition.EQUAL, new ClassAttribute(KETProgramEvent.class, WTAttributeNameIfc.ID_NAME));
	qs.appendWhere(sc2, new int[] { idx, idx2 });
	qs.appendAnd();
	SearchCondition sc3 = new SearchCondition(KETProgramEventLink.class, WTAttributeNameIfc.ROLEB_VERSION_ID, SearchCondition.EQUAL,
	        branchId);
	qs.appendWhere(sc3, new int[] { idx });
	SearchUtil.setOrderBy(qs, KETProgramEvent.class, idx2, KETProgramEvent.EVENT_ORDER, false);

	QueryResult qr = PersistenceHelper.manager.find(qs);
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    list.add(new ProgramEventDTO((KETProgramEventLink) objArr[0]));
	}
	return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.project.program.service.ProgramService#findNotifyHistoryList(ext.ket.project.program.entity.ProgramDTO)
     */
    @Override
    public List<ProgramNotifyDTO> findNotifyHistoryList(ProgramDTO paramObject) throws Exception {
	KETProgramSchedule programSchedule = (KETProgramSchedule) CommonUtil.getObject(paramObject.getOid());
	QueryResult qr = VersionControlHelper.service.allVersionsOf(programSchedule.getMaster());
	List<ProgramNotifyDTO> list = new ArrayList<ProgramNotifyDTO>();
	while (qr.hasMoreElements()) {
	    RevisionControlled obj = ((RevisionControlled) qr.nextElement());
	    list.add(new ProgramNotifyDTO(obj));
	}
	return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.service.CommonServiceInterface#findPaging(java.lang.Object)
     */
    public PageControl findPaging(ProgramDTO paramObject) throws Exception {
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
    
    public QuerySpec getFindSubQueryLastestProgramByProjectOid(String projectOid, boolean isBase) throws Exception {
	long pjtOid = CommonUtil.getOIDLongValue(projectOid);
	
	QuerySpec qs = new QuerySpec();
	
	int idx = qs.addClassList(KETProgramProjectLink.class, false);
	int idx2 = qs.addClassList(KETProgramSchedule.class, false);
	int idx3 = qs.addClassList(KETProgramEventLink.class, false);
	int idx4 = qs.addClassList(KETProgramEvent.class, false);
	
	SearchCondition sc = new SearchCondition(KETProgramProjectLink.class, WTAttributeNameIfc.ROLEA_OBJECT_ID, SearchCondition.EQUAL,pjtOid);
	qs.appendWhere(sc, new int[] { idx });
	qs.appendAnd();
	
	if(isBase){
	    SearchCondition sc1 = new SearchCondition(KETProgramProjectLink.class, "isBase", SearchCondition.IS_TRUE,Boolean.getBoolean("true"));
	    qs.appendWhere(sc1, new int[] { idx });
	    qs.appendAnd();    
	}

	SearchCondition sc2 = new SearchCondition(new ClassAttribute(KETProgramProjectLink.class, WTAttributeNameIfc.ROLEB_VERSION_ID),
	        SearchCondition.EQUAL, new ClassAttribute(KETProgramSchedule.class, WTAttributeNameIfc.VERSION_ID_NAME));
	qs.appendWhere(sc2, new int[] { idx, idx2 });
	qs.appendAnd();

	SearchCondition sc3 = new SearchCondition(new ClassAttribute(KETProgramEventLink.class, WTAttributeNameIfc.ROLEB_VERSION_ID),
	        SearchCondition.EQUAL, new ClassAttribute(KETProgramSchedule.class, WTAttributeNameIfc.VERSION_ID_NAME));
	qs.appendWhere(sc3, new int[] { idx3, idx2 });
	qs.appendAnd();

	SearchCondition sc3_1 = new SearchCondition(KETProgramSchedule.class, WTAttributeNameIfc.LATEST_ITERATION, SearchCondition.IS_TRUE, Boolean.getBoolean("true"));
	qs.appendWhere(sc3_1, new int[] { idx2 });
	qs.appendAnd();

	SearchCondition sc4 = new SearchCondition(new ClassAttribute(KETProgramEventLink.class, WTAttributeNameIfc.ROLEA_OBJECT_ID),
	        SearchCondition.EQUAL, new ClassAttribute(KETProgramEvent.class, WTAttributeNameIfc.ID_NAME));
	qs.appendWhere(sc4, new int[] { idx3, idx4 });
	
	return qs;
    }
    
    public StatementSpec getFindQueryLastestProgramByProjectOid(String projectOid, boolean isBase) throws Exception {
	long pjtOid = CommonUtil.getOIDLongValue(projectOid);
	
	QuerySpec qs = new QuerySpec();
	
	int idx = qs.addClassList(KETProgramProjectLink.class, true);
	int idx2 = qs.addClassList(KETProgramSchedule.class, false);

	SearchCondition sc = new SearchCondition(KETProgramProjectLink.class, WTAttributeNameIfc.ROLEA_OBJECT_ID, SearchCondition.EQUAL,pjtOid);
	qs.appendWhere(sc, new int[] { idx });
	qs.appendAnd();
	
	if(isBase){
	    SearchCondition sc1 = new SearchCondition(KETProgramProjectLink.class, "isBase", SearchCondition.IS_TRUE,Boolean.getBoolean("true"));
	    qs.appendWhere(sc1, new int[] { idx });
	    qs.appendAnd();    
	}

	SearchCondition sc2 = new SearchCondition(new ClassAttribute(KETProgramProjectLink.class, WTAttributeNameIfc.ROLEB_VERSION_ID),
	        SearchCondition.EQUAL, new ClassAttribute(KETProgramSchedule.class, WTAttributeNameIfc.VERSION_ID_NAME));
	qs.appendWhere(sc2, new int[] { idx, idx2 });
	qs.appendAnd();

	SearchCondition sc3_1 = new SearchCondition(KETProgramSchedule.class, WTAttributeNameIfc.LATEST_ITERATION, SearchCondition.IS_TRUE, Boolean.getBoolean("true"));
	qs.appendWhere(sc3_1, new int[] { idx2 });
	qs.appendAnd();


	
	QuerySpec subQs = this.getFindSubQueryLastestProgramByProjectOid(projectOid, isBase);
	
	StatementSpec lastquery = null;
	
	if (!subQs.isAdvancedQueryEnabled()) {
	    
	    TableColumn VERSIONIDA2VERSIONINFO = new TableColumn(subQs.getFromClause().getAliasAt(idx2), "VERSIONIDA2VERSIONINFO");
	    ColumnListExpression listExpression = new ColumnListExpression();
	    listExpression.addColumn(VERSIONIDA2VERSIONINFO);
	    
	    subQs.setAdvancedQueryEnabled(true);
	    TableColumn versionAttr = new TableColumn(subQs.getFromClause().getAliasAt(idx2), "VERSIONIDA2VERSIONINFO");
	    SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, versionAttr);
	    subQs.appendSelect(function, new int[] { idx }, false);
	    
	    StatementSpec subSpec = subQs;
	    SubSelectExpression selectExpression = new SubSelectExpression(subSpec);
	    selectExpression.setAccessControlRequired(false);
	    qs.appendWhere(new SearchCondition(listExpression, "IN", selectExpression), new int[] { idx });
	    lastquery = qs;
	    lastquery.setAdvancedQueryEnabled(true);
	}
	
	System.out.println("lastquery ###" + lastquery);
	
	return lastquery;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.project.program.service.ProgramService#findProgramByProject(java.lang.String)
     */
    @SuppressWarnings("deprecation")
    public List<ProgramDTO> findProgramByProject(String projectOid) throws Exception {
	List<ProgramDTO> list = new ArrayList<ProgramDTO>();
	if (StringUtil.isEmpty(projectOid)) {
	    return list;
	}

	//QueryResult rs = PersistenceHelper.manager.navigate((ProductProject) CommonUtil.getObject(projectOid), KETProgramProjectLink.PROGRAM_ROLE, KETProgramProjectLink.class, false);
	
	
	QueryResult rs = PersistenceHelper.manager.find(this.getFindQueryLastestProgramByProjectOid(projectOid,false));
	
	while (rs.hasMoreElements()) {
	    Object[] objArr = (Object[]) rs.nextElement();
	    KETProgramProjectLink programScheduleLink = (KETProgramProjectLink) objArr[0];
	    ProgramDTO dto = new ProgramDTO(programScheduleLink.getProgram());
	    dto.setProjectLinkOid(CommonUtil.getOIDString(programScheduleLink));
	    dto.setBaseProgram(programScheduleLink.isIsBase() ? "1" : "0");
	    dto.setCheckedEvent(programScheduleLink.getIsCheckedEvent() ? "1" : "0");
	    list.add(dto);
	}
	removeDuplicateArray(list);
	return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.project.program.service.ProgramService#findProjectByProgram(java.lang.String)
     */
    @SuppressWarnings("deprecation")
    public List<ProgramProjectDTO> findProjectByProgram(String programOid) throws Exception {
	List<ProgramProjectDTO> list = new ArrayList<ProgramProjectDTO>();
	if (StringUtil.isEmpty(programOid)) {
	    return list;
	}

	QueryResult rs = PersistenceHelper.manager.navigate((KETProgramSchedule) CommonUtil.getObject(programOid),
	        KETProgramProjectLink.PROJECT_ROLE, KETProgramProjectLink.class, false);
	while (rs.hasMoreElements()) {
	    KETProgramProjectLink programScheduleLink = (KETProgramProjectLink) rs.nextElement();
	    ProgramProjectDTO dto = new ProgramProjectDTO(programScheduleLink);
	    // 프로젝트가 최신버전인지 체크
	    if (programScheduleLink.getProject().isLastest()) {
		list.add(dto);
	    }
	}
	removeDuplicateArray(list);
	return list;
    }

    public List<ProgramProjectDTO> findProjectByProject(String projectOid) throws Exception {
	List<ProgramProjectDTO> list = new ArrayList<ProgramProjectDTO>();
	if (StringUtil.isEmpty(projectOid)) {
	    return list;
	}
	List<ProgramDTO> programList = this.findProgramByProject(projectOid);
	if (programList == null || programList.size() == 0) {
	    return null;
	}

	for (ProgramDTO programDTO : programList) {
	    list.addAll(this.findProjectByProgram(programDTO.getOid()));
	}

	removeDuplicateArray(list);

	return list;
    }

    // 중복 프로젝트 제거 및 해당 프로젝트 제거
/*    private void removeDuplicateArray(List<ProgramProjectDTO> list) {
	List<String> projectNos = new ArrayList<String>();

	for (int i = 0; i < list.size(); i++) {
	    ProgramProjectDTO programDTO = list.get(i);
	    if (projectNos.contains(programDTO.getProjectNo())) {
		list.remove(i);
	    }
	    projectNos.add(programDTO.getProjectNo());
	}
    }*/
    
 // 중복 프로젝트 제거 및 해당 프로젝트 제거
    private void removeDuplicateArray(List<?> list) {
	List<String> projectNos = new ArrayList<String>();
	
	if(list.size() > 0){
	    
	    if(list.get(0) instanceof ProgramProjectDTO){
		for (int i = 0; i < list.size(); i++) {
		    ProgramProjectDTO programDTO = (ProgramProjectDTO)list.get(i);
		    if (projectNos.contains(programDTO.getProjectNo())) {
			list.remove(i);
		    }
		    projectNos.add(programDTO.getProjectNo());
		}
	    }
	    
	    if(list.get(0) instanceof ProgramDTO){
		for (int i = 0; i < list.size(); i++) {
		    ProgramDTO programDTO = (ProgramDTO)list.get(i);
		    
		    if (projectNos.contains(programDTO.getProgramNo())) {
			list.remove(i);
		    }
		    
		    projectNos.add(programDTO.getProgramNo());
		    
		    
		}
	    }
	}
	
	
    }

    /**
     * 프로그램 검색 쿼리
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
    private QuerySpec getFindQuery(ProgramDTO paramObject) throws Exception {
	QuerySpec sqs = new QuerySpec();
	int idxSub = sqs.addClassList(KETProgramSchedule.class, false);
	ClassAttribute masterOidAttr = new ClassAttribute(KETProgramSchedule.class, "masterReference.key.id");
	sqs.appendSelect(masterOidAttr, false);
	ClassAttribute versionAttr = new ClassAttribute(KETProgramSchedule.class, "versionInfo.identifier.versionId");
	SQLFunction max = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, versionAttr);
	sqs.appendSelect(max, false);
	sqs.appendGroupBy(new ClassAttribute(KETProgramSchedule.class, "masterReference.key.id"), new int[] { idxSub }, false);

	QuerySpec qs = new QuerySpec();
	qs.setAdvancedQueryEnabled(true);
	int idx = qs.addClassList(KETProgramSchedule.class, true);
	int idx2 = qs.addClassList(WTUser.class, false);
	// int idx3 = qs.addClassList(NumberCode.class, false);
	// int idx4 = qs.addClassList(NumberCode.class, false);
	// int idx5 = qs.addClassList(NumberCode.class, false);
	TableColumn masterOidAttr1 = new TableColumn(qs.getFromClause().getAliasAt(idx), "IDA3MASTERREFERENCE");
	TableColumn versionAttr1 = new TableColumn(qs.getFromClause().getAliasAt(idx), "VERSIONIDA2VERSIONINFO");
	ColumnListExpression listExpression = new ColumnListExpression();
	listExpression.addColumn(masterOidAttr1);
	listExpression.addColumn(versionAttr1);
	SubSelectExpression selectExpression = new SubSelectExpression(sqs);
	selectExpression.setAccessControlRequired(false);
	qs.appendWhere(new SearchCondition(KETProgramSchedule.class, KETProgramSchedule.LATEST_ITERATION, SearchCondition.IS_TRUE),
	        new int[] { idx });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(new ClassAttribute(KETProgramSchedule.class, "programAdminReference.key.id"),
	        SearchCondition.EQUAL, new ClassAttribute(WTUser.class, WTAttributeNameIfc.ID_NAME)), new int[] { idx, idx2 });
	qs.appendAnd();
	// qs.appendWhere(new SearchCondition(new ClassAttribute(KETProgramSchedule.class, "carTypeReference.key.id"),
	// SearchCondition.EQUAL, new ClassAttribute(NumberCode.class,
	// WTAttributeNameIfc.ID_NAME)), new int[] { idx, idx3 });
	// qs.appendAnd();
	// qs.appendWhere(new SearchCondition(new ClassAttribute(KETProgramSchedule.class, "lastUsingBuyerReference.key.id"),
	// SearchCondition.EQUAL, new ClassAttribute(NumberCode.class,
	// WTAttributeNameIfc.ID_NAME)), new int[] { idx, idx4 });
	// qs.appendAnd();
	// qs.appendWhere(new SearchCondition(new ClassAttribute(KETProgramSchedule.class, "subContractorReference.key.id"),
	// SearchCondition.EQUAL, new ClassAttribute(NumberCode.class,
	// WTAttributeNameIfc.ID_NAME)), new int[] { idx, idx5 });
	// qs.appendAnd();
	qs.appendWhere(new SearchCondition(listExpression, SearchCondition.IN, selectExpression), new int[] { idx });
	SearchCondition sc = null;
	if (!StringUtil.isTrimToEmpty(paramObject.getProgramNo())) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    sc = new SearchCondition(KETProgramSchedule.class, KETProgramSchedule.NUMBER, SearchCondition.LIKE, StringUtil.changeString(
		    paramObject.getProgramNo(), "*", "%"), false);
	    qs.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getProgramName())) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    sc = new SearchCondition(KETProgramSchedule.class, KETProgramSchedule.PROGRAM_NAME, SearchCondition.LIKE,
		    StringUtil.changeString(paramObject.getProgramName(), "*", "%"), false);
	    qs.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getProgramAdmin())) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    sc = new SearchCondition(WTUser.class, WTUser.FULL_NAME, SearchCondition.LIKE, StringUtil.changeString(
		    paramObject.getProgramAdmin(), "*", "%"), false);
	    qs.appendWhere(sc, new int[] { idx2 });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getState())) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    sc = new SearchCondition(new ClassAttribute(KETProgramSchedule.class, "state.state"), SearchCondition.IN, new ArrayExpression(
		    paramObject.getState().split(",")));
	    qs.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getCarType())) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    sc = new SearchCondition(KETProgramSchedule.class, "carTypeReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(paramObject.getCarType()));
	    qs.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getDivisionCode())) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    sc = new SearchCondition(KETProgramSchedule.class, KETProgramSchedule.DIVISION_CODE, SearchCondition.EQUAL,
		    paramObject.getDivisionCode(), false);
	    qs.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getSubContractor())) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    sc = new SearchCondition(KETProgramSchedule.class, "subContractorReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(paramObject.getSubContractor()));
	    qs.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getLastUsingBuyer())) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    sc = new SearchCondition(KETProgramSchedule.class, "lastUsingBuyerReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(paramObject.getLastUsingBuyer()));
	    qs.appendWhere(sc, new int[] { idx });
	}

	String startDate = StringUtil.checkNull(paramObject.getStartDate());
	String endDate = StringUtil.checkNull(paramObject.getEndDate());

	if (startDate.length() > 0 || endDate.length() > 0) {

	    if (startDate != null && startDate.length() > 0) {
		Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(startDate, new ParsePosition(0)).getTime()));

		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		sc = new SearchCondition(KETProgramSchedule.class, KETProgramSchedule.START_DATE, SearchCondition.GREATER_THAN_OR_EQUAL,
		        convertDate);
		qs.appendWhere(sc, new int[] { idx });
	    }
	    if (endDate != null && endDate.length() > 0) {
		Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(endDate + ":23-59-59",
		        new ParsePosition(0)).getTime()));

		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		sc = new SearchCondition(KETProgramSchedule.class, KETProgramSchedule.START_DATE, SearchCondition.LESS_THAN, convertDate);
		qs.appendWhere(sc, new int[] { idx });
	    }
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    String sortName = paramObject.getSortName();
	    if ("programNo".equals(paramObject.getSortName())) {
		sortName = "master>number";
	    }
	    if ("-programNo".equals(paramObject.getSortName())) {
		sortName = "-master>number";
	    }
	    if ("carType".equals(paramObject.getSortName())) {
		sortName = "carTypeReference.key.id";
	    }
	    if ("-carType".equals(paramObject.getSortName())) {
		sortName = "subContractorReference.key.id";
	    }
	    if ("subContractor".equals(paramObject.getSortName())) {
		sortName = "subContractorReference.key.id";
	    }
	    if ("-subContractor".equals(paramObject.getSortName())) {
		sortName = "-subContractorReference.key.id";
	    }
	    if ("lastUsingBuyer".equals(paramObject.getSortName())) {
		sortName = "lastUsingBuyerReference.key.id";
	    }
	    if ("-lastUsingBuyer".equals(paramObject.getSortName())) {
		sortName = "-lastUsingBuyerReference.key.id";
	    }

	    // '-'내림차순
	    if (sortName.startsWith("-")) {
		SearchUtil.setOrderBy(qs, KETProgramSchedule.class, idx, sortName.substring(1), true);
	    } else {
		SearchUtil.setOrderBy(qs, KETProgramSchedule.class, idx, sortName, false);
	    }
	} else {
	    SearchUtil.setOrderBy(qs, KETProgramSchedule.class, idx, KETProgramSchedule.NUMBER, true);
	}
	Kogger.debug(getClass(), qs);
	return qs;
    }

    /**
     * 차종에 의한 형태 코드를 반환한다.
     * 
     * @param carTypeOid
     * @return
     * @throws Exception
     * @메소드명 : getFormTypeByCarType
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 2.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @SuppressWarnings("deprecation")
    private NumberCode getFormTypeByCarType(String carTypeOid) throws Exception {
	if (StringUtil.isEmpty(carTypeOid)) {
	    return null;
	}
	long mpOid = CommonUtil.getOIDLongValue(carTypeOid);
	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(OEMPlan.class, false);
	int idx2 = qs.addClassList(OEMProjectType.class, false);
	int idx3 = qs.addClassList(ModelPlan.class, true);

	SearchCondition sc = new SearchCondition(new ClassAttribute(ModelPlan.class, "carTypeReference.key.id"), "=", new ClassAttribute(
	        OEMProjectType.class, "thePersistInfo.theObjectIdentifier.id"));
	qs.appendWhere(sc, new int[] { idx3, idx2 });
	qs.appendAnd();
	SearchCondition sc2 = new SearchCondition(new ClassAttribute(ModelPlan.class, "thePersistInfo.theObjectIdentifier.id"), "=",
	        new ClassAttribute(OEMPlan.class, "modelPlanReference.key.id"));
	qs.appendWhere(sc2, new int[] { idx3, idx });
	qs.appendAnd();
	SearchCondition sc3 = new SearchCondition(OEMProjectType.class, "thePersistInfo.theObjectIdentifier.id", "=", mpOid);
	qs.appendWhere(sc3, new int[] { idx2 });

	ClassAttribute attr1 = new ClassAttribute(OEMProjectType.class, OEMProjectType.CODE);
	qs.appendOrderBy(new OrderBy(attr1, false), new int[] { idx2 });

	QueryResult qr = PersistenceHelper.manager.find(qs);
	ModelPlan modelPlan = null;
	if (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    modelPlan = (ModelPlan) objArr[0];
	}
	return modelPlan.getFormType();
    }

    @SuppressWarnings("deprecation")
    public ModelPlan getModelPlanByCarType(String carTypeOid) throws Exception {
	if (StringUtil.isEmpty(carTypeOid)) {
	    return null;
	}
	long mpOid = CommonUtil.getOIDLongValue(carTypeOid);
	QuerySpec qs = new QuerySpec();
	int idx2 = qs.addClassList(OEMProjectType.class, false);
	int idx3 = qs.addClassList(ModelPlan.class, true);

	SearchCondition sc = new SearchCondition(new ClassAttribute(ModelPlan.class, "carTypeReference.key.id"), "=", new ClassAttribute(
	        OEMProjectType.class, "thePersistInfo.theObjectIdentifier.id"));
	qs.appendWhere(sc, new int[] { idx3, idx2 });
	qs.appendAnd();
	SearchCondition sc3 = new SearchCondition(OEMProjectType.class, "thePersistInfo.theObjectIdentifier.id", "=", mpOid);
	qs.appendWhere(sc3, new int[] { idx2 });

	QueryResult qr = PersistenceHelper.manager.find(qs);
	if (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    return (ModelPlan) objArr[0];
	}
	return null;
    }

    /**
     * 신규 프로그램 No 생성
     * 
     * 1. PG 2. 사업부 구분(A-자동차사업부, E-전자사업부) 3. 년도 구분(2자리) - 14(2014년 등록 프로그램) 4. 일년번호(3자리) - 001(사업부-년도 기준 생성 순서)
     * 
     * @return
     * @throws Exception
     * @메소드명 : getNewProgramNo
     * @작성자 : sw775.park
     * @작성일 : 2014. 7. 26.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private String getNewProgramNo(ProgramDTO paramObject) throws Exception {
	// 차종기준이 앞에 오도록 추가 필요
	ModelPlan modelPlan = this.getModelPlanByCarType(paramObject.getCarType());
	String newProgramNo = modelPlan.getProgramBaseNo();
	if (StringUtil.isEmpty(newProgramNo)) {
	    newProgramNo = "PG";
	    // throw new Exception("자동차일정의 프로그램 No 기준이 존재하지 않습니다.");
	}
	if ("ER".equals(paramObject.getDivisionCode())) {
	    newProgramNo += "E";
	} else {
	    newProgramNo += "A";
	}
	newProgramNo += DateUtil.getThisYear().substring(2);
	newProgramNo += ManageSequence.getSeqNo(newProgramNo, "000", "WTDOCUMENTMASTERKEY", "WTKEY");
	return newProgramNo;
    }

    /**
     * Link된 프로젝트의 상태에 따라 프로그램의 상태를 저장 한다.
     * 
     * @param projectDTOs
     * @return
     * @메소드명 : getProjectState
     * @작성자 : sw775.park
     * @작성일 : 2014. 10. 8.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private String getStateByProjects(List<ProgramProjectDTO> projectDTOs) {
	if (projectDTOs.size() == 0) {
	    return null;
	}
	List<String> pmoInWorkList = new ArrayList<String>();
	List<String> completedList = new ArrayList<String>();
	List<String> stopedList = new ArrayList<String>();
	List<String> withdRawnList = new ArrayList<String>();
	List<String> planchangeList = new ArrayList<String>();
	List<String> delayList = new ArrayList<String>();
	List<String> underReviewList = new ArrayList<String>();
	List<String> devAssignList = new ArrayList<String>();

	for (ProgramProjectDTO programProjectDTO : projectDTOs) {
	    ProductProject productProject = (ProductProject) CommonUtil.getObject(programProjectDTO.getProjectOid());
	    // 1개라도 진행이 있으면 진행
	    // Kogger.debug(getClass(), "STATE >> " + productProject.getLifeCycleState());
	    if (productProject.getLifeCycleState().toString().equals("PROGRESS")) {
		return "PROGRESS"; // 진행
	    } else if (productProject.getLifeCycleState().toString().equals("PMOINWORK")) {
		pmoInWorkList.add("PMOINWORK"); // 등록중
	    } else if (productProject.getLifeCycleState().toString().equals("COMPLETED")) {
		completedList.add("COMPLETED"); // 완료
	    } else if (productProject.getLifeCycleState().toString().equals("STOPED")) {
		stopedList.add("STOPED"); // 중지
	    } else if (productProject.getLifeCycleState().toString().equals("WITHDRAWN")) {
		withdRawnList.add("WITHDRAWN"); // 취소
	    } else if (productProject.getLifeCycleState().toString().equals("PLANCHANGE")) {
		planchangeList.add("PLANCHANGE"); // 일정변경
	    } else if (productProject.getLifeCycleState().toString().equals("delay")) {
		delayList.add("delay"); // 지연
	    } else if (productProject.getLifeCycleState().toString().equals("DEVASSIGN")) {
		devAssignList.add("DEVASSIGN"); // 담당자지정중
	    } else if (productProject.getLifeCycleState().toString().equals("UNDERREVIEW")) {
		underReviewList.add("DEVASSIGN"); // 결재중
	    }
	}
	if (projectDTOs.size() == pmoInWorkList.size()) {
	    return pmoInWorkList.get(0); // 등록중
	} else if (projectDTOs.size() == completedList.size()) {
	    return completedList.get(0); // 완료
	} else if (projectDTOs.size() == stopedList.size()) {
	    return stopedList.get(0); // 중지
	} else if (projectDTOs.size() == withdRawnList.size()) {
	    return withdRawnList.get(0); // 취소
	} else if (projectDTOs.size() == planchangeList.size()) {
	    return planchangeList.get(0); // 일정변경
	} else if (projectDTOs.size() == delayList.size()) {
	    return delayList.get(0); // 지연
	} else if (projectDTOs.size() == devAssignList.size()) {
	    return devAssignList.get(0); // 담당자 지정중
	} else if (projectDTOs.size() == underReviewList.size()) {
	    return underReviewList.get(0); // 결재중
	} else {
	    return null;
	}
    }

    /**
     * event 변경 유무
     * 
     * @param programSchedule
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : hasChangedEvent
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public boolean hasChangedEvent(KETProgramSchedule programSchedule, ProgramDTO paramObject) throws Exception {
	if (this.findProjectByProgram(CommonUtil.getOIDString(programSchedule)).size() == 0) {
	    return false;
	}

	List<ProgramEventDTO> sourceEvents = this.findEventByProgram(CommonUtil.getOIDString(programSchedule));
	for (int i = 0; i < sourceEvents.size(); i++) {
	    if (!sourceEvents.get(i).getCustomerEventName().equals(paramObject.getEvents().get(i).getCustomerEventName())
		    || !sourceEvents.get(i).getCustomerEventDate().equals(paramObject.getEvents().get(i).getCustomerEventDate())) {
		return true;
	    }
	}
	return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.project.program.service.ProgramService#hasChangedProgramEvent(java.lang.String)
     */
    public boolean hasChangedProgramEvent(String projectOid) throws Exception {
	for (ProgramDTO programDTO : this.findProgramByProject(projectOid)) {
	    if (programDTO.getIsNotify() && programDTO.getCheckedEvent().equals("0")) {
		return true;
	    }
	}
	return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.service.CommonServiceInterface#modify(java.lang.Object)
     */
    public KETProgramSchedule modify(ProgramDTO paramObject) throws Exception {
	if (StringUtil.isEmpty(paramObject.getOid())) {
	    return null;
	}
	KETProgramSchedule programSchedule = (KETProgramSchedule) CommonUtil.getObject(paramObject.getOid());
	// 1.checkout check 및 working copy 생성
	KETProgramSchedule newProgramSchedule = (KETProgramSchedule) ObjectUtil.checkout(programSchedule);
	// 2.data 저장
	newProgramSchedule.setProgramName(paramObject.getProgramName());
	newProgramSchedule.setProgramAdmin((WTUser) CommonUtil.getObject(paramObject.getProgramAdmin()));// 프로그램 관리자
	newProgramSchedule.setLastUsingBuyer((NumberCode) CommonUtil.getObject(paramObject.getLastUsingBuyer())); // 최종사용처
	newProgramSchedule.setDivisionCode(paramObject.getDivisionCode());
	newProgramSchedule.setSubContractor((NumberCode) CommonUtil.getObject(paramObject.getSubContractor()));
	newProgramSchedule.setIsNotify(this.hasChangedEvent(programSchedule, paramObject)); // event변경 유무에 따라 공지 유무를 결정 한다.
	PersistenceHelper.manager.modify(newProgramSchedule);
	// 3.checkin
	newProgramSchedule = (KETProgramSchedule) ObjectUtil.checkin(newProgramSchedule);

	// EventLink 수정(insert)
	this.saveProjectEventLink(paramObject, newProgramSchedule);// event 다시 등록
	this.saveProjectLink(paramObject, newProgramSchedule);
	return newProgramSchedule;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.project.program.service.ProgramService#modifyBaseProgram(java.lang.String, java.lang.String)
     */
    public KETProgramProjectLink modifyBaseProgram(String programOid, String projectLinkOid) throws Exception {
	KETProgramProjectLink programProjectLink = null;
	// 전부 isBase를 false로 수정
	for (ProgramProjectDTO programProjectDTO : this.findProjectByProgram(programOid)) {
	    programProjectLink = (KETProgramProjectLink) CommonUtil.getObject(programProjectDTO.getOid());
	    programProjectLink.setIsBase(false);
	    PersistenceHelper.manager.modify(programProjectLink);
	}
	programProjectLink = (KETProgramProjectLink) CommonUtil.getObject(projectLinkOid);
	programProjectLink.setIsBase(true);
	return (KETProgramProjectLink) PersistenceHelper.manager.modify(programProjectLink);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.project.program.service.ProgramService#modifyCheckedEvent(java.lang.String)
     */
    public KETProgramProjectLink modifyCheckedEvent(String projectLinkOid) throws Exception {
	KETProgramProjectLink programProjectLink = (KETProgramProjectLink) CommonUtil.getObject(projectLinkOid);
	programProjectLink.setIsCheckedEvent(true);
	return (KETProgramProjectLink) PersistenceHelper.manager.modify(programProjectLink);
    }

    /**
     * 개정
     * 
     * @param obj
     * @param note
     * @return
     * @메소드명 : revise
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 7.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private Versioned revise(Versioned obj, String note) {
	String lifecycle = ((LifeCycleManaged) obj).getLifeCycleName();
	Folder folder = null;
	try {
	    folder = FolderHelper.service.getFolder((FolderEntry) obj);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return ObjectUtil.revise(obj, lifecycle, folder, note);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.service.CommonServiceInterface#save(java.lang.Object)
     */
    public KETProgramSchedule save(ProgramDTO paramObject) throws Exception {
	// ProgramSchedule 등록
	KETProgramSchedule programSchedule = KETProgramSchedule.newKETProgramSchedule();
	programSchedule.setNumber(this.getNewProgramNo(paramObject)); // 필수
	programSchedule.setName(paramObject.getProgramName()); // 필수
	programSchedule.setProgramName(paramObject.getProgramName()); // 프로그램명
	programSchedule.setDocType(DocumentType.getDocumentTypeDefault()); // doc type
	programSchedule.setProgramAdmin((WTUser) CommonUtil.getObject(paramObject.getProgramAdmin()));// 프로그램 관리자
	programSchedule.setCarType((OEMProjectType) CommonUtil.getObject((String) paramObject.getCarType())); // 차종
	programSchedule.setLastUsingBuyer((NumberCode) CommonUtil.getObject(paramObject.getLastUsingBuyer())); // 최종사용처
	programSchedule.setFormType(this.getFormTypeByCarType(paramObject.getCarType())); // 형태(차종에 의해 자동으로 받아온다)
	programSchedule.setDivisionCode(paramObject.getDivisionCode()); // 사업부코드
	programSchedule.setSubContractor((NumberCode) CommonUtil.getObject(paramObject.getSubContractor())); // 고객처
	if ("CA".equals(paramObject.getDivisionCode())) {
	    FolderHelper.assignLocation((FolderEntry) programSchedule, FolderUtil.getFolder("/Default/자동차사업부/프로젝트/PROGRAM"));
	} else {
	    FolderHelper.assignLocation((FolderEntry) programSchedule, FolderUtil.getFolder("/Default/전자사업부/프로젝트/PROGRAM"));
	}
	programSchedule = (KETProgramSchedule) LifeCycleHelper.setLifeCycle(programSchedule,
	        LifeCycleHelper.service.getLifeCycleTemplateReference("KET_PRODUCT_PMS_LC"));
	programSchedule = (KETProgramSchedule) PersistenceHelper.manager.save(programSchedule);
	// EventLink 생성
	this.saveProjectEventLink(paramObject, programSchedule);
	return programSchedule;
    }

    /**
     * Event Link 생성
     * 
     * @param paramObject
     * @param programSchedule
     * @throws WTException
     * @throws WTPropertyVetoException
     * @메소드명 : saveProjectEventLink
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private void saveProjectEventLink(ProgramDTO paramObject, KETProgramSchedule programSchedule) throws Exception {
	if (programSchedule == null) {
	    programSchedule = (KETProgramSchedule) CommonUtil.getObject(paramObject.getOid());
	}
	if (paramObject.getEvents() == null) {
	    throw new Exception("차종별 일정 정보가 누락되었습니다.");
	}
	// 링크정보 전부 삭제
	this.deleteEventLinkByProgramSchedule(programSchedule);
	// ProgramEvent 수정
	int i = 0;
	for (ProgramEventDTO eventDTO : paramObject.getEvents()) {
	    KETProgramEvent programEvent = KETProgramEvent.newKETProgramEvent();
	    if (!StringUtil.isEmpty(eventDTO.getCarEventOid())) {
		programEvent.setOemPlan((OEMPlan) CommonUtil.getObject(eventDTO.getCarEventOid()));
	    }else{
		//프로그램 등록 및 수정시 자동차사에 p1,p2,sop 등 일정이 없는 경우 접점고객 일정만 관리하는 경우 고객 이벤트 객체 를 set 해주기위함 2017.06.08 by 황정태 
		String oemPlanOid = this.findEventbyCarTypeAndEventName(CommonUtil.getFullOIDString(programSchedule.getCarType()), eventDTO.getCustomerEventName());
		if(!StringUtil.isEmpty(oemPlanOid)){
		    programEvent.setOemPlan((OEMPlan) CommonUtil.getObject(oemPlanOid));
		}
	    }
	    if (!StringUtil.isEmpty(eventDTO.getCustomerEventName())) {
		programEvent.setCustomerEventName(eventDTO.getCustomerEventName());
	    }
	    if (!StringUtil.isEmpty(eventDTO.getCustomerEventDate())) {
		programEvent.setCustomerEventDate(DateUtil.convertStartDate2(eventDTO.getCustomerEventDate()));
	    }
	    programEvent.setEventOrder(i++);
	    programEvent = (KETProgramEvent) PersistenceHelper.manager.save(programEvent);
	    // Link 등록
	    KETProgramEventLink programEventLink = KETProgramEventLink.newKETProgramEventLink(programEvent, programSchedule);
	    programEventLink = (KETProgramEventLink) PersistenceHelper.manager.save(programEventLink);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.project.program.service.ProgramService#saveProjectLink(ext.ket.project.program.entity.ProgramDTO)
     */
    @Override
    public void saveProjectLink(ProgramDTO paramObject, KETProgramSchedule programSchedule) throws Exception {
	if (programSchedule == null) {
	    programSchedule = (KETProgramSchedule) CommonUtil.getObject(paramObject.getOid());
	}
	this.deleteProjectLinkByProgramSchedule(programSchedule);
	if (paramObject.getProjects() != null) {
	    for (ProgramProjectDTO projectDTO : paramObject.getProjects()) {
		// ProgramProjectDTO projectDTO = paramObject.getProjects().get(i);
		ProductProject productProject = (ProductProject) CommonUtil.getObject(projectDTO.getProjectOid());
		KETProgramProjectLink projectLink = KETProgramProjectLink.newKETProgramProjectLink(productProject, programSchedule);
		if (projectDTO.getBaseProgram() != null && "1".equals(projectDTO.getBaseProgram())) {
		    projectLink.setIsBase(true);
		}
		if (projectDTO.getCheckedEvent() != null && "1".equals(projectDTO.getCheckedEvent())) {
		    projectLink.setIsCheckedEvent(true);
		}
		// Link 등록
		PersistenceHelper.manager.save(projectLink);
	    }
	    // 프로젝트 계획 시작일/종료일 업데이트
	    this.updateProgramDate(programSchedule);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.project.program.service.ProgramService#saveProjectLinkByProject(ext.ket.project.program.entity.ProgramDTO)
     */
    public void saveProjectLinkByProject(ProgramDTO paramObject) throws Exception {
	// program link 삭제
	if (paramObject.getRemovedProgramOids() != null) {
	    for (String programOid : paramObject.getRemovedProgramOids()) {
		for (ProgramProjectDTO programProjectDTO : this.findProjectByProgram(programOid)) {
		    if (programProjectDTO.getProjectOid().equals(paramObject.getProjectOid())) {
			PersistenceServerHelper.manager.remove(CommonUtil.getObject(programProjectDTO.getOid()));
		    }
		}
	    }
	}
	ProductProject productProject = (ProductProject) CommonUtil.getObject(paramObject.getProjectOid());
	// 일정 체크 및 기준프로그램 업데이트
	for (int i = 0; paramObject.getProgramOids() != null && i < paramObject.getProgramOids().size(); i++) {
	    KETProgramSchedule programSchedule = (KETProgramSchedule) CommonUtil.getObject(paramObject.getProgramOids().get(i));
	    // 프로그램에 등록된 프로젝트 link를 갖는지 확인을 위한 변수
	    boolean hasProjectLink = false;
	    // 프로그램에 등록된 프로젝트 link 정보
	    for (ProgramProjectDTO programProjectDTO : this.findProjectByProgram(paramObject.getProgramOids().get(i))) {
		if (programProjectDTO.getProjectOid().equals(paramObject.getProjectOid())) {
		    KETProgramProjectLink programProjectLink = (KETProgramProjectLink) CommonUtil.getObject(programProjectDTO.getOid());
		    // programProjectLink.setIsBase(paramObject.getBasePrograms().get(i).equals("1") ? true : false);
		    // programProjectLink.setIsCheckedEvent(paramObject.getCheckedEvents().get(i).equals("1") ? true : false);
		    PersistenceHelper.manager.save(programProjectLink);
		    hasProjectLink = true;
		    break;
		}
	    }
	    // Projectlink 정보가 없다면 추가건 이므로 추가해 준다.
	    if (!hasProjectLink) {
		KETProgramProjectLink programProjectLink = KETProgramProjectLink.newKETProgramProjectLink(productProject, programSchedule);
		// programProjectLink.setIsBase(paramObject.getBasePrograms().get(i).equals("1") ? true : false);
		// programProjectLink.setIsCheckedEvent(paramObject.getCheckedEvents().get(i).equals("1") ? true : false);
		PersistenceHelper.manager.save(programProjectLink);
	    }

	    // 프로젝트 계획 시작일/종료일 업데이트
	    this.updateProgramDate(programSchedule);
	}
    }

    /**
     * 프로그램 일정 update
     * 
     * @param programSchedule
     * @throws Exception
     * @메소드명 : updateProgramDate
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private void updateProgramDate(KETProgramSchedule programSchedule) throws Exception {
	Timestamp planStartDate = new Timestamp(new Date().getTime());
	Timestamp planEndDate = new Timestamp(new Date().getTime());
	List<ProgramProjectDTO> programProjectList = this.findProjectByProgram(CommonUtil.getFullOIDString(programSchedule));
	for (int i = 0; i < programProjectList.size(); i++) {
	    ProductProject productProject = (ProductProject) CommonUtil.getObject(programProjectList.get(i).getProjectOid());
	    ExtendScheduleData esdata = (ExtendScheduleData) productProject.getPjtSchedule().getObject();
	    // 프로젝트 계획시작일(프로젝트 계획시작일 중 가장 빠른 일정)
	    // 프로젝트 계획종료일(프로젝트 계획완료일 중 가능 늦은 일정)
	    if (i == 0) {
		planStartDate = esdata.getPlanStartDate();
		planEndDate = esdata.getPlanEndDate();
	    } else {
		if (planStartDate != null && planStartDate.getTime() > esdata.getPlanStartDate().getTime()) {
		    planStartDate = esdata.getPlanStartDate();
		}
		if (planEndDate != null && planEndDate.getTime() < esdata.getPlanEndDate().getTime()) {
		    planEndDate = esdata.getPlanEndDate();
		}
	    }
	}
	// 프로젝트 계획 시작일/종료일 업데이트
	programSchedule.setStartDate(planStartDate);
	programSchedule.setEndDate(planEndDate);
	PersistenceServerHelper.manager.update(programSchedule);

	this.updateProgramState(programSchedule, programProjectList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.project.program.service.ProgramService#updateProgramProjectLinkOfBaseProgram(java.lang.String)
     */
    public void updateProgramProjectLinkOfBaseProgram(ProgramDTO programDTO) throws Exception {
	for (ProgramDTO programProjectDTO : this.findProgramByProject(programDTO.getProjectOid())) {
	    KETProgramProjectLink projectLink = (KETProgramProjectLink) CommonUtil.getObject(programProjectDTO.getProjectLinkOid());
	    if (programProjectDTO.getProjectLinkOid().equals(programDTO.getProjectLinkOid())) {
		projectLink.setIsBase(true);
	    } else {
		projectLink.setIsBase(false);
	    }
	    PersistenceHelper.manager.modify(projectLink);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.project.program.service.ProgramService#updateProgramProjectLinkOfChangedEvent(java.lang.String)
     */
    public void updateProgramProjectLinkOfChangedEvent(ProgramDTO programDTO) throws Exception {
	for (ProgramDTO programProjectDTO : this.findProgramByProject(programDTO.getProjectOid())) {
	    KETProgramProjectLink projectLink = (KETProgramProjectLink) CommonUtil.getObject(programProjectDTO.getProjectLinkOid());
	    if (programProjectDTO.getProjectLinkOid().equals(programDTO.getProjectLinkOid())) {
		projectLink.setIsCheckedEvent(true);
		PersistenceHelper.manager.modify(projectLink);
		break;
	    }
	}
    }

    /**
     * 프로그램의 상태 업데이트
     * 
     * @param programSchedule
     * @param programProjectList
     * @throws WTException
     * @throws LifeCycleException
     * @메소드명 : updateProgramState
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private void updateProgramState(KETProgramSchedule programSchedule, List<ProgramProjectDTO> programProjectList) throws WTException,
	    LifeCycleException {
	// 상태 업데이트
	Kogger.debug(getClass(), "프로그램 상태 변경");
	String stateStr = this.getStateByProjects(programProjectList);
	if (stateStr != null) {
	    programSchedule = (KETProgramSchedule) LifeCycleHelper.service.reassign(programSchedule, LifeCycleHelper.service
		    .getLifeCycleTemplateReference("KET_PRODUCT_PMS_LC"), WTContainerRef.newWTContainerRef(WTContainerHelper.service
		    .getByPath("/wt.inf.container.OrgContainer=ket/wt.pdmlink.PDMLinkProduct=KET")));// setLifeCycleState(programSchedule,
	    LifeCycleHelper.service.setLifeCycleState(programSchedule, State.toState(this.getStateByProjects(programProjectList)));
	}
    }

    /**
     * 프로그램의 상태 업데이트
     * 
     * @param project
     * @throws Exception
     * @메소드명 : updateProgramState
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void updateProgramState(ProductProject project) throws Exception {
	List<ProgramDTO> programList = this.findProgramByProject(CommonUtil.getOIDString(project));
	for (ProgramDTO programDTO : programList) {
	    List<ProgramProjectDTO> programProjectList = this.findProjectByProgram(programDTO.getOid());
	    KETProgramSchedule programSchedule = (KETProgramSchedule) CommonUtil.getObject(programDTO.getOid());
	    this.updateProgramState(programSchedule, programProjectList);
	}
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<HashMap<String, String>> findContactEventByproject(String projectOid) throws Exception {//접점고객 P1,P2,SOP 일정 반환

	List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	if (StringUtil.isEmpty(projectOid)) {
	    return list;
	}

	QuerySpec qs = new QuerySpec();
	Persistable object = CommonUtil.getObject(projectOid);
	ProductProject project = null;
	
	if(object instanceof ProductProject){
	    project = (ProductProject) CommonUtil.getObject(projectOid);
	}
	
	if (project == null) {
	    return list;
	}
	
	long pjtOid = CommonUtil.getOIDLongValue(project);

	int idx = qs.addClassList(KETProgramProjectLink.class, false);
	int idx2 = qs.addClassList(KETProgramSchedule.class, false);
	int idx3 = qs.addClassList(KETProgramEventLink.class, false);
	int idx4 = qs.addClassList(KETProgramEvent.class, true);
	/*
	 * int idx5 = qs.addClassList(OEMPlan.class, false); int idx6 = qs.addClassList(OEMProjectType.class, false);
	 */

	SearchCondition sc = new SearchCondition(KETProgramProjectLink.class, WTAttributeNameIfc.ROLEA_OBJECT_ID, SearchCondition.EQUAL,
	        pjtOid);
	qs.appendWhere(sc, new int[] { idx });
	qs.appendAnd();

	SearchCondition sc1 = new SearchCondition(KETProgramProjectLink.class, "isBase", SearchCondition.IS_TRUE,
	        Boolean.getBoolean("true"));
	qs.appendWhere(sc1, new int[] { idx });
	qs.appendAnd();

	SearchCondition sc2 = new SearchCondition(new ClassAttribute(KETProgramProjectLink.class, WTAttributeNameIfc.ROLEB_VERSION_ID),
	        SearchCondition.EQUAL, new ClassAttribute(KETProgramSchedule.class, WTAttributeNameIfc.VERSION_ID_NAME));
	qs.appendWhere(sc2, new int[] { idx, idx2 });
	qs.appendAnd();

	SearchCondition sc3 = new SearchCondition(new ClassAttribute(KETProgramEventLink.class, WTAttributeNameIfc.ROLEB_VERSION_ID),
	        SearchCondition.EQUAL, new ClassAttribute(KETProgramSchedule.class, WTAttributeNameIfc.VERSION_ID_NAME));
	qs.appendWhere(sc3, new int[] { idx3, idx2 });
	qs.appendAnd();

	SearchCondition sc3_1 = new SearchCondition(KETProgramSchedule.class, WTAttributeNameIfc.LATEST_ITERATION, SearchCondition.IS_TRUE,
	        Boolean.getBoolean("true"));
	qs.appendWhere(sc3_1, new int[] { idx2 });
	qs.appendAnd();

	SearchCondition sc4 = new SearchCondition(new ClassAttribute(KETProgramEventLink.class, WTAttributeNameIfc.ROLEA_OBJECT_ID),
	        SearchCondition.EQUAL, new ClassAttribute(KETProgramEvent.class, WTAttributeNameIfc.ID_NAME));
	qs.appendWhere(sc4, new int[] { idx3, idx4 });
	
	String alias = qs.getFromClause().getAliasAt(idx2);
	String subQs = "(SELECT MAX(A1.VERSIONIDA2VERSIONINFO) FROM KETPROGRAMPROJECTLINK A0,KETPROGRAMSCHEDULE A1,KETPROGRAMEVENTLINK A2,KETPROGRAMEVENT A3 WHERE A0.IDA3A5 = " + pjtOid;
	subQs += " AND (A0.ISBASE = 1) AND (A0.BRANCHIDA3B5 = A1.BRANCHIDITERATIONINFO) AND (A2.BRANCHIDA3B5 = A1.BRANCHIDITERATIONINFO) AND (A1.LATESTITERATIONINFO = 1) AND (A2.IDA3A5 = A3.IDA2A2))";

	KeywordExpression kexp = new KeywordExpression(alias + "." + "VERSIONIDA2VERSIONINFO");
	KeywordExpression kexp2 = new KeywordExpression(subQs);
	
	SearchCondition sc5 = new SearchCondition(kexp, SearchCondition.EQUAL, kexp2);
	qs.appendAnd();
	qs.appendWhere(sc5, new int[] { idx2 });
	
	/*
	 * SearchCondition sc5 = new SearchCondition(new ClassAttribute(KETProgramEvent.class, "oemPlanReference.key.id"),
	 * SearchCondition.EQUAL, new ClassAttribute(OEMPlan.class, WTAttributeNameIfc.ID_NAME)); qs.appendWhere(sc5, new int[] { idx4, idx5
	 * }); qs.appendAnd();
	 * 
	 * SearchCondition sc6 = new SearchCondition(new ClassAttribute(OEMPlan.class, "oemTypeReference.key.id"), SearchCondition.EQUAL,
	 * new ClassAttribute(OEMProjectType.class, WTAttributeNameIfc.ID_NAME)); qs.appendWhere(sc6, new int[] { idx5, idx6 });
	 */

	QueryResult qr = PersistenceHelper.manager.find(qs);
	KETProgramEvent event = null;
	OEMProjectType oemType = null;
	HashMap<String, String> hash = null;
	int olevel = 0;
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    event = (KETProgramEvent) objArr[0];
	    if (event != null && event.getOemPlan() != null) {
		oemType = event.getOemPlan().getOemType();
		if (oemType != null) {

		    olevel = oemType.getOLevel();

		    if ((olevel == 0 && "SOP".equals(oemType.getName())) || olevel == 3 || olevel == 4) {
			hash = new HashMap<String, String>();
			if (olevel == 0) {
			    hash.put("sop_contact_date", DateUtil.getDateString(event.getCustomerEventDate(), "date"));
			}

			if (olevel == 3) {
			    hash.put("p1_contact_date", DateUtil.getDateString(event.getCustomerEventDate(), "date"));
			}

			if (olevel == 4) {
			    hash.put("p2_contact_date", DateUtil.getDateString(event.getCustomerEventDate(), "date"));
			}
			list.add(hash);
		    }
		}
	    }
	}

	return list;
    }
}