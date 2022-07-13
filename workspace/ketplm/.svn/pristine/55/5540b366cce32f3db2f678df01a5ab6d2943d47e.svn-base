package e3ps.load.dms;

import java.beans.PropertyVetoException;
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
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.JExcelUtil;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentPartLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.service.KETDmsHelper;
import e3ps.ecm.beans.EcmUtil;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;
import e3ps.load.migration.edm.log.LogToFile;
import ext.ket.dms.entity.ProjectDocumentDTO;
import ext.ket.dms.service.KETProjectDocumentHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.SearchUtil;

//해당 프로그램은 UPDATE 용도로 사용한다. 즉 문서번호가 기입된 템플릿에 대해서만 유효하고 , 관련정보 업데이트 후 첨부파일 삭제 후 재첨부, 관련 파트 삭제 후 재연결 작업을 수행한다 
public class MigProjectDocument_TEMP implements RemoteAccess, Serializable {

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
    public static MigProjectDocument_TEMP manager = new MigProjectDocument_TEMP();

    public MigProjectDocument_TEMP() {

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

	    Kogger.debug(MigProjectDocument_TEMP.class, "@start");
	    MigProjectDocument_TEMP.manager.saveFromExcel(fileName, delCategoryCode);
	    Kogger.debug(MigProjectDocument_TEMP.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigProjectDocument_TEMP.class, e);
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

	new ReferenceFactory();
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

	projectDocumentDTO.setVersion(NumberCodeOid);// 최신 문서만 가져오도록 한다

	List<KETProjectDocument> list = getDocumentResult(projectDocumentDTO);

	// String currentYear = DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(),"YYYY");

	for (KETProjectDocument doc : list) {

	    if (!doc.getNumber().startsWith("PD-")) {
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
	    String documentName = null;
	    String documentDescription = null;
	    String categoryCode = null;
	    String partNo1 = null;

	    KETProjectDocument d = null;
	    SessionHelper.manager.getPrincipal();

	    WTPart wtpart = null;
	    KETDocumentPartLink DpLink = null;
	    ProjectDocumentDTO projectDocumentDTO = new ProjectDocumentDTO();

	    for (int i = 0; i < datas.size(); i++) {
		d = null;
		Kogger.debug(ProjectDocumentDataLoader.class, "********************" + " migration start " + "********************");
		log("********************" + " migration start " + "********************");

		HashMap map = (HashMap) datas.get(i);

		People peo = (People) map.get("people");
		peo.getUser();
		SessionHelper.manager.setPrincipal(peo.getId());

		peo.getDepartment().getName();

		categoryCode = (String) map.get("categoryCode");

		KETDocumentCategory docCate = KETDmsHelper.service.selectDocCategory(categoryCode);

		partNo1 = (String) map.get("partNo1");
		System.out.println("::::::::::::::::: 부품번호 ---- : " + partNo1);
		String firstPartNo = "";
		if (!partNo1.equals("")) {

		    firstPartNo = partNo1.split(",")[0];
		}

		System.out.println("::::::::::::::::: 대표부품번호 ---- : " + firstPartNo);

		projectDocumentDTO.setProjectDocType(categoryCode);

		projectDocumentDTO.setVersion(NumberCodeOid);// 최신 문서만 가져오도록 한다

		documentNo = (String) map.get("documentNo");
		documentName = (String) map.get("documentName");
		documentDescription = (String) map.get("documentDescription");

		projectDocumentDTO.setDocumentNo(documentNo);
		List<KETProjectDocument> list = getDocumentResult(projectDocumentDTO);
		for (KETProjectDocument doc : list) {
		    d = doc;
		}

		boolean isNew = (d == null);

		if (!isNew) {

		    Identified localIdentified = (Identified) d.getMaster();
		    WTDocumentMasterIdentity identity = (WTDocumentMasterIdentity) localIdentified.getIdentificationObject();
		    identity.setName(documentName);
		    IdentityHelper.service.changeIdentity(localIdentified, identity);

		    d = (KETProjectDocument) PersistenceHelper.manager.refresh(d);

		    d.setTitle(documentName);
		    d.setDocumentDescription(documentDescription);

		    PersistenceServerHelper.manager.update(d);

		    d = (KETProjectDocument) PersistenceHelper.manager.refresh(d);

		    Kogger.debug(ProjectDocumentDataLoader.class, "==>" + documentName);
		    log("==>documentName : " + documentName);

		    System.out.println("::::::::::::::::: documentNo :::::: " + documentNo);

		    partNo1 = (String) map.get("partNo1");

		    if (!partNo1.equals("")) {

			QueryResult r = PersistenceHelper.manager.navigate(d, "part", KETDocumentPartLink.class, false);

			while (r.hasMoreElements()) {
			    // KETContentHelper.service.delete(doc);
			    KETDocumentPartLink link = (KETDocumentPartLink) r.nextElement();
			    PersistenceHelper.manager.delete(link);
			}

			String partNos[] = partNo1.split(",");

			for (String partNo : partNos) {
			    wtpart = PartBaseHelper.service.getLatestPart(partNo);

			    DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
			    DpLink = (KETDocumentPartLink) PersistenceHelper.manager.save(DpLink);
			    log("==>partNo: " + partNo);
			}
		    }

		    ContentDTO fileDto = KETContentHelper.manager.getPrimaryContent(d);

		    d = (KETProjectDocument) KETContentHelper.service.delete(d, (ApplicationData) CommonUtil.getObject(fileDto.getContentoid()));// 주첨부파일만
			                                                                                                                         // 삭제한다

		    String fileName = null;
		    File pFile = null;

		    fileName = (String) map.get("fileName");
		    pFile = new File(fileName);
		    ContentHolder holder = ContentHelper.service.getContents(d);
		    holder = E3PSContentHelper.service.attach(holder, pFile, "", ContentRoleType.PRIMARY);

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

	String fileName = JExcelUtil.getContent(cell, columnIndex++).trim();
	// String filePath = JExcelUtil.getContent(cell, columnIndex++).trim();

	File pFile = new File(FILESERVER + "\\" + fileName);

	// String state = "APPROVED";

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

	// String fileName_1 = JExcelUtil.getContent(cell, columnIndex++).trim(); // 부첨부파일 1 체크
	// secondFile1 = new File(FILESERVER + "\\" + fileName_1);
	// if (!fileCheck(fileName_1, sb, 1, secondFile1)) {
	// isError = true;
	// }
	//
	// String fileName_2 = JExcelUtil.getContent(cell, columnIndex++).trim(); // 부첨부파일 2 체크
	// secondFile2 = new File(FILESERVER + "\\" + fileName_2);
	// if (!fileCheck(fileName_2, sb, 2, secondFile2)) {
	// isError = true;
	// }
	//
	// String fileName_3 = JExcelUtil.getContent(cell, columnIndex++).trim(); // 부첨부파일 3 체크
	// secondFile3 = new File(FILESERVER + "\\" + fileName_3);
	// if (!fileCheck(fileName_3, sb, 3, secondFile3)) {
	// isError = true;
	// }

	String projectNo1 = JExcelUtil.getContent(cell, columnIndex++).trim();

	String projectNo2 = JExcelUtil.getContent(cell, columnIndex++).trim();

	String projectNo3 = JExcelUtil.getContent(cell, columnIndex++).trim();

	String projectNo4 = JExcelUtil.getContent(cell, columnIndex++).trim();

	String projectNo5 = JExcelUtil.getContent(cell, columnIndex++).trim();

	JExcelUtil.getContent(cell, columnIndex++).trim();

	String partNo1 = JExcelUtil.getContent(cell, columnIndex++).trim();
	System.out.println("###$#$#$#$# partNo1 : " + partNo1);
	if (!documentNo.equals("")) {

	    ProjectDocumentDTO projectDocumentDTO = new ProjectDocumentDTO();
	    projectDocumentDTO.setProjectDocType(categoryCode);

	    projectDocumentDTO.setVersion(NumberCodeOid);// 최신 문서만 가져오도록 한다

	    projectDocumentDTO.setDocumentNo(documentNo);
	    List<KETProjectDocument> list = getDocumentResult(projectDocumentDTO);
	    KETProjectDocument d = null;
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

	    SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, new ClassAttribute(WTPart.class, "versionInfo.identifier.versionSortId"));
	    // function.setColumnAlias(spec.getFromClause().getAliasAt(p_idx));

	    spec.appendSelect(new ClassAttribute(WTPartMaster.class, WTAttributeNameIfc.ID_NAME), new int[] { pm_idx }, false);
	    spec.appendSelect(new ClassAttribute(WTPartMaster.class, WTPartMaster.NUMBER), new int[] { pm_idx }, false);
	    spec.appendSelect(new ClassAttribute(WTPartMaster.class, WTPartMaster.NAME), new int[] { pm_idx }, false);
	    spec.appendSelect(function, new int[] { p_idx }, false);

	    spec.appendWhere(new SearchCondition(new ClassAttribute(WTPartMaster.class, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(WTPart.class, "masterReference.key.id")), new int[] {
		    pm_idx, p_idx });

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
