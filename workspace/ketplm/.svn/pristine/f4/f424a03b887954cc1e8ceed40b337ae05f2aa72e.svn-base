package e3ps.sap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import wt.lifecycle.State;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartTypeInfo;
import wt.part.WTPartTypeInterface;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.base.service.internal.PartBaseDao;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETHalbPartDieSetPartLink;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.sap.ErpPartHandler;
import ext.ket.part.spec.util.PartTypeEnum;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

final public class ErpPartInterFace {

    // ERP 방어 코드
    private boolean useErpDefenseCode = false;

    private static final String SPACE = " ";
    private static final String ECO_NO_ERP_FIELD = "I_ECONO";
    private static final String[] COLOR_ARRAY = { "BLACK", "BLUE", "BROWN", "GRAY", "D/GRAY", "L/GRAY", "GREEN", "L/GREEN", "IVORY",
	    "JADE", "MAGENTA", "NATURAL", "ORANGE", "RED", "YELLOW", "기타", "WHITE", "LIGHT PINK", "SKY BLUE" };

    private static final int CREATE_GENERAL = 1;
    private static final int CREATE_DIE = 2;
    private static final int CREATE_MOLD = 3;
    private static final int MODIFY_GENERAL = 4;
    private static final int MODIFY_DIE = 5;
    private static final int MODIFY_MOLD = 6;

    private static final int SAP_DATA_SIZE = 2;

    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

    private List<ErpPartProdDTO> prodCreateList = new ArrayList<ErpPartProdDTO>();
    private List<ErpPartProdDTO> prodModifyList = new ArrayList<ErpPartProdDTO>();
    private List<ErpPartDieDTO> dieCreateList = new ArrayList<ErpPartDieDTO>();
    private List<ErpPartDieDTO> dieModifyList = new ArrayList<ErpPartDieDTO>();
    private List<ErpPartDieHalbDTO> dieHalbCreateList = new ArrayList<ErpPartDieHalbDTO>();
    private List<ErpPartDieHalbDTO> dieHalbModifyList = new ArrayList<ErpPartDieHalbDTO>();
    private List<ErpPartMoldDTO> moldCreateList = new ArrayList<ErpPartMoldDTO>();
    private List<ErpPartMoldDTO> moldModifyList = new ArrayList<ErpPartMoldDTO>();

    private List<String> errorLogList = new ArrayList<String>();

    public static final String SUCCESS = "SUCCESS";
    public static final String PLM_ERROR_LIST = "PLM_ERROR_LIST";
    public static final String ERP_ERROR_MSG = "ERP_ERROR_MSG";

    private Map returnMap = new HashMap();
    private ErpPartHandler handler = new ErpPartHandler();

    // 부품 ERP 전송 Interface
    // 1. 부품을 보낼 수 있는 대상으로 분류한다.
    // 2. 부품을 해당 대상별로 dto에 넣어준다.
    // 3. 값을 넣을 때마다 필수일 경우 빈 값에 대해서 validation을 걸어준다. ( 이 때 validation 체크를 모아서하기 위해서 dto의 변수명과 enum의 attrCode는 동일하게 맞추고, BeanUtils 사용함 )
    // 4. 에러가 발생한 경우에 에러 로그를 찍고 Stop 해준다.
    // 5. Validation을 통과한 경우에 erp에 전송을 해준다.
    public Map sendPartInfoToErp(List<WTPart> iPartList, String ecoNo, Map<String, String> workerNameMap) {
	boolean isOnlyInwork = true;
	Kogger.biz("ECO 부품 ERP I/F 시작");
	Map map = sendPartInfoToErp(iPartList, ecoNo, workerNameMap, isOnlyInwork);
	Kogger.biz("ECO 부품 ERP I/F 종료");
	return map;
    }

    public Map sendPartInfoToErp(List<WTPart> iPartList, String ecoNo, Map<String, String> workerNameMap, boolean isOnlyInwork) {

	boolean success = false;

	if (iPartList == null || iPartList.size() == 0) {

	    success = true;
	    returnMap.put(SUCCESS, success);
	    returnMap.put(PLM_ERROR_LIST, errorLogList);
	    returnMap.put(ERP_ERROR_MSG, "");

	    return returnMap;
	}

	Client client = null;
	IRepository repository = null;

	try {

	    Kogger.debug(getClass(), "@@===================================================@@ ");
	    Kogger.debug(getClass(), "@@========== ERP 부품 Part Trans GO! GO! ============@@ ");
	    Kogger.info(getClass(), "@@========== ecoNo is [" + ecoNo + "]");
	    Kogger.debug(getClass(), "@@===================================================@@ ");

	    // JCO 연결
	    client = RFCConnect.getConnection();
	    client.connect();
	    repository = JCO.createRepository("BFREPOSITORY", client);

	    List<WTPart> partList = iPartList;

	    // -. 모든 부품중 In-Work인 부품만 가져온다.
	    // -. 그 중에서 Function 별로 생성하는 것(최초)인지, 수정을 하는 것인지를 정한다. ( 참고로 ERP는 리비전이 없음.)
	    // -. 그 중에서 Table 별로 제품(비금형)유형인지 금형유형인지를 분기한다.
	    List<WTPart> partErpCreateSendProdList = new ArrayList<WTPart>();
	    List<WTPart> partErpCreateSendDieList = new ArrayList<WTPart>();
	    List<WTPart> partErpCreateSendMoldList = new ArrayList<WTPart>();

	    List<WTPart> partErpModifySendProdList = new ArrayList<WTPart>();
	    List<WTPart> partErpModifySendDieList = new ArrayList<WTPart>();
	    List<WTPart> partErpModifySendMoldList = new ArrayList<WTPart>();
	    Kogger.info(getClass(), "@@========== partList size is [" + partList.size() + "]");
	    for (WTPart wtPart : partList) {
		if (wtPart != null
		        && (!isOnlyInwork || (isOnlyInwork && (State.INWORK == wtPart.getLifeCycleState() || State.UNDERREVIEW == wtPart
		                .getLifeCycleState())))) {
		    String partType = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
		    // if ("0".equals(wtPart.getVersionIdentifier().getValue()) && State.toState("APPROVED") != wtPart.getLifeCycleState())
		    // { // 최초인지 아닌지를 0리비전으로 판단한다.
		    if (!handler.existErpPart(wtPart.getNumber())) {
			if (PartUtil.isProductType(partType)) {
			    Kogger.debug(getClass(), "###### 1. erpIf part CREATE General :" + wtPart.getNumber() + " : "
				    + wtPart.getVersionDisplayIdentifier().toString() + " : " + CommonUtil.getOIDString(wtPart));
			    partErpCreateSendProdList.add(wtPart);
			} else {
			    Kogger.debug(getClass(), "###### 2. erpIf part CREATE Die Mold :" + wtPart.getNumber() + " : "
				    + wtPart.getVersionDisplayIdentifier().toString() + " : " + CommonUtil.getOIDString(wtPart));
			    if ("D".equals(partType)) {
				partErpCreateSendDieList.add(wtPart);
			    } else if ("M".equals(partType)) {
				partErpCreateSendMoldList.add(wtPart);
			    } else {
				throw new Exception("사용하지 않는 부품 유형이 존재하여 ERP I/F 부품 대상 선택 오류 발생했습니다.");
			    }
			}
		    } else {
			if (PartUtil.isProductType(partType)) {
			    Kogger.debug(getClass(), "###### 3. erpIf part Modify General :" + wtPart.getNumber() + " : "
				    + wtPart.getVersionDisplayIdentifier().toString() + " : " + CommonUtil.getOIDString(wtPart));
			    partErpModifySendProdList.add(wtPart);
			} else {
			    Kogger.debug(getClass(), "###### 4. erpIf part Modify Die Mold :" + wtPart.getNumber() + " : "
				    + wtPart.getVersionDisplayIdentifier().toString() + " : " + CommonUtil.getOIDString(wtPart));
			    if ("D".equals(partType)) {
				partErpModifySendDieList.add(wtPart);
			    } else if ("M".equals(partType)) {
				partErpModifySendMoldList.add(wtPart);
			    } else {
				throw new Exception("사용하지 않는 부품 유형이 존재하여 ERP I/F 부품 대상 선택 오류 발생했습니다.");
			    }
			}
		    }
		} else {
		    if (wtPart != null)
			Kogger.debug(getClass(),
			        "###### '진행중'이 아닌 WTPart는 대상에서 제외 :" + wtPart.getNumber() + " : " + wtPart.getLifeCycleState());

		}

	    }

	    Kogger.debug(getClass(), "@@===================================================@@ ");
	    Kogger.debug(getClass(), "@@==========  Part Property Get  GO! GO! ============@@ ");
	    Kogger.debug(getClass(), "@@===================================================@@ ");

	    // 1. 일반제품 - 전송 값을 DTO에 담기
	    if (partErpCreateSendProdList.size() > 0) {

		Kogger.debug(getClass(), "####### erpIf part CREATE General make #######");
		for (WTPart wtPart : partErpCreateSendProdList) {
		    setProdPartTable(wtPart, workerNameMap, true);
		}
	    }

	    // 2. 금형제품 - 전송 값을 DTO에 담기
	    if (partErpCreateSendDieList.size() > 0 || partErpCreateSendMoldList.size() > 0) {

		Kogger.debug(getClass(), "####### erpIf part CREATE Mold make #######");
		for (WTPart wtPart : partErpCreateSendDieList) {
		    setDiePartTable(wtPart, true);
		}
		// mold 처리
		for (WTPart wtPart : partErpCreateSendMoldList) {
		    setMoldPartTable(wtPart, true);
		}
	    }

	    // 수정하는 것 처리
	    // 3. 일반제품 - 전송 값을 DTO에 담기
	    if (partErpModifySendProdList.size() > 0) {

		Kogger.debug(getClass(), "####### erpIf part MODIFY General make #######");

		for (WTPart wtPart : partErpModifySendProdList) {
		    setProdPartTable(wtPart, workerNameMap, false);
		}
	    }

	    // 4. 금형제품 - 전송 값을 DTO에 담기
	    if (partErpModifySendDieList.size() > 0 || partErpModifySendMoldList.size() > 0) {

		Kogger.debug(getClass(), "####### erpIf part MODIFY Mold make #######");
		// die 처리, mapping 자재 처리
		for (WTPart wtPart : partErpModifySendDieList) {
		    setDiePartTable(wtPart, false);
		}
		for (WTPart wtPart : partErpModifySendMoldList) {
		    setMoldPartTable(wtPart, false);
		}
	    }

	    if (StringUtils.isEmpty(ecoNo)) {
		errorLogList.add("## Essential Check Error : EcoNo empty !! ");
	    }

	    Kogger.debug(getClass(), "@@===================================================@@ ");
	    Kogger.debug(getClass(), "@@==========  Part Error Check   GO! GO! ============@@ ");
	    Kogger.debug(getClass(), "@@===================================================@@ ");

	    // 전송 값의 필수값이 비어 있거나, 글자 수가 OVER되면 STOP 되도록 함.
	    if (errorLogList.size() > 0) {
		for (String errorLog : errorLogList) {
		    Kogger.debug(getClass(), errorLog);
		}
	    }

	    Kogger.debug(getClass(), "@@===================================================@@ ");
	    Kogger.debug(getClass(), "@@==========  Part ERP Call      GO! GO! ============@@ ");
	    Kogger.debug(getClass(), "@@===================================================@@ ");

	    // -. CREATE_GENERAL
	    if (partErpCreateSendProdList.size() > 0) {
		success = executeFuction(CREATE_GENERAL, client, repository, ecoNo);
		if (!success) {
		    success = false;
		    returnMap.put(SUCCESS, success);
		    returnMap.put(PLM_ERROR_LIST, errorLogList);
		    return returnMap;
		}
	    }

	    // -. CREATE_DIE
	    if (partErpCreateSendDieList.size() > 0) {
		success = executeFuction(CREATE_DIE, client, repository, ecoNo);
		if (!success) {
		    success = false;
		    returnMap.put(SUCCESS, success);
		    returnMap.put(PLM_ERROR_LIST, errorLogList);
		    return returnMap;
		}
	    }

	    // -. CREATE_MOLD
	    if (partErpCreateSendMoldList.size() > 0) {
		success = executeFuction(CREATE_MOLD, client, repository, ecoNo);
		if (!success) {
		    success = false;
		    returnMap.put(SUCCESS, success);
		    returnMap.put(PLM_ERROR_LIST, errorLogList);
		    return returnMap;
		}
	    }

	    // 수정하는 것 처리
	    // -. MODIFY_GENERAL
	    if (partErpModifySendProdList.size() > 0) {
		success = executeFuction(MODIFY_GENERAL, client, repository, ecoNo);
		if (!success) {
		    success = false;
		    returnMap.put(SUCCESS, success);
		    returnMap.put(PLM_ERROR_LIST, errorLogList);
		    return returnMap;
		}
	    }

	    // -. MODIFY_DIE
	    if (partErpModifySendDieList.size() > 0) {
		success = executeFuction(MODIFY_DIE, client, repository, ecoNo);
		if (!success) {
		    success = false;
		    returnMap.put(SUCCESS, success);
		    returnMap.put(PLM_ERROR_LIST, errorLogList);
		    return returnMap;
		}
	    }

	    // -. MODIFY_MOLD
	    if (partErpModifySendMoldList.size() > 0) {
		success = executeFuction(MODIFY_MOLD, client, repository, ecoNo);
		if (!success) {
		    success = false;
		    returnMap.put(SUCCESS, success);
		    returnMap.put(PLM_ERROR_LIST, errorLogList);
		    return returnMap;
		}
	    }

	} catch (Exception e) {

	    Kogger.error(getClass(), e);

	    success = false;
	    returnMap.put(SUCCESS, success);
	    returnMap.put(PLM_ERROR_LIST, errorLogList);
	    returnMap.put(ERP_ERROR_MSG, "");

	    return returnMap;

	} finally {

	    if (client != null) {
		client.disconnect();
	    }

	    repository = null;
	}

	success = true;
	returnMap.put(SUCCESS, success);
	returnMap.put(PLM_ERROR_LIST, errorLogList);
	returnMap.put(ERP_ERROR_MSG, "");

	return returnMap;
    }

    private boolean executeFuction(int erpType, Client client, IRepository repository, String ecoNo) throws Exception {

	boolean success = false;

	try {
	    Kogger.debug(getClass(), "@@==========, executeFuction. ecoNo is [" + ecoNo + "]");

	    String functionName = null;
	    if (erpType == CREATE_GENERAL) {

		Kogger.debug(getClass(), "####### erpIf part CREATE General start #######");
		functionName = "Z_ST_CREATE_MATERIAL_GENERAL";

	    } else if (erpType == CREATE_DIE || erpType == CREATE_MOLD) {

		Kogger.debug(getClass(), "####### erpIf part CREATE MOLD start #######");
		functionName = "Z_ST_CREATE_MATERIAL_MOLD";

	    } else if (erpType == MODIFY_GENERAL) {

		Kogger.debug(getClass(), "####### erpIf part MODIFY General start #######");
		functionName = "Z_ST_CHANGE_MATERIAL_GENERAL";

	    } else if (erpType == MODIFY_DIE || erpType == MODIFY_MOLD) {

		Kogger.debug(getClass(), "####### erpIf part MODIFY MOLD start #######");
		functionName = "Z_ST_CHANGE_MATERIAL_MOLD";
	    }

	    if (erpType == CREATE_GENERAL || erpType == MODIFY_GENERAL) {

		List<ErpPartProdDTO> prodList = new ArrayList<ErpPartProdDTO>();

		if (erpType == CREATE_GENERAL) {
		    // SCRAB이 먼저 가야 한다.
		    for (ErpPartProdDTO dto : prodCreateList) {
			String getSpPartType = dto.getSpPartType();
			if (PartTypeEnum.SCRP.getErpCode().equals(getSpPartType)) {
			    prodList.add(dto);
			}
		    }

		    // 기타는 나중에 가야 한다.
		    for (ErpPartProdDTO dto : prodCreateList) {
			String getSpPartType = dto.getSpPartType();
			if (!PartTypeEnum.SCRP.getErpCode().equals(getSpPartType)) {
			    prodList.add(dto);
			}
		    }

		} else if (erpType == MODIFY_GENERAL) {

		    // SCRAB이 먼저 가야 한다.
		    for (ErpPartProdDTO dto : prodModifyList) {
			String getSpPartType = dto.getSpPartType();
			if (PartTypeEnum.SCRP.getErpCode().equals(getSpPartType)) {
			    prodList.add(dto);
			}
		    }

		    // 기타는 나중에 가야 한다.
		    for (ErpPartProdDTO dto : prodModifyList) {
			String getSpPartType = dto.getSpPartType();
			if (!PartTypeEnum.SCRP.getErpCode().equals(getSpPartType)) {
			    prodList.add(dto);
			}
		    }
		}

		if (prodList.size() > 0) {

		    Kogger.info(getClass(), "functionName is [" + functionName + "]");

		    IFunctionTemplate tmpl = repository.getFunctionTemplate(functionName);
		    Function function = tmpl.getFunction();

		    Table table = function.getTableParameterList().getTable("IT_TYPE0");
		    ErpPartProdEnum[] prodEnumList = ErpPartProdEnum.values();
		    for (int i = 0; i < prodList.size(); i++) {
			ErpPartProdDTO dto = prodList.get(i); // 순서가 중요. SCRAB부터 넘어 가야 한다.

			Kogger.info(getClass(), "WTPart : " + dto.getWtpartNumber() + ", eco : " + dto.getEcoId());

			table.appendRow();
			for (ErpPartProdEnum erpPartProdEnum : prodEnumList) {
			    Kogger.info(getClass(),
				    "ErpField : " + erpPartProdEnum.getErpField() + " PLM Attr: " + erpPartProdEnum.getAttrCode());
			    Kogger.info(getClass(), "ErpValue : "
				    + checkErpValue(BeanUtils.getProperty(dto, erpPartProdEnum.getAttrCode())));
			    table.setValue(checkErpValue(BeanUtils.getProperty(dto, erpPartProdEnum.getAttrCode())),
				    erpPartProdEnum.getErpField());
			}
		    }

		    Kogger.debug(getClass(), "ecoNo is [" + ecoNo + "]");
		    function.getImportParameterList().getField(ECO_NO_ERP_FIELD).setValue(ecoNo);

		    client.execute(function);

		    // Table table = function.getTableParameterList().getTable("IT_TYPE0");
		    String table2String = table.toString();
		    Kogger.debug(getClass(), "E_RETURN_TABLE<<<<<< " + table2String);

		    int numRows = table.getNumRows();
		    for (int i = 0; i < numRows; i++) {
			table.setRow(i);

			String MATNR = table.getString("MATNR");
			String ZSTAT = table.getString("ZSTAT");
			String ZRMSG = table.getString("ZRMSG");

		    }

		    String eReturn = (String) function.getExportParameterList().getValue("E_RETURN");
		    int eCnt = function.getExportParameterList().getInt("E_CNT");
		    String eMsg = (String) function.getExportParameterList().getValue("E_MSG");

		    Kogger.info(getClass(), "E_RETURN<<<<<< " + eReturn);
		    Kogger.info(getClass(), "E_CNT<<<<<< " + eCnt);
		    Kogger.info(getClass(), "E_MSG<<<<<< " + eMsg);

		    if (eReturn != null && "S".equals((eReturn.toUpperCase()))) {
			Kogger.info(getClass(), "######## SUCCESS part CREATE ");
			success = true;
		    } else {
			Kogger.info(getClass(), "######## FAIL part CREATE ");
			success = false;
			returnMap.put(ERP_ERROR_MSG, StringUtil.checkNull(eMsg));
			return success;
		    }
		}
	    } else if (erpType == CREATE_DIE || erpType == MODIFY_DIE || erpType == CREATE_MOLD || erpType == MODIFY_MOLD) {

		List<ErpPartDieDTO> dieList = new ArrayList<ErpPartDieDTO>();
		List<ErpPartDieHalbDTO> dieHalbList = new ArrayList<ErpPartDieHalbDTO>();
		List<ErpPartMoldDTO> moldList = new ArrayList<ErpPartMoldDTO>();
		if (erpType == CREATE_DIE) {
		    dieList = dieCreateList;
		    dieHalbList = dieHalbCreateList;
		} else if (erpType == MODIFY_DIE) {
		    dieList = dieModifyList;
		    dieHalbList = dieHalbModifyList;
		} else if (erpType == CREATE_MOLD) {
		    moldList = moldCreateList;
		} else if (erpType == MODIFY_MOLD) {
		    moldList = moldModifyList;
		}

		if (dieList.size() > 0 || moldList.size() > 0) {

		    Kogger.info(getClass(), "functionName is [" + functionName + "]");

		    IFunctionTemplate tmpl = repository.getFunctionTemplate(functionName);
		    Function function = tmpl.getFunction();

		    // die 처리, mapping 자재 처리
		    Table dieTable = function.getTableParameterList().getTable("IT_TYPE7");
		    ErpPartDieEnum[] dieEnumList = ErpPartDieEnum.values();
		    for (ErpPartDieDTO dto : dieList) {

			Kogger.info(getClass(), "WTPart7 : " + dto.getWtpartNumber() + ", eco : " + ecoNo);

			dieTable.appendRow();
			for (ErpPartDieEnum erpPartDieEnum : dieEnumList) {
			    
			    if("Z_ST_CHANGE_MATERIAL_MOLD".equals(functionName)){//표준CT, 표준Cavity, 개발CT, 개발Cavity는 최초 생성시에만 값을 넣어줌
				boolean isContinue = false;
				
				if("spObjectiveCT".equals(erpPartDieEnum.getAttrCode()) ){
				    isContinue = true;
				}
				if("spCavityStd".equals(erpPartDieEnum.getAttrCode()) ){
				    isContinue = true;
				}
				if("spDEVCavityStd".equals(erpPartDieEnum.getAttrCode()) ){
				    isContinue = true;
				}
				if("spDEVObjectiveCT".equals(erpPartDieEnum.getAttrCode()) ){
				    isContinue = true;
				}
				
				if(isContinue){
				    continue;
				}
				
			    }
			    
			    Kogger.info(getClass(),
				    "ErpField : " + erpPartDieEnum.getErpField() + " PLM Attr: " + erpPartDieEnum.getAttrCode());
			    Kogger.info(getClass(), "ErpValue : " + checkErpValue(BeanUtils.getProperty(dto, erpPartDieEnum.getAttrCode())));
			    dieTable.setValue(checkErpValue(BeanUtils.getProperty(dto, erpPartDieEnum.getAttrCode())),
				    erpPartDieEnum.getErpField());
			}
		    }

		    // mapping 처리
		    Table mapTable = function.getTableParameterList().getTable("IT_TYPE7I");
		    ErpPartDieHalbEnum[] dieHalbEnumList = ErpPartDieHalbEnum.values();
		    for (ErpPartDieHalbDTO dto : dieHalbList) {

			Kogger.info(getClass(), "WTPart7I : " + dto.getDieNumber() + "<=>" + dto.getHalbNumber() + ", eco : " + ecoNo);

			mapTable.appendRow();
			for (ErpPartDieHalbEnum erpPartDieHalbEnum : dieHalbEnumList) {
			    Kogger.info(getClass(),
				    "ErpField : " + erpPartDieHalbEnum.getErpField() + " PLM Attr: " + erpPartDieHalbEnum.getAttrCode());
			    Kogger.info(getClass(),
				    "ErpValue : " + checkErpValue(BeanUtils.getProperty(dto, erpPartDieHalbEnum.getAttrCode())));
			    mapTable.setValue(checkErpValue(BeanUtils.getProperty(dto, erpPartDieHalbEnum.getAttrCode())),
				    erpPartDieHalbEnum.getErpField());
			}
		    }

		    // Mold 처리
		    Table moldTable = function.getTableParameterList().getTable("IT_TYPE8");
		    ErpPartMoldEnum[] moldEnumList = ErpPartMoldEnum.values();
		    for (ErpPartMoldDTO dto : moldList) {

			Kogger.info(getClass(), "WTPart8 : " + dto.getWtpartNumber() + ", eco : " + ecoNo);

			moldTable.appendRow();
			for (ErpPartMoldEnum erpPartMoldEnum : moldEnumList) {
			    Kogger.info(getClass(),
				    "ErpField : " + erpPartMoldEnum.getErpField() + " PLM Attr: " + erpPartMoldEnum.getAttrCode());
			    Kogger.info(getClass(), "ErpValue : "
				    + checkErpValue(BeanUtils.getProperty(dto, erpPartMoldEnum.getAttrCode())));
			    moldTable.setValue(checkErpValue(BeanUtils.getProperty(dto, erpPartMoldEnum.getAttrCode())),
				    erpPartMoldEnum.getErpField());
			}
		    }

		    // ECO NO
		    function.getImportParameterList().getField(ECO_NO_ERP_FIELD).setValue(ecoNo);

		    client.execute(function);

		    ParameterList parameterList = function.getTableParameterList();
		    dieTable = parameterList.getTable("IT_TYPE7");
		    String table2String = dieTable.toString();
		    Kogger.debug(getClass(), "E_RETURN_TABLE[IT_TYPE7]<<<<<< " + table2String);

		    mapTable = parameterList.getTable("IT_TYPE7I");
		    table2String = mapTable.toString();
		    Kogger.debug(getClass(), "E_RETURN_TABLE[IT_TYPE7I]<<<<<< " + table2String);

		    moldTable = parameterList.getTable("IT_TYPE8");
		    table2String = moldTable.toString();
		    Kogger.debug(getClass(), "E_RETURN_TABLE[IT_TYPE8]<<<<<< " + table2String);

		    String eReturn = (String) function.getExportParameterList().getValue("E_RETURN");
		    int eCnt = function.getExportParameterList().getInt("E_CNT");
		    String eMsg = (String) function.getExportParameterList().getValue("E_MSG");

		    Kogger.debug(getClass(), "E_RETURN<<<<<< " + eReturn);
		    Kogger.debug(getClass(), "E_CNT<<<<<< " + eCnt);
		    Kogger.debug(getClass(), "E_MSG<<<<<< " + eMsg);

		    if (eReturn != null && "S".equals((eReturn.toUpperCase()))) {
			Kogger.info(getClass(), "######## SUCCESS part MODIFY ");
			success = true;
		    } else {
			Kogger.info(getClass(), "######## FAIL part MODIFY ");
			success = false;
			returnMap.put(ERP_ERROR_MSG, StringUtil.checkNull(eMsg));
			return success;
		    }
		}
	    }

	    success = true;

	} catch (Exception e) {

	    success = false;

	    Kogger.info(getClass(), "##%%@@$$ ERP-Function 자체 에러 발생 ##%%@@$$");

	    Kogger.error(getClass(), e);
	    throw e;
	}

	return success;
    }

    private void setProdPartTable(WTPart wtPart, Map<String, String> workerNameMap, boolean create) throws Exception {

	ErpPartProdDTO dto = new ErpPartProdDTO();

	WTPartMaster master = (WTPartMaster) wtPart.getMaster();
	KETPartClassification claz = PartClazHelper.service.getPartClassification(wtPart);
	WTPartTypeInterface wtPartTypeInterface = (WTPartTypeInterface) wtPart;
	WTPartTypeInfo wTPartTypeInfo = wtPartTypeInterface.getTypeInfoWTPart();

	Kogger.debug(getClass(), "####### part General :" + wtPart.getNumber() + " : " + wtPart.getVersionDisplayIdentifier().toString()
	        + " : " + CommonUtil.getOIDString(wtPart));

	// 품번
	setValue(dto, wtPart.getNumber(), ErpPartProdEnum.WtpartNumber);

	// 품명
	String partNameHis = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpPartNameHis);
	if (StringUtils.isEmpty(partNameHis)) {
	    partNameHis = master.getName();
	}
	setValue(dto, partNameHis, ErpPartProdEnum.SpPartNameHis);

	// 부품유형 : FERT: 01.완제품(KET), HALB: 02.반제품(KET), HAWA: 03.상품(KET), ROH: 04.원자재(KET), PACK: 05.포장재(KET), SCRP: 06.스크랩(KET)
	String partType = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpPartType);
	PartTypeEnum partTypeEnum = PartTypeEnum.toPartTypeEnum(partType);
	setValue(dto, partTypeEnum.getErpCode(), ErpPartProdEnum.SpPartType);

	// 사업부 : 1.자동차, 2 전자, 3케이티솔루션, 4케이티네트워크 입력시 필수
	String divisionFlag = null;
	if (claz != null) {
	    String divisionCode = claz.getDivisionFlag();
	    if ("C".equals(divisionCode)) {
		divisionFlag = "1";
	    } else if ("E".equals(divisionCode)) {
		divisionFlag = "2";
	    } else if ("K".equals(divisionCode)) {
		divisionFlag = "3";
	    } else if ("N".equals(divisionCode)) {
		divisionFlag = "4";
	    }
	}
	setValue(dto, divisionFlag, ErpPartProdEnum.DivisionFlag);

	// ERP 제품코드 : 부품유형에 따라 구분 전송
	String erpProdCode = null;
	if (claz != null) {
	    erpProdCode = claz.getErpProdCode();
	}
	setValue(dto, erpProdCode, ErpPartProdEnum.ErpProdHalbCode);

	// 기본단위 - EA
	String defaultUnit = this.getPartDefaultUnit(master);

	// ERP 방어코드
	if (useErpDefenseCode) {
	    if ("R".equals(partType)) {
		if ("KET_G".equals(defaultUnit) || "G".equalsIgnoreCase(defaultUnit)) {
		    defaultUnit = "KG";
		}
	    }
	}

	setValue(dto, defaultUnit, ErpPartProdEnum.DefaultUnit);

	//제품군 : 부품분류의 제품군 코드 : 자재유형 FERT,HAWA 는 필수
	//기존 제품군은 활용안하고 제품군1을 활용함. 하지만 기본뷰의 제품군은 필수이기때문에 enum에 매핑정보를 세팅하고 던진다 
	//매핑은 경영기획팀 김한호 대리에게 받음 하지만 기본뷰의 제품군을 추가할수없는 상황이라 어거지로 매핑함..2016.05.13 by 황정태
	String erpProdGroupCode = null;
	String OldErpGroupCode = null;
	if (claz != null) {
	    erpProdGroupCode = claz.getErpProdGroupCode();
	    
	    ErpProdGroupCodeEnum[] arry = ErpProdGroupCodeEnum.values();
	    for (ErpProdGroupCodeEnum item : arry) {
		if(erpProdGroupCode.equals(item.getNewCode())){
		    OldErpGroupCode = item.getOldCode();
		}
	    }
	    System.out.println(":::: claz : "+claz.getClassKrName());
	    System.out.println(":::: erpProdGroupCode : "+erpProdGroupCode);
	    System.out.println(":::: OldErpGroupCode : "+OldErpGroupCode);
	}
	
	
	
	setValue(dto, OldErpGroupCode, ErpPartProdEnum.ErpProdGroupCode);

	// 총중량
	String totalWeight = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpTotalWeight);
	// 순중량
	String netWeight = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpNetWeight);

	// ERP 방어 코드 :
	if (useErpDefenseCode) {
	    try {
		Double.parseDouble(totalWeight);
	    } catch (Exception e) {
		totalWeight = "";
	    }

	    try {
		Double.parseDouble(netWeight);
	    } catch (Exception e) {
		netWeight = "";
	    }

	    try {

		if (StringUtils.isEmpty(totalWeight) && StringUtils.isEmpty(netWeight)) {
		    // nothing
		} else if (StringUtils.isNotEmpty(totalWeight) && StringUtils.isNotEmpty(netWeight)) {
		    if (Double.parseDouble(netWeight) - Double.parseDouble(totalWeight) > 0D) {
			totalWeight = netWeight;
		    }
		} else if (StringUtils.isEmpty(totalWeight) && StringUtils.isNotEmpty(netWeight)) {
		    totalWeight = netWeight;
		} else if (StringUtils.isNotEmpty(totalWeight) && StringUtils.isEmpty(netWeight)) {
		    netWeight = totalWeight;
		}

	    } catch (Exception e) {

	    }
	}

	setValue(dto, totalWeight, ErpPartProdEnum.SpTotalWeight);
	setValue(dto, netWeight, ErpPartProdEnum.SpNetWeight);

	// 스크랩중량
	String scrabWeight = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpScrabWeight);
	setValue(dto, scrabWeight, ErpPartProdEnum.SpScrabWeight);

	// 중량단위- Default 'G' 으로 전송
	setValue(dto, "G", ErpPartProdEnum.SpWeightUnit);

	// 제품 Size
	String prodSize = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpProdSize);
	setValue(dto, prodSize, ErpPartProdEnum.SpProdSize);

	// 스크랩 코드
	String scrabCode = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpScrabCode);

	// ERP 방어 코드 :
	if (useErpDefenseCode) {
	    if (StringUtils.isNotEmpty(scrabCode)) {
		if (!handler.existErpPart(scrabCode)) {
		    scrabCode = "S200155";
		}
	    }
	}
	setValue(dto, scrabCode, ErpPartProdEnum.SpScrabCode);

	// 시리즈
	if ("F".equals(partType) || "W".equals(partType)) { // : FERT와 HAWA만 시리즈를 넘길 수 있다.
	    String series = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpSeries);
	    setValue(dto, series, ErpPartProdEnum.SpSeries);
	}

	// 2016.04.14 추가
	// 친환경여부 : 400 : Yes, 410 : No
	String envFriend = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpEnvFriend);
	if ("Yes".equals(envFriend)) {
	    envFriend = "400";
	} else if ("No".equals(envFriend)) {
	    envFriend = "410";
	} else {
	    envFriend = "";
	    // 2017.07.21 전자사업부는 [No] 로 고정 by 황정태. 요청 박주미
	    if("2".equals(divisionFlag)){//전자사업부
		envFriend = "410";
	    }
	}
	setValue(dto, envFriend, ErpPartProdEnum.SpEnvFriend);

	// 2016.04.25 추가
	// 제품군1
	// setValue(dto, "0" + claz.getErpProdGroupCode(), ErpPartProdEnum.SpProductFamily);
	// 0을 붙이면 사용자 혼란이 가중되므로 데이터는 마이그레이션하고 로직은 기존대로 erp 제품군코드와 동일하게 진행 2016.05.10 by 황정태
	setValue(dto, claz.getErpProdGroupCode(), ErpPartProdEnum.SpProductFamily);

	// 방수여부 : 100: 방수 , 200: 비방수
	String waterProof = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpWaterProof);
	if ("SEALED010".equals(waterProof)) {
	    waterProof = "100";
	} else if ("SEALED020".equals(waterProof)) {
	    waterProof = "200";
	} else {
	    waterProof = null;
	}
	setValue(dto, waterProof, ErpPartProdEnum.SpWaterProof);

	// 2016.04.14 추가
	// 표준커넥터여부 : 700 : Yes, 710 : No, 720 : NA
	
	// 2016.05.27 변경됨
	// 용도 : 700 자동차(표준커넥터) , 710 자동차(기타) , 720 전자 ,730 기타(광통신 외) 
	String standardConnect = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.spStandardConnect);
	
	// 2017.07.21 전자사업부는 [전자] 로 고정 by 황정태. 요청 박주미
	if(StringUtils.isEmpty(standardConnect) && "2".equals(divisionFlag)){//전자사업부
	    standardConnect = "720";
	}
	
	setValue(dto, standardConnect, ErpPartProdEnum.SpStandardConnect);

	// Class No : 부품분류의 Class No ex) RD_01
	String erpClassNo = null;
	if (claz != null) {
	    erpClassNo = claz.getErpClassNo();
	}
	setValue(dto, erpClassNo, ErpPartProdEnum.ErpClassNo);

	// 작성일 : createstampa2 YYYY.MM.DD(형식으로)
	String createstampa2 = DateUtil.getTimeFormat(wtPart.getCreateTimestamp(), "yyyy.MM.dd");
	setValue(dto, createstampa2, ErpPartProdEnum.Createstampa2);
	
	String materialInfo = null;
	
	if(PartTypeEnum.PACK == partTypeEnum){//포장재
	    
	    materialInfo = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpMaterPurch);
	    
	}else{
	    // 재질(비철, 수지) 재질(금형) : 비철, 금형에 따라 별도 LOV
	    // RD_01 일경우는 TEXT 입력 ZSS 비철 자재의 경우 SELECT ex) PBT(GP-1000S)
	    List<PartSpecEnum> materialPartSpecEnumList = PartClazHelper.service.getMaterialPartSpecEnum(wtPart);

	    if (materialPartSpecEnumList != null && materialPartSpecEnumList.size() > 0) {
		for (PartSpecEnum partSpecEnum : materialPartSpecEnumList) {
		    if (partSpecEnum == PartSpecEnum.SpMaterialInfo) {// 수지
			materialInfo = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpMaterialInfo);
			if (StringUtils.isNotEmpty(materialInfo)) {
			    materialInfo = PartBaseHelper.service.getMaterialInfo(materialInfo);
			    break;
			}
		    } else if (partSpecEnum == PartSpecEnum.SpMaterNotFe) {// 비철
			materialInfo = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpMaterNotFe);
			if (StringUtils.isNotEmpty(materialInfo)) {
			    materialInfo = PartBaseHelper.service.getMaterialInfo(materialInfo);
			    break;
			}
		    }
		    // 비철(R11로 시작) - 일단 ERP I/F 중지 : ERP와 PLM의 코드가 동일하지 않아 sync 를 맞추기 어려움
		    // else if (partSpecEnum == PartSpecEnum.SpMaterNotFe) {
		    // materialInfo = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpMaterNotFe);
		    // if (StringUtils.isNotEmpty(materialInfo)) {
		    // }
		    // else {
		    // materialInfo = null;
		    // }
		    // }
		}
	    }
	}
	
	if(PartTypeEnum.FERT == partTypeEnum){//현재 ERP 에서 분류 뷰를 만들어주는 것은 제품에 한정되 있어서 기타 원자재등에 재질정보를 세팅하면 오류발생함 향후 ERP 개선이 되면 해당 if문은 풀어도 됨 2019.07.15 by 황정태
	    setValue(dto, materialInfo, ErpPartProdEnum.SpMaterialInfo);    
	}
	
	// 색상 : 색상 (제약 확인 필요)
	// BLACK, BLUE, BROWN, GRAY, D/GRAY, L/GRAY, GREEN, L/GREEN, IVORY, JADE MAGENTA, NATURAL, ORANGE, RED YELLOW, 기타
	String color = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpColor);
	if (StringUtils.isEmpty(color)) {
	    color = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpColorElec);
	}
	if (StringUtils.isNotEmpty(color)) {
	    boolean colorCheck = false;
	    for (String colorStandard : COLOR_ARRAY) {
		if (colorStandard.equals(color)) {
		    colorCheck = true;
		    break;
		}
	    }
	    color = colorCheck ? color : null;

	    if (!colorCheck) {
		// 전자의 경우는 color를 ERP에 정의한 COLOR만 보냄
		Kogger.debug(getClass(), "##### " + wtPart.getNumber() + "의 컬러 " + color + " 는 ERP TYPE과 맞지 않아 보내지 않습니다.");
	    }

	}
	setValue(dto, color, ErpPartProdEnum.SpColor);

	// 도금
	String plating = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpPlating);
	if (StringUtils.isNotEmpty(plating)) {
	    plating = CodeHelper.manager.getCodeValue(PartSpecEnum.SpPlating.getAttrCodeType(), plating, Locale.KOREA);
	}
	setValue(dto, plating, ErpPartProdEnum.SpPlating);

	// 대표금형
	String representM = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpRepresentM);
	setValue(dto, representM, ErpPartProdEnum.SpRepresentM);

	// YAZAKI여부 : 값이 없는경우 Default NO / YES, NO 'RD_01' 경우만 필수
	String isYazaki = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpIsYazaki);
	if (!"YES".equals(isYazaki))
	    isYazaki = "NO";
	setValue(dto, isYazaki, ErpPartProdEnum.SpIsYazaki);

	// YAZAKI제번
	String yazakiNo = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpYazakiNo);
	setValue(dto, yazakiNo, ErpPartProdEnum.SpYazakiNo);

	// 개발담당자 : ECO의 설계변경부품/도면 탭의 담당자
	// ERP 방어 코드 :
	String workerName = "";

	// useErpDefenseCode
	workerName = (workerNameMap != null) ? workerNameMap.get(master.getNumber()) : "관리자";

	setValue(dto, workerName, ErpPartProdEnum.WorkerId);

	if (create)
	    prodCreateList.add(dto);
	else
	    prodModifyList.add(dto);

    }

    private void setDiePartTable(WTPart wtPart, boolean create) throws Exception {

	ErpPartDieDTO dto = new ErpPartDieDTO();

	WTPartMaster master = (WTPartMaster) wtPart.getMaster();
	WTPartTypeInterface wtPartTypeInterface = (WTPartTypeInterface) wtPart;
	WTPartTypeInfo wTPartTypeInfo = wtPartTypeInterface.getTypeInfoWTPart();

	Kogger.debug(getClass(), "####### part Die :" + wtPart.getNumber() + " : " + wtPart.getVersionDisplayIdentifier().toString()
	        + " : " + CommonUtil.getOIDString(wtPart));

	// 품번
	setValue(dto, wtPart.getNumber(), ErpPartDieEnum.WtpartNumber);

	// 사급구분 : 1 (사내), 2 (외주) :
	String spMContractSAt = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpMContractSAt);
	setValue(dto, spMContractSAt, ErpPartDieEnum.SpMContractSAt);

	// 금형구분 : 1 (프레스), 2 (사출), 3 (기타)
	String spMMoldAt = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpMMoldAt);
	setValue(dto, spMMoldAt, ErpPartDieEnum.SpMMoldAt);

	// 제작구분 : 1 (자체), 2 (OEM), 3 (대여), 4 (Yazaki), 5 (타사금형), 9 (기타)
	String spMMakingAt = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpMMakingAt);
	setValue(dto, spMMakingAt, ErpPartDieEnum.SpMMakingAt);

	// 생산구분 : [ZGBN4] 1 (SET), 2 (추가), 3 (Modify)
	String spMProdAt = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpMProdAt);
	setValue(dto, spMProdAt, ErpPartDieEnum.SpMProdAt);

	// 구매담당자 : 구매 그룹
	String spPuchaseGroup = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpPuchaseGroup);
	setValue(dto, spPuchaseGroup, ErpPartDieEnum.SpPuchaseGroup);

	// 플랜트
	String spPlant = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpPlant);
	setValue(dto, spPlant, ErpPartDieEnum.SpPlant);

	// 개발담당자 : 개발담당자명
	String spDevManNm = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpDevManNm);
	setValue(dto, spDevManNm, ErpPartDieEnum.SpDevManNm);

	// 금형담당자 : 설계담당자명
	String spDesignManNm = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpDesignManNm);
	setValue(dto, spDesignManNm, ErpPartDieEnum.SpDesignManNm);

	// 제작담당자 : 금형제작처 - 사급구분이 사내일 경우 제작담당자, 사급구분이 외주일 경우 금형제작처
	String spManufacManNm = null;
	if ("1".equals(spMContractSAt)) { // 사내
	    spManufacManNm = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpManufacManNm);
	} else if ("2".equals(spMContractSAt)) { // 외주
	    spManufacManNm = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpDieWhere);

	    try {
		// 외주일 경우 코드가 아닌 명(Name)으로 ERP에 넘겨주어야 한다.
		PartBaseDao partBaseDao = new PartBaseDao();
		String partnerName = partBaseDao.getPartnerName(spManufacManNm);
		if (partnerName != null && !partnerName.equals("")) {
		    spManufacManNm = partnerName;
		}

	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }
	}
	setValue(dto, spManufacManNm, ErpPartDieEnum.SpManufacManNm);

	if (create) {
	    // 목표 C/T (SPM)
	    String spObjectiveCT = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpObjectiveCT);
	    setValue(dto, spObjectiveCT, ErpPartDieEnum.SpObjectiveCT);//표준
	    setValue(dto, spObjectiveCT, ErpPartDieEnum.SpDEVObjectiveCT);//개발
	    // Cavity
	    String spCavityStd = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpCavityStd);
	    setValue(dto, spCavityStd, ErpPartDieEnum.SpCavityStd);//표준
	    setValue(dto, spCavityStd, ErpPartDieEnum.SpDEVCavityStd);//개발
	    
	    dieCreateList.add(dto);
	}

	else {
	    dieModifyList.add(dto);
	}
	    

	// /////////////////////////////////////////////////////////////////////////////////////////
	// 매핑자재 - 반제품의 매핑자재
	// /////////////////////////////////////////////////////////////////////////////////////////

	List<KETHalbPartDieSetPartLink> linkList = query.queryForListLinkByRoleB(KETHalbPartDieSetPartLink.class, WTPartMaster.class,
	        master);

	if (linkList.size() == 0) {
	    errorLogList.add("## Essential Check Error : DieHalbNo:" + wtPart.getNumber() + " - Die No에 연결된 Halb Part empty !! ");

	    // ERP 방어코드
	    if (useErpDefenseCode) {

		ErpPartDieHalbDTO mDto = new ErpPartDieHalbDTO();

		// 금형 번호 : 제품일 경우 대표금형, 금형일 경우 금형번호 / CHAR(8)
		String dieNumber = master.getNumber();
		setValue(mDto, dieNumber, ErpPartDieHalbEnum.DieNumber);

		// 부품번호 : 제품일 경우 부품번호, 금형일 경우 링크 부품번호 / CHAR(18)
		String halbNumber = "H320099";

		setValue(mDto, halbNumber, ErpPartDieHalbEnum.HalbNuumber);

		if (create)
		    this.dieHalbCreateList.add(mDto);
		else
		    this.dieHalbModifyList.add(mDto);

	    }
	} else {

	    for (KETHalbPartDieSetPartLink link : linkList) {

		WTPartMaster halbMaster = link.getHalb();

		ErpPartDieHalbDTO mDto = new ErpPartDieHalbDTO();

		// 부품번호 : 제품일 경우 부품번호, 금형일 경우 링크 부품번호 / CHAR(18)
		String halbNumber = halbMaster.getNumber();

		// ERP 방어코드
		if (useErpDefenseCode) {
		    if (!handler.existErpPart(halbNumber)) {
			halbNumber = "H320099";
		    } else {

			WTPart tempPart = PartBaseHelper.service.getLatestPart(halbNumber);
			if (tempPart != null) {
			    String partType = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpPartType);
			    PartTypeEnum partTypeEnum = PartTypeEnum.toPartTypeEnum(partType);
			    if (partTypeEnum != PartTypeEnum.HALB) {
				// continue;
				halbNumber = "H320099";
			    }
			}
		    }
		}

		setValue(mDto, halbNumber, ErpPartDieHalbEnum.HalbNuumber);

		// 금형 번호 : 제품일 경우 대표금형, 금형일 경우 금형번호 / CHAR(8)
		String dieNumber = wtPart.getNumber();
		setValue(mDto, dieNumber, ErpPartDieHalbEnum.DieNumber);

		if (create)
		    this.dieHalbCreateList.add(mDto);
		else
		    this.dieHalbModifyList.add(mDto);
	    }
	}

    }

    private void setMoldPartTable(WTPart wtPart, boolean create) throws Exception {

	ErpPartMoldDTO dto = new ErpPartMoldDTO();

	WTPartMaster master = (WTPartMaster) wtPart.getMaster();
	// KETPartClassification claz = PartClazHelper.service.getPartClassification(wtPart);
	WTPartTypeInterface wtPartTypeInterface = (WTPartTypeInterface) wtPart;
	WTPartTypeInfo wTPartTypeInfo = wtPartTypeInterface.getTypeInfoWTPart();

	Kogger.debug(getClass(), "#######  part Mold :" + wtPart.getNumber() + " : " + wtPart.getVersionDisplayIdentifier().toString()
	        + " : " + CommonUtil.getOIDString(wtPart));

	// 품번
	setValue(dto, wtPart.getNumber(), ErpPartMoldEnum.WtpartNumber);

	// 금형번호 / CHAR(18)
	String dieNumber = wtPart.getNumber();
	if (dieNumber.indexOf("-") != -1) {
	    dieNumber = dieNumber.substring(0, dieNumber.indexOf("-"));
	}
	setValue(dto, dieNumber, ErpPartMoldEnum.DieNumber);

	// 품명 // CHAR(40)
	String partNameHis = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpPartNameHis);
	if (StringUtils.isEmpty(partNameHis)) {
	    partNameHis = master.getName();
	}
	setValue(dto, partNameHis, ErpPartMoldEnum.SpPartNameHis);

	// 기본단위 - EA
	String defaultUnit = this.getPartDefaultUnit(master);
	setValue(dto, defaultUnit, ErpPartMoldEnum.DefaultUnit);

	// 부품유형 : FERT: 01.완제품(KET), HALB: 02.반제품(KET), HAWA: 03.상품(KET), ROH: 04.원자재(KET), PACK: 05.포장재(KET), SCRP: 06.스크랩(KET)
	// String partType = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpPartType);
	// PartTypeEnum partTypeEnum = PartTypeEnum.toPartTypeEnum(partType);
	// setValue(dto, partTypeEnum.getErpCode(), ErpPartMoldEnum.SpPartType);
	setValue(dto, "DIEM", ErpPartMoldEnum.SpPartType);

	// ERP 제품코드 : 부품유형에 따라 구분 전송
	setValue(dto, "502000", ErpPartMoldEnum.ErpProdHalbCode);

	// 재질(금형) : ex) S04
	String spMaterDie = PartSpecGetter.getPartSpecWithType(wTPartTypeInfo, PartSpecEnum.SpMaterDie);
	setValue(dto, spMaterDie, ErpPartMoldEnum.SpMaterDie);

	if (create)
	    moldCreateList.add(dto);
	else
	    moldModifyList.add(dto);
    }

    private String getPartDefaultUnit(WTPartMaster master) {
	String defaultUnit = master.getDefaultUnit().toString();
	if (defaultUnit != null && defaultUnit.startsWith("KET_"))
	    defaultUnit = defaultUnit.replace("KET_", "");

	if ("PERCENT".equals(defaultUnit))
	    defaultUnit = "%";

	return defaultUnit;
    }

    private String checkErpValue(String str) {

	if (str == null)
	    return SPACE;
	else if ("".equals(str))
	    return SPACE;
	else
	    return str;
    }

    // //////////////////////////////////
    // validation & value Set
    // //////////////////////////////////

    // die part
    private void setValue(ErpPartDieDTO dto, String value, ErpPartDieEnum erpPartDieEnum) throws Exception {

	// if (erpPartDieEnum.isEssential() && StringUtils.isEmpty(value)) {
	// errorLogList.add("## Essential Check Error : DieNo:" + dto.getWtpartNumber() + " - " + erpPartDieEnum.getAttrCode() +
	// " is empty !! ");
	// }
	//
	// if (value != null && erpPartDieEnum.getDataLength() < getDataLength(value)) {
	// errorLogList.add("## Length Check Error : DieNo:" + dto.getWtpartNumber() + " - " + erpPartDieEnum.getAttrCode() +
	// " length is over ");
	// errorLogList.add("## Value :=>" + value + "<=");
	// errorLogList.add("## Length erp :" + erpPartDieEnum.getDataLength() + " plm : " + getDataLength(value));
	// }
	//
	// Kogger.debug(getClass(), "## No:" + dto.getWtpartNumber() + "::" + erpPartDieEnum.getAttrCode() + " Value =>" + value + "<=");

	BeanUtils.setProperty(dto, erpPartDieEnum.getAttrCode(), checkErpValue(value));
    }

    // die part's halb part
    private void setValue(ErpPartDieHalbDTO dto, String value, ErpPartDieHalbEnum erpPartDieHalbEnum) throws Exception {

	// if (erpPartDieHalbEnum.isEssential() && StringUtils.isEmpty(value)) {
	// errorLogList.add("## Essential Check Error : Die&HalbNo:" + dto.getDieNumber() + " - " + erpPartDieHalbEnum.getAttrCode() +
	// " is empty !! ");
	// }
	//
	// if (value != null && erpPartDieHalbEnum.getDataLength() < getDataLength(value)) {
	// errorLogList.add("## Length Check Error : DieNo:" + dto.getDieNumber() + " - " + erpPartDieHalbEnum.getAttrCode() +
	// " length is over ");
	// errorLogList.add("## Value :=>" + value + "<=");
	// errorLogList.add("## Length erp :" + erpPartDieHalbEnum.getDataLength() + " plm : " + getDataLength(value));
	// }
	//
	// Kogger.debug(getClass(), "## No:" + dto.getDieNumber() + "::" + erpPartDieHalbEnum.getAttrCode() + " Value =>" + value + "<=");

	BeanUtils.setProperty(dto, erpPartDieHalbEnum.getAttrCode(), checkErpValue(value));
    }

    // mold part
    private void setValue(ErpPartMoldDTO dto, String value, ErpPartMoldEnum erpPartMoldEnum) throws Exception {

	// if (erpPartMoldEnum.isEssential() && StringUtils.isEmpty(value)) {
	// errorLogList.add("## Essential Check Error : MoldNo:" + dto.getWtpartNumber() + " - " + erpPartMoldEnum.getAttrCode() +
	// " is empty !! ");
	// }
	//
	// if (value != null && erpPartMoldEnum.getDataLength() < getDataLength(value)) {
	// errorLogList.add("## Length Check Error : DieNo:" + dto.getWtpartNumber() + " - " + erpPartMoldEnum.getAttrCode() +
	// " length is over ");
	// errorLogList.add("## Value :=>" + value + "<=");
	// errorLogList.add("## Length erp :" + erpPartMoldEnum.getDataLength() + " plm : " + getDataLength(value));
	// }
	//
	// Kogger.debug(getClass(), "## No:" + dto.getWtpartNumber() + "::" + erpPartMoldEnum.getAttrCode() + " Value =>" + value + "<=");

	BeanUtils.setProperty(dto, erpPartMoldEnum.getAttrCode(), checkErpValue(value));
    }

    // prod part
    private void setValue(ErpPartProdDTO dto, String value, ErpPartProdEnum erpPartProdEnum) throws Exception {

	// if (erpPartProdEnum.isEssential() && StringUtils.isEmpty(value)) {
	// errorLogList.add("## Essential Check Error : ProdNo:" + dto.getWtpartNumber() + " - " + erpPartProdEnum.getAttrCode() +
	// " is empty !! ");
	// }
	//
	// if (value != null && erpPartProdEnum.getDataLength() < getDataLength(value)) {
	// errorLogList.add("## Length Check Error : DieNo:" + dto.getWtpartNumber() + " - " + erpPartProdEnum.getAttrCode() +
	// " length is over ");
	// errorLogList.add("## Value :=>" + value + "<=");
	// errorLogList.add("## Length erp :" + erpPartProdEnum.getDataLength() + " plm : " + getDataLength(value));
	// }
	//
	// Kogger.debug(getClass(), "## No:" + dto.getWtpartNumber() + "::" + erpPartProdEnum.getAttrCode() + " Value =>" + value + "<=");

	BeanUtils.setProperty(dto, erpPartProdEnum.getAttrCode(), checkErpValue(value));
    }

    // private int getDataLength(String value) {
    //
    // if (value == null)
    // return 0;
    //
    // int dataLength = 0;
    //
    // if (SAP_DATA_SIZE == 2) {
    //
    // int maxLength = 0;
    // int len = value.length();
    // for (int k = 0; k < len; k++) {
    // char charChar = value.charAt(k);
    // int code = (int) charChar;
    // if (code > 128) {
    // maxLength = maxLength + 2;
    // } else {
    // maxLength = maxLength + 1;
    // }
    // }
    //
    // return maxLength;
    //
    // } else if (SAP_DATA_SIZE == 3) {
    // return value.getBytes().length;
    // } else {
    // return 0;
    // }
    // }

    public static void main(String[] args) throws Exception {

	// ErpPartInterFace erpPartIf = new ErpPartInterFace();
	// List<WTPart> iPartList = new ArrayList<WTPart>();
	// erpPartIf.sendPartInfoToErp(iPartList, "1410-999", "김철수", null, null);

    }

}
