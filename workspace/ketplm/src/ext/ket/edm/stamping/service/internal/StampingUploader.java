package ext.ket.edm.stamping.service.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.doc.WTDocument;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.FileUtil;
import ext.ket.edm.entity.KETDrawingDistDocLink;
import ext.ket.edm.entity.KETDrawingDistEpmLink;
import ext.ket.edm.entity.KETDrawingDistFileType;
import ext.ket.edm.entity.KETDrawingDistFileTypeLink;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.edm.entity.KETStampDocItem;
import ext.ket.edm.entity.KETStampDocLink;
import ext.ket.edm.entity.KETStampEpmLink;
import ext.ket.edm.entity.KETStamping;
import ext.ket.edm.entity.KETStampingItem;
import ext.ket.edm.entity.KETStampingItemLink;
import ext.ket.edm.stamping.oxm.DeployDrawing;
import ext.ket.edm.stamping.oxm.DrawingDeployRequest;
import ext.ket.edm.stamping.oxm.RequestDrawingList;
import ext.ket.edm.stamping.util.DocumentFileDownUtil;
import ext.ket.edm.stamping.util.StampingCadTypeEnum;
import ext.ket.edm.stamping.util.StampingConstants;
import ext.ket.edm.stamping.util.StampingItemStatusEnum;
import ext.ket.edm.stamping.util.ZipUtil;
import ext.ket.edm.util.DrawingDistFileTypeEnum;
import ext.ket.shared.content.entity.UploadedFile;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class StampingUploader {

    // Stamping Item 및 첨부파일 저장
    public int uploadStamping(KETDrawingDistRequest drawingDistReq, KETStamping stamping, DrawingDeployRequest drawingDeployRequest,
	    String startFilePath) throws Exception {

	int stampingResultFileCount = 0;

	// xml Object로 cad 변환 및 stamping 관련 I/F XML정보를 Object로 가지고 있음
	RequestDrawingList requestDrawingList = drawingDeployRequest.getRequestDrawingList();
	Kogger.debug(getClass(), "## RequestDrawingList :" + drawingDeployRequest);
	Kogger.debug(getClass(), "## KETStamping :" + stamping);
	Kogger.debug(getClass(), "## KETDrawingDistRequest :" + drawingDistReq);
	Kogger.debug(getClass(), "## startFilePath :" + startFilePath);

	// 기존 링크 정보를 삭제한다.
	deleteStampingItem(drawingDistReq, stamping);

	String FILE_SEPER = drawingDeployRequest.getFileSeperator();

	// 일괄다운로드를 위해 전체를 저장하기 위해 한 곳에 저장하는 directory 위치
	String collectAllFoldPath = startFilePath + FILE_SEPER + StampingConstants.STAMP_COLLECT_ALL_FOLD;
	Kogger.debug(getClass(), "## collectAllFoldPath :" + collectAllFoldPath);
	// 미리 폴더를 만들기
	FileUtil.checkDir(collectAllFoldPath + FILE_SEPER + StampingConstants.STAMP_COLLECT_ALL_SUB_EPM_FOLD);
	FileUtil.checkDir(collectAllFoldPath + FILE_SEPER + StampingConstants.STAMP_COLLECT_ALL_SUB_DOC_FOLD);

	// 배포 포멧을 가져온다.
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<KETDrawingDistFileType> distFileTypeList = query.queryForListAByRoleB(KETDrawingDistFileType.class,
	        KETDrawingDistFileTypeLink.class, KETDrawingDistRequest.class, drawingDistReq);

	boolean isPDF = false;
	boolean isDWG = false;
	// boolean isONEDO = false;
	boolean isSTEP = false;

	for (KETDrawingDistFileType fileType : distFileTypeList) {

	    DrawingDistFileTypeEnum distFileTypeEnum = DrawingDistFileTypeEnum.valueOf(fileType.getDistFileType());

	    switch (distFileTypeEnum) {
	    case PDF:
		isPDF = true;
		break;
	    case DWG:
		isDWG = true;
		break;
	    // case ONEDO:
	    // isONEDO = true;
	    // break;
	    case STEP:
		isSTEP = true;
		break;
	    }
	}

	Kogger.debug(getClass(), "## isPDF :" + isPDF);
	Kogger.debug(getClass(), "## isDWG :" + isDWG);
	Kogger.debug(getClass(), "## isSTEP :" + isSTEP);

	List<DeployDrawing> drawingList = requestDrawingList.getDrawingList();
	for (DeployDrawing deployDrawing : drawingList) {

	    // ///////////////////////////////////////////////
	    // 도면쪽 저장
	    // ///////////////////////////////////////////////

	    // ## 도면 가져오기
	    EPMDocument epmDoc = (EPMDocument) CommonUtil.getObject(deployDrawing.getCadOid());

	    // ## Link 가져오기
	    KETDrawingDistEpmLink distEpmLink = getDisEpmLink(drawingDistReq, epmDoc);

	    // ## StampingItem 저장
	    KETStampingItem stampingItem = KETStampingItem.newKETStampingItem();
	    stampingItem.setStampStatus(StampingItemStatusEnum.SUCCESS.toString());
	    stampingItem = (KETStampingItem) PersistenceHelper.manager.save(stampingItem);

	    // ## 기타 링크 정보 저장
	    KETStampEpmLink stampEpmLink = KETStampEpmLink.newKETStampEpmLink(stampingItem, distEpmLink);
	    stampEpmLink = (KETStampEpmLink) PersistenceHelper.manager.save(stampEpmLink);

	    KETStampingItemLink stampItemLink = KETStampingItemLink.newKETStampingItemLink(stampingItem, stamping);
	    stampItemLink = (KETStampingItemLink) PersistenceHelper.manager.save(stampItemLink);

	    // ## 첨부파일 저장
	    List<UploadedFile> uploadedFiles = new ArrayList<UploadedFile>();
	    String transYN = deployDrawing.getTransRequest();
	    String drawingNumber = deployDrawing.getDrawingNumber();
	    StampingCadTypeEnum cadTypeEnum = StampingCadTypeEnum.valueOf(deployDrawing.getCadType());

	    // 파일 찾기
	    if (isDWG) {
		// dwg - include with transed creo, nx
		File[] fileArray = findFileByPattern(startFilePath, drawingNumber, ".dwg");
		addAllUploadFile(uploadedFiles, fileArray, collectAllFoldPath, FILE_SEPER, drawingNumber);
	    }

	    if (isPDF) {
		File[] fileArray = findFileByPattern(startFilePath, drawingNumber, ".pdf");
		addAllUploadFile(uploadedFiles, fileArray, collectAllFoldPath, FILE_SEPER, drawingNumber);
	    }

	    // if (isONEDO) {
	    // // TKLEE 원도의 경우 별도 처리 필요함
	    // if (cadTypeEnum == StampingCadTypeEnum.NX) {
	    // if (StampingConstants.Y.equals(transYN)) {
	    // File[] fileArray = findFileByPattern(startFilePath + FILE_SEPER + drawingNumber, deployDrawing.getTransThreedCadFileName(),
	    // ".prt");
	    // addAllUploadFile(uploadedFiles, fileArray, collectAllFoldPath, FILE_SEPER, drawingNumber);
	    // }
	    // }
	    // }

	    if (isSTEP) {
		String drawingFileName = deployDrawing.getDrawingFileName();
		if ((drawingFileName != null && drawingFileName.toUpperCase().endsWith(".PRT"))
		        || (drawingFileName != null && drawingFileName.toUpperCase().endsWith(".ASM"))) {
		    // File[] fileArray = findFileByPattern(startFilePath, drawingNumber, ".stp");
		    File[] fileArray = findFileByPattern(startFilePath, drawingNumber);
		    addAllUploadFile(uploadedFiles, fileArray, collectAllFoldPath, FILE_SEPER, drawingNumber);
		}
	    }

	    List<ContentItem> contentItems = null;
	    ContentHolder holder = stampingItem;

	    // 전체 stamping 관련 파일 수를 count 한다.
	    if (uploadedFiles != null) {
		stampingResultFileCount = stampingResultFileCount + uploadedFiles.size();
	    }

	    /*
	     * 오라클 blob 공간 낭비가 심하여 다운로드시 해당 파일을 캐드서버에서 직접 다운로드 하는 방식으로 변경함 따라서 아래의 updateContent 메서드는 주석처리한다
	     */
	    // 개별 도면 저장
	    // KETContentHelper.service.updateContent(holder, uploadedFiles, contentItems);

	}

	// 문서 정보 저장
	DocumentFileDownUtil docFileDownUtil = new DocumentFileDownUtil();
	List<KETDrawingDistDocLink> relatedWtDocLinkList = getRelatedDocLinkWithDistReq(drawingDistReq);
	for (KETDrawingDistDocLink wtDocLink : relatedWtDocLinkList) {
	    WTDocument wtDoc = wtDocLink.getDistDoc();

	    docFileDownUtil.executeDocFileInfo(wtDoc, collectAllFoldPath + FILE_SEPER + StampingConstants.STAMP_COLLECT_ALL_SUB_DOC_FOLD
		    + FILE_SEPER + wtDoc.getNumber());

	    // ///////////////////////////////////
	    // / 문서 쪽 저장
	    // ///////////////////////////////////
	    // ## StampingItem 저장
	    KETStampDocItem stampDocItem = KETStampDocItem.newKETStampDocItem();
	    stampDocItem = (KETStampDocItem) PersistenceHelper.manager.save(stampDocItem);

	    // ## 기타 링크 정보 저장
	    KETStampDocLink stampDocLink = KETStampDocLink.newKETStampDocLink(stampDocItem, wtDocLink);
	    stampDocLink = (KETStampDocLink) PersistenceHelper.manager.save(stampDocLink);

	    // ## 첨부파일 저장
	    List<UploadedFile> uploadedFiles = new ArrayList<UploadedFile>();

	    // 파일 찾기
	    File[] fileArray = findFileByPattern(collectAllFoldPath + FILE_SEPER + StampingConstants.STAMP_COLLECT_ALL_SUB_DOC_FOLD
		    + FILE_SEPER + wtDoc.getNumber(), "", "");
	    if (fileArray == null) {
		continue;
	    }

	    for (File srcFile : fileArray) {
		uploadedFiles.add(getUploadedFile(srcFile));
	    }

	    List<ContentItem> contentItems = null;
	    ContentHolder holder = stampDocItem;

	    // 전체 stamping 관련 파일 수를 count 한다.
	    if (uploadedFiles != null) {
		stampingResultFileCount = stampingResultFileCount + uploadedFiles.size();
	    }

	    // 개별 주첨부 저장
	    KETContentHelper.service.updateContent(holder, uploadedFiles, contentItems);

	}

	// 전체 일괄 도면 저장
	String sourcePath = collectAllFoldPath;
	String outputPath = startFilePath + FILE_SEPER + StampingConstants.STAMP_COLLECT_ZIO_NAME;
	ZipUtil.zip(sourcePath, outputPath);

	List<UploadedFile> uploadedZipFile = new ArrayList<UploadedFile>();
	uploadedZipFile.add(getUploadedFile(new File(outputPath)));

	List<ContentItem> contentZipItem = null;
	/*
	 * 오라클 blob 공간 낭비가 심하여 다운로드시 해당 파일을 캐드서버에서 직접 다운로드 하는 방식으로 변경함 따라서 아래의 updateContent 메서드는 주석처리한다
	 */
	// KETContentHelper.service.updateContent(stamping, uploadedZipFile, contentZipItem);

	return stampingResultFileCount;

    }

    // get stamping Object
    private void deleteStampingItem(KETDrawingDistRequest drawingDistReq, KETStamping stamping) throws Exception {

	if (drawingDistReq == null)
	    throw new Exception("# KETDrawingDistRequest is null !! : StampingUploader  deleteStampingItem() ");

	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

	// StampingItemLink delete
	List<KETStampingItemLink> stampingLinkList = query.queryForListLinkByRoleB(KETStampingItemLink.class, KETStamping.class, stamping);
	for (KETStampingItemLink link : stampingLinkList) {
	    PersistenceHelper.manager.delete(link);
	}

	// StampingItem delete
	List<KETDrawingDistEpmLink> disEpmLinkList = query.queryForListLinkByRoleB(KETDrawingDistEpmLink.class,
	        KETDrawingDistRequest.class, drawingDistReq);
	for (KETDrawingDistEpmLink distEpmlink : disEpmLinkList) {
	    List<KETStampEpmLink> stampingItemLinkList = query.queryForListLinkByRoleB(KETStampEpmLink.class, KETDrawingDistEpmLink.class,
		    distEpmlink);
	    for (KETStampEpmLink itemLink : stampingItemLinkList) {
		KETStampingItem item = itemLink.getStampItem();
		PersistenceHelper.manager.delete(item);
		PersistenceHelper.manager.delete(itemLink);
	    }
	}

	// 문서 StampingItem delete
	List<KETDrawingDistDocLink> relatedDocList = query.queryForListLinkByRoleB(KETDrawingDistDocLink.class,
	        KETDrawingDistRequest.class, drawingDistReq);
	for (KETDrawingDistDocLink relatedDoclink : relatedDocList) {
	    List<KETStampDocLink> stampingItemLinkList = query.queryForListLinkByRoleB(KETStampDocLink.class, KETDrawingDistDocLink.class,
		    relatedDoclink);
	    for (KETStampDocLink itemLink : stampingItemLinkList) {
		KETStampDocItem item = itemLink.getStampDocItem();
		PersistenceHelper.manager.delete(item);
		PersistenceHelper.manager.delete(itemLink);
	    }
	}

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
	final String allowPattern = ".+\\.(igs|jpg|pdf|stp|tif|bmp|esp|CATPart)$";
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
    private void addAllUploadFile(List<UploadedFile> uploadFileList, File[] fileArray, String collectAllFoldPath, String fileSeper,
	    String drawingNumber) throws Exception {
	if (fileArray == null)
	    return;

	for (File srcFile : fileArray) {
	    FileUtils.copyFile(srcFile, new File(collectAllFoldPath + fileSeper + StampingConstants.STAMP_COLLECT_ALL_SUB_EPM_FOLD
		    + fileSeper + drawingNumber + fileSeper + srcFile.getName()));
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

    // 배포요청서와 EPMDoc 간의 Link 객체 가져오기
    private KETDrawingDistEpmLink getDisEpmLink(KETDrawingDistRequest drawingDistReq, EPMDocument epmDoc) throws Exception {

	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	KETDrawingDistEpmLink disEpmLink = query.queryForObjectLinkByRoleAB(EPMDocument.class, KETDrawingDistEpmLink.class,
	        KETDrawingDistRequest.class, epmDoc, drawingDistReq);

	return disEpmLink;
    }

    // 연관 문서 가져오기
    private List<KETDrawingDistDocLink> getRelatedDocLinkWithDistReq(KETDrawingDistRequest drawingDistReq) throws Exception {

	if (drawingDistReq == null)
	    throw new Exception("# KETDrawingDistRequest is null !! : StampingUploader getRelatedDocWithDistReq() ");

	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<KETDrawingDistDocLink> relatedDocList = query.queryForListLinkByRoleB(KETDrawingDistDocLink.class,
	        KETDrawingDistRequest.class, drawingDistReq);

	return relatedDocList;
    }

    private List<WTDocument> getRelatedDocWithDistReq(KETDrawingDistRequest drawingDistReq) throws Exception {

	if (drawingDistReq == null)
	    throw new Exception("# KETDrawingDistRequest is null !! : StampingUploader getRelatedDocWithDistReq() ");

	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<WTDocument> relatedDocList = query.queryForListAByRoleB(WTDocument.class, KETDrawingDistDocLink.class,
	        KETDrawingDistRequest.class, drawingDistReq);

	return relatedDocList;
    }

}
