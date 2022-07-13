package e3ps.common.editor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import wt.method.RemoteAccess;
import wt.util.WTException;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import e3ps.common.jdf.config.ConfigImpl;
import ext.ket.shared.log.Kogger;

/**
 * 
 * Innoditor Web Editor Image File Upload Util
 * 
 * @author OH, MYUNGJAE
 * 
 * @version 1.0, 2013-05-27
 * 
 */

public class CommonFileUploader implements java.io.Serializable, RemoteAccess {

    private static final long serialVersionUID = -7277643209320587027L;

    private static final String UPLOAD_BASE_PATH = ConfigImpl.getInstance().getString("editor.upload.base.path");
    private static final String UPLOAD_SIZE_LIMIT = ConfigImpl.getInstance().getString("editor.upload.size.limit");
    private static final String UPLOAD_FOLDER_MODE = ConfigImpl.getInstance().getString("editor.upload.folder.mode");
    private static final String DOWNLOAD_BASE_URL = ConfigImpl.getInstance().getString("editor.download.base.url");

    private static String uploadFolderPath = "";
    private static String serverBaseUrl = "http://";

    static {

	initUpload();
    }

    /**
     * Constructor<br>
     * 
     * @param
     * @return
     * @throws
     **/
    public static void initUpload() {

	try {

	    String year = (new SimpleDateFormat("yyyy")).format(new Date());
	    String month = (new SimpleDateFormat("MM")).format(new Date());
	    String day = (new SimpleDateFormat("dd")).format(new Date());
	    File uploadFolder = null;

	    serverBaseUrl += DOWNLOAD_BASE_URL + "/editorFiles/";

	    if (UPLOAD_FOLDER_MODE.equalsIgnoreCase("daily")) {

		uploadFolder = new File(UPLOAD_BASE_PATH + File.separator + year + File.separator + month + File.separator + day);
		serverBaseUrl += year + "/" + month + "/" + day + "/";
	    } else {

		uploadFolder = new File(UPLOAD_BASE_PATH + File.separator + year + File.separator + month);
		serverBaseUrl += year + "/" + month + "/";
	    }

	    if (!uploadFolder.isDirectory()) {

		uploadFolder.mkdirs();
	    }

	    uploadFolderPath = uploadFolder.getAbsolutePath();
	} catch (Exception e) {
	    Kogger.error(CommonFileUploader.class, e);
	}
    }

    /**
     * Mulipart Form File Upload<br>
     * 
     * @param HttpServletRequest
     * @return String
     * @throws WTException
     **/
    public static String upload(HttpServletRequest req) throws WTException {

	String uploadedFileUrl = "";
	MultipartRequest mReq = null;
	DefaultFileRenamePolicy dfrp = new DefaultFileRenamePolicy();

	try {
	    mReq = new MultipartRequest(req, uploadFolderPath, Integer.parseInt(UPLOAD_SIZE_LIMIT), "UTF-8", dfrp);
	    @SuppressWarnings("rawtypes")
	    Enumeration fileEnum = mReq.getFileNames();

	    File orgFile = mReq.getFile((String) fileEnum.nextElement());
	    // 확장자추출
	    String ext = getFileExtension(orgFile.getName());

	    File newFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), UUID.randomUUID().toString() + "." + ext, new File(uploadFolderPath));
	    // File 이동
	    fileMove(orgFile, newFile);

	    uploadedFileUrl = serverBaseUrl + newFile.getName();
	} catch (Exception e) {
	    Kogger.error(CommonFileUploader.class, e);
	    throw new WTException(e);
	}

	return uploadedFileUrl;
    }

    public static boolean fileMove(File orgFile, File newFile) {
	boolean retValue = false;

	// Stream, Channel 선언
	FileInputStream fis = null;
	FileOutputStream fos = null;
	FileChannel fcin = null;
	FileChannel fcout = null;

	try {
	    // Stream 생성
	    fis = new FileInputStream(orgFile);
	    fos = new FileOutputStream(newFile);
	    // Channel 생성
	    fcin = fis.getChannel();
	    fcout = fos.getChannel();

	    // 채널을 통한 스트림 전송
	    long size = fcin.size();
	    fcin.transferTo(0, size, fcout);
	} catch (Exception e) {
	    Kogger.error(CommonFileUploader.class, e);
	} finally {
	    // 자원 해제
	    try {
		fcout.close();
	    } catch (IOException ioe) {
	    }
	    try {
		fcin.close();
	    } catch (IOException ioe) {
	    }
	    try {
		fos.close();
	    } catch (IOException ioe) {
	    }
	    try {
		fis.close();

		// 파일 이동후 원본 파일은 삭제처리
		orgFile.delete();
	    } catch (IOException ioe) {
	    }
	}
	return retValue;
    }

    /**
     * General Form File Upload<br>
     * 
     * @param File
     * @param String
     * @return String
     * @throws WTException
     **/
    public static String upload(File file, String fileName) throws WTException {
	String uploadFileUrl = "";
	InputStream in = null;
	long fileLength = 0;
	File fileOut = null;
	String serverBaseUrl = "http://";

	try {
	    serverBaseUrl += DOWNLOAD_BASE_URL + "/editorFiles/";
	    in = new FileInputStream(file);
	    fileLength = file.length();
	    fileOut = upload(in, fileName, fileLength);

	    uploadFileUrl = serverBaseUrl + fileOut.getName();

	} catch (Exception e) {
	    Kogger.error(CommonFileUploader.class, e);
	    throw new WTException(e);
	} finally {
	    try {
		if (in != null) {
		    in.close();
		}
	    } catch (Exception e) {
		Kogger.error(CommonFileUploader.class, e);
	    }
	}

	return uploadFileUrl;
    }

    /**
     * General Form File Upload<br>
     * 
     * @param InputStream
     * @param String
     * @param long
     * @return File
     * @throws
     **/
    public static File upload(InputStream inputStream, String fileName, long fileLength) {
	BufferedInputStream in = null;
	BufferedOutputStream out = null;

	File fileOut = null;

	try {
	    in = new BufferedInputStream(inputStream);
	    fileOut = new File(uploadFolderPath + File.separator + getUploadFileName(fileName));

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
	} catch (Exception e) {
	    Kogger.error(CommonFileUploader.class, e);
	} finally {
	    try {
		if (in != null) {
		    in.close();
		}

		if (out != null) {
		    out.close();
		}
	    } catch (Exception e) {
		Kogger.error(CommonFileUploader.class, e);
	    }
	}

	return fileOut;
    }

    /**
     * Get File Extension String<br>
     * 
     * @param String
     * @return String
     * @throws
     **/
    public static String getFileExtension(String fileName) {
	String extStr = "";

	int idx = -1;
	idx = fileName.lastIndexOf('.');

	if (idx < 0) {
	    Kogger.debug(CommonFileUploader.class, "###Error : Filename was wrong.###");
	} else {
	    extStr = fileName.substring(idx + 1);
	}

	return extStr;
    }

    /**
     * Get File Name String Without Extension<br>
     * 
     * @param String
     * @return String
     * @throws
     **/
    public static String getFileNameWithoutExt(String fileName) {
	String returnStr = new String();
	int idx = -1;
	idx = fileName.lastIndexOf('.');

	if (idx < 0) {
	    Kogger.debug(CommonFileUploader.class, "###Error : Filename was wrong.###");
	} else {
	    returnStr = fileName.substring(0, idx);
	}

	return returnStr;
    }

    /**
     * Get Uploaded File Name String With Extension<br>
     * 
     * @param String
     * @return String
     * @throws
     **/
    public static String getUploadFileName(String fileName) {
	Random randomNumber = new Random();
	StringBuffer stringbuffer = new StringBuffer();
	// stringbuffer.append( getFileNameWithoutExt(fileName) ).append( "_" );
	stringbuffer.append(Math.abs(randomNumber.nextInt())).append(".").append(getFileExtension(fileName));

	return stringbuffer.toString();
    }
}
