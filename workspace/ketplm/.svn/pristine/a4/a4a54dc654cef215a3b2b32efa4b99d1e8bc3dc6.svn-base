/* bcwti
 *
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package e3ps.ews.service;

import java.io.Serializable;
import java.sql.Timestamp; // Preserved unmodeled dependency
import java.util.ArrayList; // Preserved unmodeled dependency
import java.util.Hashtable;
import java.util.Vector;

import wt.content.ApplicationData; // Preserved unmodeled dependency
import wt.content.ContentHelper; // Preserved unmodeled dependency
import wt.content.ContentItem; // Preserved unmodeled dependency
import wt.content.ContentRoleType; // Preserved unmodeled dependency
import wt.doc.WTDocument; // Preserved unmodeled dependency
import wt.doc.WTDocumentMaster; // Preserved unmodeled dependency
import wt.fc.PersistenceHelper; // Preserved unmodeled dependency
import wt.fc.PersistenceServerHelper; // Preserved unmodeled dependency
import wt.fc.QueryResult; // Preserved unmodeled dependency
import wt.folder.Folder; // Preserved unmodeled dependency
import wt.folder.FolderEntry; // Preserved unmodeled dependency
import wt.folder.FolderHelper; // Preserved unmodeled dependency
import wt.folder.SubFolder; // Preserved unmodeled dependency
import wt.inf.container.WTContainerRef; // Preserved unmodeled dependency
import wt.lifecycle.LifeCycleHelper; // Preserved unmodeled dependency
import wt.lifecycle.LifeCycleManaged; // Preserved unmodeled dependency
import wt.lifecycle.LifeCycleServerHelper; // Preserved unmodeled dependency
import wt.lifecycle.LifeCycleTemplate; // Preserved unmodeled dependency
import wt.lifecycle.State; // Preserved unmodeled dependency
import wt.method.MethodContext; // Preserved unmodeled dependency
import wt.pom.DBProperties; // Preserved unmodeled dependency
import wt.pom.Transaction; // Preserved unmodeled dependency
import wt.pom.WTConnection; // Preserved unmodeled dependency
import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.util.WTPropertyVetoException; // Preserved unmodeled dependency
import wt.vc.VersionControlHelper; // Preserved unmodeled dependency
import wt.vc.Versioned; // Preserved unmodeled dependency
import wt.vc.wip.WorkInProgressHelper; // Preserved unmodeled dependency
import e3ps.common.content.E3PSContentHelper; // Preserved unmodeled dependency
import e3ps.common.content.uploader.WBFile; // Preserved unmodeled dependency
import e3ps.common.obj.ObjectData; // Preserved unmodeled dependency
import e3ps.common.obj.ObjectUtil; // Preserved unmodeled dependency
import e3ps.common.util.CommonUtil; // Preserved unmodeled dependency
import e3ps.common.util.DateUtil; // Preserved unmodeled dependency
import e3ps.common.util.ManageSequence; // Preserved unmodeled dependency
import e3ps.common.util.WCUtil; // Preserved unmodeled dependency
import e3ps.ews.beans.EWSProperties; // Preserved unmodeled dependency
import e3ps.ews.dao.PartnerDao; // Preserved unmodeled dependency
import e3ps.ews.entity.EarlyWarningTargetLink; // Preserved unmodeled dependency
import e3ps.ews.entity.KETEarlyWarning;
import e3ps.ews.entity.KETEarlyWarningEnd;
import e3ps.ews.entity.KETEarlyWarningEndReq;
import e3ps.ews.entity.KETEarlyWarningEndReqLink; // Preserved unmodeled dependency
import e3ps.ews.entity.KETEarlyWarningPlan;
import e3ps.ews.entity.KETEarlyWarningPlanLink; // Preserved unmodeled dependency
import e3ps.ews.entity.KETEarlyWarningProblem; // Preserved unmodeled dependency
import e3ps.ews.entity.KETEarlyWarningResult;
import e3ps.ews.entity.KETEarlyWarningResultLink; // Preserved unmodeled dependency
import e3ps.ews.entity.KETEarlyWarningStep;
import e3ps.ews.entity.KETEarlyWarningStepLink; // Preserved unmodeled dependency
import e3ps.ews.entity.KETEarlyWarningTarget; // Preserved unmodeled dependency
import e3ps.ews.entity.KETEndReqLink; // Preserved unmodeled dependency
import e3ps.ews.entity.KETResultProblemLink; // Preserved unmodeled dependency
import e3ps.sap.VendorInfoInterface; // Preserved unmodeled dependency
import e3ps.wfm.service.KETWfmHelper; // Preserved unmodeled dependency
import e3ps.wfm.util.WorkflowSearchHelper; // Preserved unmodeled dependency
import ext.ket.shared.log.Kogger;

/**
 * 
 * <p>
 * Use the <code>newStandardKETEwsService</code> static factory method(s), not the <code>StandardKETEwsService</code> constructor, to
 * construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of
 * the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

public class StandardKETEwsService extends StandardManager implements KETEwsService, Serializable {

    private static final String RESOURCE = "e3ps.ews.service.serviceResource";
    private static final String CLASSNAME = StandardKETEwsService.class.getName();

    /**
     * Returns the conceptual (modeled) name for the class.
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @deprecated
     * 
     * @return String
     **/
    public String getConceptualClassname() {

	return CLASSNAME;
    }

    /**
     * @exception wt.services.ManagerException
     **/
    @Override
    protected void performStartupProcess() throws ManagerException {

	super.performStartupProcess();

    }

    /**
     * Default factory for the class.
     * 
     * @return StandardKETEwsService
     * @exception wt.util.WTException
     **/
    public static StandardKETEwsService newStandardKETEwsService() throws WTException {

	StandardKETEwsService instance = new StandardKETEwsService();
	instance.initialize();
	return instance;
    }

    @Override
    public KETEarlyWarning createEarlyWarning(Hashtable hash, Vector files) throws WTException {

	// transaction start
	Transaction trx = new Transaction();
	trx.start();

	// KETEarlyWarningStep object
	KETEarlyWarningStep ketEarlyWarningStep = KETEarlyWarningStep.newKETEarlyWarningStep();
	// KETEarlyWarning object
	KETEarlyWarning ketEarlyWarning = KETEarlyWarning.newKETEarlyWarning();
	// KETEarlyWarningStepLink object
	KETEarlyWarningStepLink ketEarlyWarningStepLink = null;

	try {
	    Kogger.debug(getClass(), "********************" + "save service start");

	    // //////////////////////////////////////////// ketEarlyWarningStep info setting start
	    // //////////////////////////////////////////////////////

	    String ewsStepNumber = "EWSS-" + DateUtil.getCurrentDateString("all").substring(2, 4)
		    + DateUtil.getCurrentDateString("all").substring(5, 7) + "-";
	    ewsStepNumber += ManageSequence.getSeqNo(ewsStepNumber, "000", "WTDocumentMaster", "WTDocumentNumber");

	    ketEarlyWarningStep.setNumber(ewsStepNumber);// ketEarlyWarningStep number
	    ketEarlyWarningStep.setName(ewsStepNumber); // ketEarlyWarningStep name(ketEarlyWarningStep number)

	    ketEarlyWarningStep.setFstCharge((String) hash.get("fstCharge")); // charge(first)

	    String endDate = (String) hash.get("endDate");
	    Timestamp tEndDate = e3ps.common.util.DateUtil.convertDate2(endDate);
	    ketEarlyWarningStep.setEndDate(tEndDate); // working period(end date)

	    // //////////////////////////////////////////// ketEarlyWarningStep info setting end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// ketEarlyWarning info setting start
	    // //////////////////////////////////////////////////////

	    String ewsNumber = "EWS-" + DateUtil.getCurrentDateString("all").substring(2, 4)
		    + DateUtil.getCurrentDateString("all").substring(5, 7) + "-";
	    ewsNumber += ManageSequence.getSeqNo(ewsNumber, "000", "WTDocumentMaster", "WTDocumentNumber");

	    ketEarlyWarning.setNumber(ewsNumber);// KETEarlyWarning number
	    ketEarlyWarning.setName(ewsNumber); // KETEarlyWarning name(KETEarlyWarning number)
	    ketEarlyWarning.setPjtNo((String) hash.get("pjtNo")); // project number

	    String selInOut = (String) hash.get("selInOut");
	    ketEarlyWarning.setInOut(selInOut); // production dept type
	    if (selInOut != null && selInOut.equals("i")) {
		ketEarlyWarning.setProteamNo((String) hash.get("proteamNo")); // dept number(in)
	    } else {
		ketEarlyWarning.setPartnerNo((String) hash.get("proteamNo")); // dept number(out)
	    }
	    ketEarlyWarning.setFstCharge((String) hash.get("fstCharge")); // charge(first)
	    ketEarlyWarning.setSndCharge((String) hash.get("sndCharge")); // charge(second)

	    String workingMM = (String) hash.get("workingMM");
	    if (workingMM != null && workingMM.length() > 0) {
		ketEarlyWarning.setWorkingMM(Integer.parseInt(workingMM)); // working period(MM)
	    }

	    String startDate = (String) hash.get("startDate");
	    Timestamp tStartDate = e3ps.common.util.DateUtil.convertDate2(startDate);
	    ketEarlyWarning.setStartDate(tStartDate); // working period(start date)

	    ketEarlyWarning.setEndDate(tEndDate); // working period(end date)

	    String planDate = (String) hash.get("planDate");
	    Timestamp tPlanDate = e3ps.common.util.DateUtil.convertDate2(planDate);
	    ketEarlyWarning.setPlanDate(tPlanDate); // plan date

	    // //////////////////////////////////////////// ketEarlyWarning info setting end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// folder setting start //////////////////////////////////////////////////////

	    WTContainerRef containerRef = WCUtil.getWTContainerRef();
	    String folderPath = EWSProperties.getInstance().getString("ews.folder.car") + DateUtil.getThisYear();
	    Folder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	    FolderHelper.assignLocation((FolderEntry) ketEarlyWarningStep, folder);
	    FolderHelper.assignLocation((FolderEntry) ketEarlyWarning, folder);

	    // //////////////////////////////////////////// folder setting end //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// LifeCycle setting start //////////////////////////////////////////////////////

	    LifeCycleTemplate LCtemplate = LifeCycleHelper.service.getLifeCycleTemplate("KET_EWR_LC", WCUtil.getPDMLinkProduct()
		    .getContainerReference());
	    LifeCycleHelper.setLifeCycle(ketEarlyWarningStep, LCtemplate);

	    // //////////////////////////////////////////// LifeCycle setting end //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// ketEarlyWarning & step save start
	    // //////////////////////////////////////////////////////

	    ketEarlyWarningStep = (KETEarlyWarningStep) PersistenceHelper.manager.save(ketEarlyWarningStep);
	    ketEarlyWarning = (KETEarlyWarning) PersistenceHelper.manager.save(ketEarlyWarning);

	    // KETEarlyWarningStep & KETEarlyWarning link
	    ketEarlyWarningStepLink = KETEarlyWarningStepLink.newKETEarlyWarningStepLink(
		    (WTDocumentMaster) ketEarlyWarningStep.getMaster(), (WTDocumentMaster) ketEarlyWarning.getMaster());

	    PersistenceServerHelper.manager.insert(ketEarlyWarningStepLink);

	    // //////////////////////////////////////////// ketEarlyWarning & step save end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// KETEarlyWarningTarget save start
	    // //////////////////////////////////////////////////////

	    if (((String) hash.get("isPart")).equals("Y")) {
		// KETEarlyWarningTarget save
		createEarlyWarningTarget(hash, ketEarlyWarning);
	    }

	    // //////////////////////////////////////////// KETEarlyWarningTarget save end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// problem file save start //////////////////////////////////////////////////////

	    WBFile wbfile = null;

	    // problem List save
	    if (files != null) {
		for (int inx = 0; inx < files.size(); inx++) {
		    wbfile = (WBFile) files.elementAt(inx);
		    E3PSContentHelper.service.attach(ketEarlyWarning, wbfile, "problem");
		}
	    }

	    // //////////////////////////////////////////// problem file save end //////////////////////////////////////////////////////

	    // Transaction end
	    trx.commit();

	    Kogger.debug(getClass(), "********************" + "save service end");

	    return ketEarlyWarning;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    Kogger.error(getClass(), wtpve);
	    return null;
	} catch (WTException wte) {
	    trx.rollback();
	    Kogger.error(getClass(), wte);
	    return null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(getClass(), e);
	    return null;
	}

    }

    @Override
    public void createEarlyWarningTarget(Hashtable hash, KETEarlyWarning ketEarlyWarning) throws WTException {

	KETEarlyWarningTarget ketEarlyWarningTarget = null;
	EarlyWarningTargetLink targetLink = null;

	try {

	    Vector LProductNo = (Vector) hash.get("poid"); // part number
	    Vector LTargetcusError = (Vector) hash.get("targetcusError"); // customer error PPM
	    Vector LTargetWorkError = (Vector) hash.get("targetWorkError"); // process error PPM
	    Vector LTargetFacility = (Vector) hash.get("targetFacility"); // facility rate
	    Vector LTargetPerform = (Vector) hash.get("targetPerform"); // performance rate
	    Vector LTargetGood = (Vector) hash.get("targetGood"); // good product rate

	    // //////////////////////////////////////////// ketEarlyWarningTarget setting start
	    // //////////////////////////////////////////////////////

	    for (int inx = 1; inx < LProductNo.size(); inx++) {
		ketEarlyWarningTarget = KETEarlyWarningTarget.newKETEarlyWarningTarget();
		ketEarlyWarningTarget.setProductNo((String) LProductNo.elementAt(inx)); // part number

		if (LTargetcusError.elementAt(inx) != null && ((String) LTargetcusError.elementAt(inx)).length() > 0) {
		    ketEarlyWarningTarget.setTargetCusError((double) Double.parseDouble((String) LTargetcusError.elementAt(inx))); // customer
																   // error
																   // PPM
		}

		if (LTargetWorkError.elementAt(inx) != null && ((String) LTargetWorkError.elementAt(inx)).length() > 0) {
		    ketEarlyWarningTarget.setTargetWorkError((double) Double.parseDouble((String) LTargetWorkError.elementAt(inx))); // process
																     // error
																     // PPM
		}

		if (LTargetFacility.elementAt(inx) != null && ((String) LTargetFacility.elementAt(inx)).length() > 0) {
		    ketEarlyWarningTarget.setTargetFacility((double) Double.parseDouble((String) LTargetFacility.elementAt(inx))); // facility
																   // rate
		}

		if (LTargetPerform.elementAt(inx) != null && ((String) LTargetPerform.elementAt(inx)).length() > 0) {
		    ketEarlyWarningTarget.setTargetPerform((double) Double.parseDouble((String) LTargetPerform.elementAt(inx))); // performance
																 // rate
		}

		if (LTargetGood.elementAt(inx) != null && ((String) LTargetGood.elementAt(inx)).length() > 0) {
		    ketEarlyWarningTarget.setTargetGood((double) Double.parseDouble((String) LTargetGood.elementAt(inx))); // good product
															   // rate
		}

		// //////////////////////////////////////////// ketEarlyWarningTarget setting end
		// //////////////////////////////////////////////////////

		// //////////////////////////////////////////// ketEarlyWarningTarget save start
		// //////////////////////////////////////////////////////

		PersistenceHelper.manager.save(ketEarlyWarningTarget);

		// KETEarlyWarning & KETEarlyWarningTarget link
		targetLink = EarlyWarningTargetLink.newEarlyWarningTargetLink(ketEarlyWarning, ketEarlyWarningTarget);
		PersistenceServerHelper.manager.insert(targetLink);

		// //////////////////////////////////////////// ketEarlyWarningTarget save end
		// //////////////////////////////////////////////////////

	    }
	} catch (WTPropertyVetoException wtpve) {
	    Kogger.error(getClass(), wtpve);
	}

    }

    @Override
    public KETEarlyWarning updateEarlyWarning(Hashtable hash, Vector files) throws WTException {

	// transaction start
	Transaction trx = new Transaction();
	trx.start();

	String cmd = (String) hash.get("cmd");
	KETEarlyWarning ketEarlyWarning = null;
	KETEarlyWarningStep ketEarlyWarningStep = null;

	if (cmd.equals("update")) {
	    // update object
	    ketEarlyWarning = (KETEarlyWarning) CommonUtil.getObject((String) hash.get("oid"));
	} else if (cmd.equals("revise")) {
	    // revise object
	    ketEarlyWarning = (KETEarlyWarning) hash.get("oid");
	}

	try {
	    Kogger.debug(getClass(), "********************" + "update service start");

	    // //////////////////////////////////////////// ketEarlyWarning info setting start
	    // //////////////////////////////////////////////////////

	    ketEarlyWarning.setPjtNo((String) hash.get("pjtNo")); // project number

	    String selInOut = (String) hash.get("selInOut");
	    ketEarlyWarning.setInOut(selInOut); // production dept type
	    if (selInOut != null && selInOut.equals("i")) {
		ketEarlyWarning.setProteamNo((String) hash.get("proteamNo")); // dept number(in)
	    } else {
		ketEarlyWarning.setPartnerNo((String) hash.get("proteamNo")); // dept number(out)
	    }

	    ketEarlyWarning.setFstCharge((String) hash.get("fstCharge")); // charge(first)
	    ketEarlyWarning.setSndCharge((String) hash.get("sndCharge")); // charge(second)

	    String workingMM = (String) hash.get("workingMM");
	    if (workingMM != null && workingMM.length() > 0) {
		ketEarlyWarning.setWorkingMM(Integer.parseInt(workingMM)); // working period(MM)
	    }

	    String startDate = (String) hash.get("startDate");
	    Timestamp tStartDate = e3ps.common.util.DateUtil.convertDate2(startDate);
	    ketEarlyWarning.setStartDate(tStartDate); // working period(start date)

	    String endDate = (String) hash.get("endDate");
	    Timestamp tEndDate = e3ps.common.util.DateUtil.convertDate2(endDate);
	    ketEarlyWarning.setEndDate(tEndDate); // working period(end date)

	    String planDate = (String) hash.get("planDate");
	    Timestamp tPlanDate = e3ps.common.util.DateUtil.convertDate2(planDate);
	    ketEarlyWarning.setPlanDate(tPlanDate); // plan date

	    // //////////////////////////////////////////// ketEarlyWarning info setting end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// ketEarlyWarningStep info setting start
	    // //////////////////////////////////////////////////////

	    QueryResult isEarlyWarningStep = PersistenceHelper.navigate((WTDocumentMaster) ketEarlyWarning.getMaster(),
		    KETEarlyWarningStepLink.ROLE_AOBJECT_ROLE, KETEarlyWarningStepLink.class, false);
	    KETEarlyWarningStepLink ketEarlyWarningStepLink = null;
	    Object earlyWarningStepMaster = null;
	    ObjectData earlyWarninStepMasterData = null;

	    while (isEarlyWarningStep.hasMoreElements()) {
		ketEarlyWarningStepLink = (KETEarlyWarningStepLink) isEarlyWarningStep.nextElement();
		earlyWarningStepMaster = (Object) ObjectUtil.getLatestObject((WTDocumentMaster) ketEarlyWarningStepLink.getRoleAObject());
		earlyWarninStepMasterData = new ObjectData((WTDocument) earlyWarningStepMaster);
	    }

	    String stepOid = earlyWarninStepMasterData.getOid();
	    ketEarlyWarningStep = (KETEarlyWarningStep) CommonUtil.getObject(stepOid);

	    ketEarlyWarningStep.setFstCharge((String) hash.get("fstCharge")); // charge(first)
	    ketEarlyWarningStep.setEndDate(tEndDate); // working period(end date)

	    // //////////////////////////////////////////// ketEarlyWarningStep info setting end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// ketEarlyWarning save start
	    // //////////////////////////////////////////////////////

	    if (cmd.equals("update")) {
		// object checkout
		PersistenceServerHelper.manager.update(ketEarlyWarning);
		/*
	         * update 경우 이터레이션 발생시키지않고 업데이트 시킴 WorkInProgressHelper.service.checkout(ketEarlyWarning,
	         * WorkInProgressHelper.service.getCheckoutFolder(), ""); //create copy object ketEarlyWarning = (KETEarlyWarning)
	         * WorkInProgressHelper.service.workingCopyOf(ketEarlyWarning);
	         * 
	         * //KETEarlyWarning update ketEarlyWarning = (KETEarlyWarning) PersistenceHelper.manager.modify(ketEarlyWarning);
	         * //KETEarlyWarning refresh ketEarlyWarning = (KETEarlyWarning) PersistenceHelper.manager.refresh(ketEarlyWarning);
	         */
	    } else if (cmd.equals("revise")) {
		// KETEarlyWarning revise object save
		ketEarlyWarning = (KETEarlyWarning) PersistenceHelper.manager.save(ketEarlyWarning);
	    }

	    // object checkout
	    WorkInProgressHelper.service.checkout(ketEarlyWarningStep, WorkInProgressHelper.service.getCheckoutFolder(), "");
	    // create copy object
	    ketEarlyWarningStep = (KETEarlyWarningStep) WorkInProgressHelper.service.workingCopyOf(ketEarlyWarningStep);

	    // ketEarlyWarningStep update
	    ketEarlyWarningStep = (KETEarlyWarningStep) PersistenceHelper.manager.modify(ketEarlyWarningStep);
	    // ketEarlyWarningStep refresh
	    ketEarlyWarningStep = (KETEarlyWarningStep) PersistenceHelper.manager.refresh(ketEarlyWarningStep);

	    // //////////////////////////////////////////// ketEarlyWarning save end //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// KETEarlyWarningTarget save start
	    // //////////////////////////////////////////////////////

	    // old KETEarlyWarningTarget delete
	    deleteEarlyWarningTarget(hash, ketEarlyWarning);
	    if (((String) hash.get("isPart")).equals("Y")) {
		// new KETEarlyWarningTarget save
		createEarlyWarningTarget(hash, ketEarlyWarning);
	    }

	    // //////////////////////////////////////////// KETEarlyWarningTarget save end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// problem List save start //////////////////////////////////////////////////////

	    // old file delete
	    QueryResult rs = ContentHelper.service.getContentsByRole(ketEarlyWarning, ContentRoleType.SECONDARY);

	    while (rs.hasMoreElements()) {
		ContentItem fileContent = (ContentItem) rs.nextElement();
		ketEarlyWarning = (KETEarlyWarning) E3PSContentHelper.service.delete(ketEarlyWarning, fileContent);
	    }

	    // existing file save
	    Object secondaryFile = hash.get("secondaryDelFile");
	    ApplicationData oldFile = null;

	    if (secondaryFile instanceof String) {
		oldFile = (ApplicationData) CommonUtil.getObject((String) secondaryFile);
		E3PSContentHelper.service.attach(ketEarlyWarning, oldFile, false);
	    } else if (secondaryFile instanceof Vector) {
		Vector vecOld = (Vector) secondaryFile;
		for (int inx = 0; inx < vecOld.size(); inx++) {
		    oldFile = (ApplicationData) CommonUtil.getObject((String) vecOld.elementAt(inx));
		    E3PSContentHelper.service.attach(ketEarlyWarning, oldFile, false);
		}
	    }

	    WBFile wbfile = null;

	    // new file save
	    if (files != null) {
		for (int inx = 0; inx < files.size(); inx++) {
		    wbfile = (WBFile) files.elementAt(inx);
		    E3PSContentHelper.service.attach(ketEarlyWarning, wbfile, "problem");
		}
	    }

	    // //////////////////////////////////////////// problem List save end //////////////////////////////////////////////////////

	    // KETEarlyWarning refresh
	    ketEarlyWarning = (KETEarlyWarning) PersistenceHelper.manager.refresh(ketEarlyWarning);

	    if (WorkInProgressHelper.isCheckedOut(ketEarlyWarning)) {
		// KETEarlyWarning checkin
		ketEarlyWarning = (KETEarlyWarning) WorkInProgressHelper.service.checkin(ketEarlyWarning, "");
	    }

	    // KETEarlyWarningStep refresh
	    ketEarlyWarningStep = (KETEarlyWarningStep) PersistenceHelper.manager.refresh(ketEarlyWarningStep);

	    if (WorkInProgressHelper.isCheckedOut(ketEarlyWarningStep)) {
		// KETEarlyWarningStep checkin
		ketEarlyWarningStep = (KETEarlyWarningStep) WorkInProgressHelper.service.checkin(ketEarlyWarningStep, "");
	    }

	    // transaction end
	    trx.commit();

	    Kogger.debug(getClass(), "********************" + "update service end");

	    return ketEarlyWarning;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    Kogger.error(getClass(), wtpve);
	    return null;
	} catch (WTException wte) {
	    trx.rollback();
	    Kogger.error(getClass(), wte);
	    return null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(getClass(), e);
	    return null;
	}
    }

    @Override
    public void deleteEarlyWarningTarget(Hashtable hash, KETEarlyWarning ketEarlyWarning) throws WTException {

	EarlyWarningTargetLink link = null;
	KETEarlyWarningTarget ketEarlyWarningTarget = null;
	// EarlyWarningTargetLink List
	QueryResult result = PersistenceHelper.navigate(ketEarlyWarning, EarlyWarningTargetLink.ROLE_BOBJECT_ROLE,
	        EarlyWarningTargetLink.class, false);

	// EarlyWarningTargetLink object delete & KETEarlyWarningTarget object delete
	if (result != null) {
	    while (result.hasMoreElements()) {
		link = (EarlyWarningTargetLink) result.nextElement();
		ketEarlyWarningTarget = (KETEarlyWarningTarget) link.getRoleBObject();
		PersistenceHelper.manager.delete(ketEarlyWarningTarget);
	    }
	}

    }

    @Override
    public KETEarlyWarning reviseEarlyWarning(Hashtable hash, Vector files) throws WTException {

	Versioned newVs = null;

	try {
	    // KETEarlyWarning current version
	    Versioned vs = (Versioned) CommonUtil.getObject((String) hash.get("oid"));
	    // KETEarlyWarning new version
	    newVs = VersionControlHelper.service.newVersion(vs);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}

	// KETEarlyWarning new version save
	hash.put("oid", newVs);
	KETEarlyWarning ketEarlyWarning = updateEarlyWarning(hash, files);

	return ketEarlyWarning;

    }

    @Override
    public Boolean deleteEarlyWarning(Hashtable hash, Vector files) throws WTException {

	boolean flag = false;

	// transaction start
	Transaction trx = new Transaction();
	trx.start();

	// KETEarlyWarning object
	KETEarlyWarning ketEarlyWarning = (KETEarlyWarning) CommonUtil.getObject((String) hash.get("oid"));

	try {

	    Kogger.debug(getClass(), "********************" + "delete service start");

	    // KETEarlyWarningTarget object delete
	    deleteEarlyWarningTarget(hash, ketEarlyWarning);

	    // file delete
	    QueryResult rs = ContentHelper.service.getContentsByRole(ketEarlyWarning, ContentRoleType.SECONDARY);
	    while (rs.hasMoreElements()) {
		ContentItem fileContent = (ContentItem) rs.nextElement();
		E3PSContentHelper.service.delete(ketEarlyWarning, fileContent);
	    }

	    // KETEarlyWarning object delete
	    PersistenceHelper.manager.delete(ketEarlyWarning);

	    // transaction end
	    trx.commit();

	    // success
	    flag = true;

	    Kogger.debug(getClass(), "********************" + "delete service end");

	} catch (Exception e) {
	    trx.rollback();
	    // fail
	    flag = false;
	    Kogger.error(getClass(), e);
	}

	return flag;

    }

    @Override
    public KETEarlyWarningPlan createEarlyWarningPlan(Hashtable hash, Vector files) throws WTException {

	// transaction start
	Transaction trx = new Transaction();
	trx.start();

	// KETEarlyWarningPlan object
	KETEarlyWarningPlan ketEarlyWarningPlan = KETEarlyWarningPlan.newKETEarlyWarningPlan();
	// KETEarlyWarning object
	KETEarlyWarning ketEarlyWarning = (KETEarlyWarning) CommonUtil.getObject((String) hash.get("oid"));

	try {
	    Kogger.debug(getClass(), "********************" + "save service start");

	    // //////////////////////////////////////////// ketEarlyWarningPlan info setting start
	    // //////////////////////////////////////////////////////

	    String ewsNumber = "EWSP-" + DateUtil.getCurrentDateString("all").substring(2, 4)
		    + DateUtil.getCurrentDateString("all").substring(5, 7) + "-";
	    ewsNumber += ManageSequence.getSeqNo(ewsNumber, "000", "WTDocumentMaster", "WTDocumentNumber");

	    ketEarlyWarningPlan.setNumber(ewsNumber); // ketEarlyWarningPlan number
	    ketEarlyWarningPlan.setName(ewsNumber); // ketEarlyWarningPlan name(ketEarlyWarningPlan number)

	    Object objCftMember = hash.get("cftMember");
	    String cftmember = null;

	    if (objCftMember == null) {
		ketEarlyWarningPlan.setCftMember("");
	    } else if (objCftMember instanceof String) {
		ketEarlyWarningPlan.setCftMember((String) objCftMember); // ketEarlyWarningPlan cftmember
	    } else if (objCftMember instanceof Vector) {
		Vector arrCftMember = (Vector) objCftMember;
		cftmember = (String) arrCftMember.elementAt(0);
		for (int inx = 1; inx < arrCftMember.size(); inx++) {
		    cftmember = cftmember + "/" + arrCftMember.elementAt(inx);
		}
		ketEarlyWarningPlan.setCftMember(cftmember); // ketEarlyWarningPlan cftmember
	    }

	    ketEarlyWarningPlan.setManageDesc((String) hash.get("manageDesc")); // ketEarlyWarningPlan manageDesc

	    // //////////////////////////////////////////// ketEarlyWarningPlan info setting end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// folder setting start //////////////////////////////////////////////////////

	    WTContainerRef containerRef = WCUtil.getWTContainerRef();
	    String folderPath = EWSProperties.getInstance().getString("ews.folder.car") + DateUtil.getThisYear();
	    SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	    FolderHelper.assignLocation((FolderEntry) ketEarlyWarningPlan, folder);

	    // //////////////////////////////////////////// folder setting end //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// ketEarlyWarningPlan save start
	    // //////////////////////////////////////////////////////

	    // KETEarlyWarningPlan save
	    ketEarlyWarningPlan = (KETEarlyWarningPlan) PersistenceHelper.manager.save(ketEarlyWarningPlan);

	    // EarlyWarning & Plan link save
	    KETEarlyWarningPlanLink planLink = KETEarlyWarningPlanLink.newKETEarlyWarningPlanLink(
		    (WTDocumentMaster) ketEarlyWarning.getMaster(), (WTDocumentMaster) ketEarlyWarningPlan.getMaster());

	    PersistenceServerHelper.manager.insert(planLink);

	    // //////////////////////////////////////////// ketEarlyWarningPlan save end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// plan file save start //////////////////////////////////////////////////////

	    if (files != null) {
		E3PSContentHelper.service.attach(ketEarlyWarningPlan, files);
	    }

	    // //////////////////////////////////////////// plan file save end //////////////////////////////////////////////////////

	    // Transaction end
	    trx.commit();

	    Kogger.debug(getClass(), "********************" + "save service end");

	    return ketEarlyWarningPlan;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    Kogger.error(getClass(), wtpve);
	    return null;
	} catch (WTException wte) {
	    trx.rollback();
	    Kogger.error(getClass(), wte);
	    return null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(getClass(), e);
	    return null;
	}
    }

    @Override
    public KETEarlyWarningPlan updateEarlyWarningPlan(Hashtable hash, Vector files) throws WTException {

	// transaction start
	Transaction trx = new Transaction();
	trx.start();

	String cmd = (String) hash.get("cmd");
	KETEarlyWarningPlan ketEarlyWarningPlan = null;

	if (cmd.equals("update")) {
	    // update object
	    ketEarlyWarningPlan = (KETEarlyWarningPlan) CommonUtil.getObject((String) hash.get("planOid"));
	} else if (cmd.equals("revise")) {
	    // revise object
	    ketEarlyWarningPlan = (KETEarlyWarningPlan) hash.get("planOid");
	}

	try {
	    Kogger.debug(getClass(), "********************" + "update service start");

	    // //////////////////////////////////////////// ketEarlyWarningPlan info setting start
	    // //////////////////////////////////////////////////////

	    Object objCftMember = hash.get("cftMember");
	    String cftmember = null;

	    if (objCftMember == null) {
		ketEarlyWarningPlan.setCftMember("");
	    } else if (objCftMember instanceof String) {
		ketEarlyWarningPlan.setCftMember((String) objCftMember); // ketEarlyWarningPlan cftmember
	    } else if (objCftMember instanceof Vector) {
		Vector arrCftMember = (Vector) objCftMember;
		cftmember = (String) arrCftMember.elementAt(0);
		for (int inx = 1; inx < arrCftMember.size(); inx++) {
		    cftmember = cftmember + "/" + arrCftMember.elementAt(inx);
		}
		ketEarlyWarningPlan.setCftMember(cftmember); // ketEarlyWarningPlan cftmember
	    }

	    ketEarlyWarningPlan.setManageDesc((String) hash.get("manageDesc"));// manageDesc

	    // //////////////////////////////////////////// ketEarlyWarningPlan info setting end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// ketEarlyWarningPlan save start
	    // //////////////////////////////////////////////////////

	    if (cmd.equals("update")) {
		// object checkout
		WorkInProgressHelper.service.checkout(ketEarlyWarningPlan, WorkInProgressHelper.service.getCheckoutFolder(), "");
		// create copy object
		ketEarlyWarningPlan = (KETEarlyWarningPlan) WorkInProgressHelper.service.workingCopyOf(ketEarlyWarningPlan);

		// ketEarlyWarningPlan update
		ketEarlyWarningPlan = (KETEarlyWarningPlan) PersistenceHelper.manager.modify(ketEarlyWarningPlan);
		// ketEarlyWarningPlan refresh
		ketEarlyWarningPlan = (KETEarlyWarningPlan) PersistenceHelper.manager.refresh(ketEarlyWarningPlan);
	    } else if (cmd.equals("revise")) {
		// ketEarlyWarningPlan revise object save
		ketEarlyWarningPlan = (KETEarlyWarningPlan) PersistenceHelper.manager.save(ketEarlyWarningPlan);

	    }

	    // //////////////////////////////////////////// ketEarlyWarningPlan save start
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// plan file save start //////////////////////////////////////////////////////

	    // old file delete
	    QueryResult rs = ContentHelper.service.getContentsByRole(ketEarlyWarningPlan, ContentRoleType.SECONDARY);

	    while (rs.hasMoreElements()) {
		ContentItem fileContent = (ContentItem) rs.nextElement();
		ketEarlyWarningPlan = (KETEarlyWarningPlan) E3PSContentHelper.service.delete(ketEarlyWarningPlan, fileContent);
	    }

	    // existing file save
	    Object secondaryFile = hash.get("secondaryDelFile");
	    ApplicationData oldFile = null;

	    if (secondaryFile instanceof String) {
		oldFile = (ApplicationData) CommonUtil.getObject((String) secondaryFile);
		E3PSContentHelper.service.attach(ketEarlyWarningPlan, oldFile, false);
	    } else if (secondaryFile instanceof Vector) {
		Vector vecOld = (Vector) secondaryFile;
		for (int inx = 0; inx < vecOld.size(); inx++) {
		    oldFile = (ApplicationData) CommonUtil.getObject((String) vecOld.elementAt(inx));
		    E3PSContentHelper.service.attach(ketEarlyWarningPlan, oldFile, false);
		}
	    }

	    // new file save
	    if (files != null) {
		E3PSContentHelper.service.attach(ketEarlyWarningPlan, files);
	    }

	    // //////////////////////////////////////////// plan file save end //////////////////////////////////////////////////////

	    // ketEarlyWarningPlan refresh
	    ketEarlyWarningPlan = (KETEarlyWarningPlan) PersistenceHelper.manager.refresh(ketEarlyWarningPlan);

	    if (WorkInProgressHelper.isCheckedOut(ketEarlyWarningPlan)) {
		// ketEarlyWarningPlan checkin
		ketEarlyWarningPlan = (KETEarlyWarningPlan) WorkInProgressHelper.service.checkin(ketEarlyWarningPlan, "");
	    }

	    // transaction end
	    trx.commit();

	    Kogger.debug(getClass(), "********************" + "update service end");

	    return ketEarlyWarningPlan;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    Kogger.error(getClass(), wtpve);
	    return null;
	} catch (WTException wte) {
	    trx.rollback();
	    Kogger.error(getClass(), wte);
	    return null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(getClass(), e);
	    return null;
	}

    }

    @Override
    public KETEarlyWarningPlan reviseEarlyWarningPlan(Hashtable hash, Vector files) throws WTException {

	Versioned newVs = null;

	try {
	    // KETEarlyWarningPlan current version
	    Versioned vs = (Versioned) CommonUtil.getObject((String) hash.get("planOid"));
	    // KETEarlyWarningPlan new version
	    newVs = VersionControlHelper.service.newVersion(vs);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}

	// KETEarlyWarningPlan new version save
	hash.put("planOid", newVs);
	KETEarlyWarningPlan ketEarlyWarningPlan = updateEarlyWarningPlan(hash, files);

	return ketEarlyWarningPlan;
    }

    @Override
    public Boolean deleteEarlyWarningPlan(Hashtable hash, Vector files) throws WTException {

	boolean flag = false;

	// transaction start
	Transaction trx = new Transaction();
	trx.start();

	// KETEarlyWarningPlan object
	KETEarlyWarningPlan ketEarlyWarningPlan = (KETEarlyWarningPlan) CommonUtil.getObject((String) hash.get("planOid"));
	String ver = VersionControlHelper.getVersionIdentifier(ketEarlyWarningPlan).getSeries().getValue();

	try {

	    // KETEarlyWarning object
	    QueryResult isEarlyWarning = PersistenceHelper.navigate((WTDocumentMaster) ketEarlyWarningPlan.getMaster(),
		    KETEarlyWarningPlanLink.ROLE_AOBJECT_ROLE, KETEarlyWarningPlanLink.class, false);
	    KETEarlyWarningPlanLink ketEarlyWarningPlanLink = null;
	    Object earlyWarningMaster = null;
	    ObjectData earlyWarninMasterData = null;

	    while (isEarlyWarning.hasMoreElements()) {
		ketEarlyWarningPlanLink = (KETEarlyWarningPlanLink) isEarlyWarning.nextElement();
		earlyWarningMaster = (Object) ObjectUtil.getLatestObject((WTDocumentMaster) ketEarlyWarningPlanLink.getRoleAObject());
		earlyWarninMasterData = new ObjectData((WTDocument) earlyWarningMaster);
	    }

	    String oid = earlyWarninMasterData.getOid();
	    KETEarlyWarning ketEarlyWarning = (KETEarlyWarning) CommonUtil.getObject(oid);

	    // file delete
	    QueryResult rs = ContentHelper.service.getContentsByRole(ketEarlyWarningPlan, ContentRoleType.SECONDARY);
	    while (rs.hasMoreElements()) {
		ContentItem fileItem = (ContentItem) rs.nextElement();
		E3PSContentHelper.service.delete(ketEarlyWarningPlan, fileItem);
	    }

	    // KETEarlyWarning object delete
	    PersistenceHelper.manager.delete(ketEarlyWarningPlan);

	    // todo create
	    // if ( ver != null && ver.equals("0")){
	    // KETWfmHelper.service.createWorkItem(ketEarlyWarning);
	    // }

	    // transaction end
	    trx.commit();

	    // success
	    flag = true;
	} catch (Exception e) {
	    trx.rollback();
	    // fail
	    flag = false;
	    Kogger.error(getClass(), e);
	}

	return flag;

    }

    @Override
    public KETEarlyWarningResult createEarlyWarningResult(Hashtable hash, Vector files) throws WTException {

	// transaction start
	Transaction trx = new Transaction();
	trx.start();

	// KETEarlyWarningResult object
	KETEarlyWarningResult ketEarlyWarningResult = KETEarlyWarningResult.newKETEarlyWarningResult();
	// KETEarlyWarning object
	KETEarlyWarning ketEarlyWarning = (KETEarlyWarning) CommonUtil.getObject((String) hash.get("oid"));

	try {
	    Kogger.debug(getClass(), "********************" + "save service start");

	    // //////////////////////////////////////////// ketEarlyWarningResult info setting start
	    // //////////////////////////////////////////////////////

	    String ewsNumber = "EWSR-" + DateUtil.getCurrentDateString("all").substring(2, 4)
		    + DateUtil.getCurrentDateString("all").substring(5, 7) + "-";
	    ewsNumber += ManageSequence.getSeqNo(ewsNumber, "000", "WTDocumentMaster", "WTDocumentNumber");

	    ketEarlyWarningResult.setNumber(ewsNumber); // ketEarlyWarningResult number
	    ketEarlyWarningResult.setName(ewsNumber); // ketEarlyWarningResult name(ketEarlyWarningResult number)

	    // //////////////////////////////////////////// ketEarlyWarningResult info setting end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// folder setting start //////////////////////////////////////////////////////

	    WTContainerRef containerRef = WCUtil.getWTContainerRef();
	    String folderPath = EWSProperties.getInstance().getString("ews.folder.car") + DateUtil.getThisYear();
	    Folder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	    FolderHelper.assignLocation((FolderEntry) ketEarlyWarningResult, folder);

	    // //////////////////////////////////////////// folder setting end //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// ketEarlyWarningResult save start
	    // //////////////////////////////////////////////////////

	    ketEarlyWarningResult = (KETEarlyWarningResult) PersistenceHelper.manager.save(ketEarlyWarningResult);

	    // EarlyWarning & Result link save
	    KETEarlyWarningResultLink resultLink = KETEarlyWarningResultLink.newKETEarlyWarningResultLink(
		    (WTDocumentMaster) ketEarlyWarning.getMaster(), (WTDocumentMaster) ketEarlyWarningResult.getMaster());
	    PersistenceServerHelper.manager.insert(resultLink);
	    // todo complete
	    WorkflowSearchHelper.manager.taskComplete(ketEarlyWarningResult);
	    // //////////////////////////////////////////// ketEarlyWarningResult save end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// KETEarlyWarningProblem save start
	    // //////////////////////////////////////////////////////

	    if (((String) hash.get("isProblem")).equals("Y")) {
		// problem list save
		createEarlyWarningProblem(hash, ketEarlyWarningResult);
	    }

	    // //////////////////////////////////////////// KETEarlyWarningProblem save end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// result file save start //////////////////////////////////////////////////////

	    Object existedFile = hash.get("oldProblemFile");
	    ApplicationData appFile = null;
	    WBFile wbfile = null;

	    // old file insert
	    if (existedFile instanceof String) {
		appFile = (ApplicationData) CommonUtil.getObject((String) existedFile);
		E3PSContentHelper.service.attach(ketEarlyWarningResult, appFile, false);
	    } else if (existedFile instanceof Vector) {
		Vector vecOld = (Vector) existedFile;
		for (int inx = 0; inx < vecOld.size(); inx++) {
		    appFile = (ApplicationData) CommonUtil.getObject((String) vecOld.elementAt(inx));
		    E3PSContentHelper.service.attach(ketEarlyWarningResult, appFile, false);
		}
	    }

	    // new file insert
	    if (files != null) {
		for (int inx = 0; inx < files.size(); inx++) {
		    wbfile = (WBFile) files.elementAt(inx);
		    E3PSContentHelper.service.attach(ketEarlyWarningResult, wbfile, (wbfile.getFieldName()).substring(0, 7));
		}
	    }

	    // //////////////////////////////////////////// result file save end //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// workflow setting start //////////////////////////////////////////////////////

	    KETWfmHelper.service.createWorkItem(ketEarlyWarningResult);

	    // //////////////////////////////////////////// workflow setting end //////////////////////////////////////////////////////

	    // Transaction end
	    trx.commit();

	    Kogger.debug(getClass(), "********************" + "save service end");

	    return ketEarlyWarningResult;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    Kogger.error(getClass(), wtpve);
	    return null;
	} catch (WTException wte) {
	    trx.rollback();
	    Kogger.error(getClass(), wte);
	    return null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(getClass(), e);
	    return null;
	}

    }

    @Override
    public KETEarlyWarningResult updateEarlyWarningResult(Hashtable hash, Vector files) throws WTException {

	// transaction start
	Transaction trx = new Transaction();
	trx.start();

	KETEarlyWarningResult ketEarlyWarningResult = null;

	try {
	    Kogger.debug(getClass(), "********************" + "update service start");

	    // //////////////////////////////////////////// ketEarlyWarningResult save start
	    // //////////////////////////////////////////////////////

	    // update object
	    ketEarlyWarningResult = (KETEarlyWarningResult) CommonUtil.getObject((String) hash.get("resultOid"));

	    // object checkout
	    WorkInProgressHelper.service.checkout(ketEarlyWarningResult, WorkInProgressHelper.service.getCheckoutFolder(), "");
	    // create copy object
	    ketEarlyWarningResult = (KETEarlyWarningResult) WorkInProgressHelper.service.workingCopyOf(ketEarlyWarningResult);

	    // ketEarlyWarningResult update
	    ketEarlyWarningResult = (KETEarlyWarningResult) PersistenceHelper.manager.modify(ketEarlyWarningResult);
	    // ketEarlyWarningResult refresh
	    ketEarlyWarningResult = (KETEarlyWarningResult) PersistenceHelper.manager.refresh(ketEarlyWarningResult);

	    // //////////////////////////////////////////// ketEarlyWarningResult save end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// KETEarlyWarningProblem save start
	    // //////////////////////////////////////////////////////

	    // old problem list delete
	    deleteEarlyWarningProblem(hash, ketEarlyWarningResult);
	    if (((String) hash.get("isProblem")).equals("Y")) {
		// new problem list create
		createEarlyWarningProblem(hash, ketEarlyWarningResult);
	    }

	    // //////////////////////////////////////////// KETEarlyWarningProblem save end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// result file save start //////////////////////////////////////////////////////

	    // old file delete
	    QueryResult rs = ContentHelper.service.getContentsByRole(ketEarlyWarningResult, ContentRoleType.SECONDARY);

	    while (rs.hasMoreElements()) {
		ContentItem fileContent = (ContentItem) rs.nextElement();
		ketEarlyWarningResult = (KETEarlyWarningResult) E3PSContentHelper.service.delete(ketEarlyWarningResult, fileContent);
	    }

	    Object existedFile = hash.get("oldProblemFile");
	    ApplicationData appFile = null;
	    WBFile wbfile = null;

	    // existing file save
	    if (existedFile instanceof String) {
		appFile = (ApplicationData) CommonUtil.getObject((String) existedFile);
		E3PSContentHelper.service.attach(ketEarlyWarningResult, appFile, false);
	    } else if (existedFile instanceof Vector) {
		Vector vecOld = (Vector) existedFile;
		for (int inx = 0; inx < vecOld.size(); inx++) {
		    appFile = (ApplicationData) CommonUtil.getObject((String) vecOld.elementAt(inx));
		    E3PSContentHelper.service.attach(ketEarlyWarningResult, appFile, false);
		}
	    }

	    // new file save
	    if (files != null) {
		for (int inx = 0; inx < files.size(); inx++) {
		    wbfile = (WBFile) files.elementAt(inx);
		    E3PSContentHelper.service.attach(ketEarlyWarningResult, wbfile, (wbfile.getFieldName()).substring(0, 7));
		}
	    }

	    // //////////////////////////////////////////// result file save start //////////////////////////////////////////////////////

	    // ketEarlyWarningResult refresh
	    ketEarlyWarningResult = (KETEarlyWarningResult) PersistenceHelper.manager.refresh(ketEarlyWarningResult);

	    if (WorkInProgressHelper.isCheckedOut(ketEarlyWarningResult)) {
		// ketEarlyWarningResult checkin
		ketEarlyWarningResult = (KETEarlyWarningResult) WorkInProgressHelper.service.checkin(ketEarlyWarningResult, "");
	    }

	    // transaction end
	    trx.commit();

	    Kogger.debug(getClass(), "********************" + "update service end");

	    return ketEarlyWarningResult;

	} catch (WTException wte) {
	    trx.rollback();
	    Kogger.error(getClass(), wte);
	    return null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(getClass(), e);
	    return null;
	}
    }

    @Override
    public Boolean deleteEarlyWarningResult(Hashtable hash, Vector files) throws WTException {

	boolean flag = false;

	// transaction start
	Transaction trx = new Transaction();
	trx.start();

	// KETEarlyWarningResult object
	KETEarlyWarningResult ketEarlyWarningResult = (KETEarlyWarningResult) CommonUtil.getObject((String) hash.get("resultOid"));

	try {
	    Kogger.debug(getClass(), "********************" + "delete service start");

	    // todo delete
	    WorkflowSearchHelper.manager.deleteWorkItem(ketEarlyWarningResult);

	    // KETEarlyWarning object
	    QueryResult isEarlyWarning = PersistenceHelper.navigate((WTDocumentMaster) ketEarlyWarningResult.getMaster(),
		    KETEarlyWarningResultLink.ROLE_AOBJECT_ROLE, KETEarlyWarningResultLink.class, false);
	    KETEarlyWarningResultLink ketEarlyWarningResultLink = null;
	    Object earlyWarningMaster = null;
	    ObjectData earlyWarninMasterData = null;

	    while (isEarlyWarning.hasMoreElements()) {
		ketEarlyWarningResultLink = (KETEarlyWarningResultLink) isEarlyWarning.nextElement();
		earlyWarningMaster = (Object) ObjectUtil.getLatestObject((WTDocumentMaster) ketEarlyWarningResultLink.getRoleAObject());
		earlyWarninMasterData = new ObjectData((WTDocument) earlyWarningMaster);
	    }

	    String oid = earlyWarninMasterData.getOid();
	    KETEarlyWarning ketEarlyWarning = (KETEarlyWarning) CommonUtil.getObject(oid);

	    // KETEarlyWarningPlan object
	    QueryResult isPlan = PersistenceHelper.navigate((WTDocumentMaster) ketEarlyWarning.getMaster(),
		    KETEarlyWarningPlanLink.ROLE_BOBJECT_ROLE, KETEarlyWarningPlanLink.class, false);
	    KETEarlyWarningPlanLink ketEarlyWarningPlanLink = null;
	    Object planMaster = null;
	    ObjectData planMasterData = null;

	    while (isPlan.hasMoreElements()) {
		ketEarlyWarningPlanLink = (KETEarlyWarningPlanLink) isPlan.nextElement();
		planMaster = (Object) ObjectUtil.getLatestObject((WTDocumentMaster) ketEarlyWarningPlanLink.getRoleBObject());
		planMasterData = new ObjectData((WTDocument) planMaster);
	    }

	    String planOid = planMasterData.getOid();
	    KETEarlyWarningPlan ketEarlyWarningPlan = (KETEarlyWarningPlan) CommonUtil.getObject(planOid);

	    // KETEarlyWarningProblem object delete
	    deleteEarlyWarningProblem(hash, ketEarlyWarningResult);

	    // file delete
	    QueryResult rs = ContentHelper.service.getContentsByRole(ketEarlyWarningResult, ContentRoleType.SECONDARY);
	    while (rs.hasMoreElements()) {
		ContentItem fileItem = (ContentItem) rs.nextElement();
		E3PSContentHelper.service.delete(ketEarlyWarningResult, fileItem);
	    }

	    // KETEarlyWarningResult object delete
	    PersistenceHelper.manager.delete(ketEarlyWarningResult);

	    // todo create
	    KETWfmHelper.service.createWorkItem(ketEarlyWarningPlan);

	    // transaction end
	    trx.commit();

	    // success
	    flag = true;

	    Kogger.debug(getClass(), "********************" + "delete service end");
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(getClass(), e);
	    // fail
	    flag = false;
	}

	return flag;
    }

    @Override
    public void createEarlyWarningProblem(Hashtable hash, KETEarlyWarningResult ketEarlyWarningResult) throws WTException {

	KETEarlyWarningProblem ketEarlyWarningProblem = null;
	KETResultProblemLink ketResultProblemLink = null;

	try {
	    Vector problemKind = (Vector) hash.get("problemKind"); // problem or process
	    Vector problemStep = (Vector) hash.get("problemStep"); // problem step
	    Vector problemGroup = (Vector) hash.get("problemGroup"); // problemGroup
	    Vector problemType = (Vector) hash.get("problemType"); // problem type
	    Vector problemDesc = (Vector) hash.get("problemDesc"); // problem desc
	    Vector measure = (Vector) hash.get("measure"); // problem measure
	    Vector charge = (Vector) hash.get("charge"); // charge
	    Vector endDate = (Vector) hash.get("endDate"); // endDate
	    Vector isEnd = (Vector) hash.get("isEnd"); // end or not yet

	    // //////////////////////////////////////////// ketEarlyWarningProblem setting start
	    // //////////////////////////////////////////////////////

	    for (int inx = 1; inx < problemKind.size(); inx++) {
		if (!((problemStep.elementAt(inx) == null || ((String) problemStep.elementAt(inx)).equals(""))
		        && (problemGroup.elementAt(inx) == null || ((String) problemGroup.elementAt(inx)).equals(""))
		        && (problemType.elementAt(inx) == null || ((String) problemType.elementAt(inx)).equals(""))
		        && (problemDesc.elementAt(inx) == null || ((String) problemDesc.elementAt(inx)).equals(""))
		        && (measure.elementAt(inx) == null || ((String) measure.elementAt(inx)).equals(""))
		        && (charge.elementAt(inx) == null || ((String) charge.elementAt(inx)).equals("")) && (endDate.elementAt(inx) == null || ((String) endDate
		        .elementAt(inx)).equals("")))) {

		    ketEarlyWarningProblem = KETEarlyWarningProblem.newKETEarlyWarningProblem();
		    ketEarlyWarningProblem.setProblemkind((String) problemKind.elementAt(inx)); // problem or process
		    ketEarlyWarningProblem.setProblemstep((String) problemStep.elementAt(inx)); // problem step
		    ketEarlyWarningProblem.setProblemtype((String) problemGroup.elementAt(inx) + "/" + (String) problemType.elementAt(inx)); // problem
																	     // type
		    ketEarlyWarningProblem.setProblemdesc((String) problemDesc.elementAt(inx)); // problem desc
		    ketEarlyWarningProblem.setMeasure((String) measure.elementAt(inx)); // problem measure
		    ketEarlyWarningProblem.setCharge((String) charge.elementAt(inx)); // charge

		    Timestamp tEndDate = e3ps.common.util.DateUtil.convertDate2((String) endDate.elementAt(inx));
		    ketEarlyWarningProblem.setEnddate(tEndDate); // endDate

		    ketEarlyWarningProblem.setIsend((String) isEnd.elementAt(inx)); // end or not yet

		    // //////////////////////////////////////////// ketEarlyWarningProblem setting end
		    // //////////////////////////////////////////////////////

		    // //////////////////////////////////////////// ketEarlyWarningProblem save start
		    // //////////////////////////////////////////////////////

		    PersistenceHelper.manager.save(ketEarlyWarningProblem);

		    // ketEarlyWarningResult & ketEarlyWarningProblem link
		    ketResultProblemLink = KETResultProblemLink.newKETResultProblemLink(ketEarlyWarningResult, ketEarlyWarningProblem);
		    PersistenceServerHelper.manager.insert(ketResultProblemLink);

		    // //////////////////////////////////////////// ketEarlyWarningProblem save end
		    // //////////////////////////////////////////////////////
		}
	    }
	} catch (WTPropertyVetoException wtpve) {
	    Kogger.error(getClass(), wtpve);
	}

    }

    @Override
    public void deleteEarlyWarningProblem(Hashtable hash, KETEarlyWarningResult ketEarlyWarningResult) throws WTException {

	KETEarlyWarningProblem ketEarlyWarningProblem = null;
	KETResultProblemLink ketResultProblemLink = null;

	// KETResultProblemLink List
	QueryResult result = PersistenceHelper.navigate(ketEarlyWarningResult, KETResultProblemLink.ROLE_BOBJECT_ROLE,
	        KETResultProblemLink.class, false);

	// EarlyWarningTargetLink object delete & KETEarlyWarningTarget object delete
	while (result.hasMoreElements()) {
	    ketResultProblemLink = (KETResultProblemLink) result.nextElement();
	    ketEarlyWarningProblem = (KETEarlyWarningProblem) ketResultProblemLink.getRoleBObject();
	    PersistenceHelper.manager.delete(ketEarlyWarningProblem);
	}

    }

    @Override
    public KETEarlyWarningEndReq createEarlyWarningEndReq(Hashtable hash, Vector files) throws WTException {

	Transaction trx = new Transaction();
	trx.start();

	KETEarlyWarningEndReq ketEarlyWarningEndReq = KETEarlyWarningEndReq.newKETEarlyWarningEndReq();
	KETEarlyWarning ketEarlyWarning = (KETEarlyWarning) CommonUtil.getObject((String) hash.get("oid"));

	try {
	    Kogger.debug(getClass(), "********************" + "save service start");

	    // //////////////////////////////////////////// ketEarlyWarningEndReq info setting start
	    // //////////////////////////////////////////////////////

	    String ewsNumber = "EWSQ-" + DateUtil.getCurrentDateString("all").substring(2, 4)
		    + DateUtil.getCurrentDateString("all").substring(5, 7) + "-";
	    ewsNumber += ManageSequence.getSeqNo(ewsNumber, "000", "WTDocumentMaster", "WTDocumentNumber");

	    ketEarlyWarningEndReq.setNumber(ewsNumber);
	    ketEarlyWarningEndReq.setName(ewsNumber);

	    ketEarlyWarningEndReq.setRequestdesc((String) hash.get("requestDesc"));

	    // //////////////////////////////////////////// ketEarlyWarningEndReq info setting end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// folder setting start //////////////////////////////////////////////////////

	    WTContainerRef containerRef = WCUtil.getWTContainerRef();
	    String folderPath = EWSProperties.getInstance().getString("ews.folder.car") + DateUtil.getThisYear();
	    SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	    FolderHelper.assignLocation((FolderEntry) ketEarlyWarningEndReq, folder);

	    // //////////////////////////////////////////// folder setting end //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// ketEarlyWarningEndReq save start
	    // //////////////////////////////////////////////////////

	    ketEarlyWarningEndReq = (KETEarlyWarningEndReq) PersistenceHelper.manager.save(ketEarlyWarningEndReq);

	    KETEarlyWarningEndReqLink endReqLink = KETEarlyWarningEndReqLink.newKETEarlyWarningEndReqLink(
		    (WTDocumentMaster) ketEarlyWarning.getMaster(), (WTDocumentMaster) ketEarlyWarningEndReq.getMaster());

	    PersistenceServerHelper.manager.insert(endReqLink);

	    // //////////////////////////////////////////// ketEarlyWarningEndReq save end
	    // //////////////////////////////////////////////////////

	    Object existedFile = hash.get("existedFile");
	    ApplicationData appFile = null;

	    // //////////////////////////////////////////// problem file save start //////////////////////////////////////////////////////

	    // old file insert
	    if (existedFile instanceof String) {
		appFile = (ApplicationData) CommonUtil.getObject((String) existedFile);
		E3PSContentHelper.service.attach(ketEarlyWarningEndReq, appFile, false);
	    } else if (existedFile instanceof Vector) {
		Vector vecOld = (Vector) existedFile;
		for (int inx = 0; inx < vecOld.size(); inx++) {
		    appFile = (ApplicationData) CommonUtil.getObject((String) vecOld.elementAt(inx));
		    E3PSContentHelper.service.attach(ketEarlyWarningEndReq, appFile, false);
		}
	    }

	    if (files != null) {
		E3PSContentHelper.service.attach(ketEarlyWarningEndReq, files);
	    }

	    // //////////////////////////////////////////// problem file save end //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// KETEarlyWarningStep state save start
	    // //////////////////////////////////////////////////////

	    QueryResult isEarlyWarningStep = PersistenceHelper.navigate((WTDocumentMaster) ketEarlyWarning.getMaster(),
		    KETEarlyWarningStepLink.ROLE_AOBJECT_ROLE, KETEarlyWarningStepLink.class, false);
	    KETEarlyWarningStepLink ketEarlyWarningStepLink = null;
	    Object earlyWarningStepMaster = null;
	    ObjectData earlyWarninStepMasterData = null;

	    while (isEarlyWarningStep.hasMoreElements()) {
		ketEarlyWarningStepLink = (KETEarlyWarningStepLink) isEarlyWarningStep.nextElement();
		earlyWarningStepMaster = (Object) ObjectUtil.getLatestObject((WTDocumentMaster) ketEarlyWarningStepLink.getRoleAObject());
		earlyWarninStepMasterData = new ObjectData((WTDocument) earlyWarningStepMaster);
	    }

	    String stepOid = earlyWarninStepMasterData.getOid();
	    KETEarlyWarningStep ketEarlyWarningStep = (KETEarlyWarningStep) CommonUtil.getObject(stepOid);

	    LifeCycleServerHelper.service.setState((LifeCycleManaged) ketEarlyWarningStep, State.toState("EWRREQUEST")); // new step

	    // //////////////////////////////////////////// KETEarlyWarningStep state save end
	    // //////////////////////////////////////////////////////

	    trx.commit();

	    Kogger.debug(getClass(), "********************" + "save service end");

	    return ketEarlyWarningEndReq;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    Kogger.error(getClass(), wtpve);
	    return null;
	} catch (WTException wte) {
	    trx.rollback();
	    Kogger.error(getClass(), wte);
	    return null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(getClass(), e);
	    return null;
	}

    }

    @Override
    public KETEarlyWarningEndReq updateEarlyWarningEndReq(Hashtable hash, Vector files) throws WTException {

	Transaction trx = new Transaction();
	trx.start();

	KETEarlyWarningEndReq ketEarlyWarningEndReq = (KETEarlyWarningEndReq) CommonUtil.getObject((String) hash.get("endReqOid"));

	try {

	    Kogger.debug(getClass(), "********************" + "update service start");

	    // //////////////////////////////////////////// ketEarlyWarningEndReq save start
	    // //////////////////////////////////////////////////////

	    // object checkout
	    WorkInProgressHelper.service.checkout(ketEarlyWarningEndReq, WorkInProgressHelper.service.getCheckoutFolder(), "");
	    // create copy object
	    ketEarlyWarningEndReq = (KETEarlyWarningEndReq) WorkInProgressHelper.service.workingCopyOf(ketEarlyWarningEndReq);

	    // [START] [PLM System 1차 고도화] 초기유동관리 해제신청서 수정시 저장되지 않는 에러 개선, 2014-06-13, 김태현
	    // //////////////////////////////////////////// ketEarlyWarningEndReq info setting start
	    // //////////////////////////////////////////////////////
	    ketEarlyWarningEndReq.setRequestdesc((String) hash.get("requestDesc"));
	    // //////////////////////////////////////////// ketEarlyWarningEndReq info setting end
	    // //////////////////////////////////////////////////////
	    // [END] [PLM System 1차 고도화] 초기유동관리 해제신청서 수정시 저장되지 않는 에러 개선, 2014-06-13, 김태현

	    // ketEarlyWarningEndReq update
	    ketEarlyWarningEndReq = (KETEarlyWarningEndReq) PersistenceHelper.manager.modify(ketEarlyWarningEndReq);
	    // ketEarlyWarningEndReq refresh
	    ketEarlyWarningEndReq = (KETEarlyWarningEndReq) PersistenceHelper.manager.refresh(ketEarlyWarningEndReq);

	    // //////////////////////////////////////////// ketEarlyWarningEndReq save end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// problem file save start //////////////////////////////////////////////////////

	    QueryResult rs = ContentHelper.service.getContentsByRole(ketEarlyWarningEndReq, ContentRoleType.SECONDARY);

	    while (rs.hasMoreElements()) {
		ContentItem fileContent = (ContentItem) rs.nextElement();
		ketEarlyWarningEndReq = (KETEarlyWarningEndReq) E3PSContentHelper.service.delete(ketEarlyWarningEndReq, fileContent);
	    }

	    Object secondaryFile = hash.get("existedFile");
	    ApplicationData oldFile = null;

	    if (secondaryFile instanceof String) {
		oldFile = (ApplicationData) CommonUtil.getObject((String) secondaryFile);
		E3PSContentHelper.service.attach(ketEarlyWarningEndReq, oldFile, false);
	    } else if (secondaryFile instanceof Vector) {
		Vector vecOld = (Vector) secondaryFile;
		for (int inx = 0; inx < vecOld.size(); inx++) {
		    oldFile = (ApplicationData) CommonUtil.getObject((String) vecOld.elementAt(inx));
		    E3PSContentHelper.service.attach(ketEarlyWarningEndReq, oldFile, false);
		}
	    }

	    if (files != null) {
		E3PSContentHelper.service.attach(ketEarlyWarningEndReq, files);
	    }

	    // //////////////////////////////////////////// problem file save start //////////////////////////////////////////////////////

	    // ketEarlyWarningEndReq refresh
	    ketEarlyWarningEndReq = (KETEarlyWarningEndReq) PersistenceHelper.manager.refresh(ketEarlyWarningEndReq);

	    if (WorkInProgressHelper.isCheckedOut(ketEarlyWarningEndReq)) {
		// ketEarlyWarningEndReq checkin
		ketEarlyWarningEndReq = (KETEarlyWarningEndReq) WorkInProgressHelper.service.checkin(ketEarlyWarningEndReq, "");
	    }

	    trx.commit();

	    Kogger.debug(getClass(), "********************" + "update service end");

	    return ketEarlyWarningEndReq;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    Kogger.error(getClass(), wtpve);
	    return null;
	} catch (WTException wte) {
	    trx.rollback();
	    Kogger.error(getClass(), wte);
	    return null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(getClass(), e);
	    return null;
	}

    }

    @Override
    public Boolean deleteEarlyWarningEndReq(Hashtable hash, Vector files) throws WTException {

	boolean flag = false;

	// transaction start
	Transaction trx = new Transaction();
	trx.start();

	// KETEarlyWarningEndReq object
	KETEarlyWarningEndReq ketEarlyWarningEndReq = (KETEarlyWarningEndReq) CommonUtil.getObject((String) hash.get("endReqOid"));

	try {

	    // KETEarlyWarning object
	    QueryResult isEarlyWarning = PersistenceHelper.navigate((WTDocumentMaster) ketEarlyWarningEndReq.getMaster(),
		    KETEarlyWarningEndReqLink.ROLE_AOBJECT_ROLE, KETEarlyWarningEndReqLink.class, false);
	    KETEarlyWarningEndReqLink ketEarlyWarningEndReqLink = null;
	    Object earlyWarningMaster = null;
	    ObjectData earlyWarninMasterData = null;

	    while (isEarlyWarning.hasMoreElements()) {
		ketEarlyWarningEndReqLink = (KETEarlyWarningEndReqLink) isEarlyWarning.nextElement();
		earlyWarningMaster = (Object) ObjectUtil.getLatestObject((WTDocumentMaster) ketEarlyWarningEndReqLink.getRoleAObject());
		earlyWarninMasterData = new ObjectData((WTDocument) earlyWarningMaster);
	    }

	    String oid = earlyWarninMasterData.getOid();
	    KETEarlyWarning ketEarlyWarning = (KETEarlyWarning) CommonUtil.getObject(oid);

	    // KETEarlyWarningResult object
	    QueryResult isResult = PersistenceHelper.navigate((WTDocumentMaster) ketEarlyWarning.getMaster(),
		    KETEarlyWarningResultLink.ROLE_BOBJECT_ROLE, KETEarlyWarningResultLink.class, false);
	    KETEarlyWarningResultLink ketEarlyWarningResultLink = null;
	    Object resultMaster = null;
	    ObjectData resultMasterData = null;

	    while (isResult.hasMoreElements()) {
		ketEarlyWarningResultLink = (KETEarlyWarningResultLink) isResult.nextElement();
		resultMaster = (Object) ObjectUtil.getLatestObject((WTDocumentMaster) ketEarlyWarningResultLink.getRoleBObject());
		resultMasterData = new ObjectData((WTDocument) resultMaster);
	    }

	    String resultOid = resultMasterData.getOid();
	    KETEarlyWarningResult ketEarlyWarningResult = (KETEarlyWarningResult) CommonUtil.getObject(resultOid);

	    // file delete
	    QueryResult rs = ContentHelper.service.getContentsByRole(ketEarlyWarningEndReq, ContentRoleType.SECONDARY);
	    while (rs.hasMoreElements()) {
		ContentItem fileItem = (ContentItem) rs.nextElement();
		E3PSContentHelper.service.delete(ketEarlyWarningEndReq, fileItem);
	    }

	    // KETEarlyWarning object delete
	    PersistenceHelper.manager.delete(ketEarlyWarningEndReq);

	    // KETWfmHelper.service.createWorkItem(ketEarlyWarningResult);

	    // transaction end
	    trx.commit();

	    // success
	    flag = true;
	} catch (Exception e) {
	    trx.rollback();
	    // fail
	    flag = false;
	    Kogger.error(getClass(), e);
	}

	return flag;
    }

    @Override
    public KETEarlyWarningEnd createEarlyWarningEnd(Hashtable hash, Vector files) throws WTException {

	Transaction trx = new Transaction();
	trx.start();

	KETEarlyWarningEnd ketEarlyWarningEnd = KETEarlyWarningEnd.newKETEarlyWarningEnd();
	KETEarlyWarningEndReq ketEarlyWarningEndReq = (KETEarlyWarningEndReq) CommonUtil.getObject((String) hash.get("endReqOid"));
	KETEarlyWarning ketEarlyWarning = (KETEarlyWarning) CommonUtil.getObject((String) hash.get("oid"));
	KETEarlyWarningStep ketEarlyWarningStep = null;

	try {
	    Kogger.debug(getClass(), "********************" + "save service start");

	    // //////////////////////////////////////////// ketEarlyWarningEnd info setting start
	    // //////////////////////////////////////////////////////

	    String ewsNumber = "EWSE-" + DateUtil.getCurrentDateString("all").substring(2, 4)
		    + DateUtil.getCurrentDateString("all").substring(5, 7) + "-";
	    ewsNumber += ManageSequence.getSeqNo(ewsNumber, "000", "WTDocumentMaster", "WTDocumentNumber");

	    ketEarlyWarningEnd.setNumber(ewsNumber);
	    ketEarlyWarningEnd.setName(ewsNumber);

	    ketEarlyWarningEnd.setEndresult((String) hash.get("endResult"));
	    ketEarlyWarningEnd.setReason((String) hash.get("reason"));

	    String extensionDate = (String) hash.get("extensionDate");
	    Timestamp textensionDate = e3ps.common.util.DateUtil.convertDate2(extensionDate);
	    ketEarlyWarningEnd.setExtensiondate(textensionDate); // extension date

	    // //////////////////////////////////////////// ketEarlyWarningEnd info setting end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// folder setting start //////////////////////////////////////////////////////

	    WTContainerRef containerRef = WCUtil.getWTContainerRef();
	    String folderPath = EWSProperties.getInstance().getString("ews.folder.car") + DateUtil.getThisYear();
	    SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	    FolderHelper.assignLocation((FolderEntry) ketEarlyWarningEnd, folder);

	    // //////////////////////////////////////////// folder setting end //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// ketEarlyWarningEnd save start
	    // //////////////////////////////////////////////////////

	    ketEarlyWarningEnd = (KETEarlyWarningEnd) PersistenceHelper.manager.save(ketEarlyWarningEnd);

	    KETEndReqLink endReqLink = KETEndReqLink.newKETEndReqLink((WTDocumentMaster) ketEarlyWarningEndReq.getMaster(),
		    (WTDocumentMaster) ketEarlyWarningEnd.getMaster());

	    PersistenceServerHelper.manager.insert(endReqLink);

	    // //////////////////////////////////////////// ketEarlyWarningEnd save end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// file save start //////////////////////////////////////////////////////

	    if (files != null) {
		E3PSContentHelper.service.attach(ketEarlyWarningEnd, files);
	    }

	    // //////////////////////////////////////////// file save end //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// ketEarlyWarningStep info setting start
	    // //////////////////////////////////////////////////////

	    QueryResult isEarlyWarningStep = PersistenceHelper.navigate((WTDocumentMaster) ketEarlyWarning.getMaster(),
		    KETEarlyWarningStepLink.ROLE_AOBJECT_ROLE, KETEarlyWarningStepLink.class, false);
	    KETEarlyWarningStepLink ketEarlyWarningStepLink = null;
	    Object earlyWarningStepMaster = null;
	    ObjectData earlyWarninStepMasterData = null;

	    while (isEarlyWarningStep.hasMoreElements()) {
		ketEarlyWarningStepLink = (KETEarlyWarningStepLink) isEarlyWarningStep.nextElement();
		earlyWarningStepMaster = (Object) ObjectUtil.getLatestObject((WTDocumentMaster) ketEarlyWarningStepLink.getRoleAObject());
		earlyWarninStepMasterData = new ObjectData((WTDocument) earlyWarningStepMaster);
	    }

	    String stepOid = earlyWarninStepMasterData.getOid();
	    ketEarlyWarningStep = (KETEarlyWarningStep) CommonUtil.getObject(stepOid);

	    if (((String) hash.get("endResult")).equals("N")) {
		ketEarlyWarningStep.setEndDate(textensionDate); // working period(extension Date)
	    }

	    // //////////////////////////////////////////// ketEarlyWarningStep info setting end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// ketEarlyWarningStep save start
	    // //////////////////////////////////////////////////////

	    // object checkout
	    WorkInProgressHelper.service.checkout(ketEarlyWarningStep, WorkInProgressHelper.service.getCheckoutFolder(), "");
	    // create copy object
	    ketEarlyWarningStep = (KETEarlyWarningStep) WorkInProgressHelper.service.workingCopyOf(ketEarlyWarningStep);

	    // ketEarlyWarningStep update
	    ketEarlyWarningStep = (KETEarlyWarningStep) PersistenceHelper.manager.modify(ketEarlyWarningStep);
	    // ketEarlyWarningStep refresh
	    ketEarlyWarningStep = (KETEarlyWarningStep) PersistenceHelper.manager.refresh(ketEarlyWarningStep);

	    if (WorkInProgressHelper.isCheckedOut(ketEarlyWarningStep)) {
		// KETEarlyWarningStep checkin
		ketEarlyWarningStep = (KETEarlyWarningStep) WorkInProgressHelper.service.checkin(ketEarlyWarningStep, "");
	    }

	    // //////////////////////////////////////////// ketEarlyWarningStep save end
	    // //////////////////////////////////////////////////////

	    trx.commit();

	    Kogger.debug(getClass(), "********************" + "save service end");

	    return ketEarlyWarningEnd;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    Kogger.error(getClass(), wtpve);
	    return null;
	} catch (WTException wte) {
	    trx.rollback();
	    Kogger.error(getClass(), wte);
	    return null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(getClass(), e);
	    return null;
	}
    }

    @Override
    public KETEarlyWarningEnd updateEarlyWarningEnd(Hashtable hash, Vector files) throws WTException {

	Transaction trx = new Transaction();
	trx.start();

	KETEarlyWarningEnd ketEarlyWarningEnd = (KETEarlyWarningEnd) CommonUtil.getObject((String) hash.get("endOid"));
	KETEarlyWarning ketEarlyWarning = (KETEarlyWarning) CommonUtil.getObject((String) hash.get("oid"));
	KETEarlyWarningStep ketEarlyWarningStep = null;

	try {

	    Kogger.debug(getClass(), "********************" + "update service start");

	    // //////////////////////////////////////////// ketEarlyWarningEnd save start
	    // //////////////////////////////////////////////////////

	    // object checkout
	    WorkInProgressHelper.service.checkout(ketEarlyWarningEnd, WorkInProgressHelper.service.getCheckoutFolder(), "");
	    // create copy object
	    ketEarlyWarningEnd = (KETEarlyWarningEnd) WorkInProgressHelper.service.workingCopyOf(ketEarlyWarningEnd);

	    // [START] [PLM System 1차 고도화] 초기유동관리 해제판정 수정시 저장되지 않는 에러 개선, 2014-06-13, 김태현
	    // //////////////////////////////////////////// ketEarlyWarningEnd info setting start
	    // //////////////////////////////////////////////////////
	    ketEarlyWarningEnd.setEndresult((String) hash.get("endResult"));
	    ketEarlyWarningEnd.setReason((String) hash.get("reason"));

	    String extensionDate = (String) hash.get("extensionDate");
	    Timestamp textensionDate = e3ps.common.util.DateUtil.convertDate2(extensionDate);
	    ketEarlyWarningEnd.setExtensiondate(textensionDate); // extension date
	    // //////////////////////////////////////////// ketEarlyWarningEnd info setting end
	    // //////////////////////////////////////////////////////
	    // [END] [PLM System 1차 고도화] 초기유동관리 해제판정 수정시 저장되지 않는 에러 개선, 2014-06-13, 김태현

	    // ketEarlyWarningEnd update
	    ketEarlyWarningEnd = (KETEarlyWarningEnd) PersistenceHelper.manager.modify(ketEarlyWarningEnd);
	    // ketEarlyWarningEnd refresh
	    ketEarlyWarningEnd = (KETEarlyWarningEnd) PersistenceHelper.manager.refresh(ketEarlyWarningEnd);

	    // //////////////////////////////////////////// ketEarlyWarningEnd save start
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// file save start //////////////////////////////////////////////////////

	    QueryResult rs = ContentHelper.service.getContentsByRole(ketEarlyWarningEnd, ContentRoleType.SECONDARY);

	    while (rs.hasMoreElements()) {
		ContentItem fileContent = (ContentItem) rs.nextElement();
		ketEarlyWarningEnd = (KETEarlyWarningEnd) E3PSContentHelper.service.delete(ketEarlyWarningEnd, fileContent);
	    }

	    Object secondaryFile = hash.get("secondaryDelFile");
	    ApplicationData oldFile = null;

	    if (secondaryFile instanceof String) {
		oldFile = (ApplicationData) CommonUtil.getObject((String) secondaryFile);
		E3PSContentHelper.service.attach(ketEarlyWarningEnd, oldFile, false);
	    } else if (secondaryFile instanceof Vector) {
		Vector vecOld = (Vector) secondaryFile;
		for (int inx = 0; inx < vecOld.size(); inx++) {
		    oldFile = (ApplicationData) CommonUtil.getObject((String) vecOld.elementAt(inx));
		    E3PSContentHelper.service.attach(ketEarlyWarningEnd, oldFile, false);
		}
	    }

	    if (files != null) {
		E3PSContentHelper.service.attach(ketEarlyWarningEnd, files);
	    }

	    // //////////////////////////////////////////// file save start //////////////////////////////////////////////////////

	    // ketEarlyWarningEnd refresh
	    ketEarlyWarningEnd = (KETEarlyWarningEnd) PersistenceHelper.manager.refresh(ketEarlyWarningEnd);

	    if (WorkInProgressHelper.isCheckedOut(ketEarlyWarningEnd)) {
		// ketEarlyWarningEnd checkin
		ketEarlyWarningEnd = (KETEarlyWarningEnd) WorkInProgressHelper.service.checkin(ketEarlyWarningEnd, "");
	    }

	    // //////////////////////////////////////////// ketEarlyWarningStep info setting start
	    // //////////////////////////////////////////////////////

	    QueryResult isEarlyWarningStep = PersistenceHelper.navigate((WTDocumentMaster) ketEarlyWarning.getMaster(),
		    KETEarlyWarningStepLink.ROLE_AOBJECT_ROLE, KETEarlyWarningStepLink.class, false);
	    KETEarlyWarningStepLink ketEarlyWarningStepLink = null;
	    Object earlyWarningStepMaster = null;
	    ObjectData earlyWarninStepMasterData = null;

	    while (isEarlyWarningStep.hasMoreElements()) {
		ketEarlyWarningStepLink = (KETEarlyWarningStepLink) isEarlyWarningStep.nextElement();
		earlyWarningStepMaster = (Object) ObjectUtil.getLatestObject((WTDocumentMaster) ketEarlyWarningStepLink.getRoleAObject());
		earlyWarninStepMasterData = new ObjectData((WTDocument) earlyWarningStepMaster);
	    }

	    String stepOid = earlyWarninStepMasterData.getOid();
	    ketEarlyWarningStep = (KETEarlyWarningStep) CommonUtil.getObject(stepOid);

	    ketEarlyWarningStep.setEndDate(textensionDate); // working period(extension Date)

	    // //////////////////////////////////////////// ketEarlyWarningStep info setting end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// ketEarlyWarningStep save start
	    // //////////////////////////////////////////////////////

	    // object checkout
	    WorkInProgressHelper.service.checkout(ketEarlyWarningStep, WorkInProgressHelper.service.getCheckoutFolder(), "");
	    // create copy object
	    ketEarlyWarningStep = (KETEarlyWarningStep) WorkInProgressHelper.service.workingCopyOf(ketEarlyWarningStep);

	    // ketEarlyWarningStep update
	    ketEarlyWarningStep = (KETEarlyWarningStep) PersistenceHelper.manager.modify(ketEarlyWarningStep);
	    // ketEarlyWarningStep refresh
	    ketEarlyWarningStep = (KETEarlyWarningStep) PersistenceHelper.manager.refresh(ketEarlyWarningStep);

	    if (WorkInProgressHelper.isCheckedOut(ketEarlyWarningStep)) {
		// KETEarlyWarningStep checkin
		ketEarlyWarningStep = (KETEarlyWarningStep) WorkInProgressHelper.service.checkin(ketEarlyWarningStep, "");
	    }

	    // //////////////////////////////////////////// ketEarlyWarningStep save end
	    // //////////////////////////////////////////////////////

	    trx.commit();

	    Kogger.debug(getClass(), "********************" + "update service end");

	    return ketEarlyWarningEnd;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    Kogger.error(getClass(), wtpve);
	    return null;
	} catch (WTException wte) {
	    trx.rollback();
	    Kogger.error(getClass(), wte);
	    return null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(getClass(), e);
	    return null;
	}

    }

    @Override
    public Boolean deleteEarlyWarningEnd(Hashtable hash, Vector files) throws WTException {

	boolean flag = false;

	// transaction start
	Transaction trx = new Transaction();
	trx.start();

	// KETEarlyWarningEnd object
	KETEarlyWarningEnd ketEarlyWarningEnd = (KETEarlyWarningEnd) CommonUtil.getObject((String) hash.get("endOid"));
	KETEarlyWarning ketEarlyWarning = (KETEarlyWarning) CommonUtil.getObject((String) hash.get("oid"));
	KETEarlyWarningStep ketEarlyWarningStep = null;

	try {
	    // file delete
	    QueryResult rs = ContentHelper.service.getContentsByRole(ketEarlyWarningEnd, ContentRoleType.SECONDARY);
	    while (rs.hasMoreElements()) {
		ContentItem fileItem = (ContentItem) rs.nextElement();
		E3PSContentHelper.service.delete(ketEarlyWarningEnd, fileItem);
	    }

	    // KETEarlyWarningEnd object delete
	    PersistenceHelper.manager.delete(ketEarlyWarningEnd);

	    // //////////////////////////////////////////// ketEarlyWarningStep info setting start
	    // //////////////////////////////////////////////////////

	    QueryResult isEarlyWarningStep = PersistenceHelper.navigate((WTDocumentMaster) ketEarlyWarning.getMaster(),
		    KETEarlyWarningStepLink.ROLE_AOBJECT_ROLE, KETEarlyWarningStepLink.class, false);
	    KETEarlyWarningStepLink ketEarlyWarningStepLink = null;
	    Object earlyWarningStepMaster = null;
	    ObjectData earlyWarninStepMasterData = null;

	    while (isEarlyWarningStep.hasMoreElements()) {
		ketEarlyWarningStepLink = (KETEarlyWarningStepLink) isEarlyWarningStep.nextElement();
		earlyWarningStepMaster = (Object) ObjectUtil.getLatestObject((WTDocumentMaster) ketEarlyWarningStepLink.getRoleAObject());
		earlyWarninStepMasterData = new ObjectData((WTDocument) earlyWarningStepMaster);
	    }

	    String stepOid = earlyWarninStepMasterData.getOid();
	    ketEarlyWarningStep = (KETEarlyWarningStep) CommonUtil.getObject(stepOid);

	    ketEarlyWarningStep.setEndDate(ketEarlyWarning.getEndDate()); // working period(extension Date)

	    // //////////////////////////////////////////// ketEarlyWarningStep info setting end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// ketEarlyWarningStep save start
	    // //////////////////////////////////////////////////////

	    // object checkout
	    WorkInProgressHelper.service.checkout(ketEarlyWarningStep, WorkInProgressHelper.service.getCheckoutFolder(), "");
	    // create copy object
	    ketEarlyWarningStep = (KETEarlyWarningStep) WorkInProgressHelper.service.workingCopyOf(ketEarlyWarningStep);

	    // ketEarlyWarningStep update
	    ketEarlyWarningStep = (KETEarlyWarningStep) PersistenceHelper.manager.modify(ketEarlyWarningStep);
	    // ketEarlyWarningStep refresh
	    ketEarlyWarningStep = (KETEarlyWarningStep) PersistenceHelper.manager.refresh(ketEarlyWarningStep);

	    if (WorkInProgressHelper.isCheckedOut(ketEarlyWarningStep)) {
		// KETEarlyWarningStep checkin
		ketEarlyWarningStep = (KETEarlyWarningStep) WorkInProgressHelper.service.checkin(ketEarlyWarningStep, "");
	    }

	    // //////////////////////////////////////////// ketEarlyWarningStep save end
	    // //////////////////////////////////////////////////////

	    // transaction end
	    trx.commit();

	    // success
	    flag = true;
	} catch (Exception e) {
	    trx.rollback();
	    // fail
	    flag = false;
	    Kogger.error(getClass(), e);
	}

	return flag;
    }

    @Override
    public KETEarlyWarningStep stopEarlyWarning(Hashtable hash) throws WTException {

	// transaction start
	Transaction trx = new Transaction();
	trx.start();

	KETEarlyWarningStep ketEarlyWarningStep = null;

	// update object
	ketEarlyWarningStep = (KETEarlyWarningStep) CommonUtil.getObject((String) hash.get("stepOid"));

	try {
	    Kogger.debug(getClass(), "********************" + "update service start");

	    // //////////////////////////////////////////// ketEarlyWarningStep info setting start
	    // //////////////////////////////////////////////////////

	    String step = ketEarlyWarningStep.getLifeCycleState().toString();

	    Kogger.debug(getClass(), "********************step : " + step);

	    ketEarlyWarningStep.setStatus(step); // old step
	    ketEarlyWarningStep.setStopdate(DateUtil.getCurrentTimestamp());
	    ketEarlyWarningStep.setReason((String) hash.get("stopReason"));

	    // //////////////////////////////////////////// ketEarlyWarningStep info setting end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// ketEarlyWarningStep save start
	    // //////////////////////////////////////////////////////

	    // object checkout
	    WorkInProgressHelper.service.checkout(ketEarlyWarningStep, WorkInProgressHelper.service.getCheckoutFolder(), "");
	    // create copy object
	    ketEarlyWarningStep = (KETEarlyWarningStep) WorkInProgressHelper.service.workingCopyOf(ketEarlyWarningStep);

	    // ketEarlyWarningStep update
	    ketEarlyWarningStep = (KETEarlyWarningStep) PersistenceHelper.manager.modify(ketEarlyWarningStep);
	    // ketEarlyWarningStep refresh
	    ketEarlyWarningStep = (KETEarlyWarningStep) PersistenceHelper.manager.refresh(ketEarlyWarningStep);

	    // //////////////////////////////////////////// ketEarlyWarningStep save end
	    // //////////////////////////////////////////////////////

	    if (WorkInProgressHelper.isCheckedOut(ketEarlyWarningStep)) {
		// KETEarlyWarningStep checkin
		ketEarlyWarningStep = (KETEarlyWarningStep) WorkInProgressHelper.service.checkin(ketEarlyWarningStep, "");
	    }

	    LifeCycleServerHelper.service.setState((LifeCycleManaged) ketEarlyWarningStep, State.toState("EWRCANCELED")); // new step

	    // transaction end
	    trx.commit();

	    Kogger.debug(getClass(), "********************" + "update service end");

	    return ketEarlyWarningStep;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    Kogger.error(getClass(), wtpve);
	    return null;
	} catch (WTException wte) {
	    trx.rollback();
	    Kogger.error(getClass(), wte);
	    return null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(getClass(), e);
	    return null;
	}
    }

    @Override
    public KETEarlyWarningStep reopenEarlyWarning(Hashtable hash) throws WTException {

	// transaction start
	Transaction trx = new Transaction();
	trx.start();

	KETEarlyWarningStep ketEarlyWarningStep = null;

	// update object
	ketEarlyWarningStep = (KETEarlyWarningStep) CommonUtil.getObject((String) hash.get("stepOid"));

	try {
	    Kogger.debug(getClass(), "********************" + "update service start");

	    // //////////////////////////////////////////// ketEarlyWarningStep info setting start
	    // //////////////////////////////////////////////////////

	    String step = ketEarlyWarningStep.getStatus();

	    // [START] [PLM System 1차 고도화] '중단'후 '재개'되지 않는 에러 수정, 2014-06-17, 김태현
	    // LifeCycleServerHelper.service.setState((LifeCycleManaged)ketEarlyWarningStep, State.toState(step)); //new step
	    LifeCycleServerHelper.service.setState((LifeCycleManaged) ketEarlyWarningStep, State.toState("REWORK")); // new step
	    // [END] [PLM System 1차 고도화] '중단'후 '재개'되지 않는 에러 수정, 2014-06-17, 김태현

	    // //////////////////////////////////////////// ketEarlyWarningStep info setting end
	    // //////////////////////////////////////////////////////

	    // transaction end
	    trx.commit();

	    Kogger.debug(getClass(), "********************" + "update service end");

	    return ketEarlyWarningStep;

	} catch (WTException wte) {
	    trx.rollback();
	    Kogger.error(getClass(), wte);
	    return null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(getClass(), e);
	    return null;
	}
    }

    @Override
    public Boolean updatePartner() throws WTException {

	MethodContext methodcontext = null;
	WTConnection wtconnection = null;
	boolean flag = false;

	try {
	    methodcontext = MethodContext.getContext();
	    wtconnection = (WTConnection) methodcontext.getConnection();

	    VendorInfoInterface vendorif = new VendorInfoInterface();
	    ArrayList vendorList = vendorif.getVendorInfo();

	    PartnerDao partnerDao = new PartnerDao();
	    flag = partnerDao.InsertPartner(vendorList, wtconnection);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !wtconnection.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}

	return flag;
    }

}
