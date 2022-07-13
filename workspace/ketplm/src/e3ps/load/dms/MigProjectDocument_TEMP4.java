package e3ps.load.dms;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pds.StatementSpec;
import wt.pom.DBProperties;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.query.ClassAttribute;
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
import e3ps.common.util.CommonUtil;
import e3ps.common.util.JExcelUtil;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentPartLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.service.KETDmsHelper;
import e3ps.ecm.beans.EcmUtil;
import e3ps.load.migration.edm.log.LogToFile;
import ext.ket.dms.entity.ProjectDocumentDTO;
import ext.ket.dms.service.KETProjectDocumentHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.SearchUtil;

//해당 프로그램은 UPDATE 용도로 사용한다. 즉 문서번호가 기입된 템플릿에 대해서만 유효하고 , 관련 파트삭제 후 재연결, 문서분류 삭제 후 재연결을 진행함
public class MigProjectDocument_TEMP4 implements RemoteAccess, Serializable {

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
    public static MigProjectDocument_TEMP4 manager = new MigProjectDocument_TEMP4();

    public MigProjectDocument_TEMP4() {

    }

    // windchill e3ps.load.dms.MigProjectDocument TEST.xlsx
    public static void main(String[] args) {

	try {

	    String fileName = null;

	    if (args == null || args.length < 1)
		throw new Exception("@@ args need !");
	    else {
		fileName = args[0];
	    }

	    Kogger.debug(MigProjectDocument_TEMP4.class, "@start");
	    MigProjectDocument_TEMP4.manager.saveFromExcel(fileName);
	    Kogger.debug(MigProjectDocument_TEMP4.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigProjectDocument_TEMP4.class, e);
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

	new ReferenceFactory();
	Transaction trx = null;
	try {

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    trx = new Transaction();
	    trx.start();

	    NumberCode number = NumberCodeHelper.manager.getNumberCode("VERSION", "LATEST");
	    NumberCodeOid = CommonUtil.getOIDLongValue2Str(number);

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

	    getFolderPath("aRoot");
	    getFolderPath("eRoot");
	    String documentNo = null;

	    String categoryCode = null;
	    String partNo1 = null;
	    String documentDescription = null;

	    KETProjectDocument d = null;

	    WTPart wtpart = null;
	    KETDocumentPartLink DpLink = null;

	    for (int i = 0; i < datas.size(); i++) {

		Kogger.debug(ProjectDocumentDataLoader.class, "********************" + " migration start " + "********************");
		log("********************" + " migration start " + "********************");

		HashMap map = (HashMap) datas.get(i);
		d = (KETProjectDocument) map.get("docObj");

		documentDescription = (String) map.get("documentDescription");

		d.setDocumentDescription(documentDescription);
		PersistenceServerHelper.manager.update(d);

		d = (KETProjectDocument) PersistenceHelper.manager.refresh(d);

		partNo1 = (String) map.get("partNo1");
		System.out.println("::::::::::::::::: 부품번호 ---- : " + partNo1);

		System.out.println("::::::::::::::::: documentNo :::::: " + documentNo);

		partNo1 = (String) map.get("partNo1");

		QueryResult r = PersistenceHelper.manager.navigate(d, "part", KETDocumentPartLink.class, false);

		while (r.hasMoreElements()) {
		    // KETContentHelper.service.delete(doc);
		    KETDocumentPartLink link = (KETDocumentPartLink) r.nextElement();
		    PersistenceHelper.manager.delete(link);
		}

		if (!partNo1.equals("")) {

		    String partNos[] = partNo1.split(",");

		    for (String partNo : partNos) {
			wtpart = PartBaseHelper.service.getLatestPart(partNo);

			DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
			DpLink = (KETDocumentPartLink) PersistenceHelper.manager.save(DpLink);
			log("==>partNo: " + partNo);
		    }
		}

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

    public Object checkCellData(Cell[] cell, int row, HashMap categoryCodes) throws Exception {

	StringBuffer sb = new StringBuffer();
	sb.append((row + 1) + " 라인===>");

	boolean isError = false;
	int columnIndex = 0;

	String divisionCode = JExcelUtil.getContent(cell, columnIndex++).trim();

	divisionCode = "CA";

	// String deptName = JExcelUtil.getContent(cell, columnIndex++).trim();
	// if (deptName.equals("")) {
	// sb.append("작성부서가 없습니다.\n");
	// isError = true;
	// }

	String id = JExcelUtil.getContent(cell, columnIndex++).trim();

	String documentNo = JExcelUtil.getContent(cell, columnIndex++).trim();

	String documentName = JExcelUtil.getContent(cell, columnIndex++).trim();

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

	String fileName = JExcelUtil.getContent(cell, columnIndex++).trim();
	// String filePath = JExcelUtil.getContent(cell, columnIndex++).trim();

	// pFile = null;

	File secondFile1 = null;
	File secondFile2 = null;
	File secondFile3 = null;

	columnIndex++;
	columnIndex++;
	columnIndex++;

	String projectNo1 = JExcelUtil.getContent(cell, columnIndex++).trim();

	String projectNo2 = JExcelUtil.getContent(cell, columnIndex++).trim();

	String projectNo3 = JExcelUtil.getContent(cell, columnIndex++).trim();

	String projectNo4 = JExcelUtil.getContent(cell, columnIndex++).trim();

	String projectNo5 = JExcelUtil.getContent(cell, columnIndex++).trim();

	JExcelUtil.getContent(cell, columnIndex++).trim();

	String partNo1 = JExcelUtil.getContent(cell, columnIndex++).trim();
	System.out.println("###$#$#$#$# partNo1 : " + partNo1);

	KETProjectDocument d = null;

	if (!documentNo.equals("")) {

	    ProjectDocumentDTO projectDocumentDTO = new ProjectDocumentDTO();
	    // projectDocumentDTO.setProjectDocType(categoryCode);

	    projectDocumentDTO.setVersion(NumberCodeOid);// 최신 문서만 가져오도록 한다

	    projectDocumentDTO.setDocumentNo(documentNo);
	    List<KETProjectDocument> list = getDocumentResult(projectDocumentDTO);

	    for (KETProjectDocument doc : list) {
		d = doc;
	    }

	    if (d == null) {
		sb.append("해당 번호의 문서가 없습니다(" + documentNo + ").\n");
		isError = true;
	    }

	}

	if (StringUtils.isEmpty(documentNo)) {
	    sb.append("문서번호가 누락되었습니다.\n");
	    isError = true;
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

	    }

	}

	if (isError) {
	    return sb;
	} else {
	    HashMap map = new HashMap();
	    map.put("divisionCode", divisionCode);
	    map.put("people", "");
	    map.put("documentNo", documentNo);
	    map.put("docObj", d);
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
	    map.put("parentName", "");
	    map.put("taskName", "");
	    map.put("outputName", "");
	    map.put("partNo1", partNo1);
	    // map.put("state", state);
	    /*
	     * map.put("partNo2", partNo2); map.put("partNo3", partNo3); map.put("partNo4", partNo4); map.put("partNo5", partNo5);
	     */
	    return map;
	}
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
