package ext.ket.part.migration.revision;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import ext.ket.part.migration.erp.service.KetMigHelper;
import ext.ket.shared.log.Kogger;

public class PartRevWcLoader implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static PartRevWcLoader manager = new PartRevWcLoader();

    public PartRevWcLoader() {

    }

    // windchill ext.ket.part.migration.revision.PartRevWcLoader 0 Z
    // windchill ext.ket.part.migration.revision.PartRevWcLoader 0 15 / 16 25 / 26 5 / 6 9 / A E / F H / I N / O T / U Z
    public static void main(String[] args) {

	try {

	    String arg1 = null;
	    String arg2 = null;

	    if (args == null || args.length < 2)
		throw new Exception("@@ args need !");
	    else{
		arg1 = args[0];
		arg2 = args[1];
	    }
	    
	    String toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	
	    Kogger.debug(PartRevWcLoader.class, "@start:" + toDayTime);
	    PartRevWcLoader.manager.saveFromExcel(arg1, arg2);
	    
	    toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(PartRevWcLoader.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(PartRevWcLoader.class, e);
	}
    }

    public void saveFromExcel(String arg1, String arg2) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class, String.class };
		Object aobj[] = { arg1, arg2 };

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
	    executeMigration(arg1, arg2);
	}
    }
    
    public void executeMigration(String arg1, String arg2) throws Exception {
	
	SessionContext sessioncontext = SessionContext.newContext();
	try {

	    SessionHelper.manager.setAdministrator();
	    
	    String[] seperPartNos = new String[]{
        		"0", "10","11","12","13","14","15","16","17","18","19"
        		,"20","21","22","23","24","25","26","27","28","29"
        		,"3","4","5","6","7","8","9"
        		,"A","B","C","D","E","F","G","H","I","J","K","L","M","N"
        		,"O","P","Q","R","S","T","U","V","W","X","Y","Z" 
        		}; // 36 개
	    
	    boolean start = false;
	    
	    for( int k = 0; k < seperPartNos.length; k++ ){
		
		if(!start){
		    if(arg1.equalsIgnoreCase(seperPartNos[k])){
			start = true;
		    }else{
			continue;
		    }
		}
		
		KetMigHelper.service.migPartRevision(seperPartNos[k], seperPartNos[k]);

		if (arg2.equalsIgnoreCase(seperPartNos[k])) {
		    break;
		}
	    }
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

}
