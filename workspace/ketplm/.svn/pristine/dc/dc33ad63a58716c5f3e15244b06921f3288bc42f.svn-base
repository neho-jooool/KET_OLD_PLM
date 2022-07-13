package ext.ket.edm.stamping.util;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Vector;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.pom.DBProperties;
import ext.ket.shared.log.Kogger;

public class DocumentFileDownUtil {

    // 주파일만 대상임.
    private boolean isTest = false;
    
    public void executeDocFileInfo(WTDocument wtDoc, String filePath) throws Exception {
	getFileInfoWithDoc(wtDoc, filePath);
    }

    /**
     * 문서로 부터 파일 정보를 추출
     * 
     * @param wtDoc
     * @param is3D
     * @param is2D
     * @param isOnlyDxf
     * @throws Exception
     */
    private void getFileInfoWithDoc(WTDocument wtDoc, String filePath) throws Exception {

	try {

	    if (wtDoc != null) {

		ContentHolder contentHolder = ContentHelper.service.getContents(wtDoc);
		Vector contentList = ContentHelper.getContentListAll(contentHolder);
		Kogger.debug(getClass(), "@@@ ContentHelper.service.getContents(wtDoc)");
		String fileName = null;

		for (int i = 0; i < contentList.size(); i++) {

		    ContentItem contentItem = (ContentItem) contentList.elementAt(i);
		    Kogger.debug(getClass(), i + " contentItem = " + contentItem);

		    ContentRoleType type = contentItem.getRole();
		    if (ContentRoleType.PRIMARY != type){
			continue;
		    }

		    if (contentItem instanceof ApplicationData) {

			fileName = ((ApplicationData) contentItem).getFileName();
			Kogger.debug(getClass(), "fileName = " + fileName);
			
			if(!isTest){
			    HashMap fileInfo = getFileInfo(fileName, wtDoc, (ApplicationData) contentItem, filePath);
			}

		    } else {
			Kogger.debug(getClass(), fileName);
		    }
		}
	    }

	} catch (PropertyVetoException pve) {
	    throw pve;
	}
    }

    /**
     * 파일 정보 추출
     * 
     * @param fileName
     * @param wtDoc
     * @param appData
     * @param derivedImg
     * @return
     * @throws Exception
     */
    private HashMap getFileInfo(String fileName, WTDocument wtDoc, ApplicationData appData, String filePath) throws Exception {

	HashMap fileInfo = new HashMap();

	try {

	    if (wtDoc != null) {

		ContentRoleType crt = appData.getRole();
		String temp = crt.getStringValue();
		Kogger.debug(getClass(), "temp = " + temp);

		String fileType = (String) appData.getRole().toString();
		Kogger.debug(getClass(), "Display = " + fileType);

		String fileId = PersistenceHelper.getObjectIdentifier(appData).toString();
		Kogger.debug(getClass(), "File ID = " + fileId);

		String downURL = (ContentHelper.getDownloadURL((ContentHolder) wtDoc, appData)).toString();

		Kogger.debug(getClass(), "Content URL = " + downURL);

		String fileSize = String.valueOf(((ApplicationData) appData).getFileSizeKB());
		Kogger.debug(getClass(), "fileSize = " + fileSize);

		fileInfo.put("fileName", fileName);
		fileInfo.put("roleType", temp);
		fileInfo.put("fileType", fileType);
		fileInfo.put("fileId", fileId);
		fileInfo.put("downURL", downURL);
		fileInfo.put("fileSize", fileSize);
		fileInfo.put("epmInfo", wtDoc.getNumber() + "." + wtDoc.getVersionIdentifier().getValue() + "." + wtDoc.getIterationIdentifier().getValue());

		if (filePath != null) {

		    InputStream inputstream = null;

		    try {
			inputstream = ContentServerHelper.service.findContentStream(appData);
		    } catch (Exception fvsie) {
			Kogger.error(getClass(), fvsie);
			throw fvsie;
		    }

		    File outputFile = new File(filePath + (filePath.endsWith(File.separator) ? "" : File.separator) + fileName);
		    if (outputFile.isDirectory()) {// Directory 인 경우
			FileUtil.checkDir(outputFile.getAbsolutePath()); // 디렉토리 생성
		    } else {
			// File 인 경우
			// parent Directory 생성
			FileUtil.checkDir(outputFile.getParent());
		    }
		    FileOutputStream fo = new FileOutputStream(outputFile);

		    try {

			int j = 0;
			byte abyte0[] = new byte[(int) DBProperties.LOB_CHUNK_SIZE];
			while ((j = inputstream.read(abyte0, 0, abyte0.length)) >= 0) {
			    fo.write(abyte0, 0, j);
			}

			fo.flush();
			fo.close();
			inputstream.close();

		    } catch (IOException e) {
			Kogger.error(getClass(), e);
			throw e;
		    }
		}
	    }

	} catch (Exception e) {
	    throw e;
	}

	return fileInfo;
    }

}
