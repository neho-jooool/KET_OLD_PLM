/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : BomEcoInfoInterface.java
 * 작성자 : Shin.daebeum
 * 작성일자 : 2010. 12. 02.
 */

package e3ps.sap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import wt.part.WTPart;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;

import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.StringUtil;
import ext.ket.bom.util.KETBomPartUtil;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class BomEcoInfoInterface {
    public static Config conf = ConfigImpl.getInstance();
    public static boolean isERPCheck = conf.getBoolean("ERPCHECK");

    /**
     * 
     * @param headCd
     * @return
     * @메소드명 : getInterFaceResult
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 22.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     * @deprecated
     * 
     */
    public boolean getInterFaceResult(String headCd) {
	boolean reSult = false;
	KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();
	BomEcoInfoInterfaceQry befQry = new BomEcoInfoInterfaceQry();
	String strType = ""; // 제품:P, 금형:D, 금형부품:M
	String itemCode = "";
	String ecoNum = "";

	try {
	    ArrayList<Hashtable> ecoList = befQry.getItemCode(headCd); // ECO에 부품이 여러개가 지정될 수 있으므로

	    for (int inx = 0; inx < ecoList.size(); inx++) {

		Hashtable hash = ecoList.get(inx);
		String itemCd = (String) hash.get("itemcode");
		String econum = (String) hash.get("econum");

		Kogger.info(getClass(), "@@@@@@@@@@@@ ECO getInterFaceResult ERP @@@@@@@@@@@ ");
		Kogger.info(getClass(), "@@@@ itemCd : " + itemCd);
		Kogger.info(getClass(), "@@@@ econum : " + econum);

		WTPart part = kh.searchItem(itemCd);
		strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
		// strType = IBAUtil.getAttrValue(part, "PartType");

		Kogger.info(getClass(), "@@@@ strType : " + strType);
		if (PartUtil.isProductType(strType)) {
		    reSult = getGenInfo(econum, itemCd);
		} else if (strType.equals("D"))// || strType.equals("M")) //M이 올수 있을려나...??
		{
		    reSult = getMoldInfo(econum, itemCd);
		} else {
		    Kogger.info(getClass(), "지정된 값이 없는 PartType 입니다.");
		}
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    reSult = false;
	}
	return reSult;
    }

    public boolean executeSendBomErp(String econum, String ecoItemCode, String ecoCode) {
	boolean result = false;

	try {

	    // I. ecoItemCode를 HEAD로 하위 1레벨만 COMP로 가져온다.
	    BomEcoInfoInterfaceQry bomEcoInfoInterfaceQry = new BomEcoInfoInterfaceQry();
	    Hashtable<String, Vector<Hashtable<String, String>>> ht = bomEcoInfoInterfaceQry.getGenInterfaceData(econum, ecoItemCode, ecoCode);

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

	    String strVal = null;
	    for (int i = 0; i < vc.size(); i++) {
		Hashtable head = (Hashtable) vc.elementAt(i);
		String itemCd = (String) head.get("BOMCODE");

		// ERP BOM 존재여부 확인
		strVal = KETBomHelper.service.getIsBomComponent(itemCd);

		Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		Kogger.info(getClass(), "ERP BOM 존재여부 확인");
		Kogger.info(getClass(), "strVal is " + strVal + ", itemCd is " + itemCd);
		Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");

		break;

	    }

	    Client client = null;
	    IRepository repository = null;

	    // 이미 BOM이 ERP에 구성되어 있을 경우
	    if (strVal != null && !strVal.equals("") && strVal.equals("X")) {

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

    // 제품 BOM I/F
    public boolean getGenInfo(String econum, String ecoItemCode) {
	boolean result = false;

	try {
	    // 삭제 플래그가 있는 bom먼저 인터페이스 한다, 삭제 플래그가 섞여있으면 여기서 아무리 순서를 맞춰도 sap 인터페이스시 순차보장이 안됨..
	    result = executeSendBomErp(econum, ecoItemCode, "Remove");
	    if (!result) {
		return result;
	    }
	    result = executeSendBomErp(econum, ecoItemCode, "NONE");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return result;
    }

    // 금형 BOM I/F
    public boolean getMoldInfo(String econum, String ecoItemCode) {
	boolean result = false;

	KETBomPartUtil pUtil = new KETBomPartUtil();
	try {

	    // I. ecoItemCode를 HEAD로 하위 1레벨만 COMP로 가져온다.
	    BomEcoInfoInterfaceQry bomEcoInfoInterfaceQry = new BomEcoInfoInterfaceQry();
	    Hashtable<String, Vector<Hashtable<String, String>>> ht = bomEcoInfoInterfaceQry.getMoldInterfaceData(econum, ecoItemCode);

	    // III. 부품 인터페이스
	    // III-1. 부품을 전부 묶는다.
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

	    String strVal = null;
	    for (int i = 0; i < vc.size(); i++) {
		Hashtable head = (Hashtable) vc.elementAt(i);
		String itemCd = (String) head.get("BOMCODE");

		// ERP BOM 존재여부 확인
		strVal = KETBomHelper.service.getIsBomComponent(itemCd);

		Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		Kogger.info(getClass(), "ERP BOM 존재여부 확인");
		Kogger.info(getClass(), "strVal is " + strVal + ", itemCd is " + itemCd);
		Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");

		break;

	    }

	    if (vtr.size() == 0) {

		Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		Kogger.info(getClass(), "변경, 추가, 삭제된 BOM이 존재하지 않습니다.");
		Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");

		return true;
	    }

	    Client client = null;
	    IRepository repository = null;

	    // 이미 BOM이 ERP에 구성되어 있을 경우
	    if (strVal != null && !strVal.equals("") && strVal.equals("X")) {

		try {

		    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		    Kogger.info(getClass(), "ERP에 설변 BOM 전송 시작");
		    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");

		    // I. JCO 연결
		    client = RFCConnect.getConnection();
		    client.connect();
		    repository = JCO.createRepository("BFREPOSITORY", client);

		    // IV. BOM 인터페이스
		    IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_CHANGE_BOM");

		    // 금형 BOM Header 정보 보내기
		    Function function = tmpl.getFunction();

		    function.getImportParameterList().setValue("2", "I_ZOPTB"); // BOM 구분 - 1:일반, 2:금형
		    JCO.Table inputTable = function.getTableParameterList().getTable("T_BOMIM");

		    // 금형 BOM Component 정보(모-자 속성정보) 보내기
		    String compdelFlag = "";
		    for (int t = 0; t < vtr.size(); t++) {
			compdelFlag = "";
			Hashtable comp = (Hashtable) vtr.elementAt(t);
			String parent_item = (String) comp.get("PBOMCODE");
			String compQty = (String) comp.get("QUANTITY");
			String ecoCd = (String) comp.get("ECOCODE");
			if (ecoCd.equals("Remove")) {
			    compdelFlag = "X";
			}

			inputTable.appendRow();
			inputTable.setValue(parent_item, "DDCNO"); // 금형번호
			inputTable.setValue((String) comp.get("BOMCODE"), "IDNRK"); // 금형부품번호
			inputTable.setValue("", "EKGRP"); // 구매 그룹
			inputTable.setValue("", "WERKS"); // 플랜트
			inputTable.setValue("", "FINSH"); // BOM단종
			inputTable.setValue((String) comp.get("DESC"), "MAKTX"); // 금형부품설명
			inputTable.setValue("", "ODNRK"); // 금형부품번호
			inputTable.setValue(compQty, "MENGE"); // 수량
			inputTable.setValue((String) comp.get("UNIT"), "MEINS"); // 기본 단위
			inputTable.setValue((String) comp.get("STARTDT"), "DATUV"); // 유효일자

			String materialCode = (String) comp.get("MATERIAL");
			HashMap nCode = new HashMap();
			nCode = (HashMap) pUtil.getNumberCode("SPECMATERIALMOLD");
			inputTable.setValue((String) nCode.get(materialCode), "ATWRT"); // 재질
			Kogger.info(getClass(), "========================>>>>>>>>" + nCode.get(materialCode));

			inputTable.setValue((String) comp.get("HARDNESSFROM"), "HARDN"); // 경도(From)
			inputTable.setValue((String) comp.get("HARDNESSTO"), "HARDT"); // 경도(To)
			inputTable.setValue(compdelFlag, "LOEKZ"); // BOM 삭제표시 (삭제시 - 'X')

			/*
		         * 없어야 한다. inputTable.setValue(econum, "ECONO"); // ECO Number
		         */

			Kogger.info(getClass(), "######## parent_item : " + parent_item);
			Kogger.info(getClass(), "######## BOMCODE : " + (String) comp.get("BOMCODE"));
			Kogger.info(getClass(), "######## DESC : " + (String) comp.get("DESC"));
			// Kogger.info(getClass(), "######## MATERIAL : " + (String) comp.get("MATERIAL"));
			Kogger.info(getClass(), "######## MATERIAL : " + (String) nCode.get(materialCode));
			Kogger.info(getClass(), "######## HARDNESSFROM : " + (String) comp.get("HARDNESSFROM"));
			Kogger.info(getClass(), "######## HARDBESSTO : " + (String) comp.get("HARDBESSTO"));
		    }

		    try {
			client.execute(function);
		    } catch (Exception e) {
			result = false;
			Kogger.info(getClass(), "BomEcoInfoInterface[testBOM]>>> " + e.toString());
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
			// bomEcoInfoInterfaceQry.setFalseFlag(econum, ecoItemCode, "2");
			result = false;
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

		    // 금형 BOM Header 정보 보내기
		    Function function = tmpl.getFunction();

		    function.getImportParameterList().setValue("2", "I_ZOPTB"); // BOM 구분 - 1:일반, 2:금형
		    JCO.Table inputTable = function.getTableParameterList().getTable("T_BOMIM");

		    // 금형 BOM Component 정보(모-자 속성정보) 보내기
		    for (int t = 0; t < vtr.size(); t++) {
			Hashtable comp = (Hashtable) vtr.elementAt(t);
			String parent_item = (String) comp.get("PBOMCODE");
			inputTable.appendRow();
			inputTable.setValue(parent_item, "DDCNO"); // 금형번호
			inputTable.setValue((String) comp.get("BOMCODE"), "IDNRK"); // 금형부품번호
			inputTable.setValue("", "EKGRP"); // 구매그룹
			inputTable.setValue("", "WERKS"); // 플랜드
			inputTable.setValue("", "FINSH"); // BOM 단종
			inputTable.setValue((String) comp.get("DESC"), "MAKTX"); // 금형부품설명
			inputTable.setValue("", "ODNRK"); // 금형부품번호(구금형부품)
			inputTable.setValue((String) comp.get("QUANTITY"), "MENGE"); // 수량
			inputTable.setValue((String) comp.get("UNIT"), "MEINS"); // 기본단위
			inputTable.setValue((String) comp.get("STARTDT"), "DATUV"); // 유효일자
			// inputTable.setValue((String) comp.get("MATERIAL"), "ATWRT"); // 재질
			String materialCode = (String) comp.get("MATERIAL");
			HashMap nCode = new HashMap();
			nCode = (HashMap) pUtil.getNumberCode("SPECMATERIALMOLD");
			inputTable.setValue((String) nCode.get(materialCode), "ATWRT"); // 재질
			Kogger.info(getClass(), "========================>>>>>>>>" + nCode.get(materialCode));

			inputTable.setValue((String) comp.get("HARDNESSFROM"), "HARDN"); // 경도(From)
			inputTable.setValue((String) comp.get("HARDNESSTO"), "HARDT"); // 경도(To)
			inputTable.setValue("", "LOEKZ"); // BOM 삭제표시 (삭제시 - 'X')

			/*
		         * 있으면 안된다. inputTable.setValue(econum, "ECONO"); // ECO Number
		         */

			Kogger.info(getClass(), "######## parent_item : " + parent_item);
			Kogger.info(getClass(), "######## BOMCODE : " + (String) comp.get("BOMCODE"));
			Kogger.info(getClass(), "######## DESC : " + (String) comp.get("DESC"));
			// Kogger.info(getClass(), "######## MATERIAL : " + (String) comp.get("MATERIAL"));
			Kogger.info(getClass(), "######## MATERIAL : " + (String) nCode.get(materialCode));
			Kogger.info(getClass(), "######## HARDNESSFROM : " + (String) comp.get("HARDNESSFROM"));
			Kogger.info(getClass(), "######## HARDNESSTO : " + (String) comp.get("HARDNESSTO"));
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
			// iif.setSuccessFlag(headCd, "Y");
			result = true;
		    } else {

			// DB 작업을 SAP I/F와 분리한다.
			/*
		         * iif.setFalseFlag(headCd,"2");
		         */
			/*
		         * result = false;
		         */

			result = false;
		    }

		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		} finally {
		    if (client != null)
			client.disconnect();
		    repository = null;
		}

	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return result;
    }
}
