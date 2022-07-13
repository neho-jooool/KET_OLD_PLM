package ext.ket.part.migration.base;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import e3ps.sap.ErpPartInterFace;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.base.service.internal.PartDieInfoHandler;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.part.replacePart.util.ReplacePartUtil;
import ext.ket.part.sap.ErpPartHandler;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

public class MigPartNameChangeIdentity implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigPartNameChangeIdentity manager = new MigPartNameChangeIdentity();

    public MigPartNameChangeIdentity() {

    }

    // windchill ext.ket.part.migration.base.MigPartNameChangeIdentity
    // D:\ptc\Windchill_10.2\Windchill\loadFiles\ket\migration\edm\20170810.xlsx

    public static void main(String[] args) {

	try {

	    String filePath = "";

	    if (args == null || args.length < 1)
		throw new Exception("@@ args need !");
	    else {
		filePath = args[0];
	    }

	    Kogger.debug(MigPartNameChangeIdentity.class, "@start");
	    MigPartNameChangeIdentity.manager.saveFromExcel(filePath);
	    Kogger.debug(MigPartNameChangeIdentity.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigPartNameChangeIdentity.class, e);
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

	    if (dataFile == null) {
		throw new Exception("@@ 해당 경로에 파일이 존재하지 않습니다 ! [ " + filePath + " ]");
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
	    ErpPartInterFace erpIf = new ErpPartInterFace();
	    PartDieInfoHandler partInfoHandler = new PartDieInfoHandler();
	    for (int i = startRowNo; i <= rowSize; i++) {
		excel.setRow(i);
		String partNo = excel.getStrValue(0);
		String newpartName = excel.getStrValue(1);

		WTPartMaster partMaster = PartBaseHelper.service.getMaster(partNo);

		if (partMaster != null) {

		    WTPart part = PartBaseHelper.service.getLatestPart(partNo);

		    WTPartMasterIdentity identity = (WTPartMasterIdentity) partMaster.getIdentificationObject();

		    if (StringUtils.isNotEmpty(newpartName)) {
			identity.setName(newpartName);
		    }

		    partMaster = (WTPartMaster) IdentityHelper.service.changeIdentity(partMaster, identity);

		    Map<String, Object> reqMap = new HashMap<String, Object>();
		    reqMap.put("partNo", part.getNumber());
		    ReplacePartUtil.manager.syncKETPartInfo(reqMap);

		    // 부품연관된 프로젝트 정보 업데이트
		    partInfoHandler.updatePartDieInfo(part);

		    if (part.getLifeCycleState().toString().equals("APPROVED")) {
			try {
			    List<WTPart> partList = new ArrayList<WTPart>();
			    partList.add(part);

			    String ecoNo = ""; // partBaseDao.searchEONo(branchId); ECO-1410-222
			    Map<String, String> workerNameMap = new HashMap<String, String>();
			    ErpPartHandler erpPartHandler = new ErpPartHandler();

			    Map resultMap = erpIf.sendPartInfoToErp(partList, ecoNo, workerNameMap, false);

			    boolean success = (Boolean) resultMap.get(ErpPartInterFace.SUCCESS);
			    List<String> errorLogList = (List<String>) resultMap.get(ErpPartInterFace.PLM_ERROR_LIST);
			    String erpMsg = (String) resultMap.get(ErpPartInterFace.ERP_ERROR_MSG);

			    if (!success) {

				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				System.out.println("$$$$$$$$$$$$$$$$ ERP IF ERROR $$$$$$$$$$$$$$$$$");
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				System.out.println(erpMsg);
				for (String errorLog : errorLogList) {
				    System.out.println(errorLog);
				}
			    }

			} catch (Exception e) {
			    e.printStackTrace();
			}

		    }

		    completeCnt++;
		} else {
		    System.out.println(partNo + " 는 시스템에 존재하지 않습니다.");
		}

	    }

	    trx.commit();

	    System.out.println("총 " + completeCnt + " 건 변경완료 !!");

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

}
