package e3ps.qms;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import ext.ket.shared.log.Kogger;

public class QmsPartInterfaceMig2 implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public static QmsPartInterfaceMig2 manager = new QmsPartInterfaceMig2();

    public QmsPartInterfaceMig2() {

    }

    // windchill e3ps.qms.QmsDataLoader
    public static void main(String[] args) {

	try {

	    String fileName = null;

	    Kogger.debug(QmsPartInterfaceMig2.class, "@start");
	    QmsPartInterfaceMig2.manager.saveFromExcel(fileName);
	    Kogger.debug(QmsPartInterfaceMig2.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(QmsPartInterfaceMig2.class, e);
	}
    }

    public void saveFromExcel(String fileName) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class };
		Object aobj[] = { fileName };

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
	    executeMigration(fileName);
	}
    }

    public void executeMigration(String fileName) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();
	boolean isSuccess = false;

	try {

	    Kogger.debug(getClass(), "**************** Qms Interface Start **************************");

	    SessionHelper.manager.setAdministrator();

	    QmsPartInterfaceUtil.manager.qms4mSave();

	    Kogger.debug(getClass(), "**************** Qms Interface End **************************");
	    isSuccess = true;
	} catch (Exception e) {
	    isSuccess = false;
	    System.out.println("qms 4M 이관 실패");
	} finally {
	    System.out.println("qms 4M 이관 완료!!  성공여부 : " + isSuccess);
	    SessionContext.setContext(sessioncontext);

	}
    }

}
