package ext.ket.edm.stamping.util;

import java.beans.PropertyVetoException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Vector;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.util.WTException;
import ext.ket.shared.log.Kogger;

public class CadFileInfoUtil {

    private Vector resultVector = null;

    private void execute3DFileInfo(EPMDocument epm3d, boolean withFileByteArray) throws Exception {
	boolean is3D = true;
	getFileInfoWith3D2D(epm3d, is3D, !is3D, withFileByteArray);
    }

    private void execute2DFileInfo(EPMDocument epm2d, boolean withFileByteArray) throws Exception {
	boolean is2D = true;
	getFileInfoWith3D2D(epm2d, !is2D, is2D, withFileByteArray);
    }

    /**
     * 2D, 3D로 부터 파일 정보를 추출
     * 
     * @param epmDoc
     * @param is3D
     * @param is2D
     * @param isOnlyDxf
     * @throws WTException
     */
    private void getFileInfoWith3D2D(EPMDocument epmDoc, boolean is3D, boolean is2D, boolean withFileByteArray) throws Exception {

	if (resultVector == null) // for adding continue;
	    resultVector = new Vector();

	try {

	    if (epmDoc != null) {
		ContentHolder contentHolder = ContentHelper.service.getContents(epmDoc);
		Vector contentList = ContentHelper.getContentListAll(contentHolder);
		Kogger.debug(getClass(), "@@@ ContentHelper.service.getContents(epm)");
		String fileName = null;

		for (int i = 0; i < contentList.size(); i++) {

		    ContentItem contentItem = (ContentItem) contentList.elementAt(i);
		    Kogger.debug(getClass(), i + " contentItem = " + contentItem);

		    if (contentItem instanceof ApplicationData) {
			fileName = ((ApplicationData) contentItem).getFileName();
			if (fileName.equals("{$CAD_NAME}")) {
			    // ((ApplicationData) contentItem).setFileName(epmDoc.getCADName());
			    fileName = epmDoc.getCADName();
			}

			Kogger.debug(getClass(), "fileName = " + fileName);

			if (is3D) {
			    if (fileName.endsWith(".asm") || fileName.endsWith(".prt")) {
			    } else
				continue;
			} else if (is2D) {
			    if (fileName.endsWith(".drw") || fileName.endsWith(".prt")) {
			    } else
				continue;
			}

			HashMap fileInfo = getFileInfo(fileName, epmDoc, (ApplicationData) contentItem, withFileByteArray);

			if (is3D) {
			    resultVector.addElement(fileInfo);
			} else if (is2D) {
			    resultVector.addElement(fileInfo);
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
     * @param epmDoc
     * @param appData
     * @param derivedImg
     * @return
     * @throws WTException
     */
    private HashMap getFileInfo(String fileName, EPMDocument epmDoc, ApplicationData appData, boolean withFileByteArray) throws Exception {

	HashMap fileInfo = new HashMap();

	if (epmDoc != null) {

	    ContentRoleType crt = appData.getRole();
	    String temp = crt.getStringValue();
	    Kogger.debug(getClass(), "temp = " + temp);

	    String fileType = (String) appData.getRole().toString();
	    Kogger.debug(getClass(), "Display = " + fileType);

	    String fileId = PersistenceHelper.getObjectIdentifier(appData).toString();
	    Kogger.debug(getClass(), "File ID = " + fileId);

	    String downURL = (ContentHelper.getDownloadURL((ContentHolder) epmDoc, appData)).toString();

	    Kogger.debug(getClass(), "Content URL = " + downURL);

	    String fileSize = String.valueOf(((ApplicationData) appData).getFileSizeKB());
	    Kogger.debug(getClass(), "fileSize = " + fileSize);

	    fileInfo.put("fileName", fileName);
	    fileInfo.put("roleType", temp);
	    fileInfo.put("fileType", fileType);
	    fileInfo.put("fileId", fileId);
	    fileInfo.put("downURL", downURL);
	    fileInfo.put("fileSize", fileSize);
	    fileInfo.put("epmInfo", epmDoc.getNumber() + "." + epmDoc.getVersionIdentifier().getValue() + "." + epmDoc.getIterationIdentifier().getValue());

	    if (withFileByteArray) {

		InputStream inputstream = null;
		ByteArrayOutputStream fos = new ByteArrayOutputStream();

		try {
		    inputstream = ContentServerHelper.service.findContentStream(appData);
		    if (true) {
			int j = 0;
			byte abyte0[] = new byte[1024];
			while ((j = inputstream.read(abyte0, 0, abyte0.length)) >= 0) {
			    fos.write(abyte0, 0, j);
			}

			fos.flush();
			fos.close();
		    }

		    byte[] fileByteArray = fos.toByteArray();
		    fileInfo.put("fileByteArray", fileByteArray);

		    inputstream.close();

		} catch (Exception e) {
		    throw e;
		}
	    }

	}

	return fileInfo;
    }

}
