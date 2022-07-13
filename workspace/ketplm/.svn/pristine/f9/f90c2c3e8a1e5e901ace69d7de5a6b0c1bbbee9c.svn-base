package ext.ket.part.migration.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import wt.fc.IdentityHelper;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPartMaster;
import wt.part.WTPartMasterIdentity;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.log.Kogger;

public class MigPartNumber implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigPartNumber manager = new MigPartNumber();

    public MigPartNumber() {

    }

    // windchill ext.ket.part.migration.base.MigPartNumber H793280A-3 H793280-3
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
	    
	    Kogger.debug(MigPartNumber.class, "@start");
	    MigPartNumber.manager.saveFromExcel(oldPartNo, newPartNo);
	    Kogger.debug(MigPartNumber.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigPartNumber.class, e);
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
	    
	    WTPartMaster partMaster = PartBaseHelper.service.getMaster(oldPartNo);
	    WTPartMasterIdentity identity = (WTPartMasterIdentity) partMaster.getIdentificationObject();
	    identity.setNumber(newPartNo);
	    partMaster = (WTPartMaster) IdentityHelper.service.changeIdentity(partMaster, identity);
	    
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
