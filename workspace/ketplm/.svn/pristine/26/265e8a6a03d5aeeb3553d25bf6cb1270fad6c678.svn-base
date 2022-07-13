package ext.ket.cost.migration;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import ext.ket.cost.entity.CostFormula;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;

public class CostFormulaColumnMig implements RemoteAccess, Serializable {
    
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    
    public static CostFormulaColumnMig manager = new CostFormulaColumnMig();
    
    public CostFormulaColumnMig(){
	
    }
    
    public static void main(String[] args) {
	try{
	    CostFormulaColumnMig.manager.saveFromExcel();
	}catch(Exception e){
	    
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
	    spec.addClassList(CostFormula.class, true);
	    QueryResult qr = PersistenceHelper.manager.find(spec);
	    
	    while(qr.hasMoreElements()){
		Object[] o = (Object[]) qr.nextElement();
		CostFormula costF = (CostFormula)o[0];
		costF.getMftFactory2_old();
		costF.setMftFactory2(costF.getMftFactory2_old());
		PersistenceHelper.manager.save(costF);
	    }
	    trx.commit();
	    System.out.println("**************** DRAWING  Excel Extract End **************************");
	    
	    spec = new QuerySpec();
	    spec.addClassList(CostFormula.class, true);
	    qr = PersistenceHelper.manager.find(spec);
	    
	    while(qr.hasMoreElements()){
		Object[] o = (Object[]) qr.nextElement();
		CostFormula costF = (CostFormula)o[0];
		System.out.println("new : "+costF.getMftFactory2());
		System.out.println("old : "+costF.getMftFactory2_old());
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }
}
