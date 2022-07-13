package ext.ket.devloptest;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.project.cancel.CancelCostHistory;
import e3ps.project.cancel.CancelProject;
import e3ps.project.cancel.cancelHistoryLink;
import ext.ket.shared.log.Kogger;;

public class cancelDataLoad implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */

    public static cancelDataLoad manager = new cancelDataLoad();

    public cancelDataLoad() {

    }

    // windchill ext.ket.devloptest.SalesProjectDataLoad
    public static void main(String[] args) {

	try {

	    String oid = null;

	    if (args == null || args.length < 1)
		throw new Exception("@@ args need !");
	    else {
		oid = args[0];
	    }

	    Kogger.debug(cancelDataLoad.class, "@start");
	    cancelDataLoad.manager.saveFromExcel(oid);
	    Kogger.debug(cancelDataLoad.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(cancelDataLoad.class, e);
	}
    }

    public void saveFromExcel(String oid) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class };
		Object aobj[] = { oid };

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
	    executeMigration(oid);
	}
    }

    public void executeMigration(String oid) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	Transaction trx = null;
	try {

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    trx = new Transaction();
	    trx.start();

	    create(oid);

	    trx.commit();

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");

	} catch (Exception e) {
	    e.printStackTrace();
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    public void cancleCreate(CancelProject cancel) throws Exception{
	CancelCostHistory history = new CancelCostHistory(); 
	
	history.setRealCollectCost("400");// 회수비 실적
	history.setRev("0");
	history.setTargetCollectCost("1000"); //목표 회수비
	history.setProjectCost("5000"); //프로젝트 총액
	int expectedLoss = Integer.parseInt("5000")-Integer.parseInt("1000");//프로젝트 총액-목표 회수비
	history.setExpectedLossCost(Integer.toString(expectedLoss));//예상손실비
	int lastLoss = Integer.parseInt("5000")-Integer.parseInt("400");//프로젝트 총액-회수비실적
	history.setEndLostCost(Integer.toString(lastLoss));//최종손실비
	history = (CancelCostHistory)PersistenceHelper.manager.save(history);
	
	cancelHistoryLink hlink = cancelHistoryLink.newcancelHistoryLink(history, cancel); 
	
	hlink = (cancelHistoryLink)PersistenceHelper.manager.save(hlink);
	
	QueryResult qr = PersistenceHelper.manager.navigate(cancel, "costHistory", cancelHistoryLink.class, false);
	while (qr.hasMoreElements()) {
	    hlink = (cancelHistoryLink) qr.nextElement();
	    System.out.println(hlink.getCancel().getRealCost());
	    
	    System.out.println(hlink.getCostHistory().getRev());
	}
	this.reviseCancelProject(cancel, history);
    }
    
    public void reviseCancelProject(CancelProject cancel,CancelCostHistory org_history) throws Exception{
	
	CancelCostHistory history = new CancelCostHistory(); 
	history.setRev("1");
	history.setRealCollectCost("200");// 회수비 실적
	
	history.setTargetCollectCost(org_history.getTargetCollectCost()); //목표 회수비
	history.setProjectCost(org_history.getProjectCost()); //프로젝트 총액
	history.setExpectedLossCost(org_history.getExpectedLossCost());//예상손실비
	
	int lastLoss = Integer.parseInt("5000")-(Integer.parseInt("200")+Integer.parseInt(org_history.getRealCollectCost()));//프로젝트 총액-회수비실적
	history.setEndLostCost(Integer.toString(lastLoss));//최종손실비
	history = (CancelCostHistory)PersistenceHelper.manager.save(history);
	
	cancelHistoryLink hlink = cancelHistoryLink.newcancelHistoryLink(history, cancel); 
	
	hlink = (cancelHistoryLink)PersistenceHelper.manager.save(hlink);
	
	QueryResult qr = PersistenceHelper.manager.navigate(cancel, "costHistory", cancelHistoryLink.class, false);
	while (qr.hasMoreElements()) {
	    hlink = (cancelHistoryLink) qr.nextElement();
	    System.out.println(hlink.getCancel().getRealCost());
	    
	    System.out.println(hlink.getCostHistory().getRev());
	}
    }

    public void create(String oid) throws Exception {
	
	
	CancelProject cancel = (CancelProject)CommonUtil.getObject(oid);
	
	this.cancleCreate(cancel);
	
    }
}
