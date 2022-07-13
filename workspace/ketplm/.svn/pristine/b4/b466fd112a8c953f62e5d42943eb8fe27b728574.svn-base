package e3ps.edm.beans;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import org.drools.core.util.StringUtils;

import wt.clients.folder.FolderTaskLogic;
import wt.clients.vc.CheckInOutTaskLogic;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.epm.AuthoringAppVersionHelper;
import wt.epm.EPMApplicationType;
import wt.epm.EPMAuthoringAppType;
import wt.epm.EPMAuthoringAppVersion;
import wt.epm.EPMContextHelper;
import wt.epm.EPMDocument;
import wt.epm.EPMDocumentHelper;
import wt.epm.EPMDocumentMaster;
import wt.epm.EPMDocumentMasterIdentity;
import wt.epm.EPMDocumentType;
import wt.fc.Identified;
import wt.fc.IdentityHelper;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.collections.WTHashSet;
import wt.fc.collections.WTSet;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderException;
import wt.folder.FolderHelper;
import wt.inf.container.WTContained;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.LifeCycleTemplateReference;
import wt.lifecycle.State;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pom.PersistenceException;
import wt.pom.Transaction;
import wt.projmgmt.admin.Project2;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.sandbox.SandboxHelper;
import wt.series.HarvardSeries;
import wt.series.SeriesIncrementInvalidException;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTInvalidParameterException;
import wt.util.WTPropertyVetoException;
import wt.vc.Iterated;
import wt.vc.VersionControlException;
import wt.vc.VersionControlHelper;
import wt.vc.VersionIdentifier;
import wt.vc.Versioned;
import wt.vc.config.LatestConfigSpec;
import wt.vc.wip.CheckoutLink;
import wt.vc.wip.NonLatestCheckoutException;
import wt.vc.wip.WorkInProgressException;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;

import com.ptc.windchill.cadx.common.EPMDocumentUtilities;

import e3ps.common.code.NumberCode;
import e3ps.common.content.uploader.WBFile;
import e3ps.common.folder.FolderUtil;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.VersionUtil;
import e3ps.ecm.entity.KETMoldECAEpmDocLink;
import e3ps.ecm.entity.KETProdECAEpmDocLink;
import e3ps.edm.DrawingToModelReferenceLink;
import e3ps.edm.EDMUserData;
import e3ps.edm.EPMDocProjectLink;
import e3ps.edm.EPMDocTypeCodeLink;
import e3ps.edm.EPMLink;
import e3ps.edm.ModelReferenceLink;
import e3ps.edm.PartToEPMLink;
import e3ps.edm.util.EDMAttributeUtil;
import e3ps.edm.util.EDMAttributes;
import e3ps.edm.util.EDMEnumeratedTypeUtil;
import e3ps.edm.util.EDMProperties;
import e3ps.edm.util.EDMUtil;
import e3ps.edm.util.VersionHelper;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.project.E3PSProject;
import e3ps.project.ProjectMaster;
import e3ps.project.ProjectOutput;
import e3ps.project.beans.ProjectOutputHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.base.service.internal.associate.PartEpmRelation;
import ext.ket.shared.log.Kogger;

public class EDMServiceHelper implements wt.method.RemoteAccess, java.io.Serializable {
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    public static boolean isDRM = false;

    static {
	Config conf = ConfigImpl.getInstance();
	isDRM = conf.getBoolean("DRM");
    }

    public static void deleteObjects(String[] oids) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { String[].class };
	    Object args[] = new Object[] { oids };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("deleteObjects", EDMServiceHelper.class.getName(), null, argTypes,
		        args);
	    } catch (RemoteException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    }

	    return;
	}

	Transaction trx = new Transaction();

	try {
	    if ((oids == null) || (oids.length == 0)) {
		return;
	    }

	    trx.start();

	    ReferenceFactory rf = new ReferenceFactory();
	    Object obj = null;

	    for (int i = 0; i < oids.length; i++) {
		obj = rf.getReference(oids[i]).getObject();
		WTContainer wtContainer = null;

		if (obj instanceof WTContained) {
		    if (((WTContained) obj).getContainer() instanceof Project2) {
			wtContainer = ((WTContained) obj).getContainer();
		    }
		}

		boolean bool2 = wtContainer != null;

		WTHashSet wtSet = new WTHashSet(1);
		wtSet.add(obj);

		SessionContext sessioncontext = SessionContext.newContext();
		try {
		    SessionHelper.manager.setAdministrator();

		    SandboxHelper.service.removeObjects(wtSet, bool2);
		} catch (Exception e) {
		    Kogger.error(EDMPBOHelper.class, e);
		} finally {
		    SessionContext.setContext(sessioncontext);
		}

	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    trx.rollback();

	    Kogger.error(EDMPBOHelper.class, e);
	    throw new WTException(e);
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
    }

    public static void batchRevise(String[] oids) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { String[].class };
	    Object args[] = new Object[] { oids };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("batchRevise", EDMServiceHelper.class.getName(), null, argTypes,
		        args);
	    } catch (RemoteException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    }

	    return;
	}

	Transaction trx = new Transaction();

	try {
	    if ((oids == null) || (oids.length == 0)) {
		return;
	    }

	    trx.start();

	    EDMProperties edmProperties = EDMProperties.getInstance();
	    ReferenceFactory rf = new ReferenceFactory();
	    Versioned versioned = null;
	    EPMDocument newVersionObj = null;

	    for (int i = 0; i < oids.length; i++) {
		if ((oids[i] == null) || (oids[i].trim().length() == 0)) {
		    throw new WTException("OID 값이 널이거나 공백 입니다.");
		}

		versioned = (Versioned) rf.getReference(oids[i]).getObject();

		if (!VersionHelper.isLatestRevision(versioned)) {
		    throw new WTException("최신버전이 아닙니다.");
		}

		if (versioned instanceof LifeCycleManaged) {
		    // String statekey = ((LifeCycleManaged)revisedObj).getState().getState().toString();
		    if (!edmProperties.isReleasedState(((LifeCycleManaged) versioned).getState().getState().toString())) {
			throw new WTException("미승인상태의 데이터가 있습니다.");
		    }
		}

		String location = null;
		String lifecycle = null;

		if (versioned instanceof EPMDocument) {
		    // location = FolderHelper.service.getFolder((FolderEntry) versioned).getLocation();
		    location = FolderHelper.service.getFolder((FolderEntry) versioned).getFolderPath();

		    Kogger.debug(EDMPBOHelper.class, "=======> EDMServiceHelper batchRevise() : location : " + location);

		    lifecycle = edmProperties.getEPMDefaultLC();
		}

		newVersionObj = (EPMDocument) VersionUtil.doRevise(versioned, null, lifecycle, location, null, null, null);

	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    trx.rollback();

	    Kogger.error(EDMPBOHelper.class, e);
	    throw new WTException(e);
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
    }

    public static EPMDocument doEpmRevise(EPMDocument epm, String location, String lifecycle, String state, String version)
	    throws WTException {
	EPMDocument newEPM = null;

	try {
	    if (!VersionHelper.isLatestRevision(epm)) {
		throw new WTException("최신버전이 아닙니다.");
	    }

	    EDMProperties edmProperties = EDMProperties.getInstance();

	    // if( (location == null) || (location.trim().length() == 0) ) { location = FolderHelper.service.getFolder((FolderEntry)
	    // epm).getLocation(); }

	    if ((location == null) || (location.trim().length() == 0)) {
		location = FolderHelper.service.getFolder((FolderEntry) epm).getFolderPath();
	    }

	    if ((lifecycle == null) || (lifecycle.trim().length() == 0)) {
		lifecycle = edmProperties.getEPMDefaultLC();
	    }

	    if ((state == null) || (state.trim().length() == 0)) {
		state = "";
	    }

	    if ((version == null) || (version.trim().length() == 0)) {
		version = "";
	    }

	    Kogger.debug(EDMPBOHelper.class, "=======> EDMServiceHelper doEpmRevise() : location : " + location);

	    newEPM = (EPMDocument) VersionUtil.doRevise(epm, version, lifecycle, location, state, null, null);
	} catch (WTException e) {
	    throw new WTException(e);
	} catch (Exception e) {
	    throw new WTException(e);
	}

	return (EPMDocument) PersistenceHelper.manager.refresh(newEPM);
    }

    public static EPMDocument saveEPMDocumentByECAD(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("saveEPMDocumentByECAD", EDMServiceHelper.class.getName(), null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    }

	    return (EPMDocument) obj;
	}

	Transaction trx = new Transaction();
	EPMDocument epm = null;

	try {
	    trx.start();

	    EDMProperties edmProperties = EDMProperties.getInstance();

	    String nowEcad = null;
	    String nowEcadState = "CREATE";
	    String oid = (map.get("oid") == null) ? "" : (String) map.get("oid");
	    EPMDocument epmPsa = null;
	    if (StringUtils.isEmpty(oid)) {

	    } else {

		epmPsa = (EPMDocument) CommonUtil.getObject(oid);
		if (epmPsa != null) {
		    if (edmProperties.getAuthoringAppTypeByEcadPcb().equals(epmPsa.getAuthoringApplication().toString())) {
			nowEcad = "PCB";
		    } else if (edmProperties.getAuthoringAppTypeByEcadSch().equals(epmPsa.getAuthoringApplication().toString())) {
			nowEcad = "SCH";
		    } else if (edmProperties.getAuthoringAppTypeByEcadAcad().equals(epmPsa.getAuthoringApplication().toString())) {
			nowEcad = "ACAD";
		    }

		    if ("0".equals(epmPsa.getVersionIdentifier().getValue())) {
			nowEcadState = "MODIFY";
		    } else {
			nowEcadState = "REVISE";
		    }
		}
	    }
	    String numberPCB = (map.get("number_pcb") == null) ? "" : (String) map.get("number_pcb");
	    String namePCB = (map.get("name_pcb") == null) ? "" : (String) map.get("name_pcb");

	    String numberSCH = (map.get("number_sch") == null) ? "" : (String) map.get("number_sch");
	    String nameSCH = (map.get("name_sch") == null) ? "" : (String) map.get("name_sch");

	    String numberDWG = (map.get("number_dwg") == null) ? "" : (String) map.get("number_dwg");
	    String nameDWG = (map.get("name_dwg") == null) ? "" : (String) map.get("name_dwg");

	    String oidPCB = (map.get("oid_pcb") == null) ? "" : (String) map.get("oid_pcb");
	    String oidSCH = (map.get("oid_sch") == null) ? "" : (String) map.get("oid_sch");
	    String oidDWG = (map.get("oid_dwg") == null) ? "" : (String) map.get("oid_dwg");

	    String gerberDelFile = (map.get("gerberDelFile") == null) ? "" : (String) map.get("gerberDelFile");

	    String cadNamePCB = "";
	    String cadNameSCH = "";
	    String cadNameDWG = "";

	    String appTypePCB = "";
	    String appTypeSCH = "";
	    String appTypeDWG = "";

	    // Vector filesByPCB = new Vector();
	    // Vector filesBySCH = new Vector();
	    // Vector filesByDWG = new Vector();
	    Object file_pcb_primary = null;
	    Object file_sch_primary = null;
	    Object file_dwg_primary = null;
	    Object file_gerber_primary = null;

	    // 파일 확장자 체크
	    Vector files = (Vector) map.get("files");

	    if (files != null) {
		for (int i = 0; i < files.size(); i++) {
		    WBFile file = (WBFile) files.get(i);

		    if ("file_pcb_primary".equals(file.getFieldName())) {
			cadNamePCB = file.getName();
			appTypePCB = edmProperties.getAuthoringAppTypeByEcadPcb();// "PADS";

			if (appTypePCB.length() == 0) {
			    appTypePCB = getEPMAuthoringAppType(cadNamePCB) == null ? "" : getEPMAuthoringAppType(cadNamePCB)
				    .getStringValue();
			}

			file_pcb_primary = file;
			// filesByPCB.add(file);
		    } else if ("file_sch_primary".equals(file.getFieldName())) {
			cadNameSCH = file.getName();
			appTypeSCH = edmProperties.getAuthoringAppTypeByEcadSch();// "PADS_SCH";

			if (appTypeSCH.length() == 0) {
			    appTypeSCH = getEPMAuthoringAppType(cadNameSCH) == null ? "" : getEPMAuthoringAppType(cadNameSCH)
				    .getStringValue();
			}

			file_sch_primary = file;
			// filesBySCH.add(file);
		    } else if ("file_dwg_primary".equals(file.getFieldName())) {
			cadNameDWG = file.getName();
			appTypeDWG = edmProperties.getAuthoringAppTypeByEcadAcad();// "ACAD";

			if (appTypeDWG.length() == 0) {
			    appTypeDWG = getEPMAuthoringAppType(cadNameDWG) == null ? "" : getEPMAuthoringAppType(cadNameDWG)
				    .getStringValue();
			}

			file_dwg_primary = file;
			// filesByDWG.add(file);
		    } else if ("file_gerber_primary".equals(file.getFieldName())) {
			file_gerber_primary = file;
			// filesByPCB.add(file);
		    }
		}
	    }

	    EPMDocument ecadPCB = null;
	    EPMDocument ecadSCH = null;
	    EPMDocument ecadDWG = null;

	    ReferenceFactory rf = new ReferenceFactory();

	    if (oidPCB.trim().length() > 0) {
		ecadPCB = (EPMDocument) rf.getReference(oidPCB).getObject();

		if (cadNamePCB.trim().length() == 0) {
		    cadNamePCB = ecadPCB.getCADName();
		}

		if (appTypePCB.trim().length() == 0) {
		    appTypePCB = ecadPCB.getAuthoringApplication().toString();
		}
	    }

	    if (oidSCH.trim().length() > 0) {
		ecadSCH = (EPMDocument) rf.getReference(oidSCH).getObject();

		if (cadNameSCH.trim().length() == 0) {
		    cadNameSCH = ecadSCH.getCADName();
		}

		if (appTypeSCH.trim().length() == 0) {
		    appTypeSCH = ecadSCH.getAuthoringApplication().toString();
		}
	    }

	    if (oidDWG.trim().length() > 0) {
		ecadDWG = (EPMDocument) rf.getReference(oidDWG).getObject();

		if (cadNameDWG.trim().length() == 0) {
		    cadNameDWG = ecadDWG.getCADName();
		}

		if (appTypeDWG.trim().length() == 0) {
		    appTypeDWG = ecadDWG.getAuthoringApplication().toString();
		}
	    }

	    // Version Sync
	    // PCB를 기준으로 Revision mapping...
	    // 2차 고도화 YJLEE(TKLEE) 수정함. - SCH, PCB, AUTOCAD 별개로 등록, 수정, 개정함.
	    // String revision = "";
	    //
	    // if( ecadPCB != null )
	    // {
	    // revision = VersionControlHelper.getVersionIdentifier(ecadPCB).getSeries().getValue();
	    // }

	    // 사업부 정보 처리....
	    NumberCode bizType = null;
	    String bizTypeOid = (map.get("businessType") == null) ? "" : (String) map.get("businessType");

	    if (bizTypeOid.trim().length() > 0) {
		try {
		    bizType = (NumberCode) rf.getReference(bizTypeOid).getObject();
		} catch (Exception e) {
		    bizType = null;
		}
	    }

	    // 프로젝트 정보 처리....
	    E3PSProject project = null;
	    String projectOid = (map.get("project") == null) ? "" : (String) map.get("project");

	    if (projectOid.trim().length() > 0) {
		project = (E3PSProject) rf.getReference(projectOid).getObject();
	    }

	    // 프로젝트 산출물인 경우 ...
	    ProjectOutput output = null;
	    String outputOid = (map.get("outputOid") == null) ? "" : (String) map.get("outputOid");

	    if (outputOid.trim().length() > 0) {
		output = (ProjectOutput) rf.getReference(outputOid).getObject();
	    }

	    if (cadNamePCB.trim().length() > 0 && ("PCB".equals(nowEcad) || "CREATE".equals(nowEcadState))) {
		HashMap map0 = (HashMap) map.clone();
		map0.put("number", numberPCB);
		map0.put("name", namePCB);
		map0.put("cadName", cadNamePCB);

		// map0.put("version", revision); // SCH, PCB, AUTOCAD 별개로 등록, 수정, 개정함.

		// map0.put("files", filesByPCB);
		if (file_pcb_primary != null) {
		    map0.put("primary", file_pcb_primary);
		}

		if (file_gerber_primary != null) {
		    Vector secondary = new Vector();
		    secondary.add(file_gerber_primary);
		    map0.put("secondary", secondary);
		}

		if (ecadPCB != null) {
		    map0.put("oid", PersistenceHelper.getObjectIdentifier(ecadPCB).getStringValue());
		}

		if (gerberDelFile.trim().length() > 0) {
		    String delFile[] = new String[1];
		    delFile[0] = gerberDelFile;
		    map0.put("delFile", delFile);
		}

		map0.put("EPMAuthoringAppType", appTypePCB);

		if (ecadPCB == null) {
		    ecadPCB = createEPMDocument(map0);
		} else {
		    ecadPCB = updateEPMDocument(map0);
		}

		updateTypeCodeLink(ecadPCB, bizType, true);
		updateProjectLink(ecadPCB, (project == null) ? null : project.getMaster(), true);

		if (output != null) {
		    ProjectOutputHelper.manager.registryProjectOutput(output, ecadPCB);
		}

		ecadPCB = setAssociation(ecadPCB, map0);
	    }

	    if (cadNameSCH.trim().length() > 0 && ("SCH".equals(nowEcad) || "CREATE".equals(nowEcadState))) {
		HashMap map0 = (HashMap) map.clone();
		map0.put("number", numberSCH);
		map0.put("name", nameSCH);
		map0.put("cadName", cadNameSCH);
		// map0.put("version", revision); // SCH, PCB, AUTOCAD 별개로 등록, 수정, 개정함.

		// map0.put("files", filesBySCH);
		if (file_sch_primary != null) {
		    map0.put("primary", file_sch_primary);
		}

		if (ecadSCH != null) {
		    map0.put("oid", PersistenceHelper.getObjectIdentifier(ecadSCH).getStringValue());
		}

		map0.put("EPMAuthoringAppType", appTypeSCH);

		if (ecadSCH == null) {
		    ecadSCH = createEPMDocument(map0);
		} else {
		    ecadSCH = updateEPMDocument(map0);
		}

		updateTypeCodeLink(ecadSCH, bizType, true);
		updateProjectLink(ecadSCH, (project == null) ? null : project.getMaster(), true);

		if (output != null) {
		    ProjectOutputHelper.manager.registryProjectOutput(output, ecadSCH);
		}

		ecadSCH = setAssociation(ecadSCH, map0);
	    }

	    if (cadNameDWG.trim().length() > 0 && ("ACAD".equals(nowEcad) || "CREATE".equals(nowEcadState))) {
		HashMap map0 = (HashMap) map.clone();
		map0.put("number", numberDWG);
		map0.put("name", nameDWG);
		map0.put("cadName", cadNameDWG);
		// map0.put("version", revision); // SCH, PCB, AUTOCAD 별개로 등록, 수정, 개정함.

		// map0.put("files", filesByDWG);
		if (file_dwg_primary != null) {
		    map0.put("primary", file_dwg_primary);
		}

		if (ecadDWG != null) {
		    map0.put("oid", PersistenceHelper.getObjectIdentifier(ecadDWG).getStringValue());
		}

		map0.put("EPMAuthoringAppType", appTypeDWG);

		if (ecadDWG == null) {
		    ecadDWG = createEPMDocument(map0);
		} else {
		    ecadDWG = updateEPMDocument(map0);
		}

		updateTypeCodeLink(ecadDWG, bizType, true);
		updateProjectLink(ecadDWG, (project == null) ? null : project.getMaster(), true);

		if (output != null) {
		    ProjectOutputHelper.manager.registryProjectOutput(output, ecadDWG);
		}

		ecadDWG = setAssociation(ecadDWG, map0);
	    }

	    trx.commit();
	    trx = null;

	    if ("PCB".equals(nowEcad)) {
		epm = ecadPCB;
	    } else if ("SCH".equals(nowEcad)) {
		epm = ecadSCH;
	    } else if ("ACAD".equals(nowEcad)) {
		epm = ecadDWG;
	    } else {

		if (cadNamePCB.trim().length() > 0) {
		    epm = ecadPCB;
		} else if (cadNameSCH.trim().length() > 0) {
		    epm = ecadSCH;
		} else if (cadNameDWG.trim().length() > 0) {
		    epm = ecadDWG;
		}
	    }
	} catch (Exception e) {
	    trx.rollback();

	    Kogger.error(EDMPBOHelper.class, e);
	    throw new WTException(e.getLocalizedMessage());
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}

	return epm;
    }

    public static EPMDocument saveEPMDocument(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("saveEPMDocument", EDMServiceHelper.class.getName(), null, argTypes,
		        args);
	    } catch (RemoteException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    }

	    return (EPMDocument) obj;
	}

	Transaction trx = new Transaction();
	EPMDocument epm = null;

	try {
	    trx.start();

	    EDMProperties edmProperties = EDMProperties.getInstance();
	    String oid = (map.get("oid") == null) ? "" : (String) map.get("oid");
	    String cadAppType = (map.get("cadAppType") == null) ? "" : (String) map.get("cadAppType");
	    String cadName = (map.get("cadName") == null) ? "" : (String) map.get("cadName");
	    String docType = (map.get("EPMDocumentType") == null) ? "" : (String) map.get("EPMDocumentType");
	    Object primary = null;
	    Vector secondary = new Vector();

	    // 파일 확장자 체크
	    Vector files = (Vector) map.get("files");

	    if (files != null) {
		for (int i = 0; i < files.size(); i++) {
		    WBFile file = (WBFile) files.get(i);

		    if ("primary".equals(file.getFieldName())) {
			primary = file;

			if (cadName.trim().length() == 0) {
			    cadName = file.getName();
			    map.put("cadName", cadName);
			}
		    } else {
			secondary.addElement(file);
		    }
		}
	    }

	    if (primary != null) {
		map.put("primary", primary);
	    }

	    if (secondary.size() > 0) {
		map.put("secondary", secondary);
	    }

	    if (edmProperties.isAuthoringAppTypeByExtension(cadAppType)) {
		map.put("EPMAuthoringAppType", getEPMAuthoringAppType(cadName).toString());
	    } else {
		map.put("EPMAuthoringAppType", cadAppType);
	    }

	    ReferenceFactory rf = new ReferenceFactory();

	    if (oid.trim().length() > 0) {
		epm = (EPMDocument) rf.getReference(oid).getObject();
	    }

	    if (epm == null) {
		epm = createEPMDocument(map);
	    } else {
		epm = updateEPMDocument(map);
	    }

	    // 사업부 정보 처리....
	    NumberCode bizType = null;
	    String bizTypeOid = (map.get("businessType") == null) ? "" : (String) map.get("businessType");

	    if (bizTypeOid.trim().length() > 0) {
		try {
		    bizType = (NumberCode) rf.getReference(bizTypeOid).getObject();
		} catch (Exception e) {
		    bizType = null;
		}
	    }

	    updateTypeCodeLink(epm, bizType, true);

	    // 프로젝트 정보 처리....
	    E3PSProject project = null;
	    String projectOid = (map.get("project") == null) ? "" : (String) map.get("project");

	    if (projectOid.trim().length() > 0) {
		project = (E3PSProject) rf.getReference(projectOid).getObject();
	    }

	    updateProjectLink(epm, (project == null) ? null : project.getMaster(), true);

	    // 프로젝트 산출물인 경우 ...
	    ProjectOutput output = null;
	    String outputOid = (map.get("outputOid") == null) ? "" : (String) map.get("outputOid");

	    if (outputOid.trim().length() > 0) {
		output = (ProjectOutput) rf.getReference(outputOid).getObject();
	    }

	    if (output != null) {
		ProjectOutputHelper.manager.registryProjectOutput(output, epm);
	    }

	    /*
	     * 도면/부품/모델 연관 ...
	     */
	    epm = setAssociation(epm, map);

	    String category = (map.get("category") == null) ? "" : (String) map.get("category");
	    /*
	     * 정의된 IBA 속성/사업부/프로젝트 정보 동기화 시킴.
	     */
	    if (edmProperties.isRefModel(category)) {
		syncEPMModelData(epm);
	    }

	    trx.commit();
	    trx = null;
	    /*
	     * 2012.04.05 2D도면 등록시 섬네일로 자동변환되도록 수정함 황정태
	     */
	    if (cadAppType.equals("ACAD")) {
		// TODO 에러로 인해 주석처리 - 2014.04.swpark
		// Publish.doPublish(false, true, epm , null, null, true, "default", null, 1);
	    }

	} catch (Exception e) {
	    trx.rollback();

	    Kogger.error(EDMPBOHelper.class, e);
	    throw new WTException(e.getLocalizedMessage());
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}

	return epm;
    }

    public static EPMDocument createEPMDocument(HashMap map) throws Exception {
	String number = (map.get("number") == null) ? "" : (String) map.get("number");
	String name = (map.get("name") == null) ? "" : (String) map.get("name");
	String description = (map.get("description") == null) ? "" : (String) map.get("description");
	String cadName = (map.get("cadName") == null) ? "" : (String) map.get("cadName");

	String lifecycle = (map.get("lifecycle") == null) ? "" : (String) map.get("lifecycle");
	String location = (map.get("location") == null) ? "" : (String) map.get("location");
	String state = (map.get("state") == null) ? "" : (String) map.get("state");
	String version = (map.get("version") == null) ? "" : (String) map.get("version");

	// Vector files = (Vector)map.get("files");
	String pdmLinkProduct = (map.get("PDMLinkProduct") == null) ? "" : (String) map.get("PDMLinkProduct");

	String epmAuthoringAppType = (map.get("EPMAuthoringAppType") == null) ? "" : (String) map.get("EPMAuthoringAppType");
	String epmApplicationType = (map.get("EPMApplicationType") == null) ? "" : (String) map.get("EPMApplicationType");
	String epmDocumentType = (map.get("EPMDocumentType") == null) ? "" : (String) map.get("EPMDocumentType");

	Object primary = map.get("primary");
	Vector secondary = (Vector) map.get("secondary");

	if (primary != null) {
	    String fileName = "";

	    if (primary instanceof WBFile) {
		fileName = ((WBFile) primary).getName();
	    } else if (primary instanceof File) {
		fileName = ((File) primary).getName();
	    }

	    if (cadName.trim().length() == 0) {
		cadName = fileName;
	    }
	}

	if (epmDocumentType.length() == 0) {
	    if ("ACAD".equals(epmAuthoringAppType) || ("EXCESS".equals(epmAuthoringAppType))) {
		epmDocumentType = "CADDRAWING";
	    } else {
		epmDocumentType = getEPMDocumentType(cadName).toString();
	    }
	}

	if (epmDocumentType.trim().length() == 0) {
	    epmDocumentType = "OTHER";
	}

	if (epmApplicationType.trim().length() == 0) {
	    epmApplicationType = EDMProperties.getInstance().getAppTypeByPLM();
	}

	// PDMLinkProduct
	WTContainerRef wtContainerRef = null;

	if (pdmLinkProduct.trim().length() > 0) {
	    wtContainerRef = EDMUtil.getWTContainerRef(pdmLinkProduct);
	} else {
	    wtContainerRef = EDMUtil.getWTContinerRefByEDMDefault();
	}

	if (primary == null) {
	    throw new WTException("주 첨부파일이 없습니다.");
	}

	if (EPMDocumentUtilities.doesEPMDocumentExist(number)) { // if (isDuplicateNumber(number)) {
	    throw new WTException("이미 등록된 도면번호입니다");
	}

	if (isDuplicateCADName(cadName)) {
	    throw new WTException("이미 등록된 CAD 파일 명입니다");
	}

	EPMAuthoringAppType authoringAppType = null;
	EPMApplicationType applicationType = null;
	EPMDocumentType documentType = null;

	authoringAppType = EPMAuthoringAppType.toEPMAuthoringAppType(epmAuthoringAppType);
	applicationType = EPMApplicationType.toEPMApplicationType(epmApplicationType);
	documentType = EPMDocumentType.toEPMDocumentType(epmDocumentType);

	/*
	 * EPMContextHelper.setApplication(EPMApplicationType.toEPMApplicationType("EPM")); EPMAuthoringAppType localEPMAuthoringAppType =
	 * EPMAuthoringAppType.toEPMAuthoringAppType(AppUtilities.getCurrentApplication());
	 */

	EPMContextHelper.setApplication(applicationType);
	EPMDocument epm = null;

	epm = EPMDocument.newEPMDocument(number, name, authoringAppType, documentType, cadName);
	// epm.setNumber(number);
	// epm.setName(name);
	// epm.setCADName(cadName);
	epm.setDescription(description);
	// epm.setDocSubType(EPMDocSubType.toEPMDocSubType("OTHER"));

	// EPMAuthoringAppVersion localEPMAuthoringAppVersion = AuthoringAppVersionHelper.getAuthoringAppVersion(authoringAppType, 30,
	// "L-03");
	// localEPMDocument.setAuthoringAppVersion(localEPMAuthoringAppVersion);
	EPMAuthoringAppVersion appVersion = null;

	if (appVersion != null) {
	    epm.setAuthoringAppVersion(appVersion);
	}

	EPMDocumentMaster master = (EPMDocumentMaster) epm.getMaster();
	master.setOwnerApplication(applicationType);
	PersistenceServerHelper.manager.restore(master);

	try {
	    Folder folder = getFolder(location, wtContainerRef);// FolderTaskLogic.getFolder(location, wtContainerRef);
	    FolderHelper.assignLocation((FolderEntry) epm, folder);

	    LifeCycleHelper.setLifeCycle(epm, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, wtContainerRef));

	    if (version.trim().length() > 0) {
		setVersion(epm, version);
	    }
	} catch (Exception e) {
	    throw new WTException(e.getLocalizedMessage());
	}

	epm = (EPMDocument) EDMAttributeUtil.setAttributeValues(epm, map);
	epm = (EPMDocument) PersistenceHelper.manager.store(epm);
	// epm = (EPMDocument) PersistenceHelper.manager.save(epm);

	// file ...
	attach(epm, primary, secondary);

	if (state.trim().length() > 0) {
	    LifeCycleHelper.service.setLifeCycleState(epm, State.toState(state), false);
	}

	return epm;
    }

    public static void deleteEDMUserData(Object obj) throws WTException {
	try {
	    EDMUserData ud = EDMHelper.getEDMUserData(obj);

	    if (ud == null) {
		Kogger.debug(EDMPBOHelper.class, "ud : is null");
		return;
	    }

	    if (obj instanceof Iterated) {
		Iterated iterated = (Iterated) obj;

		if (!VersionControlHelper.service.isFirstIteration(iterated)) {
		    return;
		}

		/*
	         * if(VersionControlHelper.hasPredecessor(iterated)) { Iterated pre = VersionControlHelper.service.predecessorOf(iterated);
	         * 
	         * if(VersionControlHelper.inSameBranch(pre, iterated)) { return; } }
	         */
	    }

	    PersistenceHelper.manager.delete(ud);
	} catch (WTException e) {
	    throw new WTException(e);
	} catch (Exception e) {
	    throw new WTException(e);
	}

	return;
    }

    public static Object setEDMUserData(Object obj, boolean isRevised) throws WTException {
	try {
	    if ((obj == null) || !(obj instanceof EPMDocument)) {
		return null;
	    }

	    Timestamp curTime = new Timestamp(System.currentTimeMillis());
	    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	    Department dept = DepartmentHelper.manager.getDepartment(wtuser);

	    String deptName = (dept == null) ? "" : dept.getName();
	    String deptEnName = (dept == null) ? "" : dept.getEnName();

	    if (deptName.trim().length() == 0) {
		if (CommonUtil.isAdmin()) {
		    deptName = "관리자";
		}
	    }

	    EPMDocument epm = (EPMDocument) obj;

	    if (WorkInProgressHelper.isCheckedOut(epm)) {
		return null;
	    }

	    if (!isRevised) {
		epm = (EPMDocument) wt.vc.VersionControlHelper.service.getLatestIteration(epm, false);
	    }

	    EDMUserData edmUserData = null;

	    // new
	    if (!EDMHelper.checkEDMUserData(epm.getMaster())) {
		return initEDMUserData(epm);
	    }

	    // revised
	    if (obj instanceof Iterated) {
		Iterated iterated = (Iterated) obj;
		Iterated pre = null;

		if (VersionControlHelper.hasPredecessor(iterated)) {
		    pre = VersionControlHelper.service.predecessorOf(iterated);
		}

		if (pre == null) {
		    return initEDMUserData(iterated);
		}

		long a1 = iterated.getIterationInfo().getBranchId();
		long a2 = pre.getIterationInfo().getBranchId();
		// if( VersionControlHelper.inSameBranch(iterated, pre) )

		Kogger.debug(EDMPBOHelper.class, "직접 비교로 변환..");
		// 버그 느낌이 있습니다..
		Kogger.debug(EDMPBOHelper.class, a1 == a2);
		if (a1 == a2) {
		    // if(pre.getBranchIdentifier() == iterated.getBranchIdentifier()) {
		    // update history
		    edmUserData = EDMHelper.getEDMUserData(iterated);

		    if (edmUserData == null) {
			return initEDMUserData(iterated);
		    }

		    edmUserData.setModifierNo(wtuser.getName());
		    edmUserData.setModifierId(wtuser.getName());
		    edmUserData.setModifierName(wtuser.getFullName());
		    edmUserData.setModifierDeptName(deptName);
		    edmUserData.setModifierDeptEnName(deptEnName);
		    edmUserData.setModifyTime(curTime);
		} else {
		    // new history
		    EDMUserData predata = EDMHelper.getEDMUserData(pre);
		    if (predata == null) {
			return initEDMUserData(iterated);
		    }

		    edmUserData = (EDMUserData) predata.clone();
		    edmUserData.setCreatorNo(wtuser.getName());
		    edmUserData.setCreatorId(wtuser.getName());
		    edmUserData.setCreatorName(wtuser.getFullName());
		    edmUserData.setCreatorDeptName(deptName);
		    edmUserData.setCreatorDeptEnName(deptEnName);
		    edmUserData.setCreateTime(curTime);

		    edmUserData.setObjVersion(((Versioned) obj).getVersionIdentifier().getSeries().getValue());
		    edmUserData.setObjClassname(obj.getClass().getName());
		    edmUserData.setObjBranchId(((Versioned) obj).getBranchIdentifier());
		}

		return PersistenceHelper.manager.save(edmUserData);
	    }

	    // modify
	    edmUserData = EDMHelper.getEDMUserData(epm);

	    if (edmUserData == null) {
		return null;
	    }
	    edmUserData.setModifierNo(wtuser.getName());
	    edmUserData.setModifierId(wtuser.getName());
	    edmUserData.setModifierName(wtuser.getFullName());
	    edmUserData.setModifierDeptName(deptName);
	    edmUserData.setModifierDeptEnName(deptEnName);
	    edmUserData.setModifyTime(curTime);
	    return PersistenceHelper.manager.save(edmUserData);
	} catch (WTException e) {
	    throw new WTException(e);
	} catch (WTPropertyVetoException e) {
	    throw new WTException(e);
	} catch (Exception e) {
	    throw new WTException(e);
	}
    }

    public static Object initEDMUserData(Object obj) throws WTException {
	try {
	    if (!(obj instanceof EPMDocument)) {
		return null;
	    }

	    Timestamp curTime = new Timestamp(System.currentTimeMillis());

	    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	    Department dept = DepartmentHelper.manager.getDepartment(wtuser);
	    String deptName = (dept == null) ? "" : dept.getName();
	    String deptEnName = (dept == null) ? "" : dept.getEnName();

	    if (deptName.trim().length() == 0) {
		if (e3ps.common.util.CommonUtil.isAdmin()) {
		    deptName = "관리자";
		}
	    }

	    EDMUserData ud = EDMUserData.newEDMUserData();

	    // if(obj instanceof EPMDocument) {
	    ud.setObjData(ObjectReference.newObjectReference(((EPMDocument) obj).getMaster()));
	    // } else {
	    // ud.setObjData( ObjectReference.newObjectReference((Persistable)obj) );
	    // }

	    // if(obj instanceof EPMDocument) {
	    ud.setObjNumber(((EPMDocument) obj).getNumber());
	    // }

	    // if(obj instanceof Versioned) {
	    ud.setObjVersion(((Versioned) obj).getVersionIdentifier().getSeries().getValue());
	    ud.setObjClassname(obj.getClass().getName());
	    ud.setObjBranchId(((Versioned) obj).getBranchIdentifier());
	    // }

	    ud.setInitCreatorNo(wtuser.getName());
	    ud.setInitCreatorId(wtuser.getName());
	    ud.setInitCreatorName(wtuser.getFullName());
	    ud.setInitDeptName(deptName);
	    ud.setInitDeptEnName(deptEnName);
	    ud.setInitCreateTime(curTime);

	    ud.setCreatorNo(wtuser.getName());
	    ud.setCreatorId(wtuser.getName());
	    ud.setCreatorName(wtuser.getFullName());
	    ud.setCreatorDeptName(deptName);
	    ud.setCreatorDeptEnName(deptEnName);
	    ud.setCreateTime(curTime);

	    ud.setModifierNo(wtuser.getName());
	    ud.setModifierId(wtuser.getName());
	    ud.setModifierName(wtuser.getFullName());
	    ud.setModifierDeptName(deptName);
	    ud.setModifierDeptEnName(deptEnName);
	    ud.setModifyTime(curTime);

	    return PersistenceHelper.manager.save(ud);
	} catch (WTException e) {
	    throw new WTException(e);
	} catch (WTPropertyVetoException e) {
	    throw new WTException(e);
	} catch (Exception e) {
	    throw new WTException(e);
	}
    }

    public static EPMDocument updateEPMDocument(HashMap map) throws Exception {
	String oid = (map.get("oid") == null) ? "" : (String) map.get("oid");
	String number = (map.get("number") == null) ? "" : (String) map.get("number");
	String name = (map.get("name") == null) ? "" : (String) map.get("name");
	String description = (map.get("description") == null) ? "" : (String) map.get("description");
	String cadName = (map.get("cadName") == null) ? "" : (String) map.get("cadName");

	String lifecycle = (map.get("lifecycle") == null) ? "" : (String) map.get("lifecycle");
	String location = (map.get("location") == null) ? "" : (String) map.get("location");
	String state = (map.get("state") == null) ? "" : (String) map.get("state");
	String version = (map.get("version") == null) ? "" : (String) map.get("version");

	String pdmLinkProduct = (map.get("PDMLinkProduct") == null) ? "" : (String) map.get("PDMLinkProduct");

	// 변경 불가...
	// String epmAuthoringAppType = (map.get("EPMAuthoringAppType")==null)? "":(String)map.get("EPMAuthoringAppType");
	// String epmApplicationType = (map.get("EPMApplicationType")==null)? "":(String)map.get("EPMApplicationType");
	// String epmDocumentType = (map.get("EPMDocumentType")==null)? "":(String)map.get("EPMDocumentType");
	/*
	 * if(epmDocumentType.length() == 0) { if("ACAD".equals(epmAuthoringAppType) || ("EXCESS".equals(epmAuthoringAppType))) {
	 * epmDocumentType = "CADDRAWING"; } else { epmDocumentType = getEPMDocumentType(cadName).toString(); } }
	 * 
	 * if(epmDocumentType.trim().length() == 0) { epmDocumentType = "OTHER"; }
	 */

	Object primary = map.get("primary");
	Vector secondary = (Vector) map.get("secondary");

	if ((cadName.trim().length() == 0) && (primary != null)) {
	    if (primary instanceof WBFile) {
		cadName = ((WBFile) primary).getName();
	    } else if (primary instanceof File) {
		cadName = ((File) primary).getName();
	    }
	}

	// PDMLinkProduct
	WTContainerRef wtContainerRef = null;

	if (pdmLinkProduct.trim().length() > 0) {
	    wtContainerRef = EDMUtil.getWTContainerRef(pdmLinkProduct);
	} else {
	    wtContainerRef = EDMUtil.getWTContinerRefByEDMDefault();
	}

	ReferenceFactory rf = new ReferenceFactory();
	EPMDocument epm = null;
	epm = (EPMDocument) rf.getReference(oid).getObject();
	epm = (EPMDocument) checkout(epm);

	/*
	 * EPMDoc : Number,Name,CADName ....
	 */
	if (cadName.trim().length() == 0) {
	    cadName = ((EPMDocumentMaster) epm.getMaster()).getCADName();
	}

	EDMProperties edmProperties = EDMProperties.getInstance();
	if (edmProperties.isAppTypeByPLM(epm.getOwnerApplication().toString())) {
	    String curNumber = epm.getNumber();
	    String curName = epm.getName();
	    String curCADName = ((EPMDocumentMaster) epm.getMaster()).getCADName();
	    Kogger.debug(EDMPBOHelper.class, " curNumber = number [" + curNumber + "=" + number + "]===" + !curNumber.equals(number));
	    Kogger.debug(EDMPBOHelper.class, " curName = name [" + curName + "=" + name + "]===" + !curName.equals(name));
	    Kogger.debug(EDMPBOHelper.class, " curCADName = curCADName [" + curCADName + "=" + curCADName + "]===" + !curCADName.equals(curCADName));
	    if (!curNumber.equals(number) || !curName.equals(name) || !curCADName.equals(curCADName)) {
		if (!curNumber.equals(number) && EPMDocumentUtilities.doesEPMDocumentExist(number)) {// if (isDuplicateNumber(number)) {
		    throw new WTException("이미 등록된 도면번호입니다");
		}

		if ((cadName.length() > 0) && !curCADName.equals(cadName) && isDuplicateCADName(cadName)) {
		    throw new WTException("이미 등록된 CAD 파일 명입니다");
		}

		epm = renumberEPMDocument(epm, name, number, cadName);
	    }
	}

	/*
	 * Version set ...
	 */
	try {
	    if (version.trim().length() > 0) {
		setVersion(epm, version);
	    }
	} catch (Exception e) {
	    throw new WTException(e.getLocalizedMessage());
	}

	epm.setDescription(description);
	epm = (EPMDocument) EDMAttributeUtil.setAttributeValues(epm, map);
	epm = (EPMDocument) PersistenceHelper.manager.modify(epm);
	// epm = (EPMDocument) PersistenceHelper.manager.save(epm);

	// Kogger.debug(getClass(), "=========    File begin  ====================================");
	// file delete ...
	String delFiles[] = (String[]) map.get("delFile");

	if (!CheckInOutTaskLogic.isCheckedOut(epm)) {
	    if (delFiles != null) {
		// ContentItem item = null;
		for (int i = 0; i < delFiles.length; i++) {
		    // item = (ContentItem)rf.getReference(delFiles[i]).getObject();
		    PersistenceHelper.manager.delete((ContentItem) rf.getReference(delFiles[i]).getObject());
		}
	    }
	} else {
	    HashMap delmap = new HashMap();

	    if (delFiles != null) {
		ContentItem item = null;
		for (int i = 0; i < delFiles.length; i++) {
		    item = (ContentItem) rf.getReference(delFiles[i]).getObject();

		    if (item instanceof ApplicationData) {
			ApplicationData appData = (ApplicationData) item;
			delmap.put(appData.getFileName(), "");
		    }
		}
	    }

	    epm = (EPMDocument) ContentHelper.service.getContents(epm);
	    Vector curContents = ContentHelper.getContentList(epm);

	    for (int i = 0; curContents != null && i < curContents.size(); i++) {
		ApplicationData appData = (ApplicationData) curContents.get(i);

		if (delmap.containsKey(appData.getFileName())) {
		    PersistenceHelper.manager.delete(appData);
		}
	    }
	}

	// file delete .....................................................................end
	epm = (EPMDocument) ContentHelper.service.getContents(epm);
	attach(epm, primary, secondary);

	/*
	 * checkout 된 경우 checkin ...
	 */
	if (CheckInOutTaskLogic.isCheckedOut(epm)) {// if(WorkInProgressHelper.isCheckedOut(epm)) {
	    epm = (EPMDocument) CheckInOutTaskLogic.getWorkingCopy(epm);
	    epm = (EPMDocument) CheckInOutTaskLogic.checkInObject(epm, "");
	}

	/*
	 * Folder set ...
	 */
	try {
	    Folder curFolder = epm.getFolderingInfo().getFolder();
	    String path = FolderUtil.getFolderLocation((FolderEntry)epm);
	    if (!org.apache.commons.lang.StringUtils.contains(path, "KET_LIB")) {
		Folder newFolder = FolderTaskLogic.getFolder(location, wtContainerRef);

		if (newFolder == null) {
		    throw new WTException("Folder not found ...");
		}

		if (!curFolder.equals(newFolder)) {
		    // EDMFolderUtil.changeFolders(epm, newFolder, true, true);
		    FolderHelper.service.changeFolder((FolderEntry) epm, newFolder);
		}
	    }
	    
	} catch (Exception e) {
	    throw new WTException(e);
	}

	/*
	 * LifeCycle set ...
	 */
	try {
	    LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference(lifecycle, wtContainerRef);

	    if (!lifecycle.equals(epm.getLifeCycleTemplate().getName())) {
		epm = (EPMDocument) LifeCycleHelper.service.reassign(epm, lctRef, wtContainerRef);
	    }

	} catch (Exception e) {
	    throw new WTException(e);
	}

	String currentState = epm.getLifeCycleState().toString();

	if ((state.trim().length() > 0) && !(currentState.equals(state.trim()))) {
	    epm = (EPMDocument) LifeCycleHelper.service.setLifeCycleState(epm, State.toState(state), true);
	}

	return (EPMDocument) PersistenceHelper.manager.refresh(epm);
    }

    public static void updateTypeCodeLink(EPMDocument epm, NumberCode code, boolean bool) throws WTException {
	if (bool) {
	    Vector v0 = EDMHelper.getEPMDocTypeCodeLink(epm);

	    for (int i = 0; i < v0.size(); i++) {
		PersistenceHelper.manager.delete((EPMDocTypeCodeLink) v0.get(i));
	    }
	}

	if (!(bool) && (code != null)) {
	    Vector v1 = EDMHelper.getEPMDocTypeCodeLink(epm, code);

	    if (v1.size() > 0) {
		return;
	    }
	}

	if (code != null) {
	    EPMDocTypeCodeLink typeCodeLink = EPMDocTypeCodeLink.newEPMDocTypeCodeLink(code, epm);

	    try {
		typeCodeLink.setCodeType(code.getCodeType().toString());
	    } catch (WTPropertyVetoException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    }

	    PersistenceHelper.manager.save(typeCodeLink);
	}
    }

    public static void updateProjectLink(EPMDocument epm, ProjectMaster pjtMaster, boolean bool) throws WTException {
	if ((pjtMaster != null)) {
	    Vector v1 = EDMHelper.getEPMDocProjectLink(epm, pjtMaster);

	    if (v1.size() > 0) {
		return;
	    }
	}

	if (bool) {
	    Vector v1 = EDMHelper.getEPMDocProjectLink(epm);

	    for (int i = 0; i < v1.size(); i++) {
		PersistenceHelper.manager.delete((EPMDocProjectLink) v1.get(i));
	    }
	}

	if (pjtMaster != null) {
	    EPMDocProjectLink pjtLink = EPMDocProjectLink.newEPMDocProjectLink(pjtMaster, epm);
	    PersistenceHelper.manager.save(pjtLink);
	}
    }

    public static EPMDocument setAssociation(EPMDocument epm, HashMap map) throws WTPropertyVetoException, WTException {
	try {
	    String category = (map.get("category") == null) ? "" : (String) map.get("category");

	    // 대표부품/모델
	    String repPoid = (map.get("repPoid") == null) ? "" : (String) map.get("repPoid");
	    if (StringUtils.isEmpty(repPoid)) {
		// CP는 부품관 연결
		if (epm.getNumber().startsWith("CP")) {
		    WTPart cpPart = null;
		    try {

			cpPart = PartBaseHelper.service.getLatestPart(epm.getNumber());
		    } catch (Exception e) {
			Kogger.error(EDMPBOHelper.class, e);
		    }
		    if (cpPart != null) {
			repPoid = CommonUtil.getOIDString(cpPart);
		    }
		}
	    }

	    String repModelOid = (map.get("repModelOid") == null) ? "" : (String) map.get("repModelOid");

	    // 관련 부품/모델
	    String torelPoid[] = (String[]) map.get("torelPoid");
	    String torelModelOid[] = (String[]) map.get("torelModelOid");

	    // 무품번 도면의 관련 모델
	    String torelModelOidNonPart = (map.get("torelModelOidNonPart") == null) ? "" : (String) map.get("torelModelOidNonPart");

	    EDMProperties edmProperties = EDMProperties.getInstance();
	    ReferenceFactory rf = new ReferenceFactory();

	    // 도면/부품/모델 연관 처리 ...
	    String referenceType = edmProperties.getReferenceType(category);

	    /*
	     * 도면:대표부품 대표부품:대표모델 도면:대표모델
	     */
	    // 기존 대표부품 처리 -- Link delete ....
	    deleteReferencedByParts(epm, referenceType, EDMHelper.REQUIRED_STANDARD, edmProperties.isRefModel(category));

	    WTPart part = null;
	    EPMDocument model = null;
	    if (repPoid.trim().length() > 0) {
		part = (WTPart) rf.getReference(repPoid.trim()).getObject();
	    }
	    if (repModelOid.trim().length() > 0) {
		model = (EPMDocument) rf.getReference(repModelOid.trim()).getObject();
	    }

	    setEPMReference(part, epm, model, referenceType, EDMHelper.REQUIRED_STANDARD);

	    /*
	     * 도면:관련 부품 관련부품:관련모델 도면:관련모델
	     */
	    deleteReferencedByParts(epm, referenceType, EDMHelper.REQUIRED_RELATED, edmProperties.isRefModel(category));

	    if (torelPoid != null) {
		WTPart relatedPart = null;
		EPMDocument relatedModel = null;

		for (int i = 0; i < torelPoid.length; i++) {
		    if (torelPoid[i].trim().length() == 0) {
			continue;
		    }

		    relatedPart = (WTPart) rf.getReference(torelPoid[i].trim()).getObject();
		    relatedModel = null;

		    if ((torelModelOid != null) && (torelModelOid[i].trim().length() > 0)) {
			relatedModel = (EPMDocument) rf.getReference(torelModelOid[i].trim()).getObject();
		    }

		    setEPMReference(relatedPart, epm, relatedModel, referenceType, EDMHelper.REQUIRED_RELATED);
		}
	    }

	    /*
	     * 도면:관련모델
	     */
	    deleteReferencedByModels(epm, EDMHelper.REQUIRED_REFERENCE_MODEL);

	    if (torelModelOidNonPart.trim().length() > 0) {
		EPMDocument relatedModel = (EPMDocument) rf.getReference(torelModelOidNonPart.trim()).getObject();
		setEPMReference(null, epm, relatedModel, null, EDMHelper.REQUIRED_REFERENCE_MODEL);
	    }
	} catch (WTException e) {
	    Kogger.error(EDMPBOHelper.class, e);
	    throw new WTException(e.getLocalizedMessage());
	} catch (WTPropertyVetoException e) {
	    Kogger.error(EDMPBOHelper.class, e);
	    throw new WTPropertyVetoException(e.getLocalizedMessage(), null);
	}

	return epm;
    }

    public static void deleteRevisionByECO(EPMDocument epm) throws WTException {
	try {
	    QueryResult qr = EDMHelper.getECAEpmDocLink(epm);

	    while (qr.hasMoreElements()) {
		Object[] oo = (Object[]) qr.nextElement();

		if (oo[0] instanceof KETMoldECAEpmDocLink) {
		    KETMoldECAEpmDocLink link = (KETMoldECAEpmDocLink) oo[0];
		    link.setAfterVersion("");
		    PersistenceHelper.manager.save(link);
		} else if (oo[0] instanceof KETProdECAEpmDocLink) {
		    KETProdECAEpmDocLink link = (KETProdECAEpmDocLink) oo[0];
		    link.setAfterVersion("");
		    PersistenceHelper.manager.save(link);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	    throw new WTException(e);
	}
    }

    public static void deleteLatestLink(EPMDocument epm) throws WTException {
	EDMProperties edmProperties = EDMProperties.getInstance();

	Kogger.debug(EDMPBOHelper.class, "########>> EDMServiceHelper.java :: deleteLatestLink() :: 1");

	try {
	    // 참조 모델 여부 ...
	    if (EDMHelper.isRefedModel(epm)) {
		Kogger.debug(EDMPBOHelper.class, "ECO에서 도면개정 취소 시 모델의 링크정보가 삭제되는 문제로.... 링크 삭제 로직을 임시로 막음");
		// 관련부품에 연결된 3D Model 정보 : 참조 모델
		// ArrayList refedbys= EDMHelper.getReferencedByParts(epm, EDMHelper.REFERENCE_TYPE_MODEL, -1);
		// for( int i = 0; i < refedbys.size(); i++ )
		// {
		// Kogger.debug(getClass(),  "########>> EDMServiceHelper.java :: deleteLatestLink() :: 2" );
		// Object[] objs = (Object[])refedbys.get(i);
		// PersistenceHelper.manager.delete((EPMLink)objs[0]);
		// }

		// 2D Drawing과 3D Mode 사이의 참조관계 정보
		// ArrayList refedmodels= EDMHelper.getReferenceEPMs(epm, -1);
		// for( int i = 0; i < refedmodels.size(); i++ )
		// {
		// Kogger.debug(getClass(),  "########>> EDMServiceHelper.java :: deleteLatestLink() :: 3" );
		// Object[] objs = (Object[])refedmodels.get(i);
		// Kogger.debug(getClass(),  "########>> EDMServiceHelper.java :: deleteLatestLink() :: ModelReferenceLink :: " +
		// (ModelReferenceLink)objs[0] );
		// PersistenceHelper.manager.delete((ModelReferenceLink)objs[0]);
		// }
	    } else // 대표 모델
	    {
		Kogger.debug(EDMPBOHelper.class, "########>> EDMServiceHelper.java :: deleteLatestLink() :: 4");
		// 도면에 존재하는 모든 링크 정보 받아오기...
		ArrayList refedbys = EDMHelper.getRefedByPartMasters(epm, null, -1);
		for (int i = 0; i < refedbys.size(); i++) {
		    Object[] objs = (Object[]) refedbys.get(i);
		    EPMLink epmlink = (EPMLink) objs[0];

		    // 링크 타입이 RELATED_MODEL 인 경우는 Skip....
		    if (epmlink.getReferenceType().equals(EDMHelper.REFERENCE_TYPE_MODEL)) {
			Kogger.debug(EDMPBOHelper.class, "########>> EDMServiceHelper.java :: deleteLatestLink() :: 5");
			continue;
		    }

		    // 도면/모델과 부품 사이의 링크 정보 받아오기...
		    // 도면-부품 링크 & 부품-모델 링크
		    Object[] mdObjs = EDMHelper.getAssociatedModelLinkObjs(epm, (WTPart) objs[1], null, -1);

		    // 도면/모델-부품 링크 정보가 존재하는 경우...
		    if (mdObjs != null) {
			Kogger.debug(EDMPBOHelper.class, "########>> EDMServiceHelper.java :: deleteLatestLink() :: 6");
			// [0]:EPMDocument - 도면
			// [1]:EDMLink - 도면-부품 링크
			// [2]:WTPart - 부품
			// [3]:EDMLink - 부품-모델 링크
			// [4]:EPMDocument - 모델
			// [5]:ModelReferenceLink - 모델-도면 링크

			EPMDocument drawing2 = (EPMDocument) mdObjs[0];
			WTPart part2 = (WTPart) mdObjs[2];
			EPMDocument model2 = (EPMDocument) mdObjs[4];

			if (drawing2 != null)
			    Kogger.debug(EDMPBOHelper.class, "########>> EDMServiceHelper.java :: deleteLatestLink() :: 2D Drawing :: " + drawing2
				    + ", Drawing Name :: " + drawing2.getName());

			if (part2 != null)
			    Kogger.debug(EDMPBOHelper.class, "########>> EDMServiceHelper.java :: deleteLatestLink() :: 부품 :: " + part2 + ", 품명 :: "
				    + part2.getName());

			if (model2 != null) {
			    Kogger.debug(EDMPBOHelper.class, "########>> EDMServiceHelper.java :: deleteLatestLink() :: 3D Model :: " + model2
				    + ", Model Name :: " + model2.getName());
			}

			Kogger.debug(EDMPBOHelper.class, "########>> EDMServiceHelper.java :: deleteLatestLink() :: 7 :: epmlink :: " + epmlink);
			Kogger.debug(EDMPBOHelper.class, "########>> EDMServiceHelper.java :: deleteLatestLink() :: 8 :: (EPMLink)mdObjs[3] :: "
			        + (EPMLink) mdObjs[3]);
			System.out
			        .println("########>> EDMServiceHelper.java :: deleteLatestLink() :: 9 :: (ModelReferenceLink)mdObjs[5] :: "
			                + (ModelReferenceLink) mdObjs[5]);
			// 부품-모델 링크는 마스터-마스터 링크이므로 삭제하게되면 모든 버전에서 부품-모델 링크 정보가 사라짐
			PersistenceHelper.manager.delete((EPMLink) mdObjs[3]);
			// 모델-도면 링크는 마스터-마스터 링크이므로 삭제하게되면 모든 버전에서 모델-도면 링크 정보가 사라짐
			PersistenceHelper.manager.delete((ModelReferenceLink) mdObjs[5]);
		    }

		    PersistenceHelper.manager.delete(epmlink);
		}
	    }
	} catch (WTPropertyVetoException e) {
	    throw new WTException(e);
	}
    }

    public static void deleteLatestLink(WTPart part) throws WTException {
	try {
	    // ArrayList refs = EDMHelper.getReferenceDocs(part, null, -1);
	    ArrayList refs = EDMHelper.getRefDocMaster(part, null, -1);

	    for (int i = 0; i < refs.size(); i++) {
		Object[] oo = (Object[]) refs.get(i);
		EPMLink link = (EPMLink) oo[0];

		if (link.getReferenceType().equals(EDMHelper.REFERENCE_TYPE_MODEL)) {
		    // 모델 마스터
		    EPMDocumentMaster docMst = link.getEpmMaster();
		    QueryResult qr = EDMHelper.getRefedModel(null, docMst, null, link.getRequired());

		    if (qr != null) {
			while (qr.hasMoreElements()) {
			    Object[] obj = (Object[]) qr.nextElement();
			    ModelReferenceLink mrl = (ModelReferenceLink) obj[0];
			    PersistenceHelper.manager.delete(mrl);
			}
		    }
		}

		PersistenceHelper.manager.delete(link);
	    }
	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	    throw new WTException(e);
	}
    }

    public static void syncEPMModelData(EPMDocument epm) throws WTException, WTPropertyVetoException {
	// Kogger.debug(EDMPBOHelper.class, ">>>> call ... syncEPMModelData ..... ");
	ArrayList refedmodels = EDMHelper.getReferencedByModels(epm, -1);

	if ((refedmodels == null) || (refedmodels.size() == 0)) {
	    return;
	}

	ArrayList list = new ArrayList();
	Object[] lkData = null;

	for (int i = 0; i < refedmodels.size(); i++) {
	    lkData = (Object[]) refedmodels.get(i);
	    EPMDocument refedModel = (EPMDocument) lkData[1];

	    if ("INWORK".equals(refedModel.getLifeCycleState().toString())) {
		list.add(refedModel);
	    }

	    EPMDocument drawing = null;
	    Vector refedDrawings = EDMHelper.getRelatedDrawings(refedModel, new LatestConfigSpec());

	    for (int k = 0; k < refedDrawings.size(); k++) {
		drawing = (EPMDocument) refedDrawings.get(k);

		if ("INWORK".equals(drawing.getLifeCycleState().toString())) {
		    list.add(drawing);
		}
	    }
	}

	if (list.size() > 0) {
	    // IBA Copy ...
	    EDMAttributeUtil.copyAttributes(epm, list);

	    // 사업부, 프로젝트 sync ...
	    ProjectMaster pjtMst = (ProjectMaster) EDMHelper.getProject(epm);

	    NumberCode typeCode = null;
	    Vector tv = EDMHelper.getEPMDocTypeCodeLink(epm);

	    if (tv.size() > 0) {
		typeCode = ((EPMDocTypeCodeLink) tv.get(0)).getTypeCode();
	    }

	    for (int i = 0; i < list.size(); i++) {
		Object oo = list.get(i);

		if (oo instanceof EPMDocument) {
		    // 사업부 ...
		    updateTypeCodeLink((EPMDocument) oo, typeCode, true);

		    // 프로젝트 ...
		    updateProjectLink((EPMDocument) oo, pjtMst, true);
		}
	    }
	}
    }

    public static void setEPMReference(WTPart part, EPMDocument epm, EPMDocument model, String referenceType, int required)
	    throws WTPropertyVetoException, WTException {
	if (epm == null) {
	    return;
	}

	if (part != null) {
	    Kogger.debug(EDMPBOHelper.class, ">>>>>>>>>>>>>>>> EDMServiceHelper.java :: setEPMReference() :: 1");
	    EPMLink partLink = saveEPMLink(part, epm, referenceType, required);
	    Kogger.debug(EDMPBOHelper.class, ">>>>>>>>>>>>>>>> EDMServiceHelper.java :: setEPMReference() :: partLink :: " + partLink);
	}

	EPMLink modelLink = null;

	if ((part != null) && (model != null)) {
	    Kogger.debug(EDMPBOHelper.class, ">>>>>>>>>>>>>>>> EDMServiceHelper.java :: setEPMReference() :: 2");
	    modelLink = saveEPMLink(part, model, EDMProperties.getInstance().getReferenceTypeForModel(null), required);
	    Kogger.debug(EDMPBOHelper.class, ">>>>>>>>>>>>>>>> EDMServiceHelper.java :: setEPMReference() :: modelLink :: " + modelLink);
	}

	if (model != null) {
	    String oid = "";

	    if (modelLink != null) {
		Kogger.debug(EDMPBOHelper.class, ">>>>>>>>>>>>>>>> EDMServiceHelper.java :: setEPMReference() :: 3");
		oid = PersistenceHelper.getObjectIdentifier(modelLink).getStringValue();
		Kogger.debug(EDMPBOHelper.class, ">>>>>>>>>>>>>>>> EDMServiceHelper.java :: setEPMReference() :: modelLink oid :: " + oid);
	    }
	    Kogger.debug(EDMPBOHelper.class, ">>>>>>>>>>>>>>>> EDMServiceHelper.java :: setEPMReference() :: 4");
	    ModelReferenceLink modelReferenceLink = saveModelReferenceLink(model, epm, oid, required);
	    System.out
		    .println(">>>>>>>>>>>>>>>> EDMServiceHelper.java :: setEPMReference() :: modelReferenceLink :: " + modelReferenceLink);
	}

	return;
    }

    public static EPMLink saveEPMLink(WTPart part, EPMDocument epm, String referenceType, int required) throws WTPropertyVetoException,
	    WTException {
	QueryResult qr = EDMHelper.getEPMLink(part, epm, referenceType, required);

	if ((qr != null) && (qr.size() > 0)) {
	    Kogger.debug(EDMPBOHelper.class, "EDMServiceHelper.java :: EPMLink() :: 도면/모델-부품 링크가 존재하기 때문에 저장하지 않음");
	    return (EPMLink) ((Object[]) qr.nextElement())[0];
	}

	EPMLink link = EPMLink.newEPMLink((EPMDocumentMaster) epm.getMaster(), (WTPartMaster) part.getMaster());
	link.setRequired(required);
	link.setReferenceType(referenceType);

	HashMap ibaValues = EDMAttributes.getIBAValues(epm, Locale.KOREAN);
	boolean eCad = false;
	if (ibaValues.get(EDMHelper.IBA_CAD_CATEGORY) != null) {
	    String category = EDMEnumeratedTypeUtil.getCADCategory((String) ibaValues.get(EDMHelper.IBA_CAD_CATEGORY), Locale.KOREAN);
	    if ("ECAD_DRAWING".equals(category)) {
		link.setEcad(true);
		eCad = true;
	    } else {
		link.setEcad(false);
		eCad = false;
	    }
	}
	Kogger.debug(EDMPBOHelper.class, "EDMServiceHelper.java :: EPMLink() :: 도면/모델-부품 링크를 새로 저장함");
	PersistenceServerHelper.manager.insert(link);

	PartEpmRelation partEpmRelation = new PartEpmRelation();
	try {
	    // PartToEPMLink Save
	    partEpmRelation.createNewVerRelation((WTPartMaster) part.getMaster(), (EPMDocumentMaster) epm.getMaster(), part, epm, eCad,
		    required, referenceType, true, false);
	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	    throw new WTException(e.getMessage());
	}

	return link;
	// return (EPMLink)PersistenceHelper.manager.save(link);
    }

    public static ModelReferenceLink saveModelReferenceLink(EPMDocument model, EPMDocument epm, String relatedLinkOid, int required)
	    throws WTPropertyVetoException, WTException {
	QueryResult qr = EDMHelper.getRefedModel(epm, model, required);

	if ((qr != null) && (qr.size() > 0)) {
	    return (ModelReferenceLink) ((Object[]) qr.nextElement())[0];
	}

	ModelReferenceLink drwModel = ModelReferenceLink.newModelReferenceLink((EPMDocumentMaster) model.getMaster(),
	        (EPMDocumentMaster) epm.getMaster());
	drwModel.setRequired(required);

	if ((relatedLinkOid != null) && (relatedLinkOid.trim().length() > 0)) {
	    drwModel.setRelatedLinkOid(relatedLinkOid);
	}

	return (ModelReferenceLink) PersistenceHelper.manager.save(drwModel);
    }

    public static void deleteReferencedByParts(EPMDocument epm, String referenceType, int required, boolean isIncludedModel)
	    throws WTException, WTPropertyVetoException {
	ArrayList relateds = EDMHelper.getReferencedByParts(epm, referenceType, required);

	if (relateds.size() > 0) {
	    Object refedData[] = null;

	    for (int i = 0; i < relateds.size(); i++) {
		refedData = (Object[]) relateds.get(i);
		deleteModelRefsByPart(epm, (WTPart) refedData[1], referenceType, required);
		PersistenceHelper.manager.delete((EPMLink) refedData[0]);
	    }
	}

	return;
    }

    public static void deleteModelRefsByPart(EPMDocument epm, WTPart part, String referenceType, int required) throws WTException,
	    WTPropertyVetoException {
	Object[] oo = EDMHelper.getAssociatedModelLinkObjs(epm, part, referenceType, required);

	if (oo != null) {
	    // [0]:EPMDocument - 도면
	    // [1]:EDMLink - 도면-부품 링크
	    // [2]:WTPart - 부품
	    // [3]:EDMLink - 부품-모델 링크
	    // [4]:EPMDocument - 모델
	    // [5]:ModelReferenceLink - 모델-도면 링크

	    PersistenceHelper.manager.delete((EPMLink) oo[3]);
	    PersistenceHelper.manager.delete((ModelReferenceLink) oo[5]);
	}

	return;
    }

    public static void deleteReferencedByParts(EPMDocumentMaster epm, String referenceType, int required, boolean isIncludedModel)
	    throws WTException, WTPropertyVetoException {
	ArrayList relateds = EDMHelper.getRefedByPartMasters(epm, referenceType, required);

	if (relateds.size() > 0) {
	    Object refedData[] = null;

	    for (int i = 0; i < relateds.size(); i++) {
		refedData = (Object[]) relateds.get(i);

		if (isIncludedModel) {
		    Object[] oo = EDMHelper.getAssociatedModelLinkObjs(epm, ((EPMLink) refedData[0]).getPartMaster(), referenceType,
			    required);

		    if (oo != null) {
			// [0]:EPMDocument - 도면
			// [1]:EDMLink - 도면-부품 링크
			// [2]:WTPart - 부품
			// [3]:EDMLink - 부품-모델 링크
			// [4]:EPMDocument - 모델
			// [5]:ModelReferenceLink - 모델-도면 링크

			PersistenceHelper.manager.delete((EPMLink) oo[3]);
			PersistenceHelper.manager.delete((ModelReferenceLink) oo[5]);
		    }
		}

		PersistenceHelper.manager.delete((EPMLink) refedData[0]);
	    }
	}

	return;
    }

    public static void syncLinkData(EPMLink epmLink) throws WTException {
	// Kogger.debug(EDMPBOHelper.class, "call ............. syncLinkData ........ ");
	String referenceTypeForModel = EDMProperties.getInstance().getReferenceTypeForModel(null);

	if (referenceTypeForModel.equals(epmLink.getReferenceType())) {
	    // 부품과 모델 연관 인 경우
	    QueryResult qr = EDMHelper.getEPMLink(epmLink.getPartMaster(), null, "", -1);

	    while (qr.hasMoreElements()) {
		Object[] oo = (Object[]) qr.nextElement();

		if (!referenceTypeForModel.equals(((EPMLink) oo[0]).getReferenceType())) {
		    // if(!PersistenceHelper.isEquivalent(epmLink.getEpmMaster(), ((EPMLink)oo[0]).getEpmMaster())) {
		    deleteModelLink(((EPMLink) oo[0]).getEpmMaster(), epmLink.getEpmMaster());
		}
	    }
	} else {
	    // 부품과 도면 연관인 경우
	    QueryResult qr = EDMHelper.getEPMLink(epmLink.getPartMaster(), null, referenceTypeForModel, -1);

	    while (qr.hasMoreElements()) {
		Object[] oo = (Object[]) qr.nextElement();

		if (!PersistenceHelper.isEquivalent(epmLink.getEpmMaster(), ((EPMLink) oo[0]).getEpmMaster())) {
		    deleteModelLink(epmLink.getEpmMaster(), ((EPMLink) oo[0]).getEpmMaster());
		}
	    }
	}
    }

    public static void deleteModelLink(EPMDocumentMaster epm, EPMDocumentMaster model) throws WTException {
	QueryResult rmQr = EDMHelper.getRefedModel(epm, model, null, -1);

	while (rmQr.hasMoreElements()) {
	    Object[] rmo = (Object[]) rmQr.nextElement();
	    ModelReferenceLink mrl = (ModelReferenceLink) rmo[0];

	    if (mrl.getRequired() == EDMHelper.REQUIRED_REFERENCE_MODEL) {
		continue;
	    }

	    PersistenceHelper.manager.delete(mrl);
	}
    }

    public static void deleteReferencedByModels(EPMDocument epm, int required) throws WTException {
	ArrayList modelArry = EDMHelper.getReferencedByModels(epm, required);

	if (modelArry.size() > 0) {
	    Object[] data = null;

	    for (int i = 0; i < modelArry.size(); i++) {
		data = (Object[]) modelArry.get(i);
		PersistenceHelper.manager.delete((Persistable) data[0]);
	    }
	}
    }

    public static void deleteReferenceDocs(EPMDocument model, int required) throws WTException {
	QueryResult qr = EDMHelper.getRefedModel(null, model, required);

	if (qr != null) {
	    Object[] obj = null;

	    while (qr.hasMoreElements()) {
		obj = (Object[]) qr.nextElement();
		PersistenceHelper.manager.delete((ModelReferenceLink) obj[0]);
	    }
	}
    }

    public static void deleteReferencedByModels(EPMDocumentMaster epm, int required) throws WTException {
	QueryResult result = EDMHelper.getRefedModel(epm, null, required);

	if (result != null) {
	    Object[] data = null;

	    while (result.hasMoreElements()) {
		Object[] oo = (Object[]) result.nextElement();
		PersistenceHelper.manager.delete((ModelReferenceLink) oo[0]);
	    }
	}
    }

    public static PartToEPMLink savePartToEPMLink(WTPart part, EPMDocument epm, String referenceType, int required, boolean isEcad)
	    throws WTPropertyVetoException, WTException {
	ArrayList qr = EDMHelper.getPartToEPMLink(part, epm, referenceType, required);

	if ((qr != null) && (qr.size() > 0)) {
	    return (PartToEPMLink) qr.get(0);
	}

	PartToEPMLink link = PartToEPMLink.newPartToEPMLink(epm, part);
	link.setRequired(required);
	link.setReferenceType(referenceType);
	link.setEcad(isEcad);

	return (PartToEPMLink) PersistenceHelper.manager.save(link);
    }

    public static DrawingToModelReferenceLink saveDrawingToModelReferenceLink(EPMDocument model, EPMDocument epm, String relatedLinkOid,
	    int required) throws WTPropertyVetoException, WTException {
	QueryResult qr = EDMHelper.getRefedModelHistory(epm, model, required);

	if ((qr != null) && (qr.size() > 0)) {
	    return (DrawingToModelReferenceLink) ((Object[]) qr.nextElement())[0];
	}

	DrawingToModelReferenceLink drwModel = DrawingToModelReferenceLink.newDrawingToModelReferenceLink(model, epm);
	drwModel.setRequired(required);

	if ((relatedLinkOid != null) && (relatedLinkOid.trim().length() > 0)) {
	    drwModel.setRelatedLinkOid(relatedLinkOid);
	}

	return (DrawingToModelReferenceLink) PersistenceHelper.manager.save(drwModel);
    }

    public static void setReferenceHistory(WTPart part, EPMDocument epm, EPMDocument model, String referenceType, int required,
	    boolean isEcad, boolean isInWorkPartOnly) throws WTPropertyVetoException, WTException {
	if (epm == null) {
	    return;
	}

	PartToEPMLink pel = null;

	if (part != null) {
	    if (!isInWorkPartOnly || (isInWorkPartOnly && State.INWORK == part.getLifeCycleState()))
		pel = savePartToEPMLink(part, epm, referenceType, required, isEcad);
	}

	PartToEPMLink pml = null;

	if ((part != null) && (model != null)) {
	    if (!isInWorkPartOnly || (isInWorkPartOnly && State.INWORK == part.getLifeCycleState()))
		pml = savePartToEPMLink(part, model, EDMProperties.getInstance().getReferenceTypeForModel(null), required, isEcad);
	}

	if (model != null) {
	    String oid = "";

	    if (pml != null) {
		oid = PersistenceHelper.getObjectIdentifier(pml).getStringValue();
	    }

	    EDMServiceHelper.saveDrawingToModelReferenceLink(model, epm, oid, required);
	}

	return;
    }

    public static Folder getFolder(String path, WTContainerRef wtContainerRef) throws WTException {
	if (path == null) {
	    throw new FolderException("Folder Path is Null");
	}

	return FolderHelper.service.saveFolderPath(path, wtContainerRef);
    }

    public static ContentHolder attach(ContentHolder holder, WBFile file, String desc, ContentRoleType contentRoleType) throws WTException {
	try {
	    if (contentRoleType.equals(ContentRoleType.PRIMARY)) {
		holder = ContentHelper.service.getContents(holder);
		ContentItem item = ContentHelper.service.getPrimaryContent(ObjectReference.newObjectReference(holder));

		if ((item != null) && PersistenceHelper.isPersistent(item)) {
		    PersistenceHelper.manager.delete(item);
		}
	    }

	    e3ps.common.content.E3PSContentHelper.service.attach(holder, file, desc, contentRoleType);
	    /*
	     * WTPrincipalReference principalReference = SessionHelper.manager.getPrincipalReference(); String fileName = file.getName();
	     * if(isDRM){ File drmFile = E3PSDRMHelper.upload(file.getFile(), fileName); file.setFile(drmFile); }
	     * 
	     * InputStream is = new FileInputStream(file.getFile()); ApplicationData applicationData =
	     * ApplicationData.newApplicationData(holder); applicationData.setFileName(fileName);
	     * applicationData.setFileSize(file.getSize()); applicationData.setRole(contentRoleType); applicationData.setDescription(desc ==
	     * null ? "" : desc); applicationData.setCreatedBy(principalReference); applicationData.setModifiedBy(principalReference);
	     * 
	     * if(contentRoleType.equals(ContentRoleType.PRIMARY)) { ContentServerHelper.service.updateContent(holder, applicationData, is);
	     * //ContentServerHelper.service.updatePrimary((FormatContentHolder)holder, applicationData, is); } else {
	     * ContentServerHelper.service.updateContent(holder, applicationData, is); }
	     * 
	     * holder = ContentHelper.service.getContents(holder);
	     */
	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	    throw new WTException(e.getLocalizedMessage());
	}

	return holder;
    }

    public static ContentHolder attach(ContentHolder holder, File file, String desc, ContentRoleType contentRoleType) throws WTException {
	InputStream in = null;

	try {
	    if (contentRoleType.equals(ContentRoleType.PRIMARY)) {
		holder = ContentHelper.service.getContents(holder);
		ContentItem item = ContentHelper.service.getPrimaryContent(ObjectReference.newObjectReference(holder));

		if ((item != null) && PersistenceHelper.isPersistent(item)) {
		    PersistenceHelper.manager.delete(item);
		}
	    }

	    e3ps.common.content.E3PSContentHelper.service.attach(holder, file, desc, contentRoleType);
	    /*
	     * WTPrincipalReference principalReference = SessionHelper.manager.getPrincipalReference(); String fileName = file.getName();
	     * if(isDRM){ File drmFile = E3PSDRMHelper.upload(file, fileName); file = drmFile; }
	     * 
	     * in = new FileInputStream(file); ApplicationData applicationData = ApplicationData.newApplicationData(holder);
	     * applicationData.setFileName(fileName); applicationData.setFileSize(file.length()); applicationData.setRole(contentRoleType);
	     * applicationData.setDescription(desc == null ? "" : desc); applicationData.setCreatedBy(principalReference);
	     * applicationData.setModifiedBy(principalReference);
	     * 
	     * if(contentRoleType.equals(ContentRoleType.PRIMARY)) { ContentServerHelper.service.updateContent(holder, applicationData, in);
	     * //ContentServerHelper.service.updatePrimary((FormatContentHolder)holder, applicationData, is); } else {
	     * ContentServerHelper.service.updateContent(holder, applicationData, in); }
	     * 
	     * holder = ContentHelper.service.getContents(holder);
	     */
	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	    throw new WTException(e.getLocalizedMessage());
	} finally {
	    /*
	     * if(file != null && file.exists()) { if(e3ps.common.jdf.config.ConfigImpl.getInstance().getBoolean("auto.delete.file", true))
	     * file.delete(); }
	     */

	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		    in = null;
		    Kogger.error(EDMPBOHelper.class, e);
		}
	    }
	}

	return holder;
    }

    public static void attach(ContentHolder holder, Object primary, Vector secondary) throws WTException, PropertyVetoException {
	if (primary != null) {
	    if (primary instanceof WBFile) {
		attach(holder, (WBFile) primary, null, ContentRoleType.PRIMARY);
	    } else if (primary instanceof File) {
		attach(holder, (File) primary, null, ContentRoleType.PRIMARY);
	    }
	}

	if (secondary == null) {
	    return;
	}

	for (int i = 0; i < secondary.size(); i++) {
	    holder = ContentHelper.service.getContents(holder);
	    Object f = secondary.get(i);

	    if (f instanceof WBFile) {
		WBFile file = (WBFile) f;
		String desc = "";

		if ("file_gerber_primary".equals(file.getFieldName())) {
		    desc = "gerber";
		}

		attach(holder, file, desc, ContentRoleType.SECONDARY);
	    } else if (f instanceof File) {
		File file = (File) f;
		attach(holder, file, "", ContentRoleType.SECONDARY);
	    }
	}
    }

    public static WTSet getAuthoringAppVersions(String authoringAppType) throws WTInvalidParameterException, WTException {
	return AuthoringAppVersionHelper.getAuthoringAppVersions(EPMAuthoringAppType.toEPMAuthoringAppType(authoringAppType));
	/*
	 * WTSet wtset = wt.epm.AuthoringAppVersionHelper.getAuthoringAppVersions(EPMAuthoringAppType.toEPMAuthoringAppType("CATIAV5")); for
	 * (Iterator iterator = wtset.persistableIterator(); iterator.hasNext(); ) { EPMAuthoringAppVersion appver =
	 * (EPMAuthoringAppVersion)iterator.next();
	 * 
	 * }
	 */
    }

    public static EPMDocumentType getEPMDocumentType(String fileName) {
	if (fileName == null) {
	    return EPMDocumentType.toEPMDocumentType("OTHER");
	}

	String ext = null;
	int i = fileName.lastIndexOf(".");

	if (i <= 0) {
	    return EPMDocumentType.toEPMDocumentType("OTHER");
	}

	ext = fileName.substring(i).toLowerCase();

	if ((ext.equals(".dwg")) || (ext.equals(".drw")) || (ext.equals(".catdrawing")) || (ext.equals(".slddrw")) || (ext.equals(".dxf"))) {
	    return EPMDocumentType.toEPMDocumentType("CADDRAWING");
	} else if ((ext.equals(".prt")) || (ext.equals(".catpart")) || (ext.equals(".model")) || (ext.equals(".sldprt"))) {
	    return EPMDocumentType.toEPMDocumentType("CADCOMPONENT");
	} else if ((ext.equals(".asm")) || (ext.equals(".catproduct")) || (ext.equals(".sldasm"))) {
	    return EPMDocumentType.toEPMDocumentType("CADASSEMBLY");
	} else if ((ext.equals(".pcb"))) {
	    return EPMDocumentType.toEPMDocumentType("OTHER");
	} else if ((ext.equals(".sch"))) {
	    return EPMDocumentType.toEPMDocumentType("OTHER");
	} else if ((ext.equals(".pls"))) {
	    return EPMDocumentType.toEPMDocumentType("OTHER");
	} else {
	    return EPMDocumentType.toEPMDocumentType("OTHER");
	}
    }

    public static EPMAuthoringAppType getEPMAuthoringAppType(String fileName) {
	if (fileName == null) {
	    return EPMAuthoringAppType.toEPMAuthoringAppType("OTHER");
	}

	String ext = null;
	int i = fileName.lastIndexOf(".");

	if (i <= 0) {
	    return EPMAuthoringAppType.toEPMAuthoringAppType("OTHER");
	}

	ext = fileName.substring(i).toLowerCase();

	if ((ext.equals(".prt")) || (ext.equals(".asm")) || (ext.equals(".drw")) || (ext.equals(".frm"))) {
	    return EPMAuthoringAppType.toEPMAuthoringAppType("PROE");
	} else if ((ext.equals(".catpart")) || (ext.equals(".catproduct")) || (ext.equals(".catdrawing"))) {
	    return EPMAuthoringAppType.toEPMAuthoringAppType("CATIAV5");
	} else if ((ext.equals(".model"))) {
	    return EPMAuthoringAppType.toEPMAuthoringAppType("CATIA");
	} else if ((ext.equals(".prt"))) {
	    return EPMAuthoringAppType.toEPMAuthoringAppType("UG");
	} else if ((ext.equals(".dwg"))) {
	    return EPMAuthoringAppType.toEPMAuthoringAppType("ACAD");
	} else if ((ext.equals(".pcb"))) {
	    return EPMAuthoringAppType.toEPMAuthoringAppType("PADS");
	} else if ((ext.equals(".sch"))) {
	    return EPMAuthoringAppType.toEPMAuthoringAppType("PADS_SCH");
	} else if ((ext.equals(".pls"))) {
	    return EPMAuthoringAppType.toEPMAuthoringAppType("EXCESS");
	}

	return null;
    }

    public static boolean isDuplicateNumber(String number) {
	boolean flag = false;

	if (number != null) {
	    try {
		QuerySpec query = new QuerySpec(EPMDocumentMaster.class);
		query.appendWhere(new SearchCondition(EPMDocumentMaster.class, EPMDocumentMaster.NUMBER, "=", number), new int[] { 0 });
		QueryResult qr = PersistenceHelper.manager.find(query);

		return qr.hasMoreElements();
	    } catch (QueryException e) {
		Kogger.error(EDMPBOHelper.class, e);
	    } catch (WTException e) {
		Kogger.error(EDMPBOHelper.class, e);
	    }
	}

	return flag;
    }

    public static boolean isDuplicateCADName(String cadName) {
	boolean flag = false;

	if (cadName != null) {
	    try {
		QuerySpec query = new QuerySpec(EPMDocumentMaster.class);
		query.appendWhere(new SearchCondition(EPMDocumentMaster.class, EPMDocumentMaster.CADNAME, "=", cadName), new int[] { 0 });
		QueryResult qr = PersistenceHelper.manager.find(query);

		return qr.hasMoreElements();
	    } catch (QueryException e) {
		Kogger.error(EDMPBOHelper.class, e);
	    } catch (WTException e) {
		Kogger.error(EDMPBOHelper.class, e);
	    }
	}

	return flag;
    }

    public static EPMDocument renumberEPMDocument(EPMDocument epm, String name, String number, String cadName) throws WTException {
	Identified localIdentified = (Identified) epm.getMaster();
	EPMDocumentMasterIdentity identity = (EPMDocumentMasterIdentity) localIdentified.getIdentificationObject();

	Kogger.debug(EDMPBOHelper.class, ":::::::::::::::::: localIdentified == " + localIdentified);
	Kogger.debug(EDMPBOHelper.class, ":::::::::::::::::: identity == " + identity);

	try {
	    identity.setNumber(number);
	    identity.setName(name);
	    String str = epm.getCADName();

	    Kogger.debug(EDMPBOHelper.class, ":::::::::::::::::: renumberEPMDocument epm == " + epm);
	    Kogger.debug(EDMPBOHelper.class, ":::::::::::::::::: renumberEPMDocument number == " + number);
	    Kogger.debug(EDMPBOHelper.class, ":::::::::::::::::: renumberEPMDocument name == " + name);
	    Kogger.debug(EDMPBOHelper.class, ":::::::::::::::::: renumberEPMDocument str == " + str);
	    Kogger.debug(EDMPBOHelper.class, ":::::::::::::::::: renumberEPMDocument cadName == " + cadName);

	    if (false) {
		Kogger.debug(EDMPBOHelper.class, "docToRenumber:" + epm.getNumber());
		Kogger.debug(EDMPBOHelper.class, "docToRenumber:" + epm.getName());
		Kogger.debug(EDMPBOHelper.class, "docToRenumber:" + ((EPMDocumentMaster) epm.getMaster()).getCADName());
	    }

	    if (!(str.equals(cadName))) {
		// identity.setCADName(str);
		EPMDocumentHelper.service.changeCADName((EPMDocumentMaster) epm.getMaster(), cadName);
	    }

	    IdentityHelper.service.changeIdentity(localIdentified, identity);
	} catch (WTPropertyVetoException localWTPropertyVetoException) {
	    throw new WTException(localWTPropertyVetoException);
	}

	IdentityHelper.service.changeIdentity(localIdentified, identity);

	return (EPMDocument) PersistenceHelper.manager.refresh(epm);
    }

    public static Workable checkout(Workable workable) {
	CheckoutLink checkoutlink;

	try {
	    if (CheckInOutTaskLogic.isCheckedOut(workable)) {
		return CheckInOutTaskLogic.getWorkingCopy(workable);
		/*
	         * if (!(WorkInProgressHelper.isWorkingCopy(workable))) { return WorkInProgressHelper.service.workingCopyOf(workable); }
	         * else { return workable; }
	         */
	    }

	    checkoutlink = WorkInProgressHelper.service.checkout(workable, CheckInOutTaskLogic.getCheckoutFolder(), "");

	    return checkoutlink.getWorkingCopy();
	} catch (NonLatestCheckoutException e) {
	    Kogger.error(EDMPBOHelper.class, e);
	} catch (WorkInProgressException e) {
	    Kogger.error(EDMPBOHelper.class, e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(EDMPBOHelper.class, e);
	} catch (PersistenceException e) {
	    Kogger.error(EDMPBOHelper.class, e);
	} catch (WTException e) {
	    Kogger.error(EDMPBOHelper.class, e);
	} finally {
	    workable = null;
	}

	return null;
    }

    public static void setVersion(Versioned versioned, String ver) throws SeriesIncrementInvalidException, VersionControlException,
	    WTPropertyVetoException, WTException {
	// HarvardSeries multilevelseries = HarvardSeries.newHarvardSeries("IntegerSpec");
	Kogger.debug(EDMPBOHelper.class, "versioned = " + versioned + "  ## ver = " + ver);
	Kogger.debug(EDMPBOHelper.class, "versioned = " + versioned + "  ## ver = " + ver);
	HarvardSeries multilevelseries = HarvardSeries.newHarvardSeries("IntegerSeries");
	multilevelseries.setValue(ver);

	VersionIdentifier versionidentifier = VersionIdentifier.newVersionIdentifier(multilevelseries);
	VersionControlHelper.setVersionIdentifier(versioned, versionidentifier, false);

	// VersionControlHelper.assignVersionLabel( versioned, ver + ".0" );

	// Series series = Series.newSeries("wt.vc.IterationIdentifier", "1");
	// IterationIdentifier iterationidentifier = IterationIdentifier.newIterationIdentifier(series);
	// VersionControlHelper.setIterationIdentifier(versioned, iterationidentifier);
    }
}
