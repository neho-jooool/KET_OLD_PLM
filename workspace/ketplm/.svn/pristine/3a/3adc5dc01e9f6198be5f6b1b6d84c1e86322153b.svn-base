package ext.ket.edm.service.internal;

import java.beans.PropertyVetoException;
import java.util.Vector;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.util.WTException;
import ext.ket.edm.entity.KETStamping;
import ext.ket.shared.log.Kogger;

public class StampFileDownUtil {

    private void getDownLoadUrl(KETStamping stamp) throws WTException {

	try {

	    if (stamp != null) {
		ContentHolder contentHolder = ContentHelper.service.getContents(stamp);
		Vector contentList = ContentHelper.getContentListAll(contentHolder);
		for (int i = 0; i < contentList.size(); i++) {

		    ContentItem contentItem = (ContentItem) contentList.elementAt(i);
		    Kogger.debug(getClass(), i + " contentItem = " + contentItem);

		    if (contentItem instanceof ApplicationData) {
			
			ApplicationData appData = (ApplicationData)contentItem;
			String fileName = appData.getFileName();
			String downURL = (ContentHelper.getDownloadURL((ContentHolder) stamp, appData)).toString();
			Kogger.debug(getClass(), "fileName = " + fileName);
			Kogger.debug(getClass(), "downURL = " + downURL);
		    }
		}
	    }

	} catch (PropertyVetoException pve) {
	    throw new WTException(pve);
	}
    }

}
