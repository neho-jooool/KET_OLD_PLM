package e3ps.load.dms;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.HashMap;
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
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTProperties;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.util.JExcelUtil;
import e3ps.common.util.ManageSequence;
import e3ps.common.util.WCUtil;
import e3ps.cost.util.StringUtil;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETTechnicalCategoryLink;
import e3ps.dms.entity.KETTechnicalDocument;
import e3ps.dms.service.KETDmsHelper;
import e3ps.load.migration.edm.log.LogToFile;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

public class TechDocumentDataLoader implements wt.method.RemoteAccess, java.io.Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    private static String FILESERVER = "\\\\192.168.17.13";
    private static String EXCELFILE = "";
    private static String SEPARATOR = File.separator;
    private static String logFile = "techDocumentLoader.log";

    static {
	try {
	    String wt_home = WTProperties.getLocalProperties().getProperty("wt.home");
	    EXCELFILE = wt_home + SEPARATOR + "loadFiles\\ket\\migration\\dms";
	} catch (Exception e) {
	    Kogger.error(TechDocumentDataLoader.class, e);
	}
    }

    public static TechDocumentDataLoader manager = new TechDocumentDataLoader();

    public static boolean loadFile(String fileName) throws Exception {

	String filePath = EXCELFILE + SEPARATOR + fileName;
	File dataFile = new File(filePath);
	if (!dataFile.exists()) {
	    Kogger.debug(TechDocumentDataLoader.class, filePath + "==>File not found!!!");
	    return false;
	}

	logFile = EXCELFILE + SEPARATOR;
	int pidx = fileName.lastIndexOf(".");
	if (pidx > 0) {
	    logFile += fileName.substring(0, pidx);
	} else {
	    logFile = fileName;
	}
	logFile += ".log";
	Kogger.debug(TechDocumentDataLoader.class, "logFile==>" + logFile);
	
	dataFile = DRMHelper.Decryptupload(dataFile, dataFile.getName());
	
	Workbook wb = Workbook.getWorkbook(dataFile);
	Sheet sheets[] = wb.getSheets();
	Kogger.debug(TechDocumentDataLoader.class, "sheets==>" + sheets.length);
	Sheet sheet2 = sheets[3];

	String categoryCode = "";
	String categoryPath = "";
	HashMap categoryCodes = new HashMap();

	for (int i = 2; i < sheet2.getRows(); i++) {
	    Cell[] cell = sheet2.getRow(i);
	    categoryPath = JExcelUtil.getContent(cell, 0).trim();
	    categoryCode = JExcelUtil.getContent(cell, 1).trim();
	    categoryCodes.put(categoryPath, categoryCode);
	}

	Sheet sheet = sheets[1];
	int startRow = 2;
	Vector datas = new Vector();

	boolean result = false;
	for (int i = startRow; i < sheet.getRows(); i++) {
	    Cell[] cell = sheet.getRow(i);
	    Object obj = checkCellData(cell, i, categoryCodes);
	    if (obj instanceof StringBuffer) {
		StringBuffer sb = (StringBuffer) obj;
		Kogger.debug(TechDocumentDataLoader.class, sb.toString());
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

    public static String getFolderPath(String type) {
	String folderPath = "";

	if (type.equals("aRoot")) {
	    folderPath = "/Default/자동차사업부/문서";
	} else if (type.equals("eRoot")) {
	    folderPath = "/Default/전자사업부/문서";
	}

	return folderPath;
    }

    public static void upload(Vector datas) throws Exception {

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

	    String categoryPath = null;
	    KETTechnicalDocument d = null;
	    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	    KETTechnicalCategoryLink TDCLink = null;
	    File pFile = null;

	    for (int i = 0; i < datas.size(); i++) {
		Kogger.debug(TechDocumentDataLoader.class, "********************" + " migration start " + "********************");
		log("********************" + " migration start " + "********************");

		HashMap map = (HashMap) datas.get(i);

		divisionCode = (String) map.get("divisionCode");
		deptName = (String) map.get("deptName");

		documentNo = (String) map.get("documentNo");

		categoryCode = (String) map.get("categoryCode");
		categoryPath = KETDmsHelper.service.selectCategoryPath(categoryCode);

		KETDocumentCategory docCate = KETDmsHelper.service.selectDocCategory(categoryCode);

		if (documentNo.equals("")) {

		    String numberingCode1 = docCate.getAttribute2();
		    String numberingCode2 = docCate.getAttribute3();

		    if (StringUtils.isNotEmpty(numberingCode1)) {
			documentNo = numberingCode1 + "-";
			if (StringUtils.isNotEmpty(numberingCode2)) {
			    documentNo += numberingCode2 + "-";
			}
			documentNo += ManageSequence.getSeqNo(documentNo, "0000", "WTDocumentMaster", "WTDocumentNumber");
		    } else {

			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
			java.util.Date currentTime = new java.util.Date();
			String ymonth = formatter.format(currentTime).substring(2, 4) + formatter.format(currentTime).substring(5, 7);
			documentNo = "TD-" + ymonth;
			documentNo += ManageSequence.getSeqNo(documentNo, "000", "WTDocumentMaster", "WTDocumentNumber");
		    }
		    
		}

		documentName = (String) map.get("documentName");
		documentDescription = StringUtil.checkNull((String) map.get("documentDescription"));

		Kogger.debug(TechDocumentDataLoader.class, "==>" + documentName);
		log("==>" + documentName);

		d = KETTechnicalDocument.newKETTechnicalDocument();

		d.setDivisionCode(divisionCode);
		d.setDeptName(deptName);
		d.setNumber(documentNo);
		d.setTitle(documentName);
		d.setName(documentName);
		d.setDocumentDescription(documentDescription);
		d.setSecurity("S1");

		Folder cateFolder = null;
		if (divisionCode.equals("CA")) {
		    cateFolder = FolderHelper.service.getFolder(aRoot + categoryPath, WCUtil.getWTContainerRef());
		} else if (divisionCode.equals("ER")) {
		    cateFolder = FolderHelper.service.getFolder(eRoot + categoryPath, WCUtil.getWTContainerRef());
		} else {
		    cateFolder = FolderHelper.service.getFolder(aRoot + categoryPath, WCUtil.getWTContainerRef());
		}
		FolderHelper.assignLocation((FolderEntry) d, cateFolder);

		d = (KETTechnicalDocument) PersistenceHelper.manager.save(d);

		// 승인완료 상태로 셋팅함.
		LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) d, State.toState("APPROVED"));

		TDCLink = KETTechnicalCategoryLink.newKETTechnicalCategoryLink(docCate, d);
		TDCLink = (KETTechnicalCategoryLink) PersistenceHelper.manager.save(TDCLink);

		fileName = (String) map.get("fileName");
		pFile = new File(fileName);

		ContentHolder holder = ContentHelper.service.getContents(d);
		E3PSContentHelper.service.attach(holder, pFile, "", ContentRoleType.PRIMARY);

		Kogger.debug(TechDocumentDataLoader.class, "attached file ok==>" + fileName);
		pFile = null;

		Kogger.debug(TechDocumentDataLoader.class, "********************" + " migration success " + "********************");
		log("********************" + " migration success " + "********************");
	    }
	} catch (Exception e) {
	    Kogger.debug(TechDocumentDataLoader.class, "********************" + " migration fail " + "********************");
	    log("********************" + " migration fail " + "********************");
	    Kogger.error(TechDocumentDataLoader.class, e);
	}
    }

    public static Object checkCellData(Cell[] cell, int row, HashMap categoryCodes) throws Exception {

	StringBuffer sb = new StringBuffer();
	sb.append("엑셀 " + (row + 1) + " 라인");

	boolean isError = false;
	int columnIndex = 0;

	String divisionCode = JExcelUtil.getContent(cell, columnIndex++).trim();

	if (divisionCode.equals("자동차"))
	    divisionCode = "CA";
	else if (divisionCode.equals("전자"))
	    divisionCode = "ER";
	else {
	    sb.append("사업부구분이 맞지 않습니다." + divisionCode);
	    isError = true;
	}

	String deptName = JExcelUtil.getContent(cell, columnIndex++).trim();
	if (deptName.equals("")) {
	    sb.append("작성부서가 없습니다.");
	    isError = true;
	}

	String documentNo = JExcelUtil.getContent(cell, columnIndex++).trim();

	String documentName = JExcelUtil.getContent(cell, columnIndex++).trim();
	if (documentName.equals("")) {
	    sb.append("문서명이 없습니다.");
	    isError = true;
	}

	String documentDescription = JExcelUtil.getContent(cell, columnIndex++).trim();

	String categoryPath = JExcelUtil.getContent(cell, columnIndex++).trim();
	String categoryCode = (String) categoryCodes.get(categoryPath);
	KETDocumentCategory docCate = null;
	Kogger.debug(TechDocumentDataLoader.class, "categoryCode==>" + categoryCode);
	if (categoryCode == null) {
	    sb.append("분류체계코드가  존재하지 않습니다.");
	    isError = true;
	} else {
	    docCate = KETDmsHelper.service.selectDocCategory(categoryCode);
	}

	if (docCate == null) {
	    sb.append("분류체계코드가  맞지 않습니다.");
	    isError = true;
	}

	if (!documentNo.equals("")) {

	    String numberingCode1 = docCate.getAttribute2();
	    String numberingCode2 = docCate.getAttribute3();

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
		} else {
		    sb.append("문서번호 형식이 틀립니다⇒ - 미포함.\n");
		    isError = true;
		}

	    } else {

		if (!documentNo.substring(0, 2).equals("TD")) {
		    sb.append("문서번호 형식이 틀립니다.");
		    isError = true;
		}
	    }

	    if (!documentNo.substring(2, 3).equals("-")) {
		sb.append("문서번호 형식이 틀립니다..");
		isError = true;
	    }

	    QuerySpec query = new QuerySpec(wt.doc.WTDocumentMaster.class);
	    query.appendWhere(new SearchCondition(wt.doc.WTDocumentMaster.class, "number", "=", documentNo), new int[] { 0 });
	    // Kogger.debug(TechDocumentDataLoader.class, "query==>" + query.toString());
	    QueryResult qr = PersistenceHelper.manager.find(query);
	    if (qr.hasMoreElements()) {
		sb.append("이미 입력된 문서번호입니다(" + documentNo + ").\n");
		isError = true;
	    }
	}

	String fileName = JExcelUtil.getContent(cell, columnIndex++).trim();
	String filePath = JExcelUtil.getContent(cell, columnIndex++).trim();

	File pFile = new File(FILESERVER + "\\" + filePath + "\\" + fileName);
	if (!pFile.exists()) {
	    sb.append("\n").append("해당 파일이 없습니다.[" + FILESERVER + "\\" + filePath + "\\" + fileName + "]");
	    isError = true;
	}
	pFile = null;

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
	    return map;
	}
    }

    public static void log(String msg) {
	try {
	    LogToFile logger = new LogToFile(logFile, true);
	    logger.log(msg);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(TechDocumentDataLoader.class, e);
	}
    }

    public static void main(String[] args) {

	try {

	    String fileName = null;

	    if (args == null || args.length < 1)
		throw new Exception("@@ args need !");
	    else {
		fileName = args[0];
	    }

	    Kogger.debug(TechDocumentDataLoader.class, "@start");
	    TechDocumentDataLoader.manager.saveFromExcel(fileName);
	    Kogger.debug(TechDocumentDataLoader.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(TechDocumentDataLoader.class, e);
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
}
