package e3ps.common.drm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.StringTokenizer;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.httpgw.HTTPRequest;
import wt.method.RemoteAccess;
import wt.org.WTUser;
import wt.session.SessionHelper;
import wt.util.EncodingConverter;
import wt.util.WTException;
import wt.util.WTProperties;
import common.util.JNICipher;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.groupware.company.People;
import e3ps.groupware.company.WTUserPeopleLink;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

public class E3PSDRMHelper implements java.io.Serializable, RemoteAccess {

    private static final long   serialVersionUID       = -7277643209320587027L;

    static final boolean        SERVER                 = wt.method.RemoteMethodServer.ServerFlag;
    public static final boolean VERBOSE                = ConfigImpl.getInstance().getBoolean("drm.verbose", false);
    public static final boolean isDRM                  = ConfigImpl.getInstance().getBoolean("DRM", false);

    public static E3PSDRMHelper manager                = new E3PSDRMHelper();

    public static String        wtTempFolder           = null;
    public static String        downloadFileTempFolder = null;
    public static String        uploadFileTempFolder   = null;

    static {
	drmInit();
    }

    public static boolean isDrmApprove(String fileName) {
	Config conf = ConfigImpl.getInstance();
	String drmExtensions = conf.getString("drmExtensions");
	StringTokenizer st = new StringTokenizer(drmExtensions, ",");
	String fileExt = getFileExtension(fileName);
	String extendsStr = "";

	while (st.hasMoreElements()) {
	    extendsStr = st.nextToken().trim();

	    if (extendsStr.length() == 0) {
		continue;
	    }

	    if (fileExt.equalsIgnoreCase(extendsStr)) {

		if (VERBOSE) {
		    Kogger.debug(E3PSDRMFileDeleteScheduler.class, "===> E3PSDRMHelper : isDrmApprove() : fileExt drm File true = " + fileExt);
		}

		return true;
	    }
	}

	return false;
    }

    public static File download(InputStream in, String fileName, String contentOid, String clientIP) throws WTException {
	BufferedOutputStream outs = null;
	BufferedInputStream fin = null;

	byte b[] = new byte[4096];

	int read = 0;
	File orgFile = null;
	try {
	    orgFile = new File(downloadFileTempFolder + File.separator + getTempDownloadFileName(fileName));
	    fin = new BufferedInputStream(in);
	    outs = new BufferedOutputStream(new FileOutputStream(orgFile));

	    while ((read = fin.read(b)) != -1) {
		outs.write(b, 0, read);
		outs.flush();
	    }

	    Kogger.debug(E3PSDRMFileDeleteScheduler.class, "orgFile : " + orgFile.length());
	} catch (Exception e) {
	    Kogger.error(E3PSDRMFileDeleteScheduler.class, e);
	    throw new WTException(e);
	} finally {
	    if (fin != null) {
		try {
		    fin.close();
		} catch (IOException e) {
		    Kogger.error(E3PSDRMFileDeleteScheduler.class, e);
		}
	    }

	    if (outs != null) {
		try {
		    outs.close();
		} catch (IOException e) {
		    Kogger.error(E3PSDRMFileDeleteScheduler.class, e);
		}
	    }
	}

	return download(orgFile, fileName, contentOid, clientIP);
    }


    public static File download(File file, String fileName, String contentOid, String clientIP) throws WTException {
	if (!isDrmApprove(fileName)) {
	    Kogger.debug(E3PSDRMFileDeleteScheduler.class, "not drm = " + fileName);
	    return file;
	}

//	long outFileLength = -1;
//
//	WTUser wtUser = null;
//	People people = null;
	File drmFile = null;
	try {
//	    wtUser = (WTUser) SessionHelper.manager.getPrincipal();
//	    QueryResult qr = PersistenceHelper.manager.navigate(wtUser, "people", WTUserPeopleLink.class);
//
//	    if (qr.hasMoreElements()) {
//		people = (People) qr.nextElement();
//	    }
//
//	    /*
//	     * DRM 적용 시 처리
//	     */
//	    String encryptedFileName = file.getAbsolutePath();	// 암호화 원본파일명-Full Path
//	    //			String pstrFileName = fileName; // 파일명
//	    //			String pstrFileName = URLEncoder.encode( fileName, "UTF8" ); // 파일명
//	    //			String pstrFileName = new String(fileName.getBytes("euc-kr"), "8859_1");
//	    //			String pstrFileName = wt.util.EncodingConverter.escape(fileName);
//	    String pstrFileName = fileName;
//
//	    if (VERBOSE) {
//		Kogger.debug(E3PSDRMFileDeleteScheduler.class, "########## E3PSDRMHelper Class : download Method : pstrFileName : " + pstrFileName + " ##########");
//	    }
//
//	    long plFileSize = file.length();	// 파일크기
//	    String pstrFileId = new String(contentOid);	// 파일ID
//
//	    int piAclFlag = 0;	// 권한설정 적용방법: 1-DB, 0-파라미터
//	    int piExchangePolicy = 1;	// 문서교환정책, 예) 1-사내한, 0-개인한 [#DB필드값#]ExchangePolicy
//	    int piDrmFlag = 0;	// 암호화 여부 : 0
//	    int piUnicodeFlag = 1;	// Unicode적용여부 0: ANSI / 1: Unicode
//
//	    int piDocLevel = 0;	// 문서권한코드 [#DB필드값#] DocGradeCode
//	    String pstrGrade = new String("1급비밀");	// 문서유형 [#DB필드값#] DocGradeName
//	    String pstrDocumentTitle = new String("");	// 문서등급권한레벨 [#DB필드값#]DocTitle
//
//	    String pstrUserId = wtUser.getName();	// 사용자ID
//	    String pstrUserName = wtUser.getFullName();	// 사용자 명
//	    String pstrOwnerId = wtUser.getName();	// 작성자ID
//	    String pstrGroupId = people.getDepartment().getCode();	// 그룹ID
//	    String pstrGroupName = people.getDepartment().getName();	// 그룹명
//	    String pstrPositionId = people.getDutyCode();	// 직위ID
//	    String pstrPositionName = people.getDuty();	// 직위명
//
//	    String pstrUserIp = clientIP;
//
//	    if (VERBOSE) {
//		Kogger.debug(E3PSDRMFileDeleteScheduler.class, "########## E3PSDRMHelper Class : download Method : pstrUserIP : " + pstrUserIp + " ##########");
//	    }
//
//	    String pstrCompanyId = new String("KET-7892-7F69-8365");	// 회사ID
//	    String pstrCompanyName = new String("한국단자공업");	// 회사 명
//	    String pstrServerAddr = "192.168.1.123:40002";	// DRM로그서버IP:port
//	    String pstrServerOrigin = new String("PLM");	// 시스템 구분
//
//	    int piCanSave = 1;	// 저장가능 [#DB필드값#]CanSave
//	    int piCanEdit = 1;	// 편집가능 [#DB필드값#]CanEdit
//	    int piBlockCopy = 0;	// 클립보드지원여부[#DB필드값#]CanBlockCopy
//	    // -99를 값으로 주면 제한없음을 의미한다
//	    int piOpenCount = -99;	// 열람제한회수 [#DB필드값#]OpenCount
//	    int piPrintCount = 3;	// 인쇄제한회수 [#DB필드값#]PrintCount
//	    int piValidPeriod = 90;	// 문서사용기간 [#DB필드값#]ValidPeriod
//	    int piSaveLog = 1;	// 저장로그 [#DB필드값#]SaveLog
//	    int piPrintLog = 1;	// 프린트로그 [#DB필드값#]PrintLog
//	    int piOpenLog = 1;	// 오픈로그 [#DB필드값#]OpenLog
//	    int piVisualPrint = 1;	// 워터마킹 [#DB필드값#]PrintSafer
//	    int piImageSafer = 1;	// 이미지세이퍼 [#DB필드값#]ImageSafer
//	    int piRealTimeAcl = 0;	// 실시간권한제어
//
//	    //DRM 20130409 add
//
//	    JNICipher jniCipher = new JNICipher();
//	    //m_strEnterpriseID
//	    jniCipher.m_strEnterpriseID = "KETG-72C3-EACB-9B59";
//
//	    jniCipher.m_nEncryptPrevCipher = 0;		// 이전 사이퍼 암호화 여부 : 1-이전, 0-업그레이드 사이퍼
//
//	    outFileLength = jniCipher.EncryptFileInPlace(
//		    encryptedFileName
//		    //outFileLength = JNICipher.EncryptFileInPlace( encryptedFileName
//		    , piAclFlag, piDocLevel, pstrUserId, pstrFileName, plFileSize, pstrOwnerId, pstrCompanyId, pstrGroupId, pstrPositionId, pstrGrade, pstrFileId, piCanSave, piCanEdit, piBlockCopy,
//		    piOpenCount, piPrintCount, piValidPeriod, piSaveLog, piPrintLog, piOpenLog, piVisualPrint, piImageSafer, piRealTimeAcl, pstrServerAddr, pstrDocumentTitle, pstrCompanyName,
//		    pstrGroupName, pstrPositionName, pstrUserName, pstrUserIp, pstrServerOrigin, piExchangePolicy, piDrmFlag, piUnicodeFlag);

	    //			0	: 암호화 성공
	    //			1	: 암호화 실패 오류
	    //			2	: 암호화 함수 미 제공(masdmsjni.dll)
	    //			3	: 라이브러리 제어 오류(masdmsjni.dll)
	    //			1001	: 파일명 오류
	    //			1002	: 사용자ID 오류
	    
	    drmFile = DRMHelper.encryptDownLoad(file, "", clientIP, "", "");
	    
	}
	//		catch( UnsupportedEncodingException e ) // 오류
	//		{
	//			Kogger.error(E3PSDRMFileDeleteScheduler.class, e);
	//			throw new WTException(e);
	//		}
	
	catch (Exception e) {
	    Kogger.error(E3PSDRMFileDeleteScheduler.class, e);
	    throw new WTException(e);
	}
	
	

	return drmFile;
    }

    public static File downloadExcel(File file, String fileName, String contentOid, String clientIP) throws WTException {
	if (!isDRM) {
	    Kogger.debug(E3PSDRMFileDeleteScheduler.class, "not drm = " + fileName);
	    return file;
	}

	return download(file, fileName, contentOid, clientIP);
    }

    public static File upload(InputStream inputStream, String fileName, long fileLength) {
	BufferedInputStream in = null;
	BufferedOutputStream out = null;
	int outFileLength = -1;

//	JNICipher jniCipher = new JNICipher();

	File fileOut = null;
	File DecryptFile = null;
	try {
	    /*
	     * DRM 적용 시 처리
	     */
	    in = new BufferedInputStream(inputStream);
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
	    
	    

	    // 업로드할 파일이 암호화 되어 있는 파일인지 확인
	    // 리턴 값 0 : 일반파일, 1 : 암호화된파일
//	    int nRet = jniCipher.IsEncrypted(fileOut.getAbsolutePath());

//	    if (nRet == 1) {
//		outFileLength = jniCipher.DecryptFileInPlace(fileOut.getAbsolutePath());
//	    }

	    //	0 : 복호화 성공
	    //	1 : 복호화 실패
	    //	2, 3 : 라이브러리 관련 오류(masdmsjni.dll)
	    
	    
	    
	    DecryptFile = DRMHelper.Decryptupload(fileOut, fileName);
	    
	    
	} catch (Exception e) {
	    Kogger.error(E3PSDRMFileDeleteScheduler.class, e);
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

	return DecryptFile;
    }

    public static File upload(File file, String fileName) throws WTException {
	if (!isDrmApprove(fileName)) {
	    Kogger.debug(E3PSDRMFileDeleteScheduler.class, "not drm = " + fileName);
	    return file;
	}

	InputStream in = null;
	long fileLength = 0;
	File fileOut = null;

	try {
	    in = new FileInputStream(file);
	    fileLength = file.length();
	    fileOut = upload(in, fileName, fileLength);
	} catch (Exception e) {
	    Kogger.error(E3PSDRMFileDeleteScheduler.class, e);
	    throw new WTException(e);
	} finally {
	    try {
		if (in != null) {
		    in.close();
		}
	    } catch (Exception e) {
		Kogger.error(E3PSDRMFileDeleteScheduler.class, e);
	    }
	}

	return fileOut;
    }

    public static String getActiveWorkspaceName(HTTPRequest httprequest, boolean flag) {
	String sCookie = null;
	boolean flag1 = false;
	String s1 = null;

	if (httprequest == null) return null;

	sCookie = httprequest.getProperty("cgi.http_cookie");

	if (sCookie == null) return null;

	StringTokenizer st1 = new StringTokenizer(sCookie, ";");

	try {
	    do {
		if (flag1 || !st1.hasMoreTokens()) break;

		String s2 = st1.nextToken().trim();
		StringTokenizer st2 = new StringTokenizer(s2, "=");

		if (st2.hasMoreTokens()) {
		    String s3 = st2.nextToken().trim();

		    if (s3.compareToIgnoreCase("wf-workspace") == 0) {
			flag1 = true;
			s1 = st2.nextToken();

			if (flag) s1 = (new EncodingConverter()).decode(s1);
		    }
		}
	    } while (true);
	} catch (Exception ex) {
	    Kogger.error(E3PSDRMFileDeleteScheduler.class, ex);
	}

	return s1;
    }

    public static void drmInit() {
	if (VERBOSE) {
	    Kogger.debug(E3PSDRMFileDeleteScheduler.class, "DRMHelper initialize.......");
	}

	String separator = File.separator;

	try {
	    wtTempFolder = WTProperties.getLocalProperties().getProperty("wt.temp");
	    downloadFileTempFolder = wtTempFolder + separator + "drm" + separator + "DownloadTemp";
	    uploadFileTempFolder = wtTempFolder + separator + "drm" + separator + "UploadTemp";

	    if (VERBOSE) {
		Kogger.debug(E3PSDRMFileDeleteScheduler.class, "orgFileTempFolder : " + downloadFileTempFolder);
		Kogger.debug(E3PSDRMFileDeleteScheduler.class, "destFileTempFolder : " + uploadFileTempFolder);
	    }

	    File downloadFolder = new File(downloadFileTempFolder);
	    File uploadFolder = new File(uploadFileTempFolder);

	    if (!downloadFolder.isDirectory()) {
		downloadFolder.mkdirs();

		if (VERBOSE) {
		    Kogger.debug(E3PSDRMFileDeleteScheduler.class, "Download File Temp Folder Created : " + downloadFolder.getAbsolutePath());
		}
	    }

	    if (!uploadFolder.isDirectory()) {
		uploadFolder.mkdirs();

		if (VERBOSE) {
		    Kogger.debug(E3PSDRMFileDeleteScheduler.class, "Upload File Temp Folder Created : " + uploadFolder.getAbsolutePath());
		}
	    }
	} catch (Exception e) {
	    Kogger.error(E3PSDRMFileDeleteScheduler.class, e);
	}
    }


    public static String getFileExtension(String fileName) {
	String extStr = "";

	int idx = -1;
	idx = fileName.lastIndexOf('.');

	if (idx < 0) {
	    Kogger.debug(E3PSDRMFileDeleteScheduler.class, "###Error : Filename was wrong.###");
	}
	else {
	    extStr = fileName.substring(idx + 1);
	}

	return extStr;
    }

    public static String getFileNameWithoutExt(String fileName) {
	String returnStr = new String();
	int idx = -1;
	idx = fileName.lastIndexOf('.');

	if (idx < 0) {
	    Kogger.debug(E3PSDRMFileDeleteScheduler.class, "###Error : Filename was wrong.###");
	}
	else {
	    returnStr = fileName.substring(0, idx);
	}

	return returnStr;
    }

    private static String getTempDownloadFileName(String fileName) {
	Random randomNumber = new Random();
	StringBuffer stringbuffer = new StringBuffer();
	stringbuffer.append(getFileNameWithoutExt(fileName)).append("_");
	stringbuffer.append(Math.abs(randomNumber.nextInt())).append("_Encrypt.").append(getFileExtension(fileName));

	if (VERBOSE) {
	    Kogger.debug(E3PSDRMFileDeleteScheduler.class, "Encrypt TempFile Name : " + stringbuffer.toString());
	}

	return stringbuffer.toString();
    }

    public static String getTempUploadFileName(String fileName) {
	Random randomNumber = new Random();
	StringBuffer stringbuffer = new StringBuffer();
	stringbuffer.append(getFileNameWithoutExt(fileName)).append("_");
	stringbuffer.append(Math.abs(randomNumber.nextInt())).append("_Decrypt.").append(getFileExtension(fileName));

	if (VERBOSE) {
	    Kogger.debug(E3PSDRMFileDeleteScheduler.class, "Encrypt TempFile Name : " + stringbuffer.toString());
	}

	return stringbuffer.toString();
    }

    public static void main(String args[]) throws Exception {

    }
}
