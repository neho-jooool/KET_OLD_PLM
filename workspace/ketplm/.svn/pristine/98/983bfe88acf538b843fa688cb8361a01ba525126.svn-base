package ext.ket.project.cost.entity;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.project.E3PSProject;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.dto.BaseDTO;

public class KETCostDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private int attacheCnt = 0;
    private String classification;
    private String creator;
    private String mainIssue;
    private String partName;
    private String profitRate;
    private String projectOid = null;
    private String reviewCompleteDate;
    private String reviewStep;
    private String revision;
    private String salesTargetCost;
    private String state;
    private String totalCost;
    private String createdDate;
    private String projectNo;
    private String projectName;
    private String subContractor;
    private String carType;
    private String projectState;
    private String division;
    private String url;
    private String production;
    private String suyearSum;
    private String totalsalesSum;
    private String DevelopedType;
    private String DrStep;

    public KETCostDTO() {
    }


    public KETCostDTO(KETCostRate costRate) {
	super.setOid(CommonUtil.getOIDString(costRate));
	this.classification = costRate.classification;
	this.mainIssue = costRate.mainIssue;
	this.partName = costRate.partName;
	this.profitRate = costRate.profitRate;
	this.reviewCompleteDate = DateUtil.getDateString(costRate.reviewCompleteDate, "d");
	this.reviewStep = costRate.reviewStep;
	this.revision = costRate.revision;
	this.totalCost = costRate.totalCost;
	this.salesTargetCost = costRate.salesTargetCost;
	this.creator = costRate.getCreatorFullName();
	this.state = costRate.getState().getState().getDisplay();
	this.createdDate = DateUtil.getTimeFormat(costRate.getCreateTimestamp(), "yyyy-MM-dd");
	this.attacheCnt = KETContentHelper.manager.getSecondaryContents(costRate).size();
    }

    public KETCostDTO(KETProjectCostLink ketProjectCostLink) {
	E3PSProject project = ketProjectCostLink.getProject();
	KETCostRate costRate = ketProjectCostLink.getCost();
	super.setOid(CommonUtil.getOIDString(costRate));
	this.projectOid = CommonUtil.getOIDString(project);
	this.projectNo = project.getPjtNo();
	this.projectName = project.getPjtName();
	// this.subContractor = project.get;
	// this.carType = project.;
	this.projectState = project.getState().getState().getDisplay();

	this.revision = costRate.revision;
	this.classification = costRate.classification;
	this.totalCost = costRate.totalCost;
	this.salesTargetCost = costRate.salesTargetCost;
	this.profitRate = costRate.profitRate;
    }

    public String getProduction() {
	return production;
    }

    public void setProduction(String production) {
	this.production = production;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getDivision() {
	return division;
    }

    public void setDivision(String division) {
	this.division = division;
    }

    public String getProjectNoHtmlPrefix() {
	return super.getHtmlPrefix();
    }

    public String getProjectNoHtmlPostfix() {
	return super.getHtmlPostfix();
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

    public String getSubContractor() {
	return subContractor;
    }

    public void setSubContractor(String subContractor) {
	this.subContractor = subContractor;
    }

    public String getCarType() {
	return carType;
    }

    public void setCarType(String carType) {
	this.carType = carType;
    }

    public String getProjectState() {
	return projectState;
    }

    public void setProjectState(String projectState) {
	this.projectState = projectState;
    }

    public int getAttacheCnt() {
	return attacheCnt;
    }

    public void setAttacheCnt(int attacheCnt) {
	this.attacheCnt = attacheCnt;
    }

    public String getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(String createdDate) {
	this.createdDate = createdDate;
    }

    public String getClassification() {
	return classification;
    }

    public String getCreator() {
	return creator;
    }

    public String getMainIssue() {
	return mainIssue;
    }

    public String getPartName() {
	return partName;
    }

    public String getProfitRate() {
	return profitRate;
    }

    public String getProjectOid() {
	return projectOid;
    }

    public String getReviewCompleteDate() {
	return reviewCompleteDate;
    }

    public String getReviewStep() {
	return reviewStep;
    }

    public String getRevisionHtmlPrefix() {
	return super.getHtmlPrefix();
    }

    public String getRevisionHtmlPostfix() {
	return super.getHtmlPostfix();
    }

    public String getRevision() {
	return revision;
    }

    public String getSalesTargetCost() {
	return salesTargetCost;
    }

    public String getState() {
	return state;
    }

    public String getTotalCost() {
	return totalCost;
    }

    public void setClassification(String classification) {
	this.classification = classification;
    }

    public void setCreator(String creator) {
	this.creator = creator;
    }

    public void setMainIssue(String mainIssue) {
	this.mainIssue = mainIssue;
    }

    public void setPartName(String partName) {
	this.partName = partName;
    }

    public void setProfitRate(String profitRate) {
	this.profitRate = profitRate;
    }

    public void setProjectOid(String projectOid) {
	this.projectOid = projectOid;
    }

    public void setReviewCompleteDate(String reviewCompleteDate) {
	this.reviewCompleteDate = reviewCompleteDate;
    }

    public void setReviewStep(String reviewStep) {
	this.reviewStep = reviewStep;
    }

    public void setRevision(String revision) {
	this.revision = revision;
    }

    public void setSalesTargetCost(String salesTargetCost) {
	this.salesTargetCost = salesTargetCost;
    }

    public void setState(String state) {
	this.state = state;
    }

    public void setTotalCost(String totalCost) {
	this.totalCost = totalCost;
    }
    
    public String getSuyearSum() {
        return suyearSum;
    }

    public void setSuyearSum(String suyearSum) {
        this.suyearSum = suyearSum;
    }

    public String getTotalsalesSum() {
        return totalsalesSum;
    }

    public void setTotalsalesSum(String totalsalesSum) {
        this.totalsalesSum = totalsalesSum;
    }
    
    public String getDevelopedType() {
        return DevelopedType;
    }

    public void setDevelopedType(String developedType) {
        DevelopedType = developedType;
    }


    public String getDrStep() {
        return DrStep;
    }


    public void setDrStep(String drStep) {
        DrStep = drStep;
    }

}
