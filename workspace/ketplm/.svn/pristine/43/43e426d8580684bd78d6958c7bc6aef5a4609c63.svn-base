package e3ps.project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import e3ps.common.code.NumberCodeHelper;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETGridUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.project.CheckoutLink;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.MoldTemplateProject;
import e3ps.project.ProductProject;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ProjectOemTypeLink;
import e3ps.project.ReviewProject;
import e3ps.project.TaskMemberLink;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import e3ps.project.WorkProject;
import e3ps.project.beans.E3PSTaskData;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectTaskScheduleHelper;
import e3ps.project.beans.ProjectViewButtonAuth;
import e3ps.project.beans.RoleComparator;
import e3ps.project.beans.TaskHelper;
import e3ps.project.beans.TaskUserHelper;
// 고객사 일정 - 2013.09.26
import e3ps.project.customerPlan.CustomerPlan;
import e3ps.project.dao.ProjectScheduleDao;
import e3ps.project.historyprocess.HistoryHelper;
import e3ps.project.outputtype.ModelPlan;
import e3ps.project.outputtype.OEMPlan;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.project.program.entity.KETProgramEvent;
import ext.ket.project.program.entity.KETProgramEventLink;
import ext.ket.project.program.entity.KETProgramProjectLink;
import ext.ket.project.program.entity.KETProgramSchedule;
import ext.ket.shared.log.Kogger;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.lifecycle.LifeCycleException;
import wt.org.WTUser;
import wt.pds.StatementSpec;
import wt.project.Role;
import wt.query.ClassAttribute;
import wt.query.ColumnListExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.query.TableColumn;
import wt.session.SessionHelper;
import wt.team.TeamHelper;
import wt.team.TeamTemplate;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.util.WTInvalidParameterException;

/**
 * [PLM System 1차개선] 클래스명 : ProjectScheduleServlet 설명 : Project 일정 관리 Servlet 작성자 : BoLee 작성일자 : 2013. 6. 12.
 */

@SuppressWarnings("serial")
public class ProjectScheduleServlet extends CommonServlet {

    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	String cmd = ParamUtil.getStrParameter(req.getParameter("cmd"));

	if (cmd.equalsIgnoreCase("view")) { // view : Project 일정 조회
	    getProjectSchedule(req, res, cmd);
	} else if (cmd.equalsIgnoreCase("search")) { // search : Project 일정 변경 - 조회
	    getProjectSchedule(req, res, cmd);
	} else if (cmd.equalsIgnoreCase("change")) { // change : Project 일정 변경 상태 변경 (PLANCHANGE)
	    changeProjectState(req, res);
	} else if (cmd.equalsIgnoreCase("save")) { // save : Project 일정 저장
	    saveProjectSchedule(req, res);
	} else if (cmd.equalsIgnoreCase("complete")) { // complete : Project 일정 변경 완료 처리
	    completeChangeProjectSchedule(req, res);
	} else if (cmd.equalsIgnoreCase("wbs_search")) { // wbs_search : WBS 일정 변경 - 조회
	    getWBSSchedule(req, res);
	} else if (cmd.equalsIgnoreCase("wbs_save")) { // wbs_save : WBS 일정 저장
	    saveWBSSchedule(req, res);
	} else if (cmd.equalsIgnoreCase("getPProjectSchedule")) { // getPProjectSchedule : 제품 프로젝트 일정
        getPProjectSchedule(req, res);
	}
    }

    /**
     * <pre>
     * @description 제품 프로젝트 일정 JSON RETURN
     * @author dhkim
     * @date 2018. 7. 26. 오전 10:29:41
     * @method getPProjectSchedule
     * @param req
     * @param res
     * </pre>
     */
    private void getPProjectSchedule(HttpServletRequest req, HttpServletResponse res) {
        
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        JSONObject resJSON = new JSONObject();
        PrintWriter out = null;
        Connection conn = null;
        
        try {
            out = res.getWriter();
            
            String oid = req.getParameter("oid");

            MoldProject mProject = (MoldProject) CommonUtil.getObject(oid);
            MoldItemInfo itemInfo = mProject.getMoldInfo();
            ProductProject pProject = itemInfo.getProject();
            
            conn = PlmDBUtil.getConnection();
            ProjectScheduleDao dao = new ProjectScheduleDao(conn);

            if(pProject != null) {
                
                KETParamMapUtil paramMap = new KETParamMapUtil();
                paramMap.put("project", pProject);
                paramMap.put("oidLong", CommonUtil.getOIDLongValue(pProject));
                
                List<KETParamMapUtil> taskList = dao.getProjectTaskSchedule(paramMap);
                JSONArray taskListArr = new JSONArray(taskList);
                resJSON.put("taskList", taskListArr);
            }
            
            resJSON.put("result", true);
            out.print(resJSON.toString());
            out.close();
            
        }catch(Exception e) {
            
            try {
                if(out != null) {
                    resJSON.put("result", false);
                    out.print(resJSON.toString());
                    out.close();
                }else {
                    out = res.getWriter();
                    resJSON.put("result", false);
                    out.print(resJSON.toString());
                    out.close();
                }
            }catch(IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            PlmDBUtil.close(conn);
        }
    }

    /**
     * 함수명 : getProjectSchedule 설명 : [PLM System 1차개선] Project 일정 조회
     * 
     * @param req
     * @param res
     * @param cmd
     *            작성자 : BoLee 작성일자 : 2013. 6. 12.
     */
    @SuppressWarnings("unchecked")
    private void getProjectSchedule(HttpServletRequest req, HttpServletResponse res, String cmd) {

	StringBuffer strHeadBuffer = new StringBuffer();
	StringBuffer strHeadBuffer1 = new StringBuffer();
	StringBuffer strBodyBuffer = new StringBuffer();
	String screenWidth = "";
	String screenHeight = "";
	String oid = "";
	String projectName = "";
	String partName = "";
	String taskId = "";
	String taskName = "";
	String taskNameEn = "";
	String planStartDate = "";
	String planEndDate = "";
	String execStartDate = "";
	String execEndDate = "";
	String taskCompletion = "";
	String taskPreferComp = "";
	String planWorkTime = "";
	String personRole = "";
	String taskMaster = "";
	String taskMasterId = "";
	String taskMember = "";
	String taskMemberId = "";
	String milestoneType = "";
	String optionType = "";
	String taskType = "";
	String taskTypeCategory = "";
	String scheduleType = "";
	String priorityControl = "";
	String mainScheduleCode = "";
	String drValue = "";
	String parentHierarchy = "";
	String parentTaskOid = "";
	String isLeaf = "";
	String taskOid = "";
	String taskAncestors = "";
	String oemEndDate = "";
	String oemDisplayDate = "";
	String oemName = "";
	String oemEndDateStr = "";
	String taskNameFileIconLink = "";
	String taskMasterSearchIconLink = "";
	String taskMasterDeleteIconLink = "";
	String taskMemberSearchIconLink = "";
	String taskMemberDeleteIconLink = "";
	String fileIcon = "/plm/portal/images/img_default/button/but2_list.gif";
	String searchIcon = "/plm/portal/images/icon_5.png";
	String deleteIcon = "/plm/portal/images/icon_delete.gif";
	String personRoleEnumKey = "";
	String personRoleEnum = "";
	String milestoneTypeEnumKey = "";
	String milestoneTypeEnum = "";
	String optionTypeEnumKey = "";
	String optionTypeEnum = "";
	String taskTypeEnumKey = "";
	String taskTypeEnum = "";
	String scheduleTypeEnumKey = "";
    	String scheduleTypeEnum = "";
    	
    	String priorityControlEnumKey = "||Y";
    	String priorityControlEnum = "||Y";
    
    	String mainScheduleCodeEnumKey = "";
    	String mainScheduleCodeEnum = "";
	
	String debugSubEnumKey = "";
	String debugSubEnum = "";
	String debugNEnumKey = "";
	String debugNEnum = "";
	String teamName = "";
	String roleKey = "";
	String roleName = "";
	String loginUserId = "";
	String making = "";
	String debugTaskNames = "";
	String taskCompletionDate = "";
	String execDate = "";
	String ganttChartMaxStart = "";
	String ganttChartMinEnd = "";
	String taskStateImg = "";
	
	String costRequest = "";

	/******** 고객사 일정 관리 ********/
	/********* 2013.09.26 *********/
	String customerName = "";
	String planGubun = "";
	String planDate = "";
	String[] planGubunArray = null;
	String[] planDateArray = null;
	ArrayList<KETParamMapUtil> customerTaskScheduleList = null;
	/****************************/

	String[] ancestorTaskList = null;
	int planDuration = 0;
	int taskLevel = 0;
	int preTaskLevel = 0;
	int taskSeq = 0;
	int taskState = 0;
	int debugSub = 0;
	int debugN = 0;
	int ncha = 0;
	int idx = 0;
	int addFlowDuration = 0;
	Double calcFlow = 0.0;
	long oidLong = 0;
	long carTypeOidLong = 0;
	long modelPlanOidLong = 0;
	boolean delayTask = false;
	Object obj = null;
	KETParamMapUtil projectInfoMap = KETParamMapUtil.getMap();
	KETParamMapUtil projectRootScheduleMap = null;
	ArrayList<KETParamMapUtil> projectTaskScheduleList = null;
	Connection conn = null;
	E3PSProject project = null;
	CustomerPlan customerPlan = null;
	E3PSTask e3psTask = null;
	E3PSTask ancestorTask = null;
	E3PSTaskData e3psTaskData = null;
	TemplateTask tempTask = null;
	ProjectScheduleDao projectScheduleDao = null;
	OEMProjectType carType = null;
	Object resultObj[] = null;
	SearchCondition sCond = null;
	QuerySpec qSpec = null;
	QueryResult qResult = null;
	QueryResult masterListResult = null;
	QueryResult memberListResult = null;
	ModelPlan modelPlan = null;
	OEMPlan oemPlan = null;
	TaskMemberLink templateTaskLink = null;
	ProjectMemberLink templateProjectLink = null;
	WTUser projectUser = null;
	Vector<Role> vecTeamStd = new Vector<Role>();
	TeamTemplate tempTeam = null;
	Role role = null;
	KETMessageService messageService = KETMessageService.getMessageService(req);
	KETParamMapUtil enumMap = null;
	ArrayList<KETParamMapUtil> enumList = new ArrayList<KETParamMapUtil>();
	Map<String, Object> parameter = new HashMap<String, Object>();
	List<Map<String, Object>> taskTypeNumCode = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> scheduleTypeNumCode = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> mainScheduleCodeNumCode = new ArrayList<Map<String, Object>>();
	
	MoldProject moldProject = null;
	MoldItemInfo moldInfo = null;
	ProductProject productProject = null;

	// 화면 사이즈
	screenWidth = ParamUtil.getStrParameter(req.getParameter("width"));
	screenHeight = ParamUtil.getStrParameter(req.getParameter("height"));

	// 로그인 사용자 정보 (OID)
	try {
	    loginUserId = CommonUtil.getOIDString((WTUser) SessionHelper.manager.getPrincipal());
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	// [START] 1. Project 정보 조회

	oid = ParamUtil.getStrParameter(req.getParameter("oid"));
	oidLong = CommonUtil.getOIDLongValue(oid); // OID로부터 IDA2A2 return

	obj = CommonUtil.getObject(oid); // OID로부터 Object return

	if (obj instanceof E3PSProject) {

	    project = (E3PSProject) obj;
	} else if (obj instanceof E3PSTask) {

	    e3psTask = (E3PSTask) obj;
	    project = (E3PSProject) e3psTask.getProject();
	}
	
	boolean taskCanEdit = true;

	if (project != null) {

	    if (project instanceof MoldProject) { // 금형 Project Name - PJT_NO (PART_NAME)

		partName = StringUtil.checkNull(((MoldProject) project).getMoldInfo().getPartName());

		projectName = project.getPjtNo() + "(" + partName + ")";
	    } else { // 검토, 제품 Project Name - PJT_NO (PJT_NAME)
		projectName = project.getPjtNo() + "(" + project.getPjtName() + ")";
	    }
	    
	    if (project instanceof ProductProject) {
		try {
	            if((!project.getLifeCycleState().toString().equals("PMOINWORK") && project.getPjtType() != 4 && !CommonUtil.isAdmin()) &&("DR".equals(taskType) || "GATE".equals(taskType))){
	                //DR, GATE Task 수정 불가 처리 (전자사업부제외 , 등록중 상태 프로젝트 제외, ADMIN은 수정가능)
	                taskCanEdit = false;
	            }
                } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
                }
	    }
	}

	// [END] 1. Project 정보 조회

	// [START] 2. Head Data 구성 - 자동차 일정

	// 금형 Project일 경우 제품 Project 자동차 일정 조회

	if (project instanceof MoldProject) {

	    moldProject = (MoldProject) project;
	    moldInfo = moldProject.getMoldInfo();

	    if (moldInfo == null) {
		try {
		    moldInfo = MoldItemInfo.newMoldItemInfo();
		} catch (WTException e) {
		    Kogger.error(getClass(), e);
		}
	    }

	    if (moldInfo != null) {
		productProject = moldInfo.getProject();
	    }
	}

	if (project.getOemType() != null || (productProject != null && productProject.getOemType() != null)) { // 대표차종이 있을 경우에만 자동차 일정 표시

	    // 2-1. 대표차종 조회

	    if (project.getOemType() != null) {
		carType = project.getOemType();
	    } else if (productProject != null && productProject.getOemType() != null) {
		carType = productProject.getOemType();
	    }

	    carTypeOidLong = CommonUtil.getOIDLongValue(carType);

	    try {

		// 2-2. 대표차종의 자동차 일정 조회 - ModelPlan

		qSpec = new QuerySpec();
		idx = qSpec.addClassList(ModelPlan.class, true);
		sCond = new SearchCondition(ModelPlan.class, "carTypeReference.key.id", "=", carTypeOidLong);
		qSpec.appendWhere(sCond, new int[] { idx });
		qResult = PersistenceHelper.manager.find((StatementSpec) qSpec);

		if (qResult != null) {
		    if (qResult.hasMoreElements()) {

			resultObj = (Object[]) qResult.nextElement();
			modelPlan = (ModelPlan) resultObj[0];
			modelPlanOidLong = CommonUtil.getOIDLongValue(modelPlan);
		    }
		}

		// 2-3. 대표차종의 자동차 일정 조회 - OEMPlan

		qResult = null;
		qSpec = new QuerySpec();
		qSpec.addClassList(OEMPlan.class, true);
		sCond = new SearchCondition(OEMPlan.class, "modelPlanReference.key.id", "=", modelPlanOidLong);
		qSpec.appendWhere(sCond, new int[] { idx });
		qResult = PersistenceHelper.manager.find((StatementSpec) qSpec);

		// 2-4. 대표차종의 자동차 일정 Grid Data 구성

		strHeadBuffer.append("<I");
		// strHeadBuffer.append( " id=&quot;C&quot;" );
		strHeadBuffer
		        .append(" TaskName=&quot;"
		                + messageService.getString("e3ps.message.ket_message", "02404")/* 자동차일정 */
		                + "("
		                + carType.getName()
		                + ")&quot;"
		                + " TaskNameHtmlPrefix=&quot;&lt;b&gt;&lt;font color=&apos;#000000&apos;&gt;&quot; TaskNameHtmlPostfix=&quot;&lt;/font&gt;&lt;/b&gt;&quot;");

		if (qResult != null) {
		    while (qResult.hasMoreElements()) {

			resultObj = (Object[]) qResult.nextElement();
			oemPlan = (OEMPlan) resultObj[0];

			if (oemPlan.getOemType() != null) {

			    oemEndDate = DateUtil.getTimeFormat(oemPlan.getOemEndDate(), "yyyy-MM-dd");
			    oemName = oemPlan.getOemType().getName();

			    if (StringUtil.checkString(oemEndDate)) {
				oemDisplayDate = DateUtil.addDate(oemEndDate, 10);

				oemEndDateStr = oemEndDateStr + oemEndDate + "," + oemDisplayDate + ",," + oemName + ",,[" + oemName + "] "
				        + oemEndDate + ";";

				// Gantt Chart 표시 범위 설정
				if (!StringUtil.checkString(ganttChartMaxStart) || ganttChartMaxStart.compareTo(oemEndDate) >= 0) {
				    ganttChartMaxStart = oemEndDate;
				}
				if (!StringUtil.checkString(ganttChartMinEnd) || ganttChartMinEnd.compareTo(oemEndDate) <= 0) {
				    ganttChartMinEnd = oemEndDate;
				}
			    }
			}
		    }
		}

		strHeadBuffer.append(" OemPlan=&quot;" + oemEndDateStr + "&quot;");
		strHeadBuffer.append(" PlanDuration=&quot;&quot; TaskCompletion=&quot;&quot; TaskPreferComp=&quot;&quot;");
		strHeadBuffer.append(" PlanManHour=&quot;&quot; DRValue=&quot;&quot;");
		strHeadBuffer.append(" TaskNameCanEdit=&quot;0&quot; PlanStartDateCanEdit=&quot;0&quot; PlanEndDateCanEdit=&quot;0&quot;");
		strHeadBuffer
		        .append(" PlanDurationCanEdit=&quot;0&quot; TaskCompletionCanEdit=&quot;0&quot; PlanManHourCanEdit=&quot;0&quot;");
		strHeadBuffer.append(" PersonRoleCanEdit=&quot;0&quot; TaskMasterCanEdit=&quot;0&quot; TaskMemberCanEdit=&quot;0&quot;");
		strHeadBuffer
		        .append(" MilestoneTypeCanEdit=&quot;0&quot; OptionTypeCanEdit=&quot;0&quot; TaskTypeCanEdit=&quot;0&quot; TaskTypeCategoryCanEdit=&quot;0&quot; MainScheduleCodeCanEdit=&quot;0&quot; ScheduleTypeCanEdit=&quot;0&quot;");
		strHeadBuffer.append(" DRValueCanEdit=&quot;0&quot;");
		strHeadBuffer.append(" PriorityControlCanEdit=&quot;0&quot;");
		strHeadBuffer.append("/> \n");

		// 2-5. 고객사 일정 (2013.09.26)
		try {
		    // 커넥션 생성

		    conn = PlmDBUtil.getConnection();

		    projectScheduleDao = new ProjectScheduleDao(conn);

		    // 고객사 일정 조회
		    projectTaskScheduleList = projectScheduleDao.getCustomerSchedule(oidLong);

		    // [START] 고객사 Grid Data 구성 for loop
		    for (KETParamMapUtil taskInfoMap : projectTaskScheduleList) {
			// Grid Data 구성을 위한 고객사 정보
			customerName = taskInfoMap.getString("customerName");
			planGubun = taskInfoMap.getString("ini_sample");
			planDate = taskInfoMap.getString("ini_date");
			planGubunArray = planGubun.split("\\|");
			planDateArray = planDate.split("\\|");

			strHeadBuffer.append("<I");
			strHeadBuffer
			        .append(" TaskName=&quot;"
			                + messageService.getString("e3ps.message.ket_message", "00837")/* 고객사 */
			                + "("
			                + customerName
			                + ")&quot;"
			                + " TaskNameHtmlPrefix=&quot;&lt;b&gt;&lt;font color=&apos;#000000&apos;&gt;&quot; TaskNameHtmlPostfix=&quot;&lt;/font&gt;&lt;/b&gt;&quot;");
			oemEndDateStr = "";

			for (int i = 0; i < planGubunArray.length; i++) {
			    oemEndDate = planDateArray[i].toString();
			    oemName = planGubunArray[i].toString();
			    if (StringUtil.checkString(oemEndDate)) {
				oemDisplayDate = DateUtil.addDate(oemEndDate, 10);

				oemEndDateStr = oemEndDateStr + oemEndDate + "," + oemDisplayDate + ",," + oemName + ",,[" + oemName + "] "
				        + oemEndDate + ";";

				// Gantt Chart 표시 범위 설정
				if (!StringUtil.checkString(ganttChartMaxStart) || ganttChartMaxStart.compareTo(oemEndDate) >= 0) {
				    ganttChartMaxStart = oemEndDate;
				}
				if (!StringUtil.checkString(ganttChartMinEnd) || ganttChartMinEnd.compareTo(oemEndDate) <= 0) {
				    ganttChartMinEnd = oemEndDate;
				}
			    }
			}

			strHeadBuffer.append(" OemPlan=&quot;" + oemEndDateStr + "&quot;");
			strHeadBuffer.append(" PlanDuration=&quot;&quot; TaskCompletion=&quot;&quot; TaskPreferComp=&quot;&quot;");
			strHeadBuffer.append(" PlanManHour=&quot;&quot; DRValue=&quot;&quot;");
			strHeadBuffer
			        .append(" TaskNameCanEdit=&quot;0&quot; PlanStartDateCanEdit=&quot;0&quot; PlanEndDateCanEdit=&quot;0&quot;");
			strHeadBuffer
			        .append(" PlanDurationCanEdit=&quot;0&quot; TaskCompletionCanEdit=&quot;0&quot; PlanManHourCanEdit=&quot;0&quot;");
			strHeadBuffer
			        .append(" PersonRoleCanEdit=&quot;0&quot; TaskMasterCanEdit=&quot;0&quot; TaskMemberCanEdit=&quot;0&quot;");
			strHeadBuffer
			        .append(" MilestoneTypeCanEdit=&quot;0&quot; OptionTypeCanEdit=&quot;0&quot; TaskTypeCanEdit=&quot;0&quot; TaskTypeCategoryCanEdit=&quot;0&quot; MainScheduleCodeCanEdit=&quot;0&quot; ScheduleTypeCanEdit=&quot;0&quot;");
			strHeadBuffer.append(" DRValueCanEdit=&quot;0&quot;");
			strHeadBuffer.append(" PriorityControlCanEdit=&quot;0&quot;");
			strHeadBuffer.append("/> \n");

		    }
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}
	    } catch (WTException e) {
		Kogger.error(getClass(), e);
	    }finally {
	        PlmDBUtil.close(conn);
	    }
	}

	// [END] 2. Head Data 구성 - 자동차 일정

	// 3. Head Data 구성 - 프로그램 일정
	try {
	    QuerySpec sqs_ = new QuerySpec();
	    int idxSub_ = sqs_.addClassList(KETProgramSchedule.class, false);
	    ClassAttribute masterOidAttr_ = new ClassAttribute(KETProgramSchedule.class, "masterReference.key.id");
	    sqs_.appendSelect(masterOidAttr_, false);
	    ClassAttribute versionAttr_ = new ClassAttribute(KETProgramSchedule.class, "versionInfo.identifier.versionId");
	    SQLFunction max_ = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, versionAttr_);
	    sqs_.appendSelect(max_, false);
	    sqs_.appendGroupBy(new ClassAttribute(KETProgramSchedule.class, "masterReference.key.id"), new int[] { idxSub_ }, false);

	    QuerySpec qs_ = new QuerySpec();
	    qs_.setAdvancedQueryEnabled(true);
	    int idx0_ = qs_.addClassList(KETProgramSchedule.class, true);
	    int idx1_ = qs_.addClassList(KETProgramProjectLink.class, false);
	    qs_.appendSelect(new ClassAttribute(KETProgramProjectLink.class, KETProgramProjectLink.IS_BASE), false);
	    qs_.appendSelect(new ClassAttribute(KETProgramProjectLink.class, KETProgramProjectLink.IS_CHECKED_EVENT), false);

	    ColumnListExpression listExpression_ = new ColumnListExpression();
	    TableColumn masterOidAttr1_ = new TableColumn(qs_.getFromClause().getAliasAt(idx0_), "IDA3MASTERREFERENCE");
	    TableColumn versionAttr1_ = new TableColumn(qs_.getFromClause().getAliasAt(idx0_), "VERSIONIDA2VERSIONINFO");
	    listExpression_.addColumn(masterOidAttr1_);
	    listExpression_.addColumn(versionAttr1_);
	    SubSelectExpression selectExpression_ = new SubSelectExpression(sqs_);

	    selectExpression_.setAccessControlRequired(false);
	    SearchCondition sc_ = new SearchCondition(new ClassAttribute(KETProgramSchedule.class, WTAttributeNameIfc.VERSION_ID_NAME),
		    SearchCondition.EQUAL, new ClassAttribute(KETProgramProjectLink.class, WTAttributeNameIfc.ROLEB_VERSION_ID));
	    qs_.appendWhere(sc_, new int[] { idx0_, idx1_ });
	    qs_.appendAnd();
	    qs_.appendWhere(new SearchCondition(KETProgramSchedule.class, KETProgramSchedule.LATEST_ITERATION, SearchCondition.IS_TRUE),
		    new int[] { idx0_ });
	    qs_.appendAnd();
	    qs_.appendWhere(new SearchCondition(listExpression_, SearchCondition.IN, selectExpression_), new int[] { idx0_ });
	    qs_.appendAnd();
	    SearchCondition sc2_ = new SearchCondition(KETProgramProjectLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(oid));
	    qs_.appendWhere(sc2_, new int[] { idx1_ });
	    qs_.appendAnd();
	    qs_.appendWhere(new SearchCondition(KETProgramProjectLink.class, KETProgramProjectLink.IS_BASE, SearchCondition.IS_TRUE),
		    new int[] { idx1_ });

	    QueryResult result = PersistenceHelper.manager.find((StatementSpec) qs_);

	    String eventProgramName = "";
	    if (result != null) {
		if (result.hasMoreElements()) {
		    resultObj = (Object[]) result.nextElement();
		    KETProgramSchedule event = (KETProgramSchedule) resultObj[0];
		    eventProgramName = "[" + event.getCarType().getName() + "]" + "[" + event.getLastUsingBuyer().getName() + "]"
			    + event.getProgramName();

		}
	    }

	    if (resultObj != null) {
		QuerySpec sqs = new QuerySpec();
		int idxSub = sqs.addClassList(KETProgramSchedule.class, false);
		ClassAttribute masterOidAttr = new ClassAttribute(KETProgramSchedule.class, "masterReference.key.id");
		sqs.appendSelect(masterOidAttr, false);
		ClassAttribute versionAttr = new ClassAttribute(KETProgramSchedule.class, "versionInfo.identifier.versionId");
		SQLFunction max = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, versionAttr);
		sqs.appendSelect(max, false);
		sqs.appendGroupBy(new ClassAttribute(KETProgramSchedule.class, "masterReference.key.id"), new int[] { idxSub }, false);

		QuerySpec qs = new QuerySpec();
		qs.setAdvancedQueryEnabled(true);
		int idx0 = qs.addClassList(KETProgramSchedule.class, false);
		int idx1 = qs.addClassList(KETProgramProjectLink.class, false);
		int idx2 = qs.addClassList(KETProgramEvent.class, true);
		int idx3 = qs.addClassList(KETProgramEventLink.class, false);
		qs.appendSelect(new ClassAttribute(KETProgramProjectLink.class, KETProgramProjectLink.IS_BASE), false);
		qs.appendSelect(new ClassAttribute(KETProgramProjectLink.class, KETProgramProjectLink.IS_CHECKED_EVENT), false);

		ColumnListExpression listExpression = new ColumnListExpression();
		TableColumn masterOidAttr1 = new TableColumn(qs.getFromClause().getAliasAt(idx0), "IDA3MASTERREFERENCE");
		TableColumn versionAttr1 = new TableColumn(qs.getFromClause().getAliasAt(idx0), "VERSIONIDA2VERSIONINFO");
		listExpression.addColumn(masterOidAttr1);
		listExpression.addColumn(versionAttr1);
		SubSelectExpression selectExpression = new SubSelectExpression(sqs);

		selectExpression.setAccessControlRequired(false);
		SearchCondition sc = new SearchCondition(new ClassAttribute(KETProgramSchedule.class, WTAttributeNameIfc.VERSION_ID_NAME),
		        SearchCondition.EQUAL, new ClassAttribute(KETProgramProjectLink.class, WTAttributeNameIfc.ROLEB_VERSION_ID));
		qs.appendWhere(sc, new int[] { idx0, idx1 });
		qs.appendAnd();
		selectExpression.setAccessControlRequired(false);
		SearchCondition sc1 = new SearchCondition(new ClassAttribute(KETProgramSchedule.class, WTAttributeNameIfc.VERSION_ID_NAME),
		        SearchCondition.EQUAL, new ClassAttribute(KETProgramEventLink.class, WTAttributeNameIfc.ROLEB_VERSION_ID));
		qs.appendWhere(sc1, new int[] { idx0, idx3 });
		qs.appendAnd();
		SearchCondition sc3 = new SearchCondition(
		        new ClassAttribute(KETProgramEvent.class, "thePersistInfo.theObjectIdentifier.id"), SearchCondition.EQUAL,
		        new ClassAttribute(KETProgramEventLink.class, "roleAObjectRef.key.id"));
		qs.appendWhere(sc3, new int[] { idx2, idx3 });
		// qs.appendAnd();
		// qs.appendWhere(new SearchCondition(KETProgramEvent.class, KETProgramEvent.CUSTOMER_EVENT_DATE, SearchCondition.NOT_NULL),
		// new int[] { idx2 });
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(KETProgramSchedule.class, KETProgramSchedule.LATEST_ITERATION, SearchCondition.IS_TRUE),
		        new int[] { idx0 });
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(listExpression, SearchCondition.IN, selectExpression), new int[] { idx0 });
		qs.appendAnd();
		SearchCondition sc2 = new SearchCondition(KETProgramProjectLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL,
		        CommonUtil.getOIDLongValue(oid));
		qs.appendWhere(sc2, new int[] { idx1 });
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(KETProgramProjectLink.class, KETProgramProjectLink.IS_BASE, SearchCondition.IS_TRUE),
		        new int[] { idx1 });
		// qs.appendOrderBy(new OrderBy(new ClassAttribute(KETProgramSchedule.class, KETProgramSchedule.NUMBER), false),
		// new int[] { idx0 });

		Kogger.debug(getClass(), qs);

		strHeadBuffer1.append("<I");
		// strHeadBuffer.append( " id=&quot;C&quot;" );
		strHeadBuffer1
		        .append(" TaskName=&quot;"
		                + "프로그램 일정"/* 프로그램 일정 */
		                + "("
		                + eventProgramName
		                + ")&quot;"
		                + " TaskNameHtmlPrefix=&quot;&lt;b&gt;&lt;font color=&apos;#000000&apos;&gt;&quot; TaskNameHtmlPostfix=&quot;&lt;/font&gt;&lt;/b&gt;&quot;");

		qResult = PersistenceHelper.manager.find((StatementSpec) qs);
		String eventName = "";
		String eventDate = "";
		String eventDisplayDate = "";
		String eventDateStr = "";
		if (qResult != null) {
		    while (qResult.hasMoreElements()) {
			resultObj = (Object[]) qResult.nextElement();
			KETProgramEvent event = (KETProgramEvent) resultObj[0];
			if (event.getCustomerEventDate() != null) {
			    eventName = event.getCustomerEventName();
			    eventDate = DateUtil.getTimeFormat(event.getCustomerEventDate(), "yyyy-MM-dd");

			    if (StringUtil.checkString(eventDate)) {
				eventDisplayDate = DateUtil.addDate(eventDate, 10);

				eventDateStr = eventDateStr + eventDate + "," + eventDisplayDate + ",," + eventName + ",,[" + eventName
				        + "] " + eventDate + ";";

				// Gantt Chart 표시 범위 설정
				if (!StringUtil.checkString(ganttChartMaxStart) || ganttChartMaxStart.compareTo(eventDate) >= 0) {
				    ganttChartMaxStart = eventDate;
				}
				if (!StringUtil.checkString(ganttChartMinEnd) || ganttChartMinEnd.compareTo(eventDate) <= 0) {
				    ganttChartMinEnd = eventDate;
				}
			    }

			}

		    }
		}

		strHeadBuffer1.append(" OemPlan=&quot;" + eventDateStr + "&quot;");
		strHeadBuffer1.append(" PlanDuration=&quot;&quot; TaskCompletion=&quot;&quot; TaskPreferComp=&quot;&quot;");
		strHeadBuffer1.append(" PlanManHour=&quot;&quot; DRValue=&quot;&quot;");
		strHeadBuffer1.append(" TaskNameCanEdit=&quot;0&quot; PlanStartDateCanEdit=&quot;0&quot; PlanEndDateCanEdit=&quot;0&quot;");
		strHeadBuffer1
		        .append(" PlanDurationCanEdit=&quot;0&quot; TaskCompletionCanEdit=&quot;0&quot; PlanManHourCanEdit=&quot;0&quot;");
		strHeadBuffer1.append(" PersonRoleCanEdit=&quot;0&quot; TaskMasterCanEdit=&quot;0&quot; TaskMemberCanEdit=&quot;0&quot;");
		strHeadBuffer1
		        .append(" MilestoneTypeCanEdit=&quot;0&quot; OptionTypeCanEdit=&quot;0&quot; TaskTypeCanEdit=&quot;0&quot; TaskTypeCategoryCanEdit=&quot;0&quot; MainScheduleCodeCanEdit=&quot;0&quot; ScheduleTypeCanEdit=&quot;0&quot;");
		strHeadBuffer1.append(" DRValueCanEdit=&quot;0&quot;");
		strHeadBuffer.append(" PriorityControlCanEdit=&quot;0&quot;");
		strHeadBuffer1.append("/> \n");
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	// [END] 프로그램 일정

	try {
	    
	    String lang = messageService.getLocale().toString();

	    // [START] 3. Body Data 구성 - Project 일정

	    // 3-1. 커넥션 생성

	    conn = PlmDBUtil.getConnection();

	    projectScheduleDao = new ProjectScheduleDao(conn);

	    // 3-2. Project 일정 조회를 위한 Parameter 설정

	    projectInfoMap.put("oidLong", oidLong); // project oid(ida2a2)
	    projectInfoMap.put("project", project); // project 객체

	    // 3-3. Project 일정(Root) 조회
	    projectRootScheduleMap = projectScheduleDao.getProjectRootSchedule(projectInfoMap);

	    // 3-4. Project 일정(Root) Grid Data 구성

	    // 진행 중인 프로젝트 View 처리
	    String state = project.getLifeCycleState().getDisplay();

	    ProjectViewButtonAuth auth = null;
	    auth = new ProjectViewButtonAuth(project);

	    // 프로젝트 진행상태일 경우 편집 불가 (권한 상관없이)
	    if ("진행".equals(state)) {
		if (project.isWorkingCopy() && "진행".equals(state)) {
		    cmd = "search";
		} else {
		    cmd = "view";
		}
		// 일정변경, 등록중, 재작업 상태의 프로젝트인 경우- PM,PMO,ADMIN만 수정 가능
	    } else if ("일정변경".equals(state) || "등록중".equals(state) || "재작업".equals(state)) {
		if (auth.isPM || auth.isPMO || CommonUtil.isAdmin()) {
		    cmd = "search";
		} else {
		    cmd = "view";
		}
	    }

	    if (projectRootScheduleMap != null) {

		planStartDate = projectRootScheduleMap.getString("planStartDate");
		planEndDate = projectRootScheduleMap.getString("planEndDate");
		execStartDate = projectRootScheduleMap.getString("execStartDate");
		execEndDate = projectRootScheduleMap.getString("execEndDate");
		planDuration = projectRootScheduleMap.getInt("planDuration");
		taskOid = projectRootScheduleMap.getString("taskOid");
		taskCompletion = projectRootScheduleMap.getString("taskCompletion");
		taskPreferComp = projectRootScheduleMap.getString("preferComp");

		taskNameFileIconLink = "javascript:openUpdateProjectPopup(&apos;" + taskOid + "&apos;);";

		strBodyBuffer.append("<I");
		strBodyBuffer.append(" CanDelete=&quot;0&quot;"); // Project 삭제 불가 처리 (CanDelete='0')
		strBodyBuffer.append(" CanCopy=&quot;0&quot;"); // Project 복사 불가 처리 (CanCopy='0')
		strBodyBuffer.append(" CanCopyPaste=&quot;0&quot;"); // Project 붙여넣기 불가 처리 (CanCopyPaste='0')
		strBodyBuffer.append(" CanDrag=&quot;0&quot;"); // Project 이동 불가 처리 (CanDrag='0')
		strBodyBuffer.append(" id=&quot;0&quot");
		strBodyBuffer
		        .append(" TaskName=&quot;"
		                + projectName
		                + "&quot;"
		                + " TaskNameHtmlPrefix=&quot;&lt;b&gt;&lt;font color=&apos;#000000&apos;&gt;&quot; TaskNameHtmlPostfix=&quot;&lt;/font&gt;&lt;/b&gt;&quot;");

		if ("search".equals(cmd)) { // Project 일정 변경 화면에서만 Task 아이콘 표시
		    strBodyBuffer.append(" TaskNameButton=&quot;" + fileIcon + "&quot;" + " TaskNameOnDblClickSideButton=&quot;"
			    + taskNameFileIconLink + "&quot;");
		}

		strBodyBuffer.append(" PlanStartDate=&quot;" + planStartDate + "&quot;");
		strBodyBuffer.append(" PlanEndDate=&quot;" + planEndDate + "&quot;");
		strBodyBuffer.append(" ExecStartDate=&quot;" + execStartDate + "&quot;");
		strBodyBuffer.append(" ExecEndDate=&quot;" + execEndDate + "&quot;");

		// Project 실적시작일, 실적완료일 Gantt Flow 구성
		if (StringUtil.checkString(execStartDate)) {

		    execDate = execStartDate;

		    if (StringUtil.checkString(execEndDate)) {
			execDate = execDate + "~" + execEndDate;
		    } else {

			if (StringUtil.checkString(taskCompletion)) {

			    calcFlow = Math.floor(planDuration * (Float.parseFloat(taskCompletion) / 100)) - 1;

			    if (calcFlow < 0) {
				addFlowDuration = 0;
			    } else {
				addFlowDuration = (int) Math.floor(planDuration * (Float.parseFloat(taskCompletion) / 100)) - 1;
			    }

			    taskCompletionDate = DateUtil.addDate(execStartDate, addFlowDuration);
			    taskCompletionDate = taskCompletionDate.replaceAll("/", "-");

			    execDate = execDate + "~" + taskCompletionDate;
			}
		    }

		    strBodyBuffer.append(" ExecDate=&quot;" + execDate + "&quot;");
		}

		// Gantt Chart 표시 범위 설정

		if (!StringUtil.checkString(ganttChartMaxStart) || ganttChartMaxStart.compareTo(planStartDate) >= 0) {
		    ganttChartMaxStart = planStartDate;
		}
		if (!StringUtil.checkString(ganttChartMaxStart)
		        || (StringUtil.checkString(execStartDate) && ganttChartMaxStart.compareTo(execStartDate) >= 0)) {
		    ganttChartMaxStart = execStartDate;
		}
		if (!StringUtil.checkString(ganttChartMinEnd) || ganttChartMinEnd.compareTo(planEndDate) <= 0) {
		    ganttChartMinEnd = planEndDate;
		}
		if (!StringUtil.checkString(ganttChartMinEnd)
		        || (StringUtil.checkString(execEndDate) && ganttChartMinEnd.compareTo(execEndDate) <= 0)) {
		    ganttChartMinEnd = execEndDate;
		}

		if (StringUtil.checkString(ganttChartMaxStart)) {
		    ganttChartMaxStart = DateUtil.addDate(ganttChartMaxStart, -7);
		    ganttChartMaxStart = ganttChartMaxStart.replaceAll("/", "-");
		} else {
		    ganttChartMaxStart = DateUtil.addDate(DateUtil.getCurrentDateString(new SimpleDateFormat("yyyy-MM-dd")), -7);
		    ganttChartMaxStart = ganttChartMaxStart.replaceAll("/", "-");
		}

		if (StringUtil.checkString(ganttChartMinEnd)) {
		    ganttChartMinEnd = DateUtil.addDate(ganttChartMinEnd, 7);
		    ganttChartMinEnd = ganttChartMinEnd.replaceAll("/", "-");
		} else {
		    ganttChartMinEnd = DateUtil.addDate(DateUtil.getCurrentDateString(new SimpleDateFormat("yyyy-MM-dd")), 7);
		    ganttChartMinEnd = ganttChartMinEnd.replaceAll("/", "-");
		}

		strBodyBuffer.append(" PlanDuration=&quot;" + planDuration + "&quot;");
		strBodyBuffer.append(" PlanManHour=&quot;&quot;");
		strBodyBuffer.append(" DRValue=&quot;&quot;");
		strBodyBuffer.append(" TaskOid=&quot;" + taskOid + "&quot;");
		strBodyBuffer.append(" TaskCompletion=&quot;" + taskCompletion + "&quot;");
		strBodyBuffer.append(" TaskPreferComp=&quot;" + taskPreferComp + "&quot;");

		if (Float.parseFloat(StringUtil.checkNullZero(taskCompletion)) < 100) { // 진척률 색상 표시(100%가 아닐 경우)
		    strBodyBuffer
			    .append(" TaskCompletionHtmlPrefix=&quot;&lt;font color=&apos;teal&apos;&gt;&quot; TaskCompletionHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		}
		if (Float.parseFloat(StringUtil.checkNullZero(taskPreferComp)) < 100) { // 진행률 색상 표시(100%가 아닐 경우)
		    strBodyBuffer
			    .append(" TaskPreferCompHtmlPrefix=&quot;&lt;font color=&apos;purple&apos;&gt;&quot; TaskPreferCompHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		}

		strBodyBuffer.append(" TaskLevel=&quot;0&quot;");
		strBodyBuffer.append(" Def=&quot;Root&quot;");
		strBodyBuffer.append(" TaskNameCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" PlanDurationCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" PlanManHourCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" PersonRoleCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" MilestoneTypeCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" OptionTypeCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" TaskTypeCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" TaskTypeCategoryCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" MainScheduleCodeCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" ScheduleTypeCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" PriorityControlCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" DRValueCanEdit=&quot;0&quot;");
		strBodyBuffer.append("> \n");
	    }

	    // 3-5. Project Task 일정 조회
	    projectTaskScheduleList = projectScheduleDao.getProjectTaskSchedule(projectInfoMap);

	    // 3-6. Project Task 일정 조회 결과로 Grid Data 구성

	    boolean isEnLang = "en".equals(lang) && "view".equals(cmd) ;
	    // [START] Project Task Grid Data 구성 for loop
	    for (KETParamMapUtil taskInfoMap : projectTaskScheduleList) {

		// Grid Data 구성을 위한 Task 정보
		taskId = taskInfoMap.getString("id");
		taskName = taskInfoMap.getString("taskName");
		taskNameEn = taskInfoMap.getString("taskNameEn");
		if(isEnLang && !StringUtil.isEmpty(taskNameEn) ){
		    taskName = taskNameEn; 
		}
		planStartDate = taskInfoMap.getString("planStartDate");
		planEndDate = taskInfoMap.getString("planEndDate");
		execStartDate = taskInfoMap.getString("execStartDate");
		execEndDate = taskInfoMap.getString("execEndDate");
		planDuration = taskInfoMap.getInt("planDuration");
		taskCompletion = taskInfoMap.getString("taskCompletion");
		planWorkTime = taskInfoMap.getString("planWorkTime");
		personRole = taskInfoMap.getString("personRole");
		milestoneType = taskInfoMap.getString("milestoneType");
		optionType = taskInfoMap.getString("optionType");
		taskType = taskInfoMap.getString("taskType");
		taskTypeCategory = taskInfoMap.getString("taskTypeCategory");
		scheduleType = taskInfoMap.getString("scheduleType");
		priorityControl = taskInfoMap.getString("priorityControl");
		drValue = taskInfoMap.getString("drValue");
		taskLevel = taskInfoMap.getInt("taskLevel");
		parentHierarchy = taskInfoMap.getString("parentHierarchy");
		parentTaskOid = taskInfoMap.getString("parentTaskOid");
		isLeaf = taskInfoMap.getString("isLeaf");
		taskSeq = taskInfoMap.getInt("taskSeq");
		taskOid = taskInfoMap.getString("taskOid");
		taskAncestors = taskInfoMap.getString("taskAncestors");
		taskState = taskInfoMap.getInt("taskState");
		debugSub = taskInfoMap.getInt("debugSub");
		debugN = taskInfoMap.getInt("debugN");
		ncha = taskInfoMap.getInt("ncha");
		mainScheduleCode = taskInfoMap.getString("mainScheduleCode");
		costRequest = taskInfoMap.getString("costrequest");
		
		if(!"1".equals(isLeaf)) {
		    strBodyBuffer.append(" ScheduleTypeCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" PriorityControlCanEdit=&quot;0&quot;");
		}

		// Tree 구조에서 Summary Data 구성 완료 처리
		if (taskLevel < preTaskLevel) {

		    for (int i = 0; i < (preTaskLevel - taskLevel); i++) {

			strBodyBuffer.append("</I> \n");
		    }
		}

		// Task OID로부터 Task 객체 가져오기
		tempTask = (TemplateTask) CommonUtil.getObject(taskOid);

		// Task 책임자, 참여자 초기화
		taskMaster = "";
		taskMasterId = "";
		taskMember = "";
		taskMemberId = "";

		if (tempTask != null) {

		    // Task 책임자 가져오기

		    masterListResult = TaskUserHelper.manager.getMaster(tempTask);

		    while (masterListResult != null && masterListResult.hasMoreElements()) {

			templateTaskLink = (TaskMemberLink) ((Object[]) masterListResult.nextElement())[0];
			templateProjectLink = templateTaskLink.getMember();
			projectUser = templateProjectLink.getMember();

			if (projectUser != null) {

			    taskMaster = projectUser.getFullName();
			    taskMasterId = CommonUtil.getOIDString(projectUser);
			}
		    }

		    // Task 참여자 가져오기

		    memberListResult = TaskUserHelper.manager.getMember(tempTask);

		    while (memberListResult != null && memberListResult.hasMoreElements()) {

			templateTaskLink = (TaskMemberLink) ((Object[]) memberListResult.nextElement())[0];
			projectUser = templateTaskLink.getMember().getMember();

			if (projectUser != null) {

			    if (StringUtil.checkString(taskMemberId)) {

				taskMember = taskMember + ",";
				taskMemberId = taskMemberId + ",";
			    }

			    taskMember = taskMember + projectUser.getFullName();
			    taskMemberId = taskMemberId + CommonUtil.getOIDString(projectUser);
			}
		    }

		    // Task 정보 bean 가져오기
		    if (tempTask instanceof E3PSTask) {

			e3psTaskData = new E3PSTaskData((E3PSTask) tempTask);

			// Task 적정 진행율 가져오기
			taskPreferComp = e3psTaskData.getPreferCompStr();

			// Task 상태 Image 가져오기
			taskStateImg = e3psTaskData.getStateStr();

			// [START] 지연의 원인 Task 여부 체크 (지연 Task이고 지연된 FS 선행 Task가 없을 경우 지연의 원인 Task)

			if ("1".equals(isLeaf)) { // 최하위 Task에 대해서만 지연의 원인 Task 여부 체크

			    delayTask = false;

			    if (e3psTaskData.getStateBarType() == E3PSTaskData.STATE_BAR_DELAY) {

				delayTask = true;

				if (StringUtil.checkString(taskAncestors)) {

				    // 선행 Task 가져오기
				    ancestorTaskList = taskAncestors.split(";");

				    for (int x = 0; x < ancestorTaskList.length; x++) {

					if (!ancestorTaskList[x].contains("ss")) {

					    ancestorTask = (E3PSTask) CommonUtil.getObject("e3ps.project.E3PSTask:" + ancestorTaskList[x]);

					    if (new E3PSTaskData((E3PSTask) ancestorTask).getStateBarType() == E3PSTaskData.STATE_BAR_DELAY) {

						// 지연된 FS 선행 Task가 있을 경우 지연의 원인 Task가 아님
						delayTask = false;
					    }
					}
				    }
				}
			    }

			    if (delayTask) { // 지연의 원인 Task
				taskStateImg = taskStateImg.replaceAll("state_blank_bar.gif", "state_red_bar.gif");
			    }
			}

			// [END] 지연의 원인 Task 여부 체크 (지연 Task이고 지연된 FS 선행 Task가 없을 경우 지연의 원인 Task)
		    }
		}

		// Task 정보 조회 화면 Link
		// taskNameFileIconLink = "javascript:openUpdateTaskPopup(&apos;" + taskOid + "&apos;);";
		taskNameFileIconLink = "javascript:openViewTaskPopup(&apos;" + taskOid + "&apos;);";

		// Task 책임자 수정(Project 참여자 목록 선택) 화면 Link
		taskMasterSearchIconLink = "javascript:openSelectMemberPopup(&apos;" + oid + "&apos;, Row, &apos;Master&apos;);";
		// Task 책임자 삭제 처리
		taskMasterDeleteIconLink = "javascript:deleteTaskMember(Row, &apos;Master&apos;);";

		// Task 참여자 수정(Project 전체 인원 목록 선택) 화면 Link
		taskMemberSearchIconLink = "javascript:openSelectMemberPopup(&apos;" + oid + "&apos;, Row, &apos;Member&apos;);";
		// Task 참여자 삭제 처리
		taskMemberDeleteIconLink = "javascript:deleteTaskMember(Row, &apos;Member&apos;);";

		// Grid Data 구성
		strBodyBuffer.append("<I");
		strBodyBuffer.append(" id=&quot;" + taskId + "&quot;");

		// 완료 Task 회색 처리
		if (taskState == 5) { // 완료 Task

		    // 완료 Task 수정 불가 처리
		    strBodyBuffer.append(" TaskNameCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" TaskNameEnCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" PlanStartDateCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" PlanEndDateCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" PlanManHourCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" PersonRoleCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" MilestoneTypeCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" OptionTypeCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" TaskTypeCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" TaskTypeCategoryCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" MainScheduleCodeCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" ScheduleTypeCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" PriorityControlCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" DRValueCanEdit=&quot;0&quot;");

		    // 완료 Task 회색(#888888) 표시, 단계 Task명 Bold 처리
		    if (taskLevel == 1) {
			strBodyBuffer.append(" TaskNameHtmlPrefix=&quot;&lt;b&gt;&lt;font color=&apos;#888888&apos;&gt;&quot; TaskNameHtmlPostfix=&quot;&lt;/font&gt;&lt;/b&gt;&quot;");
			strBodyBuffer.append(" TaskNameEnHtmlPrefix=&quot;&lt;b&gt;&lt;font color=&apos;#888888&apos;&gt;&quot; TaskNameEnHtmlPostfix=&quot;&lt;/font&gt;&lt;/b&gt;&quot;");
		    } else {
			strBodyBuffer.append(" TaskNameHtmlPrefix=&quot;&lt;font color=&apos;#888888&apos;&gt;&quot; TaskNameHtmlPostfix=&quot;&lt;/font&gt;&quot;");
			strBodyBuffer.append(" TaskNameEnHtmlPrefix=&quot;&lt;font color=&apos;#888888&apos;&gt;&quot; TaskNameEnHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		    }

		} else {
		    // 완료 Task가 아닐 경우 검정색(#000000) 표시, 단계 Task명 Bold 처리
		    if (taskLevel == 1) {
			strBodyBuffer.append(" TaskNameHtmlPrefix=&quot;&lt;b&gt;&lt;font color=&apos;#000000&apos;&gt;&quot; TaskNameHtmlPostfix=&quot;&lt;/font&gt;&lt;/b&gt;&quot;");
			strBodyBuffer.append(" TaskNameEnHtmlPrefix=&quot;&lt;b&gt;&lt;font color=&apos;#000000&apos;&gt;&quot; TaskNameEnHtmlPostfix=&quot;&lt;/font&gt;&lt;/b&gt;&quot;");
		    } else {
			strBodyBuffer.append(" TaskNameHtmlPrefix=&quot;&lt;font color=&apos;#000000&apos;&gt;&quot; TaskNameHtmlPostfix=&quot;&lt;/font&gt;&quot;");
			strBodyBuffer.append(" TaskNameEnHtmlPrefix=&quot;&lt;font color=&apos;#000000&apos;&gt;&quot; TaskNameEnHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		    }
		    
		    if("1".equals(costRequest)){
			strBodyBuffer.append(" TaskTypeCanEdit=&quot;0&quot;");
			strBodyBuffer.append(" TaskTypeCategoryCanEdit=&quot;0&quot;");
			strBodyBuffer.append(" CanDelete=&quot;0&quot;");
		    }
		}
		
		if(!"1".equals(isLeaf)) {
		    strBodyBuffer.append(" ScheduleTypeCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" PriorityControlCanEdit=&quot;0&quot;");
		}
		
		if(!taskCanEdit){
//		    
//		    strBodyBuffer.append(" TaskNameCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" PlanStartDateCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" PlanEndDateCanEdit=&quot;0&quot;");
//		    strBodyBuffer.append(" PlanManHourCanEdit=&quot;0&quot;");
//		    strBodyBuffer.append(" PersonRoleCanEdit=&quot;0&quot;");
//		    strBodyBuffer.append(" MilestoneTypeCanEdit=&quot;0&quot;");
//		    strBodyBuffer.append(" OptionTypeCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" TaskTypeCanEdit=&quot;0&quot;");
//		    strBodyBuffer.append(" TaskTypeCategoryCanEdit=&quot;0&quot;");
//		    strBodyBuffer.append(" DRValueCanEdit=&quot;0&quot;");
		}

		if ("view".equals(cmd)) {

		    // Project 일정 조회 화면에서 로그인 사용자가 Task 책임자 또는 참여자일 경우 파랑색(#4398BC) 표시 (WTUser OID로 비교) - 완료 Task 제외
		    if (taskState != 5 && (loginUserId.equals(taskMasterId) || taskMemberId.contains(loginUserId))) {

			// 단계 Task명 Bold 처리
			if (taskLevel == 1) {
			    strBodyBuffer
				    .append(" TaskNameHtmlPrefix=&quot;&lt;b&gt;&lt;font color=&apos;#4398BC&apos;&gt;&quot; TaskNameHtmlPostfix=&quot;&lt;/font&gt;&lt;/b&gt;&quot;");
			} else {
			    strBodyBuffer
				    .append(" TaskNameHtmlPrefix=&quot;&lt;font color=&apos;#4398BC&apos;&gt;&quot; TaskNameHtmlPostfix=&quot;&lt;/font&gt;&quot;");
			}
		    }
		}

		strBodyBuffer.append(" TaskName=&quot;" + taskName + "&quot;");
		strBodyBuffer.append(" TaskNameEn=&quot;" + taskNameEn + "&quot;");

		if (taskLevel == 1) {

		    if ("view".equals(cmd)) { // Project 일정 조회 화면에서 단계 Task 라인 색상(#EDEDED) 표시
			strBodyBuffer.append(" Color=&quot;#EDEDED;&quot;");
		    } else if ("search".equals(cmd)) { // Project 일정 변경 화면에서 단계 Task Cell 색상(#EDEDED) 표시
			strBodyBuffer.append(" TaskNameBackground=&quot;#EDEDED;&quot;");
		    }
		}

		if (project instanceof MoldProject) {
		    // 금형프로젝트는 사업부 상관없이 금형프로젝트 Role이 나오도록 한다.
		    project.setPjtType(3);
		    MoldItemInfo info = ((MoldProject) project).getMoldInfo();
		    making = info.getMaking();

		    if ("사내".equals(making)) {

			debugTaskNames = ConfigImpl.getInstance().getString("debgingName_in");
		    } else {

			debugTaskNames = ConfigImpl.getInstance().getString("debgingName_out");
		    }

		    debugTaskNames = "|" + debugTaskNames.replaceAll(";", "|");

		    if (debugSub == 1) { // Debug Sub Task일 경우 Template Task List를 Enumeration Type으로 구성하여 Combobox에서 Task명 선택 입력

			strBodyBuffer.append(" TaskNameType=&quot;Enum&quot; TaskNameEnumKeys=&quot;" + debugTaskNames
			        + "&quot; TaskNameEnum=&quot;" + debugTaskNames + "&quot;");
		    }
		}

		strBodyBuffer.append(" TaskNameButton=&quot;" + fileIcon + "&quot;" + " TaskNameOnDblClickSideButton=&quot;"
		        + taskNameFileIconLink + "&quot;");

		strBodyBuffer.append(" PlanStartDate=&quot;" + planStartDate + "&quot;");
		strBodyBuffer.append(" PlanEndDate=&quot;" + planEndDate + "&quot;");
		strBodyBuffer.append(" ExecStartDate=&quot;" + execStartDate + "&quot;");
		strBodyBuffer.append(" ExecEndDate=&quot;" + execEndDate + "&quot;");

		// Task 실적시작일, 실적완료일 Gantt Flow 구성
		if (StringUtil.checkString(execStartDate)) {

		    execDate = execStartDate;

		    if (StringUtil.checkString(execEndDate)) {
			execDate = execDate + "~" + execEndDate;
		    } else {

			if (StringUtil.checkString(taskCompletion)) {

			    calcFlow = Math.floor(planDuration * (Float.parseFloat(taskCompletion) / 100)) - 1;

			    if (calcFlow < 0) {
				addFlowDuration = 0;
			    } else {
				addFlowDuration = (int) Math.floor(planDuration * (Float.parseFloat(taskCompletion) / 100)) - 1;
			    }

			    taskCompletionDate = DateUtil.addDate(execStartDate, addFlowDuration);
			    taskCompletionDate = taskCompletionDate.replaceAll("/", "-");

			    execDate = execDate + "~" + taskCompletionDate;
			}
		    }

		    strBodyBuffer.append(" ExecDate=&quot;" + execDate + "&quot;");
		}

		if ("search".equals(cmd)) {
		    strBodyBuffer
			    .append(" PlanDurationFormula=&quot;PlanStartDate ? Math.round(Grid.DiffGanttDate(PlanStartDate, PlanEndDate, 'd')) + 1 : ''&quot;");
		} else {
		    strBodyBuffer.append(" PlanDuration=&quot;" + planDuration + "&quot;");
		}

		// Task 상태 Image
		strBodyBuffer.append(" TaskStateImg=&quot;" + taskStateImg + "&quot;");

		strBodyBuffer.append(" TaskCompletion=&quot;" + taskCompletion + "&quot;");
		strBodyBuffer.append(" TaskPreferComp=&quot;" + taskPreferComp + "&quot;");

		if (Float.parseFloat(StringUtil.checkNullZero(taskCompletion)) < 100) { // 진척률 색상 표시(100%가 아닐 경우)
		    strBodyBuffer
			    .append(" TaskCompletionHtmlPrefix=&quot;&lt;font color=&apos;teal&apos;&gt;&quot; TaskCompletionHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		}
		if (Float.parseFloat(StringUtil.checkNullZero(taskPreferComp)) < 100) { // 진행률 색상 표시(100%가 아닐 경우)
		    strBodyBuffer
			    .append(" TaskPreferCompHtmlPrefix=&quot;&lt;font color=&apos;purple&apos;&gt;&quot; TaskPreferCompHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		}
		strBodyBuffer.append(" PlanWorkTime=&quot;" + planWorkTime + "&quot;");
		strBodyBuffer.append(" PersonRole=&quot;" + personRole + "&quot;");
		strBodyBuffer.append(" TaskMaster=&quot;" + taskMaster + "&quot;");

		if ("search".equals(cmd) && taskState != 5) { // Project 일정 변경 화면에서 완료된 Task가 아닐 경우에만 Task 책임자 수정 가능하도록 설정

		    strBodyBuffer.append(" TaskMasterIcon=&quot;" + searchIcon + "&quot;" + " TaskMasterIconAlign=&quot;Right&quot;"
			    + " TaskMasterOnClickSideIcon=&quot;" + taskMasterSearchIconLink + "&quot;" + " TaskMasterButton=&quot;"
			    + deleteIcon + "&quot;" + " TaskMasterOnClickSideButton=&quot;" + taskMasterDeleteIconLink + "&quot;");
		}

		strBodyBuffer.append(" TaskMasterId=&quot;" + taskMasterId + "&quot;");
		strBodyBuffer.append(" TaskMember=&quot;" + taskMember + "&quot;");

		if ("search".equals(cmd) && taskState != 5) { // Project 일정 변경 화면에서 완료된 Task가 아닐 경우에만 Task 참여자 수정 가능하도록 설정

		    strBodyBuffer.append(" TaskMemberIcon=&quot;" + searchIcon + "&quot;" + " TaskMemberIconAlign=&quot;Right&quot;"
			    + " TaskMemberOnClickSideIcon=&quot;" + taskMemberSearchIconLink + "&quot;" + " TaskMemberButton=&quot;"
			    + deleteIcon + "&quot;" + " TaskMemberOnClickSideButton=&quot;" + taskMemberDeleteIconLink + "&quot;");
		}

		strBodyBuffer.append(" TaskMemberId=&quot;" + taskMemberId + "&quot;");
		strBodyBuffer.append(" MilestoneType=&quot;" + milestoneType + "&quot;");
		strBodyBuffer.append(" OptionType=&quot;" + optionType + "&quot;");
		strBodyBuffer.append(" TaskType=&quot;" + taskType + "&quot;");
		strBodyBuffer.append(" MainScheduleCode=&quot;" + mainScheduleCode + "&quot;");
		strBodyBuffer.append(" ScheduleType=&quot;" + scheduleType + "&quot;");
		strBodyBuffer.append(" PriorityControl=&quot;" + priorityControl + "&quot;");
		strBodyBuffer.append(" DRValue=&quot;" + drValue + "&quot;");
		strBodyBuffer.append(" TaskLevel=&quot;" + taskLevel + "&quot;");
		strBodyBuffer.append(" ParentHierarchy=&quot;" + parentHierarchy + "&quot;");
		strBodyBuffer.append(" ParentTaskOid=&quot;" + parentTaskOid + "&quot;");
		strBodyBuffer.append(" TaskSeq=&quot;" + taskSeq + "&quot;");
		strBodyBuffer.append(" TaskOid=&quot;" + taskOid + "&quot;");
		strBodyBuffer.append(" TaskAncestors=&quot;" + taskAncestors + "&quot;");
		strBodyBuffer.append(" TaskState=&quot;" + taskState + "&quot;");
		strBodyBuffer.append(" DebugSub=&quot;" + debugSub + "&quot;");
		strBodyBuffer.append(" DebugN=&quot;" + debugN + "&quot;");
		strBodyBuffer.append(" Ncha=&quot;" + ncha + "&quot;");
		strBodyBuffer.append(" TaskTypeCategory=&quot;" + taskTypeCategory + "&quot;");

		// 필수 Task 삭제 불가 처리 (CanDelete='0')
		if ("1".equals(optionType) || "1".equals(costRequest)) {
		    strBodyBuffer.append(" CanDelete=&quot;0&quot;");
		}
		
		// Task Definition 설정 (0 : Summary, 1 : Task)
		if ("0".equals(isLeaf)) {

		    strBodyBuffer.append(" Def=&quot;Summary&quot;");
		    strBodyBuffer.append("> \n");
		} else {

		    strBodyBuffer.append(" Def=&quot;Task&quot;");
		    strBodyBuffer.append("/> \n");
		}

		// 다음 Task Data 비교 구성을 위한 Task 정보 저장
		preTaskLevel = taskLevel;

	    }
	    // [END] Project Task Grid Data 구성 for loop

	    // 3-7. Project 일정(Root) Grid Data 구성 완료
	    strBodyBuffer.append("</I> \n");

	    // [END] 3. Body Data 구성 - Project Task 일정

	    // [START] 4. Enumeration Type 구성

	    // 4-1. Role

	    // Team 정보
	    if (project != null) {
		if(project instanceof WorkProject){
		    teamName = ProjectHelper.getProjectTeam(5);
		}else{
		    teamName = ProjectHelper.getProjectTeam(project.getPjtType());    
		}
		
		tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), teamName);
	    }

	    // Team Role List
	    if (tempTeam != null) {
		vecTeamStd = tempTeam.getRoles();
	    }

	    if (vecTeamStd != null) {

		Collections.sort(vecTeamStd, new RoleComparator());

		// PM(금형개발) Role 추가

		enumMap = KETParamMapUtil.getMap();

		if (project instanceof MoldProject) { // 금형 Project : 금형개발 (화면에 표시되는 값만 '금형개발'로 표시)
		    enumMap.put("key", "PM");
		    enumMap.put("value", "금형개발");
		} else { // 검토/제품 Project : PM
		    enumMap.put("key", "PM");
		    enumMap.put("value", "PM");
		}

		enumList.add(enumMap);

		for (int i = 0; i < vecTeamStd.size(); i++) {

		    // Role

		    role = (Role) vecTeamStd.get(i);

		    roleKey = role.toString();
		    roleName = role.getDisplay(messageService.getLocale());

		    // Person Role Map List 구성

		    enumMap = KETParamMapUtil.getMap();

		    enumMap.put("key", roleKey);
		    enumMap.put("value", roleName);

		    enumList.add(enumMap);
		}
	    }

	    if (enumList != null) {
		personRoleEnumKey = KETGridUtil.getKeyEnum(enumList, true);
		personRoleEnum = KETGridUtil.getValueEnum(enumList, true);
	    }

	    // 4-2. Milestone 여부

	    enumList = new ArrayList<KETParamMapUtil>();
	    enumMap = KETParamMapUtil.getMap();

	    enumMap.put("key", 0);
	    enumMap.put("value", "N");

	    enumList.add(enumMap);

	    enumMap = KETParamMapUtil.getMap();

	    enumMap.put("key", 1);
	    enumMap.put("value", "Y");

	    enumList.add(enumMap);

	    milestoneTypeEnumKey = KETGridUtil.getKeyEnum(enumList, false);
	    milestoneTypeEnum = KETGridUtil.getValueEnum(enumList, false);

	    // 4-3. 필수여부

	    optionTypeEnumKey = KETGridUtil.getKeyEnum(enumList, false);
	    optionTypeEnum = KETGridUtil.getValueEnum(enumList, false);

	    // 4-4. Task 종류

	    if (project instanceof MoldProject) { // 금형 Project

		// 금형 Task 종류 (공통코드)
		parameter.clear();
		parameter.put("locale", messageService.getLocale());
		parameter.put("codeType", "MOLDTASKTYPE");
		taskTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
	    } else { // 검토, 제품 Project

		// Task 종류 (공통코드)
		parameter.clear();
		parameter.put("locale", messageService.getLocale());
		parameter.put("codeType", "TASKTYPE");
		taskTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
	    }

	    taskTypeEnumKey = KETGridUtil.getKeyEnumFromNumCode(taskTypeNumCode, false);
	    taskTypeEnum = KETGridUtil.getValueEnumFromNumCode(taskTypeNumCode, false);
	    
            parameter.clear();
            parameter.put("locale", messageService.getLocale());
            parameter.put("codeType", "TASKSCHEDULETYPE");
            scheduleTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
	    
	    scheduleTypeEnumKey = "|" + KETGridUtil.getKeyEnumFromNumCode(scheduleTypeNumCode, false);
	    scheduleTypeEnum = "|" + KETGridUtil.getValueEnumFromNumCode(scheduleTypeNumCode, false);
	        
	    parameter.clear();
            parameter.put("locale", messageService.getLocale());
            parameter.put("codeType", "MAINSCHEDULECODE");
            mainScheduleCodeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
	    
	    mainScheduleCodeEnumKey = "|" + KETGridUtil.getKeyEnumFromNumCode(mainScheduleCodeNumCode, false);
	    mainScheduleCodeEnum = "|" + KETGridUtil.getValueEnumFromNumCode(mainScheduleCodeNumCode, false);

	    // 4-5. Debug Sub Task 여부

	    debugSubEnumKey = KETGridUtil.getKeyEnum(enumList, false);
	    debugSubEnum = KETGridUtil.getKeyEnum(enumList, false);

	    // 4-6. Debug Task 여부

	    debugNEnumKey = KETGridUtil.getKeyEnum(enumList, false);
	    debugNEnum = KETGridUtil.getKeyEnum(enumList, false);

	    // [END] 4. Enumeration Type 구성

	    // 5. 결과 return

	    req.setAttribute("screenWidth", screenWidth); // 화면 너비
	    req.setAttribute("screenHeight", screenHeight); // 화면 높이
	    req.setAttribute("tgDataHead", strHeadBuffer.toString()); // 검색 결과 Head Data - 자동차 일정
	    req.setAttribute("tgDataHead1", strHeadBuffer1.toString()); // 검색 결과 Head Data - 프로그램 일정
	    req.setAttribute("tgDataBody", strBodyBuffer.toString()); // 검색 결과 Body Data - Project 일정
	    req.setAttribute("personRoleEnumKey", personRoleEnumKey); // Person Role Key Enumeration
	    req.setAttribute("personRoleEnum", personRoleEnum); // Person Role Value Enumeration
	    req.setAttribute("milestoneTypeEnumKey", milestoneTypeEnumKey); // Milestone 여부 Key Enumeration
	    req.setAttribute("milestoneTypeEnum", milestoneTypeEnum); // Milestone 여부 Value Enumeration
	    req.setAttribute("optionTypeEnumKey", optionTypeEnumKey); // 필수여부 Key Enumeration
	    req.setAttribute("optionTypeEnum", optionTypeEnum); // 필수여부 Value Enumeration
	    req.setAttribute("taskTypeEnumKey", taskTypeEnumKey); // Task 종류 Key Enumeration
	    req.setAttribute("taskTypeEnum", taskTypeEnum); // Task 종류 Value Enumeration
	    req.setAttribute("scheduleTypeEnumKey", scheduleTypeEnumKey); // Schedule Type Key Enumeration
	    req.setAttribute("scheduleTypeEnum", scheduleTypeEnum); // Schedule Type Value Enumeration
	    req.setAttribute("priorityControlEnumKey", priorityControlEnumKey); // priorityControl Key Enumeration
	    req.setAttribute("priorityControlEnum", priorityControlEnum); // priorityControl Value Enumeration
	    req.setAttribute("mainScheduleCodeEnumKey", mainScheduleCodeEnumKey); // mainScheduleCode Key Enumeration
	    req.setAttribute("mainScheduleCodeEnum", mainScheduleCodeEnum); // mainScheduleCode Value Enumeration
	    req.setAttribute("debugSubEnumKey", debugSubEnumKey); // Debug Sub Task 여부 Key Enumeration
	    req.setAttribute("debugSubEnum", debugSubEnum); // Debug Sub Task 여부 Value Enumeration
	    req.setAttribute("debugNEnumKey", debugNEnumKey); // Debug Task 여부 Key Enumeration
	    req.setAttribute("debugNEnum", debugNEnum); // Debug Task 여부 Value Enumeration
	    req.setAttribute("debugTaskEnumKey", debugTaskNames); // Debug Task Key Enumeration
	    req.setAttribute("debugTaskEnum", debugTaskNames); // Debug Task Value Enumeration
	    req.setAttribute("ganttChartMaxStart", ganttChartMaxStart); // Gantt Chart 표시 시작 날짜
	    req.setAttribute("ganttChartMinEnd", ganttChartMinEnd); // Gantt Chart 표시 완료 날짜

	    // 6. Servlet 호출 결과 Redirect
	    Kogger.debug(getClass(), "cmd >> " + cmd);
	    if ("view".equals(cmd)) {

		gotoResult(req, res, "/jsp/project/schedule/ViewProjectSchedule.jsp"); // Project 일정 조회 화면
	    } else if ("search".equals(cmd)) {

		gotoResult(req, res, "/jsp/project/schedule/ChangeProjectSchedule.jsp"); // Project 일정 변경 화면
	    }

	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	    e.printStackTrace();
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	    e.printStackTrace();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    e.printStackTrace();
	} finally {
	    PlmDBUtil.close(conn);
	}
    }

    /**
     * 함수명 : changeProjectState 설명 : [PLM System 1차개선] Project 일정 변경 상태 변경 (PLANCHANGE)
     * 
     * @param req
     * @param res
     *            작성자 : BoLee 작성일자 : 2013. 6. 20.
     */
    private void changeProjectState(HttpServletRequest req, HttpServletResponse res) {

	String oid = "";
	String popup = "";
	String viewUrl = "";
	String copyOid = "";
	String rtn_msg = "";
	PrintWriter out = null;
	Object obj = null;
	E3PSProject project = null;
	CheckoutLink checkoutLink = null;

	try {

	    oid = ParamUtil.getStrParameter(req.getParameter("oid"));
	    popup = ParamUtil.getStrParameter(req.getParameter("popup"));

	    obj = CommonUtil.getObject(oid); // OID로부터 Object return

	    if (obj instanceof E3PSProject) {

		project = (E3PSProject) obj;

		if (project instanceof ReviewProject) {
		    viewUrl = "/plm/jsp/project/ReviewProjectView.jsp";
		} else if (project instanceof ProductProject) {
		    viewUrl = "/plm/jsp/project/ProjectView.jsp";
		} else if (project instanceof MoldProject) {
		    viewUrl = "/plm/jsp/project/MoldProjectView.jsp";
		} else if (project instanceof WorkProject) {
		    viewUrl = "/plm/jsp/project/WorkProjectView.jsp";
		}
	    }

	    
	    QueryResult oemTypeQr = PersistenceHelper.manager.navigate(project, ProjectOemTypeLink.OEM_PJT_TYPE_ROLE, ProjectOemTypeLink.class, false);
	    // 일정변경 상태 변경 (체크아웃, Working Copy 생성) - 일정변경사유는 저장하지 않음
	    checkoutLink = (CheckoutLink) HistoryHelper.checkOut(project, "", 999, "PLANCHANGE");

	    // 일정변경 상태 변경 후 화면 이동

	    if (checkoutLink != null) {

		copyOid = CommonUtil.getOIDString(checkoutLink.getWorkingCopy());
		TemplateProject copyPjt = (TemplateProject) CommonUtil.getObject(copyOid);
		
        // 파생차종 복사
        while (oemTypeQr.hasMoreElements()) {
            ProjectOemTypeLink link = (ProjectOemTypeLink) oemTypeQr.nextElement();
            ProjectOemTypeLink copyLink = ProjectOemTypeLink.newProjectOemTypeLink(link.getOemPjtType(), copyPjt);
            PersistenceHelper.manager.save(copyLink);
        }

		out = res.getWriter();
		res.setContentType("text/html;charset=UTF-8");
		rtn_msg = "\n <script language=\"javascript\">";

		// if ("popup".equals(popup)) { // Project 상세정보 화면이 팝업일 경우

		rtn_msg += "\n     if ( parent.opener != null && parent.opener.document.location.href.indexOf('ProjectSearch.jsp') > 0) parent.opener.search(); ";
		rtn_msg += "\n     parent.document.location.href = '/plm/jsp/project/ProjectViewFrm.jsp?oid=" + copyOid + "&popup=popup';";
		// } else {
		// rtn_msg += "\n     parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=" + copyOid + "', '" + viewUrl
		// + "?oid=" + copyOid + "');";
		// }

		rtn_msg += "\n </script>";

		out.println(rtn_msg);
	    } else {
		throw new Exception("일정 변경이 실패하였습니다.");
	    }
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

    }

    /**
     * 함수명 : saveProjectSchedule 설명 : [PLM System 1차개선] Project 일정 저장
     * 
     * @param req
     * @param res
     *            작성자 : BoLee 작성일자 : 2013. 6. 20.
     */
    private void saveProjectSchedule(HttpServletRequest req, HttpServletResponse res) {

	// Form 데이터 받기
	FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	try {

	    // Project 일정 변경 화면에서 수정한 Project Task 일정 정보 저장
	    System.out.println("=============( Project 삭제되는 현상에 대한 원인 검증용 Logging!! )=============================");
	    System.out.println("@ Project 일정 변경 화면에서 수정한 Project Task 일정 정보 저장");
	    System.out.println("@ Before API Call : ProjectTaskScheduleHelper.updateTaskSchedule(paramMap) ");
		
	    String result = ProjectTaskScheduleHelper.updateTaskSchedule(paramMap);
	    
	    System.out.println("@ API Execution Done : ProjectTaskScheduleHelper.updateTaskSchedule(paramMap) ");
	    
	    String projectOid = (String) paramMap.get("Oid");
	    if (projectOid != null) {
		
		System.out.println("@ Before API Call : TaskHelper.manager.updateLeafTaskByProject(projectOid) ");
		TaskHelper.manager.updateLeafTaskByProject(projectOid);
		System.out.println("@ API Execution Done : TaskHelper.manager.updateLeafTaskByProject(projectOid) ");
	    }
	    req.setAttribute("result", result); // 일정 저장 결과

	    // Upload 처리 Page
	    gotoResult(req, res, "/jsp/project/schedule/ViewProjectScheduleGridUpload.jsp");
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * 함수명 : completeChangeProjectSchedule 설명 : [PLM System 1차개선] Project 일정 변경 완료 처리
     * 
     * @param req
     * @param res
     *            작성자 : BoLee 작성일자 : 2013. 7. 5.
     */
    private void completeChangeProjectSchedule(HttpServletRequest req, HttpServletResponse res) {

	String oid = "";
	String from = "";
	String popup = "";
	String viewUrl = "";
	String rtn_msg = "";
	PrintWriter out = null;
	Object obj = null;
	TemplateProject project = null;

	try {

	    oid = ParamUtil.getStrParameter(req.getParameter("oid"));
	    from = ParamUtil.getStrParameter(req.getParameter("from"));
	    popup = ParamUtil.getStrParameter(req.getParameter("popup"));

	    obj = CommonUtil.getObject(oid);

	    if (obj instanceof E3PSProject) {

		project = (E3PSProject) obj;

		if (project instanceof ReviewProject) {
		    viewUrl = "/plm/jsp/project/ReviewProjectView.jsp";
		} else if (project instanceof ProductProject) {
		    viewUrl = "/plm/jsp/project/ProjectView.jsp";
		} else if (project instanceof MoldProject) {
		    viewUrl = "/plm/jsp/project/MoldProjectView.jsp";
		}
	    }

	    // 1. 결재 프로세스 없이 Project Check In & Project 상태 변경 (PROGRESS)
	    if (project.isWorkingCopy()) {

		System.out.println("=============( Project 삭제되는 현상에 대한 원인 검증용 Logging!! )=============================");
		System.out.println("1. 결재 프로세스 없이 Project Check In & Project 상태 변경");
		System.out.println(" project.isWorkingCopy()  : "+project.isWorkingCopy());
		System.out.println(" Before API Call : HistoryHelper.checkInWithoutApproval(project) ");
		
		project = HistoryHelper.checkInWithoutApproval(project);
		
		System.out.println(" API Execution Done  : HistoryHelper.checkInWithoutApproval(project) ");
		System.out.println("===========================================================================================");
	    }

	    // TODO 2. 프로젝트 일정 변경 완료 공지 메일 발송
	    if (project instanceof E3PSProject) {

		System.out.println("=============( Project 삭제되는 현상에 대한 원인 검증용 Logging!! )=============================");
		System.out.println("2. 프로젝트 일정 변경 완료 공지 메일 발송");
		System.out.println(" Before API Call : ProjectHelper.approveMailWhenCompleteChangeProjectSchedule((E3PSProject) project) ");
		
		ProjectHelper.approveMailWhenCompleteChangeProjectSchedule((E3PSProject) project);
		
		System.out.println(" API Execution Done  : ProjectHelper.approveMailWhenCompleteChangeProjectSchedule((E3PSProject) project) ");
		System.out.println("===========================================================================================");
	    }

	    // 3. Project 일정 변경 완료 후 화면 처리

	    if (project != null) {

		out = res.getWriter();
		res.setContentType("text/html;charset=UTF-8");

		rtn_msg = "\n <script language=\"javascript\">";

		if ("ViewProject".equals(from)) { // Project 상세정보 화면에서 일정변경 완료한 경우

		    rtn_msg += "\n     if ( parent.opener != null ) parent.opener.document.location.href = parent.opener.document.location.href;";
		    rtn_msg += "\n     parent.document.location.href = '/plm/jsp/project/ProjectViewFrm.jsp?oid=" + oid + "&popup=popup';";
		} else { // Project 일정 변경 팝업 화면에서 일정변경 완료한 경우

		    rtn_msg += "\n     if ( opener.parent.parent.opener != null ) opener.parent.parent.opener.document.location.href = opener.parent.parent.opener.document.location.href;";
		    rtn_msg += "\n     opener.parent.parent.document.location.href = '/plm/jsp/project/ProjectViewFrm.jsp?oid=" + oid
			    + "&popup=popup';";
		}

		rtn_msg += "\n self.close();" + "\n </script>";

		out.println(rtn_msg);
	    } else {
		throw new Exception("일정 변경이 실패하였습니다.");
	    }
	} catch (WTInvalidParameterException e) {
	    Kogger.error(getClass(), e);
	} catch (LifeCycleException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

    }

    /**
     * 함수명 : getWBSSchedule 설명 : [PLM System 1차개선] WBS 일정 조회
     * 
     * @param req
     * @param res
     *            작성자 : BoLee 작성일자 : 2013. 7. 16.
     */
    @SuppressWarnings("unchecked")
    private void getWBSSchedule(HttpServletRequest req, HttpServletResponse res) {

	StringBuffer strBodyBuffer = new StringBuffer();
	String screenWidth = "";
	String screenHeight = "";
	String oid = "";
	String projectName = "";
	String taskId = "";
	String taskName = "";
	String taskNameEn = "";
	String planStartDate = "";
	String planEndDate = "";
	String personRole = "";
	String milestoneType = "";
	String optionType = "";
	String taskType = "";
	String scheduleType = "";
	String priorityControl = "";
	String mainScheduleCode = "";
	String drValue = "";
	String drValueCondition = "";
	String parentHierarchy = "";
	String parentTaskOid = "";
	String isLeaf = "";
	String taskOid = "";
	String taskAncestors = "";
	String taskNameFileIconLink = "";
	String fileIcon = "/plm/portal/images/img_default/button/but2_list.gif";
	String personRoleEnumKey = "";
	String personRoleEnum = "";
	String milestoneTypeEnumKey = "";
	String milestoneTypeEnum = "";
	String optionTypeEnumKey = "";
	String optionTypeEnum = "";
	String taskTypeEnumKey = "";
	String taskTypeEnum = "";
	String scheduleTypeEnumKey = "";
        String scheduleTypeEnum = "";
        String priorityControlEnumKey = "||Y";
        String priorityControlEnum = "||Y";
        String mainScheduleCodeEnumKey = "";
        String mainScheduleCodeEnum = "";
	String teamName = "";
	String roleKey = "";
	String roleName = "";
	int planDuration = 0;
	int taskLevel = 0;
	int preTaskLevel = 0;
	int taskSeq = 0;
	long oidLong = 0;
	Object obj = null;
	KETParamMapUtil projectInfoMap = KETParamMapUtil.getMap();
	KETParamMapUtil projectRootScheduleMap = null;
	ArrayList<KETParamMapUtil> projectTaskScheduleList = null;
	Connection conn = null;
	TemplateProject project = null;
	TemplateTask task = null;
	ProjectScheduleDao projectScheduleDao = null;
	Vector<Role> vecTeamStd = new Vector<Role>();
	TeamTemplate tempTeam = null;
	Role role = null;
	KETMessageService messageService = KETMessageService.getMessageService(req);
	KETParamMapUtil enumMap = null;
	ArrayList<KETParamMapUtil> enumList = new ArrayList<KETParamMapUtil>();
	Map<String, Object> parameter = new HashMap<String, Object>();
	List<Map<String, Object>> taskTypeNumCode = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> scheduleTypeNumCode = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> mainSheduleCodeNumCode = new ArrayList<Map<String, Object>>();

	String divide = req.getParameter("divide");
	int planWorkTime;
	int newType;
	int modifyType;
	int common;
	int mdraw;
	int hw;
	int sw;
	int m;
	int p;
	int buy;
	int system;
	String taskTypeCategory = "";

	// 화면 사이즈
	screenWidth = ParamUtil.getStrParameter(req.getParameter("width"));
	screenHeight = ParamUtil.getStrParameter(req.getParameter("height"));

	// [START] 1. WBS 정보 조회

	oid = ParamUtil.getStrParameter(req.getParameter("oid"));
	oidLong = CommonUtil.getOIDLongValue(oid); // OID로부터 IDA2A2 return

	obj = CommonUtil.getObject(oid); // OID로부터 Object return

	if (obj instanceof TemplateProject) {

	    project = (TemplateProject) obj;
	} else if (obj instanceof TemplateTask) {

	    task = (TemplateTask) obj;
	    project = (TemplateProject) task.getProject();
	}

	if (project != null) {

	    // WBS Name - PJT_NAME
	    projectName = project.getPjtName();
	}

	// [END] 1. WBS 정보 조회

	try {

	    // [START] 2. Body Data 구성 - WBS 일정

	    // 2-1. 커넥션 생성

	    conn = PlmDBUtil.getConnection();

	    projectScheduleDao = new ProjectScheduleDao(conn);

	    // 2-2. WBS 일정 조회를 위한 Parameter 설정

	    projectInfoMap.put("oidLong", oidLong); // WBS oid(ida2a2)
	    projectInfoMap.put("project", project); // WBS 객체

	    // 2-3. WBS 일정(Root) 조회
	    projectRootScheduleMap = projectScheduleDao.getWBSRootSchedule(projectInfoMap);

	    // 2-4. WBS 일정(Root) Grid Data 구성

	    if (projectRootScheduleMap != null) {

		planStartDate = projectRootScheduleMap.getString("planStartDate");
		planEndDate = projectRootScheduleMap.getString("planEndDate");
		planDuration = projectRootScheduleMap.getInt("planDuration");
		taskOid = projectRootScheduleMap.getString("taskOid");

		taskNameFileIconLink = "javascript:openUpdateWBSPopup(&apos;" + taskOid + "&apos;);"; // WBS 수정 팝업 오픈

		strBodyBuffer.append("<I");
		strBodyBuffer.append(" CanDelete=&quot;0&quot;"); // Project 삭제 불가 처리 (CanDelete='0')
		strBodyBuffer.append(" CanCopy=&quot;0&quot;"); // Project 복사 불가 처리 (CanCopy='0')
		strBodyBuffer.append(" CanCopyPaste=&quot;0&quot;"); // Project 붙여넣기 불가 처리 (CanCopyPaste='0')
		strBodyBuffer.append(" CanDrag=&quot;0&quot;"); // Project 이동 불가 처리 (CanDrag='0')
		strBodyBuffer.append(" id=&quot;0&quot");
		if ("modify".equals(divide)) {
		    strBodyBuffer.append(" TaskName=&quot;" + projectName + "&quot;"
			    + " TaskNameHtmlPrefix=&quot;&lt;b&gt;&quot; TaskNameHtmlPostfix=&quot;&lt;/b&gt;&quot;"
			    + " TaskNameButton=&quot;" + fileIcon + "&quot;" + " TaskNameOnDblClickSideButton=&quot;"
			    + taskNameFileIconLink + "&quot;");
		}
		strBodyBuffer.append(" PlanStartDate=&quot;" + planStartDate + "&quot;");
		strBodyBuffer.append(" PlanEndDate=&quot;" + planEndDate + "&quot;");
		strBodyBuffer.append(" PlanDuration=&quot;" + planDuration + "&quot;");
		strBodyBuffer.append(" DRValue=&quot;&quot;");
		strBodyBuffer.append(" DRValueCondition=&quot;&quot;");
		strBodyBuffer.append(" TaskOid=&quot;" + taskOid + "&quot;");
		strBodyBuffer.append(" TaskLevel=&quot;0&quot;");
		strBodyBuffer.append(" Def=&quot;Root&quot;");
		strBodyBuffer.append(" TaskNameCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" TaskNameEn=&quot;&quot;");
		strBodyBuffer.append(" TaskNameEnCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" PlanDurationCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" PersonRoleCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" MilestoneTypeCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" OptionTypeCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" NewCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" ModifyCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" CommonCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" MdrawCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" HWCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" SWCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" MCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" PCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" BuyCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" SystemCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" TaskTypeCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" TaskTypeCategoryCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" MainScheduleCodeCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" ScheduleTypeCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" PriorityControlCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" DRValueCanEdit=&quot;0&quot;");
		strBodyBuffer.append(" DRValueConditionCanEdit=&quot;0&quot;");
		strBodyBuffer.append("> \n");
	    }

	    // 2-5. WBS Task 일정 조회
	    projectTaskScheduleList = projectScheduleDao.getWBSTaskSchedule(projectInfoMap);

	    // 2-6. WBS Task 일정 조회 결과로 Grid Data 구성

	    // [START] WBS Task Grid Data 구성 for loop
	    for (KETParamMapUtil taskInfoMap : projectTaskScheduleList) {

		// Grid Data 구성을 위한 Task 정보
		taskId = taskInfoMap.getString("id");
		taskName = taskInfoMap.getString("taskName");
		taskNameEn = taskInfoMap.getString("taskNameEn");
		planStartDate = taskInfoMap.getString("planStartDate");
		planEndDate = taskInfoMap.getString("planEndDate");
		planDuration = taskInfoMap.getInt("planDuration");
		personRole = taskInfoMap.getString("personRole");
		milestoneType = taskInfoMap.getString("milestoneType");
		optionType = taskInfoMap.getString("optionType");
		taskType = taskInfoMap.getString("taskType");
		scheduleType = taskInfoMap.getString("scheduleType");
		priorityControl = taskInfoMap.getString("priorityControl");
		drValue = taskInfoMap.getString("drValue");
		drValueCondition = taskInfoMap.getString("drValueCondition"); 
		taskLevel = taskInfoMap.getInt("taskLevel");
		parentHierarchy = taskInfoMap.getString("parentHierarchy");
		parentTaskOid = taskInfoMap.getString("parentTaskOid");
		isLeaf = taskInfoMap.getString("isLeaf");
		taskSeq = taskInfoMap.getInt("taskSeq");
		taskOid = taskInfoMap.getString("taskOid");
		taskAncestors = taskInfoMap.getString("taskAncestors");

		planWorkTime = taskInfoMap.getInt("planWorkTime");
		newType = taskInfoMap.getInt("newType");
		modifyType = taskInfoMap.getInt("modifyType");
		common = taskInfoMap.getInt("common");
		mdraw = taskInfoMap.getInt("mdraw");
		m = taskInfoMap.getInt("m");
		p = taskInfoMap.getInt("p");
		hw = taskInfoMap.getInt("hw");
		sw = taskInfoMap.getInt("sw");
		buy = taskInfoMap.getInt("buy");
		system = taskInfoMap.getInt("system");
		taskTypeCategory = taskInfoMap.getString("taskTypeCategory");
		mainScheduleCode = taskInfoMap.getString("mainScheduleCode");

		// Tree 구조에서 Summary Data 구성 완료 처리
		if (taskLevel < preTaskLevel) {

		    for (int i = 0; i < (preTaskLevel - taskLevel); i++) {

			strBodyBuffer.append("</I> \n");
		    }
		}

		// Task 정보 조회 화면 Link
		taskNameFileIconLink = "javascript:openViewWBSTaskPopup(&apos;" + taskOid + "&apos;);"; // WBS Task 정보 조회 팝업 오픈

		// Grid Data 구성
		strBodyBuffer.append("<I");
		strBodyBuffer.append(" id=&quot;" + taskId + "&quot;");
		strBodyBuffer.append(" TaskName=&quot;" + taskName + "&quot;");
		strBodyBuffer.append(" TaskNameEn=&quot;" + taskNameEn + "&quot;");

		if (taskLevel == 1) { // 단계 Task

		    // Task명 Bold 처리
		    strBodyBuffer.append(" TaskNameHtmlPrefix=&quot;&lt;b&gt;&quot; TaskNameHtmlPostfix=&quot;&lt;/b&gt;&quot;");
		    strBodyBuffer.append(" TaskNameBackground=&quot;#EDEDED;&quot;");
		}

		if ("modify".equals(divide)) {
		    strBodyBuffer.append(" TaskNameButton=&quot;" + fileIcon + "&quot;" + " TaskNameOnDblClickSideButton=&quot;"
			    + taskNameFileIconLink + "&quot;");
		}
		strBodyBuffer.append(" PlanStartDate=&quot;" + planStartDate + "&quot;");
		strBodyBuffer.append(" PlanEndDate=&quot;" + planEndDate + "&quot;");
		strBodyBuffer.append(" PlanDuration=&quot;" + planDuration + "&quot;");
		strBodyBuffer.append(" PersonRole=&quot;" + personRole + "&quot;");
		strBodyBuffer.append(" MilestoneType=&quot;" + milestoneType + "&quot;");
		strBodyBuffer.append(" OptionType=&quot;" + optionType + "&quot;");
		strBodyBuffer.append(" TaskType=&quot;" + taskType + "&quot;");
		strBodyBuffer.append(" ScheduleType=&quot;" + scheduleType + "&quot;");
		strBodyBuffer.append(" PriorityControl=&quot;" + priorityControl + "&quot;");
		strBodyBuffer.append(" DRValue=&quot;" + drValue + "&quot;");
		strBodyBuffer.append(" DRValueCondition=&quot;" + drValueCondition + "&quot;");
		strBodyBuffer.append(" TaskLevel=&quot;" + taskLevel + "&quot;");
		strBodyBuffer.append(" ParentHierarchy=&quot;" + parentHierarchy + "&quot;");
		strBodyBuffer.append(" ParentTaskOid=&quot;" + parentTaskOid + "&quot;");
		strBodyBuffer.append(" TaskSeq=&quot;" + taskSeq + "&quot;");
		strBodyBuffer.append(" TaskOid=&quot;" + taskOid + "&quot;");
		strBodyBuffer.append(" TaskAncestors=&quot;" + taskAncestors + "&quot;");
		strBodyBuffer.append(" TaskTypeCategory=&quot;" + taskTypeCategory + "&quot;");
		strBodyBuffer.append(" MainScheduleCode=&quot;" + mainScheduleCode + "&quot;");
		strBodyBuffer.append(" PlanWorkTime=&quot;" + planWorkTime + "&quot;");
		strBodyBuffer.append(" New=&quot;" + newType + "&quot;");
		strBodyBuffer.append(" Modify=&quot;" + modifyType + "&quot;");
		strBodyBuffer.append(" Common=&quot;" + common + "&quot;");
		strBodyBuffer.append(" Mdraw=&quot;" + mdraw + "&quot;");
		strBodyBuffer.append(" HW=&quot;" + hw + "&quot;");
		strBodyBuffer.append(" SW=&quot;" + sw + "&quot;");
		strBodyBuffer.append(" M=&quot;" + m + "&quot;");
		strBodyBuffer.append(" P=&quot;" + p + "&quot;");
		strBodyBuffer.append(" Buy=&quot;" + buy + "&quot;");
		strBodyBuffer.append(" System=&quot;" + system + "&quot;");
		strBodyBuffer.append(" TaskSeq=&quot;" + taskSeq + "&quot;");
		strBodyBuffer.append(" TaskOid=&quot;" + taskOid + "&quot;");

		// 필수 Task 삭제 불가 처리 (CanDelete='0')
		if ("1".equals(optionType)) {
		    strBodyBuffer.append(" CanDelete=&quot;0&quot;");
		}

		// Task Definition 설정 (0 : Summary, 1 : Task)
		if ("0".equals(isLeaf)) {
		    strBodyBuffer.append(" ScheduleTypeCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" PriorityControlCanEdit=&quot;0&quot;");
		    strBodyBuffer.append(" Def=&quot;Summary&quot;");
		    strBodyBuffer.append("> \n");
		} else {
		    
		    strBodyBuffer.append(" Def=&quot;Task&quot;");
		    strBodyBuffer.append("/> \n");
		}

		// 다음 Task Data 비교 구성을 위한 Task 정보 저장
		preTaskLevel = taskLevel;

	    }
	    // [END] WBS Task Grid Data 구성 for loop

	    // 2-7. WBS 일정(Root) Grid Data 구성 완료
	    strBodyBuffer.append("</I> \n");

	    // [END] 2. Body Data 구성 - WBS Task 일정

	    // [START] 3. Enumeration Type 구성

	    // 3-1. Role

	    // Team 정보
	    if (project != null) {
		if (project instanceof MoldTemplateProject) {
		    teamName = ProjectHelper.getProjectTeam(3);
		} else {
		    if("work".equals(project.getMoldType())){
			teamName = ProjectHelper.getProjectTeam(5);
		    }else{
			teamName = ProjectHelper.getProjectTeam(project.getPjtType());
		    }
		    
		}

		Kogger.debug(getClass(), "teamName >> " + teamName);
		tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), teamName);
	    }

	    // Team Role List
	    if (tempTeam != null) {
		vecTeamStd = tempTeam.getRoles();
	    }

	    if (vecTeamStd != null) {

		Collections.sort(vecTeamStd, new RoleComparator());

		// PM(금형개발) Role 추가

		enumMap = KETParamMapUtil.getMap();

		if (project instanceof MoldTemplateProject) { // 금형 WBS : 금형개발 (화면에 표시되는 값만 '금형개발'로 표시)

		    enumMap.put("key", "PM");
		    enumMap.put("value", "금형개발");
		} else { // 검토/제품 WBS : PM
		    enumMap.put("key", "PM");
		    enumMap.put("value", "PM");
		}

		enumList.add(enumMap);

		for (int i = 0; i < vecTeamStd.size(); i++) {

		    // Role

		    role = (Role) vecTeamStd.get(i);

		    roleKey = role.toString();
		    roleName = role.getDisplay(messageService.getLocale());

		    // Person Role Map List 구성

		    enumMap = KETParamMapUtil.getMap();

		    enumMap.put("key", roleKey);
		    enumMap.put("value", roleName);

		    enumList.add(enumMap);
		}
	    }

	    if (enumList != null) {
		personRoleEnumKey = KETGridUtil.getKeyEnum(enumList, true);
		personRoleEnum = KETGridUtil.getValueEnum(enumList, true);
	    }

	    // 3-2. Milestone 여부

	    enumList = new ArrayList<KETParamMapUtil>();
	    enumMap = KETParamMapUtil.getMap();

	    enumMap.put("key", 0);
	    enumMap.put("value", "N");

	    enumList.add(enumMap);

	    enumMap = KETParamMapUtil.getMap();

	    enumMap.put("key", 1);
	    enumMap.put("value", "Y");

	    enumList.add(enumMap);

	    milestoneTypeEnumKey = KETGridUtil.getKeyEnum(enumList, false);
	    milestoneTypeEnum = KETGridUtil.getValueEnum(enumList, false);

	    // 3-3. 필수여부

	    optionTypeEnumKey = KETGridUtil.getKeyEnum(enumList, false);
	    optionTypeEnum = KETGridUtil.getValueEnum(enumList, false);

	    // 신규추가 여부

	    optionTypeEnumKey = KETGridUtil.getKeyEnum(enumList, false);
	    optionTypeEnum = KETGridUtil.getValueEnum(enumList, false);

	    String newTypeEnumKey = "";
	    String newTypeEnum = "";
	    String modifyTypeEnumKey = "";
	    String modifyTypeEnum = "";

	    enumList = new ArrayList<KETParamMapUtil>();
	    enumMap = KETParamMapUtil.getMap();

	    enumMap.put("key", 0);
	    enumMap.put("value", " ");

	    enumList.add(enumMap);

	    enumMap = KETParamMapUtil.getMap();

	    enumMap.put("key", 1);
	    enumMap.put("value", "√");

	    enumList.add(enumMap);

	    // 구분 - 신규
	    newTypeEnumKey = KETGridUtil.getKeyEnum(enumList, false);
	    newTypeEnum = KETGridUtil.getValueEnum(enumList, false);

	    // 구분 - Modify
	    modifyTypeEnumKey = KETGridUtil.getKeyEnum(enumList, false);
	    modifyTypeEnum = KETGridUtil.getValueEnum(enumList, false);

	    enumList = new ArrayList<KETParamMapUtil>();
	    enumMap = KETParamMapUtil.getMap();

	    enumMap.put("key", 0);
	    enumMap.put("value", " ");

	    enumList.add(enumMap);

	    enumMap = KETParamMapUtil.getMap();

	    enumMap.put("key", 1);
	    enumMap.put("value", "○");

	    enumList.add(enumMap);

	    enumMap = KETParamMapUtil.getMap();

	    enumMap.put("key", 2);
	    enumMap.put("value", "●");

	    enumList.add(enumMap);

	    /* 2014.07.15 카테고리 Enumeration Type 구성 */

	    String commonTypeEnumKey = "";
	    String commonTypeEnum = "";
	    String mdrawTypeEnumKey = "";
	    String mdrawTypeEnum = "";
	    String hwTypeEnumKey = "";
	    String hwTypeEnum = "";
	    String swTypeEnumKey = "";
	    String swTypeEnum = "";
	    String mTypeEnumKey = "";
	    String mTypeEnum = "";
	    String pTypeEnumKey = "";
	    String pTypeEnum = "";
	    String buyTypeEnumKey = "";
	    String buyTypeEnum = "";
	    String systemTypeEnumKey = "";
	    String systemTypeEnum = "";

	    // Category - 공통
	    commonTypeEnumKey = KETGridUtil.getKeyEnum(enumList, false);
	    commonTypeEnum = KETGridUtil.getValueEnum(enumList, false);

	    // Category - 기구
	    mdrawTypeEnumKey = KETGridUtil.getKeyEnum(enumList, false);
	    mdrawTypeEnum = KETGridUtil.getValueEnum(enumList, false);

	    // Category - HW
	    hwTypeEnumKey = KETGridUtil.getKeyEnum(enumList, false);
	    hwTypeEnum = KETGridUtil.getValueEnum(enumList, false);

	    // Category - SW
	    swTypeEnumKey = KETGridUtil.getKeyEnum(enumList, false);
	    swTypeEnum = KETGridUtil.getValueEnum(enumList, false);

	    // Category - M
	    mTypeEnumKey = KETGridUtil.getKeyEnum(enumList, false);
	    mTypeEnum = KETGridUtil.getValueEnum(enumList, false);

	    // Category - P
	    pTypeEnumKey = KETGridUtil.getKeyEnum(enumList, false);
	    pTypeEnum = KETGridUtil.getValueEnum(enumList, false);

	    // Category - 구매
	    buyTypeEnumKey = KETGridUtil.getKeyEnum(enumList, false);
	    buyTypeEnum = KETGridUtil.getValueEnum(enumList, false);

	    // Category - 설비
	    systemTypeEnumKey = KETGridUtil.getKeyEnum(enumList, false);
	    systemTypeEnum = KETGridUtil.getValueEnum(enumList, false);

	    // 3-4. Task 종류

	    if (project instanceof MoldTemplateProject) { // 금형 WBS

		// 금형 Task 종류 (공통코드)
		parameter.clear();
		parameter.put("locale", messageService.getLocale());
		parameter.put("codeType", "MOLDTASKTYPE");
		parameter.put("disabled", "Y");
		taskTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
	    } else { // 검토, 제품 WBS

		// Task 종류 (공통코드)
		parameter.clear();
		parameter.put("locale", messageService.getLocale());
		parameter.put("codeType", "TASKTYPE");
		taskTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
	    }

	    taskTypeEnumKey = KETGridUtil.getKeyEnumFromNumCode(taskTypeNumCode, false);
	    taskTypeEnum = KETGridUtil.getValueEnumFromNumCode(taskTypeNumCode, false);
	    
	    parameter.clear();
            parameter.put("locale", messageService.getLocale());
            parameter.put("codeType", "TASKSCHEDULETYPE");
            scheduleTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    	    
            scheduleTypeEnumKey = "|" + KETGridUtil.getKeyEnumFromNumCode(scheduleTypeNumCode, false);
            scheduleTypeEnum = "|" + KETGridUtil.getValueEnumFromNumCode(scheduleTypeNumCode, false);
            
            parameter.clear();
            parameter.put("locale", messageService.getLocale());
            parameter.put("codeType", "MAINSCHEDULECODE");
            mainSheduleCodeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    	    
            mainScheduleCodeEnumKey = "|" + KETGridUtil.getKeyEnumFromNumCode(mainSheduleCodeNumCode, false);
            mainScheduleCodeEnum = "|" + KETGridUtil.getValueEnumFromNumCode(mainSheduleCodeNumCode, false);
        
        
	    // [END] 3. Enumeration Type 구성

	    // 4. 결과 return

	    req.setAttribute("screenWidth", screenWidth); // 화면 너비
	    req.setAttribute("screenHeight", screenHeight); // 화면 높이
	    req.setAttribute("tgDataBody", strBodyBuffer.toString()); // 검색 결과 Body Data - Project 일정
	    req.setAttribute("personRoleEnumKey", personRoleEnumKey); // Person Role Key Enumeration
	    req.setAttribute("personRoleEnum", personRoleEnum); // Person Role Value Enumeration
	    req.setAttribute("milestoneTypeEnumKey", milestoneTypeEnumKey); // Milestone 여부 Key Enumeration
	    req.setAttribute("milestoneTypeEnum", milestoneTypeEnum); // Milestone 여부 Value Enumeration
	    req.setAttribute("optionTypeEnumKey", optionTypeEnumKey); // 필수여부 Key Enumeration
	    req.setAttribute("optionTypeEnum", optionTypeEnum); // 필수여부 Value Enumeration
	    req.setAttribute("taskTypeEnumKey", taskTypeEnumKey); // Task 종류 Key Enumeration
	    req.setAttribute("taskTypeEnum", taskTypeEnum); // Task 종류 Value Enumeration
	    req.setAttribute("scheduleTypeEnumKey", scheduleTypeEnumKey); // Schedule Type Key Enumeration
	    req.setAttribute("scheduleTypeEnum", scheduleTypeEnum); // Schedule Type Value Enumeration
	    req.setAttribute("priorityControlEnumKey", priorityControlEnumKey); // priorityControl Key Enumeration
	    req.setAttribute("priorityControlEnum", priorityControlEnum); // priorityControl Value Enumeration
	    req.setAttribute("mainScheduleCodeEnumKey", mainScheduleCodeEnumKey); // 주요일정식별코드 Key Enumeration
	    req.setAttribute("mainScheduleCodeEnum", mainScheduleCodeEnum); // 주요일정식별코드 Value Enumeration

	    req.setAttribute("newTypeEnumKey", newTypeEnumKey); // 신규여부 Key Enumeration
	    req.setAttribute("newTypeEnum", newTypeEnum); // 신규여부 Value Enumeration
	    req.setAttribute("modifyTypeEnumKey", modifyTypeEnumKey); // Modify여부 Key Enumeration
	    req.setAttribute("modifyTypeEnum", modifyTypeEnum); // Modify여부 Value Enumeration
	    req.setAttribute("commonTypeEnumKey", commonTypeEnumKey); // 공통여부 Key Enumeration
	    req.setAttribute("commonTypeEnum", commonTypeEnum); // 공통여부 Value Enumeration
	    req.setAttribute("mdrawTypeEnumKey", mdrawTypeEnumKey); // 기구여부 Key Enumeration
	    req.setAttribute("mdrawTypeEnum", mdrawTypeEnum); // 기구여부 Value Enumeration
	    req.setAttribute("hwTypeEnumKey", hwTypeEnumKey); // HW여부 Key Enumeration
	    req.setAttribute("hwTypeEnum", hwTypeEnum); // HW여부 Value Enumeration
	    req.setAttribute("swTypeEnumKey", swTypeEnumKey); // SW여부 Key Enumeration
	    req.setAttribute("swTypeEnum", swTypeEnum); // SW여부 Value Enumeration
	    req.setAttribute("mTypeEnumKey", mTypeEnumKey); // M여부 Key Enumeration
	    req.setAttribute("mTypeEnum", mTypeEnum); // M여부 Value Enumeration
	    req.setAttribute("pTypeEnumKey", pTypeEnumKey); // P여부 Key Enumeration
	    req.setAttribute("pTypeEnum", pTypeEnum); // P여부 Value Enumeration
	    req.setAttribute("buyTypeEnumKey", buyTypeEnumKey); // Buy여부 Key Enumeration
	    req.setAttribute("buyTypeEnum", buyTypeEnum); // Buy여부 Value Enumeration
	    req.setAttribute("systemTypeEnumKey", systemTypeEnumKey); // System여부 Key Enumeration
	    req.setAttribute("systemTypeEnum", systemTypeEnum); // System여부 Value Enumeration

	    req.setAttribute("divide", divide);
	    // 5. Servlet 호출 결과 Redirect
	    gotoResult(req, res, "/jsp/project/schedule/ChangeWBSScheduleNew.jsp"); // WBS 일정 변경 화면

	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(conn);
	}

    }

    /**
     * 함수명 : saveWBSSchedule 설명 : [PLM System 1차개선] WBS 일정 저장
     * 
     * @param req
     * @param res
     *            작성자 : BoLee 작성일자 : 2013. 7. 16.
     */
    private void saveWBSSchedule(HttpServletRequest req, HttpServletResponse res) {

	// Form 데이터 받기
	FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	try {

	    // WBS 일정 변경 화면에서 수정한 WBS Task 일정 정보 저장
	    ProjectTaskScheduleHelper.updateWBSTaskSchedule(paramMap);

	    // Upload 처리 Page
	    gotoResult(req, res, "/jsp/project/schedule/ViewWBSScheduleGridUpload.jsp");
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

}
