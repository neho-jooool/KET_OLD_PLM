package e3ps.dms.service.internal;

import java.util.HashMap;

import wt.content.ContentHelper;
import wt.content.ContentHolder;
import e3ps.common.util.CommonUtil;
import e3ps.dms.entity.KETTechnicalDocument;

public class TechdocUpload {
	public void disignFileUpload(HashMap<String, String> paramMap) {
		try {
			String oid = paramMap.get("oid");
			String ip = paramMap.get("ip");
			String userOid = paramMap.get("userOid");
			KETTechnicalDocument dms = (KETTechnicalDocument) CommonUtil.getObject(oid);

			String lastVersion = dms.getVersionInfo().getIdentifier().getValue();
			String documentNumber = dms.getNumber();
			String filePath = documentNumber + "\\" + lastVersion;

			ContentHolder contentHolder = ContentHelper.service.getContents(dms);
			TechDocFileUtil docup = new TechDocFileUtil();
			docup.getFile(contentHolder, filePath, ip, userOid);
		} catch (Exception e) {

		}
	}
}
