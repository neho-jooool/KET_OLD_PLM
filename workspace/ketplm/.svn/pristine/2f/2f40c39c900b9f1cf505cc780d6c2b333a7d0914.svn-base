package ext.ket.part.replacePart;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import wt.epm.EPMDocumentMaster;
import wt.epm.EPMDocumentMasterIdentity;
import wt.fc.IdentityHelper;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.replacePart.util.replacePartUploadUtil;
import ext.ket.shared.log.Kogger;
import ext.ket.wfm.service.KETWorkspaceHelper;

public class MigReplacePart implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigReplacePart manager = new MigReplacePart();

    public MigReplacePart() {

    }

    // windchill ext.ket.part.migration.base.MigReplacePart TEST_1102-01_2D H793280-3
    public static void main(String[] args) {

	try {

	    String filePath = null;

	    if (args == null || args.length < 1)
		throw new Exception("@@ args need !");
	    else{
		filePath = args[0];
	    }
	    
	    Kogger.debug(MigReplacePart.class, "@start");
	    MigReplacePart.manager.saveFromExcel(filePath);
	    Kogger.debug(MigReplacePart.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigReplacePart.class, e);
	}
    }

    public void saveFromExcel(String filePath) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = {String.class};
		Object aobj[] = {filePath};

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
	    executeMigration(filePath);
	}
    }

    public void executeMigration(String filePath) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	ReferenceFactory rf = new ReferenceFactory();
	Transaction trx = null;
	try {

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    replacePartUploadUtil util = new replacePartUploadUtil();
	    util.load(filePath);

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");


	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

}
