package ext.ket.part.migration.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.base.service.internal.PartDeleteUtil;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.part.migration.erp.service.KetMigHelper;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

public class PartDeleteByAdmin implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static PartDeleteByAdmin manager = new PartDeleteByAdmin();

    public PartDeleteByAdmin() {

    }
    
    // 부품 일괄 삭제. 엑셀에는 품번만 기입하며 기본적으로 part 상세조회에 있는 연관 탭에 있는 모든 항목을 체크하여 연관데이터가 없으면 삭제처리한다 2017.12.12 황정태 작성 

    // windchill ext.ket.part.migration.base.PartDeleteByAdmin D:\ptc\Windchill_10.2\Windchill\loadFiles\ket\delete_part\delete_wtpart_20171212.xlsx
    

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
	
	    Kogger.debug(PartDeleteByAdmin.class, "@start:" + toDayTime);
	    PartDeleteByAdmin.manager.saveFromExcel(filePath);
	    
	    toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(PartDeleteByAdmin.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(PartDeleteByAdmin.class, e);
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
	BufferedWriter bw = null;
	try {

	    SessionHelper.manager.setAdministrator();
	    
	    File dataFile = new File(filePath);
	    if(DRMHelper.useDrm){
		dataFile = DRMHelper.Decryptupload(dataFile, dataFile.getName());
	    }
	    
	    ExcelHandle excel = ExcelFactory.getInstance().getExcelManager(dataFile);
	    
	    int j = dataFile.getName().lastIndexOf(".");
	    String renameFileName = dataFile.getName().substring(0, j);

	    File outFile = new File("D:\\ptc\\Windchill_10.2\\Windchill\\loadFiles\\ket\\delete_part", renameFileName+".txt");
	    bw = new BufferedWriter(new FileWriter((outFile)));

	    
	    excel.setSheet(0);
	    int startRowNo = 1;
	    excel.setRow(startRowNo);
	    int rowSize = excel.getSheetLastRowNum();
	    String partNo = "";
	    List<String> msgList = null;
	    for (int i = startRowNo; i <= rowSize; i++) {
		excel.setRow(i);
		partNo = excel.getStrValue(0);
		if(StringUtils.isEmpty(partNo)) continue;
		
		WTPart wtpart = PartBaseHelper.service.getLatestPart(partNo);
		if(wtpart != null){
		    msgList = PartDeleteUtil.deleteWTPartMastByAdmin(wtpart);
		    for(String msg : msgList){
			bw.write(msg);
			bw.newLine();
		    }
		}else{
		    bw.write(partNo+" 는 존재하지 않습니다.");
		    bw.newLine();
		}
		
	    }
	    bw.flush();
	    

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    if(bw != null) try {bw.close(); } catch (IOException e) {}
	    
	    SessionContext.setContext(sessioncontext);
	}
    }

}
