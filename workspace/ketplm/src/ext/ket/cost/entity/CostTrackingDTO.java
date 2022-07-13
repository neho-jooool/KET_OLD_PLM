package ext.ket.cost.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.QuerySpec;

import com.ibm.icu.math.BigDecimal;

import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.ProductProject;
import e3ps.project.ProjectSubcontractorLink;
import e3ps.project.ReviewProject;
import e3ps.project.beans.ProjectHelper;
import ext.ket.cost.service.CostHelper;
import ext.ket.cost.util.CostCalculateUtil;
import ext.ket.cost.util.CostUtil;
import ext.ket.project.program.service.ProgramHelper;
import ext.ket.shared.dto.BaseDTO;

public class CostTrackingDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(CostTrackingDTO.class);

    private String oid;
    private String partOid;
    private String pjtNo;
    private String pjtName;
    private String devTeamName;
    private String devTeamOid;
    private String devRoleName;
    private String devRoleOid;
    private String partNo;
    private String realPartNo;
    private String grade;
    private String customer;
    private String customerCode;
    private String model;
    private String sopDate;
    private String increase;
    private String carTypeCode;
    private String linkColor = PropertiesUtil.getSearchGridLinkColor();

    private String dr0lvOid;
    private String dr1lvOid;
    private String dr2lvOid;
    private String dr3lvOid;
    private String dr4lvOid;
    private String dr5lvOid;
    private String dr6lvOid;
    private String dr0lvProfitRate;
    private String dr1lvProfitRate;
    private String dr2lvProfitRate;
    private String dr3lvProfitRate;
    private String dr4lvProfitRate;
    private String dr5lvProfitRate;
    private String dr6lvProfitRate;

    private String version;

    private String costPartNos;
    private String pjtNos;

    private List<Map<String, Object>> cpMapList = new ArrayList<Map<String, Object>>();

    public CostTrackingDTO() {

    }

    @SuppressWarnings({ "static-access", "deprecation" })
    public CostTrackingDTO(CostPart part) {
	try {
	    E3PSProjectMaster pjtMaster = part.getProject();

	    E3PSProject project = ProjectHelper.manager.getLastProject(pjtMaster);

	    this.oid = CommonUtil.getOIDString(project);
	    this.pjtNo = project.getPjtNo();
	    this.pjtName = project.getPjtName();
	    this.partNo = part.getPartNo();
	    this.realPartNo = part.getRealPartNo();
	    this.partOid = CommonUtil.getOIDString(part);

	    // 차종
	    if (project.getOemType() != null) {
		this.model = project.getOemType().getName();
	    }

	    // 고객사
	    QuerySpec psspec = new QuerySpec(ProjectSubcontractorLink.class);

	    this.customer = "";

	    SearchUtil
		    .appendEQUAL(psspec, ProjectSubcontractorLink.class, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(this.oid), 0);
	    QueryResult psresult = PersistenceHelper.manager.find(psspec);
	    while (psresult != null && psresult.hasMoreElements()) {
		ProjectSubcontractorLink link = (ProjectSubcontractorLink) psresult.nextElement();
		if (link != null) {
		    this.customer += link.getSubcontractor().getName();
		    if (psresult.hasMoreElements())
			this.customer += ", ";
		}
	    }

	    List<HashMap<String, String>> list = ProgramHelper.service.findContactEventByproject(this.oid);// 프로그램의 접점고객 일정 찾기

	    // SOP
	    for (HashMap<String, String> hash : list) {
		if (StringUtils.isNotEmpty(hash.get("sop_contact_date"))) {
		    this.sopDate = hash.get("sop_contact_date");
		}
	    }

	    if (project instanceof ProductProject && project.getDevRequest() != null && project.getDevRequest().getProjectOID() != null) {
		// 제품개발팀
		// ReviewProject rp = (ReviewProject) CommonUtil.getObject(project.getDevRequest().getProjectOID());
		String pjtNos[] = project.getReviewPjtNo().split(",");
		for (String pjtNo : pjtNos) {
		    ReviewProject rp = (ReviewProject) ProjectHelper.getProject(pjtNo);
		    if (rp.getDevDept() != null) {
			this.devTeamName += rp.getDevDept().getName() + ",";
			this.devTeamOid += CommonUtil.getOIDString(rp.getDevDept()) + ",";
		    }
		}
		this.devTeamName = StringUtils.removeEnd(this.devTeamName, ",");
		this.devTeamOid = StringUtils.removeEnd(this.devTeamOid, ",");

	    }

	    List<CostPart> cpList = CostHelper.service.getProjectPartList(part.getPartNo(), pjtMaster);

	    // DR 속성설정
	    String dr0lvProfitRate = "0";
	    for (CostPart cp : cpList) {

		String drNo = cp.getDrStep();
		String partOid = CommonUtil.getOIDString(cp);

		Map<String, Object> cpMap = CostUtil.manager.converObjectToMap(cp);
		cpMap.put("oid", partOid);
		cpMapList.add(cpMap);

		if (cp.getCaseOrder() == null || cp.getCaseOrder() != 1)
		    continue;
		this.partOid = CommonUtil.getOIDString(cp);

		String profitRate = cp.getProfitRateExpr();
		String exp = profitRate + "*100";
		profitRate = (String) CostCalculateUtil.manager.eval(exp);
		profitRate = new BigDecimal(profitRate).setScale(1, BigDecimal.ROUND_HALF_UP).toString();
		if ("DR0".equals(drNo))
		    dr0lvProfitRate = profitRate;

		exp = profitRate + "-" + dr0lvProfitRate;
		this.increase = (String) CostCalculateUtil.manager.eval(exp);
		this.increase = new BigDecimal(this.increase).setScale(1, BigDecimal.ROUND_HALF_UP).toString();

		if (Float.parseFloat(profitRate) != 0)
		    profitRate += "%";
		if (Float.parseFloat(this.increase) != 0)
		    this.increase += "%";

		switch (drNo) {
		case "DR0":
		    this.dr0lvOid = partOid;
		    this.dr0lvProfitRate = profitRate;
		case "DR1":
		    this.dr1lvOid = partOid;
		    this.dr1lvProfitRate = profitRate;
		    break;
		case "DR2":
		    this.dr2lvOid = partOid;
		    this.dr2lvProfitRate = profitRate;
		    break;
		case "DR3":
		    this.dr3lvOid = partOid;
		    this.dr3lvProfitRate = profitRate;
		    break;
		case "DR4":
		    this.dr4lvOid = partOid;
		    this.dr4lvProfitRate = profitRate;
		    break;
		case "DR5":
		    this.dr5lvOid = partOid;
		    this.dr5lvProfitRate = profitRate;
		    break;
		case "DR6":
		    this.dr6lvOid = partOid;
		    this.dr6lvProfitRate = profitRate;
		    break;
		default:
		    break;
		}
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * @return the oid
     */
    public String getOid() {
	return oid;
    }

    /**
     * @param oid
     *            the oid to set
     */
    public void setOid(String oid) {
	this.oid = oid;
    }

    /**
     * @return the pjtNo
     */
    public String getPjtNo() {
	return pjtNo;
    }

    /**
     * @param pjtNo
     *            the pjtNo to set
     */
    public void setPjtNo(String pjtNo) {
	this.pjtNo = pjtNo;
    }

    /**
     * @return the pjtName
     */
    public String getPjtName() {
	return pjtName;
    }

    /**
     * @param pjtName
     *            the pjtName to set
     */
    public void setPjtName(String pjtName) {
	this.pjtName = pjtName;
    }

    /**
     * @return the devTeamName
     */
    public String getDevTeamName() {
	return devTeamName;
    }

    /**
     * @param devTeamName
     *            the devTeamName to set
     */
    public void setDevTeamName(String devTeamName) {
	this.devTeamName = devTeamName;
    }

    /**
     * @return the partNo
     */
    public String getPartNo() {
	return partNo;
    }

    /**
     * @param partNo
     *            the partNo to set
     */
    public void setPartNo(String partNo) {
	this.partNo = partNo;
    }

    /**
     * @return the grade
     */
    public String getGrade() {
	return grade;
    }

    /**
     * @param grade
     *            the grade to set
     */
    public void setGrade(String grade) {
	this.grade = grade;
    }

    /**
     * @return the customer
     */
    public String getCustomer() {
	return customer;
    }

    /**
     * @param customer
     *            the customer to set
     */
    public void setCustomer(String customer) {
	this.customer = customer;
    }

    /**
     * @return the model
     */
    public String getModel() {
	return model;
    }

    /**
     * @param model
     *            the model to set
     */
    public void setModel(String model) {
	this.model = model;
    }

    /**
     * @return the sopDate
     */
    public String getSopDate() {
	return sopDate;
    }

    /**
     * @param sopDate
     *            the sopDate to set
     */
    public void setSopDate(String sopDate) {
	this.sopDate = sopDate;
    }

    /**
     * @return the increase
     */
    public String getIncrease() {
	return increase;
    }

    /**
     * @param increase
     *            the increase to set
     */
    public void setIncrease(String increase) {
	this.increase = increase;
    }

    /**
     * @return the carTypeCode
     */
    public String getCarTypeCode() {
	return carTypeCode;
    }

    /**
     * @param carTypeCode
     *            the carTypeCode to set
     */
    public void setCarTypeCode(String carTypeCode) {
	this.carTypeCode = carTypeCode;
    }

    /**
     * @return the devTeamOid
     */
    public String getDevTeamOid() {
	return devTeamOid;
    }

    /**
     * @param devTeamOid
     *            the devTeamOid to set
     */
    public void setDevTeamOid(String devTeamOid) {
	this.devTeamOid = devTeamOid;
    }

    /**
     * @return the devRoleName
     */
    public String getDevRoleName() {
	return devRoleName;
    }

    /**
     * @param devRoleName
     *            the devRoleName to set
     */
    public void setDevRoleName(String devRoleName) {
	this.devRoleName = devRoleName;
    }

    /**
     * @return the devRoleOid
     */
    public String getDevRoleOid() {
	return devRoleOid;
    }

    /**
     * @param devRoleOid
     *            the devRoleOid to set
     */
    public void setDevRoleOid(String devRoleOid) {
	this.devRoleOid = devRoleOid;
    }

    /**
     * @return the customerCode
     */
    public String getCustomerCode() {
	return customerCode;
    }

    /**
     * @param customerCode
     *            the customerCode to set
     */
    public void setCustomerCode(String customerCode) {
	this.customerCode = customerCode;
    }

    /**
     * @return the linkColor
     */
    public String getLinkColor() {
	return linkColor;
    }

    /**
     * @param linkColor
     *            the linkColor to set
     */
    public void setLinkColor(String linkColor) {
	this.linkColor = linkColor;
    }

    /**
     * @return the dr0lvOid
     */
    public String getDr0lvOid() {
	return dr0lvOid;
    }

    /**
     * @param dr0lvOid
     *            the dr0lvOid to set
     */
    public void setDr0lvOid(String dr0lvOid) {
	this.dr0lvOid = dr0lvOid;
    }

    /**
     * @return the dr1lvOid
     */
    public String getDr1lvOid() {
	return dr1lvOid;
    }

    /**
     * @param dr1lvOid
     *            the dr1lvOid to set
     */
    public void setDr1lvOid(String dr1lvOid) {
	this.dr1lvOid = dr1lvOid;
    }

    /**
     * @return the dr2lvOid
     */
    public String getDr2lvOid() {
	return dr2lvOid;
    }

    /**
     * @param dr2lvOid
     *            the dr2lvOid to set
     */
    public void setDr2lvOid(String dr2lvOid) {
	this.dr2lvOid = dr2lvOid;
    }

    /**
     * @return the dr3lvOid
     */
    public String getDr3lvOid() {
	return dr3lvOid;
    }

    /**
     * @param dr3lvOid
     *            the dr3lvOid to set
     */
    public void setDr3lvOid(String dr3lvOid) {
	this.dr3lvOid = dr3lvOid;
    }

    /**
     * @return the dr4lvOid
     */
    public String getDr4lvOid() {
	return dr4lvOid;
    }

    /**
     * @param dr4lvOid
     *            the dr4lvOid to set
     */
    public void setDr4lvOid(String dr4lvOid) {
	this.dr4lvOid = dr4lvOid;
    }

    /**
     * @return the dr5lvOid
     */
    public String getDr5lvOid() {
	return dr5lvOid;
    }

    /**
     * @param dr5lvOid
     *            the dr5lvOid to set
     */
    public void setDr5lvOid(String dr5lvOid) {
	this.dr5lvOid = dr5lvOid;
    }

    /**
     * @return the dr6lvOid
     */
    public String getDr6lvOid() {
	return dr6lvOid;
    }

    /**
     * @param dr6lvOid
     *            the dr6lvOid to set
     */
    public void setDr6lvOid(String dr6lvOid) {
	this.dr6lvOid = dr6lvOid;
    }

    /**
     * @return the dr0lvProfitRate
     */
    public String getDr0lvProfitRate() {
	return dr0lvProfitRate;
    }

    /**
     * @param dr0lvProfitRate
     *            the dr0lvProfitRate to set
     */
    public void setDr0lvProfitRate(String dr0lvProfitRate) {
	this.dr0lvProfitRate = dr0lvProfitRate;
    }

    /**
     * @return the dr1lvProfitRate
     */
    public String getDr1lvProfitRate() {
	return dr1lvProfitRate;
    }

    /**
     * @param dr1lvProfitRate
     *            the dr1lvProfitRate to set
     */
    public void setDr1lvProfitRate(String dr1lvProfitRate) {
	this.dr1lvProfitRate = dr1lvProfitRate;
    }

    /**
     * @return the dr2lvProfitRate
     */
    public String getDr2lvProfitRate() {
	return dr2lvProfitRate;
    }

    /**
     * @param dr2lvProfitRate
     *            the dr2lvProfitRate to set
     */
    public void setDr2lvProfitRate(String dr2lvProfitRate) {
	this.dr2lvProfitRate = dr2lvProfitRate;
    }

    /**
     * @return the dr3lvProfitRate
     */
    public String getDr3lvProfitRate() {
	return dr3lvProfitRate;
    }

    /**
     * @param dr3lvProfitRate
     *            the dr3lvProfitRate to set
     */
    public void setDr3lvProfitRate(String dr3lvProfitRate) {
	this.dr3lvProfitRate = dr3lvProfitRate;
    }

    /**
     * @return the dr4lvProfitRate
     */
    public String getDr4lvProfitRate() {
	return dr4lvProfitRate;
    }

    /**
     * @param dr4lvProfitRate
     *            the dr4lvProfitRate to set
     */
    public void setDr4lvProfitRate(String dr4lvProfitRate) {
	this.dr4lvProfitRate = dr4lvProfitRate;
    }

    /**
     * @return the dr5lvProfitRate
     */
    public String getDr5lvProfitRate() {
	return dr5lvProfitRate;
    }

    /**
     * @param dr5lvProfitRate
     *            the dr5lvProfitRate to set
     */
    public void setDr5lvProfitRate(String dr5lvProfitRate) {
	this.dr5lvProfitRate = dr5lvProfitRate;
    }

    /**
     * @return the dr6lvProfitRate
     */
    public String getDr6lvProfitRate() {
	return dr6lvProfitRate;
    }

    /**
     * @param dr6lvProfitRate
     *            the dr6lvProfitRate to set
     */
    public void setDr6lvProfitRate(String dr6lvProfitRate) {
	this.dr6lvProfitRate = dr6lvProfitRate;
    }

    /**
     * @return the cpMapList
     */
    public List<Map<String, Object>> getCpMapList() {
	return cpMapList;
    }

    /**
     * @param cpMapList
     *            the cpMapList to set
     */
    public void setCpMapList(List<Map<String, Object>> cpMapList) {
	this.cpMapList = cpMapList;
    }

    /**
     * @return the partOid
     */
    public String getPartOid() {
	return partOid;
    }

    public String getRealPartNo() {
	return realPartNo;
    }

    public void setRealPartNo(String realPartNo) {
	this.realPartNo = realPartNo;
    }

    /**
     * @return the version
     */
    public String getVersion() {
	return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(String version) {
	this.version = version;
    }

    /**
     * @return the costPartNos
     */
    public String getCostPartNos() {
	return costPartNos;
    }

    /**
     * @param costPartNos
     *            the costPartNos to set
     */
    public void setCostPartNos(String costPartNos) {
	this.costPartNos = costPartNos;
    }

    /**
     * @return the pjtNos
     */
    public String getPjtNos() {
	return pjtNos;
    }

    /**
     * @param pjtNos
     *            the pjtNos to set
     */
    public void setPjtNos(String pjtNos) {
	this.pjtNos = pjtNos;
    }

}
