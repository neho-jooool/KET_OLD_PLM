/* bcwti
 *
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package e3ps.ecm.entity;

import java.io.Serializable;


/**
 * 
 * @version 1.0
 **/

public class KETSearchEcoDetailVO extends GeneralVO implements Serializable {


    private static final String RESOURCE  = "e3ps.ecm.entity.entityResource";
    private static final String CLASSNAME = KETSearchEcoDetailVO.class.getName();

    private String              oid;                                              //ECOID
    private String              ecoId;                                            //ECO번호
    private String              ecoName;                                          //ECO제목
    private String              dieNo;                                            //DieNo
    private String              partNumberTrunc;                                  //부품번호(축약)
    private String              partNameTrunc;                                    //부품명(축약)
    private String              partType;                                         //부품유형
    private String              partTypeName;                                     //부품유형명
    private String              partNumber;                                       //부품번호
    private String              partName;                                         //부품명
    private String              partVersion;                                      //부품Version
    private String              epmDocCls;                                        //도면구분
    private String              creatorName;                                      //작성자명
    private String              createDate;                                       //작성일자
    private String              changeReason;                                     //설계변경 사유
    private String              otherChgReason;                                  //기타설계변경 사유
    private String              secureBudgetYn;                                   //예산확보여부
    private String              SancStateFlag;                                    //결재상태구분
    private String              createDeptName;                                   //작성부서명
    private String              approveDate;                                      //승인일자
    private String              prodMoldClsName;                                  //ECO구분명
    private String              projectNo;                                        //프로젝트번호
    private String              projectName;                                      //프로젝트명
    private String              projectOid;                                       //프로젝트oid
    private String              carMakerDomain;                                  //제품 고객사 국내_국외구분
    private String              carMaker;                                        //제품 고객사
    private String              carCategory;                                     //제품 차종
    private String              incProdType;                                     //금형 생산성향상유형
    private String              otherIncProdType;
    private String              vendorFlag;                                      //금형 생산처구분
    private String              prodVendor;                                      //금형 생산처
    private String              customChk;                                       //제품 고객확인구분
    private String              customChkDesc;
    private String              controlFlag;                                     //제품 조정및변경사항
    private String              cuDrawingChk;                                    //제품 CU도면 변경여부
    private String              devFlag;                                         //단계구분
    private String              reqType;                                         //ECR- 의뢰구분
    private String              reqTypeDesc;
    private String              moldReqChgType;                                  //ECR- 금형설변유형
    private String              moldReqChgTypeDesc;
    private String              procType;                                        //ECR-처리구분
    private String              prodTypeDesc;
    private String              completeReqDate;                                 //ECR 완료 요청일

    private String              planDate;                                        //활동계획 일정

    private String              relatedECRStr;
    private String              relatedECOStr;
    private String              divisionFlag;                                    //사업부구분;


    // Die No
    //private String dieNo;
    private String              dieName;
    private String              dieCnt;


    public String getOid() {
	return oid;
    }

    public void setOid(String oid) {
	this.oid = oid;
    }

    public String getEcoId() {
	return ecoId;
    }

    public void setEcoId(String ecoId) {
	this.ecoId = ecoId;
    }

    public String getEcoName() {
	return ecoName;
    }

    public void setEcoName(String ecoName) {
	this.ecoName = ecoName;
    }

    public String getPartNumberTrunc() {
	return partNumberTrunc;
    }

    public void setPartNumberTrunc(String partNumberTrunc) {
	this.partNumberTrunc = partNumberTrunc;
    }

    public String getPartNameTrunc() {
	return partNameTrunc;
    }

    public void setPartNameTrunc(String partNameTrunc) {
	this.partNameTrunc = partNameTrunc;
    }

    public String getPartNumber() {
	return partNumber;
    }

    public void setPartNumber(String partNumber) {
	this.partNumber = partNumber;
    }

    public String getPartName() {
	return partName;
    }

    public void setPartName(String partName) {
	this.partName = partName;
    }

    public String getPartVersion() {
	return partVersion;
    }

    public void setPartVersion(String partVersion) {
	this.partVersion = partVersion;
    }

    public String getCreatorName() {
	return creatorName;
    }

    public void setCreatorName(String creatorName) {
	this.creatorName = creatorName;
    }

    public String getCreateDate() {
	return createDate;
    }

    public void setCreateDate(String createDate) {
	this.createDate = createDate;
    }

    public String getChangeReason() {
	return changeReason;
    }

    public void setChangeReason(String changeReason) {
	this.changeReason = changeReason;
    }

    public String getSecureBudgetYn() {
	return secureBudgetYn;
    }

    public void setSecureBudgetYn(String secureBudgetYn) {
	this.secureBudgetYn = secureBudgetYn;
    }

    public String getSancStateFlag() {
	return SancStateFlag;
    }

    public void setSancStateFlag(String sancStateFlag) {
	SancStateFlag = sancStateFlag;
    }

    public String getDieNo() {
	return dieNo;
    }

    public void setDieNo(String dieNo) {
	this.dieNo = dieNo;
    }

    public String getCreateDeptName() {
	return createDeptName;
    }

    public void setCreateDeptName(String createDeptName) {
	this.createDeptName = createDeptName;
    }

    public String getApproveDate() {
	return approveDate;
    }

    public void setApproveDate(String approveDate) {
	this.approveDate = approveDate;
    }

    public String getProdMoldClsName() {
	return prodMoldClsName;
    }

    public void setProdMoldClsName(String prodMoldClsName) {
	this.prodMoldClsName = prodMoldClsName;
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

    public String getProjectOid() {
	return projectOid;
    }

    public void setProjectOid(String projectOid) {
	this.projectOid = projectOid;
    }

    public String getEpmDocCls() {
	return epmDocCls;
    }

    public void setEpmDocCls(String epmDocCls) {
	this.epmDocCls = epmDocCls;
    }

    public String getPartType() {
	return partType;
    }

    public void setPartType(String partType) {
	this.partType = partType;
    }

    public String getPartTypeName() {
	return partTypeName;
    }

    public void setPartTypeName(String partTypeName) {
	this.partTypeName = partTypeName;
    }

    public String getOtherChgReason() {
	return otherChgReason;
    }

    public void setOtherChgReason(String otherChgReason) {
	this.otherChgReason = otherChgReason;
    }

    public String getCarMakerDomain() {
	return carMakerDomain;
    }

    public void setCarMakerDomain(String carMakerDomain) {
	this.carMakerDomain = carMakerDomain;
    }

    public String getCarMaker() {
	return carMaker;
    }

    public void setCarMaker(String carMaker) {
	this.carMaker = carMaker;
    }

    public String getCarCategory() {
	return carCategory;
    }

    public void setCarCategory(String carCategory) {
	this.carCategory = carCategory;
    }

    public String getIncProdType() {
	return incProdType;
    }

    public void setIncProdType(String incProdType) {
	this.incProdType = incProdType;
    }

    public String getVendorFlag() {
	return vendorFlag;
    }

    public void setVendorFlag(String vendorFlag) {
	this.vendorFlag = vendorFlag;
    }

    public String getProdVendor() {
	return prodVendor;
    }

    public void setProdVendor(String prodVendor) {
	this.prodVendor = prodVendor;
    }

    public String getCustomChk() {
	return customChk;
    }

    public void setCustomChk(String customChk) {
	this.customChk = customChk;
    }

    public String getControlFlag() {
	return controlFlag;
    }

    public void setControlFlag(String controlFlag) {
	this.controlFlag = controlFlag;
    }

    public String getCuDrawingChk() {
	return cuDrawingChk;
    }

    public void setCuDrawingChk(String cuDrawingChk) {
	this.cuDrawingChk = cuDrawingChk;
    }

    public String getDevFlag() {
	return devFlag;
    }

    public void setDevFlag(String devFlag) {
	this.devFlag = devFlag;
    }

    public String getCustomChkDesc() {
	return customChkDesc;
    }

    public void setCustomChkDesc(String customChkDesc) {
	this.customChkDesc = customChkDesc;
    }

    public String getOtherIncProdType() {
	return otherIncProdType;
    }

    public void setOtherIncProdType(String otherIncProdType) {
	this.otherIncProdType = otherIncProdType;
    }

    public String getReqType() {
	return reqType;
    }

    public void setReqType(String reqType) {
	this.reqType = reqType;
    }

    public String getReqTypeDesc() {
	return reqTypeDesc;
    }

    public void setReqTypeDesc(String reqTypeDesc) {
	this.reqTypeDesc = reqTypeDesc;
    }

    public String getMoldReqChgType() {
	return moldReqChgType;
    }

    public void setMoldReqChgType(String moldReqChgType) {
	this.moldReqChgType = moldReqChgType;
    }

    public String getMoldReqChgTypeDesc() {
	return moldReqChgTypeDesc;
    }

    public void setMoldReqChgTypeDesc(String moldReqChgTypeDesc) {
	this.moldReqChgTypeDesc = moldReqChgTypeDesc;
    }

    public String getProcType() {
	return procType;
    }

    public void setProcType(String procType) {
	this.procType = procType;
    }

    public String getProdTypeDesc() {
	return prodTypeDesc;
    }

    public void setProdTypeDesc(String prodTypeDesc) {
	this.prodTypeDesc = prodTypeDesc;
    }

    public String getCompleteReqDate() {
	return completeReqDate;
    }

    public void setCompleteReqDate(String completeReqDate) {
	this.completeReqDate = completeReqDate;
    }

    public String getPlanDate() {
	return planDate;
    }

    public void setPlanDate(String planDate) {
	this.planDate = planDate;
    }

    public String getRelatedECRStr() {
	return relatedECRStr;
    }

    public void setRelatedECRStr(String relatedECRStr) {
	this.relatedECRStr = relatedECRStr;
    }

    public String getRelatedECOStr() {
	return relatedECOStr;
    }

    public void setRelatedECOStr(String relatedECOStr) {
	this.relatedECOStr = relatedECOStr;
    }

    public String getdivisionFlag() {
	return divisionFlag;
    }

    public void setdivisionFlag(String divisionFlag) {
	this.divisionFlag = divisionFlag;
    }


    public String getDieName() {
	return dieName;
    }

    public void setDieName(String dieName) {
	this.dieName = dieName;
    }

    public String getDieCnt() {
	return dieCnt;
    }

    public void setDieCnt(String dieCnt) {
	this.dieCnt = dieCnt;
    }


}
