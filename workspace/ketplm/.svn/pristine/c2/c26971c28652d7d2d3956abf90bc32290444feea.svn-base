package ext.ket.edm.migration.iba;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.edm.beans.EDMHelper;
import ext.ket.edm.cad2bom.service.internal.EpmInfoUpadator;
import ext.ket.edm.service.base.EpmBaseHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.log.Kogger;

public class MigIbaHolderUpdate implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigIbaHolderUpdate manager = new MigIbaHolderUpdate();

    public MigIbaHolderUpdate() {

    }
    
    //IBAHOLDER (CAD종류, category) 업데이트 필요시 사용한다. 도번, 버전을 파라미터로 받으며 버전은 ALL로 주면 모든 버전에 대해 업데이트실행된다. 2016.03.22 by 황정태
    //파라미터 1 : 도면번호
    //파라미터 2 : 대상버전
    //파라미터 3 : category
    //windchill ext.ket.edm.migration.iba.MigIbaHolderUpdate CU_MG615616-5 1 제품도면
    public static void main(String[] args) {

	try {

	    String epmNo = null;
	    String version = null;
	    String category = null;
	    
	    if (args == null || args.length < 3)
		throw new Exception("@@ args need !");
	    else {
		epmNo = args[0];
		version = args[1];
		category = args[2];
	    }

	    Kogger.debug(MigIbaHolderUpdate.class, "@start");
	    MigIbaHolderUpdate.manager.saveFromExcel(epmNo, version, category);
	    Kogger.debug(MigIbaHolderUpdate.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigIbaHolderUpdate.class, e);
	}
    }

    public void saveFromExcel(String epmNo, String version, String category) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class, String.class, String.class };
		Object aobj[] = { epmNo, version, category };

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
	    executeMigration(epmNo, version, category);
	}
    }

    public void executeMigration(String epmNo, String version, String category) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	//ReferenceFactory rf = new ReferenceFactory();
	//Transaction trx = null;
	try {

	    Kogger.debug(getClass(), "**************** CadApptype & CAD_CATEGORY Update Start **************************");

	    SessionHelper.manager.setAdministrator();
	    //
	    // trx = new Transaction();
	    // trx.start();

	    EPMDocumentMaster epmMast = PartBaseHelper.service.getEpmMaster(epmNo);
	    if (epmMast == null) {

	    } else {
		QueryResult qr = wt.vc.VersionControlHelper.service.allVersionsOf(epmMast);

		while (qr.hasMoreElements()) {

		    EPMDocument epm = ((EPMDocument) qr.nextElement());
		    
		    //String cateory = e3ps.common.iba.IBAUtil.getAttrValue(epm, EDMHelper.IBA_CAD_CATEGORY);
		    EpmInfoUpadator updator = new EpmInfoUpadator();

		    String empVersion = epm.getVersionIdentifier().getValue();
		    System.out.println("version : " + empVersion);

		    if (version == "ALL") {
			EpmBaseHelper.service.updateIbaCadApptype(epm);
			updator.callupdateIBAValue(epm, EDMHelper.IBA_CAD_CATEGORY, category);
			updator.callupdateIBAValue(epm, EDMHelper.IBA_CAD_MANAGE_TYPE, category);
		    } else {
			if (empVersion.equals(version)) {
			    EpmBaseHelper.service.updateIbaCadApptype(epm);
			    updator.callupdateIBAValue(epm, EDMHelper.IBA_CAD_CATEGORY, category);
			    updator.callupdateIBAValue(epm, EDMHelper.IBA_CAD_MANAGE_TYPE, category);
			}
		    }

		}
	    }
	    // trx.commit();

	    Kogger.debug(getClass(), "**************** CadApptype Update End **************************");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    //trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

}
