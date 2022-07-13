package ext.ket.shared.log;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import ext.ket.shared.log4j.KetLogger;

// windchill ext.ket.shared.log.SetLoggingUser tklee
public class SetLoggingUser implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static SetLoggingUser manager = new SetLoggingUser();

    public SetLoggingUser() {
    }

    public static void main(String[] args) {

	try {

	    KetLogger.migration.debug("@start");
	    SetLoggingUser.manager.saveFromExcel(args);
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

		if(args == null || args.length < 1){
		    Kogger.setLoggingUserFwd(null); // 로깅 user 없음
		}else{
		    Kogger.setLoggingUserFwd(args[0]); // 로깅 user 지정
		}

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
