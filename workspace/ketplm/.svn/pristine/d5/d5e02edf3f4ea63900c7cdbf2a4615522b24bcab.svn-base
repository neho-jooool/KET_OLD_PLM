package ext.ket.part.entity.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

final public class PartDieProjectHelpDTO implements Serializable {

    ////////////////////////////////
    // 금형 속성 시작
    ////////////////////////////////
    // 1. 부품의 부품구분(String) : 분류체계의 구품구분 ('Mold'|'Press'|'구매품' 3중 하나)
    private String moldPartClassificType;
    //2. 금형속성(String) : 
    // 부품분류 Tree의 금형(SET)>시험금형’ 하위의 분류인경우 a '시작', 
    // 나머지 분류인경우 a '양산', 
    // 속성 중 생산구분이 Modify이면 ‘Mo’,  Family이면 ‘Fa’
    // 경우의 수는 ('시작', '양산', '시작MO', '양산MO', '시작Fa', '양산Fa')
    // NumberCode의 codeType MOLDTYPE 참조
    private String moldProps;
    // 3. 금형부품의 Cavity(String) - 숫자이나 문자가 포함될 수 있음
    private String moldSpCavityStd;
    // 4. 금형부품의 목표 C/T(String) - 숫자이나 문자가 포함될 수 있음
    private String moldSpObjectiveCT;
    // 5. 제작처 사내외 구분(String) - '사내' 또는 '외주' < Text
    private String moldSpMContractSAt;
    // 6. 제작처 정보 사내인 경우(NumberCode) 외주인 경우(PartnerNo) < Code
    private String moldSpDieWhere;
    
    ////////////////////////////////
    // 부품 속성 시작
    ////////////////////////////////
    
    // 1. 부품의 부품구분(String) : 분류체계의 구품구분 ('Mold'|'Press'|'구매품' 3중 하나)
    private String partPartClassificType;
    // 2. 부품의 품명
    private String partName;
    // 5. 부품의 생산처 사내외 구분(String) - 사내 또는 외주 < Text
    private String partSpManufAt;
    // 6. 부품의 생산처 정보 사내인 경우(NumberCode) 외주인 경우(PartnerNo) < Code
    private String partSpManufacPlace;
    // 7. 부품의 원재료 객체(Material) - 객체안에 Grade 포함됨 < MOLDMATERIAL Oid
    //    부품의 SpMaterialInfo, SpMaterNotFe 두 개 항목 중에서 분류체계로 판단해서 하나를 넣어준다.
    private String partSpMaterialInfo;
    // 8, 부품의 원재료 특성 : 수지일때 SPColor, 비철 도금 Plating : 
    private String partSpColor;
    private String partSpPlating;    
    public String getMoldPartClassificType() {
        return moldPartClassificType;
    }
    public void setMoldPartClassificType(String moldPartClassificType) {
        this.moldPartClassificType = moldPartClassificType;
    }
    public String getMoldProps() {
        return moldProps;
    }
    public void setMoldProps(String moldProps) {
        this.moldProps = moldProps;
    }
    public String getMoldSpCavityStd() {
        return moldSpCavityStd;
    }
    public void setMoldSpCavityStd(String moldSpCavityStd) {
        this.moldSpCavityStd = moldSpCavityStd;
    }
    public String getMoldSpObjectiveCT() {
        return moldSpObjectiveCT;
    }
    public void setMoldSpObjectiveCT(String moldSpObjectiveCT) {
        this.moldSpObjectiveCT = moldSpObjectiveCT;
    }
    public String getMoldSpMContractSAt() {
        return moldSpMContractSAt;
    }
    public void setMoldSpMContractSAt(String moldSpMContractSAt) {
        this.moldSpMContractSAt = moldSpMContractSAt;
    }
    public String getMoldSpDieWhere() {
        return moldSpDieWhere;
    }
    public void setMoldSpDieWhere(String moldSpDieWhere) {
        this.moldSpDieWhere = moldSpDieWhere;
    }
    public String getPartSpManufAt() {
        return partSpManufAt;
    }
    public void setPartSpManufAt(String partSpManufAt) {
        this.partSpManufAt = partSpManufAt;
    }
    public String getPartSpManufacPlace() {
        return partSpManufacPlace;
    }
    public void setPartSpManufacPlace(String partSpManufacPlace) {
        this.partSpManufacPlace = partSpManufacPlace;
    }
    public String getPartSpMaterialInfo() {
        return partSpMaterialInfo;
    }
    public void setPartSpMaterialInfo(String partSpMaterialInfo) {
        this.partSpMaterialInfo = partSpMaterialInfo;
    }
    public String getPartPartClassificType() {
        return partPartClassificType;
    }
    public void setPartPartClassificType(String partPartClassificType) {
        this.partPartClassificType = partPartClassificType;
    }
    public String getPartName() {
        return partName;
    }
    public void setPartName(String partName) {
        this.partName = partName;
    }
    public String getPartSpColor() {
        return partSpColor;
    }
    public void setPartSpColor(String partSpColor) {
        this.partSpColor = partSpColor;
    }
    public String getPartSpPlating() {
        return partSpPlating;
    }
    public void setPartSpPlating(String partSpPlating) {
        this.partSpPlating = partSpPlating;
    }
    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
