package ext.ket.project.task.entity;

import e3ps.common.util.CommonUtil;
import e3ps.project.E3PSTask;
import ext.ket.shared.dto.BaseDTO;

public class GateTaskOutputDTO extends BaseDTO {
    private String outputName = "";
    private String chargeName = "";
    private String outputCheck = "";
    private String outputComment = "";
    private String outputLevel = "";
    private String outputTaskType = "";
    private String outputTaskName = "";
    private String outputTaskCategory = "";
    private String outputOid = "";
    private String outputState = "";
    private String taskOid = "";
    private String taskStageName = "";
    private String objectOid = "";

    // 평가결과를 담기위한 변수
    private String resultOutputVal = "";
    private String resultAssVal = "";
    private String resultDqmVal = "";
    private String resultTotalVal = "";
    private String resultTotalStr = "";
    private String resultAttatchCnt = "";
    private String resultOid = "";
    private String resultPlanVal = "";
    private String resultExecVal = "";
    private String resultStateVal = "";
    private String resultTotalResult = "";

    // Gate 결과에 링크된 객체OID(3개)
    private String outputLinkOid = "";
    private String gateLinkOid = "";
    private String dqmLinkOid = "";
    
    //평가결과 차수
    private String rev = "";
    
    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public GateTaskOutputDTO() {
    }

    public String getTaskOid() {
	return taskOid;
    }

    public void setTaskOid(String taskOid) {
	this.taskOid = taskOid;
    }

    public String getOutputTaskName() {
	return outputTaskName;
    }

    public void setOutputTaskName(String outputTaskName) {
	this.outputTaskName = outputTaskName;
    }

    public GateTaskOutputDTO(E3PSTask task) {
	this.setOid(CommonUtil.getOIDString(task));

    }

    public String getOutputName() {
	return outputName;
    }

    public void setOutputName(String outputName) {
	this.outputName = outputName;
    }

    public String getChargeName() {
	return chargeName;
    }

    public void setChargeName(String chargeName) {
	this.chargeName = chargeName;
    }

    public String getOutputCheck() {
	return outputCheck;
    }

    public void setOutputCheck(String outputCheck) {
	this.outputCheck = outputCheck;
    }

    public String getOutputComment() {
	return outputComment;
    }

    public void setOutputComment(String outputComment) {
	this.outputComment = outputComment;
    }

    public String getOutputLevel() {
	return outputLevel;
    }

    public void setOutputLevel(String outputLevel) {
	this.outputLevel = outputLevel;
    }

    public String getOutputTaskType() {
	return outputTaskType;
    }

    public void setOutputTaskType(String outputTaskType) {
	this.outputTaskType = outputTaskType;
    }

    public String getOutputOid() {
	return outputOid;
    }

    public void setOutputOid(String outputOid) {
	this.outputOid = outputOid;
    }

    public String getOutputTaskCategory() {
	return outputTaskCategory;
    }

    public void setOutputTaskCategory(String outputTaskCategory) {
	this.outputTaskCategory = outputTaskCategory;
    }

    public String getTaskStageName() {
	return taskStageName;
    }

    public void setTaskStageName(String taskStageName) {
	this.taskStageName = taskStageName;
    }

    public String getResultOutputVal() {
	return resultOutputVal;
    }

    public void setResultOutputVal(String resultOutputVal) {
	this.resultOutputVal = resultOutputVal;
    }

    public String getResultAssVal() {
	return resultAssVal;
    }

    public void setResultAssVal(String resultAssVal) {
	this.resultAssVal = resultAssVal;
    }

    public String getResultDqmVal() {
	return resultDqmVal;
    }

    public void setResultDqmVal(String resultDqmVal) {
	this.resultDqmVal = resultDqmVal;
    }

    public String getResultTotalVal() {
	return resultTotalVal;
    }

    public void setResultTotalVal(String resultTotalVal) {
	this.resultTotalVal = resultTotalVal;
    }

    public String getResultTotalStr() {
	return resultTotalStr;
    }

    public void setResultTotalStr(String resultTotalStr) {
	this.resultTotalStr = resultTotalStr;
    }

    public String getResultAttatchCnt() {
	return resultAttatchCnt;
    }

    public void setResultAttatchCnt(String resultAttatchCnt) {
	this.resultAttatchCnt = resultAttatchCnt;
    }

    public String getResultOid() {
	return resultOid;
    }

    public void setResultOid(String resultOid) {
	this.resultOid = resultOid;
    }

    public String getOutputState() {
	return outputState;
    }

    public void setOutputState(String outputState) {
	this.outputState = outputState;
    }

    public String getObjectOid() {
	return objectOid;
    }

    public void setObjectOid(String objectOid) {
	this.objectOid = objectOid;
    }

    public String getResultPlanVal() {
	return resultPlanVal;
    }

    public void setResultPlanVal(String resultPlanVal) {
	this.resultPlanVal = resultPlanVal;
    }

    public String getResultExecVal() {
	return resultExecVal;
    }

    public void setResultExecVal(String resultExecVal) {
	this.resultExecVal = resultExecVal;
    }

    public String getResultStateVal() {
	return resultStateVal;
    }

    public void setResultStateVal(String resultStateVal) {
	this.resultStateVal = resultStateVal;
    }

    public String getResultTotalResult() {
	return resultTotalResult;
    }

    public void setResultTotalResult(String resultTotalResult) {
	this.resultTotalResult = resultTotalResult;
    }

    public String getOutputLinkOid() {
	return outputLinkOid;
    }

    public void setOutputLinkOid(String outputLinkOid) {
	this.outputLinkOid = outputLinkOid;
    }

    public String getGateLinkOid() {
	return gateLinkOid;
    }

    public void setGateLinkOid(String gateLinkOid) {
	this.gateLinkOid = gateLinkOid;
    }

    public String getDqmLinkOid() {
	return dqmLinkOid;
    }

    public void setDqmLinkOid(String dqmLinkOid) {
	this.dqmLinkOid = dqmLinkOid;
    }

}
