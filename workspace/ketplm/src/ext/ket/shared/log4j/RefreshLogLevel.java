package ext.ket.shared.log4j;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;

// windchill ext.ket.shared.log4j.RefreshLogLevel
public class RefreshLogLevel implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static RefreshLogLevel manager = new RefreshLogLevel();

    public RefreshLogLevel() {
    }

    public static void main(String[] args) {

	try {

	    KetLogger.migration.debug("@start");
	    RefreshLogLevel.manager.saveFromExcel(args);
	    KetLogger.migration.debug("@end");

	} catch (Exception e) {
	    KetLogger.migration.error(e);
	}
    }

    public void saveFromExcel(String[] args) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String[].class };
		Object aobj[] = { args };

		KetLogger.migration.debug("@		start");
		RemoteMethodServer.getDefault().invoke("saveFromExcel", null, this, aclass, aobj);
		KetLogger.migration.debug("@		end");

		return;

	    } catch (RemoteException e) {
		KetLogger.migration.error(e);
	    } catch (InvocationTargetException e) {
		KetLogger.migration.error(e);
	    } catch (Exception e) {
		KetLogger.migration.error(e);
	    }
	} else {

	    executeMigration(args);
	}
    }

    public void executeMigration(String[] args) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	try {

	    SessionHelper.manager.setAdministrator();

	    testWindchill(args);

	    KetLogger.migration.debug("**************** DRAWING  Excel Extract End **************************");

	} catch (Exception e) {
	    KetLogger.migration.error(e);
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    public void testWindchill(String[] args) throws Exception {

	KetLogger.migration.debug("***************** DrawingDoc Create Start *******************");

	Transaction trx = null;

	try {

	    trx = new Transaction();
	    trx.start();

	    try {

		KetLogger.refreshLogConfigFwd();

	    } catch (WTException pve) {
		KetLogger.migration.error(pve);
	    }

	    KetLogger.migration.debug("@ 222 ");

	    trx.commit();

	} catch (Exception e) {
	    KetLogger.migration.error(e);
	    throw e;
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}
    }

}
