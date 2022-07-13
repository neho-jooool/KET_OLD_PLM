package ext.ket.part.migration.mater;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.State;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.session.SessionContext;
import wt.session.SessionHelper;


import e3ps.common.util.CommonUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.dto.PartBaseDTO;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

public class MigCreateNewPart implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigCreateNewPart manager = new MigCreateNewPart();

    public MigCreateNewPart() {

    }

    // windchill ext.ket.part.migration.mater.MigCreateNewPart D:\ptc\Windchill_10.2\Windchill\loadFiles\ket\part_erp\TEST.xlsx
    
    // 해당 프로그램의 용도 : 신규 자재마스터 생성 
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

	    Kogger.debug(MigCreateNewPart.class, "@start:" + toDayTime);
	    MigCreateNewPart.manager.saveFromExcel(filePath);

	    toDayTime = "";
	    try {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd:HH-mm-ss");
		toDayTime = sdFormat.format(Calendar.getInstance().getTime());
	    } catch (Exception e) {
		Kogger.error("Exception : " + e.getMessage());
	    }

	    Kogger.debug(MigCreateNewPart.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(MigCreateNewPart.class, e);
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
	int startRowNo = 3;
	excel.setRow(startRowNo);
	int rowSize = excel.getSheetLastRowNum();
	
	int checkCnt = 0;
	
	try {
	    for (int i = startRowNo; i <= rowSize; i++) {
		checkCnt = i;
		PartBaseDTO dto = new PartBaseDTO();
		dto.setSpPartType("K");// 포장재
		dto.setSpPartDevLevel("PC004"); // 양산

		excel.setRow(i);
		String classCode = excel.getStrValue(1);// 분류체계 코드
		KETPartClassification classific = PartClazHelper.service.getPartClassificationByClazCode(classCode);
		
		String partName = excel.getStrValue(3); // 품명
		String spProdSize = excel.getStrValue(4); // 규격
		String partDefaultUnit = excel.getStrValue(5);// 기본단위
		String spMaterPurch = excel.getStrValue(6);// 재질
		String spColor = excel.getStrValue(7);// 색상
		String spFestPrevent = excel.getStrValue(8);// 제전방지
		String spManufacturer = excel.getStrValue(9);// 제조사

		dto.setPartNumber("TEMP");
		dto.setClassCode(classCode);
		dto.setPartName(partName);
		dto.setSpProdSize(spProdSize);
		dto.setPartDefaultUnit (partDefaultUnit);
		dto.setSpMaterPurch(spMaterPurch);
		dto.setSpColor(spColor);
		dto.setSpFestPrevent(spFestPrevent);
		dto.setSpManufacturer(spManufacturer);
		dto.setPartClazOid(CommonUtil.getOIDString(classific));
		WTPart part = PartBaseHelper.service.createWTPart(dto);
		LifeCycleHelper.service.setLifeCycleState(part, wt.lifecycle.State.toState("APPROVED"), true);
	    }
	} catch (Exception e) {
	    System.out.println(checkCnt+" 행 실행 중 오류 발생!!!!");
	    throw e;
	}

    }
}
