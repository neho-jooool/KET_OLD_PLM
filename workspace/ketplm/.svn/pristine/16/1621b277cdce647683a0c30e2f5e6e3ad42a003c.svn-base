package ext.ket.project.program.entity;

import java.util.List;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import ext.ket.project.program.service.ProgramHelper;
import ext.ket.shared.dto.BaseDTO;

public class ProgramDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private String programNo;
    private String programName;
    private String programAdmin;// 프로그램 관리자
    private String divisionCode; // 사업부 코드
    private String lastUsingBuyer; // 최종사용처
    private String state; // 상태
    private String startDate;
    private String endDate;
    private String subContractor; // 접점고객
    private String subContractorOid; //접점고객 OID
    private String carType; // 차종
    private String carTypeOid; //차종 OID
    private String formType; // 형태
    private int projectsCount; // 프로젝트 count
    private boolean isNotify; // 공지 유무
    private String notifyReason; // 공지 사유
    private String selectTab; // 현재 선택된 Tab(BASE, PROJECT)
    private String projectSummary; // 현황
    private String baseProgram; // 기준 프로그램
    private String checkedEvent; // 일정 체크
    private String projectOid;
    private List<String> removedProgramOids;
    private List<String> programOids;
    private List<String> basePrograms;
    private List<String> checkedEvents;
    private List<ProgramEventDTO> events; // program event 리스트
    private List<ProgramProjectDTO> projects; // program project 리스트
    private String projectLinkOid;
    private ProgramNotifyDTO notifyDTO;

    public ProgramDTO() {

    }

    public ProgramDTO(KETProgramSchedule programSchedule) throws Exception {
	this.setOid(CommonUtil.getOIDString(programSchedule));
	this.programNo = programSchedule.getNumber();
	this.programName = programSchedule.getProgramName();
	this.programAdmin = programSchedule.getProgramAdmin().getFullName();
	this.divisionCode = programSchedule.getDivisionCode();
	this.subContractor = programSchedule.getSubContractor().getName();
	this.subContractorOid = CommonUtil.getOIDString(programSchedule.getSubContractor());
	this.lastUsingBuyer = programSchedule.getLastUsingBuyer().getName();
	this.carType = programSchedule.getCarType().getName();
	this.carTypeOid = CommonUtil.getOIDString(programSchedule.getCarType());
	this.formType = programSchedule.getFormType().getName();
	this.state = programSchedule.getLifeCycleState().toString().equals("INWORK") ? "-" : programSchedule.getLifeCycleState().getDisplay();
	this.startDate = programSchedule.getStartDate() != null ? DateUtil.getDateString(programSchedule.getStartDate(), "d") : "";
	this.endDate = programSchedule.getEndDate() != null ? DateUtil.getDateString(programSchedule.getEndDate(), "d") : "";
	this.projectsCount = ProgramHelper.service.findProjectByProgram(getOid()).size();
	this.isNotify = programSchedule.getIsNotify();
	this.setNotifyDTO(new ProgramNotifyDTO(programSchedule));
    }

    public String getProjectLinkOid() {
	return projectLinkOid;
    }

    public void setProjectLinkOid(String projectLinkOid) {
	this.projectLinkOid = projectLinkOid;
    }

    public String getBaseProgram() {
	return baseProgram;
    }

    public void setBaseProgram(String baseProgram) {
	this.baseProgram = baseProgram;
    }

    public String getCheckedEvent() {
	return checkedEvent;
    }

    public void setCheckedEvent(String checkedEvent) {
	this.checkedEvent = checkedEvent;
    }

    public List<String> getProgramOids() {
	return programOids;
    }

    public void setProgramOids(List<String> programOids) {
	this.programOids = programOids;
    }

    public List<String> getBasePrograms() {
	return basePrograms;
    }

    public void setBasePrograms(List<String> basePrograms) {
	this.basePrograms = basePrograms;
    }

    public List<String> getCheckedEvents() {
	return checkedEvents;
    }

    public void setCheckedEvents(List<String> checkedEvents) {
	this.checkedEvents = checkedEvents;
    }

    public List<String> getRemovedProgramOids() {
	return removedProgramOids;
    }

    public void setRemovedProgramOids(List<String> removedProgramOids) {
	this.removedProgramOids = removedProgramOids;
    }

    public String getProjectOid() {
	return projectOid;
    }

    public void setProjectOid(String projectOid) {
	this.projectOid = projectOid;
    }

    public String getProjectSummaryHtmlPrefix() {
	return super.getHtmlPrefix();
    }

    public String getProjectSummary() {
	return projectSummary;
    }

    public String getProjectSummaryHtmlPostfix() {
	return super.getHtmlPostfix();
    }

    public void setProjectSummary(String projectSummary) {
	this.projectSummary = projectSummary;
    }

    public String getProgramNo() {
	return programNo;
    }

    public String getProgramNoHtmlPrefix() {
	return super.getHtmlPrefix();
    }

    public String getProgramNoHtmlPostfix() {
	return super.getHtmlPostfix();
    }

    public String getProgramNameHtmlPrefix() {
	return super.getHtmlPrefix() + carType + " " + subContractor + " ";
    }

    public String getProgramNameHtmlPostfix() {
	return super.getHtmlPostfix();
    }

    public void setProgramNo(String programNo) {
	this.programNo = programNo;
    }

    public String getProgramName() {
	return programName;
    }

    public void setProgramName(String programName) {
	this.programName = programName;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getStartDate() {
	return startDate;
    }

    public void setStartDate(String startDate) {
	this.startDate = startDate;
    }

    public String getEndDate() {
	return endDate;
    }

    public void setEndDate(String endDate) {
	this.endDate = endDate;
    }

    public String getSubContractor() {
	return subContractor;
    }

    public void setSubContractor(String subContractor) {
	this.subContractor = subContractor;
    }

    /**
     * @return the subContractorOid
     */
    public String getSubContractorOid() {
        return subContractorOid;
    }

    /**
     * @param subContractorOid the subContractorOid to set
     */
    public void setSubContractorOid(String subContractorOid) {
        this.subContractorOid = subContractorOid;
    }

    public String getCarType() {
	return carType;
    }

    public void setCarType(String carType) {
	this.carType = carType;
    }

    /**
     * @return the carTypeOid
     */
    public String getCarTypeOid() {
        return carTypeOid;
    }

    /**
     * @param carTypeOid the carTypeOid to set
     */
    public void setCarTypeOid(String carTypeOid) {
        this.carTypeOid = carTypeOid;
    }

    public String getLastUsingBuyer() {
	return lastUsingBuyer;
    }

    public void setLastUsingBuyer(String lastUsingBuyer) {
	this.lastUsingBuyer = lastUsingBuyer;
    }

    public String getFormType() {
	return formType;
    }

    public void setFormType(String formType) {
	this.formType = formType;
    }

    public String getProgramAdmin() {
	return programAdmin;
    }

    public void setProgramAdmin(String programAdmin) {
	this.programAdmin = programAdmin;
    }

    public String getDivisionCode() {
	return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
	this.divisionCode = divisionCode;
    }

    public List<ProgramEventDTO> getEvents() {
	return events;
    }

    public void setEvents(List<ProgramEventDTO> events) {
	this.events = events;
    }

    public List<ProgramProjectDTO> getProjects() {
	return projects;
    }

    public void setProjects(List<ProgramProjectDTO> projects) {
	this.projects = projects;
    }

    public String getProjectsCountHtmlPrefix() {
	return super.getHtmlPrefix();
    }

    public int getProjectsCount() {
	return projectsCount;
    }

    public String getProjectsCountHtmlPostfix() {
	return super.getHtmlPostfix();
    }

    public void setProjectsCount(int projectsCount) {
	this.projectsCount = projectsCount;
    }

    public boolean getIsNotify() {
	return isNotify;
    }

    public void setNotify(boolean isNotify) {
	this.isNotify = isNotify;
    }

    public String getNotifyReason() {
	return notifyReason;
    }

    public void setNotifyReason(String notifyReason) {
	this.notifyReason = notifyReason;
    }

    public ProgramNotifyDTO getNotifyDTO() {
	return notifyDTO;
    }

    public void setNotifyDTO(ProgramNotifyDTO notifyDTO) {
	this.notifyDTO = notifyDTO;
    }

    public String getSelectTab() {
	return selectTab;
    }

    public void setSelectTab(String selectTab) {
	this.selectTab = selectTab;
    }
}