package ext.ket.common.files.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import wt.content.ApplicationData;
import wt.content.ContentHolder;
import wt.content.ContentServerHelper;
import wt.fc.PersistenceHelper;
import wt.org.WTUser;
import wt.session.SessionHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.PeopleData;
import ext.ket.common.KETHistoryMaster;
import ext.ket.edm.stamping.util.ZipUtil;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.drm.DRMHelper;

@Component
public class FileContentUtil {

    public static final FileContentUtil manager = new FileContentUtil();

    public KETHistoryMaster saveDownloadHistory(String historyType, String targetContent) throws Exception {

	WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	WTUser admin = (WTUser) SessionHelper.manager.getAdministrator();
	if (user.getName().equals(admin.getName())) {
	    return null;
	}
	return saveDownloadHistory(historyType, targetContent, user);

    }

    public KETHistoryMaster saveDownloadHistory(String historyType, String targetContent, WTUser user) throws Exception {

	PeopleData pd = new PeopleData(user);

	String targetId = pd.id;
	String targetName = pd.name;
	String deptcode = pd.department != null ? pd.department.getCode() : "";
	String deptName = pd.departmentName;
	String deptManagerId = "";
	String deptManagerName = "";

	WTUser chief = DepartmentHelper.manager.getDepartChief(pd.department);

	if (chief != null) {
	    deptManagerId = chief.getName();
	    deptManagerName = chief.getFullName();
	}

	KETHistoryMaster history = KETHistoryMaster.newKETHistoryMaster();

	history.setHistoryType(historyType);
	history.setTargetContent(targetContent);
	history.setTargetId(targetId);
	history.setTargetName(targetName);
	history.setDeptcode(deptcode);
	history.setDeptName(deptName);
	history.setDeptManagerId(deptManagerId);
	history.setDeptManagerName(deptManagerName);
	history.setHistoryDate(DateUtil.getCurrentTimestamp());

	history = (KETHistoryMaster) PersistenceHelper.manager.save(history);

	return history;
    }

    public static boolean validateFileName(String fileName) {
	return fileName.matches("^[^.\\\\/:*?\"<>|]?[^\\\\/:*?\"<>|]*") && getValidFileName(fileName).length() > 0;
    }

    public static String getValidFileName(String fileName) {
	String newFileName = fileName.replace("^\\.+", "").replaceAll("[\\\\/:*?\"<>|]", "");
	if (newFileName.length() == 0)
	    throw new IllegalStateException("File Name " + fileName + " results in a empty fileName!");
	return newFileName;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 7. 9. 오전 11:25:56
     * @method getEntitySecondaryFilesZip
     * @param holder
     * @param zipName
     * @return String
     * @throws Exception
     * </pre>
     */
    public String getEntitySecondaryFilesZip(ContentHolder holder, String zipName, boolean isDecrypt) throws Exception {

	String downloadUrl = null;

	String zipDir = DownloadView.TEMPPATH + File.separator + zipName + "_ZIP_" + DateUtil.getCurrentDateString("d");
	File tempDir = new File(zipDir);
	tempDir.mkdir();

	List<ContentDTO> secondarys = KETContentHelper.manager.getSecondaryContents(holder);

	if (secondarys.size() > 0) {

	    for (ContentDTO secondary : secondarys) {

		if ("FILE".equals(secondary.getType())) {
		    ApplicationData appData = (ApplicationData) CommonUtil.getObject(secondary.getContentoid());

		    // InputStream is = ContentServerHelper.service.findLocalContentStream(appData);

		    InputStream is = null;

		    // 복호화
		    if (isDecrypt) {
			is = ContentServerHelper.service.findLocalContentStream(appData);
			is = DRMHelper.decryptFile(holder, appData, is);
		    } else if (!isDecrypt && DRMHelper.useDrm) {
			File appFile = ContentServerHelper.service.getStoredItemFile(appData);
			appFile = DRMHelper.encryptFile(appFile, appData.getFileName());
			is = new FileInputStream(appFile);
		    }

		    File file = new File(zipDir + File.separator + appData.getFileName());
		    // FileUtils.copyInputStreamToFile(is, file);

		    FileOutputStream fos = new FileOutputStream(file);
		    IOUtils.copy(is, fos);
		    fos.flush();
		    fos.close();
		    is.close();
		}
	    }

	    zipName += ".zip";
	    ZipUtil.zip(zipDir, DownloadView.TEMPPATH + File.separator + zipName);
	    downloadUrl = "/plm/ext/download?path=/TEMP/" + zipName;
	}

	return downloadUrl;
    }

    @Scheduled(cron = "0 0 1 * * *")
    public static void deleteTempFileFolder() throws Exception {

	File tempDir = new File(DownloadView.TEMPPATH + File.separator);
	File[] files = tempDir.listFiles();

	for (File file : files) {
	    if (file.isDirectory()) {
		FileUtils.cleanDirectory(file);
	    }
	    file.delete();
	}
    }
}
