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
import java.util.Hashtable;
import java.util.Vector;

/**
 * 
 * @version 1.0
 **/

public class KETMoldChangeRequestVO extends GeneralVO implements Serializable {

    private static final String RESOURCE = "e3ps.ecm.entity.entityResource";
    private static final String CLASSNAME = KETMoldChangeRequestVO.class.getName();
    private KETMoldChangeRequest ketMoldChangeRequest;
    private ArrayList<KETMoldECRIssueLinkVO> ketMoldECRIssueLinkVOList;
    private String projectName;
    private String deleteFileList;
    private String deleteRelIssueList;
    private int totalCount;
    private Vector files;

    private ArrayList<KETMoldECOPartLinkVO> ketMoldECOPartLinkVOList;// 관련부품리스트
    private String deleteRelPartList;
    private String oid;// ECO OID
    private String projectNo;
    private String orgName;// 작성부서명
    private String teamName;// 작성팀명
    private String creatorName;// 작성자명
    private String createDate;// 작성일자
    private String updateDate;// 수정일자
    private String approvalName;// 승인자명
    private String approvalDate;// 승인일자
    private String progressState;// 진행상태
    private String progressStateName;// 진행상태명
    private String changeTypeName;// 설계변경유형명
    private String otherChangeType; // 기타 설변유형명
    private String devYnName;// 개발/양산구분명
    private String divisionFlagName;// 사업부구분명
    private String completeReqDateFormat;// 완료요청일(일자포맷팅)
    private String prodVendorName;// 생산처명
    private String processTypeName;// 처리구분명
    private String otherProcessdesc; // 처리구분 Desc
    private String requestTypeName;// 의뢰구분명
    private String otherRequestType; // 기타 의뢰구분명
    private boolean existRelMoldEcoLink;// 관련금형ECO 존재여부

    // 금형 ECR 확장팩
    private Hashtable<String, String> reqData;

    public KETMoldChangeRequest getKetMoldChangeRequest() {
	return ketMoldChangeRequest;
    }

    public void setKetMoldChangeRequest(KETMoldChangeRequest ketMoldChangeRequest) {
	this.ketMoldChangeRequest = ketMoldChangeRequest;
    }

    public ArrayList<KETMoldECRIssueLinkVO> getKetMoldECRIssueLinkVOList() {
	return ketMoldECRIssueLinkVOList;
    }

    public void setKetMoldECRIssueLinkVOList(ArrayList<KETMoldECRIssueLinkVO> ketMoldECRIssueLinkVOList) {
	this.ketMoldECRIssueLinkVOList = ketMoldECRIssueLinkVOList;
    }

    public String getProjectName() {
	return projectName;
    }

    public void setProjectName(String projectName) {
	this.projectName = projectName;
    }

    public String getDeleteFileList() {
	return deleteFileList;
    }

    public void setDeleteFileList(String deleteFileList) {
	this.deleteFileList = deleteFileList;
    }

    public String getDeleteRelIssueList() {
	return deleteRelIssueList;
    }

    public void setDeleteRelIssueList(String deleteRelIssueList) {
	this.deleteRelIssueList = deleteRelIssueList;
    }

    public int getTotalCount() {
	return totalCount;
    }

    public void setTotalCount(int totalCount) {
	this.totalCount = totalCount;
    }

    @SuppressWarnings("rawtypes")
    public Vector getFiles() {
	return files;
    }

    @SuppressWarnings("rawtypes")
    public void setFiles(Vector files) {
	this.files = files;
    }

    public ArrayList<KETMoldECOPartLinkVO> getKetMoldECOPartLinkVOList() {
	return ketMoldECOPartLinkVOList;
    }

    public void setKetMoldECOPartLinkVOList(ArrayList<KETMoldECOPartLinkVO> ketMoldECOPartLinkVOList) {
	this.ketMoldECOPartLinkVOList = ketMoldECOPartLinkVOList;
    }

    public String getDeleteRelPartList() {
	return deleteRelPartList;
    }

    public void setDeleteRelPartList(String deleteRelPartList) {
	this.deleteRelPartList = deleteRelPartList;
    }

    public String getOid() {
	return oid;
    }

    public void setOid(String oid) {
	this.oid = oid;
    }

    public String getOrgName() {
	return orgName;
    }

    public void setOrgName(String orgName) {
	this.orgName = orgName;
    }

    public String getTeamName() {
	return teamName;
    }

    public void setTeamName(String teamName) {
	this.teamName = teamName;
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

    public String getUpdateDate() {
	return updateDate;
    }

    public void setUpdateDate(String updateDate) {
	this.updateDate = updateDate;
    }

    public String getApprovalName() {
	return approvalName;
    }

    public void setApprovalName(String approvalName) {
	this.approvalName = approvalName;
    }

    public String getApprovalDate() {
	return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
	this.approvalDate = approvalDate;
    }

    public String getProgressState() {
	return progressState;
    }

    public void setProgressState(String progressState) {
	this.progressState = progressState;
    }

    public String getChangeTypeName() {
	return changeTypeName;
    }

    public void setChangeTypeName(String changeTypeName) {
	this.changeTypeName = changeTypeName;
    }

    public String getDevYnName() {
	return devYnName;
    }

    public void setDevYnName(String devYnName) {
	this.devYnName = devYnName;
    }

    public String getDivisionFlagName() {
	return divisionFlagName;
    }

    public void setDivisionFlagName(String divisionFlagName) {
	this.divisionFlagName = divisionFlagName;
    }

    public String getProgressStateName() {
	return progressStateName;
    }

    public void setProgressStateName(String progressStateName) {
	this.progressStateName = progressStateName;
    }

    public String getCompleteReqDateFormat() {
	return completeReqDateFormat;
    }

    public void setCompleteReqDateFormat(String completeReqDateFormat) {
	this.completeReqDateFormat = completeReqDateFormat;
    }

    public String getProdVendorName() {
	return prodVendorName;
    }

    public void setProdVendorName(String prodVendorName) {
	this.prodVendorName = prodVendorName;
    }

    public String getProcessTypeName() {
	return processTypeName;
    }

    public void setProcessTypeName(String processTypeName) {
	this.processTypeName = processTypeName;
    }

    public String getRequestTypeName() {
	return requestTypeName;
    }

    public void setRequestTypeName(String requestTypeName) {
	this.requestTypeName = requestTypeName;
    }

    public String getProjectNo() {
	return projectNo;
    }

    public void setProjectNo(String projectNo) {
	this.projectNo = projectNo;
    }

    public boolean isExistRelMoldEcoLink() {
	return existRelMoldEcoLink;
    }

    public void setExistRelMoldEcoLink(boolean existRelMoldEcoLink) {
	this.existRelMoldEcoLink = existRelMoldEcoLink;
    }

    public String getOtherProcessdesc() {
	return otherProcessdesc;
    }

    public void setOtherProcessdesc(String otherProcessdesc) {
	this.otherProcessdesc = otherProcessdesc;
    }

    public String getOtherChangeType() {
	return otherChangeType;
    }

    public void setOtherChangeType(String otherChangeType) {
	this.otherChangeType = otherChangeType;
    }

    public String getOtherRequestType() {
	return otherRequestType;
    }

    public void setOtherRequestType(String otherRequestType) {
	this.otherRequestType = otherRequestType;
    }

    public Hashtable<String, String> getReqData() {
	return reqData;
    }

    public void setReqData(Hashtable<String, String> reqData) {
	this.reqData = reqData;
    }

}
