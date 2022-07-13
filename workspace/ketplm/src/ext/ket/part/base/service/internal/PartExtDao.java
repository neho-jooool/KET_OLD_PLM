package ext.ket.part.base.service.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import wt.epm.EPMDocument;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.vc.VersionControlHelper;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.edm.util.VersionHelper;
import e3ps.project.E3PSProjectHelper;
import e3ps.project.ProjectMaster;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.entity.KETHalbPartDieSetPartLink;
import ext.ket.part.entity.KETPartClassLink;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.KETPartProjectLink;
import ext.ket.part.entity.dto.PartCatalogueDTO;
import ext.ket.part.entity.dto.PartListDTO;
import ext.ket.part.entity.dto.PartListItemDTO;
import ext.ket.part.entity.dto.PartSearchMainDTO;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class PartExtDao {

    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
    private DieHalbLinkUtil dieHalbLinkUtil = new DieHalbLinkUtil();

    /**
     * 1. PartList 가져오는 최상위 함수
     * 
     * @param partOid
     * @return
     * @throws Exception
     * @메소드명 : searchPartList
     * @작성자 : yjlee
     * @작성일 : 2014. 10. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */

    public PartListDTO searchPartList(String _partOid) throws Exception {

	PartListDTO partListDTO = new PartListDTO();

	String partOid = _partOid;
	WTPart wtpart = (WTPart) CommonUtil.getObject(partOid);
	if (!VersionControlHelper.isLatestIteration(wtpart)) {
	    wtpart = (WTPart) VersionControlHelper.getLatestIteration(wtpart, false);
	    partOid = CommonUtil.getOIDString(wtpart);
	}

	// 최상위 부품 정보
	Kogger.debug(getClass(), "# part No :" + wtpart.getNumber());
	Kogger.debug(getClass(), "# part Name :" + wtpart.getName());
	Kogger.debug(getClass(), "# part Rev :" + wtpart.getVersionIdentifier().getValue());

	partListDTO.setPartOid(CommonUtil.getOIDString(wtpart));
	partListDTO.setPartNo(wtpart.getNumber());
	partListDTO.setPartName(wtpart.getName());
	partListDTO.setPartRev(wtpart.getVersionIdentifier().getValue());

	String ketPartRev = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpPartRevision);
	partListDTO.setPartKetRev(ketPartRev);

	// 최상위 분류체계 정보
	KETPartClassification claz = null;
	KETPartClassLink classLink = query.queryForFirstLinkByRoleB(KETPartClassLink.class, WTPartMaster.class,
	        ((WTPartMaster) wtpart.getMaster()));
	if (classLink != null) {
	    claz = classLink.getClassific();
	    Kogger.debug(getClass(), "# claz Name :" + claz.getClassKrName());
	    partListDTO.setPartClazNameKr(claz.getClassKrName());
	}

	// 최상위 프로젝트 정보
	KETPartProjectLink projectLink = null;

	// Project Info
	QueryResult qr = PartUtil.getPartProjectLink(wtpart, null);
	while (qr.hasMoreElements()) {
	    Object[] objs = (Object[]) qr.nextElement();
	    projectLink = (KETPartProjectLink) objs[0];
	}

	ProjectMaster projectMaster = null;
	if (projectLink != null) {

	    projectMaster = projectLink.getProject();
	    Kogger.debug(getClass(), "# project No :" + projectMaster.getPjtNo());
	    partListDTO.setProjectNo(projectMaster.getPjtNo());
	    partListDTO.setProjectName(projectMaster.getPjtName());

	    // ◆ ◆ 프로젝트 정보 - 최상위부품관련 정보
	    //	1) 적용차종 projectApplyCarType >  ex) GM BEV GEN2
            //	   연계프로젝트 정보
            //	2) SOP  projectSOP > ex) 2015-12-15
            //	   연계프로젝트 차종 일정
            //	4) 개발담당자 projectDevOwner ex) 홍길동
            //	    프로젝트 CFT 제품개발 Role
            //	5) 4번관련 개발담당부서  projectDevDept ex) 커넥터 개발 2팀
            //	6)개발담당자 projectDevHwOwner ex) 홍길동
            //	    프로젝트 CFT 제품개발(H/W) Role
            //	7) 6번관련 projectDevHwDept 개발담당부서  ex) 커넥터 개발 2팀
            //	8) 예상수량 (천개 / 년) 1년 projectExpect1Qty
            //	9) 예상수량 (천개 / 년) 2년 projectExpect2Qty
            //	10) 예상수량 (천개 / 년) 3년 projectExpect3Qty
            //	11) 예상수량 (천개 / 년) 4년 projectExpect4Qty
            //	12) 예상수량 (천개 / 년) 5년 projectExpect5Qty
            //	13) 예상수량 (천개 / 년) 6년 projectExpect6Qty
            //	14) 예상수량(천개 / 년) 합계 projectExpectSumQty
	    partListDTO = E3PSProjectHelper.service.findProjectInfo1ForPartlist(projectMaster.getPjtNo(), partListDTO);
	}

	List<PartListItemDTO> itemList = searchPartItemList(partListDTO, partOid, wtpart);
	partListDTO.setItemList(itemList);

	return partListDTO;
    }

    // 2. 최상위 정보를 가지고 BOM 정보를 가져온다.
    private List<PartListItemDTO> searchPartItemList(PartListDTO partListDTO, String partOid, WTPart wtpart) throws Exception {

	String ida2a2Str = partOid.substring(partOid.lastIndexOf(":") + 1);
	String partType = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpPartType);
	
	KETBOMQueryBean bean = new KETBOMQueryBean();
	
	Hashtable hashTable = new Hashtable();
	hashTable.put("partOid", ida2a2Str);
	hashTable.put("partType", StringUtil.checkNull(partType));
	hashTable.put("dataType", "");

	List<Map<String, Object>> bomList = null; // searchLatestBom(ida2a2Str, partType, "PART-LIST");
	if (VersionHelper.isLatestRevision(wtpart)) {
	    bomList = bean.getLatestBOM(hashTable);
	} else {
	    bomList = bean.getVersionBOM(hashTable);
	}

	// bom 정보를 활용한 partList 상세 정보
	List<PartListItemDTO> itemList = searchPartBomList(partListDTO, partOid, wtpart, bomList);

	return itemList;
    }

    // 3. BOM 정보를 기반으로 기타 정보를 가져온다. : get part, project data
    private List<PartListItemDTO> searchPartBomList(PartListDTO partListDTO, String rootPartOid, WTPart rootWtpart,
	    List<Map<String, Object>> bomList) throws Exception {

	List<PartListItemDTO> itemList = new ArrayList<PartListItemDTO>();
	KETBOMQueryBean bean = new KETBOMQueryBean();

	int inxNo = 1;
	for (Map<String, Object> map : bomList) {

	    PartListItemDTO itemDTO = new PartListItemDTO();

	    String tempPartNo = (String) map.get("partNo");
	    String tempRev = (String) map.get("rev");
	    String partOid = bean.getPartOid(tempPartNo, tempRev);
	    Kogger.debug(getClass(), "# part Oid :" + partOid);
	    
//	    if(StringUtils.isEmpty(partOid)){
//		continue;
//	    }
	    
	    WTPart wtpart = (WTPart) CommonUtil.getObject(partOid);

	    if (!VersionControlHelper.isLatestIteration(wtpart)) {
		wtpart = (WTPart) VersionControlHelper.getLatestIteration(wtpart, false);
		Kogger.debug(getClass(), "##### part Changed Oid :" + CommonUtil.getOIDString(wtpart));
	    }
	    
	    WTPartMaster wtpartMast = (WTPartMaster) wtpart.getMaster();

	    String partNo = wtpart.getNumber();
	    Kogger.debug(getClass(), "# part No :" + partNo + " ver:" + wtpart.getVersionIdentifier().getValue());

	    String partName = wtpart.getName();
	    String lvl = (String) map.get("lvl");
	    // 0레벨부터 시작이라 하나를 올려줌
	    int level = Integer.parseInt(lvl);
	    String qty = (String) map.get("qty");

	    String partType = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpPartType);
	    if ("F".equals(partType)) {
		qty = bean.getBoxQty(partNo, wt.vc.VersionControlHelper.getVersionIdentifier(wtpart).getValue());
	    }

	    // String unit = (String)map.get("unit");
	    String reftop = (String) map.get("reftop");
	    String refbtm = (String) map.get("refbtm");

	    itemDTO.setIndexNo(String.valueOf(inxNo));
	    itemDTO.setPartOid(partOid);
	    itemDTO.setPartNo(partNo);
	    itemDTO.setPartName(partName);
	    itemDTO.setLvl(String.valueOf(level));

	    // maxLevel check
	    if (level > partListDTO.getMaxLevel()) {
		partListDTO.setMaxLevel(level);
	    }

	    itemDTO.setPartUS(qty);

	    // 전자소자 Reference Top
	    itemDTO.setReferenceTop(reftop);
	    // 전자소자 Reference Bottom
	    itemDTO.setReferenceBottom(refbtm);

	    // 최상위 분류체계 정보
	    KETPartClassification claz = null;
	    KETPartClassLink classLink = query.queryForFirstLinkByRoleB(KETPartClassLink.class, WTPartMaster.class,
		    ((WTPartMaster) wtpart.getMaster()));
	    if (classLink != null) {
		claz = classLink.getClassific();
		String partClassificType = claz.getPartClassificType();
		Kogger.debug(getClass(), "# claz Type :" + claz.getPartClassificType());
		// ◆ 분류체계 부품 분류 포장??
		if ("C".equals(partClassificType)) {
		    partClassificType = "구매품";
		} else if ("P".equals(partClassificType)) {
		    partClassificType = "단품";
		} else if ("A".equals(partClassificType)) {
		    partClassificType = "ASSY";
		} else if ("K".equals(partType)){
		    partClassificType = "포장";
		} else {
		    partClassificType = "";
		}

		itemDTO.setPartClassificType(partClassificType);
	    }

	    // 제품도번
	    List<EPMDocument> primaryList = PartUtil.getReleateEpmDocumnetList(wtpart);
	    Map<String, String> epmKeyMap = new HashMap<String, String>();
	    if (primaryList != null && primaryList.size() > 0) {
		String partCombineNo = "";
		String partCombineNoAlink = "";
		for (EPMDocument epmDoc : primaryList) {
		    String epmNo = epmDoc.getNumber();
		    epmNo = epmNo.endsWith("_3D")? epmNo.substring(0, epmNo.length()-3) : epmNo;
		    epmNo = epmNo.endsWith("_2D")? epmNo.substring(0, epmNo.length()-3) : epmNo;
		    
		    if(epmKeyMap.containsKey(epmNo)){
			continue;
		    }else{
			epmKeyMap.put(epmNo, epmNo);
		    }
		    
		    if (partCombineNo.equals("")) {
			partCombineNo = epmNo;
			partCombineNoAlink = "<a href=\"javascript:openView('" + CommonUtil.getOIDString(epmDoc) + "')\" >" + epmNo + "</a>";
		    } else {
			partCombineNo = partCombineNo + "," + epmNo;
			partCombineNoAlink = partCombineNoAlink + "," + "<a href=\"javascript:openView('" + CommonUtil.getOIDString(epmDoc) + "')\" >" + epmNo + "</a>";
		    }
		}
		itemDTO.setPartCombineNo(partCombineNo);
		// LINK를 빼달라는 요청으로 LINK 뺌
//		itemDTO.setPartCombineNoAlink(partCombineNoAlink);
		itemDTO.setPartCombineNoAlink(partCombineNo);
	    } else {
		itemDTO.setPartCombineNo("");
		itemDTO.setPartCombineNoAlink("");
	    }

	    // ★ 협력사 품번 > 제조사 품번
	    String partSpManufPartNo = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpManufPartNo);
	    itemDTO.setPartSpManufPartNo(partSpManufPartNo);

	    // ◆ 화면에서 처리함.
	    // itemDTO.setPartShape("");

	    // 제품 SIZE [ 사양
	    String spProdSize = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpProdSize);
	    itemDTO.setSpProdSize(spProdSize);

	    // 제품중량
	    // 부품
	    String partProdWeightPartNet = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpNetWeight);
	    itemDTO.setPartProdWeightPartNet(partProdWeightPartNet);
	    // Scrap
	    String partProdWeightScrap = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpScrabWeight);
	    itemDTO.setPartProdWeightScrap(partProdWeightScrap);
	    // Total
	    String partProdWeightTotal = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpTotalWeight);
	    itemDTO.setPartProdWeightTotal(partProdWeightTotal);

	    // /////// 시작 금형 /////////////
	    String partStartDieNo = null;
	    String partProdDieNo = null;
	    String partNote = null;

	    WTPart wtStartPart = null;
	    WTPart wtProdPart = null;

	    //setDieNo(wtpartMast, partStartDieNo, partProdDieNo, partNote, wtStartPart, wtProdPart);
	    List<String> dieStartList = new ArrayList<String>();
	    List<String> dieProdList = new ArrayList<String>();
	    List<String> dieNoList = new ArrayList<String>();

	    WTPart tempWtStartPart = null;
	    WTPart tempWtProdPart = null;

	    List<KETHalbPartDieSetPartLink> dieHalbLink = dieHalbLinkUtil.getHalbDieLinkByHalb(wtpartMast);
	    String tempPartStartDieNo = null;
	    String tempPartProdDieNo = null;
	    for (KETHalbPartDieSetPartLink dhLink : dieHalbLink) {
		WTPartMaster dieMast = dhLink.getDieSet();
		String number = dieMast.getNumber();
		dieNoList.add(number);

		WTPart latestPart = PartBaseHelper.service.getLatestPart(dieMast);
		String spMProdAt = PartSpecGetter.getPartSpec(latestPart, PartSpecEnum.SpMProdAt);

		if (number.indexOf("T") != -1) { // 시작금형 T가 들어가는 품번 중 품번이 제일 빠른 번호
		    dieStartList.add(number);

		    if (tempPartStartDieNo == null) {
			tempPartStartDieNo = number;
			tempWtStartPart = latestPart;
		    } else {
			int compareTo = number.compareTo(tempPartStartDieNo);
			if (compareTo < 0) {
			    tempPartStartDieNo = number;
			    tempWtStartPart = latestPart;
			}
		    }

		} else if ("1".equals(spMProdAt)) { // 양산금형 - Set 중 품번이 제일 빠른 번호
		    dieProdList.add(number);

		    if (tempPartProdDieNo == null) {
			tempPartProdDieNo = number;
			tempWtProdPart = latestPart;
		    } else {
			int compareTo = number.compareTo(tempPartProdDieNo);
			if (compareTo < 0) {
			    tempPartProdDieNo = number;
			    tempWtProdPart = latestPart;
			}
		    }
		}
	    }

	    partStartDieNo = tempPartStartDieNo;
	    partProdDieNo = tempPartProdDieNo;

	    wtStartPart = tempWtStartPart;
	    wtProdPart = tempWtProdPart;

	    if (dieStartList.size() > 1 || dieProdList.size() > 1 || dieNoList.size() > 2) {
		for (String no : dieNoList) {
		    partNote = StringUtils.isEmpty(partNote) ? no : partNote + "," + no;
		}
	    }

	    // Die No
	    if (StringUtils.isNotEmpty(partStartDieNo)) {
		itemDTO.setPartStartDieNo(partStartDieNo);
		// C/V cavity : spCavityStd
		String partStartCV = PartSpecGetter.getPartSpec(wtStartPart, PartSpecEnum.SpCavityStd);
		itemDTO.setPartStartCV(partStartCV);
		// 사급구분(외주/사내) : spMContractSAt
		String partStartSpMContractSAt = PartSpecGetter.getPartSpec(wtStartPart, PartSpecEnum.SpMContractSAt);
		itemDTO.setPartStartSpMContractSAt(getNumberCodeValue(PartSpecEnum.SpMContractSAt, partStartSpMContractSAt));
		// ◆ ◆ 투자비(천원)
		// 투자비(천원) startInvestMoney > 연계된 Proto 제품 Project > 비용 > 예산
		itemDTO = E3PSProjectHelper.service.findProjectInfo2ForPartlist(wtpart.getNumber(), wtStartPart.getNumber(), itemDTO);
	    }
	    
	    // /////// 양산 금형 /////////////

	    // Die No
	    if (StringUtils.isNotEmpty(partProdDieNo)) {
		itemDTO.setPartProdDieNo(partProdDieNo);
		// C/V cavity : spCavityStd
		String partProdCV = PartSpecGetter.getPartSpec(wtProdPart, PartSpecEnum.SpCavityStd);
		itemDTO.setPartProdCV(partProdCV);
		// 사급제작구분(외주/사내) : spMContractSAt
		String partProdSpMContractSAt = PartSpecGetter.getPartSpec(wtProdPart, PartSpecEnum.SpMContractSAt);
		itemDTO.setPartProdSpMContractSAt(getNumberCodeValue(PartSpecEnum.SpMContractSAt, partProdSpMContractSAt));
		// ◆ ◆ 투자비(천원) prodInvestMoney > 연계된 Pilot 제품 Project > 비용 > 예산
		// ◆ ◆  양산 생산조건 >  설비 Ton  partProdConditionEquipTon : .Die No에 연결된 금형프로젝트의 설비정보>형체력
		itemDTO = E3PSProjectHelper.service.findProjectInfo3ForPartlist(wtpart.getNumber(), wtProdPart.getNumber(), itemDTO);
		//  양산 생산조건 > C/T(SPM) - 사양 SPM( C/T ) : spObjectiveCT
		String partProdConditionCTSPM = PartSpecGetter.getPartSpec(wtProdPart, PartSpecEnum.SpObjectiveCT);
		itemDTO.setPartProdConditionCTSPM(partProdConditionCTSPM);
	    }

	    // Material Grade 재질(수지) 또는 재질(비철)
	    String spMaterialInfo = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpMaterialInfo);
	    String spMaterNotFe = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpMaterNotFe);
	    String materialInfoTemp = null;
	    if (StringUtils.isEmpty(spMaterialInfo)) {
		materialInfoTemp = spMaterNotFe;
	    } else {
		materialInfoTemp = spMaterialInfo;
	    }
	    String materialInfo = PartBaseHelper.service.getMaterialInfo(materialInfoTemp);
	    itemDTO.setPartMaterialGrade(materialInfo);
	    // Finish(Color) : 색상(Color)
	    String spColor = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpColor);
	    String spColorElec = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpColorElec);
	    String spColorPurch = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpColorPurch);
	    String colorTemp = null;
	    if (StringUtils.isNotEmpty(spColor)) {
		colorTemp = getNumberCodeValue(PartSpecEnum.SpColor, spColor);
	    } else if (StringUtils.isNotEmpty(spColorElec)) {
		colorTemp = getNumberCodeValue(PartSpecEnum.SpColorElec, spColorElec);
	    } else if (StringUtils.isNotEmpty(spColorPurch)) {
		colorTemp = getNumberCodeValue(PartSpecEnum.SpColorPurch, spColorPurch);
	    }
	    itemDTO.setPartMaterialFinishColor(colorTemp);
	    // Maker 원재료
	    String spMaterMaker = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpMaterMaker);
	    itemDTO.setPartMaterialMaker(getNumberCodeValue(PartSpecEnum.SpMaterMaker, spMaterMaker));

	    // 생산처 SpManufacPlace :  제품일 경우만 처리
	    if ("F".equals(partType)) {
		String spManufacPlace = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpManufacPlace);
		String spManufacPlaceName = PartBaseHelper.service.getManufacPlaceName(spManufacPlace);
		itemDTO.setPartProductionWhere(spManufacPlaceName);
	    }
	    
	    // ◆ ◆  납품처.납품처 partSupplyContract > 제품프로젝트의 고객처 > 완제품에만 표시
	    if ("F".equals(partType)) {
		itemDTO = E3PSProjectHelper.service.findProjectInfo4ForPartlist(wtpart.getNumber(), itemDTO);
	    }

	    // 개발 구분(신규/양산)
	    String partDevLevel = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpPartDevLevel);
	    if ("PC003".equals(partDevLevel)) {
		partDevLevel = "개발단계";
	    } else if ("PC004".equals(partDevLevel)) {
		partDevLevel = "양산단계";
	    }
	    itemDTO.setPartDevLevel(partDevLevel);

	    // // Specification ==============
	    // //Value : spValueValue?
	    String specValue = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpValueValue);
	    itemDTO.setSpecValue(specValue);
	    // // Volt : spRatedVoltage?
	    // String spRatedVoltage = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpRatedVoltage);
	    // itemDTO.setSpecVolt(spRatedVoltage);
	    // Rated voltage
	    String spRatedVoltage = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpRatedVoltage);
	    itemDTO.setSpecVolt(spRatedVoltage);
	    // // Watt : spWatt
	    String specWatt = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpWatt);
	    itemDTO.setSpecWatt(specWatt);
	    // // Tolerance : spTolerance
	    String spTolerance = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpTolerance);
	    itemDTO.setSpecTolerance(getNumberCodeValue(PartSpecEnum.SpTolerance, spTolerance));
	    // // Temp.(℃) : spOperTemp
	    // String specTemp = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpOperTemp);
	    // itemDTO.setSpecTemp(specTemp);
	    // 허용 온도
	    String spPermitTempC = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpPermitTempC);
	    itemDTO.setSpecTemp(spPermitTempC);
	    // // Package(mm) : spPackageType
	    String spPackageType = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpPackageType);
	    itemDTO.setSpecPackage(getNumberCodeValue(PartSpecEnum.SpPackageType, spPackageType));
	    // // Packing : spPackageSpec?
	    // 포장 사양
	    String spPackageSpec = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpPackageSpec);
	    itemDTO.setSpecPacking(getNumberCodeValue(PartSpecEnum.SpPackageSpec, spPackageSpec));

	    // ★ MSL ==============
	    // private String msl1;
	    String spMSL = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpMSL);
	    if ("1".equals(spMSL)) {
		itemDTO.setMsl1("1"); // getNumberCodeValue(PartSpecEnum.SpMSL, spMSL
	    } else if ("2".equals(spMSL)) {
		itemDTO.setMsl2("2");
	    } else if ("3".equals(spMSL)) {
		itemDTO.setMsl3("3");
	    }

	    // ◆ ESD ============== ESD spESD
	    // // HBM(V)
	    String spESD = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpESD);
	    itemDTO.setHbm(getNumberCodeValue(PartSpecEnum.SpESD, spESD));
	    // // SDM(V)
	    // itemDTO.setSdm(spESD);

	    // ★ 비고
	    itemDTO.setPartNote(partNote);

	    itemList.add(itemDTO);

	    inxNo++;
	}

	return itemList;
    }

    public void setDieNo(WTPartMaster halbMast, String partStartDieNo, String partProdDieNo, String partNote, WTPart wtStartPart,
	    WTPart wtProdPart) throws Exception {

	List<String> dieStartList = new ArrayList<String>();
	List<String> dieProdList = new ArrayList<String>();
	List<String> dieNoList = new ArrayList<String>();

	WTPart tempWtStartPart = null;
	WTPart tempWtProdPart = null;

	List<KETHalbPartDieSetPartLink> dieHalbLink = dieHalbLinkUtil.getHalbDieLinkByHalb(halbMast);
	String tempPartStartDieNo = null;
	String tempPartProdDieNo = null;
	for (KETHalbPartDieSetPartLink dhLink : dieHalbLink) {
	    WTPartMaster dieMast = dhLink.getDieSet();
	    String number = dieMast.getNumber();
	    dieNoList.add(number);

	    WTPart latestPart = PartBaseHelper.service.getLatestPart(dieMast);
	    String spMProdAt = PartSpecGetter.getPartSpec(latestPart, PartSpecEnum.SpMProdAt);

	    if (number.indexOf("T") != -1) { // 시작금형 T가 들어가는 품번 중 품번이 제일 빠른 번호
		dieStartList.add(number);

		if (tempPartStartDieNo == null) {
		    tempPartStartDieNo = number;
		    tempWtStartPart = latestPart;
		} else {
		    int compareTo = number.compareTo(tempPartStartDieNo);
		    if (compareTo < 0) {
			tempPartStartDieNo = number;
			tempWtStartPart = latestPart;
		    }
		}

	    } else if ("1".equals(spMProdAt)) { // 양산금형 - Set 중 품번이 제일 빠른 번호
		dieProdList.add(number);

		if (tempPartProdDieNo == null) {
		    tempPartProdDieNo = number;
		    tempWtProdPart = latestPart;
		} else {
		    int compareTo = number.compareTo(tempPartProdDieNo);
		    if (compareTo < 0) {
			tempPartProdDieNo = number;
			tempWtProdPart = latestPart;
		    }
		}
	    }
	}

	partStartDieNo = tempPartStartDieNo;
	partProdDieNo = tempPartProdDieNo;

	wtStartPart = tempWtStartPart;
	wtProdPart = tempWtProdPart;

	if (dieStartList.size() > 1 || dieProdList.size() > 1 || dieNoList.size() > 2) {
	    for (String no : dieNoList) {
		partNote = StringUtils.isEmpty(partNote) ? no : partNote + "," + no;
	    }
	}

    }

    public String getNumberCodeValue(PartSpecEnum partSpecEnum, String code) throws Exception {

	if (StringUtils.isNotEmpty(code)) {
	    code = CodeHelper.manager.getCodeValue(partSpecEnum.getAttrCodeType(), code);
	    return code;
	}

	return "";
    }

//    /**
//     * 최신 BOM 정보를 가져온다.
//     * 
//     * @param partIda2a2
//     * @param partType
//     * @param dataType
//     * @return
//     * @throws Exception
//     * @메소드명 : searchLatestBom
//     * @작성자 : yjlee
//     * @작성일 : 2014. 10. 4.
//     * @설명 :
//     * @수정이력 - 수정일,수정자,수정내용
//     * 
//     */
//    public List<Map<String, Object>> searchLatestBom(String partIda2a2, String partType, String dataType) throws Exception {
//
//	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
//	List<Map<String, Object>> partList = new ArrayList<Map<String, Object>>();
//	List<String> aBildList = new ArrayList<String>();
//	try {
//
//	    String query = getLatestBomQuery(partIda2a2, partType);
//
//	    aBildList.add(partIda2a2);
//	    aBildList.add(partIda2a2);
//
//	    partList = oDaoManager.queryForList(query, aBildList, new RowSetStrategy<Map<String, Object>>() {
//
//		@Override
//		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {
//
//		    Map<String, Object> data = new HashMap<String, Object>();
//		    // 해당 리비전
//		    String partOid = rs.getString("COMPONENT_ITEM_OID") == null ? "" : rs.getString("COMPONENT_ITEM_OID").trim();
//		    // 해당 레벨
//		    String lvl = rs.getString("LVL") == null ? "" : rs.getString("LVL").trim();
//		    // 수량
//		    String qty = rs.getString("QTY") == null ? "" : rs.getString("QTY").trim();
//		    // 단위
//		    String unit = rs.getString("UNIT") == null ? "" : rs.getString("UNIT").trim();
//		    // REFERENCE TOP
//		    String reftop = rs.getString("REFTOP") == null ? "" : rs.getString("REFTOP").trim();
//		    // REFERENCE BOTTOM
//		    String refbtm = rs.getString("REFBOTTOM") == null ? "" : rs.getString("REFBOTTOM").trim();
//
//		    data.put("partOid", partOid);
//		    data.put("lvl", lvl);
//		    data.put("qty", qty);
//		    data.put("unit", unit);
//		    data.put("reftop", reftop);
//		    data.put("refbtm", refbtm);
//
//		    return data;
//		}
//
//	    });
//
//	} catch (SQLException se) {
//	    Kogger.error(getClass(), se);
//	} catch (Exception e) {
//	    Kogger.error(getClass(), e);
//	} finally {
//
//	}
//
//	return partList;
//    }
//
//    // BOM 쿼리
//    private String getLatestBomQuery(String partOid, String partType) throws Exception {
//
//	StringBuffer sql = new StringBuffer();
//
//	sql.append(" SELECT *  \n");
//	sql.append(" FROM ( \n");
//	sql.append(" --------------------------------------------------- \n");
//	sql.append(" -- inner start \n");
//	sql.append(" --------------------------------------------------- \n");
//	sql.append("     --------------------------------------------------- \n");
//	sql.append("     -- top 1개의 정보 \n");
//	sql.append("     --------------------------------------------------- \n");
//	sql.append("     SELECT BOM.* \n");
//	sql.append("         , PH.NAME    STATUSKR  \n");
//	sql.append("         , NVL(HD.HITEMKEY,'') AS HEADERKEY \n");
//	sql.append("         , NVL(HD.ECOHEADERNUMBER,'') AS EONO \n");
//	sql.append("         , NVL(HD.COWORKERID,'') AS COUTERID \n");
//	sql.append("         , NVL(HD.COWORKERNAME,'') AS COUTER \n");
//	sql.append("     FROM \n");
//	sql.append("     ( \n");
//	sql.append("     -------------- top self part info --------------- \n");
//	sql.append("         SELECT 0 AS NUM, \n");
//	sql.append("                0 AS LVL, \n");
//	sql.append("                10 AS ITEMSEQ, \n");
//	sql.append("                null AS ASSEMBLY_ITEM_OID, \n");
//	sql.append("                '' AS ASSEMBLY_ITEM_CODE, \n");
//	sql.append("                '' AS ASSEMBLY_ITEM_REV, \n");
//	sql.append("                TO_CHAR(B0.IDA2A2) AS COMPONENT_ITEM_OID, \n");
//	sql.append("                A0.WTPARTNUMBER AS COMPONENT_ITEM_CODE, \n");
//	sql.append("                A0.NAME AS COMPONENT_ITEM_NAME, \n");
//	sql.append("                (B0.VERSIONIDA2VERSIONINFO || '.' || B0.ITERATIONIDA2ITERATIONINFO) AS COMPONENT_ITEM_REV, \n");
//	sql.append("                '1.000' AS QTY,  B0.STATESTATE AS STATUS,    B0.IDA3A2STATE, \n");
//	sql.append("                'KET_EA' AS UNIT, null AS IDA2A2, '' AS STARTDATE, '' AS ENDDATE,  '' AS MATERIAL, '' AS HARDNESSFROM, '' AS HARDNESSTO, '' AS DESIGNDATE, '' AS ICT, '' AS REFTOP, '' AS REFBOTTOM, \n");
//	sql.append("                null AS USAGELINKOID, \n");
//	sql.append("                (A0.WTPARTNUMBER || '_' ||B0.VERSIONIDA2VERSIONINFO) AS ITEMINFO, '' AS PARENTITEMINFO \n");
//	sql.append("         FROM WTPARTMASTER A0, WTPART B0 \n");
//	sql.append("         WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE AND B0.LATESTITERATIONINFO=1 \n");
//	sql.append("         AND B0.IDA2A2 = ? \n");
//	sql.append("     ) BOM \n");
//	sql.append("     , PHASETEMPLATE PH   \n");
//	sql.append("     , PHASELINK PL, \n");
//	sql.append("     ( \n");
//	sql.append("     -------------- bomheader eono, halb item-key, worker ------------- \n");
//	sql.append("         SELECT KEB.ECOHEADERNUMBER AS ECOHEADERNUMBER \n");
//	sql.append("             , (KEB.ECOITEMCODE||'_'||KEB.BOMVERSION) AS HITEMKEY \n");
//	sql.append("             , KEC.COWORKERID \n");
//	sql.append("             , KEC.COWORKERNAME \n");
//	sql.append("             , KEB.STATESTATE AS STATUS \n");
//	sql.append("         FROM KETBOMECOHEADER KEB, KETBOMECOCOWORKER KEC  \n");
//	sql.append("         WHERE KEB.ECOHEADERNUMBER=KEC.ECOHEADERNUMBER  \n");
//	sql.append("         AND KEB.ECOITEMCODE=KEC.ECOITEMCODE \n");
//	sql.append("         UNION \n");
//	sql.append("         SELECT KB.ECOHEADERNUMBER AS ECOHEADERNUMBER \n");
//	sql.append("             , (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY \n");
//	sql.append("             , KC.COWORKERID \n");
//	sql.append("             , KC.COWORKERNAME \n");
//	sql.append("             , KB.STATESTATE AS STATUS \n");
//	sql.append("         FROM KETBOMHEADER KB, KETBOMECOCOWORKER KC \n");
//	sql.append("         WHERE  KB.NEWBOMCODE=KC.ECOITEMCODE    \n");
//	sql.append("     ) HD \n");
//	sql.append("     WHERE  PL.IDA3A5 = BOM.IDA3A2STATE \n");
//	sql.append("     AND PL.IDA3B5 = PH.IDA2A2 \n");
//	sql.append("     AND PH.PHASESTATE = BOM.STATUS \n");
//	sql.append("     AND BOM.ITEMINFO = HD.HITEMKEY(+) \n");
//	sql.append("     ------------------------------------------------------- \n");
//	sql.append("     UNION \n");
//	sql.append("     ------------------------------------------------------- \n");
//	sql.append("     --------------------------------------------------- \n");
//	sql.append("     -- subbom list의 정보 \n");
//	sql.append("     --------------------------------------------------- \n");
//	sql.append("     SELECT BOM.* \n");
//	sql.append("         ,  PH.NAME    STATUSKR \n");
//	sql.append("         ,  NVL(HD.HITEMKEY,'') AS HEADERKEY \n");
//	sql.append("         ,  NVL(HD.ECOHEADERNUMBER,'') AS EONO \n");
//	sql.append("         ,  NVL(HD.COWORKERID,'') AS COUTERID \n");
//	sql.append("         ,  NVL(HD.COWORKERNAME,'') AS COUTER \n");
//	sql.append("     FROM \n");
//	sql.append("     ( \n");
//	sql.append("         SELECT ROWNUM AS NUM \n");
//	sql.append("              , LEVEL AS LVL \n");
//	sql.append("              , X0.ITEMSEQ \n");
//	sql.append("              , X0.IDA3A5 AS ASSEMBLY_ITEM_OID \n");
//	sql.append("              , X0.PARENTITEMCODE AS ASSEMBLY_ITEM_CODE \n");
//	sql.append("              , (SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5) AS ASSEMBLY_ITEM_REV \n");
//	sql.append("              , X0.VERSIONITEMCODE AS COMPONENT_ITEM_OID \n");
//	sql.append("              , X0.CHILDITEMCODE AS COMPONENT_ITEM_CODE \n");
//	sql.append("              , (SELECT NAME FROM WTPARTMASTER WHERE IDA2A2 = (SELECT IDA3MASTERREFERENCE FROM WTPART WHERE IDA2A2=X0.VERSIONITEMCODE)) AS COMPONENT_ITEM_NAME \n");
//	sql.append("              , X0.CHILDITEMVERSION AS COMPONENT_ITEM_REV \n");
//	sql.append("              , X0.QUANTITY AS QTY,  (SELECT STATESTATE FROM WTPART WHERE IDA2A2=X0.VERSIONITEMCODE) AS STATUS,    (SELECT IDA3A2STATE FROM WTPART WHERE IDA2A2=X0.VERSIONITEMCODE) AS IDA3A2STATE \n");
//	sql.append("              , X0.UNIT, X0.IDA2A2, X0.STARTDATE, X0.ENDDATE,  X0.MATERIAL, X0.HARDNESSFROM, X0.HARDNESSTO, X0.DESIGNDATE, X0.ICT AS ICT, X0.REFTOP AS REFTOP, X0.REFBOTTOM AS REFBOTTOM \n");
//	sql.append("              , X0.IDA2A2 AS USAGELINKOID \n");
//	sql.append("              , (X0.CHILDITEMCODE || '_' || X0.CHILDITEMVERSION) AS ITEMINFO, (X0.PARENTITEMCODE || '_' || (SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5)) AS PARENTITEMINFO \n");
//	sql.append("         FROM KETPARTUSAGELINK X0 \n");
//	sql.append("         START WITH X0.IDA3A5 = ? \n");
//	sql.append("         CONNECT BY PRIOR  \n");
//	sql.append("         ( \n");
//	sql.append("             SELECT MAX(B.IDA3A5) \n");
//	sql.append("             FROM KETPARTUSAGELINK B \n");
//	sql.append("             WHERE B.PARENTITEMCODE = X0.CHILDITEMCODE   \n");
//	// sql.append("             AND ( SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=B.IDA3A5) = \n");
//	// sql.append("                   (CASE INSTR(X0.BOMVERSION,'.') WHEN 0 THEN BOMVERSION ELSE  SUBSTR(BOMVERSION,0,INSTR(BOMVERSION,'.')-1) END ) \n");
//	sql.append("         ) = X0.IDA3A5 \n");
//	sql.append("         ORDER SIBLINGS BY  X0.ITEMSEQ \n");
//	sql.append("     ) BOM  \n");
//	sql.append("     , PHASETEMPLATE PH   \n");
//	sql.append("     , PHASELINK PL, \n");
//	sql.append("     ( \n");
//	sql.append("         SELECT KEB.ECOHEADERNUMBER AS ECOHEADERNUMBER \n");
//	sql.append("             , (KEB.ECOITEMCODE||'_'||KEB.BOMVERSION) AS HITEMKEY \n");
//	sql.append("             , KEC.COWORKERID, KEC.COWORKERNAME \n");
//	sql.append("             , KEB.STATESTATE AS STATUS \n");
//	sql.append("         FROM KETBOMECOHEADER KEB \n");
//	sql.append("            , KETBOMECOCOWORKER KEC  \n");
//	sql.append("         WHERE KEB.ECOHEADERNUMBER=KEC.ECOHEADERNUMBER  \n");
//	sql.append("         AND KEB.ECOITEMCODE=KEC.ECOITEMCODE \n");
//	sql.append("         UNION \n");
//	sql.append("         SELECT KB.ECOHEADERNUMBER AS ECOHEADERNUMBER \n");
//	sql.append("         , (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY \n");
//	sql.append("         , KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS \n");
//	sql.append("         FROM KETBOMHEADER KB, KETBOMECOCOWORKER KC \n");
//	sql.append("         WHERE  KB.NEWBOMCODE=KC.ECOITEMCODE \n");
//	sql.append("     ) HD \n");
//	sql.append("     WHERE  PL.IDA3A5 = BOM.IDA3A2STATE \n");
//	sql.append("     AND PL.IDA3B5 = PH.IDA2A2 \n");
//	sql.append("     AND PH.PHASESTATE = BOM.STATUS \n");
//	sql.append("     AND (BOM.PARENTITEMINFO ||'_'||BOM.COMPONENT_ITEM_CODE) = HD.HITEMKEY(+) \n");
//	sql.append(" --------------------------------------------------- \n");
//	sql.append(" -- inner end \n");
//	sql.append(" --------------------------------------------------- \n");
//	sql.append(" ) \n");
//	sql.append(" ORDER BY NUM \n");
//
//	// Kogger.debug(getClass(), "SQL===> " + sql.toString());
//	Kogger.debug(getClass(), "partOid===> " + partOid);
//
//	return sql.toString();
//    }

    private String sigleQueto(String str) {
	return "'" + str + "'";
    }
    
    // Catalogue Export
    public List<PartCatalogueDTO> getCatalogueList(PartSearchMainDTO partSearchMainDTO) throws Exception{
	
	List<PartCatalogueDTO> list = new ArrayList<PartCatalogueDTO>();
	
	List<String[]> tempList = getCatalougeInfo(partSearchMainDTO);
	for(String[] array : tempList){
	    
	    String partOid = array[0];
	    String catalogueCode = array[1];
	    String classEnName = array[2];
	    String classKrName = array[3];
	    String partNo = array[4];
	    
	    Kogger.debug(getClass(), "# partNo :" + partNo );
	    WTPart wtPart = (WTPart)CommonUtil.getObject(partOid);
	    
	    PartCatalogueDTO dto = new PartCatalogueDTO();
	    // CaltalogueCode > 부품분류에 카테고리 코드 추가  분류(*)
	    dto.setCatalogueCode(StringUtil.checkNull(catalogueCode)); 
	    
	     
	    
	    // 시리즈
	    String Series = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpSeries);
	    if(!StringUtils.isEmpty(Series)){
	    	Series = CodeHelper.manager.getCodeValue(PartSpecEnum.SpSeries.getAttrCodeType(), Series);
	    }
	    if(!StringUtils.isEmpty(Series)){
	    	if(!StringUtils.isEmpty(classEnName)){
	    		classEnName = StringUtil.checkNull(classEnName)+" "+Series;
	    	}
	    	if(!StringUtils.isEmpty(classKrName)){
	    		classKrName = StringUtil.checkNull(classKrName)+" "+Series;
	    	}
	    }
	    
	    // 부품분류(영문)
	    dto.setPartClassEnName(StringUtil.checkNull(classEnName));
	    
	    // 부품분류(국문)
	    dto.setPartclassKrName(StringUtil.checkNull(classKrName));
	    
	    // Fert No
	    dto.setPartNumber(wtPart.getNumber()); 
	    // Fert Name
	    dto.setPartName(StringUtil.checkNull(wtPart.getName())); 
	    
	    // 극수
	    String spNoOfPole = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpNoOfPole);
	    dto.setSpNoOfPole(StringUtil.checkNull(spNoOfPole)); 
	    
	    // Wire Range_AWG : AWG
	    String spWireRangeAWG = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpWireRangeAWG);
	    dto.setSpWireRangeAWG(StringUtil.checkNull(spWireRangeAWG)); 
	    
	    // Wire Range_mm : mm2
	    String spWireRangeMm = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpWireRangeMm);
	    dto.setSpWireRangeMm(StringUtil.checkNull(spWireRangeMm)); 
	    
	    // TabThickness
	    String spTabThickness = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpTabThickness);
	    dto.setSpTabThickness(StringUtil.checkNull(spTabThickness)); 
	    
	    // Stud Size(mm)-없음 //  private String StudSizeMm;
	    // 비철 원재료 두께-없음 //  private String terminalThick;
	    
	    // 도금 > TERMINAL Finish
	    String spPlating = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPlating);
	    spPlating = CodeHelper.manager.getCodeValue(PartSpecEnum.SpPlating.getAttrCodeType(), spPlating);
	    dto.setSpPlating(StringUtil.checkNull(spPlating)); 
	    
	    //원재료 Type
	    String SpMaterType = CodeHelper.manager.getCodeValue(PartSpecEnum.SpMaterType.getAttrCodeType(), PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMaterType));
	    // 재질(수지) > HOUSING 원재료
	    String spMaterialInfo = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMaterialInfo);
	    
	    //spMaterialInfo = PartBaseHelper.service.getMaterialInfo(spMaterialInfo);
	    //dto.setSpMaterialInfo(StringUtil.checkNull(spMaterialInfo)); 
	    
	    // HOUSING 난연등급 //  private String what 
	    
	    // 재질(비철) > TERMINAL 원재료
	    //String spMaterNotFe = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMaterNotFe);
	    //spMaterNotFe = PartBaseHelper.service.getMaterialInfo(spMaterNotFe);
	    //dto.setSpMaterNotFe(StringUtil.checkNull(spMaterNotFe));
	    
	    if(StringUtils.isEmpty(spMaterialInfo)){
		dto.setSpMaterNotFe(StringUtil.checkNull(SpMaterType)); //재질(비철) > TERMINAL 원재료
	    }else{
		dto.setSpMaterialInfo(StringUtil.checkNull(SpMaterType)); //재질(수지) > HOUSING 원재료
	    }
	    
	    // Color > 색상(Color) 색상(전자사업부)
	    String spColor = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpColor);
	    spColor = CodeHelper.manager.getCodeValue(PartSpecEnum.SpColor.getAttrCodeType(), spColor);
	    if(StringUtils.isEmpty(spColor)){
		String spColorElec = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpColorElec);
		spColorElec = CodeHelper.manager.getCodeValue(PartSpecEnum.SpColorElec.getAttrCodeType(), spColorElec);
		if(StringUtils.isNotEmpty(spColorElec)){
		    spColor = spColorElec;
		}
	    }
	    dto.setSpColor(StringUtil.checkNull(spColor));  
	    
	    // 제품 Size 
	    String spProdSize = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpProdSize);
	    dto.setSpProdSize(StringUtil.checkNull(spProdSize));
	    
	    // BracketSize_D > Diameter(Ø)
	    String spBracketSizeD = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpBracketSizeD);
	    dto.setSpBracketSizeD(StringUtil.checkNull(spBracketSizeD));
	    
	    // BracketSize_H > Hole(mm
	    String spBracketSizeH = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpBracketSizeH);
	    dto.setSpBracketSizeH(StringUtil.checkNull(spBracketSizeH));
	    
	    // BracketSize_T > Thicknessmm
	    String spBracketSizeT = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpBracketSizeT);
	    dto.setSpBracketSizeT(StringUtil.checkNull(spBracketSizeT));
	    
	    // 특성1, 특성2 > Description
	    String spCharact1 = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpCharact1);
	    String spCharact2 = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpCharact2);
	    
	    if(StringUtils.isEmpty(spCharact1) && StringUtils.isEmpty(spCharact2)){
	    }else if(StringUtils.isNotEmpty(spCharact1) && StringUtils.isEmpty(spCharact2)){
	    }else if(StringUtils.isEmpty(spCharact1) && StringUtils.isNotEmpty(spCharact2)){
		spCharact1 = spCharact2;
	    }else if(StringUtils.isNotEmpty(spCharact1) && StringUtils.isNotEmpty(spCharact2)){
		spCharact1 = spCharact1 + "," + spCharact2;
	    }
	    
	    dto.setSpCharact1(StringUtil.checkNull(spCharact1));
	    
	    // Certified
	    String spCertified = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpCertified);
	    spCertified = CodeHelper.manager.getCodeValue(PartSpecEnum.SpCertified.getAttrCodeType(), spCertified);
	    dto.setSpCertified(StringUtil.checkNull(spCertified));
	    
	    // 품번.jpg > 대표 이미지 ex) p_02_1.jpg
	    dto.setRepresentImage(wtPart.getNumber()+".jpg");
	    // 품번.jpg > 확대보기 이미지 ex) p_02_2.jpg
	    dto.setRepresentImageExt(wtPart.getNumber()+".jpg");
	    
	    list.add(dto);
	    
	}
	    
	return list;
    }
    
    // Material Type by Material Maker
    public List<String[]> getCatalougeInfo(PartSearchMainDTO partSearchMainDTO) throws Exception {

	List<String[]> list = null;
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" SELECT M.WTPARTNUMBER, C.CATALOGUECODE, C.CLASSKRNAME, C.CLASSENNAME, P.CLASSNAMEA2A2||':'||P.IDA2A2 PARTOID \n");
	    buffer.append(" FROM WTPARTMASTER M, WTPART P \n");
	    buffer.append(" ,( SELECT J.IDA2A2, MAX(BRANCHIDITERATIONINFO) BRANCHIDITERATIONINFO  \n");
	    buffer.append("    FROM WTPART I, WTPARTMASTER J  \n");
	    buffer.append("    WHERE I.IDA3MASTERREFERENCE = J.IDA2A2  \n");
	    buffer.append("    AND I.LATESTITERATIONINFO = 1   \n");
	    buffer.append("    AND I.STATECHECKOUTINFO != 'wrk' \n");
	    buffer.append("    AND I.STATESTATE = 'APPROVED' \n");
	    buffer.append("    AND I.PTC_STR_3TYPEINFOWTPART = 'F' \n");
	    buffer.append("    AND I.PTC_STR_4TYPEINFOWTPART = 'PC004' \n");
	    // 작성일자(from)가 있는 경우
	    if (StringUtils.isNotEmpty(partSearchMainDTO.getCreateStartDate())) {
		String create_start = partSearchMainDTO.getCreateStartDate();
		create_start = create_start.substring(0, 4) + create_start.substring(5, 7) + create_start.substring(8, 10);
		buffer.append(" AND I.createstampa2 >= TO_DATE('" + create_start + "' || '000000','YYYYMMDDHH24MISS')         \n");
	    }
	    // 작성일자(to)가 있는 경우
	    if (StringUtils.isNotEmpty(partSearchMainDTO.getCreateEndDate())) {
		String create_end = partSearchMainDTO.getCreateEndDate();
		create_end = create_end.substring(0, 4) + create_end.substring(5, 7) + create_end.substring(8, 10);
		buffer.append(" AND I.createstampa2 <= TO_DATE('" + create_end + "' || '235959','YYYYMMDDHH24MISS')           \n");
	    }
	    buffer.append("    GROUP BY J.IDA2A2  \n");
	    buffer.append("  ) MAXVERPART \n");
	    buffer.append("  , KETPartClassLink CL \n");
	    buffer.append("  , KETPartClassification C \n");
	    buffer.append(" WHERE M.IDA2A2 = P.IDA3MASTERREFERENCE \n");
	    buffer.append(" AND P.LATESTITERATIONINFO = 1 \n");
	    buffer.append(" AND P.PTC_STR_3TYPEINFOWTPART = 'F' \n");
	    buffer.append(" AND P.PTC_STR_4TYPEINFOWTPART = 'PC004' \n");
	    buffer.append(" AND P.STATESTATE = 'APPROVED' \n");
	    buffer.append(" AND M.IDA2A2 = MAXVERPART.IDA2A2 \n");
	    buffer.append(" AND P.BRANCHIDITERATIONINFO = MAXVERPART.BRANCHIDITERATIONINFO \n");
	    buffer.append(" AND M.IDA2A2 = CL.IDA3B5(+) \n");
	    buffer.append(" AND CL.IDA3A5 = C.IDA2A2(+) \n");
	    // 작성일자(from)가 있는 경우
	    if (StringUtils.isNotEmpty(partSearchMainDTO.getCreateStartDate())) {
		String create_start = partSearchMainDTO.getCreateStartDate();
		create_start = create_start.substring(0, 4) + create_start.substring(5, 7) + create_start.substring(8, 10);
		buffer.append(" AND P.createstampa2 >= TO_DATE('" + create_start + "' || '000000','YYYYMMDDHH24MISS')         \n");
	    }
	    // 작성일자(to)가 있는 경우
	    if (StringUtils.isNotEmpty(partSearchMainDTO.getCreateEndDate())) {
		String create_end = partSearchMainDTO.getCreateEndDate();
		create_end = create_end.substring(0, 4) + create_end.substring(5, 7) + create_end.substring(8, 10);
		buffer.append(" AND P.createstampa2 <= TO_DATE('" + create_end + "' || '235959','YYYYMMDDHH24MISS')           \n");
	    }
	    buffer.append(" ORDER BY M.WTPARTNUMBER \n");

	    String sSql = buffer.toString();
	    list = oDaoManager.queryForList(sSql, new RowSetStrategy<String[]>() {

		@Override
		public String[] mapRow(ResultSet rs) throws SQLException {

		    String[] array = new String[5];

		    array[0] = rs.getString("PARTOID");
		    array[1] = rs.getString("CATALOGUECODE");
		    array[2] = rs.getString("CLASSENNAME");
		    array[3] = rs.getString("CLASSKRNAME");
		    array[4] = rs.getString("WTPARTNUMBER");

		    return array;
		}

	    });

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return list;
    }

}
