package ext.ket.part.migration.base;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

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
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

public class MigEpmChangeIdentity implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigEpmChangeIdentity manager = new MigEpmChangeIdentity();

    public MigEpmChangeIdentity() {

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
	    
	    Kogger.debug(MigEpmChangeIdentity.class, "@start");
	    MigEpmChangeIdentity.manager.saveFromExcel(filePath);
	    Kogger.debug(MigEpmChangeIdentity.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigEpmChangeIdentity.class, e);
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
	    excel.setRow(startRowNo);
	    int rowSize = excel.getSheetLastRowNum();
	    int completeCnt = 0;
	    
	    for (int i = startRowNo; i <= rowSize; i++) {
		String oldEmpNo = excel.getStrValue(0);
		String newEmpNo = excel.getStrValue(1);
		String newEmpName = excel.getStrValue(2);
		
		EPMDocumentMaster empMaster = PartBaseHelper.service.getEpmMaster(oldEmpNo);
		
		if(empMaster != null){
		    EPMDocumentMasterIdentity identity = (EPMDocumentMasterIdentity) empMaster.getIdentificationObject();
		    
		    if(StringUtils.isNotEmpty(newEmpNo)){
			identity.setNumber(newEmpNo);
		    }
		    
		    if(StringUtils.isNotEmpty(newEmpName)){
			identity.setName(newEmpName);
		    }
		    
		    empMaster = (EPMDocumentMaster) IdentityHelper.service.changeIdentity(empMaster, identity);    
		    completeCnt++;
		}else{
		    System.out.println(oldEmpNo + " 는 시스템에 존재하지 않습니다.");
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
