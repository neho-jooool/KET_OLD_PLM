package ext.ket.part.migration.erp.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import wt.fc.IdentityHelper;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.part.QuantityUnit;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartMasterIdentity;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.base.service.internal.associate.AssociateClaz;
import ext.ket.part.base.service.internal.associate.PartClassificationRelation;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETHalbPartDieSetPartLink;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.dto.CommonCodeDTO;
import ext.ket.part.migration.base.PartPropDTO;
import ext.ket.part.migration.base.PartPropLoader;
import ext.ket.part.migration.dao.StampDaoFactory;
import ext.ket.part.migration.dao.StampDaoManager;
import ext.ket.part.migration.dao.StampRowSetStrategy;
import ext.ket.part.migration.erp.ErpDiePartDTO;
import ext.ket.part.migration.erp.ErpMoldPartDTO;
import ext.ket.part.migration.erp.ErpPartLoader;
import ext.ket.part.migration.erp.ErpProdPartDTO;
import ext.ket.part.migration.mater.PartMaterDTO;
import ext.ket.part.migration.mater.PartMaterLoader;
import ext.ket.part.migration.revision.PartRevisionDTO;
import ext.ket.part.spec.util.PartTypeEnum;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartSpecSetter;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class StandardKetMigService extends StandardManager implements KetMigService {
    
    private static final long serialVersionUID = 1L;
    private MigLogUtil migLogUtil = new MigLogUtil();
    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
    private Map<String, KETPartClassification> clazMap = new HashMap<String, KETPartClassification>();

    public static StandardKetMigService newStandardKetMigService() throws WTException {
	StandardKetMigService instance = new StandardKetMigService();
	instance.initialize();
	return instance;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////
    // 부품의 일부만 처리
    ////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public void migErpProd(String filePath, String fromPartType, List<ErpProdPartDTO> prodList) throws Exception{
	
	Kogger.debug(getClass(), "****************  Excel Extract Start " + fromPartType + " **************************");
	updateProd(prodList);
	Kogger.debug(getClass(), "****************  Excel Extract End " + fromPartType + " **************************");
	
    }
    
    @Override
    public void migErpDie(String filePath, String fromPartType, List<ErpDiePartDTO> dieList) throws Exception{
	
	Kogger.debug(getClass(), "****************  Excel Extract Start " + fromPartType + " **************************");
	updateDie(dieList);
	Kogger.debug(getClass(), "****************  Excel Extract End " + fromPartType + " **************************");
    }
    
    @Override
    public void migErpMold(String filePath, String fromPartType, List<ErpMoldPartDTO> moldList) throws Exception{
	
	Kogger.debug(getClass(), "****************  Excel Extract Start " + fromPartType + " **************************");
	updateMold(moldList);
	Kogger.debug(getClass(), "****************  Excel Extract End " + fromPartType + " **************************");
	
    }
    
    ////////////////////////////////////////////////////////////////////////////////////
    // 부품의 제품, DIE, Mold로 구분해서 처리
    ////////////////////////////////////////////////////////////////////////////////////
    
    public void migErpProd(String filePath) throws Exception{
	try {
	    
	    // "FERT" , "HALB", 
	    String[] fileTypeArray = new String[] { "HAWA", "ROH", "PACK", "SCRP"
		        , "FERT10", "FERT11","FERT12","FERT13","FERT14","FERT15","FERT16","FERT17","FERT18","FERT19","FERT20"
			, "FERT21"
			, "HALB10", "HALB11","HALB12","HALB13","HALB14","HALB15","HALB16","HALB17","HALB18","HALB19","HALB20"
			, "HALB21", "HALB22","HALB23","HALB24","HALB25","HALB26","HALB27"
	    };
	    ErpPartLoader loader = new ErpPartLoader();
	    
	    for (String fileType : fileTypeArray) {
		
		Kogger.debug(getClass(), "****************  Excel Extract Start " + fileType + " **************************");
		loader.load(filePath, fileType);
		
		List<ErpProdPartDTO> prodList = loader.getProdList();
		updateProd(prodList);
		prodList.clear();

		Kogger.debug(getClass(), "****************  Excel Extract End " + fileType + " **************************");
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	}
    }
    
    public void migErpDie(String filePath) throws Exception{
	try {

	    String[] fileTypeArray = new String[] { "CAST" };
	    ErpPartLoader loader = new ErpPartLoader();
	    
	    for (String fileType : fileTypeArray) {
				
		Kogger.debug(getClass(), "****************  Excel Extract Start " + fileType + " **************************");
		loader.load(filePath, fileType);
		
		List<ErpDiePartDTO> dieList = loader.getDieList();
		updateDie(dieList);
		dieList.clear();
		
		Kogger.debug(getClass(), "****************  Excel Extract End " + fileType + " **************************");
	    }
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	}
    }
    
    public void migErpMold1(String filePath) throws Exception{
	try {

	    String[] fileTypeArray = new String[] {
		      "DIEM_01", "DIEM_02", "DIEM_03", "DIEM_04", "DIEM_05", "DIEM_06" };
	    
	    ErpPartLoader loader = new ErpPartLoader();
	    
	    for (String fileType : fileTypeArray) {
				
		Kogger.debug(getClass(), "****************  Excel Extract Start " + fileType + " **************************");
		loader.load(filePath, fileType);
		
		List<ErpMoldPartDTO> moldList = loader.getMoldList();
		updateMold(moldList);
		moldList.clear();
		
		Kogger.debug(getClass(), "****************  Excel Extract End " + fileType + " **************************");
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	}
    }
    
    public void migErpMold2(String filePath) throws Exception{
	try {
	    
	    String[] fileTypeArray = new String[] {
		     "DIEM_07", "DIEM_08", "DIEM_09", "DIEM_10", "DIEM_11", "DIEM_12", "DIEM_13", "DIEM_14" };
	    
	    ErpPartLoader loader = new ErpPartLoader();
	    
	    for (String fileType : fileTypeArray) {
		
		Kogger.debug(getClass(), "****************  Excel Extract Start " + fileType + " **************************");
		loader.load(filePath, fileType);
		
		List<ErpMoldPartDTO> moldList = loader.getMoldList();
		updateMold(moldList);
		moldList.clear();
		
		Kogger.debug(getClass(), "****************  Excel Extract End " + fileType + " **************************");
	    }
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	}
    }
    
    ////////////////////////////////////////////////////////////////////////////////////
    // 부품의 Excel파일 하나에 하나로 구분해서 처리
    ////////////////////////////////////////////////////////////////////////////////////
    
    public void migErpOnePartType(String filePath, String partType) throws Exception{
	
	try {
	    
	    // "FERT" , "HALB", 
	    String[] fileTypeArray = new String[] { "HAWA", "ROH", "PACK", "SCRP"
		    	, "FERT10", "FERT11","FERT12","FERT13","FERT14","FERT15","FERT16","FERT17","FERT18","FERT19","FERT20"
			, "FERT21"
			, "HALB10", "HALB11","HALB12","HALB13","HALB14","HALB15","HALB16","HALB17","HALB18","HALB19","HALB20"
			, "HALB21", "HALB22","HALB23","HALB24","HALB25","HALB26","HALB27"
	    
	    };
	    ErpPartLoader loader = new ErpPartLoader();
	    
	    for (String fileType : fileTypeArray) {
		
		if(!fileType.equals(partType)) continue;
		
		Kogger.debug(getClass(), "****************  Excel Extract Start " + fileType + " **************************");
		loader.load(filePath, fileType);
		
		List<ErpProdPartDTO> prodList = loader.getProdList();
		updateProd(prodList);
		prodList.clear();
		
		Kogger.debug(getClass(), "****************  Excel Extract End " + fileType + " **************************");
	    }
	    
	    fileTypeArray = new String[] { "CAST" };
	    
	    for (String fileType : fileTypeArray) {
		
		if(!fileType.equals(partType)) continue;
		
		Kogger.debug(getClass(), "****************  Excel Extract Start " + fileType + " **************************");
		loader.load(filePath, fileType);
		
		List<ErpDiePartDTO> dieList = loader.getDieList();
		updateDie(dieList);
		dieList.clear();
		
		Kogger.debug(getClass(), "****************  Excel Extract End " + fileType + " **************************");
	    }
	    
	    fileTypeArray = new String[] {
		      "DIEM_01", "DIEM_02", "DIEM_03", "DIEM_04", "DIEM_05", "DIEM_06", "DIEM_07", "DIEM_08"
		    , "DIEM_09", "DIEM_10", "DIEM_11", "DIEM_12", "DIEM_13", "DIEM_14" };
	    
	    for (String fileType : fileTypeArray) {
		
		if(!fileType.equals(partType)) continue;
		
		Kogger.debug(getClass(), "****************  Excel Extract Start " + fileType + " **************************");
		loader.load(filePath, fileType);
		
		List<ErpMoldPartDTO> moldList = loader.getMoldList();
		updateMold(moldList);
		moldList.clear();
		
		Kogger.debug(getClass(), "****************  Excel Extract End " + fileType + " **************************");
	    }
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	}
    }
    
    private void updateMold(List<ErpMoldPartDTO> moldList)throws Exception {

	Transaction trx = null;
	Map<String, String> materDieMap = getNumberCodeMapByCodeKey(PartSpecEnum.SpMaterDie.getAttrCodeType()); // "SPECMATERIALMOLD"
	
	try {
	    
   	    for (ErpMoldPartDTO dto : moldList) {

   		String partNo = dto.getPartNo();
   		Kogger.debug(getClass(), "# M partNo:" + partNo + " " + dto.getExcelFileName());
   		WTPartMaster partMaster = getMaster(partNo);
   		if (partMaster == null) {
   		    Kogger.debug(getClass(), "####### partNo:" + partNo + " " + dto.getExcelFileName() + " not found!");
   		    migLogUtil.log("ERP_PART", partMaster, "PartNotFound", "부품 부재", partNo, partNo);
   		    continue;
   		}
   		
   		trx = new Transaction();
		trx.start();
   		
   		WTPart wtPart = getLatestObject(partMaster);
   		// 부품명 수정
   		if (!wtPart.getName().equals(dto.getPartName())) {
   		    WTPartMasterIdentity identity = (WTPartMasterIdentity) partMaster.getIdentificationObject();
   		    identity.setName(dto.getPartName());
   		    partMaster = (WTPartMaster) IdentityHelper.service.changeIdentity(partMaster, identity);
   		}

   		// 단위
   		if (!(partMaster.getDefaultUnit().toString()).equalsIgnoreCase("KET_" + dto.getPartUnit()) && StringUtils.isNotEmpty(dto.getPartUnit())) {
   		    if("%".equals(dto.getPartUnit())){
			dto.setPartUnit("PERCENT");
		    }
   		    partMaster.setDefaultUnit(QuantityUnit.toQuantityUnit("KET_" + dto.getPartUnit()));
   		    partMaster = (WTPartMaster) PersistenceHelper.manager.modify(partMaster);
   		}
   		
   		// BOMFLAG
   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpBOMFlag, "OLD");

   		// Part TYPE
   		if ("DIEM".equals(dto.getPartType())) {
   		    String nowValue = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
   		    if (!"M".equals(nowValue)) {
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartType, "M");
   		    }
   		}

   		// 재질 spMaterDie : 재질 code값이 excel에 들어 있음
   		if(StringUtils.isEmpty(dto.getSpMaterDie())){
   		    
   		}else{
   		    if(materDieMap.containsKey(dto.getSpMaterDie())){
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMaterDie, dto.getSpMaterDie());
   		    }else{
   			migLogUtil.log("ERP_PART", wtPart, "materialNotFound", "재질 코드 부재", dto.getSpMaterDie(), partNo);
   		    }
   		}

   		// 삭제 spIsDeleted
   		if (StringUtils.isNotEmpty(dto.getSpIsDeleted())) {
   		    if ("X".equals(dto.getSpIsDeleted())) {
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, "Y");
   		    } else {
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, "N");
   		    }
   		} else {
   		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, "N");
   		}

   		// save - update
   		PersistenceServerHelper.manager.update(wtPart);

   		wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);

   		trx.commit();
   		trx = null;
   		dto = null;
   		wtPart = null;
   		/**
   select m.wtpartnumber, m.name, M.DEFAULTUNIT, p.PTC_STR_3TYPEINFOWTPART partType
   , PTC_STR_51TYPEINFOWTPART material, PTC_STR_5TYPEINFOWTPART deletedFlag
   , P.VERSIONIDA2VERSIONINFO revision 
   from wtpartmaster m, wtpart p
   where 1=1
   and m.ida2a2 = P.IDA3MASTERREFERENCE
   and P.LATESTITERATIONINFO = 1 
   and wtpartnumber in ('07884000-006', '10617001-048')
   		 */
   	    }

   	} catch (Exception e) {
   	    Kogger.error(getClass(), e);
   	    trx.rollback();
   	    throw e;
   	}
       }
    
       private void updateDie(List<ErpDiePartDTO> dieList)throws Exception {
	   
	Transaction trx = null;
	Map<String, String> spMContractSAtMap = getNumberCodeMapByCodeKey(PartSpecEnum.SpMContractSAt.getAttrCodeType());
	Map<String, String> spMMoldAtMap = getNumberCodeMapByCodeKey(PartSpecEnum.SpMMoldAt.getAttrCodeType());
	Map<String, String> spMMakingAtMap = getNumberCodeMapByCodeKey(PartSpecEnum.SpMMakingAt.getAttrCodeType());
	Map<String, String> spMProdAtMap = getNumberCodeMapByCodeKey(PartSpecEnum.SpMProdAt.getAttrCodeType());
	Map<String, String> spPuchaseGroupMap = getNumberCodeMapByCodeKey(PartSpecEnum.SpPuchaseGroup.getAttrCodeType());
	Map<String, String> spPlantMap = getNumberCodeMapByCodeKey(PartSpecEnum.SpPlant.getAttrCodeType());
	
   	try {

   	    for (ErpDiePartDTO dto : dieList) {

   		String partNo = dto.getPartNo();
   		Kogger.debug(getClass(), "# D partNo:" + partNo);
   		WTPartMaster partMaster = getMaster(partNo);
   		if (partMaster == null) {
   		    Kogger.debug(getClass(), "####### partNo:" + partNo + " not found!");
   		    migLogUtil.log("ERP_PART", partMaster, "PartNotFound", "부품 부재", partNo, partNo);
   		    continue;
   		}
   		
   		trx = new Transaction();
		trx.start();
		
   		WTPart wtPart = getLatestObject(partMaster);

   		// BOMFLAG
   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpBOMFlag, "OLD");
   		
   		// Part TYPE
   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartType, "D");
   		
   		// 사급구분
   		if(StringUtils.isEmpty(dto.getSpMContractSAt())){
   		}else{
   		    if(spMContractSAtMap.containsKey(dto.getSpMContractSAt())){
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMContractSAt, dto.getSpMContractSAt());
   		    }else{
   			migLogUtil.log("ERP_PART", wtPart, "SaGubAt", "사급구분 코드 부재", dto.getSpMContractSAt(), partNo);
   		    }
   		}
   		
   		// 금형구분
   		if(StringUtils.isEmpty(dto.getSpMMoldAt())){
   		}else{
   		    if(spMMoldAtMap.containsKey(dto.getSpMMoldAt())){
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMMoldAt, dto.getSpMMoldAt());
   		    }else{
   			migLogUtil.log("ERP_PART", wtPart, "MoldAt", "금형구분 코드 부재", dto.getSpMMoldAt(), partNo);
   		    }
   		}

   		// 제작구분
   		if(StringUtils.isEmpty(dto.getSpMMakingAt())){
   		}else{
   		    if(spMMakingAtMap.containsKey(dto.getSpMMakingAt())){
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMMakingAt, dto.getSpMMakingAt());
   		    }else{
   			migLogUtil.log("ERP_PART", wtPart, "ManufAt", "제작구분 코드 부재", dto.getSpMMakingAt(), partNo);
   		    }
   		}
   		
   		// 생산구분
   		if(StringUtils.isEmpty(dto.getSpMProdAt())){
   		}else{
   		    if(spMProdAtMap.containsKey(dto.getSpMProdAt())){
   			if("1".equals(dto.getSpMProdAt()) || "2".equals(dto.getSpMProdAt())){
   			    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMProdAt, dto.getSpMProdAt());
   			}
   		    }else{
   			migLogUtil.log("ERP_PART", wtPart, "ProdAt", "생산구분 코드 부재", dto.getSpMProdAt(), partNo);
   		    }
   		}
   		
   		// 구매그룹
   		if(StringUtils.isEmpty(dto.getSpPuchaseGroup())){
   		}else{
   		    if(spPuchaseGroupMap.containsKey(dto.getSpPuchaseGroup())){
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPuchaseGroup, dto.getSpPuchaseGroup());
   		    }else{
   			migLogUtil.log("ERP_PART", wtPart, "P-GroupAt", "구매그룹 코드 부재", dto.getSpPuchaseGroup(), partNo);
   		    }
   		}
   		
   		// 플랜트
   		if(StringUtils.isEmpty(dto.getSpPlant())){
   		}else{
   		    if(spPlantMap.containsKey(dto.getSpPlant())){
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPlant, dto.getSpPlant());
   		    }else{
   			migLogUtil.log("ERP_PART", wtPart, "Plant", "플랜트 코드 부재", dto.getSpPlant(), partNo);
   		    }
   		}
   		
   		// 업체번호 - 금형제작처
//   		String tempDieWhere = dto.getSpDieWhere(); 
//   		if(StringUtils.isNotEmpty(tempDieWhere)){
//   		    int tempDieWhereInt = Integer.parseInt(tempDieWhere);
//   		    tempDieWhere = String.valueOf(tempDieWhereInt);
//   		} 
   		// 외주 생산처 정보는 등록하지 않음
//   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpDieWhere, tempDieWhere);
   		// 개발담당자
   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpDevManNm, dto.getSpDevManNm());
   		// 설계담당자 - 금형담당자
   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpDesignManNm, dto.getSpDesignManNm());
   		// 제작담당자
   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpManufacManNm, dto.getSpManufacManNm());
   		// 표준SPM(C/
   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpObjectiveCT, dto.getSpObjectiveCT());
   		// 표준 Cavity
   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpCavityStd, dto.getSpCavityStd());
   		// 자재코드 - 반제품 연결
   		if (StringUtils.isNotEmpty(dto.getPartHalb())) {
   		    WTPartMaster halbPartMaster = getMaster(dto.getPartHalb());
   		    if (halbPartMaster != null) {
   			if (!hasDieHalbRelation(partMaster, halbPartMaster.getNumber())) {
   			    KETHalbPartDieSetPartLink hdLink = KETHalbPartDieSetPartLink.newKETHalbPartDieSetPartLink(halbPartMaster, partMaster);
   			    PersistenceHelper.manager.save(hdLink);
   			}
   		    }else{
   			migLogUtil.log("ERP_PART", partMaster, "Die-HalbPartNotFound", "부품 부재", dto.getPartHalb(), partNo);
   		    }
   		}

   		// 삭제 spIsDeleted
   		if (StringUtils.isNotEmpty(dto.getSpIsDeleted())) {
   		    if ("X".equals(dto.getSpIsDeleted())) {
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, "Y");
   		    } else {
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, "N");
   		    }
   		} else {
   		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, "N");
   		}

   		// save - update
   		PersistenceServerHelper.manager.update(wtPart);

   		wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);

   		trx.commit();
   		trx = null;
   		dto = null;
   		wtPart = null;
   		/**
   select m.wtpartnumber, p.PTC_STR_3TYPEINFOWTPART partType
   , PTC_STR_139TYPEINFOWTPART 사급구분, PTC_STR_140TYPEINFOWTPART 금형구분
   , PTC_STR_141TYPEINFOWTPART 제작구분, PTC_STR_142TYPEINFOWTPART 생산구분
   , PTC_STR_135TYPEINFOWTPART 구매담당그룹, PTC_STR_134TYPEINFOWTPART plant 
   , PTC_STR_143TYPEINFOWTPART 금형제작처, PTC_STR_136TYPEINFOWTPART 개발담당자
   , PTC_STR_137TYPEINFOWTPART 금형담당자, PTC_STR_138TYPEINFOWTPART 제작담당자
   , PTC_STR_144TYPEINFOWTPART 목표CT, PTC_STR_145TYPEINFOWTPART Cavity
   , PTC_STR_5TYPEINFOWTPART spIsDeleted
   , P.VERSIONIDA2VERSIONINFO revision
   , HM.WTPARTNUMBER
   from wtpartmaster m, wtpart p, KETHalbPartDieSetPartLink hl, wtpartmaster hm
   where 1=1
   and m.ida2a2 = P.IDA3MASTERREFERENCE 
   and M.IDA2A2 = HL.IDA3b5(+)
   and hl.ida3a5 = HM.IDA2A2(+)
   --and HM.WTPARTNUMBER is not null
   and P.LATESTITERATIONINFO = 1 
   and m.wtpartnumber in ('1A147000', '23732000')
   		 */
   	    }

   	} catch (Exception e) {
   	    Kogger.error(getClass(), e);
   	    trx.rollback();
   	    throw e;
   	}
       }
       
       private Map<String, String> getNumberCodeMapByCodeKey(String codeType) throws Exception{
		
	// Type 미리 가져오기
	Map<String, String> map = new HashMap<String, String>();
	try {

	    Map<String, Object> parameter = new HashMap<String, Object>();
	    parameter.put("locale", Locale.KOREAN);
	    parameter.put("codeType", codeType);

	    List<Map<String, Object>> codeList = NumberCodeHelper.manager.getNumberCodeList(parameter);
	    for (Map<String, Object> mapIn : codeList) {
		String value = (String) mapIn.get("value");
		String code = (String) mapIn.get("code");
		map.put(code, value);
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return map;
    }
       
       private void updateProd(List<ErpProdPartDTO> prodList)throws Exception {
	
	Transaction trx = null;
	Map<String, String> spColorMap = getNumberCodeMapByCodeKey(PartSpecEnum.SpColor.getAttrCodeType()); // "SPECCOLOR"
	Map<String, String> spColorElecMap = getNumberCodeMapByCodeKey(PartSpecEnum.SpColorElec.getAttrCodeType()); // "SPCOLORELEC"
	Map<String, String> spPlatingMap = getNumberCodeMapByValKey(PartSpecEnum.SpPlating.getAttrCodeType()); // "SPECPLATING"
	
   	try {
   	    
   	    for (ErpProdPartDTO dto : prodList) {
   		
   		String partNo = dto.getPartNo();
   		Kogger.debug(getClass(), "# P partNo:" + partNo + " " + dto.getPartType());
   		WTPartMaster partMaster = getMaster(partNo);
   		if (partMaster == null) {
   		    Kogger.debug(getClass(), "####### partNo:" + partNo + " " + dto.getPartType() + " not found!");
   		    migLogUtil.log("ERP_PART", partMaster, "PartNotFound", "부품 부재", partNo, partNo);
   		    continue;
   		}
   		
   		trx = new Transaction();
		trx.start();
		
		// 부품 유형 수정
   		updatePartType(partMaster, dto.getPartType());
   		WTPart wtPart = getLatestObject(partMaster, dto.getPartType());

   		// 부품명 수정
   		if (!wtPart.getName().equals(dto.getPartName())) {
   		    WTPartMasterIdentity identity = (WTPartMasterIdentity) partMaster.getIdentificationObject();
   		    identity.setName(dto.getPartName());
   		    partMaster = (WTPartMaster) IdentityHelper.service.changeIdentity(partMaster, identity);
   		}
   		
   		// 단위
   		if (!(partMaster.getDefaultUnit().toString()).equalsIgnoreCase("KET_" + dto.getPartUnit()) && StringUtils.isNotEmpty(dto.getPartUnit())) {
   		    if("%".equals(dto.getPartUnit())){
   			dto.setPartUnit("PERCENT");
   		    }
   		    partMaster.setDefaultUnit(QuantityUnit.toQuantityUnit("KET_" + dto.getPartUnit()));
   		    partMaster = (WTPartMaster) PersistenceHelper.manager.modify(partMaster);
   		}
   		
   		// BOMFLAG
   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpBOMFlag, "OLD");

   		// Part TYPE
   		if ("FERT".equals(dto.getPartType())) {
   		    String nowValue = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
   		    if (!PartTypeEnum.FERT.getPlmCode().equals(nowValue)) {
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartType, PartTypeEnum.FERT.getPlmCode());
   		    }
   		} else if ("HALB".equals(dto.getPartType())) {
   		    String nowValue = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
   		    if (!PartTypeEnum.HALB.getPlmCode().equals(nowValue)) {
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartType, PartTypeEnum.HALB.getPlmCode());
   		    }
   		} else if ("HAWA".equals(dto.getPartType())) {
   		    String nowValue = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
   		    if (!PartTypeEnum.HAWA.getPlmCode().equals(nowValue)) {
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartType, PartTypeEnum.HAWA.getPlmCode());
   		    }
   		} else if ("ROH".equals(dto.getPartType())) {
   		    String nowValue = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
   		    if (!PartTypeEnum.ROH.getPlmCode().equals(nowValue)) {
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartType, PartTypeEnum.ROH.getPlmCode());
   		    }
   		} else if ("PACK".equals(dto.getPartType())) {
   		    String nowValue = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
   		    if (!PartTypeEnum.PACK.getPlmCode().equals(nowValue)) {
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartType, PartTypeEnum.PACK.getPlmCode());
   		    }
   		} else if ("SCRP".equals(dto.getPartType())) {
   		    String nowValue = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
   		    if (!PartTypeEnum.SCRP.getPlmCode().equals(nowValue)) {
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartType, PartTypeEnum.SCRP.getPlmCode());
   		    }
   		}

   		// 중량
   		if(StringUtils.isNotEmpty(dto.getTotalW())){
   		    String totalW = dto.getTotalW();
   		    totalW = totalW.replace("-", "");
   		    
   		    if (totalW.indexOf(".") != -1) {
			String endPoint = totalW.substring(totalW.indexOf(".") + 1);
			if (endPoint.length() == 0) {
			    totalW = totalW + "000";
			} else if (endPoint.length() == 1) {
			    totalW = totalW + "00";
			} else if (endPoint.length() == 2) {
			    totalW = totalW + "0";
			} else if (endPoint.length() == 3) {

			} else {
			    totalW = totalW.substring(0, totalW.indexOf(".") + 4);
			}
		    }else{
			if(totalW.length() == 0){
			    totalW = totalW + "0.000";
			} else {
			    totalW = totalW + ".000";
			}
		    }
   		    
   		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpTotalWeight, totalW);
   		    
   		}else{   		    
   		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpTotalWeight, "0.000");
   		}
   		
   		if(StringUtils.isNotEmpty(dto.getNetW())){
   		    String netW = dto.getNetW();
   		    netW = netW.replace("-", "");
   		 
   		    if (netW.indexOf(".") != -1) {
			String endPoint = netW.substring(netW.indexOf(".") + 1);
			if (endPoint.length() == 0) {
			    netW = netW + "000";
			} else if (endPoint.length() == 1) {
			    netW = netW + "00";
			} else if (endPoint.length() == 2) {
			    netW = netW + "0";
			} else if (endPoint.length() == 3) {

			} else {
			    netW = netW.substring(0, netW.indexOf(".") + 4);
			}
		    }else{
			if(netW.length() == 0){
			    netW = netW + "0.000";
			} else {
			    netW = netW + ".000";
			}
		    }
   		    
   		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpNetWeight, netW);
   		    
   		}else{
   		    
   		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpNetWeight, "0.000");
   		}
   		
   		// 스크랩은 자동으로 처리
   		if(StringUtils.isNotEmpty(dto.getTotalW()) && StringUtils.isNotEmpty(dto.getNetW())){
   		    // 스크랩 중량 계산
   		    String totalW = dto.getTotalW();
   		    totalW = totalW.replace("-", "");
   		    totalW = totalW.replace(",", "");
   		    String netW = dto.getNetW();
		    netW = netW.replace("-", "");
		    netW = netW.replace(",", "");

		    Double totalWL = Double.parseDouble(totalW);
		    Double netWL = Double.parseDouble(netW);
		    DecimalFormat decimalFormat = new DecimalFormat( "0.000" );
		    String scrabW = decimalFormat.format( totalWL - netWL );
//		    if(scrabWL < 0){
//			scrabW = String.valueOf(scrabWL);
//			scrabW.replace("-", "");
//		    }else{
//			scrabW = String.valueOf(scrabWL);
//		    }
		    if (scrabW.indexOf(".") != -1) {
			String endPoint = scrabW.substring(scrabW.indexOf(".") + 1);
			if (endPoint.length() == 0) {
			    scrabW = scrabW + "000";
			} else if (endPoint.length() == 1) {
			    scrabW = scrabW + "00";
			} else if (endPoint.length() == 2) {
			    scrabW = scrabW + "0";
			} else if (endPoint.length() == 3) {

			} else {
			    scrabW = scrabW.substring(0, scrabW.indexOf(".") + 4);
			}
		    }else{
			if(scrabW.length() == 0){
			    scrabW = scrabW + "0.000";
			} else {
			    scrabW = scrabW + ".000";
			}
		    }
   		    
   		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpScrabWeight, scrabW);
   		}else{
   		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpScrabWeight, "0.000");
   		}
   		//PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpScrabWeight, dto.getScrabW());
   		
   		// 제품 SIZE
   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpProdSize, dto.getProdSize());
   		// 스크랩코드
   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpScrabCode, dto.getScrabCode());

   		// 삭제 spIsDeleted
   		if (StringUtils.isNotEmpty(dto.getIsDeleted())) {
   		    if ("X".equals(dto.getIsDeleted())) {
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, "Y");
   		    } else {
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, "N");
   		    }
   		} else {
   		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, "N");
   		}

   		// 시리즈
   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpSeries, dto.getSeries());
   		// 방수 : 100 방수 200 비방수 => SEALED010 방수, SEALED020 비방수 [, SEALED030 해당없음]
   		if(StringUtils.isNotEmpty(dto.getWaterProof())){
   		    if("100".equals(dto.getWaterProof())){
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpWaterProof, "SEALED010" );
   		    }else if("200".equals(dto.getWaterProof())){
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpWaterProof, "SEALED020");
   		    }else{
   			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpWaterProof, "SEALED030");
   		    }
   		}else{
   		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpWaterProof, "SEALED030");
   		}
   		
   		// 재질 : code값이 excel에 들어 있음
//   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMaterialInfo, dto.getMaterFe());
//   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMaterNotFe, dto.getMaterNotFe());
   		// 색상 
   		if(partMaster.getNumber().startsWith("K1") || partMaster.getNumber().startsWith("KR")
   			//||partMaster.getNumber().startsWith("K2")||partMaster.getNumber().startsWith("K3")
   			//|| partMaster.getNumber().startsWith("K5") || partMaster.getNumber().startsWith("K7")|| partMaster.getNumber().startsWith("RK")
   			//|| partMaster.getNumber().startsWith("HK5")|| partMaster.getNumber().startsWith("HRK") 
   			//|| partMaster.getNumber().startsWith("K")
   			){
   		    
   		    if(StringUtil.isEmpty(dto.getColor())){
   			
   		    }else{
   			if(spColorElecMap.containsKey(dto.getColor())){
   			    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpColorElec, dto.getColor());
   			}else{
   			    migLogUtil.log("ERP_PART", wtPart, "Color_Elec", "컬러 Elec 부재", dto.getColor(), partNo);
   			}
   		    }
   		}else{
   		    if(StringUtil.isEmpty(dto.getColor())){
			
		    }else{
			if(spColorMap.containsKey(dto.getColor())){
			    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpColor, dto.getColor());
			}else{
			    migLogUtil.log("ERP_PART", wtPart, "Color", "컬러 부재", dto.getColor(), partNo);
			}
		    }
   		}
   		
   		// 도금 : name으로 code값을 가져와야 함.
   		if(StringUtils.isNotEmpty(dto.getPlating())){
   		    
//   		    if("PER-TIN".equals(dto.getPlating())){
//   			dto.setPlating("Pre-Tin");	
//   		    }else if("PRE-TIN".equals(dto.getPlating())){
//   			dto.setPlating("Pre-Tin");	
//   		    }else if("PRE TIN".equals(dto.getPlating())){
//   			dto.setPlating("Pre-Tin");	
//   		    }else if("TIN".equals(dto.getPlating())){
//   			dto.setPlating("Tin");	
//   		    }
   		    
   		    if(dto.getPlating().indexOf("#N/A") != -1){
   			// 도금 값이 없는 경우
   		    }else{
   		    
			// String platingCode = CodeHelper.manager.getCodeCodeByValue(PartSpecEnum.SpPlating.getAttrCodeType(), dto.getPlating());
			if (spPlatingMap.containsKey(dto.getPlating().toUpperCase())) {
			    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPlating, spPlatingMap.get(dto.getPlating().toUpperCase()));
			} else {
			    Kogger.debug(getClass(), "#### 못찾은 도금 :" + dto.getPlating());
			    migLogUtil.log("ERP_PART", wtPart, "PlatingNotFound", "도금 부재", dto.getPlating(), partNo);
			    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPlating, "");
			}
   		    }

   		}else{
   		    
   		}
   		
   		// 대표금형
   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpRepresentM, dto.getRepresentM());
   		// IsYazaki
   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsYazaki, dto.getIsYazaki());
   		// yazakino
   		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpYazakiNo, dto.getYazakiNo());

   		// save - update
   		PersistenceServerHelper.manager.update(wtPart);

   		wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);

   		trx.commit();
   		trx = null;
   		dto = null;
   		wtPart = null;
   		/**
   -- Prod No
   select m.wtpartnumber, m.name, M.DEFAULTUNIT, p.PTC_STR_3TYPEINFOWTPART partType
   , PTC_STR_9TYPEINFOWTPART totalW, PTC_STR_8TYPEINFOWTPART netW
   , PTC_STR_10TYPEINFOWTPART scrabW, PTC_STR_23TYPEINFOWTPART prodSize
   , PTC_STR_15TYPEINFOWTPART scrabCode, PTC_STR_5TYPEINFOWTPART deletedFlag 
   , PTC_STR_21TYPEINFOWTPART series, PTC_STR_16TYPEINFOWTPART waterProof
   , PTC_STR_49TYPEINFOWTPART mater, PTC_STR_50TYPEINFOWTPART materNotFe
   , PTC_STR_18TYPEINFOWTPART color, PTC_STR_53TYPEINFOWTPART plating
   , PTC_STR_48TYPEINFOWTPART repMold, PTC_STR_11TYPEINFOWTPART isYazaki
   , PTC_STR_12TYPEINFOWTPART yazakiNo
   , P.VERSIONIDA2VERSIONINFO revision
   from wtpartmaster m, wtpart p
   where 1=1
   and m.ida2a2 = P.IDA3MASTERREFERENCE 
   and wtpartnumber in ('07884000-006', '10617001-048')
   		 */
   	    }

   	} catch (Exception e) {
   	    Kogger.error(getClass(), e);
   	    trx.rollback();
   	    throw e;
   	}
    }
       
    private boolean hasDieHalbRelation(WTPartMaster dieSet, String halbCode) throws Exception {

	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	QueryResult qr = query.getQueryResultByRoleB(WTPartMaster.class, KETHalbPartDieSetPartLink.class, WTPartMaster.class, dieSet);
	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    WTPartMaster halbPartMast = (WTPartMaster) obj[0];
	    Kogger.debug(getClass(), "related halb part: " + halbPartMast.getNumber());
	    if (halbCode.equals(halbPartMast.getNumber())) {
		return true;
	    }
	}

	return false;
    }

    private WTPartMaster getMaster(String partNo) throws WTException {

	WTPartMaster master = null;
	QuerySpec query = new QuerySpec(WTPartMaster.class);
	query.appendWhere(new SearchCondition(WTPartMaster.class, WTPartMaster.NUMBER, SearchCondition.EQUAL, partNo), new int[] { 0 });
	QueryResult qr = PersistenceHelper.manager.find(query);
	if (qr.hasMoreElements()) {
	    master = (WTPartMaster) qr.nextElement();
	}

	return master;
    }

    private WTPart getLatestObject(WTPartMaster master) throws WTException {

	WTPart rc = null;
	QueryResult qr = wt.vc.VersionControlHelper.service.allVersionsOf(master);

	while (qr.hasMoreElements()) {

	    WTPart obj = ((WTPart) qr.nextElement());

	    if (rc == null || obj.getVersionIdentifier().getSeries().greaterThan(rc.getVersionIdentifier().getSeries())) {
		rc = obj;
	    }
	}

	if (rc != null)
	    return (WTPart) VersionControlHelper.getLatestIteration(rc, false);
	else
	    return rc;
    }

    private WTPart getLatestObject(WTPartMaster master, String partType) throws Exception {

	WTPart rc = null;
	QueryResult qr = wt.vc.VersionControlHelper.service.allVersionsOf(master);

	while (qr.hasMoreElements()) {

	    WTPart obj = ((WTPart) qr.nextElement());

	    if ("FERT".equals(partType)) {
		String nowValue = PartSpecGetter.getPartSpec(obj, PartSpecEnum.SpPartType);
		if (!PartTypeEnum.FERT.getPlmCode().equals(nowValue)) {
		    PartSpecSetter.setPartSpec(obj, PartSpecEnum.SpPartType, PartTypeEnum.FERT.getPlmCode());
		}
	    } else if ("HALB".equals(partType)) {
		String nowValue = PartSpecGetter.getPartSpec(obj, PartSpecEnum.SpPartType);
		if (!PartTypeEnum.HALB.getPlmCode().equals(nowValue)) {
		    PartSpecSetter.setPartSpec(obj, PartSpecEnum.SpPartType, PartTypeEnum.HALB.getPlmCode());
		}
	    } else if ("HAWA".equals(partType)) {
		String nowValue = PartSpecGetter.getPartSpec(obj, PartSpecEnum.SpPartType);
		if (!PartTypeEnum.HAWA.getPlmCode().equals(nowValue)) {
		    PartSpecSetter.setPartSpec(obj, PartSpecEnum.SpPartType, PartTypeEnum.HAWA.getPlmCode());
		}
	    } else if ("ROH".equals(partType)) {
		String nowValue = PartSpecGetter.getPartSpec(obj, PartSpecEnum.SpPartType);
		if (!PartTypeEnum.ROH.getPlmCode().equals(nowValue)) {
		    PartSpecSetter.setPartSpec(obj, PartSpecEnum.SpPartType, PartTypeEnum.ROH.getPlmCode());
		}
	    } else if ("PACK".equals(partType)) {
		String nowValue = PartSpecGetter.getPartSpec(obj, PartSpecEnum.SpPartType);
		if (!PartTypeEnum.PACK.getPlmCode().equals(nowValue)) {
		    PartSpecSetter.setPartSpec(obj, PartSpecEnum.SpPartType, PartTypeEnum.PACK.getPlmCode());
		}
	    } else if ("SCRP".equals(partType)) {
		String nowValue = PartSpecGetter.getPartSpec(obj, PartSpecEnum.SpPartType);
		if (!PartTypeEnum.SCRP.getPlmCode().equals(nowValue)) {
		    PartSpecSetter.setPartSpec(obj, PartSpecEnum.SpPartType, PartTypeEnum.SCRP.getPlmCode());
		}
	    }

	    // save - update
	    PersistenceServerHelper.manager.update(obj);
	    obj = (WTPart) PersistenceHelper.manager.refresh(obj);

	    if (rc == null || obj.getVersionIdentifier().getSeries().greaterThan(rc.getVersionIdentifier().getSeries())) {
		rc = obj;
	    }
	}

	if (rc != null)
	    return (WTPart) VersionControlHelper.getLatestIteration(rc, false);
	else
	    return rc;
    }

    private void updatePartType(WTPartMaster master, String partType) throws Exception {

	QueryResult qr = wt.vc.VersionControlHelper.service.allIterationsOf(master);

	while (qr.hasMoreElements()) {

	    WTPart obj = ((WTPart) qr.nextElement());

	    if ("FERT".equals(partType)) {
		String nowValue = PartSpecGetter.getPartSpec(obj, PartSpecEnum.SpPartType);
		if (!PartTypeEnum.FERT.getPlmCode().equals(nowValue)) {
		    PartSpecSetter.setPartSpec(obj, PartSpecEnum.SpPartType, PartTypeEnum.FERT.getPlmCode());
		}
	    } else if ("HALB".equals(partType)) {
		String nowValue = PartSpecGetter.getPartSpec(obj, PartSpecEnum.SpPartType);
		if (!PartTypeEnum.HALB.getPlmCode().equals(nowValue)) {
		    PartSpecSetter.setPartSpec(obj, PartSpecEnum.SpPartType, PartTypeEnum.HALB.getPlmCode());
		}
	    } else if ("HAWA".equals(partType)) {
		String nowValue = PartSpecGetter.getPartSpec(obj, PartSpecEnum.SpPartType);
		if (!PartTypeEnum.HAWA.getPlmCode().equals(nowValue)) {
		    PartSpecSetter.setPartSpec(obj, PartSpecEnum.SpPartType, PartTypeEnum.HAWA.getPlmCode());
		}
	    } else if ("ROH".equals(partType)) {
		String nowValue = PartSpecGetter.getPartSpec(obj, PartSpecEnum.SpPartType);
		if (!PartTypeEnum.ROH.getPlmCode().equals(nowValue)) {
		    PartSpecSetter.setPartSpec(obj, PartSpecEnum.SpPartType, PartTypeEnum.ROH.getPlmCode());
		}
	    } else if ("PACK".equals(partType)) {
		String nowValue = PartSpecGetter.getPartSpec(obj, PartSpecEnum.SpPartType);
		if (!PartTypeEnum.PACK.getPlmCode().equals(nowValue)) {
		    PartSpecSetter.setPartSpec(obj, PartSpecEnum.SpPartType, PartTypeEnum.PACK.getPlmCode());
		}
	    } else if ("SCRP".equals(partType)) {
		String nowValue = PartSpecGetter.getPartSpec(obj, PartSpecEnum.SpPartType);
		if (!PartTypeEnum.SCRP.getPlmCode().equals(nowValue)) {
		    PartSpecSetter.setPartSpec(obj, PartSpecEnum.SpPartType, PartTypeEnum.SCRP.getPlmCode());
		}
	    }else if ("CAST".equals(partType)) {
		String nowValue = PartSpecGetter.getPartSpec(obj, PartSpecEnum.SpPartType);
		if (!PartTypeEnum.DIENO.getPlmCode().equals(nowValue)) {
		    PartSpecSetter.setPartSpec(obj, PartSpecEnum.SpPartType, PartTypeEnum.DIENO.getPlmCode());
		}
	    }else if ("DIEM".equals(partType)) {
		String nowValue = PartSpecGetter.getPartSpec(obj, PartSpecEnum.SpPartType);
		if (!PartTypeEnum.MOLD.getPlmCode().equals(nowValue)) {
		    PartSpecSetter.setPartSpec(obj, PartSpecEnum.SpPartType, PartTypeEnum.MOLD.getPlmCode());
		}
	    }

	    // save - update
	    PersistenceServerHelper.manager.update(obj);
	    obj = (WTPart) PersistenceHelper.manager.refresh(obj);

	}
    }

    /**
     * 부품의 분류체계, 개발단계등 마이그레이션
     * 
     */
    public void migPartProp(String filePath, String lastSheet) throws Exception {

	try {

	    String[] fileTypeArray = new String[] { "PART_TOBE_PROPS" };
	    PartPropLoader loader = new PartPropLoader();
	    int lastSheetInt = Integer.parseInt(lastSheet);

	    for (String fileType : fileTypeArray) {

		for(int k=0; k<=lastSheetInt; k++){
		    Kogger.debug(getClass(), "****************  Excel Extract Start " + fileType + k + " **************************");
		    
		    loader.load(filePath, fileType, k);
		    List<PartPropDTO> propList = loader.getPropList();
		    updatePartProp(propList);
		    propList.clear();
		    
		    Kogger.debug(getClass(), "****************  Excel Extract End " + fileType + k + " **************************");
		}
		
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	}

    }
    
    // 개발 서버용 단건
    public void migPartProp(String filePath, String lastSheet, String fileName) throws Exception{
	
	try {

	    PartPropLoader loader = new PartPropLoader();
	    int lastSheetInt = Integer.parseInt(lastSheet);

	    for (int k = 0; k <= lastSheetInt; k++) {
		Kogger.debug(getClass(), "****************  Excel Extract Start " + fileName + k + " **************************");

		loader.load(filePath, fileName, k);
		List<PartPropDTO> propList = loader.getPropList();
		updatePartProp(propList);
		propList.clear();

		Kogger.debug(getClass(), "****************  Excel Extract End " + fileName + k + " **************************");
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	}

    }
    

    private void updatePartProp(List<PartPropDTO> propList) throws Exception {

	Transaction trx = null;
	
	try {

	    for (PartPropDTO dto : propList) {

		String partNo = dto.getPartNo();
		Kogger.debug(getClass(), "# M partNo:" + partNo + " " + dto.getPartRev());
		
		                    
		WTPart wtPart = null;
		try{
		    
		    wtPart = (WTPart)CommonUtil.getObject(dto.getIterOid());
		}catch(Exception e){
		    Kogger.debug(getClass(), "마이그레이션 안된 부품:" + partNo);
		    Kogger.error(getClass(), e);
		}
		
		if(wtPart == null){
		    migLogUtil.log("PART_Prop", wtPart, "Part_No", "부품 마이그레이션 부재", partNo, partNo);
		    continue;
		}
		
		WTPartMaster partMaster = (WTPartMaster)wtPart.getMaster(); //getMaster(partNo);
		if (partMaster == null) {
		    migLogUtil.log("PART_Prop", partMaster, "Part_No", "부품 마이그레이션 부재", partNo, partNo);
		    Kogger.debug(getClass(), "####### partNo:" + partNo + " " + dto.getPartRev() + " not found!");
		    continue;
		}

		// Part TYPE :   Die No, 제품, 반제품, 원자재
		if(StringUtils.isNotEmpty(dto.getPartType())){
		    String partType = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
		    if("O".equals(partType)){
			if ("Die No".equals(dto.getPartType()) || "금형세트".equals(dto.getPartType())) {
			    updatePartType(partMaster, "CAST");
			    wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
			} else if ("제품".equals(dto.getPartType())) {
			    updatePartType(partMaster, "FERT");
			    wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
			} else if ("반제품".equals(dto.getPartType())) {
			    updatePartType(partMaster, "HALB");
			    wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
			} else if ("상품".equals(dto.getPartType())) {
			    updatePartType(partMaster, "HAWA");
			    wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
			} else if ("원자재".equals(dto.getPartType())) {
			    updatePartType(partMaster, "ROH");
			    wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
			} else if ("스크랩".equals(dto.getPartType())) {
			    updatePartType(partMaster, "SCRP");
			    wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
			} else if ("포장재".equals(dto.getPartType())) {
			    updatePartType(partMaster, "PACK");
			    wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
			} else if ("금형부품".equals(dto.getPartType())) {
			    updatePartType(partMaster, "DIEM");
			    wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
			} else if ("기타".equals(dto.getPartType())) {
			} else {
			    migLogUtil.log("PART_Prop", partMaster, "Part_Type", "부품 유형 부재", dto.getPartType(), partNo);
			}
		    }
		}
		
		//WTPart wtPart = getRevObject(partMaster, dto.getPartRev());
		
		// 부품분류 - 분류 코드
		KETPartClassification  partClassification = null;
		if (StringUtils.isNotEmpty(dto.getPartClaz())) {
		    
		    if(clazMap.containsKey(dto.getPartClaz())){
			partClassification = clazMap.get(dto.getPartClaz());
		    }else{
			partClassification = PartClazHelper.service.getPartClassificationByClazCode(dto.getPartClaz());
			if (partClassification != null) {
			    String oidStr = CommonUtil.getOIDLongValue2Str(partClassification);
			    if (isLeafClaz(oidStr)) {
				clazMap.put(dto.getPartClaz(), partClassification);
			    } else {
				Kogger.debug(getClass(), "# M partNo:" + partNo + " " + dto.getPartRev() + " @#$%:" + dto.getPartClaz() );
				migLogUtil.log("PART_Prop", wtPart, "Claz_Leaf", "부품분류 Leaf 아님", dto.getPartClaz(), partNo );
				continue;
			    }
			}
		    }
   		}
		
		trx = new Transaction();
		trx.start();
		
		if(partClassification != null){
		    AssociateClaz ac = new PartClassificationRelation();
		    ac.associateCreate(wtPart, partClassification);
		}else{
		    migLogUtil.log("PART_Prop", wtPart, "Claz_None", "부품분류 None", dto.getPartClaz(), partNo);
		}
		
		// 개발단계
		if(StringUtils.isNotEmpty(dto.getPartDevLevel())){
		    String devLevel = null;
//		    if("T-CAR".equals(dto.getPartDevLevel())){
//			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartDevLevel, "PC003");
//		    }else if("Proto".equals(dto.getPartDevLevel())){
//			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartDevLevel, "PC003");
//		    }else if("Pilot".equals(dto.getPartDevLevel())){
//			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartDevLevel, "PC003");
//		    }else 
	            if("양산단계".equals(dto.getPartDevLevel())){
			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartDevLevel, "PC004");
		    }
		    else if("개발단계".equals(dto.getPartDevLevel())){
			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartDevLevel, "PC003");
		    }else{
			migLogUtil.log("PART_Prop", wtPart, "DevStage", "부품개발단계 부재", dto.getPartDevLevel(), partNo);
		    }
		}
		
		// 리비전
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartRevision, dto.getPartKetRev());

		// save - update
		PersistenceServerHelper.manager.update(wtPart);

		wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);

		trx.commit();
   		trx = null;
		dto = null;
		wtPart = null;
		/**
select m.wtpartnumber, m.name, M.DEFAULTUNIT, p.PTC_STR_3TYPEINFOWTPART partType , PTC_STR_4TYPEINFOWTPART devLevel
, PTC_STR_5TYPEINFOWTPART deletedFlag , PTC_STR_2TYPEINFOWTPART ketRev, P.VERSIONIDA2VERSIONINFO revision
from wtpartmaster m, wtpart p 
where 1=1 
and m.ida2a2 = P.IDA3MASTERREFERENCE 
and P.LATESTITERATIONINFO = 1 
and wtpartnumber in ('07884000-006', '10617001-048')
		 */
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	    throw e;
	}
    }
    
    private WTPart getRevObject(WTPartMaster master, String rev) throws WTException {

	WTPart rc = null;
	QueryResult qr = wt.vc.VersionControlHelper.service.allVersionsOf(master);

	while (qr.hasMoreElements()) {

	    WTPart obj = ((WTPart) qr.nextElement());

	    if (rev.equals(obj.getVersionIdentifier().getValue())){
		rc = obj;
	    }
	}

	if (rc != null)
	    return (WTPart) VersionControlHelper.getLatestIteration(rc, false);
	else
	    return rc;
    }

    public boolean isLeafClaz(String oid) throws Exception {

	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();

	try {

	    List aBind = new ArrayList();

	    StringBuffer buffer = new StringBuffer();
	    buffer.append("	SELECT C.IDA2A2  \n");
	    buffer.append("	FROM KETPartClassification C  \n");
	    buffer.append("	WHERE 1 = 1      \n");
	    buffer.append("	START WITH IDA3A3 =  ? \n");
	    buffer.append("	CONNECT BY PRIOR IDA2A2 = IDA3A3  \n");

	    aBind.add(oid);

	    String sSql = buffer.toString();
	    int count = oDaoManager.queryForCount(sSql, aBind);

	    return count == 0;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }
    
    // 리비전 마이그레이션
    public void migPartRevision(String prefix, String arg2) throws Exception{

	try {
	    
	    // String[] seperEpmNos = new String[]{ "EM420151"
	    // "0"
	    // ,"10","11","12","13","14","15","16","17","18","19"
	    // ,"20","21","22","23","24","25","26","27","28","29"
	    // ,"3","4","5","6","7","8","9"
	    // ,"A","B","C","D","E","F","G","H","I","J","K","L","M","N"
	    // ,"O","P","Q","R","S","T","U","V","W","X","Y","Z"
	    // };

	    // 마스터 리스트
	    List<PartRevisionDTO> prefixPartList = getProdPartAllInfo(prefix);

	    for (PartRevisionDTO oneMast : prefixPartList) {

		// 전체 Iteration 리스트
		List<PartRevisionDTO> detailMastList = getPartDetailInfo(oneMast.getMastId());

		// 부품 하나당 양산 리비전 처리
		if (detailMastList != null) {
		    updatePartRevision(detailMastList);
		}
	    }
	
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }

    // 부품 정보 업데이트 - 부품 하나당 양산 리비전 처리
    public void updatePartRevision(List<PartRevisionDTO> detailMastList) throws Exception {
	
	List<List<PartRevisionDTO>> allRevList = new ArrayList<List<PartRevisionDTO>>();
	
	List<PartRevisionDTO> oneRevList = null;
	
	long oldRevId = 0;
	
	// 1. 부품을 리비전별로 나눈다.
	for( int k = 0; k < detailMastList.size(); k++ ){
	    
	    PartRevisionDTO dto = detailMastList.get(k);
	    
	    long revId = dto.getRevId();
	    
	    if(k==0){
		oldRevId = revId;
		oneRevList = new ArrayList<PartRevisionDTO>();
	    }
	    
	    if(revId == oldRevId){
		oneRevList.add(dto);
	    }else{
		allRevList.add(oneRevList); // new 
		oneRevList = new ArrayList<PartRevisionDTO>();
		oneRevList.add(dto);
	    }
	    
	    if(k == detailMastList.size() -1 ){
		allRevList.add(oneRevList); // new end
	    }
	    
	    // oldRev Change
	    oldRevId = revId;
	}
	
	// 각 Revision의 최신Iteration에서 개발단계가 있는지 체크한다.
	boolean hasDevStage = false;
	List<String> devStageList = new ArrayList<String>();
	WTPart logWtPart = null;
	String tempDevState = null;
	for( List<PartRevisionDTO> revOneList : allRevList){
	    for(int k = 0; k < 1; k++ ){
		PartRevisionDTO dto = revOneList.get(k);
		String iterationOid = dto.getIterOid();
		WTPart wtPart = (WTPart)CommonUtil.getObject(iterationOid);
		logWtPart = wtPart;
		String spDevStage = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartDevLevel);
		tempDevState = spDevStage;
		if (StringUtils.isNotEmpty(spDevStage)) {
		    if(spDevStage.equals("PC003") || spDevStage.equals("PC004")){
			hasDevStage = true;
		    }
		    devStageList.add(spDevStage);
		}else{
		    devStageList.add("");
		}
	    }
	}
	
	if(!hasDevStage){
	    migLogUtil.log("WTPART_Revision", logWtPart, "DevStage", "개발단계 부재", tempDevState, (logWtPart==null?null:logWtPart.getNumber()));
	    return;
	}
	
	// 금형은 먼저 쿼리로 돌린다. - 성능 개선
	// 금형부품인지 아닌지 체크한다.
//	String partType = PartSpecGetter.getPartSpec(logWtPart, PartSpecEnum.SpPartType);
//	String isMoldSide = "";
//	if("D".equals(partType) || "M".equals(partType)){
//	    isMoldSide = "Y";
//	}else{
//	    isMoldSide = "N";
//	}
//	
//	if("Y".equals(isMoldSide)){
//	    // 금형은 EPM과 동일한 Rev을 넣어준다.
//	    for( List<PartRevisionDTO> revOneList : allRevList){
//		for (PartRevisionDTO dto : revOneList) {
//		    String iterationOid = dto.getIterOid();
//		    WTPart wtPart = (WTPart) CommonUtil.getObject(iterationOid);
//		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartRevision, wtPart.getVersionIdentifier().getValue());
//		    PersistenceServerHelper.manager.update(wtPart);
//		}
//	    }
//	    
//	    return;
//	}
	
	// 부품을 리비전 별로 처리한다.
	int developLevel = -1;
	int manufLevel = -1;
	String revision = "";
	boolean startedManuf = false;
	for( int i=0; i < allRevList.size(); i++){
	    
	    List<PartRevisionDTO> revOneList = allRevList.get(i);
	    
	    // 개발단계인지 양산단계인지 체크
	    int checkRet = 0;
	    for(int k = 0; k < revOneList.size(); k++ ){
		
		PartRevisionDTO dto = revOneList.get(k);
		
		if(k == 0){
		    // 현 리비전의 최신 이터레이션의 '개발단계' 확인
		    if(startedManuf){
			manufLevel = manufLevel + 1;
			revision = String.valueOf(manufLevel);
		    }else{
			
			String devStageName = devStageList.get(i);
			checkRet = checkDevelopLevel(devStageName);
			if (checkRet > 0) { // 양산단계
			    startedManuf = true;
			    manufLevel = manufLevel + 1;
			    revision = String.valueOf(manufLevel);
			} else { // 개발단계
			    developLevel = developLevel + 1;
			    revision = String.valueOf(developLevel);
			    if (revision.length() == 1) {
				revision = "D0" + String.valueOf(developLevel);
			    } else if (revision.length() == 2) {
				revision = "D" + String.valueOf(developLevel);
			    } else {
				throw new Exception("리비전 3자리 이상입니다.");
			    }
			}
		    }
		}
		
		// 해당하는 것 처리
		String iterationOid = dto.getIterOid();
		WTPart wtPart = (WTPart)CommonUtil.getObject(iterationOid);
		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartRevision, revision);
		PersistenceServerHelper.manager.update(wtPart);
	    }
	}
    }
    
    private int checkDevelopLevel(String devStageName) throws Exception {
	
	 if ("PC004".equals(devStageName)) {
	     
	     return 1;
	     
	 }else if( "PC003".equals(devStageName) ){
	     
	     return 0;
	     
	 }else{
	     
	     return -1;
	     
	 }
   }
    
    // 마스터의 상세 부품 리스트
    private List<PartRevisionDTO> getPartDetailInfo(long mid) throws Exception {

	List<PartRevisionDTO> list = null;
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();

	try {

	    List aBind = new ArrayList();

	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" SELECT WTPARTNUMBER DOC_NO, M.NAME \n");
	    buffer.append("      , E.VERSIONIDA2VERSIONINFO VER_NO \n");
	    buffer.append("      , E.VERSIONSORTIDA2VERSIONINFO VER_SORT_NO \n");
	    buffer.append("      , E.ITERATIONIDA2ITERATIONINFO ITER_NO \n");
	    buffer.append("      , M.IDA2A2 MID \n");
	    buffer.append("      , E.STATECHECKOUTINFO STATECHECKOUTINFO \n");
	    buffer.append("      , M.CLASSNAMEA2A2||':'||M.IDA2A2 MOID \n");
	    buffer.append("      , E.BRANCHIDITERATIONINFO VID \n");
	    buffer.append("      , 'VR:'||E.CLASSNAMEA2A2||':'||E.BRANCHIDITERATIONINFO VOID \n");
	    buffer.append("      , E.IDA2A2 EID \n");
	    buffer.append("      , E.CLASSNAMEA2A2||':'||E.IDA2A2 EOID \n");
	    buffer.append(" FROM WTPARTMASTER M, WTPART E \n");
	    buffer.append(" WHERE 1 = 1 \n");
	    buffer.append(" AND M.IDA2A2 = ? \n");
	    buffer.append(" AND M.IDA2A2 = E.IDA3MASTERREFERENCE \n");
	    buffer.append(" ORDER BY M.IDA2A2, E.VERSIONSORTIDA2VERSIONINFO , TO_NUMBER(E.ITERATIONIDA2ITERATIONINFO) DESC \n");

	    aBind.add(mid);
	    
	    String sSql = buffer.toString();
	    list = oDaoManager.queryForList(sSql, aBind, new StampRowSetStrategy<PartRevisionDTO>() {
		
		@Override
                public PartRevisionDTO mapRow(ResultSet rs) throws SQLException {
		    
		    PartRevisionDTO dto = new PartRevisionDTO();
	            
	            dto.setEpmNo(rs.getString("DOC_NO"));
	            dto.setEpmName(rs.getString("NAME"));
	            
	            dto.setVerNo(rs.getString("VER_NO"));
	            dto.setVerSortNo(rs.getString("VER_SORT_NO"));
	            
	            dto.setIterNo(rs.getString("ITER_NO"));
	            
	            dto.setStateCheckState(rs.getString("STATECHECKOUTINFO"));
	            
	            dto.setMastOid(rs.getString("MOID"));
	            dto.setIterOid(rs.getString("EOID"));
	            dto.setRevOid(rs.getString("VOID"));
	            
	            dto.setMastId(rs.getLong("MID"));
	            dto.setRevId(rs.getLong("VID"));
	            dto.setIterId(rs.getLong("EID"));
	            
	            return dto;
                }
		
	    });

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	
	return list;
    }
    
    // prefix로 시작되는 모든 부품 리스트
    private List<PartRevisionDTO> getProdPartAllInfo(String prefix) throws Exception {
	
	List<PartRevisionDTO> list = null;
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	
	try {
	    
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" SELECT M.IDA2A2 MID \n");
	    buffer.append(" FROM WTPARTMASTER M \n");
	    buffer.append(" WHERE 1 = 1 \n");
	    buffer.append(" AND M.WTPARTNUMBER LIKE '" + prefix + "%' \n");
	    buffer.append(" AND EXISTS ( SELECT IDA2A2 FROM WTPART WHERE IDA3MASTERREFERENCE = M.IDA2A2 AND PTC_STR_3TYPEINFOWTPART NOT IN ('D', 'M')) \n");
	    buffer.append(" ORDER BY M.WTPARTNUMBER \n");
	    
	    String sSql = buffer.toString();
	    list = oDaoManager.queryForList(sSql, new StampRowSetStrategy<PartRevisionDTO>() {
		
		@Override
		public PartRevisionDTO mapRow(ResultSet rs) throws SQLException {
		    
		    PartRevisionDTO dto = new PartRevisionDTO();
		    
		    dto.setMastId(rs.getLong("MID"));
		    
		    return dto;
		}
		
	    });
	    
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	
	return list;
    }
    
    // 원재료 / 특성
    public void migPartMater(String filePath) throws Exception{
	
	try {
	    
	    PartMaterLoader loader = new PartMaterLoader();
	    loader.load(filePath);
	    List<PartMaterDTO> list = loader.getMaterList();
	    
	    // maker data 미리 만들어 놓기
	    Map<String, String> sujiMakerMap = getMakerMap(true); // Suji
	    Map<String, String> notFeMakerMap = getMakerMap(false); // 비철
	    // 특성 처리
	    Map<String, String> spColorMap = getNumberCodeMapByValKey(PartSpecEnum.SpColor.getAttrCodeType()); // "SPECCOLOR"
	    Map<String, String> spPlatingMap = getNumberCodeMapByValKey(PartSpecEnum.SpPlating.getAttrCodeType()); // "SPPLATINGPURCH"
	    
	    for (PartMaterDTO oneMast : list) {

		// 전체 Iteration 리스트
		String partNo = oneMast.getPartNo();
		if(StringUtils.isEmpty(partNo)){
		    continue;
		}
		
		WTPartMaster wtpartMast = PartBaseHelper.service.getMaster(partNo.trim());
		
		if(wtpartMast == null){
		    migLogUtil.log("WTPART_Material", wtpartMast, "Material_Part", "부품 부재", partNo, partNo);
		    continue;
		}
		
		// Maker 가져오기
		String maker = oneMast.getPartMaterMaker();
		
		if(StringUtils.isEmpty(maker)){
		    migLogUtil.log("WTPART_Material", wtpartMast, "Material_Maker", "원재료 Maker 부재", maker, partNo);
		    continue;
		}
		maker = maker.trim().toUpperCase();
		
		// 수지 비철 판단.
		KETPartClassification claz = PartClazHelper.service.getPartClassification(wtpartMast);
		boolean[] sujinOrNot = isSuji(wtpartMast, oneMast, claz);
		boolean isTrouble = sujinOrNot[0];
		boolean isSuji = sujinOrNot[1];
		// 판단 불가
		if(isTrouble){
		    continue;
		}
		
		// 수지, 비철에 해당 Maker가 존재하는가?
		String makerCode = null;
		if(isSuji){
		    if(!sujiMakerMap.containsKey(maker)){
			 migLogUtil.log("WTPART_Material", wtpartMast, "Material_Maker_Suji", "수지 Maker 부재", oneMast.getPartMaterMaker(), partNo);
			 continue;
		    }else{
			makerCode = sujiMakerMap.get(maker);
		    }
		}else{
		    if(!notFeMakerMap.containsKey(maker)){
			 migLogUtil.log("WTPART_Material", wtpartMast, "Material_Maker_NotFe", "비철 Maker 부재", oneMast.getPartMaterMaker(), partNo);
			 continue;
		    }else{
			makerCode = notFeMakerMap.get(maker);
		    }
		}
		
		// Type 입력
		String type = oneMast.getPartMaterType();
		if(StringUtils.isEmpty(type)){
		    migLogUtil.log("WTPART_Material", wtpartMast, "Material_Type", "원재료 Type 부재", "["+maker+"]" + "[" + oneMast.getPartMaterType() + "]", partNo);
		    continue;
		}
		type = type.trim().toUpperCase();
		
		// Type이 존재하는가?
		Map<String, Object> typeMap = PartBaseHelper.service.getMaterialTypeList(isSuji, makerCode);
		List<CommonCodeDTO> typeCodeList = (List<CommonCodeDTO>)typeMap.get("options");
		if(typeCodeList == null || typeCodeList.size() == 0){
		    migLogUtil.log("WTPART_Material", wtpartMast, "Material_Type_Maker", "Maker내 원재료 Type 부재", "["+maker+"]" + "[" + oneMast.getPartMaterType()+ "]", partNo);
		    continue;
		}
		
		// Type 중복 체크
		String typeCode = null;
		int typeCount = 0;
		for(CommonCodeDTO dto : typeCodeList){
		    String text =  dto.getText();
		    String value =  dto.getValue();
		    if(type.equals(text)){
			typeCode = value;
			typeCount = typeCount + 1;
		    }
		}
		
		if(typeCount > 1){
		    migLogUtil.log("WTPART_Material", wtpartMast, "Material_Type_Multi", "Maker내 원재료 Type 중복", "["+maker+"]" + "[" + oneMast.getPartMaterType() + "]", partNo);
		    continue;
		}
		
		if(typeCode == null){
		    migLogUtil.log("WTPART_Material", wtpartMast, "Material_Type_Maker_NONE", "Maker내 원재료 Type Grade 부재", "["+maker+"]" + "[" + oneMast.getPartMaterType() + "]" + "[" + oneMast.getPartMaterGrade() + "]", partNo);
		    continue;
		}
		
		// Grade[재질] 가져오기
		String grade = oneMast.getPartMaterGrade();
		if(StringUtils.isEmpty(grade)){
		    migLogUtil.log("WTPART_Material", wtpartMast, "Material_Grade", "원재료 Grade 부재", "["+maker+"]" + "["+type+"]" + "[" + oneMast.getPartMaterGrade() + "]", partNo);
		    continue;
		}
		grade = grade.trim();
		
		// Grade가 존재하는가?
		Map<String, Object> gradeMap = PartBaseHelper.service.getMaterialInfoList(isSuji, makerCode, typeCode);
		List<CommonCodeDTO> gradeCodeList = (List<CommonCodeDTO>) gradeMap.get("options");
		if (gradeCodeList == null || gradeCodeList.size() == 0) {
		    migLogUtil.log("WTPART_Material", wtpartMast, "Material_Grade_Type_Maker", "Type내 원재료 Grade 부재",  "["+maker+"]" + "["+type+"]" + "[" + oneMast.getPartMaterGrade() + "]", partNo);
		    continue;
		}
		
		// Grade 중복 체크
		String gradeCode = null;
		int gradeCount = 0;
		for (CommonCodeDTO dto : gradeCodeList) {
		    String text = dto.getText();
		    String value = dto.getValue();
		    if (grade.equals(text)) {
			gradeCode = value;
			gradeCount = gradeCount + 1;
		    }
		}

		if (gradeCount > 1) {
		    migLogUtil.log("WTPART_Material", wtpartMast, "Material_Grade_Multi", "원재료 Grade 중복",  "["+maker+"]" + "["+type+"]" + "[" + oneMast.getPartMaterGrade() + "]", partNo);
		    continue;
		}

		if (gradeCode == null) {
		    migLogUtil.log("WTPART_Material", wtpartMast, "Material_Grade_Type_Maker_NONE", "MakerType내 원재료 Grade 부재",  "["+maker+"]" + "["+type+"]" + "[" + oneMast.getPartMaterGrade() + "]", partNo);
		    continue;
		}

		// 특성 값 처리 - 도금, Color
		String characteristics = oneMast.getPartMaterCharacteristics();
		String spColor = null;
		String spPlating = null;
		if(StringUtils.isEmpty(characteristics)){
		    
		}else{
		    characteristics = characteristics.trim().toUpperCase();
		    if(isSuji){
			if(spColorMap.containsKey(characteristics)){
			    spColor = spColorMap.get(characteristics);
			}else{
			    migLogUtil.log("WTPART_Material", wtpartMast, "Material_Characteristics", "원재료 Color 특성 부재", oneMast.getPartMaterCharacteristics(), partNo);
			    spColor = null;
			}
		    }else{
			if(spPlatingMap.containsKey(characteristics)){
			    spPlating = spPlatingMap.get(characteristics);
			}else{
			    migLogUtil.log("WTPART_Material", wtpartMast, "Material_Characteristics", "원재료 도금 특성 부재", oneMast.getPartMaterCharacteristics(), partNo);
			    spPlating = null;
			}
		    }
		}
		
		// 전체 속성 업데이트
		updateMaterial(isSuji, wtpartMast, makerCode, typeCode, gradeCode, spColor, spPlating);
		
		wtpartMast = null;
		oneMast = null;
		maker = null;
		makerCode = null; 
		type = null;
		typeCode = null;
		grade = null;
		gradeCode = null;
		spColor = null; 
		spPlating = null;
		partNo = null;
		characteristics = null;
	    }
	
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }
    
    private void updateMaterial(boolean isSuji, WTPartMaster wtpartMast,String makerCode, String typeCode, String gradeCode, String spColor, String spPlating) throws Exception{
	
	QueryResult qr = wt.vc.VersionControlHelper.service.allIterationsOf(wtpartMast);

	while (qr.hasMoreElements()) {

	    WTPart wtPart = ((WTPart) qr.nextElement());
	    // Maker 입력
	    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMaterMaker, makerCode);
	    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMaterType, typeCode);
	    if(isSuji){
		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMaterialInfo, gradeCode);
		if(StringUtils.isNotEmpty(spColor)){
		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpColor, spColor);
		}
	    }else{
		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMaterNotFe, gradeCode);
		if(StringUtils.isNotEmpty(spPlating)){
		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPlating, spPlating);
		}
	    }
	    
	    PersistenceServerHelper.manager.update(wtPart);
	}
    }
    
    private Map<String, String> getMakerMap(boolean isSuji) throws Exception{
	
	// Type 미리 가져오기
	Map<String, String> map = new HashMap<String, String>();
	try {

	    Map<String, Object> parameter = new HashMap<String, Object>();
	    parameter.put("locale", Locale.KOREAN);
	    parameter.put("codeType", "MATERIALMAKER");

	    if (isSuji) {
		parameter.put("code", "MAT1000"); // 수지
	    } else {
		parameter.put("code", "MAT2000"); // 비철
	    }

	    List<Map<String, Object>> codeList = NumberCodeHelper.manager.getNumberCodeList(parameter);
	    for (Map<String, Object> mapIn : codeList) {
		String value =  (String)mapIn.get("value");
		if(value != null){ value = value.toUpperCase();}
		String code = (String)mapIn.get("code");
		map.put( value, code);
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	
	return map;
    }
    
    private Map<String, String> getNumberCodeMapByValKey(String codeType) throws Exception{
	
	// Type 미리 가져오기
	Map<String, String> map = new HashMap<String, String>();
	try {
	    
	    Map<String, Object> parameter = new HashMap<String, Object>();
	    parameter.put("locale", Locale.KOREAN);
	    parameter.put("codeType", codeType);
	    
	    List<Map<String, Object>> codeList = NumberCodeHelper.manager.getNumberCodeList(parameter);
	    for (Map<String, Object> mapIn : codeList) {
		String value =  (String)mapIn.get("value");
		if(value != null){ value = value.toUpperCase();}
		String code = (String)mapIn.get("code");
		map.put( value, code);
	    }
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	
	return map;
    }
    
    private boolean[] isSuji(WTPartMaster wtpartMast, PartMaterDTO oneMast, KETPartClassification claz) throws Exception{
	
	boolean isTrouble = false;
	boolean isSuji = false;
	boolean isSujiDb = false;
	boolean isSujiExcel = false;
	
	if(claz == null){
	    
	    String sujiGubun = oneMast.getPartSujiGubun();
	    if(StringUtils.isEmpty(sujiGubun)){
		
		isTrouble = true;
		migLogUtil.log("WTPART_Material", wtpartMast, "Material_Gubun", "수지 비철 구분 불가", oneMast.getPartSujiGubun(), (wtpartMast==null?null:wtpartMast.getNumber()));
	    }else{
		if(sujiGubun.indexOf("수지") != -1){
		    isSujiExcel = true;
		}else if(sujiGubun.indexOf("비철") != -1){
		    isSujiExcel = false;
		}else{
		    isTrouble = true;
		    migLogUtil.log("WTPART_Material", wtpartMast, "Material_Gubun", "수지 비철 구분 불가", oneMast.getPartSujiGubun(), (wtpartMast==null?null:wtpartMast.getNumber()));
		}
	    }
	    isSuji = isSujiExcel;
	    
	}else{
	    
	    isSujiDb = PartClazHelper.service.isSuji(claz);
	    String sujiGubun = oneMast.getPartSujiGubun();
	    if(StringUtils.isEmpty(sujiGubun)){
		isSujiExcel = isSujiDb;
	    }else{
		if(sujiGubun.indexOf("수지") != -1){
		    isSujiExcel = true;
		}else if(sujiGubun.indexOf("비철") != -1){
		    isSujiExcel = false;
		}else{
		    isSujiExcel = isSujiDb;
		}
	    }
	    
	    if(isSujiDb != isSujiExcel){
		isTrouble = true;
		migLogUtil.log("WTPART_Material", wtpartMast, "Material_Gubun_Differ", "수지 비철 구분 다름", oneMast.getPartSujiGubun()+":" + claz.getClassCode(), (wtpartMast==null?null:wtpartMast.getNumber()));
	    }
	    
	    isSuji = isSujiDb;
	}
	
	return new boolean[]{isTrouble, isSuji};
    }
    
    
    
    
    
}