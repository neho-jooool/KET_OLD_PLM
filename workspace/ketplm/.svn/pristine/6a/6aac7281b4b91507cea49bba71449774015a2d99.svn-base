package e3ps.project.beans;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.QuerySpec;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.ProductProject;
import e3ps.project.ProjectCustomereventLink;
import e3ps.project.ProjectDependencyLink;
import e3ps.project.ProjectOemTypeLink;
import e3ps.project.ProjectSubcontractorLink;
import e3ps.project.ProjectViewDepartmentLink;
import e3ps.project.ReviewProject;
import e3ps.project.ScheduleData;
import e3ps.project.TemplateProject;
import e3ps.project.WorkProject;
import e3ps.project.outputtype.OEMPlan;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.project.task.service.ProjectTaskCompHelper;
import ext.ket.shared.log.Kogger;

public class E3PSProjectData {

    // TemplateProject Attribute Values
    public TemplateProject tempProject; // TemplateProject Object
    public String pjtNo = ""; // 프로젝트NO
    public String ViewpjtNo = ""; // 프로젝트NO(예: #2140)
    public String pjtName = ""; // 프로젝트 명(장비명)
    public int pjtSeq; // 순서
    public String pjtDesc = ""; // 설명
    public int pjtType; // 프로젝트 종류
    public String pjtTypeName = ""; // 프로젝트 종류(String Values)
    public int pjtHistory; // 이력
    // [START] [PLM System 1차개선] Project 이력 Iteration 추가, 2013-07-04, BoLee
    public int pjtIteration; // Project 이력 Iteration
    // [END] [PLM System 1차개선] Project 이력 Iteration 추가, 2013-07-04, BoLee
    public String pjtOutputType = ""; // 산출물 인증방식

    // ExtendSchedule Attribute Values
    public ScheduleData scheduleData; // ScheduleData Object
    public int pjtDuration; // 총 기간
    public int scheduleHistory; // 이력 (0: 최초생성 시, 1~N: 갱신 시)
    public ExtendScheduleData schedule; // ExtendScheduleData Object(프로젝트 일정)
    public Timestamp pjtPlanStartDate; // 계획 시작일자
    public Timestamp pjtPlanEndDate; // 계획 종료일자
    public Timestamp pjtExecStartDate; // 실제 시작일자
    public Timestamp pjtExecEndDate; // 실제 종료일자

    // Attribute Values
    public E3PSProject e3psProject;
    public String e3psPjtOID = "";
    public int pjtCompletion; // 프로젝트 완료율
    public int pjtState; // 프로젝트 상태
    public String pjtStateName = ""; // 프로젝트 상태(String Values)
    public Timestamp pjtCreateDate; // 최초 등록일자
    public Timestamp pjtModifyDate; // 최종 수정일자

    // Member Attribute Values
    public WTUser pjtPm; // PM
    public String pjtPmName = ""; // PM명
    public String roleMember = ""; // ROLE Member
    public String etcMember = ""; // ETC Member
    public WTUser sales; // 영업담당자
    public String salesOid; // 영업담당자 OID
    public String salesName = ""; // 영업담당자 이름
    public String salesDept = "";
    public WTUser pjtQA;  // 선행품질보증
    public WTUser pjtQC; //전자사업부 전자품질관리
    public WTUser pjtCarQC; //자동차사업부 선행품질관리

    public String devDept = ""; // 개발부서명
    public String devDeptOid = ""; // 개발부서 OID
    public String department = ""; // 부서
    public String departmentOid = ""; // 부서 OID

    // NumberCode(LINK) Attribute Values
    public NumberCode producttypeLink; // 제품구분
    public String producttypeOID;
    public String producttypeName;

    public NumberCode assembledtypeLink; // 조립구분
    public String assembledtypeOID;
    public String assembledtypeName;

    public NumberCode developenttypeLink; // 개발구분
    public String developenttypeOID;
    public String developenttypeName;

    public NumberCode rankLink; // Rank
    public String rankOID;
    public String rankName;

    public NumberCode pjtMakeCompanyCodeLink; // 생산조직
    public String pjtMakeCompanyCodeOID;
    public String pjtMakeCompanyCodeName;

    public Vector customereventVec = new Vector(); // 고객사(Vector)
    public Vector subcontractorVec = new Vector(); // 의뢰처(Vector)

    public String currentTaskName = "";
    // 현재 진행중인 Task 정보(Level1 에 한해서)
    public String state = ""; // 프로젝트 상태
    public String stateKorea = "";
    public double differDateGap;

    private double preferComp;
    NumberFormat nf;

    public Timestamp pjtExecEndDate_ex;

    private String preferCompStr = "";

    public String cashPreferCompStr = null;

    boolean isSetting = false;

    boolean isDelay = false;

    public String customer = "";
    public String dependence = "";

    public String teamType = "";
    public String partNo = "";

    public String representModel = "";

    public String planWorkTime = "";
    public String execWorkTime = "";

    public boolean isCarDivisionMode = true; // 자동차사업부 모드(for 전자사업부 IT전장모듈 개발팀)
    public WTUser costMember = null;
    public String costMemberName = "";
    
    public List<OEMProjectType> oemTypes = new ArrayList<OEMProjectType>(); //파생차종 OBJECT
    public String oemNames = "";    //파생차종 NAME
    public String oemOids = "";     //파생차종 OID
    
    public String workType = "";

    public E3PSProjectData(E3PSProject e3psProject) throws Exception {
    	nf = NumberFormat.getInstance();
    	nf.setMaximumFractionDigits(2);
    	this.pjtNo = StringUtil.checkNull(e3psProject.getPjtNo());
    
    	this.ViewpjtNo = StringUtil.checkNull(e3psProject.getPjtNo());
    	this.pjtName = StringUtil.checkNull(e3psProject.getPjtName());
    	this.pjtSeq = e3psProject.getPjtSeq();
    	this.pjtDesc = StringUtil.checkNull(e3psProject.getPjtDesc());
    	this.pjtType = e3psProject.getPjtType();
    	if (this.pjtType == 0)
    	    this.pjtTypeName = "검토개발(전자)";
    	else if (this.pjtType == 1)
    	    this.pjtTypeName = "검토개발(자동차)";
    	else if (this.pjtType == 2)
    	    this.pjtTypeName = "제품(자동차)";
    	else if (this.pjtType == 3)
    	    this.pjtTypeName = "금형(자동차)";
    	else if (this.pjtType == 4)
    	    this.pjtTypeName = "제품(전자)";
    	else if (this.pjtType == 5)
    	    this.pjtTypeName = "제품(KETS)";
    	else if (this.pjtType == 6)
    	    this.pjtTypeName = "금형(KETS)";
    
    	this.pjtHistory = e3psProject.getPjtHistory();
    	// [START] [PLM System 1차개선] Project 이력 Iteration 추가, 2013-07-04, BoLee
    	this.pjtIteration = e3psProject.getPjtIteration();
    	// [END] [PLM System 1차개선] Project 이력 Iteration 추가, 2013-07-04, BoLee
    	this.pjtCompletion = e3psProject.getPjtCompletion();
    
    	// ExtendSchedule Attribute Values
    	this.schedule = (ExtendScheduleData) e3psProject.getPjtSchedule().getObject();
    
    	this.scheduleHistory = this.schedule.getScheduleHistory();
    	this.pjtPlanStartDate = this.schedule.getPlanStartDate();
    	this.pjtPlanEndDate = this.schedule.getPlanEndDate();
    	this.pjtDuration = 0;
    	if (pjtPlanStartDate != null && pjtPlanEndDate != null)
    	    this.pjtDuration = DateUtil.getDuration(pjtPlanStartDate, pjtPlanEndDate) + 1; // this.schedule.getDuration();
    	this.pjtExecStartDate = this.schedule.getExecStartDate();
    	this.pjtExecEndDate = this.schedule.getExecEndDate();
    
    	// e3psProject Attribute Values
    	this.e3psProject = e3psProject;
    	this.e3psPjtOID = CommonUtil.getOIDString(e3psProject);
    	this.pjtState = e3psProject.getPjtState();
    	this.pjtStateName = StringUtil.checkNull(ProjectStateHelper.manager.getStateName(this.pjtState));
    	this.pjtCreateDate = e3psProject.getPersistInfo().getCreateStamp();
    	this.pjtModifyDate = e3psProject.getPersistInfo().getModifyStamp();
    	this.state = e3psProject.getLifeCycleState().toString();
    	this.stateKorea = e3psProject.getLifeCycleState().getDisplay(Locale.KOREA);
    
    	// Member Attribute Values
    	this.pjtPm = ProjectUserHelper.manager.getPM(e3psProject);
    	if (pjtPm != null) {
    	    PeopleData peopleData = new PeopleData(pjtPm);
    
    	    this.pjtPmName = StringUtil.checkNull(peopleData.people.getName());
    	} else {
    	    this.pjtPmName = "";
    	}
    	
    	this.pjtQA = ProjectUserHelper.manager.getQA(e3psProject);
    	this.pjtQC = ProjectUserHelper.manager.getElecQC(e3psProject);
    	this.pjtCarQC = ProjectUserHelper.manager.getElecQC(e3psProject);
    	this.costMember = ProjectUserHelper.manager.getCOST(e3psProject);
    	
    	if (costMember != null) {
    	    PeopleData peopleData = new PeopleData(costMember);
    	    this.costMemberName = StringUtil.checkNull(peopleData.people.getName());
    	} else {
    	    this.costMemberName = "";
    	}
    
    	this.sales = e3psProject.getBusiness();
    	if (sales != null) {
    	    PeopleData peopleData = new PeopleData(sales);
    	    this.salesName = StringUtil.checkNull(peopleData.people.getName());
    	    this.salesOid = CommonUtil.getOIDString(this.sales);
    	    this.salesDept = peopleData.departmentName;
    	} else {
    	    this.salesName = "";
    	    this.salesDept = "";
    	}
    
    	QueryResult departmentQr = ProjectUserHelper.manager.getViewDepartmentLink(e3psProject, null);
    	if (departmentQr.hasMoreElements()) {
    	    Object[] obj = (Object[]) departmentQr.nextElement();
    	    ProjectViewDepartmentLink link = (ProjectViewDepartmentLink) obj[0];
    	    this.department = link.getDepartment().getName();
    	    this.departmentOid = CommonUtil.getOIDString(link.getDepartment());
    	}
    
    	if (e3psProject instanceof ReviewProject) {
    	    ReviewProject rp = (ReviewProject) CommonUtil.getObject(this.e3psPjtOID);
    	    if (rp.getDevDept() != null) {
    		this.devDept = rp.getDevDept().getName();
    		this.devDeptOid = CommonUtil.getOIDString(rp.getDevDept());
    	    }
    	}
    	
    	if (e3psProject instanceof WorkProject) {
    	    WorkProject rp = (WorkProject) CommonUtil.getObject(this.e3psPjtOID);
    	    if (rp.getDevDept() != null) {
    		this.devDept = rp.getDevDept().getName();
    		this.devDeptOid = CommonUtil.getOIDString(rp.getDevDept());
    	    }
    	    if(rp.getWorkType() != null){
    		this.workType = rp.getWorkType().getName();
    	    }
    	}
    
    	// NumberCode(LINK) Attribute Values
    	if (e3psProject.getProductType() != null) {
    	    this.producttypeLink = e3psProject.getProductType();
    	    this.producttypeOID = CommonUtil.getOIDString(this.producttypeLink);
    	    this.producttypeName = StringUtil.checkNull(this.producttypeLink.getName());
    	}
    	if (e3psProject.getAssembledType() != null) {
    	    this.assembledtypeLink = e3psProject.getAssembledType();
    	    this.assembledtypeOID = CommonUtil.getOIDString(this.assembledtypeLink);
    	    this.assembledtypeName = StringUtil.checkNull(this.assembledtypeLink.getName());
    	}
    	if (e3psProject.getDevelopentType() != null) {
    	    this.developenttypeLink = e3psProject.getDevelopentType();
    	    this.developenttypeOID = CommonUtil.getOIDString(this.developenttypeLink);
    	    this.developenttypeName = StringUtil.checkNull(this.developenttypeLink.getName());
    	}
    	if (e3psProject.getRank() != null) {
    	    this.rankLink = e3psProject.getRank();
    	    this.rankOID = CommonUtil.getOIDString(this.rankLink);
    	    this.rankName = StringUtil.checkNull(this.rankLink.getName());
    	}
    	try {
    	    if (e3psProject.getOemType() != null) {
    		this.representModel = e3psProject.getOemType().getName();
    	    }
    	} catch (Exception e) {
    	    Kogger.error(getClass(), e);
    	    this.representModel = "";
    	}
    
    	QuerySpec pcspec = new QuerySpec(ProjectCustomereventLink.class);
    	SearchUtil.appendEQUAL(pcspec, ProjectCustomereventLink.class, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(e3psProject), 0);
    	QueryResult pcresult = PersistenceHelper.manager.find(pcspec);
    	while (pcresult != null && pcresult.hasMoreElements()) {
    	    ProjectCustomereventLink link = (ProjectCustomereventLink) pcresult.nextElement();
    	    if (link != null) {
    		this.customereventVec.addElement(link.getCustomerevent());
    
    		this.customer = this.customer + link.getCustomerevent().getName();
    		if (pcresult.hasMoreElements())
    		    this.customer = this.customer + ", ";
    	    }
    	}
    
    	QuerySpec psspec = new QuerySpec(ProjectSubcontractorLink.class);
    	SearchUtil.appendEQUAL(psspec, ProjectSubcontractorLink.class, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(e3psProject), 0);
    	QueryResult psresult = PersistenceHelper.manager.find(psspec);
    	while (psresult != null && psresult.hasMoreElements()) {
    	    ProjectSubcontractorLink link = (ProjectSubcontractorLink) psresult.nextElement();
    	    if (link != null) {
    		this.subcontractorVec.addElement(link.getSubcontractor());
    
    		this.dependence = this.dependence + link.getSubcontractor().getName();
    		if (psresult.hasMoreElements())
    		    this.dependence = this.dependence + ", ";
    	    }
    	}
    	
    	QueryResult oemTypeQr = PersistenceHelper.manager.navigate(e3psProject, ProjectOemTypeLink.OEM_PJT_TYPE_ROLE, ProjectOemTypeLink.class, true);
        
    	while (oemTypeQr.hasMoreElements()) {
            OEMProjectType oemType = (OEMProjectType) oemTypeQr.nextElement();
            
            oemTypes.add(oemType);
            this.oemNames += oemType.getName() + ", ";
            this.oemOids += CommonUtil.getOIDString(oemType) + ", ";
        }
    	
    	this.oemNames = StringUtils.removeEnd(this.oemNames, ", ");
    	this.oemOids = StringUtils.removeEnd(this.oemOids, ", ");
    	
    	Timestamp lastTaskEndDate = null;
    
    	// 현재 진행중인 Task 명
    	QueryResult parentResult = ProjectTaskHelper.manager.getChild(this.e3psProject);
    	while (parentResult != null && parentResult.hasMoreElements()) {
    	    Object[] parentObj = (Object[]) parentResult.nextElement();
    	    E3PSTask parentTask = (E3PSTask) parentObj[0]; // Level 1 Task
    	    E3PSTaskData taskData = new E3PSTaskData(parentTask);
    	    if (taskData.taskState != ProjectStateFlag.TASK_STATE_COMPLETE) {
    		this.currentTaskName = parentTask.getTaskName();
    		lastTaskEndDate = taskData.taskPlanEndDate;
    		break;
    	    } else {
    		continue;
    	    }
    	}
    
    	if (lastTaskEndDate != null)
    	    this.pjtDuration = DateUtil.getDuration(pjtPlanStartDate, lastTaskEndDate) + 1;
    
    	Calendar startDate = Calendar.getInstance();
    	startDate.setTime(this.pjtPlanStartDate);
    	int compPer = this.pjtCompletion;
    	double d = ((double) compPer) / 100D;
    	d *= pjtDuration * 0x5265c00L;
    
    	// startDate.add(5, (int)d);
    	double realLength = startDate.getTimeInMillis() + d;
    
    	Calendar currentDate = Calendar.getInstance();
    
    	double continueTimeGap = realLength - (double) currentDate.getTime().getTime();
    
    	differDateGap = ((double) continueTimeGap) / ((double) 0x5265c00L);
    
    	long realGap = pjtDuration * 0x5265c00L;
    
    	long nest = pjtPlanStartDate.getTime() + realGap;
    
    	preferComp = 0;
    
    	long time = 0;// currentDate.getTime().getTime() - (pjtPlanEndDate.getTime() + 0x5265c00L);
    
    	if (time >= 0) {
    	    preferComp = 100;
    	} else {
    	    long reallength = pjtDuration * 0x5265c00L;
    	    long currentGap = currentDate.getTime().getTime() - pjtPlanStartDate.getTime();
    	    preferComp = ((double) currentGap / (double) reallength) * 100d;
    	}
    
    	if (preferComp < 0) {
    	    preferComp = 0;
    	}
    
    	preferCompStr = nf.format(preferComp);
    
    	if (e3psProject instanceof ProductProject) {
    	    this.teamType = ((ProductProject) e3psProject).getTeamType();
    	    if ("전자 사업부".equals(this.teamType) && "dev".equals(((ProductProject) e3psProject).getItDivision())) {
    		this.isCarDivisionMode = false;
    	    }
    	    this.partNo = ((ProductProject) e3psProject).getPartNo();
    	}
    	if (e3psProject instanceof ReviewProject) {
    	    this.teamType = ((ReviewProject) e3psProject).getAttr1();
    	}
    
    	List<String> workTimeList = new ArrayList<String>();
    	workTimeList = ProjectTaskCompHelper.service.getProjectWorkTimeSumList(CommonUtil.getOIDString(e3psProject));
    	if (workTimeList != null) {
    	    this.planWorkTime = workTimeList.get(0);
    	    this.execWorkTime = workTimeList.get(1);
    	}
    
        }
    
        public ProjectAccessData getAccessState() {
    	// project.getPjtState()==ProjectStateFlag.PROJECT_STATE_PROGRESS
    	boolean isComplete = "COMPLETED" == this.state;
    	boolean isContinue = false;
    	// boolean isCompleting = ProjectStateFlag.PROJECT_STATE_COMPLETE == this.state;
    	boolean isStop = "STOPED" == this.state;
    	// boolean isReady = ProjectStateFlag.PROJECT_STATE_READY == this.pjtState;
    
    	if (isComplete) {
    	    isContinue = false;
    	} else {
    	    isContinue = true;
    	}
    
    	ProjectAccessData accessData = new ProjectAccessData();
    	accessData.setComplete(isComplete);
    	// accessData.setCompleting(isCompleting);
    	accessData.setContinue(isContinue);
    	accessData.setStop(isStop);
    	// accessData.setReady(isReady);
    
    	return accessData;
        }
    
        public String getPreferCompStr() throws Exception {
    
    	if (e3psProject.getPreferComp() == null) {
    	    return "0";
    	}
    
    	return e3psProject.getPreferComp();
    
        }
    
        public String getStateStr(String State) throws Exception {
    
    	return getStateStr(State, true);
    
        }
    
        public int getPjtState() {
    	if (state.equals("COMPLETED")) {
    	    return ProjectStateFlag.PROJECT_STATE_COMPLETE;
    	}
    	if (state.equals("PROGRESS")) {
    	    return pjtState;
    	} else {
    	    return -1;
    	}
        }
    
        public String getPjtStateColor(int state) {
    	String color = "black";
    	switch (state) {
    	case ProjectStateFlag.PROJECT_STATE_COMPLETE: {
    	    color = "green";
    	    break;
    	}
    	case ProjectStateFlag.PROJECT_STATE_DELAY: {
    	    color = "green";
    	    break;
    	}
    	case ProjectStateFlag.PROJECT_STATE_DELAY_PROGRESS: {
    	    color = "green";
    	    break;
    	}
    	case ProjectStateFlag.PROJECT_STATE_PROGRESS: {
    	    color = "green";
    	    break;
    	}
    	}
    
    	return color;

    }

    public String getStateStr(String State, boolean withState) throws Exception {

    	/*
    	 * if (state.equals("STOPED")) { return "<table><tr title='중지되었습니다'>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl +
    	 * "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_red_bar.gif></td>" + "<td>("
    	 * + StringUtil.checkNull(this.stateKorea) + ")</td></tr></table>"; }
    	 */
    
    	if (state.equals("COMPLETED")) {
    	    // 사전개발 게획 일 경우는 상태를 다르게 표시
    	    // if (this.jelProject.isPre())
    	    // return this.jelProject.getPreProjectState();
    	    // else
    
    	    String returnStr = "<table><tr title='종료되었습니다'>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
    		    + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
    		    + "/state_green_bar.gif></td>";
    	    if (withState) {
    		returnStr += "<td>(" + StringUtil.checkNull(State) + ")</td>";
    	    }
    	    returnStr += "</tr></table>";
    	    return returnStr;
    
    	}
    
    	String typeState = "<font color=red>" + State + "</font>";
    	String typeStateSelect = null;
    
    	if (State.equals("작업 중"))
    	    typeStateSelect = "결재 중";
    	else
    	    typeStateSelect = State;
    
    	int pjtState = e3psProject.getPjtState();
    
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

    public ArrayList getMilestoneTasks() {
    	ArrayList milestoneArr = new ArrayList();
    
    	QueryResult qr = ProjectTaskHelper.manager.getTaskWithMilestone(e3psProject, true);
    	if (qr != null && qr.size() > 0) {
    	    while (qr.hasMoreElements()) {
    		Object[] obj = (Object[]) qr.nextElement();
    		E3PSTask task = (E3PSTask) obj[0];
    		milestoneArr.add(task);
    	    }
    	}
    
    	return milestoneArr;
    }

    public Hashtable getDependencyProjects(E3PSProject project) {
    	Hashtable hash = new Hashtable();
    
    	try {
    	    ArrayList arr1 = getProjectDependencyLink(project);
    	    for (int i = 0; i < arr1.size(); i++) {
    		E3PSProject dependProject1 = (E3PSProject) arr1.get(i);
    		E3PSProjectData dependData1 = new E3PSProjectData(dependProject1);
    		if (!CommonUtil.getOIDString(project).equals(CommonUtil.getOIDString(dependProject1)))
    		    hash.put(dependData1.e3psPjtOID, dependData1);
    
    		ArrayList arr2 = getProjectDependencyLink(dependProject1);
    		for (int j = 0; j < arr2.size(); j++) {
    		    E3PSProject dependProject2 = (E3PSProject) arr2.get(j);
    		    E3PSProjectData dependData2 = new E3PSProjectData(dependProject2);
    		    if (!CommonUtil.getOIDString(project).equals(CommonUtil.getOIDString(dependProject2)))
    			hash.put(dependData2.e3psPjtOID, dependData2);
    
    		    ArrayList arr3 = getProjectDependencyLink(dependProject2);
    		    for (int k = 0; k < arr3.size(); k++) {
    			E3PSProject dependProject3 = (E3PSProject) arr3.get(k);
    			E3PSProjectData dependData3 = new E3PSProjectData(dependProject3);
    			if (!CommonUtil.getOIDString(project).equals(CommonUtil.getOIDString(dependProject3)))
    			    hash.put(dependData3.e3psPjtOID, dependData3);
    		    }
    		}
    	    }
    	} catch (Exception e) {
    	    Kogger.error(getClass(), e);
    	}
    
    	return hash;
    }

    public ArrayList getProjectDependencyLink(E3PSProject project) {
    	ArrayList arr = new ArrayList();
    	try {
    	    QueryResult projectQr = PersistenceHelper.manager.navigate(project, "dependTarget", ProjectDependencyLink.class);
    	    while (projectQr != null && projectQr.hasMoreElements()) {
    		E3PSProject dependProject = (E3PSProject) projectQr.nextElement();
    
    		arr.add(dependProject);
    	    }
    
    	    QueryResult projectQr2 = PersistenceHelper.manager.navigate(project, "dependSource", ProjectDependencyLink.class);
    	    while (projectQr2 != null && projectQr2.hasMoreElements()) {
    		E3PSProject dependProject = (E3PSProject) projectQr2.nextElement();
    
    		arr.add(dependProject);
    	    }
    
    	} catch (WTException e) {
    	    Kogger.error(getClass(), e);
    	} catch (Exception e) {
    	    Kogger.error(getClass(), e);
    	}
    
    	return arr;
    }

    public String getNowEventName(OEMProjectType model) {
	String eventName = "";
	try {
	    if (model != null) {
		QueryResult opqr = OEMTypeHelper.getCustomerEvent(model);
		int count = 0;

		if (opqr != null) {
		    Date oemEndDate = null;
		    while (opqr.hasMoreElements()) {
			Object[] oo = (Object[]) opqr.nextElement();
			OEMPlan op = (OEMPlan) oo[0];
			if (op != null) {
			    if (!StringUtil.checkNull(op.getOemEndDate().toString()).equals("")) {
				Kogger.debug(getClass(), "1===>" + op.getOemEndDate().toString());
				oemEndDate = new Date(op.getOemEndDate().getTime());
			    }
			    if (!StringUtil.checkNull(oemEndDate.toString()).equals("")) {
				if (oemEndDate.before(new Date())) {
				    if (op.getOemType() != null) {
					eventName = StringUtil.checkNull(op.getOemType().getName());
				    }
				}
			    }
			}
		    }
		}
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.debug(getClass(), "Exception!!!!!!!!!!!!!!");
	    // Kogger.error(getClass(), e);
	}

	return eventName;
    }

}
