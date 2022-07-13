package e3ps.load.migration.edm;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import wt.clients.folder.FolderTaskLogic;
import wt.clients.vc.CheckInOutTaskLogic;
import wt.content.ContentHelper;
import wt.content.ContentRoleType;
import wt.epm.EPMApplicationType;
import wt.epm.EPMAuthoringAppType;
import wt.epm.EPMContextHelper;
import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.epm.EPMDocumentType;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.pds.StatementSpec;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.util.WTPropertyVetoException;

import com.ptc.windchill.cadx.common.EPMDocumentUtilities;
import com.ptc.windchill.cadx.common.WTPartUtilities;

import e3ps.common.code.NumberCode;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.JExcelUtil;
import e3ps.edm.CADCategory;
import e3ps.edm.CADManageType;
import e3ps.edm.EDMUserData;
import e3ps.edm.EPMLink;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.beans.EDMServiceHelper;
import e3ps.edm.util.EDMAttributeUtil;
import e3ps.edm.util.EDMEnumeratedTypeUtil;
import e3ps.edm.util.EDMProperties;
import e3ps.edm.util.EDMUtil;
import e3ps.load.migration.edm.log.LogToFile;
import e3ps.project.E3PSProject;
import e3ps.project.beans.ProjectHelper;
import ext.ket.shared.log.Kogger;

public class EDMEcadMigDataLoader implements wt.method.RemoteAccess, java.io.Serializable {


    static final boolean   SERVER       = wt.method.RemoteMethodServer.ServerFlag;

    private static String  FILESERVER   = "\\\\192.168.17.13";
    private static String  EXCELFILE    = "";

    private static String  SEPARATOR    = File.separator;

    static {
	try {
	    String wt_home = WTProperties.getLocalProperties().getProperty("wt.home");
	    EXCELFILE = wt_home + SEPARATOR + "loadFiles\\ket\\migration\\edm";
	} catch (Exception e) {
	    Kogger.error(EDMEcadMigDataLoader.class, e);
	}
    }

    private static HashMap bizMap;
    private static HashMap catMgtMap;

    private static boolean IS_CHECK_DRM = false;

    private static String  logFile      = "mylogfile.log";

    static {
	Config conf = ConfigImpl.getInstance();
	IS_CHECK_DRM = conf.getBoolean("DRM");
    }

    public static boolean loadFile(String fileName, String type) throws WTException {
	try {
	    String filePath = EXCELFILE + SEPARATOR + fileName;
	    File dataFile = new File(filePath);
	    if (!dataFile.exists()) {
		Kogger.debug(EDMEcadMigDataLoader.class, "File not found!!!");
		return false;
	    }

	    if (IS_CHECK_DRM) {
		File drmFile = E3PSDRMHelper.upload(dataFile, fileName);
		dataFile = drmFile;
	    }

	    logFile = EXCELFILE + SEPARATOR;
	    int pidx = fileName.lastIndexOf(".");
	    if (pidx > 0) {
		logFile += fileName.substring(0, pidx);
	    }
	    else {
		logFile = fileName;
	    }
	    logFile += ".log";

	    EDMProperties edmProperties = EDMProperties.getInstance();

	    bizMap = getNumberCodeMap(edmProperties.getBizCodeType());
	    catMgtMap = getCategoryType();

	    Iterator itr = bizMap.keySet().iterator();
	    while (itr.hasNext()) {
		Kogger.debug(EDMEcadMigDataLoader.class, ">" + (String) itr.next());
	    }

	    Workbook workbook = Workbook.getWorkbook(dataFile);
	    Sheet sheets[] = workbook.getSheets();

	    Vector lds = new Vector();

	    if (sheets.length > 0) {
		Sheet sheet = null;
		for (int i = 0; i < sheets.length; i++) {
		    sheet = sheets[i];

		    int rows = sheet.getRows();

		    Cell hdrs[] = sheet.getRow(0);
		    for (int k = 0; k < hdrs.length; k++) {
			Cell hdr = hdrs[k];
			Kogger.debug(EDMEcadMigDataLoader.class, "Column name : " + hdr.getContents());
		    }

		    for (int k = 1; k < rows; k++) {
			//Kogger.debug(getClass(), "current row : " + i);
			//Cell cell[] = sheet.getRow(k);
			EDMEcadMigData rdata = (EDMEcadMigData) getRowData(sheet.getRow(k), k + 1, sheet.getName());

			if (rdata != null) {
			    if (!rdata.isValidate) {
				log(rdata.msg);
			    }
			    else {
				Vector v = new Vector();
				v.add(rdata);

				Vector v0 = (Vector) save(v);
				for (int m = 0; m < v0.size(); m++) {
				    EDMEcadMigData rdata0 = (EDMEcadMigData) v0.get(m);
				    if (!(rdata0.isLoadCompleted)) {
					log(rdata0.msg);
				    }
				}
			    }
			}
		    }
		}
	    }
	    workbook.close();
	    workbook = null;
	} catch (Exception e) {
	    Kogger.error(EDMEcadMigDataLoader.class, e);
	    throw new WTException("file name = " + fileName);
	}
	return true;
    }

    public static void log(String msg) {
	try {
	    LogToFile logger = new LogToFile(logFile, true);
	    logger.log(msg);
	} catch (IOException e) {
	    Kogger.error(EDMEcadMigDataLoader.class, e);
	}
    }


    public static Object getRowData(Cell[] cells, int rownum, String sheetName) throws WTException {
	EDMEcadMigData data = null;

	StringBuffer sb = new StringBuffer();
	try {
	    if (JExcelUtil.getContent(cells, 0).length() == 0) {
		return null;
	    }

	    sb.append("\n********** >>> [ row:" + rownum + " ]-[ sheet:" + sheetName + " ] <<< *********");

	    data = new EDMEcadMigData(rownum);
	    data.setCellData(cells);

	    //사업부
	    if (bizMap.containsKey(data.bizName)) {
		data.bizCode = (NumberCode) bizMap.get(data.bizName);
	    }
	    else {
		data.isValidate = false;
		sb.append("\n").append("-사업부\t\t\t:정의되지 않은 사업부입니다.[" + data.bizName + "]");
	    }

	    //작성부서

	    //도면구분
	    if (data.cadDevStageName.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("-도면구분\t\t\t:입력값이 없습니다.");
	    }
	    else {
		data.devStage = EDMEnumeratedTypeUtil.getDevStage(data.cadDevStageName, Locale.KOREAN);
	    }

	    //도면유형
	    if (data.cadCategoryName.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("-도면유형\t\t\t:입력값이 없습니다.");
	    }
	    else {
		data.category = EDMEnumeratedTypeUtil.getCADCategory(data.cadCategoryName, Locale.KOREAN);
	    }

	    //CAD종류
	    if (data.cadAppTypeName.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("-CAD종류\t\t\t:입력값이 없습니다.");
	    }
	    else {
		data.cadAppType = data.cadAppTypeName;
	    }

	    //프로젝트번호
	    if (data.pjtno.length() > 0) {
		data.project = getProject(data.pjtno);
		if ((data.project == null)) {
		    data.isValidate = false;
		    sb.append("\n").append("-프로젝트번호\t\t\t:프로젝트가 존재하지 않습니다.[" + data.pjtno + "]");
		}
	    }

	    //제품/금형 도면
	    data.manageType = (String) catMgtMap.get(data.category);


	    EPMDocument epmPcb = null;
	    EPMDocument epmSch = null;
	    EPMDocument epmDwg = null;

	    if (data.numberPcb.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("-PCB도면번호*\t\t\t:입력값이 없습니다.");
	    }
	    else {
		epmPcb = EPMDocumentUtilities.getEPMDocument(data.numberPcb.toUpperCase());
		if (epmPcb != null) {
		    data.isExistPcb = true;
		    data.isValidate = false;
		    sb.append("\n").append("-PCB도면번호*\t\t\t:이미 등록된 도면입니다.[" + data.numberPcb + "]");
		}
	    }

	    if (data.namePcb.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("-PCB도면명*\t\t\t:입력값이 없습니다.");
	    }

	    if (data.cadVersionPcb.length() == 0) {
		data.cadVersionPcb = "0";
	    }


	    //PCB Dir/File
	    if ((data.cadFileDirPcb.length() == 0)) {
		data.isValidate = false;
		sb.append("\n").append("-PCB파일위치(절대경로)*\t\t:입력값이 없습니다.");
	    }
	    else {
		data.dirPcb = new File(FILESERVER + SEPARATOR + data.cadFileDirPcb);
		if (!data.dirPcb.exists()) {
		    data.isValidate = false;
		    sb.append("\n").append("-PCB파일위치(절대경로)*\t\t:폴더가 없습니다.[" + data.cadFileDirPcb + "]");
		}
	    }

	    if ((data.cadFileNamePcb.length() == 0)) {
		data.isValidate = false;
		sb.append("\n").append("-PCB파일명*\t\t\t:입력값이 없습니다.");
	    }
	    else {
		String fileDir = FILESERVER + SEPARATOR + data.cadFileDirPcb + SEPARATOR;

		data.primaryPcb = new File(fileDir + data.cadFileNamePcb);
		if (!data.primaryPcb.exists()) {
		    data.isValidate = false;
		    sb.append("\n").append("-PCB파일명*\t\t\t:해당 파일이 없습니다.[" + data.cadFileNamePcb + "]");
		}
	    }

	    //Gerber Dir/File
	    if ((data.cadFileDirGerber.length() == 0)) {
		//data.isValidate = false;
		//sb.append("\n").append("-GERBER파일위치:입력값이 없습니다.");
	    }
	    else {
		data.dirGerber = new File(FILESERVER + SEPARATOR + data.cadFileDirGerber);
		if (!data.dirGerber.exists()) {
		    data.isValidate = false;
		    sb.append("\n").append("-GERBER파일위치\t\t\t:폴더가 없습니다.[" + data.cadFileDirGerber + "]");
		}
	    }

	    if ((data.cadFileNameGerber.length() == 0)) {
		//data.isValidate = false;
		//sb.append("\n").append("-GERBER파일명:입력값이 없습니다.");
	    }
	    else {
		String fileDir = FILESERVER + SEPARATOR + data.cadFileDirGerber + SEPARATOR;

		data.primaryGerber = new File(fileDir + data.cadFileNameGerber);
		if (!data.primaryGerber.exists()) {
		    data.isValidate = false;
		    sb.append("\n").append("-GERBER파일명\t\t\t:해당 파일이 없습니다.[" + data.cadFileNameGerber + "]");
		}
	    }


	    //SCHEMATIC
	    if (data.numberSch.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("-SCHEMATIC도면번호*\t\t:입력값이 없습니다.");
	    }
	    else {
		epmSch = EPMDocumentUtilities.getEPMDocument(data.numberSch.toUpperCase());
		if (epmSch != null) {
		    data.isExistSch = true;
		    data.isValidate = false;
		    sb.append("\n").append("-SCHEMATIC도면번호*\t\t:이미 등록된 도면입니다.[" + data.numberSch + "]");
		}
	    }

	    if (data.nameSch.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("-SCHEMATIC도면명*\t\t:입력값이 없습니다.");
	    }

	    if (data.cadVersionSch.length() == 0) {
		data.cadVersionSch = "0";
	    }


	    //Schematic Dir/File
	    if ((data.cadFileDirSch.length() == 0)) {
		data.isValidate = false;
		sb.append("\n").append("-SCHEMATIC파일위치(절대경로)*\t:입력값이 없습니다.");
	    }
	    else {
		data.dirSch = new File(FILESERVER + SEPARATOR + data.cadFileDirSch);
		if (!data.dirSch.exists()) {
		    data.isValidate = false;
		    sb.append("\n").append("-SCHEMATIC파일위치(절대경로)*\t:폴더가 없습니다.[" + data.cadFileDirSch + "]");
		}
	    }

	    if ((data.cadFileNameSch.length() == 0)) {
		data.isValidate = false;
		sb.append("\n").append("-SCHEMATIC파일명*\t\t:입력값이 없습니다.");
	    }
	    else {
		String fileDir = FILESERVER + SEPARATOR + data.cadFileDirSch + SEPARATOR;

		data.primarySch = new File(fileDir + data.cadFileNameSch);
		if (!data.primarySch.exists()) {
		    data.isValidate = false;
		    sb.append("\n").append("-SCHEMATIC파일명*\t\t:해당 파일이 없습니다.[" + data.cadFileNameSch + "]");
		}
	    }


	    boolean insertDwg = false;
	    if ((data.numberDwg.length() > 0) || (data.cadFileNameDwg.length() > 0)) {
		insertDwg = true;
	    }

	    if (insertDwg) {
		//AutoCAD
		if ((data.numberDwg.length() == 0)) {
		    data.isValidate = false;
		    sb.append("\n").append("-AUTOCAD도면번호\t\t:입력값이 없습니다.");
		}
		else {
		    epmDwg = EPMDocumentUtilities.getEPMDocument(data.numberDwg.toUpperCase());
		    if (epmDwg != null) {
			data.isExistDwg = true;
			data.isValidate = false;
			sb.append("\n").append("-AUTOCAD도면번호\t\t:이미 등록된 도면입니다.[" + data.numberDwg + "]");
		    }
		}

		if ((data.nameDwg.length() == 0)) {
		    data.isValidate = false;
		    sb.append("\n").append("-AUTOCAD도면명\t\t:입력값이 없습니다.");
		}

		if (data.cadVersionDwg.length() == 0) {
		    data.cadVersionDwg = "0";
		}


		//AutoCAD Dir/File
		if ((data.cadFileDirDwg.length() == 0)) {
		    data.isValidate = false;
		    sb.append("\n").append("-AUTOCAD파일위치(절대경로)\t:입력값이 없습니다.");
		}
		else {
		    data.dirDwg = new File(FILESERVER + SEPARATOR + data.cadFileDirDwg);
		    if (!data.dirDwg.exists()) {
			data.isValidate = false;
			sb.append("\n").append("-AUTOCAD파일위치(절대경로)\t:폴더가 없습니다.[" + data.cadFileDirDwg + "]");
		    }
		}

		if ((data.cadFileNameDwg.length() == 0)) {
		    data.isValidate = false;
		    sb.append("\n").append("-AUTOCAD파일명\t\t\t:입력값이 없습니다.");
		}
		else {
		    String fileDir = FILESERVER + SEPARATOR + data.cadFileDirDwg + SEPARATOR;

		    data.primaryDwg = new File(fileDir + data.cadFileNameDwg);
		    if (!data.primaryDwg.exists()) {
			data.isValidate = false;
			sb.append("\n").append("-AUTOCAD파일명\t\t\t:해당 파일이 없습니다.[" + data.cadFileNameDwg + "]");
		    }
		}
	    }


	    WTPart rpart = null;
	    //대표 부품 존재 여부 check
	    if (data.repPartNumber.trim().length() > 0) {
		rpart = WTPartUtilities.getWTPart(data.repPartNumber.toUpperCase());
		if (rpart == null) {
		    data.isValidate = false;
		    data.isRepPartExist = false;
		    sb.append("\n").append("-대표부품번호\t\t\t:등록된 부품이 없습니다.[" + data.repPartNumber + "]");
		}
		else {
		    data.repPoid = rpart.getPersistInfo().getObjectIdentifier().getStringValue();

		    String odrw = isReference(rpart, null, data.category, 1);
		    if (odrw.length() > 0) {
			data.isValidate = false;
			sb.append("\n").append("-대표부품이 이미 다른 도면을 참조하고 있습니다.[" + data.repPartNumber + "|" + odrw + "]");
		    }

		}
	    }


	    //관련 부품 존재 여부 check
	    String[] refps = getStringTokens(data.refPartNums, ",");

	    if (refps.length > 0) {
		sb.append("\n").append("---------------------------------------------");

		String notFound = "";
		for (int i = 0; i < refps.length; i++) {
		    String[] refObjs = new String[1];

		    WTPart p = null;
		    boolean cp = true;

		    p = WTPartUtilities.getWTPart(refps[i].toUpperCase());
		    if (p == null) {
			cp = false;
		    }
		    else {
			String odrw = isReference(p, null, data.category, 0);
			if (odrw.length() > 0) {
			    data.isValidate = false;
			    sb.append("\n").append("-관련 부품이  이미 다른 도면을 참조하고 있습니다.[" + refps[i] + "|" + odrw + "]");
			}
		    }

		    if (!(cp)) {
			if (notFound.length() > 0) {
			    notFound += ",";
			}
			notFound += "[" + refps[i];
			if (p == null) {
			    notFound += "(등록안됨)";
			}
			notFound += "]";

			if (data.isValidate) {
			    data.isValidate = false;
			}
		    }

		    refObjs[0] = (p == null) ? "" : p.getPersistInfo().getObjectIdentifier().getStringValue();

		    if (data.refs == null) {
			data.refs = new Vector();
		    }
		    data.refs.add(refObjs);
		}

		if (notFound.length() > 0) {
		    sb.append("\n").append("-[관련부품번호]\t\t\t:" + notFound);
		}
		sb.append("\n").append("---------------------------------------------");
	    }

	} catch (Exception e) {
	    Kogger.error(EDMEcadMigDataLoader.class, e);
	    throw new WTException(e);
	} finally {
	    if (data != null) {
		data.msg = sb.toString();
	    }
	}
	return data;
    }


    public static Object save(Vector data) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Vector.class };
	    Object args[] = new Object[] { data };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("save", EDMEcadMigDataLoader.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(EDMEcadMigDataLoader.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMEcadMigDataLoader.class, e);
		throw new WTException(e);
	    }
	    return obj;
	}

	Transaction trx = new Transaction();

	Vector r = new Vector();
	try {
	    trx.start();

	    for (int i = 0; i < data.size(); i++) {
		EDMEcadMigData emd = (EDMEcadMigData) data.get(i);

		try {
		    emd.msg += "\n====== 저장 중 ... ============================================";

		    EPMDocument epmPcb = savePcb(emd);
		    EPMDocument epmSch = saveSch(emd);
		    EPMDocument epmDwg = saveDwg(emd);

		    Vector epms = new Vector();
		    epms.add(epmPcb);
		    epms.add(epmSch);
		    if (epmDwg != null) {
			epms.add(epmDwg);
		    }


		    HashMap hm = new HashMap();
		    hm.put("category", emd.category);
		    //대표 부품/모델
		    if ((emd.repPoid != null) && (emd.repPoid.length() > 0)) {
			hm.put("repPoid", emd.repPoid);
		    }

		    //관련 부품/모델
		    String torelPoid[] = null;
		    if ((emd.refs != null) && (emd.refs.size() > 0)) {
			torelPoid = new String[emd.refs.size()];

			for (int k = 0; k < emd.refs.size(); k++) {
			    String[] ref = (String[]) emd.refs.get(k);
			    torelPoid[k] = ref[0];
			}
		    }
		    if (torelPoid != null) {
			hm.put("torelPoid", torelPoid);
		    }

		    setAssociation(epms, hm);
		    //사업부
		    updateTypeCodeLink(epms, emd.bizCode);
		    //프로젝트
		    updateProjectLink(epms, ((E3PSProject) emd.project));
		    //상태 변경
		    setState(epms, emd.state);
		    //작성부서 변경
		    updateUserData(epms, emd.edmCreateDeptName);


		    emd.isLoadCompleted = true;
		    emd.msg += "\n====== 저장 완료  ============================================";
		} catch (Exception e) {
		    emd.isLoadCompleted = false;
		    emd.msg += "\n" + e.getLocalizedMessage();
		    throw new Exception(e);
		}

		r.add(emd);
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(EDMEcadMigDataLoader.class, e);
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return r;
    }

    public static EPMDocument savePcb(EDMEcadMigData emd) throws WTException {
	EPMDocument epm = null;
	try {
	    if (!emd.isExistPcb) {
		String appType = emd.getEPMApplicationType().toString();//EPMApplicationType	    	
		String authAppType = emd.getAuthoringAppTypeByEcadPcb().toString();//"PADS";	
		String docType = "OTHER";//EPMDocumentType

		epm = newEpm(emd.numberPcb, emd.namePcb, emd.cadFileNamePcb, authAppType, appType, docType, emd.getLifecycle(), emd.getLocation(), emd.cadVersionPcb, emd.getIBAData(), emd.primaryPcb,
		        null);

	    }
	    else {
		epm = update(emd.numberPcb, emd.namePcb, emd.cadFileNamePcb, emd.cadVersionPcb, emd.getIBAData(), emd.primaryPcb, null);
	    }

	    if ((emd.primaryGerber != null) && (emd.primaryGerber.exists())) {
		EDMServiceHelper.attach(epm, emd.primaryGerber, "gerber", ContentRoleType.SECONDARY);
	    }
	} catch (Exception e) {
	    Kogger.error(EDMEcadMigDataLoader.class, e);
	    throw new WTException(e);
	}
	return epm;
    }

    public static EPMDocument saveSch(EDMEcadMigData emd) throws WTException {
	EPMDocument epm = null;
	try {
	    if (!emd.isExistSch) {
		String appType = emd.getEPMApplicationType().toString();//EPMApplicationType
		String authAppType = emd.getAuthoringAppTypeByEcadSch().toString();
		String docType = "OTHER";//EPMDocumentType

		epm = newEpm(emd.numberSch, emd.nameSch, emd.cadFileNameSch, authAppType, appType, docType, emd.getLifecycle(), emd.getLocation(), emd.cadVersionSch, emd.getIBAData(), emd.primarySch,
		        null);
	    }
	    else {
		epm = update(emd.numberSch, emd.nameSch, emd.cadFileNameSch, emd.cadVersionSch, emd.getIBAData(), emd.primarySch, null);
	    }
	} catch (Exception e) {
	    Kogger.error(EDMEcadMigDataLoader.class, e);
	    throw new WTException(e);
	}
	return epm;
    }


    public static EPMDocument saveDwg(EDMEcadMigData emd) throws WTException {
	EPMDocument epm = null;
	try {
	    if ((emd.numberDwg.trim().length() == 0)) {
		return null;
	    }

	    if (!emd.isExistDwg) {
		String appType = emd.getEPMApplicationType().toString();//EPMApplicationType
		String authAppType = emd.getAuthoringAppTypeByEcadAcad().toString();//"ACAD";
		String docType = "OTHER";//EPMDocumentType

		epm = newEpm(emd.numberDwg, emd.nameDwg, emd.cadFileNameDwg, authAppType, appType, docType, emd.getLifecycle(), emd.getLocation(), emd.cadVersionDwg, emd.getIBAData(), emd.primaryDwg,
		        null);
	    }
	    else {
		epm = update(emd.numberDwg, emd.nameDwg, emd.cadFileNameDwg, emd.cadVersionDwg, emd.getIBAData(), emd.primaryDwg, null);
	    }
	} catch (Exception e) {
	    Kogger.error(EDMEcadMigDataLoader.class, e);
	    throw new WTException(e);
	}
	return epm;
    }

    public static EPMDocument newEpm(String number, String name, String cadName, String authAppType, String appType, String docType, String lifecycle, String location, String version, HashMap iba,
	    File primary, Vector secondary) throws WTException {

	EPMDocument epm = null;
	try {
	    if (EPMDocumentUtilities.doesEPMDocumentExist(number)) {
		throw new WTException("이미 등록된 도면번호입니다[" + number + "]");
	    }

	    if (EDMServiceHelper.isDuplicateCADName(cadName)) {
		throw new WTException("이미 등록된 CAD 파일 명입니다[" + cadName + "]");
	    }

	    EPMApplicationType applicationType = EPMApplicationType.toEPMApplicationType(appType);
	    EPMContextHelper.setApplication(applicationType);


	    epm = EPMDocument.newEPMDocument(number, name, EPMAuthoringAppType.toEPMAuthoringAppType(authAppType), EPMDocumentType.toEPMDocumentType(docType), cadName);

	    EPMDocumentMaster master = (EPMDocumentMaster) epm.getMaster();
	    master.setOwnerApplication(applicationType);
	    PersistenceServerHelper.manager.restore(master);


	    WTContainerRef wtContainerRef = EDMUtil.getWTContinerRefByEDMDefault();
	    Folder folder = FolderTaskLogic.getFolder(location, wtContainerRef);
	    FolderHelper.assignLocation((FolderEntry) epm, folder);

	    LifeCycleHelper.setLifeCycle(epm, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, wtContainerRef));

	    if ((version.length() > 0) && (Integer.parseInt(version) > 0)) {
		EDMServiceHelper.setVersion(epm, version);
	    }

	    epm = (EPMDocument) EDMAttributeUtil.setAttributeValues(epm, iba);

	    epm = (EPMDocument) PersistenceHelper.manager.store(epm);

	    EDMServiceHelper.attach(epm, primary, secondary);
	} catch (Exception e) {
	    Kogger.error(EDMEcadMigDataLoader.class, e);
	    throw new WTException(e);
	}
	return epm;
    }

    public static EPMDocument update(String number, String name, String cadName, String version, HashMap iba, File primary, Vector secondary) throws WTException {

	EPMDocument epm = null;
	try {
	    EDMProperties edmProperties = EDMProperties.getInstance();

	    epm = EPMDocumentUtilities.getEPMDocument(number);
	    epm = (EPMDocument) EDMServiceHelper.checkout(epm);

	    if (edmProperties.isAppTypeByPLM(epm.getOwnerApplication().toString())) {
		String curNumber = epm.getNumber();
		String curName = epm.getName();
		String curCADName = ((EPMDocumentMaster) epm.getMaster()).getCADName();

		if (!curNumber.equals(number) || !curName.equals(name) || !curCADName.equals(cadName)) {
		    if (!curNumber.equals(number) && EPMDocumentUtilities.doesEPMDocumentExist(number)) {
			throw new WTException("이미 등록된 도면번호입니다");
		    }

		    if (!curCADName.equals(cadName) && EDMServiceHelper.isDuplicateCADName(cadName)) {
			throw new WTException("이미 등록된 CAD 파일 명입니다");
		    }

		    epm = EDMServiceHelper.renumberEPMDocument(epm, name, number, cadName);
		}
	    }

	    if (version.length() > 0) {
		EDMServiceHelper.setVersion(epm, version);
	    }

	    //IBA
	    epm = (EPMDocument) EDMAttributeUtil.setAttributeValues(epm, iba);

	    epm = (EPMDocument) PersistenceHelper.manager.modify(epm);


	    //Kogger.debug(getClass(), "=========    File begin  ====================================");


	    //첨부파일
	    epm = (EPMDocument) ContentHelper.service.getContents(epm);
	    EDMServiceHelper.attach(epm, primary, secondary);


	    if (CheckInOutTaskLogic.isCheckedOut(epm)) {
		epm = (EPMDocument) CheckInOutTaskLogic.getWorkingCopy(epm);
		epm = (EPMDocument) CheckInOutTaskLogic.checkInObject(epm, "");
	    }
	} catch (Exception e) {
	    Kogger.error(EDMEcadMigDataLoader.class, e);
	    throw new WTException(e);
	}
	return epm;
    }

    public static void updateTypeCodeLink(Vector epms, NumberCode code) throws WTException {
	if ((epms == null) || (epms.size() == 0) || (code == null)) {
	    return;
	}

	for (int i = 0; i < epms.size(); i++) {
	    EDMServiceHelper.updateTypeCodeLink((EPMDocument) epms.get(i), code, true);
	}
    }

    public static void updateProjectLink(Vector epms, E3PSProject project) throws WTException {
	if ((epms == null) || (epms.size() == 0) || (project == null)) {
	    return;
	}

	for (int i = 0; i < epms.size(); i++) {
	    EDMServiceHelper.updateProjectLink((EPMDocument) epms.get(i), project.getMaster(), true);
	}
    }

    public static void setAssociation(Vector epms, HashMap map) throws WTException, WTPropertyVetoException {
	if ((epms == null) || (epms.size() == 0)) {
	    return;
	}

	for (int i = 0; i < epms.size(); i++) {
	    EDMServiceHelper.setAssociation((EPMDocument) epms.get(i), map);
	}
    }

    public static void setState(Vector epms, String state) throws WTException {
	if ((epms == null) || (epms.size() == 0) || (state == null) || (state.trim().length() == 0)) {
	    return;
	}

	for (int i = 0; i < epms.size(); i++) {
	    LifeCycleHelper.service.setLifeCycleState((EPMDocument) epms.get(i), State.toState(state.trim()), false);
	}
    }

    public static void updateUserData(Vector epms, String deptName) throws WTException, WTPropertyVetoException {
	if ((epms == null) || (epms.size() == 0) || (deptName == null) || (deptName.trim().length() == 0)) {
	    return;
	}

	for (int i = 0; i < epms.size(); i++) {
	    EDMUserData ud = EDMHelper.getEDMUserData(epms.get(i));
	    if (ud != null) {
		ud.setInitDeptName(deptName);
		ud.setCreatorDeptName(deptName);
		ud.setModifierDeptName(deptName);
		PersistenceHelper.manager.save(ud);
	    }
	}
    }

    /**
     * 참조가 가능한 경우 공백("") 리턴 참조가 있는 경우, 해당 도면 번호 리턴
     * 
     * @param part
     * @param dnum
     * @param category
     * @param required
     * @return
     * @throws WTException
     */
    public static String isReference(WTPart part, String dnum, String category, int required) throws WTException {
	EDMProperties edmProperties = EDMProperties.getInstance();
	String referenceType = edmProperties.getReferenceType(category);

	String drw = "";

	if (edmProperties.isCADCatsByEcad(category)) {
	    QueryResult qr = EDMHelper.getEPMLink(part, null, referenceType, -1);
	    if (qr.size() == 0) {
		return "";
	    }
	    String ecad = "";
	    while (qr.hasMoreElements()) {
		Object[] o = (Object[]) qr.nextElement();
		EPMLink el = (EPMLink) o[0];
		EPMDocumentMaster edm = el.getEpmMaster();
		ecad += ((ecad.length() > 0) ? "," : "") + edm.getNumber();
	    }
	    return ecad;

	}
	else {
	    //부품이 여러 도면을 참조하는 경우.return true
	    if (!edmProperties.isOnlyRefedByCat(category)) {
		return "";
	    }

	    QueryResult qr = EDMHelper.getEPMLink(part, null, referenceType, -1);
	    if (qr.size() == 0) {
		return "";
	    }

	    while (qr.hasMoreElements()) {
		Object[] o = (Object[]) qr.nextElement();
		EPMLink el = (EPMLink) o[0];
		EPMDocumentMaster edm = el.getEpmMaster();
		drw = edm.getNumber();
		if (drw.equals(dnum.toUpperCase())) {
		    return "";
		}
	    }
	}
	return drw;
    }



    public static String[] getStringTokens(String str, String delim) {
	String[] tokens = null;
	StringTokenizer st = new StringTokenizer(str, delim);

	int idx = 0;

	tokens = new String[st.countTokens()];
	while (st.hasMoreTokens()) {
	    tokens[idx++] = st.nextToken().trim();
	}

	return tokens;
    }

    public static Object getProject(String pjtno) throws WTException {

	E3PSProject project = null;
	try {
	    project = ProjectHelper.getProject(pjtno);
	} catch (Exception e) {
	    throw new WTException(e);
	}
	return project;
    }

    public static HashMap getCategoryType() {
	EDMProperties edmProperties = EDMProperties.getInstance();

	HashMap m = new HashMap();

	CADManageType[] cmts = CADManageType.getCADManageTypeSet();
	for (int i = 0; i < cmts.length; i++) {
	    CADManageType cmt = cmts[i];
	    CADCategory[] cats = edmProperties.getCADCategorySet(cmt.toString());
	    if (cats != null) {
		for (int k = 0; k < cats.length; k++) {
		    CADCategory cat = cats[k];
		    m.put(cat.toString(), cmt.toString());
		    //m.put(cat.getDisplay(Locale.KOREAN), cmt.toString());
		}
	    }
	}
	return m;
    }

    public static HashMap getNumberCodeMap(String type) {
	HashMap m = new HashMap();
	try {
	    QuerySpec qs = new QuerySpec();
	    int idx = qs.appendClassList(NumberCode.class, true);

	    qs.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", type), new int[] { idx });

	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);
	    while (qr.hasMoreElements()) {
		Object[] o = (Object[]) qr.nextElement();
		NumberCode code = (NumberCode) o[0];
		m.put(code.getName(), code);
	    }
	} catch (Exception e) {
	    Kogger.error(EDMEcadMigDataLoader.class, e);
	}
	return m;
    }

    public static NumberCode getBizCode(String name, String type) {
	HashMap m = getNumberCodeMap(type);
	if (m.containsKey(name)) {
	    return (NumberCode) m.get(name);
	}
	return null;
    }

    private static void printUsage() {
	StringBuffer sb = new StringBuffer();
	sb.append("\n").append("\n\tSample Usages.").append("\n\twindchill e3ps.load.migration.edm.EDMEcadMigDataLoader\tid\tpw\t파일명").append("\n");
	Kogger.debug(EDMEcadMigDataLoader.class, sb.toString());
    }


    public static void main(String args[]) throws Exception {
	if ((args.length != 3) || args[0].equals("-h") || args[0].equals("-help")) {
	    printUsage();
	    return;
	}


	wt.method.RemoteMethodServer remotemethod = wt.method.RemoteMethodServer.getDefault();
	//remotemethod.setUserName("wcadmin");
	//remotemethod.setPassword("wcadmin");
	remotemethod.setUserName(args[0]);
	remotemethod.setPassword(args[1]);


	String type = "";
	String fileName = args[2];

	long startTimeMillis = System.currentTimeMillis();
	Kogger.debug(EDMEcadMigDataLoader.class, "Load Start...");

	try {
	    loadFile(fileName, type);
	} catch (Exception e) {
	    Kogger.error(EDMEcadMigDataLoader.class, e);
	}

	Kogger.debug(EDMEcadMigDataLoader.class, "Load End...");

	long endTimeMillis = System.currentTimeMillis();
	Kogger.debug(EDMEcadMigDataLoader.class, "Load Time : " + (endTimeMillis - startTimeMillis));
	System.exit(0);
    }

}


class EDMEcadMigData implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID     = 1L;


    //Excel Data ............................................

    public int                rownum;

    public String             bizName;
    public String             edmCreateDeptName;
    public String             cadDevStageName;
    public String             cadCategoryName;
    public String             cadAppTypeName;
    public String             pjtno;

    //PCB
    public String             numberPcb;
    public String             namePcb;
    public String             cadVersionPcb;
    public String             cadFileDirPcb;
    public String             cadFileNamePcb;

    public String             cadFileDirGerber;
    public String             cadFileNameGerber;

    //Schematic
    public String             numberSch;
    public String             nameSch;
    public String             cadVersionSch;
    public String             cadFileDirSch;
    public String             cadFileNameSch;

    //AutoCAD
    public String             numberDwg;
    public String             nameDwg;
    public String             cadVersionDwg;
    public String             cadFileDirDwg;
    public String             cadFileNameDwg;

    public String             repPartNumber;
    public String             refPartNums;


    //Save Data ............................................

    public String             state                = "APPROVED";

    public NumberCode         bizCode;                           //사업부 코드	
    public Object             project;                           //프로젝트

    //IBA
    public String             manageType;
    public String             devStage;
    public String             category;
    public String             cadAppType;

    public String             repPoid              = null;
    public Vector             refs                 = null;

    public File               dirPcb;
    public File               dirGerber;
    public File               dirSch;
    public File               dirDwg;

    public File               primaryPcb;
    public File               primaryGerber;
    public File               primarySch;
    public File               primaryDwg;

    public String             appType;


    //Result ............................................
    public String             msg;

    public boolean            isExistPcb           = false;
    public boolean            isExistSch           = false;
    public boolean            isExistDwg           = false;

    public boolean            isDocNumberSizeValid = true;
    public boolean            iswgmdata            = false;      //WGM으로 등록된 model, drawing 정보 update
    public boolean            isValidate           = true;
    public boolean            isRepPartExist       = true;

    public boolean            isLoadCompleted      = false;

    public EDMEcadMigData(int rownum) {
	this.rownum = rownum;
    }

    public EDMEcadMigData(Cell[] cells, int rownum) {
	this.rownum = rownum;
	setCellData(cells);
    }

    public void setCellData(Cell[] cells) {
	int colIdex = -1;

	bizName = JExcelUtil.getContent(cells, ++colIdex);
	edmCreateDeptName = JExcelUtil.getContent(cells, ++colIdex);
	cadDevStageName = JExcelUtil.getContent(cells, ++colIdex);
	cadCategoryName = JExcelUtil.getContent(cells, ++colIdex);
	cadAppTypeName = JExcelUtil.getContent(cells, ++colIdex);
	pjtno = JExcelUtil.getContent(cells, ++colIdex);

	//PCB
	numberPcb = JExcelUtil.getContent(cells, ++colIdex);
	namePcb = JExcelUtil.getContent(cells, ++colIdex);
	cadVersionPcb = JExcelUtil.getContent(cells, ++colIdex);
	cadFileDirPcb = backSlashPrefixToBlank(JExcelUtil.getContent(cells, ++colIdex));
	cadFileNamePcb = JExcelUtil.getContent(cells, ++colIdex);

	cadFileDirGerber = backSlashPrefixToBlank(JExcelUtil.getContent(cells, ++colIdex));
	cadFileNameGerber = JExcelUtil.getContent(cells, ++colIdex);

	//Schematic
	numberSch = JExcelUtil.getContent(cells, ++colIdex);
	nameSch = JExcelUtil.getContent(cells, ++colIdex);
	cadVersionSch = JExcelUtil.getContent(cells, ++colIdex);
	cadFileDirSch = backSlashPrefixToBlank(JExcelUtil.getContent(cells, ++colIdex));
	cadFileNameSch = JExcelUtil.getContent(cells, ++colIdex);

	//AutoCAD
	numberDwg = JExcelUtil.getContent(cells, ++colIdex);
	nameDwg = JExcelUtil.getContent(cells, ++colIdex);
	cadVersionDwg = JExcelUtil.getContent(cells, ++colIdex);
	cadFileDirDwg = backSlashPrefixToBlank(JExcelUtil.getContent(cells, ++colIdex));
	cadFileNameDwg = JExcelUtil.getContent(cells, ++colIdex);

	repPartNumber = JExcelUtil.getContent(cells, ++colIdex);
	refPartNums = JExcelUtil.getContent(cells, ++colIdex);
    }

    public HashMap getIBAData() {
	HashMap im = new HashMap();
	im.put(EDMHelper.IBA_CAD_MANAGE_TYPE, manageType);
	im.put(EDMHelper.IBA_DEV_STAGE, devStage);
	im.put(EDMHelper.IBA_CAD_CATEGORY, category);
	im.put(EDMHelper.IBA_CAD_APP_TYPE, cadAppType);
	im.put(EDMHelper.IBA_DUMMY_FILE, EDMHelper.IBA_DUMMY_FILE_VALUE_NO);
	return im;
    }

    public String getLocation() {
	return EDMProperties.getInstance().getFullPath(bizName, manageType, category, iswgmdata);
    }

    public String getLifecycle() {
	return EDMProperties.getInstance().getEPMDefaultLC();
    }

    public EPMAuthoringAppType getEPMAuthoringAppType() {
	return EPMAuthoringAppType.toEPMAuthoringAppType(cadAppType);
    }

    public EPMAuthoringAppType getAuthoringAppTypeByEcadAcad() {
	//"ACAD"
	return EPMAuthoringAppType.toEPMAuthoringAppType(EDMProperties.getInstance().getAuthoringAppTypeByEcadAcad());
    }

    public EPMAuthoringAppType getAuthoringAppTypeByEcadSch() {
	//"PADS_SCH"
	return EPMAuthoringAppType.toEPMAuthoringAppType(EDMProperties.getInstance().getAuthoringAppTypeByEcadSch());
    }

    public EPMAuthoringAppType getAuthoringAppTypeByEcadPcb() {
	//"PADS"
	return EPMAuthoringAppType.toEPMAuthoringAppType(EDMProperties.getInstance().getAuthoringAppTypeByEcadPcb());
    }

    public EPMApplicationType getEPMApplicationType() {
	if ((appType == null) || (appType.trim().length() == 0)) {
	    appType = "PLMSYSTEM";
	}
	return EPMApplicationType.toEPMApplicationType(appType);
    }

    public EPMDocumentType getEPMDocumentType() {
	String epmDocumentType = "";

	if (epmDocumentType.trim().length() == 0) {
	    epmDocumentType = "OTHER";
	}
	return EPMDocumentType.toEPMDocumentType(epmDocumentType);
    }

    public String backSlashPrefixToBlank(String str) {
	if ((str == null) || (str.trim().length() == 0)) {
	    return "";
	}

	if (str.startsWith("\\")) str = str.substring(1);
	if (str.endsWith("\\")) str = str.substring(0, str.lastIndexOf("\\"));

	return str;
    }
}
