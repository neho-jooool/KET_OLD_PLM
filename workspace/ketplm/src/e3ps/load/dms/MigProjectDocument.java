package e3ps.load.dms;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentRoleType;
import wt.doc.WTDocumentMasterIdentity;
import wt.fc.Identified;
import wt.fc.IdentityHelper;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.ownership.Ownership;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pds.StatementSpec;
import wt.pom.DBProperties;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.util.WTProperties;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.JExcelUtil;
import e3ps.common.util.ManageSequence;
import e3ps.common.util.WCUtil;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETDocumentOutputLink;
import e3ps.dms.entity.KETDocumentPartLink;
import e3ps.dms.entity.KETDocumentProjectLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.service.KETDmsHelper;
import e3ps.ecm.beans.EcmUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;
import e3ps.load.migration.edm.log.LogToFile;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ProjectOutput;
import e3ps.project.beans.MoldProjectHelper;
import e3ps.project.beans.ProjectOutputHelper;
import ext.ket.dms.entity.ProjectDocumentDTO;
import ext.ket.dms.service.KETProjectDocumentHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.SearchUtil;

public class MigProjectDocument implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    private static String FILESERVER = "\\\\192.168.17.13";
    private static String EXCELFILE = "";
    private static String SEPARATOR = File.separator;
    private static String logFile = "projectDocumentLoader.log";
    private static String NumberCodeOid = "";

    static {
	try {
	    String wt_home = WTProperties.getLocalProperties().getProperty("wt.home");
	    EXCELFILE = wt_home + SEPARATOR + "loadFiles\\ket\\migration\\dms";
	    FILESERVER = wt_home + SEPARATOR + "loadFiles\\ket\\migration\\dms";
	} catch (Exception e) {
	    Kogger.error(ProjectDocumentDataLoader.class, e);
	}
    }
    public static MigProjectDocument manager = new MigProjectDocument();

    public MigProjectDocument() {

    }

    // windchill e3ps.load.dms.MigProjectDocument TEST.xlsx
    public static void main(String[] args) {

	try {

	    String fileName = null;
	    String delCategoryCode = null;

	    if (args == null || args.length < 1)
		throw new Exception("@@ args need !");
	    else {
		fileName = args[0];
	    }

	    if (args.length > 1) {
		delCategoryCode = args[1];
	    }

	    Kogger.debug(MigProjectDocument.class, "@start");
	    MigProjectDocument.manager.saveFromExcel(fileName, delCategoryCode);
	    Kogger.debug(MigProjectDocument.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigProjectDocument.class, e);
	}
    }

    public void saveFromExcel(String fileName, String delCategoryCode) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class, String.class };
		Object aobj[] = { fileName, delCategoryCode };

		Kogger.debug(getClass(), "@		start");
		RemoteMethodServer.getDefault().invoke("saveFromExcel", null, this, aclass, aobj);
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
	    executeMigration(fileName, delCategoryCode);
	}
    }

    public void executeMigration(String fileName, String delCategoryCode) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	ReferenceFactory rf = new ReferenceFactory();
	Transaction trx = null;
	try {

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    trx = new Transaction();
	    trx.start();
	    
	    NumberCode number = NumberCodeHelper.manager.getNumberCode("VERSION", "LATEST");
	    NumberCodeOid = CommonUtil.getOIDLongValue2Str(number);
	    
	    loadFile(fileName, delCategoryCode);

	    trx.commit();

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    public void deletPartLinkByCategory(String delCategoryCode) throws Exception {// 특정 분류체계에 속한 문서들의 부품 링크를 제거한다(전자사업부 제외)

	ProjectDocumentDTO projectDocumentDTO = new ProjectDocumentDTO();

	projectDocumentDTO.setProjectDocType(delCategoryCode);// categorycode
	
	projectDocumentDTO.setVersion(NumberCodeOid);//최신 문서만 가져오도록 한다

	List<KETProjectDocument> list = getDocumentResult(projectDocumentDTO);
	
	//String currentYear = DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(),"YYYY");
	

	for (KETProjectDocument doc : list) {
	    
	    boolean isExcept = false;

	    String modifyYear = DateUtil.getTimeFormat(doc.getModifyTimestamp() , "YYYY");
	    
	    isExcept = "2018".equals(modifyYear) || "2019".equals(modifyYear);
	    
	    WTUser Creator =  (WTUser)doc.getCreator().getPrincipal();
	    Department dept = DepartmentHelper.manager.getRootDepartment(Creator);
	    String deptName = "";
	    
	    if(dept != null){
		deptName = dept.getName();
	    }
	    
	    if (isExcept || "ER".equals(doc.getDivisionCode()) || !doc.getNumber().startsWith("PD-") || !deptName.startsWith("한국단자")) {// 2019년도 문서 제외, 전자사업부 제외, prefix가 PD- 가 아닌 것은 제외, 한국단자공업의 문서가 아니면 제외
		continue;
	    }
	    QueryResult r = PersistenceHelper.manager.navigate(doc, "part", KETDocumentPartLink.class, false);

	    while (r.hasMoreElements()) {
		// KETContentHelper.service.delete(doc);
		KETDocumentPartLink link = (KETDocumentPartLink) r.nextElement();
		PersistenceHelper.manager.delete(link);
	    }
	}
    }

    public List<KETProjectDocument> getDocumentResult(ProjectDocumentDTO projectDocumentDTO) throws Exception {

	// KETProjectDocument spec
	List<KETProjectDocument> list = null;
	StatementSpec query = KETProjectDocumentHelper.service.getListProjectDocumentQuerySpec(projectDocumentDTO);

	if (!query.isAdvancedQueryEnabled())
	    query.setAdvancedQueryEnabled(true);

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

    public boolean loadFile(String fileName, String delCategoryCode) throws Exception {

	String filePath = EXCELFILE + SEPARATOR + fileName;
	File dataFile = new File(filePath);
	Kogger.debug(ProjectDocumentDataLoader.class, "file==>" + filePath);
	if (!dataFile.exists()) {
	    Kogger.debug(ProjectDocumentDataLoader.class, filePath + "==>File not found!!!");
	    return false;
	}

	logFile = EXCELFILE + SEPARATOR;
	int pidx = fileName.lastIndexOf(".");
	if (pidx > 0) {
	    logFile += fileName.substring(0, pidx);
	} else {
	    logFile += fileName;
	}
	logFile += ".log";
	Kogger.debug(ProjectDocumentDataLoader.class, "logFile==>" + logFile);
	if (DRMHelper.useDrm) {
	    dataFile = DRMHelper.Decryptupload(dataFile, dataFile.getName());
	}
	Workbook wb = Workbook.getWorkbook(dataFile);
	Sheet sheets[] = wb.getSheets();
	Kogger.debug(ProjectDocumentDataLoader.class, "sheets==>" + sheets.length);
	Sheet sheet2 = sheets[1];
	String categoryCode = "";
	String categoryPath = "";
	HashMap categoryCodes = new HashMap();

	for (int i = 0; i < sheet2.getRows(); i++) {
	    Cell[] cell = sheet2.getRow(i);
	    categoryPath = JExcelUtil.getContent(cell, 0).trim();
	    categoryCode = JExcelUtil.getContent(cell, 1).trim();
	    categoryCodes.put(categoryPath, categoryCode);
	}

	Kogger.debug(ProjectDocumentDataLoader.class, "categoryCodes loaded.");

	Sheet sheet = sheets[0];
	Vector datas = new Vector();
	int startRow = 3;
	boolean result = true;
	for (int i = startRow; i < sheet.getRows(); i++) {
	    Cell[] cell = sheet.getRow(i);
	    Object obj = checkCellData(cell, i, categoryCodes);
	    if (obj instanceof StringBuffer) {
		StringBuffer sb = (StringBuffer) obj;
		Kogger.debug(ProjectDocumentDataLoader.class, sb.toString());
		log(sb.toString());
		result = false;
	    } else {
		datas.add(obj);
	    }
	}

	if (result) {
	    if (StringUtils.isNotEmpty(delCategoryCode)) {
		//deletPartLinkByCategory(delCategoryCode);// 특정분류체계 부품 링크 제거
	    }

	    upload(datas);
	}

	return result;
    }

    public static void log(String msg) {
	try {
	    LogToFile logger = new LogToFile(logFile, true);
	    logger.log(msg);
	} catch (IOException e) {
	    Kogger.error(ProjectDocumentDataLoader.class, e);
	}
    }

    public static String getFolderPath(String type) {
	String folderPath = "";

	if (type.equals("aRoot")) {
	    folderPath = "/Default/자동차사업부/문서";
	} else if (type.equals("eRoot")) {
	    folderPath = "/Default/전자사업부/문서";
	}

	return folderPath;
    }

    public void upload(Vector datas) throws Exception {

	SessionContext current = SessionContext.getContext();
	Connection conn = null;
	WTConnection wtConn = null;

	try {

	    wtConn = EcmUtil.getWTConnection();
	    conn = wtConn.getConnection();

	    String aRoot = getFolderPath("aRoot");
	    String eRoot = getFolderPath("eRoot");
	    String divisionCode = null;
	    String deptName = null;
	    String documentNo = null;
	    String documentName = null;
	    String documentDescription = null;
	    String categoryCode = null;
	    String fileName = null;
	    String projectNo1 = null;
	    String projectNo2 = null;
	    String projectNo3 = null;
	    String projectNo4 = null;
	    String projectNo5 = null;
	    String parentName = null;
	    String taskName = null;
	    String outputName = null;
	    String partNo1 = null;
	    String partNo2 = null;
	    String partNo3 = null;
	    String partNo4 = null;
	    String partNo5 = null;

	    String categoryPath = null;
	    KETProjectDocument d = null;
	    E3PSProject project = null;
	    E3PSTask et = null;
	    ProjectOutput po = null;
	    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

	    WTPart wtpart = null;
	    KETDocumentCategoryLink DCLink = null;
	    KETDocumentOutputLink DoLink = null;
	    KETDocumentProjectLink DPrLink = null;
	    KETDocumentPartLink DpLink = null;
	    File pFile = null;

	    ProjectDocumentDTO projectDocumentDTO = new ProjectDocumentDTO();
	    
	    

	    for (int i = 0; i < datas.size(); i++) {
		d = null;
		Kogger.debug(ProjectDocumentDataLoader.class, "********************" + " migration start " + "********************");
		log("********************" + " migration start " + "********************");

		HashMap map = (HashMap) datas.get(i);

		People peo = (People) map.get("people");
		user = peo.getUser();
		SessionHelper.manager.setPrincipal(peo.getId());
		
		documentName = (String) map.get("documentName");
		documentDescription = (String) map.get("documentDescription");
		
		divisionCode = (String) map.get("divisionCode");
		deptName = peo.getDepartment().getName();

		categoryCode = (String) map.get("categoryCode");
		categoryPath = KETDmsHelper.service.selectCategoryPath(categoryCode);
		KETDocumentCategory docCate = KETDmsHelper.service.selectDocCategory(categoryCode);

		String numberingCode1 = docCate.getAttribute2();
		String numberingCode2 = docCate.getAttribute3();
		partNo1 = (String) map.get("partNo1");
		System.out.println("::::::::::::::::: 부품번호 ---- : "+partNo1);
		String firstPartNo = "";
		if (!partNo1.equals("")) {

		    firstPartNo = partNo1.split(",")[0];
		}
		
		System.out.println("::::::::::::::::: 대표부품번호 ---- : "+firstPartNo);
		
		projectDocumentDTO.setProjectDocType(categoryCode);
		
		projectDocumentDTO.setVersion(NumberCodeOid);//최신 문서만 가져오도록 한다

		documentNo = (String) map.get("documentNo");
		if (documentNo.equals("")) {
		    /*
	             * 채번규칙 채번코드 레벨 1, 레벨2가 다 존재하면 documentNo = numberingCode1 + numberingCode2 + 시리얼 채번코드 레벨 1 만 있으면 documentNo =
	             * numberingCode1 + partNo1 채번코드가 없으면 PD-년월-시리얼
	             */
		    if (StringUtils.isNotEmpty(numberingCode1)) {
			documentNo = numberingCode1;

			if (StringUtils.isNotEmpty(numberingCode2)) {
			    documentNo = documentNo += numberingCode2;
			    documentNo += ManageSequence.getSeqNoByWTCon(documentNo, "0000", "WTDocumentMaster", "WTDocumentNumber", conn);
			} else {
			    documentNo = numberingCode1 + "-" + firstPartNo;

			    projectDocumentDTO.setDocumentNo(documentNo);
			    List<KETProjectDocument> list = getDocumentResult(projectDocumentDTO);
			    for (KETProjectDocument doc : list) {
				d = doc;
			    }

			    if (d == null && firstPartNo.startsWith("H")) {

				documentNo = numberingCode1 + "-" + firstPartNo.split("H")[1];
				projectDocumentDTO.setDocumentNo(documentNo);
				list = getDocumentResult(projectDocumentDTO);
				for (KETProjectDocument doc : list) {
				    d = doc;
				    d.setDocumentNo(numberingCode1 + "-" + firstPartNo);
				}
			    }
			    System.out.println("::::::::::::::::: numberingCode1 :::::: "+numberingCode1);
			    System.out.println("::::::::::::::::: firstPartNo :::::: "+firstPartNo);
			    if (d == null) {
				documentNo = numberingCode1 + "-" + firstPartNo;
			    }
			    
//			    else {
//
//				Identified localIdentified = (Identified) d.getMaster();
//				WTDocumentMasterIdentity identity = (WTDocumentMasterIdentity) localIdentified.getIdentificationObject();
//				identity.setNumber(numberingCode1 + "-" + firstPartNo);
//				IdentityHelper.service.changeIdentity(localIdentified, identity);
//				
//				d = (KETProjectDocument)PersistenceHelper.manager.refresh(d);
//				
//				d.setDocumentDescription(documentDescription);
//				d.setDocumentNo(numberingCode1 + "-" + firstPartNo);
//				
//				PersistenceServerHelper.manager.update(d);
//				
//				d = (KETProjectDocument)PersistenceHelper.manager.refresh(d);
//
//			    }
			}

		    } else {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
			java.util.Date currentTime = new java.util.Date();
			String ymonth = formatter.format(currentTime).substring(2, 4) + formatter.format(currentTime).substring(5, 7);
			documentNo = "PD-" + ymonth + "-";
			documentNo += ManageSequence.getSeqNoByWTCon(documentNo, "0000", "WTDocumentMaster", "WTDocumentNumber", conn);
		    }
		}

		boolean isNew = (d == null);

		Kogger.debug(ProjectDocumentDataLoader.class, "==>" + documentName);
		log("==>documentName : " + documentName);
		
		System.out.println("::::::::::::::::: documentNo :::::: "+documentNo);
		
		//if (isNew) {
		    d = KETProjectDocument.newKETProjectDocument();

		    d.setDivisionCode(divisionCode);
		    d.setDeptName(deptName);
		    d.setDocumentNo(documentNo);
		    d.setNumber(documentNo);
		    d.setTitle(documentName);
		    d.setName(documentName);
		    d.setDocumentDescription(documentDescription);
		    d.setFirstRegistrationStage(1);
		    d.setSecurity("S1");
		    d.setIsBuyerSummit("2");

		    categoryCode = (String) map.get("categoryCode");
		    categoryPath = KETDmsHelper.service.selectCategoryPath(categoryCode);

		    Folder cateFolder = null;
		    if (divisionCode.equals("CA")) {
			cateFolder = FolderHelper.service.getFolder(aRoot + categoryPath, WCUtil.getWTContainerRef());
		    } else if (divisionCode.equals("ER")) {
			cateFolder = FolderHelper.service.getFolder(eRoot + categoryPath, WCUtil.getWTContainerRef());
		    } else {
			cateFolder = FolderHelper.service.getFolder(aRoot + categoryPath, WCUtil.getWTContainerRef());
		    }

		    //d.setAttribute8("Y");

		    FolderHelper.assignLocation((FolderEntry) d, cateFolder);
		    d = (KETProjectDocument) PersistenceHelper.manager.save(d);

		    log("==>Folder : OK ");

		    String state = (String) map.get("state");
		    log("==>state : " + state);
		    // 승인완료 상태로 셋팅함.
		    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) d, State.toState(state));

		    DCLink = KETDocumentCategoryLink.newKETDocumentCategoryLink(docCate, d);
		    DCLink = (KETDocumentCategoryLink) PersistenceHelper.manager.save(DCLink);

		    log("==>categoryCode : " + categoryCode);

		    projectNo1 = (String) map.get("projectNo1");
		    parentName = (String) map.get("parentName");
		    taskName = (String) map.get("taskName");
		    outputName = (String) map.get("outputName");

		    if ((!projectNo1.equals("")) && (!taskName.equals(""))) {
			project = getProject(projectNo1);
			if (parentName == null || parentName.equals("")) {
			    et = MoldProjectHelper.getTask((E3PSProject) project, taskName);
			} else {
			    et = getTask2((E3PSProject) project, taskName, parentName);
			}

			HashMap poMap = new HashMap();
			String projectOid = CommonUtil.getOIDString(project);
			String taskOid = CommonUtil.getOIDString(et);
			String name = outputName;
			String description = documentDescription;
			String docTypeOid = categoryCode;
			String outputUser = CommonUtil.getOIDString(user);
			String isPrimary = "0";

			poMap.put("projectOid", projectOid);
			poMap.put("taskOid", taskOid);
			poMap.put("name", name);
			poMap.put("description", description);
			poMap.put("docTypeOid", docTypeOid);
			poMap.put("outputUser", outputUser);
			poMap.put("objType", "DOC");
			poMap.put("isPrimary", isPrimary);

			try {
			    po = ProjectOutputHelper.saveDefProjectOutput(poMap);
			} catch (Exception e) {
			    Kogger.error(ProjectDocumentDataLoader.class, e);
			    po = null;
			}

			if (po != null) {
			    DoLink = KETDocumentOutputLink.newKETDocumentOutputLink(d, po);
			    PersistenceHelper.manager.save(DoLink);
			    ProjectOutputHelper.manager.registryProjectOutput(po, d);
			}

			log("==>projectNo1,  taskName: " + projectNo1 + taskName);

		    } else if (!projectNo1.equals("")) {
			project = getProject(projectNo1);
			DPrLink = KETDocumentProjectLink.newKETDocumentProjectLink(d, project);
			DPrLink = (KETDocumentProjectLink) PersistenceHelper.manager.save(DPrLink);
			log("==>projectNo1: " + projectNo1);
		    }

		    projectNo2 = (String) map.get("projectNo2");
		    if (!projectNo2.equals("")) {
			project = getProject(projectNo2);
			DPrLink = KETDocumentProjectLink.newKETDocumentProjectLink(d, project);
			DPrLink = (KETDocumentProjectLink) PersistenceHelper.manager.save(DPrLink);
			log("==>projectNo2: " + projectNo2);
		    }

		    projectNo3 = (String) map.get("projectNo3");
		    if (!projectNo3.equals("")) {
			project = getProject(projectNo3);
			DPrLink = KETDocumentProjectLink.newKETDocumentProjectLink(d, project);
			DPrLink = (KETDocumentProjectLink) PersistenceHelper.manager.save(DPrLink);
			log("==>projectNo3: " + projectNo3);
		    }

		    projectNo4 = (String) map.get("projectNo4");
		    if (!projectNo4.equals("")) {
			project = getProject(projectNo4);
			DPrLink = KETDocumentProjectLink.newKETDocumentProjectLink(d, project);
			DPrLink = (KETDocumentProjectLink) PersistenceHelper.manager.save(DPrLink);
			log("==>projectNo4: " + projectNo4);
		    }

		    projectNo5 = (String) map.get("projectNo5");
		    if (!projectNo5.equals("")) {
			project = getProject(projectNo5);
			DPrLink = KETDocumentProjectLink.newKETDocumentProjectLink(d, project);
			DPrLink = (KETDocumentProjectLink) PersistenceHelper.manager.save(DPrLink);
			log("==>projectNo5: " + projectNo5);
		    }
		//}

		partNo1 = (String) map.get("partNo1");

		if (!partNo1.equals("")) {

		    String partNos[] = partNo1.split(",");

		    for (String partNo : partNos) {
			wtpart = PartBaseHelper.service.getLatestPart(partNo);

			DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
			DpLink = (KETDocumentPartLink) PersistenceHelper.manager.save(DpLink);
			log("==>partNo: " + partNo);

			/*
		         * if(partNo.startsWith("6")){
		         * 
		         * List<WTPart> partList = getPartNoList((partNo.split("-"))[0]);
		         * 
		         * for (WTPart target : partList) { DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, target); DpLink =
		         * (KETDocumentPartLink) PersistenceHelper.manager.save(DpLink); log("==>partNo: " + partNo); }
		         * 
		         * 
		         * } else { DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart); DpLink = (KETDocumentPartLink)
		         * PersistenceHelper.manager.save(DpLink); log("==>partNo: " + partNo); }
		         */

		    }

		}

		/*
	         * partNo2 = (String) map.get("partNo2"); if (!partNo2.equals("")) { wtpart = KETPartHelper.service.getPart(partNo2); DpLink
	         * = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart); DpLink = (KETDocumentPartLink)
	         * PersistenceHelper.manager.save(DpLink); log("==>partNo2: " + partNo2); }
	         * 
	         * partNo3 = (String) map.get("partNo3"); if (!partNo3.equals("")) { wtpart = KETPartHelper.service.getPart(partNo3); DpLink
	         * = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart); DpLink = (KETDocumentPartLink)
	         * PersistenceHelper.manager.save(DpLink); log("==>partNo3: " + partNo3); }
	         * 
	         * partNo4 = (String) map.get("partNo4"); if (!partNo4.equals("")) { wtpart = KETPartHelper.service.getPart(partNo4); DpLink
	         * = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart); DpLink = (KETDocumentPartLink)
	         * PersistenceHelper.manager.save(DpLink); log("==>partNo4: " + partNo4); }
	         * 
	         * partNo5 = (String) map.get("partNo5"); if (!partNo5.equals("")) { wtpart = KETPartHelper.service.getPart(partNo5); DpLink
	         * = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart); DpLink = (KETDocumentPartLink)
	         * PersistenceHelper.manager.save(DpLink); log("==>partNo5: " + partNo5); }
	         */

		if (!isNew) {// 첨부파일 삭제
		    
		    ContentDTO fileDto = KETContentHelper.manager.getPrimaryContent(d);
		    
		    d = (KETProjectDocument)KETContentHelper.service.delete(d, (ApplicationData) CommonUtil.getObject(fileDto.getContentoid()));//주첨부파일만 삭제한다
		    
//		    ArrayList<ContentDTO> secondaryContents = KETContentHelper.manager.getSecondaryContents(d); //부첨부파일만 삭제한다
//
//		    if (secondaryContents != null) {
//			for (int j = 0; j < secondaryContents.size(); i++) {
//			    ContentDTO broker = (ContentDTO) secondaryContents.get(j);
//			    d = (KETProjectDocument) KETContentHelper.service.delete((ContentHolder) d,(ApplicationData) CommonUtil.getObject(broker.getContentoid()));
//			}
//		    }
		    
		    //d = (KETProjectDocument) KETContentHelper.service.delete(d); //전체파일 삭제
//		    WTPrincipalReference currentUserRef = SessionHelper.manager.getPrincipalReference();
//		    Ownership paramOwnership = Ownership.newOwnership(currentUserRef);
//		    d.setOwnership(paramOwnership);
		    
		}

		fileName = (String) map.get("fileName");
		pFile = new File(fileName);
		ContentHolder holder = ContentHelper.service.getContents(d);
		holder = E3PSContentHelper.service.attach(holder, pFile, "", ContentRoleType.PRIMARY);

//		uploadSecondFile(d, (File) map.get("file1"));
//		uploadSecondFile(d, (File) map.get("file2"));
//		uploadSecondFile(d, (File) map.get("file3"));

		log("==>File: OK");

		pFile = null;

		Kogger.debug(ProjectDocumentDataLoader.class, "********************" + " migration success " + "********************");
		log("********************" + " migration success " + "********************");

		SessionContext.setContext(current);
	    }

	} catch (Exception e) {
	    Kogger.debug(ProjectDocumentDataLoader.class, "********************" + " migration fail !" + "********************");
	    log("********************" + " migration fail !" + "********************");
	    Kogger.error(ProjectDocumentDataLoader.class, e);
	} finally {
	    SessionContext.setContext(current);

	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !wtConn.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}

    }

    public void uploadSecondFile(KETProjectDocument d, File secondFile) throws WTException, PropertyVetoException {
	if (secondFile.exists() && secondFile.isFile()) {
	    d = (KETProjectDocument) PersistenceHelper.manager.refresh(d);
	    ContentHolder holder = ContentHelper.service.getContents(d);
	    E3PSContentHelper.service.attach(holder, secondFile, "", ContentRoleType.SECONDARY);
	}
    }

    public boolean fileCheck(String fileName, StringBuffer sb, int cnt, File secondFile) {

	if (!secondFile.exists()) {
	    sb.append("부첨부(" + cnt + ") 파일이 없습니다.[" + FILESERVER + "\\" + fileName + "]\n");
	    return false;
	}

	return true;
    }

    public Object checkCellData(Cell[] cell, int row, HashMap categoryCodes) throws Exception {

	StringBuffer sb = new StringBuffer();
	sb.append((row + 1) + " 라인===>");

	boolean isError = false;
	int columnIndex = 0;

	String divisionCode = JExcelUtil.getContent(cell, columnIndex++).trim();

	if (divisionCode.equals("자동차"))
	    divisionCode = "CA";
	else if (divisionCode.equals("전자"))
	    divisionCode = "ER";
	else {
	    sb.append("사업부구분이 맞지 않습니다." + divisionCode + "\n");
	    isError = true;
	}

	// String deptName = JExcelUtil.getContent(cell, columnIndex++).trim();
	// if (deptName.equals("")) {
	// sb.append("작성부서가 없습니다.\n");
	// isError = true;
	// }

	String id = JExcelUtil.getContent(cell, columnIndex++).trim();
	People peo = PeopleHelper.manager.getPeople(id);
	if (peo == null) {
	    sb.append("작성자가 없습니다.\n");
	    isError = true;
	}

	String documentNo = JExcelUtil.getContent(cell, columnIndex++).trim();

	String documentName = JExcelUtil.getContent(cell, columnIndex++).trim();
	if (documentName.equals("")) {
	    sb.append("문서명이 없습니다.\n");
	    isError = true;
	}

	String documentDescription = JExcelUtil.getContent(cell, columnIndex++).trim();

	String categoryPath = JExcelUtil.getContent(cell, columnIndex++).trim();
	String categoryCode = (String) categoryCodes.get(categoryPath);
	KETDocumentCategory docCate = null;
	if (categoryCode == null) {
	    sb.append("분류체계코드가  존재하지 않습니다.\n");
	    isError = true;
	} else {
	    docCate = KETDmsHelper.service.selectDocCategory(categoryCode);
	}

	if (docCate == null) {
	    sb.append("분류체계코드가  맞지 않습니다.\n");
	    isError = true;
	}

	String numberingCode1 = "";
	String numberingCode2 = "";

	if (docCate != null) {
	    numberingCode1 = docCate.getAttribute2();
	    numberingCode2 = docCate.getAttribute3();
	}

	String fileName = JExcelUtil.getContent(cell, columnIndex++).trim();
	// String filePath = JExcelUtil.getContent(cell, columnIndex++).trim();

	File pFile = new File(FILESERVER + "\\" + fileName);

	String state = "APPROVED";

	if (pFile.length() < 1) {
	    sb.append("해당 파일이 없습니다.[" + FILESERVER + "\\" + fileName + "]\n");
	    isError = true;

	}

	// pFile = null;

	File secondFile1 = null;
	File secondFile2 = null;
	File secondFile3 = null;
	
	columnIndex++;
	columnIndex++;
	columnIndex++;

//	String fileName_1 = JExcelUtil.getContent(cell, columnIndex++).trim(); // 부첨부파일 1 체크
//	secondFile1 = new File(FILESERVER + "\\" + fileName_1);
//	if (!fileCheck(fileName_1, sb, 1, secondFile1)) {
//	    isError = true;
//	}
//
//	String fileName_2 = JExcelUtil.getContent(cell, columnIndex++).trim(); // 부첨부파일 2 체크
//	secondFile2 = new File(FILESERVER + "\\" + fileName_2);
//	if (!fileCheck(fileName_2, sb, 2, secondFile2)) {
//	    isError = true;
//	}
//
//	String fileName_3 = JExcelUtil.getContent(cell, columnIndex++).trim(); // 부첨부파일 3 체크
//	secondFile3 = new File(FILESERVER + "\\" + fileName_3);
//	if (!fileCheck(fileName_3, sb, 3, secondFile3)) {
//	    isError = true;
//	}

	String projectNo1 = JExcelUtil.getContent(cell, columnIndex++).trim();
	E3PSProject project1 = null;
	if (!projectNo1.equals("")) {
	    project1 = getProject(projectNo1);
	    if (project1 == null) {
		isError = true;
		sb.append(" ProjectNo1 정보가 맞지 않습니다.\n");
	    }
	}

	String projectNo2 = JExcelUtil.getContent(cell, columnIndex++).trim();
	E3PSProject project2 = null;
	if (!projectNo2.equals("")) {
	    project2 = getProject(projectNo2);
	    if (project2 == null) {
		isError = true;
		sb.append(" ProjectNo2 정보가 맞지 않습니다.\n");
	    }
	}

	String projectNo3 = JExcelUtil.getContent(cell, columnIndex++).trim();
	E3PSProject project3 = null;
	if (!projectNo3.equals("")) {

	    project3 = getProject(projectNo3);
	    if (project3 == null) {
		isError = true;
		sb.append(" ProjectNo3 정보가 맞지 않습니다.\n");
	    }
	}

	String projectNo4 = JExcelUtil.getContent(cell, columnIndex++).trim();
	E3PSProject project4 = null;
	if (!projectNo4.equals("")) {
	    project4 = getProject(projectNo4);
	    if (project4 == null) {
		isError = true;
		sb.append(" ProjectNo4 정보가 맞지 않습니다.\n");
	    }
	}

	String projectNo5 = JExcelUtil.getContent(cell, columnIndex++).trim();
	E3PSProject project5 = null;
	if (!projectNo5.equals("")) {
	    project5 = getProject(projectNo5);
	    if (project5 == null) {
		isError = true;
		sb.append(" ProjectNo5 정보가 맞지 않습니다.\n");
	    }
	}

	String task = JExcelUtil.getContent(cell, columnIndex++).trim();

	int inx = 0;
	String parentName = null;
	String taskName = "";
	String outputName = null;

	if (!task.equals("") && project1 != null) {
	    StringTokenizer st = new StringTokenizer(task, "/");

	    String[] tasklist = new String[st.countTokens()];
	    while (st.hasMoreTokens()) {
		tasklist[inx] = st.nextToken();
		inx++;
	    }

	    if (tasklist.length == 2) {
		taskName = tasklist[0];
		outputName = tasklist[1];
	    } else if (tasklist.length == 3) {
		parentName = tasklist[0];
		taskName = tasklist[1];
		outputName = tasklist[2];
		int cnt = getTaskCnt((E3PSProject) project1, parentName);
		if (cnt > 1) {
		    isError = true;
		    sb.append(" 동일 parent가 존재합니다.\n");
		}
	    } else {
		isError = true;
		sb.append(" task 입력방식이 맞지 않습니다.\n");
	    }

	    if (projectNo1.equals("")) {
		isError = true;
		sb.append(" task명 정보는 projectNo1정보와  같이 입력되어야 합니다.\n");
	    } else {
		if (tasklist.length == 2) {
		    E3PSTask et = MoldProjectHelper.getTask((E3PSProject) project1, taskName);
		    if (et == null) {
			isError = true;
			sb.append(" task명 정보가 맞지 않습니다.\n");
		    } else {
			int cnt = getTaskCnt((E3PSProject) project1, taskName);
			if (cnt > 1) {
			    isError = true;
			    sb.append(" 동일 task가 존재합니다.\n");
			}
		    }
		} else if (tasklist.length == 3) {
		    E3PSTask et = getTask2((E3PSProject) project1, taskName, parentName);
		    if (et == null) {
			isError = true;
			sb.append("project1: " + project1 + ",taskName: " + taskName + ",parentName: " + parentName + " \n");
			sb.append(" task명 정보가 맞지 않습니다.\n");
		    } else {
			int cnt = getTaskCnt2((E3PSProject) project1, taskName, parentName);
			if (cnt > 1) {
			    isError = true;
			    sb.append(" 동일 task가 존재합니다.\n");
			}
		    }
		}
	    }
	}

	String partNo1 = JExcelUtil.getContent(cell, columnIndex++).trim();
System.out.println("###$#$#$#$# partNo1 : "+partNo1);
	if (!documentNo.equals("")) {

	    if (StringUtils.isNotEmpty(numberingCode1)) {
		if (StringUtils.isNotEmpty(numberingCode2) && StringUtils.isEmpty(partNo1)) {
		    sb.append(categoryPath + " 는 부품번호1 필수입니다.\n");
		    isError = true;
		}
	    } else {

		if (!documentNo.substring(0, 2).equals("PD")) {
		    sb.append("문서번호 형식이 틀립니다.\n");
		    isError = true;
		}

		if (!documentNo.substring(2, 3).equals("-")) {
		    sb.append("문서번호 형식이 틀립니다..\n");
		    isError = true;
		}

		if (!documentNo.substring(7, 8).equals("-")) {
		    sb.append("문서번호 형식이 틀립니다...\n");
		    isError = true;
		}

	    }
	    QuerySpec query = new QuerySpec(wt.doc.WTDocumentMaster.class);
	    query.appendWhere(new SearchCondition(wt.doc.WTDocumentMaster.class, "number", "=", documentNo), new int[] { 0 });
	    QueryResult qr = PersistenceHelper.manager.find(query);
	    if (qr.hasMoreElements()) {
		sb.append("이미 입력된 문서번호입니다(" + documentNo + ").\n");
		isError = true;
	    }

	}

	if (!partNo1.equals("")) {
	    System.out.println("공백제거...");
	    partNo1 = partNo1.replaceAll("\\p{Space}", "");
	    String partNos[] = partNo1.split(",");

	    for (String part : partNos) {
		part = part.replaceAll("\\p{Space}", "");
		// WTPart wtpart1 = KETPartHelper.service.getPart(part);
		WTPart wtpart1 = PartBaseHelper.service.getLatestPart(part);

		if (wtpart1 == null) {

		    sb.append("[" + part + "] 는 존재하지 않는 자재번호입니다.\n");
		    isError = true;
		}

		/*
	         * if (wtpart1 == null) { if(part.startsWith("6")){ if(getPartNoList((part.split("-"))[0]).size() == 0){ sb.append("[" +
	         * part + "] 는 존재하지 않는 자재번호입니다.\n"); isError = true; } }else{ sb.append("[" + part + "] 는 존재하지 않는 자재번호입니다.\n"); isError =
	         * true; }
	         * 
	         * }
	         */
	    }

	}

	/*
	 * String partNo2 = JExcelUtil.getContent(cell, columnIndex++).trim(); if (!partNo2.equals("")) { WTPart wtpart2 =
	 * KETPartHelper.service.getPart(partNo2); if (wtpart2 == null) { sb.append("[" + partNo2 + "]부품번호2가  맞지 않습니다.\n"); isError = true;
	 * } }
	 * 
	 * String partNo3 = JExcelUtil.getContent(cell, columnIndex++).trim(); if (!partNo3.equals("")) { WTPart wtpart3 =
	 * KETPartHelper.service.getPart(partNo3); if (wtpart3 == null) { sb.append("[" + partNo3 + "]부품번호3이  맞지 않습니다.\n"); isError = true;
	 * } }
	 * 
	 * String partNo4 = JExcelUtil.getContent(cell, columnIndex++).trim(); if (!partNo4.equals("")) { WTPart wtpart4 =
	 * KETPartHelper.service.getPart(partNo4); if (wtpart4 == null) { sb.append("[" + partNo4 + "]부품번호4가  맞지 않습니다.\n"); isError = true;
	 * } }
	 * 
	 * String partNo5 = JExcelUtil.getContent(cell, columnIndex++).trim(); if (!partNo5.equals("")) { WTPart wtpart5 =
	 * KETPartHelper.service.getPart(partNo5); if (wtpart5 == null) { sb.append("[" + partNo5 + "]부품번호5가  맞지 않습니다.\n"); isError = true;
	 * } }
	 */

	if (isError) {
	    return sb;
	} else {
	    HashMap map = new HashMap();
	    map.put("divisionCode", divisionCode);
	    map.put("people", peo);
	    map.put("documentNo", documentNo);
	    map.put("documentName", documentName);
	    map.put("documentDescription", documentDescription);
	    map.put("categoryCode", categoryCode);
	    map.put("fileName", FILESERVER + "\\" + fileName);
	    map.put("file1", secondFile1);
	    map.put("file2", secondFile2);
	    map.put("file3", secondFile3);
	    map.put("projectNo1", projectNo1);
	    map.put("projectNo2", projectNo2);
	    map.put("projectNo3", projectNo3);
	    map.put("projectNo4", projectNo4);
	    map.put("projectNo5", projectNo5);
	    map.put("parentName", parentName);
	    map.put("taskName", taskName);
	    map.put("outputName", outputName);
	    map.put("partNo1", partNo1);
	    map.put("state", state);
	    /*
	     * map.put("partNo2", partNo2); map.put("partNo3", partNo3); map.put("partNo4", partNo4); map.put("partNo5", partNo5);
	     */
	    return map;
	}
    }

    public static E3PSProject getProject(String pjtNo) throws Exception {

	QuerySpec spec = new QuerySpec(E3PSProject.class);

	spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { 0 });

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}

	spec.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { 0 });

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}

	ClassAttribute ca0 = new ClassAttribute(E3PSProject.class, E3PSProject.PJT_NO);
	SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);

	pjtNo = pjtNo.toUpperCase();
	ColumnExpression ce = ConstantExpression.newExpression(pjtNo);
	spec.appendWhere(new SearchCondition(upper, SearchCondition.EQUAL, ce), new int[] { 0 });

	QueryResult rs = PersistenceHelper.manager.find(spec);

	E3PSProject project = null;

	if (rs.hasMoreElements()) {
	    project = (E3PSProject) rs.nextElement();
	}

	return project;
    }

    public static int getTaskCnt(E3PSProject project, String taskName) throws Exception {

	QuerySpec spec = new QuerySpec(E3PSTask.class);
	long projectId = project.getPersistInfo().getObjectIdentifier().getId();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.PROJECT_REFERENCE + ".key.id", "=", projectId), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.TASK_NAME, "=", taskName), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.DEBUG_SUB, SearchCondition.IS_FALSE, false), new int[] { 0 });
	QueryResult rs = PersistenceHelper.manager.find(spec);

	return rs.size();
    }

    public static int getTaskCnt2(E3PSProject project, String taskName, String parentName) throws Exception {
	E3PSTask parentTask = MoldProjectHelper.getTask((E3PSProject) project, parentName);
	long parentId = parentTask.getPersistInfo().getObjectIdentifier().getId();

	long projectId = project.getPersistInfo().getObjectIdentifier().getId();

	QuerySpec spec = new QuerySpec(E3PSTask.class);
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.PROJECT_REFERENCE + ".key.id", "=", projectId), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.PARENT_REFERENCE + ".key.id", "=", projectId), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.TASK_NAME, "=", taskName), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.DEBUG_SUB, SearchCondition.IS_FALSE, false), new int[] { 0 });
	QueryResult rs = PersistenceHelper.manager.find(spec);

	return rs.size();
    }

    public static E3PSTask getTask2(E3PSProject project, String taskName, String parentName) throws Exception {
	E3PSTask parentTask = MoldProjectHelper.getTask((E3PSProject) project, parentName);
	long parentId = parentTask.getPersistInfo().getObjectIdentifier().getId();

	long projectId = project.getPersistInfo().getObjectIdentifier().getId();

	QuerySpec spec = new QuerySpec(E3PSTask.class);
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.PROJECT_REFERENCE + ".key.id", "=", projectId), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.PARENT_REFERENCE + ".key.id", "=", parentId), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.TASK_NAME, "=", taskName), new int[] { 0 });
	// spec.appendAnd();
	// spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.DEBUG_SUB, SearchCondition.IS_FALSE, false), new int[]{0});
	QueryResult rs = PersistenceHelper.manager.find(spec);

	E3PSTask task = null;

	if (rs.hasMoreElements()) {
	    task = (E3PSTask) rs.nextElement();
	}
	return task;
    }

    public static List<WTPart> getPartNoList(String partNo) throws Exception {
	try {
	    QuerySpec spec = new QuerySpec();
	    int pm_idx = spec.appendClassList(WTPartMaster.class, false);
	    int p_idx = spec.appendClassList(WTPart.class, false);

	    SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, new ClassAttribute(WTPart.class,
		    "versionInfo.identifier.versionSortId"));
	    // function.setColumnAlias(spec.getFromClause().getAliasAt(p_idx));

	    spec.appendSelect(new ClassAttribute(WTPartMaster.class, WTAttributeNameIfc.ID_NAME), new int[] { pm_idx }, false);
	    spec.appendSelect(new ClassAttribute(WTPartMaster.class, WTPartMaster.NUMBER), new int[] { pm_idx }, false);
	    spec.appendSelect(new ClassAttribute(WTPartMaster.class, WTPartMaster.NAME), new int[] { pm_idx }, false);
	    spec.appendSelect(function, new int[] { p_idx }, false);

	    spec.appendWhere(new SearchCondition(new ClassAttribute(WTPartMaster.class, WTAttributeNameIfc.ID_NAME), "=",
		    new ClassAttribute(WTPart.class, "masterReference.key.id")), new int[] { pm_idx, p_idx });

	    // Others도 보여준다.
	    // spec.appendAnd();
	    // spec.appendWhere(new SearchCondition(WTPart.class, PartSpecEnum.SpPartType.getQuerySpecCode(), SearchCondition.NOT_EQUAL,
	    // "O"), new int[] { p_idx });
	    // spec.appendAnd();
	    // spec.appendWhere(new SearchCondition(WTPart.class, PartSpecEnum.SpIsDeleted.getQuerySpecCode(), SearchCondition.NOT_EQUAL,
	    // "Y"), new int[] { p_idx });

	    SearchUtil.appendBOOLEAN(spec, WTPart.class, "iterationInfo.latest", SearchCondition.IS_TRUE, p_idx);
	    SearchUtil.appendSLIKE(spec, WTPartMaster.class, WTPartMaster.NUMBER, partNo, pm_idx, false);
	    spec.appendGroupBy(new ClassAttribute(WTPartMaster.class, WTAttributeNameIfc.ID_NAME), new int[] { pm_idx }, false);
	    spec.appendGroupBy(new ClassAttribute(WTPartMaster.class, WTPartMaster.NUMBER), new int[] { pm_idx }, false);
	    spec.appendGroupBy(new ClassAttribute(WTPartMaster.class, WTPartMaster.NAME), new int[] { pm_idx }, false);

	    SearchUtil.setOrderBy(spec, WTPartMaster.class, pm_idx, WTPartMaster.NUMBER, false);
	    spec.setDistinct(true);

	    // spec.appendAnd();
	    // spec.appendRowNumCondition(SUGGEST_MAXIMUN_COUNT);
	    if (!spec.isAdvancedQueryEnabled())
		spec.setAdvancedQueryEnabled(true);
	    List<WTPart> list = new ArrayList<WTPart>();
	    QueryResult result = PersistenceServerHelper.manager.query(spec);

	    if (result != null) {
		while (result.hasMoreElements()) {
		    Object[] objects = (Object[]) result.nextElement();

		    WTPart part = PartBaseHelper.service.getLatestPart(String.valueOf(objects[1]));
		    partNo = String.valueOf(objects[1]).toUpperCase();

		    if (part != null && !StringUtils.contains(partNo, "Z")) {
			list.add(part);
		    }

		}
	    }
	    return list;
	} catch (Exception e) {
	    throw e;
	}
    }

}
