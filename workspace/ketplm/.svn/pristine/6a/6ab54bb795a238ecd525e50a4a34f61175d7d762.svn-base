package ext.ket.shared.content.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import ext.ket.shared.drm.DRMHelper;
import wt.content.ContentRoleType;

/**
 * @클래스명 : UploadFile
 * @작성자 : Administrator
 * @작성일 : 2014. 6. 27.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class UploadedFile {

    private String fileName;
    private String contentRoleType;
    private String contentType;
    private String extension;
    private long size = 0;
    private InputStream file;
    private String description;

    public static UploadedFile newUploadedFile(MultipartFile file, boolean isPrimary) throws Exception {

	UploadedFile uploadfile = new UploadedFile();

	// uploadfile.setContentType(file.getContentType());
	// uploadfile.setContentRoleType(isPrimary ? ContentRoleType.PRIMARY.toString() : ContentRoleType.SECONDARY.toString());
	// uploadfile.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
	// uploadfile.setFile(file.getInputStream());
	// uploadfile.setSize(file.getSize());
	// uploadfile.setFileName(FilenameUtils.getName(file.getOriginalFilename()));
	
	MultipartFile Decryptfile = null; 
		
	if(file.getSize() > 0){
	    	File tempFile = null;
	    try{
		tempFile = multipartToFile(file);
		if(DRMHelper.useDrm){
		    Decryptfile = fileToMultipart(DRMHelper.Decryptupload(tempFile, file.getName()));
		}else{
		    Decryptfile = fileToMultipart(tempFile);
		}
	    }catch(Exception e){
		e.printStackTrace();
		Decryptfile = fileToMultipart(tempFile);
	    }
	        
	}else{
	    Decryptfile = file;
	}
	
	String roleType = (isPrimary ? ContentRoleType.PRIMARY.toString() : ContentRoleType.SECONDARY.toString());
	String ext = FilenameUtils.getExtension(file.getOriginalFilename());
	setUploadFile(uploadfile, file.getContentType(), roleType, ext, Decryptfile.getInputStream(), file.getSize(), FilenameUtils.getName(file.getOriginalFilename()));

	return uploadfile;
    }

    public static void setUploadFile(UploadedFile uploadfile, String contentType, String roleType, String ext, InputStream inputStream, long size, String fileName) {
	uploadfile.setContentType(contentType);
	uploadfile.setContentRoleType(roleType);
	uploadfile.setExtension(ext);
	uploadfile.setFile(inputStream);
	uploadfile.setSize(size);
	uploadfile.setFileName(fileName);
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
	return fileName;
    }

    /**
     * @param fileName
     *            the fileName to set
     */
    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    /**
     * @return the contentRoleType
     */
    public String getContentRoleType() {
	return contentRoleType;
    }

    /**
     * @param contentRoleType
     *            the contentRoleType to set
     */
    public void setContentRoleType(String contentRoleType) {
	this.contentRoleType = contentRoleType;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
	return contentType;
    }

    /**
     * @param contentType
     *            the contentType to set
     */
    public void setContentType(String contentType) {
	this.contentType = contentType;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
	return extension;
    }

    /**
     * @param extension
     *            the extension to set
     */
    public void setExtension(String extension) {
	this.extension = extension;
    }

    /**
     * @return the size
     */
    public long getSize() {
	return size;
    }

    /**
     * @param size
     *            the size to set
     */
    public void setSize(long size) {
	this.size = size;
    }

    /**
     * @return the file
     */
    public InputStream getFile() {
	return file;
    }

    /**
     * @param file
     *            the file to set
     */
    public void setFile(InputStream file) {
	this.file = file;
    }
    
    
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static File multipartToFile(MultipartFile multipart) throws Exception
    {
        File convFile = new File( multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }
    
    public static MultipartFile fileToMultipart(File file) throws Exception{
    	FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",file.getName(), "text/plain", IOUtils.toByteArray(input));
        return multipartFile;
    }

}
