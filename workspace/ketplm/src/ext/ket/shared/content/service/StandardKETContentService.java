package ext.ket.shared.content.service;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.content.HolderToContent;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTPrincipalReference;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.content.remote.ContentDownload;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.entity.UploadedFile;
import ext.ket.shared.dto.BaseDTO;
import ext.ket.shared.log.Kogger;

/**
 * @클래스명 : StandardContentService
 * @작성자 : Jason, Han
 * @작성일 : 2014. 6. 27.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class StandardKETContentService extends StandardManager implements KETContentService, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5653095282978342241L;

    public static StandardKETContentService newStandardKETContentService() throws WTException {
	StandardKETContentService instance = new StandardKETContentService();
	instance.initialize();
	return instance;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.content.service.KETContentService#attache(wt.content.ContentHolder, java.io.InputStream, java.lang.String, long,
     * wt.content.ContentRoleType)
     */
    @Override
    public ContentHolder attache(ContentHolder holder, InputStream is, String filename, long filesize, ContentRoleType role)
	    throws Exception {

	WTPrincipalReference principalReference = SessionHelper.manager.getPrincipalReference();

	ApplicationData applicationData = ApplicationData.newApplicationData(holder);
	applicationData.setFileName(filename);
	applicationData.setFileSize(filesize);
	applicationData.setRole(role);

	applicationData.setCreatedBy(principalReference);
	ContentServerHelper.service.updateContent(holder, applicationData, is);

	return holder;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.content.service.KETContentService#attache(wt.content.ContentHolder, ext.ket.shared.content.entity.UploadedFile,
     * wt.content.ContentRoleType)
     */
    @Override
    public ContentHolder attache(ContentHolder holder, UploadedFile file, ContentRoleType role) throws Exception {

	WTPrincipalReference principalReference = SessionHelper.manager.getPrincipalReference();

	InputStream is = file.getFile();
	ApplicationData applicationData = ApplicationData.newApplicationData(holder);
	applicationData.setFileName(file.getFileName());
	applicationData.setFileSize(file.getSize());
	applicationData.setRole(role);
	applicationData.setCreatedBy(principalReference);
	applicationData.setDescription(file.getDescription());

	ContentServerHelper.service.updateContent(holder, applicationData, is);

	return holder;
    }

    @Override
    public ApplicationData attache2App(ContentHolder holder, UploadedFile file, ContentRoleType role) throws Exception {

	WTPrincipalReference principalReference = SessionHelper.manager.getPrincipalReference();

	InputStream is = file.getFile();
	ApplicationData applicationData = ApplicationData.newApplicationData(holder);
	applicationData.setFileName(file.getFileName());
	applicationData.setFileSize(file.getSize());
	applicationData.setRole(role);
	applicationData.setCreatedBy(principalReference);

	ApplicationData app = ContentServerHelper.service.updateContent(holder, applicationData, is);

	return app;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.content.service.KETContentService#attache(wt.content.ContentHolder, wt.content.ApplicationData, boolean)
     */
    @Override
    public ContentHolder attache(ContentHolder holder, ApplicationData data, boolean isPrimary) throws Exception {

	InputStream is = ContentServerHelper.service.findContentStream(data);

	ApplicationData saveData = ApplicationData.newApplicationData(holder);
	saveData.setIntendedForHttpOp(true);
	saveData.setFileName(data.getFileName());
	saveData.setFileSize(data.getFileSize());
	saveData.setCreatedBy(data.getCreatedBy());
	saveData.setDescription(data.getDescription() == null ? "" : data.getDescription());
	if (isPrimary)
	    saveData.setRole(ContentRoleType.PRIMARY);
	else
	    saveData.setRole(ContentRoleType.SECONDARY);
	ContentServerHelper.service.updateContent(holder, saveData, is);

	return holder;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.content.service.KETContentService#replace(wt.content.ContentHolder, ext.ket.shared.content.entity.UploadedFile,
     * wt.content.ContentItem)
     */
    @Override
    public ContentHolder replace(ContentHolder holder, UploadedFile file, ContentItem replaceItem) throws Exception {

	delete(holder, replaceItem);
	attache(holder, file, replaceItem.getRole());

	return holder;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.content.service.KETContentService#replace(wt.content.ContentHolder, java.io.InputStream, java.lang.String, long,
     * wt.content.ContentItem)
     */
    @Override
    public ContentHolder replace(ContentHolder holder, InputStream is, String filename, long filesize, ContentItem replaceItem)
	    throws Exception {

	delete(holder, replaceItem);
	attache(holder, is, filename, filesize, replaceItem.getRole());

	return holder;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.content.service.KETContentService#delete(wt.content.ContentHolder, wt.content.ContentItem)
     */
    @Override
    public ContentHolder delete(ContentHolder holder, ContentItem deleteItem) throws Exception {

	if (holder == null || deleteItem == null)
	    return holder;
	holder = ContentHelper.service.getContents(holder);
	ContentServerHelper.service.deleteContent(holder, deleteItem);

	return holder;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.content.service.KETContentService#delete(wt.content.ContentHolder)
     */
    @Override
    public ContentHolder delete(ContentHolder holder) throws Exception {

	holder = ContentHelper.service.getContents(holder);
	ArrayList<ContentDTO> list = KETContentHelper.manager.getContents(holder);
	if (list != null) {
	    for (int i = 0; i < list.size(); i++) {
		ContentDTO broker = (ContentDTO) list.get(i);
		holder = delete(holder, (ApplicationData) CommonUtil.getObject(broker.getContentoid()));
	    }
	}
	return holder;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.content.service.KETContentService#updateContent(wt.content.ContentHolder,
     * ext.ket.shared.content.entity.UploadedFile[], wt.content.ContentItem[])
     */
    @Override
    public void updateContent(ContentHolder holder, List<UploadedFile> uploadedFiles, List<ContentItem> contentItems) throws Exception {

	ArrayList<ContentDTO> secondaryContents = KETContentHelper.manager.getSecondaryContents(holder);
	if (secondaryContents != null && secondaryContents.size() > 0) {
	    for (ContentDTO dto : secondaryContents) {
		ApplicationData existApp = (ApplicationData) CommonUtil.getObject(dto.getContentoid());
		ApplicationData compareApp = null;
		boolean doDelete = true;
		if (contentItems != null && contentItems.size() > 0) {
		    for (ContentItem item : contentItems) {
			compareApp = (ApplicationData) item;
			if (compareApp == null)
			    continue;
			if (existApp.getFileName().equals(compareApp.getFileName()) && existApp.getFileSize() == compareApp.getFileSize()) {
			    doDelete = false;
			    break;
			} else {
			    doDelete = true;
			}
		    }
		}
		if (doDelete) {
		    Kogger.debug(getClass(), "Delete Exist Content Item : " + existApp);
		    holder = KETContentHelper.service.delete(holder, existApp);
		}
		// String contentoid = dto.getContentoid();
		// if (!existMap.containsKey(contentoid)) {
		// holder = KETContentHelper.service.delete(holder, (ContentItem) CommonUtil.getObject(contentoid));
		// }
	    }
	}

	if (uploadedFiles != null && uploadedFiles.size() > 0) {
	    for (UploadedFile file : uploadedFiles) {
		if (file != null && file.getFile() != null && file.getSize() > 0) {
		    String roleType = StringUtil.checkNull(file.getContentRoleType());
		    Kogger.debug(getClass(), "uploadedFiles roleType : " + roleType);
		    if (StringUtil.checkString(roleType)) {
			ContentRoleType role = ContentRoleType.toContentRoleType(roleType);
			if ("PRIMARY".equals(roleType)) {
			    ContentDTO primaryDto = KETContentHelper.manager.getPrimaryContent(holder);
			    if (primaryDto != null)
				holder = KETContentHelper.service.delete(holder,
				        (ContentItem) CommonUtil.getObject(primaryDto.getContentoid()));
			    Kogger.debug(getClass(), "ADD ContentRoleType : " + role);
			    KETContentHelper.service.attache(holder, file, role);
			} else if ("SECONDARY".equals(roleType)) {
			    Kogger.debug(getClass(), "ADD ContentRoleType : " + role);
			    KETContentHelper.service.attache(holder, file, role);
			}
		    }
		}
	    }
	}
    }

    @Override
    public ApplicationData updateContent(ContentHolder holder, UploadedFile file) throws Exception {

	ApplicationData applicationData = null;

	if (file != null && file.getFile() != null && file.getSize() > 0) {
	    String roleType = StringUtil.checkNull(file.getContentRoleType());
	    Kogger.debug(getClass(), "uploadedFiles roleType : " + roleType);
	    if (StringUtil.checkString(roleType)) {
		ContentRoleType role = ContentRoleType.toContentRoleType(roleType);
		if ("PRIMARY".equals(roleType)) {
		    ContentDTO primaryDto = KETContentHelper.manager.getPrimaryContent(holder);
		    if (primaryDto != null)
			holder = KETContentHelper.service.delete(holder, (ContentItem) CommonUtil.getObject(primaryDto.getContentoid()));
		    Kogger.debug(getClass(), "ADD ContentRoleType : " + role);
		    applicationData = KETContentHelper.service.attache2App(holder, file, role);
		} else if ("SECONDARY".equals(roleType)) {
		    Kogger.debug(getClass(), "ADD ContentRoleType : " + role);
		    applicationData = KETContentHelper.service.attache2App(holder, file, role);
		}
	    }

	}
	return applicationData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.shared.content.service.KETContentService#updateContent(wt.content.ContentHolder, ext.ket.shared.dto.BaseDTO)
     */
    @Override
    public void updateContent(ContentHolder holder, BaseDTO dto) throws Exception {

	List<ContentItem> contentItems = new ArrayList<ContentItem>(); // not remove contentitem list
	List<UploadedFile> files = new ArrayList<UploadedFile>(); // uploaded new file list

	// getting not remove contentitem list
	String[] cios = dto.getSecondaryFileOids();
	if (cios != null && cios.length > 0) {
	    for (String cio : cios) {
		contentItems.add((ContentItem) CommonUtil.getObject(cio));
	    }
	}

	// getting uploaded new file list
	MultipartFile primaryFile = dto.getPrimaryFile();
	List<MultipartFile> secondaryFiles = dto.getSecondaryFiles();
	if (primaryFile != null) {
	    UploadedFile uploadedFile = UploadedFile.newUploadedFile(primaryFile, true);
	    files.add(uploadedFile);
	}

	if (secondaryFiles != null && secondaryFiles.size() > 0) {
	    
	    int i=0;
	    
	    String description[] = dto.getFileDescriptions();
	    
	    for (MultipartFile multipartFile : secondaryFiles) {
		
		UploadedFile uploadedFile = UploadedFile.newUploadedFile(multipartFile, false);
		
		if(description != null && description.length > 0){
		    uploadedFile.setDescription(description[i]);
		}
		
		files.add(uploadedFile);
		i++;
	    }
	}

	updateContent(holder, files, contentItems);
    }

    @Override
    public void copyContent(ContentHolder srcHolder, ContentHolder copyHolder) throws Exception {

	// primary content copy
	ContentDTO primaryDto = KETContentHelper.manager.getPrimaryContent(srcHolder);
	if (primaryDto != null) {
	    ApplicationData data = (ApplicationData) CommonUtil.getObject(primaryDto.getContentoid());
	    ApplicationData copyData = (ApplicationData) data.duplicate();
	    copyData.setHolderLink(getHolderToContent(srcHolder, copyData));
	    ContentDownload cd = new ContentDownload();
	    cd.addContentStream(copyData);
	    cd.execute();
	    InputStream is = cd.getInputStream();
	    copyHolder = attache(copyHolder, is, copyData.getFileName(), copyData.getFileSize(), ContentRoleType.PRIMARY);
	    // copyHolder = attache(copyHolder, copyData, true);
	}

	// secondary content copy
	ArrayList<ContentDTO> dtos = KETContentHelper.manager.getSecondaryContents(srcHolder);
	if (dtos != null) {
	    for (ContentDTO dto : dtos) {
		ApplicationData data = (ApplicationData) CommonUtil.getObject(dto.getContentoid());
		ApplicationData copyData = (ApplicationData) data.duplicate();
		copyData.setHolderLink(getHolderToContent(srcHolder, copyData));
		ContentDownload cd = new ContentDownload();
		cd.addContentStream(copyData);
		cd.execute();
		InputStream is = cd.getInputStream();
		copyHolder = attache(copyHolder, is, copyData.getFileName(), copyData.getFileSize(), ContentRoleType.SECONDARY);
		// copyHolder = attache(copyHolder, copyData, false);
	    }
	}
    }

    private HolderToContent getHolderToContent(ContentHolder contentHolder, ApplicationData ad) throws Exception {
	QuerySpec spec = new QuerySpec(ApplicationData.class, wt.content.HolderToContent.class);
	spec.appendWhere(new SearchCondition(ApplicationData.class, "thePersistInfo.theObjectIdentifier.id", "=", ad.getPersistInfo()
	        .getObjectIdentifier().getId()));
	QueryResult queryresult = PersistenceHelper.manager.navigate(contentHolder, "theContentItem", spec, false); // (pp,
														    // "theContentItem",
														    // wt.content.HolderToContent.class,
														    // false);
	HolderToContent holdertocontent = (HolderToContent) queryresult.nextElement();
	return holdertocontent;
    }

    @Override
    public List<ContentHolder> find(BaseDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PageControl findPaging(BaseDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ContentHolder modify(BaseDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ContentHolder delete(BaseDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ContentHolder save(BaseDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }
}
