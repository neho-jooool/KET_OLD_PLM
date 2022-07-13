package e3ps.load.migration.edm;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import wt.clients.folder.FolderTaskLogic;
import wt.clients.vc.CheckInOutTaskLogic;
import wt.content.ContentHelper;
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
import wt.folder.SubFolder;
import wt.iba.value.service.IBAValueHelper;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.State;
import wt.part.PartType;
import wt.part.QuantityUnit;
import wt.part.Source;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pds.StatementSpec;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.util.WTPropertyVetoException;
import wt.vc.views.ViewHelper;

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
import e3ps.edm.ModelReferenceLink;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.beans.EDMServiceHelper;
import e3ps.edm.util.EDMAttributeUtil;
import e3ps.edm.util.EDMEnumeratedTypeUtil;
import e3ps.edm.util.EDMProperties;
import e3ps.edm.util.EDMUtil;
import e3ps.load.migration.edm.log.LogToFile;
import e3ps.project.E3PSProject;
import e3ps.project.beans.ProjectHelper;
import ext.ket.part.code.NumberCodeUtil;
import ext.ket.shared.log.Kogger;

public class EDMMigDataLoader_pro implements wt.method.RemoteAccess, java.io.Serializable {

    static final boolean   SERVER         = wt.method.RemoteMethodServer.ServerFlag;

    private static String  FILESERVER     = "\\\\192.168.17.13";
    private static String  EXCELFILE      = "";

    private static String  SEPARATOR      = File.separator;

    static {
	try {
	    String wt_home = WTProperties.getLocalProperties().getProperty("wt.home");
	    EXCELFILE = wt_home + SEPARATOR + "loadFiles\\ket\\migration\\edm";
	    FILESERVER = wt_home + SEPARATOR + "loadFiles\\ket\\migration\\edm";
	} catch (Exception e) {
	    Kogger.error(EDMMigDataLoader_pro.class, e);
	}
    }


    private static HashMap bizMap;
    private static HashMap catMgtMap;

    private static boolean IS_CREATE_PART = false;
    private static boolean IS_CHECK_DRM   = false;

    private static String  logFile        = "mylogfile.log";

    static {
	Config conf = ConfigImpl.getInstance();
	IS_CHECK_DRM = conf.getBoolean("DRM");
    }

    public static boolean loadFile(String fileName, String type) throws WTException {
	try {
	    String filePath = EXCELFILE + SEPARATOR + fileName;
	    
	    Kogger.debug(EDMMigDataLoader_pro.class, "###########" + filePath);
	    Kogger.debug(EDMMigDataLoader_pro.class, "###########" + filePath);
	    Kogger.debug(EDMMigDataLoader_pro.class, "###########" + filePath);
	    Kogger.debug(EDMMigDataLoader_pro.class, "###########" + filePath);
	    File dataFile = new File(filePath);
	    if (!dataFile.exists()) {
		Kogger.debug(EDMMigDataLoader_pro.class, "File not found!!!");
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
		Kogger.debug(EDMMigDataLoader_pro.class, ">" + (String) itr.next());
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
			Kogger.debug(EDMMigDataLoader_pro.class, "Column name : " + hdr.getContents());
		    }

		    for (int k = 1; k < rows; k++) {
			//Kogger.debug(getClass(), "current row : " + i);
			//Cell cell[] = sheet.getRow(k);
			EDMMigData_pro rdata = (EDMMigData_pro) getRowData(sheet.getRow(k), k + 1, sheet.getName());

			if (rdata != null) {
			    if (!rdata.isValidate) {
				log(rdata.msg);
			    }
			    else {
				Vector v = new Vector();
				v.add(rdata);

				Vector v0 = (Vector) save(v);
				for (int m = 0; m < v0.size(); m++) {
				    EDMMigData_pro rdata0 = (EDMMigData_pro) v0.get(m);
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
	    Kogger.error(EDMMigDataLoader_pro.class, e);
	    throw new WTException("file name = " + fileName);
	}
	return true;
    }

    public static void log(String msg) {
	try {
	    LogToFile logger = new LogToFile(logFile, true);
	    logger.log(msg);
	} catch (IOException e) {
	    Kogger.error(EDMMigDataLoader_pro.class, e);
	}
    }

    public static Object getRowData(Cell[] cells, int rownum, String sheetName) throws WTException {
	EDMMigData_pro data = null;

	StringBuffer sb = new StringBuffer();
	try {
	    if (JExcelUtil.getContent(cells, 0).length() == 0) {
		return null;
	    }
	    sb.append("\n********** >>> [ row:" + rownum + " ]-[ sheet:" + sheetName + " ] <<< *********");

	    data = new EDMMigData_pro(rownum);
	    data.setCellData(cells);

	    //사업부
	    if (bizMap.containsKey(data.bizName)) {
		data.bizCode = (NumberCode) bizMap.get(data.bizName);
	    }
	    else {
		data.isValidate = false;
		sb.append("\n").append("-사업부\t\t\t\t:정의되지 않은 사업부입니다.[" + data.bizName + "]");
	    }

	    //작성부서

	    //도면구분
	    if (data.cadDevStageName.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("-도면구분\t\t\t\t:입력값이 없습니다.");
	    }
	    else {
		data.devStage = EDMEnumeratedTypeUtil.getDevStage(data.cadDevStageName, Locale.KOREAN);
	    }

	    //도면유형
	    if (data.cadCategoryName.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("-도면유형\t\t\t\t:입력값이 없습니다.");
	    }
	    else {
		data.category = EDMEnumeratedTypeUtil.getCADCategory(data.cadCategoryName, Locale.KOREAN);
	    }

	    //CAD종류
	    if (data.cadAppTypeName.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("-CAD종류\t\t\t\t:입력값이 없습니다.");
	    }
	    else {
		data.cadAppType = data.cadAppTypeName;
	    }

	    //프로젝트번호
	    if (data.pjtno.length() > 0) {
		data.project = getProject(data.pjtno);
		if ((data.project == null)) {
		    data.isValidate = false;
		    sb.append("\n").append("-프로젝트번호\t\t\t\t:프로젝트가 존재하지 않습니다.[" + data.pjtno + "]");
		}
	    }

	    //제품/금형 도면
	    data.manageType = (String) catMgtMap.get(data.category);

	    //금형도면/UG 인 경우 모델 정보 update.
	    if ("MOLD_DRAWING".equals(data.manageType)) {
		if ("UG".equals(data.cadAppType)) {
		    data.iswgmdata = true;
		}
	    }

	    EPMDocument epm = null;
	    if (data.number.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("-도면번호\t\t\t\t:입력값이 없습니다.");
	    }
	    else {	
		if("CU".equals(data.number.substring(0, 2))){
		    
		    	// 2011.05.02 고객제출도면 체크 (도면번호, 부품번호, 파일명이 다를때 Migration 실패  by TaeHun
			// 2012.4.30 예외처리 삭제요청 윤도혁 (수정자 : 황정태)
		    
//			String subNumer = data.number.substring(5, data.number.length());	// 도면번호
//			String subRepPartNumer = data.cadFileName.substring(5, data.number.length());	// 파일명
//			String subCadFileNumer = data.repPartNumber.substring(1, data.number.substring(5, data.number.length()).length()+1);	// 부품번호
//
//			Kogger.debug(EDMMigDataLoader_pro.class, "CU ::: " + data.number.substring(0, 2));
//			Kogger.debug(EDMMigDataLoader_pro.class, "도면번호 ::: "+subNumer );
//			Kogger.debug(EDMMigDataLoader_pro.class, "파일명 ::: "+subRepPartNumer );
//			Kogger.debug(EDMMigDataLoader_pro.class, "부품번호 ::: "+subCadFileNumer );
//
//			if(!subNumer.equals(subRepPartNumer) || !subNumer.equals(subCadFileNumer) || !subRepPartNumer.equals(subCadFileNumer) ){    // 대표부품과 파일명(name ???) 비교
//				data.isValidate = false;
//				data.isRepPartExist = false;
//				sb.append("\n").append("-도면번호\t\t\t\t:CU도면의 도면번호, 파일명, 대표부품번호가 다릅니다.");	 //비교 후 도면, 부품번호, 파일명이  다르면 실패
//			}else{
//				epm = EPMDocumentUtilities.getEPMDocument(data.number.toUpperCase());
//			}
			
			
			String cadNumber = StringUtils.removeStart(data.number.toUpperCase(), "CU_");
			Kogger.debug(EDMMigDataLoader_pro.class, "CU ::: " + data.number.substring(0, 2));
			Kogger.debug(EDMMigDataLoader_pro.class, "도면번호 ::: "+cadNumber );

			cadNumber = StringUtils.removeStart(cadNumber, "MG");
			cadNumber = StringUtils.removeStart(cadNumber, "ST");
			cadNumber = StringUtils.removeStart(cadNumber, "EM");
			cadNumber = StringUtils.removeStart(cadNumber, "JB");
			cadNumber = StringUtils.removeStart(cadNumber, "WH");
			cadNumber = StringUtils.removeEnd(cadNumber, "_3D");
			String partNo = cadNumber;
			
			
			if (!cadNumber.startsWith("R") & !cadNumber.startsWith("K") && !cadNumber.startsWith("68")) {// K로 시작하는 부품 또는 상품이 아니면
			    partNo = "H" + cadNumber;
			}
			
			Kogger.debug(EDMMigDataLoader_pro.class, "부품번호 ::: "+partNo );
			
//			if(!data.repPartNumber.equals(partNo)){
//			    data.isValidate = false;
//			    data.isRepPartExist = false;
//			    sb.append("\n").append("-도면번호\t\t\t\t:CU도면의 도면번호, 대표부품번호가 다릅니다.");	 //비교 후 도면, 부품번호가  다르면 실패
//			}else{
			    epm = EPMDocumentUtilities.getEPMDocument(data.number.toUpperCase());
//			}
//			
			
		}else{
			epm = EPMDocumentUtilities.getEPMDocument(data.number.toUpperCase());
		}
		//epm = EPMDocumentUtilities.getEPMDocument(data.number.toUpperCase());
	    }

	    // 체크 로직..
	    // 도면 번호, 파일명6자리, 대표부품 번호가 같을시 붙는다.. 아니면 에러 발생..
	    // 3가지번호의 조건이 많은 관계로 주석 처리..(2011.07.12)
	    //			String checkSub = "";
	    //			int subIndex = 0;
	    //			if(  "ST".equals(data.number.substring(0, 2) ) || "AP_ST".equals(data.number.substring(0, 5) ) ){
	    //				subIndex = data.number.indexOf("T")+1;
	    //			}else if(  "JB".equals(data.number.substring(0, 2) ) || "AP_JB".equals(data.number.substring(0, 5) ) ){
	    //				subIndex = data.number.indexOf("B")+1;
	    //			}else if(  "MG".equals(data.number.substring(0, 2) ) || "AP_MG".equals(data.number.substring(0, 5) ) ){
	    //				subIndex = data.number.indexOf("G")+1;
	    //			}else if(  "R".equals(data.number.substring(0, 1) ) || "AP_R".equals(data.number.substring(0, 4) ) ){
	    //				subIndex = data.number.indexOf("R")+1;
	    //			}else{}
	    //			String checkNumber = "";
	    //			String checkFile =  "";
	    //			String checkPart =  "";
	    //			if(!"CU".equals(data.number.substring(0, 2))){
	    //				checkNumber = data.number.substring( subIndex , data.number.length());
	    //				checkFile = data.cadFileName.substring(subIndex , data.number.length());
	    //				if( "JB50".equals(data.number.substring(0, 4) ) ){
	    //					checkPart = data.repPartNumber.substring(data.repPartNumber.indexOf("R")+1 , data.number.substring(subIndex, data.number.length()).length()+1);
	    //				}else{
	    //					checkPart = data.repPartNumber.substring(data.repPartNumber.indexOf("H")+1 , data.number.substring(subIndex, data.number.length()).length()+1);
	    //				}
	    //
	    //				if(!checkNumber.equals(checkFile) || !checkNumber.equals(checkPart) || !checkFile.equals(checkPart)){
	    //					data.isValidate = false;
	    //					data.isRepPartExist = false;
	    //					sb.append("\n").append("-도면번호\t\t\t\t:도면의 도면번호, 파일명, 대표부품번호가 다릅니다.");	 //비교 후 도면, 부품번호, 파일명이  다르면 실패
	    //					Kogger.debug(getClass(), "도면번호 == " + checkNumber);
	    //					Kogger.debug(getClass(), "파일명 8자리 == " + checkFile);
	    //					Kogger.debug(getClass(), "대표부품 번호 == " + checkPart);
	    //					Kogger.debug(getClass(), "엑셀" + rownum + "행의 도변번호, 파일명8자리, 대표부품번호를 확인 하고 다시 작업하세요.");
	    //					System.exit(0);
	    //
	    //				}
	    //			}else{}



	    if (data.name.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("-도면명\t\t\t\t:입력값이 없습니다.");
	    }

	    data.isExist = (epm == null) ? false : true;//EPMDocumentUtilities.doesEPMDocumentExist(data.number.toUpperCase());

	    data.isDocNumberSizeValid = EPMDocumentUtilities.isDocNumberSizeValid(data.number.toUpperCase());



	    //금형도면/UG 인 경우 모델 정보 update.
	    if (data.iswgmdata) {
		if (!(data.isExist)) { // if(epm == null) {
		    data.isValidate = false;
		    sb.append("\n").append("-모델 데이터\t\t\t\t:PLM에 등록된 모델이 없습니다.[" + data.number + "]");
		}
		else {
		    data.appType = epm.getOwnerApplication().toString();
		}
	    }
	    else {
		if (data.isExist) {
		    data.isValidate = false;
		    sb.append("\n").append("-도면번호\t\t\t\t:이미 등록된 도면입니다.[" + data.number + "]");
		}
	    }


	    if (!(data.iswgmdata) && (data.cadFileDir.length() == 0)) {
		data.isValidate = false;
		sb.append("\n").append("-파일위치(절대경로)\t\t\t:입력값이 없습니다.");
	    }

	    if ((data.cadFileDir.length() > 0)) {
		data.dir = new File(FILESERVER + SEPARATOR + data.cadFileDir);
		if (!data.dir.exists()) {
		    data.isValidate = false;
		    sb.append("\n").append("-파일위치(절대경로)\t\t\t:폴더가 없습니다.[" + data.cadFileDir + "]");
		}
	    }

	    String fileDir = FILESERVER + SEPARATOR + data.cadFileDir + SEPARATOR;

	    //주 도면 파일 존재 여부 check
	    if (!(data.iswgmdata) && (data.cadFileName.length() == 0)) {
		data.isValidate = false;
		sb.append("\n").append("-파일명\t\t\t\t\t:입력값이 없습니다.");
	    }

	    if (!(data.iswgmdata) && (data.cadFileName.length() > 0)) {
		data.primary = new File(fileDir + data.cadFileName);
		if (!data.primary.exists()) {
		    data.isValidate = false;
		    sb.append("\n").append("-파일명\t\t\t\t\t:해당 파일이 없습니다.[" + data.cadFileName + "]");
		}
	    }

	    //부 도면 파일's 존재 여부 check
	    String[] sfs = getStringTokens(data.secondaryFiles, ",");
	    if (sfs.length > 0) {
		String fileNotFound = "";
		for (int i = 0; i < sfs.length; i++) {
		    File file = new File(fileDir + sfs[i]);
		    if (!file.exists()) {
			if (fileNotFound.length() > 0) {
			    fileNotFound += ",";
			}
			fileNotFound += sfs[i];
			continue;
		    }

		    if (data.secondary == null) {
			data.secondary = new Vector();
		    }
		    data.secondary.add(file);
		}
		if (fileNotFound.length() > 0) {
		    data.isValidate = false;
		    sb.append("\n").append("-부도면첨부\t\t\t\t:해당 파일이 없습니다.[" + fileNotFound + "]");
		}
	    }

	    //CATIA모델(승인도) 존재 여부 check
	    if ("APPROVAL_DRAWING".equals(data.category)) {
		if (data.catiaFile.trim().length() > 0) {
		    data.cdir = new File(FILESERVER + SEPARATOR + data.catiaFileDir);
		    if (!data.cdir.exists()) {
			data.isValidate = false;
			sb.append("\n").append("-첨부파일경로\t\t\t\t:해당 폴더가 없습니다.[" + data.catiaFileDir + "]");
		    }
		    else {
			if (data.catiaFile.length() > 0) {
			    File file = new File(FILESERVER + SEPARATOR + data.catiaFileDir + SEPARATOR + data.catiaFile);
			    if (file.exists()) {
				if (data.secondary == null) {
				    data.secondary = new Vector();
				}
				data.secondary.add(file);
			    }
			    else {
				data.isValidate = false;
				sb.append("\n").append("-CATIA모델(승인도첨부)\t\t:해당 파일이 없습니다.[" + data.catiaFile + "]");
			    }
			}
		    }
		}

	    }

	    WTPart rpart = null;
	    //대표 부품/모델 존재 여부 check
	    if (data.repPartNumber.trim().length() > 0) {
		rpart = WTPartUtilities.getWTPart(data.repPartNumber.toUpperCase());
		if (rpart == null) {
		    data.isValidate = false;
		    data.isRepPartExist = false;
		    sb.append("\n").append("-대표부품번호\t\t\t\t:등록된 부품이 없습니다.[" + data.repPartNumber + "]");
		}
		else {
		    data.repPoid = rpart.getPersistInfo().getObjectIdentifier().getStringValue();

		    String odrw = isReference(rpart, data.number, data.category, 1);
		    if (odrw.length() > 0) {
			data.isValidate = false;
			sb.append("\n").append("-대표부품이 이미 다른 도면을 참조하고 있습니다.[" + data.repPartNumber + "|" + odrw + "]");
		    }

		}
	    }

	    if (data.repModelNumber.trim().length() > 0) {
		EPMDocument model = EPMDocumentUtilities.getEPMDocument(data.repModelNumber.trim().toUpperCase());

		if (model == null) {
		    data.isValidate = false;
		    sb.append("\n").append("-대표부품3D모델번호\t\t\t:등록된 모델이 없습니다.[" + data.repModelNumber + "]");
		}
		else {
		    data.repModelOid = model.getPersistInfo().getObjectIdentifier().getStringValue();

		    if (!checkDrwModelRef(model, data.number, data.category, -1) || !checkPrtModelRef(rpart, model, data.category, -1)) {
			data.isValidate = false;
			sb.append("\n").append("-모델이 이미 다른 부품이나 도면을 참조하고 있습니다.[" + data.repModelNumber + "]");
		    }
		}
		/*
		if(m != null) {
			EPMDocument m2d = EPMDocumentUtilities.getEPMDocument(data.repModelNumber.trim()+"_2D");
			if(m2d != null) {
				data.repModel2DOid = m2d.getPersistInfo().getObjectIdentifier().getStringValue();
			}
		}
		*/
	    }


	    //관련 부품/모델 존재 여부 check
	    // 부품과 모델 있는 경우 부품-모델 동일 순서로 체크.
	    String[] refps = getStringTokens(data.refPartNums, ",");
	    String[] refms = getStringTokens(data.refModelNums, ",");

	    if (refps.length > 0) {

		String notFound = "";
		String alreadyExist = "";

		sb.append("\n").append("---------------------------------------------");

		for (int i = 0; i < refps.length; i++) {
		    String[] refObjs = new String[3];

		    if (alreadyExist.length() > 0) {
			alreadyExist += ",";
		    }

		    String refModelName = (refms.length > 0) ? refms[i] : "";
		    WTPart p = null;
		    EPMDocument e = null;
		    EPMDocument e2d = null;
		    boolean cp = true;
		    boolean ce = true;

		    p = WTPartUtilities.getWTPart(refps[i].toUpperCase());
		    if (p == null) {
			cp = false;
		    }
		    else {
			String odrw = isReference(p, data.number, data.category, 0);
			if (odrw.length() > 0) {
			    data.isValidate = false;
			    sb.append("\n").append("-관련 부품이  이미 다른 도면을 참조하고 있습니다.[" + refps[i] + "|" + odrw + "]");
			}
		    }

		    if ((refms.length > 0) && !"NA".equals(refms[i])) {
			refModelName = refms[i];
			e = EPMDocumentUtilities.getEPMDocument(refms[i].toUpperCase());
			if (e == null) {
			    ce = false;
			}
			else {
			    if (!checkDrwModelRef(e, data.number, data.category, -1) || !checkPrtModelRef(p, e, data.category, -1)) {
				data.isValidate = false;
				sb.append("\n").append("-관련 모델이 이미 다른 부품이나 도면을 참조하고 있습니다.[" + refms[i] + "]");
			    }
			}

			if (e != null) {
			    e2d = EPMDocumentUtilities.getEPMDocument(refps[i].toUpperCase() + "_2D");
			}
		    }

		    if (!(cp) || !(ce)) {

			if (notFound.length() > 0) {
			    notFound += ",";
			}
			notFound += "[" + refps[i];
			if (p == null) {
			    notFound += "(등록안됨)";
			}
			notFound += "/";
			notFound += refModelName;
			if ((e == null) && (refModelName.length() > 0) && !("NA".equals(refModelName))) {
			    notFound += "(등록안됨)";
			}
			notFound += "]";

			if (data.isValidate) {
			    data.isValidate = false;
			}
		    }

		    refObjs[0] = (p == null) ? "" : p.getPersistInfo().getObjectIdentifier().getStringValue();
		    refObjs[1] = (e == null) ? "" : e.getPersistInfo().getObjectIdentifier().getStringValue();
		    refObjs[2] = (e2d == null) ? "" : e2d.getPersistInfo().getObjectIdentifier().getStringValue();

		    if (data.refs == null) {
			data.refs = new Vector();
		    }
		    data.refs.add(refObjs);
		}

		if (notFound.length() > 0) {
		    sb.append("\n").append("-[관련부품번호/관련부품3D모델번호]\t:" + notFound);
		}
		sb.append("\n").append("---------------------------------------------");
	    }

	    if (!(data.iswgmdata) && (data.isExist)) {
		data.isValidate = false;
		//sb.append("\n").append("-이미 등록된 도면입니다.["+data.number+"]");
	    }

	} catch (Exception e) {
	    Kogger.error(EDMMigDataLoader_pro.class, e);
	    throw new WTException(e);
	} finally {
	    if (data != null) {
		data.msg = sb.toString();
	    }
	}
	return data;
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
	    QueryResult qr = EDMHelper.getEPMLink(part, null, referenceType, required);
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

	    QueryResult qr = EDMHelper.getEPMLink(part, null, referenceType, required);
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


    public static boolean checkDrwModelRef(EPMDocument model, String dnum, String category, int required) throws WTException {
	//EDMProperties edmProperties = EDMProperties.getInstance();
	//String referenceType = edmProperties.getReferenceType(category);

	//drawing과 연관이 있는지 여부 체크
	QueryResult qr = EDMHelper.getRefedModel(null, model, required);
	if (qr.size() > 0) {
	    while (qr.hasMoreElements()) {
		Object[] o = (Object[]) qr.nextElement();
		ModelReferenceLink mrl = (ModelReferenceLink) o[0];
		EPMDocumentMaster edm = mrl.getDrawingMaster();
		if (dnum.toUpperCase().equals(edm.getNumber())) {
		    return true;
		}
	    }
	    return false;
	}

	return true;
    }


    public static boolean checkPrtModelRef(WTPart part, EPMDocument model, String category, int required) throws WTException {
	EDMProperties edmProperties = EDMProperties.getInstance();
	//String referenceType = edmProperties.getReferenceType(category);

	String pnum = "";
	if (part != null) {
	    pnum = part.getNumber();
	}
	//부품과 연관이 있는지 여부 체크
	QueryResult qr = EDMHelper.getEPMLink(null, model, edmProperties.getReferenceTypeForModel(null), -1);
	if (qr.size() == 0) {
	    return true;
	}

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    EPMLink el = (EPMLink) o[0];
	    WTPartMaster wpm = el.getPartMaster();
	    if (pnum.equals(wpm.getNumber())) {
		return true;
	    }
	}
	return false;
    }


    public static Object save(Vector data) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Vector.class };
	    Object args[] = new Object[] { data };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("save", EDMMigDataLoader_pro.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(EDMMigDataLoader_pro.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMMigDataLoader_pro.class, e);
		throw new WTException(e);
	    }
	    return obj;
	}

	Transaction trx = new Transaction();

	Vector r = new Vector();
	try {
	    trx.start();

	    for (int i = 0; i < data.size(); i++) {
		EDMMigData_pro emd = (EDMMigData_pro) data.get(i);

		if (IS_CREATE_PART) {
		    if ((emd.repPartNumber.length() > 0) && !(emd.isRepPartExist)) {
			Hashtable ht = new Hashtable();
			ht.put("number", emd.repPartNumber);
			ht.put("name", emd.name);
			WTPart part = createPart(ht);
			if (part != null) {
			    emd.repPoid = part.getPersistInfo().getObjectIdentifier().getStringValue();
			}
			Kogger.debug(EDMMigDataLoader_pro.class, "create part : " + part.getNumber());
		    }
		}

		try {
		    if (emd.isExist) {
			emd = update(emd);
		    }
		    else {
			emd = save(emd);
		    }
		    emd.isLoadCompleted = true;
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
	    Kogger.error(EDMMigDataLoader_pro.class, e);
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return r;
    }


    public static EDMMigData_pro save(EDMMigData_pro data) throws WTException {
	try {
	    if (!data.isValidate) {
		return data;
	    }

	    EDMProperties edmProperties = EDMProperties.getInstance();

	    if (EPMDocumentUtilities.doesEPMDocumentExist(data.number)) {
		throw new WTException("이미 등록된 도면번호입니다[" + data.number + "]");
	    }

	    if (EDMServiceHelper.isDuplicateCADName(data.cadFileName)) {
		throw new WTException("이미 등록된 CAD 파일 명입니다[" + data.cadFileName + "]");
	    }

	    EPMApplicationType applicationType = EPMApplicationType.toEPMApplicationType(edmProperties.getAppTypeByPLM());
	    EPMContextHelper.setApplication(applicationType);


	    EPMDocument epm = null;

	    epm = EPMDocument.newEPMDocument(data.number, data.name, data.getEPMAuthoringAppType(), data.getEPMDocumentType(), data.cadFileName);

	    EPMDocumentMaster master = (EPMDocumentMaster) epm.getMaster();
	    master.setOwnerApplication(applicationType);
	    PersistenceServerHelper.manager.restore(master);

	    WTContainerRef wtContainerRef = EDMUtil.getWTContinerRefByEDMDefault();


	    String folderPath = data.folderRoute;
	    if(StringUtils.isNotEmpty(folderPath)){
		SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, wtContainerRef);
		FolderHelper.assignLocation((FolderEntry) epm, folder);
	    }else{
		boolean isWgm = data.iswgmdata;
		if ("PROE".equals(data.cadAppType)) {
		    isWgm = true;
	        }
		Folder folder = FolderTaskLogic.getFolder(data.getLocation(isWgm), wtContainerRef);
		FolderHelper.assignLocation((FolderEntry) epm, folder);		
	    }
	    

	    LifeCycleHelper.setLifeCycle(epm, LifeCycleHelper.service.getLifeCycleTemplate(data.getLifecycle(), wtContainerRef));

	    if ((data.cadVersion.length() > 0) && (Integer.parseInt(data.cadVersion) > 0)) {
		EDMServiceHelper.setVersion(epm, data.cadVersion);
	    }

	    //IBA
	    epm = (EPMDocument) EDMAttributeUtil.setAttributeValues(epm, data.getIBAData());
	    //dilshin 추가
	    epm = (EPMDocument) PersistenceHelper.manager.store(epm);
	    
	    //첨부파일
	    epm = (EPMDocument) ContentHelper.service.getContents(epm);
	    EDMServiceHelper.attach(epm, data.primary, data.secondary);

	    
	    if (CheckInOutTaskLogic.isCheckedOut(epm)) {
		epm = (EPMDocument) CheckInOutTaskLogic.getWorkingCopy(epm);
		epm = (EPMDocument) CheckInOutTaskLogic.checkInObject(epm, "");
	    }
	    
	    //사업부
	    if (data.bizCode != null) {
		EDMServiceHelper.updateTypeCodeLink(epm, data.bizCode, true);
	    }
	    
	    //프로젝트
	    if (data.project != null) {
		EDMServiceHelper.updateProjectLink(epm, ((E3PSProject) data.project).getMaster(), true);
	    }
	    
	    HashMap hm = new HashMap();
	    hm.put("category", data.category);
	    //대표 부품/모델
	    if ((data.repPoid != null) && (data.repPoid.length() > 0)) {
		hm.put("repPoid", data.repPoid);
		hm.put("repModelOid", data.repModelOid);
	    }
	    
	    //관련 부품/모델
	    String torelPoid[] = null;
	    String torelModelOid[] = null;
	    if ((data.refs != null) && (data.refs.size() > 0)) {
		torelPoid = new String[data.refs.size()];
		torelModelOid = new String[data.refs.size()];

		for (int i = 0; i < data.refs.size(); i++) {
		    String[] ref = (String[]) data.refs.get(i);
		    torelPoid[i] = ref[0];
		    torelModelOid[i] = ref[1];
		}
	    }
	    if (torelPoid != null) {
		hm.put("torelPoid", torelPoid);
		hm.put("torelModelOid", torelModelOid);
	    }

	    epm = EDMServiceHelper.setAssociation(epm, hm);
	    
	    if (edmProperties.isRefModel(data.category)) {
		EDMServiceHelper.syncEPMModelData(epm);
	    }
	    
	    if (data.state.trim().length() > 0) {
		epm = (EPMDocument) LifeCycleHelper.service.setLifeCycleState(epm, State.toState(data.state.trim()), false);
	    }
	    
	    updateUserData(epm, data.edmCreateDeptName);
	    
	} catch (Exception e) {
	    Kogger.error(EDMMigDataLoader_pro.class, e);
	    throw new WTException(e);
	}
	return data;
    }

    public static EDMMigData_pro update(EDMMigData_pro data) throws WTException {
	try {
	    if (!data.isValidate) {
		return data;
	    }
	    EDMProperties edmProperties = EDMProperties.getInstance();

	    EPMDocument epm = EPMDocumentUtilities.getEPMDocument(data.number);
	    epm = (EPMDocument) EDMServiceHelper.checkout(epm);

	    if (edmProperties.isAppTypeByPLM(epm.getOwnerApplication().toString())) {
		String curNumber = epm.getNumber();
		String curName = epm.getName();
		String curCADName = ((EPMDocumentMaster) epm.getMaster()).getCADName();

		if (!curNumber.equals(data.number) || !curName.equals(data.name) || !curCADName.equals(data.cadFileName)) {
		    if (!curNumber.equals(data.number) && EPMDocumentUtilities.doesEPMDocumentExist(data.number)) {
			throw new WTException("이미 등록된 도면번호입니다");
		    }

		    if (!curCADName.equals(data.cadFileName) && EDMServiceHelper.isDuplicateCADName(data.cadFileName)) {
			throw new WTException("이미 등록된 CAD 파일 명입니다");
		    }

		    epm = EDMServiceHelper.renumberEPMDocument(epm, data.name, data.number, data.cadFileName);
		}
	    }

	    if (data.cadVersion.length() > 0) {
		EDMServiceHelper.setVersion(epm, data.cadVersion);
	    }

	    //IBA
	    epm = (EPMDocument) EDMAttributeUtil.setAttributeValues(epm, data.getIBAData());

	    epm = (EPMDocument) PersistenceHelper.manager.modify(epm);

	    //첨부파일
	    epm = (EPMDocument) ContentHelper.service.getContents(epm);
	    EDMServiceHelper.attach(epm, data.primary, data.secondary);


	    if (CheckInOutTaskLogic.isCheckedOut(epm)) {
		epm = (EPMDocument) CheckInOutTaskLogic.getWorkingCopy(epm);
		epm = (EPMDocument) CheckInOutTaskLogic.checkInObject(epm, "");
	    }

	    //사업부
	    if (data.bizCode != null) {
		EDMServiceHelper.updateTypeCodeLink(epm, data.bizCode, true);
	    }
	    //프로젝트
	    if (data.project != null) {
		EDMServiceHelper.updateProjectLink(epm, ((E3PSProject) data.project).getMaster(), true);
	    }

	    HashMap hm = new HashMap();
	    hm.put("category", data.category);
	    //대표 부품/모델
	    hm.put("repPoid", data.repPoid);
	    hm.put("repModelOid", data.repModelOid);
	    //관련 부품/모델
	    String torelPoid[] = null;
	    String torelModelOid[] = null;
	    if ((data.refs != null) && (data.refs.size() > 0)) {
		torelPoid = new String[data.refs.size()];
		torelModelOid = new String[data.refs.size()];

		for (int i = 0; i < data.refs.size(); i++) {
		    String[] ref = (String[]) data.refs.get(i);
		    torelPoid[i] = ref[0];
		    torelModelOid[i] = ref[1];
		}
	    }
	    if (torelPoid != null) {
		hm.put("torelPoid", torelPoid);
		hm.put("torelModelOid", torelModelOid);
	    }

	    epm = EDMServiceHelper.setAssociation(epm, hm);

	    if (edmProperties.isRefModel(data.category)) {
		EDMServiceHelper.syncEPMModelData(epm);
	    }

	    if (data.state.trim().length() > 0) {
		epm = (EPMDocument) LifeCycleHelper.service.setLifeCycleState(epm, State.toState(data.state.trim()), false);
	    }

	    updateUserData(epm, data.edmCreateDeptName);

	    //EDMFolderUtil.changeFolders(epm, newFolder, true, true);
	} catch (Exception e) {
	    Kogger.error(EDMMigDataLoader_pro.class, e);
	    throw new WTException(e);
	}
	return data;
    }

    public static WTPart createPart(Hashtable hash) throws WTException {

	WTPart part = null;
	try {
	    String name = (String) hash.get("name");
	    String number = (String) hash.get("number");

	    String quantityUnit = "ea";//(String) hash.get("quantityunit");
	    String partType = "separable";//(String) hash.get("parttype");
	    String source = "make";//(String) hash.get("source");

	    String location = "/Default";
	    if (hash.containsKey("location")) {
		location = (String) hash.get("location");
	    }

	    String lifecycle = "Default";
	    if (hash.containsKey("lifecycle")) {
		lifecycle = (String) hash.get("lifecycle");
	    }

	    String view = "Design";
	    if (hash.containsKey("view")) {
		view = (String) hash.get("view");
	    }

	    part = WTPart.newWTPart();

	    part.setName(name.trim());
	    part.setNumber(number.trim());
	    part.setDefaultUnit(QuantityUnit.toQuantityUnit(quantityUnit));
	    part.setPartType(PartType.toPartType(partType));
	    part.setSource(Source.toSource(source));

	    ViewHelper.assignToView(part, ViewHelper.service.getView(view));

	    WTContainerRef wtContainerRef = EDMUtil.getWTContinerRefByEDMDefault();
	    FolderHelper.assignLocation((FolderEntry) part, FolderTaskLogic.getFolder(location, wtContainerRef));
	    part = (WTPart) LifeCycleHelper.setLifeCycle(part, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, wtContainerRef));

	    part = (WTPart) IBAValueHelper.service.createIBAHolder(part, null);

	    if (hash.containsKey("state")) {
		LifeCycleHelper.service.setLifeCycleState(part, State.toState((String) hash.get("state")), false);
	    }
	} catch (Exception e) {
	    part = null;
	    throw new WTException(e);
	}
	return part;
    }

    public static void updateUserData(EPMDocument epm, String deptName) throws WTException, WTPropertyVetoException {
	EDMUserData ud = EDMHelper.getEDMUserData(epm);
	if (ud != null) {
	    ud.setInitDeptName(deptName);
	    ud.setCreatorDeptName(deptName);
	    ud.setModifierDeptName(deptName);
	    PersistenceHelper.manager.save(ud);
	}
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
	    Kogger.error(EDMMigDataLoader_pro.class, e);
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
	Kogger.debug(EDMMigDataLoader_pro.class, sb.toString());
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
	//String fileName = "10938000.xls";//"22695000.xls";//"22153000.xls";//"10938000.xls";//fileName = args[2];

	long startTimeMillis = System.currentTimeMillis();
	Kogger.debug(EDMMigDataLoader_pro.class, "Load Start...");

	try {
	    loadFile(fileName, type);
	} catch (Exception e) {
	    Kogger.error(EDMMigDataLoader_pro.class, e);
	}

	Kogger.debug(EDMMigDataLoader_pro.class, "Load End...");

	long endTimeMillis = System.currentTimeMillis();
	Kogger.debug(EDMMigDataLoader_pro.class, "Load Time : " + (endTimeMillis - startTimeMillis));
	System.exit(0);
    }
}

class EDMMigData_pro implements Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID     = 1L;

    public int                rownum;

    public String             bizName;                           //check
    public String             edmCreateDeptName;                 //check?
    public String             cadDevStageName;                   //check
    public String             cadCategoryName;                   //check
    public String             cadAppTypeName;
    public String             pjtno;                             //check


    public String             number;                            //check
    public String             name;                              //check
    public String             cadVersion;
    public String             cadFileDir;
    public String             cadFileName;


    public String             repPartNumber;                     //check
    public String             repModelNumber;                    //check

    public String             refPartNums;                       //check
    public String             refModelNums;                      //check

    public String             catiaFile;
    public String             catiaFileDir;

    public String             secondaryFiles;

    //Save Data ............................................

    public String             state                = "APPROVED";

    //사업부 코드
    public NumberCode         bizCode;
    //프로젝트
    public Object             project;

    //IBA
    public String             manageType;
    public String             devStage;
    public String             category;
    public String             cadAppType;

    public String             repPoid              = null;
    public String             repModelOid          = null;
    public String             repModel2DOid        = null;

    public Vector             refs                 = null;


    public File               dir;
    public File               cdir;
    public File               primary;
    public Vector             secondary;

    public String             appType;

    public String             msg;

    public boolean            isExist              = false;
    public boolean            isDocNumberSizeValid = true;
    public boolean            iswgmdata            = false;      //WGM으로 등록된 model, drawing 정보 update
    public boolean            isValidate           = true;
    public boolean            isRepPartExist       = true;

    public boolean            isLoadCompleted      = false;
    
    public String	      folderRoute;

    public EDMMigData_pro(int rownum) {
	this.rownum = rownum;
    }

    public EDMMigData_pro(Cell[] cells, int rownum) {
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
	number = JExcelUtil.getContent(cells, ++colIdex);
	name = JExcelUtil.getContent(cells, ++colIdex);
	cadVersion = JExcelUtil.getContent(cells, ++colIdex);
	cadFileDir = backSlashPrefixToBlank(JExcelUtil.getContent(cells, ++colIdex));


	cadFileName = JExcelUtil.getContent(cells, ++colIdex);
	repPartNumber = JExcelUtil.getContent(cells, ++colIdex);
	repModelNumber = JExcelUtil.getContent(cells, ++colIdex);

	refPartNums = JExcelUtil.getContent(cells, ++colIdex);
	refModelNums = JExcelUtil.getContent(cells, ++colIdex);

	catiaFile = JExcelUtil.getContent(cells, ++colIdex);
	catiaFileDir = backSlashPrefixToBlank(JExcelUtil.getContent(cells, ++colIdex));

	secondaryFiles = JExcelUtil.getContent(cells, ++colIdex);
	folderRoute = JExcelUtil.getContent(cells, ++colIdex);
    }

    public HashMap getIBAData() {
	HashMap im = new HashMap();
	im.put(EDMHelper.IBA_CAD_MANAGE_TYPE, manageType);
	im.put(EDMHelper.IBA_DEV_STAGE, devStage);
	im.put(EDMHelper.IBA_CAD_CATEGORY, category);
	im.put(EDMHelper.IBA_CAD_APP_TYPE, cadAppType);
	im.put(EDMHelper.IBA_DUMMY_FILE, EDMHelper.IBA_DUMMY_FILE_VALUE_NO);
	//dilshin	Add
	Kogger.debug(getClass(), "devstage : " + devStage);
	Kogger.debug(getClass(), "devstage : " + cadVersion);
	if ("DEVELOPMENT_STAGE".equals(devStage) || "DEV_REVIEW_STAGE".equals(devStage)) {
	    
	    if (cadVersion != null && cadVersion.startsWith("D")) {
		im.put(EDMHelper.IBA_MANUFACTURING_VERSION, cadVersion);
	    } else if (cadVersion != null && cadVersion.startsWith("P")) { // 만일을 대비해서 추가
		String revSuffixCode = String.valueOf(Integer.parseInt(cadVersion.substring(1)) + 1);
		try {
		    revSuffixCode = String.valueOf(Integer.parseInt(cadVersion));
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}
		im.put(EDMHelper.IBA_MANUFACTURING_VERSION, revSuffixCode);
	    } else if (cadVersion == null) { 
	    } else {
		String newVersion = cadVersion;
		try {
		    newVersion = "D" + NumberCodeUtil.getSerailNumberFormat(newVersion, 2);
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}
		im.put(EDMHelper.IBA_MANUFACTURING_VERSION, newVersion);
	    }
	    
	}else if ("PRODUCTION_STAGE".equals(devStage)) {
	    im.put(EDMHelper.IBA_MANUFACTURING_VERSION, String.valueOf(Integer.parseInt(cadVersion)));
	}
	return im;
    }

    public String getLocation(boolean isWgm) {
	return EDMProperties.getInstance().getFullPath(bizName, manageType, category, isWgm);
    }

    public String getLifecycle() {
	return EDMProperties.getInstance().getEPMDefaultLC();
    }

    public EPMAuthoringAppType getEPMAuthoringAppType() {
	return EPMAuthoringAppType.toEPMAuthoringAppType(cadAppType);
    }

    public EPMApplicationType getEPMApplicationType() {
	if ((appType == null) || (appType.trim().length() == 0)) {
	    appType = "PLMSYSTEM";
	}
	return EPMApplicationType.toEPMApplicationType(appType);
    }

    public EPMDocumentType getEPMDocumentType() {
	String epmDocumentType = "";
	if (epmDocumentType.length() == 0) {
	    if ("ACAD".equals(cadAppType) || ("EXCESS".equals(cadAppType))) {
		epmDocumentType = "CADDRAWING";
	    }
	    else {
		epmDocumentType = EDMServiceHelper.getEPMDocumentType(cadFileName).toString();
	    }
	}
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
