package ext.ket.part.migration.base;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

import wt.fc.IdentityHelper;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartMasterIdentity;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

public class MigPartChangeIdentity implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigPartChangeIdentity manager = new MigPartChangeIdentity();

    public MigPartChangeIdentity() {

    }

    // windchill ext.ket.part.migration.base.MigEpmChangeIdentity D:\ptc\Windchill_10.2\Windchill\loadFiles\ket\migration\edm\20170810.xlsx

    public static void main(String[] args) {

	try {
	    	    
	    String filePath = "";
	    
	    if (args == null || args.length < 1)
		throw new Exception("@@ args need !");
	    else{
		filePath = args[0];
	    }
	    
	    Kogger.debug(MigPartChangeIdentity.class, "@start");
	    MigPartChangeIdentity.manager.saveFromExcel(filePath);
	    Kogger.debug(MigPartChangeIdentity.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigPartChangeIdentity.class, e);
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

	    trx = new Transaction();
	    trx.start();
	    
	    
	    File dataFile = new File(filePath);
	    
	    if(dataFile == null){
		throw new Exception("@@ 해당 경로에 파일이 존재하지 않습니다 ! [ "+filePath+" ]");
	    }
	    if (DRMHelper.useDrm) {
		dataFile = DRMHelper.Decryptupload(dataFile, dataFile.getName());
	    }
	    ExcelHandle excel = ExcelFactory.getInstance().getExcelManager(dataFile);
	    int sheetNo = 0;
	    excel.setSheet(sheetNo);
	    int startRowNo = 1;
	    
	    int rowSize = excel.getSheetLastRowNum();
	    int completeCnt = 0;
	    
	    for (int i = startRowNo; i <= rowSize; i++) {
		excel.setRow(i);
		String oldPartNo = excel.getStrValue(0);
		String newPartNo = excel.getStrValue(1);
		String newpartName = excel.getStrValue(2);
		
		WTPartMaster partMaster = PartBaseHelper.service.getMaster(oldPartNo);
		
		if(partMaster != null){
		    
		    WTPart part = PartBaseHelper.service.getLatestPart(oldPartNo);
		    
		    if(part.getLifeCycleState().toString().equals("APPROVED")){
			System.out.println(oldPartNo + " 는 승인된 자재입니다.");
			continue;
		    }
		    
		    part = PartBaseHelper.service.getLatestPart(newPartNo);
		    
		    if(part != null){
			System.out.println("변경하고자 하는 "+newPartNo + " 는 이미 존재하는 번호입니다.");
			continue;
		    }
		    
		    WTPartMasterIdentity identity = (WTPartMasterIdentity) partMaster.getIdentificationObject();

		    
		    if(StringUtils.isNotEmpty(newPartNo)){
			identity.setNumber(newPartNo);
		    }
		    
		    if(StringUtils.isNotEmpty(newpartName)){
			identity.setName(newpartName);
		    }
		    
		    partMaster = (WTPartMaster) IdentityHelper.service.changeIdentity(partMaster, identity);
		     
		    completeCnt++;
		}else{
		    System.out.println(oldPartNo + " 는 시스템에 존재하지 않습니다.");
		}
		
	    }
	    
	    trx.commit();
	    
	    System.out.println("총 "+completeCnt+" 건 변경완료 !!");
	    
	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");


	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

}
