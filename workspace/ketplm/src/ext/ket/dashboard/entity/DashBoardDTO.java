package ext.ket.dashboard.entity;

import java.sql.Timestamp;
import java.util.Date;

import e3ps.common.message.KETMessageService;
import e3ps.common.util.DateUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.project.beans.ProjectStateFlag;
import ext.ket.shared.dto.BaseDTO;

public class DashBoardDTO extends BaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Boolean isRegistry;

    private String rnum;

    private int month; // 현재달
    private int year; // 현재년도
    private int num;
    private int budget;
    private int totalPrice;
    private int moldTotalPrice;
    private int company;
    private int outCompany;
    private int projectCount;
    private int taskCompleteCount;
    private int taskProcessCount;
    private int taskProcessDelayCount;
    private int taskTotalCount;
    private int qualityTotal;
    private int attchCount;
    private int ecoComplete;
    private int ecoProcess;
    private int defineCount;
    private int registryCount;
    private int issueNum;
    private int qulityNum;

    private int issueTeamNum;
    private int qulityTeamNum;

    private int taskProcessNum;
    private int taskDelayNum;
    private int pjtPlanNum;
    private int pjtProcessNum;
    private int pjtCompleteNum;
    private int pjtDelayNum;

    private int pjtTeamPlanNum;
    private int pjtTeamProcessNum;
    private int pjtTeamCompleteNum;
    private int pjtTeamDelayNum;

    private int assemblyCount;
    private int singleCount;
    private int goodsCount;

    private int moldCount;
    private int pressCount;

    private int prodCompCount;
    private int prodProcCount;
    private int prodDelayCount;
    private int reviewCompCount;
    private int reviewProcCount;
    private int reviewDelayCount;
    private int moldCompCount;
    private int moldProcCount;
    private int moldDelayCount;
    private int issueCount;
    private int quiltyCount;
    private int prodTotalCount;
    private int reviewTotalCount;
    private int moldTotalCount;

    private int typeByIn;
    private int typeByOut;
    private int typeByTotal;
    private int monthSetting;
    private String yearSetting;
    private int dqmProcessCount;
    private int dqmCompleteCount;

    private float targetCost;
    private float budgetCost;
    private float resultCost;
    private float budgetCost1;
    private float resultCost1;
    private float budgetCost2;
    private float resultCost2;
    private float budgetCost3;
    private float resultCost3;
    private float balanceCost;
    private float totalBudget;
    private float totalResult;

    private String oid;
    private String week; // 주차
    private String weekStart; // 주차 시작일
    private String weekEnd;// 주차 마지막일
    private String termType; // termType
    private String pjtNo; // 프로젝트NO
    private String pjtName; // 프로젝트명
    private String making; // 제작구분
    private String moldType; // 금형구분
    private String moldType1;
    private String dieNo; // Die.No.
    private String moldCategory; // 금형유형
    private String taskName; // Task명
    private String color; // 범례색깔
    private String startDate; // 휴무일
    private String endDate;
    private String mainSchePjtCount; // 주요일정 프로젝트수
    private String searchDate; // 주요일정 프로젝트 조회일;
    private String type;
    private String state;
    private String moldTypeCheck;
    private String moldCategoryCheck;
    private String outsourcing;
    private String step;
    private String departName;
    private String userName;
    private String reason;
    private String pjtState;
    private String stateState;
    private String debuging;
    private String subject;
    private String importance;
    private String urgency;
    private String issueType;
    private String isDone;
    private String issueOid;
    private String term;
    private String mOid;
    private String division;
    private String pjtType;
    private String pjtType1;
    private String pjtType2;
    private String partNo;
    private String sortType;
    private String devType;
    private String dateType;
    private String customCompany;
    private String lastCompany;
    private String initMoldType;
    private String initDivision;
    private String carName;
    private String rank;
    private String partName;
    private String makeCompany;
    private String total;
    private String complete;
    private String processRate;
    private String productionPlace;
    private String partnerNo;
    private String wOid;
    private String itemType;
    private String programName;
    private String programNo;
    private String carType;
    private String customer;
    private String pjtTypeName;
    private String taskState;
    private String taskUserName;
    private String objType;
    private String name;
    private String version;
    private String lastVersion;
    private String docOid;
    private String docOid1;
    private String outOid;
    private String problem;
    private String defectDivName;
    private String defectTypeName;
    private String occurName;
    private String occurDivName;
    private String dqmStateName;
    private String duty;
    private String currentDate;
    private String nextDate;
    private String beforeDate;
    private String currentType;
    private String nextType;
    private String beforeType;
    private String formType;
    private String assemType;
    private String ecrName;
    private String ecrId;
    private String deptName;
    private String ecoId;
    private String ecoName;
    private String expectCost;
    private String budgetCostStr;
    private String resultCostStr;
    private String displayName;
    private String displayTeam;
    private String displayTaskPlan;
    private String displayTaskProcess;
    private String displayTaskComplete;
    private String displayTaskDelay;

    private String displayTaskTeamPlan;
    private String displayTaskTeamProcess;
    private String displayTaskTeamComplete;
    private String displayTaskTeamDelay;

    private String carDate;
    private String shrinkage;
    private String useCompleteType;
    private String level2;
    private String pjtId;
    private String realTime;
    private String expectTime;
    private String ecrNumber;
    private String currentTaskName;
    private String currentGate;
    private String currentGateDate;
    private String beforeTaskName;
    private String beforeGate;
    private String beforeGateDate;
    private String nextTaskName;
    private String nextGate;
    private String nextGateDate;
    private String dieCategory;
    private String ecnNo;
    private String ecoNo;
    private String stateApproName;
    private String creator;
    private String receiveConfirm;
    private String jobComplete;
    private String assemblyPlaceType;
    private String divisionFlag;
    private String nOid;
    private String itDivision;
    private String typeDivide;
    private String pm;
    private String updateDivision;
    private String dieNo3;
    private String deptCode;
    private String status;
    private String devPattern;
    private String displayCode;
    private String rankS;
    private String rankR;
    private String rankA;
    private String rankB;
    private String rankC;
    private String rankSum;
    private String taskCnt;
    private String ecrCnt;
    private String ecoCnt;
    private String common;
    private String havework;
    private String havemaxwork;
    private String work_plan_project;
    private String work_plan_sum;
    private String work_load;
    private String departCode;
    private String userCode;
    private String period;

    private Date reviewDate;
    private Date requestDate;
    private Date occurDate;
    private Date planStartDate; // Task시작일
    private Date planEndDate; // Task완료일
    private Date execStartDate;
    private Date execEndDate;
    private Date createDate;
    private Date oemEndDate;
    private Date completeDate;
    private String jobCompleteDate;

    private String dashboardType;// 상황판 메일발송시 사용
    private String processGb; // 상황판 메일발송시 사용 (Pilot,Proto,TCAR..)

    private Timestamp sDate;
    private Timestamp eDate;

    private String[] vMaking; // 제작구분
    private String[] vMoldType; // 금형구분
    private String[] vMoldCategory; // 금형유형
    private String[] vDevType; // 개발구분
    private String[] vState; // 상태
    private String[] vLastCompany; // 최종사용처
    private String[] vDieCategory; // 금형분류
    private String[] vRank;
    private String[] vTaskCategory;
    private String[] vDevPattern; // 개발유형
    private String[] vdivision; // 사업부
    private String[] vduty; // 직급
    private String dataType; //타시스템에서 링크걸어사용시(예: 종합상황실)
    private String taskRev;
    private String taskResultAndon;
    
    private String trustResult;
    private String assessCheck;
    private String execenMon;
    private String unusualCnt;
    private String taskCategory;
    private String processCode; 
    private String trustCode;   
    
    private String[] vProcessCode; 
    private String[] vTrustCode; 
    
    public String getTaskRev() {
        return taskRev;
    }

    public void setTaskRev(String taskRev) {
        this.taskRev = taskRev;
    }

    public String getTaskResultAndon() {
        return taskResultAndon;
    }

    public void setTaskResultAndon(String taskResultAndon) {
        this.taskResultAndon= taskResultAndon;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getRnum() {
	return rnum;
    }

    public void setRnum(String rnum) {
	this.rnum = rnum;
    }

    public int getYear() {
	return year;
    }

    public void setYear(int year) {
	this.year = year;
    }

    public int getNum() {
	return num;
    }

    public void setNum(int num) {
	this.num = num;
    }

    public String getOid() {
	return oid;
    }

    public void setOid(String oid) {
	this.oid = oid;
    }

    public String getWeek() {
	return week;
    }

    public void setWeek(String week) {
	this.week = week;
    }

    public String getWeekStart() {
	return weekStart;
    }

    public void setWeekStart(String weekStart) {
	this.weekStart = weekStart;
    }

    public String getWeekEnd() {
	return weekEnd;
    }

    public void setWeekEnd(String weekEnd) {
	this.weekEnd = weekEnd;
    }

    public int getMonth() {
	return month;
    }

    public void setMonth(int month) {
	this.month = month;
    }

    public String getTermType() {
	return termType;
    }

    public void setTermType(String termType) {
	this.termType = termType;
    }

    public String getPjtNo() {
	return pjtNo;
    }

    public void setPjtNo(String pjtNo) {
	this.pjtNo = pjtNo;
    }

    public String getPjtName() {
	return pjtName;
    }

    public void setPjtName(String pjtName) {
	this.pjtName = pjtName;
    }

    public String getMaking() {
	return making;
    }

    public void setMaking(String making) {
	this.making = making;
    }

    public String getMoldType() {
	return moldType;
    }

    public void setMoldType(String moldType) {
	this.moldType = moldType;
    }

    public String getDieNo() {
	return dieNo;
    }

    public void setDieNo(String dieNo) {
	this.dieNo = dieNo;
    }

    public String getMoldCategory() {
	return moldCategory;
    }

    public void setMoldCategory(String moldCategory) {
	this.moldCategory = moldCategory;
    }

    public String getTaskName() {
	return taskName;
    }

    public void setTaskName(String taskName) {
	this.taskName = taskName;
    }

    public String getColor() {
	return color;
    }

    public void setColor(String color) {
	this.color = color;
    }

    public String getMainSchePjtCount() {
	return mainSchePjtCount;
    }

    public void setMainSchePjtCount(String mainSchePjtCount) {
	this.mainSchePjtCount = mainSchePjtCount;
    }

    public Date getPlanStartDate() {
	return planStartDate;
    }

    public void setPlanStartDate(Date planStartDate) {
	this.planStartDate = planStartDate;
    }

    public Date getPlanEndDate() {
	return planEndDate;
    }

    public void setPlanEndDate(Date planEndDate) {
	this.planEndDate = planEndDate;
    }

    public String getSearchDate() {
	return searchDate;
    }

    public void setSearchDate(String searchDate) {
	this.searchDate = searchDate;
    }

    public String getSortType() {
	return sortType;
    }

    public void setSortType(String sortType) {
	this.sortType = sortType;
    }

    public String getStartDate() {
	return startDate;
    }

    public void setStartDate(String startDate) {
	this.startDate = startDate;
    }

    public String[] getvMaking() {
	return vMaking;
    }

    public void setvMaking(String[] vMaking) {
	this.vMaking = vMaking;
    }

    public String[] getvMoldType() {
	return vMoldType;
    }

    public void setvMoldType(String[] vMoldType) {
	this.vMoldType = vMoldType;
    }

    public String[] getvMoldCategory() {
	return vMoldCategory;
    }

    public void setvMoldCategory(String[] vMoldCategory) {
	this.vMoldCategory = vMoldCategory;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getMoldTypeCheck() {
	return moldTypeCheck;
    }

    public void setMoldTypeCheck(String moldTypeCheck) {
	this.moldTypeCheck = moldTypeCheck;
    }

    public String getMoldCategoryCheck() {
	return moldCategoryCheck;
    }

    public void setMoldCategoryCheck(String moldCategoryCheck) {
	this.moldCategoryCheck = moldCategoryCheck;
    }

    public String getOutsourcing() {
	return outsourcing;
    }

    public void setOutsourcing(String outsourcing) {
	this.outsourcing = outsourcing;
    }

    public String getStep() {
	return step;
    }

    public void setStep(String step) {
	this.step = step;
    }

    public String getDepartName() {
	return departName;
    }

    public void setDepartName(String departName) {
	this.departName = departName;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public Date getExecStartDate() {
	return execStartDate;
    }

    public void setExecStartDate(Date execStartDate) {
	this.execStartDate = execStartDate;
    }

    public String getReason() {
	String str = "";

	if ("1".equals(this.reason)) {
	    str = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "00850");
	} else if ("2".equals(this.reason)) {
	    str = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "03027");
	} else if ("3".equals(this.reason)) {
	    str = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02601");
	} else if ("4".equals(this.reason)) {
	    str = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01063");
	} else if ("5".equals(this.reason)) {
	    str = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01136");
	} else if ("6".equals(this.reason)) {
	    str = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01784");
	}

	return str;
    }

    public void setReason(String reason) {
	this.reason = reason;
    }

    public String getPjtState() {
	return pjtState;
    }

    public void setPjtState(String pjtState) {
	this.pjtState = pjtState;
    }

    public String getStateState() {
	String str = "";

	if ("PMOINWORK".equals(this.stateState)) {
	    str = "등록중";
	} else if ("WITHDRAWN".equals(this.stateState)) {
	    str = "취소";
	} else if ("STOPED".equals(this.stateState)) {
	    str = "중지";
	} else if ("REWORK".equals(this.stateState)) {
	    str = "재작업";
	} else if ("CANCELING".equals(this.stateState)) {
	    str = "취소중";
	} else if ("UNDERREVIEW".equals(this.stateState)) {
	    str = "검토중";
	} else if ("PLANCHANGE".equals(this.stateState)) {
	    str = "일정변경";
	} else if ("PROGRESS".equals(this.stateState)) {
	    str = "진행";
	} else if ("COMPLETED".equals(this.stateState)) {
	    str = "완료";
	} else if ("DELAY".equals(this.stateState)) {
	    str = "지연";
	} else if ("APPROVED".equals(this.stateState)) {
	    str = "승인완료";
	} else if ("INWORK".equals(this.stateState)) {
	    str = "작업중";
	} else if ("REJECTED".equals(this.stateState)) {
	    str = "반려됨";
	}
	return str;
    }

    public void setStateState(String stateState) {
	this.stateState = stateState;
    }

    public String getDebuging() {
	return debuging;
    }

    public void setDebuging(String debuging) {
	this.debuging = debuging;
    }

    public String getSubject() {
	return subject;
    }

    public void setSubject(String subject) {
	this.subject = subject;
    }

    public String getImportance() {
	return importance;
    }

    public void setImportance(String importance) {
	this.importance = importance;
    }

    public String getUrgency() {
	return urgency;
    }

    public void setUrgency(String urgency) {
	this.urgency = urgency;
    }

    public String getIssueType() {
	String str = "";

	if ("PRODUCT".equals(this.issueType)) {
	    str = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02536");
	} else if ("MOLD".equals(this.issueType)) {
	    str = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "00997");
	} else if ("CUSTOMER".equals(this.issueType)) {
	    str = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "00828");
	} else if ("QUALITY".equals(this.issueType)) {
	    str = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "03024");
	} else if ("COST".equals(this.issueType)) {
	    str = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01640");
	} else if ("SCHEDULE".equals(this.issueType)) {
	    str = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02348");
	} else if ("ETC".equals(this.issueType)) {
	    str = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01136");
	}
	return str;
    }

    public void setIssueType(String issueType) {
	this.issueType = issueType;
    }

    public String getIsDone() {
	String str = "";

	if ("1".equals(this.isDone)) {
	    str = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02171");
	} else if ("0".equals(this.isDone)) {
	    str = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01454");
	}
	return str;
    }

    public void setIsDone(String isDone) {
	this.isDone = isDone;
    }

    public Date getCreateDate() {
	return createDate;
    }

    public void setCreateDate(Date createDate) {
	this.createDate = createDate;
    }

    public String getIssueOid() {
	return issueOid;
    }

    public void setIssueOid(String issueOid) {
	this.issueOid = issueOid;
    }

    public int getBudget() {
	return budget;
    }

    public void setBudget(int budget) {
	this.budget = budget;
    }

    public int getTotalPrice() {
	return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
	this.totalPrice = totalPrice;
    }

    public Date getExecEndDate() {
	return execEndDate;
    }

    public void setExecEndDate(Date execEndDate) {
	this.execEndDate = execEndDate;
    }

    public String getTerm() {
	return term;
    }

    public void setTerm(String term) {
	this.term = term;
    }

    public String getmOid() {
	return mOid;
    }

    public void setmOid(String mOid) {
	this.mOid = mOid;
    }

    public int getMoldTotalPrice() {
	return moldTotalPrice;
    }

    public void setMoldTotalPrice(int moldTotalPrice) {
	this.moldTotalPrice = moldTotalPrice;
    }

    public String getDisplayState() {
	String str = "";
	return str;
    }

    public String getDisplayStateHtmlPrefix() {
	String str = "";

	if ("COMPLETED".equals(this.getStateState())) {
	    str = "<img src='" + ProjectStateFlag.iconUrl + "/state_green_bar.gif'>";
	} else if ("PROGRESS".equals(this.getStateState())) {
	    if ("4".equals(this.getPjtState())) {
		str = "<img src='" + ProjectStateFlag.iconUrl + "/state_red_bar.gif' />";
	    } else if ("3".equals(this.getPjtState())) {
		str = "<img src='" + ProjectStateFlag.iconUrl + "/state_yellow_bar.gif' />";
	    } else {
		str = "<img src='" + ProjectStateFlag.iconUrl + "/state_blue_bar.gif' />";
	    }
	} else {
	    str = "<img src='" + ProjectStateFlag.iconUrl + "/state_blue_bar.gif' />";
	}

	return str;

    }

    public String getDisplayOccurDate() {
	String str = "";
	str = DateUtil.getDateString(this.getOccurDate(), "all");

	return str;
    }

    public String getDisplayPlanStartDate() {

	String str = "";
	str = DateUtil.getDateString(this.getPlanStartDate(), "all");

	return str;
    }

    public String getDisplayRequestDate() {

	String str = "";
	str = DateUtil.getDateString(this.getRequestDate(), "all");

	return str;
    }

    public String getDisplayCompleteDate() {

	String str = "";
	str = DateUtil.getDateString(this.getCompleteDate(), "all");

	return str;
    }

    public String getDisplayPlanEndDate() {

	String str = "";
	str = DateUtil.getDateString(this.getPlanEndDate(), "all");

	return str;
    }

    public String getDisplayExecEndDate() {

	String str = "";
	str = DateUtil.getDateString(this.getExecEndDate(), "all");

	return str;
    }

    public String getDisplayCreateDate() {
	String str = "";
	str = DateUtil.getDateString(this.getCreateDate(), "all");

	return str;
    }

    public String getDisplayStateHtmlPostfix() {
	return "";
    }

    public String getPjtNoHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getPjtNoHtmlPostfix() {
	return "</font>";
    }

    public String getSubjectHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getSubjectHtmlPostfix() {
	return "</font>";
    }

    public String getProgramNameHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getProgramNameHtmlPostfix() {
	return "</font>";
    }

    public String getProjectCountHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getProjectCountHtmlPostfix() {
	return "</font>";
    }

    public String getTaskNameHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getTaskNameHtmlPostfix() {
	return "</font>";
    }

    public String getDisplayNameHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getDisplayNameHtmlPostfix() {
	return "</font>";
    }

    public String getDisplayTaskProcessHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getDisplayTaskProcessHtmlPostfix() {
	return "</font>";
    }

    public String getDisplayTaskDelayHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getDisplayTaskDelayHtmlPostfix() {
	return "</font>";
    }

    public String getMoldCountHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getMoldCountHtmlPostfix() {
	return "</font>";
    }

    public String getPressCountHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getPressCountHtmlPostfix() {
	return "</font>";
    }

    public String getIssueNumHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getIssueNumHtmlPostfix() {
	return "</font>";
    }

    public String getPjtProcessNumHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getPjtProcessNumHtmlPostfix() {
	return "</font>";
    }

    public String getPjtDelayNumHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getPjtDelayNumHtmlPostfix() {
	return "</font>";
    }

    public String getQulityNumHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getQulityNumHtmlPostfix() {
	return "</font>";
    }

    public String getEcoIdHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getEcoIdHtmlPostfix() {
	return "</font>";
    }

    public String getEcrIdHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getEcrIdHtmlPostfix() {
	return "</font>";
    }

    public String getEcnNoHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getEcnNoHtmlPostfix() {
	return "</font>";
    }

    public String getEcoNoHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getEcoNoHtmlPostfix() {
	return "</font>";
    }

    public String getProblemHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getProblemHtmlPostfix() {
	return "</font>";
    }

    public String getPjtPlanNumHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getPjtPlanNumHtmlPostfix() {
	return "</font>";
    }

    public String getPjtCompleteNumHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getPjtCompleteNumHtmlPostfix() {
	return "</font>";
    }

    public String getDisplayTaskPlanHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getDisplayTaskPlanHtmlPostfix() {
	return "</font>";
    }

    public String getDisplayTaskCompleteHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getDisplayTaskCompleteHtmlPostfix() {
	return "</font>";
    }

    public String getPartNoHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getPartNoHtmlPostfix() {
	return "</font>";
    }

    public String getDieNoHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getDieNoHtmlPostfix() {
	return "</font>";
    }

    public String getDivision() {
	return division;
    }

    public void setDivision(String division) {
	this.division = division;
    }

    public String getPjtType() {
	return pjtType;
    }

    public void setPjtType(String pjtType) {
	this.pjtType = pjtType;
    }

    public String getPartNo() {
	return partNo;
    }

    public void setPartNo(String partNo) {
	this.partNo = partNo;
    }

    public String getDevType() {
	return devType;
    }

    public void setDevType(String devType) {
	this.devType = devType;
    }

    public String[] getvDevType() {
	return vDevType;
    }

    public void setvDevType(String[] vDevType) {
	this.vDevType = vDevType;
    }

    public String[] getvState() {
	return vState;
    }

    public void setvState(String[] vState) {
	this.vState = vState;
    }

    public String[] getvLastCompany() {
	return vLastCompany;
    }

    public void setvLastCompany(String[] vLastCompany) {
	this.vLastCompany = vLastCompany;
    }

    public String[] getVdivision() {
	return vdivision;
    }

    public void setVdivision(String[] vdivision) {
	this.vdivision = vdivision;
    }

    public String[] getVduty() {
	return vduty;
    }

    public void setVduty(String[] vduty) {
	this.vduty = vduty;
    }

    public String getDateType() {
	return dateType;
    }

    public void setDateType(String dateType) {
	this.dateType = dateType;
    }

    public String getEndDate() {
	return endDate;
    }

    public void setEndDate(String endDate) {
	this.endDate = endDate;
    }

    public String getCustomCompany() {
	return customCompany;
    }

    public void setCustomCompany(String customCompany) {
	this.customCompany = customCompany;
    }

    public String getLastCompany() {
	return lastCompany;
    }

    public void setLastCompany(String lastCompany) {
	this.lastCompany = lastCompany;
    }

    public String getInitMoldType() {
	return initMoldType;
    }

    public void setInitMoldType(String initMoldType) {
	this.initMoldType = initMoldType;
    }

    public String getInitDivision() {
	return initDivision;
    }

    public void setInitDivision(String initDivision) {
	this.initDivision = initDivision;
    }

    public String getCarName() {
	return carName;
    }

    public void setCarName(String carName) {
	this.carName = carName;
    }

    public String getRank() {
	return rank;
    }

    public void setRank(String rank) {
	this.rank = rank;
    }

    public String getPartName() {
	return partName;
    }

    public void setPartName(String partName) {
	this.partName = partName;
    }

    public String getMakeCompany() {
	return makeCompany;
    }

    public void setMakeCompany(String makeCompany) {
	this.makeCompany = makeCompany;
    }

    public String[] getvDieCategory() {
	return vDieCategory;
    }

    public void setvDieCategory(String[] vDieCategory) {
	this.vDieCategory = vDieCategory;
    }

    public Date getOemEndDate() {
	return oemEndDate;
    }

    public void setOemEndDate(Date oemEndDate) {
	this.oemEndDate = oemEndDate;
    }

    public String getTotal() {
	return total;
    }

    public void setTotal(String total) {
	this.total = total;
    }

    public String getComplete() {
	return complete;
    }

    public void setComplete(String complete) {
	this.complete = complete;
    }

    public String getProcessRate() {
	return processRate;
    }

    public void setProcessRate(String processRate) {
	this.processRate = processRate;
    }

    public void setBalanceCost(long balanceCost) {
	this.balanceCost = balanceCost;
    }

    public String getProductionPlace() {
	return productionPlace;
    }

    public void setProductionPlace(String productionPlace) {
	this.productionPlace = productionPlace;
    }

    public int getCompany() {
	return company;
    }

    public void setCompany(int company) {
	this.company = company;
    }

    public int getOutCompany() {
	return outCompany;
    }

    public void setOutCompany(int outCompany) {
	this.outCompany = outCompany;
    }

    public String getPartnerNo() {
	return partnerNo;
    }

    public void setPartnerNo(String partnerNo) {
	this.partnerNo = partnerNo;
    }

    public String getwOid() {
	return wOid;
    }

    public void setwOid(String wOid) {
	this.wOid = wOid;
    }

    public String getItemType() {
	return itemType;
    }

    public void setItemType(String itemType) {
	this.itemType = itemType;
    }

    public String getProgramName() {
	return programName;
    }

    public void setProgramName(String programName) {
	this.programName = programName;
    }

    public String getProgramNo() {
	return programNo;
    }

    public void setProgramNo(String programNo) {
	this.programNo = programNo;
    }

    public int getProjectCount() {
	return projectCount;
    }

    public void setProjectCount(int projectCount) {
	this.projectCount = projectCount;
    }

    public int getAssemblyCount() {
	return assemblyCount;
    }

    public void setAssemblyCount(int assemblyCount) {
	this.assemblyCount = assemblyCount;
    }

    public int getSingleCount() {
	return singleCount;
    }

    public void setSingleCount(int singleCount) {
	this.singleCount = singleCount;
    }

    public int getGoodsCount() {
	return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
	this.goodsCount = goodsCount;
    }

    public int getMoldCount() {
	return moldCount;
    }

    public void setMoldCount(int moldCount) {
	this.moldCount = moldCount;
    }

    public int getPressCount() {
	return pressCount;
    }

    public void setPressCount(int pressCount) {
	this.pressCount = pressCount;
    }

    public int getTaskCompleteCount() {
	return taskCompleteCount;
    }

    public void setTaskCompleteCount(int taskCompleteCount) {
	this.taskCompleteCount = taskCompleteCount;
    }

    public int getTaskProcessCount() {
	return taskProcessCount;
    }

    public void setTaskProcessCount(int taskProcessCount) {
	this.taskProcessCount = taskProcessCount;
    }

    public int getTaskProcessDelayCount() {
	return taskProcessDelayCount;
    }

    public void setTaskProcessDelayCount(int taskProcessDelayCount) {
	this.taskProcessDelayCount = taskProcessDelayCount;
    }

    public int getTaskTotalCount() {
	return taskTotalCount;
    }

    public void setTaskTotalCount(int taskTotalCount) {
	this.taskTotalCount = taskTotalCount;
    }

    public int getQualityTotal() {
	return qualityTotal;
    }

    public void setQualityTotal(int qualityTotal) {
	this.qualityTotal = qualityTotal;
    }

    public String getCarType() {
	return carType;
    }

    public void setCarType(String carType) {
	this.carType = carType;
    }

    public String getCustomer() {
	return customer;
    }

    public void setCustomer(String customer) {
	this.customer = customer;
    }

    public String getPjtTypeName() {
	return pjtTypeName;
    }

    public void setPjtTypeName(String pjtTypeName) {
	this.pjtTypeName = pjtTypeName;
    }

    public String getTaskState() {
	return taskState;
    }

    public void setTaskState(String taskState) {
	this.taskState = taskState;
    }

    public String getTaskUserName() {
	return taskUserName;
    }

    public void setTaskUserName(String taskUserName) {
	this.taskUserName = taskUserName;
    }

    public int getAttchCount() {
	return attchCount;
    }

    public void setAttchCount(int attchCount) {
	this.attchCount = attchCount;
    }

    public int getEcoComplete() {
	return ecoComplete;
    }

    public void setEcoComplete(int ecoComplete) {
	this.ecoComplete = ecoComplete;
    }

    public int getEcoProcess() {
	return ecoProcess;
    }

    public void setEcoProcess(int ecoProcess) {
	this.ecoProcess = ecoProcess;
    }

    public int getDefineCount() {
	return defineCount;
    }

    public void setDefineCount(int defineCount) {
	this.defineCount = defineCount;
    }

    public int getRegistryCount() {
	return registryCount;
    }

    public void setRegistryCount(int registryCount) {
	this.registryCount = registryCount;
    }

    public String getObjType() {
	return objType;
    }

    public void setObjType(String objType) {
	this.objType = objType;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Boolean getIsRegistry() {
	return isRegistry;
    }

    public void setIsRegistry(Boolean isRegistry) {
	this.isRegistry = isRegistry;
    }

    public String getVersion() {
	return version;
    }

    public void setVersion(String version) {
	this.version = version;
    }

    public String getLastVersion() {
	return lastVersion;
    }

    public void setLastVersion(String lastVersion) {
	this.lastVersion = lastVersion;
    }

    public String getDocOid() {
	return docOid;
    }

    public void setDocOid(String docOid) {
	this.docOid = docOid;
    }

    public String getOutOid() {
	return outOid;
    }

    public void setOutOid(String outOid) {
	this.outOid = outOid;
    }

    public String getDocOid1() {
	return docOid1;
    }

    public void setDocOid1(String docOid1) {
	this.docOid1 = docOid1;
    }

    public String getProblem() {
	return problem;
    }

    public void setProblem(String problem) {
	this.problem = problem;
    }

    public String getDefectDivName() {
	return defectDivName;
    }

    public void setDefectDivName(String defectDivName) {
	this.defectDivName = defectDivName;
    }

    public String getDefectTypeName() {
	return defectTypeName;
    }

    public void setDefectTypeName(String defectTypeName) {
	this.defectTypeName = defectTypeName;
    }

    public String getOccurName() {
	return occurName;
    }

    public void setOccurName(String occurName) {
	this.occurName = occurName;
    }

    public String getOccurDivName() {
	return occurDivName;
    }

    public void setOccurDivName(String occurDivName) {
	this.occurDivName = occurDivName;
    }

    public String getDqmStateName() {
	return dqmStateName;
    }

    public void setDqmStateName(String dqmStateName) {
	this.dqmStateName = dqmStateName;
    }

    public Date getReviewDate() {
	return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
	this.reviewDate = reviewDate;
    }

    public Date getRequestDate() {
	return requestDate;
    }

    public void setRequestDate(Date requestDate) {
	this.requestDate = requestDate;
    }

    public Date getOccurDate() {
	return occurDate;
    }

    public void setOccurDate(Date occurDate) {
	this.occurDate = occurDate;
    }

    public int getIssueNum() {
	return issueNum;
    }

    public void setIssueNum(int issueNum) {
	this.issueNum = issueNum;
    }

    public int getQulityNum() {
	return qulityNum;
    }

    public void setQulityNum(int qulityNum) {
	this.qulityNum = qulityNum;
    }

    public int getTaskProcessNum() {
	return taskProcessNum;
    }

    public void setTaskProcessNum(int taskProcessNum) {
	this.taskProcessNum = taskProcessNum;
    }

    public int getTaskDelayNum() {
	return taskDelayNum;
    }

    public void setTaskDelayNum(int taskDelayNum) {
	this.taskDelayNum = taskDelayNum;
    }

    public int getPjtProcessNum() {
	return pjtProcessNum;
    }

    public void setPjtProcessNum(int pjtProcessNum) {
	this.pjtProcessNum = pjtProcessNum;
    }

    public int getPjtDelayNum() {
	return pjtDelayNum;
    }

    public void setPjtDelayNum(int pjtDelayNum) {
	this.pjtDelayNum = pjtDelayNum;
    }

    public String getDuty() {
	return duty;
    }

    public void setDuty(String duty) {
	this.duty = duty;
    }

    public String getCurrentDate() {
	return currentDate;
    }

    public void setCurrentDate(String currentDate) {
	this.currentDate = currentDate;
    }

    public String getNextDate() {
	return nextDate;
    }

    public void setNextDate(String nextDate) {
	this.nextDate = nextDate;
    }

    public String getBeforeDate() {
	return beforeDate;
    }

    public void setBeforeDate(String beforeDate) {
	this.beforeDate = beforeDate;
    }

    public String getCurrentType() {
	return currentType;
    }

    public void setCurrentType(String currentType) {
	this.currentType = currentType;
    }

    public String getNextType() {
	return nextType;
    }

    public void setNextType(String nextType) {
	this.nextType = nextType;
    }

    public String getBeforeType() {
	return beforeType;
    }

    public void setBeforeType(String beforeType) {
	this.beforeType = beforeType;
    }

    public String getFormType() {
	return formType;
    }

    public void setFormType(String formType) {
	this.formType = formType;
    }

    public String getAssemType() {
	return assemType;
    }

    public void setAssemType(String assemType) {
	this.assemType = assemType;
    }

    public void setTotalResult(long totalResult) {
	this.totalResult = totalResult;
    }

    public String getEcrName() {
	return ecrName;
    }

    public void setEcrName(String ecrName) {
	this.ecrName = ecrName;
    }

    public String getEcrId() {
	return ecrId;
    }

    public void setEcrId(String ecrId) {
	this.ecrId = ecrId;
    }

    public String getDeptName() {
	return deptName;
    }

    public void setDeptName(String deptName) {
	this.deptName = deptName;
    }

    public String getEcoId() {
	return ecoId;
    }

    public void setEcoId(String ecoId) {
	this.ecoId = ecoId;
    }

    public String getEcoName() {
	return ecoName;
    }

    public void setEcoName(String ecoName) {
	this.ecoName = ecoName;
    }

    public String getExpectCost() {
	return expectCost;
    }

    public void setExpectCost(String expectCost) {
	this.expectCost = expectCost;
    }

    public String getBudgetCostStr() {
	return budgetCostStr;
    }

    public void setBudgetCostStr(String budgetCostStr) {
	this.budgetCostStr = budgetCostStr;
    }

    public String getResultCostStr() {
	return resultCostStr;
    }

    public void setResultCostStr(String resultCostStr) {
	this.resultCostStr = resultCostStr;
    }

    public String getDisplayName() {
	return displayName;
    }

    public void setDisplayName(String displayName) {
	this.displayName = displayName;
    }

    public String getDisplayTaskProcess() {
	return displayTaskProcess;
    }

    public void setDisplayTaskProcess(String displayTaskProcess) {
	this.displayTaskProcess = displayTaskProcess;
    }

    public String getDisplayTaskDelay() {
	return displayTaskDelay;
    }

    public void setDisplayTaskDelay(String displayTaskDelay) {
	this.displayTaskDelay = displayTaskDelay;
    }

    public String getCarDate() {
	return carDate;
    }

    public void setCarDate(String carDate) {
	this.carDate = carDate;
    }

    public String getShrinkage() {
	return shrinkage;
    }

    public void setShrinkage(String shrinkage) {
	this.shrinkage = shrinkage;
    }

    public String getUseCompleteType() {
	return useCompleteType;
    }

    public void setUseCompleteType(String useCompleteType) {
	this.useCompleteType = useCompleteType;
    }

    public String getLevel2() {
	return level2;
    }

    public void setLevel2(String level2) {
	this.level2 = level2;
    }

    public String getPjtId() {
	return pjtId;
    }

    public void setPjtId(String pjtId) {
	this.pjtId = pjtId;
    }

    public String getRealTime() {
	return realTime;
    }

    public void setRealTime(String realTime) {
	this.realTime = realTime;
    }

    public String getExpectTime() {
	return expectTime;
    }

    public void setExpectTime(String expectTime) {
	this.expectTime = expectTime;
    }

    public String getPjtType1() {
	return pjtType1;
    }

    public void setPjtType1(String pjtType1) {
	this.pjtType1 = pjtType1;
    }

    public String getPjtType2() {
	return pjtType2;
    }

    public void setPjtType2(String pjtType2) {
	this.pjtType2 = pjtType2;
    }

    public String getEcrNumber() {
	return ecrNumber;
    }

    public void setEcrNumber(String ecrNumber) {
	this.ecrNumber = ecrNumber;
    }

    public Date getCompleteDate() {
	return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
	this.completeDate = completeDate;
    }

    public String getCurrentTaskName() {
	return currentTaskName;
    }

    public void setCurrentTaskName(String currentTaskName) {
	this.currentTaskName = currentTaskName;
    }

    public String getCurrentGate() {
	return currentGate;
    }

    public void setCurrentGate(String currentGate) {
	this.currentGate = currentGate;
    }

    public String getCurrentGateDate() {
	return currentGateDate;
    }

    public void setCurrentGateDate(String currentGateDate) {
	this.currentGateDate = currentGateDate;
    }

    public String getBeforeTaskName() {
	return beforeTaskName;
    }

    public void setBeforeTaskName(String beforeTaskName) {
	this.beforeTaskName = beforeTaskName;
    }

    public String getBeforeGate() {
	return beforeGate;
    }

    public void setBeforeGate(String beforeGate) {
	this.beforeGate = beforeGate;
    }

    public String getBeforeGateDate() {
	return beforeGateDate;
    }

    public void setBeforeGateDate(String beforeGateDate) {
	this.beforeGateDate = beforeGateDate;
    }

    public String getNextTaskName() {
	return nextTaskName;
    }

    public void setNextTaskName(String nextTaskName) {
	this.nextTaskName = nextTaskName;
    }

    public String getNextGate() {
	return nextGate;
    }

    public void setNextGate(String nextGate) {
	this.nextGate = nextGate;
    }

    public String getNextGateDate() {
	return nextGateDate;
    }

    public void setNextGateDate(String nextGateDate) {
	this.nextGateDate = nextGateDate;
    }

    public int getProdCompCount() {
	return prodCompCount;
    }

    public void setProdCompCount(int prodCompCount) {
	this.prodCompCount = prodCompCount;
    }

    public int getProdProcCount() {
	return prodProcCount;
    }

    public void setProdProcCount(int prodProcCount) {
	this.prodProcCount = prodProcCount;
    }

    public int getProdDelayCount() {
	return prodDelayCount;
    }

    public void setProdDelayCount(int prodDelayCount) {
	this.prodDelayCount = prodDelayCount;
    }

    public int getReviewCompCount() {
	return reviewCompCount;
    }

    public void setReviewCompCount(int reviewCompCount) {
	this.reviewCompCount = reviewCompCount;
    }

    public int getReviewProcCount() {
	return reviewProcCount;
    }

    public void setReviewProcCount(int reviewProcCount) {
	this.reviewProcCount = reviewProcCount;
    }

    public int getReviewDelayCount() {
	return reviewDelayCount;
    }

    public void setReviewDelayCount(int reviewDelayCount) {
	this.reviewDelayCount = reviewDelayCount;
    }

    public int getMoldCompCount() {
	return moldCompCount;
    }

    public void setMoldCompCount(int moldCompCount) {
	this.moldCompCount = moldCompCount;
    }

    public int getMoldProcCount() {
	return moldProcCount;
    }

    public void setMoldProcCount(int moldProcCount) {
	this.moldProcCount = moldProcCount;
    }

    public int getMoldDelayCount() {
	return moldDelayCount;
    }

    public void setMoldDelayCount(int moldDelayCount) {
	this.moldDelayCount = moldDelayCount;
    }

    public int getIssueCount() {
	return issueCount;
    }

    public void setIssueCount(int issueCount) {
	this.issueCount = issueCount;
    }

    public int getQuiltyCount() {
	return quiltyCount;
    }

    public void setQuiltyCount(int quiltyCount) {
	this.quiltyCount = quiltyCount;
    }

    public int getProdTotalCount() {
	return prodTotalCount;
    }

    public void setProdTotalCount(int prodTotalCount) {
	this.prodTotalCount = prodTotalCount;
    }

    public int getReviewTotalCount() {
	return reviewTotalCount;
    }

    public void setReviewTotalCount(int reviewTotalCount) {
	this.reviewTotalCount = reviewTotalCount;
    }

    public int getMoldTotalCount() {
	return moldTotalCount;
    }

    public void setMoldTotalCount(int moldTotalCount) {
	this.moldTotalCount = moldTotalCount;
    }

    public String[] getvRank() {
	return vRank;
    }

    public void setvRank(String[] vRank) {
	this.vRank = vRank;
    }

    public String getDieCategory() {
	return dieCategory;
    }

    public void setDieCategory(String dieCategory) {
	this.dieCategory = dieCategory;
    }

    public String getMoldType1() {
	return moldType1;
    }

    public void setMoldType1(String moldType1) {
	this.moldType1 = moldType1;
    }

    public String[] getvTaskCategory() {
	return vTaskCategory;
    }

    public void setvTaskCategory(String[] vTaskCategory) {
	this.vTaskCategory = vTaskCategory;
    }

    public int getTypeByIn() {
	return typeByIn;
    }

    public void setTypeByIn(int typeByIn) {
	this.typeByIn = typeByIn;
    }

    public int getTypeByOut() {
	return typeByOut;
    }

    public void setTypeByOut(int typeByOut) {
	this.typeByOut = typeByOut;
    }

    public int getTypeByTotal() {
	return typeByTotal;
    }

    public void setTypeByTotal(int typeByTotal) {
	this.typeByTotal = typeByTotal;
    }

    public String getEcnNo() {
	return ecnNo;
    }

    public void setEcnNo(String ecnNo) {
	this.ecnNo = ecnNo;
    }

    public String getEcoNo() {
	return ecoNo;
    }

    public void setEcoNo(String ecoNo) {
	this.ecoNo = ecoNo;
    }

    public String getStateApproName() {
	return stateApproName;
    }

    public void setStateApproName(String stateApproName) {
	this.stateApproName = stateApproName;
    }

    public String getCreator() {
	return creator;
    }

    public void setCreator(String creator) {
	this.creator = creator;
    }

    public String getReceiveConfirm() {
	return receiveConfirm;
    }

    public void setReceiveConfirm(String receiveConfirm) {
	this.receiveConfirm = receiveConfirm;
    }

    public String getJobComplete() {
	return jobComplete;
    }

    public void setJobComplete(String jobComplete) {
	this.jobComplete = jobComplete;
    }

    public Timestamp getsDate() {
	return sDate;
    }

    public void setsDate(Timestamp sDate) {
	this.sDate = sDate;
    }

    public Timestamp geteDate() {
	return eDate;
    }

    public void seteDate(Timestamp eDate) {
	this.eDate = eDate;
    }

    public String getJobCompleteDate() {
	return jobCompleteDate;
    }

    public void setJobCompleteDate(String jobCompleteDate) {
	this.jobCompleteDate = jobCompleteDate;
    }

    public String getAssemblyPlaceType() {
	return assemblyPlaceType;
    }

    public void setAssemblyPlaceType(String assemblyPlaceType) {
	this.assemblyPlaceType = assemblyPlaceType;
    }

    public String getDivisionFlag() {
	return divisionFlag;
    }

    public void setDivisionFlag(String divisionFlag) {
	this.divisionFlag = divisionFlag;
    }

    public String getnOid() {
	return nOid;
    }

    public void setnOid(String nOid) {
	this.nOid = nOid;
    }

    public float getTargetCost() {
	return targetCost;
    }

    public void setTargetCost(float targetCost) {
	this.targetCost = targetCost;
    }

    public float getBudgetCost() {
	return budgetCost;
    }

    public void setBudgetCost(float budgetCost) {
	this.budgetCost = budgetCost;
    }

    public float getResultCost() {
	return resultCost;
    }

    public void setResultCost(float resultCost) {
	this.resultCost = resultCost;
    }

    public float getBudgetCost1() {
	return budgetCost1;
    }

    public void setBudgetCost1(float budgetCost1) {
	this.budgetCost1 = budgetCost1;
    }

    public float getResultCost1() {
	return resultCost1;
    }

    public void setResultCost1(float resultCost1) {
	this.resultCost1 = resultCost1;
    }

    public float getBudgetCost2() {
	return budgetCost2;
    }

    public void setBudgetCost2(float budgetCost2) {
	this.budgetCost2 = budgetCost2;
    }

    public float getResultCost2() {
	return resultCost2;
    }

    public void setResultCost2(float resultCost2) {
	this.resultCost2 = resultCost2;
    }

    public float getBudgetCost3() {
	return budgetCost3;
    }

    public void setBudgetCost3(float budgetCost3) {
	this.budgetCost3 = budgetCost3;
    }

    public float getResultCost3() {
	return resultCost3;
    }

    public void setResultCost3(float resultCost3) {
	this.resultCost3 = resultCost3;
    }

    public float getBalanceCost() {
	return balanceCost;
    }

    public void setBalanceCost(float balanceCost) {
	this.balanceCost = balanceCost;
    }

    public float getTotalBudget() {
	return totalBudget;
    }

    public void setTotalBudget(float totalBudget) {
	this.totalBudget = totalBudget;
    }

    public float getTotalResult() {
	return totalResult;
    }

    public void setTotalResult(float totalResult) {
	this.totalResult = totalResult;
    }

    public String getItDivision() {
	return itDivision;
    }

    public void setItDivision(String itDivision) {
	this.itDivision = itDivision;
    }

    public String getTypeDivide() {
	return typeDivide;
    }

    public void setTypeDivide(String typeDivide) {
	this.typeDivide = typeDivide;
    }

    public String getPm() {
	return pm;
    }

    public void setPm(String pm) {
	this.pm = pm;
    }

    public int getPjtPlanNum() {
	return pjtPlanNum;
    }

    public void setPjtPlanNum(int pjtPlanNum) {
	this.pjtPlanNum = pjtPlanNum;
    }

    public int getPjtCompleteNum() {
	return pjtCompleteNum;
    }

    public void setPjtCompleteNum(int pjtCompleteNum) {
	this.pjtCompleteNum = pjtCompleteNum;
    }

    public String getDisplayTaskPlan() {
	return displayTaskPlan;
    }

    public void setDisplayTaskPlan(String displayTaskPlan) {
	this.displayTaskPlan = displayTaskPlan;
    }

    public String getDisplayTaskComplete() {
	return displayTaskComplete;
    }

    public void setDisplayTaskComplete(String displayTaskComplete) {
	this.displayTaskComplete = displayTaskComplete;
    }

    public String getUpdateDivision() {
	return updateDivision;
    }

    public void setUpdateDivision(String updateDivision) {
	this.updateDivision = updateDivision;
    }

    public int getMonthSetting() {
	return monthSetting;
    }

    public void setMonthSetting(int monthSetting) {
	this.monthSetting = monthSetting;
    }

    public String getYearSetting() {
	return yearSetting;
    }

    public void setYearSetting(String yearSetting) {
	this.yearSetting = yearSetting;
    }

    public String getDieNo3() {
	return dieNo3;
    }

    public void setDieNo3(String dieNo3) {
	this.dieNo3 = dieNo3;
    }

    public int getDqmProcessCount() {
	return dqmProcessCount;
    }

    public void setDqmProcessCount(int dqmProcessCount) {
	this.dqmProcessCount = dqmProcessCount;
    }

    public int getDqmCompleteCount() {
	return dqmCompleteCount;
    }

    public void setDqmCompleteCount(int dqmCompleteCount) {
	this.dqmCompleteCount = dqmCompleteCount;
    }

    public String getDeptCode() {
	return deptCode;
    }

    public void setDeptCode(String deptCode) {
	this.deptCode = deptCode;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String[] getvDevPattern() {
	return vDevPattern;
    }

    public void setvDevPattern(String[] vDevPattern) {
	this.vDevPattern = vDevPattern;
    }

    public String getDevPattern() {
	return devPattern;
    }

    public void setDevPattern(String devPattern) {
	this.devPattern = devPattern;
    }

    public String getDisplayCode() {
	return displayCode;
    }

    public void setDisplayCode(String displayCode) {
	this.displayCode = displayCode;
    }

    public String getDisplayTeam() {
	return displayTeam;
    }

    public void setDisplayTeam(String displayTeam) {
	this.displayTeam = displayTeam;
    }

    public int getPjtTeamPlanNum() {
	return pjtTeamPlanNum;
    }

    public void setPjtTeamPlanNum(int pjtTeamPlanNum) {
	this.pjtTeamPlanNum = pjtTeamPlanNum;
    }

    public int getPjtTeamProcessNum() {
	return pjtTeamProcessNum;
    }

    public void setPjtTeamProcessNum(int pjtTeamProcessNum) {
	this.pjtTeamProcessNum = pjtTeamProcessNum;
    }

    public int getPjtTeamCompleteNum() {
	return pjtTeamCompleteNum;
    }

    public void setPjtTeamCompleteNum(int pjtTeamCompleteNum) {
	this.pjtTeamCompleteNum = pjtTeamCompleteNum;
    }

    public int getPjtTeamDelayNum() {
	return pjtTeamDelayNum;
    }

    public void setPjtTeamDelayNum(int pjtTeamDelayNum) {
	this.pjtTeamDelayNum = pjtTeamDelayNum;
    }

    public String getDisplayTaskTeamPlan() {
	return displayTaskTeamPlan;
    }

    public void setDisplayTaskTeamPlan(String displayTaskTeamPlan) {
	this.displayTaskTeamPlan = displayTaskTeamPlan;
    }

    public String getDisplayTaskTeamProcess() {
	return displayTaskTeamProcess;
    }

    public void setDisplayTaskTeamProcess(String displayTaskTeamProcess) {
	this.displayTaskTeamProcess = displayTaskTeamProcess;
    }

    public String getDisplayTaskTeamComplete() {
	return displayTaskTeamComplete;
    }

    public void setDisplayTaskTeamComplete(String displayTaskTeamComplete) {
	this.displayTaskTeamComplete = displayTaskTeamComplete;
    }

    public String getDisplayTaskTeamDelay() {
	return displayTaskTeamDelay;
    }

    public void setDisplayTaskTeamDelay(String displayTaskTeamDelay) {
	this.displayTaskTeamDelay = displayTaskTeamDelay;
    }

    public int getIssueTeamNum() {
	return issueTeamNum;
    }

    public void setIssueTeamNum(int issueTeamNum) {
	this.issueTeamNum = issueTeamNum;
    }

    public int getQulityTeamNum() {
	return qulityTeamNum;
    }

    public void setQulityTeamNum(int qulityTeamNum) {
	this.qulityTeamNum = qulityTeamNum;
    }

    public String getRankS() {
	return rankS;
    }

    public void setRankS(String rankS) {
	this.rankS = rankS;
    }

    public String getRankR() {
	return rankR;
    }

    public void setRankR(String rankR) {
	this.rankR = rankR;
    }

    public String getRankA() {
	return rankA;
    }

    public void setRankA(String rankA) {
	this.rankA = rankA;
    }

    public String getRankB() {
	return rankB;
    }

    public void setRankB(String rankB) {
	this.rankB = rankB;
    }

    public String getRankC() {
	return rankC;
    }

    public void setRankC(String rankC) {
	this.rankC = rankC;
    }

    public String getRankSum() {
	return rankSum;
    }

    public void setRankSum(String rankSum) {
	this.rankSum = rankSum;
    }

    public String getTaskCnt() {
	return taskCnt;
    }

    public void setTaskCnt(String taskCnt) {
	this.taskCnt = taskCnt;
    }

    public String getEcrCnt() {
	return ecrCnt;
    }

    public void setEcrCnt(String ecrCnt) {
	this.ecrCnt = ecrCnt;
    }

    public String getEcoCnt() {
	return ecoCnt;
    }

    public void setEcoCnt(String ecoCnt) {
	this.ecoCnt = ecoCnt;
    }

    public String getCommon() {
	return common;
    }

    public void setCommon(String common) {
	this.common = common;
    }

    public String getHavework() {
	return havework;
    }

    public void setHavework(String havework) {
	this.havework = havework;
    }

    public String getHavemaxwork() {
	return havemaxwork;
    }

    public void setHavemaxwork(String havemaxwork) {
	this.havemaxwork = havemaxwork;
    }

    public String getWork_plan_project() {
	return work_plan_project;
    }

    public void setWork_plan_project(String work_plan_project) {
	this.work_plan_project = work_plan_project;
    }

    public String getWork_plan_sum() {
	return work_plan_sum;
    }

    public void setWork_plan_sum(String work_plan_sum) {
	this.work_plan_sum = work_plan_sum;
    }

    public String getWork_load() {
	return work_load;
    }

    public void setWork_load(String work_load) {
	this.work_load = work_load;
    }

    public String getDepartCode() {
	return departCode;
    }

    public void setDepartCode(String departCode) {
	this.departCode = departCode;
    }

    public String getUserCode() {
	return userCode;
    }

    public void setUserCode(String userCode) {
	this.userCode = userCode;
    }

    public String getDashboardType() {
	return dashboardType;
    }

    public void setDashboardType(String dashboardType) {
	this.dashboardType = dashboardType;
    }

    public String getProcessGb() {
	return processGb;
    }

    public void setProcessGb(String processGb) {
	this.processGb = processGb;
    }

    public String getIssueTeamNumHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getIssueTeamNumHtmlPostfix() {
	return "</font>";
    }

    public String getQulityTeamNumHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getQulityTeamNumHtmlPostfix() {
	return "</font>";
    }

    public String getDisplayTaskTeamPlanHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getDisplayTaskTeamPlanHtmlPostfix() {
	return "</font>";
    }

    public String getDisplayTaskTeamProcessHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getDisplayTaskTeamProcessHtmlPostfix() {
	return "</font>";
    }

    public String getDisplayTaskTeamCompleteHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getDisplayTaskTeamCompleteHtmlPostfix() {
	return "</font>";
    }

    public String getDisplayTaskTeamDelayHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getDisplayTaskTeamDelayHtmlPostfix() {
	return "</font>";
    }

    public String getPjtTeamPlanNumHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getPjtTeamPlanNumHtmlPostfix() {
	return "</font>";
    }

    public String getPjtTeamProcessNumHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getPjtTeamProcessNumHtmlPostfix() {
	return "</font>";
    }

    public String getPjtTeamCompleteNumHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getPjtTeamCompleteNumHtmlPostfix() {
	return "</font>";
    }

    public String getPjtTeamDelayNumHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getPjtTeamDelayNumHtmlPostfix() {
	return "</font>";
    }
    
    public String getStateHtmlPrefix() {
	String font = "";
	if("지연".equals(this.getState())){
	    font = "<font color='red'>";
	}else{
	    
	}
	return font;
	
	
    }

    public String getStateHtmlPostfix() {
	String font = "";
	if("지연".equals(this.getState())){
	    font = "</font>";
	}else{
	    
	}
	return font;
    }


    
    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriodHtmlPrefix() {
	String font = "";
	if("지연".equals(this.getState())){
	    font = "<font color='red'>";
	}else{
	    
	}
	return font;
	
	
    }

    public String getPeriodHtmlPostfix() {
	String font = "";
	if("지연".equals(this.getState())){
	    font = "</font>";
	}else{
	    
	}
	return font;
    }
    
    public String getTaskResultSignalHtmlPrefix() {
	String str = "";

	if ("R".equals(this.getTaskResultAndon())) {
	    str = "<img src='/plm/extcore/image/ico_red.png' />";
	} else if ("Y".equals(this.getTaskResultAndon())) {
	    str = "<img src='/plm/extcore/image/ico_yellow.png' />";
	} else if ("G".equals(this.getTaskResultAndon())) {
	    str = "<img src='/plm/extcore/image/ico_green.png' />";
	}
	
	return str;
    }

    public String getTaskResultSignalHtmlPostfix() {
	return "";
    }

    public String getTrustResult() {
        return trustResult;
    }

    public void setTrustResult(String trustResult) {
        this.trustResult = trustResult;
    }

    public String getAssessCheck() {
        return assessCheck;
    }

    public void setAssessCheck(String assessCheck) {
        this.assessCheck = assessCheck;
    }

    public String getExecenMon() {
        return execenMon;
    }

    public void setExecenMon(String execenMon) {
        this.execenMon = execenMon;
    }

    public String getUnusualCnt() {
        return unusualCnt;
    }

    public void setUnusualCnt(String unusualCnt) {
        this.unusualCnt = unusualCnt;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getTrustCode() {
        return trustCode;
    }

    public void setTrustCode(String trustCode) {
        this.trustCode = trustCode;
    }

    public String[] getvProcessCode() {
        return vProcessCode;
    }

    public void setvProcessCode(String[] vProcessCode) {
        this.vProcessCode = vProcessCode;
    }

    public String[] getvTrustCode() {
        return vTrustCode;
    }

    public void setvTrustCode(String[] vTrustCode) {
        this.vTrustCode = vTrustCode;
    }
    
    
}
