package ext.ket.edm.migration.stamping;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;

import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.team.TeamHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.WCUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import ext.ket.edm.entity.KETDrawingDistEpmLink;
import ext.ket.edm.entity.KETDrawingDistFileType;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.edm.entity.dto.KETDrawingDistReqDTO;
import ext.ket.edm.service.DrawingDistHelper;
import ext.ket.edm.service.PlmHpIfCtl;
import ext.ket.edm.stamping.jms.StampingQueueSender;
import ext.ket.edm.util.DrawingDistFileTypeEnum;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.part.migration.dao.StampDaoFactory;
import ext.ket.part.migration.dao.StampDaoManager;
import ext.ket.part.migration.dao.StampRowSetStrategy;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

public class MigHompageStampingGenerator implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigHompageStampingGenerator manager = new MigHompageStampingGenerator();

    public MigHompageStampingGenerator() {

    }

    // windchill ext.ket.edm.migration.stamping.MigHompageStampingGenerator
    // D:\ptc\Windchill_10.2\Windchill\loadFiles\ket\trans\edm\20170810.xlsx
    // 해당 프로그램의 용도 : 품번기준 고객제출도면을 찾아 (2D, 3D) 스탬핑서버의 변환 프로그램을 호출하여 파일변환을 한다 (pdf, iges, step, jpg) 변환된 파일을 홈페이지에 업데이트 하기 위함임
    // 구분 1: DB 속성정보 업데이트, 2 : 스탬핑 파일 추출 .. 작업순서는 스탬핑 파일을 추출 후 모두 변환이되면 DB 속성업데이트 한다. 파일 존재 유무까지 업데이트를 위해서다.
    public static void main(String[] args) {

	try {

	    String filePath = null;
	    String gubun = null;
	    String endDate = null;

	    if (args == null || args.length < 3)
		throw new Exception("@@ args need !");
	    else {
		filePath = args[0];
		gubun = args[1];
		endDate = args[2];
	    }

	    String toDayTime = "";
	    try {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd:HH-mm-ss");
		toDayTime = sdFormat.format(Calendar.getInstance().getTime());
	    } catch (Exception e) {
		Kogger.error("Exception : " + e.getMessage());
	    }

	    Kogger.debug(MigHompageStampingGenerator.class, "@start:" + toDayTime);
	    MigHompageStampingGenerator.manager.saveFromExcel(filePath, gubun, endDate);

	    toDayTime = "";
	    try {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd:HH-mm-ss");
		toDayTime = sdFormat.format(Calendar.getInstance().getTime());
	    } catch (Exception e) {
		Kogger.error("Exception : " + e.getMessage());
	    }

	    Kogger.debug(MigHompageStampingGenerator.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(MigHompageStampingGenerator.class, e);
	}
    }

    public void saveFromExcel(String filePath, String gubun, String endDate) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class, String.class, String.class };
		Object aobj[] = { filePath, gubun, endDate };

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
	    executeMigration(filePath, gubun, endDate);
	}
    }

    public void executeMigration(String filePath, String gubun, String endDate) throws Exception {

	SessionContext sessioncontext = SessionContext.newContext();
	try {

	    SessionHelper.manager.setAdministrator();

	    execute(filePath, gubun, endDate);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    public void execute(String filePath, String gubun, String endDate) throws Exception {

	File dataFile = new File(filePath);
	if (DRMHelper.useDrm) {
	    dataFile = DRMHelper.Decryptupload(dataFile, dataFile.getName());
	}

	ExcelHandle excel = ExcelFactory.getInstance().getExcelManager(dataFile);
	int sheetNo = 0;
	excel.setSheet(sheetNo);
	int startRowNo = 1;
	excel.setRow(startRowNo);
	int rowSize = excel.getSheetLastRowNum();
	List<String> drawingList = null;
	// List<String> drawingList2 = null;

	String path = "D:\\ptc\\Windchill_10.2\\Windchill\\loadFiles\\ket\\trans\\edm";
	List<String> folderList = new ArrayList<String>();

	Map<String, List<String>> epmList = new HashMap<String, List<String>>();

	for (int i = startRowNo; i <= rowSize; i++) {
	    excel.setRow(i);
	    String partNo = excel.getStrValue(0);

	    if ("1".equals(gubun)) {
		// 부품정보를 홈페이지로 이관
		WTPart part = PartBaseHelper.service.getLatestPart(partNo);
		String partOid = CommonUtil.getOIDString(part);
		System.out.println("partOid : " + partOid);
		if (StringUtils.isEmpty(partOid)) {
		    Kogger.debug(getClass(), "##################################################");
		    Kogger.debug(getClass(), "### 존재하지 않는 PartNO : " + partNo);
		    Kogger.debug(getClass(), "##################################################");
		    continue;
		}

		KETDrawingDistReqDTO paramObject = new KETDrawingDistReqDTO();

		paramObject.setDirect2Hompage("Y");
		paramObject.setPartOid(partOid);

		List<Map<String, Object>> distReqEpmDocList = DrawingDistHelper.service.sendHpList(paramObject);

		// drawingList = searchKetHompageEPMByCustomer(partNo);//품번으로 연계 고객제출도를 찾아온다
		//
		// drawingList2 = searchStampingEpmNo(drawingList, endDate); // 연계 고객제출도면을 in 으로 stamping 성공한 것을 찾는다
		//
		// for (Map<String, Object> epmDocument : distReqEpmDocList) {
		//
		// for (String TITLE : drawingList2) {
		//
		// TITLE = StringUtils.removeStart(TITLE, "CU_");
		// if (StringUtils.contains(TITLE, "3D")) {
		//
		// epmDocument.put("spImgFile", StringUtils.removeEnd(TITLE, "_3D") + "_img.jpg");
		// epmDocument.put("spStepFile", TITLE + ".stp");
		// epmDocument.put("sp3DpdfFile", TITLE + ".pdf");
		// epmDocument.put("spIgsFile", TITLE + ".igs");
		//
		// System.out.println("PARTNO : "+partNo+" TITLE : " + StringUtils.removeEnd(TITLE, "_3D") + "_img.jpg");
		//
		// } else {
		//
		// if (StringUtils.endsWithIgnoreCase(TITLE, "2D")) {
		// epmDocument.put("sp2DpdfFile", TITLE + ".pdf");
		// System.out.println("PARTNO : "+ partNo + " TITLE : " +TITLE + ".pdf");
		// } else {
		// epmDocument.put("sp2DpdfFile", TITLE + "_2D.pdf");
		// System.out.println("PARTNO : "+ partNo + " TITLE : " +TITLE + "_2D.pdf");
		// }
		// }
		//
		// }
		// }

		// distReqEpmDocList 정보로 mssql 에 저장 후 리턴
		PlmHpIfCtl send = new PlmHpIfCtl();
		boolean bSuccess = send.savePlmHpIf(distReqEpmDocList);

		if (!bSuccess) {
		    System.out.println("##################################################");
		    System.out.println("### 부품정보 홈페이지 이관 실패 : " + partNo);
		    System.out.println("##################################################");
		}
		// 부품정보 홈페이지 이관 종료
	    }

	    // 도면변환
	    if ("2".equals(gubun)) {
		drawingList = searchKetHompageEPMByCustomer(partNo);

		List<String> numberList = new ArrayList<String>();

		for (String epmOid : drawingList) {
		    KETDrawingDistRequest drawingDistReq = createDrawingDistRequest(epmOid, partNo, numberList);

		    String reqId = drawingDistReq.getNumber();
		    Kogger.debug(getClass(), "##################################################");
		    Kogger.debug(getClass(), "### 배포요청서 Queue 입력 " + reqId);
		    Kogger.debug(getClass(), "##################################################");

		    StampingQueueSender sender = StampingQueueSender.getInstance();
		    sender.sendMessage(drawingDistReq.getNumber(), CommonUtil.getOIDString(drawingDistReq));

		    Kogger.debug(getClass(), "### Queue : Sended");
		}
	    }

	    // 폴더별 파일 만들기
	    if ("3".equals(gubun)) {
		String folderName = "";

		String removeHpartNumber = StringUtils.removeStart(partNo, "H");
		String rePartNumber = removeHpartNumber.substring(0, 1);

		if ("3".equals(rePartNumber)) {
		    folderName = "WH" + removeHpartNumber;
		} else if ("4".equals(rePartNumber)) {
		    folderName = "EM" + removeHpartNumber;
		} else if ("5".equals(rePartNumber)) {
		    folderName = "JB" + removeHpartNumber;
		} else if ("6".equals(rePartNumber)) {
		    folderName = "MG" + removeHpartNumber;
		} else if ("7".equals(rePartNumber)) {
		    folderName = "ST" + removeHpartNumber;
		} else if ("8".equals(rePartNumber)) {
		    folderName = "PG" + removeHpartNumber;
		} else {
		    folderName = partNo;
		}

		String targetPath = path + "\\" + folderName;
		makeFolder(targetPath);

		folderList.add(targetPath);
		List<String> drwList = searchKetHompageEPMByCustomer(partNo);

		epmList.put(partNo, drwList);

		for (String oid : drwList) {

		    EPMDocument epm = (EPMDocument) CommonUtil.getObject(oid);

		    String matchFileName = epm.getNumber();

		    String reNameFileName = StringUtils.removeStart(folderName, "CU_");
		    reNameFileName = StringUtils.removeEnd(folderName, "_3D");
		    reNameFileName = StringUtils.removeEnd(folderName, "_2D");

		    if (folderName.equals(reNameFileName)) {
			moveFile(path, targetPath, matchFileName);
		    }
		}
	    }
	}

	if ("3".equals(gubun)) {

	    for (String targetPath : folderList) {

		if (isFileEmptyDir(targetPath)) {

		    for (int i = startRowNo; i <= rowSize; i++) {
			excel.setRow(i);
			String partNo = excel.getStrValue(0);
			List<String> drwList = epmList.get(partNo);

			for (String oid : drwList) {

			    EPMDocument epm = (EPMDocument) CommonUtil.getObject(oid);

			    String matchFileName = epm.getNumber();

			    moveFile(path, targetPath, matchFileName);

			}
		    }
		}
		delFolder(targetPath);
	    }

	}

    }

    private void moveFile(String orgPath, String targetPath, String matchFileName) {

	List<File> targetFileList = new ArrayList<File>();

	for (File info : FileUtils.listFiles(new File(orgPath), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {

	    String ext = FilenameUtils.getExtension(info.getName());

	    String fileName = info.getName();
	    fileName = StringUtils.removeEnd(fileName, "." + ext);
	    fileName = StringUtils.removeEnd(fileName, "_s");

	    if (matchFileName.equals(fileName) && ("igs".equalsIgnoreCase(ext) || "jpg".equalsIgnoreCase(ext) || "pdf".equalsIgnoreCase(ext) || "stp".equalsIgnoreCase(ext))) {
		targetFileList.add(info);

		String reNameFileName = StringUtils.removeStart(matchFileName, "CU_");

		if (StringUtils.contains(reNameFileName, "3D")) {

		    if ("jpg".equals(ext)) {
			reNameFileName = StringUtils.removeEnd(reNameFileName, "_3D");
			reNameFileName = reNameFileName + "_img.jpg";
		    } else {
			reNameFileName = reNameFileName + "." + ext;
		    }

		} else {

		    if (StringUtils.endsWithIgnoreCase(reNameFileName, "2D")) {
			reNameFileName = reNameFileName + ".pdf";

		    } else {
			reNameFileName = reNameFileName + "_2D.pdf";

		    }
		}

		info.renameTo(new File(targetPath + "\\" + reNameFileName));
	    }

	}
    }

    private boolean isFileEmptyDir(String targetPath) {
	File dir = new File(targetPath);

	int fileCnt = 0;

	if (dir.exists() && dir.isDirectory()) {
	    File[] fileList = dir.listFiles();
	    for (File tFile : fileList) {
		if (!tFile.isDirectory()) {
		    fileCnt++;
		}
	    }
	}

	return fileCnt == 0;
    }

    private void delFolder(String targetPath) {

	File dir = new File(targetPath);

	if (isFileEmptyDir(targetPath)) {
	    System.out.println("파일없는 폴더 삭제 : " + dir);
	    dir.delete();
	}
    }

    private void makeFolder(String path) {

	File Folder = new File(path);

	if (!Folder.exists()) {
	    try {
		Folder.mkdir();
	    } catch (Exception e) {
		e.getStackTrace();
	    }
	}
    }

    private KETDrawingDistRequest createDrawingDistRequest(String epmOid, String partNo, List<String> numberList) throws Exception {

	SessionContext sessioncontext = SessionContext.newContext();
	Transaction trx = null;
	KETDrawingDistRequest drawingDistReq = null;

	try {

	    trx = new Transaction();
	    trx.start();
	    EPMDocument distEpm = (EPMDocument) CommonUtil.getObject(epmOid);

	    drawingDistReq = KETDrawingDistRequest.newKETDrawingDistRequest();

	    String distNumber = this.getNumber(partNo);

	    drawingDistReq.setNumber(distEpm.getNumber() + "(" + distNumber + ")");
	    drawingDistReq.setName(distEpm.getNumber());
	    drawingDistReq.setTitle(distEpm.getNumber());
	    // drawingDistReq.setDescription(paramObject.getDescription());

	    drawingDistReq.setDistType("APPROVED");
	    drawingDistReq.setDistReason("홈페이지 배포용");
	    drawingDistReq.setRefPart(partNo);
	    // drawingDistReq.setDownloadExpireDate(null);
	    // drawingDistReq.setDistSubcontractor(paramObject.getDistSubcontractor());
	    // drawingDistReq.setDistDeptName(paramObject.getDistDeptName());

	    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	    Department userDept = DepartmentHelper.manager.getDepartment(wtuser);
	    String writeDeptKrName = (userDept == null) ? "" : userDept.getName();
	    String writeDeptCode = (userDept == null) ? "" : userDept.getCode();
	    drawingDistReq.setWriteDeptKrName(writeDeptKrName);
	    drawingDistReq.setWriteDeptEnName(userDept.getEnName());
	    drawingDistReq.setWriteDeptCode(writeDeptCode);
	    drawingDistReq.setBackgroundYn("Y");
	    WTContainerRef containerRef = WCUtil.getWTContainerRef();
	    String lcName = "KET_COMMON_LC";
	    LifeCycleHelper.setLifeCycle((LifeCycleManaged) drawingDistReq, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

	    String folderPath = "/Default/자동차사업부/초기유동/";

	    SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	    FolderHelper.assignLocation((FolderEntry) drawingDistReq, folder);
	    drawingDistReq = (KETDrawingDistRequest) TeamHelper.setTeamTemplate(drawingDistReq, TeamHelper.service.getTeamTemplate("Default"));
	    drawingDistReq = (KETDrawingDistRequest) PersistenceHelper.manager.save(drawingDistReq);

	    // 배포 포멧
	    String[] drawingDistFileTypeArrayItems = { "PDF", "DWG", "STEP", "IGS", "JPG", "THREEPDF" };
	    for (String fileTypeItem : drawingDistFileTypeArrayItems) {

		KETDrawingDistFileType fileTypes = KETDrawingDistFileType.newKETDrawingDistFileType();
		DrawingDistFileTypeEnum drawingDistFileType = DrawingDistFileTypeEnum.valueOf(fileTypeItem);
		fileTypes.setDistFileType(drawingDistFileType.toString());
		fileTypes.setDistReq(drawingDistReq);
		PersistenceHelper.manager.save(fileTypes);
	    }

	    // 배포 도면
	    KETDrawingDistEpmLink epmLink = KETDrawingDistEpmLink.newKETDrawingDistEpmLink(distEpm, drawingDistReq);
	    epmLink.setSheetType("All");
	    PersistenceHelper.manager.save(epmLink);

	    numberList.add(distNumber);
	    trx.commit();
	    trx = null;

	} catch (Exception e) {

	    Kogger.debug(getClass(), "## 정의되지 않은 에러가 발생했습니다.");
	    Kogger.error(getClass(), e);
	    throw e;

	} finally {

	    if (trx != null)
		trx.rollback();

	    SessionContext.setContext(sessioncontext);
	}

	return drawingDistReq;
    }

    public String getNumber(String partNo) throws Exception {

	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	String number = "";

	try {

	    StringBuffer sql = new StringBuffer();

	    sql.append("select count(*) as distNumber from KETDrawingDistRequest where refpart = '" + partNo + "'");

	    String query = sql.toString();

	    number = oDaoManager.queryForObject(query, new StampRowSetStrategy<String>() {
		@Override
		public String mapRow(ResultSet rs) throws SQLException {
		    String number = rs.getString("distNumber");
		    return number;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}

	return number;
    }

    private List<String> searchKetHompageEPMByCustomer(String partNo) throws Exception {

	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List<String> drwList = new ArrayList<String>();

	try {

	    StringBuffer sb = new StringBuffer();
	    sb.append(" SELECT EPM.CLASSNAMEA2A2||':'||EPM.IDA2A2 as OID                                                                                           \n");
	    sb.append("   FROM EPMDOCUMENTMASTER EM                                                                                  \n");
	    sb.append("       ,EPMDOCUMENT EPM                                                                                       \n");
	    sb.append("       ,(SELECT B.IDA3A5 FROM WTPARTMASTER A, EPMLINK B                                                       \n");
	    sb.append("          WHERE WTPARTNUMBER = '" + partNo + "'                                                                      \n");
	    sb.append("            AND A.IDA2A2 = B.IDA3B5                                                       \n");
	    sb.append("            AND REFERENCETYPE = 'CU_DRAWING') LINK                                                            \n");
	    sb.append("  WHERE EM.IDA2A2 = LINK.IDA3A5                                                                               \n");
	    sb.append("    AND EM.IDA2A2 = EPM.IDA3MASTERREFERENCE                                                                   \n");
	    sb.append("    AND EPM.LATESTITERATIONINFO = 1                                                                           \n");
	    sb.append("    AND EPM.STATECHECKOUTINFO IN ('c/i')                                                                      \n");
	    sb.append("    AND EPM.VERSIONIDA2VERSIONINFO = ( SELECT TO_CHAR(MAX(TO_NUMBER(E.VERSIONIDA2VERSIONINFO)))               \n");
	    sb.append("                                         FROM EPMDOCUMENT E                                                   \n");
	    sb.append("                                        WHERE E.IDA3MASTERREFERENCE = EPM.IDA3MASTERREFERENCE                 \n");
	    sb.append("                                          AND E.STATECHECKOUTINFO NOT IN ('term','wrk','to del','to wrk')     \n");
	    sb.append("                                          AND E.LATESTITERATIONINFO = 1 )                                     \n");

	    String query = sb.toString();

	    drwList = oDaoManager.queryForList(query, new StampRowSetStrategy<String>() {
		@Override
		public String mapRow(ResultSet rs) throws SQLException {

		    String oid = rs.getString("OID");
		    return oid;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}

	return drwList;
    }

    private List<String> searchStampingEpmNo(List<String> drawingList, String endDate) throws Exception {

	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List<String> drwList = new ArrayList<String>();

	String epmNos = "";

	for (String oid : drawingList) {
	    String empNo = ((EPMDocument) CommonUtil.getObject(oid)).getNumber();
	    epmNos += "'" + empNo + "',";
	}

	epmNos = StringUtils.removeEnd(epmNos, ",");

	try {

	    StringBuffer sb = new StringBuffer();
	    sb.append(" SELECT A.TITLE                                                   ");
	    sb.append("   FROM KETDrawingDistRequest A, KETStamping S,KETStampDistLink L ");
	    sb.append("  WHERE 1=1                                                       ");
	    sb.append("    AND A.DISTTYPE = 'APPROVED'                                   ");
	    sb.append("    AND A.BackgroundYn = 'Y'                                      ");
	    sb.append("    AND L.IDA3B5 = A.IDA2A2                                       ");
	    sb.append("    AND S.IDA2A2 = L.IDA3A5                                       ");
	    sb.append("    AND STAMPSTATUS = 'SUCCESS'                                   ");
	    sb.append("    AND TITLE IN (" + epmNos + ")                                     ");
	    sb.append("    AND TO_CHAR(A.CREATESTAMPA2,'YYYYMMDD') >= '" + endDate + "'                      ");
	    sb.append("    AND REFPART IS NOT NULL ");

	    String query = sb.toString();

	    drwList = oDaoManager.queryForList(query, new StampRowSetStrategy<String>() {
		@Override
		public String mapRow(ResultSet rs) throws SQLException {

		    String TITLE = rs.getString("TITLE");
		    return TITLE;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}

	return drwList;
    }

}
