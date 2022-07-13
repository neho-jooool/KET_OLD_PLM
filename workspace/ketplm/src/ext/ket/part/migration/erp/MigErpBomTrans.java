package ext.ket.part.migration.erp;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.session.SessionContext;
import wt.session.SessionHelper;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;

import e3ps.bom.service.KETBomHelper;
import e3ps.common.util.StringUtil;
import e3ps.sap.BomEcoInfoInterfaceQry;
import e3ps.sap.RFCConnect;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.part.sap.ErpPartHandler;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

public class MigErpBomTrans implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigErpBomTrans manager = new MigErpBomTrans();

    public MigErpBomTrans() {

    }

    // windchill ext.ket.part.migration.erp.MigErpBomTrans D:\ptc\Windchill_10.2\Windchill\loadFiles\ket\part_erp\20170810.xlsx

    // 해당 프로그램의 용도 : ERP에 BOM 전송
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

	    Kogger.debug(MigErpBomTrans.class, "@start:" + toDayTime);
	    MigErpBomTrans.manager.saveFromExcel(filePath);

	    toDayTime = "";
	    try {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd:HH-mm-ss");
		toDayTime = sdFormat.format(Calendar.getInstance().getTime());
	    } catch (Exception e) {
		Kogger.error("Exception : " + e.getMessage());
	    }

	    Kogger.debug(MigErpBomTrans.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(MigErpBomTrans.class, e);
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

	ErpPartHandler erpPartHandler = new ErpPartHandler();

	WTPart part = null;
	for (int i = startRowNo; i <= rowSize; i++) {
	    excel.setRow(i);
	    String econum = excel.getStrValue(0);
	    String ecoItemCode = excel.getStrValue(1);
	    String gubun = excel.getStrValue(2);

	    part = PartBaseHelper.service.getLatestPart(ecoItemCode);

	    if (part != null && erpPartHandler.existErpPart(part.getNumber())) {
		getGenInfo(econum, ecoItemCode, gubun);
	    }

	}

    }

    public boolean getGenInfo(String econum, String ecoItemCode, String strVal) {
	boolean result = false;

	try {

	    // I. ecoItemCode를 HEAD로 하위 1레벨만 COMP로 가져온다.
	    BomEcoInfoInterfaceQry bomEcoInfoInterfaceQry = new BomEcoInfoInterfaceQry();
	    Hashtable<String, Vector<Hashtable<String, String>>> ht = bomEcoInfoInterfaceQry.getGenInterfaceData(econum, ecoItemCode, "");

	    Vector<Hashtable<String, String>> vc = (Vector<Hashtable<String, String>>) ht.get("HEAD");
	    Vector<Hashtable<String, String>> vtr = (Vector<Hashtable<String, String>>) ht.get("COMP");

	    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
	    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
	    Kogger.info(getClass(), "econum is \n" + econum);
	    Kogger.info(getClass(), "ecoItemCode is \n" + ecoItemCode);
	    Kogger.info(getClass(), "vc is \n" + vc.toString());
	    Kogger.info(getClass(), "vtr is \n" + vtr.toString());
	    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
	    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");

	    if (vtr.size() == 0) {

		Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		Kogger.info(getClass(), "변경, 추가, 삭제된 BOM이 존재하지 않습니다.");
		Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");

		return true;
	    }

	    /*
	     * for (int i = 0; i < vc.size(); i++) { Hashtable head = (Hashtable) vc.elementAt(i); String itemCd = (String)
	     * head.get("BOMCODE");
	     * 
	     * // ERP BOM 존재여부 확인 strVal = KETBomHelper.service.getIsBomComponent(itemCd);
	     * 
	     * Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE"); Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
	     * Kogger.info(getClass(), "ERP BOM 존재여부 확인"); Kogger.info(getClass(), "strVal is " + strVal + ", itemCd is " + itemCd);
	     * Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE"); Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
	     * 
	     * break;
	     * 
	     * }
	     */

	    Client client = null;
	    IRepository repository = null;

	    // 이미 BOM이 ERP에 구성되어 있을 경우
	    if (strVal != null && !strVal.equals("") && strVal.equals("UPDATE")) {

		try {

		    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		    Kogger.info(getClass(), "ERP에 설변 BOM 전송 시작");
		    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");

		    // ///////////////////////////////////////////////////////////////////////////
		    // 제품 BOM Header 정보 보내기
		    // I. JCO 연결
		    client = RFCConnect.getConnection();
		    client.connect();
		    repository = JCO.createRepository("BFREPOSITORY", client);

		    // IV. BOM 인터페이스
		    IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_CHANGE_BOM");

		    String parent_item = "";
		    String itemCd = "";
		    String bomText = "";
		    String headdelFlag = "";
		    String compdelFlag = "";
		    String compQty = "";
		    String ecoCd = "";

		    // 제품 BOM Header 정보 보내기
		    for (int i = 0; i < vc.size(); i++) {
			Hashtable head = (Hashtable) vc.elementAt(i);
			itemCd = (String) head.get("BOMCODE");
			bomText = (String) head.get("BOMTEXT");
			ecoCd = (String) head.get("ECOCODE");
			if (ecoCd.equals("Remove")) {
			    headdelFlag = "X";
			}

			Function function = tmpl.getFunction();

			function.getImportParameterList().setValue("1", "I_ZOPTB"); // BOM 구분 - 1:일반, 2:금형
			JCO.Structure inputStructure = function.getImportParameterList().getStructure("I_BOMHN");

			inputStructure.setValue(itemCd, "MATNR"); // 자재 번호
			inputStructure.setValue("", "WERKS"); // 플랜트
			inputStructure.setValue((String) head.get("BOMUSE"), "STLAN"); // BOM 용도 (2: 개발BOM 으로 셋팅)
			inputStructure.setValue((String) head.get("STARTDT"), "DATUV"); // 효력 시작일
			inputStructure.setValue((String) head.get("UNIT"), "BMEIN"); // BOM의 기본단위
			inputStructure.setValue((String) head.get("QUANTITY"), "BMENG"); // 기준 수량
			inputStructure.setValue("01", "STLST"); // BOM 상태 (01:Active 로 셋팅)
			inputStructure.setValue(bomText, "ZTEXT"); // BOM 텍스트 (변경사유 기술함)
			inputStructure.setValue((String) head.get("SUBSTITUDETEXT"), "STKTX"); // 대체 BOM 텍스트 :: 대체부품정보 전달
			inputStructure.setValue(headdelFlag, "LOEKZ"); // BOM 삭제표시 (삭제시 - 'X')

			function.getImportParameterList().setValue(inputStructure, "I_BOMHN");

			Kogger.info(getClass(), "@@@@@@@@ itemCd : " + itemCd);

			JCO.Table inputTable = function.getTableParameterList().getTable("T_BOMIN");

			// 제품 BOM Component 정보(모-자 속성정보) 보내기 (헤더가 하나이므로 아마 1번만 돌아갈거임)
			for (int t = 0; t < vtr.size(); t++) {
			    compdelFlag = "";
			    Hashtable comp = (Hashtable) vtr.elementAt(t);
			    parent_item = (String) comp.get("PBOMCODE");
			    if (parent_item.equals(itemCd)) {
				compQty = (String) comp.get("QUANTITY");
				ecoCd = (String) comp.get("ECOCODE");
				if (ecoCd.equals("Remove")) {
				    compdelFlag = "X";
				}
				Kogger.info(getClass(), "@@@@@@ 삭제플래그 값 : " + compdelFlag);

				Kogger.info(getClass(), "@@@@@@ comp.get(ICT) : " + (String) comp.get("ICT"));

				inputTable.appendRow();
				inputTable.setValue(parent_item, "MATNR"); // 자재 번호
				inputTable.setValue((String) comp.get("BOMCODE"), "IDNRK"); // BOM 구성부품 (필수입력)

				if (ecoCd.equals("Remove")) {
				    inputTable.setValue((String) comp.get("DELICT"), "POSTP"); // 품목 범주(BOM) -> 'L'(재고품목)로 셋팅
				} else {
				    inputTable.setValue((String) comp.get("ICT"), "POSTP"); // 품목 범주(BOM) -> 'L'(재고품목)로 셋팅
				}
				inputTable.setValue("", "POSNR"); // BOM 품목번호
				inputTable.setValue("", "SORTF"); // 정렬 문자열
				inputTable.setValue((String) comp.get("UNIT"), "MEINS"); // 구성부품단위
				inputTable.setValue(compQty, "MENGE"); // 구성부품수량
				inputTable.setValue((String) comp.get("SUBSTITUDETEXT"), "POTX1"); // BOM 품목 텍스트(라인 1) :: 대체부품정보 전달
				inputTable.setValue("", "POTX2"); // BOM 품목 텍스트(라인 2)
				inputTable.setValue("", "LGORT"); // 생산오더에 대한 출고위치
				inputTable.setValue((String) comp.get("STARTDT"), "DATUV"); // 효력 시작일
				inputTable.setValue(compdelFlag, "LOEKZ"); // BOM 삭제표시 (삭제시 - 'X')
				inputTable.setValue(econum, "ECONO"); // ECO Number

				String afterict = StringUtil.checkNull((String) comp.get("ICT"));
				String beforeict = StringUtil.checkNull((String) comp.get("DELICT"));

				if (ecoCd.equals("Update")) {
				    if (!afterict.equals(beforeict)) {
					inputTable.appendRow();
					inputTable.setValue(parent_item, "MATNR"); // 자재 번호
					inputTable.setValue((String) comp.get("BOMCODE"), "IDNRK"); // BOM 구성부품 (필수입력)
					inputTable.setValue((String) comp.get("DELICT"), "POSTP");
					inputTable.setValue("", "POSNR"); // BOM 품목번호
					inputTable.setValue("", "SORTF"); // 정렬 문자열
					inputTable.setValue((String) comp.get("UNIT"), "MEINS"); // 구성부품단위
					inputTable.setValue(compQty, "MENGE"); // 구성부품수량
					inputTable.setValue((String) comp.get("SUBSTITUDETEXT"), "POTX1"); // BOM 품목 텍스트(라인 1) :: 대체부품정보 전달
					inputTable.setValue("", "POTX2"); // BOM 품목 텍스트(라인 2)
					inputTable.setValue("", "LGORT"); // 생산오더에 대한 출고위치
					inputTable.setValue((String) comp.get("STARTDT"), "DATUV"); // 효력 시작일
					inputTable.setValue("X", "LOEKZ"); // BOM 삭제표시 (삭제시 - 'X')
					inputTable.setValue(econum, "ECONO"); // ECO Number
				    }
				}

				// Kogger.info(getClass(), "######## parent_item : "+parent_item);
				// Kogger.info(getClass(), "######## BOMCODE : "+(String)comp.get("BOMCODE"));
			    }
			}
			try {
			    client.execute(function);
			} catch (Exception e) {
			    result = false;
			    Kogger.info(getClass(), "BomEcoInfoInterface [ getGenInfo ] >>> " + e.toString());
			}
			String r = (String) function.getExportParameterList().getValue("E_RETURN");
			int c = function.getExportParameterList().getInt("E_CNT");
			String r_msg = (String) function.getExportParameterList().getValue("E_MSG");

			Kogger.info(getClass(), "E_RETURN<<<<<< " + r);
			Kogger.info(getClass(), "E_CNT<<<<<< " + c);
			Kogger.info(getClass(), "E_MSG<<<<<< " + r_msg);

			if ((r.toUpperCase()).equals("S")) {

			    result = true;

			    // 변경사유가 'BOM 양산이관' 인 경우 자재 Master 를 Update 하는 인터페이스 호출
			    // if (bomText.indexOf("BOM 양산이관") > -1 || bomText.indexOf("양산 이관") > -1 || bomText.indexOf("양산") > -1) {
			    if (bomText.indexOf("양산") > -1) {
				Kogger.info(getClass(), ">>>>>>>>>>>>>>제품 BOM 양산이관시 자재마스터 Update 인터페이스 호출 시작!!");
				KETBomHelper.service.updateItemMaster(econum, ecoItemCode);
				Kogger.info(getClass(), ">>>>>>>>>>>>>>제품 BOM 양산이관시 자재마스터 Update 인터페이스 호출 완료!!");
			    }

			} else {

			    // DB 작업을 SAP I/F와 분리한다.
			    // bomEcoInfoInterfaceQry.setFalseFlag(econum, ecoItemCode, "2");
			    result = false;
			    break;
			}
		    } // for end

		    if (result) {
			Kogger.info(getClass(), "SUCCESSSUCCESSSUCCESSSUCCESS");
			Kogger.info(getClass(), "SUCCESSSUCCESSSUCCESSSUCCESS");
			Kogger.info(getClass(), "ERP에 설변 BOM 전송 성공");
			Kogger.info(getClass(), "SUCCESSSUCCESSSUCCESSSUCCESS");
			Kogger.info(getClass(), "SUCCESSSUCCESSSUCCESSSUCCESS");
		    }

		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		} finally {
		    if (client != null)
			client.disconnect();
		    repository = null;
		}

	    }
	    // ERP에 구성되어 있지 않는 BOM일 경우
	    else {

		try {

		    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		    Kogger.info(getClass(), "ERP에 초도 BOM 전송 시작");
		    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");

		    // I. JCO 연결
		    client = RFCConnect.getConnection();
		    client.connect();
		    repository = JCO.createRepository("BFREPOSITORY", client);

		    // IV. BOM 인터페이스
		    IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_CREATE_BOM");

		    // 제품 BOM Header 정보 보내기
		    RENEW: for (int i = 0; i < vc.size(); i++) {
			Hashtable head = (Hashtable) vc.elementAt(i);
			String itemCd = (String) head.get("BOMCODE");
			Function function = tmpl.getFunction();

			function.getImportParameterList().setValue("1", "I_ZOPTB"); // BOM 구분 - 1:일반, 2:금형
			JCO.Structure inputStructure = function.getImportParameterList().getStructure("I_BOMHN");

			inputStructure.setValue(itemCd, "MATNR"); // 자재번호
			inputStructure.setValue("", "WERKS"); // 플랜트
			inputStructure.setValue((String) head.get("BOMUSE"), "STLAN"); // BOM 용도 (2:개발BOM 으로 셋팅함)
			inputStructure.setValue("", "STLNR"); // BOM (사용안함)
			inputStructure.setValue((String) head.get("STARTDT"), "DATUV"); // 효력시작일 (현재일로 셋팅)
			inputStructure.setValue((String) head.get("UNIT"), "BMEIN"); // BOM 기본단위
			inputStructure.setValue((String) head.get("QUANTITY"), "BMENG"); // 기준수량
			inputStructure.setValue("01", "STLST"); // BOM 상태 (01:Active 로 셋팅)
			inputStructure.setValue((String) head.get("BOMTEXT"), "ZTEXT"); // BOM Text
			inputStructure.setValue((String) head.get("SUBSTITUDETEXT"), "STKTX"); // 대체 BOM Text :: 대체부품정보 전달
			inputStructure.setValue("", "LOEKZ"); // 삭제표시 (사용안함)
			function.getImportParameterList().setValue(inputStructure, "I_BOMHN");

			Kogger.info(getClass(), "@@@@@@@@ itemCd : " + itemCd);

			JCO.Table inputTable = function.getTableParameterList().getTable("T_BOMIN");

			// 제품 BOM Component 정보(모-자 속성정보) 보내기
			for (int t = 0; t < vtr.size(); t++) {
			    Hashtable comp = (Hashtable) vtr.elementAt(t);
			    String parent_item = (String) comp.get("PBOMCODE");
			    if (parent_item.equals(itemCd)) {
				inputTable.appendRow();
				inputTable.setValue(parent_item, "MATNR"); // 자재번호
				inputTable.setValue("", "STLNR"); // BOM 번호 (사용안함)
				inputTable.setValue((String) comp.get("BOMCODE"), "IDNRK"); // BOM 구성부품
				inputTable.setValue((String) comp.get("ICT"), "POSTP"); // 품목범주 (L:재고품목 으로 셋팅)
				inputTable.setValue("", "POSNR"); // BOM 품목번호
				inputTable.setValue("", "SORTF"); // 정렬 문자열
				inputTable.setValue((String) comp.get("UNIT"), "MEINS"); // 구성부품단위
				inputTable.setValue((String) comp.get("QUANTITY"), "MENGE"); // 구성부품수량
				inputTable.setValue((String) comp.get("SUBSTITUDETEXT"), "POTX1"); // BOM 품목 텍스트(라인 1) :: 대체부품정보 전달
				inputTable.setValue("", "POTX2"); // BOM 품목 텍스트(라인 2)
				inputTable.setValue("", "LGORT"); // 생산오더에 대한 출고위치
				inputTable.setValue((String) comp.get("STARTDT"), "DATUV"); // 효력 시작일 (현재일로 셋팅)
				inputTable.setValue("", "LOEKZ"); // BOM 삭제 표시 (사용안함)
				inputTable.setValue(econum, "ECONO"); // ECO Number

				Kogger.info(getClass(), "######## parent_item : " + parent_item);
				Kogger.info(getClass(), "######## BOMCODE : " + (String) comp.get("BOMCODE"));
			    }
			}

			// inputStructure(모)에 inputTable(자식)이 없으면 SAP에 보내지 아니한다.
			if (inputTable.getNumRows() == 0) {
			    continue RENEW;
			}

			try {
			    client.execute(function);
			} catch (Exception e) {
			    result = false;
			    Kogger.info(getClass(), "BomInfoInterface[testBOM]>>> " + e.toString());
			}
			String r = (String) function.getExportParameterList().getValue("E_RETURN");
			int c = function.getExportParameterList().getInt("E_CNT");
			String r_msg = (String) function.getExportParameterList().getValue("E_MSG");

			Kogger.info(getClass(), "E_RETURN<<<<<< " + r);
			Kogger.info(getClass(), "E_CNT<<<<<< " + c);
			Kogger.info(getClass(), "E_MSG<<<<<< " + r_msg);

			if ((r.toUpperCase()).equals("S")) {
			    // iif.setSuccessFlag(headCd,"Y");
			    result = true;
			} else {

			    // DB 작업을 SAP I/F와 분리한다.
			    /*
		             * iif.setFalseFlag(headCd, "2");
		             */
			    result = false;
			    break;
			}

		    } // for end

		    if (result) {
			Kogger.info(getClass(), "SUCCESSSUCCESSSUCCESSSUCCESS");
			Kogger.info(getClass(), "SUCCESSSUCCESSSUCCESSSUCCESS");
			Kogger.info(getClass(), "ERP에 초도 BOM 전송 성공");
			Kogger.info(getClass(), "SUCCESSSUCCESSSUCCESSSUCCESS");
			Kogger.info(getClass(), "SUCCESSSUCCESSSUCCESSSUCCESS");

		    }

		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		} finally {
		    if (client != null)
			client.disconnect();
		    repository = null;
		}

		/*
	         * JCO.Table outputTable = function.getTableParameterList().getTable( "T_STPO" ); String changeNo = ""; for( int i = 0; i <
	         * outputTable.getNumRows(); i++ ) { outputTable.setRow( i ); changeNo = outputTable.getValue( "CHANGE_NO" ).toString();
	         * 
	         * Kogger.info(getClass(), "Result Change No. " + (i+1) + " <<<<<< "+ changeNo ); }
	         */

	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return result;
    }
}
