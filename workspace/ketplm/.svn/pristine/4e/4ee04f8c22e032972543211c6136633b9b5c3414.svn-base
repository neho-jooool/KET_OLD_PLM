package e3ps.common.content;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import org.drools.core.util.StringUtils;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.FormatContentHolder;
import wt.content.URLData;
import wt.epm.EPMDocument;
import wt.fc.QueryResult;
import wt.httpgw.URLFactory;
import wt.util.WTException;
import wt.util.WTProperties;
import e3ps.common.util.CommonUtil;
import ext.ket.shared.log.Kogger;

// import e3ps.cpc.DistributeProcess;

public class ContentUtil {
    /**
     * Get PrimaryContent from FormatContentHolder
     * 
     * @param : FormatContentHolder
     * @return : ContentInfo
     * @since : 2005.08
     */
    public static ContentInfo getPrimaryContent(FormatContentHolder holder) throws Exception {
	ContentInfo info = null;
	try {
	    QueryResult result = ContentHelper.service.getContentsByRole(holder, ContentRoleType.PRIMARY);
	    while (result.hasMoreElements()) {
		ContentItem item = (ContentItem) result.nextElement();
		info = getContentInfo(holder, item);
		// if ( holder instanceof WTObject ) {
		// info.setIconURLStr(E3PSContentHelper.service.getIconImgTag((WTObject)holder));
		// }
	    }
	} catch (Exception e) {
	    Kogger.error(ContentUtil.class, e);
	}
	return info;
    }

    /**
     * Get SecondaryContents from ContentHolder
     * 
     * @param : FormatContentHolder
     * @return : Vector(ContentInfo)
     * @since : 2005.08
     */
    public static Vector getSecondaryContents(ContentHolder holder) {
	Vector returnVec = new Vector();
	try {
	    QueryResult result = ContentHelper.service.getContentsByRole(holder, ContentRoleType.SECONDARY);
	    while (result.hasMoreElements()) {
		ContentItem item = (ContentItem) result.nextElement();
		returnVec.add(getContentInfo(holder, item));
	    }
	} catch (Exception e) {
	    Kogger.error(ContentUtil.class, e);
	}
	return returnVec;
    }

    /**
     * 함수명 : getSecondaryContents 설명 :
     * 
     * @param holder
     * @return 작성자 : 김종호 작성일자 : 2013. 6. 7.
     */
    public static Vector getSecondaryContents(ContentHolder holder, String desc) {
	Vector returnVec = new Vector();
	try {
	    QueryResult result = ContentHelper.service.getContentsByRole(holder, ContentRoleType.SECONDARY);
	    while (result.hasMoreElements()) {
		ContentItem item = (ContentItem) result.nextElement();
		if (desc == null && item.getDescription() == null) {
		    returnVec.add(getContentInfo(holder, item));
		} else if (desc != null && item.getDescription() != null && item.getDescription().equals(desc)) {
		    returnVec.add(getContentInfo(holder, item));
		}
	    }
	} catch (Exception e) {
	    Kogger.error(ContentUtil.class, e);
	}
	return returnVec;
    }

    public static ContentInfo getContentInfo(ContentHolder holder, ContentItem item) throws WTException {
	ContentInfo info = null;
	if (item instanceof URLData) {
	    URLData url = (URLData) item;
	    info = new ContentInfo();
	    info.setType("URL");
	    info.setContentOid(url.toString());
	    try {
		info.setDownURL(new URL(url.getUrlLocation()));
	    } catch (MalformedURLException e) {
		Kogger.error(ContentUtil.class, e);
	    }
	    info.setName(url.getUrlLocation());
	    info.setIconURLStr(getContentIconStr(url));
	    info.setDescription(url.getDescription());
	} else if (item instanceof ApplicationData) {
	    ApplicationData file = (ApplicationData) item;
	    info = new ContentInfo();
	    info.setType("FILE");
	    info.setContentOid(file.toString());

	    String fileName = file.getFileName();

	    if (holder instanceof EPMDocument) {
		fileName = wt.epm.util.EPMContentHelper.getContentDisplayName((wt.epm.EPMDocument) holder, item);
	    }

	    info.setName(fileName);
	    info.setDescription(file.getDescription());

	    URL url = null;

	    // String drm = ConfigImpl.getInstance().getString("DRM");
	    // if("true".equals(drm.trim().toLowerCase())) {
	    // String context = "";
	    // try {
	    // context = WTProperties.getLocalProperties().getProperty("wt.server.codebase");
	    // } catch (IOException e1) {
	    // Kogger.error(ContentUtil.class, e1);
	    // }
	    //
	    // try {
	    // url = new URL(context + "/servlet/e3ps/DownLoadContentServlet?"+DownLoadContentServlet.HOLDEROID +"=" +
	    // CommonUtil.getFullOIDString(holder) + "&" +
	    // DownLoadContentServlet.ADOID +"=" + CommonUtil.getFullOIDString(item));
	    // Kogger.debug(ContentUtil.class, url.toString());
	    // } catch (MalformedURLException e) {
	    // Kogger.error(ContentUtil.class, e);
	    // }
	    // }
	    // else {
	    // url = wt.content.ContentHelper.getDownloadURL ( holder , file );
	    // }

	    try {
		String context = WTProperties.getLocalProperties().getProperty("wt.server.codebase");
		url = new URL(context + "/servlet/AttachmentsDownloadDirectionServlet?oid=" + CommonUtil.getFullOIDString(holder)
		        + "&cioids=" + CommonUtil.getFullOIDString(item) + "&role=" + file.getRole().toString() + "&ketCustomFlag=Y");

	    } catch (IOException e1) {
		Kogger.error(ContentUtil.class, e1);
	    }
	    info.setDownURL(url);
	    info.setIconURLStr(getContentIconStr(file));
	    info.setFileSize(file.getFileSize());
	    info.setBusinessType(file.getBusinessType());
	}
	return info;
    }

    public static String getFileIconUrl(String tempFileName) throws WTException {
	URLFactory urlFac = new URLFactory();
	String baseUrlPath = urlFac.getBaseURL().getPath();
	int dot = tempFileName.lastIndexOf(".");
	String extStr = "";
	String iconStr = "";
	if (dot != -1) {
	    extStr = tempFileName.substring(dot + 1); // excludes "."
	    // Kogger.debug(getClass(), "########################"+extStr);
	    if (extStr.equalsIgnoreCase("exe"))
		iconStr = "portal/icon/fileicon/exe.gif";
	    else if (extStr.equalsIgnoreCase("doc") || extStr.equalsIgnoreCase("docx"))
		iconStr = "portal/icon/fileicon/doc.gif";
	    else if (extStr.equalsIgnoreCase("ppt") || extStr.equalsIgnoreCase("pptx"))
		iconStr = "portal/icon/fileicon/ppt.gif";
	    else if (extStr.equalsIgnoreCase("xls") || extStr.equalsIgnoreCase("xlsx"))
		iconStr = "portal/icon/fileicon/xls.gif";
	    else if (extStr.equalsIgnoreCase("csv"))
		iconStr = "portal/icon/fileicon/xls.gif";
	    else if (extStr.equalsIgnoreCase("txt"))
		iconStr = "portal/icon/fileicon/notepad.gif";
	    else if (extStr.equalsIgnoreCase("mpp"))
		iconStr = "portal/icon/fileicon/mpp.gif";
	    else if (extStr.equalsIgnoreCase("pdf"))
		iconStr = "portal/icon/fileicon/pdf.gif";
	    else if (extStr.equalsIgnoreCase("tif"))
		iconStr = "portal/icon/fileicon/tif.gif";
	    else if (extStr.equalsIgnoreCase("gif"))
		iconStr = "portal/icon/fileicon/gif.gif";
	    else if (extStr.equalsIgnoreCase("jpg"))
		iconStr = "portal/icon/fileicon/jpg.gif";
	    else if (extStr.equalsIgnoreCase("cc"))
		iconStr = "portal/icon/fileicon/ed.gif";
	    else if (extStr.equalsIgnoreCase("ed"))
		iconStr = "portal/icon/fileicon/ed.gif";
	    else if (extStr.equalsIgnoreCase("zip"))
		iconStr = "portal/icon/fileicon/zip.gif";
	    else if (extStr.equalsIgnoreCase("tar"))
		iconStr = "portal/icon/fileicon/zip.gif";
	    else if (extStr.equalsIgnoreCase("rar"))
		iconStr = "portal/icon/fileicon/zip.gif";
	    else if (extStr.equalsIgnoreCase("jar"))
		iconStr = "portal/icon/fileicon/zip.gif";
	    else if (extStr.equalsIgnoreCase("igs"))
		iconStr = "portal/icon/fileicon/epmall.gif";
	    else if (extStr.equalsIgnoreCase("pls"))
		iconStr = "portal/icon/fileicon/excess.gif";
	    else if (extStr.equalsIgnoreCase("pcb"))
		iconStr = "portal/icon/fileicon/pcb.gif";
	    else if (extStr.equalsIgnoreCase("sch"))
		iconStr = "portal/icon/fileicon/sch.gif";
	    else if (extStr.equalsIgnoreCase("dwg"))
		iconStr = "portal/icon/fileicon/dwg.gif";
	    else if (extStr.equalsIgnoreCase("dxf"))
		iconStr = "portal/icon/fileicon/dwg.gif";
	    else if (extStr.equalsIgnoreCase("drw"))
		iconStr = "portal/icon/fileicon/drw.gif";
	    else if (extStr.equalsIgnoreCase("catdrawing"))
		iconStr = "portal/icon/fileicon/catdrawing.gif";
	    else if (extStr.equalsIgnoreCase("prt") || extStr.equalsIgnoreCase("asm"))
		iconStr = "portal/icon/fileicon/prt.gif";
	    else if (extStr.equalsIgnoreCase("html"))
		iconStr = "portal/icon/fileicon/htm.gif";
	    else if (extStr.equalsIgnoreCase("htm"))
		iconStr = "portal/icon/fileicon/htm.gif";
	    else
		iconStr = "portal/icon/fileicon/generic.gif";
	} else {
	    return null;
	}

	return baseUrlPath + iconStr;
    }

    public static String getContentIconStr(ContentItem item) throws WTException {
	URLFactory urlFac = new URLFactory();
	String iconStr = "";
	String baseUrlPath = urlFac.getBaseURL().getPath();
	if (item instanceof URLData) {
	    iconStr = baseUrlPath + "portal/icon/fileicon/link.gif";
	} else if (item instanceof ApplicationData) {
	    ApplicationData data = (ApplicationData) item;

	    String tempFileName = data.getFileName();

	    // Kogger.debug(getClass(), "========> ContentUtil Class :: getContentIconStr Method :: FileName 1 : " + tempFileName );

	    if (tempFileName.equals("{$CAD_NAME}")) {
		// Kogger.debug(getClass(), "########################"+item.getHolderLink());
		if (item.getHolderLink() != null && item.getHolderLink().getContentHolder() != null) {
		    tempFileName = ((EPMDocument) item.getHolderLink().getContentHolder()).getCADName();
		}
		// Kogger.debug(getClass(), "========> ContentUtil Class :: getContentIconStr Method :: FileName 2 : " + tempFileName );
	    }

	    iconStr = getFileIconUrl(tempFileName);
	    if (iconStr == null || StringUtils.isEmpty(iconStr)) {
		return null;
	    }
	}
	iconStr = "<img src=\'" + iconStr + "\' border=0>";
	return iconStr;
    }

    /*
     * [PLM System 1차개선] 수정내용 : 파일 아이콘 URL String 리턴 (그리드에서 파일 다운로드 아이콘으로 사용) 수정일자 : 2013. 5. 31 수정자 : 오명재
     */
    public static String getContentIconPath(ContentItem item) throws WTException {
	URLFactory urlFac = new URLFactory();
	String iconStr = "";
	if (item instanceof URLData) {
	    iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/link.gif";
	} else if (item instanceof ApplicationData) {
	    ApplicationData data = (ApplicationData) item;

	    String extStr = "";
	    String tempFileName = data.getFileName();

	    if (tempFileName.equals("{$CAD_NAME}")) {

		if (item.getHolderLink() != null && item.getHolderLink().getContentHolder() != null) {
		    tempFileName = ((EPMDocument) item.getHolderLink().getContentHolder()).getCADName();
		}
	    }

	    int dot = tempFileName.lastIndexOf(".");

	    if (dot != -1)
		extStr = tempFileName.substring(dot + 1); // excludes "."

	    if (extStr.equalsIgnoreCase("exe"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/exe.gif";
	    else if (extStr.equalsIgnoreCase("doc") || extStr.equalsIgnoreCase("docx"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/doc.gif";
	    else if (extStr.equalsIgnoreCase("ppt") || extStr.equalsIgnoreCase("pptx"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/ppt.gif";
	    else if (extStr.equalsIgnoreCase("xls") || extStr.equalsIgnoreCase("xlsx"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/xls.gif";
	    else if (extStr.equalsIgnoreCase("csv"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/xls.gif";
	    else if (extStr.equalsIgnoreCase("txt"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/notepad.gif";
	    else if (extStr.equalsIgnoreCase("mpp"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/mpp.gif";
	    else if (extStr.equalsIgnoreCase("pdf"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/pdf.gif";
	    else if (extStr.equalsIgnoreCase("tif"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/tif.gif";
	    else if (extStr.equalsIgnoreCase("gif"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/gif.gif";
	    else if (extStr.equalsIgnoreCase("jpg"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/jpg.gif";
	    else if (extStr.equalsIgnoreCase("cc"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/ed.gif";
	    else if (extStr.equalsIgnoreCase("ed"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/ed.gif";
	    else if (extStr.equalsIgnoreCase("zip"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/zip.gif";
	    else if (extStr.equalsIgnoreCase("tar"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/zip.gif";
	    else if (extStr.equalsIgnoreCase("rar"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/zip.gif";
	    else if (extStr.equalsIgnoreCase("jar"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/zip.gif";
	    else if (extStr.equalsIgnoreCase("igs"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/epmall.gif";
	    else if (extStr.equalsIgnoreCase("pls"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/excess.gif";
	    else if (extStr.equalsIgnoreCase("pcb"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/pcb.gif";
	    else if (extStr.equalsIgnoreCase("sch"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/sch.gif";
	    else if (extStr.equalsIgnoreCase("dwg"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/dwg.gif";
	    else if (extStr.equalsIgnoreCase("dxf"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/dwg.gif";
	    else if (extStr.equalsIgnoreCase("drw"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/drw.gif";
	    else if (extStr.equalsIgnoreCase("catdrawing"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/catdrawing.gif";
	    else if (extStr.equalsIgnoreCase("prt") || extStr.equalsIgnoreCase("asm"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/prt.gif";
	    else if (extStr.equalsIgnoreCase("html"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/htm.gif";
	    else if (extStr.equalsIgnoreCase("htm"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/htm.gif";
	    else
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/generic.gif";
	} else {
	    return null;
	}

	return iconStr;
    }

    /*
     * [PLM System 1차개선] 수정내용 : 파일 아이콘 URL String 리턴 (그리드에서 파일 다운로드 아이콘으로 사용) 수정일자 : 2013. 7. 13 수정자 : 오명재
     */
    public static String getContentIconPath(String extStr) throws WTException {

	URLFactory urlFac = new URLFactory();
	String iconStr = "";

	if (extStr != null) {
	    if (extStr.equalsIgnoreCase("exe"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/exe.gif";
	    else if (extStr.equalsIgnoreCase("doc") || extStr.equalsIgnoreCase("docx"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/doc.gif";
	    else if (extStr.equalsIgnoreCase("ppt") || extStr.equalsIgnoreCase("pptx"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/ppt.gif";
	    else if (extStr.equalsIgnoreCase("xls") || extStr.equalsIgnoreCase("xlsx"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/xls.gif";
	    else if (extStr.equalsIgnoreCase("csv"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/xls.gif";
	    else if (extStr.equalsIgnoreCase("txt"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/notepad.gif";
	    else if (extStr.equalsIgnoreCase("mpp"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/mpp.gif";
	    else if (extStr.equalsIgnoreCase("pdf"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/pdf.gif";
	    else if (extStr.equalsIgnoreCase("tif"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/tif.gif";
	    else if (extStr.equalsIgnoreCase("gif"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/gif.gif";
	    else if (extStr.equalsIgnoreCase("jpg"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/jpg.gif";
	    else if (extStr.equalsIgnoreCase("cc"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/ed.gif";
	    else if (extStr.equalsIgnoreCase("ed"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/ed.gif";
	    else if (extStr.equalsIgnoreCase("zip"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/zip.gif";
	    else if (extStr.equalsIgnoreCase("tar"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/zip.gif";
	    else if (extStr.equalsIgnoreCase("rar"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/zip.gif";
	    else if (extStr.equalsIgnoreCase("jar"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/zip.gif";
	    else if (extStr.equalsIgnoreCase("igs"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/epmall.gif";
	    else if (extStr.equalsIgnoreCase("pls"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/excess.gif";
	    else if (extStr.equalsIgnoreCase("pcb"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/pcb.gif";
	    else if (extStr.equalsIgnoreCase("sch"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/sch.gif";
	    else if (extStr.equalsIgnoreCase("dwg"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/dwg.gif";
	    else if (extStr.equalsIgnoreCase("dxf"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/dwg.gif";
	    else if (extStr.equalsIgnoreCase("drw"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/drw.gif";
	    else if (extStr.equalsIgnoreCase("catdrawing"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/catdrawing.gif";
	    else if (extStr.equalsIgnoreCase("prt") || extStr.equalsIgnoreCase("asm"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/prt.gif";
	    else if (extStr.equalsIgnoreCase("html"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/htm.gif";
	    else if (extStr.equalsIgnoreCase("htm"))
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/htm.gif";
	    else
		iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/generic.gif";
	} else
	    iconStr = urlFac.getBaseURL().getPath() + "portal/icon/fileicon/generic.gif";

	return iconStr;
    }
}
