package e3ps.wfm.dao;

import java.util.ArrayList;

import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.LifeCycleTemplate;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.session.SessionHelper;
import wt.team.TeamHelper;
import wt.team.TeamTemplate;
import wt.team.TeamTemplateReference;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.ManageSequence;
import e3ps.common.util.WCUtil;
import e3ps.wfm.entity.KETWfmMultiApprovalRequest;
import e3ps.wfm.entity.KETWfmMultiReqDocLink;
import e3ps.wfm.util.WFMProperties;
import ext.ket.shared.log.Kogger;

public class WfmHelper implements RemoteAccess {

	public static final WfmHelper manager = new WfmHelper();
	static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

	public void createDocLink(String docOid, KETWfmMultiApprovalRequest multiReq)
			throws Exception {

		if (!SERVER) {
			Class argTypes[] = new Class[] { String.class,
					KETWfmMultiApprovalRequest.class };
			Object args[] = new Object[] { docOid, multiReq };
			Object obj = null;

			try {
				obj = RemoteMethodServer.getDefault().invoke("createDocLink",
						WfmHelper.class.getName(), null, argTypes, args);
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}
		}

		Transaction trs = new Transaction();

		try {
			trs.start();

			KETWfmMultiReqDocLink multiDoc = new KETWfmMultiReqDocLink();

			WTDocument wtDoc = (WTDocument) CommonUtil.getObject(docOid);

			if (!wtDoc.getLifeCycleTemplate().getName().equals("KET_COMMON_LC")) {
				LifeCycleTemplate LCtemplate = LifeCycleHelper.service
						.getLifeCycleTemplate("KET_COMMON_LC", WCUtil
								.getPDMLinkProduct().getContainerReference());

				if (LCtemplate != null) {
					wtDoc = (WTDocument) LifeCycleHelper.service.reassign(
							(LifeCycleManaged) wtDoc,
							LCtemplate.getLifeCycleTemplateReference());
				}
			}

			multiDoc.setDoc(wtDoc);
			multiDoc.setRequest(multiReq);
			PersistenceHelper.manager.save(multiDoc);

			trs.commit();
			trs = null;
		} catch (Exception e) {
			Kogger.error(getClass(), e);
			trs.rollback();
		} finally {
			if (trs != null) {
				trs = null;
			}
		}
	}

	public String createMultiReq(ArrayList target) throws Exception {
		if (!SERVER) {
			Class argTypes[] = new Class[] { ArrayList.class };
			Object args[] = new Object[] { target };
			Object obj = null;

			try {
				obj = RemoteMethodServer.getDefault().invoke("createMultiReq",
						WfmHelper.class.getName(), null, argTypes, args);
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}
		}

		String oid = "";
		String title = target.get(0).toString();
		String desc = target.get(1).toString();
		String[] docoid = (String[]) target.get(2);

		Transaction trs = new Transaction();

		try {
			trs.start();

			KETWfmMultiApprovalRequest multiReq = new KETWfmMultiApprovalRequest();
			multiReq.setReqName(title);
			multiReq.setDescription(desc);
			multiReq.setOwner(SessionHelper.manager.getPrincipalReference());
			multiReq.setCreator(SessionHelper.manager.getPrincipalReference());
			multiReq.setContainer(WCUtil.getPDMLinkProduct());
			TeamTemplate tTemplate = TeamHelper.service.getTeamTemplate(WCUtil
					.getPDMLinkProduct().getContainerReference(), "Default");
			multiReq.setTeamTemplateId(TeamTemplateReference
					.newTeamTemplateReference(tTemplate));

			String reqNumber = "WFM-"
					+ DateUtil.getCurrentDateString("all").substring(2, 4)
					+ DateUtil.getCurrentDateString("all").substring(5, 7)
					+ "-";
			reqNumber += ManageSequence.getSeqNo(reqNumber, "000",
					"KETWfmMultiApprovalRequest", "reqNumber");
			multiReq.setReqNumber(reqNumber);

			Folder folder = FolderHelper.service.getFolder("/Default",
					WCUtil.getWTContainerRef());
			FolderHelper.assignLocation((FolderEntry) multiReq, folder);

			String templateName = WFMProperties.getInstance().getString(
					"wfm.template.common");
			LifeCycleTemplate commonTemplate = LifeCycleHelper.service
					.getLifeCycleTemplate(templateName, WCUtil
							.getSiteContainer().getContainerReference());
			LifeCycleHelper.setLifeCycle((LifeCycleManaged) multiReq,
					commonTemplate);

			multiReq = (KETWfmMultiApprovalRequest) PersistenceHelper.manager
					.save(multiReq);

			oid = CommonUtil.getFullOIDString(multiReq);

			for (int i = 0; i < docoid.length; i++) {
				createDocLink(docoid[i], multiReq);
			}

			trs.commit();
			trs = null;
		} catch (Exception e) {
			Kogger.error(getClass(), e);
			trs.rollback();
		} finally {
			if (trs != null) {
				trs = null;
			}
		}
		return "";
	}
}
