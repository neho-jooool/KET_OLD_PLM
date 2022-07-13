package ext.ket.part.migration.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.sap.ErpPartInterFace;
import ext.ket.part.sap.ErpPartInterFaceSample;
import ext.ket.part.sap.ErpPartSampleUtil;
import ext.ket.shared.log.Kogger;

public class MigPartSendToErpSample2 implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    // windchill ext.ket.part.migration.base.MigPartSendToErpSample
    public static MigPartSendToErpSample2 manager = new MigPartSendToErpSample2();

    public MigPartSendToErpSample2() {

    }

    public static void main(String[] args) {

	try {

	    Kogger.debug(MigPartSendToErpSample2.class, "@start");
	    MigPartSendToErpSample2.manager.saveFromExcel();
	    Kogger.debug(MigPartSendToErpSample2.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigPartSendToErpSample2.class, e);
	}
    }

    public void saveFromExcel() throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = {};
		Object aobj[] = {};

		Kogger.debug(getClass(), "@		start");
		RemoteMethodServer.getDefault().invoke("saveFromExcel", null, this, aclass, aobj);
		Kogger.debug(getClass(), "@		end");

		return;

	    } catch (RemoteException e) {
		Kogger.debug(getClass(), e);
	    } catch (InvocationTargetException e) {
		Kogger.debug(getClass(), e);
	    } catch (Exception e) {
		Kogger.debug(getClass(), e);
	    }
	} else {
	    executeMigration();
	}
    }

    public void executeMigration() throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	ReferenceFactory rf = new ReferenceFactory();
	try {


	    SessionHelper.manager.setAdministrator();
	    
	    ErpPartSampleUtil util = new ErpPartSampleUtil();
	    String [] partTypes = new String[]{"D"}; //
	    for(String partType : partTypes ) {
		List<String> oidList = util.getOidList(partType);
		Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");
		// ECO No 없어도 저장되도록 수정
		for(String oid : oidList){

		    WTPart wtpart = (WTPart)CommonUtil.getObject(oid);
		    List<WTPart> partList = new ArrayList<WTPart>();
		    partList.add(wtpart);
		    
		    String ecoNo = ""; // partBaseDao.searchEONo(branchId);
		    Map<String, String> workerNameMap = new HashMap<String, String>();
		    ErpPartInterFaceSample erpPartInterFace = new ErpPartInterFaceSample();
		    Map resultMap = erpPartInterFace.sendPartInfoToErp(partList, ecoNo, workerNameMap, false);

		    boolean success = (Boolean) resultMap.get(ErpPartInterFace.SUCCESS);
//		    List<String> errorLogList = (List<String>) resultMap.get(ErpPartInterFace.PLM_ERROR_LIST);
//		    String erpMsg = (String) resultMap.get(ErpPartInterFace.ERP_ERROR_MSG);
		    Kogger.debug(getClass(), "##" + wtpart.getNumber() + " : " + success);
		    
		}
		Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");
	    }


	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

}
