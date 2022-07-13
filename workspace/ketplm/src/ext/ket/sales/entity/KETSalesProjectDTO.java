package ext.ket.sales.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import e3ps.common.code.NumberCode;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.shared.dto.BaseDTO;
import wt.fc.QueryResult;
import wt.org.WTUser;

final public class KETSalesProjectDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String projectNo;
    private String projectName;
    private String applyArea;

    private String salesRank;
    private String customerCode;
    private String customerName;
    private String lastBuyerName;
    private String lastBuyerCode;
    private String developType;
    private String model;
    private String tempmodel;
    private String sopDate;
    private String competitorCompany;
    private String nation;
    private String classOidArr[];
    private String investCost[];
    private String planTotal[];
    private String planYear[];
    private String expectSalesTotal[];
    private String expectSalesYear[];
    private String subject[];
    private String salsePlan[];
    private String planDate[];
    private String resultDate[];
    private String planCheck[];
    private String webEditor;
    private String webEditorText;

    private String targetPeopleName;
    private String targetPeopleOid;
    private String targetPeopleOidAttr[];
    private String mainCheck[];

    private String stepName;
    private String idx;

    private String propelwebEditor[];
    private String propelwebEditorText[];

    private String problemwebEditor[];
    private String problemwebEditorText[];

    private String planwebEditor[];
    private String planwebEditorText[];

    private String planCheck_1;
    private String propelwebEditor_1;
    private String propelwebEditorText_1;

    private String problemwebEditor_1;
    private String problemwebEditorText_1;

    private String planwebEditor_1;
    private String planwebEditorText_1;

    private String partClazOid;

    private String rankName;
    private String rankOid;
    private String devTypeName;
    private String oemName;
    private String competitorName;
    private String nationName;
    private List<Map<String, String>> partClzList;
    private List<Map<String, String>> salesTaskList;
    private List<Map<String, Object>> taksLineUpList;
    private KETSalesProject salesPjt;
    private String customerOid;
    private String lastBuyerOid;
    private String devTypeOid;
    private String oemOid;
    private String competitorOid;
    private String nationOid;
    private String pmName;
    private String pmOid;
    private String rev;
    private String pmdept;
    private String stateName;
    private String salesStateName;
    private String salesStateOid;
    private String afterSalesStateName;
    private String afterSalesStateOid;
    private String basicEditAuth;
    private String vrOid;
    private String revise;
    private String isLastRev;
    private String sopStartDate;
    private String sopEndDate;
    private String createDateFrom;
    private String createDateTo;
    private String stateCode;
    private String mainPartClz;
    private String mainExpectSalesYear;
    private String mainExpectSalesTotal;
    private String mainSignal;
    private String failReason;
    private String obtainCompany;
    private String projectResult;
    private String obtainCompanyOid;
    private String csYN;
    private String bigo;
    private String failtypecode;
    private String failtypeName;
    private String obtainCompanyName;
    private String failReason_ModifyType;
    private String projectResult_ModifyType;
    private String bigo_ModifyType;
    private String isTransient;
    private String year;
    private String degree;
    private String csDegreeDate;
    private String createUser;
    private String modifyUser;
    private String csStartDate;
    private String csEndDate;
    private String csClose;
    private String month;
    private QueryResult queryResult;
    private List<Map<String, Object>> csPorjectList;
    private String[] sortNo;
    private String[] oids;
    private String delOids;
    private String[] taskOids;
    private String approveOid;
    private String mainCategory;
    private String mainCategoryName;
    private List<Map<String, String>> csDeptList;
    private String csNextStartDate;
    private String taskEdit;
    private String[] years;
    private String[] months;
    private String[] closeYNs;
    private KETSalesCSMeetingManage csCloseTartget;
    private String csimgSrc;
    private String csDir;
    private String csimgCnt;
    private String csimgNaming;
    private String csFileName;
    private String csFileType;
    private String[] fileSort;
    private String seniorAuth;
    private String productPjtNo;
    private String productPjtName;
    private String[] productPjtNos;
    private List<Map<String, String>> productProjectList;
    private String closeProjectYN;
    private String centerName;
    private List<Map<String, String>> salesPresentList;
    private String[] membserOids;
    
    private List<Map<String, String>> memberList;
    private String workShopYN;
    private String isDelay;
    private String signalText;
    
    private String refTag[];
    private List<Map<String, String>> refTagList;
    
    public String getSignalText() {
        return signalText;
    }

    public void setSignalText(String signalText) {
        this.signalText = signalText;
    }

    public String getIsDelay() {
        return isDelay;
    }

    public void setIsDelay(String isDelay) {
        this.isDelay = isDelay;
    }

    public String getWorkShopYN() {
        return workShopYN;
    }

    public void setWorkShopYN(String workShopYN) {
        this.workShopYN = workShopYN;
    }

    public List<Map<String, String>> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Map<String, String>> memberList) {
        this.memberList = memberList;
    }

    public String[] getMembserOids() {
        return membserOids;
    }

    public void setMembserOids(String[] membserOids) {
        this.membserOids = membserOids;
    }

    public List<Map<String, String>> getSalesPresentList() {
        return salesPresentList;
    }

    public void setSalesPresentList(List<Map<String, String>> salesPresentList) {
        this.salesPresentList = salesPresentList;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCloseProjectYN() {
        return closeProjectYN;
    }

    public void setCloseProjectYN(String closeProjectYN) {
        this.closeProjectYN = closeProjectYN;
    }

    public String getProductPjtNo() {
	return productPjtNo;
    }

    public void setProductPjtNo(String productPjtNo) {
	this.productPjtNo = productPjtNo;
    }

    public String getProductPjtName() {
	return productPjtName;
    }

    public void setProductPjtName(String productPjtName) {
	this.productPjtName = productPjtName;
    }

    public String[] getProductPjtNos() {
	return productPjtNos;
    }

    public void setProductPjtNos(String[] productPjtNos) {
	this.productPjtNos = productPjtNos;
    }

    public List<Map<String, String>> getProductProjectList() {
	return productProjectList;
    }

    public void setProductProjectList(List<Map<String, String>> productProjectList) {
	this.productProjectList = productProjectList;
    }

    public String getSeniorAuth() {
	return seniorAuth;
    }

    public void setSeniorAuth(String seniorAuth) {
	this.seniorAuth = seniorAuth;
    }

    public String[] getFileSort() {
	return fileSort;
    }

    public void setFileSort(String[] fileSort) {
	this.fileSort = fileSort;
    }

    public String getCsFileType() {
	return csFileType;
    }

    public void setCsFileType(String csFileType) {
	this.csFileType = csFileType;
    }

    public String getCsFileName() {
	return csFileName;
    }

    public void setCsFileName(String csFileName) {
	this.csFileName = csFileName;
    }

    public String getCsimgCnt() {
	return csimgCnt;
    }

    public void setCsimgCnt(String csimgCnt) {
	this.csimgCnt = csimgCnt;
    }

    public String getCsimgNaming() {
	return csimgNaming;
    }

    public void setCsimgNaming(String csimgNaming) {
	this.csimgNaming = csimgNaming;
    }

    public String getCsDir() {
	return csDir;
    }

    public void setCsDir(String csDir) {
	this.csDir = csDir;
    }

    public String getCsimgSrc() {
	return csimgSrc;
    }

    public void setCsimgSrc(String csimgSrc) {
	this.csimgSrc = csimgSrc;
    }

    public KETSalesCSMeetingManage getCsCloseTartget() {
	return csCloseTartget;
    }

    public void setCsCloseTartget(KETSalesCSMeetingManage csCloseTartget) {
	this.csCloseTartget = csCloseTartget;
    }

    public String[] getYears() {
	return years;
    }

    public void setYears(String[] years) {
	this.years = years;
    }

    public String[] getMonths() {
	return months;
    }

    public void setMonths(String[] months) {
	this.months = months;
    }

    public String[] getCloseYNs() {
	return closeYNs;
    }

    public void setCloseYNs(String[] closeYNs) {
	this.closeYNs = closeYNs;
    }

    public String getTaskEdit() {
	return taskEdit;
    }

    public void setTaskEdit(String taskEdit) {
	this.taskEdit = taskEdit;
    }

    public String getCsNextStartDate() {
	return csNextStartDate;
    }

    public void setCsNextStartDate(String csNextStartDate) {
	this.csNextStartDate = csNextStartDate;
    }

    public List<Map<String, String>> getCsDeptList() {
	return csDeptList;
    }

    public void setCsDeptList(List<Map<String, String>> csDeptList) {
	this.csDeptList = csDeptList;
    }

    public String getMainCategoryName() {
	return mainCategoryName;
    }

    public void setMainCategoryName(String mainCategoryName) {
	this.mainCategoryName = mainCategoryName;
    }

    public String getMainCategory() {
	return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
	this.mainCategory = mainCategory;
    }

    public String getApproveOid() {
	return approveOid;
    }

    public void setApproveOid(String approveOid) {
	this.approveOid = approveOid;
    }

    public String[] getTaskOids() {
	return taskOids;
    }

    public void setTaskOids(String[] taskOids) {
	this.taskOids = taskOids;
    }

    public String getDelOids() {
	return delOids;
    }

    public void setDelOids(String delOids) {
	this.delOids = delOids;
    }

    public String[] getOids() {
	return oids;
    }

    public void setOids(String[] oids) {
	this.oids = oids;
    }

    public String[] getSortNo() {
	return sortNo;
    }

    public void setSortNo(String[] sortNo) {
	this.sortNo = sortNo;
    }

    public List<Map<String, Object>> getCsPorjectList() {
	return csPorjectList;
    }

    public void setCsPorjectList(List<Map<String, Object>> csPorjectList) {
	this.csPorjectList = csPorjectList;
    }

    public String getMonth() {
	return month;
    }

    public void setMonth(String month) {
	this.month = month;
    }

    public QueryResult getQueryResult() {
	return queryResult;
    }

    public void setQueryResult(QueryResult queryResult) {
	this.queryResult = queryResult;
    }

    public String getCreateUser() {
	return createUser;
    }

    public void setCreateUser(String createUser) {
	this.createUser = createUser;
    }

    public String getModifyUser() {
	return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
	this.modifyUser = modifyUser;
    }

    public String getCsStartDate() {
	return csStartDate;
    }

    public void setCsStartDate(String csStartDate) {
	this.csStartDate = csStartDate;
    }

    public String getCsEndDate() {
	return csEndDate;
    }

    public void setCsEndDate(String csEndDate) {
	this.csEndDate = csEndDate;
    }

    public String getCsClose() {
	return csClose;
    }

    public void setCsClose(String csClose) {
	this.csClose = csClose;
    }

    public String getCsDegreeDate() {
	return csDegreeDate;
    }

    public void setCsDegreeDate(String csDegreeDate) {
	this.csDegreeDate = csDegreeDate;
    }

    public String getYear() {
	return year;
    }

    public void setYear(String year) {
	this.year = year;
    }

    public String getDegree() {
	return degree;
    }

    public void setDegree(String degree) {
	this.degree = degree;
    }

    public String getIsTransient() {
	return isTransient;
    }

    public void setIsTransient(String isTransient) {
	this.isTransient = isTransient;
    }

    public String getFailReason_ModifyType() {
	return failReason_ModifyType;
    }

    public void setFailReason_ModifyType(String failReason_ModifyType) {
	this.failReason_ModifyType = failReason_ModifyType;
    }

    public String getProjectResult_ModifyType() {
	return projectResult_ModifyType;
    }

    public void setProjectResult_ModifyType(String projectResult_ModifyType) {
	this.projectResult_ModifyType = projectResult_ModifyType;
    }

    public String getBigo_ModifyType() {
	return bigo_ModifyType;
    }

    public void setBigo_ModifyType(String bigo_ModifyType) {
	this.bigo_ModifyType = bigo_ModifyType;
    }

    public String getObtainCompanyName() {
	return obtainCompanyName;
    }

    public void setObtainCompanyName(String obtainCompanyName) {
	this.obtainCompanyName = obtainCompanyName;
    }

    public String getFailtypeName() {
	return failtypeName;
    }

    public void setFailtypeName(String failtypeName) {
	this.failtypeName = failtypeName;
    }

    public String getFailtypecode() {
	return failtypecode;
    }

    public void setFailtypecode(String failtypecode) {
	this.failtypecode = failtypecode;
    }

    public String getBigo() {
	return bigo;
    }

    public void setBigo(String bigo) {
	this.bigo = bigo;
    }

    public String getCsYN() {
	return csYN;
    }

    public void setCsYN(String csYN) {
	this.csYN = csYN;
    }

    public String getObtainCompanyOid() {
	return obtainCompanyOid;
    }

    public void setObtainCompanyOid(String obtainCompanyOid) {
	this.obtainCompanyOid = obtainCompanyOid;
    }

    public String getFailReason() {
	return failReason;
    }

    public void setFailReason(String failReason) {
	this.failReason = failReason;
    }

    public String getObtainCompany() {
	return obtainCompany;
    }

    public void setObtainCompany(String obtainCompany) {
	this.obtainCompany = obtainCompany;
    }

    public String getProjectResult() {
	return projectResult;
    }

    public void setProjectResult(String projectResult) {
	this.projectResult = projectResult;
    }

    public String getMainSignal() {
	return mainSignal;
    }

    public void setMainSignal(String mainSignal) {
	this.mainSignal = mainSignal;
    }

    public String getMainSignalAndonHtmlPrefix() {

	String str = "";

	if ("red".equals(this.getMainSignal())) {
	    str = "<img src='/plm/extcore/image/ico_red.png' />";
	} else if ("yellow".equals(this.getMainSignal())) {
	    str = "<img src='/plm/extcore/image/ico_yellow.png' />";
	} else if ("green".equals(this.getMainSignal())) {
	    str = "<img src='/plm/extcore/image/ico_green.png' />";
	} else if ("gray".equals(this.getMainSignal())) {
	    str = "<img src='/plm/extcore/image/ico_gray.gif' />";
	}
	
	return str;
    }

    public String getMainSignalAndonHtmlPostfix() {
	return "";
    }

    public String getMainExpectSalesYear() {
	return mainExpectSalesYear;
    }

    public void setMainExpectSalesYear(String mainExpectSalesYear) {
	this.mainExpectSalesYear = mainExpectSalesYear;
    }

    public String getMainExpectSalesTotal() {
	return mainExpectSalesTotal;
    }

    public void setMainExpectSalesTotal(String mainExpectSalesTotal) {
	this.mainExpectSalesTotal = mainExpectSalesTotal;
    }

    public String getMainPartClz() {
	return mainPartClz;
    }

    public void setMainPartClz(String mainPartClz) {
	this.mainPartClz = mainPartClz;
    }

    public String getStateCode() {
	return stateCode;
    }

    public void setStateCode(String stateCode) {
	this.stateCode = stateCode;
    }

    public String getCreateDateFrom() {
	return createDateFrom;
    }

    public void setCreateDateFrom(String createDateFrom) {
	this.createDateFrom = createDateFrom;
    }

    public String getCreateDateTo() {
	return createDateTo;
    }

    public void setCreateDateTo(String createDateTo) {
	this.createDateTo = createDateTo;
    }

    public String getSopStartDate() {
	return sopStartDate;
    }

    public void setSopStartDate(String sopStartDate) {
	this.sopStartDate = sopStartDate;
    }

    public String getSopEndDate() {
	return sopEndDate;
    }

    public void setSopEndDate(String sopEndDate) {
	this.sopEndDate = sopEndDate;
    }

    public List<Map<String, Object>> getTaksLineUpList() {
	return taksLineUpList;
    }

    public void setTaksLineUpList(List<Map<String, Object>> taksLineUpList) {
	this.taksLineUpList = taksLineUpList;
    }

    public KETSalesProjectDTO KETSalesCSDTOGrid(KETSalesCSMeetingManage csManagae, KETSalesProjectDTO ketSalesDTO,
	    KETMessageService messageService) throws Exception {
	ketSalesDTO.setOid(CommonUtil.getOIDString(csManagae));
	ketSalesDTO.setYear(csManagae.getYear());
	ketSalesDTO.setDegree(csManagae.getDegree());
	ketSalesDTO.setCsDegreeDate(DateUtil.getDateString(csManagae.getPersistInfo().getCreateStamp(), "date"));
	ketSalesDTO.setMonth(csManagae.getMonth());

	ketSalesDTO.setCreateUser(csManagae.getCreateUser().getFullName());
	ketSalesDTO.setModifyUser(csManagae.getModifyUser().getFullName());
	ketSalesDTO.setCsStartDate(DateUtil.getDateString(csManagae.getCsStartDate(), "date"));
	ketSalesDTO.setCsEndDate(DateUtil.getDateString(csManagae.getCsEndDate(), "date"));
	ketSalesDTO.setCsClose(csManagae.getCsClose());

	return ketSalesDTO;
    }

    public KETSalesProjectDTO KETSalesDTOGrid(KETSalesProject SalesProject, KETSalesProjectDTO ketSalesDTO,
	    KETMessageService messageService, String mainSignal, boolean authCheckFlag, String isDelay) throws Exception {
	ketSalesDTO.setOid(CommonUtil.getOIDString(SalesProject));
	ketSalesDTO.mainSignal = mainSignal;
	ketSalesDTO.projectNo = SalesProject.getProjectNo();
	ketSalesDTO.projectName = SalesProject.getProjectName();
	
	if(authCheckFlag){
	    ketSalesDTO.mainExpectSalesTotal = SalesProject.getExpectSalesTotal();
	}
	 
	NumberCode num = (NumberCode) SalesProject.getSalesState();// 프로젝트 상태
	if (num != null) {
	    ketSalesDTO.salesStateName = num.getName();
	    if(num.getName().equals("실패")){
		ketSalesDTO.mainSignal = "red";
	    }
	}
	
	String signalText = "";
	
	if("green".equals(ketSalesDTO.mainSignal)){
	    signalText = "G";
	}
	if("yellow".equals(ketSalesDTO.mainSignal)){
	    signalText = "Y";
	}
	if("red".equals(ketSalesDTO.mainSignal)){
	    signalText = "R";
	}
	if("gray".equals(ketSalesDTO.mainSignal)){
	    signalText = "P";
	}
	
	ketSalesDTO.signalText = signalText;
	
	num = (NumberCode) SalesProject.getRankType();// 중요도
	if (num != null) {
	    ketSalesDTO.rankName = num.getName();
	}
	num = (NumberCode) SalesProject.getDevType(); // 프로젝트유형
	if (num != null) {
	    ketSalesDTO.devTypeName = num.getName();
	}
	if (SalesProject.getSop() != null) {
	    ketSalesDTO.sopDate = DateUtil.getDateString(SalesProject.getSop(), "date");// sop
	}
	num = (NumberCode) SalesProject.getNationType(); // 국가
	if (num != null) {
	    ketSalesDTO.nationName = num.getName();
	}
	OEMProjectType oem = (OEMProjectType) SalesProject.getOemType(); // 차종
	if (oem != null) {
	    ketSalesDTO.oemName = oem.getName();
	}

	String VrOid = "VR:" + SalesProject.getPersistInfo().getObjectIdentifier().getClassname() + ":"
	        + SalesProject.getBranchIdentifier();

	ketSalesDTO.stateName = ((KETSalesProject) CommonUtil.getObject(VrOid)).getState().getState()
	        .getDisplay(messageService.getLocale());

	ketSalesDTO.setRev(SalesProject.getVersionInfo().getIdentifier().getValue() + "."
	        + SalesProject.getIterationInfo().getIdentifier().getValue());// 버전

	WTUser wtuser = (WTUser) SalesProject.getIterationInfo().getCreator().getPrincipal();
	ketSalesDTO.setPmName(wtuser.getFullName());

	// Department userDept = DepartmentHelper.manager.getDepartment(wtuser);
	if (SalesProject.getSalesTeam() != null) {
	    ketSalesDTO.setPmdept(SalesProject.getSalesTeam().getName());
	}

	ketSalesDTO.createDateFrom = DateUtil.getDateString(SalesProject.getCreateTimestamp(), "date");
	ketSalesDTO.isDelay = isDelay;

	return ketSalesDTO;
    }

    public String getRevHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getRevHtmlPostfix() {
	return "</font>";
    }

    public String getProjectNoHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getProjectNoHtmlPostfix() {
	return "</font>";
    }

    public String getDegreeHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getDegreeHtmlPostfix() {
	return "</font>";
    }

    public String getIsLastRev() {
	return isLastRev;
    }

    public void setIsLastRev(String isLastRev) {
	this.isLastRev = isLastRev;
    }

    public String getRevise() {
	return revise;
    }

    public void setRevise(String revise) {
	this.revise = revise;
    }

    public String getVrOid() {
	return vrOid;
    }

    public void setVrOid(String vrOid) {
	this.vrOid = vrOid;
    }

    public String getBasicEditAuth() {
	return basicEditAuth;
    }

    public void setBasicEditAuth(String basicEditAuth) {
	this.basicEditAuth = basicEditAuth;
    }

    public String getAfterSalesStateName() {
	return afterSalesStateName;
    }

    public void setAfterSalesStateName(String afterSalesStateName) {
	this.afterSalesStateName = afterSalesStateName;
    }

    public String getAfterSalesStateOid() {
	return afterSalesStateOid;
    }

    public void setAfterSalesStateOid(String afterSalesStateOid) {
	this.afterSalesStateOid = afterSalesStateOid;
    }

    public String getSalesStateOid() {
	return salesStateOid;
    }

    public void setSalesStateOid(String salesStateOid) {
	this.salesStateOid = salesStateOid;
    }

    public String getSalesStateName() {
	return salesStateName;
    }

    public void setSalesStateName(String salesStateName) {
	this.salesStateName = salesStateName;
    }

    public String getStateName() {
	return stateName;
    }

    public void setStateName(String stateName) {
	this.stateName = stateName;
    }

    public String getPmName() {
	return pmName;
    }

    public void setPmName(String pmName) {
	this.pmName = pmName;
    }

    public String getPmOid() {
	return pmOid;
    }

    public void setPmOid(String pmOid) {
	this.pmOid = pmOid;
    }

    public String getRev() {
	return rev;
    }

    public void setRev(String rev) {
	this.rev = rev;
    }

    public String getPmdept() {
	return pmdept;
    }

    public void setPmdept(String pmdept) {
	this.pmdept = pmdept;
    }

    public String getCustomerOid() {
	return customerOid;
    }

    public void setCustomerOid(String customerOid) {
	this.customerOid = customerOid;
    }

    public String getLastBuyerOid() {
	return lastBuyerOid;
    }

    public void setLastBuyerOid(String lastBuyerOid) {
	this.lastBuyerOid = lastBuyerOid;
    }

    public String getDevTypeOid() {
	return devTypeOid;
    }

    public void setDevTypeOid(String devTypeOid) {
	this.devTypeOid = devTypeOid;
    }

    public String getOemOid() {
	return oemOid;
    }

    public void setOemOid(String oemOid) {
	this.oemOid = oemOid;
    }

    public String getCompetitorOid() {
	return competitorOid;
    }

    public void setCompetitorOid(String competitorOid) {
	this.competitorOid = competitorOid;
    }

    public String getNationOid() {
	return nationOid;
    }

    public void setNationOid(String nationOid) {
	this.nationOid = nationOid;
    }

    public String getRankOid() {
	return rankOid;
    }

    public void setRankOid(String rankOid) {
	this.rankOid = rankOid;
    }

    public KETSalesProject getSalesPjt() {
	return salesPjt;
    }

    public void setSalesPjt(KETSalesProject salesPjt) {
	this.salesPjt = salesPjt;
    }

    public List<Map<String, String>> getSalesTaskList() {
	return salesTaskList;
    }

    public void setSalesTaskList(List<Map<String, String>> salesTaskList) {
	this.salesTaskList = salesTaskList;
    }

    public List<Map<String, String>> getPartClzList() {
	return partClzList;
    }

    public void setPartClzList(List<Map<String, String>> partClzList) {
	this.partClzList = partClzList;
    }

    public String getTargetPeopleName() {
	return targetPeopleName;
    }

    public void setTargetPeopleName(String targetPeopleName) {
	this.targetPeopleName = targetPeopleName;
    }

    public String getTargetPeopleOid() {
	return targetPeopleOid;
    }

    public void setTargetPeopleOid(String targetPeopleOid) {
	this.targetPeopleOid = targetPeopleOid;
    }

    public String[] getTargetPeopleOidAttr() {
	return targetPeopleOidAttr;
    }

    public void setTargetPeopleOidAttr(String[] targetPeopleOidAttr) {
	this.targetPeopleOidAttr = targetPeopleOidAttr;
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

    public String getApplyArea() {
	return applyArea;
    }

    public void setApplyArea(String applyArea) {
	this.applyArea = applyArea;
    }

    public String getSalesRank() {
	return salesRank;
    }

    public void setSalesRank(String salesRank) {
	this.salesRank = salesRank;
    }

    public String getCustomerCode() {
	return customerCode;
    }

    public void setCustomerCode(String customerCode) {
	this.customerCode = customerCode;
    }

    public String getCustomerName() {
	return customerName;
    }

    public void setCustomerName(String customerName) {
	this.customerName = customerName;
    }

    public String getLastBuyerName() {
	return lastBuyerName;
    }

    public void setLastBuyerName(String lastBuyerName) {
	this.lastBuyerName = lastBuyerName;
    }

    public String getLastBuyerCode() {
	return lastBuyerCode;
    }

    public void setLastBuyerCode(String lastBuyerCode) {
	this.lastBuyerCode = lastBuyerCode;
    }

    public String getDevelopType() {
	return developType;
    }

    public void setDevelopType(String developType) {
	this.developType = developType;
    }

    public String getModel() {
	return model;
    }

    public void setModel(String model) {
	this.model = model;
    }

    public String getTempmodel() {
	return tempmodel;
    }

    public void setTempmodel(String tempmodel) {
	this.tempmodel = tempmodel;
    }

    public String getSopDate() {
	return sopDate;
    }

    public void setSopDate(String sopDate) {
	this.sopDate = sopDate;
    }

    public String getCompetitorCompany() {
	return competitorCompany;
    }

    public void setCompetitorCompany(String competitorCompany) {
	this.competitorCompany = competitorCompany;
    }

    public String getNation() {
	return nation;
    }

    public void setNation(String nation) {
	this.nation = nation;
    }

    public String[] getClassOidArr() {
	return classOidArr;
    }

    public void setClassOidArr(String[] classOidArr) {
	this.classOidArr = classOidArr;
    }

    public String[] getInvestCost() {
	return investCost;
    }

    public void setInvestCost(String[] investCost) {
	this.investCost = investCost;
    }

    public String[] getPlanTotal() {
	return planTotal;
    }

    public void setPlanTotal(String[] planTotal) {
	this.planTotal = planTotal;
    }

    public String[] getPlanYear() {
	return planYear;
    }

    public void setPlanYear(String[] planYear) {
	this.planYear = planYear;
    }

    public String[] getExpectSalesTotal() {
	return expectSalesTotal;
    }

    public void setExpectSalesTotal(String[] expectSalesTotal) {
	this.expectSalesTotal = expectSalesTotal;
    }

    public String[] getExpectSalesYear() {
	return expectSalesYear;
    }

    public void setExpectSalesYear(String[] expectSalesYear) {
	this.expectSalesYear = expectSalesYear;
    }

    public String[] getSubject() {
	return subject;
    }

    public void setSubject(String[] subject) {
	this.subject = subject;
    }

    public String[] getSalsePlan() {
	return salsePlan;
    }

    public void setSalsePlan(String[] salsePlan) {
	this.salsePlan = salsePlan;
    }

    public String[] getPlanDate() {
	return planDate;
    }

    public void setPlanDate(String[] planDate) {
	this.planDate = planDate;
    }

    public String[] getResultDate() {
	return resultDate;
    }

    public void setResultDate(String[] resultDate) {
	this.resultDate = resultDate;
    }

    public String[] getPlanCheck() {
	return planCheck;
    }

    public void setPlanCheck(String[] planCheck) {
	this.planCheck = planCheck;
    }

    public String getWebEditor() {
	return webEditor;
    }

    public void setWebEditor(String webEditor) {
	this.webEditor = webEditor;
    }

    public String getWebEditorText() {
	return webEditorText;
    }

    public void setWebEditorText(String webEditorText) {
	this.webEditorText = webEditorText;
    }

    public String[] getMainCheck() {
	return mainCheck;
    }

    public void setMainCheck(String[] mainCheck) {
	this.mainCheck = mainCheck;
    }

    public String getStepName() {
	return stepName;
    }

    public void setStepName(String stepName) {
	this.stepName = stepName;
    }

    public String getIdx() {
	return idx;
    }

    public void setIdx(String idx) {
	this.idx = idx;
    }

    public String[] getPropelwebEditor() {
	return propelwebEditor;
    }

    public void setPropelwebEditor(String[] propelwebEditor) {
	this.propelwebEditor = propelwebEditor;
    }

    public String[] getPropelwebEditorText() {
	return propelwebEditorText;
    }

    public void setPropelwebEditorText(String[] propelwebEditorText) {
	this.propelwebEditorText = propelwebEditorText;
    }

    public String[] getProblemwebEditor() {
	return problemwebEditor;
    }

    public void setProblemwebEditor(String[] problemwebEditor) {
	this.problemwebEditor = problemwebEditor;
    }

    public String[] getProblemwebEditorText() {
	return problemwebEditorText;
    }

    public void setProblemwebEditorText(String[] problemwebEditorText) {
	this.problemwebEditorText = problemwebEditorText;
    }

    public String[] getPlanwebEditor() {
	return planwebEditor;
    }

    public void setPlanwebEditor(String[] planwebEditor) {
	this.planwebEditor = planwebEditor;
    }

    public String[] getPlanwebEditorText() {
	return planwebEditorText;
    }

    public void setPlanwebEditorText(String[] planwebEditorText) {
	this.planwebEditorText = planwebEditorText;
    }

    public String getPlanCheck_1() {
	return planCheck_1;
    }

    public void setPlanCheck_1(String planCheck_1) {
	this.planCheck_1 = planCheck_1;
    }

    public String getPropelwebEditor_1() {
	return propelwebEditor_1;
    }

    public void setPropelwebEditor_1(String propelwebEditor_1) {
	this.propelwebEditor_1 = propelwebEditor_1;
    }

    public String getPropelwebEditorText_1() {
	return propelwebEditorText_1;
    }

    public void setPropelwebEditorText_1(String propelwebEditorText_1) {
	this.propelwebEditorText_1 = propelwebEditorText_1;
    }

    public String getProblemwebEditor_1() {
	return problemwebEditor_1;
    }

    public void setProblemwebEditor_1(String problemwebEditor_1) {
	this.problemwebEditor_1 = problemwebEditor_1;
    }

    public String getProblemwebEditorText_1() {
	return problemwebEditorText_1;
    }

    public void setProblemwebEditorText_1(String problemwebEditorText_1) {
	this.problemwebEditorText_1 = problemwebEditorText_1;
    }

    public String getPlanwebEditor_1() {
	return planwebEditor_1;
    }

    public void setPlanwebEditor_1(String planwebEditor_1) {
	this.planwebEditor_1 = planwebEditor_1;
    }

    public String getPlanwebEditorText_1() {
	return planwebEditorText_1;
    }

    public void setPlanwebEditorText_1(String planwebEditorText_1) {
	this.planwebEditorText_1 = planwebEditorText_1;
    }

    public String getPartClazOid() {
	return partClazOid;
    }

    public void setPartClazOid(String partClazOid) {
	this.partClazOid = partClazOid;
    }

    public String getRankName() {
	return rankName;
    }

    public void setRankName(String rankName) {
	this.rankName = rankName;
    }

    public String getDevTypeName() {
	return devTypeName;
    }

    public void setDevTypeName(String devTypeName) {
	this.devTypeName = devTypeName;
    }

    public String getOemName() {
	return oemName;
    }

    public void setOemName(String oemName) {
	this.oemName = oemName;
    }

    public String getCompetitorName() {
	return competitorName;
    }

    public void setCompetitorName(String competitorName) {
	this.competitorName = competitorName;
    }

    public String getNationName() {
	return nationName;
    }

    public void setNationName(String nationName) {
	this.nationName = nationName;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the refTag
     */
    public String[] getRefTag() {
        return refTag;
    }

    /**
     * @param refTag the refTag to set
     */
    public void setRefTag(String[] refTag) {
        this.refTag = refTag;
    }

    /**
     * @return the refTagList
     */
    public List<Map<String, String>> getRefTagList() {
        return refTagList;
    }

    /**
     * @param refTagList the refTagList to set
     */
    public void setRefTagList(List<Map<String, String>> refTagList) {
        this.refTagList = refTagList;
    }
    
    
}