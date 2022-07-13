package ext.ket.edm.migration.revisionrel;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import ext.ket.edm.migration.service.KetMigEpmHelper;
import ext.ket.shared.log.Kogger;

public class EpmRevisionRelWcLoader implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static EpmRevisionRelWcLoader manager = new EpmRevisionRelWcLoader();

    public EpmRevisionRelWcLoader() {

    }

    // windchill ext.ket.edm.migration.revisionrel.EpmRevisionRelWcLoader
    // windchill ext.ket.edm.migration.revisionrel.EpmRevisionRelWcLoader
    public static void main(String[] args) {

	try {
	    
	    String toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	
	    Kogger.debug(EpmRevisionRelWcLoader.class, "@start:" + toDayTime);
	    EpmRevisionRelWcLoader.manager.saveFromExcel(args);
	    
	    toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(EpmRevisionRelWcLoader.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(EpmRevisionRelWcLoader.class, e);
	}
    }

    public void saveFromExcel(String[] args) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String[].class };
		Object aobj[] = { args };

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
	    executeMigration(args);
	}
    }
    
    public void executeMigration(String[] args) throws Exception {
	
	SessionContext sessioncontext = SessionContext.newContext();
	try {

	    SessionHelper.manager.setAdministrator();
		
	    KetMigEpmHelper.service.migEpmRevisionRel(args);
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

}
