package ext.ket.project.program.entity;

import wt.org.WTUser;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.project.E3PSProject;
import e3ps.project.ExtendScheduleData;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.project.servlet.ProjectServlet;
import ext.ket.shared.dto.BaseDTO;

public class ProgramProjectDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private String            projectOid       = "";
    private String            projectNo        = "";
    private String            projectName      = "";
    private String            planStartDate    = "";
    private String            planEndDate      = "";
    private String            pm               = "";
    private String            state            = "";
    private int               pjtState         = 0;
    private String            displayState     = "";
    private String            baseProgram      = "";          // 기준 프로그램
    private String            checkedEvent     = "";         // 일정 확인/미확인

    private String            domain           = "";         //domain
    private String            maker            = "";         //maker
    private String            category         = "";         //category


    // ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
    private String            projectInfos4ECM = "";

    public ProgramProjectDTO() {
    }

    public ProgramProjectDTO(E3PSProject project) {
	this.setProject(project);
    }

    public ProgramProjectDTO(KETProgramProjectLink projectLink) {
	this.setOid(CommonUtil.getOIDString(projectLink));
	this.setProject(projectLink.getProject());
	this.baseProgram = (projectLink.isBase) ? "1" : "0";
	this.checkedEvent = (projectLink.isCheckedEvent) ? "1" : "0";
    }

    public String getBaseProgram() {
	return baseProgram;
    }

    public String getCategory() {
	return category;
    }

    public String getCheckedEvent() {
	return checkedEvent;
    }

    public String getDisplayBaseProgram() {
	return (this.baseProgram.equals("1")) ? "확인" : "미확인";
    }

    public String getDisplayCheckedEvent() {
	return (this.checkedEvent.equals("1")) ? "확인" : "미확인";
    }

    public String getDisplayState() throws Exception {
	return this.displayState;
    }

    public String getDomain() {
	return domain;
    }

    public String getMaker() {
	return maker;
    }

    public String getPlanEndDate() {
	return planEndDate;
    }

    public String getPlanStartDate() {
	return planStartDate;
    }

    public String getPm() {
	return pm;
    }

    public String getProjectInfos4ECM() {
	return projectInfos4ECM;
    }

    public String getProjectName() {
	return projectName;
    }

    public String getProjectNo() {
	return projectNo;
    }

    public String getProjectNoHtmlPostfix() {
	return super.getHtmlPostfix();
    }

    public String getProjectNoHtmlPrefix() {
	return super.getHtmlPrefix();
    }

    public String getProjectOid() {
	return projectOid;
    }

    public String getProjectSumary() throws Exception {
	return new ProjectServlet().getStateStr(pjtState, state, displayState, false);
    }

    public String getState() {
	return state;
    }

    public void setBaseProgram(String baseProgram) {
	this.baseProgram = baseProgram;
    }

    public void setCategory(String category) {
	this.category = category;
    }

    public void setCheckedEvent(String checkedEvent) {
	this.checkedEvent = checkedEvent;
    }

    public void setDisplayState(String displayState) {
	this.displayState = displayState;
    }

    public void setDomain(String domain) {
	this.domain = domain;
    }

    public void setMaker(String maker) {
	this.maker = maker;
    }

    public void setPlanEndDate(String planEndDate) {
	this.planEndDate = planEndDate;
    }

    public void setPlanStartDate(String planStartDate) {
	this.planStartDate = planStartDate;
    }

    public void setPm(String pm) {
	this.pm = pm;
    }

    private void setProject(E3PSProject project) {
	ExtendScheduleData esd = (ExtendScheduleData) project.getPjtSchedule().getObject();
	this.projectOid = CommonUtil.getOIDString(project);
	this.projectNo = project.getPjtNo();
	this.projectName = project.getPjtName();
	this.planStartDate = DateUtil.getDateString(esd.getPlanStartDate(), "d");
	this.planEndDate = DateUtil.getDateString(esd.getPlanEndDate(), "d");
	this.state = project.getState().toString();
	this.displayState = project.getState().getState().getDisplay();
	this.pjtState = project.getPjtState();
	WTUser pm = ProjectUserHelper.manager.getPM(project);
	this.pm = (pm == null) ? "" : pm.getFullName();
    }

    public void setProjectInfos4ECM(String projectInfos4ECM) {
	this.projectInfos4ECM = projectInfos4ECM;
    }

    public void setProjectName(String projectName) {
	this.projectName = projectName;
    }

    public void setProjectNo(String projectNo) {
	this.projectNo = projectNo;
    }

    public void setProjectOid(String projectOid) {
	this.projectOid = projectOid;
    }

    public void setState(String state) {
	this.state = state;
    }
}
