package e3ps.cost.service;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import e3ps.cost.migration.service.KetMigCostHelper;
import ext.ket.shared.log.Kogger;

public class CostBomTreeLoader implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static CostBomTreeLoader manager = new CostBomTreeLoader();

    public CostBomTreeLoader() {

    }

    // windchill e3ps.cost.migration.CostReportLoader d:\ptc\Windchill_10.2\Windchill\loadFiles\ket\cost 0 ADMigrationSheetR03.xlsx
    public static void main(String[] args) {

	try {

	    String filePath = "test";
	    String lastSheet = "test";
	    String fileName = "test";
	    /*
	    if (args == null || args.length < 2)
		throw new Exception("@@ args need !");
	    else{
		filePath = args[0];
		lastSheet = args[1];
		
		if(args.length > 2){
		    fileName = args[2];
		}else{
		    fileName = "";
		}
	    }*/
	    
	    String toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	
	    Kogger.debug(CostBomTreeLoader.class, "@start:" + toDayTime);
	    CostBomTreeLoader.manager.saveFromExcel(filePath, lastSheet, fileName);
	    
	    toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(CostBomTreeLoader.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(CostBomTreeLoader.class, e);
	}
    }

    public void saveFromExcel(String filePath, String lastSheet, String fileName) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class, String.class, String.class };
		Object aobj[] = { filePath, lastSheet, fileName };

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
	    executeMigration(filePath, lastSheet, fileName);
	}
    }

    public void executeMigration(String filePath, String lastSheet, String fileName) throws Exception {

	SessionContext sessioncontext = SessionContext.newContext();
	try {
	    SessionHelper.manager.setAdministrator();
	    KetCostInfoHelper.service.searchPlmFullList("","");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

}
