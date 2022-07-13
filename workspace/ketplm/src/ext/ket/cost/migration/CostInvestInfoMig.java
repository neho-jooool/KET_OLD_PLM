package ext.ket.cost.migration;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import ext.ket.cost.code.service.CostCodeHelper;
import ext.ket.cost.entity.CostPart;

public class CostInvestInfoMig implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public static CostInvestInfoMig manager = new CostInvestInfoMig();

    public CostInvestInfoMig() {

    }

    public static void main(String[] args) {
	try {
	    CostInvestInfoMig.manager.saveFromExcel();
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

	ReferenceFactory rf = new ReferenceFactory();
	Transaction trx = null;
	try {

	    System.out.println("**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    trx = new Transaction();
	    trx.start();

	    QuerySpec spec = new QuerySpec();
	    spec.addClassList(CostPart.class, true);
	    spec.appendWhere(new SearchCondition(CostPart.class, "parentReference.key.id", SearchCondition.EQUAL, 0L), new int[] { 0 });
	    QueryResult qr = PersistenceHelper.manager.find(spec);
	    int cnt = qr.size();
	    while (qr.hasMoreElements()) {
		Object[] o = (Object[]) qr.nextElement();
		CostPart part = (CostPart) o[0];
		CostCodeHelper.service.NinvestPartiotionUpdate(part);
		PersistenceHelper.manager.save(part);

		cnt--;
		System.out.println("총 대상 건수 : " + qr.size() + " 건 , 남은 건수 : " + cnt);
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
