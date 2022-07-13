/*
 * @(#) ObjectData.java  Create on 2004. 11. 16.
 * Copyright (c) e3ps. All rights reserverd
 */
package e3ps.common.obj;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Locale;
import java.util.Vector;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.FormatContentHolder;
import wt.content.URLData;
import wt.doc.WTDocument;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.locks.LockException;
import wt.locks.LockHelper;
import wt.org.WTPrincipalReference;
import wt.util.WTException;
import wt.vc.VersionControlException;
import wt.vc.VersionControlHelper;
import wt.vc.wip.WorkInProgressHelper;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import ext.ket.shared.log.Kogger;


/**
 * WTDocument, EPMDocument 객체의 일반정보를 볼때 사용한다.
 * 
 * @author Choi Seunghwan, skyprda@e3ps.com
 * @version 1.00, 2004. 11. 16.
 * @since 1.4
 * @see e3ps.part.beans.PartData
 */
public class ObjectData {
    private String             number;
    private String             description;
    private String             oid;
    private String             fileUrl;
    private String             fileName;
    private String             onlyFileName;
    private String             primary;
    private String             size;
    private ContentItem        item;
    private boolean            isApplicationData;
    private RevisionControlled rc;
    private Object[]           secondary;
    private String             applicateoid;

    /**
     * RevisionControlled 객체의 정보를 추출한다.
     * 
     * @param rc
     * @param flag
     *            if ture, Content 정보 포함
     * @throws Exception
     */
    public ObjectData(RevisionControlled rc, boolean includeContent) throws Exception {
	initialize(rc, includeContent);
    }

    /**
     * Content를 포함한 모든 정보를 추출할경우 사용한다.
     * 
     * @param rc
     * @throws Exception
     */
    public ObjectData(RevisionControlled rc) throws Exception {
	this(rc, true);
    }

    private void initialize(RevisionControlled rc, boolean includeContent) throws Exception {
	this.rc = rc;

	if (rc instanceof WTDocument) {
	    WTDocument doc = (WTDocument) rc;
	    number = doc.getNumber();
	    oid = CommonUtil.getOIDString(doc);
	    description = doc.getDescription();

	    if (includeContent) setContentInfo(rc, true);
	    //                setContentInfo(rc, isAuth(doc));
	}
	else if (rc instanceof EPMDocument) {
	    EPMDocument doc = (EPMDocument) rc;
	    number = doc.getNumber();
	    oid = CommonUtil.getOIDString(doc);
	    description = doc.getDescription();
	    if (includeContent) setContentInfo(rc, true);
	}
    }

    private void setContentInfo(RevisionControlled rc, boolean _isView) throws WTException, PropertyVetoException, IOException {
	FormatContentHolder fch = (FormatContentHolder) ContentHelper.service.getContents((ContentHolder) rc);
	item = fch.getPrimary();
	if (item != null) {
	    if (item instanceof ApplicationData) {
		isApplicationData = true;
		ApplicationData appData = (ApplicationData) item;
		applicateoid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
		// onlyFileName = appData.getFileName();
		size = appData.getFileSizeKB() + " KB";
		//                URL url = ContentHelper.getDownloadURL(fch, appData);

		if (_isView) fileUrl = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(rc) + "&cioids=" + applicateoid + "&role=" + appData.getRole().toString();
		//                    fileUrl ="/plm/servlet/e3ps/DownLoadContentServlet?" +  DownLoadContentServlet.HOLDEROID + "="+CommonUtil.getOIDString(rc)+"&" + DownLoadContentServlet.ADOID + "="+applicateoid;
		else fileUrl = "javascript:alert('권한이 없습니다')";
		fileName = appData.getFileName();

		// String codebase =
		// WTProperties.getServerCodebase().toString();
		// fileName =
		// codebase+"servlet/WindchillAuthGW/wt.enterprise.FormProcessor/invokeAction?Action=GetContent&oid="+CommonUtil.getFullOID(rc)+"&u8=1";
		// primary = "<a href=\"JavaScript:openWindow('" + fileName +
		// "', '', 200, 100)\">" + onlyFileName + "</a>";
	    }
	    else if (item instanceof URLData) {
		URLData urldata = (URLData) item;
		primary = "<a href=" + urldata.getUrlLocation() + " target=blank>" + urldata.getUrlLocation() + "</a>";
	    }
	}
	Vector vec = ContentHelper.getContentList(fch);
	if (vec != null && vec.size() > 0) {
	    this.secondary = new Object[vec.size()];
	    for (int i = vec.size() - 1; i > -1; i--) {
		ApplicationData appData = (ApplicationData) vec.get(i);
		if (!appData.getRole().equals(ContentRoleType.SECONDARY)) {
		    continue;
		}
		//                URL url = ContentHelper.getDownloadURL(fch, appData);
		String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();

		String url = "javascript:alert('권한이 없습니다')";
		if (_isView) url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(rc) + "&cioids=" + applicateoid + "&role=" + appData.getRole().toString();
		//		    url = "/plm/servlet/e3ps/DownLoadContentServlet?" + DownLoadContentServlet.HOLDEROID + "=" + CommonUtil.getOIDString(rc) + "&" + DownLoadContentServlet.ADOID + "=" + appDataOid;
		secondary[i] = new Object[] { url, appData.getFileName(), appDataOid };
		// secondary[i] = "<a href=" + CharUtil.E2K(url.toString()) +
		// ">" + appData.getFileName() + "</a>";
	    }
	}
    }

    /**
     * @return Returns the checkOuter.
     */
    public String getCheckOuter() {
	if (isChecked()) {
	    try {
		WTPrincipalReference user = LockHelper.getLockerReference(rc);
		return "<A HREF=mailto:" + user.getEMail() + ">" + user.getFullName() + "</A>" + " 님에 의해 체크아웃";
	    } catch (LockException e) {
		Kogger.error(getClass(), e);
	    }
	}
	return "";
    }

    /**
     * @return Returns the createDate.
     */
    public String getCreateDate() {
	return PersistenceHelper.getCreateStamp(rc).toString();
    }

    /**
     * @return Returns the creator.
     */
    public String getCreator() {
	String creator = "";
	try {
	    WTPrincipalReference user = VersionControlHelper.getVersionCreator(rc);
	    creator = "<A HREF=mailto:" + user.getEMail() + ">" + user.getFullName() + "</A>";
	} catch (VersionControlException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return creator;
    }

    /**
     * 최종 수정일을 지정된 포맷으로 출력한다.
     * 
     * @return Returns the date.
     */
    public String getDate() {
	return DateUtil.getDateString(PersistenceHelper.getModifyStamp(rc), "a");
    }

    /**
     * @return Returns the fileName.
     */
    public String getFileName() {
	return fileName;
    }

    /**
     * @return Returns the icon.
     * @throws WTException
     */
    public String getIcon() throws WTException {
	return E3PSContentHelper.service.getIconImgTag(rc);
    }

    /**
     * @return Returns the isApplicationData.
     */
    public boolean isApplicationData() {
	return isApplicationData;
    }

    /**
     * @return Returns the isChecked.
     */
    public boolean isChecked() {
	try {
	    return WorkInProgressHelper.isCheckedOut(rc);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return false;
    }

    /**
     * @return Returns the item.
     */
    public ContentItem getItem() {
	return item;
    }

    /**
     * @return Returns the location.
     */
    public String getLocation() {
	return rc.getLocation();
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
	return rc.getName();
    }

    public String getNumber() {
	return number;
    }

    public String getOid() {
	return oid;
    }

    /**
     * @return Returns the onlyFileName.
     */
    public String getOnlyFileName() {
	return onlyFileName;
    }

    /**
     * @return Returns the primary.
     */
    public String getPrimary() {
	return primary;
    }

    /**
     * @return Returns the size.
     */
    public String getSize() {
	return size;
    }

    /**
     * @return Returns the state.
     */
    public String getState() {
	return rc.getLifeCycleState().getDisplay(Locale.KOREA);
    }

    /**
     * @return Returns the type.
     */
    public String getType() {
	return rc.getDisplayType().getLocalizedMessage(Locale.KOREA);
    }

    /**
     * @return Returns the updateDate.
     */
    public String getUpdateDate() {
	return PersistenceHelper.getModifyStamp(rc).toString();
    }

    /**
     * @return Returns the updator.
     */
    public String getUpdator() {
	try {
	    WTPrincipalReference user = VersionControlHelper.getIterationModifier(rc);
	    return "<A HREF=mailto:" + user.getEMail() + ">" + user.getFullName() + "</A>";
	} catch (VersionControlException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return "";
    }

    /**
     * @return Returns the version.
     * @throws Exception
     */
    public String getVersion() throws Exception {
	return ObjectUtil.getVersion(rc);
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
	return description;
    }

    public Object[] getSecondary() {
	return secondary;
    }

    public String getFileUrl() {
	return fileUrl;
    }

    public String getApplicateOid() {
	return applicateoid;
    }

    private boolean isAuth(WTDocument _doc) {
	// 문서 파일 내용보기 권한 추가 (2006/02/09 shchoi)
	// boolean isAuth = AccessControlUtil.isMyObject(doc);
	// if (!isAuth)
	// {

	try {
	    if (CommonUtil.isAdmin()) {
		return true;
	    }
	} catch (Exception e1) {
	    Kogger.error(getClass(), e1);
	}

	boolean isAuth = false;

	// }
	return isAuth;
    }
}
