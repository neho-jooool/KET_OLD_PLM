package ext.ket.project.task.entity;

import ext.ket.shared.dto.BaseDTO;

public class TrustTaskResultDTO extends BaseDTO {
    private String taskOid = "";
    private String trustOid = "";
    private String outputOid = "";
    private String resultOid = "";
    private String docLinkOid = "";
    private String outputName = "";
    private String rev = "";
    private String assResult = "";
    private String estimateDate = "";
    private String totCnt = "";
    private String okCnt = "";
    private String ngCnt = "";
    private String ngReason = "";
    private String objName;
    private String objType;
    private String lastModifier;
    private String objVer;
    private String objStatus;
    private String objCreateDate;
    private String objFileIcon;
    private String progressRatio;
    private String gateTarget;
    private String gateTemplate;
    private String isPrimary;
    private String rowSpanCnt;
    private String desc;
    private String docClassName;

    public String getRowSpanCnt() {
	return rowSpanCnt;
    }

    public void setRowSpanCnt(String rowSpanCnt) {
	this.rowSpanCnt = rowSpanCnt;
    }

    public String getIsPrimary() {
	return isPrimary;
    }

    public void setIsPrimary(String isPrimary) {
	this.isPrimary = isPrimary;
    }

    public TrustTaskResultDTO() {
    }

    public String getTaskOid() {
	return taskOid;
    }

    public void setTaskOid(String taskOid) {
	this.taskOid = taskOid;
    }

    public String getTrustOid() {
	return trustOid;
    }

    public void setTrustOid(String trustOid) {
	this.trustOid = trustOid;
    }

    public String getOutputOid() {
	return outputOid;
    }

    public void setOutputOid(String outputOid) {
	this.outputOid = outputOid;
    }

    public String getRev() {
	return rev;
    }

    public void setRev(String rev) {
	this.rev = rev;
    }

    public String getAssResult() {
	return assResult;
    }

    public void setAssResult(String assResult) {
	this.assResult = assResult;
    }

    public String getEstimateDate() {
	return estimateDate;
    }

    public void setEstimateDate(String estimateDate) {
	this.estimateDate = estimateDate;
    }

    public String getTotCnt() {
	return totCnt;
    }

    public void setTotCnt(String totCnt) {
	this.totCnt = totCnt;
    }

    public String getOkCnt() {
	return okCnt;
    }

    public void setOkCnt(String okCnt) {
	this.okCnt = okCnt;
    }

    public String getNgCnt() {
	return ngCnt;
    }

    public void setNgCnt(String ngCnt) {
	this.ngCnt = ngCnt;
    }

    public String getNgReason() {
	return ngReason;
    }

    public void setNgReason(String ngReason) {
	this.ngReason = ngReason;
    }

    public String getObjName() {
	return objName;
    }

    public void setObjName(String objName) {
	this.objName = objName;
    }

    public String getObjType() {
	return objType;
    }

    public void setObjType(String objType) {
	this.objType = objType;
    }

    public String getLastModifier() {
	return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
	this.lastModifier = lastModifier;
    }

    public String getObjVer() {
	return objVer;
    }

    public void setObjVer(String objVer) {
	this.objVer = objVer;
    }

    public String getObjStatus() {
	return objStatus;
    }

    public void setObjStatus(String objStatus) {
	this.objStatus = objStatus;
    }

    public String getObjCreateDate() {
	return objCreateDate;
    }

    public void setObjCreateDate(String objCreateDate) {
	this.objCreateDate = objCreateDate;
    }

    public String getObjFileIcon() {
	return objFileIcon;
    }

    public void setObjFileIcon(String objFileIcon) {
	this.objFileIcon = objFileIcon;
    }

    public String getProgressRatio() {
	return progressRatio;
    }

    public void setProgressRatio(String progressRatio) {
	this.progressRatio = progressRatio;
    }

    public String getGateTarget() {
	return gateTarget;
    }

    public void setGateTarget(String gateTarget) {
	this.gateTarget = gateTarget;
    }

    public String getGateTemplate() {
	return gateTemplate;
    }

    public void setGateTemplate(String gateTemplate) {
	this.gateTemplate = gateTemplate;
    }

    public String getDocLinkOid() {
	return docLinkOid;
    }

    public void setDocLinkOid(String docLinkOid) {
	this.docLinkOid = docLinkOid;
    }

    public String getOutputName() {
	return outputName;
    }

    public void setOutputName(String outputName) {
	this.outputName = outputName;
    }

    public String getResultOid() {
	return resultOid;
    }

    public void setResultOid(String resultOid) {
	this.resultOid = resultOid;
    }

    public String getDesc() {
	return desc;
    }

    public void setDesc(String desc) {
	this.desc = desc;
    }

    public String getDocClassName() {
	return docClassName;
    }

    public void setDocClassName(String docClassName) {
	this.docClassName = docClassName;
    }

}
