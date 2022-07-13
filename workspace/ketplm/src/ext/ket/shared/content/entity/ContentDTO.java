/**
 * 
 */
package ext.ket.shared.content.entity;

import java.net.URL;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.URLData;
import wt.util.WTException;
import e3ps.common.content.ContentUtil;
import e3ps.common.util.CommonUtil;
import ext.ket.shared.dto.BaseDTO;

/**
 * @클래스명 : ContentDTO
 * @작성자 : Jason, Han
 * @작성일 : 2014. 6. 27.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class ContentDTO extends BaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 2239694041831618213L;
    private String type; // 컨텐트 타입 (URL/FILE)
    private String mimeType; // 컨텐트 mime type
    private String contentRoleType; // 컨텐트 롤 타입
    private String holderoid; // HOLDER OID
    private String contentoid; // APPLICATIONDATA OID
    private String name; // 컨텐트이름
    private String description; // 설명
    private URL downURL; // 다운로드 URL (OOTB 호출 URL)
    private String downURLStr; // 다운로드 URL (Customizing)
    private String imgURLStr; // 이미지 URL (이미지 호출 URL)
    private String fileSize; // 파일크기(KB)
    private String fileSizeKB; // 파일크기(KB)
    private String businessType; // 비즈니스타입
    private String iconURLStr; // 아이콘URL
    private String downloadURL;

    public ContentDTO(ContentHolder holder, ContentItem item) throws WTException {

	this.holderoid = CommonUtil.getOIDString(holder);

	if (item instanceof URLData) {
	    URLData url = (URLData) item;
	    this.type = "URL";
	    this.contentRoleType = item.getRole().toString();
	    this.contentoid = CommonUtil.getOIDString(url);
	    this.name = url.getUrlLocation();
	    this.description = url.getDescription();
	    // this.iconURLStr = KETContentHelper.manager.getContentIconStr(item);
	    this.iconURLStr = ContentUtil.getContentIconStr(item);
	} else if (item instanceof ApplicationData) {
	    ApplicationData data = (ApplicationData) item;

	    this.type = "FILE";
	    this.contentRoleType = item.getRole().toString();
	    this.contentoid = CommonUtil.getOIDString(data);
	    this.name = data.getFileName();
	    this.description = data.getDescription();
	    this.downURL = ContentHelper.getDownloadURL(holder, data, false);
	    // this.iconURLStr = KETContentHelper.manager.getContentIconStr(item);
	    this.iconURLStr = ContentUtil.getContentIconStr(item);
	    this.mimeType = data.getFormat().getDataFormat().getMimeType();
	    // this.downURLStr = "/plm/ext/download.do?holderoid=" + this.holderoid + "&appoid=" + this.contentoid;
	    this.downURLStr = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + this.holderoid + "&cioids=" + this.contentoid
		    + "&role=" + this.contentRoleType + "&ketCustomFlag=Y";
	    // this.downURLStr = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid=" + CommonUtil.getOIDString(holder) + "&adOid=" +
	    // this.contentoid;
	    this.downloadURL = "<a href=\"" + downURLStr + "\" target=\"download\">" + iconURLStr + "</a>";
	    this.fileSize = String.valueOf(data.getFileSize());
	    this.fileSizeKB = String.valueOf(data.getFileSizeKB()) + " KB";
	    this.businessType = data.getBusinessType();
	}

    }

    /**
     * @return the type
     */
    public String getType() {
	return this.type;
    }

    /**
     * @return the name
     */
    public String getName() {
	return this.name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
	return this.description;
    }

    /**
     * @return the downURL
     */
    public URL getDownURL() {
	return this.downURL;
    }

    /**
     * @return the downURLStr
     */
    public String getDownURLStr() {
	return this.downURLStr;
    }

    /**
     * @return the fileSize
     */
    public String getFileSize() {
	return this.fileSize;
    }

    /**
     * @return the businessType
     */
    public String getBusinessType() {
	return this.businessType;
    }

    /**
     * @return the iconURLStr
     */
    public String getIconURLStr() {
	return this.iconURLStr;
    }

    /**
     * @return the mimeType
     */
    public String getMimeType() {
	return this.mimeType;
    }

    /**
     * @return the contentRoleType
     */
    public String getContentRoleType() {
	return this.contentRoleType;
    }

    /**
     * @return the holderoid
     */
    public String getHolderoid() {
	return this.holderoid;
    }

    /**
     * @return the contentoid
     */
    public String getContentoid() {
	return this.contentoid;
    }

    /**
     * @return the imgURLStr
     */
    public String getImgURLStr() {
	return this.imgURLStr;
    }

    /**
     * @return the fileSizeKB
     */
    public String getFileSizeKB() {
	return this.fileSizeKB;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
	this.type = type;
    }

    /**
     * @param mimeType
     *            the mimeType to set
     */
    public void setMimeType(String mimeType) {
	this.mimeType = mimeType;
    }

    /**
     * @param contentRoleType
     *            the contentRoleType to set
     */
    public void setContentRoleType(String contentRoleType) {
	this.contentRoleType = contentRoleType;
    }

    /**
     * @param holderoid
     *            the holderoid to set
     */
    public void setHolderoid(String holderoid) {
	this.holderoid = holderoid;
    }

    /**
     * @param contentoid
     *            the contentoid to set
     */
    public void setContentoid(String contentoid) {
	this.contentoid = contentoid;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * @param downURL
     *            the downURL to set
     */
    public void setDownURL(URL downURL) {
	this.downURL = downURL;
    }

    /**
     * @param downURLStr
     *            the downURLStr to set
     */
    public void setDownURLStr(String downURLStr) {
	this.downURLStr = downURLStr;
    }

    /**
     * @param imgURLStr
     *            the imgURLStr to set
     */
    public void setImgURLStr(String imgURLStr) {
	this.imgURLStr = imgURLStr;
    }

    /**
     * @param fileSize
     *            the fileSize to set
     */
    public void setFileSize(String fileSize) {
	this.fileSize = fileSize;
    }

    /**
     * @param fileSizeKB
     *            the fileSizeKB to set
     */
    public void setFileSizeKB(String fileSizeKB) {
	this.fileSizeKB = fileSizeKB;
    }

    /**
     * @param businessType
     *            the businessType to set
     */
    public void setBusinessType(String businessType) {
	this.businessType = businessType;
    }

    /**
     * @param iconURLStr
     *            the iconURLStr to set
     */
    public void setIconURLStr(String iconURLStr) {
	this.iconURLStr = iconURLStr;
    }

    /**
     * @return the downloadURL
     */
    public String getDownloadURL() {
	return downloadURL;
    }

    /**
     * @param downloadURL
     *            the downloadURL to set
     */
    public void setDownloadURL(String downloadURL) {
	this.downloadURL = downloadURL;
    }

}
