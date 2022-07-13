package ext.ket.part.migration.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import wt.epm.EPMDocumentMaster;
import wt.epm.EPMDocumentMasterIdentity;
import wt.fc.IdentityHelper;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.log.Kogger;

public class MigEpmNumber implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigEpmNumber manager = new MigEpmNumber();

    public MigEpmNumber() {

    }

    // windchill ext.ket.part.migration.base.MigEpmNumber TEST_1102-01_2D H793280-3
    public static void main(String[] args) {

	try {

	    String oldPartNo = null;
	    String newPartNo = null;

	    if (args == null || args.length < 2)
		throw new Exception("@@ args need !");
	    else{
		oldPartNo = args[0];
		newPartNo = args[1];
	    }
	    
	    Kogger.debug(MigEpmNumber.class, "@start");
	    MigEpmNumber.manager.saveFromExcel(oldPartNo, newPartNo);
	    Kogger.debug(MigEpmNumber.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigEpmNumber.class, e);
	}
    }

    public void saveFromExcel(String oldPartNo, String newPartNo) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = {String.class, String.class};
		Object aobj[] = {oldPartNo, newPartNo};

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
	    executeMigration(oldPartNo, newPartNo);
	}
    }

    public void executeMigration(String oldPartNo, String newPartNo) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	ReferenceFactory rf = new ReferenceFactory();
	Transaction trx = null;
	try {

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    trx = new Transaction();
	    trx.start();
	    
	    EPMDocumentMaster partMaster = PartBaseHelper.service.getEpmMaster(oldPartNo);
	    EPMDocumentMasterIdentity identity = (EPMDocumentMasterIdentity) partMaster.getIdentificationObject();
	    identity.setNumber(newPartNo);
	    partMaster = (EPMDocumentMaster) IdentityHelper.service.changeIdentity(partMaster, identity);
	    
	    trx.commit();

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");


	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

}
