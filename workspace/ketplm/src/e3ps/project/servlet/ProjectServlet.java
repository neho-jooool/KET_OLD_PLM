package e3ps.project.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringEscapeUtils;

import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.introspection.WTIntrospectionException;
import wt.lifecycle.State;
import wt.org.WTUser;
import wt.pds.StatementSpec;
import wt.project.Role;
import wt.query.ClassAttribute;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.team.TeamHelper;
import wt.team.TeamTemplate;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.query.SearchUtil;
import e3ps.common.treegrid.TgPagingControl;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.ManageSequence;
import e3ps.common.util.NumberCodeUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamHash;
import e3ps.common.web.ParamUtil;
import e3ps.common.web.WebUtil;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.ews.dao.PartnerDao;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.PeopleData;
import e3ps.project.AssessTemplateTaskLink;
import e3ps.project.CheckoutLink;
import e3ps.project.CostInfo;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectHelper;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.Performance;
import e3ps.project.ProductInfo;
import e3ps.project.ProductProject;
import e3ps.project.ProjectChangeStop;
import e3ps.project.ProjectIssueLink;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ProjectOemTypeLink;
import e3ps.project.ProjectOutput;
import e3ps.project.ProjectProductInfoLink;
import e3ps.project.ProjectSubcontractorLink;
import e3ps.project.ReviewProject;
import e3ps.project.TaskAssessResult;
import e3ps.project.TaskOutputLink;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import e3ps.project.TrustProjectOutputLink;
import e3ps.project.TrustTemplateTaskLink;
import e3ps.project.Weights;
import e3ps.project.beans.E3PSProjectData;
import e3ps.project.beans.E3PSTaskData;
import e3ps.project.beans.MoldProjectHelper;
import e3ps.project.beans.OEMTypeHelper;
import e3ps.project.beans.PerformanceHelper;
import e3ps.project.beans.ProductProjectHelper;
import e3ps.project.beans.ProjectERPConMgr;
import e3ps.project.beans.ProjectOutputData;
import e3ps.project.beans.ProjectOutputHelper;
import e3ps.project.beans.ProjectScheduleHelper;
import e3ps.project.beans.ProjectStateFlag;
import e3ps.project.beans.ProjectTaskHelper;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.project.beans.SearchPagingProjectHelper;
import e3ps.project.cancel.CancelProject;
import e3ps.project.cancel.ProjectCancelLink;
import e3ps.project.customerPlan.beans.CustomerPlanHelper;
import e3ps.project.dao.ProjectDao;
import e3ps.project.machine.MoldMachine;
import e3ps.project.material.MoldMaterial;
import e3ps.project.outputtype.OEMPlan;
import e3ps.project.outputtype.OEMProjectType;
import e3ps.project.sap.ProductPrice;
import e3ps.project.sap.SAPMoldPrice;
import e3ps.sap.BudgetExpenseInterface;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.project.gate.entity.AssessResultOutputLink;
import ext.ket.project.gate.entity.GateAssRsltTaskLink;
import ext.ket.project.gate.entity.GateAssessResult;
import ext.ket.project.gate.entity.ProjectAssSheetLink;
import ext.ket.project.program.entity.KETProgramProjectLink;
import ext.ket.project.program.service.ProgramHelper;
import ext.ket.shared.log.Kogger;

public class ProjectServlet extends CommonServlet {
    private static final long serialVersionUID = 1L;

    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	String command = ParamUtil.getStrParameter(req.getParameter("command"));

	if (command.equalsIgnoreCase("create")) { // E3PSProject Create
	    // create(req, res);
	} else if (command.equalsIgnoreCase("Update")) { // E3PSProject Update
	    update(req, res);
	} else if (command.equalsIgnoreCase("delete")) { // E3PSProject Delete
	    delete(req, res);
	} else if (command.equalsIgnoreCase("select")) { // E3PSProject Select(설계변경 등 타 모듈에서 사용)
	    list(req, res);
	} else if (command.equalsIgnoreCase("updateProject")) { // E3PSProject ERPUpdate
	    updateProject(req, res);
	} else if (command.equalsIgnoreCase("historyUpdate")) { // 일정이력 수정
	    // historyUpdate(req, res);
	} else if (command.equalsIgnoreCase("myworkProject")) { // 나의 Project
	    myworkProject(req, res);
	} else if (command.equalsIgnoreCase("PLMupdate")) { // 나의 Project
	    PLMupdate(req, res);
	} else if (command.equalsIgnoreCase("disabled")) {
	    disabled(req, res);
	} else if (command.equalsIgnoreCase("enabled")) {
	    enabled(req, res);
	} else if (command.equalsIgnoreCase("excelDown")) {
	    excelDown(req, res);
	} else if (command.equalsIgnoreCase("projectExcelDown")) {
	    projectExcelDown(req, res);
	} else if (command.equalsIgnoreCase("searchGridData")) { // 프로젝트 검색(total Count를 위한 호출인 경우)
	    search(req, res, false);
	} else if (command.equalsIgnoreCase("searchGridPage")) { // 프로젝트 검색(paging)
	    search(req, res, true);
	} else if (command.equalsIgnoreCase("excelDownProject")) { // 프로젝트 검색 Excel Download
	    excelDownProject(req, res);
	} else if (command.equalsIgnoreCase("searchPeople")) { // 사람 검색
	    searchPeople(req, res);
	} else if (command.equalsIgnoreCase("searchAnalysisPeople")) { // 사람 검색
	    searchAnalysisPeople(req, res);
	} else if (command.equalsIgnoreCase("searchMyTask")) { // My Task
	    searchMyTask(req, res);
	} else if (command.equalsIgnoreCase("searchMyProject")) { // My Project
	    searchMyProject(req, res);
	} else { // Default List
	    list(req, res);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : My Project 검색 서블릿 적용 수정일자 : 2013. 7. 01 수정자 : 김종호
     */
    @SuppressWarnings("unchecked")
    private void searchMyProject(HttpServletRequest req, HttpServletResponse res) {
	QueryResult result = null;

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;
	String pjtType = "";

	try {

	    // 다국어 처리
	    KETMessageService messageService = KETMessageService.getMessageService(req);

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    String searchPjtNo = paramMap.getString("searchPjtNo");
	    String searchPjtName = paramMap.getString("searchPjtName");

	    String searchPjtState = paramMap.getString("pjtStateList");
	    if (searchPjtState == "" && paramMap.getString("searchYN") == "") {
		searchPjtState = "PROGRESS";
	    }
	    String searchPjtType = paramMap.getString("pjtTypeList");

	    String planStartStartDate = paramMap.getString("planStartStartDate");
	    String planStartEndDate = paramMap.getString("planStartEndDate");
	    String planEndStartDate = paramMap.getString("planEndStartDate");
	    String planEndEndDate = paramMap.getString("planEndEndDate");

	    HashMap<String, Object> hash = new HashMap<String, Object>();
	    hash.put("command", paramMap.getString("command"));

	    if (searchPjtNo.length() > 0) {
		hash.put("searchPjtNo", searchPjtNo);
	    }
	    if (searchPjtName.length() > 0) {
		hash.put("searchPjtName", searchPjtName);
	    }
	    if (planStartStartDate.length() > 0) {
		hash.put("planStartStartDate", planStartStartDate);
	    }
	    if (planStartEndDate.length() > 0) {
		hash.put("planStartEndDate", planStartEndDate);
	    }
	    if (planEndStartDate.length() > 0) {
		hash.put("planEndStartDate", planEndStartDate);
	    }
	    if (planEndEndDate.length() > 0) {
		hash.put("planEndEndDate", planEndEndDate);
	    }
	    if (searchPjtState != null && searchPjtState.trim().length() > 0 && !searchPjtState.equalsIgnoreCase("null")) {
		hash.put("searchPjtState", searchPjtState);
	    }
	    if (searchPjtType != null && searchPjtType.trim().length() > 0 && !searchPjtType.equalsIgnoreCase("null")) {
		hash.put("searchPjtType", searchPjtType);
	    }

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("myProjectSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("myProjectSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("myProjectSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(hash);
	    session.setAttribute("myProjectSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    result = SearchPagingProjectHelper.getMyProjectList(conditionList);

	    if (result.size() > 0) {

		Object resultObj[] = null;

		while (result.hasMoreElements()) {
		    resultObj = (Object[]) result.nextElement();
		    String className = (String) resultObj[0];
		    BigDecimal decimal = (BigDecimal) resultObj[1];
		    long id = decimal.longValue();

		    E3PSProject project = (E3PSProject) CommonUtil.getObject(className + ":" + id);
		    E3PSProjectData data = new E3PSProjectData(project);

		    if ("검토".equals(project.getPjtTypeName())) {
			pjtType = messageService.getString("e3ps.message.ket_message", "00716");// 검토
		    } else if ("제품".equals(project.getPjtTypeName())) {
			pjtType = messageService.getString("e3ps.message.ket_message", "02536");// 제품
		    } else if ("금형".equals(project.getPjtTypeName())) {
			pjtType = messageService.getString("e3ps.message.ket_message", "00997");// 금형
		    }

		    strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
		    strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		    strBuffer.append(" PjtType=&quot;" + pjtType + "&quot;");
		    strBuffer.append(" PjtNo=&quot;" + project.getPjtNo() + "&quot;" + " PjtNoOnClick=&quot;javascript:viewProject(&apos;"
			    + PersistenceHelper.getObjectIdentifier(project).getStringValue() + "&apos;);&quot;" + " PjtNoHtmlPrefix=&quot;&lt;font color=&apos;"
			    + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;&quot;" + " PjtNoHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		    strBuffer.append(" PjtName=&quot;" + project.getPjtName() + "&quot;");
		    strBuffer.append(" Buyer=&quot;" + data.dependence + "&quot;");
		    strBuffer.append(" PjtPlanStartDate=&quot;" + DateUtil.getDateString(data.pjtPlanStartDate, "d") + "&quot;");
		    strBuffer.append(" PjtPlanEndDate=&quot;" + DateUtil.getDateString(data.pjtPlanEndDate, "d") + "&quot;");
		    strBuffer.append(" PjtStatus=&quot;&lt;center&gt;" + data.getStateStr(data.stateKorea, false) + "&lt;/center&gt;&quot;");
		    strBuffer.append(" PjtState=&quot;" + project.getLifeCycleState().getDisplay(messageService.getLocale()) + "&quot;");
		    strBuffer.append("/>");
		}
	    }

	    req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    gotoResult(req, res, "/jsp/project/ListMyProject.jsp");
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : My Task 검색 서블릿 적용 수정일자 : 2013. 7. 01 수정자 : 김종호
     */
    @SuppressWarnings("unchecked")
    private void searchMyTask(HttpServletRequest req, HttpServletResponse res) {
	QueryResult result = null;

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;

	try {

	    // 다국어 처리
	    KETMessageService messageService = KETMessageService.getMessageService(req);

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    String searchPjtNo = paramMap.getString("searchPjtNo");
	    String searchTaskName = paramMap.getString("searchTaskName");

	    String searchPjtState = paramMap.getString("pjtStateList");
	    if (searchPjtState == "" && paramMap.getString("searchYN") == "") {
		searchPjtState = "PROGRESS";
	    }
	    String searchState = paramMap.getString("taskCompleteList");
	    if (searchState == "" && paramMap.getString("searchYN") == "") {
		searchState = "0";
	    }

	    String planStartStartDate = paramMap.getString("planStartStartDate");
	    String planStartEndDate = paramMap.getString("planStartEndDate");
	    String planEndStartDate = paramMap.getString("planEndStartDate");
	    String planEndEndDate = paramMap.getString("planEndEndDate");

	    HashMap<String, Object> hash = new HashMap<String, Object>();
	    hash.put("command", paramMap.getString("command"));

	    if (searchPjtState.length() > 0) {
		hash.put("searchPjtState", searchPjtState);
	    }
	    if (searchState.length() > 0) {
		hash.put("searchState", searchState);
	    }
	    if (searchTaskName.length() > 0) {
		hash.put("searchTaskName", searchTaskName);
	    }
	    if (searchPjtNo.length() > 0) {
		hash.put("searchPjtNo", searchPjtNo);
	    }
	    if (planStartStartDate.length() > 0) {
		hash.put("planStartStartDate", planStartStartDate);
	    }
	    if (planStartEndDate.length() > 0) {
		hash.put("planStartEndDate", planStartEndDate);
	    }
	    if (planEndStartDate.length() > 0) {
		hash.put("planEndStartDate", planEndStartDate);
	    }
	    if (planEndEndDate.length() > 0) {
		hash.put("planEndEndDate", planEndEndDate);
	    }
	    // 취소된 프로젝트는 조회하지 않기위함
	    hash.put("notEqualState", "WITHDRAWN");

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("myTaskSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("myTaskSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("myTaskSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(hash);
	    session.setAttribute("myTaskSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    result = SearchPagingProjectHelper.getMyTaskList(conditionList);

	    if (result.size() > 0) {

		Object resultObj[] = null;

		while (result.hasMoreElements()) {
		    resultObj = (Object[]) result.nextElement();
		    E3PSTask et = (E3PSTask) resultObj[0];
		    E3PSTaskData data = new E3PSTaskData(et);
		    E3PSProject project = (E3PSProject) et.getProject();

		    /*
	             * 개발 검토 전자 0 자동차 1 제품 자동차 2 전자 4 금형 3
	             */

		    String pjtType = messageService.getString("e3ps.message.ket_message", "02536");// 제품
		    if (project instanceof ReviewProject) {
			pjtType = messageService.getString("e3ps.message.ket_message", "00716");// 검토
		    } else if (project instanceof ProductProject) {
			pjtType = messageService.getString("e3ps.message.ket_message", "02536");// 제품
		    } else if (project instanceof MoldProject) {
			pjtType = messageService.getString("e3ps.message.ket_message", "00997");// 금형
		    }

		    String taskPlanStartDateStr = "";
		    String taskPlanEndDatStr = "";
		    ExtendScheduleData schedule = (ExtendScheduleData) et.getTaskSchedule().getObject();
		    if (schedule.getPlanStartDate() != null) {
			taskPlanStartDateStr = DateUtil.getDateString(schedule.getPlanStartDate(), "D");
		    }
		    if (schedule.getPlanEndDate() != null) {
			taskPlanEndDatStr = DateUtil.getDateString(schedule.getPlanEndDate(), "D");
		    }

		    String pjtStatus = "";
		    if (et.getTaskState() == 0) {
			pjtStatus = messageService.getString("e3ps.message.ket_message", "02735");// 진행중
		    } else if (et.getTaskState() == 5) {
			pjtStatus = messageService.getString("e3ps.message.ket_message", "02173");// 완료됨
		    }

		    strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
		    strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		    strBuffer.append(" PjtType=&quot;" + pjtType + "&quot;");
		    strBuffer.append(" PjtNo=&quot;" + et.getProject().getPjtNo() + "&quot;" + " PjtNoOnClick=&quot;javascript:viewProject(&apos;"
			    + PersistenceHelper.getObjectIdentifier(et).getStringValue() + "&apos;);&quot;" + " PjtNoHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor()
			    + "&apos;&gt;&quot;" + " PjtNoHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		    strBuffer.append(" PjtName=&quot;" + et.getProject().getPjtName() + "&quot;" + " PjtNameOnClick=&quot;javascript:viewProject(&apos;"
			    + PersistenceHelper.getObjectIdentifier(et).getStringValue() + "&apos;);&quot;" + " PjtNamHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor()
			    + "&apos;&gt;&quot;" + " PjtNamHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		    strBuffer.append(" TaskName=&quot;" + et.getTaskName() + "&quot;" + " TaskNameOnClick=&quot;javascript:viewProject(&apos;"
			    + PersistenceHelper.getObjectIdentifier(et).getStringValue() + "&apos;);&quot;" + " TaskNameHtmlPrefix=&quot;&lt;font color=&apos;"
			    + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;&quot;" + " TaskNameHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		    strBuffer.append(" TaskPlanStartDate=&quot;" + taskPlanStartDateStr + "&quot;");
		    strBuffer.append(" TaskPlanEndDate=&quot;" + taskPlanEndDatStr + "&quot;");
		    strBuffer.append(" TaskStatus=&quot;&lt;center&gt;" + data.getStateStr() + "&lt;/center&gt;&quot;");
		    strBuffer.append(" PjtStatus=&quot;" + pjtStatus + "&quot;");
		    strBuffer.append("/>");
		}
	    }

	    req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    gotoResult(req, res, "/jsp/project/ListMyTask.jsp");
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : 사람 검색 서블릿 적용 수정일자 : 2013. 7. 01 수정자 : 김종호
     */
    private void searchPeople(HttpServletRequest req, HttpServletResponse res) {

	Connection conn = null;
	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;

	try {
	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader.getFormParameters());

	    // [START] [PLM System 1차 고도화] 조직도 검색 처음 로딩시 화면만 나오게 하여 체감 속도를 높인다, 2014-06-17, 김태현
	    if (map != null && map.getString("init").equals("true")) {
		gotoResult(req, res, "/jsp/project/AddProjectPeopleList.jsp");

	    }
	    // [END] [PLM System 1차 고도화] 조직도 검색 처음 로딩시 화면만 나오게 하여 체감 속도를 높인다, 2014-06-17, 김태현

	    else {

		PeopleData pdata = null;
		String currentDutyCode = (pdata = new PeopleData((WTUser) SessionHelper.manager.getPrincipal())).dutycode;
		if (currentDutyCode == null)
		    currentDutyCode = "0";

		String oid = map.getString("oid");
		if (oid.equals("root") || oid.trim().equals("")) {
		    map.put("deptName", "");
		} else {
		    Department dept = (Department) CommonUtil.getObject(oid);
		    map.put("deptName", dept.getName());
		}

		if (map.getString("keyvalue").equals("")) {
		    if (oid.equals("") && pdata.department != null) {
			oid = pdata.department.getPersistInfo().getObjectIdentifier().toString();

			// Session DepartmentName
			map.put("deptName", pdata.departmentName);
		    }

		    if (!oid.equals("") && !oid.equals("root")) {
			// 하위 부서도 모두 출력되게 수정 ( 2006.04.04 Choi Seunghwan )
			ArrayList<Object> arr = new ArrayList<Object>();
			ArrayList list = e3ps.common.impl.TreeHelper.manager.getAllChildList(Department.class, (e3ps.common.impl.Tree) CommonUtil.getObject(oid), new ArrayList());
			for (int i = list.size() - 1; i > -1; i--) {
			    Department temp = (Department) list.get(i);
			    arr.add(CommonUtil.getOIDLongValue(temp));
			}
			arr.add(CommonUtil.getOIDLongValue(oid));

			map.put("deptOid", KETStringUtil.join(arr, ","));
			// 부서 클릭시에는 부서명으로 검색되지 않도록
			map.put("deptName", "");
		    }
		}

		if (!CommonUtil.isAdmin()) {
		    map.put("disable", "0");
		}

		// 커넥션 생성
		conn = PlmDBUtil.getConnection();
		ProjectDao dao = new ProjectDao(conn);

		List<Map<String, Object>> list = dao.searchPeople(map);

		for (Map<String, Object> obj : list) {
		    int totalCount = Integer.parseInt(obj.get("reviewCount").toString()) + Integer.parseInt(obj.get("protoCount").toString()) + Integer.parseInt(obj.get("pilotCount").toString())
			    + Integer.parseInt(obj.get("moldCount").toString());

		    String titleCount = "Total : " + totalCount + "&lt;br&gt;" + "검토&nbsp;  : " + obj.get("reviewCount") + "&lt;br&gt;" + "Proto : " + obj.get("protoCount") + "&lt;br&gt;"
			    + "Pilot : " + obj.get("pilotCount") + "&lt;br&gt;" + "금형&nbsp;  : " + obj.get("moldCount");

		    String fileIcon = "&nbsp;<img src='/plm/portal/images/img_default/button/but2_list.gif'/>&nbsp;";
		    String pjtHistory = fileIcon + "T:" + totalCount;

		    strBuffer.append("<I NoColor=\"2\" CanDelete=\"0\"");
		    strBuffer.append(" RowNum=\"" + rowCount++ + "\"");
		    strBuffer.append(" UserName=\"" + obj.get("userName") + "\"");
		    strBuffer.append(" DeptName=\"" + obj.get("deptName") + "\"");
		    strBuffer.append(" Duty=\"" + obj.get("duty") + "\"");
		    strBuffer.append(" Dutycode=\"" + obj.get("dutyCode") + "\"");
		    strBuffer.append(" PjtHistory=\"" + pjtHistory + "\" PjtHistoryTip=\"" + titleCount + "\"" + " PjtHistoryOnClick=\"javascript:viewTodo2(&apos;" + obj.get("wtuserOid")
			    + "&apos;);\"" + " PjtHistoryHtmlPrefix=\"&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;\"" + " PjtHistoryHtmlPostfix=\"&lt;/font&gt;\"");
		    strBuffer.append(" WtuserOID=\"" + obj.get("wtuserOid") + "\"");
		    strBuffer.append(" Poid=\"" + obj.get("peopleOid") + "\"");
		    strBuffer.append(" Doid=\"" + obj.get("deptOid") + "\"");
		    strBuffer.append(" Uid=\"" + obj.get("userId") + "\"");
		    strBuffer.append(" Email=\"" + obj.get("email") + "\"");
		    strBuffer.append("/>");
		}

		req.setAttribute("searchCondition", map); // 검색 화면에서 받은 검색조건
		req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
		gotoResult(req, res, "/jsp/common/treegrid/SearchGridPage.jsp");

	    }

	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
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

    /*
     * [PLM System 1차개선] 수정내용 : 사람 검색 서블릿 적용 수정일자 : 2013. 7. 01 수정자 : 김종호
     */
    private void searchAnalysisPeople(HttpServletRequest req, HttpServletResponse res) {

	Connection conn = null;
	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;

	try {
	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader.getFormParameters());

	    // [START] [PLM System 1차 고도화] 조직도 검색 처음 로딩시 화면만 나오게 하여 체감 속도를 높인다, 2014-06-17, 김태현
	    if (map != null && map.getString("init").equals("true")) {
		gotoResult(req, res, "/jsp/project/AddProjectPeopleList.jsp");

	    }
	    // [END] [PLM System 1차 고도화] 조직도 검색 처음 로딩시 화면만 나오게 하여 체감 속도를 높인다, 2014-06-17, 김태현

	    else {

		PeopleData pdata = null;
		String currentDutyCode = (pdata = new PeopleData((WTUser) SessionHelper.manager.getPrincipal())).dutycode;
		if (currentDutyCode == null)
		    currentDutyCode = "0";

		String oid = map.getString("oid");
		map.put("deptName", "선행연구팀");

		if (!CommonUtil.isAdmin()) {
		    map.put("disable", "0");
		}

		// 커넥션 생성
		conn = PlmDBUtil.getConnection();
		ProjectDao dao = new ProjectDao(conn);

		List<Map<String, Object>> list = dao.searchPeople(map);

		for (Map<String, Object> obj : list) {
		    int totalCount = Integer.parseInt(obj.get("reviewCount").toString()) + Integer.parseInt(obj.get("protoCount").toString()) + Integer.parseInt(obj.get("pilotCount").toString())
			    + Integer.parseInt(obj.get("moldCount").toString());

		    String titleCount = "Total : " + totalCount + "&lt;br&gt;" + "검토&nbsp;  : " + obj.get("reviewCount") + "&lt;br&gt;" + "Proto : " + obj.get("protoCount") + "&lt;br&gt;"
			    + "Pilot : " + obj.get("pilotCount") + "&lt;br&gt;" + "금형&nbsp;  : " + obj.get("moldCount");

		    String fileIcon = "&nbsp;<img src='/plm/portal/images/img_default/button/but2_list.gif'/>&nbsp;";
		    String pjtHistory = fileIcon + "T:" + totalCount;

		    strBuffer.append("<I NoColor=\"2\" CanDelete=\"0\"");
		    strBuffer.append(" RowNum=\"" + rowCount++ + "\"");
		    strBuffer.append(" UserName=\"" + obj.get("userName") + "\"");
		    strBuffer.append(" DeptName=\"" + obj.get("deptName") + "\"");
		    strBuffer.append(" Duty=\"" + obj.get("duty") + "\"");
		    strBuffer.append(" Dutycode=\"" + obj.get("dutyCode") + "\"");
		    strBuffer.append(" PjtHistory=\"" + pjtHistory + "\" PjtHistoryTip=\"" + titleCount + "\"" + " PjtHistoryOnClick=\"javascript:viewTodo2(&apos;" + obj.get("wtuserOid")
			    + "&apos;);\"" + " PjtHistoryHtmlPrefix=\"&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;\"" + " PjtHistoryHtmlPostfix=\"&lt;/font&gt;\"");
		    strBuffer.append(" WtuserOID=\"" + obj.get("wtuserOid") + "\"");
		    strBuffer.append(" Poid=\"" + obj.get("peopleOid") + "\"");
		    strBuffer.append(" Doid=\"" + obj.get("deptOid") + "\"");
		    strBuffer.append(" Uid=\"" + obj.get("userId") + "\"");
		    strBuffer.append(" Email=\"" + obj.get("email") + "\"");
		    strBuffer.append("/>");
		}

		req.setAttribute("searchCondition", map); // 검색 화면에서 받은 검색조건
		req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
		gotoResult(req, res, "/jsp/common/treegrid/SearchGridPage.jsp");

	    }

	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
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

    private String getCostTaskState(String oid) {
	E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);

	ExtendScheduleData scheduleData = (ExtendScheduleData) task.getTaskSchedule().getObject();

	String bomTaskState = "";

	if (scheduleData.getExecEndDate() != null) {
	    bomTaskState = "완료";
	} else {
	    Calendar today = Calendar.getInstance();

	    String todayStr = DateUtil.getDateString(today.getTime(), "d");

	    String planEndDateStr = DateUtil.getTimeFormat(scheduleData.getPlanEndDate(), "yyyy-MM-dd");

	    boolean todayBefore = todayStr.compareTo(planEndDateStr) > 0;
	    if (todayBefore) {
		bomTaskState = "지연";
	    } else {
		bomTaskState = "진행";
	    }
	}

	return bomTaskState;
    }

    private String getFontColor(String bomTaskState) {
	String fontColor = PropertiesUtil.getSearchGridLinkColor();
	if ("지연".equals(bomTaskState)) {
	    fontColor = "FC0000";
	} else if ("완료".equals(bomTaskState)) {
	    fontColor = "4CD743";
	}
	return fontColor;
    }

    /*
     * [PLM System 1차개선] 수정내용 : Project 검색 서블릿 적용 수정일자 : 2013. 7. 01 수정자 : 김종호 [PLM System 1차 고도화] 수정내용 : 속도개선(Server paging 처리) 수정일자 :
     * 2014. 6. 25 수정자 : sw775.park
     */
    @SuppressWarnings("unchecked")
    private void search(HttpServletRequest req, HttpServletResponse res, boolean isPaging) {

	// 다국어 처리
	KETMessageService messageService = KETMessageService.getMessageService(req);

	Connection conn = null;
	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;
	String resultStr = ""; // 검색 결과 제한 건수 초과 여부
	int resultSize = 0; // 검색 결과 표시 건수

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    // paging 처리
	    TgPagingControl pager = new TgPagingControl(isPaging, paramMap);

	    // 검토 / 제품 중에
	    if (paramMap.getString("radio").equals("1")) {
		// 사업부가 자동차 - bizUnit 1 / 사업부가 전자 - bizUnit 2
		if (paramMap.getString("dType1").equals("1")) {
		    paramMap.put("bizUnit", "1");
		} else if (paramMap.getString("dType1").equals("2")) {
		    paramMap.put("bizUnit", "2");
		}
	    } else if (paramMap.getString("radio").equals("2")) {
		// 사업부가 자동차 - bizUnit 1 / 사업부가 전자 - bizUnit 2
		if (paramMap.getString("dType2").equals("1")) {
		    paramMap.put("bizUnit", "1");
		} else if (paramMap.getString("dType2").equals("2")) {
		    paramMap.put("bizUnit", "2");
		}
	    }

	    // 쿼리스펙에 전달하기 위해서 HashMap으로 전환
	    HashMap<String, String> map = new HashMap<String, String>();
	    map.put("command", paramMap.getString("command"));
	    /** Command */
	    map.put("radio", paramMap.getString("radio"));
	    /** Project 유형 */
	    map.put("initType", paramMap.getString("radio"));
	    /** Project 유형 */
	    map.put("pjtType", paramMap.getString("radio"));
	    /** Project 유형 */
	    map.put("sap", paramMap.getString("sap"));
	    /** 비용 */

	    if (map.get("initType").toString().equals("1")) {
		/** Project No */
		map.put("pjtNo", paramMap.getString("pjtNo1"));
		/** 사업부 */
		map.put("dType", paramMap.getString("dType1"));
		/** 개발담당자 */
		map.put("setPm", paramMap.getString("setPm1"));
		/** 상태 */
		map.put("pjtState", KETParamMapUtil.toString(paramMap.getStringArray("pjtState1")));
		/** 제품구분 */
		map.put("productType", KETParamMapUtil.toString(paramMap.getStringArray("productType1")));
		/** 개발구분 */
		map.put("developType", KETParamMapUtil.toString(paramMap.getStringArray("developType1")));
		/** Project Name */
		map.put("pjtName", paramMap.getString("pjtName1"));
		/** 개발담당부서 */
		map.put("rdevDeptOid", paramMap.getString("devUserDeptOid1"));
		/** 개발시작일TYpe */
		map.put("developDateType", paramMap.getString("developDateType1"));
		/** 개발 시작일 */
		map.put("planStartStartDate", paramMap.getString("planStartStartDate1"));
		/** 개발 완료일 */
		map.put("planStartEndDate", paramMap.getString("planStartEndDate1"));
		/** 조립구분 */
		map.put("assembledType", KETParamMapUtil.toString(paramMap.getStringArray("assembledType1")));
		/** 최종사용처 */
		map.put("customerEvent", KETParamMapUtil.toString(paramMap.getStringArray("customerEvent1")));
		/** 고객처 */
		map.put("subcontractor", paramMap.getString("subcontractor1"));
		/** Rank */
		map.put("rank", KETParamMapUtil.toString(paramMap.getStringArray("rank1")));
		/** 원가프로그램연동여부 */
		map.put("isCost", paramMap.getString("isCost"));

	    } else if (map.get("initType").toString().equals("2")) {
		/** Project No */
		map.put("pjtNo", paramMap.getString("pjtNo2"));
		/** 사업부 */
		map.put("dType", paramMap.getString("dType2"));
		/** 개발담당자 */
		map.put("setPm", paramMap.getString("setPm2"));
		/** 제품구분 */
		map.put("productType", KETParamMapUtil.toString(paramMap.getStringArray("productType2")));
		/** 제품구분Level2 */
		map.put("productTypeLevel2", KETParamMapUtil.toString(paramMap.getStringArray("productTypeLevel2")));
		/** 시리즈 */
		map.put("series", KETParamMapUtil.toString(paramMap.getStringArray("series2")));
		/** 방수여부 */
		map.put("sealed", KETParamMapUtil.toString(paramMap.getStringArray("sealed2")));
		/** Part No */
		map.put("partNo", paramMap.getString("partNo2"));
		/** 상태 */
		map.put("pjtState", KETParamMapUtil.toString(paramMap.getStringArray("pjtState2")));
		/** 개발담당부서 */
		map.put("pdevUserDept", paramMap.getString("devUserDept2"));
		/** 개발담당부서 */
		map.put("pdevDeptOid", paramMap.getString("devUserDeptOid2"));
		/** Project Name */
		map.put("pjtName", paramMap.getString("pjtName2"));
		/** 개발구분 */
		map.put("developType", KETParamMapUtil.toString(paramMap.getStringArray("developType2")));
		/** 관리제품군 */
		map.put("manageProductType", KETParamMapUtil.toString(paramMap.getStringArray("manageProductType")));
		/** 개발목적1 */
		map.put("developePurpose1", KETParamMapUtil.toString(paramMap.getStringArray("developePurpose1")));
		/** 개발목적2 */
		map.put("developePurpose2", KETParamMapUtil.toString(paramMap.getStringArray("developePurpose2")));

		/** 개발단계 */
		map.put("process", KETParamMapUtil.toString(paramMap.getStringArray("process")));
		/** 최종사용처 */
		map.put("customerEvent", KETParamMapUtil.toString(paramMap.getStringArray("customerEvent2")));
		/** 개발시작일TYpe */
		map.put("developDateType", paramMap.getString("developDateType2"));
		/** 개발 시작일 */
		map.put("planStartStartDate", paramMap.getString("planStartStartDate2"));
		/** 개발 완료일 */
		map.put("planStartEndDate", paramMap.getString("planStartEndDate2"));
		/** 조립구분 */
		map.put("assembledType", KETParamMapUtil.toString(paramMap.getStringArray("assembledType2")));
		/** Rank */
		map.put("rank", KETParamMapUtil.toString(paramMap.getStringArray("rank2")));
		/** 고객처 */
		map.put("subcontractor", paramMap.getString("subcontractor2"));
		/** 대표차종 */
		map.put("carTypeInfo", paramMap.getString("carTypeInfo2"));

		/** 설계구분 */
		map.put("designType", paramMap.getString("designType"));
		System.out.println("developPatternType" + KETStringUtil.join(paramMap.getStringArray("developPatternType"), "|"));
		/** 개발유형 */
		map.put("developPatternType", KETStringUtil.join(paramMap.getStringArray("developPatternType"), "|"));
		/** 생산처 */
		map.put("manufacPlace", KETStringUtil.join(paramMap.getStringArray("manufacPlace"), "|"));
		/** 파생차종 **/
		map.put("oemOids", paramMap.getString("oemOids"));

	    } else if (map.get("initType").toString().equals("3")) {
		/** Die No */
		map.put("dieNo", paramMap.getString("dieNo3"));
		/** Project No */
		map.put("productpjtNo", paramMap.getString("pjtNo3"));
		/** Part No */
		map.put("partNo", paramMap.getString("partNo3"));
		/** 개발구분 */
		map.put("developType", KETParamMapUtil.toString(paramMap.getStringArray("developType3")));
		/** Part Name */
		map.put("partName", paramMap.getString("partName3"));
		/** 대표차종 */
		map.put("carTypeInfo", paramMap.getString("carTypeInfo3"));
		/** 금형구분 */
		map.put("itemType", KETParamMapUtil.toString(paramMap.getStringArray("itemType3")));
		/** 금형분류 */
		map.put("moldProductType", KETParamMapUtil.toString(paramMap.getStringArray("moldProductType3")));
		/** 금형유형 */
		map.put("moldType", KETParamMapUtil.toString(paramMap.getStringArray("moldType3")));
		/** Rank */
		map.put("rank", KETParamMapUtil.toString(paramMap.getStringArray("rank3")));
		/** 상태 */
		map.put("pjtState", KETParamMapUtil.toString(paramMap.getStringArray("pjtState3")));
		/** 금형PM */
		map.put("setMoldPm", paramMap.getString("setMoldPm"));
		/** 제작구분 */
		map.put("making", KETParamMapUtil.toString(paramMap.getStringArray("making3")));
		/** 제작처 */
		map.put("outsourcing", paramMap.getString("outsourcing3"));
		/** 개발담당부서 */
		map.put("devDeptOid", paramMap.getString("devUserDeptOid3"));
		/** 개발시작일TYpe */
		map.put("developDateType", paramMap.getString("developDateType3"));
		/** 개발 시작일 */
		map.put("planStartStartDate", paramMap.getString("planStartStartDate3"));
		/** 개발 완료일 */
		map.put("planStartEndDate", paramMap.getString("planStartEndDate3"));
		/** 개발담당자 */
		map.put("setPm", paramMap.getString("setPm3"));
		/** 사업부 */
		map.put("dType", paramMap.getString("dType1"));

		/** 구매품 여부 **/
		map.put("isPurchase", paramMap.getString("isPurchase"));

	    } else if (map.get("initType").toString().equals("4")) {
		/** Project No */
		map.put("pjtNo", paramMap.getString("pjtNo4"));

		/** 개발담당자 */
		map.put("setPm", paramMap.getString("setPm4"));

		/** 상태 */
		map.put("pjtState", KETParamMapUtil.toString(paramMap.getStringArray("pjtState4")));
		/** 개발담당부서 */
		map.put("pdevUserDept", paramMap.getString("devUserDept4"));
		/** 개발담당부서 */
		map.put("pdevDeptOid", paramMap.getString("devUserDeptOid4"));
		/** Project Name */
		map.put("pjtName", paramMap.getString("pjtName4"));

		/** 개발시작일TYpe */
		map.put("developDateType", paramMap.getString("developDateType4"));
		/** 개발 시작일 */
		map.put("planStartStartDate", paramMap.getString("planStartStartDate4"));
		/** 개발 완료일 */
		map.put("planStartEndDate", paramMap.getString("planStartEndDate4"));

		/** Rank */
		map.put("rank", KETParamMapUtil.toString(paramMap.getStringArray("rank4")));

		map.put("workType", KETParamMapUtil.toString(paramMap.getStringArray("workType")));

	    }

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, String>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, String>>();
		session.setAttribute("projectSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("projectSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, String>>) session.getAttribute("projectSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, String>>();
		}
	    }

	    conditionList.add(map);
	    session.setAttribute("projectSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    List<Map<String, Object>> list = null;

	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();
	    ProjectDao dao = new ProjectDao(conn);

	    if (isPaging) {
		if (map.get("initType").toString().equals("1")) {

		    // 목록 결과
		    list = dao.searchReviewProjectList(conditionList, pager);

		    if (!PropertiesUtil.getSearchGridCountLimitFlag() || list.size() <= PropertiesUtil.getSearchGridCountLimit()) {

			// 검색 결과 건수를 제한하지 않거나, 검색 결과 건수가 검색 결과 제한 건수 이하일 경우 검색 결과 모두 표시
			resultSize = list.size();
		    } else {

			// 검색 결과 개수가 검색 결과 제한 건수 초과일 경우 제한 건수만 표시
			resultSize = PropertiesUtil.getSearchGridCountLimit();

			resultStr = "T"; // 검색 결과 건수 제한 (alert message 표시 용도)
		    }

		    for (int i = 0; i < resultSize; i++) {

			Map<String, Object> obj = list.get(i);
			Map<String, String> popupData = this.getProjectData4SearchPopup(StringUtil.checkString(paramMap.getString("searchPopup")), (String) obj.get("pjtOid"));
			String state = State.toState(obj.get("state").toString()).getDisplay();
			String devROid = "";

			String bomTaskState = "";
			String reqTaskState = "";
			String salesTaskState = "";
			String calcTaskState = "";
			String reportTaskState = "";

			String costBomTaskOid = StringUtil.checkNull((String) obj.get("costBomTask"));
			if (!"".equals(costBomTaskOid)) {
			    costBomTaskOid = "e3ps.project.E3PSTask:" + costBomTaskOid;
			    bomTaskState = getCostTaskState(costBomTaskOid);
			}
			String costReqTaskOid = StringUtil.checkNull((String) obj.get("costReqTask"));
			if (!"".equals(costReqTaskOid)) {
			    costReqTaskOid = "e3ps.project.E3PSTask:" + costReqTaskOid;
			    reqTaskState = getCostTaskState(costReqTaskOid);
			}
			String costSalesTaskOid = StringUtil.checkNull((String) obj.get("costSalesTask"));
			if (!"".equals(costSalesTaskOid)) {
			    costSalesTaskOid = "e3ps.project.E3PSTask:" + costSalesTaskOid;
			    salesTaskState = getCostTaskState(costSalesTaskOid);
			}
			String costCalcTaskOid = StringUtil.checkNull((String) obj.get("costCalcTask"));
			if (!"".equals(costCalcTaskOid)) {
			    costCalcTaskOid = "e3ps.project.E3PSTask:" + costCalcTaskOid;
			    calcTaskState = getCostTaskState(costCalcTaskOid);
			}
			String costReportTaskOid = StringUtil.checkNull((String) obj.get("costReportTask"));
			if (!"".equals(costReportTaskOid)) {
			    costReportTaskOid = "e3ps.project.E3PSTask:" + costReportTaskOid;
			    reportTaskState = getCostTaskState(costReportTaskOid);
			}

			if (obj.get("reqNo") != null && obj.get("reqNo").toString().length() > 0) {
			    devROid = " ReqNoOnClick=\"javascript:requestPop('" + obj.get("reqOid") + "');\"" + " ReqNoHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\""
				    + " ReqNoHtmlPostfix=\"</font>\"";
			}
			strBuffer.append("<I NoColor=\"2\" CanDelete=\"0\" ");
			strBuffer.append(" RowNum=\"\" ");
			strBuffer.append(" ReqNo=\"" + StringUtil.checkNull((String) obj.get("reqNo")) + "\"" + devROid);
			strBuffer.append(" PjtNo=\"" + obj.get("pjtNo") + "\"" + " PjtNoType=\"Text\"" + " PjtNoOnClick=\"javascript:openView('" + obj.get("pjtOid") + "');\""
			        + " PjtNoHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\" PjtNoHtmlPostfix=\"</font>\"");
			strBuffer.append(" PjtName=\"" + StringEscapeUtils.escapeXml((String) obj.get("pjtName")) + "\"" + " PjtNameType=\"Text\"" + " PjtNameOnClick=\"javascript:openView('"
			        + obj.get("pjtOid") + "&key=popup&value=popup', 1080, 800);\"" + " PjtNameHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor()
			        + "'>\" ProjectNameHtmlPostfix=\"</font>\"");
			strBuffer.append(" PlanStartDate=\"" + StringUtil.checkNull((String) obj.get("planStartDate")) + "\"");
			strBuffer.append(" Buyer=\"" + StringUtil.checkNull((String) obj.get("buyer")) + "\"");
			strBuffer.append(" CostMember=\"" + StringUtil.checkNull((String) obj.get("costMember")) + "\"");
			strBuffer.append(" SalesName=\"" + StringUtil.checkNull((String) obj.get("salesName")) + "\"");
			strBuffer.append(" PjtState=\"<center>" + getStateStr(Integer.parseInt(obj.get("pjtState").toString()), obj.get("state").toString(), state, false) + "</center>\"");

			strBuffer.append(" CostBomTask=\"" + bomTaskState + "\"" + " CostBomTaskType=\"Text\"" + " CostBomTaskOnClick=\"javascript:openBomEditor('" + costBomTaskOid + "');\""
			        + " CostBomTaskHtmlPrefix=\"<font color='" + getFontColor(bomTaskState) + "'>\" CostBomTaskHtmlPostfix=\"</font>\"");
			strBuffer.append(" CostReqTask=\"" + reqTaskState + "\"" + " CostReqTaskType=\"Text\"" + " CostReqTaskOnClick=\"javascript:openBomEditor('" + costReqTaskOid + "');\""
			        + " CostReqTaskHtmlPrefix=\"<font color='" + getFontColor(reqTaskState) + "'>\" CostReqTaskHtmlPostfix=\"</font>\"");
			strBuffer.append(" CostSalesTask=\"" + salesTaskState + "\"" + " CostSalesTaskType=\"Text\"" + " CostSalesTaskOnClick=\"javascript:openBomEditor('" + costSalesTaskOid
			        + "');\"" + " CostSalesTaskHtmlPrefix=\"<font color='" + getFontColor(salesTaskState) + "'>\" CostSalesTaskHtmlPostfix=\"</font>\"");
			strBuffer.append(" CostCalcTask=\"" + calcTaskState + "\"" + " CostCalcTaskType=\"Text\"" + " CostCalcTaskOnClick=\"javascript:openBomEditor('" + costCalcTaskOid + "');\""
			        + " CostCalcTaskHtmlPrefix=\"<font color='" + getFontColor(calcTaskState) + "'>\" CostCalcTaskHtmlPostfix=\"</font>\"");
			strBuffer.append(" CostReportTask=\"" + reportTaskState + "\"" + " CostReportTaskType=\"Text\"" + " CostReportTaskOnClick=\"javascript:openCostReport('" + costReportTaskOid
			        + "','" + calcTaskState + "');\"" + " CostReportTaskHtmlPrefix=\"<font color='" + getFontColor(reportTaskState) + "'>\" CostReportTaskHtmlPostfix=\"</font>\"");

			strBuffer.append(" Statestate=\"" + state + "\"");
			strBuffer.append(" PjtOid=\"" + obj.get("pjtOid") + "\"");
			strBuffer.append(" Pm=\"" + popupData.get("pjtPmName") + "\"");
			strBuffer.append(" Rank=\"" + popupData.get("rank") + "\"");
			strBuffer.append(" Domain=\"" + popupData.get("domain") + "\"");
			strBuffer.append(" Maker=\"" + popupData.get("maker") + "\"");
			strBuffer.append(" Category=\"" + popupData.get("category") + "\"");
			strBuffer.append(" projectInfos4ECM=\"" + popupData.get("projectInfos4ECM") + "\"");
			strBuffer.append(" customerCode=\"" + popupData.get("customerCode") + "\"");
			strBuffer.append(" customerName=\"" + popupData.get("customerName") + "\"");
			strBuffer.append(" reviewResult=\"" + obj.get("reviewResult") + "\"");
			strBuffer.append("/>");
		    }
		} else if (map.get("initType").toString().equals("2")) {

		    // 목록 결과
		    list = dao.searchProdProjectList(conditionList, pager);

		    if (!PropertiesUtil.getSearchGridCountLimitFlag() || list.size() <= PropertiesUtil.getSearchGridCountLimit()) {

			// 검색 결과 건수를 제한하지 않거나, 검색 결과 건수가 검색 결과 제한 건수 이하일 경우 검색 결과 모두 표시
			resultSize = list.size();
		    } else {

			// 검색 결과 개수가 검색 결과 제한 건수 초과일 경우 제한 건수만 표시
			resultSize = PropertiesUtil.getSearchGridCountLimit();

			resultStr = "T"; // 검색 결과 건수 제한 (alert message 표시 용도)
		    }

		    for (int i = 0; i < resultSize; i++) {

			Map<String, Object> obj = list.get(i);
			Map<String, String> popupData = this.getProjectData4SearchPopup(StringUtil.checkString(paramMap.getString("searchPopup")), (String) obj.get("pjtOid"));
			String state = State.toState(obj.get("state").toString()).getDisplay();

			strBuffer.append("<I NoColor=\"2\" CanDelete=\"0\" ");
			strBuffer.append(" RowNum=\"\" ");

			strBuffer.append(" PjtNo=\"" + obj.get("pjtNo") + "\"" + " PjtNoOnClick=\"javascript:openView('" + obj.get("pjtOid") + "');\"" + " PjtNoHtmlPrefix=\"<font color='"
			        + PropertiesUtil.getSearchGridLinkColor() + "'>\" PjtNoHtmlPostfix=\"</font>\"");
			strBuffer.append(" PjtName=\"" + StringEscapeUtils.escapeXml((String) obj.get("pjtName")) + "\"" + " PjtNameOnClick=\"javascript:openView('" + obj.get("pjtOid") + "');\""
			        + " PjtNameHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\" PjtNameHtmlPostfix=\"</font>\"");
			strBuffer.append(" obtainType=\"" + StringUtil.checkNull((String) obj.get("obtainType")) + "\"");
			strBuffer.append(" Buyer=\"" + StringUtil.checkNull((String) obj.get("buyer")) + "\"");
			strBuffer.append(" CarName=\"" + StringUtil.checkNull((String) obj.get("carName")) + "\"");
			strBuffer.append(" PlanStartDate=\"" + obj.get("planStartDate") + "\"");
			strBuffer.append(" PlanEndDate=\"" + obj.get("planEndDate") + "\"");
			strBuffer.append(" PjtState=\"<center>" + getStateStr(Integer.parseInt(obj.get("pjtState").toString()), obj.get("state").toString(), state, false) + "</center>\"");
			strBuffer.append(" P1=\"" + StringUtil.checkNull((String) obj.get("P1")) + "\"");
			strBuffer.append(" P2=\"" + StringUtil.checkNull((String) obj.get("P2")) + "\"");
			strBuffer.append(" Sop=\"" + StringUtil.checkNull((String) obj.get("Sop")) + "\"");
			strBuffer.append(" PgmP1=\"" + StringUtil.checkNull((String) obj.get("PgmP1")) + "\"");
			strBuffer.append(" PgmP2=\"" + StringUtil.checkNull((String) obj.get("PgmP2")) + "\"");
			strBuffer.append(" PgmSop=\"" + StringUtil.checkNull((String) obj.get("PgmSop")) + "\"");
			strBuffer.append(" PmDept=\"" + StringUtil.checkNull((String) obj.get("PmDept")) + "\"");
			strBuffer.append(" PmUser=\"" + StringUtil.checkNull((String) obj.get("PmUser")) + "\"");
			strBuffer.append(" Statestate=\"" + state + "\"");
			strBuffer.append(" Eco=\"" + obj.get("eco") + "\"");
			strBuffer.append(" PjtOid=\"" + obj.get("pjtOid") + "\"");
			strBuffer.append(" Pm=\"" + popupData.get("pjtPmName") + "\"");
			strBuffer.append(" Rank=\"" + popupData.get("rank") + "\"");
			strBuffer.append(" Domain=\"" + popupData.get("domain") + "\"");
			strBuffer.append(" Maker=\"" + popupData.get("maker") + "\"");
			strBuffer.append(" Category=\"" + popupData.get("category") + "\"");
			strBuffer.append(" projectInfos4ECM=\"" + popupData.get("projectInfos4ECM") + "\"");
			strBuffer.append(" customerCode=\"" + popupData.get("customerCode") + "\"");
			strBuffer.append(" customerName=\"" + popupData.get("customerName") + "\"");
			strBuffer.append("/>");
		    }
		} else if (map.get("initType").toString().equals("3")) {

		    // 목록 결과
		    list = dao.searchMoldProjectList(conditionList, pager);

		    if (!PropertiesUtil.getSearchGridCountLimitFlag() || list.size() <= PropertiesUtil.getSearchGridCountLimit()) {

			// 검색 결과 건수를 제한하지 않거나, 검색 결과 건수가 검색 결과 제한 건수 이하일 경우 검색 결과 모두 표시
			resultSize = list.size();
		    } else {

			// 검색 결과 개수가 검색 결과 제한 건수 초과일 경우 제한 건수만 표시
			resultSize = PropertiesUtil.getSearchGridCountLimit();

			resultStr = "T"; // 검색 결과 건수 제한 (alert message 표시 용도)
		    }

		    for (int i = 0; i < resultSize; i++) {

			Map<String, Object> obj = list.get(i);
			Map<String, String> popupData = this.getProjectData4SearchPopup(StringUtil.checkString(paramMap.getString("searchPopup")), (String) obj.get("pjtOid"));
			String state = State.toState(obj.get("state").toString()).getDisplay();

			String makingPlace = "";

			String pjtNo = StringUtil.checkNull((String) obj.get("dieNo"));
			String pjtName = StringEscapeUtils.escapeXml(StringUtil.checkNull((String) obj.get("partName")));
			String pPjtOid = "";

			if ("Y".equals(paramMap.getString("isPurchase"))) {
			    MoldProject mProject = (MoldProject) CommonUtil.getObject((String) obj.get("pjtOid"));
			    MoldItemInfo moldInfo = mProject.getMoldInfo();
			    ProductProject pProject = moldInfo.getProject();

			    if (pProject != null) {
				pjtNo = pProject.getPjtNo();
				pjtName = pProject.getPjtName();
				pPjtOid = CommonUtil.getOIDString(pProject);
			    }

			    WTUser pm = ProjectUserHelper.manager.getPM(mProject);

			    if (pm != null)
				popupData.put("pjtPmName", StringUtil.checkNull(pm.getFullName()));

			    String purchaseStr = "";
			    if (moldInfo.getPurchasePlace() != null) {
				purchaseStr = NumberCodeUtil.getNumberCodeValue("PRODUCTIONDEPT", moldInfo.getPurchasePlace().getCode(), messageService.getLocale().toString());
			    }
			    if ("사내".equals(moldInfo.getProductionPlace())) {
				makingPlace = messageService.getString("e3ps.message.ket_message", "01655")/* 사내 */+ " / " + purchaseStr;
			    } else if (moldInfo.getPartnerNo() != null && moldInfo.getPartnerNo().length() > 0) {
				PartnerDao pdao = new PartnerDao();
				String partnerName = pdao.ViewPartnerName(moldInfo.getPartnerNo());
				if (partnerName == null || partnerName.length() == 0) {
				    partnerName = "&nbsp;";
				}
				makingPlace = messageService.getString("e3ps.message.ket_message", "02184")/* 외주 */+ " / " + partnerName;
			    }
			}

			strBuffer.append("<I NoColor=\"2\" CanDelete=\"0\" ");
			strBuffer.append(" RowNum=\"\" ");
			strBuffer.append(" DieNo=\"" + StringUtil.checkNull((String) obj.get("dieNo")) + "\""
			        + " DieNoType=\"Text\""
			        // + " DieNoOnClick=\"javascript:parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid="
			        // + obj.get("pjtOid") + "','/plm/jsp/project/MoldProjectView_2.jsp?oid=" + obj.get("pjtOid") + "');\""
			        + " DieNoOnClick=\"javascript:openView('" + obj.get("pjtOid") + "');\"" + " DieNoHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor()
			        + "'>\" DieNoHtmlPostfix=\"</font>\"");
			strBuffer.append(" PartNo=\"" + StringUtil.checkNull((String) obj.get("partNo")) + "\"");
			strBuffer.append(" PartName=\"" + StringUtil.checkNull((String) obj.get("partName")) + "\"");
			strBuffer.append(" PlanStartDate=\"" + StringUtil.checkNull((String) obj.get("planStartDate")) + "\"");
			strBuffer.append(" PlanEndDate=\"" + StringUtil.checkNull((String) obj.get("planEndDate")) + "\"");
			strBuffer.append(" PjtState=\"<center>" + getStateStr(Integer.parseInt(obj.get("pjtState").toString()), obj.get("state").toString(), state, false) + "</center>\"");
			strBuffer.append(" Statestate=\"" + state + "\"");
			strBuffer.append(" PjtNo=\"" + pjtNo + "\" PjtNoType=\"Text\"" + " PjtNoOnClick=\"javascript:openView('" + pPjtOid + "');\"" + " PjtNoHtmlPrefix=\"<font color='"
			        + PropertiesUtil.getSearchGridLinkColor() + "'>\" PjtNoHtmlPostfix=\"</font>\"");
			strBuffer.append(" PjtName=\"" + pjtName + "\" PjtNameType=\"Text\"");
			strBuffer.append(" Making=\"" + StringUtil.checkNull((String) obj.get("making")) + "\"");
			strBuffer.append(" Eco=\"" + obj.get("eco") + "\"");
			strBuffer.append(" PjtOid=\"" + obj.get("pjtOid") + "\"");
			strBuffer.append(" Pm=\"" + popupData.get("pjtPmName") + "\"");
			strBuffer.append(" Rank=\"" + popupData.get("rank") + "\"");
			strBuffer.append(" Domain=\"" + popupData.get("domain") + "\"");
			strBuffer.append(" Maker=\"" + popupData.get("maker") + "\"");
			strBuffer.append(" Category=\"" + popupData.get("category") + "\"");
			strBuffer.append(" projectInfos4ECM=\"" + popupData.get("projectInfos4ECM") + "\"");
			strBuffer.append(" customerCode=\"" + popupData.get("customerCode") + "\"");
			strBuffer.append(" customerName=\"" + popupData.get("customerName") + "\"");
			strBuffer.append(" OutSourcing=\"" + StringUtil.checkNull((String) obj.get("outSourcing")) + "\"");
			strBuffer.append(" MakingPlace=\"" + makingPlace + "\"");
			strBuffer.append("/>");
		    }
		} else if (map.get("initType").toString().equals("4")) {

		    // 목록 결과
		    list = dao.searchWorkProjectList(conditionList, pager);

		    if (!PropertiesUtil.getSearchGridCountLimitFlag() || list.size() <= PropertiesUtil.getSearchGridCountLimit()) {

			// 검색 결과 건수를 제한하지 않거나, 검색 결과 건수가 검색 결과 제한 건수 이하일 경우 검색 결과 모두 표시
			resultSize = list.size();
		    } else {

			// 검색 결과 개수가 검색 결과 제한 건수 초과일 경우 제한 건수만 표시
			resultSize = PropertiesUtil.getSearchGridCountLimit();

			resultStr = "T"; // 검색 결과 건수 제한 (alert message 표시 용도)
		    }

		    for (int i = 0; i < resultSize; i++) {

			Map<String, Object> obj = list.get(i);
			Map<String, String> popupData = this.getProjectData4SearchPopup(StringUtil.checkString(paramMap.getString("searchPopup")), (String) obj.get("pjtOid"));
			String state = State.toState(obj.get("state").toString()).getDisplay();

			strBuffer.append("<I NoColor=\"2\" CanDelete=\"0\" ");
			strBuffer.append(" RowNum=\"\" ");

			strBuffer.append(" PjtNo=\"" + obj.get("pjtNo") + "\"" + " PjtNoOnClick=\"javascript:openView('" + obj.get("pjtOid") + "');\"" + " PjtNoHtmlPrefix=\"<font color='"
			        + PropertiesUtil.getSearchGridLinkColor() + "'>\" PjtNoHtmlPostfix=\"</font>\"");
			strBuffer.append(" PjtName=\"" + StringEscapeUtils.escapeXml((String) obj.get("pjtName")) + "\"" + " PjtNameOnClick=\"javascript:openView('" + obj.get("pjtOid") + "');\""
			        + " PjtNameHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\" PjtNameHtmlPostfix=\"</font>\"");
			strBuffer.append(" PlanStartDate=\"" + obj.get("planStartDate") + "\"");
			strBuffer.append(" PlanEndDate=\"" + obj.get("planEndDate") + "\"");
			strBuffer.append(" PjtState=\"<center>" + getStateStr(Integer.parseInt(obj.get("pjtState").toString()), obj.get("state").toString(), state, false) + "</center>\"");
			strBuffer.append(" PmDept=\"" + StringUtil.checkNull((String) obj.get("PmDept")) + "\"");
			strBuffer.append(" PmUser=\"" + StringUtil.checkNull((String) obj.get("PmUser")) + "\"");
			strBuffer.append(" Statestate=\"" + state + "\"");
			strBuffer.append(" PjtOid=\"" + obj.get("pjtOid") + "\"");
			strBuffer.append(" Pm=\"" + popupData.get("pjtPmName") + "\"");
			strBuffer.append(" Rank=\"" + popupData.get("rank") + "\"");
			strBuffer.append(" Domain=\"" + popupData.get("domain") + "\"");
			strBuffer.append("/>");
		    }
		}

		req.setAttribute("hashMap", map); // parameter 정리
		req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건

		req.setAttribute("currentPageNo", String.valueOf(pager.getCurrentPageNo()));
		req.setAttribute("tgData", strBuffer.toString());
		req.setAttribute("pageSize", String.valueOf(pager.getPageSize()));
		// req.setAttribute("resultStr", resultStr); // 검색 결과 건수 제한 여부(제한될 경우 T)

		// gotoResult(req, res, "/jsp/project/ProjectSearch.jsp");
		gotoResult(req, res, "/jsp/common/treegrid/SearchGridPage.jsp");
	    } else {
		int totalCount = 0;
		if (map.get("initType").toString().equals("1")) {
		    totalCount = dao.searchReviewProjectCount(conditionList);
		} else if (map.get("initType").toString().equals("2")) {
		    totalCount = dao.searchProdProjectCount(conditionList);
		} else if (map.get("initType").toString().equals("3")) {
		    totalCount = dao.searchMoldProjectCount(conditionList);
		} else if (map.get("initType").toString().equals("4")) {
		    totalCount = dao.searchWorkProjectCount(conditionList);
		}
		req.setAttribute("rootCount", String.valueOf(totalCount));
		req.setAttribute("pagingLength", String.valueOf(pager.getPagingLength(totalCount)));
		gotoResult(req, res, "/jsp/common/treegrid/SearchGridData.jsp");
	    }
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
     * 프로젝트 검색 팝업에서 부가적인 정보
     * 
     * @param projectOid
     * @return
     * @throws Exception
     * @메소드명 : getProjectData4SearchPopup
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 18.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private Map<String, String> getProjectData4SearchPopup(boolean isSearchPopup, String projectOid) throws Exception {
	String maker = "";
	String category = "";
	String domain = "";
	String pjtPmName = "";
	String rank = "";
	String customerCode = "";
	String customerName = "";
	String projectInfos4ECM = "";// ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리

	if (isSearchPopup) {
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(projectOid);
	    WTUser pm = ProjectUserHelper.manager.getPM(project);
	    if (pm != null) {
		pjtPmName = pm.getFullName();
	    }
	    if (project.getOemType() != null) {
		maker = project.getOemType().getMaker().getCode();
		category = project.getOemType().getCode();
		if (maker.startsWith("10")) {
		    domain = "1000";
		} else {
		    domain = "2000";
		}
	    }

	    // 프로젝트 상태
	    State projectState = null;
	    E3PSProject productProject = null;
	    if (project instanceof MoldProject) {
		MoldProject moldProject = (MoldProject) project;
		productProject = moldProject.getMoldInfo().getProject();

		projectState = moldProject.getLifeCycleState();
	    } else {
		productProject = project;

		projectState = productProject.getLifeCycleState();
	    }
	    QuerySpec psspec = new QuerySpec(ProjectSubcontractorLink.class);
	    SearchUtil.appendEQUAL(psspec, ProjectSubcontractorLink.class, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(productProject), 0);
	    QueryResult psresult = PersistenceHelper.manager.find((StatementSpec) psspec);
	    while (psresult != null && psresult.hasMoreElements()) {
		ProjectSubcontractorLink link = (ProjectSubcontractorLink) psresult.nextElement();
		if (link != null) {
		    NumberCode ncode = link.getSubcontractor();
		    customerCode += "," + CommonUtil.getOIDString(ncode);
		    customerName += "," + ncode.getName();
		}
	    }

	    // 프로젝트 상태
	    projectInfos4ECM += projectState.toString() + "↕";

	    // 개발단계 = 단계구분
	    NumberCode process = project.getProcess();
	    String devYnOid = (process != null) ? CommonUtil.getOIDString(process) : "";
	    String devYnName = (process != null) ? process.getName() : "";
	    String devYnCode = (process != null) ? process.getCode() : "";
	    projectInfos4ECM += devYnOid + "↔" + devYnName + "↔" + devYnCode + "↕";

	    // 대표 차종
	    OEMProjectType oemType = project.getOemType();
	    String carTypeOid = (oemType != null) ? CommonUtil.getOIDString(oemType) : "";
	    String carTypeName = (oemType != null) ? oemType.getName() : "";
	    String carTypeCode = (oemType != null) ? oemType.getCode() : "";
	    projectInfos4ECM += carTypeOid + "↔" + carTypeName + "↔" + carTypeCode + "↕";

	    // Rank
	    rank = project.getRank() != null ? project.getRank().getName() : "";

	    // 제품정보
	    KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();
	    QueryResult qr = PersistenceHelper.manager.navigate(project, ProjectProductInfoLink.PRODUCT_INFO_ROLE, ProjectProductInfoLink.class, false);
	    while (qr.hasMoreElements()) {
		ProjectProductInfoLink projectProductInfoLink = (ProjectProductInfoLink) qr.nextElement();
		ProductInfo productInfo = projectProductInfoLink.getProductInfo();

		String pNum = productInfo.getPNum();
		String pName = productInfo.getPName();
		@SuppressWarnings("rawtypes")
		Hashtable partInfo = ketBOMQueryBean.getPartInfo(pNum);
		String relPartVersion = (partInfo != null) ? (String) partInfo.get("rev") : "";
		String relPartOid = (partInfo != null) ? (String) partInfo.get("oid") : "";

		projectInfos4ECM += relPartOid + "↔" + pNum + "↔" + StringEscapeUtils.escapeXml("\"" + pName + "\"") + "↔" + relPartVersion + "↕";

	    }

	    if (!projectInfos4ECM.equals(""))
		projectInfos4ECM = projectInfos4ECM.substring(0, projectInfos4ECM.lastIndexOf("↕"));
	}

	Map<String, String> returnMap = new HashMap<String, String>();
	returnMap.put("maker", maker);
	returnMap.put("category", category);
	returnMap.put("domain", domain);
	returnMap.put("customerCode", customerCode);
	returnMap.put("customerName", customerName);
	returnMap.put("pjtPmName", pjtPmName);
	returnMap.put("rank", rank);
	returnMap.put("projectInfos4ECM", projectInfos4ECM);
	return returnMap;
    }

    public String getStateStr(int pjtState, String state, String stateName, boolean withState) throws Exception {

	if (state.equals("COMPLETED")) {
	    String returnStr = "<table><tr title='종료되었습니다'>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
		    + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
		    + "/state_green_bar.gif></td>";
	    if (withState) {
		returnStr += "<td>(" + StringUtil.checkNull(stateName) + ")</td>";
	    }
	    returnStr += "</tr></table>";
	    return returnStr;

	}

	String typeState = "<font color=red>" + stateName + "</font>";
	String typeStateSelect = null;

	if (stateName.equals("작업 중"))
	    typeStateSelect = "결재 중";
	else
	    typeStateSelect = stateName;

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

    private void excelDown(HttpServletRequest req, HttpServletResponse res) {
	String strClient = req.getHeader("User-Agent");

	String oid = req.getParameter("oid");
	E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	String fileName = project.getPjtNo();

	if (strClient.indexOf("MSIE 5.5") > -1) {
	    res.setHeader("Content-Disposition", "filename=" + fileName + ".xls;");
	}

	else {
	    res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls;");
	}
	res.setHeader("Set-Cookie", "fileDownload=true; path=/");
	try {
	    ProjectScheduleHelper.excelGantt(project, res.getOutputStream());
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /*
     * private void create(HttpServletRequest req, HttpServletResponse res) { ParamHash paramHash = WebUtil.getHttpParams(req); String
     * projectNo = (String)paramHash.get("projectNo"); String initType = (String) paramHash.get("initType");
     * 
     * //ProjectNO Check boolean checkPN = ProjectHelper.manager.checkProjectNo(projectNo); if(!checkPN) { Hashtable hash = new Hashtable();
     * //ERP(MIS) Interface Attribute hash.put("projectNo", paramHash.get("projectNo")); //ProjectNO hash.put("projectProduct",
     * paramHash.get("projectProduct")); //PRODUCT hash.put("projectName", paramHash.get("projectName")); //프로젝트 명(장비명)
     * hash.put("projectAcceptanceDate", paramHash.get("projectAcceptanceDate")); //수주일자 hash.put("projectDeliveredDate",
     * paramHash.get("projectDeliveredDate")); //출하일자 hash.put("projectConsignment", paramHash.get("projectConsignment")); //출하조건
     * 
     * 
     * //E3PSProject Attributes hash.put("projectType", paramHash.get("projectType")); //프로젝트종류(양산, 개발) hash.put("projectFab",
     * paramHash.get("projectFabName")); //FAB Name hash.put("planStartDate", paramHash.get("planStartDate")); //계획 시작일자
     * hash.put("planEndDate", paramHash.get("planEndDate")); //계획 종료일자 hash.put("projectDesc", paramHash.get("projectDesc")); //설명
     * hash.put("gugcheck", paramHash.get("gugcheck"));
     * 
     * //E3PSProject Link Attributes hash.put("projectCustomer", paramHash.get("projectCustomer")); //JELProjectCustomerLink Code
     * hash.put("projectSite", paramHash.get("projectSite")); //JELProjectSiteLink Code hash.put("projectAcceptanceType",
     * paramHash.get("projectAcceptanceType")); //JELProjectAcceptanceLink Code hash.put("projectSaleType",
     * paramHash.get("projectSaleType")); //JELProjectMarketingLink Code hash.put("processoid", paramHash.get("processoid"));
     * //ProcessDivisionCodeLink Code
     * 
     * //PM hash.put("projectState", paramHash.get("projectState")); //프로젝트 상태(준비중) - PM만 보임
     * 
     * //ROLE별 Memeber Role role = null; String roleName_en = null; try { TeamTemplate tempTeam =
     * TeamHelper.service.getTeamTemplate("Team_Project"); Vector vecTeamStd = tempTeam.getRoles(); for (int i = vecTeamStd.size() - 1; i >
     * -1; i--) { role = (Role) vecTeamStd.get(i);
     * 
     * roleName_en = role.toString(); if (paramHash.get(roleName_en) != null && ((String) paramHash.get(roleName_en)).length() != 0) {
     * hash.put(roleName_en, paramHash.get(roleName_en)); } else { hash.put(roleName_en, ""); } } } catch (Exception e) {
     * Kogger.error(getClass(), e); }
     * 
     * //Add Memeber hash.put("USERMEMBER", paramHash.get("userMember", true));
     * 
     * //Template Attributes hash.put("TemplateOID", paramHash.get("tempid"));
     * 
     * //E3PSProject Object Create(StandardJELProjectService Call) try { E3PSProject E3PSProject =
     * JELProjectHelper.service.createJELProject(hash); if(E3PSProject != null) { this.alertNgo(res, "프로젝트가 생성되었습니다.",
     * "/plm/servlet/e3ps.project.servlet.ProjectServlet?initType="+initType); } else { this.alertNgo(res,
     * "프로젝트가 생성되지 않았습니다.\n다시 프로젝트를 생성시켜 주십시요.", "/plm/servlet/e3ps.project.servlet.ProjectServlet?initType="+initType); } } catch
     * (WTException e) { Kogger.error(getClass(), e); } } else { //Dupulication Check and Return String msg = "이미 프로젝트NO(" +
     * paramHash.get("projectNo") + ")가 등록되었습니다. \n 다시 한번 더 확인해 주십시요."; this.alertNback(res, msg); } }
     */

    private void update(HttpServletRequest req, HttpServletResponse res) {
	ParamHash paramHash = WebUtil.getHttpParams(req);
	String oid = (String) paramHash.get("oid"); // Old E3PSProject Object OID
	String mode = (String) paramHash.get("mode"); // mode(erpUpdate, plmUpdate)
	// req.getParameter
	Kogger.debug(getClass(), "oid=>" + oid);
	String oid2 = req.getParameter("oid"); // Old E3PSProject Object OID
	Kogger.debug(getClass(), "oid2=>" + oid2);
	String initType = req.getParameter("initType");
	String projectType = req.getParameter("projectType");
	String projectState = req.getParameter("projectState");
	if (projectState == null)
	    projectState = "";
	String planStartDate = req.getParameter("planStartDate");
	String planEndDate = req.getParameter("planEndDate");
	if (planEndDate == null)
	    planEndDate = "";
	String projectDesc = req.getParameter("projectDesc");
	if (projectDesc == null)
	    projectDesc = "";
	String templateOid = req.getParameter("templateOid");
	String pwlinkOid = req.getParameter("pwlinkOid");
	// String userMemberArr[] = (String[])req.getParameterValues("userMember");
	String levelCode = "";// req.getParameter("levelcode");
	String productCode = "";// req.getParameter("productcode");
	String customerCode = "";// (String)paramHash.get("customercode");
	String devcompanyCode = "";// (String)paramHash.get("devcompanycode");
	String makecompanyCode = "";// (String)paramHash.get("makecompanycode");
	String modelCode = req.getParameter("modelcode");

	String drNumber = req.getParameter("drNumber");
	String drKeyOid = req.getParameter("drKeyOid");
	String teamType = req.getParameter("teamType"); // 사업부
	String process = req.getParameter("process"); // Process
	String sales = req.getParameter("sales"); // 영업담당자
	String isPM = req.getParameter("isPM"); // 개발담당자(false) or PM(true)
	String devManager = req.getParameter("devManager"); // 개발담당자
	String department = req.getParameter("department"); // 부서
	String partNo = req.getParameter("partNo"); // Part No
	String productType = req.getParameter("productType"); // 제품구분
	String rank = req.getParameter("rank"); // Rank
	String model = req.getParameter("model"); // 대표차종
	String assembledType = req.getParameter("assembledType"); // 조립구분
	String developentType = req.getParameter("developentType"); // 개발구분
	String designType = req.getParameter("designType"); // 설계구분
	String proteamNo = req.getParameter("proteamNo"); // 생산조립처
	String customer[] = (String[]) req.getParameterValues("CUSTOMEREVENTOid"); // 최종 사용처
	String dependence[] = (String[]) req.getParameterValues("SUBCONTRACTOROid"); // 개발검토 의뢰처
	String costsDate = req.getParameter("costsDate"); // 개발원가 제출일
	// Item 현황
	String moldItemOid[] = (String[]) req.getParameterValues("moldItemOid");
	String itemType[] = (String[]) req.getParameterValues("itemType");
	String moldProductType[] = (String[]) req.getParameterValues("moldProductType");
	String moldPartNo[] = (String[]) req.getParameterValues("moldPartNo");
	String partName[] = (String[]) req.getParameterValues("partName");
	String dieNo[] = (String[]) req.getParameterValues("dieNo");
	String costOid[] = (String[]) req.getParameterValues("costOid");
	String moldType[] = (String[]) req.getParameterValues("moldType");
	String cVPitch[] = (String[]) req.getParameterValues("cVPitch");
	String cTSPM[] = (String[]) req.getParameterValues("cTSPM");
	String making[] = (String[]) req.getParameterValues("making");
	String productionPlace[] = (String[]) req.getParameterValues("productionPlace");
	String productionPlace2[] = (String[]) req.getParameterValues("productionPlaceTwo");
	String materials[] = (String[]) req.getParameterValues("materials");
	String poidvalue[] = (String[]) req.getParameterValues("poidvalue");
	String height[] = (String[]) req.getParameterValues("height");
	String wide[] = (String[]) req.getParameterValues("wide");
	String shrinkage[] = (String[]) req.getParameterValues("newType");
	String etc[] = (String[]) req.getParameterValues("etc");
	String delItemOid = req.getParameter("delItemOid");

	String projectoid[] = (String[]) req.getParameterValues("projectOid");

	String lifecycle = req.getParameter("lifecycle");
	String pmoid = req.getParameter("pmoid");

	String projectName = req.getParameter("projectName");

	if (projectName == null)
	    projectName = "";

	e3ps.common.jdf.config.Config conf = e3ps.common.jdf.config.ConfigImpl.getInstance();

	String projectNo = null;
	String teamTypeCode = "";
	if ("자동차 사업부".equals(teamType))
	    teamTypeCode = "A";
	else
	    teamTypeCode = "E";

	String tempDate = DateUtil.getDateString(new Date(), "all");
	String processCode = "";
	if (process != null && process.length() > 0) {
	    NumberCode code = NumberCodeHelper.manager.getNumberCode("PROCESS", process);
	    if ("Proto".equals(code.getName())) {
		processCode = "T";
	    } else if ("Pilot".equals(code.getName())) {
		processCode = "B";
	    }
	    if (developentType != null) {
		if (developentType.length() > 0) {
		    NumberCode nc = NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", developentType);
		    if ("연구개발".equals(nc.getName())) {
			processCode = "D";
		    } else if ("추가금형".equals(nc.getName())) {
			processCode = "C";
		    }
		}
	    }
	    projectNo = teamTypeCode + tempDate.substring(2, 4) + processCode;
	    projectNo = projectNo + ManageSequence.getSeqNo(projectNo, "000", "E3PSProjectMaster", E3PSProjectMaster.PJT_NO);
	}

	// 최종 사용처
	Vector customerVec = new Vector();
	if (customer != null && customer.length > 0) {
	    for (int i = 0; i < customer.length; i++) {
		customerVec.add(customer[i]);
	    }
	}
	// 고객처
	String SUBCONTRACTOROid[] = (String[]) req.getParameterValues("sOid");
	Vector SUBCONTRACTOROidVec = new Vector();
	if (SUBCONTRACTOROid != null && SUBCONTRACTOROid.length > 0) {
	    for (int i = 0; i < SUBCONTRACTOROid.length; i++) {
		SUBCONTRACTOROidVec.add(SUBCONTRACTOROid[i]);
	    }
	}
	// 납입처 Link
	String nOid[] = (String[]) req.getParameterValues("nOid");
	Vector nOidVec = new Vector();
	if (nOid != null && nOid.length > 0) {
	    for (int i = 0; i < nOid.length; i++) {
		nOidVec.add(nOid[i]);
	    }
	}

	// Item 현황
	Vector moldItemOidVec = new Vector();
	if (moldItemOid != null && moldItemOid.length > 0) {
	    for (int i = 0; i < moldItemOid.length; i++) {
		moldItemOidVec.add(moldItemOid[i]);
	    }
	}
	Vector itemTypeVec = new Vector();
	if (itemType != null && itemType.length > 0) {
	    for (int i = 0; i < itemType.length; i++) {
		itemTypeVec.add(itemType[i]);
	    }
	}
	Vector moldProductTypeVec = new Vector();
	if (moldProductType != null && moldProductType.length > 0) {
	    for (int i = 0; i < moldProductType.length; i++) {
		moldProductTypeVec.add(moldProductType[i]);
	    }
	}
	Vector moldPartNoVec = new Vector();
	if (moldPartNo != null && moldPartNo.length > 0) {
	    for (int i = 0; i < moldPartNo.length; i++) {
		moldPartNoVec.add(moldPartNo[i]);
	    }
	}
	Vector partNameVec = new Vector();
	if (partName != null && partName.length > 0) {
	    for (int i = 0; i < partName.length; i++) {
		partNameVec.add(partName[i]);
	    }
	}
	Vector dieNoVec = new Vector();
	if (dieNo != null && dieNo.length > 0) {
	    for (int i = 0; i < dieNo.length; i++) {
		dieNoVec.add(dieNo[i].trim());
	    }
	}
	Vector costOidVec = new Vector();
	if (costOid != null && costOid.length > 0) {
	    for (int i = 0; i < costOid.length; i++) {
		costOidVec.add(costOid[i]);
	    }
	}
	Vector moldTypeVec = new Vector();
	if (moldType != null && moldType.length > 0) {
	    for (int i = 0; i < moldType.length; i++) {
		moldTypeVec.add(moldType[i]);
	    }
	}
	Vector cVPitchVec = new Vector();
	if (cVPitch != null && cVPitch.length > 0) {
	    for (int i = 0; i < cVPitch.length; i++) {
		cVPitchVec.add(cVPitch[i]);
	    }
	}
	Vector cTSPMVec = new Vector();
	if (cTSPM != null && cTSPM.length > 0) {
	    for (int i = 0; i < cTSPM.length; i++) {
		cTSPMVec.add(cTSPM[i]);
	    }
	}
	Vector makingVec = new Vector();
	if (making != null && making.length > 0) {
	    for (int i = 0; i < making.length; i++) {
		makingVec.add(making[i]);
	    }
	}
	Vector productionPlaceVec = new Vector();
	if (productionPlace != null && productionPlace.length > 0) {
	    for (int i = 0; i < productionPlace.length; i++) {
		productionPlaceVec.add(productionPlace[i]);
	    }
	}
	Vector productionPlace2Vec = new Vector();
	if (productionPlace2 != null && productionPlace2.length > 0) {
	    for (int i = 0; i < productionPlace2.length; i++) {
		productionPlace2Vec.add(productionPlace2[i]);
	    }
	}
	Vector materialsVec = new Vector();
	if (materials != null && materials.length > 0) {
	    for (int i = 0; i < materials.length; i++) {
		materialsVec.add(materials[i]);
	    }
	}

	Vector poidvalueVec = new Vector();
	if (poidvalue != null && poidvalue.length > 0) {
	    for (int i = 0; i < poidvalue.length; i++) {
		poidvalueVec.add(poidvalue[i]);
	    }
	}
	Vector heightVec = new Vector();
	if (height != null && height.length > 0) {
	    for (int i = 0; i < height.length; i++) {
		heightVec.add(height[i]);
	    }
	}
	Vector wideVec = new Vector();
	if (wide != null && wide.length > 0) {
	    for (int i = 0; i < wide.length; i++) {
		wideVec.add(wide[i]);
	    }
	}
	Vector shrinkageVec = new Vector();
	if (shrinkage != null && shrinkage.length > 0) {
	    for (int i = 0; i < shrinkage.length; i++) {
		// Kogger.debug(getClass(), "newType = " + shrinkage[i]);
		shrinkageVec.add(shrinkage[i]);
	    }
	}
	Vector etcVec = new Vector();
	if (etc != null && etc.length > 0) {
	    for (int i = 0; i < etc.length; i++) {
		etcVec.add(etc[i]);
	    }
	}
	/*
	 * Vector userMemberVec = new Vector(); if(userMemberArr != null && userMemberArr.length > 0) { for(int i = 0; i <
	 * userMemberArr.length; i++) { userMemberVec.add(userMemberArr[i]); } }
	 */
	// 관련 Project
	Vector projectOidVec = new Vector();
	if (projectoid != null && projectoid.length > 0) {
	    for (int i = 0; i < projectoid.length; i++) {
		projectOidVec.add(projectoid[i]);
	    }
	}

	Hashtable hash = new Hashtable();
	hash.put("projectType", projectType); // 프로젝트 종류
	if (projectNo != null)
	    hash.put("projectNo", projectNo); // ProjectNO
	hash.put("projectName", projectName); // 프로젝트 명
	if (planStartDate != null)
	    hash.put("planStartDate", planStartDate); // 계획 시작일자
	hash.put("planEndDate", planEndDate); // 계획 종료일자
	hash.put("projectDesc", projectDesc); // 설명

	if (drNumber != null)
	    hash.put("drNumber", drNumber);
	if (drKeyOid != null)
	    hash.put("drKeyOid", drKeyOid);
	if (teamType != null)
	    hash.put("teamType", teamType); // 사업부
	hash.put("sales", sales); // 영업담당자
	if (isPM != null) {
	    hash.put("isPM", isPM); // 개발담당 or PM
	}
	if (devManager != null)
	    hash.put("devUser", devManager); // 개발담당자
	if (department != null)
	    hash.put("department", department); // 부서
	hash.put("partNo", partNo); // Part No
	hash.put("productType", productType); // 제품구분
	hash.put("rank", rank); // Rank
	hash.put("model", model); // 대표차종
	hash.put("assembledType", assembledType); // 조립구분
	if (process != null)
	    hash.put("process", process); // Process
	hash.put("developentType", developentType); // 개발구분
	hash.put("designType", designType); // 설계구분
	hash.put("proteamNo", proteamNo); // 생산조립처
	hash.put("customer", customerVec); // 최종 사용처
	hash.put("SUBCONTRACTOR", SUBCONTRACTOROidVec);
	hash.put("nOidVec", nOidVec);
	hash.put("costsDate", costsDate); // 개발원가 제출일
	// Item 현황
	hash.put("moldItemOid", moldItemOidVec);
	hash.put("itemType", itemTypeVec);
	hash.put("moldProductType", moldProductTypeVec);
	hash.put("moldPartNo", moldPartNoVec);
	hash.put("partName", partNameVec);
	hash.put("dieNo", dieNoVec);
	hash.put("costOid", costOidVec);
	hash.put("moldType", moldTypeVec);
	hash.put("cVPitch", cVPitchVec);
	hash.put("cTSPM", cTSPMVec);
	hash.put("making", makingVec);
	hash.put("productionPlace", productionPlaceVec);
	hash.put("productionPlace2", productionPlace2Vec);
	hash.put("materials", materialsVec);
	hash.put("poidvalue", poidvalueVec);
	hash.put("height", heightVec);
	hash.put("wide", wideVec);
	hash.put("shrinkage", shrinkageVec);
	hash.put("etc", etcVec);
	if (delItemOid != null)
	    hash.put("delItemOid", delItemOid);

	hash.put("projectoid", projectOidVec); // 관련 Project

	if (templateOid != null)
	    hash.put("TemplateOID", templateOid);
	hash.put("pwlinkOid", pwlinkOid);

	hash.put("projectState", projectState);

	// hash.put("USERMEMBER", userMemberVec);

	if (oid != null && oid.length() > 0) {
	    hash.put("oid", oid);
	}

	// 제품정보
	if ((String[]) req.getParameterValues("rowId") != null) {
	    String rowId[] = (String[]) req.getParameterValues("rowId");
	    Vector rowIdVec = new Vector();
	    if (rowId != null && rowId.length > 0) {
		for (int i = 0; i < rowId.length; i++) {
		    rowIdVec.add(rowId[i]);

		    if ((String[]) req.getParameterValues("count" + rowId[i]) != null) {
			String count[] = (String[]) req.getParameterValues("count" + rowId[i]);
			Vector countVec = new Vector();
			if (count != null && count.length > 0) {
			    for (int j = 0; j < count.length; j++) {
				countVec.add(count[j]);
			    }
			}
			hash.put("countVec" + rowId[i], countVec);
		    }

		    if ((String[]) req.getParameterValues("optOid" + rowId[i]) != null) {
			String optOid[] = (String[]) req.getParameterValues("optOid" + rowId[i]);
			Vector optOidVec = new Vector();
			if (optOid != null && optOid.length > 0) {
			    for (int j = 0; j < optOid.length; j++) {
				optOidVec.add(optOid[j]);
			    }
			}
			hash.put("optOidVec" + rowId[i], optOidVec);
		    }

		    if ((String[]) req.getParameterValues("y1" + rowId[i]) != null) {
			String y1[] = (String[]) req.getParameterValues("y1" + rowId[i]);
			Vector y1Vec = new Vector();
			if (y1 != null && y1.length > 0) {
			    for (int j = 0; j < y1.length; j++) {
				y1Vec.add(y1[j]);
			    }
			}
			hash.put("y1Vec" + rowId[i], y1Vec);
		    }

		    if ((String[]) req.getParameterValues("y2" + rowId[i]) != null) {
			String y2[] = (String[]) req.getParameterValues("y2" + rowId[i]);
			Vector y2Vec = new Vector();
			if (y2 != null && y2.length > 0) {
			    for (int j = 0; j < y2.length; j++) {
				y2Vec.add(y2[j]);
			    }
			}
			hash.put("y2Vec" + rowId[i], y2Vec);
		    }

		    if ((String[]) req.getParameterValues("y3" + rowId[i]) != null) {
			String y3[] = (String[]) req.getParameterValues("y3" + rowId[i]);
			Vector y3Vec = new Vector();
			if (y3 != null && y3.length > 0) {
			    for (int j = 0; j < y3.length; j++) {
				y3Vec.add(y3[j]);
			    }
			}
			hash.put("y3Vec" + rowId[i], y3Vec);
		    }

		    if ((String[]) req.getParameterValues("y4" + rowId[i]) != null) {
			String y4[] = (String[]) req.getParameterValues("y4" + rowId[i]);
			Vector y4Vec = new Vector();
			if (y4 != null && y4.length > 0) {
			    for (int j = 0; j < y4.length; j++) {
				y4Vec.add(y4[j]);
			    }
			}
			hash.put("y4Vec" + rowId[i], y4Vec);
		    }

		    if ((String[]) req.getParameterValues("y5" + rowId[i]) != null) {
			String y5[] = (String[]) req.getParameterValues("y5" + rowId[i]);
			Vector y5Vec = new Vector();
			if (y5 != null && y5.length > 0) {
			    for (int j = 0; j < y5.length; j++) {
				y5Vec.add(y5[j]);
			    }
			}
			hash.put("y5Vec" + rowId[i], y5Vec);
		    }

		    if ((String[]) req.getParameterValues("y6" + rowId[i]) != null) {
			String y6[] = (String[]) req.getParameterValues("y6" + rowId[i]);
			Vector y6Vec = new Vector();
			if (y6 != null && y6.length > 0) {
			    for (int j = 0; j < y6.length; j++) {
				y6Vec.add(y6[j]);
			    }
			}
			hash.put("y6Vec" + rowId[i], y6Vec);
		    }

		    if ((String[]) req.getParameterValues("y7" + rowId[i]) != null) {
			String y7[] = (String[]) req.getParameterValues("y7" + rowId[i]);
			Vector y7Vec = new Vector();
			if (y7 != null && y7.length > 0) {
			    for (int j = 0; j < y7.length; j++) {
				y7Vec.add(y7[j]);
			    }
			}
			hash.put("y7Vec" + rowId[i], y7Vec);
		    }

		    if ((String[]) req.getParameterValues("y8" + rowId[i]) != null) {
			String y8[] = (String[]) req.getParameterValues("y8" + rowId[i]);
			Vector y8Vec = new Vector();
			if (y8 != null && y8.length > 0) {
			    for (int j = 0; j < y8.length; j++) {
				y8Vec.add(y8[j]);
			    }
			}
			hash.put("y8Vec" + rowId[i], y8Vec);
		    }

		    if ((String[]) req.getParameterValues("y9" + rowId[i]) != null) {
			String y9[] = (String[]) req.getParameterValues("y9" + rowId[i]);
			Vector y9Vec = new Vector();
			if (y9 != null && y9.length > 0) {
			    for (int j = 0; j < y9.length; j++) {
				y9Vec.add(y9[j]);
			    }
			}
			hash.put("y9Vec" + rowId[i], y9Vec);
		    }

		    if ((String[]) req.getParameterValues("y10" + rowId[i]) != null) {
			String y10[] = (String[]) req.getParameterValues("y10" + rowId[i]);
			Vector y10Vec = new Vector();
			if (y10 != null && y10.length > 0) {
			    for (int j = 0; j < y10.length; j++) {
				y10Vec.add(y10[j]);
			    }
			}
			hash.put("y10Vec" + rowId[i], y10Vec);
		    }

		    if ((String[]) req.getParameterValues("usage" + rowId[i]) != null) {
			String usage[] = (String[]) req.getParameterValues("usage" + rowId[i]);
			Vector usageVec = new Vector();
			if (usage != null && usage.length > 0) {
			    for (int j = 0; j < usage.length; j++) {
				usageVec.add(usage[j]);
			    }
			}
			hash.put("usageVec" + rowId[i], usageVec);
		    }

		    if ((String[]) req.getParameterValues("optionRate" + rowId[i]) != null) {
			String optionRate[] = (String[]) req.getParameterValues("optionRate" + rowId[i]);
			Vector optionRateVec = new Vector();
			if (optionRate != null && optionRate.length > 0) {
			    for (int j = 0; j < optionRate.length; j++) {
				optionRateVec.add(optionRate[j]);
			    }
			}
			hash.put("optionRateVec" + rowId[i], optionRateVec);
		    }

		    if (req.getParameter("pOid" + rowId[i]) != null) {
			String pOid = req.getParameter("pOid" + rowId[i]);
			hash.put("pOidVec" + rowId[i], pOid);
		    }

		}
	    }
	    hash.put("rowIdVec", rowIdVec);
	}

	if ((String[]) req.getParameterValues("pNum") != null) {
	    String pNum[] = (String[]) req.getParameterValues("pNum");
	    Vector pNumVec = new Vector();
	    if (pNum != null && pNum.length > 0) {
		for (int i = 0; i < pNum.length; i++) {
		    pNumVec.add(pNum[i]);
		}
	    }
	    hash.put("pNumVec", pNumVec);
	}
	if ((String[]) req.getParameterValues("reviewProjectNo") != null) {
	    String reviewProjectNo[] = (String[]) req.getParameterValues("reviewProjectNo");
	    Vector reviewProjectNoVec = new Vector();
	    if (reviewProjectNo != null && reviewProjectNo.length > 0) {
		for (int i = 0; i < reviewProjectNo.length; i++) {
		    reviewProjectNoVec.add(reviewProjectNo[i]);
		}
	    }
	    hash.put("reviewProjectNoVec", reviewProjectNoVec);
	}
	if ((String[]) req.getParameterValues("reviewSeqNo") != null) {
	    String reviewSeqNo[] = (String[]) req.getParameterValues("reviewSeqNo");
	    Vector reviewSeqNoVec = new Vector();
	    if (reviewSeqNo != null && reviewSeqNo.length > 0) {
		for (int i = 0; i < reviewSeqNo.length; i++) {
		    reviewSeqNoVec.add(reviewSeqNo[i]);
		}
	    }
	    hash.put("reviewSeqNoVec", reviewSeqNoVec);
	}

	if ((String[]) req.getParameterValues("pName") != null) {
	    String pName[] = (String[]) req.getParameterValues("pName");
	    Vector pNameVec = new Vector();
	    if (pName != null && pName.length > 0) {
		for (int i = 0; i < pName.length; i++) {
		    pNameVec.add(pName[i]);
		}
	    }
	    hash.put("pNameVec", pNameVec);
	}

	if ((String[]) req.getParameterValues("areas") != null) {
	    String areas[] = (String[]) req.getParameterValues("areas");
	    Vector areasVec = new Vector();
	    if (areas != null && areas.length > 0) {
		for (int i = 0; i < areas.length; i++) {
		    areasVec.add(areas[i]);
		}
	    }
	    hash.put("areasVec", areasVec);
	}

	if ((String[]) req.getParameterValues("carName") != null) {
	    String carName[] = (String[]) req.getParameterValues("carName");
	    Vector carNameVec = new Vector();
	    if (carName != null && carName.length > 0) {
		for (int i = 0; i < carName.length; i++) {
		    carNameVec.add(carName[i]);
		}
	    }
	    hash.put("carNameVec", carNameVec);
	}

	if ((String[]) req.getParameterValues("usageT") != null) {
	    String usageT[] = (String[]) req.getParameterValues("usageT");
	    Vector usageTVec = new Vector();
	    if (usageT != null && usageT.length > 0) {
		for (int i = 0; i < usageT.length; i++) {
		    usageTVec.add(usageT[i]);
		}
	    }
	    hash.put("usageTVec", usageTVec);
	}

	if ((String[]) req.getParameterValues("price") != null) {
	    String price[] = (String[]) req.getParameterValues("price");
	    Vector priceVec = new Vector();
	    if (price != null && price.length > 0) {
		for (int i = 0; i < price.length; i++) {
		    priceVec.add(price[i]);
		}
	    }
	    hash.put("priceVec", priceVec);
	}

	if ((String[]) req.getParameterValues("cost") != null) {
	    String cost[] = (String[]) req.getParameterValues("cost");
	    Vector costVec = new Vector();
	    if (cost != null && cost.length > 0) {
		for (int i = 0; i < cost.length; i++) {
		    costVec.add(cost[i]);
		}
	    }
	    hash.put("costVec", costVec);
	}

	if ((String[]) req.getParameterValues("rate") != null) {
	    String rate[] = (String[]) req.getParameterValues("rate");
	    Vector rateVec = new Vector();
	    if (rate != null && rate.length > 0) {
		for (int i = 0; i < rate.length; i++) {
		    rateVec.add(rate[i]);
		}
	    }
	    hash.put("rateVec", rateVec);
	}

	if ((String[]) req.getParameterValues("y1T") != null) {
	    String y1T[] = (String[]) req.getParameterValues("y1T");
	    Vector y1TVec = new Vector();
	    if (y1T != null && y1T.length > 0) {
		for (int i = 0; i < y1T.length; i++) {
		    y1TVec.add(y1T[i]);
		}
	    }
	    hash.put("y1TVec", y1TVec);
	}

	if ((String[]) req.getParameterValues("y2T") != null) {
	    String y2T[] = (String[]) req.getParameterValues("y2T");
	    Vector y2TVec = new Vector();
	    if (y2T != null && y2T.length > 0) {
		for (int i = 0; i < y2T.length; i++) {
		    y2TVec.add(y2T[i]);
		}
	    }
	    hash.put("y2TVec", y2TVec);
	}

	if ((String[]) req.getParameterValues("y3T") != null) {
	    String y3T[] = (String[]) req.getParameterValues("y3T");
	    Vector y3TVec = new Vector();
	    if (y3T != null && y3T.length > 0) {
		for (int i = 0; i < y3T.length; i++) {
		    y3TVec.add(y3T[i]);
		}
	    }
	    hash.put("y3TVec", y3TVec);
	}

	if ((String[]) req.getParameterValues("y4T") != null) {
	    String y4T[] = (String[]) req.getParameterValues("y4T");
	    Vector y4TVec = new Vector();
	    if (y4T != null && y4T.length > 0) {
		for (int i = 0; i < y4T.length; i++) {
		    y4TVec.add(y4T[i]);
		}
	    }
	    hash.put("y4TVec", y4TVec);
	}

	if ((String[]) req.getParameterValues("y5T") != null) {
	    String y5T[] = (String[]) req.getParameterValues("y5T");
	    Vector y5TVec = new Vector();
	    if (y5T != null && y5T.length > 0) {
		for (int i = 0; i < y5T.length; i++) {
		    y5TVec.add(y5T[i]);
		}
	    }
	    hash.put("y5TVec", y5TVec);
	}

	if ((String[]) req.getParameterValues("y6T") != null) {
	    String y6T[] = (String[]) req.getParameterValues("y6T");
	    Vector y6TVec = new Vector();
	    if (y6T != null && y6T.length > 0) {
		for (int i = 0; i < y6T.length; i++) {
		    y6TVec.add(y6T[i]);
		}
	    }
	    hash.put("y6TVec", y6TVec);
	}

	if ((String[]) req.getParameterValues("y7T") != null) {
	    String y7T[] = (String[]) req.getParameterValues("y7T");
	    Vector y7TVec = new Vector();
	    if (y7T != null && y7T.length > 0) {
		for (int i = 0; i < y7T.length; i++) {
		    y7TVec.add(y7T[i]);
		}
	    }
	    hash.put("y7TVec", y7TVec);
	}

	if ((String[]) req.getParameterValues("y8T") != null) {
	    String y8T[] = (String[]) req.getParameterValues("y8T");
	    Vector y8TVec = new Vector();
	    if (y8T != null && y8T.length > 0) {
		for (int i = 0; i < y8T.length; i++) {
		    y8TVec.add(y8T[i]);
		}
	    }
	    hash.put("y8TVec", y8TVec);
	}

	if ((String[]) req.getParameterValues("y9T") != null) {
	    String y9T[] = (String[]) req.getParameterValues("y9T");
	    Vector y9TVec = new Vector();
	    if (y9T != null && y9T.length > 0) {
		for (int i = 0; i < y9T.length; i++) {
		    y9TVec.add(y9T[i]);
		}
	    }
	    hash.put("y9TVec", y9TVec);
	}

	if ((String[]) req.getParameterValues("y10T") != null) {
	    String y10T[] = (String[]) req.getParameterValues("y10T");
	    Vector y10TVec = new Vector();
	    if (y10T != null && y10T.length > 0) {
		for (int i = 0; i < y10T.length; i++) {
		    y10TVec.add(y10T[i]);
		}
	    }
	    hash.put("y10TVec", y10TVec);
	}

	if (req.getParameter("deletePOid") != null) {
	    String deletePOid = req.getParameter("deletePOid");
	    hash.put("deletePOid", deletePOid);
	}

	Role role = null;
	String roleUser = null;

	TeamTemplate tempTeam = null;
	try {
	    if (teamTypeCode.equals("A")) {
		tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");
	    } else if (teamTypeCode.equals("E")) {
		tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Electron Project");
	    }

	    Vector vecTeamStd = tempTeam.getRoles();
	    for (int i = 0; i < vecTeamStd.size(); i++) {
		role = (Role) vecTeamStd.get(i);
		roleUser = req.getParameter(role.toString());
		if (roleUser == null) {
		    roleUser = "";
		}
		hash.put(role.toString(), roleUser);
	    }

	} catch (Exception e) {

	}

	hash.put("lifecycle", lifecycle);

	if (pmoid.length() > 0) {
	    hash.put("pmoid", pmoid);
	}

	try {
	    E3PSProject e3psProject = null;
	    e3psProject = E3PSProjectHelper.service.updateE3PSProject(hash);
	    // if(E3PSProject != null) {
	    // this.alertNgoNclose(res, "프로젝트가 수정되었습니다.",
	    // "/plm/jsp/project/ProjectView.jsp?oid="+CommonUtil.getOIDString(E3PSProject)+"&isReload=true");
	    // } else {
	    // int tempType = ((E3PSProject)CommonUtil.getObject(oid)).getPjtType();
	    // if(tempType == 0) { //영업수주 프로젝트
	    // initType = "saleproject";
	    // } else if(tempType == 1) { //고객 제공 프로젝트
	    // initType = "produceproject";
	    // } else if(tempType == 2) { //연구 개발 프로젝트
	    // initType = "devproject";
	    // }
	    if (e3psProject != null) {
		this.alertNreloadNclose(res, "수정 되었습니다.");
	    } else {
		this.onlyAlert(res, "수정시 오류 발생 (관리자한테  문의 하십시오)");
	    }

	} catch (WTException e) {
	    Kogger.error(getClass(), e);

	}
    }

    private void PLMupdate(HttpServletRequest req, HttpServletResponse res) {
	// ParamHash paramHash = WebUtil.getHttpParams(req);
	// String oid = (String)paramHash.get("oid"); //Old E3PSProject Object OID
	String initType = null; // initType(영업수주, 양산, 개발)

	// Config conf = ConfigImpl.getInstance();
	// boolean isERPCheck = conf.getBoolean("ERPCHECK");
	// String projectSite = (String) paramHash.get("projectSite"); //SITE (JELProjectSiteLink)
	// String singleProject = (String) paramHash.get("singleProject");

	// String projectName = (String) paramHash.get("projectName");
	// String projectDesc = (String) paramHash.get("projectDesc"); //설명
	String projectDivisionCode = ParamUtil.getStrParameter(req.getParameter("divisioncode")); // JELProjectDivisionCodeLink
	String projectLevelCode = ParamUtil.getStrParameter(req.getParameter("levelcode")); // JELProjectLevelCodeLink
	String projectProductCode = ParamUtil.getStrParameter(req.getParameter("productcode")); // JELProjectProductCodeLink
	String projectCustomerCode = ParamUtil.getStrParameter(req.getParameter("customercode")); // JELProjectCustomerCodeLink
	String projectDevcompanyCode = ParamUtil.getStrParameter(req.getParameter("devcompanycode")); // JELProjectDevCompanyCodeLink
	String projectMakecompanyCode = ParamUtil.getStrParameter(req.getParameter("makecompanycode")); // JELProjectMakeCompanyCodeLink
	String projectModelCode = ParamUtil.getStrParameter(req.getParameter("modelcode"));

	/*
	 * String projectDivisionCode = (String) paramHash.get("divisioncode"); //JELProjectDivisionCodeLink String projectLevelCode =
	 * (String) paramHash.get("levelcode"); //JELProjectLevelCodeLink String projectProductCode = (String) paramHash.get("productcode");
	 * //JELProjectProductCodeLink String projectCustomerCode = (String) paramHash.get("customercode"); //JELProjectCustomerCodeLink
	 * String projectDevcompanyCode = (String) paramHash.get("devcompanycode"); //JELProjectDevCompanyCodeLink String
	 * projectMakecompanyCode = (String) paramHash.get("makecompanycode"); //JELProjectMakeCompanyCodeLink String projectModelCode =
	 * (String) paramHash.get("modelcode"); //JELProjectModelCodeLink
	 */

	// try {
	// E3PSProject E3PSProject = null;
	// E3PSProject = (E3PSProject)CommonUtil.getObject(oid);
	// JELProjectMaster pm = (JELProjectMaster)E3PSProject.getMaster();
	// pm.setPjtName(projectName);
	// PersistenceHelper.manager.save(pm);
	//
	// E3PSProject.setPjtDesc(projectDesc);
	// if(projectDivisionCode != null && projectDivisionCode.length() > 0) {
	// NumberCode code = NumberCodeHelper.manager.getNumberCode("DIVISIONCODE", projectDivisionCode);
	// E3PSProject.setDivision(code);
	// }
	//
	// if(projectLevelCode != null && projectLevelCode.length() > 0) {
	// NumberCode code = NumberCodeHelper.manager.getNumberCode("LEVELCODE", projectLevelCode);
	// E3PSProject.setLevel(code);
	// }
	//
	// if(projectProductCode != null && projectProductCode.length() > 0) {
	// NumberCode code = NumberCodeHelper.manager.getNumberCode("PRODUCTCODE", projectProductCode);
	// E3PSProject.setProduct(code);
	// }
	//
	// if(projectCustomerCode != null && projectCustomerCode.length() > 0) {
	// NumberCode code = NumberCodeHelper.manager.getNumberCode("CUSTOMERCODE", projectCustomerCode);
	// E3PSProject.setCustomer(code);
	// }
	//
	// if(projectDevcompanyCode != null && projectDevcompanyCode.length() > 0) {
	// NumberCode code = NumberCodeHelper.manager.getNumberCode("DEVCOMPANYCODE", projectDevcompanyCode);
	// E3PSProject.setDevcompany(code);
	// }
	//
	// if(projectMakecompanyCode != null && projectMakecompanyCode.length() > 0) {
	// NumberCode code = NumberCodeHelper.manager.getNumberCode("MAKECOMPANYCODE", projectMakecompanyCode);
	// E3PSProject.setMakecompany(code);
	// }
	//
	// if(projectModelCode != null && projectModelCode.length() > 0) {
	// NumberCode code = NumberCodeHelper.manager.getNumberCode("MODELCODE", projectModelCode);
	// E3PSProject.setModel(code);
	// }
	//
	// E3PSProject = (E3PSProject)PersistenceHelper.manager.modify(E3PSProject);
	//
	// if(E3PSProject != null) {
	// this.alertNgoNclose(res, "프로젝트가 수정되었습니다.",
	// "/plm/jsp/project/ProjectView.jsp?oid="+CommonUtil.getOIDString(E3PSProject)+"&isReload=true");
	// } else {
	// int tempType = ((E3PSProject)CommonUtil.getObject(oid)).getPjtType();
	// if(tempType == 0) { //영업수주 프로젝트
	// initType = "saleproject";
	// } else if(tempType == 1) { //고객 제공 프로젝트
	// initType = "produceproject";
	// } else if(tempType == 2) { //연구 개발 프로젝트
	// initType = "devproject";
	// }
	// this.alertNgoNclose(res, "프로젝트가 수정되지 않았습니다.\n다시 프로젝트를 수정시켜 주십시요.", "/plm/jsp/project/ListJELProject.jsp?initType="+initType);
	// }
	// } catch (Exception e) {
	// Kogger.error(getClass(), e);
	// }
    }

    private Vector getParamsVector(Object obj) {
	Vector vec = new Vector();
	if (obj == null)
	    return vec;
	if (obj instanceof String) {
	    vec.add(obj.toString());
	} else if (obj instanceof String[]) {
	    String[] strs = (String[]) obj;
	    for (int i = 0; i < strs.length; i++) {
		vec.addElement(strs[i]);
	    }
	} else if (obj instanceof Vector) {
	    vec = (Vector) obj;
	}
	return vec;
    }

    private void enabled(HttpServletRequest req, HttpServletResponse res) {
	// ParamHash paramHash = WebUtil.getHttpParams(req);
	// String oid = (String)paramHash.get("oid");
	String url = "";
	Hashtable hash = new Hashtable();
	// hash.put("OID", oid);

	// try {
	// Object obj = (Object)CommonUtil.getObject(oid);
	// if (obj instanceof TemplateProject) {
	// TemplateProject project = (TemplateProject)obj;
	//
	// LifeCycleHelper.service.setLifeCycleState(project, State.toState("APPROVED"), true);
	//
	// url = "/plm/servlet/e3ps.project.servlet.SearchProjectTemplateServlet";
	// }
	//
	// res.setContentType("text/html;charset=KSC5601");
	// PrintWriter out = res.getWriter();
	// String rtn_msg = "";
	//
	// String urlA = "/plm/jsp/common/loading.jsp?url=/plm/portal/common/project_submenu.jsp";
	// // var urlB = "/plm/jsp/project/ProjectSearch.jsp?initType="+type;
	// // parent.movePaage(urlA, urlB);
	//
	//
	// rtn_msg = "\n <script language=\"javascript\">"
	// + "\n   alert(\"프로젝트가 사용 가능 되었습니다..\");"
	// //+ "\n   parent.document.location.href = '" + url + "';"
	// + "\n parent.movePaage('"+urlA+"','"+url+"');"
	// + "\n </script>";
	// out.println(rtn_msg);
	// } catch (WTException e) {
	// Kogger.error(getClass(), e);
	// } catch (IOException e) {
	// Kogger.error(getClass(), e);
	// }
    }

    private void disabled(HttpServletRequest req, HttpServletResponse res) {
	// ParamHash paramHash = WebUtil.getHttpParams(req);
	// String oid = (String)paramHash.get("oid");
	String url = "";
	Hashtable hash = new Hashtable();
	// hash.put("OID", oid);

	// try {
	// Object obj = (Object)CommonUtil.getObject(oid);
	// if (obj instanceof TemplateProject) {
	// TemplateProject project = (TemplateProject)obj;
	//
	// LifeCycleHelper.service.setLifeCycleState(project, State.toState("CANCELLED"), true);
	//
	// url = "/plm/servlet/e3ps.project.servlet.SearchProjectTemplateServlet";
	// }
	//
	// res.setContentType("text/html;charset=KSC5601");
	// PrintWriter out = res.getWriter();
	// String rtn_msg = "";
	//
	// String urlA = "/plm/jsp/common/loading.jsp?url=/plm/portal/common/project_submenu.jsp";
	// // var urlB = "/plm/jsp/project/ProjectSearch.jsp?initType="+type;
	// // parent.movePaage(urlA, urlB);
	//
	//
	// rtn_msg = "\n <script language=\"javascript\">"
	// + "\n   alert(\"프로젝트가 사용 불가 되었습니다..\");"
	// //+ "\n   parent.document.location.href = '" + url + "';"
	// + "\n parent.movePaage('"+urlA+"','"+url+"');"
	// + "\n </script>";
	// out.println(rtn_msg);
	// } catch (WTException e) {
	// Kogger.error(getClass(), e);
	// } catch (IOException e) {
	// Kogger.error(getClass(), e);
	// }
    }

    private void delete(HttpServletRequest req, HttpServletResponse res) {
	ParamHash paramHash = WebUtil.getHttpParams(req);
	String oid = (String) paramHash.get("oid");
	String popup = (String) paramHash.get("popup");

	// [PLM System 1차개선] Project 일정 변경 팝업 화면에서 [일정변경 취소] 버튼 클릭 시 처리, 2013-06-23, BoLee
	String mode = StringUtil.checkNull((String) paramHash.get("mode"));

	// [PLM System 1차개선] 다국어 처리, 2013-08-23, BoLee
	KETMessageService messageService = KETMessageService.getMessageService(req);
	String deleteMsg = messageService.getString("e3ps.message.ket_message", "03400");// 일정변경이 취소되었습니다.

	Kogger.debug(getClass(), "delete==popup>" + popup);
	// String oid = req.getParameter("oid");
	// String auth = req.getParameter("auth");

	String url = "";
	Hashtable hash = new Hashtable();
	hash.put("OID", oid);

	try {
	    Object obj = (Object) CommonUtil.getObject(oid);
	    String name0Value = "";

	    if (obj instanceof E3PSProject) {
		Kogger.debug(getClass(), "E3PSProject Instance");

		E3PSProject e3psoid = (E3PSProject) obj;
		String type = "";
		if (e3psoid.getPjtType() == 0) {
		    type = "postproject";
		} else if (e3psoid.getPjtType() == 1) {
		    type = "produceproject";
		} else if (e3psoid.getPjtType() == 2) {
		    type = "devproject";
		}
		try {
		    // this.restoreGateTask(e3psoid);
		    // 프로그램 링크 복원
		    this.restoreProgram(e3psoid);
		    CustomerPlanHelper.deleteFromProject(oid);
		    this.deleteTaskAssessByTask(e3psoid);// 평가관리 삭제

		    // 파생차종 삭제
		    QueryResult oemTypeQr = PersistenceHelper.manager.navigate(e3psoid, ProjectOemTypeLink.OEM_PJT_TYPE_ROLE, ProjectOemTypeLink.class, false);
		    while (oemTypeQr.hasMoreElements()) {
			ProjectOemTypeLink link = (ProjectOemTypeLink) oemTypeQr.nextElement();
			PersistenceHelper.manager.delete(link);
		    }

		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}

		PersistenceHelper.manager.delete(e3psoid);

		if (e3psoid.getState().toString().equals("PMOINWORK")) {
		    deleteMsg = messageService.getString("e3ps.message.ket_message", "01699");// 삭제되었습니다.
		}
		url = "/plm/jsp/project/ProjectSearch.jsp?initType=" + type;
	    } else if (obj instanceof TemplateProject) {
		E3PSProjectHelper.service.deleteTemplateProject(hash);
		url = "/plm/servlet/e3ps/SearchProjectTemplateServlet";
		name0Value = "&key=name0&value=template";
	    }

	    res.setContentType("text/html;charset=KSC5601");
	    PrintWriter out = res.getWriter();
	    String rtn_msg = "";
	    String urlA = "/plm/jsp/common/loading.jsp?url=/plm/portal/common/project_submenu.jsp" + name0Value;

	    // [START] [PLM System 1차개선] Project 일정 변경 팝업 화면에서 [일정변경 취소] 버튼 클릭 시 Project 삭제 처리 추가, 2013-06-23, BoLee

	    if ("ChangeSchedule".equals(mode)) {

		// Project 일정 변경 팝업에서 [일정변경 취소] 버튼 클릭 시, Project 삭제 후 Project 검색 화면으로 이동 및 Project 일정 변경 팝업 닫음

		rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + deleteMsg + "\");"
		        + "\n   if ( opener.parent.opener != null && opener.parent.opener.document.location.href.indexOf('ProjectSearch.jsp') > 0) opener.parent.opener.search();"
		        + "\n   opener.parent.window.close();" + "\n   self.close();" + "\n </script>";

		out.println(rtn_msg);
	    } else {

		rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + deleteMsg + "\");"
		        + "\n   if ( parent.opener != null && parent.opener.document.location.href.indexOf('ProjectSearch.jsp') > 0) parent.opener.search(); " + "\n   parent.window.close();"
		        + "\n   self.close();" + "\n </script>" + "\n </script>";

		out.println(rtn_msg);
	    }

	    // [END] [PLM System 1차개선] Project 일정 변경 팝업 화면에서 [일정변경 취소] 버튼 클릭 시 Project 삭제 처리 추가, 2013-06-23, BoLee

	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * 프로그램 복원
     * 
     * @param e3psoid
     * @throws Exception
     * @메소드명 : restoreProgram
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 26.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private void restoreProgram(E3PSProject e3psoid) throws Exception {
	if (e3psoid instanceof ProductProject) {
	    QueryResult rs = PersistenceHelper.manager.navigate(e3psoid, CheckoutLink.ORIGINAL_COPY_ROLE, CheckoutLink.class, true);
	    if (rs.hasMoreElements()) {
		E3PSProject originalCopy = (E3PSProject) rs.nextElement();

		// 프로그램 링크 복원
		Kogger.debug(getClass(), "프로그램 링크 복원");
		QueryResult rs6 = PersistenceHelper.manager.navigate(e3psoid, KETProgramProjectLink.PROGRAM_ROLE, KETProgramProjectLink.class, false);
		while (rs6.hasMoreElements()) {
		    KETProgramProjectLink programProjectLink = (KETProgramProjectLink) rs6.nextElement();
		    programProjectLink.setProject((ProductProject) originalCopy);
		    programProjectLink = (KETProgramProjectLink) PersistenceHelper.manager.save(programProjectLink);
		}
		// 프로그램 상태 변경
		Kogger.debug(getClass(), "프로그램 상태 변경");
		ProgramHelper.service.updateProgramState((ProductProject) originalCopy);

		// 이슈 링크 복사
		rs = PersistenceHelper.manager.navigate(e3psoid, ProjectIssueLink.ISSUE_ROLE, ProjectIssueLink.class, false);
		while (rs.hasMoreElements()) {
		    ProjectIssueLink link = (ProjectIssueLink) rs.nextElement();
		    ProjectIssueLink newLink = ProjectIssueLink.newProjectIssueLink(originalCopy, link.getIssue());
		    // newSheetLink.setProject((ProductProject) workingCopy);
		    PersistenceHelper.manager.save(newLink);
		}
	    }
	}
    }

    private void deleteTaskAssessByTask(E3PSProject e3psoid) throws Exception {

	TaskAssessResult assRst = null;

	QuerySpec spec = new QuerySpec(TemplateTask.class);
	spec.appendWhere(new SearchCondition(TemplateTask.class, "projectReference.key.id", "=", PersistenceHelper.getObjectIdentifier(e3psoid).getId()), new int[] { 0 });

	QueryResult rs = PersistenceHelper.manager.find(spec);

	while (rs.hasMoreElements()) {
	    TemplateTask task = (TemplateTask) rs.nextElement();
	    if ("DR".equalsIgnoreCase(task.getTaskType())) {
		QueryResult qr = PersistenceHelper.manager.navigate(task, AssessTemplateTaskLink.ASSESS_ROLE, AssessTemplateTaskLink.class, false);

		while (qr.hasMoreElements()) {
		    AssessTemplateTaskLink taskLink = (AssessTemplateTaskLink) qr.nextElement();
		    assRst = taskLink.getAssess();
		    PersistenceHelper.manager.delete(taskLink);
		    PersistenceHelper.manager.delete(assRst);
		}
	    }
	}

    }

    /**
     * 프로젝트 링크 정보 복원(프로그램, Gate, 신뢰성 차수)
     * 
     * @param e3psoid
     * @throws WTException
     * @throws WTPropertyVetoException
     * @throws Exception
     * @메소드명 : restoreGateTask
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private void restoreGateTask(E3PSProject e3psoid) throws Exception {
	// 제픔프로젝트이면 이전 Ref 삭제 이전 버전으로 변경
	if (e3psoid instanceof ProductProject) {
	    // if(e3psoid.getO)
	    QueryResult rs = PersistenceHelper.manager.navigate(e3psoid, CheckoutLink.ORIGINAL_COPY_ROLE, CheckoutLink.class, true);
	    if (rs.hasMoreElements()) {
		E3PSProject originalCopy = (E3PSProject) rs.nextElement();

		// 프로그램 링크 복원
		Kogger.debug(getClass(), "프로그램 링크 복원");
		QueryResult rs6 = PersistenceHelper.manager.navigate(e3psoid, KETProgramProjectLink.PROGRAM_ROLE, KETProgramProjectLink.class, false);
		while (rs6.hasMoreElements()) {
		    KETProgramProjectLink programProjectLink = (KETProgramProjectLink) rs6.nextElement();
		    programProjectLink.setProject((ProductProject) originalCopy);
		    programProjectLink = (KETProgramProjectLink) PersistenceHelper.manager.save(programProjectLink);
		}
		// 프로그램 상태 변경
		Kogger.debug(getClass(), "프로그램 상태 변경");
		ProgramHelper.service.updateProgramState((ProductProject) originalCopy);

		// Gate 평가결과 변경
		Kogger.debug(getClass(), "Gate 평가결과 복원");
		List<E3PSTask> taskList = ProjectTaskHelper.manager.getAllTask(e3psoid);
		Kogger.debug(getClass(), "taskList.size() >> " + taskList.size());
		// 삭제될 모든 Task들을 조회
		for (E3PSTask fromTask : taskList) {
		    // 이중 Gate Task를 통해 GateAssessResult 객체를 찾은 후 이전 등록된 Task로 복원 한다.
		    if ("Gate".equalsIgnoreCase(fromTask.getTaskType())) {
			QueryResult rs7 = PersistenceHelper.manager.navigate(fromTask, GateAssRsltTaskLink.ASSESS_ROLE, GateAssRsltTaskLink.class, true);
			while (rs7.hasMoreElements()) {
			    GateAssessResult assessResult = (GateAssessResult) rs7.nextElement();
			    E3PSTask toTask = (E3PSTask) CommonUtil.getObject(fromTask.getOldTaskOid());
			    if (toTask != null) {
				Kogger.debug(getClass(), "Gate 평가결과 복원 >> " + assessResult);
				assessResult.setProject((ProductProject) originalCopy);
				// assessResult.setTask(toTask); // 과거 Task oid로 변경
				PersistenceServerHelper.manager.update(assessResult);

				// 기존 산출물을 다시 기존 산출물로 복원
				QueryResult rs8 = PersistenceHelper.manager.navigate(fromTask, TaskOutputLink.OUTPUT_ROLE, TaskOutputLink.class, true);
				while (rs8.hasMoreElements()) {
				    ProjectOutput fromOutput = (ProjectOutput) rs8.nextElement();
				    ProjectOutput toOutput = (ProjectOutput) CommonUtil.getObject(fromOutput.getOldOutputOid());

				    // Gate 산출물 관계
				    rs = PersistenceHelper.manager.navigate(fromOutput, AssessResultOutputLink.ASSESS_ROLE, AssessResultOutputLink.class, false);
				    while (rs.hasMoreElements()) {
					Kogger.debug(getClass(), ">>> Gate 산출물 ProjectOut 변경");
					Kogger.debug(getClass(), "Gate output >> " + fromOutput.getOutputName());
					AssessResultOutputLink assessResultOutputLink = (AssessResultOutputLink) rs.nextElement();
					assessResultOutputLink.setOutput(toOutput);
					PersistenceHelper.manager.save(assessResultOutputLink);
				    }

				    // 신뢰성 Task 링크 관계
				    rs = PersistenceHelper.manager.navigate(fromOutput, TrustTemplateTaskLink.TRUST_ROLE, TrustTemplateTaskLink.class, false);
				    while (rs.hasMoreElements()) {
					Kogger.debug(getClass(), ">>> 신뢰성 Task link 변경");
					Kogger.debug(getClass(), "신뢰성 Task >> " + toTask.getTaskName());
					TrustTemplateTaskLink trustTemplateTaskLink = (TrustTemplateTaskLink) rs.nextElement();
					trustTemplateTaskLink.setTask(toTask);
					PersistenceHelper.manager.save(trustTemplateTaskLink);
				    }

				    // 신뢰성 산출물 관계
				    rs = PersistenceHelper.manager.navigate(fromOutput, TrustProjectOutputLink.TRUST_ROLE, TrustProjectOutputLink.class, false);
				    while (rs.hasMoreElements()) {
					Kogger.debug(getClass(), ">>> 신뢰성 산출물 ProjectOut 변경");
					Kogger.debug(getClass(), "신뢰성 output >> " + fromOutput.getOutputName());
					TrustProjectOutputLink trustProjectOutputLink = (TrustProjectOutputLink) rs.nextElement();
					trustProjectOutputLink.setOutput(toOutput);
					PersistenceHelper.manager.save(trustProjectOutputLink);
				    }

				}
			    }
			}
		    }
		}

		// 프로젝트 목표 Gate관리 변경
		Kogger.debug(getClass(), "프로젝트 목표 Gate관리 복원");
		QueryResult rs8 = PersistenceHelper.manager.navigate(e3psoid, ProjectAssSheetLink.ASSESS_ROLE, ProjectAssSheetLink.class, false);
		while (rs8.hasMoreElements()) {
		    ProjectAssSheetLink sheetLink = (ProjectAssSheetLink) rs8.nextElement();
		    sheetLink.setProject((ProductProject) originalCopy);
		    sheetLink = (ProjectAssSheetLink) PersistenceHelper.manager.save(sheetLink);
		}
	    }
	}
    }

    private void updateProject(HttpServletRequest req, HttpServletResponse res) {
	try {
	    String oid = req.getParameter("oid");
	    ArrayList arrayList = ProjectERPConMgr.getInstance().getUpdateProjectNO(oid);
	    if (arrayList != null) {
		req.setAttribute("arrayList", arrayList);
		gotoResult(req, res, "/jsp/project/ProjectERPUpdate.jsp?oid=" + oid);
	    } else {
		this.alertNclose(res, "변경된 내용이 존재하지 않습니다.");
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    private void myworkProject(HttpServletRequest req, HttpServletResponse res) {
	try {
	    // req.setAttribute("control", getPageControl(req, res));
	    gotoResult(req, res, "/jsp/project/ProjectSearch.jsp");

	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	}
    }

    private void list(HttpServletRequest req, HttpServletResponse res) {
	try {
	    // req.setAttribute("control", getPageControl(req, res));
	    gotoResult(req, res, "/jsp/project/ProjectSearch.jsp");
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	}
    }

    protected QuerySpec getQuerySpec(HttpServletRequest req, HttpServletResponse res) {
	QuerySpec spec = this.getJELProjectQuery(req, res);
	return spec;
    }

    private QuerySpec getJELProjectQuery(HttpServletRequest req, HttpServletResponse res) {
	QuerySpec spec = null;
	try {
	    // Attribute Setting
	    String cmd = req.getParameter("command");
	    String initType = req.getParameter("initType");

	    spec = new QuerySpec();

	    Class target = E3PSProject.class;
	    int idx_target = spec.addClassList(target, true);

	    if (StringUtil.checkString(cmd)) {
		if (cmd.equalsIgnoreCase("search") || cmd.equalsIgnoreCase("select")) {
		    String pjtNo = req.getParameter("pjtNo").trim(); // pjtNo(프로젝트 NO)
		    String pjtName = req.getParameter("pjtName").trim(); // pjtName(프로젝트 명)
		    String pjtType = req.getParameter("pjtType").trim(); // pjtType(프로젝트종류)
		    String pjtState = req.getParameter("pjtState").trim(); // pjtState(상태)
		    String pjtFabName = req.getParameter("pjtFabName").trim(); // pjtFabName(FAB Name)
		    String pjtProduct = req.getParameter("pjtProduct").trim(); // pjtProduct(PRODUCT)
		    String pjtCustomer = req.getParameter("pjtCustomer").trim(); // pjtCustomer(거래처)
		    String pjtConsignment = req.getParameter("pjtConsignment").trim(); // pjtConsignment(출하조건)
		    String pjtAcceptance = req.getParameter("pjtAcceptance").trim(); // pjtAcceptance(수주형태)
		    String pjtMaketing = req.getParameter("pjtMaketing").trim(); // pjtMaketing(판매형태)
		    String planStartStartDate = req.getParameter("planStartStartDate").trim(); // planStartStartDate(계획 시작일자 - 시작)
		    String planStartEndDate = req.getParameter("planStartEndDate").trim(); // planStartEndDate(계획 시작일자 - 끝)
		    String planEndStartDate = req.getParameter("planEndStartDate").trim(); // planEndStartDate(계획 종료일자 - 시작)
		    String planEndEndDate = req.getParameter("planEndEndDate").trim(); // planEndEndDate(계획 종료일자 - 끝)
		    String acceptanceStartDate = req.getParameter("acceptanceStartDate").trim(); // acceptanceStartDate(수주일자 - 시작)
		    String acceptanceEndDate = req.getParameter("acceptanceEndDate").trim(); // acceptanceEndDate(수주일자 - 끝)
		    String deliveredStartDate = req.getParameter("deliveredStartDate").trim(); // deliveredStartDate(출하주일자 - 시작)
		    String deliveredEndDate = req.getParameter("deliveredEndDate").trim(); // deliveredEndDate(출하일자 - 끝)

		    // 프로젝트 NO Field
		    if (StringUtil.checkString(pjtNo)) {
			// SearchUtil.appendLIKE(spec, target, E3PSProject.PJT_NO, pjtNo, idx_target);
		    }

		    // 프로젝트 명 Field
		    if (StringUtil.checkString(pjtName)) {
			// SearchUtil.appendLIKE(spec, target, E3PSProject.PJT_NAME, pjtName, idx_target);
		    }

		    // 프로젝트 종류 Field(0: 영업수주, 1: 양산, 2: 개발)
		    if (StringUtil.checkString(pjtType)) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();
			// spec.appendWhere(new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE,
			// Integer.parseInt(pjtType)), new int[]{idx_target});
		    }

		    // 프로젝트 상태 Field
		    if (StringUtil.checkString(pjtState)) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();
			// SearchCondition where = new SearchCondition(target, E3PSProject.PJT_STATE, SearchCondition.LIKE,
			// Integer.parseInt(pjtState));
			// spec.appendWhere(where, new int[]{idx_target});
		    }

		    // FAB Name Field
		    /*
	             * if(StringUtil.checkString(pjtFabName)) { SearchUtil.appendLIKE(spec, target, E3PSProject.FAB_NAME, pjtFabName,
	             * idx_target); }
	             */

		    // PRODUCT Field
		    if (StringUtil.checkString(pjtProduct)) {
			// SearchUtil.appendLIKE(spec, target, E3PSProject.PRODUCT, pjtProduct, idx_target);
		    }

		    // 거래처 Field
		    if (StringUtil.checkString(pjtCustomer)) {
			getNumberCodeQuery(spec, target, idx_target, "customerReference.key.id", "CUSTOMER", pjtCustomer);
		    }

		    // 출하조건 Field
		    if (StringUtil.checkString(pjtConsignment)) {
			getNumberCodeQuery(spec, target, idx_target, "consignmentReference.key.id", "CONSIGNMENT", pjtConsignment);
		    }

		    // 수주형태 Field
		    if (StringUtil.checkString(pjtAcceptance)) {
			getNumberCodeQuery(spec, target, idx_target, "acceptanceReference.key.id", "ACCEPTANCETYPE", pjtAcceptance);
		    }

		    // 판매형태 Field
		    if (StringUtil.checkString(pjtMaketing)) {
			getNumberCodeQuery(spec, target, idx_target, "marketingReference.key.id", "SALETYPE", pjtMaketing);
		    }

		    // 계획 시작일자(시작 ~ 끝), 계획 종료일자(시작 ~ 끝)
		    if ((planStartStartDate != null && planStartStartDate.length() > 0) || (planStartEndDate != null && planStartEndDate.length() > 0)
			    || (planEndStartDate != null && planEndStartDate.length() > 0) || (planEndEndDate != null && planEndEndDate.length() > 0)) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			Class schedule = ExtendScheduleData.class;
			int idx_schedule = spec.appendClassList(schedule, false);

			ClassAttribute ca1 = null;
			ClassAttribute ca2 = null;

			ca1 = new ClassAttribute(target, "pjtSchedule.key.id");
			ca2 = new ClassAttribute(schedule, "thePersistInfo.theObjectIdentifier.id");
			SearchCondition sc = new SearchCondition(ca1, "=", ca2);
			sc.setFromIndicies(new int[] { idx_target, idx_schedule }, 0);
			sc.setOuterJoin(0);
			spec.appendWhere(sc, new int[] { idx_target, idx_schedule });

			// 계획 시작일자 - 시작 Field
			if (planStartStartDate != null && planStartStartDate.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE, SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil.convertStartDate(planStartStartDate));
			    spec.appendWhere(sc, new int[] { idx_schedule });
			}

			// 계획 시작일자 - 끝 Field
			if (planStartEndDate != null && planStartEndDate.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE, SearchCondition.LESS_THAN, DateUtil.convertStartDate(planStartEndDate));
			    spec.appendWhere(sc, new int[] { idx_schedule });
			}

			// 계획 종료일자 - 시작 Field
			if (planEndStartDate != null && planEndStartDate.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil.convertStartDate(planEndStartDate));
			    spec.appendWhere(sc, new int[] { idx_schedule });
			}

			// 계획 종료일자 - 끝 Field
			if (planEndEndDate != null && planEndEndDate.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, SearchCondition.LESS_THAN, DateUtil.convertStartDate(planEndEndDate));
			    spec.appendWhere(sc, new int[] { idx_schedule });
			}
		    }

		    // 수주일자 - 시작 Field
		    /*
	             * if(acceptanceStartDate != null && acceptanceStartDate.length() > 0) { if(spec.getConditionCount() > 0)
	             * spec.appendAnd(); SearchCondition where = new SearchCondition(target, E3PSProject.ACCEPTANCE_DATE,
	             * SearchCondition.GREATER_THAN, DateUtil.convertStartDate(acceptanceStartDate)); spec.appendWhere(where, new
	             * int[]{idx_target}); }
	             */
		    // 수주일자 - 끝 Field
		    /*
	             * if(acceptanceEndDate != null && acceptanceEndDate.length() > 0) { if(spec.getConditionCount() > 0) spec.appendAnd();
	             * SearchCondition where = new SearchCondition(target, E3PSProject.ACCEPTANCE_DATE, SearchCondition.LESS_THAN,
	             * DateUtil.convertEndDate(acceptanceEndDate)); spec.appendWhere(where, new int[]{idx_target}); }
	             */

		    // 출하일자 - 시작 Field
		    /*
	             * if(deliveredStartDate != null && deliveredStartDate.length() > 0) { if(spec.getConditionCount() > 0)
	             * spec.appendAnd(); SearchCondition where = new SearchCondition(target, E3PSProject.DELIVERED_DATE,
	             * SearchCondition.GREATER_THAN, DateUtil.convertStartDate(deliveredStartDate)); spec.appendWhere(where, new
	             * int[]{idx_target}); }
	             */
		    // 출하일자 - 끝 Field
		    /*
	             * if(deliveredEndDate != null && deliveredEndDate.length() > 0) { if(spec.getConditionCount() > 0) spec.appendAnd();
	             * SearchCondition where = new SearchCondition(target, E3PSProject.DELIVERED_DATE, SearchCondition.LESS_THAN,
	             * DateUtil.convertEndDate(deliveredEndDate)); spec.appendWhere(where, new int[]{idx_target}); }
	             */

		    // PROCESS Field
		    // if(StringUtil.checkString(pjtProcess)) {
		    // SearchUtil.appendLIKE(spec, target, E3PSProject.PROCESS, pjtProcess, idx_target);
		    // }

		    // Chamber NO Field
		    // if(StringUtil.checkString(pjtChamberNo)) {
		    // SearchUtil.appendLIKE(spec, target, E3PSProject.CHAMBER_NO, pjtChamberNo, idx_target);
		    // }

		    // 총 기간 Field
		    // if(StringUtil.checkString(pjtDuration)) {
		    // Class schedule = ExtendScheduleData.class;
		    // int idx_schedule = spec.addClassList(schedule, false);
		    // if(spec.getConditionCount() > 0) spec.appendAnd();
		    // spec.appendOpenParen();
		    // ClassAttribute attr1 = new ClassAttribute(target, E3PSProject.PJT_HISTORY);
		    // ClassAttribute attr2 = new ClassAttribute(schedule, ExtendScheduleData.SCHEDULE_HISTORY);
		    // SearchCondition where1 = new SearchCondition(attr1, SearchCondition.EQUAL, attr2);
		    // spec.appendWhere(where1, new int[]{idx_target, idx_schedule});
		    // spec.appendAnd();
		    // SearchCondition where2 = new SearchCondition(schedule, ExtendScheduleData.DURATION, SearchCondition.EQUAL,
		    // Integer.parseInt(pjtDuration));
		    // spec.appendWhere(where2, new int[]{idx_schedule});
		    // spec.appendCloseParen();
		    // }

		    // 프로젝트 설명 Field
		    // if(StringUtil.checkString(pjtDesc)) {
		    // SearchUtil.appendLIKE(spec, target, E3PSProject.PJT_DESC, pjtDesc, idx_target);
		    // }
		} else if (cmd.equalsIgnoreCase("myworkProject")) {
		    Class wtuserClass = WTUser.class;
		    Class linkClass = ProjectMemberLink.class;
		    int idx_wtuserClass = spec.addClassList(wtuserClass, false);
		    int idx_linkClass = spec.addClassList(linkClass, false);

		    spec.appendJoin(idx_linkClass, ProjectMemberLink.PROJECT_ROLE, idx_target);
		    spec.appendJoin(idx_linkClass, ProjectMemberLink.MEMBER_ROLE, idx_wtuserClass);
		    spec.appendWhere(new SearchCondition(linkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.NOT_EQUAL, ProjectUserHelper.PM), idx_linkClass);
		    spec.appendAnd();
		    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
		    long lperistable = CommonUtil.getOIDLongValue(wtuser);
		    spec.appendWhere(new SearchCondition(wtuserClass, "thePersistInfo.theObjectIdentifier.id", SearchCondition.EQUAL, lperistable), idx_wtuserClass);

		    // SearchUtil.setOrderBy( spec, target, idx_target, E3PSProject.PJT_STATE, "sort0", false);

		}
	    } else {
		String pjtNo = req.getParameter("searchPjtNo");
		String pjtCustomer = req.getParameter("searchPjtCustomer");
		String pjtState = req.getParameter("pjtState");

		// 프로젝트 NO Field
		if (StringUtil.checkString(pjtNo)) {
		    // SearchUtil.appendLIKE(spec, target, E3PSProject.PJT_NO, pjtNo, idx_target);
		}
		// 거래처 Field
		if (StringUtil.checkString(pjtCustomer)) {
		    getNumberCodeQuery(spec, target, idx_target, "customerReference.key.id", "CUSTOMER", pjtCustomer);
		}
		// 프로젝트 상태 Field
		if (StringUtil.checkString(pjtState)) {
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    // SearchCondition where = new SearchCondition(target, E3PSProject.PJT_STATE, SearchCondition.LIKE,
		    // Integer.parseInt(pjtState));
		    // spec.appendWhere(where, new int[]{idx_target});
		}
	    }

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(target, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")), idx_target);

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(target, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { idx_target });

	    if (StringUtil.checkString(initType)) {
		if (initType.equalsIgnoreCase("produceproject")) { // 프로젝트
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    // spec.appendWhere(new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("1")), new
		    // int[]{idx_target});
		} else if (initType.equalsIgnoreCase("devproject")) { // 개발 프로젝트
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    // spec.appendWhere(new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("2")), new
		    // int[]{idx_target});
		}
	    }

	    if (!CommonUtil.isAdmin()) {
		if (isProjectUser((WTUser) SessionHelper.manager.getPrincipal(), ProjectUserHelper.PM)) {
		    // 프로젝트 상태 Field
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    // spec.appendOpenParen();
		    // SearchCondition where1 = new SearchCondition(target, E3PSProject.PJT_STATE, SearchCondition.LIKE,
		    // ProjectStateFlag.PROJECT_STATE_PROGRESS);
		    // spec.appendWhere(where1, new int[]{idx_target});
		    // spec.appendOr();
		    // SearchCondition where2 = new SearchCondition(target, E3PSProject.PJT_STATE, SearchCondition.LIKE,
		    // ProjectStateFlag.PROJECT_STATE_READY);
		    // spec.appendWhere(where2, new int[]{idx_target});
		    // spec.appendCloseParen();
		} else {
		    // 프로젝트 상태 Field
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    // spec.appendOpenParen();
		    // SearchCondition where1 = new SearchCondition(target, E3PSProject.PJT_STATE, SearchCondition.NOT_LIKE,
		    // ProjectStateFlag.PROJECT_STATE_READY);
		    // spec.appendWhere(where1, new int[]{idx_target});
		    // spec.appendCloseParen();
		}
	    }

	    SearchUtil.setOrderBy(spec, target, idx_target, "thePersistInfo.theObjectIdentifier.id", true);
	    // Kogger.debug(getClass(), "ProjectServlet>>>QuerySpec<<< \n"+spec);
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return spec;
    }

    private QuerySpec getNumberCodeQuery(QuerySpec spec, Class target, int idx, String ref, String key, String value) {
	try {
	    SearchUtil.appendEQUAL(spec, target, ref, CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode(key, value)), idx);
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	}
	return spec;
    }

    private boolean isProjectUser(WTUser wtuser, int memberType) {
	try {
	    QuerySpec qs = new QuerySpec();
	    Class peopleProjectLinkClass = ProjectMemberLink.class;
	    int peopleProjectLinkClassPos = qs.addClassList(peopleProjectLinkClass, true);

	    qs.appendWhere(new SearchCondition(peopleProjectLinkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, memberType), peopleProjectLinkClassPos);
	    qs.appendAnd();
	    long oidValue = CommonUtil.getOIDLongValue(wtuser);
	    qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue), peopleProjectLinkClassPos);
	    QueryResult queryresult = PersistenceHelper.manager.find(qs);
	    if (queryresult != null && queryresult.size() > 0) {
		return true;
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return false;
    }

    private void projectExcelDown(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException {
	String strClient = req.getHeader("User-Agent");

	// Excel file download ...
	java.util.Date date = new java.util.Date();
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
	String fileName = "ProjectExcelList_" + formatter.format(date) + ".xls";
	// String fileName = new String("list".getBytes("euc-kr"), "8859_1");

	if (strClient.indexOf("MSIE 5.5") > -1) {
	    res.setHeader("Content-Disposition", "filename=" + fileName + ".xls;");
	}

	else {
	    res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls;");
	}
	res.setHeader("Set-Cookie", "fileDownload=true; path=/");
	// 저장
	HashMap map = new HashMap();
	String command = ParamUtil.getStrParameter(req.getParameter("command"));
	String isSelect = ParamUtil.getStrParameter(req.getParameter("isSelect"));
	String mode = ParamUtil.getStrParameter(req.getParameter("mode"));
	String initType = ParamUtil.getStrParameter(req.getParameter("radio"));

	String partNo = ParamUtil.getStrParameter(req.getParameter("partNo"));
	String partName = ParamUtil.getStrParameter(req.getParameter("partName"));
	String dType = "";
	String pjtNo = "";
	String pjtName = "";
	String pjtState = "";
	String setPm = "";
	String planStartStartDate = "";
	String planStartEndDate = "";
	String planEndStartDate = "";
	String planEndEndDate = "";

	String PRODUCTTYPE = "";
	String RANK = "";
	String ASSEMBLEDTYPE = "";
	String DEVELOPENTTYPE = "";

	String dieNo = ParamUtil.getStrParameter(req.getParameter("dieNo"));
	String devUserDept = ParamUtil.getStrParameter(req.getParameter("devUserDept"));
	String devDeptOid = ParamUtil.getStrParameter(req.getParameter("devDeptOid"));
	String productpjtNo = ParamUtil.getStrParameter(req.getParameter("productpjtNo"));
	String itemType = ParamUtil.getStrParameter(req.getParameter("itemType"));
	String moldProductType = ParamUtil.getStrParameter(req.getParameter("moldProductType"));
	String productPartNo = ParamUtil.getStrParameter(req.getParameter("productPartNo"));
	String productName = ParamUtil.getStrParameter(req.getParameter("productName"));
	String making = ParamUtil.getStrParameter(req.getParameter("making"));
	String moldType = ParamUtil.getStrParameter(req.getParameter("moldType"));
	String outsourcing = ParamUtil.getStrParameter(req.getParameter("outsourcing"));
	String tempsetProductPm = ParamUtil.getStrParameter(req.getParameter("tempsetProductPm"));
	String setProductPm = ParamUtil.getStrParameter(req.getParameter("setProductPm"));
	String tempsetMoldCharger = ParamUtil.getStrParameter(req.getParameter("tempsetMoldCharger"));
	String setMoldCharger = ParamUtil.getStrParameter(req.getParameter("setMoldCharger"));
	String carTypeInfo2 = ParamUtil.getStrParameter(req.getParameter("carTypeInfo2"));
	map.put("dieNo", dieNo);
	map.put("devDeptOid", devDeptOid);
	map.put("productpjtNo", productpjtNo);
	map.put("itemType", itemType);
	map.put("moldProductType", moldProductType);
	map.put("productPartNo", productPartNo);
	map.put("productName", productName);
	map.put("making", making);
	map.put("moldType", moldType);
	map.put("outsourcing", outsourcing);
	map.put("tempsetProductPm", tempsetProductPm);
	map.put("setProductPm", setProductPm);
	map.put("tempsetMoldCharger", setProductPm);
	map.put("setMoldCharger", setMoldCharger);
	map.put("carTypeInfo2", carTypeInfo2);

	// Kogger.debug(getClass(), "carTypeInfo2 = " + carTypeInfo2);

	String moldPjtState = ParamUtil.getStrParameter(req.getParameter("moldPjtState"));
	String moldRank = ParamUtil.getStrParameter(req.getParameter("moldRank"));
	String planStartStartDate2 = ParamUtil.getStrParameter(req.getParameter("planStartStartDate2"));
	String planStartEndDate2 = ParamUtil.getStrParameter(req.getParameter("planStartEndDate2"));
	String planEndStartDate2 = ParamUtil.getStrParameter(req.getParameter("planEndStartDate2"));
	String planEndEndDate2 = ParamUtil.getStrParameter(req.getParameter("planEndEndDate2"));
	String tempsetMoldPm = ParamUtil.getStrParameter(req.getParameter("tempsetMoldPm"));
	String setMoldPm = ParamUtil.getStrParameter(req.getParameter("setMoldPm"));

	pjtState = moldPjtState;
	setPm = setMoldPm;
	planStartStartDate = planStartStartDate2;
	planStartEndDate = planStartEndDate2;
	planEndStartDate = planEndStartDate2;
	planEndEndDate = planEndEndDate2;
	RANK = moldRank;

	if (command == null)
	    command = "";

	if (isSelect == null)
	    isSelect = "";

	if (mode == null)
	    mode = "";

	if (command.length() > 0) {
	    map.put("command", command);
	}

	if (isSelect.length() > 0) {
	    map.put("isSelect", isSelect);
	}

	if (mode.length() > 0) {
	    map.put("mode", mode);
	}

	if (pjtNo != null && pjtNo.length() > 0) {
	    map.put("pjtNo", pjtNo);
	}
	if (pjtName != null && pjtName.length() > 0) {
	    map.put("pjtName", pjtName);
	}
	if (pjtState != null && pjtState.length() > 0) {
	    map.put("pjtState", pjtState);
	}
	if (initType != null && initType.length() > 0) {
	    map.put("pjtType", initType);
	}
	if (dType != null && dType.length() > 0) {
	    map.put("dType", dType);
	}
	if (setPm != null && setPm.length() > 0) {
	    map.put("setPm", setPm);
	}

	if (RANK != null && RANK.length() > 0) {
	    map.put("RANK", RANK);
	}
	if (PRODUCTTYPE != null && PRODUCTTYPE.length() > 0) {
	    map.put("PRODUCTTYPE", PRODUCTTYPE);
	}

	if (ASSEMBLEDTYPE != null && ASSEMBLEDTYPE.length() > 0) {
	    map.put("ASSEMBLEDTYPE", ASSEMBLEDTYPE);
	}
	if (DEVELOPENTTYPE != null && DEVELOPENTTYPE.length() > 0) {
	    map.put("DEVELOPENTTYPE", DEVELOPENTTYPE);
	}

	if (partNo != null && partNo.length() > 0) {
	    map.put("partNo", partNo);
	}
	if (partName != null && partName.length() > 0) {
	    map.put("partName", partName);
	}

	if (planStartStartDate != null && planStartStartDate.length() > 0) {
	    map.put("planStartStartDate", planStartStartDate);
	}
	if (planStartEndDate != null && planStartEndDate.length() > 0) {
	    map.put("planStartEndDate", planStartEndDate);
	}
	if (planEndStartDate != null && planEndStartDate.length() > 0) {
	    map.put("planEndStartDate", planEndStartDate);
	}
	if (planEndEndDate != null && planEndEndDate.length() > 0) {
	    map.put("planEndEndDate", planEndEndDate);
	}
	Kogger.debug(getClass(), "making = " + making);
	// 저장 끝

	try {
	    // WritableWorkbook workbook = Workbook.createWorkbook(res.getOutputStream());
	    WritableWorkbook workbook = Workbook.createWorkbook(new File(E3PSDRMHelper.downloadFileTempFolder + File.separator + fileName));
	    WritableSheet sheet = workbook.createSheet("list", 1);
	    WritableCellFormat titleformat = getCellFormat(jxl.format.Alignment.CENTRE, jxl.format.Colour.LIGHT_GREEN);

	    int row = 0;
	    int column = 0;

	    String titles[] = new String[] { "Project No", "Die No", "Part No", "Part Name", "대표차종", "금형구분", "금형분류", "금형유형", "금형Rank", "상태", "금형담당(PM)", "제작구분", "제작처", "개발시작일", "개발완료일", "개발담당부서",
		    "개발담당자" };

	    for (int i = 0; i < titles.length; i++) {
		sheet.addCell(new Label(i, row, titles[i], titleformat));
	    }

	    // MoldProject moldProject = null;

	    // QuerySpec spec = SearchPagingProjectHelper.getE3PSProjectQuerySpec(map);//new QuerySpec(MoldProject.class);

	    QueryResult rs = SearchPagingProjectHelper.find(map);// PersistenceHelper.manager.find(spec);

	    while (rs.hasMoreElements()) {
		++row;
		int columnIndex = 0;

		Object[] obj = (Object[]) rs.nextElement();
		E3PSProjectData data = new E3PSProjectData((E3PSProject) obj[0]);
		MoldProject moldProject = (MoldProject) data.e3psProject;
		MoldItemInfo itemInfo = moldProject.getMoldInfo();

		String pjtNoPrint = data.pjtNo;
		String dieNoPrint = itemInfo.getDieNo();
		String partNoPrint = itemInfo.getPartNo();
		String partNamePrint = itemInfo.getPartName();
		String carTypePrint = "";
		if (data.e3psProject.getOemType() != null) {
		    carTypePrint = data.e3psProject.getOemType().getName();
		}
		String itemTypePrint = itemInfo.getItemType(); // 금형구분

		String productTypePrint = "";
		if (itemInfo.getProductType() != null) {
		    productTypePrint = itemInfo.getProductType().getName(); // 금형분류
		}
		String moldTypePrint = ""; // 금형유형
		if (itemInfo.getMoldType() != null) {
		    moldTypePrint = itemInfo.getMoldType().getName();
		}
		String moldRankPrint = "";
		if (moldProject.getRank() != null) {
		    moldRankPrint = moldProject.getRank().getName();
		}
		String statePrint = data.state;

		String moldPmPrint = "";

		WTUser user = null;
		if (ProjectUserHelper.manager.getPM(moldProject) != null) {
		    user = ProjectUserHelper.manager.getPM(moldProject);
		    PeopleData pData = new PeopleData(user);
		    moldPmPrint = pData.name;
		}

		String makingPrint = itemInfo.getMaking();
		String outsourcingPrint = moldProject.getOutSourcing();
		String planStartDatePrint = DateUtil.getDateString(data.pjtPlanStartDate, "d");
		String planEndDatePrint = DateUtil.getDateString(data.pjtPlanEndDate, "d");
		String devUserDeptPrint = data.devDept;
		String productPmPrint = data.pjtPmName;

		sheet.addCell(new Label(columnIndex++, row, pjtNoPrint));
		sheet.addCell(new Label(columnIndex++, row, dieNoPrint));
		sheet.addCell(new Label(columnIndex++, row, partNoPrint));
		sheet.addCell(new Label(columnIndex++, row, partNamePrint));
		sheet.addCell(new Label(columnIndex++, row, carTypePrint));
		sheet.addCell(new Label(columnIndex++, row, itemTypePrint));
		sheet.addCell(new Label(columnIndex++, row, productTypePrint));
		sheet.addCell(new Label(columnIndex++, row, moldTypePrint));
		sheet.addCell(new Label(columnIndex++, row, moldRankPrint));
		sheet.addCell(new Label(columnIndex++, row, statePrint));
		sheet.addCell(new Label(columnIndex++, row, moldPmPrint));
		sheet.addCell(new Label(columnIndex++, row, makingPrint));
		sheet.addCell(new Label(columnIndex++, row, outsourcingPrint));
		sheet.addCell(new Label(columnIndex++, row, planStartDatePrint));
		sheet.addCell(new Label(columnIndex++, row, planEndDatePrint));
		sheet.addCell(new Label(columnIndex++, row, devUserDeptPrint));
		sheet.addCell(new Label(columnIndex++, row, productPmPrint));

	    }
	    workbook.write();
	    workbook.close();
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	File drmFile = new File(E3PSDRMHelper.downloadFileTempFolder + File.separator + fileName);

	try {
	    drmFile = E3PSDRMHelper.downloadExcel(drmFile, fileName, fileName.substring(0, fileName.lastIndexOf(".")), req.getRemoteAddr());

	    FileInputStream fin = new FileInputStream(drmFile);
	    int ifilesize = (int) drmFile.length();
	    byte b[] = new byte[ifilesize];

	    ServletOutputStream sos = res.getOutputStream();

	    fin.read(b);
	    sos.write(b, 0, ifilesize);

	    sos.flush();
	    sos.close();
	    fin.close();
	} catch (Exception wte) {
	    Kogger.error(getClass(), wte);
	}
    }

    private void excelDownProject(HttpServletRequest req, HttpServletResponse res) {
	QueryResult result = null;

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    // 검토 / 제품 중에
	    if (paramMap.getString("radio").equals("1")) {
		// 사업부가 자동차 - bizUnit 1 / 사업부가 전자 - bizUnit 2
		if (paramMap.getString("dType1").equals("1")) {
		    paramMap.put("bizUnit", "1");
		} else if (paramMap.getString("dType1").equals("2")) {
		    paramMap.put("bizUnit", "2");
		}
	    } else if (paramMap.getString("radio").equals("2")) {
		// 사업부가 자동차 - bizUnit 1 / 사업부가 전자 - bizUnit 2
		if (paramMap.getString("dType2").equals("1")) {
		    paramMap.put("bizUnit", "1");
		} else if (paramMap.getString("dType2").equals("2")) {
		    paramMap.put("bizUnit", "2");
		}
	    }

	    // 쿼리스펙에 전달하기 위해서 HashMap으로 전환
	    HashMap<String, String> map = new HashMap<String, String>();
	    map.put("command", paramMap.getString("command"));
	    /** Command */
	    map.put("radio", paramMap.getString("radio"));
	    /** Project 유형 */
	    map.put("initType", paramMap.getString("radio"));
	    /** Project 유형 */
	    map.put("pjtType", paramMap.getString("radio"));
	    /** Project 유형 */
	    map.put("sap", paramMap.getString("sap"));
	    /** 비용 */

	    if (map.get("initType").toString().equals("1")) {
		/** Project No */
		map.put("pjtNo", paramMap.getString("pjtNo1"));
		/** 사업부 */
		map.put("dType", paramMap.getString("dType1"));
		/** 개발담당자 */
		map.put("setPm", paramMap.getString("setPm1"));
		/** 상태 */
		map.put("pjtState", KETParamMapUtil.toString(paramMap.getStringArray("pjtState1")));
		/** 제품구분 */
		map.put("productType", KETParamMapUtil.toString(paramMap.getStringArray("productType1")));
		/** 개발구분 */
		map.put("developType", KETParamMapUtil.toString(paramMap.getStringArray("developType1")));
		/** Project Name */
		map.put("pjtName", paramMap.getString("pjtName1"));
		/** 개발담당부서 */
		map.put("rdevDeptOid", paramMap.getString("devUserDeptOid1"));
		/** 개발시작일TYpe */
		map.put("developDateType", paramMap.getString("developDateType1"));
		/** 개발 시작일 */
		map.put("planStartStartDate", paramMap.getString("planStartStartDate1"));
		/** 개발 완료일 */
		map.put("planStartEndDate", paramMap.getString("planStartEndDate1"));
		/** 조립구분 */
		map.put("assembledType", KETParamMapUtil.toString(paramMap.getStringArray("assembledType1")));
		/** 최종사용처 */
		map.put("customerEvent", KETParamMapUtil.toString(paramMap.getStringArray("customerEvent1")));
		/** 고객처 */
		map.put("subcontractor", paramMap.getString("subcontractor1"));
		/** Rank */
		map.put("rank", KETParamMapUtil.toString(paramMap.getStringArray("rank1")));
	    } else if (map.get("initType").toString().equals("2")) {
		/** Project No */
		map.put("pjtNo", paramMap.getString("pjtNo2"));
		/** 사업부 */
		map.put("dType", paramMap.getString("dType2"));
		/** 개발담당자 */
		map.put("setPm", paramMap.getString("setPm2"));
		/** 제품구분 */
		map.put("productType", KETParamMapUtil.toString(paramMap.getStringArray("productType2")));
		/** 제품구분Level2 */
		map.put("productTypeLevel2", KETParamMapUtil.toString(paramMap.getStringArray("productTypeLevel2")));
		/** 시리즈 */
		map.put("series", KETParamMapUtil.toString(paramMap.getStringArray("series2")));
		/** 방수여부 */
		map.put("sealed", KETParamMapUtil.toString(paramMap.getStringArray("sealed2")));
		/** Part No */
		map.put("partNo", paramMap.getString("partNo2"));
		/** 상태 */
		map.put("pjtState", KETParamMapUtil.toString(paramMap.getStringArray("pjtState2")));
		/** 개발담당부서 */
		map.put("pdevUserDept", paramMap.getString("devUserDept2"));
		/** 개발담당부서 */
		map.put("pdevDeptOid", paramMap.getString("devUserDeptOid2"));
		/** Project Name */
		map.put("pjtName", paramMap.getString("pjtName2"));
		/** 개발구분 */
		map.put("developType", KETParamMapUtil.toString(paramMap.getStringArray("developType2")));
		/** 관리제품군 */
		map.put("manageProductType", KETParamMapUtil.toString(paramMap.getStringArray("manageProductType")));
		/** 개발목적1 */
		map.put("developePurpose1", KETParamMapUtil.toString(paramMap.getStringArray("developePurpose1")));
		/** 개발목적2 */
		map.put("developePurpose2", KETParamMapUtil.toString(paramMap.getStringArray("developePurpose2")));

		/** 개발단계 */
		map.put("process", KETParamMapUtil.toString(paramMap.getStringArray("process")));
		/** 최종사용처 */
		map.put("customerEvent", KETParamMapUtil.toString(paramMap.getStringArray("customerEvent2")));
		/** 개발시작일TYpe */
		map.put("developDateType", paramMap.getString("developDateType2"));
		/** 개발 시작일 */
		map.put("planStartStartDate", paramMap.getString("planStartStartDate2"));
		/** 개발 완료일 */
		map.put("planStartEndDate", paramMap.getString("planStartEndDate2"));
		/** 조립구분 */
		map.put("assembledType", KETParamMapUtil.toString(paramMap.getStringArray("assembledType2")));
		/** Rank */
		map.put("rank", KETParamMapUtil.toString(paramMap.getStringArray("rank2")));
		/** 고객처 */
		map.put("subcontractor", paramMap.getString("subcontractor2"));
		/** 대표차종 */
		map.put("carTypeInfo", paramMap.getString("carTypeInfo2"));
		/** 설계구분 */
		map.put("designType", paramMap.getString("designType"));
		System.out.println("developPatternType" + KETStringUtil.join(paramMap.getStringArray("developPatternType"), "|"));
		/** 개발유형 */
		map.put("developPatternType", KETStringUtil.join(paramMap.getStringArray("developPatternType"), "|"));
		/** 생산처 */
		map.put("manufacPlace", KETStringUtil.join(paramMap.getStringArray("manufacPlace"), "|"));

		/** 파생차종 */
		map.put("oemOids", paramMap.getString("oemOids"));
	    } else if (map.get("initType").toString().equals("3")) {

		/** Die No */
		map.put("dieNo", paramMap.getString("dieNo3"));
		/** Project No */
		map.put("productpjtNo", paramMap.getString("pjtNo3"));
		/** Part No */
		map.put("partNo", paramMap.getString("partNo3"));
		/** 개발구분 */
		map.put("developType", KETParamMapUtil.toString(paramMap.getStringArray("developType3")));
		/** Part Name */
		map.put("partName", paramMap.getString("partName3"));
		/** 대표차종 */
		map.put("carTypeInfo", paramMap.getString("carTypeInfo3"));
		/** 금형구분 */
		map.put("itemType", KETParamMapUtil.toString(paramMap.getStringArray("itemType3")));
		/** 금형분류 */
		map.put("moldProductType", KETParamMapUtil.toString(paramMap.getStringArray("moldProductType3")));
		/** 금형유형 */
		map.put("moldType", KETParamMapUtil.toString(paramMap.getStringArray("moldType3")));
		/** Rank */
		map.put("rank", KETParamMapUtil.toString(paramMap.getStringArray("rank3")));
		/** 상태 */
		map.put("pjtState", KETParamMapUtil.toString(paramMap.getStringArray("pjtState3")));
		/** 금형PM */
		map.put("setPm", paramMap.getString("setMoldPm"));
		/** 제작구분 */
		map.put("making", KETParamMapUtil.toString(paramMap.getStringArray("making3")));
		/** 제작처 */
		map.put("outsourcing", paramMap.getString("outsourcing3"));
		/** 개발담당부서 */
		map.put("devDeptOid", paramMap.getString("devUserDeptOid3"));
		/** 개발시작일TYpe */
		map.put("developDateType", paramMap.getString("developDateType3"));
		/** 개발 시작일 */
		map.put("planStartStartDate", paramMap.getString("planStartStartDate3"));
		/** 개발 완료일 */
		map.put("planStartEndDate", paramMap.getString("planStartEndDate3"));
		/** 개발담당자 */
		map.put("setProductPm", paramMap.getString("setPm3"));
		/** 사업부 */
		map.put("dType", paramMap.getString("dType1"));
		/** 구매품 여부 */
		map.put("isPurchase", paramMap.getString("isPurchase"));

		/** 차종 */
		map.put("carTypeInfo", paramMap.getString("carTypeInfo3"));
	    }

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, String>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, String>>();
		session.setAttribute("projectSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("projectSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, String>>) session.getAttribute("projectSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, String>>();
		}
	    }

	    conditionList.add(map);
	    session.setAttribute("projectSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    // Excel File 위치 설정
	    String userAgent = req.getHeader("User-Agent");
	    boolean ie = userAgent.indexOf("MSIE") > -1;

	    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
	    String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	    String sFilePath = sWtHome + "/temp/download";
	    File downPath = new File(sFilePath);
	    if (!downPath.isDirectory()) {
		downPath.mkdir();
	    }
	    String sFileName = "PMSExcel_" + ff.format(new Date()) + ".xls";

	    // 파일명 한글 깨짐 방지
	    if (ie) {
		sFileName = URLEncoder.encode(sFileName, "utf-8");
	    } else {
		sFileName = new String(sFileName.getBytes("utf-8"), "iso-8859-1");
	    }

	    // Excel File Object
	    File excelFileObj = new File(sFilePath + "/" + sFileName);

	    try {

		WritableWorkbook workbook = Workbook.createWorkbook(excelFileObj);
		WritableSheet sheet = workbook.createSheet("프로젝트 목록", 1);

		WritableCellFormat titleformat = new WritableCellFormat(); // Title Cell의 스타일을 지정
		titleformat.setBorder(Border.ALL, BorderLineStyle.THIN);
		titleformat.setBackground(Colour.LIGHT_GREEN);
		titleformat.setAlignment(Alignment.CENTRE);
		titleformat.setVerticalAlignment(VerticalAlignment.CENTRE);

		WritableCellFormat cellFormatC = new WritableCellFormat(); // Cell의 스타일을 지정
		cellFormatC.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellFormatC.setAlignment(Alignment.CENTRE);

		WritableCellFormat cellFormatR = new WritableCellFormat(); // Cell의 스타일을 지정
		cellFormatR.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellFormatR.setAlignment(Alignment.RIGHT);

		String pjtType = (String) map.get("pjtType");
		if (pjtType == null) {
		    return;
		}

		// SAP Interface 할지 여부.. Default 는 안하는거로 ..
		String sap = (String) map.get("sap");

		// 검색 결과 조회
		result = SearchPagingProjectHelper.find(map, conditionList);

		System.out.println("result ## " + result.size());

		int row = 0;
		int column = 0;

		if (pjtType.equals("0") || pjtType.equals("1")) { // 검토
		    String titles[] = new String[] { "요청번호", "Project No", "Project Name", "상태", "사업부", "품목", "개발구분", "제품구분", "조립구분", "Rank", "최종사용처", "고객", "차종", "적용부위", "U/S", "영업부서", "영업담당자",
			    "개발부서", "개발담당자", "개발시작일", "계획시작일", "계획완료일", "실제시작일", "실제완료일", "진행율", "DR0일", "개발원가제출일", "개발제안서제출일", "예상가", "목표가", "수익율" };

		    for (int i = 0; i < titles.length; i++) {
			sheet.addCell(new Label(i, row, titles[i], titleformat));
			if (i == 2 || i == 10 || i == 30) {
			    sheet.setColumnView(i, 30);
			} else {
			    sheet.setColumnView(i, 15);
			}
		    }

		    while (result.hasMoreElements()) {
			++row;
			int columnIndex = 0;
			Object[] obj = (Object[]) result.nextElement();
			E3PSProjectData data = new E3PSProjectData((E3PSProject) obj[0]);
			ReviewProject project = (ReviewProject) obj[0];

			String rnumberPrint = "";
			try {
			    if (project.getDevRequest() != null) {
				rnumberPrint = project.getDevRequest().getNumber();
			    }
			} catch (Exception e) {
			    // Kogger.error(getClass(), e);
			}

			String numberPrint = data.pjtNo;
			String namePrint = data.pjtName;
			String statePrint = data.stateKorea;
			String teamType = data.teamType; // 사업부
			String infoCount = "";// 품목 - 제품정보 갯수
			String developentType = project.getDevelopentType() == null ? "" : StringUtil.checkNull(project.getDevelopentType().getName()); // 개발구분
			String productType = project.getProductType() == null ? "" : StringUtil.checkNull(project.getProductType().getName()); // 제품구분
			String assembledType = project.getAssembledType() == null ? "" : StringUtil.checkNull(project.getAssembledType().getName()); // 조립구분
			String rank = project.getRank() == null ? "" : StringUtil.checkNull(project.getRank().getName()); // Rank
			String customer = data.customer; // 최종사용처
			String customerPrint = data.dependence; // 고객
			String carInfo = ""; // 차종 A
			String areas = ""; // 적용부위 1개만
			String us = "";// us : 최소값
			String salseDpt = data.salesDept;// 영업부서
			String salesPrint = data.salesName; // 영업담당자
			String pmDpt = data.devDept; // 개발부서
			String pmPrint = data.pjtPmName;// 개발담당자
			String planStartDatePrint = DateUtil.getDateString(data.pjtPlanStartDate, "d"); // 계획 시작일 / 개발 시작일
			String planEndDatePrint = DateUtil.getDateString(data.pjtPlanEndDate, "d"); // 계획 종료일
			String pjtExecStartDate = DateUtil.getDateString(data.pjtExecStartDate, "d");// 실제 시작일
			String pjtExecEndDate = DateUtil.getDateString(data.pjtExecEndDate, "d");// 실제 종료일
			String completion = String.valueOf(data.pjtCompletion);// 진행율
			String dr0 = ""; // DR0;
			String estimateDate = DateUtil.getDateString(project.getEstimateDate(), "D");
			String proposalDate = DateUtil.getDateString(project.getProposalDate(), "D");
			String price = "";
			String cost = "";
			String rate = "";

			QuerySpec qs = new QuerySpec();
			int idxpi = qs.appendClassList(ProductInfo.class, true);
			SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id", "=", CommonUtil.getOIDLongValue(data.e3psPjtOID));
			qs.appendWhere(cs, new int[] { idxpi });
			SearchUtil.setOrderBy(qs, ProductInfo.class, idxpi, "thePersistInfo.theObjectIdentifier.id", "idA2A2", false);
			QueryResult qrpi = PersistenceHelper.manager.find(qs);
			int productCount = 0;
			int compareus1 = 0;
			int compareus2 = 10000;
			int usCount = 0;
			int priceInt = 0;
			int costInt = 0;
			int rateInt = 0;

			while (qrpi.hasMoreElements()) {
			    Object o[] = (Object[]) qrpi.nextElement();
			    ProductInfo pi = (ProductInfo) o[0];
			    // String carName = ProductHelper.getCarName(pi.getPersistInfo().getObjectIdentifier().toString());
			    // String carName2 = ProductHelper.getCarName2(pi.getPersistInfo().getObjectIdentifier().toString());
			    // 차종

			    // if (carName.length() > 0) {
			    // if (carInfo.length() == 0) {
			    // carInfo = carName;
			    // } else {
			    // carInfo = carInfo + ", " + carName;
			    // }
			    // } else {
			    // if (carInfo.length() == 0) {
			    // carInfo = carName2;
			    // } else {
			    // carInfo = carInfo + ", " + carName2;
			    // }
			    // }

			    productCount++;
			    if (pi.getAreas() != null) {
				if (pi.getAreas().length() > 0) {
				    areas = pi.getAreas();
				}
			    }
			    // U/S
			    if (pi.getUsage() != null) {
				compareus1 = Integer.parseInt(pi.getUsage());
				if (compareus1 != 0) {
				    if (compareus1 < compareus2) {
					usCount = compareus1;
				    }
				    compareus2 = 0;
				    if (compareus1 < usCount) {
					usCount = compareus1;
				    }
				}
			    }
			    // 예상가, 목표가, 수익율
			    if (pi.getPrice() != null && "sap".equals(sap)) {
				if (pi.getPrice().length() > 0) {
				    priceInt = priceInt + Integer.parseInt(pi.getPrice());
				}
			    }

			    if (StringUtil.checkNullZero(pi.getPrice()).length() > 0 && "sap".equals(sap)) {
				costInt = costInt + Integer.parseInt(StringUtil.checkNullZero(pi.getCost()));
			    } else {
				costInt = 0;
			    }

			    if (StringUtil.checkNullZero(pi.getPrice()).length() > 0 && "sap".equals(sap)) {
				rateInt = rateInt + Integer.parseInt(StringUtil.checkNullZero(pi.getRate()));
			    } else {
				rateInt = 0;
			    }

			}

			E3PSTask et = MoldProjectHelper.getTask((E3PSProject) project, "DR0");
			if (et != null) {
			    E3PSTaskData etd = new E3PSTaskData(et);
			    dr0 = DateUtil.getDateString(etd.taskPlanStartDate, "D");
			}
			long rateLong = 0;
			if (rateInt != 0) {
			    rateLong = Math.round(rateInt / productCount);
			}

			infoCount = "" + productCount;
			us = "" + usCount;
			if ("sap".equals(sap)) {
			    price = "" + priceInt;
			    cost = "" + costInt;
			    rate = "" + rateLong;
			}

			sheet.addCell(new Label(columnIndex++, row, rnumberPrint));
			sheet.addCell(new Label(columnIndex++, row, numberPrint));
			sheet.addCell(new Label(columnIndex++, row, namePrint));
			sheet.addCell(new Label(columnIndex++, row, statePrint));
			sheet.addCell(new Label(columnIndex++, row, teamType));
			sheet.addCell(new Label(columnIndex++, row, infoCount));
			sheet.addCell(new Label(columnIndex++, row, developentType));
			sheet.addCell(new Label(columnIndex++, row, productType));
			sheet.addCell(new Label(columnIndex++, row, assembledType));
			sheet.addCell(new Label(columnIndex++, row, rank));
			sheet.addCell(new Label(columnIndex++, row, customer));
			sheet.addCell(new Label(columnIndex++, row, customerPrint));
			sheet.addCell(new Label(columnIndex++, row, carInfo));
			sheet.addCell(new Label(columnIndex++, row, areas));
			sheet.addCell(new Label(columnIndex++, row, us));
			sheet.addCell(new Label(columnIndex++, row, salseDpt));
			sheet.addCell(new Label(columnIndex++, row, salesPrint));
			sheet.addCell(new Label(columnIndex++, row, pmDpt));
			sheet.addCell(new Label(columnIndex++, row, pmPrint));
			sheet.addCell(new Label(columnIndex++, row, planStartDatePrint));
			sheet.addCell(new Label(columnIndex++, row, planStartDatePrint));
			sheet.addCell(new Label(columnIndex++, row, planEndDatePrint));
			sheet.addCell(new Label(columnIndex++, row, pjtExecStartDate));
			sheet.addCell(new Label(columnIndex++, row, pjtExecEndDate));
			sheet.addCell(new Label(columnIndex++, row, completion));
			sheet.addCell(new Label(columnIndex++, row, dr0));
			sheet.addCell(new Label(columnIndex++, row, estimateDate));
			sheet.addCell(new Label(columnIndex++, row, proposalDate));
			sheet.addCell(new Label(columnIndex++, row, price));
			sheet.addCell(new Label(columnIndex++, row, cost));
			sheet.addCell(new Label(columnIndex++, row, rate));
		    }
		} else if (pjtType.equals("2") || pjtType.equals("4")) { // 제품
		    String titles[] = new String[] { "Project No", "Project Name", "상태", "Part No", "Process", "접수번호", "검토Project No", "사업부", "개발구분", "제품구분", "조립구분", "설계구분", "Rank", "최종사용처", "고객",
			    "대표차종", "파생차종", "Event", "적용부위", "U/S", "영업부서", "영업담당", "PM부서", "PM", "개발부서", "개발담당", "생산구분", "조립생산처", "Item수", "금형(P)", "금형(M)", "구매품", "개발시작일", "계획시작일", "계획완료일",
			    "실제시작일", "실제완료일", "진행율", "목표가", "예산", "실적", "금형예산", "설비예산", "지그예산", "경비예산", "금형실적", "설비실적", "지그실적", "경비실적", "DR1실적", "DR2실적", "DR3실적", "DR4실적", "DR5실적", "DR6실적", "Q_점수",
			    "Q_평가", "C_점수", "C_평가", "D_점수", "D_평가", "T_점수", "T_평가", "설변_건수", "총점_점수", "총점_평가", "예상가(원)", "판매가(원)", "수익률", "개발유형", "프로젝트 중지일", "프로젝트 취소일" };

		    for (int i = 0; i < titles.length; i++) {
			sheet.addCell(new Label(i, row, titles[i], titleformat));
			if (i == 1) {
			    sheet.setColumnView(i, 30);
			} else if (i == 2) {
			    sheet.setColumnView(i, 20);
			} else {
			    sheet.setColumnView(i, 15);
			}
		    }

		    Map<String, Object> reqSapMap = new HashMap<String, Object>();
		    Map<String, Object> resSapMap = new HashMap<String, Object>();

		    while (result.hasMoreElements()) {
			++row;
			int columnIndex = 0;
			Object[] obj = (Object[]) result.nextElement();
			E3PSProjectData data = new E3PSProjectData((E3PSProject) obj[0]);
			ProductProject project = (ProductProject) obj[0];
			String numberPrint = data.pjtNo;
			String namePrint = data.pjtName;
			String statePrint = data.stateKorea;
			String partNo = data.partNo;
			String process = project.getProcess() == null ? "" : StringUtil.checkNull(project.getProcess().getName());
			String devRNumber = ""; // 접수번호
			String devROid = ""; // 접수 OID
			try {
			    if (project.getDevRequest() != null) {
				devRNumber = project.getDevRequest().getNumber();
				devROid = CommonUtil.getOIDString(project.getDevRequest());
			    }
			} catch (Exception e) {
			    Kogger.error(getClass(), e);
			}
			String reviewpjtNo = ""; // 검토 프로젝트 No

			if (devROid != null && devROid.length() > 0) {
			    e3ps.dms.entity.KETDevelopmentRequest dr = (e3ps.dms.entity.KETDevelopmentRequest) CommonUtil.getObject(devROid);
			    if (dr.getProjectOID() != null && dr.getProjectOID().length() > 0) {
				Object reviewobj = CommonUtil.getObject(dr.getProjectOID());
				if (reviewobj instanceof E3PSProject) {
				    E3PSProject reviewProject = (E3PSProject) reviewobj;
				    reviewpjtNo = reviewProject.getPjtNo();
				}
			    }
			}
			String productType = project.getProductType() == null ? "" : StringUtil.checkNull(project.getProductType().getName()); // 제품구분
			String teamType = data.teamType; // 사업부
			String developentType = project.getDevelopentType() == null ? "" : StringUtil.checkNull(project.getDevelopentType().getName()); // 개발구분
			// String productType = project.getProductType() == null ? "" :
			// StringUtil.checkNull(project.getProductType().getName()); // 제품구분
			if (StringUtil.checkString(project.getProductTypeLevel2()) && project.getProductTypeLevel2().indexOf("KETPartClassification") > 0) {
			    KETPartClassification partClaz = (KETPartClassification) CommonUtil.getObject(project.getProductTypeLevel2());
			    if (partClaz != null) {
				productType = partClaz.getParent().getClassKrName() + "/" + partClaz.getClassKrName();
			    }
			}
			String assembledType = project.getAssembledType() == null ? "" : StringUtil.checkNull(project.getAssembledType().getName()); // 조립구분
			String designType = project.getDesignType() == null ? "" : StringUtil.checkNull(project.getDesignType().getName());// 설계
			                                                                                                                   // 구분
			String rank = project.getRank() == null ? "" : StringUtil.checkNull(project.getRank().getName()); // Rank
			String customer = data.customer; // 최종사용처
			String customerPrint = data.dependence; // 고객
			String representModel = data.representModel; // 대표차종
			String oemPrint = "";
			String eventPrint = ""; // 이벤트
			try {
			    if (project.getOemType() != null) {
				oemPrint = project.getOemType().getName();
				eventPrint = data.getNowEventName(project.getOemType());
			    }
			} catch (Exception e) {
			    Kogger.error(getClass(), e);
			}
			String areas = ""; // 적용부위 1개만
			String us = ""; // us
			String rate = ""; // rate
			String price = ""; // price
			String cost = ""; // cost

			String developPattern = ""; // 개발유형
			Map<String, Object> parameter = new HashMap<String, Object>();
			List<Map<String, Object>> numCode = null;
			KETMessageService messageService = new KETMessageService();
			parameter.put("locale", messageService.getLocale());
			parameter.put("codeType", "DEVELOPPATTERN");
			numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

			String[] DevelopedType_temp = null;
			if (project.getDevelopedType() != null) {
			    DevelopedType_temp = project.getDevelopedType().split("\\|");
			}
			if (DevelopedType_temp != null) {
			    for (int i = 0; i < DevelopedType_temp.length; i++) {
				for (int j = 0; j < numCode.size(); j++) {
				    if (DevelopedType_temp[i].equals(numCode.get(j).get("code"))) {
					if (i == DevelopedType_temp.length - 1) {
					    developPattern += numCode.get(j).get("value");
					} else {
					    developPattern += numCode.get(j).get("value") + ",";
					}
				    }
				}
			    }
			}

			QuerySpec qs = new QuerySpec();
			int idxpi = qs.appendClassList(ProductInfo.class, true);
			SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id", "=", CommonUtil.getOIDLongValue(data.e3psPjtOID));
			qs.appendWhere(cs, new int[] { idxpi });
			SearchUtil.setOrderBy(qs, ProductInfo.class, idxpi, "thePersistInfo.theObjectIdentifier.id", "idA2A2", false);
			QueryResult qrpi = PersistenceHelper.manager.find(qs);
			int compareus1 = 0;
			int compareus2 = 10000;
			int usCount = 0;
			while (qrpi.hasMoreElements()) {
			    Object o[] = (Object[]) qrpi.nextElement();
			    ProductInfo pi = (ProductInfo) o[0];
			    if (pi.getAreas() != null) {
				if (pi.getAreas().length() > 0) {
				    areas = pi.getAreas();
				}
			    }
			    // U/S
			    if (pi.getUsage() != null) {
				compareus1 = Integer.parseInt(pi.getUsage());
				if (compareus1 != 0) {
				    if (compareus1 < compareus2) {
					usCount = compareus1;
				    }
				    compareus2 = 0;
				    if (compareus1 < usCount) {
					usCount = compareus1;
				    }
				}
			    }

			    if (!StringUtil.checkNull(pi.getRate()).equals("") && "sap".equals(sap)) {
				rate += pi.getRate() + "%,";
			    }
			    if (!StringUtil.checkNull(pi.getPrice()).equals("") && "sap".equals(sap)) {
				price += pi.getPrice() + ",";
			    }
			    if (!StringUtil.checkNull(pi.getCost()).equals("") && "sap".equals(sap)) {
				cost += pi.getCost() + ",";
			    }
			}
			us = "" + usCount;

			if (!StringUtil.checkNull(rate).equals("")) {
			    rate = rate.substring(0, rate.length() - 1);
			}

			if (!StringUtil.checkNull(price).equals("")) {
			    price = price.substring(0, price.length() - 1);
			}

			if (!StringUtil.checkNull(cost).equals("")) {
			    cost = cost.substring(0, cost.length() - 1);
			}

			String oemNames = data.oemNames; // 파생차종
			String salseDpt = data.salesDept;// 영업부서
			String salesPrint = data.salesName; // 영업담당자
			String pmDpt = data.department; // pm부서
			String pmPrint = data.pjtPmName;// pm담당자
			String divisionRole = "";
			String devDpt = ""; // 개발부서
			String devPrint = ""; // 개발담당자
			// 프로젝트 CFT 제품개발 Role(자동차사업부:Team_PRODUCT01,전자사업부:Team_ELECTRON01)
			if ("자동차 사업부".equals(project.getTeamType())) {
			    divisionRole = "Team_PRODUCT01";
			} else {
			    divisionRole = "Team_ELECTRON01";
			}
			QueryResult qs_ = ProjectUserHelper.manager.getProjectRoleMember(project, divisionRole);
			if (qs_ != null && qs_.size() > 0) {
			    ProjectMemberLink memberLink = (ProjectMemberLink) qs_.nextElement();
			    PeopleData roleUser = new PeopleData(memberLink.getMember());
			    devPrint = roleUser.name;
			    devDpt = roleUser.departmentName;
			}

			String inOutType = ""; // 생산구분
			String inoutName = ""; // 조립생산처
			PartnerDao partnerDao = null;
			if (((ProductProject) project).getPartnerNo() != null && ((ProductProject) project).getPartnerNo().length() > 0) {
			    partnerDao = new PartnerDao();
			    inOutType = "외주";
			    inoutName = partnerDao.ViewPartnerName(((ProductProject) project).getPartnerNo());
			} else if (project.getAssemblyPlace() != null) {
			    inOutType = "사내";
			    inoutName = StringUtil.checkNull(project.getAssemblyPlace().getName());
			}

			String itemCountStr = ""; // Item수
			String pressCountStr = ""; // 금형(P)
			String moldCoutnStr = ""; // 금형(M)
			String gCountStr = ""; // 구매품

			int pressCount = 0;
			int moldCount = 0;
			int gCount = 0;
			int itemTotal = 0;
			QueryResult rt = ProductProjectHelper.manager.getMoldItemInfo((ProductProject) project);
			while (rt.hasMoreElements()) {
			    Object[] objItem = (Object[]) rt.nextElement();
			    MoldItemInfo miInfo = (MoldItemInfo) objItem[0];

			    if ("Press".equals(miInfo.getItemType()))
				pressCount++;
			    else if ("Mold".equals(miInfo.getItemType()))
				moldCount++;
			    else if ("구매품".equals(miInfo.getItemType()))
				gCount++;
			}
			itemTotal = pressCount + moldCount + gCount;
			itemCountStr = String.valueOf(itemTotal);
			pressCountStr = String.valueOf(pressCount);
			moldCoutnStr = String.valueOf(moldCount);
			gCountStr = String.valueOf(gCount);

			String planStartDatePrint = DateUtil.getDateString(data.pjtPlanStartDate, "d");// 개발시작일, 계획 시작일
			String planEndDatePrint = DateUtil.getDateString(data.pjtPlanEndDate, "d");// 게획 종료일
			String pjtExecStartDate = DateUtil.getDateString(data.pjtExecStartDate, "d");// 실제 시작일
			String pjtExecEndDate = DateUtil.getDateString(data.pjtExecEndDate, "d");// 실제 종료일
			String completion = String.valueOf(data.pjtCompletion);// 진행율

			String t1 = "";// 목표가 T
			String t2 = "";// 예산 T
			String t3 = "";// 실적 T
			String b1 = "";// 금형예산
			String b2 = "";// 설비예산
			String b3 = "";// 지그예산
			String b4 = "";// 경비예산

			String r1 = "";// 금형실적
			String r2 = "";// 설비실적
			String r3 = "";// 지그실적
			String r4 = "";// 경비실적

			long targetCost1 = 0;
			long targetCost2 = 0;
			long targetCost3 = 0;
			long targetCost4 = 0;
			long budget1 = 0;
			long budget2 = 0;
			long budget3 = 0;
			long budget4 = 0;
			long resultsCost1 = 0;
			long resultsCost2 = 0;
			long resultsCost3 = 0;
			long resultsCost4 = 0;
			long balanceCost1 = 0;
			long balanceCost2 = 0;
			long balanceCost3 = 0;
			long balanceCost4 = 0;

			java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
			int count = 0;
			QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);

			String amt01 = "0"; // 경비 예산 총합
			String amt07 = "0"; // 경비 집행 실적

			if ("sap".equals(sap)) {

			    reqSapMap.put("pjtNo", data.pjtNo);

			    try {
				resSapMap = BudgetExpenseInterface.getTotalExpenseByProject(reqSapMap);
				amt01 = (String) resSapMap.get("amt01");// 프로젝트 별 경비 예산 총합
				amt07 = (String) resSapMap.get("amt07");// 프로젝트 별 경비 집행 실적
			    } catch (Exception e) {
				e.printStackTrace();
			    }

			    while (rtCost.hasMoreElements()) {
				Object[] objCost = (Object[]) rtCost.nextElement();
				CostInfo costInfo = (CostInfo) objCost[0];

				long budget = 0; // 예산
				long executionCost = 0; // 초기집행가
				long editCost = 0; // 추가집행가
				long totalExpense = 0; // 총집행가
				long balanceCost = 0; // 잔액

				if (costInfo.getOrderInvest() != null) {
				    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
				    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
				    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
				    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
				    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

				    if (totalPriceObj != null)
					budget = totalPriceObj.longValue(); // 예산
				    if (initExpenseObj != null)
					executionCost = initExpenseObj.longValue(); // 초기집행가
				    if (addExpenseObj != null)
					editCost = addExpenseObj.longValue(); // 추가집행가
				    if (totalExpenseObj != null)
					totalExpense = totalExpenseObj.longValue(); // 총집행가
				    balanceCost = budget - totalExpense; // 잔액
				} else {
				    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
					budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

				    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
					executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
				    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
					executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

				    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
					editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

				    totalExpense = executionCost + editCost; // 총집행가
				    balanceCost = budget - totalExpense; // 잔액
				}

				if ("금형".equals(costInfo.getCostType())) {
				    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
					targetCost1 = targetCost1 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
				    budget1 = budget1 + budget;
				    resultsCost1 = resultsCost1 + executionCost + editCost;
				    balanceCost1 = balanceCost1 + balanceCost;
				} else if ("설비".equals(costInfo.getCostType())) {
				    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
					targetCost2 = targetCost2 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
				    budget2 = budget2 + budget;
				    resultsCost2 = resultsCost2 + executionCost + editCost;
				    balanceCost2 = balanceCost2 + balanceCost;
				} else if ("JIG".equals(costInfo.getCostType())) {
				    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
					targetCost4 = targetCost4 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
				    budget4 = budget4 + budget;
				    resultsCost4 = resultsCost4 + executionCost + editCost;
				    balanceCost4 = balanceCost4 + balanceCost;
				} else {
				    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
					targetCost3 = targetCost3 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
				    budget3 = budget3 + budget;
				    resultsCost3 = resultsCost3 + executionCost + editCost;
				    balanceCost3 = balanceCost3 + balanceCost;
				}
			    }
			    t1 = nf.format(targetCost1 + targetCost2 + targetCost3 + targetCost4);
			    t2 = nf.format(budget1 + budget2 + budget3 + budget4 + Long.parseLong(amt01));
			    t3 = nf.format(resultsCost1 + resultsCost2 + resultsCost3 + resultsCost4 + Long.parseLong(amt07));
			    b1 = nf.format(budget1);
			    b2 = nf.format(budget2);
			    b3 = nf.format(budget4);
			    b4 = nf.format(budget3);
			    r1 = nf.format(resultsCost1);
			    r2 = nf.format(resultsCost2);
			    r3 = nf.format(resultsCost4);
			    r4 = nf.format(resultsCost3);
			}
			b4 = nf.format(Long.parseLong(amt01));
			r4 = nf.format(Long.parseLong(amt07));

			int ecoCountInt = PerformanceHelper.manager.ecogetMax(project);
			String dr1 = "";
			String dr2 = "";
			String dr3 = "";
			String dr4 = "";
			String dr5 = "";
			String dr6 = "";
			E3PSTask task = null;
			for (int i = 1; i < 7; i++) {
			    task = MoldProjectHelper.getTask(project, "DR" + i);
			    if (task != null) {
				KETProjectDocument drDocument = null;
				try {
				    drDocument = ProjectOutputHelper.manager.getDRTaskOutput(task);
				} catch (Exception e) {
				    System.out.println("== Excel Export Exception !! ==");
				    e.printStackTrace();
				} finally {
				    drDocument = null;
				}

				if (drDocument != null) {
				    int drPoint = drDocument.getDRCheckPoint();
				    if (i == 1) {
					dr1 = "" + drPoint;
				    }
				    if (i == 2) {
					dr2 = "" + drPoint;
				    }
				    if (i == 3) {
					dr3 = "" + drPoint;
				    }
				    if (i == 4) {
					dr4 = "" + drPoint;
				    }
				    if (i == 5) {
					dr5 = "" + drPoint;
				    }
				    if (i == 6) {
					dr6 = "" + drPoint;
				    }
				}
			    }
			}
			String qt = "";
			String qe = "";
			String ct = "";
			String ce = "";
			String dt = "";
			String de = "";
			String tt = "";
			String te = "";
			String ecoCount = "" + ecoCountInt;
			String totalScoreStr = "";
			String totalevaluate = "";

			Performance pf = null;
			QueryResult qrtest = PerformanceHelper.manager.searchPerformance(project.getPjtNo());
			Object[] pobj = null;
			if (qrtest.hasMoreElements()) {
			    pobj = (Object[]) qrtest.nextElement();
			    pf = (Performance) pobj[0];
			}

			Weights wg = null;
			QueryResult wgQr = null;
			int totalScore = 0;
			String totalEvaluate = "";
			wgQr = PerformanceHelper.manager.searchWeights(0, true, project.getPjtNo());
			Object[] wgobj = null;
			if (wgQr.hasMoreElements()) {
			    wgobj = (Object[]) wgQr.nextElement();
			    wg = (Weights) wgobj[0];
			}
			if (wg != null) {
			    if (pf.getScoreQuality() != null && pf.getScoreCost() != null && pf.getScoreDelivery1() != null && pf.getScoreTechnical() != null && pf.getScoreQuality().length() > 0
				    && pf.getScoreCost().length() > 0 && pf.getScoreDelivery1().length() > 0 && pf.getScoreTechnical().length() > 0) {
				totalScore = Integer.parseInt(pf.getScoreQuality()) + Integer.parseInt(pf.getScoreCost()) + Integer.parseInt(pf.getScoreDelivery1())
				        + Integer.parseInt(pf.getScoreTechnical());
				totalScoreStr = "" + totalScore;
				qt = pf.getScoreQuality();
				qe = pf.getEvaluateQuality();
				ct = pf.getScoreCost();
				ce = pf.getEvaluateCost();
				dt = pf.getScoreDelivery1();
				de = pf.getEvaluateDelivery1();
				tt = pf.getScoreTechnical();
				te = pf.getEvaluateTechnical();

			    }
			    if (totalScore != 0) {
				if (wg.getTotalS() <= totalScore) {
				    totalevaluate = "S";
				} else if (wg.getTotalA() <= totalScore) {
				    totalevaluate = "A";
				} else if (wg.getTotalB() <= totalScore) {
				    totalevaluate = "B";
				} else if (wg.getTotalC() <= totalScore) {
				    totalevaluate = "C";
				} else {
				    totalevaluate = "D";
				}
			    }
			}

			// 프로젝트 취소일, 중지일 뽑기 Start - 2017.11.06 황정태 적용
			E3PSProject pjt = (E3PSProject) obj[0];

			QuerySpec hold_cancel_pjt_qs = null;
			QueryResult hold_cancel_pjt_qr = null;

			String hold_changeDate = "";
			String cancel_changeDate = "";

			long idLong = CommonUtil.getOIDLongValue(pjt.getMaster());
			Object[] hold_cancel_obj = null;
			int idx = 0;

			if (project.getLifeCycleState().toString().equals("STOPED")) {
			    hold_cancel_pjt_qs = new QuerySpec();
			    idx = hold_cancel_pjt_qs.addClassList(ProjectChangeStop.class, true);
			    hold_cancel_pjt_qs.appendWhere(new SearchCondition(ProjectChangeStop.class, "pcsMasterReference.key.id", "=", idLong), new int[] { idx });
			    SearchUtil.setOrderBy(hold_cancel_pjt_qs, ProjectChangeStop.class, idx, wt.util.WTAttributeNameIfc.CREATE_STAMP_NAME, "createStamp", false);
			    hold_cancel_pjt_qr = PersistenceHelper.manager.find(hold_cancel_pjt_qs);

			    while (hold_cancel_pjt_qr.hasMoreElements()) {
				hold_cancel_obj = (Object[]) hold_cancel_pjt_qr.nextElement();
				ProjectChangeStop ps = (ProjectChangeStop) hold_cancel_obj[0];

				if (ps.getChangeDate() != null) {
				    hold_changeDate = DateUtil.getDateString(ps.getChangeDate(), "D");
				}
			    }
			}

			if (project.getLifeCycleState().toString().equals("WITHDRAWN")) {
			    hold_cancel_pjt_qs = new QuerySpec();
			    idx = hold_cancel_pjt_qs.addClassList(ProjectCancelLink.class, true);
			    hold_cancel_pjt_qs.appendWhere(new SearchCondition(ProjectCancelLink.class, "roleBObjectRef.key.id", "=", idLong), new int[] { idx });
			    SearchUtil.setOrderBy(hold_cancel_pjt_qs, ProjectCancelLink.class, idx, wt.util.WTAttributeNameIfc.CREATE_STAMP_NAME, "createStamp", false);
			    hold_cancel_pjt_qr = PersistenceHelper.manager.find(hold_cancel_pjt_qs);

			    while (hold_cancel_pjt_qr.hasMoreElements()) {
				CancelProject cpProject = null;
				Object[] ProjectObj = null;

				ProjectObj = (Object[]) hold_cancel_pjt_qr.nextElement();
				ProjectCancelLink ps = (ProjectCancelLink) ProjectObj[0];
				cpProject = ps.getCancle();

				if (cpProject.getCancelDate() != null) {
				    cancel_changeDate = DateUtil.getDateString(cpProject.getCancelDate(), "D");
				}
			    }
			}
			// 프로젝트 취소일, 중지일 뽑기 End - 2017.11.06 황정태 적용

			sheet.addCell(new Label(columnIndex++, row, numberPrint));
			sheet.addCell(new Label(columnIndex++, row, namePrint));
			sheet.addCell(new Label(columnIndex++, row, statePrint));
			sheet.addCell(new Label(columnIndex++, row, partNo));
			sheet.addCell(new Label(columnIndex++, row, process));
			sheet.addCell(new Label(columnIndex++, row, devRNumber));
			sheet.addCell(new Label(columnIndex++, row, reviewpjtNo));
			sheet.addCell(new Label(columnIndex++, row, teamType));
			sheet.addCell(new Label(columnIndex++, row, developentType));
			sheet.addCell(new Label(columnIndex++, row, productType));
			sheet.addCell(new Label(columnIndex++, row, assembledType));
			sheet.addCell(new Label(columnIndex++, row, designType));
			sheet.addCell(new Label(columnIndex++, row, rank));
			sheet.addCell(new Label(columnIndex++, row, customer));
			sheet.addCell(new Label(columnIndex++, row, customerPrint));
			sheet.addCell(new Label(columnIndex++, row, representModel));
			sheet.addCell(new Label(columnIndex++, row, oemNames));
			sheet.addCell(new Label(columnIndex++, row, eventPrint));
			sheet.addCell(new Label(columnIndex++, row, areas));
			sheet.addCell(new Label(columnIndex++, row, us));
			sheet.addCell(new Label(columnIndex++, row, salseDpt));
			sheet.addCell(new Label(columnIndex++, row, salesPrint));
			sheet.addCell(new Label(columnIndex++, row, pmDpt));
			sheet.addCell(new Label(columnIndex++, row, pmPrint));
			sheet.addCell(new Label(columnIndex++, row, devDpt));
			sheet.addCell(new Label(columnIndex++, row, devPrint));
			sheet.addCell(new Label(columnIndex++, row, inOutType));
			sheet.addCell(new Label(columnIndex++, row, inoutName));
			sheet.addCell(new Label(columnIndex++, row, itemCountStr));
			sheet.addCell(new Label(columnIndex++, row, pressCountStr));
			sheet.addCell(new Label(columnIndex++, row, moldCoutnStr));
			sheet.addCell(new Label(columnIndex++, row, gCountStr));
			sheet.addCell(new Label(columnIndex++, row, planStartDatePrint));
			sheet.addCell(new Label(columnIndex++, row, planStartDatePrint));
			sheet.addCell(new Label(columnIndex++, row, planEndDatePrint));
			sheet.addCell(new Label(columnIndex++, row, pjtExecStartDate));
			sheet.addCell(new Label(columnIndex++, row, pjtExecEndDate));
			sheet.addCell(new Label(columnIndex++, row, completion));
			sheet.addCell(new Label(columnIndex++, row, t1));
			sheet.addCell(new Label(columnIndex++, row, t2));
			sheet.addCell(new Label(columnIndex++, row, t3));
			sheet.addCell(new Label(columnIndex++, row, b1));
			sheet.addCell(new Label(columnIndex++, row, b2));
			sheet.addCell(new Label(columnIndex++, row, b3));
			sheet.addCell(new Label(columnIndex++, row, b4));
			sheet.addCell(new Label(columnIndex++, row, r1));
			sheet.addCell(new Label(columnIndex++, row, r2));
			sheet.addCell(new Label(columnIndex++, row, r3));
			sheet.addCell(new Label(columnIndex++, row, r4));
			sheet.addCell(new Label(columnIndex++, row, dr1));
			sheet.addCell(new Label(columnIndex++, row, dr2));
			sheet.addCell(new Label(columnIndex++, row, dr3));
			sheet.addCell(new Label(columnIndex++, row, dr4));
			sheet.addCell(new Label(columnIndex++, row, dr5));
			sheet.addCell(new Label(columnIndex++, row, dr6));
			sheet.addCell(new Label(columnIndex++, row, qt));
			sheet.addCell(new Label(columnIndex++, row, qe));
			sheet.addCell(new Label(columnIndex++, row, ct));
			sheet.addCell(new Label(columnIndex++, row, ce));
			sheet.addCell(new Label(columnIndex++, row, dt));
			sheet.addCell(new Label(columnIndex++, row, de));
			sheet.addCell(new Label(columnIndex++, row, tt));
			sheet.addCell(new Label(columnIndex++, row, te));
			sheet.addCell(new Label(columnIndex++, row, ecoCount));
			sheet.addCell(new Label(columnIndex++, row, totalScoreStr));
			sheet.addCell(new Label(columnIndex++, row, totalevaluate));
			sheet.addCell(new Label(columnIndex++, row, price));
			sheet.addCell(new Label(columnIndex++, row, cost));
			sheet.addCell(new Label(columnIndex++, row, rate));
			sheet.addCell(new Label(columnIndex++, row, developPattern));
			sheet.addCell(new Label(columnIndex++, row, hold_changeDate));
			sheet.addCell(new Label(columnIndex++, row, cancel_changeDate));

		    }
		} else if (pjtType.equals("3")) { // 금형

		    if ("Y".equals(paramMap.getString("isPurchase"))) {

			String titles[] = new String[] { "Part No", "Part Name", "시작일", "완료일", "상태", "구매담당(PM)", "구매처", "제품 Project No", "제품 Project Name", "검사협정서접수 계획", "검사협정서접수 실적", "잠정단가등록 계획",
			        "잠정단가등록 실적", "P1공정점검 계획", "P1공정점검 실적", "ISIR접수 계획", "ISIR접수 실적", "외주품공정감사 계획", "외주품공정감사 실적" };

			for (int i = 0; i < titles.length; i++) {
			    sheet.addCell(new Label(i, row, titles[i], titleformat));
			}
			sheet.setColumnView(1, 30);
			sheet.setColumnView(8, 30);

			while (result.hasMoreElements()) {

			    row++;

			    Object[] obj = (Object[]) result.nextElement();

			    MoldProject mProject = (MoldProject) obj[0];
			    E3PSProjectData mData = new E3PSProjectData(mProject);

			    MoldItemInfo itemInfo = mProject.getMoldInfo();
			    ProductProject pProject = itemInfo.getProject();
			    E3PSProjectData pData = new E3PSProjectData(pProject);

			    String makingPlace = "";

			    String purchaseStr = "";
			    if (itemInfo.getPurchasePlace() != null) {
				purchaseStr = NumberCodeUtil.getNumberCodeValue("PRODUCTIONDEPT", itemInfo.getPurchasePlace().getCode());
			    }

			    if ("사내".equals(itemInfo.getProductionPlace())) {
				makingPlace = "사내 / " + purchaseStr;
			    } else if (itemInfo.getPartnerNo() != null && itemInfo.getPartnerNo().length() > 0) {
				PartnerDao pdao = new PartnerDao();
				String partnerName = pdao.ViewPartnerName(itemInfo.getPartnerNo());
				if (partnerName == null || partnerName.length() == 0) {
				    partnerName = "&nbsp;";
				}
				makingPlace = "외주 / " + partnerName;
			    }

			    int columnIndex = 0;
			    sheet.addCell(new Label(columnIndex++, row, StringUtil.checkNull(itemInfo.getPartNo()))); // Part No
			    sheet.addCell(new Label(columnIndex++, row, StringUtil.checkNull(itemInfo.getPartName()))); // Part Name
			    // sheet.addCell(new Label(columnIndex++, row, DateUtil.getDateString(data.pjtPlanStartDate, "d"))); //계획 시작일
			    // sheet.addCell(new Label(columnIndex++, row, DateUtil.getDateString(data.pjtPlanEndDate, "d"))); //계획 종료일
			    sheet.addCell(new Label(columnIndex++, row, DateUtil.getDateString(mData.pjtExecStartDate, "d"))); // 실제 시작일
			    sheet.addCell(new Label(columnIndex++, row, DateUtil.getDateString(mData.pjtExecEndDate, "d"))); // 실제 종료일
			    sheet.addCell(new Label(columnIndex++, row, mData.stateKorea)); // 상태
			    sheet.addCell(new Label(columnIndex++, row, mData.pjtPmName)); // 구매담당(PM)
			    sheet.addCell(new Label(columnIndex++, row, StringUtil.checkNull(makingPlace))); // 구매처
			    sheet.addCell(new Label(columnIndex++, row, pData.pjtNo)); // 제품 Project No
			    sheet.addCell(new Label(columnIndex++, row, pData.pjtName)); // 제품 Project Name

			    E3PSTaskData taskData = new E3PSTaskData(MoldProjectHelper.getTask(mProject, "협력사 검사협정서 접수"));
			    sheet.addCell(new Label(columnIndex++, row, DateUtil.getDateString(taskData.taskPlanEndDate, "d"))); // 협력사 검사
				                                                                                                 // 협정서 접수
				                                                                                                 // 계획
			    sheet.addCell(new Label(columnIndex++, row, DateUtil.getDateString(taskData.taskExecEndDate, "d"))); // 협력사 검사
				                                                                                                 // 협정서 접수
				                                                                                                 // 실적

			    taskData = new E3PSTaskData(MoldProjectHelper.getTask(mProject, "잠정단가 등록"));
			    sheet.addCell(new Label(columnIndex++, row, DateUtil.getDateString(taskData.taskPlanEndDate, "d"))); // 잠정단가 등록
				                                                                                                 // 계획
			    sheet.addCell(new Label(columnIndex++, row, DateUtil.getDateString(taskData.taskExecEndDate, "d"))); // 잠정단가 등록
				                                                                                                 // 실적

			    taskData = new E3PSTaskData(MoldProjectHelper.getTask(mProject, "P1 공정점검"));
			    sheet.addCell(new Label(columnIndex++, row, DateUtil.getDateString(taskData.taskPlanEndDate, "d"))); // P1 공정점검
				                                                                                                 // 계획
			    sheet.addCell(new Label(columnIndex++, row, DateUtil.getDateString(taskData.taskExecEndDate, "d"))); // P1 공정점검
				                                                                                                 // 실적

			    taskData = new E3PSTaskData(MoldProjectHelper.getTask(mProject, "외주품 ISIR 접수"));
			    sheet.addCell(new Label(columnIndex++, row, DateUtil.getDateString(taskData.taskPlanEndDate, "d"))); // 외주품 ISIR
				                                                                                                 // 접수 계획
			    sheet.addCell(new Label(columnIndex++, row, DateUtil.getDateString(taskData.taskExecEndDate, "d"))); // 외주품 ISIR
				                                                                                                 // 접수 실적

			    taskData = new E3PSTaskData(MoldProjectHelper.getTask(mProject, "외주품 공정감사"));
			    sheet.addCell(new Label(columnIndex++, row, DateUtil.getDateString(taskData.taskPlanEndDate, "d"))); // 외주품 공정감사
				                                                                                                 // 계획
			    sheet.addCell(new Label(columnIndex++, row, DateUtil.getDateString(taskData.taskExecEndDate, "d"))); // 외주품 공정감사
				                                                                                                 // 실적

			}

		    } else {
			String titles[] = new String[] { "Project No", "Die No", "Part No", "Part Name", "고객", "SOP", "대표차종", "개발구분", "금형구분", "금형분류", "금형유형", "금형Rank", "상태", "금형단계", "제작구분", "제작처",
			        "생산구분", "생산처", "Cavity", "설비Ton(형체력)", "C/T", "원재료Maker", "원재료Type", "원재료Grade", "원재료특성", "원재료특성(1)", "DR1실적", "제품도계획", "제품도실적", "금형설계일정", "금형제작계획", "금형제작실적",
			        "초도검토회의계획", "초도검토회의실적", "디버깅1차기간", "디버깅2차기간", "디버깅3차기간", "디버깅4차기간", "디버깅5차기간", "디버깅6차기간", "디버깅7차기간", "디버깅8차기간", "디버깅9차기간", "디버깅10차기간", "제품합격계획", "제품합격실적", "양산검증계획",
			        "양산검증실적", "금형이관계획", "금형이관실적", "계획시작일", "계획완료일", "실제시작일", "실제완료일", "진행율", "개발담당부서", "PM", "제품개발", "금형개발", "금형설계", "금형구매", "초류관리", "투자오더", "금형비예산", "제작비", "디버깅비",
			        "금형비용실적" };

			for (int i = 0; i < titles.length; i++) {
			    sheet.addCell(new Label(i, row, titles[i], titleformat));
			    if (i == 4) {
				sheet.setColumnView(i, 31);
			    } else if (i == 52) {
				sheet.setColumnView(i, 21);
			    } else {
				sheet.setColumnView(i, 16);
			    }
			}

			int count = 0;
			while (result.hasMoreElements()) {
			    ++row;
			    Kogger.debug(getClass(), "문제가 발생한 라인.. == " + count++); // 몇번째 줄에서 끊기는지 확인 하기 위해서 찍음...
			    int columnIndex = 0;
			    Object[] obj = (Object[]) result.nextElement();
			    E3PSProjectData data = new E3PSProjectData((E3PSProject) obj[0]);

			    MoldProject moldProject = (MoldProject) data.e3psProject;
			    MoldItemInfo itemInfo = moldProject.getMoldInfo();
			    ProductProject productProject = null;
			    E3PSProjectData productData = null;
			    if (itemInfo != null) {
				productProject = itemInfo.getProject();
				if (moldProject != null) {
				    productData = new E3PSProjectData(productProject);
				}
			    }
			    String pjtNoPrint = productData.pjtNo; // 제품 Pjt NO
			    String dieNoPrint = itemInfo.getDieNo(); // Die No
			    String partNoPrint = itemInfo.getPartNo(); // Part No
			    String partNamePrint = itemInfo.getPartName(); // Part Name
			    String customer = ""; // 고객
			    Kogger.debug(getClass(), "다이 넘버 확인.. == " + dieNoPrint);
			    if (productData.dependence != null) {
				customer = productData.dependence;
			    }
			    String sop = "";
			    QueryResult qr = OEMTypeHelper.getCustomerEvent(productProject.getOemType());
			    if (qr != null) {
				while (qr.hasMoreElements()) {
				    Object[] oo = (Object[]) qr.nextElement();
				    OEMPlan op = (OEMPlan) oo[0];
				    sop = DateUtil.getTimeFormat(op.getOemEndDate(), "yyyy-MM-dd");
				}
			    }
			    String carTypePrint = ""; // 대표차종
			    if (productData.representModel != null) {
				carTypePrint = productData.representModel;
			    }
			    String developentType = ""; // 개발구분
			    if (productProject.getDevelopentType() != null) {
				developentType = StringUtil.checkNull(productProject.getDevelopentType().getName());
			    }

			    String itemTypePrint = itemInfo.getItemType(); // 금형구분
			    String productTypePrint = "";
			    if (itemInfo.getProductType() != null) {
				productTypePrint = itemInfo.getProductType().getName(); // 금형분류
			    }
			    String moldTypePrint = ""; // 금형유형
			    if (itemInfo.getMoldType() != null) {
				moldTypePrint = itemInfo.getMoldType().getName();
			    }
			    String moldRankPrint = ""; // 금형 Rank
			    if (moldProject.getRank() != null) {
				moldRankPrint = moldProject.getRank().getName();
			    }
			    String statePrint = data.stateKorea; // 상태
			    String progressMold = data.currentTaskName; // 금형단계 (현재 진행 태스크 )
			    String making = ""; // 제작 구분
			    if (itemInfo.getMaking() != null) {
				making = itemInfo.getMaking();
			    }
			    String outSourcing = ""; // 제작처
			    NumberCode makingPlace = itemInfo.getMakingPlace();
			    if (makingPlace != null) {
				outSourcing = makingPlace.getName();
			    }
			    // if (moldProject.getOutSourcing() != null && moldProject.getOutSourcing().length() > 0) {
			    // outSourcing = moldProject.getOutSourcing();
			    // }
			    // 생산처
			    String inoutName1 = ""; // 생산구분
			    String inoutName2 = ""; // 생산처
			    PartnerDao partnerDao2 = null;

			    if (itemInfo.getPartnerNo() != null && itemInfo.getPartnerNo().length() > 0) {
				partnerDao2 = new PartnerDao();
				inoutName1 = "외주";
				inoutName2 = partnerDao2.ViewPartnerName(itemInfo.getPartnerNo());
			    } else if (itemInfo.getPurchasePlace() != null) {
				inoutName1 = "사내";
				inoutName2 = StringUtil.checkNull(itemInfo.getPurchasePlace().getName());
			    }
			    String cavity = ""; // Cavity //Cavity - Line*Pcs
			    boolean isMold = false;
			    if (itemTypePrint.equals("Mold")) {
				isMold = true;
			    }
			    double intCavity = 0;
			    if (itemInfo.getCVPitch() != null) {
				cavity = itemInfo.getCVPitch();
				if (isMold) {
				    try {
					QuerySpec specMoldItem = new QuerySpec();
					int idx = specMoldItem.addClassList(MoldItemInfo.class, true);
					SearchCondition sc = new SearchCondition(MoldItemInfo.class, "projectReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(productProject));
					specMoldItem.appendWhere(sc, new int[] { idx });
					specMoldItem.appendAnd();
					specMoldItem.appendWhere(new SearchCondition(MoldItemInfo.class, "dieNo", SearchCondition.EQUAL, itemInfo.getDieNo()), new int[] { idx });
					QueryResult rt = PersistenceHelper.manager.find(specMoldItem);
					MoldItemInfo mInfo = null;
					Object[] obj2 = null;
					Vector vt = new Vector();
					while (rt.hasMoreElements()) {
					    obj2 = (Object[]) rt.nextElement();
					    mInfo = (MoldItemInfo) obj2[0];

					    if (mInfo.getCVPitch() != null) {
						intCavity = intCavity + Double.parseDouble(mInfo.getCVPitch());
						if (intCavity != 0) {
						    vt.add(mInfo.getCVPitch());
						}
					    }
					}
					if (intCavity != 0) {
					    cavity = "";
					}
					for (int i = 0; i < vt.size(); i++) {
					    if (vt.size() == 1) {
						cavity = (String) vt.get(i);
					    } else {
						if ((vt.size() - 1) == i) {
						    cavity = cavity + (String) vt.get(i);
						} else {
						    cavity = cavity + (String) vt.get(i) + "+";
						}
					    }
					}

				    } catch (Exception e) {
					Kogger.debug(getClass(), "cavity = " + cavity);
				    }
				} else {
				    StringTokenizer st = new StringTokenizer(cavity, "*");
				    intCavity = 1;
				    while (st.hasMoreElements()) {
					String value = st.nextToken();
					int value_i = 0;
					try {
					    value_i = Integer.parseInt(value);
					} catch (Exception e) {
					    Kogger.debug(getClass(), "value = " + value);
					}
					intCavity *= value_i;
				    }
				}
			    }

			    String ctSpm = StringUtil.checkNull(itemInfo.getCTSPM());

			    // 기계설비 정보
			    MoldMachine machine = null;
			    String machineTon = ""; // 설비Ton(형체력)
			    if (moldProject.getMoldMachine() != null) {
				machine = moldProject.getMoldMachine();
				machineTon = machine.getTon().getName();
			    }

			    String maker = ""; // 원재료Maker
			    String type = ""; // 원재료Type
			    String grade = "";
			    String mType = "";
			    MoldMaterial material = null;
			    if (itemInfo.getMaterial() != null) {
				material = itemInfo.getMaterial();
				mType = material.getMaterial();
				maker = material.getMaterialMaker().getName();
				type = material.getMaterialType().getName();
				grade = material.getGrade();
			    }
			    String grade2 = ""; // 원재료Grade
			    if (mType.equals("비철")) {
				grade2 = itemInfo.getThickness() + "t" + "*" + itemInfo.getWidth() + "w";
				grade = grade + "/" + grade2;
			    }

			    String property = "";// 원재료특성
			    if (itemInfo.getProperty() != null) {
				property = itemInfo.getProperty().getName();
			    }
			    String shrinkage = ""; // 원재료특성(1)
			    if (moldProject.getShrinkage() != null && moldProject.getShrinkage().length() > 0) {
				shrinkage = moldProject.getShrinkage();
				if ("Mold".equals(itemInfo.getItemType())) {
				    shrinkage += " %";
				} else {
				    shrinkage += " mm";
				}
			    }

			    String dr1 = ""; // DR1실적
			    E3PSTask task = MoldProjectHelper.getTask(productProject, "DR1");
			    if (task != null) {
				E3PSTaskData ed = new E3PSTaskData(task);
				if (ed.taskExecEndDate != null) {
				    dr1 = DateUtil.getDateString(ed.taskExecEndDate, "D");
				}
			    }

			    // 제품 -> 금형 프로젝트의 제품도출도의 내용 가져오도록 수정...
			    E3PSTask task1 = MoldProjectHelper.getTask(moldProject, "제품도출도");
			    String pdwgPlan = ""; // 제품도계획
			    String pdwgResult = ""; // 제품도실적

			    if (task1 != null) {
				E3PSTaskData ed1 = new E3PSTaskData(task1);
				if (ed1.taskPlanEndDate != null) {
				    pdwgPlan = DateUtil.getDateString(ed1.taskPlanEndDate, "D");
				}
				if (ed1.taskExecEndDate != null) {
				    pdwgResult = DateUtil.getDateString(ed1.taskExecEndDate, "D");
				}
			    }

			    String moldDwgResult = "";
			    E3PSTask taskadd1 = null;
			    if (inoutName1.equals("사내")) {
				taskadd1 = MoldProjectHelper.getTask(moldProject, "금형설계"); // Task 이름..

				if (taskadd1 != null) {
				    Kogger.debug(getClass(), "태스크이름?? " + taskadd1.getTaskName());
				    ProjectOutput output = ProjectOutputHelper.manager.getLikeTaskOutput(taskadd1, "금형설계도"); // 산출물 이름이
					                                                                                     // 들어가야함..
					                                                                                     // 06.금혈설계도
				    if (output != null) {
					ProjectOutputData outputdata = new ProjectOutputData(output);
					if (outputdata.document != null) {
					    moldDwgResult = DateUtil.getDateString(outputdata.document.getPersistInfo().getCreateStamp(), "d");
					}
				    }
				}

			    } else if (inoutName1.equals("외주")) {
				taskadd1 = MoldProjectHelper.getTask(moldProject, "외주금형제작");
				if (taskadd1 != null) {
				    ProjectOutput output = ProjectOutputHelper.manager.getLikeTaskOutput(taskadd1, "금형제작시방서");
				    Kogger.debug(getClass(), "외주 산출물 == " + output);
				    if (output != null) {
					ProjectOutputData outputdata = new ProjectOutputData(output);
					if (outputdata.document != null) {
					    moldDwgResult = DateUtil.getDateString(outputdata.document.getPersistInfo().getCreateStamp(), "d");
					}
				    }
				}

				/*
		                 * taskadd1 = MoldProjectHelper.getTask(moldProject, "외주금형제작"); if(taskadd1 != null){ ProjectOutput output =
		                 * ProjectOutputHelper.manager.getLikeTaskOutput(task, "금형설계"); if(output != null){ RevisionControlled doc =
		                 * ProjectOutputHelper.manager.getDocMasterForOutput(output); if(doc != null){ moldDwgResult =
		                 * DateUtil.getDateString(doc.getPersistInfo().getCreateStamp(),"d"); } } }
		                 */
			    }

			    E3PSTask task2 = MoldProjectHelper.getTask(moldProject, "금형제작");
			    String moldMakePlan = ""; // 금형제작계획
			    String moldMakeResult = ""; // 금형제작실적
			    if (task2 != null) {
				E3PSTaskData ed2 = new E3PSTaskData(task2);
				if (ed2.taskPlanEndDate != null) {
				    moldMakePlan = DateUtil.getDateString(ed2.taskPlanEndDate, "D");
				}
				if (ed2.taskExecEndDate != null) {
				    moldMakeResult = DateUtil.getDateString(ed2.taskExecEndDate, "D");
				}
			    }
			    E3PSTask task3 = MoldProjectHelper.getTask(moldProject, "제품검토협의[개발품_초도]");
			    String chodoPlan = ""; // 초도검토회의계획
			    String chodoResult = ""; // 초도검토회의실적
			    if (task3 != null) {
				E3PSTaskData ed3 = new E3PSTaskData(task3);
				if (ed3.taskPlanEndDate != null) {
				    chodoPlan = DateUtil.getDateString(ed3.taskPlanEndDate, "D");
				}
				if (ed3.taskExecEndDate != null) {
				    chodoResult = DateUtil.getDateString(ed3.taskExecEndDate, "D");
				}
			    }

			    String debuging1 = ""; // 1 차 디버깅
			    String debuging2 = ""; // 2 차 디버깅
			    String debuging3 = ""; // 3 차 디버깅
			    String debuging4 = ""; // 4 차 디버깅
			    String debuging5 = ""; // 5 차 디버깅
			    String debuging6 = ""; // 6 차 디버깅
			    String debuging7 = ""; // 7 차 디버깅
			    String debuging8 = ""; // 8 차 디버깅
			    String debuging9 = ""; // 9 차 디버깅
			    String debuging10 = ""; // 10 차 디버깅

			    E3PSTask debugingTask = MoldProjectHelper.getTask(moldProject, "1 차 디버깅");
			    E3PSTaskData debugingTaskData = null;
			    if (debugingTask != null) {
				debugingTaskData = new E3PSTaskData(debugingTask);
				if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
				    debuging1 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
				}
			    }
			    debugingTask = MoldProjectHelper.getTask(moldProject, "2 차 디버깅");
			    if (debugingTask != null) {
				debugingTaskData = new E3PSTaskData(debugingTask);
				if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
				    debuging2 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
				}
			    }
			    debugingTask = MoldProjectHelper.getTask(moldProject, "3 차 디버깅");
			    if (debugingTask != null) {
				debugingTaskData = new E3PSTaskData(debugingTask);
				if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
				    debuging3 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
				}
			    }
			    debugingTask = MoldProjectHelper.getTask(moldProject, "4 차 디버깅");
			    if (debugingTask != null) {
				debugingTaskData = new E3PSTaskData(debugingTask);
				if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
				    debuging4 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
				}
			    }
			    debugingTask = MoldProjectHelper.getTask(moldProject, "5 차 디버깅");
			    if (debugingTask != null) {
				debugingTaskData = new E3PSTaskData(debugingTask);
				if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
				    debuging5 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
				}
			    }
			    debugingTask = MoldProjectHelper.getTask(moldProject, "6 차 디버깅");
			    if (debugingTask != null) {
				debugingTaskData = new E3PSTaskData(debugingTask);
				if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
				    debuging6 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
				}
			    }
			    debugingTask = MoldProjectHelper.getTask(moldProject, "7 차 디버깅");
			    if (debugingTask != null) {
				debugingTaskData = new E3PSTaskData(debugingTask);
				if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
				    debuging7 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
				}
			    }
			    debugingTask = MoldProjectHelper.getTask(moldProject, "8 차 디버깅");
			    if (debugingTask != null) {
				debugingTaskData = new E3PSTaskData(debugingTask);
				if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
				    debuging8 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
				}
			    }
			    debugingTask = MoldProjectHelper.getTask(moldProject, "9 차 디버깅");
			    if (debugingTask != null) {
				debugingTaskData = new E3PSTaskData(debugingTask);
				if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
				    debuging9 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
				}
			    }
			    debugingTask = MoldProjectHelper.getTask(moldProject, "10 차 디버깅");
			    if (debugingTask != null) {
				debugingTaskData = new E3PSTaskData(debugingTask);
				if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
				    debuging10 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
				}
			    }
			    E3PSTask sTask = MoldProjectHelper.getTask(moldProject, "제품합격");
			    String pSucessPlan = "";
			    String pSucessResult = "";
			    if (sTask != null) {
				E3PSTaskData sTaskData = new E3PSTaskData(sTask);
				if (sTaskData.taskPlanEndDate != null) {
				    pSucessPlan = DateUtil.getDateString(sTaskData.taskPlanEndDate, "D");
				}
				if (sTaskData.taskExecEndDate != null) {
				    pSucessResult = DateUtil.getDateString(sTaskData.taskExecEndDate, "D");
				}
			    }

			    E3PSTask vTask = MoldProjectHelper.getTask(moldProject, "양산검증단계");
			    String vSucessPlan = "";
			    String vSucessResult = "";
			    if (vTask != null) {
				E3PSTaskData vTaskData = new E3PSTaskData(vTask);
				if (vTaskData.taskPlanEndDate != null) {
				    vSucessPlan = DateUtil.getDateString(vTaskData.taskPlanEndDate, "D");
				}
				if (vTaskData.taskExecEndDate != null) {
				    vSucessResult = DateUtil.getDateString(vTaskData.taskExecEndDate, "D");
				}
			    }

			    E3PSTask mTask = MoldProjectHelper.getTask(moldProject, "금형양산이관");
			    String moldMgrPlan = "";
			    String moldMgrResult = "";
			    if (mTask != null) {
				E3PSTaskData mTaskData = new E3PSTaskData(mTask);
				if (mTaskData.taskPlanEndDate != null) {
				    moldMgrPlan = DateUtil.getDateString(mTaskData.taskPlanEndDate, "D");
				}
				if (mTaskData.taskExecEndDate != null) {
				    moldMgrResult = DateUtil.getDateString(mTaskData.taskExecEndDate, "D");
				}
			    }
			    String planStartDatePrint = DateUtil.getDateString(data.pjtPlanStartDate, "d");// 계획 시작일
			    String planEndDatePrint = DateUtil.getDateString(data.pjtPlanEndDate, "d"); // 계획 종료일
			    String pjtExecStartDate = DateUtil.getDateString(data.pjtExecStartDate, "d");// 실제 시작일
			    String pjtExecEndDate = DateUtil.getDateString(data.pjtExecEndDate, "d");// 실제 종료일
			    String completion = String.valueOf(data.pjtCompletion);// 진행율
			    String devUserDeptPrint = productData.department;// 개발담당부서
			    String productPmPrint = productData.pjtPmName;// 개발 PM

			    HashMap legacyMap = new HashMap();
			    ProjectMemberLink roleLink = null;
			    QueryResult memberResult = ProjectUserHelper.manager.getQueryForTeamUsers(moldProject, "ROLEMEMBER");
			    while (memberResult.hasMoreElements()) {
				roleLink = (ProjectMemberLink) memberResult.nextElement();
				legacyMap.put(roleLink.getPjtRole(), roleLink);
			    }

			    String roleName1 = ""; // 제품개발
			    String moldPmPrint = ""; // 금형개발
			    WTUser user = null;
			    if (ProjectUserHelper.manager.getPM(moldProject) != null) {
				user = ProjectUserHelper.manager.getPM(moldProject);
				PeopleData pData = new PeopleData(user);
				moldPmPrint = pData.name;
			    }
			    String roleName2 = ""; // 금형설계
			    String roleName3 = ""; // 금형구매
			    String roleName4 = ""; // 초류관리
			    Vector vecTeamStd = null;
			    Role role = null;
			    String roleName_en = null;
			    String roleName_ko = null;
			    PeopleData pdata = null;
			    TeamTemplate tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Mold Project");
			    if (tempTeam != null) {
				vecTeamStd = tempTeam.getRoles();
			    }
			    for (int i = vecTeamStd.size() - 1; i > -1; i--) {
				role = (Role) vecTeamStd.get(i);
				roleName_en = role.toString();
				roleName_ko = role.getDisplay(Locale.KOREA);
				if (legacyMap.get(roleName_en) != null) {
				    roleLink = (ProjectMemberLink) legacyMap.get(roleName_en);
				    pdata = new PeopleData(roleLink.getMember());
				    if ("Team_MOLD14".equals(roleName_en)) {// 제품개발
					roleName1 = pdata.name;
				    }
				    if ("Team_MOLD01".equals(roleName_en)) {// 금형설계
					roleName2 = pdata.name;
				    }
				    if ("Team_MOLD05".equals(roleName_en)) {// 금형구매
					roleName3 = pdata.name;
				    }
				    if ("Team_MOLD06".equals(roleName_en)) {// 금형초류관리
					roleName4 = pdata.name;
				    }
				}
			    }
			    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
			    CostInfo costInfo = itemInfo.getCostInfo();
			    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

			    String orderNumber = null; // 투자오더
			    String targetCost = "";
			    int initPrice = 0;
			    int totalPrice = 0;
			    String initPlanPrice = "-";
			    String addPlanPrice = "-";
			    String totalPlanPrice = "-";// 금형비예산

			    String initmoldTotalPrice = "-"; // 제작비
			    String debugingTotalPrice = "-"; // 디버깅비
			    String moldTotalPrice = "-"; // 금형비용실적

			    // ERP 비용 가져오기..??
			    if (costInfo != null) {
				orderNumber = costInfo.getOrderInvest();
			    }
			    boolean isTotal = false;
			    // orderNumber = "402937";
			    Vector priceV = new Vector();
			    if (orderNumber != null && orderNumber.length() > 0 && "sap".equals(sap)) {

				Hashtable hash = ProductPrice.getPrice(orderNumber);
				Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
				if (longValue != null) {
				    initPlanPrice = nf.format(longValue.longValue());
				}
				longValue = (Long) hash.get(ProductPrice.ADDPRICE);
				if (longValue != null) {
				    addPlanPrice = nf.format(longValue.longValue());
				}

				longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
				if (longValue != null) {
				    totalPlanPrice = nf.format(longValue.longValue());
				}

				priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
				int initMold = 0;
				Integer integer = 0;
				if (priceV.size() > 0) {

				    Hashtable hhh = (Hashtable) priceV.get(0);
				    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

				    if (integer != null) {
					initMold = integer.intValue();

				    }
				}

				initmoldTotalPrice = nf.format(initMold);

				int debugingtotal = 0;
				for (int i = 1; i < priceV.size(); i++) {
				    Hashtable hhh = (Hashtable) priceV.get(i);
				    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

				    if (integer != null) {
					debugingtotal += integer.intValue();
				    }
				}

				debugingTotalPrice = nf.format(debugingtotal);
				moldTotalPrice = nf.format(initMold + debugingtotal);

				if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
				    isTotal = true;
				}
			    } else {
				orderNumber = "-";
			    }

			    sheet.addCell(new Label(columnIndex++, row, pjtNoPrint));
			    sheet.addCell(new Label(columnIndex++, row, dieNoPrint));
			    sheet.addCell(new Label(columnIndex++, row, partNoPrint));
			    sheet.addCell(new Label(columnIndex++, row, partNamePrint));
			    sheet.addCell(new Label(columnIndex++, row, customer));
			    sheet.addCell(new Label(columnIndex++, row, sop));
			    sheet.addCell(new Label(columnIndex++, row, carTypePrint));
			    sheet.addCell(new Label(columnIndex++, row, developentType));
			    sheet.addCell(new Label(columnIndex++, row, itemTypePrint));
			    sheet.addCell(new Label(columnIndex++, row, productTypePrint));
			    sheet.addCell(new Label(columnIndex++, row, moldTypePrint));
			    sheet.addCell(new Label(columnIndex++, row, moldRankPrint));
			    sheet.addCell(new Label(columnIndex++, row, statePrint));
			    sheet.addCell(new Label(columnIndex++, row, progressMold));
			    sheet.addCell(new Label(columnIndex++, row, making));
			    sheet.addCell(new Label(columnIndex++, row, outSourcing));
			    sheet.addCell(new Label(columnIndex++, row, inoutName1));
			    sheet.addCell(new Label(columnIndex++, row, inoutName2));
			    sheet.addCell(new Label(columnIndex++, row, cavity));
			    sheet.addCell(new Label(columnIndex++, row, machineTon));
			    sheet.addCell(new Label(columnIndex++, row, ctSpm));
			    sheet.addCell(new Label(columnIndex++, row, maker));
			    sheet.addCell(new Label(columnIndex++, row, type));
			    sheet.addCell(new Label(columnIndex++, row, grade));
			    sheet.addCell(new Label(columnIndex++, row, property));
			    sheet.addCell(new Label(columnIndex++, row, shrinkage));
			    sheet.addCell(new Label(columnIndex++, row, dr1));
			    sheet.addCell(new Label(columnIndex++, row, pdwgPlan));
			    sheet.addCell(new Label(columnIndex++, row, pdwgResult));
			    sheet.addCell(new Label(columnIndex++, row, moldDwgResult)); // 금형설계도실적 추가 // 2011.04.28 by TaeHun
			    sheet.addCell(new Label(columnIndex++, row, moldMakePlan));
			    sheet.addCell(new Label(columnIndex++, row, moldMakeResult));
			    sheet.addCell(new Label(columnIndex++, row, chodoPlan));
			    sheet.addCell(new Label(columnIndex++, row, chodoResult));
			    sheet.addCell(new Label(columnIndex++, row, debuging1));
			    sheet.addCell(new Label(columnIndex++, row, debuging2));
			    sheet.addCell(new Label(columnIndex++, row, debuging3));
			    sheet.addCell(new Label(columnIndex++, row, debuging4));
			    sheet.addCell(new Label(columnIndex++, row, debuging5));
			    sheet.addCell(new Label(columnIndex++, row, debuging6));
			    sheet.addCell(new Label(columnIndex++, row, debuging7));
			    sheet.addCell(new Label(columnIndex++, row, debuging8));
			    sheet.addCell(new Label(columnIndex++, row, debuging9));
			    sheet.addCell(new Label(columnIndex++, row, debuging10));
			    sheet.addCell(new Label(columnIndex++, row, pSucessPlan));
			    sheet.addCell(new Label(columnIndex++, row, pSucessResult));
			    sheet.addCell(new Label(columnIndex++, row, vSucessPlan));
			    sheet.addCell(new Label(columnIndex++, row, vSucessResult));
			    sheet.addCell(new Label(columnIndex++, row, moldMgrPlan));
			    sheet.addCell(new Label(columnIndex++, row, moldMgrResult));
			    sheet.addCell(new Label(columnIndex++, row, planStartDatePrint));
			    sheet.addCell(new Label(columnIndex++, row, planEndDatePrint));
			    sheet.addCell(new Label(columnIndex++, row, pjtExecStartDate));
			    sheet.addCell(new Label(columnIndex++, row, pjtExecEndDate));
			    sheet.addCell(new Label(columnIndex++, row, completion));
			    sheet.addCell(new Label(columnIndex++, row, devUserDeptPrint));
			    sheet.addCell(new Label(columnIndex++, row, productPmPrint));
			    sheet.addCell(new Label(columnIndex++, row, roleName1));
			    sheet.addCell(new Label(columnIndex++, row, moldPmPrint));
			    sheet.addCell(new Label(columnIndex++, row, roleName2));
			    sheet.addCell(new Label(columnIndex++, row, roleName3));
			    sheet.addCell(new Label(columnIndex++, row, roleName4));
			    sheet.addCell(new Label(columnIndex++, row, orderNumber));
			    sheet.addCell(new Label(columnIndex++, row, totalPlanPrice));
			    sheet.addCell(new Label(columnIndex++, row, initmoldTotalPrice));
			    sheet.addCell(new Label(columnIndex++, row, debugingTotalPrice));
			    sheet.addCell(new Label(columnIndex++, row, moldTotalPrice));
			}
		    }
		}

		workbook.write();
		workbook.close();
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		throw e;
	    }

	    try {
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Added by MJOH, 2011-04-18
		// 엑셀 다운로드 파일 DRM 암호화 적용
		// String contentID = sFileName.substring( 0, sFileName.lastIndexOf(".") );
		// excelFileObj = E3PSDRMHelper.downloadExcel( excelFileObj, sFileName, contentID, req.getRemoteAddr() );
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////

		FileInputStream fin = new FileInputStream(excelFileObj);
		int ifilesize = (int) excelFileObj.length();
		byte b[] = new byte[ifilesize];

		res.setContentLength(ifilesize);
		res.setContentType("application/vnd.ms-excel;charset=UTF-8");
		res.setHeader("Content-Disposition", "attachment; filename=\"" + sFileName + "\";");
		res.setHeader("Content-Transfer-Encoding", "binary");

		ServletOutputStream oout = res.getOutputStream();
		fin.read(b);
		oout.write(b, 0, ifilesize);
		oout.close();
		fin.close();
	    } catch (Exception e) {
		e.printStackTrace();
		Kogger.error(getClass(), e);
		throw e;
	    } finally {
		if (excelFileObj.isFile()) {
		    excelFileObj.delete();
		}
	    }
	} catch (ServletException e) {
	    e.printStackTrace();
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    e.printStackTrace();
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    e.printStackTrace();
	    Kogger.error(getClass(), e);
	} finally {

	}
    }

    private WritableCellFormat getCellFormat(jxl.format.Alignment alignment, jxl.format.Colour color) {
	WritableCellFormat format = null;
	try {
	    format = new WritableCellFormat();
	    if (color != null)
		format.setBackground(color);

	    format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

	    if (alignment != null)
		format.setAlignment(alignment);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return format;
    }
}
