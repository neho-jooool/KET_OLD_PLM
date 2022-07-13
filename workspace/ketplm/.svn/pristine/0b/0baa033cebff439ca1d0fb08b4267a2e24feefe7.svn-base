package ext.ket.dms.officeDocConvert.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.FileUtil;
import e3ps.common.util.PlmDBUtil;
import ext.ket.dms.entity.KETSGDocument;
import ext.ket.dms.officeDocConvert.util.docFileDownLoadUtil;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.entity.UploadedFile;
import ext.ket.shared.content.service.KETContentHelper;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.util.WTProperties;

public class StandardOfficeDocConvertService extends StandardManager implements OfficeDocConvertService {
    private static final long serialVersionUID = 1L;
    private static final String foldDefaultPath = "X:\\ConvertFileDownload";
    private static final String convertServerFoldDefaultPath = "F:\\ConvertFileDownload";
    public static StandardOfficeDocConvertService newStandardOfficeDocConvertService() throws WTException {
	StandardOfficeDocConvertService instance = new StandardOfficeDocConvertService();
	instance.initialize();
	return instance;
    }
    

    @Override
    public void updateConvertFiles2PLM(JSONObject paramObj) throws Exception {
	// TODO Auto-generated method stub
	List<UploadedFile> uploadedFiles = new ArrayList<UploadedFile>();
	String startFilePath = (String)paramObj.get("convertDirPath");
	
	File[] fileArray = findFileByPattern(startFilePath);
	addAllUploadFile(uploadedFiles, fileArray);
	
	//String oid = "e3ps.dms.entity.KETProjectDocument:994039822";
	String oid = (String)paramObj.get("oid");
	
	
	List<ContentItem> contentItems = new ArrayList<ContentItem>();
	
	ContentHolder holder = (ContentHolder)CommonUtil.getObject(oid);
	
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
    private File[] findFileByPattern(String dirPath) throws Exception {

	File rootDir = new File(dirPath);
	final String allowPattern = ".+\\.(png|jpg)$";
	File[] matchingFiles = rootDir.listFiles(new FilenameFilter() {
	    public boolean accept(File dir, String name) {
		Pattern p = Pattern.compile(allowPattern);
		Matcher m = p.matcher(name);
		return m.matches();
	    }
	});

	return matchingFiles;
    }

    // 파일을 추가하면서 특정 위치에 파일 복사
    private void addAllUploadFile(List<UploadedFile> uploadFileList, File[] fileArray) throws Exception {
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
    
    
    public void contentItemDelete(ContentHolder holder) throws Exception {
	
	ArrayList<ContentDTO> secondaryContents = KETContentHelper.manager.getSecondaryContents(holder);
	if (secondaryContents != null && secondaryContents.size() > 0) {
	    for (ContentDTO dto : secondaryContents) {
		
		ApplicationData existApp = (ApplicationData) CommonUtil.getObject(dto.getContentoid());
		holder = KETContentHelper.service.delete(holder, existApp);
		
	    }
	}
    }



    @Override
    public void docConvertCall(String oid) throws Exception {
	// TODO Auto-generated method stub
	
	String year = null;
	String month = null;
	String day = null;

	FileUtil.checkDir(foldDefaultPath+"\\PROGRESS");
	FileUtil.checkDir(foldDefaultPath+"\\SUCCESS");
	FileUtil.checkDir(foldDefaultPath+"\\FAIL");
	
	try {

	    Date nowDate = Calendar.getInstance().getTime();
	    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
	    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
	    SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

	    year = yearFormat.format(nowDate);
	    month = monthFormat.format(nowDate);
	    day = dayFormat.format(nowDate);

	} catch (Exception e) {
	    throw e;
	}
	WTProperties properties = WTProperties.getLocalProperties();
	String hostName = properties.getProperty("java.rmi.server.hostname");
	String prefix = "plmWas";
	    // 개발서버 hostName.equals("ketplmdev.ket.com")
	    // 운영서버 hostName.equals("plm.ket.com")
	if (!hostName.equals("plm.ket.com")) {
	    prefix = "plmDev";
	}
	String requestNo = this.getNumber(prefix);
	
	String filePath = foldDefaultPath + "\\" + year + month + day + "\\" + requestNo; //convert 대상 원본과 convert 결과 파일 경로
	String convertServerReqFilePath = convertServerFoldDefaultPath+ "\\" + year + month + day + "\\" + requestNo;
	String zipFilePath = foldDefaultPath + "\\" + year + month + day + "\\" + requestNo + "\\collectAll";
	FileUtil.checkDir(filePath);
	FileUtil.checkDir(zipFilePath);
	
	
	if(CommonUtil.getObject(oid) instanceof KETSGDocument ){
	    contentItemDelete((ContentHolder)CommonUtil.getObject(oid));
	}
	
	ContentHolder doc = (ContentHolder)CommonUtil.getObject(oid);

	docFileDownLoadUtil util = new docFileDownLoadUtil();
	
	String fileName = util.getFileInfoForDoc(doc, filePath);
	
	
//	1. system : MES, PLM

//	2. docCategory : 작업표준서, DFMEA 등등..

//	3. sourceFilePath : 변환대상 파일 경로 (엑셀) 문서변환서버의 저장경로

//	4. oid (PLM 객체 objectId)

//	5. version

//	6. destFilePath : 목적지 서버의 변환파일 저장경로
	
	JSONObject obj = new JSONObject();
	
	String sourcePath = convertServerReqFilePath+"\\"+fileName;
	
	
	
	obj.put("System", "PLM");
	obj.put("HostName", hostName);
	obj.put("Category", "시스템가이드");
	obj.put("sourcePath", sourcePath);
	obj.put("oid", oid);
	obj.put("version","" );
	obj.put("convertDirPath", filePath);
	obj.put("convertServerReqFilePath", convertServerReqFilePath);
	obj.put("divide", "NO"); //OK로 설정시 엑셀의 경우 시트별로 이미지 생성한다
	
	String jsonFilePath = foldDefaultPath+"\\"+requestNo + ".json";
	
	Writer out = null;
	try {
	    File file = new File(jsonFilePath);

	    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
	    out.write(obj.toJSONString());
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (out != null)
		try {
		    out.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
    }
    
    
    private String getNumber(String prefix) throws Exception {
	StringBuffer sql = new StringBuffer();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    con = PlmDBUtil.getConnection();

	    sql.append("SELECT '").append(prefix).append("'|| TO_CHAR (SYSDATE, 'YYMM')").append("|| TRIM(TO_CHAR(SEQ_DRAWINGDISREQUEST_NUM.NEXTVAL,'000'))").append("FROM DUAL");
	    pstmt = con.prepareStatement(sql.toString());

	    rs = pstmt.executeQuery();
	    String docId = "";
	    while (rs.next()) {
		docId = rs.getString(1);
	    }

	    return docId;
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception(e);
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
    }

}
