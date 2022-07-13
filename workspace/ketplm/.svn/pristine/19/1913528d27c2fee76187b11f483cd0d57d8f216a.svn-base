package ext.ket.shared.content.service;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.io.FilenameUtils;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.URLData;
import wt.httpgw.URLFactory;
import wt.services.ServiceFactory;
import wt.util.WTException;
import wt.util.WTProperties;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.log.Kogger;

/**
 * @클래스명 : ContentHelper
 * @작성자 : Jason, Han
 * @작성일 : 2014. 6. 27.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class KETContentHelper {

    public static final KETContentService service = ServiceFactory.getService(KETContentService.class);

    public static final KETContentHelper  manager = new KETContentHelper();

    @SuppressWarnings("unchecked")
    public ContentDTO getPrimaryContent(ContentHolder holder) {

	ContentDTO broker = null;
	Vector<ContentItem> result = null;
	try {
	    holder = ContentHelper.service.getContents(holder);
	    result = ContentHelper.getContentListAll(holder);
	    Iterator<ContentItem> iter = result.iterator();
	    while (iter.hasNext()) {
		ContentItem item = iter.next();
		if (item.getRole() == ContentRoleType.PRIMARY) {
		    broker = new ContentDTO(holder, item);
		    break;
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return broker;
    }

    /**
     * @param holder
     * @return
     */
    public ArrayList<ContentDTO> getSecondaryContents(ContentHolder holder) {
	ArrayList<ContentDTO> arr = new ArrayList<ContentDTO>();
	try {
	    holder = ContentHelper.service.getContents(holder);
	    @SuppressWarnings("unchecked")
	    Vector<ContentItem> result = ContentHelper.getContentListAll(holder);
	    Iterator<ContentItem> iter = result.iterator();
	    while (iter.hasNext()) {
		ContentItem item = iter.next();
		if (item.getRole() == ContentRoleType.SECONDARY) {
		    arr.add(new ContentDTO(holder, item));
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return arr;
    }

    /**
     * @param holder
     * @return
     */
    public ArrayList<ContentDTO> getContents(ContentHolder holder) {
	ArrayList<ContentDTO> arr = new ArrayList<ContentDTO>();
	try {
	    holder = ContentHelper.service.getContents(holder);
	    @SuppressWarnings("unchecked")
	    Vector<ContentItem> result = ContentHelper.getContentListAll(holder);
	    Iterator<ContentItem> iter = result.iterator();
	    while (iter.hasNext()) {
		ContentItem item = (ContentItem) iter.next();
		arr.add(new ContentDTO(holder, item));
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (PropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}
	return arr;
    }

    /**
     * @param item
     * @return
     * @throws WTException
     * @throws IOException
     */
    public String getContentIconStr(ContentItem item) throws WTException, IOException {
	String iconStr = "";
	URLFactory urlFac = new URLFactory();
	WTProperties prop = WTProperties.getServerProperties();
	String wthome = prop.getProperty("wt.home");

	if (item instanceof URLData) {
	    iconStr = urlFac.getBaseURL().getPath() + "extcore/images/fileicon/link.gif";
	} else if (item instanceof ApplicationData) {
	    ApplicationData data = (ApplicationData) item;
	    String extension = FilenameUtils.getExtension(data.getFileName());
	    iconStr = urlFac.getBaseURL().getPath();
	    String testIconStr = wthome + "\\codebase\\extcore\\images\\fileicon\\IC" + extension.toUpperCase() + ".gif";
	    iconStr += "extcore/images/fileicon/IC" + extension.toUpperCase() + ".gif";
	    File test = new File(testIconStr);
	    if (!test.exists())
		iconStr = urlFac.getBaseURL().getPath() + "extcore/images/fileicon/ICGEN.gif";
	}
	return iconStr;
    }
}
