package e3ps.sap;

import java.util.Hashtable;
import java.util.Vector;

import wt.part.WTPart;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.Table;

import e3ps.bom.service.KETPartHelper;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class ItemMasterInterface {

    /**
     *	ERP 자재 Master 정보 조회 (일배치용)
     * 함수명 : getItemMasterInfo
     * @return 조회 해 온 전체 Master 정보
     */
	public static Vector<String> getItemMasterInfo(String strNumber) {
        Vector<String> returnVec = new Vector<String>();

		Client client = null;
		IRepository repository = null;

		String strReturn = "";
		String strItemCode = "";

		try {

			if (strNumber != null && !strNumber.trim().equals(""))
				strItemCode = strNumber.trim();

			// 1. Connection 맺기
			client = RFCConnect.getConnection();
	        client.connect();
	        repository = JCO.createRepository( "BFREPOSITORY", client );

	        // 2. Function 가져오기
	        IFunctionTemplate tmpl = repository.getFunctionTemplate( "Z_ST_SELECT_MATERIAL_INFO" );
	        Function function = tmpl.getFunction();

	        // 3. 조회 조건 입력 (미입력 시 전체 정보 리턴)
	        function.getImportParameterList().setValue( strItemCode, "I_MATNR" );

	        // 4. 조회 결과 저장 할 I/F 테이블 지정
	        Table tables = function.getTableParameterList().getTable( "T_MAT1" );

	        try {
	        	client.execute(function);
	        } catch( Exception e ) {
	        	Kogger.debug(ItemMasterInterface.class,  "ItemMasterInterface [getItemMasterInfo] >>>>>>>> "+e.toString() );
	        }

	        String strSuccess = (String)function.getExportParameterList().getValue( "E_RETURN" );
	        int resultCnt = function.getExportParameterList().getInt( "E_CNT" );
	        String strMsg = (String)function.getExportParameterList().getValue( "E_MSG" );

	        Kogger.debug(ItemMasterInterface.class,  "E_RETURN <<<<<<< "+ strSuccess );
	        Kogger.debug(ItemMasterInterface.class,  "E_CNT <<<<<<< "+ resultCnt );
	        Kogger.debug(ItemMasterInterface.class,  "E_MSG <<<<<<< "+ strMsg );

	        String strToken = "||";
	        String strDel = "";
	        String strbomFlag = "";
	        String strProdCode = "";
	        String strProdName = "";
	        String strYazaki = "";
	        String strYazakiNo = "";
	        String strPartWeight = "";
	        String strWeightUnit = "";
	        String strPartGroupDesc = "";
	        String strPartGroup = "";

	        for( int j = 0; j < tables.getNumRows(); j++ ) {
	        	tables.setRow(j);

	        	// '삭제여부' 컬럼이 빈 문자열인 경우 인식 못함
	        	Kogger.debug(ItemMasterInterface.class, "strDel=====>"+tables.getString("LVORM"));
	        	if (tables.getString("LVORM").replaceAll(" ", "").equals("")) {
	        		Kogger.debug(ItemMasterInterface.class, "######################"+tables.getString("LVORM")+"###################");
	        		strDel = "O";
	        	}else {
	        		Kogger.debug(ItemMasterInterface.class, "******************"+tables.getString("LVORM")+"*******************");
	        		strDel = tables.getString("LVORM");
	        	}
	        	Kogger.debug(ItemMasterInterface.class, "strDel2====>"+strDel);
	        	Kogger.debug(ItemMasterInterface.class, "strDel3=====>"+tables.getString("LVORM"));
	        	// 'BOM 구성이력 여부' 컬럼이 빈 문자열인 경우 인식 못함
	        	if (tables.getString("ZBOMC").equals("")) {
	        		strbomFlag = "O";
	        	} else {
	        		strbomFlag = tables.getString("ZBOMC");
	        	}

	        	// '금형-제품코드' 컬럼이 빈 문자열인 경우 인식 못함
	        	if (tables.getString("IDNRK").equals("")) {
	        		strProdCode = "O";
	        	}else {
	        		strProdCode = tables.getString("IDNRK");
	        	}
	        	// '금형-제품코드내역' 컬럼이 빈 문자열인 경우 인식 못함
	        	if (tables.getString("OJTXP").equals("")) {
	        		strProdName = "O";
	        	} else {
	        		strProdName = tables.getString("OJTXP");
	        	}

	        	// 'YAZAKI 여부' 컬럼이 빈 문자열인 경우 인식 못함
	        	if (tables.getString("RD_01_12").equals("")) {
	        		strYazaki = "O";
	        	} else {
	        		strYazaki = tables.getString("RD_01_12");
	        	}
	        	// 'YAZAKI 제번' 컬럼이 빈 문자열인 경우 인식 못함
	        	if (tables.getString("RD_01_13").equals("")) {
	        		strYazakiNo = "O";
	        	} else {
	        		strYazakiNo = tables.getString("RD_01_13");
	        	}

	        	// '순중량' 컬럼이 빈 문자열인 경우 인식 못함
	        	if (tables.getString("NTGEW").equals("")) {
	        		strPartWeight = "O";
	        	} else {
	        		strPartWeight = tables.getString("NTGEW");
	        	}
	        	// '중량단위' 컬럼이 빈 문자열인 경우 인식 못함
	        	if (tables.getString("GEWEI").equals("")) {
	        		strWeightUnit = "O";
	        	} else {
	        		strWeightUnit = tables.getString("GEWEI");
	        	}

	        	// '자재 그룹 내역' 컬럼이 빈 문자열인 경우 인식 못함
	        	if (tables.getString("WGBEZ").equals("")) {
	        		strPartGroupDesc = "O";
	        	} else {
	        		strPartGroupDesc = tables.getString("WGBEZ");
	        	}

	        	// '자재 그룹' 컬럼이 빈 문자열인 경우 인식 못함 2012.4.26 황정태 변경
	        	if (tables.getString("MATKL").equals("")) {
	        		strPartGroup = "O";
	        	} else {
	        		strPartGroup = tables.getString("MATKL");
	        	}

	        	strReturn = 	tables.getString("MATNR") + strToken + 			// 자재번호
	        					tables.getString("MAKTX") + strToken + 			// 자재내역
	        					tables.getString("MEINS") + strToken + 			// 기본단위
	        					tables.getString("MTART") + strToken + 			// 자재유형
	        					//tables.getString("MATKL") + strToken + 			// 자재그룹
	        					strPartGroup + strToken + 			// 자재그룹
	        					strDel + strToken + 									// 삭제여부
	        					strbomFlag + strToken +								// BOM 구성이력 여부
	        					strProdCode + strToken + 							// 금형-제품코드
	        					strProdName + strToken + 							// 금형-제품코드내역
//	        					tables.getString("ZDIRE") + strToken + 				// 신규,변경 구분
	        					strYazaki + strToken + 								// YAZAKI 여부
	        					strYazakiNo + strToken + 							// YAZAKI 제번
	        					strPartWeight + strToken + 							// 순중량
	        					strWeightUnit + strToken + 							// 중량 단위
	        					strPartGroupDesc; 										// 자재 그룹 내역

//Kogger.debug(getClass(), "@@@@@@@@@@ strReturn : " + strReturn);
	        	returnVec.addElement(strReturn);
	        }
		} catch( Exception e ) {
			Kogger.error(ItemMasterInterface.class, e);
		} finally {
			client.disconnect();
			repository = null;
		}
		return returnVec;
	}


	/**
	 * I/F 관련 작업(PLM 부품 등록/수정) 성공/실패 정보 업데이트
	 * 함수명 : setItemMasterResultInfo
	 * @return 성공/실패 여부
	 */
	public static String setItemMasterResultInfo(Hashtable<String, String> hash, boolean isSuccess) {
		String strReturn = "";
		String strUpdate = "";

		String itemCode = (String) hash.get("number");				// part number
		String itemName = (String) hash.get("name");					// part name
		String unit = (String) hash.get("unit");							// unit
		String isDeleted = (String) hash.get("isDeleted");				// isDeleted
		String bomFlag = (String) hash.get("bomFlag");				// bomFlag
		String prodCode = (String) hash.get("prodCode");				// prodCode
		String prodName = (String) hash.get("prodName");			// prodName
		String partGroupDesc = (String) hash.get("partGroupDesc");	// partGroupDesc
		String weightUnit = (String) hash.get("weightUnit");			// weightUnit
		String partWeight = (String) hash.get("partWeight");			// partWeight
		String yazaki = (String) hash.get("yazaki");						// IsYazaki
		String yazakiNo = (String) hash.get("yazakiNo");				// yazakiNo

		Client client = null;
		IRepository repository = null;

		try {
			// 1. Connection 맺기
			client = RFCConnect.getConnection();
	        client.connect();
	        repository = JCO.createRepository( "BFREPOSITORY", client );

	        // 2. Function 가져오기
	        IFunctionTemplate tmpl = repository.getFunctionTemplate( "Z_ST_UPDATE_MAT_INTERFACE" );
	        Function function = tmpl.getFunction();

	        // 3. 업데이트 정보 저장 할 I/F 테이블 지정
	        Table tables = function.getTableParameterList().getTable( "T_MAT1" );

	        if (isSuccess)	strUpdate = "S";
	        else	strUpdate = "F";

	        tables.appendRow();
	        tables.setValue( itemCode, "MATNR" );				// 자재번호
	        tables.setValue( itemName, "MAKTX" );				// 자재내역
	        tables.setValue( unit, "MEINS" );						// 기본단위
	        tables.setValue( isDeleted, "LVORM" );				// 삭제표시
	        tables.setValue( bomFlag, "ZBOMC" );				// BOM 구성이력 여부
	        tables.setValue( strUpdate, "ZSTAT" );				// I/F 상태
	        tables.setValue( prodCode, "IDNRK" );				// 금형-제품코드
	        tables.setValue( prodName, "OJTXP" );				// 금형-제품코드 내역
	        tables.setValue( partGroupDesc, "WGBEZ" );		// 자재그룹내역
	        tables.setValue( partWeight, "NTGEW" );				// 순중량
	        tables.setValue( weightUnit, "GEWEI" );				// 중량단위
	        tables.setValue( yazaki, "RD_01_12" );				// YAZAKI 여부
	        tables.setValue( yazakiNo, "RD_01_13" );				// YAZAKI 제번

	        try {
	        	client.execute(function);
	        } catch( Exception e ) {
	        	Kogger.debug(ItemMasterInterface.class,  "ItemMasterInterface [setItemMasterResultInfo] >>>>>>>> "+e.toString() );
	        }

	        String strSuccess = (String)function.getExportParameterList().getValue( "E_RETURN" );
	        int resultCnt = function.getExportParameterList().getInt( "E_CNT" );
	        String strMsg = (String)function.getExportParameterList().getValue( "E_MSG" );

	        Kogger.debug(ItemMasterInterface.class,  "E_RETURN <<<<<<< "+ strSuccess );
	        Kogger.debug(ItemMasterInterface.class,  "E_CNT <<<<<<< "+ resultCnt );
	        Kogger.debug(ItemMasterInterface.class,  "E_MSG <<<<<<< "+ strMsg );

		} catch( Exception e ) {
			Kogger.error(ItemMasterInterface.class, e);
		} finally {
			client.disconnect();
			repository = null;
		}
		return strReturn;
	}


	/**
	 * 금형 신규 부품 생성
	 * 함수명 : setMoldMasterInfo
	 * @return 성공/실패 여부
	 */
	public static Vector<String> setMoldMasterInfo(Hashtable<String, String> hashInfo) {
		Vector<String> vecReturn = new Vector<String>();

		Client client = null;
		IRepository repository = null;

		try {
			// 1. Connection 맺기
			client = RFCConnect.getConnection();
	        client.connect();
	        repository = JCO.createRepository( "BFREPOSITORY", client );

	        // 2. Function 가져오기
	        IFunctionTemplate tmpl = repository.getFunctionTemplate( "Z_ST_CREATE_MATERIAL" );
	        Function function = tmpl.getFunction();

	        // 3. 신규 금형 정보를 저장 할 I/F 테이블 지정
	        Table tables = function.getTableParameterList().getTable( "T_MAT1" );

	        if (hashInfo != null) {
	        	tables.appendRow();
	        	tables.setValue( hashInfo.get("dieno"), "MATNR" );				// 금형 번호 (Die No)
	        	tables.setValue( hashInfo.get("number"), "IDNRK" );				// 금형 부품 번호
	        	tables.setValue( hashInfo.get("name"), "MAKTX" );					// 금형 부품 내역
	        	tables.setValue( hashInfo.get("unit"), "MEINS" );					// 기본단위
	        	tables.setValue( hashInfo.get("material"), "ATWRT" );				// 재질
	        	tables.setValue( "DIEM", "MTART" );									// 자재유형 (Fixed Value)
	        	tables.setValue( "502000", "MATKL" );								// 자재그룹 (Fixed Value)
	        }

	        try {
	        	client.execute(function);
	        } catch( Exception e ) {
	        	Kogger.debug(ItemMasterInterface.class,  "ItemMasterInterface [setMoldMasterInfo] >>>>>>>> "+e.toString() );
	        }

	        String strSuccess = (String) tables.getValue("ZSTAT");
	        String strMsg = (String)tables.getValue("ZRMSG");

	        Kogger.debug(ItemMasterInterface.class,  "E_RETURN <<<<<<< "+ strSuccess );
	        Kogger.debug(ItemMasterInterface.class,  "E_MSG <<<<<<< "+ strMsg );

	        vecReturn.addElement(strSuccess);
	        vecReturn.addElement(strMsg);

		} catch( Exception e ) {
			Kogger.error(ItemMasterInterface.class, e);
		} finally {
			client.disconnect();
			repository = null;
		}
		return vecReturn;
	}


    /**
     *	Migration 용 ERP 자재 Master 정보 조회
     * 함수명 : getItemMigration
     * @return 조회 해 온 전체 자재 Master 정보
     */
	public static Vector<String> getItemMigration(String strNumber, String fromDate, String toDate) {
        Vector<String> returnVec = new Vector<String>();

		Client client = null;
		IRepository repository = null;

		String strReturn = "";
		String strItemCode = "";

		try {

			if (strNumber != null && !strNumber.trim().equals(""))
				strItemCode = strNumber.trim();

			// 1. Connection 맺기
			client = RFCConnect.getConnection();
	        client.connect();
	        repository = JCO.createRepository( "BFREPOSITORY", client );

	        // 2. Function 가져오기
	        IFunctionTemplate tmpl = repository.getFunctionTemplate( "Z_ST_MIG_MATERIAL" );
	        Function function = tmpl.getFunction();

	        // 3. 조회 조건 입력 (미입력 시 전체 정보 리턴)
	        function.getImportParameterList().setValue( strItemCode, "I_MATNR" );
	        function.getImportParameterList().setValue( fromDate, "I_ERSDA_FR" );
	        function.getImportParameterList().setValue( toDate, "I_ERSDA_TO" );

	        // 4. 조회 결과 저장 할 I/F 테이블 지정
	        Table tables = function.getTableParameterList().getTable( "T_MAT1" );

	        try {
	        	client.execute(function);
	        } catch( Exception e ) {
	        	Kogger.debug(ItemMasterInterface.class,  "ItemMasterInterface [getItemMigration] >>>>>>>> "+e.toString() );
	        }

	        String strSuccess = (String)function.getExportParameterList().getValue( "E_RETURN" );
	        int resultCnt = function.getExportParameterList().getInt( "E_CNT" );
	        String strMsg = (String)function.getExportParameterList().getValue( "E_MSG" );

	        Kogger.debug(ItemMasterInterface.class,  "E_RETURN <<<<<<< "+ strSuccess );
	        Kogger.debug(ItemMasterInterface.class,  "E_CNT <<<<<<< "+ resultCnt );
	        Kogger.debug(ItemMasterInterface.class,  "E_MSG <<<<<<< "+ strMsg );

	        String strToken = "||";
	        String strDel = "";
	        String strbomFlag = "";
	        String strProdCode = "";
	        String strProdName = "";
	        String strYazaki = "";
	        String strYazakiNo = "";
	        String strPartWeight = "";
	        String strWeightUnit = "";
	        String strPartGroupDesc = "";

	        for( int j = 0; j < tables.getNumRows(); j++ ) {
	        	tables.setRow(j);

	        	// '삭제여부' 컬럼이 빈 문자열인 경우 인식 못함
	        	if (tables.getString("LVORM").equals("")) {
	        		strDel = "O";
	        	} else {
	        		strDel = tables.getString("LVORM");
	        	}
	        	// 'BOM 구성이력 여부' 컬럼이 빈 문자열인 경우 인식 못함
	        	if (tables.getString("ZBOMC").equals("")) {
	        		strbomFlag = "O";
	        	} else {
	        		strbomFlag = tables.getString("ZBOMC");
	        	}

	        	// '금형-제품코드' 컬럼이 빈 문자열인 경우 인식 못함
	        	if (tables.getString("IDNRK").equals("")) {
	        		strProdCode = "O";
	        	}else {
	        		strProdCode = tables.getString("IDNRK");
	        	}
	        	// '금형-제품코드내역' 컬럼이 빈 문자열인 경우 인식 못함
	        	if (tables.getString("OJTXP").equals("")) {
	        		strProdName = "O";
	        	} else {
	        		strProdName = tables.getString("OJTXP");
	        	}

	        	// 'YAZAKI 여부' 컬럼이 빈 문자열인 경우 인식 못함
	        	if (tables.getString("RD_01_12").equals("")) {
	        		strYazaki = "O";
	        	} else {
	        		strYazaki = tables.getString("RD_01_12");
	        	}
	        	// 'YAZAKI 제번' 컬럼이 빈 문자열인 경우 인식 못함
	        	if (tables.getString("RD_01_13").equals("")) {
	        		strYazakiNo = "O";
	        	} else {
	        		strYazakiNo = tables.getString("RD_01_13");
	        	}

	        	// '순중량' 컬럼이 빈 문자열인 경우 인식 못함
	        	if (tables.getString("NTGEW").equals("")) {
	        		strPartWeight = "O";
	        	} else {
	        		strPartWeight = tables.getString("NTGEW");
	        	}
	        	// '중량단위' 컬럼이 빈 문자열인 경우 인식 못함
	        	if (tables.getString("GEWEI").equals("")) {
	        		strWeightUnit = "O";
	        	} else {
	        		strWeightUnit = tables.getString("GEWEI");
	        	}

	        	// '자재 그룹 내역' 컬럼이 빈 문자열인 경우 인식 못함
	        	if (tables.getString("WGBEZ").equals("")) {
	        		strPartGroupDesc = "O";
	        	} else {
	        		strPartGroupDesc = tables.getString("WGBEZ");
	        	}

	        	strReturn =	tables.getString("MATNR") + strToken + 			// 자재번호
	        					tables.getString("MAKTX") + strToken + 			// 자재내역
	        					tables.getString("MEINS") + strToken + 			// 기본단위
	        					tables.getString("MTART") + strToken + 			// 자재유형
	        					tables.getString("MATKL") + strToken + 			// 자재그룹
	        					strDel + strToken + 									// 삭제여부
	        					strbomFlag + strToken +								// BOM 구성이력 여부
	        					strProdCode + strToken + 							// 금형-제품코드
	        					strProdName + strToken + 							// 금형-제품코드내역
	        					strYazaki + strToken + 								// YAZAKI 여부
	        					strYazakiNo + strToken + 							// YAZAKI 제번
	        					strPartWeight + strToken + 							// 순중량
	        					strWeightUnit + strToken + 							// 중량 단위
	        					strPartGroupDesc; 										// 자재 그룹 내역

	        	returnVec.addElement(strReturn);
	        }
		} catch( Exception e ) {
			Kogger.error(ItemMasterInterface.class, e);
		} finally {
			client.disconnect();
			repository = null;
		}
		return returnVec;
	}


	// ERP BOM 구성이력 정보 조회
	public static String getIsBomComponent(String strNumber) {

		Client client = null;
		IRepository repository = null;

		String strReturn = "";
		String strItemCode = "";

		try {

			if (strNumber != null && !strNumber.trim().equals(""))
				strItemCode = strNumber.trim();

			// 1. Connection 맺기
			client = RFCConnect.getConnection();
	        client.connect();
	        repository = JCO.createRepository( "BFREPOSITORY", client );

	        // 2. Function 가져오기
	        WTPart part = KETPartHelper.service.getPart(strItemCode);
			//String strType = IBAUtil.getAttrValue(part, "PartType");
			String strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
			IFunctionTemplate tmpl = null;
			if (PartUtil.isProductType(strType)) {	// 제품인 경우
				tmpl = repository.getFunctionTemplate( "Z_ST_GET_INFO_BOM_V1" );
			} else {												// 금형인 경우
				tmpl = repository.getFunctionTemplate( "Z_ST_GET_INFO_BOM" );

			}
			Function function = tmpl.getFunction();

	        // 3. 조회 조건 입력 (미입력 시 전체 정보 리턴)
	        function.getImportParameterList().setValue( strItemCode, "I_MATNR" );

	        try {
	        	client.execute(function);
	        } catch( Exception e ) {
	        	Kogger.debug(ItemMasterInterface.class,  "ItemMasterInterface [getIsBomComponent] >>>>>>>> "+e.toString() );
	        }

	        String strIsBomC = (String)function.getExportParameterList().getValue( "E_ZBOMC" );
	        String strSuccess = (String)function.getExportParameterList().getValue( "E_RETURN" );
	        int resultCnt = function.getExportParameterList().getInt( "E_CNT" );
	        String strMsg = (String)function.getExportParameterList().getValue( "E_MSG" );

	        Kogger.debug(ItemMasterInterface.class,  "E_ZBOMC <<<<<<< "+ strIsBomC );
	        Kogger.debug(ItemMasterInterface.class,  "E_RETURN <<<<<<< "+ strSuccess );
	        Kogger.debug(ItemMasterInterface.class,  "E_CNT <<<<<<< "+ resultCnt );
	        Kogger.debug(ItemMasterInterface.class,  "E_MSG <<<<<<< "+ strMsg );

	        if (strIsBomC != null && strIsBomC.equals("")) {
	        	strReturn = "O";
	        } else {
	        	strReturn = strIsBomC;
	        }

		} catch( Exception e ) {
			Kogger.error(ItemMasterInterface.class, e);
		} finally {
			client.disconnect();
			repository = null;
		}
		return strReturn;
	}

	// 양산이관 설계변경 결재완료 시 호출됨
	// 해당 변경 BOM 정보로 ERP 자재 Master Update
	public static String updateMasterErp(Hashtable<String, String> hashUpdate) {

		Client client = null;
		IRepository repository = null;

		String partNo = hashUpdate.get("partNo");			// 자재번호
		String rQuantity = hashUpdate.get("rQuantity");	// 순중량
		String rUnit = hashUpdate.get("rUnit");				// 중량단위
		String sQuantity = hashUpdate.get("sQuantity");	// 스크랩중량
		String sCode = hashUpdate.get("sCode");			// 스크랩코드

		String strReturn = "";

		try {

			// 1. Connection 맺기
			client = RFCConnect.getConnection();
	        client.connect();
	        repository = JCO.createRepository( "BFREPOSITORY", client );

	        // 2. Function 가져오기
	        IFunctionTemplate tmpl = repository.getFunctionTemplate( "Z_ST_UPDATE_MATERIAL" );
	        Function function = tmpl.getFunction();

	        // 3. 조회 결과 저장 할 I/F 테이블 지정
	        Table tables = function.getTableParameterList().getTable( "T_MAT1" );

	        tables.appendRow();
	        tables.setValue( partNo, "MATNR" );		// 자재번호
	        tables.setValue( rQuantity, "NTGEW" );	// 순중량
	        tables.setValue( rUnit, "GEWEI" );			// 중량단위
	        tables.setValue( sQuantity, "SCGEW" );	// 스크랩중량
	        tables.setValue( sCode, "ZSCRP" );		// 스크랩코드

	        try {
	        	client.execute(function);
	        } catch( Exception e ) {
	        	Kogger.debug(ItemMasterInterface.class,  "ItemMasterInterface [updateMasterErp] >>>>>>>> "+e.toString() );
	        }

	        String strSuccess = (String)function.getExportParameterList().getValue( "E_RETURN" );
	        int resultCnt = function.getExportParameterList().getInt( "E_CNT" );
	        String strMsg = (String)function.getExportParameterList().getValue( "E_MSG" );

	        Kogger.debug(ItemMasterInterface.class,  "E_RETURN <<<<<<< "+ strSuccess );
	        Kogger.debug(ItemMasterInterface.class,  "E_CNT <<<<<<< "+ resultCnt );
	        Kogger.debug(ItemMasterInterface.class,  "E_MSG <<<<<<< "+ strMsg );

        	strReturn =	tables.getString("ZSTAT") + ":" +tables.getString("ZRMSG");

		} catch( Exception e ) {
			Kogger.error(ItemMasterInterface.class, e);
		} finally {
			client.disconnect();
			repository = null;
		}
		return strReturn;
	}
}
