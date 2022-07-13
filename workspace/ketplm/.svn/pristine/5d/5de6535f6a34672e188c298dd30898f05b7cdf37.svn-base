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

package e3ps.bom.service;

import java.io.FileWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException; // Preserved unmodeled dependency
import java.sql.Statement; // Preserved unmodeled dependency
import java.util.ArrayList; // Preserved unmodeled dependency
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.change2.WTChangeOrder2;
import wt.clients.vc.CheckInOutTaskLogic; // Preserved unmodeled dependency
import wt.configuration.TraceCode; // Preserved unmodeled dependency
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper; // Preserved unmodeled dependency
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult; // Preserved unmodeled dependency
import wt.fc.ReferenceFactory; // Preserved unmodeled dependency
import wt.folder.Folder; // Preserved unmodeled dependency
import wt.folder.FolderEntry; // Preserved unmodeled dependency
import wt.folder.FolderHelper; // Preserved unmodeled dependency
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.LifeCycleServerHelper;
import wt.lifecycle.State;
import wt.org.WTPrincipalReference; // Preserved unmodeled dependency
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pds.StatementSpec; // Preserved unmodeled dependency
import wt.pom.Transaction; // Preserved unmodeled dependency
import wt.pom.WTConnection;
import wt.query.QuerySpec; // Preserved unmodeled dependency
import wt.query.SearchCondition; // Preserved unmodeled dependency
import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.vc.VersionControlHelper; // Preserved unmodeled dependency
import wt.vc.Versioned; // Preserved unmodeled dependency
import wt.vc.config.ConfigSpec; // Preserved unmodeled dependency
import wt.vc.config.LatestConfigSpec; // Preserved unmodeled dependency
import wt.vc.wip.WorkInProgressHelper; // Preserved unmodeled dependency
import wt.vc.wip.Workable; // Preserved unmodeled dependency
import e3ps.bom.dao.BOMSearchUtilDao; // Preserved unmodeled dependency
import e3ps.bom.dao.PartDao; // Preserved unmodeled dependency
import e3ps.bom.dao.WFBomEcoPartUsageQry; // Preserved unmodeled dependency
import e3ps.bom.dao.WFBomPartUsageQry; // Preserved unmodeled dependency
import e3ps.bom.dao.pool.DBConnectionManager; // Preserved unmodeled dependency
import e3ps.bom.entity.KETBomEcoHeader; // Preserved unmodeled dependency
import e3ps.bom.entity.KETBomHeader; // Preserved unmodeled dependency
import e3ps.bom.entity.KETPartUsageLink; // Preserved unmodeled dependency
import e3ps.bom.framework.util.Registry; // Preserved unmodeled dependency
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil; // Preserved unmodeled dependency
import e3ps.common.util.KETObjectUtil; // Preserved unmodeled dependency
import e3ps.ecm.beans.EcmUtil;
import e3ps.ecm.beans.ProdEcoBeans;
import e3ps.ecm.dao.EcmComDao; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.sap.BomEcoInfoInterface;
import e3ps.sap.BomEcoInfoInterfaceQry;
import e3ps.sap.BomInfoInterface; // Preserved unmodeled dependency
import e3ps.sap.ErpPartInterFace;
import e3ps.sap.InfoInterfaceQry;
import e3ps.sap.ItemMasterInterface; // Preserved unmodeled dependency
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.service.KETWfmHelper; // Preserved unmodeled dependency
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.base.service.internal.PartDieInfoHandler;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartSpecSetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.KogDbUtil;
// Preserved unmodeled dependency
import ext.ket.shared.log.Kogger;

// Preserved unmodeled dependency

/**
 * 
 * <p>
 * Use the <code>newStandardKETBomService</code> static factory method(s), not the <code>StandardKETBomService</code> constructor, to
 * construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of
 * the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

public class StandardKETBomService extends StandardManager implements KETBomService, Serializable {

    private static final String RESOURCE = "e3ps.bom.service.serviceResource";
    private static final String CLASSNAME = StandardKETBomService.class.getName();

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
     * @return StandardKETBomService
     * @exception wt.util.WTException
     **/
    public static StandardKETBomService newStandardKETBomService() throws WTException {

	StandardKETBomService instance = new StandardKETBomService();
	instance.initialize();
	return instance;
    }

    @Override
    public Vector searchItem(Hashtable searchAttrHash) throws WTException {

	Vector outputParams = new Vector();
	Hashtable<String, String> part = new Hashtable<String, String>();
	ArrayList<Hashtable<String, String>> partList = new ArrayList<Hashtable<String, String>>();
	try {
	    PartDao dao = new PartDao();
	    partList = dao.searchPartApplet(searchAttrHash);

	    for (int i = 0; i < partList.size(); i++) {
		part = partList.get(i);

		Hashtable<String, String> hasItem = new Hashtable<String, String>();
		hasItem.put("itemCode", checkNVL(part.get("number")));
		hasItem.put("description", checkNVL(part.get("name")));
		hasItem.put("defaultunit", checkNVL(part.get("unit")));
		// hasItem.put( "createdBy", checkNVL( part.get("unit") ) );
		// hasItem.put( "createdDate", checkNVL( part.get("unit") ) );
		hasItem.put("status", checkNVL(part.get("state")));
		hasItem.put("statusKr", checkNVL(part.get("stateKr")));
		hasItem.put("version", checkNVL(part.get("version")));
		hasItem.put("type", checkNVL(part.get("type")));
		hasItem.put("typeValue", checkNVL(part.get("typeValue")));
		hasItem.put("isDeleted", checkNVL(part.get("isDeleted")));
		hasItem.put("versionSort", checkNVL(part.get("versionSort")));
		hasItem.put("iteration", checkNVL(part.get("iteration")));
		hasItem.put("latestIteration", checkNVL(part.get("latestIteration")));
		hasItem.put("checkout", checkNVL(part.get("checkout")));
		hasItem.put("oid", checkNVL(part.get("oid")));
		hasItem.put("oidMaster", checkNVL(part.get("oidMaster")));

		outputParams.addElement(hasItem);
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	}
	return outputParams;

	// return null;
    }

    @Override
    public String searchDuplicationItem(Hashtable searchAttrHash) throws WTException {

	String searchDupItemStr = "";
	try {
	    Vector searchDupItemVector = new Vector();
	    KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
	    searchDupItemVector = query.searchDuplicationItem(searchAttrHash);

	    if (searchDupItemVector.size() > 0) {
		searchDupItemStr = "Y";
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	}
	return searchDupItemStr;

	// return null;
    }

    @Override
    public String createBom(Hashtable createAttrHash) throws WTException {

	KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
	return query.createBom(createAttrHash);
	// return null;

    }

    @Override
    public Vector myBom(Hashtable inputParams) throws WTException {

	Vector outputParams = new Vector();
	try {
	    if (inputParams.size() > 0) {
		String userId = "";
		String orgCode = "";
		String strItemCode = "";

		Vector vecHeaders = new Vector();
		String bomOid = "";

		KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
		KETBomHeader header = null;
		ReferenceFactory rf = new ReferenceFactory();

		userId = checkNVL((String) inputParams.get("userId"));
		orgCode = checkNVL((String) inputParams.get("orgCode"));
		Vector vecItemCode = new Vector();
		vecItemCode = (Vector) inputParams.get("vecItemCode");

		vecHeaders = query.getAllBomHeaders(orgCode, vecItemCode, userId);

		for (int i = 0; i < vecHeaders.size(); i++) {
		    header = (KETBomHeader) vecHeaders.elementAt(i);
		    bomOid = rf.getReferenceString(header);

		    Hashtable hasHeader = new Hashtable();
		    hasHeader.put("itemCode", checkNVL((String) header.getNewBOMCode()));
		    hasHeader.put("description", checkNVL((String) header.getDescription()));
		    hasHeader.put("createdBy", checkNVL((String) header.getCreatorFullName()));
		    hasHeader.put("createdDate", checkNVL((String) header.getCreateTimestamp().toString()));

		    // shin...
		    hasHeader.put("bomState", checkNVL((String) header.getLifeCycleState().getDisplay()));
		    // hasHeader.put("bomState", checkNVL((String)header.getBOMStatus()));

		    hasHeader.put("bomOid", checkNVL(bomOid));

		    outputParams.addElement(hasHeader);
		}
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	}
	return outputParams;

	// return null;
    }

    @Override
    public Vector searchBom(Hashtable inputParams) throws WTException {

	Vector outputParams = new Vector();
	try {
	    if (inputParams.size() > 0) {
		String orgCode = "";
		String strItemCode = "";
		String strItemName = "";
		String strState = "";
		String strCreated = "";
		String strCreatedDateFrom = "";
		String strCreatedDateTo = "";
		String strOperation = "";

		Vector vecHeaders = new Vector();
		String bomOid = "";

		KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
		KETBomHeader header = null;
		ReferenceFactory rf = new ReferenceFactory();

		orgCode = checkNVL((String) inputParams.get("orgCode"));
		strState = checkNVL((String) inputParams.get("bomState"));
		strItemCode = checkNVL((String) inputParams.get("itemCode"));
		strItemName = checkNVL((String) inputParams.get("itemName"));
		strCreated = checkNVL((String) inputParams.get("created"));
		strCreatedDateFrom = checkNVL((String) inputParams.get("createdDateFrom"));
		strCreatedDateTo = checkNVL((String) inputParams.get("createdDateTo"));
		strOperation = checkNVL((String) inputParams.get("operation"));

		if (strOperation.equals("BOM")) {
		    vecHeaders = query.searchBomHeaders(orgCode, strItemCode, strItemName, strCreatedDateFrom, strCreatedDateTo, strState, strCreated);

		    for (int i = 0; i < vecHeaders.size(); i++) {
			header = (KETBomHeader) vecHeaders.elementAt(i);
			bomOid = rf.getReferenceString(header);

			Hashtable hasHeader = new Hashtable();
			hasHeader.put("itemCode", checkNVL((String) header.getNewBOMCode()));
			hasHeader.put("description", checkNVL((String) header.getDescription()));
			hasHeader.put("createdBy", checkNVL((String) header.getCreatorFullName()));
			hasHeader.put("createdDate", checkNVL((String) header.getCreateTimestamp().toString()));
			// shin...
			hasHeader.put("bomState", checkNVL((String) header.getLifeCycleState().getDisplay()));
			// hasHeader.put("bomState", checkNVL((String)header.getBOMStatus()));

			hasHeader.put("bomOid", checkNVL(bomOid));
			outputParams.addElement(hasHeader);
		    }
		}
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	}
	return outputParams;

	// return null;
    }

    @Override
    public Hashtable getBom(String bomOid) throws WTException {

	Hashtable outputParams = new Hashtable();
	try {
	    if (bomOid.length() > 0) {
		ReferenceFactory rf = new ReferenceFactory();
		String oid = checkNVL(bomOid);

		KETBomHeader header = null;
		header = (KETBomHeader) rf.getReference(oid).getObject();

		String newBomCode = checkNVL((String) header.getNewBOMCode());

		// ?? BOM ??? ????, ???? ??
		WTPart part = KETPartHelper.service.getPart(newBomCode);
		String strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
		String bomFlag = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpBOMFlag);

		outputParams.put("lifeCycleState", checkNVL(header.getLifeCycleState().toString()));
		outputParams.put("lifeCycleStateKr", checkNVL((String) header.getLifeCycleState().getDisplay().toString()));
		outputParams.put("publicApproveDate", checkNVL(header.getApproveDate() == null ? "" : (String) header.getApproveDate().toString()));
		outputParams.put("publicApproveDept", checkNVL((String) header.getApproverDept()));
		// outputParams.put("publicApproveId", checkNVL((String)header.getApproverID()));
		outputParams.put("publicApproveName", checkNVL((String) header.getApproverName()));
		outputParams.put("publicOwnerID", checkNVL((String) header.getCreator().toString()));
		outputParams.put("publicOwnerDate", checkNVL(header.getCreateTimestamp() == null ? "" : (String) header.getCreateTimestamp().toString()));
		outputParams.put("publicOwnerDept", checkNVL((String) header.getCreatorDept()));
		outputParams.put("publicOwnerName", checkNVL((String) header.getCreatorFullName()));
		outputParams.put("publicOwnerEmail", checkNVL((String) header.getCreatorEMail()));
		outputParams.put("publicModelName", newBomCode);
		outputParams.put("publicModelDesc", checkNVL((String) header.getDescription()));
		outputParams.put("publicModelUom", checkNVL((String) header.getUnitOfQuantity()));
		// outputParams.put("publicModelUserItemType", checkNVL((String)header.getUserItemType()));

		// shin..........
		// outputParams.put("publicModelStatus", checkNVL((String)header.getBOMStatus()));
		outputParams.put("publicModelStatus", checkNVL(part.getLifeCycleState().toString())); // BOM WF Status (Eng)
		outputParams.put("publicModelStatusKr", checkNVL(part.getLifeCycleState().getDisplay())); // BOM WF Status (Kor)

		// outputParams.put("publicBasicModelName", checkNVL((String)header.getRefBomCode()));
		// outputParams.put("publicBasicModelDesc", checkNVL((String)header.getRefDescription()));
		// outputParams.put("publicCheckOutStatus", checkNVL((String)header.getCheckOutStatus()));
		outputParams.put("publicCheckOutStatus", checkNVL((String) header.getCheckoutStatus()));
		outputParams.put("publicTransFlag", checkNVL((String) header.getTransferFlag()));
		outputParams.put("bomHeaderType", checkNVL(strType));
		outputParams.put("BOMFlag", checkNVL(bomFlag));
		outputParams.put("bomBoxQuantity", checkNVL((String) header.getBoxQuantity()));
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	}
	return outputParams;

	// return null;
    }

    @Override
    public Vector searchWorkList() throws WTException {
	KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
	return query.searchWorkList();
    }

    @Override
    public void removeBom(String bomOid) throws WTException {
	try {
	    ReferenceFactory rf = new ReferenceFactory();

	    KETBomHeader header = null;
	    header = (KETBomHeader) rf.getReference(bomOid).getObject();
	    // wt.workflow.engine.WfEngineHelper.service.terminateObjectsRunningWorkflows(header);
	    PersistenceHelper.manager.delete((Persistable) header);
	} catch (WTException wte) {
	    Kogger.error(getClass(), wte);
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	}
    }

    @Override
    public void setCoworkerCheckOut(Hashtable inputParams) throws WTException {

	Vector itemCodeVector = new Vector();
	String bomOid = (String) inputParams.get("bomOid");
	itemCodeVector = (Vector) inputParams.get("itemCode");
	String bomEcoFlag = (String) inputParams.get("bomEcoFlag");

	Hashtable searchHash = new Hashtable();
	Vector qrVector = new Vector();
	KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
	WTPart item = null;
	Hashtable checkoutHash = new Hashtable();

	try {
	    for (int i = 0; i < itemCodeVector.size(); i++) {
		searchHash.put("itemCode", (String) itemCodeVector.elementAt(i));
		qrVector = query.searchItem(searchHash);
		// if( qrVector.size() != 0 )
		// {
		// item = (WTPart)qrVector.elementAt(0);
		// checkoutHash = KETObjectUtil.checkOut(item);
		// }

		searchHash.clear();
		checkoutHash.clear();
	    }

	    if (bomEcoFlag.equals("N")) {
		ReferenceFactory rf = new ReferenceFactory();

		KETBomHeader header = null;
		header = (KETBomHeader) rf.getReference(bomOid).getObject();

		header.setCheckoutStatus("2");

		header = (KETBomHeader) PersistenceHelper.manager.modify(header);
	    } else {
		ReferenceFactory rf = new ReferenceFactory();

		KETBomEcoHeader header = null;
		header = (KETBomEcoHeader) rf.getReference(bomOid).getObject();

		header.setCheckoutStatus("2");

		header = (KETBomEcoHeader) PersistenceHelper.manager.modify(header);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new WTException(e.getMessage());
	}

    }

    @Override
    public void setCoworkerCheckIn(Hashtable inputParams) throws WTException {

	Vector itemCodeVector = new Vector();
	String bomOid = (String) inputParams.get("bomOid");
	String checkInFlag = (String) inputParams.get("flag");
	itemCodeVector = (Vector) inputParams.get("itemCode");
	String bomEcoFlag = (String) inputParams.get("bomEcoFlag");

	Hashtable searchHash = new Hashtable();
	Vector qrVector = new Vector();
	KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
	WTPart item = null;

	try {
	    for (int i = 0; i < itemCodeVector.size(); i++) {
		searchHash.put("itemCode", (String) itemCodeVector.elementAt(i));
		qrVector = query.searchItem(searchHash);
		// if( qrVector.size() != 0 )
		// {
		// item = (WTPart)qrVector.elementAt(0);
		// if( KETObjectUtil.isCheckout(item) )
		// {
		// WorkInProgressHelper.service.checkin( item, "BOM Check-In");
		// }
		// }
	    }

	    if (!checkInFlag.equals("Y")) {
		if (bomEcoFlag.equals("N")) {
		    ReferenceFactory rf = new ReferenceFactory();

		    KETBomHeader header = null;
		    header = (KETBomHeader) rf.getReference(bomOid).getObject();

		    header.setCheckoutStatus("1");

		    header = (KETBomHeader) PersistenceHelper.manager.modify(header);
		} else {
		    ReferenceFactory rf = new ReferenceFactory();

		    KETBomEcoHeader header = null;
		    header = (KETBomEcoHeader) rf.getReference(bomOid).getObject();

		    header.setCheckoutStatus("1");

		    header = (KETBomEcoHeader) PersistenceHelper.manager.modify(header);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new WTException(e.getMessage());
	}

    }

    @Override
    public String setAllEndWorking(String bomOid, String headerType) throws WTException {
	KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
	return query.setAllEndWorking(bomOid, headerType);
	// return null;
    }

    @Override
    public Vector getCheckOuter(Vector inputParams) throws WTException {

	// return null;

	WTPart item = null;
	WTPrincipalReference principalRef = null;
	Vector returnVector = new Vector();
	StringBuffer userString = new StringBuffer();

	Hashtable searchHash = new Hashtable();
	Vector qrVector = new Vector();
	KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
	Workable workingCopy = null;

	try {
	    for (int i = 0; i < inputParams.size(); i++) {
		searchHash.put("itemCode", (String) inputParams.elementAt(i));

		qrVector = query.searchItem(searchHash);

		if (qrVector.size() != 0) {
		    item = (WTPart) qrVector.elementAt(0);
		    /*
	             * if( WorkInProgressHelper.isWorkingCopy((Workable)item) ) { principalRef = item.getModifier();
	             * 
	             * userString.append( item.getNumber() ).append( "|" ).append( principalRef.getFullName() ).append( "(" ).append(
	             * principalRef.getEMail() ).append( ")" ); returnVector.addElement(userString.toString() );
	             * 
	             * break; }
	             */

		    if (CheckInOutTaskLogic.isCheckedOut((Workable) item)) {
			if (CheckInOutTaskLogic.isWorkingCopy((Workable) item)) {
			    principalRef = item.getModifier();

			    userString.append(item.getNumber()).append("|").append(principalRef.getFullName()).append("(").append(principalRef.getEMail()).append(")");
			    returnVector.addElement(userString.toString());
			} else {
			    userString.append(item.getNumber()).append("|").append(item.getLockerFullName()).append("(").append(item.getLockerEMail()).append(")");
			    returnVector.addElement(userString.toString());
			}
		    }

		    userString.delete(0, userString.length());
		}
	    }

	    return returnVector;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return null;
	}

    }

    @Override
    public String changeEndWorkFlag(Hashtable inputParams) throws WTException {

	return null;
    }

    @Override
    public Vector getItemVersion(Hashtable inputParams) throws WTException {
	String orgCode = (String) inputParams.get("orgCode");
	Vector itemCodeVector = new Vector();
	itemCodeVector = (Vector) inputParams.get("itemCode");
	Vector qrVector = new Vector();
	KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();

	try {
	    qrVector = query.getVersionInfo(orgCode, itemCodeVector);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return qrVector;

	// return null;
    }

    @Override
    public Hashtable getBomEco(String bomEcoOid) throws WTException {
	Hashtable outputParams = new Hashtable();
	try {
	    if (bomEcoOid.length() > 0) {
		ReferenceFactory rf = new ReferenceFactory();
		String oid = checkNVL(bomEcoOid);

		KETBomEcoHeader header = null;
		header = (KETBomEcoHeader) rf.getReference(oid).getObject();

		outputParams.put("lifeCycleState", checkNVL((String) header.getLifeCycleState().toString()));
		outputParams.put("lifeCycleStateKr", checkNVL((String) header.getLifeCycleState().getDisplay()));
		outputParams.put("publicOwnerID", checkNVL((String) header.getCreator().toString()));
		outputParams.put("publicOwnerDate", checkNVL(header.getCreateTimestamp() == null ? "" : (String) header.getCreateTimestamp().toString()));
		outputParams.put("publicOwnerName", checkNVL((String) header.getCreatorFullName()));
		outputParams.put("publicOwnerEmail", checkNVL((String) header.getCreatorEMail()));
		outputParams.put("publicModelName", checkNVL((String) header.getEcoItemCode()));
		outputParams.put("publicTransFlag", checkNVL((String) header.getTransferFlag()));
		outputParams.put("publicBomEcoNumber", checkNVL((String) header.getEcoHeaderNumber()));
		outputParams.put("publicBomEcoType", checkNVL((String) header.getIsMultiple()));

		outputParams.put("state", checkNVL((String) header.getLifeCycleState().getDisplay()));
		outputParams.put("creatorId", checkNVL((String) header.getCreator().toString()));
		outputParams.put("creatorDate", checkNVL(header.getCreateTimestamp() == null ? "" : (String) header.getCreateTimestamp().toString()));
		outputParams.put("creatorName", checkNVL((String) header.getCreatorFullName()));
		outputParams.put("itemCode", checkNVL((String) header.getEcoItemCode()));
		outputParams.put("transferflag", checkNVL((String) header.getTransferFlag()));
		outputParams.put("headerId", bomEcoOid);
		outputParams.put("headerNumber", checkNVL((String) header.getEcoHeaderNumber()));
		outputParams.put("ecoType", checkNVL((String) header.getIsMultiple()));
		outputParams.put("ecoNo", checkNVL((String) header.getEcoHeaderNumber()));
		outputParams.put("boxQuantity", checkNVL((String) header.getBoxQuantity()));

		String ecoItemCode = checkNVL((String) header.getEcoItemCode());
		WTPart item = null;
		item = searchItem(ecoItemCode);
		if (KETObjectUtil.isCheckout(item)) {
		    outputParams.put("publicCheckOutStatus", "2"); // 2 : Check-Out
		} else {
		    outputParams.put("publicCheckOutStatus", "1"); // 1 : Check-In
		}
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	}
	return outputParams;
    }

    @Override
    public Vector searchBomEco(Hashtable inputParams) throws WTException {
	String itemCode = inputParams.get("itemCode") != null ? (String) inputParams.get("itemCode") : "";
	String itemName = inputParams.get("itemName") != null ? (String) inputParams.get("itemName") : "";
	String ecoNo = inputParams.get("ecoNo") != null ? (String) inputParams.get("ecoNo") : "";
	String reasonType = inputParams.get("reasonType") != null ? (String) inputParams.get("reasonType") : "";
	String reason = inputParams.get("reason") != null ? (String) inputParams.get("reason") : "";
	String state = inputParams.get("state") != null ? (String) inputParams.get("state") : "";
	String createdFrom = inputParams.get("createdFrom") != null ? (String) inputParams.get("createdFrom") : "";
	String createdTo = inputParams.get("createdTo") != null ? (String) inputParams.get("createdTo") : "";

	KETBOMECOQueryBean ecoQueryBean = new KETBOMECOQueryBean();

	return ecoQueryBean.searchBomEco(inputParams);
    }

    @Override
    public Vector searchBomEcoWorkList() throws WTException {

	// KETBOMECOQueryBean query = new KETBOMECOQueryBean();
	// return query.searchWorkEcoList();
	return null;
    }

    @Override
    public Vector searchParentItem(String itemCode, String itemDesc, String orgCode) throws WTException {
	KETBOMECOQueryBean ecoQueryBean = new KETBOMECOQueryBean();
	return ecoQueryBean.searchParentItem(itemCode, itemDesc, orgCode);
    }

    @Override
    public String manageCoworker(String bomOid, Vector coworkers) throws WTException {
	String returnStr = "";
	try {
	    if (coworkers != null && coworkers.size() > 0) {
		KETObjectUtil.setCoWorker(bomOid, coworkers);
		returnStr = "OK";
	    }
	} catch (Exception ex) {
	    returnStr = "Error";
	}
	return returnStr;
    }

    @Override
    public String getItemOid(String itemCode) throws WTException {
	String itemOidStr = "";
	try {
	    wt.org.WTUser user = (wt.org.WTUser) wt.session.SessionHelper.manager.getPrincipal();

	    ReferenceFactory rf = new ReferenceFactory();
	    KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
	    WTPart item = null;

	    item = query.searchItem(itemCode);

	    if (item != null) {
		itemOidStr = PersistenceHelper.getObjectIdentifier(item).getStringValue();
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	}
	return itemOidStr;
    }

    @Override
    public void checkOutItem(Vector inputParams) throws WTException {
	Hashtable searchHash = new Hashtable();
	Vector qrVector = new Vector();
	KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
	WTPart item = null;

	try {
	    for (int i = 0; i < inputParams.size(); i++) {
		searchHash.put("itemCode", (String) inputParams.elementAt(i));

		qrVector = query.searchItem(searchHash);
		if (qrVector.size() != 0) {
		    item = (WTPart) qrVector.elementAt(0);
		    KETObjectUtil.checkOut(item);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    @Override
    public void checkInItem(Vector inputParams) throws WTException {
	Hashtable searchHash = new Hashtable();
	Vector qrVector = new Vector();
	KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
	WTPart item = null;

	try {
	    for (int i = 0; i < inputParams.size(); i++) {
		searchHash.put("itemCode", (String) inputParams.elementAt(i));
		qrVector = query.searchItem(searchHash);

		if (qrVector.size() != 0) {
		    item = (WTPart) qrVector.elementAt(0);
		    if (KETObjectUtil.isCheckout(item)) {
			WorkInProgressHelper.service.checkin(item, "Multiple ECO parent item Check-In");
		    }
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    @Override
    public void cancelCheckOutItem(Vector inputParams) throws WTException {
	Hashtable searchHash = new Hashtable();
	Vector qrVector = new Vector();
	KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
	WTPart item = null;

	try {
	    for (int i = 0; i < inputParams.size(); i++) {
		searchHash.put("itemCode", (String) inputParams.elementAt(i));
		qrVector = query.searchItem(searchHash);

		if (qrVector.size() != 0) {
		    item = (WTPart) qrVector.elementAt(0);
		    if (KETObjectUtil.isCheckout(item)) {
			WorkInProgressHelper.service.undoCheckout(item);
		    }
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    @Override
    public void reviseItem(Vector inputParams) throws WTException {
	Hashtable searchHash = new Hashtable();
	Vector qrVector = new Vector();
	KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
	WTPart beforeItem = null;
	WTPart afterItem = null;

	try {
	    for (int i = 0; i < inputParams.size(); i++) {
		searchHash.put("itemCode", (String) inputParams.elementAt(i));
		qrVector = query.searchItem(searchHash);

		if (qrVector.size() != 0) {
		    beforeItem = (WTPart) qrVector.elementAt(0);

		    Folder revisionFolder = FolderHelper.service.getFolder((FolderEntry) beforeItem);

		    afterItem = (WTPart) VersionControlHelper.service.newVersion((Versioned) beforeItem);
		    FolderHelper.assignLocation((FolderEntry) afterItem, revisionFolder);
		    afterItem = (WTPart) PersistenceHelper.manager.save(afterItem);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    @Override
    public void cancelReviseItem(Vector inputParams) throws WTException {
	Hashtable searchHash = new Hashtable();
	Vector qrVector = new Vector();
	KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
	WTPart item = null;

	try {
	    for (int i = 0; i < inputParams.size(); i++) {
		searchHash.put("itemCode", (String) inputParams.elementAt(i));
		qrVector = query.searchItem(searchHash);

		if (qrVector.size() != 0) {
		    item = (WTPart) qrVector.elementAt(0);
		    PersistenceHelper.manager.delete(item);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new WTException(e.getMessage());
	}
    }

    @Override
    public Vector myBomEco(Hashtable inputParams) throws WTException {
	// Vector returnVec = null;

	KETBOMECOQueryBean bean = new KETBOMECOQueryBean();
	return bean.myBomEco(inputParams);

	// return returnVec;
    }

    @Override
    public void setCoworkerCancelCheckOut(Hashtable inputParams) throws WTException {

    }

    @Override
    public Vector checkAuthority(Vector itemCodeVec) throws WTException {

	KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
	return query.checkAuthority(itemCodeVec);

	// return null;
    }

    /*
     * 2015.06.22 황정태 작성 transaction 관리를 위해 기존에 모자리스트 라인별 hashtable로 넘겨받아 commit하던 것을 ArrayList로 넘겨받아 처리하도록 추가 ERP에 존재하지 않는 자부품은 삭제하는 로직이
     * 추가됨 기존에는 하위 1LEVEL 자부품만 처리했지만 모든 하위레벨의 BOM구조를 처리한다 타 모듈에서 호출하여 사용할 수 있으므로 기존 setKetPartUsageLink은 보존함
     * 
     * @see e3ps.bom.service.KETBomService#setKetPartUsageLink(java.util.ArrayList, java.lang.Integer, java.io.FileWriter)
     */
    @Override
    public Boolean setKetPartUsageLink(ArrayList list, Integer chk, FileWriter fw) throws WTException {
	boolean reSult = false;
	Registry registry = Registry.getRegistry("e3ps.bom.bom");
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	Statement stmt = null;
	BOMSearchUtilDao dao = new BOMSearchUtilDao();
	KETPartUsageLink bomMaster = null;
	Hashtable erpht = new Hashtable();
	// HashMap<String, String> sortedPlmhash = new HashMap<String, String>();

	Transaction transaction = new Transaction();

	try {
	    transaction.start();

	    int cnt = 0; // hashtable erpht의 key로 활용
	    // Start ⇒ erpht에 ERP 부품 Parent | Child 리스트 put
	    for (int i = 0; i < list.size(); i++) {
		erpht.put("erpChildPart" + cnt, (String) ((Hashtable) list.get(i)).get("MATNR") + "|" + (String) ((Hashtable) list.get(i)).get("IDNRK"));
		cnt++; // hashtable의 key 중복 방지
	    }
	    // End ⇒ erpht에 ERP 부품 Child 리스트 put

	    // cnt = 0;
	    WTPart checkParentPart;
	    String ParentPart;

	    Set<String> set = new LinkedHashSet<String>(); // 모부품list 중복을 방지하기 위해 LinkedHashSet 사용

	    // Start ⇒ set에 ERP 부품 Parent 리스트 add
	    for (int i = 0; i < list.size(); i++) {
		set.add((String) ((Hashtable) list.get(i)).get("MATNR"));
	    }
	    // End ⇒ set에 ERP 부품 Parent 리스트 add

	    QuerySpec spec;
	    QueryResult result;
	    KETPartUsageLink usageLink;

	    Iterator it = set.iterator();

	    // Parent 리스트만큼 루프를 돌린다
	    while (it.hasNext()) {
		ParentPart = (String) it.next();
		checkParentPart = searchItem(ParentPart);

		// Start ⇒ ParentPart로 KETPartUsageLink에서 1 Level 자부품 리스트를 찾고, ERP와 비교하여 없으면 해당 KETPartUsageLink를 삭제한다
		spec = new QuerySpec(KETPartUsageLink.class);
		spec.appendWhere(new SearchCondition(KETPartUsageLink.class, "roleAObjectRef.key.id", "=", CommonUtil.getOIDLongValue(checkParentPart)), new int[] { 0 });
		result = PersistenceHelper.manager.find((StatementSpec) spec);

		while (result.hasMoreElements()) {
		    usageLink = (KETPartUsageLink) result.nextElement();
		    if (!erpht.containsValue(usageLink.getParentItemCode() + "|" + usageLink.getChildItemCode())) {
			wt.fc.PersistenceServerHelper.manager.remove(usageLink);
			Kogger.debug(getClass(), ":::::::::::::: [삭제] 모부품번호 : " + usageLink.getParentItemCode() + ", 자부품번호 : " + usageLink.getChildItemCode());
			fw.write(":::::::::::::: [삭제] 모부품번호 : " + usageLink.getParentItemCode() + ", 자부품번호 : " + usageLink.getChildItemCode() + "\n");
		    }
		}
		// End ⇒ ParentPart로 KETPartUsageLink에서 1 Level 자부품 리스트를 찾고, ERP와 비교하여 없으면 해당 KETPartUsageLink를 삭제한다
	    }

	    // TreeSet<Hashtable<String, String>> treeset = new TreeSet<Hashtable<String, String>>(new Comparator <Object>() {
	    // public int compare(Object obj, Object obj1) {
	    // return ((Comparable<Object>) ((Entry<Object, Object>) obj).getValue()).compareTo(((Entry<Object, Object>) obj1).getValue());
	    // }
	    // });
	    //
	    // treeset.addAll((Collection<? extends Hashtable<String, String>>) plmht.entrySet());
	    // it = treeset.iterator();
	    //
	    // while (it.hasNext()) {
	    // Entry<String, String> entry = (Entry<String, String>) it.next();
	    // System.out.println(entry.getKey() + " - " + entry.getValue());
	    // }

	    WTPart parentPart;
	    WTPart childPart;
	    WTPartMaster childPartMaster;
	    Long oids;
	    String oid2;
	    QueryResult qr;
	    String logMsg = "";
	    for (int i = 0; i < list.size(); i++) {
		parentPart = searchItem((String) ((Hashtable) list.get(i)).get("MATNR"));
		childPart = searchItem((String) ((Hashtable) list.get(i)).get("IDNRK"));
		childPartMaster = (WTPartMaster) (childPart.getMaster());
		oids = CommonUtil.getOIDLongValue(childPart);
		oid2 = String.valueOf(oids); // child part ida2a2
		qr = PersistenceHelper.manager.find(KETPartUsageLink.class, parentPart, KETPartUsageLink.USED_BY_ROLE, childPartMaster);

		if (qr != null && qr.hasMoreElements()) {
		    logMsg = ":::::::::::::: [기존 BOM이 존재하여 변경 중..] " + i + " 번째  모부품번호 : " + ((Hashtable) list.get(i)).get("MATNR") + ", 자부품번호 : " + ((Hashtable) list.get(i)).get("IDNRK");

		    bomMaster = (KETPartUsageLink) qr.nextElement();
		    if (chk == 1) {// Prod
			bomMaster.setOrgCode("000");
			bomMaster.setParentItemCode((String) ((Hashtable) list.get(i)).get("MATNR"));
			bomMaster.setChildItemCode((String) ((Hashtable) list.get(i)).get("IDNRK"));
			bomMaster.setChildItemVersion((String) ((Hashtable) list.get(i)).get("CHILDV"));
			bomMaster.setBomVersion((String) ((Hashtable) list.get(i)).get("NBOMV"));
			bomMaster.setItemSeq(Integer.parseInt((String) ((Hashtable) list.get(i)).get("POSNR")));
			bomMaster.setQuantity((String) ((Hashtable) list.get(i)).get("ERFMG"));
			bomMaster.setBoxQuantity((String) ((Hashtable) list.get(i)).get("BMENG"));
			bomMaster.setUnit("KET_" + (String) ((Hashtable) list.get(i)).get("MEINS"));
			bomMaster.setStartDate((String) ((Hashtable) list.get(i)).get("DATUV"));
			bomMaster.setTraceCode(TraceCode.UNTRACED);
			bomMaster.setVersionItemCode(checkNVL(oid2));

			bomMaster.setIct((String) ((Hashtable) list.get(i)).get("ICT"));
			bomMaster.setRefTop((String) ((Hashtable) list.get(i)).get("REFTOP"));
			bomMaster.setRefBottom((String) ((Hashtable) list.get(i)).get("REFBOTTOM"));
		    } else {// Mold
			bomMaster.setOrgCode("000");
			bomMaster.setParentItemCode((String) ((Hashtable) list.get(i)).get("MATNR"));
			bomMaster.setChildItemCode((String) ((Hashtable) list.get(i)).get("IDNRK"));
			bomMaster.setChildItemVersion((String) ((Hashtable) list.get(i)).get("CHILDV"));
			bomMaster.setBomVersion((String) ((Hashtable) list.get(i)).get("NBOMV"));
			bomMaster.setItemSeq(Integer.parseInt((String) ((Hashtable) list.get(i)).get("ODNRK")));
			bomMaster.setQuantity((String) ((Hashtable) list.get(i)).get("MENGE"));
			bomMaster.setBoxQuantity("1");
			bomMaster.setUnit("KET_" + (String) ((Hashtable) list.get(i)).get("MEINS"));
			bomMaster.setStartDate((String) ((Hashtable) list.get(i)).get("DATUV"));
			bomMaster.setMaterial((String) ((Hashtable) list.get(i)).get("ATWRT"));
			bomMaster.setHardnessFrom((String) ((Hashtable) list.get(i)).get("HARDN"));
			bomMaster.setHardnessTo((String) ((Hashtable) list.get(i)).get("HARDT"));
			bomMaster.setDesignDate((String) ((Hashtable) list.get(i)).get("DATUV"));
			bomMaster.setTraceCode(TraceCode.UNTRACED);
			bomMaster.setVersionItemCode(checkNVL(oid2));
		    }
		    Kogger.debug(getClass(), logMsg);
		    fw.write(logMsg + "\n");
		    wt.fc.PersistenceServerHelper.manager.update(bomMaster);
		} else {// }
			// [ Create ] BOM node is not exist
		    logMsg = ":::::::::::::: [신규 생성 중..] " + i + " 번째  모부품번호 : " + ((Hashtable) list.get(i)).get("MATNR") + ", 자부품번호 : " + ((Hashtable) list.get(i)).get("IDNRK");
		    bomMaster = KETPartUsageLink.newKETPartUsageLink(parentPart, childPartMaster);
		    if (chk == 1) { // Prod
			bomMaster.setOrgCode("000");
			bomMaster.setParentItemCode((String) ((Hashtable) list.get(i)).get("MATNR"));
			bomMaster.setChildItemCode((String) ((Hashtable) list.get(i)).get("IDNRK"));
			bomMaster.setChildItemVersion((String) ((Hashtable) list.get(i)).get("CHILDV"));
			bomMaster.setBomVersion((String) ((Hashtable) list.get(i)).get("NBOMV"));
			bomMaster.setItemSeq(Integer.parseInt((String) ((Hashtable) list.get(i)).get("POSNR")));
			bomMaster.setQuantity((String) ((Hashtable) list.get(i)).get("ERFMG"));
			bomMaster.setBoxQuantity((String) ((Hashtable) list.get(i)).get("BMENG"));
			bomMaster.setUnit("KET_" + (String) ((Hashtable) list.get(i)).get("MEINS"));
			bomMaster.setStartDate((String) ((Hashtable) list.get(i)).get("DATUV"));
			bomMaster.setTraceCode(TraceCode.UNTRACED);
			bomMaster.setVersionItemCode(checkNVL(oid2));

			bomMaster.setIct((String) ((Hashtable) list.get(i)).get("ICT"));
			bomMaster.setRefTop((String) ((Hashtable) list.get(i)).get("REFTOP"));
			bomMaster.setRefBottom((String) ((Hashtable) list.get(i)).get("REFBOTTOM"));
		    } else {// Mold
			bomMaster.setOrgCode("000");
			bomMaster.setParentItemCode((String) ((Hashtable) list.get(i)).get("MATNR"));
			bomMaster.setChildItemCode((String) ((Hashtable) list.get(i)).get("IDNRK"));
			bomMaster.setChildItemVersion((String) ((Hashtable) list.get(i)).get("CHILDV"));
			bomMaster.setBomVersion((String) ((Hashtable) list.get(i)).get("NBOMV"));
			bomMaster.setItemSeq(Integer.parseInt((String) ((Hashtable) list.get(i)).get("ODNRK")));
			bomMaster.setBoxQuantity("1");
			bomMaster.setUnit("KET_" + (String) ((Hashtable) list.get(i)).get("MEINS"));
			bomMaster.setQuantity((String) ((Hashtable) list.get(i)).get("MENGE"));
			bomMaster.setStartDate((String) ((Hashtable) list.get(i)).get("DATUV"));
			bomMaster.setMaterial((String) ((Hashtable) list.get(i)).get("ATWRT"));
			bomMaster.setHardnessFrom((String) ((Hashtable) list.get(i)).get("HARDN"));
			bomMaster.setHardnessTo((String) ((Hashtable) list.get(i)).get("HARDT"));
			bomMaster.setDesignDate((String) ((Hashtable) list.get(i)).get("DATUV"));
			bomMaster.setTraceCode(TraceCode.UNTRACED);
			bomMaster.setVersionItemCode(checkNVL(oid2));
		    }
		    Kogger.debug(getClass(), logMsg);
		    fw.write(logMsg + "\n");
		    wt.fc.PersistenceServerHelper.manager.insert(bomMaster);
		}

		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer.append("UPDATE WTPart SET " + PartSpecEnum.SpBOMFlag.getColumnName() + "='OLD'	\n");
		queryBuffer.append("	WHERE 1=1 								\n");
		queryBuffer.append("	AND ida2a2 IN ( SELECT p.ida2a2 					\n");
		queryBuffer.append("	                            FROM WTPart p, WTPartMaster m  		\n");
		queryBuffer.append("	                           WHERE ( m.wtpartnumber = '" + (String) ((Hashtable) list.get(i)).get("MATNR") + "'	\n");
		queryBuffer.append("	                              OR     m.wtpartnumber = '" + (String) ((Hashtable) list.get(i)).get("IDNRK") + "'  )	\n");
		queryBuffer.append("	                             AND    m.ida2a2 = p.ida3masterreference ) 		\n");

		conn.setAutoCommit(false);
		stmt = conn.createStatement();
		stmt.executeUpdate(queryBuffer.toString());
		if (stmt != null) {
		    try {
			stmt.close();
		    } catch (SQLException se) {
			Kogger.error(getClass(), se);
		    }
		}

		reSult = true;
	    }
	    conn.commit();
	    transaction.commit();
	    transaction = null;

	} catch (Exception e) {
	    try {
		reSult = false;
		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	} finally {
	    if (stmt != null) {
		try {
		    stmt.close();
		} catch (SQLException se) {
		    Kogger.error(getClass(), se);
		}
	    }

	    if (res != null) {
		res.freeConnection(registry.getString("plm"), conn);
	    }

	    if (transaction != null) {
		transaction.rollback();
		reSult = false;
	    }
	}

	return reSult;

    }

    @Override
    public Boolean setKetPartUsageLink(Hashtable ht, Integer chk) throws WTException {
	boolean reSult = false;
	Registry registry = Registry.getRegistry("e3ps.bom.bom");
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	Statement stmt = null;
	BOMSearchUtilDao dao = new BOMSearchUtilDao();
	KETPartUsageLink bomMaster = null;

	Transaction transaction = new Transaction();
	try {
	    transaction.start();
	    WTPart parentPart = searchItem((String) ht.get("MATNR"));
	    WTPart childPart = searchItem((String) ht.get("IDNRK"));
	    WTPartMaster childPartMaster = (WTPartMaster) (childPart.getMaster());

	    Long oids = CommonUtil.getOIDLongValue(childPart);
	    String oid2 = String.valueOf(oids); // child part ida2a2
	    // String parentOid = KETObjectUtil.getIda2a2(parentPart); // parent part ida2a2
	    // String childMasterOid = KETObjectUtil.getIda2a2(childPartMaster); // child partMaster ida2a2

	    // int existCount = dao.isBomExistInPartUsageLink(parentOid, oid2, childMasterOid);

	    // [ Update ] existCount is larger than 0, same BOM node exist
	    // if ( existCount > 0 )
	    // {
	    QueryResult qr = PersistenceHelper.manager.find(KETPartUsageLink.class, parentPart, KETPartUsageLink.USED_BY_ROLE, childPartMaster);

	    if (qr != null && qr.hasMoreElements()) {
		bomMaster = (KETPartUsageLink) qr.nextElement();
		if (chk == 1) // Prod
		{
		    bomMaster.setOrgCode("000");
		    bomMaster.setParentItemCode((String) ht.get("MATNR"));
		    bomMaster.setChildItemCode((String) ht.get("IDNRK"));
		    bomMaster.setChildItemVersion((String) ht.get("CHILDV"));
		    bomMaster.setBomVersion((String) ht.get("NBOMV"));
		    bomMaster.setItemSeq(Integer.parseInt((String) ht.get("POSNR")));
		    bomMaster.setQuantity((String) ht.get("ERFMG"));
		    bomMaster.setBoxQuantity((String) ht.get("BMENG"));
		    bomMaster.setUnit("KET_" + (String) ht.get("MEINS"));
		    bomMaster.setStartDate((String) ht.get("DATUV"));
		    bomMaster.setTraceCode(TraceCode.UNTRACED);
		    bomMaster.setVersionItemCode(checkNVL(oid2));

		    bomMaster.setIct((String) ht.get("ICT"));
		    bomMaster.setRefTop((String) ht.get("REFTOP"));
		    bomMaster.setRefBottom((String) ht.get("REFBOTTOM"));
		} else // Mold
		{
		    bomMaster.setOrgCode("000");
		    bomMaster.setParentItemCode((String) ht.get("MATNR"));
		    bomMaster.setChildItemCode((String) ht.get("IDNRK"));
		    bomMaster.setChildItemVersion((String) ht.get("CHILDV"));
		    bomMaster.setBomVersion((String) ht.get("NBOMV"));
		    bomMaster.setItemSeq(Integer.parseInt((String) ht.get("ODNRK")));
		    bomMaster.setQuantity((String) ht.get("MENGE"));
		    bomMaster.setBoxQuantity("1");
		    bomMaster.setUnit("KET_" + (String) ht.get("MEINS"));
		    bomMaster.setStartDate((String) ht.get("DATUV"));
		    bomMaster.setMaterial((String) ht.get("ATWRT"));
		    bomMaster.setHardnessFrom((String) ht.get("HARDN"));
		    bomMaster.setHardnessTo((String) ht.get("HARDT"));
		    bomMaster.setDesignDate((String) ht.get("DATUV"));
		    bomMaster.setTraceCode(TraceCode.UNTRACED);
		    bomMaster.setVersionItemCode(checkNVL(oid2));
		}
		wt.fc.PersistenceServerHelper.manager.update(bomMaster);
	    }
	    // }
	    // [ Create ] BOM node is not exist
	    else {
		bomMaster = KETPartUsageLink.newKETPartUsageLink(parentPart, childPartMaster);
		if (chk == 1) // Prod
		{
		    bomMaster.setOrgCode("000");
		    bomMaster.setParentItemCode((String) ht.get("MATNR"));
		    bomMaster.setChildItemCode((String) ht.get("IDNRK"));
		    bomMaster.setChildItemVersion((String) ht.get("CHILDV"));
		    bomMaster.setBomVersion((String) ht.get("NBOMV"));
		    bomMaster.setItemSeq(Integer.parseInt((String) ht.get("POSNR")));
		    bomMaster.setQuantity((String) ht.get("ERFMG"));
		    bomMaster.setBoxQuantity((String) ht.get("BMENG"));
		    bomMaster.setUnit("KET_" + (String) ht.get("MEINS"));
		    bomMaster.setStartDate((String) ht.get("DATUV"));
		    bomMaster.setTraceCode(TraceCode.UNTRACED);
		    bomMaster.setVersionItemCode(checkNVL(oid2));

		    bomMaster.setIct((String) ht.get("ICT"));
		    bomMaster.setRefTop((String) ht.get("REFTOP"));
		    bomMaster.setRefBottom((String) ht.get("REFBOTTOM"));
		} else // Mold
		{
		    bomMaster.setOrgCode("000");
		    bomMaster.setParentItemCode((String) ht.get("MATNR"));
		    bomMaster.setChildItemCode((String) ht.get("IDNRK"));
		    bomMaster.setChildItemVersion((String) ht.get("CHILDV"));
		    bomMaster.setBomVersion((String) ht.get("NBOMV"));
		    bomMaster.setItemSeq(Integer.parseInt((String) ht.get("ODNRK")));
		    bomMaster.setBoxQuantity("1");
		    bomMaster.setUnit("KET_" + (String) ht.get("MEINS"));
		    bomMaster.setQuantity((String) ht.get("MENGE"));
		    bomMaster.setStartDate((String) ht.get("DATUV"));
		    bomMaster.setMaterial((String) ht.get("ATWRT"));
		    bomMaster.setHardnessFrom((String) ht.get("HARDN"));
		    bomMaster.setHardnessTo((String) ht.get("HARDT"));
		    bomMaster.setDesignDate((String) ht.get("DATUV"));
		    bomMaster.setTraceCode(TraceCode.UNTRACED);
		    bomMaster.setVersionItemCode(checkNVL(oid2));
		}
		wt.fc.PersistenceServerHelper.manager.insert(bomMaster);
	    }

	    StringBuffer queryBuffer = new StringBuffer();
	    queryBuffer.append("UPDATE WTPart SET " + PartSpecEnum.SpBOMFlag.getColumnName() + "='OLD'	\n");
	    queryBuffer.append("	WHERE 1=1 								\n");
	    queryBuffer.append("	AND ida2a2 IN ( SELECT p.ida2a2 					\n");
	    queryBuffer.append("	                            FROM WTPart p, WTPartMaster m  		\n");
	    queryBuffer.append("	                           WHERE ( m.wtpartnumber = '" + (String) ht.get("MATNR") + "'	\n");
	    queryBuffer.append("	                              OR     m.wtpartnumber = '" + (String) ht.get("IDNRK") + "'  )	\n");
	    queryBuffer.append("	                             AND    m.ida2a2 = p.ida3masterreference ) 		\n");

	    conn.setAutoCommit(false);
	    stmt = conn.createStatement();
	    stmt.executeUpdate(queryBuffer.toString());

	    conn.commit();
	    transaction.commit();
	    transaction = null;

	    reSult = true;
	} catch (Exception e) {
	    try {
		reSult = false;
		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	} finally {
	    if (stmt != null) {
		try {
		    stmt.close();
		} catch (SQLException se) {
		    Kogger.error(getClass(), se);
		}
	    }

	    if (res != null) {
		res.freeConnection(registry.getString("plm"), conn);
	    }

	    if (transaction != null) {
		transaction.rollback();
		reSult = false;
	    }
	}
	return reSult;

    }

    @Override
    public boolean startProcess(String oid, KETWfmApprovalMaster master) throws WTException {
	boolean returnFlag = false;

	returnFlag = KETWfmHelper.service.startProcess(oid, master);

	return returnFlag;
    }

    @Override
    public String getIsBomComponent(String strNumber) throws WTException {
	String strReturn = "";

	strReturn = ItemMasterInterface.getIsBomComponent(strNumber);

	return strReturn;
    }

    @Override
    public void createLine(Hashtable hashParam) throws WTException {
	KETWfmHelper.service.createLine(hashParam);
    }

    @Override
    public void updateMaster(String oid, String agreementType, String comment) throws WTException {
	KETWfmHelper.service.updateMaster(oid, agreementType, comment);
    }

    @Override
    public Persistable createMaster(Hashtable hashParam) throws WTException {
	Persistable master = KETWfmHelper.service.createMaster(hashParam);

	return master;
    }

    @Override
    public Boolean setWfKetPartUsageLink(Vector vc, Integer chk) throws WTException {
	boolean reSult = false;
	if (vc == null || vc.size() == 0)
	    return true;

	try {

	    /*
	     * Registry registry = Registry.getRegistry("e3ps.bom.bom"); DBConnectionManager res = DBConnectionManager.getInstance();
	     * Connection conn = res.getConnection(registry.getString("plm")); Transaction transaction = new Transaction();
	     */

	    WTConnection wtConn = EcmUtil.getWTConnection();
	    Connection conn = wtConn.getConnection();

	    /*
	     * try { transaction.start();
	     */
	    for (int i = 0; i < vc.size(); i++) {
		Hashtable ht = (Hashtable) vc.elementAt(i);
		WTPart parentPart = searchItem((String) ht.get("MATNR"));
		WTPart childPart = searchItem((String) ht.get("IDNRK"));
		WTPartMaster childPartMaster = (WTPartMaster) (childPart.getMaster());

		reSult = createPartUsageLink(ht, parentPart, childPart, childPartMaster, chk, conn);

		// DB fail case
		if (!reSult) {
		    break;
		}
	    }

	    /*
	     * // when All case Success if (reSult) { conn.commit(); transaction.commit(); transaction = null; }
	     * 
	     * reSult = true; } catch (Exception e) { try { reSult = false; conn.rollback(); Kogger.error(getClass(), e); } catch
	     * (SQLException se) { Kogger.error(getClass(), se); } } finally {
	     * 
	     * if (res != null) { res.freeConnection(registry.getString("plm"), conn); conn = null; }
	     * 
	     * if (transaction != null) { transaction.rollback(); transaction = null;
	     * 
	     * reSult = false; } }
	     */

	} catch (Exception e) {
	    throw new WTException(e);

	}

	return reSult;
    }

    @Override
    public Boolean getBomInterface(String oid) throws WTException {
	boolean reSult = false;
	BomInfoInterface sf = new BomInfoInterface();
	if (sf.getInterFaceResult(oid)) {
	    WFBomPartUsageQry wp = new WFBomPartUsageQry();
	    if (wp.getPartUsageResult(oid)) {
		reSult = true;
	    }
	}
	return reSult;
    }

    /*
     * 초도일 경우(제품, 금형)
     * 
     * @see e3ps.bom.service.KETBomService#getBomInterface2(java.lang.String)
     */
    @Override
    public Boolean[] getBomInterface2(String ecoLongValue, boolean isProd) throws WTException {
	Boolean ret[] = new Boolean[2];
	boolean reSult = false;
	boolean isPartErpInterFaceFail = false;

	Transaction transaction = new Transaction();
	PartDieInfoHandler partDieInfoHandler = new PartDieInfoHandler();

	try {

	    String startEcoOid = (isProd) ? "e3ps.ecm.entity.KETProdChangeOrder:" + ecoLongValue : "e3ps.ecm.entity.KETMoldChangeOrder:" + ecoLongValue;

	    transaction.start();

	    BomEcoInfoInterface bef = new BomEcoInfoInterface();
	    InfoInterfaceQry iif = new InfoInterfaceQry();

	    /*
	     * reSult = bef.getInterFaceResult(oid);
	     */

	    KETBOMHeaderQueryBean ketBOMHeaderQueryBean = new KETBOMHeaderQueryBean();
	    BomEcoInfoInterfaceQry bomEcoInfoInterfaceQry = new BomEcoInfoInterfaceQry();
	    BomInfoInterface bomInfoInterface = new BomInfoInterface();
	    KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();
	    ProdEcoBeans prodEcoBeans = new ProdEcoBeans();

	    WTConnection wtConn = EcmUtil.getWTConnection();
	    Connection conn = wtConn.getConnection();
	    try {

		// ====================================================================================================
		// I. ECO의 설변에 추가된 Part List를 가져온다.
		// ====================================================================================================
		String ECO_NUMBER = "";

		ArrayList<Hashtable> ecoList = bomEcoInfoInterfaceQry.getItemCode(ecoLongValue); // ECO에 부품이 여러개가 지정될 수 있으므로
		int ecoListSize = (ecoList != null) ? ecoList.size() : 0;

		// I. Part
		Hashtable<String, Vector<Hashtable<String, String>>> ht = null;
		Vector<Hashtable<String, String>> vc = null;
		Vector<Hashtable<String, String>> vtr = null;

		// ECO에서 초도대상으로 추가한 부품을 가져온다.
		ArrayList<WTPart> partList = new ArrayList<WTPart>();

		for (int inx = 0; inx < ecoListSize; inx++) {

		    Hashtable hash = ecoList.get(inx);
		    String headerOid = (String) hash.get("headerOid");
		    String headerLongValue = (String) hash.get("headerLongValue");
		    String itemCd = (String) hash.get("itemcode");
		    ECO_NUMBER = (String) hash.get("econum");

		    Kogger.info(getClass(), "@@@@@@@@@@@@ ECO getInterFaceResult ERP @@@@@@@@@@@ ");
		    Kogger.info(getClass(), "@@@@ headerOid : " + headerOid);
		    Kogger.info(getClass(), "@@@@ headerLongValue : " + headerLongValue);
		    Kogger.info(getClass(), "@@@@ itemCd : " + itemCd);
		    Kogger.info(getClass(), "@@@@ econum : " + ECO_NUMBER);

		    WTPart wtPartAddedEco = ketBOMHeaderQueryBean.searchItem(itemCd);

		    // BOM 작업이 없는 부품을 ECO에 보내기 위한 코드이다.
		    if (!partList.contains(wtPartAddedEco))
			partList.add(wtPartAddedEco);

		    String strType = PartSpecGetter.getPartSpec(wtPartAddedEco, PartSpecEnum.SpPartType);
		    // strType = IBAUtil.getAttrValue(part, "PartType");

		    /*
	             * BomInfoInterface sf = new BomInfoInterface(); if (sf.getInterFaceResult(oid)) {
	             */

		    //
		    /*
	             * KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean(); InfoInterfaceQry iifQry = new InfoInterfaceQry(); String
	             * itemCd = iifQry.getItemCode(headCd); // WTPart.number WTPart part = kh.searchItem(itemCd); strType =
	             * IBAUtil.getAttrValue(part, "PartType");
	             */

		    Kogger.info(getClass(), "@@@@ strType : " + strType);
		    Kogger.info(getClass(), "@@@@ PartUtil.isProductType(strType) : " + PartUtil.isProductType(strType));
		    if (PartUtil.isProductType(strType)) {
			ht = iif.getGenInterfaceData(headerLongValue);

		    } else if (strType.equals("D"))// || strType.equals("M")) //M이 올수 있을려나...??
		    {
			ht = iif.getMoldInterfaceData(headerLongValue);

		    } else {
			Kogger.info(getClass(), "지정된 값이 없는 PartType 입니다.");
		    }

		    if (ht != null) {
			vc = (Vector<Hashtable<String, String>>) ht.get("HEAD");
			vtr = (Vector<Hashtable<String, String>>) ht.get("COMP");

			Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
			Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
			Kogger.info(getClass(), "vc is \n" + vc.toString());
			Kogger.info(getClass(), "vtr is \n" + vtr.toString());
			Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
			Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");

			partList = iif.getPartList4SapIFbyPartMaster(vc, vtr, partList);

		    }

		}

		// ====================================================================================================
		// II. ERP I/F 를 해야하는지 말아야 하는지를 조사한다.
		// ====================================================================================================
		boolean DO_SEND_ERP = false;

		// 제품일 경우
		if (isProd) {

		    DO_SEND_ERP = true;

		}
		// 금형일 경우
		else {
		    int partListSize = (partList != null) ? partList.size() : 0;
		    for (int i = 0; i < partListSize; i++) {
			WTPart wtPart = (WTPart) partList.get(i);
			String strType = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
			if (strType.equals("D")) {

			    DO_SEND_ERP = true;

			}
		    }
		}

		Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		Kogger.info(getClass(), "$$$$$$$$$$$$$$$$ DO_SEND_ERP is " + DO_SEND_ERP);
		Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

		// ====================================================================================================
		// III. ERP I/F 를 하지 말아야 하는 경우
		// ====================================================================================================
		if (!DO_SEND_ERP) {

		    reSult = true;

		    try {

			for (int inx = 0; inx < ecoListSize; inx++) {

			    Hashtable hash = ecoList.get(inx);
			    String headerOid = (String) hash.get("headerOid");
			    String headerLongValue = (String) hash.get("headerLongValue");
			    String itemCd = (String) hash.get("itemcode");
			    String econum = (String) hash.get("econum");

			    // 헤더 상태변경
			    KETBomEcoHeader bomHeader = (KETBomEcoHeader) CommonUtil.getObject(headerOid);

			    Kogger.info(getClass(), "@@@@@@@@@@@@ BomHeader change APPROVED ERP @@@@@@@@@@@ ");
			    Kogger.info(getClass(), "@@@@ BomHeader : " + bomHeader.toString());

			    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, State.toState("APPROVED"));

			    Kogger.info(getClass(), "@@@@@@@@@@@@ Set Flag Y means Success ERP @@@@@@@@@@@ ");
			    Kogger.info(getClass(), "@@@@ headerLongValue : " + headerLongValue);
			    Kogger.info(getClass(), "@@@@ Y ");

			    // iif.setSuccessFlag(headCd,"Y");
			    iif.setFalseFlag(headerLongValue, "Y", conn);

			}

		    } catch (Exception e) {

			KogDbUtil.log("ECO 초도 BOM Header 변경 에러 - No ErpI/F", "Fail", startEcoOid, e, null, null);
			throw e;
		    }

		    try {

			// 부품, BOM 모두 성공하였을 경우에만 부품 상태를 'APPROVED'로 변경한다.
			int partListSize = (partList != null) ? partList.size() : 0;
			for (int j = 0; j < partListSize; j++) {
			    WTPart wtPart = partList.get(j);

			    Kogger.info(getClass(), "@@@@@@@@@@@@ WTPart change APPROVED @@@@@@@@@@@ ");
			    Kogger.info(getClass(), wtPart.getNumber() + ", " + wtPart.getVersionIdentifier().getValue() + ", " + CommonUtil.getOIDString(wtPart));

			    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpEoNo, ECO_NUMBER);
			    PersistenceServerHelper.update(wtPart);
			    wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
			    partDieInfoHandler.updatePartDieInfo(wtPart);
			    LifeCycleServerHelper.service.setState((LifeCycleManaged) wtPart, State.toState("APPROVED"));

			}

		    } catch (Exception e) {

			KogDbUtil.log("ECO 초도 부품 상태 변경 에러 - No ErpI/F", "Fail", startEcoOid, e, null, null);
			throw e;
		    }

		    try {

			// 도면처리
			// WTChangeOrder2 eco = prodEcoBeans.getEcoByNo(ECO_NUMBER);
			String ecoOid = (isProd) ? "e3ps.ecm.entity.KETProdChangeOrder:" + ecoLongValue : "e3ps.ecm.entity.KETMoldChangeOrder:" + ecoLongValue;
			WTChangeOrder2 eco = (WTChangeOrder2) CommonUtil.getObject(ecoOid);
			Kogger.info(getClass(), "@@@@@@@@@@@@ eco is " + eco);

			Object[] eca = EcmUtil.getChangeActivities(eco);
			if (eca != null) {

			    for (int cnt = 0; cnt < eca.length; cnt++) {

				Object object = (Object) eca[cnt];
				if (object instanceof KETProdChangeActivity) {
				    KETProdChangeActivity prodCA = (KETProdChangeActivity) object;
				    if (!prodCA.getActivityType().equals("3") && !prodCA.getActivityType().equals("4")) {
					Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromProdECA(prodCA);
					for (int i = 0; i < epmVec.size(); i++) {
					    EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));
					    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("APPROVED"));
					}

				    }

				} else {
				    KETMoldChangeActivity moldCA = (KETMoldChangeActivity) object;
				    if (!moldCA.getActivityType().equals("4")) {
					Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromMoldECA(moldCA);
					for (int i = 0; i < epmVec.size(); i++) {
					    EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));
					    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("APPROVED"));
					}

				    }

				}

			    }
			}

		    } catch (Exception e) {

			KogDbUtil.log("ECO 초도 도면 상태 변경 에러 - No ErpI/F", "Fail", startEcoOid, e, null, null);
			throw e;
		    }

		}
		// ====================================================================================================
		// IV. ERP I/F 를 해야 하는 경우
		// ====================================================================================================
		else {

		    // ///////////////////////////////////////////////////////////////////////////
		    /*
	             * 일반자재생성 Z_ST_CREATE_MATERIAL_GENERAL 일반자재수정 Z_ST_CHANGE_MATERIAL_GENERAL 인수 I_ECONO 테이블 IT_TYPE0
	             * 
	             * 금형자재생성 Z_ST_CREATE_MATERIAL_MOLD 금형자재수정 Z_ST_CREATE_MATERIAL_MOLD 인수 I_ECONO 테이블 IT_TYPE7 테이블 IT_TYPE7I 테이블 IT_TYPE8
	             */
		    String ecoNo = ECO_NUMBER;
		    Map<String, String> workerNameMap = null;
		    ErpPartInterFace erpPartInterFace = new ErpPartInterFace();
		    Map resultMap = erpPartInterFace.sendPartInfoToErp(partList, ecoNo, workerNameMap);

		    boolean success = (Boolean) resultMap.get(ErpPartInterFace.SUCCESS);
		    if (!success) {

			reSult = false;

			// List<String> errorLogList = (List<String>) resultMap.get(ErpPartInterFace.PLM_ERROR_LIST);
			String erpMsg = (String) resultMap.get(ErpPartInterFace.ERP_ERROR_MSG);
			Kogger.info(getClass(), erpMsg);

			KogDbUtil.log("ECO 초도 ERP 부품 I/F 에러", "Fail", startEcoOid, erpMsg, null, null);

			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$ ERP IF ERROR $$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

		    } else {

			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$ PART ERP IF SUCCESS $$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

			// 설변에 추가된 부품이 없을 경우
			if (ecoListSize == 0) {
			    // 도면만 붙어있을 경우 도면을 '승인완료'처리하기 위한 로직
			    reSult = true;
			}
			// 설변에 추가된 부품이 있을 경우
			else {

			    for (int inx = 0; inx < ecoListSize; inx++) {

				Hashtable hash = ecoList.get(inx);
				String headerOid = (String) hash.get("headerOid");
				String headerLongValue = (String) hash.get("headerLongValue");
				String itemCd = (String) hash.get("itemcode");
				String econum = (String) hash.get("econum");

				Kogger.info(getClass(), "@@@@@@@@@@@@ ECO getInterFaceResult ERP @@@@@@@@@@@ ");
				Kogger.info(getClass(), "@@@@ headerOid : " + headerOid);
				Kogger.info(getClass(), "@@@@ headerLongValue : " + headerLongValue);
				Kogger.info(getClass(), "@@@@ itemCd : " + itemCd);
				Kogger.info(getClass(), "@@@@ econum : " + econum);

				WTPart part = ketBOMHeaderQueryBean.searchItem(itemCd);
				String strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
				// strType = IBAUtil.getAttrValue(part, "PartType");

				/*
		                 * BomInfoInterface sf = new BomInfoInterface(); if (sf.getInterFaceResult(oid)) {
		                 */

				//
				/*
		                 * KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean(); InfoInterfaceQry iifQry = new InfoInterfaceQry();
		                 * String itemCd = iifQry.getItemCode(headCd); // WTPart.number WTPart part = kh.searchItem(itemCd); strType
		                 * = IBAUtil.getAttrValue(part, "PartType");
		                 */

				Kogger.info(getClass(), "@@@@ strType : " + strType);
				Kogger.info(getClass(), "@@@@ PartUtil.isProductType(strType) : " + PartUtil.isProductType(strType));
				if (PartUtil.isProductType(strType)) {
				    reSult = bomInfoInterface.getGenInfo2(econum, headerLongValue);

				} else if (strType.equals("D"))// || strType.equals("M")) //M이 올수 있을려나...??
				{
				    reSult = bomInfoInterface.getMoldInfo2(econum, headerLongValue);

				} else if (strType.equals("M")) // 금형부품일 경우 bom 구성대상이 아니므로 pass 2015.08.20 by 황정태
				{
				    reSult = true;

				} else {
				    Kogger.info(getClass(), "지정된 값이 없는 PartType 입니다.");
				}

				if (reSult) {

				    try {

					// 헤더 상태변경
					KETBomEcoHeader bomHeader = (KETBomEcoHeader) CommonUtil.getObject(headerOid);

					Kogger.info(getClass(), "@@@@@@@@@@@@ BomHeader change APPROVED ERP @@@@@@@@@@@ ");
					Kogger.info(getClass(), "@@@@ BomHeader : " + bomHeader.toString());

					LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, State.toState("APPROVED"));

					Kogger.info(getClass(), "@@@@@@@@@@@@ Set Flag Y means Success ERP @@@@@@@@@@@ ");
					Kogger.info(getClass(), "@@@@ headerLongValue : " + headerLongValue);
					Kogger.info(getClass(), "@@@@ Y ");

					// iif.setSuccessFlag(headCd,"Y");
					iif.setFalseFlag(headerLongValue, "Y", conn);

				    } catch (Exception e) {

					KogDbUtil.log("ECO 초도 BOM Header 변경 에러 - with Erp I/F", "Fail", startEcoOid, e, null, null);
					throw e;
				    }

				} else {

				    Kogger.info(getClass(), "@@@@@@@@@@@@ Set Flag 2 means Fail ERP @@@@@@@@@@@ ");
				    Kogger.info(getClass(), "@@@@ headerLongValue : " + headerLongValue);
				    Kogger.info(getClass(), "@@@@ 2 ");

				    KogDbUtil.log("ECO 초도 ERP BOM 구조 I/F 에러", "Fail", startEcoOid, (Throwable) null, null, null);

				    iif.setFalseFlag(headerLongValue, "2", conn);

				    /*
		                     * 하나의 ECO에 붙은 여러 설변(초도)부품 중 하나라도 ERP I/F에 실패하면 성공한 것까지는 ketbomecoheader, ketbomecocomponent 에 각각
		                     * transferflag='Y' 를 set 하고, 실패한 것에서 바로 ketbomecoheader, ketbomecocomponent 에 각각 transferflag='2' 를 set
		                     * 하고, 아직 나머지가 있더라도 멈춘다.
		                     */
				    break;

				}

				// Usage Link 처리
				if (reSult) {

				    Kogger.info(getClass(), "@@@@@@@@@@@@ Usage Link Process ERP @@@@@@@@@@@ ");
				    Kogger.info(getClass(), "@@@@ headerLongValue : " + headerLongValue);

				    WFBomPartUsageQry wp = new WFBomPartUsageQry();
				    reSult = wp.getPartUsageResult(headerLongValue);

				    if (!reSult) {
					KogDbUtil.log("ECO 초도 BOM UsageLink 에러 - with Erp I/F", "Fail", startEcoOid, (Throwable) null, null, null);
				    }
				}
			    }
			}

			// Part 상태를 Approved로 set 한다.
			if (reSult) {

			    try {

				// 부품, BOM 모두 성공하였을 경우에만 부품 상태를 'APPROVED'로 변경한다.
				int partListSize = (partList != null) ? partList.size() : 0;
				for (int j = 0; j < partListSize; j++) {
				    WTPart wtPart = partList.get(j);
				    if (wtPart != null) {
					Kogger.info(getClass(), "@@@@@@@@@@@@ WTPart change APPROVED ERP @@@@@@@@@@@ ( partListSize = " + partListSize + " )");
					Kogger.info(getClass(), "@@@@ wtPart 객체  : " + wtPart);
					Kogger.info(getClass(), "@@@@ wtPart : " + wtPart.getNumber());

					PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpEoNo, ECO_NUMBER);
					PersistenceServerHelper.update(wtPart);
					wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
					partDieInfoHandler.updatePartDieInfo(wtPart);
					LifeCycleServerHelper.service.setState((LifeCycleManaged) wtPart, State.toState("APPROVED"));
				    }

				}

			    } catch (Exception e) {

				KogDbUtil.log("ECO 초도 부품 상태 변경 에러 - with Erp I/F", "Fail", startEcoOid, e, null, null);
				throw e;
			    }

			    try {

				// 도면처리
				// WTChangeOrder2 eco = prodEcoBeans.getEcoByNo(ECO_NUMBER);
				String ecoOid = (isProd) ? "e3ps.ecm.entity.KETProdChangeOrder:" + ecoLongValue : "e3ps.ecm.entity.KETMoldChangeOrder:" + ecoLongValue;
				WTChangeOrder2 eco = (WTChangeOrder2) CommonUtil.getObject(ecoOid);
				Kogger.info(getClass(), "@@@@@@@@@@@@ eco is " + eco);

				Object[] eca = EcmUtil.getChangeActivities(eco);
				if (eca != null) {

				    for (int cnt = 0; cnt < eca.length; cnt++) {

					Object object = (Object) eca[cnt];
					if (object instanceof KETProdChangeActivity) {
					    KETProdChangeActivity prodCA = (KETProdChangeActivity) object;
					    if (!prodCA.getActivityType().equals("3") && !prodCA.getActivityType().equals("4")) {
						Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromProdECA(prodCA);
						for (int i = 0; i < epmVec.size(); i++) {
						    EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));
						    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("APPROVED"));
						}

					    }

					} else {
					    KETMoldChangeActivity moldCA = (KETMoldChangeActivity) object;
					    if (!moldCA.getActivityType().equals("4")) {
						Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromMoldECA(moldCA);
						for (int i = 0; i < epmVec.size(); i++) {
						    EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));
						    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("APPROVED"));
						}

					    }

					}

				    }
				}

			    } catch (Exception e) {

				KogDbUtil.log("ECO 초도 도면 상태 변경 에러 - with Erp I/F", "Fail", startEcoOid, e, null, null);
				throw e;
			    }

			}

		    }

		}

	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		reSult = false;
	    }

	    // when All case Success
	    // SAP I/F 가 실패하여도 결재승인은 나고 차후 '재전송'으로 다시 시도한다.
	    if (reSult) {
		transaction.commit();
		transaction = null;
	    }

	} catch (Exception e) {
	    reSult = false;
	    Kogger.error(getClass(), e);
	} finally {
	    if (transaction != null) {
		transaction.rollback();
		transaction = null;

		reSult = false;
	    }
	}

	ret[0] = reSult;
	ret[1] = isPartErpInterFaceFail;
	return ret;
    }

    @Override
    public void updateItemMaster(String ecoHeaderNo, String ecoItemCode) throws WTException {
	BOMSearchUtilDao.updateItemMasterERP(ecoHeaderNo, ecoItemCode);
    }

    @Override
    public String updateItemMasterERP(Hashtable hashUpdate) throws WTException {
	String strReturn = "";

	strReturn = ItemMasterInterface.updateMasterErp(hashUpdate);

	return strReturn;
    }

    @Override
    public void updateEndWorkingFlag(String ecoHeaderNo, String flag) throws WTException {
	BOMSearchUtilDao.updateEndWorkingFlag(ecoHeaderNo, flag);
    }

    @Override
    public void updateEndWorkingFlagNew(String newBomCode, String flag) throws WTException {
	BOMSearchUtilDao.updateEndWorkingFlagNew(newBomCode, flag);
    }

    @Override
    public Boolean setWfKetPartUsageLinkEco(Vector vc, Integer chk) throws WTException {
	boolean reSult = false;
	String ecoCode = "";

	/*
	 * Registry registry = Registry.getRegistry("e3ps.bom.bom"); DBConnectionManager res = DBConnectionManager.getInstance(); Connection
	 * conn = res.getConnection(registry.getString("plm"));
	 */

	Transaction transaction = new Transaction();

	try {

	    KETBOMQueryBean bomqr = new KETBOMQueryBean();

	    transaction.start();

	    WTConnection wtConn = EcmUtil.getWTConnection();
	    Connection conn = wtConn.getConnection();

	    for (int i = 0; i < vc.size(); i++) {
		Hashtable ht = (Hashtable) vc.elementAt(i);
		ecoCode = (String) ht.get("ECOCODE");
		// WTPart parentPart = searchItem((String) ht.get("MATNR"));
		WTPart parentPart = (WTPart) KETObjectUtil.getObject(bomqr.getLatestPart((String) ht.get("MATNR"), wtConn)); // Latest
		// WTPart childPart = searchItem( (String)ht.get("IDNRK") );

		EcmComDao ecmDao = new EcmComDao(conn);

		WTPart childPart = (WTPart) KETObjectUtil.getObject(bomqr.getLatestPart((String) ht.get("IDNRK"), wtConn)); // Latest
		// Version
		// WTPart childPart = (WTPart) KETObjectUtil.getObject(ecmDao.getLastestPart((String) ht.get("IDNRK"))); // Latest Version

		WTPartMaster childPartMaster = (WTPartMaster) (childPart.getMaster());

		if (ecoCode.equalsIgnoreCase("Add")) {
		    reSult = createPartUsageLink(ht, parentPart, childPart, childPartMaster, chk, conn);

		} else if (ecoCode.equalsIgnoreCase("Update")) {
		    reSult = updatePartUsageLink(ht, parentPart, childPart, childPartMaster, chk, conn);

		} else if (ecoCode.equalsIgnoreCase("Remove")) {
		    reSult = removePartUsageLink(ht, parentPart, childPart, childPartMaster, chk, conn);
		}

		// DB fail case
		if (!reSult) {
		    break;
		}
	    }

	    if (vc.size() == 0) {
		reSult = true;
	    }

	    // when All case Success
	    if (reSult) {
		// conn.commit();
		transaction.commit();
		transaction = null;
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    reSult = false;

	    /*
	     * try { reSult = false; //conn.rollback(); Kogger.error(getClass(), e); } catch (SQLException se) { Kogger.error(getClass(),
	     * se); }
	     */
	} finally {

	    /*
	     * if (res != null) { res.freeConnection(registry.getString("plm"), conn); }
	     */

	    if (transaction != null) {
		transaction.rollback();
		reSult = false;
	    }
	}

	return reSult;
    }

    @Override
    public Boolean[] getBomInterface3(String partOid) throws WTException {
	Boolean ret[] = new Boolean[2];
	boolean reSult = false;
	boolean isPartErpInterFaceFail = false;

	Transaction transaction = new Transaction();
	try {

	    /*
	     * String startEcoOid = (isProd) ? "e3ps.ecm.entity.KETProdChangeOrder:" + ecoLongValue : "e3ps.ecm.entity.KETMoldChangeOrder:"
	     * + ecoLongValue;
	     */

	    transaction.start();

	    BomEcoInfoInterface bef = new BomEcoInfoInterface();
	    InfoInterfaceQry iif = new InfoInterfaceQry();

	    /*
	     * reSult = bef.getInterFaceResult(oid);
	     */

	    KETBOMHeaderQueryBean ketBOMHeaderQueryBean = new KETBOMHeaderQueryBean();
	    BomEcoInfoInterfaceQry bomEcoInfoInterfaceQry = new BomEcoInfoInterfaceQry();
	    BomInfoInterface bomInfoInterface = new BomInfoInterface();
	    KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();
	    ProdEcoBeans prodEcoBeans = new ProdEcoBeans();

	    WTConnection wtConn = EcmUtil.getWTConnection();
	    Connection conn = wtConn.getConnection();
	    try {

		// ====================================================================================================
		// I. ECO의 설변에 추가된 Part List를 가져온다.
		// ====================================================================================================
		String ECO_NUMBER = prodEcoBeans.getNewProdEcoId();

		/*
	         * ArrayList<Hashtable> ecoList = bomEcoInfoInterfaceQry.getItemCode(ecoLongValue); // ECO에 부품이 여러개가 지정될 수 있으므로 int
	         * ecoListSize = (ecoList != null) ? ecoList.size() : 0;
	         * 
	         * // I. Part Hashtable<String, Vector<Hashtable<String, String>>> ht = null; Vector<Hashtable<String, String>> vc = null;
	         * Vector<Hashtable<String, String>> vtr = null;
	         */

		// ECO에서 초도대상으로 추가한 부품을 가져온다.
		ArrayList<WTPart> partList = new ArrayList<WTPart>();

		WTPart wtPart = (WTPart) CommonUtil.getObject(partOid);
		partList.add(wtPart);

		/*
	         * for (int inx = 0; inx < ecoListSize; inx++) {
	         * 
	         * Hashtable hash = ecoList.get(inx); String headerOid = (String) hash.get("headerOid"); String headerLongValue = (String)
	         * hash.get("headerLongValue"); String itemCd = (String) hash.get("itemcode"); ECO_NUMBER = (String) hash.get("econum");
	         * 
	         * Kogger.info(getClass(), "@@@@@@@@@@@@ ECO getInterFaceResult ERP @@@@@@@@@@@ "); Kogger.info(getClass(),
	         * "@@@@ headerOid : " + headerOid); Kogger.info(getClass(), "@@@@ headerLongValue : " + headerLongValue);
	         * Kogger.info(getClass(), "@@@@ itemCd : " + itemCd); Kogger.info(getClass(), "@@@@ econum : " + ECO_NUMBER);
	         * 
	         * WTPart wtPartAddedEco = ketBOMHeaderQueryBean.searchItem(itemCd);
	         * 
	         * // BOM 작업이 없는 부품을 ECO에 보내기 위한 코드이다. if (!partList.contains(wtPartAddedEco)) partList.add(wtPartAddedEco);
	         * 
	         * String strType = PartSpecGetter.getPartSpec(wtPartAddedEco, PartSpecEnum.SpPartType); // strType =
	         * IBAUtil.getAttrValue(part, "PartType");
	         * 
	         * 
	         * BomInfoInterface sf = new BomInfoInterface(); if (sf.getInterFaceResult(oid)) {
	         * 
	         * 
	         * //
	         * 
	         * KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean(); InfoInterfaceQry iifQry = new InfoInterfaceQry(); String itemCd =
	         * iifQry.getItemCode(headCd); // WTPart.number WTPart part = kh.searchItem(itemCd); strType = IBAUtil.getAttrValue(part,
	         * "PartType");
	         * 
	         * 
	         * Kogger.info(getClass(), "@@@@ strType : " + strType); Kogger.info(getClass(), "@@@@ PartUtil.isProductType(strType) : " +
	         * PartUtil.isProductType(strType)); if (PartUtil.isProductType(strType)) { ht = iif.getGenInterfaceData(headerLongValue);
	         * 
	         * } else if (strType.equals("D"))// || strType.equals("M")) //M이 올수 있을려나...?? { ht =
	         * iif.getMoldInterfaceData(headerLongValue);
	         * 
	         * } else { Kogger.info(getClass(), "지정된 값이 없는 PartType 입니다."); }
	         * 
	         * if (ht != null) { vc = (Vector<Hashtable<String, String>>) ht.get("HEAD"); vtr = (Vector<Hashtable<String, String>>)
	         * ht.get("COMP");
	         * 
	         * Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE"); Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
	         * Kogger.info(getClass(), "vc is \n" + vc.toString()); Kogger.info(getClass(), "vtr is \n" + vtr.toString());
	         * Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE"); Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
	         * 
	         * partList = iif.getPartList4SapIFbyPartMaster(vc, vtr, partList);
	         * 
	         * }
	         * 
	         * }
	         */

		// ====================================================================================================
		// II. ERP I/F 를 해야하는지 말아야 하는지를 조사한다.
		// ====================================================================================================
		boolean DO_SEND_ERP = true;

		/*
	         * // 제품일 경우 if (isProd) {
	         * 
	         * DO_SEND_ERP = true;
	         * 
	         * } // 금형일 경우 else { int partListSize = (partList != null) ? partList.size() : 0; for (int i = 0; i < partListSize; i++) {
	         * WTPart wtPart = (WTPart) partList.get(i); String strType = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
	         * if (strType.equals("D")) {
	         * 
	         * DO_SEND_ERP = true;
	         * 
	         * } } }
	         */

		Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		Kogger.info(getClass(), "$$$$$$$$$$$$$$$$ DO_SEND_ERP is " + DO_SEND_ERP);
		Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

		// ====================================================================================================
		// III. ERP I/F 를 하지 말아야 하는 경우
		// ====================================================================================================
		if (!DO_SEND_ERP) {
		    /*
	             * if (!DO_SEND_ERP) {
	             * 
	             * reSult = true;
	             * 
	             * try{
	             * 
	             * for (int inx = 0; inx < ecoListSize; inx++) {
	             * 
	             * Hashtable hash = ecoList.get(inx); String headerOid = (String) hash.get("headerOid"); String headerLongValue =
	             * (String) hash.get("headerLongValue"); String itemCd = (String) hash.get("itemcode"); String econum = (String)
	             * hash.get("econum");
	             * 
	             * // 헤더 상태변경 KETBomEcoHeader bomHeader = (KETBomEcoHeader) CommonUtil.getObject(headerOid);
	             * 
	             * Kogger.info(getClass(), "@@@@@@@@@@@@ BomHeader change APPROVED ERP @@@@@@@@@@@ "); Kogger.info(getClass(),
	             * "@@@@ BomHeader : " + bomHeader.toString());
	             * 
	             * LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, State.toState("APPROVED"));
	             * 
	             * Kogger.info(getClass(), "@@@@@@@@@@@@ Set Flag Y means Success ERP @@@@@@@@@@@ "); Kogger.info(getClass(),
	             * "@@@@ headerLongValue : " + headerLongValue); Kogger.info(getClass(), "@@@@ Y ");
	             * 
	             * // iif.setSuccessFlag(headCd,"Y"); iif.setFalseFlag(headerLongValue, "Y", conn);
	             * 
	             * }
	             * 
	             * }catch(Exception e){
	             * 
	             * KogDbUtil.log( "ECO 초도 BOM Header 변경 에러 - No ErpI/F", "Fail", startEcoOid, e, null, null); throw e; }
	             * 
	             * 
	             * try{
	             * 
	             * // 부품, BOM 모두 성공하였을 경우에만 부품 상태를 'APPROVED'로 변경한다. int partListSize = (partList != null) ? partList.size() : 0; for
	             * (int j = 0; j < partListSize; j++) { WTPart wtPart = partList.get(j);
	             * 
	             * Kogger.info(getClass(), "@@@@@@@@@@@@ WTPart change APPROVED @@@@@@@@@@@ "); Kogger.info(getClass(),
	             * wtPart.getNumber() + ", " + wtPart.getVersionIdentifier().getValue() + ", " + CommonUtil.getOIDString(wtPart));
	             * 
	             * PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpEoNo, ECO_NUMBER); PersistenceServerHelper.update(wtPart); wtPart =
	             * (WTPart) PersistenceHelper.manager.refresh(wtPart);
	             * 
	             * LifeCycleServerHelper.service.setState((LifeCycleManaged) wtPart, State.toState("APPROVED"));
	             * 
	             * }
	             * 
	             * }catch(Exception e){
	             * 
	             * KogDbUtil.log( "ECO 초도 부품 상태 변경 에러 - No ErpI/F", "Fail", startEcoOid, e, null, null); throw e; }
	             */

		    /*
	             * try{
	             * 
	             * // 도면처리 // WTChangeOrder2 eco = prodEcoBeans.getEcoByNo(ECO_NUMBER); String ecoOid = (isProd) ?
	             * "e3ps.ecm.entity.KETProdChangeOrder:" + ecoLongValue : "e3ps.ecm.entity.KETMoldChangeOrder:" + ecoLongValue;
	             * WTChangeOrder2 eco = (WTChangeOrder2) CommonUtil.getObject(ecoOid); Kogger.info(getClass(), "@@@@@@@@@@@@ eco is " +
	             * eco);
	             * 
	             * Object[] eca = EcmUtil.getChangeActivities(eco); if (eca != null) {
	             * 
	             * for (int cnt = 0; cnt < eca.length; cnt++) {
	             * 
	             * Object object = (Object) eca[cnt]; if (object instanceof KETProdChangeActivity) { KETProdChangeActivity prodCA =
	             * (KETProdChangeActivity) object; if (!prodCA.getActivityType().equals("3") && !prodCA.getActivityType().equals("4")) {
	             * Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromProdECA(prodCA); for (int i = 0; i <
	             * epmVec.size(); i++) { EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));
	             * LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("APPROVED")); }
	             * 
	             * }
	             * 
	             * } else { KETMoldChangeActivity moldCA = (KETMoldChangeActivity) object; if (!moldCA.getActivityType().equals("4")) {
	             * Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromMoldECA(moldCA); for (int i = 0; i <
	             * epmVec.size(); i++) { EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));
	             * LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("APPROVED")); }
	             * 
	             * }
	             * 
	             * }
	             * 
	             * } }
	             * 
	             * }catch(Exception e){
	             * 
	             * KogDbUtil.log( "ECO 초도 도면 상태 변경 에러 - No ErpI/F", "Fail", startEcoOid, e, null, null); throw e; }
	             */

		}
		// ====================================================================================================
		// IV. ERP I/F 를 해야 하는 경우
		// ====================================================================================================
		else {

		    // ///////////////////////////////////////////////////////////////////////////
		    /*
	             * 일반자재생성 Z_ST_CREATE_MATERIAL_GENERAL 일반자재수정 Z_ST_CHANGE_MATERIAL_GENERAL 인수 I_ECONO 테이블 IT_TYPE0
	             * 
	             * 금형자재생성 Z_ST_CREATE_MATERIAL_MOLD 금형자재수정 Z_ST_CREATE_MATERIAL_MOLD 인수 I_ECONO 테이블 IT_TYPE7 테이블 IT_TYPE7I 테이블 IT_TYPE8
	             */
		    String ecoNo = ECO_NUMBER;
		    Map<String, String> workerNameMap = null;
		    ErpPartInterFace erpPartInterFace = new ErpPartInterFace();
		    Map resultMap = erpPartInterFace.sendPartInfoToErp(partList, ecoNo, workerNameMap);

		    boolean success = (Boolean) resultMap.get(ErpPartInterFace.SUCCESS);
		    if (!success) {

			reSult = false;

			// List<String> errorLogList = (List<String>) resultMap.get(ErpPartInterFace.PLM_ERROR_LIST);
			String erpMsg = (String) resultMap.get(ErpPartInterFace.ERP_ERROR_MSG);
			Kogger.info(getClass(), erpMsg);

			// KogDbUtil.log( "ECO 초도 ERP 부품 I/F 에러", "Fail", startEcoOid, erpMsg, null, null);

			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$ ERP IF ERROR $$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

		    } else {
			reSult = true;
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$ PART ERP IF SUCCESS $$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

			/*
		         * // 설변에 추가된 부품이 없을 경우 if (ecoListSize == 0) { // 도면만 붙어있을 경우 도면을 '승인완료'처리하기 위한 로직 reSult = true; } // 설변에 추가된 부품이
		         * 있을 경우 else {
		         * 
		         * for (int inx = 0; inx < ecoListSize; inx++) {
		         * 
		         * Hashtable hash = ecoList.get(inx); String headerOid = (String) hash.get("headerOid"); String headerLongValue =
		         * (String) hash.get("headerLongValue"); String itemCd = (String) hash.get("itemcode"); String econum = (String)
		         * hash.get("econum");
		         * 
		         * Kogger.info(getClass(), "@@@@@@@@@@@@ ECO getInterFaceResult ERP @@@@@@@@@@@ "); Kogger.info(getClass(),
		         * "@@@@ headerOid : " + headerOid); Kogger.info(getClass(), "@@@@ headerLongValue : " + headerLongValue);
		         * Kogger.info(getClass(), "@@@@ itemCd : " + itemCd); Kogger.info(getClass(), "@@@@ econum : " + econum);
		         * 
		         * WTPart part = ketBOMHeaderQueryBean.searchItem(itemCd); String strType = PartSpecGetter.getPartSpec(part,
		         * PartSpecEnum.SpPartType); // strType = IBAUtil.getAttrValue(part, "PartType");
		         * 
		         * 
		         * BomInfoInterface sf = new BomInfoInterface(); if (sf.getInterFaceResult(oid)) {
		         * 
		         * 
		         * //
		         * 
		         * KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean(); InfoInterfaceQry iifQry = new InfoInterfaceQry(); String
		         * itemCd = iifQry.getItemCode(headCd); // WTPart.number WTPart part = kh.searchItem(itemCd); strType =
		         * IBAUtil.getAttrValue(part, "PartType");
		         * 
		         * 
		         * Kogger.info(getClass(), "@@@@ strType : " + strType); Kogger.info(getClass(),
		         * "@@@@ PartUtil.isProductType(strType) : " + PartUtil.isProductType(strType)); if
		         * (PartUtil.isProductType(strType)) { reSult = bomInfoInterface.getGenInfo2(econum, headerLongValue);
		         * 
		         * } else if (strType.equals("D"))// || strType.equals("M")) //M이 올수 있을려나...?? { reSult =
		         * bomInfoInterface.getMoldInfo2(econum, headerLongValue);
		         * 
		         * } else { Kogger.info(getClass(), "지정된 값이 없는 PartType 입니다."); }
		         * 
		         * if (reSult) {
		         * 
		         * try{
		         * 
		         * // 헤더 상태변경 KETBomEcoHeader bomHeader = (KETBomEcoHeader) CommonUtil.getObject(headerOid);
		         * 
		         * Kogger.info(getClass(), "@@@@@@@@@@@@ BomHeader change APPROVED ERP @@@@@@@@@@@ "); Kogger.info(getClass(),
		         * "@@@@ BomHeader : " + bomHeader.toString());
		         * 
		         * LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, State.toState("APPROVED"));
		         * 
		         * Kogger.info(getClass(), "@@@@@@@@@@@@ Set Flag Y means Success ERP @@@@@@@@@@@ "); Kogger.info(getClass(),
		         * "@@@@ headerLongValue : " + headerLongValue); Kogger.info(getClass(), "@@@@ Y ");
		         * 
		         * // iif.setSuccessFlag(headCd,"Y"); iif.setFalseFlag(headerLongValue, "Y", conn);
		         * 
		         * }catch(Exception e){
		         * 
		         * KogDbUtil.log( "ECO 초도 BOM Header 변경 에러 - with Erp I/F", "Fail", startEcoOid, e, null, null); throw e; }
		         * 
		         * } else {
		         * 
		         * Kogger.info(getClass(), "@@@@@@@@@@@@ Set Flag 2 means Fail ERP @@@@@@@@@@@ "); Kogger.info(getClass(),
		         * "@@@@ headerLongValue : " + headerLongValue); Kogger.info(getClass(), "@@@@ 2 ");
		         * 
		         * KogDbUtil.log( "ECO 초도 ERP BOM 구조 I/F 에러", "Fail", startEcoOid, (Throwable)null, null, null);
		         * 
		         * iif.setFalseFlag(headerLongValue, "2", conn);
		         * 
		         * 
		         * 하나의 ECO에 붙은 여러 설변(초도)부품 중 하나라도 ERP I/F에 실패하면 성공한 것까지는 ketbomecoheader, ketbomecocomponent 에 각각 transferflag='Y' 를
		         * set 하고, 실패한 것에서 바로 ketbomecoheader, ketbomecocomponent 에 각각 transferflag='2' 를 set 하고, 아직 나머지가 있더라도 멈춘다.
		         * 
		         * break;
		         * 
		         * }
		         * 
		         * // Usage Link 처리 if (reSult) {
		         * 
		         * Kogger.info(getClass(), "@@@@@@@@@@@@ Usage Link Process ERP @@@@@@@@@@@ "); Kogger.info(getClass(),
		         * "@@@@ headerLongValue : " + headerLongValue);
		         * 
		         * WFBomPartUsageQry wp = new WFBomPartUsageQry(); reSult = wp.getPartUsageResult(headerLongValue);
		         * 
		         * if (!reSult) { KogDbUtil.log("ECO 초도 BOM UsageLink 에러 - with Erp I/F", "Fail", startEcoOid, (Throwable)null,
		         * null, null); } } } }
		         */

			// Part 상태를 Approved로 set 한다.
			if (reSult) {

			    try {

				// 부품, BOM 모두 성공하였을 경우에만 부품 상태를 'APPROVED'로 변경한다.
				int partListSize = (partList != null) ? partList.size() : 0;
				for (int j = 0; j < partListSize; j++) {
				    WTPart wtPart2 = partList.get(j);

				    Kogger.info(getClass(), "@@@@@@@@@@@@ WTPart change APPROVED ERP @@@@@@@@@@@ ");
				    Kogger.info(getClass(), "@@@@ wtPart : " + wtPart.getNumber());

				    PartSpecSetter.setPartSpec(wtPart2, PartSpecEnum.SpEoNo, ECO_NUMBER);
				    PersistenceServerHelper.update(wtPart2);
				    wtPart2 = (WTPart) PersistenceHelper.manager.refresh(wtPart2);

				    LifeCycleServerHelper.service.setState((LifeCycleManaged) wtPart2, State.toState("APPROVED"));

				}

			    } catch (Exception e) {

				// KogDbUtil.log( "ECO 초도 부품 상태 변경 에러 - with Erp I/F", "Fail", startEcoOid, e, null, null);
				throw e;
			    }

			    /*
		             * try{
		             * 
		             * // 도면처리 // WTChangeOrder2 eco = prodEcoBeans.getEcoByNo(ECO_NUMBER); String ecoOid = (isProd) ?
		             * "e3ps.ecm.entity.KETProdChangeOrder:" + ecoLongValue : "e3ps.ecm.entity.KETMoldChangeOrder:" + ecoLongValue;
		             * WTChangeOrder2 eco = (WTChangeOrder2) CommonUtil.getObject(ecoOid); Kogger.info(getClass(),
		             * "@@@@@@@@@@@@ eco is " + eco);
		             * 
		             * Object[] eca = EcmUtil.getChangeActivities(eco); if (eca != null) {
		             * 
		             * for (int cnt = 0; cnt < eca.length; cnt++) {
		             * 
		             * Object object = (Object) eca[cnt]; if (object instanceof KETProdChangeActivity) { KETProdChangeActivity
		             * prodCA = (KETProdChangeActivity) object; if (!prodCA.getActivityType().equals("3") &&
		             * !prodCA.getActivityType().equals("4")) { Vector<EPMDocument> epmVec =
		             * WorkflowSearchHelper.manager.getEPMfromProdECA(prodCA); for (int i = 0; i < epmVec.size(); i++) { EPMDocument
		             * epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));
		             * LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("APPROVED")); }
		             * 
		             * }
		             * 
		             * } else { KETMoldChangeActivity moldCA = (KETMoldChangeActivity) object; if
		             * (!moldCA.getActivityType().equals("4")) { Vector<EPMDocument> epmVec =
		             * WorkflowSearchHelper.manager.getEPMfromMoldECA(moldCA); for (int i = 0; i < epmVec.size(); i++) { EPMDocument
		             * epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));
		             * LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("APPROVED")); }
		             * 
		             * }
		             * 
		             * }
		             * 
		             * } }
		             * 
		             * }catch(Exception e){
		             * 
		             * KogDbUtil.log( "ECO 초도 도면 상태 변경 에러 - with Erp I/F", "Fail", startEcoOid, e, null, null); throw e; }
		             */

			}

		    }

		}

	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		reSult = false;
	    }

	    // when All case Success
	    // SAP I/F 가 실패하여도 결재승인은 나고 차후 '재전송'으로 다시 시도한다.
	    if (reSult) {
		transaction.commit();
		transaction = null;
	    }

	} catch (Exception e) {
	    reSult = false;
	    Kogger.error(getClass(), e);
	} finally {
	    if (transaction != null) {
		transaction.rollback();
		transaction = null;

		reSult = false;
	    }
	}

	ret[0] = reSult;
	ret[1] = isPartErpInterFaceFail;
	return ret;
    }

    /*
     * @see e3ps.bom.service.KETBomService#getBomEcoInterface(java.lang.String)
     */
    @Override
    public Boolean getBomEcoInterface(String ecoLongValue) throws WTException {
	return this.getBomEcoInterface(ecoLongValue, true)[0];
    }

    /*
     * 설변일 경우(제품, 금형)
     * 
     * @see e3ps.bom.service.KETBomService#getBomEcoInterface(java.lang.String)
     */
    @Override
    public Boolean[] getBomEcoInterface(String ecoLongValue, boolean isProd) throws WTException {
	Boolean ret[] = new Boolean[2];
	boolean reSult = false;
	boolean isPartErpInterFaceFail = false;

	Transaction transaction = new Transaction();

	PartDieInfoHandler partDieInfoHandler = new PartDieInfoHandler();

	try {

	    String startEcoOid = (isProd) ? "e3ps.ecm.entity.KETProdChangeOrder:" + ecoLongValue : "e3ps.ecm.entity.KETMoldChangeOrder:" + ecoLongValue;

	    transaction.start();

	    BomEcoInfoInterface bomEcoInfoInterface = new BomEcoInfoInterface();
	    InfoInterfaceQry infoInterfaceQry = new InfoInterfaceQry();

	    /*
	     * reSult = bef.getInterFaceResult(oid);
	     */

	    KETBOMHeaderQueryBean ketBOMHeaderQueryBean = new KETBOMHeaderQueryBean();
	    BomEcoInfoInterfaceQry bomEcoInfoInterfaceQry = new BomEcoInfoInterfaceQry();
	    KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();
	    ProdEcoBeans prodEcoBeans = new ProdEcoBeans();

	    WTConnection wtConn = EcmUtil.getWTConnection();
	    Connection conn = wtConn.getConnection();
	    try {

		// ====================================================================================================
		// I. ECO의 설변에 추가된 Part List를 가져온다.
		// ====================================================================================================
		String ECO_NUMBER = "";
		WTChangeOrder2 wtChageOrder2 = null;

		ArrayList<Hashtable> ecoList = bomEcoInfoInterfaceQry.getItemCode(ecoLongValue); // ECO에 부품이 여러개가 지정될 수 있으므로
		int ecoListSize = (ecoList != null) ? ecoList.size() : 0;

		// I. Part
		Hashtable<String, Vector<Hashtable<String, String>>> ht = null;
		Vector<Hashtable<String, String>> vc = null;
		Vector<Hashtable<String, String>> vtr = null;

		// ECO에서 설변대상으로 추가한 부품을 가져온다.
		ArrayList<WTPart> partList = new ArrayList<WTPart>();

		for (int inx = 0; inx < ecoListSize; inx++) {

		    Hashtable hash = ecoList.get(inx);
		    String itemCd = (String) hash.get("itemcode");
		    ECO_NUMBER = (String) hash.get("econum");

		    String ecoOid = (String) hash.get("ecoOid");
		    wtChageOrder2 = (WTChangeOrder2) CommonUtil.getObject(ecoOid);

		    Kogger.info(getClass(), "@@@@@@@@@@@@ ECO getInterFaceResult ERP @@@@@@@@@@@ ");
		    Kogger.info(getClass(), "@@@@ itemCd : " + itemCd);
		    Kogger.info(getClass(), "@@@@ econum : " + ECO_NUMBER);

		    WTPart wtPartAddedEco = ketBOMHeaderQueryBean.searchItem(itemCd);

		    // BOM 작업이 없는 부품을 ECO에 보내기 위한 코드이다.
		    if (!partList.contains(wtPartAddedEco))
			partList.add(wtPartAddedEco);

		    String strType = PartSpecGetter.getPartSpec(wtPartAddedEco, PartSpecEnum.SpPartType);
		    // strType = IBAUtil.getAttrValue(part, "PartType");

		    Kogger.info(getClass(), "@@@@ strType : " + strType);
		    Kogger.info(getClass(), "@@@@ PartUtil.isProductType(strType) : " + PartUtil.isProductType(strType));
		    if (PartUtil.isProductType(strType)) {
			// II. ECO에 직접 추가된 것을 HEAD로 그 HEAD에 '변경, 추가, 삭제'된 것을 COMP로 가져온다.
			ht = bomEcoInfoInterfaceQry.getGenInterfaceData(ECO_NUMBER, itemCd, "");

		    } else if (strType.equals("D")) // M이 올수 있을려나...?? 현재 있다.
		    {
			// II. ECO에 직접 추가된 것을 HEAD로 그 HEAD에 '변경, 추가, 삭제'된 것을 COMP로 가져온다.
			ht = bomEcoInfoInterfaceQry.getMoldInterfaceData(ECO_NUMBER, itemCd);

		    } else {
			Kogger.info(getClass(), "지정된 값이 없는 PartType 입니다.");
		    }

		    if (ht != null) {
			vc = (Vector<Hashtable<String, String>>) ht.get("HEAD");
			vtr = (Vector<Hashtable<String, String>>) ht.get("COMP");

			Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
			Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
			Kogger.info(getClass(), "vc is \n" + vc.toString());
			Kogger.info(getClass(), "vtr is \n" + vtr.toString());
			Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
			Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");

			partList = infoInterfaceQry.getPartList4SapIFbyPartMaster(vc, vtr, partList);
		    }

		}

		// ====================================================================================================
		// II. ERP I/F 를 해야하는지 말아야 하는지를 조사한다.
		// ====================================================================================================
		boolean DO_SEND_ERP = false;

		// 제품일 경우
		if (isProd) {

		    DO_SEND_ERP = true;

		}
		// 금형일 경우
		else {
		    int partListSize = (partList != null) ? partList.size() : 0;
		    for (int i = 0; i < partListSize; i++) {
			WTPart wtPart = (WTPart) partList.get(i);
			String strType = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
			if (strType.equals("D")) {

			    DO_SEND_ERP = true;

			}
		    }
		}

		Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		Kogger.info(getClass(), "$$$$$$$$$$$$$$$$ DO_SEND_ERP is " + DO_SEND_ERP);
		Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

		// ====================================================================================================
		// III. ERP I/F 를 하지 말아야 하는 경우
		// ====================================================================================================
		if (!DO_SEND_ERP) {

		    reSult = true;

		    try {

			for (int inx = 0; inx < ecoListSize; inx++) {

			    Hashtable hash = ecoList.get(inx);
			    String itemCd = (String) hash.get("itemcode");

			    Kogger.info(getClass(), "@@@@@@@@@@@@ Set Flag Y means Success ERP @@@@@@@@@@@ ");
			    Kogger.info(getClass(), "@@@@ econum : " + ECO_NUMBER);
			    Kogger.info(getClass(), "@@@@ itemCd : " + itemCd);
			    Kogger.info(getClass(), "@@@@ Y ");

			    bomEcoInfoInterfaceQry.setFalseFlag(ECO_NUMBER, itemCd, "Y", conn);

			}

		    } catch (Exception e) {

			KogDbUtil.log("ECO 설변 BOM Header 변경 에러 - No ErpI/F", "Fail", startEcoOid, e, null, null);
			throw e;
		    }

		    try {

			// 부품, BOM 모두 성공하였을 경우에만 부품 상태를 'APPROVED'로 변경한다.
			int partListSize = (partList != null) ? partList.size() : 0;
			for (int j = 0; j < partListSize; j++) {
			    WTPart wtPart = partList.get(j);

			    Kogger.info(getClass(), "@@@@@@@@@@@@ WTPart change APPROVED @@@@@@@@@@@ ");
			    Kogger.info(getClass(), wtPart.getNumber() + ", " + wtPart.getVersionIdentifier().getValue() + ", " + CommonUtil.getOIDString(wtPart));

			    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpEoNo, ECO_NUMBER);
			    PersistenceServerHelper.update(wtPart);
			    wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
			    partDieInfoHandler.updatePartDieInfo(wtPart);
			    LifeCycleServerHelper.service.setState((LifeCycleManaged) wtPart, State.toState("APPROVED"));

			}

		    } catch (Exception e) {

			KogDbUtil.log("ECO 설변 부품 상태 변경 에러 - No ErpI/F", "Fail", startEcoOid, e, null, null);
			throw e;
		    }

		    try {

			// 도면처리
			// WTChangeOrder2 eco = prodEcoBeans.getEcoByNo(ECO_NUMBER);
			String ecoOid = (isProd) ? "e3ps.ecm.entity.KETProdChangeOrder:" + ecoLongValue : "e3ps.ecm.entity.KETMoldChangeOrder:" + ecoLongValue;
			WTChangeOrder2 eco = (WTChangeOrder2) CommonUtil.getObject(ecoOid);
			Kogger.info(getClass(), "@@@@@@@@@@@@ eco is " + eco);

			Object[] eca = EcmUtil.getChangeActivities(eco);
			if (eca != null) {

			    for (int cnt = 0; cnt < eca.length; cnt++) {

				Object object = (Object) eca[cnt];
				if (object instanceof KETProdChangeActivity) {
				    KETProdChangeActivity prodCA = (KETProdChangeActivity) object;
				    if (!prodCA.getActivityType().equals("3") && !prodCA.getActivityType().equals("4")) {
					Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromProdECA(prodCA);
					for (int i = 0; i < epmVec.size(); i++) {
					    EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));
					    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("APPROVED"));
					}

				    }

				} else {
				    KETMoldChangeActivity moldCA = (KETMoldChangeActivity) object;
				    if (!moldCA.getActivityType().equals("4")) {
					Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromMoldECA(moldCA);
					for (int i = 0; i < epmVec.size(); i++) {
					    EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));
					    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("APPROVED"));
					}

				    }

				}

			    }
			}

		    } catch (Exception e) {

			KogDbUtil.log("ECO 설변 도면 상태 변경 에러 - No ErpI/F", "Fail", startEcoOid, e, null, null);
			throw e;
		    }
		}
		// ====================================================================================================
		// IV. ERP I/F 를 해야 하는 경우
		// ====================================================================================================
		else {

		    // ///////////////////////////////////////////////////////////////////////////
		    /*
	             * 일반자재생성 Z_ST_CREATE_MATERIAL_GENERAL 일반자재수정 Z_ST_CHANGE_MATERIAL_GENERAL 인수 I_ECONO 테이블 IT_TYPE0
	             * 
	             * 금형자재생성 Z_ST_CREATE_MATERIAL_MOLD 금형자재수정 Z_ST_CREATE_MATERIAL_MOLD 인수 I_ECONO 테이블 IT_TYPE7 테이블 IT_TYPE7I 테이블 IT_TYPE8
	             */

		    String ecoNo = ECO_NUMBER;
		    Map<String, String> workerNameMap = null;
		    ErpPartInterFace erpPartInterFace = new ErpPartInterFace();
		    Map resultMap = erpPartInterFace.sendPartInfoToErp(partList, ecoNo, workerNameMap);

		    boolean success = (Boolean) resultMap.get(ErpPartInterFace.SUCCESS);
		    if (!success) {

			List<String> errorLogList = (List<String>) resultMap.get(ErpPartInterFace.PLM_ERROR_LIST);
			String erpMsg = (String) resultMap.get(ErpPartInterFace.ERP_ERROR_MSG);
			Kogger.info(getClass(), erpMsg);

			KogDbUtil.log("ECO 설변 ERP 부품 I/F 에러", "Fail", startEcoOid, erpMsg, null, null);

			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$ PART ERP IF ERROR $$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

		    } else {

			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$ PART ERP IF SUCCESS $$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Kogger.info(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

			// 설변에 추가된 부품이 없을 경우
			if (ecoListSize == 0) {
			    Kogger.info(getClass(), "@@@@@@@@@@@@ 설변에 추가된 부품이 없을 경우 @@@@@@@@@@@ ");

			    // 도면만 붙어있을 경우 도면을 '승인완료'처리하기 위한 로직
			    reSult = true;
			}
			// 설변에 추가된 부품이 있을 경우
			else {
			    Kogger.info(getClass(), "@@@@@@@@@@@@ 설변에 추가된 부품이 있을 경우 @@@@@@@@@@@ ");

			    try {

				// II. BOM
				// ECO에 추가한 부품들을 가져온다. 코드를 따라가보면 알겠지만 BOM 에디터에서 하위 1레벨만 수정해야 하는 이유가 나온다.
				for (int inx = 0; inx < ecoListSize; inx++) {

				    Hashtable hash = ecoList.get(inx);
				    String itemCd = (String) hash.get("itemcode");
				    // String econum = (String) hash.get("econum");

				    Kogger.info(getClass(), "@@@@@@@@@@@@ ECO getInterFaceResult ERP @@@@@@@@@@@ ");
				    Kogger.info(getClass(), "@@@@ itemCd : " + itemCd);
				    Kogger.info(getClass(), "@@@@ econum : " + ECO_NUMBER);

				    WTPart part = ketBOMHeaderQueryBean.searchItem(itemCd);
				    String strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
				    // strType = IBAUtil.getAttrValue(part, "PartType");

				    Kogger.info(getClass(), "@@@@ strType : " + strType);
				    Kogger.info(getClass(), "@@@@ PartUtil.isProductType(strType) : " + PartUtil.isProductType(strType));
				    if (PartUtil.isProductType(strType)) {
					reSult = bomEcoInfoInterface.getGenInfo(ECO_NUMBER, itemCd);

					if (reSult) {

					    Kogger.info(getClass(), "@@@@@@@@@@@@ Set Flag Y means Success ERP @@@@@@@@@@@ ");
					    Kogger.info(getClass(), "@@@@ econum : " + ECO_NUMBER);
					    Kogger.info(getClass(), "@@@@ itemCd : " + itemCd);
					    Kogger.info(getClass(), "@@@@ Y ");

					    bomEcoInfoInterfaceQry.setFalseFlag(ECO_NUMBER, itemCd, "Y", conn);

					} else {

					    Kogger.info(getClass(), "@@@@@@@@@@@@ Set Flag 2 means Fail ERP @@@@@@@@@@@ ");
					    Kogger.info(getClass(), "@@@@ econum : " + ECO_NUMBER);
					    Kogger.info(getClass(), "@@@@ itemCd : " + itemCd);
					    Kogger.info(getClass(), "@@@@ 2 ");

					    KogDbUtil.log("ECO 설변 ERP BOM 구조 I/F 에러", "Fail", startEcoOid, (Throwable) null, null, null);

					    bomEcoInfoInterfaceQry.setFalseFlag(ECO_NUMBER, itemCd, "2", conn);

					    /*
			                     * 하나의 ECO에 붙은 여러 설변(초도)부품 중 하나라도 ERP I/F에 실패하면 성공한 것까지는 ketbomecoheader, ketbomecocomponent 에
			                     * 각각 transferflag='Y' 를 set 하고, 실패한 것에서 바로 ketbomecoheader, ketbomecocomponent 에 각각
			                     * transferflag='2' 를 set 하고, 아직 나머지가 있더라도 멈춘다.
			                     */
					    break;

					}

				    } else if (strType.equals("D")) // M이 올수 있을려나...?? 현재 있다.
				    {
					reSult = bomEcoInfoInterface.getMoldInfo(ECO_NUMBER, itemCd);

					if (reSult) {

					    Kogger.info(getClass(), "@@@@@@@@@@@@ Set Flag Y means Success ERP @@@@@@@@@@@ ");
					    Kogger.info(getClass(), "@@@@ econum : " + ECO_NUMBER);
					    Kogger.info(getClass(), "@@@@ itemCd : " + itemCd);
					    Kogger.info(getClass(), "@@@@ Y ");

					    bomEcoInfoInterfaceQry.setFalseFlag(ECO_NUMBER, itemCd, "Y", conn);

					} else {

					    Kogger.info(getClass(), "@@@@@@@@@@@@ Set Flag 2 means Fail ERP @@@@@@@@@@@ ");
					    Kogger.info(getClass(), "@@@@ econum : " + ECO_NUMBER);
					    Kogger.info(getClass(), "@@@@ itemCd : " + itemCd);
					    Kogger.info(getClass(), "@@@@ 2 ");

					    bomEcoInfoInterfaceQry.setFalseFlag(ECO_NUMBER, itemCd, "2", conn);

					    /*
			                     * 하나의 ECO에 붙은 여러 설변(초도)부품 중 하나라도 ERP I/F에 실패하면 성공한 것까지는 ketbomecoheader, ketbomecocomponent 에
			                     * 각각 transferflag='Y' 를 set 하고, 실패한 것에서 바로 ketbomecoheader, ketbomecocomponent 에 각각
			                     * transferflag='2' 를 set 하고, 아직 나머지가 있더라도 멈춘다.
			                     */
					    break;

					}

				    } else {
					Kogger.info(getClass(), "지정된 값이 없는 PartType 입니다.");

					if (reSult) {

					    Kogger.info(getClass(), "@@@@@@@@@@@@ Set Flag Y means Success ERP @@@@@@@@@@@ ");
					    Kogger.info(getClass(), "@@@@ econum : " + ECO_NUMBER);
					    Kogger.info(getClass(), "@@@@ itemCd : " + itemCd);
					    Kogger.info(getClass(), "@@@@ Y ");

					    bomEcoInfoInterfaceQry.setFalseFlag(ECO_NUMBER, itemCd, "Y", conn);

					}

				    }

				}

			    } catch (Exception e) {

				KogDbUtil.log("ECO 초도 BOM ECO Header 변경 에러 - with Erp I/F", "Fail", startEcoOid, e, null, null);
				throw e;
			    }

			    // Usage Link 처리, BOMHeader Approved 처리
			    if (reSult) {

				Kogger.info(getClass(), "@@@@@@@@@@@@ Usage Link Process ERP @@@@@@@@@@@ ");
				Kogger.info(getClass(), "@@@@ ecoLongValue : " + ecoLongValue);

				WFBomEcoPartUsageQry we = new WFBomEcoPartUsageQry();
				reSult = we.getPartUsageResult(ecoLongValue);

				if (!reSult) {
				    KogDbUtil.log("ECO 설변 BOM UsageLink, BaseLine 에러 - with Erp I/F", "Fail", startEcoOid, (Throwable) null, null, null);
				}
			    }

			}

			// Part 상태를 Approved로 set 한다.
			if (reSult) {

			    // 고객요청인지 아닌지
			    boolean isCustomerRequest = false;
			    if (wtChageOrder2 instanceof KETProdChangeOrder) {
				KETProdChangeOrder eco = (KETProdChangeOrder) wtChageOrder2;
				String changeReason = StringUtils.stripToEmpty(eco.getChangeReason());
				String[] changeReasons = StringUtils.splitPreserveAllTokens(changeReason, "|");
				int changeReasonsLength = (changeReasons != null) ? changeReasons.length : 0;
				for (int k = 0; k < changeReasonsLength; k++) {
				    if (changeReasons[k] != null && changeReasons[k].equalsIgnoreCase("REASON_1")) {
					isCustomerRequest = true;
					break;
				    }
				}
			    } else if (wtChageOrder2 instanceof KETMoldChangeOrder) {
				KETMoldChangeOrder eco = (KETMoldChangeOrder) wtChageOrder2;
				String changeReason = StringUtils.stripToEmpty(eco.getChangeReason());
				String[] changeReasons = StringUtils.splitPreserveAllTokens(changeReason, "|");
				int changeReasonsLength = (changeReasons != null) ? changeReasons.length : 0;
				for (int k = 0; k < changeReasonsLength; k++) {
				    if (changeReasons[k] != null && changeReasons[k].equalsIgnoreCase("REASON_1")) {
					isCustomerRequest = true;
					break;
				    }
				}
			    }

			    try {

				// 부품, BOM 모두 성공하였을 경우에만 부품 상태를 'APPROVED'로 변경한다.
				int partListSize = (partList != null) ? partList.size() : 0;
				for (int j = 0; j < partListSize; j++) {
				    WTPart wtPart = partList.get(j);
				    if (wtPart != null) {
					Kogger.info(getClass(), "@@@@@@@@@@@@ WTPart change APPROVED ERP @@@@@@@@@@@ ");
					Kogger.info(getClass(), "@@@@ wtPart : " + wtPart.getNumber());
					Kogger.info(getClass(), "@@@@ oid : " + CommonUtil.getOIDString(wtPart));

					/*
			                 * Hashtable partInfo = ketBOMQueryBean.getPartInfo(wtPart.getNumber()); wtPart = (WTPart)
			                 * partInfo.get("WTPart");
			                 * 
			                 * Kogger.info(getClass(), "@@@@ 최신 oid : " + CommonUtil.getOIDString(wtPart));
			                 */

					/*
			                 * 아래 내용을 부품 APPROVED 때리기 전에 고객요쳥일 경우에 한 번 콜 해주세요.
			                 */
					// ECO 부품상태 Released 되는 시점에 - 고객 Rev 올려줌. 고객 요청 속성 체크일 때만
					if (isCustomerRequest) {
					    wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
					    wtPart = PartBaseHelper.service.checkReviseCustomerVersion(wtPart);
					}

					wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
					PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpEoNo, ECO_NUMBER);

					PersistenceServerHelper.update(wtPart);
					wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);

					partDieInfoHandler.updatePartDieInfo(wtPart);

					LifeCycleServerHelper.service.setState((LifeCycleManaged) wtPart, State.toState("APPROVED"));
				    }

				}

			    } catch (Exception e) {

				KogDbUtil.log("ECO 설변 부품 상태 변경 에러 - with Erp I/F", "Fail", startEcoOid, e, null, null);
				throw e;
			    }

			    try {

				// 도면처리
				// WTChangeOrder2 eco = prodEcoBeans.getEcoByNo(ECO_NUMBER);
				String ecoOid = (isProd) ? "e3ps.ecm.entity.KETProdChangeOrder:" + ecoLongValue : "e3ps.ecm.entity.KETMoldChangeOrder:" + ecoLongValue;
				WTChangeOrder2 eco = (WTChangeOrder2) CommonUtil.getObject(ecoOid);
				Kogger.info(getClass(), "@@@@@@@@@@@@ eco is " + eco);

				Object[] eca = EcmUtil.getChangeActivities(eco);
				if (eca != null) {
				    Kogger.info(getClass(), "@@@@@@@@@@@@ eca.length is " + eca.length);

				    for (int cnt = 0; cnt < eca.length; cnt++) {

					Object object = (Object) eca[cnt];
					if (object instanceof KETProdChangeActivity) {
					    KETProdChangeActivity prodCA = (KETProdChangeActivity) object;
					    if (!prodCA.getActivityType().equals("3") && !prodCA.getActivityType().equals("4")) {

						Kogger.info(getClass(), "@@@@@@@@@@@@ prodCA is " + prodCA);

						Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromProdECA(prodCA);
						Kogger.info(getClass(), "@@@@@@@@@@@@ epmVec is " + epmVec.size());

						for (int i = 0; i < epmVec.size(); i++) {
						    EPMDocument eDoc = epmVec.elementAt(i);
						    Kogger.info(getClass(), "@@@@@@@@@@@@ eDoc is " + eDoc + " " + eDoc.getNumber() + " " + eDoc.getVersionIdentifier().getValue());

						    EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(eDoc);
						    Kogger.info(getClass(), "@@@@@@@@@@@@ epmDoc is " + epmDoc + " " + epmDoc.getNumber() + " " + epmDoc.getVersionIdentifier().getValue());

						    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("APPROVED"));
						}

					    }

					} else {
					    KETMoldChangeActivity moldCA = (KETMoldChangeActivity) object;
					    if (!moldCA.getActivityType().equals("4")) {

						Kogger.info(getClass(), "@@@@@@@@@@@@ moldCA is " + moldCA);

						Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromMoldECA(moldCA);
						Kogger.info(getClass(), "@@@@@@@@@@@@ epmVec is " + epmVec.size());

						for (int i = 0; i < epmVec.size(); i++) {
						    EPMDocument eDoc = epmVec.elementAt(i);
						    Kogger.info(getClass(), "@@@@@@@@@@@@ eDoc is " + eDoc + " " + eDoc.getNumber() + " " + eDoc.getVersionIdentifier().getValue());

						    EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(eDoc);
						    Kogger.info(getClass(), "@@@@@@@@@@@@ epmDoc is " + epmDoc + " " + epmDoc.getNumber() + " " + epmDoc.getVersionIdentifier().getValue());

						    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("APPROVED"));
						}

					    }

					}

				    }
				}

			    } catch (Exception e) {

				KogDbUtil.log("ECO 설변 도면 상태 변경 에러 - with Erp I/F", "Fail", startEcoOid, e, null, null);
				throw e;
			    }
			}

		    }

		}

	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		reSult = false;
	    }

	    // when All case Success
	    // 제거하여야 한다. SAP I/F 가 실패하여도 결재승인은 나고 차후 '재전송'으로 다시 시도한다.
	    if (reSult) {
		transaction.commit();
		transaction = null;
	    }

	} catch (Exception e) {
	    reSult = false;
	    Kogger.error(getClass(), e);
	} finally {
	    if (transaction != null) {
		transaction.rollback();
		transaction = null;

		reSult = false;
	    }

	}

	ret[0] = reSult;
	ret[1] = isPartErpInterFaceFail;
	return ret;
    }

    @Override
    public Boolean createPartUsageLink(Hashtable ht, WTPart parentPart, WTPart childPart, WTPartMaster childPartMaster, Integer chk, Connection conn) throws WTException {
	boolean reSult = false;
	Statement stmt = null;

	try {
	    Long oids = CommonUtil.getOIDLongValue(childPart);
	    String oid2 = String.valueOf(oids);

	    QueryResult qr = PersistenceHelper.manager.find(KETPartUsageLink.class, parentPart, KETPartUsageLink.USED_BY_ROLE, childPartMaster);

	    if (qr.size() == 0) {

		KETPartUsageLink bomMaster = KETPartUsageLink.newKETPartUsageLink(parentPart, childPartMaster);
		if (chk == 1) // Prod
		{
		    bomMaster.setOrgCode("000");
		    bomMaster.setParentItemCode((String) ht.get("MATNR"));
		    bomMaster.setChildItemCode((String) ht.get("IDNRK"));
		    bomMaster.setChildItemVersion((String) ht.get("CHILDV"));
		    bomMaster.setBomVersion((String) ht.get("NBOMV"));
		    bomMaster.setItemSeq(Integer.parseInt((String) ht.get("POSNR")));
		    bomMaster.setQuantity((String) ht.get("MENGE")); // quantity
		    // bomMaster.setQuantity( (String)ht.get("ERFMG") ); // erp_quantity = header_boxQuantity x quantity
		    bomMaster.setBoxQuantity((String) ht.get("BMENG"));
		    bomMaster.setUnit("KET_" + (String) ht.get("MEINS"));
		    bomMaster.setStartDate((String) ht.get("DATUV"));
		    bomMaster.setTraceCode(TraceCode.UNTRACED);
		    bomMaster.setVersionItemCode(checkNVL(oid2));

		    if (ht.containsKey("ICT"))
			bomMaster.setIct((String) ht.get("ICT"));
		    if (ht.containsKey("POSTP"))
			bomMaster.setIct((String) ht.get("POSTP"));

		    bomMaster.setRefTop((String) ht.get("REFTOP"));
		    bomMaster.setRefBottom((String) ht.get("REFBOTTOM"));
		    if (ht.containsKey("ECONO"))
			bomMaster.setAttribute10((String) ht.get("ECONO"));

		} else { // Mold
		    bomMaster.setOrgCode("000");
		    bomMaster.setParentItemCode((String) ht.get("MATNR"));
		    bomMaster.setChildItemCode((String) ht.get("IDNRK"));
		    bomMaster.setChildItemVersion((String) ht.get("CHILDV"));
		    bomMaster.setBomVersion((String) ht.get("NBOMV"));
		    bomMaster.setItemSeq(Integer.parseInt((String) ht.get("ODNRK")));
		    bomMaster.setQuantity((String) ht.get("MENGE"));
		    bomMaster.setBoxQuantity("1");
		    bomMaster.setUnit("KET_" + (String) ht.get("MEINS"));
		    bomMaster.setStartDate((String) ht.get("DATUV"));
		    bomMaster.setMaterial((String) ht.get("ATWRT"));
		    bomMaster.setHardnessFrom((String) ht.get("HARDN"));
		    bomMaster.setHardnessTo((String) ht.get("HARDT"));
		    bomMaster.setDesignDate((String) ht.get("DATUV"));
		    bomMaster.setTraceCode(TraceCode.UNTRACED);
		    bomMaster.setVersionItemCode(checkNVL(oid2));
		    if (ht.containsKey("ECONO"))
			bomMaster.setAttribute10((String) ht.get("ECONO"));
		}

		// 이미 만들어진 모자관계가 있을 경우 Skip한다. 단 Exception 메세지는 print 한다.
		try {
		    wt.fc.PersistenceServerHelper.manager.insert(bomMaster);

		} catch (wt.pom.UniquenessException une) {
		    Kogger.info(getClass(), "parentPart is " + parentPart.getNumber() + ", " + parentPart.toString());
		    Kogger.info(getClass(), "childPart is " + childPart.getNumber() + ", " + childPart.toString());
		    Kogger.info(getClass(), "childPartMaster is " + childPartMaster.getNumber() + ", " + childPartMaster.toString());

		    Kogger.error(getClass(), une);
		}

	    }

	    StringBuffer queryBuffer = new StringBuffer();
	    queryBuffer.append("UPDATE WTPart SET " + PartSpecEnum.SpBOMFlag.getColumnName() + "='OLD'	\n");
	    queryBuffer.append("	WHERE 1=1 								\n");
	    queryBuffer.append("	AND ida2a2 IN ( SELECT p.ida2a2 					\n");
	    queryBuffer.append("	                            FROM WTPart p, WTPartMaster m  		\n");
	    queryBuffer.append("	                           WHERE ( m.wtpartnumber = '" + (String) ht.get("MATNR") + "'	\n");
	    queryBuffer.append("	                              OR     m.wtpartnumber = '" + (String) ht.get("IDNRK") + "'  )	\n");
	    queryBuffer.append("	                             AND    m.ida2a2 = p.ida3masterreference ) 		\n");

	    // conn.setAutoCommit(false);
	    stmt = conn.createStatement();
	    stmt.executeUpdate(queryBuffer.toString());

	    reSult = true;
	} catch (Exception e) {
	    reSult = false;
	    Kogger.error(getClass(), e);
	} finally {
	    if (stmt != null) {
		try {
		    stmt.close();
		} catch (SQLException se) {
		    Kogger.error(getClass(), se);
		}
	    }

	}
	return reSult;
    }

    @Override
    public Boolean updatePartUsageLink(Hashtable ht, WTPart parentPart, WTPart childPart, WTPartMaster childPartMaster, Integer chk, Connection conn) throws WTException {
	boolean reSult = false;
	Statement stmt = null;
	KETPartUsageLink bomMaster = null;

	try {
	    Long oids = CommonUtil.getOIDLongValue(childPart);
	    String oid2 = String.valueOf(oids);

	    QueryResult qr = PersistenceHelper.manager.find(KETPartUsageLink.class, parentPart, KETPartUsageLink.USED_BY_ROLE, childPartMaster);

	    if (qr.hasMoreElements()) {
		bomMaster = (KETPartUsageLink) qr.nextElement();

		if (chk == 1) // Prod
		{
		    bomMaster.setOrgCode("000");
		    bomMaster.setParentItemCode((String) ht.get("MATNR"));
		    bomMaster.setChildItemCode((String) ht.get("IDNRK"));
		    bomMaster.setChildItemVersion((String) ht.get("CHILDV"));
		    bomMaster.setBomVersion((String) ht.get("NBOMV"));
		    bomMaster.setItemSeq(Integer.parseInt((String) ht.get("POSNR")));
		    bomMaster.setQuantity((String) ht.get("MENGE")); // quantity
		    // bomMaster.setQuantity( (String)ht.get("ERFMG") ); // erp_quantity = header_boxQuantity x quantity
		    bomMaster.setBoxQuantity((String) ht.get("BMENG"));
		    bomMaster.setUnit("KET_" + (String) ht.get("MEINS"));
		    bomMaster.setStartDate((String) ht.get("DATUV"));
		    bomMaster.setTraceCode(TraceCode.UNTRACED);
		    bomMaster.setVersionItemCode(checkNVL(oid2));

		    if (ht.containsKey("ICT"))
			bomMaster.setIct((String) ht.get("ICT"));
		    if (ht.containsKey("POSTP"))
			bomMaster.setIct((String) ht.get("POSTP"));

		    bomMaster.setRefTop((String) ht.get("REFTOP"));
		    bomMaster.setRefBottom((String) ht.get("REFBOTTOM"));
		    if (ht.containsKey("ECONO"))
			bomMaster.setAttribute10((String) ht.get("ECONO"));

		} else { // Mold
		    bomMaster.setOrgCode("000");
		    bomMaster.setParentItemCode((String) ht.get("MATNR"));
		    bomMaster.setChildItemCode((String) ht.get("IDNRK"));
		    bomMaster.setChildItemVersion((String) ht.get("CHILDV"));
		    bomMaster.setBomVersion((String) ht.get("NBOMV"));
		    bomMaster.setItemSeq(Integer.parseInt((String) ht.get("ODNRK")));
		    bomMaster.setQuantity((String) ht.get("MENGE"));
		    bomMaster.setBoxQuantity("1");
		    bomMaster.setUnit("KET_" + (String) ht.get("MEINS"));
		    bomMaster.setStartDate((String) ht.get("DATUV"));
		    bomMaster.setMaterial((String) ht.get("ATWRT"));
		    bomMaster.setHardnessFrom((String) ht.get("HARDN"));
		    bomMaster.setHardnessTo((String) ht.get("HARDT"));
		    bomMaster.setDesignDate((String) ht.get("DATUV"));
		    bomMaster.setTraceCode(TraceCode.UNTRACED);
		    bomMaster.setVersionItemCode(checkNVL(oid2));
		    if (ht.containsKey("ECONO"))
			bomMaster.setAttribute10((String) ht.get("ECONO"));
		}
		wt.fc.PersistenceServerHelper.manager.update(bomMaster);
	    }

	    StringBuffer queryBuffer = new StringBuffer();
	    queryBuffer.append("UPDATE WTPart SET " + PartSpecEnum.SpBOMFlag.getColumnName() + "='OLD'	\n");
	    queryBuffer.append("	WHERE 1=1 								\n");
	    queryBuffer.append("	AND ida2a2 IN ( SELECT p.ida2a2 					\n");
	    queryBuffer.append("	                            FROM WTPart p, WTPartMaster m  		\n");
	    queryBuffer.append("	                           WHERE ( m.wtpartnumber = '" + (String) ht.get("MATNR") + "'	\n");
	    queryBuffer.append("	                              OR     m.wtpartnumber = '" + (String) ht.get("IDNRK") + "'  )	\n");
	    queryBuffer.append("	                             AND    m.ida2a2 = p.ida3masterreference ) 		\n");

	    // conn.setAutoCommit(false);
	    stmt = conn.createStatement();
	    stmt.executeUpdate(queryBuffer.toString());

	    reSult = true;
	} catch (Exception e) {
	    reSult = false;
	    Kogger.error(getClass(), e);
	} finally {
	    if (stmt != null) {
		try {
		    stmt.close();
		} catch (SQLException se) {
		    Kogger.error(getClass(), se);
		}
	    }
	}
	return reSult;
    }

    @Override
    public Boolean removePartUsageLink(Hashtable ht, WTPart parentPart, WTPart childPart, WTPartMaster childPartMaster, Integer chk, Connection conn) throws WTException {

	boolean flag = false;
	KETPartUsageLink bomMaster = null;
	Transaction transaction = new Transaction();
	try {
	    transaction.start();

	    QueryResult qr = PersistenceHelper.manager.find(KETPartUsageLink.class, parentPart, KETPartUsageLink.USED_BY_ROLE, childPartMaster);

	    if (qr.hasMoreElements()) {
		bomMaster = (KETPartUsageLink) qr.nextElement();

		wt.fc.PersistenceServerHelper.manager.remove(bomMaster);
	    }

	    transaction.commit();
	    transaction = null;

	    flag = true;
	} catch (Exception e) {
	    flag = false;
	    Kogger.error(getClass(), e);
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}

	return flag;
    }

    @Override
    public Boolean setTransKetPartUsageLinkEco(String bomOid) throws WTException {
	boolean reSult = false;
	WFBomEcoPartUsageQry wp = new WFBomEcoPartUsageQry();
	if (wp.getPartUsageResult(bomOid)) {
	    reSult = true;
	}

	return reSult;
    }

    @Override
    public Boolean setTransKetPartUsageLink(String bomOid) throws WTException {
	boolean reSult = false;
	WFBomPartUsageQry wp = new WFBomPartUsageQry();
	if (wp.getPartUsageResult(bomOid)) {
	    reSult = true;
	}

	return reSult;
    }

    private String checkNVL(String str) {
	if (str == null || str.equals("null")) {
	    return "";
	} else {
	    return str;
	}
    }

    private WTPart searchItem(String itemCode) throws WTException {
	WTPart item = WTPart.newWTPart();

	try {
	    QuerySpec qs = new QuerySpec(WTPart.class);
	    qs.appendWhere(new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.EQUAL, itemCode), new int[] { 0 });

	    Object obj = new LatestConfigSpec();
	    qs = ((ConfigSpec) (obj)).appendSearchCriteria(qs);
	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);
	    qr = ((ConfigSpec) (obj)).process(qr);

	    if (qr.hasMoreElements()) {
		item = (WTPart) qr.nextElement();
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	}
	return item;
    }
}
