/*
 * bcwti
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 * ecwti
 */

package e3ps.edm;

import java.io.Serializable;

import org.apache.commons.io.FilenameUtils;

import wt.epm.EPMAuthoringAppType;
import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.epm.EPMDocumentMasterIdentity;
import wt.epm.EPMDocumentType;
import wt.epm.structure.EPMReferenceLink;
import wt.fc.IdentityHelper;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import ext.ket.shared.log.Kogger;

/**
 * <p>
 * Use the <code>newStandardEDMDocumentService</code> static factory method(s), not the <code>StandardEDMDocumentService</code> constructor, to construct instances of this class.
 * Instances must be constructed using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * @version 1.0
 **/

public class StandardEDMDocumentService extends StandardManager implements EDMDocumentService, Serializable {

    /**
     * 
     */
    private static final long   serialVersionUID = 8217396252666079207L;
    private static final String CLASSNAME	= StandardEDMDocumentService.class.getName();

    /**
     * Returns the conceptual (modeled) name for the class. <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @deprecated
     * @return String
     **/
    public String getConceptualClassname() {

	return CLASSNAME;
    }

    /**
     * @exception wt.services.ManagerException
     **/
    @Override
    protected synchronized void performStartupProcess() throws ManagerException {

	super.performStartupProcess();

	System.out.println("Add EDMDocumentService Event ...");
	/* Change: Add event listeners here */
	e3ps.edm.listener.EDMEventListener listener = new e3ps.edm.listener.EDMEventListener(this.getConceptualClassname());
	getManagerService().addEventListener(listener, wt.vc.VersionControlServiceEvent.generateEventKey(wt.vc.VersionControlServiceEvent.NEW_VERSION));
	getManagerService().addEventListener(listener, wt.lifecycle.LifeCycleServiceEvent.generateEventKey(wt.lifecycle.LifeCycleServiceEvent.STATE_CHANGE));
	getManagerService().addEventListener(listener, wt.vc.wip.WorkInProgressServiceEvent.generateEventKey(wt.vc.wip.WorkInProgressServiceEvent.POST_CHECKIN));
	getManagerService().addEventListener(listener, wt.fc.PersistenceManagerEvent.generateEventKey(wt.fc.PersistenceManagerEvent.PRE_DELETE));
	getManagerService().addEventListener(listener, wt.fc.PersistenceManagerEvent.generateEventKey(wt.fc.PersistenceManagerEvent.PRE_STORE));
	getManagerService().addEventListener(listener, wt.fc.PersistenceManagerEvent.generateEventKey(wt.fc.PersistenceManagerEvent.PRE_MODIFY));
	getManagerService().addEventListener(listener, wt.fc.PersistenceManagerEvent.generateEventKey(wt.fc.PersistenceManagerEvent.POST_STORE));
	getManagerService().addEventListener(listener, wt.fc.PersistenceManagerEvent.generateEventKey(wt.fc.PersistenceManagerEvent.POST_MODIFY));
	getManagerService().addEventListener(listener, wt.fc.PersistenceManagerEvent.generateEventKey(wt.fc.PersistenceManagerEvent.POST_DELETE));
	getManagerService().addEventListener(listener, wt.vc.wip.WorkInProgressServiceEvent.generateEventKey(wt.vc.wip.WorkInProgressServiceEvent.PRE_CHECKIN));
	// getManagerService().addEventListener(listener, wt.vc.VersionControlServiceEvent.generateEventKey(wt.vc.VersionControlServiceEvent.NEW_ITERATION));
	getManagerService().addEventListener(listener, wt.vc.wip.WorkInProgressServiceEvent.generateEventKey(wt.vc.VersionControlServiceEvent.NEW_ITERATION));
	// getManagerService().addEventListener(listener, wt.vc.wip.WorkInProgressServiceEvent.generateEventKey(wt.vc.VersionControlServiceEvent.p));
	getManagerService().addEventListener(listener, wt.vc.wip.WorkInProgressServiceEvent.generateEventKey(wt.vc.wip.WorkInProgressServiceEvent.POST_CHECKOUT));
	getManagerService().addEventListener(listener, wt.vc.wip.WorkInProgressServiceEvent.generateEventKey(wt.vc.wip.WorkInProgressServiceEvent.PRE_CHECKOUT));
    }

    /**
     * Default factory for the class.
     * 
     * @return StandardEDMDocumentService
     * @exception wt.util.WTException
     **/
    public static StandardEDMDocumentService newStandardEDMDocumentService() throws WTException {

	StandardEDMDocumentService instance = new StandardEDMDocumentService();
	instance.initialize();
	return instance;
    }

    @Override
    public void addEPMDocumentNumberSuffix(EPMDocument epm) {

	Kogger.debug(getClass(), "");
	Kogger.debug(getClass(), "=========================== addEPMDocumentNumberSuffix START ===============================");
	SessionContext old_session = SessionContext.newContext();
	try {
	    String version = epm.getVersionIdentifier().getValue();
	    String iteration = epm.getIterationIdentifier().getValue();
	    Kogger.debug(getClass(), "addEPMDocumentNumberSuffix : CHECK EPMDocument version : " + version + " / iteration : " + iteration);
	    if (!"0".equals(version) && !"0".equals(iteration))
		return;
	    /****************************************
	     * check case
	     * 1. EPMAuthoringAppType : PROE
	     * 2. EPMDocumentType :
	     * - CADASSEMBLY / CADCOMPONENT - _3D
	     * - CADDRAWING - _2D
	     ***************************************/
	    SessionHelper.manager.setAdministrator();
	    String suffix_2D = "_2D";
	    String suffix_3D = "_3D";
	    EPMAuthoringAppType authoringAppType = epm.getAuthoringApplication();
	    EPMDocumentType documentType = epm.getDocType();
	    if ("PROE".equals(authoringAppType.toString())) {
		if ("CADASSEMBLY".equals(documentType.toString()) || "CADCOMPONENT".equals(documentType.toString()) || "CADDRAWING".equals(documentType.toString())) {
		    String documentNumber = FilenameUtils.getBaseName(epm.getNumber());
		    Kogger.debug(getClass(), "addEPMDocumentNumberSuffix : CHECK EPMDocument Number:" + documentNumber + " / " + authoringAppType.toString() + " / " + documentType.toString());

		    if ("CADDRAWING".equals(documentType.toString())) {
			return;
			// String part_no = "";
			// String part_name = "";
			// EPMDocumentMaster epm_3d = getRefEPMDocumentMaster(epm);
			// if (epm_3d != null) {
			// Kogger.debug(getClass(), "addEPMDocumentNumberSuffix : FOUND 3D EPMDocument");
			// part_no = epm_3d.getNumber();
			// part_name = epm_3d.getName();
			// // 3D도면의 접미사 체크
			// if (part_no.indexOf(suffix_3D) != -1) {
			// int len = part_no.length();
			// int pos = part_no.lastIndexOf(suffix_3D);
			// if (len == pos + 3) {
			// part_no = part_no.substring(0, part_no.lastIndexOf(suffix_3D));
			// }
			// }
			// }
			// Kogger.debug(getClass(), "addEPMDocumentNumberSuffix : 3D EPMDocument PART_NO : " + part_no);
			// if (StringUtil.checkString(part_no)) {
			// // 접미사 체크
			// // if (part_no.indexOf(suffix_2D) != -1) {
			// // Kogger.debug(getClass(), "addEPMDocumentNumberSuffix : EPMDocument PART_NO has suffix_2D / " + part_no);
			// // int len = part_no.length();
			// // int pos = part_no.lastIndexOf(suffix_2D);
			// // if (len == pos + 3) {
			// // part_no = part_no.substring(0, part_no.lastIndexOf(suffix_2D));
			// // }
			// // }
			// String epmNumberSuffix = part_no + suffix_2D;
			// // 도면번호 업데이트
			// if (!epmNumberSuffix.equals(documentNumber)) {
			// Kogger.debug(getClass(), "addEPMDocumentNumberSuffix : EPMDocument number update from :: " + documentNumber);
			// EPMDocumentMaster master = (EPMDocumentMaster) epm.getMaster();
			// EPMDocumentMasterIdentity epmidentity = (EPMDocumentMasterIdentity) master.getIdentificationObject();
			// epmidentity.setNumber(epmNumberSuffix);
			// epmidentity.setName(part_name);
			// master = (EPMDocumentMaster) IdentityHelper.service.changeIdentity(master, epmidentity);
			// Kogger.debug(getClass(), "addEPMDocumentNumberSuffix : EPMDocument number update to :: " + epmNumberSuffix);
			// }
			// }
		    } else if ("CADASSEMBLY".equals(documentType.toString()) || "CADCOMPONENT".equals(documentType.toString())) {
			String part_no = documentNumber;
			// 접미사를 입력해놓은 경우
			if (part_no.indexOf(suffix_3D) != -1) {
			    Kogger.debug(getClass(), "addEPMDocumentNumberSuffix : EPMDocument PART_NO has suffix_3D / " + part_no);
			    int len = part_no.length();
			    int pos = part_no.lastIndexOf(suffix_3D);
			    if (len == pos + 3) {
				part_no = part_no.substring(0, part_no.lastIndexOf(suffix_3D));
			    }
			}
			String epmNumberSuffix = part_no + suffix_3D;
			// 도면번호 업데이트
			if (!epmNumberSuffix.equals(documentNumber)) {
			    Kogger.debug(getClass(), "addEPMDocumentNumberSuffix : EPMDocument number update from :: " + documentNumber);
			    EPMDocumentMaster master = (EPMDocumentMaster) epm.getMaster();
			    EPMDocumentMasterIdentity epmidentity = (EPMDocumentMasterIdentity) master.getIdentificationObject();
			    epmidentity.setNumber(epmNumberSuffix);
			    master = (EPMDocumentMaster) IdentityHelper.service.changeIdentity(master, epmidentity);
			    Kogger.debug(getClass(), "addEPMDocumentNumberSuffix : EPMDocument number update to :: " + epmNumberSuffix);
			}
			EPMDocumentMaster epm_2d = getRefEPMDocumentMaster(epm);
			if (epm_2d != null && "CADDRAWING".equals(epm_2d.getDocType().toString())) {
			    Kogger.debug(getClass(), "addEPMDocumentNumberSuffix : FOUND 2D EPMDocument");
			    String epmNumberSuffix_2D = part_no + suffix_2D;
			    Kogger.debug(getClass(), "addEPMDocumentNumberSuffix : EPMDocument 2D number update from :: " + epm_2d.getNumber());
			    EPMDocumentMasterIdentity epmidentity = (EPMDocumentMasterIdentity) epm_2d.getIdentificationObject();
			    epmidentity.setNumber(epmNumberSuffix_2D);
			    epmidentity.setName(epm.getName());
			    epm_2d = (EPMDocumentMaster) IdentityHelper.service.changeIdentity(epm_2d, epmidentity);
			    Kogger.debug(getClass(), "addEPMDocumentNumberSuffix : EPMDocument 2D number update to :: " + epmNumberSuffix_2D);
			}
		    }
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (old_session != null)
		SessionContext.setContext(old_session);
	    Kogger.debug(getClass(), "=========================== addEPMDocumentNumberSuffix END ===============================");
	    Kogger.debug(getClass(), "");
	}
    }

    private EPMDocumentMaster getRefEPMDocumentMaster(EPMDocument epm) throws Exception {

	QueryResult references = null;
	QueryResult referencedBy = null;
	EPMDocumentMaster master = null;

	references = PersistenceHelper.manager.navigate(epm, "references", EPMReferenceLink.class, false);
	referencedBy = PersistenceHelper.manager.navigate(epm.getMaster(), "referencedBy", EPMReferenceLink.class, false);

	if (references != null) {
	    while (references.hasMoreElements()) {
		EPMReferenceLink referenceLink = (EPMReferenceLink) references.nextElement();
		if ("DRAWING".equals(referenceLink.getReferenceType().toString())) {
		    master = (EPMDocumentMaster) referenceLink.getReferences();
		    break;
		}
	    }
	}
	if (referencedBy != null) {
	    while (referencedBy.hasMoreElements()) {
		EPMReferenceLink referencedByLink = (EPMReferenceLink) referencedBy.nextElement();
		if ("DRAWING".equals(referencedByLink.getReferenceType().toString())) {
		    EPMDocument refByEpm = (EPMDocument) referencedByLink.getReferencedBy();
		    master = (EPMDocumentMaster) refByEpm.getMaster();
		    break;
		}
	    }
	}
	return master;
    }
}
