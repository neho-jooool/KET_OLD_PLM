﻿package ext.ket.shared.drm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.io.FilenameUtils;

import wt.content.ApplicationData;
import wt.content.ContentHolder;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.content.Streamed;
import wt.epm.EPMAuthoringAppType;
import wt.epm.EPMDocument;
import wt.fc.ObjectIdentifier;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fv.FileFolder;
import wt.fv.FvMount;
import wt.fv.StandardFvService;
import wt.fv.StoredItem;
import wt.fv.uploadtocache.CachedContentDescriptor;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;

import com.fasoo.fcwpkg.packager.WorkPackager;
import common.util.JNICipher;

import e3ps.common.drm.E3PSDRMFileDeleteScheduler;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.dms.entity.KETTechnicalDocument;
import ext.ket.shared.log.Kogger;

/**
 * 
 * @클래스명 : DRMHelper
 * @작성자 : Jason, Han
 * @작성일 : 2014. 12. 4.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class DRMHelper {

    public static final boolean VERBOSE = false;
    public static final boolean useDrm = false;
    public static final String drm_fsdinit_path = "D:\\fasoo\\key\\fsdinit";
    /**
     * DRMHelper 설정 Properties 1) useDrm : DRM 사용 여부 2) propertiesFilePath : Service Link 설정 Properties 파일 경로 3) keyFilePath : Service Link
     * 암호화/복호화 Key 파일 경로 4) tempDir : 암호화/복호화를 위한 임시 폴더(디렉토리)
     */
    /**
     * 암호화/복호화를 위한 임시 폴더(디렉토리)
     */

    static String tempDir = getTempDirPath();
    static String uploadFileTempFolder = tempDir + File.separator + "UploadTemp";

    public static boolean isDrmApprove(String fileName) {
	Config conf = ConfigImpl.getInstance();
	String drmExtensions = conf.getString("drmExtensions");
	StringTokenizer st = new StringTokenizer(drmExtensions, ",");
	String fileExt = FilenameUtils.getExtension(fileName);
	Kogger.debug(DRMHelper.class, "==> DRM fileExt :: " + fileExt);
	String extendsStr = "";
	while (st.hasMoreElements()) {
	    extendsStr = st.nextToken().trim();
	    if (extendsStr.length() == 0) {
		continue;
	    }
	    if (fileExt.equalsIgnoreCase(extendsStr)) {
		if (VERBOSE)
		    if (VERBOSE)
			Kogger.debug(DRMHelper.class, "===> E3PSDRMHelper : isDrmApprove() : fileExt drm File true = " + fileExt);
		return true;
	    }
	}
	return false;
    }

    /**
     * <pre>
     * 암호화/복호화를 위한 임시 폴더(디렉토리)
     * </pre>
     * 
     * @return String
     */
    static String getTempDirPath() {
	Config conf = ConfigImpl.getInstance();
	String tempFolder = conf.getString("drm.tempFolder");
	return tempFolder;
    }

    static String getTempUploadFileName(String fileName) {
	Random randomNumber = new Random();
	StringBuffer stringbuffer = new StringBuffer();
	stringbuffer.append(getFileNameWithoutExt(fileName)).append("_");
	stringbuffer.append(Math.abs(randomNumber.nextInt())).append("_Decrypt.").append(FilenameUtils.getExtension(fileName));

	if (VERBOSE) {
	    Kogger.debug(E3PSDRMFileDeleteScheduler.class, "Encrypt TempFile Name : " + stringbuffer.toString());
	}

	return stringbuffer.toString();
    }

    static String getFileNameWithoutExt(String fileName) {
	String returnStr = new String();
	int idx = -1;
	idx = fileName.lastIndexOf('.');

	if (idx < 0) {
	    Kogger.debug(E3PSDRMFileDeleteScheduler.class, "###Error : Filename was wrong.###");
	} else {
	    returnStr = fileName.substring(0, idx);
	}

	return returnStr;
    }

    public static File encryptFile(File file) throws Exception {
	return encryptFile(file, file.getName());
    }

    public static File encryptFile(File file, String fileName) throws Exception {

	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SS");
	String tmpFileName = sdf.format(cal.getTime());

	File orgFile = file;
	File tarFile = new File(tempDir, tmpFileName);

	if (VERBOSE)
	    Kogger.debug(DRMHelper.class, "--------------------------> Copy File");
	copyFile(orgFile, tarFile);

	if (VERBOSE)
	    Kogger.debug(DRMHelper.class, "orgFile : " + orgFile.getAbsolutePath() + " / " + orgFile.length());
	if (VERBOSE)
	    Kogger.debug(DRMHelper.class, "tarFile : " + tarFile.getAbsolutePath() + " / " + tarFile.length());
	/**
	 * [암호화] srcFile에서 DestFile로 파일 암호화 DestFile이 null인 경우 srcFile을 암호화 함
	 */
	if (VERBOSE)
	    Kogger.debug(DRMHelper.class, "tarFile.getAbsolutePath() : " + tarFile.getAbsolutePath());

	return excuteEncrypt(fileName, tmpFileName, tarFile, "");
    }

    public static File encryptDownLoad(File file, String principalIdString, String clientIP, String checkKetCustomFlag, String oid)
	    throws Exception {

	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SS");
	String tmpFileName = sdf.format(cal.getTime());

	File orgFile = file;
	File tarFile = new File(tempDir, tmpFileName);

	if (VERBOSE)
	    Kogger.debug(DRMHelper.class, "--------------------------> Copy File");
	copyFile(orgFile, tarFile);

	if (VERBOSE)
	    Kogger.debug(DRMHelper.class, "orgFile : " + orgFile.getAbsolutePath() + " / " + orgFile.length());
	if (VERBOSE)
	    Kogger.debug(DRMHelper.class, "tarFile : " + tarFile.getAbsolutePath() + " / " + tarFile.length());
	/**
	 * [암호화] srcFile에서 DestFile로 파일 암호화 DestFile이 null인 경우 srcFile을 암호화 함
	 */
	if (VERBOSE)
	    Kogger.debug(DRMHelper.class, "tarFile.getAbsolutePath() : " + tarFile.getAbsolutePath());

	// HashMap<String, String> map = null;
	// if (!"10".equals(principalIdString)) {
	// map = PeopleHelper.manager.getPeopleFromOid(principalIdString);
	// } else {
	// map = new HashMap<String, String>();
	// map.put("id", "Administrator");
	// map.put("name", "관리자");
	// map.put("id", "Administrator");
	// map.put("deptcode", "10190");
	// map.put("deptname", "한국단자공업㈜");
	// map.put("dutycode", "S_J10");
	// map.put("duty", "과장");
	// }

	/*
	 * DRM 적용 시 처리
	 */
	// long outFileLength = -1;
	// String encryptedFileName = tarFile.getAbsolutePath(); // 암호화 원본파일명-Full Path
	// String pstrFileName = file.getName();
	//
	// if (VERBOSE)
	// Kogger.debug(DRMHelper.class, "########## E3PSDRMHelper Class : download Method : pstrFileName : " + pstrFileName +
	// " ##########");
	//
	// long plFileSize = tarFile.length(); // 파일크기
	// String pstrFileId = oid; // 파일ID
	//
	// int piAclFlag = 0; // 권한설정 적용방법: 1-DB, 0-파라미터
	// int piExchangePolicy = 1; // 문서교환정책, 예) 1-사내한, 0-개인한 [#DB필드값#]ExchangePolicy
	// int piDrmFlag = 0; // 암호화 여부 : 0
	// int piUnicodeFlag = 1; // Unicode적용여부 0: ANSI / 1: Unicode
	//
	// int piDocLevel = 0; // 문서권한코드 [#DB필드값#] DocGradeCode
	// String pstrGrade = new String("1급비밀"); // 문서유형 [#DB필드값#] DocGradeName
	// String pstrDocumentTitle = new String(""); // 문서등급권한레벨 [#DB필드값#]DocTitle
	//
	// String pstrUserId = map.get("id"); // 사용자ID
	// String pstrUserName = map.get("name"); // 사용자 명
	// String pstrOwnerId = map.get("id"); // 작성자ID
	// String pstrGroupId = map.get("deptcode"); // 그룹ID
	// String pstrGroupName = map.get("deptname"); // 그룹명
	// String pstrPositionId = map.get("dutycode"); // 직위ID
	// String pstrPositionName = map.get("duty"); // 직위명
	//
	// String pstrUserIp = clientIP;
	//
	// if (VERBOSE)
	// Kogger.debug(DRMHelper.class, "########## E3PSDRMHelper Class : download Method : pstrUserIP : " + pstrUserIp + " ##########");
	//
	// String pstrCompanyId = new String("KET-7892-7F69-8365"); // 회사ID
	// String pstrCompanyName = new String("한국단자공업"); // 회사 명
	// String pstrServerAddr = "192.168.1.123:40002"; // DRM로그서버IP:port
	// String pstrServerOrigin = new String("PLM"); // 시스템 구분
	//
	// int piCanSave = 1; // 저장가능 [#DB필드값#]CanSave
	// int piCanEdit = 1; // 편집가능 [#DB필드값#]CanEdit
	// int piBlockCopy = 0; // 클립보드지원여부[#DB필드값#]CanBlockCopy
	// // -99를 값으로 주면 제한없음을 의미한다
	// int piOpenCount = -99; // 열람제한회수 [#DB필드값#]OpenCount
	// int piPrintCount = 3; // 인쇄제한회수 [#DB필드값#]PrintCount
	// int piValidPeriod = 90; // 문서사용기간 [#DB필드값#]ValidPeriod
	// int piSaveLog = 1; // 저장로그 [#DB필드값#]SaveLog
	// int piPrintLog = 1; // 프린트로그 [#DB필드값#]PrintLog
	// int piOpenLog = 1; // 오픈로그 [#DB필드값#]OpenLog
	// int piVisualPrint = 1; // 워터마킹 [#DB필드값#]PrintSafer
	// int piImageSafer = 1; // 이미지세이퍼 [#DB필드값#]ImageSafer
	// int piRealTimeAcl = 0; // 실시간권한제어
	//
	// // if ("DesignDoc".equals(checkKetCustomFlag) || checkKetCustomFlag.indexOf("ketCustomFlag=DesignDoc") != -1) {// 기술문서-설계가이드 문서일때
	// 암호화정책 다르게 적용
	// // piCanSave = 0;
	// // piCanEdit = 1; // 편집 가능하도록 요청 연구기획팀 전상우 2015.05.13
	// // piPrintCount = 0;
	// // piValidPeriod = -99; // 문서사용기간 제한없음으로 변경요청 연구기획팀 전상우 2015.10.12
	// // // System.out.println("설계가이드 문서 암호화 플래그 : " + checkKetCustomFlag);
	// // }
	// // DRM 20130409 add
	//
	// JNICipher jniCipher = new JNICipher();
	// // m_strEnterpriseID
	// jniCipher.m_strEnterpriseID = "KETG-72C3-EACB-9B59";
	// jniCipher.m_nEncryptPrevCipher = 0; // 이전 사이퍼 암호화 여부 : 1-이전, 0-업그레이드 사이퍼
	// // 0 : 암호화 성공
	// // 1 : 암호화 실패 오류
	// // 2 : 암호화 함수 미 제공(masdmsjni.dll)
	// // 3 : 라이브러리 제어 오류(masdmsjni.dll)
	// // 1001 : 파일명 오류
	// // 1002 : 사용자ID 오류
	// outFileLength = jniCipher.EncryptFileInPlace(encryptedFileName, piAclFlag, piDocLevel, pstrUserId, pstrFileName, plFileSize,
	// pstrOwnerId, pstrCompanyId, pstrGroupId,
	// pstrPositionId, pstrGrade, pstrFileId, piCanSave, piCanEdit, piBlockCopy, piOpenCount, piPrintCount, piValidPeriod, piSaveLog,
	// piPrintLog, piOpenLog,
	// piVisualPrint, piImageSafer, piRealTimeAcl, pstrServerAddr, pstrDocumentTitle, pstrCompanyName, pstrGroupName, pstrPositionName,
	// pstrUserName, pstrUserIp,
	// pstrServerOrigin, piExchangePolicy, piDrmFlag, piUnicodeFlag);
	//
	// if (VERBOSE)
	// Kogger.debug(DRMHelper.class, "outFileLength : " + outFileLength);

	return excuteEncrypt(file.getName(), tmpFileName, tarFile, checkKetCustomFlag);
    }

    /**
     * <pre>
     * 첨부파일 다운로드 시 파일 암호화
     * 
     * 다음의 Method에서 파일 암호화를 하고 있다.
     * wt.fv.master.StandardMasterService.doDirectDownload( HTTPRequest req, HTTPResponse resp )
     * </pre>
     * 
     * @param appDataIdString
     *            String
     * @param file
     *            File
     * @return File
     * @throws WTException
     */
    public static File encryptFile(String appDataIdString, File file, String principalIdString, String clientIP, String checkKetCustomFlag)
	    throws WTException {
	try {
	    ApplicationData appData = ContentServerHelper.service.getApplicationData(ObjectIdentifier
		    .newObjectIdentifier("wt.content.ApplicationData:" + appDataIdString));

	    String fileName = appData.getFileName();
	    /**
	     * 도면의 경우, cadname을 ApplicationData에서 가져오기때문에 확장자를 체크할 수 가 없다 그래서 도면일 때 fileName을 가져오는 방식을 변경함 2014.12.17 황정태
	     */

	    /*
	     * IF customization ui에서 호출 시 drm 걸리도록 $CAD_NAME 처리, ELSE creo일 경우에는 drm 안걸리도록 $CAD_NAME 처리안함, NX 등일 경우에 DRM 처리함
	     */

	    boolean isKetCustomFlag = false;
	    if (checkKetCustomFlag != null) {
		if (checkKetCustomFlag.indexOf("ketCustomFlag=Y") != -1) {
		    isKetCustomFlag = true;
		}
	    }

	    // IF customization ui에서 호출 시
	    if (isKetCustomFlag) {

		if (fileName.equals("{$CAD_NAME}")) {
		    Kogger.debug(DRMHelper.class, "########################" + fileName);
		    /*
	             * if (appData.getHolderLink() != null && appData.getHolderLink().getContentHolder() != null) { fileName =
	             * ((EPMDocument) appData.getHolderLink().getContentHolder()).getCADName(); }
	             */

		    SessionContext sessioncontext = SessionContext.newContext();
		    try {
			SessionHelper.manager.setAdministrator();
			QuerySpec query = new QuerySpec();
			int idx = query.addClassList(wt.content.HolderToContent.class, true);

			query.appendWhere(new SearchCondition(wt.content.HolderToContent.class, "roleBObjectRef.key.id",
			        SearchCondition.EQUAL, Long.parseLong(appDataIdString)), new int[] { idx });

			QueryResult qr = PersistenceHelper.manager.find(query);
			if (qr.hasMoreElements()) {
			    Object[] obj = (Object[]) qr.nextElement();
			    wt.content.HolderToContent holder = (wt.content.HolderToContent) obj[0];
			    EPMDocument epm = (EPMDocument) holder.getRoleAObject();
			    fileName = epm.getCADName();
			}
		    } finally {
			SessionContext.setContext(sessioncontext);
		    }
		    // Kogger.debug(DRMHelper.class, "========> ContentUtil Class :: getContentIconStr Method :: FileName 2 : " +
		    // tempFileName );
		}

	    } else {

		Kogger.debug(DRMHelper.class, "########################" + fileName);
		System.out.println("::: OOTB에서 DRM 호출..");
		SessionContext sessioncontext = SessionContext.newContext();
		try {
		    SessionHelper.manager.setAdministrator();
		    QuerySpec query = new QuerySpec();
		    int idx = query.addClassList(wt.content.HolderToContent.class, true);

		    query.appendWhere(new SearchCondition(wt.content.HolderToContent.class, "roleBObjectRef.key.id", SearchCondition.EQUAL,
			    Long.parseLong(appDataIdString)), new int[] { idx });

		    QueryResult qr = PersistenceHelper.manager.find(query);
		    if (qr.hasMoreElements()) {
			Object[] obj = (Object[]) qr.nextElement();
			wt.content.HolderToContent holder = (wt.content.HolderToContent) obj[0];

			if (fileName.equals("{$CAD_NAME}")) {
			    EPMDocument epm = (EPMDocument) holder.getRoleAObject();
			    EPMAuthoringAppType cadAppTypeEnum = epm.getAuthoringApplication();
			    Kogger.debug(DRMHelper.class, "EPMAuthoringAppType: " + cadAppTypeEnum.toString());
			    String cadAppType = cadAppTypeEnum.toString(); // PROE

			    if ("PROE".equals(cadAppType)) {
				// DRM에서 제거함.
			    } else {
				fileName = epm.getCADName();
			    }
			} else {
			    System.out.println("::: OOTB에서 DRM 호출..기술문서 체크!");
			    Object obj_ = holder.getRoleAObject();
			    if (obj_ instanceof KETTechnicalDocument) {
				System.out.println("::: OOTB에서 DRM 호출..기술문서임 !");
				KETTechnicalDocument techdoc = (KETTechnicalDocument) obj_;
				if ("Y".equals(techdoc.getAttribute1())) {
				    checkKetCustomFlag = "DesignDoc";
				}
				System.out.println("::: checkKetCustomFlag ===> " + checkKetCustomFlag);
			    }
			}
		    }
		} finally {
		    SessionContext.setContext(sessioncontext);
		}
		// Kogger.debug(DRMHelper.class, "========> ContentUtil Class :: getContentIconStr Method :: FileName 2 : " + tempFileName
		// );

	    }

	    // 도면일때 파일명 가져오기 Start

	    /*
	     * ContentHolder contentholder = appData.getHolderLink().getContentHolder();
	     * 
	     * String fileName = appData.getFileName();
	     * 
	     * if( contentholder instanceof EPMDocument ) { Kogger.debug(DRMHelper.class, "Epmdocument DRM.. "); try { fileName =
	     * wt.epm.util.EPMContentHelper.getContentDisplayName( (EPMDocument)contentholder, appData ); Kogger.debug(DRMHelper.class,
	     * "Epmdocument DRM Name : "+fileName);
	     * 
	     * } catch( Exception e ) { fileName = appData.getFileName(); Kogger.error(DRMHelper.class, e); } }
	     */
	    // 도면일때 파일명 가져오기 End

	    if (!isDrmApprove(fileName)) {
		if (VERBOSE)
		    Kogger.debug(DRMHelper.class, "not drm = " + fileName);
		return file;
	    }
	    ContentRoleType roleType = appData.getRole();
	    if (ContentRoleType.PRIMARY.toString() != roleType.toString() && ContentRoleType.SECONDARY.toString() != roleType.toString()) {
		Kogger.debug(DRMHelper.class, roleType.toString() + " not drm = " + fileName);
		return file;
	    }
	    // if (ContentRoleType.PRODUCT_VIEW_ED == roleType || ContentRoleType.PRODUCT_VIEW_EDM == roleType ||
	    // ContentRoleType.PRODUCT_VIEW_EDP == roleType
	    // || ContentRoleType.PRODUCT_VIEW_EDZ == roleType || ContentRoleType.THUMBNAIL == roleType || ContentRoleType.THUMBNAIL3D ==
	    // roleType ||
	    // ContentRoleType.THUMBNAIL_SMALL == roleType) {
	    // if (VERBOSE) Kogger.debug(DRMHelper.class, roleType.toString() + " not drm = " + fileName);
	    // return file;
	    // }

	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SS");
	    String tmpFileName = sdf.format(cal.getTime());

	    File orgFile = file;
	    File tarFile = new File(tempDir, tmpFileName);

	    if (VERBOSE)
		Kogger.debug(DRMHelper.class, "--------------------------> Copy File");
	    copyFile(orgFile, tarFile);

	    if (VERBOSE)
		Kogger.debug(DRMHelper.class, "orgFile : " + orgFile.getAbsolutePath() + " / " + orgFile.length());
	    if (VERBOSE)
		Kogger.debug(DRMHelper.class, "tarFile : " + tarFile.getAbsolutePath() + " / " + tarFile.length());
	    /**
	     * [암호화] srcFile에서 DestFile로 파일 암호화 DestFile이 null인 경우 srcFile을 암호화 함
	     */
	    if (VERBOSE)
		Kogger.debug(DRMHelper.class, "tarFile.getAbsolutePath() : " + tarFile.getAbsolutePath());
	    // if (VERBOSE) Kogger.debug(DRMHelper.class, "appData.getStreamId() : " + appData.getStreamId());
	    if (VERBOSE)
		Kogger.debug(DRMHelper.class, "FilenameUtils.getBaseName() : " + FilenameUtils.getBaseName(appData.getFileName()));
	    if (VERBOSE)
		Kogger.debug(DRMHelper.class, "FilenameUtils.getExtension() : " + FilenameUtils.getExtension(appData.getFileName()));

	    // HashMap<String, String> map = null;
	    // if (!"10".equals(principalIdString)) {
	    // map = PeopleHelper.manager.getPeopleFromOid(principalIdString);
	    // } else {
	    // map = new HashMap<String, String>();
	    // map.put("id", "Administrator");
	    // map.put("name", "관리자");
	    // map.put("id", "Administrator");
	    // map.put("deptcode", "10190");
	    // map.put("deptname", "한국단자공업㈜");
	    // map.put("dutycode", "S_J10");
	    // map.put("duty", "과장");
	    // }
	    //
	    // /*
	    // * DRM 적용 시 처리
	    // */
	    // long outFileLength = -1;
	    // String encryptedFileName = tarFile.getAbsolutePath(); // 암호화 원본파일명-Full Path
	    // String pstrFileName = fileName;
	    //
	    // if (VERBOSE)
	    // Kogger.debug(DRMHelper.class, "########## E3PSDRMHelper Class : download Method : pstrFileName : " + pstrFileName +
	    // " ##########");
	    //
	    // long plFileSize = tarFile.length(); // 파일크기
	    // String pstrFileId = new String(appDataIdString); // 파일ID
	    //
	    // int piAclFlag = 0; // 권한설정 적용방법: 1-DB, 0-파라미터
	    // int piExchangePolicy = 1; // 문서교환정책, 예) 1-사내한, 0-개인한 [#DB필드값#]ExchangePolicy
	    // int piDrmFlag = 0; // 암호화 여부 : 0
	    // int piUnicodeFlag = 1; // Unicode적용여부 0: ANSI / 1: Unicode
	    //
	    // int piDocLevel = 0; // 문서권한코드 [#DB필드값#] DocGradeCode
	    // String pstrGrade = new String("1급비밀"); // 문서유형 [#DB필드값#] DocGradeName
	    // String pstrDocumentTitle = new String(""); // 문서등급권한레벨 [#DB필드값#]DocTitle
	    //
	    // String pstrUserId = map.get("id"); // 사용자ID
	    // String pstrUserName = map.get("name"); // 사용자 명
	    // String pstrOwnerId = map.get("id"); // 작성자ID
	    // String pstrGroupId = map.get("deptcode"); // 그룹ID
	    // String pstrGroupName = map.get("deptname"); // 그룹명
	    // String pstrPositionId = map.get("dutycode"); // 직위ID
	    // String pstrPositionName = map.get("duty"); // 직위명
	    //
	    // String pstrUserIp = clientIP;
	    //
	    // if (VERBOSE)
	    // Kogger.debug(DRMHelper.class, "########## E3PSDRMHelper Class : download Method : pstrUserIP : " + pstrUserIp +
	    // " ##########");
	    //
	    // String pstrCompanyId = new String("KET-7892-7F69-8365"); // 회사ID
	    // String pstrCompanyName = new String("한국단자공업"); // 회사 명
	    // String pstrServerAddr = "192.168.1.123:40002"; // DRM로그서버IP:port
	    // String pstrServerOrigin = new String("PLM"); // 시스템 구분
	    //
	    // int piCanSave = 1; // 저장가능 [#DB필드값#]CanSave
	    // int piCanEdit = 1; // 편집가능 [#DB필드값#]CanEdit
	    // int piBlockCopy = 0; // 클립보드지원여부[#DB필드값#]CanBlockCopy
	    // // -99를 값으로 주면 제한없음을 의미한다
	    // int piOpenCount = -99; // 열람제한회수 [#DB필드값#]OpenCount
	    // int piPrintCount = 3; // 인쇄제한회수 [#DB필드값#]PrintCount
	    // int piValidPeriod = 90; // 문서사용기간 [#DB필드값#]ValidPeriod
	    // int piSaveLog = 1; // 저장로그 [#DB필드값#]SaveLog
	    // int piPrintLog = 1; // 프린트로그 [#DB필드값#]PrintLog
	    // int piOpenLog = 1; // 오픈로그 [#DB필드값#]OpenLog
	    // int piVisualPrint = 1; // 워터마킹 [#DB필드값#]PrintSafer
	    // int piImageSafer = 1; // 이미지세이퍼 [#DB필드값#]ImageSafer
	    // int piRealTimeAcl = 0; // 실시간권한제어
	    //
	    // if ("DesignDoc".equals(checkKetCustomFlag) || checkKetCustomFlag.indexOf("ketCustomFlag=DesignDoc") != -1) {// 기술문서-설계가이드
	    // 문서일때 암호화정책 다르게 적용
	    // piCanSave = 0;
	    // piCanEdit = 1; // 편집 가능하도록 요청 연구기획팀 전상우 2015.05.13
	    // piPrintCount = 0;
	    // piValidPeriod = -99; // 문서사용기간 제한없음으로 변경요청 연구기획팀 전상우 2015.10.12
	    // // System.out.println("설계가이드 문서 암호화 플래그 : " + checkKetCustomFlag);
	    // }
	    // // DRM 20130409 add
	    //
	    // JNICipher jniCipher = new JNICipher();
	    // // m_strEnterpriseID
	    // jniCipher.m_strEnterpriseID = "KETG-72C3-EACB-9B59";
	    // jniCipher.m_nEncryptPrevCipher = 0; // 이전 사이퍼 암호화 여부 : 1-이전, 0-업그레이드 사이퍼
	    // // 0 : 암호화 성공
	    // // 1 : 암호화 실패 오류
	    // // 2 : 암호화 함수 미 제공(masdmsjni.dll)
	    // // 3 : 라이브러리 제어 오류(masdmsjni.dll)
	    // // 1001 : 파일명 오류
	    // // 1002 : 사용자ID 오류
	    // outFileLength = jniCipher.EncryptFileInPlace(encryptedFileName, piAclFlag, piDocLevel, pstrUserId, pstrFileName, plFileSize,
	    // pstrOwnerId, pstrCompanyId, pstrGroupId,
	    // pstrPositionId, pstrGrade, pstrFileId, piCanSave, piCanEdit, piBlockCopy, piOpenCount, piPrintCount, piValidPeriod,
	    // piSaveLog, piPrintLog, piOpenLog,
	    // piVisualPrint, piImageSafer, piRealTimeAcl, pstrServerAddr, pstrDocumentTitle, pstrCompanyName, pstrGroupName,
	    // pstrPositionName, pstrUserName, pstrUserIp,
	    // pstrServerOrigin, piExchangePolicy, piDrmFlag, piUnicodeFlag);
	    //
	    // if (VERBOSE)
	    // Kogger.debug(DRMHelper.class, "outFileLength : " + outFileLength);

	    return excuteEncrypt(fileName, tmpFileName, tarFile, checkKetCustomFlag);

	} catch (Exception e) {
	    Kogger.error(DRMHelper.class, e);
	    throw new WTException(e.toString());
	}
    }

    public static File excuteEncrypt(String fileName, String tmpFileName, File tarFile, String checkKetCustomFlag) throws Exception {

	try {
	    // final String drm_fsdinit_path = "D:\\fasoo\\key\\fsdinit"; // fsdinit 폴더 FullPath 설정

	    final String domain_id = "0100000000001454"; // 한국단자공업 고유 코드
	    
	    // 08f7268981f048e692edb1fd838065fe          일반
	    // 3908cf744cd542768c4bbe789db75750        PLM1급
	    // 3e266ff174164b7683540762bdef4d81         인비
	    // 8486b2085ff34f0fa51098d2944b7d5c          2급 비밀
	    // a8453c90cd6344629cbfa0d0ded19be4        1급 비밀
	    // bde693e21c0d47d1b76b037f29ccc3d7        대외비


	    String SecurityCode = "bde693e21c0d47d1b76b037f29ccc3d7"; // 등급코드(대외비)

	    if ("DesignDoc".equals(checkKetCustomFlag)) {
		SecurityCode = "3908cf744cd542768c4bbe789db75750"; // 설계가이드문서일 경우 읽기만 가능
	    }

	    int iErrCheck = 0;

	    String sErrMessage = "";

	    int iBret = 0;

	    String extention = "." + FilenameUtils.getExtension(fileName);

	    WorkPackager oWorkPackager = new WorkPackager();

	    iBret = oWorkPackager.GetFileType(tarFile.getAbsolutePath()); // 파일 타입 체크

	    System.out.println("iBret : " + iBret);

	    if (iBret == 29) { // 일반 문서일 경우 암호화

		boolean nret = false;

		nret = oWorkPackager.IsSupportFile(drm_fsdinit_path, domain_id, tarFile.getAbsolutePath() + extention); // 지원 확장자 구분

		System.out.println("nret" + nret);

		if (nret) {

		    oWorkPackager.setOverWriteFlag(false); // OverWrite true/false

		    boolean bret = oWorkPackager.DoPackagingFsn2(drm_fsdinit_path, domain_id, tarFile.getAbsolutePath(), tempDir + "\\"
			    + "DownloadTemp" + "\\" + tmpFileName, fileName, "admin", // 소유자 ID > 업무시스템명에 맞게 변경 필요 (ex. GROUPWARE)
			    "Administrator", // 소유자 이름 > 업무시스템명에 맞게 변경 필요 (ex. GROUPWARE)
			    "admin", "Administrator", "KET", "PLM", // 소유자 ID, 이름, 부서코드, 부서명 > 업무시스템명에 맞게 변경 필요 (ex. GROUPWARE)
			    "admin", "Administrator", "KET", "PLM", // 업무시스템명에 맞게 변경 필요 (ex. GROUPWARE)
			    SecurityCode);
		    if (!bret) {
			if (oWorkPackager.getLastErrorNum() == 11) {

			} else {
			    System.out.println("암호화 중 오류입니다.");
			    System.out.println(" 오류 정보..");
			    System.out.println("    [" + oWorkPackager.getLastErrorNum() + "] " + oWorkPackager.getLastErrorStr());
			    iErrCheck = 1;
			    sErrMessage = oWorkPackager.getLastErrorStr();
			}
		    } else {
			System.out.println("암호화성공 Packaged FileName : " + oWorkPackager.getContainerFileName() + "");
		    }

		} else {
		    System.out.println(" 오류 정보..");
		    System.out.println("    [" + oWorkPackager.getLastErrorNum() + "] " + oWorkPackager.getLastErrorStr());
		}
	    } else { // 지원하지 않는 확장자 파일일 경우
		System.out.println("지원하지 않는 확장자 파일입니다!(복호화되지 않고 서버에 업로드된 것으로 판단됨..");
		if (oWorkPackager.getLastErrorNum() == 0) {

		} else {
		    System.out.println(" 오류 정보..");
		    System.out.println("    [" + oWorkPackager.getLastErrorNum() + "] " + oWorkPackager.getLastErrorStr());
		}
	    }
	    if (iErrCheck == 0) {

	    } else {
		System.out.println("Download Action Error [message]: " + sErrMessage);
	    }

	    File drmFile = new File(tempDir + "\\" + "DownloadTemp" + "\\" + tmpFileName);
	    return drmFile;
	} catch (Exception e) {
	    Kogger.error(DRMHelper.class, e);
	    throw new WTException(e.toString());
	}

    }

    /**
     * <pre>
     * 첨부파일 업로드 시 파일 복호화
     * 
     * 다음의 Method에서 파일 복호화를 하고 있다.
     * wt.content.StandardContentService.updateContent( ContentHolder holder, ApplicationData appData, CachedContentDescriptor cachedContDesc, boolean mustCreateNewFile )
     * </pre>
     * 
     * @param holder
     *            ContentHolder
     * @param appData
     *            ApplicationData
     * @param cachedContDesc
     *            CachedContentDescriptor
     * @return void
     * @throws WTException
     */
    public static void decryptFile(ContentHolder holder, ApplicationData appData, CachedContentDescriptor cachedContDesc)
	    throws WTException {
	try {
	    Streamed streamed = null;
	    if (appData.getStreamData() != null) {
		streamed = (Streamed) appData.getStreamData().getObject();
	    }

	    if (VERBOSE)
		Kogger.debug(DRMHelper.class, "----------------------> " + streamed);
	    if (streamed instanceof StoredItem) {
		StoredItem storeItem = (StoredItem) streamed;
		FileFolder filefolder = storeItem.getFolder();
		FvMount fvmount = StandardFvService.getLocalMount(filefolder);

		String folderPath = fvmount.getPath();
		File orgFile = new File(folderPath, storeItem.getName());
		File tarFile = new File(tempDir, storeItem.getName());
		File tmpFile = new File(tempDir, "dec_" + storeItem.getName());

		if (VERBOSE)
		    Kogger.debug(DRMHelper.class, "--------------------------> Copy File");
		copyFile(orgFile, tarFile);

		if (VERBOSE)
		    Kogger.debug(DRMHelper.class, "orgFile : " + orgFile.getAbsolutePath() + " / " + orgFile.length());
		if (VERBOSE)
		    Kogger.debug(DRMHelper.class, "tarFile : " + tarFile.getAbsolutePath() + " / " + tarFile.length());
		if (VERBOSE)
		    Kogger.debug(DRMHelper.class, "tmpFile : " + tmpFile.getAbsolutePath() + " / " + tmpFile.length());

		/**
		 * [복호화] srcFile에서 DestFile로 파일 복호화 DestFile이 null인 경우 srcFile을 복호화 함
		 */

	    }
	} catch (Exception e) {
	    throw new WTException(e.toString());
	}
    }

    /**
     * <pre>
     * 첨부파일 업로드 시 파일 복호화
     * 
     * 다음의 Method에서 파일 복호화를 하고 있다.
     * wt.content.StandardContentService.updateContent( ContentHolder holder, ApplicationData appData, InputStream is, boolean mustCreateNewFile )
     * </pre>
     * 
     * @param holder
     *            ContentHolder
     * @param appData
     *            ApplicationData
     * @param is
     *            InputStream
     * @return InputStream
     * @throws WTException
     */
    public static InputStream decryptFile(ContentHolder holder, ApplicationData appData, InputStream is) throws WTException {
	InputStream dis = null;

	try {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SS");
	    String fileName = sdf.format(cal.getTime());
	    File tarFile = new File(tempDir, fileName);
	    File tmpFile = new File(tempDir, "dec_" + fileName);

	    copyFile(is, tarFile);
	    dis = new FileInputStream(tarFile);
	    if (VERBOSE)
		Kogger.debug(DRMHelper.class, "orgInputStream : " + is.toString());
	    if (VERBOSE)
		Kogger.debug(DRMHelper.class, "tarFile : " + tarFile.getAbsolutePath() + " / " + tarFile.length());
	    if (VERBOSE)
		Kogger.debug(DRMHelper.class, "tmpFile : " + tmpFile.getAbsolutePath() + " / " + tmpFile.length());

	} catch (Exception e) {
	    throw new WTException(e.toString());
	}
	return dis;
    }

    /**
     * <pre>
     * 파일 복사
     * </pre>
     * 
     * @param orgFile
     *            File
     * @param tarFile
     *            File
     * @return void
     * @throws WTException
     */
    public static void copyFile(File orgFile, File tarFile) throws WTException {
	BufferedInputStream bis = null;
	BufferedOutputStream bos = null;

	try {
	    bis = new BufferedInputStream(new FileInputStream(orgFile));
	    bos = new BufferedOutputStream(new FileOutputStream(tarFile));

	    int len = 0;
	    byte[] b = new byte[2048];

	    while ((len = bis.read(b)) > 0) {
		bos.write(b, 0, len);
		bos.flush();
	    }
	} catch (Exception e) {
	    throw new WTException(e.toString());
	} finally {
	    if (bis != null) {
		try {
		    bis.close();
		} catch (Exception e) {
		}
	    }

	    if (bos != null) {
		try {
		    bos.close();
		} catch (Exception e) {
		}
	    }
	}
    }

    /**
     * <pre>
     * 파일 복사
     * </pre>
     * 
     * @param is
     *            InputStream
     * @param tarFile
     *            File
     * @return void
     * @throws WTException
     */
    public static void copyFile(InputStream is, File tarFile) throws WTException {
	BufferedInputStream bis = null;
	BufferedOutputStream bos = null;

	try {
	    bis = new BufferedInputStream(is);
	    bos = new BufferedOutputStream(new FileOutputStream(tarFile));

	    int len = 0;
	    byte[] b = new byte[2048];

	    while ((len = bis.read(b)) > 0) {
		bos.write(b, 0, len);
		bos.flush();
	    }
	} catch (Exception e) {
	    throw new WTException("fail to copy file");
	} finally {
	    if (bis != null) {
		try {
		    bis.close();
		} catch (Exception e) {
		}
	    }

	    if (bos != null) {
		try {
		    bos.close();
		} catch (Exception e) {
		}
	    }
	}
    }

    public static File MarkAnyDecryptupload(File file, String fileName) throws WTException {

	File fileOut = null;
	BufferedInputStream in = null;
	BufferedOutputStream out = null;
	int nRet = 0;

	try {

	    JNICipher jniCipher = new JNICipher();

	    // 업로드할 파일이 암호화 되어 있는 파일인지 확인
	    // 리턴 값 0 : 일반파일, 1 : 암호화된파일
	    nRet = jniCipher.IsEncrypted(file.getAbsolutePath());

	    if (nRet != 1) {
		return file;
	    }
	    int outFileLength = -1;

	    in = new BufferedInputStream(new FileInputStream(file));
	    fileOut = new File(uploadFileTempFolder + File.separator + getTempUploadFileName(fileName));

	    out = new BufferedOutputStream(new FileOutputStream(fileOut));
	    int read = 0;
	    byte b[] = new byte[4096];

	    while ((read = in.read(b)) > 0) {
		out.write(b, 0, read);
		out.flush();
	    }

	    if (out != null) {
		out.close();
	    }

	    if (nRet == 1) {
		outFileLength = jniCipher.DecryptFileInPlace(fileOut.getAbsolutePath());
	    }

	} catch (Exception e) {
	    Kogger.error(E3PSDRMFileDeleteScheduler.class, e);
	    throw new WTException(e);
	} finally {
	    try {
		if (in != null) {
		    in.close();
		}

		if (out != null) {
		    out.close();
		}
	    } catch (Exception e) {
		Kogger.error(E3PSDRMFileDeleteScheduler.class, e);
	    }

	}
	return fileOut;
    }

    public static boolean isMarkAnyDrm(File file) {
	JNICipher jniCipher = new JNICipher();
	int nRet = jniCipher.IsEncrypted(file.getAbsolutePath());

	return nRet == 1;
    }

    public static File Decryptupload(File file, String fileName) throws WTException {

	File fileOut = null;
	BufferedInputStream in = null;
	BufferedOutputStream out = null;
	// int nRet = 0;

	int error_num = 0;
	String error_str = "";
	int Error_Check = 0;
	String Error_Message = "";

	try {

	    // JNICipher jniCipher = new JNICipher();
	    //
	    // // 업로드할 파일이 암호화 되어 있는 파일인지 확인
	    // // 리턴 값 0 : 일반파일, 1 : 암호화된파일
	    // nRet = jniCipher.IsEncrypted(file.getAbsolutePath());
	    //
	    // if (nRet != 1) {
	    // return file;
	    // }
	    // int outFileLength = -1;
	    //
	    // in = new BufferedInputStream(new FileInputStream(file));
	    // fileOut = new File(uploadFileTempFolder + File.separator + getTempUploadFileName(fileName));
	    //
	    // out = new BufferedOutputStream(new FileOutputStream(fileOut));
	    // int read = 0;
	    // byte b[] = new byte[4096];
	    //
	    // while ((read = in.read(b)) > 0) {
	    // out.write(b, 0, read);
	    // out.flush();
	    // }
	    //
	    // if (out != null) {
	    // out.close();
	    // }
	    //
	    // if (nRet == 1) {
	    // outFileLength = jniCipher.DecryptFileInPlace(fileOut.getAbsolutePath());
	    // }
	    //
	    Hashtable GetHeaderstr = null;

	    int iBret = 0;
	    WorkPackager oWorkPackager = new WorkPackager();

	    iBret = oWorkPackager.GetFileType(file.getAbsolutePath()); // 파일 타입 체크
	    System.out.println("iBret : " + iBret);
	    if (iBret == 26 || iBret == 103 || iBret == 106) { // 암호화 문서일 경우 복호화

		System.out.println("File type ? " + iBret);

		boolean bRet = false;

		GetHeaderstr = oWorkPackager.GetFileHeaderW(file.getAbsolutePath());

		for (Enumeration e = GetHeaderstr.keys(); e.hasMoreElements();) {
		    String msgName = (String) e.nextElement();
		    String msgValue = (String) GetHeaderstr.get(msgName);
		    System.out.println(msgName + "=" + msgValue);
		}

		int i = 0;
		String SecurityCodeValue = (String) GetHeaderstr.get("misc2-info");
		System.out.println("SecurityCode:" + SecurityCodeValue); // 등급코드

		String CPIDValue = (String) GetHeaderstr.get("CPID");
		System.out.println("CPIDValue:" + CPIDValue);
		String domain_id = CPIDValue;

		System.out.println("File type ? " + iBret);

		String decryptFile = uploadFileTempFolder + File.separator + getTempUploadFileName(fileName);

		bRet = oWorkPackager.DoExtract(drm_fsdinit_path, domain_id, file.getAbsolutePath(), decryptFile);

		fileOut = new File(decryptFile);

		error_num = oWorkPackager.getLastErrorNum();

		error_str = oWorkPackager.getLastErrorStr();

		if (error_num != 0) {
		    System.out.println("error_num = ? " + error_num);
		    System.out.println("error_str = ?[ " + error_str + " ]");

		    Error_Check = 1;
		    Error_Message = "DRM_PKGING_ERROR";
		} else {
		    System.out.println("FSD 문서 복호화");
		    System.out.println("DoExtract Success!!! ");
		}

	    } else {
		Error_Check = 1;
		Error_Message = "NOT Support File";
		return file;
	    }

	} catch (NoClassDefFoundError e) {
	    throw new WTException("서버에 DRM 모듈이 설정되어 있지 않음..");
	} catch (Exception e) {
	    System.out.println("Error_Check num = :" + error_num);
	    System.out.println("error_str = :" + error_str);
	    System.out.println("Error_Message = :" + Error_Message);
	    Kogger.error(E3PSDRMFileDeleteScheduler.class, e);
	    throw new WTException(e);
	} finally {
	    try {
		if (in != null) {
		    in.close();
		}

		if (out != null) {
		    out.close();
		}
	    } catch (Exception e) {
		Kogger.error(E3PSDRMFileDeleteScheduler.class, e);
	    }

	}
	return fileOut;
    }

    public static boolean isEncFile(File file) {
	int iBret = 0;
	WorkPackager oWorkPackager = new WorkPackager();

	iBret = oWorkPackager.GetFileType(file.getAbsolutePath()); // 파일 타입 체크
	System.out.println("iBret : " + iBret);
	return (iBret == 26 || iBret == 103 || iBret == 106); // 암호화 문서일 경우
    }

}
