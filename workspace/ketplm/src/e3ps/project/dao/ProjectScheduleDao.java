package e3ps.project.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import e3ps.common.query.LoggableStatement;
import e3ps.common.util.KETParamMapUtil;
import e3ps.project.E3PSProject;
import e3ps.project.ElectronTemplateProject;
import e3ps.project.MoldProject;
import e3ps.project.MoldTemplateProject;
import e3ps.project.ProductProject;
import e3ps.project.ProductTemplateProject;
import e3ps.project.ReviewProject;
import e3ps.project.TemplateProject;
import e3ps.project.WorkProject;
import ext.ket.shared.log.Kogger;

/**
 * [PLM System 1차개선] 클래스명 : ProjectScheduleDao 설명 : Project 일정 조회 Dao 작성자 : BoLee 작성일자 : 2013. 6. 17.
 */

public class ProjectScheduleDao {

    private Connection conn;

    public ProjectScheduleDao(Connection conn) {
	this.conn = conn;
    }

    public ProjectScheduleDao() {
	super();
    }

    /**
     * 함수명 : getProjectRootSchedule 설명 : Project Root 일정 조회
     * 
     * @param KETParamMapUtil
     *            paramMap
     * @return KETParamMapUtil
     * @throws Exception
     *             작성자 : BoLee 작성일자 : 2013. 6. 17.
     */
    public KETParamMapUtil getProjectRootSchedule(KETParamMapUtil paramMap) throws Exception {

	LoggableStatement pstmt = null;
	StringBuffer sb = null;
	long oidLong = 0;
	ResultSet rs = null;
	KETParamMapUtil projectRootScheduleMap = KETParamMapUtil.getMap();
	E3PSProject project = null;

	try {

	    oidLong = paramMap.getLong("oidLong"); // project oid(ida2a2)
	    project = (E3PSProject) paramMap.get("project"); // project 객체

	    sb = new StringBuffer();

	    sb.append("SELECT to_char(s.planstartdate, 'yyyy-MM-dd') planstartdate                                                             \n");
	    sb.append("      ,to_char(s.planenddate, 'yyyy-MM-dd') planenddate                                                                 \n");
	    sb.append("      ,to_char(s.execstartdate, 'yyyy-MM-dd') execstartdate                                                             \n");
	    sb.append("      ,to_char(s.execenddate, 'yyyy-MM-dd') execenddate                                                                 \n");
	    sb.append("      ,s.duration planduration                                                                                          \n");
	    sb.append("      ,(p.classnamea2a2 || ':' || p.ida2a2) taskoid                                                                     \n");
	    sb.append("      ,p.pjtcompletion taskcompletion                                                                                   \n");
	    sb.append("      ,p.prefercomp                                                                                                     \n");
	    sb.append("      ,p.pjtstate taskstate                                                                                             \n");
	    sb.append("  FROM E3PSProjectMaster m                                                                                              \n");

	    if (project instanceof ReviewProject) { // 검토 Project (ReviewProject)
		sb.append("      ,ReviewProject p                                                                                              \n");
	    } else if (project instanceof ProductProject) { // 제품 Project (ProductProject)
		sb.append("      ,ProductProject p                                                                                             \n");
	    } else if (project instanceof MoldProject) { // 금형 Project (MoldProject)
		sb.append("      ,MoldProject p                                                                                                \n");
	    } else if (project instanceof WorkProject) { // 업무 Project (WorkProject)
		sb.append("      ,WorkProject p                                                                                                \n");
	    }

	    sb.append("      ,ExtendScheduleData s                                                                                             \n");
	    sb.append(" WHERE p.ida3b8 = m.ida2a2                                                                                              \n");
	    sb.append("   AND p.ida3a8 = s.ida2a2                                                                                              \n");
	    sb.append("   AND p.ida2a2 = " + oidLong
		    + "                                                                                       \n");

	    pstmt = new LoggableStatement(conn, sb.toString());
	    rs = pstmt.executeQuery();

	    if (rs.next()) {

		projectRootScheduleMap.put("planStartDate", rs.getString(1)); // 계획시작일
		projectRootScheduleMap.put("planEndDate", rs.getString(2)); // 계획완료일
		projectRootScheduleMap.put("execStartDate", rs.getString(3)); // 실적시작일
		projectRootScheduleMap.put("execEndDate", rs.getString(4)); // 실적완료일
		projectRootScheduleMap.put("planDuration", rs.getInt(5)); // 기간
		projectRootScheduleMap.put("taskOid", rs.getString(6)); // Task OID (Project OID)
		projectRootScheduleMap.put("taskCompletion", rs.getString(7)); // Project 진척률
		projectRootScheduleMap.put("preferComp", rs.getString(8)); // Project 적정 진행률
		projectRootScheduleMap.put("taskState", rs.getString(9)); // Project 상태
	    }

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    sb.delete(0, sb.length());
	    statementRsClose(rs, pstmt);
	}

	return projectRootScheduleMap;
    }

    /**
     * 함수명 : getProjectTaskSchedule 설명 : Project Task 일정 조회
     * 
     * @param KETParamMapUtil
     *            paramMap
     * @return ArrayList<KETParamMapUtil>
     * @throws Exception
     *             작성자 : BoLee 작성일자 : 2013. 6. 17.
     */
    public ArrayList<KETParamMapUtil> getProjectTaskSchedule(KETParamMapUtil paramMap) throws Exception {

	LoggableStatement pstmt = null;
	StringBuffer sb = null;
	long oidLong = 0;
	String defaultTask = "일반";
	ResultSet rs = null;
	ArrayList<KETParamMapUtil> projectScheduleList = new ArrayList<KETParamMapUtil>();
	KETParamMapUtil taskInfoMap = null;
	E3PSProject project = null;

	try {

	    oidLong = paramMap.getLong("oidLong"); // project oid(ida2a2)
	    project = (E3PSProject) paramMap.get("project"); // project 객체

	    sb = new StringBuffer();

	    sb.append("SELECT t.ida2a2 id                                                                                                                      \n");
	    sb.append("      ,t.taskname                                                                                                                       \n");
	    sb.append("      ,t.tasknameen                                                                                                                       \n");
	    sb.append("      ,to_char(s.planstartdate, 'yyyy-MM-dd') planstartdate                                                                             \n");
	    sb.append("      ,to_char(s.planenddate, 'yyyy-MM-dd') planenddate                                                                                 \n");
	    sb.append("      ,to_char(s.execstartdate, 'yyyy-MM-dd') execstartdate                                                                             \n");
	    sb.append("      ,to_char(s.execenddate, 'yyyy-MM-dd') execenddate                                                                                 \n");
	    sb.append("      ,s.duration planduration                                                                                                          \n");
	    sb.append("      ,t.taskcompletion                                                                                                                 \n");
	    sb.append("      ,t.planworktime                                                                                                                    \n");
	    sb.append("      ,t.personrole                                                                                                                     \n");
	    sb.append("      ,t.milestone                                                                                                                      \n");
	    sb.append("      ,t.optiontype                                                                                                                     \n");
	    sb.append("      ,DECODE(t.tasktype, null, '" + defaultTask
		    + "', t.tasktype) tasktype                                                             \n");
	    sb.append("      ,t.scheduletype                                                                                                                        \n");
	    sb.append("      ,t.priorityControl                                                                                                                        \n");
	    sb.append("      ,t.drvalue                                                                                                                        \n");
	    sb.append("      ,LEVEL tasklevel                                                                                                                  \n");
	    sb.append("      ,DECODE(t.ida3parentreference, 0, '0', '0' || REPLACE(SYS_CONNECT_BY_PATH(t.ida2a2, '/'), '/' || t.ida2a2, '')) parenthierarchy   \n");
	    sb.append("      ,DECODE(t.ida3parentreference, 0, '', t.classnamea2a2 || ':' || t.ida3parentreference) parenttaskoid                              \n");
	    sb.append("      ,CONNECT_BY_ISLEAF isleaf                                                                                                         \n");
	    sb.append("      ,t.taskseq                                                                                                                        \n");
	    sb.append("      ,(t.classnamea2a2 || ':' || t.ida2a2) taskoid                                                                                     \n");
	    sb.append("      ,FN_GET_DEPENDENCY_TASK(t.ida2a2, s.planstartdate) taskancestors                                                                  \n");
	    sb.append("      ,t.taskstate                                                                                                                      \n");
	    sb.append("      ,t.debug_sub                                                                                                                      \n");
	    sb.append("      ,t.debug_n                                                                                                                        \n");
	    sb.append("      ,t.ncha                                                                                                                           \n");
	    sb.append("      ,t.tasktypecategory                                                                                                                           \n");
	    sb.append("      ,t.mainschedulecode                                                                                                                           \n");
	    sb.append("      ,t.costrequest                                                                                                                           \n");
	    sb.append("  FROM E3PSTask t                                                                                                                       \n");

	    if (project instanceof ReviewProject) { // 검토 Project (ReviewProject)
		sb.append("      ,ReviewProject p                                                                                                              \n");
	    } else if (project instanceof ProductProject) { // 제품 Project (ProductProject)
		sb.append("      ,ProductProject p                                                                                                             \n");
	    } else if (project instanceof MoldProject) { // 금형 Project (MoldProject)
		sb.append("      ,MoldProject p                                                                                                                \n");
	    }  else if (project instanceof WorkProject) { // 금형 Project (MoldProject)
		sb.append("      ,WorkProject p                                                                                                                \n");
	    }

	    sb.append("      ,ExtendScheduleData s                                                                                                             \n");
	    sb.append(" WHERE t.ida3b4 = p.ida2a2                                                                                                              \n");
	    sb.append("   AND t.ida3a4 = s.ida2a2                                                                                                              \n");
	    sb.append(" START WITH t.ida3parentreference = 0                                                                                                   \n");
	    sb.append("   AND p.ida2a2 = " + oidLong
		    + "                                                                                                       \n");
	    sb.append(" CONNECT BY PRIOR t.ida2a2 = t.ida3parentreference                                                                                      \n");
	    sb.append(" ORDER SIBLINGS BY t.taskseq                                                                                                            \n");

	    pstmt = new LoggableStatement(conn, sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {

		taskInfoMap = KETParamMapUtil.getMap();
		
		int iterator = 1;

		taskInfoMap.put("id", rs.getString(iterator++)); // ID
		taskInfoMap.put("taskName", rs.getString(iterator++)); // Task명
		taskInfoMap.put("taskNameEn", rs.getString(iterator++)); // Task명(영문)
		taskInfoMap.put("planStartDate", rs.getString(iterator++)); // 계획시작일
		taskInfoMap.put("planEndDate", rs.getString(iterator++)); // 계획완료일
		taskInfoMap.put("execStartDate", rs.getString(iterator++)); // 실적시작일
		taskInfoMap.put("execEndDate", rs.getString(iterator++)); // 실적완료일
		taskInfoMap.put("planDuration", rs.getInt(iterator++)); // 기간
		taskInfoMap.put("taskCompletion", rs.getFloat(iterator++)); // 진척률
		taskInfoMap.put("planWorkTime", rs.getString(iterator++)); // 계획공수
		taskInfoMap.put("personRole", rs.getString(iterator++)); // Role
		taskInfoMap.put("milestoneType", rs.getString(iterator++)); // Milestone여부
		taskInfoMap.put("optionType", rs.getString(iterator++)); // 필수여부
		taskInfoMap.put("taskType", rs.getString(iterator++)); // Task종류
		taskInfoMap.put("scheduleType", rs.getString(iterator++)); // Schedule 구분
		taskInfoMap.put("priorityControl", rs.getString(iterator++)); // 중점관리 구분
		taskInfoMap.put("drValue", rs.getString(iterator++)); // DR 값
		taskInfoMap.put("taskLevel", rs.getInt(iterator++)); // Level
		taskInfoMap.put("parentHierarchy", rs.getString(iterator++)); // Parent 계층 구조
		taskInfoMap.put("parentTaskOid", rs.getString(iterator++)); // Parent Task OID
		taskInfoMap.put("isLeaf", rs.getString(iterator++)); // Leaf Node 여부 (0 : Summary, 1 : Task)
		taskInfoMap.put("taskSeq", rs.getInt(iterator++)); // Task Seq
		taskInfoMap.put("taskOid", rs.getString(iterator++)); // Task OID
		taskInfoMap.put("taskAncestors", rs.getString(iterator++)); // 선행 Task
		taskInfoMap.put("taskState", rs.getInt(iterator++)); // Task 상태
		taskInfoMap.put("debugSub", rs.getInt(iterator++)); // Debug Sub Task 여부
		taskInfoMap.put("debugN", rs.getInt(iterator++)); // Debug Task 여부
		taskInfoMap.put("ncha", rs.getInt(iterator++)); // Debug 차수
		taskInfoMap.put("taskTypeCategory", rs.getString(iterator++));// TaskTypeCategory종류
		taskInfoMap.put("mainScheduleCode", rs.getString(iterator++)); // Schedule 구분
		taskInfoMap.put("costrequest", rs.getString(iterator++)); // 원가요청서 생성여부
		// Task 정보(taskInfoMap)를 Project Schedule ArrayList(projectScheduleList)에 추가
		projectScheduleList.add(taskInfoMap);
	    }

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    sb.delete(0, sb.length());
	    statementRsClose(rs, pstmt);
	}

	return projectScheduleList;
    }

    /**
     * 함수명 : getWBSRootSchedule 설명 : WBS Root 일정 조회
     * 
     * @param KETParamMapUtil
     *            paramMap
     * @return KETParamMapUtil
     * @throws Exception
     *             작성자 : BoLee 작성일자 : 2013. 7. 16.
     */
    public KETParamMapUtil getWBSRootSchedule(KETParamMapUtil paramMap) throws Exception {

	LoggableStatement pstmt = null;
	StringBuffer sb = null;
	long oidLong = 0;
	ResultSet rs = null;
	KETParamMapUtil projectRootScheduleMap = KETParamMapUtil.getMap();
	TemplateProject project = null;

	try {

	    oidLong = paramMap.getLong("oidLong"); // WBS oid(ida2a2)
	    project = (TemplateProject) paramMap.get("project"); // WBS 객체

	    sb = new StringBuffer();

	    sb.append("SELECT to_char(s.planstartdate, 'yyyy-MM-dd') planstartdate                                                             \n");
	    sb.append("      ,to_char(s.planenddate, 'yyyy-MM-dd') planenddate                                                                 \n");
	    sb.append("      ,s.duration planduration                                                                                          \n");
	    sb.append("      ,(p.classnamea2a2 || ':' || p.ida2a2) taskoid                                                                     \n");
	    sb.append("  FROM ProjectMaster m                                                                                                  \n");

	    if (project instanceof ElectronTemplateProject) { // 전자 제품 WBS (ElectronTemplateProject)
		sb.append("      ,ElectronTemplateProject p                                                                                    \n");
	    } else if (project instanceof ProductTemplateProject) { // 자동차 제품 WBS (ProductTemplateProject)
		sb.append("      ,ProductTemplateProject p                                                                                     \n");
	    } else if (project instanceof MoldTemplateProject) { // 자동차 금형 WBS (MoldTemplateProject)
		sb.append("      ,MoldTemplateProject p                                                                                        \n");
	    } else if (project instanceof TemplateProject) { // 검토 WBS (TemplateProject)
		sb.append("      ,TemplateProject p                                                                                            \n");
	    }

	    sb.append("      ,ScheduleData s                                                                                                   \n");
	    sb.append(" WHERE p.ida3b8 = m.ida2a2                                                                                              \n");
	    sb.append("   AND p.ida3a8 = s.ida2a2                                                                                              \n");
	    sb.append("   AND p.ida2a2 = " + oidLong
		    + "                                                                                       \n");

	    pstmt = new LoggableStatement(conn, sb.toString());
	    rs = pstmt.executeQuery();

	    if (rs.next()) {

		projectRootScheduleMap.put("planStartDate", rs.getString(1)); // 계획시작일
		projectRootScheduleMap.put("planEndDate", rs.getString(2)); // 계획완료일
		projectRootScheduleMap.put("planDuration", rs.getInt(3)); // 기간
		projectRootScheduleMap.put("taskOid", rs.getString(4)); // Task OID (Project OID)
	    }

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    sb.delete(0, sb.length());
	    statementRsClose(rs, pstmt);
	}

	return projectRootScheduleMap;
    }

    /**
     * 함수명 : getWBSTaskSchedule 설명 : WBS Task 일정 조회
     * 
     * @param KETParamMapUtil
     *            paramMap
     * @return ArrayList<KETParamMapUtil>
     * @throws Exception
     *             작성자 : BoLee 작성일자 : 2013. 7. 16.
     */
    public ArrayList<KETParamMapUtil> getWBSTaskSchedule(KETParamMapUtil paramMap) throws Exception {

	LoggableStatement pstmt = null;
	StringBuffer sb = null;
	long oidLong = 0;
	String defaultTask = "일반";
	ResultSet rs = null;
	ArrayList<KETParamMapUtil> projectScheduleList = new ArrayList<KETParamMapUtil>();
	KETParamMapUtil taskInfoMap = null;
	TemplateProject project = null;

	try {

	    oidLong = paramMap.getLong("oidLong"); // WBS oid(ida2a2)
	    project = (TemplateProject) paramMap.get("project"); // WBS 객체

	    sb = new StringBuffer();

	    sb.append("SELECT t.ida2a2 id                                                                                                                      \n");
	    sb.append("      ,t.taskname                                                                                                                       \n");
	    sb.append("      ,t.tasknameen                                                                                                                       \n");
	    sb.append("      ,to_char(s.planstartdate, 'yyyy-MM-dd') planstartdate                                                                             \n");
	    sb.append("      ,to_char(s.planenddate, 'yyyy-MM-dd') planenddate                                                                                 \n");
	    sb.append("      ,s.duration planduration                                                                                                          \n");
	    sb.append("      ,t.personrole                                                                                                                     \n");
	    sb.append("      ,t.milestone                                                                                                                      \n");
	    sb.append("      ,t.optiontype                                                                                                                     \n");
	    sb.append("      ,DECODE(t.tasktype, null, '" + defaultTask
		    + "', t.tasktype) tasktype                                                             \n");
	    sb.append("      ,t.scheduletype                                                                                                                        \n");
	    sb.append("      ,t.priorityControl                                                                                                                        \n");
	    sb.append("      ,t.drvalue                                                                                                                        \n");
	    sb.append("      ,LEVEL tasklevel                                                                                                                  \n");
	    sb.append("      ,DECODE(t.ida3parentreference, 0, '0', '0' || REPLACE(SYS_CONNECT_BY_PATH(t.ida2a2, '/'), '/' || t.ida2a2, '')) parenthierarchy   \n");
	    sb.append("      ,DECODE(t.ida3parentreference, 0, '', t.classnamea2a2 || ':' || t.ida3parentreference) parenttaskoid                              \n");
	    sb.append("      ,CONNECT_BY_ISLEAF isleaf                                                                                                         \n");
	    sb.append("      ,t.taskseq                                                                                                                        \n");
	    sb.append("      ,(t.classnamea2a2 || ':' || t.ida2a2) taskoid                                                                                     \n");
	    sb.append("      ,FN_GET_DEPENDENCY_WBS_TASK(t.ida2a2, s.planstartdate) taskancestors                                                              \n");
	    sb.append("      ,t.planworktime                                                              \n");
	    sb.append("      ,t.common                                                              \n");
	    sb.append("      ,t.mdraw                                                              \n");
	    sb.append("      ,t.hw                                                              \n");
	    sb.append("      ,t.sw                                                              \n");
	    sb.append("      ,t.m                                                              \n");
	    sb.append("      ,t.p                                                              \n");
	    sb.append("      ,t.buy                                                              \n");
	    sb.append("      ,t.system                                                              \n");
	    sb.append("      ,t.newType                                                              \n");
	    sb.append("      ,t.modifyType                                                              \n");
	    sb.append("      ,t.taskTypeCategory                                                              \n");
	    sb.append("      ,t.drValueCondition                                                                                                                        \n");
	    sb.append("      ,t.mainScheduleCode                                                                                                                        \n");
	    sb.append("  FROM TemplateTask t                                                                                                                   \n");

	    if (project instanceof ElectronTemplateProject) { // 전자 제품 WBS (ElectronTemplateProject)
		sb.append("      ,ElectronTemplateProject p                                                                                                    \n");
	    } else if (project instanceof ProductTemplateProject) { // 자동차 제품 WBS (ProductTemplateProject)
		sb.append("      ,ProductTemplateProject p                                                                                                     \n");
	    } else if (project instanceof MoldTemplateProject) { // 자동차 금형 WBS (MoldTemplateProject)
		sb.append("      ,MoldTemplateProject p                                                                                                        \n");
	    } else if (project instanceof TemplateProject) { // 검토 WBS (TemplateProject)
		sb.append("      ,TemplateProject p                                                                                                            \n");
	    }

	    sb.append("      ,ScheduleData s                                                                                                                   \n");
	    sb.append(" WHERE t.ida3b4 = p.ida2a2                                                                                                              \n");
	    sb.append("   AND t.ida3a4 = s.ida2a2                                                                                                              \n");
	    sb.append("   AND p.ida2a2 = " + oidLong
		    + "                                                                                                       \n");
	    sb.append(" START WITH t.ida3parentreference = 0                                                                                                   \n");
	    sb.append(" CONNECT BY PRIOR t.ida2a2 = t.ida3parentreference                                                                                      \n");
	    sb.append(" ORDER SIBLINGS BY t.taskseq                                                                                                            \n");

	    pstmt = new LoggableStatement(conn, sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {

		taskInfoMap = KETParamMapUtil.getMap();
		
		int iterator = 1;

		taskInfoMap.put("id", rs.getString(iterator++)); // ID
		taskInfoMap.put("taskName", rs.getString(iterator++)); // Task명
		taskInfoMap.put("taskNameEn", rs.getString(iterator++)); // Task명(영문)
		taskInfoMap.put("planStartDate", rs.getString(iterator++)); // 계획시작일
		taskInfoMap.put("planEndDate", rs.getString(iterator++)); // 계획완료일
		taskInfoMap.put("planDuration", rs.getInt(iterator++)); // 기간
		taskInfoMap.put("personRole", rs.getString(iterator++)); // Role
		taskInfoMap.put("milestoneType", rs.getString(iterator++)); // Milestone여부
		taskInfoMap.put("optionType", rs.getString(iterator++)); // 필수여부
		taskInfoMap.put("taskType", rs.getString(iterator++)); // Task종류
		taskInfoMap.put("scheduleType", rs.getString(iterator++)); // Schedule 구분
		taskInfoMap.put("priorityControl", rs.getString(iterator++)); // 중점관리 구분
		taskInfoMap.put("drValue", rs.getString(iterator++)); // DR 값
		taskInfoMap.put("taskLevel", rs.getInt(iterator++)); // Level
		taskInfoMap.put("parentHierarchy", rs.getString(iterator++)); // Parent 계층 구조
		taskInfoMap.put("parentTaskOid", rs.getString(iterator++)); // Parent Task OID
		taskInfoMap.put("isLeaf", rs.getString(iterator++)); // Leaf Node 여부 (0 : Summary, 1 : Task)
		taskInfoMap.put("taskSeq", rs.getInt(iterator++)); // Task Seq
		taskInfoMap.put("taskOid", rs.getString(iterator++)); // Task OID
		taskInfoMap.put("taskAncestors", rs.getString(iterator++)); // 선행 Task
		taskInfoMap.put("planWorkTime", rs.getString(iterator++)); // 선행 Task
		taskInfoMap.put("common", rs.getString(iterator++)); // 선행 Task
		taskInfoMap.put("mdraw", rs.getString(iterator++)); // 선행 Task
		taskInfoMap.put("hw", rs.getString(iterator++)); // 선행 Task
		taskInfoMap.put("sw", rs.getString(iterator++)); // 선행 Task
		taskInfoMap.put("m", rs.getString(iterator++)); // 선행 Task
		taskInfoMap.put("p", rs.getString(iterator++)); // 선행 Task
		taskInfoMap.put("buy", rs.getString(iterator++)); // 선행 Task
		taskInfoMap.put("system", rs.getString(iterator++)); // 선행 Task
		taskInfoMap.put("newType", rs.getString(iterator++)); // 선행 Task
		taskInfoMap.put("modifyType", rs.getString(iterator++)); // 선행 Task
		taskInfoMap.put("taskTypeCategory", rs.getString(iterator++));
		taskInfoMap.put("drValueCondition", rs.getString(iterator++)); //DR 조건부승인 값
		taskInfoMap.put("mainScheduleCode", rs.getString(iterator++)); //주요일정식별코드

		// Task 정보(taskInfoMap)를 Project Schedule ArrayList(projectScheduleList)에 추가
		projectScheduleList.add(taskInfoMap);
	    }

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    sb.delete(0, sb.length());
	    statementRsClose(rs, pstmt);
	}

	return projectScheduleList;
    }

    /**
     * 함수명 : getCustomerSchedule 설명 : 고객사 일정 조회
     * 
     * @param KETParamMapUtil
     *            paramMap
     * @return KETParamMapUtil
     * @throws Exception
     *             작성자 : Hooni 작성일자 : 2013. 9. 26.
     */
    public ArrayList<KETParamMapUtil> getCustomerSchedule(Long oidLong) throws Exception {

	LoggableStatement pstmt = null;
	StringBuffer sb = null;

	String defaultTask = "일반";
	ResultSet rs = null;
	ArrayList<KETParamMapUtil> customerPlanList = new ArrayList<KETParamMapUtil>();
	KETParamMapUtil customerPlanMap = null;
	E3PSProject project = null;

	try {
	    sb = new StringBuffer();
	    sb.append("SELECT N.name as customerName, \n");
	    sb.append("       C.ini_sample as ini_sample, \n");
	    sb.append("       C.ini_date as ini_date \n");
	    sb.append("   FROM customerPlan C, numberCode N \n");
	    sb.append("     WHERE C.ida3a3 = N.ida2a2 AND C.ida3b3 = " + oidLong + "    \n");

	    pstmt = new LoggableStatement(conn, sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {

		customerPlanMap = KETParamMapUtil.getMap();

		customerPlanMap.put("customerName", rs.getString(1)); // 고객사
		customerPlanMap.put("ini_sample", rs.getString(2)); // 일정명
		customerPlanMap.put("ini_date", rs.getString(3)); // 일정
		// (customerPlanMap)를 Project Schedule ArrayList(customerPlanList)에 추가
		customerPlanList.add(customerPlanMap);
	    }

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    sb.delete(0, sb.length());
	    statementRsClose(rs, pstmt);
	}

	return customerPlanList;
    }
    
    /**
     * 함수명 : getProjectTaskMainSchedule 설명 : Project Task 주요 일정 조회
     * 
     * @param KETParamMapUtil
     *            paramMap
     * @return ArrayList<KETParamMapUtil>
     * @throws Exception
     *             작성자 : 황정태 작성일자 : 2018. 8. 28.
     */
    public ArrayList<KETParamMapUtil> getProjectTaskMainSchedule(KETParamMapUtil paramMap) throws Exception {

	LoggableStatement pstmt = null;
	StringBuffer sb = null;
	long oidLong = 0;
	
	ResultSet rs = null;
	ArrayList<KETParamMapUtil> projectScheduleList = new ArrayList<KETParamMapUtil>();
	KETParamMapUtil taskInfoMap = null;
	E3PSProject project = null;
	String mainScheduleCode = "";
	try {

	    oidLong = paramMap.getLong("oidLong"); // project oid(ida2a2)
	    project = (E3PSProject) paramMap.get("project"); // project 객체
	    mainScheduleCode = (String)paramMap.get("mainScheduleCode");
	    
	    sb = new StringBuffer();

	    sb.append("SELECT s.planenddate                                                                                                                      \n");
	    sb.append("      ,(t.classnamea2a2 || ':' || t.ida2a2) taskoid                                                                                     \n");
	    sb.append("      ,t.mainschedulecode                                                                                                                           \n");
	    sb.append("  FROM E3PSTask t                                                                                                                       \n");

	    if (project instanceof ReviewProject) { // 검토 Project (ReviewProject)
		sb.append("      ,ReviewProject p                                                                                                              \n");
	    } else if (project instanceof ProductProject) { // 제품 Project (ProductProject)
		sb.append("      ,ProductProject p                                                                                                             \n");
	    } else if (project instanceof MoldProject) { // 금형 Project (MoldProject)
		sb.append("      ,MoldProject p                                                                                                                \n");
	    }

	    sb.append("      ,ExtendScheduleData s                                                                                                             \n");
	    sb.append(" WHERE t.ida3b4 = p.ida2a2                                                                                                              \n");
	    sb.append("   AND t.ida3a4 = s.ida2a2                                                                                                              \n");
	    sb.append("   AND mainschedulecode IS NOT NULL 												       \n");
	    if(StringUtils.isNotEmpty(mainScheduleCode)){
		sb.append("   AND mainschedulecode = '"+mainScheduleCode+"' 												       \n");
	    }
	    sb.append(" START WITH t.ida3parentreference = 0                                                                                                   \n");
	    sb.append("   AND p.ida2a2 = " + oidLong + "                                                                                                       \n");
	    sb.append(" CONNECT BY PRIOR t.ida2a2 = t.ida3parentreference                                                                                      \n");
	    sb.append(" ORDER BY mainschedulecode,execenddate,planenddate ASC                                                                                     \n");

	    pstmt = new LoggableStatement(conn, sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {

		taskInfoMap = KETParamMapUtil.getMap();

		taskInfoMap.put("taskOid", rs.getString("taskoid")); // Task OID
		taskInfoMap.put("mainScheduleCode", rs.getString("mainschedulecode")); // Schedule 구분

		// Task 정보(taskInfoMap)를 Project Schedule ArrayList(projectScheduleList)에 추가
		projectScheduleList.add(taskInfoMap);
	    }

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    sb.delete(0, sb.length());
	    statementRsClose(rs, pstmt);
	}

	return projectScheduleList;
    }

    /**
     * 함수명 : statementRsClose
     */
    public void statementRsClose(ResultSet rs, LoggableStatement pstmt) throws Exception {
	if (rs != null)
	    rs.close();

	if (pstmt != null)
	    pstmt.close();
    }

}
