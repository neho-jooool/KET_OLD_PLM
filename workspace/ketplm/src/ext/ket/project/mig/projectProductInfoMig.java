package ext.ket.project.mig;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.project.ProductProject;
import e3ps.project.beans.MoldProjectHelper;

public class projectProductInfoMig implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public static projectProductInfoMig manager = new projectProductInfoMig();

    public projectProductInfoMig() {

    }

    public static void main(String[] args) {
	try {
	    projectProductInfoMig.manager.saveFromExcel();
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
	ArrayList<ProductProject> targetPjtList = new ArrayList<ProductProject>();
	try {

	    System.out.println("**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    trx = new Transaction();
	    trx.start();

	    getAllLastProductProject(false, targetPjtList); // last 프로젝트 (checkin 상태)
	    getAllLastProductProject(true, targetPjtList); // last 프로젝트 (checkout 상태, 즉 일정변경 직전 프로젝트)

	    MoldProjectHelper.projectManufacPlaceUpdate(targetPjtList);

	    trx.commit();
	    System.out.println("**************** DRAWING  Excel Extract End **************************");

	} catch (Exception e) {
	    e.printStackTrace();
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    public static QueryResult getAllLastProductProject(boolean checkOut, ArrayList<ProductProject> targetPjtList) {

	QueryResult qr = null;
	try {

	    QuerySpec qs = new QuerySpec();
	    int idx = qs.appendClassList(ProductProject.class, true);

	    qs.appendWhere(new SearchCondition(ProductProject.class, ProductProject.LASTEST, SearchCondition.IS_TRUE, true),
		    new int[] { idx });

	    if (checkOut) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(new SearchCondition(ProductProject.class, ProductProject.CHECK_OUT_STATE, SearchCondition.EQUAL, "c/o"),
		        new int[] { idx });
	    } else {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(new SearchCondition(ProductProject.class, ProductProject.CHECK_OUT_STATE, SearchCondition.NOT_EQUAL, "c/o"),
		        new int[] { idx });
	    }

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(ProductProject.class, ProductProject.PJT_TYPE, SearchCondition.EQUAL, 2), new int[] { idx });

	    qr = PersistenceHelper.manager.find(qs);

	    if (qr != null && qr.hasMoreElements()) {

		while (qr.hasMoreElements()) {

		    Object obj[] = (Object[]) qr.nextElement();
		    ProductProject project = (ProductProject) obj[0];
		    targetPjtList.add(project);
		}
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return qr;
    }
}
