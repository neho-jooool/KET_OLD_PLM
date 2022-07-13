package ext.ket.part.base.service.internal;

import org.apache.commons.lang.StringUtils;

import wt.part.WTPart;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.beans.MoldProjectHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.dto.PartDieProjectHelpDTO;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.log.Kogger;

/**
 * 
 * @클래스명 : PartDieInfoHandler
 * @작성자 : yjlee
 * @작성일 : 2014. 10. 30.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class PartDieInfoHandler {

    // 프로젝트와 원재료 등 정보 공유 및 Sync : 부품 수정 시점 등
    public PartDieProjectHelpDTO getPartDieInfoForSync(String partNo, String dieNo) throws Exception {

	PartDieProjectHelpDTO dto = new PartDieProjectHelpDTO();

	if (StringUtils.isNotEmpty(dieNo)) {

	    WTPart wtPart = PartBaseHelper.service.getLatestPart(dieNo);
	    KETPartClassification claz = PartClazHelper.service.getPartClassification(wtPart);
	    // //////////////////////////////
	    // 금형 속성 시작
	    // //////////////////////////////
	    if (claz != null) {

		// 1. 부품의 부품구분(String) : 분류체계의 구품구분 ('Mold'|'Press'|'구매품' 3중 하나)
		String moldPartClassificType = claz.getPartClassificType();
		dto.setMoldPartClassificType(getPartCalssTypeName(moldPartClassificType));

		// 2. 금형속성(String) :
		// 부품분류 Tree의 금형(SET)>시험금형’ 하위의 분류인경우 a '시작',
		// 나머지 분류인경우 a '양산',
		// 속성 중 생산구분이 Modify이면 ‘Mo’, Family이면 ‘Fa’
		// 경우의 수는 ('시작', '양산', '시작MO', '양산MO', '시작Fa', '양산Fa')
		// NumberCode의 codeType MOLDTYPE 참조
		// 값을 넘겨주기로 함
		String classCode = claz.getClassCode();
		String spMProdAt = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMProdAt);
		dto.setMoldProps(getMoldProps(classCode, spMProdAt));
	    }

	    // 3. 금형부품의 Cavity(String) - 숫자이나 문자가 포함될 수 있음
	    String moldSpCavityStd = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpCavityStd);
	    dto.setMoldSpCavityStd(moldSpCavityStd);

	    // 4. 금형부품의 목표 C/T(String) - 숫자이나 문자가 포함될 수 있음
	    String moldSpObjectiveCT = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpObjectiveCT);
	    dto.setMoldSpObjectiveCT(moldSpObjectiveCT);

	    // 5. 제작처 사내외 구분(String) - '사내' 또는 '외주' < Text
	    String spMContractSAt = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMContractSAt);
	    dto.setMoldSpMContractSAt(getManufName(spMContractSAt));

	    // 6. 제작처 정보 사내인 경우(NumberCode) 외주인 경우(PartnerNo) < Code
	    String moldSpDieWhere = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpDieWhere);
	    dto.setMoldSpDieWhere(moldSpDieWhere);
	}

	if (StringUtils.isNotEmpty(partNo)) {

	    WTPart wtPart = PartBaseHelper.service.getLatestPart(partNo);
	    KETPartClassification claz = PartClazHelper.service.getPartClassification(wtPart);
	    // //////////////////////////////
	    // 부품 속성 시작
	    // //////////////////////////////
	    if (claz != null) {

		// 1. 부품의 부품구분(String) : 분류체계의 구품구분 ('Mold'|'Press'|'구매품' 3중 하나)
		String partPartClassificType = claz.getPartClassificType();
		dto.setPartPartClassificType(getPartCalssTypeName(partPartClassificType));
	    }

	    // 2. 부품의 품명
	    dto.setPartName(wtPart.getName());

	    // 5. 부품의 생산처 사내외 구분(String) - 사내 또는 외주 < Text SpManufAt
	    String spManufAt = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpManufAt);
	    dto.setPartSpManufAt(getManufName(spManufAt));

	    // 6. 부품의 생산처 정보 사내인 경우(NumberCode) 외주인 경우(PartnerNo) < Code SpManufacPlace
	    String spManufacPlace = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpManufacPlace);
	    dto.setPartSpManufacPlace(spManufacPlace);

	    if (claz != null) {

		// 7. 부품의 원재료 객체(Material) - 객체안에 Grade 포함됨 < MOLDMATERIAL Oid
		// 부품의 SpMaterialInfo, SpMaterNotFe 두 개 항목 중에서 분류체계로 판단해서 하나를 넣어준다.
		boolean isSuji = PartClazHelper.service.isSuji(claz);
		if (isSuji) {
		    String spMaterialInfo = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMaterialInfo);
		    if (StringUtils.isNotEmpty(spMaterialInfo)) {
			dto.setPartSpMaterialInfo("e3ps.project.material.MoldMaterial:" + spMaterialInfo);
		    }
		} else {
		    String spMaterNotFe = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMaterNotFe);
		    if (StringUtils.isNotEmpty(spMaterNotFe)) {
			dto.setPartSpMaterialInfo("e3ps.project.material.MoldMaterial:" + spMaterNotFe);
		    }
		}

		// 8. 특성
		if (isSuji) {
		    // 수지일때 - SPColor code 값 : numbercode : SPECCOLOR
		    String spColor = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpColor);
		    dto.setPartSpColor(spColor);
		} else {
		    // 비철일때 - 도금 Plating code 값 : numbercode : SPECPLATING
		    String partSpPlating = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPlating);
		    dto.setPartSpPlating(partSpPlating);
		}
	    }
	}

	Kogger.debug(getClass(), dto);

	return dto;
    }

    private String getPartCalssTypeName(String partClassificType) {

	String partCalssTypeName = null;
	if ("M".equals(partClassificType)) {
	    partCalssTypeName = "Mold";
	} else if ("S".equals(partClassificType)) {
	    partCalssTypeName = "Press";
	} else if ("C".equals(partClassificType)) {
	    partCalssTypeName = "구매품";
	}

	return partCalssTypeName;
    }

    private String getManufName(String spManufAt) {

	String spManufAtName = null;
	if ("1".equals(spManufAt)) {
	    spManufAtName = "사내";
	} else if ("2".equals(spManufAt)) {
	    spManufAtName = "외주";
	}

	return spManufAtName;
    }

    private String getMoldProps(String classCode, String spMProdAt) {

	// 2. 금형속성(String) :
	// 부품분류 Tree의 금형(SET)>시험금형’ 하위의 분류인경우 a '시작',
	// 나머지 분류인경우 a '양산',
	// 속성 중 생산구분이 Modify이면 ‘Mo’, Family이면 ‘Fa’
	// 경우의 수는 ('시작', '양산', '시작MO', '양산MO', '시작Fa', '양산Fa')
	// NumberCode의 codeType MOLDTYPE 참조
	// 값을 넘겨주기로 함
	String modlProps = "";
	if (classCode != null && classCode.startsWith("S40")) { // 부품분류 Tree의 금형(SET)>시험금형’ 하위의 분류인경우
	    modlProps = "시작";
	} else {
	    modlProps = "양산";
	}

	if ("3".equals(spMProdAt)) { // 생산구분이 Modify이면
	    modlProps = modlProps + "Mo";
	}
	// TODO TKLEE 확인 필요 함.
	// Family가 추가후 ERP 전송, 채번 수정, 
//	else if ("2".equals(spMProdAt)) { // Family이면
//	    modlProps = modlProps + "Fa";
//	}

	return modlProps;
    }
    
    // 부품 수정시 호출되어져야 할 method 입니다.
    // 부품 수정, 부품 Admin 수정 시점
    public void updatePartDieInfo(WTPart wtPart) throws Exception {
	
	String partType = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType );
	String partNo = wtPart.getNumber();
	
	/**
	 * 1) FERT, HALB, 원자재, 상품 일 경우 : partNo: o, dieNo : x
	 */
	if("F".equals(partType) || "H".equals(partType) || "R".equals(partType) || "W".equals(partType) ){
	    MoldProjectHelper.updateMoldItemForSync(partNo, null);
	}
	
	/**
	 * 2) Die :  partNo: x, dieNo : o
	 */
	if("D".equals(partType)){
	    MoldProjectHelper.updateMoldItemForSync(null, partNo);
	}
	
	/**
	 * 3) 금형부품, 패키지, 스크랩일 경우에는 제외
	 */
	
	// nothing
    }
    
    public void updateProudctInfo(WTPart wtPart) throws Exception{
	MoldProjectHelper.updateProductInfoForSync(wtPart);
    }

}
