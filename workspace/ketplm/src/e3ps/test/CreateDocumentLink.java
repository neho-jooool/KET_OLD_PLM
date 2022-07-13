package e3ps.test;

import wt.fc.PersistenceHelper;
import wt.method.RemoteAccess;
import wt.part.WTPart;
import e3ps.common.util.CommonUtil;
import e3ps.dms.entity.KETDocumentProjectLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.project.E3PSProject;

public class CreateDocumentLink implements RemoteAccess{

	public static void main(String[] args) throws Exception {
		KETDocumentProjectLink link = new KETDocumentProjectLink();
		E3PSProject pjt = (E3PSProject)CommonUtil.getObject("e3ps.project.ProductProject:806550963");
		link.setProject(pjt);

		KETProjectDocument doc = (KETProjectDocument)CommonUtil.getObject("e3ps.dms.entity.KETProjectDocument:183761737");
		link.setDocument(doc);

		PersistenceHelper.manager.save(link);
	}
}
