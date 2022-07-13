package ext.ket.shared.content.service;

import java.io.InputStream;
import java.util.List;

import wt.content.ApplicationData;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.method.RemoteInterface;
import ext.ket.shared.content.entity.UploadedFile;
import ext.ket.shared.dto.BaseDTO;
import ext.ket.shared.service.CommonServiceInterface;

/**
 * @클래스명 : ContentService
 * @작성자 : Jason, Han
 * @작성일 : 2014. 6. 27.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
@RemoteInterface
public interface KETContentService extends CommonServiceInterface<BaseDTO, ContentHolder> {

    /**
     * @param holder
     * @param is
     * @param filename
     * @param filesize
     * @param role
     * @return ContentHolder
     * @exception java.lang.Exception
     * @exception wt.util.WTException
     **/
    public ContentHolder attache(ContentHolder holder, InputStream is, String filename, long filesize, ContentRoleType role) throws Exception;

    /**
     * @param holder
     * @param file
     * @param role
     * @return ContentHolder
     * @exception java.lang.Exception
     * @exception wt.util.WTException
     **/
    public ContentHolder attache(ContentHolder holder, UploadedFile file, ContentRoleType role) throws Exception;

    /**
     * @param holder
     * @param data
     * @param isPrimary
     * @return ContentHolder
     * @exception java.lang.Exception
     * @exception wt.util.WTException
     **/
    public ContentHolder attache(ContentHolder holder, ApplicationData data, boolean isPrimary) throws Exception;

    /**
     * @param holder
     * @param file
     * @param replaceItem
     * @return ContentHolder
     * @exception java.lang.Exception
     * @exception wt.util.WTException
     **/
    public ContentHolder replace(ContentHolder holder, UploadedFile file, ContentItem replaceItem) throws Exception;

    /**
     * @param holder
     * @param is
     * @param filename
     * @param filesize
     * @param replaceItem
     * @return ContentHolder
     * @exception java.lang.Exception
     * @exception wt.util.WTException
     **/
    public ContentHolder replace(ContentHolder holder, InputStream is, String filename, long filesize, ContentItem replaceItem) throws Exception;

    /**
     * @param holder
     * @param deleteItem
     * @return ContentHolder
     * @exception java.lang.Exception
     * @exception wt.util.WTException
     **/
    public ContentHolder delete(ContentHolder holder, ContentItem deleteItem) throws Exception;

    /**
     * @param holder
     * @return ContentHolder
     * @exception java.lang.Exception
     * @exception wt.util.WTException
     **/
    public ContentHolder delete(ContentHolder holder) throws Exception;

    /**
     * @param holder
     * @param dto
     * @return
     * @throws Exception
     * @메소드명 : updateContent
     * @작성자 : Jason, Han
     * @작성일 : 2014. 6. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public void updateContent(ContentHolder holder, BaseDTO dto) throws Exception;

    /**
     * @param holder
     * @param uploadedFiles
     * @param contentItems
     * @throws Exception
     * @메소드명 : updateContent
     * @작성자 : Jason, Han
     * @작성일 : 2014. 6. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public void updateContent(ContentHolder holder, List<UploadedFile> uploadedFiles, List<ContentItem> contentItems) throws Exception;

    /**
     * srcHolder의 컨텐트 정보를 copyHolder로 복사한다.
     * 
     * @param srcHolder
     * @param copyHolder
     * @throws Exception
     * @메소드명 : copyContent
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 21.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public void copyContent(ContentHolder srcHolder, ContentHolder copyHolder) throws Exception;
    
    /**
     * 파일별 ApplicationData를 반환한다
     * @param holder
     * @param file
     * @param role
     * @return ApplicationData
     * @exception java.lang.Exception
     * @exception wt.util.WTException
     * @메소드명 : attache2App
     * @작성자 : 황정태
     * @작성일 : 2017. 3. 02.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     **/
    public ApplicationData attache2App(ContentHolder holder, UploadedFile file, ContentRoleType role) throws Exception;
    
    
    /**
     * @param ApplicationData
     * @param dto
     * @return
     * @throws Exception
     * @메소드명 : updateContent
     * @작성자 : 황정태
     * @작성일 : 2017. 3. 02.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public ApplicationData updateContent(ContentHolder holder, UploadedFile file) throws Exception;
    

}
