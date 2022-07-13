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
import java.util.ArrayList;
import java.util.Hashtable;  // Preserved unmodeled dependency
import java.util.Vector;

/**
 * 
 * @version 1.0
 **/

public class KETMoldChangeOrderVO extends GeneralVO implements Serializable {


    private static final String                  RESOURCE             = "e3ps.ecm.entity.entityResource";
    private static final String                  CLASSNAME            = KETMoldChangeOrderVO.class.getName();
    private KETMoldChangeOrder                   ketMoldChangeOrder;
    private ArrayList<KETMoldChangeRequest>      ketMoldChangeRequestList;
    private ArrayList<KETProdChangeOrder>        ketProdChangeOrderList;
    private ArrayList<KETMoldECOPartLinkVO>      ketMoldECOPartLinkVOList;
    private ArrayList<KETMoldChangeActivityVO>   ketMoldChangeActivityVOList;
    private ArrayList<KETMoldECAEpmDocLink>      ketMoldECAEpmDocLink;
    private ArrayList<KETMoldECADocLink>         ketMoldECADocLink;
    private ArrayList<KETMoldECABomLink>         ketMoldECABomLink;
    private String                               projectName;
    private String                               prodVendorName;
    private String                               deleteFileList;
    private String                               deleteRelEcrList;
    private String                               deleteRelProdEcoList;
    private String                               deleteRelPartList;
    private String                               deleteRelBomList;
    private String                               deleteRelEpmList;
    private String                               deleteRelDocList;
    private int                                  totalCount;
    private Vector                               files;

    private String                               isToDO;
    private String                               isCompleteModify;
    private String                               oid;                                                        //ECO OID
    private String                               orgName;                                                    //작성부서명
    private String                               teamName;                                                   //작성팀명
    private String                               creatorName;                                                //작성자명
    private String                               createDate;                                                 //작성일자
    private String                               updateDate;                                                 //수정일자
    private String                               approvalName;                                               //승인자명
    private String                               approvalDate;                                               //승인일자
    private String                               progressState;                                              //진행상태
    private String                               progressStateName;                                          //진행상태명
    private String                               changeReasonName;                                           //설계변경사유
    private String                               increaseProdTypeName;                                       //생산성향상유형
    private String                               oldEcoWorkerId;                                             //기존ECO담당자id
    private String                               ecoWorkerName;                                              //ECO담당자명
    private String                               devYnName;                                                  //개발/양산구분명
    private String                               divisionFlagName;                                           //사업부구분명
    private String                               mold_oid;                                                   //양산금형 link oid

    private ArrayList<KETMoldECALinkVO>          ketMoldECALinkVOList;                                       //활동계획(도면/BOM/표준품) 정보
    private ArrayList<KETMoldEcoEcrLinkVO>       ketMoldEcoEcrLinkVOList;                                    //연계ECR정보
    private ArrayList<KETMoldEcoEcrLinkVO>       ketMoldEcoProdEcoLinkVOList;                                //연계 제품ECO정보
    @SuppressWarnings("rawtypes")
    private ArrayList                            deleteRelEcaList;
    @SuppressWarnings("rawtypes")
    private ArrayList                            deleteMoldPlanList;
    private String                               loginUserOid;                                               //로그인담당자oid
    private String                               ecaType;                                                    //(1:도면, 2:BOM, 3:표준품)

    private ArrayList<Hashtable<String, String>> stdPartList;                                                //표준품List
    private String                               projectNo;


    // 프로젝트에서 산출물로 ECO 직접작성
    // reqData.put("projectOid", paramMap.getString("projectOid")); // 프로젝트 OID
    private String                               projectOutputOid     = null;                                // 프로젝트 - 산출물 관리 OID


    // SAP I/F
    private boolean                              isSucessSapInterface = false;

    public String getApprovalDate() {
	return approvalDate;
    }

    public String getApprovalName() {
	return approvalName;
    }


    public String getChangeReasonName() {
	return changeReasonName;
    }

    public String getCreateDate() {
	return createDate;
    }

    public String getCreatorName() {
	return creatorName;
    }

    public String getDeleteFileList() {
	return deleteFileList;
    }

    @SuppressWarnings("rawtypes")
    public ArrayList getDeleteMoldPlanList() {
	return deleteMoldPlanList;
    }

    public String getDeleteRelBomList() {
	return deleteRelBomList;
    }

    public String getDeleteRelDocList() {
	return deleteRelDocList;
    }

    @SuppressWarnings("rawtypes")
    public ArrayList getDeleteRelEcaList() {
	return deleteRelEcaList;
    }

    public String getDeleteRelEcrList() {
	return deleteRelEcrList;
    }

    public String getDeleteRelEpmList() {
	return deleteRelEpmList;
    }

    public String getDeleteRelPartList() {
	return deleteRelPartList;
    }

    public String getDeleteRelProdEcoList() {
	return deleteRelProdEcoList;
    }

    public String getDevYnName() {
	return devYnName;
    }

    public String getDivisionFlagName() {
	return divisionFlagName;
    }

    public String getEcaType() {
	return ecaType;
    }

    public String getEcoWorkerName() {
	return ecoWorkerName;
    }

    @SuppressWarnings("rawtypes")
    public Vector getFiles() {
	return files;
    }

    public String getIncreaseProdTypeName() {
	return increaseProdTypeName;
    }

    public String getIsCompleteModify() {
	return isCompleteModify;
    }

    public String getIsToDO() {
	return isToDO;
    }

    public ArrayList<KETMoldChangeActivityVO> getKetMoldChangeActivityVOList() {
	return ketMoldChangeActivityVOList;
    }

    public KETMoldChangeOrder getKetMoldChangeOrder() {
	return ketMoldChangeOrder;
    }

    public ArrayList<KETMoldChangeRequest> getKetMoldChangeRequestList() {
	return ketMoldChangeRequestList;
    }

    public ArrayList<KETMoldECABomLink> getKetMoldECABomLink() {
	return ketMoldECABomLink;
    }

    public ArrayList<KETMoldECADocLink> getKetMoldECADocLink() {
	return ketMoldECADocLink;
    }

    public ArrayList<KETMoldECAEpmDocLink> getKetMoldECAEpmDocLink() {
	return ketMoldECAEpmDocLink;
    }

    public ArrayList<KETMoldECALinkVO> getKetMoldECALinkVOList() {
	return ketMoldECALinkVOList;
    }

    public ArrayList<KETMoldEcoEcrLinkVO> getKetMoldEcoEcrLinkVOList() {
	return ketMoldEcoEcrLinkVOList;
    }

    public ArrayList<KETMoldECOPartLinkVO> getKetMoldECOPartLinkVOList() {
	return ketMoldECOPartLinkVOList;
    }

    public ArrayList<KETMoldEcoEcrLinkVO> getKetMoldEcoProdEcoLinkVOList() {
	return ketMoldEcoProdEcoLinkVOList;
    }

    public ArrayList<KETProdChangeOrder> getKetProdChangeOrderList() {
	return ketProdChangeOrderList;
    }

    public String getLoginUserOid() {
	return loginUserOid;
    }

    public String getMoldOid() {
	return mold_oid;
    }

    public String getOid() {
	return oid;
    }

    public String getOldEcoWorkerId() {
	return oldEcoWorkerId;
    }

    public String getOrgName() {
	return orgName;
    }

    public String getProdVendorName() {
	return prodVendorName;
    }

    public String getProgressState() {
	return progressState;
    }

    public String getProgressStateName() {
	return progressStateName;
    }

    public String getProjectName() {
	return projectName;
    }

    public String getProjectNo() {
	return projectNo;
    }

    public String getProjectOutputOid() {
	return projectOutputOid;
    }

    public ArrayList<Hashtable<String, String>> getStdPartList() {
	return stdPartList;
    }

    public String getTeamName() {
	return teamName;
    }

    public int getTotalCount() {
	return totalCount;
    }

    public String getUpdateDate() {
	return updateDate;
    }

    public boolean isSucessSapInterface() {
	return isSucessSapInterface;
    }

    public void setApprovalDate(String approvalDate) {
	this.approvalDate = approvalDate;
    }

    public void setApprovalName(String approvalName) {
	this.approvalName = approvalName;
    }

    public void setChangeReasonName(String changeReasonName) {
	this.changeReasonName = changeReasonName;
    }

    public void setCreateDate(String createDate) {
	this.createDate = createDate;
    }

    public void setCreatorName(String creatorName) {
	this.creatorName = creatorName;
    }

    public void setDeleteFileList(String deleteFileList) {
	this.deleteFileList = deleteFileList;
    }

    @SuppressWarnings("rawtypes")
    public void setDeleteMoldPlanList(ArrayList deleteMoldPlanList) {
	this.deleteMoldPlanList = deleteMoldPlanList;
    }

    public void setDeleteRelBomList(String deleteRelBomList) {
	this.deleteRelBomList = deleteRelBomList;
    }

    public void setDeleteRelDocList(String deleteRelDocList) {
	this.deleteRelDocList = deleteRelDocList;
    }

    @SuppressWarnings("rawtypes")
    public void setDeleteRelEcaList(ArrayList deleteRelEcaList) {
	this.deleteRelEcaList = deleteRelEcaList;
    }

    public void setDeleteRelEcrList(String deleteRelEcrList) {
	this.deleteRelEcrList = deleteRelEcrList;
    }

    public void setDeleteRelEpmList(String deleteRelEpmList) {
	this.deleteRelEpmList = deleteRelEpmList;
    }

    public void setDeleteRelPartList(String deleteRelPartList) {
	this.deleteRelPartList = deleteRelPartList;
    }

    public void setDeleteRelProdEcoList(String deleteRelProdEcoList) {
	this.deleteRelProdEcoList = deleteRelProdEcoList;
    }

    public void setDevYnName(String devYnName) {
	this.devYnName = devYnName;
    }

    public void setDivisionFlagName(String divisionFlagName) {
	this.divisionFlagName = divisionFlagName;
    }

    public void setEcaType(String ecaType) {
	this.ecaType = ecaType;
    }

    public void setEcoWorkerName(String ecoWorkerName) {
	this.ecoWorkerName = ecoWorkerName;
    }

    @SuppressWarnings("rawtypes")
    public void setFiles(Vector files) {
	this.files = files;
    }

    public void setIncreaseProdTypeName(String increaseProdTypeName) {
	this.increaseProdTypeName = increaseProdTypeName;
    }

    public void setIsCompleteModify(String isCompleteModify) {
	this.isCompleteModify = isCompleteModify;
    }

    public void setIsToDO(String isToDO) {
	this.isToDO = isToDO;
    }

    public void setKetMoldChangeActivityVOList(ArrayList<KETMoldChangeActivityVO> ketMoldChangeActivityVOList) {
	this.ketMoldChangeActivityVOList = ketMoldChangeActivityVOList;
    }

    public void setKetMoldChangeOrder(KETMoldChangeOrder ketMoldChangeOrder) {
	this.ketMoldChangeOrder = ketMoldChangeOrder;
    }

    public void setKetMoldChangeRequestList(ArrayList<KETMoldChangeRequest> ketMoldChangeRequestList) {
	this.ketMoldChangeRequestList = ketMoldChangeRequestList;
    }

    public void setKetMoldECABomLink(ArrayList<KETMoldECABomLink> ketMoldECABomLink) {
	this.ketMoldECABomLink = ketMoldECABomLink;
    }

    public void setKetMoldECADocLink(ArrayList<KETMoldECADocLink> ketMoldECADocLink) {
	this.ketMoldECADocLink = ketMoldECADocLink;
    }

    public void setKetMoldECAEpmDocLink(ArrayList<KETMoldECAEpmDocLink> ketMoldECAEpmDocLink) {
	this.ketMoldECAEpmDocLink = ketMoldECAEpmDocLink;
    }

    public void setKetMoldECALinkVOList(ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList) {
	this.ketMoldECALinkVOList = ketMoldECALinkVOList;
    }

    public void setKetMoldEcoEcrLinkVOList(ArrayList<KETMoldEcoEcrLinkVO> ketMoldEcoEcrLinkVOList) {
	this.ketMoldEcoEcrLinkVOList = ketMoldEcoEcrLinkVOList;
    }

    public void setKetMoldECOPartLinkVOList(ArrayList<KETMoldECOPartLinkVO> ketMoldECOPartLinkVOList) {
	this.ketMoldECOPartLinkVOList = ketMoldECOPartLinkVOList;
    }

    public void setKetMoldEcoProdEcoLinkVOList(ArrayList<KETMoldEcoEcrLinkVO> ketMoldEcoProdEcoLinkVOList) {
	this.ketMoldEcoProdEcoLinkVOList = ketMoldEcoProdEcoLinkVOList;
    }

    public void setKetProdChangeOrderList(ArrayList<KETProdChangeOrder> ketProdChangeOrderList) {
	this.ketProdChangeOrderList = ketProdChangeOrderList;
    }

    public void setLoginUserOid(String loginUserOid) {
	this.loginUserOid = loginUserOid;
    }

    public void setMoldOid(String mold_oid) {
	this.mold_oid = mold_oid;
    }

    public void setOid(String oid) {
	this.oid = oid;
    }

    public void setOldEcoWorkerId(String oldEcoWorkerId) {
	this.oldEcoWorkerId = oldEcoWorkerId;
    }

    public void setOrgName(String orgName) {
	this.orgName = orgName;
    }

    public void setProdVendorName(String prodVendorName) {
	this.prodVendorName = prodVendorName;
    }

    public void setProgressState(String progressState) {
	this.progressState = progressState;
    }

    public void setProgressStateName(String progressStateName) {
	this.progressStateName = progressStateName;
    }

    public void setProjectName(String projectName) {
	this.projectName = projectName;
    }

    public void setProjectNo(String projectNo) {
	this.projectNo = projectNo;
    }

    public void setProjectOutputOid(String projectOutputOid) {
	this.projectOutputOid = projectOutputOid;
    }

    public void setStdPartList(ArrayList<Hashtable<String, String>> stdPartList) {
	this.stdPartList = stdPartList;
    }

    public void setSucessSapInterface(boolean isSucessSapInterface) {
	this.isSucessSapInterface = isSucessSapInterface;
    }

    public void setTeamName(String teamName) {
	this.teamName = teamName;
    }

    public void setTotalCount(int totalCount) {
	this.totalCount = totalCount;
    }

    public void setUpdateDate(String updateDate) {
	this.updateDate = updateDate;
    }

}
