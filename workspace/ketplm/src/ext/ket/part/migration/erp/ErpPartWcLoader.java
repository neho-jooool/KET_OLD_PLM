package ext.ket.part.migration.erp;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import ext.ket.part.migration.erp.service.KetMigHelper;
import ext.ket.shared.log.Kogger;

public class ErpPartWcLoader implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static ErpPartWcLoader manager = new ErpPartWcLoader();

    public ErpPartWcLoader() {

    }

    // windchill ext.ket.part.migration.erp.ErpPartWcLoader E:\MigrationData\erp_part <PART_TYPE>
    // windchill ext.ket.part.migration.erp.ErpPartWcLoader d:\ptc\Windchill_10.2\Windchill\loadFiles\ket\part_erp <PART_TYPE>

    public static void main(String[] args) {

	try {

	    String filePath = null;
	    String partType = null;

	    if (args == null || args.length < 2)
		throw new Exception("@@ args need !");
	    else{
		filePath = args[0];
		partType = args[1];
	    }
	    
	    String toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	
	    Kogger.debug(ErpPartWcLoader.class, "@start:" + toDayTime);
	    ErpPartWcLoader.manager.saveFromExcel(filePath, partType);
	    
	    toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(ErpPartWcLoader.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(ErpPartWcLoader.class, e);
	}
    }

    public void saveFromExcel(String filePath, String partType) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class, String.class };
		Object aobj[] = { filePath, partType };

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
	    executeMigration(filePath, partType);
	}
    }
    
    public void executeMigration(String filePath, String partType) throws Exception {
	
	SessionContext sessioncontext = SessionContext.newContext();
	try {

	    SessionHelper.manager.setAdministrator();
	    if("P".equals(partType)){
		KetMigHelper.service.migErpProd(filePath);
	    }else if("D".equals(partType)){
		KetMigHelper.service.migErpDie(filePath);
	    }else if("M1".equals(partType)){
		KetMigHelper.service.migErpMold1(filePath);
	    }else if("M2".equals(partType)){
		KetMigHelper.service.migErpMold2(filePath);
	    }else if("ALL".equals(partType)){
		KetMigHelper.service.migErpProd(filePath);
		KetMigHelper.service.migErpDie(filePath);
		KetMigHelper.service.migErpMold1(filePath);
		KetMigHelper.service.migErpMold2(filePath);
	    }else{
		// "FERT01" , "HALB"
		String[] fileTypeArray = new String[] { "HAWA", "ROH", "PACK", "SCRP", "CAST"
			, "DIEM_01", "DIEM_02", "DIEM_03", "DIEM_04", "DIEM_05", "DIEM_06", "DIEM_07", "DIEM_08"
			, "DIEM_09", "DIEM_10", "DIEM_11", "DIEM_12", "DIEM_13", "DIEM_14"
			, "FERT10", "FERT11","FERT12","FERT13","FERT14","FERT15","FERT16","FERT17","FERT18","FERT19","FERT20"
			, "FERT21"
			, "HALB10", "HALB11","HALB12","HALB13","HALB14","HALB15","HALB16","HALB17","HALB18","HALB19","HALB20"
			, "HALB21", "HALB22","HALB23","HALB24", "HALB25","HALB26","HALB27"
		};
		
		for (String fileType : fileTypeArray) {
		    if(!fileType.equals(partType)){
			continue;
		    }else{
			KetMigHelper.service.migErpOnePartType(filePath, partType);
		    }
		}
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

//    public void executeMigrationBy1000(String filePath, String partType) throws Exception {
//
//	SessionContext sessioncontext = SessionContext.newContext();
//	try {
//
//	    SessionHelper.manager.setAdministrator();
//	    
//	    String[] fileTypeArray = new String[] { "FERT" , "HALB", "HAWA", "ROH", "PACK", "SCRP" };
//	    ErpPartLoader loader = new ErpPartLoader();
//	    
//	    for (String fileType : fileTypeArray) {
//		
//		if(!fileType.equals(partType)) continue;
//		
//		Kogger.debug(getClass(), "****************  Excel Extract Start " + fileType + " **************************");
//		loader.load(filePath, fileType);
//		
//		List<ErpProdPartDTO> prodList = loader.getProdList();
//		List<ErpProdPartDTO> paramList = new ArrayList<ErpProdPartDTO>();
//		for(int k=0; k<prodList.size(); k++){
//		    
//		    paramList.add(prodList.get(k));
//		    
//		    if((k+1)%1000 == 0 || (k+1) == prodList.size()){
//			KetMigHelper.service.migErpProd(filePath, partType, paramList); // updateProd(loader);
//			paramList.clear();
//		    }
//		}
//		
//		Kogger.debug(getClass(), "****************  Excel Extract End " + fileType + " **************************");
//	    }
//	    
//	    fileTypeArray = new String[] { "CAST" };
//	    
//	    for (String fileType : fileTypeArray) {
//		
//		if(!fileType.equals(partType)) continue;
//		
//		Kogger.debug(getClass(), "****************  Excel Extract Start " + fileType + " **************************");
//		loader.load(filePath, fileType);
//		
//		List<ErpDiePartDTO> dieList = loader.getDieList();
//		List<ErpDiePartDTO> paramList = new ArrayList<ErpDiePartDTO>();
//		for(int k=0; k<dieList.size(); k++){
//		    
//		    paramList.add(dieList.get(k));
//		    
//		    if((k+1)%1000 == 0 || (k+1) == dieList.size()){
//			KetMigHelper.service.migErpDie(filePath, partType, paramList); //
//			paramList.clear();
//		    }
//		}
//		
//		Kogger.debug(getClass(), "****************  Excel Extract End " + fileType + " **************************");
//	    }
//	    
//	    fileTypeArray = new String[] {
//		      "DIEM_01", "DIEM_02", "DIEM_03", "DIEM_04", "DIEM_05", "DIEM_06", "DIEM_07", "DIEM_08"
//		    , "DIEM_09", "DIEM_10", "DIEM_11", "DIEM_12", "DIEM_13", "DIEM_14" };
//	    
//	    for (String fileType : fileTypeArray) {
//		
//		if(!fileType.equals(partType)) continue;
//		
//		Kogger.debug(getClass(), "****************  Excel Extract Start " + fileType + " **************************");
//		loader.load(filePath, fileType);
//		
//		List<ErpMoldPartDTO> moldList = loader.getMoldList();
//		List<ErpMoldPartDTO> paramList = new ArrayList<ErpMoldPartDTO>();
//		for(int k=0; k<moldList.size(); k++){
//		    
//		    paramList.add(moldList.get(k));
//		    
//		    if((k+1)%1000 == 0 || (k+1) == moldList.size()){
//			KetMigHelper.service.migErpMold(filePath, partType, paramList); //
//			paramList.clear();
//		    }
//		}
//		
//		Kogger.debug(getClass(), "****************  Excel Extract End " + fileType + " **************************");
//	    }
//
//	} catch (Exception e) {
//	    Kogger.error(getClass(), e);
//	    throw e;
//	} finally {
//	    SessionContext.setContext(sessioncontext);
//	}
//    }
//    
//    private void updateMold(ErpPartLoader loader)throws Exception {
//	Transaction trx = null;
//	try {
//	    List<ErpMoldPartDTO> moldList = loader.getMoldList();
//
//	    for (ErpMoldPartDTO dto : moldList) {
//
//		String partNo = dto.getPartNo();
//		log.debug("# M partNo:" + partNo + " " + dto.getExcelFileName());
//		WTPartMaster partMaster = getMaster(partNo);
//		if (partMaster == null) {
//		    log.debug("####### partNo:" + partNo + " " + dto.getExcelFileName() + " not found!");
//		    continue;
//		}
//		
//		trx = new Transaction();
//		trx.start();
//		
//		WTPart wtPart = getLatestObject(partMaster);
//		// 부품명 수정
//		if (!wtPart.getName().equals(dto.getPartName())) {
//		    WTPartMasterIdentity identity = (WTPartMasterIdentity) partMaster.getIdentificationObject();
//		    identity.setName(dto.getPartName());
//		    partMaster = (WTPartMaster) IdentityHelper.service.changeIdentity(partMaster, identity);
//		}
//
//		// 단위
//		if (!(partMaster.getDefaultUnit().toString()).equalsIgnoreCase("KET_" + dto.getPartUnit()) && StringUtils.isNotEmpty(dto.getPartUnit())) {
//		    partMaster.setDefaultUnit(QuantityUnit.toQuantityUnit("KET_" + dto.getPartUnit()));
//		    partMaster = (WTPartMaster) PersistenceHelper.manager.modify(partMaster);
//		}
//
//		// Part TYPE
//		if ("DIEM".equals(dto.getPartType())) {
//		    String nowValue = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
//		    if (!"M".equals(nowValue)) {
//			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartType, "M");
//		    }
//		}
//
//		// 재질 spMaterDie
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMaterDie, dto.getSpMaterDie());
//
//		// 삭제 spIsDeleted
//		if (StringUtils.isNotEmpty(dto.getSpIsDeleted())) {
//		    if ("Y".equals(dto.getSpIsDeleted())) {
//			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, dto.getSpIsDeleted());
//		    } else {
//			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, "N");
//		    }
//		} else {
//		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, "N");
//		}
//
//		// save - update
//		PersistenceServerHelper.manager.update(wtPart);
//
//		wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
//
//		trx.commit();
//		trx = null;
//		dto = null;
//		wtPart = null;
//		/**
//select m.wtpartnumber, m.name, M.DEFAULTUNIT, p.PTC_STR_3TYPEINFOWTPART partType
//, PTC_STR_51TYPEINFOWTPART material, PTC_STR_5TYPEINFOWTPART deletedFlag
//, P.VERSIONIDA2VERSIONINFO revision 
//from wtpartmaster m, wtpart p
//where 1=1
//and m.ida2a2 = P.IDA3MASTERREFERENCE
//and P.LATESTITERATIONINFO = 1 
//and wtpartnumber in ('07884000-006', '10617001-048')
//		 */
//	    }
//
//	    moldList.clear();
//	} catch (Exception e) {
//	    Kogger.error(getClass(), e);
//	    trx.rollback();
//	    throw e;
//	}
//    }
//    private void updateDie(ErpPartLoader loader)throws Exception {
//	Transaction trx = null;
//	try {
//
//	    List<ErpDiePartDTO> dieList = loader.getDieList();
//	    for (ErpDiePartDTO dto : dieList) {
//
//		String partNo = dto.getPartNo();
//		log.debug("# D partNo:" + partNo);
//		WTPartMaster partMaster = getMaster(partNo);
//		if (partMaster == null) {
//		    log.debug("####### partNo:" + partNo + " not found!");
//		    continue;
//		}
//		trx = new Transaction();
//		trx.start();
//		WTPart wtPart = getLatestObject(partMaster);
//
//		// Part TYPE
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartType, "D");
//
//		// 사급구분
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMContractSAt, dto.getSpMContractSAt());
//		// 금형구분
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMMoldAt, dto.getSpMMoldAt());
//		// 제작구분
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMMakingAt, dto.getSpMMakingAt());
//		// 생산구분
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMProdAt, dto.getSpMProdAt());
//		// 구매그룹
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPuchaseGroup, dto.getSpPuchaseGroup());
//		// 플랜트
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPlant, dto.getSpPlant());
//		// 업체번호 - 금형제작처
//		String tempDieWhere = dto.getSpDieWhere(); 
//		if(StringUtils.isNotEmpty(tempDieWhere)){
//		    int tempDieWhereInt = Integer.parseInt(tempDieWhere);
//		    tempDieWhere = String.valueOf(tempDieWhereInt);
//		}
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpDieWhere, tempDieWhere);
//		// 개발담당자
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpDevManNm, dto.getSpDevManNm());
//		// 설계담당자 - 금형담당자
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpDesignManNm, dto.getSpDesignManNm());
//		// 제작담당자
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpManufacManNm, dto.getSpManufacManNm());
//		// 표준SPM(C/
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpObjectiveCT, dto.getSpObjectiveCT());
//		// 표준 Cavity
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpCavityStd, dto.getSpCavityStd());
//		// 자재코드 - 반제품 연결
//		if (StringUtils.isNotEmpty(dto.getPartHalb())) {
//		    WTPartMaster halbPartMaster = getMaster(dto.getPartHalb());
//		    if (halbPartMaster != null) {
//			if (!hasDieHalbRelation(partMaster, halbPartMaster.getNumber())) {
//			    KETHalbPartDieSetPartLink hdLink = KETHalbPartDieSetPartLink.newKETHalbPartDieSetPartLink(halbPartMaster, partMaster);
//			    PersistenceHelper.manager.save(hdLink);
//			}
//		    }
//		}
//
//		// 삭제 spIsDeleted
//		if (StringUtils.isNotEmpty(dto.getSpIsDeleted())) {
//		    if ("Y".equals(dto.getSpIsDeleted())) {
//			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, dto.getSpIsDeleted());
//		    } else {
//			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, "N");
//		    }
//		} else {
//		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, "N");
//		}
//
//		// save - update
//		PersistenceServerHelper.manager.update(wtPart);
//
//		wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
//
//		trx.commit();
//		trx = null;
//		dto = null;
//		wtPart = null;
//		/**
//select m.wtpartnumber, p.PTC_STR_3TYPEINFOWTPART partType
//, PTC_STR_139TYPEINFOWTPART 사급구분, PTC_STR_140TYPEINFOWTPART 금형구분
//, PTC_STR_141TYPEINFOWTPART 제작구분, PTC_STR_142TYPEINFOWTPART 생산구분
//, PTC_STR_135TYPEINFOWTPART 구매담당그룹, PTC_STR_134TYPEINFOWTPART plant 
//, PTC_STR_143TYPEINFOWTPART 금형제작처, PTC_STR_136TYPEINFOWTPART 개발담당자
//, PTC_STR_137TYPEINFOWTPART 금형담당자, PTC_STR_138TYPEINFOWTPART 제작담당자
//, PTC_STR_144TYPEINFOWTPART 목표CT, PTC_STR_145TYPEINFOWTPART Cavity
//, PTC_STR_5TYPEINFOWTPART spIsDeleted
//, P.VERSIONIDA2VERSIONINFO revision
//, HM.WTPARTNUMBER
//from wtpartmaster m, wtpart p, KETHalbPartDieSetPartLink hl, wtpartmaster hm
//where 1=1
//and m.ida2a2 = P.IDA3MASTERREFERENCE 
//and M.IDA2A2 = HL.IDA3b5(+)
//and hl.ida3a5 = HM.IDA2A2(+)
//--and HM.WTPARTNUMBER is not null
//and P.LATESTITERATIONINFO = 1 
//and m.wtpartnumber in ('1A147000', '23732000')
//		 */
//	    }
//	    dieList.clear();
//
//	} catch (Exception e) {
//	    Kogger.error(getClass(), e);
//	    trx.rollback();
//	    throw e;
//	}
//    }
//    private void updateProd(ErpPartLoader loader)throws Exception {
//	Transaction trx = null;
//	try {
//	    List<ErpProdPartDTO> prodList = loader.getProdList();
//	    for (ErpProdPartDTO dto : prodList) {
//
//		String partNo = dto.getPartNo();
//		log.debug("# P partNo:" + partNo + " " + dto.getPartType());
//		WTPartMaster partMaster = getMaster(partNo);
//		if (partMaster == null) {
//		    log.debug("####### partNo:" + partNo + " " + dto.getPartType() + " not found!");
//		    continue;
//		}
//		
//		trx = new Transaction();
//		trx.start();
//		
//		WTPart wtPart = getLatestObject(partMaster);
//
//		// 부품명 수정
//		if (!wtPart.getName().equals(dto.getPartName())) {
//		    WTPartMasterIdentity identity = (WTPartMasterIdentity) partMaster.getIdentificationObject();
//		    identity.setName(dto.getPartName());
//		    partMaster = (WTPartMaster) IdentityHelper.service.changeIdentity(partMaster, identity);
//		}
//
//		// 단위
//		if (!(partMaster.getDefaultUnit().toString()).equalsIgnoreCase("KET_" + dto.getPartUnit()) && StringUtils.isNotEmpty(dto.getPartUnit())) {
//		    partMaster.setDefaultUnit(QuantityUnit.toQuantityUnit("KET_" + dto.getPartUnit()));
//		    partMaster = (WTPartMaster) PersistenceHelper.manager.modify(partMaster);
//		}
//
//		// Part TYPE
//		if ("FERT".equals(dto.getPartType())) {
//		    String nowValue = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
//		    if (!PartTypeEnum.FERT.getPlmCode().equals(nowValue)) {
//			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartType, PartTypeEnum.FERT.getPlmCode());
//		    }
//		} else if ("HALB".equals(dto.getPartType())) {
//		    String nowValue = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
//		    if (!PartTypeEnum.HALB.getPlmCode().equals(nowValue)) {
//			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartType, PartTypeEnum.HALB.getPlmCode());
//		    }
//		} else if ("HAWA".equals(dto.getPartType())) {
//		    String nowValue = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
//		    if (!PartTypeEnum.HAWA.getPlmCode().equals(nowValue)) {
//			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartType, PartTypeEnum.HAWA.getPlmCode());
//		    }
//		} else if ("ROH".equals(dto.getPartType())) {
//		    String nowValue = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
//		    if (!PartTypeEnum.ROH.getPlmCode().equals(nowValue)) {
//			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartType, PartTypeEnum.ROH.getPlmCode());
//		    }
//		} else if ("PACK".equals(dto.getPartType())) {
//		    String nowValue = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
//		    if (!PartTypeEnum.PACK.getPlmCode().equals(nowValue)) {
//			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartType, PartTypeEnum.PACK.getPlmCode());
//		    }
//		} else if ("SCRP".equals(dto.getPartType())) {
//		    String nowValue = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
//		    if (!PartTypeEnum.SCRP.getPlmCode().equals(nowValue)) {
//			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartType, PartTypeEnum.SCRP.getPlmCode());
//		    }
//		}
//
//		// 중량
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpTotalWeight, dto.getTotalW());
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpNetWeight, dto.getNetW());
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpScrabWeight, dto.getScrabW());
//		// 제품 SIZE
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpProdSize, dto.getProdSize());
//		// 스크랩코드
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpScrabCode, dto.getScrabCode());
//
//		// 삭제 spIsDeleted
//		if (StringUtils.isNotEmpty(dto.getIsDeleted())) {
//		    if ("Y".equals(dto.getIsDeleted())) {
//			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, dto.getIsDeleted());
//		    } else {
//			PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, "N");
//		    }
//		} else {
//		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, "N");
//		}
//
//		// 시리즈
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpSeries, dto.getSeries());
//		// 방수
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpWaterProof, dto.getWaterProof());
//		// 재질
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMaterialInfo, dto.getMaterFe());
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpMaterNotFe, dto.getMaterNotFe());
//		// 색상
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpColor, dto.getColor());
//		// 도금
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPlating, dto.getPlating());
//		// 대표금형
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpRepresentM, dto.getRepresentM());
//		// IsYazaki
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsYazaki, dto.getIsYazaki());
//		// yazakino
//		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpYazakiNo, dto.getYazakiNo());
//
//		// save - update
//		PersistenceServerHelper.manager.update(wtPart);
//
//		wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
//
//		trx.commit();
//		trx = null;
//		dto = null;
//		wtPart = null;
//		/**
//-- Prod No
//select m.wtpartnumber, m.name, M.DEFAULTUNIT, p.PTC_STR_3TYPEINFOWTPART partType
//, PTC_STR_9TYPEINFOWTPART totalW, PTC_STR_8TYPEINFOWTPART netW
//, PTC_STR_10TYPEINFOWTPART scrabW, PTC_STR_23TYPEINFOWTPART prodSize
//, PTC_STR_15TYPEINFOWTPART scrabCode, PTC_STR_5TYPEINFOWTPART deletedFlag 
//, PTC_STR_21TYPEINFOWTPART series, PTC_STR_16TYPEINFOWTPART waterProof
//, PTC_STR_49TYPEINFOWTPART mater, PTC_STR_50TYPEINFOWTPART materNotFe
//, PTC_STR_18TYPEINFOWTPART color, PTC_STR_53TYPEINFOWTPART plating
//, PTC_STR_48TYPEINFOWTPART repMold, PTC_STR_11TYPEINFOWTPART isYazaki
//, PTC_STR_12TYPEINFOWTPART yazakiNo
//, P.VERSIONIDA2VERSIONINFO revision
//from wtpartmaster m, wtpart p
//where 1=1
//and m.ida2a2 = P.IDA3MASTERREFERENCE 
//and wtpartnumber in ('07884000-006', '10617001-048')
//		 */
//	    }
//
//	    prodList.clear();
//	} catch (Exception e) {
//	    Kogger.error(getClass(), e);
//	    trx.rollback();
//	    throw e;
//	}
//    }
//    
//    private boolean hasDieHalbRelation(WTPartMaster dieSet, String halbCode) throws Exception {
//	
//	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
//	QueryResult qr = query.getQueryResultByRoleB(WTPartMaster.class, KETHalbPartDieSetPartLink.class, WTPartMaster.class, dieSet);
//	while (qr.hasMoreElements()) {
//	    Object[] obj = (Object[]) qr.nextElement();
//	    WTPartMaster halbPartMast = (WTPartMaster) obj[0];
//	    log.debug("related halb part: " + halbPartMast.getNumber());
//	    if(halbCode.equals(halbPartMast.getNumber())){
//		return true;
//	    }
//	}
//	
//	return false;
//    }
//    
//    private WTPartMaster getMaster(String partNo) throws WTException {
//	
//	WTPartMaster master = null;
//	QuerySpec query = new QuerySpec(WTPartMaster.class);
//	query.appendWhere(new SearchCondition(WTPartMaster.class, WTPartMaster.NUMBER, SearchCondition.EQUAL,  partNo ), new int[]{0});
//	QueryResult qr = PersistenceHelper.manager.find(query);
//	if(qr.hasMoreElements()){ 
//	    master = (WTPartMaster)qr.nextElement();
//	}
//	
//	return master;
//    }
//    
//    private WTPart getLatestObject(WTPartMaster master) throws WTException {
//
//	WTPart rc = null;
//	QueryResult qr = wt.vc.VersionControlHelper.service.allVersionsOf(master);
//
//	while (qr.hasMoreElements()) {
//	    
//	    WTPart obj = ((WTPart) qr.nextElement());
//
//	    if (rc == null || obj.getVersionIdentifier().getSeries().greaterThan(rc.getVersionIdentifier().getSeries())) {
//		rc = obj;
//	    }
//	}
//	
//	if (rc != null)
//	    return (WTPart) VersionControlHelper.getLatestIteration(rc, false);
//	else
//	    return rc;
//    }

}
