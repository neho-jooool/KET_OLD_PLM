package ext.ket.edm.migration.approval;

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

public class EpmApprovalWcLoader implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static EpmApprovalWcLoader manager = new EpmApprovalWcLoader();

    public EpmApprovalWcLoader() {

    }

    // windchill ext.ket.edm.migration.approval.EpmApprovalWcLoader 0 Z Y(로그에 결재이력 처리시에만 Y 아니면 N)
    // windchill ext.ket.edm.migration.approval.EpmApprovalWcLoader 0 15 N/ 16 25 N/ 26 5 N/ 6 9 N/ A E N/ F H N/ I N N/ O T N/ U Z N
    public static void main(String[] args) {

	try {

	    String arg1 = null;
	    String arg2 = null;
	    String arg3 = null;

	    if (args == null || args.length < 3)
		throw new Exception("@@ args need !");
	    else{
		arg1 = args[0];
		arg2 = args[1];
		arg3 = args[2];
	    }
	    
	    String toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	
	    Kogger.debug(EpmApprovalWcLoader.class, "@start:" + toDayTime);
	    EpmApprovalWcLoader.manager.saveFromExcel(arg1, arg2, arg3);
	    
	    toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(EpmApprovalWcLoader.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(EpmApprovalWcLoader.class, e);
	}
    }

    public void saveFromExcel(String arg1, String arg2, String arg3) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class, String.class, String.class };
		Object aobj[] = { arg1, arg2, arg3 };

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
	    executeMigration(arg1, arg2, arg3);
	}
    }
    
    public void executeMigration(String arg1, String arg2, String arg3) throws Exception {
	
	SessionContext sessioncontext = SessionContext.newContext();
	try {

	    SessionHelper.manager.setAdministrator();
	    
	    String[] seperEpmNos = new String[]{
        		"0", "10","11","12","13","14","15","16","17","18","19"
        		,"20","21","22","23","24","25","26","27","28","29"
        		,"3","4","5","6","7","8","9"
        		,"A","B","C","D","E","F","G","H","I","J","K","L","M","N"
        		,"O","P","Q","R","S","T","U","V","W","X","Y","Z" 
        		}; // 36 개
	    
	    boolean start = false;
	    
	    for( int k = 0; k < seperEpmNos.length; k++ ){
		
		if(!start){
		    if(arg1.equalsIgnoreCase(seperEpmNos[k])){
			start = true;
		    }else{
			continue;
		    }
		}
		
		KetMigEpmHelper.service.migEpmApproval(seperEpmNos[k], arg3);

		if (arg2.equalsIgnoreCase(seperEpmNos[k])) {
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
