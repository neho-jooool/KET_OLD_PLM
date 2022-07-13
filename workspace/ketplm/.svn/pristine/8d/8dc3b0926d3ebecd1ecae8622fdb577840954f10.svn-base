package e3ps.load.dms;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentRoleType;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTProperties;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.JExcelUtil;
import e3ps.common.util.ManageSequence;
import e3ps.common.util.WCUtil;
import e3ps.cost.util.StringUtil;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETDocumentOutputLink;
import e3ps.dms.entity.KETDocumentPartLink;
import e3ps.dms.entity.KETDocumentProjectLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.service.KETDmsHelper;
import e3ps.load.migration.edm.log.LogToFile;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ProjectOutput;
import e3ps.project.beans.MoldProjectHelper;
import e3ps.project.beans.ProjectOutputHelper;
import ext.ket.dms.service.KETProjectDocumentHelper;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

public class ProjectDocumentDataLoader implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    private static String FILESERVER = "\\\\192.168.17.13";
    private static String EXCELFILE = "";
    private static String SEPARATOR = File.separator;
    private static String logFile = "projectDocumentLoader.log";

    static {
	try {
	    String wt_home = WTProperties.getLocalProperties().getProperty("wt.home");
	    EXCELFILE = wt_home + SEPARATOR + "loadFiles\\ket\\migration\\dms";
	} catch (Exception e) {
	    Kogger.error(ProjectDocumentDataLoader.class, e);
	}
    }
    public static ProjectDocumentDataLoader manager = new ProjectDocumentDataLoader();

    public ProjectDocumentDataLoader() {

    }

    // windchill e3ps.load.dms.base.ProjectDocumentDataLoader
    public static void main(String[] args) {

	try {

	    String fileName = null;

	    if (args == null || args.length < 1)
		throw new Exception("@@ args need !");
	    else {
		fileName = args[0];
	    }

	    Kogger.debug(ProjectDocumentDataLoader.class, "@start");
	    ProjectDocumentDataLoader.manager.saveFromExcel(fileName);
	    Kogger.debug(ProjectDocumentDataLoader.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(ProjectDocumentDataLoader.class, e);
	}
    }

    public void saveFromExcel(String fileName) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class };
		Object aobj[] = { fileName };

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
	    executeMigration(fileName);
	}
    }

    public void executeMigration(String fileName) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	ReferenceFactory rf = new ReferenceFactory();
	Transaction trx = null;
	try {

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    trx = new Transaction();
	    trx.start();

	    loadFile(fileName);

	    trx.commit();

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    public boolean loadFile(String fileName) throws Exception {

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

	dataFile = DRMHelper.Decryptupload(dataFile, dataFile.getName());

	Workbook wb = Workbook.getWorkbook(dataFile);
	Sheet sheets[] = wb.getSheets();
	Kogger.debug(ProjectDocumentDataLoader.class, "sheets==>" + sheets.length);
	Sheet sheet2 = sheets[2];
	String categoryCode = "";
	String categoryPath = "";
	HashMap categoryCodes = new HashMap();

	for (int i = 2; i < sheet2.getRows(); i++) {
	    Cell[] cell = sheet2.getRow(i);
	    categoryPath = JExcelUtil.getContent(cell, 0).trim();
	    categoryCode = JExcelUtil.getContent(cell, 1).trim();
	    categoryCodes.put(categoryPath, categoryCode);
	}

	Kogger.debug(ProjectDocumentDataLoader.class, "categoryCodes loaded.");

	Sheet sheet = sheets[0];
	Vector datas = new Vector();
	int startRow = 3;
	boolean result = false;
	for (int i = startRow; i < sheet.getRows(); i++) {
	    Cell[] cell = sheet.getRow(i);
	    Object obj = checkCellData(cell, i, categoryCodes);
	    if (obj instanceof StringBuffer) {
		StringBuffer sb = (StringBuffer) obj;
		Kogger.debug(ProjectDocumentDataLoader.class, sb.toString());
		log(sb.toString());
		throw new Exception("로그 확인하여 엑셀데이터 점검하십시오.");
	    } else {
		datas.add(obj);
		result = true;
	    }
	}

	if (result) {
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

	try {

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

	    for (int i = 0; i < datas.size(); i++) {
		Kogger.debug(ProjectDocumentDataLoader.class, "********************" + " migration start " + "********************");
		log("********************" + " migration start " + "********************");

		HashMap map = (HashMap) datas.get(i);

		divisionCode = (String) map.get("divisionCode");
		deptName = (String) map.get("deptName");

		categoryCode = (String) map.get("categoryCode");
		categoryPath = KETDmsHelper.service.selectCategoryPath(categoryCode);
		KETDocumentCategory docCate = KETDmsHelper.service.selectDocCategory(categoryCode);

		String numberingCode1 = docCate.getAttribute2();
		String numberingCode2 = docCate.getAttribute3();
		partNo1 = (String) map.get("partNo1");

		documentNo = (String) map.get("documentNo");
		if (documentNo.equals("")) {
		    /*
	             * 채번규칙 1. 채번코드 레벨 1, 레벨2가 다 존재하면 documentNo = numberingCode1 + numberingCode2 + 시리얼 2. 채번코드 레벨 1 만 있으면 documentNo =
	             * numberingCode1 + partNo1 3. 채번코드가 없으면 PD-년월-시리얼
	             */
		    if (StringUtils.isNotEmpty(numberingCode1)) {
			documentNo = numberingCode1;

			if (StringUtils.isNotEmpty(numberingCode2)) {
			    documentNo = documentNo += "-" + numberingCode2 + "-";
			    documentNo += ManageSequence.getSeqNo(documentNo, "0000", "WTDocumentMaster", "WTDocumentNumber");
			} else {
			    documentNo += "-" + partNo1;
			}

		    } else {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
			java.util.Date currentTime = new java.util.Date();
			String ymonth = formatter.format(currentTime).substring(2, 4) + formatter.format(currentTime).substring(5, 7);
			documentNo = "PD-" + ymonth + "-";
			documentNo += ManageSequence.getSeqNo(documentNo, "0000", "WTDocumentMaster", "WTDocumentNumber");
		    }
		}

		documentName = (String) map.get("documentName");
		documentDescription = StringUtil.checkNull((String) map.get("documentDescription"));

		Kogger.debug(ProjectDocumentDataLoader.class, "==>" + documentName);
		log("==>documentName : " + documentName);

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

		FolderHelper.assignLocation((FolderEntry) d, cateFolder);
		d = (KETProjectDocument) PersistenceHelper.manager.save(d);

		log("==>Folder : OK ");

		// 승인완료 상태로 셋팅함.
		LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) d, State.toState("APPROVED"));

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

		if (StringUtils.isNotEmpty(numberingCode1) && StringUtils.isEmpty(numberingCode2)) {

		}

		partNo1 = (String) map.get("partNo1");
		if (!partNo1.equals("")) {
		    wtpart = KETPartHelper.service.getPart(partNo1);
		    DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
		    DpLink = (KETDocumentPartLink) PersistenceHelper.manager.save(DpLink);
		    log("==>partNo1: " + partNo1);
		}

		if (StringUtils.isNotEmpty(numberingCode1) && StringUtils.isEmpty(numberingCode2)) {//표준품질문서의 경우 part연계는 하나
		    partNo2 = (String) map.get("partNo2");
		    if (!partNo2.equals("")) {
			wtpart = KETPartHelper.service.getPart(partNo2);
			DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
			DpLink = (KETDocumentPartLink) PersistenceHelper.manager.save(DpLink);
			log("==>partNo2: " + partNo2);
		    }

		    partNo3 = (String) map.get("partNo3");
		    if (!partNo3.equals("")) {
			wtpart = KETPartHelper.service.getPart(partNo3);
			DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
			DpLink = (KETDocumentPartLink) PersistenceHelper.manager.save(DpLink);
			log("==>partNo3: " + partNo3);
		    }

		    partNo4 = (String) map.get("partNo4");
		    if (!partNo4.equals("")) {
			wtpart = KETPartHelper.service.getPart(partNo4);
			DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
			DpLink = (KETDocumentPartLink) PersistenceHelper.manager.save(DpLink);
			log("==>partNo4: " + partNo4);
		    }

		    partNo5 = (String) map.get("partNo5");
		    if (!partNo5.equals("")) {
			wtpart = KETPartHelper.service.getPart(partNo5);
			DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
			DpLink = (KETDocumentPartLink) PersistenceHelper.manager.save(DpLink);
			log("==>partNo5: " + partNo5);
		    }
		}

		fileName = (String) map.get("fileName");
		pFile = new File(fileName);
		ContentHolder holder = ContentHelper.service.getContents(d);
		E3PSContentHelper.service.attach(holder, pFile, "", ContentRoleType.PRIMARY);

		log("==>File: OK");

		pFile = null;

		Kogger.debug(ProjectDocumentDataLoader.class, "********************" + " migration success " + "********************");
		log("********************" + " migration success " + "********************");
	    }

	} catch (Exception e) {
	    Kogger.debug(ProjectDocumentDataLoader.class, "********************" + " migration fail " + "********************");
	    log("********************" + " migration fail " + "********************");
	    Kogger.error(ProjectDocumentDataLoader.class, e);
	}

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

	String deptName = JExcelUtil.getContent(cell, columnIndex++).trim();
	if (deptName.equals("")) {
	    sb.append("작성부서가 없습니다.\n");
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

	String numberingCode1 = docCate.getAttribute2();
	String numberingCode2 = docCate.getAttribute3();

	String fileName = JExcelUtil.getContent(cell, columnIndex++).trim();
	String filePath = JExcelUtil.getContent(cell, columnIndex++).trim();

	File pFile = new File(FILESERVER + "\\" + filePath + "\\" + fileName);

	if (pFile.length() < 1) {
	    sb.append("해당 파일이 없습니다.[" + FILESERVER + "\\" + filePath + "\\" + fileName + "]\n");
	    isError = true;
	}
	pFile = null;

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

	if (!documentNo.equals("")) {

	    if (StringUtils.isNotEmpty(numberingCode1)) {

		if (StringUtils.contains(documentNo, "-")) {
		    String[] checkNo = StringUtils.split(documentNo, "-");

		    if (!checkNo[0].equals(numberingCode1)) {
			sb.append("문서번호 형식이 틀립니다.\n");
			isError = true;
		    }

		    if (StringUtils.isNotEmpty(numberingCode2)) {
			if (checkNo.length < 2) {
			    sb.append("문서번호 형식이 틀립니다.\n");
			    isError = true;
			} else {
			    if (!checkNo[1].equals(numberingCode2)) {
				sb.append("문서번호 형식이 틀립니다.\n");
				isError = true;
			    }
			}
		    }

		    if (StringUtils.isEmpty(numberingCode2) && StringUtils.isEmpty(partNo1)) {
			sb.append(categoryPath + " 는 부품번호1 필수입니다.\n");
			isError = true;
		    }

		    if (StringUtils.isEmpty(numberingCode2) && StringUtils.isNotEmpty(partNo1)) {

			if (!documentNo.equals(numberingCode1 + "-" + partNo1)) {
			    sb.append("부품번호1과 문서번호가 일치하지 않습니다.(" + documentNo + " != " + numberingCode1 + "-" + partNo1 + ")\n");
			    isError = true;
			}

		    }

		} else {
		    sb.append("문서번호 형식이 틀립니다⇒ - 미포함.\n");
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
	    WTPart wtpart1 = KETPartHelper.service.getPart(partNo1);
	    if (wtpart1 == null) {
		sb.append("[" + partNo1 + "]부품번호1이  맞지 않습니다.\n");
		isError = true;
	    }
	}

	String partNo2 = JExcelUtil.getContent(cell, columnIndex++).trim();
	if (!partNo2.equals("")) {
	    WTPart wtpart2 = KETPartHelper.service.getPart(partNo2);
	    if (wtpart2 == null) {
		sb.append("[" + partNo2 + "]부품번호2가  맞지 않습니다.\n");
		isError = true;
	    }
	}

	String partNo3 = JExcelUtil.getContent(cell, columnIndex++).trim();
	if (!partNo3.equals("")) {
	    WTPart wtpart3 = KETPartHelper.service.getPart(partNo3);
	    if (wtpart3 == null) {
		sb.append("[" + partNo3 + "]부품번호3이  맞지 않습니다.\n");
		isError = true;
	    }
	}

	String partNo4 = JExcelUtil.getContent(cell, columnIndex++).trim();
	if (!partNo4.equals("")) {
	    WTPart wtpart4 = KETPartHelper.service.getPart(partNo4);
	    if (wtpart4 == null) {
		sb.append("[" + partNo4 + "]부품번호4가  맞지 않습니다.\n");
		isError = true;
	    }
	}

	String partNo5 = JExcelUtil.getContent(cell, columnIndex++).trim();
	if (!partNo5.equals("")) {
	    WTPart wtpart5 = KETPartHelper.service.getPart(partNo5);
	    if (wtpart5 == null) {
		sb.append("[" + partNo5 + "]부품번호5가  맞지 않습니다.\n");
		isError = true;
	    }
	}

	if (isError) {
	    return sb;
	} else {
	    HashMap map = new HashMap();
	    map.put("divisionCode", divisionCode);
	    map.put("deptName", deptName);
	    map.put("documentNo", documentNo);
	    map.put("documentName", documentName);
	    map.put("documentDescription", documentDescription);
	    map.put("categoryCode", categoryCode);
	    map.put("fileName", FILESERVER + "\\" + filePath + "\\" + fileName);
	    map.put("projectNo1", projectNo1);
	    map.put("projectNo2", projectNo2);
	    map.put("projectNo3", projectNo3);
	    map.put("projectNo4", projectNo4);
	    map.put("projectNo5", projectNo5);
	    map.put("parentName", parentName);
	    map.put("taskName", taskName);
	    map.put("outputName", outputName);
	    map.put("partNo1", partNo1);
	    map.put("partNo2", partNo2);
	    map.put("partNo3", partNo3);
	    map.put("partNo4", partNo4);
	    map.put("partNo5", partNo5);
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

}
