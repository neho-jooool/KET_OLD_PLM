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

package e3ps.common.service;

import java.io.Serializable;
import java.util.ArrayList;

import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument; // Preserved unmodeled dependency
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTObject;
import wt.query.QuerySpec; // Preserved unmodeled dependency
import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.vc.Mastered; // Preserved unmodeled dependency
import e3ps.common.jdf.config.Config; // Preserved unmodeled dependency
import e3ps.common.jdf.config.ConfigImpl; // Preserved unmodeled dependency
import e3ps.common.query.SearchUtil; // Preserved unmodeled dependency
import e3ps.common.util.VersionUtil; // Preserved unmodeled dependency
import e3ps.dms.entity.KETProjectDocument; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETMoldChangeActivity; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETMoldChangeActivityLink; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETMoldChangeOrder; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETMoldECAEpmDocLink; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETProdChangeActivity; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETProdChangeActivityLink; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETProdChangeOrder; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETProdECAEpmDocLink; // Preserved unmodeled dependency
import e3ps.edm.beans.EDMPBOHelper; // Preserved unmodeled dependency
import e3ps.wfm.entity.KETWfmMultiApprovalRequest; // Preserved unmodeled dependency
import e3ps.wfm.entity.KETWfmMultiReqDocLink; // Preserved unmodeled dependency
import e3ps.wfm.entity.KETWfmMultiReqEpmLink; // Preserved unmodeled dependency
import e3ps.wfm.util.WorkflowSearchHelper; // Preserved unmodeled dependency
import ext.ket.sales.entity.KETSalesProject;
import ext.ket.shared.log.Kogger;

/**
 * <p>
 * Use the <code>newStandardKETCommonService</code> static factory method(s), not the <code>StandardKETCommonService</code> constructor, to construct instances of this class. Instances must be
 * constructed using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * @version 1.0
 **/

public class StandardKETCommonService extends StandardManager implements KETCommonService, Serializable {

    private static final String RESOURCE  = "e3ps.common.service.serviceResource";
    private static final String CLASSNAME = StandardKETCommonService.class.getName();

    Config		      conf      = ConfigImpl.getInstance();
    boolean		     VERBOSE   = conf.getBoolean("develop.verbose");

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
    protected void performStartupProcess() throws ManagerException {

	super.performStartupProcess();

    }

    /**
     * Default factory for the class.
     * 
     * @return StandardKETCommonService
     * @exception wt.util.WTException
     **/
    public static StandardKETCommonService newStandardKETCommonService() throws WTException {

	StandardKETCommonService instance = new StandardKETCommonService();
	instance.initialize();
	return instance;
    }

    @Override
    public ArrayList<RevisionControlled> getVersionHistory(String oidStr) throws WTException {
	ArrayList<RevisionControlled> returnList = new ArrayList<RevisionControlled>();

	ReferenceFactory rf = new ReferenceFactory();
	Persistable obj = rf.getReference(oidStr).getObject();

	if (obj instanceof RevisionControlled) {
	    QueryResult queryResult = null;
	    
	    if (obj instanceof KETSalesProject) {
		queryResult = wt.vc.VersionControlHelper.service.allIterationsOf((((RevisionControlled) obj).getMaster()));
		
            }else{
        	queryResult = wt.vc.VersionControlHelper.service.allVersionsOf(((RevisionControlled) obj).getMaster());
            }
	    

	    while (queryResult.hasMoreElements()) {
		returnList.add((RevisionControlled) queryResult.nextElement());
	    }
	}

	return returnList;
    }

    @Override
    public WTObject getPBO(WTObject pbo) throws WTException {
	WTObject returnObj = null;
	Kogger.debug(getClass(), "pbo======" + pbo);
	if (pbo instanceof KETProjectDocument) {
	    KETProjectDocument document = (KETProjectDocument) pbo;
	    QueryResult qr = PersistenceHelper.manager.navigate(document, "request", KETWfmMultiReqDocLink.class);
	    if (qr.size() > 0) {
		KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest) qr.nextElement();
		returnObj = multiReq;
	    } else {
		if (WorkflowSearchHelper.manager.getMaster(pbo) != null) {
		    returnObj = pbo;
		} else {
		    returnObj = null;
		}
	    }
	} else if (pbo instanceof EPMDocument) // ???
	{
	    if (VERBOSE) {
		Kogger.debug(getClass(), "======> StandardKETCommonService.java :: getPBO() :: EPMDocument");
	    }

	    if (WorkflowSearchHelper.manager.getMaster(pbo) != null) // ?????? ?????? ??? ???
	    {
		if (VERBOSE) {
		    Kogger.debug(getClass(), "======> StandardKETCommonService.java :: getPBO() :: Standalone Approval");
		}

		returnObj = pbo;
	    } else {

		EPMDocument epmDoc = (EPMDocument) pbo;
		QueryResult multiReqQR = PersistenceHelper.manager.navigate(epmDoc, "request", KETWfmMultiReqEpmLink.class);

		if (multiReqQR.size() > 0) // ?????? ??? ???
		{
		    if (VERBOSE) {
			Kogger.debug(getClass(), "======> StandardKETCommonService.java :: getPBO() :: Multiple Approval");
		    }

		    KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest) multiReqQR.nextElement();
		    returnObj = multiReq;

		} else {

		    Persistable ecoObj = getRelatedEco(epmDoc);

		    if (ecoObj != null) // ?????? ??? ??? ???
		    {
			if (VERBOSE) {
			    Kogger.debug(getClass(), "======> StandardKETCommonService.java :: getPBO() :: ECO Approval");
			}

			if (ecoObj instanceof KETProdChangeOrder) {
			    returnObj = (KETProdChangeOrder) ecoObj;
			} else if (ecoObj instanceof KETMoldChangeOrder) {
			    returnObj = (KETMoldChangeOrder) ecoObj;
			}
		    } else {
			if (VERBOSE) {
			    Kogger.debug(getClass(), "======> StandardKETCommonService.java :: getPBO() :: ETC & EPMDoc Link Approval");
			}

			EPMDocument resultEPM = EDMPBOHelper.getPrimaryBusinessObject(epmDoc);

			if (resultEPM != null) {
			    // resultEPM ???????? ????????????????????? ??? ?????? ???????? ??????????
			    returnObj = getPBO(resultEPM);
			} else {
			    if (VERBOSE) {
				Kogger.debug(getClass(), "======> StandardKETCommonService.java :: getPBO() :: EPMDoc Approval Info. doesn't exist");
			    }

			    returnObj = null;
			}
		    }
		}
	    }

	    if (VERBOSE) {
		Kogger.debug(getClass(), "======> StandardKETCommonService.java :: getPBO() :: PBO Object : " + returnObj);
	    }
	} else {
	    if (WorkflowSearchHelper.manager.getMaster(pbo) != null) {
		returnObj = pbo;
	    } else {
		returnObj = null;
	    }
	}

	return returnObj;
    }

    public Persistable getRelatedEco(EPMDocument afterEpmDoc) {
	Persistable ecoObj = null;
	QueryResult qr = null;
	QueryResult subQr = null;

	KETProdChangeActivity prodECA = null;
	KETMoldChangeActivity moldECA = null;

	EPMDocument preEpmDoc = null;

	KETProdECAEpmDocLink prodEpmDocLink = null;
	KETMoldECAEpmDocLink moldEpmDocLink = null;

	try {
	    Mastered master = afterEpmDoc.getMaster();
	    String version = VersionUtil.getMajorVersion(afterEpmDoc);

	    QuerySpec spec = new QuerySpec(KETProdECAEpmDocLink.class);
	    SearchUtil.appendEQUAL(spec, KETProdECAEpmDocLink.class, KETProdECAEpmDocLink.AFTER_VERSION, version, 0);
	    QueryResult result = PersistenceHelper.manager.find(spec);

	    while (result.hasMoreElements()) {
		prodEpmDocLink = (KETProdECAEpmDocLink) result.nextElement();

		if (prodEpmDocLink.getEpmDoc().getMaster().equals(master)) {
		    preEpmDoc = prodEpmDocLink.getEpmDoc();
		}
	    }

	    if (preEpmDoc == null) {
		spec = new QuerySpec(KETMoldECAEpmDocLink.class);
		SearchUtil.appendEQUAL(spec, KETMoldECAEpmDocLink.class, KETMoldECAEpmDocLink.AFTER_VERSION, version, 0);
		result = PersistenceHelper.manager.find(spec);

		while (result.hasMoreElements()) {
		    moldEpmDocLink = (KETMoldECAEpmDocLink) result.nextElement();

		    if (moldEpmDocLink.getEpmDoc().getMaster().equals(master)) {
			preEpmDoc = moldEpmDocLink.getEpmDoc();
		    }
		}
	    }

	    if (preEpmDoc != null) {
		qr = PersistenceHelper.manager.navigate(preEpmDoc, "prodECA", KETProdECAEpmDocLink.class);

		if (qr.hasMoreElements()) // ??? ECO
		{
		    prodECA = (KETProdChangeActivity) qr.nextElement();
		    subQr = PersistenceHelper.manager.navigate(prodECA, "prodECO", KETProdChangeActivityLink.class);

		    if (subQr.hasMoreElements()) {
			ecoObj = (Persistable) subQr.nextElement();
		    }
		} else // ??? ECO
		{
		    qr = PersistenceHelper.manager.navigate(preEpmDoc, "moldECA", KETMoldECAEpmDocLink.class);

		    if (qr.hasMoreElements()) {
			moldECA = (KETMoldChangeActivity) qr.nextElement();
			subQr = PersistenceHelper.manager.navigate(moldECA, "moldECO", KETMoldChangeActivityLink.class);

			if (subQr.hasMoreElements()) {
			    ecoObj = (Persistable) subQr.nextElement();
			}
		    }
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return ecoObj;
    }
}
