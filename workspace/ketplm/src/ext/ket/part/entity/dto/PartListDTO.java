package ext.ket.part.entity.dto;

import java.util.ArrayList;
import java.util.List;

import ext.ket.shared.dto.BaseDTO;

final public class PartListDTO extends BaseDTO {

    private String partOid;
    // 기구 | 기구 + 전자소자 구분 값
    private String partListType;

    // 전산품번 [ WTPartMaster
    private String partNo;
    // 품명 [ WTPartMaster
    private String partName;
    // 리비전
    private String partRev;
    private String partKetRev;

    private int maxLevel = 0;

    // Project No [ Project
    private String projectNo;
    // Project Name [ Project
    private String projectName;
    // 제품구분 [ Claz
    private String partClazNameKr;
    // 적용 차종 [ 연계프로젝트 정보
    private String projectApplyCarType;
    // SOP [ 연계프로젝트 차종일정 - 적용차종에 1개
    private String projectSOP;
    // 개발 담당자 [ CFT 1명
    private String projectDevOwner;
    // 커넥터 개발 1팀 [ CFT의 부서명
    private String projectDevDept;
    // 개발 HW 담당자 [ CFT 1명
    private String projectDevHwOwner;
    // 커넥터 개발 1팀 [ CFT의 HW 부서명
    private String projectDevHwDept;

    private String projectExpect1Qty;
    private String projectExpect2Qty;
    private String projectExpect3Qty;
    private String projectExpect4Qty;
    private String projectExpect5Qty;
    private String projectExpect6Qty;
    private String projectExpectSumQty;

    private List<PartListItemDTO> itemList = new ArrayList<PartListItemDTO>();

    public String getPartOid() {
	return partOid;
    }

    public void setPartOid(String partOid) {
	this.partOid = partOid;
    }

    public String getProjectNo() {
	return projectNo;
    }

    public void setProjectNo(String projectNo) {
	this.projectNo = projectNo;
    }

    public String getProjectName() {
	return projectName;
    }

    public void setProjectName(String projectName) {
	this.projectName = projectName;
    }

    public String getPartClazNameKr() {
	return partClazNameKr;
    }

    public void setPartClazNameKr(String partClazNameKr) {
	this.partClazNameKr = partClazNameKr;
    }

    public String getProjectApplyCarType() {
	return projectApplyCarType;
    }

    public void setProjectApplyCarType(String projectApplyCarType) {
	this.projectApplyCarType = projectApplyCarType;
    }

    public String getProjectSOP() {
	return projectSOP;
    }

    public void setProjectSOP(String projectSOP) {
	this.projectSOP = projectSOP;
    }

    public String getProjectDevOwner() {
	return projectDevOwner;
    }

    public void setProjectDevOwner(String projectDevOwner) {
	this.projectDevOwner = projectDevOwner;
    }

    public String getProjectDevDept() {
	return projectDevDept;
    }

    public void setProjectDevDept(String projectDevDept) {
	this.projectDevDept = projectDevDept;
    }

    public List<PartListItemDTO> getItemList() {
	return itemList;
    }

    public void setItemList(List<PartListItemDTO> itemList) {
	this.itemList = itemList;
    }

    public String getPartNo() {
	return partNo;
    }

    public void setPartNo(String partNo) {
	this.partNo = partNo;
    }

    public String getPartName() {
	return partName;
    }

    public void setPartName(String partName) {
	this.partName = partName;
    }

    public String getPartRev() {
	return partRev;
    }

    public void setPartRev(String partRev) {
	this.partRev = partRev;
    }

    public String getPartKetRev() {
	return partKetRev;
    }

    public void setPartKetRev(String partKetRev) {
	this.partKetRev = partKetRev;
    }

    public int getMaxLevel() {
	return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
	this.maxLevel = maxLevel;
    }

    public String getPartListType() {
	return partListType;
    }

    public void setPartListType(String partListType) {
	this.partListType = partListType;
    }

    public String getProjectDevHwOwner() {
	return projectDevHwOwner;
    }

    public void setProjectDevHwOwner(String projectDevHwOwner) {
	this.projectDevHwOwner = projectDevHwOwner;
    }

    public String getProjectDevHwDept() {
	return projectDevHwDept;
    }

    public void setProjectDevHwDept(String projectDevHwDept) {
	this.projectDevHwDept = projectDevHwDept;
    }

    public String getProjectExpect1Qty() {
	return projectExpect1Qty;
    }

    public void setProjectExpect1Qty(String projectExpect1Qty) {
	this.projectExpect1Qty = projectExpect1Qty;
    }

    public String getProjectExpect2Qty() {
	return projectExpect2Qty;
    }

    public void setProjectExpect2Qty(String projectExpect2Qty) {
	this.projectExpect2Qty = projectExpect2Qty;
    }

    public String getProjectExpect3Qty() {
	return projectExpect3Qty;
    }

    public void setProjectExpect3Qty(String projectExpect3Qty) {
	this.projectExpect3Qty = projectExpect3Qty;
    }

    public String getProjectExpect4Qty() {
	return projectExpect4Qty;
    }

    public void setProjectExpect4Qty(String projectExpect4Qty) {
	this.projectExpect4Qty = projectExpect4Qty;
    }

    public String getProjectExpect5Qty() {
	return projectExpect5Qty;
    }

    public void setProjectExpect5Qty(String projectExpect5Qty) {
	this.projectExpect5Qty = projectExpect5Qty;
    }

    public String getProjectExpect6Qty() {
	return projectExpect6Qty;
    }

    public void setProjectExpect6Qty(String projectExpect6Qty) {
	this.projectExpect6Qty = projectExpect6Qty;
    }

    public String getProjectExpectSumQty() {
	return projectExpectSumQty;
    }

    public void setProjectExpectSumQty(String projectExpectSumQty) {
	this.projectExpectSumQty = projectExpectSumQty;
    }

}
