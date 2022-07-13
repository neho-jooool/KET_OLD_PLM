package e3ps.qms;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import ext.ket.shared.log.Kogger;

public class QmsPartInterfaceMig implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public static QmsPartInterfaceMig manager = new QmsPartInterfaceMig();

    public QmsPartInterfaceMig() {

    }

    // windchill e3ps.qms.QmsDataLoader
    public static void main(String[] args) {

	try {

	    String fileName = null;

	    Kogger.debug(QmsPartInterfaceMig.class, "@start");
	    QmsPartInterfaceMig.manager.saveFromExcel(fileName);
	    Kogger.debug(QmsPartInterfaceMig.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(QmsPartInterfaceMig.class, e);
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

	String partNo = "";

	try {

	    Kogger.debug(getClass(), "**************** Qms Interface Start **************************");

	    SessionHelper.manager.setAdministrator();

	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT WTPARTNUMBER as PARTNO FROM WTPARTMASTER");

	    List<Map<String, Object>> partList = QmsPartInterfaceUtil.manager.extractList(sql.toString());

	    for (Map<String, Object> map : partList) {
		partNo = (String) map.get("PARTNO");
		QmsPartInterfaceUtil.manager.qmsPartSave((String) map.get("PARTNO"), false);
		System.out.println("qms 자재마스터 이관 성공 -> partNo : " + partNo);
	    }

	    Kogger.debug(getClass(), "**************** Qms Interface End **************************");
	    isSuccess = true;
	} catch (Exception e) {
	    isSuccess = false;
	    System.out.println("qms 자재마스터 이관 실패 -> partNo : " + partNo);
	} finally {
	    System.out.println("partMaster 이관 완료!!  성공여부 : " + isSuccess);
	    SessionContext.setContext(sessioncontext);

	}
    }

}
