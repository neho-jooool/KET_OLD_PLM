package ext.ket.part.entity.dto;

import ext.ket.shared.dto.BaseDTO;

final public class PartListItemDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private String partOid;
    // 화면 순차 NO
    private String indexNo;

    // Level
    private String lvl;
    // 품명 [ WTPartMaster
    private String partName;
    // 품번 [ 도번
    private String partCombineNo;
    // 품번 [ 도번 a Link
    private String partCombineNoAlink;
    // 전산품번 [ WTPartMaster
    private String partNo;
    // 협력사 품번 [ 제외
    private String partSpManufPartNo;

    // 단품 / Ass'y품 구분 [ Claz
    private String partClassificType;
    // 이미지 경로 [ Vault => <img src="/plm/portal/images/sample_component.png" />
    private String partShape;
    // 제품 SIZE [ 사양
    private String spProdSize;
    // U/S [ BOM
    private String partUS;

    // /////// 시작 금형 /////////////

    // Die No
    private String partStartDieNo;
    // C/V cavity : spCavityStd
    private String partStartCV;
    // 사급구분(외주/사내) : spMContractSAt
    private String partStartSpMContractSAt;
    // 투자비(천원) : sap?
    private String startInvestMoney;

    // /////// 양산 금형 /////////////

    // Die No
    private String partProdDieNo;
    // C/V cavity : spCavityStd
    private String partProdCV;
    // 사급제작구분(외주/사내) : spMContractSAt
    private String partProdSpMContractSAt;
    // 투자비(천원) : sap?
    private String prodInvestMoney;

    // 양산 생산조건
    // 설비 Ton
    private String partProdConditionEquipTon;
    // C/T(SPM) - 사양 SPM( C/T ) : spObjectiveCT
    private String partProdConditionCTSPM;
    // Material
    // Grade
    private String partMaterialGrade;
    // Finish(Color)
    private String partMaterialFinishColor;
    // Maker
    private String partMaterialMaker;

    // 제품중량
    // 부품
    private String partProdWeightPartNet;
    // Scrap
    private String partProdWeightScrap;
    // Total
    private String partProdWeightTotal;

    // 생산처(?)
    private String partProductionWhere;

    // 납품처( 없어짐... 고객처가 맞을 듯 )
    private String partSupplyContract;
    // 포장물류비(원) [ 삭제
    // private String partPackageDistribute;
    // 개발 구분(신규/양산)
    private String partDevLevel;
    // 임가공비 / 구매품단가 [ 삭제
    // private String partTollProcessingPurchasePrice ;
    // 비고 ( 특이사항, 신규 원재료 단가 등) [ 삭제
    private String partNote;

    // ////////////////////////////////////////////////////
    // 전자소자 추가
    // ////////////////////////////////////////////////////

    // BOM 정보
    private String referenceTop;
    private String referenceBottom;

    // Specification ==============
    // Value : spValueValue?
    private String specValue;
    // Volt : spRatedVoltage?
    private String specVolt;
    // Watt : spWatt
    private String specWatt;
    // Tolerance : spTolerance
    private String specTolerance;
    // Temp.(℃) : spOperTemp
    private String specTemp;
    // Package(mm) : spPackageType
    private String specPackage;
    // Packing : spPackageSpec?
    private String specPacking;

    // MSL ==============
    private String msl1; // spMSL
    private String msl2;
    private String msl3;

    // ESD ============== ESD spESD
    // HBM(V)
    private String hbm;
    // SDM(V)
    private String sdm;

    public String getPartOid() {
	return partOid;
    }

    public void setPartOid(String partOid) {
	this.partOid = partOid;
    }

    public String getLvl() {
	return lvl;
    }

    public void setLvl(String lvl) {
	this.lvl = lvl;
    }

    public String getPartName() {
	return partName;
    }

    public void setPartName(String partName) {
	this.partName = partName;
    }

    public String getPartCombineNo() {
	return partCombineNo;
    }

    public void setPartCombineNo(String partCombineNo) {
	this.partCombineNo = partCombineNo;
    }

    public String getPartNo() {
	return partNo;
    }

    public void setPartNo(String partNo) {
	this.partNo = partNo;
    }

    public String getPartClassificType() {
	return partClassificType;
    }

    public void setPartClassificType(String partClassificType) {
	this.partClassificType = partClassificType;
    }

    public String getPartShape() {
	return partShape;
    }

    public void setPartShape(String partShape) {
	this.partShape = partShape;
    }

    public String getSpProdSize() {
	return spProdSize;
    }

    public void setSpProdSize(String spProdSize) {
	this.spProdSize = spProdSize;
    }

    public String getPartUS() {
	return partUS;
    }

    public void setPartUS(String partUS) {
	this.partUS = partUS;
    }

    public String getPartStartDieNo() {
	return partStartDieNo;
    }

    public void setPartStartDieNo(String partStartDieNo) {
	this.partStartDieNo = partStartDieNo;
    }

    public String getPartStartCV() {
	return partStartCV;
    }

    public void setPartStartCV(String partStartCV) {
	this.partStartCV = partStartCV;
    }

    public String getStartInvestMoney() {
	return startInvestMoney;
    }

    public void setStartInvestMoney(String startInvestMoney) {
	this.startInvestMoney = startInvestMoney;
    }

    public String getPartProdDieNo() {
	return partProdDieNo;
    }

    public void setPartProdDieNo(String partProdDieNo) {
	this.partProdDieNo = partProdDieNo;
    }

    public String getPartProdCV() {
	return partProdCV;
    }

    public void setPartProdCV(String partProdCV) {
	this.partProdCV = partProdCV;
    }

    public String getPartStartSpMContractSAt() {
	return partStartSpMContractSAt;
    }

    public void setPartStartSpMContractSAt(String partStartSpMContractSAt) {
	this.partStartSpMContractSAt = partStartSpMContractSAt;
    }

    public String getPartProdSpMContractSAt() {
	return partProdSpMContractSAt;
    }

    public void setPartProdSpMContractSAt(String partProdSpMContractSAt) {
	this.partProdSpMContractSAt = partProdSpMContractSAt;
    }

    public String getProdInvestMoney() {
	return prodInvestMoney;
    }

    public void setProdInvestMoney(String prodInvestMoney) {
	this.prodInvestMoney = prodInvestMoney;
    }

    public String getPartProdConditionEquipTon() {
	return partProdConditionEquipTon;
    }

    public void setPartProdConditionEquipTon(String partProdConditionEquipTon) {
	this.partProdConditionEquipTon = partProdConditionEquipTon;
    }

    public String getPartProdConditionCTSPM() {
	return partProdConditionCTSPM;
    }

    public void setPartProdConditionCTSPM(String partProdConditionCTSPM) {
	this.partProdConditionCTSPM = partProdConditionCTSPM;
    }

    public String getPartMaterialGrade() {
	return partMaterialGrade;
    }

    public void setPartMaterialGrade(String partMaterialGrade) {
	this.partMaterialGrade = partMaterialGrade;
    }

    public String getPartMaterialFinishColor() {
	return partMaterialFinishColor;
    }

    public void setPartMaterialFinishColor(String partMaterialFinishColor) {
	this.partMaterialFinishColor = partMaterialFinishColor;
    }

    public String getPartMaterialMaker() {
	return partMaterialMaker;
    }

    public void setPartMaterialMaker(String partMaterialMaker) {
	this.partMaterialMaker = partMaterialMaker;
    }

    public String getPartProdWeightPartNet() {
	return partProdWeightPartNet;
    }

    public void setPartProdWeightPartNet(String partProdWeightPartNet) {
	this.partProdWeightPartNet = partProdWeightPartNet;
    }

    public String getPartProdWeightScrap() {
	return partProdWeightScrap;
    }

    public void setPartProdWeightScrap(String partProdWeightScrap) {
	this.partProdWeightScrap = partProdWeightScrap;
    }

    public String getPartProdWeightTotal() {
	return partProdWeightTotal;
    }

    public void setPartProdWeightTotal(String partProdWeightTotal) {
	this.partProdWeightTotal = partProdWeightTotal;
    }

    public String getPartProductionWhere() {
	return partProductionWhere;
    }

    public void setPartProductionWhere(String partProductionWhere) {
	this.partProductionWhere = partProductionWhere;
    }

    public String getPartSupplyContract() {
	return partSupplyContract;
    }

    public void setPartSupplyContract(String partSupplyContract) {
	this.partSupplyContract = partSupplyContract;
    }

    public String getPartDevLevel() {
	return partDevLevel;
    }

    public void setPartDevLevel(String partDevLevel) {
	this.partDevLevel = partDevLevel;
    }

    public String getIndexNo() {
	return indexNo;
    }

    public void setIndexNo(String indexNo) {
	this.indexNo = indexNo;
    }

    public String getReferenceTop() {
	return referenceTop;
    }

    public void setReferenceTop(String referenceTop) {
	this.referenceTop = referenceTop;
    }

    public String getReferenceBottom() {
	return referenceBottom;
    }

    public void setReferenceBottom(String referenceBottom) {
	this.referenceBottom = referenceBottom;
    }

    public String getSpecValue() {
	return specValue;
    }

    public void setSpecValue(String specValue) {
	this.specValue = specValue;
    }

    public String getSpecVolt() {
	return specVolt;
    }

    public void setSpecVolt(String specVolt) {
	this.specVolt = specVolt;
    }

    public String getSpecWatt() {
	return specWatt;
    }

    public void setSpecWatt(String specWatt) {
	this.specWatt = specWatt;
    }

    public String getSpecTolerance() {
	return specTolerance;
    }

    public void setSpecTolerance(String specTolerance) {
	this.specTolerance = specTolerance;
    }

    public String getSpecTemp() {
	return specTemp;
    }

    public void setSpecTemp(String specTemp) {
	this.specTemp = specTemp;
    }

    public String getSpecPackage() {
	return specPackage;
    }

    public void setSpecPackage(String specPackage) {
	this.specPackage = specPackage;
    }

    public String getSpecPacking() {
	return specPacking;
    }

    public void setSpecPacking(String specPacking) {
	this.specPacking = specPacking;
    }

    public String getMsl1() {
	return msl1;
    }

    public void setMsl1(String msl1) {
	this.msl1 = msl1;
    }

    public String getMsl2() {
	return msl2;
    }

    public void setMsl2(String msl2) {
	this.msl2 = msl2;
    }

    public String getMsl3() {
	return msl3;
    }

    public void setMsl3(String msl3) {
	this.msl3 = msl3;
    }

    public String getHbm() {
	return hbm;
    }

    public void setHbm(String hbm) {
	this.hbm = hbm;
    }

    public String getSdm() {
	return sdm;
    }

    public void setSdm(String sdm) {
	this.sdm = sdm;
    }

    public String getPartCombineNoAlink() {
	return partCombineNoAlink;
    }

    public void setPartCombineNoAlink(String partCombineNoAlink) {
	this.partCombineNoAlink = partCombineNoAlink;
    }

    public String getPartSpManufPartNo() {
	return partSpManufPartNo;
    }

    public void setPartSpManufPartNo(String partSpManufPartNo) {
	this.partSpManufPartNo = partSpManufPartNo;
    }

    public String getPartNote() {
	return partNote;
    }

    public void setPartNote(String partNote) {
	this.partNote = partNote;
    }

}
