package ext.ket.cost.migration;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import ext.ket.cost.code.sap.ErpProductCostInterface;

public class CostErpProductInterfaceMig implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public static CostErpProductInterfaceMig manager = new CostErpProductInterfaceMig();

    public CostErpProductInterfaceMig() {

    }

    public static void main(String[] args) {
	try {
	    CostErpProductInterfaceMig.manager.saveFromExcel();
	} catch (Exception e) {

	}
    }

    public void saveFromExcel() throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = {};
		Object aobj[] = {};

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
	    executeMigration();
	}
    }

    public void executeMigration() throws WTException {
	SessionContext sessioncontext = SessionContext.newContext();

	Transaction trx = null;
	try {

	    System.out.println("**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    trx = new Transaction();
	    trx.start();
	    ErpProductCostInterface costInterface = new ErpProductCostInterface();
	    Map<String, Object> reqMap = new HashMap<String, Object>();
	    costInterface.costProductHistoryCreate(reqMap);
	    costInterface.costProductSendErp(costInterface.getCostSendTargetList());
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
