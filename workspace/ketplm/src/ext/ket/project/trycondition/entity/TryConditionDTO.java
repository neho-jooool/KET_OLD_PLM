package ext.ket.project.trycondition.entity;

import wt.fc.WTObject;
import wt.org.WTUser;
import e3ps.common.service.KETCommonHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.groupware.company.PeopleData;
import e3ps.project.MoldProject;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.shared.dto.BaseDTO;

public class TryConditionDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private String projectOid;
    private String projectOutputOid;
    private String parentTaskOid;
    private String tryNo;
    private int subVer;
    private String tryDate;
    private String tryPlace;
    private String dieNo;
    private String partNo;
    private String partName;
    private String moldRank;
    private String attendant;
    private String debugReason;
    private String detail;
    private String productDesignRole;
    private String moldMake;
    private String moldDesignRole;
    private String moldTryRole;
    private String creator;
    private String state;
    private String moldType;
    private String description;
    private String approver;
    private String approvedDate;
    private String approvedDeptName;
    private String createdDeptName;
    private String createdDate;
    private String modifyedDate;
    private String division;

    public TryConditionDTO() {
    }

    public TryConditionDTO(KETTryCondition tryCondition) throws Exception {
	super.setOid(CommonUtil.getOIDString(tryCondition));
	MoldProject moldProject = (MoldProject) tryCondition.getMoldProject();

	this.projectOid = CommonUtil.getOIDString(tryCondition.getMoldProject());
	this.dieNo = tryCondition.getDieNo();
	this.partNo = moldProject.getMoldInfo().getPartNo();
	this.partName = moldProject.getMoldInfo().getPartName();
	this.tryNo = tryCondition.getTryNo();
	this.subVer = tryCondition.getSubVer();
	this.moldMake = moldProject.getMoldInfo().getMaking();
	this.creator = tryCondition.getCreatorFullName();
	this.state = tryCondition.getLifeCycleState().getDisplay();
	this.moldType = moldProject.getMoldInfo().getItemType();
	this.createdDate = DateUtil.getDateString(tryCondition.getPersistInfo().getCreateStamp(), "D");
	this.debugReason = tryCondition.getDebugReason();
	this.modifyedDate = DateUtil.getDateString(tryCondition.getPersistInfo().getModifyStamp(), "D");
	this.tryPlace = tryCondition.getTryPlace();
	this.approver = "";
	this.approvedDate = "";
	this.approvedDeptName = "";

	PeopleData peopleData = new PeopleData(tryCondition.getCreator().getPrincipal());
	this.createdDeptName = peopleData.departmentName;
	if (!tryCondition.getLifeCycleState().toString().equals("INWORK")) {
	    WTObject pbo = KETCommonHelper.service.getPBO(tryCondition);
	    if (pbo != null) {
		WTUser appUser = WorkflowSearchHelper.manager.getLastApprover(pbo);
		peopleData = new PeopleData(appUser);
		this.approver = appUser != null ? appUser.getFullName() : "-";
		this.approvedDate = WorkflowSearchHelper.manager.getLastApprovalDate(pbo);
		this.approvedDeptName = appUser != null ? peopleData.departmentName : "-";
	    }
	}

    }

    public String getModifyedDate() {
	return modifyedDate;
    }

    public void setModifyedDate(String modifyedDate) {
	this.modifyedDate = modifyedDate;
    }

    public String getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(String createdDate) {
	this.createdDate = createdDate;
    }

    public String getApprovedDeptName() {
	return approvedDeptName;
    }

    public void setApprovedDeptName(String approvedDeptName) {
	this.approvedDeptName = approvedDeptName;
    }

    public String getCreatedDeptName() {
	return createdDeptName;
    }

    public void setCreatedDeptName(String createdDeptName) {
	this.createdDeptName = createdDeptName;
    }

    public String getApprover() {
	return approver;
    }

    public void setApprover(String approver) {
	this.approver = approver;
    }

    public String getApprovedDate() {
	return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
	this.approvedDate = approvedDate;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getMoldType() {
	return moldType;
    }

    public void setMoldType(String moldType) {
	this.moldType = moldType;
    }

    public String getCreator() {
	return creator;
    }

    public void setCreator(String creator) {
	this.creator = creator;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getTryNoHtmlPrefix() {
	return super.getHtmlPrefix();
    }

    public String getTryNoHtmlPostfix() {
	return super.getHtmlPostfix();
    }

    public String getTryNo() {
	return tryNo;
    }

    public void setTryNo(String tryNo) {
	this.tryNo = tryNo;
    }

    public int getSubVer() {
	return subVer;
    }

    public void setSubVer(int subVer) {
	this.subVer = subVer;
    }

    public String getParentTaskOid() {
	return parentTaskOid;
    }

    public void setParentTaskOid(String parentTaskOid) {
	this.parentTaskOid = parentTaskOid;
    }

    public String getTryPlace() {
	return tryPlace;
    }

    public void setTryPlace(String tryPlace) {
	this.tryPlace = tryPlace;
    }

    public String getTryDate() {
	return tryDate;
    }

    public void setTryDate(String tryDate) {
	this.tryDate = tryDate;
    }

    public String getDieNoHtmlPrefix() {
	return super.getHtmlPrefix();
    }

    public String getDieNoHtmlPostfix() {
	return super.getHtmlPostfix();
    }

    public String getDieNo() {
	return dieNo;
    }

    public void setDieNo(String dieNo) {
	this.dieNo = dieNo;
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

    public String getMoldRank() {
	return moldRank;
    }

    public void setMoldRank(String moldRank) {
	this.moldRank = moldRank;
    }

    public String getAttendant() {
	return attendant;
    }

    public void setAttendant(String attendant) {
	this.attendant = attendant;
    }

    public String getDebugReason() {
	return debugReason;
    }

    public void setDebugReason(String debugReason) {
	this.debugReason = debugReason;
    }

    public String getDetail() {
	return detail;
    }

    public void setDetail(String detail) {
	this.detail = detail;
    }

    public String getProductDesignRole() {
	return productDesignRole;
    }

    public void setProductDesignRole(String productDesignRole) {
	this.productDesignRole = productDesignRole;
    }

    public String getMoldMake() {
	return moldMake;
    }

    public void setMoldMake(String moldMake) {
	this.moldMake = moldMake;
    }

    public String getMoldDesignRole() {
	return moldDesignRole;
    }

    public void setMoldDesignRole(String moldDesignRole) {
	this.moldDesignRole = moldDesignRole;
    }

    public String getMoldTryRole() {
	return moldTryRole;
    }

    public void setMoldTryRole(String moldTryRole) {
	this.moldTryRole = moldTryRole;
    }

    public String getProjectOid() {
	return projectOid;
    }

    public void setProjectOid(String projectOid) {
	this.projectOid = projectOid;
    }

    public String getProjectOutputOid() {
	return projectOutputOid;
    }

    public void setProjectOutputOid(String projectOutputOid) {
	this.projectOutputOid = projectOutputOid;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
    
}
