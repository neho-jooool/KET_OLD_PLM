package ext.ket.project.atft.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.pds.StatementSpec;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import wt.vc.Versioned;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.ManageSequence;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.PageControl;
import e3ps.edm.util.VersionHelper;
import e3ps.project.E3PSProject;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductInfo;
import e3ps.project.ProductProject;
import e3ps.project.ReviewProject;
import e3ps.project.beans.MoldProjectHelper;
import e3ps.project.beans.ProductHelper;
import e3ps.project.beans.ProductProjectHelper;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.dao.ProjectDao;
import e3ps.sap.RFCConnect;
import ext.ket.cost.code.sap.PurchasePriceInterface;
import ext.ket.cost.util.CostCalculateUtil;
import ext.ket.cost.util.CostCalculatorUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.spec.util.PartTypeEnum;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.project.atft.entity.KETATFTMainSheet;
import ext.ket.project.atft.entity.KETATFTSheetLink;
import ext.ket.project.atft.entity.KETATFTSheetTemplate;
import ext.ket.project.atft.entity.KETAtftDTO;
import ext.ket.project.atft.entity.KETProjectATFTSheetLink;
import ext.ket.shared.log.Kogger;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.Table;

public class StandardAtftService extends StandardManager implements AtftService {

    private static final long serialVersionUID = 1L;

    public static StandardAtftService newStandardAtftService() throws WTException {
	StandardAtftService instance = new StandardAtftService();
	instance.initialize();
	return instance;
    }

    @Override
    public void createAtft(KETAtftDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	String jsonData = paramObject.getAftfInfo();
	String escapedJson = StringEscapeUtils.unescapeHtml(jsonData);
	JSONTokener tokener = new JSONTokener(escapedJson);
	JSONObject jsonObject = new JSONObject(tokener);

	String projectOid = paramObject.getProjectOid();

	JSONArray aftfData = jsonObject.getJSONArray("aftfData");

	KETAtftDTO atftDto = null;
	String classification = "";
	String sheetdivision = "";
	String desision = "";
	String basis = "";

	Transaction transaction = new Transaction();
	List<KETATFTSheetTemplate> attrList = this.createSheetAttrTemplate();

	try {

	    transaction.start();

	    paramObject.setSheetdivision("P1");
	    KETATFTMainSheet atftSheet_P1 = this.createProjectSheet(paramObject);
	    paramObject.setSheetdivision("P2");
	    KETATFTMainSheet atftSheet_P2 = this.createProjectSheet(paramObject);

	    for (int i = 0; i < aftfData.length(); i++) {
		atftDto = new KETAtftDTO();

		classification = (String) aftfData.getJSONObject(i).get("classification");

		KETATFTSheetTemplate attr = this.getSheetTempalte(classification, attrList);
		if (attr == null) {
		    throw new Exception("해당 attribute는 존재하지 않습니다. => " + classification);
		}

		atftDto.setAttribute(attr);
		sheetdivision = (String) aftfData.getJSONObject(i).get("sheetdivision");
		desision = (String) aftfData.getJSONObject(i).get("desision");
		basis = (String) aftfData.getJSONObject(i).get("basis");

		atftDto.setClassification(classification);
		atftDto.setSheetdivision(sheetdivision);
		atftDto.setDesision(desision);
		atftDto.setBasis(basis);
		atftDto.setProjectOid(projectOid);

		if ("P1".equals(sheetdivision)) {
		    this.createSheetLink(attr, atftSheet_P1, atftDto);
		}

		if ("P2".equals(sheetdivision)) {
		    this.createSheetLink(attr, atftSheet_P2, atftDto);
		}

	    }

	    transaction.commit();
	    transaction = null;
	} catch (Exception e) {
	    e.printStackTrace();
	    transaction.rollback();
	    throw e;
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}

    }

    public void createSheetLink(KETATFTSheetTemplate attrTemplate, KETATFTMainSheet atftSheet, KETAtftDTO paramObject) throws Exception {

	KETATFTSheetLink SheetLink = KETATFTSheetLink.newKETATFTSheetLink(atftSheet, attrTemplate);

	SheetLink.setBasis(paramObject.getBasis());
	SheetLink.setDesision(paramObject.getDesision());
	SheetLink = (KETATFTSheetLink) PersistenceHelper.manager.save(SheetLink);

    }

    public KETATFTSheetTemplate getSheetTempalte(String attrName, List<KETATFTSheetTemplate> attrList) {// 화면에서 넘어온 attibute에 대한 템플릿을 찾는다

	KETATFTSheetTemplate target = null;
	String source = "";
	for (KETATFTSheetTemplate attr : attrList) {
	    source = attr.getClassification().replaceAll(System.getProperty("line.separator"), "");

	    if (source.equals(attrName)) {
		target = attr;
		break;
	    }
	}

	return target;
    }

    public List<KETATFTSheetTemplate> createSheetAttrTemplate() throws Exception {// attribute list 생성
	/*
	 * QuerySpec spec = new QuerySpec();
	 * 
	 * int idx = spec.appendClassList(KETATFTSheetTemplate.class, true); spec.appendWhere(new
	 * SearchCondition(KETSalesCSMeetingManageLink.class, "classification", "=", attrName), new int[] { idx }); StatementSpec Lastquery
	 * = spec; QueryResult qr = PersistenceHelper.manager.find(Lastquery); KETATFTSheetTemplate target = null;
	 * 
	 * while (qr.hasMoreElements()) { Object[] obj = (Object[]) qr.nextElement(); target = (KETATFTSheetTemplate) obj[0]; }
	 */

	QuerySpec query = new QuerySpec(KETATFTSheetTemplate.class);

	StatementSpec Lastquery = query;
	QueryResult qr = PersistenceHelper.manager.find(Lastquery);

	KETATFTSheetTemplate sheetTemp = null;
	List<KETATFTSheetTemplate> sheetTempateList = new ArrayList<KETATFTSheetTemplate>();

	while (qr.hasMoreElements()) {
	    sheetTemp = (KETATFTSheetTemplate) qr.nextElement();
	    sheetTempateList.add(sheetTemp);
	}

	return sheetTempateList;
    }

    public KETATFTMainSheet createProjectSheet(KETAtftDTO paramObject) throws Exception {
	/* KETATFTMainSheet 생성후 프로젝트와 KETATFTMainSheet를 연결한다(KETProjectATFTSheetLink) */

	String folderPath = "/Default/자동차사업부/초기유동/";

	ProductProject project = (ProductProject) CommonUtil.getObject(paramObject.getProjectOid());

	KETATFTMainSheet atftSheet = null;
	KETProjectATFTSheetLink projectSheetLink = null;

	String tempDate = DateUtil.getDateString(new Date(), "all");

	String number = project.getPjtNo() + "-" + tempDate.substring(2, 4) + "-";
	number = number + ManageSequence.getSeqNo(number, "000", "WTDOCUMENTMASTER", "WTDOCUMENTNUMBER") + "-"
	        + paramObject.getSheetdivision();

	atftSheet = KETATFTMainSheet.newKETATFTMainSheet();
	atftSheet.setName(project.getPjtNo());
	atftSheet.setNumber(number);
	atftSheet.setSheetDivision(paramObject.getSheetdivision());

	WTContainerRef containerRef = WCUtil.getWTContainerRef();

	SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	FolderHelper.assignLocation((FolderEntry) atftSheet, folder);

	atftSheet = (KETATFTMainSheet) PersistenceHelper.manager.save(atftSheet);

	projectSheetLink = KETProjectATFTSheetLink.newKETProjectATFTSheetLink(atftSheet, project);

	PersistenceHelper.manager.save(projectSheetLink);

	return atftSheet;
    }

    @Override
    public void modifyAtft(KETAtftDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	String jsonData = paramObject.getAftfInfo();
	String escapedJson = StringEscapeUtils.unescapeHtml(jsonData);
	JSONTokener tokener = new JSONTokener(escapedJson);
	JSONObject jsonObject = new JSONObject(tokener);

	JSONArray aftfData = jsonObject.getJSONArray("aftfData");

	String desision = "";
	String basis = "";
	String oid = "";
	Transaction transaction = new Transaction();

	KETATFTSheetLink sheetLink = null;

	try {

	    transaction.start();

	    for (int i = 0; i < aftfData.length(); i++) {

		oid = (String) aftfData.getJSONObject(i).get("oid");

		sheetLink = (KETATFTSheetLink) CommonUtil.getObject(oid);
		
		if(sheetLink.getMainSheet().getState().toString().equals("APPROVED")){//상태가 완료됐으면 스킵
		    continue;
		}

		desision = (String) aftfData.getJSONObject(i).get("desision");
		basis = (String) aftfData.getJSONObject(i).get("basis");

		sheetLink.setDesision(desision);
		sheetLink.setBasis(basis);
		// Iteration 증가 없이 속성 수정

		PersistenceServerHelper.manager.update(sheetLink);

	    }

	    transaction.commit();
	    transaction = null;
	} catch (Exception e) {
	    e.printStackTrace();
	    transaction.rollback();
	    throw e;
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}
    }

    @Override
    public void completeAtft(KETAtftDTO paramObject) throws Exception {
	Transaction transaction = new Transaction();
	try {

	    transaction.start();
	    // 트랜잭션이 안먹을 거 같긴함.. 완료를 P1/P2 나눠서 하게되었기 때문에 사실 transaction은 필요없긴하나 그냥 놔둠 
	    if("P1".equals(paramObject.getSheetdivision())){
		KETATFTMainSheet P1 = (KETATFTMainSheet) CommonUtil.getObject(paramObject.getP1_oid());
		LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) P1, State.toState("APPROVED"));
	    }
	    
	    if("P2".equals(paramObject.getSheetdivision())){
		KETATFTMainSheet P2 = (KETATFTMainSheet) CommonUtil.getObject(paramObject.getP2_oid());
		LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) P2, State.toState("APPROVED"));
	    }
	    

	    transaction.commit();
	    transaction = null;
	} catch (Exception e) {
	    e.printStackTrace();
	    transaction.rollback();
	    throw e;
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}
	// TODO Auto-generated method stub

    }

    public KETATFTMainSheet reviseMainSheet(KETATFTMainSheet sheet) throws Exception {
	Versioned newVs = null;
	Versioned vs = (Versioned) sheet;

	if (!VersionHelper.isLatestRevision(vs)) {
	    throw new Exception("최신 버전이 아닙니다!");
	}

	Folder folder = FolderHelper.service.getFolder((FolderEntry) vs);
	newVs = VersionControlHelper.service.newVersion(vs);
	FolderHelper.assignLocation((FolderEntry) newVs, folder);
	KETATFTMainSheet target = (KETATFTMainSheet) newVs;
	target = (KETATFTMainSheet)PersistenceHelper.manager.save(target);
	
	return target;
    }

    public void copyAttr(KETATFTSheetLink sourceLink,KETATFTMainSheet atftSheet) throws Exception {
	KETATFTSheetLink targetLink = null;
	targetLink = KETATFTSheetLink.newKETATFTSheetLink(atftSheet, sourceLink.getSheetTemplate());
	targetLink.setBasis(sourceLink.getBasis());
	targetLink.setDesision(sourceLink.getDesision());
	PersistenceHelper.manager.save(targetLink);
    }

    public void createProjectLinkNattr(KETATFTMainSheet atftSheet, KETAtftDTO paramObject) throws Exception {

	ProductProject project = (ProductProject) CommonUtil.getObject(paramObject.getProjectOid());

	KETProjectATFTSheetLink projectSheetLink = KETProjectATFTSheetLink.newKETProjectATFTSheetLink(atftSheet, project);

	PersistenceHelper.manager.save(projectSheetLink);
	List<Map<String, Object>> returnObjList = this.lastestAtftList(project.getPjtNo());
	KETATFTSheetLink sourceLink = null;
	String classKey = atftSheet.getSheetDivision();
	
	if("P1".equals(classKey)){
	    classKey = "P1_CLASSKEY";
	}
	
	if("P2".equals(classKey)){
	    classKey = "P2_CLASSKEY";
	}

	for (Map<String, Object> attr : returnObjList) {
	    sourceLink = (KETATFTSheetLink) CommonUtil.getObject((String) attr.get(classKey));
	    this.copyAttr(sourceLink, atftSheet);
	}
    }

    @Override
    public void reviseAtft(KETAtftDTO paramObject) throws Exception {
	// TODO Auto-generated method stub

	Transaction transaction = new Transaction();

	KETATFTMainSheet target = null;

	try {

	    transaction.start();

	    KETATFTMainSheet P1 = (KETATFTMainSheet) CommonUtil.getObject(paramObject.getP1_oid());
	    target = reviseMainSheet(P1);
	    this.createProjectLinkNattr(target, paramObject);

	    KETATFTMainSheet P2 = (KETATFTMainSheet) CommonUtil.getObject(paramObject.getP2_oid());
	    target = reviseMainSheet(P2);
	    this.createProjectLinkNattr(target, paramObject);

	    transaction.commit();
	    transaction = null;
	} catch (Exception e) {
	    e.printStackTrace();
	    transaction.rollback();
	    throw e;
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}

    }

    @Override
    public List<Map<String, Object>> lastestAtftList(String ProjectNo) throws Exception {
	// TODO Auto-generated method stub
	Connection conn = null;
	List<Map<String, Object>> returnObjList = null;

	try {
	    conn = PlmDBUtil.getConnection();
	    ProjectDao dao = new ProjectDao(conn);
	    HashMap<String, String> paramMap = new HashMap<String, String>();
	    paramMap.put("pjtNo", ProjectNo);
	    returnObjList = dao.searchAtftInfo(paramMap);

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    PlmDBUtil.close(conn);
	}

	return returnObjList;
    }

    @Override
    public List<KETATFTMainSheet> find(KETAtftDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PageControl findPaging(KETAtftDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public KETATFTMainSheet save(KETAtftDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public KETATFTMainSheet modify(KETAtftDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public KETATFTMainSheet delete(KETAtftDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }
    
    public void dataConfig(List<Map<String, String>> dataList, String classification, String OKtotaldecision){
	String totalState = "OK";
	String NGtotaldecision = "";
	//String OKtotaldecision = "";
	for (Map<String, String> data : dataList) {
	    if (classification.equals(data.get("classification"))) {
		if("NG".equals(data.get("state"))){
		    totalState = "NG";
		    NGtotaldecision += data.get("projectNo")+" : "+data.get("taskName")+" 미완료\r\n";
		}
//		if("OK".equals(data.get("state"))){
//		    OKtotaldecision += data.get("projectNo")+" : "+data.get("taskName")+" 완료\r\n";
//		}
	    }
	}
	
	for (Map<String, String> data : dataList) {
//	    if(data.get("taskName") != null && !"".equals(data.get("taskName")) && StringUtils.startsWith("제품측정",data.get("taskName"))){
//		continue;
//	    }
	    if (classification.equals(data.get("classification"))) {
		data.put("totalState", totalState);
		if("OK".equals(totalState)){
		    data.put("totaldecision", OKtotaldecision);    
		}
//		else{
//		    data.put("totaldecision", NGtotaldecision);
//		}
	    }
	}
	
    }
    
    public List<Map<String, String>> generateData_P1(String projectOid) throws Exception {
	
	
	QueryResult qr = ProjectHelper.getProductInfo(projectOid);
	List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
	
	while (qr.hasMoreElements()) {
	    Object o[] = (Object[]) qr.nextElement();
	    ProductInfo pi = (ProductInfo) o[0];
	    
	    if(pi == null){
		continue;
	    }
	    String partNo = pi.getPNum();
	    WTPart part = PartBaseHelper.service.getLatestPart(partNo);
	    
	    if(part == null){
		continue;
	    }
	    
	    String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType); // 제품 F, 반제품 H
	    PartTypeEnum ptype = PartTypeEnum.toPartTypeEnum(partType);
	    if (ptype == PartTypeEnum.HAWA) {
		continue;
	    }
	    
	    setCostBySap(part, dataList);
	}
	
	ProductProject project = (ProductProject) CommonUtil.getObject(projectOid);

	QueryResult rt = ProductProjectHelper.manager.getMoldItemInfo((ProductProject) project);

	
	List<Map<String, Object>> taskList = null;
	Map<String, String> data = null;

	String ProjectOid = "";

	
	String measure_state = "";
	String measure_totalState = "OK";
	String measure_decision = "";
	String measure_projectNo = "";
	String measure_projectOid = "";
	String measure_taskName = "";
	String measure_taskOid = "";
	String measure_totaldecision = "";

	while (rt.hasMoreElements()) {
	    Object[] obj = (Object[]) rt.nextElement();
	    MoldItemInfo miInfo = (MoldItemInfo) obj[0];
	    String itemType = StringUtil.checkNull(miInfo.getItemType());
	    String partNo = StringUtil.checkNull(miInfo.getPartNo());
	    String shrinkage = StringUtil.checkNull(miInfo.getShrinkage());
	    
	    if("".equals(shrinkage)){
		continue;
	    }
	    
	    if(!"신규".equals(shrinkage)){
		continue;
	    }
	    
	    if("".equals(itemType)){
		continue;
	    }
	    
	    if(!"MOLD".equals(itemType.toUpperCase()) && !"PRESS".equals(itemType.toUpperCase())){
		continue;
	    }
	    
	    WTPart part = PartBaseHelper.service.getLatestPart(partNo);
	    
	    if(part == null){
		continue;
	    }
	    
	    String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType); // 제품 F, 반제품 H
	    PartTypeEnum ptype = PartTypeEnum.toPartTypeEnum(partType);
	    if (ptype != PartTypeEnum.HALB) {
		continue;
	    }
	    
	    //String partNo = miInfo.getPartNo();
	    
	    MoldProject mProject = null;
	    if (miInfo.getDieNo() != null) {
		mProject = MoldProjectHelper.getMoldProject(miInfo.getDieNo());
	    }

	    if (mProject != null) {

		ProjectOid = CommonUtil.getOIDString(mProject);
		taskList = getTaskList(ProjectOid);

		for (Map<String, Object> task : taskList) {
		    String taskType = (String) task.get("TASKTYPE");
		    String category = (String) task.get("TASKTYPECATEGORY");
		    String ida2a2 = (String) task.get("IDA2A2");
		    String taskState = (String) task.get("TASKSTATE");
		    String taskName = (String) task.get("TASKNAME");
		    String checkResult = (String) task.get("CHECKRESULT_I");

		    if (StringUtils.startsWith(taskName, "제품측정") && "5".equals(taskState)) {
			if (checkResult.equals("합격")) {
			    measure_state = "OK";
			    measure_decision = "중요 Point 합격";
			} else if (checkResult.equals("불합격")) {
			    measure_state = "NG";
			    measure_decision = "중요 Point 불합격";

			} else {
			    measure_state = "NG";
			    measure_decision = "중요 Point 결과 미입력";

			}

			measure_projectNo = mProject.getPjtNo();
			measure_projectOid = ProjectOid;
			measure_taskName = taskName;
			measure_taskOid = ida2a2;
		    }
		}

		if ("NG".equals(measure_state) || "".equals(measure_taskOid)) {
		    //measure_totaldecision += measure_decision + "(" + mProject.getPjtNo() + ")\r\n";
		    measure_totalState = "NG";
		}

		if ("".equals(measure_taskOid)) {
		    data = new HashMap<String, String>();
		    data.put("classification", "치수");
		    data.put("state", "NG");
		    data.put("decision", "완료된 제품측정 Task 없음");
		    //data.put("totaldecision", "완료된 제품측정 Task 없음");
		    data.put("projectNo", mProject.getPjtNo());
		    data.put("projectOid", ProjectOid);
		    data.put("taskName", "");
		    data.put("taskOid", "");
		    dataList.add(data);
		} else {
		    data = new HashMap<String, String>();
		    data.put("classification", "치수");
		    data.put("state", measure_state);
		    data.put("decision", measure_decision);
		    data.put("projectNo", measure_projectNo);
		    data.put("projectOid", measure_projectOid);
		    data.put("taskName", measure_taskName);
		    data.put("taskOid", measure_taskOid);
		    dataList.add(data);
		}
		

	    }
	    measure_taskOid = "";
	}
	
/*	if("OK".equals(measure_totalState)){
	    for (Map<String, String> data_ : dataList) {
		if ("치수".equals(data_.get("classification"))) {
		    measure_totaldecision += measure_decision + "(" + data_.get("projectNo") + ")\r\n";
		}
	    }
	}*/

	for (Map<String, String> data_ : dataList) {
	    if ("치수".equals(data_.get("classification"))) {
		data_.put("totalState", measure_totalState);
		data_.put("totaldecision", measure_totaldecision);
	    }

	}

	taskList = getTaskList(projectOid);

	for (Map<String, Object> task : taskList) {
	    String taskType = (String) task.get("TASKTYPE");
	    String category = (String) task.get("TASKTYPECATEGORY");
	    String ida2a2 = (String) task.get("IDA2A2");
	    String taskState = (String) task.get("TASKSTATE");
	    String taskName = (String) task.get("TASKNAME");
	    String mainSchCode = (String) task.get("MAINSCHEDULECODE");

	    if ("MC019".equals(mainSchCode)) { // 초도품평가

		// 치수
		data = new HashMap<String, String>();
		data.put("classification", "외관");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "초도품평가Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "초도품평가Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);

		// 기능/성능
		data = new HashMap<String, String>();
		data.put("classification", "기능/성능");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "초도품평가Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "초도품평가Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);

		// 신뢰성
		data = new HashMap<String, String>();
		data.put("classification", "신뢰성");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "초도품평가Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "초도품평가Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);
	    } else if ("MC022".equals(mainSchCode)) {//All-Tool 준비
		// 사출(C/T)
		data = new HashMap<String, String>();
		data.put("classification", "사출(C/T)");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "All-Tool 준비 Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "All Tool 준비 Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);
		
		// 프레스(SPM)
		data = new HashMap<String, String>();
		data.put("classification", "프레스(SPM)");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "All Tool 준비 Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "All Tool 준비 Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);
		
		// 조립(T/T)
		data = new HashMap<String, String>();
		data.put("classification", "조립(T/T)");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "All Tool 준비 Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "All Tool 준비 Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);
		
		// Error-Proof
		data = new HashMap<String, String>();
		data.put("classification", "Error-Proof");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "All Tool 준비 Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "All Tool 준비 Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);
		
		// 공정불량
		data = new HashMap<String, String>();
		data.put("classification", "공정불량");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "All-Tool 검증 Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "All-Tool 검증 Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);
		
		// 협력사
		data = new HashMap<String, String>();
		data.put("classification", "협력사");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "구매품 검사협정서 Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "구매품 검사협정서 Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);
		
		
		// 작업자/검사자
		data = new HashMap<String, String>();
		data.put("classification", "작업자/검사자");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "구매품 검사협정서 Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "구매품 검사협정서 Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);
		
		// JIG준비(취출/조립/검사)
		data = new HashMap<String, String>();
		data.put("classification", "JIG준비(취출/조립/검사)");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "All Tool 준비회의 Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "All Tool 준비회의 Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);
		
	    } else if ("완제품 포장사양".equals(taskName)) {// 완제품 포장사양
		// 포장사양
		data = new HashMap<String, String>();
		data.put("classification", "포장사양");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "완제품 포장사양 Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "완제품 포장사양 Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);
	    } else if ("생산CAPA확보계획서".equals(taskName)) {// 생산CAPA확보계획서
		// 생산처
		data = new HashMap<String, String>();
		data.put("classification", "생산처");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "생산CAPA확보계획서 Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "생산CAPA확보계획서 Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);
	    }

	}

	dataConfig(dataList, "치수", "중요치수 합격 만족(Auto check)");
	dataConfig(dataList, "외관", "외관 만족(Auto check)");
	dataConfig(dataList, "기능/성능", "기능/성능 만족(Auto check)");
	dataConfig(dataList, "신뢰성", "기초성능평가 만족(Auto check)");
	dataConfig(dataList, "사출(C/T)", "");
	dataConfig(dataList, "프레스(SPM)", "");
	dataConfig(dataList, "조립(T/T)", "");
	dataConfig(dataList, "Error-Proof", "E/Proof 적용 계획 완료(Auto check)");
	dataConfig(dataList, "공정불량", "품질목표 수립 완료(Auto check)");
	dataConfig(dataList, "협력사", "협력사 검사협정서 체결 완료(Auto check)");
	dataConfig(dataList, "작업자/검사자", "생산자 교육계획 수립 완료(Auto check)");
	dataConfig(dataList, "JIG준비(취출/조립/검사)", "지그제작 계획 수립 완료(Auto check)");
	dataConfig(dataList, "포장사양", "완제품포장사양 고객승인 완료(Auto check)");
	dataConfig(dataList, "생산처", "생산처 확정 완료(Auto check)");
	dataConfig(dataList, "개발BOM", "ERP 등록 완료(Auto check)");
	dataConfig(dataList, "양산BOM", "ERP 등록 완료(Auto check)");
	dataConfig(dataList, "판매가", "ERP 등록 완료(Auto check)");
	dataConfig(dataList, "구매가", "ERP 등록 완료(Auto check)");
	dataConfig(dataList, "임가공가", "ERP 등록 완료(Auto check)");
	
	return dataList;
    }
    
public void setOutputBySap(WTPart part, List<Map<String, String>> dataList) throws Exception {
	
    	String partNo = part.getNumber();
	Map<String, String> data = null;

	String quantity = "";
	String plant = "";
	ArrayList<HashMap<String, Object>> outputCheckList = null;
	
	
//	String make_type = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpManufAt); // 생산처구분
//	
//	if (StringUtils.isNotEmpty(make_type)) {
//	    if ("1".equals(make_type)) {
//		String spManufacPlace = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpManufacPlace);
//		NumberCode num = NumberCodeHelper.manager.getNumberCode("PRODUCTIONDEPT", spManufacPlace);
//		plant = num.getDescription();
//	    }
//	}
//	
//	if (StringUtils.isNotEmpty(plant)) {
//	    outputCheckList = getPruductOutputBySap(partNo, plant);
//	    NumberCode num = NumberCodeHelper.manager.getNumberCode("SPECPLANT", plant);
//	    plant = num.getName();
//	} else {
//	    List<NumberCode> codeList = NumberCodeHelper.manager.getNumberCodeList("SPECPLANT");
//
//	    for (NumberCode code : codeList) {
//		outputCheckList = getPruductOutputBySap(partNo, code.getCode());
//		plant = code.getName();
//		if (outputCheckList != null) {
//		    break;
//		}
//	    }
//	}
	
	List<NumberCode> codeList = NumberCodeHelper.manager.getNumberCodeList("SPECPLANT");

	    for (NumberCode code : codeList) {
		outputCheckList = getPruductOutputBySap(partNo, code.getCode());
		plant = code.getName();
		if (outputCheckList != null) {
		    break;
		}
	    }
	
	if (outputCheckList == null) {

	} else {
	    for (HashMap<String, Object> map : outputCheckList) {
		quantity = (String) map.get("quantity");
	    }
	}
	
	String zPartNo = "";
	
	if ("".equals(quantity)) {
	    
	    if(StringUtils.contains(partNo, "-")){
		String partNoArr[] = partNo.split("-");
		zPartNo = partNoArr[0]+"Z-"+partNoArr[1];
	    }else{
		zPartNo = partNo+"Z";
	    }
	    
	    outputCheckList = getPruductOutputBySap(zPartNo, "C100");
	    plant = "중국";
	    if (outputCheckList == null) {

	    } else {
		for (HashMap<String, Object> map : outputCheckList) {
		    quantity = (String) map.get("quantity");
		}
		partNo = zPartNo;
	    }
	}
	

	if ("".equals(quantity)) {
	    
	    data = new HashMap<String, String>();
	    data.put("classification", "개발BOM");
	    data.put("state", "NG");
	    data.put("decision", partNo + " : 생산실적/구매입고 이력 없음");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);
	    
	    data = new HashMap<String, String>();
	    data.put("classification", "양산BOM");
	    data.put("state", "NG");
	    data.put("decision", partNo + " : 생산실적/구매입고 이력 없음");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);

	    data = new HashMap<String, String>();
	    data.put("classification", "판매가");
	    data.put("state", "NG");
	    data.put("decision", partNo + " : 생산실적/구매입고 이력 없음");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);

	    data = new HashMap<String, String>();
	    data.put("classification", "구매가");
	    data.put("state", "NG");
	    data.put("decision", partNo + " : 생산실적/구매입고 이력 없음");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);

	    data = new HashMap<String, String>();
	    data.put("classification", "임가공가");
	    data.put("state", "NG");
	    data.put("decision", partNo + " : 생산실적/구매입고 이력 없음");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);
	} else {
	    data = new HashMap<String, String>();
	    data.put("classification", "개발BOM");
	    data.put("state", "OK");
	    data.put("decision", partNo + " 생산실적/구매입고 이력 있음 (플랜트 : "+plant+")");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);
	    
	    data = new HashMap<String, String>();
	    data.put("classification", "양산BOM");
	    data.put("state", "OK");
	    data.put("decision", partNo + " 생산실적/구매입고 이력 있음 (플랜트 : "+plant+")");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);

	    data = new HashMap<String, String>();
	    data.put("classification", "판매가");
	    data.put("state", "OK");
	    data.put("decision", partNo + " 생산실적/구매입고 이력 있음 (플랜트 : "+plant+")");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);

	    data = new HashMap<String, String>();
	    data.put("classification", "구매가");
	    data.put("state", "OK");
	    data.put("decision", partNo + " 생산실적/구매입고 이력 있음 (플랜트 : "+plant+")");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);

	    data = new HashMap<String, String>();
	    data.put("classification", "임가공가");
	    data.put("state", "OK");
	    data.put("decision", partNo + " 생산실적/구매입고 이력 있음 (플랜트 : "+plant+")");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);
	}
    }

    public boolean checkStd(String std) throws Exception {
	ScriptEngineManager mgr = new ScriptEngineManager();
	ScriptEngine engine = mgr.getEngineByName("javascript");

	String check = engine.eval(std).toString();
	return Boolean.valueOf(check);
    }
    
    public void setCostBySap(WTPart part, List<Map<String, String>> dataList) throws Exception {
	
	String partNo = part.getNumber();
	
	Map<String, String> data = null;

	String cost = "";
	String plant = "";
	ArrayList<HashMap<String, Object>> purchaseList = null;
	
//	String make_type = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpManufAt); // 생산처구분
//	
//	if (StringUtils.isNotEmpty(make_type)) {
//	    if ("1".equals(make_type)) {//사내
//		String spManufacPlace = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpManufacPlace);
//		NumberCode num = NumberCodeHelper.manager.getNumberCode("PRODUCTIONDEPT", spManufacPlace);
//		plant = num.getDescription();
//	    }
//	}
//	
//	if (StringUtils.isNotEmpty(plant)) {
//	    purchaseList = getCostPriceBySap(partNo, plant);
//	    NumberCode num = NumberCodeHelper.manager.getNumberCode("SPECPLANT", plant);
//	    plant = num.getName();
//	} else {
//	    List<NumberCode> codeList = NumberCodeHelper.manager.getNumberCodeList("SPECPLANT");
//
//	    for (NumberCode code : codeList) {
//		purchaseList = getCostPriceBySap(partNo, code.getCode());
//		plant = code.getName();
//		if (purchaseList != null) {
//		    break;
//		}
//	    }
//	}
	
	List<NumberCode> codeList = NumberCodeHelper.manager.getNumberCodeList("SPECPLANT");

	for (NumberCode code : codeList) {
	    if("7000".equals(code.getCode())){//7000은 제외 차주원 차장 요청 2019.09.23 by 황정태
		continue;
	    }
	    purchaseList = getCostPriceBySap(partNo, code.getCode());
	    plant = code.getName();
	    if (purchaseList != null) {
		
		
		for (HashMap<String, Object> map : purchaseList) {
		    String vprsv = (String) map.get("vprsv");
		    if ("S".equals(vprsv)) {
			cost = (String) map.get("stprs");
		    } else if ("V".equals(vprsv)) {
			cost = (String) map.get("verpr");
		    }
		}
		
		if (StringUtils.isNotEmpty(cost)) {
		    String std = "if (" + StringUtil.checkNullZero(cost) + " > 0){ true; } else { false; }";
		    if (!checkStd(std)){
			cost = "";
		    }
		}
		if(StringUtils.isNotEmpty(cost)){
		    break;    
		}
		
	    }
	}

	
	String zPartNo = "";
	
	if ("".equals(cost)) {
	    
	    if(StringUtils.contains(partNo, "-")){
		String partNoArr[] = partNo.split("-");
		zPartNo = partNoArr[0]+"Z-"+partNoArr[1];
	    }else{
		zPartNo = partNo+"Z";
	    }
	    
	    purchaseList = getCostPriceBySap(zPartNo, "C100");
	    plant = "중국";
	    if (purchaseList == null) {

	    } else {
		for (HashMap<String, Object> map : purchaseList) {
		    String vprsv = (String) map.get("vprsv");
		    if ("S".equals(vprsv)) {
			cost = (String) map.get("stprs");
		    } else if ("V".equals(vprsv)) {
			cost = (String) map.get("verpr");
		    }
		}
		partNo = zPartNo;
	    }
	}
	
	if (StringUtils.isNotEmpty(cost)) {
	    String std = "if (" + StringUtil.checkNullZero(cost) + " > 0){ true; } else { false; }";
	    if (!checkStd(std)){
		cost = "";
	    }
	}
	

	if ("".equals(cost)) {
	    
	    data = new HashMap<String, String>();
	    data.put("classification", "개발BOM");
	    data.put("state", "NG");
	    data.put("decision", partNo + " : 표준원가 없음");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);
	    
	    data = new HashMap<String, String>();
	    data.put("classification", "양산BOM");
	    data.put("state", "NG");
	    data.put("decision", partNo + " : 표준원가 없음");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);

	    data = new HashMap<String, String>();
	    data.put("classification", "판매가");
	    data.put("state", "NG");
	    data.put("decision", partNo + " : 표준원가 없음");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);

	    data = new HashMap<String, String>();
	    data.put("classification", "구매가");
	    data.put("state", "NG");
	    data.put("decision", partNo + " : 표준원가 없음");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);

	    data = new HashMap<String, String>();
	    data.put("classification", "임가공가");
	    data.put("state", "NG");
	    data.put("decision", partNo + " : 표준원가 없음");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);
	} else {
	    data = new HashMap<String, String>();
	    data.put("classification", "개발BOM");
	    data.put("state", "OK");
	    data.put("decision", partNo + " 표준원가 등록됨 (플랜트 : "+plant+")");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);
	    
	    data = new HashMap<String, String>();
	    data.put("classification", "양산BOM");
	    data.put("state", "OK");
	    data.put("decision", partNo + " 표준원가 등록됨 (플랜트 : "+plant+")");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);

	    data = new HashMap<String, String>();
	    data.put("classification", "판매가");
	    data.put("state", "OK");
	    data.put("decision", partNo + " 표준원가 등록됨 (플랜트 : "+plant+")");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);

	    data = new HashMap<String, String>();
	    data.put("classification", "구매가");
	    data.put("state", "OK");
	    data.put("decision", partNo + " 표준원가 등록됨 (플랜트 : "+plant+")");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);

	    data = new HashMap<String, String>();
	    data.put("classification", "임가공가");
	    data.put("state", "OK");
	    data.put("decision", partNo + " 표준원가 등록됨 (플랜트 : "+plant+")");
	    data.put("projectNo", "");
	    data.put("projectOid","");
	    data.put("taskName", "");
	    data.put("taskOid", "");
	    dataList.add(data);
	}
    }
    
    public List<Map<String, String>> generateData_P2(String projectOid) throws Exception {
	ProductProject project = (ProductProject) CommonUtil.getObject(projectOid);
	
	QueryResult qr = ProjectHelper.getProductInfo(projectOid);
	List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
	
	while (qr.hasMoreElements()) {
	    Object o[] = (Object[]) qr.nextElement();
	    ProductInfo pi = (ProductInfo) o[0];
	    
	    if(pi == null){
		continue;
	    }
	    String partNo = pi.getPNum();
	    WTPart part = PartBaseHelper.service.getLatestPart(partNo);
	    
	    if(part == null){
		continue;
	    }
	    
	    String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType); // 제품 F, 반제품 H
	    PartTypeEnum ptype = PartTypeEnum.toPartTypeEnum(partType);
	    if (ptype == PartTypeEnum.HAWA) {
		continue;
	    }
	    
	    setOutputBySap(part, dataList);
	}
	
	QueryResult rt = ProductProjectHelper.manager.getMoldItemInfo((ProductProject) project);

	List<Map<String, Object>> taskList = null;
	Map<String, String> data = null;

	String ProjectOid = "";

	
	String measure_state = "";
	String measure_totalState = "OK";
	String measure_decision = "";
	String measure_projectNo = "";
	String measure_projectOid = "";
	String measure_taskName = "";
	String measure_taskOid = "";
	String measure_totaldecision = "";

	while (rt.hasMoreElements()) {
	    Object[] obj = (Object[]) rt.nextElement();
	    MoldItemInfo miInfo = (MoldItemInfo) obj[0];
	    
	    String itemType = StringUtil.checkNull(miInfo.getItemType());
	    String partNo = StringUtil.checkNull(miInfo.getPartNo());
	    String shrinkage = StringUtil.checkNull(miInfo.getShrinkage());
	    
	    if("".equals(shrinkage)){
		continue;
	    }
	    
	    if(!"신규".equals(shrinkage)){
		continue;
	    }
	    
	    if("".equals(itemType)){
		continue;
	    }
	    
	    if(!"MOLD".equals(itemType.toUpperCase()) && !"PRESS".equals(itemType.toUpperCase())){
		continue;
	    }
	    
	    WTPart part = PartBaseHelper.service.getLatestPart(partNo);
	    
	    if(part == null){
		continue;
	    }
	    
	    String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType); // 제품 F, 반제품 H
	    PartTypeEnum ptype = PartTypeEnum.toPartTypeEnum(partType);
	    if (ptype != PartTypeEnum.HALB) {
		continue;
	    }
	    
	    MoldProject mProject = null;
	    if (miInfo.getDieNo() != null) {
		mProject = MoldProjectHelper.getMoldProject(miInfo.getDieNo());
	    }

	    if (mProject != null) {

		ProjectOid = CommonUtil.getOIDString(mProject);
		taskList = getTaskList(ProjectOid);

		for (Map<String, Object> task : taskList) {
		    String taskType = (String) task.get("TASKTYPE");
		    String category = (String) task.get("TASKTYPECATEGORY");
		    String ida2a2 = (String) task.get("IDA2A2");
		    String taskState = (String) task.get("TASKSTATE");
		    String taskName = (String) task.get("TASKNAME");
		    String checkResult = (String) task.get("CHECKRESULT");

		    if (StringUtils.startsWith(taskName, "제품측정") && "5".equals(taskState)) {
			
			if (checkResult.equals("합격")) {
			    measure_state = "OK";
			    measure_decision = "전 Point 합격";
			} else if (checkResult.equals("불합격")) {
			    measure_state = "NG";
			    measure_decision = "전 Point 불합격";

			} else {
			    measure_state = "NG";
			    measure_decision = "전 Point 결과 미입력";

			}

			measure_projectNo = mProject.getPjtNo();
			measure_projectOid = ProjectOid;
			measure_taskName = taskName;
			measure_taskOid = ida2a2;
		    }

		}

		if ("NG".equals(measure_state) || "".equals(measure_taskOid)) {
		    //measure_totaldecision += measure_decision + "(" + mProject.getPjtNo() + ")\r\n";
		    measure_totalState = "NG";
		}
		

		if ("".equals(measure_taskOid)) {
		    data = new HashMap<String, String>();
		    data.put("classification", "치수");
		    data.put("state", "NG");
		    data.put("decision", "완료된 제품측정 Task 없음");
		    //data.put("totaldecision", "완료된 제품측정 Task 없음");
		    data.put("projectNo", mProject.getPjtNo());
		    data.put("projectOid",ProjectOid);
		    data.put("taskName", "");
		    data.put("taskOid", "");
		    dataList.add(data);
		} else {
		    data = new HashMap<String, String>();
		    data.put("classification", "치수");
		    data.put("state", measure_state);
		    data.put("decision", measure_decision);
		    data.put("projectNo", measure_projectNo);
		    data.put("projectOid", measure_projectOid);
		    data.put("taskName", measure_taskName);
		    data.put("taskOid", measure_taskOid);
		    dataList.add(data);
		}
		

	    }
	    measure_taskOid = "";
	}

/*	if("OK".equals(measure_totalState)){
	    for (Map<String, String> data_ : dataList) {
		if ("치수".equals(data_.get("classification"))) {
		    measure_totaldecision += measure_decision + "(" + data_.get("projectNo") + ")\r\n";
		}
	    }
	}*/

	for (Map<String, String> data_ : dataList) {
	    if ("치수".equals(data_.get("classification"))) {
		data_.put("totalState", measure_totalState);
		data_.put("totaldecision", measure_totaldecision);
	    }
	}

	taskList = getTaskList(projectOid);

	for (Map<String, Object> task : taskList) {
	    String taskType = (String) task.get("TASKTYPE");
	    String category = (String) task.get("TASKTYPECATEGORY");
	    String ida2a2 = (String) task.get("IDA2A2");
	    String taskState = (String) task.get("TASKSTATE");
	    String taskName = (String) task.get("TASKNAME");
	    String mainSchCode = (String) task.get("MAINSCHEDULECODE");

	    if ("MC032".equals(mainSchCode)) { // 제품합격

		// 치수
		data = new HashMap<String, String>();
		data.put("classification", "치수");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "제품합격Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "제품합격Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);

		// 외관
		data = new HashMap<String, String>();
		data.put("classification", "외관");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "제품합격Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "제품합격Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);

		// 기능/성능
		data = new HashMap<String, String>();
		data.put("classification", "기능/성능");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "제품합격Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "제품합격Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);
	    } else if ("PV".equals(category) || "MC028".equals(mainSchCode)) {
		// 신뢰성
		data = new HashMap<String, String>();
		data.put("classification", "신뢰성");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "신뢰성평가(PV)Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "신뢰성평가(PV)Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);
	    } else if ("MC031".equals(mainSchCode)) {// 양산유효성평가(DR5)
		// 사출(C/T)
		data = new HashMap<String, String>();
		data.put("classification", "사출(C/T)");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "양산유효성평가(DR5)Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "양산유효성평가(DR5)Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);

		// 프레스(SPM)
		data = new HashMap<String, String>();
		data.put("classification", "프레스(SPM)");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "양산유효성평가(DR5)Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "양산유효성평가(DR5)Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);

		// 조립(T/T)
		data = new HashMap<String, String>();
		data.put("classification", "조립(T/T)");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "양산유효성평가(DR5)Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "양산유효성평가(DR5)Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);

		// Error-Proof
		data = new HashMap<String, String>();
		data.put("classification", "Error-Proof");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "양산유효성평가(DR5)Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "양산유효성평가(DR5)Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);

		// 공정불량
		data = new HashMap<String, String>();
		data.put("classification", "공정불량");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "양산유효성평가(DR5)Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "양산유효성평가(DR5)Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);

		// 작업자/검사자
		data = new HashMap<String, String>();
		data.put("classification", "작업자/검사자");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "양산유효성평가(DR5)Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "양산유효성평가(DR5)Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);

		// JIG준비(취출/조립/검사)
		data = new HashMap<String, String>();
		data.put("classification", "JIG준비(취출/조립/검사)");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "양산유효성평가(DR5)Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "양산유효성평가(DR5)Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);

		// 포장사양
		data = new HashMap<String, String>();
		data.put("classification", "포장사양");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "양산유효성평가(DR5)Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "양산유효성평가(DR5)Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);

		// 생산처
		data = new HashMap<String, String>();
		data.put("classification", "생산처");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "양산유효성평가(DR5)Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "양산유효성평가(DR5)Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);
	    } else if ("MC033".equals(mainSchCode)) {// 협력사 ISIR승인
		// 협력사
		data = new HashMap<String, String>();
		data.put("classification", "협력사");

		if ("5".equals(taskState)) {
		    data.put("state", "OK");
		    data.put("decision", "협력사 ISIR승인 Task 완료");
		} else {
		    data.put("state", "NG");
		    data.put("decision", "협력사 ISIR승인 Task 미완료");
		}

		data.put("projectNo", project.getPjtNo());
		data.put("projectOid", projectOid);
		data.put("taskName", taskName);
		data.put("taskOid", ida2a2);
		dataList.add(data);
	    }

	}
	
	dataConfig(dataList, "치수", "전치수 합격 만족(Auto check)");
	dataConfig(dataList, "외관", "마스터샘플 설정 완료(Auto check)");
	dataConfig(dataList, "기능/성능", "기능/성능 만족(Auto check)");
	dataConfig(dataList, "신뢰성", "PV평가 만족(Auto check)");
	dataConfig(dataList, "사출(C/T)", "계획 대비 실적 만족(Auto check)");
	dataConfig(dataList, "프레스(SPM)", "계획 대비 실적 만족(Auto check)");
	dataConfig(dataList, "조립(T/T)", "계획 대비 실적 만족(Auto check)");
	dataConfig(dataList, "Error-Proof", "E/Proof 적용 계획 완료(Auto check)");
	dataConfig(dataList, "공정불량", "계획 대비 실적 만족(Auto check)");
	dataConfig(dataList, "협력사", "협력사ISIR 승인 완료(Auto check)");
	dataConfig(dataList, "작업자/검사자", "생산자 교육실시 완료(Auto check)");
	dataConfig(dataList, "JIG준비(취출/조립/검사)", "지그제작 완료(Auto check)");
	dataConfig(dataList, "포장사양", "완제품포장사양 제작 완료(Auto check)");
	dataConfig(dataList, "생산처", "생산처 확정 완료(Auto check)");
	dataConfig(dataList, "개발BOM", "ERP 등록 완료(Auto check)");
	dataConfig(dataList, "양산BOM", "ERP 등록 완료(Auto check)");
	dataConfig(dataList, "판매가", "ERP 등록 완료(Auto check)");
	dataConfig(dataList, "구매가", "ERP 등록 완료(Auto check)");
	dataConfig(dataList, "임가공가", "ERP 등록 완료(Auto check)");
	
	return dataList;
    }

    @Override
    public List<Map<String, String>> autoCheckData(String projectOid, String sheetdivision) throws Exception {
	// TODO Auto-generated method stub
	
	List<Map<String, String>> dataList = null;
	
	if("P1".equals(sheetdivision)){
	    dataList = generateData_P1(projectOid);
	}else if("P2".equals(sheetdivision)){
	    dataList = generateData_P2(projectOid);
	}
	
	return dataList;
    }
    
    public List<Map<String, Object>> getTaskList(String ProjectOid) throws Exception{
	E3PSProject project = (E3PSProject) CommonUtil.getObject(ProjectOid);

	String targetPjt = "";
	

	if (project instanceof ReviewProject) {
		targetPjt = "ReviewProject";
	} else if (project instanceof ProductProject) {
		targetPjt = "ProductProject";
	} else if (project instanceof MoldProject) {
		targetPjt = "MoldProject";
	}

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	List<Map<String, Object>> taskList = new ArrayList<Map<String, Object>>();

	try {

		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT TASKNAME,TASKTYPE,TASKTYPECATEGORY,'e3ps.project.E3PSTask:'||IDA2A2 AS IDA2A2,TASKSTATE,CHECKRESULT,MAINSCHEDULECODE, CHECKRESULT_I             \n");
		sb.append("   FROM (SELECT T.* FROM E3PSTASK T," + targetPjt + " P,EXTENDSCHEDULEDATA S         \n");
		sb.append("           WHERE T.IDA3B4 = P.IDA2A2                                           \n");
		sb.append("             AND T.IDA3A4 = S.IDA2A2                                           \n");
		sb.append("         START WITH T.IDA3PARENTREFERENCE = 0                                  \n");
		sb.append("             AND P.IDA2A2 = " + CommonUtil.getOIDLongValue(ProjectOid) + "         \n");
		sb.append("         CONNECT BY PRIOR T.IDA2A2 = T.IDA3PARENTREFERENCE                     \n");
		sb.append("         ORDER SIBLINGS BY T.TASKSEQ)                                          \n");

		String query = sb.toString();

		taskList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
			@Override
			public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

				Map<String, Object> taskMap = new HashMap<String, Object>();

				taskMap.put("TASKNAME", rs.getString("TASKNAME"));
				taskMap.put("TASKTYPE", StringUtil.checkNull(rs.getString("TASKTYPE")));
				taskMap.put("TASKTYPECATEGORY", StringUtil.checkNull(rs.getString("TASKTYPECATEGORY")));
				taskMap.put("IDA2A2", StringUtil.checkNull(rs.getString("IDA2A2")));
				taskMap.put("TASKSTATE", StringUtil.checkNull(rs.getString("TASKSTATE")));
				taskMap.put("CHECKRESULT", StringUtil.checkNull(rs.getString("CHECKRESULT")));
				taskMap.put("MAINSCHEDULECODE", StringUtil.checkNull(rs.getString("MAINSCHEDULECODE")));
				taskMap.put("CHECKRESULT_I", StringUtil.checkNull(rs.getString("CHECKRESULT_I")));

				return taskMap;
			}
		});

	} catch (Exception e) {
		throw e;
	} finally {

	}
	
	return taskList;
    }
    
    
    public ArrayList<HashMap<String, Object>> getPruductOutputBySap(String partNo, String plant) throws Exception{
	Client client = null;
	IRepository repository = null;
	ArrayList<HashMap<String, Object>> outputCheckList = null;
	HashMap<String, Object> output = null;
	
	try {
	    client = RFCConnect.getConnection();
	    client.connect();

	    repository = JCO.createRepository("BFREPOSITORY", client);

	    IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_SELECT_ST_GR_CHECK");
	    Function function = tmpl.getFunction();
	    
	    if(!"".equals(partNo) && !"".equals(plant)){
		function.getImportParameterList().setValue(partNo, "I_MATNR");
		function.getImportParameterList().setValue(plant, "I_WERKS");
	    }
	    
	    Table tables = function.getTableParameterList().getTable("IT_LIST");

	    try {
		client.execute(function);
	    } catch (Exception e) {
		Kogger.debug(PurchasePriceInterface.class, "Interface[getPruductOutputBySap]>>> " + e.toString());
	    }

	    String E_RETURN = (String) function.getExportParameterList().getValue("E_RETURN");
	    String E_MSG = (String) function.getExportParameterList().getValue("E_MSG");
	    
	    System.out.println("E_RETURN : "+E_RETURN);
	    System.out.println("E_MSG : "+E_MSG);
	    
	    if ("S".equals(E_RETURN)) {
		outputCheckList = new ArrayList<HashMap<String, Object>>();
		for (int j = 0; j < tables.getNumRows(); j++) {
		    tables.setRow(j);

		    output = new HashMap<String, Object>();
		    output.put("plant", StringUtil.checkNull(tables.getString("WERKS"))); // 평가영역(플랜트)
		    output.put("partNo", StringUtil.checkNull(tables.getString("MATNR"))); // 자재번호
		    output.put("spmon", StringUtil.checkNull(tables.getString("SPMON"))); // 분석기간 월(YYYYMM)
		    output.put("sptag", StringUtil.checkNull(tables.getString("SPTAG"))); // 분석기간 일자(YYYYMMDD)
		    output.put("unit", StringUtil.checkNull(tables.getString("MEINS"))); // 단위
		    output.put("quantity", StringUtil.checkNull(tables.getString("WEMNG"))); // 입고수량

		    outputCheckList.add(output);
		}
	    }
	    
	} catch (Exception e) {
	    Kogger.error(PurchasePriceInterface.class, e);
	    throw e;
	} finally {
	    client.disconnect();
	    repository = null;
	}
	
	return outputCheckList;
    }
    
    @Override
    public ArrayList<HashMap<String, Object>> getCostPriceBySap(String partNo, String plant) throws Exception{
	Client client = null;
	IRepository repository = null;
	ArrayList<HashMap<String, Object>> purchaseList = null;
	HashMap<String, Object> purchase = null;
	
	try {
	    client = RFCConnect.getConnection();
	    client.connect();

	    repository = JCO.createRepository("BFREPOSITORY", client);

	    IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_SELECT_ST_COST_PRICE");
	    Function function = tmpl.getFunction();
	    
	    if(!"".equals(partNo) && !"".equals(plant)){
		function.getImportParameterList().setValue(partNo, "I_MATNR");
		function.getImportParameterList().setValue(plant, "I_WERKS");
	    }
	    
	    Table tables = function.getTableParameterList().getTable("IT_LIST");

	    try {
		client.execute(function);
	    } catch (Exception e) {
		Kogger.debug(PurchasePriceInterface.class, "Interface[getCostPriceBySap]>>> " + e.toString());
	    }

	    String E_RETURN = (String) function.getExportParameterList().getValue("E_RETURN");
	    String E_MSG = (String) function.getExportParameterList().getValue("E_MSG");
	    
	    System.out.println("E_RETURN : "+E_RETURN);
	    System.out.println("E_MSG : "+E_MSG);
	    
	    if ("S".equals(E_RETURN)) {
		purchaseList = new ArrayList<HashMap<String, Object>>();
		for (int j = 0; j < tables.getNumRows(); j++) {
		    tables.setRow(j);

		    purchase = new HashMap<String, Object>();
		    purchase.put("plant", StringUtil.checkNull(tables.getString("BWKEY"))); // 평가영역(플랜트)
		    purchase.put("partNo", StringUtil.checkNull(tables.getString("MATNR"))); // 자재번호
		    purchase.put("vprsv", StringUtil.checkNull(tables.getString("VPRSV"))); // 가격관리지시자
		    purchase.put("verpr", StringUtil.checkNull(tables.getString("VERPR"))); // 이동평균가
		    
		    String stprs = StringUtil.checkNullZero(tables.getString("STPRS"));
		    String peinh = StringUtil.checkNullZero(tables.getString("PEINH"));
		    
		    stprs = (String)CostCalculatorUtil.eval("("+stprs + " * 100) / "+peinh);
		    
		    purchase.put("stprs", stprs); // 표준원가
		    purchase.put("currency", StringUtil.checkNull(tables.getString("WAERS"))); // 통화
		    purchase.put("unit", peinh); // 단위

		    purchaseList.add(purchase);
		}
	    }
	    
	} catch (Exception e) {
	    Kogger.error(PurchasePriceInterface.class, e);
	    throw e;
	} finally {
	    client.disconnect();
	    repository = null;
	}
	
	return purchaseList;
    }

}
