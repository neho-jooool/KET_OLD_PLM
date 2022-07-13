package ext.ket.part.migration.mater;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import ext.ket.part.migration.erp.service.KetMigHelper;
import ext.ket.shared.log.Kogger;

public class PartMaterWcLoader implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static PartMaterWcLoader manager = new PartMaterWcLoader();

    public PartMaterWcLoader() {

    }

    // windchill ext.ket.part.migration.mater.PartMaterWcLoader E:\MigrationData\part_mater\MigrationData_MATER.xlsx
    // windchill ext.ket.part.migration.mater.PartMaterWcLoader d:\ptc\Windchill_10.2\Windchill\loadFiles\ket\part_mater\Migration Data_MATER.xlsx
    public static void main(String[] args) {

	try {
	    
            String filePath = null;
            
            if (args == null || args.length < 1)
            	throw new Exception("@@ args need !");
            else{
            	filePath = args[0];
            }
		    
	    String toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(PartMaterWcLoader.class, "@start:" + toDayTime);
	    PartMaterWcLoader.manager.saveFromExcel(filePath);
	    
	    toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(PartMaterWcLoader.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(PartMaterWcLoader.class, e);
	}
    }

    public void saveFromExcel(String filePath) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class };
		Object aobj[] = { filePath };

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
    
    public void executeMigration(String filePath) throws Exception {
	
	SessionContext sessioncontext = SessionContext.newContext();
	try {

	    SessionHelper.manager.setAdministrator();
	    
	    KetMigHelper.service.migPartMater(filePath);
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

}
