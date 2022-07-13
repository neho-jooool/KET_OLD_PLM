package e3ps.load.remote.edm;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;

import com.ptc.windchill.cadx.common.EPMDocumentUtilities;

import e3ps.common.code.NumberCode;
import e3ps.edm.EPMDocProjectLink;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.beans.EDMServiceHelper;
import e3ps.edm.clients.batch.EPMLoadData;
import e3ps.edm.util.EDMProperties;
import e3ps.project.E3PSProject;
import e3ps.project.ProjectMaster;
import ext.ket.shared.log.Kogger;

public class EPMLoadHelper implements RemoteAccess {
    public static boolean isAdmin(String userId) {
	if (!RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = { String.class };
	    Object argValues[] = { userId };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("isAdmin", EPMLoadHelper.class.getName(), null, argTypes, argValues);
	    } catch (RemoteException e) {
		Kogger.error(EPMLoadHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EPMLoadHelper.class, e);
	    }

	    return ((Boolean) obj).booleanValue();
	}

	boolean isAdmin = false;

	try {
	    WTUser wtuser = wt.org.OrganizationServicesHelper.manager.getAuthenticatedUser(userId);
	    Enumeration en = wtuser.parentGroupNames();

	    while (en.hasMoreElements()) {
		String st = (String) en.nextElement();

		if (st.equals("Administrators")) {
		    isAdmin = true;
		    break;
		}
	    }

	    Kogger.debug(EPMLoadHelper.class, "# isAdmin : " + isAdmin);
	} catch (Exception e) {
	    Kogger.error(EPMLoadHelper.class, e);
	}

	return isAdmin;
    }

    public static String convNumber(String partNumber, String category, String cadAppType) throws WTException {
	if (!RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = { String.class, String.class, String.class };
	    Object argValues[] = { partNumber, category, cadAppType };
	    Object obj = null;

	    try {
		obj = RemoteMethodServer.getDefault().invoke("convNumber", EPMLoadHelper.class.getName(), null, argTypes, argValues);
	    } catch (RemoteException e) {
		Kogger.error(EPMLoadHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EPMLoadHelper.class, e);
		throw new WTException(e);
	    }

	    return (String) obj;
	}

	String number = "";

	try {
	    if ((partNumber == null) || (partNumber.trim().length() == 0)) {
		return "";
	    }

	    number = EDMProperties.getInstance().getConvertedNumber(partNumber, category, cadAppType);
	} catch (Exception e) {
	    Kogger.error(EPMLoadHelper.class, e);
	}

	return number;
    }

    public static boolean deleteLoadFile(String filePath, String userId) throws Exception {
	if (!RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = { String.class, String.class };
	    Object argValues[] = { filePath, userId };
	    Object obj = null;

	    try {
		obj = RemoteMethodServer.getDefault().invoke("deleteLoadFile", EPMLoadHelper.class.getName(), null, argTypes, argValues);
	    } catch (RemoteException e) {
		Kogger.error(EPMLoadHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EPMLoadHelper.class, e);
	    }

	    return ((Boolean) obj).booleanValue();
	}

	SessionContext sessioncontext = SessionContext.newContext();

	try {
	    SessionHelper.manager.setPrincipal(userId);
	    EPMLoadHelper lm = new EPMLoadHelper();

	    return lm._deleteLoadFile(filePath, userId);
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    private boolean _deleteLoadFile(String filePath, String userId) throws Exception {
	try {
	    File fDir = new File(filePath);
	    boolean bRetval = false;

	    if (fDir != null && fDir.exists()) {
		if (fDir.isDirectory()) {
		    bRetval = deleteDir(fDir);
		} else {
		    bRetval = fDir.delete();
		}
	    }

	    return bRetval;
	} catch (Exception e) {
	    Kogger.error(EPMLoadHelper.class, e);
	    throw new Exception(e);
	}
    }

    /**
     * Deletes all files and subdirectories under the specified directory including the specified directory
     * 
     * @param fDir
     *            - directory to be deleted
     * @return boolean - true if directory was successfully deleted
     */
    public static boolean deleteDir(File fDir) {
	boolean bRetval = false;

	if (fDir != null && fDir.exists()) {
	    bRetval = deleteDirectoryContent(fDir);

	    if (bRetval) {
		bRetval = bRetval && fDir.delete();
	    }
	}

	return bRetval;
    }

    /**
     * Delete all files and directories in directory but do not delete the directory itself.
     * 
     * @param fDir
     *            - directory to delete
     * @return boolean - sucess flag
     */
    public static boolean deleteDirectoryContent(File fDir) {
	boolean bRetval = false;

	if (fDir != null && fDir.isDirectory()) {
	    File[] files = fDir.listFiles();

	    if (files != null) {
		bRetval = true;
		boolean dirDeleted;

		for (int index = 0; index < files.length; index++) {
		    if (files[index].isDirectory()) {
			// TODO: Performance: Implement this as a queue where you add to
			// the end and take from the beginning, it will be more efficient
			// than the recursion
			dirDeleted = deleteDirectoryContent(files[index]);

			if (dirDeleted) {
			    bRetval = bRetval && files[index].delete();
			} else {
			    bRetval = false;
			}
		    } else {
			bRetval = bRetval && files[index].delete();
		    }
		}
	    }
	}

	return bRetval;
    }

    public static String load(String userId, Vector vector, String dirString) throws Exception {
	SessionContext sessioncontext = SessionContext.newContext();

	try {
	    SessionHelper.manager.setPrincipal(userId);
	    EPMLoadHelper lm = new EPMLoadHelper();

	    return lm._load(vector, dirString);
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    private String _load(Vector datas, String dirString) throws Exception {
	Transaction trx = new Transaction();

	try {
	    trx.start();

	    ReferenceFactory rf = new ReferenceFactory();
	    EDMProperties edmProperties = EDMProperties.getInstance();
	    EPMDocument epm = null;

	    for (int i = 0; i < datas.size(); i++) {
		EPMLoadData data = (EPMLoadData) datas.get(i);

		/*
	         * String number = data.number; String name = data.name; String cadName = data.cadName;
	         * 
	         * CADManageType mt = CADManageType.toCADManageType(data.manageType); DevStage ds = DevStage.toDevStage(data.devStage);
	         * CADCategory cat = CADCategory.toCADCategory(data.category); CADAppType at = CADAppType.toCADAppType(data.cadAppType);
	         */

		NumberCode bizCode = (NumberCode) rf.getReference(data.bizOid).getObject();

		// ////////////////////////////////////////////////////////////////////////////////////////////////////
		// Added by MJOH, 2011-03-19
		// 프로젝트 정보 처리....
		// ////////////////////////////////////////////////////////////////////////////////////////////////////
		ProjectMaster pjtMaster = null;
		E3PSProject project = null;

		String projectOid = data.projectOid == null ? "" : data.projectOid;

		if (projectOid.trim().length() > 0) {
		    project = (E3PSProject) rf.getReference(projectOid).getObject();
		}

		pjtMaster = (project == null) ? null : project.getMaster();
		// ////////////////////////////////////////////////////////////////////////////////////////////////////

		String location = "";
		String lifecycle = edmProperties.getEPMDefaultLC();
		epm = EPMDocumentUtilities.getEPMDocument(data.number.toUpperCase());
		boolean wgm = false;

		if (epm != null) {
		    if (!edmProperties.isAppTypeByPLM(epm.getOwnerApplication().toString())) {
			wgm = true;
			location = epm.getLocation();
			// data.cadAppType = EDMHelper.getCADAppType(epm);
		    }
		}

		if (location.length() == 0) {
		    location = edmProperties.getFullPath(bizCode.getName(), data.manageType, data.category, wgm);
		}

		File file = null;
		if (data.fileName.length() > 0) {
		    file = new File(dirString + "\\" + data.fileName);
		}

		/*
	         * Kogger.debug(getClass(), "#===================================================="); Kogger.debug(getClass(), "# number : "
	         * + number); Kogger.debug(getClass(), "# name : " + name); Kogger.debug(getClass(), "# cadName : " + cadName);
	         * Kogger.debug(getClass(), "# location : " + location); Kogger.debug(getClass(), "# lifecycle : " + lifecycle);
	         * Kogger.debug(getClass(), "# 사업부 : " + bizCode.getName()); Kogger.debug(getClass(), "# 제품/금형 : " +
	         * mt.getDisplay(Locale.KOREAN)); Kogger.debug(getClass(), "# 도면구분 : " + ds.getDisplay(Locale.KOREAN));
	         * Kogger.debug(getClass(), "# 도면유형 : " + cat.getDisplay(Locale.KOREAN)); Kogger.debug(getClass(), "# CAD종류 : " +
	         * at.getDisplay(Locale.KOREAN)); Kogger.debug(getClass(), "# WGM : " + wgm);
	         * 
	         * if(file.exists()) { Kogger.debug(getClass(), "File Name : " + file.getAbsolutePath()); Kogger.debug(getClass(),
	         * "File length : " + file.length()); } else { Kogger.debug(getClass(), "File Name : " + dirString + "\\" + data.fileName);
	         * Kogger.debug(getClass(), "File : not found!!!"); }
	         */

		HashMap map = new HashMap();

		map.put("number", data.number);
		map.put("name", data.name);
		map.put("description", "");
		map.put("cadName", data.cadName);
		map.put("lifecycle", lifecycle);
		map.put("location", location);
		map.put("EPMAuthoringAppType", data.cadAppType);

		if ((file != null) && (file.exists())) {
		    map.put("primary", file);
		}

		// IBA 값 처리
		if (data.manageType.length() > 0) {
		    map.put(EDMHelper.IBA_CAD_MANAGE_TYPE, data.manageType);
		}

		if (data.devStage.length() > 0) {
		    map.put(EDMHelper.IBA_DEV_STAGE, data.devStage);
		}

		if (data.category.length() > 0) {
		    map.put(EDMHelper.IBA_CAD_CATEGORY, data.category);
		}

		if (data.cadAppType.length() > 0) {
		    map.put(EDMHelper.IBA_CAD_APP_TYPE, data.cadAppType);
		}

		if (data.security.length() > 0) {
		    map.put(EDMHelper.IBA_SECURITY, data.security);
		}

		/*
	         * if(data.manufacturingVersion.length() > 0) { map.put(EDMHelper.IBA_MANUFACTURING_VERSION, data.manufacturingVersion); }
	         */

		map.put(EDMHelper.IBA_DUMMY_FILE, EDMHelper.IBA_DUMMY_FILE_VALUE_NO);

		if (epm != null) {
		    map.put("oid", rf.getReferenceString(epm));
		    epm = EDMServiceHelper.updateEPMDocument(map);
		} else {
		    epm = EDMServiceHelper.createEPMDocument(map);
		}

		// 사업부 정보 처리....
		EDMServiceHelper.updateTypeCodeLink(epm, bizCode, true);

		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Added by MJOH, 2011-03-19
		// 프로젝트 정보 처리....
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (pjtMaster != null) {
		    EPMDocProjectLink pjtLink = EPMDocProjectLink.newEPMDocProjectLink(pjtMaster, epm);
		    PersistenceHelper.manager.save(pjtLink);
		}
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*
	         * //3D 모델 검색... if( !(wgm) ) { if("MOLD_DRAWING".equals(data.manageType)) { String modelNumber =
	         * edmProperties.getConvertedNumber(data.getPartNumber(), data.category, "UG"); EPMDocument model =
	         * EPMDocumentUtilities.getEPMDocument(modelNumber.toUpperCase()); if(model != null) { Kogger.debug(getClass(), "# Model : "
	         * + model.getNumber());
	         * 
	         * //도면 ...
	         * 
	         * 
	         * //관련 도면 check boolean isValid = true; boolean isExist = EDMHelper.isReferenceEPMExist(model, -1); if(isExist) { isValid =
	         * EDMHelper.isReferenceEPMExist(model, epm, -1); }
	         * 
	         * if( !(isValid) ) { throw new Exception("도면번호'"+data.number+"'에 대한 모델'"+model.getNumber()+"'과 이미  관련된 도면이 존재합니다."); }
	         * 
	         * String referenceType = edmProperties.getReferenceType(data.category); EDMServiceHelper.setEPMReference(null, epm, model,
	         * referenceType, EDMHelper.REQUIRED_STANDARD); } } }
	         */

		Kogger.debug(getClass(), "save : " + epm.getNumber());

		/*
	         * 정의된 IBA 속성/사업부/프로젝트 정보 동기화 시킴.
	         */
		/*
	         * if(edmProperties.isRefModel(data.category)) { EDMServiceHelper.syncEPMModelData(epm); }
	         */
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(getClass(), e);
	    throw new Exception(e);
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}

	return "";
    }

    public static Object validate(Object data) throws Exception {
	if (!RemoteMethodServer.ServerFlag) {
	    try {
		Class argTypes[] = { Object.class };
		Object argValues[] = { data };

		return RemoteMethodServer.getDefault().invoke("validate", EPMLoadHelper.class.getName(), null, argTypes, argValues);
	    } catch (Exception ex) {
		Kogger.error(EPMLoadHelper.class, ex);
	    }
	}

	SessionContext sessioncontext = SessionContext.newContext();

	try {
	    SessionHelper.manager.setAdministrator();

	    EPMLoadData data0 = (EPMLoadData) data;

	    if ((data0.number == null) || (data0.number.trim().length() == 0)) {
		return data0;
	    }

	    EPMDocument epm = null;
	    epm = EPMDocumentUtilities.getEPMDocument(data0.number.toUpperCase());

	    if (epm != null) {
		data0.isExist = true;

		if (!(data0.cadAppType).equals(epm.getAuthoringApplication().toString())) {
		    data0.isAppTypeDiff = true;
		}
	    }

	    // CAD Name check
	    if (((epm == null) && (data0.cadName.trim().length() > 0)) || ((epm != null) && !(data0.cadName.equals(epm.getCADName())))) {
		data0.isCADName = e3ps.edm.beans.EDMServiceHelper.isDuplicateCADName(data0.cadName);
	    }

	    return data0;
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    public static Vector validate(Vector datas) throws Exception {
	if (!RemoteMethodServer.ServerFlag) {
	    try {
		Class argTypes[] = { Vector.class };
		Object argValues[] = { datas };
		Object obj = RemoteMethodServer.getDefault().invoke("validate", EPMLoadHelper.class.getName(), null, argTypes, argValues);

		return (Vector) obj;
	    } catch (Exception ex) {
		Kogger.error(EPMLoadHelper.class, ex);
	    }
	}

	SessionContext sessioncontext = SessionContext.newContext();

	try {
	    SessionHelper.manager.setAdministrator();
	    EDMProperties edmProperties = EDMProperties.getInstance();
	    Vector chkedDatas = new Vector();

	    for (int i = 0; i < datas.size(); i++) {
		EPMLoadData data = (EPMLoadData) datas.get(i);

		if ((data.number == null) || (data.number.trim().length() == 0)) {
		    chkedDatas.add(data);
		    continue;
		}

		/*
	         * if(data.isSkipRow) { data.isValidate = true; chkedDatas.add(data); continue; }
	         */

		EPMDocument epm = null;
		epm = EPMDocumentUtilities.getEPMDocument(data.number.toUpperCase());

		if (epm != null) {
		    data.isExist = true;

		    if (!(data.cadAppType).equals(epm.getOwnerApplication().toString())) {
			data.isAppTypeDiff = true;
		    }
		}

		// CAD Name check
		if (data.cadName.trim().length() > 0 && (epm == null || (epm != null && !(data.cadName.equals(epm.getCADName()))))) {
		    data.isCADName = e3ps.edm.beans.EDMServiceHelper.isDuplicateCADName(data.cadName);
		}

		chkedDatas.add(data);
	    }
	    return chkedDatas;

	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

}
