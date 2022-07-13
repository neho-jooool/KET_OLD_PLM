/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : BomInfoInterface.java
 * 작성자 : Shin.
 * 작성일자 : 2010. 12. 02.
 */

package e3ps.sap;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import wt.part.WTPart;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;

import e3ps.bom.common.iba.IBAUtil;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import ext.ket.bom.util.KETBomPartUtil;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class BomInfoInterface {
    public static Config conf = ConfigImpl.getInstance();
    public static boolean isERPCheck = conf.getBoolean("ERPCHECK");

    public boolean getInterFaceResult(String headCd) {
	boolean reSult = false;
	KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();
	String strType = ""; // 제품:P, 금형:D, 금형부품:M
	try {
	    InfoInterfaceQry iifQry = new InfoInterfaceQry();
	    String itemCd = iifQry.getItemCode(headCd);
	    WTPart part = kh.searchItem(itemCd);
	    strType = IBAUtil.getAttrValue(part, "PartType");

	    if (PartUtil.isProductType(strType)) {
		if (getGenInfo(headCd)) {
		    reSult = true;
		} else {
		    reSult = false;
		}
	    } else if (strType.equals("D"))// || strType.equals("M")) //M이 올수 있을려나...??
	    {
		if (getMoldInfo(headCd)) {
		    reSult = true;
		} else {
		    reSult = false;
		}
	    } else {
		Kogger.debug(getClass(), "지정된 값이 없는 PartType 입니다.");
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    reSult = false;
	}
	return reSult;
    }

    /**
     * 
     * 제품 BOM I/F
     * 
     * @param headCd
     * @return
     * @메소드명 : getGenInfo
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 10. 6.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     * @deprecated
     * 
     */
    public boolean getGenInfo(String headCd) {
	boolean result = false;
	Client client = null;
	IRepository repository = null;

	try {
	    InfoInterfaceQry iif = new InfoInterfaceQry();
	    Hashtable ht = iif.getGenInterfaceData(headCd);
	    client = RFCConnect.getConnection();
	    client.connect();
	    repository = JCO.createRepository("BFREPOSITORY", client);
	    IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_CREATE_BOM");
	    Vector vc = (Vector) ht.get("HEAD");
	    Vector vtr = (Vector) ht.get("COMP");

	    // 제품 BOM Header 정보 보내기
	    for (int i = 0; i < vc.size(); i++) {
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

		Kogger.debug(getClass(), "@@@@@@@@ itemCd : " + itemCd);

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

			Kogger.debug(getClass(), "######## parent_item : " + parent_item);
			Kogger.debug(getClass(), "######## BOMCODE : " + (String) comp.get("BOMCODE"));
		    }
		}

		try {
		    client.execute(function);
		} catch (Exception e) {
		    result = false;
		    Kogger.debug(getClass(), "BomInfoInterface[testBOM]>>> " + e.toString());
		}
		String r = (String) function.getExportParameterList().getValue("E_RETURN");
		int c = function.getExportParameterList().getInt("E_CNT");
		String r_msg = (String) function.getExportParameterList().getValue("E_MSG");

		Kogger.debug(getClass(), "E_RETURN<<<<<< " + r);
		Kogger.debug(getClass(), "E_CNT<<<<<< " + c);
		Kogger.debug(getClass(), "E_MSG<<<<<< " + r_msg);

		if ((r.toUpperCase()).equals("S")) {
		    // iif.setSuccessFlag(headCd,"Y");
		    result = true;
		} else {
		    iif.setFalseFlag(headCd, "2");
		    result = false;
		    break;
		}

	    } // for end

	    /*
	     * JCO.Table outputTable = function.getTableParameterList().getTable( "T_STPO" ); String changeNo = ""; for( int i = 0; i <
	     * outputTable.getNumRows(); i++ ) { outputTable.setRow( i ); changeNo = outputTable.getValue( "CHANGE_NO" ).toString();
	     * 
	     * Kogger.debug(getClass(),  "Result Change No. " + (i+1) + " <<<<<< "+ changeNo ); }
	     */
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    client.disconnect();
	    repository = null;
	}

	return result;
    }

    /**
     * 
     * 금형 BOM I/F
     * 
     * @param headCd
     * @return
     * @메소드명 : getMoldInfo
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 10. 6.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     * @deprecated
     * 
     */
    public boolean getMoldInfo(String headCd) {
	boolean result = false;
	Client client = null;
	IRepository repository = null;
	KETBomPartUtil pUtil = new KETBomPartUtil();

	try {
	    InfoInterfaceQry iif = new InfoInterfaceQry();
	    Hashtable ht = iif.getMoldInterfaceData(headCd);
	    client = RFCConnect.getConnection();
	    client.connect();

	    repository = JCO.createRepository("BFREPOSITORY", client);

	    IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_CREATE_BOM");

	    Vector vc = (Vector) ht.get("HEAD");
	    Vector vtr = (Vector) ht.get("COMP");

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

		String materialCode = (String) comp.get("MATERIAL");
		HashMap nCode = new HashMap();
		nCode = (HashMap) pUtil.getNumberCode("SPECMATERIALMOLD");
		inputTable.setValue((String) nCode.get(materialCode), "ATWRT"); // 재질
		Kogger.debug(getClass(), "========================>>>>>>>>" + nCode.get(materialCode));

		inputTable.setValue((String) comp.get("HARDNESSFROM"), "HARDN"); // 경도(From)
		inputTable.setValue((String) comp.get("HARDNESSTO"), "HARDT"); // 경도(To)
		inputTable.setValue("", "LOEKZ"); // BOM 삭제표시 (삭제시 - 'X')

		Kogger.debug(getClass(), "######## parent_item : " + parent_item);
		Kogger.debug(getClass(), "######## BOMCODE : " + (String) comp.get("BOMCODE"));
		Kogger.debug(getClass(), "######## DESC : " + (String) comp.get("DESC"));
		// Kogger.debug(getClass(), "######## MATERIAL : " + (String) comp.get("MATERIAL"));
		Kogger.debug(getClass(), "######## MATERIAL : " + (String) nCode.get(materialCode));
		Kogger.debug(getClass(), "######## HARDNESSFROM : " + (String) comp.get("HARDNESSFROM"));
		Kogger.debug(getClass(), "######## HARDNESSTO : " + (String) comp.get("HARDNESSTO"));
	    }

	    try {
		client.execute(function);
	    } catch (Exception e) {
		result = false;
		Kogger.debug(getClass(), "BomInfoInterface[testBOM]>>> " + e.toString());
	    }

	    String r = (String) function.getExportParameterList().getValue("E_RETURN");
	    int c = function.getExportParameterList().getInt("E_CNT");
	    String r_msg = (String) function.getExportParameterList().getValue("E_MSG");

	    Kogger.debug(getClass(), "E_RETURN<<<<<< " + r);
	    Kogger.debug(getClass(), "E_CNT<<<<<< " + c);
	    Kogger.debug(getClass(), "E_MSG<<<<<< " + r_msg);

	    if ((r.toUpperCase()).equals("S")) {
		// iif.setSuccessFlag(headCd, "Y");
		result = true;
	    } else {
		// iif.setFalseFlag(headCd,"2");
		result = true;
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    client.disconnect();
	    repository = null;
	}
	return result;
    }

    /**
     * 
     * 제품 BOM I/F (초도일 경우)
     * 
     * @param econum
     * @param headCd
     * @return
     * @메소드명 : getGenInfo2
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 10. 6.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public boolean getGenInfo2(String econum, String headCd) {
	boolean result = false;

	Client client = null;
	IRepository repository = null;
	try {

	    // I. ecoItemCode를 HEAD로 하위 1레벨만 COMP로 가져온다.
	    InfoInterfaceQry iif = new InfoInterfaceQry();
	    Hashtable ht = iif.getGenInterfaceData(headCd);

	    Vector<Hashtable<String, String>> vc = (Vector<Hashtable<String, String>>) ht.get("HEAD");
	    Vector<Hashtable<String, String>> vtr = (Vector<Hashtable<String, String>>) ht.get("COMP");

	    Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEE");
	    Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEE");
	    Kogger.debug(getClass(), "econum is \n" + econum);
	    Kogger.debug(getClass(), "headCd is \n" + headCd);
	    Kogger.debug(getClass(), "vc is \n" + vc.toString());
	    Kogger.debug(getClass(), "vtr is \n" + vtr.toString());
	    Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEE");
	    Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEE");

	    if (vtr.size() == 0) {

		Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		Kogger.debug(getClass(), "변경, 추가, 삭제된 BOM이 존재하지 않습니다.");
		Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEE");
		Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEE");

		return true;
	    }

	    /*
	     * String strVal = null; for (int i = 0; i < vc.size(); i++) { Hashtable head = (Hashtable) vc.elementAt(i); String itemCd =
	     * (String) head.get("BOMCODE");
	     * 
	     * // ERP BOM 존재여부 확인 strVal = KETBomHelper.service.getIsBomComponent(itemCd);
	     * 
	     * Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEE"); Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEE");
	     * Kogger.debug(getClass(), "ERP BOM 존재여부 확인"); Kogger.debug(getClass(), "strVal is " + strVal + ", itemCd is " + itemCd);
	     * Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEE"); Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEE");
	     * 
	     * break;
	     * 
	     * }
	     */

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

		Kogger.debug(getClass(), "@@@@@@@@ itemCd : " + itemCd);

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

			Kogger.debug(getClass(), "######## parent_item : " + parent_item);
			Kogger.debug(getClass(), "######## BOMCODE : " + (String) comp.get("BOMCODE"));
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
		    Kogger.debug(getClass(), "BomInfoInterface[testBOM]>>> " + e.toString());
		}
		String r = (String) function.getExportParameterList().getValue("E_RETURN");
		int c = function.getExportParameterList().getInt("E_CNT");
		String r_msg = (String) function.getExportParameterList().getValue("E_MSG");

		Kogger.debug(getClass(), "E_RETURN<<<<<< " + r);
		Kogger.debug(getClass(), "E_CNT<<<<<< " + c);
		Kogger.debug(getClass(), "E_MSG<<<<<< " + r_msg);

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

	    /*
	     * JCO.Table outputTable = function.getTableParameterList().getTable( "T_STPO" ); String changeNo = ""; for( int i = 0; i <
	     * outputTable.getNumRows(); i++ ) { outputTable.setRow( i ); changeNo = outputTable.getValue( "CHANGE_NO" ).toString();
	     * 
	     * Kogger.debug(getClass(),  "Result Change No. " + (i+1) + " <<<<<< "+ changeNo ); }
	     */

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (client != null)
		client.disconnect();
	    repository = null;
	}

	return result;
    }

    /**
     * 
     * 금형 BOM I/F (초도일 경우)
     * 
     * @param econum
     * @param headCd
     * @return
     * @메소드명 : getMoldInfo2
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 10. 6.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public boolean getMoldInfo2(String econum, String headCd) {
	boolean result = false;

	Client client = null;
	IRepository repository = null;
	KETBomPartUtil pUtil = new KETBomPartUtil();
	try {

	    // I. ecoItemCode를 HEAD로 하위 1레벨만 COMP로 가져온다.
	    InfoInterfaceQry iif = new InfoInterfaceQry();
	    Hashtable ht = iif.getMoldInterfaceData(headCd);

	    Vector<Hashtable<String, String>> vc = (Vector<Hashtable<String, String>>) ht.get("HEAD");
	    Vector<Hashtable<String, String>> vtr = (Vector<Hashtable<String, String>>) ht.get("COMP");

	    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
	    Kogger.info(getClass(), "HEENEEHEENEEHEENEEHEENEE");
	    Kogger.info(getClass(), "econum is \n" + econum);
	    Kogger.info(getClass(), "headCd is \n" + headCd);
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
	     * String strVal = null; for (int i = 0; i < vc.size(); i++) { Hashtable head = (Hashtable) vc.elementAt(i); String itemCd =
	     * (String) head.get("BOMCODE");
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

	    // ERP BOM 존재여부 확인 .. 오류로 인해 인터페이스 재전송을 해야할 경우를 대비하여 해당 코드추가 2015.08.20 by 황정태
	    String strVal = null;
	    for (int i = 0; i < vc.size(); i++){
	    	Hashtable head = (Hashtable) vc.elementAt(i); 
	    	String itemCd = (String) head.get("BOMCODE");
	    	strVal = KETBomHelper.service.getIsBomComponent(itemCd);
	    	break;
	    }
    	
    	if (strVal != null && !strVal.equals("") && strVal.equals("X")) { //이미 BOM이 ERP에 구성되어 있을 경우
    		return true;
    	}
    	
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
		Kogger.error(getClass(), "BomInfoInterface[testBOM]>>> " + e.toString());
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

		// JYP TODO : 설변인데 ecoheader, bomecocomponent에 정보가 쌓였을 경우, 설계변경사유를 초도(REASON_10)으로 바꾸고 작업
		result = false;
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (client != null)
		client.disconnect();
	    repository = null;
	}

	return result;
    }

}
