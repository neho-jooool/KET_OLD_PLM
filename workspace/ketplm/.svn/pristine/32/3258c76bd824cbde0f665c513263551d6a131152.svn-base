package ext.ket.edm.migration.mastrel;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import wt.epm.EPMDocument;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.base.service.internal.associate.PartEpmRelation;
import ext.ket.shared.log.Kogger;

/**
 * 부품 마스터와 도면 마스터간의 연결을 만든다.
 * 
 * @클래스명 : EpmPartMastRelater
 * @작성자 : yjlee
 * @작성일 : 2014. 12. 4.
 * @설명 : 
 * @수정이력 - 수정일,수정자,수정내용  					   
 *
 */
public class EpmPartMastOneRelater implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static EpmPartMastOneRelater manager = new EpmPartMastOneRelater();

    public EpmPartMastOneRelater() {

    }
    
//    ReferenceType 정의 ::
//    
//    APP_DRAWING: 승인도
//    - CATDrawing
//    CU_DRAWING: 고객제출도
//    - DWG
//    RELATED_MODEL : 부품연계3D
//    - PRT, ASM
//    REF_DRAWING : 제품참고도
//    - DWG
//    RELATED_DRAWING
//    : 제품도, ECAD, 금형도면들
//    - DWG, PCB, SCH, PLS, PRT

    // windchill ext.ket.edm.migration.mastrel.EpmPartMastRelater PartNo EpmNo ReferenceType Required Ecad
    // windchill ext.ket.edm.migration.mastrel.EpmPartMastRelater PartNo EpmNo ReferenceType Required Ecad
    public static void main(String[] args) {

	try {

	    String arg1 = null;
	    String arg2 = null;

	    if (args == null || args.length < 5)
		throw new Exception("@@ args need !");
	    
	    String toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	
	    Kogger.debug(EpmPartMastOneRelater.class, "@start:" + toDayTime);
	    EpmPartMastOneRelater.manager.saveFromExcel(args);
	    
	    toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(EpmPartMastOneRelater.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(EpmPartMastOneRelater.class, e);
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
	    
	    Kogger.debug(getClass(), "#################################################");
	    Kogger.debug(getClass(), "#################################################");
	    Kogger.debug(getClass(), "################### " + args[0].toUpperCase());
	    Kogger.debug(getClass(), "################### " + args[1].toUpperCase());
	    Kogger.debug(getClass(), "################### " + args[2].toUpperCase());
	    Kogger.debug(getClass(), "################### " + args[3].toUpperCase());
	    Kogger.debug(getClass(), "################### " + args[4].toUpperCase());
	    Kogger.debug(getClass(), "#################################################");
	    Kogger.debug(getClass(), "#################################################");
	    
	    WTPart wtPart = PartBaseHelper.service.getLatestPart(args[0].toUpperCase());
	    EPMDocument epm = PartBaseHelper.service.getLatestEPM(args[1].toUpperCase());
	    
	    PartEpmRelation partEpmRelation = new PartEpmRelation();
	    
	    //**********************************
	    // 소스를 필요할 경우에 수정하세요.
	    //**********************************
	    
	    
	    String referenceType = args[2];
	    int required = Integer.parseInt(args[3]);
	    boolean isECAD = "1".equals(args[4]); // ECAD 유무
	    
	    boolean isUserServerHelper = true;
	    partEpmRelation.associateCreate(wtPart, epm, isECAD, required, referenceType, isUserServerHelper); 
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

}
