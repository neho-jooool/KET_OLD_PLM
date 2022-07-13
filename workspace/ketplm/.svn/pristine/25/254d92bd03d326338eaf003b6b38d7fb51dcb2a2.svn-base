package ext.ket.wfm.entity;

import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.project.E3PSTask;
import e3ps.project.TemplateProject;
import e3ps.project.beans.E3PSTaskData;
import ext.ket.shared.dto.BaseDTO;

/**
 * @클래스명 : MyTaskDTO
 * @작성자 : Jason, Han
 * @작성일 : 2014. 7. 9.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class MyTaskDTO extends BaseDTO {

    private static final long serialVersionUID = -1674204638826648130L;

    private String pjtOid;
    private String pjtType;
    private String pjtNo;
    private String pjtName;
    private String taskName;
    private String taskPlanStartDate;
    private String taskPlanEndDate;
    private String planStartStartDate;
    private String planStartEndDate;
    private String planEndStartDate;
    private String planEndEndDate;
    private String taskStatus;
    private String pjtStatus;
    private String taskCompletion;
    private String searchPjtState;
    private String searchState;
    private String searchType;
    private String taskType;
    private static String searchPjtType;
    private boolean onlyResult = false;
    private int totalSize;

    public MyTaskDTO() {
    }

    public MyTaskDTO(E3PSTask task) throws Exception {

	KETMessageService messageService = KETMessageService.getMessageService();
	String oid = CommonUtil.getOIDString(task);
	TemplateProject project = task.getProject();
	String pjtOid = "";
	String pjtType = "";
	String pjtNo = "";
	String pjtName = project.getPjtName();
	if (project != null) {
	    pjtOid = CommonUtil.getOIDString(project);
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
	    pjtNo = project.getPjtNo();
	    pjtName = project.getPjtName();
	}

	E3PSTaskData taskData = new E3PSTaskData(task);
	String taskName = taskData.taskName;
	String taskPlanStartDateStr = DateUtil.getDateString(taskData.taskPlanStartDate, "D");
	String taskPlanEndDatStr = DateUtil.getDateString(taskData.taskPlanEndDate, "D");
	String taskStatus = taskData.getStateStr();
	String pjtStatus = "";
	String taskCompletion = "";
	if (task.getTaskState() == 0) {
	    pjtStatus = messageService.getString("e3ps.message.ket_message", "02735");// 진행중
	    taskCompletion = "<span class=\"b-small box-size\" onclick=\"javascript:MyTask.doTaskCompletion('" + oid
		    + "');\" style=\"cursor: hand;\">" + messageService.getString("e3ps.message.ket_message", "02171")/* 완료 */+ "</span>";
	} else if (task.getTaskState() == 5) {
	    pjtStatus = messageService.getString("e3ps.message.ket_message", "02173");// 완료됨
	}
	String taskType = task.getTaskType();

	setOid(oid);
	setPjtOid(pjtOid);
	setPjtType(pjtType);
	setPjtNo(pjtNo);
	setPjtName(pjtName);
	setTaskName(taskName);
	setTaskPlanStartDate(taskPlanStartDateStr);
	setTaskPlanEndDate(taskPlanEndDatStr);
	setTaskStatus(taskStatus);
	setPjtStatus(pjtStatus);
	setTaskCompletion(taskCompletion);
	setTaskType(taskType);

    }

    public String getTaskNameHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getTaskNameHtmlPostfix() {
	return "</font>";
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
     * @return the taskName
     */
    public String getTaskName() {
	return taskName;
    }

    /**
     * @param taskName
     *            the taskName to set
     */
    public void setTaskName(String taskName) {
	this.taskName = taskName;
    }

    /**
     * @return the taskPlanStartDate
     */
    public String getTaskPlanStartDate() {
	return taskPlanStartDate;
    }

    /**
     * @param taskPlanStartDate
     *            the taskPlanStartDate to set
     */
    public void setTaskPlanStartDate(String taskPlanStartDate) {
	this.taskPlanStartDate = taskPlanStartDate;
    }

    /**
     * @return the taskPlanEndDate
     */
    public String getTaskPlanEndDate() {
	return taskPlanEndDate;
    }

    /**
     * @param taskPlanEndDate
     *            the taskPlanEndDate to set
     */
    public void setTaskPlanEndDate(String taskPlanEndDate) {
	this.taskPlanEndDate = taskPlanEndDate;
    }

    /**
     * @return the taskStatus
     */
    public String getTaskStatus() {
	return taskStatus;
    }

    /**
     * @param taskStatus
     *            the taskStatus to set
     */
    public void setTaskStatus(String taskStatus) {
	this.taskStatus = taskStatus;
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
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
	return serialVersionUID;
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
     * @return the pjtOid
     */
    public String getPjtOid() {
	return pjtOid;
    }

    /**
     * @param pjtOid
     *            the pjtOid to set
     */
    public void setPjtOid(String pjtOid) {
	this.pjtOid = pjtOid;
    }

    /**
     * @param planEndEndDate
     *            the planEndEndDate to set
     */
    public void setPlanEndEndDate(String planEndEndDate) {
	this.planEndEndDate = planEndEndDate;
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
     * @return the searchState
     */
    public String getSearchState() {
	return searchState;
    }

    /**
     * @param searchState
     *            the searchState to set
     */
    public void setSearchState(String searchState) {
	this.searchState = searchState;
    }

    /**
     * @return the taskCompletion
     */
    public String getTaskCompletion() {
	return taskCompletion;
    }

    /**
     * @param taskCompletion
     *            the taskCompletion to set
     */
    public void setTaskCompletion(String taskCompletion) {
	this.taskCompletion = taskCompletion;
    }

    /**
     * @return the searchType
     */
    public String getSearchType() {
	return searchType;
    }

    /**
     * @param searchType
     *            the searchType to set
     */
    public void setSearchType(String searchType) {
	this.searchType = searchType;
    }

    /**
     * @return the taskType
     */
    public String getTaskType() {
	return taskType;
    }

    /**
     * @param taskType
     *            the taskType to set
     */
    public void setTaskType(String taskType) {
	this.taskType = taskType;
    }

    /**
     * @return the searchPjtType
     */
    public static String getSearchPjtType() {
	return searchPjtType;
    }

    /**
     * @param searchPjtType
     *            the searchPjtType to set
     */
    public void setSearchPjtType(String searchPjtType) {
	this.searchPjtType = searchPjtType;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public boolean isOnlyResult() {
        return onlyResult;
    }

    public void setOnlyResult(boolean onlyResult) {
        this.onlyResult = onlyResult;
    }
    
    
}
