package ext.ket.common;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import ext.ket.cost.code.sap.ErpProductCostInterface;
import ext.ket.shared.log.Kogger;

public class CostInterfaceErp2 implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static CostInterfaceErp2 manager = new CostInterfaceErp2();

    public CostInterfaceErp2() {

    }

    public static void main(String[] args) throws Exception {

	try {

	    String toDayTime = "";
	    try {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd:HH-mm-ss");
		toDayTime = sdFormat.format(Calendar.getInstance().getTime());
	    } catch (Exception e) {
		Kogger.error("Exception : " + e.getMessage());
	    }

	    Kogger.debug(CostInterfaceErp2.class, "@start:" + toDayTime);
	    CostInterfaceErp2.manager.saveFromExcel("");

	    toDayTime = "";
	    try {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd:HH-mm-ss");
		toDayTime = sdFormat.format(Calendar.getInstance().getTime());
	    } catch (Exception e) {
		Kogger.error("Exception : " + e.getMessage());
	    }

	    Kogger.debug(CostInterfaceErp2.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(CostInterfaceErp2.class, e);
	}
    }

    public void saveFromExcel(String temp) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class };
		Object aobj[] = { temp };

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
	    executeMigration(temp);
	}
    }

    public void executeMigration(String temp) throws Exception {
	SessionContext sessioncontext = SessionContext.newContext();
	try {

	    SessionHelper.manager.setAdministrator();

	    execute(temp);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    public void execute(String temp) throws Exception {
	ErpProductCostInterface costInterface = new ErpProductCostInterface();
	Map<String, Object> reqMap = new HashMap<String, Object>();
	costInterface.costProductHistoryCreate(reqMap);
	costInterface.costProductSendErp(costInterface.getErpTargetList());

    }
}
