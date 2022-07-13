package ext.ket.cost.migration;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import ext.ket.cost.code.sap.ErpProductCostInterface;

public class CostErpChildInterfaceMig implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public static CostErpChildInterfaceMig manager = new CostErpChildInterfaceMig();

    public CostErpChildInterfaceMig() {

    }

    public static void main(String[] args) {
	try {
	    String flag = args[0];
	    CostErpChildInterfaceMig.manager.saveFromExcel(flag);
	} catch (Exception e) {

	}
    }

    public void saveFromExcel(String flag) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class };
		Object aobj[] = { flag };

		System.out.println("@		start");
		RemoteMethodServer.getDefault().invoke("saveFromExcel", null, this, aclass, aobj);
		System.out.println("@		end");

		return;

	    } catch (RemoteException e) {
		e.printStackTrace();
	    } catch (InvocationTargetException e) {
		e.printStackTrace();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	} else {
	    executeMigration(flag);
	}
    }

    public void executeMigration(String flag) throws WTException {
	SessionContext sessioncontext = SessionContext.newContext();

	Transaction trx = null;
	try {

	    System.out.println("**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    trx = new Transaction();
	    trx.start();
	    ErpProductCostInterface costInterface = new ErpProductCostInterface();

	    if ("1".equals(flag)) {
		costInterface.migChildPartMaterailUpdate();
	    }
	    if ("2".equals(flag)) {
		costInterface.migChildPartERPSend();
	    }

	    trx.commit();
	    System.out.println("**************** DRAWING  Excel Extract End **************************");

	} catch (Exception e) {
	    e.printStackTrace();
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }
}
