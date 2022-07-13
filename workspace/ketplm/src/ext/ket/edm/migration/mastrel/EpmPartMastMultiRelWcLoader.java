package ext.ket.edm.migration.mastrel;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import wt.epm.EPMDocument;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.base.service.internal.associate.PartEpmRelation;
import ext.ket.shared.log.Kogger;

//ReferenceType 정의 ::
//
//APP_DRAWING: 승인도
//- CATDrawing
//CU_DRAWING: 고객제출도
//- DWG
//RELATED_MODEL : 부품연계3D
//- PRT, ASM
//REF_DRAWING : 제품참고도
//- DWG
//RELATED_DRAWING
//: 제품도, ECAD, 금형도면들
//- DWG, PCB, SCH, PLS, PRT

/**
 * 
 * 부품 마스터와 도면 마스터간의 리비전간 연결을 만든다. - 리비전은 최신만 연결
 * with Excel
 * 
 */
public class EpmPartMastMultiRelWcLoader implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static EpmPartMastMultiRelWcLoader manager = new EpmPartMastMultiRelWcLoader();

    public EpmPartMastMultiRelWcLoader() {

    }

    // windchill ext.ket.part.migration.mastrel.EpmPartMastMultiRelWcLoader Z:\CP_EPM.xlsx
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
	    
	    Kogger.debug(EpmPartMastMultiRelWcLoader.class, "@start:" + toDayTime);
	    EpmPartMastMultiRelWcLoader.manager.saveFromExcel(filePath);
	    
	    toDayTime = "";
	    try {
		
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	       
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(EpmPartMastMultiRelWcLoader.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(EpmPartMastMultiRelWcLoader.class, e);
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
	Transaction trx = null;
	 
	try {

	    SessionHelper.manager.setAdministrator();
		
	    Kogger.debug(getClass(), "## args :" + filePath);
	    
	    EpmPartMastMultiRelLoader loader = new EpmPartMastMultiRelLoader();
	    loader.load(filePath);
	    List<Map> list = loader.getList();
	    
	    for( Map map : list ){
		
		trx = new Transaction();
		trx.start();
		    
		String partNo = (String)map.get(EpmPartMastMultiRelLoader.PART_NO);
		String epmNo = (String)map.get(EpmPartMastMultiRelLoader.EPM_NO);
		
		Kogger.debug(getClass(), "### partNo:" + partNo);
		Kogger.debug(getClass(), "### epmNo:" + epmNo);
		
		WTPart wtPart = PartBaseHelper.service.getLatestPart(partNo.toUpperCase());
		EPMDocument epm = PartBaseHelper.service.getLatestEPM(epmNo.toUpperCase());

		PartEpmRelation partEpmRelation = new PartEpmRelation();
		//**********************************
		// 소스를 필요할 경우에 수정하세요.
		// **********************************

		String referenceType = (String)map.get(EpmPartMastMultiRelLoader.REFER_TYPE);
		int required = Integer.parseInt((String)map.get(EpmPartMastMultiRelLoader.REQ));
		boolean isECAD = "1".equals((String)map.get(EpmPartMastMultiRelLoader.ECAD)); // ECAD 유무

		boolean isUserServerHelper = true;
		partEpmRelation.associateCreate(wtPart, epm, isECAD, required, referenceType, isUserServerHelper);
		    
		trx.commit();
		trx = null;
		
	    }
		
	} catch (Exception e) {
	    
	    Kogger.debug(getClass(), "## 정의되지 않은 에러가 발생했습니다.");
	    Kogger.error(getClass(), e);
	    throw e;
	    
	} finally {
	    
	    if(trx != null)
   		trx.rollback();
	    
	    SessionContext.setContext(sessioncontext);
	}
    }
    

}
