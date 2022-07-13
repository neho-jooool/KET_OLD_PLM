package ext.ket.edm.migration.revision;

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
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import e3ps.edm.beans.EDMHelper;
import ext.ket.edm.cad2bom.service.internal.EpmInfoUpadator;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.log.Kogger;

/**
 * 
 * 부품 마스터와 도면 마스터간의 리비전간 연결을 만든다. - 리비전은 최신만 연결
 * with Excel
 * 
 */
public class EpmRevReWcLoader implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static EpmRevReWcLoader manager = new EpmRevReWcLoader();

    public EpmRevReWcLoader() {

    }

    // windchill ext.ket.edm.migration.revision.EpmRevReWcLoader D:\epm_rev.xlsx
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
	    
	    Kogger.debug(EpmRevReWcLoader.class, "@start:" + toDayTime);
	    EpmRevReWcLoader.manager.saveFromExcel(filePath);
	    
	    toDayTime = "";
	    try {
		
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	       
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(EpmRevReWcLoader.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(EpmRevReWcLoader.class, e);
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
	    
	    EpmRevReLoader loader = new EpmRevReLoader();
	    loader.load(filePath);
	    List<Map> list = loader.getList();
	    
	    for( Map map : list ){
		
		trx = new Transaction();
		trx.start();
		    
		String epmNo = (String)map.get(EpmRevReLoader.EPM_NO);
		String devLvl = (String)map.get(EpmRevReLoader.DEV_LVL);
		String manageType = (String)map.get(EpmRevReLoader.MNG_TYPE);
		
		Kogger.debug(getClass(), "### epmNo:" + epmNo);
		Kogger.debug(getClass(), "### devLvl:" + devLvl);
		Kogger.debug(getClass(), "### manageType:" + manageType);
		
		EPMDocument epm = PartBaseHelper.service.getLatestEPM(epmNo.toUpperCase());
		
		// 개발단계 업데이트
		EpmInfoUpadator updator = new EpmInfoUpadator();
		if("개발단계".equals(devLvl)){
		    updator.updateIBAValueIncludeEmptyValue(epm, EDMHelper.IBA_DEV_STAGE, devLvl);
		}else if("양산단계".equals(devLvl)){
		    updator.updateIBAValueIncludeEmptyValue(epm, EDMHelper.IBA_DEV_STAGE, devLvl);
		}else if("개발검토단계".equals(devLvl)){
		    updator.updateIBAValueIncludeEmptyValue(epm, EDMHelper.IBA_DEV_STAGE, devLvl);
		}
		
		// 제품/금형 업데이트
		if("제품도면".equals(manageType) || "제품".equals(manageType)){
		    updator.updateIBAValueIncludeEmptyValue(epm, EDMHelper.IBA_CAD_MANAGE_TYPE, "제품도면");
		}else if("금형도면".equals(devLvl)){
		    updator.updateIBAValueIncludeEmptyValue(epm, EDMHelper.IBA_CAD_MANAGE_TYPE, "금형도면");
		}

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
