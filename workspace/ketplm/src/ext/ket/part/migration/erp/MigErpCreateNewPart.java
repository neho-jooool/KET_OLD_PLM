package ext.ket.part.migration.erp;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.session.SessionContext;
import wt.session.SessionHelper;


import e3ps.sap.ErpPartInterFace;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.part.migration.dao.StampDaoFactory;
import ext.ket.part.migration.dao.StampDaoManager;
import ext.ket.part.migration.dao.StampRowSetStrategy;
import ext.ket.part.sap.ErpPartHandler;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

public class MigErpCreateNewPart implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigErpCreateNewPart manager = new MigErpCreateNewPart();

    public MigErpCreateNewPart() {

    }

    // windchill ext.ket.part.migration.erp.MigErpCreateNewPart D:\ptc\Windchill_10.2\Windchill\loadFiles\ket\part_erp\20170810.xlsx
    
    // 해당 프로그램의 용도 : ERP에 신규 자재마스터 생성 
    public static void main(String[] args) {

	try {

	    String filePath = null;

	    if (args == null || args.length < 1)
		throw new Exception("@@ args need !");
	    else {
		filePath = args[0];
	    }

	    String toDayTime = "";
	    try {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd:HH-mm-ss");
		toDayTime = sdFormat.format(Calendar.getInstance().getTime());
	    } catch (Exception e) {
		Kogger.error("Exception : " + e.getMessage());
	    }

	    Kogger.debug(MigErpCreateNewPart.class, "@start:" + toDayTime);
	    MigErpCreateNewPart.manager.saveFromExcel(filePath);

	    toDayTime = "";
	    try {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd:HH-mm-ss");
		toDayTime = sdFormat.format(Calendar.getInstance().getTime());
	    } catch (Exception e) {
		Kogger.error("Exception : " + e.getMessage());
	    }

	    Kogger.debug(MigErpCreateNewPart.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(MigErpCreateNewPart.class, e);
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

	    execute(filePath);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    public void execute(String filePath) throws Exception {

	File dataFile = new File(filePath);

	if (DRMHelper.useDrm) {
	    dataFile = DRMHelper.Decryptupload(dataFile, dataFile.getName());
	}
	ExcelHandle excel = ExcelFactory.getInstance().getExcelManager(dataFile);
	int sheetNo = 0;
	excel.setSheet(sheetNo);
	int startRowNo = 1;
	excel.setRow(startRowNo);
	int rowSize = excel.getSheetLastRowNum();
	
	List<WTPart> partList = new ArrayList<WTPart>();

	ErpPartHandler erpPartHandler = new ErpPartHandler();
	ErpPartInterFace erpPartInterFace = new ErpPartInterFace();
	WTPart part = null;
	for (int i = startRowNo; i <= rowSize; i++) {
	    excel.setRow(i);
	    String partNo = excel.getStrValue(0);

	    part = PartBaseHelper.service.getLatestPart(partNo);
	    

	    if (part != null && !erpPartHandler.existErpPart(part.getNumber())) {
		System.out.println("");
		partList.add(part);
	    }
	    
	}

	if (partList.size() > 0) {
	    String ecoNo = this.getNewEcoNumber();
	    Map<String, String> workerNameMap = new HashMap<String, String>();
	    Kogger.biz("부품 Admin ERP I/F 시작");
	    Map resultMap = erpPartInterFace.sendPartInfoToErp(partList, ecoNo, workerNameMap, false);
	    Kogger.biz("부품 Admin ERP I/F 종료");

	    boolean success = (Boolean) resultMap.get(ErpPartInterFace.SUCCESS);
	    List<String> errorLogList = (List<String>) resultMap.get(ErpPartInterFace.PLM_ERROR_LIST);
	    String erpMsg = (String) resultMap.get(ErpPartInterFace.ERP_ERROR_MSG);

	    if (!success) {

		Kogger.debug(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		Kogger.debug(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		Kogger.debug(getClass(), "$$$$$$$$$$$$$$$$ ERP IF ERROR $$$$$$$$$$$$$$$$$");
		Kogger.debug(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		Kogger.debug(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

		Kogger.debug(getClass(), erpMsg);

		for (String errorLog : errorLogList) {
		    Kogger.debug(getClass(), errorLog);
		}

		throw new Exception("ERP Interface Error !!");
	    }

	}

    }
    
    	public String getNewEcoNumber() throws Exception {
	
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	String econo = "";

	try {

	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT fn_get_ecm_numbering('ECO') as econo FROM dual");

	    String query = sql.toString();

	    econo = oDaoManager.queryForObject(query, new StampRowSetStrategy<String>() {
		@Override
		public String mapRow(ResultSet rs) throws SQLException {
		    String number = rs.getString("econo");
		    return number;
		}
	    });
	    
	} catch (Exception e) {
	    throw e;
	} finally {

	}

	return econo;
    }
}
