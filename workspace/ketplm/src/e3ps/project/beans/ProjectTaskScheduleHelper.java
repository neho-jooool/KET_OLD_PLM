package e3ps.project.beans;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Relation;
import net.sf.mpxj.Task;
import net.sf.mpxj.reader.ProjectReader;
import net.sf.mpxj.reader.ProjectReaderUtility;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import wt.fc.ObjectReference;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.ParamUtil;
import e3ps.project.CheckoutLink;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.ScheduleData;
import e3ps.project.TaskDependencyLink;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import e3ps.project.historyprocess.HistoryHelper;
import ext.ket.shared.log.Kogger;

/**
 * [PLM System 1차개선] 클래스명 : ProjectTaskScheduleHelper 설명 : Project Task 일정 관리 Helper 작성자 : BoLee 작성일자 : 2013. 6. 27.
 */

public class ProjectTaskScheduleHelper implements RemoteAccess {

    public static final ProjectTaskScheduleHelper manager = new ProjectTaskScheduleHelper();
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    private ProjectTaskScheduleHelper() {
    }

    /**
     * 함수명 : updateTaskSchedule 설명 : [PLM System 1차개선] Project 일정 변경 화면에서 수정한 Project Task 일정 정보 저장
     * 
     * @param paramMap
     * @return String
     * @throws Exception
     *             작성자 : BoLee 작성일자 : 2013. 6. 27.
     */
    @SuppressWarnings("rawtypes")
    public static String updateTaskSchedule(KETParamMapUtil paramMap) throws Exception {

	if (!SERVER) {

	    Class argTypes[] = new Class[] { KETParamMapUtil.class };
	    Object args[] = new Object[] { paramMap };

	    String result = (String) RemoteMethodServer.getDefault().invoke("updateTaskSchedule",
		    ProjectTaskScheduleHelper.class.getName(), null, argTypes, args);
	    return result;
	}

	String result = "";
	String oid = "";
	String gridData = "";
	String taskDef = "";
	String taskOid = "";
	String taskName = "";
	String taskNameEn = "";
	String planStartDate = "";
	String planEndDate = "";
	String planWorkTime = "";
	String personRole = "";
	String taskMasterId = "";
	String taskMemberId = "";
	String milestoneType = "";
	String optionType = "";
	String taskType = "";
	String taskTypeCategory = "";
	String scheduleType = "";
	String priorityControl = "";
	String mainScheduleCode = "";
	String drValue = "";
	String taskLevel = "";
	String parentTaskOid = "";
	String taskSeq = "";
	String taskAncestors = "";
	String taskDescendants = "";
	String debugSub = "";
	String ncha = "";
	String deleteFlag = "";
	String delFlag = "D"; // 화면에서 삭제된 Task
	String[] taskMembers = null;
	String ancestorTasks[] = null;
	String descendantTasks[] = null;
	String ancestorTaskOid = "";
	String descendantTaskOid = "";
	String tempTaskId = "";
	Object obj = null;
	Calendar tempCal = Calendar.getInstance();
	Document docX = null;
	NodeList nodeList = null;
	Element element = null;
	Element ancestorElement = null;
	Element descendantElement = null;
	Transaction trx = new Transaction();
	E3PSProject project = null;
	E3PSTask task = null;
	E3PSTask parentTask = null;
	E3PSTaskData taskData = null;
	ExtendScheduleData scheduleData = null;
	QueryResult qResults = null;
	TemplateTask dependTask = null;
	TaskDependencyLink dependLink = null;

	try {

	    oid = paramMap.getString("Oid");
	    gridData = paramMap.getString("TGData");

	    obj = CommonUtil.getObject(oid); // OID로부터 Object return

	    if (obj instanceof E3PSProject) {

		project = (E3PSProject) obj; // project 객체 return
	    }

	    gridData = gridData.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&quot;", "\"")
		    .replaceAll("&apos;", "'");

	    Kogger.debug(ProjectTaskScheduleHelper.class, "##### [Project 일정 변경] - oid :: " + oid);

	    // 1. Project 일정 TGData에서 전체 Grid Data List 가져오기
	    docX = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(gridData))); // Grid
		                                                                                                                 // Data XML
		                                                                                                                 // Parsing
	    nodeList = docX.getElementsByTagName("I"); // Head, Solid, Body 내 <I> Tag Element (한 개의 <I> Tag는 한 개의 Row를 표시함)

	    if (nodeList != null && nodeList.getLength() > 0) {

		trx.start(); // Transaction 시작

		for (int i = 0; i < nodeList.getLength(); i++) {

		    // 2. Grid Data List에서 Project 일정 Data Row 추출
		    element = (Element) nodeList.item(i);

		    taskDef = element.getAttributeNode("Def").getNodeValue(); // Task 정의(Summary / Task)

		    // [START] 3. Grid Data 중 Project Task 일정 정보만 추출 - Data 정의가 Summary, Task인 Data Row만 추출
		    if ("Summary".equals(taskDef) || "Task".equals(taskDef)) {

			// [START] 4. Project Task 정보 저장

			// 4-1. Grid Data Task 정보 가져오기

			taskOid = element.getAttributeNode("TaskOid").getNodeValue(); // Task OID
			taskName = element.getAttributeNode("TaskName").getNodeValue(); // Task명
			taskNameEn = element.getAttributeNode("TaskNameEn").getNodeValue(); // Task명(영문)
			planStartDate = element.getAttributeNode("PlanStartDate").getNodeValue(); // 계획시작일
			planEndDate = element.getAttributeNode("PlanEndDate").getNodeValue(); // 계획완료일
			planWorkTime = element.getAttributeNode("PlanWorkTime").getNodeValue(); // 계획공수
			personRole = element.getAttributeNode("PersonRole").getNodeValue(); // Role
			taskMasterId = element.getAttributeNode("TaskMasterId").getNodeValue(); // Task 책임자
			taskMemberId = element.getAttributeNode("TaskMemberId").getNodeValue(); // Task 참여자
			milestoneType = element.getAttributeNode("MilestoneType").getNodeValue(); // Milestone여부
			optionType = element.getAttributeNode("OptionType").getNodeValue(); // 필수여부
			taskType = element.getAttributeNode("TaskType").getNodeValue(); // Task종류
			taskTypeCategory = element.getAttributeNode("TaskTypeCategory").getNodeValue(); // TaskTypeCategory종류
			scheduleType = element.getAttributeNode("ScheduleType").getNodeValue(); // Schedule 구분
			priorityControl = element.getAttributeNode("PriorityControl").getNodeValue(); // 중점관리 task 구분
			mainScheduleCode = element.getAttributeNode("MainScheduleCode").getNodeValue(); // 주요일정 식별코드
			drValue = element.getAttributeNode("DRValue").getNodeValue(); // DR 값
			taskLevel = element.getAttributeNode("TaskLevel").getNodeValue(); // Task Level
			parentTaskOid = element.getAttributeNode("ParentTaskOid").getNodeValue(); // Parent Task OID
			taskSeq = element.getAttributeNode("TaskSeq").getNodeValue(); // Task Seq
			taskAncestors = element.getAttributeNode("TaskAncestors").getNodeValue(); // 선행 Task
			taskDescendants = element.getAttributeNode("TaskDescendants").getNodeValue(); // 후행 Task
			debugSub = element.getAttributeNode("DebugSub").getNodeValue(); // Debug Sub Task 여부
			ncha = element.getAttributeNode("Ncha").getNodeValue(); // Debug 차수
			deleteFlag = element.getAttributeNode("DeleteFlag").getNodeValue(); // Delete Flag

			if (StringUtil.checkString(taskOid)) { // 기존 Task 수정, 삭제

			    // 4-2. Task OID로부터 Task 객체 얻기
			    task = (E3PSTask) CommonUtil.getObject(taskOid);

			    if (!delFlag.equals(deleteFlag)) {

				// 4-3. Task 정보로부터 Task 일정 정보 얻기
				taskData = new E3PSTaskData(task); // Task 객체로부터 Task 정보(bean) 얻기
				scheduleData = taskData.schedule;
			    }
			} else if (!delFlag.equals(deleteFlag)) { // 신규 Task 생성 (삭제한 Task가 아닐 경우)

			    // 4-2. 신규 Task 객체 생성
			    task = E3PSTask.newE3PSTask();

			    // 4-3. Task 일정 객체 생성
			    scheduleData = new ExtendScheduleData();
			}

			if (delFlag.equals(deleteFlag) && StringUtil.checkString(taskOid)) {

			    // 4-4. Grid에서 삭제한 기존 Task 삭제
			    PersistenceHelper.manager.delete(task);
			} else if (!delFlag.equals(deleteFlag)) { // 삭제한 Task가 아닐 경우에만 Task 정보 수정

			    // 4-4. Task 일정 정보 Setting

			    if (StringUtil.checkString(planStartDate)) {

				planStartDate = planStartDate.trim();

				// 날짜 형식 변경 : MM/dd/yyyy -> yyyy-MM-dd
				planStartDate = planStartDate.substring(6) + "-" + planStartDate.substring(0, 2) + "-"
				        + planStartDate.substring(3, 5);

				tempCal.setTime(DateUtil.parseDateStr(new SimpleDateFormat("yyyy-MM-dd"), planStartDate));
				scheduleData.setPlanStartDate(new Timestamp(tempCal.getTime().getTime())); // 계획시작일
			    }

			    if (StringUtil.checkString(planEndDate)) {

				planEndDate = planEndDate.trim();

				// 날짜 형식 변경 : MM/dd/yyyy -> yyyy-MM-dd
				planEndDate = planEndDate.substring(6) + "-" + planEndDate.substring(0, 2) + "-"
				        + planEndDate.substring(3, 5);

				tempCal.setTime(DateUtil.parseDateStr(new SimpleDateFormat("yyyy-MM-dd"), planEndDate));
				scheduleData.setPlanEndDate(new Timestamp(tempCal.getTime().getTime())); // 계획완료일

				if (StringUtil.checkString(planStartDate)) {

				    scheduleData.setDuration(DateUtil.getDaysFromTo(planEndDate, planStartDate)); // 기간
				}
			    }

			    if (StringUtil.checkString(planWorkTime)) {

				task.setPlanWorkTime(Float.parseFloat(planWorkTime)); // 계획공수
			    }

			    // 4-5. Task 일정 정보 저장
			    scheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(scheduleData);

			    // 4-6. Task 정보 Setting

			    task.setTaskName(taskName.trim()); // Task명
			    task.setTaskNameEn(taskNameEn.trim()); // Task명(영문)
			    task.setProject(project); // Project

			    if (StringUtil.checkString(parentTaskOid)) {

				// Parent Task OID로부터 Parent Task 객체 얻기
				parentTask = (E3PSTask) CommonUtil.getObject(parentTaskOid);

				task.setParent(parentTask); // Parent Task
			    } else {

				if (Integer.parseInt(taskLevel) > 1) {

				    // Parent Task 얻기
				    parentTaskOid = ((Element) element.getParentNode()).getAttributeNode("TaskOid").getNodeValue();

				    if (StringUtil.checkString(parentTaskOid)) {

					// Parent Task OID로부터 Parent Task 객체 얻기
					parentTask = (E3PSTask) CommonUtil.getObject(parentTaskOid);

					task.setParent(parentTask); // Parent Task
				    }
				}
			    }

			    task.setTaskSeq(Integer.parseInt(taskSeq)); // Task Seq

			    task.setTaskNo(taskSeq); // Task No

			    if ("1".equals(debugSub)) {
				task.setDebug_sub(true); // Debug Sub Task 여부(true)
			    } else {
				task.setDebug_sub(false); // Debug Sub Task 여부(false)
			    }

			    if (StringUtil.checkString(ncha)) { // Debug 차수
				task.setNcha(Integer.parseInt(ncha));
			    }

			    if ("1".equals(milestoneType)) {
				task.setMileStone(true); // Milestone여부(true)
			    } else {
				task.setMileStone(false); // Milestone여부(false)
			    }

			    if ("1".equals(optionType)) {
				task.setOptionType(true); // 필수여부(true)
			    } else {
				task.setOptionType(false); // 필수여부(false)
			    }

			    task.setTaskType(taskType); // Task종류
			    if(!"COST".equals(taskType)){
				task.setCostRequest(false);
				task.setCostVersion("0");
			    }
			    task.setScheduleType(scheduleType); // Schedule 구분
			    task.setPriorityControl(priorityControl); //중점관리 task 구분
			    task.setMainScheduleCode(mainScheduleCode); //주요일정식별코드

			    if (StringUtil.checkString(drValue)) {
				task.setDrValue(drValue); // DR 값
			    }

			    task.setTaskTypeCategory(taskTypeCategory); // TaskTypeCategory종류

			    if (!StringUtil.checkString(taskOid)) {
				task.setTaskSchedule(ObjectReference.newObjectReference(scheduleData)); // Task - 일정 Link
			    }

			    // 4-7. Task 객체 저장
			    task = (E3PSTask) PersistenceHelper.manager.save(task);

			    if (!StringUtil.checkString(taskOid) && !delFlag.equals(deleteFlag)) {

				// Task 객체 저장 후 신규 생성된 Task에 대해 Grid Data Task OID 값 설정
				element.getAttributeNode("TaskOid").setNodeValue(CommonUtil.getOIDString(task));
			    }

			    // 4-8. Task Role, 책임자, 참여자 저장

			    // 4-8-1. Task Role 저장
			    task = (E3PSTask) TaskUserHelper.setTaskRole(task, personRole);

			    if (!StringUtil.checkString(personRole) && StringUtil.checkString(taskMasterId)) {

				// 4-8-2. Task 책임자 저장
				TaskUserHelper.manager.setMaster(task, (WTUser) CommonUtil.getObject(taskMasterId));
			    }

			    if (StringUtil.checkString(taskMemberId)) {

				taskMembers = taskMemberId.split(",");

				for (int x = 0; x < taskMembers.length; x++) {

				    if (StringUtil.checkString(taskMembers[x])) {

					// 4-8-3. Task 참여자 저장
					TaskUserHelper.manager.setMember(task, (WTUser) CommonUtil.getObject(taskMembers[x]));
				    }
				}
			    }

			    // 4-9. 선후행 관계 설정

			    if ("Summary".equals(taskDef)) {

				// 4-9-1. 선행 Task Link 삭제

				qResults = PersistenceHelper.manager.navigate(task, TaskDependencyLink.DEPEND_TARGET_ROLE,
				        TaskDependencyLink.class, false);

				while (qResults.hasMoreElements()) {
				    PersistenceHelper.manager.delete((TaskDependencyLink) qResults.nextElement());
				}

				// 4-9-2. 후행 Task Link 삭제

				qResults = PersistenceHelper.manager.navigate(task, TaskDependencyLink.DEPEND_SOURCE_ROLE,
				        TaskDependencyLink.class, false);

				while (qResults.hasMoreElements()) {
				    PersistenceHelper.manager.delete((TaskDependencyLink) qResults.nextElement());
				}
			    } else if ("Task".equals(taskDef)) {

				ancestorTasks = taskAncestors.split(";"); // 선행 Task List (';'로 구분)
				descendantTasks = taskDescendants.split(";"); // 후행 Task List (';'로 구분)

				// 4-9-1. 선행 Task Link 삭제

				qResults = PersistenceHelper.manager.navigate(task, TaskDependencyLink.DEPEND_TARGET_ROLE,
				        TaskDependencyLink.class, false);

				while (qResults.hasMoreElements()) {
				    PersistenceHelper.manager.delete((TaskDependencyLink) qResults.nextElement());
				}

				// 4-9-2. 선행 Task Link 생성
				ancestorTaskOid = null;
				dependTask = null;

				for (int x = 0; x < ancestorTasks.length; x++) {

				    // 선행 Task 중 신규 생성된 Task가 있을 경우 해당 Task OID 값으로 선행 Task OID 가져오기
				    if (ancestorTasks[x].startsWith("T")) {

					// Element id가 ancestorTasks[x]와 같은 Element 찾아서 Task OID 값 받기
					for (int y = 0; y < nodeList.getLength(); y++) {

					    ancestorElement = (Element) nodeList.item(y);

					    if (ancestorElement != null) {

						tempTaskId = ancestorElement.getAttributeNode("id").getNodeValue();

						if (ancestorTasks[x].replaceAll("ss", "").equals(tempTaskId)) {

						    ancestorTaskOid = ancestorElement.getAttributeNode("TaskOid").getNodeValue();
						}
					    }
					}
				    } else {
					if (StringUtil.checkString(ancestorTasks[x]))
					    ancestorTaskOid = "e3ps.project.E3PSTask:" + ancestorTasks[x].replaceAll("ss", "");
				    }

				    // 선행 Task 객체 가져오기
				    if (StringUtil.checkString(ancestorTaskOid)
					    && ancestorTaskOid.indexOf("e3ps.project.E3PSTask:") < ancestorTaskOid.length())
					dependTask = (TemplateTask) CommonUtil.getObject(ancestorTaskOid);

				    if (dependTask != null) {

					if (!TaskDependencyHelper.isAlreadyDependencyLink(task, dependTask)) {

					    dependLink = TaskDependencyLink.newTaskDependencyLink(task, dependTask);
					    PersistenceHelper.manager.save(dependLink);
					}
				    }
				}

				// 4-9-3. 후행 Task Link 생성 - 후행 Task 중 신규 생성된 Task가 있을 경우에만 생성

				for (int x = 0; x < descendantTasks.length; x++) {

				    // 후행 Task 중 신규 생성된 Task가 있을 경우 해당 Task OID 값으로 후행 Task OID 가져오기
				    if (descendantTasks[x].startsWith("T")) {

					// Element id가 descendantTasks[x]와 같은 Element 찾아서 Task OID 값 받기
					for (int y = 0; y < nodeList.getLength(); y++) {

					    descendantElement = (Element) nodeList.item(y);

					    if (descendantElement != null) {

						tempTaskId = descendantElement.getAttributeNode("id").getNodeValue();

						if (descendantTasks[x].replaceAll("ss", "").equals(tempTaskId)) {

						    descendantTaskOid = descendantElement.getAttributeNode("TaskOid").getNodeValue();

						    // 후행 Task 객체 가져오기
						    dependTask = (TemplateTask) CommonUtil.getObject(descendantTaskOid);

						    if (dependTask != null) {

							if (!TaskDependencyHelper.isAlreadyDependencyLink(dependTask, task)) {

							    dependLink = TaskDependencyLink.newTaskDependencyLink(dependTask, task);
							    PersistenceHelper.manager.save(dependLink);
							}
						    }
						}
					    }
					}
				    }
				}
			    }
			    // [END] 4. Project Task 정보 저장

			} // [END] else if ( !taskId.startsWith(delTaskFlag) )

		    } // [END] 3. Grid Data 중 Project Task 일정 정보만 추출 - Data 정의가 Summary, Task인 Data Row만 추출

		} // [END] for ( int i = 0; i < nodeList.getLength(); i++ )

		trx.commit(); // Transaction commit

		trx = null;

		// 5. Project 전체 일정 수정
		ProjectScheduleHelper.manager.post_modify_Schedule(project, false);

	    } // [END] if ( nodeList != null && nodeList.getLength() > 0 )

	    result = "0";
	} catch (SAXException e) {
	    result = "-99";
	    Kogger.error(ProjectTaskScheduleHelper.class, e);
	    throw new Exception(e);
	} catch (ParserConfigurationException e) {
	    result = "-99";
	    Kogger.error(ProjectTaskScheduleHelper.class, e);
	    throw new Exception(e);
	} catch (ServletException e) {
	    result = "-99";
	    Kogger.error(ProjectTaskScheduleHelper.class, e);
	    throw new Exception(e);
	} catch (IOException e) {
	    result = "-99";
	    Kogger.error(ProjectTaskScheduleHelper.class, e);
	    throw new Exception(e);
	} catch (Exception e) {
	    result = "-99";
	    Kogger.error(ProjectTaskScheduleHelper.class, e);
	    throw new Exception(e);
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }

	    return result;
	}
    }
    
    /**
     * 
     * 
     * @param root
     * @param isApproval
     * @return
     * @throws Exception
     * @메소드명 : projectCheckForDescendents
     * @작성자 : 황정태
     * @작성일 : 2017. 10. 18.
     * @설명 : 프로젝트의 하위 타스크 Tree를 순회하며 일정변경 범위등을 체크하는 재귀함수
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    public static String projectCheckForDescendents(TreeNode root, String isApproval) throws Exception {
	int compareResult = 0;

	DefaultProjectTreeNode node = null;
	E3PSTask childTask = null;
	E3PSTask compareTask = null;
	E3PSTaskData childData = null;
	E3PSTaskData compareData = null;
	TreeNodeData treeNodeData = null;

	Enumeration<TreeNode> children = root.children();
	if (children != null) {
	    while (children.hasMoreElements()) {
		node = (DefaultProjectTreeNode) children.nextElement();
		
		// Task 비교 결과
		compareResult = node.getCompareResult();

		treeNodeData = (TreeNodeData) node.getUserObject();

		if (treeNodeData.getData() instanceof E3PSProject) {
		    continue;
		}

		childTask = (E3PSTask) treeNodeData.getData();
		compareTask = (E3PSTask) node.getCompareTask();

		// 일정 변경 범위 체크
		if (!"T_assess".equals(isApproval)) {// 평가대상 Task(dr,gate)의 일정이 지연된 경우 결재라인 지정이 별도로 되기 때문에 1레벨 단계에서 일정변경범위 체크하는 로직과는 분기되어야함. isApproval이 "T" 는 일반 1레벨 타스크, "T_assess"는 DR, GATE, 중점관리
		    if (compareResult == DefaultProjectTreeNode.ADD && node.getLevel() == 1) { // 단계가 추가된 경우 결재 대상 (1레벨)

			isApproval = "T";
		    } else if (compareResult == DefaultProjectTreeNode.MODIFY) { // 단계가 수정된 경우 단계 일정이 늦춰진 경우 결재 대상

			childData = new E3PSTaskData(childTask);
			compareData = new E3PSTaskData(compareTask);

			if (node.getLevel() == 1) {//1레벨일 때(일반 TASK 일정지연 체크)
			    if (childData != null && compareData != null
				    && (childData.taskPlanEndDate).compareTo(compareData.taskPlanEndDate) > 0) {

				isApproval = "T";
				if ("GATE".equalsIgnoreCase(childTask.getTaskType()) || "DR".equalsIgnoreCase(childTask.getTaskType()) || "Y".equals(childTask.getPriorityControl())) {//gate, dr, 중점관리 일정지연 체크
				    isApproval = "T_assess";
				}
			    }
			} else {
			    if ("GATE".equalsIgnoreCase(childTask.getTaskType()) || "DR".equalsIgnoreCase(childTask.getTaskType()) || "Y".equals(childTask.getPriorityControl())) {//gate, dr, 중점관리 일정지연 체크
				if (childData != null && compareData != null
				        && (childData.taskPlanEndDate).compareTo(compareData.taskPlanEndDate) > 0) {

				    isApproval = "T_assess";
				}
			    }
			}
		    }
		}

		//System.out.println("isApproval : " + isApproval + " childTask =" + childTask.getTaskName()+"childTask.getTaskType() = "+childTask.getTaskType());
		isApproval = projectCheckForDescendents(node, isApproval);
	    }

	}
	return isApproval;
    }

    /**
     * 함수명 : isProjectScheduleForApproval 설명 : [PLM System 1차개선] Project 일정 변경 결재 대상 여부 체크
     * 
     * @param project
     * @return String
     * @throws Exception
     *             작성자 : BoLee 작성일자 : 2013. 7. 15.
     */
    @SuppressWarnings("rawtypes")
    public static String isProjectScheduleForApproval(E3PSProject project) throws Exception {

	int compareResult = 0;
	String isApproval = "F";
	QueryResult qResult = null;
	E3PSProject original = null;
	DefaultProjectTreeNode root = null;
	DefaultProjectTreeNode node = null;
	E3PSTask childTask = null;
	E3PSTask compareTask = null;
	E3PSTaskData childData = null;
	E3PSTaskData compareData = null;
	TreeNodeData treeNodeData = null;

	if (project != null) {

	    // 일정 변경 범위 체크 대상 객체가 Working Copy가 아닐 경우 Exception 발생
	    if (!project.isWorkingCopy()) {
		throw new Exception("Working Copy가 아니어서 일정 변경 범위를 체크할 수 없습니다.");
	    }

	    // 원본 객체 검색
	    qResult = PersistenceHelper.manager.navigate(project, CheckoutLink.ORIGINAL_COPY_ROLE, CheckoutLink.class);

	    if (qResult.hasMoreElements()) {
		original = (E3PSProject) qResult.nextElement();
	    }

	    if (original == null) {
		throw new Exception("원본 객체가 존재하지 않습니다.");
	    } else {

		// 원본 Project 객체와 Working Copy 비교
		root = (DefaultProjectTreeNode) HistoryHelper.getCompareProject(project, original, new HashMap());
		/*
		 *  기존 1레벨만 체크하던 로직 주석처리하고 모든 하위 타스크를 점검하는 로직으로 변경(projectCheckForDescendents 함수생성) 2017.10.18 by 황정태
		 *  연구기획센터장(유승우) 지시로 dr,gate 일정지연을 체크하기 위함
		 */
		
		
		isApproval = projectCheckForDescendents(root, isApproval);

		/*
	         * // 단계(1레벨) 일정 변경 범위 체크 for (int i = 0; i < root.getChildCount(); i++) {
	         * 
	         * node = (DefaultProjectTreeNode) root.getChildAt(i);
	         * 
	         * // Task 비교 결과 compareResult = node.getCompareResult();
	         * 
	         * treeNodeData = (TreeNodeData) node.getUserObject();
	         * 
	         * if (treeNodeData.getData() instanceof E3PSProject) { continue; }
	         * 
	         * childTask = (E3PSTask) treeNodeData.getData(); compareTask = (E3PSTask) node.getCompareTask();
	         * 
	         * // 일정 변경 범위 체크
	         * 
	         * if (compareResult == DefaultProjectTreeNode.ADD) { // 단계가 추가된 경우 결재 대상
	         * 
	         * isApproval = "T"; } else if (compareResult == DefaultProjectTreeNode.MODIFY) { // 단계가 수정된 경우 단계 일정이 늦춰진 경우 결재 대상
	         * 
	         * childData = new E3PSTaskData(childTask); compareData = new E3PSTaskData(compareTask);
	         * 
	         * if (childData != null && compareData != null && (childData.taskPlanEndDate).compareTo(compareData.taskPlanEndDate) > 0) {
	         * 
	         * isApproval = "T"; } } }
	         */

	    }
	} else {
	    throw new Exception("일정 변경 대상 객체가 존재하지 않습니다.");
	}
	return isApproval;

    }

    /**
     * 함수명 : updateWBSTaskSchedule 설명 : [PLM System 1차개선] WBS 일정 변경 화면에서 수정한 WBS Task 일정 정보 저장
     * 
     * @param paramMap
     * @return String
     * @throws Exception
     *             작성자 : BoLee 작성일자 : 2013. 7. 16.
     */
    @SuppressWarnings("rawtypes")
    public static String updateWBSTaskSchedule(KETParamMapUtil paramMap) throws Exception {

	if (!SERVER) {

	    Class argTypes[] = new Class[] { KETParamMapUtil.class };
	    Object args[] = new Object[] { paramMap };

	    String result = (String) RemoteMethodServer.getDefault().invoke("updateWBSTaskSchedule",
		    ProjectTaskScheduleHelper.class.getName(), null, argTypes, args);
	    return result;
	}

	String result = "";
	String oid = "";
	String gridData = "";
	String taskDef = "";
	String taskOid = "";
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
	String taskLevel = "";
	String parentTaskOid = "";
	String taskAncestors = "";
	String taskDescendants = "";
	String deleteFlag = "";
	String delFlag = "D"; // 화면에서 삭제된 Task
	String ancestorTasks[] = null;
	String descendantTasks[] = null;
	String ancestorTaskOid = "";
	String descendantTaskOid = "";
	String tempTaskId = "";
	int maxSeq = 0;
	Object obj = null;
	Calendar tempCal = Calendar.getInstance();
	Document docX = null;
	NodeList nodeList = null;
	Element element = null;
	Element ancestorElement = null;
	Element descendantElement = null;
	Transaction trx = new Transaction();
	TemplateProject project = null;
	TemplateTask task = null;
	TemplateTask parentTask = null;
	TemplateTaskData taskData = null;
	ScheduleData scheduleData = null;
	QueryResult qResults = null;
	TemplateTask dependTask = null;
	TaskDependencyLink dependLink = null;

	/* 2014.07.17 컬럼추가 */
	String taskTypeCategory = "";
	String new_ = "";
	String modify = "";
	String planWorkTime = "";
	String common = "";
	String mdraw = "";
	String hw = "";
	String sw = "";
	String m = "";
	String p = "";
	String buy = "";
	String system = "";

	try {

	    oid = paramMap.getString("Oid");
	    gridData = paramMap.getString("TGData");

	    obj = CommonUtil.getObject(oid); // OID로부터 Object return

	    if (obj instanceof TemplateProject) {

		project = (TemplateProject) obj; // WBS 객체 return
	    }

	    gridData = gridData.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&quot;", "\"")
		    .replaceAll("&apos;", "'");

	    // 1. Project 일정 TGData에서 전체 Grid Data List 가져오기
	    docX = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(gridData))); // Grid
		                                                                                                                 // Data XML
		                                                                                                                 // Parsing
	    nodeList = docX.getElementsByTagName("I"); // Head, Solid, Body 내 <I> Tag Element (한 개의 <I> Tag는 한 개의 Row를 표시함)

	    if (nodeList != null && nodeList.getLength() > 0) {

		trx.start(); // Transaction 시작

		for (int i = 0; i < nodeList.getLength(); i++) {

		    // 2. Grid Data List에서 Project 일정 Data Row 추출
		    element = (Element) nodeList.item(i);

		    taskDef = element.getAttributeNode("Def").getNodeValue(); // Task 정의(Summary / Task)

		    // [START] 3. Grid Data 중 Project Task 일정 정보만 추출 - Data 정의가 Summary, Task인 Data Row만 추출
		    if ("Root".equals(taskDef)) {

			scheduleData = (ScheduleData) project.getPjtSchedule().getObject();

			planStartDate = element.getAttributeNode("PlanStartDate").getNodeValue(); // 계획시작일
			planEndDate = element.getAttributeNode("PlanEndDate").getNodeValue(); // 계획완료일

			if (StringUtil.checkString(planStartDate)) {

			    planStartDate = planStartDate.trim();

			    // 날짜 형식 변경 : MM/dd/yyyy -> yyyy-MM-dd
			    planStartDate = planStartDate.substring(6) + "-" + planStartDate.substring(0, 2) + "-"
				    + planStartDate.substring(3, 5);

			    tempCal.setTime(DateUtil.parseDateStr(new SimpleDateFormat("yyyy-MM-dd"), planStartDate));

			    scheduleData.setPlanStartDate(new Timestamp(tempCal.getTime().getTime())); // 계획시작일

			}

			if (StringUtil.checkString(planEndDate)) {

			    planEndDate = planEndDate.trim();

			    // 날짜 형식 변경 : MM/dd/yyyy -> yyyy-MM-dd
			    planEndDate = planEndDate.substring(6) + "-" + planEndDate.substring(0, 2) + "-" + planEndDate.substring(3, 5);

			    tempCal.setTime(DateUtil.parseDateStr(new SimpleDateFormat("yyyy-MM-dd"), planEndDate));
			    scheduleData.setPlanEndDate(new Timestamp(tempCal.getTime().getTime())); // 계획완료일

			    if (StringUtil.checkString(planStartDate)) {

				scheduleData.setDuration(DateUtil.getDaysFromTo(planEndDate, planStartDate)); // 기간
			    }
			}

			scheduleData = (ScheduleData) PersistenceHelper.manager.save(scheduleData);
		    }

		    if ("Summary".equals(taskDef) || "Task".equals(taskDef)) {

			// [START] 4. Project Task 정보 저장

			// 4-1. Grid Data Task 정보 가져오기

			taskOid = element.getAttributeNode("TaskOid").getNodeValue(); // Task OID
			taskName = element.getAttributeNode("TaskName").getNodeValue(); // Task명
			taskNameEn = element.getAttributeNode("TaskNameEn").getNodeValue(); // Task명(영문)
			planStartDate = element.getAttributeNode("PlanStartDate").getNodeValue(); // 계획시작일
			planEndDate = element.getAttributeNode("PlanEndDate").getNodeValue(); // 계획완료일
			personRole = element.getAttributeNode("PersonRole").getNodeValue(); // Role
			milestoneType = element.getAttributeNode("MilestoneType").getNodeValue(); // Milestone여부
			optionType = element.getAttributeNode("OptionType").getNodeValue(); // 필수여부
			taskType = element.getAttributeNode("TaskType").getNodeValue(); // Task종류
			scheduleType = element.getAttributeNode("ScheduleType").getNodeValue(); // Schedule 구분
			priorityControl = element.getAttributeNode("PriorityControl").getNodeValue(); // 중점관리 TASK 구분
			mainScheduleCode = element.getAttributeNode("MainScheduleCode").getNodeValue(); // 주요일정식별코드
			drValue = element.getAttributeNode("DRValue").getNodeValue(); // DR 값
			drValueCondition = element.getAttributeNode("DRValueCondition").getNodeValue(); // DR 조건부승인값
			taskLevel = element.getAttributeNode("TaskLevel").getNodeValue(); // Task Level
			parentTaskOid = element.getAttributeNode("ParentTaskOid").getNodeValue(); // Parent Task OID
			taskAncestors = element.getAttributeNode("TaskAncestors").getNodeValue(); // 선행 Task
			taskDescendants = element.getAttributeNode("TaskDescendants").getNodeValue(); // 후행 Task
			deleteFlag = element.getAttributeNode("DeleteFlag").getNodeValue(); // Delete Flag
			planWorkTime = element.getAttributeNode("PlanWorkTime").getNodeValue();// 계획작업시간
			new_ = element.getAttributeNode("New").getNodeValue();// 구분-신규
			modify = element.getAttributeNode("Modify").getNodeValue();// 구분-Modify
			common = element.getAttributeNode("Common").getNodeValue();// Category-공통
			mdraw = element.getAttributeNode("Mdraw").getNodeValue();// Category-기구
			hw = element.getAttributeNode("HW").getNodeValue();// Category-HW
			sw = element.getAttributeNode("SW").getNodeValue();// Category-SW
			m = element.getAttributeNode("M").getNodeValue();// Category-M
			p = element.getAttributeNode("P").getNodeValue();// Category-P
			buy = element.getAttributeNode("Buy").getNodeValue();// Category-구매
			system = element.getAttributeNode("System").getNodeValue();// Category-설비
			taskTypeCategory = element.getAttribute("TaskTypeCategory");

			if (StringUtil.checkString(taskOid)) { // 기존 Task 수정, 삭제

			    // 4-2. Task OID로부터 Task 객체 얻기
			    task = (TemplateTask) CommonUtil.getObject(taskOid);

			    if (!delFlag.equals(deleteFlag)) {

				// 4-3. Task 정보로부터 Task 일정 정보 얻기
				taskData = new TemplateTaskData(task); // Task 객체로부터 Task 정보(bean) 얻기
				scheduleData = taskData.schedule;
			    }
			} else if (!delFlag.equals(deleteFlag)) { // 신규 Task 생성 (삭제한 Task가 아닐 경우)

			    // 4-2. 신규 Task 객체 생성
			    task = TemplateTask.newTemplateTask();

			    // 4-3. Task 일정 객체 생성
			    scheduleData = new ScheduleData();
			}

			if (delFlag.equals(deleteFlag) && StringUtil.checkString(taskOid)) {

			    // 4-4. Grid에서 삭제한 기존 Task 삭제
			    PersistenceHelper.manager.delete(task);
			} else if (!delFlag.equals(deleteFlag)) { // 삭제한 Task가 아닐 경우에만 Task 정보 수정

			    // 4-4. Task 일정 정보 Setting

			    if (StringUtil.checkString(planStartDate)) {

				planStartDate = planStartDate.trim();

				// 날짜 형식 변경 : MM/dd/yyyy -> yyyy-MM-dd
				planStartDate = planStartDate.substring(6) + "-" + planStartDate.substring(0, 2) + "-"
				        + planStartDate.substring(3, 5);

				tempCal.setTime(DateUtil.parseDateStr(new SimpleDateFormat("yyyy-MM-dd"), planStartDate));
				scheduleData.setPlanStartDate(new Timestamp(tempCal.getTime().getTime())); // 계획시작일
			    }

			    if (StringUtil.checkString(planEndDate)) {

				planEndDate = planEndDate.trim();

				// 날짜 형식 변경 : MM/dd/yyyy -> yyyy-MM-dd
				planEndDate = planEndDate.substring(6) + "-" + planEndDate.substring(0, 2) + "-"
				        + planEndDate.substring(3, 5);

				tempCal.setTime(DateUtil.parseDateStr(new SimpleDateFormat("yyyy-MM-dd"), planEndDate));
				scheduleData.setPlanEndDate(new Timestamp(tempCal.getTime().getTime())); // 계획완료일

				if (StringUtil.checkString(planStartDate)) {

				    scheduleData.setDuration(DateUtil.getDaysFromTo(planEndDate, planStartDate)); // 기간
				}
			    }

			    // 4-5. Task 일정 정보 저장
			    scheduleData = (ScheduleData) PersistenceHelper.manager.save(scheduleData);

			    // 4-6. Task 정보 Setting

			    task.setTaskName(taskName.trim()); // Task명
			    task.setTaskNameEn(taskNameEn.trim()); // Task명(영문)
			    task.setProject(project); // Project

			    if (StringUtil.checkString(parentTaskOid)) {

				// Parent Task OID로부터 Parent Task 객체 얻기
				parentTask = (TemplateTask) CommonUtil.getObject(parentTaskOid);

				task.setParent(parentTask); // Parent Task
			    } else {

				if (Integer.parseInt(taskLevel) > 1) {

				    // Parent Task 얻기
				    parentTaskOid = ((Element) element.getParentNode()).getAttributeNode("TaskOid").getNodeValue();

				    if (StringUtil.checkString(parentTaskOid)) {

					// Parent Task OID로부터 Parent Task 객체 얻기
					parentTask = (TemplateTask) CommonUtil.getObject(parentTaskOid);

					task.setParent(parentTask); // Parent Task
				    }
				}
			    }

			    maxSeq = ProjectTaskHelper.getMaxSeq((TemplateTask) task.getParent(), task.getProject());

			    task.setTaskSeq(maxSeq); // Task Seq
			    task.setTaskNo(String.valueOf(maxSeq)); // Task No

			    if ("1".equals(milestoneType)) {
				task.setMileStone(true); // Milestone여부(true)
			    } else {
				task.setMileStone(false); // Milestone여부(false)
			    }

			    if ("1".equals(optionType)) {
				task.setOptionType(true); // 필수여부(true)
			    } else {
				task.setOptionType(false); // 필수여부(false)
			    }

			    // 계획작업시간
			    if (planWorkTime == "") {
				planWorkTime = "0";
			    }
			    task.setPlanWorkTime(Integer.parseInt(planWorkTime));

			    // 구분- 신규
			    if ("1".equals(new_)) {
				task.setNewType(1);
			    } else if ("0".equals(new_)) {
				task.setNewType(0);
			    }
			    // 구분- modify
			    if ("1".equals(modify)) {
				task.setModifyType(1);
			    } else if ("0".equals(modify)) {
				task.setModifyType(0);
			    }

			    // Category- 공통
			    if ("2".equals(common)) {
				task.setCommon(2);
			    } else if ("1".equals(common)) {
				task.setCommon(1);
			    } else {
				task.setCommon(0);
			    }
			    // Category- 기구
			    if ("2".equals(mdraw)) {
				task.setMdraw(2);
			    } else if ("1".equals(mdraw)) {
				task.setMdraw(1);
			    } else {
				task.setMdraw(0);
			    }
			    // Category- HW
			    if ("2".equals(hw)) {
				task.setHw(2);
			    } else if ("1".equals(hw)) {
				task.setHw(1);
			    } else {
				task.setHw(0);
			    }
			    // Category- SW
			    if ("2".equals(sw)) {
				task.setSw(2);
			    } else if ("1".equals(sw)) {
				task.setSw(1);
			    } else {
				task.setSw(0);
			    }
			    // Category- M
			    if ("2".equals(m)) {
				task.setM(2);
			    } else if ("1".equals(m)) {
				task.setM(1);
			    } else {
				task.setM(0);
			    }
			    // Category- P
			    if ("2".equals(p)) {
				task.setP(2);
			    } else if ("1".equals(p)) {
				task.setP(1);
			    } else {
				task.setP(0);
			    }
			    // Category- 구매
			    if ("2".equals(buy)) {
				task.setBuy(2);
			    } else if ("1".equals(buy)) {
				task.setBuy(1);
			    } else {
				task.setBuy(0);
			    }
			    // Category- 설비
			    if ("2".equals(system)) {
				task.setSystem(2);
			    } else if ("1".equals(system)) {
				task.setSystem(1);
			    } else {
				task.setSystem(0);
			    }

			    task.setTaskType(taskType); // Task종류

			    if (taskTypeCategory.equals("")) {
				task.setTaskTypeCategory("-");
			    } else {
				task.setTaskTypeCategory(taskTypeCategory);
			    }
			    task.setScheduleType(scheduleType); // Schedule 구분
			    task.setPriorityControl(priorityControl); // 중점관리 task 구분
			    task.setMainScheduleCode(mainScheduleCode); //주요일정식별코드

			    if ("DR".equals(taskType) && StringUtil.checkString(drValue)) {
				task.setDrValue(drValue); // DR 값
			    }
			    if ("DR".equals(taskType) && StringUtil.checkString(drValueCondition)) {
				task.setDrValueCondition(drValueCondition); // DR 조건부 승인 값
			    }

			    if (!StringUtil.checkString(taskOid)) {
				task.setTaskSchedule(ObjectReference.newObjectReference(scheduleData)); // Task - 일정 Link
			    }

			    // 4-7. Task 객체 저장
			    task = (TemplateTask) PersistenceHelper.manager.save(task);

			    if (!StringUtil.checkString(taskOid) && !delFlag.equals(deleteFlag)) {

				// Task 객체 저장 후 신규 생성된 Task에 대해 Grid Data Task OID 값 설정
				element.getAttributeNode("TaskOid").setNodeValue(CommonUtil.getOIDString(task));
			    }

			    // 4-8. Task Role 저장
			    task = (TemplateTask) TaskUserHelper.setTaskRole(task, personRole);

			    // 4-9. 선후행 관계 설정

			    if ("Summary".equals(taskDef)) {

				// 4-9-1. 선행 Task Link 삭제

				qResults = PersistenceHelper.manager.navigate(task, TaskDependencyLink.DEPEND_TARGET_ROLE,
				        TaskDependencyLink.class, false);

				while (qResults.hasMoreElements()) {
				    PersistenceHelper.manager.delete((TaskDependencyLink) qResults.nextElement());
				}

				// 4-9-2. 후행 Task Link 삭제

				qResults = PersistenceHelper.manager.navigate(task, TaskDependencyLink.DEPEND_SOURCE_ROLE,
				        TaskDependencyLink.class, false);

				while (qResults.hasMoreElements()) {
				    PersistenceHelper.manager.delete((TaskDependencyLink) qResults.nextElement());
				}
			    } else if ("Task".equals(taskDef)) {

				ancestorTasks = taskAncestors.split(";"); // 선행 Task List (';'로 구분)
				descendantTasks = taskDescendants.split(";"); // 후행 Task List (';'로 구분)

				// 4-9-1. 선행 Task Link 삭제

				qResults = PersistenceHelper.manager.navigate(task, TaskDependencyLink.DEPEND_TARGET_ROLE,
				        TaskDependencyLink.class, false);

				while (qResults.hasMoreElements()) {
				    PersistenceHelper.manager.delete((TaskDependencyLink) qResults.nextElement());
				}

				// 4-9-2. 선행 Task Link 생성

				for (int x = 0; x < ancestorTasks.length; x++) {
				    
				    if(StringUtils.isEmpty(ancestorTasks[x])){
					continue;
				    }
				    
				    System.out.println(ancestorTasks[x]);

				    // 선행 Task 중 신규 생성된 Task가 있을 경우 해당 Task OID 값으로 선행 Task OID 가져오기
				    if (ancestorTasks[x].startsWith("T")) {

					// Element id가 ancestorTasks[x]와 같은 Element 찾아서 Task OID 값 받기
					for (int y = 0; y < nodeList.getLength(); y++) {

					    ancestorElement = (Element) nodeList.item(y);

					    if (ancestorElement != null) {

						tempTaskId = ancestorElement.getAttributeNode("id").getNodeValue();

						if (ancestorTasks[x].replaceAll("ss", "").equals(tempTaskId)) {

						    ancestorTaskOid = ancestorElement.getAttributeNode("TaskOid").getNodeValue();
						}
					    }
					}
				    } else if(StringUtil.checkString(ancestorTasks[x])){
				        ancestorTaskOid = "e3ps.project.TemplateTask:" + ancestorTasks[x].replaceAll("ss", "");
				    }

				    // 선행 Task 객체 가져오기
				    dependTask = (TemplateTask) CommonUtil.getObject(ancestorTaskOid);

				    if (dependTask != null) {

					if (!TaskDependencyHelper.isAlreadyDependencyLink(task, dependTask)) {

					    dependLink = TaskDependencyLink.newTaskDependencyLink(task, dependTask);
					    PersistenceHelper.manager.save(dependLink);
					}
				    }
				}

				// 4-9-3. 후행 Task Link 생성 - 후행 Task 중 신규 생성된 Task가 있을 경우에만 생성

				for (int x = 0; x < descendantTasks.length; x++) {

				    // 후행 Task 중 신규 생성된 Task가 있을 경우 해당 Task OID 값으로 후행 Task OID 가져오기
				    if (descendantTasks[x].startsWith("T")) {

					// Element id가 descendantTasks[x]와 같은 Element 찾아서 Task OID 값 받기
					for (int y = 0; y < nodeList.getLength(); y++) {

					    descendantElement = (Element) nodeList.item(y);

					    if (descendantElement != null) {

						tempTaskId = descendantElement.getAttributeNode("id").getNodeValue();

						if (descendantTasks[x].replaceAll("ss", "").equals(tempTaskId)) {

						    descendantTaskOid = descendantElement.getAttributeNode("TaskOid").getNodeValue();

						    // 후행 Task 객체 가져오기
						    dependTask = (TemplateTask) CommonUtil.getObject(descendantTaskOid);

						    if (dependTask != null) {

							if (!TaskDependencyHelper.isAlreadyDependencyLink(dependTask, task)) {

							    dependLink = TaskDependencyLink.newTaskDependencyLink(dependTask, task);
							    PersistenceHelper.manager.save(dependLink);
							}
						    }
						}
					    }
					}
				    }
				}
			    }
			    // [END] 4. Project Task 정보 저장

			} // [END] else if ( !taskId.startsWith(delTaskFlag) )

		    } // [END] 3. Grid Data 중 Project Task 일정 정보만 추출 - Data 정의가 Summary, Task인 Data Row만 추출

		} // [END] for ( int i = 0; i < nodeList.getLength(); i++ )

		trx.commit(); // Transaction commit

		trx = null;

	    } // [END] if ( nodeList != null && nodeList.getLength() > 0 )

	    result = "0";
	} catch (SAXException e) {
	    result = "-99";
	    Kogger.error(ProjectTaskScheduleHelper.class, e);
	    throw new Exception(e);
	} catch (ParserConfigurationException e) {
	    result = "-99";
	    Kogger.error(ProjectTaskScheduleHelper.class, e);
	    throw new Exception(e);
	} catch (ServletException e) {
	    result = "-99";
	    Kogger.error(ProjectTaskScheduleHelper.class, e);
	    throw new Exception(e);
	} catch (IOException e) {
	    result = "-99";
	    Kogger.error(ProjectTaskScheduleHelper.class, e);
	    throw new Exception(e);
	} catch (Exception e) {
	    result = "-99";
	    Kogger.error(ProjectTaskScheduleHelper.class, e);
	    throw new Exception(e);
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }

	    return result;
	}
    }

    /**
     * 함수명 : uploadTaskSchedule 설명 : MPP Upload 후 저장
     * 
     * @param paramMap
     * @throws WTException
     *             작성자 : 김종호 작성일자 : 2013. 7. 23.
     */
    @SuppressWarnings("rawtypes")
    public static void uploadTaskSchedule(KETParamMapUtil paramMap) throws WTException {

	if (!SERVER) {

	    Class argTypes[] = new Class[] { KETParamMapUtil.class };
	    Object args[] = new Object[] { paramMap };

	    try {
		RemoteMethodServer.getDefault().invoke("uploadTaskSchedule", ProjectTaskScheduleHelper.class.getName(), null, argTypes,
		        args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectTaskScheduleHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectTaskScheduleHelper.class, e);
	    }
	    return;
	}

	String oid = "";
	String planStartDate = "";
	String planEndDate = "";
	String optionType = "";
	String taskType = "";
	String scheduleType = ""; 
	String priorityControl = "";
	String mainScheduleCode = "";
	String drValue = "";
	Object obj = null;
	E3PSProject project = null;
	E3PSTask e3psTask = null;
	TemplateTask dependTask = null;
	TaskDependencyLink dependLink = null;

	Transaction trx = new Transaction();
	Calendar tempCal = Calendar.getInstance();

	@SuppressWarnings("unchecked")
	ArrayList<KETParamMapUtil> projectTaskScheduleList = (ArrayList<KETParamMapUtil>) paramMap.get("projectTaskScheduleList");

	String mppFileName = paramMap.getString("fileFullPath");
	File file = new File(mppFileName);
	// 파일이 존재 할 경우
	if (file.exists()) {
	    try {
		ProjectReader reader = ProjectReaderUtility.getProjectReader(mppFileName);
		ProjectFile mpx = reader.read(mppFileName);
		Kogger.debug(ProjectTaskScheduleHelper.class, "MPP file type: " + mpx.getMppFileType());

		oid = ParamUtil.getStrParameter(paramMap.getString("oid"));
		obj = CommonUtil.getObject(oid); // OID로부터 Object return
		if (obj instanceof E3PSProject) {
		    project = (E3PSProject) obj;
		} else if (obj instanceof E3PSTask) {
		    e3psTask = (E3PSTask) obj;
		    project = (E3PSProject) e3psTask.getProject();
		}

		trx.start(); // Transaction 시작

		// 현재 Task 삭제
		for (KETParamMapUtil taskInfoMap : projectTaskScheduleList) {
		    PersistenceHelper.manager.delete((E3PSTask) CommonUtil.getObject(taskInfoMap.getString("taskOid")));
		}

		for (Task task : mpx.getAllTasks()) {

		    // mpp의 1레벨은 가져오지 않음
		    if (task.getOutlineLevel() > 1) {
			// 신규 Task 객체 생성
			e3psTask = E3PSTask.newE3PSTask();

			// Task 일정 객체 생성
			ExtendScheduleData scheduleData = new ExtendScheduleData();

			planStartDate = DateUtil.trancKST(task.getStart().toString());
			planEndDate = DateUtil.trancKST(task.getFinish().toString());

			if (StringUtil.checkString(planStartDate.trim()) && StringUtil.checkString(planEndDate.trim())) {
			    scheduleData.setDuration(DateUtil.getDaysFromTo(planEndDate, planStartDate)); // 기간
			}

			if (StringUtil.checkString(planStartDate)) {

			    tempCal.setTime(DateUtil.parseDateStr(new SimpleDateFormat("yyyy-MM-dd"), planStartDate));
			    scheduleData.setPlanStartDate(new Timestamp(tempCal.getTime().getTime())); // 계획시작일
			}

			if (StringUtil.checkString(planEndDate)) {

			    tempCal.setTime(DateUtil.parseDateStr(new SimpleDateFormat("yyyy-MM-dd"), planEndDate));
			    scheduleData.setPlanEndDate(new Timestamp(tempCal.getTime().getTime())); // 계획완료일
			}

			// Task 일정 정보 저장
			scheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(scheduleData);

			// Task 정보 Setting
			e3psTask.setTaskName(task.getName().trim()); // Task명
			e3psTask.setProject(project); // Project

			// Parent Task
			if (task.getParentTask() != null && task.getOutlineLevel() > 2) {
			    e3psTask.setParent((E3PSTask) CommonUtil.getObject(task.getParentTask().getNotes().toString()));
			}

			// Task Seq - OutlineNumber을 .(점)으로 split하여 마지막 index값을 seq에 세팅
			e3psTask.setTaskSeq(Integer
			        .parseInt(task.getOutlineNumber().split("\\.")[task.getOutlineNumber().split("\\.").length - 1]));

			if (task.getMilestone() == true) {
			    e3psTask.setMileStone(false); // Milestone여부(true)
			} else {
			    e3psTask.setMileStone(false); // Milestone여부(false)
			}

			if ("1".equals(optionType)) {
			    e3psTask.setOptionType(true); // 필수여부(true)
			} else {
			    e3psTask.setOptionType(false); // 필수여부(false)
			}

			e3psTask.setTaskType(taskType); // Task종류
			e3psTask.setScheduleType(scheduleType); // Schedule 구분
			e3psTask.setPriorityControl(priorityControl); //중점관리 task 구분
			e3psTask.setMainScheduleCode(mainScheduleCode); //주요일정식별코드

			if (StringUtil.checkString(drValue)) {
			    e3psTask.setDrValue(drValue); // DR 값
			}

			e3psTask.setTaskSchedule(ObjectReference.newObjectReference(scheduleData)); // Task - 일정 Link

			e3psTask = (E3PSTask) PersistenceHelper.manager.save(e3psTask); // Task 객체 저장

			// e3psTask의 Oid를 task Notes에 값 저장하여 Parent 찾을 때 사용
			task.setNotes(CommonUtil.getOIDString(e3psTask));

			// 선행 Task 객체 가져오기
			String relationList = relationList(task.getPredecessors());
			String[] relations = relationList.split(",");
			for (int i = 0; i < relations.length; i++) {
			    dependTask = (TemplateTask) CommonUtil.getObject(relations[i]);

			    if (dependTask != null) {

				if (!TaskDependencyHelper.isAlreadyDependencyLink(e3psTask, dependTask)) {

				    dependLink = TaskDependencyLink.newTaskDependencyLink(e3psTask, dependTask);
				    PersistenceHelper.manager.save(dependLink);
				}
			    }
			}

			// 후행 Task 객체 가져오기
			String relationList2 = relationList(task.getSuccessors());
			String[] relations2 = relationList2.split(",");
			for (int i = 0; i < relations2.length; i++) {
			    dependTask = (TemplateTask) CommonUtil.getObject(relations2[i]);

			    if (dependTask != null) {

				if (!TaskDependencyHelper.isAlreadyDependencyLink(dependTask, e3psTask)) {

				    dependLink = TaskDependencyLink.newTaskDependencyLink(dependTask, e3psTask);
				    PersistenceHelper.manager.save(dependLink);
				}
			    }
			}
		    }
		}

		trx.commit(); // Transaction commit

		trx = null;

		// 5. Project 전체 일정 수정
		ProjectScheduleHelper.manager.post_modify_Schedule(project, false);

	    } catch (Exception e) {
		Kogger.error(ProjectTaskScheduleHelper.class, e);
	    } finally {
		if (trx != null) {
		    trx.rollback();
		}

		File fileObj = new File(mppFileName);
		if (fileObj.isFile()) {
		    fileObj.delete();
		}
	    }
	}
    }

    /**
     * 함수명 : relationList 설명 :
     * 
     * @param relations
     * @return String - taskOid 작성자 : 김종호 작성일자 : 2013. 7. 23.
     */
    private static String relationList(List<Relation> relations) {
	String returnValue = "";

	if (relations != null && relations.isEmpty() == false) {
	    boolean first = true;
	    for (Relation relation : relations) {
		if (!first) {
		    returnValue = returnValue + (',');
		}
		first = false;
		returnValue = returnValue + relation.getTargetTask().getNotes().toString();
	    }
	}

	return returnValue;
    }
}
