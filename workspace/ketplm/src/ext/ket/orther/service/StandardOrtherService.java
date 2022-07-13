package ext.ket.orther.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.dms.entity.KETProjectDocument;
import ext.ket.common.files.util.DownloadView;
import ext.ket.common.files.util.FileRenamePolicy;
import ext.ket.edm.stamping.util.ZipUtil;
import ext.ket.orther.entity.dto.OrtherDto;
import ext.ket.orther.service.internal.OrtherDao;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.entity.UploadedFile;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.drm.DRMHelper;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.services.StandardManager;
import wt.util.WTException;

public class StandardOrtherService extends StandardManager implements OrtherService {
    private static final long serialVersionUID = 1L;
    public static StandardOrtherService newStandardOrtherService() throws WTException {
	StandardOrtherService instance = new StandardOrtherService();
	instance.initialize();
	return instance;
    }
    
    @Override
    public OrtherDto getDividendInfoByEmpNo(OrtherDto paramObject) throws Exception {
	// TODO Auto-generated method stub
	Connection con = PlmDBUtil.getConnection();
	OrtherDao dao = new OrtherDao(con);
	paramObject = dao.getDividendInfo(paramObject);
	
	return paramObject;
    }

    @Override
    public void dividendSave(OrtherDto paramObject) throws Exception {
	// TODO Auto-generated method stub
	Connection con = PlmDBUtil.getConnection();
	OrtherDao dao = new OrtherDao(con);
	dao.dividendSave(paramObject);
    }

    @Override
    public void updateConvertFiles2PLM(JSONObject paramObj) throws Exception {
	// TODO Auto-generated method stub
	List<UploadedFile> uploadedFiles = new ArrayList<UploadedFile>();
	String startFilePath = "D:\\KET_DOCUMENT\\ipdftest";
	String requstNumber = "reqtest001_";
	String convertDirPath = "D:\\KET_DOCUMENT\\ipdftest";
	File[] fileArray = findFileByPattern(startFilePath, requstNumber, ".png");
	addAllUploadFile(uploadedFiles, fileArray, convertDirPath, "\\", requstNumber);
	String oid = "e3ps.dms.entity.KETProjectDocument:994039822";
	KETProjectDocument doc = (KETProjectDocument)CommonUtil.getObject(oid);
	
	List<ContentItem> contentItems = new ArrayList<ContentItem>();
	
	ContentHolder holder = doc;
	
	holder = ContentHelper.service.getContents(holder);
	@SuppressWarnings("unchecked")
	Vector<ContentItem> result = ContentHelper.getContentListAll(holder);
	Iterator<ContentItem> iter = result.iterator();
	while (iter.hasNext()) {
	    ContentItem item = iter.next();
	    contentItems.add(item);
	}
	
	int resultFileCount = 0;
	// 전체 stamping 관련 파일 수를 count 한다.
	if (uploadedFiles != null) {
	    resultFileCount = resultFileCount + uploadedFiles.size();
	}

	// 개별 파일 저장
	KETContentHelper.service.updateContent(holder, uploadedFiles, contentItems);
	
    }
    
    
    // 특정 Directory의 파일을 찾아오기
    private File[] findFileByPattern(String dirPath, String prefix, String suffix) throws Exception {

	final String _prefix = prefix.toLowerCase();
	final String _suffix = suffix.toLowerCase();
	File rootDir = new File(dirPath);

	File[] matchingFiles = rootDir.listFiles(new FilenameFilter() {
	    public boolean accept(File dir, String name) {
		return name.toLowerCase().startsWith(_prefix) && name.toLowerCase().endsWith(_suffix);
	    }
	});

	return matchingFiles;
    }
    
    // 특정 Directory의 파일을 찾아오기
    private File[] findFileByPattern(String dirPath, String prefix) throws Exception {

	final String _prefix = prefix.toLowerCase();
	File rootDir = new File(dirPath);
	final String allowPattern = ".+\\.(igs|jpg|pdf|stp|tif|bmp|esp)$";
	File[] matchingFiles = rootDir.listFiles(new FilenameFilter() {
	    public boolean accept(File dir, String name) {
		Pattern p = Pattern.compile(allowPattern);
		Matcher m = p.matcher(name);
		return name.toLowerCase().startsWith(_prefix) && m.matches();
	    }
	});

	return matchingFiles;
    }

    // 파일을 추가하면서 특정 위치에 파일 복사
    private void addAllUploadFile(List<UploadedFile> uploadFileList, File[] fileArray, String collectAllFoldPath, String fileSeper, String drawingNumber) throws Exception {
	if (fileArray == null)
	    return;

	for (File srcFile : fileArray) {
	    //FileUtils.copyFile(srcFile, new File(collectAllFoldPath + fileSeper + drawingNumber + fileSeper + srcFile.getName()));
	    uploadFileList.add(getUploadedFile(srcFile));
	}
    }

    // 업로드 파일 객체로 변경
    private UploadedFile getUploadedFile(File file) throws Exception {

	UploadedFile uploadfile = new UploadedFile();

	String contentType = StringUtils.EMPTY;
	String roleType = ContentRoleType.SECONDARY.toString();
	String ext = FilenameUtils.getExtension(file.getName());
	InputStream inputStream = new FileInputStream(file);
	long size = file.length();
	String fileName = file.getName();

	uploadfile.setUploadFile(uploadfile, contentType, roleType, ext, inputStream, size, fileName);

	return uploadfile;
    }
    
    @Override
    public String decryptMarkAnyDrmFile(List<MultipartFile> fileList) throws Exception{
	
	File uploadFile = null;
	File drmFile = null;
	String fileName = "";
	String zipDir = "";
	boolean isZip = fileList.size() > 1;
	File tempDir = null;
	
	if(isZip){
	    zipDir = DownloadView.TEMPPATH + File.separator + "NEW_DRM_ZIP_" + DateUtil.getCurrentTimestamp().getTime();
	    tempDir = new File(zipDir);
	    tempDir.mkdir();
	}

	for (MultipartFile file : fileList) {
	    uploadFile = new File(DownloadView.TEMPPATH + File.separator + file.getOriginalFilename());
	    fileName = file.getOriginalFilename();
	    file.transferTo(uploadFile);

	    File targetFile = new File(zipDir + File.separator + fileName);

	    FileOutputStream fos = new FileOutputStream(targetFile);

	    // 구DRM(MarkAny) 복호화 후 신DRM 암호화
	    if (DRMHelper.useDrm) {
		if(DRMHelper.isMarkAnyDrm(uploadFile)){
		    drmFile = DRMHelper.MarkAnyDecryptupload(uploadFile, fileName);
		    drmFile = DRMHelper.encryptFile(drmFile, fileName);
		}else{
		    drmFile = uploadFile;    
		}
	    } else {
		drmFile = uploadFile;
	    }

	    FileInputStream is = new FileInputStream(drmFile);
	    IOUtils.copy(is, fos);

	    is.close();
	    fos.flush();
	    fos.close();
	    
	    if(isZip){
		uploadFile.delete();
	    }
	    
	}
	
	if(isZip){
	    String zipName = "DOCFILES_" + DateUtil.getCurrentTimestamp().getTime() + ".zip";
	    ZipUtil.zip(zipDir, DownloadView.TEMPPATH + File.separator + zipName);
	    fileName = zipName;
	    
	    while(tempDir.exists()) {
		File[] folder_list = tempDir.listFiles(); //파일리스트 얻어오기
				
		for (int j = 0; j < folder_list.length; j++) {
		    folder_list[j].delete(); //파일 삭제 
		}
				
		if(folder_list.length == 0 && tempDir.isDirectory()){ 
		    tempDir.delete(); //대상폴더 삭제
		}
            }
	}
	
	
	String downloadUrl = "/plm/ext/download?path=" +"temp" + File.separator + fileName;
	 
	return downloadUrl;
    }

}
