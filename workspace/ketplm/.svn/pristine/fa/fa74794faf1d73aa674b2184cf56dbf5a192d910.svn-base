package ext.ket.wfm.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import e3ps.common.message.KETMessageService;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.project.E3PSProject;
import e3ps.project.ExtendScheduleData;
import e3ps.project.ProjectSubcontractorLink;
import e3ps.project.beans.ProjectStateFlag;
import ext.ket.project.program.service.ProgramHelper;
import ext.ket.shared.dto.BaseDTO;

/**
 * @클래스명 : MyProjectDTO
 * @작성자 : Jason, Han
 * @작성일 : 2014. 7. 15.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class MyProjectDTO extends BaseDTO {

    private static final long serialVersionUID = 8107700339362318404L;

    private String	    pjtType;
    private String	    pjtNo;
    private String	    pjtName;

    private String	    planStartStartDate;
    private String	    planStartEndDate;
    private String	    planEndStartDate;
    private String	    planEndEndDate;

    private String	    buyer;
    private String	    pjtPlanStartDate;
    private String	    pjtPlanEndDate;
    private String	    pjtStatus;
    private String	    pjtState;

    private String	    searchPjtName;
    private String	    searchPjtNo;
    private String	    searchPjtState;
    private String	    searchPjtType;
    private String          p1_contact_date;
    private String          p2_contact_date;
    private String          sop_contact_date;
    private String          isPm;
    private boolean onlyResult = false;
    private int totalSize;

    public MyProjectDTO() {
    }

    public MyProjectDTO(E3PSProject project) throws Exception {

	KETMessageService messageService = KETMessageService.getMessageService();

	String pjtType = "";
	String pjtNo = project.getPjtNo();
	String pjtName = project.getPjtName();
	String buyer = getSubcontractor(project);
	ExtendScheduleData scheduleData = (ExtendScheduleData) project.getPjtSchedule().getObject();
	String pjtPlanStartDate = DateUtil.getDateString(scheduleData.getPlanStartDate(), "d");
	String pjtPlanEndDate = DateUtil.getDateString(scheduleData.getPlanEndDate(), "d");
	String pjtStatus = getStateStr(project, false);
	String pjtState = project.getLifeCycleState().getDisplay(messageService.getLocale());

	if ("검토".equals(project.getPjtTypeName())) {
	    pjtType = messageService.getString("e3ps.message.ket_message", "00716");// 검토
	} else if ("제품".equals(project.getPjtTypeName())) {
	    pjtType = messageService.getString("e3ps.message.ket_message", "02536");// 제품
	} else if ("금형".equals(project.getPjtTypeName())) {
	    pjtType = messageService.getString("e3ps.message.ket_message", "00997");// 금형
	} else if ("업무".equals(project.getPjtTypeName())) {
	    pjtType = "업무";
	}

	setOid(CommonUtil.getOIDString(project));
	setPjtType(pjtType);
	setPjtNo(pjtNo);
	setPjtName(pjtName);
	setBuyer(buyer);
	setPjtPlanStartDate(pjtPlanStartDate);
	setPjtPlanEndDate(pjtPlanEndDate);
	setPjtStatus(pjtStatus);
	setPjtState(pjtState);
	List<HashMap<String, String>> list = ProgramHelper.service.findContactEventByproject(getOid());//프로그램의 접점고객 일정 찾기
	
	for(HashMap<String, String> hash : list){
	    if(StringUtils.isNotEmpty(hash.get("p1_contact_date"))){
		setP1_contact_date(hash.get("p1_contact_date"));
	    }
	    if(StringUtils.isNotEmpty(hash.get("p2_contact_date"))){
		setP2_contact_date(hash.get("p2_contact_date"));
	    }
	    if(StringUtils.isNotEmpty(hash.get("sop_contact_date"))){
		setSop_contact_date(hash.get("sop_contact_date"));
	    }
	}
    }

    private String getSubcontractor(E3PSProject project) throws Exception {

	String rtnStr = "";
	QuerySpec psspec = new QuerySpec(ProjectSubcontractorLink.class);
	SearchUtil.appendEQUAL(psspec, ProjectSubcontractorLink.class, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(project), 0);
	QueryResult psresult = PersistenceHelper.manager.find((StatementSpec) psspec);
	while (psresult != null && psresult.hasMoreElements()) {
	    ProjectSubcontractorLink link = (ProjectSubcontractorLink) psresult.nextElement();
	    if (link != null) {

		rtnStr = rtnStr + link.getSubcontractor().getName();
		if (psresult.hasMoreElements())
		    rtnStr = rtnStr + ", ";
	    }
	}
	return rtnStr;
    }

    private String getStateStr(E3PSProject project, boolean withState) throws Exception {

	String state = project.getLifeCycleState().toString();
	String stateKR = project.getLifeCycleState().getDisplay(Locale.KOREA);

	if (state.equals("COMPLETED")) {
	    // 사전개발 게획 일 경우는 상태를 다르게 표시
	    // if (this.jelProject.isPre())
	    // return this.jelProject.getPreProjectState();
	    // else

	    String returnStr = "<table><tr title='종료되었습니다'>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
		    + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
		    + "/state_green_bar.gif></td>";
	    if (withState) {
		returnStr += "<td>(" + StringUtil.checkNull(stateKR) + ")</td>";
	    }
	    returnStr += "</tr></table>";
	    return returnStr;

	}

	String typeStateSelect = null;

	if (stateKR.equals("작업 중"))
	    typeStateSelect = "결재 중";
	else
	    typeStateSelect = stateKR;

	int pjtState = project.getPjtState();

	if (state.equals("PROGRESS")) {
	    if (pjtState == ProjectStateFlag.PROJECT_STATE_DELAY) {

		String returnStr = "<table><tr title=''>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
			+ "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_red_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
			+ "/state_blank_bar.gif></td>";
		if (withState) {
		    returnStr += "<td>(" + typeStateSelect + ")</td>";
		}
		returnStr += "</tr></table>";
		return returnStr;
	    } else if (pjtState == ProjectStateFlag.PROJECT_STATE_DELAY_PROGRESS) {

		String returnStr = "<table><tr title=''>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
			+ "/state_yellow_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
			+ "/state_blank_bar.gif></td>";
		if (withState) {
		    returnStr += "<td>(" + typeStateSelect + ")</td>";
		}
		returnStr += "</tr></table>";
		return returnStr;

	    } else if (pjtState == ProjectStateFlag.PROJECT_STATE_PROGRESS) {

		String returnStr = "<table><tr title=''>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blue_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
			+ "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
			+ "/state_blank_bar.gif></td>";
		if (withState) {
		    returnStr += "<td>(" + typeStateSelect + ")</td>";
		}
		returnStr += "</tr></table>";
		return returnStr;

	    }
	} else {
	    String returnStr = "<table><tr title=''>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
		    + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
		    + "/state_blank_bar.gif></td>";
	    if (withState) {
		returnStr += "<td>(" + typeStateSelect + ")</td>";
	    }
	    returnStr += "</tr></table>";
	    return returnStr;
	}

	String returnStr = "<table><tr title=''>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
		+ "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>";
	if (withState) {
	    returnStr += "<td>(" + typeStateSelect + ")</td>";
	}
	returnStr += "</tr></table>";
	return returnStr;

    }

    public String getPjtNoHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getPjtNoHtmlPostfix() {
	return "</font>";
    }

    /**
     * @return the pjtType
     */
    public String getPjtType() {
	return pjtType;
    }

    /**
     * @param pjtType
     *            the pjtType to set
     */
    public void setPjtType(String pjtType) {
	this.pjtType = pjtType;
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
     * @return the planStartStartDate
     */
    public String getPlanStartStartDate() {
	return planStartStartDate;
    }

    /**
     * @param planStartStartDate
     *            the planStartStartDate to set
     */
    public void setPlanStartStartDate(String planStartStartDate) {
	this.planStartStartDate = planStartStartDate;
    }

    /**
     * @return the planStartEndDate
     */
    public String getPlanStartEndDate() {
	return planStartEndDate;
    }

    /**
     * @param planStartEndDate
     *            the planStartEndDate to set
     */
    public void setPlanStartEndDate(String planStartEndDate) {
	this.planStartEndDate = planStartEndDate;
    }

    /**
     * @return the planEndStartDate
     */
    public String getPlanEndStartDate() {
	return planEndStartDate;
    }

    /**
     * @param planEndStartDate
     *            the planEndStartDate to set
     */
    public void setPlanEndStartDate(String planEndStartDate) {
	this.planEndStartDate = planEndStartDate;
    }

    /**
     * @return the planEndEndDate
     */
    public String getPlanEndEndDate() {
	return planEndEndDate;
    }

    /**
     * @param planEndEndDate
     *            the planEndEndDate to set
     */
    public void setPlanEndEndDate(String planEndEndDate) {
	this.planEndEndDate = planEndEndDate;
    }

    /**
     * @return the buyer
     */
    public String getBuyer() {
	return buyer;
    }

    /**
     * @param buyer
     *            the buyer to set
     */
    public void setBuyer(String buyer) {
	this.buyer = buyer;
    }

    /**
     * @return the pjtPlanStartDate
     */
    public String getPjtPlanStartDate() {
	return pjtPlanStartDate;
    }

    /**
     * @param pjtPlanStartDate
     *            the pjtPlanStartDate to set
     */
    public void setPjtPlanStartDate(String pjtPlanStartDate) {
	this.pjtPlanStartDate = pjtPlanStartDate;
    }

    /**
     * @return the pjtPlanEndDate
     */
    public String getPjtPlanEndDate() {
	return pjtPlanEndDate;
    }

    /**
     * @param pjtPlanEndDate
     *            the pjtPlanEndDate to set
     */
    public void setPjtPlanEndDate(String pjtPlanEndDate) {
	this.pjtPlanEndDate = pjtPlanEndDate;
    }

    /**
     * @return the pjtStatus
     */
    public String getPjtStatus() {
	return pjtStatus;
    }

    /**
     * @param pjtStatus
     *            the pjtStatus to set
     */
    public void setPjtStatus(String pjtStatus) {
	this.pjtStatus = pjtStatus;
    }

    /**
     * @return the pjtState
     */
    public String getPjtState() {
	return pjtState;
    }

    /**
     * @param pjtState
     *            the pjtState to set
     */
    public void setPjtState(String pjtState) {
	this.pjtState = pjtState;
    }

    /**
     * @return the searchPjtName
     */
    public String getSearchPjtName() {
	return searchPjtName;
    }

    /**
     * @param searchPjtName
     *            the searchPjtName to set
     */
    public void setSearchPjtName(String searchPjtName) {
	this.searchPjtName = searchPjtName;
    }

    /**
     * @return the searchPjtNo
     */
    public String getSearchPjtNo() {
	return searchPjtNo;
    }

    /**
     * @param searchPjtNo
     *            the searchPjtNo to set
     */
    public void setSearchPjtNo(String searchPjtNo) {
	this.searchPjtNo = searchPjtNo;
    }

    /**
     * @return the searchPjtState
     */
    public String getSearchPjtState() {
	return searchPjtState;
    }

    /**
     * @param searchPjtState
     *            the searchPjtState to set
     */
    public void setSearchPjtState(String searchPjtState) {
	this.searchPjtState = searchPjtState;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    /**
     * @return the searchPjtType
     */
    public String getSearchPjtType() {
	return searchPjtType;
    }

    /**
     * @param searchPjtType
     *            the searchPjtType to set
     */
    public void setSearchPjtType(String searchPjtType) {
	this.searchPjtType = searchPjtType;
    }

    public String getP1_contact_date() {
        return p1_contact_date;
    }

    public void setP1_contact_date(String p1_contact_date) {
        this.p1_contact_date = p1_contact_date;
    }

    public String getP2_contact_date() {
        return p2_contact_date;
    }

    public void setP2_contact_date(String p2_contact_date) {
        this.p2_contact_date = p2_contact_date;
    }

    public String getSop_contact_date() {
        return sop_contact_date;
    }

    public void setSop_contact_date(String sop_contact_date) {
        this.sop_contact_date = sop_contact_date;
    }
    
    public String getP1_contact_dateHtmlPrefix() {
	return "<font color=blue>";
    }

    public String getP1_contact_dateHtmlPostfix() {
	return "</font>";
    }
    
    public String getP2_contact_dateHtmlPrefix() {
	return "<font color=blue>";
    }

    public String getP2_contact_dateHtmlPostfix() {
	return "</font>";
    }
    
    public String getSop_contact_dateHtmlPrefix() {
	return "<font color=blue>";
    }

    public String getSop_contact_dateHtmlPostfix() {
	return "</font>";
    }

    public String getIsPm() {
        return isPm;
    }

    public void setIsPm(String isPm) {
        this.isPm = isPm;
    }

    public boolean isOnlyResult() {
        return onlyResult;
    }

    public void setOnlyResult(boolean onlyResult) {
        this.onlyResult = onlyResult;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }



}
