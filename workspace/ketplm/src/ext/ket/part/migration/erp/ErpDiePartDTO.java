package ext.ket.part.migration.erp;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ErpDiePartDTO implements Serializable {

    private String partNo;
    private String spMContractSAt;// 사급구분
    private String spMMoldAt;// 금형구분
    private String spMMakingAt;// 제작구분
    private String spMProdAt;// 생산구분
    private String spPuchaseGroup;// 구매그룹
    private String spPlant;// 플랜트
    private String spDieWhere;// 업체번호 - 금형제작처
    private String spDevManNm;// 개발담당자
    private String spDesignManNm;// 설계담당자 - 금형담당자
    private String spManufacManNm;// 제작담당자
    private String spObjectiveCT;// 표준SPM(C/
    private String spCavityStd;// 표준 Cavity
    private String spIsDeleted;// 삭제 Flag
    private String partHalb;// 자재코드

    public String getPartNo() {
	return partNo;
    }

    public void setPartNo(String partNo) {
	this.partNo = partNo;
    }

    public String getSpMContractSAt() {
	return spMContractSAt;
    }

    public void setSpMContractSAt(String spMContractSAt) {
	this.spMContractSAt = spMContractSAt;
    }

    public String getSpMMoldAt() {
	return spMMoldAt;
    }

    public void setSpMMoldAt(String spMMoldAt) {
	this.spMMoldAt = spMMoldAt;
    }

    public String getSpMMakingAt() {
	return spMMakingAt;
    }

    public void setSpMMakingAt(String spMMakingAt) {
	this.spMMakingAt = spMMakingAt;
    }

    public String getSpMProdAt() {
	return spMProdAt;
    }

    public void setSpMProdAt(String spMProdAt) {
	this.spMProdAt = spMProdAt;
    }

    public String getSpPuchaseGroup() {
	return spPuchaseGroup;
    }

    public void setSpPuchaseGroup(String spPuchaseGroup) {
	this.spPuchaseGroup = spPuchaseGroup;
    }

    public String getSpPlant() {
	return spPlant;
    }

    public void setSpPlant(String spPlant) {
	this.spPlant = spPlant;
    }

    public String getSpDieWhere() {
	return spDieWhere;
    }

    public void setSpDieWhere(String spDieWhere) {
	this.spDieWhere = spDieWhere;
    }

    public String getSpDevManNm() {
	return spDevManNm;
    }

    public void setSpDevManNm(String spDevManNm) {
	this.spDevManNm = spDevManNm;
    }

    public String getSpDesignManNm() {
	return spDesignManNm;
    }

    public void setSpDesignManNm(String spDesignManNm) {
	this.spDesignManNm = spDesignManNm;
    }

    public String getSpManufacManNm() {
	return spManufacManNm;
    }

    public void setSpManufacManNm(String spManufacManNm) {
	this.spManufacManNm = spManufacManNm;
    }

    public String getSpObjectiveCT() {
	return spObjectiveCT;
    }

    public void setSpObjectiveCT(String spObjectiveCT) {
	this.spObjectiveCT = spObjectiveCT;
    }

    public String getSpCavityStd() {
	return spCavityStd;
    }

    public void setSpCavityStd(String spCavityStd) {
	this.spCavityStd = spCavityStd;
    }

    public String getSpIsDeleted() {
	return spIsDeleted;
    }

    public void setSpIsDeleted(String spIsDeleted) {
	this.spIsDeleted = spIsDeleted;
    }

    public String getPartHalb() {
	return partHalb;
    }

    public void setPartHalb(String partHalb) {
	this.partHalb = partHalb;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
