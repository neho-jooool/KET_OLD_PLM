package ext.ket.project.task.entity;

import e3ps.common.util.CommonUtil;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import ext.ket.shared.dto.BaseDTO;

public class ProjectTaskDTO extends BaseDTO {
    private String planStartDate = "";
    private String planEndDate = "";
    private String execStartDate = "";
    private String execEndDate = "";
    private String planWorkTime = "";
    private String execWorkTime = "";
    private String delayReason = "";
    private String delayType = "";
    private String compReason = "";
    private String pjtOid;
    private String managerDeptNames;
    private String[] tgTaskOids;
    private String[] tgOutputOids;
    private String[] tgTaskChecks;
    private String[] tgTaskComments;
    private String[] tgGateResults;
    private String[] tgGateCheckOids;
    private String[] tgGateRsltCodes;
    private String[] tgGateChecks;
    private String[] tgDqmHeaderOids;
    private String[] tgDqmHeaderChecks;
    private String[] estimateDates;
    private String[] okCnts;
    private String[] ngCnts;
    private String[] ngReasons;
    private String[] trustOids;
    private String[] trustOidChks;
    private String[] managerDeptOids;

    private int rev;

    public ProjectTaskDTO() {
    }

    public ProjectTaskDTO(E3PSTask task) {
	this.setOid(CommonUtil.getOIDString(task));
	ExtendScheduleData schData = (ExtendScheduleData) task.getTaskSchedule().getObject();
	this.planStartDate = "" + schData.getPlanStartDate();
	this.planEndDate = "" + schData.getPlanEndDate();
	this.execStartDate = "" + schData.getExecStartDate();
	this.execEndDate = "" + schData.getExecEndDate();
	this.planWorkTime = "" + task.getPlanWorkTime();
	this.execWorkTime = "" + task.getExecWorkTime();
	this.delayReason = "" + task.getDelayReason();
	this.delayType = "" + task.getDelayType();
	this.compReason = "" + task.getCompReason();
    }

    public int getRev() {
	return rev;
    }

    public void setRev(int rev) {
	this.rev = rev;
    }

    public String getCompReason() {
	return compReason;
    }

    public void setCompReason(String compReason) {
	this.compReason = compReason;
    }

    public String[] getTrustOids() {
	return trustOids;
    }

    public void setTrustOids(String[] trustOids) {
	this.trustOids = trustOids;
    }

    public String[] getTrustOidChks() {
	return trustOidChks;
    }

    public void setTrustOidChks(String[] trustOidChks) {
	this.trustOidChks = trustOidChks;
    }

    public String[] getEstimateDates() {
	return estimateDates;
    }

    public void setEstimateDates(String[] estimateDates) {
	this.estimateDates = estimateDates;
    }

    public String[] getOkCnts() {
	return okCnts;
    }

    public void setOkCnts(String[] okCnts) {
	this.okCnts = okCnts;
    }

    public String[] getNgCnts() {
	return ngCnts;
    }

    public void setNgCnts(String[] ngCnts) {
	this.ngCnts = ngCnts;
    }

    public String[] getNgReasons() {
	return ngReasons;
    }

    public void setNgReasons(String[] ngReasons) {
	this.ngReasons = ngReasons;
    }

    public String[] getTgGateRsltCodes() {
	return tgGateRsltCodes;
    }

    public void setTgGateRsltCodes(String[] tgGateRsltCodes) {
	this.tgGateRsltCodes = tgGateRsltCodes;
    }

    public String[] getTgGateCheckOids() {
	return tgGateCheckOids;
    }

    public void setTgGateCheckOids(String[] tgGateCheckOids) {
	this.tgGateCheckOids = tgGateCheckOids;
    }

    public String[] getTgGateResults() {
	return tgGateResults;
    }

    public void setTgGateResults(String[] tgGateResults) {
	this.tgGateResults = tgGateResults;
    }

    public String[] getTgOutputOids() {
	return tgOutputOids;
    }

    public void setTgOutputOids(String[] tgOutputOids) {
	this.tgOutputOids = tgOutputOids;
    }

    public String[] getTgTaskOids() {
	return tgTaskOids;
    }

    public void setTgTaskOids(String[] tgTaskOids) {
	this.tgTaskOids = tgTaskOids;
    }

    public String[] getTgTaskChecks() {
	return tgTaskChecks;
    }

    public void setTgTaskChecks(String[] tgTaskChecks) {
	this.tgTaskChecks = tgTaskChecks;
    }

    public String[] getTgTaskComments() {
	return tgTaskComments;
    }

    public void setTgTaskComments(String[] tgTaskComments) {
	this.tgTaskComments = tgTaskComments;
    }

    public String getPlanStartDate() {
	return planStartDate;
    }

    public void setPlanStartDate(String planStartDate) {
	this.planStartDate = planStartDate;
    }

    public String getPlanEndDate() {
	return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
	this.planEndDate = planEndDate;
    }

    public String getExecStartDate() {
	return execStartDate;
    }

    public void setExecStartDate(String execStartDate) {
	this.execStartDate = execStartDate;
    }

    public String getExecEndDate() {
	return execEndDate;
    }

    public void setExecEndDate(String execEndDate) {
	this.execEndDate = execEndDate;
    }

    public String getPlanWorkTime() {
	return planWorkTime;
    }

    public void setPlanWorkTime(String planWorkTime) {
	this.planWorkTime = planWorkTime;
    }

    public String getExecWorkTime() {
	return execWorkTime;
    }

    public void setExecWorkTime(String execWorkTime) {
	this.execWorkTime = execWorkTime;
    }

    public String getDelayReason() {
	return delayReason;
    }

    public void setDelayReason(String delayReason) {
	this.delayReason = delayReason;
    }

    public String getDelayType() {
	return delayType;
    }

    public void setDelayType(String delayType) {
	this.delayType = delayType;
    }

    public String getPjtOid() {
	return pjtOid;
    }

    public void setPjtOid(String pjtOid) {
	this.pjtOid = pjtOid;
    }

    public String[] getTgGateChecks() {
	return tgGateChecks;
    }

    public void setTgGateChecks(String[] tgGateChecks) {
	this.tgGateChecks = tgGateChecks;
    }

    public String[] getTgDqmHeaderOids() {
	return tgDqmHeaderOids;
    }

    public void setTgDqmHeaderOids(String[] tgDqmHeaderOids) {
	this.tgDqmHeaderOids = tgDqmHeaderOids;
    }

    public String[] getTgDqmHeaderChecks() {
	return tgDqmHeaderChecks;
    }

    public void setTgDqmHeaderChecks(String[] tgDqmHeaderChecks) {
	this.tgDqmHeaderChecks = tgDqmHeaderChecks;
    }

    public String getManagerDeptNames() {
	return managerDeptNames;
    }

    public void setManagerDeptNames(String managerDeptNames) {
	this.managerDeptNames = managerDeptNames;
    }

    public String[] getManagerDeptOids() {
	return managerDeptOids;
    }

    public void setManagerDeptOids(String[] managerDeptOids) {
	this.managerDeptOids = managerDeptOids;
    }
}
